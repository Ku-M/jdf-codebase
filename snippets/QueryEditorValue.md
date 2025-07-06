### 1. 文件核心功能
`QueryEditorValue.java` 文件的主要职责是定义一个通用能力（Ability），用于从前端面板（Panel）中的特定UI编辑器组件（widget）获取其当前值。它封装了与UI框架进行交互以查询组件值的逻辑，并提供了一系列静态辅助方法，方便地将查询结果转换为不同的数据类型，如String、double、long、int，以及特定数据结构（如PairDto的key）。

它在整个项目中扮演的角色是：
*   **桥梁作用**: 作为UI层与业务逻辑层之间获取UI组件值的桥梁。业务逻辑可以通过调用此能力来获取UI上的数据，而无需直接操作UI组件。
*   **数据查询封装**: 提供统一的API来查询各种类型的编辑器组件的值，简化了数据获取的复杂性。
*   **状态控制**: 允许指定是否只获取用户修改过的值，这对于区分初始化值和用户输入值至关重要。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :------ | :--- |
| `public class QueryEditorValue` | `BasicAbility<CsonPojo>` | 定义一个可被调用的能力，用于查询前端面板中指定UI编辑器组件的当前值。它包含查询参数（`widgetId` 和 `onlyGuiValue`），并负责通过 `PanelContext` 发起实际的查询回调。 |

#### 方法与属性详情

**类: `QueryEditorValue`**

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `serialVersionUID` | `long` | Java序列化ID，确保类的兼容性。 |
| `@NullSafe String widgetId` | `String` | 需要查询值的UI编辑器组件的唯一标识符（ID）。该字段被 `@NullSafe` 注解标记，可能用于空安全检查。 |
| `@NullSafe boolean onlyGuiValue` | `boolean` | **查询模式标志**。<br> - `false`（默认）：获取界面最终的值，即修改后的值和初始化值二选一（通常是最终显示的值）。<br> - `true`：只获取界面被用户做过动作或修改后的值。如果组件值未被修改，则可能返回 `null`。该字段被 `@NullSafe` 注解标记，可能用于空安全检查。 |
| `getWidgetId()` | `String` | 返回 `widgetId` 属性的值。 |
| `setWidgetId(String widgetId)` | `QueryEditorValue` | 设置 `widgetId` 属性的值，并返回当前 `QueryEditorValue` 实例，支持链式调用。 |
| `isOnlyGuiValue()` | `boolean` | 返回 `onlyGuiValue` 属性的值。 |
| `setOnlyGuiValue(boolean onlyGuiValue)` | `QueryEditorValue` | 设置 `onlyGuiValue` 属性的值，并返回当前 `QueryEditorValue` 实例，支持链式调用。 |

### 3. 主要函数/方法 (静态辅助方法)

`QueryEditorValue` 类还提供了一系列静态辅助方法，方便外部调用者查询并获取特定数据类型的组件值。

