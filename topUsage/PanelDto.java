package fe.cmn.panel;

import java.util.List;
import java.util.Map;

import com.leavay.common.util.ToolBasic;
import com.leavay.ms.tool.CmnUtil;

import fe.cmn.app.SessionStorageDto;
import fe.cmn.app.listener.OnSessionStorageValueChanged;
import fe.cmn.editor.decoration.EditorReadonlyStyleTheme;
import fe.cmn.editor.listener.OnKeyboard;
import fe.cmn.pojo.annotation.FieldDefine;
import fe.cmn.widget.WidgetDto;
import flutter.coder.annt.DefaultGetter;
import flutter.coder.annt.FlutterCode;
import flutter.coder.annt.NullSafe;

/**
 * Panel作为容器，是树上组织串接上下游的主要节点：tree、workspace、table、box等都隶属于Panel
 * Panel的数量不会太多，在flutter端默认都会创建一一对应的GlobalKey，用以跨Container访问
 *
 *
 * Panel具有对子成员的管理功能，不同的Panel提供不同的对子成员的访问、控制、监听等逻辑总管
 * 
 * PanelDto的maxSize及minSize的大小限制不包含topBar及bottomBar
 * 
 * 【重要】：子类必须调用父类的构造函数，以便初始化一个全局唯一的WidgetID
 */
// 自动化确保globalKey被使用时能自动赋值
@FlutterCode("String ensureGlobalKey() => Cson.isStringEmpty(panelGlobalKey)?panelGlobalKey=Cson.allocUuid():panelGlobalKey;bool get hasBar => topBar != null || bottomBar != null;")
public abstract class PanelDto extends LayoutDto
{
    private static final long serialVersionUID = 3552683370344404113L;
    
    // Panel都会申请一个GlobalKey，且必须分配为全局唯一UUID，以便唯一定位到Panel
    // 一般非特别必要，不要自行设定该值，容易造成全局冲突
    @FieldDefine(label = "面板全局标识", description = "Panel都会默认分配一个全局唯一UUID用以定位该面板，一般情况不要自行设定该值，容易造成全局冲突")
    @NullSafe(initCode = "Cson.allocUuid()") // 双保险，java端万一没分配，flutter端自行分配
    String panelGlobalKey;
    
    // 顶bar
    WidgetDto topBar;
    
    // 底bar
    WidgetDto bottomBar;
    
    // 设置了topBar，或者bottomBar，可以视为纵向盒子包裹了topBar + Panel实际内容 + bottomBar，此字段就是设置Panel实际内容的expandFlex
    @DefaultGetter("1")
    Integer contentExpandFlex;
    
    /**
     * 取值适配器（为空则默认Map值集合作为该panel的取值）
     * 把Panel看做单个Editor，对其进行总体取值、设值、显示值的适配器
     * 例如针对Table的Panel，可以制作多种取值适配器，有些以选中行为值，有些以全表数据为值，有些以某固定列选中行为值等等可扩展
     *
     * 默认则以Panel获取所有子控件的值组成Map
     */
    PanelValueAdapter valueAdapter;
    
    // 全局监视其它指定Panel下指定Widget的监听器触发消息（反向订阅监听）
    // 即：其它面板下特定组件触发监听事件，会被发送到此Panel下得以响应
    /**
     * 全局监视其它指定Panel下指定Widget的监听器触发消息（反向订阅监听）
     * 即：其它面板下特定组件触发监听事件，会被发送到此Panel下得以响应
     * 
     * 隐藏的Panel亦可接收，销毁的则会自动注销监听（使用者无需关系注销动作）
     */
    List<GlobalMonitorDto> globalMonitors;

    // 键盘事件监听
    @FieldDefine(label = "监听按键")
    OnKeyboard onKeyboard;
    
    @FieldDefine(isStyleField = true, label = "工具条水平对齐", description = "TopBar和BottomBar宽度未占满时的水平排列方式，默认居中")
    CrossAxisAlign barAlign;
    
    @FieldDefine(isStyleField = true, label = "文字自适应显示", description = "此面板下的文字是否自适应显示，仅在AppDto设置了定稿值时生效，默认值为AppDto.defaultFontSizeAutoFix")
    Boolean fontSizeAutoFix;
    
    @FieldDefine(label = "绑定缓存", description = "随面板生成及销毁，无法通过Rebuild等回调创建")
    SessionStorageDto sessionStorage;
    
    // editor只读状态样式主题
    // 优先级EditorDto > PanelDto > AppDto
    EditorReadonlyStyleTheme editorReadonlyStyleTheme;
    
