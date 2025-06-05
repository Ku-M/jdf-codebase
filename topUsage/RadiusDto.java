package fe.cmn.widget.decoration;

import cson.core.CsonPojo;

// 圆角
public class RadiusDto extends CsonPojo {

	private static final long serialVersionUID = -6587036464622088308L;

	public RadiusDto() {
	}

	public RadiusDto(RadiusType type, double radius) {
		this.type = type;
		this.radius = radius;
	}

	public RadiusDto(RadiusType type, double x, double y) {
		this.type = type;
		this.x = x;
		this.y = y;
	}

	// 无圆角
	public static RadiusDto zero( ) {
		return new RadiusDto().setType(RadiusType.circular).setRadius(0);
	};

	// 类型: 圆、椭圆
	RadiusType type;

	// 圆半径，例如组件长宽为200，设置一半（100）即为正圆。
	double radius;

	// 椭圆x
	double x;

	// 椭圆y
	double y;

	public RadiusType getType() {
		return type;
	}

	public RadiusDto setType(RadiusType type) {
		this.type = type;
		return this;
	}

	public double getRadius() {
		return radius;
	}

	public RadiusDto setRadius(double radius) {
		this.radius = radius;
		return this;
	}

	public double getX() {
		return x;
	}

	public RadiusDto setX(double x) {
		this.x = x;
		return this;
	}

	public double getY() {
		return y;
	}

	public RadiusDto setY(double y) {
		this.y = y;
		return this;
	}

	public static RadiusDto circular(double radius) {
		RadiusDto circular = new RadiusDto().setType(RadiusType.circular).setRadius(radius);
		return circular;
	}

	public static RadiusDto elliptical(double x, double y) {
		RadiusDto elliptical = new RadiusDto().setType(RadiusType.elliptical).setX(x).setY(y);
		return elliptical;
	}
	
	public static RadiusDto clipOvalBySize(double width, double height) {
		RadiusDto oval = new RadiusDto().setType(RadiusType.elliptical).setX(width * 8).setY(height * 8);
		return oval;
	}
}
