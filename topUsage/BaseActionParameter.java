package gpf.dc.action.param;

import java.util.ArrayList;
import java.util.List;

import cell.gpf.dc.runtime.IDCRuntimeContext;
import cmn.anotation.ClassDeclare;
import cmn.anotation.MethodDeclare;
import gpf.action.parameter.ParameterIntf;
import gpf.action.parameter.SystemVaribleInfo;
@ClassDeclare(label = "动作模型参数基类"
,what = "动作模型参数基类，所有自定义的动作模型参数都需要继承此类，使用动作模型参数时需在动作模型代码中的getInputParamClass()中声明"
, why = ""
,how = "以动作模型代码使用为例：\r\n"
		+ "//动作模型代码需要继承CellIntf和BaseActionIntf接口或继承BaseActionIntf的子接口，并在接口上声明泛型T继承的动作模型参数类型\r\n" + 
		"public interface IStudyBaseActionDefine <T extends BaseActionParameter> extends CellIntf, BaseActionIntf<T>{\r\n" + 
		"	@Override\r\n" + 
		"	default Object execute(T input) throws Exception {\r\n" + 
		"		//这里编写动作模型代码\r\n" + 
		"		return null;\r\n" + 
		"	}\r\n" + 
		"	@Override\r\n" + 
		"	default Class<? extends T> getInputParamClass() {\r\n" + 
		"		//填写类上泛型T声明的动作模型参数类\r\n" + 
		"		return (Class<? extends T>) BaseActionParameter.class;\r\n" + 
		"	}\r\n" + 
		"}"
, developer = "陈晓斌"
, createTime = "2025-03-14"
,updateTime = "2025-03-14"
, version = "1.0" )
public class BaseActionParameter implements ParameterIntf<IDCRuntimeContext>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6918884582121731250L;
	IDCRuntimeContext rtx;
	
	@MethodDeclare(label = "获取动作运行上下文",
	        what = "所有动作运行都会携带运行上下文，可以通过上下文拿到事务对象、当前操作人、当前表单等数据",
	        why = "",
	        how = "",
	        inputs = {},
	        developer = "",
	        createTime = "",
	        updateTime = "",
	        version = "")
	public IDCRuntimeContext getRtx() {
		return rtx;
	}
	public void setRtx(IDCRuntimeContext rtx) {
		this.rtx = rtx;
	}
	
	public Object getPdcFormAttrValue(String attrName) throws Exception {
		return rtx.getPdcForm().getAttrValue(attrName);
	}
	
	public void setPdcFormAttrValue(String attrName,Object value) throws Exception {
		rtx.getPdcForm().setAttrValue(attrName, value);
	}
	
	public Object getPdcFormAttrValueByCode(String attrCode) throws Exception {
		return rtx.getPdcForm().getAttrValueByCode(attrCode);
	}
	
	public void setPdcFormAttrValueByCode(String attrCode,Object value) throws Exception {
		rtx.getPdcForm().setAttrValueByCode(attrCode,value);
	}
	
	@Override
	public List<SystemVaribleInfo> getSystemVariableInfos() {
		return new ArrayList<>();
	}
}
