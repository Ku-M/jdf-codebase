package fe.util.component;

import java.util.List;

import com.cdao.mgr.lock.LockFailException;
import com.kwaidoo.ms.tool.ToolUtilities;
import com.leavay.common.util.TimeoutException;

import cell.cdao.ILock;
import crpc.CRpcContainerIntf;
import fe.cmn.data.CColor;
import fe.cmn.data.ImageDataDto;
import fe.cmn.panel.PanelContext;
import fe.cmn.panel.PanelDto;
import fe.cmn.panel.PanelInfo;
import fe.cmn.panel.ability.GetScreenshotOfWidget;
import fe.cmn.panel.ability.QueryParentPanel;
import fe.cmn.panel.ability.SetChildVisible;
import fe.cmn.widget.WidgetDto;
import fe.util.LoadingMask;
import fe.util.OperateTransaction;
import fe.util.component.builder.AbsFeBuilder;
import fe.util.component.builder.FeBuilderPortal;
import fe.util.component.extlistener.CommandCallbackListener;
import fe.util.component.param.WidgetParam;
import fe.util.dto.loading.LoadingMaskConfigDto;
import fe.util.intf.ComponentI18nIntf;
import fe.util.intf.ServiceIntf;
import fe.util.style.FeStyleSetting;
import fe.util.style.FeStyleSettingUtil;

/**
 * 组件实现的抽象基类，使用脚手架构建的组件必须派生于当前类，
 * 封装了对脚手架接管的监听器构建方法，可使用相关方法构建脚手架所需的监听器对象
 * 封装了对基础编辑器组件、按钮组件的构建方法，
 *
 * @param <T>
 * @author chenxb
 */
public abstract class AbsComponent<T extends WidgetParam> implements Component<T>, ComponentI18nIntf,FeBuilderPortal,CRpcContainerIntf {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4375322512347486139L;
	public T widgetParam;
	private PanelContext panelContext;

	public T getWidgetParam() {
		return widgetParam;
	}

	@Override
	public void setWidgetParam(T param) {
		this.widgetParam = param;
		if (widgetParam != null) {
			this.widgetParam.setInvokeClass(getClass().getName());
		}
	}
	/**
	 * 在信道中缓存widgetParam，调用此方法可优化交互监听反复从前端查询widgetParam的传输开销
	 * 需要注意，调用此方法前，需要为面板预先分配好panelGlobalKey和widgetId,并设置在返回的面板对象上。
	 * @throws Exception 
	 */
	public void cacheWidgetParamInChannel(PanelContext panelContext,String initPanelGlobalKey) throws Exception {
		ToolUtilities.assertNotNull(widgetParam, "widgetParam is null!");
		ToolUtilities.assertNotEmpty(widgetParam.getWidgetId(), "widgetId is not inited in widgetParam!");
		String cacheKey = ServiceIntf.getCacheWidgetParamKey(widgetParam.getWidgetId());
		initPanelCacheValue(panelContext, initPanelGlobalKey, cacheKey, widgetParam);
	}
	/**
	 * 在信道中缓存widgetParam，调用此方法可优化交互监听反复从前端查询widgetParam的传输开销
	 * 需要注意，调用此方法前，需要为面板预先分配好panelGlobalKey和widgetId。
	 * @param panelContext
	 * @param panel
	 * @throws Exception
	 */
	public void cacheWidgetParamInChannel(PanelContext panelContext,PanelDto panel) throws Exception {
		ToolUtilities.assertNotEmpty(panel.getPanelGlobalKey(), "panelGlobalKey in panel is empty!");
		ToolUtilities.assertNotEmpty(panel.getWidgetId(), "widgetId in pnael is empty!");
		ToolUtilities.assertNotNull(widgetParam, "widgetParam is null!");
		ToolUtilities.assertNotEmpty(widgetParam.getWidgetId(), "widgetId in widgetParam is empty!");
		String cacheKey = ServiceIntf.getCacheWidgetParamKey(widgetParam.getWidgetId());
		initPanelCacheValue(panelContext, panel.getPanelGlobalKey(), cacheKey, widgetParam);
	}

	public PanelContext getPanelContext() {
		return panelContext;
	}

	public void setPanelContext(PanelContext panelContext) {
		this.panelContext = panelContext;
	}

	/**
	 * 实现此方法，定义为当前开发工程中的Cell类，可在云开发时进行Cell接管调试
	 */
	public abstract Class<? extends ServiceIntf> getService();

	@Override
	public final Class<? extends ServiceIntf> getBuilderService() {
		Class<? extends ServiceIntf> debugService = AbsFeBuilder.getDebugService();
		return debugService == null ? getService() : debugService;
	}

	/**
	 * 将父组件参数中的上下文参数带到子组件中，可在子组件中使用上下文参数获取一些会话中缓存的参数值
	 *
	 * @param param
	 * @param parentWidgetParam
	 */
	public void setWidgetParamWithContext(T param, WidgetParam parentWidgetParam) {
		setWidgetParam(param);
		if (widgetParam != null) {
			this.widgetParam.getContext().putAll(parentWidgetParam.getContext());
		}
	}

