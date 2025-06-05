package fe.util;

import java.util.List;

import com.kwaidoo.ms.tool.CmnUtil;
import com.leavay.common.util.ToolUtilities;
import com.leavay.common.util.javac.ClassFactory;

import cell.fe.cmn.IFeCmnService;
import cmn.anotation.ClassDeclare;
import fe.cmn.panel.LazyPanelBuilder;
import fe.cmn.panel.LazyPanelDto;
import fe.cmn.panel.LazyPanelInterface;
import fe.cmn.panel.PanelContext;
import fe.cmn.widget.ExtListenerDto;
import fe.cmn.widget.WidgetDto;
import fe.util.component.AbsComponent;
import fe.util.component.Component;
import fe.util.component.dto.FeDeliverData;
import fe.util.component.param.LazyPanelParam;
import fe.util.component.param.WidgetParam;
import fe.util.intf.ServiceIntf;
@ClassDeclare(
	    label = "懒加载面板工具",
	    what = "用于处理延迟加载面板相关功能的实用工具类",
	    why = "提供接口实现和功能封装，以支持延迟加载面板的动态加载和显示",
	    how = "",
	    developer = "陈晓斌",
	    version = "1.0",
	    createTime = "2024-11-22",
	    updateTime = "2024-11-22"
	)
