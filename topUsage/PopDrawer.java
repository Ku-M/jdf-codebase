package fe.cmn.panel.ability;

import com.leavay.common.util.ToolUtilities;
import com.leavay.ms.tool.CmnUtil;

import fe.cmn.app.PopupRouteSettingsDto;
import fe.cmn.callbackWidget.popWidget.DrawerDto;
import fe.cmn.callbackWidget.popWidget.PopWidgetDto;
import fe.cmn.data.BasicAbility;
import fe.cmn.editor.TextEditorDto;
import fe.cmn.panel.BoxDto;
import fe.cmn.panel.DrawerDirection;
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
import fe.cmn.widget.decoration.DrawerDecorationDto;
import fe.cmn.widget.decoration.PopDecorationDto;

/**
 * 
 * 回调前端弹出抽屉
 *
 */
public class PopDrawer extends BasicAbility<Void> {

    private static final long serialVersionUID = -309028411713264149L;

    public static final Boolean defaultShowClose = true;

	public static final Boolean defaultBarrierDismissible = true;

	public static final Boolean defaultOnlyGuiValue = false;

	public transient static final DrawerDirection defaultDirection = DrawerDirection.rtl;

	DrawerDto drawer;

	public PopDrawer() {
		super.setTimeout(PopWidgetDto.DEFAULT_TIME_OUT);
	}

	public DrawerDto getDrawer() {
		return drawer;
	}

	public PopDrawer setDrawer(DrawerDto drawer) {
		this.drawer = drawer;
		return this;
	}
	
	@Override
	public PopDrawer setTimeout(long timeout) {
		super.setTimeout(timeout);
		if (this.drawer == null) {
			this.drawer = new DrawerDto();
		}
		this.drawer.setWaitForClose(timeout);
		return this;
	}

	public static PanelValue pop(PanelContext ctx, DrawerDto drawerDto) throws Exception {
		PopDrawer pop = new PopDrawer().setDrawer(drawerDto);
		return (PanelValue) ctx.callback(pop);
	}

	public static PanelValue pop(PanelContext ctx, DrawerDto drawerDto, Long timeout) throws Exception {
		PopDrawer pop = new PopDrawer().setDrawer(drawerDto);
		
		if(timeout != null) {
			pop.setTimeout(timeout);
		}
		
		return (PanelValue) ctx.callback(pop);
	}

	// =========================================================================================================

	// DrawerDto属性设置------------------------------------------------

    public PopDrawer setWaitForClose(long timeout) {
        super.setTimeout(timeout);
        if (this.drawer == null) {
            this.drawer = new DrawerDto();
        }
        this.drawer.setWaitForClose(timeout);
        return this;
    }

    public DrawerDirection getDirection() {
        if (this.drawer != null) {
            return this.drawer.getDirection();
        }
        return null;
    }

    public PopDrawer setDirection(DrawerDirection direction) {
        if (this.drawer == null) {
            this.drawer = new DrawerDto();
        }
        this.drawer.setDirection(direction);
        return this;
    }

    public PanelDto getPanel() {
        if (this.drawer != null) {
            return this.drawer.getPanel();
        }
        return null;
    }

    public PopDrawer setPanel(PanelDto panel) {
        if (this.drawer == null) {
            this.drawer = new DrawerDto();
        }
        this.drawer.setPanel(panel);
        return this;
    }

    public String getTitle() {
        if (this.drawer != null) {
            return this.drawer.getTitle();
        }
        return null;
    }

    public PopDrawer setTitle(String title) {
        if (this.drawer == null) {
            this.drawer = new DrawerDto();
        }
        this.drawer.setTitle(title);
        return this;
    }

    public Boolean getShowClose() {
        if (this.drawer != null) {
            return this.drawer.isShowClose();
        }
        return null;
    }

    public PopDrawer setShowClose(Boolean showClose) {
        if (this.drawer == null) {
            this.drawer = new DrawerDto();
        }
        this.drawer.setShowClose(showClose);
        return this;
    }

    public Boolean getOnlyGuiValue() {
        if (this.drawer != null) {
            return this.drawer.isOnlyGuiValue();
        }
        return null;
    }

    public PopDrawer setOnlyGuiValue(Boolean onlyGuiValue) {
        if (this.drawer == null) {
            this.drawer = new DrawerDto();
        }
        this.drawer.setOnlyGuiValue(onlyGuiValue);
        return this;
    }

    public PopDecorationDto getDecoration() {
        if (this.drawer != null) {
            return this.drawer.getDecoration();
        }
        return null;
    }

