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
import flutter.coder.annt.DefaultGetter;
import flutter.coder.annt.NullSafe;

/**
 * 分割视图
 */
@PojoMeta(label = "分割视图", icon="res://images/units/split-h.png")
public class SplitViewDto extends SpecialLayoutDto
{

    private static final long serialVersionUID = -6562844486306797414L;

    @FieldDefine(isStyleField = true, label="纵向方向")
    boolean vertical = false;

    @NullSafe
    @FieldDefine(visible=false) // 容器子对象不允许用editor来修改，容易引起DTO引用不统一从而数据不一致（Studio中要求所有DTO指向同一个内存对象）
    WidgetDto left = LabelDto.simple("");

    @NullSafe
    @FieldDefine(visible=false) // 容器子对象不允许用editor来修改，容易引起DTO引用不统一从而数据不一致（Studio中要求所有DTO指向同一个内存对象）
    WidgetDto right = LabelDto.simple("");
    
    @NullSafe
    @FieldDefine(label="分割比例", description = "0 < x < 1")
    double dividRatio = 0.3;
    
    @DefaultGetter("SplitViewChildShowType.both")
    SplitViewChildShowType splitViewChildShowType;
    
    // 左侧内容最小比例
    @FieldDefine(label="左侧内容最小比例", description = "0 < x < 1")
    Double leftMinimal;
    
    // 右侧内容最小比例
    @FieldDefine(label="右侧内容最小比例", description = "0 < x < 1")
    Double rightMinimal;
    
    // 左侧内容宽度，优先级高于dividRatio
    Double leftWidth;
    
    // 右侧内容宽度，优先级高于dividRatio
    Double rightWidth;
    
    // 左侧内容最小宽度，优先级高于leftMinimal
    Double leftMinimalWidth;
    
    // 右侧内容最小宽度，优先级高于rightMinimal
    Double rightMinimalWidth;
    
    // 当同时设置左侧内容宽度和右侧内容宽度时，是否以左侧为准，右侧铺满
    Boolean leftshallPrevail;
    
    public boolean isVertical()
    {
        return vertical;
    }

    public SplitViewDto setVertical(boolean vertical)
    {
        this.vertical = vertical;
        return this;
    }

    public WidgetDto getLeft()
    {
        return left;
    }

    public SplitViewDto setLeft(WidgetDto left)
    {
        this.left = left;
        return this;
    }

    public WidgetDto getRight()
    {
        return right;
    }

    public SplitViewDto setRight(WidgetDto right)
    {
        this.right = right;
        return this;
    }

    public double getDividRatio()
    {
        return dividRatio;
    }

    public SplitViewDto setDividRatio(double dividRatio)
    {
        this.dividRatio = dividRatio;
        return this;
    }

    public SplitViewChildShowType getSplitViewChildShowType() {
		return splitViewChildShowType;
	}

	public SplitViewDto setSplitViewChildShowType(SplitViewChildShowType splitViewChildShowType) {
		this.splitViewChildShowType = splitViewChildShowType;
		return this;
	}

	public Double getLeftMinimal() {
		return leftMinimal;
	}

	public SplitViewDto setLeftMinimal(Double leftMinimal) {
		this.leftMinimal = leftMinimal;
		return this;
	}

	public Double getRightMinimal() {
		return rightMinimal;
	}

	public SplitViewDto setRightMinimal(Double rightMinimal) {
		this.rightMinimal = rightMinimal;
		return this;
	}

	public Double getLeftWidth() {
		return leftWidth;
	}

	public SplitViewDto setLeftWidth(Double leftWidth) {
		this.leftWidth = leftWidth;
		return this;
	}

	public Double getRightWidth() {
		return rightWidth;
	}

	public SplitViewDto setRightWidth(Double rightWidth) {
		this.rightWidth = rightWidth;
		return this;
	}

	public Double getLeftMinimalWidth() {
		return leftMinimalWidth;
	}

	public SplitViewDto setLeftMinimalWidth(Double leftMinimalWidth) {
		this.leftMinimalWidth = leftMinimalWidth;
		return this;
	}

	public Double getRightMinimalWidth() {
		return rightMinimalWidth;
	}

	public SplitViewDto setRightMinimalWidth(Double rightMinimalWidth) {
		this.rightMinimalWidth = rightMinimalWidth;
		return this;
	}

	public Boolean getLeftshallPrevail() {
		return leftshallPrevail;
	}

	public SplitViewDto setLeftshallPrevail(Boolean leftshallPrevail) {
		this.leftshallPrevail = leftshallPrevail;
		return this;
	}

	public static SplitViewDto wrap(WidgetDto left, WidgetDto right)
    {
        return wrap(left, right, 0.3f);
    }
    
    public static SplitViewDto wrap(WidgetDto left, WidgetDto right, double dividRatio) {
        return new SplitViewDto().setLeft(left).setRight(right).setDividRatio(dividRatio);
    }

    @Override
    public SplitViewDto setWidgetId(String widgetId) {
        return (SplitViewDto)super.setWidgetId(widgetId);
    }

    @Override
    public SplitViewDto setDropListener(DropListener dropListener) {
        return (SplitViewDto)super.setDropListener(dropListener);
    }

    @Override
    public SplitViewDto setSubscribeEvents(List<EventSubscriberDto> subscribeEvents) {
        return (SplitViewDto)super.setSubscribeEvents(subscribeEvents);
    }

    @Override
    public SplitViewDto addSubscribeEvent(EventSubscriberDto subscriber) {
        return (SplitViewDto)super.addSubscribeEvent(subscriber);
    }

    @Override
    public SplitViewDto setPreferSize(SizeDto preferSize) {
        return (SplitViewDto)super.setPreferSize(preferSize);
    }

    @Override
    public SplitViewDto setMinSize(SizeDto minSize) {
        return (SplitViewDto)super.setMinSize(minSize);
    }

    @Override
    public SplitViewDto setMaxSize(SizeDto maxSize) {
        return (SplitViewDto)super.setMaxSize(maxSize);
    }

    @Override
    public SplitViewDto setExpandInBox(boolean expandInBox) {
        return (SplitViewDto)super.setExpandInBox(expandInBox);
    }

    @Override
    public SplitViewDto setVisible(boolean visible) {
        return (SplitViewDto)super.setVisible(visible);
    }

    @Override
    public SplitViewDto setDraggable(DraggableDto draggableData) {
        return (SplitViewDto)super.setDraggable(draggableData);
    }

    @Override
    public SplitViewDto setDecoration(DecorationDto decoration) {
        return (SplitViewDto)super.setDecoration(decoration);
    }
    
    public static SplitViewDto empty(boolean vertical)
    {
        SplitViewDto v = new SplitViewDto();
        v.setLeft(new EmptySlotDto());
        v.setRight(new EmptySlotDto());
        v.setVertical(vertical);
        return v;
    }
}
