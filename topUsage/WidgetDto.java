package fe.cmn.widget;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.leavay.ms.tool.CmnUtil;

import fe.cmn.data.TimerDto;
import fe.cmn.event.EventSubscriberDto;
import fe.cmn.pojo.annotation.FieldDefine;
import fe.cmn.script.WidgetController;
import fe.cmn.studio.DesignerSettingsDto;
import fe.cmn.style.StyleDto;
import fe.cmn.widget.decoration.DecorationDto;
import fe.cmn.widget.decoration.PositionedDto;
import fe.cmn.widget.listener.PointerEventListenerDto;
import flutter.coder.annt.DefaultGetter;
import flutter.coder.annt.FlutterCode;
import flutter.coder.annt.NullSafe;

@FlutterCode("bool get verticalShrinkWrap => preferSize == null && (maxSize?.height != null || minSize?.height !=null);\n@JsonKey()\nString? unitId;")
public class WidgetDto extends StyleDto<DecorationDto>
{
    private static final long serialVersionUID = 1619552545572813722L;

    // 该组件的唯一ID（尽量保持全局唯一，至少在父Panel下保持唯一），可以不填则无法获取界面值
    // 如果出现多重父panel取值时，重复ID会相互覆盖
    @FieldDefine(label = "组件ID", description = "组件ID可用于对该组件的访问、求值、监听等操作。\n组件ID在父Panel下必须保证唯一，当父子Panel间出现重复时，遵循顶层覆盖底层的规则")
    String widgetId;

    // 如果是来自模板库的组件，可以携带一个标识，以便保存回模板库
    @FieldDefine(label = "模板ID", description = "模板库标识，一般不手工指定")
    String templateKey;

    // 布局器中对应的组件类型(unitDto.name)
    // 布局器中可针对不同组件提供自定义操作按钮，通过此名字来识别对应的按钮层对象：StudioWidgetOverlayDto
    @FieldDefine(visible = false)
    String unitName;

    @FieldDefine(isStyleField = true, label = "偏好尺寸", description = "可单独指定宽、高")
    SizeDto preferSize;

    @FieldDefine(isStyleField = true, label = "最小尺寸", description = "可单独指定宽、高")
    SizeDto minSize;

    @FieldDefine(isStyleField = true, label = "最大尺寸", description = "可单独指定宽、高")
    SizeDto maxSize;

    @NullSafe(initCode = "true")
    @FieldDefine(label = "是否可见")
    boolean visible = true;

    /**
     * 在Box中（Row、Column）是否自动撑开（安全起见，默认true）
     * 
     * 当设置了Size约束（prefSize、maxSize），则此标志无效
     * 
     * 当没有设置Size约束，则以此标志决定是否自动撑开
     * 
     * 注：大部分控件（如：List、Text、Table、Tree）边界都不确定，如果又没有设置Size约束，则必须将此字段设置为true，否则会出现布局异常
     * 所以为了安全，默认都设置为自动撑开
     * 
     * 那么对于按钮、Label这类自身可确定尺寸的组件，可以在没有Size约束的情况下设置为false
     */
    @FieldDefine(label = "自动扩张", isStyleField = true, description = "主要在盒子容器内生效，是否自动撑满可用空间\n注：有些组件如文本编辑器，必须自动撑满或者明确设定尺寸，否则容易导致布局崩溃")
    @DefaultGetter("true")
    Boolean expandMeInBox = true; // 这里由于历史原因，无法改成Boolean会导致老的数据反序列化失败，于是这个字段在套用样式时很可能不能生效，所以不能设置为样式字段了

    @FieldDefine(isStyleField = true, label = "扩张权重", description = "主要在盒子容器内且expandInBox为true时生效，用于设置和兄弟组件的扩张比例")
    Integer expandFlex;
	
	// 个性化装饰（颜色、边框、边距、阴影等修饰）
    @FieldDefine(isStyleField = true, label = "装饰器", description = "设定边框、边距、颜色、字体等")
    DecorationDto decoration;
    public final static String FIELD_NAME_decoration = "decoration";

