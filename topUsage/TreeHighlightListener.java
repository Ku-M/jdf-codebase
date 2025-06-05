package fe.cmn.tree;

import fe.cmn.event.EventDto;
import fe.cmn.widget.EventListenerExecutorDto;
import fe.cmn.widget.ListenerDto;
import fe.cmn.widget.ListenerExecutorDto;
import fe.cmn.widget.ListenerInterface;
import flutter.coder.annt.DefaultGetter;

import java.io.IOException;
import java.io.Serializable;

/**
 * 
 */
public class TreeHighlightListener<T> extends ListenerDto<T> {

    private static final long serialVersionUID = -1481727172478264459L;
    
    /**
     * 是否返回树节点DTO。
     */
    @DefaultGetter("false")
    Boolean bringTreeNode;
    
    /**
     * 树节点key值（触发时由前端返回）。
     */
    String treeNodeKey;
    
    /**
     * 树节点DTO（若bringTreeNode为true，则触发时由前端返回）。
     */
    TreeNodeDto treeNode;

    public TreeHighlightListener() {
	}

	public TreeHighlightListener(EventDto eventDto, boolean synchronize) {
		super(new EventListenerExecutorDto(eventDto), synchronize);
	}

	public TreeHighlightListener(ListenerExecutorDto executor, boolean synchronize) {
		super(executor, synchronize);
	}
	
	public TreeHighlightListener(Class<? extends ListenerInterface> service, String command, boolean synchronize) {
		super(service, command, synchronize);
	}
	
	public TreeHighlightListener(Class<? extends ListenerInterface> service, String command, boolean synchronize, T data) {
		super(service, command, synchronize, data);
	}

	public Boolean getBringTreeNode() {
		return bringTreeNode;
	}

	public TreeHighlightListener<T> setBringTreeNode(Boolean bringTreeNode) {
		this.bringTreeNode = bringTreeNode;
		return this;
	}

	public String getTreeNodeKey() {
		return treeNodeKey;
	}

	public TreeNodeDto getTreeNode() {
		return treeNode;
	}

	@Override
	public TreeHighlightListener<T> setUserObject(Object userObject) {
		return (TreeHighlightListener<T>) super.setUserObject(userObject);
	}

	@Override
	public TreeHighlightListener<T> setBinaryData(Serializable binaryData) throws IOException {
		return (TreeHighlightListener<T>) super.setBinaryData(binaryData);
	}

	@Override
	public TreeHighlightListener<T> setExecutor(ListenerExecutorDto executor) {
		return (TreeHighlightListener<T>) super.setExecutor(executor);
	}

	@Override
	public TreeHighlightListener<T> setServerExecutor(Class service, String command) {
		return (TreeHighlightListener<T>) super.setServerExecutor(service, command);
	}

	@Override
	public TreeHighlightListener<T> setEventExecutor(EventDto event) {
		return (TreeHighlightListener<T>) super.setEventExecutor(event);
	}

	@Override
	public TreeHighlightListener<T> setSynchronize(boolean synchronize) {
		return (TreeHighlightListener<T>) super.setSynchronize(synchronize);
	}

	@Override
	public TreeHighlightListener<T> setData(T data) {
		return (TreeHighlightListener<T>) super.setData(data);
	}
}
