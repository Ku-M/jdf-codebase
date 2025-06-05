package fe.util;

import java.awt.Color;

import com.kwaidoo.ms.tool.CmnUtil;
import com.kwaidoo.ms.tool.ToolUtilities;

import cmn.util.TraceUtil;
import cmn.util.Tracer;
import fe.cmn.data.CColor;
import fe.cmn.exception.FeInvalidPanelException;
import fe.cmn.panel.BoxDto;
import fe.cmn.panel.MainAxisAlign;
import fe.cmn.panel.PanelContext;
import fe.cmn.panel.ability.PopMaskOfWidget;
import fe.cmn.text.CFontWeight;
import fe.cmn.text.CTextStyle;
import fe.cmn.widget.CLabelAlign;
import fe.cmn.widget.CircularProgressIndicatorDto;
import fe.cmn.widget.LabelDto;
import fe.cmn.widget.LinearProgressIndicatorDto;
import fe.cmn.widget.OverlayDto;
import fe.cmn.widget.decoration.DecorationDto;
import fe.cmn.widget.decoration.LabelDecorationDto;
import fe.cmn.widget.decoration.PositionedDto;
import fe.util.dto.loading.LoadingMaskConfigDto;
import fe.util.dto.loading.LoadingMaskConfigDto.LoadingType;
import fe.util.style.FeStyleSetting;

public class LoadingMask {
	
	public static CColor defaultBgColor = new FeStyleSetting("").getBackgroudColor().setOpacity(0.8f);
	/**
	 * 根据配置参数显示遮罩层
	 * @param panelContext
	 * @param config
	 * @throws Exception
	 */
	public static void show(PanelContext panelContext,String widgetId,LoadingMaskConfigDto config) throws Exception {
		LoadingType type = config.getLoadingTypeEnum();
		switch (type) {
		case Text:
			showTextProgress(panelContext, widgetId, config.getMinDisplayTime(), config.getLoadingText(), config.getBgColor());
			break;
		case Circular:
			showCircularProgress(panelContext, widgetId, config.getMinDisplayTime());
			break;
		case Linear:
			showLinearProgress(panelContext, widgetId, config.getMinDisplayTime());
			break;
		default:
			break;
		}
	}
	
	/**
	 * 异步显示组件上的遮罩层
	 * @param panelContext
	 * @param widgetId
	 * @throws Exception
	 */
	public static void asyncShow(PanelContext panelContext,String widgetId,LoadingMaskConfigDto config) throws Exception {
		LoadingType type = config.getLoadingTypeEnum();
		switch (type) {
		case Text:
			asyncShowTextProgress(panelContext, widgetId, config.getMinDisplayTime(), config.getLoadingText(), config.getBgColor());
			break;
		case Circular:
			asyncShowCircularProgress(panelContext, widgetId, config.getMinDisplayTime());
			break;
		case Linear:
			asyncShowLinearProgress(panelContext, widgetId, config.getMinDisplayTime());
			break;
		default:
			break;
		}
	}
	
	/**
	 * 显示文本遮罩层，需要在面板上设置setWrapMask(true);
	 * @param panelContext
	 * @throws Exception
	 */
	public static void showTextProgress(PanelContext panelContext) throws Exception {
		showTextProgress(panelContext, panelContext.getCurrentPanelWidgetId(),null,null,null);
	}
	/**
	 * 在组件上显示文本遮罩层，需要在组件上设置setWrapMask(true);
	 * @param panelContext
	 * @param widgetId
	 * @param minDisplayTime	遮罩层最短显示时长，默认 1s
	 * @param loadingText	遮罩层显示文本，默认：页面加载中......
	 * @param bgColor	遮罩层背景颜色，
	 * @throws Exception
	 */
	public static void showTextProgress(PanelContext panelContext,String widgetId,Integer minDisplayTime,String loadingText,CColor bgColor) throws Exception {
		if(CmnUtil.isStringEmpty(loadingText)){
			loadingText = "页面加载中...";
		}
		if(bgColor == null) {
			bgColor = defaultBgColor;
		}
		BoxDto mask = BoxDto.vbar(
    			BoxDto.hbar(new LabelDto(loadingText).setDecoration(new LabelDecorationDto().setAlign(CLabelAlign.CENTER).setTextStyle(new CTextStyle().setFontWeight(CFontWeight.bold))))
    			.setMainAxisAlignment(MainAxisAlign.center)
    			).setMainAxisAlignment(MainAxisAlign.center);
		mask.setDecoration(new DecorationDto().setBackground(bgColor));
		try {
			Tracer tracer = TraceUtil.getCurrentTracer();
			tracer.logStart();
			PopMaskOfWidget.show(panelContext, widgetId, mask,PositionedDto.fill(),minDisplayTime);
			tracer.debugCost("", "LoadingMask.showTextProgress");
		}catch (FeInvalidPanelException e) {
			if(FeDebugUtil.isEnableDebug(panelContext))
				throw e;
			else
				ToolUtilities.warning(LoadingMask.class.getSimpleName(), ToolUtilities.getFullExceptionStack(e));
		}
	}
	/**
	 * 在组件上显示文本遮罩层，需要在组件上设置setWrapMask(true);
	 * @param panelContext
	 * @param widgetId
	 * @param minDisplayTime	遮罩层最短显示时长，默认 1s
	 * @param loadingText	遮罩层显示文本，默认：页面加载中......
	 * @param bgColor	遮罩层背景颜色，
	 * @throws Exception
	 */
	public static void asyncShowTextProgress(PanelContext panelContext,String widgetId,Integer minDisplayTime,String loadingText,CColor bgColor) throws Exception {
		if(CmnUtil.isStringEmpty(loadingText)){
			loadingText = "页面加载中...";
		}
		if(bgColor == null) {
			bgColor = defaultBgColor;
		}
		BoxDto mask = BoxDto.vbar(
    			BoxDto.hbar(new LabelDto(loadingText).setDecoration(new LabelDecorationDto().setAlign(CLabelAlign.CENTER).setTextStyle(new CTextStyle().setFontWeight(CFontWeight.bold))))
    			.setMainAxisAlignment(MainAxisAlign.center)
    			).setMainAxisAlignment(MainAxisAlign.center);
		mask.setDecoration(new DecorationDto().setBackground(bgColor));
		Tracer tracer = TraceUtil.getCurrentTracer();
		tracer.logStart();
		PopMaskOfWidget callback = new PopMaskOfWidget(widgetId, new OverlayDto(mask, PositionedDto.fill())).setMinDisplayTime(minDisplayTime);
		FeCallbackPool.add(panelContext, callback);
		tracer.debugCost("", "LoadingMask.ayncShowTextProgress");
	}
	
