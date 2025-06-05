package fe.cmn.widget.decoration;

import com.leavay.common.util.ToolBasic;
import fe.cmn.data.CColor;
import fe.cmn.pojo.annotation.FieldDefine;
import fe.cmn.pojo.annotation.PojoMeta;
import fe.cmn.text.CTextStyle;
import fe.cmn.widget.InsetDto;
import flutter.coder.annt.NullSafe;

import java.awt.*;

@PojoMeta(label = "基础样式")
public class DecorationDto extends BasicDecorationDto {
	private static final long serialVersionUID = -7406300193044180854L;
    @FieldDefine(label="文字样式")
	CTextStyle textStyle;

	@FieldDefine(label="内边距")
	InsetDto padding;

    @FieldDefine(label="外边距")
	InsetDto margin;
    
    @FieldDefine(label="形状")
	ShapeType shape;
	
	// 光标样式
    @FieldDefine(label="光标样式")
	MouseCursorType mouseCursorType;

	/*
	 * 溢出部分裁剪
	 * 不设置默认为不裁剪
	 *
	 * 1.hardEdge：快速裁剪，保真度较低
	 * 2.antiAlias：抗锯齿裁剪，边缘平滑
	 * 3.antiAliasWithSaveLayer： 更为准确地裁剪，很少用到
	 *
	 * 渲染速度：hardEdge > antiAlias > antiAliasWithSaveLayer
	 * */
    @FieldDefine(label="溢出裁切", description = "\n1.hardEdge：快速裁剪，保真度较低\n2.antiAlias：抗锯齿裁剪，边缘平滑\n3.antiAliasWithSaveLayer： 更为准确地裁剪，很少用到\n\n渲染速度：hardEdge > antiAlias > antiAliasWithSaveLayer")
	ClipType clip;

	// ToopTip样式配置
    @FieldDefine(label="Tooltip样式")
	ToolTipDecorationDto toolTipDecoration;
	
    @FieldDefine(label="透明度", description = "0-1之间的小数")
	@NullSafe(initCode = "1.0")
	double opacity = 1.0;
    
    // 模糊度
    @FieldDefine(label="模糊度", description = "数值越大越模糊")
    double blur;
	
	public DecorationDto() {
		
	}

	public static DecorationDto borderAll(Color color, double width) {
		return new DecorationDto().setBorder(BorderDto.all(color, width));
	}

	public static DecorationDto borderAll(Color color, double width, double borderRadius) {
		return new DecorationDto().setBorder(BorderDto.all(color, width)).setBorderRadius(BorderRadiusDto.all(new RadiusDto(RadiusType.circular, borderRadius)));
	}
	
	public static DecorationDto borderAll(CColor color, double width) {
		return new DecorationDto().setBorder(BorderDto.all(color, width));
	}

	public static DecorationDto borderAll(CColor color, double width, double borderRadius) {
		return new DecorationDto().setBorder(BorderDto.all(color, width)).setBorderRadius(BorderRadiusDto.all(new RadiusDto(RadiusType.circular, borderRadius)));
	}

	public static DecorationDto padding(Double left, Double top, Double right, Double bottom) {
		return new DecorationDto().setPadding(new InsetDto(left, top, right, bottom));
	}

	public static DecorationDto paddingLeftRight(Double padding) {
		return new DecorationDto().setPadding(InsetDto.leftRight(padding));
	}

	public static DecorationDto paddingTopBottom(Double padding) {
		return new DecorationDto().setPadding(InsetDto.topBottom(padding));
	}

	public static DecorationDto paddingAll(Double padding) {
		return new DecorationDto().setPadding(padding);
	}

	public static DecorationDto margin(Double left, Double top, Double right, Double bottom) {
		return new DecorationDto().setMargin(new InsetDto(left, top, right, bottom));
	}

	public static DecorationDto marginLeftRight(Double margin) {
		return new DecorationDto().setMargin(InsetDto.leftRight(margin));
	}

