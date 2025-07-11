# Analysis for: gpf_dc_scgc\src\core\cell\fe\scgc\pages\home\ParPage.java

## Original File Path
`D:\TMP\CLASS_BUS_PROJECT\gpf_dc_scgc\src\core\cell\fe\scgc\pages\home\ParPage.java`

## Extracted Snippets & Analysis
好的，作为一名资深的软件架构师，我将严格遵循您提供的[核心规则]，从这段复杂的代码中，提炼出简洁、优雅且极具教学价值的核心API使用模式。

我将重点关注：
1.  **可执行的API调用**，忽略声明、注释。
2.  **绝对可靠性**：只提取独立、上下文自足的片段，避免依赖私有或不明确的实例状态。
3.  **去业务化**：所有具体业务值将被通用占位符取代。
4.  **原子性**：每个样例只关注一个核心任务。

---

以下是我从您提供的代码中提取的高质量API使用样例：

```java
// 1. 创建垂直布局盒子 (BoxDto)
BoxDto your_vertical_box = BoxDto.vbar();

// 2. 创建水平布局盒子 (BoxDto)
BoxDto your_horizontal_box = BoxDto.hbar(your_child_widget_1, your_child_widget_2);

// 3. 创建图标 (IconDto) 并设置装饰 (IconDecorationDto) 和颜色 (CColor)
IconDto your_icon_dto = new IconDto("path/to/icon.png")
    .setDecoration(new IconDecorationDto().setIconColor(new CColor(red, green, blue, alpha)));

// 4. 设置Widget的下外边距 (InsetDto)
your_widget_instance.setMargin(new InsetDto().setBottom(your_margin_value));

// 5. 设置Widget的装饰 (DecorationDto) 及边框 (BorderDto) 和边框边 (BorderSideDto)
your_widget_instance.setDecoration(new DecorationDto()
    .setBorder(new BorderDto().setTop(new BorderSideDto(new CColor(red, green, blue, alpha), your_border_width))));

// 6. 设置Widget的内边距 (Padding)
your_widget_instance.setPadding(your_padding_value); // 单一值，所有方向相同
your_widget_instance.setPadding(new InsetDto().setLeft(your_left_padding_value)); // 指定方向

// 7. 创建Scaffold页面 (ScaffoldDto)
ScaffoldDto your_scaffold_page = new ScaffoldDto();

// 8. 设置Scaffold内容
your_scaffold_page.setContent(your_main_widget_instance);

// 9. 为Scaffold添加事件订阅 (EventSubscriberDto)
your_scaffold_page.addSubscribeEvent(new EventSubscriberDto(YourEventClass.class)
    .setService(your_service_instance) // 假设 your_service_instance 是 ServiceIntf 类型
    .setCommand("your_command_string"));

// 10. 设置Scaffold的全局Key
your_scaffold_page.setPanelGlobalKey("your_global_key_string");

// 11. 获取应用设置 (ApplicationSetting)
ApplicationSetting your_application_setting = AppCacheUtil.getSetting(your_panel_context_instance);

// 12. 创建图片 (ImageDto) 并设置字节数据
ImageDto your_image_dto = new ImageDto().setBytes(your_image_byte_array);

// 13. 创建图片 (ImageDto) 并设置源路径
ImageDto your_image_from_src = new ImageDto("path/to/image.png");

// 14. 设置Widget的推荐大小 (SizeDto)
your_widget_instance.setPreferSize(new SizeDto(your_width_double, your_height_double));
your_widget_instance.setPreferSize(SizeDto.all(your_size_double, your_size_double)); // 宽高相同

// 15. 设置图片装饰 (ImageDecorationDto) 包括适配类型 (FitType)、形状 (ShapeType)、裁剪 (ClipType)、背景色和边框半径 (BorderRadiusDto)
your_image_dto.setDecoration(new ImageDecorationDto()
    .setFit(FitType.none)
    .setShape(ShapeType.circle)
    .setClip(ClipType.antiAlias)
    .setBackground(new CColor(red, green, blue, alpha))
    .setBorderRadius(BorderRadiusDto.all(new RadiusDto(RadiusType.circular, your_radius_value))));

// 16. 创建标签 (LabelDto) 并设置Widget ID
LabelDto your_label_dto = new LabelDto("您的文本内容").setWidgetId("your_widget_id_string");

// 17. 设置标签装饰 (LabelDecorationDto) 及文本样式 (CTextStyle)
your_label_dto.setDecoration(new LabelDecorationDto()
    .setTextStyle(new CTextStyle()
        .setFontSize(your_font_size_double)
        .setFontWeight(CFontWeight.bold)));

// 18. 通过IDaoService获取Dao实例
try (IDao your_dao_instance = IDaoService.get().newDao()) {
    // 使用your_dao_instance进行数据库操作
    // CDoUser user = (CDoUser) your_dao_instance.queryDo(CDoUser.class.getName(), Cnd.where("Code", "=", "your_user_code"));
    // 这里提取的是获取DAO的模式，具体查询操作可能过于业务化，或Cnd.where的内部结构无法访问。
}

// 19. 创建手势检测器 (GestureDetectorDto) 并设置点击监听器 (OnClickListener)
GestureDetectorDto your_gesture_detector = new GestureDetectorDto()
    .setOnClick(your_on_click_listener_instance); // your_on_click_listener_instance 可能是 newListener 的结果

// 20. 创建Button装饰 (ButtonDecorationDto) 并设置图标样式 (IconStyleDto)、边框、焦点和悬停状态样式
ButtonDecorationDto your_button_decoration = (ButtonDecorationDto) new ButtonDecorationDto()
    .setIcon(new IconStyleDto(new CColor(red, green, blue, alpha)))
    .setButtonBorder(new BorderSideDto().setColor(CColor.transparent()))
    .setFocusedBackgroundColor(CColor.transparent())
    .setFocusedForegroundColor(CColor.fromColor(java.awt.Color.BLACK))
    .setFocusedButtonBorder(new BorderSideDto().setColor(CColor.transparent()))
    .setHoverBackgroundColor(CColor.transparent())
    .setHoveredForegroundColor(CColor.fromColor(java.awt.Color.BLACK))
    .setHoveredButtonBorder(new BorderSideDto().setColor(CColor.transparent()))
    .setTextStyle(new CTextStyle().setColor(java.awt.Color.BLACK).setFontSize(your_font_size_double))
    .setMargin(your_margin_value)
    .setPadding(your_padding_value);

// 21. 创建ListenerDto实例 (通常由框架方法创建)
// 注意: newListener 是 Page 内部方法，这里假设它返回 ListenerDto 实例
ListenerDto your_listener = newListener(ListenerDto.class, your_builder_service_instance, "your_command_string", your_boolean_flag, your_data_object);
// 或者更通用地:
// ListenerDto your_listener = your_framework_api.createListener("your_command_string", your_boolean_flag, your_data_object);

// 22. 创建按钮 (ButtonDto) 并设置图标、点击监听器和装饰
ButtonDto your_button_dto = new ButtonDto()
    .setIcon("path/to/icon.png") // Icon source
    .setOnClick(your_listener)
    .setDecoration(your_button_decoration);

// 23. 创建网格布局 (GridDto) 并设置列和行尺寸 (GridSize)
GridDto your_grid_dto = new GridDto()
    .setColumns(GridSize.flex(your_flex_value_1, your_flex_value_2, your_flex_value_3))
    .setRows(GridSize.flex(your_flex_value_4, your_flex_value_5));

// 24. 创建定时器 (TimerDto)
TimerDto your_timer_dto = new TimerDto("your_command_string", your_interval_ms, your_listener_instance);

// 25. 设置标签的文本对齐方式和背景色
your_label_dto.setDecoration(new LabelDecorationDto()
    .setAlign(CLabelAlign.CENTER)
    .setTextStyle(your_text_style_instance)
    .setBackground(CColor.fromColor(java.awt.Color.RED))
    .setShape(ShapeType.circle));

// 26. 设置Widget的定时器
your_widget_instance.setTimers(your_timer_dto);

// 27. 创建网格块 (GridBlock)
GridBlock your_grid_block = GridBlock.wrap(your_col_start, your_col_end, your_row_start, your_row_end, your_widget_to_wrap);

// 28. 设置网格块列表
your_grid_dto.setBlocks(new ArrayList<>(java.util.Arrays.asList(your_grid_block_1, your_grid_block_2)));

// 29. 将数据写入会话存储 (SessionStorageUtil)
SessionStorageUtil.write(your_channel_instance, "your_key_string", your_value_object);

// 30. 从会话存储读取数据 (SessionStorageUtil)
YourClass your_read_value = SessionStorageUtil.read(your_channel_instance, "your_key_string", YourClass.class);

// 31. 获取PDF运行时管理器并查询待办数量
long todo_count = IPDFRuntimeMgr.get().lazyQueryTodoCount("your_current_user_id", your_union_query_instance, your_boolean_flag);

// 32. 重建子Widget (RebuildChild)
RebuildChild.rebuild(your_panel_context_instance, your_widget_instance_to_rebuild);
RebuildChild.rebuild(your_panel_context_instance, your_widget_1, your_widget_2); // 重建多个Widget

// 33. 构建Container (ContainerDto) 并设置子Widget
ContainerDto your_container_dto = new ContainerDto().setChild(your_child_widget);

// 34. 触发事件 (FireEvent)
FireEvent.fire(your_panel_context_instance, your_event_dto_instance);

// 35. 弹出菜单 (PopMenu)
PopMenu your_pop_menu = new PopMenu();
your_pop_menu.attachShow(your_panel_context_instance, your_menu_dto_instance, "your_source_widget_id_string");

// 36. 创建菜单项 (MenuItemDto)
MenuItemDto your_menu_item = new MenuItemDto()
    .setLabel("您的菜单项标签")
    .setIcon("path/to/menu/icon.png") // Icon source
    .setOnClick(your_listener_instance);

// 37. 创建菜单 (MenuDto) 并设置菜单项列表
MenuDto your_menu_dto = new MenuDto();
your_menu_dto.setMenuItems(new LinkedList<>(java.util.Arrays.asList(your_menu_item_1, your_menu_item_2)));

// 38. 弹出对话框 (PopDialog)
PopDialog.show(your_panel_context_instance, "您的对话框标题", your_panel_dto_instance);

// 39. 跳转或弹出页面 (JumpToOrPopupPage)
JumpToOrPopupPage.show(your_panel_context_instance, "your_target_path", your_map_of_parameters);

// 40. 查询Widget字段 (QueryWidgetField)
Object field_value = QueryWidgetField.queryOne(your_panel_context_instance, "your_widget_id", "your_field_name");
long numeric_value = Long.valueOf((String) QueryWidgetField.queryOne(your_panel_context_instance, "your_widget_id", "your_field_name"));

// 41. 重建Widget并更新字段值 (RebuildWidget)
RebuildWidget.rebuild(your_panel_context_instance, "your_widget_id", "your_field_name", "your_new_value");

// 42. 用户登出 (AppLoginPanel)
AppLoginPanel.logout(your_panel_context_instance, YourLoginPanelClass.class, "your_app_session_key");

// 43. 获取并设置基础组件参数 (BaseWidgetParam)
your_component_instance.setWidgetParam(new BaseWidgetParam());

// 44. 获取并设置表格参数 (TableParam)
your_table_component_instance.setWidgetParam(new TableParam().defaultParam());

// 45. 获取Action管理器并查询Action
try (IDao dao = IDaoService.get().newDao()) {
    Action your_action_instance = IActionMgr.get().queryActionByCode(dao, "your_model_id", "your_model_code");
}

// 46. 创建PDF运行时上下文 (IDCRuntimeContext)
IDCRuntimeContext your_runtime_context = IPDFRuntimeMgr.get().newRuntimeContext();

// 47. 设置运行时上下文操作者和DAO
your_runtime_context.setOperator("your_operator_id");
your_runtime_context.setDao(your_dao_instance);

// 48. 准备基础前端动作参数 (BaseFeActionParameter)
BaseFeActionParameter.prepareFeActionParameter(your_runtime_context, your_panel_context_instance, your_listener_dto_instance, your_page_instance);

// 49. 执行Action (IActionMgr)
WidgetDto result_widget = (WidgetDto) IActionMgr.get().executeAction(your_action_instance, your_runtime_context);

// 50. 转换PanelContext (ConvertPanelContext)
PanelContext converted_context = ConvertPanelContext.convert(your_panel_context_instance, "your_panel_id_string");

// 51. 设置Widget的展开行为 (ExpandInBox)
your_box_dto.setExpandInBox(true);

// 52. 设置Box的主轴对齐方式 (MainAxisAlignment)
your_box_dto.setMainAxisAlignment(MainAxisAlign.center);

// 53. 获取国际化字符串 (getI18nString)
String i18n_text = getI18nString(your_panel_context_instance, "your_i18n_key_string");

// 54. 设置Widget的提示信息 (ToolTip)
your_widget_instance.setToolTip("您的提示信息");

// 55. 设置标签的最大宽度和最大行数 (LabelDecorationDto)
your_label_dto.setDecoration(new LabelDecorationDto()
    .setMaxWidth(your_max_width_double)
    .setMaxLines(your_max_lines_integer));

// 56. 设置Image装饰的过滤质量 (FilterQualityType)
your_image_dto.setDecoration(new ImageDecorationDto()
    .setFilterQuality(FilterQualityType.medium));
```