package fe.cmn.widget;

import java.awt.Color;
import java.util.List;

import fe.cmn.data.CColor;
import fe.cmn.event.EventSubscriberDto;
import fe.cmn.pojo.annotation.PojoMeta;
import fe.cmn.widget.decoration.DecorationDto;
import flutter.coder.annt.DefaultGetter;

/**
 * 图片
 * 
 * <p>装饰类为ImageDecorationDto
 */
@PojoMeta(label = "图片", icon="res://images/units/image.png")
public class ImageDto extends WidgetDto {

	static final long serialVersionUID = 5709553652866639481L;
	
	// icon、服务器图片名称、链接
	String src;
	
	// 字节流
	byte[] bytes;
	
	// 图标颜色 
	@Deprecated
	CColor iconColor;
	
	// 点击图片时是否可以预览
	@DefaultGetter("false")
	Boolean preview;
	
	ImageZoomConfig zoomConfig;
	
	public ImageDto() {
		this.expandMeInBox = false;
	}
	
	public ImageDto(String src) {
		this.src = src;
		this.expandMeInBox = false;
	}

	public String getSrc() {
		return src;
	}

	public ImageDto setSrc(String src) {
		this.src = src;
		return this;
	}
	
	public byte[] getBytes() {
		return bytes;
	}

	public ImageDto setBytes(byte[] bytes) {
		this.bytes = bytes;
		return this;
	}

	@Deprecated
	public CColor getIconColor() {
		return iconColor;
	}

	@Deprecated
	public ImageDto setIconColor(CColor iconColor) {
		this.iconColor = iconColor;
		return this;
	}

	@Deprecated
	public ImageDto setIconColor(Color iconColor) {
		this.iconColor = CColor.fromColor(iconColor);
		return this;
	}

	public Boolean getPreview() {
		return preview;
	}

	public ImageDto setPreview(Boolean preview) {
		this.preview = preview;
		return this;
	}

	public ImageZoomConfig getZoomConfig() {
		return zoomConfig;
	}

	public ImageDto setZoomConfig(ImageZoomConfig zoomConfig) {
		this.zoomConfig = zoomConfig;
		return this;
	}

	@Override
	public ImageDto setWidgetId(String widgetId) {
		return (ImageDto) super.setWidgetId(widgetId);
	}

	@Override
	public ImageDto setDropListener(DropListener dropListener) {
		return (ImageDto) super.setDropListener(dropListener);
	}

	@Override
	public ImageDto setSubscribeEvents(List<EventSubscriberDto> subscribeEvents) {
		return (ImageDto) super.setSubscribeEvents(subscribeEvents);
	}

	@Override
	public ImageDto addSubscribeEvent(EventSubscriberDto subscriber) {
		return (ImageDto) super.addSubscribeEvent(subscriber);
	}

	@Override
	public ImageDto setPreferSize(SizeDto preferSize) {
		return (ImageDto) super.setPreferSize(preferSize);
	}

	@Override
	public ImageDto setMinSize(SizeDto minSize) {
		return (ImageDto) super.setMinSize(minSize);
	}

	@Override
	public ImageDto setMaxSize(SizeDto maxSize) {
		return (ImageDto) super.setMaxSize(maxSize);
	}

	@Override
	public ImageDto setExpandInBox(boolean expandInBox) {
		return (ImageDto) super.setExpandInBox(expandInBox);
	}

	@Override
	public ImageDto setVisible(boolean visible) {
		return (ImageDto) super.setVisible(visible);
	}

	@Override
	public ImageDto setDraggable(DraggableDto draggableData) {
		return (ImageDto) super.setDraggable(draggableData);
	}

	@Override
	public ImageDto setDecoration(DecorationDto decoration) {
		return (ImageDto) super.setDecoration(decoration);
	}
}
