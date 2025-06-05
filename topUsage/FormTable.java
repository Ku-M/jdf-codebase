package gpf.dc.basic.fe.component.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.nutz.dao.Cnd;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.kwaidoo.ms.tool.ToolUtilities;
import com.leavay.ms.tool.CmnUtil;

import cell.cdao.IDao;
import cell.cdao.IDaoService;
import cell.cmn.io.IFiles;
import cell.fe.IFileService;
import cell.fe.gpf.dc.IAppGlobalSettingPlugin;
import cell.fe.gpf.dc.basic.IGpfDCBasicFeService;
import cell.fe.progress.CFeProgressCtrlWithTextArea;
import cell.gpf.adur.data.IFormMgr;
import cell.gpf.dc.backup.IBackupService;
import cell.gpf.dc.runtime.IDCRuntimeContext;
import cmn.anotation.ClassDeclare;
import cmn.anotation.FieldDeclare;
import cmn.dto.Progress;
import cmn.i18n.I18nIntf;
import cmn.util.TraceUtil;
import cmn.util.Tracer;
import fe.cmn.app.ability.PopToast;
import fe.cmn.data.BeFile;
import fe.cmn.data.ByteArrayDto;
import fe.cmn.data.PickFileType;
import fe.cmn.data.UploadFileResult;
import fe.cmn.panel.BoxDto;
import fe.cmn.panel.ContainerDto;
import fe.cmn.panel.CrossAxisAlign;
import fe.cmn.panel.MainAxisAlign;
import fe.cmn.panel.PanelContext;
import fe.cmn.panel.ability.DownloadFile;
import fe.cmn.panel.ability.QuitPopup;
import fe.cmn.panel.ability.UploadFile;
import fe.cmn.res.JDFICons;
import fe.cmn.table.TableBuilder;
import fe.cmn.table.TableColumnDto;
import fe.cmn.table.TableContext;
import fe.cmn.table.TableDto;
import fe.cmn.table.TableHeaderDto;
import fe.cmn.table.TableInterface;
import fe.cmn.table.TableQuerier;
import fe.cmn.table.TableQuerierContext;
import fe.cmn.table.TableRowDto;
import fe.cmn.table.TableRowsDto;
import fe.cmn.table.TableValueAdapter_AllRows;
import fe.cmn.widget.ButtonDto;
import fe.cmn.widget.LabelDto;
import fe.cmn.widget.ListenerDto;
import fe.cmn.widget.ListenerInterface;
import fe.cmn.widget.WidgetDto;
import fe.util.component.FormEditPanelIntf;
import fe.util.component.ProgressDialog;
import fe.util.component.SearchBar;
import fe.util.component.dto.FeDeliverData;
import fe.util.component.extlistener.CommandCallbackListener;
import fe.util.component.param.WidgetParam;
import fe.util.exception.VerifyException;
import fe.util.intf.ServiceIntf;
import fe.util.style.FeStyleConst;
import gpf.adur.data.DataType;
import gpf.adur.data.Form;
import gpf.adur.data.FormField;
import gpf.adur.data.FormModel;
import gpf.adur.data.ResultSet;
import gpf.adur.data.TableData;
import gpf.dc.basic.fe.component.app.AppCacheUtil;
import gpf.dc.basic.fe.component.fieldextend.editor.WidgetLayoutUtil;
import gpf.dc.basic.fe.component.param.BaseDataViewParam;
import gpf.dc.basic.fe.component.param.BaseTableViewParam;
import gpf.dc.basic.fe.enums.ListenerApplyLocation;
import gpf.dc.basic.fe.enums.TableCellEditorType;
import gpf.dc.basic.fe.intf.TableRowDtoInterceptor;
import gpf.dc.basic.i18n.GpfDCBasicI18n;
import gpf.dc.basic.param.view.dto.ButtonDefine;
import gpf.dc.basic.param.view.dto.FormFieldDefine;
import gpf.dc.basic.param.view.dto.OrderByOptionDto;
import gpf.dc.basic.param.view.dto.TableColumnDefine;
import gpf.dc.basic.param.view.dto.TableViewSetting;
import gpf.dc.basic.util.GpfDCBasicConst;
import gpf.dc.basic.util.GpfDCBasicUtil;
import gpf.dc.expimp.FormDataExcelExpImp;
import gpf.dc.fe.component.adur.data.FormCompnentIntf;
import gpf.dc.fe.component.adur.data.field.FormFieldEditorFactory;
import gpf.dc.fe.component.adur.data.field.handler.FormVisitor;
import gpf.dc.fe.util.GpfDCFeI18n;
import gpf.dc.intf.FormOpObserver;
import gpf.dto.model.data.ActionPrivilegeDto;
import web.dto.Pair;
@ClassDeclare(label = "表单表格"
,what=""
, why = ""
, how = ""
,developer="陈晓斌"
,version = "1.0"
,createTime = "2024-11-22"
,updateTime = "2024-11-22")
public class FormTable<T extends BaseTableViewParam> extends AbsTableView<T> implements TableInterface,ViewListenerBuilder,ListenerInterface,FormCompnentIntf<T>{

	/**
	 *
	 */
	private static final long serialVersionUID = 4631562325704060855L;
	@FieldDeclare(label = "升序排序", desc = "按升序排列数据的命令")
	public final static String CMD_ORDER_BY_ASC = "CMD_ORDER_BY_ASC";

	@FieldDeclare(label = "降序排序", desc = "按降序排列数据的命令")
	public final static String CMD_ORDER_BY_DESC = "CMD_ORDER_BY_DESC";

	@FieldDeclare(label = "退出弹窗", desc = "关闭当前弹窗的命令")
	public final static String CMD_QUIT_POPUP = "CMD_QUIT_POPUP";

	@FieldDeclare(label = "值变更", desc = "当值发生变化时触发的命令")
	public final static String CMD_VALUE_CHANGED = "CMD_VALUE_CHANGED";

	@FieldDeclare(label = "导出选中项", desc = "导出选中数据项的命令")
	public final static String CMD_EXPORT_SELECTED = "CMD_EXPORT_SELECTED";
	
	public static final String ROW_DELETE_WIDGET_ID = ROW_BUTTON_WIDGET_PREFIX+"delete";


	public Class<? extends ServiceIntf> getService(){
		return IGpfDCBasicFeService.class;
	}
	
