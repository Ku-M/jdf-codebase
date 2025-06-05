package fe.cmn.widget.decoration;

import java.awt.Color;

import fe.cmn.data.CColor;
import fe.cmn.pojo.annotation.FieldDefine;
import fe.cmn.pojo.annotation.PojoMeta;

@PojoMeta(label = "按钮样式", icon = "res://images/units/click_button.png")
public class ButtonDecorationDto extends DecorationDto {

	private static final long serialVersionUID = 2448858026083750532L;
	
	@FieldDefine(label="主题类型", description="其他字段会基于此主题进行合并")
	ButtonThemeType theme;

	@FieldDefine(label="图标样式")
	IconStyleDto icon;

	@FieldDefine(label="图标同文字之间的间隔")
	double gap;

	@FieldDefine(label="叠加颜色")
	CColor overlayColor;

	@FieldDefine(label="边框形状类型", description="圆形(CircleBorder)、足球场形状(StadiumBorder)无圆弧设置")
	ButtonBorderShapeType buttonShape;

	@FieldDefine(label="边框", description="由于字段类型原因，按钮边框不使用border设置，而是使用此字段")
	BorderSideDto buttonBorder;
	
	@FieldDefine(label="hover边框")
	BorderSideDto hoveredButtonBorder;
	
	@FieldDefine(label="聚焦边框")
	BorderSideDto focusedButtonBorder;

	@FieldDefine(label="对齐排列方式", description="仅在按钮整体大小大于内容(包含padding)时生效，默认为居中显示")
	AlignmentType alignment;

	@FieldDefine(label="按钮阴影颜色")
	CColor shadowColor;

	@FieldDefine(label="按钮阴影偏移值")
	double elevation;
	
	@FieldDefine(label="聚焦背景色")
	CColor focusedBackgroundColor;
	
	@FieldDefine(label="禁用背景色")
	CColor disableBackgroundColor;
	
	@FieldDefine(label="hover背景色")
	CColor hoverBackgroundColor;
	
	@FieldDefine(label="hover悬浮前景色", description="文本图标颜色")
	CColor hoveredForegroundColor;
	
	@FieldDefine(label="聚焦前景色", description="文本图标颜色")
	CColor focusedForegroundColor;
	
	@FieldDefine(label="禁用前景色", description="文本图标颜色")
	CColor disabledForegroundColor;

	public static ButtonDecorationDto setIconColor(CColor color) {
		return new ButtonDecorationDto().setIcon(new IconStyleDto().setIconColor(color));
	}

	public double getGap() {
		return gap;
	}

	public ButtonDecorationDto setGap(double gap) {
		this.gap = gap;
		return this;
	}

	public CColor getOverlayColor() {
		return overlayColor;
	}

	public ButtonDecorationDto setOverlayColor(CColor overlayColor) {
		this.overlayColor = overlayColor;
		return this;
	}
	
	public ButtonDecorationDto setOverlayColor(Color overlayColor) {
		this.overlayColor = CColor.fromColor(overlayColor);
		return this;
	}

	public ButtonBorderShapeType getButtonShape() {
		return buttonShape;
	}

	public ButtonDecorationDto setButtonShape(ButtonBorderShapeType buttonShape) {
		this.buttonShape = buttonShape;
		return this;
	}

	public BorderSideDto getButtonBorder() {
		return buttonBorder;
	}

	public ButtonDecorationDto setButtonBorder(BorderSideDto buttonBorder) {
		this.buttonBorder = buttonBorder;
		return this;
	}

	public AlignmentType getAlignment() {
		return alignment;
	}

	public ButtonDecorationDto setAlignment(AlignmentType alignment) {
		this.alignment = alignment;
		return this;
	}

	public IconStyleDto getIcon() {
		return icon;
	}

	public ButtonDecorationDto setIcon(IconStyleDto icon) {
		this.icon = icon;
		return this;
	}

	public CColor getShadowColor() {
		return shadowColor;
	}

	public ButtonDecorationDto setShadowColor(Color shadowColor) {
		this.shadowColor = CColor.fromColor(shadowColor);
		return this;
	}

	public ButtonDecorationDto setShadowColor(CColor shadowColor) {
		this.shadowColor = shadowColor;
		return this;
	}

	public double getElevation() {
		return elevation;
	}

	public ButtonDecorationDto setElevation(double elevation) {
		this.elevation = elevation;
		return this;
	}

	public ButtonThemeType getTheme() {
		return theme;
	}

	public ButtonDecorationDto setTheme(ButtonThemeType theme) {
		this.theme = theme;
		return this;
	}
	
	public BorderSideDto getHoveredButtonBorder() {
		return hoveredButtonBorder;
	}

	public ButtonDecorationDto setHoveredButtonBorder(BorderSideDto hoveredButtonBorder) {
		this.hoveredButtonBorder = hoveredButtonBorder;
		return this;
	}

	public BorderSideDto getFocusedButtonBorder() {
		return focusedButtonBorder;
	}

	public ButtonDecorationDto setFocusedButtonBorder(BorderSideDto focusedButtonBorder) {
		this.focusedButtonBorder = focusedButtonBorder;
		return this;
	}

	public CColor getHoveredForegroundColor() {
		return hoveredForegroundColor;
	}

	public ButtonDecorationDto setHoveredForegroundColor(CColor hoveredForegroundColor) {
		this.hoveredForegroundColor = hoveredForegroundColor;
		return this;
	}

	public CColor getFocusedForegroundColor() {
		return focusedForegroundColor;
	}
	

	public ButtonDecorationDto setFocusedForegroundColor(CColor focusedForegroundColor) {
		this.focusedForegroundColor = focusedForegroundColor;
		return this;
	}

	public CColor getDisabledForegroundColor() {
		return disabledForegroundColor;
	}

	public ButtonDecorationDto setDisabledForegroundColor(CColor disabledForegroundColor) {
		this.disabledForegroundColor = disabledForegroundColor;
		return this;
	}

	public CColor getFocusedBackgroundColor() {
		return focusedBackgroundColor;
	}

	public ButtonDecorationDto setFocusedBackgroundColor(CColor focusedBackgroundColor) {
		this.focusedBackgroundColor = focusedBackgroundColor;
		return this;
	}

	public CColor getDisableBackgroundColor() {
		return disableBackgroundColor;
	}

	public ButtonDecorationDto setDisableBackgroundColor(CColor disableBackgroundColor) {
		this.disableBackgroundColor = disableBackgroundColor;
		return this;
	}

	public CColor getHoverBackgroundColor() {
		return hoverBackgroundColor;
	}

	public ButtonDecorationDto setHoverBackgroundColor(CColor hoverBackgroundColor) {
		this.hoverBackgroundColor = hoverBackgroundColor;
		return this;
	}

	@Override
	public DecorationDto setBorder(BorderDto border) {
		throw new RuntimeException("Please use buttonBorder field to set the border on button");
	}
	
	@Override
	public DecorationDto setBorder(Color color, Double width, Double borderRadius) {
		throw new RuntimeException("Please use buttonBorder field to set the border on button");
	}
}
