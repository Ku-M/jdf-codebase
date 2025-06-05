package fe.cmn.widget;

import java.util.List;

import fe.cmn.event.EventSubscriberDto;
import fe.cmn.pojo.annotation.FieldDefine;
import fe.cmn.pojo.annotation.PojoMeta;
import fe.cmn.text.CTextStyle;
import fe.cmn.widget.decoration.DecorationDto;
import fe.cmn.widget.decoration.LabelDecorationDto;
import flutter.coder.annt.DefaultGetter;

/**
 * 文本
 * 
 * <p>装饰类为LabelDecorationDto
 */

@PojoMeta(label = "文本", icon="res://images/units/text.png", fields= {@FieldDefine(fieldName = "decoration", targetClass = LabelDecorationDto.class)})
public class LabelDto extends WidgetDto
{
    private static final long serialVersionUID = 5472247890965396570L;

    @FieldDefine(label="标签文本")
    String text;
    
    /**
	 * 是否可选中，默认不可选中。
	 * 
	 * 开启选中需注意，点击文本将只会触发选中。
	 */
    @DefaultGetter("false")
    @FieldDefine(label="文本是否可选中", description = "默认不可选中")
    Boolean selectable;
    
    /**
     * 菜单禁用，默认不禁用。
     */
    @DefaultGetter("false")
    @FieldDefine(label="菜单禁用", description = "默认不可选中")
    Boolean disableContextMenu;
    
    public LabelDto()
    {
        setExpandInBox(false);
    }
    
    public LabelDto(String text)
    {
        this();
        setText(text);
    }

    public LabelDto(String text, CTextStyle textStyle) {
        this();
        setText(text);
        setDecoration(new DecorationDto().setTextStyle(textStyle));
    }
    
    public LabelDto(String text, CLabelAlign textAlign) {
        this();
        setText(text);
        setDecoration(new LabelDecorationDto().setAlign(textAlign));
    }
    
    public LabelDto(String text, CTextStyle textStyle, CLabelAlign textAlign) {
        this();
        setText(text);
        setDecoration(new LabelDecorationDto().setAlign(textAlign).setTextStyle(textStyle));
    }
    
    public static LabelDto simple(String text)
    {
        return new LabelDto(text);
    }

    public String getText()
    {
        return text;
    }

    public LabelDto setText(String text)
    {
        this.text = text;
        return this;
    }

    public Boolean getSelectable() {
		return selectable;
	}

	public LabelDto setSelectable(Boolean selectable) {
		this.selectable = selectable;
		return this;
	}

	public Boolean getDisableContextMenu() {
		return disableContextMenu;
	}

	public LabelDto setDisableContextMenu(Boolean disableContextMenu) {
		this.disableContextMenu = disableContextMenu;
		return this;
	}

	@Override
    public LabelDto setWidgetId(String widgetId) {
        return (LabelDto) super.setWidgetId(widgetId);
    }

    @Override
    public LabelDto setDropListener(DropListener dropListener) {
        return (LabelDto)super.setDropListener(dropListener);
    }

    @Override
    public LabelDto setSubscribeEvents(List<EventSubscriberDto> subscribeEvents) {
        return (LabelDto)super.setSubscribeEvents(subscribeEvents);
    }

    @Override
    public LabelDto addSubscribeEvent(EventSubscriberDto subscriber) {
        return (LabelDto)super.addSubscribeEvent(subscriber);
    }

    @Override
    public LabelDto setPreferSize(SizeDto preferSize) {
        return (LabelDto)super.setPreferSize(preferSize);
    }

    @Override
    public LabelDto setMinSize(SizeDto minSize) {
        return (LabelDto)super.setMinSize(minSize);
    }

    @Override
    public LabelDto setMaxSize(SizeDto maxSize) {
        return (LabelDto)super.setMaxSize(maxSize);
    }

    @Override
    public LabelDto setExpandInBox(boolean expandInBox) {
        return (LabelDto)super.setExpandInBox(expandInBox);
    }

    @Override
    public LabelDto setVisible(boolean visible) {
        return (LabelDto)super.setVisible(visible);
    }

    @Override
    public LabelDto setDraggable(DraggableDto draggableData) {
        return (LabelDto)super.setDraggable(draggableData);
    }

    @Override
    public LabelDto setDecoration(DecorationDto decoration) {
        return (LabelDto)super.setDecoration(decoration);
    }
}