    public PopDrawer setDecoration(PopDecorationDto decoration) throws Exception {
        if (this.drawer == null) {
            this.drawer = new DrawerDto();
        }
        
        DrawerDto.setPopDecoration(drawer, decoration);
        
        return this;
    }

    public Boolean getBarrierDismissible() {
        if (this.drawer != null) {
            return this.drawer.getBarrierDismissible();
        }
        return null;
    }

    public PopDrawer setBarrierDismissible(Boolean barrierDismissible) {
        if (this.drawer == null) {
            this.drawer = new DrawerDto();
        }
        this.drawer.setBarrierDismissible(barrierDismissible);
        return this;
    }

    public IconDto getTitleIcon() {
        if (this.drawer != null) {
            return this.drawer.getTitleIcon();
        }
        return null;
    }

    public PopDrawer setTitleIcon(IconDto titleIcon) {
        if (this.drawer == null) {
            this.drawer = new DrawerDto();
        }
        this.drawer.setTitleIcon(titleIcon);
        return this;
    }

    public PopDialogType getPopDialogType() {
        if (this.drawer != null) {
            return this.drawer.getPopDialogType();
        }
        return null;
    }

    public PopDrawer setPopDialogType(PopDialogType popDialogType) {
        if (this.drawer == null) {
            this.drawer = new DrawerDto();
        }
        this.drawer.setPopDialogType(popDialogType);
        return this;
    }

    public PopupRouteSettingsDto getRouteSettings() {
        if (this.drawer != null) {
            return this.drawer.getRouteSettings();
        }
        return null;
    }

    public PopDrawer setRouteSettings(PopupRouteSettingsDto routeSettings) {
        if (this.drawer == null) {
            this.drawer = new DrawerDto();
        }
        this.drawer.setRouteSettings(routeSettings);
        return this;
    }

