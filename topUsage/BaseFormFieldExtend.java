package gpf.adur.data;

import java.io.Serializable;

import com.kwaidoo.ms.tool.CmnUtil;
import com.leavay.common.util.javac.ClassFactory;

import cmn.dto.model.extend.intf.ModelFieldExtendIntf;
/**
 * 表单属性扩展信息接口
 * @author chenxb
 *
 */
public class BaseFormFieldExtend implements ModelFieldExtendIntf,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5164415963818783802L;
	String dataTransClass;
	/**
	 * 数据格式转换类
	 * @return
	 */
	public String getDataTransClass() {
		return dataTransClass;
	}
	
	public Class<? extends FormFieldDataTransIntf> getDataTransClazz() {
		if(CmnUtil.isStringEmpty(dataTransClass))
			return null;
		try {
			return (Class<? extends FormFieldDataTransIntf>) ClassFactory.getValidClassLoader().loadClass(dataTransClass);
		}catch (Exception e) {
			return null;
		}
	}
	public void setDataTransClass(Class<? extends FormFieldDataTransIntf> dataTransClass) {
		this.dataTransClass = dataTransClass.getName();
	}

	
}
