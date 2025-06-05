package fe.util.component.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.leavay.ms.cmn.DtoIntf;

import fe.cmn.widget.ButtonDto;
import fe.util.dto.ButtonPrivilege;

public abstract class CardViewDtoIntf<T> implements Serializable,DtoIntf{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3788887866724495304L;
	/**
	 * 数据唯一标识
	 */
	String key;
	/**
	 * 数据
	 */
	T data;
	/**
	 * 外部传入的按钮列表，按钮的响应处理逻辑由外部提供
	 */
	List<ButtonDto> buttons;
	/**
	 * 动作权限
	 */
	Map<String,ButtonPrivilege> buttonPrivileges;
	
	@Override
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public List<ButtonDto> getButtons() {
		return buttons;
	}
	public void setButtons(List<ButtonDto> buttons) {
		this.buttons = buttons;
	}
	public Map<String, ButtonPrivilege> getButtonPrivileges() {
		return buttonPrivileges;
	}
	public void setButtonPrivileges(Map<String, ButtonPrivilege> buttonPrivileges) {
		this.buttonPrivileges = buttonPrivileges;
	}
}
