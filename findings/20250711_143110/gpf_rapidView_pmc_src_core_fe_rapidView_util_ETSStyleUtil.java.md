# Analysis for: gpf_rapidView_pmc\src\core\fe\rapidView\util\ETSStyleUtil.java

## Original File Path
`D:\TMP\CLASS_BUS_PROJECT\gpf_rapidView_pmc\src\core\fe\rapidView\util\ETSStyleUtil.java`

## Extracted Snippets & Analysis
好的，架构师的任务就是从喧嚣中提炼精华。我将严格遵循你提供的核心规则，从这段代码中识别并提取出最具教学价值的API调用模式。

以下是我的分析和提取出的代码样例：

---

### 提取出的核心代码模式样例

**注意：**
*   所有 `your_...` 形式的变量、参数或值都是占位符，请根据实际情况替换。
*   `PanelContext yourPanelContextInstance;` 和 `WidgetDto yourWidgetInstance;` 等行表示需要一个预先存在的实例，AI应学习如何调用方法，而不是如何创建这些复杂实例。
*   `// ... 其他配置` 表示可以根据需要添加更多链式调用或属性设置。

---

#### 1. **构建基本几何图形与装饰对象**

**模式：创建圆形边框半径**
如何使用 `BorderRadiusDto` 和 `RadiusDto` 构建一个圆形边框半径配置。

```java
import fe.cmn.panel.BorderRadiusDto;
import fe.cmn.panel.RadiusDto;
import fe.cmn.panel.RadiusType;

// 创建一个指定半径的圆形边框
BorderRadiusDto borderRadius = BorderRadiusDto.all(new RadiusDto(RadiusType.circular, your_double_value_radius));
```

**模式：创建纯色边框**
如何使用 `BorderDto` 构建一个指定颜色和宽度的边框。

```java
import fe.cmn.widget.decoration.BorderDto;
import java.awt.Color;

// 创建一个指定颜色和宽度的纯色边框
BorderDto border = BorderDto.all(your_color_value, your_double_value_width);
```

**模式：创建对话框装饰**
如何使用 `DialogDecorationDto` 设置对话框的边框半径。

```java
import fe.cmn.panel.BorderRadiusDto;
import fe.cmn.panel.DialogDecorationDto;
import fe.cmn.panel.RadiusDto;
import fe.cmn.panel.RadiusType;

// 创建一个对话框装饰，并设置其边框半径
DialogDecorationDto dialogDecoration = new DialogDecorationDto()
    .setBorderRadius(BorderRadiusDto.all(new RadiusDto(RadiusType.circular, your_double_value_radius)));
```

**模式：创建文本样式**
如何使用 `CTextStyle` 设置文本的字体粗细、颜色和大小。

```java
import fe.cmn.text.CFontWeight;
import fe.cmn.text.CTextStyle;
import java.awt.Color;

// 创建一个文本样式，设置字体粗细、颜色和大小
CTextStyle textStyle = new CTextStyle()
    .setFontWeight(CFontWeight.bold) // 或 CFontWeight.normal, CFontWeight.light
    .setColor(your_color_value)
    .setFontSize(your_double_value_fontSize);
```

**模式：创建内边距（Inset）**
如何使用 `InsetDto` 创建不同方向的内边距。

```java
import fe.cmn.panel.InsetDto;

// 创建一个左右各5单位的内边距
InsetDto leftRightInset = InsetDto.leftRight(your_double_value_margin);

// 创建一个上下各5单位的内边距
InsetDto topBottomInset = InsetDto.topBottom(your_double_value_margin);

// 创建一个所有方向都为20单位的内边距
InsetDto allInset = InsetDto.all(your_double_value_margin);

// 创建一个自定义的内边距，分别设置上下左右
InsetDto customInset = new InsetDto()
    .setTop(your_double_value_top)
    .setBottom(your_double_value_bottom)
    .setLeft(your_double_value_left)
    .setRight(your_double_value_right);
```