    /**
     * 可拖拽对象，如果为NULL表示该对象不可拖拽 所有Widget都可以被设定为拖拽，且自定义拖拽数据
     * 
     * 释放时会触发目标组件的拽入投放事件：dropListener
     */
    @FieldDefine(label = "拖拽对象", description = "定义了此对象，则可以被拖拽并携带此对象数据到目标地")
    DraggableDto draggable;

    /**
     * 监听拽入投放事件（通常需要与dropFilter配合使用） 如果为空则拒绝所有拽入动作
     */
    @FieldDefine(label = "拽入监听", description = "可以设定过滤规则来接收拖拽进入的动作")
    DropListener dropListener;

    /**
     * 此字段为具体widget外包裹一层事件处理，与按钮等自身的事件分开 动作：点击、双击、长按、右键
     */
    @FieldDefine(label = "手势监听", description = "对所有组件通用的，可以监听手势操作\n需注意：当设定了双击监听时，单击监听会被延迟触发，即响应变慢")
    GestureDetectorDto gestureDetector;
    
    /**
     * 指针事件监听，独立于gestureDetector，会逐层往上冒泡触发。
     */
    @FieldDefine(label = "指针事件监听", description = "独立于gestureDetector，会逐层往上冒泡触发。")
    PointerEventListenerDto pointerEventListener;

    // 消息订阅（可以订阅多种消息）
    @FieldDefine(label = "消息订阅器", description = "可根据命令字订阅消息转发调用到后端服务")
    List<EventSubscriberDto> subscribeEvents;

    // ToolTip
    @FieldDefine(label = "悬浮提示", description = "悬浮提示的样式设定在组件装饰器中统一设定，且支持全局统一样式设定")
    ToolTipDto toolTip;

    // 定时触发监听列表
    @FieldDefine(label = "定时器")
    List<TimerDto> timers;

    public final static String Field_ExtendListener = "extendListeners";

    @FieldDefine(label = "扩展触发器", description = "对外提供的触发功能，可通过二次开发进行扩展")
    Map<String, ExtListenerDto> extendListeners;

    @FieldDefine(label = "控制器", description = "为空则使用系统默认控制器，可自主开发扩展，控制器可提供若干接口方法")
    String controllerClass;

    // 首次渲染完成回调
    ListenerDto postFrameCallback;
    
    @FieldDefine(label = "遮罩层", description = "可调用PopMaskOfWidget.hide进行隐藏")
    OverlayDto mask;
    
    @FieldDefine(label = "是否开启遮罩层", description = "在无初始遮罩层的情况下，开启此字段后才可以使用PopMaskOfWidget；LazyPanel自动启用遮罩")
    @DefaultGetter("false")
    Boolean wrapMask;
    
    // 在布局器中的一些设置
    @FieldDefine(visible = false)
    DesignerSettingsDto designerSettings;
    
    @FieldDefine(label = "是否开启截图功能", description = "开启此字段后才可以使用GetScreenshotOfWidget；拖拽功能可自动启用截图功能")
    @DefaultGetter("false")
    Boolean enableScreenshot;
    
    @FieldDefine(label = "所属组别", description = "表示该组件或面板所属的类别，集合，可用于GetWidgetIdsFromGroup")
    String[] groupIds;

    public String getWidgetId()
    {
        return widgetId;
    }

    public WidgetDto setWidgetId(String widgetId)
    {
        this.widgetId = widgetId;
        return this;
    }

    public String getTemplateKey()
    {
        return templateKey;
    }

    public WidgetDto setTemplateKey(String templateKey)
    {
        this.templateKey = templateKey;
        return this;
    }

    public String getUnitName()
    {
        return unitName;
    }

    public WidgetDto setUnitName(String unitName)
    {
        this.unitName = unitName;
        return this;
    }

    public DropListener getDropListener()
    {
        return dropListener;
    }

    public WidgetDto setDropListener(DropListener dropListener)
    {
        this.dropListener = dropListener;
        return this;
    }

    public List<EventSubscriberDto> getSubscribeEvents()
    {
        return subscribeEvents;
    }

    public WidgetDto setSubscribeEvents(List<EventSubscriberDto> subscribeEvents)
    {
        this.subscribeEvents = subscribeEvents;
        return this;
    }

    public WidgetDto addSubscribeEvent(EventSubscriberDto subscriber)
    {
        subscribeEvents = subscribeEvents == null ? new LinkedList<EventSubscriberDto>() : subscribeEvents;
        subscribeEvents.add(subscriber);
        return this;
    }

