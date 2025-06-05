package gpf.dc.basic.fe.component.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.nutz.dao.Cnd;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.kwaidoo.ms.tool.CmnUtil;
import com.kwaidoo.ms.tool.ToolUtilities;
import com.leavay.common.util.Pair;

import cell.cdao.IDao;
import cell.cdao.IDaoService;
import cell.fe.gpf.dc.basic.IGpfDCBasicFeService;
import cell.gpf.adur.action.IActionMgr;
import cell.gpf.adur.data.IFormMgr;
import cell.gpf.dc.runtime.IDCRuntimeContext;
import cell.gpf.dc.runtime.IPDFRuntimeMgr;
import cmn.anotation.ClassDeclare;
import cmn.anotation.FieldDeclare;
import cmn.util.TraceUtil;
import cmn.util.Tracer;
import fe.cmn.app.ability.PopToast;
import fe.cmn.data.NullPojo;
import fe.cmn.event.EventSubscriberDto;
import fe.cmn.panel.BoxDto;
import fe.cmn.panel.ContainerDto;
import fe.cmn.panel.PanelContext;
import fe.cmn.panel.ability.QuitPopup;
import fe.cmn.res.JDFICons;
import fe.cmn.table.TableBuilder;
import fe.cmn.table.TableColumnDto;
import fe.cmn.table.TableContext;
import fe.cmn.table.TableDto;
import fe.cmn.table.TableHeaderDto;
import fe.cmn.table.TableQuerier;
import fe.cmn.table.TableQuerierContext;
import fe.cmn.table.TableRowDto;
import fe.cmn.table.TableRowGestureDetectorDto;
import fe.cmn.table.TableRowsDto;
import fe.cmn.table.decoration.TableRowDecorationDto;
import fe.cmn.widget.ButtonDto;
import fe.cmn.widget.LabelDto;
import fe.cmn.widget.ListenerDto;
import fe.cmn.widget.WidgetDto;
import fe.util.FeListenerUtil;
import fe.util.anotation.CommandDefine;
import fe.util.anotation.ComponentDefine;
import fe.util.component.FormEditPanelIntf;
import fe.util.component.dto.FeCmnEvent;
import fe.util.component.dto.FeDeliverData;
import fe.util.component.extlistener.CommandCallbackListener;
import fe.util.component.param.WidgetParam;
import fe.util.exception.VerifyException;
import fe.util.intf.ServiceIntf;
import fe.util.style.FeStyleConst;
import fe.util.style.FeStyleSetting;
import gpf.adur.action.Action;
import gpf.adur.data.DataType;
import gpf.adur.data.FormField;
import gpf.adur.data.ResultSet;
import gpf.dc.basic.action.intf.CustomQueryIntf;
import gpf.dc.basic.dto.privilege.ResultSetQueryParam;
import gpf.dc.basic.fe.component.fieldextend.editor.WidgetLayoutUtil;
import gpf.dc.basic.fe.component.param.BaseTableViewParam;
import gpf.dc.basic.fe.enums.ListenerApplyLocation;
import gpf.dc.basic.fe.enums.TableCellEditorType;
import gpf.dc.basic.fe.intf.TableRowDtoInterceptor;
import gpf.dc.basic.i18n.GpfDCBasicI18n;
import gpf.dc.basic.param.view.BaseFeActionParameter;
import gpf.dc.basic.param.view.CustomQueryParameter;
import gpf.dc.basic.param.view.dto.ButtonDefine;
import gpf.dc.basic.param.view.dto.FormFieldDefine;
import gpf.dc.basic.param.view.dto.OrderByOptionDto;
import gpf.dc.basic.param.view.dto.TableColumnDefine;
import gpf.dc.basic.param.view.dto.TableViewSetting;
import gpf.dc.basic.util.GpfDCBasicUtil;
import gpf.dc.concrete.RefActionConfig;
import gpf.dc.fe.component.adur.data.field.FormFieldEditorFactory;
import gpf.dc.runtime.PDCForm;
import gpf.dc.runtime.PDFForm;
import gpf.dc.runtime.PDFFormQueryOption;
import gpf.dto.model.data.ActionPrivilegeDto;