**模式：创建通用标签装饰**
如何使用 `GpfDCBasicFeUtil` 创建一个通用的标签装饰器。

```java
import gpf.dc.basic.util.GpfDCBasicFeUtil;
import fe.cmn.text.CLabelAlign;
import java.awt.Color;
import fe.cmn.widget.decoration.LabelDecorationDto; // 假设此Dto是返回类型

// 创建一个通用的标签装饰器
LabelDecorationDto labelDecoration = GpfDCBasicFeUtil.getLabelDecorationDto(
    your_double_value_fontSize,
    your_boolean_value_isBold, // 例如：true 表示粗体
    CLabelAlign.LEFT, // 或 CLabelAlign.CENTER, CLabelAlign.RIGHT
    your_color_value_fontColor
);
```

**模式：创建图标装饰**
如何使用 `IconDecorationDto` 设置图标的颜色、大小和边距。

```java
import fe.cmn.panel.InsetDto;
import fe.cmn.widget.decoration.IconDecorationDto;
import java.awt.Color;

// 创建一个图标装饰器，设置颜色、大小和左右边距
IconDecorationDto iconDecoration = (IconDecorationDto) new IconDecorationDto()
    .setIconColor(your_color_value)
    .setSize(your_double_value_size)
    .setMargin(InsetDto.leftRight(your_double_value_margin));
```

**模式：创建菜单项装饰**
如何使用 `MenuItemDecorationDto` 设置菜单项的文本样式和图标装饰。

```java
import fe.cmn.text.CFontWeight;
import fe.cmn.text.CTextStyle;
import fe.cmn.widget.decoration.IconDecorationDto;
import fe.cmn.menu.MenuItemDecorationDto;
import java.awt.Color;

// 假设已存在或创建图标装饰 yourIconDecorationInstance
IconDecorationDto yourIconDecorationInstance = new IconDecorationDto().setIconColor(Color.BLUE); // 示例

// 创建一个菜单项装饰器
MenuItemDecorationDto menuItemDecoration = new MenuItemDecorationDto()
    .setTextStyle(new CTextStyle()
        .setFontWeight(CFontWeight.bold) // 例如：CFontWeight.bold
        .setColor(your_color_value)
        .setFontSize(your_double_value_fontSize)
    )
    .setIconDecoration(yourIconDecorationInstance)
    .setHeight(your_double_value_height);
```

**模式：创建选择编辑器菜单装饰**
如何使用 `SelectEditorMenuDecorationDto` 设置菜单项的装饰和高亮装饰。

```java
import fe.cmn.menu.MenuItemDecorationDto;
import fe.cmn.menu.SelectEditorMenuDecorationDto;

// 假设已存在或创建菜单项装饰 yourMenuItemDecorationInstance, yourHighlightMenuItemDecorationInstance
MenuItemDecorationDto yourMenuItemDecorationInstance = new MenuItemDecorationDto(); // 示例
MenuItemDecorationDto yourHighlightMenuItemDecorationInstance = new MenuItemDecorationDto(); // 示例

// 创建一个选择编辑器菜单装饰器
SelectEditorMenuDecorationDto selectEditorMenuDecoration = new SelectEditorMenuDecorationDto()
    .setMenuItemDecoration(yourMenuItemDecorationInstance)
    .setHighlightMenuItemDecoration(yourHighlightMenuItemDecorationInstance);
```

#### 2. **对话框与面板交互**

**模式：显示一个弹出对话框**
如何使用 `PopDialog.show` 方法显示一个通用弹出对话框。

```java
import fe.cmn.panel.PanelContext;
import fe.cmn.panel.PanelDto;
import fe.cmn.panel.DialogDecorationDto;
import fe.cmn.panel.ability.PopDialog;

// 假设 yourPanelContextInstance 和 yourPanelDtoInstance 已存在
PanelContext yourPanelContextInstance;
PanelDto yourPanelDtoInstance;
DialogDecorationDto yourDialogDecorationInstance = new DialogDecorationDto(); // 示例

// 显示一个弹出对话框
PopDialog.show(
    yourPanelContextInstance,
    "此处填写对话框标题",
    yourPanelDtoInstance,
    null, // 通常为null，或指定一个关闭回调
    your_boolean_value_modal, // 例如：true 表示模态对话框
    yourDialogDecorationInstance // 对话框装饰
);
```

