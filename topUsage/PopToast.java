package fe.cmn.app.ability;

import com.leavay.ms.tool.CmnUtil;

import fe.cmn.sys.SystemCallback;
import cell.nio.ws.IWsCallbackChannel;
import fe.cmn.widget.ToastDto;
import flutter.coder.annt.NullSafe;

/**
 * 
 * @author GZ
 * 
 * 弹出吐司显示提示信息（一般用于操作反馈）
 * 
 * icon点击会弹出弹窗显示详细信息（detailMessage,detailMessage为空时显示message）
 * 
 */

public class PopToast extends SystemCallback {

	private static final long serialVersionUID = -3417774746402223410L;
	
	@NullSafe
	ToastDto toast;
	
	public PopToast() {
		
	}
	
	public PopToast(ToastDto toast) {
		this.toast = toast;
	}
	
	public ToastDto getToast() {
		return toast;
	}

	public PopToast setToast(ToastDto toast) {
		this.toast = toast;
		return this;
	}

	public static void show(IWsCallbackChannel feChannel, ToastDto toast) throws Exception {
		PopToast callback = new PopToast(toast);
		callback.execute(feChannel);
	}
	
	public static void success(IWsCallbackChannel feChannel, String message) throws Exception {
		PopToast callback = new PopToast(ToastDto.sucess(message));
		callback.execute(feChannel);
	}
	
	public static void success(IWsCallbackChannel feChannel, String message, String detailMessage) throws Exception {
		PopToast callback = new PopToast(ToastDto.sucess(message, detailMessage));
		callback.execute(feChannel);
	}
	
	public static void error(IWsCallbackChannel feChannel, String message) throws Exception {
		PopToast callback = new PopToast(ToastDto.error(message));
		callback.execute(feChannel);
	}
	
	public static void error(IWsCallbackChannel feChannel, String message, String detailMessage) throws Exception {
		PopToast callback = new PopToast(ToastDto.error(message, detailMessage));
		callback.execute(feChannel);
	}
	
	public static void errorIgnore(IWsCallbackChannel feChannel, String message, String detailMessage){
	    try
	    {
	        error(feChannel, message, detailMessage);
	    }catch (Throwable err)
	    {
            err.printStackTrace();
            CmnUtil.err("Failed to post error message to GUI : %s\n%s", message, detailMessage);
	    }
    }
	
	public static void errorIgnore(IWsCallbackChannel feChannel, String message){
        try
        {
            error(feChannel, message);
        }catch (Throwable err)
        {
            err.printStackTrace();
            CmnUtil.err("Failed to post error message to GUI : %s", message);
        }
    }
	
	public static void warning(IWsCallbackChannel feChannel, String message) throws Exception {
		PopToast callback = new PopToast(ToastDto.warning(message));
		callback.execute(feChannel);
	}
	
	public static void warning(IWsCallbackChannel feChannel, String message, String detailMessage) throws Exception {
		PopToast callback = new PopToast(ToastDto.warning(message, detailMessage));
		callback.execute(feChannel);
	}
	
	public static void info(IWsCallbackChannel feChannel, String message) throws Exception {
		PopToast callback = new PopToast(ToastDto.info(message));
		callback.execute(feChannel);
	}
	
	public static void info(IWsCallbackChannel feChannel, String message, String detailMessage) throws Exception {
		PopToast callback = new PopToast(ToastDto.info(message, detailMessage));
		callback.execute(feChannel);
	}
}
