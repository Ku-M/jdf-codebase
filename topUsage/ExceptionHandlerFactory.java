package fe.util.exception.handler;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.kwaidoo.ms.tool.CmnUtil;
import com.leavay.client.util.CNClientUtil;
import com.leavay.common.util.ToolUtilities;
import com.leavay.common.util.javac.ClassFactory;

import bap.cells.Cells;
import cell.CellIntf;
import cell.cdao.IDao;
import cell.cdao.IDaoService;
import cell.cmn.util.IServerConfig;
import cmn.exception.handler.ErrorHandler;
import cmn.util.ProxyUtil;
import cmn.util.TraceUtil;
import cmn.util.Tracer;
import fe.cmn.app.Context;
import fe.cmn.panel.PanelContext;
import fe.md.ExceptionHandlerConfig;
import fe.util.FeDebugUtil;
import fe.util.enums.ExceptionHandlerType;
import fe.util.intf.ErrorHandlerPluginIntf;

public class ExceptionHandlerFactory {

	private static boolean _isDebug = false;

	private static ExceptionHandlerFactory me = null;

	private final Map<String, Class<? extends Throwable>> exceptionClassMap = new HashMap<>();
	private final Map<String, ExceptionHandler> handlerMap = new HashMap<>();

	private ExceptionHandlerFactory() {
		init();
	}

	public synchronized static ExceptionHandlerFactory get() {
		if (me == null) {
			me = new ExceptionHandlerFactory();
		}
		return me;
	}

	public static void setDebugMode(boolean isDebug) {
		ExceptionHandlerFactory._isDebug = isDebug;
	}
	
	public static boolean isDebug() {
		return _isDebug;
	}

	private void loadExceptionHandlerInResFile() {
		try {
			URL url = CNClientUtil.getResourceURL("ConfigRes/ExceptionHandler.properties");
			Properties props = new Properties();
			props.load(url.openStream());
			for (Object keyObj : props.keySet()) {
				String exceptionClass = (String) keyObj;
				String handlerClass = props.getProperty(exceptionClass);
				try {
					Class handlerClazz = ClassFactory.getValidClassLoader().loadClass(handlerClass);
					if (ExceptionHandler.class.isAssignableFrom(handlerClazz)) {
						addHandler(exceptionClass, (ExceptionHandler) handlerClazz.newInstance());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void init() {
		try {
			// 读取资源文件内的配置
			loadExceptionHandlerInResFile();
			// 读取数据库的动态配置
			try (IDao dao = IDaoService.get().newDao()) {
				List<ExceptionHandlerConfig> list = dao.queryDoList(ExceptionHandlerConfig.class, null);
				for (ExceptionHandlerConfig config : list) {
					String exceptionClass = config.getExceptionClass();
					String handlerClass = config.getHandlerClass();
					try {
						Class handlerClazz = ClassFactory.getValidClassLoader().loadClass(handlerClass);
						if (ExceptionHandler.class.isAssignableFrom(handlerClazz))
							addHandler(exceptionClass, (ExceptionHandler) handlerClazz.newInstance());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addHandler(String exceptionClassName, ExceptionHandler handler) throws Exception {
		handlerMap.put(exceptionClassName, handler);

		Class exceptionClass = ClassFactory.loadClass(exceptionClassName);
		if (Throwable.class.isAssignableFrom(exceptionClass)) {
			exceptionClassMap.put(exceptionClassName, exceptionClass);
		}
	}

	/**
	 * 找到最近继承层级Throwable父类对应的处理类
	 *
	 * @param exceptionClass
	 * @return
	 */
	private ExceptionHandler findLatestSuperClassHandle(Class<? extends Throwable> exceptionClass) {
		Class<?> superclass = exceptionClass.getSuperclass();

		if (superclass != null && Throwable.class.isAssignableFrom(superclass)) {
			for (String key : exceptionClassMap.keySet()) {
				Class<? extends Throwable> value = exceptionClassMap.get(key);
				if (value == superclass) {
					return handlerMap.get(key);
				}
			}
			return findLatestSuperClassHandle((Class<? extends Throwable>) superclass);
		}
		return null;
	}
	/**
	 * 通过插件获取项目上定义的异常处理器
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public ErrorHandler getErrorHandler(Context context) throws Exception {
		String feErrorHandler = IServerConfig.get().getString(ErrorHandlerPluginIntf.Param_ErrorHandlerPlugin,null);
		if(!CmnUtil.isStringEmpty(feErrorHandler)) {
			try {
				Class<? extends ErrorHandlerPluginIntf> clazz = ClassFactory.loadClass(feErrorHandler);
				if(CellIntf.class.isAssignableFrom(clazz)) {
					return Cells.get(clazz).getErrorHandler(context);
				}else {
					return clazz.newInstance().getErrorHandler(context);
				}
			}catch (Exception e) {
				Tracer tracer = TraceUtil.getCurrentTracer();
				tracer.debug(ExceptionHandlerFactory.class.getSimpleName(), ToolUtilities.getFullExceptionMessage(e));
			}
		}
		return null;
	}


	public ExceptionHandleResult handle(Class serviceClass,PanelContext context, Throwable e) throws Exception {
		if(FeDebugUtil.isSimulateFrontEnd(context)) {
			ExceptionHandleResult result = new ExceptionHandleResult().setException(e);
			return result;
		}
		ErrorHandler handler = getErrorHandler(context);
		if(handler == null)
			handler = ProxyUtil.getErrorHandler(serviceClass);
		if(handler != null) {
			e = handler.handle(e);
		}
		ExceptionHandleResult result = innerHandle(context, e);
		result.setOrgException(e);
		return result;
	}
	
	public ExceptionHandleResult innerHandle(PanelContext context, Throwable e) throws Exception {
		if (!_isDebug) {
			Class<? extends Throwable> exceptionClass = e.getClass();
			String key = exceptionClass.getName();

			ExceptionHandler handler = null;
			if (handlerMap.containsKey(key)) {
				handler = handlerMap.get(key);
			} else {
				handler = findLatestSuperClassHandle(exceptionClass);
			}

			if (handler != null) {
				Throwable exp =  handler.handle(context, e);
				ExceptionHandleResult result = handler.getHandleResult();
				if(result == null) {
					result = new ExceptionHandleResult().setErrorType(ExceptionHandlerType.error).setException(exp);
				}
				return result;
			} else {
				e.printStackTrace();
				ToolUtilities.error(context.getCurrentPanelGlobalKey(), e);
//			String message = CmnUtil.getString(e.getMessage(),"");
//			String detailMessage = ToolUtilities.getFullExceptionStack(e);
//			PopToast.error(context, FeI18n.RUNTIME_ERROR, detailMessage);
				ExceptionHandleResult result = new ExceptionHandleResult().setErrorType(ExceptionHandlerType.error).setException(e);
				return result;
			}
		}
		ExceptionHandleResult result = new ExceptionHandleResult().setErrorType(ExceptionHandlerType.error).setException(e);
		return result;
	}
}
