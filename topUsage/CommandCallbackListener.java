package fe.util.component.extlistener;

import fe.cmn.pojo.annotation.PojoMeta;
import fe.cmn.widget.ExtListenerDto;
import fe.cmn.widget.ListenerDto;
@PojoMeta(label = "自定义命令回调监听器", desc = "在指定命令后执行额外的操作")
public class CommandCallbackListener extends ExtListenerDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 522630571510936906L;
	/**
	 * 相应回调的组件和响应监听的组件是否在一个面板下
	 */
	boolean isSamePanel;
	/**
	 * 是否弹窗后底层界面的回调
	 */
	boolean isPopupCallback = false;

	public CommandCallbackListener() {
		super();
	}
	
	public CommandCallbackListener(String cmd,String label,String desc,ListenerDto lsnr,boolean inOnePanel) {
		setName(cmd);
		setLabel(label);
		setDesc(desc);
		setListener(lsnr);
		this.isSamePanel = inOnePanel;
	}
	
	public boolean isInOnePanel() {
		return isSamePanel;
	}
	public void setInOnePanel(boolean inOnePanel) {
		this.isSamePanel = inOnePanel;
	}
	public boolean isPopupCallback() {
		return isPopupCallback;
	}
	public void setPopupCallback(boolean isPopupCallback) {
		this.isPopupCallback = isPopupCallback;
	}
}