	public static DecorationDto marginTopBottom(Double padding) {
		return new DecorationDto().setMargin(InsetDto.topBottom(padding));
	}

	public static DecorationDto marginAll(Double margin) {
		return new DecorationDto().setMargin(margin);
	}

	public static DecorationDto background(Color color) {
		return new DecorationDto().setBackground(color);
	}

    public static DecorationDto background(CColor color) {
        return new DecorationDto().setBackground(color);
    }

	public CTextStyle getTextStyle() {
		return textStyle;
	}

	public DecorationDto setTextStyle(CTextStyle textStyle) {
		this.textStyle = textStyle;
		return this;
	}
	
	@Override
	public DecorationDto setBackground(CColor background) {
		this.background = background;
		return this;
	}

	@Override
	public DecorationDto setBackground(Color background) {
		this.background = CColor.fromColor(background);
		return this;
	}
	
	@Override
	public DecorationDto setBorderRadius(BorderRadiusDto borderRadius) {
		this.borderRadius = borderRadius;
		return this;
	}
	
	@Override
	public DecorationDto setBorder(BorderDto border) {
		this.border = border;
		return this;
	}
	
	@Override
	public DecorationDto setBorder(Color color, Double width, Double borderRadius) {
		this.border = BorderDto.all(color, width);
		this.borderRadius = BorderRadiusDto.all(new RadiusDto(RadiusType.circular, borderRadius));
		return this;
	}
	
	@Override
	public DecorationDto setBorder(CColor color, Double width, Double borderRadius) {
		this.border = BorderDto.all(color, width);
		this.borderRadius = BorderRadiusDto.all(new RadiusDto(RadiusType.circular, borderRadius));
		return this;
	}
	
	@Override
	public DecorationDto setShadow(ShadowDto... shadows) {
		this.shadow = ToolBasic.array2List(shadows);
		return this;
	}
	
	public ShapeType getShape() {
		return shape;
	}

	public DecorationDto setShape(ShapeType shape) {
		this.shape = shape;
		return this;
	}
	
	@Override
	public DecorationDto setBackgroundImage(BackgroundImageDto backgroundImage) {
		this.backgroundImage = backgroundImage;
		return this;
	}
	
	@Override
	public DecorationDto setGradient(GradientDto gradient) {
		super.setGradient(gradient);
		return this;
	}

	public InsetDto getPadding() {
		return padding;
	}

	public DecorationDto setPadding(InsetDto padding) {
		this.padding = padding;
		return this;
	}

	public DecorationDto setPadding(double padding) {
		this.padding = InsetDto.all(padding);
		return this;
	}

	public InsetDto getMargin() {
		return margin;
	}

	public DecorationDto setMargin(InsetDto margin) {
		this.margin = margin;
		return this;
	}

	public DecorationDto setMargin(double margin) {
		this.margin = InsetDto.all(margin);
		return this;
	}

	public ClipType getClip() {
		return clip;
	}

	public DecorationDto setClip(ClipType clip) {
		this.clip = clip;
		return this;
	}

	public ToolTipDecorationDto getToolTipDecoration() {
		return toolTipDecoration;
	}

	public DecorationDto setToolTipDecoration(ToolTipDecorationDto toolTipDecoration) {
		this.toolTipDecoration = toolTipDecoration;
		return this;
	}

	public MouseCursorType getMouseCursorType() {
		return mouseCursorType;
	}

	public DecorationDto setMouseCursorType(MouseCursorType mouseCursorType) {
		this.mouseCursorType = mouseCursorType;
		return this;
	}

	public double getOpacity() {
		return opacity;
	}

	public DecorationDto setOpacity(double opacity) {
		this.opacity = opacity;
		return this;
	}

	public double getBlur() {
		return blur;
	}

	public DecorationDto setBlur(double blur) {
		this.blur = blur;
		return this;
	}
}
