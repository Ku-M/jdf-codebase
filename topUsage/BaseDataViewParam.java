package gpf.dc.basic.fe.component.param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.nutz.dao.entity.annotation.Comment;

import com.leavay.ms.tool.CmnUtil;

import cell.cdao.IDao;
import cell.cdao.IDaoService;
import cell.gpf.adur.action.IActionMgr;
import cmn.util.NullUtil;
import cmn.util.Nulls;
import fe.cmn.data.KeyboardDto;
import fe.cmn.widget.WidgetDto;
import fe.util.component.param.DataEditParam;
import gpf.adur.action.Action;
import gpf.adur.data.Form;
import gpf.dc.basic.dto.view.FeEventSubscribeDto;
import gpf.dc.basic.dto.view.SubmitButtonHookConfigDto;
import gpf.dc.basic.dto.view.TimerConfigDto;
import gpf.dc.basic.fe.enums.ListenerApplyLocation;
import gpf.dc.basic.fe.enums.TriggerTime;
import gpf.dc.basic.field.extend.FormModelViewActionSetting;
import gpf.dc.basic.param.view.dto.BaseWidgetDefine;
import gpf.dc.basic.param.view.dto.ButtonDefine;
import gpf.dc.basic.param.view.dto.FormFieldDefine;
import gpf.dc.basic.param.view.dto.IgnoreRequireSetting;
import gpf.dc.basic.param.view.dto.ListenerDefine;
import gpf.dc.basic.param.view.dto.ViewInitActionDefine;
import gpf.dc.basic.param.view.dto.WidgetDefine;
import gpf.dc.basic.util.GpfDCBasicFeUtil;
import gpf.dc.concrete.PrivilegeSetting;
import gpf.dc.concrete.RefActionConfig;
import gpf.dc.fe.component.param.FormModelSelectorParam;

public class BaseDataViewParam<T> extends DataEditParam<T>  implements ViewBriefInfoIntf{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4837785667484861894L;
	@Comment("模型ID")
	String modelId;
	@Comment("属性列表")
	List<FormFieldDefine> fieldDefines = new ArrayList<>();
	@Comment("初始化动作定义")
	List<ViewInitActionDefine> initActionDefines = new ArrayList<>();
	@Comment("动作定义")
	List<RefActionConfig> actionDefines = new ArrayList<>();
	@Comment("动作监听")
	List<ListenerDefine> listenerDefines = new ArrayList<>();
	@Comment("事件订阅")
	List<FeEventSubscribeDto> eventSubscribes = new ArrayList<>();
	@Comment("组件定义")
	List<WidgetDefine> widgetDefines = new ArrayList<>();
	@Comment("按钮定义")
	List<ButtonDefine> buttonDefines = new ArrayList<>();
	@Comment("基础组件定义")
	@Deprecated
	List<BaseWidgetDefine> baseWidgetDefines = new ArrayList<>();
	@Comment("扩展视图列表")
	List<RefActionConfig> extViewFuncs = new ArrayList<>();
	@Comment("布局")
	WidgetDto layout = null;
	@Comment("动作权限")
	List<PrivilegeSetting> actionPrivileges = new ArrayList<>();
	@Comment("忽略必填")
	List<IgnoreRequireSetting> ignoreRequireSettings = new ArrayList<>();
	
	@Comment("定时器")
	List<TimerConfigDto> timerConfigs = new ArrayList<>();
	@Comment("提交按钮干预配置")
	List<SubmitButtonHookConfigDto> submitButtonHookConfigs = new ArrayList<>();
	@Comment("启用布局模式")
	boolean layoutMode = false;
	
	//---------------------------嵌套模型相关参数-----------------------------------------------
	String masterClass;
	String masterKey;
	String masterField;
	
	public String getMasterClass() {
		return masterClass;
	}

	public BaseDataViewParam<T> setMasterClass(String masterClass) {
		this.masterClass = masterClass;
		return this;
	}

	public String getMasterKey() {
		return masterKey;
	}

	public BaseDataViewParam<T> setMasterKey(String masterKey) {
		this.masterKey = masterKey;
		return this;
	}

	public String getMasterField() {
		return masterField;
	}

	public BaseDataViewParam<T> setMasterField(String masterField) {
		this.masterField = masterField;
		return this;
	}
	//------------------------------------------------------------------------------------------
	//---------------------------动态模型选择相关参数-------------------------------------------
	/**
	 * 模型选择组件参数，动态切换模型时使用
	 */
	FormModelSelectorParam modelSelectParam;
	/**
	 * 动态选择模型时，模型的视图配置参数
	 */
	List<FormModelViewActionSetting> formViewSettings = new ArrayList<>();
	/**
	 * 是否子表单模型
	 */
	boolean isEmbedForm = false;
	//------------------------------------------------------------------------------------------
	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	
	public List<ViewInitActionDefine> getInitActionDefines() {
		return initActionDefines;
	}
	
