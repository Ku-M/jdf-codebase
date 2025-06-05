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
import fe.util.component.param.TableParam;
import gpf.adur.action.Action;
import gpf.adur.data.TableData;
import gpf.dc.basic.dto.view.FeEventSubscribeDto;
import gpf.dc.basic.dto.view.TimerConfigDto;
import gpf.dc.basic.fe.enums.ListenerApplyLocation;
import gpf.dc.basic.intf.FormActionIntf;
import gpf.dc.basic.param.view.convertor.WidgetDefineConvertor;
import gpf.dc.basic.param.view.dto.ButtonDefine;
import gpf.dc.basic.param.view.dto.FilterDto;
import gpf.dc.basic.param.view.dto.ListenerDefine;
import gpf.dc.basic.param.view.dto.NodeViewSetting;
import gpf.dc.basic.param.view.dto.SearchBarDefine;
import gpf.dc.basic.param.view.dto.TableColumnDefine;
import gpf.dc.basic.param.view.dto.WidgetDefine;
import gpf.dc.basic.util.GpfDCBasicFeUtil;
import gpf.dc.concrete.PrivilegeSetting;
import gpf.dc.concrete.RefActionConfig;

/**
 * @author chenxb
 *
 */
public class BaseTableViewParam extends TableParam implements ViewBriefInfoIntf{

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
	@Comment("表格列")
	List<TableColumnDefine> cloumns = new ArrayList<>();
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
	@Comment("节点视图配置")
	List<NodeViewSetting> nodeViewSettings = new ArrayList<>();
	@Comment("自定义表单操作接口")
	String customFormActionClass;
	/**
	 * 默认过滤条件
	 */
	SqlExpressionGroup defaultFilter;
	
	@Comment("初始过滤参数")
	FilterDto initFilterDto;
	@Comment("默认排序")
	String defaultOrder;
	@Comment("是否布局模式")
	boolean layoutMode;
	@Comment("附加视图")
	TableData addtionalWidgetTable;
	/**
	 * 查看详情嵌套数据是否在表单加载后再查询
	 */
	boolean isLazyQueryCompoundField = false;

	public boolean isLazyQueryCompoundField() {
		return isLazyQueryCompoundField;
	}
	public BaseTableViewParam setLazyQueryCompoundField(boolean isLazyQueryCompoundField) {
		this.isLazyQueryCompoundField = isLazyQueryCompoundField;
		return this;
	}
	//---------------------------嵌套模型相关参数-----------------------------------------------
	String masterClass;
	String masterKey;
	String masterField;
	public String getMasterClass() {
		return masterClass;
	}
	
	public BaseTableViewParam setMasterClass(String masterClass) {
		this.masterClass = masterClass;
		return this;
	}
	
	public String getMasterKey() {
		return masterKey;
	}
	
	public BaseTableViewParam setMasterKey(String masterKey) {
		this.masterKey = masterKey;
		return this;
	}
	
	public String getMasterField() {
		return masterField;
	}
	
	public BaseTableViewParam setMasterField(String masterField) {
		this.masterField = masterField;
		return this;
	}
	//------------------------------------------------------------------------------------------
	//---------------------
	public BaseTableViewParam() {
		defaultParam();
		getSetting().setIsAllowRefresh(true);
	}
	
	
	String viewModelId;
	String viewCode;
	
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
	
