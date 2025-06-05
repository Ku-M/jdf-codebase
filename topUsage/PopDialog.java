package fe.cmn.panel.ability;

import com.leavay.common.util.ToolUtilities;
import com.leavay.ms.tool.CmnUtil;

import fe.cmn.app.PopupRouteSettingsDto;
import fe.cmn.callbackWidget.popWidget.DialogDto;
import fe.cmn.callbackWidget.popWidget.PopWidgetDto;
import fe.cmn.callbackWidget.popWidget.PopWidgetTheme;
import fe.cmn.data.BasicAbility;
import fe.cmn.editor.TextEditorDto;
import fe.cmn.panel.BoxDto;
import fe.cmn.panel.PanelContext;
import fe.cmn.panel.PanelDto;
import fe.cmn.panel.PanelValue;
import fe.cmn.panel.PopDialogType;
import fe.cmn.panel.SinglePanelDto;
import fe.cmn.widget.ButtonDto;
import fe.cmn.widget.EscapeButtonDto;
import fe.cmn.widget.IconDto;
import fe.cmn.widget.InsetDto;
import fe.cmn.widget.LabelDto;
import fe.cmn.widget.SizeDto;
import fe.cmn.widget.decoration.DialogDecorationDto;
import fe.cmn.widget.decoration.PopDecorationDto;

// 回调前端弹出对话框


public class PopDialog extends BasicAbility<Void> {
	
    private static final long serialVersionUID = 2036496216981358395L;

    public static final Boolean defaultShowClose = true;

	public static final Boolean defaultBarrierDismissible = true;
	
	public static final Boolean defaultOnlyGuiValue = false;

	public PopDialog() {
		super.setTimeout(PopWidgetDto.DEFAULT_TIME_OUT);
	}

	DialogDto dialog;

	public DialogDto getDialog() {
		return dialog;
	}

	public PopDialog setDialog(DialogDto dialog) {
		this.dialog = dialog;
		return this;
	}

	@Override
	public PopDialog setTimeout(long timeout) {
		if (this.dialog != null && timeout >= 0) {
			dialog.setWaitForClose(timeout);
		}
		super.setTimeout(timeout);
		return this;
	}

	public static DialogDto buildDialog(String title, PanelDto panel, ButtonDto ok, ButtonDto cancel) {
		DialogDto dlg = new DialogDto();
		dlg.setTitle(title);

		PanelDto content = panel;

		if (ok != null || cancel != null) {
			BoxDto box = new BoxDto();
			box.addChildren(panel.setExpandInBox(true));

			BoxDto btnBox = new BoxDto().setVertical(false);
			btnBox.addChildren(new LabelDto().setExpandInBox(true));
			if (ok != null) {
				btnBox.addChildren(ok);
			}

			if (cancel != null) {
				btnBox.addChildren(new LabelDto().setMinSize(SizeDto.width(20)));
				btnBox.addChildren(cancel);
			}
			btnBox.setMargin(new InsetDto().setTop(36));
			btnBox.setExpandInBox(false);
			box.addChildren(btnBox);

			content = new SinglePanelDto(box);
			if (panel.getPreferSize() != null) {
				content.setPreferSize(panel.getPreferSize());
				panel.setPreferSize(null);
			}
		}

		dlg.setPanel(content);
		return dlg;
	}

	public static DialogDto buildDialog(String title, PanelDto panel, ButtonDto ok, ButtonDto cancel,
			boolean showClose) {
		return buildDialog(title, panel, ok, cancel).setShowClose(showClose);
	}

	public static PanelValue pop(PanelContext ctx, DialogDto dlg) throws Exception {
		PopDialog PopDialog = new PopDialog().setDialog(dlg);
		return (PanelValue) ctx.callback(PopDialog);
	}

	public static PanelValue pop(PanelContext ctx, DialogDto dlg, Long timeout) throws Exception {
		PopDialog PopDialog = new PopDialog().setDialog(dlg);
		
		if(timeout != null) {
			PopDialog.setTimeout(timeout);
        }
		
		return (PanelValue) ctx.callback(PopDialog);
	}

	// =========================================================================================================================================

	/**
	 * 获取等待关闭的时间。
	 *
	 * @return 等待关闭的时间
	 */
	public long getWaitForClose() {
		if (this.dialog != null) {
			return this.dialog.getWaitForClose();

		}
		return -1;
	}

	/**
	 * 设置等待关闭的时间。
	 *
	 * @param waitForClose 等待关闭的时间
	 * @return 当前 PopDialog 实例
	 */
	public PopDialog setWaitForClose(long waitForClose) {
		if (this.dialog == null) {
			this.dialog = new DialogDto();
		}
		this.dialog.setWaitForClose(waitForClose);
		return this;
	}

	/**
	 * 获取弹窗标题。
	 *
	 * @return 弹窗标题
	 */
	public String getTitle() {
		if (this.dialog != null) {
			return this.dialog.getTitle();
		}
		return null;
	}

	/**
	 * 设置弹窗标题。
	 *
	 * @param title 弹窗标题
	 * @return 当前 PopDialog 实例
	 */
	public PopDialog setTitle(String title) {
		if (this.dialog == null) {
			this.dialog = new DialogDto();
		}
		this.dialog.setTitle(title);
		return this;
	}

	/**
	 * 获取是否显示关闭按钮。
	 *
	 * @return 是否显示关闭按钮
	 */
	public Boolean getShowClose() {
		if (this.dialog != null) {
			return this.dialog.isShowClose();
		}
		return null;
	}

	/**
	 * 设置是否显示关闭按钮。
	 *
	 * @param showClose 是否显示关闭按钮
	 * @return 当前 PopDialog 实例
	 */
	public PopDialog setShowClose(Boolean showClose) {
		if (this.dialog == null) {
			this.dialog = new DialogDto();
		}
		this.dialog.setShowClose(showClose);
		return this;
	}

	/**
	 * 获取是否仅返回界面修改过的数据。
	 *
	 * @return 是否仅返回界面修改过的数据
	 */
	public Boolean isOnlyGuiValue() {
		if (this.dialog != null) {
			return this.dialog.isOnlyGuiValue();
		}
		return null;
	}

	/**
	 * 设置是否仅返回界面修改过的数据。
	 *
	 * @param onlyGuiValue 是否仅返回界面修改过的数据
	 * @return 当前 PopDialog 实例
	 */
	public PopDialog setOnlyGuiValue(Boolean onlyGuiValue) {
		if (this.dialog == null) {
			this.dialog = new DialogDto();
		}
		this.dialog.setOnlyGuiValue(onlyGuiValue);
		return this;
	}

	/**
	 * 获取面板组件。
	 *
	 * @return 面板组件
	 */
	public PanelDto getPanel() {
		if (this.dialog != null) {
			return this.dialog.getPanel();
		}
		return null;
	}

