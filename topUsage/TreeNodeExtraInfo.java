package fe.util.component.dto;

import java.io.Serializable;

import fe.cmn.tree.TreeInterface;
import fe.util.OperateTransaction;
/**
 * 树节点的扩展信息，使用抽象树面板及树节点工厂时，树节点的BinaryData需要设置TreeNodeExtraInfo数据
 * @author chenxb
 *
 * @param <T>
 */
public class TreeNodeExtraInfo<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6128915684645744679L;
	/**
	 * 对应的树面板组件类
	 */
	String invokeClass;
	/**
	 * 树节点类型
	 */
	String nodeType;
	/**
	 * 树节点绑定的真实数据ID
	 */
	String realDataUuid;
	/**
	 * 树节点绑定的真实数据
	 */
	T data;
	
	/**
	 * 用户操作事务标识
	 */
	String opTransId;
	
	String lastOpTransId;
	
	public TreeNodeExtraInfo() {
		this.opTransId = OperateTransaction.getTransId();
		this.lastOpTransId = OperateTransaction.getLastTransId();
	}
	
	public String getInvokeClass() {
		return invokeClass;
	}
	
	public TreeNodeExtraInfo<T> setInvokeClass(Class<? extends TreeInterface> invokeClass) {
		this.invokeClass = invokeClass.getName();
		return this;
	}
	
	public String getNodeType() {
		return nodeType;
	}
	public TreeNodeExtraInfo<T> setNodeType(String nodeType) {
		this.nodeType = nodeType;
		return this;
	}
	public String getRealDataUuid() {
		return realDataUuid;
	}
	public TreeNodeExtraInfo<T> setRealDataUuid(String realDataUuid) {
		this.realDataUuid = realDataUuid;
		return this;
	}
	public T getData() {
		return data;
	}
	public TreeNodeExtraInfo<T> setData(T data) {
		this.data = data;
		return this;
	}
	public String getLastOpTransId() {
		return lastOpTransId;
	}
	public String getOpTransId() {
		return opTransId;
	}
}
