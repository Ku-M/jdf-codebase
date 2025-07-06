### 1. 文件核心功能
`PDCForm.java` 文件定义了一个核心的数据模型，用于表示“流程节点表单”。它的主要职责是封装在一个业务流程的特定节点上，用户需要填写或查看的表单数据、相关的操作动作以及该表单的权限信息。

该文件在整个项目中扮演的角色：
*   **数据载体**: 作为流程引擎与前端或外部系统之间交换表单数据的载体。
*   **流程状态快照**: 结合了流程总表单中与当前节点相关的属性，以及当前节点独有的状态信息，形成一个该节点下的表单视图。
*   **业务逻辑封装**: 虽然主要是一个数据模型，但它通过提供类型安全的Getter/Setter方法，并继承自通用的 `Form` 基类，间接封装了对底层表单数据的存取逻辑，使得上层业务代码可以更清晰地操作特定属性。
*   **元数据支持**: 通过 `@ClassDeclare` 和 `@FieldDeclare` 等自定义注解，为表单及其字段提供了丰富的元数据，这可能用于运行时反射、表单渲染、文档生成或自动化测试等场景。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `PDCForm` | `gpf.adur.data.Form`, `java.io.Serializable` | 表示特定流程节点上的表单数据模型，包含表单的元数据定义（`meta`）、可执行动作（`actions`）、表单权限（`formPrivilege`），以及一系列与流程、表单实例、创建/修改信息、状态等相关的属性。它扩展了基础 `Form` 类，提供了更具体的流程相关属性访问器。 |

#### 方法与属性详情

**类: `PDCForm`**

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `serialVersionUID` | `static final long` | 用于序列化的版本ID。 |
| `OpLogUuid`, `Code`, `PdfInstUuid`, `ParentFormUuid`, `Creator`, `CreateTime`, `UpdateTime`, `Closed`, `Assignee`, `StepOperator`, `ExecuteTime`, `StepName`, `NodeName`, `NodeKey`, `Status`, `StepError` | `static final String` | 定义了一系列常量，作为表单内部通用属性的键，方便通过这些键从基类 `Form` 中获取对应的值。 |
| `pdfUuid` | `String` | PDF文件的唯一标识符。 |
| `meta` | `List<FormField>` | 存储表单中所有字段的元数据列表，每个 `FormField` 定义了一个表单项的属性，如代码、名称、类型等。 |
| `actions` | `List<String>` | 存储当前表单可执行的操作动作列表，例如“提交”、“批准”、“驳回”等。 |
| `formPrivilege` | `FormPrivilegeDto` | 存储当前表单的权限信息，如哪些用户或角色可以查看、编辑、操作此表单。 |
| `modified` | `boolean` | 标识表单数据自加载后是否发生过修改。 |
| `PDCForm()` | `构造函数` | 默认构造函数，调用父类 `Form` 的构造函数，初始化表单。 |
| `getPdfUuid()` | `String` | 获取 `pdfUuid`。 |
| `setPdfUuid(String pdfUuid)` | `void` | 设置 `pdfUuid`。 |
| `getMeta()` | `List<FormField>` | 获取表单字段元数据列表。 |
| `setMeta(List<FormField> meta)` | `void` | 设置表单字段元数据列表。 |
| `getFieldMap()` | `Map<String,FormField>` | 将 `meta` 列表转换为一个以字段 `code` 为键的 `Map`，方便通过字段代码快速查找 `FormField`。使用了 `NullUtil` 进行空安全处理。 |
| `getActions()` | `List<String>` | 获取表单可执行动作列表。 |
| `setActions(List<String> actions)` | `void` | 设置表单可执行动作列表。 |
| `getFormPrivilege()` | `FormPrivilegeDto` | 获取表单权限对象。 |
| `setFormPrivilege(FormPrivilegeDto formPrivilege)` | `void` | 设置表单权限对象。 |
| `getOpLogUuid()`, `setOpLogUuid(String stepUuid)` | `String`, `void` | 获取/设置操作日志UUID，底层调用 `Form` 的 `getString`/`setAttrValueByCode`。 |
| `getPdfInstUuid()`, `setPdfInstUuid(String v)` | `String`, `void` | 获取/设置流程实例UUID，底层调用 `Form` 的 `getString`/`setAttrValueByCode`。 |
| `getParentFormUuid()`, `setParentFormUuid(String v)` | `String`, `void` | 获取/设置父表单UUID，底层调用 `Form` 的 `getString`/`setAttrValueByCode`。 |
| `getCreator()`, `setCreator(AssociationData v)` | `AssociationData`, `void` | 获取/设置创建人信息，底层调用 `Form` 的 `getAssociationByCode`/`setAttrValueByCode`。 |
| `getCreateTime()`, `setCreateTime(Long v)` | `Long`, `void` | 获取/设置创建时间，底层调用 `Form` 的 `getLong`/`setAttrValueByCode`。 |
| `getUpdateTime()`, `setUpdateTime(Long v)` | `Long`, `void` | 获取/设置更新时间，底层调用 `Form` 的 `getLong`/`setAttrValueByCode`。 |
| `getClosed()`, `setClosed(Boolean v)`, `isClosed()` | `Boolean`, `void`, `boolean` | 获取/设置表单是否关闭的状态，`isClosed()` 提供便利方法，若为null则返回false。底层调用 `Form` 的 `getBoolean`/`setAttrValueByCode`。 |
| `getStepName()`, `setStepName(String v)` | `String`, `void` | 获取/设置步骤名称，底层调用 `Form` 的 `getString`/`setAttrValueByCode`。 |
| `getNodeName()`, `setNodeName(String v)` | `String`, `void` | 获取/设置节点名称，底层调用 `Form` 的 `getString`/`setAttrValueByCode`。包含异常处理。 |
| `getNodeKey()`, `setNodeKey(String v)` | `String`, `void` | 获取/设置节点键（唯一标识），底层调用 `Form` 的 `getString`/`setAttrValueByCode`。包含异常处理。 |
| `getAssignee()`, `setAssignee(List<AssociationData> v)` | `List<AssociationData>`, `void` | 获取/设置任务处理人列表，底层调用 `Form` 的 `getAssociationsByCode`/`setAttrValueByCode`。 |
| `getStepOperator()`, `setStepOperator(AssociationData v)` | `AssociationData`, `void` | 获取/设置当前步骤操作人，底层调用 `Form` 的 `getAssociationByCode`/`setAttrValueByCode`。 |
| `getExecuteTime()`, `setExecuteTime(Long v)` | `Long`, `void` | 获取/设置执行时间，底层调用 `Form` 的 `getLong`/`setAttrValueByCode`。 |
| `getStatus()`, `setStatus(String status)` | `String`, `void` | 获取/设置流程节点状态，底层调用 `Form` 的 `getString`/`setAttrValueByCode`。 |
| `getStepError()`, `setStepError(String stepError)` | `String`, `void` | 获取/设置步骤错误信息，底层调用 `Form` 的 `getString`/`setAttrValueByCode`。 |
| `isModified()`, `setModified(boolean modified)` | `boolean`, `void` | 获取/设置数据是否发生修改的标志位。 |
| `toString()` | `String` | 重写 `toString` 方法，提供表单的模型ID、UUID、节点名称和节点键的字符串表示，便于日志输出和调试。 |

