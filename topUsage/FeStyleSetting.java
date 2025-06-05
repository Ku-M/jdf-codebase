package fe.util.style;

import java.awt.Color;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.kwaidoo.ms.tool.CmnUtil;
import com.kwaidoo.ms.tool.ToolUtilities;

import fe.cmn.collapse.CollapseContentDecorationDto;
import fe.cmn.collapse.CollapseDecorationDto;
import fe.cmn.collapse.CollapseItemDecorationDto;
import fe.cmn.collapse.CollapseTitleDecorationDto;
import fe.cmn.data.CColor;
import fe.cmn.editor.CustomizeEditorDto;
import fe.cmn.editor.TextEditorDto;
import fe.cmn.listView.ListViewDecorationDto;
import fe.cmn.panel.BoxDto;
import fe.cmn.panel.BoxMainAxisSize;
import fe.cmn.panel.CollapseDto;
import fe.cmn.panel.CrossAxisAlign;
import fe.cmn.panel.DrawerDirection;
import fe.cmn.panel.MainAxisAlign;
import fe.cmn.panel.PanelContext;
import fe.cmn.panel.PanelDto;
import fe.cmn.panel.PanelValue;
import fe.cmn.panel.PlaceholderDto;
import fe.cmn.panel.ScrollBoxDto;
import fe.cmn.panel.SinglePanelDto;
import fe.cmn.panel.TabDto;
import fe.cmn.panel.ability.PopDialog;
import fe.cmn.panel.ability.PopDrawer;
import fe.cmn.panel.ability.PopPanelType;
import fe.cmn.res.JDFICons;
import fe.cmn.style.AppStyleDto;
import fe.cmn.style.CStyleDto;
import fe.cmn.tab.TabTheme;
import fe.cmn.text.CFontWeight;
import fe.cmn.text.CTextOverflow;
import fe.cmn.text.CTextStyle;
import fe.cmn.tree.TreeButtonBarLayoutType;
import fe.cmn.tree.TreeDto;
import fe.cmn.tree.decoration.TreeDecorationDto;
import fe.cmn.tree.decoration.TreeNodeDecorationDto;
import fe.cmn.widget.ButtonDto;
import fe.cmn.widget.CLabelAlign;
import fe.cmn.widget.IconDto;
import fe.cmn.widget.InsetDto;
import fe.cmn.widget.LabelDto;
import fe.cmn.widget.LabelSpanDto;
import fe.cmn.widget.SizeDto;
import fe.cmn.widget.WidgetDto;
import fe.cmn.widget.WindowSizeDto;
import fe.cmn.widget.decoration.BorderDto;
import fe.cmn.widget.decoration.BorderRadiusDto;
import fe.cmn.widget.decoration.BorderSideDto;
import fe.cmn.widget.decoration.BoxDecorationDto;
import fe.cmn.widget.decoration.ButtonBorderShapeType;
import fe.cmn.widget.decoration.ButtonDecorationDto;
import fe.cmn.widget.decoration.ButtonThemeType;
import fe.cmn.widget.decoration.CTextAlign;
import fe.cmn.widget.decoration.DecorationDto;
import fe.cmn.widget.decoration.DirectionAlignmentType;
import fe.cmn.widget.decoration.HintTextDecorationDto;
import fe.cmn.widget.decoration.IconDecorationDto;
import fe.cmn.widget.decoration.IconStyleDto;
import fe.cmn.widget.decoration.LabelDecorationDto;
import fe.cmn.widget.decoration.LabelSpanDecorationDto;
import fe.cmn.widget.decoration.MouseCursorType;
import fe.cmn.widget.decoration.RadiusDto;
import fe.cmn.widget.decoration.TextEditorDecorationDto;
import fe.util.component.FormEditPanelIntf;
import fe.util.component.PopupPanel;

public class FeStyleSetting implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6892196895529741950L;

	public FeStyleSetting(String styleId) {
		this.styleId = styleId;
	}

// private static FeStyleSetting inst = new FeStyleSetting();
// 
// public static FeStyleSetting getMe() {
//  return inst;
// }

// public final static String styleId = "common_style_v1";
	String styleId;
	CColor doingColor = new CColor(255, 253, 204, 1);
	CColor doneColor = new CColor(210, 248, 216, 1);
	CColor waittingColor = new CColor(234, 238, 255, 1);
	CColor delayColor = new CColor(253, 237, 239, 1);
	CColor checkColor = new CColor(255, 234, 211, 1);
	CColor suspendColor = new CColor(245, 246, 250, 1);

	// 首层默认弹窗类型
	public PopPanelType firstPanelPopupType = PopPanelType.drawer;
	// 内部默认弹窗类型
	public PopPanelType innerPanelPopupType = PopPanelType.drawer;
	public SizeDto popupDialogSize = new SizeDto(1000.0, 800.0);
	public SizeDto popupDrawerSize = new WindowSizeDto(0.6, 1.0);
	public DrawerDirection drawerDirection = DrawerDirection.rtl;
	public String defaultFormBoxStyle = FeStyleConst.common_form_box;
	public String defaultFormBottomBarStyle = FeStyleConst.common_form_bottom_bar_right;
	public String defaultFormTopBarStyle = FeStyleConst.common_form_top_bar_left;
	// 主色
	public CColor mainColor = new CColor(54, 94, 254, 0.9f);
	//动效反馈颜色
	public CColor feedbackColor;
// public CColor mainBtnColor = new CColor(54,94,254, 1);
// public CColor tipsColor = CColor.fromColor(Color.GRAY);
	// 标题文字、内容文字、输入框正常状态文字
	public CColor normalColor = new CColor(52, 52, 52, 1);
	// 未选中标题文字、输入框说明叙述文字
	public CColor unselectedColor = new CColor(191, 191, 191, 1);
	// 背景底色、表格正文内容底色
	public CColor backgroudColor = new CColor(245, 246, 250, 1);
	// 边框颜色、分割线、表格标题底色、悬停未点击颜色
	public CColor borderColor = new CColor(235, 236, 240, 1);
	// 告警颜色
	public CColor warnningColor = new CColor(255, 195, 57, 1);
	// 危险颜色
	public CColor dangerousColor = new CColor(255, 82, 100, 1);

//	String bigTitleFontFamily = "Source Han Sans CN-Bold";
	// 20px Bold 思源黑体 CN 大标题文字
	double bigTitleFontSize = 18.0;
	public CTextStyle big_title_blod_text = new CTextStyle().setColor(normalColor).setFontSize(bigTitleFontSize)
//			.setFontFamily(bigTitleFontFamily)
			.setFontWeight(CFontWeight.bold).setHeight(1.2);
	// 20px BoldNormal 思源黑体 CN 大标题未选中文字
//	String bigTitleUnselectedFontFamily = "Source Han Sans CN-Normal";
	public CTextStyle big_title_unselected_text = new CTextStyle().setColor(normalColor).setFontSize(bigTitleFontSize)
//			.setFontFamily(bigTitleUnselectedFontFamily)
			.setFontWeight(CFontWeight.normal).setHeight(1.2);
	// 18px Medium 思源黑体 CN 标题文字，用于顶部导航栏、表格标题、弹窗标题、强调性文字
//	String mediumTitleFontFamily = "Source Han Sans CN-Medium";
	double mediumTitleFontSize = 16.0;
	public CTextStyle medium_title_text = new CTextStyle().setColor(normalColor).setFontSize(mediumTitleFontSize)
//			.setFontFamily(bigTitleUnselectedFontFamily)
			.setFontWeight(CFontWeight.normal).setHeight(1.2);
//	String normalTextFontFamily = "Source Han Sans CN-Normal";
	// 16px Normal 思源黑体 CN 正文，用于左侧标签栏、按钮、大部分正文字体
	double mainBodyFontSize = 14.0;
	public CTextStyle main_body_text = new CTextStyle().setColor(normalColor)
			.setFontSize(mainBodyFontSize)
//			.setFontFamily(normalTextFontFamily)
			.setFontWeight(CFontWeight.normal).setHeight(1.2);
	// 14px Normal 思源黑体 CN 辅助文字，用于添加图标组件底下标签文字
	double assistTextFontSize = 12.0;
	public CTextStyle assist_text = new CTextStyle().setColor(normalColor).setFontSize(assistTextFontSize)
//			.setFontFamily(normalTextFontFamily)
			.setFontWeight(CFontWeight.normal).setHeight(1.2);
	// 正文字体
	public CTextStyle main_body_require_text = new CTextStyle().setColor(dangerousColor);

	// 14号字体
	public CTextStyle bold_14_text = new CTextStyle().setColor(normalColor).setFontSize(mainBodyFontSize)
//			.setFontFamily(bigTitleFontFamily)
			.setFontWeight(CFontWeight.bold).setHeight(1.2);

	public CTextStyle medium_14_text = new CTextStyle().setColor(normalColor).setFontSize(mainBodyFontSize)
//			.setFontFamily(mediumTitleFontFamily)
			.setFontWeight(CFontWeight.w500).setHeight(1.2);

	public CTextStyle normal_14_text = new CTextStyle().setColor(normalColor).setFontSize(mainBodyFontSize)
//			.setFontFamily(normalTextFontFamily)
			.setFontWeight(CFontWeight.normal).setHeight(1.2);

	// 16号字体
	public CTextStyle bold_16_text = new CTextStyle().setColor(normalColor).setFontSize(mediumTitleFontSize)
//			.setFontFamily(bigTitleFontFamily)
			.setFontWeight(CFontWeight.bold).setHeight(1.2);

	public CTextStyle medium_16_text = new CTextStyle().setColor(normalColor).setFontSize(mediumTitleFontSize)
//			.setFontFamily(mediumTitleFontFamily)
			.setFontWeight(CFontWeight.w500).setHeight(1.2);

	public CTextStyle normal_16_text = new CTextStyle().setColor(normalColor).setFontSize(mediumTitleFontSize)
//			.setFontFamily(normalTextFontFamily)
			.setFontWeight(CFontWeight.normal).setHeight(1.2);

	// 18号字体
	public CTextStyle bold_18_text = new CTextStyle().setColor(normalColor).setFontSize(bigTitleFontSize)
//			.setFontFamily(bigTitleFontFamily)
			.setFontWeight(CFontWeight.bold).setHeight(1.2);

	public CTextStyle medium_18_text = new CTextStyle().setColor(normalColor).setFontSize(bigTitleFontSize)
//			.setFontFamily(mediumTitleFontFamily)
			.setFontWeight(CFontWeight.w500).setHeight(1.2);

	public CTextStyle normal_18_text = new CTextStyle().setColor(normalColor).setFontSize(bigTitleFontSize)
//			.setFontFamily(normalTextFontFamily)
			.setFontWeight(CFontWeight.normal).setHeight(1.2);

	// 手机端用的字体
	// 不固定分辨率的情况的倍数
	double power = 0.55;
	// 26号字体
	double mobileMainBodyFontSize = 26.0 * power;
	public CTextStyle bold_26_text = new CTextStyle().setColor(normalColor).setFontSize(mobileMainBodyFontSize)
