package gpf.dc.basic.fe.component.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.kwaidoo.ms.tool.ToolUtilities;
import com.leavay.ms.tool.CmnUtil;

import cell.cdao.IDao;
import cell.cdao.IDaoService;
import cmn.i18n.I18nIntf;
import fe.cmn.data.CColor;
import fe.cmn.panel.PanelContext;
import fe.cmn.res.JDFICons;
import fe.cmn.table.OperateTableColumnDto;
import fe.cmn.table.TableBuilder;
import fe.cmn.table.TableContext;
import fe.cmn.table.TableDto;
import fe.cmn.table.TableHeaderDto;
import fe.cmn.table.TableOpener;
import fe.cmn.table.TableQuerier;
import fe.cmn.table.TableQuerierContext;
import fe.cmn.table.TableRowDto;
import fe.cmn.table.TableRowGestureDetectorDto;
import fe.cmn.table.TableRowsDto;
import fe.cmn.table.listener.TableRowListener;
import fe.cmn.widget.ButtonDto;
import fe.cmn.widget.ListenerDto;
import fe.cmn.widget.WidgetDto;
import fe.cmn.widget.listener.ListenerFeedbackDto;
import fe.util.FeLayoutUtil;
import fe.util.FeListenerUtil;
import fe.util.component.AbsBasicTablePanel;
import fe.util.component.FormEditPanelIntf;
import fe.util.component.dto.FeDeliverData;
import fe.util.component.extlistener.AfterLoadedData;
import fe.util.component.extlistener.CommandListener;
import fe.util.dto.loading.LoadingMaskConfigDto;
import fe.util.exception.handler.ExceptionHandlerFactory;
import fe.util.intf.ServiceIntf;
import fe.util.style.FeStyleConst;
import gpf.adur.data.FormField;
import gpf.dc.basic.dto.view.FeEventSubscribeDto;
import gpf.dc.basic.dto.view.TimerConfigDto;
import gpf.dc.basic.fe.component.app.AppCacheUtil;
import gpf.dc.basic.fe.component.app.AppEventConst;
import gpf.dc.basic.fe.component.param.BaseTableViewParam;
import gpf.dc.basic.fe.component.param.ViewBriefInfo;
import gpf.dc.basic.param.view.dto.ButtonDefine;
import gpf.dc.basic.param.view.dto.TableViewSetting;
import gpf.dc.fe.util.GpfEventUtil;
import gpf.dto.model.data.ActionPrivilegeDto;

public abstract class AbsTableView <T extends BaseTableViewParam> extends AbsBasicTablePanel<T> implements TableViewActionIntf<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1681857838147570124L;
	
