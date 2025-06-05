package fe.cmn.widget.decoration;

import java.awt.Color;

import cson.core.CsonPojo;
import fe.cmn.data.CColor;
import fe.cmn.pojo.annotation.FieldDefine;
import flutter.coder.annt.FlutterCode;

@FlutterCode("IconStyleDto.build(this.iconColor, this.size){setObjectType(JAVA_TYPE);}\n\tIconStyleDto merge(IconStyleDto? other) {return IconStyleDto.build(other?.iconColor ?? iconColor, other?.size ?? size);}\n\tstatic IconStyleDto? mergeTwoStyle(IconStyleDto? orginStyle, IconStyleDto? newStyle) {return orginStyle == null ? newStyle : orginStyle.merge(newStyle);}")
public class IconStyleDto extends CsonPojo {

	private static final long serialVersionUID = -6304443302178086554L;
	
	// 颜色
	@FieldDefine(label = "图标颜色")
	CColor iconColor; // 纯色Icon/png是可以设置图标颜色的，现在也比较流行纯色Icon
	
	// 大小
	@FieldDefine(label= "图标大小")
	double size;
	
	public IconStyleDto() {
		
	}
	
	public IconStyleDto(CColor iconColor) {
		this.iconColor = iconColor;
	}
	
	public IconStyleDto(Color iconColor) {
		this.iconColor = CColor.fromColor(iconColor);
	}
	
	public IconStyleDto(CColor iconColor, double size) {
		this.iconColor = iconColor;
		this.size = size;
	}
	
	public IconStyleDto(Color iconColor, double size) {
		this.iconColor = CColor.fromColor(iconColor);
		this.size = size;
	}

	public CColor getIconColor() {
		return iconColor;
	}

	public IconStyleDto setIconColor(CColor iconColor) {
		this.iconColor = iconColor;
		return this;
	}
	
	public IconStyleDto setIconColor(Color iconColor) {
		this.iconColor = CColor.fromColor(iconColor);
		return this;
	}

	public double getSize() {
		return size;
	}

	public IconStyleDto setSize(double size) {
		this.size = size;
		return this;
	}
}