	/**
	 * 设置面板组件。
	 *
	 * @param panel 面板组件
	 * @return 当前 PopDialog 实例
	 */
	public PopDialog setPanel(PanelDto panel) {
		if (this.dialog == null) {
			this.dialog = new DialogDto();
		}
		this.dialog.setPanel(panel);
		return this;
	}

	/**
	 * 获取是否允许点击外部关闭。
	 *
	 * @return 是否允许点击外部关闭
	 */
	public Boolean getBarrierDismissible() {
		if (this.dialog != null) {
			return this.dialog.getBarrierDismissible();
		}
		return null;
	}

	/**
	 * 设置是否允许点击外部关闭。
	 *
	 * @param barrierDismissible 是否允许点击外部关闭
	 * @return 当前 PopDialog 实例
	 */
	public PopDialog setBarrierDismissible(Boolean barrierDismissible) {
		if (this.dialog == null) {
			this.dialog = new DialogDto();
		}
		this.dialog.setBarrierDismissible(barrierDismissible);
		return this;
	}

	/**
	 * 获取弹窗样式。
	 *
	 * @return 弹窗样式
	 */
	public PopDecorationDto getDecoration() {
		if (this.dialog != null) {
			return this.dialog.getDecoration();
		}
		return null;
	}

	/**
	 * 设置弹窗样式。
	 *
	 * @param decoration 弹窗样式
	 * @return 当前 PopDialog 实例
	 * @throws Exception
	 */
	public PopDialog setDecoration(PopDecorationDto decoration) throws Exception{
		if (this.dialog == null) {
			this.dialog = new DialogDto();
		}
		
		DialogDto.setPopDecoration(dialog, decoration);
		
		return this;
	}

	/**
	 * 获取标题图标。
	 *
	 * @return 标题图标
	 */
	public IconDto getTitleIcon() {
		if (this.dialog != null) {
			return this.dialog.getTitleIcon();
		}
		return null;
	}

	/**
	 * 设置标题图标。
	 *
	 * @param titleIcon 标题图标
	 * @return 当前 PopDialog 实例
	 */
	public PopDialog setTitleIcon(IconDto titleIcon) {
		if (this.dialog == null) {
			this.dialog = new DialogDto();
		}

		this.dialog.setTitleIcon(titleIcon);
		return this;
	}

	/**
	 * 获取弹出框类型。
	 *
	 * @return 弹出框类型
	 */
	public PopDialogType getPopDialogType() {
		if (this.dialog != null) {
			return this.dialog.getPopDialogType();
		}
		return null;
	}

	/**
	 * 设置弹出框类型。
	 *
	 * @param popDialogType 弹出框类型
	 * @return 当前 PopDialog 实例
	 */
	public PopDialog setPopDialogType(PopDialogType popDialogType) {
		if (this.dialog == null) {
			this.dialog = new DialogDto();
		}

		this.dialog.setPopDialogType(popDialogType);
		return this;
	}

	/**
	 * 获取弹出框路由设置。
	 *
	 * @return 弹出框路由设置
	 */
	public PopupRouteSettingsDto getRouteSettings() {
		if (this.dialog != null) {
			return this.dialog.getRouteSettings();
		}
		return null;
	}

	/**
	 * 设置弹出框路由设置。
	 *
	 * @param routeSettings 弹出框路由设置
	 * @return 当前 PopDialog 实例
	 */
	public PopDialog setRouteSettings(PopupRouteSettingsDto routeSettings) {
		if (this.dialog == null) {
			this.dialog = new DialogDto();
		}

		this.dialog.setRouteSettings(routeSettings);
		return this;
	}

	// =========================================================================================================================================

	public static PanelDto buildPanel(PanelDto panel, ButtonDto ok, ButtonDto cancel) {
		PanelDto content = panel;

		if (ok != null || cancel != null) {
			BoxDto box = new BoxDto();
			box.addChildren(panel.setExpandInBox(true));

			BoxDto btnBox = new BoxDto().setVertical(false);
			btnBox.addChildren(new LabelDto().setExpandInBox(true));
			if (ok != null) {
				btnBox.addChildren(ok);
			}

			if (cancel != null) {
				btnBox.addChildren(new LabelDto().setMinSize(SizeDto.width(20)));
				btnBox.addChildren(cancel);
			}
			btnBox.setMargin(new InsetDto().setTop(36));
			btnBox.setExpandInBox(false);
			box.addChildren(btnBox);

			content = new SinglePanelDto(box);
			if (panel.getPreferSize() != null) {
				content.setPreferSize(panel.getPreferSize());
				panel.setPreferSize(null);
			}
		}

		return content;
	}

	public static PopDialog build(String title, PanelDto panel, ButtonDto ok, ButtonDto cancel) {
		return new PopDialog().setDialog(buildDialog(title, panel, ok, cancel));
	}

	public static PopDialog build(String title, PanelDto panel, ButtonDto ok, ButtonDto cancel, boolean showClose) {
		return new PopDialog().setDialog(buildDialog(title, panel, ok, cancel).setShowClose(showClose));
	}

	// __________________________________________________________________________________________________________________________________________________________________________________________
	/**
	 * 显示一个基础对话框（无按钮）
	 * 
	 * @param ctx   面板上下文对象，用于处理回调
	 * @param title 对话框标题
	 * @param panel 对话框主内容面板
	 * @throws Exception
	 */
	public static void post(PanelContext ctx, String title, PanelDto panel) throws Exception {
		DialogDto dlg = buildDialog(title, panel, null, null);
		pop(ctx, dlg);
	}

	/**
	 * 显示带自定义装饰的基础对话框（无按钮）
	 * 
	 * @param ctx        面板上下文对象，用于处理回调
	 * @param title      对话框标题
	 * @param panel      对话框主内容面板
	 * @param decoration 对话框装饰配置（边框/背景等样式）
	 * @throws Exception
	 */
	public static void post(PanelContext ctx, String title, PanelDto panel, DialogDecorationDto decoration)
			throws Exception {
		DialogDto dlg = buildDialog(title, panel, null, null).setDecoration(decoration);
		pop(ctx, dlg);
	}

	/**
	 * 显示带确认/取消按钮的标准对话框
	 * 
	 * @param ctx    面板上下文对象，用于处理回调
	 * @param title  对话框标题
	 * @param panel  对话框主内容面板
	 * @param ok     确认按钮配置对象
	 * @param cancel 取消按钮配置对象
	 * @throws Exception
	 */
	public static void post(PanelContext ctx, String title, PanelDto panel, ButtonDto ok, ButtonDto cancel)
			throws Exception {
		DialogDto dlg = buildDialog(title, panel, ok, cancel);
		pop(ctx, dlg);
	}

	/**
	 * 显示可控制关闭按钮的对话框
	 * 
	 * @param ctx       面板上下文对象，用于处理回调
	 * @param title     对话框标题
	 * @param panel     对话框主内容面板
	 * @param ok        确认按钮配置对象
	 * @param cancel    取消按钮配置对象
	 * @param showClose 是否显示右上角关闭按钮
	 * @throws Exception
	 */
	public static void post(PanelContext ctx, String title, PanelDto panel, ButtonDto ok, ButtonDto cancel,
			boolean showClose) throws Exception {
		DialogDto dlg = buildDialog(title, panel, ok, cancel, showClose);
		pop(ctx, dlg);
	}

