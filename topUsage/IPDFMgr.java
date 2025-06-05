package cell.gpf.dc.config;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nutz.dao.Cnd;

import bap.cells.Cells;
import cell.ServiceCellIntf;
import cell.cdao.IDao;
import cmn.dto.Progress;
import gpf.adur.data.FormModel;
import gpf.adur.data.ResultSet;
import gpf.adur.data.TableIndex;
import gpf.dc.config.OperateLogStatusHookDto;
import gpf.dc.config.PDC;
import gpf.dc.config.PDF;
import gpf.dc.intf.ExportImportIntf;
import web.dto.Pair;

/**
 * 流程定义模型（PDF）管理接口
 *  提供流程定义模型（PDF）的CRUD操作，以及模型继承路径查询、表单模型关联、操作日志配置等功能。
 */
public interface IPDFMgr extends ServiceCellIntf {

	static IPDFMgr get() {
		return Cells.get(IPDFMgr.class);
	}

	/**
	 * 获取根流程定义模型ID
	 * 
	 * @return 根流程定义模型ID
	 */
	public String getRootPDFId();

	/**
	 * 获取根流程定义模型
	 * 
	 * @return 根流程定义模型对象
	 * @throws Exception 
	 */
	public PDF getRoot() throws Exception;

	/**
	 * 判断是否为流程定义模型
	 * 
	 * @param modelId 模型ID
	 * @return 是否为流程定义模型
	 * @throws Exception 
	 */
	public boolean isPDF(String modelId) throws Exception;

	/**
	 * 查询模型的继承路径
	 * 
	 * @param formModelId 模型ID
	 * @return 模型的祖先模型ID列表，从根路径开始，当前模型ID为列表末尾值
	 * @throws Exception 
	 */
	public List<String> queryInheritPaths(String formModelId) throws Exception;
	/**
	 * 校验流程是否合法
	 * @param pdf
	 * @throws Exception
	 */
	public void verifyPDF(Progress prog,PDF pdf)throws Exception;

	/**
	 * 创建流程定义模型（PDF）
	 * 
	 * @param pdf 流程定义模型对象
	 * @return 创建后的流程定义模型对象
	 * @throws Exception 
	 */
	public PDF createPDF(PDF pdf) throws Exception;

	/**
	 * 更新流程定义模型（PDF）
	 * 
	 * @param prog 进度通知对象
	 * @param pdf  流程定义模型对象
	 * @return 更新后的流程定义模型对象
	 * @throws Exception 
	 */
	public PDF updatePDF(Progress prog, PDF pdf) throws Exception;

	/**
	 * 查询流程定义模型关联的表单索引
	 * 
	 * @param pdfUuid 流程定义模型UUID
	 * @return 表单索引列表
	 * @throws Exception 
	 */
	public List<TableIndex> queryFormModelIndex(String pdfUuid) throws Exception;

	/**
	 * 更新流程定义模型的表单索引
	 * 
	 * @param pdfUuid 流程定义模型UUID
	 * @param indexs  表单索引列表
	 * @throws Exception 
	 */
	public void updateFormModelIndex(String pdfUuid, List<TableIndex> indexs) throws Exception;

	/**
	 * 更新流程定义模型的模板数据
	 * 
	 * @param prog 进度通知对象
	 * @param dao  数据访问对象
	 * @param pdf  流程定义模型对象
	 * @throws Exception 
	 */
	public void updatePDFTemplateData(Progress prog, IDao dao, PDF pdf) throws Exception;

	/**
	 * 重命名流程定义模型
	 * 
	 * @param prog         进度通知对象
	 * @param renameModels key:模型ID, value:(新模型ID,模型中文名)
	 * @throws Exception 
	 */
	public void renamePDF(Progress prog, Map<String, Pair<String, String>> renameModels) throws Exception;