//			.setFontFamily(bigTitleFontFamily)
			.setFontWeight(CFontWeight.bold).setHeight(1.2);

	public CTextStyle medium_26_text = new CTextStyle().setColor(normalColor).setFontSize(mobileMainBodyFontSize)
//			.setFontFamily(mediumTitleFontFamily)
			.setFontWeight(CFontWeight.w500).setHeight(1.2);

	public CTextStyle normal_26_text = new CTextStyle().setColor(normalColor).setFontSize(mobileMainBodyFontSize)
//			.setFontFamily(normalTextFontFamily)
			.setFontWeight(CFontWeight.normal).setHeight(1.2);

	// 28号字体
	double mobileTitleFontSize = 28.0 * power;
	public CTextStyle bold_28_text = new CTextStyle().setColor(normalColor).setFontSize(mobileTitleFontSize)
//			.setFontFamily(bigTitleFontFamily)
			.setFontWeight(CFontWeight.bold).setHeight(1.2);

	public CTextStyle medium_28_text = new CTextStyle().setColor(normalColor).setFontSize(mobileTitleFontSize)
//			.setFontFamily(mediumTitleFontFamily)
			.setFontWeight(CFontWeight.w500).setHeight(1.2);

	public CTextStyle normal_28_text = new CTextStyle().setColor(normalColor).setFontSize(mobileTitleFontSize)
//			.setFontFamily(normalTextFontFamily)
			.setFontWeight(CFontWeight.normal).setHeight(1.2);

	// 32号字体
	double mobileBigTitleFontSize = 32.0 * power;
	public CTextStyle bold_32_text = new CTextStyle().setColor(normalColor).setFontSize(mobileBigTitleFontSize)
//			.setFontFamily(bigTitleFontFamily)
			.setFontWeight(CFontWeight.bold).setHeight(1.2);

	public CTextStyle medium_32_text = new CTextStyle().setColor(normalColor).setFontSize(mobileBigTitleFontSize)
//			.setFontFamily(mediumTitleFontFamily)
			.setFontWeight(CFontWeight.w500).setHeight(1.2);

	public CTextStyle normal_32_text = new CTextStyle().setColor(normalColor).setFontSize(mobileBigTitleFontSize)
//			.setFontFamily(normalTextFontFamily)
			.setFontWeight(CFontWeight.normal).setHeight(1.2);

	// 跟随主题色的字体
	public CTextStyle follow_main_color_text = new CTextStyle().setColor(getMainColor()).setFontSize(mainBodyFontSize)
//			.setFontFamily(mediumTitleFontFamily)
			.setFontWeight(CFontWeight.w500).setHeight(1.2);
	// 跟随主题色的icon颜色
	public IconStyleDto follow_main_color_icon = new IconStyleDto().setIconColor(getMainColor());