	// __________________________________________________________________________________________________________________________________________________________________________________________
	/**
	 * 显示基础对话框（无操作按钮）
	 * 
	 * @param ctx   面板上下文对象，用于处理回调
	 * @param title 对话框标题
	 * @param panel 对话框主内容面板
	 * @throws Exception
	 */
	public static void show(PanelContext ctx, String title, PanelDto panel) throws Exception {
		post(ctx, title, panel);
	}

	/**
	 * 显示可控制关闭按钮的基础对话框
	 * 
	 * @param ctx       面板上下文对象
	 * @param title     对话框标题
	 * @param panel     对话框主内容面板
	 * @param showClose 是否显示右上角关闭按钮
	 * @throws Exception
	 */
	public static void show(PanelContext ctx, String title, PanelDto panel, boolean showClose) throws Exception {
		DialogDto dlg = buildDialog(title, panel, null, null, showClose);
		pop(ctx, dlg);
	}

	/**
	 * 显示带单个确认按钮的对话框
	 * 
	 * @param ctx   面板上下文对象
	 * @param title 对话框标题
	 * @param panel 对话框主内容面板
	 * @param ok    确认按钮的配置对象（样式/文本等）
	 * @throws Exception
	 */
	public static void show(PanelContext ctx, String title, PanelDto panel, ButtonDto ok) throws Exception {
		DialogDto dlg = buildDialog(title, panel, ok, null);
		pop(ctx, dlg);
	}

	/**
	 * 显示带确认按钮且可控制关闭按钮的对话框
	 * 
	 * @param ctx       面板上下文对象
	 * @param title     对话框标题
	 * @param panel     对话框主内容面板
	 * @param ok        确认按钮配置
	 * @param showClose 是否显示关闭按钮
	 * @throws Exception
	 */
	public static void show(PanelContext ctx, String title, PanelDto panel, ButtonDto ok, boolean showClose)
			throws Exception {
		DialogDto dlg = buildDialog(title, panel, ok, null, showClose);
		pop(ctx, dlg);
	}

	/**
	 * 显示带确认按钮、关闭控制和标题图标的对话框
	 * 
	 * @param ctx       面板上下文对象
	 * @param title     对话框标题
	 * @param panel     对话框主内容面板
	 * @param ok        确认按钮配置
	 * @param showClose 是否显示关闭按钮
	 * @param titleIcon 标题栏图标配置
	 * @throws Exception
	 */
	public static void show(PanelContext ctx, String title, PanelDto panel, ButtonDto ok, boolean showClose,
			IconDto titleIcon) throws Exception {
		DialogDto dlg = buildDialog(title, panel, ok, null, showClose).setTitleIcon(titleIcon);
		pop(ctx, dlg);
	}

	/**
	 * 显示带主题样式的对话框
	 * 
	 * @param ctx       面板上下文对象
	 * @param title     对话框标题
	 * @param panel     对话框主内容面板
	 * @param ok        确认按钮配置
	 * @param showClose 是否显示关闭按钮
	 * @param popType   对话框主题类型（如警告/普通/错误等样式）
	 * @throws Exception
	 */
	public static void show(PanelContext ctx, String title, PanelDto panel, ButtonDto ok, boolean showClose,
			PopDialogType popType) throws Exception {
		DialogDto dlg = buildDialog(title, panel, ok, null, showClose).setPopDialogType(popType);
		pop(ctx, dlg);
	}

	/**
	 * 显示带自定义装饰的对话框
	 * 
	 * @param ctx        面板上下文对象
	 * @param title      对话框标题
	 * @param panel      对话框主内容面板
	 * @param ok         确认按钮配置
	 * @param showClose  是否显示关闭按钮
	 * @param decoration 对话框装饰配置（边距/阴影等样式）
	 * @throws Exception
	 */
	public static void show(PanelContext ctx, String title, PanelDto panel, ButtonDto ok, boolean showClose,
			DialogDecorationDto decoration) throws Exception {
		DialogDto dlg = buildDialog(title, panel, ok, null, showClose).setDecoration(decoration);
		pop(ctx, dlg);
	}

	/**
	 * 显示可控制外部点击关闭的对话框
	 * 
	 * @param ctx                面板上下文对象
	 * @param title              对话框标题
	 * @param panel              对话框主内容面板
	 * @param ok                 确认按钮配置
	 * @param showClose          是否显示关闭按钮
	 * @param decoration         对话框装饰配置
	 * @param barrierDismissible 点击对话框外部区域是否关闭对话框
	 * @throws Exception
	 */
	public static void show(PanelContext ctx, String title, PanelDto panel, ButtonDto ok, boolean showClose,
			DialogDecorationDto decoration, Boolean barrierDismissible) throws Exception {
		DialogDto dlg = buildDialog(title, panel, ok, null, showClose).setDecoration(decoration)
				.setBarrierDismissible(barrierDismissible);
		pop(ctx, dlg);
	}

	/**
	 * 显示全功能对话框（含图标+外部点击控制）
	 * 
	 * @param ctx                面板上下文对象
	 * @param title              对话框标题
	 * @param panel              对话框主内容面板
	 * @param ok                 确认按钮配置
	 * @param showClose          是否显示关闭按钮
	 * @param decoration         对话框装饰配置
	 * @param barrierDismissible 点击外部关闭控制
	 * @param titleIcon          标题栏图标配置
	 * @throws Exception
	 */
	public static void show(PanelContext ctx, String title, PanelDto panel, ButtonDto ok, boolean showClose,
			DialogDecorationDto decoration, Boolean barrierDismissible, IconDto titleIcon) throws Exception {
		DialogDto dlg = buildDialog(title, panel, ok, null, showClose).setDecoration(decoration)
				.setBarrierDismissible(barrierDismissible).setTitleIcon(titleIcon);
		pop(ctx, dlg);
	}

	/**
	 * 显示带主题类型的高级对话框
	 * 
	 * @param ctx                面板上下文对象
	 * @param title              对话框标题
	 * @param panel              对话框主内容面板
	 * @param ok                 确认按钮配置
	 * @param showClose          是否显示关闭按钮
	 * @param decoration         对话框装饰配置
	 * @param barrierDismissible 点击外部关闭控制
	 * @param popType            对话框主题类型
	 * @throws Exception
	 */
	public static void show(PanelContext ctx, String title, PanelDto panel, ButtonDto ok, boolean showClose,
			DialogDecorationDto decoration, Boolean barrierDismissible, PopDialogType popType) throws Exception {
		DialogDto dlg = buildDialog(title, panel, ok, null, showClose).setDecoration(decoration)
				.setBarrierDismissible(barrierDismissible).setPopDialogType(popType);
		pop(ctx, dlg);
	}

