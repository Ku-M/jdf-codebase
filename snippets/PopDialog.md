## 文件核心功能

`PopDialog.java` 文件作为前端UI框架的一个关键组成部分，其核心职责是提供一个统一且便捷的API，用于创建、配置和展示各种类型的弹出对话框（Dialog）。它充当了后端业务逻辑与前端UI渲染之间的桥梁，将后端组装的对话框配置（通过一系列DTO对象表示）通过特定的回调机制（`PanelContext.callback`）发送到前端进行渲染和交互。

它封装了对话框的构建细节、按钮布局、超时管理、数据回传逻辑，并提供了丰富的静态方法，以满足从简单提示框到复杂输入框、确认框等多种场景的需求，极大地简化了对话框的调用方式。

## 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class PopDialog` | `fe.cmn.data.BasicAbility<Void>` | 作为弹出对话框能力的实现类，聚合了 `DialogDto` 及其相关配置，并提供了静态方法用于便捷地构建和触发前端的对话框显示。它处理了对话框的生命周期、用户交互结果回传等逻辑。 |

### 方法与属性详情

**类属性:**

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `serialVersionUID` | `long` | 类的序列化版本UID。 |
| `defaultShowClose` | `Boolean` | 默认是否显示右上角关闭按钮的配置。 |
| `defaultBarrierDismissible` | `Boolean` | 默认是否允许点击对话框外部区域关闭对话框的配置。 |
| `defaultOnlyGuiValue` | `Boolean` | 默认是否仅返回界面修改过的数据（而非全部业务数据）的配置。 |
| `dialog` | `DialogDto` | 内部持有的对话框数据传输对象，所有对话框的配置和行为最终都由该对象承载。 |

**实例方法 (主要用于配置 `PopDialog` 实例内部的 `dialog` 属性，支持链式调用):**

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `PopDialog()` | 构造函数 | 初始化 `PopDialog` 实例，并设置默认超时时间为 `PopWidgetDto.DEFAULT_TIME_OUT`。 |
| `getDialog()` | `DialogDto` | 获取当前 `PopDialog` 实例持有的 `DialogDto` 对象。 |
| `setDialog(DialogDto dialog)` | `PopDialog` | 设置当前 `PopDialog` 实例的 `DialogDto` 对象，支持链式调用。 |
| `setTimeout(long timeout)` | `PopDialog` | 设置对话框等待关闭的超时时间，会更新内部 `DialogDto` 的 `waitForClose` 属性，并调用父类方法。 |
| `getWaitForClose()` | `long` | 获取对话框等待关闭的时间。 |
| `setWaitForClose(long waitForClose)` | `PopDialog` | 设置对话框等待关闭的时间。 |
| `getTitle()` | `String` | 获取弹窗标题。 |
| `setTitle(String title)` | `PopDialog` | 设置弹窗标题。 |
| `getShowClose()` | `Boolean` | 获取是否显示关闭按钮。 |
| `setShowClose(Boolean showClose)` | `PopDialog` | 设置是否显示关闭按钮。 |
| `isOnlyGuiValue()` | `Boolean` | 获取是否仅返回界面修改过的数据。 |
| `setOnlyGuiValue(Boolean onlyGuiValue)` | `PopDialog` | 设置是否仅返回界面修改过的数据。 |
| `getPanel()` | `PanelDto` | 获取弹窗内容面板组件。 |
| `setPanel(PanelDto panel)` | `PopDialog` | 设置弹窗内容面板组件。 |
| `getBarrierDismissible()` | `Boolean` | 获取是否允许点击外部关闭。 |
| `setBarrierDismissible(Boolean barrierDismissible)` | `PopDialog` | 设置是否允许点击外部关闭。 |
| `getDecoration()` | `PopDecorationDto` | 获取弹窗样式。 |
| `setDecoration(PopDecorationDto decoration)` | `PopDialog` | 设置弹窗样式。 |
| `getTitleIcon()` | `IconDto` | 获取标题图标。 |
| `setTitleIcon(IconDto titleIcon)` | `PopDialog` | 设置标题图标。 |
| `getPopDialogType()` | `PopDialogType` | 获取弹出框类型。 |
| `setPopDialogType(PopDialogType popDialogType)` | `PopDialog` | 设置弹出框类型。 |
| `getRouteSettings()` | `PopupRouteSettingsDto` | 获取弹出框路由设置。 |
| `setRouteSettings(PopupRouteSettingsDto routeSettings)` | `PopDialog` | 设置弹出框路由设置。 |

## 主要函数/方法

该文件大量使用静态方法提供便捷的对话框创建和显示功能。

