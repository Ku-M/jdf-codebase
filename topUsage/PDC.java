package gpf.dc.config;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.kwaidoo.ms.tool.CmnUtil;
import com.leavay.common.util.javac.ClassFactory;

import cell.gpf.dc.concrete.ICDCMgr;
import gpf.adur.data.Form;
import gpf.dc.concrete.DCAction;
import gpf.dc.concrete.PrivilegeSetting;
import gpf.dc.concrete.RefFormField;
import gpf.dc.intf.InheritableIntf;
import gpf.dc.intf.node.DCNodeExtBehavior;

public class PDC extends Form implements InheritableIntf{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5886319441617690442L;
	
	public final static String Code = "code";
	public static final String Label = "label";
	public static final String CreateTime = "createTime";
	public static final String UpdateTime = "updateTime";
	public final static String RefFieldList = "refFieldList";
	public final static String ActionList = "actionList";
	public final static String PrivilegeSettings = "privilegeSettings";
	
	List<RefFormField> refFieldList = new ArrayList<>();
	
	List<DCAction> actionList = new ArrayList<>();
	
	List<PrivilegeSetting> privilegeSettings = new ArrayList<>();
	
	String extBehaviorClass;
	
	public PDC() {
		super(ICDCMgr.get().getRootCdcId());
		setCode(getUuid());
	}
	
	public PDC(String cdcId) {
		super(cdcId);
		setCode(getUuid());
	}
	
	public String getCdcId() {
		return getFormModelId();
	}
	
	public PDC setCdcId(String cdcId) {
		return (PDC) setFormModelId(cdcId);
	}
	
	public String getCode() {
		return (String) getAttrValueByCode(Code);
	}
	
	public void setCode(String code) {
		setAttrValueByCode(Code, code);
	}
	
	/**
	 * 数据属性
	 * @return
	 */
	public List<RefFormField> getRefFieldList() {
		return refFieldList;
	}

	public PDC setRefFieldList(List<RefFormField> refFieldList) {
		this.refFieldList = refFieldList;
		return this;
	}
	
	public Set<String> getRefFieldFormModelIds(){
		Set<String> formModelIds = new LinkedHashSet<>();
		for(RefFormField field : refFieldList) {
			formModelIds.add(field.getFormModelID());
		}
		return formModelIds;
	}
	/**
	 * 工位动作
	 * @return
	 */
	public List<DCAction> getActionList() {
		return actionList;
	}
	
	public PDC setActionList(List<DCAction> actionList) {
		this.actionList = actionList;
		return this;
	}
	/**
	 * 权限函数
	 * @return
	 */
	public List<PrivilegeSetting> getPrivilegeSettings() {
		return privilegeSettings;
	}

	public PDC setPrivilegeSettings(List<PrivilegeSetting> privilegeFunctions) {
		this.privilegeSettings = privilegeFunctions;
		return this;
	}
	
	public String getExtBehaviorClass() {
		return extBehaviorClass;
	}
	
	public PDC setExtBehaviorClass(String extBehaviorClass) {
		this.extBehaviorClass = extBehaviorClass;
		return this;
	}
	
	public Class<? extends DCNodeExtBehavior> getExtBehaviorClazz() {
		if(CmnUtil.isStringEmpty(extBehaviorClass))
			return null;
		try {
			return ClassFactory.loadClass(extBehaviorClass);
		}catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getInheritTmplt() {
		try {
			return getString(InheritTmplt);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public PDC setInheritTmplt(String inheritTmplt) {
		setAttrValueByCode(InheritTmplt, inheritTmplt);
		return this;
	}

	@Override
	public Long getUpdateTag() {
		try {
			return getLong(UpdateTag);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public PDC setUpdateTag(Long updateTag) {
		setAttrValueByCode(UpdateTag, updateTag);
		return this;
	}

	@Override
	public Long getTmpltUpdateTag() {
		try {
			return getLong(TmpltUpdateTag);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public PDC setTmpltUpdateTag(Long tmpltUpdateTag) {
		setAttrValueByCode(TmpltUpdateTag, tmpltUpdateTag);
		return this;
	}

	@Override
	public boolean isOverride(){
		Boolean override = null;
		try {
			override = getBoolean(Override);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return override != null && override;
	}

	@Override
	public PDC setOverride(boolean override) {
		setAttrValueByCode(Override, override);
		return this;
	}

//	@Override
	public String getOwner(){
		try {
			return getString(Owner);
		} catch (Exception e) {
			return null;
		}
	}

//	@Override
	public PDC setOwner(String owner) {
		setAttrValueByCode(Owner, owner);
		return this;
	}
	
	@Override
	public String getTmpltAllowModifyFields() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public InheritableIntf setTmpltAllowModifyFields(String tmpltAllowModifyFields) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 标签
	 * @return
	 */
	public String getLabel() {
		return (String) getAttrValueByCode(Label);
	}
	public PDC setLabel(String label) {
		setAttrValueByCode(Label, label);
		return this;
	}
	/**
	 * 创建时间
	 * @return
	 */
	public Long getCreateTime() {
		return (Long) getAttrValueByCode(CreateTime);
	}
	public PDC setCreateTime(Long createTime) {
		setAttrValueByCode(CreateTime, createTime);
		return this;
	}
	/**
	 * 更新时间
	 * @return
	 */
	public Long getUpdateTime() {
		return (Long) getAttrValueByCode(UpdateTime);
	}
	public PDC setUpdateTime(Long updateTime) {
		setAttrValueByCode(UpdateTime, updateTime);
		return this;
	}
	
	@Override
	public String toString() {
		return getFormModelId()+"("+getUuid()+")"+"{code = " + getCode()+"}";
	}
}
