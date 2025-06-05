package gpf.dc.basic.fe.component.app;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kwaidoo.ms.tool.CmnUtil;
import com.kwaidoo.ms.tool.ToolUtilities;
import com.leavay.nio.crpc.RpcMap;

import bap.cells.Cells;
import cell.CellIntf;
import cell.cdao.IDao;
import cell.cdao.IDaoService;
import cell.cmn.IJson;
import cell.cmn.IJsonService;
import cell.cmn.cache.CMapCell;
import cell.cmn.cache.IMapCell;
import cell.cmn.shortlink.IShortLinkService;
import cell.fe.gpf.dc.basic.CAppFeI18n;
import cell.fe.gpf.dc.basic.IAppCacheMgr;
import cell.fe.gpf.dc.basic.IAppFeI18n;
import cell.fe.gpf.dc.basic.IAppFeLoginPage;
import cell.fe.gpf.dc.basic.IApplicationService;
import cell.gpf.adur.data.IFormMgr;
import cell.gpf.adur.user.IUserMgr;
import cmn.dto.PreloadTreeNode;
import cmn.i18n.I18nIntf;
import cmn.util.DateUtil;
import cmn.util.NullUtil;
import cmn.util.StringUtil;
import cmn.util.TraceUtil;
import cmn.util.Tracer;
import fe.cmn.app.Context;
import fe.cmn.app.ability.ReadSessionStorage;
import fe.cmn.app.ability.WriteLocalStorage;
import fe.cmn.app.ability.WriteSessionStorage;
import fe.cmn.data.DeviceInfoDto;
import fe.cmn.data.LocaleDto;
import fe.cmn.data.PairDto;
import fe.cmn.panel.PanelContext;
import fe.cmn.weixin.ability.WxMiniCommand;
import fe.cmn.weixin.mini.WxDecryptUtil;
import fe.cmn.weixin.mini.WxDecryptUtil.SessionInfo;
import fe.cmn.weixin.mini.WxMiniAppInfo;
import fe.cmn.weixin.mini.WxMiniAppInfoUtil;
import fe.cmn.weixin.mini.WxSharePageInfo;
import fe.util.FeCallbackPool;
import fe.util.FePaltformUtil;
import fe.util.dto.loading.LoadingMaskConfigDto;
import fe.util.dto.loading.LoadingMaskConfigDto.LoadingType;
import gpf.adur.data.AttachData;
import gpf.adur.data.Form;
import gpf.adur.data.TableData;
import gpf.adur.user.User;
import gpf.dc.basic.dto.I18nResSettingDto;
import gpf.dc.basic.fe.i18n.AppFeI18n;
import gpf.dc.basic.fe.intf.AppCacheMgrIntf;
import gpf.dc.basic.fe.intf.AppInitIntf;
import gpf.dc.basic.form.define.ApplicationDefine;
import gpf.dc.basic.i18n.GpfDCBasicI18n;
import gpf.dc.basic.intf.AppDefaultFilterIntf;
import gpf.dc.basic.param.view.dto.AppRouter;
import gpf.dc.basic.param.view.dto.AppViewSetting;
import gpf.dc.basic.param.view.dto.ApplicationSetting;
import gpf.dc.basic.param.view.dto.FormViewSetting;
import gpf.dc.basic.param.view.dto.MenuNodeDto;
import gpf.dc.basic.param.view.dto.TableViewSetting;
import gpf.dc.basic.privilege.dto.AppPrivilegeDto;
import gpf.dc.basic.privilege.dto.MenuPrivilegeDto;
import gpf.dc.basic.util.GpfDCBasicConst;
import gpf.dc.basic.util.JwtUserInfo;
import gpf.dc.basic.util.JwtUtil;
import gpf.dc.intf.FormOpObserver;
import web.jwt.exceptions.TokenExpiredException;

public class AppCacheUtil {
	
	public final static String CacheKey_App = "$app";
	public final static String CacheKey_MenuNodes = "$menuNodes";
	public final static String CacheKey_MenuTree = "$menuTree";
	public final static String CacheKey_Setting = "$setting";
	public final static String CacheKey_AdminSetting = "$adminSetting";
	public final static String CacheKey_AppPrivileges = "$appPrivilege";
	public final static String CacheKey_IsBoundWeChatAccount = "isBoundWeChatAccount";
	public final static String CacheKey_DefaultLoadingMask = "defaultLoadingMask";
	
	public final static String InitParam_SystemUuid = "systemUuid";
	public final static String InitParam_AppCode = "appCode";
	public final static String InitParam_AppSessionKey = "appSessionKey";
	//是否使用JDV的登录页
	public final static String InitParam_UseJDVPage = "useJDVPage";
	
	public final static String InitParam_RouterShortLink = "routerShortLink";
	