| 函数名 | 参数 | 返回值 | 功能描述 |
| :--- | :--- | :--- | :--- |
| `buildDialog` (重载) | `String title, PanelDto panel, ButtonDto ok, ButtonDto cancel`, optionally `boolean showClose` | `DialogDto` | 构建一个 `DialogDto` 对象，包含标题、内容面板以及可选的确认/取消按钮，并处理按钮的布局。 |
| `pop` (重载) | `PanelContext ctx, DialogDto dlg`, optionally `Long timeout` | `PanelValue` | 核心方法，将构建好的 `DialogDto` 通过 `PanelContext` 的 `callback` 方法发送给前端，触发弹窗显示，并返回用户操作后的数据。 |
| `buildPanel` | `PanelDto panel, ButtonDto ok, ButtonDto cancel` | `PanelDto` | 辅助方法，用于在面板底部添加确认/取消按钮。 |
| `build` (重载) | `String title, PanelDto panel, ButtonDto ok, ButtonDto cancel`, optionally `boolean showClose` | `PopDialog` | 方便地创建一个 `PopDialog` 实例，其中包含通过 `buildDialog` 构建好的 `DialogDto`。 |
| `post` (多重载) | `PanelContext ctx, String title, PanelDto panel`, 各种可选参数（`decoration`, `ok`, `cancel`, `showClose`） | `void` | 显示一个非阻塞式的对话框，通常用于展示信息，不等待用户输入结果。内部调用 `buildDialog` 和 `pop`。 |
| `show` (多重载) | `PanelContext ctx, String title, PanelDto panel`, 各种可选参数（`ok`, `cancel`, `showClose`, `decoration`, `barrierDismissible`, `titleIcon`, `popType`） | `void` | 显示一个非阻塞式的对话框，提供比 `post` 更多参数组合，方便调用，不等待用户输入结果。内部调用 `buildDialog` 和 `pop`。 |
| `showInput` (多重载) | `PanelContext ctx, String title, PanelDto panel`, 各种可选参数（`initText`, `onlyGuiValue`, `timeout`, `showClose`, `decoration`, `titleIcon`, `popType`, `confirmBtnText`, `cancelBtnText`） | `String` 或 `PanelValue` | 显示一个阻塞式的对话框，用于获取用户输入。它会等待对话框关闭，并根据“确认”按钮的点击状态返回用户输入的数据。 |
| `showInputwithOutButton` (多重载) | `PanelContext ctx, String title, PanelDto panel`, 各种可选参数（`onlyGuiValue`, `timeout`, `showClose`, `decoration`, `barrierDismissible`, `titleIcon`, `popType`） | `PanelValue` | 显示一个无确认/取消按钮的阻塞式对话框。返回用户输入数据不依赖按钮点击，而是对话框关闭事件。 |
| `showInputWithCustomedButton` (多重载) | `PanelContext ctx, String title, PanelDto panel, ButtonDto okButton`, 各种可选参数（`onlyGuiValue`, `timeout`, `showClose`, `decoration`, `barrierDismissible`, `titleIcon`, `popType`） | `PanelValue` | 显示一个带自定义确认按钮的阻塞式输入对话框。只有当自定义确认按钮被点击时，才返回用户输入的数据。 |
| `showConfirm` (多重载) | `PanelContext ctx, String title, String msg`, 各种可选参数（`windowSize`, `showClose`, `decoration`, `barrierDismissible`, `titleIcon`, `popType`） | `boolean` | 显示一个阻塞式的确认对话框，通常用于询问用户是否执行某项操作。返回 `true` 表示用户点击“确认”，`false` 表示点击“取消”或关闭。 |
| `pop` (全参数重载) | `PanelContext context, PanelDto panel, PopupRouteSettingsDto routeSettings, String title, Boolean showClose, Long waitForClose, Boolean onlyGuiValue, Boolean barrierDismissible, PopDecorationDto decoration, IconDto titleIcon, PopDialogType popDialogType` | `PanelValue` | 提供一个包含所有可配置参数的终极弹出对话框方法，允许开发者精细控制弹窗的所有方面。 |

## 对外依赖与交互

`PopDialog.java` 文件通过导入大量来自 `fe.cmn` (前端通用模块) 和 `com.leavay` (公司通用工具库) 的类，与系统的其他部分紧密耦合，并协同工作。

**导入的外部库/项目内其他类:**

*   **`com.leavay.common.util.ToolUtilities`**: 提供通用的工具函数，例如 `getInteger()` 用于从 `PanelValue` 中安全地获取整数值，常用于判断按钮点击结果。
*   **`com.leavay.ms.tool.CmnUtil`**: 提供通用工具函数，例如 `getString()` 用于从 `PanelValue` 中获取字符串值。

