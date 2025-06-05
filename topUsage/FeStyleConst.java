package fe.util.style;

import java.io.Serializable;

import cmn.anotation.FieldDeclare;
import fe.cmn.callbackWidget.popWidget.DialogDto;
import fe.cmn.callbackWidget.popWidget.DrawerDto;
import fe.cmn.editor.AmountEditorDto;
import fe.cmn.editor.CCodeEditorDto;
import fe.cmn.editor.CalendarDto;
import fe.cmn.editor.CascaderDto;
import fe.cmn.editor.CheckboxDto;
import fe.cmn.editor.CheckboxGroupDto;
import fe.cmn.editor.ColorPickerDto;
import fe.cmn.editor.DatePickerDto;
import fe.cmn.editor.DateTimePickerDto;
import fe.cmn.editor.LicensePlateEditorDto;
import fe.cmn.editor.MobileCascaderDto;
import fe.cmn.editor.PaginatorDto;
import fe.cmn.editor.PinEditorDto;
import fe.cmn.editor.RadioDto;
import fe.cmn.editor.RadioGroupDto;
import fe.cmn.editor.RatingBarDto;
import fe.cmn.editor.RichTextEditorDto;
import fe.cmn.editor.RollerListDto;
import fe.cmn.editor.SelectEditorDto;
import fe.cmn.editor.TextEditorDto;
import fe.cmn.editor.TimePickerDto;
import fe.cmn.gantt.GanttDto;
import fe.cmn.navMenu.NavMenuDto;
import fe.cmn.panel.CollapseDto;
import fe.cmn.panel.TabDto;
import fe.cmn.table.TableDto;
import fe.cmn.tree.TreeDto;
import fe.cmn.treeTable.TreeTableDto;
import fe.cmn.widget.SwitchDto;