	/**
	 * 获取应用上的信道缓存管理接口
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static AppCacheMgrIntf getAppCacheMgr(Context context,ApplicationSetting appSetting)throws Exception{
//		ApplicationSetting appSetting = getSetting(context);
		AppViewSetting viewSetting = appSetting.getAppViewSetting();
		Class<? extends AppCacheMgrIntf> clazz = viewSetting.getAppCacheIntfClazz();
		if(clazz == null)
			return IAppCacheMgr.get();
		if(CellIntf.class.isAssignableFrom(clazz)) {
			return Cells.get(clazz);
		}else {
			return clazz.newInstance();
		}
	}
	
	public static AppInitIntf getAppInitImpl(Context context,ApplicationSetting appSetting)throws Exception{
		AppViewSetting viewSetting = appSetting.getAppViewSetting();
		Class<? extends AppInitIntf> appInitClazz = viewSetting.getAppInitIntfClazz();
		if(appInitClazz != null) {
			if(CellIntf.class.isAssignableFrom(appInitClazz)) {
				AppInitIntf initIntf = Cells.get(appInitClazz);
				return initIntf;
			}else if(!appInitClazz.isInterface()) {
				AppInitIntf initIntf = appInitClazz.newInstance();
				return initIntf;
			}
		}
		return null;
	}
	/**
	 * 获取应用的所有缓存
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static IMapCell getAllAppCache(Context context)throws Exception{
		String appUuid = getAppUuid(context);
		RpcMap<Object> map = (RpcMap<Object>) context.getCacheValue(CacheKey_App);
		if(map != null) {
			RpcMap<Object> appCache = (RpcMap<Object>) map.get(appUuid);
			IMapCell<String, Object> mapCell = new CMapCell<>();
			mapCell.putAll(appCache);
			return mapCell;
		}
		return null;
	}
	
	public static Object getAppCache(Context context,String appUuid,String key)throws Exception{
		try {
			boolean isExist = context.tryContainCacheKey(CacheKey_App);
//			if(context.tryContainCacheKey(key) == null) {
//				context.putCacheValue(CacheKey_App, new RpcMap<>());
//				return null;
//			}
			if(!isExist) {
				context.putCacheValue(CacheKey_App, new RpcMap<>());
			}
			RpcMap<Object> map = (RpcMap<Object>) context.getCacheValue(CacheKey_App);
			if(map.containsKey(appUuid)) {
				RpcMap<Object> appCache = (RpcMap<Object>) map.get(appUuid);
				return appCache.get(key);
			}
		}catch (Exception e) {
			e.printStackTrace();
			context.putCacheValue(CacheKey_App, new RpcMap<>());
		}
		return null;
	}
	
	public static void putAppCache(Context context,String appUuid,String key,Object value)throws Exception{
		try {
			RpcMap<Object> map = (RpcMap<Object>) context.getCacheValue(CacheKey_App);
			if(map == null) {
				map = new RpcMap<>();
				context.putCacheValue(CacheKey_App, map);
			}
			if(!map.containsKey(appUuid))
				map.put(appUuid, new RpcMap<>());
			RpcMap<Object> appCache = (RpcMap<Object>) map.get(appUuid);
			if(value == null) {
				appCache.remove(key);
			}else {
				appCache.put(key, value);
			}
			context.putCacheValue(CacheKey_App, map);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void removeAppCache(Context context,String appUuid,String key)throws Exception{
		try {
			Map<String,Map<String,Object>> map = (Map<String, Map<String, Object>>) context.getCacheValue(CacheKey_App);
			if(map == null) {
				map = new RpcMap<>();
				context.putCacheValue(CacheKey_App, map);
			}
			if(!map.containsKey(appUuid))
				map.put(appUuid, new RpcMap<>());
			map.get(appUuid).remove(key);
			context.putCacheValue(CacheKey_App, map);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 退出登录时清除缓存
	 * @param context
	 * @throws Exception
	 */
	public static void clearAppCacheWhenLogout(Context context)throws Exception{
		ApplicationSetting setting = getSetting(context);
		if(setting != null) {
			removeAppCache(context, setting.getUuid(), CacheKey_MenuNodes);
			removeAppCache(context, setting.getUuid(), CacheKey_AppPrivileges);
			removeAppCache(context, setting.getUuid(), CacheKey_IsBoundWeChatAccount);
			removeAppCache(context, setting.getUuid(), CacheKey_MenuTree);
		}
	}
	/**
	 * 获取当前应用下，用户的应用权限
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static AppPrivilegeDto getAppPrivilege(Context context)throws Exception{
		AppPrivilegeDto appPrivileges = (AppPrivilegeDto) context.getCacheValue(CacheKey_AppPrivileges);
		if(appPrivileges == null) {
			ApplicationSetting setting  = getSetting(context);
//			String appUuid = getAppUuid(context);
			appPrivileges = (AppPrivilegeDto) getAppCache(context, setting.getUuid(), CacheKey_AppPrivileges);
			if(appPrivileges == null) {
				appPrivileges = IApplicationService.get().queryMenuPrivileges(setting, context.getCurrentUser(),context);
//				context.putCacheValue(CacheKey_MenuPrivileges, menuPrivileges);
				putAppCache(context, setting.getUuid(), CacheKey_AppPrivileges, appPrivileges);
			}
		}
		return appPrivileges;
	}
	/**
	 * 获取当前应用下，用户的菜单权限
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static Map<String,MenuPrivilegeDto> getMenuPrivileges(Context context)throws Exception{
		AppPrivilegeDto appPrivilege = getAppPrivilege(context);
		return appPrivilege.getAllMenuPrivileges();
	}
	/**
	 * 获取当前应用下，用户的身份列表，根据在应用的权限配置上匹配得到
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static Set<String> getIdentifiesOfUser(Context context)throws Exception{
		AppPrivilegeDto appPrivilege = getAppPrivilege(context);
		return appPrivilege.getIdentifyMenuPrivilegeMap().keySet();
	}
	/**
	 * 检查用户是否拥有APP访问权限，当APP菜单不为空时，用户没有任何菜单文档权限，则无APP权限
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static boolean hasAppMenuPrivilege(Context context) throws Exception {
		List<MenuNodeDto> authorizeMenus = getMenuNodes(context);
		if(CmnUtil.isCollectionEmpty(authorizeMenus)) {
			ApplicationSetting setting = getSetting(context);
			try(IDao dao = IDaoService.newIDao()){
				Form form = IFormMgr.get().queryForm(dao, GpfDCBasicConst.ApplicationModelId, setting.getUuid());
				TableData menuTable = form.getTable(ApplicationDefine.sMenu);
				if(!menuTable.isEmtpy()) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean isAuthorizedAdminApp(Context context)throws Exception{
		Map<String,MenuPrivilegeDto> menuPrivileges = getMenuPrivileges(context);
		return ApplicationSetting.isAuthorizedAdminApp(menuPrivileges);
	}
	public static	List<PreloadTreeNode<Form>> queryMenuTree(Context context) throws Exception {
		ApplicationSetting setting =  getSetting(context);
		String appUuid = setting.getUuid();
		List<PreloadTreeNode<Form>> preloadTree = new ArrayList<>();
		if(setting != null) {
			preloadTree = (List<PreloadTreeNode<Form>>) getAppCache(context, setting.getUuid(), CacheKey_MenuTree);
			if(preloadTree==null) {
				AppPrivilegeDto appPrivs = getAppPrivilege(context);
				preloadTree = IApplicationService.get().queryAppMenus(setting, context.getCurrentUser(), context,appPrivs);
				putAppCache(context, setting.getUuid(), CacheKey_MenuTree, preloadTree);
			}
		}
		return  preloadTree;

	}

	public static List<MenuNodeDto> getMenuNodes(Context context)throws Exception{
		ApplicationSetting setting  = getSetting(context);
		List<MenuNodeDto> menus = new ArrayList<>();
		if(setting != null) {
//			String appUuid = getAppUuid(context);
			menus = (List<MenuNodeDto>) getAppCache(context, setting.getUuid(), CacheKey_MenuNodes);
			if(menus == null) {
				AppPrivilegeDto appPrivilege = getAppPrivilege(context);
				menus = IApplicationService.get().queryAppMenuNodes(setting, context.getCurrentUser(),context,appPrivilege);
				putAppCache(context, setting.getUuid(), CacheKey_MenuNodes, menus);
			}
//			try {
//				context.putCacheValue(CacheKey_MenuNodes, menus);
//			}catch (Exception e) {
//				e.printStackTrace();
//			}
		}
		return menus;
	}
	
	public static MenuNodeDto getMenuNode(PanelContext panelContext,String menuUuid) throws Exception {
		List<MenuNodeDto> menuTree = getMenuNodes(panelContext);
		Map<String,MenuNodeDto> map = new LinkedHashMap<String, MenuNodeDto>();
		buildMenuNodeMap(menuTree, map);
		return map.get(menuUuid);
	}
	
	public static MenuNodeDto getMenuNodeByName(PanelContext panelContext,String menuName) throws Exception {
		List<MenuNodeDto> menuTree = getMenuNodes(panelContext);
		Map<String,MenuNodeDto> map = new LinkedHashMap<String, MenuNodeDto>();
		buildMenuNodeNameMap(menuTree, map);
		return map.get(menuName);
	}
	
	private static void buildMenuNodeNameMap(List<MenuNodeDto> menuTree,Map<String, MenuNodeDto> map){
		for(MenuNodeDto node : NullUtil.get(menuTree)) {
			map.put(node.getLabel(),node);
			buildMenuNodeNameMap(node.getChildren(), map);
		}
	}
	
	private static void buildMenuNodeMap(List<MenuNodeDto> menuTree,Map<String, MenuNodeDto> map){
		for(MenuNodeDto node : NullUtil.get(menuTree)) {
			map.put(node.getKey(),node);
			buildMenuNodeMap(node.getChildren(), map);
		}
	}

	public static String getAppUuid(Context context) {
		Map<String,Object> appInfo = (Map<String, Object>) context.getAppInfo();
		String appUuid = (String) appInfo.get(InitParam_SystemUuid);
		return appUuid;
	}
	
	public static String getAppCode(Context context) {
		Map<String,Object> appInfo = (Map<String, Object>) context.getAppInfo();
		String appCode = (String) appInfo.get(InitParam_AppCode);
		return appCode;
	}

	public static ApplicationSetting getSetting(Context context) throws Exception {
		String appUuid = getAppUuid(context);
		if(CmnUtil.isStringEmpty(appUuid))
			return null;
		ApplicationSetting setting  = null;
		try {
			setting = (ApplicationSetting) getAppCache(context, appUuid, CacheKey_Setting);
		}catch (Exception e) {
		}
		if(setting == null) {
			setting = IApplicationService.get().queryApplicationSetting(appUuid);
			if(setting == null) {
				String appCode = getAppCode(context);
				try(IDao dao = IDaoService.newIDao()){
					setting = IApplicationService.get().queryApplicationSettingByCode(dao, appCode);
				}
			}
			if(setting != null) {
				if(!CmnUtil.isStringEmpty(setting.getAdaptAppCode())) {
					
					if(((FePaltformUtil.isMobile(context) || FePaltformUtil.isMiniProgram(context))
							&& !setting.isApplicableMobile())
							|| (!FePaltformUtil.isMobile(context) && !FePaltformUtil.isMiniProgram(context)
									&& !setting.isApplicablePC())) {
						try(IDao dao = IDaoService.newIDao()){
							ApplicationSetting adaptSetting = IApplicationService.get().queryApplicationSettingByCode(dao, setting.getAdaptAppCode());
							if(adaptSetting != null)
								setting = adaptSetting;
						}
					}
				}
				AppInitIntf appInitIntf = getAppInitImpl(context,setting);
				if(appInitIntf != null)
					setting = appInitIntf.afterQueryApplicatinSetting(context, setting);
				putAppCache(context, appUuid, CacheKey_Setting, setting);
//				try {
//				context.putCacheValue(CacheKey_Setting, setting);
//				}catch (Exception e) {
//					e.printStackTrace();
//				}
			}
		}
		if(!CmnUtil.isStringEmpty(setting.getAdminAppCode()) && isAdminMode(context)) {
			ApplicationSetting adminSetting = (ApplicationSetting) getAppCache(context, appUuid, CacheKey_AdminSetting);
//			try{
//				adminSetting = (ApplicationSetting) context.getCacheValue(CacheKey_AdminSetting);
//			}catch (Exception e) {
//				// TODO: handle exception
//			}
			if(adminSetting == null) {
				if(!CmnUtil.isStringEmpty(setting.getAdminAppCode())) {
					try(IDao dao = IDaoService.newIDao()){
						adminSetting = IApplicationService.get().queryApplicationSettingByCode(dao, setting.getAdminAppCode());
					}
				}
				AppInitIntf appInitIntf = getAppInitImpl(context,setting);
				if(appInitIntf != null)
					adminSetting = appInitIntf.afterQueryApplicatinSetting(context, adminSetting);
				putAppCache(context, appUuid, CacheKey_AdminSetting, adminSetting);
//				try {
//					context.putCacheValue(CacheKey_AdminSetting, adminSetting);
//				}catch (Exception e) {
//					e.printStackTrace();
//				}
			}
			if(adminSetting != null)
				return adminSetting;
		}
		return setting;
	}
	
	/**
	 * 获取 APP SessionKey，默认sessionKey为App Code
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static String getAppSessionKey(Context context) throws Exception {
		Map<String,Object> appInfo = (Map<String, Object>) context.getAppInfo();
		String appSessionKey = (String) appInfo.get(InitParam_AppSessionKey);
		ApplicationSetting appSetting = getSetting(context);
		if(CmnUtil.isStringEmpty(appSessionKey))
			appSessionKey = "SessionKey_"+appSetting.getName();
		return appSessionKey;
	}
	
	public static String getStorageUser(Context context) throws Exception {
//		String sessionKey = getAppSessionKey(context);
//		Object userStorage = ReadLocalStorage.read(context.getChannel(),sessionKey, "userName");
//		return CmnUtil.getString(userStorage,"");
		ApplicationSetting appSetting = getSetting(context);
		return (String)getAppCacheMgr(context, appSetting).getCacheValue(context,IAppCacheMgr.UserName);
	}
	
	private static Map<String,Boolean> getOrInitAdminModelCache(Context context) throws Exception{
		Map<String,Boolean> adminModelStorage = null;
		String appUuid = getAppUuid(context);
		try{
			adminModelStorage = (Map<String, Boolean>) getAppCache(context, appUuid, AppCacheMgrIntf.AdminMode);
		}catch (Exception e) {
		}
		if(adminModelStorage == null) {
			adminModelStorage = (Map<String, Boolean>) ReadSessionStorage.read(context.getChannel(),IAppCacheMgr.AdminMode);
			if(adminModelStorage == null) {
				adminModelStorage = new LinkedHashMap<>();
//				context.putCacheValue(AppCacheMgrIntf.AdminMode, adminModelStorage);
				FeCallbackPool.add(context.getChannel(), new WriteSessionStorage().setKey(AppCacheMgrIntf.AdminMode).setValue(adminModelStorage));
			}
			putAppCache(context, appUuid, AppCacheMgrIntf.AdminMode, adminModelStorage);
		}
		return adminModelStorage;
	}
	/**
	 * 当前应用是否在后台管理模式
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static boolean isAdminMode(Context context) throws Exception {
//		Object adminModelStorage = ReadSessionStorage.read(context.getChannel(),"adminMode");
		Map<String,Boolean> adminModelStorage = getOrInitAdminModelCache(context);
//		if(adminModelStorage == null)
//			return false;
		String appUuid = getAppUuid(context);
		Boolean isAdminMode = CmnUtil.getBoolean(adminModelStorage.get(appUuid), false);
		return isAdminMode;
	}
	
	public static void setAdminMode(PanelContext context,boolean isAdminMode) throws Exception {
		Map<String,Boolean> adminModelStorage = getOrInitAdminModelCache(context);
//		Map<String,Boolean> adminModelStorage = (Map<String, Boolean>) ReadSessionStorage.read(context.getChannel(),"adminMode");
//		if(adminModelStorage == null)
//			adminModelStorage = new LinkedHashMap<>();
		String appUuid = getAppUuid(context);
		adminModelStorage.put(appUuid, isAdminMode);
//		WriteSessionStorage.write(context.getChannel(), "adminMode", adminModelStorage);
		FeCallbackPool.add(context.getChannel(), new WriteSessionStorage().setKey(IAppCacheMgr.AdminMode).setValue(adminModelStorage));
	}
	
	public static String getToken(Context context)throws Exception{
//		String sessionKey = getAppSessionKey(context);
//		Object tokenObj = ReadLocalStorage.read(context.getChannel(),sessionKey, "token");
//		if(CmnUtil.isObjectEmpty(tokenObj))
//			return null;
//		return (String) tokenObj;
		ApplicationSetting appSetting = getSetting(context);
		return (String)getAppCacheMgr(context, appSetting).getCacheValue(context,IAppCacheMgr.Token);
	}
	
	public static void setToken(Context context,JwtUserInfo userInfo)throws Exception{
		String sessionKey = getAppSessionKey(context);
		if(userInfo != null) {
			String refreshToken = JwtUtil.generateJWT(userInfo.getUserId(), userInfo, userInfo.getExpireSec(), null);
			FeCallbackPool.add(context.getChannel(), new WriteLocalStorage().setId(sessionKey).setData(new PairDto<>("token", refreshToken)));
//			WriteLocalStorage.write(context.getChannel(), sessionKey,
//					new PairDto<>("token", refreshToken));
		}else {
//			WriteLocalStorage.write(context.getChannel(), sessionKey,
//					new PairDto<>("token", ""));
			FeCallbackPool.add(context.getChannel(), new WriteLocalStorage().setId(sessionKey).setData(new PairDto<>("token", "")));
		}
	}
	
	public static void refreshToken(Context context,User user)throws Exception{
		String sessionKey = getAppSessionKey(context);
		if(user != null) {
			JwtUserInfo userInfo = new JwtUserInfo().setUserId(user.getCode()).setName(user.getUserName()).setFullName(user.getFullName())
					.setSessionId(sessionKey);
			DeviceInfoDto device = FePaltformUtil.getCacheDeviceInfo(context);
			userInfo.setUserAgent(device.getUserAgent());
			userInfo.setClientId(device.getId());
//			userInfo.setClientIp(device.getExternalIP());
			//默认失效时间1个月
			long tokenExpireTime = user.getTokenExpireTime() == null ? 30 * 24 * 60L : user.getTokenExpireTime();
			userInfo.setExpireSec(tokenExpireTime * 60 * 1000L);
			Tracer tracer = TraceUtil.getCurrentTracer();
			tracer.info("更新token有效时长：" + DateUtil.formatMs(userInfo.getExpireSec()));
			setToken(context, userInfo);
		}else {
			setToken(context, null);
		}
	}
	
	public static boolean isLogined(Context context) throws Exception {
		String token = getToken(context);
		Tracer tracer = TraceUtil.getCurrentTracer();
		if(CmnUtil.isStringEmpty(token))
			return false;
		try {
			JwtUserInfo userInfo = JwtUtil.validateLogin(token, null, JwtUserInfo.class);
			validateLogin(context, userInfo);
			return true;
		}catch (Exception e) {
			if(e instanceof TokenExpiredException) {
				tracer.info("Token已失效，token = " + token);
			}
			return false;
		}
//		Object loginedStorage = ReadLocalStorage.read(context.getChannel(),sessionKey, "logined");
//		Boolean isLogined = CmnUtil.isObjectEmpty(loginedStorage) ? false : (Boolean) loginedStorage;
//		return isLogined;
	}
	
	public static void validateLogin(Context context,JwtUserInfo userInfo) throws Exception {
		//可以补充检验userAgent的一致性
		String sessionKey = getAppSessionKey(context);
		if(!CmnUtil.isStringEqual(userInfo.getSessionId(), sessionKey)) {
			throw new TokenExpiredException("会话ID不一致！");
		}
		DeviceInfoDto device = FePaltformUtil.getCacheDeviceInfo(context);
		if(!CmnUtil.isStringEqual(device.getId(),userInfo.getClientId())) {
			throw new TokenExpiredException("客户端ID不一致！");
		}
//		if(!CmnUtil.isStringEqual(device.getExternalIP(), userInfo.getClientIp())) {
//			throw new TokenExpiredException("客户端IP不一致！");
//		}
//		userInfo.getClientIp()
	}
	
	public static void setLogined(PanelContext context,User user,boolean logined) throws Exception {
		ApplicationSetting appSetting = getSetting(context);
		if(appSetting == null)
			return;
//		String sessionKey = getAppSessionKey(context);
		if(logined) {
			refreshToken(context, user);
		}else {
			setToken(context, null);
		}
//		WriteLocalStorage.write(context.getChannel(), sessionKey,
//				new PairDto<>("logined", logined));
	}
	
	public static boolean isLogouted(PanelContext context) throws Exception {
		ApplicationSetting appSetting = getSetting(context);
		boolean isLogouted = CmnUtil.getBoolean(getAppCache(context, appSetting.getUuid(), "isLogouted"),false);
		return isLogouted;
	}
	
	public static void setLogouted(PanelContext context,boolean isLogouted) throws Exception {
		ApplicationSetting appSetting = getSetting(context);
		putAppCache(context, appSetting.getUuid(), "isLogouted", isLogouted);
	}
	
//	public static void setLoginUser(PanelContext context,String loginUser) throws Exception {
//		String sessionKey = getAppSessionKey(context);
//		WriteLocalStorage.write(context.getChannel(), sessionKey,
//				new PairDto<>("userCode", loginUser));
//	}
	
	public static void setUserName(PanelContext context,String userName) throws Exception {
		String sessionKey = getAppSessionKey(context);
		FeCallbackPool.add(context.getChannel(), new WriteLocalStorage().setId(sessionKey).setData(new PairDto<>("userName", userName)));
//		WriteLocalStorage.write(context.getChannel(), sessionKey,
//				new PairDto<>("userName", userName));

	}
	
	public static String getLoginUser(Context context) throws Exception {
		String sessionKey = getAppSessionKey(context);
		String token = getToken(context);
		if(CmnUtil.isStringEmpty(token))
			return null;
		JwtUserInfo userInfo = JwtUtil.decodeToken(token, JwtUserInfo.class);
		if(userInfo != null) {
			return userInfo.getUserId();
		}
		return null;
//		Object userNameStorage = ReadLocalStorage.read(context.getChannel(), sessionKey, "userCode");
//		String userName = CmnUtil.isObjectEmpty(userNameStorage) ? "" : (String) userNameStorage;
//		return userName;
	}
	/**
	 * 检查用户是否绑定微信账号
	 * @param context
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public static boolean isBoundWeChatAccount(Context context,User user)throws Exception{
		if(user == null)
			return false;
		ApplicationSetting setting = getSetting(context);
		Boolean isBoundWeChatAccount = (Boolean) getAppCache(context, setting.getUuid(), CacheKey_IsBoundWeChatAccount);
		if(isBoundWeChatAccount == null) {
			String appId = AppCacheUtil.getWeChatMiniProgramAppId(context);
			String uniqueIdentification = AppCacheUtil.getWeChatMinProgramUniqueIdentification(context);
			isBoundWeChatAccount = IApplicationService.get().isBoundWeChatAccount(user, appId, uniqueIdentification);
			putAppCache(context, setting.getUuid(), CacheKey_IsBoundWeChatAccount, isBoundWeChatAccount);
		}
		return isBoundWeChatAccount;
	}
	/**
	 * 设置用户绑定微信账号状态缓存
	 * @param context
	 * @param user
	 * @param isBoundWeChatAccount
	 * @throws Exception
	 */
	public static void setBoundWeChatAccount(Context context,User user,boolean isBoundWeChatAccount)throws Exception{
		ApplicationSetting setting = getSetting(context);
		putAppCache(context, setting.getUuid(), CacheKey_IsBoundWeChatAccount, isBoundWeChatAccount);
	}
	/**
	 * 获取小程序appId
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static String getWeChatMiniProgramAppId(Context context) throws Exception {
		ApplicationSetting setting = getSetting(context);
		String appId = (String) getAppCache(context, setting.getUuid(), "wxAppId");
		try {
			if(appId == null) {
				WxMiniAppInfo appInfo = WxMiniAppInfoUtil.prepareAppInfo(context.getChannel());
				appId = appInfo.getAppId();
				putAppCache(context, setting.getUuid(), "wxAppId", appId);
				putAppCache(context, setting.getUuid(), "wxAppSecret", appInfo.getAppSecret());
			}
		}catch (Throwable e) {
			e.printStackTrace();
			appId = "";
		}
		putAppCache(context, setting.getUuid(), "wxAppId", appId);
		return appId;
	}
	/**
	 * 获取小程序appSecret
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static String getWeChatMiniProgramAppSecret(Context context) throws Exception {
		ApplicationSetting setting = getSetting(context);
		String appId = getWeChatMiniProgramAppId(context);
		if(CmnUtil.isStringEmpty(appId)) {
			return null;
		}
		String appSecret = (String) getAppCache(context, setting.getUuid(), "wxAppSecret");
		try {
			if(appSecret == null) {
				WxMiniAppInfo appInfo = WxMiniAppInfoUtil.prepareAppInfo(context.getChannel());
				appSecret = appInfo.getAppSecret();
				putAppCache(context, setting.getUuid(), "wxAppId", appId);
				putAppCache(context, setting.getUuid(), "wxAppSecret", appInfo.getAppSecret());
			}
		}catch (Throwable e) {
			e.printStackTrace();
			appSecret = "";
		}
		putAppCache(context, setting.getUuid(), "wxAppSecret", appSecret);
		return appSecret;
	}
	/**
	 * 获取小程序用户openId
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static String getWeChatMinProgramOpenId(Context context) throws Exception {
		String appId = getWeChatMiniProgramAppId(context);
		if(CmnUtil.isStringEmpty(appId))
			return null;
		String code = WxMiniCommand.loginGetCode(context.getChannel());
		String appSecret = getWeChatMiniProgramAppSecret(context);//(String) context.getCacheValue(WxMiniAppInfoUtil.KEY_WECHAT_MINI_APP_SECRET);//getWeChatMiniProgramAppSecret(context);

		try {
			SessionInfo info = WxDecryptUtil.querySessionInfo(code, appId, appSecret);
//			SessionInfo info = (SessionInfo)GsonUtil.fromJson(sessionJson, SessionInfo.class);
			info.tryThrowError();
			return info.openid;
		} catch (Throwable err) {
			err.printStackTrace();
			throw new Exception("Failed to get session info", err);
		} 
	}
	/**
	 * 获取小程序用户的唯一标识，如果unionID存在则返回unionID，反之返回openID
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static String getWeChatMinProgramUniqueIdentification(Context context) throws Exception {
//		String appId = getWeChatMiniProgramAppId(context);
//		if(CmnUtil.isStringEmpty(appId))
//			return null;
		ApplicationSetting appSetting = getSetting(context);
		Tracer tracer = TraceUtil.getCurrentTracer();
		Map<String,String> sessionMap = (Map<String, String>) getAppCache(context, appSetting.getUuid(), AppCacheMgrIntf.WeChatSessionInfo);
		if(sessionMap != null) {
			String unionId = sessionMap.get("unionId");
			String openId = sessionMap.get("openId");
			if (StringUtil.isEmpty(unionId)) {
				return openId;
			}else {
				return unionId;
			}
		}
//		tracer.info("getWeChatMinProgramUniqueIdentification","获取小程序用户的唯一标识");
//		String code = WxMiniCommand.loginGetCode(context.getChannel());
//		tracer.info("getWeChatMinProgramUniqueIdentification","获取小程序用户的code="+code);
//		String appSecret = getWeChatMiniProgramAppSecret(context);//(String) context.getCacheValue(WxMiniAppInfoUtil.KEY_WECHAT_MINI_APP_SECRET);//getWeChatMiniProgramAppSecret(context);
//		tracer.info("getWeChatMinProgramUniqueIdentification","获取小程序的appSecret");
		try {
			SessionInfo info = WxDecryptUtil.querySessionInfo(context.getChannel());
//			SessionInfo info = WxDecryptUtil.querySessionInfo(code, appId, appSecret);
			info.tryThrowError();
			tracer.info("getWeChatMinProgramUniqueIdentification","获取小程序用户的唯一标识结束");
			sessionMap = new LinkedHashMap<>();
			String unionId = info.getUnionid();
			String openId = info.getOpenid();
			sessionMap.put("unionId", unionId);
			sessionMap.put("openId", openId);
			if (StringUtil.isEmpty(unionId)) {
				return openId;
			}else {
				return unionId;
			}
		} catch (Throwable err) {
			err.printStackTrace();
			throw new Exception("Failed to get session info", err);
		}
	}

	/**
	 * 获取应用上下文缓存
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> getAppContext(Context context) throws Exception{
		String appUuid = getAppUuid(context);
		Map<String,Object> map = (Map<String, Object>) getAppCache(context, appUuid, "contextMap");
		if(map == null) {
			map = new RpcMap<>();
			putAppCache(context, appUuid, "contextMap", map);
		}
		return map;
	}
	public static String ContextKey_FormOpObserver = "FormOpObserver";
	public static String ContextKey_AppDefaultFilter = "AppDefaultFilter";
	public static FormOpObserver getFormOpObserver(Context context)throws Exception{
		ApplicationSetting setting = getSetting(context);
		if(setting != null) {
			AppCacheMgrIntf cacheMgr = getAppCacheMgr(context,setting);
			if(cacheMgr != null  && cacheMgr.isCacheExpired(context)) {
				cacheMgr.initCache(context);
			}
			return (FormOpObserver) getAppContext(context).get(ContextKey_FormOpObserver);
		}
		return null;
	}
	
	public static void setFormOpObserver(Context context,FormOpObserver observer) throws Exception {
		ApplicationSetting setting = getSetting(context);
		if(setting != null) {
			getAppContext(context).put(ContextKey_FormOpObserver, observer);
		}
	}
	
	public static AppDefaultFilterIntf getAppDefaultFilter(Context context) throws Exception {
		ApplicationSetting setting = getSetting(context);
		if(setting != null) {
			AppCacheMgrIntf cacheMgr = getAppCacheMgr(context,setting);
			if(cacheMgr != null  && cacheMgr.isCacheExpired(context)) {
				cacheMgr.initCache(context);
			}
			return (AppDefaultFilterIntf) getAppContext(context).get(ContextKey_AppDefaultFilter);
		}
		return null;
	}
	
	public static void setAppDefaultFilter(Context context,AppDefaultFilterIntf defaultFilter) throws Exception {
		ApplicationSetting setting = getSetting(context);
		if(setting != null) {
			getAppContext(context).put(ContextKey_AppDefaultFilter, defaultFilter);
		}
	}
	/**
	 * 获取应用初始设置的路由信息，用于小程序和APP端
	 * @param context
	 * @return
	 * @throws Exception 
	 */
	public static AppRouter getInitAppRouter(Context context) throws Exception {
		Map<String,Object> appInfo = (Map<String, Object>) context.getAppInfo();
		if(appInfo == null)
			return null;
		String routerShortLink = (String) appInfo.get(InitParam_RouterShortLink);
		String scene = context.getSceneFromWxMiniParams();
		//如果是小程序码打开的页面，从scene获取路由参数，存放短链形式的路由参数，在小程序码打开时决定要跳转到哪个详情页面
		if(!CmnUtil.isStringEmpty(scene) && FePaltformUtil.isMiniProgram(context)) {
			Map<String,String> miniProgramParams = parseScene(scene);
			if(miniProgramParams.containsKey(InitParam_RouterShortLink)) {
				routerShortLink = miniProgramParams.get(InitParam_RouterShortLink);
			}
		}
		if(CmnUtil.isStringEmpty(routerShortLink)) {
			Tracer tracer = TraceUtil.getCurrentTracer();
			tracer.logStart();
			ApplicationSetting appSetting = getSetting(context);
			Map<String,Object> pageParams = (Map<String, Object>) getAppCache(context, appSetting.getUuid(), AppCacheMgrIntf.PageParameters);
//    		Map<String,Object> pageParams = GetPageParameters.get(context.getChannel());
    		tracer.infoCost("getInitAppRouter", "GetPageParameters耗时");
    		if(pageParams != null) {
    			routerShortLink = (String) pageParams.get(InitParam_RouterShortLink);
    			if(CmnUtil.isStringEmpty(routerShortLink))
    				return null;
    		}else {
    			return null;
    		}
		}
		return getAppRouterByShortLink(routerShortLink);
	}
	
