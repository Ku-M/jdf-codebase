package fe.cmn.panel;

import java.util.LinkedList;
import java.util.List;

import com.kwaidoo.ms.tool.CmnUtil;
import com.leavay.common.util.ToolBasic;

import fe.cmn.event.EventSubscriberDto;
import fe.cmn.pojo.annotation.FieldDefine;
import fe.cmn.pojo.annotation.PojoMeta;
import fe.cmn.widget.DraggableDto;
import fe.cmn.widget.DropListener;
import fe.cmn.widget.GestureDetectorDto;
import fe.cmn.widget.LabelDto;
import fe.cmn.widget.ScrollPhysicsType;
import fe.cmn.widget.SizeDto;
import fe.cmn.widget.WidgetDto;
import fe.cmn.widget.decoration.DecorationDto;
import flutter.coder.annt.DefaultGetter;
import flutter.coder.annt.NullSafe;

/**
 * 盒子布局, 将子组件以横向或纵向方式排列并根据设定自动调整子组件的尺寸以及位置。
 * 
 * <p>Box作为横向、纵向的主要弹性布局器，在多重嵌套时容易导致布局错误。
 * 
 * <p>例如：在纵向布局时，其内部组件如果有大量弹性设定，如自动撑开、不确定高度等情况，就很容易在弹性尺寸上计算出错，这是这种布局的一个麻烦。
 * 
 * <p>经验：
 *  1、如果纵向弹性元素太多，系统可能无法决策高度，最简单的方法就是尽量多指定prefSize（只给出高度）
 *  2、如果纵向弹性计算失败，可以尝试用IntrinsicHeightDto包裹，使其子组件保持固有高度，不做无限度的扩张
 *  3、对于需要适时出现滚动条的，还可以用ScrollBoxDto包裹，并推荐给出固定高度
 */
@PojoMeta(label = "盒子布局", icon="res://images/units/bar-h.png")
public class BoxDto extends MultiChildLayoutDto
{
    private static final long serialVersionUID = 6692692055134168386L;

    @NullSafe
    @FieldDefine(visible=false) // 容器子对象不允许用editor来修改，容易引起DTO引用不统一从而数据不一致（Studio中要求所有DTO指向同一个内存对象）
    List<WidgetDto> children;

    @DefaultGetter("true")
    @FieldDefine(label="纵向布局", isStyleField = true)
    Boolean verticalLayout;

    @FieldDefine(isStyleField = true, label="主轴尺寸策略", description = "主轴是指主布局方向，如横向Box，主轴方向则为横向")
    BoxMainAxisSize mainAxisSize;
    
    // 主轴排列
    @FieldDefine(isStyleField = true, label="主轴布局", description = "主轴是指主布局方向，如横向Box，主轴方向则为横向")
    MainAxisAlign mainAxisAlignment;
    
    // 副轴排列
    @FieldDefine(isStyleField = true, label="正交轴布局", description = "正交轴指与主布局方向垂直，如横向Box，正交轴为纵向")
    CrossAxisAlign crossAxisAlignment;
    
    @DefaultGetter("false")
    @FieldDefine(isStyleField = true, label="是否可滚动", description = "此字段为true时，主轴尺寸策略默认为min，内部组件必须具有固定或约束宽度/高度")
    Boolean scrollable;
    
    /**
	 * 在scrollable为true，开启滚动时控制滚动行为，例如禁用滚动，滚动到边界反弹等。
	 */
	ScrollPhysicsType scrollPhysics;
	
	/*
	 * 在开启截图功能时（enabelScreenshot为true），是否截图子组件，开启滚动的情况下即截图整体滚动视图。
	 * 默认值跟随scrollable。
	 */
	@DefaultGetter("true")
	Boolean screenshotChildren;

    public boolean isVertical()
    {
        return CmnUtil.getBoolean(verticalLayout, true);
    }

    public BoxDto setVertical(boolean vertical)
    {
        this.verticalLayout = vertical;
        return this;
    }

    public BoxMainAxisSize getMainAxisSize()
    {
        return mainAxisSize;
    }

    public BoxDto setMainAxisSize(BoxMainAxisSize mainAxisSize)
    {
        this.mainAxisSize = mainAxisSize;
        return this;
    }

    public List<WidgetDto> getChildren()
    {
        return children;
    }
    
    public int getChildrenCount()
    {
        return ToolBasic.getObjectSize(children);
    }

    public BoxDto setChildren(List<WidgetDto> children)
    {
        this.children = children;
        return this;
    }