	/**
	 * 以非阻塞方式显示带有“确认”和“取消”按钮的弹出框，并可控制是否显示关闭按钮。
	 *
	 * @param ctx       上下文对象
	 * @param title     弹窗标题
	 * @param panel     面板组件
	 * @param ok        确认按钮
	 * @param cancel    取消按钮
	 * @param showClose 是否显示关闭按钮
	 * @throws Exception 抛出异常
	 */
	public static void show(PanelContext ctx, String title, PanelDto panel, ButtonDto ok, ButtonDto cancel,
			boolean showClose) throws Exception {
		DialogDto dlg = buildDialog(title, panel, ok, cancel, showClose);
		pop(ctx, dlg);
	}

	/**
	 * 以非阻塞方式显示带有“确认”和“取消”按钮的弹出框，并可自定义样式和是否允许点击外部关闭。
	 *
	 * @param ctx                上下文对象
	 * @param title              弹窗标题
	 * @param panel              面板组件
	 * @param ok                 确认按钮
	 * @param cancel             取消按钮
	 * @param showClose          是否显示关闭按钮
	 * @param decoration         弹窗样式
	 * @param barrierDismissible 是否允许点击外部关闭
	 * @throws Exception 抛出异常
	 */
	public static void show(PanelContext ctx, String title, PanelDto panel, ButtonDto ok, ButtonDto cancel,
			boolean showClose, DialogDecorationDto decoration, Boolean barrierDismissible) throws Exception {
		DialogDto dlg = buildDialog(title, panel, ok, cancel, showClose).setDecoration(decoration)
				.setBarrierDismissible(barrierDismissible);
		pop(ctx, dlg);
	}

	/**
	 * 以非阻塞方式显示弹窗，其中带有“确认”和“取消”按钮，支持自定义弹窗样式、外部点击关闭以及标题图标。
	 *
	 * @param ctx                上下文对象
	 * @param title              弹窗标题
	 * @param panel              面板组件
	 * @param ok                 确认按钮
	 * @param cancel             取消按钮
	 * @param showClose          是否显示关闭按钮
	 * @param decoration         弹窗样式
	 * @param barrierDismissible 是否允许通过点击外部关闭
	 * @param titleIcon          标题图标
	 * @throws Exception 抛出异常
	 */
	public static void show(PanelContext ctx, String title, PanelDto panel, ButtonDto ok, ButtonDto cancel,
			boolean showClose, DialogDecorationDto decoration, Boolean barrierDismissible, IconDto titleIcon)
			throws Exception {
		DialogDto dlg = buildDialog(title, panel, ok, cancel, showClose).setDecoration(decoration)
				.setBarrierDismissible(barrierDismissible).setTitleIcon(titleIcon);
		;
		pop(ctx, dlg);
	}

	/**
	 * 以非阻塞方式显示弹出框，带“确认”按钮，并可自定义标题图标和弹出框类型。
	 *
	 * @param ctx                上下文对象
	 * @param title              弹窗标题
	 * @param panel              面板组件
	 * @param ok                 确认按钮
	 * @param showClose          是否显示关闭按钮
	 * @param decoration         弹窗样式
	 * @param barrierDismissible 是否允许点击外部关闭
	 * @param titleIcon          标题图标
	 * @param popDialogType      弹出框类型
	 * @throws Exception 抛出异常
	 */

	public static void show(PanelContext ctx, String title, PanelDto panel, ButtonDto ok, ButtonDto cancel,
			boolean showClose, DialogDecorationDto decoration, Boolean barrierDismissible, PopDialogType popDialogType)
			throws Exception {
		DialogDto dlg = buildDialog(title, panel, ok, cancel, showClose).setDecoration(decoration)
				.setBarrierDismissible(barrierDismissible).setPopDialogType(popDialogType);
		pop(ctx, dlg);
	}

	// 弹出阻塞式对话框，等待对话框关闭并回传对话框中输入的数据，如果cancel则返回NULL
	// __________________________________________________________________________________________________________________________________________________________________________________________
	/**
	 * 显示阻塞式文本输入对话框（单行文本输入）
	 * 
	 * @param ctx      面板上下文对象，用于处理回调
	 * @param title    对话框标题
	 * @param initText 输入框初始文本内容
	 * @return 用户输入的字符串，取消操作返回null
	 * @throws Exception
	 */
	public static String showInput(PanelContext ctx, String title, String initText) throws Exception {
		EscapeButtonDto ok = (EscapeButtonDto) new EscapeButtonDto().setText("确认").setWidgetId("_BUTTON_OK")
				.setConfirmStyle();

		DialogDto dlg = buildDialog(title,
				SinglePanelDto.wrap(new TextEditorDto(initText).setWidgetId("input_text_editor")), ok, null).setOnlyGuiValue(defaultOnlyGuiValue);
		PanelValue pv = pop(ctx, dlg, PopWidgetDto.DEFAULT_TIME_OUT);
		if (pv == null)
			return null;

		int clickOK = ToolUtilities.getInteger(pv.getValue("_BUTTON_OK"), -1);
		if (clickOK > 0)
			return CmnUtil.getString(pv.getValue("input_text_editor"), null);
		else
			return null;
	}

	/**
	 * 显示标准输入对话框（带确认按钮）
	 * 
	 * @param ctx   面板上下文对象
	 * @param title 对话框标题
	 * @param panel 自定义输入面板
	 * @return 包含输入数据的PanelValue对象，取消操作返回null
	 * @throws Exception
	 */
	public static PanelValue showInput(PanelContext ctx, String title, PanelDto panel) throws Exception {
		return showInput(ctx, title, panel, defaultOnlyGuiValue, PopWidgetDto.DEFAULT_TIME_OUT, defaultShowClose);
	}

	/**
	 * 显示可控制关闭按钮的输入对话框
	 * 
	 * @param ctx       面板上下文对象
	 * @param title     对话框标题
	 * @param panel     自定义输入面板
	 * @param showClose 是否显示关闭按钮
	 * @return 输入数据结果对象
	 * @throws Exception
	 */
	public static PanelValue showInput(PanelContext ctx, String title, PanelDto panel, boolean showClose)
			throws Exception {
		return showInput(ctx, title, panel, defaultOnlyGuiValue, PopWidgetDto.DEFAULT_TIME_OUT, showClose);
	}

	/**
	 * 显示带数据处理选项的输入对话框
	 * 
	 * @param ctx          面板上下文对象
	 * @param title        对话框标题
	 * @param panel        自定义输入面板
	 * @param onlyGuiValue 是否仅返回GUI组件值（true跳过业务数据转换）
	 * @param timeout      对话框等待超时时间（毫秒）
	 * @return 输入数据结果对象
	 * @throws Exception
	 */
	public static PanelValue showInput(PanelContext ctx, String title, PanelDto panel, boolean onlyGuiValue,
			long timeout) throws Exception {
		return showInput(ctx, title, panel, onlyGuiValue, timeout, defaultShowClose);
	}

