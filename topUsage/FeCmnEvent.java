package fe.util.component.dto;

import fe.cmn.event.EventDto;
import fe.cmn.event.EventInterface;
import fe.util.OperateTransaction;

public class FeCmnEvent extends EventDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1750474000256610076L;
	String srcPanelGlobalKey;
	/**
	 * 调用的实现类，必须是实现PanelInterface,TreeInterface,TableInterface,
	 * GraphInterface,ListenerInterface,EventInterface
	 * 以上任一接口
	 */
	String invokeClass;
	Object[] invokeParams;
	/**
	 * 调用实现类在界面上实例化时的widgetId,用于获取组件 widgetParam。为空时则指向最近一层的 panel
	 */
	String widgetId;
	/**
	 * 用户操作事务标识
	 */
	String opTransId;
	
	String lastOpTransId;

	public FeCmnEvent() {
		this.opTransId = OperateTransaction.getTransId();
		this.lastOpTransId = OperateTransaction.getLastTransId();
	}
	
	public String getSrcPanelGlobalKey() {
		return srcPanelGlobalKey;
	}
	public FeCmnEvent setSrcPanelGlobalKey(String srcPanelGlobalKey) {
		this.srcPanelGlobalKey = srcPanelGlobalKey;
		return this;
	}
	
	public String getInvokeClass() {
		return invokeClass;
	}
	
	public FeCmnEvent setInvokeClass(Class<? extends EventInterface> lsnr) {
		this.invokeClass = lsnr.getName();
		return this;
	}

	public Object[] getInvokeParams() {
		return invokeParams;
	}

	public FeCmnEvent setInvokeParams(Object[] invokeParams) {
		this.invokeParams = invokeParams;
		return this;
	}

	public String getWidgetId() {
		return widgetId;
	}

	public FeCmnEvent setWidgetId(String widgetId) {
		this.widgetId = widgetId;
		return this;
	}
	
	public String getLastOpTransId() {
		return lastOpTransId;
	}
	public String getOpTransId() {
		return opTransId;
	}
}
