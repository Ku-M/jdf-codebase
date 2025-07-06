以下是对 `PanelInfo.java` 文件的详细技术分析。

---

### 1. 文件核心功能

`PanelInfo.java` 文件定义了一个数据传输对象（DTO），其核心职责是封装UI面板（Panel）的关键元数据。它在系统中主要扮演以下角色：

*   **前端向后端传输数据载体**: 如文件注释所示，它通常用于将前端页面或UI组件的相关信息（如面板的唯一标识、关联的组件ID、以及面板对应的DTO类名）传递给后端服务。
*   **面板身份和层级表示**: 通过 `panelGlobalKey`、`panelWidgetId` 和 `parentGlobalKey`，它定义了一个面板的唯一身份以及它在UI层级结构中的位置。
*   **上下文转换**: 提供了将自身关键信息转换为 `PanelContext` 对象的能力，以便在后端业务逻辑中建立或获取面板的运行时上下文。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class PanelInfo` | `CsonPojo` | 作为数据传输对象（DTO），封装了UI面板（Panel）的关键元信息，如其全局唯一标识、关联的Widget ID、DTO类名以及其父面板的关联信息。它主要用于前端向后端传输面板的相关数据，并能转换成`PanelContext`。 |

#### 方法与属性详情

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `serialVersionUID` | `static final long` | Java序列化ID，用于类的版本控制。 |
| `panelGlobalKey` | `String` | 当前面板的全局唯一标识符（GlobalKey）。 |
| `panelWidgetId` | `String` | 当前面板内用于值搜集和获取的Widget ID。 |
| `panelClass` | `String` | 当前面板所关联或对应的DTO（Data Transfer Object）的类名。 |
| `parentGlobalKey` | `String` | 父级面板的全局唯一标识符，用于表示面板的层级关系或父子关联。 |
| `panelContext` | `PanelContext` | 存储当前面板的上下文信息，通常包含更多运行时或业务相关的数据。 |
| `getPanelGlobalKey()` | `String` | 获取 `panelGlobalKey` 的值。 |
| `setPanelGlobalKey(String)` | `void` | 设置 `panelGlobalKey` 的值。 |
| `getPanelWidgetId()` | `String` | 获取 `panelWidgetId` 的值。 |
| `setPanelWidgetId(String)` | `void` | 设置 `panelWidgetId` 的值。 |
| `getPanelClass()` | `String` | 获取 `panelClass` 的值。 |
| `setPanelClass(String)` | `void` | 设置 `panelClass` 的值。 |
| `getParentGlobalKey()` | `String` | 获取 `parentGlobalKey` 的值。 |
| `setParentGlobalKey(String)` | `void` | 设置 `parentGlobalKey` 的值。 |
| `getPanelContext()` | `PanelContext` | 获取 `panelContext` 对象。 |
| `setPanelContext(PanelContext)` | `PanelInfo` | 设置 `panelContext` 对象，并返回当前 `PanelInfo` 实例（支持链式调用，提供更流畅的API）。 |
| `toContext()` | `PanelContext` | 创建并返回一个新的 `PanelContext` 实例，其中包含从当前 `PanelInfo` 中提取的 `panelGlobalKey`、`panelClass` 和 `panelWidgetId` 信息。该方法将 `PanelInfo` 的核心标识信息转换为上下文对象。 |
| `toString()` | `String` | 提供 `PanelInfo` 对象的字符串表示，通常用于日志记录或调试。其格式为 "Panel的DTO类名=全局Key和WidgetId的组合名称"，其中组合名称由 `CmnUtil` 工具类生成。 |

### 3. 主要函数/方法 (如果适用)

本文件主要定义了一个Java类及其成员方法，不包含独立的工具函数。所有关键功能都作为 `PanelInfo` 类的实例方法（如 `toContext()` 和 `toString()`）被实现，并已在“方法与属性详情”中详细描述。

### 4. 对外依赖与交互

`PanelInfo` 类与以下外部库或项目内的其他类存在依赖与交互：

*   **`cson.core.CsonPojo`**:
    *   `PanelInfo` 继承自 `CsonPojo`。这表明 `PanelInfo` 实例被设计为可以通过 CSON (Customized JSON) 格式进行序列化和反序列化。CSON 是一种用于数据传输的轻量级格式，这使得 `PanelInfo` 能够方便地在不同系统（特别是前端和后端）之间进行数据交换。`CsonPojo` 提供了实现CSON转换的基础能力。
*   **`com.leavay.ms.tool.CmnUtil`**:
    *   在 `toString()` 方法中被调用，具体使用了 `CmnUtil.getNameAndLabel(String globalKey, String widgetId)` 方法。这表明 `CmnUtil` 是一个通用的工具类，可能提供了字符串格式化、资源命名约定或标签解析等辅助功能，用于生成更具可读性的面板标识。
*   **`fe.cmn.panel.PanelContext`**:
    *   `PanelInfo` 类内部包含一个 `PanelContext` 类型的成员变量 `panelContext`。
    *   `PanelInfo` 的 `toContext()` 方法会创建一个新的 `PanelContext` 实例，并将 `PanelInfo` 自身的核心标识信息（`panelGlobalKey`, `panelClass`, `panelWidgetId`）复制到新创建的 `PanelContext` 对象中。
    *   这表明 `PanelContext` 是与 `PanelInfo` 紧密相关的另一个数据结构，可能用于在后端业务逻辑中维护面板的运行时状态、执行权限或更丰富的业务上下文信息。`PanelInfo` 提供了一种从传输层数据向内部上下文数据转换的机制，实现了数据模型的分离和职责的划分。