	/**
	 * 显示全功能输入对话框（基础版）
	 * 
	 * @param ctx          面板上下文对象
	 * @param title        对话框标题
	 * @param panel        自定义输入面板
	 * @param onlyGuiValue 是否仅返回GUI原始值
	 * @param timeout      超时时间（毫秒）
	 * @param showClose    是否显示关闭按钮
	 * @return 输入数据结果对象
	 * @throws Exception
	 */
	public static PanelValue showInput(PanelContext ctx, String title, PanelDto panel, boolean onlyGuiValue,
			long timeout, boolean showClose) throws Exception {
		return showInput(ctx, title, panel, onlyGuiValue, timeout, showClose, null);
	}

	/**
	 * 显示带装饰样式的输入对话框
	 * 
	 * @param ctx          面板上下文对象
	 * @param title        对话框标题
	 * @param panel        自定义输入面板
	 * @param onlyGuiValue 数据处理模式开关
	 * @param timeout      超时设置
	 * @param showClose    关闭按钮可见性
	 * @param decoration   对话框装饰配置（边框/边距等）
	 * @return 输入数据结果对象
	 * @throws Exception
	 */
	public static PanelValue showInput(PanelContext ctx, String title, PanelDto panel, boolean onlyGuiValue,
			long timeout, boolean showClose, DialogDecorationDto decoration) throws Exception {
		EscapeButtonDto ok = (EscapeButtonDto) new EscapeButtonDto().setText("确认").setWidgetId("_BUTTON_OK")
				.setConfirmStyle();

		DialogDto dlg = buildDialog(title, panel, ok, null, showClose).setDecoration(decoration);
		dlg.setOnlyGuiValue(onlyGuiValue);

		PanelValue panelValue = pop(ctx, dlg, timeout);
		if (panelValue == null)
			return null;

		int clickOK = ToolUtilities.getInteger(panelValue.getValue("_BUTTON_OK"), -1);
		if (clickOK > 0)
			return panelValue;
		else
			return null;
	}

	/**
	 * 显示带标题图标的输入对话框
	 * 
	 * @param ctx          面板上下文对象
	 * @param title        对话框标题
	 * @param panel        输入面板
	 * @param onlyGuiValue 数据处理模式
	 * @param timeout      超时时间
	 * @param showClose    关闭按钮可见性
	 * @param decoration   装饰配置
	 * @param titleIcon    标题栏图标配置
	 * @return 输入数据结果对象
	 * @throws Exception
	 */
	public static PanelValue showInput(PanelContext ctx, String title, PanelDto panel, boolean onlyGuiValue,
			long timeout, boolean showClose, DialogDecorationDto decoration, IconDto titleIcon) throws Exception {
		EscapeButtonDto ok = (EscapeButtonDto) new EscapeButtonDto().setText("确认").setWidgetId("_BUTTON_OK")
				.setConfirmStyle();

		DialogDto dlg = buildDialog(title, panel, ok, null, showClose).setDecoration(decoration)
				.setTitleIcon(titleIcon);
		dlg.setOnlyGuiValue(onlyGuiValue);

		PanelValue panelValue = pop(ctx, dlg, timeout);
		if (panelValue == null)
			return null;

		int clickOK = ToolUtilities.getInteger(panelValue.getValue("_BUTTON_OK"), -1);
		if (clickOK > 0)
			return panelValue;
		else
			return null;
	}

	/**
	 * 显示指定主题样式的输入对话框
	 * 
	 * @param ctx          面板上下文对象
	 * @param title        对话框标题
	 * @param panel        输入面板
	 * @param onlyGuiValue 数据处理模式
	 * @param timeout      超时时间
	 * @param showClose    关闭按钮可见性
	 * @param decoration   装饰配置
	 * @param popType      对话框主题类型
	 * @return 输入数据结果对象
	 * @throws Exception
	 */
	public static PanelValue showInput(PanelContext ctx, String title, PanelDto panel, boolean onlyGuiValue,
			long timeout, boolean showClose, DialogDecorationDto decoration, PopDialogType popType) throws Exception {
		EscapeButtonDto ok = (EscapeButtonDto) new EscapeButtonDto().setText("确认").setWidgetId("_BUTTON_OK")
				.setConfirmStyle();

		DialogDto dlg = buildDialog(title, panel, ok, null, showClose).setDecoration(decoration)
				.setPopDialogType(popType);
		dlg.setOnlyGuiValue(onlyGuiValue);

		PanelValue panelValue = pop(ctx, dlg, timeout);
		if (panelValue == null)
			return null;

		int clickOK = ToolUtilities.getInteger(panelValue.getValue("_BUTTON_OK"), -1);
		if (clickOK > 0)
			return panelValue;
		else
			return null;
	}

	/**
	 * 显示双按钮确认输入对话框
	 * 
	 * @param ctx            面板上下文对象
	 * @param title          对话框标题
	 * @param panel          输入面板
	 * @param onlyGuiValue   数据处理模式
	 * @param timeout        超时时间
	 * @param showClose      关闭按钮可见性
	 * @param confirmBtnText 确认按钮自定义文本
	 * @param cancelBtnText  取消按钮自定义文本
	 * @return 用户确认时返回输入数据，否则返回null
	 * @throws Exception
	 */
	public static PanelValue showInput(PanelContext ctx, String title, PanelDto panel, boolean onlyGuiValue,
			long timeout, boolean showClose, String confirmBtnText, String cancelBtnText) throws Exception {
		return showInput(ctx, title, panel, onlyGuiValue, timeout, showClose, confirmBtnText, cancelBtnText, null,
				defaultBarrierDismissible);
	}

	/**
	 * 显示完全自定义的双按钮输入对话框
	 * 
	 * @param ctx                面板上下文对象
	 * @param title              对话框标题
	 * @param panel              输入面板
	 * @param onlyGuiValue       数据处理模式
	 * @param timeout            超时时间
	 * @param showClose          关闭按钮可见性
	 * @param confirmBtnText     确认按钮文本
	 * @param cancelBtnText      取消按钮文本
	 * @param decoration         装饰配置
	 * @param barrierDismissible 点击外部关闭控制
	 * @return 输入数据结果对象
	 * @throws Exception
	 */
	public static PanelValue showInput(PanelContext ctx, String title, PanelDto panel, boolean onlyGuiValue,
			long timeout, boolean showClose, String confirmBtnText, String cancelBtnText,
			DialogDecorationDto decoration, Boolean barrierDismissible) throws Exception {
		EscapeButtonDto confirmBtn = (EscapeButtonDto) new EscapeButtonDto().setText(confirmBtnText)
				.setWidgetId("_BUTTON_OK").setConfirmStyle();

		EscapeButtonDto cancelBtn = (EscapeButtonDto) new EscapeButtonDto().setText(cancelBtnText).setCancelStyle();

		DialogDto dlg = buildDialog(title, panel, confirmBtn, cancelBtn, showClose).setDecoration(decoration)
				.setBarrierDismissible(barrierDismissible);
		dlg.setOnlyGuiValue(onlyGuiValue);

		PanelValue panelValue = pop(ctx, dlg, timeout);
		if (panelValue == null)
			return null;

		int clickOK = ToolUtilities.getInteger(panelValue.getValue("_BUTTON_OK"), -1);
		if (clickOK > 0)
			return panelValue;
		else
			return null;
	}

