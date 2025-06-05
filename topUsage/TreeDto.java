package fe.cmn.tree;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.leavay.ms.tool.CmnUtil;

import fe.cmn.event.EventSubscriberDto;
import fe.cmn.panel.PanelDto;
import fe.cmn.panel.PanelValueAdapter;
import fe.cmn.pojo.annotation.FieldDefine;
import fe.cmn.pojo.annotation.PojoMeta;
import fe.cmn.tree.decoration.TreeDecorationDto;
import fe.cmn.tree.listener.OnSelectValueChanged;
import fe.cmn.widget.DraggableDto;
import fe.cmn.widget.DropListener;
import fe.cmn.widget.GestureDetectorDto;
import fe.cmn.widget.SizeDto;
import fe.cmn.widget.ToolTipDto;
import fe.cmn.widget.WidgetDto;
import fe.cmn.widget.decoration.DecorationDto;
import flutter.coder.annt.DefaultGetter;

/**
 * 树控件
 * 
 * JDF中树(TreeDto)是和TreeInterface(后端服务)配合使用的
 * TreeDto必须设置service指向一个后端服务, 该服务派生并实现TreeInterface接口, 以便提供树节点初始化,查询以及弹出菜单等服务
 * 
 * 后端如果要动态控制树以及树节点, 可通过各种回调(Ability)或TreeController工具, 具体可参考TreeController及其使用样例
 * 
 * 【注！】树的宽高由外部容器决定，例如弹框中放一个包含树的无限制宽高的容器，则这时候可能树会显示溢出而无滚动条显示
 * 树拖拽接收监听器建议使用TreeDropListener
 * 
 * 关于树控件TreeDto的一些样例代码:
        TreeDto tree = (TreeDto) new TreeDto().setService(IFeDemoService.class)
                .setShowCheckbox(true).setShowToolTip(true)
                .setWidgetId("Tree1").setPanelGlobalKey("Tree1")
                .setDropListener(new TreeDropListener(IFeDemoHomePage.class, "tree_drop"))
                .setCheckStrictly(true)
                .setBinaryData(123456)
                ;
        tree.setDecoration(new TreeDecorationDto(new IconStyleDto(Color.blue)));
        tree.setDraggable(new DraggableDto("TREE").setData("过分了，整颗树都来了^_^"));

           TreeDto tree2 = new TreeDto("Tree2").setService(IFeDemoTree.class).setShowCheckbox(false).setShowToolTip(true)
                .setDropListener((TreeDropListener) new TreeDropListener(IFeDemoHomePage.class, "tree_drop").setSynchronize(true)); // 测试动态服务接口（无需生成flutter端代码）
        tree2.setExpandOnSingleClick(false);
        tree2.setShowCheckbox(true);
        BoxDto topBar2 = new BoxDto().setVertical(false);
        tree2.setTopBar(topBar2);
        TreeDto tree = (TreeDto) new TreeDto("Test").setService(IStudyTree.class)
                .setPanelGlobalKey("Test")
                .setPostFrameCallback(new ListenerDto<>(IStudyTableService.class, "treePostFrame", true));
        tree.setDecoration(new TreeDecorationDto().setTreeButtonBarLayoutType(TreeButtonBarLayoutType.normalRight));
        TreeDto tree = (TreeDto) new TreeDto().setService(IFeDemoService.class)
                .setShowCheckbox(true).setShowToolTip(true)
                .setWidgetId("Tree1").setPanelGlobalKey("Tree1")
                .setDropListener(new TreeDropListener(IFeDemoHomePage.class, "tree_drop"))
                .setCheckStrictly(true)
                .setBinaryData(123456)
                ;
        tree.setDecoration(new TreeDecorationDto(new IconStyleDto(Color.blue)));
        tree.setDraggable(new DraggableDto("TREE").setData("过分了，整颗树都来了^_^"));
 */
@PojoMeta(label = "树", icon="res://images/units/tree.png")
public class TreeDto extends PanelDto {
	private static final long serialVersionUID = -4712995940313203321L;

	String service;

	TreeNodeQuerier querier;

	OnSelectValueChanged onSelectValueChanged;

    @FieldDefine(label = "显示选择框")
	boolean showCheckbox = false;

    @FieldDefine(label = "显示工具提示")
	boolean showToolTip = false;
	
	// 在显示复选框的情况下，是否严格的遵循父子不互相关联的做法,默认为false
	boolean checkStrictly = false;
	
