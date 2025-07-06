### 1. 文件核心功能
`TreeParam.java` 文件定义了一个参数对象，专门用于封装和传递配置树形组件（例如，UI界面中的树形结构或后端查询树状数据所需的条件）所需的所有参数。它在一个通用的组件参数库中，为实现树状数据的展示、查询、过滤和交互提供了一个标准化的数据模型。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class TreeParam` | `BaseWidgetParam` (并与 `WidgetParam` 相关) | 封装树形组件（如UI树、数据树）的查询、配置和行为参数。这些参数包括树的使用场景、查询的根节点信息、搜索关键字、懒加载设置以及复选框的行为模式等。它作为一个数据传输对象（DTO）或配置对象在不同层之间传递。 |

#### 方法与属性详情

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `private static final long serialVersionUID` | `long` | 用于Java序列化的版本UID，确保在序列化/反序列化过程中类的兼容性。 |
| `String scene` | `String` | 树的使用场景或业务类型标识，用于区分不同业务背景下的树状数据。 |
| `String rootNodeType` | `String` | 查询树时，指定根节点的类型。 |
| `String rootNodeKey` | `String` | 查询树时，指定根节点的唯一标识符（Key）。 |
| `String filtersKeyWord` | `String` | 用于在树中进行搜索或过滤的关键字。 |
| `boolean lazyLoad` | `boolean` | 指示树是否采用懒加载模式（即只加载当前层级，子节点按需加载），默认为 `true`。 |
| `Boolean checkStrictly` | `Boolean` | 在树形组件显示复选框的情况下，是否严格遵循父子节点不互相关联的规则（即父节点选中不影响子节点，反之亦然），默认为 `false`。 |
| `public String getScene()` | `String` | 获取树的使用场景。 |
| `public TreeParam setScene(String scene)` | `TreeParam` | 设置树的使用场景，支持链式调用（返回 `this`）。 |
| `public String getFiltersKeyWord()` | `String` | 获取搜索关键字。 |
| `public TreeParam setFiltersKeyWord(String filtersKeyWord)` | `TreeParam` | 设置搜索关键字，支持链式调用。 |
| `public boolean isLazyLoad()` | `boolean` | 判断树是否设置为懒加载模式。 |
| `public TreeParam setLazyLoad(boolean lazyLoad)` | `TreeParam` | 设置懒加载模式，支持链式调用。 |
| `public String getRootNodeKey()` | `String` | 获取查询根节点的Key。 |
| `public TreeParam setRootNodeKey(String rootNodeKey)` | `TreeParam` | 设置查询根节点的Key，支持链式调用。 |
| `public String getRootNodeType()` | `String` | 获取查询根节点的类型。 |
| `public TreeParam setRootNodeType(String rootNodeType)` | `TreeParam` | 设置查询根节点的类型，支持链式调用。 |
| `@Override public TreeParam setWritable(boolean isWritable)` | `TreeParam` | 重写父类 `BaseWidgetParam` 的方法，用于设置该参数对象是否可写，并保持链式调用。 |
| `public Boolean getCheckStrictly()` | `Boolean` | 获取 `checkStrictly` 属性的原始 `Boolean` 值。 |
| `public TreeParam setCheckStrictly(Boolean checkStrictly)` | `TreeParam` | 设置复选框是否严格遵循父子不关联，支持链式调用。 |
| `public boolean isCheckStrictly()` | `boolean` | 提供一个便捷方法判断 `checkStrictly` 是否为 `true`（处理了 `null` 情况，如果为 `null` 或 `false` 则返回 `false`）。 |

### 3. 主要函数/方法 (如果适用)
此文件主要定义了一个JavaBean/POJO（Plain Old Java Object）类，其方法主要是属性的Getter/Setter以及对父类方法的重写，以提供数据访问和修改接口。文件中不包含独立的工具类函数。

### 4. 对外依赖与交互
*   **导入的外部库或项目内其他类**:
    *   `fe.util.component.param.WidgetParam`: 虽然 `TreeParam` 直接继承自 `BaseWidgetParam`，但 `WidgetParam` 的导入表明 `BaseWidgetParam` 可能与 `WidgetParam` 有继承或实现关系，例如 `BaseWidgetParam` 继承自 `WidgetParam`，或者 `WidgetParam` 定义了所有组件参数的通用接口，这使得 `TreeParam` 成为整个组件参数体系中的一员。
    *   `fe.util.component.param.BaseWidgetParam`: `TreeParam` 的直接父类，它提供了组件参数的一些通用属性和行为（如 `setWritable` 方法），确保了参数对象的基本一致性。
*   **交互方式**:
    *   **UI层与服务层交互**: 前端UI组件（如基于Vue、React的树形组件）在进行数据请求或配置显示时，会构建 `TreeParam` 实例，并将其作为请求参数发送给后端服务。
    *   **后端服务处理**: 后端服务接收到 `TreeParam` 对象后，会解析其中的参数（如 `scene`, `rootNodeType`, `rootNodeKey`, `filtersKeyWord` 等），据此执行数据库查询、业务逻辑判断，并返回相应的树状结构数据。
    *   **组件库内部使用**: `TreeParam` 作为统一的配置标准，会被 `fe.util.component` 包下其他与树相关的通用组件、数据提供者或渲染器所使用，以确保不同模块之间对树配置的理解和处理一致。
    *   **序列化/反序列化**: `serialVersionUID` 的存在表明 `TreeParam` 实例可能在网络传输（例如REST API的请求/响应体）或持久化存储时进行序列化和反序列化，便于在不同系统或进程间传递其状态。

