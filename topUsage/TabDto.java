package fe.cmn.panel;

import java.awt.Color;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import fe.cmn.data.CColor;
import fe.cmn.pojo.annotation.FieldDefine;
import fe.cmn.pojo.annotation.PojoMeta;
import fe.cmn.tab.ContentAreaStyleDto;
import fe.cmn.tab.TabAreaStyleDto;
import fe.cmn.tab.TabBarPosition;
import fe.cmn.tab.TabButtonDto;
import fe.cmn.tab.TabItemDto;
import fe.cmn.tab.TabItemSelectedStyleDto;
import fe.cmn.tab.TabItemStyleDto;
import fe.cmn.tab.TabMenuStyleDto;
import fe.cmn.tab.TabTheme;
import fe.cmn.tab.TabVerticalShrinkWrapType;
import fe.cmn.tab.listener.OnTabCloseDto;
import fe.cmn.tab.listener.OnTabSelectDto;
import fe.cmn.tab.listener.OnTabSelectedClick;
import fe.cmn.widget.SizeDto;
import fe.cmn.widget.WidgetDto;
import flutter.coder.annt.DefaultGetter;
import flutter.coder.annt.NullSafe;

/**
 * 标签页
 */
@PojoMeta(label="标签页", icon="res://images/units/tab.png")
public class TabDto extends SpecialLayoutDto {

	private static final long serialVersionUID = -2276966729400131582L;
	
	// 初始选择显示tab项
    @FieldDefine(visible=false) // 应该在布局器上图形化配置
	String initTabId;
	
	// tabs
    @FieldDefine(visible=false) // 容器子对象不允许用editor来修改，容易引起DTO引用不统一从而数据不一致（Studio中要求所有DTO指向同一个内存对象）
    @NullSafe
	List<TabItemDto> tabItems;
	
	// 附带按钮组
    @FieldDefine(visible=false) // 容器子对象不允许用editor来修改，容易引起DTO引用不统一从而数据不一致（Studio中要求所有DTO指向同一个内存对象）
    List<TabButtonDto> tabsAreaButtons;
    
    // 附带组件，跟随在tab组后面，在附带按钮组前面
    // 【注意widgetDto】 
    // 1.tabAreaStyle.equalHeights为EqualHeightsType.all，且TabBarPosition为top/bottom，必须设置宽度
    // 2.tabAreaStyle.equalHeights为EqualHeightsType.all，且TabBarPosition为left/right，必须设置高度
    // 3.其余情况必须设置宽高
    @FieldDefine(visible=false)
    List<WidgetDto> tabAreaAttachments;

    @FieldDefine(isStyleField = true, label="tab栏位置") // 默认为top
    TabBarPosition tabBarPosition;
    
    // 主题： none、classic、dark、mobile、minimalist
    @FieldDefine(isStyleField = true, label="主题")
    TabTheme theme = TabTheme.none;  
    
    // 整体颜色
    @FieldDefine(isStyleField = true, label="整体颜色")
    CColor colorSet;
    
    // 选中tab下边框颜色（minimalist主题生效）
    @FieldDefine(isStyleField = true, label="选中tab下边框颜色",description="主题为minimalist时生效")
	CColor accentColor;
        
    // 自定义
	// tab样式
    @FieldDefine(isStyleField = true, label="自定义tab样式",description="会覆盖主题中的样式")
    TabItemStyleDto tabItemStyle;
    
    // 选中tab样式
    @FieldDefine(isStyleField = true, label="选中tab样式",description="会覆盖主题中的样式")
    TabItemSelectedStyleDto tabItemSelectedStyle;
    
    // 分页器头部样式
    @FieldDefine(isStyleField = true, label="分页器头部样式",description="会覆盖主题中的样式")
    TabAreaStyleDto tabAreaStyle;
    
    // 内容样式
    @FieldDefine(isStyleField = true, label="tab内容样式",description="会覆盖主题中的样式")
    ContentAreaStyleDto contentAreaStyle;
    
    // tab关闭监听
    @FieldDefine(label="tab关闭监听")
    OnTabCloseDto onTabClose;
    
    // tab选择监听
    @FieldDefine(label="tab选择监听")
    OnTabSelectDto onTabSelect;
    
    // 点击已选中tab标签
    @FieldDefine(label="点击已选中tab标签")
    OnTabSelectedClick onTabSelectedClick;

    @FieldDefine(label="隐藏标签列表样式")
    TabMenuStyleDto tabMenuStyle;
    
    // 内部content决定整体高度，比较耗费性能，建议手动设置高度
    // 【注意！！】此字段在1.2.0-RC版本后由verticalShrinkWrapType替代
    @DefaultGetter("false")
    @Deprecated
    Boolean intrinsicHeight;
    
    @FieldDefine(isStyleField = true, label="高度收缩方式",description="仅在上下布局时生效，若无设置maxSize，则默认最大高度约束为3000")
    TabVerticalShrinkWrapType verticalShrinkWrapType;
    
    /**
     * 折叠项是否可搜索过滤（折叠菜单显示搜索框），匹配tab项的text字段。
     * 
     * <p>以下情况不显示：1.只有一个折叠项 2.所有折叠项text字段都为空。
     */
    @DefaultGetter("true")
    @FieldDefine(label="折叠项是否可搜索过滤", description = "匹配tab项的text字段。以下情况不显示：1.只有一个折叠项 2.所有折叠项text字段都为空。")
    Boolean foldingItemsFilterable;

	public TabDto() {
	}

	public TabDto(List<TabItemDto> tabItems) {
		this.tabItems = tabItems;
	}

	public TabDto(TabItemDto... tabItems) {
		this.tabItems = Arrays.asList(tabItems);
	}
	