	@Override
	public void setVisible(PanelContext panelContext, WidgetDto widget, boolean visible) throws Exception {
		PanelInfo parentInfo = QueryParentPanel.queryOne(panelContext);
		PanelContext copyCtx = panelContext.cloneWithChannel();
		copyCtx.setCurrentPanelGlobalKey(parentInfo.getPanelGlobalKey());
		SetChildVisible.set(copyCtx, panelContext.getCurrentPanelWidgetId(), visible);
	}

	@Override
	public void setWritable(PanelContext panelContext, WidgetDto widget, boolean writable) throws Exception {
		//TODO
	}

	@Override
	public WidgetDto rebuildWidget(PanelContext panelContext, WidgetDto widget) throws Exception {
		WidgetDto newWidget = getWidget(panelContext);
		newWidget.setWidgetId(widget.getWidgetId());
		if (newWidget instanceof PanelDto) {
			PanelDto orgPanel = (PanelDto) widget;
			((PanelDto) newWidget).setPanelGlobalKey(orgPanel.getPanelGlobalKey());
		}
		return newWidget;
	}

	/**
	 * 获取基础样式设置类
	 *
	 * @return
	 * @throws Exception
	 */
	public FeStyleSetting getFeStyleSetting(PanelContext panelContext) throws Exception {
		return FeStyleSettingUtil.getFeStyleSetting(panelContext.getChannel());
	}
	
	/**
	 * 对指定key进行加锁，注意需要和unlockKey成对出现，在finally的时候进行unlock
	 * @param key
	 * @param timeout
	 * @throws LockFailException
	 * @throws TimeoutException
	 */
	public void lockWidget(PanelContext panelContext,String widgetId,long timeout) throws LockFailException, TimeoutException {
		String key = panelContext.getCurrentPanelGlobalKey() + "@"+ widgetId;
		ILock.get().lockKey(key, timeout);
	}
	
	public void unlockWidget(PanelContext panelContext,String widgetId) {
		String key = panelContext.getCurrentPanelGlobalKey() + "@"+ widgetId;
		ILock.get().unlock(key);
	}
	
	/**
	 * 给组件设置指令回调监听器，需要在widget.setBinaryData前调用
	 * @param widget
	 * @param callbackLsnrs
	 * @throws Exception 
	 * @throws  
	 */
	public void setCommandCallbackListener(WidgetDto widget,List<CommandCallbackListener> callbackLsnrs) throws Exception {
		if(callbackLsnrs != null) {
			for(CommandCallbackListener callback : callbackLsnrs) {
				widgetParam.addCommandCallbackLsnr(callback);
				widget.addExtendListener(callback);
			}
		}
	}
	
	/**
	 * 获取指定组件的截图
	 * @param panelContext
	 * @param widgetId
	 * @return
	 * @throws Exception
	 */
	public byte[] getScreenshot(PanelContext panelContext,String widgetId)throws Exception{
		ImageDataDto data = GetScreenshotOfWidget.getScreenshot(panelContext, widgetId);
		if (data != null) {
			byte[] imageBytes = data.getBytes();
			return imageBytes;
		}
		return null;
	}
	
	public byte[] getScreenshot(PanelContext panelContext,String widgetId,CColor color)throws Exception{
		ImageDataDto data = GetScreenshotOfWidget.getScreenshot(panelContext, widgetId,color);
		if (data != null) {
			byte[] imageBytes = data.getBytes();
			return imageBytes;
		}
		return null;
	}
	/**
	 * 显示遮罩层
	 * @param panelContext
	 * @param config
	 * @throws Exception
	 */
	public void showLoading(PanelContext panelContext,LoadingMaskConfigDto config) throws Exception {
		LoadingMask.show(panelContext, panelContext.getCurrentPanelWidgetId(),config);
	}
	public void showLoading(PanelContext panelContext,String widgetId,LoadingMaskConfigDto config) throws Exception {
		ToolUtilities.assertNotEmpty(widgetId, "widgetId cant not be empty!");
		LoadingMask.show(panelContext, widgetId,config);
	}
	public void hideLoading(PanelContext panelContext) throws Exception {
		hideLoading(panelContext, panelContext.getCurrentPanelWidgetId());
	}
	/**
	 * 隐藏遮罩层
	 * @param panelContext
	 * @param widgetId
	 * @throws Exception
	 */
	public void hideLoading(PanelContext panelContext,String widgetId) throws Exception {
		ToolUtilities.assertNotEmpty(widgetId, "widgetId cant not be empty!");
		LoadingMask.hide(panelContext,widgetId);
	}
	
	public void asyncShowLoading(PanelContext panelContext,LoadingMaskConfigDto config) throws Exception {
		LoadingMask.asyncShow(panelContext, panelContext.getCurrentPanelWidgetId(),config);
	}
	public void asyncShowLoading(PanelContext panelContext,String widgetId,LoadingMaskConfigDto config) throws Exception {
		ToolUtilities.assertNotEmpty(widgetId, "widgetId cant not be empty!");
		LoadingMask.asyncShow(panelContext, widgetId,config);
	}
	public void asyncHideLoading(PanelContext panelContext) throws Exception {
		asyncHideLoading(panelContext, panelContext.getCurrentPanelWidgetId());
	}
	public void asyncHideLoading(PanelContext panelContext,String widgetId) throws Exception {
		ToolUtilities.assertNotEmpty(widgetId, "widgetId cant not be empty!");
		LoadingMask.asyncHide(panelContext,widgetId);
	}
	
}
