package gpf.dc.concrete;

import java.io.Serializable;

import gpf.dc.intf.DCAttributeIntf;
import gpf.dc.intf.Inhertiable;

/**
 * 数据属性
 * @author chenxb
 *
 */
public class RefFormField extends Inhertiable implements DCAttributeIntf,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2977558917242458376L;
	
	public final static String DBClass = "gpf.md.slave.RefFormField";
	public final static String Code = "code";
	public final static String Name = "name";
	public final static String IsNotNull = "isNotNull";
	public final static String FormModelID = "formModelID";
	public final static String FormFieldCode = "formFieldCode";
	String uuid;
	String code;
	String name;
	boolean isNotNull = false;
	String formModelID;
	String formFieldCode;
	public RefFormField() {
	}
	public RefFormField(String code) {
		this.code = code;
	}
	public String getUuid() {
		return uuid;
	}
	public RefFormField setUuid(String uuid) {
		this.uuid = uuid;
		return this;
	}
	/**
	 * 属性编号
	 * @return
	 */
	public String getCode() {
		return code;
	}
//	public DCAttribute setCode(String code) {
//		this.code = code;
//		return this;
//	}
	/**
	 * 属性名
	 */
	public String getName() {
		return name;
	}
	public RefFormField setName(String name) {
		this.name = name;
		return this;
	}
	/**
	 * 是否可为空
	 * @return
	 */
	public boolean isNotNull() {
		return isNotNull;
	}
	public RefFormField setNotNull(boolean isNotNull) {
		this.isNotNull = isNotNull;
		return this;
	}
	/**
	 * 引用表单模型
	 * @return
	 */
	public String getFormModelID() {
		return formModelID;
	}
	public RefFormField setFormModelID(String formModelID) {
		this.formModelID = formModelID;
		return this;
	}
	/**
	 * 引用表单属性
	 * @return
	 */
	public String getFormFieldCode() {
		return formFieldCode;
	}
	public RefFormField setFormFieldCode(String formFieldCode) {
		this.formFieldCode = formFieldCode;
		return this;
	}
	
	@Override
	public String toString() {
		return name + "("+code+")";
	}
}
