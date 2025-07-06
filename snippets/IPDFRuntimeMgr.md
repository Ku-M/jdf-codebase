以下是对`IPDFRuntimeMgr.java`文件的技术知识库分析：

---

### 1. 文件核心功能

`IPDFRuntimeMgr.java` 文件定义了一个核心的服务接口，负责整个流程引擎的运行时管理。它在项目中扮演着流程生命周期、表单数据、操作记录以及运行时环境的中心协调者角色。

其主要职责包括：
*   **流程实例生命周期管理**: 提供创建、查询、更新、删除、启动、停止、重置流程实例的功能，并支持流程实例的同步（当流程模型发生变更时）。
*   **流程表单操作**: 管理流程表单的创建、提交（包括开始节点表单和普通节点表单的提交），以及表单数据的查询（总表单、PDC表单、历史表单、最新表单、嵌套属性数据等）。
*   **流程数据查询**: 提供丰富的查询接口，包括待办、已办、流程追踪、通用集合视图的分页查询，以及待办数量统计。
*   **操作日志管理**: 提供操作日志的查询、忽略和历史日志清理功能。
*   **运行时上下文与参数管理**: 构建和维护流程运行上下文，并支持流程实例运行时参数的持久化存储和获取。
*   **流程控制与异常处理**: 抛出特定的业务异常信号（如删除/关闭流程实例、提交错误），以控制流程的走向或通知错误状态。
*   **流程视图与权限**: 提供构建流程视图查询SQL、查询表单字段定义、计算表单权限等能力。
*   **运行时线程管理**: 提供查询和管理流程引擎内部运行线程（如阻塞线程、获取线程堆栈）的能力，便于监控和故障排查。

### 2. 主要组件/类定义

该文件定义了一个核心服务接口：`IPDFRuntimeMgr`。

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `IPDFRuntimeMgr` | `ServiceCellIntf` | 流程运行管理的核心接口，提供对流程实例、表单、操作日志、运行时上下文、流程查询和线程管理的全面服务。 |

#### 方法与属性详情

**常量和静态方法:**

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `CONFLICT_OPERATE_LOG_FIELD_NAME_PREFIX` | `String` | 定义操作日志相关字段名的前缀，用于避免命名冲突。 |
| `CONFLICT_FIELD_NAME_PREFIX` | `String` | 定义通用流程相关字段名的前缀。 |
| `OperateLogExtFields` | `Set<String>` | 定义操作日志中需要包含的额外字段集合。 |
| `PdfFormSystemFields` | `List<String>` | 定义流程表单的系统字段列表，如创建时间、创建人、更新时间。 |
| `get()` | `IPDFRuntimeMgr` | 静态方法，通过 `Cells` 框架获取 `IPDFRuntimeMgr` 服务的单例实例。 |

