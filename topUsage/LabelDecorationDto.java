package fe.cmn.widget.decoration;

import fe.cmn.pojo.annotation.FieldDefine;
import fe.cmn.pojo.annotation.PojoMeta;
import fe.cmn.text.CTextDirection;
import fe.cmn.widget.CLabelAlign;
import flutter.coder.annt.FlutterCode;

@PojoMeta(label = "标签样式", icon="res://images/units/text.png")
@FlutterCode("\tLabelDecorationDto.build(this.align, this.maxLines) {setObjectType(JAVA_TYPE);}")
public class LabelDecorationDto extends DecorationDto {

	private static final long serialVersionUID = -2328420149968367927L;

	// 整体相对容器对齐
	@FieldDefine(label="整体相对容器对齐")
	CLabelAlign align;
	
	// 文字对齐
	@FieldDefine(label="文字对齐")
	CTextAlign textAlign;
	
	/**
	 * 最大行数，表格单元格下默认为1，其余默认为无限制。
	 * 
	 * CTextStyle.overflow的溢出判定和maxLines设置相关：
	 * 
	 * 1. 为null或者单行时，则第一行文本超出宽度限制时视为溢出。
	 * 2. 为多行时，则最后一行文本超出宽度限制时视为溢出（无视高度限制，会出现行溢出）。
	 */
	@FieldDefine(label="最大行数", description = "表格单元格下默认为1，其余默认为无限制。")
	int maxLines;
	
	/**
	 * 每个逻辑像素的字体像素数。
	 * 
	 * 例如，如果文本比例因子为 1.5，则文本将比指定的字体大小大 50%。
	 */
	@FieldDefine(label="每个逻辑像素的字体像素数", description = "例如，如果文本比例因子为 1.5，则文本将比指定的字体大小大 50%。")
	Double textScaleFactor;
	
	/**
	 * 文本是否应在软换行符处断开。
	 * 
	 * 如果为 false，则文本中的字形将定位为水平空间无限。
	 */
	@FieldDefine(label="文本是否应在软换行符处断开", description = "如果为 false，则文本中的字形将定位为水平空间无限。")
	Boolean softWrap;
	
	/**
	 * 文本的方向性。
	 * 
	 * 这决定了如何解释 [textAlign] 值，例如 [CTextAlign.start] 和 [CTextAlign.end]。
	 * 
	 * 这也用于消除如何呈现双向文本的歧义。例如，如果文本是一个英语短语，后面跟着一个希伯来语短语，则在 [CTextDirection.ltr] 上下文中，英语短语将位于左侧，希伯来语短语将位于其右侧；
	 * 而在 [CTextDirection.rtl] 上下文中，英语短语将位于右侧，希伯来语短语将位于其左侧。
	 */
	@FieldDefine(label="文本的方向性", description = "这决定了如何解释 [textAlign] 值，例如 [CTextAlign.start] 和 [CTextAlign.end]。\n\n这也用于消除如何呈现双向文本的歧义。例如，如果文本是一个英语短语，后面跟着一个希伯来语短语，则在 [CTextDirection.ltr] 上下文中，英语短语将位于左侧，希伯来语短语将位于其右侧；\n而在 [CTextDirection.rtl] 上下文中，英语短语将位于右侧，希伯来语短语将位于其左侧。")
	CTextDirection textDirection;

	public LabelDecorationDto() {

	}

	public CLabelAlign getAlign() {
		return align;
	}

	public LabelDecorationDto setAlign(CLabelAlign align) {
		this.align = align;
		return this;
	}
	
	public CTextAlign getTextAlign() {
		return textAlign;
	}

	public LabelDecorationDto setTextAlign(CTextAlign textAlign) {
		this.textAlign = textAlign;
		return this;
	}

	public int getMaxLines() {
		return maxLines;
	}

	public LabelDecorationDto setMaxLines(int maxLines) {
		this.maxLines = maxLines;
		return this;
	}

	public Double getTextScaleFactor() {
		return textScaleFactor;
	}

	public LabelDecorationDto setTextScaleFactor(Double textScaleFactor) {
		this.textScaleFactor = textScaleFactor;
		return this;
	}

	public Boolean getSoftWrap() {
		return softWrap;
	}

	public LabelDecorationDto setSoftWrap(Boolean softWrap) {
		this.softWrap = softWrap;
		return this;
	}

	public CTextDirection getTextDirection() {
		return textDirection;
	}

	public LabelDecorationDto setTextDirection(CTextDirection textDirection) {
		this.textDirection = textDirection;
		return this;
	}
}
