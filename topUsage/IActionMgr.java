package cell.gpf.adur.action;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nutz.dao.Cnd;

import bap.cells.Cells;
import cell.ServiceCellIntf;
import cell.cdao.IDao;
import cmn.dto.Progress;
import gpf.adur.action.Action;
import gpf.adur.action.ActionModel;
import gpf.adur.data.ResultSet;
import gpf.dc.intf.ExportImportIntf;
import gpf.dc.intf.FormOpObserver;
import gpf.dto.cfg.runtime.RuntimeContextIntf;
import web.dto.Pair;
/**
 * 动作模型管理操作
 * 管理动作模型和动作实例的CURD操作
 * @author chenxb
 *
 */
public interface IActionMgr extends ServiceCellIntf{

	static IActionMgr get() {
		return Cells.get(IActionMgr.class);
	}
	/**
	 * 获取动作根模型ID
	 * @return
	 */
	public String getRootActionModelId();
	/**
	 * 获取动作根模型
	 * @return	动作模型
	 * @throws Exception
	 */
	public ActionModel getActionModelRoot()throws Exception;
	/**
	 * 判断是否动作模型
	 * @param modelId	模型ID
	 * @return
	 * @throws Exception
	 */
	public boolean isActionModel(String modelId)throws Exception;
	/**
	 * 复制动作实例，排除uuid和code,内部存在引用动作实例时将生成引用动作实例的副本
	 * @param action	动作实例
	 * @return	动作实例副本
	 * @throws Exception
	 */
	public Action copyAction(Action action)throws Exception;
	/**
	 * 创建动作模型
	 * @param actionModel	动作模型
	 * @return	创建后的动作模型
	 * @throws Exception
	 */
	public ActionModel createActionModel(ActionModel actionModel) throws Exception;
	/**
	 * 更新动作模型
	 * @param prog	进度通知对象
	 * @param actionModel	动作模型
	 * @return
	 * @throws Exception
	 */
	public ActionModel updateActionModel(Progress prog,ActionModel actionModel) throws Exception;
	/**
	 * 更新动作模型模板数据，当动作模型不需要更新属性，只需要更新java参数映射时使用
	 * @param prog	进度通知对象
	 * @param dao	
	 * @param actionModel	动作模型
	 * @throws Exception
	 */
	public void updateActionModelTemplateData(Progress prog,IDao dao,ActionModel actionModel) throws Exception;
	
	/**
	 * 重命名模型
	 * @param prog	进度通知对象
	 * @param renameModels	key:模型ID, value:(新模型ID,模型中文名)
	 * @throws Exception
	 */
	public void renameActionModel(Progress prog,Map<String,Pair<String, String>> renameModels)throws Exception;
	
