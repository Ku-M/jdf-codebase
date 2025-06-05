package fe.util.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.kwaidoo.ms.tool.CmnUtil;
import com.leavay.common.util.ToolUtilities;
import com.leavay.common.util.javac.ClassFactory;

import cell.fe.cmn.IFeCmnService;
import cell.fe.cmn.IFeI18nPlugin;
import cmn.anotation.ClassDeclare;
import cmn.anotation.FieldDeclare;
import fe.cmn.data.CColor;
import fe.cmn.data.PairDto;
import fe.cmn.editor.CustomizeEditorDto;
import fe.cmn.editor.decoration.EditorReadonlyStyleTheme;
import fe.cmn.panel.BoxDto;
import fe.cmn.panel.ContainerDto;
import fe.cmn.panel.CrossAxisAlign;
import fe.cmn.panel.DrawerDirection;
import fe.cmn.panel.MainAxisAlign;
import fe.cmn.panel.PanelContext;
import fe.cmn.panel.PanelDto;
import fe.cmn.panel.PanelValue;
import fe.cmn.panel.PopDialogType;
import fe.cmn.panel.SinglePanelDto;
import fe.cmn.panel.ability.PopDialog;
import fe.cmn.panel.ability.PopDrawer;
import fe.cmn.panel.ability.QuitPopup;
import fe.cmn.panel.ability.SetCustomizeEditorValue;
import fe.cmn.res.FeIcons;
import fe.cmn.res.JDFICons;
import fe.cmn.widget.ButtonDto;
import fe.cmn.widget.EscapeButtonDto;
import fe.cmn.widget.IconDto;
import fe.cmn.widget.LabelDto;
import fe.cmn.widget.ListenerDto;
import fe.cmn.widget.ListenerInterface;
import fe.cmn.widget.SizeDto;
import fe.cmn.widget.WidgetDto;
import fe.cmn.widget.decoration.BorderDto;
import fe.cmn.widget.decoration.BorderSideDto;
import fe.cmn.widget.decoration.DecorationDto;
import fe.cmn.widget.decoration.DialogDecorationDto;
import fe.cmn.widget.decoration.DrawerDecorationDto;
import fe.cmn.widget.decoration.IconStyleDto;
import fe.cmn.widget.decoration.PopDecorationDto;
import fe.util.LazyPanelUtil;
import fe.util.component.ablity.PanelAblity;
import fe.util.component.callback.ComponentCallback;
import fe.util.component.callback.QuitPopupCallback;
import fe.util.component.callback.SetPanelLastCommandCallBack;
import fe.util.component.dto.FeDeliverData;
import fe.util.component.param.CallBackSetting;
import fe.util.component.param.DataEditParam;
import fe.util.component.param.PopupPanelParam;
import fe.util.component.param.WidgetParam;
import fe.util.editor.valuehanlder.EditorFieldDefine;
import fe.util.editor.valuehanlder.EditorValueHandlerFactory;
import fe.util.i18n.FeI18n;
import fe.util.i18n.FeUtilConst;
import fe.util.intf.ServiceIntf;
import fe.util.style.FeStyleConst;
import fe.util.style.FeStyleSetting;

/**
 * 弹窗面板工具类，
 * 
 *
 * @param <T>
 * @author chenxb
 */

@ClassDeclare(
	    label = "弹出面板",
	    what = "用于显示弹出式界面的组件",
	    why = "提供一个通用的弹出面板，用于各种需要弹出显示的场景",
	    how = "可选参数：okBtnCommands，当弹窗内部面板中自定义了提交按钮时，可指定按钮的cmd，匹配到对应的cmd之后将退出弹窗并返回界面值",
	    developer = "陈晓斌",
	    version = "1.0",
	    createTime = "2024-11-22",
	    updateTime = "2024-11-22"
	)
public class PopupPanel<T extends PopupPanelParam> extends AbsComponent<T> implements ListenerInterface, PanelAblity {

	/**
	 * widgetId - 缓存编辑数据的自定义编辑组件
	 */
	public static final String WIDGET_ID_RETURN_EDIT_DATA = "WIDGET_ID_RETURN_EDIT_DATA";
	/*
	 * widgetId - 缓存最后操作命令的文本编辑器
	 */
	public static final String WIDGET_ID_LAST_COMMAND = "_last_command";

	@FieldDeclare(label = "确认", desc = "执行确认操作的命令")
	public static final String CMD_CONFIRM = "CMD_CONFIRM";
//	public static final String CMD_CLOSE = "CMD_CLOSE";