    public boolean hasSizeConstraint()
    {
        if (preferSize != null && (preferSize.width != null || preferSize.height != null))
            return true;
        if (minSize != null && (minSize.width != null || minSize.height != null))
            return true;
        if (maxSize != null && (maxSize.width != null || maxSize.height != null))
            return true;
        
        return false;
    }
    
    public SizeDto getPreferSize()
    {
        return preferSize;
    }

    public WidgetDto setPreferSize(SizeDto preferSize)
    {
        this.preferSize = preferSize;
        return this;
    }

    public SizeDto getMinSize()
    {
        return minSize;
    }

    public WidgetDto setMinSize(SizeDto minSize)
    {
        this.minSize = minSize;
        return this;
    }

    public SizeDto getMaxSize()
    {
        return maxSize;
    }

    public WidgetDto setMaxSize(SizeDto maxSize)
    {
        this.maxSize = maxSize;
        return this;
    }

    public WidgetDto setMaxHeight(double height)
    {
        if (maxSize == null)
            maxSize = SizeDto.height(height);
        else
            maxSize.setHeight(height);
        return this;
    }

    public WidgetDto setMaxHeightByWindowSize(double height)
    {
        if (maxSize == null || !(maxSize instanceof WindowSizeDto))
            maxSize = WindowSizeDto.height(height);
        else
            maxSize.setHeight(height);
        return this;
    }

    public WidgetDto setMaxWidth(double width)
    {
        if (maxSize == null)
            maxSize = SizeDto.width(width);
        else
            maxSize.setWidth(width);
        return this;
    }

    public WidgetDto setMaxWidthByWindowSize(double width)
    {
        if (maxSize == null || !(maxSize instanceof WindowSizeDto))
            maxSize = WindowSizeDto.width(width);
        else
            maxSize.setWidth(width);
        return this;
    }

    public WidgetDto setPreferHeight(double height)
    {
        if (preferSize == null)
            preferSize = SizeDto.height(height);
        else
            preferSize.setHeight(height);
        return this;
    }

    public WidgetDto setPreferHeightByWindowSize(double height)
    {
        if (preferSize == null || !(preferSize instanceof WindowSizeDto))
            preferSize = WindowSizeDto.height(height);
        else
            preferSize.setHeight(height);
        return this;
    }

    public WidgetDto setPreferWidth(double width)
    {
        if (preferSize == null)
            preferSize = SizeDto.width(width);
        else
            preferSize.setWidth(width);
        return this;
    }

    public WidgetDto setPreferWidthByWindowSize(double width)
    {
        if (preferSize == null || !(preferSize instanceof WindowSizeDto))
            preferSize = WindowSizeDto.width(width);
        else
            preferSize.setWidth(width);
        return this;
    }

    public boolean isExpandInBox()
    {
        return CmnUtil.getBoolean(expandMeInBox, true);
    }

    public WidgetDto setExpandInBox(boolean expandInBox)
    {
        this.expandMeInBox = expandInBox;
        return this;
    }

    public int getExpandFlex()
    {
        return expandFlex;
    }

    public WidgetDto setExpandFlex(int expandFlex)
    {
        this.expandFlex = expandFlex;
        return this;
    }

    public boolean isVisible()
    {
        return visible;
    }

    public WidgetDto setVisible(boolean visible)
    {
        this.visible = visible;
        return this;
    }

    public DraggableDto getDraggable()
    {
        return draggable;
    }

    public WidgetDto setDraggable(DraggableDto draggableData)
    {
        this.draggable = draggableData;
        return this;
    }

    public DecorationDto getDecoration()
    {
        return decoration;
    }

    public WidgetDto setDecoration(DecorationDto decoration)
    {
        this.decoration = decoration;
        return this;
    }

    public WidgetDto setPadding(double padding)
    {
        if (decoration == null)
            decoration = new DecorationDto();
        decoration.setPadding(padding);
        return this;
    }

    public WidgetDto setPadding(InsetDto padding)
    {
        if (decoration == null)
            decoration = new DecorationDto();
        decoration.setPadding(padding);
        return this;
    }

