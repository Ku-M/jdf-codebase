package gpf.dc.basic.fe.intf;

import cmn.i18n.I18nIntf;
import fe.cmn.app.Context;
import fe.cmn.panel.PanelContext;
import fe.util.intf.ComponentI18nIntf;
import gpf.dc.basic.fe.component.app.AppCacheUtil;
import gpf.dc.basic.param.view.dto.ApplicationSetting;
/**
 * 
 * @What: 应用的国际化资源接口，实现此接口提供统一的国际化资源获取
 * @Why: 
 * @How: 
 * @Author 陈晓斌
 * @CreateTime : 2024年11月7日
 * @Version: 1.0
 */
public interface AppComponentI18nIntf extends ComponentI18nIntf{

	@Override
	default I18nIntf getI18n(PanelContext context) throws Exception {
		return AppCacheUtil.getAppFeI18n(context);
	}
	
	default I18nIntf getI18n(Context context) throws Exception {
		return AppCacheUtil.getAppFeI18n(context);
	}
	
	default String getI18nStringOfApp(Context context,ApplicationSetting appSetting,String key,Object... params) throws Exception {
		if(appSetting == null || !appSetting.getAppViewSetting().isEnableI18n())
			return key;
		I18nIntf i18n = getI18n(context);
		if(i18n == null)
			return key;
		return i18n.formatInGroup(key, appSetting.getName(), params);
	}
}
