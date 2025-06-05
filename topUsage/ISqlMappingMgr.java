package cell.gpf.dc.runtime;

import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;

import com.cdao.dto.DataRow;

import bap.cells.Cells;
import cell.ServiceCellIntf;
import cell.cdao.IDao;
import gdk.dto.GdkDataModelDto;
import gdk.dto.flow.GdkDataModelFlowDto;
import gpf.adur.data.Form;
import gpf.adur.data.FormField;
import gpf.adur.data.FormModel;
import gpf.adur.data.ResultSet;
import gpf.adur.data.TableData;
import gpf.dc.config.PDC;
import gpf.dc.config.PDF;
import gpf.dc.config.sqlmapping.SqlMappingFlow;
import gpf.dc.config.sqlmapping.ViewSqlDto;
import gpf.dc.intf.ExternalQueryIntf;
import gpf.dc.runtime.PDCForm;
import gpf.dc.runtime.PDFForm;

public interface ISqlMappingMgr extends ServiceCellIntf{

	public static ISqlMappingMgr get() {
		return Cells.get(ISqlMappingMgr.class);
	}
	
	/**
	 * 将查询条件对象转换成SQL查询条件
	 * @param cnd
	 * @param keepWhereWord	保留WHERE关键字
	 * @param keepOrderBy	保留排序
	 * @return
	 */
	public String cndToSql(Cnd cnd,boolean keepWhereWord,boolean keepOrderBy);
	/**
	 * 将查询视图map转换成with查询语句
	 * @param viewSqls	key:视图别名 value: 视图SQL
	 * @return
	 */
	public String viewSqlsToWithSql(Map<String,String> viewSqls)throws Exception;
	/**
	 * 根据useFieldName给属性名加上quote
	 * @param code
	 * @param name
	 * @param useFieldName
	 * @return
	 */
	public String quoteColumn(String code,String name,boolean useFieldName);
	/**
	 * 将数据映射转换成嵌套表数据
	 * @param flow
	 * @return
	 * @throws Exception
	 */
	public TableData convertSqlMappingFlow2TableData(List<SqlMappingFlow> flow)throws Exception;
	/**
	 * 将嵌套表数据转成为数据映射
	 * @param tableData
	 * @return
	 * @throws Exception
	 */
	public List<SqlMappingFlow> convertTableData2SqlMappingFlow(TableData tableData)throws Exception;
	
	public Form convertSqlMappingFlow2Form(SqlMappingFlow flow)throws Exception;
	
	public SqlMappingFlow convertForm2SqlMappingFlow(Form form)throws Exception;
	/**
	 * 从表单中获取数据映射流图定义
	 * @param form
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public Map<String,SqlMappingFlow> getSqlMapppingFlows(Form form,String fieldName) throws Exception;
	/**
	 * 将GDK的数据流转成数据映射
	 * @param flow
	 * @return
	 * @throws Exception
	 */
	public SqlMappingFlow convertGdkDataModelFlow2SqlMappingFlow(GdkDataModelFlowDto flow) throws Exception;
	/**
	 * 将数据映射转成GDK的数据流
	 * @param flow
	 * @return
	 * @throws Exception
	 */
	public GdkDataModelFlowDto convertSqlMappingFlow2GdkDataModelFlow(SqlMappingFlow flow) throws Exception;
	/**
	 * 查询或创建模型对应的GDK数据模型
	 * @param formModel
	 * @param useFieldName
	 * @return
	 * @throws Exception
	 */
	public GdkDataModelDto queryOrCreateGdkDataModelByFormModel(FormModel formModel,boolean useFieldName)throws Exception;
	/**
	 * 查询或创建流程对应的GDK数据模型
	 * @param pdf
	 * @param useFieldName
	 * @return
	 * @throws Exception
	 */
	public GdkDataModelDto queryOrCreateGdkDataModelByPDF(PDF pdf,boolean useFieldName)throws Exception;
	
	public GdkDataModelDto queryOrCreateGdkDataModelOfOrgAndRole(String orgModelId,boolean useFieldName)throws Exception;
	
	public GdkDataModelDto queryOrCreateGdkDataModelOfRoleAndUser(String userModelId,boolean useFieldName)throws Exception;
	
	public GdkDataModelDto queryOrCreateGdkDataModelOfOrgAndUser(String orgModelId,String userModelId,boolean useFieldName)throws Exception;
	
