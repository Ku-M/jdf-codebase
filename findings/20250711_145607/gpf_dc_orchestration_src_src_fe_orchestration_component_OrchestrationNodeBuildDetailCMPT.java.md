# Analysis for: gpf_dc_orchestration\src\src\fe\orchestration\component\OrchestrationNodeBuildDetailCMPT.java

## Original File Path
`D:\TMP\CLASS_BUS_PROJECT\gpf_dc_orchestration\src\src\fe\orchestration\component\OrchestrationNodeBuildDetailCMPT.java`

## Extracted Snippets & Analysis
作为一名资深的软件架构师，我已仔细分析了您提供的代码，并严格遵循了您制定的[核心规则]来识别和提取有价值的、可用于训练AI的代码样例。

以下是我从代码中提炼出的核心API使用模式，它们均符合独立、可靠、去业务化和原子性的标准：

---

### 提取的代码样例

#### 1. 构建文本样式 (`CTextStyle`)

- **功能**: 展示如何创建和配置 `CTextStyle` 对象，用于定义文本的颜色和行高。
- **可靠性**: 完全独立，只依赖于标准Java类型。
- **原子性**: 专注于 `CTextStyle` 的构建。

```java
import fe.cmn.text.CTextStyle;
import java.awt.Color;

// 构建文本样式 (CTextStyle)
CTextStyle textStyle = new CTextStyle()
    .setColor(Color.BLACK) // 设置文本颜色
    .setHeight(1.3); // 设置行高，例如1.3倍行距
```

#### 2. 构建带文本且包含装饰的标签 (`LabelDto`)

- **功能**: 展示如何创建 `LabelDto` 并为其设置 `DecorationDto`，以定义文本的样式。
- **可靠性**: 完全独立，内部构造了所有依赖。
- **原子性**: 专注于 `LabelDto` 的构建和基础装饰设置。

```java
import fe.cmn.panel.DecorationDto;
import fe.cmn.widget.LabelDto;
import fe.cmn.text.CTextStyle;
import java.awt.Color;

// 构建一个带文本且包含装饰的标签 (LabelDto)
LabelDto label = new LabelDto("此处填写您的标签文本");
label.setDecoration(new DecorationDto()
    .setTextStyle(new CTextStyle()
        .setColor(Color.BLUE) // 设置文本颜色
        .setHeight(1.5) // 设置行高
    )
    // .setPadding(5) // 可选：设置内边距
);
```

#### 3. 创建水平布局容器 (`BoxDto.hbar`)

- **功能**: 展示如何使用 `BoxDto.hbar` 创建一个水平排列子组件的容器。
- **可靠性**: `BoxDto.hbar` 是静态方法，传入通用 `WidgetDto` 即可。
- **原子性**: 专注于水平布局容器的创建。

```java
import fe.cmn.panel.BoxDto;
import fe.cmn.widget.LabelDto;
import fe.cmn.widget.ImageDto;

// 创建一个水平布局容器 (BoxDto.hbar)
// 可以包含一个或多个 WidgetDto 作为子组件
BoxDto horizontalBox = BoxDto.hbar(
    new LabelDto("组件A"), // 第一个子组件：标签
    new ImageDto("res://images/icon_placeholder.png"), // 第二个子组件：图片
    new LabelDto("组件B") // 第三个子组件：另一个标签
);
```

#### 4. 构建可折叠项的装饰样式 (`CollapseItemDecorationDto`)

- **功能**: 展示如何创建 `CollapseItemDecorationDto` 以自定义可折叠项的外观。
- **可靠性**: 完全独立，所有依赖都在内部构建。
- **原子性**: 专注于 `CollapseItemDecorationDto` 的构建。

```java
import fe.cmn.collapse.CollapseItemDecorationDto;
import fe.cmn.widget.decoration.BorderDto;
import fe.cmn.widget.decoration.BorderRadiusDto;
import fe.cmn.data.RadiusDto;
import java.awt.Color;

// 构建可折叠项的装饰样式 (CollapseItemDecorationDto)
CollapseItemDecorationDto itemDecoration = new CollapseItemDecorationDto()
    .setBorder(BorderDto.all(Color.LIGHT_GRAY, 1.0)) // 设置所有边框：颜色和宽度
    .setBorderRadius(BorderRadiusDto.all(RadiusDto.circular(10.0))) // 设置圆角半径
    .setPadding(10) // 设置内边距
    .setMargin(10) // 设置外边距
    .setBackground(new Color(240, 240, 240)); // 设置背景颜色 (RGB)
```

