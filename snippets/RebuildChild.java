我们将对 `RebuildChild.java` 文件进行深入分析，以帮助AI编码助手理解其功能和在项目中的作用。

---

### 1. 文件核心功能
`RebuildChild.java` 文件的核心功能是**封装并触发前端界面中子组件（Widget或Panel）的重建或刷新操作**。它作为一种“能力”（Ability）或命令对象，将需要重建的子组件元数据 (`WidgetDto`) 以及重建时的行为参数（如是否清理当前GUI值、是否重建计时器）传递给UI渲染上下文 (`PanelContext`)。

它在项目中扮演的角色是：
*   **UI操作命令**: 作为一个具体的命令，定义了如何请求UI框架重建特定的子组件。
*   **前端后端交互桥梁**: 通常这类 `Ability` 对象会在后端生成并传递给前端，前端根据其内容执行相应的UI操作。
*   **统一重建入口**: 提供多种静态 `rebuild` 方法作为便捷的API，允许调用方以不同粒度控制重建行为，无需直接构造 `RebuildChild` 实例。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class RebuildChild` | `BasicBatchAbility<Void>` | 定义了用于重建或替换前端界面中子组件（Widget或Panel）的“能力”或命令。它封装了重建所需的所有参数，并通过 `PanelContext` 发送给前端执行。不能用于重建自身，仅限于子组件。 |

#### 方法与属性详情

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `serialVersionUID` | `long` | Java序列化ID。 |
| `newWidgets` | `List<WidgetDto>` | **属性**: 存储需要被重建或替换的新子组件的元数据列表。这些 `WidgetDto` 定义了组件的类型、属性等信息。 |
| `clearGuiValue` | `boolean` | **属性**: 控制是否在重建时清理界面上当前输入框等组件的值。默认为 `true` (清理)。仅在组件是编辑器时有意义。 |
| `rebuildTimers` | `boolean` | **属性**: 控制是否在重建时同时重建与组件相关的计时器。默认为 `true` (重建)。 |
| `getNewWidgets()` | `List<WidgetDto>` | 获取 `newWidgets` 属性的值。 |
| `setNewWidgets(List<WidgetDto>)` | `RebuildChild` | 设置 `newWidgets` 属性的值，并返回当前对象，支持链式调用（fluent style）。 |
| `isClearGuiValue()` | `boolean` | 获取 `clearGuiValue` 属性的值。 |
| `setClearGuiValue(boolean)` | `RebuildChild` | 设置 `clearGuiValue` 属性的值，并返回当前对象，支持链式调用（fluent style）。 |
| `isRebuildTimers()` | `boolean` | 获取 `rebuildTimers` 属性的值。 |
| `setRebuildTimers(boolean)` | `RebuildChild` | 设置 `rebuildTimers` 属性的值，并返回当前对象，支持链式调用（fluent style）。 |
| `setDeferErrors(Boolean)` | `RebuildChild` | 重写父类方法，设置是否延迟处理错误。也支持链式调用。 |

### 3. 主要函数/方法

该文件主要提供了一系列静态 `rebuild` 方法作为便捷的入口点，用于创建并分发 `RebuildChild` 命令。

| 函数名 | 参数 | 返回值 | 功能描述 |
| :--- | :--- | :--- | :--- |
| `rebuild(PanelContext ctx, WidgetDto ... newWidget)` | `PanelContext ctx`, `WidgetDto ... newWidget` | `void` | 创建一个 `RebuildChild` 命令，设置新的子组件列表，默认清理界面旧值并重建计时器，然后通过 `ctx.callback()` 触发重建。 |
| `rebuild(PanelContext ctx, List<WidgetDto> newWidget)` | `PanelContext ctx`, `List<WidgetDto> newWidget` | `void` | 功能同上，但接受 `WidgetDto` 列表作为参数。 |
| `rebuildWithGuiValue(PanelContext ctx, WidgetDto ... newWidget)` | `PanelContext ctx`, `WidgetDto ... newWidget` | `void` | 创建一个 `RebuildChild` 命令，设置新的子组件列表，**明确设置不清空界面旧值 (`clearGuiValue = false`)**，并重建计时器，然后通过 `ctx.callback()` 触发重建。 |
| `rebuildWithGuiValue(PanelContext ctx, List<WidgetDto> newWidget)` | `PanelContext ctx`, `List<WidgetDto> newWidget` | `void` | 功能同上，但接受 `WidgetDto` 列表作为参数。 |
| `rebuild(PanelContext ctx, Boolean clearGuiValue, Boolean rebuildTimers, List<WidgetDto> newWidget)` | `PanelContext ctx`, `Boolean clearGuiValue`, `Boolean rebuildTimers`, `List<WidgetDto> newWidget` | `void` | 提供完全控制的 `rebuild` 方法，允许调用方显式指定是否清理界面值和是否重建计时器。 |
| `rebuild(PanelContext ctx, Boolean clearGuiValue, Boolean rebuildTimers, List<WidgetDto> newWidget, boolean deferErrors)` | `PanelContext ctx`, `Boolean clearGuiValue`, `Boolean rebuildTimers`, `List<WidgetDto> newWidget`, `boolean deferErrors` | `void` | 提供更高级的控制，除了清理界面值和重建计时器外，还允许指定是否延迟处理错误。 |

### 4. 对外依赖与交互

这个文件与以下重要的外部库或项目内的其他类进行交互：

*   **`java.util.List`**: Java标准库，用于处理组件列表。
*   **`com.leavay.common.util.ToolUtilities`**:
    *   **依赖**: 导入并使用了 `ToolUtilities.newArrayList()` 方法。
    *   **交互**: 表明 `leavay` 项目内部有一个通用的工具类库，用于提供便捷的集合操作等。
*   **`fe.cmn.panel.BasicBatchAbility`**:
    *   **依赖**: `RebuildChild` 的父类。
    *   **交互**: `RebuildChild` 继承了 `BasicBatchAbility` 的通用结构和行为，表明它是一个批处理能力（Batch Ability）的一部分。这暗示了系统中存在一个处理这类“能力”的框架。
*   **`fe.cmn.panel.PanelContext`**:
    *   **依赖**: 关键的上下文对象。
    *   **交互**: `RebuildChild` 实例通过 `ctx.callback(callback)` 方法被传递给 `PanelContext`。这意味着 `PanelContext` 是一个调度器或处理器，负责接收并执行这些UI相关的“能力”（包括 `RebuildChild`），从而在前端触发实际的组件重建逻辑。
*   **`fe.cmn.widget.WidgetDto`**:
    *   **依赖**: 表示UI组件的元数据对象。
    *   **交互**: `RebuildChild` 通过 `newWidgets` 属性接收 `WidgetDto` 列表，这些 `WidgetDto` 包含了前端组件重建所需的所有信息（如组件ID、类型、属性等）。
*   **`flutter.coder.annt.NullSafe`**:
    *   **依赖**: 自定义注解，可能用于代码生成、静态分析或运行时检查。
    *   **交互**: 标记了 `newWidgets`, `clearGuiValue`, `rebuildTimers` 属性，暗示了代码中对空值安全性的关注，可能与代码质量工具、IDE插件或某种代码生成框架集成。这可能表明该系统在某种程度上与Flutter或某种“coder”工具链相关联。

