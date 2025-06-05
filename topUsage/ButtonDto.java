package fe.cmn.widget;

import java.util.List;

import fe.cmn.event.EventSubscriberDto;
import fe.cmn.pojo.annotation.FieldDefine;
import fe.cmn.pojo.annotation.PojoMeta;
import fe.cmn.widget.decoration.ButtonDecorationDto;
import fe.cmn.widget.decoration.ButtonThemeType;
import fe.cmn.widget.decoration.DecorationDto;

/**
 * 按钮
 * 
 * <p>装饰类为ButtonDecorationDto
 */
@PojoMeta(label = "按钮", icon = "res://images/units/click_button.png")
public class ButtonDto extends BaseButtonDto
{
    
    private static final long serialVersionUID = 7213217098039410055L;
    
    @FieldDefine(label = "监听单击事件")
    ListenerDto onClick;
    
    String text;
    
    // 内部插槽
    WidgetDto slot;
    
    public ButtonDto()
    {
        setExpandInBox(false);
    }
    
    public ButtonDto(String text) {
        this.text = text;
        setExpandInBox(false);
    }
    
    public ButtonDto(String text, String icon) {
        this.text = text;
        this.icon = icon;
        setExpandInBox(false);
    }
    
    public ListenerDto getOnClick()
    {
        return onClick;
    }
    
    public ButtonDto setOnClick(ListenerDto onClick)
    {
        this.onClick = onClick;
        return this;
    }
    
    public String getText()
    {
        return text;
    }
    
    public ButtonDto setText(String text)
    {
        this.text = text;
        return this;
    }
    
    public WidgetDto getSlot() {
        return slot;
    }
    
    public ButtonDto setSlot(WidgetDto slot) {
        this.slot = slot;
        return this;
    }
    
    @Override
    public ButtonDto setIcon(String icon)
    {
        this.icon = icon;
        return this;
    }
    
    @Override
    public Integer getValue()
    {
        return clickCount;
    }
    
    @Override
    public void setValue(Integer v)
    {
        clickCount = v;
    }
    
    
    @Override
    public ButtonDto setWidgetId(String widgetId) {
        return (ButtonDto)super.setWidgetId(widgetId);
    }
    
    @Override
    public ButtonDto setDropListener(DropListener dropListener) {
        return (ButtonDto)super.setDropListener(dropListener);
    }
    
    @Override
    public ButtonDto setSubscribeEvents(List<EventSubscriberDto> subscribeEvents) {
        return (ButtonDto)super.setSubscribeEvents(subscribeEvents);
    }
    
    @Override
    public ButtonDto addSubscribeEvent(EventSubscriberDto subscriber) {
        return (ButtonDto)super.addSubscribeEvent(subscriber);
    }

    public ButtonDto setPreferSize(int size) {
        return setPreferSize(SizeDto.all(size, size));
    }
    
    @Override
    public ButtonDto setPreferSize(SizeDto preferSize) {
        return (ButtonDto)super.setPreferSize(preferSize);
    }
    
    @Override
    public ButtonDto setMinSize(SizeDto minSize) {
        return (ButtonDto)super.setMinSize(minSize);
    }
    
    @Override
    public ButtonDto setMaxSize(SizeDto maxSize) {
        return (ButtonDto)super.setMaxSize(maxSize);
    }
    
    @Override
    public ButtonDto setExpandInBox(boolean expandInBox) {
        return (ButtonDto)super.setExpandInBox(expandInBox);
    }
    
    @Override
    public ButtonDto setVisible(boolean visible) {
        return (ButtonDto)super.setVisible(visible);
    }
    
    @Override
    public ButtonDto setDraggable(DraggableDto draggableData) {
        return (ButtonDto)super.setDraggable(draggableData);
    }
    
    @Override
    public ButtonDto setDecoration(DecorationDto decoration) {
        return (ButtonDto)super.setDecoration(decoration);
    }
    
    @Override
    public ButtonDto setToolTip(ToolTipDto toolTip) {
        return (ButtonDto)super.setToolTip(toolTip);
    }
    
    @Override
    public ButtonDto setToolTip(String message) {
        return (ButtonDto)super.setToolTip(message);
    }
    
    public ButtonDto setCancelStyle() {
    	setDecoration(new ButtonDecorationDto().setTheme(ButtonThemeType.cancelStyle));
    	return this;
    }
    
    public ButtonDto setConfirmStyle() {
    	setDecoration(new ButtonDecorationDto().setTheme(ButtonThemeType.confirmStyle));
    	return this;
    }
}