    public BoxDto addChild(WidgetDto child)
    {
        if (children == null)
            children = new LinkedList<WidgetDto>();

        children.add(child);
        return this;
    }
    
    public BoxDto addChildren(WidgetDto ... children)
    {
        for (WidgetDto dto : children)
            addChild(dto);
        
        return this;
    }
    
    public BoxDto addStub(int size)
    {
        if (isVertical())
            addChild(vStub(size));
        else
            addChild(hStub(size));

        return this;
    }

    public BoxDto addExpander()
    {
        addChild(expander());
        return this;
    }
    
    public static WidgetDto hStub(int size)
    {
        return new LabelDto().setMinSize(SizeDto.width(size)).setMaxWidth(size).setPreferWidth(size);
    }
    
    public static WidgetDto vStub(int size)
    {
        return new LabelDto().setMinSize(SizeDto.height(size)).setMaxHeight(size).setPreferHeight(size);
    }
    
    public static WidgetDto expander()
    {
        return new LabelDto().setExpandInBox(true);
    }
    
    public static BoxDto hbar(WidgetDto ... children)
    {
        BoxDto box = new BoxDto().setVertical(false);
        box.addChildren(children);
        return box;
    }
    
    public static BoxDto hbar(double height, WidgetDto ... children)
    {
        return (BoxDto) hbar(children).setPreferHeight(height).setExpandInBox(false);
    }
    
    public static BoxDto vbar(WidgetDto ... children)
    {
        BoxDto box = new BoxDto().setVertical(true);
        box.addChildren(children);
        return box;
    }

	public MainAxisAlign getMainAxisAlignment() {
		return mainAxisAlignment;
	}

	public BoxDto setMainAxisAlignment(MainAxisAlign mainAxisAlignment) {
		this.mainAxisAlignment = mainAxisAlignment;
		return this;
	}

	public CrossAxisAlign getCrossAxisAlignment() {
		return crossAxisAlignment;
	}

	public BoxDto setCrossAxisAlignment(CrossAxisAlign crossAxisAlignment) {
		this.crossAxisAlignment = crossAxisAlignment;
		return this;
	}

    public Boolean getScrollable() {
		return scrollable;
	}

	public BoxDto setScrollable(Boolean scrollable) {
		this.scrollable = scrollable;
		return this;
	}

	public ScrollPhysicsType getScrollPhysics() {
		return scrollPhysics;
	}

	public BoxDto setScrollPhysics(ScrollPhysicsType scrollPhysics) {
		this.scrollPhysics = scrollPhysics;
		return this;
	}

	public Boolean getScreenshotChildren() {
		return screenshotChildren;
	}

	public BoxDto setScreenshotChildren(Boolean screenshotChildren) {
		this.screenshotChildren = screenshotChildren;
		return this;
	}

	@Override
    public BoxDto setWidgetId(String widgetId) {
        return (BoxDto)super.setWidgetId(widgetId);
    }

    @Override
    public BoxDto setDropListener(DropListener dropListener) {
        return (BoxDto)super.setDropListener(dropListener);
    }

    @Override
    public BoxDto setSubscribeEvents(List<EventSubscriberDto> subscribeEvents) {
        return (BoxDto)super.setSubscribeEvents(subscribeEvents);
    }

    @Override
    public BoxDto addSubscribeEvent(EventSubscriberDto subscriber) {
        return (BoxDto)super.addSubscribeEvent(subscriber);
    }

    @Override
    public BoxDto setPreferSize(SizeDto preferSize) {
        return (BoxDto)super.setPreferSize(preferSize);
    }

    @Override
    public BoxDto setMinSize(SizeDto minSize) {
        return (BoxDto)super.setMinSize(minSize);
    }

    @Override
    public BoxDto setMaxSize(SizeDto maxSize) {
        return (BoxDto)super.setMaxSize(maxSize);
    }

    @Override
    public BoxDto setExpandInBox(boolean expandInBox) {
        return (BoxDto)super.setExpandInBox(expandInBox);
    }

    @Override
    public BoxDto setVisible(boolean visible) {
        return (BoxDto)super.setVisible(visible);
    }

    @Override
    public BoxDto setDraggable(DraggableDto draggableData) {
        return (BoxDto)super.setDraggable(draggableData);
    }

    @Override
    public BoxDto setDecoration(DecorationDto decoration) {
        return (BoxDto)super.setDecoration(decoration);
    }
    
    @Override
    public BoxDto setGestureDetector(GestureDetectorDto gestureDetector) {
        return (BoxDto)super.setGestureDetector(gestureDetector);
    }
}