	private static Map<String,String> parseScene(String scene) {
		Map<String,String> entity = new HashMap<>();
		if (scene == null) {
			return entity;
		}
		scene = scene.trim();
		if (scene.equals("")) {
			return entity;
		}
		String[] urlParts = scene.split("\\?");
//  entity.baseUrl = urlParts[0];
		//没有参数
		if (urlParts.length == 1) {
			return entity;
		}
		//有参数
		if(urlParts[1].contains("#/")) {
			urlParts[1] = urlParts[1].substring(0,urlParts[1].lastIndexOf("#/"));
		}
		String[] params = urlParts[1].split("&");
		for (String param : params) {
			String[] keyValue = param.split("=");
			entity.put(keyValue[0], keyValue[1]);
		}
		
		return entity;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String str = "eyJjZWxsIjoiY2VsbC5mZS5ncGYuZGMuYmFzaWMuSUFwcEZlTG9naW5QYWdlIiwidXJsIjoid3M6Ly8xMC4wLjAuMjI6MjAyMCIsImluaXRQYXJhbSI6eyJzeXN0ZW1VdWlkIjoiYjQ0NjYzNThfYzAxNF80MGQyXzgwNGRfMjc2MjA1NTMxODViIiwiYXBwQ29kZSI6InRlc3RBcHAiLCJhcHBTZXNzaW9uS2V5IjoiIn0sInBhZ2VQYXJhbXMiOnsicm91dGVyU2hvcnRMaW5rIjoiUXJxTW5xIn19";
		System.out.println(ToolUtilities.base64ToString(str));
	}
	
