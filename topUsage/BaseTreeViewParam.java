package gpf.dc.basic.fe.component.param;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import com.leavay.common.util.javac.ClassFactory;
import com.leavay.ms.tool.CmnUtil;

import bap.cells.Cells;
import cell.CellIntf;
import cell.cdao.IDao;
import cell.cdao.IDaoService;
import cell.gpf.adur.action.IActionMgr;
import cmn.util.NullUtil;
import cmn.util.Nulls;
import fe.cmn.data.KeyboardDto;
import fe.cmn.widget.WidgetDto;
import fe.util.component.param.TreeParam;
import gpf.adur.action.Action;
import gpf.dc.basic.dto.view.FeEventSubscribeDto;
import gpf.dc.basic.dto.view.TimerConfigDto;
import gpf.dc.basic.fe.enums.ListenerApplyLocation;
import gpf.dc.basic.i18n.GpfDCBasicI18n;
import gpf.dc.basic.intf.FormActionIntf;
import gpf.dc.basic.param.view.dto.ButtonDefine;
import gpf.dc.basic.param.view.dto.ListenerDefine;
import gpf.dc.basic.param.view.dto.SearchBarDefine;
import gpf.dc.basic.param.view.dto.TreeNodeTypeSettingDto;
import gpf.dc.basic.param.view.dto.TreeViewSetting;
import gpf.dc.basic.param.view.dto.WidgetDefine;
import gpf.dc.basic.util.GpfDCBasicFeUtil;
import gpf.dc.concrete.PrivilegeSetting;
import gpf.dc.concrete.RefActionConfig;

public class BaseTreeViewParam extends TreeParam implements ViewBriefInfoIntf{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7878431922220275145L;
	@Comment("模型ID")
	String modelId;
	@Comment("动作定义")
	List<RefActionConfig> actionDefines = new ArrayList<>();
	@Comment("动作监听")
	List<ListenerDefine> listenerDefines = new ArrayList<>();
	@Comment("事件订阅")
	List<FeEventSubscribeDto> eventSubscribes = new ArrayList<>();
	@Comment("定时器")
	List<TimerConfigDto> timerConfigs = new ArrayList<>();
	@Comment("组件定义")
	List<WidgetDefine> widgetDefines = new ArrayList<>();
	@Comment("布局")
	WidgetDto layout = null;
	@Comment("工具栏")
	List<ButtonDefine> toolbar = new ArrayList<>();
	@Comment("行操作按钮")
	List<ButtonDefine> rowOperateBar = new ArrayList<>();
	@Comment("搜索栏")
	List<SearchBarDefine> searchBar = new ArrayList<>();
	@Comment("数据过滤函数")
	List<RefActionConfig> dataFilterFuncs = new ArrayList<>();
	@Comment("自定义过滤函数")
	List<RefActionConfig> customQueryFuncs = new ArrayList<>();
	@Comment("数据权限函数")
	List<RefActionConfig> dataPrivilegeFuncs = new ArrayList<>();
	@Comment("动作权限函数")
	List<PrivilegeSetting> actionPrivilegeFuncs = new ArrayList<>();
	@Comment("行动作权限函数")
	List<PrivilegeSetting> rowActionPrivilegeFuncs = new ArrayList<>();
//	@Comment("父节点属性名")
//	String parentKeyFieldName = "父节点";
//	@Comment("节点类型属性名")
//	String nodeTypeFieldName = "类型";
	@Comment("树面板自定义配置参数")
	TreeViewSetting setting = new TreeViewSetting();
	@Comment("树节点类型配置参数")
	List<TreeNodeTypeSettingDto> nodeTypeSettings;
	@Comment("只读节点Key")
	List<String> readOnlyNodeKeys;
	/**
	 * 默认过滤条件
	 */
	SqlExpressionGroup defaultFilter;
	
	String viewModelId;
	String viewCode;
	
	@Comment("自定义表单操作接口")
//	FormActionIntf customFormAction;
	String customFormActionClass;
	
	boolean layoutMode;
	
	/**
	 * 查看详情嵌套数据是否在表单加载后再查询
	 */
	boolean isLazyQueryCompoundField = false;

	public boolean isLazyQueryCompoundField() {
		return isLazyQueryCompoundField;
	}
	public BaseTreeViewParam setLazyQueryCompoundField(boolean isLazyQueryCompoundField) {
		this.isLazyQueryCompoundField = isLazyQueryCompoundField;
		return this;
	}
	
