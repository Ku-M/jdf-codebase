package fe.cmn.panel;

import java.util.List;

import fe.cmn.event.EventSubscriberDto;
import fe.cmn.pojo.annotation.FieldDefine;
import fe.cmn.pojo.annotation.PojoMeta;
import fe.cmn.studio.EmptySlotDto;
import fe.cmn.widget.DraggableDto;
import fe.cmn.widget.DropListener;
import fe.cmn.widget.LabelDto;
import fe.cmn.widget.SizeDto;
import fe.cmn.widget.WidgetDto;
import fe.cmn.widget.decoration.DecorationDto;
import flutter.coder.annt.FlutterCode;
import flutter.coder.annt.NullSafe;

/**
 * 单面板
 */
@PojoMeta(label="单面板", icon="res://images/units/panel.png")
@FlutterCode("bool isEmptySlot() => content is EmptySlotDto;")
public class SinglePanelDto extends PanelDto
{

    private static final long serialVersionUID = -6935288520138721503L;

    // 主容器
    @NullSafe
    @FieldDefine(visible=false) // 容器子对象不允许用editor来修改，容易引起DTO引用不统一从而数据不一致（Studio中要求所有DTO指向同一个内存对象）
    WidgetDto content = new LabelDto();

    public SinglePanelDto()
    {
        super();
        setExpandInBox(false);
    }

    public SinglePanelDto(WidgetDto contentWidget)
    {
        this();

        setContent(contentWidget);
    }

    public WidgetDto getContent()
    {
        return content;
    }

    public SinglePanelDto setContent(WidgetDto content)
    {
        this.content = content;
        return this;
    }
    
    public static SinglePanelDto wrap(WidgetDto child) {
        return new SinglePanelDto(child);
    }

    public SinglePanelDto(String panelKey) {
        super(panelKey);
    }

    @Override
    public SinglePanelDto setWidgetId(String widgetId) {
        return (SinglePanelDto) super.setWidgetId(widgetId);
    }

    @Override
    public SinglePanelDto setDropListener(DropListener dropListener) {
        return (SinglePanelDto) super.setDropListener(dropListener);
    }

    @Override
    public SinglePanelDto setSubscribeEvents(List<EventSubscriberDto> subscribeEvents) {
        return (SinglePanelDto) super.setSubscribeEvents(subscribeEvents);
    }

    @Override
    public SinglePanelDto addSubscribeEvent(EventSubscriberDto subscriber) {
        return (SinglePanelDto) super.addSubscribeEvent(subscriber);
    }

    @Override
    public SinglePanelDto setPreferSize(SizeDto preferSize) {
        return (SinglePanelDto) super.setPreferSize(preferSize);
    }

    @Override
    public SinglePanelDto setMinSize(SizeDto minSize) {
        return (SinglePanelDto) super.setMinSize(minSize);
    }

    @Override
    public SinglePanelDto setMaxSize(SizeDto maxSize) {
        return (SinglePanelDto) super.setMaxSize(maxSize);
    }

    @Override
    public SinglePanelDto setExpandInBox(boolean expandInBox) {
        return (SinglePanelDto) super.setExpandInBox(expandInBox);
    }

    @Override
    public SinglePanelDto setVisible(boolean visible) {
        return (SinglePanelDto) super.setVisible(visible);
    }

    @Override
    public SinglePanelDto setDraggable(DraggableDto draggableData) {
        return (SinglePanelDto) super.setDraggable(draggableData);
    }

    @Override
    public SinglePanelDto setDecoration(DecorationDto decoration) {
        return (SinglePanelDto) super.setDecoration(decoration);
    }
    
    @Override
    public SinglePanelDto setPanelGlobalKey(String panelGlobalKey) {
    	super.setPanelGlobalKey(panelGlobalKey);
    	return this;
    }
    
    public static SinglePanelDto empty()
    {
        return SinglePanelDto.wrap(new EmptySlotDto());
    }
}
