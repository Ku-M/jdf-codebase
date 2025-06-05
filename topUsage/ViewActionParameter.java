package jit.param.view.action;

import java.util.List;
import java.util.Map;

import org.nutz.dao.entity.annotation.Comment;

import gpf.adur.data.TableData;
import gpf.dc.basic.param.view.BaseFeActionParameter;
import jit.param.ActionHandlerParameterIntf;

public class ViewActionParameter extends BaseFeActionParameter implements ActionHandlerParameterIntf{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1945346004781986427L;
	@Comment("规则列表")
	TableData ruleTable;
	@Comment("动作接口")
	String actionIntfClass;
	@Comment("方法")
	String method;
	@Comment("参数映射")
	List<Map<String,String>> paramMapping;
	
	public TableData getRuleTable() {
		return ruleTable;
	}
	public ViewActionParameter setRuleTable(TableData ruleTable) {
		this.ruleTable = ruleTable;
		return this;
	}
	public String getActionIntfClass() {
		return actionIntfClass;
	}
	public ViewActionParameter setActionIntfClass(String actionIntfClass) {
		this.actionIntfClass = actionIntfClass;
		return this;
	}
	public String getMethod() {
		return method;
	}
	public ViewActionParameter setMethod(String method) {
		this.method = method;
		return this;
	}
	public List<Map<String, String>> getParamMapping() {
		return paramMapping;
	}
	public ViewActionParameter setParamMapping(List<Map<String, String>> paramMapping) {
		this.paramMapping = paramMapping;
		return this;
	}
	
}