### 3. 主要函数/方法
该文件主要定义了一个类及其成员方法，没有独立的工具类函数。所有方法都作为 `PDCForm` 类的一部分。

### 4. 对外依赖与交互

`PDCForm.java` 依赖于多个外部库和项目内部的其他类，并通过它们实现其功能：

*   **标准 Java 库**:
    *   `java.io.Serializable`: 实现序列化，支持对象在网络传输或持久化存储。
    *   `java.util.ArrayList`, `java.util.LinkedHashMap`, `java.util.List`, `java.util.Map`: 用于处理集合数据，例如存储表单字段列表 (`meta`) 和操作动作列表 (`actions`)。`getFieldMap()` 方法利用 `LinkedHashMap` 维持字段顺序。

*   **项目内部核心公共库 (`cmn` packages)**:
    *   `cmn.anotation.ClassDeclare`, `cmn.anotation.FieldDeclare`: 自定义注解，用于在编译期或运行时提供类和字段的元数据（如中文标签、用途、开发者信息等）。这对于构建元数据驱动的系统（如自动化表单生成、文档或代码分析工具）非常关键。
    *   `cmn.util.NullUtil`: 用于提供空安全操作的工具类，例如在 `getFieldMap()` 方法中安全地处理 `meta` 列表可能为 `null` 的情况。

*   **项目内部业务数据模型 (`gpf.adur.data` packages)**:
    *   `gpf.adur.data.Form`: `PDCForm` 的父类。`PDCForm` 继承了 `Form` 的核心机制，即通过 `getString()`, `getLong()`, `getBoolean()`, `getAssociationByCode()`, `getAssociationsByCode()`, `setAttrValueByCode()` 等方法来管理和存取表单的通用属性。这意味着 `Form` 类可能内部维护一个通用的属性映射（如 `Map<String, Object>`），`PDCForm` 在此基础上提供了类型安全和语义化的访问接口。
    *   `gpf.adur.data.AssociationData`: 用于表示关联数据，例如创建人、操作人、分配人等，这些通常是关联到用户、组织或其他实体的数据。
    *   `gpf.adur.data.FormField`: 用于定义表单中的单个字段的结构和属性（如字段代码、类型、显示名称等）。`PDCForm` 通过 `List<FormField> meta` 属性来管理其包含的所有表单字段。

*   **项目内部数据传输对象 (`gpf.dto.model.data` packages)**:
    *   `gpf.dto.model.data.FormPrivilegeDto`: 一个数据传输对象，封装了表单的权限信息。`PDCForm` 通过此对象来管理用户对表单的访问和操作权限。

*   **项目内部国际化/常量 (`gpf.i18n` packages)**:
    *   `gpf.i18n.GpfConst`: 提供项目范围内的常量。此处使用了 `GpfConst.STEP_UUID` 来初始化 `OpLogUuid` 常量，表明该常量与步骤UUID相关。

**交互方式**:
`PDCForm` 主要通过其继承的父类 `Form` 提供的方法，与其内部维护的通用属性集合进行交互。对于 `pdfUuid`、`meta`、`actions`、`formPrivilege` 和 `modified` 这几个 `PDCForm` 特有的属性，它直接通过自身的成员变量进行管理。外部系统或业务逻辑将通过 `PDCForm` 的Getter/Setter方法来填充或读取流程节点表单的数据、动作和权限信息，然后将 `PDCForm` 实例传递给流程引擎或其他服务进行处理。

