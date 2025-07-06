`IActionMgr.java` 文件是系统核心服务接口之一，专注于管理“动作模型”及其对应的“动作实例”。它提供了对这些业务实体进行全面的生命周期管理，包括数据持久化、查询、导入导出和业务逻辑执行等功能。

### 1. 文件核心功能

`IActionMgr` 文件定义了一个Java接口，其核心功能是：
*   **动作模型（ActionModel）管理**: 提供创建、更新、查询、删除、重命名、复制动作模型定义的功能。支持模型的层级（父子模型）和继承路径查询。
*   **动作实例（Action）管理**: 提供创建、更新、查询、删除动作实例数据的功能。支持通过多种条件（UUID、编号、SQL）进行查询，并提供分页、条件删除、批量导入导出等操作。
*   **动作执行**: 提供一个接口方法用于执行具体的动作实例，传入运行时上下文。
*   **导入导出**: 支持动作模型和动作实例数据的导入导出功能，通常用于数据迁移或备份。
*   **服务集成**: 作为 `ServiceCellIntf` 的实现，它在整个系统中扮演一个可被其他模块获取和调用的核心服务角色，通常通过一个类似依赖注入的机制（如 `Cells.get()`）获取其实例。

简而言之，该文件是系统处理所有与“动作”相关的元数据和运行时数据的核心业务逻辑入口。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public interface IActionMgr` | `ServiceCellIntf` | 定义了对动作模型和动作实例进行管理、查询、持久化、导入导出以及执行的各项操作的契约。它是整个“动作”管理模块对外暴露的API接口。 |

#### 方法与属性详情

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `static IActionMgr get()` | `IActionMgr` | 获取 `IActionMgr` 接口的单例实现实例。 |
| `getRootActionModelId()` | `String` | 获取动作根模型的唯一标识ID。 |
| `getActionModelRoot()` | `ActionModel` | 获取动作根模型的实体对象。 |
| `isActionModel(String modelId)` | `boolean` | 判断给定ID是否表示一个动作模型。 |
| `copyAction(Action action)` | `Action` | 复制一个动作实例，会排除UUID和Code，并处理内部引用。 |
| `createActionModel(ActionModel actionModel)` | `ActionModel` | 创建一个新的动作模型。 |
| `updateActionModel(Progress prog, ActionModel actionModel)` | `ActionModel` | 更新一个动作模型，支持进度通知。 |
| `updateActionModelTemplateData(Progress prog, IDao dao, ActionModel actionModel)` | `void` | 更新动作模型的模板数据，主要用于参数映射的更新。 |
| `renameActionModel(Progress prog, Map<String,Pair<String, String>> renameModels)` | `void` | 批量重命名动作模型，包括模型ID和中文名。 |
| `renameActionModelId(Progress prog, Map<String,String> renameModels)` | `void` | 批量重命名动作模型ID。 |
| `deleteActionModel(String modelId)` | `void` | 删除指定的动作模型。 |
| `forceDeleteActionModel(Progress prog, Set<String> actionModelIds)` | `void` | 强制删除动作模型及所有子模型，支持进度通知。 |
| `queryActionModel(String modelId)` | `ActionModel` | 根据模型ID查询动作模型。 |
| `queryActionModelByUuid(String uuid)` | `ActionModel` | 根据模型UUID查询动作模型。 |
| `queryChildActionModels(String modelId)` | `List<ActionModel>` | 查询指定模型的直接子动作模型列表。 |
| `queryAllActionModels()` | `List<ActionModel>` | 查询所有动作模型列表。 |
| `queryActionModelByPackage(String packagePath)` | `List<ActionModel>` | 根据包路径查询动作模型列表。 |
| `queryActionModelPage(List<String> parentIds, String packagePath, String keyword, int pageNo, int pageSize)` | `ResultSet<ActionModel>` | 分页查询动作模型，支持按父ID、包路径、关键字过滤。 |
| `isInheritForm(String actionModelId,String targetActionModelId)` | `boolean` | 判断一个模型是否是另一个模型的子孙模型。 |
| `queryInheritPaths(String actionModelId)` | `List<String>` | 查询指定模型的继承路径（所有祖先模型ID，从根开始）。 |
| `newAction(IDao dao,String actionModelId)` | `Action` | 构建一个动作实例的内存对象，携带模板信息。 |
| `createAction(IDao dao,Action action)` | `Action` | 创建一个动作实例。 |
| `createAction(Progress prog,IDao dao,Action action,FormOpObserver observer)` | `Action` | 创建一个动作实例，带进度通知和操作观察者。 |
| `updateAction(IDao dao,Action action)` | `Action` | 更新一个动作实例。 |
| `updateAction(Progress prog,IDao dao,Action action,FormOpObserver observer)` | `Action` | 更新一个动作实例，带进度通知和操作观察者。 |
| `queryAction(IDao dao,String actionModelId,String uuid)` | `Action` | 根据模型ID和实例UUID查询动作实例。 |
| `queryActionFileValue(IDao dao,String actionModelId,Cnd cnd,String[] fields)` | `Action` | 查询动作实例的指定属性值。 |
| `queryActionByCode(IDao dao,String actionModelId,String code)` | `Action` | 根据模型ID和实例编号（code）查询动作实例。 |
| `queryActionByCode(IDao dao,String actionModelId,String code,boolean queryCache)` | `Action` | 根据模型ID和实例编号查询动作实例，可指定是否查询缓存。 |
| `queryActionUuidByCode(IDao dao,String actionModelId,String code)` | `String` | 根据模型ID和实例编号查询动作实例的UUID。 |
| `queryActionPage(IDao dao,String actionModelId,Cnd cnd,int pageNo,int pageSize)` | `ResultSet<Action>` | 分页查询动作实例列表。 |
| `queryActionPage(IDao dao,String actionModelId,Cnd cnd,int pageNo,int pageSize,boolean queryRowCount,boolean compoundField,String... fields)` | `ResultSet<Action>` | 分页查询动作实例列表，带更多查询选项（如是否查总数、嵌套属性、指定字段）。 |
| `countAction(IDao dao,String actionModelId,Cnd cnd)` | `long` | 统计满足条件的动作实例数量。 |
| `queryActionPageBySql(IDao dao,String actionModelId,String querySql,Set<String> extFields,Map<String, Object> replaceParam,Map<String, Object> queryParam,Cnd cnd,int pageNo,int pageSize)` | `ResultSet<Action>` | 通过自定义SQL查询动作实例分页列表。 |
| `deleteAction(IDao dao,String actionModelId,String uuid)` | `void` | 删除指定UUID的动作实例。 |
| `deleteAction(Progress prog,IDao dao,String actionModelId,String uuid,FormOpObserver observer)` | `void` | 删除指定UUID的动作实例，带进度通知和操作观察者。 |
| `deleteActionByCnd(IDao dao,String actionModelId,Cnd cnd)` | `void` | 根据条件删除动作实例。 |
| `deleteActionByCnd(Progress prog,IDao dao,String actionModelId,Cnd cnd,FormOpObserver observer)` | `void` | 根据条件删除动作实例，带进度通知和操作观察者。 |
| `exportActionModel(Progress prog,ExportImportIntf expImpIntf,List<String> modelIds)` | `Pair<String, byte[]>` | 导出动作模型数据为压缩包。 |
| `importActionModel(Progress prog,ExportImportIntf expImpIntf,Pair<String, byte[]> zipFile)` | `void` | 导入动作模型数据。 |
| `exportActionData(Progress prog,ExportImportIntf expImpIntf,String modelId,Cnd cnd)` | `Pair<String, byte[]>` | 导出动作实例数据为压缩包。 |
| `importActionData(Progress prog,ExportImportIntf expImpIntf,String modelId,Pair<String, byte[]> zipFile)` | `void` | 导入动作实例数据。 |
| `beforeBatchImportActionDatas(Progress prog,IDao dao,List<Action> list)` | `void` | 批量导入动作实例数据前的预处理操作。 |
| `batchImportActionDatas(Progress prog,IDao dao,List<Action> list)` | `void` | 批量导入动作实例数据。 |
| `executeAction(Action data,RuntimeContextIntf rtx)` | `Object` | 执行一个动作实例，传入运行时上下文。 |

### 3. 主要函数/方法 (如果适用)

由于 `IActionMgr` 是一个接口，所有定义的功能都是其方法，已在上述“方法与属性详情”中详细列出。

### 4. 对外依赖与交互

`IActionMgr` 接口通过其方法签名和导入的包，展示了与系统其他组件和外部库的广泛依赖与交互：

*   **数据持久层**:
    *   `cell.cdao.IDao`: 这是一个核心依赖，许多方法都将 `IDao` 对象作为参数，表明其实现类会利用这个 DAO 接口进行数据库操作，通常在事务管理之下。
    *   `org.nutz.dao.Cnd`: 间接依赖于 Nutz.dao 框架，用于构建数据库查询条件，这表示底层持久化实现可能使用了 Nutz.dao。

*   **业务实体/数据传输对象 (DTO)**:
    *   `gpf.adur.action.ActionModel`: 代表动作模型的数据结构。
    *   `gpf.adur.action.Action`: 代表动作实例的数据结构。
    *   `gpf.adur.data.ResultSet`: 用于处理分页查询结果的通用数据结构。
    *   `web.dto.Pair`: 一个通用的二元组类，用于返回或传递成对的数据，例如在重命名或导入导出功能中。

*   **系统服务与上下文**:
    *   `cell.ServiceCellIntf`: `IActionMgr` 接口继承自此接口，表明它是一个“服务单元”，可能与一个服务注册/发现机制集成。
    *   `bap.cells.Cells`: `static get()` 方法通过 `Cells.get(IActionMgr.class)` 获取实例，暗示了系统采用某种形式的组件/服务管理框架（类似IoC容器），`Cells` 是获取服务实例的入口。
    *   `gpf.dto.cfg.runtime.RuntimeContextIntf`: 在 `executeAction` 方法中作为参数，提供动作执行时的运行时上下文信息，如数据库连接、操作员、流程ID等。

*   **流程与通知机制**:
    *   `cmn.dto.Progress`: 许多耗时操作（如更新、删除、导入导出）都接受 `Progress` 对象作为参数，用于实时报告操作进度，这对于前端展示加载状态或后台任务监控非常有用。
    *   `gpf.dc.intf.FormOpObserver`: 在创建/更新/删除动作实例时，可以传入 `FormOpObserver`，允许其他模块对动作的生命周期事件进行观察和响应（例如，触发验证、记录日志或执行后续业务逻辑）。

*   **导入导出功能**:
    *   `gpf.dc.intf.ExportImportIntf`: 导入导出方法接收此接口实例，表明具体的导入导出逻辑可以被定制或替换，提供高度的灵活性。

*   **Java标准库**:
    *   `java.util.List`, `java.util.Map`, `java.util.Set`: 广泛用于参数和返回值，处理集合数据。

**交互方式总结**:
`IActionMgr` 的实现类将作为业务逻辑层与数据持久层（通过 `IDao`）交互，执行数据的增删改查。它通过 `Progress` 和 `FormOpObserver` 与上层调用者或观察者进行状态通知和事件回调。通过 `RuntimeContextIntf` 接收执行环境信息。它高度模块化，允许通过接口（如 `ExportImportIntf`）注入不同的功能实现。整体来看，它是一个设计良好、职责清晰、易于扩展和维护的核心业务接口。

