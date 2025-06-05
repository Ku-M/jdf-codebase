package gpf.dc.basic.param.view.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kwaidoo.ms.tool.ToolUtilities;
import com.leavay.ms.tool.CmnUtil;

import cmn.util.Nulls;
import gpf.dc.basic.fe.enums.EnumUtil;
import gpf.dc.basic.fe.enums.ListenerApplyLocation;
import gpf.dc.basic.fe.enums.ListenerType;

public class ListenerDefine implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3606691272263371409L;
	String uuid = ToolUtilities.allockUUIDWithUnderline();
//	List<String> command;
	//应用位置 ListenerApplyLocation
	List<String> applyLocations; 
	String listenerType;
	String combineKeyCode;
	boolean synchronize; 
//	ListenerDto listener;
	String description;
	String sourceWidgetId;
	String sourceCommand;
//	Function function;
//	String targetWidgetId;
//	String targetCommand;
	List<ListenerResponse> responseSettings;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
//	public List<String> getCommand() {
//		return command;
//	}
//	public void setCommand(List<String> command) {
//		this.command = command;
//	}
//	public String getCommandString() {
//		if(CmnUtil.isCollectionEmpty(command))
//			return null;
//		return String.join(",", ToolUtilities.toStringArray(command));
//	}
	public List<String> getApplyLocations() {
		return applyLocations;
	}
	public List<ListenerApplyLocation> getApplyLocationEnums() {
		return EnumUtil.getEnumByName(ListenerApplyLocation.class, applyLocations);
	}
	public ListenerDefine setApplyLocations(List<String> applyLocations) {
		this.applyLocations = applyLocations;
		return this;
	}
	public String getListenerType() {
		return listenerType;
	}
	public void setListenerType(String listenerType) {
		this.listenerType = listenerType;
	}
	public ListenerType getListenerTypeEnum() {
		return EnumUtil.getEnumByName(ListenerType.class, listenerType);
	}
	public String getCombineKeyCode() {
		return combineKeyCode;
	}
	public ListenerDefine setCombineKeyCode(String combineKeyCode) {
		this.combineKeyCode = combineKeyCode;
		return this;
	}
//	public ListenerDto getListener() {
//		return listener;
//	}
//	public void setListener(ListenerDto listener) {
//		this.listener = listener;
//	}
	public boolean isSynchronize() {
		return synchronize;
	}
	public void setSynchronize(boolean synchronize) {
		this.synchronize = synchronize;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
//	public Function getFunction() {
//		return function;
//	}
//	public void setFunction(Function function) {
//		this.function = function;
//	}
	public String getSourceWidgetId() {
		return sourceWidgetId;
	}
	public void setSourceWidgetId(String sourceWidgetId) {
		this.sourceWidgetId = sourceWidgetId;
	}
	public String getSourceCommand() {
		return sourceCommand;
	}
	public void setSourceCommand(String sourceCommand) {
		this.sourceCommand = sourceCommand;
	}
//	public String getTargetWidgetId() {
//		return targetWidgetId;
//	}
//	public void setTargetWidgetId(String targetWidgetId) {
//		this.targetWidgetId = targetWidgetId;
//	}
//	public String getTargetCommand() {
//		return targetCommand;
//	}
//	public void setTargetCommand(String targetCommand) {
//		this.targetCommand = targetCommand;
//	}
	public List<ListenerResponse> getResponseSettings() {
		return responseSettings;
	}
	public void setResponseSettings(List<ListenerResponse> responseSettings) {
		this.responseSettings = responseSettings;
	}
	public String getResponseCommand() {
		List<String> responseCommands = new ArrayList<>();
		for(ListenerResponse resp : Nulls.get(responseSettings)) {
			if(CmnUtil.isStringEmpty(resp.getCommand()))
				continue;
			responseCommands.add(resp.getCommand());
		}
		return String.join(",", responseCommands);
	}
	public boolean containResponseCommand(String command) {
		for(ListenerResponse resp : Nulls.get(responseSettings)) {
			if(CmnUtil.isStringEqual(resp.getCommand(), command))
				return true;
		}
		return false;
	}
	
	public ListenerResponse getListenerResponseByCommand(String command) {
		for(ListenerResponse resp : Nulls.get(responseSettings)) {
			if(CmnUtil.isStringEqual(resp.getCommand(), command))
				return resp;
		}
		return null;
	}
}