	/**
	 * 重命名流程定义模型UUID
	 * 
	 * @param prog         进度通知对象
	 * @param renameModels key:模型ID, value:新模型ID
	 * @throws Exception 
	 */
	public void renamePDFUuid(Progress prog, Map<String, String> renameModels) throws Exception;

	/**
	 * 查询流程定义模型
	 * 
	 * @param pdfUuid 流程定义模型UUID
	 * @return 流程定义模型对象
	 * @throws Exception 
	 */
	public PDF queryPDF(String pdfUuid) throws Exception;

	/**
	 * 根据名称查询流程定义模型
	 * 
	 * @param name 流程定义模型名称
	 * @return 流程定义模型对象
	 * @throws Exception 
	 */
	public PDF queryPDFByName(String name) throws Exception;

	/**
	 * 根据PDC实例查询其所属的流程定义模型
	 * 
	 * @param pdc PDC实例对象
	 * @return 流程定义模型对象
	 * @throws Exception 
	 */
	public PDF queryPDFByPDC(PDC pdc) throws Exception;

	/**
	 * 查询所有流程定义模型
	 * 
	 * @return 流程定义模型列表
	 * @throws Exception 
	 */
	public List<PDF> queryAllPDF() throws Exception;

	/**
	 * 按包路径查询流程定义模型
	 * 
	 * @param packagePath 包路径
	 * @return 流程定义模型列表
	 * @throws Exception 
	 */
	public List<PDF> queryPDFByPackagePath(String packagePath) throws Exception;

	/**
	 * 分页查询流程定义模型
	 * 
	 * @param parentIds   父模型ID列表
	 * @param packagePath 包路径
	 * @param keyword     搜索关键字
	 * @param pageNo      查询页码，从1开始
	 * @param pageSize    每页大小
	 * @return 流程定义模型分页结果集
	 * @throws Exception 
	 */
	public ResultSet<PDF> queryPDFPage(List<String> parentIds, String packagePath, String keyword, int pageNo,
			int pageSize) throws Exception;

	/**
	 * 删除流程定义模型
	 * 
	 * @param pdfId 流程定义模型ID
	 * @throws Exception 
	 */
	public void deletePDF(String pdfId) throws Exception;

	/**
	 * 查询流程定义模型关联的表单模型
	 * 
	 * @param pdfUuid 流程定义模型UUID
	 * @return 表单模型对象
	 * @throws Exception 
	 */
	public FormModel queryFormModelOfPDF(String pdfUuid) throws Exception;

	/**
	 * 查询流程定义模型关联的表单模型分页结果集
	 * 
	 * @param keyword  搜索关键字
	 * @param pageNo   查询页码，从1开始
	 * @param pageSize 每页大小
	 * @return 表单模型分页结果集
	 * @throws Exception 
	 */
	public ResultSet<FormModel> queryFormModelPageOfPDF(String keyword, int pageNo, int pageSize)
			throws Exception;

	/**
	 * 查询流程定义模型关联的历史操作记录模型
	 * 
	 * @param pdfUuid 流程定义模型UUID
	 * @return 历史操作记录模型对象
	 * @throws Exception 
	 */
	public FormModel queryOperateLogModelOfPDF(String pdfUuid) throws Exception;

	/**
	 * 查询所有历史操作记录模型分页结果集
	 * 
	 * @param keyword  搜索关键字
	 * @param pageNo   查询页码，从1开始
	 * @param pageSize 每页大小
	 * @return 历史操作记录模型分页结果集
	 * @throws Exception 
	 */
	public ResultSet<FormModel> queryOperateLogModelPageOfPDF(String keyword, int pageNo, int pageSize)
			throws Exception;

	/**
	 * 查询流程定义模型关联的当前操作记录模型
	 * 
	 * @param pdfUuid 流程定义模型UUID
	 * @return 当前操作记录模型对象
	 * @throws Exception 
	 */
	public FormModel queryCurrOpStatusLogModelOfPDF(String pdfUuid) throws Exception;