	@Override
	public WidgetDto getTopBar(PanelContext panelContext,String panelGlobalKey,T widgetParam) throws Exception {
		Map<String,ActionPrivilegeDto> actionPriviegeMap = getActionPrivielge(panelContext);
		TableViewSetting setting = widgetParam.getSetting(TableViewSetting.class);
		boolean writable = widgetParam.isWritable();
		BoxDto buttonBar = new BoxDto().setVertical(false).setExpandInBox(false);
		ButtonDto refresh = buildRefreshButton(null).setVisible(setting.isAllowRefresh());
		setButtonPrivilege(actionPriviegeMap, refresh,widgetParam.getGroupVisibleOverride(),widgetParam.getGroupWritableOverride());
		buttonBar.addChildren(refresh);
		if(writable) {
			ButtonDefine createBtnDef = getButtonDefineByCommand(CMD_CREATE);
			ButtonDto create = null;
			if(createBtnDef != null) {
				create = buildButton(panelContext,createBtnDef);
			}else {
				create = buildButton("", JDFICons.plus, CMD_CREATE,null);
			}
			create.setVisible(setting.isAllowCreate());
			setButtonPrivilege(actionPriviegeMap, create,widgetParam.getGroupVisibleOverride(),widgetParam.getGroupWritableOverride());
			buttonBar.addChildren(create);
			if(setting.isAllowCopyRow()) {
				ButtonDefine copyBtnDef = getButtonDefineByCommand(CMD_COPY_ROW);
				ButtonDto copyBtn = null;
				if(copyBtnDef != null) {
					copyBtn = buildButton(panelContext,copyBtnDef);
				}else{
					copyBtn= buildButton("", JDFICons.copy, CMD_COPY_ROW, null);
				}
				setButtonPrivilege(actionPriviegeMap, copyBtn,widgetParam.getGroupVisibleOverride(),widgetParam.getGroupWritableOverride());
				buttonBar.addChildren(copyBtn);
			}
		}
		if(writable) {
			ButtonDefine deleteBtnDef = getButtonDefineByCommand(CMD_DELETE);
			ButtonDto delete = null;
			if(deleteBtnDef != null) {
				delete = buildButton(panelContext,deleteBtnDef);
			}else{
				delete = buildButton("",JDFICons.delete, CMD_DELETE,null);
			}
			delete.setVisible(setting.isAllowDelete());
			setButtonPrivilege(actionPriviegeMap, delete,widgetParam.getGroupVisibleOverride(),widgetParam.getGroupWritableOverride());
			buttonBar.addChildren(delete);
		}
		addImportExportButton(panelContext,buttonBar,setting,actionPriviegeMap);
		
		addHideColBtn(panelContext, buttonBar, setting, actionPriviegeMap, getI18nString(panelContext, GpfDCBasicI18n.HiddenColum));
		
		for(ButtonDefine buttonDef : widgetParam.getRootToolButtons()) {
			if(isSystemCommand(buttonDef.getName())) {
				continue;
			}
			String buttonLabel = getI18nString(panelContext, buttonDef.getLabel());
			if(buttonDef.isButtonGroup()) {
				ButtonDto button = buildButton(buttonLabel, buttonDef.getIconSrc(), CMD_CLICK_TOOL_BUTTON_GROUP, null);
				if(!CmnUtil.isStringEmpty(buttonDef.getStyle())) {
					button.setStyleName(buttonDef.getStyle());
				}
				button.setWidgetId(buttonDef.getName());
				button.setUnitName(WidgetLayoutUtil.UNIT_NAME_CUSTOM_BUTTON);
				button.setGroupIds(buttonDef.getTagGroupIdArray());
				setButtonPrivilege(actionPriviegeMap, button,widgetParam.getGroupVisibleOverride(),widgetParam.getGroupWritableOverride());
				buttonBar.addChild(button);
			}else {
				ButtonDto button = buildButton(buttonLabel, buttonDef.getIconSrc(), buttonDef.getName(), null);
				if(!CmnUtil.isStringEmpty(buttonDef.getStyle())) {
					button.setStyleName(buttonDef.getStyle());
				}
				setRowButtonListenerByListenerDefine(button, widgetParam.getListenerDefines());
				button.setUnitName(WidgetLayoutUtil.UNIT_NAME_CUSTOM_BUTTON);
				button.setGroupIds(buttonDef.getTagGroupIdArray());
				setButtonPrivilege(actionPriviegeMap, button,widgetParam.getGroupVisibleOverride(),widgetParam.getGroupWritableOverride());
				buttonBar.addChild(button);
			}
		}
		buttonBar.addChild(newSearchBar(panelContext,panelGlobalKey,setting));

		buttonBar.addExpander();
//		buttonBar.addChild(buildButton("", JDFICons.mt_advanced_search, CMD_SEARCH, widgetParam));
		BoxDto mainBox = new BoxDto().setVertical(true).setExpandInBox(false);
		mainBox.setMainAxisAlignment(MainAxisAlign.start).setCrossAxisAlignment(CrossAxisAlign.start);
		mainBox.addChild(buttonBar);
//		mainBox.setStyleName(FeStyleConst.common_table_top_bar_box);
		mainBox = addAddtionalWidget(panelContext, mainBox);
		return ContainerDto.wrap(mainBox);
	}
	