	public static String buildRouterShortLink(AppRouter router) throws Exception {
		try(IJson json = IJsonService.get().getJson()){
			String routerJsonStr = json.toJson(router);
			String routerShortLink = IShortLinkService.get().createShortLink(routerJsonStr,-1L);
			return routerShortLink;
		}
	}
	
	public static AppRouter getAppRouterByShortLink(String routerShortLink)throws Exception{
		try(IJson json = IJsonService.get().getJson()){
			String longLink = IShortLinkService.get().getLongLink(routerShortLink);
			AppRouter router = json.fromJson(longLink,AppRouter.class);
			return router;
		}
	}
	/**
	 * 获取默认的遮罩层配置，默认遮罩是环形Loading，移动端是静态文本
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static LoadingMaskConfigDto getDefaultLoadingMaskConfig(Context context) throws Exception {
		ApplicationSetting setting = getSetting(context);
		LoadingMaskConfigDto config = null;
		if(setting != null) {
			config = (LoadingMaskConfigDto) getAppCache(context, setting.getUuid(), CacheKey_DefaultLoadingMask);
			if(config == null) {
				AppViewSetting viewSetting = setting.getAppViewSetting();
				String loadingType = viewSetting.getLoadingType();
				String loadingText = null;
				if(CmnUtil.isStringEmpty(loadingType)) {
					if(FePaltformUtil.isMiniProgram(context) || FePaltformUtil.isMobile(context)) {
						loadingType = LoadingType.Text.name();
						I18nIntf i18n = getAppFeI18n(context);
						if(i18n != null)
							loadingText = i18n.format(GpfDCBasicI18n.Loading);
					}else {
						loadingType = LoadingType.Circular.name();
					}
				}
				config = new LoadingMaskConfigDto();
				config.setLoadingText(loadingText);
				config.setLoadingType(loadingType)
				.setMinDisplayTime(viewSetting.getLoadingMinDisplayTime())
				;
				putAppCache(context, setting.getUuid(), CacheKey_DefaultLoadingMask, config);
			}
		}else {
			config = new LoadingMaskConfigDto();
			config.setLoadingType(LoadingType.Circular);
		}
		return config;
	}
	
	public void setMiniProgramSharePath(PanelContext panelContext,String title,String viewModel,String viewCode,String modelId,String uuid) throws Exception {
		if(!FePaltformUtil.isMiniProgram(panelContext))
			return;
		ApplicationSetting shareAppSetting = getSetting(panelContext);
		AppRouter router = IApplicationService.get().buildAppRouter(title, viewModel, viewCode, modelId, uuid);
		String routerShortLink = AppCacheUtil.buildRouterShortLink(router);
		Map<String,Object> projectConfig = new LinkedHashMap<>();
		//获取webApps目录下的service_uri.config作为url配置参数
		AppCacheMgrIntf cacheMgr = getAppCacheMgr(panelContext, shareAppSetting);
		String wsUrl = cacheMgr.getWebSocketUrl(panelContext);
//		String wsUrl = QueryWebSocketConnectUrl.query(panelContext);
		projectConfig.put("url", wsUrl);
		projectConfig.put("cell", IAppFeLoginPage.class.getName());
		Map<String,Object> initParam = new LinkedHashMap<>();
		initParam.put(AppCacheUtil.InitParam_SystemUuid, shareAppSetting.getUuid());
		initParam.put(AppCacheUtil.InitParam_AppCode, shareAppSetting.getName());
		initParam.put(AppCacheUtil.InitParam_AppSessionKey, shareAppSetting.getSessionKey());
		try(IJson json = IJsonService.get().getJson()){
			initParam.put(AppCacheUtil.InitParam_RouterShortLink, routerShortLink);
			projectConfig.put("initParam", initParam);
		}
		String weAppLink = buildMiniProgramPath(projectConfig);
		
		WxSharePageInfo sharePageInfo = new WxSharePageInfo();
		sharePageInfo.setTitle(title);
		sharePageInfo.setPath(weAppLink);
//		sharePageInfo.setImageUrl(wxFilePath);
		WxMiniCommand.setSharePageMessage(panelContext.getChannel(), sharePageInfo);
	}
	
	/**
	 * 构建小程序分享链接
	 * @param projectConfig
	 * @return
	 * @throws Exception
	 */
	public static String buildMiniProgramPath(Map<String, Object> projectConfig) throws Exception {
		String shareLink = "pages/index/index?#query#";
		String query = "";
		try(IJson json = IJsonService.get().getJson()){
			String projectConfigStr = json.toJson(projectConfig);
			query = "projectConfig="+projectConfigStr;
		}
		Map<String,String> regexMap = new LinkedHashMap<>();
		regexMap.put("#query#", query);
		shareLink = ToolUtilities.replaceAll(shareLink, regexMap);
		return shareLink;
	}
	/**
	 * 应用是否开启调试模式
	 * @param context
	 * @return
	 */
	public static boolean isDebugMode(Context context) {
		Map<String,Object> appInfo = (Map<String, Object>) context.getAppInfo();
		if(appInfo == null)
			return false;
		return CmnUtil.getBoolean(appInfo.get("debug"), false);
	}
	/**
	 * 应用是否开启弹窗路由，除了web端，其他端都是直接开启，web端可通过应用配置项控制开关，默认关闭
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static boolean isEnablePopupRouter(Context context) throws Exception {
		if(FePaltformUtil.isWeb(context) && !FePaltformUtil.isMiniProgram(context)) {
			ApplicationSetting setting = getSetting(context);
			if(setting != null) {
				AppViewSetting viewSetting = setting.getAppViewSetting();
				return viewSetting.isEnablePopupRouterInWeb();
			}
			return false;
		}else {
			return true;
		}
	}
	
	public static User getCurrentUser(Context context) throws Exception {
		if(CmnUtil.isStringEmpty(context.getCurrentUser()))
			return null;
		ApplicationSetting setting = getSetting(context);
		if(setting != null) {
			String cacheKey = "$currentUser";
			User user = (User) getAppCache(context, setting.getUuid(), cacheKey);
			if(user == null) {
				try(IDao dao = IDaoService.newIDao()){
					user = IUserMgr.get().queryUserByCode(dao, setting.getUserModelId(), context.getCurrentUser());
					if(user != null)
						putAppCache(context, setting.getUuid(), cacheKey, user);
				}
			}
			return user;
		}
		return null;
	}
	
	public final static String LocalStorage_CurrentLanguage = "currentLanguage";
	/**
	 * 获取应用当前的语言环境
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static String getCurrentLanguage(Context context) throws Exception {
		ApplicationSetting appSetting = getSetting(context);
		return getAppCacheMgr(context,appSetting).getCurrentLanguage(context);
	}
	/**
	 * 设置应用当前的语言环境
	 * @param context
	 * @param languageCode
	 * @return
	 * @throws Exception
	 */
	public static void setCurrentLanguage(Context context,String languageCode) throws Exception {
		ApplicationSetting appSetting = getSetting(context);
		getAppCacheMgr(context,appSetting).setCurrentLanguage(context, languageCode);
	}
	
