package fe.cmn.editor.listener;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import fe.cmn.data.KeyboardDto;
import fe.cmn.event.EventDto;
import fe.cmn.widget.ListenerDto;
import fe.cmn.widget.ListenerExecutorDto;

public class OnKeyboard extends ListenerDto {

	private static final long serialVersionUID = -7063861313889760658L;
	
	// 前端回填当前触发的键盘监听组合，只允许读取
	// triggeredKeyCombination
	KeyboardDto keyboardDto;
	
	// 后端指定需要监听的键盘组合（若不指定则默认监听所有键盘事件，建议指定）
	// monitoredKeyCombination
	List<KeyboardDto> monitoredKeyCombinations;
	
	public OnKeyboard() {
		
	}
	
	public OnKeyboard(Class service, String command, boolean synchronize) {
		super(service, command, synchronize);
	}
	
	public OnKeyboard(Class service, String command, boolean synchronize, Object data) {
		super(service, command, synchronize, data);
	}
	
	public KeyboardDto getKeyboardDto() {
		return keyboardDto;
	}

	public List<KeyboardDto> getMonitoredKeyCombinations() {
		return monitoredKeyCombinations;
	}

	public OnKeyboard setMonitoredKeyCombinations(List<KeyboardDto> monitoredKeyCombinations) {
		this.monitoredKeyCombinations = monitoredKeyCombinations;
		return this;
	}
	
	public OnKeyboard setMonitoredKeyCombinations(KeyboardDto ...monitoredKeyCombinations) {
		this.monitoredKeyCombinations = Arrays.stream(monitoredKeyCombinations).collect(Collectors.toList());
		return this;
	}

	@Override
	public OnKeyboard setExecutor(ListenerExecutorDto executor) {
		super.setExecutor(executor);
		return this;
	}

	@Override
	public OnKeyboard setServerExecutor(Class service, String command) {
		super.setServerExecutor(service, command);
		return this;
	}

	@Override
	public OnKeyboard setEventExecutor(EventDto event) {
		super.setEventExecutor(event);
		return this;
	}

	@Override
	public OnKeyboard setSynchronize(boolean synchronize) {
		super.setSynchronize(synchronize);
		return this;
	}

	@Override
	public OnKeyboard setData(Object data) {
		super.setData(data);
		return this;
	}

	@Override
	public OnKeyboard setSelfBinaryData() {
		super.setSelfBinaryData();
		return this;
	}
}