	public void addImportExportButton(PanelContext panelContext,BoxDto box,TableViewSetting setting,Map<String,ActionPrivilegeDto> actionPriviegeMap) throws Exception {
		if(setting.isAllowExport()) {
//			ButtonDto exportSelectedBtn = buildButton("", FeIcons.downloading, CMD_EXPORT_SELECTED, null).setToolTip(GpfDCFeI18n.ExportSelectedData);
//			setButtonPrivilege(actionPriviegeMap, exportSelectedBtn,widgetParam.getGroupVisibleOverride(),widgetParam.getGroupWritableOverride());
//			box.addChild(exportSelectedBtn);
			ButtonDto exportBtn = buildButton(getI18nString(panelContext,GpfDCBasicI18n.Export), JDFICons.export_data, CMD_EXPORT, null).setToolTip(GpfDCFeI18n.ExportData);
			setButtonPrivilege(actionPriviegeMap, exportBtn,widgetParam.getGroupVisibleOverride(),widgetParam.getGroupWritableOverride());
			box.addChild(exportBtn);
		}
		if(setting.isAllowImport()) {
			ButtonDto importBtn = buildButton(getI18nString(panelContext,GpfDCBasicI18n.Import), JDFICons.import_data, CMD_IMPORT, null).setToolTip(GpfDCFeI18n.Import);
			setButtonPrivilege(actionPriviegeMap, importBtn,widgetParam.getGroupVisibleOverride(),widgetParam.getGroupWritableOverride());
			box.addChild(importBtn);
		}
	}

//	public WidgetDto newSearchBar(PanelContext panelContext) throws Exception {
//		FilterDto filter = new FilterDto();
//		FormModel formModel = getCacheFormModel(panelContext);
//		
//		List<FormFieldDefine> fieldDefs = widgetParam.getSearchBar();
//		List<FormField> filterFields = new ArrayList<>();
//		Map<String,FormField> fieldMap = formModel.getFieldMap();
//		for(FormFieldDefine fieldDef : fieldDefs) {
//			FormField field = fieldMap.get(fieldDef.getCode());
//			if(field != null) {
//				filterFields.add(field);
//				break;
//			}
//		}
//		fieldDefs = GpfDCBasicUtil.mergeFormField(filterFields, fieldDefs);
//		filter.setFields(fieldDefs);
//		filter.setShowReset(false);
//		filter.setSummaryFilter(true);
//		filter.setShowAdvFilter(false);
//		FilterPanel filterPanel = new FilterPanel();
//		BaseDataViewParam<FilterDto> filterParam = new BaseDataViewParam<FilterDto>();
//		filterParam.setData(filter);
//		filterPanel.setWidgetParam(filterParam);
//		WidgetDto searchBar = filterPanel.getWidget(panelContext);
//		
//		ListenerDto lsnr = newListener(getBuilderService(), CMD_SEARCH, true, null);
//		CommandCallbackListener searchCallback = newCommandCallback(FilterPanel.CMD_SEARCH, "搜索", "", lsnr, false);
//		searchBar.addExtendListener(searchCallback);
//		return searchBar;
//	}
	
	
	@Override
	public List<ButtonDto> buildRowOperateButtons(PanelContext context, T widgetParam) throws Exception {
		boolean writable = widgetParam.isWritable();
		TableViewSetting setting = widgetParam.getSetting(TableViewSetting.class);
		List<ButtonDto> buttons = new ArrayList<>();
		if(writable && setting.isAllowDelete()) {
			ButtonDefine rowBtnDelDef = getRowOperateButtonDefineByCommand(ROW_DELETE_WIDGET_ID);
			ButtonDto delete = null;
			if(rowBtnDelDef != null) {
				delete = buildButton(context,rowBtnDelDef);
			}else {
				delete = new ButtonDto("", JDFICons.delete).setWidgetId(ROW_DELETE_WIDGET_ID);
			}
			delete
			.setOnClick(newListener(getService(), CMD_ROW_DELETE, true, null));
			delete.setStyleName(FeStyleConst.common_table_row_icon_btn);
			buttons.add(delete);
		}
		if(setting.isShowRowDetailBtn()) {
			buttons.add(buildRowDetailBtn());
		}
		for(ButtonDefine buttonDef : widgetParam.getRootRowOperateButtons()) {
			String buttonLabel = getI18nString(context, buttonDef.getLabel());
			if(buttonDef.isButtonGroup()) {
				ButtonDto rowBtn = buildButton(buttonLabel, buttonDef.getIconSrc(), CMD_CLICK_ROW_BUTTON_GROUP, null);
				if(!CmnUtil.isStringEmpty(buttonDef.getStyle())) {
					rowBtn.setStyleName(buttonDef.getStyle());
				}else {
					rowBtn.setStyleName(FeStyleConst.common_table_row_icon_btn);
					
				}
				rowBtn.setWidgetId(ROW_BUTTON_WIDGET_PREFIX+buttonDef.getName());
				buttons.add(rowBtn);
			}else {
				ButtonDto button = buildButton(buttonLabel, buttonDef.getIconSrc(), ROW_BUTTON_WIDGET_PREFIX+buttonDef.getName(), null);
				if(!CmnUtil.isStringEmpty(buttonDef.getStyle())) {
					button.setStyleName(buttonDef.getStyle());
				}else {
					button.setStyleName(FeStyleConst.common_table_row_icon_btn);
				}
				setRowButtonListenerByListenerDefine(button, widgetParam.getListenerDefines());
				buttons.add(button);
			}
		}
		return buttons;
	}
	
	@Override
	public void initPanelCache(PanelContext panelContext, String panelGlobalKey) throws Exception {
		initPanelCacheValue(panelContext, panelGlobalKey, CacheFormModel, null);
		super.initPanelCache(panelContext, panelGlobalKey);
	}

	@Override
	public WidgetDto doGetWidget(PanelContext panelContext) throws Exception {
		TableDto table = (TableDto) super.doGetWidget(panelContext);
		//嵌套模型补充表格行数据的收集
		if(!CmnUtil.isStringEmpty(widgetParam.getMasterClass())) {
			table.setValueAdapter(new TableValueAdapter_AllRows());
		}
		table.setFontSizeAutoFix(true);
		WidgetDto layout = widgetParam.getLayout();
		if(layout != null) {
			table = (TableDto) WidgetLayoutUtil.setRealWidget2Layout(table, layout,getI18n(panelContext),widgetParam.getModelId());
		}
//		table.addSubscribeEvent(new EventSubscriberDto(FeCmnEvent.class).setService(getBuilderService()).setCommand(CMD_REFRESH).setIdentifyFilter(table.getPanelGlobalKey()));
		table.setUnitName(WidgetLayoutUtil.UNIT_NAME_CUSTOM_TABLE);
		setListenerByListenerDefine(panelContext,table, widgetParam.getListenerDefines(),ListenerApplyLocation.Table);
		
		TableViewSetting setting = widgetParam.getSetting(TableViewSetting.class);
		table.setShowCheckbox(setting.isShowCheckBox());
		if(setting.getMaxEmbedHeight() != null) {
			table.setPreferHeight(setting.getMaxEmbedHeight());
		}
//		doAfterInited(panelContext, table.getPanelGlobalKey(), table);
		return table;
	}
	
	public boolean isHiddenField(PanelContext context,FormModel model,FormField formField) throws Exception {
		List<String> hiddenFields = model.getHiddenFields();
		return hiddenFields.contains(formField.getCode());
	}


	@Override
	public TableHeaderDto doQueryTableMeta(TableBuilder builder, TableQuerierContext context) throws Exception {
		FormModel model = getCacheFormModel(context,context.getCurrentPanelGlobalKey());
		FormFieldEditorFactory factory = getEditorFactory(context.getCurrentPanelGlobalKey(), widgetParam);
		List<FormField> fieldList = getCacheFormField(context);
		List<TableColumnDefine> columnDefs = widgetParam.getColumns();
		List<TableColumnDto> lstCols = new LinkedList<TableColumnDto>();
		if(CmnUtil.isCollectionEmpty(columnDefs)) {
			for(FormField bizField : fieldList) {
				DataType display = bizField.getDataTypeEnum();
				if(display == DataType.RichDocument || display == DataType.NestingModel 
						|| display == DataType.Password || display == DataType.KeyValue
						|| display == DataType.Image || display == DataType.Binary)
					continue;
				if(isHiddenField(context, model, bizField))
					continue;
				TableColumnDto fieldCol = new TableColumnDto();
				fieldCol.setName(bizField.getCode());
				fieldCol.setLabel(getI18nString(context, bizField.getName()));
				fieldCol.setEditor(new LabelDto());
				fieldCol.setMaxWidth(200);
				fieldCol.setButtons(buildColButton(context, bizField));
				lstCols.add(fieldCol);
			}
		}else{
			Map<String,FormField> fieldMap = fieldList.stream().collect(Collectors.toMap(FormField::getCode, v->v,(e,r)->e));
			for(TableColumnDefine columDef : widgetParam.getColumns()) {
				FormField formField = fieldMap.get(columDef.getCode());
				if(formField != null) {
					DataType display = formField.getDataTypeEnum();
					if(display == DataType.RichDocument || display == DataType.NestingModel 
							|| display == DataType.Password || display == DataType.KeyValue)
						continue;
					if(isHiddenField(context, model, formField))
						continue;
					TableColumnDto fieldCol = buildLabelColumn(context,columDef, formField,factory);
					lstCols.add(fieldCol);
				}
			}
		}
		TableHeaderDto meta = new TableHeaderDto();
		meta.setColumns(lstCols);

		return meta;
	}
	
	@Override
	public Map<String, String> getCteQuerySqls(PanelContext panelContext,String fieldCode) throws Exception {
		FormModel model = getCacheFormModel(panelContext, panelContext.getCurrentPanelGlobalKey());
		Map<String, String> cteSqls = new LinkedHashMap<>();
		cteSqls.put("total", "select * from " + model.getTableName());
		return cteSqls;
	}
	