	public static TabDto empty() {
		TabDto tab = new TabDto();
		
		List<TabItemDto> tabItems = new LinkedList<TabItemDto>();
		
		for(int i = 0; i < 2; i++) {
			String tabName = "tab" + Integer.toString(i + 1);
//			tabItems.add(new TabItemDto(tabName, new EmptySlotDto().setWidgetId(tabName)));
			BoxDto scrollColumn = new BoxDto().setScrollable(true).setWidgetId(tabName).setMinSize(new SizeDto().setHeight(200.0)).setCrossAxisAlignment(CrossAxisAlign.start);
			tabItems.add(new TabItemDto(tabName, BoxDto.hbar(scrollColumn)));
		}
		
		tab.setTabItems(tabItems).setTheme(TabTheme.underLine);
		
		return tab;
	}

	public String getInitTabId() {
		return initTabId;
	}

	public TabDto setInitTabId(String initTabId) {
		this.initTabId = initTabId;
		return this;
	}

	public List<TabItemDto> getTabItems() {
		return tabItems;
	}
	public TabDto setTabItems(List<TabItemDto> tabItems) {
		this.tabItems = tabItems;
		return this;
	}
	public OnTabCloseDto getOnTabClose() {
		return onTabClose;
	}
	public TabDto setOnTabClose(OnTabCloseDto onTabClose) {
		this.onTabClose = onTabClose;
		return this;
	}
	public OnTabSelectDto getOnTabSelection() {
		return onTabSelect;
	}
	public TabDto setOnTabSelect(OnTabSelectDto onTabSelect) {
		this.onTabSelect = onTabSelect;
		return this;
	}
	public OnTabSelectedClick getOnTabSelectedClick() {
		return onTabSelectedClick;
	}
	public TabDto setOnTabSelectedClick(OnTabSelectedClick onTabSelectedClick) {
		this.onTabSelectedClick = onTabSelectedClick;
		return this;
	}
	public List<TabButtonDto> getTabsAreaButtons() {
		return tabsAreaButtons;
	}
	public TabDto setTabsAreaButtons(List<TabButtonDto> tabsAreaButtons) {
		this.tabsAreaButtons = tabsAreaButtons;
		return this;
	}
	public TabTheme getTheme() {
		return theme;
	}
	public TabDto setTheme(TabTheme theme) {
		this.theme = theme;
		return this;
	}
	public CColor getColorSet() {
		return colorSet;
	}
	public TabDto setColorSet(CColor colorSet) {
		this.colorSet = colorSet;
		return this;
	}
	public TabDto setColorSet(Color colorSet) {
		this.colorSet = CColor.fromColor(colorSet);
		return this;
	}
	public CColor getAccentColor() {
		return accentColor;
	}
	public TabDto setAccentColor(CColor accentColor) {
		this.accentColor = accentColor;
		return this;
	}
	public TabDto setAccentColor(Color accentColor) {
		this.accentColor = CColor.fromColor(accentColor);
		return this;
	}
	public TabItemStyleDto getTabItemStyle() {
		return tabItemStyle;
	}
	public TabDto setTabItemStyle(TabItemStyleDto tabItemStyle) {
		this.tabItemStyle = tabItemStyle;
		return this;
	}
	public TabItemSelectedStyleDto getTabItemSelectedStyle() {
		return tabItemSelectedStyle;
	}
	public TabDto setTabItemSelectedStyle(TabItemSelectedStyleDto tabItemSelectedStyle) {
		this.tabItemSelectedStyle = tabItemSelectedStyle;
		return this;
	}
	public TabAreaStyleDto getTabAreaStyle() {
		return tabAreaStyle;
	}
	public TabDto setTabAreaStyle(TabAreaStyleDto tabAreaStyle) {
		this.tabAreaStyle = tabAreaStyle;
		return this;
	}
	public ContentAreaStyleDto getContentAreaStyle() {
		return contentAreaStyle;
	}
	public TabDto setContentAreaStyle(ContentAreaStyleDto contentAreaStyle) {
		this.contentAreaStyle = contentAreaStyle;
		return this;
	}

	public TabBarPosition getTabBarPosition() {
		return tabBarPosition;
	}

	public TabDto setTabBarPosition(TabBarPosition tabBarPosition) {
		this.tabBarPosition = tabBarPosition;
		return this;
	}

	public Boolean getIntrinsicHeight() {
		return intrinsicHeight;
	}

	public TabDto setIntrinsicHeight(Boolean intrinsicHeight) {
		this.intrinsicHeight = intrinsicHeight;
		return this;
	}

	public List<WidgetDto> getTabAreaAttachments() {
		return tabAreaAttachments;
	}

	public TabDto setTabAreaAttachments(List<WidgetDto> tabAreaAttachments) {
		this.tabAreaAttachments = tabAreaAttachments;
		return this;
	}

	public TabMenuStyleDto getTabMenuStyle() {
		return tabMenuStyle;
	}

	public TabDto setTabMenuStyle(TabMenuStyleDto tabMenuStyle) {
		this.tabMenuStyle = tabMenuStyle;
		return this;
	}

	public TabVerticalShrinkWrapType getVerticalShrinkWrapType() {
		return verticalShrinkWrapType;
	}

	public TabDto setVerticalShrinkWrapType(TabVerticalShrinkWrapType verticalShrinkWrapType) {
		this.verticalShrinkWrapType = verticalShrinkWrapType;
		return this;
	}

	public Boolean getFoldingItemsFilterable() {
		return foldingItemsFilterable;
	}

	public TabDto setFoldingItemsFilterable(Boolean foldingItemsFilterable) {
		this.foldingItemsFilterable = foldingItemsFilterable;
		return this;
	}
}
