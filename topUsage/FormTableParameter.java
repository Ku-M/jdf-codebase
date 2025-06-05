package gpf.dc.basic.param.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.kwaidoo.ms.tool.ToolUtilities;
import com.leavay.ms.tool.CmnUtil;

import cell.gpf.adur.data.IFormMgr;
import cell.gpf.dc.runtime.IDCRuntimeContext;
import cmn.util.NullUtil;
import fe.util.component.dto.TableSetting;
import gpf.adur.data.Form;
import gpf.adur.data.FormField;
import gpf.adur.data.TableData;
import gpf.dc.basic.dto.view.TimerConfigDto;
import gpf.dc.basic.param.view.convertor.ButtonDefineConvertor;
import gpf.dc.basic.param.view.convertor.FunctionConvertor;
import gpf.dc.basic.param.view.convertor.NodeViewSettingConvertor;
import gpf.dc.basic.param.view.convertor.PrivilegeSettingConvertor;
import gpf.dc.basic.param.view.convertor.SearchBarDefineConvertor;
import gpf.dc.basic.param.view.convertor.TableColumnDefineConvetor;
import gpf.dc.basic.param.view.dto.ButtonDefine;
import gpf.dc.basic.param.view.dto.FilterDto;
import gpf.dc.basic.param.view.dto.NodeViewSetting;
import gpf.dc.basic.param.view.dto.SearchBarDefine;
import gpf.dc.basic.param.view.dto.TableColumnDefine;
import gpf.dc.basic.param.view.dto.TableViewSetting;
import gpf.dc.basic.util.GpfDCBasicUtil;
import gpf.dc.concrete.PrivilegeSetting;
import gpf.dc.concrete.RefActionConfig;
import gpf.dc.fe.component.adur.data.field.FormFieldEditorFactory;
import gpf.dc.util.DtoConvertUtil;

public class FormTableParameter extends ViewPageParameter{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1574318772643575640L;
	@Comment("模型ID")
	String modelId;
	@Comment("表格列")
	TableData tableColumns;
	@Comment("工具栏")
	TableData toolBar;
	@Comment("行操作栏")
	TableData rowOperateBar;
	@Comment("搜索栏")
	TableData searchBar;
	@Comment("表格扩展配置")
	List<Map<String,String>> tableSettings;
	@Comment("表单视图模型ID")
	String formViewModelId;
	@Comment("表单视图编号")
	String formViewCode;
	@Comment("数据权限")
	TableData dataPrivileges;
	@Comment("行动作权限")
	TableData rowActoinPrivilegeTable;
	@Comment("默认排序")
	String defaultOrder;
	@Comment("数据过滤")
	TableData dataFilterTable;
	@Comment("自定义查询")
	TableData customQueryTable;
	@Comment("节点视图配置")
	TableData nodeViewTable;
	@Comment("自定义表单操作接口")
	String customFormActionClass;
	@Comment("自定义视图类")
	String customComponentClass;
	
