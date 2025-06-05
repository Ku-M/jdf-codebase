package gpf.dc.basic.param.view;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.nutz.dao.entity.annotation.Comment;

import com.kwaidoo.ms.tool.ToolUtilities;
import com.leavay.ms.tool.CmnUtil;

import cell.gpf.dc.runtime.IDCRuntimeContext;
import fe.cmn.panel.PanelContext;
import fe.cmn.widget.ListenerDto;
import fe.util.component.Component;
import fe.util.component.dto.FeCmnEvent;
import fe.util.component.dto.FormSetting;
import gpf.adur.data.Form;
import gpf.adur.data.FormField;
import gpf.adur.data.TableData;
import gpf.dc.basic.dto.view.SubmitButtonHookConfigDto;
import gpf.dc.basic.dto.view.TimerConfigDto;
import gpf.dc.basic.field.extend.FormModelViewActionSetting;
import gpf.dc.basic.param.view.convertor.ButtonDefineConvertor;
import gpf.dc.basic.param.view.convertor.FormFieldDefineConvertor;
import gpf.dc.basic.param.view.convertor.FunctionConvertor;
import gpf.dc.basic.param.view.convertor.IgnoreRequireSettingConvertor;
import gpf.dc.basic.param.view.convertor.ViewInitActionDefineConvertor;
import gpf.dc.basic.param.view.dto.ButtonDefine;
import gpf.dc.basic.param.view.dto.FormFieldDefine;
import gpf.dc.basic.param.view.dto.IgnoreRequireSetting;
import gpf.dc.basic.param.view.dto.ViewInitActionDefine;
import gpf.dc.basic.util.GpfDCBasicUtil;
import gpf.dc.concrete.RefActionConfig;
import gpf.dc.fe.component.adur.data.field.FormFieldEditorFactory;
import gpf.dc.fe.component.param.FormModelSelectorParam;
import gpf.dc.util.DtoConvertUtil;

public class FormParameter extends ViewPageParameter{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1574318772643575640L;
//	public final static String InputParamKey_Data = "$data";
	@Comment("模型ID")
	String modelId;
	@Comment("表单属性")
	TableData formFields;
	@Comment("附加组件列表")
	TableData extViewTable;
	@Comment("表单扩展配置")
	List<Map<String,String>> formSettings;
	String customViewClass;
	@Comment("视图初始化动作")
	TableData viewInitActionTable;
	@Comment("按钮定义")
	TableData buttonTable;
	@Comment("忽略必填")
	TableData ignoreRequireTable;
	@Comment("定时器")
	TableData timerConfigTable;
	@Comment("提交按钮干预配置")
	TableData submitButtonHookConfigTable;
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public TableData getFormFields() {
		return formFields;
	}
	public void setFormFields(TableData formFields) {
		this.formFields = formFields;
	}
	public TableData getViewInitActionTable() {
		return viewInitActionTable;
	}
	public void setViewInitActionTable(TableData viewInitActionTable) {
		this.viewInitActionTable = viewInitActionTable;
	}
	
	public List<ViewInitActionDefine> getViewInitActionDefine() throws Exception {
		return ViewInitActionDefineConvertor.getViewInitActionDefine(viewInitActionTable);
	}
	
	public TableData getExtViewTable() {
		return extViewTable;
	}
	public void setExtViewTable(TableData extViewTable) {
		this.extViewTable = extViewTable;
	}
	
	public List<RefActionConfig> getExtViewFunctions() throws Exception {
		return FunctionConvertor.getFunctions(extViewTable);
	}
	
	public String getCustomViewClass() {
		return customViewClass;
	}
	public void setCustomViewClass(String customViewClass) {
		this.customViewClass = customViewClass;
	}
	
	public Object getData() throws Exception {
		return getRtx().getParam(FeActionParameter_Data);
//		return getRtx().getInteractiveForm();
	}
	
	public List<FormFieldDefine> getFormFieldDefines() throws Exception{
		return FormFieldDefineConvertor.getFormFieldDefines(formFields);
	}
	
	public TableData getButtonTable() {
		return buttonTable;
	}
	public void setButtonTable(TableData buttonTable) {
		this.buttonTable = buttonTable;
	}
	public List<ButtonDefine> getButtonDefines() throws Exception{
		return ButtonDefineConvertor.getButtonDefines(buttonTable);
	}
	
	public TableData getIgnoreRequireTable() {
		return ignoreRequireTable;
	}
	public void setIgnoreRequireTable(TableData ignoreRequireTable) {
		this.ignoreRequireTable = ignoreRequireTable;
	}
	