public class FeStyleConst implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5265894171617123465L;
	//----------------------------------基础组件样式---------------------------------------------
	@FieldDeclare(label = "文本输入框默认样式", desc = "")
	public final static String common_text_eidtor = "#"+TextEditorDto.class.getSimpleName();
	@FieldDeclare(label = "下拉框默认样式", desc = "")
	public final static String common_select_eidtor = "#"+SelectEditorDto.class.getSimpleName();
	@FieldDeclare(label = "密码默认样式", desc = "")
	public final static String common_password_eidtor = "common_password_eidtor";
	@FieldDeclare(label = "整数框默认样式", desc = "")
	public final static String common_integer_eidtor = "common_integer_eidtor";
	@FieldDeclare(label = "小数框默认样式", desc = "")
	public final static String common_double_eidtor = "common_double_eidtor";
	@FieldDeclare(label = "数值输入器默认样式", desc = "")
	public final static String common_amount_eidtor = "#"+AmountEditorDto.class.getSimpleName();
	@FieldDeclare(label = "勾选框默认样式", desc = "")
	public final static String common_checkbox_eidtor = "#"+CheckboxDto.class.getSimpleName();
	@FieldDeclare(label = "勾选框组默认样式", desc = "")
	public final static String common_checkboxgroup_eidtor = "#"+CheckboxGroupDto.class.getSimpleName();
	@FieldDeclare(label = "单选框默认样式", desc = "")
	public final static String common_radio_eidtor = "#"+RadioDto.class.getSimpleName();
	@FieldDeclare(label = "单选框组默认样式", desc = "")
	public final static String common_radiogroup_eidtor = "#"+RadioGroupDto.class.getSimpleName();
	@FieldDeclare(label = "开关默认样式", desc = "")
	public final static String common_swtich_eidtor = "#"+SwitchDto.class.getSimpleName();
	@FieldDeclare(label = "代码编辑器默认样式", desc = "")
	public final static String common_ccode_eidtor = "#"+CCodeEditorDto.class.getSimpleName();
	@FieldDeclare(label = "级联选择器默认样式", desc = "")
	public final static String common_cascader = "#"+CascaderDto.class.getSimpleName();
	@FieldDeclare(label = "移动端级联选择器默认样式", desc = "")
	public final static String common_mobilecascader = "#"+MobileCascaderDto.class.getSimpleName();
	@FieldDeclare(label = "富文本编辑器默认样式", desc = "")
	public final static String common_richtext_eidtor = "#"+RichTextEditorDto.class.getSimpleName();
	@FieldDeclare(label = "日期选择器默认样式", desc = "")
	public final static String common_date_picker = "#"+DatePickerDto.class.getSimpleName();
	@FieldDeclare(label = "时间选择器默认样式", desc = "")
	public final static String common_time_picker = "#"+TimePickerDto.class.getSimpleName();
	@FieldDeclare(label = "日期时间选择器默认样式", desc = "")
	public final static String common_date_time_picker = "#"+DateTimePickerDto.class.getSimpleName();
	@FieldDeclare(label = "日历组件默认样式", desc = "")
	public final static String common_calendar = "#"+CalendarDto.class.getSimpleName();
	@FieldDeclare(label = "颜色选择器", desc = "")
	public final static String common_color_picker = "#"+ColorPickerDto.class.getSimpleName();
	@FieldDeclare(label = "图标默认样式", desc = "")
	public final static String common_icon = "common_icon";
	@FieldDeclare(label = "按钮默认样式", desc = "")
	public final static String common_toolbar_icon_btn = "common_toolbar_icon_btn";
	@FieldDeclare(label = "表格行按钮默认样式", desc = "")
	public final static String common_table_row_icon_btn = "common_table_row_icon_btn";
	//-----------表格
	@FieldDeclare(label = "表格默认样式", desc = "")
	public final static String common_table = "#"+TableDto.class.getSimpleName();
	@FieldDeclare(label = "列筛选表格默认样式", desc = "")
	public final static String common_column_filter_table = "common_column_filter_table";
	@FieldDeclare(label = "表格默认样式", desc = "")
	public final static String common_tree_table = "#"+TreeTableDto.class.getSimpleName();
	//-----------列表
	@FieldDeclare(label = "移动端列表默认样式", desc = "")
	public final static String mobile_list_view = "mobile_list_view";
	//-----------树
	@FieldDeclare(label = "树默认样式", desc = "")
	public final static String common_tree = "#"+TreeDto.class.getSimpleName();
	@FieldDeclare(label = "菜单树默认样式", desc = "")
	public final static String common_menu_tree = "common_menu_tree";
	@FieldDeclare(label = "按钮默认样式", desc = "")
	public final static String common_button = "common_button";
	@FieldDeclare(label = "标签页默认样式", desc = "")
	public final static String common_tab = "#"+TabDto.class.getSimpleName();
	@FieldDeclare(label = "弹窗默认样式", desc = "")
	public final static String common_dialog = "#"+DialogDto.class.getSimpleName();
	@FieldDeclare(label = "抽屉默认样式", desc = "")
	public final static String common_drawer = "#"+DrawerDto.class.getSimpleName();
	@FieldDeclare(label = "导航菜单默认样式", desc = "")
	public final static String common_navmenu = "#"+NavMenuDto.class.getSimpleName();
	@FieldDeclare(label = "甘特图默认样式", desc = "")
	public final static String common_gantt = "#"+GanttDto.class.getSimpleName();
	@FieldDeclare(label = "滚动选择器默认样式", desc = "")
	public final static String common_roller_list = "#"+RollerListDto.class.getSimpleName();
	@FieldDeclare(label = "评分组件默认样式", desc = "")
	public final static String common_rating_bar = "#"+RatingBarDto.class.getSimpleName();
	@FieldDeclare(label = "pin码输入器默认样式", desc = "")
	public final static String common_pin_editor = "#"+PinEditorDto.class.getSimpleName();
	@FieldDeclare(label = "分页器默认样式", desc = "")
	public final static String common_paginator = "#"+PaginatorDto.class.getSimpleName();
	@FieldDeclare(label = "车牌输入框默认样式", desc = "")
	public final static String common_license_plate_editor = "#"+LicensePlateEditorDto.class.getSimpleName();
	
	/**
	 * 菜单树内容样式
	 */
	public final static String common_white_menu_tree = "common_white_menu_tree";
	
	/**
	 * 表单盒子样式
	 */
	@FieldDeclare(label = "表单盒子默认样式", desc = "")
	public final static String common_form_box = "common_form_box";
	/**
	 * 表单属性编辑器盒子样式
	 */
	@FieldDeclare(label = "表单属性编辑器盒子默认样式", desc = "")
	public final static String common_field_box = "common_field_box";
	/**
	 * 表单属性自定义编辑器盒子样式
	 */
	public final static String common_field_custom_editor = "common_field_custom_editor";
	/**
	 * PC端表单属性标签盒子样式
	 */
	@FieldDeclare(label = "PC端表单属性标签盒子默认样式", desc = "")
	public final static String common_pc_field_label_box = "common_pc_field_label_box";
	/**
	 * PC端表单属性标签文字样式
	 */
	public final static String common_pc_field_label_span = "common_pc_field_label_span";
	
	/**
	 * PC端表单属性标签文字样式(左对齐)
	 */
	public final static String common_pc_field_label_span_align_left = "common_pc_field_label_span_align_left";

	/**
	 * 附件列表标签样式
	 */
	public final static String common_attach_label = "common_attach_label";
	/**
	 * 树顶部栏盒子样式
	 */
	public final static String common_tree_top_bar_box = "common_tree_top_bar_box";
	
	/**
	 * 表格顶部栏盒子样式
	 */
	public final static String common_table_top_bar_box = "common_table_top_bar_box";
	/**
	 * 搜索框样式
	 */
	public final static String common_search_text = "common_search_text";
	/**
	 * 滚动条样式
	 */
	public final static String common_scrollbar = "common_scrollbar";

	/**
	 * 工具栏文本按钮样式
	 */
	public final static String common_toolbar_text_btn = "common_toolbar_text_btn";
	/**
	 * 表单内文本按钮样式
	 */
	public final static String common_form_text_btn = "common_form_text_btn";
	/**
	 * 表单提交按钮样式
	 */
	@FieldDeclare(label = "表单提交按钮默认样式", desc = "")
	public final static String common_form_submit_btn = "common_form_submit_btn";
	/**
	 * 表单警告按钮样式
	 */
	public final static String common_form_warnning_btn = "common_form_warnning_btn";
	/**
	 * 表单危险按钮样式
	 */
	public final static String common_form_dangerous_btn = "common_form_dangerous_btn";
	/**
	 * 表单顶部栏样式-居左
	 */
	public final static String common_form_top_bar_left = "common_form_top_bar_left";
	/**
	 * 表单顶部栏样式-居中
	 */
	public final static String common_form_top_bar_center = "common_form_top_bar_center";
	/**
	 * 表单顶部栏样式-居右
	 */
	public final static String common_form_top_bar_right = "common_form_top_bar_right";
	/**
	 * 表单底部栏样式-居左
	 */
	public final static String common_form_bottom_bar_left = "common_form_bottom_bar_left";
	/**
	 * 表单底部栏样式-居中
	 */
	public final static String common_form_bottom_bar_center = "common_form_bottom_bar_center";
	/**
	 * 表单底部栏样式-居右
	 */
	public final static String common_form_bottom_bar_right = "common_form_bottom_bar_right";
	
	/**
	 * 大标题样式
	 */
	public final static String common_big_title_label = "common_big_title_label";
	/**
	 * 正文样式
	 */
	public final static String common_main_body_label = "common_main_body_label";
	/**
	 * 正文必填标签样式
	 */
	public final static String common_main_body_require_label = "common_main_body_require_label";
	
	/**
	 * 折叠页标题文本样式
	 */
	public final static String common_collapse= "#"+CollapseDto.class.getSimpleName();
	/**
	 * 折叠页标题插槽左样式
	 */
	public final static String common_collapse_item_slot_left= "common_collapse_item_slot_left";
	/**
	 * 折叠页标题插槽下样式
	 */
	public final static String common_collapse_item_slot_bottom= "common_collapse_item_slot_bottom";
	/**
	 * 移动端表单底部栏样式
	 */
	public final static String mobile_form_bottom_bar = "mobile_form_bottom_bar";
	
	/**
	 * 移动端底部栏样式
	 */
	public final static String mobile_bottom_tab = "mobile_bottom_tab";
	/**
	 * 移动端底部栏图标样式
	 */
	public final static String mobile_bottom_tab_icon = "mobile_bottom_tab_icon";
	/**
	 * 移动端底部栏文本样式
	 */
	public final static String mobile_bottom_tab_text = "mobile_bottom_tab_text";
	/**
	 * 移动端面板背景样式
	 */
	public final static String mobile_panel_backgroud = "mobile_panel_backgroud";
	/**
	 * 移动端工作台折叠面板样式
	 */
	public final static String mobile_workbrench_collapse = "mobile_workbrench_collapse";
	/**
	 * 移动端应用盒子样式
	 */
	public final static String mobile_workbrench_app_box = "mobile_workbrench_app_box";
	
	/**
	 * 移动端列表标题文字样式
	 */
	public final static String mobile_list_view_title = "mobile_list_view_title";
	/**
	 * 移动端列表副标题文字样式
	 */
	public final static String mobile_list_view_sub_title = "mobile_list_view_sub_title";
	/**
	 * 移动端列表侧边栏文字样式
	 */
	public final static String mobile_list_view_aside = "mobile_list_view_aside";
	/**
	 * 移动端滑动按钮样式
	 */
	public final static String mobile_list_swipe_button = "mobile_list_swipe_button";
	/**
	 * 移动端滑动按钮样式-危险操作
	 */
	public final static String mobile_list_swipe_button_dangerous = "mobile_list_swipe_button_dangerous";
	
	/**
	 * 移动端表单提交按钮样式
	 */
	public final static String mobile_form_submit_btn = "mobile_form_submit_btn";
	/**
	 * 移动端表单取消按钮样式
	 */
	public final static String mobile_form_cancel_btn = "mobile_form_cancel_btn";
	
	/**
	 * 移动端弹窗取消按钮样式
	 */
	public final static String mobile_popup_cancel_button = "mobile_popup_cancel_button";
	/**
	 * 移动端弹窗确定按钮样式
	 */
	public final static String mobile_popup_confirm_button = "mobile_popup_confirm_button";
	/**
	 * 移动端表单顶部栏样式
	 */
	public final static String mobile_form_top_bar = "mobile_form_top_bar";
	/**
	 * 移动端表单顶部栏按钮样式
	 */
	public final static String mobile_form_top_bar_button = "mobile_form_top_bar_button";
	
	/**
	 * 移动端表单盒子样式
	 */
	@FieldDeclare(label = "移动端表单盒子默认样式", desc = "")
	public final static String mobile_form_box = "mobile_form_box";
	
	/**
	 * 移动端局部表单盒子样式
	 */
	@FieldDeclare(label = "移动端局部表单盒子默认样式", desc = "")
	public final static String mobile_part_form_box = "mobile_part_form_box";
	/**
	 * 移动端表单属性盒子样式
	 */
	public final static String mobile_form_field_box = "mobile_form_field_box";
	/**
	 * 移动端表单属性盒子样式-不带边框
	 */
	public final static String mobile_form_field_box_without_border = "mobile_form_field_box_without_border";
	/**
	 * 移动端内嵌表单属性盒子样式
	 */
	public final static String mobile_embedform_field_box = "mobile_embedform_field_box";
	/**
	 * 移动端表单属性标签盒子样式
	 */
	public final static String mobile_form_field_label_box = "mobile_form_field_label_box";
	/**
	 * 下拉框左对齐样式
	 */
	public final static String edtior_align_left= "edtior_align_left";
	/**
	 * 下拉框居中样式
	 */
	public final static String edtior_align_center= "edtior_align_center";
	/**
	 * 下拉框右对齐样式
	 */
	public final static String edtior_align_right= "edtior_align_right";
	
}