	@Comment("定时器")
	TableData timerConfigTable;
	
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String pdfUuid) {
		this.modelId = pdfUuid;
	}
	public TableData getTableColumns() {
		return tableColumns;
	}
	public void setTableColumns(TableData tableColumns) {
		this.tableColumns = tableColumns;
	}
	public TableData getToolBar() {
		return toolBar;
	}
	public void setToolBar(TableData toolBar) {
		this.toolBar = toolBar;
	}
	public TableData getRowOperateBar() {
		return rowOperateBar;
	}
	public void setRowOperateBar(TableData rowOperateBar) {
		this.rowOperateBar = rowOperateBar;
	}
	public TableData getSearchBar() {
		return searchBar;
	}
	public void setSearchBar(TableData searchBar) {
		this.searchBar = searchBar;
	}
	public String getFormViewModelId() {
		return formViewModelId;
	}
	public void setFormViewModelId(String formViewModelId) {
		this.formViewModelId = formViewModelId;
	}
	public String getFormViewCode() {
		return formViewCode;
	}
	public void setFormViewCode(String formViewCode) {
		this.formViewCode = formViewCode;
	}
	
	public TableData getDataPrivileges() {
		return dataPrivileges;
	}
	public void setDataPrivileges(TableData dataPrivileges) {
		this.dataPrivileges = dataPrivileges;
	}
	public String getDefaultOrder() {
		return defaultOrder;
	}
	public void setDefaultOrder(String defaultOrder) {
		this.defaultOrder = defaultOrder;
	}
	public TableData getDataFilterTable() {
		return dataFilterTable;
	}
	public void setDataFilterTable(TableData dataFilterTable) {
		this.dataFilterTable = dataFilterTable;
	}
	
	public TableData getCustomQueryTable() {
		return customQueryTable;
	}
	
	public void setCustomQueryTable(TableData customQueryTable) {
		this.customQueryTable = customQueryTable;
	}
	
	public TableData getRowActoinPrivilegeTable() {
		return rowActoinPrivilegeTable;
	}
	public void setRowActoinPrivilegeTable(TableData rowActoinPrivilegeTable) {
		this.rowActoinPrivilegeTable = rowActoinPrivilegeTable;
	}
	
	public List<TableColumnDefine> getTableColumDefines() throws Exception{
		return TableColumnDefineConvetor.getTableColumDefines(tableColumns);
	}
	
	public List<ButtonDefine> getToolBarButtonDefines() throws Exception{
		List<ButtonDefine> buttons = getButtonDefines(toolBar);
		return buttons;
	}
	
	public List<ButtonDefine> getRowOperateButtonDefines() throws Exception{
		List<ButtonDefine> buttons = getButtonDefines(rowOperateBar);
		return buttons;
	}
	
	public static List<ButtonDefine> getButtonDefines(TableData tableData) throws Exception{
		return ButtonDefineConvertor.getButtonDefines(tableData);
	}
	
	public List<SearchBarDefine> getSearchBarDefines() throws Exception{
		List<SearchBarDefine> fieldDefs = SearchBarDefineConvertor.getSearchBarDefines(searchBar);
		return fieldDefs;
	}
	
	public List<RefActionConfig> getDataPrivilegeFunctions() throws Exception{
		return FunctionConvertor.getFunctions(dataPrivileges);
	}
	
	public List<RefActionConfig> getDataFilterFunctions() throws Exception{
		return FunctionConvertor.getFunctions(dataFilterTable);
	}
	
	public List<RefActionConfig> getCustomQueryFunctions() throws Exception{
		return FunctionConvertor.getFunctions(customQueryTable);
	}
	
	public List<PrivilegeSetting> getRowActionPrivilegeFunctions() throws Exception{
		return PrivilegeSettingConvertor.getSettings(rowActoinPrivilegeTable);
	}
	
	public TableData getNodeViewTable() {
		return nodeViewTable;
	}
	public void setNodeViewTable(TableData nodeViewTable) {
		this.nodeViewTable = nodeViewTable;
	}
	
	public List<NodeViewSetting> getNodeViewSettinggs() throws Exception{
		return NodeViewSettingConvertor.getNodeViewSettings(nodeViewTable);
	}
	
	public <T extends TableSetting> T getTableSetting(Class<T> settingClazz,T appTableSetting) throws Exception {
		T setting = null;
		if(appTableSetting != null) {
			setting = ToolUtilities.clone(appTableSetting);
		}else {
			setting = settingClazz.newInstance();
		}
//		setting.setAllowCopyRow(true);
		setting = GpfDCBasicUtil.getSetting(setting, tableSettings);
		Boolean showLoadingOnListener = null;
		for(Map<String,String> map : NullUtil.get(tableSettings)) {
			String key = map.get("key");
			String value = map.get("value");
			if(CmnUtil.isStringEqual(key, "showLoadingOnListener")) {
				showLoadingOnListener = CmnUtil.getBoolean(value);
			}
		}
		if(showLoadingOnListener == null) {
			if(IFormMgr.get().isNestingEntityModel(modelId)) {
				showLoadingOnListener = false;
			}else {
				showLoadingOnListener = true;
			}
		}
		((TableViewSetting)setting).setShowLoadingOnListener(showLoadingOnListener);
		return setting;
	}
	
	public String getCustomFormActionClass() {
		return customFormActionClass;
	}
	public FormTableParameter setCustomFormActionClass(String customFormActionClass) {
		this.customFormActionClass = customFormActionClass;
		return this;
	}
	
	public String getCustomComponentClass() {
		return customComponentClass;
	}
	public FormTableParameter setCustomComponentClass(String customComponentClass) {
		this.customComponentClass = customComponentClass;
		return this;
	}
	
	public TableData getTimerConfigTable() {
		return timerConfigTable;
	}
	public FormTableParameter setTimerConfigTable(TableData timerConfigTable) {
		this.timerConfigTable = timerConfigTable;
		return this;
	}
	public List<TimerConfigDto> getTimerConfigs() throws Exception{
		return DtoConvertUtil.convertToDtos(timerConfigTable, TimerConfigDto.class);
	}
	
