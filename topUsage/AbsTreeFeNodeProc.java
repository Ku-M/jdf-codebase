package fe.util.component.tree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.kwaidoo.ms.tool.CmnUtil;
import com.leavay.common.util.ToolUtilities;

import cmn.anotation.FieldDeclare;
import cmn.i18n.I18nIntf;
import fe.cmn.app.Context;
import fe.cmn.panel.PanelContext;
import fe.cmn.res.JDFICons;
import fe.cmn.tree.ButtonBarDto;
import fe.cmn.tree.ButtonBarItemDto;
import fe.cmn.tree.PreloadTreeNodeDto;
import fe.cmn.tree.TreeHighlightListener;
import fe.cmn.tree.TreeInterface;
import fe.cmn.tree.TreeMenuDto;
import fe.cmn.tree.TreeMenuItemDto;
import fe.cmn.tree.TreeNodeDto;
import fe.cmn.tree.TreeNodeQuerier;
import fe.cmn.tree.TreeNodeQuerierContext;
import fe.cmn.tree.listener.TreeDropListener;
import fe.cmn.widget.DraggableDto;
import fe.cmn.widget.GestureDetectorDto;
import fe.cmn.widget.ListenerDto;
import fe.cmn.widget.ServerListenerExecutorDto;
import fe.cmn.widget.WidgetDto;
import fe.cmn.widget.listener.OnClickListener;
import fe.util.FeListenerUtil;
import fe.util.component.AbsComponent;
import fe.util.component.Component;
import fe.util.component.TreeSearchBar;
import fe.util.component.ablity.TreeAblity;
import fe.util.component.dto.FeDeliverData;
import fe.util.component.dto.TreeNodeExtraInfo;
import fe.util.component.param.WidgetParam;
import fe.util.i18n.FeI18n;
import fe.util.intf.ComponentI18nIntf;
import fe.util.intf.ServiceIntf;

public abstract class AbsTreeFeNodeProc implements FeTreeNodeProcessor,TreeAblity,ComponentI18nIntf{

	@FieldDeclare(label = "创建节点", desc = "执行创建节点操作的命令")
	public final static String CMD_CREATE_NODE = "CMD_CREATE_NODE";

	@FieldDeclare(label = "更新节点", desc = "执行更新节点操作的命令")
	public final static String CMD_UPDATE_NODE = "CMD_UPDATE_NODE";

	@FieldDeclare(label = "删除节点", desc = "执行删除节点操作的命令")
	public final static String CMD_DELETE_NODE = "CMD_DELETE_NODE";

	@FieldDeclare(label = "点击节点", desc = "执行点击节点操作的命令")
	public final static String CMD_CLICK_NODE = "CMD_CLICK_NODE";

	@FieldDeclare(label = "刷新", desc = "执行刷新操作的命令")
	public final static String CMD_REFRESH = "CMD_REFRESH";

	@FieldDeclare(label = "设置搜索开始", desc = "设置搜索开始点的命令")
	public final static String CMD_SET_SEARCH_START = "CMD_SET_SEARCH_START";
	FeTreeNodeFactory factory;
	TreeInterface treePanel;
	Class<? extends ServiceIntf> serviceCell;
	Context context;

	public FeTreeNodeFactory getFactory() {
		return factory;
	}

	public void setFactory(FeTreeNodeFactory factory) {
		this.factory = factory;
	}
	
	@Override
	public I18nIntf getI18n(PanelContext context) throws Exception {
		AbsComponent treeCmp = (AbsComponent) getTreePanel();
		return treeCmp.getI18n(context);
	}
	
	@Override
	public String getI18nString(PanelContext context, String key, Object... params) throws Exception {
		AbsComponent treeCmp = (AbsComponent) getTreePanel();
		return treeCmp.getI18nString(context, key, params);
	}

	@Override
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public TreeInterface getTreePanel() {
		return treePanel;
	}

	@Override
	public void setTreePanel(TreeInterface treePanel) throws Exception {
		this.treePanel = treePanel;
	}

	public WidgetParam getTreePanelWidgetParam() {
		Component component = (Component) getTreePanel();
		return component.getWidgetParam();
	}

	public Class<? extends ServiceIntf> getServiceCell() {
		return serviceCell;
	}

	public void setServiceCell(Class<? extends ServiceIntf> serviceCell) {
		this.serviceCell = serviceCell;
	}

