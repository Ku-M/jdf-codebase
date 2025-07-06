### 1. 文件核心功能
`FormTableParam.java` 文件定义了一个参数数据传输对象（DTO），专门用于封装从前端表单界面向后端查询表格数据时所需的各种参数。它扩展了通用的 `TableParam`，在此基础上增加了与表单和高级查询相关的特定属性。

其主要职责包括：
*   **封装查询条件**: 收集表单ID、排序字段、排序模式、高级过滤条件（通过Nutz.dao的`SqlExpressionGroup`）、SQL相关参数及查询别名等。
*   **提供分页信息**: 继承并初始化了分页相关的参数（起始位置、页面大小、是否查询总数）。
*   **支持高级查询**: 允许通过`SqlExpressionGroup`构建复杂的动态SQL过滤条件。
*   **支持懒加载**: 提供了一个标志位用于控制嵌套详情数据是否在表单加载后进行懒查询。

它在整个项目中扮演的角色是作为前端与后端数据查询接口之间的桥梁，将前端用户在表单界面上选择的或输入的查询条件、排序要求以及分页需求，以结构化的方式传递给后端服务层，从而使后端能够据此构建数据库查询并返回相应的数据。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class FormTableParam` | `fe.util.component.param.TableParam` | 封装前端表单查询表格数据所需的所有参数，包括基础分页、排序、高级SQL过滤及表单模型ID等。 |

#### 方法与属性详情

**类: `FormTableParam`**

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `serialVersionUID` | `long` | Java序列化ID，用于版本控制。 |
| `formModelID` | `String` | 对应前端表单的模型ID，用于标识或区分不同的表单数据源。 |
| `orderColumn` | `String` | 指定数据结果集需要排序的列名。 |
| `orderMode` | `String` | 排序模式，默认为 "asc"（升序），可选 "desc"（降序）。 |
| `advFilter` | `org.nutz.dao.util.cri.SqlExpressionGroup` | 高级过滤条件组，Nutz.dao框架中的SQL表达式对象，用于构建复杂的WHERE子句。 |
| `withSqls` | `java.util.Map<String, String>` | 包含SQL查询相关的额外参数，键值对形式。 |
| `queryAlias` | `String` | 查询别名，可能用于多表联查或子查询的别名。 |
| `isLazyQueryCompoundField` | `boolean` | 标记是否在查看详情时，嵌套的复合字段数据采取懒加载模式（即在表单加载完成后再查询）。 |
| `FormTableParam()` | 构造方法 | 初始化分页相关的默认值：起始位置`setStartPos(0)`，页面大小`setPageSize(20)`，查询总数`setQueryCount(true)`，并设置默认的页面大小选项。 |
| `getFormModelID()` | `String` | 获取表单模型ID。 |
| `setFormModelID(String formModelID)` | `FormTableParam` | 设置表单模型ID，并返回当前对象（链式调用）。 |
| `getAdvFilter()` | `SqlExpressionGroup` | 获取高级过滤条件组。 |
| `setAdvFilter(SqlExpressionGroup advFilter)` | `FormTableParam` | 设置高级过滤条件组，并返回当前对象（链式调用）。 |
| `getOrderColumn()` | `String` | 获取排序的列名。 |
| `setOrderColumn(String orderColumn)` | `FormTableParam` | 设置排序的列名，并返回当前对象（链式调用）。 |
| `getOrderMode()` | `String` | 获取排序模式。 |
| `setOrderMode(String orderMode)` | `FormTableParam` | 设置排序模式，并返回当前对象（链式调用）。 |
| `getWithSqls()` | `Map<String, String>` | 获取SQL查询相关的额外参数。 |
| `setWithSqls(Map<String, String> withSqls)` | `FormTableParam` | 设置SQL查询相关的额外参数，并返回当前对象（链式调用）。 |
| `getQueryAlias()` | `String` | 获取查询别名。 |
| `setQueryAlias(String queryAlias)` | `FormTableParam` | 设置查询别名，并返回当前对象（链式调用）。 |
| `isLazyQueryCompoundField()` | `boolean` | 判断是否开启复合字段的懒加载。 |
| `setLazyQueryCompoundField(boolean isLazyQueryCompoundField)` | `FormTableParam` | 设置复合字段的懒加载标志位，并返回当前对象（链式调用）。 |

### 3. 主要函数/方法 (如果适用)
此文件主要定义了一个POJO（Plain Old Java Object），封装了数据属性和对应的getter/setter方法，不包含独立的工具类函数。

### 4. 对外依赖与交互

*   **`java.util.Map`**:
    *   **依赖**: `FormTableParam` 内部使用 `Map<String, String>` 类型的 `withSqls` 字段来存储键值对形式的SQL相关参数。
    *   **交互**: 允许将一组动态的、可扩展的SQL参数传递给后端，例如存储一些特定的业务规则标识或附加的查询上下文信息。

*   **`org.nutz.dao.util.cri.SqlExpressionGroup`**:
    *   **依赖**: 这是Nutz.dao ORM框架中的一个核心类，用于构建复杂和动态的SQL条件表达式。`FormTableParam` 持有该类型的 `advFilter` 字段。
    *   **交互**: 表明后端的数据访问层很可能使用了Nutz.dao框架进行数据库操作。前端通过设置 `advFilter` 属性，将复杂的查询逻辑（如多条件AND/OR组合、范围查询等）以Nutz.dao原生的表达式形式传递给后端，后端可以直接使用这个`SqlExpressionGroup`对象来构建并执行数据库查询，避免了手动拼接SQL字符串，提高了代码的安全性和可维护性。

*   **`fe.util.component.param.TableParam`**:
    *   **依赖**: `FormTableParam` 继承自项目内部定义的 `TableParam` 类。
    *   **交互**: `TableParam` 应该是系统中所有表格数据查询参数的基类，定义了通用的分页、排序等基础属性和方法。`FormTableParam` 通过继承，复用了这些基础能力，并在此基础上增加了针对“表单关联表格”场景的特定参数，体现了良好的模块化和代码复用。这意味着在处理任何表格数据查询时，都可以期望其参数对象继承自 `TableParam`。