	// buildDrawer 构建DrawerDto------------------------------
	/**
	 * 构建基础抽屉配置对象
	 * 
	 * @param title  抽屉标题文本
	 * @param panel  主要内容面板组件
	 * @param ok     确定按钮配置对象（可空，为空时不显示确认按钮）
	 * @param cancel 取消按钮配置对象（可空，为空时不显示取消按钮）
	 * @return 包含垂直布局面板和水平按钮栏的抽屉配置对象
	 *         按钮区域自动右对齐，确认/取消按钮间距为20，顶部外边距36
	 */
	public static DrawerDto buildDrawer(String title, PanelDto panel, ButtonDto ok, ButtonDto cancel) {
		DrawerDto dlg = new DrawerDto();
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

	/**
	 * 构建带关闭按钮控制的抽屉配置对象
	 * 
	 * @param title     抽屉标题文本
	 * @param panel     主要内容面板组件
	 * @param ok        确定按钮配置对象（可空，为空时不显示确认按钮）
	 * @param cancel    取消按钮配置对象（可空，为空时不显示取消按钮）
	 * @param showClose 是否显示右上角关闭按钮
	 *                  继承基础版本所有参数并追加此配置项
	 * @return 在基础配置上追加关闭按钮显示状态的抽屉配置对象
	 *         其他布局规则与基础版本一致
	 */
	public static DrawerDto buildDrawer(String title, PanelDto panel, ButtonDto ok, ButtonDto cancel,
			boolean showClose) {
		return (DrawerDto) buildDrawer(title, panel, ok, cancel).setShowClose(showClose);
	}

	// buildDrawer构建DrawerDto---------------------------------------
	/**
	 * 构建基础抽屉实例
	 * 
	 * @param title  抽屉标题文本
	 * @param panel  主要内容面板组件
	 * @param ok     确定按钮配置（可空，为空时不渲染该按钮）
	 * @param cancel 取消按钮配置（可空，为空时不渲染该按钮）
	 * @return 预配置的PopNewDrawer实例，包含：
	 *         - 垂直布局的内容面板
	 *         - 右对齐的按钮区域（确认/取消按钮间距20）
	 *         - 默认显示关闭按钮（参见defaultShowClose常量）
	 */
	public static PopDrawer build(String title, PanelDto panel, ButtonDto ok, ButtonDto cancel) {
		return new PopDrawer().setDrawer(buildDrawer(title, panel, ok, cancel));
	}

	/**
	 * 构建带关闭按钮控制的抽屉实例
	 * 
	 * @param title     抽屉标题文本
	 * @param panel     主要内容面板组件
	 * @param ok        确定按钮配置（可空，为空时不渲染该按钮）
	 * @param cancel    取消按钮配置（可空，为空时不渲染该按钮）
	 * @param showClose 显式控制右上角关闭按钮可见性
	 *                  true: 显示关闭按钮
	 *                  false: 隐藏关闭按钮
	 * @return 在基础配置上追加关闭按钮显示控制的PopNewDrawer实例
	 *         其他布局规则与基础版本一致
	 */
	public static PopDrawer build(String title, PanelDto panel, ButtonDto ok, ButtonDto cancel, boolean showClose) {
		return new PopDrawer().setDrawer((DrawerDto) buildDrawer(title, panel, ok, cancel).setShowClose(showClose));
	}

	// post 弹出带面板的抽屉-----------------------
	public static void post(PanelContext ctx, String title, PanelDto panel, DrawerDirection direction)
			throws Exception {
		DrawerDto drawer = buildDrawer(title, panel, null, null).setDirection(direction);
		pop(ctx, drawer);
	}

	public static void post(PanelContext ctx, String title, PanelDto panel, DrawerDirection direction,
	DrawerDecorationDto decoration) throws Exception {
		DrawerDto drawer = (DrawerDto) buildDrawer(title, panel, null, null).setDirection(direction)
				.setDecoration(decoration);
		pop(ctx, drawer);
	}

	// show 弹出带面板的抽屉------------------------
	/**
	 * 显示基础抽屉组件（默认方向、显示关闭按钮）
	 * 
	 * @param ctx   面板上下文对象，用于与前端通信
	 * @param title 抽屉标题文本内容
	 * @param panel 主要内容面板组件配置
	 */
	public static void show(PanelContext ctx, String title, PanelDto panel) throws Exception {
		show(ctx, title, panel, defaultDirection);
	}

	/**
	 * 显示可指定弹出方向的抽屉
	 * 
	 * @param ctx       面板上下文对象
	 * @param title     标题文本
	 * @param panel     内容面板
	 * @param direction 抽屉弹出方向枚举值（如DrawerDirection.rtl表示从右向左滑出）
	 */
	public static void show(PanelContext ctx, String title, PanelDto panel, DrawerDirection direction)
			throws Exception {
		show(ctx, title, panel, null, direction);
	}

	/**
	 * 显示可控制关闭按钮可见性的抽屉
	 * 
	 * @param ctx       上下文对象
	 * @param title     标题
	 * @param panel     内容面板
	 * @param showClose 是否显示右上角关闭按钮（true显示/false隐藏）
	 */
	public static void show(PanelContext ctx, String title, PanelDto panel, boolean showClose) throws Exception {
		show(ctx, title, panel, null, null, showClose, defaultDirection);
	}

	/**
	 * 显示带单个确认按钮的抽屉（无取消按钮）
	 * 
	 * @param ctx   上下文
	 * @param title 标题
	 * @param panel 内容面板
	 * @param ok    确认按钮的完整配置对象（需包含文本、样式、点击行为等）
	 */
	public static void show(PanelContext ctx, String title, PanelDto panel, ButtonDto ok) throws Exception {
		show(ctx, title, panel, ok, null, defaultShowClose, defaultDirection);
	}

	/**
	 * 显示带确认按钮和自定义方向的抽屉
	 * 
	 * @param ctx       上下文
	 * @param title     标题
	 * @param panel     内容面板
	 * @param ok        确认按钮配置
	 * @param direction 抽屉弹出方向枚举值
	 */
	public static void show(PanelContext ctx, String title, PanelDto panel, ButtonDto ok, DrawerDirection direction)
			throws Exception {
		show(ctx, title, panel, ok, null, defaultShowClose, direction);
	}

	/**
	 * 显示带确认按钮及关闭控制的抽屉
	 * 
	 * @param ctx       上下文
	 * @param title     标题
	 * @param panel     内容面板
	 * @param ok        确认按钮配置
	 * @param showClose 是否显示关闭按钮
	 */
	public static void show(PanelContext ctx, String title, PanelDto panel, ButtonDto ok, boolean showClose)
			throws Exception {
		show(ctx, title, panel, ok, null, showClose, defaultDirection);
	}

	/**
	 * 显示带确认按钮、关闭控制和标题图标的抽屉
	 * 
	 * @param ctx       上下文
	 * @param title     标题
	 * @param panel     内容面板
	 * @param ok        确认按钮配置
	 * @param showClose 关闭按钮可见性
	 * @param titleIcon 标题栏图标配置对象（包含图标资源路径、尺寸等）
	 */
	public static void show(PanelContext ctx, String title, PanelDto panel, ButtonDto ok, boolean showClose,
			IconDto titleIcon) throws Exception {
		show(ctx, title, panel, ok, null, showClose, defaultDirection, null, defaultBarrierDismissible, titleIcon);
	}

	/**
	 * 显示带确认按钮和主题样式的抽屉
	 * 
	 * @param ctx           上下文
	 * @param title         标题
	 * @param panel         内容面板
	 * @param ok            确认按钮配置
	 * @param showClose     关闭按钮可见性
	 * @param popDialogType 弹窗主题类型（如PopDialogType.WARNING表示警告样式）
	 */
	public static void show(PanelContext ctx, String title, PanelDto panel, ButtonDto ok, boolean showClose,
			PopDialogType popDialogType) throws Exception {
		show(ctx, title, panel, ok, null, showClose, defaultDirection, null, defaultBarrierDismissible, popDialogType);
	}

	/**
	 * 显示带确认按钮和装饰配置的抽屉
	 * 
	 * @param ctx        上下文
	 * @param title      标题
	 * @param panel      内容面板
	 * @param ok         确认按钮配置
	 * @param showClose  关闭按钮可见性
	 * @param decoration 视觉装饰配置对象（设置背景色、边框样式等）
	 */
	public static void show(PanelContext ctx, String title, PanelDto panel, ButtonDto ok, boolean showClose,
	DrawerDecorationDto decoration) throws Exception {
		show(ctx, title, panel, ok, null, showClose, defaultDirection, decoration, defaultBarrierDismissible);
	}

	/**
	 * 显示带确认/取消按钮和指定方向的抽屉
	 * 
	 * @param ctx       上下文
	 * @param title     标题
	 * @param panel     内容面板
	 * @param ok        确认按钮配置
	 * @param cancel    取消按钮配置
	 * @param showClose 关闭按钮可见性
	 * @param direction 弹出方向枚举值
	 */
	public static void show(PanelContext ctx, String title, PanelDto panel, ButtonDto ok, ButtonDto cancel,
			boolean showClose, DrawerDirection direction) throws Exception {
		DrawerDto drawer = (DrawerDto) buildDrawer(title, panel, ok, cancel, showClose).setDirection(direction);
		pop(ctx, drawer);
	}

	/**
	 * 显示全功能配置抽屉（包含装饰和遮罩行为）
	 * 
	 * @param ctx                上下文
	 * @param title              标题
	 * @param panel              内容面板
	 * @param ok                 确认按钮配置
	 * @param cancel             取消按钮配置
	 * @param showClose          关闭按钮可见性
	 * @param direction          弹出方向
	 * @param decoration         视觉装饰配置
	 * @param barrierDismissible 是否允许点击遮罩层关闭抽屉（true允许/false禁止）
	 */
	public static void show(PanelContext ctx, String title, PanelDto panel, ButtonDto ok, ButtonDto cancel,
			boolean showClose, DrawerDirection direction, DrawerDecorationDto decoration, Boolean barrierDismissible)
			throws Exception {
		DrawerDto drawer = (DrawerDto) buildDrawer(title, panel, ok, cancel, showClose).setDirection(direction)
				.setDecoration(decoration).setBarrierDismissible(barrierDismissible);
		pop(ctx, drawer);
	}

	/**
	 * 显示带标题图标的完整配置抽屉
	 * 
	 * @param ctx                上下文
	 * @param title              标题
	 * @param panel              内容面板
	 * @param ok                 确认按钮
	 * @param cancel             取消按钮
	 * @param showClose          关闭按钮可见性
	 * @param direction          弹出方向
	 * @param decoration         装饰配置
	 * @param barrierDismissible 遮罩点击关闭控制
	 * @param titleIcon          标题栏图标配置
	 */
	public static void show(PanelContext ctx, String title, PanelDto panel, ButtonDto ok, ButtonDto cancel,
			boolean showClose, DrawerDirection direction, DrawerDecorationDto decoration, Boolean barrierDismissible,
			IconDto titleIcon) throws Exception {
		DrawerDto drawer = (DrawerDto) buildDrawer(title, panel, ok, cancel, showClose).setDirection(direction)
				.setDecoration(decoration).setBarrierDismissible(barrierDismissible).setTitleIcon(titleIcon);
		pop(ctx, drawer);
	}

	/**
	 * 显示带主题类型的完整配置抽屉
	 * 
	 * @param ctx                上下文
	 * @param title              标题
	 * @param panel              内容面板
	 * @param ok                 确认按钮
	 * @param cancel             取消按钮
	 * @param showClose          关闭按钮可见性
	 * @param direction          弹出方向
	 * @param decoration         装饰配置
	 * @param barrierDismissible 遮罩点击关闭控制
	 * @param popType            主题类型枚举值（如PopDialogType.INFO表示信息样式）
	 */
	public static void show(PanelContext ctx, String title, PanelDto panel, ButtonDto ok, ButtonDto cancel,
			boolean showClose, DrawerDirection direction, DrawerDecorationDto decoration, Boolean barrierDismissible,
			PopDialogType popType) throws Exception {
		DrawerDto drawer = (DrawerDto) buildDrawer(title, panel, ok, cancel, showClose).setDirection(direction)
				.setDecoration(decoration).setBarrierDismissible(barrierDismissible)
				.setPopDialogType(popType);
		pop(ctx, drawer);
	}

	// showInput 弹出带输入框的抽屉---------------------------
	public static String showInput(PanelContext ctx, String title, String initText) throws Exception {
		PanelValue drawer = PopDrawer.showInput(ctx, title,
				SinglePanelDto.wrap(new TextEditorDto(initText).setWidgetId("input_text_editor")));
		if (drawer == null)
			return null;

		return CmnUtil.getString(drawer.getValue("input_text_editor"), null);
	}

	// 弹出阻塞式对话框，等待对话框关闭并回传对话框中输入的数据，如果cancel则返回NULL
	public static PanelValue showInput(PanelContext ctx, String title, PanelDto panel) throws Exception {
		return showInput(ctx, title, panel, defaultOnlyGuiValue, PopWidgetDto.DEFAULT_TIME_OUT, defaultShowClose, defaultDirection, null);
	}

	// 弹出阻塞式抽屉，等待抽屉关闭并回传抽屉中输入的数据，如果cancel则返回NULL
	public static PanelValue showInput(PanelContext ctx, String title, PanelDto panel, String okButtonText)
			throws Exception {
		return showInput(ctx, title, panel, defaultOnlyGuiValue, PopWidgetDto.DEFAULT_TIME_OUT, defaultShowClose, defaultDirection, okButtonText);
	}

	// 弹出阻塞式抽屉，等待抽屉关闭并回传抽屉中输入的数据，如果cancel则返回NULL
	public static PanelValue showInput(PanelContext ctx, String title, PanelDto panel, boolean showClose,
			String okButtonText) throws Exception {
		return showInput(ctx, title, panel, defaultOnlyGuiValue, PopWidgetDto.DEFAULT_TIME_OUT, showClose, defaultDirection, okButtonText);
	}

	public static PanelValue showInput(PanelContext ctx, String title, PanelDto panel, boolean onlyGuiValue,
			long timeout, String okButtonText) throws Exception {
		return showInput(ctx, title, panel, onlyGuiValue, timeout, defaultShowClose, defaultDirection, okButtonText);
	}

	public static PanelValue showInput(PanelContext ctx, String title, PanelDto panel, DrawerDirection direction,
			String okButtonText) throws Exception {
		return showInput(ctx, title, panel, defaultOnlyGuiValue, PopWidgetDto.DEFAULT_TIME_OUT, defaultShowClose, direction, okButtonText);
	}

	// 弹出阻塞式抽屉，等待抽屉关闭并回传抽屉中输入的数据，如果cancel则返回NULL
	public static PanelValue showInput(PanelContext ctx, String title, PanelDto panel, boolean onlyGuiValue,
			long timeout, boolean showClose, DrawerDirection direction, String okButtonText) throws Exception {
		return showInput(ctx, title, panel, onlyGuiValue, timeout, showClose, direction, okButtonText, null);
	}

	public static PanelValue showInput(PanelContext ctx, String title, PanelDto panel, boolean onlyGuiValue,
			long timeout, boolean showClose, DrawerDirection direction, String okButtonText,
			DrawerDecorationDto decoration) throws Exception {
				return showInput(ctx, title, panel, onlyGuiValue, timeout, showClose, okButtonText, null, direction, decoration, defaultBarrierDismissible, null, null);
	}

	public static PanelValue showInput(PanelContext ctx, String title, PanelDto panel, boolean onlyGuiValue,
			long timeout, boolean showClose, DrawerDirection direction, String okButtonText,
			DrawerDecorationDto decoration, IconDto titleIcon) throws Exception {
			return showInput(ctx, title, panel, onlyGuiValue, timeout, showClose, okButtonText, null, direction, decoration, defaultBarrierDismissible, titleIcon, null);
	}

	public static PanelValue showInput(PanelContext ctx, String title, PanelDto panel, boolean onlyGuiValue,
			long timeout, boolean showClose, DrawerDirection direction, String okButtonText,
			DrawerDecorationDto decoration, PopDialogType popType) throws Exception {
			return showInput(ctx, title, panel, onlyGuiValue, timeout, showClose, okButtonText, null, direction, decoration, defaultBarrierDismissible, null, popType);
	}

	public static PanelValue showInput(PanelContext ctx, String title, PanelDto panel, boolean onlyGuiValue,
			long timeout, boolean showClose, String confirmBtnText, String cancelBtnText) throws Exception {
		return showInput(ctx, title, panel, onlyGuiValue, timeout, showClose, confirmBtnText, cancelBtnText,
				defaultDirection);
	}

	public static PanelValue showInput(PanelContext ctx, String title, PanelDto panel, boolean onlyGuiValue,
			long timeout, boolean showClose, String confirmBtnText, String cancelBtnText, DrawerDirection direction)
			throws Exception {
		return showInput(ctx, title, panel, onlyGuiValue, timeout, showClose, confirmBtnText, cancelBtnText,
		direction, null, defaultBarrierDismissible);
	}

	public static PanelValue showInput(PanelContext ctx, String title, PanelDto panel, boolean onlyGuiValue,
			long timeout, boolean showClose, String confirmBtnText, String cancelBtnText, DrawerDirection direction,
			DrawerDecorationDto decoration, Boolean barrierDismissible) throws Exception {
		return showInput(ctx, title, panel, onlyGuiValue, timeout, showClose, confirmBtnText, cancelBtnText, direction, decoration, barrierDismissible, null, null);
	}

	public static PanelValue showInput(PanelContext ctx, String title, PanelDto panel, boolean onlyGuiValue,
			long timeout, boolean showClose, String confirmBtnText, String cancelBtnText, DrawerDirection direction,
			DrawerDecorationDto decoration, Boolean barrierDismissible, IconDto titleIcon, PopDialogType popType) throws Exception {
		EscapeButtonDto confirmBtn = (EscapeButtonDto) new EscapeButtonDto().setText(confirmBtnText)
				.setWidgetId("_BUTTON_OK").setConfirmStyle();

		EscapeButtonDto cancelBtn = CmnUtil.isObjectEmpty(cancelBtnText) ? null : (EscapeButtonDto) new EscapeButtonDto().setText(cancelBtnText).setCancelStyle();

		DrawerDto drawer = (DrawerDto) buildDrawer(title, panel, confirmBtn, cancelBtn, showClose)
				.setDirection(direction).setDecoration(decoration).setOnlyGuiValue(onlyGuiValue)
				.setBarrierDismissible(barrierDismissible)
				.setTitleIcon(titleIcon)
				.setPopDialogType(popType);

		PanelValue panelValue = pop(ctx, drawer, timeout);
		if (panelValue == null)
			return null;

		int clickOK = ToolUtilities.getInteger(panelValue.getValue("_BUTTON_OK"), -1);
		if (clickOK > 0)
			return panelValue;
		else
			return null;
	}

	// showInputwithOutButton 弹出带输入框及按钮的抽屉-----------------------

	// 弹出阻塞式抽屉，等待对话框关闭并回传对话框中输入的数据，如果cancel则返回NULL
	public static PanelValue showInputwithOutButton(PanelContext ctx, String title, PanelDto panel) throws Exception {
		return showInputwithOutButton(ctx, title, panel, defaultOnlyGuiValue, PopWidgetDto.DEFAULT_TIME_OUT, defaultShowClose);
	}

	// 弹出阻塞式对话框，等待对话框关闭并回传对话框中输入的数据，如果cancel则返回NULL
	public static PanelValue showInputwithOutButton(PanelContext ctx, String title, PanelDto panel, boolean showClose)
			throws Exception {
		return showInputwithOutButton(ctx, title, panel, defaultOnlyGuiValue, PopWidgetDto.DEFAULT_TIME_OUT, showClose);
	}

	public static PanelValue showInputwithOutButton(PanelContext ctx, String title, PanelDto panel,
			boolean onlyGuiValue, long timeout) throws Exception {
		return showInputwithOutButton(ctx, title, panel, onlyGuiValue, timeout, defaultShowClose);
	}

	public static PanelValue showInputwithOutButton(PanelContext ctx, String title, PanelDto panel,
			boolean onlyGuiValue, long timeout, boolean showClose) throws Exception {
		return showInputwithOutButton(ctx, title, panel, onlyGuiValue, timeout, showClose, null,
				defaultBarrierDismissible);
	}

	public static PanelValue showInputwithOutButton(PanelContext ctx, String title, PanelDto panel,
			boolean onlyGuiValue, long timeout, boolean showClose, PopDecorationDto decoration,
			Boolean barrierDismissible) throws Exception {
			return showInputwithOutButton(ctx, title, panel, onlyGuiValue, timeout, showClose, decoration, barrierDismissible, null, null);
	}

	public static PanelValue showInputwithOutButton(PanelContext ctx, String title, PanelDto panel,
			boolean onlyGuiValue, long timeout, boolean showClose, PopDecorationDto decoration,
			Boolean barrierDismissible, IconDto titleIcon) throws Exception {
			return showInputwithOutButton(ctx, title, panel, onlyGuiValue, timeout, showClose, decoration, barrierDismissible, titleIcon, null);
	}

	public static PanelValue showInputwithOutButton(PanelContext ctx, String title, PanelDto panel,
			boolean onlyGuiValue, long timeout, boolean showClose, PopDecorationDto decoration,
			Boolean barrierDismissible, PopDialogType popType) throws Exception {
		return showInputwithOutButton(ctx, title, panel, onlyGuiValue, timeout, showClose, decoration, barrierDismissible, null, popType);
	}

	public static PanelValue showInputwithOutButton(PanelContext ctx, String title, PanelDto panel,
			boolean onlyGuiValue, long timeout, boolean showClose, PopDecorationDto decoration,
			Boolean barrierDismissible, IconDto titleIcon, PopDialogType popType) throws Exception {
		DrawerDto drawer = (DrawerDto) buildDrawer(title, panel, null, null, showClose)
				.setOnlyGuiValue(onlyGuiValue).setBarrierDismissible(barrierDismissible)
				.setTitleIcon(titleIcon)
				.setPopDialogType(popType);
		
		DrawerDto.setPopDecoration(drawer, decoration);
		
		PanelValue panelValue = pop(ctx, drawer, timeout);
		return panelValue;
	}

	public static PanelValue showInputWithCustomedButton(PanelContext ctx, String title, PanelDto panel,
			ButtonDto okButton, boolean showClose) throws Exception {
		return showInputWithCustomedButton(ctx, title, panel, okButton, defaultOnlyGuiValue, PopWidgetDto.DEFAULT_TIME_OUT, showClose);
	}

	// 弹出阻塞式对话框，等待对话框关闭并回传对话框中输入的数据，如果cancel则返回NULL（自定义确认按钮）
	public static PanelValue showInputWithCustomedButton(PanelContext ctx, String title, PanelDto panel,
			ButtonDto okButton, boolean onlyGuiValue, long timeout, boolean showClose) throws Exception {
		return showInputWithCustomedButton(ctx, title, panel, okButton, onlyGuiValue, timeout, showClose, null,
				defaultBarrierDismissible);
	}

	public static PanelValue showInputWithCustomedButton(PanelContext ctx, String title, PanelDto panel,
			ButtonDto okButton, boolean onlyGuiValue, long timeout, boolean showClose, PopDecorationDto decoration,
			Boolean barrierDismissible) throws Exception {
				return showInputWithCustomedButton(ctx, title, panel, okButton, onlyGuiValue, timeout, showClose, decoration, barrierDismissible, null, null);
	}

	public static PanelValue showInputWithCustomedButton(PanelContext ctx, String title, PanelDto panel,
			ButtonDto okButton, boolean onlyGuiValue, long timeout, boolean showClose, PopDecorationDto decoration,
			Boolean barrierDismissible, IconDto titleIcon) throws Exception {
		return showInputWithCustomedButton(ctx, title, panel, okButton, onlyGuiValue, timeout, showClose, decoration, barrierDismissible, titleIcon, null);
	}

	public static PanelValue showInputWithCustomedButton(PanelContext ctx, String title, PanelDto panel,
			ButtonDto okButton, boolean onlyGuiValue, long timeout, boolean showClose, PopDecorationDto decoration,
			Boolean barrierDismissible, PopDialogType popType) throws Exception {
		return showInputWithCustomedButton(ctx, title, panel, okButton, onlyGuiValue, timeout, showClose, decoration, barrierDismissible, null, popType);
	}

	public static PanelValue showInputWithCustomedButton(PanelContext ctx, String title, PanelDto panel,
			ButtonDto okButton, boolean onlyGuiValue, long timeout, boolean showClose, PopDecorationDto decoration,
			Boolean barrierDismissible, IconDto titleIcon, PopDialogType popType) throws Exception {
		DrawerDto drawer = (DrawerDto) buildDrawer(title, panel, okButton, null, showClose)
				.setOnlyGuiValue(onlyGuiValue).setBarrierDismissible(barrierDismissible)
				.setPopDialogType(popType)
				.setTitleIcon(titleIcon);
		
		DrawerDto.setPopDecoration(drawer, decoration);

		PanelValue panelValue = pop(ctx, drawer, timeout);
		if (panelValue == null)
			return null;

		int clickOK = ToolUtilities.getInteger(panelValue.getValue(okButton.getWidgetId()), -1);
		if (clickOK > 0)
			return panelValue;
		else
			return null;
	}

	// showInputwithOutButton 弹出带“确认”/“取消”按钮的抽屉-------------------
	public static boolean showConfirm(PanelContext ctx, String title, String msg) throws Exception {
		return showConfirm(ctx, title, msg, SizeDto.all(300, 100), defaultShowClose, defaultDirection, null,
				defaultBarrierDismissible);
	}

	public static boolean showConfirm(PanelContext ctx, String title, String msg, boolean showClose) throws Exception {
		return showConfirm(ctx, title, msg, SizeDto.all(300, 100), showClose, defaultDirection, null,
				defaultBarrierDismissible);
	}

	public static boolean showConfirm(PanelContext ctx, String title, String msg, DrawerDirection direction)
			throws Exception {
		return showConfirm(ctx, title, msg, SizeDto.all(300, 100), defaultShowClose, direction, null,
				defaultBarrierDismissible);
	}

	public static boolean showConfirm(PanelContext ctx, String title, String msg, SizeDto windowSize, boolean showClose,
			DrawerDirection direction, DrawerDecorationDto decoration, Boolean barrierDismissible) throws Exception {
		return showConfirm(ctx, title, msg, windowSize, showClose, direction, decoration, barrierDismissible, null, null);
	}

	public static boolean showConfirm(PanelContext ctx, String title, String msg, SizeDto windowSize, boolean showClose,
			DrawerDirection direction, DrawerDecorationDto decoration, Boolean barrierDismissible, IconDto titleIcon)
			throws Exception {
		return showConfirm(ctx, title, msg, windowSize, showClose, direction, decoration, barrierDismissible, titleIcon, null);
	}

	public static boolean showConfirm(PanelContext ctx, String title, String msg, SizeDto windowSize, boolean showClose,
			DrawerDirection direction, DrawerDecorationDto decoration, Boolean barrierDismissible,
			PopDialogType popType) throws Exception {
		return showConfirm(ctx, title, msg, windowSize, showClose, direction, decoration, barrierDismissible, null, popType);
	}

	public static boolean showConfirm(PanelContext ctx, String title, String msg, SizeDto windowSize, boolean showClose,
			DrawerDirection direction, DrawerDecorationDto decoration, Boolean barrierDismissible, IconDto titleIcon,
			PopDialogType popType) throws Exception {
		EscapeButtonDto ok = (EscapeButtonDto) new EscapeButtonDto().setText("确认").setWidgetId("_BUTTON_YES")
				.setConfirmStyle();
				
		EscapeButtonDto cancel = (EscapeButtonDto) new EscapeButtonDto().setText("取消").setCancelStyle();

		SinglePanelDto panel = new SinglePanelDto(new LabelDto(msg));
		panel.setPreferSize(windowSize);

		DrawerDto drawer = (DrawerDto) buildDrawer(title, panel, ok, cancel, showClose).setDirection(direction)
				.setDecoration(decoration).setBarrierDismissible(barrierDismissible)
				.setPopDialogType(popType)
				.setTitleIcon(titleIcon);

		PanelValue panelValue = pop(ctx, drawer, PopWidgetDto.DEFAULT_TIME_OUT);
		if (panelValue == null)
			return false;

		int clickOK = ToolUtilities.getInteger(panelValue.getValue("_BUTTON_YES"), -1);
		if (clickOK > 0)
			return true;

		return false;
	}

	public static PanelValue pop(PanelContext context, PanelDto panel, PopupRouteSettingsDto routeSettings,
			String title, Boolean showClose, Long waitForClose, Boolean onlyGuiValue, Boolean barrierDismissible,
			PopDecorationDto decoration, IconDto titleIcon, PopDialogType popDialogType, DrawerDirection direction)
			throws Exception {

		DrawerDto drawer = new DrawerDto().setPanel(panel).setRouteSettings(routeSettings).setTitle(title)
				.setShowClose(showClose).setOnlyGuiValue(onlyGuiValue).setBarrierDismissible(barrierDismissible)
				.setTitleIcon(titleIcon)
				.setPopDialogType(popDialogType)
				.setDirection(direction);
		
		DrawerDto.setPopDecoration(drawer, decoration);

		return pop(context, drawer, waitForClose);
	}
}