    public PanelDto()
    {
        setPanelGlobalKey(CmnUtil.allocUUIDWithUnderline());
        setWidgetId(CmnUtil.allocUUIDWithUnderline()); // 默认也给分配一个，panel默认还是做值管理的，不分配则子控件无法访问无法传值
    }
    
    public PanelDto(String widgetId)
    {
        this();
        setWidgetId(widgetId);
    }
    
    public String getPanelGlobalKey()
    {
        return panelGlobalKey;
    }

    public PanelDto setPanelGlobalKey(String panelGlobalKey)
    {
        this.panelGlobalKey = panelGlobalKey;
        return this;
    }

    public WidgetDto getTopBar()
    {
        return topBar;
    }

    public PanelDto setTopBar(WidgetDto topBar)
    {
        this.topBar = topBar;
        return this;
    }

    public WidgetDto getBottomBar()
    {
        return bottomBar;
    }

    public PanelDto setBottomBar(WidgetDto bottomBar)
    {
        this.bottomBar = bottomBar;
        return this;
    }

    public PanelValueAdapter getValueAdapter()
    {
        return valueAdapter;
    }

    public PanelDto setValueAdapter(PanelValueAdapter valueAdapter)
    {
        this.valueAdapter = valueAdapter;
        return this;
    }

    public Integer getContentExpandFlex() {
		return contentExpandFlex;
	}

	public PanelDto setContentExpandFlex(Integer contentExpandFlex) {
		this.contentExpandFlex = contentExpandFlex;
		return this;
	}

	public List<GlobalMonitorDto> getGlobalMonitors()
    {
        return globalMonitors;
    }

    public PanelDto setGlobalMonitors(List<GlobalMonitorDto> globalMonitors)
    {
        this.globalMonitors = globalMonitors;
        return this;
    }
    
    public PanelDto addGlobalMonitors(GlobalMonitorDto monitor)
    {
        globalMonitors = ToolBasic.addToList(globalMonitors, monitor);
        return this;
    }

	public OnKeyboard getOnKeyboard() {
		return onKeyboard;
	}

	public PanelDto setOnKeyboard(OnKeyboard onKeyboard) {
		this.onKeyboard = onKeyboard;
		return this;
	}

	public CrossAxisAlign getBarAlign() {
		return barAlign;
	}

	public PanelDto setBarAlign(CrossAxisAlign barAlign) {
		this.barAlign = barAlign;
		return this;
	}

	public Boolean getFontSizeAutoFix() {
		return fontSizeAutoFix;
	}

	public PanelDto setFontSizeAutoFix(Boolean fontSizeAutoFix) {
		this.fontSizeAutoFix = fontSizeAutoFix;
		return this;
	}

	public SessionStorageDto getSessionStorage() {
		return sessionStorage;
	}
	
	public PanelDto setSessionStorage(Boolean open) {
		if(this.sessionStorage == null && open) {
			this.sessionStorage = new SessionStorageDto(ToolBasic.allockUUID());
		} else if(this.sessionStorage != null && !open) {
			this.sessionStorage = null;
		}
		return this;
	}
	
	public PanelDto setSessionStorage(OnSessionStorageValueChanged onValueChange) {
		if(this.sessionStorage == null) {
			this.sessionStorage = new SessionStorageDto(ToolBasic.allockUUID(), onValueChange);
		}else {	
			this.sessionStorage.setOnValueChange(onValueChange);
		}
		
		return this;
	}
	
	public PanelDto setSessionStorage(Map<String, Object> initData) {
		if(this.sessionStorage == null) {
			this.sessionStorage = new SessionStorageDto(ToolBasic.allockUUID()).setInitData(initData);
		}else {	
			this.sessionStorage.setInitData(initData);
		}
		
		return this;
	}
	
	public PanelDto setSessionStorage(Map<String, Object> initData, OnSessionStorageValueChanged onValueChange) {
		if(this.sessionStorage == null) {
			this.sessionStorage = new SessionStorageDto(ToolBasic.allockUUID(), onValueChange).setInitData(initData);
		}else {	
			this.sessionStorage.setOnValueChange(onValueChange).setInitData(initData);
		}
		
		return this;
	}
	
	public PanelDto setSessionStorage(SessionStorageDto sessionStorage) {
		this.sessionStorage = sessionStorage;
		return this;
	}
	
	public EditorReadonlyStyleTheme getEditorReadonlyStyleTheme() {
		return editorReadonlyStyleTheme;
	}

	public PanelDto setEditorReadonlyStyleTheme(EditorReadonlyStyleTheme editorReadonlyStyleTheme) {
		this.editorReadonlyStyleTheme = editorReadonlyStyleTheme;
		return this;
	}
}