**核心业务方法 (按功能分组):**

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| **流程实例生命周期** | | |
| `newRuntimeContext()` | `IDCRuntimeContext` | 构建并返回一个新的流程运行上下文。 |
| `createPDFInstance(...)` | `PDFInstance` | 创建流程实例，支持仅创建或同时提交开始节点表单（包括废弃版本）。 |
| `queryPDFInstance(String)` | `PDFInstance` | 根据UUID查询流程实例。 |
| `updatePDFInstace(PDFInstance)` | `PDFInstance` | 更新流程实例的信息。 |
| `synchornizePDFInstances(...)` | `void` | 同步一个或所有流程实例，使其与流程模型定义保持一致。 |
| `deletePDFInstance(String)` | `void` | 删除指定UUID的流程实例。 |
| `startPDFInstance(String)` | `void` | 启动流程实例。 |
| `stopPDFInstance(String)` | `void` | 停止流程实例。 |
| `resetPDFInstance(...)` | `void` | 重置流程实例的指定或所有节点状态，可用于重启已关闭的流程实例。 |
| `queryPDFInstanceStatus(String)` | `PdfInstanceStatus` | 查询流程实例的当前节点状态。 |
| `createRelatePDFInstanceAndSubmit(...)` | `PDFInstance` | 创建并提交一个关联的流程实例。 |
| `querySrcPDFInstance(String)` | `PDFInstance` | 查询当前流程实例的源流程实例。 |
| `queryDstPDFInstanceList(...)` | `List<PDFInstance>` | 查询当前流程实例关联的目标流程实例列表。 |
| **流程表单操作与查询** | | |
| `createAndSubmitPDCForm(...)` | `PDCForm` | 创建并提交PDC表单，支持多种提交方式。 |
| `submitPDCForm(...)` | `PDCForm` | 提交PDC表单到指定节点，支持表单操作观察者。 |
| `newStartForm(...)` | `PDCForm` | 初始化开始节点的PDC表单，支持鉴权、确权和从现有表单复制。 |
| `queryTotalForm(...)` | `Form` | 查询流程实例的总表单数据。 |
| `queryPDCForm(...)` | `PDCForm` | 查询指定操作记录的PDC表单。 |
| `queryHistoryPDCForm(...)` | `PDCForm` | 从历史交互单数据中查询PDC表单。 |
| `queryLatestPDCForm(...)` | `PDCForm` | 根据节点Key查询最新的PDC表单。 |
| `queryNestingTableData(...)` | `PDCForm` | 查询并设置表单上的嵌套属性数据。 |
| `queryPDFFormFields(...)` | `List<FormField>` | 查询PDF表单的属性列表，可选择是否添加操作记录字段前缀。 |
| `queryPdfFormExtQueryFields()` | `List<FormField>` | 获取流程视图中附加查询的字段定义列表。 |
| **权限与路由** | | |
| `caculateFormPrivilegeDto(...)` | `FormPrivilegeDto` | 计算表单的权限数据。 |
| `mockRunRouterPaths(...)` | `void` | 模拟自动节点路由，跳过指定路由节点的提交。 |
| **数据查询 (列表、分页、统计)** | | |
| `buildPDFFormQueryCteSql(...)` | `Map<String,String>` | 构建PDF表单查询的默认CTE SQL语句。 |
| `buildPDFFormQuerySql(...)` | `String` | 构建PDF表单查询的SQL语句，支持查询条件和权限表达式。 |
| `buildPDFFormCountSql(...)` | `String` | 构建PDF表单总数查询的SQL语句。 |
| `queryPDFFormPage(...)` | `ResultSet<PDFForm>` | 查询PDF表单的分页数据，支持多种查询选项和条件。 |
| `queryPDFFormPageBySql(...)` | `ResultSet<PDFForm>` | 通过自定义SQL查询PDF表单分页。 |
| `queryToDoPage(...)` | `ResultSet<ToDoForm>` | 查询用户的待办数据。 |
| `queryDonePage(...)` | `ResultSet<DoneForm>` | 查询用户的已办数据。 |
| `queryUnionPage(...)` | `ResultSet<DataRow>` | 通用集合视图的分页查询。 |
| `queryProgressPage(...)` | `ResultSet<PdfInstanceProgress>` | 查询流程追踪数据。 |
| `lazyQueryTodoCount(...)` | `long` | 懒查询用户的待办数量，支持立即查询。 |
| `queryTodoCount(...)` | `long` | 查询用户的待办数量。 |
| `queryColumnDistintValues(...)` | `ResultSet<DataRow>` | 查询指定列的去重值。 |
| **操作日志管理** | | |
| `queryOperateLogPage(...)` | `ResultSet<OperateLog>` | 查询操作日志的分页数据。 |
| `queryOperateLog(...)` | `OperateLog` | 查询指定的操作日志。 |
| `queryPreviousOperateLog(...)` | `OperateLog` | 查询当前操作记录的上一步操作记录。 |
| `ignoreOperateLog(...)` | `void` | 忽略指定操作记录的状态，用于错误修复。 |
| `deleteStayOperateLog(...)` | `void` | 删除指定流程的历史操作记录，可设置保留天数。 |
| **流程实例上下文** | | |
| `savePDFInstanceContext(...)` | `void` | 保存流程实例的运行上下文参数。 |
| `getPDFInstanceContext(...)` | `Object` | 获取流程实例持久化后的运行上下文参数。 |
| `queryAllPDFInstanceContext(...)` | `Map<String,Object>` | 查询所有流程实例的上下文参数。 |
| **控制流与异常** | | |
| `throwDeletePDFInstance(...)` | `void` | 抛出删除流程实例的信号。 |
| `throwClosePDFInstance(...)` | `void` | 抛出关闭流程实例的信号。 |
| `throwSubmitError(...)` | `void` | 抛出提交表单出错的信号，并更新节点状态为出错。 |
| **事件通知与线程管理** | | |
| `subscribeNodeStatusChanged(...)` | `void` | 订阅流程节点状态变更事件。 |
| `notifyOperateStatusChanged(...)` | `void` | 通知操作状态变更。 |
| `getRunningNodeInfos(...)` | `List<RunningThreadInfo>` | 查询正在运行节点的线程信息。 |
| `getThreadStackInfo(long)` | `String` | 获取指定线程的堆栈信息。 |
| `getAllThreadStackInfo(boolean)` | `String` | 获取所有线程的堆栈信息。 |
| `tryInterruptThread(...)` | `void` | 尝试中断指定线程。 |