	public BaseTreeViewParam() {
		
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	
	public List<RefActionConfig> getActionDefines() {
		return actionDefines;
	}
	public void setActionDefines(List<RefActionConfig> actionDefines) {
		this.actionDefines = actionDefines;
	}
	
	public Map<String,RefActionConfig> getActionDefineMap(){
		Map<String,RefActionConfig> map = new LinkedHashMap<String, RefActionConfig>();
		for(RefActionConfig func : Nulls.get(actionDefines)) {
			map.put(func.getName(), func);
		}
		return map;
	}

	public List<ListenerDefine> getListenerDefines() {
		return listenerDefines;
	}

	public void setListenerDefines(List<ListenerDefine> listenerDefines) {
		this.listenerDefines = listenerDefines;
	}
	public List<ListenerDefine> getViewInitListenerDefines(ListenerApplyLocation location){
		return GpfDCBasicFeUtil.getViewInitListenerDefines(listenerDefines,location);
	}
	
	public List<ListenerDefine> getViewInitedListenerDefines(ListenerApplyLocation location){
		return GpfDCBasicFeUtil.getViewInitedListenerDefines(listenerDefines,location);
	}
	public ListenerDefine getListenerDefineByCommand(String command) {
		ListenerDefine matchLsnr = null;
		for(ListenerDefine lsnr : Nulls.get(listenerDefines)) {
			if(CmnUtil.isStringEqual(lsnr.getResponseCommand(), command)) {
				matchLsnr = lsnr;
				break;
			}
		}
		return matchLsnr;
	}
	
	public ListenerDefine getListenerDefineByKeyboard(KeyboardDto keyboard) {
		return GpfDCBasicFeUtil.getListenerDefineByKeyboard(listenerDefines, keyboard);
	}
	
	public ListenerDefine getListenerDefineBySourceWidgetId(String sourceWidgetId) {
		ListenerDefine matchLsnr = null;
		for(ListenerDefine lsnr : Nulls.get(listenerDefines)) {
			if(CmnUtil.isStringEqual(lsnr.getSourceWidgetId(), sourceWidgetId)) {
				matchLsnr = lsnr;
				break;
			}
		}
		return matchLsnr;
	}
	public List<FeEventSubscribeDto> getEventSubscribes() {
		return eventSubscribes;
	}
	public BaseTreeViewParam setEventSubscribes(List<FeEventSubscribeDto> eventSubscribes) {
		this.eventSubscribes = eventSubscribes;
		return this;
	}
	public List<TimerConfigDto> getTimerConfigs() {
		return timerConfigs;
	}
	public BaseTreeViewParam setTimerConfigs(List<TimerConfigDto> timerConfigs) {
		this.timerConfigs = timerConfigs;
		return this;
	}
	public List<ButtonDefine> getToolbar() {
		return toolbar;
	}
	public ButtonDefine getToolbarButtonDefineByName(String name){
		return GpfDCBasicFeUtil.getButtonDefineByName(toolbar, name);
	}
	/**
	 * 获取第一级的按钮或按钮组
	 * @return
	 */
	public List<ButtonDefine> getRootToolButtons(){
		List<ButtonDefine> buttonGroups = new ArrayList<>();
		for(ButtonDefine button : NullUtil.get(toolbar)) {
			if(CmnUtil.isStringEmpty(button.getBelongGroup())) {
				buttonGroups.add(button);
			}
		}
		return buttonGroups;
	}
	/**
	 * 获取按钮组下的按钮
	 * @param groupName
	 * @return
	 */
	public List<ButtonDefine> getToolButtonInGroup(String groupName){
		List<ButtonDefine> buttonGroups = new ArrayList<>();
		for(ButtonDefine button : NullUtil.get(toolbar)) {
			if(CmnUtil.isStringEqual(button.getBelongGroup(),groupName)) {
				buttonGroups.add(button);
			}
		}
		return buttonGroups;
	}

	public void setToolbar(List<ButtonDefine> toolbar) {
		this.toolbar = toolbar;
	}

	public List<ButtonDefine> getRowOperateBar() {
		return rowOperateBar;
	}
	
	/**
	 * 获取第一级的按钮或按钮组
	 * @return
	 */
	public List<ButtonDefine> getRootRowOperateButtons(){
		List<ButtonDefine> buttonGroups = new ArrayList<>();
		for(ButtonDefine button : NullUtil.get(rowOperateBar)) {
			if(CmnUtil.isStringEmpty(button.getBelongGroup())) {
				buttonGroups.add(button);
			}
		}
		return buttonGroups;
	}
	/**
	 * 获取按钮组下的按钮
	 * @param groupName
	 * @return
	 */
	public List<ButtonDefine> getRowOperateButtonInGroup(String groupName){
		List<ButtonDefine> buttonGroups = new ArrayList<>();
		for(ButtonDefine button : NullUtil.get(rowOperateBar)) {
			if(CmnUtil.isStringEqual(button.getBelongGroup(),groupName)) {
				buttonGroups.add(button);
			}
		}
		return buttonGroups;
	}
	
	public ButtonDefine getRowOperateBarButtonDefineByName(String name){
		return GpfDCBasicFeUtil.getButtonDefineByName(rowOperateBar, name);
	}

	public void setRowOperateBar(List<ButtonDefine> rowOperateBar) {
		this.rowOperateBar = rowOperateBar;
	}

	public List<SearchBarDefine> getSearchBar() {
		return searchBar;
	}

	public void setSearchBar(List<SearchBarDefine> searchBar) {
		this.searchBar = searchBar;
	}

	public List<WidgetDefine> getWidgetDefines() {
		return widgetDefines;
	}

	public void setWidgetDefines(List<WidgetDefine> widgetDefines) {
		this.widgetDefines = widgetDefines;
	}

	public WidgetDto getLayout() {
		return layout;
	}

	public void setLayout(WidgetDto layout) {
		this.layout = layout;
	}
	public String getViewModelId() {
		return viewModelId;
	}
	public void setViewModelId(String viewModelId) {
		this.viewModelId = viewModelId;
	}
	public String getViewCode() {
		return viewCode;
	}
	public void setViewCode(String viewCode) {
		this.viewCode = viewCode;
	}
	
	public Action getViewAction() throws Exception {
		if(!CmnUtil.isStringEmpty(viewModelId) && !CmnUtil.isStringEmpty(viewCode)) {
			try(IDao dao = IDaoService.get().newDao()){
				return IActionMgr.get().queryActionByCode(dao, viewModelId, viewCode);
			}
		}
		return null;
	}
	
	public List<TreeNodeTypeSettingDto> getNodeTypeSettings() {
		return nodeTypeSettings;
	}
	
	public void setNodeTypeSettings(List<TreeNodeTypeSettingDto> nodeTypeSettings) {
		this.nodeTypeSettings = nodeTypeSettings;
	}
	
	public TreeNodeTypeSettingDto getNodeTypeSetting(String nodeType) {
		if(CmnUtil.isStringEmpty(nodeType)) {
			nodeType = GpfDCBasicI18n.Default;
		}
		for(TreeNodeTypeSettingDto setting : NullUtil.get(nodeTypeSettings)) {
			if(CmnUtil.isStringEqual(setting.getNodeType(), nodeType)) {
				return setting;
			}
		}
		return null;
	}
	

//	public String getParentKeyFieldName() {
//		return parentKeyFieldName;
//	}
//
//	public void setParentKeyFieldName(String parentKeyFieldName) {
//		this.parentKeyFieldName = parentKeyFieldName;
//	}
//
//	public String getNodeTypeFieldName() {
//		return nodeTypeFieldName;
//	}
//
//	public void setNodeTypeFieldName(String nodeTypeFieldName) {
//		this.nodeTypeFieldName = nodeTypeFieldName;
//	}
	
	public List<RefActionConfig> getDataPrivilegeFuncs() {
		return dataPrivilegeFuncs;
	}
	public void setDataPrivilegeFuncs(List<RefActionConfig> dataPrivilegeFuncs) {
		this.dataPrivilegeFuncs = dataPrivilegeFuncs;
	}
	
	public List<RefActionConfig> getDataFilterFuncs() {
		return dataFilterFuncs;
	}
	
	public void setDataFilterFuncs(List<RefActionConfig> dataFilterFuncs) {
		this.dataFilterFuncs = dataFilterFuncs;
	}
	public List<RefActionConfig> getCustomQueryFuncs() {
		return customQueryFuncs;
	}
	public void setCustomQueryFuncs(List<RefActionConfig> customQueryFuncs) {
		this.customQueryFuncs = customQueryFuncs;
	}
	
	public List<PrivilegeSetting> getRowActionPrivilegeFuncs() {
		return rowActionPrivilegeFuncs;
	}
	public void setRowActionPrivilegeFuncs(List<PrivilegeSetting> rowActionPrivilegeFuncs) {
		this.rowActionPrivilegeFuncs = rowActionPrivilegeFuncs;
	}
	public List<PrivilegeSetting> getActionPrivilegeFuncs() {
		return actionPrivilegeFuncs;
	}
	public void setActionPrivilegeFuncs(List<PrivilegeSetting> actionPrivilegeFuncs) {
		this.actionPrivilegeFuncs = actionPrivilegeFuncs;
	}
	public SqlExpressionGroup getDefaultFilter() {
		return defaultFilter;
	}
	public void setDefaultFilter(SqlExpressionGroup defaultFilter) {
		this.defaultFilter = defaultFilter;
	}
	
	public TreeViewSetting getSetting() {
		return setting;
	}
	public void setSetting(TreeViewSetting setting) {
		this.setting = setting;
	}
	public List<String> getReadOnlyNodeKeys() {
		return readOnlyNodeKeys;
	}
	public void setReadOnlyNodeKeys(List<String> readOnlyNodeKeys) {
		this.readOnlyNodeKeys = readOnlyNodeKeys;
	}
	
//	public FormActionIntf getCustomFormAction() {
//		param.setCustomFormActionClass(input.getCustomFormActionClass());	
//		return customFormAction;
//	}
//	public BaseTreeViewParam setCustomFormAction(FormActionIntf customFormAction) {
//		this.customFormAction = customFormAction;
//		return this;
//	}
	public FormActionIntf getCustomFormAction() throws Exception {
		if(CmnUtil.isStringEmpty(customFormActionClass))
			return null;
		Class clazz = ClassFactory.getValidClassLoader().loadClass(customFormActionClass);
		FormActionIntf customFormAction = null;
		if(CellIntf.class.isAssignableFrom(clazz)) {
			customFormAction = (FormActionIntf) Cells.get(clazz);
		}else {
			customFormAction = (FormActionIntf) clazz.newInstance();
		}
		return customFormAction;
	}
	public String getCustomFormActionClass() {
		return customFormActionClass;
	}
	public BaseTreeViewParam setCustomFormActionClass(String customFormActionClass) {
		this.customFormActionClass = customFormActionClass;
		return this;
	}
	
	public boolean isLayoutMode() {
		return layoutMode;
	}
	public BaseTreeViewParam setLayoutMode(boolean layoutMode) {
		this.layoutMode = layoutMode;
		return this;
	}
	
	
	public final static String ContextKey_GroupVisibleOverride = "$groupVisibleOverride";
	public Map<String, Boolean> getGroupVisibleOverride() {
		return (Map<String, Boolean>) getContext().get(ContextKey_GroupVisibleOverride);
	}
	
	public BaseTreeViewParam setGroupVisibleOverride(Map<String, Boolean> groupVisibleOverride) {
		getContext().put(ContextKey_GroupVisibleOverride, groupVisibleOverride);
		return this;
	}
	
	public final static String ContextKey_GroupWritableOverride = "$groupWritableOverride";
	public Map<String, Boolean> getGroupWritableOverride() {
		return (Map<String, Boolean>) getContext().get(ContextKey_GroupWritableOverride);
	}
	
	public BaseTreeViewParam setGroupWritableOverride(Map<String, Boolean> groupWritableOverride) {
		getContext().put(ContextKey_GroupWritableOverride, groupWritableOverride);
		return this;
	}
	public final static String ContextKey_GroupRequireOverride = "$groupRequireOverride";
	public Map<String, Boolean> getGroupRequireOverride() {
		return (Map<String, Boolean>) getContext().get(ContextKey_GroupRequireOverride);
	}
	
	public BaseTreeViewParam setGroupRequireOverride(Map<String, Boolean> groupRequireOverride) {
		getContext().put(ContextKey_GroupRequireOverride, groupRequireOverride);
		return this;
	}
	
	ViewBriefInfo briefInfo;
	@Override
	public ViewBriefInfo getViewBriefInfo() {
		return briefInfo;
	}

	@Override
	public void setViewBriefInfo(ViewBriefInfo buildInfo) {
		this.briefInfo = buildInfo;
	}
}
