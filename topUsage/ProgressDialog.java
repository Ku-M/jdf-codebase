package fe.util.component;

import java.awt.Color;

import com.kwaidoo.ms.tool.ToolUtilities;
import com.leavay.ms.tool.CmnUtil;

import cell.fe.cmn.IFeCmnService;
import cell.fe.progress.CFeProgressCtrlWithTextArea;
import cmn.anotation.ClassDeclare;
import cmn.anotation.FieldDeclare;
import fe.cmn.data.CColor;
import fe.cmn.editor.CCodeEditorDto;
import fe.cmn.editor.CCodeEditorLanguage;
import fe.cmn.editor.CCodeEditorTheme;
import fe.cmn.editor.CustomizeEditorDto;
import fe.cmn.panel.BoxDto;
import fe.cmn.panel.CrossAxisAlign;
import fe.cmn.panel.MainAxisAlign;
import fe.cmn.panel.PanelContext;
import fe.cmn.panel.PanelDto;
import fe.cmn.panel.SinglePanelDto;
import fe.cmn.panel.ability.PopDialog;
import fe.cmn.panel.ability.QueryEditorValue;
import fe.cmn.panel.ability.QuitPopup;
import fe.cmn.progress.ProgressBarDecorationDto;
import fe.cmn.res.JDFICons;
import fe.cmn.widget.InsetDto;
import fe.cmn.widget.LabelDto;
import fe.cmn.widget.ListenerDto;
import fe.cmn.widget.ListenerInterface;
import fe.cmn.widget.ProgressBarDto;
import fe.cmn.widget.SizeDto;
import fe.cmn.widget.WidgetDto;
import fe.cmn.widget.WindowSizeDto;
import fe.cmn.widget.decoration.BorderSideDto;
import fe.cmn.widget.decoration.ButtonDecorationDto;
import fe.cmn.widget.decoration.IconStyleDto;
import fe.util.FePaltformUtil;
import fe.util.component.param.ProgressDialogParam;
import fe.util.dto.progress.FeProgressCtrlContextDto;
import fe.util.i18n.FeI18n;
import fe.util.intf.ServiceIntf;
import fe.util.style.FeStyleSetting;

/**
 * 进度条对象使用样例：
 * PanelContext panelContext = null;
 * String title = "";
 * boolean showClose = true;
 * boolean quitWhenFinish = false;
 * //构建进度条对象
 * CFeProgressCtrlWithTextArea prog = showProgressDialog(panelContext, title, showClose, quitWhenFinish);
 * try {
 * //将进度条对象传递到接口中，接口中可定义IProgress的进度接口对象
 * //			call(prog);
 * }catch (Exception e) {
 * prog.finishError(ToolUtilities.getFullExceptionStack(e));
 * }
 *
 * @author chenxb
 */
@ClassDeclare(
	    label = "进度对话框",
	    what = "用于显示操作进度的对话框",
	    why = "提供用户界面以展示长时间操作的进度状态",
	    how = "进度条对象使用样例：\r\n" + 
	    		" * PanelContext panelContext = null;\r\n" + 
	    		" * String title = \"\";\r\n" + 
	    		" * boolean showClose = true;\r\n" + 
	    		" * boolean quitWhenFinish = false;\r\n" + 
	    		" * //构建进度条对象\r\n" + 
	    		" * CFeProgressCtrlWithTextArea prog = showProgressDialog(panelContext, title, showClose, quitWhenFinish);\r\n" + 
	    		" * try {\r\n" + 
	    		" * //将进度条对象传递到接口中，接口中可定义IProgress的进度接口对象\r\n" + 
	    		" * //			call(prog);\r\n" + 
	    		" * }catch (Exception e) {\r\n" + 
	    		" * prog.finishError(ToolUtilities.getFullExceptionStack(e));",
	    developer = "陈晓斌",
	    version = "1.0",
	    createTime = "2024-11-22",
	    updateTime = "2024-11-22"
	)
public class ProgressDialog extends AbsComponent<ProgressDialogParam> implements ListenerInterface {

