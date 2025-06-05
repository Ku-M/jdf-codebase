package fe.cmn.editor.listener;

import fe.cmn.event.EventDto;
import fe.cmn.widget.ListenerDto;
import fe.cmn.widget.ListenerExecutorDto;
import flutter.coder.annt.DefaultGetter;

// Editor都有的值变化监听命令
// 由前端回传Editor的当前值
public class OnValueChanged extends ListenerDto {

	private static final long serialVersionUID = -2766745249497357019L;

	// 由前端回传当的值（简单类型会包裹在CsonValue里）
	Object value;
	
	// 前后值改变触发监听间隔时间
	Integer changeInterval;
	
	// 旧值
	Object oldValue;
	
	// 是否返回旧值
	@DefaultGetter("false")
	Boolean bingBackOldValue;

	public OnValueChanged() {
	}

	public OnValueChanged(Class service, String command, boolean synchronize) {
		super(service, command, synchronize);
	}
	
	public OnValueChanged(Class service, String command, boolean synchronize, Object data) {
		super(service, command, synchronize, data);
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Integer getChangeInterval() {
		return changeInterval;
	}

	public OnValueChanged setChangeInterval(Integer changeInterval) {
		this.changeInterval = changeInterval;
		return this;
	}

	public Object getOldValue() {
		return oldValue;
	}

	public Boolean getBingBackOldValue() {
		return bingBackOldValue;
	}

	public OnValueChanged setBingBackOldValue(Boolean bingBackOldValue) {
		this.bingBackOldValue = bingBackOldValue;
		return this;
	}

	@Override
	public OnValueChanged setExecutor(ListenerExecutorDto executor) {
		super.setExecutor(executor);
		return this;
	}

	@Override
	public OnValueChanged setServerExecutor(Class service, String command) {
		super.setServerExecutor(service, command);
		return this;
	}

	@Override
	public OnValueChanged setEventExecutor(EventDto event) {
		super.setEventExecutor(event);
		return this;
	}

	@Override
	public OnValueChanged setSynchronize(boolean synchronize) {
		super.setSynchronize(synchronize);
		return this;
	}

	@Override
	public OnValueChanged setData(Object data) {
		super.setData(data);
		return this;
	}

	@Override
	public OnValueChanged setSelfBinaryData() {
		super.setSelfBinaryData();
		return this;
	}
}