	public abstract boolean enableCreate(TreeNodeDto node) ;

	public abstract boolean enableUpdate(TreeNodeDto node) ;

	public abstract boolean enableDelete(TreeNodeDto node) ;

	public abstract String getIcon();

	@SuppressWarnings({ "rawtypes" })
	public DraggableDto getDraggableDto(TreeNodeDto node) throws Exception{
		TreeNodeExtraInfo extraInfo = (TreeNodeExtraInfo) node.getBinaryData();
		DraggableDto dto = new DraggableDto();
		dto.setType(extraInfo.getNodeType());
		dto.setData(node.getKey());
		return dto;
	}
	
	
	@Override
	public void dropNode(TreeDropListener listener, PanelContext context) throws Exception {
		// TODO Auto-generated method stub
		
	}

//	public Class<? extends TreeInterface> getTreeCell(){
//		return IGpfFeTree.class;
//	}

//	@Override
//	public void setTreeCell(Class<? extends TreeInterface> treeCell) {
//		this.treeCell = treeCell;
//	}

	@Override
	public TreeNodeDto reloadNode(TreeNodeDto feNode, TreeNodeQuerierContext context) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 调用接口查询树节点列表，并将DTO数据转换为界面的树节点对象
	 * @param querier
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public abstract List<TreeNodeDto> queryAndConvert2TreeNodeDto(TreeNodeQuerier querier, TreeNodeQuerierContext context) throws Exception;
	/**
	 * 构建树节点的点击操作事件
	 * @param node
	 * @param widgetParam
	 * @return
	 * @throws Exception
	 */
	public GestureDetectorDto buildGestureDetectorDto(TreeNodeDto node,WidgetParam widgetParam) throws Exception {
		GestureDetectorDto detectorDto = new GestureDetectorDto();
		detectorDto.setOnClick(newOnClickListener(node,CMD_CLICK_NODE));
		detectorDto.setOnDoubleClick(newOnClickListener(node,CMD_CLICK_NODE));
		return detectorDto;
	}

	public OnClickListener newOnClickListener(TreeNodeDto node,String cmd) throws IOException {
		OnClickListener lsnr = new OnClickListener<>(getServiceCell(), cmd, true);
		lsnr.setData(node.getKey());
		FeDeliverData data = new FeDeliverData<>(getTreePanel().getClass());
		lsnr.setBinaryData(data);
		return lsnr;
	}
//	@SuppressWarnings("rawtypes")
//	public abstract OnClickListener buildOnClickListener(TreeNodeDto node);
	/**
	 * 构建树节点的高亮监听事件
	 * @param node
	 * @param widgetParam
	 * @return
	 */
	public abstract TreeHighlightListener buildOnHighlightListener(TreeNodeDto node,WidgetParam widgetParam);

	@Override
	public List<TreeNodeDto> queryTree(TreeNodeQuerier querier, TreeNodeQuerierContext context) throws Exception {
		FeDeliverData<WidgetParam> data = (FeDeliverData<WidgetParam>) querier.getBinaryData();
		List<TreeNodeDto> treeNodeList = queryAndConvert2TreeNodeDto(querier,context);
		for(TreeNodeDto node : treeNodeList) {
			FeTreeNodeProcessor proc = getFactory().getTreeNodeProcessor(context,node,treePanel,serviceCell);
			proc.appendTreeNodeSetting(node,data.getData());
//			appendTreeNodeSetting(node);
		}
		onAfterQueryTree(querier, context, treeNodeList);
		return treeNodeList;
	}