	// 点击非叶子节点时是否展开子节点，false为点击展开图标进行展开
	@FieldDefine(label = "单击节点展开", isStyleField = true)
	@DefaultGetter(value = "true")
	@Deprecated
	Boolean expandOnClick;
	
	@FieldDefine(label = "单击节点勾选", isStyleField = true)
	@DefaultGetter(value = "false")
	@Deprecated
	Boolean checkOnClick;
	
	/**
	 * 除展开图标点击外，指定哪些节点手势会触发节点展开。
	 * 
	 * <p>默认节点单击触发。
	 * 
	 * <p>替代旧的expandOnClick字段。
	 */
//	@FieldDefine(label = "节点展开触发事件", description = "默认单击触发。")
//	@DefaultGetter("[TreeNodeGestureEventEnum.click]")
//	List<TreeNodeGestureEventEnum> treeNodeExpandEvents;
	
	/**
	 * 除勾选框点击勾选外，指定哪些节点手势会触发节点勾选。
	 * 
	 * <p>默认无节点手势会触发勾选。
	 * 
	 * <p>替代旧的checkOnClick字段。
	 */
//	@FieldDefine(label = "节点勾选触发事件", description = "默认节点手势不会触发勾选。")
//	List<TreeNodeGestureEventEnum> treeNodeCheckEvents;
	
	@FieldDefine(label = "高亮触发事件", description = "默认单击、双击、长按触发")
	@DefaultGetter("[TreeHighlightEvent.click, TreeHighlightEvent.doubleClick, TreeHighlightEvent.longPress]")
	List<TreeHighlightEvent> highlightEvents;
	
	/**
	 * 树节点高亮事件监听。
	 */
	@FieldDefine(label = "树节点高亮事件监听")
	TreeHighlightListener onTreeNodeHighlight;

	/**
	 * 树节点手势事件监听。
	 */
	@FieldDefine(label = "树节点手势事件监听")
	TreeNodeGestureDetector treeNodeGestureDetector;
	
	@DefaultGetter("false")
	Boolean alwaysShowButtonBar;

	public TreeDto() {
		super();
	}

	public TreeDto(String panelKey) {
		super(panelKey);
	}

	public String getService() {
		return service;
	}

	public TreeDto setService(Class<? extends TreeInterface> serviceCls) {
		this.service = serviceCls.getName();
		return this;
	}

	public TreeNodeQuerier getQuerier() {
		return querier;
	}

	public TreeDto setQuerier(TreeNodeQuerier querier) {
		this.querier = querier;
		return this;
	}

	public boolean isExpandOnSingleClick() {
		return CmnUtil.getBoolean(expandOnClick, true);
	}

	public TreeDto setExpandOnSingleClick(boolean expandOnSingleClick) {
		this.expandOnClick = expandOnSingleClick;
		return this;
	}

	public Boolean getCheckOnClick() {
		return checkOnClick;
	}

	public TreeDto setCheckOnClick(Boolean checkOnClick) {
		this.checkOnClick = checkOnClick;
		return this;
	}

//	public List<TreeNodeGestureEventEnum> getTreeNodeExpandEvents() {
//		return treeNodeExpandEvents;
//	}
//
//	public TreeDto setTreeNodeExpandEvents(List<TreeNodeGestureEventEnum> treeNodeExpandEvents) {
//		this.treeNodeExpandEvents = treeNodeExpandEvents;
//		return this;
//	}
//
//	public List<TreeNodeGestureEventEnum> getTreeNodeCheckEvents() {
//		return treeNodeCheckEvents;
//	}
//
//	public TreeDto setTreeNodeCheckEvents(List<TreeNodeGestureEventEnum> treeNodeCheckEvents) {
//		this.treeNodeCheckEvents = treeNodeCheckEvents;
//		return this;
//	}

	public List<TreeHighlightEvent> getHighlightEvents() {
		return highlightEvents;
	}

	public TreeDto setHighlightEvents(List<TreeHighlightEvent> highlightEvents) {
		this.highlightEvents = highlightEvents;
		return this;
	}
	
	public TreeDto setHighlightEvents(TreeHighlightEvent... highlightEvents) {
		this.highlightEvents = Arrays.stream(highlightEvents).collect(Collectors.toList());
		return this;
	}
	
	public TreeHighlightListener getOnTreeNodeHighlight() {
		return onTreeNodeHighlight;
	}

	public TreeDto setOnTreeNodeHighlight(TreeHighlightListener onTreeNodeHighlight) {
		this.onTreeNodeHighlight = onTreeNodeHighlight;
		return this;
	}

