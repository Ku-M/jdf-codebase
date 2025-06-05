package gpf.dc.basic.fe.component.param;

import fe.component.annotation.WidgetParamComment;
import fe.util.component.param.WidgetParam;

public class AppHomePageParam extends WidgetParam{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3746835951430635335L;
	@WidgetParamComment(label="应用名称")
	String appName;
	@WidgetParamComment(label="应用标签")
	String appLabel;
	@WidgetParamComment(label="发布系统uuid")
	String systemUuid;
	
	String sessionKey;
	
	boolean shwoTodoTab = false;
	String openTabId;
	
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppLabel() {
		return appLabel;
	}
	public void setAppLabel(String appLabel) {
		this.appLabel = appLabel;
	}
	public String getSystemUuid() {
		return systemUuid;
	}
	public void setSystemUuid(String systemUuid) {
		this.systemUuid = systemUuid;
	}
	public boolean isShwoTodoTab() {
		return shwoTodoTab;
	}
	public void setShwoTodoTab(boolean shwoTodoTab) {
		this.shwoTodoTab = shwoTodoTab;
	}
	public String getOpenTabId() {
		return openTabId;
	}
	public void setOpenTabId(String openTabId) {
		this.openTabId = openTabId;
	}
	public String getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
}