package fe.util.style;

import com.kwaidoo.ms.tool.CmnUtil;

import cell.nio.ws.IWsCallbackChannel;
import fe.util.FeCallbackPool;
import fe.util.SessionStorageUtil;

public class FeStyleSettingUtil {

	public static final String session_key_styleId = "styleId";
	public static final String session_stylesetting = "stylesetting";
//	public static final ConcurrentHashMap<String, FeStyleSetting> cache = new ConcurrentHashMap<>();

	/**
	 * 注册 feStyleSetting
	 *
	 * @param setting
	 * @throws Exception
	 */
	public static void registerFeStyleSetting(IWsCallbackChannel channel,FeStyleSetting setting) throws Exception {
		if (!CmnUtil.isStringEmpty(setting.getStyleId())) {
			channel.putCacheValue(session_key_styleId, setting.getStyleId());
			channel.putCacheValue(session_stylesetting, setting);
			FeCallbackPool.add(channel, SessionStorageUtil.buildWrite(session_key_styleId, setting.getStyleId()));
			FeCallbackPool.add(channel, SessionStorageUtil.buildWrite(session_stylesetting, setting));
//			SessionStorageUtil.write(channel.getChannel(),session_key_styleId, setting.getStyleId());
//			SessionStorageUtil.write(channel.getChannel(),session_stylesetting, setting);
//			cache.put(setting.getStyleId(), setting);
		}
	}

//	public static ThreadLocal<String> styleIdLocalCache = new ThreadLocal<>();
	
	/**
	 * 获取 feStyleSetting
	 *
	 * @return
	 * @throws Exception
	 */
	public static FeStyleSetting getFeStyleSetting(IWsCallbackChannel feChannel) throws Exception {
		//先从本地缓存获取styleId，如果没有才从前端缓存界面获取，避免频繁先前端发送请求交互
//		String styleId = styleIdLocalCache.get();
//		if(CmnUtil.isStringEmpty(styleId)) {
//			styleId = (String) feChannel.getCacheValue(session_key_styleId);
////			styleId = SessionStorageUtil.read(feChannel,session_key_styleId, String.class);
//			styleIdLocalCache.set(styleId);
//		}
//		if (CmnUtil.isStringEmpty(styleId)) {
//			System.out.println("未找到样式配置styleId");
//			FeStyleSetting setting =  new FeStyleSetting("common_style_v1");
//			registerFeStyleSetting(feChannel,setting);
//			return setting;
//		}else if(!cache.containsKey(styleId)) {
//			System.out.println("未找到样式配置缓存，重新加载样式FeStyleSetting");
////			FeStyleSetting setting = SessionStorageUtil.read(feChannel,session_stylesetting, FeStyleSetting.class);
//			FeStyleSetting setting = (FeStyleSetting) feChannel.getCacheValue(session_stylesetting);
//			if(setting == null) {
//				System.out.println("会话缓存中未找到样式配置缓存，重新初始化");
//				setting = new FeStyleSetting(styleId);
//			}
//			registerFeStyleSetting(feChannel,setting);
//			return setting;
//		}
//		return cache.get(styleId);
		
		String styleId = (String) feChannel.tryGetCacheValue(session_key_styleId);
		if(CmnUtil.isStringEmpty(styleId)) {
			styleId = SessionStorageUtil.read(feChannel,session_key_styleId, String.class);
			if(CmnUtil.isStringEmpty(styleId)) {
				styleId = "common_style_v1";
			}
			FeStyleSetting setting =  new FeStyleSetting(styleId);
			registerFeStyleSetting(feChannel,setting);
			return setting;
		}else {
			FeStyleSetting setting = (FeStyleSetting) feChannel.tryGetCacheValue(session_stylesetting);
			if(setting == null) {
				setting = SessionStorageUtil.read(feChannel,session_stylesetting, FeStyleSetting.class);
				if(setting == null) {
					System.out.println("会话缓存中未找到样式配置缓存，重新初始化");
					setting = new FeStyleSetting(styleId);
				}
				registerFeStyleSetting(feChannel,setting);
			}
			return setting;
		}
	}

}
