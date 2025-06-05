package gpf.adur.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import gpf.adur.data.FormModel;
import gpf.dto.model.TmpltAllowModifyFieldSetting;

/**
 * 动作模型
 * @author chenxb
 *
 */
public class ActionModel extends FormModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7531160769953811879L;
	
	public final static String JavaClassPath = "javaClassPath";
	public final static String ParamMappings = "paramMappings";
	public final static String TmpltAllowModifyFields = "tmpltAllowModifyFields";
	/**
	 * Java代码/类路径
	 */
	String javaClassPath;
	/**
	 * 参数映射
	 */
	List<ParamMapping> paramMappings = new ArrayList<>();
	/**
	 * 实例继承配置
	 */
	List<TmpltAllowModifyFieldSetting> tmpltAllowModifyFields = new ArrayList<>();
	public ActionModel() {
	}
	
	public ActionModel(String packagePath,String name) {
		setName(name);
		setPackagePath(packagePath);
	}
	/**
	 * Java代码/类路径
	 */
	public String getJavaClassPath() {
		return javaClassPath;
	}

	public void setJavaClassPath(String javaClassPath) {
		this.javaClassPath = javaClassPath;
	}
	/**
	 * 参数映射
	 */
	public List<ParamMapping> getParamMappings() {
		return paramMappings;
	}

	public void setParamMappings(List<ParamMapping> paramMappings) {
		this.paramMappings = paramMappings;
	}
	/**
	 * 实例继承配置
	 */
	public List<TmpltAllowModifyFieldSetting> getTmpltAllowModifyFields() {
		return tmpltAllowModifyFields;
	}
	public void setTmpltAllowModifyFields(List<TmpltAllowModifyFieldSetting> tmpltAllowModifyFields) {
		this.tmpltAllowModifyFields = tmpltAllowModifyFields;
	}
	/**
	 * Java入参
	 *	Map<参数名,参数输入值>
	 * @return
	 */
	public LinkedHashMap<String,String> getJavaArguments(){
		LinkedHashMap<String,String> map = new LinkedHashMap<>();
		for(ParamMapping mapping : paramMappings) {
			map.put(mapping.getJavaArgumentName(), mapping.getInputValue());
		}
		return map;
	}
	
	
}
