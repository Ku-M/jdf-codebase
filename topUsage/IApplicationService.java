package cell.fe.gpf.dc.basic;

import java.util.List;
import java.util.Map;
import java.util.Set;

import bap.cells.Cells;
import cell.ServiceCellIntf;
import cell.cdao.IDao;
import cmn.dto.PreloadTreeNode;
import cmn.dto.Progress;
import fe.cmn.app.Context;
import gpf.adur.data.AttachData;
import gpf.adur.data.Form;
import gpf.adur.user.User;
import gpf.dc.basic.expimp.VisitedContext;
import gpf.dc.basic.param.view.dto.AppRouter;
import gpf.dc.basic.param.view.dto.ApplicationSetting;
import gpf.dc.basic.param.view.dto.MenuNodeDto;
import gpf.dc.basic.privilege.dto.AppPrivilegeDto;
import gpf.dc.expimp.ExportSetting;
import web.dto.Pair;

public interface IApplicationService extends ServiceCellIntf{

	public static IApplicationService get() {
		return Cells.get(IApplicationService.class);
	}
	
	public ApplicationSetting queryApplicationSetting(String appUuid)throws Exception;
	
	public ApplicationSetting queryApplicationSettingByCode(IDao dao,String code) throws Exception;
	
	public void deployApp(Form form)throws Exception;
	/**
	 * 查询应用的菜单uuid路径map
	 * @param appUuid
	 * @return	Map key: 菜单uuid , value : 菜单路径
	 * @throws Exception
	 */
	public Map<String,String> queryAppMenuUuidPathMap(ApplicationSetting appSetting)throws Exception;
	
	public List<PreloadTreeNode<Form>> queryAppMenus(ApplicationSetting appSetting,String user,Context context,AppPrivilegeDto appPrivilege)throws Exception;
	
	public List<MenuNodeDto> queryAppMenuNodes(ApplicationSetting appSetting, String user,Context context,AppPrivilegeDto appPrivilege) throws Exception;
	
	public MenuNodeDto queryAppMenuNode(String menuUuid)throws Exception;
	
	public AppPrivilegeDto queryMenuPrivileges(ApplicationSetting appSetting,String user,Context context) throws Exception;
	/**
	 * 检查当前用户模型是否支持微信账号
	 * @param userModelId
	 * @return
	 * @throws Exception
	 */
	public boolean isSupportWeChatAccountAuthentication(String userModelId)throws Exception;
	/**
	 * 当前用户是否绑定小程序微信账号
	 * @param user
	 * @param appId
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public boolean isBoundWeChatAccount(User user,String appId,String openId)throws Exception;
	/**
	 * 查询微信小程序用户绑定的用户
	 * 注意：需要用户模型中定义有微信账号信息的嵌套模型属性
	 * @param userModelId
	 * @param appId	小程序的APPID
	 * @param openId	微信用户在小程序里的openId
	 * @return
	 * @throws Exception
	 */
	public User queryWeChatMiniProgramBindingUser(String userModelId,String appId,String openId) throws Exception;
	/**
	 * 绑定微信小程序账号和用户
	 * 注意：需要用户模型中定义有微信账号信息的嵌套模型属性
	 * @param userModelId
	 * @param userCode
	 * @param appId	小程序的APPID
	 * @param openId	微信用户在小程序里的openId
	 * @throws Exception
	 */
	public void bindingWeChatMiniProgramAccountToUser(String userModelId,String userCode,String appId,String openId)throws Exception;
	
	/**
	 * 解除微信小程序账号和用户的绑定
	 * 注意：需要用户模型中定义有微信账号信息的嵌套模型属性
	 * @param userModelId
	 * @param userCode
	 * @param appId
	 * @param openId
	 * @throws Exception
	 */
	public void unbindWeChatMiniProgramAccountToUser(String userModelId,String userCode,String appId,String openId)throws Exception;
	
	public Pair<String, byte[]> exportApplication(String appCode)throws Exception;
	
	public void importApplication(Pair<String, byte[]> file,String userModelId,String orgModelId)throws Exception;
	
	public AppRouter buildAppRouter(String title,String viewModeId,String viewCode,String modelId,String uuid)throws Exception;
	/**
	 * 预加载应用的模型和动作实例缓存等内容
	 * @param appCode
	 * @throws Exception
	 */
	public void preloadAppCache(Progress prog,List<String> appCodes)throws Exception;
	/**
	 * 解析应用及依赖动作作为导出工程包配置
	 * @param prog
	 * @param appUuid
	 * @return
	 * @throws Exception
	 */
	public ExportSetting buildExportSetting(Progress prog,String appUuid)throws Exception;

	public ExportSetting buildExportSetting(Progress prog, String appUuid, VisitedContext visitedContext)
			throws Exception;
	/**
	 * 解析依赖动作作为工程包配置
	 * @param prog
	 * @param dao
	 * @param actionModelId
	 * @param actionCode
	 * @param visitedActions
	 * @param visitedPdfs
	 * @param exportSetting
	 * @throws Exception
	 */
	public ExportSetting buildExportSetting(Progress prog, IDao dao, String actionModelId, String actionCode,
			Set<String> visitedActions, Set<String> visitedPdfs, Set<String> roleCodes, ExportSetting exportSetting,
			boolean onlyView) throws Exception;
	/**
	 * 解析PDF依赖动作作为工程包配置
	 * @param prog
	 * @param dao
	 * @param pdfUuid
	 * @param visitedPdfs
	 * @param exportSetting
	 * @throws Exception
	 */
	public ExportSetting buildExportSettingByPDF(Progress prog,IDao dao,String pdfUuid,Set<String> visitedPdfs,ExportSetting exportSetting) throws Exception;
	
	/**
	 * 导出应用国际化资源配置文件
	 * @param prog
	 * @param appCode
	 * @return
	 * @throws Exception
	 */
	public Pair<String,byte[]> exportI18nSettingTemplate(Progress prog,String appCode)throws Exception;
	/**
	 * 根据语言查找相应的基础包资源文件
	 * @param languageCode
	 * @return
	 * @throws Exception
	 */
	public List<AttachData> queryBasicI18nSettingByLanguage(String languageCode)throws Exception;
}