	public TreeNodeGestureDetector getTreeNodeGestureDetector() {
		return treeNodeGestureDetector;
	}

	public TreeDto setTreeNodeGestureDetector(TreeNodeGestureDetector treeNodeGestureDetector) {
		this.treeNodeGestureDetector = treeNodeGestureDetector;
		return this;
	}

	@Override
	public TreeDto setPanelGlobalKey(String panelGlobalKey) {
		super.setPanelGlobalKey(panelGlobalKey);
		return this;
	}

	@Override
	public TreeDto setTopBar(WidgetDto topBar) {
		super.setTopBar(topBar);
		return this;
	}

	@Override
	public TreeDto setBottomBar(WidgetDto bottomBar) {
		super.setBottomBar(bottomBar);
		return this;
	}

	@Override
	public TreeDto setValueAdapter(PanelValueAdapter valueAdapter) {
		super.setValueAdapter(valueAdapter);
		return this;
	}

	@Override
	public TreeDto setWidgetId(String widgetId) {
		super.setWidgetId(widgetId);
		return this;
	}

	@Override
	public TreeDto setDropListener(DropListener dropListener) {
		super.setDropListener(dropListener);
		return this;
	}

	@Override
	public TreeDto setSubscribeEvents(List<EventSubscriberDto> subscribeEvents) {
		super.setSubscribeEvents(subscribeEvents);
		return this;
	}

	@Override
	public TreeDto addSubscribeEvent(EventSubscriberDto subscriber) {
		super.addSubscribeEvent(subscriber);
		return this;
	}

	@Override
	public TreeDto setPreferSize(SizeDto preferSize) {
		super.setPreferSize(preferSize);
		return this;
	}

	@Override
	public TreeDto setMinSize(SizeDto minSize) {
		super.setMinSize(minSize);
		return this;
	}

	@Override
	public TreeDto setMaxSize(SizeDto maxSize) {
		super.setMaxSize(maxSize);
		return this;
	}

	@Override
	public TreeDto setExpandInBox(boolean expandInBox) {
		super.setExpandInBox(expandInBox);
		return this;
	}

	@Override
	public TreeDto setVisible(boolean visible) {
		super.setVisible(visible);
		return this;
	}

	@Override
	public TreeDto setDraggable(DraggableDto draggableData) {
		super.setDraggable(draggableData);
		return this;
	}

	@Override
	public TreeDto setDecoration(DecorationDto decoration) {
		if(decoration instanceof TreeDecorationDto) {
			super.setDecoration(decoration);
		} else {
			throw new RuntimeException("DecorationDto should be TreeDecorationDto");
		}
//		super.setDecoration(decoration);
		return this;
	}

	@Override
	public TreeDto setGestureDetector(GestureDetectorDto gestureDetector) {
		super.setGestureDetector(gestureDetector);
		return this;
	}

	@Override
	public TreeDto setStyleName(String styleName) {
		super.setStyleName(styleName);
		return this;
	}

	@Override
	public TreeDto setToolTip(ToolTipDto toolTip) {
		super.setToolTip(toolTip);
		return this;
	}

	@Override
	public TreeDto setToolTip(String message) {
		super.setToolTip(message);
		return this;
	}

	public OnSelectValueChanged getOnSelectValueChanged() {
		return onSelectValueChanged;
	}

	public TreeDto setOnSelectValueChanged(OnSelectValueChanged onSelectValueChanged) {
		this.onSelectValueChanged = onSelectValueChanged;
		return this;
	}

	public Boolean getShowCheckbox() {
		return this.showCheckbox;
	}

	public TreeDto setShowCheckbox(Boolean showCheckbox) {
		this.showCheckbox = showCheckbox;
		return this;
	}

	public Boolean getShowToolTip() {
		return this.showToolTip;
	}

	public TreeDto setShowToolTip(Boolean showToolTip) {
		this.showToolTip = showToolTip;
		return this;
	}
	
	public Boolean getCheckStrictly() {
		return this.checkStrictly;
	}

	public TreeDto setCheckStrictly(Boolean checkStrictly) {
		this.checkStrictly = checkStrictly;
		return this;
	}

	public Boolean getAlwaysShowButtonBar() {
		return alwaysShowButtonBar;
	}

	public TreeDto setAlwaysShowButtonBar(Boolean alwaysShowButtonBar) {
		this.alwaysShowButtonBar = alwaysShowButtonBar;
		return this;
	}
}