	@Override
	public String getMainTableAlias(PanelContext panelContext,String fieldCode) throws Exception {
		return "total";
	}
//	public final static String CacheKey_OrderByOption = "$OrderByOption";
//	public final static String CacheKey_AdvFilter = "$advFilter"; 
//	public final static String CacheKey_FilterKeyword = "$filterKeyword";
//	public OrderByOptionDto getOrderByOption(PanelContext panelContext) {
//		IMapCell map = panelContext.getOrCreatePanelCache();
//		OrderByOptionDto orderByOption = (OrderByOptionDto) map.getValue(CacheKey_OrderByOption);
//		if(orderByOption == null) {
//			orderByOption = new OrderByOptionDto();
//			map.putValue(CacheKey_OrderByOption, orderByOption);
//		}
//		return orderByOption;
//	}
//	
//	public String getFilterKeyword(PanelContext panelContext) {
//		return (String) panelContext.getPanelCacheValue(CacheKey_FilterKeyword);
//	}
//	public void setFilterKeyword(PanelContext panelContext,String filterKeyword) throws Exception {
//		if(!CmnUtil.isStringEmpty(filterKeyword)) {
//			panelContext.putPanelCache(CacheKey_FilterKeyword, filterKeyword);
//		}else {
//			panelContext.removeCacheValue(CacheKey_FilterKeyword);
//		}
//	}
//	
//	public SqlExpressionGroup getAdvFilter(PanelContext panelContext) {
//		IMapCell map = panelContext.getOrCreatePanelCache();
//		SqlExpressionGroup advFilter = (SqlExpressionGroup) map.getValue(CacheKey_AdvFilter);
//		return advFilter;
//	}
//	
//	public void setAdvFilter(PanelContext panelContext,SqlExpressionGroup advFilter) {
//		IMapCell map = panelContext.getOrCreatePanelCache();
//		map.putValue(CacheKey_AdvFilter,advFilter);
//	}
	
	@Override
	public TableRowsDto doQueryTableRows(TableBuilder builder, TableQuerier querier, TableQuerierContext context) throws Exception {
		String formModelID = widgetParam.getModelId();
		FormModel model = getCacheFormModel(context,context.getCurrentPanelGlobalKey());
		TableViewSetting setting = widgetParam.getSetting(TableViewSetting.class);
		int pageNo = querier.getStartPos() + 1;///querier.getPageSize() + 1;
		OrderByOptionDto orderByOption = getOrderByOption(context);
		try(IDao dao = IDaoService.get().newDao()){
			Cnd cnd = Cnd.NEW();
			if(CmnUtil.isStringEmpty(orderByOption.getColumn())) {
				if(!CmnUtil.isStringEmpty(widgetParam.getDefaultOrder())) {
					buildDefaultOrder(getCacheFormField(context), cnd, widgetParam.getDefaultOrder());
				}
			}else {
				if(orderByOption.isAsc()) {
					cnd.asc(orderByOption.getColumn());
				}else {
					cnd.descNullsLast(orderByOption.getColumn());
				}
			}
			cnd = buildCondition(context, dao, cnd);
			Tracer tracer = TraceUtil.getCurrentTracer();
			tracer.info("开始查询数据");
			tracer.logStart();
			ResultSet<Form> rs = doQueryDataPage(context,widgetParam.getCustomFormAction(),formModelID, cnd, pageNo, querier.getPageSize(),null,widgetParam.getDataPrivilegeFuncs(),widgetParam.getCustomQueryFuncs());
			tracer.infoCost("", "数据查询结束");
			tracer.info("预加载关联数据缓存");
			tracer.logStart();
			preloadAssocDatas(dao, getEditorFactory(context.getCurrentPanelGlobalKey(), widgetParam), context, (List)rs.getDataList());
			tracer.infoCost("", "关联数据缓存预加载结束");
			TableRowsDto rows = new TableRowsDto();
			List<TableRowDto> listRow = new LinkedList<TableRowDto>();
			if(rs.getSize() > 0 ) {
				//查询行动作权限
				tracer.info("计算行操作权限");
				tracer.logStart();
				Map<String,Map<String,ActionPrivilegeDto>> allRowPrivs = getRowActionPrivilege(context, rs.getDataList());
				tracer.infoCost("", "行操作权限计算结束");
				
				for(Form cdo : rs.getDataList()) {
					TableRowDto row = convert2TableRowDto(cdo);
					listRow.add(row);
				}
			}
			rows.setRows(listRow);
			rows.setPageStart(querier.getStartPos());
			if(setting.isPaginationable()) {
				rows.setTotalRows(rs.getTotalCount());
			}
			tracer.info("", "返回表格行数据");
			rows = doAfterQueryTableRows(context, rows);
			return rows;
		}
	}
	
	@Override
	public Cnd buildCondition(PanelContext context, IDao dao, Cnd cnd) throws Exception {
		TableViewSetting setting = widgetParam.getSetting(TableViewSetting.class);
		String formModelID = widgetParam.getModelId();
		//独立事务的嵌套模型视图，要补充父表单的关联查询条件
		if(!CmnUtil.isStringEmpty(widgetParam.getMasterKey())) {
			cnd.and(new SqlExpressionGroup().andEquals(TableData.MasterClass, widgetParam.getMasterClass())
					.andEquals(TableData.MasterField, widgetParam.getMasterField())
					.andEquals(TableData.MasterKey, widgetParam.getMasterKey()));
		}
		FormModel model = getCacheFormModel(context,context.getCurrentPanelGlobalKey());
		List<TableColumnDefine> columnDefs = widgetParam.getColumns();
		List<FormFieldDefine> formFieldDefines = GpfDCBasicUtil.mergeFormFieldByTableColumnDefines(model.getFieldList(), columnDefs == null ? new ArrayList<>():columnDefs);
		Map<String,FormFieldDefine>  formFieldDefineMap = formFieldDefines.stream().collect(Collectors.toMap(FormFieldDefine::getCode, v->v,(e,r)->e));
		String filtersKeyWord = getFilterKeyword(context);
		SqlExpressionGroup advFilter = getAdvFilter(context);
		SqlExpressionGroup defaultFilter = widgetParam.getDefaultFilter();
		if(defaultFilter != null) {
			cnd.and(defaultFilter);
		}
		if(!CmnUtil.isStringEmpty(filtersKeyWord)) {
			List<String> summaryFilterCols = setting.getSummaryFilterColList();
			List<FormField> queryFields = new ArrayList<>();
			if(!CmnUtil.isCollectionEmpty(summaryFilterCols)) {
				for(FormField field : model.getFieldList()) {
					if(summaryFilterCols.contains(field.getName()))
						queryFields.add(field);
				}
			}else {
				List<String> hiddendFields = model.getHiddenFields();
				for(FormField field : model.getFieldList()) {
					if(hiddendFields.contains(field.getCode()))
						continue;
					queryFields.add(field);
				}
			}
			SqlExpressionGroup exprGrp = IFormMgr.get().buildMatchQueryOfFields(queryFields,filtersKeyWord);
			cnd.and(exprGrp);
		}
		if(advFilter != null) {
			cnd.and(advFilter);
		}
		Map<String,List<String>> filterColumnMap = (Map<String, List<String>>) getPanelCacheValue(context, CacheKey_FilterColumns);
		if(!CmnUtil.isMapEmpty(filterColumnMap)) {
			for(String columnName : filterColumnMap.keySet()) {
//				String columnKey = getFilterFieldMapping().get(columnName);
				List<String> inList = filterColumnMap.get(columnName);
				if(CmnUtil.isCollectionEmpty(inList))
					continue;
				SqlExpressionGroup group = new SqlExpressionGroup();
//				if(CmnUtil.isStringEmpty(columnKey)) {
				FormFieldDefine fieldDef = formFieldDefineMap.get(columnName);
				if(fieldDef != null && fieldDef.getDataTypeEnum() == DataType.Relate && fieldDef.isAssocMultiSelect()) {
					SqlExpressionGroup orExpr = new SqlExpressionGroup();
					for(String value : inList)
						orExpr.orLike(columnName, "[-]"+value+"[-]");
					group.and(orExpr);
				}else {
					group.andInStrList(columnName, inList);
				}
//				}else {
//					group.andInStrList(columnKey, inList);
//				}
				cnd.and(group);
			}
		}
//		SqlExpressionGroup privSqlExpr = getDataPrivilegeSqlExpression(context,dao);
//		if(privSqlExpr != null) {
//			cnd.and(privSqlExpr);
//		}
		getDataFilterSqlExpression(context, dao,cnd);
		return cnd;
	}
	
//	public <T extends Form> ResultSet<T> doQueryDataPage(PanelContext context,String formModelID,Cnd cnd,int pageNo,int pageSize) throws Exception{
//		return doQueryDataPage(context, formModelID, cnd, pageNo, pageSize, widgetParam.getDataPrivilegeFuncs(),widgetParam.getCustomQueryFuncs());
//	}
	