	public static LocaleDto convert2Locale(String languageCode) {
		if(CmnUtil.isStringEmpty(languageCode)) {
			return null;
		}
		String[] languageCodes = languageCode.split("_");
		if(languageCodes.length == 2) {
			return new LocaleDto().setLanguageCode(languageCodes[0]).setCountryCode(languageCodes[1]);
		}
		return null;
	}
	
	public final static String CacheKey_I18nMap = "I18nMap";
	public static Map<String,IAppFeI18n> getAppFeI18nMap(Context context) throws Exception{
		ApplicationSetting appSetting = getSetting(context);
		if(appSetting == null)
			return null;
		Map<String,IAppFeI18n> i18nMap = (Map<String, IAppFeI18n>) getAppCache(context, appSetting.getUuid(), CacheKey_I18nMap);
		if(i18nMap == null) {
			i18nMap = new RpcMap<>();
			putAppCache(context, appSetting.getUuid(), CacheKey_I18nMap, i18nMap);
		}
		return i18nMap;
	}
	/**
	 * 获取应用的国际化资源
	 * @param context
	 * @return
	 * @throws Exception 
	 */
	public static IAppFeI18n defaultAppI18n = null;
	private static IAppFeI18n getDefaultAppFeI18n() {
		if(defaultAppI18n == null) {
			AppFeI18n i18n = new AppFeI18n(null);
			defaultAppI18n = new CAppFeI18n(i18n);
		}
		return defaultAppI18n;
	}
	public static IAppFeI18n getAppFeI18n(Context context) throws Exception {
		ApplicationSetting appSetting = getSetting(context);
		if(appSetting == null || !appSetting.getAppViewSetting().isEnableI18n()) {
			//获取I18n
			return getDefaultAppFeI18n();
		}
		String languageCode = getCurrentLanguage(context);
		if(CmnUtil.isStringEmpty(languageCode)) {
			return getDefaultAppFeI18n();
		}
		Map<String,IAppFeI18n> i18nMap = getAppFeI18nMap(context);
		if(i18nMap.containsKey(languageCode)) {
			IAppFeI18n i18n = i18nMap.get(languageCode);
			return i18n;
		}
		Map<String,I18nResSettingDto> i18nSettingMap = appSetting.getI18nResSettingMap();
		IAppFeI18n i18n = null;
		if(!i18nSettingMap.containsKey(languageCode)) {
			return getDefaultAppFeI18n();
		}else {
			I18nResSettingDto setting = i18nSettingMap.get(languageCode);
			List<AttachData> basicI18nSettings = IApplicationService.get().queryBasicI18nSettingByLanguage(languageCode);
			List<AttachData> allI18nSettings = new ArrayList<>();
			if(!CmnUtil.isCollectionEmpty(basicI18nSettings))
				allI18nSettings.addAll(basicI18nSettings);
			if(!CmnUtil.isCollectionEmpty(setting.getResFile())){
				allI18nSettings.addAll(setting.getResFile());
			}
			if(!allI18nSettings.isEmpty()) {
				i18n = new CAppFeI18n(new AppFeI18n(allI18nSettings));
			}else {
				i18n = new CAppFeI18n(new AppFeI18n(null));
			}
		}
		i18nMap.put(languageCode, i18n);
		putAppCache(context, appSetting.getUuid(), CacheKey_I18nMap, i18nMap);
		return i18n;
	}
	/**
	 * 获取当前应用默认的表格扩展配置
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static TableViewSetting getAppTableSetting(Context context) throws Exception {
		ApplicationSetting appSetting = getSetting(context);
		if(appSetting == null)
			return null;
		AppCacheMgrIntf cacheMgr = getAppCacheMgr(context, appSetting);
		return cacheMgr.getAppTableViewSetting(context);
	}
	public static TableViewSetting newTableSetting(Context context) throws Exception {
		TableViewSetting setting = getAppTableSetting(context);
		if(setting == null) {
			return new TableViewSetting();
		}else {
			return ToolUtilities.clone(setting);
		}
	}
	/**
	 * 获取当前应用默认的表格扩展配置
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static FormViewSetting getAppFormSetting(Context context) throws Exception {
		ApplicationSetting appSetting = getSetting(context);
		if(appSetting == null)
			return null;
		AppCacheMgrIntf cacheMgr = getAppCacheMgr(context, appSetting);
		return cacheMgr.getAppFormViewSetting(context);
	}
	
	public static FormViewSetting newFormSetting(Context context) throws Exception {
		FormViewSetting setting = getAppFormSetting(context);
		if(setting == null) {
			return new FormViewSetting();
		}else {
			return ToolUtilities.clone(setting);
		}
	}
}