	/**
	 * 重命名模型ID
	 * @param prog	进度通知对象
	 * @param renameModels	key:模型ID, value:新模型ID
	 * @throws Exception
	 */
	public void renameActionModelId(Progress prog,Map<String,String> renameModels)throws Exception;
	/**
	 * 删除动作模型	
	 * @param modelId	模型ID
	 * @throws Exception
	 */
	public void deleteActionModel(String modelId) throws Exception;
	/**
	 * 强制删除动作模型，包含子模型
	 * @param prog	进度通知对象
	 * @param actionModelIds	模型ID
	 * @throws Exception
	 */
	public void forceDeleteActionModel(Progress prog,Set<String> actionModelIds)throws Exception;
	/**
	 * 查询动作模型
	 * @param modelId	模型ID
	 * @return
	 * @throws Exception
	 */
	public ActionModel queryActionModel(String modelId)throws Exception;
	/**
	 * 根据模型uuid查询动作模型
	 * @param uuid	模型uuid
	 * @return
	 * @throws Exception
	 */
	public ActionModel queryActionModelByUuid(String uuid)throws Exception;
	/**
	 * 查询子动作模型
	 * @param modelId
	 * @return	子动作模型列表
	 * @throws Exception
	 */
	public List<ActionModel> queryChildActionModels(String modelId)throws Exception;
	/**
	 * 查询所有动作模型
	 * @return	动作模型列表
	 * @throws Exception
	 */
	public List<ActionModel> queryAllActionModels()throws Exception;
	/**
	 * 根据包路径查找动作模型
	 * @param packagePath	模型包路径
	 * @return	动作模型列表
	 * @throws Exception
	 */
	public List<ActionModel> queryActionModelByPackage(String packagePath)throws Exception;
	/**
	 * 分页查询动作模型结果集
	 * @param parentIds	父模型ID
	 * @param packagePath	模型包路径，填null时匹配所有
	 * @param keyword	搜索关键字，匹配模型名称或模型标签，填null时匹配所有
	 * @param pageNo	查询页码，页码从1开始
	 * @param pageSize	分页大小
	 * @return	表单模型结果集
	 * @throws Exception
	 */
	public ResultSet<ActionModel> queryActionModelPage(List<String> parentIds, String packagePath, String keyword,int pageNo,int pageSize)throws Exception;
	/**
	 * 判断模型是否是指定模型的子模型或孙子模型
	 * @param actionModelId	模型
	 * @param targetActionModelId	目标模型
	 * @return
	 * @throws Exception
	 */
	public boolean isInheritForm(String actionModelId,String targetActionModelId) throws Exception;
	/**
	 * 查询模型的继承路径
	 * @param actionModelId	模型ID
	 * @return	模型的祖先模型ID列表，从根路径开始，当前模型ID为列表末尾值
	 * @throws Exception
	 */
	public List<String> queryInheritPaths(String actionModelId) throws Exception ;
	/**
	 * 构建动作实例内存对象，携带动作实例模板信息
	 * @param dao
	 * @param actionModelId	动作模型ID
	 * @return	动作实例内存对象
	 * @throws Exception
	 */
	public Action newAction(IDao dao,String actionModelId)throws Exception;
	/**
	 * 创建动作实例
	 * @param dao
	 * @param action
	 * @return
	 * @throws Exception
	 */
	public Action createAction(IDao dao,Action action)throws Exception;
	/**
	 * 创建动作实例,携带动作操作观察者，在动作实例操作时执行相应回调处理
	 * @param prog	进度通知对象，没有时可传null
	 * @param dao	
	 * @param action	动作实例
	 * @param observer	动作操作观察者
	 * @return
	 * @throws Exception
	 */
	public Action createAction(Progress prog,IDao dao,Action action,FormOpObserver observer)throws Exception;
	/**
	 * 更新动作实例
	 * @param action	动作实例
	 * @return
	 * @throws Exception
	 */
	public Action updateAction(IDao dao,Action action)throws Exception;
	/**
	 * 更新动作实例,携带动作操作观察者，在动作实例操作时执行相应回调处理
	 * @param prog	进度通知对象，没有时可传null
	 * @param dao
	 * @param action	动作实例
	 * @param observer	动作操作观察者
	 * @return
	 * @throws Exception
	 */
	public Action updateAction(Progress prog,IDao dao,Action action,FormOpObserver observer)throws Exception;
	/**
	 * 查询动作实例
	 * @param actionModelId	动作模型ID
	 * @param uuid	动作实例uuid
	 * @return
	 * @throws Exception
	 */
	public Action queryAction(IDao dao,String actionModelId,String uuid)throws Exception;
	/**
	 * 查询动作属性值
	 * @param dao
	 * @param actionModelId	动作模型ID
	 * @param cnd	条件
	 * @param fields	查询属性
	 * @return	携带指定属性的动作实例
	 * @throws Exception
	 */
	public Action queryActionFileValue(IDao dao,String actionModelId,Cnd cnd,String[] fields)throws Exception;
	/**
	 * 根据编号查询动作实例
	 * @param dao
	 * @param actionModelId	动作模型ID
	 * @param code	编号
	 * @return
	 * @throws Exception
	 */
	public Action queryActionByCode(IDao dao,String actionModelId,String code)throws Exception;
	/**
	 * 根据编号查询动作实例，可指定是否查询缓存，当缓存被污染时，可指定不查询缓存刷新被污染的缓存
	 * @param dao
	 * @param actionModelId	动作模型ID
	 * @param code	编号
	 * @param queryCache	是否查询缓存
	 * @return
	 * @throws Exception
	 */
	public Action queryActionByCode(IDao dao,String actionModelId,String code,boolean queryCache)throws Exception;
	/**
	 * 根据编号查询动作实例uuid
	 * @param dao
	 * @param actionModelId	动作模型ID
	 * @param code	编号
	 * @return
	 * @throws Exception
	 */
	public String queryActionUuidByCode(IDao dao,String actionModelId,String code)throws Exception;
	/**
	 * 查询动作实例分页列表
	 * @param dao
	 * @param actionModelId	动作模型ID
	 * @param cnd	条件
	 * @param pageNo	分页页码，页码从1开始
	 * @param pageSize	分页大小
	 * @return
	 * @throws Exception
	 */
	public ResultSet<Action> queryActionPage(IDao dao,String actionModelId,Cnd cnd,int pageNo,int pageSize)throws Exception;
	/**
	 * 查询动作实例分页列表
	 * @param dao
	 * @param actionModelId	动作模型ID
	 * @param cnd	条件
	 * @param pageNo	分页页码，页码从1开始
	 * @param pageSize	分页大小
	 * @param queryRowCount	是否查询记录数
	 * @param compoundField	是否查询嵌套属性数据
	 * @param fields	指定查询的属性，不传时查询所有属性
	 * @return
	 * @throws Exception
	 */
	public ResultSet<Action> queryActionPage(IDao dao,String actionModelId,Cnd cnd,int pageNo,int pageSize,boolean queryRowCount,boolean compoundField,String... fields)throws Exception;
	/**
	 * 统计满足查询条件的记录数
	 * @param dao
	 * @param actionModelId	动作模型ID
	 * @param cnd	条件
	 * @return
	 * @throws Exception
	 */
	public long countAction(IDao dao,String actionModelId,Cnd cnd)throws Exception;
	/**
	 * 自定义SQL查询动作实例分页列表
	 * @param dao
	 * @param actionModelId	动作模型ID
	 * @param sql	自定义查询SQL
	 * @param extFields 附带查询列，需要在自定义查询SQL中有定义该列的查询语句，查询记录数时需要携带totalCount，其他附带的查询列值都将设置在Form的extFields上
	 * @param replaceParam
	 * @param queryParam
	 * @param cnd	条件
	 * @param pageNo	分页页码，页码从1开始
	 * @param pageSize	页码大小
	 * @return
	 * @throws Exception
	 */
	public ResultSet<Action> queryActionPageBySql(IDao dao,String actionModelId, String querySql,Set<String> extFields,
			Map<String, Object> replaceParam, Map<String, Object> queryParam, Cnd cnd,int pageNo, int pageSize)throws Exception;
	/**
	 * 删除动作实例
	 * @param dao	
	 * @param actionModelId	动作模型ID
	 * @param uuid	动作实例uuid
	 * @throws Exception
	 */
	public void deleteAction(IDao dao,String actionModelId,String uuid)throws Exception;
	/**
	 * 删除动作实例，携带动作操作观察者，在动作操作时执行相应回调
	 * @param prog	进度通知对象
	 * @param dao	
	 * @param actionModelId	动作模型ID
	 * @param uuid	动作实例uuid
	 * @param observer	动作操作观察者
	 * @throws Exception
	 */
	public void deleteAction(Progress prog,IDao dao,String actionModelId,String uuid,FormOpObserver observer)throws Exception;
	/**
	 * 根据条件删除动作
	 * @param dao
	 * @param actionModelId	动作模型ID
	 * @param cnd	条件
	 * @throws Exception
	 */
	public void deleteActionByCnd(IDao dao,String actionModelId,Cnd cnd)throws Exception;
	/**
	 * 根据条件删除动作，携带动作操作观察者，在动作操作时执行相应回调
	 * @param prog	进度通知对象
	 * @param dao
	 * @param actionModelId	动作模型ID
	 * @param cnd	条件
	 * @param observer	动作操作观察者
	 * @throws Exception
	 */
	public void deleteActionByCnd(Progress prog,IDao dao,String actionModelId,Cnd cnd,FormOpObserver observer)throws Exception;