#### 5. 构建Markdown文本的装饰样式 (`MarkdownDecorationDto`)

- **功能**: 展示如何创建 `MarkdownDecorationDto` 以自定义 `MarkdownDto` 的外观。
- **可靠性**: 完全独立。
- **原子性**: 专注于 `MarkdownDecorationDto` 的构建。

```java
import fe.cmn.widget.decoration.MarkdownDecorationDto;
import fe.cmn.text.CTextStyle;
import java.awt.Color;

// 构建Markdown文本的装饰样式 (MarkdownDecorationDto)
MarkdownDecorationDto markdownDecoration = new MarkdownDecorationDto()
    .setTextStyle(new CTextStyle()
        .setColor(Color.DARK_GRAY) // 示例文本颜色
        .setHeight(1.2) // 示例行高
    )
    .setPadding(5); // 设置内边距
```

#### 6. 构建Markdown文本组件 (`MarkdownDto`)

- **功能**: 展示如何创建 `MarkdownDto` 并设置其数据、行为和装饰。
- **可靠性**: 完全独立，所有依赖都在内部构建。
- **原子性**: 专注于 `MarkdownDto` 的构建。

```java
import fe.cmn.widget.MarkdownDto;
import fe.cmn.widget.decoration.MarkdownDecorationDto;

// 构建Markdown文本组件 (MarkdownDto)
MarkdownDto markdownWidget = new MarkdownDto();
markdownWidget
    .setData("### 标题\n\n- 列表项1\n- 列表项2") // 设置Markdown格式的内容
    .setShrinkWrap(true) // 内容自适应大小
    .setSelectable(false) // 文本不可选择
    .setDecoration(new MarkdownDecorationDto() // 设置装饰样式
        .setPadding(10) // 例如：设置内边距
    )
    .setWidgetId("your_markdown_widget_id"); // 设置组件的唯一ID
```

#### 7. 构建可折叠项 (`CollapseItemDto`)

- **功能**: 展示如何创建 `CollapseItemDto`，作为 `CollapseDto` 的子项。
- **可靠性**: 完全独立，所有依赖都在内部构建。
- **原子性**: 专注于 `CollapseItemDto` 的构建。

```java
import fe.cmn.collapse.CollapseItemDto;
import fe.cmn.panel.BoxDto;
import fe.cmn.widget.LabelDto;
import fe.cmn.widget.MarkdownDto;

// 构建可折叠项 (CollapseItemDto)
CollapseItemDto collapseItem = new CollapseItemDto()
    .setCollapseId("your_collapse_item_id") // 设置折叠项的唯一ID
    .setTitleSlot(BoxDto.hbar(new LabelDto("折叠项标题"))) // 设置标题区域的Widget（例如，一个标签）
    .setContent(new MarkdownDto().setData("折叠项的具体内容，可以是Markdown格式。")); // 设置折叠项展开后显示的内容
```

#### 8. 构建可折叠面板 (`CollapseDto`)

- **功能**: 展示如何创建 `CollapseDto`，包含多个 `CollapseItemDto` 和整体装饰。
- **可靠性**: 完全独立，所有依赖都在内部构建，或使用通用集合类型。
- **原子性**: 专注于 `CollapseDto` 的构建。

```java
import fe.cmn.panel.CollapseDto;
import fe.cmn.collapse.CollapseItemDto;
import fe.cmn.collapse.CollapseDecorationDto;
import fe.cmn.collapse.CollapseItemDecorationDto;
import fe.cmn.panel.BoxMainAxisSize;
import fe.cmn.panel.BoxDto;
import fe.cmn.widget.LabelDto;
import fe.cmn.widget.MarkdownDto;
import com.google.common.collect.Lists; // 确保导入 Lists
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

// 示例：构建 CollapseItemDto 和 CollapseItemDecorationDto，以供 CollapseDto 使用
CollapseItemDto exampleItem = new CollapseItemDto()
    .setCollapseId("item_id_1")
    .setTitleSlot(BoxDto.hbar(new LabelDto("Item 1 Title")))
    .setContent(new LabelDto("Item 1 Content"));

CollapseItemDecorationDto exampleItemDecoration = new CollapseItemDecorationDto()
    .setBackground(new Color(230, 245, 255)); // 示例背景色

Map<String, CollapseItemDecorationDto> itemDecorations = new HashMap<>();
itemDecorations.put("item_id_1", exampleItemDecoration); // 将装饰与特定Item ID关联

// 构建可折叠面板 (CollapseDto)
CollapseDto collapsePanel = new CollapseDto();
collapsePanel
    .setDefaultExpand(true) // 默认是否展开所有项
    .setMainAxisSize(BoxMainAxisSize.min) // 主轴尺寸最小化
    .setCollapseItems(Lists.newArrayList(exampleItem)) // 设置可折叠项列表
    .setDecoration(new CollapseDecorationDto() // 设置整体装饰
        .setItemDecorationDtoMap(itemDecorations) // 设置每个可折叠项的装饰映射
    )
    .setWidgetId("your_collapse_panel_id"); // 设置组件的唯一ID
```

