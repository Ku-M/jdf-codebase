package fe.cmn.widget;

import java.util.List;

import fe.cmn.event.EventSubscriberDto;
import fe.cmn.pojo.annotation.PojoMeta;
import fe.cmn.widget.decoration.DecorationDto;
import fe.cmn.widget.decoration.IconStyleDto;
import flutter.coder.annt.NullSafe;

/**
 * 图标
 * 
 * <p>装饰类为IconDecorationDto
 *
 */
@PojoMeta(label = "图标", icon="res://images/units/icon.png")
public class IconDto extends WidgetDto {

	static final long serialVersionUID = 5709553652866639481L;
	
	@NullSafe
	// FeIcons、图片（服务器图片名、网络图片，只建议纯色图片进行转化）
	String src;
	
	// 图标装饰
	IconStyleDto iconDecoration;
	
	public IconDto() {
		this.expandMeInBox = false;
	}
	
	public IconDto(String src) {
		this.src = src;
		this.expandMeInBox = false;
	}
	
	public IconDto(String src, IconStyleDto iconDecoration) {
		this.src = src;
		this.iconDecoration = iconDecoration;
		this.expandMeInBox = false;
	}

	public String getSrc() {
		return src;
	}

	public IconDto setSrc(String src) {
		this.src = src;
		return this;
	}

	@Override
	public IconDto setWidgetId(String widgetId) {
		return (IconDto) super.setWidgetId(widgetId);
	}

	@Override
	public IconDto setDropListener(DropListener dropListener) {
		return (IconDto) super.setDropListener(dropListener);
	}

	@Override
	public IconDto setSubscribeEvents(List<EventSubscriberDto> subscribeEvents) {
		return (IconDto) super.setSubscribeEvents(subscribeEvents);
	}

	@Override
	public IconDto addSubscribeEvent(EventSubscriberDto subscriber) {
		return (IconDto) super.addSubscribeEvent(subscriber);
	}

	@Override
	public IconDto setPreferSize(SizeDto preferSize) {
		return (IconDto) super.setPreferSize(preferSize);
	}

	@Override
	public IconDto setMinSize(SizeDto minSize) {
		return (IconDto) super.setMinSize(minSize);
	}

	@Override
	public IconDto setMaxSize(SizeDto maxSize) {
		return (IconDto) super.setMaxSize(maxSize);
	}

	@Override
	public IconDto setExpandInBox(boolean expandInBox) {
		return (IconDto) super.setExpandInBox(expandInBox);
	}

	@Override
	public IconDto setVisible(boolean visible) {
		return (IconDto) super.setVisible(visible);
	}

	@Override
	public IconDto setDraggable(DraggableDto draggableData) {
		return (IconDto) super.setDraggable(draggableData);
	}

	@Override
	public IconDto setDecoration(DecorationDto decoration) {
		return (IconDto) super.setDecoration(decoration);
	}
}
