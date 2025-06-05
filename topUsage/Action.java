package gpf.adur.action;

import com.kwaidoo.ms.tool.CmnUtil;
import com.leavay.common.util.javac.ClassFactory;

import cell.gpf.adur.action.IActionMgr;
import gpf.adur.data.Form;
import gpf.dc.intf.InheritableIntf;
import gpf.md.udf.Udf;
/**
 * 动作实例
 * @author chenxb
 *
 */
public class Action extends Form implements InheritableIntf{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6203357811616142809L;
	public final static String Code = "code";
	public static final String Label = "label";
	public static final String CreateTime = "createTime";
	public static final String UpdateTime = "updateTime";
	
	public Action() {
		super(IActionMgr.get().getRootActionModelId());
		setCode(getUuid());
	}
	
	public Action(String actionModelID) {
		super(actionModelID);
		setCode(getUuid());
	}
	
	public String getActionModelId() {
		return getFormModelId();
	}
	
	public Action setActionModelId(String actionModelId) {
		return (Action) setFormModelId(actionModelId);
	}
	
	public String getCode() {
		return (String) getAttrValueByCode(Code);
	}
	
	public void setCode(String code) {
		setAttrValueByCode(Code, code);
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
	public Action setInheritTmplt(String inheritTmplt) {
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
	public Action setUpdateTag(Long updateTag) {
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
	public Action setTmpltUpdateTag(Long tmpltUpdateTag) {
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
	public Action setOverride(boolean override) {
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
	public Action setOwner(String owner) {
		setAttrValueByCode(Owner, owner);
		return this;
	}
	
	public Class getJavaClass() throws Exception {
		String javaCode = getString(Udf.JavaCode);
		if(!CmnUtil.isStringEmpty(javaCode)) {
			try {
				Class clazz = ClassFactory.getValidClassLoader().loadClass(javaCode);
				return clazz;
			}catch (Exception e) {
			}
		}
		return null;
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
	public Action setLabel(String label) {
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
	public Action setCreateTime(Long createTime) {
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
	public Action setUpdateTime(Long updateTime) {
		setAttrValueByCode(UpdateTime, updateTime);
		return this;
	}


}