	public FormFieldEditorFactory getEditorFactory(String panelGobalKey,WidgetParam widgetParam) {
		FormFieldEditorFactory factory = FormFieldEditorFactory.create(panelGobalKey,this,isLazyQueryCompoundField());
//		factory.setLayoutMode(isLayoutMode());
		return factory;
	}

	protected final static String CacheFormModel = "$formModel$";
	protected FormModel getCacheFormModel(PanelContext panelContext,String panelGlobalKey) throws Exception {
		FormModel formModel = (FormModel) getPanelCacheValue(panelContext, CacheFormModel);
		if(formModel == null) {
			String formModelID = widgetParam.getModelId();
			if(CmnUtil.isStringEmpty(formModelID))
				throw new VerifyException(GpfDCBasicI18n.TIPS_MODEL_ID_IS_NULL);
			formModel = IAppGlobalSettingPlugin.get().queryCacheFormModel(formModelID);
			if(formModel == null)
				throw new VerifyException(GpfDCBasicI18n.getString(GpfDCBasicI18n.TIPS_MODEL_IS_NOT_EXIST, formModelID));
			if(formModel != null)
				setPanelCacheValue(panelContext, CacheFormModel, formModel);
		}
		return formModel;
	}
	
	@Override
	public List<FormField> doQueryModelFormField(PanelContext panelContext) throws Exception {
		FormModel model = getCacheFormModel(panelContext,panelContext.getCurrentPanelGlobalKey());
		List<FormField> formFields = model.getFieldList();
		if(CmnUtil.isCollectionEmpty(widgetParam.getColumns())) {
			return formFields;
		}else {
			List<FormField> fields = new ArrayList<>();
			for(TableColumnDefine colDef : widgetParam.getColumns()) {
				if(CmnUtil.isStringEmpty(colDef.getName()))
					continue;
				FormField newfield=null;
				//自定义列
				if(colDef.isCustom()){
					newfield=new FormField(IFormMgr.get().getFieldCode(colDef.getName()));
					newfield.setName(colDef.getName());
					newfield.setDataType(colDef.getExtFieldDataTypeEnum());
					if (colDef.getExtendInfo() != null) {
						newfield.setExtendInfo(colDef.getExtendInfo());
					}
				}
				else {
					for (FormField formField : formFields) {
						if (CmnUtil.isStringEqual(colDef.getCode(), formField.getCode())) {
							newfield = ToolUtilities.clone(formField);
							if (colDef.getExtendInfo() != null) {
								newfield.setExtendInfo(colDef.getExtendInfo());
							}
						}
					}
				}
				if(newfield != null)
					fields.add(newfield);
			}
			return fields;
		}
//		for(FormField formField : formFields){
//			FormField field = ToolUtilities.clone(formField);
//			for(TableColumnDefine colDef : widgetParam.getColumns()) {
//				if(CmnUtil.isStringEqual(colDef.getCode(), field.getCode())) {
//					if(colDef.getExtendInfo() != null) {
//						field.setExtendInfo(colDef.getExtendInfo());
//					}
//				}
//			}
//			fields.add(field);
//		}
	}
	