| 函数名 | 参数 | 返回值 | 功能描述 |
| :--- | :--- | :--- | :--- |
| `public static Object query(PanelContext ctx, String widgetId)` | `ctx`: `PanelContext` <br> `widgetId`: `String` | `Object` | 使用默认的 `onlyGuiValue=false`（即获取最终值）查询指定 `widgetId` 的组件值。它内部调用了重载的 `query` 方法。 |
| `public static Object query(PanelContext ctx, boolean onlyGuiValue, String widgetId)` | `ctx`: `PanelContext` <br> `onlyGuiValue`: `boolean` <br> `widgetId`: `String` | `Object` | 核心查询方法。创建一个 `QueryEditorValue` 实例，设置 `widgetId` 和 `onlyGuiValue`，然后通过 `PanelContext` 的 `callback` 方法发起实际的UI组件值查询。返回值为 `Object`，需要调用方进行类型转换。 |
| `public static String queryString(PanelContext ctx, String widgetId)` | `ctx`: `PanelContext` <br> `widgetId`: `String` | `String` | 调用 `query` 方法获取组件值，并将其强制转换为 `String` 类型返回。 |
| `public static double queryDouble(PanelContext ctx, String widgetId)` | `ctx`: `PanelContext` <br> `widgetId`: `String` | `double` | 调用 `query` 方法获取组件值，然后使用 `ToolUtilities.getDouble()` 方法将其转换为 `double` 类型返回。 |
| `public static long queryLong(PanelContext ctx, String widgetId)` | `ctx`: `PanelContext` <br> `widgetId`: `String` | `long` | 调用 `query` 方法获取组件值，然后使用 `ToolUtilities.getLong()` 方法将其转换为 `long` 类型返回。 |
| `public static int queryInt(PanelContext ctx, String widgetId)` | `ctx`: `PanelContext` <br> `widgetId`: `String` | `int` | 调用 `query` 方法获取组件值，然后使用 `ToolUtilities.getInteger()` 方法将其转换为 `int` 类型返回。 |
| `public static Object queryPairKey(PanelContext ctx, String widgetId)` | `ctx`: `PanelContext` <br> `widgetId`: `String` | `Object` | 调用 `query` 方法获取组件值，期望返回一个 `PairDto` 对象。如果返回的是 `PairDto`，则提取并返回其 `key` 值；否则返回 `null`。 |

### 4. 对外依赖与交互

`QueryEditorValue.java` 文件依赖于以下外部库或项目内部的其他类，并通过它们与系统的其他部分进行交互：

*   **`package fe.cmn.panel.ability;`**:
    *   表明该类位于 `fe.cmn.panel.ability` 包中，暗示它是一个与前端公共面板能力相关的组件。
*   **`import com.leavay.common.util.ToolUtilities;`**:
    *   导入了 `ToolUtilities` 工具类，用于在 `queryDouble`、`queryLong` 和 `queryInt` 等方法中进行数据类型转换。这表明系统可能有一个公共的工具库来处理常见的类型转换操作。
*   **`import cson.core.CsonPojo;`**:
    *   导入了 `CsonPojo` 类。`QueryEditorValue` 继承自 `BasicAbility<CsonPojo>`，这意味着 `CsonPojo` 是能力执行结果的通用数据类型或者作为能力的输入/输出载体。`Cson` 可能代表“Compact JSON”或某种自定义的序列化格式。
*   **`import fe.cmn.data.BasicAbility;`**:
    *   导入了 `BasicAbility` 抽象类。`QueryEditorValue` 继承自 `BasicAbility`，这表明它遵循了项目中定义的一种“能力”模式，即某种可被统一管理和调用的操作单元。这使得系统能够以通用的方式触发和处理各种前端操作。
*   **`import fe.cmn.data.PairDto;`**:
    *   导入了 `PairDto` 数据传输对象。在 `queryPairKey` 方法中被使用，表明某些编辑器组件的值可能以键值对的形式存在，并且系统支持直接从这种结构中提取 `key` 值。
*   **`import fe.cmn.panel.PanelContext;`**:
    *   导入了 `PanelContext` 类。这是最核心的交互点。所有的查询操作都必须通过 `PanelContext` 实例的 `callback` 方法来执行。`PanelContext` 负责提供执行能力所需的运行时上下文，例如访问UI组件、调用底层框架API等。这通常是前端面板与后端或服务层之间进行通信的桥梁。
*   **`import flutter.coder.annt.NullSafe;`**:
    *   导入了 `NullSafe` 注解。该注解可能用于编译时或运行时检查，以确保被标记的字段（`widgetId`, `onlyGuiValue`）不会出现空指针异常，从而提高代码的健壮性。这可能是一个自定义的或第三方库提供的注解。

**交互方式总结**:
`QueryEditorValue` 实例作为一种 `BasicAbility`，被构建并传入 `PanelContext` 的 `callback` 方法。`PanelContext` 负责解释这个 `Ability` 的含义（即查询一个编辑器组件的值），然后在UI框架内部执行相应的操作（例如，根据 `widgetId` 找到对应的UI组件并获取其值），最后将获取到的值作为 `callback` 的返回值返回。`ToolUtilities` 则用于对这个原始值进行后续的类型转换。