	/**
	 * widgetId - 消息组件
	 */
	public final static String WIDGET_ID_MESSAGE = "WIDGET_ID_MESSAGE";
	/**
	 * widgetId - 错误详情
	 */
	public final static String WIDGET_ID_ERROR_DETAIL = "WIDGET_ID_ERROR_DETAIL";
	/**
	 * widgetId - 进度条
	 */
	public final static String WIDGET_ID_PROGRESS_BAR = "WIDGET_ID_PROGRESS_BAR";
	/**
	 * widgetId - 关闭按钮
	 */
	public final static String WIDGET_ID_ERROR_DETAIL_BUTTON = "WIDGET_ID_ERROR_DETAIL_BUTTON";
	/**
	 * widgetId - 关闭按钮
	 */
	public final static String WIDGET_ID_CLOSE_BUTTON = "WIDGET_ID_CLOSE_BUTTON";


	@FieldDeclare(label = "点击错误详情", desc = "查看错误详细信息的命令")
	public final static String CMD_VIEW_ERROR_DETAIL = "CMD_VIEW_ERROR_DETAIL";

	@FieldDeclare(label = "进度结束", desc = "标记进度完成的命令")
	public final static String CMD_FINISH = "CMD_FINISH";

	@FieldDeclare(label = "关闭页面", desc = "关闭当前页面的命令")
	public final static String CMD_CLOSE = "CMD_CLOSE";

	/**
	 *
	 */
	private static final long serialVersionUID = 6149200281631800558L;
	CFeProgressCtrlWithTextArea _progCtrl;

	public static CFeProgressCtrlWithTextArea showProgressDialog(PanelContext panelContext, String title, boolean showClose, boolean quitWhenFinish) throws Exception {
		return showProgressDialog(panelContext, title, showClose, quitWhenFinish, null, null);
	}

	public static CFeProgressCtrlWithTextArea showProgressDialog(PanelContext panelContext, String title, boolean showClose, boolean quitWhenFinish
			, String timeFormatter, ProgressBarDecorationDto progressBarDirection) throws Exception {

		ProgressDialog dialog = new ProgressDialog();
		dialog.setWidgetParam(new ProgressDialogParam()
				.setQuitWhenFinish(quitWhenFinish)
				.setTimeFormatter(timeFormatter)
				.setProgressBarDecorationDto(progressBarDirection)
		);
		PanelDto panel = (PanelDto) dialog.getWidget(panelContext);

		PopDialog.show(panelContext, title, panel, null, null, showClose, null, false);
		return dialog.getProgress();
	}


	@Override
	public Class<? extends ServiceIntf> getService() {
		return IFeCmnService.class;
	}

	public CFeProgressCtrlWithTextArea getProgress() {
		return _progCtrl;
	}

	public ProgressBarDto newProgressBar(PanelContext panelContext) throws Exception {
		ProgressDialogParam widgetParam = getWidgetParam();

		// 经过线程导出的上下文，可以传递给其它线程使用了
		FeStyleSetting setting = getFeStyleSetting(panelContext);
		CColor borderColor = setting.getBorderColor();

		ProgressBarDecorationDto dec = widgetParam.getProgressBarDecorationDto();
		if (dec == null) {
			dec = new ProgressBarDecorationDto();
			dec
					.setErrorColor(CColor.fromColor(Color.LIGHT_GRAY))
					.setDottedBorderWidth(1.0)
					.setShowMessage(false)
					.setShowCancelButton(false)
					.setBorder(borderColor.toColor(), 0.5, 10d);
		}

		ProgressBarDto bar = new ProgressBarDto();
		bar
				.setProgressCtrl(_progCtrl.getController())
				.setOnFinished(newListener(ListenerDto.class, getBuilderService(), CMD_FINISH, true, null))
				.setProgressBarDecoration(dec)
				.setWidgetId(WIDGET_ID_PROGRESS_BAR)
				.setPreferHeight(20);
		return bar;
	}

