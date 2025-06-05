package fe.util.component.dto;

import fe.cmn.data.FePojo;
import fe.util.OperateTransaction;

import java.io.Serializable;

/**
 * 界面交互传递附加数据的包装类
 *
 * @param <T>
 * @author chenxb
 */
public class FeDeliverData<T> extends FePojo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 9017324672735886686L;
	/**
	 * 调用的实现类，必须是实现PanelInterface,TreeInterface,TableInterface,
	 * GraphInterface,ListenerInterface,EventInterface
	 * 以上任一接口
	 */
	String invokeClass;
	/**
	 * 从指定 widgetId实例化的组件或面板上,获取组件 widgetParam。为空时则指向所在的 panel
	 */
	String widgetIdOfWidgetParam;
	T data;
	/**
	 * 用户操作事务标识
	 */
	String opTransId;
	
	String lastOpTransId;

	public FeDeliverData() {
	}

	@SuppressWarnings("rawtypes")
	public FeDeliverData(Class invokeClass) {
		this(invokeClass, null);
	}

	@SuppressWarnings("rawtypes")
	public FeDeliverData(Class invokeClass, T data) {
		this.invokeClass = invokeClass.getName();
		this.data = data;
		this.opTransId = OperateTransaction.getTransId();
		this.lastOpTransId = OperateTransaction.getLastTransId();
	}

	@SuppressWarnings("rawtypes")
	public FeDeliverData(Class invokeClass, String widgetIdOfWidgetParam, T data) {
		this.invokeClass = invokeClass.getName();
		this.widgetIdOfWidgetParam = widgetIdOfWidgetParam;
		this.data = data;
		this.opTransId = OperateTransaction.getTransId();
		this.lastOpTransId = OperateTransaction.getLastTransId();
	}

	public String getInvokeClass() {
		return invokeClass;
	}

	@SuppressWarnings("rawtypes")
	public FeDeliverData<T> setInvokeClass(Class invokeClass) {
		this.invokeClass = invokeClass.getName();
		return this;
	}

	public FeDeliverData<T> setInvokeClass(String invokeClass) {
		this.invokeClass = invokeClass;
		return this;
	}

	public T getData() {
		return data;
	}

	public FeDeliverData<T> setData(T data) {
		this.data = data;
		return this;
	}

	public String getWidgetIdOfWidgetParam() {
		return widgetIdOfWidgetParam;
	}

	public FeDeliverData<T> setWidgetIdOfWidgetParam(String widgetIdOfWidgetParam) {
		this.widgetIdOfWidgetParam = widgetIdOfWidgetParam;
		return this;
	}
	public String getOpTransId() {
		return opTransId;
	}

	public String getLastOpTransId() {
		return lastOpTransId;
	}
	public FeDeliverData<T> setlastOpTransId(String lastOpTransId) {
		this.lastOpTransId = lastOpTransId;
		return this;
	}
}
