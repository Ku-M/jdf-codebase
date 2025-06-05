package fe.util.component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.kwaidoo.ms.tool.ToolUtilities;
import com.leavay.ms.tool.CmnUtil;

import fe.cmn.data.FePojo;
import fe.cmn.data.PairDto;
import fe.cmn.panel.PanelContext;
import fe.cmn.panel.ability.RebuildChild;
import fe.cmn.table.ColumnWidthAutoFitType;
import fe.cmn.table.OperateTableColumnDto;
import fe.cmn.table.TableBuilder;
import fe.cmn.table.TableColumnDto;
import fe.cmn.table.TableDto;
import fe.cmn.table.TableHeaderDto;
import fe.cmn.table.TableOpener;
import fe.cmn.table.TableQuerier;
import fe.cmn.table.TableQuerierContext;
import fe.cmn.table.TableRowDto;
import fe.cmn.table.TableRowGestureDetectorDto;
import fe.cmn.table.TableRowsDto;
import fe.cmn.table.TableValueAdapter_AllRows;
import fe.cmn.table.ability.QueryTableRows;
import fe.cmn.widget.ButtonDto;
import fe.cmn.widget.LabelDto;
import fe.cmn.widget.ListenerDto;
import fe.cmn.widget.SizeDto;
import fe.cmn.widget.WidgetDto;
import fe.cmn.widget.decoration.DecorationDto;
import fe.util.FeLayoutUtil;
import fe.util.FeListenerUtil;
import fe.util.component.dto.FeDeliverData;
import fe.util.component.dto.TableSetting;
import fe.util.component.extlistener.OnTableRowChanged;
import fe.util.component.param.EmbedTableParam;
import fe.util.style.FeStyleConst;

/**
 * 表单中的内嵌表格组件
 * 在此组件中，将DTO的值保存在TableRowDto的BinaryData中，因此只需要在获取界面值时，只需要将表格行中的BinaryData拿出即可
 *
 * @param <T>
 * @author chenxb
 */
