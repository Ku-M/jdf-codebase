package fe.util.intf;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.kwaidoo.ms.tool.CmnUtil;
import com.leavay.common.util.ToolUtilities;
import com.leavay.common.util.javac.ClassFactory;
import com.leavay.dfc.gui.LvUtil;
import com.leavay.dfc.gui.LvUtil.AutoTracer;

import cell.CellIntf;
import cell.cmn.IRequestLogPlugin;
import cell.cmn.IRequestLogService;
import cell.fe.IFeService;
import cell.fe.MicCaptureService;
import cell.fe.cmn.IFeCmnService;
import cell.fe.cmn.IFeServicePlugin;
import cmn.exception.BaseException;
import cmn.util.NullUtil;
import cmn.util.TraceUtil;
import cmn.util.Tracer;
import fe.cmn.app.Context;
import fe.cmn.app.ability.QueryPopContextStack;
import fe.cmn.audio.AudioFrameDto;
import fe.cmn.cascader.CascaderInterface;
import fe.cmn.cascader.CascaderNodeDto;
import fe.cmn.cascader.CascaderNodeQuerierContext;
import fe.cmn.cascader.CascaderQuerier;
import fe.cmn.data.BinPojo;
import fe.cmn.data.FePojo;
import fe.cmn.data.PairDto;
import fe.cmn.data.ServiceParamDto;
import fe.cmn.editor.SelectEditorInterface;
import fe.cmn.editor.SelectEditorQuerier;
import fe.cmn.editor.SelectEditorQuerierContext;
import fe.cmn.editor.TextEditorInterface;
import fe.cmn.editor.TextEditorQuerierContext;
import fe.cmn.editor.TextEditorServiceParamDto;
import fe.cmn.editor.TextEditorValueVerifyResultDto;
import fe.cmn.event.EventDto;
import fe.cmn.event.EventInterface;
import fe.cmn.graph.GraphDataDto;
import fe.cmn.graph.GraphInterface;
import fe.cmn.graph.GraphQuerier;
import fe.cmn.graph.GraphQuerierContext;
import fe.cmn.listView.ListViewInterface;
import fe.cmn.listView.ListViewItemDto;
import fe.cmn.listView.ListViewItemQuerier;
import fe.cmn.listView.ListViewQuerierContext;
import fe.cmn.menu.MenuItemDto;
import fe.cmn.navMenu.NavMenuInterface;
import fe.cmn.navMenu.NavMenuNodeDto;
import fe.cmn.navMenu.NavMenuNodeQuerier;
import fe.cmn.navMenu.NavMenuNodeQuerierContext;
import fe.cmn.panel.LazyPanelBuilder;
import fe.cmn.panel.LazyPanelDto;
import fe.cmn.panel.LazyPanelInterface;
import fe.cmn.panel.PanelBuilder;
import fe.cmn.panel.PanelContext;
import fe.cmn.panel.PanelDto;
import fe.cmn.panel.PanelInfo;
import fe.cmn.panel.PanelInterface;
import fe.cmn.panel.ability.ConvertPanelContext;
import fe.cmn.panel.ability.GetExtListener;
import fe.cmn.panel.ability.QueryBinaryData;
import fe.cmn.panel.ability.QueryParentPanel;
import fe.cmn.panel.ability.SetBinaryData;
import fe.cmn.panel.chart.ChartAxisData;
import fe.cmn.panel.chart.ChartContext;
import fe.cmn.panel.chart.ChartInterface;
import fe.cmn.panel.chart.ChartSeriesDto;
import fe.cmn.panel.chart.ChartServiceParamDto;
import fe.cmn.studio.PanelDesignerExtendInterface;
import fe.cmn.studio.StudioWidgetOverlayDto;
import fe.cmn.studio.units.UnitDropInfoDto;
import fe.cmn.studio.units.UnitDropResultDto;
import fe.cmn.sys.FeTracker;
import fe.cmn.table.TableBuilder;
import fe.cmn.table.TableHeaderDto;
import fe.cmn.table.TableInterface;
import fe.cmn.table.TableQuerier;
import fe.cmn.table.TableQuerierContext;
import fe.cmn.table.TableRowsDto;
import fe.cmn.tree.TreeInterface;
import fe.cmn.tree.TreeMenuDto;
import fe.cmn.tree.TreeNodeDto;
import fe.cmn.tree.TreeNodeQuerier;
import fe.cmn.tree.TreeNodeQuerierContext;
import fe.cmn.treeTable.TreeTableHeaderDto;
import fe.cmn.treeTable.TreeTableInterface;
import fe.cmn.treeTable.TreeTableNodeQuerier;
import fe.cmn.treeTable.TreeTableNodeQuerierContext;
import fe.cmn.treeTable.TreeTableRowsDto;
import fe.cmn.widget.ExtListenerDto;
import fe.cmn.widget.ListenerDto;
import fe.cmn.widget.ListenerInterface;
import fe.cmn.widget.WidgetDto;
import fe.util.FeCallbackPool;
import fe.util.FeDebugUtil;
import fe.util.OperateTransaction;
import fe.util.WidgetParamClassCache;
import fe.util.component.Component;
import fe.util.component.callback.ComponentCallback;
import fe.util.component.dto.CallbackLogDto;
import fe.util.component.dto.DebugLogDto;
import fe.util.component.dto.FeCmnEvent;
import fe.util.component.dto.FeDeliverData;
import fe.util.component.dto.RequestLogDto;
import fe.util.component.dto.TreeNodeExtraInfo;
import fe.util.component.extlistener.CommandCallbackListener;
import fe.util.component.param.CallBackSetting;
import fe.util.component.param.WidgetParam;
import fe.util.exception.handler.ExceptionHandleResult;
import fe.util.exception.handler.ExceptionHandlerFactory;
import fe.util.i18n.FeUtilConst;
import flutter.rpc.CRpcTraceRow;