	// 弹出阻塞式对话框，等待对话框关闭并回传对话框中输入的数据，如果cancel则返回NULL
	// __________________________________________________________________________________________________________________________________________________________________________________________
	/**
	 * 显示无操作按钮的输入对话框（依赖外部关闭）
	 * 
	 * @param ctx   面板上下文对象，用于处理回调
	 * @param title 对话框标题
	 * @param panel 自定义输入面板
	 * @return 直接返回用户输入数据（无确认按钮，依赖对话框关闭事件）
	 * @throws Exception
	 */
	public static PanelValue showInputwithOutButton(PanelContext ctx, String title, PanelDto panel) throws Exception {
		return showInputwithOutButton(ctx, title, panel, defaultOnlyGuiValue, PopWidgetDto.DEFAULT_TIME_OUT, defaultShowClose);
	}

	/**
	 * 显示可控制关闭按钮的无按钮对话框
	 * 
	 * @param ctx       面板上下文对象
	 * @param title     对话框标题
	 * @param panel     自定义输入面板
	 * @param showClose 是否显示右上角关闭按钮
	 * @return 用户输入数据集合
	 * @throws Exception
	 */
	public static PanelValue showInputwithOutButton(PanelContext ctx, String title, PanelDto panel, boolean showClose)
			throws Exception {
		return showInputwithOutButton(ctx, title, panel, defaultOnlyGuiValue, PopWidgetDto.DEFAULT_TIME_OUT, showClose);
	}

	/**
	 * 显示带数据处理模式的无按钮对话框
	 * 
	 * @param ctx          面板上下文对象
	 * @param title        对话框标题
	 * @param panel        自定义输入面板
	 * @param onlyGuiValue true表示仅返回原始GUI组件值（跳过业务数据转换）
	 * @param timeout      对话框等待超时时间（毫秒）
	 * @return 输入数据结果对象
	 * @throws Exception
	 */
	public static PanelValue showInputwithOutButton(PanelContext ctx, String title, PanelDto panel,
			boolean onlyGuiValue, long timeout) throws Exception {
		return showInputwithOutButton(ctx, title, panel, onlyGuiValue, timeout, defaultShowClose);
	}

	/**
	 * 显示全配置无按钮对话框（基础版）
	 * 
	 * @param ctx          面板上下文对象
	 * @param title        对话框标题
	 * @param panel        自定义输入面板
	 * @param onlyGuiValue 数据处理模式开关
	 * @param timeout      超时时间（毫秒）
	 * @param showClose    关闭按钮可见性
	 * @return 输入数据结果对象
	 * @throws Exception
	 */
	public static PanelValue showInputwithOutButton(PanelContext ctx, String title, PanelDto panel,
			boolean onlyGuiValue, long timeout, boolean showClose) throws Exception {
		return showInputwithOutButton(ctx, title, panel, onlyGuiValue, timeout, showClose, null,
				defaultBarrierDismissible);
	}

	/**
	 * 显示带装饰配置的无按钮对话框
	 * 
	 * @param ctx                面板上下文对象
	 * @param title              对话框标题
	 * @param panel              自定义输入面板
	 * @param onlyGuiValue       数据处理模式
	 * @param timeout            超时时间
	 * @param showClose          关闭按钮可见性
	 * @param decoration         对话框视觉装饰配置（边距/背景等）
	 * @param barrierDismissible 点击对话框外部区域是否自动关闭
	 * @return 输入数据结果对象（无论以何种方式关闭都直接返回数据）
	 * @throws Exception
	 */
	public static PanelValue showInputwithOutButton(PanelContext ctx, String title, PanelDto panel,
			boolean onlyGuiValue, long timeout, boolean showClose, PopDecorationDto decoration,
			Boolean barrierDismissible) throws Exception {
		DialogDto dlg = buildDialog(title, panel, null, null, showClose)
				.setBarrierDismissible(barrierDismissible).setOnlyGuiValue(onlyGuiValue);
		
		DialogDto.setPopDecoration(dlg, decoration);
		
		PanelValue panelValue = pop(ctx, dlg, timeout);
		return panelValue;
	}

	/**
	 * 显示带标题图标的无按钮对话框
	 * 
	 * @param ctx                面板上下文对象
	 * @param title              对话框标题
	 * @param panel              自定义输入面板
	 * @param onlyGuiValue       数据处理模式
	 * @param timeout            超时时间
	 * @param showClose          关闭按钮可见性
	 * @param decoration         装饰配置
	 * @param barrierDismissible 外部点击关闭控制
	 * @param titleIcon          标题栏图标配置
	 * @return 输入数据结果对象
	 * @throws Exception
	 */
	public static PanelValue showInputwithOutButton(PanelContext ctx, String title, PanelDto panel,
			boolean onlyGuiValue, long timeout, boolean showClose, PopDecorationDto decoration,
			Boolean barrierDismissible, IconDto titleIcon) throws Exception {
		DialogDto dlg = buildDialog(title, panel, null, null, showClose)
				.setBarrierDismissible(barrierDismissible).setTitleIcon(titleIcon).setOnlyGuiValue(onlyGuiValue);
		
		DialogDto.setPopDecoration(dlg, decoration);
		
		PanelValue panelValue = pop(ctx, dlg, timeout);
		return panelValue;
	}

	/**
	 * 显示指定主题样式的无按钮对话框
	 * 
	 * @param ctx                面板上下文对象
	 * @param title              对话框标题
	 * @param panel              自定义输入面板
	 * @param onlyGuiValue       数据处理模式
	 * @param timeout            超时时间
	 * @param showClose          关闭按钮可见性
	 * @param decoration         装饰配置
	 * @param barrierDismissible 外部点击关闭控制
	 * @param popType            对话框主题类型（如警告/信息样式）
	 * @return 输入数据结果对象
	 * @throws Exception
	 */
	public static PanelValue showInputwithOutButton(PanelContext ctx, String title, PanelDto panel,
			boolean onlyGuiValue, long timeout, boolean showClose, PopDecorationDto decoration,
			Boolean barrierDismissible, PopDialogType popType) throws Exception {
		DialogDto dlg = buildDialog(title, panel, null, null, showClose)
				.setBarrierDismissible(barrierDismissible).setPopDialogType(popType)
				.setOnlyGuiValue(onlyGuiValue);
		
		DialogDto.setPopDecoration(dlg, decoration);
		
		PanelValue panelValue = pop(ctx, dlg, timeout);
		return panelValue;
	}

