package gpf.dc.basic.param.view.dto;

import com.kwaidoo.ms.tool.CmnUtil;

import fe.util.component.dto.FormSetting;
import fe.util.enums.LabelLayoutDirection;
import gpf.dc.basic.fe.enums.ColumnAlignType;

public class FormViewSetting extends FormSetting{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8024590545664185092L;

	public final static String FilterMode_Normal = "nomral";
	public final static String FilterMode_Summary = "summary";
	/**
	 * 属性盒子样式
	 */
	String fieldBoxStyle;
	/**
	 * 标签页里没有属性时是否显示标签页
	 */
	boolean showTabWhenFieldsRFalse = false;
	public boolean isShowTabWhenFieldsRFalse() {
		return showTabWhenFieldsRFalse;
	}
	public void setShowTabWhenFieldsRFalse(boolean showTabWhenFieldsRFalse) {
		this.showTabWhenFieldsRFalse = showTabWhenFieldsRFalse;
	}
	
	
	/**
	 *	弹窗时点击外部进行关闭 
	 */
	boolean barrierDismissible = true;
	public boolean isBarrierDismissible() {
		return barrierDismissible;
	}
	public FormViewSetting setBarrierDismissible(boolean barrierDismissible) {
		this.barrierDismissible = barrierDismissible;
		return this;
	}
	/**
	 * 显示流程详情按钮
	 */
	boolean showOperateLogBtn = false;
	public boolean isShowOperateLogBtn() {
		return showOperateLogBtn;
	}
	public void setShowOperateLogBtn(boolean showOperateLogBtn) {
		this.showOperateLogBtn = showOperateLogBtn;
	}
	
	String title = "";
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	Long labelWidth;
	public Long getLabelWidth() {
		return labelWidth;
	}
	public void setLabelWidth(Long labelWidth) {
		this.labelWidth = labelWidth;
	}
	
	Long componentWidth;
	
	public Long getComponentWidth() {
		return componentWidth;
	}
	public void setComponentWidth(Long componentWidth) {
		this.componentWidth = componentWidth;
	}
	
	boolean componentWidthAdaptive= false;
	
	public boolean isComponentWidthAdaptive() {
		return componentWidthAdaptive;
	}
	public void setComponentWidthAdaptive(boolean componentWidthAdaptive) {
		this.componentWidthAdaptive = componentWidthAdaptive;
	}
	
	String componentAlign;
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
	
	String readOnlyComponentAlign;
	public String getReadOnlyComponentAlign() {
		return readOnlyComponentAlign;
	}
	public void setReadOnlyComponentAlign(String componentAlign) {
		this.readOnlyComponentAlign = componentAlign;
	}
	public ColumnAlignType getReadOnlyComponentAlignEnum() {
		for(ColumnAlignType item : ColumnAlignType.values()) {
			if(CmnUtil.isStringEqual(item.name(), getReadOnlyComponentAlign()))
				return item;
		}
		return null;
	}
	
	String labelLayoutDirection;
	
	public String getLabelLayoutDirection() {
		return labelLayoutDirection;
	}
	public void setLabelLayoutDirection(String labelLayoutDirection) {
		this.labelLayoutDirection = labelLayoutDirection;
	}
	public LabelLayoutDirection getLabelLayoutDirectionEnum() {
		for(LabelLayoutDirection item : LabelLayoutDirection.values()) {
			if(CmnUtil.isStringEqual(item.name(), labelLayoutDirection))
				return item;
		}
		return null;
	}
	
	public ColumnAlignType getBottomBarAlignEnum() {
		for(ColumnAlignType item : ColumnAlignType.values()) {
			if(CmnUtil.isStringEqual(item.name(), getFormActionsPosition()))
				return item;
		}
		return null;
	}
	
	/**
	 * 是否禁止编辑表单，默认可编辑
	 */
	Boolean writable;
	public boolean isWritable() {
		return writable == null || writable;
	}
	public void setWritable(boolean writable) {
		this.writable = writable;
	}
	
	public String getFieldBoxStyle() {
		return fieldBoxStyle;
	}
	public FormViewSetting setFieldBoxStyle(String fieldBoxStyle) {
		this.fieldBoxStyle = fieldBoxStyle;
		return this;
	}
	
}
