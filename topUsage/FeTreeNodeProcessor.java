package fe.util.component.tree;

import fe.cmn.app.Context;
import fe.cmn.panel.PanelContext;
import fe.cmn.tree.*;
import fe.cmn.tree.listener.TreeDropListener;
import fe.cmn.widget.ListenerDto;
import fe.cmn.widget.WidgetDto;
import fe.util.component.param.WidgetParam;
import fe.util.intf.ServiceIntf;

import java.util.List;
import java.util.function.Consumer;

public interface FeTreeNodeProcessor {
	/**
	 * 是否匹配树节点类型，在实现类中根据节点类型决定是否使用该实现类处理响应节点
	 * @param nodeType
	 * @return
	 */
	boolean isMatch(String nodeType);
	/**
	 * 获取树面板的上下文参数
	 * @return
	 */
	Context getContext();

	void setContext(Context context);
	/**
	 * 获取树节点工厂类
	 * @return
	 */
	FeTreeNodeFactory getFactory();

	void setFactory(FeTreeNodeFactory factory);
	/**
	 * 是否叶子节点，声明改方法决定节点是否
	 * @return
	 */
	boolean isLeaf();

	void setTreePanel(TreeInterface service) throws Exception;

	void setServiceCell(Class<? extends ServiceIntf> serviceCell);
	/**
	 * 查询子节点列表
	 * @param querier
	 * @param context
	 * @return
	 * @throws Exception
	 */
	List<TreeNodeDto> queryTree(TreeNodeQuerier querier, TreeNodeQuerierContext context) throws Exception;
	/**
	 * 拖动节点到目标节点的响应方法，响应的是目标节点的处理类
	 * @param listener
	 * @param context
	 * @throws Exception
	 */
	void dropNode(TreeDropListener listener, PanelContext context) throws Exception;
//	/**
//	 * 拖拽树节点的响应方法，响应的是拖动节点的处理类，目前已弃用
//	 * @param listener
//	 * @param context
//	 * @throws Exception
//	 */
//	@Deprecated
//	void _dragNode(TreeDropListener listener, PanelContext context) throws Exception;
	/**
	 * 将树节点的DTO对象转换为界面的树节点类
	 * @param node
	 * @return
	 * @throws Exception
	 */
	TreeNodeDto convert2FeTreeNodeDto(Object node) throws Exception;
	/**
	 * 补充树节点的界面信息设置，包括是否叶子节点、节点图标、节点高亮监听、节点拖拽监听等
	 * @param node
	 * @param widgetParam
	 * @throws Exception
	 */
	void appendTreeNodeSetting(TreeNodeDto node, WidgetParam widgetParam) throws Exception;

	/**
	 * 获取树节点的悬浮工具栏
	 *
	 * @param node
	 * @return
	 * @throws Exception
	 */
	ButtonBarDto getButtonBar(TreeNodeDto node) throws Exception;

	void onClickNode(ListenerDto listener, PanelContext panelContext, WidgetDto source, TreeNodeDto feNode) throws Exception;

	/**
	 * 点击树节点工具栏按钮的响应方法
	 *
	 * @param listener
	 * @param panelContext
	 * @param source
	 * @param feNode
	 * @return 动作执行完成返回true, 取消则返回false
	 * @throws Exception
	 */
	void onClickButtonBar(ListenerDto listener, PanelContext panelContext, WidgetDto source, TreeNodeDto feNode, Consumer<Object> callback) throws Exception;

	/**
	 * 树节点右键操作反应的右键菜单
	 *
	 * @param feNode
	 * @param otherSelected
	 * @param context
	 * @return
	 * @throws Exception
	 */
	TreeMenuDto getContextMenu(TreeNodeDto feNode, List<TreeNodeDto> otherSelected, TreeNodeQuerierContext context) throws Exception;

	TreeNodeDto reloadNode(TreeNodeDto feNode, TreeNodeQuerierContext context) throws Exception;
	/**
	 * 树节点点击菜单时的响应方法
	 * @param listener
	 * @param panelContext
	 * @param source
	 * @param feNode
	 * @throws Exception
	 */
	void onClickMenuItem(ListenerDto listener, PanelContext panelContext, WidgetDto source, TreeNodeDto feNode) throws Exception;
}