	public List<ViewInitActionDefine> getInitActionDefines(TriggerTime triggerTime){
		List<ViewInitActionDefine> beforeInitAactionDefs = new ArrayList<>();
		for(ViewInitActionDefine initActionDef : Nulls.get(initActionDefines)) {
			if(CmnUtil.isStringEqual(initActionDef.getTriggerTime(), triggerTime.name())) {
				beforeInitAactionDefs.add(initActionDef);
			}
		}
		return beforeInitAactionDefs;
	}
	
	public void setInitActionDefines(List<ViewInitActionDefine> initActionDefines) {
		this.initActionDefines = initActionDefines;
	}
	
	public List<RefActionConfig> getActionDefines() {
		return actionDefines;
	}
	public void setActionDefines(List<RefActionConfig> actionDefines) {
		this.actionDefines = actionDefines;
	}
	
	public List<ButtonDefine> getButtonDefines() {
		return buttonDefines;
	}
	
	public ButtonDefine getButtonDefineByName(String name){
		return GpfDCBasicFeUtil.getButtonDefineByName(buttonDefines, name);
	}
	/**
	 * 获取第一级的按钮或按钮组
	 * @return
	 */
	public List<ButtonDefine> getRootFormButtons(){
		List<ButtonDefine> buttonGroups = new ArrayList<>();
		for(ButtonDefine button : NullUtil.get(buttonDefines)) {
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
	public List<ButtonDefine> getFormButtonInGroup(String groupName){
		List<ButtonDefine> buttonGroups = new ArrayList<>();
		for(ButtonDefine button : NullUtil.get(buttonDefines)) {
			if(CmnUtil.isStringEqual(button.getBelongGroup(),groupName)) {
				buttonGroups.add(button);
			}
		}
		return buttonGroups;
	}
	public void setButtonDefines(List<ButtonDefine> buttonDefines) {
		this.buttonDefines = buttonDefines;
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

	public void setListenerDefines(List<ListenerDefine> listenerDefines) {
		this.listenerDefines = listenerDefines;
	}
	
	public List<FeEventSubscribeDto> getEventSubscribes() {
		return eventSubscribes;
	}
	@SuppressWarnings("rawtypes")
	public BaseDataViewParam setEventSubscribes(List<FeEventSubscribeDto> eventSubscribes) {
		this.eventSubscribes = eventSubscribes;
		return this;
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

	public List<FormFieldDefine> getFieldDefines() {
		return fieldDefines;
	}

	public void setFieldDefines(List<FormFieldDefine> fieldDefines) {
		this.fieldDefines = fieldDefines;
	}
	
	public List<RefActionConfig> getExtViewFuncs() {
		return extViewFuncs;
	}
	public void setExtViewFuncs(List<RefActionConfig> extViewFuncs) {
		this.extViewFuncs = extViewFuncs;
	}

	public List<BaseWidgetDefine> getBaseWidgetDefines() {
		return baseWidgetDefines;
	}

	public void setBaseWidgetDefines(List<BaseWidgetDefine> baseWidgetDefines) {
		this.baseWidgetDefines = baseWidgetDefines;
	}
	
	public List<PrivilegeSetting> getActionPrivileges() {
		return actionPrivileges;
	}
	
	public void setActionPrivileges(List<PrivilegeSetting> actionPrivileges) {
		this.actionPrivileges = actionPrivileges;
	}
	
	public List<IgnoreRequireSetting> getIgnoreRequireSettings() {
		return ignoreRequireSettings;
	}
	public void setIgnoreRequireSettings(List<IgnoreRequireSetting> ignoreRequireSettings) {
		this.ignoreRequireSettings = ignoreRequireSettings;
	}
	
	public IgnoreRequireSetting getIgnoreRequireSettingByAction(String action) {
		for(IgnoreRequireSetting setting : ignoreRequireSettings) {
			if(setting.getActions().contains(action)) {
				return setting;
			}
		}
		return null;
	}
	
	public IgnoreRequireSetting getIgnoreRequireSettingByAction(String nodeKey,String action) {
		for(IgnoreRequireSetting setting : ignoreRequireSettings) {
			if(CmnUtil.isStringEqual(nodeKey, setting.getNodeKey())) {
				if(setting.getActions().contains(action)) {
					return setting;
				}
			}
		}
		return null;
	}
	public List<TimerConfigDto> getTimerConfigs() {
		return timerConfigs;
	}
	public BaseDataViewParam setTimerConfigs(List<TimerConfigDto> timerConfigs) {
		this.timerConfigs = timerConfigs;
		return this;
	}
	
	public List<SubmitButtonHookConfigDto> getSubmitButtonHookConfigs() {
		return submitButtonHookConfigs;
	}
	public BaseDataViewParam setSubmitButtonHookConfigs(List<SubmitButtonHookConfigDto> submitButtonHookConfigs) {
		this.submitButtonHookConfigs = submitButtonHookConfigs;
		return this;
	}
	public Map<String,SubmitButtonHookConfigDto> getSubmitButtonHookConfigMap(){
		return NullUtil.get(submitButtonHookConfigs).stream().collect(Collectors.toMap(SubmitButtonHookConfigDto::getButton, v->v,(e,r)->e));
	}
	//-----------------------------------------------------------------------------------------------------------------
	public FormModelSelectorParam getModelSelectParam() {
		return modelSelectParam;
	}
	
	public BaseDataViewParam<T> setModelSelectParam(FormModelSelectorParam modelSelectParam) {
		this.modelSelectParam = modelSelectParam;
		return this;
	}
	public List<FormModelViewActionSetting> getFormViewSettings() {
		return formViewSettings;
	}
	public BaseDataViewParam<T> setFormViewSettings(List<FormModelViewActionSetting> formViewSettings) {
		this.formViewSettings = formViewSettings;
		return this;
	}
	public boolean isEmbedForm() {
		return isEmbedForm;
	}
	public BaseDataViewParam<T> setEmbedForm(boolean isEmbedForm) {
		this.isEmbedForm = isEmbedForm;
		return this;
	}
	/**
	 * 查看详情嵌套数据是否在表单加载后再查询
	 */
	boolean isLazyQueryCompoundField = false;

	public boolean isLazyQueryCompoundField() {
		return isLazyQueryCompoundField;
	}
	public BaseDataViewParam<T> setLazyQueryCompoundField(boolean isLazyQueryCompoundField) {
		this.isLazyQueryCompoundField = isLazyQueryCompoundField;
		return this;
	}
	/**
	 * 动态选择模型时，根据模型表单视图配置获取相应的视图配置
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public Action getDynamicFormModelViewAction(Form form) throws Exception {
		if(form == null)
			return null;
		for(FormModelViewActionSetting setting : formViewSettings) {
			if(CmnUtil.isStringEqual(setting.getModelId(), form.getFormModelId())){
				String viewModelId = setting.getViewModelId();
				String viewCode = setting.getViewCode();
				if(!CmnUtil.isStringEmpty(viewModelId) && !CmnUtil.isStringEmpty(viewCode)) {
					try(IDao dao = IDaoService.get().newDao()){
						return IActionMgr.get().queryActionByCode(dao, viewModelId, viewCode);
					}
				}
			}
		}
		return null;
	}
	//-----------------------------------------------------------------------------------------------------------------------
	/**
	 * 属性必填复写设置，当界面属性需要根据内容动态调整必填项时，除了将属性标签重建为带*号标签，还需要修改EditorTypeHandler定义的必填设置，此处重写配合实现
	 */
	Map<String,Boolean> requireOverride;
	
	/**
	 * 属性可见复写设置
	 */
	Map<String,Boolean> visibleOverride;
	/**
	 * 属性可写复写设置
	 */
	Map<String,Boolean> writableOverride;
	/**
	 * 按钮可见复写配置
	 */
	Map<String,Boolean> buttonVisibleOverride;
	
	Map<String,Boolean> buttonWritableOverride;
//	/**
//	 * 分组可见复写配置
//	 */
//	Map<String,Boolean> groupVisibleOverride;
//	/**
//	 * 分组可写复写配置
//	 */
//	Map<String,Boolean> groupWritableOverride;
//	/**
//	 * 分组必填复写配置
//	 */
//	Map<String,Boolean> groupRequireOverride;
	
	public Map<String, Boolean> getRequireOverride() {
		return requireOverride;
	}
	
	public BaseDataViewParam<T> setRequireOverride(Map<String, Boolean> requireOverride) {
		this.requireOverride = requireOverride;
		return this;
	}
	
	public void mergeRequireOverride(Map<String, Boolean> requireOverride) {
		if(this.requireOverride == null) {
			this.requireOverride = new HashMap<>();
		}
		this.requireOverride.putAll(requireOverride);;
	}
	
	public Map<String, Boolean> getVisibleOverride() {
		return visibleOverride;
	}
	
	public BaseDataViewParam<T> setVisibleOverride(Map<String, Boolean> visibleOverride) {
		this.visibleOverride = visibleOverride;
		return this;
	}
	
	public void mergeVisibleOverride(Map<String, Boolean> visibleOverride) {
		if(this.visibleOverride == null) {
			this.visibleOverride = new HashMap<>();
		}
		this.visibleOverride.putAll(visibleOverride);
	}
	
	public Map<String, Boolean> getWritableOverride() {
		return writableOverride;
	}
	
	public BaseDataViewParam<T> setWritableOverride(Map<String, Boolean> writableOverride) {
		this.writableOverride = writableOverride;
		return this;
	}
	public void mergeWritableOverride(Map<String, Boolean> writableOverride) {
		if(this.writableOverride == null) {
			this.writableOverride = new HashMap<>();
		}
		this.writableOverride.putAll(writableOverride);
	}
	public Map<String, Boolean> getButtonVisibleOverride() {
		return buttonVisibleOverride;
	}
	public BaseDataViewParam<T> setButtonVisibleOverride(Map<String, Boolean> buttonVisibleOverride) {
		this.buttonVisibleOverride = buttonVisibleOverride;
		return this;
	}
	public void mergeButtonVisibleOverride(Map<String, Boolean> buttonVisibleOverride) {
		if(this.buttonVisibleOverride == null) {
			this.buttonVisibleOverride = new HashMap<>();
		}
		this.buttonVisibleOverride.putAll(buttonVisibleOverride);
	}
	
	public Map<String, Boolean> getButtonWritableOverride() {
		return buttonWritableOverride;
	}
	public BaseDataViewParam<T> setButtonWritableOverride(Map<String, Boolean> buttonWritableOverride) {
		this.buttonWritableOverride = buttonWritableOverride;
		return this;
	}
	public void mergeButtonWritableOverride(Map<String, Boolean> buttonWritableOverride) {
		if(this.buttonWritableOverride == null) {
			this.buttonWritableOverride = new HashMap<>();
		}
		this.buttonWritableOverride.putAll(buttonWritableOverride);
	}
	
	public final static String ContextKey_GroupVisibleOverride = "$groupVisibleOverride";
	public Map<String, Boolean> getGroupVisibleOverride() {
		return (Map<String, Boolean>) getContext().get(ContextKey_GroupVisibleOverride);
	}
	
	public BaseDataViewParam<T> setGroupVisibleOverride(Map<String, Boolean> groupVisibleOverride) {
		getContext().put(ContextKey_GroupVisibleOverride, groupVisibleOverride);
		return this;
	}
	public void megerGroupVisibleOverride(Map<String, Boolean> visibleOverride) {
		Map<String,Boolean> groupVisibleOverride = getGroupVisibleOverride();
		if(groupVisibleOverride == null) {
			groupVisibleOverride = new HashMap<>();
			setGroupVisibleOverride(groupVisibleOverride);
		}
		groupVisibleOverride.putAll(visibleOverride);
	}
	
	public final static String ContextKey_GroupWritableOverride = "$groupWritableOverride";
	public Map<String, Boolean> getGroupWritableOverride() {
		return (Map<String, Boolean>) getContext().get(ContextKey_GroupWritableOverride);
	}
	
	public BaseDataViewParam<T> setGroupWritableOverride(Map<String, Boolean> groupWritableOverride) {
		getContext().put(ContextKey_GroupWritableOverride, groupWritableOverride);
		return this;
	}
	public void megerGroupWritableOverride(Map<String, Boolean> writableOverride) {
		Map<String,Boolean> groupWritableOverride = getGroupWritableOverride();
		if(groupWritableOverride == null) {
			groupWritableOverride = new HashMap<>();
			setGroupWritableOverride(groupWritableOverride);
		}
		groupWritableOverride.putAll(writableOverride);;
	}
	public final static String ContextKey_GroupRequireOverride = "$groupRequireOverride";
	public Map<String, Boolean> getGroupRequireOverride() {
		return (Map<String, Boolean>) getContext().get(ContextKey_GroupRequireOverride);
	}
	
	public BaseDataViewParam<T> setGroupRequireOverride(Map<String, Boolean> groupRequireOverride) {
		getContext().put(ContextKey_GroupRequireOverride, groupRequireOverride);
		return this;
	}
	public void megerGroupRequireOverride(Map<String, Boolean> requireOverride) {
		Map<String,Boolean> groupRequireOverride = getGroupRequireOverride();
		if(groupRequireOverride == null) {
			groupRequireOverride = new HashMap<>();
			setGroupRequireOverride(groupRequireOverride);
		}
		groupRequireOverride.putAll(requireOverride);
	}
	
	public boolean isLayoutMode() {
		return layoutMode;
	}
	public void setLayoutMode(boolean layoutMode) {
		this.layoutMode = layoutMode;
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