**模式：显示确认对话框**
如何使用 `PopupPanel.showConfirm` 方法显示一个确认对话框并获取用户选择。

```java
import fe.cmn.panel.PanelContext;
import fe.cmn.panel.DialogDecorationDto;
import fe.cmn.panel.SizeDto;
import fe.util.component.PopupPanel;
import fe.cmn.panel.ability.PopDialog;

// 假设 yourPanelContextInstance 已存在
PanelContext yourPanelContextInstance;
DialogDecorationDto yourDialogDecorationInstance = new DialogDecorationDto(); // 示例

// 显示一个确认对话框
boolean confirmed = PopupPanel.showConfirm(
    yourPanelContextInstance,
    "此处填写确认标题",
    "此处填写确认消息",
    SizeDto.all(your_double_value_width, your_double_value_height), // 对话框尺寸
    your_boolean_value_modal, // 例如：true
    yourDialogDecorationInstance, // 对话框装饰
    PopDialog.defaultBarrierDismissible // 点击外部区域是否可关闭
);
// confirmed 将是 true 如果用户确认，否则为 false
```

**模式：显示无按钮输入对话框**
如何使用 `PopDialog.showInputwithOutButton` 方法显示一个没有默认按钮的输入对话框。

```java
import fe.cmn.panel.PanelContext;
import fe.cmn.panel.PanelDto;
import fe.cmn.panel.PanelValue;
import fe.cmn.panel.DialogDecorationDto;
import fe.cmn.panel.ability.PopDialog;

// 假设 yourPanelContextInstance 和 yourPanelDtoInstance 已存在
PanelContext yourPanelContextInstance;
PanelDto yourPanelDtoInstance;
DialogDecorationDto yourDialogDecorationInstance = new DialogDecorationDto(); // 示例

// 显示一个无按钮的输入对话框
PanelValue panelValue = PopDialog.showInputwithOutButton(
    yourPanelContextInstance,
    "此处填写标题",
    yourPanelDtoInstance,
    your_boolean_value_onlyGuiValue, // 例如：true
    your_long_value_timeoutMillis, // 例如：60000 (1分钟)
    your_boolean_value_showCloseButton, // 例如：true
    yourDialogDecorationInstance, // 对话框装饰
    PopDialog.defaultBarrierDismissible // 点击外部区域是否可关闭
);
// panelValue 包含用户输入的数据
```

**模式：显示自定义按钮的输入对话框**
如何使用 `PopDialog.showInputWithCustomedButton` 方法显示一个带自定义按钮的输入对话框。

```java
import fe.cmn.panel.PanelContext;
import fe.cmn.panel.PanelDto;
import fe.cmn.panel.EscapeButtonDto;
import fe.cmn.panel.PanelValue;
import fe.cmn.panel.DialogDecorationDto;
import fe.cmn.panel.ability.PopDialog;

// 假设 yourPanelContextInstance 和 yourPanelDtoInstance 已存在
PanelContext yourPanelContextInstance;
PanelDto yourPanelDtoInstance;
EscapeButtonDto yourOkButtonInstance = new EscapeButtonDto().setText("自定义确认").setWidgetId("custom_ok"); // 示例
DialogDecorationDto yourDialogDecorationInstance = new DialogDecorationDto(); // 示例

// 显示一个带自定义按钮的输入对话框
PanelValue panelValue = PopDialog.showInputWithCustomedButton(
    yourPanelContextInstance,
    "此处填写标题",
    yourPanelDtoInstance,
    yourOkButtonInstance, // 自定义确认按钮
    your_boolean_value_onlyGuiValue, // 例如：true
    your_long_value_timeoutMillis, // 例如：60000 (1分钟)
    your_boolean_value_showCloseButton, // 例如：true
    yourDialogDecorationInstance, // 对话框装饰
    PopDialog.defaultBarrierDismissible // 点击外部区域是否可关闭
);
// panelValue 包含用户输入的数据
```