	/**
	 * 导出动作模型
	 * @param prog	进度通知对象
	 * @param expImpIntf	重载的导入导出接口，传null使用默认的导入导出接口ActionModelExpImp
	 * @param modelIds	动作模型ID
	 * @return	动作模型压缩包数据
	 * @throws Exception
	 */
	public Pair<String, byte[]> exportActionModel(Progress prog,ExportImportIntf expImpIntf,List<String> modelIds)throws Exception;
	/**
	 * 导入动作模型
	 * @param prog	进度通知对象
	 * @param expImpIntf	重载的导入导出接口，传null使用默认的导入导出接口ActionModelExpImp
	 * @param zipFile	动作模型压缩包数据
	 * @throws Exception
	 */
	public void importActionModel(Progress prog,ExportImportIntf expImpIntf,Pair<String, byte[]> zipFile)throws Exception;
	/**
	 * 导出动作实例数据
	 * @param prog	进度通知对象
	 * @param expImpIntf	重载的导入导出接口，传null使用默认的导入导出接口ActionDataExpImp
	 * @param modelId	动作模型ID
	 * @param cnd	条件
	 * @return	动作实例数据压缩包
	 * @throws Exception
	 */
	public Pair<String, byte[]> exportActionData(Progress prog,ExportImportIntf expImpIntf,String modelId,Cnd cnd)throws Exception;
	/**
	 * 导入动作实例数据
	 * @param prog	进度通知对象
	 * @param expImpIntf	重载的导入导出接口，传null使用默认的导入导出接口ActionDataExpImp
	 * @param modelId	动作模型ID
	 * @param zipFile	动作实例数据压缩包
	 * @throws Exception
	 */
	public void importActionData(Progress prog,ExportImportIntf expImpIntf,String modelId,Pair<String, byte[]> zipFile)throws Exception;
	/**
	 * 批量导入动作实例数据前的预处理操作，在导入动作实例数据时调用
	 * @param prog	进度通知对象
	 * @param dao
	 * @param list	动作实例
	 * @throws Exception
	 */
	public void beforeBatchImportActionDatas(Progress prog,IDao dao,List<Action> list) throws Exception;
	/**
	 * 批量导入动作实例数据
	 * @param prog	进度通知对象
	 * @param dao
	 * @param list	动作实例
	 * @throws Exception
	 */
	public void batchImportActionDatas(Progress prog,IDao dao,List<Action> list) throws Exception;
	
	//----------------------------------动作实例运行------------------------------------------------
	/**
	 * 执行动作实例
	 * @param data	动作实例
	 * @param rtx	运行上下文，构建方式如下：
	 * IDCRuntimeContext rtx = IPDFRuntimeMgr.get().newRuntimeContext();
	 * try(IDao dao = IDaoService.newIDao()){
	 * 	rtx.setDao(dao);
	 *  //筹备动作实例执行上下文参数，流程uuid等
	 *  rtx.setPdfUuid("");
	 *  rtx.setOperator("");//操作人的编号
	 * }
	 * @return
	 * @throws Exception
	 */
	public Object executeAction(Action data,RuntimeContextIntf rtx)throws Exception;
}