	public void onAfterQueryTree(TreeNodeQuerier querier, TreeNodeQuerierContext context,List<TreeNodeDto> treeNodeList) {
		//TODO 在查询树后进行干预
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void appendTreeNodeSetting(TreeNodeDto node,WidgetParam widgetParam) throws Exception {
		TreeNodeExtraInfo extraInfo = (TreeNodeExtraInfo) node.getBinaryData();
		if(extraInfo != null) {
			if(getTreePanel() != null) {
				extraInfo.setInvokeClass(getTreePanel().getClass());
				node.setBinaryData(extraInfo);
			}else {
				System.out.println();
			}
		}
		node.setLeaf(isLeaf());
		node.setIcon(getIcon());
		node.setDraggable(getDraggableDto(node));
//		feNode.setDraggable(new DraggableDto("NODE"));
		GestureDetectorDto gestureDetector = buildGestureDetectorDto(node,widgetParam);
		node.setGestureDetector(gestureDetector);
//		if(gestureDetector == null) {
//			gestureDetector = new GestureDetectorDto();
//		}
//		OnClickListener clickLsnr = buildOnClickListener(node);
//		if(clickLsnr != null)
//			gestureDetector.setOnClick(clickLsnr);
		TreeHighlightListener onHighlight = buildOnHighlightListener(node,widgetParam);
			node.setOnHignlight(onHighlight);
		if(node instanceof PreloadTreeNodeDto) {
			List<TreeNodeDto> childrens = ((PreloadTreeNodeDto) node).getPreloadChildren();
			if(!CmnUtil.isCollectionEmpty(childrens)) {
				for(TreeNodeDto child : childrens) {
					FeTreeNodeProcessor proc = getFactory().getTreeNodeProcessor(context,child,treePanel,serviceCell);
					proc.appendTreeNodeSetting(child,widgetParam);
				}
			}
		}
		node.setButtonBar(getButtonBar(node));
	}
	@Override
	public void onClickNode(ListenerDto listener, PanelContext panelContext, WidgetDto source, TreeNodeDto feNode) throws Exception {

	}

	@SuppressWarnings("rawtypes")
	@Override
	public void onClickButtonBar(ListenerDto listener, PanelContext panelContext, WidgetDto source, TreeNodeDto feNode, Consumer<Object> callback) throws Exception {
		ServerListenerExecutorDto exe = (ServerListenerExecutorDto) listener.getExecutor();
		String cmd = exe.getCommand();
		if(CmnUtil.isStringEqual(CMD_CREATE_NODE, cmd)) {
			onClickCreateButton(listener, panelContext, source, feNode, callback);
		}else if(CmnUtil.isStringEqual(CMD_UPDATE_NODE, cmd)) {
			onClickUpdateButton(listener, panelContext, source, feNode, callback);
		}else if(CmnUtil.isStringEqual(CMD_DELETE_NODE, cmd)) {
			onClickDeleteButton(listener, panelContext, source, feNode, callback);
		}
	}

	/**
	 * 点击树节点工具栏新增按钮的响应方法
	 *
	 * @param listener
	 * @param panelContext
	 * @param source
	 * @param feNode
	 * @param callback
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void onClickCreateButton(ListenerDto listener, PanelContext panelContext, WidgetDto source, TreeNodeDto feNode, Consumer<Object> callback) throws Exception {
	}

	/**
	 * 点击树节点工具栏修改按钮的响应方法
	 *
	 * @param listener
	 * @param panelContext
	 * @param source
	 * @param feNode
	 * @param callback
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void onClickUpdateButton(ListenerDto listener, PanelContext panelContext, WidgetDto source, TreeNodeDto feNode, Consumer<Object> callback) throws Exception {
	}

	/**
	 * 点击树节点工具栏删除按钮的响应方法
	 *
	 * @param listener
	 * @param panelContext
	 * @param source
	 * @param feNode
	 * @param callback
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void onClickDeleteButton(ListenerDto listener, PanelContext panelContext, WidgetDto source, TreeNodeDto feNode, Consumer<Object> callback) throws Exception {
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ListenerDto newListener(TreeNodeDto node,String cmd,boolean synchronize) throws Exception {
		FeDeliverData data = new FeDeliverData(getTreePanel().getClass());
		ListenerDto lsnr = FeListenerUtil.newListener(getServiceCell(), cmd, true, data);
		lsnr.setData(node.getKey());
		return lsnr;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ButtonBarItemDto buildCreateButton(TreeNodeDto node) throws IOException {
		ButtonBarItemDto create = new ButtonBarItemDto();
//		Map<String,Object> data = GpfFeUtil.buildListenerData(this.getClass());
		FeDeliverData data = new FeDeliverData(getTreePanel().getClass());
		create.setIcon(JDFICons.plus).setOnClick(FeListenerUtil.OnButtonBarClick(getServiceCell(), CMD_CREATE_NODE, true, data));
		return create;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ButtonBarItemDto buildUpdateButton(TreeNodeDto node) throws IOException {
		ButtonBarItemDto update = new ButtonBarItemDto();
//		Map<String,Object> data = GpfFeUtil.buildListenerData(this.getClass());
		FeDeliverData data = new FeDeliverData(getTreePanel().getClass());
		update.setIcon(JDFICons.compile).setOnClick(FeListenerUtil.OnButtonBarClick(getServiceCell(), CMD_UPDATE_NODE, true, data));
		return update;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ButtonBarItemDto buildDeleteButton(TreeNodeDto node) throws IOException {
		ButtonBarItemDto delete = new ButtonBarItemDto();
//		Map<String,Object> data = GpfFeUtil.buildListenerData(this.getClass());
		FeDeliverData data = new FeDeliverData(getTreePanel().getClass());
		delete.setIcon(JDFICons.delete).setOnClick(FeListenerUtil.OnButtonBarClick(getServiceCell(), CMD_DELETE_NODE, true, data));
		return delete;
	}

	@Override
	public ButtonBarDto getButtonBar(TreeNodeDto node) throws Exception {
		ButtonBarDto buttonBar = new ButtonBarDto();
		buttonBar.setButtonBarItems(new ArrayList<>());
		if(enableCreate(node)) {
			ButtonBarItemDto create = buildCreateButton(node);
			buttonBar.getButtonBarItems().add(create);
		}
		if(enableUpdate(node)) {
			ButtonBarItemDto update = buildUpdateButton(node);
			buttonBar.getButtonBarItems().add(update);
		}
		if(enableDelete(node)) {
			ButtonBarItemDto delete = buildDeleteButton(node);
			buttonBar.getButtonBarItems().add(delete);
		}
		return buttonBar;
	}

//	@SuppressWarnings("rawtypes")
//	@Override
//	public Object onListener(ListenerDto listener, PanelContext panelContext, WidgetDto source) throws Exception {
//		if(listener instanceof OnButtonBarClick) {
//			String cmd = listener.getServiceCommand();
//			if(CmnUtil.isStringEqual(cmd, CMD_CREATE_NODE)) {
//				onClickCreateButton(listener, panelContext, source, ((OnButtonBarClick) listener).getNode());
//			}else if(CmnUtil.isStringEqual(cmd, CMD_UPDATE_NODE)) {
//				onClickUpdateButton(listener, panelContext, source, ((OnButtonBarClick) listener).getNode());
//			}else if(CmnUtil.isStringEqual(cmd, CMD_DELETE_NODE)) {
//				onClickDeleteButton(listener, panelContext, source, ((OnButtonBarClick) listener).getNode());
//			}
//		}
//		return null;
//	}

	@Override
	public TreeMenuDto getContextMenu(TreeNodeDto feNode, List<TreeNodeDto> otherSelected,TreeNodeQuerierContext context) throws Exception {
		TreeMenuItemDto refresh = new TreeMenuItemDto().setNode(feNode);
		refresh.setIcon(JDFICons.refresh).setLabel(getI18nString(context, FeI18n.Refresh));
		refresh.setOnClick(newListener(feNode, CMD_REFRESH, true));

		TreeMenuItemDto search = new TreeMenuItemDto().setNode(feNode);
		search.setIcon(JDFICons.search).setLabel(getI18nString(context, FeI18n.SET_SEARCH_START));
		search.setOnClick(newListener(feNode, CMD_SET_SEARCH_START, true));

		TreeMenuDto menu = new TreeMenuDto();
		menu.setMenuItems(ToolUtilities.newArrayList(refresh));
		return menu;
	}

	@Override
	public void onClickMenuItem(ListenerDto listener, PanelContext panelContext, WidgetDto source, TreeNodeDto feNode) throws Exception {
		String cmd = listener.getServiceCommand();
		if(CmnUtil.isStringEqual(CMD_SET_SEARCH_START, cmd)) {
			onSetSearchStart(listener, panelContext, source, feNode);
		}else if(CmnUtil.isStringEqual(CMD_REFRESH, cmd)) {
			onRefresh(listener, panelContext, source, feNode);
		}
	}

	public void onSetSearchStart(ListenerDto listener, PanelContext panelContext, WidgetDto source, TreeNodeDto feNode) throws Exception {
		TreeSearchBar.setSearchStartTreeNode(panelContext, feNode);
	}

	public void onRefresh(ListenerDto listener, PanelContext panelContext, WidgetDto source, TreeNodeDto feNode) throws Exception {
		refreshNode(panelContext, feNode, true, null);
	}
}
