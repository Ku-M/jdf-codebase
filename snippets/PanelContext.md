### 1. 文件核心功能
`PanelContext.java` 文件是一个核心的上下文（Context）对象，用于在前端（Flutter应用）与后端（Java服务）之间传递当前前端UI容器（通常指一个“面板”或“页面”）的运行时信息。

它的主要职责包括：
1.  **传递前端上下文信息**: 包含当前操作所在面板的唯一标识、类名、Widget ID、路径等，以及路由参数、查询参数、编辑值等数据。
2.  **实现后端回调前端**: 提供机制让后端逻辑能够回溯到特定的前端面板，并触发前端定义的行为（通过 `callback` 方法）。
3.  **支持跨面板访问**: 通过 `targetPanelPath` 字段和回调机制，允许后端指定或请求访问不同于当前面板的其它面板（父、子或兄弟面板）。
4.  **管理面板级缓存**: 提供API用于在后端存储和检索与特定前端面板相关的临时数据（通过 `PanelCacheUtil` ）。
5.  **处理远程信道**: 内部包含一个远程信道对象（remote channel object），这是实现后端回调前端通信的基础，但需要特别注意其生命周期管理和不可序列化的特性。

在整个项目中，`PanelContext` 扮演着前端与后端交互的“桥梁”和“信使”角色，它封装了前端请求的元数据，并提供了后端与前端进行异步通信和数据共享的能力。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class PanelContext` | `Context` | 封装前端当前操作的容器（Panel）上下文信息，包括其标识、路径、路由参数、编辑值等，并提供后端回调前端的机制和面板级数据缓存功能。 |

#### 方法与属性详情

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `serialVersionUID` | `long` | Java序列化ID，尽管类注释强调包含远程信道时不应自行序列化。 |
| `targetPanelPath` | `String` | 目标面板的Widget路径，用于跨面板访问（如父/子面板）。推荐使用此方式取代 `GlobalKey`。 |
| `currentPanelGlobalKey` | `String` | **（已废弃）** 当前面板的全局唯一键，用于全局定位面板，旧版回调机制使用此ID。 |
| `currentPanelWidgetId` | `String` | 当前面板的Widget ID，用于值搜集和获取，来自前端，只读。 |
| `currentPanelClass` | `String` | 当前面板的类名（DTO的全类名），来自前端，只读。 |
| `panelValue` | `PanelValue` | 当前界面存在的编辑值，用于前端传值给后端，后端只读。 |
| `routeParameter` | `Object` | 页面间跳转时传递的参数，来自前端，只读，必须是CsonPojo子类或基础类型。 |
| `queryParameters` | `Map<String, Object>` | 路由查询参数，仅在首页及路由构建时前端会带回（如Web端的URL查询参数）。 |
| `relatedUnitId` | `String` | 当前悬浮层操作所关联的组件单元ID（前端设置），仅在布局器中使用。 |
| `serviceParam` | `ServiceParamDto` | 接口方法标识参数，用于后端服务调用时传递。 |
| `getTargetPanelPath()` | `String` | 获取目标面板路径。 |
| `setTargetPanelPath(String path)` | `PanelContext` | 设置目标面板路径，返回 `this` 实现链式调用。 |
| `getRelatedUnitId()` | `String` | 获取关联组件单元ID。 |
| `getCurrentPanelGlobalKey()` | `String` | 获取当前面板全局键。 |
| `setCurrentPanelGlobalKey(String key)` | `PanelContext` | **（已废弃）** 设置当前面板全局键。 |
| `getCurrentPanelClass()` | `String` | 获取当前面板类名。 |
| `getCurrentPanelWidgetId()` | `String` | 获取当前面板Widget ID。 |
| `getPanelValue()` | `PanelValue` | 获取面板编辑值对象。 |
| `getWidgetValue(String widgetId)` | `Object` | 从 `panelValue` 中根据Widget ID获取具体组件的值。 |
| `getRouteParameter()` | `Object` | 获取路由参数。 |
| `getQueryParameters()` | `Map<String, Object>` | 获取路由查询参数。 |
| `getServiceParam()` | `ServiceParamDto` | 获取服务参数。 |
| `setServiceParam(ServiceParamDto param)` | `PanelContext` | 设置服务参数。 |
| `callback(PanelCallback callback)` | `Object` | **核心方法。** 在后端调用，回调客户端执行相关的Ability。利用 `ThreadLocal` 获取信道进行回调，只能在前端调用后端的响应线程中使用。 |
| `setChannelExpireMs(long expireMs)` | `void` | 设置信道资源的有效期（毫秒），`-1` 表示永不释放（需手动 `close`）。 |
| `exportThread()` | `PanelContext` | **（已废弃）** 信道改造为资源型CELL后的遗留方法，不再需要导出线程。 |
| `cloneWithChannel()` | `PanelContext` | 复制 `PanelContext` 对象，并显式将信道（channel）从原对象复制到新对象。这表明信道本身不能通过常规序列化/反序列化复制。 |
| `getOrCreatePanelCache()` | `IMapCell<String, Object>` | 获取或创建当前面板的缓存。 |
| `getPanelCache()` | `IMapCell<String, Object>` | 获取当前面板的缓存（如果存在）。 |
| `putPanelCache(String key, Object value)` | `void` | 将键值对放入面板缓存。 |
| `getPanelCacheValue(String key)` | `Object` | 从面板缓存中获取指定键的值。 |
| `removePanelCacheValue(String key)` | `Object` | 从面板缓存中移除指定键的值。 |
| `hasPanelCache()` | `boolean` | 检查当前面板是否存在缓存。 |
| `getPanelCacheSize()` | `int` | 获取面板缓存的大小。 |
| `deletePanelCache()` | `boolean` | 删除并释放当前面板的缓存。 |

### 3. 主要函数/方法 (如果适用)
本文件主要是一个类的定义，其核心功能通过实例方法实现。以下列出其中几个特别关键的方法：

| 函数名 | 参数 | 返回值 | 功能描述 |
| :--- | :--- | :--- | :--- |
| `callback` | `PanelCallback callback` | `Object` | 这是 `PanelContext` 的核心交互方法。它允许后端根据前端传入的上下文信息，向前端特定面板发送一个回调指令，触发前端定义的行为（如更新UI、执行某个操作）。此方法依赖于当前线程持有的通信信道。 |
| `cloneWithChannel` | 无 | `PanelContext` | 提供了一种特殊的复制机制。由于 `PanelContext` 内部包含一个不可序列化且不可直接复制的远程信道对象，此方法确保在复制 `PanelContext` 的同时，能够正确地传递或引用（`setChannel(getChannel())`）原始信道对象，从而保持新旧实例都能利用相同的通信信道。 |
| `setChannelExpireMs` | `long expireMs` | `void` | 允许动态设置与此 `PanelContext` 关联的通信信道的生命周期。这对于管理后端回调信道的资源释放至关重要，特别是对于长期存在的或需手动管理的信道。 |
| `getOrCreatePanelCache` / `putPanelCache` / `getPanelCacheValue` / `deletePanelCache` | 变参 | 变返回值 | 这些方法封装了对 `PanelCacheUtil` 的调用，提供了一种方便的方式来管理与特定前端面板生命周期绑定的后端缓存。这使得后端可以在处理同一面板的多次请求时，保持一些状态或数据，而无需重新从数据库或其他外部源加载。 |

### 4. 对外依赖与交互

`PanelContext` 文件导入了多个外部类和包，并与它们进行交互以实现其功能：

*   **`java.util.Map`**: Java标准库，用于 `queryParameters` 字段存储键值对形式的路由查询参数。
*   **`cell.cmn.cache.IMapCell`**: 这是一个自定义的缓存接口，`PanelContext` 通过 `PanelCacheUtil` 使用它来实现面板级别的键值存储。
*   **`cson.CsonUtil`**: 这是一个自定义的工具类，用于Cson（可能是某种序列化/反序列化格式）。`PanelContext` 在 `cloneWithChannel()` 方法中调用 `CsonUtil.clone(this)` 来进行对象复制，这表明 `CsonUtil` 提供了对 `CsonPojo` 类型或兼容对象的克隆能力。
*   **`fe.cmn.app.Context`**: `PanelContext` 的父类，继承了其基本上下文功能。
*   **`fe.cmn.data.ServiceParamDto`**: 一个数据传输对象，用于封装服务调用时的参数。`PanelContext` 包含此类型字段以传递服务相关元数据。
*   **`fe.cmn.mgr.PanelCacheUtil`**: 一个管理面板缓存的工具类。`PanelContext` 的所有缓存相关方法（如 `getOrCreatePanelCache`, `putPanelCache`, `deletePanelCache` 等）都委托给此工具类实现，表明 `PanelContext` 自身不管理缓存数据，而是通过 `PanelCacheUtil` 进行统一的缓存管理和生命周期控制。
*   **`fe.cmn.sys.FeTracker`**: 一个系统级别的跟踪或通信工具类。`PanelContext` 的 `callback` 方法通过调用 `FeTracker.callback()` 来触发后端到前端的回调，这意味着 `FeTracker` 负责底层的通信信道管理和远程过程调用（RPC）的执行。
*   **`flutter.coder.annt.NullSafe`**: 一个注解，可能用于标记字段或方法可以接受或返回null，提示代码生成器或分析工具进行空安全检查。这暗示了后端框架与Flutter前端的紧密集成，可能涉及代码生成或跨语言数据传输。
*   **`flutter.rpc.CRpcCallbackCmd`**: 一个远程过程调用（RPC）回调命令对象。在 `callback` 方法中，`PanelContext` 会构建一个 `CRpcCallbackCmd` 对象来封装要传递给前端的回调信息。这表明系统使用RPC机制进行前后端通信。

综上，`PanelContext` 与其依赖项紧密协作，共同构建了一个复杂而高效的前后端通信和上下文管理系统，特别强调了回调机制和面板级数据缓存。其内部的远程信道管理是其核心但也是最需要注意的方面。

