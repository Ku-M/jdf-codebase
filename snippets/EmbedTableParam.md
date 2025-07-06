这是一个文件级的技术知识库，用于分析 `EmbedTableParam.java`。

---

### 1. 文件核心功能
`EmbedTableParam.java` 文件定义了一个泛型参数类 `EmbedTableParam<T>`。它的核心职责是封装和传递一个数据列表 (`List<T> rows`) 以及与表格相关的配置信息，以便在UI组件（如表格）中直接“嵌入”或显示这些数据。它继承自 `BasicTableParam`，扩展了其功能，使其能够携带具体的业务数据。此文件在项目中扮演数据传输对象（DTO）的角色，特别用于那些数据源直接作为参数提供给表格组件的场景。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class EmbedTableParam<T>` | `BasicTableParam` | 一个泛型参数类，用于封装并传递一个数据行列表 (`List<T>`) 以及表格配置，方便在表格组件中直接嵌入并展示数据。它提供了链式调用的方法来设置其属性。 |

#### 方法与属性详情

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `private static final long serialVersionUID` | `long` | 用于序列化的版本UID，确保序列化和反序列化时的兼容性。 |
| `List<T> rows` | `java.util.List<T>` | 存储要嵌入到表格中的数据行列表。`T` 是泛型，代表数据行的具体类型。 |
| `public static <T> EmbedTableParam<T> create(List<T> rows)` | `EmbedTableParam<T>` | 静态工厂方法，用于便捷地创建 `EmbedTableParam` 实例。它在创建时默认将 `isOpShowPopToast` 设置为 `false`，并链式调用 `setRows` 方法初始化数据。 |
| `public List<T> getRows()` | `java.util.List<T>` | 获取当前参数对象中存储的数据行列表。 |
| `public EmbedTableParam<T> setRows(List<T> rows)` | `EmbedTableParam<T>` | 设置数据行列表。该方法返回当前对象实例，支持链式调用。 |
| `public EmbedTableParam<T> setWritable(boolean isWritable)` | `EmbedTableParam<T>` | 设置表格是否可写（此属性可能继承自 `BasicTableParam`）。该方法返回当前对象实例，支持链式调用。 |
| `public EmbedTableParam setSetting(TableSetting setting)` | `EmbedTableParam` | 设置表格的详细配置信息（此属性可能继承自 `BasicTableParam`）。该方法返回当前对象实例，支持链式调用。注意：此方法返回类型未保留泛型 `<T>`。 |

### 3. 主要函数/方法 (如果适用)
该文件中的所有方法都是 `EmbedTableParam` 类的一部分（包括静态工厂方法和实例方法），没有独立的工具函数。

### 4. 对外依赖与交互

*   **`fe.util.component.dto.TableSetting`**:
    *   **依赖类型**: 导入并使用了 `TableSetting` 类。
    *   **交互方式**: `EmbedTableParam` 包含一个 `TableSetting` 类型的属性（通过继承或直接定义），用于传递表格的各种配置，如是否显示操作弹窗提示 (`isOpShowPopToast`) 等。这使得表格组件能够根据这些设置来渲染和行为。

*   **`fe.util.component.param.BasicTableParam`**:
    *   **依赖类型**: `EmbedTableParam` 继承自 `BasicTableParam`。
    *   **交互方式**: `EmbedTableParam` 扩展了 `BasicTableParam` 的功能，继承了其基础属性和方法（例如可能继承了 `isWritable` 和 `setting` 属性）。通过继承，`EmbedTableParam` 可以在保持与基础表格参数兼容性的同时，增加特有的数据嵌入功能。

*   **`java.util.List`**:
    *   **依赖类型**: Java标准库类。
    *   **交互方式**: 作为核心数据结构，用于存储和传递泛型数据行 `T` 的集合。`EmbedTableParam` 的主要目的是封装这个列表并将其传递给其他组件。

**总结交互**: `EmbedTableParam` 作为一个参数对象，封装了具体的业务数据列表 (`rows`) 和表格的通用配置 (`setting`, `isWritable`)。它通过继承 `BasicTableParam` 获得基础能力，并通过包含 `TableSetting` 对象来传递更详细的配置。最终，它会被传递给一个需要渲染表格的组件，该组件将利用其内部的 `rows` 和 `setting` 信息来展示数据。