@ClassDeclare(label = "流程表格"
,what=""
, why = ""
, how = ""
,developer="陈晓斌"
,version = "1.0"
,createTime = "2024-11-22"
,updateTime = "2024-11-22")
@ComponentDefine(
		label = "PDF实例表格",
		commands = {
				@CommandDefine(value = PDFFormTableView.CMD_ADD_OR_UPDATE_ROW,label="新增或修改行",desc = "",inputClass = PDFForm.class,outputClass= void.class),
				@CommandDefine(value = PDFFormTableView.CMD_SEARCH,label="查询",desc = "",inputClass = NullPojo.class,outputClass= void.class)
		}
		)
public class PDFFormTableView<T extends BaseTableViewParam> extends AbsTableView<T> implements ViewListenerBuilder,PDCFormBuilder{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5275446666680644039L;
	@FieldDeclare(label = "添加或更新行", desc = "执行添加新行或更新现有行的操作")
	public final static String CMD_ADD_OR_UPDATE_ROW = "CMD_ADD_OR_UPDATE_ROW";

	@FieldDeclare(label = "退出弹窗", desc = "关闭或退出当前弹出窗口的命令")
	public final static String CMD_QUIT_POPUP = "CMD_QUIT_POPUP";

	@FieldDeclare(label = "值变更", desc = "值发生变化时触发的命令")
	public final static String CMD_VALUE_CHANGED = "CMD_VALUE_CHANGED";
	public final static String CacheKey_CustomCols="CacheKey_CustomCols";
//	public final static String CacheKey_PDFFormFields="CacheKey_PDFFormFields";

	@Override
	public List<ButtonDto> buildRowOperateButtons(PanelContext context, BaseTableViewParam widgetParam) throws Exception {
		List<ButtonDto> rowButtons = new ArrayList<>();
		TableViewSetting setting = widgetParam.getSetting(TableViewSetting.class);
		if(setting.isShowRowDetailBtn()) {
			rowButtons.add(buildRowDetailBtn());
		}
		//添加行操作按钮
		for(ButtonDefine button : widgetParam.getRootRowOperateButtons()) {
			String buttonLabel = getI18nString(context,button.getLabel());
			if(button.isButtonGroup()) {
				ButtonDto rowBtn = buildButton(buttonLabel, button.getIconSrc(), CMD_CLICK_ROW_BUTTON_GROUP, null);
				rowBtn.setWidgetId(ROW_BUTTON_WIDGET_PREFIX+button.getName());
				if(!CmnUtil.isStringEmpty(button.getStyle())) {
					rowBtn.setStyleName(button.getStyle());
				}else {
					rowBtn.setStyleName(FeStyleConst.common_table_row_icon_btn);
				}
				rowButtons.add(rowBtn);
			}else {
				ButtonDto rowBtn = buildButton(buttonLabel, button.getIconSrc(), ROW_BUTTON_WIDGET_PREFIX+button.getName(), null);
				setRowButtonListenerByListenerDefine(rowBtn, widgetParam.getListenerDefines());
				if(!CmnUtil.isStringEmpty(button.getStyle())) {
					rowBtn.setStyleName(button.getStyle());
				}else {
					rowBtn.setStyleName(FeStyleConst.common_table_row_icon_btn);
				}
				rowButtons.add(rowBtn);
			}
		}
		return rowButtons;
	}
	
