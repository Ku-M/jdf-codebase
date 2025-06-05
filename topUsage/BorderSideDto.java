package fe.cmn.widget.decoration;

import java.awt.Color;

import cson.core.CsonPojo;
import fe.cmn.data.CColor;

public class BorderSideDto extends CsonPojo {

	private static final long serialVersionUID = -6018617557737399282L;
	
	//	边框颜色
	CColor color;
	
	//  边框宽度
	Double borderWidth;

	public BorderSideDto() {
	}

	public BorderSideDto(double width) {
		this.borderWidth = width;
		this.color = CColor.fromColor(Color.black);
	}

	public BorderSideDto(CColor color, double width) {
		this.color = color;
		this.borderWidth = width;
	}
	
	public BorderSideDto(Color color, double width) {
		this.color = CColor.fromColor(color);
		this.borderWidth = width;
	}

	public CColor getColor() {
		return color;
	}

	public BorderSideDto setColor(CColor color) {
		this.color = color;
		return this;
	}
	
	public BorderSideDto setColor(Color color) {
		this.color = CColor.fromColor(color);
		return this;
	}

	public double getWidth() {
		return borderWidth;
	}

	public BorderSideDto setWidth(double width) {
		this.borderWidth = width;
		return this;
	}
}
