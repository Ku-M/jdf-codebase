package fe.util.editor.valuehanlder;

import com.kwaidoo.ms.tool.ToolUtilities;
import com.leavay.ms.tool.CmnUtil;

import cell.fe.cmn.IFeI18nPlugin;
import fe.cmn.data.NullPojo;
import fe.cmn.panel.PanelContext;
import fe.util.exception.VerifyException;
import fe.util.i18n.FeI18n;

import java.io.Serializable;
import java.util.Map;

public class DefaultEditorHandler implements EditorTypeHandler, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7659729702295013526L;
	EditorValueHandlerFactory factory;

	@Override
	public EditorValueHandlerFactory getFactory() {
		return factory;
	}

	public void setFactory(EditorValueHandlerFactory factory) {
		this.factory = factory;
	}

	@Override
	public void handler(Object object, String widgetId, EditorFieldDefine fieldDef, Object guiValue) throws Exception {
		setFieldValue(object, widgetId, guiValue);
	}
	
	public Object getFieldValue(Object object, String widgetId)
			throws Exception {
		return factory.getFieldValue(object, widgetId);
	}

	public void setFieldValue(Object object, String widgetId, Object guiValue)
			throws Exception {
		factory.setFieldValue(object, widgetId, guiValue);
	}
	
	@Override
	public void verifyRequireValue(PanelContext panelContext,EditorFieldDefine fieldDef,Object value,String tipsPrefix) throws Exception {
		if (NullPojo.isNull(value) || CmnUtil.isObjectEmpty(value)) {
			String msg = getVerifyRequireErrorMsg(panelContext, tipsPrefix, fieldDef.getLabel());
			throw new VerifyException(msg);
		}
	}
	
	public String getI18nString(PanelContext panelContext,String key,Object... params) throws Exception {
		return IFeI18nPlugin.get().getI18nString(panelContext, key,params);
	}
	/**
	 * 获取默认的
	 * @param panelContext
	 * @param tipsPrefix
	 * @param fieldLabel
	 * @return
	 * @throws Exception
	 */
	public String getVerifyRequireErrorMsg(PanelContext panelContext,String tipsPrefix,String fieldLabel) throws Exception {
		String msg = getI18nString(panelContext,FeI18n.CAN_NOT_BE_NULL,CmnUtil.getString(tipsPrefix,"")+fieldLabel);
		return msg;
	}
	
	@Override
	public boolean isBasicEditor() {
		return true;
	}
}
