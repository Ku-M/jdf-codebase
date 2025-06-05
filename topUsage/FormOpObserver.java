package gpf.dc.intf;

import cmn.anotation.ClassDeclare;
import cmn.dto.Progress;
import cmn.dto.model.extend.intf.ObserverContext;
/**
 * 数据操作监听，包括数据模型、动作模型、用户模型、组织用户，角色、PDC的数据
* @author chenxb
 *
 */
@ClassDeclare(label = "数据操作监听"
,what="数据操作监听，包括数据模型、动作模型、用户模型、组织用户，角色、PDC的数据"
, why = ""
, how = ""
,developer="陈晓斌"
,version = "1.0"
,createTime = "205-01-24"
,updateTime = "205-01-24")
public interface FormOpObserver extends GpfDataOpObserver{
	
	public final static String ContextKey_FormModelId = "formModelID";
	public final static String ContextKey_Cnd = "cnd";
	public final static String ContextKey_Uuid = "uuid";
	public final static String ContextKey_MapValue = "mapValue";
	
	
	/**
	 * 在表单提交创建前执行
	 * @param prog
	 * @param context
	 * @throws Exception
	 */
	public void onBeforeCreate(Progress prog,ObserverContext context)throws Exception;
	/**
	 * 在表单提交创建后执行
	 * @param prog
	 * @param context
	 * @throws Exception
	 */
	public void onAfterCreate(Progress prog,ObserverContext context)throws Exception;
	/**
	 * 在表单批量提交创建前执行，数据类型： List<Form>
	 * @param prog
	 * @param context
	 * @throws Exception
	 */
	public void onBeforeBatchCreate(Progress prog,ObserverContext observerContext)throws Exception;
	/**
	 * 在表单批量提交创建后执行，数据类型： List<Form>
	 * @param prog
	 * @param context
	 * @throws Exception
	 */
	public void onAfterBatchCreate(Progress prog,ObserverContext observerContext)throws Exception;
	/**
	 * 表单更新前执行
	 * @param prog
	 * @param context
	 * @throws Exception
	 */
	public void onBeforeUpdate(Progress prog,ObserverContext context)throws Exception;
	/**
	 * 表单更新后执行
	 * @param prog
	 * @param context
	 * @throws Exception
	 */
	public void onAfterUpdate(Progress prog,ObserverContext context)throws Exception;
	/**
	 * 在表单批量提交更新前执行，批量更新接口无form参数，通过条件进行数据更新，可从上下文获取相关的执行参数，如下：
	 * 表单模型ID： FormOpObserver.ContextKey_FormModelId
	 * 查询条件：FormOpObserver.ContextKey_Cnd
	 * 更新值：FormOpObserver.ContextKey_MapValue
	 * @param prog
	 * @param context
	 * @throws Exception
	 */
	public void onBeforeBatchUpdate(Progress prog,ObserverContext observerContext)throws Exception;
	/**
	 * 在表单批量提交更新后执行，批量更新接口无form参数，通过条件进行数据更新，可从上下文获取相关的执行参数，如下：
	 * 表单模型ID： FormOpObserver.ContextKey_FormModelId
	 * 查询条件：FormOpObserver.ContextKey_Cnd
	 * 更新值：FormOpObserver.ContextKey_MapValue
	 * @param prog
	 * @param context
	 * @throws Exception
	 */
	public void onAfterBatchUpdate(Progress prog,ObserverContext observerContext)throws Exception;
	/**
	 * 表单删除前执行
	 * @param prog
	 * @param context
	 * @throws Exception
	 */
	public void onBeforeDelete(Progress prog,ObserverContext context)throws Exception;
	/**
	 * 表单删除后执行
	 * @param prog
	 * @param context
	 * @throws Exception
	 */
	public void onAfterDelete(Progress prog,ObserverContext context)throws Exception;
	/**
	 * 批量导入后执行，数据类型：List<Form>
	 * @param prog
	 * @param context
	 * @throws Exception
	 */
	public void onAfterImport(Progress prog,ObserverContext context)throws Exception;
	/**
	 * 表单批量删除前执行，带有cnd查询条件或uuids,，批量更新接口无form参数，通过条件进行数据更新，可从上下文获取相关的执行参数，如下：
	 * 表单模型ID： FormOpObserver.ContextKey_FormModelId
	 * 查询条件：FormOpObserver.ContextKey_Cnd
	 * 更新值：FormOpObserver.ContextKey_MapValue
	 * @param prog
	 * @param context
	 * @throws Exception
	 */
	public void onBeforeBatchDelete(Progress prog,ObserverContext context)throws Exception;
	/**
	 * 表单批量删除后执行，带有cnd查询条件或uuids
	 * @param prog
	 * @param context
	 * @throws Exception
	 */
	public void onAfterBatchDelete(Progress prog,ObserverContext context)throws Exception;
}
