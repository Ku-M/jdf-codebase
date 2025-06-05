package gpf.dc.basic.param.view.dto;

import java.util.Collections;
import java.util.List;

import com.kwaidoo.ms.tool.CmnUtil;

import cn.hutool.core.collection.CollUtil;
import fe.util.enums.LabelLayoutDirection;
import gpf.adur.data.FormField;
import gpf.dc.basic.fe.enums.ColumnAlignType;
import gpf.dc.concrete.RefActionConfig;

public class FormFieldDefine extends FormField{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7284411435218295908L;
	String uuid;
	String alias;
	String tips;
	String labelStyle;
	Long labelWidth;
	//组件对齐方式
	String componentAlign;
	/**
	 * 组件只读对齐样式
	 */
	String readOnlyComponentAlign;
	Long componentWitdh;
	Long componentHeight;
	Boolean componentExpandInBox;
	List<RefActionConfig> dataFilterFuncs;
	String initValue;
	/**
	 * 组件样式
	 */
	String componentStyle;
	/**
	 * 标签组
	 */
	String tagGroupIds;
	
	public FormFieldDefine() {
		super();
	}
	
	public FormFieldDefine(String code) {
		super(code);
	}
		
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public String getLabelStyle() {
		return labelStyle;
	}

	public void setLabelStyle(String labelStyle) {
		this.labelStyle = labelStyle;
	}
	
	public LabelLayoutDirection getLabelLayoutDirection() {
		for(LabelLayoutDirection v : LabelLayoutDirection.values()) {
			if(CmnUtil.isStringEqual(labelStyle, v.name()))
				return v;
		}
		return null;
	}

	public Long getLabelWidth() {
		return labelWidth;
	}

	public void setLabelWidth(Long labelWidth) {
		this.labelWidth = labelWidth;
	}

	public Long getComponentWitdh() {
		return componentWitdh;
	}

	public void setComponentWitdh(Long componentWitdh) {
		this.componentWitdh = componentWitdh;
	}

	public Long getComponentHeight() {
		return componentHeight;
	}

	public void setComponentHeight(Long componentHeight) {
		this.componentHeight = componentHeight;
	}

	public boolean isComponentExpandInBox() {
		return componentExpandInBox != null && componentExpandInBox;
	}

	public void setComponentExpandInBox(Boolean componentExpandInBox) {
		this.componentExpandInBox = componentExpandInBox;
	}
	
	public List<RefActionConfig> getDataFilterFuncs() {
		return dataFilterFuncs;
	}
	public void setDataFilterFuncs(List<RefActionConfig> dataFilterFuncs) {
		this.dataFilterFuncs = dataFilterFuncs;
	}
	
	public String getInitValue() {
		return initValue;
	}
	public void setInitValue(String initValue) {
		this.initValue = initValue;
	}
	
	public String getComponentAlign() {
		return componentAlign;
	}
	public void setComponentAlign(String componentAlign) {
		this.componentAlign = componentAlign;
	}
	public ColumnAlignType getComponentAlignEnum() {
		for(ColumnAlignType item : ColumnAlignType.values()) {
			if(CmnUtil.isStringEqual(item.name(), getComponentAlign()))
				return item;
		}
		return null;
	}
	
	public String getReadOnlyComponentAlign() {
		return readOnlyComponentAlign;
	}
	public void setReadOnlyComponentAlign(String readOnlyComponentAlign) {
		this.readOnlyComponentAlign = readOnlyComponentAlign;
	}
	public ColumnAlignType getReadOnlyComponentAlignEnum() {
		for(ColumnAlignType item : ColumnAlignType.values()) {
			if(CmnUtil.isStringEqual(item.name(), getReadOnlyComponentAlign()))
				return item;
		}
		return null;
	}
	
	public String getComponentStyle() {
		return componentStyle;
	}
	public FormFieldDefine setComponentStyle(String componentStyle) {
		this.componentStyle = componentStyle;
		return this;
	}
	
	public String getTagGroupIds() {
		return tagGroupIds;
	}
	public FormFieldDefine setTagGroupIds(String groupIds) {
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
}
