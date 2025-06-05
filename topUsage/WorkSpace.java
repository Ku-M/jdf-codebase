package jit.dto;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cmn.util.NullUtil;
import gpf.dc.anotation.dto.FieldInfo;
import gpf.dc.dto.BusinessModelDto;
import pcr.dto.WorkSpaceVariable;
/**
 * 工作空间
 * @author chenxb
 *
 */
public class WorkSpace extends BusinessModelDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 273614324262003868L;
	
	public final static String CommonWorkspace = "common";
	
	public final static String FormModelId = "gpf.md.workSpaceConfig";
	public final static String Code = "编号";
	public final static String Label = "标签";
	public final static String OrgModel = "组织模型";
	public final static String UserModel = "用户模型";
	public final static String FlowPackagePath = "流程包路径";
	public final static String DefaultNodeModel = "默认节点模型";
	public final static String GlobalVariableTable = "变量表";

//	String uuid;
//	/**
//	 * 编号
//	 */
//	String code;
	/**
	 * 标签
	 */
	@FieldInfo(label = Label)
	String label;
	/**
	 * 组织模型
	 */
	@FieldInfo(label = OrgModel)
	String orgModel;
	/**
	 * 用户模型
	 */
	@FieldInfo(label = UserModel)
	String userModel;
	/**
	 * 流程包路径
	 */
	@FieldInfo(label = FlowPackagePath)
	String flowPackagePath;
	/**
	 * 默认节点模型
	 */
	@FieldInfo(label = DefaultNodeModel)
	String defaultNodeModel;
	/**
	 * 工作空间全局变量表
	 */
	List<WorkSpaceVariable> variables;
//	public String getUuid() {
//		return uuid;
//	}
//	public WorkSpace setUuid(String uuid) {
//		this.uuid = uuid;
//		return this;
//	}
//	public String getCode() {
//		return code;
//	}
//	public WorkSpace setCode(String code) {
//		this.code = code;
//		return this;
//	}
	public String getLabel() {
		return label;
	}
	public WorkSpace setLabel(String label) {
		this.label = label;
		return this;
	}
	public String getOrgModel() {
		return orgModel;
	}
	public WorkSpace setOrgModel(String orgModel) {
		this.orgModel = orgModel;
		return this;
	}
	public String getUserModel() {
		return userModel;
	}
	public WorkSpace setUserModel(String userModel) {
		this.userModel = userModel;
		return this;
	}
	public String getFlowPackagePath() {
		return flowPackagePath;
	}
	public WorkSpace setFlowPackagePath(String flowPackagePath) {
		this.flowPackagePath = flowPackagePath;
		return this;
	}
	
	public String getDefaultNodeModel() {
		return defaultNodeModel;
	}
	public WorkSpace setDefaultNodeModel(String defaultNodeModel) {
		this.defaultNodeModel = defaultNodeModel;
		return this;
	}
	
	public List<WorkSpaceVariable> getVariables() {
		return variables;
	}
	public WorkSpace setVariables(List<WorkSpaceVariable> variables) {
		this.variables = variables;
		return this;
	}
	
	public Map<String,String> getWorkSpaceVariableValueMap(){
		Map<String,String> map = new LinkedHashMap<>();
		for(WorkSpaceVariable var : NullUtil.get(variables)) {
			map.put(var.getName(), var.getValue());
		}
		return map;
	}
	
	
	
	
}
