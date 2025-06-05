package fe.cmn.widget.listener;

import fe.cmn.event.EventDto;
import fe.cmn.widget.ListenerDto;
import fe.cmn.widget.ListenerExecutorDto;
import fe.cmn.widget.ListenerInterface;
import fe.cmn.widget.OffsetDto;

// 点击事件监听器
public class OnClickListener<T> extends ListenerDto<T> {

	private static final long serialVersionUID = -582548139378108634L;
	
	// 当前触发事件时鼠标位置
	OffsetDto position;
	
	// 样式反馈，不支持EditorDto
	ListenerFeedbackDto feedback;
	
	public OnClickListener() {
		
	}
	
	public OnClickListener(Class service, String command, boolean synchronize) {
		super(service, command, synchronize);
	}
	
	public OnClickListener(Class service, String command, boolean synchronize,T data) {
		super(service, command, synchronize, data);
	}

	public OffsetDto getPosition() {
		return position;
	}

	public OnClickListener setPosition(OffsetDto position) {
		this.position = position;
		return this;
	}
	
	public ListenerFeedbackDto getFeedback() {
		return feedback;
	}

	public OnClickListener setFeedback(ListenerFeedbackDto feedback) {
		this.feedback = feedback;
		return this;
	}

	@Override
	public OnClickListener setExecutor(ListenerExecutorDto executor) {
		super.setExecutor(executor);
		return this;
	}

	@Override
	public OnClickListener setServerExecutor(Class service, String command) {
		super.setServerExecutor(service, command);
		return this;
	}

	@Override
	public OnClickListener setEventExecutor(EventDto event) {
		super.setEventExecutor(event);
		return this;
	}

	@Override
	public OnClickListener setSynchronize(boolean synchronize) {
		super.setSynchronize(synchronize);
		return this;
	}

	@Override
	public OnClickListener setData(T data) {
		super.setData(data);
		return this;
	}

	@Override
	public OnClickListener setSelfBinaryData() {
		super.setSelfBinaryData();
		return this;
	}
	
	public static OnClickListener buildServiceListener(Class<? extends ListenerInterface> service, String command) {
        return new OnClickListener().setServerExecutor(service, command);
    }

    public static OnClickListener buildEventListener(EventDto event) {
        return new OnClickListener().setEventExecutor(event);
    }
}