//	public static void setFieldValue(Object setting,Field f,String value) {
//		try {
//			if(f.getType() == Integer.class || f.getType() == int.class) {
//				ToolUtilities.setFieldValue(setting, f.getName(), CmnUtil.getInteger(value));
//			}else if(f.getType() == Long.class || f.getType() == long.class) {
//				ToolUtilities.setFieldValue(setting, f.getName(), CmnUtil.getLong(value));
//			}else if(f.getType() == Double.class || f.getType() == double.class) {
//				ToolUtilities.setFieldValue(setting, f.getName(), CmnUtil.getDouble(value));
//			}else if(f.getType() == Float.class || f.getType() == float.class) {
//				ToolUtilities.setFieldValue(setting, f.getName(), CmnUtil.getFloat(value));
//			}else if(f.getType() == Boolean.class || f.getType() == boolean.class) {
//				ToolUtilities.setFieldValue(setting, f.getName(), CmnUtil.getBoolean(value));
//			}else {
//				ToolUtilities.setFieldValue(setting, f.getName(), value);
//			}
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	
	public final static String InputParamKey_FormFieldEditorFactory = "$factory";
	public final static String InputParamKey_Field = "$field";
	public final static String InputParamKey_FieldValue = "$fieldValue";
	public final static String InputParamKey_Writable = "$writable";
	
	public List<Form> getRows() throws Exception{
		TableData tableData = (TableData) getRtx().getParam(InputParamKey_FieldValue);
		if(tableData == null) {
			return new ArrayList<>();
		}else {
			return tableData.getRows();
		}
	}
	
	public FormFieldEditorFactory getFactory() throws Exception {
		return (FormFieldEditorFactory) getRtx().getParam(InputParamKey_FormFieldEditorFactory);
	}
	
	public Form getMasterForm() throws Exception {
		return (Form) getRtx().getParam(FormParameter.InputParamKey_MasterForm);
	}
	
	public FormField getFormField() throws Exception {
		return (FormField) getRtx().getParam(InputParamKey_Field);
	}
	
	public boolean isFieldWritable() throws Exception {
		return (boolean) CmnUtil.getBoolean(getRtx().getParam(InputParamKey_Writable),true);
	}
	
	public final static String InputParamKey_ModelId = "$modelId";
	public static void setRuntimeModelId(IDCRuntimeContext rtx,String modelId) throws Exception {
		rtx.setParam(InputParamKey_ModelId, modelId);
	}
	
	public String getRuntimeModelId() throws Exception {
		return (String) getRtx().getParam(InputParamKey_ModelId);
	}
	
	public final static String InputParamKey_DefaultFilter = "$defaultFilter";
	
	public static void setDefaultFilter(IDCRuntimeContext rtx,SqlExpressionGroup expression) throws Exception {
		rtx.setParam(InputParamKey_DefaultFilter, expression);
	}
	
	public SqlExpressionGroup getDefaultFilter() throws Exception {
		return (SqlExpressionGroup) getRtx().getParam(InputParamKey_DefaultFilter);
	}
	
	public final static String InputParamKey_InitFilterDto = "$initFilterDto";
	
	public static void setInitFilterDto(IDCRuntimeContext rtx,FilterDto filterDto) throws Exception {
		rtx.setParam(InputParamKey_InitFilterDto, filterDto);
	}
	
	public FilterDto getInitFilterDto() throws Exception {
		return (FilterDto) getRtx().getParam(InputParamKey_InitFilterDto);
	}
}