	/**
	 * 查询所有当前操作记录模型分页结果集
	 * 
	 * @param keyword  搜索关键字
	 * @param pageNo   查询页码，从1开始
	 * @param pageSize 每页大小
	 * @return 当前操作记录模型分页结果集
	 * @throws Exception 
	 */
	public ResultSet<FormModel> queryCurrOpStatusLogModelPageOfPDF(String keyword, int pageNo,
			int pageSize) throws Exception;

	/**
	 * 获取操作日志扩展字段
	 * 
	 * @return 操作日志扩展字段集合
	 * @throws Exception 
	 */
	public Set<String> getOperateLogExtFields() throws Exception;

	/**
	 * 导出流程定义模型（PDF）
	 * 
	 * @param prog       进度通知对象，用于跟踪导出进度
	 * @param expImpIntf 导出导入接口，定义了导出的具体实现方式
	 * @param pdfIds     需要导出的流程定义模型ID列表
	 * @return 包含导出文件名和字节流的Pair对象
	 * @throws Exception 
	 */
	public Pair<String, byte[]> exportPDF(Progress prog, ExportImportIntf expImpIntf, List<String> pdfIds)
			throws Exception;

	/**
	 * 导入流程定义模型（PDF）
	 * 
	 * @param prog       进度通知对象，用于跟踪导入进度
	 * @param expImpIntf 导出导入接口，定义了导入的具体实现方式
	 * @param zipFile    包含导入文件名和字节流的Pair对象
	 * @throws Exception 
	 */
	public void importPDF(Progress prog, ExportImportIntf expImpIntf, Pair<String, byte[]> zipFile) throws Exception;

	// ----------------------------------流程干预接口配置操作---------------------------------

	/**
	 * 查询操作日志状态钩子配置
	 * 
	 * @param hookUuid 操作日志状态钩子的UUID
	 * @return 操作日志状态钩子配置对象
	 * @throws Exception 
	 */
	public OperateLogStatusHookDto queryOperateLogStatusHook(String hookUuid) throws Exception;

	/**
	 * 创建操作日志状态钩子配置
	 * 
	 * @param pdfUuid 所属流程定义模型的UUID
	 * @param hook    操作日志状态钩子配置对象
	 * @return 创建后的操作日志状态钩子配置对象
	 * @throws Exception 
	 */
	public OperateLogStatusHookDto createOperateLogStatusHook(String pdfUuid, OperateLogStatusHookDto hook)
			throws Exception;

	/**
	 * 更新操作日志状态钩子配置
	 * 
	 * @param pdfUuid 所属流程定义模型的UUID
	 * @param hook    操作日志状态钩子配置对象
	 * @return 更新后的操作日志状态钩子配置对象
	 * @throws Exception 
	 */
	public OperateLogStatusHookDto updateOperateLogStatusHook(String pdfUuid, OperateLogStatusHookDto hook)
			throws Exception;

	/**
	 * 删除操作日志状态钩子配置
	 * 
	 * @param hookUuid 操作日志状态钩子的UUID
	 * @throws Exception 
	 */
	public void deleteOpeateLogStatusHook(String hookUuid) throws Exception;

	/**
	 * 分页查询操作日志状态钩子配置
	 * 
	 * @param pdfUuid  所属流程定义模型的UUID
	 * @param cnd      查询条件
	 * @param page     查询页码，从1开始
	 * @param pageSize 每页大小
	 * @return 操作日志状态钩子配置的分页结果集
	 * @throws Exception 
	 */
	public ResultSet<OperateLogStatusHookDto> queryOperateLogStatusHookPage(String pdfUuid, Cnd cnd, int page,
			int pageSize) throws Exception;

	/**
	 * 获取操作日志状态钩子配置列表
	 * 
	 * @param pdfUuid 所属流程定义模型的UUID
	 * @return 操作日志状态钩子配置列表
	 * @throws Exception 
	 */
	public List<OperateLogStatusHookDto> getOperateLogStatusHookList(String pdfUuid) throws Exception;
}
