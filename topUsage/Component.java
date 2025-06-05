package fe.util.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.kwaidoo.ms.tool.CmnUtil;
import com.kwaidoo.ms.tool.ToolUtilities;

import cell.cmn.cache.IMapCell;
import cell.fe.IFeService;
import cmn.util.TraceUtil;
import cmn.util.Tracer;
import cson.core.CsonPojo;
import fe.cmn.app.Context;
import fe.cmn.app.ability.BatchExecuteCallback;
import fe.cmn.event.EventDto;
import fe.cmn.panel.PanelContext;
import fe.cmn.panel.ability.ConvertPanelContext;
import fe.cmn.panel.ability.GetExtListener;
import fe.cmn.widget.ExtListenerDto;
import fe.cmn.widget.WidgetDto;
import fe.util.component.dto.FeCmnEvent;
import fe.util.component.dto.FeDeliverData;
import fe.util.component.extlistener.CommandListener;
import fe.util.component.param.WidgetParam;
import fe.util.exception.VerifyException;
import fe.util.intf.ServiceIntf;

public interface Component<T extends WidgetParam> extends Serializable{
	
	public T getWidgetParam();
	public void setWidgetParam(T param);
	
	public PanelContext getPanelContext();
	public void setPanelContext(PanelContext panelContext);

//	public String getWidgetId();
	public WidgetDto getWidget(PanelContext panelContext) throws Exception;
	public Class<? extends ServiceIntf> getService();
	
	//设置属性
	public void setVisible(PanelContext panelContext,WidgetDto widget,boolean visible)throws Exception;
	//设置组件可写
	public void setWritable(PanelContext panelContext,WidgetDto widget,boolean writable)throws Exception;
	
	public WidgetDto rebuildWidget(PanelContext panelContext,WidgetDto widget)throws Exception;
	