	@Override
	public TableRowDto convert2TableRowDto(Object data) throws Exception {
		Form form = (Form) data;
		if(CmnUtil.isStringEmpty(form.getUuid()))
			throw new VerifyException(getI18nString(getPanelContext(),GpfDCBasicI18n.CAN_NOT_BE_NULL,"uuid"));
		TableViewSetting setting = widgetParam.getSetting(TableViewSetting.class);
		TableRowDtoInterceptor interceptor = setting.getTableRowDtoInterceptorInst();
		IDCRuntimeContext rtx = null;
		if(interceptor != null) {
			rtx = getOrPrepareRtx(getPanelContext(), getPanelContext().getCurrentPanelGlobalKey());
		}
		doBeforeConvertTableRowDto(interceptor, rtx, form);
		TableRowDto row = new TableRowDto();//convert2TableRow(bizField);
		row.setRowId(form.getUuid());
		FormModel model = getCacheFormModel(getPanelContext(),getPanelContext().getCurrentPanelGlobalKey());
		List<String> hiddenFields = new ArrayList<>();
		List<FormField> fields = getCacheFormField(getPanelContext());
		if(CmnUtil.isCollectionEmpty(widgetParam.getColumns())) {
			for(FormField field : fields) {
				if(hiddenFields.contains(field.getCode()))
					continue;
				Object value = form.getAttrValueByCode(field.getCode());
				row.putValue(field.getCode(), getEditorFactory(getPanelContext().getCurrentPanelGlobalKey(),widgetParam).buildLabelTableCell(getPanelContext(),form,field, value));
			}
		}else {
			Map<String,FormField> fieldMap = fields.stream().collect(Collectors.toMap(FormField::getCode, v->v,(e,r)->e));
			for(TableColumnDefine columDef : widgetParam.getColumns()) {
				FormField formField = fieldMap.get(columDef.getCode());
				if(formField != null) {
					DataType display = formField.getDataTypeEnum();
					if(display == DataType.RichDocument || display == DataType.NestingModel 
							|| display == DataType.Password || display == DataType.KeyValue)
						continue;
					if(isHiddenField(getPanelContext(), model, formField))
						continue;
					Object value = form.getAttrValueByCode(formField.getCode());
					TableCellEditorType editorType = columDef.getTableCellEditorTypeEnum();
					if(editorType == TableCellEditorType.ReadOnlyEditor) {
						try {
							row.putValue(formField.getCode(), getEditorFactory(getPanelContext().getCurrentPanelGlobalKey(),widgetParam).buildEditorTableCell(getPanelContext(),form,formField, value));
						}catch (Exception e) {
							PopToast.warning(getPanelContext().getChannel(), "构建单元格["+columDef.getName()+"]值出错：",ToolUtilities.getFullExceptionStack(e));
							row.putValue(formField.getCode(), getEditorFactory(getPanelContext().getCurrentPanelGlobalKey(),widgetParam).buildLabelTableCell(getPanelContext(),form,formField, value));
						}
					}else {
						row.putValue(formField.getCode(), getEditorFactory(getPanelContext().getCurrentPanelGlobalKey(),widgetParam).buildLabelTableCell(getPanelContext(),form,formField, value));
					}
				}
			}
		}
		
		//设置行操作按钮是否可见
		Map<String,Map<String,ActionPrivilegeDto>> allRowPrivs = getRowActionPrivielge(getPanelContext());
		if(allRowPrivs != null) {
			Map<String,ActionPrivilegeDto> singleRowPriv = allRowPrivs.get(row.getRowId());
			if(singleRowPriv != null) {
				Map<String,Boolean> operateColumnButtonsVisible = new LinkedHashMap<>();
				for(ActionPrivilegeDto actionPriv : singleRowPriv.values()) {
					operateColumnButtonsVisible.put(ROW_BUTTON_WIDGET_PREFIX+actionPriv.getName(), actionPriv.isVisible());
				}
				row.setOperateColumnButtonsVisible(operateColumnButtonsVisible);
			}else if(widgetParam.getGroupVisibleOverride() != null) {
				Map<String,Boolean> operateColumnButtonsVisible = new LinkedHashMap<>();
				for(ButtonDefine rowBtn : widgetParam.getRootRowOperateButtons()) {
					for(String tagGroup : rowBtn.getTagGroupList()) {
						if(widgetParam.getGroupVisibleOverride().containsKey(tagGroup)) {
							operateColumnButtonsVisible.put(ROW_BUTTON_WIDGET_PREFIX+rowBtn.getName(), widgetParam.getGroupVisibleOverride().containsKey(tagGroup));
						}
					}
				}
				row.setOperateColumnButtonsVisible(operateColumnButtonsVisible);
			}
		}else {
			
			//如果未设置行按钮操作权限，默认行操作按钮都可见
			Map<String,Boolean> operateColumnButtonsVisible = new LinkedHashMap<>();
			for(ButtonDefine buttonDef : widgetParam.getRowOperateBar()) {
				operateColumnButtonsVisible.put(ROW_BUTTON_WIDGET_PREFIX+buttonDef.getName(), true);
			}
			if(widgetParam.getGroupVisibleOverride() != null) {
				for(ButtonDefine rowBtn : widgetParam.getRootRowOperateButtons()) {
					for(String tagGroup : rowBtn.getTagGroupList()) {
						if(widgetParam.getGroupVisibleOverride().containsKey(tagGroup)) {
							operateColumnButtonsVisible.put(ROW_BUTTON_WIDGET_PREFIX+rowBtn.getName(), widgetParam.getGroupVisibleOverride().containsKey(tagGroup));
						}
					}
				}
			}
			row.setOperateColumnButtonsVisible(operateColumnButtonsVisible);
		}
		row.setBinaryData(form);
		row = doAfterConvertTableRowDto(interceptor, rtx, row);
		return row;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object onListener(ListenerDto listener, PanelContext panelContext, WidgetDto source) throws Exception {
//		if(listener instanceof TableRowListener) {
//			onRowClick((TableRowListener)listener, panelContext, source);
//		}else {
//			String cmd = listener.getServiceCommand();
			if(listener.isServiceCommand(CMD_ADD_OR_UPDATE_ROW)) {
				FeDeliverData<Form> feData = (FeDeliverData<Form>) listener.getBinaryData();
				Form data = feData.getData();
				addOrUpdateRow(panelContext, data);
				QuitPopup.quit(panelContext);
			}else if(listener.isServiceCommand(CMD_EXPORT)) {
				onBtnExport(listener, panelContext, source);
			}else if(listener.isServiceCommand(CMD_EXPORT_SELECTED)) {
				onBtnExportSelected(listener, panelContext, source);
			}else if(listener.isServiceCommand(CMD_IMPORT)) {
				onBtnImport(listener, panelContext, source);
			}else {
				return super.onListener(listener, panelContext, source);
			}
//			else if(listener.isServiceCommand(CMD_QUIT_POPUP)){
//				QuitPopup.quit(panelContext);
//			}else if(CmnUtil.isStringEqual(CMD_CREATE, cmd)) {
//				onBtnAdd(listener,panelContext, source);
//			}else if(CmnUtil.isStringEqual(CMD_DELETE, cmd)) {
//				onBtnDelete(listener,panelContext, source);
//			}else if(cmd.startsWith(CMD_ORDER_BY_ASC)) {
//				rebuildTableByOrderColumn(listener, panelContext, source, true);
//			}else if(cmd.startsWith(CMD_ORDER_BY_DESC)) {
//				rebuildTableByOrderColumn(listener, panelContext, source, false);
//			}else if(CmnUtil.isStringEqual(CMD_SEARCH, cmd)) {
//				FeDeliverData<FilterDto> feData = (FeDeliverData<FilterDto>) listener.getBinaryData();
//				search(panelContext, feData.getData());
//			}else {
		
//		}
		return null;
	}
	

	@Override
	public Object getEditObject(TableRowDto row) throws ClassNotFoundException, IOException {
		Form data = (Form) row.getBinaryData();
		try(IDao dao = IDaoService.get().newDao()){
			TableViewSetting setting = widgetParam.getSetting(TableViewSetting.class);
			if(setting.isReGetDataOnRowClick()) {
				if(widgetParam.getCustomFormAction() != null) {
					Form form = widgetParam.getCustomFormAction().query(dao, data.getFormModelId(), data.getUuid());
					form.setExtFields(data.getExtFields());
					data = form;
				}else {
					//补充懒查询嵌套模型数据
					Form form = IFormMgr.get().queryForm(dao,data.getFormModelId(), data.getUuid(),!isLazyQueryCompoundField());
					form.setExtFields(data.getExtFields());
					data = form;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	@Override
	public String getEditRowPanelTitle() {
		String title = " ";
		try {
			FormModel model = getCacheFormModel(getPanelContext(),getPanelContext().getCurrentPanelGlobalKey());
			title = model.getLabel();
			I18nIntf appI18n = getI18n(getPanelContext());
			if(appI18n != null)
				title = appI18n.formatInGroup(title, widgetParam.getModelId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return title;
	}

	@Override
	public FormEditPanelIntf buildEditRowPanel(ListenerDto listener, PanelContext panelContext, WidgetDto source,
			Object rowData, boolean isWriteable) throws Exception {
		Form cdo = (Form) rowData;
		BaseFormEditView editPanel = null;
		if(editPanel == null) {
			editPanel = new BaseFormEditView<>();
			BaseDataViewParam<Form> editParam = new BaseDataViewParam<Form>();
			editParam.setModelId(cdo.getFormModelId());
			editParam.setData(cdo);
			editParam.setWritable(isWriteable);
			editParam.setSetting(AppCacheUtil.newFormSetting(panelContext));
			CommandCallbackListener confirmCallbacklsnr = newPopupPanelCallbackListener(getService(), BaseFormEditView.CMD_CONFIRM, CMD_ADD_OR_UPDATE_ROW, null);
			CommandCallbackListener cancelCallbackLsnr = newPopupPanelCallbackListener(getService(), BaseFormEditView.CMD_CANCEL, CMD_QUIT_POPUP, null);
			CommandCallbackListener deleteCallbackLsnr = newPopupPanelCallbackListener(getService(), BaseFormEditView.CMD_DELETE, CMD_DELETE_CALLBACK, null);
			editParam.setCommandCallbackLsnrs(Arrays.asList(confirmCallbacklsnr,cancelCallbackLsnr,deleteCallbackLsnr));
			editPanel.setWidgetParamWithContext(editParam,widgetParam);
		}
		return editPanel;
	}

	@Override
	public TableRowDto doCreateRowData(PanelContext panelContext, Object rowData) throws Exception {
		Form data = (Form)rowData;
		new FormVisitor().visit(data);
		try(IDao dao = IDaoService.get().newDao()){
			FormOpObserver observer = AppCacheUtil.getFormOpObserver(panelContext);
			if(widgetParam.getCustomFormAction() != null) {
				Form form = widgetParam.getCustomFormAction().create(dao, data, observer);
				data = form;
			}else {
				Form form = IFormMgr.get().createForm(null,dao,data,observer);
				data = form;
			}
			dao.commit();
			return convert2TableRowDto(data);
		}
	}

	@Override
	public TableRowDto doUpdateRowData(PanelContext panelContext, Object rowData) throws Exception {
		Form data = (Form)rowData;
		new FormVisitor().visit(data);
		try(IDao dao = IDaoService.get().newDao()){
			FormOpObserver observer = AppCacheUtil.getFormOpObserver(panelContext);
			if(widgetParam.getCustomFormAction() != null) {
				Form form = widgetParam.getCustomFormAction().update(dao, data, observer);
				form.setExtFields(data.getExtFields());
				data = form;
			}else {
				Form form = IFormMgr.get().updateForm(null,dao,data,observer);
				form.setExtFields(data.getExtFields());
				data = form;
			}
			dao.commit();
			return convert2TableRowDto(data);
		}
	}

	@Override
	public Object newRowObject(ListenerDto listener, PanelContext panelContext, WidgetDto source) throws Exception {
		Form form = null;
		if(widgetParam.getCustomFormAction() != null) {
			form = widgetParam.getCustomFormAction().newForm(widgetParam.getModelId());
		}else {
			form = new Form();
			form.setFormModelId(widgetParam.getModelId());
		}
		//嵌套模型的新增要补充父表单信息
		if(!CmnUtil.isStringEmpty(widgetParam.getMasterKey())) {
			try(IDao dao = IDaoService.newIDao()){
				if(!IFormMgr.get().isFormExist(dao, widgetParam.getMasterClass(), widgetParam.getMasterKey())) {
					throw new VerifyException("父表单未保存，请先保存父表单！");
				}
			}
			form.setAttrValueByCode(TableData.MasterClass, widgetParam.getMasterClass());
			form.setAttrValueByCode(TableData.MasterKey, widgetParam.getMasterKey());
			form.setAttrValueByCode(TableData.MasterField, widgetParam.getMasterField());
		}
		return form;
	}

	@Override
	public void doDeleteRowData(PanelContext panelContext, List<String> rowIds) throws Exception {
		try(IDao dao = IDaoService.get().newDao()){
			List<TableRowDto> rows = queryRows(panelContext, rowIds);
			for(TableRowDto row : rows) {
				FormOpObserver observer = AppCacheUtil.getFormOpObserver(panelContext);
				if(widgetParam.getCustomFormAction() != null) {
					widgetParam.getCustomFormAction().delete(dao, widgetParam.getModelId(), row.getRowId(), observer);
				}else {
					IFormMgr.get().deleteForm(null,dao,widgetParam.getModelId(), row.getRowId(),observer);
				}
			}
			dao.commit();
		}
	}

	@Override
	public TableQuerier doRebuildTableQuerier(ListenerDto listener, PanelContext panelContext, WidgetDto source)
			throws Exception {
		String filtersKeyWord = SearchBar.getFiltersKeyWord(panelContext);
		T param = widgetParam;
		param.setFiltersKeyWord(filtersKeyWord);
		Integer pageSize = param.getPageSize();
		if(panelContext instanceof TableContext) {
			Integer contextPageSize = ((TableContext) panelContext).getPageSize();
			pageSize = contextPageSize == null ? param.getPageSize() : contextPageSize;
		}
		FeDeliverData<T> querierData = new FeDeliverData<T>(getClass(), param);
		TableQuerier tableQuerier = new TableQuerier();
		tableQuerier.setStartPos(param.getStartPos()).setPageSize(pageSize).setQueryCount(param.isQueryCount());
		tableQuerier.setBinaryData(querierData);
		return tableQuerier;
	}

	@Override
	public void onBtnRefresh(ListenerDto listener, PanelContext panelContext, WidgetDto source) throws Exception {
		panelContext.removePanelCacheValue(CacheFormModel);
//		setWidgetParam(widgetParam);
//		TableDto newWidget = (TableDto) getWidget(panelContext);
//		newWidget.setPanelGlobalKey(panelContext.getCurrentPanelGlobalKey());
//		newWidget.setWidgetId(panelContext.getCurrentPanelWidgetId());
//		RebuildChild.rebuild(panelContext, newWidget);
		super.onBtnRefresh(listener, panelContext, source);
	}
	@Override
	public void addOrUpdateRow(PanelContext panelContext,Object data) throws Exception {
		Form form = (Form) data;
		TableRowDto existRow = queryRow(panelContext, form.getUuid());
		boolean isExist = false;
		try(IDao dao = IDaoService.newIDao()){
			isExist = IFormMgr.get().isFormExist(dao, form.getFormModelId(), form.getUuid());
		}
		TableRowDto tableRow = null;
		if(!isExist) {
			tableRow = doCreateRowData(panelContext, data);
		}else {
			tableRow = doUpdateRowData(panelContext, data);
		}
		if(existRow == null) {
//			InsertRowsByIndex.execute(panelContext, 0, tableRow);
			addRows(panelContext, tableRow);
		}else {
			updateRows(panelContext, tableRow);
		}
		PopToast.success(panelContext.getChannel(), getI18nString(panelContext,GpfDCFeI18n.UPDATE_SUCCESS));
		fireCommandListener(panelContext, panelContext.getCurrentPanelWidgetId(), CMD_VALUE_CHANGED, tableRow);
	}
	
	public void onBtnExportSelected(ListenerDto listener, PanelContext panelContext, WidgetDto source) throws Exception {
		List<TableRowDto> rows = querySelected(panelContext);
		if(CmnUtil.isCollectionEmpty(rows)) {
			PopToast.info(panelContext.getChannel(),getI18nString(panelContext,GpfDCBasicI18n.TIPS_SELECT_EXPORT_ROW));
			return;
		}
		List<String> uuids = new ArrayList<>();
		for(TableRowDto row : rows) {
			uuids.add(row.getRowId());
		}
		Cnd cnd = Cnd.NEW();
		cnd.where().andInStrList("uuid", uuids);
		exportByCnd(panelContext, cnd);
	}

	public void onBtnExport(ListenerDto listener, PanelContext panelContext, WidgetDto source) throws Exception {
		List<TableRowDto> rows = querySelected(panelContext);
		Cnd cnd = Cnd.NEW();
		if(!CmnUtil.isCollectionEmpty(rows)) {
			List<String> uuids = new ArrayList<>();
			for(TableRowDto row : rows) {
				uuids.add(row.getRowId());
			}
			cnd.where().andInStrList("uuid", uuids);
		}else {
			String filtersKeyWord = widgetParam.getFiltersKeyWord();
			OrderByOptionDto orderOption = getOrderByOption(panelContext);
			if(orderOption != null) {
				if(!CmnUtil.isStringEmpty(orderOption.getColumn())) {
					if(orderOption.isAsc())
						cnd.asc(orderOption.getColumn());
					else
						cnd.desc(orderOption.getColumn());
				}
			}
			FormModel model = getCacheFormModel(panelContext,panelContext.getCurrentPanelGlobalKey());
			if(!CmnUtil.isStringEmpty(filtersKeyWord)) {
				List<FormField> queryFields = new ArrayList<>();
				List<String> hiddendFields = model.getHiddenFields();
				for(FormField field : model.getFieldList()) {
					if(hiddendFields.contains(field.getCode()))
						continue;
					queryFields.add(field);
				}
				SqlExpressionGroup exprGrp = IFormMgr.get().buildMatchQueryOfFields(queryFields,filtersKeyWord);
				cnd.and(exprGrp);
			}
		}
		cnd = apppendAppDefaultFilter(panelContext, widgetParam.getModelId(), cnd);
		exportByCnd(panelContext, cnd);
	}
	
	public void exportByCnd(PanelContext panelContext,Cnd cnd) throws Exception {
		String formModelId = widgetParam.getModelId();
		FormModel formModel = IFormMgr.get().queryFormModel(formModelId);
		CFeProgressCtrlWithTextArea prog = ProgressDialog.showProgressDialog(panelContext, getI18nString(panelContext,GpfDCBasicI18n.ExportData)+"...",true,true);
		try {
			Progress prog2 = Progress.wrap(prog);
			FormDataExcelExpImp expImp = new FormDataExcelExpImp();
			expImp.setMatchWriteUuidModel(v->CmnUtil.isStringEqual(v, GpfDCBasicConst.ApplicationMenuTreeModelId));
			Pair<String, byte[]> pair = null;
			if(widgetParam.getCustomFormAction() != null) {
				FormOpObserver opObserver = AppCacheUtil.getFormOpObserver(panelContext);
				pair = widgetParam.getCustomFormAction().exportData(prog2, formModelId, cnd,opObserver);
				if(pair == null)
					return;
				BeFile file = new BeFile();
				file.setBytes(pair.right);
				file.setName(pair.left);
				file.setStorPath("./uploadTemp/"+panelContext.getCurrentUser()+"/"+file.getName());
				file.setLength(pair.right.length);
				try {
					IFiles.get().saveFile(file.getStorPath(), file.getBytes(), false);
					DownloadFile.start(panelContext, IFileService.class, file.getStorPath(), pair.left);
				}finally {
					IFiles.get().deleteFile(file.getStorPath());
				}
			}else {
				pair = IBackupService.get().exportFormToExcel(prog2, null, formModelId, cnd);
				BeFile file = new BeFile();
				file.setBytes(pair.right);
				file.setName(pair.left);
				file.setStorPath("./uploadTemp/"+panelContext.getCurrentUser()+"/"+file.getName());
				file.setLength(pair.right.length);
				try {
					IFiles.get().saveFile(file.getStorPath(), file.getBytes(), false);
					DownloadFile.start(panelContext, IFileService.class, file.getStorPath(), getI18nString(panelContext,GpfDCBasicI18n.FormData)+"-"+formModel.getLabel()+".zip");
				}finally {
					IFiles.get().deleteFile(file.getStorPath());
				}
			}
			prog2.finish();
//			Pair<String, byte[]> pair = IFormMgr.get().exportFormData(Progress.wrap(prog), null, formModelId, cnd);
		}catch (Exception e) {
			prog.finishError(ToolUtilities.getFullExceptionStack(e));
		}
	}
	
	public void onBtnImport(ListenerDto listener, PanelContext panelContext, WidgetDto source) throws Exception {
		
		String formModelId = widgetParam.getModelId();
		UploadFileResult result = null;
		UploadFile upload = new UploadFile().setTempFolder("temp/Upload").setTempFilePrefix("TempUpload");
		upload.setFileType(PickFileType.custom);
		TableViewSetting setting = widgetParam.getSetting(TableViewSetting.class);
		List<String> allowImportFileTypes = setting.getAllowImportFileTypeList();
		if(allowImportFileTypes == null)
			upload.setExtFilter("zip");
		else
			upload.setExtFilter(allowImportFileTypes);
		upload.setAllowMultiple(setting.isAllowMultipleImport());
		result = upload.action(panelContext);
        if (result == null)
            return;
		CFeProgressCtrlWithTextArea prog = ProgressDialog.showProgressDialog(panelContext, getI18nString(panelContext,GpfDCBasicI18n.ImportData)+"...",true,true);
		try {
			Progress prog2 = Progress.wrap(prog);
			int i = 0;
			for(BeFile file : result.getLstFiles()) {
//				BeFile file = result.getLstFiles().get(0);
				Progress childProg = prog2.newChildProgress(i, i+1, result.getLstFiles().size());
		        ByteArrayDto byteArray = IFileService.get().getResource(file.getStorPath());
				Pair<String,byte[]> pair = new Pair<>(file.getName(),byteArray.getData());
				childProg.setMessage(getI18nString(panelContext, GpfDCBasicI18n.ImportData)+"["+pair.getKey()+"]...", true);
				FormOpObserver observer = AppCacheUtil.getFormOpObserver(panelContext);
				if(widgetParam.getCustomFormAction() != null) {
					widgetParam.getCustomFormAction().importData(childProg, formModelId, pair,observer);
				}else {
					IBackupService.get().importFormFormExcel(childProg, null, formModelId, pair,observer);
				}
				i++;
			}
			prog2.finish();
//			IFormMgr.get().importFormData(Progress.wrap(prog), null, formModelId, pair);
		}catch (Exception e) {
			prog.finishError(ToolUtilities.getFullExceptionStack(e));
		}
	}
}