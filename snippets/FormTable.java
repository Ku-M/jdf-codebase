### 1. 文件核心功能

`FormTable.java` 文件定义了一个通用的前端表格视图组件，用于展示和操作基于“表单模型”（FormModel）的数据。它在应用程序中扮演着核心的数据展示和交互角色，提供了表格数据的查询、过滤、排序、增删改（通过弹出编辑面板）、以及导入导出等完整的数据管理功能。该组件高度可配置，支持自定义列、行操作按钮和权限控制，并与后端数据服务和表单管理模块紧密集成。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class FormTable<T extends BaseTableViewParam>` | `AbsTableView<T>` | 核心表格视图组件，用于显示和操作动态表单模型（FormModel）的数据。它集成了数据查询、UI构建、事件处理、行操作、数据导入导出等功能。 |
| `TableInterface` | 接口 | 定义了表格组件的基本行为和数据操作约定，如查询元数据、查询行数据等。 |
| `ViewListenerBuilder` | 接口 | 用于构建视图相关的监听器。 |
| `ListenerInterface` | 接口 | 统一的事件监听器接口，处理UI组件触发的各种命令。 |
| `FormCompnentIntf<T>` | 接口 | 与表单组件相关的接口，可能定义了表单数据处理的一些通用方法。 |

#### 方法与属性详情

以下是 `FormTable` 类的关键方法和属性详情：

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `CMD_ORDER_BY_ASC` | `public final static String` | 定义按升序排列数据的命令字符串。 |
| `CMD_ORDER_BY_DESC` | `public final static String` | 定义按降序排列数据的命令字符串。 |
| `CMD_QUIT_POPUP` | `public final static String` | 定义关闭当前弹窗的命令字符串。 |
| `CMD_VALUE_CHANGED` | `public final static String` | 定义当值发生变化时触发的命令字符串。 |
| `CMD_EXPORT_SELECTED` | `public final static String` | 定义导出选中数据项的命令字符串。 |
| `ROW_DELETE_WIDGET_ID` | `public static final String` | 定义行删除按钮的部件ID。 |
| `getService()` | `public Class<? extends ServiceIntf>` | 返回与此组件关联的服务接口类（`IGpfDCBasicFeService.class`），用于与后端进行交互。 |
| `getTopBar(PanelContext context, String key, T param)` | `public WidgetDto` | 构建并返回表格顶部的工具栏组件，包含刷新、创建、复制、删除、导入、导出等按钮，以及搜索栏。按钮可见性受权限和配置控制。 |
| `addImportExportButton(PanelContext context, BoxDto box, TableViewSetting setting, Map<String,ActionPrivilegeDto> privilegeMap)` | `public void` | 向顶部工具栏添加导入和导出按钮，根据 `TableViewSetting` 和操作权限决定其可见性。 |
| `buildRowOperateButtons(PanelContext context, T param)` | `public List<ButtonDto>` | 构建表格每一行可操作的按钮（如删除、详情、自定义按钮），其显示受权限和配置控制。 |
| `initPanelCache(PanelContext context, String key)` | `public void` | 初始化面板级别的缓存，特别是用于缓存 `FormModel` 对象以提高性能。 |
| `doGetWidget(PanelContext context)` | `public WidgetDto` | 获取并配置 `TableDto` 对象（表格UI组件），包括设置其值适配器、字体、布局、复选框显示等，并应用监听器。 |
| `isHiddenField(PanelContext context, FormModel model, FormField field)` | `public boolean` | 判断给定的表单字段是否应该在UI中隐藏。 |
| `doQueryTableMeta(TableBuilder builder, TableQuerierContext context)` | `public TableHeaderDto` | 查询并构建表格的元数据（列头信息）。它根据 `FormModel` 的字段和 `TableColumnDefine` 来生成 `TableColumnDto` 列表。 |
| `doQueryTableRows(TableBuilder builder, TableQuerier querier, TableQuerierContext context)` | `public TableRowsDto` | 执行表格数据的实际查询操作。使用 `Nutz.dao` 和 `IFormMgr` 分页查询 `Form` 数据，并进行关联数据预加载和行权限计算。 |
| `buildCondition(PanelContext context, IDao dao, Cnd cnd)` | `public Cnd` | 根据各种过滤条件（如主从关系、搜索关键词、高级过滤、默认过滤、列过滤）构建 `Nutz.dao` 的查询条件 `Cnd` 对象。 |
| `getCacheFormModel(PanelContext context, String key)` | `protected FormModel` | 从面板缓存中获取 `FormModel`，如果不存在则通过 `IAppGlobalSettingPlugin` 查询并缓存。 |
| `doQueryModelFormField(PanelContext context)` | `public List<FormField>` | 查询并返回表单模型的字段列表，根据 `widgetParam` 中的 `columns` 定义进行过滤或补充自定义列。 |
| `convert2TableRowDto(Object data)` | `public TableRowDto` | 将后端查询到的 `Form` 对象转换为前端表格所需的 `TableRowDto`。在此过程中，会构建单元格的显示内容，并设置行操作按钮的可见性。 |
| `onListener(ListenerDto listener, PanelContext context, WidgetDto source)` | `public Object` | 统一的事件处理方法，根据 `listener` 的命令类型分发到具体的处理逻辑，如添加/更新行、导出、导入等。 |
| `getEditObject(TableRowDto row)` | `public Object` | 获取待编辑行的原始数据对象（`Form`），可能会根据配置重新从数据库加载。 |
| `getEditRowPanelTitle()` | `public String` | 获取编辑行弹出面板的标题，通常是表单模型的标签。 |
| `buildEditRowPanel(ListenerDto listener, PanelContext context, WidgetDto source, Object rowData, boolean isWriteable)` | `public FormEditPanelIntf` | 构建用于编辑/新增行的表单编辑面板（`BaseFormEditView`），并设置其数据和回调监听器。 |
| `doCreateRowData(PanelContext context, Object rowData)` | `public TableRowDto` | 执行创建新行数据（`Form` 对象）的业务逻辑，将其持久化到数据库。 |
| `doUpdateRowData(PanelContext context, Object rowData)` | `public TableRowDto` | 执行更新现有行数据（`Form` 对象）的业务逻辑，更新数据库。 |
| `newRowObject(ListenerDto listener, PanelContext context, WidgetDto source)` | `public Object` | 创建一个新的空白 `Form` 对象，用于新增行操作，并填充默认值或父表单信息。 |
| `doDeleteRowData(PanelContext context, List<String> rowIds)` | `public void` | 根据传入的行ID列表，执行从数据库中删除数据的操作。 |
| `onBtnRefresh(ListenerDto listener, PanelContext context, WidgetDto source)` | `public void` | 处理刷新按钮事件，清除相关缓存并触发表格数据重新加载。 |
| `addOrUpdateRow(PanelContext context, Object data)` | `public void` | 统一处理新增或更新行数据的逻辑，判断数据是否存在并调用相应的创建或更新方法，然后更新表格UI。 |
| `onBtnExportSelected(ListenerDto listener, PanelContext context, WidgetDto source)` | `public void` | 处理导出选中行数据的事件，收集选中行的UUID并调用导出逻辑。 |
| `onBtnExport(ListenerDto listener, PanelContext context, WidgetDto source)` | `public void` | 处理导出当前表格数据（所有或选中）的事件。 |
| `exportByCnd(PanelContext context, Cnd cnd)` | `public void` | 根据给定的查询条件 `Cnd` 执行数据导出到Excel文件，并触发文件下载。显示进度对话框。 |
| `onBtnImport(ListenerDto listener, PanelContext context, WidgetDto source)` | `public void` | 处理导入数据的事件。接收用户上传的Excel/Zip文件，并调用导入服务处理数据。显示进度对话框。 |

### 3. 主要函数/方法 (如果适用)

该文件主要围绕 `FormTable` 类展开，所有功能都封装在该类的方法中，没有独立的工具类函数。

### 4. 对外依赖与交互

`FormTable.java` 与多个内部框架和外部库进行广泛的交互：

*   **Java标准库**:
    *   `java.io.IOException`: 处理IO异常。
    *   `java.util.*`: 集合类，如 `ArrayList`, `Arrays`, `LinkedHashMap`, `LinkedList`, `List`, `Map`。
    *   `java.util.stream.Collectors`: 流操作工具。
*   **Nutz ORM框架**:
    *   `org.nutz.dao.Cnd`, `org.nutz.dao.util.cri.SqlExpressionGroup`: 用于构建SQL查询条件。
*   **Kwaidoo / Leavay 工具**:
    *   `com.kwaidoo.ms.tool.ToolUtilities`: 通用工具类，可能包含对象克隆、异常栈获取等。
    *   `com.leavay.ms.tool.CmnUtil`: 常用工具类，提供字符串、集合等实用方法。
*   **Cell 框架核心**:
    *   `cell.cdao.IDao`, `cell.cdao.IDaoService`: 数据访问对象和服务的接口，用于数据库操作。
    *   `cell.cmn.io.IFiles`: 文件操作服务。
    *   `cell.fe.IFileService`: 文件服务接口。
    *   `cell.fe.gpf.dc.IAppGlobalSettingPlugin`: 应用全局设置插件，用于获取缓存的表单模型。
    *   `cell.fe.gpf.dc.basic.IGpfDCBasicFeService`: 基础前端服务接口，可能是该组件进行RPC调用的目标服务。
    *   `cell.fe.progress.CFeProgressCtrlWithTextArea`: 进度控制UI组件。
    *   `cell.gpf.adur.data.IFormMgr`: 表单管理接口，用于核心的表单数据CRUD和模型查询。
    *   `cell.gpf.dc.backup.IBackupService`: 备份服务，用于处理表单数据的导入导出。
    *   `cell.gpf.dc.runtime.IDCRuntimeContext`: DC运行时上下文。
*   **通用（cmn）模块**:
    *   `cmn.anotation.ClassDeclare`, `cmn.anotation.FieldDeclare`: 自定义元数据注解。
    *   `cmn.dto.Progress`: 进度信息DTO。
    *   `cmn.i18n.I18nIntf`: 国际化接口。
    *   `cmn.util.TraceUtil`, `cmn.util.Tracer`: 性能追踪和日志工具。
*   **前端（fe）公共组件**:
    *   `fe.cmn.app.ability.PopToast`: 弹出提示消息工具。
    *   `fe.cmn.data.*`: 文件、数据传输相关的DTO（如 `BeFile`, `ByteArrayDto`, `UploadFileResult`）和枚举（`PickFileType`）。
    *   `fe.cmn.panel.*`: UI面板和布局相关的DTO（如 `BoxDto`, `ContainerDto`, `PanelContext`）和UI能力（`DownloadFile`, `QuitPopup`, `UploadFile`）。
    *   `fe.cmn.res.JDFICons`: 图标资源。
    *   `fe.cmn.table.*`: 表格UI核心类和接口（如 `TableBuilder`, `TableDto`, `TableQuerier`, `TableRowDto`, `TableInterface`），用于表格的构建、查询和数据显示。
    *   `fe.cmn.widget.*`: UI小部件的DTO和接口（如 `ButtonDto`, `LabelDto`, `ListenerDto`）。
    *   `fe.util.component.*`: 前端通用组件（如 `FormEditPanelIntf`, `ProgressDialog`, `SearchBar`）。
    *   `fe.util.exception.VerifyException`: 前端验证异常。
*   **ADUR模块**:
    *   `gpf.adur.data.*`: 表单核心数据模型（`DataType`, `Form`, `FormField`, `FormModel`, `ResultSet`, `TableData`）。`FormTable` 的核心职责就是操作这些数据模型。
*   **GPF DC Basic FE / ExpImp / FE / Intf 模块**:
    *   `gpf.dc.basic.fe.component.app.AppCacheUtil`: 应用缓存工具。
    *   `gpf.dc.basic.fe.component.fieldextend.editor.WidgetLayoutUtil`: 小部件布局工具。
    *   `gpf.dc.basic.fe.component.param.*`: 组件参数DTO。
    *   `gpf.dc.basic.fe.enums.*`: 枚举定义。
    *   `gpf.dc.basic.fe.intf.TableRowDtoInterceptor`: 表格行DTO拦截器。
    *   `gpf.dc.basic.i18n.GpfDCBasicI18n`: 国际化资源。
    *   `gpf.dc.basic.param.view.dto.*`: 视图定义参数DTO，如 `ButtonDefine`, `TableColumnDefine`, `TableViewSetting`。
    *   `gpf.dc.basic.util.*`: 常量和工具类。
    *   `gpf.dc.expimp.FormDataExcelExpImp`: 表单数据Excel导入导出实现。
    *   `gpf.dc.fe.component.adur.data.*`: ADUR相关的前端组件，如 `FormFieldEditorFactory`。
    *   `gpf.dc.fe.util.GpfDCFeI18n`: 国际化资源。
    *   `gpf.dc.intf.FormOpObserver`: 表单操作观察者。
*   **Web / GPF DTO Model**:
    *   `web.dto.Pair`: 通用键值对DTO。
    *   `gpf.dto.model.data.ActionPrivilegeDto`: 动作权限DTO，用于控制UI元素的可见性和可操作性。

**交互方式**:
`FormTable` 通过以下方式与这些依赖进行交互：
1.  **数据持久化**: 调用 `IDaoService` 获取 `IDao` 对象，然后通过 `IFormMgr` 或自定义的 `CustomFormAction` 执行 `Form` 数据的CRUD操作。
2.  **UI渲染与事件处理**: 继承 `AbsTableView` 并实现 `TableInterface`, `ListenerInterface` 等，利用 `fe.cmn.table.*` 和 `fe.cmn.widget.*` 包中的类来构建表格UI，并响应用户操作。
3.  **权限控制**: 通过查询 `ActionPrivilegeDto` 来动态设置按钮和行操作的可见性及可写性。
4.  **国际化**: 使用 `I18nIntf` 和特定的国际化资源类（如 `GpfDCBasicI18n`）来获取多语言文本。
5.  **文件操作**: 通过 `IFileService` 和 `IFiles` 来处理导入导出的文件存取。
6.  **导入导出**: 利用 `FormDataExcelExpImp` 和 `IBackupService` 来实现表单数据的导入导出功能。
7.  **上下文管理**: 通过 `PanelContext` 获取当前面板的运行时上下文信息、缓存数据和执行UI操作（如弹出Toast、显示进度条、退出弹窗等）。
8.  **表单模型管理**: 从 `IAppGlobalSettingPlugin` 获取 `FormModel` 定义，指导表格的列构建和数据处理。
9.  **性能监控**: 使用 `TraceUtil` 和 `Tracer` 记录操作耗时。

`FormTable` 是一个高度耦合但功能强大的前端组件，它集成了数据、业务逻辑和UI，并依赖于一套成熟的内部框架和工具集。