	/**
	 *
	 */
	private static final long serialVersionUID = -2549816917918088726L;
	
	private static String getI18nString(PanelContext panelContext,String key) throws Exception {
		return IFeI18nPlugin.get().getI18nString(panelContext, key);
	}

	public static Map<String, CallBackSetting> buildCallbackMap(List<String> okBtnCommands) {
		Map<String, CallBackSetting> callBackMap = new HashMap<>();
		for (String quitCommand : okBtnCommands) {
			CallBackSetting setting = new CallBackSetting();
			setting.setCommand(quitCommand);
			List<PairDto<String, ComponentCallback>> callbacks = new ArrayList<>();
//			SetEditorValue callback = new SetEditorValue();
//			callback.setWidgetId("../"+LAST_COMMAND_WIDGETID);
//			callback.setValue(quitCommand);

			SetPanelLastCommandCallBack callback = new SetPanelLastCommandCallBack();
			callback.setWidgetId("../" + WIDGET_ID_LAST_COMMAND);

			callbacks.add(new PairDto<String, ComponentCallback>(FeUtilConst.CALL_BACK_ON_AFTER, callback));
			callbacks.add(new PairDto<String, ComponentCallback>(FeUtilConst.CALL_BACK_ON_AFTER, new QuitPopupCallback()));
			setting.setCallBacks(callbacks);
			callBackMap.put(quitCommand, setting);
		}
		return callBackMap;
	}

	/**
	 * showDrawer 弹出 Drawer
	 */

	public static PanelValue showDrawer(PanelContext ctx, String title, DrawerDirection direction, FormEditPanelIntf content
			, boolean showClose) throws Exception {
		return showDrawer(ctx, title, direction, content, new ArrayList<>(), showClose);
	}

	public static PanelValue showDrawer(PanelContext ctx, String title, DrawerDirection direction, FormEditPanelIntf content
			, List<String> okBtnCommands, boolean showClose) throws Exception {
		return showDrawer(null, ctx, title, direction, content, okBtnCommands, showClose, null, null);
	}

	public static PanelValue showDrawer(PanelContext ctx, String title, DrawerDirection direction, FormEditPanelIntf content
			, List<String> okBtnCommands, boolean showClose, SizeDto popupSize) throws Exception {
		return showDrawer(null, ctx, title, direction, content, okBtnCommands, showClose, popupSize, null);
	}

	public static PanelValue showDrawer(Class<? extends ServiceIntf> service, PanelContext ctx, String title, DrawerDirection direction, FormEditPanelIntf content
			, List<String> okBtnCommands, boolean showClose) throws Exception {
		return showDrawer(service, ctx, title, direction, content, okBtnCommands, showClose, null, null);
	}

	private static PanelValue _doShowDrawer(Function<PopupPanelParam, PanelDto> _doBuildPopupPanelFunction, Class<? extends ServiceIntf> service, PanelContext ctx, String title, DrawerDirection direction, FormEditPanelIntf content
			, List<String> okBtnCommands, boolean showClose, SizeDto popupSize, DrawerDecorationDto decoration) throws Exception {
		PopupPanelParam param = new PopupPanelParam()
				.setContent(content)
				.setOkBtnCommands(okBtnCommands)
				.setPopupSize(popupSize)
				.setServiceClass(service);

		PopupPanel<PopupPanelParam> popPanel = new PopupPanel<>();
		popPanel.setWidgetParam(param);

		PanelDto panel = _doBuildPopupPanelFunction.apply(param);
		if (popupSize != null)
			panel.setPreferSize(popupSize);
		boolean onlyGuiValue = false;
		long timeout = 24 * 60 * 60 * 1000L;

		PopDrawer dlg = PopDrawer.build(title, panel, null, null, showClose).setDirection(direction);
		dlg.setOnlyGuiValue(onlyGuiValue);
		dlg.setWaitForClose(timeout);
		dlg.setTimeout(timeout);
		dlg.setOnlyGuiValue(false);
		dlg.setDecoration(decoration);

		PanelValue panelValue = (PanelValue) ctx.callback(dlg);
		if (panelValue == null) {
			return null;
		}

		boolean clickOK = false;
		if (CmnUtil.isCollectionEmpty(okBtnCommands)) {
			clickOK = CmnUtil.isStringEqual((String) panelValue.getValue(WIDGET_ID_LAST_COMMAND), CMD_CONFIRM);
		} else {
			String lastCmd = (String) panelValue.getValue(WIDGET_ID_LAST_COMMAND);
			clickOK = okBtnCommands.contains(lastCmd);
		}
		return clickOK ? panelValue : null;
	}

