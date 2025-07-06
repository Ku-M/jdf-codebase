### 1. 文件核心功能
`ButtonDecorationDto.java` 文件定义了一个数据传输对象（DTO），其核心功能是封装和管理用户界面中按钮的各种视觉样式和行为属性。它作为一个配置类，聚合了按钮在不同状态（如普通、悬停、聚焦、禁用）下的背景色、前景（文本/图标）色、边框、阴影、图标样式、对齐方式等。

它在项目中扮演的角色是：
*   **样式配置载体**: 提供一个标准化的结构来定义和传递按钮的完整样式信息。
*   **POJO**: 作为普通Java对象，便于数据传输、序列化以及与其他模块（如UI渲染引擎、数据持久化层）的交互。
*   **元数据支持**: 通过自定义注解`@FieldDefine`和`@PojoMeta`，可能支持自动化的UI生成或元数据驱动的配置界面。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class ButtonDecorationDto` | `DecorationDto` | 定义按钮的各种视觉样式属性，包括在不同交互状态（普通、悬停、聚焦、禁用）下的颜色、边框、图标、阴影等，并提供构建器模式的setter方法。 |

#### 方法与属性详情

**类**: `ButtonDecorationDto`

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `serialVersionUID` | `long` | 用于序列化的版本UID。 |
| `theme` | `ButtonThemeType` | 按钮的主题类型，其他字段可能会基于此主题进行合并。 |
| `icon` | `IconStyleDto` | 按钮的图标样式配置。 |
| `gap` | `double` | 图标与文字之间的间隔距离。 |
| `overlayColor` | `CColor` | 按钮的叠加颜色。 |
| `buttonShape` | `ButtonBorderShapeType` | 按钮的边框形状类型（如圆形、足球场形）。 |
| `buttonBorder` | `BorderSideDto` | 按钮的默认边框样式。 |
| `hoveredButtonBorder` | `BorderSideDto` | 按钮悬停时的边框样式。 |
| `focusedButtonBorder` | `BorderSideDto` | 按钮聚焦时的边框样式。 |
| `alignment` | `AlignmentType` | 按钮内容的对齐排列方式，仅在按钮大小大于内容时生效。 |
| `shadowColor` | `CColor` | 按钮阴影的颜色。 |
| `elevation` | `double` | 按钮阴影的偏移值。 |
| `focusedBackgroundColor` | `CColor` | 按钮聚焦时的背景色。 |
| `disableBackgroundColor` | `CColor` | 按钮禁用时的背景色。 |
| `hoverBackgroundColor` | `CColor` | 按钮悬停时的背景色。 |
| `hoveredForegroundColor` | `CColor` | 按钮悬停时文本和图标的前景色。 |
| `focusedForegroundColor` | `CColor` | 按钮聚焦时文本和图标的前景色。 |
| `disabledForegroundColor` | `CColor` | 按钮禁用时文本和图标的前景色。 |
| `static setIconColor(CColor color)` | `ButtonDecorationDto` | 一个静态工厂方法，用于快速创建一个新的`ButtonDecorationDto`实例并设置其图标颜色。 |
| `getGap()` | `double` | 获取图标与文字之间的间隔。 |
| `setGap(double gap)` | `ButtonDecorationDto` | 设置图标与文字之间的间隔，返回`this`支持链式调用。 |
| `getOverlayColor()` | `CColor` | 获取叠加颜色。 |
| `setOverlayColor(CColor overlayColor)` | `ButtonDecorationDto` | 设置叠加颜色，返回`this`支持链式调用。 |
| `setOverlayColor(Color overlayColor)` | `ButtonDecorationDto` | 重载方法，接受`java.awt.Color`并转换为`CColor`设置叠加颜色，返回`this`支持链式调用。 |
| `getButtonShape()` | `ButtonBorderShapeType` | 获取边框形状类型。 |
| `setButtonShape(ButtonBorderShapeType buttonShape)` | `ButtonDecorationDto` | 设置边框形状类型，返回`this`支持链式调用。 |
| `getButtonBorder()` | `BorderSideDto` | 获取默认边框样式。 |
| `setButtonBorder(BorderSideDto buttonBorder)` | `ButtonDecorationDto` | 设置默认边框样式，返回`this`支持链式调用。 |
| `getAlignment()` | `AlignmentType` | 获取对齐排列方式。 |
| `setAlignment(AlignmentType alignment)` | `ButtonDecorationDto` | 设置对齐排列方式，返回`this`支持链式调用。 |
| `getIcon()` | `IconStyleDto` | 获取图标样式。 |
| `setIcon(IconStyleDto icon)` | `ButtonDecorationDto` | 设置图标样式，返回`this`支持链式调用。 |
| `getShadowColor()` | `CColor` | 获取按钮阴影颜色。 |
| `setShadowColor(Color shadowColor)` | `ButtonDecorationDto` | 重载方法，接受`java.awt.Color`并转换为`CColor`设置阴影颜色，返回`this`支持链式调用。 |
| `setShadowColor(CColor shadowColor)` | `ButtonDecorationDto` | 设置按钮阴影颜色，返回`this`支持链式调用。 |
| `getElevation()` | `double` | 获取按钮阴影偏移值。 |
| `setElevation(double elevation)` | `ButtonDecorationDto` | 设置按钮阴影偏移值，返回`this`支持链式调用。 |
| `getTheme()` | `ButtonThemeType` | 获取主题类型。 |
| `setTheme(ButtonThemeType theme)` | `ButtonDecorationDto` | 设置主题类型，返回`this`支持链式调用。 |
| `getHoveredButtonBorder()` | `BorderSideDto` | 获取悬停边框样式。 |
| `setHoveredButtonBorder(BorderSideDto hoveredButtonBorder)` | `ButtonDecorationDto` | 设置悬停边框样式，返回`this`支持链式调用。 |
| `getFocusedButtonBorder()` | `BorderSideDto` | 获取聚焦边框样式。 |
| `setFocusedButtonBorder(BorderSideDto focusedButtonBorder)` | `ButtonDecorationDto` | 设置聚焦边框样式，返回`this`支持链式调用。 |
| `getHoveredForegroundColor()` | `CColor` | 获取悬停前景色。 |
| `setHoveredForegroundColor(CColor hoveredForegroundColor)` | `ButtonDecorationDto` | 设置悬停前景色，返回`this`支持链式调用。 |
| `getFocusedForegroundColor()` | `CColor` | 获取聚焦前景色。 |
| `setFocusedForegroundColor(CColor focusedForegroundColor)` | `ButtonDecorationDto` | 设置聚焦前景色，返回`this`支持链式调用。 |
| `getDisabledForegroundColor()` | `CColor` | 获取禁用前景色。 |
| `setDisabledForegroundColor(CColor disabledForegroundColor)` | `ButtonDecorationDto` | 设置禁用前景色，返回`this`支持链式调用。 |
| `getFocusedBackgroundColor()` | `CColor` | 获取聚焦背景色。 |
| `setFocusedBackgroundColor(CColor focusedBackgroundColor)` | `ButtonDecorationDto` | 设置聚焦背景色，返回`this`支持链式调用。 |
| `getDisableBackgroundColor()` | `CColor` | 获取禁用背景色。 |
| `setDisableBackgroundColor(CColor disableBackgroundColor)` | `ButtonDecorationDto` | 设置禁用背景色，返回`this`支持链式调用。 |
| `getHoverBackgroundColor()` | `CColor` | 获取悬停背景色。 |
| `setHoverBackgroundColor(CColor hoverBackgroundColor)` | `ButtonDecorationDto` | 设置悬停背景色，返回`this`支持链式调用。 |
| `setBorder(BorderDto border)` | `DecorationDto` | 重写父类方法，但抛出`RuntimeException`，指示应使用`buttonBorder`字段设置按钮边框。 |
| `setBorder(Color color, Double width, Double borderRadius)` | `DecorationDto` | 重写父类方法，但抛出`RuntimeException`，指示应使用`buttonBorder`字段设置按钮边框。 |

### 3. 主要函数/方法 (如果适用)

除了大量的getter/setter方法（它们通常作为属性的一部分被理解）之外，文件包含一个值得注意的静态工厂方法：

| 函数名 | 参数 | 返回值 | 功能描述 |
| :--- | :--- | :--- | :--- |
| `setIconColor` | `CColor color` | `ButtonDecorationDto` | 创建一个新的`ButtonDecorationDto`实例，并立即设置其内部`IconStyleDto`的颜色。这是一个便捷的工厂方法，用于快速创建一个特定图标颜色的按钮样式DTO。 |

### 4. 对外依赖与交互

`ButtonDecorationDto`文件依赖于多个外部类和项目内的其他类，以构建其完整的按钮样式配置能力：

*   **`java.awt.Color`**: Java AWT库中的标准颜色类。它在`setOverlayColor`和`setShadowColor`方法中被用于接收标准的Java颜色对象，并将其转换为项目自定义的`CColor`类型。
*   **`fe.cmn.data.CColor`**: 这是项目内部定义的颜色类，可能对`java.awt.Color`进行了封装或扩展，以提供额外的功能或统一的颜色处理方式。`ButtonDecorationDto`的大部分颜色属性都使用此类型。
*   **`fe.cmn.pojo.annotation.FieldDefine`**: 项目内部定义的注解，用于标注DTO的字段，可能提供字段的标签（label）和描述（description），这有助于在UI界面中自动生成表单或展示字段信息。
*   **`fe.cmn.pojo.annotation.PojoMeta`**: 项目内部定义的注解，用于标注整个DTO类，提供DTO的元数据，如标签（label）和图标路径（icon），可能用于在管理界面或设计工具中展示此DTO类型。
*   **`DecorationDto`**: `ButtonDecorationDto`的父类，意味着它继承了`DecorationDto`中定义的通用装饰属性。同时，它明确地重写了父类的`setBorder`方法并抛出异常，强制要求使用`buttonBorder`字段来设置按钮边框，表明按钮有其特殊的边框处理机制。
*   **`IconStyleDto`**: 定义图标样式的DTO。`ButtonDecorationDto`包含一个`IconStyleDto`实例来配置按钮的图标。
*   **`BorderSideDto`**: 定义边框侧边样式的DTO。`ButtonDecorationDto`使用它来配置默认、悬停和聚焦状态下的按钮边框。
*   **`ButtonThemeType`**: 可能是一个枚举类型，用于定义按钮的预设主题。
*   **`ButtonBorderShapeType`**: 可能是一个枚举类型，用于定义按钮的边框形状。
*   **`AlignmentType`**: 可能是一个枚举类型，用于定义内容在按钮内部的对齐方式。

**交互方式**:
`ButtonDecorationDto`主要作为数据载体与这些依赖项交互：
*   它持有`CColor`、`IconStyleDto`、`BorderSideDto`、枚举类型`ButtonThemeType`、`ButtonBorderShapeType`、`AlignmentType`的实例，通过组合这些对象来构建完整的按钮样式。
*   通过其setter方法，它可以从`java.awt.Color`等外部类型接收数据并转换为内部`CColor`类型。
*   通过其getter方法，它将自身封装的样式数据暴露给UI渲染引擎或其他需要这些样式信息的组件。
*   通过继承`DecorationDto`并重写其方法，它对父类的通用行为进行了特定化和限制，确保按钮边框的设置符合其特有的设计。
*   `@FieldDefine`和`@PojoMeta`注解表明它可能与一个元数据处理框架或自动化UI生成工具进行交互，这些工具会读取注解信息来构建或展示配置界面。