#### 9. 设置 WidgetDto/PanelDto 的可见性 (`setVisible`)

- **功能**: 展示如何通过 `setVisible` 方法控制任何 `WidgetDto` 或 `PanelDto` 的显示与隐藏。
- **可靠性**: 独立操作，适用于任何已存在的 DTO 对象。
- **原子性**: 专注于设置可见性这一单一动作。

```java
import fe.cmn.widget.LabelDto;
import fe.cmn.widget.WidgetDto;

// 设置 WidgetDto 或 PanelDto 的可见性
WidgetDto yourWidget = new LabelDto("这是一个标签"); // 假设这是你想要控制可见性的Widget
yourWidget.setVisible(true); // 设置为可见
// yourWidget.setVisible(false); // 设置为不可见
```

#### 10. 构建图片组件 (`ImageDto`)

- **功能**: 展示如何创建 `ImageDto` 并配置其图片路径、ID、尺寸、边距和可见性。
- **可靠性**: 完全独立，所有依赖都在内部构建。
- **原子性**: 专注于 `ImageDto` 的构建。

```java
import fe.cmn.widget.ImageDto;
import fe.cmn.data.SizeDto;

// 构建图片组件 (ImageDto)
ImageDto image = new ImageDto("res://images/your_image.png") // 设置图片资源的路径
    .setWidgetId("your_image_widget_id") // 设置组件的唯一ID
    .setPreferSize(new SizeDto(50.0, 50.0)) // 设置首选尺寸（宽度和高度）
    .setMargin(5) // 设置外边距
    .setVisible(true); // 设置初始可见性
```

#### 11. 构建复杂的布局容器 (`BoxDto.vbar` 嵌套 `BoxDto.hbar`)

- **功能**: 展示如何使用 `BoxDto.vbar` 和 `BoxDto.hbar` 组合构建复杂布局，并设置滚动、对齐等属性。
- **可靠性**: 完全独立，所有嵌套组件均在内部创建。
- **原子性**: 专注于构建一个层次化的布局结构。

```java
import fe.cmn.panel.BoxDto;
import fe.cmn.panel.BoxMainAxisSize;
import fe.cmn.panel.MainAxisAlign;
import fe.cmn.panel.CrossAxisAlign;
import fe.cmn.widget.ImageDto;
import fe.cmn.widget.LabelDto;
import fe.cmn.widget.MarkdownDto;
import fe.cmn.data.SizeDto;
import fe.cmn.panel.CollapseDto;
import fe.cmn.collapse.CollapseItemDto;
import com.google.common.collect.Lists; // 确保导入 Lists

// 构建一个复杂的垂直布局容器 (BoxDto.vbar)
// 包含嵌套的子组件，并设置滚动、对齐等属性
BoxDto complexVerticalLayout = BoxDto.vbar(
        // 子组件1: 一个简单的标签
        new LabelDto("顶部信息区域")
            .setVisible(true), // 设置子组件可见性

        // 子组件2: 一个嵌套的水平布局容器
        BoxDto.hbar(
                // 嵌套水平布局中的第一个子组件：图片
                new ImageDto("res://images/icon.png") // 图片路径
                    .setWidgetId("image_widget_id") // 设置组件ID
                    .setPreferSize(new SizeDto(30.0, 30.0)) // 设置首选尺寸
                    .setMargin(5) // 设置外边距
                    .setVisible(true),

                // 嵌套水平布局中的第二个子组件：Markdown文本
                new MarkdownDto()
                    .setData("### 欢迎\n\n这是一个Markdown文本组件。")
                    .setWidgetId("markdown_widget_id")
                    .setShrinkWrap(true)
                    .setVisible(true)
            )
            .setExpandInBox(true) // 水平布局在垂直布局中展开
            .setMainAxisAlignment(MainAxisAlign.center) // 水平布局主轴居中对齐
            .setCrossAxisAlignment(CrossAxisAlign.center) // 水平布局交叉轴居中对齐
    )
    .setScrollable(true) // 整个垂直布局容器可滚动
    .setExpandInBox(true) // 整个垂直布局容器在父容器中展开 (如果存在父容器)
    .setMainAxisSize(BoxMainAxisSize.min); // 垂直布局主轴尺寸最小化
```