**模式：构建弹出对话框实例**
如何使用 `PopDialog.build` 方法构建一个 PopDialog 实例，用于后续配置和显示。

```java
import fe.cmn.panel.PanelDto;
import fe.cmn.panel.EscapeButtonDto;
import fe.cmn.panel.DialogDecorationDto;
import fe.cmn.panel.ability.PopDialog;

// 假设 yourPanelDtoInstance 已存在
PanelDto yourPanelDtoInstance;
EscapeButtonDto yourOkButtonInstance = new EscapeButtonDto().setText("确认"); // 示例
DialogDecorationDto yourDialogDecorationInstance = new DialogDecorationDto(); // 示例

// 构建一个 PopDialog 实例
PopDialog dialog = PopDialog.build(
    "此处填写对话框标题",
    yourPanelDtoInstance,
    yourOkButtonInstance, // 确认按钮
    null, // 取消按钮，可为 null
    your_boolean_value_showCloseButton // 是否显示关闭按钮
);

// 设置对话框装饰
dialog.setDecoration(yourDialogDecorationInstance);

// ... 其他配置，例如：
dialog.setOnlyGuiValue(your_boolean_value_onlyGuiValue);
dialog.setWaitForClose(your_long_value_timeoutMillis);
dialog.setTimeout(dialog.getWaitForClose()); // 通常与 WaitForClose 相同
```

#### 3. **UI布局与组件**

**模式：创建垂直布局盒子**
如何使用 `BoxDto.vbar` 创建一个垂直布局的容器。

```java
import fe.cmn.widget.BoxDto;
import fe.cmn.widget.WidgetDto;
import fe.cmn.panel.DecorationDto;
import fe.cmn.panel.BorderRadiusDto;
import fe.cmn.panel.RadiusDto;
import fe.cmn.panel.RadiusType;
import java.awt.Color;

// 假设 yourWidget1Instance, yourWidget2Instance 已存在
WidgetDto yourWidget1Instance;
WidgetDto yourWidget2Instance;

// 创建一个垂直布局盒子
BoxDto verticalBox = BoxDto.vbar(
    yourWidget1Instance,
    yourWidget2Instance // 可以添加更多 WidgetDto 作为子元素
);

// 为盒子添加背景颜色和边框半径装饰
verticalBox.setDecoration(DecorationDto.background(your_color_value_backgroundColor)
    .setBorderRadius(BorderRadiusDto.all(new RadiusDto(RadiusType.circular, your_double_value_radius)))
);
```

**模式：创建水平布局盒子**
如何使用 `BoxDto.hbar` 创建一个水平布局的容器。

```java
import fe.cmn.widget.BoxDto;
import fe.cmn.widget.WidgetDto;
import fe.cmn.panel.MainAxisAlign;
import fe.cmn.panel.CrossAxisAlign;

// 假设 yourWidget1Instance, yourWidget2Instance 已存在
WidgetDto yourWidget1Instance;
WidgetDto yourWidget2Instance;

// 创建一个水平布局盒子
BoxDto horizontalBox = BoxDto.hbar(
    yourWidget1Instance,
    BoxDto.expander(), // 填充剩余空间
    yourWidget2Instance
);

// 设置主轴和交叉轴对齐方式（可选）
horizontalBox.setMainAxisAlignment(MainAxisAlign.center) // 例如：MainAxisAlign.start, MainAxisAlign.end
    .setCrossAxisAlignment(CrossAxisAlign.center); // 例如：CrossAxisAlign.start, CrossAxisAlign.end
```

**模式：创建占位伸缩组件**
如何使用 `BoxDto.expander` 创建一个在布局中占据剩余空间的组件。

```java
import fe.cmn.widget.BoxDto;

// 创建一个占位伸缩组件
WidgetDto expander = BoxDto.expander();
// 此组件通常用于 BoxDto.hbar 或 BoxDto.vbar 中，用于填充空间
```

