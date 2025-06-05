package cell.fe.gpf.dc.basic;

import bap.cells.Cells;
import fe.util.intf.ServiceIntf;

public interface IGpfDCBasicFeService extends ServiceIntf{

	public static IGpfDCBasicFeService get() {
		return Cells.get(IGpfDCBasicFeService.class);
	}
	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Override
//	default Object onListener(ListenerDto listener, PanelContext panelContext, WidgetDto source) throws Exception {
//		AutoTracer tracer = getDebugTracer(panelContext);
//		WidgetParam widgetParam = null;
//		Exception exp = null;
//		long start = System.currentTimeMillis();
//		try {
//			long start1 = System.currentTimeMillis();
//			FeDeliverData extraData = (FeDeliverData) listener.getBinaryData();
//			String widgetId = extraData.getWidgetIdOfWidgetParam();
//			String invokeClass = extraData.getInvokeClass();
//
//			widgetParam = getPanelWidgetParam(panelContext, widgetId);
//			long end1 = System.currentTimeMillis();
//			LvUtil.trace("获取面板二进制数据：" + (end1 - start1));
//			start1 = System.currentTimeMillis();
//			if(widgetParam != null) {
//				long size = ToolUtilities.calcObjectMemSize(widgetParam);
//				LvUtil.trace("组件参数大小："+ToolUtilities.memSize2String(size));
//			}
//			if (widgetParam != null) {
//				if (CmnUtil.isStringEmpty(invokeClass)) {
//					invokeClass = widgetParam.getInvokeClass();
//				} else if (!CmnUtil.isStringEqual(widgetParam.getInvokeClass(), invokeClass)) {
//					if(FeDebugUtil.isEnableDebug(panelContext)) {
//						LvUtil.trace("忽略监听操作，监听器设置的invokeClass与组件参数中的invokeClass不一致！widgetParam.getInvokeClass() = " + widgetParam.getInvokeClass() + ", invokeClass = " + invokeClass);
//					}
//					return null;
//				}
//			}
//			ListenerInterface inst = newInstance(invokeClass, ListenerInterface.class);
//			end1 = System.currentTimeMillis();
//			LvUtil.trace("初始化监听实例：" + (end1 - start1));
//			start1 = System.currentTimeMillis();
//			if (inst instanceof Component) {
//				((Component) inst).setWidgetParam(widgetParam);
//				((Component) inst).setPanelContext(panelContext);
//				handleListenerCallback((Component) inst, widgetParam, listener, panelContext, source, extraData, FeUtilConst.CALL_BACK_ON_BEFORE,null);
//			}
//			Object result = inst.onListener(listener, panelContext, source);
//			end1 = System.currentTimeMillis();
//			LvUtil.trace("监听执行：" + (end1 - start1));
//			start1 = System.currentTimeMillis();
//			if (inst instanceof Component) {
//				handlerCommandCallback(panelContext, listener.getServiceCommand(), result);
//				//执行监听命令完成后的回调，需要在onListener内部的方法缓存执行结果数据
//				handleListenerCallback((Component) inst, widgetParam, listener, panelContext, source, extraData, FeUtilConst.CALL_BACK_ON_AFTER,result);
//			}
//			end1 = System.currentTimeMillis();
//			LvUtil.trace("监听回调：" + (end1 - start1));
//			start1 = System.currentTimeMillis();
//			return result;
//		} catch (Exception e) {
//			exp = e;
//			e = handleException(panelContext, e);
//			if (e != null) {
//				throw e;
//			}
//		} finally {
//			logTrace(tracer, panelContext, widgetParam, listener.getSourceWidgetId(), listener.getServiceCommand(), start,exp);
//		}
//		return null;
//	}

//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	default void handlerCommandCallback(PanelContext panelContext,String command,Object result) throws Exception {
//		String callbackCommand = command +"_CALLBACK";
//		boolean isDebug = isEnableDebugLog(panelContext);
//		if(isDebug)LvUtil.trace("处理" + command + "的回调指令");
//		ExtListenerDto lsnr = null;
//		try{
//			long start = System.currentTimeMillis();
//			lsnr = GetExtListener.get(panelContext, panelContext.getCurrentPanelWidgetId(), callbackCommand);
//			long end = System.currentTimeMillis();
//			
//			if(isDebug) {
//				if(lsnr != null) {
//					LvUtil.trace("ExtListener Size : "+ToolUtilities.memSize2String(ToolUtilities.calcObjectMemSize(lsnr)));
//				}
//				LvUtil.trace("GetExtListener执行耗时："+(end-start)+"ms");
//			}
//		}catch (Exception e) {
//			if(isDebug)LvUtil.trace("未找到[" + command + "]的回调指令，无需回调处理。");
//			return;
//		}
//		if(lsnr instanceof CommandCallbackListener) {
//			if (lsnr != null && lsnr.getListener() != null) {
//				String invokeClass = "";
//				if(lsnr.getListener().getServerExecutor() != null) {
//					FeDeliverData cmdExtraData = (FeDeliverData) lsnr.getListener().getBinaryData();
//					invokeClass = cmdExtraData.getInvokeClass();
//				}
//				
//				PanelContext listenPanelContext = panelContext.cloneWithChannel();
//				if(((CommandCallbackListener) lsnr).isPopupCallback()) {
//					if(isDebug)LvUtil.trace("回调运行环境筹备：回调指令[" + lsnr.getName() + "]是弹窗回调，开始查找父级弹窗的面板上下文。");
//					long start = System.currentTimeMillis();
//					List<PanelContext> popContexts = QueryPopContextStack.query(panelContext.getChannel());
//					listenPanelContext = popContexts.get(popContexts.size()-2);
//					listenPanelContext.setChannel(panelContext.getChannel());
//					long end = System.currentTimeMillis();
//					if(isDebug)LvUtil.trace("QueryPopContextStack执行耗时："+(end-start)+"ms");
//				}else {
//					if(!((CommandCallbackListener) lsnr).isInOnePanel()) {
//						if(isDebug)LvUtil.trace("回调运行环境筹备：回调指令[" + lsnr.getName() + "]的组件和当前指令组件不在一个panel，开始查找父级面板上下文。");
//						//如果回调目标组件不是一个面板，需要找到当前面板的父面板
//						long start = System.currentTimeMillis();
//						List<PanelInfo> parentPanels = QueryParentPanel.query(panelContext);
//						long end = System.currentTimeMillis();
//						if(isDebug)LvUtil.trace("QueryParentPanel执行耗时："+(end-start)+"ms");
//						for(int i = parentPanels.size()-1;i>=0;i--) {
//							PanelInfo parentPanel = parentPanels.get(i);
//							if(parentPanel.getPanelClass().equals(LazyPanelDto.class.getName())) {
//								if(parentPanels.size() == 1) {
//									//如果是懒加载面板，这里因为没找到往上的面板，所以重新查一遍
//									try {
//										long start2 = System.currentTimeMillis();
//										listenPanelContext = ConvertPanelContext.convert(panelContext, "../"+parentPanel.getPanelWidgetId());
//										List<PanelInfo> lazyParentPanels = QueryParentPanel.query(listenPanelContext);
//										if(lazyParentPanels.size() > 0) {
//											parentPanel = lazyParentPanels.get(lazyParentPanels.size()-1);
//											listenPanelContext = ConvertPanelContext.convert(panelContext, "../"+parentPanel.getPanelWidgetId());
//										}
//										long end2 = System.currentTimeMillis();
//										if(isDebug)LvUtil.trace("懒加载面板，QueryParentPanel执行耗时："+(end2-start2)+"ms");
//									}catch (Exception e) {
//										e.printStackTrace();
//										if(isDebug)LvUtil.trace("回调运行环境筹备出错：" + ToolUtilities.getFullExceptionStack(e));
//										return;
//									}
//								}
//							}else {
//								try {
//									long start2 = System.currentTimeMillis();
//									listenPanelContext = ConvertPanelContext.convert(panelContext, "../"+parentPanel.getPanelWidgetId());
//									long end2 = System.currentTimeMillis();
//									if(isDebug)LvUtil.trace("ConvertPanelContext执行耗时：" + (end2-start2)+"ms");
//									break;
//								}catch (Exception e) {
//									e.printStackTrace();
//									if(isDebug)LvUtil.trace("回调运行环境筹备出错：" + ToolUtilities.getFullExceptionStack(e));
//									return;
//								}
//							}
//						}
//					}
//						
//				}
//				if(result != null) {
//					if(lsnr.getListener().getServerExecutor() != null) {
//						FeDeliverData cmdExtraData = (FeDeliverData) lsnr.getListener().getBinaryData();
//						cmdExtraData.setData(result);
//						lsnr.getListener().setBinaryData(cmdExtraData);
//					}else if(lsnr.getListener().getEventExecutor() != null) {
//						EventDto event = lsnr.getListener().getEventExecutor().getEvent();
//						if(event instanceof FeCmnEvent) {
//							FeCmnEvent cmnEvent = (FeCmnEvent) event;
//							if(cmnEvent.getInvokeParams() == null) {
//								cmnEvent.setInvokeParams(new Object[] {result});
//							}
//							cmnEvent.setSelfBinaryData();
//						}
//						
//					}
//				}
//				if(isDebug)LvUtil.trace("触发回调指令[" + lsnr.getName() + "]执行！");
//				long start2 = System.currentTimeMillis();
//				IFeService.get().fireListener(listenPanelContext, lsnr);
//				long end2 = System.currentTimeMillis();
//				if(isDebug)LvUtil.trace("回调指令执行耗时：" + (end2-start2)+"ms");
//			}
//		}
//	}
	
	
}