public interface ServiceIntf extends CellIntf, PanelInterface, TreeInterface, TableInterface, TreeTableInterface, GraphInterface, ListViewInterface, ListenerInterface, EventInterface
		, SelectEditorInterface, CascaderInterface, LazyPanelInterface, NavMenuInterface, TextEditorInterface, PanelDesignerExtendInterface,MicCaptureService,ChartInterface {
	default ExceptionHandleResult handleException(PanelContext context, Throwable e) throws Exception {
		ExceptionHandlerFactory factory = ExceptionHandlerFactory.get();
		ExceptionHandleResult handleResult = factory.handle(getClass(),context, e);
		return handleResult;
	}
	/**
	 * 最终在检查异常是否已处理，未处理的抛出运行异常
	 * @param result
	 * @throws Exception
	 */
	default void finalHandleResult(ExceptionHandleResult result) throws Exception{
		if (!result.isHandled()) {
			Throwable exp = result.getException();
			ToolUtilities.error(ServiceIntf.class.getSimpleName(), exp);
			throw exp instanceof Exception ? (Exception) exp : new RuntimeException(exp);
		}
	}
	
	default FeTracker getFeTracker(Context context) throws Exception {
		if(IFeCmnService.get().isEnableFeTracker()) {
			ThreadLocal<FeTracker> _currTracker = (ThreadLocal<FeTracker>)ToolUtilities.getStaticFieldValue(FeTracker.class,"_currTracker");
			FeTracker tracker = _currTracker.get();
			if(tracker == null) {
				tracker = FeTracker.applyNew();
				Tracer tracer = TraceUtil.getCurrentTracer();
				tracer.debug("未找到前端日志追踪器，重新初始化：" +tracker);
			}
			return tracker;
		}
		return null;
	}
	
	default void setCurrentOperateTransaction(FeDeliverData feData,boolean newTransaction) {
		String opTransId = feData == null ? null : feData.getOpTransId();
		String lastOpTransId = feData == null ? null : feData.getLastOpTransId();
		setCurrentOperateTransaction(opTransId, lastOpTransId, newTransaction);
	}
	
	default void setCurrentOperateTransaction(TreeNodeExtraInfo extraInfo,boolean newTransaction) {
		String opTransId = extraInfo == null ? null : extraInfo.getOpTransId();
		String lastOpTransId = extraInfo == null ? null : extraInfo.getLastOpTransId();
		setCurrentOperateTransaction(opTransId, lastOpTransId, newTransaction);
	}
	
	default void setCurrentOperateTransaction(FeCmnEvent event,boolean newTransaction) {
		String opTransId = event == null ? null : event.getOpTransId();
		String lastOpTransId = event == null ? null : event.getLastOpTransId();
		setCurrentOperateTransaction(opTransId, lastOpTransId, newTransaction);
	}
	
	default void setCurrentOperateTransaction(WidgetParam widgetParam,boolean newTransaction) {
		String opTransId = widgetParam == null ? null : widgetParam.getOpTransId();
		String lastOpTransId = widgetParam == null ? null : widgetParam.getLastOpTransId();
		setCurrentOperateTransaction(opTransId, lastOpTransId, newTransaction);
	}
	
	default void setCurrentOperateTransaction(String opTransId,String lastOpTransId,boolean newTransaction) {
		if(newTransaction) {
			OperateTransaction.setTransId(ToolUtilities.allockUUIDWithUnderline());
			OperateTransaction.setLastTransId(opTransId);
		}else {
			if(!CmnUtil.isStringEmpty(opTransId)) {
				OperateTransaction.setTransId(opTransId);
				OperateTransaction.setLastTransId(lastOpTransId);
			}else {
				OperateTransaction.setTransId(ToolUtilities.allockUUIDWithUnderline());
			}
		}
		OperateTransaction.clearCategory();
	}
	
	default void setRequestCateogys(List<String> categorys) {
		if(CmnUtil.isCollectionEmpty(categorys))
			return;
		OperateTransaction.setCategoryIfNotExist(categorys.toArray(new String[] {}));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	default Class loadClass(String className, Class inheritClazz) throws Exception {
		if (CmnUtil.isStringEmpty(className))
			throw new Exception("className Can not be null!");
		Class clazz = ClassFactory.getValidClassLoader().loadClass(className, false);
		if (inheritClazz != null && !inheritClazz.isAssignableFrom(clazz)) {
			throw new Exception(className + " is not inherit from " + inheritClazz.getName());
		}
		return clazz;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	default <T> T newInstance(String className, Class<T> inheritClazz, Object... params) throws Exception {
		Class clazz = loadClass(className, inheritClazz);
		Constructor constructor = ToolUtilities.getBestConstructor(clazz, params);
		Object instance = constructor.newInstance(params);
		return (T) instance;
	}

	@SuppressWarnings("rawtypes")
	@Override
	default PanelDto buildPanel(PanelBuilder builder, PanelContext context) throws Exception {
		AutoTracer tracer = getDebugTracer(context);
		FeTracker tracker = getFeTracker(context);
		WidgetParam widgetParam = null;
		Throwable exp = null;
		long start = System.currentTimeMillis();
		try {
			FeDeliverData querierData = (FeDeliverData) builder.getBinaryData();
			setCurrentOperateTransaction(querierData,true);
			String invokeClass = querierData.getInvokeClass();
			PanelInterface inst = newInstance(invokeClass, PanelInterface.class);
			if (inst instanceof Component) {
				((Component) inst).setPanelContext(context);
				setRequestCateogys(((Component) inst).getRequestCategorys(context));
			}
			return inst.buildPanel(builder, context);
		} catch (Throwable e) {
			ExceptionHandleResult result = handleException(context, e);
			exp = result.getOrgException();
			finalHandleResult(result);
		} finally {
			logTrace(tracer,tracker, context, widgetParam, context.getCurrentPanelWidgetId(), "buildPanel", start,exp);
		}
		return null;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	default List<TreeNodeDto> queryChild(TreeNodeQuerier querier, TreeNodeQuerierContext context) throws Exception {
		AutoTracer tracer = getDebugTracer(context);
		FeTracker tracker = getFeTracker(context);
		WidgetParam widgetParam = null;
		Throwable exp = null;
		long start = System.currentTimeMillis();
		try {
			FeDeliverData querierData = (FeDeliverData) querier.getBinaryData();
			setCurrentOperateTransaction(querierData,false);
			String widgetIdOfWidgetParam = querierData.getWidgetIdOfWidgetParam();
			String invokeClass = querierData.getInvokeClass();

			TreeInterface inst = newInstance(invokeClass, TreeInterface.class);
			if (inst instanceof Component) {
				widgetParam = getPanelWidgetParam(context, widgetIdOfWidgetParam);
				((Component) inst).setWidgetParam(widgetParam);
				((Component) inst).setPanelContext(context);
				setRequestCateogys(((Component) inst).getRequestCategorys(context));
			}
			return inst.queryChild(querier, context);
		} catch (Throwable e) {
			ExceptionHandleResult result = handleException(context, e);
			exp = result.getOrgException();
			finalHandleResult(result);
		} finally {
			logTrace(tracer, tracker,context, widgetParam, context.getCurrentPanelWidgetId(), "queryChild", start,exp);
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	default TreeMenuDto getContextMenu(TreeNodeDto feNode, List<TreeNodeDto> otherSelected, TreeNodeQuerierContext context) throws Exception {
		AutoTracer tracer = getDebugTracer(context);
		FeTracker tracker = getFeTracker(context);
		WidgetParam widgetParam = null;
		Throwable exp = null;
		long start = System.currentTimeMillis();
		try {
			TreeNodeExtraInfo extraInfo = (TreeNodeExtraInfo) feNode.getBinaryData();
			setCurrentOperateTransaction(extraInfo,true);
			String invokeClass = extraInfo.getInvokeClass();
			TreeInterface inst = newInstance(invokeClass, TreeInterface.class);
			if (inst instanceof Component) {
				widgetParam = getPanelWidgetParam(context, null);
				((Component) inst).setWidgetParam(widgetParam);
				((Component) inst).setPanelContext(context);
				setRequestCateogys(((Component) inst).getRequestCategorys(context));
			}
			return inst.getContextMenu(feNode, otherSelected, context);
		} catch (Throwable e) {
			ExceptionHandleResult result = handleException(context, e);
			exp = result.getOrgException();
			finalHandleResult(result);
		} finally {
			logTrace(tracer, tracker,context, widgetParam, context.getCurrentPanelWidgetId(), "getContextMenu", start,exp);
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	default TreeNodeDto reloadNode(TreeNodeDto feNode, TreeNodeQuerierContext context) throws Exception {
		AutoTracer tracer = getDebugTracer(context);
		FeTracker tracker = getFeTracker(context);
		WidgetParam widgetParam = null;
		Throwable exp = null;
		long start = System.currentTimeMillis();
		try {
			TreeNodeExtraInfo extraInfo = (TreeNodeExtraInfo) feNode.getBinaryData();
			setCurrentOperateTransaction(extraInfo,false);
			String invokeClass = extraInfo.getInvokeClass();

			TreeInterface inst = newInstance(invokeClass, TreeInterface.class);
			if (inst instanceof Component) {
				widgetParam = getPanelWidgetParam(context, null);
				((Component) inst).setWidgetParam(widgetParam);
				((Component) inst).setPanelContext(context);
				setRequestCateogys(((Component) inst).getRequestCategorys(context));
			}
			return inst.reloadNode(feNode, context);
		} catch (Throwable e) {
			ExceptionHandleResult result = handleException(context, e);
			exp = result.getOrgException();
			finalHandleResult(result);
		} finally {
			logTrace(tracer, tracker,context, widgetParam, context.getCurrentPanelWidgetId(), "reloadNode", start,exp);
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	default TableHeaderDto queryTableMeta(TableBuilder builder, TableQuerierContext context) throws Exception {
		AutoTracer tracer = getDebugTracer(context);
		FeTracker tracker = getFeTracker(context);
		WidgetParam widgetParam = null;
		Throwable exp = null;
		long start = System.currentTimeMillis();
		try {
			FeDeliverData querierData = (FeDeliverData) builder.getBinaryData();
			setCurrentOperateTransaction(querierData,false);
			String widgetIdOfWidgetParam = querierData.getWidgetIdOfWidgetParam();
			String invokeClass = querierData.getInvokeClass();

			TableInterface inst = newInstance(invokeClass, TableInterface.class);
			if (inst instanceof Component) {
				widgetParam = getPanelWidgetParam(context, widgetIdOfWidgetParam);
				((Component) inst).setWidgetParam(widgetParam);
				((Component) inst).setPanelContext(context);
				setRequestCateogys(((Component) inst).getRequestCategorys(context));
			}
			return inst.queryTableMeta(builder, context);
		} catch (Throwable e) {
			ExceptionHandleResult result = handleException(context, e);
			exp = result.getOrgException();
			finalHandleResult(result);
		} finally {
			logTrace(tracer, tracker,context, widgetParam, context.getCurrentPanelWidgetId(), "queryTableMeta", start,exp);
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	default TableRowsDto queryTableRows(TableBuilder builder, TableQuerier querier, TableQuerierContext context) throws Exception {
		AutoTracer tracer = getDebugTracer(context);
		FeTracker tracker = getFeTracker(context);
		Throwable exp = null;
		WidgetParam widgetParam = null;
		long startTime = System.currentTimeMillis();
		try{
			FeDeliverData querierData = (FeDeliverData) querier.getBinaryData();
			setCurrentOperateTransaction(querierData,false);
			String widgetIdOfWidgetParam = querierData.getWidgetIdOfWidgetParam();
			String invokeClass = querierData.getInvokeClass();

			TableInterface inst = newInstance(invokeClass, TableInterface.class);
			if (inst instanceof Component) {
				widgetParam = getPanelWidgetParam(context, widgetIdOfWidgetParam);
				((Component) inst).setWidgetParam(widgetParam);
				((Component) inst).setPanelContext(context);
				setRequestCateogys(((Component) inst).getRequestCategorys(context));
			}
			return inst.queryTableRows(builder, querier, context);
		} catch (Throwable e) {
			ExceptionHandleResult result = handleException(context, e);
			exp = result.getOrgException();
			finalHandleResult(result);
		}finally {
			logTrace(tracer, tracker,context, widgetParam, context.getCurrentPanelWidgetId(), "queryTableRows", startTime, exp);
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	default TreeTableHeaderDto queryTreeTableMeta(TableBuilder builder, TreeTableNodeQuerierContext context) throws Exception {
		AutoTracer tracer = getDebugTracer(context);
		FeTracker tracker = getFeTracker(context);
		WidgetParam widgetParam = null;
		Throwable exp = null;
		long start = System.currentTimeMillis();
		try {
			FeDeliverData querierData = (FeDeliverData) builder.getBinaryData();
			setCurrentOperateTransaction(querierData,false);
			String widgetIdOfWidgetParam = querierData.getWidgetIdOfWidgetParam();
			String invokeClass = querierData.getInvokeClass();

			TreeTableInterface inst = newInstance(invokeClass, TreeTableInterface.class);
			if (inst instanceof Component) {
				widgetParam = getPanelWidgetParam(context, widgetIdOfWidgetParam);
				((Component) inst).setWidgetParam(widgetParam);
				((Component) inst).setPanelContext(context);
				setRequestCateogys(((Component) inst).getRequestCategorys(context));
			}
			return inst.queryTreeTableMeta(builder, context);
		} catch (Throwable e) {
			ExceptionHandleResult result = handleException(context, e);
			exp = result.getOrgException();
			finalHandleResult(result);
		}finally {
			logTrace(tracer, tracker,context, widgetParam, context.getCurrentPanelWidgetId(), "queryTreeTableMeta", start, exp);
		}
		return null;
	}
	
	default AutoTracer getDebugTracer(Context panelContext) throws Exception {
//		if(isEnableDebugLog(panelContext)) {
			return LvUtil.newAutoTracer();
//		}
//		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	default TreeTableRowsDto queryTreeTableRows(TableBuilder builder, TreeTableNodeQuerier querier, TreeTableNodeQuerierContext context) throws Exception {
		AutoTracer tracer = getDebugTracer(context);
		FeTracker tracker = getFeTracker(context);
		Throwable exp = null;
		WidgetParam widgetParam = null;
		long startTime = System.currentTimeMillis();
		try {
			FeDeliverData querierData = (FeDeliverData) querier.getBinaryData();
			setCurrentOperateTransaction(querierData,false);
			String widgetIdOfWidgetParam = querierData.getWidgetIdOfWidgetParam();
			String callClass = querierData.getInvokeClass();

			TreeTableInterface inst = newInstance(callClass, TreeTableInterface.class);
			if (inst instanceof Component) {
				widgetParam = getPanelWidgetParam(context, widgetIdOfWidgetParam);
				((Component) inst).setWidgetParam(widgetParam);
				((Component) inst).setPanelContext(context);
				setRequestCateogys(((Component) inst).getRequestCategorys(context));
			}
			return inst.queryTreeTableRows(builder, querier, context);
		} catch (Throwable e) {
			ExceptionHandleResult result = handleException(context, e);
			exp = result.getOrgException();
			finalHandleResult(result);
		}finally {
			logTrace(tracer, tracker,context, widgetParam, context.getCurrentPanelWidgetId(), "queryTreeTableRows", startTime, exp);
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	default GraphDataDto loadData(GraphQuerier querier, GraphQuerierContext context) throws Exception {
		AutoTracer tracer = getDebugTracer(context);
		FeTracker tracker = getFeTracker(context);
		WidgetParam widgetParam = null;
		Throwable exp = null;
		long start = System.currentTimeMillis();
		try {
			FeDeliverData querierData = (FeDeliverData) querier.getBinaryData();
			setCurrentOperateTransaction(querierData,false);
			String widgetIdOfWidgetParam = querierData.getWidgetIdOfWidgetParam();
			String invokeClass = querierData.getInvokeClass();

			GraphInterface inst = newInstance(invokeClass, GraphInterface.class);
			if (inst instanceof Component) {
				widgetParam = getPanelWidgetParam(context, widgetIdOfWidgetParam);
				((Component) inst).setWidgetParam(widgetParam);
				((Component) inst).setPanelContext(context);
				setRequestCateogys(((Component) inst).getRequestCategorys(context));
			}
			return inst.loadData(querier, context);
		} catch (Throwable e) {
			ExceptionHandleResult result = handleException(context, e);
			exp = result.getOrgException();
			finalHandleResult(result);
		}finally {
			logTrace(tracer, tracker,context, widgetParam, context.getCurrentPanelWidgetId(), "loadData", start, exp);
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	default List<ListViewItemDto> queryList(ListViewItemQuerier querier, ListViewQuerierContext context) throws Exception {
		AutoTracer tracer = getDebugTracer(context);
		FeTracker tracker = getFeTracker(context);
		WidgetParam widgetParam = null;
		Throwable exp = null;
		long start = System.currentTimeMillis();
		try {
			FeDeliverData querierData = (FeDeliverData) querier.getBinaryData();
			setCurrentOperateTransaction(querierData,false);
			String widgetIdOfWidgetParam = querierData.getWidgetIdOfWidgetParam();
			String invokeClass = querierData.getInvokeClass();

			ListViewInterface inst = newInstance(invokeClass, ListViewInterface.class);
			if (inst instanceof Component) {
				widgetParam = getPanelWidgetParam(context, widgetIdOfWidgetParam);
				((Component) inst).setWidgetParam(widgetParam);
				((Component) inst).setPanelContext(context);
				setRequestCateogys(((Component) inst).getRequestCategorys(context));
			}
			return inst.queryList(querier, context);
		} catch (Throwable e) {
			ExceptionHandleResult result = handleException(context, e);
			exp = result.getOrgException();
			finalHandleResult(result);
		}finally {
			logTrace(tracer, tracker,context, widgetParam, context.getCurrentPanelWidgetId(), "queryList", start, exp);
		}
		return null;
	}
	
	default boolean isEnableDebugLog(Context panelContext) throws Exception {
		return FeDebugUtil.isEnableDebug(panelContext);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	default Object onListener(ListenerDto listener, PanelContext panelContext, WidgetDto source) throws Exception {
		AutoTracer tracer = getDebugTracer(panelContext);
		FeTracker tracker = getFeTracker(panelContext);
		Tracer _tracer = TraceUtil.getCurrentTracer();
		WidgetParam widgetParam = null;
		Throwable exp = null;
		long start = System.currentTimeMillis();
		try {
			_tracer.debug("开始响应监听。");
			FeDeliverData extraData = (FeDeliverData) listener.getBinaryData();
			setCurrentOperateTransaction(extraData,true);
			String widgetId = extraData.getWidgetIdOfWidgetParam();
			String invokeClass = extraData.getInvokeClass();

			widgetParam = getPanelWidgetParam(panelContext, widgetId);

			if (widgetParam != null) {
				if (CmnUtil.isStringEmpty(invokeClass)) {
					invokeClass = widgetParam.getInvokeClass();
				} else if (!CmnUtil.isStringEqual(widgetParam.getInvokeClass(), invokeClass)) {
					if(FeDebugUtil.isEnableDebug(panelContext)) {
						_tracer.warning("ServiceIntf.onListener","忽略监听操作，监听器设置的invokeClass与组件参数中的invokeClass不一致！widgetParam.getInvokeClass() = " + widgetParam.getInvokeClass() + ", invokeClass = " + invokeClass);
					}
					return null;
				}
			}

			ListenerInterface inst = newInstance(invokeClass, ListenerInterface.class);
			if (inst instanceof Component) {
				((Component) inst).setWidgetParam(widgetParam);
				((Component) inst).setPanelContext(panelContext);
				setRequestCateogys(((Component) inst).getRequestCategorys(panelContext));
				handleListenerCallback((Component) inst, widgetParam, listener, panelContext, source, extraData, FeUtilConst.CALL_BACK_ON_BEFORE,null);
			}
			Object result = inst.onListener(listener, panelContext, source);
			if (inst instanceof Component) {
				handlerCommandCallback(listener,panelContext, widgetParam,listener.getServiceCommand(), result);
				//执行监听命令完成后的回调，需要在onListener内部的方法缓存执行结果数据
				handleListenerCallback((Component) inst, widgetParam, listener, panelContext, source, extraData, FeUtilConst.CALL_BACK_ON_AFTER,result);
			}
			_tracer.debug("结束响应监听。");
			return result;
		} catch (Throwable e) {
			ExceptionHandleResult result = handleException(panelContext, e);
			exp = result.getOrgException();
			finalHandleResult(result);
		} finally {
			logTrace(tracer, tracker,panelContext, widgetParam, listener.getSourceWidgetId(), listener.getServiceCommand(), start,exp);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	default void logTrace(AutoTracer tracer,FeTracker tracker,Context panelContext,WidgetParam widgetParam,String sourceWigetId,String command,long startTime,Throwable exp) throws Exception {
		if(tracer != null) {
			long end = System.currentTimeMillis();
			String msg = tracer.getTrace();
			DebugLogDto debugLog = new DebugLogDto();
			debugLog.setOpTransId(OperateTransaction.getTransId())
			.setLastOpTransId(OperateTransaction.getLastTransId());
			List<String> categorys = OperateTransaction.getCategory();
			if(!CmnUtil.isCollectionEmpty(categorys)) {
				if(categorys.size() >= 1)
					debugLog.setCategory1(categorys.get(0));
				if(categorys.size() >= 2)
					debugLog.setCategory2(categorys.get(1));
				if(categorys.size() >= 3)
					debugLog.setCategory3(categorys.get(2));
			}
			debugLog.setServiceCell(this.getClass().getName());
			String widgetId = null;
			if(panelContext instanceof PanelContext) {
				debugLog.setPanelGlobalKey(((PanelContext) panelContext).getCurrentPanelGlobalKey());
				widgetId = ((PanelContext) panelContext).getCurrentPanelWidgetId();
			}
			if(widgetParam != null) {
				if(widgetParam.getWidgetId() != null)
					widgetId = widgetParam.getWidgetId();
				debugLog.setComponentClass(widgetParam.getInvokeClass());
			}
			debugLog.setWidgetId(widgetId);
			debugLog.setTrace(msg);
			debugLog.setStartTime(startTime).setEndTime(end).setCostTime((end-startTime));
			debugLog.setCommand(command).setSourceWidgetId(sourceWigetId);
			LinkedList<DebugLogDto> hisMsg = null;
			try {
				hisMsg = (LinkedList<DebugLogDto>)panelContext.getCacheValue("$Trace");
			}catch (Exception e) {
			}
			if(exp != null) {
				if(exp instanceof BaseException) {
					debugLog.setErrorCode(((BaseException) exp).getErrorCode());
					debugLog.setErrorLevel(((BaseException) exp).getErrorLevel().name());
					debugLog.setErrorBrief(exp.getMessage());
				}
				debugLog.setError(ToolUtilities.getFullExceptionStack(exp));
			}
			if(isEnableDebugLog(panelContext)) {
				if(hisMsg == null)
					hisMsg = new LinkedList<>();
				if(hisMsg.size() < 200)
					hisMsg.push(debugLog);
				else {
					hisMsg.removeLast();
					hisMsg.push(debugLog);
				}
				panelContext.putCacheValue("$Trace", hisMsg);
			}
			if(tracker != null) {
				List<CallbackLogDto> callbackLogs = new ArrayList<>();
				List<CRpcTraceRow> traceRows = tracker.getTracerRows();
				System.out.println(ToolUtilities.logString(traceRows, true));
				for(CRpcTraceRow traceRow : NullUtil.get(traceRows)) {
					CallbackLogDto callbackLog = new CallbackLogDto();
					callbackLog.setStartTime(traceRow.getTimeTag()).setEndTime(traceRow.getEndTime()).setMsg(traceRow.getMsg())
					.setDetail(traceRow.getDetail());
					if(traceRow.getExtend() != null) {
						callbackLog.setExtend(traceRow.getExtend().toString());
					}
					callbackLogs.add(callbackLog);
				}
				debugLog.setCallbackLogs(callbackLogs);
			}
			logRequest(panelContext, debugLog);
			tracer.close();
			if(tracker != null) {
				try {
				tracker.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	default void logRequest(Context panelContext,DebugLogDto debugLog) throws Exception {
		RequestLogDto requestLog = new RequestLogDto();
		requestLog = IRequestLogPlugin.get().appendBussinessInfo(panelContext,requestLog);
		IRequestLogPlugin.get().appendClientInfo(panelContext, null, requestLog);
		String serviceCell = debugLog.getServiceCell();
		if(serviceCell.endsWith("EmptyImplement")) {
			serviceCell = serviceCell.replaceAll("EmptyImplement", "");
		}
		requestLog.setTransactionId(OperateTransaction.getTransId())
		.setLastTransactionId(OperateTransaction.getLastTransId())
		.setCategory1(debugLog.getCategory1())
		.setCategory2(debugLog.getCategory2())
		.setCategory3(debugLog.getCategory3());
		requestLog.setServiceCell(serviceCell).setPanelGlobalKey(debugLog.getPanelGlobalKey())
		.setComponentClass(debugLog.getComponentClass()).setWidgetId(debugLog.getWidgetId())
		.setSourceWidgetId(debugLog.getSourceWidgetId()).setCommand(debugLog.getCommand())
		.setTrace(debugLog.getTrace()).setBackendStartTime(debugLog.getStartTime()).setBackendEndTime(debugLog.getEndTime()).setBackendCost(debugLog.getCostTime())
		.setErrorCode(debugLog.getErrorCode()).setErrorLevel(debugLog.getErrorLevel()).setErrorBrief(debugLog.getErrorBrief()).setError(debugLog.getError())
		.setCallbackLogs(debugLog.getCallbackLogs());
		IRequestLogService.get().log(requestLog);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	default void handlerCommandCallback(ListenerDto sourceLsnr,PanelContext panelContext,WidgetParam widgetParam,String command,Object result) throws Exception {
		String callbackCommand = command +"_CALLBACK";
		boolean isDebug = isEnableDebugLog(panelContext);
		Tracer tracer  =TraceUtil.getCurrentTracer();
		if(isDebug)LvUtil.trace("处理" + command + "的回调指令");
		ExtListenerDto lsnr = null;
		try{
			long start = System.currentTimeMillis();
			if(IFeServicePlugin.get().enableCommandCallbackFormChannel()) {
				lsnr = widgetParam.searchCommandCallbackLsnr(callbackCommand);
			}else {
				lsnr = GetExtListener.get(panelContext, panelContext.getCurrentPanelWidgetId(), callbackCommand);
			}
			long end = System.currentTimeMillis();
			
			if(isDebug) {
				LvUtil.trace("GetExtListener执行耗时："+(end-start)+"ms");
			}
		}catch (Exception e) {
			if(isDebug)LvUtil.trace("未找到[" + command + "]的回调指令，无需回调处理。");
			return;
		}
		if(lsnr instanceof CommandCallbackListener) {
			if (lsnr != null && lsnr.getListener() != null) {
				String invokeClass = "";
				if(lsnr.getListener().getServerExecutor() != null) {
					FeDeliverData cmdExtraData = (FeDeliverData) lsnr.getListener().getBinaryData();
					invokeClass = cmdExtraData.getInvokeClass();
				}
				
				PanelContext listenPanelContext = panelContext.cloneWithChannel();
				if(((CommandCallbackListener) lsnr).isPopupCallback()) {
					if(isDebug)LvUtil.trace("回调运行环境筹备：回调指令[" + lsnr.getName() + "]是弹窗回调，开始查找父级弹窗的面板上下文。");
					long start = System.currentTimeMillis();
					List<PanelContext> popContexts = QueryPopContextStack.query(panelContext.getChannel());
					listenPanelContext = popContexts.get(popContexts.size()-2);
					listenPanelContext.setChannel(panelContext.getChannel());
					long end = System.currentTimeMillis();
					if(isDebug)LvUtil.trace("QueryPopContextStack执行耗时："+(end-start)+"ms");
				}else {
					if(!((CommandCallbackListener) lsnr).isInOnePanel()) {
						if(isDebug)LvUtil.trace("回调运行环境筹备：回调指令[" + lsnr.getName() + "]的组件和当前指令组件不在一个panel，开始查找父级面板上下文。");
						//如果回调目标组件不是一个面板，需要找到当前面板的父面板
						long start = System.currentTimeMillis();
						List<PanelInfo> parentPanels = QueryParentPanel.query(panelContext);
						long end = System.currentTimeMillis();
						if(isDebug)LvUtil.trace("QueryParentPanel执行耗时："+(end-start)+"ms");
						for(int i = parentPanels.size()-1;i>=0;i--) {
							PanelInfo parentPanel = parentPanels.get(i);
							if(parentPanel.getPanelClass().equals(LazyPanelDto.class.getName())) {
								if(parentPanels.size() == 1) {
									//如果是懒加载面板，这里因为没找到往上的面板，所以重新查一遍
									try {
										long start2 = System.currentTimeMillis();
										listenPanelContext = ConvertPanelContext.convert(panelContext, "../"+parentPanel.getPanelWidgetId());
										List<PanelInfo> lazyParentPanels = QueryParentPanel.query(listenPanelContext);
										if(lazyParentPanels.size() > 0) {
											parentPanel = lazyParentPanels.get(lazyParentPanels.size()-1);
											listenPanelContext = ConvertPanelContext.convert(panelContext, "../"+parentPanel.getPanelWidgetId());
										}
										long end2 = System.currentTimeMillis();
										if(isDebug)LvUtil.trace("懒加载面板，QueryParentPanel执行耗时："+(end2-start2)+"ms");
									}catch (Exception e) {
										e.printStackTrace();
										if(isDebug)LvUtil.trace("回调运行环境筹备出错：" + ToolUtilities.getFullExceptionStack(e));
										return;
									}
								}
							}else {
								try {
									long start2 = System.currentTimeMillis();
									listenPanelContext = ConvertPanelContext.convert(panelContext, "../"+parentPanel.getPanelWidgetId());
									long end2 = System.currentTimeMillis();
									if(isDebug)LvUtil.trace("ConvertPanelContext执行耗时：" + (end2-start2)+"ms");
									break;
								}catch (Exception e) {
									e.printStackTrace();
									if(isDebug)LvUtil.trace("回调运行环境筹备出错：" + ToolUtilities.getFullExceptionStack(e));
									return;
								}
							}
						}
					}
						
				}
				if(result != null) {
					if(lsnr.getListener().getServerExecutor() != null) {
						FeDeliverData cmdExtraData = (FeDeliverData) lsnr.getListener().getBinaryData();
						cmdExtraData.setData(result);
						lsnr.getListener().setBinaryData(cmdExtraData);
					}else if(lsnr.getListener().getEventExecutor() != null) {
						EventDto event = lsnr.getListener().getEventExecutor().getEvent();
						if(event instanceof FeCmnEvent) {
							FeCmnEvent cmnEvent = (FeCmnEvent) event;
							if(cmnEvent.getInvokeParams() == null) {
								cmnEvent.setInvokeParams(new Object[] {result});
							}
							cmnEvent.setSelfBinaryData();
						}
						
					}
				}
				if(isDebug)LvUtil.trace("触发回调指令[" + lsnr.getName() + "]执行！");
				long start2 = System.currentTimeMillis();
				ToolUtilities.setFieldValue(lsnr.getListener(), "sourceWidgetId", sourceLsnr.getSourceWidgetId());
				//同步触发
				if(lsnr.getListener().isSynchronize())
					IFeService.get().fireListener(listenPanelContext, lsnr);
				else {
					TraceUtil.info("异步调用指令！");
					//异步触发
					ToolUtilities.asynCallFunction(IFeService.get(), "fireListener", listenPanelContext,lsnr);
				}
//				IFeService.get().fireListener(listenPanelContext, lsnr);
				long end2 = System.currentTimeMillis();
				if(isDebug)LvUtil.trace("回调指令执行耗时：" + (end2-start2)+"ms");
			}
		}
	}
	
	default boolean enableWidgetParamCache() {
		return true;
	}
	
	public static String getCacheWidgetParamKey(String widgetId) throws Exception {
		if(CmnUtil.isStringEmpty(widgetId))
			return null;
		return "$CacheWidgetParam_"+widgetId;
	}
	/**
	 * 设置面板的widgetParam，更新信道缓存以及前端面板的数据
	 * @param context
	 * @param widgetIdOfWidgetParam
	 * @param widgetParam
	 * @throws Exception
	 */
	default void setPanelWidgetParam(PanelContext context, String widgetIdOfWidgetParam,WidgetParam widgetParam) throws Exception {
		String cacheKey = getCacheWidgetParamKey(widgetIdOfWidgetParam);
		if(cacheKey != null) {
			context.putCacheValue(cacheKey, widgetParam);
		}
//		SetBinaryData.set(context, widgetIdOfWidgetParam, widgetParam);
		FeCallbackPool.add(context, new SetBinaryData().setBinPojo((BinPojo) new BinPojo().setBinaryData(widgetParam)).setWidgetId(widgetIdOfWidgetParam).setPanelGlobalKey(context.getCurrentPanelGlobalKey()));
	}
	/**
	 * 用于从组件或面板上获取 widgetParam
	 *
	 * @param context               页面上下文
	 * @param widgetIdOfWidgetParam 指定的用于获取 widgetParam 的 widgetId 可为空
	 * @return
	 * @throws Exception
	 */
	default WidgetParam getPanelWidgetParam(PanelContext context, String widgetIdOfWidgetParam) throws Exception {
		long start = System.currentTimeMillis();
		WidgetParam widgetParam = null;
		try {
			Object object = null;

//			if (context instanceof TreeNodeQuerierContext) {
//				object = ((TreeNodeQuerierContext) context).getTreeFepojo().getBinaryData();
//			} else if (context instanceof TableQuerierContext) {
//				object = ((TableQuerierContext) context).getTableFePojo().getBinaryData();
//			} else if (context instanceof ListViewQuerierContext) {
//				object = ((ListViewQuerierContext) context).getListViewFePojo().getBinaryData();
//			} else {
				// 指定了 widgetId 则从当前面板下的指定 widgetId 组件上取 widgetParam
				String widgetId = CmnUtil.isStringEmpty(widgetIdOfWidgetParam)
						? context.getCurrentPanelWidgetId() : widgetIdOfWidgetParam;
				if(enableWidgetParamCache()) {
					String cacheKey = ServiceIntf.getCacheWidgetParamKey(widgetId);
					if(FeDebugUtil.isEnableDebug(context)) {
						LvUtil.trace("widgetParam缓存key="+cacheKey);
					}
					if(cacheKey != null) {
						object = context.getPanelCacheValue(cacheKey);
						if(WidgetParamClassCache.isWidgetParamDirty(object)) {
							LvUtil.trace("widgetParam缓存已脏，重新获取="+cacheKey);
							object = null;
						}
					}
				}
				if(object == null){
					if(FeDebugUtil.isEnableDebug(context)) {
						LvUtil.trace("widgetParam未设置信道缓存，通过组件binaryData查询！");
					}
					if (context instanceof TreeNodeQuerierContext) {
						object = ((TreeNodeQuerierContext) context).getTreeFepojo().getBinaryData();
					} else if (context instanceof TableQuerierContext) {
						object = ((TableQuerierContext) context).getTableFePojo().getBinaryData();
					} else if (context instanceof ListViewQuerierContext) {
						object = ((ListViewQuerierContext) context).getListViewFePojo().getBinaryData();
					} else {
						object = QueryBinaryData.queryOne(context, widgetId);
					}
					if(object != null) {
						//重查后，将数据重新放到信道缓存中
						if(enableWidgetParamCache()) {
							String cacheKey = ServiceIntf.getCacheWidgetParamKey(widgetId);
							if(cacheKey != null) {
								context.putPanelCache(cacheKey, object);
							}
						}
					}
				}
//			}

			if (object instanceof WidgetParam) {
				widgetParam = (WidgetParam) object;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		if(FeDebugUtil.isEnableDebug(context)) {
			LvUtil.trace("getPanelWidgetParam = "+(end - start) + "ms");
		}
		return widgetParam;
	}

	@SuppressWarnings("rawtypes")
	default void handleListenerCallback(Component inst, WidgetParam widgetParam, ListenerDto listener, PanelContext panelContext, WidgetDto source, FeDeliverData data, String triggerTime,Object result) throws Exception {
		if (widgetParam == null) {
			return;
		}
		if (CmnUtil.isMapEmpty(widgetParam.getCallBackMap()))
			return;
		String cmd = listener.getServiceCommand();
		CallBackSetting callBackSetting = null;
		for (String cmdRegex : widgetParam.getCallBackMap().keySet()) {
			if (cmd.matches(cmdRegex)) {
				callBackSetting = widgetParam.getCallBackMap().get(cmdRegex);
			}
		}
		if (callBackSetting == null)
			return;
		for (PairDto<String, ComponentCallback> pair : callBackSetting.getCallBacks()) {
			if (CmnUtil.isStringEqual(pair.getKey(), triggerTime)) {
//				panelContext.callback(pair.getValue());
				//准备执行callback的上下文参数
				pair.getValue().setComponent(inst);
				pair.getValue().setListener(listener);
				pair.getValue().setPanelContext(panelContext);
				pair.getValue().setWidgetParam(widgetParam);
				pair.getValue().setReturnResult(result);
				pair.getValue().callback();
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	default void onEvent(EventDto event, PanelContext panelContext, WidgetDto source) throws Exception {
		AutoTracer tracer = getDebugTracer(panelContext);
		FeTracker tracker = getFeTracker(panelContext);
		WidgetParam widgetParam = null;
		Throwable exp = null;
		long start = System.currentTimeMillis();
		try {
			Object binaryData = event.getBinaryData();
			if (binaryData == null) {
				return;
			}

			String widgetId;
			String invokeClass;
			if (event instanceof FeCmnEvent) {
				FeCmnEvent extraData = (FeCmnEvent) binaryData;
				setCurrentOperateTransaction(extraData,false);
				widgetId = extraData.getWidgetId();
				invokeClass = extraData.getInvokeClass();
				event = extraData;
			} else {
				// FeDeliverData 传递数据方式
				if (binaryData instanceof FeDeliverData) {
					FeDeliverData extraData = (FeDeliverData) binaryData;
					setCurrentOperateTransaction(extraData,false);
					// 事件响应端所在面板 widgetId,若指定了组件则从组件上获取
					String widgetIdOfWidgetParam = extraData.getWidgetIdOfWidgetParam();
					widgetId = CmnUtil.isStringEmpty(widgetIdOfWidgetParam) ? panelContext.getCurrentPanelWidgetId() : widgetIdOfWidgetParam;

					// 1. 广播事件:此项为空,无指定响应面板类
					// 2. 主动事件:此项为指定响应面板类
					invokeClass = extraData.getInvokeClass();
				} else {
					// FIXME 旧有方式代码暂留
					EventDto paramEvent = (EventDto) ToolUtilities.copyFields(event, binaryData);
					if (paramEvent instanceof EventInterface) {
						EventInterface inst = (EventInterface) paramEvent;
						inst.onEvent(paramEvent, panelContext, source);
						return;
					} else {
						throw new Exception("Event " + paramEvent.getClass() + " has not implements EventInterface!");
					}
				}
			}

			widgetParam = getPanelWidgetParam(panelContext, widgetId);
			if (widgetParam != null) {
				if (CmnUtil.isStringEmpty(invokeClass)) {
					// 若无指定响应面板,则使用当前所在面板类进行响应
					invokeClass = widgetParam.getInvokeClass();
				} else if (!CmnUtil.isStringEqual(widgetParam.getInvokeClass(), invokeClass)) {
					// 若已指定响应面板,则需是当前所在面板类
					if(FeDebugUtil.isEnableDebug(panelContext)) {
						LvUtil.trace("忽略事件操作，事件设置的invokeClass与组件参数中的invokeClass不一致！widgetParam.getInvokeClass() = " + widgetParam.getInvokeClass() + ", invokeClass = " + invokeClass);
					}
					return;
				}
			}

			EventInterface inst = newInstance(invokeClass, EventInterface.class);
			if (inst instanceof Component) {
				((Component) inst).setWidgetParam(widgetParam);
				((Component) inst).setPanelContext(panelContext);
				setRequestCateogys(((Component) inst).getRequestCategorys(panelContext));
			}
			inst.onEvent(event, panelContext, source);

		} catch (Throwable e) {
			ExceptionHandleResult result = handleException(panelContext, e);
			exp = result.getOrgException();
			finalHandleResult(result);
		} finally {
			logTrace(tracer, tracker,panelContext, widgetParam, event.getSourceWidgetId(), event.getCommand(), start,exp);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	default SelectEditorInterface _doBuildSelectEditorInterface(SelectEditorQuerier querier, SelectEditorQuerierContext context) throws Exception {
		
		if (querier != null) {
			FeDeliverData querierData = (FeDeliverData) querier.getBinaryData();
			setCurrentOperateTransaction(querierData,false);
			if (querierData != null) {
				String invokeClass = querierData.getInvokeClass();
				String widgetId = querierData.getWidgetIdOfWidgetParam();
				SelectEditorInterface inst = newInstance(invokeClass, SelectEditorInterface.class);
				if (inst instanceof Component) {
					WidgetParam panelWidgetParam = getPanelWidgetParam(context, widgetId);
					((Component) inst).setWidgetParam(panelWidgetParam);
				}
				return inst;
			}
		}
		FePojo fePojo = context.getSelectEditorFePojo();
		if (fePojo == null || !(fePojo.getBinaryData() instanceof WidgetParam))
			return null;
		WidgetParam widgetParam = (WidgetParam) fePojo.getBinaryData();
		String invokeClass = widgetParam.getInvokeClass();
		
		SelectEditorInterface inst = newInstance(invokeClass, SelectEditorInterface.class);
		if (inst instanceof Component) {
			WidgetParam panelWidgetParam = getPanelWidgetParam(context, null);
			((Component) inst).setWidgetParam(panelWidgetParam);
			((Component) inst).setPanelContext(context);
			setRequestCateogys(((Component) inst).getRequestCategorys(context));
		}
		return inst;
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	default List<PairDto> querySelectItems(SelectEditorQuerier querier, SelectEditorQuerierContext context) throws Exception {
		AutoTracer tracer = getDebugTracer(context);
		FeTracker tracker = getFeTracker(context);
		WidgetParam widgetParam = null;
		Throwable exp = null;
		long start = System.currentTimeMillis();
		try {
			SelectEditorInterface inst = _doBuildSelectEditorInterface(querier, context);
			if(inst instanceof Component) {
				widgetParam = ((Component) inst).getWidgetParam();
			}
			return inst == null ? new ArrayList<>() : inst.querySelectItems(querier, context);
		} catch (Throwable e) {
			ExceptionHandleResult result = handleException(context, e);
			exp = result.getOrgException();
			finalHandleResult(result);
		} finally {
			logTrace(tracer, tracker,context, widgetParam, context.getCurrentPanelWidgetId(), "querySelectItems", start,exp);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	default List<PairDto> filterSelectItems(SelectEditorQuerier querier, SelectEditorQuerierContext context) throws Exception {
		AutoTracer tracer = getDebugTracer(context);
		FeTracker tracker = getFeTracker(context);
		WidgetParam widgetParam = null;
		Throwable exp = null;
		long start = System.currentTimeMillis();
		try {
			SelectEditorInterface inst = _doBuildSelectEditorInterface(querier, context);
			if(inst instanceof Component) {
				widgetParam = ((Component) inst).getWidgetParam();
			}
			return inst == null ? new ArrayList<>() : inst.filterSelectItems(querier, context);
		} catch (Throwable e) {
			ExceptionHandleResult result = handleException(context, e);
			exp = result.getOrgException();
			finalHandleResult(result);
		} finally {
			logTrace(tracer, tracker,context, widgetParam, context.getCurrentPanelWidgetId(), "filterSelectItems", start,exp);
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	default List<CascaderNodeDto> queryCascaderOptions(CascaderQuerier querier, CascaderNodeQuerierContext context) throws Exception {
		AutoTracer tracer = getDebugTracer(context);
		FeTracker tracker = getFeTracker(context);
		WidgetParam widgetParam = null;
		Throwable exp = null;
		long start = System.currentTimeMillis();
		try {
			FeDeliverData querierData = (FeDeliverData) querier.getBinaryData();
			setCurrentOperateTransaction(querierData,false);
			//拿到级联面板的二进制数据
			FePojo fePojo = context.getTreeFepojo();
			widgetParam = (WidgetParam) fePojo.getBinaryData();
			String widgetIdOfWidgetParam = widgetParam != null ? widgetParam.getWidgetId() : null;
			WidgetParam parentWidigetParam = getPanelWidgetParam(context, widgetIdOfWidgetParam);
			String invokeClass = parentWidigetParam.getInvokeClass();
			CascaderInterface inst = newInstance(invokeClass, CascaderInterface.class);
			if (inst instanceof Component) {
				((Component) inst).setWidgetParam(parentWidigetParam);
				((Component) inst).setPanelContext(context);
				setRequestCateogys(((Component) inst).getRequestCategorys(context));
			}
			return inst.queryCascaderOptions(querier, context);
		} catch (Throwable e) {
			ExceptionHandleResult result = handleException(context, e);
			exp = result.getOrgException();
			finalHandleResult(result);
		} finally {
			logTrace(tracer, tracker,context, widgetParam, context.getCurrentPanelWidgetId(), "queryCascaderOptions", start,exp);
		}
		return new ArrayList<>();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	default WidgetDto buildLazyPanelChild(LazyPanelBuilder builder, PanelContext context) throws Exception {
		AutoTracer tracer = getDebugTracer(context);
		FeTracker tracker = getFeTracker(context);
		WidgetParam widgetParam = null;
		Throwable exp = null;
		long start = System.currentTimeMillis();
		try {
			FeDeliverData querierData = (FeDeliverData) builder.getBinaryData();
			setCurrentOperateTransaction(querierData,false);
			String widgetId = querierData.getWidgetIdOfWidgetParam();
			String invokeClass = querierData.getInvokeClass();

			widgetParam = getPanelWidgetParam(context, widgetId);

			if (widgetParam != null) {
				if (CmnUtil.isStringEmpty(invokeClass)) {
					invokeClass = widgetParam.getInvokeClass();
				} else if (!CmnUtil.isStringEqual(widgetParam.getInvokeClass(), invokeClass)) {
					if(FeDebugUtil.isEnableDebug(context)) {
						LvUtil.trace("忽略事件操作，invokeClass与组件参数中的invokeClass不一致！widgetParam.getInvokeClass() = " + widgetParam.getInvokeClass() + ", invokeClass = " + invokeClass);
					}
					return null;
				}
			}
			LazyPanelInterface inst = newInstance(invokeClass, LazyPanelInterface.class);
			if (inst instanceof Component) {
				((Component) inst).setWidgetParam(widgetParam);
				((Component) inst).setPanelContext(context);
				setRequestCateogys(((Component) inst).getRequestCategorys(context));
			}
			return inst.buildLazyPanelChild(builder, context);
		} catch (Throwable e) {
			ExceptionHandleResult result = handleException(context, e);
			exp = result.getOrgException();
			finalHandleResult(result);
		} finally {
			logTrace(tracer, tracker,context, widgetParam, context.getCurrentPanelWidgetId(), "buildLazyPanelChild", start,exp);
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	default List<NavMenuNodeDto> queryNavChild(NavMenuNodeQuerier querier, NavMenuNodeQuerierContext context) throws Exception {
		AutoTracer tracer = getDebugTracer(context);
		FeTracker tracker = getFeTracker(context);
		WidgetParam widgetParam = null;
		Throwable exp = null;
		long start = System.currentTimeMillis();
		try {
			FeDeliverData querierData = (FeDeliverData) querier.getBinaryData();
			setCurrentOperateTransaction(querierData,false);
			String widgetIdOfWidgetParam = querierData.getWidgetIdOfWidgetParam();
			String invokeClass = querierData.getInvokeClass();

			NavMenuInterface inst = newInstance(invokeClass, NavMenuInterface.class);
			if (inst instanceof Component) {
				widgetParam = getPanelWidgetParam(context, widgetIdOfWidgetParam);
				((Component) inst).setWidgetParam(widgetParam);
				((Component) inst).setPanelContext(context);
				setRequestCateogys(((Component) inst).getRequestCategorys(context));
			}
			return inst.queryNavChild(querier, context);
		} catch (Throwable e) {
			ExceptionHandleResult result = handleException(context, e);
			exp = result.getOrgException();
			finalHandleResult(result);
		} finally {
			logTrace(tracer, tracker,context, widgetParam, context.getCurrentPanelWidgetId(), "queryNavChild", start,exp);
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	default List<String> queryAutoCompleteList(TextEditorServiceParamDto serviceParam, TextEditorQuerierContext context) throws Exception {
		AutoTracer tracer = getDebugTracer(context);
		FeTracker tracker = getFeTracker(context);
		WidgetParam widgetParam = null;
		Throwable exp = null;
		long start = System.currentTimeMillis();
		try {
			FeDeliverData querierData = (FeDeliverData) serviceParam.getBinaryData();
			setCurrentOperateTransaction(querierData,true);
			String invokeClass = querierData.getInvokeClass();
			TextEditorInterface inst = newInstance(invokeClass, TextEditorInterface.class);
			if (inst instanceof Component) {
				widgetParam = getPanelWidgetParam(context, null);
				((Component) inst).setWidgetParam(widgetParam);
				((Component) inst).setPanelContext(context);
				setRequestCateogys(((Component) inst).getRequestCategorys(context));
			}
			return inst.queryAutoCompleteList(serviceParam, context);
		} catch (Throwable e) {
			exp = e;
			ExceptionHandleResult result = handleException(context, e);
			exp = result.getOrgException();
			finalHandleResult(result);
		} finally {
			logTrace(tracer, tracker,context, widgetParam, context.getCurrentPanelWidgetId(), "queryAutoCompleteList", start,exp);
		}
		return new ArrayList<>();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	default void receiveData(PanelContext context, AudioFrameDto frame) throws Exception {
		AutoTracer tracer = getDebugTracer(context);
		FeTracker tracker = getFeTracker(context);
		WidgetParam widgetParam = null;
		Throwable exp = null;
		long start = System.currentTimeMillis();
		try {
			ServiceParamDto serviceParamDto = context.getServiceParam();
			FeDeliverData serviceParamData = (FeDeliverData) serviceParamDto.getBinaryData();
			setCurrentOperateTransaction(serviceParamData,true);
			String invokeClass = serviceParamData.getInvokeClass();
			MicCaptureService inst = newInstance(invokeClass, MicCaptureService.class);
			if (inst instanceof Component) {
				widgetParam = getPanelWidgetParam(context, null);
				((Component) inst).setWidgetParam(widgetParam);
				((Component) inst).setPanelContext(context);
				setRequestCateogys(((Component) inst).getRequestCategorys(context));
			}
			inst.receiveData(context, frame);
		} catch (Throwable e) {
			ExceptionHandleResult result = handleException(context, e);
			exp = result.getOrgException();
			finalHandleResult(result);
		} finally {
			logTrace(tracer, tracker,context, widgetParam, context.getCurrentPanelWidgetId(), "receiveData", start,exp);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	default UnitDropResultDto unitDrop(PanelContext designerPanelContext, UnitDropInfoDto dropInfo) throws Exception {
		AutoTracer tracer = getDebugTracer(designerPanelContext);
		FeTracker tracker = getFeTracker(designerPanelContext);
		WidgetParam widgetParam = null;
		Throwable exp = null;
		long start = System.currentTimeMillis();
		try {
			setCurrentOperateTransaction(widgetParam,false);
			widgetParam = getPanelWidgetParam(designerPanelContext, null);
			String invokeClass = widgetParam.getInvokeClass();
			PanelDesignerExtendInterface inst = newInstance(invokeClass, PanelDesignerExtendInterface.class);
			if (inst instanceof Component) {
				WidgetParam panelWidgetParam = getPanelWidgetParam(designerPanelContext, null);
				((Component) inst).setWidgetParam(panelWidgetParam);
				((Component) inst).setPanelContext(designerPanelContext);
				setRequestCateogys(((Component) inst).getRequestCategorys(designerPanelContext));
			}
			return inst.unitDrop(designerPanelContext, dropInfo);
		} catch (Throwable e) {
			ExceptionHandleResult result = handleException(designerPanelContext, e);
			exp = result.getOrgException();
			finalHandleResult(result);
		} finally {
			logTrace(tracer, tracker,designerPanelContext, widgetParam, designerPanelContext.getCurrentPanelWidgetId(), "unitDrop", start,exp);
		}
		return null;
	}
	
	// 前端点击组件编辑按钮，弹出菜单时，可以向后端请求扩展菜单项，以便后端实现自己的右键编辑菜单
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	default List<MenuItemDto> loadPopupMenu(PanelContext designerPanelContext, String selectedUnitId) throws Exception {
		AutoTracer tracer = getDebugTracer(designerPanelContext);
		FeTracker tracker = getFeTracker(designerPanelContext);
		WidgetParam widgetParam = null;
		Throwable exp = null;
		long start = System.currentTimeMillis();
		try {
			widgetParam = getPanelWidgetParam(designerPanelContext, null);
			setCurrentOperateTransaction(widgetParam,false);
			String invokeClass = widgetParam.getInvokeClass();
			PanelDesignerExtendInterface inst = newInstance(invokeClass, PanelDesignerExtendInterface.class);
			if (inst instanceof Component) {
				WidgetParam panelWidgetParam = getPanelWidgetParam(designerPanelContext, null);
				((Component) inst).setWidgetParam(panelWidgetParam);
				((Component) inst).setPanelContext(designerPanelContext);
				setRequestCateogys(((Component) inst).getRequestCategorys(designerPanelContext));
			}
			return inst.loadPopupMenu(designerPanelContext, selectedUnitId);
		} catch (Throwable e) {
			ExceptionHandleResult result = handleException(designerPanelContext, e);
			exp = result.getOrgException();
			finalHandleResult(result);
		} finally {
			logTrace(tracer, tracker,designerPanelContext, widgetParam, designerPanelContext.getCurrentPanelWidgetId(), "loadPopupMenu", start,exp);
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	default StudioWidgetOverlayDto getWidgetOverlay(PanelContext designerPanelContext, String unitName)
			throws Exception {
		AutoTracer tracer = getDebugTracer(designerPanelContext);
		FeTracker tracker = getFeTracker(designerPanelContext);
		WidgetParam widgetParam = null;
		Throwable exp = null;
		long start = System.currentTimeMillis();
		try {
			widgetParam = getPanelWidgetParam(designerPanelContext, null);
			setCurrentOperateTransaction(widgetParam,false);
			String invokeClass = widgetParam.getInvokeClass();
			PanelDesignerExtendInterface inst = newInstance(invokeClass, PanelDesignerExtendInterface.class);
			if (inst instanceof Component) {
				WidgetParam panelWidgetParam = getPanelWidgetParam(designerPanelContext, null);
				((Component) inst).setWidgetParam(panelWidgetParam);
				((Component) inst).setPanelContext(designerPanelContext);
				setRequestCateogys(((Component) inst).getRequestCategorys(designerPanelContext));
			}
			return inst.getWidgetOverlay(designerPanelContext, unitName);
		} catch (Throwable e) {
			ExceptionHandleResult result = handleException(designerPanelContext, e);
			exp = result.getOrgException();
			finalHandleResult(result);
		} finally {
			logTrace(tracer, tracker,designerPanelContext, widgetParam, designerPanelContext.getCurrentPanelWidgetId(), "loadPopupMenu", start,exp);
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	default TextEditorValueVerifyResultDto verifyTextEditorValue(TextEditorServiceParamDto serviceParam,
			TextEditorQuerierContext context) throws Exception {
		AutoTracer tracer = getDebugTracer(context);
		FeTracker tracker = getFeTracker(context);
		WidgetParam widgetParam = null;
		Throwable exp = null;
		long start = System.currentTimeMillis();
		try {
//			ServiceParamDto serviceParamDto = context.getServiceParam();
			FeDeliverData serviceParamData = (FeDeliverData) serviceParam.getBinaryData();
			setCurrentOperateTransaction(serviceParamData,true);
			String invokeClass = serviceParamData.getInvokeClass();
			TextEditorInterface inst = newInstance(invokeClass, TextEditorInterface.class);
			if (inst instanceof Component) {
				WidgetParam panelWidgetParam = getPanelWidgetParam(context, null);
				((Component) inst).setWidgetParam(panelWidgetParam);
				((Component) inst).setPanelContext(context);
				setRequestCateogys(((Component) inst).getRequestCategorys(context));
			}
			return inst.verifyTextEditorValue(serviceParam, context);
		} catch (Throwable e) {
			ExceptionHandleResult result = handleException(context, e);
			exp = result.getOrgException();
			finalHandleResult(result);
		} finally {
			logTrace(tracer, tracker,context, widgetParam, context.getCurrentPanelWidgetId(), "verifyTextEditorValue", start,exp);
		}
		return null;
	}
	
	@Override
	default List<ChartSeriesDto> querySeriesData(ChartServiceParamDto serviceParam, ChartContext context)
			throws Exception {
		AutoTracer tracer = getDebugTracer(context);
		FeTracker tracker = getFeTracker(context);
		WidgetParam widgetParam = null;
		Throwable exp = null;
		long start = System.currentTimeMillis();
		try {
			ServiceParamDto serviceParamDto = context.getServiceParam();
			FeDeliverData serviceParamData = (FeDeliverData) serviceParamDto.getBinaryData();
			setCurrentOperateTransaction(serviceParamData,true);
			String invokeClass = serviceParamData.getInvokeClass();
			ChartInterface inst = newInstance(invokeClass, ChartInterface.class);
			if (inst instanceof Component) {
				WidgetParam panelWidgetParam = getPanelWidgetParam(context, null);
				((Component) inst).setWidgetParam(panelWidgetParam);
				((Component) inst).setPanelContext(context);
				setRequestCateogys(((Component) inst).getRequestCategorys(context));
			}
			return inst.querySeriesData(serviceParam, context);
		} catch (Throwable e) {
			ExceptionHandleResult result = handleException(context, e);
			exp = result.getOrgException();
			finalHandleResult(result);
		} finally {
			logTrace(tracer, tracker,context, widgetParam, context.getCurrentPanelWidgetId(), "querySeriesData", start,exp);
		}
		return null;
	}
	
	@Override
	default ChartAxisData queryAxis(ChartServiceParamDto serviceParam, ChartContext context) throws Exception {
		AutoTracer tracer = getDebugTracer(context);
		FeTracker tracker = getFeTracker(context);
		WidgetParam widgetParam = null;
		Throwable exp = null;
		long start = System.currentTimeMillis();
		try {
			ServiceParamDto serviceParamDto = context.getServiceParam();
			FeDeliverData serviceParamData = (FeDeliverData) serviceParamDto.getBinaryData();
			setCurrentOperateTransaction(serviceParamData,true);
			String invokeClass = serviceParamData.getInvokeClass();
			ChartInterface inst = newInstance(invokeClass, ChartInterface.class);
			if (inst instanceof Component) {
				WidgetParam panelWidgetParam = getPanelWidgetParam(context, null);
				((Component) inst).setWidgetParam(panelWidgetParam);
				((Component) inst).setPanelContext(context);
				setRequestCateogys(((Component) inst).getRequestCategorys(context));
			}
			return inst.queryAxis(serviceParam, context);
		} catch (Throwable e) {
			ExceptionHandleResult result = handleException(context, e);
			exp = result.getOrgException();
			finalHandleResult(result);
		} finally {
			logTrace(tracer, tracker,context, widgetParam, context.getCurrentPanelWidgetId(), "querySeriesData", start,exp);
		}
		return null;
	}
	
	default Object callFunction(Object instance,String sFunName,Object... params) throws Exception{
		return ToolUtilities.callFunction(instance, sFunName, params);
	}
}