	/**
	 * 显示环形进度条，需要在组件上设置setWrapMask(true);
	 * @param panelContext
	 * @throws Exception
	 */
	public static void showCircularProgress(PanelContext panelContext) throws Exception {
		showCircularProgress(panelContext, panelContext.getCurrentPanelWidgetId());
	}
	/**
	 * 显示环形进度条，需要在组件上设置setWrapMask(true);
	 * @param panelContext
	 * @param widgetId
	 * @throws Exception
	 */
	public static void showCircularProgress(PanelContext panelContext,String widgetId) throws Exception {
		showCircularProgress(panelContext, widgetId, null);
	}
	/**
	 * 显示环形进度条，需要在组件上设置setWrapMask(true);
	 * @param panelContext
	 * @param widgetId
	 * @param minDisplayTime
	 * @throws Exception
	 */
	public static void showCircularProgress(PanelContext panelContext,String widgetId,Integer minDisplayTime) throws Exception {
		CircularProgressIndicatorDto loaingDto = new CircularProgressIndicatorDto();
		BoxDto mask = BoxDto.vbar(
    			BoxDto.hbar(loaingDto)
    			.setMainAxisAlignment(MainAxisAlign.center)
    			).setMainAxisAlignment(MainAxisAlign.center);
		mask.setDecoration(new DecorationDto().setBackground(CColor.fromColor(Color.white).setOpacity(0.5f)));
		try {
			Tracer tracer = TraceUtil.getCurrentTracer();
			tracer.logStart();
			PopMaskOfWidget.show(panelContext, widgetId, mask,PositionedDto.fill(),minDisplayTime);
			tracer.debugCost("", "LoadingMask.showCircularProgress");
		}catch (FeInvalidPanelException e) {
			if(FeDebugUtil.isEnableDebug(panelContext))
				throw e;
			else
				ToolUtilities.warning(LoadingMask.class.getSimpleName(), ToolUtilities.getFullExceptionStack(e));
		}
	}
	
