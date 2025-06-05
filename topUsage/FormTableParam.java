package gpf.dc.fe.component.param;

import java.util.Map;

import org.nutz.dao.util.cri.SqlExpressionGroup;

import fe.util.component.param.TableParam;

public class FormTableParam extends TableParam{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3901980496062243097L;
	public FormTableParam() {
		setStartPos(0);
		setPageSize(20);
		setQueryCount(true);
		setPageSizes(defaultPageSizes);
	}
	String formModelID;
	String orderColumn;//排序列
	String orderMode = "asc";//排序模式，升级，降序
	SqlExpressionGroup advFilter;
	//通过SQL查询的相关参数
	Map<String,String> withSqls;
	String queryAlias;
	
	public String getFormModelID() {
		return formModelID;
	}
	public FormTableParam setFormModelID(String formModelID) {
		this.formModelID = formModelID;
		return this;
	}
	
	public SqlExpressionGroup getAdvFilter() {
		return advFilter;
	}
	public FormTableParam setAdvFilter(SqlExpressionGroup advFilter) {
		this.advFilter = advFilter;
		return this;
	}
	
	public String getOrderColumn() {
		return orderColumn;
	}
	public FormTableParam setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
		return this;
	}
	public String getOrderMode() {
		return orderMode;
	}
	public FormTableParam setOrderMode(String orderMode) {
		this.orderMode = orderMode;
		return this;
	}
	
	public Map<String, String> getWithSqls() {
		return withSqls;
	}
	public FormTableParam setWithSqls(Map<String, String> withSqls) {
		this.withSqls = withSqls;
		return this;
	}
	public String getQueryAlias() {
		return queryAlias;
	}
	public FormTableParam setQueryAlias(String queryAlias) {
		this.queryAlias = queryAlias;
		return this;
	}
	
	/**
	 * 查看详情嵌套数据是否在表单加载后再查询
	 */
	boolean isLazyQueryCompoundField = false;

	public boolean isLazyQueryCompoundField() {
		return isLazyQueryCompoundField;
	}
	public FormTableParam setLazyQueryCompoundField(boolean isLazyQueryCompoundField) {
		this.isLazyQueryCompoundField = isLazyQueryCompoundField;
		return this;
	}
	
}