**模式：创建文本标签**
如何使用 `LabelDto` 创建一个带有文本和装饰的标签。

```java
import fe.cmn.widget.LabelDto;
import fe.cmn.panel.DecorationDto;
import fe.cmn.text.CTextStyle;
import fe.cmn.text.CFontWeight;
import fe.cmn.text.CLabelAlign;
import java.awt.Color;
import fe.cmn.panel.InsetDto;

// 创建一个带有文本的标签
LabelDto label = new LabelDto("此处填写标签文本");

// 为标签设置装饰
label.setDecoration(new DecorationDto()
    .setMargin(new InsetDto().setRight(your_double_value_marginRight))
    .setTextStyle(new CTextStyle()
        .setColor(your_color_value_textColor)
        .setFontSize(your_double_value_fontSize)
        .setFontWeight(CFontWeight.bold) // 或 CFontWeight.normal
    )
);

// 或者使用通用标签装饰
// LabelDecorationDto yourLabelDecorationInstance; // 假设已创建
// label.setDecoration(yourLabelDecorationInstance);
```

**模式：创建通用装饰对象**
如何使用 `new DecorationDto()` 构建一个通用的装饰对象并设置其属性。

```java
import fe.cmn.panel.DecorationDto;
import fe.cmn.panel.BorderDto;
import fe.cmn.panel.BorderRadiusDto;
import fe.cmn.panel.RadiusDto;
import fe.cmn.panel.RadiusType;
import fe.cmn.panel.InsetDto;
import java.awt.Color;

// 创建一个装饰对象
DecorationDto decoration = new DecorationDto()
    .setBackground(your_color_value_backgroundColor) // 设置背景颜色
    .setBorder(BorderDto.all(your_color_value_borderColor, your_double_value_borderWidth)) // 设置边框
    .setBorderRadius(BorderRadiusDto.all(new RadiusDto(RadiusType.circular, your_double_value_radius))) // 设置边框半径
    .setPadding(your_double_value_padding) // 设置内边距 (所有方向相同)
    .setPadding(InsetDto.all(your_double_value_padding)); // 设置内边距 (更精细控制)
// ... 其他配置
```

**模式：创建输入框编辑器**
如何使用 `TextEditorDto` 构建一个文本输入框的配置。

```java
import fe.cmn.editor.RegularExpType;
import fe.cmn.editor.TextEditorDto;

// 创建一个文本编辑器配置对象
TextEditorDto editor = new TextEditorDto()
    .setWidgetId("your_widget_id") // 绑定到表单字段的ID
    .setValue("此处填写默认值") // 设置默认显示的值
    .setObscureText(your_boolean_value_obscureText) // 例如：true 用于密码输入
    .setStyleName("your_css_style_name") // 应用CSS样式
    .setRegularExpType(RegularExpType.changeFilter) // 或 RegularExpType.unfocus, RegularExpType.none
    .setRegularExp("此处填写正则表达式") // 例如："[0-9]+"
    .setErrorText("此处填写错误提示信息") // 验证失败时的错误信息
    .setWritable(your_boolean_value_isWritable) // 是否可编辑
    .setMaxRenderLines(your_integer_value_maxLines) // 最大显示行数
    .setMinRenderLines(your_integer_value_minLines) // 最小显示行数
    .setExpandInBox(your_boolean_value_expandInBox) // 是否在容器中扩展
    .setPreferHeight(your_double_value_height); // 偏好高度
// ... 其他配置
```

#### 4. **国际化与表单管理**

**模式：获取国际化字符串**
如何使用 `IFeI18nPlugin` 获取指定键的国际化文本。

```java
import cell.fe.cmn.IFeI18nPlugin;
import fe.cmn.panel.PanelContext;

// 假设 yourPanelContextInstance 已存在
PanelContext yourPanelContextInstance;

// 获取国际化字符串
String i18nString = IFeI18nPlugin.get().getI18nString(yourPanelContextInstance, "your_i18n_key");
```