	public static PanelValue showDrawer(Class<? extends ServiceIntf> service, PanelContext ctx, String title, DrawerDirection direction, FormEditPanelIntf content
			, List<String> okBtnCommands, boolean showClose, SizeDto popupSize, DrawerDecorationDto decoration) throws Exception {
		Function<PopupPanelParam, PanelDto> _doBuildPopupPanelFunction = param -> {
			try {
				PopupPanel<PopupPanelParam> popupPanel = new PopupPanel<>();
				popupPanel.setWidgetParam(param);
				return (PanelDto) popupPanel.getWidget(ctx);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
		return _doShowDrawer(_doBuildPopupPanelFunction, service, ctx, title, direction, content, okBtnCommands, showClose, popupSize, decoration);
	}

	public static PanelValue lazyShowDrawer(Class<? extends ServiceIntf> service, PanelContext ctx, String title, DrawerDirection direction, FormEditPanelIntf content
			, List<String> okBtnCommands, boolean showClose, SizeDto popupSize, DrawerDecorationDto decoration) throws Exception {
		Function<PopupPanelParam, PanelDto> _doBuildPopupPanelFunction = param -> {
			try {
				return LazyPanelUtil.build(service, ctx, PopupPanel.class, param);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
		return _doShowDrawer(_doBuildPopupPanelFunction, service, ctx, title, direction, content, okBtnCommands, showClose, popupSize, decoration);
	}

	public static <X extends DataEditParam<Y>, Y> Y showDrawerAndGetData(Class<? extends ServiceIntf> service, PanelContext ctx, String title, DrawerDirection direction, FormEditPanelIntf<X, Y> content
			, List<String> okBtnCommands, boolean showClose, SizeDto popupSize, DrawerDecorationDto decoration) throws Exception {
		Function<PopupPanelParam, PanelDto> _doBuildPopupPanelFunction = param -> {
			try {
				param.setIsReturnEditData(true);

				PopupPanel<PopupPanelParam> popupPanel = new PopupPanel<>();
				popupPanel.setWidgetParam(param);
				return (PanelDto) popupPanel.getWidget(ctx);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};

		PanelValue panelValue = _doShowDrawer(_doBuildPopupPanelFunction, service, ctx, title, direction, content, okBtnCommands, showClose, popupSize, decoration);
		if (panelValue != null) {
			return (Y) panelValue.getValue(WIDGET_ID_RETURN_EDIT_DATA);
		}
		return null;
	}

	/**
	 * showDialog 弹出Dialog
	 */

	public static PanelValue showDialog(PanelContext ctx, String title, FormEditPanelIntf content
			, boolean showClose) throws Exception {
		return showDialog(ctx, title, content, new ArrayList<>(), showClose);
	}

	public static PanelValue showDialog(PanelContext ctx, String title, FormEditPanelIntf content
			, List<String> okBtnCommands, boolean showClose) throws Exception {
		return showDialog(null, ctx, title, content, okBtnCommands, showClose);
	}

	public static PanelValue showDialog(Class<? extends ServiceIntf> service, PanelContext ctx, String title, FormEditPanelIntf content
			, List<String> okBtnCommands, boolean showClose) throws Exception {
		return showDialog(service, ctx, title, content, okBtnCommands, showClose, null);
	}

	private static PanelValue _doShowDialog(Function<PopupPanelParam, PanelDto> _doBuildPopupPanelFunction, Class<? extends ServiceIntf> service, PanelContext ctx, String title, FormEditPanelIntf content
			, List<String> okBtnCommands, boolean showClose, SizeDto popupSize) throws Exception {

		PopupPanelParam param = new PopupPanelParam()
				.setContent(content)
				.setOkBtnCommands(okBtnCommands)
				.setServiceClass(service)
				.setPopupSize(popupSize);

//		Map<String,CallBackSetting> callBackMap = buildCallbackMap(okBtnCommands);
//		param.setCallBackMap(callBackMap);

		PanelDto panel = _doBuildPopupPanelFunction.apply(param);
		if (popupSize != null) {
			panel.setPreferSize(popupSize);
		}

		boolean onlyGuiValue = false;
		long timeout = 24 * 60 * 60 * 1000L;
		PopDialog dlg = PopDialog.build(title, panel, null, null, showClose);
		dlg.setOnlyGuiValue(onlyGuiValue);
		dlg.setWaitForClose(timeout);
		dlg.setTimeout(dlg.getWaitForClose());
		dlg.setOnlyGuiValue(false);
		PanelValue panelValue = (PanelValue) ctx.callback(dlg);

		if (panelValue == null) {
			return null;
		}

		boolean clickOK = false;
		if (CmnUtil.isCollectionEmpty(okBtnCommands)) {
			clickOK = CmnUtil.isStringEqual((String) panelValue.getValue(WIDGET_ID_LAST_COMMAND), CMD_CONFIRM);
		} else {
			String lastCmd = (String) panelValue.getValue(WIDGET_ID_LAST_COMMAND);
			clickOK = okBtnCommands.contains(lastCmd);
		}
		return clickOK ? panelValue : null;
	}

	public static PanelValue showDialog(Class<? extends ServiceIntf> service, PanelContext ctx, String title, FormEditPanelIntf content
			, List<String> okBtnCommands, boolean showClose, SizeDto popupSize) throws Exception {
		Function<PopupPanelParam, PanelDto> _doBuildPopupPanelFunction = param -> {
			try {
				PopupPanel<PopupPanelParam> popupPanel = new PopupPanel<>();
				popupPanel.setWidgetParam(param);
				return (PanelDto) popupPanel.getWidget(ctx);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
		return _doShowDialog(_doBuildPopupPanelFunction, service, ctx, title, content, okBtnCommands, showClose, popupSize);
	}

	public static PanelValue lazyShowDialog(Class<? extends ServiceIntf> service, PanelContext ctx, String title, FormEditPanelIntf content
			, List<String> okBtnCommands, boolean showClose, SizeDto popupSize) throws Exception {

		Function<PopupPanelParam, PanelDto> _doBuildPopupPanelFunction = param -> {
			try {
				return LazyPanelUtil.build(service, ctx, PopupPanel.class, param);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
		return _doShowDialog(_doBuildPopupPanelFunction, service, ctx, title, content, okBtnCommands, showClose, popupSize);
	}

	public static <X extends DataEditParam<Y>, Y> Y showDialogAndGetData(Class<? extends ServiceIntf> service, PanelContext ctx, String title, FormEditPanelIntf<X, Y> content
			, List<String> okBtnCommands, boolean showClose, SizeDto popupSize) throws Exception {

		Function<PopupPanelParam, PanelDto> _doBuildPopupPanelFunction = param -> {
			try {
				param.setIsReturnEditData(true);

				PopupPanel<PopupPanelParam> popupPanel = new PopupPanel<>();
				popupPanel.setWidgetParam(param);
				return (PanelDto) popupPanel.getWidget(ctx);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};

		PanelValue panelValue = _doShowDialog(_doBuildPopupPanelFunction, service, ctx, title, content, okBtnCommands, showClose, popupSize);
		if (panelValue != null) {
			return (Y) panelValue.getValue(WIDGET_ID_RETURN_EDIT_DATA);
		}
		return null;
	}
	
	public static PanelValue showInput(PanelContext ctx, String title, PanelDto panel) throws Exception {
		boolean showClose = true;
		PopDecorationDto decoration = null;
		boolean onlyGuiValue = false;
		long timeout = 60 * 60 * 1000L;
		EscapeButtonDto ok = (EscapeButtonDto) new EscapeButtonDto().setText(getI18nString(ctx, FeI18n.Confirm)).setWidgetId("_BUTTON_OK").setConfirmStyle();

        PopDialog dlg = PopDialog.build(title, panel, ok, null, showClose).setDecoration(decoration);
        dlg.setOnlyGuiValue(onlyGuiValue);
        dlg.setWaitForClose(timeout);
        dlg.setTimeout(dlg.getWaitForClose());
        PanelValue panelValue = (PanelValue) ctx.callback(dlg);
        if (panelValue == null)
            return null;

        int clickOK = ToolUtilities.getInteger(panelValue.getValue("_BUTTON_OK"), -1);
        if (clickOK > 0)
            return panelValue;
        else
            return null;
	}
	
	public static boolean showConfirm(PanelContext ctx, String title, String msg) throws Exception
    {
		boolean defaultShowClose = true;
		boolean defaultBarrierDismissible = false;
        return showConfirm(ctx, title, msg, SizeDto.all(300, 100), defaultShowClose, null, defaultBarrierDismissible);
    }
    public static boolean showConfirm(PanelContext ctx, String title, String msg, boolean showClose) throws Exception
    {
    	boolean defaultBarrierDismissible = false;
        return showConfirm(ctx, title, msg, SizeDto.all(300, 100), showClose, null, defaultBarrierDismissible);
    }
    
    public static boolean showConfirm(PanelContext ctx, String title, String msg, SizeDto windowSize, boolean showClose, DialogDecorationDto decoration, Boolean barrierDismissible) throws Exception
    {
        long timeout = 10*60*1000;
        
        EscapeButtonDto ok = (EscapeButtonDto) new EscapeButtonDto().setText(getI18nString(ctx, FeI18n.Confirm)).setWidgetId("_BUTTON_YES").setConfirmStyle();
        EscapeButtonDto cancel = (EscapeButtonDto) new EscapeButtonDto().setText(getI18nString(ctx, FeI18n.Cancel)).setCancelStyle();

        SinglePanelDto panel = new SinglePanelDto(new LabelDto(msg));
        panel.setPreferSize(windowSize);
        
        PopDialog dlg = PopDialog.build(title, panel, ok, cancel, showClose).setDecoration(decoration).setBarrierDismissible(barrierDismissible);
        dlg.setWaitForClose(timeout);
        dlg.setTimeout(dlg.getWaitForClose());
        PanelValue panelValue = (PanelValue) ctx.callback(dlg);
        if (panelValue == null)
            return false;

        int clickOK = ToolUtilities.getInteger(panelValue.getValue("_BUTTON_YES"), -1);
        if (clickOK > 0)
            return true;
        
        return false;
    }
    
    public static boolean showConfirm(PanelContext ctx, String title, String msg, int maxRow, SizeDto windowSize, boolean showClose) throws Exception {
    	return showConfirm(ctx, PopDialogType.success, title, msg, maxRow, windowSize, showClose,false);
    }

    public static final CColor infoTextColor = new CColor(206, 214, 252, 1);
//    public static final CColor infoBgColor = new CColor(239, 241, 254, 1);
    public static final CColor warningTextColor = new CColor(250, 173, 20, 1);
//    public static final CColor warningBgColor = new CColor(254, 251, 242, 1);
    public static final CColor errorTextColor = new CColor(245, 63, 63, 1);
//    public static final CColor errorBgColor = new CColor(253,246,246, 1);
    public static final CColor successTextColor = new CColor(82, 196, 26, 1);
//    public static final CColor successBgColor = new CColor(240,254,243, 1);
    
	public static boolean showConfirm(PanelContext ctx, PopDialogType popDialogType,String title, String msg, int maxRow, SizeDto windowSize, boolean showClose,boolean barrierDismissible) throws Exception {
		long timeout = 12 * 60 * 60 * 1000L;
		EscapeButtonDto ok = (EscapeButtonDto) new EscapeButtonDto().setText(getI18nString(ctx, FeI18n.Confirm)).setWidgetId("_BUTTON_YES").setConfirmStyle();
		ok.setStyleName(FeStyleConst.common_form_submit_btn);
		EscapeButtonDto cancel = (EscapeButtonDto) new EscapeButtonDto().setText(getI18nString(ctx, FeI18n.Cancel));
		cancel.setStyleName(FeStyleConst.common_form_text_btn);
		BoxDto mainBox = new BoxDto().setVertical(true).setExpandInBox(true).setScrollable(true);
		mainBox.setMainAxisAlignment(MainAxisAlign.start).setCrossAxisAlignment(CrossAxisAlign.start);
		mainBox.addChild(FeWidgetBuilder.get().newTextArea("message", msg, maxRow, maxRow).setExpandInBox(true).setWritable(false)
				.setEditorReadonlyStyleTheme(EditorReadonlyStyleTheme.text));
		SinglePanelDto panel = new SinglePanelDto(mainBox);
		panel.setBottomBar(BoxDto.hbar(ok,cancel).setStyleName(FeStyleConst.common_form_bottom_bar_center));
		panel.setPreferSize(windowSize);

		PopDialog dlg = PopDialog.build(title, panel, null, null, showClose);
		dlg.setBarrierDismissible(barrierDismissible);
		dlg.setWaitForClose(timeout);
		dlg.setTimeout(dlg.getWaitForClose());
//		CColor bgColor = null;
//		CColor textColor = null;
//		String src = null;
		FeStyleSetting styleSetting = new PopupPanel<>().getFeStyleSetting(ctx);
//		if(popDialogType == PopDialogType.info) {
//			src = JDFICons.warning;
////			textColor = infoTextColor;
//			textColor = styleSetting.getMainColor();
////			bgColor = infoBgColor;
//		}else if(popDialogType == PopDialogType.warning) {
//			src = JDFICons.warning;
//			textColor = warningTextColor;
////			bgColor = warningBgColor;
//		}else if(popDialogType == PopDialogType.error) {
//			src = JDFICons.mistake;
//			textColor = errorTextColor;
////			bgColor = errorBgColor;
//		}else if(popDialogType == PopDialogType.success) {
//			src = JDFICons.succeed;
//			textColor = successTextColor;
////			bgColor = successBgColor;
//		}
//		if(src != null) {
//			IconDto icon = new IconDto(src,new IconStyleDto(textColor));
////			dlg.setTitleIcon(icon);
//		}
		mainBox.setDecoration(new DecorationDto().setBorder(BorderDto.horizontal(new BorderSideDto(styleSetting.getBorderColor(), 1))));
//		if(bgColor != null) {
//			mainBox.setDecoration(new DecorationDto().setBackground(bgColor));
//		}
		PopDecorationDto decoration = null;
		dlg.setDecoration(decoration);
		//设置弹窗类型会报错
		dlg.setPopDialogType(popDialogType);
		PanelValue panelValue = (PanelValue) ctx.callback(dlg);
		if (panelValue == null)
			return false;

		int clickOK = ToolUtilities.getInteger(panelValue.getValue("_BUTTON_YES"), -1);
		return clickOK > 0;
	}
	/**
	 * 显示消息确认框，自定义选项按钮
	 * @param ctx
	 * @param popDialogType	弹窗类型，可为null
	 * @param title	标题
	 * @param msg	消息
	 * @param maxRow	消息框最大行数，超出将滚动
	 * @param windowSize	窗口大小
	 * @param showClose	显示关闭按钮
	 * @param buttons	确认框中的按钮列表
	 * @return	选择的按钮序号
	 * @throws Exception
	 */
	public static int showConfirm(PanelContext ctx, PopDialogType popDialogType,String title, String msg, int maxRow, SizeDto windowSize, boolean showClose,boolean barrierDismissible,List<ButtonDto> buttons) throws Exception {
		long timeout = 12 * 60 * 60 * 1000L;
		
		BoxDto mainBox = new BoxDto().setVertical(true).setExpandInBox(true).setScrollable(true);
		mainBox.setMainAxisAlignment(MainAxisAlign.start).setCrossAxisAlignment(CrossAxisAlign.start);
		mainBox.addChild(FeWidgetBuilder.get().newTextArea("message", msg, maxRow, maxRow).setExpandInBox(true).setWritable(false)
				.setEditorReadonlyStyleTheme(EditorReadonlyStyleTheme.text));
		BoxDto bottomBar = BoxDto.hbar();
		bottomBar.setStyleName(FeStyleConst.common_form_bottom_bar_center);
		for(int i = 0;i<buttons.size();i++) {
			ButtonDto button = buttons.get(i);
			EscapeButtonDto escButton = new EscapeButtonDto();
			escButton.setText(button.getText()).setIcon(button.getIcon()).setStyleName(button.getStyleName()).setDecoration(button.getDecoration());
			escButton.setWidgetId("_BUTTON_"+i);
			bottomBar.addChild(escButton);
		}
		
		SinglePanelDto panel = new SinglePanelDto(mainBox);
		panel.setBottomBar(bottomBar);
		panel.setPreferSize(windowSize);

		PopDialog dlg = PopDialog.build(title, panel, null, null, showClose);
		dlg.setBarrierDismissible(barrierDismissible);
		dlg.setWaitForClose(timeout);
		dlg.setTimeout(dlg.getWaitForClose());
//		CColor bgColor = null;
//		CColor textColor = null;
//		String src = null;
		FeStyleSetting styleSetting = new PopupPanel<>().getFeStyleSetting(ctx);
//		if(popDialogType == PopDialogType.info) {
//			src = JDFICons.warning;
////			textColor = infoTextColor;
////			bgColor = infoBgColor;
//			textColor = styleSetting.getMainColor();
//		}else if(popDialogType == PopDialogType.warning) {
//			src = JDFICons.warning;
//			textColor = warningTextColor;
////			bgColor = warningBgColor;
//		}else if(popDialogType == PopDialogType.error) {
//			src = JDFICons.mistake;
//			textColor = errorTextColor;
////			bgColor = errorBgColor;
//		}else if(popDialogType == PopDialogType.success) {
//			src = JDFICons.succeed;
//			textColor = successTextColor;
////			bgColor = successBgColor;
//		}
//		if(src != null) {
//			IconDto icon = new IconDto(src,new IconStyleDto(textColor));
////			dlg.setTitleIcon(icon);
//		}
		mainBox.setDecoration(new DecorationDto().setBorder(BorderDto.horizontal(new BorderSideDto(styleSetting.getBorderColor(), 1))));
//		if(bgColor != null) {
//			mainBox.setDecoration(new DecorationDto().setBackground(bgColor));
//		}
		PopDecorationDto decoration = null;
		dlg.setDecoration(decoration);
		//设置弹窗类型会报错
		dlg.setPopDialogType(popDialogType);
		PanelValue panelValue = (PanelValue) ctx.callback(dlg);
		if (panelValue == null)
			return -1;
		for(int i = 0;i<buttons.size();i++) {
			int clickOK = ToolUtilities.getInteger(panelValue.getValue("_BUTTON_"+i), -1);
			if(clickOK > 0) {
				return i;
			}
		}
		return -1;
	}
	
	public static void showMessage(PanelContext ctx,PopDialogType popDialogType,String title,String msg,int maxRow,SizeDto windowSize,boolean showClose,boolean barrierDismissible)throws Exception{
		long timeout = -1;
		EscapeButtonDto ok = (EscapeButtonDto) new EscapeButtonDto().setText(getI18nString(ctx, FeI18n.Confirm)).setWidgetId("_BUTTON_YES");
		ok.setStyleName(FeStyleConst.common_form_submit_btn);
		BoxDto mainBox = new BoxDto().setVertical(true).setExpandInBox(true).setScrollable(true);
		mainBox.setMainAxisAlignment(MainAxisAlign.start).setCrossAxisAlignment(CrossAxisAlign.start);
		mainBox.addChild(FeWidgetBuilder.get().newTextArea("message", msg, maxRow, maxRow).setExpandInBox(true).setWritable(false)
				.setEditorReadonlyStyleTheme(EditorReadonlyStyleTheme.text));
		SinglePanelDto panel = new SinglePanelDto(mainBox);
		panel.setBottomBar(BoxDto.hbar(ok).setStyleName(FeStyleConst.common_form_bottom_bar_center));
		panel.setPreferSize(windowSize);
		PopDialog dlg = PopDialog.build(title, panel, null, null, showClose)
//	        		.setDecoration(popDecorationDto)
				.setBarrierDismissible(barrierDismissible)
				;
//		CColor bgColor = null;
//		CColor textColor = null;
//		String src = null;
		FeStyleSetting styleSetting = new PopupPanel<>().getFeStyleSetting(ctx);
//		if(popDialogType == PopDialogType.info) {
//			src = JDFICons.warning;
////			textColor = infoTextColor;
////			bgColor = infoBgColor;
//			textColor = styleSetting.getMainColor();
//		}else if(popDialogType == PopDialogType.warning) {
//			src = JDFICons.warning;
//			textColor = warningTextColor;
////			bgColor = warningBgColor;
//		}else if(popDialogType == PopDialogType.error) {
//			src = JDFICons.mistake;
//			textColor = errorTextColor;
////			bgColor = errorBgColor;
//		}else if(popDialogType == PopDialogType.success) {
//			src = JDFICons.succeed;
//			textColor = successTextColor;
////			bgColor = successBgColor;
//		}
//		if(src != null) {
//			IconDto icon = new IconDto(src,new IconStyleDto(textColor));
////			dlg.setTitleIcon(icon);
//		}
		mainBox.setDecoration(new DecorationDto().setBorder(BorderDto.horizontal(new BorderSideDto(styleSetting.getBorderColor(), 1))));
//		if(bgColor != null) {
//			mainBox.setDecoration(new DecorationDto().setBackground(bgColor));
//		}
		dlg.setPopDialogType(popDialogType);
		dlg.setWaitForClose(timeout);
//		dlg.setTimeout(dlg.getWaitForClose());
		ctx.callback(dlg);
	}

	@Override
	public Class<? extends ServiceIntf> getService() {
		if (widgetParam == null || CmnUtil.isStringEmpty(widgetParam.getServiceClass()))
			return IFeCmnService.class;
		try {
			return (Class<? extends ServiceIntf>) ClassFactory.getValidClassLoader().loadClass(widgetParam.getServiceClass());
		} catch (ClassNotFoundException e) {
			return IFeCmnService.class;
		}
	}

	@Override
	public WidgetDto getWidget(PanelContext panelContext) throws Exception {
		if(CmnUtil.isStringEmpty(widgetParam.getWidgetId())) {
			widgetParam.setWidgetId(ToolUtilities.allockUUIDWithUnderline());
		}
		String panelGlobalKey = ToolUtilities.allockUUIDWithUnderline();
		cacheWidgetParamInChannel(panelContext, panelGlobalKey);
		T widgetParam = getWidgetParam();
		List<String> okBtnCommands = widgetParam.getOkBtnCommands();
		FormEditPanelIntf fromEditPanel = widgetParam.getContent();
		SizeDto popupSize = widgetParam.getPopupSize();

		if (!CmnUtil.isCollectionEmpty(okBtnCommands)) {
			Map<String, CallBackSetting> callBackMap = buildCallbackMap(okBtnCommands);
			fromEditPanel.getWidgetParam().setCallBackMap(callBackMap);
		}

		WidgetDto content = fromEditPanel.getWidget(panelContext);
		BoxDto mainBox = new BoxDto().setVertical(true).setExpandInBox(false);
		mainBox.addChild(content);
		mainBox.addChildren(
				newTextEditor(WIDGET_ID_LAST_COMMAND, "")
						.setVisible(false),
				new CustomizeEditorDto()
						.setChild(new ContainerDto())
						.setWidgetId(WIDGET_ID_RETURN_EDIT_DATA)
						.setVisible(false)
		);

		SinglePanelDto panel = new SinglePanelDto()
				.setContent(mainBox);

		if (CmnUtil.isCollectionEmpty(okBtnCommands)) {
			panel.setBottomBar(
					BoxDto.hbar(
									buildButton(getI18nString(panelContext, FeI18n.Confirm), null, CMD_CONFIRM, widgetParam)
											.setStyleName(FeStyleConst.common_form_submit_btn)
//									, buildButton(FeI18n.Close, null, CMD_CLOSE, widgetParam)
//											.setMainAxisAlignment(MainAxisAlign.end)
							)
							.setStyleName(getFeStyleSetting(panelContext).getDefaultFormBottomBarStyle())
			);
		}

		if (popupSize != null) {
			panel.setPreferSize(popupSize);
		} else {
			panel.setPreferSize(content.getPreferSize());
		}

		//将内部面板的宽高给外部面板后，取消内部面板宽高，自动撑开面板
		content.setPreferSize(null);
		content.setExpandInBox(true);
		panel.setBinaryData(widgetParam);
		panel.setPanelGlobalKey(panelGlobalKey);
		panel.setWidgetId(widgetParam.getWidgetId());
		return panel;
	}

	@Override
	public Object onListener(ListenerDto listener, PanelContext panelContext, WidgetDto source) throws Exception {
		T widgetParam = getWidgetParam();
		boolean isReturnEditData = widgetParam.isReturnEditData();

		String cmd = listener.getServiceCommand();
		if (CmnUtil.isStringEqual(cmd, CMD_CONFIRM)) {
			PanelValue panelValue = queryPanelValue(panelContext, false);
			FeDeliverData<T> feDeliverData = (FeDeliverData<T>) listener.getBinaryData();
			FormEditPanelIntf editPanel = feDeliverData.getData().getContent();

			WidgetParam editPanelWidgetParam = editPanel.getWidgetParam();
			if (editPanelWidgetParam instanceof DataEditParam) {
				DataEditParam editParam = (DataEditParam) editPanelWidgetParam;
				Object editData = editParam.getData();
				Object cloneEditData = editData == null ? new HashMap<>() : ToolUtilities.clone(editData, ClassFactory.getValidClassLoader());

				List<EditorFieldDefine> fieldDefs = editPanel.getEditorFieldDefine(panelContext, cloneEditData, panelValue);
				editPanel.verifyRequireFields(panelContext, fieldDefs, panelValue);

				// 将数据写入到内存对象之后进行校验
				if(editPanel instanceof AbsFormEditPanel) {
					editPanel.writePanelValue(panelContext, cloneEditData, panelValue);
				}else {
					EditorValueHandlerFactory.create().writePanelValue(cloneEditData, panelValue.getMapValue(), fieldDefs);
				}
				editPanel.verifyValue(panelContext, fieldDefs, cloneEditData, panelValue);

				if (isReturnEditData) {
					SetCustomizeEditorValue.set(panelContext, WIDGET_ID_RETURN_EDIT_DATA, cloneEditData);
				}
			}
			setEditorValue(panelContext, WIDGET_ID_LAST_COMMAND, cmd);

			QuitPopup.quit(panelContext);
//		}else if(CmnUtil.isStringEqual(cmd, CMD_CLOSE)) {
//			QuitPopup.quit(panelContext);
		}
		return null;
	}
}