	/**
	 * 显示环形进度条，需要在组件上设置setWrapMask(true);
	 * @param panelContext
	 * @param widgetId
	 * @param minDisplayTime
	 * @throws Exception
	 */
	public static void asyncShowCircularProgress(PanelContext panelContext,String widgetId,Integer minDisplayTime) throws Exception {
		CircularProgressIndicatorDto loaingDto = new CircularProgressIndicatorDto();
		BoxDto mask = BoxDto.vbar(
    			BoxDto.hbar(loaingDto)
    			.setMainAxisAlignment(MainAxisAlign.center)
    			).setMainAxisAlignment(MainAxisAlign.center);
		mask.setDecoration(new DecorationDto().setBackground(CColor.fromColor(Color.white).setOpacity(0.5f)));
		Tracer tracer = TraceUtil.getCurrentTracer();
		tracer.logStart();
		PopMaskOfWidget callback = new PopMaskOfWidget(widgetId, new OverlayDto(mask, PositionedDto.fill())).setMinDisplayTime(minDisplayTime);
		FeCallbackPool.add(panelContext, callback);
		tracer.debugCost("", "LoadingMask.ayncShowCircularProgress");
	}
	/**
	 * 显示线形进度条，需要在组件上设置setWrapMask(true);
	 * @param panelContext
	 * @throws Exception
	 */
	public static void showLinearProgress(PanelContext panelContext) throws Exception {
		showLinearProgress(panelContext, panelContext.getCurrentPanelWidgetId());
	}
	/**
	 *  显示线形进度条，需要在组件上设置setWrapMask(true);
	 * @param panelContext
	 * @param widgetId
	 * @throws Exception
	 */
	public static void showLinearProgress(PanelContext panelContext,String widgetId) throws Exception {
		showLinearProgress(panelContext, widgetId, 500);
	}
	/**
	 * 显示线形进度条，需要在组件上设置setWrapMask(true);
	 * @param panelContext
	 * @param widgetId
	 * @param minDisplayTime
	 * @throws Exception
	 */
	public static void showLinearProgress(PanelContext panelContext,String widgetId,Integer minDisplayTime) throws Exception {
		LinearProgressIndicatorDto loaingDto = new LinearProgressIndicatorDto();
		BoxDto mask = BoxDto.vbar(
    			BoxDto.hbar(loaingDto)
    			.setMainAxisAlignment(MainAxisAlign.center)
    			).setMainAxisAlignment(MainAxisAlign.center);
		mask.setDecoration(new DecorationDto().setBackground(CColor.fromColor(Color.white).setOpacity(0.5f)));
		try {
			Tracer tracer = TraceUtil.getCurrentTracer();
			tracer.logStart();
			PopMaskOfWidget.show(panelContext, widgetId, mask,PositionedDto.fill(),minDisplayTime);
			tracer.debugCost("", "LoadingMask.showLinearProgress");
		}catch (FeInvalidPanelException e) {
			if(FeDebugUtil.isEnableDebug(panelContext))
				throw e;
			else
				ToolUtilities.warning(LoadingMask.class.getSimpleName(), ToolUtilities.getFullExceptionStack(e));
		}
	}
	
	public static void asyncShowLinearProgress(PanelContext panelContext,String widgetId,Integer minDisplayTime) throws Exception {
		LinearProgressIndicatorDto loaingDto = new LinearProgressIndicatorDto();
		BoxDto mask = BoxDto.vbar(
    			BoxDto.hbar(loaingDto)
    			.setMainAxisAlignment(MainAxisAlign.center)
    			).setMainAxisAlignment(MainAxisAlign.center);
		mask.setDecoration(new DecorationDto().setBackground(CColor.fromColor(Color.white).setOpacity(0.5f)));
		Tracer tracer = TraceUtil.getCurrentTracer();
		tracer.logStart();
		PopMaskOfWidget callback = new PopMaskOfWidget(widgetId, new OverlayDto(mask, PositionedDto.fill())).setMinDisplayTime(minDisplayTime);
		FeCallbackPool.add(panelContext, callback);
		tracer.debugCost("", "LoadingMask.asyncShowLinearProgress");
	}
	/**
	 * 隐藏遮罩层
	 * @param panelContext
	 * @throws Exception
	 */
	public static void hide(PanelContext panelContext) throws Exception {
		hide(panelContext, panelContext.getCurrentPanelWidgetId());
	}
	/**
	 * 隐藏组件上的遮罩层
	 * @param panelContext
	 * @param widgetId
	 * @throws Exception
	 */
	public static void hide(PanelContext panelContext,String widgetId) throws Exception {
		try {
			Tracer tracer = TraceUtil.getCurrentTracer();
			tracer.logStart();
			PopMaskOfWidget.hide(panelContext, widgetId);
			tracer.debugCost("", "LoadingMask.hide");
		}catch (Exception e) {
			if(FeDebugUtil.isEnableDebug(panelContext)) {
				throw e;
			}else
				ToolUtilities.warning(LoadingMask.class.getSimpleName(), ToolUtilities.getFullExceptionStack(e));
		}
	}
	
	/**
	 * 一异步隐藏组件上的遮罩层
	 * @param panelContext
	 * @param widgetId
	 * @throws Exception
	 */
	public static void asyncHide(PanelContext panelContext,String widgetId) throws Exception {
		try {
			Tracer tracer = TraceUtil.getCurrentTracer();
			tracer.logStart();
//			PopMaskOfWidget.hide(panelContext, widgetId);
			//更改为异步方式关闭遮罩
			PopMaskOfWidget callback = new PopMaskOfWidget(widgetId);
			FeCallbackPool.add(panelContext, callback);
			tracer.debugCost("", "LoadingMask.asyncHide");
		}catch (Exception e) {
			if(FeDebugUtil.isEnableDebug(panelContext)) {
				throw e;
			}else
				ToolUtilities.warning(LoadingMask.class.getSimpleName(), ToolUtilities.getFullExceptionStack(e));
		}
	}
	
	
}