**模式：获取表单字段代码**
如何使用 `IFormMgr` 获取指定字段名的代码（通常用于绑定UI组件）。

```java
import cell.gpf.adur.data.IFormMgr;

// 获取表单字段对应的代码
String fieldCode = IFormMgr.get().getFieldCode("此处填写字段名");
```

#### 5. **时间与日期处理**

**模式：格式化持续时间字符串**
如何使用 Hutool `StrUtil` 将小时数转换为 "xh ym" 格式的持续时间字符串。

```java
import cn.hutool.core.util.StrUtil;

// 将小时数（可能带小数）格式化为 "xh ym" 形式的持续时间字符串
Double your_duration_in_hours = your_double_value_duration; // 例如：1.5 表示1小时30分钟
String durationStr = StrUtil.format("{}h {}m", your_duration_in_hours.intValue(), (int) ((your_duration_in_hours - your_duration_in_hours.intValue()) * 60));
// 例如：如果 your_duration_in_hours = 1.5，则 durationStr = "1h 30m"
```

**模式：简化日期字符串**
如何使用 Hutool `DateUtil` 将标准日期字符串简化为 "Day.MonthDate" 格式。

```java
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Week;
import java.time.format.TextStyle; // 注意：这是 java.time 包下的 TextStyle

// 将标准日期字符串简化为 "Day.MonthDate" 格式
String your_standard_date_string = "此处填写标准日期字符串"; // 例如："2023-10-26"
DateTime dateTime = DateUtil.parse(your_standard_date_string);
Week weekEnum = DateUtil.dayOfWeekEnum(dateTime);
String monthShortName = DateUtil.monthEnum(dateTime).getDisplayName(TextStyle.SHORT); // 使用 java.time.format.TextStyle
int dayOfMonth = DateUtil.dayOfMonth(dateTime);

// 假设 ALIASES 数组已定义: private static final String[] ALIASES = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
String[] ALIASES = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; // 仅为样例完整性包含，实际应从类获取

String simpleDateStr = ALIASES[weekEnum.getValue() - 1] + "." + monthShortName + dayOfMonth;
// 例如：如果 your_standard_date_string = "2023-10-26"，则 simpleDateStr = "Thu.Oct26"
```

**模式：将日期字符串转换为 DateDto**
如何使用 Hutool `DateUtil` 将日期字符串解析后转换为 `DateDto`。

```java
import cn.hutool.core.date.DateUtil;
import fe.cmn.data.DateDto;
import java.util.Calendar;

// 将日期字符串转换为 DateDto
String your_date_string = "此处填写日期字符串"; // 例如："2023-10-26 10:30:00"

DateDto dateDto = null;
try {
    dateDto = new DateDto(); // 实际通过 convertDateDto(long) 方法创建
    // 模拟内部调用逻辑：
    // DateTime dateTime = DateUtil.parseDate(your_date_string);
    // Calendar calendar = Calendar.getInstance();
    // calendar.setTimeInMillis(dateTime.getTime());
    // dateDto = new DateDto(
    //     calendar.get(Calendar.YEAR),
    //     calendar.get(Calendar.MONTH) + 1,
    //     calendar.get(Calendar.DAY_OF_MONTH),
    //     calendar.get(Calendar.HOUR_OF_DAY),
    //     calendar.get(Calendar.MINUTE),
    //     calendar.get(Calendar.SECOND)
    // );
} catch (Exception ignore) {
    // 转换失败返回 null
}
// dateDto 将包含转换后的日期组件
```

**模式：将时间戳转换为 DateDto**
如何将毫秒时间戳转换为 `DateDto` 对象。

