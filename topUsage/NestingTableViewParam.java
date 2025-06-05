package gpf.dc.basic.fe.component.param;

import java.util.List;

import gpf.adur.data.Form;

public class NestingTableViewParam extends BaseTableViewParam{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3703990713831721488L;
	
	String masterClass;
	String masterKey;
	String masterField;
	
	public NestingTableViewParam() {
		getSetting().setIsOpShowPopToast(false);
	}
	List<Form> rows;
	
	public List<Form> getRows() {
		return rows;
	}

	public NestingTableViewParam setRows(List<Form> rows) {
		this.rows = rows;
		return this;
	}

	public String getMasterClass() {
		return masterClass;
	}

	public NestingTableViewParam setMasterClass(String masterClass) {
		this.masterClass = masterClass;
		return this;
	}

	public String getMasterKey() {
		return masterKey;
	}

	public NestingTableViewParam setMasterKey(String masterKey) {
		this.masterKey = masterKey;
		return this;
	}
	
	public String getMasterField() {
		return masterField;
	}
	public NestingTableViewParam setMasterField(String masterField) {
		this.masterField = masterField;
		return this;
	}
	
}
