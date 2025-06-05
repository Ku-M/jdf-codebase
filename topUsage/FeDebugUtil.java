package fe.util;

import com.kwaidoo.ms.tool.CmnUtil;

import fe.cmn.app.Context;

public class FeDebugUtil {

	public static boolean isEnableDebug(Context panelContext){
		boolean enableDebugLog = false;
		try{
			enableDebugLog = CmnUtil.getBoolean(panelContext.getCacheValue("$EnableDebug"), false);
		}catch (Exception e) {
			// TODO: handle exception
		}
		return enableDebugLog;
	}
	
	public static void setEnableDebug(Context panelContext,boolean enableDebug)throws Exception{
		if(!enableDebug)
			panelContext.removeCacheValue("$Trace");
		panelContext.putCacheValue("$EnableDebug", enableDebug);
	}
	
	public static boolean isSimulateFrontEnd(Context panelContext) throws Exception {
		boolean isSimulateForntEnd = false;
		try{
			isSimulateForntEnd = CmnUtil.getBoolean(panelContext.getCacheValue("$SimulateFrontEnd"), false);
		}catch (Exception e) {
		}
		return isSimulateForntEnd;
	}
	
	public static void setSimulateFrontEnd(Context panelContext,boolean isSimulateForntEnd)throws Exception{
		panelContext.putCacheValue("$SimulateFrontEnd", isSimulateForntEnd);
	}
}
