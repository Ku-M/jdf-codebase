package gpf.dc.concrete;

import java.io.Serializable;

import com.kwaidoo.ms.tool.CmnUtil;
import com.kwaidoo.ms.tool.ToolUtilities;

import bap.cells.Cells;
import cell.cdao.IDao;
import cell.cdao.IDaoService;
import cell.gpf.adur.action.IActionMgr;
import gpf.adur.action.Action;
import gpf.adur.action.ActionModel;
import gpf.dc.intf.Inhertiable;
/**
 * 引用动作配置
 * @author chenxb
 *
 */
public class RefActionConfig extends Inhertiable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8637277098097642192L;
	
	public final static String FormModelId = "gpf.md.slave.RefAction";
	
	public final static String Name = "name";
	public final static String Description = "description";
	public final static String ActionModelID = "actionModelID";
	public final static String Action = "action";
	
	String uuid = ToolUtilities.allockUUIDWithUnderline();
	String name;
	String description;
	String actionModelID;
	ActionModel actionModel;
	String actionUuid;
	Action action;
	boolean isRefInstModified = false;
	public String getUuid() {
		return uuid;
	}
	public RefActionConfig setUuid(String uuid) {
		this.uuid = uuid;
		return this;
	}
	public String getName() {
		return name;
	}
	public RefActionConfig setName(String name) {
		this.name = name;
		return this;
	}
	public String getDescription() {
		return description;
	}
	public RefActionConfig setDescription(String description) {
		this.description = description;
		return this;
	}
	public String getActionModelID() {
		return actionModelID;
	}
	public RefActionConfig setActionModelID(String actionModelID) {
		this.actionModelID = actionModelID;
		return this;
	}
	public ActionModel getActionModel() throws Exception {
		if(actionModel == null) {
			if(!CmnUtil.isStringEmpty(actionModelID)) {
				actionModel = IActionMgr.get().queryActionModel(actionModelID);
			}
		}
		return actionModel;
	}
	public RefActionConfig setActionModel(ActionModel actionModel) {
		this.actionModel = actionModel;
		if(actionModel == null) {
			this.actionModelID = null;
		}else {
			this.actionModelID = actionModel.getId();
		}
		return this;
	}
	public String getActionUuid() {
		return actionUuid;
	}
	public RefActionConfig setActionUuid(String actionInstUuid) {
		this.actionUuid = actionInstUuid;
		return this;
	}
	protected IDaoService getDaoService() {
		return Cells.get(IDaoService.class);
	}
	public Action getAction() throws Exception {
		if(action == null) {
			if(!CmnUtil.isStringEmpty(actionModelID) && !CmnUtil.isStringEmpty(actionUuid)) {
				try(IDao dao = getDaoService().newDao()){
					action = IActionMgr.get().queryAction(dao,actionModelID, actionUuid);
				}
			}
		}
		return action;
	}
	public RefActionConfig setAction(Action action) {
		this.action = action;
		setRefInstModified(true);
		if(action == null) {
			this.actionUuid = null;
			this.actionModelID = null;
		}else {
			this.actionUuid = action.getUuid();
			this.actionModelID = action.getFormModelId();
		}
		return this;
	}
	public boolean isRefInstModified() {
		return isRefInstModified;
	}
	public void setRefInstModified(boolean isRefInstModified) {
		this.isRefInstModified = isRefInstModified;
	}
	
	@Override
	public String toString() {
		return "function("+name+")";
	}
	
}