	public List<IgnoreRequireSetting> getIgnoreRequireSettings() throws Exception{
		return IgnoreRequireSettingConvertor.getSettings(ignoreRequireTable);
	}
	
	public TableData getTimerConfigTable() {
		return timerConfigTable;
	}
	public FormParameter setTimerConfigTable(TableData timerConfigTable) {
		this.timerConfigTable = timerConfigTable;
		return this;
	}
	public List<TimerConfigDto> getTimerConfigs() throws Exception{
		return DtoConvertUtil.convertToDtos(timerConfigTable, TimerConfigDto.class);
	}
	public TableData getSubmitButtonHookConfigTable() {
		return submitButtonHookConfigTable;
	}
	public FormParameter setSubmitButtonHookConfigTable(TableData submitButtonHookConfigTable) {
		this.submitButtonHookConfigTable = submitButtonHookConfigTable;
		return this;
	}
	
	public List<SubmitButtonHookConfigDto> getSubmitButtonHookConfigs() throws Exception{
		return DtoConvertUtil.convertToDtos(submitButtonHookConfigTable, SubmitButtonHookConfigDto.class);
	}
	
	public <T extends FormSetting> T getFormSetting(Class<T> settingClazz,T appFormSetting) throws Exception {
		T setting = null;
		if(appFormSetting != null) {
			setting = ToolUtilities.clone(appFormSetting);
		}else {
			setting = settingClazz.newInstance();
		}
		return GpfDCBasicUtil.getSetting(setting, formSettings);
//		T setting = settingClazz.newInstance();
//		Map<String,Field> fieldMap = ToolUtilities.getAllFieldMap(settingClazz, false);
//		for(Map<String,Object> settingRow : NullUtil.get(formSettings)) {
//			String key = (String) settingRow.get("key");
//			String value = (String) settingRow.get("value");
//			if(fieldMap.containsKey(key)) {
//				Field f = fieldMap.get(key);
//				setFieldValue(setting, f, value);
//			}
//		}
//		return setting;
	}
	