	/**
	 * 触发指令监听器
	 * @param panelContext
	 * @param targetWidgetId	目标组件
	 * @param targetCmd	目标指令
	 * @param inputParam	目标指令的入参
	 * @throws Exception
	 */
	default void fireCommandListener(PanelContext panelContext,String targetWidgetId,String targetCmd,Object inputParam) throws Exception {
		ExtListenerDto lsnr = GetExtListener.get(panelContext, targetWidgetId, targetCmd);
		if(lsnr instanceof CommandListener) {
			if (lsnr != null && lsnr.getListener() != null) {
				PanelContext targetPanelContext = panelContext.cloneWithChannel();
				if(((CommandListener) lsnr).isPanel()) {
					targetPanelContext = ConvertPanelContext.convert(panelContext, ""+targetWidgetId);
				}
				if(inputParam != null) {
					if(lsnr.getListener().getServerExecutor() != null) {
						FeDeliverData cmdExtraData = (FeDeliverData) lsnr.getListener().getBinaryData();
						cmdExtraData.setData(inputParam);
						lsnr.getListener().setBinaryData(cmdExtraData);
					}else if(lsnr.getListener().getEventExecutor() != null) {
						EventDto event = lsnr.getListener().getEventExecutor().getEvent();
						if(event instanceof FeCmnEvent) {
							FeCmnEvent cmnEvent = (FeCmnEvent) event;
							if(cmnEvent.getInvokeParams() == null) {
								cmnEvent.setInvokeParams(new Object[] {inputParam});
							}
							cmnEvent.setSelfBinaryData();
						}
					}
				}
				ToolUtilities.setFieldValue(lsnr.getListener(), "sourceWidgetId", targetWidgetId);
				//同步触发
				if(lsnr.getListener().isSynchronize())
					IFeService.get().fireListener(targetPanelContext, lsnr);
				else {
					//异步触发
					ToolUtilities.asynCallFunction(IFeService.get(), "fireListener", targetPanelContext,lsnr);
				}
			}
		}
	}
	/**
	 * 获取面板上的全局缓存（信道缓存），
	 * 需要缓存key在widgetParam的context中有指定初始化缓存面板的panelGlobalKey的关联关系
	 * @param panelContext
	 * @param cacheKey
	 * @return
	 */
	default Object getPanelCacheValue(PanelContext panelContext,String cacheKey) {
		return getPanelCacheValue(panelContext, cacheKey, false);
	}
	/**
	 * 获取面板上的全局缓存（信道缓存），
	 * 需要缓存key在widgetParam的context中有指定初始化缓存面板的panelGlobalKey的关联关系
	 * @param panelContext
	 * @param cacheKey
	 * @param strictCheck 是否严格检查缓存所指定的面板是否存在
	 * @return
	 */
	default Object getPanelCacheValue(PanelContext panelContext,String cacheKey,boolean strictCheck) {
		String panelGlobalKey = (String) getWidgetParam().getContext().get(cacheKey);
		if(CmnUtil.isStringEmpty(panelGlobalKey)) {
			if(strictCheck) {
				throw new VerifyException("getPanelCacheValue错误：未找到缓存key["+cacheKey+"]所在的面板panelGlobalKey");
			}else {
				Tracer tracer = TraceUtil.getCurrentTracer();
				tracer.warning("getPanelCacheValue", "未找到缓存key["+cacheKey+"]所在的面板panelGlobalKey");
				return null;
			}
		}
		IMapCell map = panelContext.getOrCreatePanelCache(panelGlobalKey);
		return map.getValue(cacheKey);
	}
	/**
	 * 初始化面板上的全局缓存（信道缓存），
	 * 在组件getWidget的时候，通过预先分配面板的panelGlobalKey，指定要缓存key和需要初始化缓存的值
	 * 初始化后的缓存可在当前面板下的子面板或者弹出面板获取缓存，只需在构建子面板或者弹出面板时调用
	 * AbsComponent.setWidgetParamWithContext,将context传递到其他面板接口
	 * 初始化后的全局缓存将跟随指定面板的生命周期。
	 * @param panelContext
	 * @param panelGlobalKey
	 * @param cacheKey
	 * @param value
	 * @throws Exception 
	 */
	default void initPanelCacheValue(PanelContext panelContext,String panelGlobalKey,String cacheKey,Object value){
		if(cacheKey == null)
			return;
		if(CmnUtil.isStringEmpty(panelGlobalKey))
			throw new RuntimeException("initPanelCacheValue Error : panelGlobalKey不能为空！");
		getWidgetParam().getContext().put(cacheKey, panelGlobalKey);
//		if(value != null)
		setPanelCacheValue(panelContext, cacheKey, value);
	}
	/**
	 * 设置面板上的全局缓存（信道缓存）
	 * 需要缓存key在widgetParam的context中有指定初始化缓存面板的panelGlobalKey的关联关系
	 * @param panelContext
	 * @param cacheKey
	 * @param value
	 * @throws Exception 
	 */
	default void setPanelCacheValue(PanelContext panelContext,String cacheKey,Object value){
		setPanelCacheValue(panelContext, cacheKey, value, false);
	}
	/**
	 * 设置面板上的全局缓存（信道缓存）
	 * 需要缓存key在widgetParam的context中有指定初始化缓存面板的panelGlobalKey的关联关系
	 * @param panelContext
	 * @param cacheKey
	 * @param value
	 * @param strictCheck 是否严格检查缓存所指定的面板是否存在
	 * @throws Exception 
	 */
	default void setPanelCacheValue(PanelContext panelContext,String cacheKey,Object value,boolean strictCheck){
		String panelGlobalKey = (String) getWidgetParam().getContext().get(cacheKey);
		if(CmnUtil.isStringEmpty(panelGlobalKey)) {
			if(strictCheck) {
				throw new VerifyException("setPanelCacheValue错误：未找到缓存key["+cacheKey+"]所在的面板panelGlobalKey，请先调用initPanelCacheValue");
			}else {
				Tracer tracer = TraceUtil.getCurrentTracer();
				tracer.warning("setPanelCacheValue", "未找到缓存key["+cacheKey+"]所在的面板panelGlobalKey，请先调用initPanelCacheValue");
				return;
			}
		}
		IMapCell map = panelContext.getOrCreatePanelCache(panelGlobalKey);
		if(value == null)
			map.remove(cacheKey);
		else
			map.putValue(cacheKey, value);
	}
	/**
	 * 获取面板自定义的请求日志业务分类，最多可以有三个
	 * @param panelContext
	 * @return
	 */
	default List<String> getRequestCategorys(PanelContext panelContext){
		return null;
	}
	
	/**
	 * 批量执行回调，并将回调执行结果返回
	 * @param context	界面上下文
	 * @param callbackMap	key ： 回调结果标识 value：回调
	 * @return	Map key : 回调结果标识， value:回调值
	 * @throws Exception
	 */
	default Map<String,Object> batchExecuteCallbacks(Context context,Map<String,CsonPojo> callbackMap) throws Exception{
		List<CsonPojo> callbacks = new ArrayList<>(callbackMap.values());
		List<Object> results = BatchExecuteCallback.batchExecute(context.getChannel(), callbacks);
		int i = 0;
		Map<String,Object> resultMap = new LinkedHashMap<String, Object>();
		for(String key : callbackMap.keySet()) {
			resultMap.put(key, results.get(i));
			i++;
		}
		return resultMap;
	}
}
