package gpf.dc.basic.param.view.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kwaidoo.ms.tool.CmnUtil;

import cell.cdao.IDao;
import cell.cdao.IDaoService;
import cell.gpf.adur.action.IActionMgr;
import cell.gpf.adur.data.IFormMgr;
import cell.gpf.dc.config.IPDFMgr;
import cmn.util.NullUtil;
import cmn.util.Nulls;
import gpf.adur.action.Action;
import gpf.adur.data.AttachData;
import gpf.dc.basic.dto.I18nResSettingDto;
import gpf.dc.basic.dto.view.FeEventSubscribeDto;
import gpf.dc.basic.dto.view.TimerConfigDto;
import gpf.dc.basic.privilege.dto.MenuPrivilegeDto;
import gpf.dc.concrete.PrivilegeSetting;
import gpf.dc.concrete.RefActionConfig;
import gpf.dc.config.PDF;
import gpf.dc.runtime.PDFForm;
import gpf.dc.runtime.PDFFormProgressQuery;
import gpf.dc.runtime.PDFFormTodoQuery;
import gpf.dc.runtime.PdfInstanceProgress;
import gpf.dc.runtime.ToDoForm;
import gpf.dc.runtime.UnionQuery;

public class ApplicationSetting implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6171118543430288096L;
	public final static String MENU_ADMIN_APP = "Menu_Admin_App";
	String uuid;
	/**
	 * 应用名称
	 */
	String name;
	/**
	 * 应用中文名
	 */
	String label;
	/**
	 * 导航Logo
	 */
	byte[] navigatorLogo;
	/**
	 * 登录Logo
	 */
	byte[] loginLogo;
	/**
	 * 网站Logo
	 */
	byte[] favicon;
	/**
	 * 首页加载图
	 */
	byte[] loading;
	/**
	 * 主题样式
	 */
	SubjectStyle subjectStyle;
	/**
	 * 管理后台应用编号
	 */
	String adminAppCode;
	/**
	 * 适用终端：PC端、移动端
	 */
	List<String> applicableTerminals;
	/**
	 * 适配应用名称
	 */
	String adaptAppCode;
	String userModelId;
	String orgModelId;
	String noticeModelId;
	String sessionKey;
	String homeViewModelId;
	String homeViewCode;
	/**
	 * 分角色首页视图
	 */
	List<RoleBasedHomePageViewDto> roleBasedHomePageViews;
	String loginViewModelId;
	String loginViewCode;
	String registViewModelId;
	String registViewCode;
	//注册登记账号
	String registFormCreator;
	
	List<TodoSetting> todoSettings;
	boolean showTodo;
	
	List<PrivilegeSetting> privilegeSettings;
	
	List<RefActionConfig> loginVerifyFuncs;
	/**
	 * 动作定义
	 */
	List<RefActionConfig> actionDefines = new ArrayList<>();
	/**
	 * 事件订阅
	 */
	List<FeEventSubscribeDto> eventSubscribes = new ArrayList<>();
	/**
	 * 定时器
	 */
	List<TimerConfigDto> timerConfigs = new ArrayList<>();
	
	/**
	 * 访客账号，不做身份认证时的默认账号
	 */
	String guestAccount;
	/**
	 * 应用扩展配置
	 */
	AppViewSetting appViewSetting = new AppViewSetting();
	/**
	 * 应用配置参数
	 */
	List<ApplicationParameter> parameters = new ArrayList<>();
	/**
	 * 国际化资源配置
	 */
	List<I18nResSettingDto> i18nResSettings = new ArrayList<>();
	
	Long publishTime;
	Long updateTime;
	/**
	 * 服务协议
	 */
	AttachData serviceAgreement;

	public AttachData getServiceAgreement() {
		return serviceAgreement;
	}

	public ApplicationSetting setServiceAgreement(AttachData serviceAgreement) {
		this.serviceAgreement = serviceAgreement;
		return this;
	}

	public String getUuid() {
		return uuid;
	}
	public ApplicationSetting setUuid(String uuid) {
		this.uuid = uuid;
		return this;
	}
	public String getName() {
		return name;
	}
	public ApplicationSetting setName(String name) {
		this.name = name;
		return this;
	}
	public String getLabel() {
		return label;
	}
	public ApplicationSetting setLabel(String label) {
		this.label = label;
		return this;
	}
	public byte[] getNavigatorLogo() {
		return navigatorLogo;
	}
	public ApplicationSetting setNavigatorLogo(byte[] navigatorLogo) {
		this.navigatorLogo = navigatorLogo;
		return this;
	}
	public byte[] getLoginLogo() {
		return loginLogo;
	}
	public ApplicationSetting setLoginLogo(byte[] loginLogo) {
		this.loginLogo = loginLogo;
		return this;
	}
	public byte[] getFavicon() {
		return favicon;
	}
	public ApplicationSetting setFavicon(byte[] favicon) {
		this.favicon = favicon;
		return this;
	}
	public byte[] getLoading() {
		return loading;
	}
	public ApplicationSetting setLoading(byte[] loading) {
		this.loading = loading;
		return this;
	}
	public SubjectStyle getSubjectStyle() {
		return subjectStyle;
	}
	public ApplicationSetting setSubjectStyle(SubjectStyle subjectStyle) {
		this.subjectStyle = subjectStyle;
		return this;
	}
	public String getUserModelId() {
		return userModelId;
	}
	public ApplicationSetting setUserModelId(String userModelId) {
		this.userModelId = userModelId;
		return this;
	}
	public String getOrgModelId() {
		return orgModelId;
	}
	public ApplicationSetting setOrgModelId(String orgModelId) {
		this.orgModelId = orgModelId;
		return this;
	}
	
	public String getHomeViewModelId() {
		return homeViewModelId;
	}
	public ApplicationSetting setHomeViewModelId(String homeViewModelId) {
		this.homeViewModelId = homeViewModelId;
		return this;
	}
	public String getHomeViewCode() {
		return homeViewCode;
	}
	public ApplicationSetting setHomeViewCode(String homeViewCode) {
		this.homeViewCode = homeViewCode;
		return this;
	}
	public Action getHomeViewAction() throws Exception {
		if(!CmnUtil.isStringEmpty(homeViewModelId) && !CmnUtil.isStringEmpty(homeViewCode)) {
			try(IDao dao = IDaoService.get().newDao()){
				return IActionMgr.get().queryActionByCode(dao, homeViewModelId, homeViewCode);
			}
		}
		return null;
	}
	public List<RoleBasedHomePageViewDto> getRoleBasedHomePageViews() {
		return roleBasedHomePageViews;
	}
	/**
	 * 根据用户身份匹配相应的角色首页视图
	 * @param identifies
	 * @return
	 * @throws Exception
	 */
	public Action getRoleBasedHomePageViewAction(Set<String> identifies) throws Exception {
		if(CmnUtil.isCollectionEmpty(identifies) || CmnUtil.isCollectionEmpty(roleBasedHomePageViews))
			return null;
		for(RoleBasedHomePageViewDto roleBasedHomePageView : roleBasedHomePageViews) {
			for(String identify : identifies) {
				if(roleBasedHomePageView.getRole().contains(identify)) {
					Action action = roleBasedHomePageView.getViewAction();
					if(action != null) {
						return action;
					}
				}
			}
		}
		return null;
	}
	public ApplicationSetting setRoleBasedHomePageViews(List<RoleBasedHomePageViewDto> roleBasedHomePageViews) {
		this.roleBasedHomePageViews = roleBasedHomePageViews;
		return this;
	}
	public String getLoginViewModelId() {
		return loginViewModelId;
	}
	public ApplicationSetting setLoginViewModelId(String loginViewModelId) {
		this.loginViewModelId = loginViewModelId;
		return this;
	}
	public String getLoginViewCode() {
		return loginViewCode;
	}
	public ApplicationSetting setLoginViewCode(String loginViewCode) {
		this.loginViewCode = loginViewCode;
		return this;
	}
	public Action getLoginViewAction() throws Exception {
		if(!CmnUtil.isStringEmpty(loginViewModelId) && !CmnUtil.isStringEmpty(loginViewCode)) {
			try(IDao dao = IDaoService.get().newDao()){
				return IActionMgr.get().queryActionByCode(dao, loginViewModelId, loginViewCode);
			}
		}
		return null;
	}
	
	public String getRegistViewModelId() {
		return registViewModelId;
	}
	public ApplicationSetting setRegistViewModelId(String registViewModelId) {
		this.registViewModelId = registViewModelId;
		return this;
	}
	public String getRegistViewCode() {
		return registViewCode;
	}
	public ApplicationSetting setRegistViewCode(String registViewCode) {
		this.registViewCode = registViewCode;
		return this;
	}
	public Action getRegistViewAction() throws Exception {
		if(!CmnUtil.isStringEmpty(registViewModelId) && !CmnUtil.isStringEmpty(registViewCode)) {
			try(IDao dao = IDaoService.get().newDao()){
				return IActionMgr.get().queryActionByCode(dao, registViewModelId, registViewCode);
			}
		}
		return null;
	}
	
	public String getRegistFormCreator() {
		return registFormCreator;
	}
	public ApplicationSetting setRegistFormCreator(String registFormCreator) {
		this.registFormCreator = registFormCreator;
		return this;
	}
	
	public ApplicationSetting setNoticeModelId(String noticeModelId) {
		this.noticeModelId = noticeModelId;
		return this;
	}
	public String getNoticeModelId() {
		return noticeModelId;
	}
	
	public List<TodoSetting> getTodoSettings() {
		return todoSettings;
	}
	public ApplicationSetting setTodoSettings(List<TodoSetting> todoSettings) {
		this.todoSettings = todoSettings;
		return this;
	}
	
	public TodoSetting getTodoSetting(String pdfUuid) {
		for(TodoSetting setting : todoSettings) {
			if(CmnUtil.isStringEqual(setting.getPdfUuid(), pdfUuid))
				return setting;
		}
		return null;
	}
	
	
	public boolean isShowTodo() {
		return showTodo;
	}
	public ApplicationSetting setShowTodo(boolean showTodo) {
		this.showTodo = showTodo;
		return this;
	}
	
	public UnionQuery<PDFFormTodoQuery> getPDFFormTodoQuery(String user)throws Exception{
		List<String> columnAlias = Arrays.asList(ToDoForm.PdfUuid,ToDoForm.PdfName,ToDoForm.PdfInstUuid,ToDoForm.OpLogUuid,ToDoForm.Title,ToDoForm.SubTitle,ToDoForm.Creator,ToDoForm.CreatorCnName,ToDoForm.CreateTime,ToDoForm.Assignee,ToDoForm.AssigneeCnName,ToDoForm.StepName);
		UnionQuery<PDFFormTodoQuery> unionQuery = new UnionQuery<>();
		unionQuery.setColumnAlias(columnAlias);
		List<PDFFormTodoQuery> selectQuerys = new ArrayList<>();
		for(TodoSetting setting : NullUtil.get(todoSettings)) {
			if(CmnUtil.isStringEmpty(setting.getPdfUuid()))
				continue;
			Map<String,String> relateFieldDisplaySettingMap = new LinkedHashMap<>();
			if(setting.getRealteDisplayFieldMapping() != null) {
				for(String key : setting.getRealteDisplayFieldMapping().keySet()) {
					String pinyinKey = IFormMgr.get().getFieldCode(key);
					String pinyinValue = IFormMgr.get().getFieldCode(setting.getRealteDisplayFieldMapping().get(key));
					relateFieldDisplaySettingMap.put(pinyinKey, pinyinValue);
				}
			}
			PDF pdf = IPDFMgr.get().queryPDF(setting.getPdfUuid());
			PDFFormTodoQuery todoQuery = new PDFFormTodoQuery();
			todoQuery.setPdfUuid(setting.getPdfUuid());
			List<String> columns = new ArrayList<>();
			columns.add("'"+setting.getPdfUuid()+"'");
			columns.add("'"+pdf.getLabel()+"'");
			columns.add(PDFForm.PdfInstUuid);
			columns.add(PDFForm.OpLogUuid);
			columns.add(IFormMgr.get().getFieldCode(setting.getTitle()));
			if(!CmnUtil.isStringEmpty(setting.getSubTitle())) {
				columns.add(IFormMgr.get().getFieldCode(setting.getSubTitle()));
			}else {
				columns.add("''");
			}
			columns.add(PDFForm.Creator);
			columns.add(PDFForm.CreatorCnName);
			columns.add(PDFForm.CreateTime);
			columns.add(PDFForm.Assignee);
			columns.add(PDFForm.AssigneeCnName);
			columns.add(PDFForm.StepName);
			todoQuery.setColumns(columns);
			todoQuery.setUser(user);
			todoQuery.setRelateDisplayFieldMap(relateFieldDisplaySettingMap);
			selectQuerys.add(todoQuery);
		}
		unionQuery.setSelectQuerys(selectQuerys);
		return unionQuery;
	}
	
	public UnionQuery<PDFFormProgressQuery> getPDFFormProgressQuery(String user)throws Exception{
		List<String> columnAlias = Arrays.asList(PdfInstanceProgress.PdfUuid,PdfInstanceProgress.PdfName,PdfInstanceProgress.PdfInstUuid,PdfInstanceProgress.FormUuid,PdfInstanceProgress.Title,PdfInstanceProgress.SubTitle,PdfInstanceProgress.Creator,PdfInstanceProgress.CreatorCnName,PdfInstanceProgress.CreateTime,PdfInstanceProgress.UpdateTime,PdfInstanceProgress.ProgressStatus);
		UnionQuery<PDFFormProgressQuery> unionQuery = new UnionQuery<>();
		unionQuery.setColumnAlias(columnAlias);
		List<PDFFormProgressQuery> selectQuerys = new ArrayList<>();
		for(TodoSetting setting : NullUtil.get(todoSettings)) {
			if(CmnUtil.isStringEmpty(setting.getPdfUuid()))
				continue;
			Map<String,String> relateFieldDisplaySettingMap = new LinkedHashMap<>();
			if(setting.getRealteDisplayFieldMapping() != null) {
				for(String key : setting.getRealteDisplayFieldMapping().keySet()) {
					String pinyinKey = IFormMgr.get().getFieldCode(key);
					String pinyinValue = IFormMgr.get().getFieldCode(setting.getRealteDisplayFieldMapping().get(key));
					relateFieldDisplaySettingMap.put(pinyinKey, pinyinValue);
				}
			}
			PDF pdf = IPDFMgr.get().queryPDF(setting.getPdfUuid());
			PDFFormProgressQuery todoQuery = new PDFFormProgressQuery();
			todoQuery.setPdfUuid(setting.getPdfUuid());
			List<String> columns = new ArrayList<>();
			columns.add("'"+setting.getPdfUuid()+"'");
			columns.add("'"+pdf.getLabel()+"'");
			columns.add(PDFForm.PdfInstUuid);
			columns.add(PDFForm.UUID);
			columns.add(IFormMgr.get().getFieldCode(setting.getTitle()));
			if(!CmnUtil.isStringEmpty(setting.getSubTitle())) {
				columns.add(IFormMgr.get().getFieldCode(setting.getSubTitle()));
			}else {
				columns.add("''");
			}
			columns.add(PDFForm.Creator);
			columns.add(PDFForm.CreatorCnName);
			columns.add(PDFForm.CreateTime);
			columns.add(PDFForm.UpdateTime);
			columns.add(PdfInstanceProgress.ProgressStatus);
			todoQuery.setColumns(columns);
			todoQuery.setUser(user);
			selectQuerys.add(todoQuery);
		}
		unionQuery.setSelectQuerys(selectQuerys);
		return unionQuery;
	}
	
	public String getSessionKey() {
		return sessionKey;
	}
	public ApplicationSetting setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
		return this;
	}
	
	public List<PrivilegeSetting> getPrivilegeSettings() {
		return privilegeSettings;
	}
	public ApplicationSetting setPrivilegeSettings(List<PrivilegeSetting> privilegeSettings) {
		this.privilegeSettings = privilegeSettings;
		return this;
	}
	
	public List<RefActionConfig> getLoginVerifyFuncs() {
		return loginVerifyFuncs;
	}
	public ApplicationSetting setLoginVerifyFuncs(List<RefActionConfig> loginVerifyFuncs) {
		this.loginVerifyFuncs = loginVerifyFuncs;
		return this;
	}
	
	public String getGuestAccount() {
		return guestAccount;
	}
	public ApplicationSetting setGuestAccount(String guestAccount) {
		this.guestAccount = guestAccount;
		return this;
	}
	public String getAdminAppCode() {
		return adminAppCode;
	}
	public ApplicationSetting setAdminAppCode(String adminAppCode) {
		this.adminAppCode = adminAppCode;
		return this;
	}
	public List<String> getApplicableTerminals() {
		return applicableTerminals;
	}
	public ApplicationSetting setApplicableTerminals(List<String> applicableTerminals) {
		this.applicableTerminals = applicableTerminals;
		return this;
	}
	public String getAdaptAppCode() {
		return adaptAppCode;
	}
	public ApplicationSetting setAdaptAppCode(String adaptAppCode) {
		this.adaptAppCode = adaptAppCode;
		return this;
	}
	
	public boolean isApplicablePC() {
		return applicableTerminals.isEmpty() || applicableTerminals.contains("PC端");
	}
	
	public boolean isApplicableMobile() {
		return applicableTerminals.isEmpty() || applicableTerminals.contains("移动端");
	}
	/**
	 * 是否有管理后台访问权限
	 * @param menuPrivs
	 * @return
	 */
	public static boolean isAuthorizedAdminApp(Map<String,MenuPrivilegeDto> menuPrivs) {
		if(menuPrivs != null && menuPrivs.containsKey(MENU_ADMIN_APP)) {
			MenuPrivilegeDto menu = menuPrivs.get(MENU_ADMIN_APP);
			return menu.isVisible();
		}
		return false;
	}
	
	/**
	 * @return the parameters
	 */
	public List<ApplicationParameter> getParameters() {
		return parameters;
	}
	/**
	 * @param parameters the parameters to set
	 */
	public ApplicationSetting setParameters(List<ApplicationParameter> parameters) {
		this.parameters = parameters;
		return this;
	}
	
	public Long getPublishTime() {
		return publishTime;
	}
	public ApplicationSetting setPublishTime(Long publishTime) {
		this.publishTime = publishTime;
		return this;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public ApplicationSetting setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
		return this;
	}
	
	public AppViewSetting getAppViewSetting() {
		return appViewSetting;
	}
	public ApplicationSetting setAppViewSetting(AppViewSetting appViewSetting) {
		this.appViewSetting = appViewSetting;
		return this;
	}
	
	public List<I18nResSettingDto> getI18nResSettings() {
		return i18nResSettings;
	}
	public ApplicationSetting setI18nResSettings(List<I18nResSettingDto> i18nSettings) {
		this.i18nResSettings = i18nSettings;
		return this;
	}
	
	public Map<String,I18nResSettingDto> getI18nResSettingMap(){
		Map<String,I18nResSettingDto> map = new LinkedHashMap<>();
		for(I18nResSettingDto setting : NullUtil.get(i18nResSettings)) {
			map.put(setting.getLanguage(), setting);
		}
		return map;
	}
	
	public List<RefActionConfig> getActionDefines() {
		return actionDefines;
	}
	public ApplicationSetting setActionDefines(List<RefActionConfig> actionDefines) {
		this.actionDefines = actionDefines;
		return this;
	}
	public Map<String,RefActionConfig> getActionDefineMap(){
		Map<String,RefActionConfig> map = new LinkedHashMap<String, RefActionConfig>();
		for(RefActionConfig func : Nulls.get(actionDefines)) {
			map.put(func.getName(), func);
		}
		return map;
	}
	public List<FeEventSubscribeDto> getEventSubscribes() {
		return eventSubscribes;
	}
	public ApplicationSetting setEventSubscribes(List<FeEventSubscribeDto> eventSubscribes) {
		this.eventSubscribes = eventSubscribes;
		return this;
	}
	public List<TimerConfigDto> getTimerConfigs() {
		return timerConfigs;
	}
	public ApplicationSetting setTimerConfigs(List<TimerConfigDto> timerConfigs) {
		this.timerConfigs = timerConfigs;
		return this;
	}
}
