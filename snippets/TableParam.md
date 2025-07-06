### 1. 文件核心功能

`TableParam.java` 文件定义了一个数据传输对象（DTO），主要用于封装在前端或后端进行表格数据查询和展示时所需的各种参数。它继承自 `BasicTableParam`，在此基础上扩展了分页、过滤关键词以及页面大小选择等功能。其核心职责是：

*   **封装查询参数**：集中管理表格数据查询所需的参数，如起始位置、每页大小、是否查询总数和过滤关键词等。
*   **支持分页功能**：提供设置和获取分页参数（`startPos`, `pageSize`, `queryCount`, `pageSizes`）的能力。
*   **提供链式调用（Fluent API）**：所有setter方法都返回当前 `TableParam` 实例，方便参数的链式设置。
*   **继承基础表格设置**：通过继承 `BasicTableParam`，能够利用父类提供的基础表格设置（如 `TableSetting`）。

在整个项目中，`TableParam` 扮演着数据查询请求的载体角色，使得表格数据相关的接口调用更加规范和易于管理。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class TableParam` | `BasicTableParam` | 封装表格数据查询所需的分页、过滤关键词等参数，并提供链式调用的API。它定义了表格分页的默认行为和可选项。 |

#### 方法与属性详情

**类：`TableParam`**

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `defaultPageSizes` | `public final static List<Integer>` | 预定义的默认页面大小列表，通常用于前端分页组件的选项。 |
| `serialVersionUID` | `private static final long` | 序列化版本UID，表明该类（或其父类）是可序列化的。 |
| `filtersKeyWord` | `String` | 用于数据过滤的关键词。 |
| `startPos` | `Integer` | 查询结果的起始位置，用于分页。 |
| `pageSize` | `Integer` | 每页显示的记录数，用于分页。 |
| `queryCount` | `boolean` | 指示是否需要查询总记录数。 |
| `pageSizes` | `List<Integer>` | 当前 `TableParam` 实例的页面大小选项列表，如果为 `null` 则可能使用默认值。 |
| `public TableParam()` | 构造函数 | 构造方法，在实例创建时将 `getSetting().setIsAllowRefresh(true)`，表明默认允许表格刷新。 |
| `public TableParam defaultParam()` | `TableParam` | 将当前 `TableParam` 实例设置为默认分页参数（`startPos=0`, `pageSize=20`, `queryCount=true`），并使用 `defaultPageSizes`。返回 `this` 实现链式调用。 |
| `public String getFiltersKeyWord()` | `String` | 获取过滤关键词。 |
| `public TableParam setFiltersKeyWord(String filtersKeyWord)` | `TableParam` | 设置过滤关键词，并返回当前实例以支持链式调用。 |
| `public Integer getStartPos()` | `Integer` | 获取查询起始位置。 |
| `public TableParam setStartPos(Integer startPos)` | `TableParam` | 设置查询起始位置，并返回当前实例以支持链式调用。 |
| `public Integer getPageSize()` | `Integer` | 获取每页记录数。 |
| `public TableParam setPageSize(Integer pageSize)` | `TableParam` | 设置每页记录数，并返回当前实例以支持链式调用。 |
| `public boolean isQueryCount()` | `boolean` | 检查是否需要查询总记录数。 |
| `public TableParam setQueryCount(boolean queryCount)` | `TableParam` | 设置是否需要查询总记录数，并返回当前实例以支持链式调用。 |
| `public List<Integer> getPageSizes()` | `List<Integer>` | 获取页面大小选项列表。 |
| `public TableParam setPageSizes(List<Integer> pageSizes)` | `TableParam` | 设置页面大小选项列表，并返回当前实例以支持链式调用。 |
| `@Override public TableParam setWritable(boolean isWritable)` | `TableParam` | 重写父类方法，设置可写性，并返回 `TableParam` 类型实例。 |
| `@Override public TableParam setSetting(TableSetting setting)` | `TableParam` | 重写父类方法，设置表格配置，并返回当前实例以支持链式调用。 |

### 3. 主要函数/方法 (如果适用)

该文件不包含独立的工具类方法，所有功能都封装在 `TableParam` 类内部作为其成员方法。

### 4. 对外依赖与交互

*   **`fe.util.component.dto.TableSetting`**:
    *   **依赖类型**: 导入了一个数据传输对象（DTO）。
    *   **交互方式**: `TableParam` 通过其父类 `BasicTableParam` 的 `getSetting()` 和 `setSetting()` 方法与 `TableSetting` 对象进行交互。这表明 `TableParam` 不仅处理查询参数，还可能与表格的显示或行为设置相关联，例如在构造函数中通过 `getSetting().setIsAllowRefresh(true)` 来设置表格的刷新行为。
*   **`fe.util.component.param.BasicTableParam`**:
    *   **依赖类型**: 继承关系。`TableParam` 扩展了 `BasicTableParam` 的功能。
    *   **交互方式**: `TableParam` 继承了 `BasicTableParam` 的属性和方法，并通过 `@Override` 注解重写了 `setWritable` 和 `setSetting` 方法，以返回 `TableParam` 自身，保持链式调用的连贯性。这意味着 `BasicTableParam` 提供了表格参数的基础结构和通用功能，而 `TableParam` 在此基础上增加了更具体的查询和分页参数。
*   **`java.util.Arrays`**:
    *   **依赖类型**: Java标准库工具类。
    *   **交互方式**: 用于方便地创建 `defaultPageSizes` 列表，通过 `Arrays.asList()` 方法将数组转换为列表。
*   **`java.util.List`**:
    *   **依赖类型**: Java集合框架接口。
    *   **交互方式**: 用于定义和操作页面大小的集合 (`defaultPageSizes`, `pageSizes`)。

总体而言，`TableParam` 与其父类 `BasicTableParam` 以及 `TableSetting` 形成了紧密的协作关系，共同构建了一套用于管理表格查询参数和设置的体系。