//	@Override
//	public I18nIntf getI18n(PanelContext context) throws Exception {
//		return AppCacheUtil.getAppFeI18n(context);
//	}
	@Override	
	public String getI18nString(PanelContext context,String key,Object... params) throws Exception {
		I18nIntf i18n = getI18n(context);
		if(i18n == null)
			return key;
		return i18n.formatInGroup(key, widgetParam.getModelId(),params);
	}
	
	@Override
	public boolean isLayoutMode() {
		return widgetParam.isLayoutMode();
	}
	
	@Override
	public boolean isLazyQueryCompoundField() {
		return widgetParam.isLazyQueryCompoundField();
	}
	
	public List<WidgetDto> buildColButton(PanelContext panelContext,FormField field) throws Exception{
		return null;
	}
	
	/**
	 * 构建表格的行操作按钮
	 *
	 * @param context
	 * @param widgetParam
	 * @return
	 * @throws Exception
	 */
	public List<ButtonDto> buildRowOperateButtons(PanelContext context, T widgetParam) throws Exception {
		TableViewSetting setting = widgetParam.getSetting(TableViewSetting.class);

		FeDeliverData<T> data = new FeDeliverData<T>(getClass());
		List<ButtonDto> buttons = new ArrayList<>();

		ButtonDto delete = new ButtonDto("", JDFICons.delete).setWidgetId(ROW_DELETE_WIDGET_ID)
				.setOnClick(FeListenerUtil.newListener(getBuilderService(), CMD_ROW_DELETE, true, data));
		delete.setStyleName(FeStyleConst.common_table_row_icon_btn);
		delete.setVisible(setting.isAllowDelete());
		buttons.add(delete);
		if(setting.isShowRowDetailBtn()) {
			ButtonDto detailBtn = buildRowDetailBtn();
			buttons.add(detailBtn);
		}
		//添加行操作按钮
		for(ButtonDefine button : widgetParam.getRootRowOperateButtons()) {
			String buttonLabel = getI18nString(context, button.getLabel());
			if(button.isButtonGroup()) {
				ButtonDto rowBtn = buildButton(buttonLabel, button.getIconSrc(), CMD_CLICK_ROW_BUTTON_GROUP, null);
				rowBtn.setWidgetId(ROW_BUTTON_WIDGET_PREFIX+button.getName());
				rowBtn.setStyleName(FeStyleConst.common_table_row_icon_btn);
				buttons.add(rowBtn);
			}else {
				ButtonDto rowBtn = buildButton(buttonLabel, button.getIconSrc(), ROW_BUTTON_WIDGET_PREFIX+button.getName(), null);
//				setRowButtonListenerByListenerDefine(button, widgetParam.getListenerDefines());
				rowBtn.setStyleName(FeStyleConst.common_table_row_icon_btn);
				buttons.add(rowBtn);
			}
		}
		return buttons;
	}

	/**
	 * -------------- 表格响应动作 --------------
	 */
