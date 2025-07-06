作为一名资深的Java软件工程师，我对 `QueryTableRows.java` 文件进行了深入分析，旨在为AI编码助手提供一份清晰、结构化的技术知识库。

---

### 1. 文件核心功能
`QueryTableRows.java` 文件的主要职责是封装和提供一套用于**查询表格行数据（`TableRowDto`）**的机制。它继承自 `TableCallback`，表明它是一个回调（Callback）模式的实现，用于将特定的查询请求发送给底层的表格处理逻辑（通过 `PanelContext`）。

它在项目中扮演的角色是：
*   **查询请求封装器**: 将查询表格行所需的参数（如行ID、是否选中、需要查询的列）封装在一个对象中。
*   **查询操作入口**: 提供了一系列静态辅助方法（如 `query`, `queryOne`, `queryAll`, `querySelected`），简化了表格数据的获取。
*   **统一查询接口**: 作为 `TableCallback` 的一个具体实现，它使得前端或业务逻辑可以通过统一的 `PanelContext.callback()` 接口来获取表格数据，实现了查询逻辑与调用者的解耦。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class QueryTableRows` | `TableCallback<List<TableRowDto>>` | 封装表格行查询参数（行ID、是否选中、所需列），并作为回调对象，通过 `PanelContext` 执行实际的表格数据查询操作，最终返回 `List<TableRowDto>`。 |

#### 方法与属性详情

针对 `QueryTableRows` 类：

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `private static final long serialVersionUID` | `long` | Java序列化ID，用于版本控制。 |
| `List<String> rowIds` | `List<String>` | 私有属性，存储需要查询的表格行ID列表。 |
| `Boolean isSelected` | `Boolean` | 私有属性，标识是否只获取处于勾选选中状态的行。 |
| `List<String> columns` | `List<String>` | 私有属性，存储需要从 `TableRowDto` 中映射的列名，用于结果过滤或投影。 |
| `public QueryTableRows()` | 构造函数 | 无参构造函数。 |
| `public QueryTableRows(List<String> rowIds)` | 构造函数 | 带行ID列表参数的构造函数，设置 `rowIds`。 |
| `public QueryTableRows(Boolean isSelected)` | 构造函数 | 带是否选中状态参数的构造函数，设置 `isSelected`。 |
| `public QueryTableRows(Boolean isSelected, List<String> rowIds)` | 构造函数 | 带是否选中状态和行ID列表参数的构造函数。 |
| `public QueryTableRows(Boolean isSelected, String... rowIds)` | 构造函数 | 带是否选中状态和可变参数行ID的构造函数，将变长参数转换为 `List<String>`。 |
| `public List<String> getRowIds()` | `List<String>` | 获取当前查询对象中设置的行ID列表。 |
| `public QueryTableRows setRowIds(List<String> rowIds)` | `QueryTableRows` | 设置需要查询的行ID列表，并返回当前对象，支持链式调用。 |
| `public QueryTableRows setRowIds(String... rowIds)` | `QueryTableRows` | 设置需要查询的行ID（可变参数），将其转换为 `List<String>`，并返回当前对象，支持链式调用。 |
| `public Boolean getIsSelected()` | `Boolean` | 获取当前查询对象中设置的 `isSelected` 状态。 |
| `public QueryTableRows setIsSelected(Boolean isSelected)` | `QueryTableRows` | 设置是否获取选中状态的行，并返回当前对象，支持链式调用。 |
| `public List<String> getColumns()` | `List<String>` | 获取当前查询对象中设置的列名列表。 |
| `public QueryTableRows setColumns(List<String> columns)` | `QueryTableRows` | 设置需要查询的列名列表，并返回当前对象，支持链式调用。 |
| `public QueryTableRows setColumns(String... columns)` | `QueryTableRows` | 设置需要查询的列名（可变参数），将其转换为 `List<String>`，并返回当前对象，支持链式调用。 |

### 3. 主要函数/方法

除了上述的Getter/Setter和构造函数，`QueryTableRows` 类还提供了一系列重要的静态查询方法，作为便捷的入口：

| 函数名 | 参数 | 返回值 | 功能描述 |
| :--- | :--- | :--- | :--- |
| `public static List<TableRowDto> query` | `PanelContext context`, `List<String> rowIds`, `String... columns` | `List<TableRowDto>` | 根据指定的行ID列表和需要查询的列名，查询表格行数据。`isSelected` 默认设置为 `false` (不查询选中行)。 |
| `public static List<TableRowDto> query` | `PanelContext context`, `String... rowIds` | `List<TableRowDto>` | 根据指定的行ID（可变参数），查询表格行数据。`isSelected` 默认设置为 `false`。 |
| `public static TableRowDto queryOne` | `PanelContext context`, `String rowId`, `String... columns` | `TableRowDto` | 根据单个行ID和需要查询的列名，查询单个表格行数据。如果查询结果为空则返回 `null`。 |
| `public static List<TableRowDto> queryAll` | `PanelContext context`, `String... columns` | `List<TableRowDto>` | 查询表格中的所有行数据，可以指定需要返回的列。`isSelected` 默认设置为 `false`。 |
| `public static List<TableRowDto> querySelected` | `PanelContext context`, `String... columns` | `List<TableRowDto>` | 查询表格中所有处于勾选选中状态的行数据，可以指定需要返回的列。`isSelected` 明确设置为 `true`。 |

### 4. 对外依赖与交互

`QueryTableRows.java` 文件依赖于以下外部库或项目内部类：

*   **Java标准库**:
    *   `java.util.Arrays`: 用于将变长参数 (`String...`) 转换为 `List`。
    *   `java.util.List`: 泛型列表接口，用于处理行ID和列名集合。
    *   `java.util.stream.Collectors`: 用于将流（Stream）中的元素收集到集合中（例如 `Collectors.toList()`）。

*   **项目内部依赖**:
    *   `com.leavay.ms.tool.CmnUtil`: 这是一个内部工具类，其中 `CmnUtil.isObjectEmpty(lst)` 方法用于判断集合是否为空，在 `queryOne` 方法中避免空指针异常。
    *   `fe.cmn.panel.PanelContext`: **核心依赖**。这是整个框架的上下文对象，`QueryTableRows` 实例作为回调参数传递给 `context.callback(callback)` 方法，由 `PanelContext` 负责协调实际的表格数据查询逻辑。
    *   `fe.cmn.table.TableRowDto`: 表示表格中的一行数据的DTO（Data Transfer Object）。查询操作最终返回的就是这种对象的列表。
    *   `fe.cmn.table.ability.TableCallback`: `QueryTableRows` 的父类。它定义了表格操作回调的通用接口，使得 `QueryTableRows` 可以作为一种特定类型的回调被 `PanelContext` 处理。

**交互方式**:
`QueryTableRows` 通过创建自身实例并设置查询参数，然后将该实例传递给 `PanelContext` 的 `callback` 方法来发起查询请求。`PanelContext` 会根据回调对象的类型（即 `QueryTableRows`），执行相应的业务逻辑，访问数据源（可能是前端UI组件的数据模型或后端服务），获取 `TableRowDto` 列表，并将其返回给调用者。`CmnUtil` 则作为辅助工具，处理结果集的判断逻辑。