	/**
	 * 显示带自定义确认按钮的输入对话框
	 * 
	 * @param ctx       面板上下文对象，用于处理回调
	 * @param title     对话框标题
	 * @param panel     自定义输入面板组件
	 * @param okButton  完全自定义的确认按钮配置对象（需包含widgetId）
	 * @param showClose 是否显示关闭按钮
	 * @return 用户点击自定义确认按钮时返回输入数据，否则返回null
	 * @throws Exception
	 */

	public static PanelValue showInputWithCustomedButton(PanelContext ctx, String title, PanelDto panel,
			ButtonDto okButton, boolean showClose) throws Exception {
		return showInputWithCustomedButton(ctx, title, panel, okButton, defaultOnlyGuiValue, PopWidgetDto.DEFAULT_TIME_OUT, showClose);
	}

	/**
	 * 显示带自定义按钮的基础输入对话框
	 * 
	 * @param ctx          面板上下文对象
	 * @param title        对话框标题
	 * @param panel        输入内容面板配置
	 * @param okButton     自定义确认按钮配置（需设置widgetId用于状态检测）
	 * @param onlyGuiValue 是否仅返回原始GUI组件值
	 * @param timeout      超时时间（毫秒）
	 * @param showClose    关闭按钮可见性
	 * @return 用户确认时返回输入数据，取消/关闭返回null
	 * @throws Exception
	 */
	public static PanelValue showInputWithCustomedButton(PanelContext ctx, String title, PanelDto panel,
			ButtonDto okButton, boolean onlyGuiValue, long timeout, boolean showClose) throws Exception {
		return showInputWithCustomedButton(ctx, title, panel, okButton, onlyGuiValue, timeout, showClose, null,
				defaultBarrierDismissible);
	}

	/**
	 * 显示带装饰配置的自定义按钮对话框
	 * 
	 * @param ctx                面板上下文对象
	 * @param title              对话框标题
	 * @param panel              输入面板配置
	 * @param okButton           自定义确认按钮（必须包含唯一widgetId）
	 * @param onlyGuiValue       数据处理模式（true=原始GUI值）
	 * @param timeout            超时时间
	 * @param showClose          关闭按钮可见性
	 * @param decoration         对话框装饰配置（边距/阴影等）
	 * @param barrierDismissible 是否允许点击外部区域关闭
	 * @return 当且仅当自定义确认按钮被点击时返回有效数据
	 * @throws Exception
	 */
	public static PanelValue showInputWithCustomedButton(PanelContext ctx, String title, PanelDto panel,
			ButtonDto okButton, boolean onlyGuiValue, long timeout, boolean showClose, PopDecorationDto decoration,
			Boolean barrierDismissible) throws Exception {
		DialogDto dlg = buildDialog(title, panel, okButton, null, showClose)
				.setBarrierDismissible(barrierDismissible).setOnlyGuiValue(onlyGuiValue);
		
		DialogDto.setPopDecoration(dlg, decoration);
		
		PanelValue panelValue = pop(ctx, dlg, timeout);
		if (panelValue == null)
			return null;

		int clickOK = ToolUtilities.getInteger(panelValue.getValue(okButton.getWidgetId()), -1);
		if (clickOK > 0)
			return panelValue;
		else
			return null;
	}

	/**
	 * 显示带标题图标的自定义按钮对话框
	 * 
	 * @param ctx                面板上下文对象
	 * @param title              对话框标题
	 * @param panel              输入面板配置
	 * @param okButton           自定义确认按钮配置
	 * @param onlyGuiValue       数据处理模式
	 * @param timeout            超时时间
	 * @param showClose          关闭按钮可见性
	 * @param decoration         装饰配置
	 * @param barrierDismissible 外部点击关闭控制
	 * @param titleIcon          标题栏图标配置
	 * @return 用户确认操作时返回输入数据
	 * @throws Exception
	 */
	public static PanelValue showInputWithCustomedButton(PanelContext ctx, String title, PanelDto panel,
			ButtonDto okButton, boolean onlyGuiValue, long timeout, boolean showClose, PopDecorationDto decoration,
			Boolean barrierDismissible, IconDto titleIcon) throws Exception {
		DialogDto dlg = buildDialog(title, panel, okButton, null, showClose)
				.setBarrierDismissible(barrierDismissible).setTitleIcon(titleIcon).setOnlyGuiValue(onlyGuiValue);
		
		DialogDto.setPopDecoration(dlg, decoration);
		
		PanelValue panelValue = pop(ctx, dlg, timeout);
		if (panelValue == null)
			return null;

		int clickOK = ToolUtilities.getInteger(panelValue.getValue(okButton.getWidgetId()), -1);
		if (clickOK > 0)
			return panelValue;
		else
			return null;
	}

	/**
	 * 显示指定主题样式的自定义按钮对话框
	 * 
	 * @param ctx                面板上下文对象
	 * @param title              对话框标题
	 * @param panel              输入面板配置
	 * @param okButton           自定义确认按钮（需预置widgetId）
	 * @param onlyGuiValue       数据处理模式
	 * @param timeout            超时时间
	 * @param showClose          关闭按钮可见性
	 * @param decoration         装饰配置
	 * @param barrierDismissible 外部点击关闭控制
	 * @param popType            对话框主题类型
	 * @return 用户确认操作时返回输入数据
	 * @throws Exception
	 */
	public static PanelValue showInputWithCustomedButton(PanelContext ctx, String title, PanelDto panel,
			ButtonDto okButton, boolean onlyGuiValue, long timeout, boolean showClose, PopDecorationDto decoration,
			Boolean barrierDismissible, PopDialogType popType) throws Exception {
		DialogDto dlg = buildDialog(title, panel, okButton, null, showClose)
				.setBarrierDismissible(barrierDismissible).setPopDialogType(popType)
				.setOnlyGuiValue(onlyGuiValue);
		
		DialogDto.setPopDecoration(dlg, decoration);
		
		PanelValue panelValue = pop(ctx, dlg, timeout);
		if (panelValue == null)
			return null;

		int clickOK = ToolUtilities.getInteger(panelValue.getValue(okButton.getWidgetId()), -1);
		if (clickOK > 0)
			return panelValue;
		else
			return null;
	}

	/**
	 * 显示基础确认对话框（默认样式）
	 * 
	 * @param ctx   面板上下文对象，用于处理回调
	 * @param title 对话框标题
	 * @param msg   显示的消息内容
	 * @return 用户点击确认返回true，其他情况返回false
	 * @throws Exception
	 */
	public static boolean showConfirm(PanelContext ctx, String title, String msg) throws Exception {
		return showConfirm(ctx, title, msg, SizeDto.all(300, 100), defaultShowClose, null, defaultBarrierDismissible);
	}