### 3. 主要函数/方法 (如果适用)

由于 `IPDFRuntimeMgr` 是一个接口，其所有的“函数”都是其定义的方法。这些方法已在“方法与属性详情”中详细列出，因此本节不再重复。

### 4. 对外依赖与交互

`IPDFRuntimeMgr` 作为流程引擎的核心接口，依赖并与多个外部库和内部模块进行广泛交互：

*   **Java标准库**: 
    *   `java.util.Arrays`, `java.util.List`, `java.util.Map`, `java.util.Set`: 用于数据集合操作和管理。
*   **NutZ ORM框架**:
    *   `org.nutz.dao.Cnd`, `org.nutz.dao.util.cri.SqlExpressionGroup`: 核心数据访问依赖，用于构建数据库查询条件和表达式，表明该接口与底层数据库操作紧密相关。
*   **`com.cdao` 模块**:
    *   `com.cdao.dto.DataRow`, `com.cdao.model.CDoLink`: 引用自定义的数据传输对象和模型，用于处理数据库返回的数据行和业务模型之间的链接关系。
*   **`com.kwaidoo.ms.tool` 模块**:
    *   `com.kwaidoo.ms.tool.ToolUtilities`: 使用其中的通用工具方法，例如创建集合。
*   **`cell` 框架核心组件**:
    *   `bap.cells.Cells`: 用于获取 `IPDFRuntimeMgr` 服务实例，体现其在 `cell` 组件架构中的集成方式。
    *   `cell.ServiceCellIntf`: 作为其父接口，表明 `IPDFRuntimeMgr` 遵循 `cell` 框架的服务单元规范。
    *   `cell.cdao.IDao`: 在多个方法中作为参数传入，表明其数据操作通过统一的 `IDao` 接口进行，可能用于事务管理或抽象数据源。
*   **`gpf.cfg`, `gpf.dc`, `gpf.adur`, `gpf.dto`, `gpf.exception`, `gpf.i18n`, `gpf.model` 等内部模块**:
    *   `cell.gpf.cfg.IRuntimeContext`, `cell.gpf.dc.runtime.IDCRuntimeContext`: 依赖于运行时上下文接口，该上下文承载了流程执行的关键参数和状态。
    *   `gpf.adur.data.Form`, `FormField`, `ResultSet`: 引用表单数据结构和查询结果集，是表单操作的基础。
    *   `gpf.dc.action.param.NodeStatusChangeEventParam`, `gpf.dc.config.OperateLogStatusHookDto`, `PDC`, `gpf.dc.intf.FormOpObserver`: 引用流程域内的特定参数、配置和接口，用于事件处理、配置管理和观察者模式。
    *   `gpf.dc.runtime` 包下的众多类 (`AbsSelectQuery`, `DoneForm`, `OperateLog`, `PDCForm`, `PDFForm`, `PDFInstance`, `ToDoForm`, `UnionQuery` 等): 这是 `IPDFRuntimeMgr` 服务的核心业务领域，与这些类构成了流程运行时的主要数据模型和逻辑。
    *   `gpf.dto.cfg.runtime.RunningThreadInfo`, `gpf.dto.model.data.FormPrivilegeDto`: 引用数据传输对象，用于线程信息和表单权限的数据传递。
    *   `gpf.exception.CloseControlFlowException`, `DeleteControlFlowException`, `SubmitRunErrorException`: 抛出自定义的业务异常，用于流程控制流的显式管理和错误报告。
    *   `gpf.i18n.GpfConst`: 引用国际化常量，用于系统消息或字段定义。
    *   `gpf.model.observer.OperateLogStatusChangeProxyImpl`: 与操作日志状态变更的观察者模式实现相关。

**交互模式**:
`IPDFRuntimeMgr` 作为服务提供者，其方法通常接受 `IDCRuntimeContext` 和 `IDao` 作为核心参数，以便在特定运行时上下文和数据库会话中执行操作。它返回或操作 `PDFInstance`、`PDCForm`、`OperateLog` 等流程领域的核心数据对象。通过 NutZ DAO 进行底层数据持久化和查询。同时，它也通过抛出特定异常来通知调用者流程中的特殊事件或错误，并通过订阅/通知机制与其他组件进行事件驱动的交互。此外，它还提供了对流程引擎内部线程的监控和管理能力。

