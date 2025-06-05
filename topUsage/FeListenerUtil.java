package fe.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import com.leavay.ms.tool.CmnUtil;

import cell.cmn.cache.IMapCell;
import cmn.util.NullUtil;
import fe.cmn.editor.listener.OnValueChanged;
import fe.cmn.panel.PanelContext;
import fe.cmn.panel.PanelDto;
import fe.cmn.table.listener.OnTableCellValueChanged;
import fe.cmn.table.listener.TableRowListener;
import fe.cmn.tree.listener.OnButtonBarClick;
import fe.cmn.widget.ListenerDto;
import fe.cmn.widget.ListenerInterface;
import fe.cmn.widget.WidgetDto;
import fe.cmn.widget.listener.OnClickListener;
import fe.util.component.dto.FeDeliverData;
import fe.util.component.extlistener.CommandCallbackListener;
import fe.util.component.param.WidgetParam;
import fe.util.intf.ServiceIntf;

public class FeListenerUtil implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2697155628859535072L;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> ListenerDto newListener(Class<? extends ListenerInterface> clazz,String cmd,boolean synchronize,FeDeliverData<T> data) throws IOException{
		ListenerDto listener = new ListenerDto<>(clazz, cmd,synchronize);
		listener.setBinaryData(data);
		return listener;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> OnClickListener OnClick(Class<? extends ListenerInterface> clazz, String cmd, boolean synchronize, FeDeliverData<T> data) throws IOException{
		OnClickListener listener = new OnClickListener<>(clazz, cmd,synchronize);
		listener.setBinaryData(data);
		return listener;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> TableRowListener OnTableRowClick(Class<? extends ListenerInterface> clazz, String cmd, boolean synchronize, FeDeliverData<T> data) throws IOException{
		TableRowListener listener = new TableRowListener<>(clazz, cmd,synchronize);
		listener.setBinaryData(data);
		return listener;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> OnTableCellValueChanged OnTableCellValueChanged(Class<? extends ListenerInterface> clazz,String cmd,boolean synchronize,FeDeliverData<T> data) throws IOException{
		OnTableCellValueChanged listener = new OnTableCellValueChanged<>(clazz, cmd,synchronize);
		listener.setBinaryData(data);
		return listener;
	}
	
	@SuppressWarnings("rawtypes")
	public static <T> OnButtonBarClick OnButtonBarClick(Class<? extends ListenerInterface> clazz,String cmd,boolean synchronize,FeDeliverData<T> data) throws IOException{
		OnButtonBarClick listener = new OnButtonBarClick<>(clazz, cmd,synchronize);
		listener.setBinaryData(data);
		return listener;
	}
	
	public static <T> OnValueChanged OnValueChanged(Class<? extends ListenerInterface> clazz,String cmd,boolean synchronize,FeDeliverData<T> data) throws IOException{
		OnValueChanged listener = new OnValueChanged(clazz, cmd,synchronize);
		listener.setBinaryData(data);
		return listener;
	}
	/**
	 * 给组件设置指令回调监听器，需要在widget.setBinaryData之后调用
	 * @param widget
	 * @param callbackLsnrs
	 * @throws Exception 
	 * @throws  
	 */
	public static void setWidgetCommandCallbackListener(PanelContext panelContext,WidgetDto widget,CommandCallbackListener... callbackLsnrs) throws Exception {
		if(callbackLsnrs == null || callbackLsnrs.length == 0)
			return;
		WidgetParam widgetParam = null;
		IMapCell map = null;
		String cacheKey = null;
		if(widget instanceof PanelDto) {
			cacheKey = ServiceIntf.getCacheWidgetParamKey(widget.getWidgetId());
			map = panelContext.getOrCreatePanelCache(((PanelDto) widget).getPanelGlobalKey());
			widgetParam = (WidgetParam) map.getValue(cacheKey);
			if(widgetParam == null) {
				widgetParam = (WidgetParam) widget.getBinaryData();
			}
		}else {
			widgetParam = (WidgetParam) widget.getBinaryData();
		}
		if(widgetParam == null) {
			throw new Exception("widgetParam is null!");
		}
		for(CommandCallbackListener callback : callbackLsnrs) {
			widgetParam.addCommandCallbackLsnr(callback);
			widget.addExtendListener(callback);
		}
		widget.setBinaryData(widgetParam);
		if(map != null && cacheKey != null) {
			map.putValue(cacheKey, widgetParam);
		}
	}
}
