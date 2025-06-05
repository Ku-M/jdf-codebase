package fe.cmn.text;

import com.leavay.common.util.ToolUtilities;
import cson.core.CsonPojo;
import fe.cmn.data.CColor;
import fe.cmn.pojo.annotation.FieldDefine;
import fe.cmn.pojo.annotation.PojoMeta;

import java.awt.*;
import java.util.List;

@PojoMeta(label = "文字样式")
public class CTextStyle extends CsonPojo {
	private static final long serialVersionUID = -5550996118420241224L;

	// 字体名
	@FieldDefine(label = "字体名")
	String fontFamily;

	// 溢出时的样式
	/**
	 * 支持多行文本的组件的溢出显示判定和最大行数设置相关：
	 * 
	 * 1. 为单行时，则第一行文本超出宽度限制时视为溢出。
	 * 2. 为多行时，则最后一行文本超出宽度限制时视为溢出（无视高度限制，会出现行溢出）。
	 */
    @FieldDefine(label = "溢出模式")
	CTextOverflow overflow;
	
	// 在设置定稿值时此值才会生效（空值代表TRUE）
    @FieldDefine(label = "大小自动固定")
	Boolean fontSizeAutoFix;
	
	// 字体大小
    @FieldDefine(label = "字体大小")
	Double fontSize;

	// 颜色
    @FieldDefine(label = "颜色")
	CColor color;

	// 背景色
    @FieldDefine(label = "背景色")
	CColor backgroundColor;

	// 字体粗细（粗体、标准等）
    @FieldDefine(label = "字体粗细")
	CFontWeight fontWeight;

	// 是否斜体
    @FieldDefine(label = "是否斜体")
	Boolean italic;

	// 字母间距
    @FieldDefine(label = "字母间距")
	Double letterSpacing;

	// 单词间距
    @FieldDefine(label = "单词间距")
	Double wordSpacing;

	// 行高间距，字体大小的倍数如1.1， 1.2等
    @FieldDefine(label = "行高间距", description = "字体大小的倍数如1.1， 1.2等")
	Double height;

	// 阴影（可多个）
    @FieldDefine(label = "阴影")
	List<CTextShadow> shadows;

	// 装饰线：下划线、中划线等
    @FieldDefine(label = "装饰线")
	CTextDecoration decoration;

	// 装饰线颜色
    @FieldDefine(label = "装饰线颜色")
	CColor decorationColor;

	// 装饰线类型：波浪、虚线、直线、点等
    @FieldDefine(label = "装饰线类型")
	CTextDecorationStyle decorationStyle;

	// 装饰线粗细
    @FieldDefine(label = "装饰线粗细")
	Double decorationThickness;
    
    // 文本行距上下大小分布方式，默认为CTextLeadingDistribution.proportional
    @FieldDefine(label = "文本行距上下大小分布方式", description = "默认为CTextLeadingDistribution.proportional")
    CTextLeadingDistribution leadingDistribution;

	public CTextStyle() {
	}

	public CTextStyle(Double fontSize, Color color) {
		this.fontSize = fontSize;
		this.color = CColor.fromColor(color);
	}

	public CTextStyle(Double fontSize, CColor color) {
		this.fontSize = fontSize;
		this.color = color;
	}

	public String getFontFamily() {
		return fontFamily;
	}

	public CTextStyle setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
		return this;
	}

	public CTextOverflow getOverflow() {
		return overflow;
	}

	public CTextStyle setOverflow(CTextOverflow overflow) {
		this.overflow = overflow;
		return this;
	}

	public Double getFontSize() {
		return fontSize;
	}

	public CTextStyle setFontSize(Double fontSize) {
		this.fontSize = fontSize;
		return this;
	}

	public CColor getColor() {
		return color;
	}

	public CTextStyle setColor(CColor color) {
		this.color = color;
		return this;
	}

	public CTextStyle setColor(Color color) {
		this.color = CColor.fromColor(color);
		return this;
	}

	public CColor getBackgroundColor() {
		return backgroundColor;
	}

	public CTextStyle setBackgroundColor(CColor backgroundColor) {
		this.backgroundColor = backgroundColor;
		return this;
	}

	public CFontWeight getFontWeight() {
		return fontWeight;
	}

	public CTextStyle setFontWeight(CFontWeight fontWeight) {
		this.fontWeight = fontWeight;
		return this;
	}

	public Boolean getItalic() {
		return italic;
	}

	public CTextStyle setItalic(Boolean italic) {
		this.italic = italic;
		return this;
	}

	public Double getLetterSpacing() {
		return letterSpacing;
	}

	public CTextStyle setLetterSpacing(Double letterSpacing) {
		this.letterSpacing = letterSpacing;
		return this;
	}

	public Double getWordSpacing() {
		return wordSpacing;
	}

	public CTextStyle setWordSpacing(Double wordSpacing) {
		this.wordSpacing = wordSpacing;
		return this;
	}

	public Double getHeight() {
		return height;
	}

	public CTextStyle setHeight(Double height) {
		this.height = height;
		return this;
	}

	public List<CTextShadow> getShadows() {
		return shadows;
	}

	public CTextStyle setShadows(CTextShadow... shadows) {
		this.shadows = ToolUtilities.array2List(shadows);
		return this;
	}

	public CTextDecoration getDecoration() {
		return decoration;
	}

	public CTextStyle setDecoration(CTextDecoration decoration) {
		this.decoration = decoration;
		return this;
	}

	public CColor getDecorationColor() {
		return decorationColor;
	}

	public CTextStyle setDecorationColor(CColor decorationColor) {
		this.decorationColor = decorationColor;
		return this;
	}

	public CTextDecorationStyle getDecorationStyle() {
		return decorationStyle;
	}

	public CTextStyle setDecorationStyle(CTextDecorationStyle decorationStyle) {
		this.decorationStyle = decorationStyle;
		return this;
	}

	public Double getDecorationThickness() {
		return decorationThickness;
	}

	public CTextStyle setDecorationThickness(Double decorationThickness) {
		this.decorationThickness = decorationThickness;
		return this;
	}

	public Boolean getFontSizeAutoFix() {
		return fontSizeAutoFix;
	}

	public CTextStyle setFontSizeAutoFix(Boolean fontSizeAutoFix) {
		this.fontSizeAutoFix = fontSizeAutoFix;
		return this;
	}
	
	public CTextLeadingDistribution getLeadingDistribution() {
		return leadingDistribution;
	}

	public CTextStyle setLeadingDistribution(CTextLeadingDistribution leadingDistribution) {
		this.leadingDistribution = leadingDistribution;
		return this;
	}
}