*   **`fe.cmn.app.PopupRouteSettingsDto`**: 定义了弹出框的路由设置，用于控制弹窗的导航行为。
*   **`fe.cmn.callbackWidget.popWidget.DialogDto`**: 核心依赖，代表一个完整的弹出对话框数据结构，包含了标题、内容、按钮、样式等所有配置信息。`PopDialog` 类内部持有一个 `DialogDto` 实例，并对其进行各种操作。
*   **`fe.cmn.callbackWidget.popWidget.PopWidgetDto`**: 弹出窗口小部件的通用数据传输对象，提供了默认超时时间 `DEFAULT_TIME_OUT`。
*   **`fe.cmn.callbackWidget.popWidget.PopWidgetTheme`**: 弹出窗口小部件的主题枚举，虽然导入但在此文件中未直接使用，可能通过 `DialogDto` 间接关联。
*   **`fe.cmn.data.BasicAbility`**: `PopDialog` 的父类，提供基础能力框架，如超时设置。
*   **`fe.cmn.editor.TextEditorDto`**: 文本编辑器组件的数据传输对象，用于在 `showInput` 方法中构建简单的文本输入框。
*   **`fe.cmn.panel.BoxDto`**: 面板布局组件的数据传输对象，用于在构建对话框时进行水平或垂直布局（例如将内容面板和按钮组合起来）。
*   **`fe.cmn.panel.PanelContext`**: **核心交互接口**，代表了面板的上下文。`PopDialog` 通过调用 `ctx.callback(PopDialog)` 将自身（作为一种能力）发送给前端框架，从而触发弹窗的显示和处理用户交互。
*   **`fe.cmn.panel.PanelDto`**: 面板组件的抽象数据传输对象，表示对话框的主要内容区域。
*   **`fe.cmn.panel.PanelValue`**: `PanelContext.callback()` 方法的返回值，包含了用户在弹窗中操作后的数据（例如点击了哪个按钮、输入了什么内容）。
*   **`fe.cmn.panel.PopDialogType`**: 弹出对话框类型枚举，用于定义不同预设主题或行为的弹窗（如警告、成功、信息等）。
*   **`fe.cmn.panel.SinglePanelDto`**: 单个面板组件的数据传输对象，通常用于包装一个单一的UI组件作为对话框的内容面板。
*   **`fe.cmn.widget.ButtonDto`**: 按钮组件的数据传输对象，用于配置对话框中的“确认”、“取消”等按钮。
*   **`fe.cmn.widget.EscapeButtonDto`**: 继承自 `ButtonDto`，可能用于表示具有特殊行为（如自动触发某些效果）的按钮，常用于确认/取消操作。
*   **`fe.cmn.widget.IconDto`**: 图标组件的数据传输对象，用于设置对话框标题的图标。
*   **`fe.cmn.widget.InsetDto`**: 边距设置的数据传输对象，用于控制组件的内边距。
*   **`fe.cmn.widget.LabelDto`**: 标签组件的数据传输对象，用于显示文本内容，例如对话框消息。
*   **`fe.cmn.widget.SizeDto`**: 尺寸设置的数据传输对象，用于指定组件的首选尺寸或最小尺寸。
*   **`fe.cmn.widget.decoration.DialogDecorationDto`**: 对话框特有的装饰配置数据传输对象，用于控制对话框的边框、背景、阴影等视觉样式。
*   **`fe.cmn.widget.decoration.PopDecorationDto`**: 弹出框装饰的基类数据传输对象，可能包含通用装饰属性。

**交互方式:**

`PopDialog` 主要通过以下步骤与外部交互：
1.  **构造 `DialogDto`**: 通过 `buildDialog` 等静态方法，结合 `PanelDto`、`ButtonDto`、`IconDto`、`DecorationDto` 等各种UI组件的DTO，组装出一个完整的 `DialogDto` 对象，定义了弹窗的结构和样式。
2.  **触发回调**: 将构造好的 `DialogDto` 封装在 `PopDialog` 实例中（或直接传递），然后通过 `PanelContext` 对象的 `callback()` 方法，将这个弹出请求发送给前端框架。
3.  **接收结果**: 对于阻塞式弹窗（如 `showInput` 和 `showConfirm` 系列方法），`PanelContext.callback()` 会返回一个 `PanelValue` 对象。`PopDialog` 会解析这个 `PanelValue`，根据预设的 `widgetId`（如 `_BUTTON_OK`, `_BUTTON_YES`）来判断用户在前端的操作，并提取用户输入的数据，最终返回给调用者。
4.  **默认值与常量**: 利用 `PopWidgetDto.DEFAULT_TIME_OUT` 等常量，以及类内定义的 `defaultShowClose` 等默认布尔值，减少重复配置，提供便捷的默认行为。

总体而言，`PopDialog` 作为一个高级封装层，屏蔽了底层UI框架与前端通信的复杂性，提供了一套面向业务的、声明式的API来管理和显示弹出对话框。

