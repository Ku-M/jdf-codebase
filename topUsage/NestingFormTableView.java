package gpf.dc.basic.fe.component.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.kwaidoo.ms.tool.ToolUtilities;
import com.leavay.common.util.Pair;
import com.leavay.ms.tool.CmnUtil;

import cell.fe.gpf.dc.IAppGlobalSettingPlugin;
import cell.fe.gpf.dc.basic.IGpfDCBasicFeService;
import cell.gpf.adur.data.IFormMgr;
import cmn.anotation.ClassDeclare;
import cmn.anotation.FieldDeclare;
import cmn.util.TraceUtil;
import cmn.util.Tracer;
import fe.cmn.app.ability.PopToast;
import fe.cmn.data.PairDto;
import fe.cmn.panel.PanelContext;
import fe.cmn.res.JDFICons;
import fe.cmn.table.TableBuilder;
import fe.cmn.table.TableCellDto;
import fe.cmn.table.TableColumnDto;
import fe.cmn.table.TableDto;
import fe.cmn.table.TableHeaderDto;
import fe.cmn.table.TableQuerier;
import fe.cmn.table.TableQuerierContext;
import fe.cmn.table.TableRowDto;
import fe.cmn.table.TableRowsDto;
import fe.cmn.table.ability.QueryTableRows;
import fe.cmn.widget.ButtonDto;
import fe.cmn.widget.LabelDto;
import fe.cmn.widget.ListenerDto;
import fe.cmn.widget.WidgetDto;
import fe.util.anotation.CommandDefine;
import fe.util.anotation.ComponentDefine;
import fe.util.component.FormEditPanelIntf;
import fe.util.component.PopupPanel;
import fe.util.component.dto.FeDeliverData;
import fe.util.component.extlistener.CommandCallbackListener;
import fe.util.component.extlistener.CommandListener;
import fe.util.component.param.WidgetParam;
import fe.util.exception.VerifyException;
import fe.util.i18n.FeI18n;
import fe.util.intf.ServiceIntf;
import fe.util.style.FeStyleConst;
import gpf.adur.data.DataType;
import gpf.adur.data.Form;
import gpf.adur.data.FormField;
import gpf.adur.data.FormModel;
import gpf.adur.data.TableData;
import gpf.dc.basic.fe.component.app.AppCacheUtil;
import gpf.dc.basic.fe.component.param.BaseDataViewParam;
import gpf.dc.basic.fe.component.param.NestingTableViewParam;
import gpf.dc.basic.fe.enums.TableCellEditorType;
import gpf.dc.basic.i18n.GpfDCBasicI18n;
import gpf.dc.basic.param.view.dto.ButtonDefine;
import gpf.dc.basic.param.view.dto.FormFieldDefine;
import gpf.dc.basic.param.view.dto.TableColumnDefine;
import gpf.dc.basic.param.view.dto.TableViewSetting;
import gpf.dc.basic.util.GpfDCBasicUtil;
import gpf.dc.fe.component.adur.data.field.FormFieldEditorFactory;
import gpf.dc.intf.InheritableIntf;
import gpf.dto.model.data.ActionPrivilegeDto;


@ClassDeclare(label = "嵌套表格"
,what=""
, why = ""
, how = ""
,developer="陈晓斌"
,version = "1.0"
,createTime = "2024-11-22"
,updateTime = "2024-11-22")
@ComponentDefine(
		label = "数据集",
		commands = {
				@CommandDefine(label = "值改变监听",inputClass = void.class, outputClass = void.class, value = NestingFormTableView.CMD_VALUE_CHANGED)
		}
		)