#### 12. 将 WidgetDto 包装成 `SinglePanelDto`

- **功能**: 展示如何使用 `SinglePanelDto.wrap` 将一个 `WidgetDto` 包装成可独立的面板。
- **可靠性**: `SinglePanelDto.wrap` 是静态方法，传入 `WidgetDto` 即可。
- **原子性**: 专注于 `SinglePanelDto` 的创建和基本配置。

```java
import fe.cmn.panel.SinglePanelDto;
import fe.cmn.widget.LabelDto;
import fe.cmn.widget.WidgetDto;

// 将一个 WidgetDto 包装成 SinglePanelDto
WidgetDto contentWidget = new LabelDto("这是面板的内容"); // 假设这是你想要包装的 Widget
SinglePanelDto singlePanel = SinglePanelDto.wrap(contentWidget);
singlePanel
    .setWidgetId("your_single_panel_id") // 可以为面板设置ID
    .setScrollable(true); // 例如，使其可滚动
```

#### 13. 向指定ID的Markdown组件追加文本内容 (`AppendMarkdownData.append`)

- **功能**: 展示如何通过静态方法 `AppendMarkdownData.append` 向特定 `MarkdownDto` 组件追加文本。
- **可靠性**: 依赖于 `PanelContext` 和已知的组件ID，这些通常在运行时可获取。
- **原子性**: 专注于文本追加这一动作。

```java
import fe.cmn.panel.PanelContext;
import fe.cmn.panel.ability.AppendMarkdownData;

// 假设 PanelContext 是从框架提供的上下文参数中获取的
// PanelContext panelContext = your_panel_context_instance; 

// 向指定ID的Markdown组件追加文本内容
// 注意: your_panel_context_instance 应替换为实际的 PanelContext 实例
//       your_markdown_widget_id 应替换为目标 MarkdownDto 的实际 Widget ID
PanelContext panelContext = /* 从方法参数或框架回调中获取 PanelContext 实例 */;
String targetWidgetId = "your_markdown_widget_id"; // 目标Markdown组件的ID
String contentToAppend = "这是要追加的新内容。\n- 列表项\n"; // 要追加的Markdown格式文本

AppendMarkdownData.append(panelContext, targetWidgetId, contentToAppend);
```

#### 14. 批量设置多个子组件的可见性 (`SetChildVisible.set`)

- **功能**: 展示如何通过静态方法 `SetChildVisible.set` 批量控制多个子组件的可见性。
- **可靠性**: 依赖于 `PanelContext` 和组件ID，这些通常在运行时可获取。
- **原子性**: 专注于批量可见性设置这一动作。

```java
import fe.cmn.panel.PanelContext;
import fe.cmn.panel.ability.SetChildVisible;
import java.util.HashMap;
import java.util.Map;

// 假设 PanelContext 是从框架提供的上下文参数中获取的
// PanelContext panelContext = your_panel_context_instance; 

// 批量设置多个子组件的可见性
// 注意: your_panel_context_instance 应替换为实际的 PanelContext 实例
PanelContext panelContext = /* 从方法参数或框架回调中获取 PanelContext 实例 */;

Map<String, Boolean> visibilityChanges = new HashMap<>();
visibilityChanges.put("widget_id_1", true); // 设置ID为"widget_id_1"的组件可见
visibilityChanges.put("widget_id_2", false); // 设置ID为"widget_id_2"的组件不可见
visibilityChanges.put("another_widget_id", true); // 设置另一个组件可见

SetChildVisible.set(panelContext, visibilityChanges);
```