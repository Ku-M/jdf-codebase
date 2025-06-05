package fe.cmn.widget;

import cson.core.CsonPojo;
import fe.cmn.widget.listener.OnClickListener;
import fe.cmn.widget.listener.OnPanListener;
import fe.cmn.widget.listener.PointerListenerDto;

// 动作事件
public class GestureDetectorDto extends CsonPojo {

	private static final long serialVersionUID = 2157673854793580995L;

	// 点击（up）
	OnClickListener onClick;
	
	// 点击（down）
	OnClickListener onClickDown;

	// 双击（up）
	OnClickListener onDoubleClick;
	
	// 双击（down）
	OnClickListener onDoubleClickDown;
	
	// 右键点击（up）
	OnClickListener onSecondaryClick;
	
	// 右键点击（down）
	OnClickListener onSecondaryClickDown;

	// 长按（up）
	OnClickListener onLongPress;
	
	// 滑动结束监听
	OnPanListener onPanEnd;
	
	// 长按结束监听（同时是长按滑动结束监听）
	OnPanListener onLongPressUp;
	
	// 长按（down）
	OnClickListener onLongPressDown;
	
	// 移进
	PointerListenerDto onEnter;
	
	// 移出
	PointerListenerDto onExit;

	public GestureDetectorDto() {

	}

	public GestureDetectorDto(OnClickListener onClick) {
		this.onClick = onClick;
	}

	public static GestureDetectorDto buildOnClickGestureDetectorDto(Class service, String cmd, boolean synchronize) {
		return new GestureDetectorDto().setOnClick(new OnClickListener(service, cmd, synchronize));
	}

	public OnClickListener getOnClick() {
		return onClick;
	}

	public GestureDetectorDto setOnClick(OnClickListener onClick) {
		this.onClick = onClick;
		return this;
	}

	public OnClickListener getOnClickDown() {
		return onClickDown;
	}

	public GestureDetectorDto setOnClickDown(OnClickListener onClickDown) {
		this.onClickDown = onClickDown;
		return this;
	}

	public OnClickListener getOnDoubleClickDown() {
		return onDoubleClickDown;
	}

	public GestureDetectorDto setOnDoubleClickDown(OnClickListener onDoubleClickDown) {
		this.onDoubleClickDown = onDoubleClickDown;
		return this;
	}

	public OnClickListener getOnSecondaryClickDown() {
		return onSecondaryClickDown;
	}

	public GestureDetectorDto setOnSecondaryClickDown(OnClickListener onSecondaryClickDown) {
		this.onSecondaryClickDown = onSecondaryClickDown;
		return this;
	}

	public OnClickListener getOnDoubleClick() {
		return onDoubleClick;
	}

	public GestureDetectorDto setOnDoubleClick(OnClickListener onDoubleClick) {
		this.onDoubleClick = onDoubleClick;
		return this;
	}

	public OnClickListener getOnLongPress() {
		return onLongPress;
	}

	public GestureDetectorDto setOnLongPress(OnClickListener onLongPress) {
		this.onLongPress = onLongPress;
		return this;
	}

	public OnClickListener getOnSecondaryClick() {
		return onSecondaryClick;
	}

	public GestureDetectorDto setOnSecondaryClick(OnClickListener onSecondaryClick) {
		this.onSecondaryClick = onSecondaryClick;
		return this;
	}

	public OnPanListener getOnPanEnd() {
		return onPanEnd;
	}

	public GestureDetectorDto setOnPanEnd(OnPanListener onPanEnd) {
		this.onPanEnd = onPanEnd;
		return this;
	}

	public OnPanListener getOnLongPressUp() {
		return onLongPressUp;
	}

	public GestureDetectorDto setOnLongPressUp(OnPanListener onLongPressUp) {
		this.onLongPressUp = onLongPressUp;
		return this;
	}

	public OnClickListener getOnLongPressDown() {
		return onLongPressDown;
	}

	public GestureDetectorDto setOnLongPressDown(OnClickListener onLongPressDown) {
		this.onLongPressDown = onLongPressDown;
		return this;
	}

	public PointerListenerDto getOnEnter() {
		return onEnter;
	}

	public GestureDetectorDto setOnEnter(PointerListenerDto onEnter) {
		this.onEnter = onEnter;
		return this;
	}

	public PointerListenerDto getOnExit() {
		return onExit;
	}

	public GestureDetectorDto setOnExit(PointerListenerDto onExit) {
		this.onExit = onExit;
		return this;
	}
}
