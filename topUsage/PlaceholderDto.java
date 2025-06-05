package fe.cmn.panel;

import java.util.List;

import fe.cmn.event.EventSubscriberDto;
import fe.cmn.pojo.annotation.PojoMeta;
import fe.cmn.widget.DraggableDto;
import fe.cmn.widget.DropListener;
import fe.cmn.widget.SizeDto;
import fe.cmn.widget.ToolTipDto;
import fe.cmn.widget.WidgetDto;
import fe.cmn.widget.decoration.DecorationDto;

/**
 * 占位符
 */
@PojoMeta(label = "占位符")
public class PlaceholderDto extends ContainerDto {

	private static final long serialVersionUID = -5407633015913150110L;
	
	public PlaceholderDto() {
		setExpandInBox(false);
	}
	
	@Override
	public PlaceholderDto setBindInsideWidget(Boolean bindInsideWidget) {
		super.setBindInsideWidget(bindInsideWidget);
		return this;
	}

	@Override
    public PlaceholderDto setChild(WidgetDto child)
    {
        this.child = child;
        return this;
    }
	
	@Override
    public PlaceholderDto setConstructUuid(String constructUuid)
    {
        this.constructUuid = constructUuid;
        return this;
    }
    
    @Override
    public PlaceholderDto setWidgetId(String widgetId) {
        return (PlaceholderDto)super.setWidgetId(widgetId);
    }

    @Override
    public PlaceholderDto setDropListener(DropListener dropListener) {
        return (PlaceholderDto)super.setDropListener(dropListener);
    }

    @Override
    public PlaceholderDto setSubscribeEvents(List<EventSubscriberDto> subscribeEvents) {
        return (PlaceholderDto)super.setSubscribeEvents(subscribeEvents);
    }

    @Override
    public PlaceholderDto addSubscribeEvent(EventSubscriberDto subscriber) {
        return (PlaceholderDto)super.addSubscribeEvent(subscriber);
    }

    @Override
    public PlaceholderDto setPreferSize(SizeDto preferSize) {
        return (PlaceholderDto)super.setPreferSize(preferSize);
    }

    @Override
    public PlaceholderDto setMinSize(SizeDto minSize) {
        return (PlaceholderDto)super.setMinSize(minSize);
    }

    @Override
    public PlaceholderDto setMaxSize(SizeDto maxSize) {
        return (PlaceholderDto)super.setMaxSize(maxSize);
    }

    @Override
    public PlaceholderDto setExpandInBox(boolean expandInBox) {
        return (PlaceholderDto)super.setExpandInBox(expandInBox);
    }

    @Override
    public PlaceholderDto setVisible(boolean visible) {
        return (PlaceholderDto)super.setVisible(visible);
    }

    @Override
    public PlaceholderDto setDraggable(DraggableDto draggableData) {
        return (PlaceholderDto)super.setDraggable(draggableData);
    }

    @Override
    public PlaceholderDto setDecoration(DecorationDto decoration) {
        return (PlaceholderDto)super.setDecoration(decoration);
    }
    
    @Override
    public PlaceholderDto setToolTip(ToolTipDto toolTip) {
        return (PlaceholderDto)super.setToolTip(toolTip);
    }
    
    @Override
    public PlaceholderDto setToolTip(String message) {
        return (PlaceholderDto)super.setToolTip(message);
    }
}