```java
import fe.cmn.data.DateDto;
import java.util.Calendar;

// 将毫秒时间戳转换为 DateDto
long your_time_in_millis = your_long_value_timestamp; // 例如：System.currentTimeMillis()

Calendar calendar = Calendar.getInstance();
calendar.setTimeInMillis(your_time_in_millis);
DateDto dateDto = new DateDto(
    calendar.get(Calendar.YEAR),
    calendar.get(Calendar.MONTH) + 1, // Calendar.MONTH 是 0-11，DateDto 是 1-12
    calendar.get(Calendar.DAY_OF_MONTH),
    calendar.get(Calendar.HOUR_OF_DAY),
    calendar.get(Calendar.MINUTE),
    calendar.get(Calendar.SECOND)
);
// dateDto 包含时间戳分解出的年、月、日、时、分、秒
```

**模式：检查对象是否为空**
如何使用 `NullPojo.isNull` 检查一个对象是否被视为“空”对象。

```java
import fe.cmn.data.NullPojo;

// 检查一个对象是否为空（框架定义的空对象）
Object your_object_to_check = your_date_dto_instance; // 例如：一个 DateDto 实例
boolean isNull = NullPojo.isNull(your_object_to_check);
// isNull 为 true 如果对象被认为是空，否则为 false
```

**模式：将 DateDto 转换为时间戳**
如何将 `DateDto` 对象转换为毫秒时间戳。

```java
import fe.cmn.data.DateDto;
import fe.cmn.data.NullPojo;
import cn.hutool.core.date.DateTime;
import java.util.Calendar;

// 假设 yourDateDtoInstance 已存在
DateDto yourDateDtoInstance = new DateDto(your_year, your_month, your_day, your_hour, your_minute, your_second); // 示例

long timeInMillis;
if (NullPojo.isNull(yourDateDtoInstance)) {
    timeInMillis = DateTime.now().getTime(); // 如果是空对象，则返回当前时间戳
} else {
    Calendar c = Calendar.getInstance();
    c.set(Calendar.YEAR, yourDateDtoInstance.getYear());
    c.set(Calendar.MONTH, yourDateDtoInstance.getMonth() - 1); // Calendar.MONTH 是 0-11
    c.set(Calendar.DAY_OF_MONTH, yourDateDtoInstance.getDate());
    c.set(Calendar.HOUR_OF_DAY, yourDateDtoInstance.getHour());
    c.set(Calendar.MINUTE, yourDateDtoInstance.getMinute());
    c.set(Calendar.SECOND, yourDateDtoInstance.getSecond());
    // 如果 DateDto 包含毫秒，可设置：
    // c.set(Calendar.MILLISECOND, yourDateDtoInstance.getMillisecond());
    timeInMillis = c.getTimeInMillis();
}
// timeInMillis 包含转换后的毫秒时间戳
```

#### 6. **菜单装饰**

**模式：获取默认菜单装饰**
如何使用 `MenuDecorationDto` 创建一个带有图标和高亮样式的菜单装饰器。

```java
import fe.cmn.menu.MenuDecorationDto;
import fe.cmn.menu.MenuItemDecorationDto;
import fe.cmn.panel.InsetDto;
import fe.cmn.text.CTextStyle;
import fe.cmn.widget.decoration.IconDecorationDto;
import java.awt.Color;

// 创建一个带有左右边距的默认菜单装饰器
double your_double_value_lrMargin = your_double_value_margin; // 例如：10.0

IconDecorationDto iconDecoration = new IconDecorationDto()
    .setIconColor(your_color_value_iconColor)
    .setSize(your_double_value_iconSize)
    .setMargin(new InsetDto().setLeft(your_double_value_iconMarginLeft));

MenuDecorationDto menuDecoration = new MenuDecorationDto()
    .setBackgroundColor(your_color_value_backgroundColor)
    .setMargin(InsetDto.all(your_double_value_lrMargin).setBottom(your_double_value_bottomMargin))
    .setMenuItemDecoration(
        new MenuItemDecorationDto()
            .setHeight(your_double_value_menuItemHeight)
    )
    .setHighlightMenuItemDecoration(new MenuItemDecorationDto()
        .setIconDecoration(iconDecoration)
        .setTextStyle(new CTextStyle().setColor(your_color_value_highlightTextColor))
    );
```