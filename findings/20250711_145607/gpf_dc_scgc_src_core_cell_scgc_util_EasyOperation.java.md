# Analysis for: gpf_dc_scgc\src\core\cell\scgc\util\EasyOperation.java

## Original File Path
`D:\TMP\CLASS_BUS_PROJECT\gpf_dc_scgc\src\core\cell\scgc\util\EasyOperation.java`

## Extracted Snippets & Analysis
好的，作为一名资深软件架构师，我将严格遵循您的核心规则，从提供的代码中提炼出高质量、原子化、去业务化的API使用模式。

核心分析点：

1.  **忽略声明性代码**：`package`, `import`, 类定义，成员变量，注解，`@Override` 等。
2.  **可靠性 (Rule 2)**：
    *   `EasyOperation.get()` 是静态方法，可靠。但它返回的实例方法如果依赖复杂上下文（如 `PanelContext` 或 `List<Form>`），则其本身或内部逻辑可能不可靠。
    *   `getFieldsByForms` 方法：
        *   依赖 `List<Form> forms` 和 `form.getString(fieldName)`。`Form` 是一个私有库类型，且 `forms` 作为参数传入，无法在样例中假设其存在或创建。因此，整个 `getFieldsByForms` 方法无法被可靠地提取。
        *   `isEmpty(forms)` 也是对 `forms` 的依赖，不可提取。
        *   `StrUtil.isBlank` 是 Hutool 的静态方法，可靠。
    *   `showYesOrNoDialog` 方法：
        *   接收 `PanelContext panelContext` 参数。`PanelContext` 同样是私有库类型，无法假设其存在或创建。因此，`PopDialog.pop` 调用因依赖 `panelContext` 而不可靠。
        *   方法内部构建 `EscapeButtonDto`, `LabelDto`, `SinglePanelDto`, `SizeDto`, `DialogDto` 的过程，都使用了 `new` 或静态工厂方法 (`SizeDto.all`, `PopDialog.buildDialog`)，这些是完全可靠且可提取的。
        *   `ToolUtilities.getInteger` 是静态方法，可靠，但它操作的 `panelValue` 来自不可靠的 `PopDialog.pop` 返回值，因此 `ToolUtilities.getInteger` 的使用场景也无法可靠提取。
3.  **原子性与模式化**：每个样例只关注一个核心任务，替换业务值。

---

根据以上分析，以下是符合您要求的代码样例：

### 提取的 API 使用样例

---

#### 样例 1: 构建一个“确认”（是）类型的对话框按钮 `EscapeButtonDto`

**模式说明**: 展示如何创建一个带有确认（"是"）样式、自定义文本和控件ID的逃逸按钮数据传输对象。

```java
// 构建一个“确认”类型的按钮
EscapeButtonDto confirmButton = (EscapeButtonDto) new EscapeButtonDto()
        .setText("您的确认按钮文本") // 例如："是"
        .setWidgetId("您的确认按钮唯一ID") // 例如："_BUTTON_YES"
        .setConfirmStyle(); // 设置为确认样式
```

---

#### 样例 2: 构建一个“取消”（否）类型的对话框按钮 `EscapeButtonDto`

**模式说明**: 展示如何创建一个带有取消（"否"）样式和自定义文本的逃逸按钮数据传输对象。

```java
// 构建一个“取消”类型的按钮
EscapeButtonDto cancelButton = (EscapeButtonDto) new EscapeButtonDto()
        .setText("您的取消按钮文本") // 例如："否"
        .setCancelStyle(); // 设置为取消样式
```

---

#### 样例 3: 构建一个带有标签和首选尺寸的 `SinglePanelDto`

**模式说明**: 展示如何创建一个包含文本标签并设置其首选尺寸的单面板数据传输对象。

```java
import fe.cmn.panel.SinglePanelDto;
import fe.cmn.widget.LabelDto;
import fe.cmn.widget.SizeDto;

// 构建一个包含文本标签的面板
SinglePanelDto panelWithLabel = new SinglePanelDto(new LabelDto("此处填写面板上的提示信息"));

// 设置面板的首选尺寸
panelWithLabel.setPreferSize(SizeDto.all(您的宽度值_例如_300, 您的高度值_例如_100));
```

---

#### 样例 4: 使用 `PopDialog` 构建一个可配置的 `DialogDto` 对象

**模式说明**: 展示如何通过 `PopDialog.buildDialog` 静态方法，结合之前创建的面板和按钮，构建一个完整的、高度可配置的对话框数据传输对象。这包括设置标题、内容面板、操作按钮以及对话框行为（如是否可点击背景关闭、是否显示装饰和标题图标）。

```java
import fe.cmn.callbackWidget.popWidget.DialogDto;
import fe.cmn.panel.SinglePanelDto;
import fe.cmn.panel.ability.PopDialog;
import fe.cmn.widget.EscapeButtonDto;
import fe.cmn.widget.LabelDto;
import fe.cmn.widget.SizeDto;

// 1. 准备对话框内容面板 (来自样例 3)
SinglePanelDto dialogContentPanel = new SinglePanelDto(new LabelDto("您的对话框正文消息"));
dialogContentPanel.setPreferSize(SizeDto.all(your_dialog_width_example_300, your_dialog_height_example_100));

// 2. 准备对话框确认按钮 (来自样例 1)
EscapeButtonDto dialogConfirmButton = (EscapeButtonDto) new EscapeButtonDto()
        .setText("您的确认按钮文本")
        .setWidgetId("您的确认按钮唯一ID")
        .setConfirmStyle();

// 3. 准备对话框取消按钮 (来自样例 2)
EscapeButtonDto dialogCancelButton = (EscapeButtonDto) new EscapeButtonDto()
        .setText("您的取消按钮文本")
        .setCancelStyle();

// 4. 使用 PopDialog.buildDialog 构建 DialogDto
DialogDto configuredDialog = PopDialog.buildDialog(
                "您的对话框标题", // 对话框标题
                dialogContentPanel, // 对话框内容面板
                dialogConfirmButton, // 确认按钮
                dialogCancelButton, // 取消按钮
                true // 是否显示右上角的关闭按钮 (通常为 true/false)
        )
        .setDecoration(null) // 设置对话框装饰，例如 null 表示无装饰
        .setBarrierDismissible(true) // 设置点击对话框外部（屏障）是否可关闭对话框
        .setTitleIcon(null); // 设置对话框标题栏图标，例如 null 表示无图标
```