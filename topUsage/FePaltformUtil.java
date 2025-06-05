package fe.util;

import com.leavay.ms.tool.CmnUtil;

import fe.cmn.app.Context;
import fe.cmn.app.ability.QueryDeviceInfo;
import fe.cmn.data.DeviceInfoDto;
import fe.cmn.data.PlatformType;
import fe.cmn.panel.PanelContext;
import fe.cmn.panel.ability.QueryWebSocketConnectUrl;

public class FePaltformUtil {
	public final static String DeviceInfo = "deviceInfo";
	public final static String WebSocketUrl = "webSocketUrl";
	
	public static String getWebSocketUrl(PanelContext context) throws Exception {
		String wsUrl = null;
		try {
			wsUrl = (String) context.getCacheValue(WebSocketUrl);
		}catch (Exception e) {
		}
		if(wsUrl != null) {
			return wsUrl;
		}
		wsUrl = QueryWebSocketConnectUrl.query(context);
		try {
			context.putCacheValue(WebSocketUrl, wsUrl);
		}catch (Exception e) {
		}
		return wsUrl;
	}
	
	public static DeviceInfoDto getCacheDeviceInfo(Context context) throws Exception {
		Object value = null;
		try {
			value = context.getCacheValue(DeviceInfo);
		}catch (Exception e) {
		}
		if(value != null) {
			return (DeviceInfoDto) value;
		}
		DeviceInfoDto device = QueryDeviceInfo.query(context.getChannel());
		cacheDeviceInfo(context, device);
		return device;
	}
	
	public static void cacheDeviceInfo(Context context,DeviceInfoDto device) {
		try {
			context.putCacheValue(DeviceInfo, device);
		}catch (Exception e) {
		}
	}
	
	/**
	 * 判断是否运行在移动端
	 * @param device
	 * @return
	 */
	public static boolean isMobile(DeviceInfoDto device) {
		PlatformType type = device.getPlatformType();
		return type == PlatformType.Android || type == PlatformType.IOS;
	}
	
	public static boolean isMobile(Context context) throws Exception {
		DeviceInfoDto device = getCacheDeviceInfo(context);
		return isMobile(device);
	}
	
	public static boolean isWeb(Context context) throws Exception {
		DeviceInfoDto device = getCacheDeviceInfo(context);
		return CmnUtil.getBoolean(device.getIsWeb(),false);
	}
	/**
	 * 判断是否运行在微信小程序中
	 * @param device
	 * @return
	 */
	public static boolean isMiniProgram(DeviceInfoDto device) {
		//PC端浏览器标识，先用miniProgram 忽略大小写判定
		//Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36 MicroMessenger/7.0.20.1781(0x6700143B) NetType/WIFI MiniProgramEnv/Windows WindowsWechat/WMPF WindowsWechat(0x63090a13) XWEB/9129
		return device.getUserAgent() != null && (
				device.getUserAgent().toLowerCase().contains("miniprogram")
				);
	}
	
	public static boolean isMiniProgram(Context context) throws Exception {
		DeviceInfoDto device = getCacheDeviceInfo(context);
		return isMiniProgram(device);
	}
}