	@Override
	public WidgetDto doGetWidget(PanelContext panelContext) throws Exception {
		TableDto table =  (TableDto) super.doGetWidget(panelContext);
		WidgetDto layout = widgetParam.getLayout();
		if(layout != null) {
			table = (TableDto) WidgetLayoutUtil.setRealWidget2Layout(table, layout,getI18n(panelContext),widgetParam.getModelId());
		}
		table.addSubscribeEvent(new EventSubscriberDto(FeCmnEvent.class).setService(getBuilderService()).setCommand(CMD_REFRESH).setIdentifyFilter(table.getPanelGlobalKey()));
		table.setUnitName(WidgetLayoutUtil.UNIT_NAME_CUSTOM_TABLE);
		setListenerByListenerDefine(panelContext,table, widgetParam.getListenerDefines(),ListenerApplyLocation.Table);
		
		TableViewSetting setting = widgetParam.getSetting(TableViewSetting.class);
		table.setShowCheckbox(setting.isShowCheckBox());
		if(setting.getMaxEmbedHeight() != null) {
			table.setPreferHeight(setting.getMaxEmbedHeight());
		}
		if (setting.isDoubleClickOpen()){
			FeDeliverData<T> lsnrData = new FeDeliverData<T>(getClass());
			table.setTableRowGestureDetector(new TableRowGestureDetectorDto()
					.setOnDoubleClick(FeListenerUtil.OnTableRowClick(getBuilderService(), CMD_ON_TABLE_ROW_CLICK, true, lsnrData))
			);
		}
//		doAfterInited(panelContext, table.getPanelGlobalKey(), table);
		return table;
	}
	
	@Override
	public Object newRowObject(ListenerDto listener, PanelContext panelContext, WidgetDto source) throws Exception {
		String pdfUuid = widgetParam.getModelId();
		String user = panelContext.getCurrentUser();
		return newPDCForm(panelContext,this,pdfUuid, user,null);
	}
	
	@Override
	public Object getEditObject(TableRowDto row) throws ClassNotFoundException, IOException {
		PDFForm cdo = (PDFForm) row.getBinaryData();
		String pdfUuid = widgetParam.getModelId();
		String user = getPanelContext().getCurrentUser();
		PDCForm pdcForm = getEditPDCForm(getPanelContext(),this,pdfUuid, user, cdo);
		pdcForm.setExtFields(cdo.getExtFields());
		return pdcForm;
	}
	
	@Override
	public Object copyRowObject(TableContext panelContext, TableRowDto row) throws Exception {
		PDCForm copyForm = (PDCForm) getEditObject(row);
		String pdfUuid = widgetParam.getModelId();
		String user = panelContext.getCurrentUser();
		return newPDCForm(panelContext,this,pdfUuid, user,copyForm);
	}
	
	@Override
	public WidgetDto getTopBar(PanelContext panelContext,String panelGlobalKey, T widgetParam) throws Exception {
		Map<String,ActionPrivilegeDto> actionPriviegeMap = getActionPrivielge(panelContext);
		
		BoxDto topBar = new BoxDto().setVertical(false);
		TableViewSetting setting = widgetParam.getSetting(TableViewSetting.class);
		
		ButtonDto refresh = buildRefreshButton(widgetParam).setVisible(setting.isAllowRefresh());
		setButtonPrivilege(actionPriviegeMap, refresh,widgetParam.getGroupVisibleOverride(),widgetParam.getGroupWritableOverride());
		topBar.addChild(refresh);

		ButtonDefine addBtnDef = getButtonDefineByCommand(CMD_CREATE);
		ButtonDto addBtn = null;
		if(addBtnDef != null) {
			addBtn = buildButton(panelContext,addBtnDef);
		}else {
			addBtn = buildAddButton(widgetParam);
		}
		addBtn.setVisible(setting.isAllowCreate());
		setButtonPrivilege(actionPriviegeMap, addBtn,widgetParam.getGroupVisibleOverride(),widgetParam.getGroupWritableOverride());
		topBar.addChild(addBtn);
		
		if(setting.isAllowCopyRow()) {
			ButtonDefine copyBtnDef = getButtonDefineByCommand(CMD_COPY_ROW);
			ButtonDto copyBtn = null;
			if(copyBtnDef != null) {
				copyBtn = buildButton(panelContext,copyBtnDef);
			}else{
				copyBtn= buildButton("", JDFICons.copy, CMD_COPY_ROW, null);
			}
			setButtonPrivilege(actionPriviegeMap, copyBtn,widgetParam.getGroupVisibleOverride(),widgetParam.getGroupWritableOverride());
			topBar.addChildren(copyBtn);
		}
		
//		ButtonDto deleteBtn = buildDeleteButton(widgetParam).setVisible(setting.isAllowDelete());
//		topBar.addChild(deleteBtn);
		
		addHideColBtn(panelContext, topBar, setting, actionPriviegeMap, getI18nString(panelContext, GpfDCBasicI18n.HiddenColum));
		
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
				topBar.addChild(button);
			}else {
				ButtonDto button = buildButton(buttonLabel, buttonDef.getIconSrc(), buttonDef.getName(), null);
				if(!CmnUtil.isStringEmpty(buttonDef.getStyle())) {
					button.setStyleName(buttonDef.getStyle());
				}
				setRowButtonListenerByListenerDefine(button, widgetParam.getListenerDefines());
				button.setUnitName(WidgetLayoutUtil.UNIT_NAME_CUSTOM_BUTTON);
				button.setGroupIds(buttonDef.getTagGroupIdArray());
				setButtonPrivilege(actionPriviegeMap, button,widgetParam.getGroupVisibleOverride(),widgetParam.getGroupWritableOverride());
				topBar.addChild(button);
			}
		}