	public static void setFieldValue(Object setting,Field f,String value) {
		try {
			if(f.getType() == Integer.class || f.getType() == int.class) {
				ToolUtilities.setFieldValue(setting, f.getName(), CmnUtil.getInteger(value));
			}else if(f.getType() == Long.class || f.getType() == long.class) {
				ToolUtilities.setFieldValue(setting, f.getName(), CmnUtil.getLong(value));
			}else if(f.getType() == Double.class || f.getType() == double.class) {
				ToolUtilities.setFieldValue(setting, f.getName(), CmnUtil.getDouble(value));
			}else if(f.getType() == Float.class || f.getType() == float.class) {
				ToolUtilities.setFieldValue(setting, f.getName(), CmnUtil.getFloat(value));
			}else if(f.getType() == Boolean.class || f.getType() == boolean.class) {
				ToolUtilities.setFieldValue(setting, f.getName(), CmnUtil.getBoolean(value));
			}else {
				ToolUtilities.setFieldValue(setting, f.getName(), value);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setData(IDCRuntimeContext rtx,Object data) throws Exception {
		rtx.setParam(FeActionParameter_Data, data);
//		rtx.setInteractiveForm((Form)data);
	}
	public final static String FeActionParameter_Data = "$sysvar_data";
	public static void prepareFeActionParameter(IDCRuntimeContext rtx,PanelContext panelContext,ListenerDto listener,Component currComponent,Object data) throws Exception {
		prepareFeActionParameter(rtx, panelContext, listener, currComponent);
		rtx.setParam(FeActionParameter_Data, data);
//		rtx.setInteractiveForm((Form)data);
	}
	
	public final static String FeActionParameter_IsLazyQueryCompoundField = "$sysvar_isLazyQueryCompoundField";
	public boolean isLazyQueryCompoundField() throws Exception {
		return CmnUtil.getBoolean(getRtx().getParam(FeActionParameter_IsLazyQueryCompoundField),false);
	}
	public static void setLazyQueryCompoundField(IDCRuntimeContext rtx,boolean isLazyQueryCompoundField) throws Exception {
		rtx.setParam(FeActionParameter_IsLazyQueryCompoundField, isLazyQueryCompoundField);
	}
	
	public static void prepareFeActionParameter(IDCRuntimeContext rtx,PanelContext panelContext,FeCmnEvent event,Component currComponent,Object data) throws Exception {
		prepareFeActionParameter(rtx, panelContext, event, currComponent);
		rtx.setParam(FeActionParameter_Data, data);
//		rtx.setInteractiveForm((Form)data);
	}
	public final static String InputParamKey_FormFieldEditorFactory = "$factory";
	public final static String InputParamKey_MasterForm = "$masterForm";
	public final static String InputParamKey_Field = "$field";
	public final static String InputParamKey_FieldValue = "$fieldValue";
	public final static String InputParamKey_Writable = "$writable";
	
	public static void prepareNestingFormParameter(IDCRuntimeContext rtx,FormFieldEditorFactory factory,FormField fieldDef,Object form,Object fieldValue,boolean writable) throws Exception {
		rtx.setParam(InputParamKey_FormFieldEditorFactory, factory);
		rtx.setParam(InputParamKey_Field, fieldDef);
		rtx.setParam(InputParamKey_MasterForm, form);
		rtx.setParam(InputParamKey_FieldValue, fieldValue);
		rtx.setParam(InputParamKey_Writable, writable);
	}
	
	public Form getMasterForm() throws Exception {
		return (Form) getRtx().getParam(FormParameter.InputParamKey_MasterForm);
	}
	
	public Form getRowObject() throws Exception{
		TableData tableData = (TableData) getRtx().getParam(InputParamKey_FieldValue);
		if(tableData == null || CmnUtil.isCollectionEmpty(tableData.getRows())) {
			if(isLazyQueryCompoundField()) {
				return null;
			}else {
				return new Form(getFormField().getTableFormModel());
			}
		}else {
			
			return tableData.getRows().get(0);
		}
	}
	
//	public FormFieldEditorFactory getFactory() throws Exception {
//		return (FormFieldEditorFactory) getRtx().getParam(InputParamKey_FormFieldEditorFactory);
//	}
	
	public FormField getFormField() throws Exception {
		return (FormField) getRtx().getParam(InputParamKey_Field);
	}
	
	public boolean isFieldWritable() throws Exception {
		return (boolean) CmnUtil.getBoolean(getRtx().getParam(InputParamKey_Writable),true);
	}
	public static void setWritable(IDCRuntimeContext rtx,boolean isWritable) throws Exception {
		rtx.setParam(InputParamKey_Writable, isWritable);
	}
	
	public final static String FeActionParameter_InitDataValue = "sysvar_initDataValue";
	public static void setInitDataValue(IDCRuntimeContext rtx,boolean initDataValue) throws Exception {
		rtx.setParam(FeActionParameter_InitDataValue, initDataValue);
	}
	
	public boolean isInitDataValue() throws Exception {
		return CmnUtil.getBoolean(getRtx().getParam(FeActionParameter_InitDataValue), false);
	}
	
	//-----------------------------------------应用在动态模型选择表单的上下文参数-------------------------------
	public final static String FeActionParameter_FormModelSelectorParam = "formModelSelectorParam";
	public static void setFormModelSelectorParam(IDCRuntimeContext rtx,FormModelSelectorParam modelSelectorParam) throws Exception {
		rtx.setParam(FeActionParameter_FormModelSelectorParam, modelSelectorParam);
	}
	
	public FormModelSelectorParam getFormModelSelectorParam() throws Exception {
		return (FormModelSelectorParam) getRtx().getParam(FeActionParameter_FormModelSelectorParam);
	}
	
	public final static String FeActionParameter_DynamicFormModelViewActionSettings = "dynamicFormModelViewActionSettings";
	public static void setDynamicFormModelViewActionSettings(IDCRuntimeContext rtx,List<FormModelViewActionSetting> formViewSettings) throws Exception {
		rtx.setParam(FeActionParameter_DynamicFormModelViewActionSettings, formViewSettings);
	}
	
	public List<FormModelViewActionSetting> getDynamicFormModelViewActionSettings() throws Exception {
		return (List<FormModelViewActionSetting>) getRtx().getParam(FeActionParameter_DynamicFormModelViewActionSettings);
	}
	public final static String FeActionParameter_IsEmbedForm = "isEmbedForm";
	public static void setEmbedForm(IDCRuntimeContext rtx,boolean isEmbedForm) throws Exception {
		rtx.setParam(FeActionParameter_IsEmbedForm, isEmbedForm);
	}
	
	public boolean isEmbedForm() throws Exception {
		return CmnUtil.getBoolean(getRtx().getParam(FeActionParameter_IsEmbedForm),false);
	}
	//----------------------------------------------------------------------------------------------------------
}
