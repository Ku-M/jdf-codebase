package fe.cmn.panel;

import java.util.List;

import fe.cmn.event.EventSubscriberDto;
import fe.cmn.pojo.annotation.FieldDefine;
import fe.cmn.pojo.annotation.PojoMeta;
import fe.cmn.studio.EmptySlotDto;
import fe.cmn.widget.DraggableDto;
import fe.cmn.widget.DropListener;
import fe.cmn.widget.SizeDto;
import fe.cmn.widget.ToolTipDto;
import fe.cmn.widget.WidgetDto;
import fe.cmn.widget.decoration.DecorationDto;

/**
 * 基本容器
 */
@PojoMeta(label="基本容器", icon="res://images/units/single_panel.png")
public class ContainerDto extends LayoutDto {

	private static final long serialVersionUID = 7592060486896789926L;
	
	// 布局器中内部组件为一体
	@FieldDefine(label = "与内部组件融合一体")
	boolean bindInsideWidget;
	
    @FieldDefine(visible=false) // 容器子对象不允许用editor来修改，容易引起DTO引用不统一从而数据不一致（Studio中要求所有DTO指向同一个内存对象）
	WidgetDto child;
    public final static String FIELD_CHILD = "child";
	
	public ContainerDto() {
		
	}
	
	public ContainerDto(String widgetId)
    {
        super();
        this.setWidgetId(widgetId);
    }
    
    public Boolean getBindInsideWidget() {
		return bindInsideWidget;
	}

	public ContainerDto setBindInsideWidget(Boolean bindInsideWidget) {
		this.bindInsideWidget = bindInsideWidget;
		return this;
	}

	public WidgetDto getChild()
    {
        return child;
    }

    public ContainerDto setChild(WidgetDto child)
    {
        this.child = child;
        return this;
    }
    
    public static ContainerDto empty()
    {
        return new ContainerDto().setChild(new EmptySlotDto());
    }
    
    public static ContainerDto wrap(WidgetDto wgt)
    {
        return new ContainerDto().setChild(wgt);
    }

    @Override
    public ContainerDto setConstructUuid(String constructUuid)
    {
        this.constructUuid = constructUuid;
        return this;
    }
    
    @Override
    public ContainerDto setWidgetId(String widgetId) {
        return (ContainerDto)super.setWidgetId(widgetId);
    }

    @Override
    public ContainerDto setDropListener(DropListener dropListener) {
        return (ContainerDto)super.setDropListener(dropListener);
    }

    @Override
    public ContainerDto setSubscribeEvents(List<EventSubscriberDto> subscribeEvents) {
        return (ContainerDto)super.setSubscribeEvents(subscribeEvents);
    }

    @Override
    public ContainerDto addSubscribeEvent(EventSubscriberDto subscriber) {
        return (ContainerDto)super.addSubscribeEvent(subscriber);
    }

    @Override
    public ContainerDto setPreferSize(SizeDto preferSize) {
        return (ContainerDto)super.setPreferSize(preferSize);
    }

    @Override
    public ContainerDto setMinSize(SizeDto minSize) {
        return (ContainerDto)super.setMinSize(minSize);
    }

    @Override
    public ContainerDto setMaxSize(SizeDto maxSize) {
        return (ContainerDto)super.setMaxSize(maxSize);
    }

    @Override
    public ContainerDto setExpandInBox(boolean expandInBox) {
        return (ContainerDto)super.setExpandInBox(expandInBox);
    }

    @Override
    public ContainerDto setVisible(boolean visible) {
        return (ContainerDto)super.setVisible(visible);
    }

    @Override
    public ContainerDto setDraggable(DraggableDto draggableData) {
        return (ContainerDto)super.setDraggable(draggableData);
    }

    @Override
    public ContainerDto setDecoration(DecorationDto decoration) {
        return (ContainerDto)super.setDecoration(decoration);
    }
    
    @Override
    public ContainerDto setToolTip(ToolTipDto toolTip) {
        return (ContainerDto)super.setToolTip(toolTip);
    }
    
    @Override
    public ContainerDto setToolTip(String message) {
        return (ContainerDto)super.setToolTip(message);
    }
}