	public List<ListenerDefine> getAfterQueryTableRowListenerDefines(ListenerApplyLocation location){
		return GpfDCBasicFeUtil.getAfterQueryTableRowListenerDefines(listenerDefines,location);
	}

	
	public List<ListenerDefine> getListenerDefinesByApplyLocation(ListenerApplyLocation applyLocation) {
		List<ListenerDefine> filterList = new ArrayList<>();
		for(ListenerDefine lsnr : Nulls.get(listenerDefines)) {
			if(lsnr.getApplyLocationEnums().contains(applyLocation)) {
				filterList.add(lsnr);
			}
		}
		return filterList;
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
	public BaseTableViewParam setEventSubscribes(List<FeEventSubscribeDto> eventSubscribes) {
		this.eventSubscribes = eventSubscribes;
		return this;
	}
	public List<TimerConfigDto> getTimerConfigs() {
		return timerConfigs;
	}
	public BaseTableViewParam setTimerConfigs(List<TimerConfigDto> timerConfigs) {
		this.timerConfigs = timerConfigs;
		return this;
	}

	public List<TableColumnDefine> getColumns() {
		return cloumns;
	}

	public void setCloumns(List<TableColumnDefine> cloumns) {
		this.cloumns = cloumns;
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

	public void setRowOperateBar(List<ButtonDefine> rowOperateBar) {
		this.rowOperateBar = rowOperateBar;
	}
	
	public ButtonDefine getRowOperateBarButtonDefineByName(String name){
		return GpfDCBasicFeUtil.getButtonDefineByName(rowOperateBar, name);
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
	
	public List<RefActionConfig> getDataPrivilegeFuncs() {
		return dataPrivilegeFuncs;
	}
	
	public void setDataPrivilegeFuncs(List<RefActionConfig> dataPrivilegeFuncs) {
		this.dataPrivilegeFuncs = dataPrivilegeFuncs;
	}
	
	public List<PrivilegeSetting> getActionPrivilegeFuncs() {
		return actionPrivilegeFuncs;
	}
	public void setActionPrivilegeFuncs(List<PrivilegeSetting> actionPrivilegeFuncs) {
		this.actionPrivilegeFuncs = actionPrivilegeFuncs;
	}
	
	public List<PrivilegeSetting> getRowActionPrivilegeFuncs() {
		return rowActionPrivilegeFuncs;
	}
	public void setRowActionPrivilegeFuncs(List<PrivilegeSetting> rowActionPrivilegeFuncs) {
		this.rowActionPrivilegeFuncs = rowActionPrivilegeFuncs;
	}
	
	public List<NodeViewSetting> getNodeViewSettings() {
		return nodeViewSettings;
	}
	public void setNodeViewSettings(List<NodeViewSetting> nodeViewSettings) {
		this.nodeViewSettings = nodeViewSettings;
	}
	public Action getViewActionByNode(String nodeKey) throws Exception {
		Action action = null;
		for(NodeViewSetting setting : nodeViewSettings) {
			if(setting.getNodeKeys().contains(nodeKey)) {
				action = setting.getViewAction();
				break;
			}
		}
		if(action == null)
			action = getViewAction();
		return action;
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
	public SqlExpressionGroup getDefaultFilter() {
		return defaultFilter;
	}
	public void setDefaultFilter(SqlExpressionGroup defaultFilter) {
		this.defaultFilter = defaultFilter;
	}
	public FilterDto getInitFilterDto() {
		return initFilterDto;
	}
	public BaseTableViewParam setInitFilterDto(FilterDto initFilterDto) {
		this.initFilterDto = initFilterDto;
		return this;
	}
	public String getDefaultOrder() {
		return defaultOrder;
	}
	public BaseTableViewParam setDefaultOrder(String defaultOrder) {
		this.defaultOrder = defaultOrder;
		return this;
	}
	
	public boolean isLayoutMode() {
		return layoutMode;
	}
	public BaseTableViewParam setLayoutMode(boolean layoutMode) {
		this.layoutMode = layoutMode;
		return this;
	}
	
	public TableData getAddtionalWidgetTable() {
		return addtionalWidgetTable;
	}
	public BaseTableViewParam setAddtionalWidgetTable(TableData addtionalWidgetTable) {
		this.addtionalWidgetTable = addtionalWidgetTable;
		return this;
	}
	public List<WidgetDefine> getAddtionalWidgets() throws Exception{
		return WidgetDefineConvertor.getWidgetDefines(addtionalWidgetTable);
	}
	
//	public FormActionIntf getCustomFormAction() {
//		return customFormAction;
//	}
//	public BaseTableViewParam setCustomFormAction(FormActionIntf customFormAction) {
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
	public BaseTableViewParam setCustomFormActionClass(String customFormActionClass) {
		this.customFormActionClass = customFormActionClass;
		return this;
	}
	
	public final static String ContextKey_GroupVisibleOverride = "$groupVisibleOverride";
	public Map<String, Boolean> getGroupVisibleOverride() {
		return (Map<String, Boolean>) getContext().get(ContextKey_GroupVisibleOverride);
	}
	
	public BaseTableViewParam setGroupVisibleOverride(Map<String, Boolean> groupVisibleOverride) {
		getContext().put(ContextKey_GroupVisibleOverride, groupVisibleOverride);
		return this;
	}
	
	public final static String ContextKey_GroupWritableOverride = "$groupWritableOverride";
	public Map<String, Boolean> getGroupWritableOverride() {
		return (Map<String, Boolean>) getContext().get(ContextKey_GroupWritableOverride);
	}
	
	public BaseTableViewParam setGroupWritableOverride(Map<String, Boolean> groupWritableOverride) {
		getContext().put(ContextKey_GroupWritableOverride, groupWritableOverride);
		return this;
	}
	public final static String ContextKey_GroupRequireOverride = "$groupRequireOverride";
	public Map<String, Boolean> getGroupRequireOverride() {
		return (Map<String, Boolean>) getContext().get(ContextKey_GroupRequireOverride);
	}
	
	public BaseTableViewParam setGroupRequireOverride(Map<String, Boolean> groupRequireOverride) {
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