//		if(!CmnUtil.isCollectionEmpty(widgetParam.getSearchBar())) {
//			topBar.addChild(WidgetLayoutUtil.wrapDesignContainer("搜索",buildButton("", JDFICons.search, CMD_SEARCH, null)));
//		}
		topBar.addChild(newSearchBar(panelContext,panelGlobalKey,setting));
//		topBar.addExpander();
		topBar = addAddtionalWidget(panelContext, topBar);
		return ContainerDto.wrap(topBar);
	}
	
	

//	@Override
//	public List<FormField> doQueryModelFormField(PanelContext panelContext) throws Exception {
//		List<FormField> formFields = IPDFRuntimeMgr.get().queryPDFFormFields(widgetParam.getModelId());
//		List<FormField> fields = new ArrayList<>();
//		for(FormField formField : formFields){
//			FormField field = ToolUtilities.clone(formField);
//			for(TableColumnDefine colDef : widgetParam.getCloumns()) {
//				if(CmnUtil.isStringEqual(colDef.getCode(), field.getCode())) {
//					if(colDef.getExtendInfo() != null) {
//						field.setExtendInfo(colDef.getExtendInfo());
//					}
//				}
//			}
//			fields.add(field);
//		}
//		return fields;
//	}

	@Override
	public List<FormField> doQueryModelFormField(PanelContext panelContext) throws Exception {
		List<FormField> formFields = IPDFRuntimeMgr.get().queryPDFFormFields(widgetParam.getModelId());
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
	//FIXME 这段代码后面弃用
	@Deprecated
	List<String> getCacheCostomCols(PanelContext panelContext) throws Exception{
		List<String> customCols=(List<String>) getPanelCacheValue(panelContext, CacheKey_CustomCols);
		if(customCols == null) {
			customCols=new ArrayList<>();
			for (TableColumnDefine cloumn : widgetParam.getColumns()) {
				if(cloumn.isCustom())
					customCols.add(cloumn.getCode());
			}
			setPanelCacheValue(panelContext, CacheKey_CustomCols, customCols);
		}
		return customCols;
	}
//	List<FormField> getCachePDFFormFields(PanelContext panelContext) throws Exception{
//		List<FormField> formFields=(List<FormField>) getPanelCacheValue(panelContext, CacheKey_PDFFormFields);
//		if(formFields == null) {
//			formFields=IPDFRuntimeMgr.get().queryPDFFormFields(widgetParam.getModelId());
//			setPanelCacheValue(panelContext, CacheKey_PDFFormFields, formFields);
//		}
//		return formFields;
//	}
	@Override
	public TableRowDto convert2TableRowDto(Object data) throws Exception {
		PDFForm cdo = (PDFForm) data;
		if(CmnUtil.isStringEmpty(cdo.getOpLogUuid()))
			throw new VerifyException(getI18nString(getPanelContext(),GpfDCBasicI18n.CAN_NOT_BE_NULL,"opLogUuid"));
		TableViewSetting setting = widgetParam.getSetting(TableViewSetting.class);
		TableRowDtoInterceptor interceptor = setting.getTableRowDtoInterceptorInst();
		IDCRuntimeContext rtx = null;
		if(interceptor != null) {
			rtx = getOrPrepareRtx(getPanelContext(), getPanelContext().getCurrentPanelGlobalKey());
		}
		doBeforeConvertTableRowDto(interceptor, rtx, cdo);
		List<FormField> fieldList = getCacheFormField(getPanelContext());
		TableRowDto row = new TableRowDto();//convert2TableRow(bizField);
		row.setRowId(cdo.getOpLogUuid());
		List<String> customCols=getCacheCostomCols(getPanelContext());

		if(CmnUtil.isCollectionEmpty(widgetParam.getColumns())) {
			for(FormField field : fieldList) {
				if(hiddenFields.contains(field.getCode()))
					continue;
				Object value = cdo.getAttrValueByCode(field.getCode());
				row.putValue(field.getCode(), getEditorFactory(getPanelContext().getCurrentPanelGlobalKey(),widgetParam).buildLabelTableCell(getPanelContext(),cdo,field, value));
			}
		}else {
			
		}
		Map<String,FormField> fieldMap = fieldList.stream().collect(Collectors.toMap(FormField::getCode, v->v,(e,r)->e));
		for(TableColumnDefine columDef : widgetParam.getColumns()) {
			FormField field = fieldMap.get(columDef.getCode());
			if(field != null) {
				DataType display = field.getDataTypeEnum();
				if(display == DataType.RichDocument || display == DataType.NestingModel 
						|| display == DataType.Password || display == DataType.KeyValue)
					continue;
				if(hiddenFields.contains(field.getCode()))
					continue;
				Object value = cdo.getAttrValueByCode(field.getCode());
				//自定义列把整个form传进去
				if(customCols.contains(field.getCode())&&field.getExtendInfo()!=null) {
					//FIXME 这段代码后面弃用
					List<FormField> cachePDFFormFields = getCacheFormField(getPanelContext());
					value= new Pair<>(cachePDFFormFields,cdo);
				}
				TableCellEditorType editorType = columDef.getTableCellEditorTypeEnum();
				if(editorType == TableCellEditorType.ReadOnlyEditor) {
					try {
						row.putValue(field.getCode(), getEditorFactory(getPanelContext().getCurrentPanelGlobalKey(),widgetParam).buildEditorTableCell(getPanelContext(),cdo,field, value));
					}catch (Exception e) {
						PopToast.warning(getPanelContext().getChannel(), "构建单元格["+columDef.getName()+"]值出错：",ToolUtilities.getFullExceptionStack(e));
						row.putValue(field.getCode(), getEditorFactory(getPanelContext().getCurrentPanelGlobalKey(),widgetParam).buildLabelTableCell(getPanelContext(),cdo,field, value));
					}
				}else {
					row.putValue(field.getCode(), getEditorFactory(getPanelContext().getCurrentPanelGlobalKey(),widgetParam).buildLabelTableCell(getPanelContext(),cdo,field, value));
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
		if(!CmnUtil.isStringEmpty(cdo.getErrorMsg())) {
			FeStyleSetting styleSetting = getFeStyleSetting(getPanelContext());
			row.setDecoration(new TableRowDecorationDto(styleSetting.getDangerousColor()));
		}
		row.setBinaryData((Serializable)cdo);
		doAfterConvertTableRowDto(interceptor, rtx, row);
		return row;
	}
	
	public FormFieldEditorFactory getEditorFactory(String panelGlobalKey,WidgetParam widgetParam) {
		return FormFieldEditorFactory.create(panelGlobalKey,this,isLazyQueryCompoundField());
	}
	@Override
	public TableRowDto doCreateRowData(PanelContext panelContext, Object rowData) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TableRowDto doUpdateRowData(PanelContext panelContext, Object rowData) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doDeleteRowData(PanelContext panelContext, List<String> rowIds) throws Exception {
		List<TableRowDto> rows = queryRows(panelContext, rowIds);
		for(TableRowDto row : rows) {
			PDFForm form = (PDFForm) row.getBinaryData();
			IPDFRuntimeMgr.get().deletePDFInstance(form.getPdfInstUuid());
		}
	}
	
	List<String> hiddenFields = Arrays.asList(PDFForm.PdfInstUuid,PDFForm.ParentFormUuid,PDFForm.StepTag,PDFForm.Assignee,PDFForm.NodeKey,PDFForm.NodeName,PDFForm.StepOperator,PDFForm.ExecuteTime,PDFForm.ActionName,PDCForm.OpLogUuid);
	public boolean isHiddenField(PanelContext context,FormField formField) throws Exception {
		return hiddenFields.contains(formField.getCode());
	}
	
	@Override
	public List<String> getRequestCategorys(PanelContext panelContext) {
		try {
//			TraceUtil.logStart();
//			PDF pdf = IPDFMgr.get().queryPDF(widgetParam.getModelId());
			String modelLabel = widgetParam.getModelId();//pdf.getLabel();
//			TraceUtil.printCost("getRequestCategorys", false);
			return Arrays.asList(modelLabel);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public TableHeaderDto doQueryTableMeta(TableBuilder builder, TableQuerierContext context) throws Exception {
		TableHeaderDto header = new TableHeaderDto();
		List<TableColumnDto> columns = new ArrayList<>();
		List<FormField> fieldList = getCacheFormField(context);
		FormFieldEditorFactory factory = getEditorFactory(context.getCurrentPanelGlobalKey(), widgetParam);
		if(CmnUtil.isCollectionEmpty(widgetParam.getColumns())) {
			for(FormField bizField : fieldList) {
				DataType display = bizField.getDataTypeEnum();
				if(display == DataType.RichDocument || display == DataType.NestingModel || display == DataType.Password)
					continue;
				if(isHiddenField(context, bizField))
					continue;
				TableColumnDto fieldCol = new TableColumnDto();
				fieldCol.setName(bizField.getCode());
				fieldCol.setLabel(getI18nString(context,bizField.getName()));
				fieldCol.setEditor(new LabelDto());
				fieldCol.setButtons(buildColButton(context, bizField));
				columns.add(fieldCol);
			}
		}else {
			Map<String,FormField> fieldMap = new LinkedHashMap<>();
			for(FormField formField : fieldList) {
				fieldMap.put(formField.getCode(), formField);
			}
			for(TableColumnDefine columDef : widgetParam.getColumns()) {
				FormField formField = fieldMap.get(columDef.getCode());
				if(formField != null) {
			
					DataType display = formField.getDataTypeEnum();
					if(display == DataType.RichDocument || display == DataType.NestingModel || display == DataType.Password)
						continue;
					if(isHiddenField(context, formField))
						continue;
					TableColumnDto fieldCol = buildLabelColumn(context,columDef, formField,factory);
					columns.add(fieldCol);
				}
			}
		}
		
		header.setColumns(columns);
		return header;
	}

	@Override
	public TableRowsDto doQueryTableRows(TableBuilder builder, TableQuerier querier, TableQuerierContext context)
			throws Exception {
		Tracer tracer = TraceUtil.getCurrentTracer();
		TableViewSetting setting = widgetParam.getSetting(TableViewSetting.class);
		String pdfUuid = widgetParam.getModelId();
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
			ResultSetQueryParam privilegeParam = getDataPrivilegeSqlExpression(context, dao);
			//获取含权限sql
//			SqlExpressionGroup privilegeExpr = null;
			//FIXME 这里要考虑性能问题，传入的查询列要限定下来
			PDFFormQueryOption queryOption = null;
			String dataSql = IPDFRuntimeMgr.get().buildPDFFormQuerySql(pdfUuid,queryOption, null, null);
			String user = context.getCurrentUser();
			SqlExpressionGroup defaultExpr = null;
			if(setting.isEnableDefaultDataPrivilege())
				defaultExpr = new SqlExpressionGroup().orEquals(PDFForm.Creator, user).orEquals(PDFForm.StepOperator, user).orLike(PDFForm.Assignee, "[-]" + user + "[-]");
			String sql = buildResultSetQuerySqlWithPrivilege(dataSql, privilegeParam,defaultExpr);
			tracer.info("视图查询SQL：\n"+sql);
			cnd = apppendAppDefaultFilter(context, pdfUuid, cnd);
			if(cnd != null) {
				tracer.info(" " + cnd.toString());
			}
			ResultSet<PDFForm> rs = new ResultSet<>();
			if(!CmnUtil.isCollectionEmpty(widgetParam.getCustomQueryFuncs())) {
				RefActionConfig func = widgetParam.getCustomQueryFuncs().get(0);
				Action customQueryAction = func.getAction();
				if(customQueryAction != null) {
					if(!CustomQueryIntf.class.isAssignableFrom(customQueryAction.getJavaClass())) {
						PopToast.warning(context.getChannel(), "当前自定义查询未实现CustomQueryIntf接口！");
					}else {
						IDCRuntimeContext rtx = getOrPrepareRtx(context, context.getCurrentPanelGlobalKey());
						rtx.setDao(dao);
						BaseFeActionParameter.prepareFeActionParameter(rtx, context, (ListenerDto)null, this);
						CustomQueryParameter.prepareCustomQueryParameter(rtx, sql,privilegeParam,defaultExpr,cnd, pageNo, querier.getPageSize(),getFilter(context));
						rs = (ResultSet<PDFForm>) IActionMgr.get().executeAction(customQueryAction, rtx);
					}
				}
			}else {
				Set<String> extFields = new LinkedHashSet<>();
				extFields.add(ResultSet.TotalCount);
				if(cnd != null) {
					tracer.info(" " + cnd.toString());
				}
				tracer.info("开始查询数据");
				tracer.logStart();
				setCacheCustomQuerySql(context, sql);
				setCacheCnd(context, cnd);
				rs = IPDFRuntimeMgr.get().queryPDFFormPageBySql(pdfUuid, sql, extFields, cnd, pageNo, querier.getPageSize());
				tracer.infoCost("", "数据查询结束");
				tracer.info("预加载关联数据缓存");
				tracer.logStart();
				preloadAssocDatas(dao, getEditorFactory(context.getCurrentPanelGlobalKey(), widgetParam), context, (List)rs.getDataList());
				tracer.infoCost("", "关联数据缓存预加载结束");
			}
			TableRowsDto rows = new TableRowsDto();
			List<TableRowDto> listRow = new LinkedList<TableRowDto>();
			if(rs.getSize() > 0) {
				tracer.info("计算行操作权限");
				tracer.logStart();
				Map<String,Map<String,ActionPrivilegeDto>> allRowPrivs = getRowActionPrivilege(context, rs.getDataList());
				tracer.infoCost("", "行操作权限计算结束");
				
				for(PDFForm cdo : rs.getDataList()) {
					TableRowDto row = convert2TableRowDto(cdo);
					listRow.add(row);
				}
			}
			rows.setRows(listRow);
			rows.setPageStart(querier.getStartPos());
			rows.setTotalRows(rs.getTotalCount());
			tracer.info("", "返回表格行数据");
			rows = doAfterQueryTableRows(context, rows);
			return rows;
		}
	}
	
	@Override
	public Cnd buildCondition(PanelContext context,IDao dao,Cnd cnd) throws Exception {
		TableViewSetting setting = widgetParam.getSetting(TableViewSetting.class);
		String pdfUuid = widgetParam.getModelId();
		String filtersKeyWord = getFilterKeyword(context);
		SqlExpressionGroup advFilter = getAdvFilter(context);
		SqlExpressionGroup defaultFilter = widgetParam.getDefaultFilter();
		if(defaultFilter != null) {
			cnd.and(defaultFilter);
		}
		List<FormField> fieldList = getCacheFormField(context);
		if(!CmnUtil.isStringEmpty(filtersKeyWord)) {
//			List<FormField> fieldList = IPDFRuntimeMgr.get().queryPDFFormFields(pdfUuid);
			List<FormField> queryFields = new ArrayList<>();
			List<String> summaryFilterCols = setting.getSummaryFilterColList();
			if(!CmnUtil.isCollectionEmpty(summaryFilterCols)) {
				for(FormField field : fieldList) {
					if(summaryFilterCols.contains(field.getName()))
						queryFields.add(field);
				}
			}else {
				queryFields = fieldList;
			}
			SqlExpressionGroup exprGrp = IFormMgr.get().buildMatchQueryOfFields(queryFields, filtersKeyWord);
			cnd.and(exprGrp);
		}
		if(advFilter != null) {
			cnd.and(advFilter);
		}
		List<TableColumnDefine> columnDefs = widgetParam.getColumns();
		List<FormFieldDefine> formFieldDefines = GpfDCBasicUtil.mergeFormFieldByTableColumnDefines(fieldList, columnDefs == null ? new ArrayList<>():columnDefs);
		Map<String,FormFieldDefine>  formFieldDefineMap = formFieldDefines.stream().collect(Collectors.toMap(FormFieldDefine::getCode, v->v,(e,r)->e));
		
		//字段值筛选条件
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
		String user = context.getCurrentUser();
		//数据默认权限
//		SqlExpressionGroup defaultExpr = new SqlExpressionGroup().orEquals(PDFForm.Creator, user).orEquals(PDFForm.StepOperator, user).orLike(PDFForm.Assignee, "[-]" + user + "[-]");
		
//		SqlExpressionGroup privExpr = getDataPrivilegeSqlExpression(context,dao);
//		if(privExpr != null) {
//			//补充的其他权限
//			defaultExpr.or(privExpr);
////			cnd.and(privExpr);
//		}
//		cnd.and(defaultExpr);
		
		getDataFilterSqlExpression(context, dao,cnd);
		return cnd;
	}

	@Override
	public Class<? extends ServiceIntf> getService() {
		return IGpfDCBasicFeService.class;
	}

	@Override
	public Object onListener(ListenerDto listener, PanelContext panelContext, WidgetDto source) throws Exception {
		if(listener.isServiceCommand(CMD_ADD_OR_UPDATE_ROW)) {
			FeDeliverData<PDCForm> feData = (FeDeliverData<PDCForm>) listener.getBinaryData();
			PDCForm data = feData.getData();
//				addOrUpdateRow(panelContext, data);
			QuitPopup.quit(panelContext);
			onBtnRefresh(listener, panelContext, source);
		}else {
			return super.onListener(listener, panelContext, source);
		}
		return null;
	}
	
	
	@Override
	public FormEditPanelIntf buildEditRowPanel(ListenerDto listener, PanelContext panelContext, WidgetDto source,
			Object rowData, boolean isWriteable) throws Exception {
		CommandCallbackListener confirmCallbacklsnr = newPopupPanelCallbackListener(getService(), BaseFormEditView.CMD_CONFIRM, CMD_ADD_OR_UPDATE_ROW, null);
		CommandCallbackListener cancelCallbackLsnr = newPopupPanelCallbackListener(getService(), BaseFormEditView.CMD_CANCEL, CMD_QUIT_POPUP, null);
		CommandCallbackListener deleteCallbackLsnr = newPopupPanelCallbackListener(getService(), BaseFormEditView.CMD_DELETE, CMD_DELETE_CALLBACK, null);
		return buildFormEditPanel(listener, panelContext, source, rowData, isWriteable,Arrays.asList(confirmCallbacklsnr,cancelCallbackLsnr,deleteCallbackLsnr));
	}
	
	@Override
	public Action getViewAction(Object data) throws Exception {
		PDCForm pdcForm = (PDCForm) data;
		return widgetParam.getViewActionByNode(pdcForm.getNodeKey());
	}
	
	@Override
	public Map<String, String> getCteQuerySqls(PanelContext panelContext, String fieldCode) throws Exception {
		String pdfUuid = widgetParam.getModelId();
		//FIXME 这里要考虑性能问题，传入的查询列要限定下来
		PDFFormQueryOption queryOption = null;
		return IPDFRuntimeMgr.get().buildPDFFormQueryCteSql(pdfUuid,queryOption);
	}
	
	@Override
	public String getMainTableAlias(PanelContext panelContext, String fieldCode) throws Exception {
		// TODO Auto-generated method stub
		return "ticket";
	}
}