// public ButtonDto defaultButton = new ButtonDto()
//   .setDecoration(new ButtonDecorationDto().setBackground(mainColor).set);

	public String getDefaultFormBoxStyle() {
		return defaultFormBoxStyle;
	}

	public double getBigTitleFontSize() {
		return bigTitleFontSize;
	}

	public void setBigTitleFontSize(double bigTitleFontSize) {
		this.bigTitleFontSize = bigTitleFontSize;
	}

	public double getMediumTitleFontSize() {
		return mediumTitleFontSize;
	}

	public void setMediumTitleFontSize(double mediumTitleFontSize) {
		this.mediumTitleFontSize = mediumTitleFontSize;
	}

	public double getMainBodyFontSize() {
		return mainBodyFontSize;
	}

	public void setMainBodyFontSize(double mainBodyFontSize) {
		this.mainBodyFontSize = mainBodyFontSize;
	}

	public double getAssistTextFontSize() {
		return assistTextFontSize;
	}

	public void setAssistTextFontSize(double assistTextFontSize) {
		this.assistTextFontSize = assistTextFontSize;
	}

	public String getDefaultFormTopBarStyle() {
		return defaultFormTopBarStyle;
	}

	public String getDefaultFormBottomBarStyle() {
		return defaultFormBottomBarStyle;
	}

	public String getStyleId() {
		return styleId;
	}

	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	public CColor getDoingColor() {
		return doingColor;
	}

	public void setDoingColor(CColor doingColor) {
		this.doingColor = doingColor;
	}

	public CColor getDoneColor() {
		return doneColor;
	}

	public void setDoneColor(CColor doneColor) {
		this.doneColor = doneColor;
	}

	public CColor getWaittingColor() {
		return waittingColor;
	}

	public void setWaittingColor(CColor waittingColor) {
		this.waittingColor = waittingColor;
	}

	public CColor getDelayColor() {
		return delayColor;
	}

	public void setDelayColor(CColor delayColor) {
		this.delayColor = delayColor;
	}

	public CColor getCheckColor() {
		return checkColor;
	}

	public void setCheckColor(CColor checkColor) {
		this.checkColor = checkColor;
	}

	public CColor getSuspendColor() {
		return suspendColor;
	}

	public void setSuspendColor(CColor suspendColor) {
		this.suspendColor = suspendColor;
	}

	public PopPanelType getFirstPanelPopupType() {
		return firstPanelPopupType;
	}

	public void setFirstPanelPopupType(PopPanelType firstPanelPopupType) {
		this.firstPanelPopupType = firstPanelPopupType;
	}

	public PopPanelType getInnerPanelPopupType() {
		return innerPanelPopupType;
	}

	public void setInnerPanelPopupType(PopPanelType innerPanelPopupType) {
		this.innerPanelPopupType = innerPanelPopupType;
	}

	public SizeDto getPopupDialogSize() {
		return popupDialogSize;
	}

	public void setPopupDialogSize(SizeDto popupDialogSize) {
		this.popupDialogSize = popupDialogSize;
	}

	public SizeDto getPopupDrawerSize() {
		return popupDrawerSize;
	}

	public void setPopupDrawerSize(SizeDto popupDrawerSize) {
		this.popupDrawerSize = popupDrawerSize;
	}

	public DrawerDirection getDrawerDirection() {
		return drawerDirection;
	}

	public void setDrawerDirection(DrawerDirection drawerDirection) {
		this.drawerDirection = drawerDirection;
	}

	public CColor getMainColor() {
		return mainColor;
	}
	
	public CColor getFeedbackColor() {
		if(feedbackColor == null) {
			try {
				feedbackColor = ToolUtilities.clone(mainColor);
				feedbackColor.setOpacity(0.2f);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return feedbackColor;
	}

	public void setMainColor(CColor mainColor) {
		this.mainColor = mainColor;
	}

	public CColor getNormalColor() {
		return normalColor;
	}

	public void setNormalColor(CColor normalColor) {
		this.normalColor = normalColor;
	}

	public CColor getUnselectedColor() {
		return unselectedColor;
	}

	public void setUnselectedColor(CColor unselectedColor) {
		this.unselectedColor = unselectedColor;
	}

	public CColor getBackgroudColor() {
		return backgroudColor;
	}

	public void setBackgroudColor(CColor backgroudColor) {
		this.backgroudColor = backgroudColor;
	}

	public CColor getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(CColor borderColor) {
		this.borderColor = borderColor;
	}

	public CColor getWarnningColor() {
		return warnningColor;
	}

	public void setWarnningColor(CColor warnningColor) {
		this.warnningColor = warnningColor;
	}

	public CColor getDangerousColor() {
		return dangerousColor;
	}

	public void setDangerousColor(CColor dangerousColor) {
		this.dangerousColor = dangerousColor;
	}

//	public String getBigTitleFontFamily() {
//		return bigTitleFontFamily;
//	}
//
//	public void setBigTitleFontFamily(String bigTitleFontFamily) {
//		this.bigTitleFontFamily = bigTitleFontFamily;
//	}

	public CTextStyle getBig_title_blod_text() {
		return big_title_blod_text;
	}

	public void setBig_title_blod_text(CTextStyle big_title_blod_text) {
		this.big_title_blod_text = big_title_blod_text;
	}

//	public String getBigTitleUnselectedFontFamily() {
//		return bigTitleUnselectedFontFamily;
//	}
//
//	public void setBigTitleUnselectedFontFamily(String bigTitleUnselectedFontFamily) {
//		this.bigTitleUnselectedFontFamily = bigTitleUnselectedFontFamily;
//	}

	public CTextStyle getBig_title_unselected_text() {
		return big_title_unselected_text;
	}

	public void setBig_title_unselected_text(CTextStyle big_title_unselected_text) {
		this.big_title_unselected_text = big_title_unselected_text;
	}

//	public String getMediumTitleFontFamily() {
//		return mediumTitleFontFamily;
//	}
//
//	public void setMediumTitleFontFamily(String mediumTitleFontFamily) {
//		this.mediumTitleFontFamily = mediumTitleFontFamily;
//	}

	public CTextStyle getMedium_title_text() {
		return medium_title_text;
	}

	public void setMedium_title_text(CTextStyle medium_title_text) {
		this.medium_title_text = medium_title_text;
	}

//	public String getNormalTextFontFamily() {
//		return normalTextFontFamily;
//	}
//
//	public void setNormalTextFontFamily(String normalTextFontFamily) {
//		this.normalTextFontFamily = normalTextFontFamily;
//	}

	public CTextStyle getMain_body_text() {
		return main_body_text;
	}

	public void setMain_body_text(CTextStyle main_body_text) {
		this.main_body_text = main_body_text;
	}

	public CTextStyle getAssist_text() {
		return assist_text;
	}

	public void setAssist_text(CTextStyle assist_text) {
		this.assist_text = assist_text;
	}

	public CTextStyle getMain_body_require_text() {
		return main_body_require_text;
	}

	public void setMain_body_require_text(CTextStyle main_body_require_text) {
		this.main_body_require_text = main_body_require_text;
	}

	public void setDefaultFormBoxStyle(String defaultFormBoxStyle) {
		this.defaultFormBoxStyle = defaultFormBoxStyle;
	}

	public void setDefaultFormBottomBarStyle(String defaultFormBottomBarStyle) {
		this.defaultFormBottomBarStyle = defaultFormBottomBarStyle;
	}

	public void setDefaultFormTopBarStyle(String defaultFormTopBarStyle) {
		this.defaultFormTopBarStyle = defaultFormTopBarStyle;
	}

	public CTextStyle getBold_14_text() {
		return bold_14_text;
	}

	public void setBold_14_text(CTextStyle bold_14_text) {
		this.bold_14_text = bold_14_text;
	}

	public CTextStyle getMedium_14_text() {
		return medium_14_text;
	}

	public void setMedium_14_text(CTextStyle medium_14_text) {
		this.medium_14_text = medium_14_text;
	}

	public CTextStyle getNormal_14_text() {
		return normal_14_text;
	}

	public void setNormal_14_text(CTextStyle normal_14_text) {
		this.normal_14_text = normal_14_text;
	}

	public CTextStyle getBold_16_text() {
		return bold_16_text;
	}

	public void setBold_16_text(CTextStyle bold_16_text) {
		this.bold_16_text = bold_16_text;
	}

	public CTextStyle getMedium_16_text() {
		return medium_16_text;
	}

	public void setMedium_16_text(CTextStyle medium_16_text) {
		this.medium_16_text = medium_16_text;
	}

	public CTextStyle getNormal_16_text() {
		return normal_16_text;
	}

	public void setNormal_16_text(CTextStyle normal_16_text) {
		this.normal_16_text = normal_16_text;
	}

	public CTextStyle getBold_18_text() {
		return bold_18_text;
	}

	public void setBold_18_text(CTextStyle bold_18_text) {
		this.bold_18_text = bold_18_text;
	}

	public CTextStyle getMedium_18_text() {
		return medium_18_text;
	}

	public void setMedium_18_text(CTextStyle medium_18_text) {
		this.medium_18_text = medium_18_text;
	}

	public CTextStyle getNormal_18_text() {
		return normal_18_text;
	}

	public void setNormal_18_text(CTextStyle normal_18_text) {
		this.normal_18_text = normal_18_text;
	}

	public CTextStyle getBold_26_text() {
		return bold_26_text;
	}

	public void setBold_26_text(CTextStyle bold_26_text) {
		this.bold_26_text = bold_26_text;
	}

	public CTextStyle getMedium_26_text() {
		return medium_26_text;
	}

	public void setMedium_26_text(CTextStyle medium_26_text) {
		this.medium_26_text = medium_26_text;
	}

	public CTextStyle getNormal_26_text() {
		return normal_26_text;
	}

	public void setNormal_26_text(CTextStyle normal_26_text) {
		this.normal_26_text = normal_26_text;
	}

	public CTextStyle getBold_28_text() {
		return bold_28_text;
	}

	public void setBold_28_text(CTextStyle bold_28_text) {
		this.bold_28_text = bold_28_text;
	}

	public CTextStyle getMedium_28_text() {
		return medium_28_text;
	}

	public void setMedium_28_text(CTextStyle medium_28_text) {
		this.medium_28_text = medium_28_text;
	}

	public CTextStyle getNormal_28_text() {
		return normal_28_text;
	}

	public void setNormal_28_text(CTextStyle normal_28_text) {
		this.normal_28_text = normal_28_text;
	}

	public CTextStyle getBold_32_text() {
		return bold_32_text;
	}

	public void setBold_32_text(CTextStyle bold_32_text) {
		this.bold_32_text = bold_32_text;
	}

	public CTextStyle getMedium_32_text() {
		return medium_32_text;
	}

	public void setMedium_32_text(CTextStyle medium_32_text) {
		this.medium_32_text = medium_32_text;
	}

	public CTextStyle getNormal_32_text() {
		return normal_32_text;
	}

	public void setNormal_32_text(CTextStyle normal_32_text) {
		this.normal_32_text = normal_32_text;
	}

	public PanelValue popupFirstPanel(PanelContext ctx, String title, FormEditPanelIntf content, boolean showClose,
			boolean ismobile) throws Exception {
		if (ismobile) {
			return PopupPanel.showDrawer(ctx, title, drawerDirection, content, showClose);
		} else {
			if (firstPanelPopupType == PopPanelType.dialog) {
				return PopupPanel.showDialog(ctx, title, content, showClose);
			} else {
				return PopupPanel.showDrawer(ctx, title, drawerDirection, content, showClose);
			}
		}
	}

	public void popupFirstPanel(PanelContext ctx, String title, PanelDto panel, boolean ismobile) throws Exception {
		if (ismobile) {
			PopDrawer.show(ctx, title, panel);
		} else {
			if (firstPanelPopupType == PopPanelType.dialog) {
				PopDialog.show(ctx, title, panel);
			} else {
				PopDrawer.show(ctx, title, panel);
			}
		}
	}

	public PanelValue popupInnerPanel(PanelContext ctx, String title, FormEditPanelIntf content, boolean showClose,
			boolean ismobile) throws Exception {
		if (ismobile) {
			return PopupPanel.showDrawer(ctx, title, drawerDirection, content, showClose);
		} else {
			if (innerPanelPopupType == PopPanelType.dialog) {
				return PopupPanel.showDialog(ctx, title, content, showClose);
			} else {
				return PopupPanel.showDrawer(ctx, title, drawerDirection, content, showClose);
			}
		}
	}

	public void popupInnerPanel(PanelContext ctx, String title, PanelDto panel, boolean ismobile) throws Exception {
		if (ismobile) {
			PopDrawer.show(ctx, title, panel);
		} else {
			if (innerPanelPopupType == PopPanelType.dialog) {
				PopDialog.show(ctx, title, panel);
			} else {
				PopDrawer.show(ctx, title, panel);
			}
		}
	}

	public void setBottomBarStyle(BoxDto bottomBar, String styleName) {
		if (CmnUtil.isStringEmpty(styleName)) {
			bottomBar.setStyleName(styleName);
		} else {
			bottomBar.setStyleName(defaultFormBottomBarStyle);
		}
	}

	public AppStyleDto getStyleTree() throws Exception {
		AppStyleDto appStyle = new AppStyleDto(styleId, new CStyleDto());
		appStyle.getRoot().setName("Root");
		appStyle.getRoot().setLabel("根节点");

		// 滚动条样式
		initScrollBarStyle(appStyle);
		// 表单盒子样式
		initFormBoxStyle(appStyle);
		// 属性盒子样式_横向
		initFieldBoxStyle(appStyle);
		// 属性标签盒子样式
		initFieldLabelBoxStyle(appStyle);
		// 自定义编辑器样式
		initPcFieldCustomEditorStyle(appStyle);
		// 属性标签样式
		initPcFieldLabelSpanStyle(appStyle);
		// 图标样式
		initIconStyle(appStyle);
		// 树顶部盒子样式
		initTreeTopBarStyle(appStyle);
		// 树内容样式
		initTreeStyle(appStyle);
		// 菜单树内容样式
		initMenuTreeStyle(appStyle);
		// 表格顶部栏样式
		initTableTopBarBoxStyle(appStyle);
		// 搜索栏输入框样式
		initSearchTextStyle(appStyle);
		// 工具栏图标按钮样式
		initToolBarIconBtnStyle(appStyle);
		// 工具栏文本按钮样式
		initToolBarTextBtnStyle(appStyle);
		// 表单文本按钮样式
		initFormTextBtnStyle(appStyle);
		// 表单提交按钮样式
		initFormSubmitBtnStyle(appStyle);
		// 表单警告按钮样式
		initFormWarnningBtnStyle(appStyle);
		// 表单危险按钮样式
		initFormDangerousBtnStyle(appStyle);
		// 表单顶部按钮栏左对齐样式
		initFormTopBarLeftStyle(appStyle);
		// 表单顶部按钮栏居中样式
		initFormTopBarCenterStyle(appStyle);
		// 表单顶部按钮栏居中样式
		initFormTopBarRightStyle(appStyle);
		// 表单底部按钮栏左对齐样式
		initFormBottomBarLeftStyle(appStyle);
		// 表单底部按钮栏居中样式
		initFormBottomBarCenterStyle(appStyle);
		// 表单底部按钮栏居右样式
		initFormBottomBarRightStyle(appStyle);
		// 正文文本样式
		initMainBodyLabelStyle(appStyle);
		// 正文必填文本样式
		initMainBodyRequireLabelStyle(appStyle);
		// 标题加粗文本样式
		initBigTitleLabelStyle(appStyle);
		// 通用TAB样式
		initTabDtoStyle(appStyle);
		// 白色背景菜单树
		initWhiteMenuTreeStyle(appStyle);
		//折叠页样式
		initCollapseStyle(appStyle);
		initCollapseItemSlotLeftStyle(appStyle);

		// 移动端底部Tab按钮样式
		initMobileBottomTabStyle(appStyle);
		// 移动端底部Tab按钮图标样式
		initMobileBottomTabIconStyle(appStyle);
		// 移动端底部Tab按钮文本样式
		initMobileBottomTabTextStyle(appStyle);
		// 移动端工作区间折叠面板样式
		initMobileWorkbrenchCollapseStyle(appStyle);
		// 移动端面板背景样式
		initMobilePanelBackGroundStyle(appStyle);
		// 移动端应用盒子样式
		initMobileWorkBrenchAppBoxStyle(appStyle);
		// 移动端ListView样式
		initMobileListViewStyle(appStyle);

		initMobileListViewTitleStyle(appStyle);
		initMobileListViewSubTitleStyle(appStyle);
		initMobileListViewAsideStyle(appStyle);
		initMobileListSwipeButtonStyle(appStyle);

		initMobileFormSubmitButtonStyle(appStyle);
		initMobileFormFieldBoxStyle(appStyle);
		initMobileEmbedFormFieldBoxStyle(appStyle);
		initMobileFormFieldLabelBoxStyle(appStyle);
		initMobileFormTopBarStyle(appStyle);
		initMobileFormTopBarButtonStyle(appStyle);
		initMobileFormBottonBarStyle(appStyle);
		initMobileFormBoxStyle(appStyle);
		initMobilePartFormBoxStyle(appStyle);
		initMobilePopupConfirmButtonStyle(appStyle);
		initMobilePopupCancelButtonStyle(appStyle);

		initEditorAlignLeftStyle(appStyle);
		initEditorAlignCenterStyle(appStyle);
		initEditorAlignRightStyle(appStyle);
		return appStyle;
	}
	
//	public void initCollapseItemTitleStyle(AppStyleDto appStyle) {
//		LabelDto collapse_item_label = new LabelDto().setDecoration(new LabelDecorationDto().setMaxLines(1)
////                        .setTextStyle(medium_title_text)
//                        .setBorder(new BorderDto().setLeft(new BorderSideDto(mainColor, 3)))
//                        .setPadding(new InsetDto(8.0, 8.0, 0.0, 8.0))
//                ); 
//		CStyleDto style_mobile_list_view_title = new CStyleDto(FeStyleConst.common_collapse_item_label);
//		style_mobile_list_view_title.setLabel("折叠页标题样式");
//		style_mobile_list_view_title.setStyleWidget(collapse_item_label);
//		appStyle.getRoot().addChild(style_mobile_list_view_title);
//	}
	
	public void initCollapseStyle(AppStyleDto appStyle) {
		CollapseDecorationDto collapseDecorationDto = new CollapseDecorationDto()
				.setItemDecorationDto(new CollapseItemDecorationDto().setMargin(InsetDto.topBottom(5)));
		CollapseDto collapse = new CollapseDto();
		collapse.setDecoration(collapseDecorationDto);
		
		CStyleDto style_mobile_list_view_title = new CStyleDto(FeStyleConst.common_collapse);
		style_mobile_list_view_title.setLabel("折叠页样式");
		style_mobile_list_view_title.setStyleWidget(collapse);
		appStyle.getRoot().addChild(style_mobile_list_view_title);
	}
	
	public void initCollapseItemSlotLeftStyle(AppStyleDto appStyle) {
		BoxDto collapse_item_slot = new BoxDto().setVertical(false)
                .setMainAxisAlignment(MainAxisAlign.start)
                .setCrossAxisAlignment(CrossAxisAlign.start)
                .setMainAxisSize(BoxMainAxisSize.min)
                .setExpandInBox(false)
                .setDecoration(new DecorationDto()
//                        .setTextStyle(medium_title_text)
                        .setBorder(new BorderDto().setLeft(new BorderSideDto(mainColor, 3)))
                        .setPadding(new InsetDto(10.0, 5.0, 10.0, 5.0))
                ); 
		CStyleDto style_mobile_list_view_title = new CStyleDto(FeStyleConst.common_collapse_item_slot_left);
		style_mobile_list_view_title.setLabel("折叠页插槽左样式");
		style_mobile_list_view_title.setStyleWidget(collapse_item_slot);
		appStyle.getRoot().addChild(style_mobile_list_view_title);
	}
	
	public void initCollapseItemSlotBottomStyle(AppStyleDto appStyle) {
		BoxDto collapse_item_slot = new BoxDto().setVertical(false)
                .setMainAxisAlignment(MainAxisAlign.start)
                .setCrossAxisAlignment(CrossAxisAlign.start)
                .setMainAxisSize(BoxMainAxisSize.min)
                .setExpandInBox(false)
                .setDecoration(new DecorationDto()
//                        .setTextStyle(medium_title_text)
                        .setBorder(new BorderDto().setBottom(new BorderSideDto(mainColor, 3)))
                        .setPadding(new InsetDto(10.0, 5.0, 10.0, 5.0))
                ); 
		CStyleDto style_mobile_list_view_title = new CStyleDto(FeStyleConst.common_collapse_item_slot_bottom);
		style_mobile_list_view_title.setLabel("折叠页插槽底部样式");
		style_mobile_list_view_title.setStyleWidget(collapse_item_slot);
		appStyle.getRoot().addChild(style_mobile_list_view_title);
	}

	public void initMobileListViewTitleStyle(AppStyleDto appStyle) {
		CTextStyle main_body_mark_text = new CTextStyle().setColor(normalColor).setFontSize(32.0)
//				.setFontFamily("PingFang SC-Bold")
				.setFontWeight(CFontWeight.bold);
		CTextStyle main_body_text = new CTextStyle().setColor(normalColor).setFontSize(32.0)
//				.setFontFamily("PingFang SC-Bold")
				.setFontWeight(CFontWeight.bold);

		Map<String, CTextStyle> mobileItemTextStyles = new HashMap<>();
		mobileItemTextStyles.put("mark", main_body_mark_text);
		mobileItemTextStyles.put("normal", main_body_text);
		LabelSpanDto mobile_list_view_title = new LabelSpanDto().setDecoration(
				new LabelSpanDecorationDto().setItemTextStyles(mobileItemTextStyles).setAlign(CLabelAlign.LEFT_CENTER));
		CStyleDto style_mobile_list_view_title = new CStyleDto(FeStyleConst.mobile_list_view_title);
		style_mobile_list_view_title.setLabel("移动端ListView标题样式");
		style_mobile_list_view_title.setStyleWidget(mobile_list_view_title);
		appStyle.getRoot().addChild(style_mobile_list_view_title);
	}

	public void initMobileListViewSubTitleStyle(AppStyleDto appStyle) {
		CTextStyle main_body_text = new CTextStyle().setColor(new CColor(190, 190, 190, 1)).setFontSize(24.0)
//				.setFontFamily("PingFang SC-Bold")
				;
		LabelSpanDto mobile_list_view_title = new LabelSpanDto().setDecoration(
				new LabelSpanDecorationDto().setAlign(CLabelAlign.LEFT_CENTER).setTextStyle(main_body_text));
		CStyleDto style_mobile_list_view_title = new CStyleDto(FeStyleConst.mobile_list_view_sub_title);
		style_mobile_list_view_title.setLabel("移动端ListView副标题样式");
		style_mobile_list_view_title.setStyleWidget(mobile_list_view_title);
		appStyle.getRoot().addChild(style_mobile_list_view_title);
	}

	public void initMobileListViewAsideStyle(AppStyleDto appStyle) {
		CTextStyle main_body_text = new CTextStyle().setColor(new CColor(54, 94, 254, 1)).setFontSize(24.0)
//				.setFontFamily("PingFang SC-Bold")
				.setFontWeight(CFontWeight.bold);
		LabelSpanDto mobile_list_view_title = new LabelSpanDto().setDecoration(
				new LabelSpanDecorationDto().setAlign(CLabelAlign.RIGHT_CENTER).setTextStyle(main_body_text));
		CStyleDto style_mobile_list_view_title = new CStyleDto(FeStyleConst.mobile_list_view_aside);
		style_mobile_list_view_title.setLabel("移动端ListView侧边栏样式");
		style_mobile_list_view_title.setStyleWidget(mobile_list_view_title);
		appStyle.getRoot().addChild(style_mobile_list_view_title);
	}

	public void initMobileFormBottonBarStyle(AppStyleDto appStyle) {
		BoxDto form_bottom_bar_left = new BoxDto().setVertical(false).setExpandInBox(false)
				.setMainAxisAlignment(MainAxisAlign.center).setCrossAxisAlignment(CrossAxisAlign.center)
				.setDecoration(new DecorationDto().setMargin(new InsetDto(0.0, 0.0, 0.0, 12.0))
						.setPadding(new InsetDto(32.0, 24.0, 32.0, 24.0)));
		CStyleDto common_form_bottom_bar_left = new CStyleDto(FeStyleConst.mobile_form_bottom_bar);
		common_form_bottom_bar_left.setLabel("移动端表单底部栏样式");
		common_form_bottom_bar_left.setStyleWidget(form_bottom_bar_left);
		appStyle.getRoot().addChild(common_form_bottom_bar_left);

	}

	public void initMobileFormSubmitButtonStyle(AppStyleDto appStyle) {
		CTextStyle button_text = new CTextStyle().setFontWeight(CFontWeight.bold)
//		  .setFontSize(32.0)
		;
		ButtonDto form_submit_btn = new ButtonDto().setDecoration(
				new ButtonDecorationDto().setIcon(new IconStyleDto().setSize(32)).setTheme(ButtonThemeType.confirmStyle)
//      .setIcon(new IconStyleDto().setSize(20))
//      .setBackground(mainColor)
//      .setBorderRadius(BorderRadiusDto.all(RadiusDto.circular(12)))
						.setTextStyle(button_text)

						.setMargin(InsetDto.leftRight(7)));
		form_submit_btn.setPreferHeight(40);
		CStyleDto common_form_submit_btn = new CStyleDto(FeStyleConst.mobile_form_submit_btn);
		common_form_submit_btn.setLabel("移动端底部栏提交按钮样式");
		common_form_submit_btn.setStyleWidget(form_submit_btn);
		appStyle.getRoot().addChild(common_form_submit_btn);

	}
	
	public void initMobileFormCancelButtonStyle(AppStyleDto appStyle) {
		CTextStyle button_text = new CTextStyle().setFontWeight(CFontWeight.bold)
//		  .setFontSize(32.0)
		;
		ButtonDto form_submit_btn = new ButtonDto().setDecoration(
				new ButtonDecorationDto().setIcon(new IconStyleDto().setSize(32)).setTheme(ButtonThemeType.cancelStyle)
//      .setIcon(new IconStyleDto().setSize(20))
//      .setBackground(mainColor)
//      .setBorderRadius(BorderRadiusDto.all(RadiusDto.circular(12)))
						.setTextStyle(button_text)

						.setMargin(InsetDto.leftRight(7)));
		form_submit_btn.setPreferHeight(40);
		CStyleDto common_form_submit_btn = new CStyleDto(FeStyleConst.mobile_form_cancel_btn);
		common_form_submit_btn.setLabel("移动端底部栏取消按钮样式");
		common_form_submit_btn.setStyleWidget(form_submit_btn);
		appStyle.getRoot().addChild(common_form_submit_btn);

	}

	public void initMobileFormFieldBoxStyle(AppStyleDto appStyle) {
		BorderSideDto borderSide = new BorderSideDto(borderColor, 1);
		
		BoxDto fieldBox = new BoxDto();
		fieldBox.setVertical(false);
		fieldBox.setMainAxisAlignment(MainAxisAlign.start).setCrossAxisAlignment(CrossAxisAlign.center);
		fieldBox.setDecoration(new DecorationDto().setBackground(CColor.fromColor(Color.white))
				.setBorder(new BorderDto(null, null, null, borderSide))
//				.setBorderRadius(BorderRadiusDto.all(RadiusDto.circular(10)))
//    .setPadding(InsetDto.all(6))
				.setPadding(new InsetDto(0.0, 12.0, 0.0, 12.0))
//				.setMargin(new InsetDto(32.0, 0.0, 32.0, 0.0))
				)
		;

		CStyleDto common_form_submit_btn = new CStyleDto(FeStyleConst.mobile_form_field_box);
		common_form_submit_btn.setLabel("移动端表单属性盒子样式");
		common_form_submit_btn.setStyleWidget(fieldBox);
		appStyle.getRoot().addChild(common_form_submit_btn);
		
		
		BoxDto withoutBorderFieldBox = new BoxDto();
		withoutBorderFieldBox.setVertical(false);
		withoutBorderFieldBox.setMainAxisAlignment(MainAxisAlign.start).setCrossAxisAlignment(CrossAxisAlign.center);
		withoutBorderFieldBox.setDecoration(new DecorationDto().setBackground(CColor.fromColor(Color.white))
//				.setBorder(new BorderDto(null, null, null, borderSide))
//				.setBorderRadius(BorderRadiusDto.all(RadiusDto.circular(10)))
//    .setPadding(InsetDto.all(6))
				.setPadding(new InsetDto(0.0, 12.0, 0.0, 12.0))
//				.setMargin(new InsetDto(32.0, 0.0, 32.0, 0.0))
				)
		;
		CStyleDto common_form_submit_btn_without_border = new CStyleDto(FeStyleConst.mobile_form_field_box_without_border);
		common_form_submit_btn_without_border.setLabel("移动端表单属性盒子样式-不带边框");
		common_form_submit_btn_without_border.setStyleWidget(withoutBorderFieldBox);
		appStyle.getRoot().addChild(common_form_submit_btn_without_border);
		

	}

	public void initMobileEmbedFormFieldBoxStyle(AppStyleDto appStyle) {
		BorderSideDto borderSide = new BorderSideDto(getBorderColor(), 1);
		BoxDto fieldBox = new BoxDto();
		fieldBox.setVertical(false);
		fieldBox.setMainAxisAlignment(MainAxisAlign.start).setCrossAxisAlignment(CrossAxisAlign.start);
		fieldBox.setDecoration(new DecorationDto().setBackground(CColor.fromColor(Color.white))
				.setBorder(new BorderDto(null, null, null, borderSide))
//	    .setBorderRadius(BorderRadiusDto.all(RadiusDto.circular(10)))
//	    .setPadding(InsetDto.all(6))
				.setPadding(new InsetDto(10.0, 0.0, 10.0, 0.0)).setMargin(InsetDto.topBottom(6)));

		CStyleDto common_form_submit_btn = new CStyleDto(FeStyleConst.mobile_embedform_field_box);
		common_form_submit_btn.setLabel("移动端子表单属性盒子样式");
		common_form_submit_btn.setStyleWidget(fieldBox);
		appStyle.getRoot().addChild(common_form_submit_btn);

	}

	public void initMobileFormFieldLabelBoxStyle(AppStyleDto appStyle) {
		BoxDto labelBox = new BoxDto();
		labelBox.setDecoration(new DecorationDto()
//				.setPadding(new InsetDto(9.0, 8.0, 8.0, 8.0))
				.setPadding(new InsetDto(9.0, 0.0, 8.0, 0.0))
				);

		CStyleDto common_form_submit_btn = new CStyleDto(FeStyleConst.mobile_form_field_label_box);
		common_form_submit_btn.setLabel("移动端表单属性标签盒子样式");
		common_form_submit_btn.setStyleWidget(labelBox);
		appStyle.getRoot().addChild(common_form_submit_btn);

	}
	
	public void initMobileListSwipeButtonStyle(AppStyleDto appStyle) {
		ButtonDto mobile_list_swipe_button = new ButtonDto().setDecoration(new ButtonDecorationDto()
				.setButtonShape(ButtonBorderShapeType.CircleBorder)
				.setIcon(new IconStyleDto().setSize(32)).setButtonBorder(new BorderSideDto().setWidth(0))
				.setHoveredButtonBorder(new BorderSideDto().setWidth(0))
				.setFocusedButtonBorder(new BorderSideDto().setWidth(0))
				.setHoveredForegroundColor(new CColor(47, 88, 254, 1))
				.setFocusedForegroundColor(new CColor(47, 88, 254, 1)).setFocusedBackgroundColor(CColor.transparent())
				.setBorderRadius(BorderRadiusDto.all(RadiusDto.zero())).setBackground(mainColor)
				.setTextStyle(new CTextStyle().setFontWeight(CFontWeight.bold).setFontSize(32.0).setColor(Color.WHITE)));
		CStyleDto style_mobile_list_swipe_button = new CStyleDto(FeStyleConst.mobile_list_swipe_button);
		style_mobile_list_swipe_button.setLabel("移动端滑动按钮样式");
		style_mobile_list_swipe_button.setStyleWidget(mobile_list_swipe_button);
		appStyle.getRoot().addChild(style_mobile_list_swipe_button);
		
		ButtonDto mobile_list_swipe_button_dangerous = new ButtonDto().setDecoration(new ButtonDecorationDto().setBackground(dangerousColor));
		CStyleDto style_mobile_list_swipe_button_dangerous = new CStyleDto(FeStyleConst.mobile_list_swipe_button_dangerous);
		style_mobile_list_swipe_button_dangerous.setLabel("移动端滑动按钮样式-危险");
		style_mobile_list_swipe_button_dangerous.setStyleWidget(mobile_list_swipe_button_dangerous);
		style_mobile_list_swipe_button.addChild(style_mobile_list_swipe_button_dangerous);
	}
	

	public void initMobileFormTopBarStyle(AppStyleDto appStyle) {
		BoxDto topBar = new BoxDto();
		topBar.setDecoration(new DecorationDto().setBackground(mainColor));

		CStyleDto common_form_submit_btn = new CStyleDto(FeStyleConst.mobile_form_top_bar);
		common_form_submit_btn.setLabel("移动端表单顶部栏盒子样式");
		common_form_submit_btn.setStyleWidget(topBar);
		appStyle.getRoot().addChild(common_form_submit_btn);

	}

	public void initMobileFormTopBarButtonStyle(AppStyleDto appStyle) {
		ButtonDto mobile_bottom_tab = new ButtonDto().setDecoration(new ButtonDecorationDto()
				.setIcon(new IconStyleDto().setSize(32)).setButtonBorder(new BorderSideDto().setWidth(0))
				.setHoveredButtonBorder(new BorderSideDto().setWidth(0))
				.setFocusedButtonBorder(new BorderSideDto().setWidth(0))
				.setHoveredForegroundColor(new CColor(47, 88, 254, 1))
				.setFocusedForegroundColor(new CColor(47, 88, 254, 1)).setFocusedBackgroundColor(CColor.transparent())
				.setBorderRadius(BorderRadiusDto.all(RadiusDto.zero())).setBackground(CColor.transparent())
				.setTextStyle(new CTextStyle().setFontWeight(CFontWeight.bold).setFontSize(32.0)));
		CStyleDto style_mobile_bottom_tab = new CStyleDto(FeStyleConst.mobile_form_top_bar_button);
		style_mobile_bottom_tab.setLabel("移动端表单顶部栏按钮样式");
		style_mobile_bottom_tab.setStyleWidget(mobile_bottom_tab);
		appStyle.getRoot().addChild(style_mobile_bottom_tab);

	}

	public void initMobilePopupCancelButtonStyle(AppStyleDto appStyle) {
		ButtonDto mobile_bottom_tab = new ButtonDto().setDecoration(new ButtonDecorationDto()
				.setButtonBorder(new BorderSideDto().setWidth(0))
				.setHoveredButtonBorder(new BorderSideDto().setWidth(0))
				.setFocusedButtonBorder(new BorderSideDto().setWidth(0)).setHoveredForegroundColor(CColor.transparent())
				.setFocusedForegroundColor(CColor.transparent()).setFocusedBackgroundColor(CColor.transparent())
				.setBorderRadius(BorderRadiusDto.all(RadiusDto.zero())).setBackground(CColor.transparent())
				.setTextStyle(new CTextStyle().setColor(borderColor)));
		CStyleDto style_mobile_bottom_tab = new CStyleDto(FeStyleConst.mobile_popup_cancel_button);
		style_mobile_bottom_tab.setLabel("移动端弹窗取消按钮样式");
		style_mobile_bottom_tab.setStyleWidget(mobile_bottom_tab);
		appStyle.getRoot().addChild(style_mobile_bottom_tab);

	}

	public void initMobilePopupConfirmButtonStyle(AppStyleDto appStyle) {
		ButtonDto mobile_bottom_tab = new ButtonDto().setDecoration(new ButtonDecorationDto()
				.setButtonBorder(new BorderSideDto().setWidth(0))
				.setHoveredButtonBorder(new BorderSideDto().setWidth(0))
				.setFocusedButtonBorder(new BorderSideDto().setWidth(0)).setHoveredForegroundColor(CColor.transparent())
				.setFocusedForegroundColor(CColor.transparent()).setFocusedBackgroundColor(CColor.transparent())
				.setBorderRadius(BorderRadiusDto.all(RadiusDto.zero())).setBackground(CColor.transparent())
				.setTextStyle(new CTextStyle().setColor(mainColor)));
		CStyleDto style_mobile_bottom_tab = new CStyleDto(FeStyleConst.mobile_popup_confirm_button);
		style_mobile_bottom_tab.setLabel("移动端弹窗确定按钮样式");
		style_mobile_bottom_tab.setStyleWidget(mobile_bottom_tab);
		appStyle.getRoot().addChild(style_mobile_bottom_tab);

	}

	public void initMobileFormBoxStyle(AppStyleDto appStyle) {
		BoxDto mobile_form_box = new BoxDto();
		mobile_form_box.setDecoration(new DecorationDto()
				.setMargin(InsetDto.leftRight(32.0))
				.setPadding(InsetDto.leftRight(32.0))
				.setBackground(Color.white).setBorderRadius(BorderRadiusDto.all(RadiusDto.circular(6))));
		CStyleDto style_mobile_bottom_tab = new CStyleDto(FeStyleConst.mobile_form_box);
		style_mobile_bottom_tab.setLabel("移动端表单盒子样式");
		style_mobile_bottom_tab.setStyleWidget(mobile_form_box);
		appStyle.getRoot().addChild(style_mobile_bottom_tab);

	}
	
	public void initMobilePartFormBoxStyle(AppStyleDto appStyle) {
		BoxDto mobile_form_box = new BoxDto();
		mobile_form_box.setDecoration(new DecorationDto()
				.setMargin(new InsetDto(32.0, 24.0, 32.0, 0.0))
				.setPadding(InsetDto.leftRight(32.0))
				.setBackground(Color.white).setBorderRadius(BorderRadiusDto.all(RadiusDto.circular(6))));
		CStyleDto style_mobile_bottom_tab = new CStyleDto(FeStyleConst.mobile_part_form_box);
		style_mobile_bottom_tab.setLabel("移动端局部表单盒子样式");
		style_mobile_bottom_tab.setStyleWidget(mobile_form_box);
		appStyle.getRoot().addChild(style_mobile_bottom_tab);

	}

	public void initTabDtoStyle(AppStyleDto appStyle) {
//TabAreaStyleDto tabAreaStyle = new TabAreaStyleDto().setColor(new CColor(220, 221, 222, 1)).setMiddleGap(4.0).setInitialGap(6.0);

//  TabItemStyleDto tabItemStyle = new TabItemStyleDto().setDecoration(new DecorationDto()
//    .setBackground(new CColor(235, 236, 237, 1))
//    .setBorderRadius(new BorderRadiusDto().setTopLeft(new RadiusDto(RadiusType.circular,8)).setTopRight(new RadiusDto(RadiusType.circular,8)))).setPadding(new InsetDto(16.0, 4.0, 16.0, 0.0));
//  
//  BorderSideDto borderSide = new BorderSideDto(getBorderColor(), 1);
//  TabItemSelectedStyleDto tabItemSelectedStyle = new TabItemSelectedStyleDto().setDecoration(new DecorationDto()
////    .setBackground(new CColor(246, 246, 247, 1))
//    .setBorder(new BorderDto(borderSide,borderSide,borderSide,borderSide))
//    .setBorderRadius(new BorderRadiusDto().setTopLeft(new RadiusDto(RadiusType.circular,8)).setTopRight(new RadiusDto(RadiusType.circular,8))));

//  ContentAreaStyleDto areaStyle = new ContentAreaStyleDto().setDecoration(new DecorationDto().setBackground(new CColor(246, 246, 247, 1)));
		TabDto tab = new TabDto();
		tab.setTheme(TabTheme.underLine);
//  tab.setTabAreaStyle(tabAreaStyle);
//  tab.setTabItemStyle(tabItemStyle);
//  tab.setTabItemSelectedStyle(tabItemSelectedStyle);
//  tab.setContentAreaStyle(areaStyle);

		CStyleDto style_tab = new CStyleDto(FeStyleConst.common_tab);
		style_tab.setLabel("通用TAB样式");
		style_tab.setStyleWidget(tab);
		appStyle.getRoot().addChild(style_tab);
	}

	public void initScrollBarStyle(AppStyleDto appStyle) {
		ScrollBoxDto scrollbar = ScrollBoxDto.wrap(new PlaceholderDto()).setChildMaxHeight(9999);
		CStyleDto basic_scrollbar = new CStyleDto(FeStyleConst.common_scrollbar);
		basic_scrollbar.setLabel("滚动条样式");
		basic_scrollbar.setStyleWidget(scrollbar);
		appStyle.getRoot().addChild(basic_scrollbar);
	}

	public void initFormBoxStyle(AppStyleDto appStyle) {
		BoxDto common_form = new BoxDto().setVertical(true).setExpandInBox(false).setMainAxisSize(BoxMainAxisSize.min)
				.setDecoration(new DecorationDto()
//      .setPadding(new InsetDto(20.0, 24.0, 20.0, 40.0))
						.setPadding(new InsetDto(10.0, 5.0, 10.0, 5.0))
//      .setBorder(new BorderDto(null, new BorderSideDto(borderColor, 2), null, null))
				);
		CStyleDto basic_form_box = new CStyleDto(FeStyleConst.common_form_box);
		basic_form_box.setLabel("表单盒子样式");
		basic_form_box.setStyleWidget(common_form);
		appStyle.getRoot().addChild(basic_form_box);
	}

	public void initFieldBoxStyle(AppStyleDto appStyle) {
		CStyleDto common_field_box = new CStyleDto(FeStyleConst.common_field_box);
		common_field_box.setLabel("属性盒子样式_横向");
		BoxDto pc_field_box = new BoxDto().setVertical(false).setExpandInBox(false)
				.setMainAxisAlignment(MainAxisAlign.start).setCrossAxisAlignment(CrossAxisAlign.start)
//    .setMainAxisSize(BoxMainAxisSize.min)
				.setDecoration(new DecorationDto()
//      .setBorder(new BorderDto(null, new BorderSideDto(2), null, new BorderSideDto(2)))
//      .setPadding(new InsetDto(0.0, 20.0, 0.0, 0.0))
						.setPadding(new InsetDto(0.0, 5.0, 0.0, 5.0)));
		common_field_box.setStyleWidget(pc_field_box);
		appStyle.getRoot().addChild(common_field_box);
	}

	public void initFieldLabelBoxStyle(AppStyleDto appStyle) {
		BoxDto pc_field_label_box = new BoxDto().setVertical(false).setExpandInBox(true)
//    .setMainAxisAlignment(MainAxisAlign.end)
				.setCrossAxisAlignment(CrossAxisAlign.end).setDecoration(new DecorationDto()
//      .setPadding(new InsetDto(0.0, 14.0, 14.0, 0.0))
//      .setBorder(new BorderDto(null, new BorderSideDto((CColor)null,2), null, new BorderSideDto((CColor)null,2)))
						.setPadding(new InsetDto(0.0, 8.0, 10.0, 8.0)));
//		.setPadding(new InsetDto(0.0, 0.0, 10.0, 0.0)));
		pc_field_label_box.setPreferWidth(100);
		CStyleDto common_pc_field_label_box = new CStyleDto(FeStyleConst.common_pc_field_label_box);
		common_pc_field_label_box.setLabel("属性标签盒子样式");
		common_pc_field_label_box.setStyleWidget(pc_field_label_box);
		appStyle.getRoot().addChild(common_pc_field_label_box);
	}

	public void initPcFieldCustomEditorStyle(AppStyleDto appStyle) {
		CustomizeEditorDto pc_field_custom_editor = new CustomizeEditorDto().setChild(new PlaceholderDto());
		pc_field_custom_editor.setDecoration(new DecorationDto()
//    .setBorder(new BorderDto(null, null, null, new BorderSideDto(2)))
				.setPadding(new InsetDto(2.0, 0.0, 2.0, 5.0)));
		CStyleDto common_pc_field_custom_editor = new CStyleDto(FeStyleConst.common_field_custom_editor);
		common_pc_field_custom_editor.setLabel("自定义编辑器样式");
		common_pc_field_custom_editor.setStyleWidget(pc_field_custom_editor);
		appStyle.getRoot().addChild(common_pc_field_custom_editor);
	}

	public void initPcFieldLabelSpanStyle(AppStyleDto appStyle) throws ClassNotFoundException, IOException {
		Map<String, CTextStyle> itemTextStyles = new HashMap<>();
		CTextStyle requireStyle = ToolUtilities.clone(main_body_require_text);
		requireStyle.setHeight(1.0);
		CTextStyle labelStyle = ToolUtilities.clone(main_body_text);
		labelStyle.setHeight(1.0);
		itemTextStyles.put("require", requireStyle);
		itemTextStyles.put("normal", labelStyle);
		LabelSpanDto pc_field_label_span = new LabelSpanDto().setDecoration(
				new LabelSpanDecorationDto().setItemTextStyles(itemTextStyles).setTextAlign(CTextAlign.right)
						.setTextStyle(new CTextStyle().setOverflow(CTextOverflow.visible)));
		CStyleDto common_pc_field_label_span = new CStyleDto(FeStyleConst.common_pc_field_label_span);
		common_pc_field_label_span.setLabel("属性标签样式");
		common_pc_field_label_span.setStyleWidget(pc_field_label_span);

		LabelSpanDto pc_field_label_span_align_left = new LabelSpanDto()
				.setDecoration(new LabelSpanDecorationDto().setTextAlign(CTextAlign.left));
		CStyleDto common_pc_field_label_span_align_left = new CStyleDto(
				FeStyleConst.common_pc_field_label_span_align_left);
		common_pc_field_label_span_align_left.setLabel("属性标签样式(左对齐)");
		common_pc_field_label_span_align_left.setStyleWidget(pc_field_label_span_align_left);
		common_pc_field_label_span.addChild(common_pc_field_label_span_align_left);
		appStyle.getRoot().addChild(common_pc_field_label_span);
	}

	public void initIconStyle(AppStyleDto appStyle) {
		IconDto icon = new IconDto(JDFICons.download)
				.setDecoration(new IconDecorationDto().setSize(18).setMouseCursorType(MouseCursorType.click));
		CStyleDto common_icon = new CStyleDto(FeStyleConst.common_icon);
		common_icon.setLabel("图标样式");
		common_icon.setStyleWidget(icon);
		appStyle.getRoot().addChild(common_icon);
	}

	public void initTreeTopBarStyle(AppStyleDto appStyle) {
		CStyleDto common_tree_top_bar_box = new CStyleDto(FeStyleConst.common_tree_top_bar_box);
		common_tree_top_bar_box.setLabel("树顶部盒子样式");
		BoxDto tree_top_bar_box = new BoxDto().setVertical(false).setExpandInBox(true)
//    .setMainAxisAlignment(MainAxisAlign.start).setCrossAxisAlignment(CrossAxisAlign.start)
//    .setMainAxisSize(BoxMainAxisSize.min)
				.setDecoration(new DecorationDto().setPadding(new InsetDto(0.0, 0.0, 0.0, 10.0)));
		common_tree_top_bar_box.setStyleWidget(tree_top_bar_box);
		appStyle.getRoot().addChild(common_tree_top_bar_box);
	}

	public void initTreeStyle(AppStyleDto appStyle) {
		CStyleDto common_tree = new CStyleDto(FeStyleConst.common_tree);
		common_tree.setLabel("树内容样式");
		TreeDto tree = new TreeDto()
				.setDecoration(new TreeDecorationDto().setTreeButtonBarLayoutType(TreeButtonBarLayoutType.normalRight)
						.setPadding(new InsetDto(10.0, 10.0, 10.0, 10.0)));
		common_tree.setStyleWidget(tree);
		appStyle.getRoot().addChild(common_tree);
	}

	public void initMenuTreeStyle(AppStyleDto appStyle) throws Exception {
		CStyleDto common_menu_tree = new CStyleDto(FeStyleConst.common_menu_tree);
		common_menu_tree.setLabel("菜单树内容样式");
		// 默认样式
		TreeNodeDecorationDto defDct = new TreeNodeDecorationDto().setTextStyle(medium_14_text);

		// 选中
		TreeNodeDecorationDto selectedNodeDecorationDto = new TreeNodeDecorationDto()
				.setBackground(ToolUtilities.clone(mainColor).setOpacity(0.25f)).setTextStyle(follow_main_color_text)
				.setIconStyle(follow_main_color_icon);
		// hover样式
		TreeNodeDecorationDto hoverNodeDecorationDto = new TreeNodeDecorationDto()
				.setBackground(ToolUtilities.clone(mainColor).setOpacity(0.15f)).setTextStyle(follow_main_color_text)
				.setIconStyle(follow_main_color_icon);
		TreeDto menu_tree = new TreeDto().setDecoration(new TreeDecorationDto().setExpandIconOnFront(false)
				.setDefalutNodeDecorationDto(defDct).setHoverNodeDecorationDto(hoverNodeDecorationDto)
				.setSelectedNodeDecorationDto(selectedNodeDecorationDto)
				.setBackground(ToolUtilities.clone(mainColor).setOpacity(0.1f)));
		common_menu_tree.setStyleWidget(menu_tree);
		appStyle.getRoot().addChild(common_menu_tree);
	}

	public void initWhiteMenuTreeStyle(AppStyleDto appStyle) throws Exception {
		CStyleDto common_menu_tree = new CStyleDto(FeStyleConst.common_white_menu_tree);
		common_menu_tree.setLabel("菜单树内容样式");
		// 默认样式
		TreeNodeDecorationDto defDct = new TreeNodeDecorationDto().setTextStyle(medium_14_text);

		CTextStyle cTextStyle = new CTextStyle().setColor(getMainColor()).setFontSize(mainBodyFontSize)
//				.setFontFamily(mediumTitleFontFamily)
				.setFontWeight(CFontWeight.w500).setHeight(1.2);
		IconStyleDto follow_main_color_icon = new IconStyleDto().setIconColor(getMainColor());
		// 选中
		TreeNodeDecorationDto selectedNodeDecorationDto = new TreeNodeDecorationDto()
				.setBackground(ToolUtilities.clone(mainColor).setOpacity(0.25f)).setTextStyle(cTextStyle)
				.setIconStyle(follow_main_color_icon);
		// hover样式
		TreeNodeDecorationDto hoverNodeDecorationDto = new TreeNodeDecorationDto()
				.setBackground(ToolUtilities.clone(mainColor).setOpacity(0.15f)).setTextStyle(cTextStyle)
				.setIconStyle(follow_main_color_icon);
		TreeDto menu_tree = new TreeDto().setDecoration(new TreeDecorationDto().setExpandIconOnFront(false)
				.setDefalutNodeDecorationDto(defDct).setHoverNodeDecorationDto(hoverNodeDecorationDto)
				.setSelectedNodeDecorationDto(selectedNodeDecorationDto).setBackground(Color.WHITE));
		common_menu_tree.setStyleWidget(menu_tree);
		appStyle.getRoot().addChild(common_menu_tree);
	}

	public void initTableTopBarBoxStyle(AppStyleDto appStyle) {
		CStyleDto common_table_top_bar_box = new CStyleDto(FeStyleConst.common_table_top_bar_box);
		common_table_top_bar_box.setLabel("表格顶部栏样式");
		BoxDto table_top_bar_box = new BoxDto()
//  .setVertical(false).setExpandInBox(false)
//    .setMainAxisAlignment(MainAxisAlign.start).setCrossAxisAlignment(CrossAxisAlign.start)
//    .setMainAxisSize(BoxMainAxisSize.min)
				.setDecoration(new DecorationDto().setPadding(new InsetDto(0.0, 12.0, 0.0, 12.0)));
		common_table_top_bar_box.setStyleWidget(table_top_bar_box);
		appStyle.getRoot().addChild(common_table_top_bar_box);
	}

	public void initSearchTextStyle(AppStyleDto appStyle) {
		CStyleDto common_search_text = new CStyleDto(FeStyleConst.common_search_text);
		common_search_text.setLabel("搜索栏输入框样式");
		TextEditorDto tree_search_text = new TextEditorDto();
		tree_search_text.setDecoration(new TextEditorDecorationDto().setHintStyle(assist_text)
				.setIcon(new IconStyleDto().setSize(18)).setBackground(Color.white)
				.setBorderRadius(BorderRadiusDto.all(new RadiusDto().setRadius(12))).setTextStyle(main_body_text))
				.setPreferHeight(32);
		common_search_text.setStyleWidget(tree_search_text);
		appStyle.getRoot().addChild(common_search_text);
	}

	public void initToolBarIconBtnStyle(AppStyleDto appStyle) {
		ButtonDto toolbar_icon_btn = new ButtonDto()
				.setDecoration(new ButtonDecorationDto().setTheme(ButtonThemeType.defaultStyle)
						.setIcon(new IconStyleDto().setSize(15)).setMargin(new InsetDto(7.0, 2.0, 7.0, 2.0)));
		CStyleDto common_toolbar_icon_btn = new CStyleDto(FeStyleConst.common_toolbar_icon_btn);
		common_toolbar_icon_btn.setLabel("工具栏图标按钮样式");
		common_toolbar_icon_btn.setStyleWidget(toolbar_icon_btn);
		appStyle.getRoot().addChild(common_toolbar_icon_btn);
		
		ButtonDto table_row_icon_btn = new ButtonDto()
				.setDecoration(new ButtonDecorationDto().setTheme(ButtonThemeType.defaultStyle)
						.setIcon(new IconStyleDto().setSize(15)).setMargin(new InsetDto(7.0, 2.0, 7.0, 2.0))
						.setTextStyle(new CTextStyle().setFontSize(assistTextFontSize)));
		
		CStyleDto common_table_row_icon_btn = new CStyleDto(FeStyleConst.common_table_row_icon_btn);
		common_table_row_icon_btn.setLabel("表格行图标按钮样式");
		common_table_row_icon_btn.setStyleWidget(table_row_icon_btn);
		appStyle.getRoot().addChild(common_table_row_icon_btn);
	}

	public void initToolBarTextBtnStyle(AppStyleDto appStyle) {
		ButtonDto toolbar_text_btn = new ButtonDto()
				.setDecoration(new ButtonDecorationDto().setTheme(ButtonThemeType.defaultStyle)
						.setIcon(new IconStyleDto().setSize(20)).setMargin(new InsetDto(7.0, 2.0, 7.0, 2.0)));
		CStyleDto common_toolbar_text_btn = new CStyleDto(FeStyleConst.common_toolbar_text_btn);
		common_toolbar_text_btn.setLabel("工具栏文本按钮样式");
		common_toolbar_text_btn.setStyleWidget(toolbar_text_btn);
		appStyle.getRoot().addChild(common_toolbar_text_btn);
	}

	public void initFormTextBtnStyle(AppStyleDto appStyle) {
		ButtonDto form_text_btn = new ButtonDto().setDecoration(new ButtonDecorationDto()
				.setTheme(ButtonThemeType.defaultStyle).setIcon(new IconStyleDto().setSize(20)));
		CStyleDto common_form_text_btn = new CStyleDto(FeStyleConst.common_form_text_btn);
		common_form_text_btn.setLabel("表单文本按钮样式");
		common_form_text_btn.setStyleWidget(form_text_btn);
		appStyle.getRoot().addChild(common_form_text_btn);
	}

	public void initFormSubmitBtnStyle(AppStyleDto appStyle) {
		ButtonDto form_submit_btn = new ButtonDto()
				.setDecoration(new ButtonDecorationDto().setTheme(ButtonThemeType.confirmStyle)
//      .setIcon(new IconStyleDto().setSize(20))
//      .setBackground(mainColor)
//      .setBorderRadius(BorderRadiusDto.all(RadiusDto.circular(12)))
//      .setTextStyle(ToolUtilities.clone(main_body_text).setColor(CColor.fromColor(Color.WHITE)))
						.setMargin(InsetDto.leftRight(7)));
		CStyleDto common_form_submit_btn = new CStyleDto(FeStyleConst.common_form_submit_btn);
		common_form_submit_btn.setLabel("表单提交按钮样式");
		common_form_submit_btn.setStyleWidget(form_submit_btn);
		appStyle.getRoot().addChild(common_form_submit_btn);
	}

	public void initFormWarnningBtnStyle(AppStyleDto appStyle) {
		ButtonDto form_warnning_btn = new ButtonDto().setDecoration(
				new ButtonDecorationDto().setTheme(ButtonThemeType.confirmStyle).setBackground(warnningColor));
		CStyleDto common_form_warnning_btn = new CStyleDto(FeStyleConst.common_form_warnning_btn);
		common_form_warnning_btn.setLabel("表单警告按钮样式");
		common_form_warnning_btn.setStyleWidget(form_warnning_btn);
		appStyle.getRoot().addChild(common_form_warnning_btn);
	}

	public void initFormDangerousBtnStyle(AppStyleDto appStyle) {
		ButtonDto form_dangerous_btn = new ButtonDto().setDecoration(
				new ButtonDecorationDto().setTheme(ButtonThemeType.confirmStyle).setBackground(dangerousColor));
		CStyleDto common_form_dangerous_btn = new CStyleDto(FeStyleConst.common_form_dangerous_btn);
		common_form_dangerous_btn.setLabel("表单危险按钮样式");
		common_form_dangerous_btn.setStyleWidget(form_dangerous_btn);
		appStyle.getRoot().addChild(common_form_dangerous_btn);
	}

	public void initFormTopBarLeftStyle(AppStyleDto appStyle) {
		BorderSideDto borderline = new BorderSideDto(borderColor, 2);
		BoxDto form_top_bar_left = new BoxDto().setVertical(false).setExpandInBox(false)
				.setMainAxisAlignment(MainAxisAlign.start).setCrossAxisAlignment(CrossAxisAlign.center)
				.setDecoration(new DecorationDto().setBorder(new BorderDto(null, null, null, borderline))
						.setPadding(InsetDto.topBottom(2)));
		CStyleDto common_form_top_bar_left = new CStyleDto(FeStyleConst.common_form_top_bar_left);
		common_form_top_bar_left.setLabel("表单顶部按钮栏左对齐样式");
		common_form_top_bar_left.setStyleWidget(form_top_bar_left);
		appStyle.getRoot().addChild(common_form_top_bar_left);
	}

	public void initFormTopBarCenterStyle(AppStyleDto appStyle) {
		BorderSideDto borderline = new BorderSideDto(borderColor, 2);
		BoxDto form_top_bar_center = new BoxDto().setVertical(false).setExpandInBox(false)
				.setMainAxisAlignment(MainAxisAlign.center).setCrossAxisAlignment(CrossAxisAlign.center)
				.setDecoration(new DecorationDto().setBorder(new BorderDto(null, null, null, borderline))
						.setPadding(InsetDto.topBottom(2)));
		CStyleDto common_form_top_bar_center = new CStyleDto(FeStyleConst.common_form_top_bar_center);
		common_form_top_bar_center.setLabel("表单顶部按钮栏居中样式");
		common_form_top_bar_center.setStyleWidget(form_top_bar_center);
		appStyle.getRoot().addChild(common_form_top_bar_center);
	}

	public void initFormTopBarRightStyle(AppStyleDto appStyle) {
		BorderSideDto borderline = new BorderSideDto(borderColor, 2);
		BoxDto form_top_bar_right = new BoxDto().setVertical(false).setExpandInBox(false)
				.setMainAxisAlignment(MainAxisAlign.end).setCrossAxisAlignment(CrossAxisAlign.center)
				.setDecoration(new DecorationDto().setBorder(new BorderDto(null, null, null, borderline))
						.setPadding(InsetDto.topBottom(2)));
		CStyleDto common_form_top_bar_right = new CStyleDto(FeStyleConst.common_form_top_bar_right);
		common_form_top_bar_right.setLabel("表单顶部按钮栏居中样式");
		common_form_top_bar_right.setStyleWidget(form_top_bar_right);
		appStyle.getRoot().addChild(common_form_top_bar_right);
	}

	public void initFormBottomBarLeftStyle(AppStyleDto appStyle) {
		BoxDto form_bottom_bar_left = new BoxDto().setVertical(false).setExpandInBox(false)
				.setMainAxisAlignment(MainAxisAlign.start).setCrossAxisAlignment(CrossAxisAlign.center)
				.setDecoration(new DecorationDto().setMargin(new InsetDto().setTop(10)));
		CStyleDto common_form_bottom_bar_left = new CStyleDto(FeStyleConst.common_form_bottom_bar_left);
		common_form_bottom_bar_left.setLabel("表单底部按钮栏左对齐样式");
		common_form_bottom_bar_left.setStyleWidget(form_bottom_bar_left);
		appStyle.getRoot().addChild(common_form_bottom_bar_left);
	}

	public void initFormBottomBarCenterStyle(AppStyleDto appStyle) {
		BoxDto form_bottom_bar_center = new BoxDto().setVertical(false).setExpandInBox(false)
				.setMainAxisAlignment(MainAxisAlign.center).setCrossAxisAlignment(CrossAxisAlign.center)
				.setDecoration(new DecorationDto().setMargin(new InsetDto().setTop(10)));
		CStyleDto common_form_bottom_bar_center = new CStyleDto(FeStyleConst.common_form_bottom_bar_center);
		common_form_bottom_bar_center.setLabel("表单底部按钮栏居中样式");
		common_form_bottom_bar_center.setStyleWidget(form_bottom_bar_center);
		appStyle.getRoot().addChild(common_form_bottom_bar_center);
	}

	public void initFormBottomBarRightStyle(AppStyleDto appStyle) {
		BoxDto form_bottom_bar_right = new BoxDto().setVertical(false).setExpandInBox(false)
				.setMainAxisAlignment(MainAxisAlign.end).setCrossAxisAlignment(CrossAxisAlign.center)
				.setDecoration(new DecorationDto().setMargin(new InsetDto().setTop(10)));
		CStyleDto common_form_bottom_bar_right = new CStyleDto(FeStyleConst.common_form_bottom_bar_right);
		common_form_bottom_bar_right.setLabel("表单底部按钮栏居右样式");
		common_form_bottom_bar_right.setStyleWidget(form_bottom_bar_right);
		appStyle.getRoot().addChild(common_form_bottom_bar_right);
	}

	public void initMainBodyLabelStyle(AppStyleDto appStyle) {
		LabelDto main_body_label = new LabelDto("", main_body_text, CLabelAlign.RIGHT_TOP);
		CStyleDto common_main_body_label = new CStyleDto(FeStyleConst.common_main_body_label);
		common_main_body_label.setLabel("正文文本样式");
		common_main_body_label.setStyleWidget(main_body_label);
		appStyle.getRoot().addChild(common_main_body_label);
	}

	public void initMainBodyRequireLabelStyle(AppStyleDto appStyle) {
		LabelDto main_body_require_label = new LabelDto()
				.setDecoration(new LabelDecorationDto().setTextStyle(main_body_require_text));
		CStyleDto common_main_body_require_label = new CStyleDto(FeStyleConst.common_main_body_require_label);
		common_main_body_require_label.setLabel("正文必填文本样式");
		common_main_body_require_label.setStyleWidget(main_body_require_label);
		appStyle.getRoot().addChild(common_main_body_require_label);
	}

	public void initBigTitleLabelStyle(AppStyleDto appStyle) {
		LabelDto big_title_label = new LabelDto("", big_title_blod_text, CLabelAlign.LEFT_CENTER);
		CStyleDto common_big_title_label = new CStyleDto(FeStyleConst.common_big_title_label);
		common_big_title_label.setLabel("标题加粗文本样式");
		common_big_title_label.setStyleWidget(big_title_label);
		appStyle.getRoot().addChild(common_big_title_label);
	}

	public void initMobileBottomTabStyle(AppStyleDto appStyle) {
		ButtonDto mobile_bottom_tab = new ButtonDto().setDecoration(new ButtonDecorationDto()
				.setButtonBorder(new BorderSideDto().setWidth(0))
				.setHoveredButtonBorder(new BorderSideDto().setWidth(0))
				.setFocusedButtonBorder(new BorderSideDto().setWidth(0))
				.setHoveredForegroundColor(new CColor(47, 88, 254, 1))
				.setFocusedForegroundColor(new CColor(47, 88, 254, 1)).setFocusedBackgroundColor(CColor.transparent())
				.setBorderRadius(BorderRadiusDto.all(RadiusDto.zero())).setBackground(CColor.transparent()));
		CStyleDto style_mobile_bottom_tab = new CStyleDto(FeStyleConst.mobile_bottom_tab);
		style_mobile_bottom_tab.setLabel("移动端底部Tab按钮样式");
		style_mobile_bottom_tab.setStyleWidget(mobile_bottom_tab);
		appStyle.getRoot().addChild(style_mobile_bottom_tab);
	}

	public void initMobileBottomTabIconStyle(AppStyleDto appStyle) {
		IconDto mobile_bottom_tab_icon = new IconDto(JDFICons.download, new IconStyleDto().setSize(24));
		CStyleDto style_mobile_bottom_tab_icon = new CStyleDto(FeStyleConst.mobile_bottom_tab_icon);
		style_mobile_bottom_tab_icon.setLabel("移动端底部Tab按钮图标样式");
		style_mobile_bottom_tab_icon.setStyleWidget(mobile_bottom_tab_icon);
		appStyle.getRoot().addChild(style_mobile_bottom_tab_icon);
	}

	public void initMobileBottomTabTextStyle(AppStyleDto appStyle) {
		CTextStyle textStyle = new CTextStyle().setFontSize(10.0)
//				.setFontFamily("PingFang SC-Regular")
				;
		LabelDto mobile_bottom_tab_text = new LabelDto("")
				.setDecoration(new LabelDecorationDto().setTextStyle(textStyle));
		CStyleDto style_mobile_bottom_tab_text = new CStyleDto(FeStyleConst.mobile_bottom_tab_text);
		style_mobile_bottom_tab_text.setLabel("移动端底部Tab按钮文本样式");
		style_mobile_bottom_tab_text.setStyleWidget(mobile_bottom_tab_text);
		appStyle.getRoot().addChild(style_mobile_bottom_tab_text);
	}

	public void initMobileWorkbrenchCollapseStyle(AppStyleDto appStyle) {
		CollapseTitleDecorationDto titleDecoration = new CollapseTitleDecorationDto().setTextStyle(
				new CTextStyle()
//				.setFontFamily("PingFang SC-Bold")
				.setFontWeight(CFontWeight.bold).setFontSize(18.0))
				.setPadding(InsetDto.all(12));
		CollapseContentDecorationDto contentDecoration = new CollapseContentDecorationDto()
//    .setBorder(BorderDto.all(Color.GRAY, 1)).setBorderRadius(BorderRadiusDto.all(new RadiusDto().setRadius(7)))
		;
		CollapseItemDecorationDto itemDecoration = new CollapseItemDecorationDto();
		itemDecoration.setBorderRadius(BorderRadiusDto.all(RadiusDto.circular(10)))
				.setBackground(CColor.fromColor(Color.WHITE)).setMargin(InsetDto.topBottom(8));

		CollapseDecorationDto collapseDecoration = new CollapseDecorationDto(titleDecoration);
//  collapseDecorationDto.setBackground(new CColor(245,246,250,1));
		collapseDecoration.setMargin(InsetDto.all(12));
		collapseDecoration.setItemDecorationDto(itemDecoration);
		collapseDecoration.setDefalutContexteDecorationDto(contentDecoration);
		CollapseDto mobile_workbrench_collapse = new CollapseDto();
		mobile_workbrench_collapse.setDecoration(collapseDecoration);

		CStyleDto style_mobile_workbrench_collapse = new CStyleDto(FeStyleConst.mobile_workbrench_collapse);
		style_mobile_workbrench_collapse.setLabel("移动端工作区间折叠面板样式");
		style_mobile_workbrench_collapse.setStyleWidget(mobile_workbrench_collapse);
		appStyle.getRoot().addChild(style_mobile_workbrench_collapse);
	}

	public void initMobilePanelBackGroundStyle(AppStyleDto appStyle) {
		SinglePanelDto mobile_panel_backgroud = SinglePanelDto.empty()
				.setDecoration(new DecorationDto()
//						.setPadding(InsetDto.leftRight(32))
						.setBackground(new CColor(250, 250, 250, 1)));
		CStyleDto style_mobile_panel_backgroud = new CStyleDto(FeStyleConst.mobile_panel_backgroud);
		style_mobile_panel_backgroud.setLabel("移动端面板背景样式");
		style_mobile_panel_backgroud.setStyleWidget(mobile_panel_backgroud);
		appStyle.getRoot().addChild(style_mobile_panel_backgroud);
	}

	public void initMobileWorkBrenchAppBoxStyle(AppStyleDto appStyle) {
		BoxDto mobile_workbrench_app_box = new BoxDto().setCrossAxisAlignment(CrossAxisAlign.center)
				.setMainAxisAlignment(MainAxisAlign.center);
		mobile_workbrench_app_box.setDecoration(new DecorationDto().setBackground(Color.white)
//    .setBorderRadius(BorderRadiusDto.all(new RadiusDto(RadiusType.circular, 6.0)))
				.setMargin(new InsetDto().setTop(4.0).setLeft(2.0).setRight(2.0)));
		CStyleDto style_mobile_workbrench_app_box = new CStyleDto(FeStyleConst.mobile_workbrench_app_box);
		style_mobile_workbrench_app_box.setLabel("移动端应用盒子样式");
		style_mobile_workbrench_app_box.setStyleWidget(mobile_workbrench_app_box);
		appStyle.getRoot().addChild(style_mobile_workbrench_app_box);
	}

	public void initMobileListViewStyle(AppStyleDto appStyle) {
		WidgetDto mobile_list_view = new WidgetDto().setDecoration(new ListViewDecorationDto().setItemCardStyle(false)
				.setItemPadding(InsetDto.all(0)).setPadding(InsetDto.all(0))
//    .setBackground(new CColor(250,250,250,1))
		);
		CStyleDto style_mobile_list_view = new CStyleDto(FeStyleConst.mobile_list_view);
		style_mobile_list_view.setLabel("移动端ListView样式");
		style_mobile_list_view.setStyleWidget(mobile_list_view);
		appStyle.getRoot().addChild(style_mobile_list_view);
	}

	public void initEditorAlignLeftStyle(AppStyleDto appStyle) {
		WidgetDto mobile_list_view = new WidgetDto()
				.setDecoration(new HintTextDecorationDto().setContentAlign(DirectionAlignmentType.start));
		CStyleDto style_mobile_list_view = new CStyleDto(FeStyleConst.edtior_align_left);
		style_mobile_list_view.setLabel("编辑器左对齐样式");
		style_mobile_list_view.setStyleWidget(mobile_list_view);
		appStyle.getRoot().addChild(style_mobile_list_view);
	}

	public void initEditorAlignCenterStyle(AppStyleDto appStyle) {
		WidgetDto mobile_list_view = new WidgetDto()
				.setDecoration(new HintTextDecorationDto().setContentAlign(DirectionAlignmentType.center));
		CStyleDto style_mobile_list_view = new CStyleDto(FeStyleConst.edtior_align_center);
		style_mobile_list_view.setLabel("编辑器居中样式");
		style_mobile_list_view.setStyleWidget(mobile_list_view);
		appStyle.getRoot().addChild(style_mobile_list_view);
	}

	public void initEditorAlignRightStyle(AppStyleDto appStyle) {
		WidgetDto mobile_list_view = new WidgetDto()
				.setDecoration(new HintTextDecorationDto().setContentAlign(DirectionAlignmentType.end));
		CStyleDto style_mobile_list_view = new CStyleDto(FeStyleConst.edtior_align_right);
		style_mobile_list_view.setLabel("编辑器右对齐样式");
		style_mobile_list_view.setStyleWidget(mobile_list_view);
		appStyle.getRoot().addChild(style_mobile_list_view);
	}

}
