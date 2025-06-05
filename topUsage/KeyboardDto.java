package fe.cmn.data;

import flutter.coder.annt.FlutterCode;
/**
 * 
 * 键盘事件信息
 *
 */
@FlutterCode("\tKeyboardDto.build(this.type, bool? isAltPressed, bool? isControlPressed,\r\n"
		+ "      bool? isShiftPressed, bool? isMetaPressed, String? keyCode) {this.isAltPressed = isAltPressed;\r\n"
		+ "    this.isControlPressed = isControlPressed;\r\n"
		+ "    this.isShiftPressed = isShiftPressed;\r\n"
		+ "    this.isMetaPressed = isMetaPressed;\r\n"
		+ "    this.keyCode = keyCode;\r\n"
		+ "    this.result = result;\r\n"
		+ "    setObjectType(JAVA_TYPE);}")
public class KeyboardDto extends BasicKeyboardDto {

	private static final long serialVersionUID = 2070604709376638619L;
	
	// down/up
	KeyboardPressType type;
	
	/**
	 * 是否冒泡，默认冒泡。
	 * 
	 * 拦截冒泡推荐使用SKIP_REMAINING_HANDLERS，而非HANDLED。因为HANDLED会同时拦截文本框的输入，但SKIP_REMAINING_HANDLERS不会。
	 */
	KeyboardEventResult result;
	
	public KeyboardDto() {
		
	}
	
	@Override
	public KeyboardDto setIsAltPressed(Boolean isAltPressed) {
		this.isAltPressed = isAltPressed;
		return this;
	}
	
	@Override
	public KeyboardDto setIsControlPressed(Boolean isControlPressed) {
		this.isControlPressed = isControlPressed;
		return this;
	}
	
	@Override
	public KeyboardDto setIsShiftPressed(Boolean isShiftPressed) {
		this.isShiftPressed = isShiftPressed;
		return this;
	}
	
	@Override
	public KeyboardDto setIsMetaPressed(Boolean isMetaPressed) {
		this.isMetaPressed = isMetaPressed;
		return this;
	}
	
	@Override
	//例如：KeyCode.enter
	public KeyboardDto setKeyCode(String keyCode) {
		this.keyCode = keyCode;
		return this;
	}

	public KeyboardPressType getType() {
		return type;
	}

	public KeyboardDto setType(KeyboardPressType type) {
		this.type = type;
		return this;
	}
	
	public Boolean isKeyDownPress() {
		return type.equals(KeyboardPressType.down) ;
	}

	public KeyboardEventResult getResult() {
		return result;
	}

	public KeyboardDto setResult(KeyboardEventResult result) {
		this.result = result;
		return this;
	}
}
