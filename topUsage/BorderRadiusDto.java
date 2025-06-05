package fe.cmn.widget.decoration;

import cson.core.CsonPojo;
import flutter.coder.annt.FlutterCode;

// 圆角设置
@FlutterCode("BorderRadiusDto.all(double radius) {setObjectType(JAVA_TYPE);RadiusDto radiusDto = RadiusDto()..radius = radius;this.topLeft = radiusDto;this.topRight = radiusDto;this.bottomLeft = radiusDto;this.bottomRight = radiusDto;}")
public class BorderRadiusDto extends CsonPojo {

	private static final long serialVersionUID = 5193872750219709784L;

	RadiusDto topLeft;

	RadiusDto topRight;

	RadiusDto bottomLeft;

	RadiusDto bottomRight;

	public RadiusDto getTopLeft() {
		return topLeft;
	}

	public BorderRadiusDto setTopLeft(RadiusDto topLeft) {
		this.topLeft = topLeft;
		return this;
	}

	public RadiusDto getTopRight() {
		return topRight;
	}

	public BorderRadiusDto setTopRight(RadiusDto topRight) {
		this.topRight = topRight;
		return this;
	}

	public RadiusDto getBottomLeft() {
		return bottomLeft;
	}

	public BorderRadiusDto setBottomLeft(RadiusDto bottomLeft) {
		this.bottomLeft = bottomLeft;
		return this;
	}

	public RadiusDto getBottomRight() {
		return bottomRight;
	}

	public BorderRadiusDto setBottomRight(RadiusDto bottomRight) {
		this.bottomRight = bottomRight;
		return this;
	}

	// 设置所有
	public static BorderRadiusDto all(RadiusDto RadiusDto) {
		BorderRadiusDto borderRadiusDto = new BorderRadiusDto().setTopLeft(RadiusDto).setTopRight(RadiusDto)
				.setBottomLeft(RadiusDto).setBottomRight(RadiusDto);
		return borderRadiusDto;
	}

	// 水平设置
	public static BorderRadiusDto horizontal(RadiusDto leftRadiusDto, RadiusDto rightRadiusDto) {
		BorderRadiusDto borderRadiusDto = new BorderRadiusDto().setTopLeft(leftRadiusDto).setTopRight(rightRadiusDto)
				.setBottomLeft(leftRadiusDto).setBottomRight(rightRadiusDto);
		return borderRadiusDto;
	}

	// 垂直设置
	public static BorderRadiusDto vertical(RadiusDto topRadiusDto, RadiusDto bottomRadiusDto) {
		BorderRadiusDto borderRadiusDto = new BorderRadiusDto().setTopLeft(topRadiusDto).setTopRight(topRadiusDto)
				.setBottomLeft(bottomRadiusDto).setBottomRight(bottomRadiusDto);
		return borderRadiusDto;
	}
	
	public static BorderRadiusDto zero() {
		BorderRadiusDto borderRadiusDto = BorderRadiusDto.all(RadiusDto.zero());
		return borderRadiusDto;
	}
}
