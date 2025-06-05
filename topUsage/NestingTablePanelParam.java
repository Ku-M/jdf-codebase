package gpf.dc.fe.component.param;

import fe.util.component.param.EmbedTableParam;
import gpf.adur.data.Form;
import gpf.dc.basic.param.view.dto.FormViewSetting;

public class NestingTablePanelParam extends EmbedTableParam<Form>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6321887912267636281L;
	
	String tableFormModelID;
	
	String masterClass;
	
	String masterField;
	
	String masterKey;
	
	
	public String getTableFormModelID() {
		return tableFormModelID;
	}
	public NestingTablePanelParam setTableFormModelID(String tableFormModelID) {
		this.tableFormModelID = tableFormModelID;
		return this;
	}
	public String getMasterClass() {
		return masterClass;
	}
	public NestingTablePanelParam setMasterClass(String masterClass) {
		this.masterClass = masterClass;
		return this;
	}
	public String getMasterField() {
		return masterField;
	}
	public NestingTablePanelParam setMasterField(String masterField) {
		this.masterField = masterField;
		return this;
	}
	public String getMasterKey() {
		return masterKey;
	}
	public NestingTablePanelParam setMasterKey(String masterKey) {
		this.masterKey = masterKey;
		return this;
	}
	
	/**
	 * 查看详情嵌套数据是否在表单加载后再查询
	 */
	boolean isLazyQueryCompoundField = false;

	public boolean isLazyQueryCompoundField() {
		return isLazyQueryCompoundField;
	}
	public NestingTablePanelParam setLazyQueryCompoundField(boolean isLazyQueryCompoundField) {
		this.isLazyQueryCompoundField = isLazyQueryCompoundField;
		return this;
	}

	
}