	/**
	 * 查询或创建附件对应的GDK数据模型
	 * @param useFieldName
	 * @return
	 * @throws Exception
	 */
	public GdkDataModelDto queryOrCreateGdkDataModelOfAttach(boolean useFieldName)throws Exception;
	/**
	 * 查询或创建上下文变量对应的GDK数据模型
	 * @param useFieldName
	 * @return
	 * @throws Exception
	 */
	public GdkDataModelDto queryOrCreateGdkDataModelOfContextVariable(boolean useFieldName)throws Exception;

	/**
	 * 查询或创建内存表单对应的GDK数据模型
	 * @param formTag
	 * @param alias
	 * @param aliasLabel
	 * @param formFields
	 * @param useFieldName
	 * @return
	 * @throws Exception
	 */
	public List<GdkDataModelDto> queryOrCreateGdkDataModelOfMemoryForm(String formTag,String alias,String aliasLabel,List<FormField> formFields,boolean useFieldName) throws Exception;
	
	public String convertInternalView2Sql(GdkDataModelFlowDto flow, String viewName, boolean convertParam,boolean isPreview,
			boolean isFormat, long opTime) throws Exception;
	
	public String applyParam2Sql(String sql,Map<String,Object> sqlParams) throws Exception ;
	
	/**
	 * 对SQL中出现的@字符进行转义
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public String escapeAtSymbol(String sql)throws Exception;
	/**
	 * 构建数据映射中指定视图名的SQL
	 * @param flow
	 * @param viewName
	 * @param preview
	 * @param useFieldName
	 * @param opTime
	 * @return
	 * @throws Exception
	 */
	public String buildWithCteSqlOfSqlMappingFlow(SqlMappingFlow flow, String formTag, Form form,
			List<FormField> formFields,String viewName, boolean preview, boolean useFieldName, long opTime,Map<String,Object> sqlParams,boolean convertParam) throws Exception;
	
	public Map<String, String> buildCteSqlOfForm(List<FormField> fields,Form form,String alias,String aliasLabel,boolean useFieldName) throws Exception; 
	
	public Map<String,String> buildCteSqlOfVariable(Map<String,Object> sqlParams,boolean useFieldName) throws Exception;
	
	public String buildSqlOfFormModel(FormModel model, boolean useFieldName) throws Exception;
	
	public String buildSqlOfPDF(String pdfUuid, boolean useFieldName) throws Exception ;
	
	public String buildSqlOfOrgAndRole(String orgModelId)throws Exception;
	
	public String buildSqlOfRoleAndUser(String userModelId)throws Exception;
	
	public String buildSqlOfOrgAndUser(String orgModelId,String userModelId)throws Exception;
	
	public String buildUnionSql(List<String> unionSqls);
	/**
	 * 根据表单数据、数据映射查询或创建GDK的数据流
	 * @param sqlFlow
	 * @param formTag
	 * @param form
	 * @param formFields
	 * @param useFieldName
	 * @return
	 * @throws Exception
	 */
	public GdkDataModelFlowDto queryOrCreateGdkDataModelFlow(SqlMappingFlow sqlFlow, String formTag, Form form,
			List<FormField> formFields, Map<String,Object> sqlParams,boolean useFieldName) throws Exception ;
	/**
	 * 校验数据流中来源表单视图引用是否合法
	 * @param gdkFlow
	 * @throws Exception
	 */
	public String verifyExternalViewOfMemeory(GdkDataModelFlowDto gdkFlow)throws Exception;
	/**
	 * 修正数据流中来源表单视图的引用
	 * @param gdkFlow
	 * @throws Exception
	 */
	public GdkDataModelFlowDto fixExternalViewOfMemeory(GdkDataModelFlowDto gdkFlow) throws Exception;
	