public class NestingFormTableView <T extends NestingTableViewParam> extends AbsNestingTableView<T>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3001562862633098120L;
	@FieldDeclare(label = "值变更", desc = "值发生变化时触发的命令")
	public final static String CMD_VALUE_CHANGED = "CMD_VALUE_CHANGED";
	
	public Class<? extends ServiceIntf> getService(){
		return IGpfDCBasicFeService.class;
	}
	
	@Override
	public void initPanelCache(PanelContext panelContext, String panelGlobalKey) throws Exception {
		super.initPanelCache(panelContext, panelGlobalKey);
		initPanelCacheValue(panelContext, panelGlobalKey, CacheFormModel, null);
	}

	@Override
	public List<ButtonDto> buildRowOperateButtons(PanelContext context, T widgetParam) throws Exception {
		boolean writable = widgetParam.isWritable();
		TableViewSetting setting = widgetParam.getSetting(TableViewSetting.class);
		List<ButtonDto> buttons = new ArrayList<>();
		if(writable && setting.isAllowDelete()) {
			ButtonDto delete = new ButtonDto("", JDFICons.delete).setWidgetId(ROW_DELETE_WIDGET_ID)
					.setOnClick(newListener(getBuilderService(), CMD_ROW_DELETE, true, null))
					;
			delete.setStyleName(FeStyleConst.common_table_row_icon_btn);
			buttons.add(delete);
		}
		if(setting.isShowRowDetailBtn()) {
			buttons.add(buildRowDetailBtn());
		}
		//添加行操作按钮
		for(ButtonDefine buttonDef : widgetParam.getRootRowOperateButtons()) {
			String buttonLabel = getI18nString(context, buttonDef.getLabel());
			if(buttonDef.isButtonGroup()) {
				ButtonDto rowBtn = buildButton(buttonLabel, buttonDef.getIconSrc(), CMD_CLICK_ROW_BUTTON_GROUP, null);
				rowBtn.setWidgetId(ROW_BUTTON_WIDGET_PREFIX+buttonDef.getName());
				rowBtn.setStyleName(FeStyleConst.common_table_row_icon_btn);
				buttons.add(rowBtn);
			}else {
				ButtonDto button = buildButton(buttonLabel, buttonDef.getIconSrc(), ROW_BUTTON_WIDGET_PREFIX+buttonDef.getName(), null);
				setRowButtonListenerByListenerDefine(button, widgetParam.getListenerDefines());
				button.setStyleName(FeStyleConst.common_table_row_icon_btn);
				buttons.add(button);
			}
		}
		return buttons;
	}
	
	@Override
	public WidgetDto doGetWidget(PanelContext panelContext) throws Exception {
		TableDto table =  (TableDto) super.doGetWidget(panelContext);
		ListenerDto lsnr = newListener(getBuilderService(), CMD_VALUE_CHANGED, true, null);
		CommandListener onValueChangedLsnr = new CommandListener(CMD_VALUE_CHANGED, "值改变监听", "", lsnr, true);
		table.addExtendListener(onValueChangedLsnr);
		return table;
	}
	
	protected final static String CacheFormModel = "$formModel$";
	protected FormModel getCacheFormModel(PanelContext panelContext) throws Exception {
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
	
//	@Override
//	public List<FormField> doQueryModelFormField(PanelContext panelContext) throws Exception {
//		FormModel formModel = getCacheFormModel(panelContext);
//		return formModel.getFieldList();
//	}
	@Override
	public List<FormField> doQueryModelFormField(PanelContext panelContext) throws Exception {
		FormModel formModel = getCacheFormModel(panelContext);
		List<FormField> formFields = formModel.getFieldList();
		List<FormField> fields = new ArrayList<>();
		for(TableColumnDefine colDef : widgetParam.getColumns()) {
			if(com.kwaidoo.ms.tool.CmnUtil.isStringEmpty(colDef.getName()))
				continue;
			FormField newfield=null;
			//自定义列
			if(colDef.isCustom()){
				newfield=new FormField(IFormMgr.get().getFieldCode(colDef.getName()));
				newfield.setName(colDef.getName());
				newfield.setDataType(DataType.Text.name());
				if (colDef.getExtendInfo() != null) {
					newfield.setExtendInfo(colDef.getExtendInfo());
				}
			}
			else {
				for (FormField formField : formFields) {
					if (com.kwaidoo.ms.tool.CmnUtil.isStringEqual(colDef.getCode(), formField.getCode())) {
						newfield = ToolUtilities.clone(formField);
						if (colDef.getExtendInfo() != null) {
							newfield.setExtendInfo(colDef.getExtendInfo());
						}
					}
				}
			}
			if(newfield!=null)
				fields.add(newfield);
		}
		return fields;
	}
	public boolean isHiddenField(PanelContext context,FormField field) throws Exception {
		FormModel model = getCacheFormModel(context);
		List<String> hiddenFields = model.getHiddenFields();
		boolean isHidden = hiddenFields.contains(field.getCode());
		if(isHidden) {
			return isHidden;
		}
		return isHidden;
	}
	
	@Override
	public TableHeaderDto queryTableMeta(TableBuilder builder, TableQuerierContext context) throws Exception {
		FormModel model = getCacheFormModel(context);
		List<FormField> fieldList = getCacheFormField(context);
		List<TableColumnDto> lstCols = new LinkedList<TableColumnDto>();
		if(CmnUtil.isCollectionEmpty(widgetParam.getColumns())) {
			for(FormField bizField : model.getFieldList()) {
				DataType display = bizField.getDataTypeEnum();
				if(display == DataType.RichDocument || display == DataType.NestingModel)
					continue;
				if(isHiddenField(context, bizField))
					continue;
				TableColumnDto fieldCol = new TableColumnDto();
				fieldCol.setName(bizField.getCode());
				fieldCol.setLabel(getI18nString(context, bizField.getName()));
				fieldCol.setEditor(new LabelDto());
				lstCols.add(fieldCol);
			}
		}else {
			List<FormFieldDefine> fieldDefines = GpfDCBasicUtil.mergeFormFieldByTableColumnDefines(fieldList, widgetParam.getColumns());
			FormFieldEditorFactory factory = getEditorFactory(getPanelContext().getCurrentPanelGlobalKey(), widgetParam);
			Map<String,FormFieldDefine> fieldMap = fieldDefines.stream().collect(Collectors.toMap(FormFieldDefine::getCode, v->v,(element,replacement) -> replacement));
			for(TableColumnDefine column : widgetParam.getColumns()) {
				FormFieldDefine field = fieldMap.get(column.getCode());
				if(field == null)
					continue;
				DataType display = field.getDataTypeEnum();
				if(display == DataType.RichDocument || display == DataType.NestingModel 
						|| display == DataType.Password || display == DataType.KeyValue)
					continue;
				if(isHiddenField(context, field))
					continue;
				TableColumnDto fieldCol = buildLabelColumn(context,column, field,factory);
//				String label = CmnUtil.isStringEmpty(column.getAlias()) ? field.getName() : column.getAlias();
//				TableColumnDto fieldCol = new TableColumnDto();
//				fieldCol.setName(field.getCode());
//				fieldCol.setLabel(label);
//				if(column.getMinWidth() != null) {
//					fieldCol.setMinWidth(column.getMinWidth());
//				}
//				if(column.getWidth() != null) {
//					fieldCol.setFixWidth(column.getWidth());
//				}
//				if(!CmnUtil.isStringEmpty(column.getAlign())) {
//					TableColumnDecorationDto columnDecoreation = fieldCol.getDecoration();
//					if(columnDecoreation == null) {
//						columnDecoreation = new TableColumnDecorationDto();
//						fieldCol.setDecoration(columnDecoreation);
//					}
//					if(CmnUtil.isStringEqual(column.getAlign(), ColumnAlignType.left.name())) {
//						fieldCol.getDecoration().setHeaderAlign(CLabelAlign.LEFT_CENTER);
//						fieldCol.getDecoration().setContentAlign(CLabelAlign.LEFT_CENTER);
//					}else if(CmnUtil.isStringEqual(column.getAlign(), ColumnAlignType.right.name())) {
//						fieldCol.getDecoration().setHeaderAlign(CLabelAlign.RIGHT_CENTER);
//						fieldCol.getDecoration().setContentAlign(CLabelAlign.RIGHT_CENTER);
//					}else if(CmnUtil.isStringEqual(column.getAlign(), ColumnAlignType.center.name())) {
//						fieldCol.getDecoration().setHeaderAlign(CLabelAlign.CENTER);
//						fieldCol.getDecoration().setContentAlign(CLabelAlign.CENTER);
//					}
//				}
//				fieldCol.setEditor(new LabelDto().setStyleName(field.getComponentStyle()));
				lstCols.add(fieldCol);
			}
			
		}
		TableHeaderDto meta = new TableHeaderDto();
		meta.setColumns(lstCols);

		return meta;
	}
	
	@Override
	public TableRowsDto queryTableRows(TableBuilder builder, TableQuerier querier, TableQuerierContext context) throws Exception {
		List slaveTable = widgetParam.getRows();
		if(CmnUtil.isCollectionEmpty(slaveTable) && isLazyQueryCompoundField()) {
			Tracer tracer = TraceUtil.getCurrentTracer();
			tracer.logStart();
			tracer.debug("查询嵌套表格数据");
			slaveTable = queryNestingTableData(widgetParam.getModelId(), widgetParam.getMasterClass(), widgetParam.getMasterField(), widgetParam.getMasterKey());
			tracer.debugCost("", "嵌套表格数据查询结束");
		}
		TableRowsDto rows = new TableRowsDto();
        List<TableRowDto> listRow = new LinkedList<TableRowDto>();
        if(!CmnUtil.isCollectionEmpty(slaveTable)) {
			Map<String,Map<String,ActionPrivilegeDto>> allRowPrivs = getRowActionPrivilege(context,slaveTable);
        	for(Object cdo : slaveTable) {
        		TableRowDto row = convert2TableRowDto(cdo);
        		//设置行操作按钮是否可见
        		listRow.add(row);
        	}
        }
		rows.setRows(listRow);
        return rows;
	}
	
//	@SuppressWarnings("rawtypes")
//	public void onRowClick(TableRowListener listener, PanelContext panelContext, WidgetDto source) throws Exception {
//		TableRowDto row = listener.getRow();
//		Form data = (Form) getEditObject(row);
//		popupFormPanel(listener, panelContext, source, data);
//	}
	
	@Override
	public FormEditPanelIntf buildEditRowPanel(ListenerDto listener, PanelContext panelContext, WidgetDto source,
			Object rowData, boolean isWriteable) throws Exception {
		Form cdo = (Form) rowData;
		BaseFormEditView editPanel = new BaseFormEditView<>();
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
		return editPanel;
	}
	
	@Override
	public TableRowDto convert2TableRowDto(Object data) throws Exception {
		Form form = (Form) data;
		if(CmnUtil.isStringEmpty(form.getUuid()))
			throw new VerifyException(getI18nString(getPanelContext(),GpfDCBasicI18n.CAN_NOT_BE_NULL,"uuid"));
		TableRowDto row = new TableRowDto();//convert2TableRow(bizField);
		row.setRowId(form.getUuid());
		FormModel model = getCacheFormModel(getPanelContext());
		List<FormField> fieldList = getCacheFormField(getPanelContext());
		List<String>	customCols=new ArrayList<>();
		for (TableColumnDefine cloumn : widgetParam.getColumns()) {
			if(cloumn.isCustom())
				customCols.add(cloumn.getCode());
		}
		if(CmnUtil.isCollectionEmpty(widgetParam.getColumns())) {
			List<String> hiddenFields = new ArrayList<>();
			for(FormField field : model.getFieldList()) {
				if(hiddenFields.contains(field.getCode()))
					continue;
				Object value = form.getAttrValueByCode(field.getCode());
				row.putValue(field.getCode(), buildTableCell(getPanelContext(), form,field, value));
			}
		}else {
//			Map<String,FormField> fieldMap = model.getFieldMap();
			List<FormFieldDefine> fieldDefines = GpfDCBasicUtil.mergeFormFieldByTableColumnDefines(model.getFieldList(), widgetParam.getColumns());
			Map<String,FormFieldDefine> fieldMap = fieldDefines.stream().collect(Collectors.toMap(FormFieldDefine::getCode, v->v,(element,replacement) -> replacement));
			for(TableColumnDefine column : widgetParam.getColumns()) {
				FormFieldDefine field = fieldMap.get(column.getCode());
				if(field == null)
					continue;
				Object value = form.getAttrValueByCode(field.getCode());
				//自定义列把整个form传进去
				if(customCols.contains(field.getCode())&&field.getExtendInfo()!=null) {
					List<FormField> cachePDFFormFields = fieldDefines.stream().filter(v->!CmnUtil.isStringEmpty(v.getCode())).collect(Collectors.toList());
//					List<FormField> cachePDFFormFields = getCacheFormModel(getPanelContext()).getFieldList();
					value= new Pair<>(cachePDFFormFields,form);
//				value = cdo;
				}
				TableCellEditorType editorType = column.getTableCellEditorTypeEnum();
				if(editorType == TableCellEditorType.ReadOnlyEditor) {
					try {
						row.putValue(field.getCode(), getEditorFactory(getPanelContext().getCurrentPanelGlobalKey(),widgetParam).buildEditorTableCell(getPanelContext(),form,field, value));
					}catch (Exception e) {
						PopToast.warning(getPanelContext().getChannel(), "构建单元格["+field.getName()+"]值出错：",ToolUtilities.getFullExceptionStack(e));
						row.putValue(field.getCode(), getEditorFactory(getPanelContext().getCurrentPanelGlobalKey(),widgetParam).buildLabelTableCell(getPanelContext(),form,field, value));
					}
				}else {
					row.putValue(field.getCode(), buildTableCell(getPanelContext(), form, field, value));
				}
			}
		}
		//设置行操作按钮是否可见
		String inheitTmplt = (String) form.getAttrValueByCode(InheritableIntf.InheritTmplt);
		boolean writable = widgetParam.isWritable() && !CmnUtil.isStringEmpty(inheitTmplt);
		if(writable) {
			Map<String,Boolean> operateColumnButtonsVisible = new HashMap<>();
			operateColumnButtonsVisible.put(ROW_DELETE_WIDGET_ID, false);
			row.setOperateColumnButtonsVisible(operateColumnButtonsVisible);
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
			}
		}else {
			//如果未设置行按钮操作权限，默认行操作按钮都可见
			Map<String,Boolean> operateColumnButtonsVisible = new LinkedHashMap<>();
			for(ButtonDefine buttonDef : widgetParam.getRowOperateBar()) {
				operateColumnButtonsVisible.put(ROW_BUTTON_WIDGET_PREFIX+buttonDef.getName(), true);
			}
			row.setOperateColumnButtonsVisible(operateColumnButtonsVisible);
		}
		row.setBinaryData(form);
		return row;
	}
	
	public TableCellDto buildTableCell(PanelContext panelContext,Form form,FormField field,Object value) throws Exception {
		return getEditorFactory(panelContext.getCurrentPanelGlobalKey(),widgetParam).buildLabelTableCell(panelContext,form,field, value);
	}
	
	public FormFieldEditorFactory getEditorFactory(String panelGlobalKey,WidgetParam widgetParam) {
		return FormFieldEditorFactory.create(panelGlobalKey,this,isLazyQueryCompoundField());
	}
	
	@Override
	public List<PairDto<String, String>> getTableHeader() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object newRowObject(ListenerDto listener, PanelContext panelContext, WidgetDto source) throws Exception {
		Form form = new Form();
		form.setFormModelId(widgetParam.getModelId());
		form.setUuid(ToolUtilities.allockUUIDWithUnderline());
		form.setAttrValueByCode(TableData.MasterClass, widgetParam.getMasterClass());
		form.setAttrValueByCode(TableData.MasterKey, widgetParam.getMasterKey());
		form.setAttrValueByCode(TableData.MasterField, widgetParam.getMasterField());
		return form;
	}
	
	@Override
	public boolean isEnablePopupRouter() {
		return false;
	}
	
	@Override
	public String getEditRowPanelTitle() {
		String title = " ";
		return title;
	}
	
	@SuppressWarnings("rawtypes")
	public void onBtnDelete(ListenerDto listener, PanelContext panelContext, WidgetDto source)throws Exception{
		boolean confirm = !isShowConfirmWhenDelete() || PopupPanel.showConfirm(panelContext, "", getI18nString(panelContext,FeI18n.DELETE_CONFIRM_TIPS));
		if (confirm) {
			List<TableRowDto> rows = QueryTableRows.querySelected(panelContext);
			if(!CmnUtil.isCollectionEmpty(rows)) {
				List<String> rowIds = rows.stream().map(v->v.getRowId()).collect(Collectors.toList());
				doDeleteRowData(panelContext, rowIds);
				removeRowsByRowId(panelContext, rowIds);
				fireCommandListener(panelContext, panelContext.getCurrentPanelWidgetId(), CMD_VALUE_CHANGED, null);
			}
		}
	}

	@Override
	public void onRowDelete(ListenerDto listener, PanelContext panelContext, WidgetDto source) throws Exception {
		super.onRowDelete(listener, panelContext, source);
		fireCommandListener(panelContext, panelContext.getCurrentPanelWidgetId(), CMD_VALUE_CHANGED, null);
	}
	
	@Override
	public Object onListener(ListenerDto listener, PanelContext panelContext, WidgetDto source) throws Exception {
		if(listener.isServiceCommand(CMD_VALUE_CHANGED)) {
			FeDeliverData<TableRowDto> feData = (FeDeliverData<TableRowDto>) listener.getBinaryData();
			return feData.getData();
		}else {
			return super.onListener(listener, panelContext, source);
		}
	}
	
	@Override
	public void addOrUpdateRow(PanelContext panelContext,Object data) throws Exception {
		Form form = (Form) data;
		List<TableRowDto> existRows = QueryTableRows.query(panelContext, Arrays.asList(form.getUuid()), "rowId");
		TableRowDto tableRow = convert2TableRowDto(data);
		if(CmnUtil.isCollectionEmpty(existRows)) {
			addRows(panelContext, tableRow);
		}else {
			updateRows(panelContext, tableRow);
		}
		//异步触发值改变监听
		if(false)
			fireCommandListener(panelContext, panelContext.getCurrentPanelWidgetId(), CMD_VALUE_CHANGED, tableRow);
		ToolUtilities.asynCallFunction(this, "fireCommandListener", panelContext, panelContext.getCurrentPanelWidgetId(), CMD_VALUE_CHANGED, tableRow);
	}
	
}
