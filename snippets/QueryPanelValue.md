作为一名资深的Java软件工程师，我对`QueryPanelValue.java`文件进行了深入分析，以下是其技术知识库：

---

### 1. 文件核心功能
`QueryPanelValue.java` 文件的核心职责是定义一个用于**查询面板（Panel）中组件值**的能力（Ability）或命令（Command）对象。它封装了查询操作的各种参数，例如：
*   指定要查询的组件（`widgets`）。
*   指定要忽略的组件（`ignoreWidgets`）。
*   指定是否只获取用户在界面上做过修改（即发生动作后）的值（`onlyGuiValue`）。

它作为 `PanelContext` 的一个可回调的能力，通过将自身实例传递给 `PanelContext` 的 `callback` 方法来触发实际的查询逻辑。此文件在项目中扮演的角色是：
*   **参数载体**：为面板值查询操作提供统一、可配置的参数封装。
*   **命令模式的请求**：作为命令模式中的具体请求（或能力）对象，由 `PanelContext` 负责解析和执行。
*   **API提供者**：提供一系列静态工厂方法，简化了面板值查询的调用方式。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class QueryPanelValue` | `BasicAbility<PanelValue>` | 定义一个请求对象，用于从 `PanelContext` 中查询面板组件的值。它封装了查询的条件（如指定组件、忽略组件、是否只获取修改后的值），并通过 `PanelContext.callback()` 方法触发实际的查询逻辑，并期望返回一个 `PanelValue` 对象。 |

#### 方法与属性详情

**类: `QueryPanelValue`**

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `serialVersionUID` | `long` | 用于序列化的版本UID。 |
| `ignoreWidgets` | `List<String>` | 表示在查询时需要忽略的组件名称列表。 |
| `widgets` | `List<String>` | 表示在查询时需要特别指定（或只获取）的组件名称列表。当与`ignoreWidgets`同时设置时，优先`widgets`生效。 |
| `onlyGuiValue` | `boolean` | 标志位，默认为`false`。如果设置为`true`，表示只获取界面上用户做过修改的值；如果为`false`，则获取界面的最终值（修改后的值或初始化值）。 |
| `getIgnoreWidgets()` | `List<String>` | 获取`ignoreWidgets`属性的值。 |
| `setIgnoreWidgets(List<String>)` | `QueryPanelValue` | 设置`ignoreWidgets`属性的值，并返回当前对象实例（支持链式调用）。 |
| `isOnlyGuiValue()` | `boolean` | 获取`onlyGuiValue`属性的值。 |
| `setOnlyGuiValue(boolean)` | `QueryPanelValue` | 设置`onlyGuiValue`属性的值，并返回当前对象实例（支持链式调用）。 |
| `getWidgets()` | `List<String>` | 获取`widgets`属性的值。 |
| `setWidgets(List<String>)` | `QueryPanelValue` | 设置`widgets`属性的值，并返回当前对象实例（支持链式调用）。 |

### 3. 主要函数/方法

该文件中的所有核心查询逻辑都通过静态方法对外暴露，方便调用。

| 函数名 | 参数 | 返回值 | 功能描述 |
| :--- | :--- | :--- | :--- |
| `query(PanelContext ctx, String... ignoreWidgets)` | `ctx`: `PanelContext`实例； `ignoreWidgets`: 可变参数，表示要忽略的组件名称数组。 | `PanelValue` | 查询面板的所有值，并忽略指定的组件。默认获取最终值（`onlyGuiValue`为`false`）。 |
| `queryOnlyGuiValue(PanelContext ctx, String... ignoreWidgets)` | `ctx`: `PanelContext`实例； `ignoreWidgets`: 可变参数，表示要忽略的组件名称数组。 | `PanelValue` | 查询面板中所有值中**只被修改过**的部分，并忽略指定的组件。(`onlyGuiValue`为`true`)。 |
| `query(PanelContext ctx, boolean onlyGuiValue, String... ignoreWidgets)` | `ctx`: `PanelContext`实例； `onlyGuiValue`: 布尔值，是否只获取修改后的值； `ignoreWidgets`: 可变参数，表示要忽略的组件名称数组。 | `PanelValue` | 内部核心方法，构建`QueryPanelValue`对象，设置`onlyGuiValue`和`ignoreWidgets`，然后通过`ctx.callback()`发起查询。 |
| `queryTargets(PanelContext ctx, String... widgets)` | `ctx`: `PanelContext`实例； `widgets`: 可变参数，表示要查询的指定组件名称数组。 | `PanelValue` | 查询面板中**仅限于指定**的组件的值。默认获取最终值（`onlyGuiValue`为`false`）。 |
| `queryTargetsOnlyGuiValue(PanelContext ctx, String... widgets)` | `ctx`: `PanelContext`实例； `widgets`: 可变参数，表示要查询的指定组件名称数组。 | `PanelValue` | 查询面板中**仅限于指定**的组件中**只被修改过**的值。(`onlyGuiValue`为`true`)。 |
| `queryTargets(PanelContext ctx, boolean onlyGuiValue, String... widgets)` | `ctx`: `PanelContext`实例； `onlyGuiValue`: 布尔值，是否只获取修改后的值； `widgets`: 可变参数，表示要查询的指定组件名称数组。 | `PanelValue` | 内部核心方法，构建`QueryPanelValue`对象，设置`onlyGuiValue`和`widgets`，然后通过`ctx.callback()`发起查询。 |

### 4. 对外依赖与交互

`QueryPanelValue.java` 文件依赖于以下重要的外部库或项目内的其他类：

*   **`java.util.List`**: Java标准库，用于定义和操作组件名称列表（`ignoreWidgets`和`widgets`）。
*   **`com.leavay.common.util.ToolBasic`**: 来自 `leavay` 项目的通用工具类。
    *   **交互**: 使用 `ToolBasic.newArrayList(String... arr)` 方法将可变参数的字符串数组转换为 `ArrayList` 实例，方便赋值给 `List<String>` 类型的属性。
*   **`com.leavay.ms.tool.CmnUtil`**: 来自 `leavay` 项目的另一个通用工具类。
    *   **交互**: 使用 `CmnUtil.isObjectEmpty(Object obj)` 方法检查传入的组件名称数组（`ignoreWidgets`或`widgets`）是否为空或`null`，以避免不必要的列表创建。
*   **`fe.cmn.data.BasicAbility`**: 项目内定义的抽象能力基类。
    *   **交互**: `QueryPanelValue` 继承自 `BasicAbility`，这意味着它是一个可以被 `PanelContext` 识别并执行的“能力”或“命令”。泛型参数 `PanelValue` 表明此能力执行后预期返回的结果类型。
*   **`fe.cmn.panel.PanelContext`**: 项目内定义的面板上下文类。
    *   **交互**: 这是 `QueryPanelValue` 最核心的交互对象。`QueryPanelValue` 实例会被作为参数传递给 `PanelContext` 的 `callback(BasicAbility ability)` 方法。`PanelContext` 负责接收此能力对象，并根据其封装的参数（`ignoreWidgets`, `widgets`, `onlyGuiValue`）在内部执行实际的面板值获取逻辑，最终返回 `PanelValue` 对象。
*   **`fe.cmn.panel.PanelValue`**: 项目内定义的面板值封装类。
    *   **交互**: `QueryPanelValue` 执行后的预期返回类型。它很可能是一个包含多个组件名称及其对应值的Map或类似结构。
*   **`flutter.coder.annt.NullSafe`**: 一个自定义注解，可能与代码生成、静态分析或特定框架（如Flutter相关的代码生成工具）有关。
    *   **交互**: 标记 `onlyGuiValue` 属性为“空安全”。虽然`boolean`基本类型本身不能为`null`，这可能是一个通用标记，用于指示该字段在特定上下文中总是安全的，或在涉及到封装类型（如`Boolean`）时提供提示。

