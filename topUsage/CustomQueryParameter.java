package gpf.dc.basic.param.view;

import org.nutz.dao.Cnd;
import org.nutz.dao.util.cri.SqlExpressionGroup;

import cell.gpf.dc.runtime.IDCRuntimeContext;
import gpf.dc.basic.dto.privilege.ResultSetQueryParam;
import gpf.dc.basic.param.view.dto.FilterDto;

/**
 * 自定义查询界面基类参数
 * @author chenxb
 *
 */
public class CustomQueryParameter extends BaseFeActionParameter{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5719674667346018526L;

	public final static String FeActionParameter_Filter = "$sysvar_filter$";
	public final static String FeActionParameter_Cnd = "$sysvar_cnd$";
	public final static String FeActionParameter_PageNo = "$sysvar_pageNo$";
	public final static String FeActionParameter_PageSize = "$sysvar_pageSize$";
	public final static String FeActionParameter_QuerySql = "$sysvar_querySql$";
	public final static String FeActionParameter_PrivilegeParam = "$sysvar_privilegeParam$";
	public final static String FeActionParameter_DefaultExpr = "$sysvar_defaultExpr$";

	public static void prepareCustomQueryParameter(IDCRuntimeContext rtx,Cnd cnd,int pageNo,int pageSize) throws Exception {
		rtx.setParam(FeActionParameter_Cnd, cnd);
		rtx.setParam(FeActionParameter_PageNo, pageNo);
		rtx.setParam(FeActionParameter_PageSize, pageSize);
	}
	public static void prepareCustomQueryParameter(IDCRuntimeContext rtx, String querySql, ResultSetQueryParam privilegeParam , SqlExpressionGroup defaultExpr,Cnd cnd, int pageNo, int pageSize,FilterDto filter) throws Exception {
		rtx.setParam(FeActionParameter_Cnd, cnd);
		rtx.setParam(FeActionParameter_PageNo, pageNo);
		rtx.setParam(FeActionParameter_PageSize, pageSize);
		rtx.setParam(FeActionParameter_QuerySql,querySql);
		rtx.setParam(FeActionParameter_PrivilegeParam,privilegeParam);
		rtx.setParam(FeActionParameter_DefaultExpr,defaultExpr);
		rtx.setParam(FeActionParameter_Filter, filter);
	}
	/**
	 * 组装好的查询条件
	 * @return
	 * @throws Exception
	 */
	public Cnd getCnd() throws Exception {
		return (Cnd) getRtx().getParam(FeActionParameter_Cnd);
	}
	/**
	 * 查询页面
	 * @return
	 * @throws Exception
	 */
	public Integer getPageNo() throws Exception  {
		return (Integer) getRtx().getParam(FeActionParameter_PageNo);
	}
	/**
	 * 分页大小
	 * @return
	 * @throws Exception
	 */
	public Integer getPageSize() throws Exception  {
		return (Integer) getRtx().getParam(FeActionParameter_PageSize);
	}
	/**
	 * 组装好的查询SQL
	 * @return
	 * @throws Exception
	 */
	public String getQuerySql() throws Exception  {
		return (String) getRtx().getParam(FeActionParameter_QuerySql);
	}
	/**
	 * 结果集查询的权限参数
	 * @return
	 * @throws Exception
	 */
	public ResultSetQueryParam getPrivilegeParam() throws Exception  {
		return (ResultSetQueryParam) getRtx().getParam(FeActionParameter_PrivilegeParam);
	}
	/**
	 * 默认的过滤条件表达式
	 * @return
	 * @throws Exception
	 */
	public SqlExpressionGroup getDefaultExpr() throws Exception  {
		return (SqlExpressionGroup) getRtx().getParam(FeActionParameter_DefaultExpr);
	}
	/**
	 * 界面上的过滤参数
	 * @return
	 * @throws Exception
	 */
	public FilterDto getFilterDto() throws Exception {
		return (FilterDto) getRtx().getParam(FeActionParameter_Filter);
	}

}
