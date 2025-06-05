package gpf.dc.basic.param.view.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.kwaidoo.ms.tool.CmnUtil;

import cn.hutool.core.collection.CollUtil;
import fe.cmn.widget.IconDto;
import fe.cmn.widget.ImageDto;
import fe.cmn.widget.WidgetDto;

public class ButtonDefine implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1423705811868431187L;
	String uuid;
	String name;
	String label;
	WidgetDto icon;
	/**
	 * 所属按钮组
	 */
	String belongGroup;
	/**
	 * 是否按钮组
	 */
	boolean isButtonGroup = false;
//	String command;
	String tagGroupIds;
	String style;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public WidgetDto getIcon() {
		return icon;
	}
	public void setIcon(WidgetDto icon) {
		this.icon = icon;
	}
	
	public String getIconSrc() {
		if(icon != null) {
			if(icon instanceof ImageDto) {
				return ((ImageDto) icon).getSrc();
			}else if(icon instanceof IconDto) {
				return ((IconDto) icon).getSrc();
			}
		}
		return null;
	}
	
	public String getBelongGroup() {
		return belongGroup;
	}
	public ButtonDefine setBelongGroup(String belongGroup) {
		this.belongGroup = belongGroup;
		return this;
	}
	
	public boolean isButtonGroup() {
		return isButtonGroup;
	}
	public ButtonDefine setButtonGroup(boolean isButtonGroup) {
		this.isButtonGroup = isButtonGroup;
		return this;
	}
	
	public String getTagGroupIds() {
		return tagGroupIds;
	}
	public ButtonDefine setTagGroupIds(String groupIds) {
		this.tagGroupIds = groupIds;
		return this;
	}
	
	public List<String> getTagGroupList(){
		if(CmnUtil.isStringEmpty(tagGroupIds)) {
			return Collections.emptyList();
		}
		String[] strArr = tagGroupIds.split(",");
		return CollUtil.newArrayList(strArr);
	}
	
	public String[] getTagGroupIdArray(){
		if(CmnUtil.isStringEmpty(tagGroupIds)) {
			return null;
		}
		String[] strArr = tagGroupIds.split(",");
		return strArr;
	}
	public String getStyle() {
		return style;
	}
	public ButtonDefine setStyle(String style) {
		this.style = style;
		return this;
	}
//	public String getCommand() {
//		return command;
//	}
//	public void setCommand(String command) {
//		this.command = command;
//	}
	
}