	/**
	 * 自定义查询语句，返回原始的查询结果，不做任何转换
	 * 返回结果中的DataRow为字段名（或Alias）对应库表记录值Object
	* 如果需要总行数，可再调用queryLong另行查询
	*  @param dao	dao数据操作句柄
	 * @param sqlText	自定义的查询SQL
	 * @param pageNo	分页号
	 * @param pageSize	分页大小
	 *  样例：
            String sql = "select a.*, b.name as projectName from cjava_code a, $TABLE_PROJECT b where a.masterKey=b.uuid and b.name<>@paramProject";
            ResultSet<DataRow> rs2 = queryDataRowPage(dao,sql, vars, params, 1, 20);
	 * @return 原始查询数据分页
	 * @throws Exception
	 */
	public ResultSet<DataRow> queryDataRowPage(IDao dao, ExternalQueryIntf queryIntf,String sqlText, int pageNo, int pageSize)throws Exception;
	
//	public List<NestingFormDataRow> queryNestingDataRows(IDao dao,String withSql, String nestingAlias,String pAlias,String pColumn, String pFormUuid,Map<String, Object> varMap,boolean useFieldName)throws Exception;
//	
//	/**
//	 * 查询附件视图数据
//	 * @param dao
//	 * @param cteSql
//	 * @param attachAlias
//	 * @param pAlias
//	 * @param pColumn
//	 * @param varMap
//	 * @return
//	 * @throws Exception
//	 */
//	public List<AttachDataRow> queryAttachDataRows(IDao dao, String withSql, String attachAlias,
//			String pAlias, String pColumn, Map<String, Object> varMap,boolean useFieldName) throws Exception;
//	
//	/**
//	 * 数据映射SQL构建规则：
//			当前表单的数据会转换为查询SQL，可与其他模型一起关联映射转换
//			属性数据类型对应的值查询逻辑如下： 
//				1.密码 ：查询结果为加密文本
//				2.关联 ：查询结果为关联数据的编号，多选时为[-]编号1[-]编号2
//				3.嵌套模型, 查询结果为查询SQL中的其他视图名称，可通过视图名称获取对应视图的查询结果对嵌套模型进行数据增量更新活或校验，嵌套模型视图的必要字段包括：（pAlias(父视图名称)、pColumn（父视图嵌套模型属性名称）、pFormUuid（父视图表单的Uuid)
//				4.附件 ,查询结果为查询SQL中的其他视图名称，可通过视图名称获取对应视图的查询结果，构建的附件列表视图的必要字段包括：pAlias(父视图名称) 、pColumn(父视图附件属性名称) 、pFormUuid(父视图表单的Uuid)、fileName(文件名)、uploadPath（文件上传路径）  
//					uploadPath提供两种附件获取方式：
//					4.1.来自其他表单的附件数据，使用 form:// 作为前缀路径前缀，示例 ： form://$pFormUuid$/$pColumn$/$fileName$，
//					4.2.来自操作系统的文件路径，
//				5.时间Date,查询结果为Long或者TimeStamp类型，最终会都转成Long数值
//		系统参数说明：
//		$operator$：当前操作人编号
//		$action$：当前执行动作
//		$nodeName$：当前节点
//		$currentTime$:当前时间毫秒：
//	 * 
//	 * @param dao
//	 * @param dataRow  结果数据
//	 * @param fieldMap 表单属性集合，根据useQuote决定是属性Code的集合还是属性名称的集合
//	 * @param form     原表单属性
//	 * @param withSqls
//	 * @param useFieldName
//	 * @throws Exception
//	 */
//	public <T extends Form> T mappingValue(IDao dao, DataRow dataRow, Map<String, FormField> fieldMap, T form,
//			String pAliasValue, String withSql, Map<String, Object> varMap, boolean useFieldName) throws Exception; 
	
	
	public void verifyMappingValueIsLegal(IDao dao, DataRow dataRow, List<FormField> fields, PDC pdc,boolean useFieldName) throws Exception;
	
	public <T extends Form> T mappingDataRowToForm(IDao dao, ExternalQueryIntf queryIntf,DataRow dataRow, List<FormField> fields, T form,
			Map<String,ViewSqlDto> viewSqls, Map<String,Object> params,boolean useFieldName) throws Exception;
	/**
	 * 批处理映射生成表单值
	 * @param dao
	 * @param queryIntf
	 * @param dataRow
	 * @param fields
	 * @param viewSqls
	 * @param params
	 * @param useFieldName
	 * @return
	 * @throws Exception
	 */
	public List<PDCForm> batchMappingDataRowsToPDCForms(IDao dao, ExternalQueryIntf queryIntf, List<DataRow> dataRows,
			String pdfUuid,List<FormField> fields, Map<String, ViewSqlDto> viewSqls, Map<String,Object> params,boolean useFieldName) throws Exception;
	
	public List<PDFForm> batchMappingDataRowsToPDFForms(IDao dao, ExternalQueryIntf queryIntf, List<DataRow> dataRows,
			String pdfUuid,List<FormField> fields, Map<String, ViewSqlDto> viewSqls, Map<String,Object> params,boolean useFieldName) throws Exception;
	
	public List<Form> batchMappingDataRowsToForms(IDao dao, ExternalQueryIntf queryIntf, List<DataRow> dataRows,
			String formModelId,List<FormField> fields, Map<String, ViewSqlDto> viewSqls, Map<String, Object> params, boolean useFieldName) throws Exception;
}
