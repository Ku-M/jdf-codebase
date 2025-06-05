package gpf.dc.basic.param.view.dto;

import java.util.List;

import com.kwaidoo.ms.tool.CmnUtil;

import fe.util.enums.LabelLayoutDirection;
import gpf.adur.data.DataType;
import gpf.adur.data.FormField;
import gpf.dc.concrete.RefActionConfig;

public class SearchBarDefine extends FormField{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5062281261643589466L;
	public final static String NormalFilter="NormalFilter";
	public final static String AdvanceFilter="AdvanceFilter";
	String uuid;
	String alias;
	String labelLayoutStyle;
	String advanceLabelLayoutStyle;
	String tips;
	Long labelWidth;
	Long componentWidth;
	List<String> showLocations;
	/**
	 * 组件样式
	 */
	String componentStyle;
	/**
	 * 数据过滤
	 */
	List<RefActionConfig> dataFilterFuncs;
	
	/**
	 * 是否自定义列
	 */
	Boolean isCustom=false;
	
	/**
	 * 扩展列数据类型
	 */
	String extFieldDataType;
	/**
	 * 扩展列关联模型
	 */
	String extAssocFormModel;
	
	/**
	 * 扩展列关联模型
	 */
	Boolean extMul;
	
	public SearchBarDefine(String fieldCode) {
		super(fieldCode);
	}
	public String getUuid() {
		return uuid;
	}
	public SearchBarDefine setUuid(String uuid) {
		this.uuid = uuid;
		return this;
	}
	public String getAlias() {
		return alias;
	}
	public SearchBarDefine setAlias(String alias) {
		this.alias = alias;
		return this;
	}
	public String getLabelLayoutStyle() {
		return labelLayoutStyle;
	}
	public SearchBarDefine setLabelLayoutStyle(String labelLayoutStyle) {
		this.labelLayoutStyle = labelLayoutStyle;
		return this;
	}
	public LabelLayoutDirection getLabelLayoutDirection() {
		for(LabelLayoutDirection v : LabelLayoutDirection.values()) {
			if(CmnUtil.isStringEqual(labelLayoutStyle, v.name()))
				return v;
		}
		return null;
	}
	public String getAdvanceLabelLayoutStyle() {
		return advanceLabelLayoutStyle;
	}
	public SearchBarDefine setAdvanceLabelLayoutStyle(String advanceLabelLayoutStyle) {
		this.advanceLabelLayoutStyle = advanceLabelLayoutStyle;
		return this;
	}
	public LabelLayoutDirection getAdvanceLabelLayoutDirection() {
		for(LabelLayoutDirection v : LabelLayoutDirection.values()) {
			if(CmnUtil.isStringEqual(advanceLabelLayoutStyle, v.name()))
				return v;
		}
		return null;
	}
	public String getTips() {
		return tips;
	}
	public SearchBarDefine setTips(String tips) {
		this.tips = tips;
		return this;
	}
	public Long getLabelWidth() {
		return labelWidth;
	}
	public SearchBarDefine setLabelWidth(Long labelWidth) {
		this.labelWidth = labelWidth;
		return this;
	}
	public Long getComponentWidth() {
		return componentWidth;
	}
	public SearchBarDefine setComponentWidth(Long componentWidth) {
		this.componentWidth = componentWidth;
		return this;
	}
	public List<String> getShowLocations() {
		return showLocations;
	}
	public SearchBarDefine setShowLocations(List<String> showLocations) {
		this.showLocations = showLocations;
		return this;
	}
	public boolean isShowAtNormalFilter() {
		return CmnUtil.isCollectionEmpty(showLocations) || showLocations.contains(NormalFilter);
	}
	public boolean isShowAtAdvanceFilter() {
		return CmnUtil.isCollectionEmpty(showLocations) || showLocations.contains(AdvanceFilter);
	}
	public String getComponentStyle() {
		return componentStyle;
	}
	public SearchBarDefine setComponentStyle(String componentStyle) {
		this.componentStyle = componentStyle;
		return this;
	}
	
	public List<RefActionConfig> getDataFilterFuncs() {
		return dataFilterFuncs;
	}
	public SearchBarDefine setDataFilterFuncs(List<RefActionConfig> dataFilterFuncs) {
		this.dataFilterFuncs = dataFilterFuncs;
		return this;
	}
	
	public Boolean getCustom() {
		return isCustom;
	}

	public void setCustom(Boolean custom) {
		isCustom = custom;
	}
	public boolean isCustom() {
		return isCustom != null && isCustom;
	}
	public String getExtFieldDataType() {
		return extFieldDataType;
	}
	public SearchBarDefine setExtFieldDataType(String extFieldDataType) {
		this.extFieldDataType = extFieldDataType;
		return this;
	}
	public DataType getExtFieldDataTypeEnum() {
		DataType dataType = gpf.adur.data.DataType.fromValue(extFieldDataType);
		if(dataType == null)
			return gpf.adur.data.DataType.Text;
		return dataType;
	}
	public String getExtAssocFormModel() {
		return extAssocFormModel;
	}
	public SearchBarDefine setExtAssocFormModel(String extAssocFormModel) {
		this.extAssocFormModel = extAssocFormModel;
		return this;
	}
}