public abstract class AbsEmbedTablePanel<T extends EmbedTableParam<?>> extends AbsBasicTablePanel<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7851313191948188507L;

	@Override
	public final TableRowDto doCreateRowData(PanelContext panelContext, Object rowData) throws Exception {
		return convert2TableRowDto(rowData);
	}

	@Override
	public final TableRowDto doUpdateRowData(PanelContext panelContext, Object rowData) throws Exception {
		return convert2TableRowDto(rowData);
	}

	@Override
	public boolean isShowConfirmWhenDelete() {
		return false;
	}

	@Override
	public final void doDeleteRowData(PanelContext panelContext, List<String> rowIds) throws Exception {
	}

	@Override
	public final TableQuerier doRebuildTableQuerier(ListenerDto listener, PanelContext panelContext, WidgetDto source) throws Exception {
		return new TableQuerier();
	}

	@Override
	public WidgetDto getWidget(PanelContext panelContext) throws Exception {
		if(CmnUtil.isStringEmpty(widgetParam.getWidgetId())) {
			widgetParam.setWidgetId(ToolUtilities.allockUUIDWithUnderline());
		}
		String panelGlobalKey = ToolUtilities.allockUUIDWithUnderline();
		cacheWidgetParamInChannel(panelContext, panelGlobalKey);
		T widgetParam = getWidgetParam();
		boolean writable = widgetParam.isWritable();
		TableSetting setting = widgetParam.getSetting();

		boolean isShowCheckBox = writable && setting.isShowCheckBox();
		boolean isEnableRowDrag = writable && setting.isEnableRowDrag();
		boolean isEnableRowOperate = writable && setting.isEnableRowOperate();
		boolean isAllowRowClick = setting.isAllowRowClick();

		FeDeliverData<T> querierData = new FeDeliverData<>(getClass());

		TableQuerier tableQuerier = new TableQuerier();
		tableQuerier.setBinaryData(querierData);

		FeDeliverData builderData = new FeDeliverData<>(getClass());
		TableBuilder tableBuilder = new TableBuilder();
		tableBuilder.setBinaryData(builderData);

		TableOpener opener = new TableOpener()
				.setBuilder(tableBuilder)
				.setFirstPageQuerier(tableQuerier)
				.setServiceName(getBuilderService());

		WidgetDto topBar = getTopBar(panelContext, widgetParam);
		FeDeliverData<T> lsnrData = new FeDeliverData<>(getClass(), null);
		TableDto table = new TableDto()
				.setOpener(opener)
				.setTopBar(topBar)
				.setAutoFit(true)
				.setAutoFitType(ColumnWidthAutoFitType.header)
				.setMinSize(SizeDto.height(40))
				.setMaxSize(SizeDto.height(300));

		if (isAllowRowClick) {
			table.setTableRowGestureDetector(new TableRowGestureDetectorDto()
					.setOnClick(FeListenerUtil.OnTableRowClick(getBuilderService(), CMD_ON_TABLE_ROW_CLICK, true, lsnrData))
			);
		}

		if (setting.getMaxEmbedHeight() != null) {
			table.setMaxSize(SizeDto.height(setting.getMaxEmbedHeight()));
		}

		table.setShowCheckbox(isShowCheckBox)
				.setNoDataText("")
				.setEnableRowDrag(isEnableRowDrag)
				.setFontSizeAutoFix(false)
				.setBarAlign(getTopBarAxisAlign())
				.setWrapMask(false)
				;

		if (isEnableRowOperate) {
			List<ButtonDto> rowOperateButton = buildRowOperateButtons(panelContext, widgetParam);
			if (!CmnUtil.isCollectionEmpty(rowOperateButton)) {
				int opRowWidth = 150;
				if (setting.isRowOperateAutoWidth()) {
					double fontSize = getFeStyleSetting(panelContext).getMainBodyFontSize();
					opRowWidth = FeLayoutUtil.caculateRowOperateWidth(fontSize, rowOperateButton);
				} else {
					if (setting.getRowOperateFixWidth() != null) {
						opRowWidth = setting.getRowOperateFixWidth();
					}
				}
				table.setOperateTableColumn(new OperateTableColumnDto().setButtons(rowOperateButton).setFixWidth(opRowWidth));
			}
		}

		table
				.setValueAdapter(new TableValueAdapter_AllRows())
				.addExtendListener(new OnTableRowChanged())
				.setBinaryData(widgetParam);

		if (!CmnUtil.isStringEmpty(widgetParam.getWidgetId())) {
			table.setWidgetId(widgetParam.getWidgetId());
		}
		table.setPanelGlobalKey(panelGlobalKey);
		table.setStyleName(FeStyleConst.common_table);
		return table;
	}

	/**
	 * -------------- 表格列数据 --------------
	 */

	/**
	 * 构建内嵌表格的表头，由于单元格不需要编辑，构建的表格列都是标签属性
	 *
	 * @return
	 * @throws Exception
	 */
	public abstract List<PairDto<String, String>> getTableHeader() throws Exception;

	public TableHeaderDto buildHeader(List<PairDto<String, String>> lstHeader) {
		List<TableColumnDto> lstCols = new LinkedList<TableColumnDto>();
		for (PairDto<String, String> header : lstHeader) {
			TableColumnDto column = new TableColumnDto();
			column.setEditor(new LabelDto().setDecoration(new DecorationDto()));
			column.setName(header.getKey());
			column.setLabel(header.getValue());
			lstCols.add(column);
		}
		TableHeaderDto meta = new TableHeaderDto();
		meta.setColumns(lstCols);

		return meta;
	}

	@Override
	public TableHeaderDto queryTableMeta(TableBuilder builder, TableQuerierContext context) throws Exception {
		FePojo fePojo = context.getTableFePojo();
		T widgetParam = (T) fePojo.getBinaryData();
		setWidgetParam(widgetParam);
		TableHeaderDto header = buildHeader(getTableHeader());
		afterQueryTableMeta(builder, context, header);
		return header;
	}

	/**
	 * 在表头数据查询后触发的后续操作
	 *
	 * @param builder
	 * @param context
	 * @param header
	 * @throws Exception
	 */
	public void afterQueryTableMeta(TableBuilder builder, TableQuerierContext context, TableHeaderDto header) throws Exception {

	}

	/**
	 * -------------- 表格行数据 --------------
	 */

	@Override
	public TableRowsDto queryTableRows(TableBuilder builder, TableQuerier querier, TableQuerierContext context) throws Exception {
		FePojo fePojo = context.getTableFePojo();
		T widgetParam = (T) fePojo.getBinaryData();
//		T widgetParam = (T) QueryBinaryData.queryOne(context, context.getCurrentPanelWidgetId());
		setWidgetParam(widgetParam);
		TableRowsDto rowsDto = new TableRowsDto();
		List lstConfig = widgetParam.getRows();
		List<TableRowDto> rows = new ArrayList<>();
		if (!CmnUtil.isCollectionEmpty(lstConfig)) {
			for (int i = 0; i < lstConfig.size(); i++) {
				Object config = lstConfig.get(i);
				TableRowDto row = convert2TableRowDto(config);
//				appendRowOperateCell(row);
				if (CmnUtil.isStringEmpty(row.getRowId()))
					row.setRowId(ToolUtilities.allockUUIDWithUnderline());
				rows.add(row);
			}
		}
		rowsDto.setRows(rows);
		onAfterQueryTableRows(builder, querier, context, rowsDto);
		return rowsDto;
	}

	/**
	 * 在表格行数据查询后触发的后续操作
	 *
	 * @param builder
	 * @param querier
	 * @param context
	 * @param rowsDto
	 * @throws Exception
	 */
	public void onAfterQueryTableRows(TableBuilder builder, TableQuerier querier, TableQuerierContext context, TableRowsDto rowsDto) throws Exception {
	}

	@Override
	public void setWritable(PanelContext panelContext, WidgetDto widget, boolean writable) throws Exception {
		TableDto tableDto = (TableDto) widget;
		T param = (T) tableDto.getBinaryData();
		if (param.isWritable() == writable) {
			return;
		}
		PanelContext copyCtx = panelContext.cloneWithChannel();
		copyCtx.setCurrentPanelGlobalKey(tableDto.getPanelGlobalKey());
		//获取整个表格数据，重建整个表格
		List<TableRowDto> rows = QueryTableRows.queryAll(copyCtx);
		List rowObjs = new ArrayList<>();
		for (TableRowDto row : rows) {
			rowObjs.add(row.getBinaryData());
		}
		param.setRows(rowObjs);
		param.setWritable(writable);
		setWidgetParam(param);
		TableDto newTable = (TableDto) getWidget(panelContext);
		newTable.setWidgetId(tableDto.getWidgetId());
		newTable.setPanelGlobalKey(tableDto.getPanelGlobalKey());
		RebuildChild.rebuild(panelContext, newTable);
	}
}