	@Override
	public WidgetDto getWidget(PanelContext panelContext) throws Exception {
		
		ProgressDialogParam widgetParam = getWidgetParam();
		String timeFormatter = widgetParam.getTimeFormatter();
		boolean isQuitWhenFinish = widgetParam.isQuitWhenFinish();

		if(CmnUtil.isStringEmpty(widgetParam.getWidgetId())) {
			widgetParam.setWidgetId(ToolUtilities.allockUUIDWithUnderline());
		}
		if(CmnUtil.isStringEmpty(widgetParam.getPanelGlobalKey())) {
			widgetParam.setPanelGlobalKey(ToolUtilities.allockUUIDWithUnderline());
		}
		cacheWidgetParamInChannel(panelContext, widgetParam.getPanelGlobalKey());
		PanelContext expContext = panelContext.cloneWithChannel()
				.setCurrentPanelGlobalKey(widgetParam.getPanelGlobalKey());

		this._progCtrl = new CFeProgressCtrlWithTextArea(new FeProgressCtrlContextDto(expContext, timeFormatter
				, WIDGET_ID_MESSAGE, WIDGET_ID_ERROR_DETAIL, WIDGET_ID_ERROR_DETAIL_BUTTON, WIDGET_ID_CLOSE_BUTTON)
				.setQuitWhenFinish(isQuitWhenFinish)
		);

		SinglePanelDto panel = SinglePanelDto.wrap(
				BoxDto.vbar(
						BoxDto.hbar(
										newProgressBar(panelContext),
										buildButton("", JDFICons.warning, CMD_VIEW_ERROR_DETAIL, null)
												.setWidgetId(WIDGET_ID_ERROR_DETAIL_BUTTON)
												.setDecoration(new ButtonDecorationDto()
														.setIcon(new IconStyleDto()
																.setSize(18))
														.setButtonBorder(new BorderSideDto(0))
														.setHoveredButtonBorder(new BorderSideDto(0))
														.setFocusedButtonBorder(new BorderSideDto(0))
														.setFocusedForegroundColor(CColor.fromColor(Color.black))
														.setFocusedBackgroundColor(CColor.transparent())
														.setBackground(CColor.transparent())
												)
												.setVisible(false)
								)
								.setExpandInBox(false)
								.setPadding(new InsetDto().setBottom(5))
						,
						new CustomizeEditorDto()
								.setChild(new LabelDto())
								.setWidgetId(WIDGET_ID_ERROR_DETAIL)
								.setVisible(false),
						new CCodeEditorDto()
								.setShowLineNumber(false)
								.setWidgetId(WIDGET_ID_MESSAGE)
								.setTheme(CCodeEditorTheme.githubTheme)
//						new TextEditorDto()
//								.setWidgetId(WIDGET_ID_MESSAGE)
//								.setMinRenderLines(20)
				)
		);

		panel
				.setPanelGlobalKey(widgetParam.getPanelGlobalKey())
				.setBottomBar(BoxDto.hbar(
										buildButton(getI18nString(panelContext, FeI18n.Close), null, CMD_CLOSE, null)
												.setWidgetId(WIDGET_ID_CLOSE_BUTTON)
												.setVisible(false)
								)
								.setMainAxisAlignment(MainAxisAlign.end)
								.setCrossAxisAlignment(CrossAxisAlign.center)
								.setPadding(new InsetDto().setTop(10.0))
				)
				.setBinaryData(widgetParam)
				.setPreferSize(SizeDto.all(600, 300));
		if(FePaltformUtil.isMiniProgram(panelContext) || FePaltformUtil.isMobile(panelContext)) {
			panel.setPreferSize(WindowSizeDto.all(1, 0.3));
		}
		panel.setWidgetId(widgetParam.getWidgetId());
		return panel;
	}

	@Override
	public Object onListener(ListenerDto listener, PanelContext panelContext, WidgetDto source) throws Exception {
		if (listener.isServiceCommand(CMD_VIEW_ERROR_DETAIL)) {
			String value = QueryEditorValue.queryString(panelContext, WIDGET_ID_ERROR_DETAIL);
			CCodeEditorDto codeEditorDto = new CCodeEditorDto();
			codeEditorDto
					.setTheme(CCodeEditorTheme.darculaTheme)
					.setLanguage(CCodeEditorLanguage.java)
					.setWidgetId(WIDGET_ID_ERROR_DETAIL);
			codeEditorDto.setValue(value);

			SinglePanelDto panel = SinglePanelDto.wrap(codeEditorDto);
			panel.setPreferSize(SizeDto.all(800, 480));
			if(FePaltformUtil.isMiniProgram(panelContext) || FePaltformUtil.isMobile(panelContext)) {
				panel.setPreferSize(WindowSizeDto.all(1, 1));
			}
			PopDialog.show(panelContext, getI18nString(panelContext, FeI18n.ErrorDetail), panel);
		} else if (listener.isServiceCommand(CMD_FINISH)) {
//			QuitPopup.quit(panelContext);
		} else if (listener.isServiceCommand(CMD_CLOSE)) {
			QuitPopup.quit(panelContext);
		}
		return null;
	}

}