    public WidgetDto setMargin(double margin)
    {
        if (decoration == null)
            decoration = new DecorationDto();
        decoration.setMargin(margin);
        return this;
    }

    public WidgetDto setMargin(InsetDto margin)
    {
        if (decoration == null)
            decoration = new DecorationDto();
        decoration.setMargin(margin);
        return this;
    }

    public GestureDetectorDto getGestureDetector()
    {
        return gestureDetector;
    }

    public WidgetDto setGestureDetector(GestureDetectorDto gestureDetector)
    {
        this.gestureDetector = gestureDetector;
        return this;
    }

    public PointerEventListenerDto getPointerEventListener() {
		return pointerEventListener;
	}

	public WidgetDto setPointerEventListener(PointerEventListenerDto pointerEventListener) {
		this.pointerEventListener = pointerEventListener;
		return this;
	}

	// 可以用+拼接多个样式
    public WidgetDto setStyleName(String styleName)
    {
        return (WidgetDto)super.setStyleName(styleName);
    }

    public ToolTipDto getToolTip()
    {
        return toolTip;
    }

    public WidgetDto setToolTip(ToolTipDto toolTip)
    {
        this.toolTip = toolTip;
        return this;
    }

    public WidgetDto setToolTip(String message)
    {
        this.toolTip = new ToolTipDto(message);
        return this;
    }

    public List<TimerDto> getTimers()
    {
        return timers;
    }

    public WidgetDto setTimers(List<TimerDto> timers)
    {
        this.timers = timers;
        return this;
    }

    public WidgetDto setTimers(TimerDto... timers)
    {
        this.timers = Arrays.stream(timers).collect(Collectors.toList());;
        return this;
    }

    public WidgetDto setBinaryData(Serializable binaryData) throws IOException
    {
        super.setBinaryData(binaryData);
        return this;
    }

    public Map<String, ExtListenerDto> getExtendListeners()
    {
        return extendListeners;
    }

    public WidgetDto setExtendListeners(Map<String, ExtListenerDto> extendListeners)
    {
        this.extendListeners = extendListeners;
        return this;
    }

    public WidgetDto addExtendListener(ExtListenerDto lsnr)
    {
        if (extendListeners == null)
            extendListeners = new HashMap<String, ExtListenerDto>();
        extendListeners.put(lsnr.getName(), lsnr);
        return this;
    }

    public String getControllerClass()
    {
        return controllerClass;
    }

    public WidgetDto setControllerClass(Class<? extends WidgetController> controllerClass)
    {
        this.controllerClass = controllerClass.getCanonicalName();
        return this;
    }

    public ListenerDto getPostFrameCallback()
    {
        return postFrameCallback;
    }

    public WidgetDto setPostFrameCallback(ListenerDto postFrameCallback)
    {
        this.postFrameCallback = postFrameCallback;
        return this;
    }

    public OverlayDto getMask() {
    	return mask;
    }

    public WidgetDto setMask(WidgetDto mask) {
    	if(this.mask == null) {
    		this.mask = new OverlayDto();
    	}
    	this.mask.setChild(mask);
    	return this;
    }
    
    public WidgetDto setMaskPosition(PositionedDto position) {
    	if(this.mask == null) {
    		this.mask = new OverlayDto();
    	}
    	this.mask.setPosition(position);
    	return this;
    }
    
    public WidgetDto setMask(OverlayDto mask) {
    	this.mask = mask;
    	return this;
    }

    public Boolean getWrapMask() {
    	return wrapMask;
    }

    public WidgetDto setWrapMask(Boolean wrapMask) {
    	this.wrapMask = wrapMask;
    	return this;
    }

	public DesignerSettingsDto getDesignerSettings() {
		return designerSettings;
	}

	public WidgetDto setDesignerSettings(DesignerSettingsDto designerSettings) {
		this.designerSettings = designerSettings;
		return this;
	}

	public Boolean getEnableScreenshot() {
		return enableScreenshot;
	}

	public WidgetDto setEnableScreenshot(Boolean enableScreenshot) {
		this.enableScreenshot = enableScreenshot;
		return this;
	}

	public String[] getGroupIds() {
		return groupIds;
	}

	public WidgetDto setGroupIds(String[] groupIds) {
		this.groupIds = groupIds;
		return this;
	}
}
