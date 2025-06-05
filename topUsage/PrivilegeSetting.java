package gpf.dc.concrete;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gpf.dc.intf.Inhertiable;

public class PrivilegeSetting extends Inhertiable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7712097702542462245L;
	String uuid;
	String roleCode;
	List<RefActionConfig> matchUserFunctions = new ArrayList<>();
	List<RefActionConfig> privilegeFunctions = new ArrayList<>();
	public String getUuid() {
		return uuid;
	}
	public PrivilegeSetting setUuid(String uuid) {
		this.uuid = uuid;
		return this;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public PrivilegeSetting setRoleCode(String roleCode) {
		this.roleCode = roleCode;
		return this;
	}
	public List<RefActionConfig> getMatchUserFunctions() {
		return matchUserFunctions;
	}
	public PrivilegeSetting setMatchUserFunctions(List<RefActionConfig> matchUserFunctions) {
		this.matchUserFunctions = matchUserFunctions;
		return this;
	}
	public List<RefActionConfig> getPrivilegeFunctions() {
		return privilegeFunctions;
	}
	public PrivilegeSetting setPrivilegeFunctions(List<RefActionConfig> privilegeFunctions) {
		this.privilegeFunctions = privilegeFunctions;
		return this;
	}
	
	
}
