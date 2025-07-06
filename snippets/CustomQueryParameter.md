### 1. 文件核心功能
`CustomQueryParameter.java` 文件定义了一个基础类，用于封装和管理在自定义查询界面中使用的各种查询参数。它的主要职责是：

1.  **参数标准化与抽象**: 提供一套标准化的机制来存储和获取查询条件 (`Cnd`)、分页信息 (`pageNo`, `pageSize`)、原始查询SQL (`querySql`)、权限参数 (`privilegeParam`)、默认表达式 (`defaultExpr`) 以及界面过滤器 (`filter`)。
2.  **运行时参数管理**: 利用 `IDCRuntimeContext` 作为中央存储，通过静态方法将这些参数设置到运行时上下文中，并通过实例方法从上下文中获取这些参数，确保参数在整个请求生命周期内可访问。
3.  **提高可维护性**: 将复杂的查询参数逻辑集中管理，避免在各个业务逻辑中重复定义和传递参数，从而提高代码的可读性和可维护性。

它在整个项目中扮演着**查询参数的数据载体和管理器的角色**，是构建灵活、可配置查询界面的基础组件。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class CustomQueryParameter` | `BaseFeActionParameter` | 作为自定义查询界面的基类参数，封装了所有与查询相关的数据（如查询条件、分页信息、SQL、权限、过滤器等）。它提供静态方法将这些参数设置到运行时上下文 `IDCRuntimeContext` 中，并提供实例方法从上下文中检索这些参数。 |

#### 方法与属性详情

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `serialVersionUID` | `long` | Java序列化版本UID。 |
| `FeActionParameter_Filter` | `public final static String` | 定义一个字符串常量，作为在 `IDCRuntimeContext` 中存储 `FilterDto` 参数的键。 |
| `FeActionParameter_Cnd` | `public final static String` | 定义一个字符串常量，作为在 `IDCRuntimeContext` 中存储 `Cnd` 查询条件参数的键。 |
| `FeActionParameter_PageNo` | `public final static String` | 定义一个字符串常量，作为在 `IDCRuntimeContext` 中存储页码参数的键。 |
| `FeActionParameter_PageSize` | `public final static String` | 定义一个字符串常量，作为在 `IDCRuntimeContext` 中存储分页大小参数的键。 |
| `FeActionParameter_QuerySql` | `public final static String` | 定义一个字符串常量，作为在 `IDCRuntimeContext` 中存储查询SQL参数的键。 |
| `FeActionParameter_PrivilegeParam` | `public final static String` | 定义一个字符串常量，作为在 `IDCRuntimeContext` 中存储结果集权限参数的键。 |
| `FeActionParameter_DefaultExpr` | `public final static String` | 定义一个字符串常量，作为在 `IDCRuntimeContext` 中存储默认过滤表达式的键。 |
| `prepareCustomQueryParameter(IDCRuntimeContext rtx, Cnd cnd, int pageNo, int pageSize)` | `void` | 静态方法，用于向运行时上下文 `rtx` 中设置基本的查询条件 `cnd`、页码 `pageNo` 和分页大小 `pageSize`。 |
| `prepareCustomQueryParameter(IDCRuntimeContext rtx, String querySql, ResultSetQueryParam privilegeParam, SqlExpressionGroup defaultExpr, Cnd cnd, int pageNo, int pageSize, FilterDto filter)` | `void` | 静态方法（重载），用于向运行时上下文 `rtx` 中设置所有可能的查询参数，包括查询SQL、权限参数、默认表达式、查询条件、页码、分页大小和界面过滤参数。 |
| `getCnd()` | `Cnd` | 从运行时上下文 `IDCRuntimeContext` 中获取并返回组装好的 `Nutz.Dao` 查询条件对象。 |
| `getPageNo()` | `Integer` | 从运行时上下文 `IDCRuntimeContext` 中获取并返回查询页码。 |
| `getPageSize()` | `Integer` | 从运行时上下文 `IDCRuntimeContext` 中获取并返回分页大小。 |
| `getQuerySql()` | `String` | 从运行时上下文 `IDCRuntimeContext` 中获取并返回组装好的查询SQL字符串。 |
| `getPrivilegeParam()` | `ResultSetQueryParam` | 从运行时上下文 `IDCRuntimeContext` 中获取并返回结果集查询的权限参数对象。 |
| `getDefaultExpr()` | `SqlExpressionGroup` | 从运行时上下文 `IDCRuntimeContext` 中获取并返回默认的过滤条件表达式。 |
| `getFilterDto()` | `FilterDto` | 从运行时上下文 `IDCRuntimeContext` 中获取并返回界面上的过滤参数DTO。 |

### 3. 主要函数/方法 (如果适用)
此文件中的所有方法都属于 `CustomQueryParameter` 类，没有独立的工具类函数。

### 4. 对外依赖与交互

`CustomQueryParameter.java` 文件依赖于多个外部库和项目内部的类，并与它们进行紧密交互：

*   **`org.nutz.dao.Cnd`**: Nutz.Dao ORM框架中的条件构造器。`CustomQueryParameter` 用它来表示和传递复杂的数据库查询条件。
*   **`org.nutz.dao.util.cri.SqlExpressionGroup`**: Nutz.Dao 中用于组合SQL表达式的类。用于封装默认的或更复杂的过滤条件。
*   **`cell.gpf.dc.runtime.IDCRuntimeContext`**: 这是一个核心依赖。`CustomQueryParameter` 通过 `IDCRuntimeContext` 的 `setParam` 方法将各种查询参数存储起来，并通过 `getParam` 方法检索它们。这意味着 `IDCRuntimeContext` 充当了一个请求范围内的参数容器，实现了参数的传递和共享。
*   **`gpf.dc.basic.dto.privilege.ResultSetQueryParam`**: 项目内部定义的DTO，用于封装结果集查询所需的权限相关参数。
*   **`gpf.dc.basic.param.view.dto.FilterDto`**: 项目内部定义的DTO，可能用于封装前端界面传递过来的通用过滤参数。
*   **`gpf.dc.basic.param.view.BaseFeActionParameter`**: `CustomQueryParameter` 的父类。通过继承，`CustomQueryParameter` 能够访问父类中可能定义的公共方法，例如 `getRtx()` 方法，该方法负责获取 `IDCRuntimeContext` 实例。这表明父类可能定义了视图层参数处理的通用模式。

**交互方式**:
该类通过静态方法接受 `IDCRuntimeContext` 实例，将不同的查询参数对象（如 `Cnd`, `ResultSetQueryParam`, `FilterDto` 等）作为键值对存入其中。在业务逻辑需要时，通过其自身的实例方法（通过继承 `BaseFeActionParameter` 获取的 `getRtx()` 方法）从 `IDCRuntimeContext` 中取出并强转为相应的类型使用。这种设计模式使得查询参数的准备和获取变得集中和标准化。