public class LazyPanelUtil extends AbsComponent<LazyPanelParam> implements LazyPanelInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5957716405730117187L;
	
	public static String CONTEXT_KEY_LAZY_INVOKE_CLASS = "lazyInvokeClass";
	public static String CONTEXT_KEY_SERVICE_CLASS = "serviceClass";
	
	public void initPanelCache(PanelContext panelContext,String panelGlobalKey) throws Exception {
		initPanelCacheValue(panelContext, panelGlobalKey, ServiceIntf.getCacheWidgetParamKey(widgetParam.getWidgetId()), widgetParam);
	}

	@Override
	public WidgetDto getWidget(PanelContext panelContext) throws Exception {
		FeDeliverData data = new FeDeliverData<>(getClass());
		LazyPanelBuilder builder = new LazyPanelBuilder(getBuilderService());
		builder.setBinaryData(data);
		if(CmnUtil.isStringEmpty(widgetParam.getWidgetId()))
			widgetParam.setWidgetId(ToolUtilities.allockUUIDWithUnderline());
		String panelGlobalKey = ToolUtilities.allockUUIDWithUnderline();
		initPanelCache(panelContext, panelGlobalKey);
		LazyPanelDto lazyPanel = new LazyPanelDto(builder);
		lazyPanel.setPanelGlobalKey(panelGlobalKey);
		lazyPanel.setBinaryData(widgetParam);
		if(widgetParam.isUseWidgetIdAsLazyPanelId())
			lazyPanel.setWidgetId(widgetParam.getWidgetId());
		else
			lazyPanel.setWidgetId("LAZY_"+widgetParam.getWidgetId());
		return lazyPanel;
	}

	@Override
	public WidgetDto buildLazyPanelChild(LazyPanelBuilder builder, PanelContext context) throws Exception {
//		String realInvokeClass = (String) widgetParam.getContext().get(CONTEXT_KEY_LAZY_INVOKE_CLASS);
		Component inst = getRealPanelComponent();
		WidgetDto realPanel = inst.getWidget(context);
		if(!CmnUtil.isCollectionEmpty(widgetParam.getExtListeners())) {
			for(ExtListenerDto extListener : widgetParam.getExtListeners()){
				realPanel.addExtendListener(extListener);
			}
		}
		return realPanel;
	}
	
	public Component getRealPanelComponent() throws Exception {
		String realInvokeClass = widgetParam.getLazyInvokeClass();
		Class<?> clazz = ClassFactory.getValidClassLoader().loadClass(realInvokeClass);
		Component inst = (Component) clazz.newInstance();
		inst.setWidgetParam(widgetParam.getLazyPanelParam());
		return inst;
	}

	@Override
	public Class<? extends ServiceIntf> getService() {
		try {
			String serviceClass = (String) widgetParam.getContext().get(CONTEXT_KEY_SERVICE_CLASS);
			if(!CmnUtil.isStringEmpty(serviceClass)) {
				return (Class<? extends ServiceIntf>) ClassFactory.getValidClassLoader().loadClass(serviceClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String realInvokeClass = (String) widgetParam.getContext().get(CONTEXT_KEY_LAZY_INVOKE_CLASS);
			if(!CmnUtil.isStringEmpty(realInvokeClass)){
				Class<?> clazz = ClassFactory.getValidClassLoader().loadClass(realInvokeClass);
				AbsComponent inst = (AbsComponent) clazz.newInstance();
				Class<? extends ServiceIntf> service = inst.getBuilderService();
				if(service != null)
					return service;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return IFeCmnService.class;
	}

	public static LazyPanelDto build(PanelContext panelContext,Class<? extends Component> clazz,WidgetParam widgetParam) throws Exception {
		LazyPanelUtil panel = new LazyPanelUtil();
//		widgetParam.getContext().put(CONTEXT_KEY_LAZY_INVOKE_CLASS, clazz.getName());
		LazyPanelParam param = new LazyPanelParam();
		param.setLazyInvokeClass(clazz.getName());
		param.setLazyPanelParam(widgetParam);
		param.setWidgetId(widgetParam.getWidgetId());
		panel.setWidgetParam(param);
		return (LazyPanelDto) panel.getWidget(panelContext);
	}
	
	public static LazyPanelDto build(Class<? extends ServiceIntf> service,PanelContext panelContext,Class<? extends Component> clazz,WidgetParam widgetParam) throws Exception {
		LazyPanelUtil panel = new LazyPanelUtil();
		if(service != null)
			widgetParam.getContext().put(CONTEXT_KEY_SERVICE_CLASS, service.getName());
//		widgetParam.getContext().put(CONTEXT_KEY_LAZY_INVOKE_CLASS, clazz.getName());
//		panel.setWidgetParam(ToolUtilities.clone(widgetParam));
		LazyPanelParam param = new LazyPanelParam();
		param.setLazyInvokeClass(clazz.getName());
		param.setLazyPanelParam(widgetParam);
		param.setWidgetId(widgetParam.getWidgetId());
		if(service != null)
			param.getContext().put(CONTEXT_KEY_SERVICE_CLASS, service.getName());
		panel.setWidgetParam(param);
		return (LazyPanelDto) panel.getWidget(panelContext);
	}
	
	public static LazyPanelDto build(Class<? extends ServiceIntf> service,PanelContext panelContext,Class<? extends Component> clazz,WidgetParam widgetParam,boolean useWidgetIdAsLazyPanelId) throws Exception {
		LazyPanelUtil panel = new LazyPanelUtil();
		if(service != null)
			widgetParam.getContext().put(CONTEXT_KEY_SERVICE_CLASS, service.getName());
//		widgetParam.getContext().put(CONTEXT_KEY_LAZY_INVOKE_CLASS, clazz.getName());
//		panel.setWidgetParam(ToolUtilities.clone(widgetParam));
		LazyPanelParam param = new LazyPanelParam();
		param.setLazyInvokeClass(clazz.getName());
		param.setLazyPanelParam(widgetParam);
		param.setWidgetId(widgetParam.getWidgetId());
		param.setUseWidgetIdAsLazyPanelId(useWidgetIdAsLazyPanelId);
		if(service != null)
			param.getContext().put(CONTEXT_KEY_SERVICE_CLASS, service.getName());
		panel.setWidgetParam(param);
		return (LazyPanelDto) panel.getWidget(panelContext);
	}
	
	public static LazyPanelDto build(Class<? extends ServiceIntf> service,PanelContext panelContext,Class<? extends Component> clazz,WidgetParam widgetParam,List<ExtListenerDto> extLsnrs) throws Exception {
		LazyPanelUtil panel = new LazyPanelUtil();
		if(service != null)
			widgetParam.getContext().put(CONTEXT_KEY_SERVICE_CLASS, service.getName());
//		widgetParam.getContext().put(CONTEXT_KEY_LAZY_INVOKE_CLASS, clazz.getName());
//		panel.setWidgetParam(ToolUtilities.clone(widgetParam));
		LazyPanelParam param = new LazyPanelParam();
		param.setLazyInvokeClass(clazz.getName());
		param.setLazyPanelParam(widgetParam);
		param.setExtListeners(extLsnrs);
		param.setWidgetId(widgetParam.getWidgetId());
		if(service != null)
			param.getContext().put(CONTEXT_KEY_SERVICE_CLASS, service.getName());
		panel.setWidgetParam(param);
		return (LazyPanelDto) panel.getWidget(panelContext);
	}
	
	public static LazyPanelDto build(Class<? extends ServiceIntf> service,PanelContext panelContext,Class<? extends Component> clazz,WidgetParam widgetParam,boolean useWidgetIdAsLazyPanelId,List<ExtListenerDto> extLsnrs) throws Exception {
		LazyPanelUtil panel = new LazyPanelUtil();
		if(service != null)
			widgetParam.getContext().put(CONTEXT_KEY_SERVICE_CLASS, service.getName());
//		widgetParam.getContext().put(CONTEXT_KEY_LAZY_INVOKE_CLASS, clazz.getName());
//		panel.setWidgetParam(ToolUtilities.clone(widgetParam));
		LazyPanelParam param = new LazyPanelParam();
		param.setLazyInvokeClass(clazz.getName());
		param.setLazyPanelParam(widgetParam);
		param.setUseWidgetIdAsLazyPanelId(useWidgetIdAsLazyPanelId);
		param.setExtListeners(extLsnrs);
		param.setWidgetId(widgetParam.getWidgetId());
		if(service != null)
			param.getContext().put(CONTEXT_KEY_SERVICE_CLASS, service.getName());
		panel.setWidgetParam(param);
		return (LazyPanelDto) panel.getWidget(panelContext);
	}
}