//	@Override
//	public abstract Object newRowObject(ListenerDto listener, PanelContext panelContext, WidgetDto source) throws Exception;

	@Override
	public abstract FormEditPanelIntf buildEditRowPanel(ListenerDto listener, PanelContext panelContext, WidgetDto source, Object rowData, boolean isWriteable) throws Exception;

	@Override
	public abstract TableRowDto convert2TableRowDto(Object data) throws Exception;

	@Override
	public abstract TableRowDto doCreateRowData(PanelContext panelContext, Object rowData) throws Exception;

	@Override
	public abstract TableRowDto doUpdateRowData(PanelContext panelContext, Object rowData) throws Exception;

	@Override
	public abstract void doDeleteRowData(PanelContext panelContext, List<String> rowIds) throws Exception;

	public TableQuerier convertTableParam2TableQuerier(PanelContext panelContext) throws Exception {
		T widgetParam = getWidgetParam();
		Integer startPos = widgetParam.getStartPos();
		Integer pageSize = widgetParam.getPageSize();
		if(panelContext instanceof TableContext) {
			Integer contextPageSize = ((TableContext) panelContext).getPageSize();
			pageSize = contextPageSize == null ? widgetParam.getPageSize() : contextPageSize;
		}
		TableViewSetting tableSetting = widgetParam.getSetting(TableViewSetting.class);
		if(tableSetting.getPageSize() != null) {
			pageSize = tableSetting.getPageSize();
		}
//		boolean queryCount = widgetParam.isQueryCount();
		boolean queryCount = tableSetting.isPaginationable();

		FeDeliverData<T> querierData = new FeDeliverData<T>(getClass());
		TableQuerier tableQuerier = new TableQuerier();
		if (startPos != null) {
			tableQuerier.setStartPos(startPos);
		}
		if (pageSize != null) {
			tableQuerier.setPageSize(pageSize);
		}
		tableQuerier.setQueryCount(queryCount);
		tableQuerier.setBinaryData(querierData);
		return tableQuerier;
	}

	@Override
	public TableQuerier doRebuildTableQuerier(ListenerDto listener, PanelContext panelContext, WidgetDto source) throws Exception {
		return convertTableParam2TableQuerier(panelContext);
	}
	
	
	public void initPanelCache(PanelContext panelContext,String panelGlobalKey) throws Exception {
		initPanelCacheValue(panelContext, panelGlobalKey, CacheKey_ActionPrivileges, null);
		initPanelCacheValue(panelContext, panelGlobalKey, CacheKey_RowActionPrivileges, null);
		initPanelCacheValue(panelContext, panelGlobalKey, CacheKey_FormField, null);
		initPanelCacheValue(panelContext, panelGlobalKey, CacheKey_FilterColumns, null);
		initPanelCacheValue(panelContext, panelGlobalKey, CacheKey_CustomQuerySql, null);
		initPanelCacheValue(panelContext, panelGlobalKey, CacheKey_Cnd, null);
		initPanelCacheValue(panelContext, panelGlobalKey, ServiceIntf.getCacheWidgetParamKey(widgetParam.getWidgetId()), widgetParam);
	}
	
	@Override
	public WidgetDto getWidget(PanelContext panelContext) throws Exception {
		return TableViewActionIntf.super.getWidget(panelContext);
	}

	@Override
	public WidgetDto doGetWidget(PanelContext panelContext) throws Exception {
//		if(ToolUtilities.isStringEmpty(widgetParam.getWidgetId())) {
//			widgetParam.setWidgetId(ToolUtilities.allockUUIDWithUnderline());
//		}
//		if(ToolUtilities.isStringEmpty(widgetParam.getPanelGlobalKey())) {
//			widgetParam.setPanelGlobalKey(ToolUtilities.allockUUIDWithUnderline());
//		}
		String panelGlobalKey = widgetParam.getPanelGlobalKey();
//		initPanelCache(panelContext, panelGlobalKey);
//		doBeforeInit(panelContext, panelGlobalKey);
		//计算动作权限
		if(!CmnUtil.isCollectionEmpty(getWidgetParam().getActionPrivilegeFuncs())) {
			try(IDao dao = IDaoService.get().newDao()){
				Map<String,ActionPrivilegeDto> actionPrivileges = caculateActionPrivilegeDto(panelContext, panelGlobalKey, dao, getWidgetParam().getActionPrivilegeFuncs());
				setActionPrivilege(panelContext, actionPrivileges);
			}
		}
		T widgetParam = getWidgetParam();
		boolean writable = widgetParam.isWritable();

		String widgetId = widgetParam.getWidgetId();
		List<Integer> pageSizes = widgetParam.getPageSizes();

		TableViewSetting setting = widgetParam.getSetting(TableViewSetting.class);
		boolean isShowCheckBox = writable && setting.isShowCheckBox();
		boolean isEnableRowDrag = writable && setting.isEnableRowDrag();
		boolean isEnableRowOperate = writable && setting.isEnableRowOperate();
		boolean isAllowRowClick = setting.isAllowRowClick();

		TableQuerier tableQuerier = convertTableParam2TableQuerier(null);

		FeDeliverData builderData = new FeDeliverData<>(getClass(), null);
		TableBuilder tableBuilder = new TableBuilder();
		tableBuilder.setBinaryData(builderData);

		TableOpener opener = new TableOpener()
				.setBuilder(tableBuilder)
				.setFirstPageQuerier(tableQuerier)
				.setServiceName(getBuilderService());

		WidgetDto topBar = getTopBar(panelContext, panelGlobalKey,widgetParam);
		FeDeliverData<T> lsnrData = new FeDeliverData<T>(getClass());
		TableDto table = new TableDto()
				.setNoDataText("")
				.setOpener(opener)
				.setTopBar(topBar)
				.setAutoFit(true)
				.setAutoFitType(setting.getColumnWidthAutoFitType())
				.setPageSizes(pageSizes);

		if (isAllowRowClick) {
			TableRowListener onClickLsnr = FeListenerUtil.OnTableRowClick(getBuilderService(), CMD_ON_TABLE_ROW_CLICK, true, lsnrData);
			CColor feedbackColor = ToolUtilities.clone(getFeStyleSetting(panelContext).getMainColor());
			feedbackColor.setOpacity(0.2f);
			onClickLsnr.setFeedback(ListenerFeedbackDto.backgroundColorFeedback(feedbackColor));
			if(setting.isDoubleClickOpen()) {
				table.setTableRowGestureDetector(new TableRowGestureDetectorDto()
						.setOnDoubleClick(onClickLsnr)
				);
			}else {
				table.setTableRowGestureDetector(new TableRowGestureDetectorDto()
						.setOnClick(onClickLsnr)
				);
			}
		}

		table.setShowCheckbox(isShowCheckBox)
				.setCheckType(setting.getCheckTypeEnum())
				.setCheckLimit(setting.getCheckLimit())
				.setHideHeader(setting.isHideHeader())
				.setHighlightType(setting.getHighlightTypeEnum())
				.setTextEditorEditMouseEventType(setting.getTextEditorEditMouseEventTypeEnum())
				.setKeepOneRowSelectedOnSingleClick(setting.getKeepOneRowSelectedOnSingleClick())
				.setTableCellsHighlightByShift(setting.getTableCellsHighlightByShift())
				.setLastColumnDragFillArea(setting.getLastColumnDragFillArea())
				.setShowRowIndex(setting.getShowRowIndex())
				.setEnableGuiSorting(setting.getEnableGuiSorting())
				.setEnableCheckboxChange(setting.getEnableCheckboxChange())
				.setEnableColumnDrag(setting.getEnableColumnDrag())
				.setEnableRowDrag(isEnableRowDrag)
				.setBarAlign(getTopBarAxisAlign())
				.setFontSizeAutoFix(false)
				.setWrapMask(true);
		

		if (isEnableRowOperate) {
			List<ButtonDto> rowOperateButton = buildRowOperateButtons(panelContext, widgetParam);
			if (!CmnUtil.isCollectionEmpty(rowOperateButton)) {
				table.setOperateTableColumn(new OperateTableColumnDto().setColumnFrozenType(setting.getRowOperateColumnFrozenTypeEnum()));
				//调整根据按钮文字设定宽度
				int opRowWidth = 150;
				if (setting.isRowOperateAutoWidth()) {
					double fontSize = getFeStyleSetting(panelContext).getMainBodyFontSize();
					opRowWidth = FeLayoutUtil.caculateRowOperateWidth(fontSize, rowOperateButton);
				} else {
					if (setting.getRowOperateFixWidth() != null) {
						opRowWidth = setting.getRowOperateFixWidth();
					}
				}
				table.getOperateTableColumn().setButtons(rowOperateButton).setFixWidth(opRowWidth);
			}
		}

		AfterLoadedData loadedDataLsnr = new AfterLoadedData();
		loadedDataLsnr.setListener(FeListenerUtil.newListener(getBuilderService(), CMD_ON_LOADED_DATA, true, lsnrData));
		table.addExtendListener(loadedDataLsnr);
		
		ListenerDto addOrUpdateRowLsnr = newListener(getBuilderService(), CMD_ADD_OR_UPDATE_ROW, true, null);
		CommandListener addOrUpdateRow = new CommandListener(CMD_ADD_OR_UPDATE_ROW, "", "", addOrUpdateRowLsnr, true);
		table.addExtendListener(addOrUpdateRow);
//		//设置指令回调监听
//		setCommandCallbackListener(table, widgetParam.getCommandCallbackLsnrs());
//		subscribEvent(panelContext, table, getEventSubscribes());
//		//设置定时器
//		initTimer(panelContext, this,table, widgetParam.getTimerConfigs());
		table.setBinaryData(widgetParam);

		if (!StringUtils.isEmpty(widgetId)) {
			table.setWidgetId(widgetId);
		}
		table.setPanelGlobalKey(panelGlobalKey);
		table.setStyleName(setting.getTableStyle());
		
		return table;
	}
	
	@Override
	public List<TimerConfigDto> getTimerConfigs() {
		return widgetParam.getTimerConfigs();
	}

	/**
	 * -------------- 表格列数据 --------------
	 */
	@Override
	public List<String> getRequestCategorys(PanelContext panelContext) {
		try {
//			TraceUtil.logStart();
//			FormModel model = IFormMgr.get().queryFormModel(widgetParam.getModelId());
//			TraceUtil.printCost("getRequestCategorys", false);
			List<String> categorys = new ArrayList<>();
			if(widgetParam.getViewBriefInfo() != null) {
				categorys = widgetParam.getViewBriefInfo().getCategorys();
			}
			if(categorys.isEmpty()) {
				String modelLabel = widgetParam.getModelId();//
				categorys.add(modelLabel);
			}
			return categorys;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 构建表格的表格列
	 *
	 * @param builder
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public abstract TableHeaderDto doQueryTableMeta(TableBuilder builder, TableQuerierContext context) throws Exception;

	public void afterQueryTableMeta(TableBuilder builder, TableQuerierContext context, TableHeaderDto header) throws Exception {

	}
	
	public TableRowDto queryCurrentRow(PanelContext context) throws Exception {
		if(context instanceof TableContext) {
			TableContext ctx = (TableContext)context;
			if(!CmnUtil.isStringEmpty(ctx.getCurrentRowId())) {
				return queryRow(context, ctx.getCurrentRowId());
			}
		}
		return null;
	}

	@Override
	public TableHeaderDto queryTableMeta(TableBuilder builder, TableQuerierContext context) throws Exception {
		TableHeaderDto header = doQueryTableMeta(builder, context);
		afterQueryTableMeta(builder, context, header);
		return header;
	}

	/**
	 * -------------- 表格行数据 --------------
	 */

	/**
	 * 查询表格行数据
	 *
	 * @param builder
	 * @param querier
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public abstract TableRowsDto doQueryTableRows(TableBuilder builder, TableQuerier querier, TableQuerierContext context) throws Exception;

	public void afterQueryTableRows(TableBuilder builder, TableQuerierContext context, TableRowsDto tableRows) throws Exception {

	}

	@Override
	public TableRowsDto queryTableRows(TableBuilder builder, TableQuerier querier, TableQuerierContext context) throws Exception {
		TableRowsDto tableRows = new TableRowsDto(new ArrayList<>());

		try {
			tableRows = doQueryTableRows(builder, querier, context);
			afterQueryTableRows(builder, context, tableRows);
			fireEventOfRefreshDataTotalCount(context, tableRows);
		} catch (Exception e) {
			ExceptionHandlerFactory handlerFactory = ExceptionHandlerFactory.get();
			handlerFactory.handle(getService(),context, e);
		}
		
		return tableRows;
	}
	/**
	 * 触发表格总记录数刷新事件，只在无查询条件下触发
	 * @param context
	 * @param tableRows
	 * @throws Exception
	 */
	public void  fireEventOfRefreshDataTotalCount(PanelContext context,TableRowsDto tableRows) throws Exception {
		if(userFilterIsNotEmpty(context)) {
			return;
		}
		//只在第一次初始化的时候设置
		int totalCnt = tableRows.getTotalRows();
		ViewBriefInfo buildInfo = widgetParam.getViewBriefInfo();
		if(buildInfo == null) {
			buildInfo = new ViewBriefInfo();
			buildInfo.setCategorys(getRequestCategorys(context));
		}
		Object[] params = new Object[] {buildInfo,totalCnt};
		GpfEventUtil.fireGlobalEvent(context, AppEventConst.EVENT_RERESH_VIEW_DATA_TOTAL_COUNT, null, params);
	}
	
	public LoadingMaskConfigDto getLoadingMaskConfig(PanelContext panelContext) throws Exception {
		LoadingMaskConfigDto config = AppCacheUtil.getDefaultLoadingMaskConfig(panelContext);
		return config;
	}
	
	@Override
	public List<FeEventSubscribeDto> getEventSubscribes() throws Exception {
		return widgetParam.getEventSubscribes();
	}
}