	/**
	 * 显示可控制关闭按钮的确认对话框
	 * 
	 * @param ctx       面板上下文对象
	 * @param title     对话框标题
	 * @param msg       消息内容
	 * @param showClose 是否显示关闭按钮
	 * @return 用户是否确认操作
	 * @throws Exception
	 */
	public static boolean showConfirm(PanelContext ctx, String title, String msg, boolean showClose) throws Exception {
		return showConfirm(ctx, title, msg, SizeDto.all(300, 100), showClose, null, defaultBarrierDismissible);
	}

	/**
	 * 显示全功能确认对话框（自定义尺寸+样式）
	 * 
	 * @param ctx                面板上下文对象
	 * @param title              对话框标题
	 * @param msg                消息文本内容
	 * @param windowSize         对话框预设尺寸（宽度/高度）
	 * @param showClose          是否显示关闭按钮
	 * @param decoration         对话框装饰配置（边距/背景等）
	 * @param barrierDismissible 点击对话框外部是否关闭
	 * @return 用户确认操作返回true，取消/关闭返回false
	 * @throws Exception
	 */
	public static boolean showConfirm(PanelContext ctx, String title, String msg, SizeDto windowSize, boolean showClose,
			DialogDecorationDto decoration, Boolean barrierDismissible) throws Exception {
		EscapeButtonDto ok = (EscapeButtonDto) new EscapeButtonDto().setText("确认").setWidgetId("_BUTTON_YES")
				.setConfirmStyle();
		EscapeButtonDto cancel = (EscapeButtonDto) new EscapeButtonDto().setText("取消").setCancelStyle();

		SinglePanelDto panel = new SinglePanelDto(new LabelDto(msg));
		panel.setPreferSize(windowSize);

		DialogDto dlg = buildDialog(title, panel, ok, cancel, showClose).setDecoration(decoration)
				.setBarrierDismissible(barrierDismissible);
		PanelValue panelValue = pop(ctx, dlg, PopWidgetDto.DEFAULT_TIME_OUT);
		if (panelValue == null)
			return false;

		int clickOK = ToolUtilities.getInteger(panelValue.getValue("_BUTTON_YES"), -1);
		if (clickOK > 0)
			return true;

		return false;
	}

	/**
	 * 显示带标题图标的确认对话框
	 * 
	 * @param ctx                面板上下文对象
	 * @param title              对话框标题
	 * @param msg                消息内容
	 * @param windowSize         预设窗口尺寸
	 * @param showClose          关闭按钮可见性
	 * @param decoration         装饰配置
	 * @param barrierDismissible 外部点击关闭控制
	 * @param titleIcon          标题栏图标配置
	 * @return 用户是否确认操作
	 * @throws Exception
	 */
	public static boolean showConfirm(PanelContext ctx, String title, String msg, SizeDto windowSize, boolean showClose,
			DialogDecorationDto decoration, Boolean barrierDismissible, IconDto titleIcon) throws Exception {
		EscapeButtonDto ok = (EscapeButtonDto) new EscapeButtonDto().setText("确认").setWidgetId("_BUTTON_YES")
				.setConfirmStyle();
		EscapeButtonDto cancel = (EscapeButtonDto) new EscapeButtonDto().setText("取消").setCancelStyle();

		SinglePanelDto panel = new SinglePanelDto(new LabelDto(msg));
		panel.setPreferSize(windowSize);

		DialogDto dlg = buildDialog(title, panel, ok, cancel, showClose).setDecoration(decoration)
				.setBarrierDismissible(barrierDismissible).setTitleIcon(titleIcon);
		PanelValue panelValue = pop(ctx, dlg, PopWidgetDto.DEFAULT_TIME_OUT);
		if (panelValue == null)
			return false;

		int clickOK = ToolUtilities.getInteger(panelValue.getValue("_BUTTON_YES"), -1);
		if (clickOK > 0)
			return true;

		return false;
	}

	/**
	 * 显示指定主题样式的确认对话框
	 * 
	 * @param ctx                面板上下文对象
	 * @param title              对话框标题
	 * @param msg                消息内容
	 * @param windowSize         预设窗口尺寸
	 * @param showClose          关闭按钮可见性
	 * @param decoration         装饰配置
	 * @param barrierDismissible 外部点击关闭控制
	 * @param popType            对话框主题类型（如：警告/成功样式）
	 * @return 用户是否确认操作
	 * @throws Exception
	 */
	public static boolean showConfirm(PanelContext ctx, String title, String msg, SizeDto windowSize, boolean showClose,
			DialogDecorationDto decoration, Boolean barrierDismissible, PopDialogType popType) throws Exception {
		EscapeButtonDto ok = (EscapeButtonDto) new EscapeButtonDto().setText("确认").setWidgetId("_BUTTON_YES")
				.setConfirmStyle();
		EscapeButtonDto cancel = (EscapeButtonDto) new EscapeButtonDto().setText("取消").setCancelStyle();

		SinglePanelDto panel = new SinglePanelDto(new LabelDto(msg));
		panel.setPreferSize(windowSize);

		DialogDto dlg = buildDialog(title, panel, ok, cancel, showClose).setDecoration(decoration)
				.setBarrierDismissible(barrierDismissible).setPopDialogType(popType);
		PanelValue panelValue = pop(ctx, dlg, PopWidgetDto.DEFAULT_TIME_OUT);
		if (panelValue == null)
			return false;

		int clickOK = ToolUtilities.getInteger(panelValue.getValue("_BUTTON_YES"), -1);
		if (clickOK > 0)
			return true;

		return false;
	}

	// ----------------------------------------------------------------------------------------------------------------

	/**
	 * 显示一个弹窗，并允许自定义所有参数，包括路由设置、关闭按钮、超时时间等。
	 *
	 * @param context            面板上下文
	 * @param panel              面板组件
	 * @param routeSettings      路由设置
	 * @param title              弹窗标题
	 * @param showClose          是否显示关闭按钮
	 * @param waitForClose       等待关闭的超时时间
	 * @param onlyGuiValue       是否仅返回 GUI 修改的数据
	 * @param barrierDismissible 是否允许点击外部关闭
	 * @param decoration         弹窗样式
	 * @param titleIcon          标题图标
	 * @param popDialogType      弹出框类型
	 * @return PanelValue 返回的面板数据
	 * @throws Exception 抛出异常
	 */
	public static PanelValue pop(PanelContext context, PanelDto panel, PopupRouteSettingsDto routeSettings,
			String title, Boolean showClose, Long waitForClose, Boolean onlyGuiValue, Boolean barrierDismissible,
			PopDecorationDto decoration, IconDto titleIcon, PopDialogType popDialogType) throws Exception {

		DialogDto dlg = new DialogDto().setPanel(panel).setRouteSettings(routeSettings).setTitle(title)
				.setShowClose(showClose).setOnlyGuiValue(onlyGuiValue).setBarrierDismissible(barrierDismissible)
				.setTitleIcon(titleIcon)
				.setPopDialogType(popDialogType);

		DialogDto.setPopDecoration(dlg, decoration);
		
		return pop(context, dlg, waitForClose);

	}
}
