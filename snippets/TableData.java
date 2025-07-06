我们来分析一下 `TableData.java` 文件。

---

### 1. 文件核心功能
`TableData.java` 文件的核心职责是**封装和管理用于表示嵌套表格的数据集合**。它主要作为表单中属性类型为“嵌套模型 (NestingModel)”的值类型。

在整个项目中，它扮演着以下角色：
*   **数据容器**: 存储一系列 `Form` 对象，每个 `Form` 对象代表表格中的一行数据。
*   **数据操作接口**: 提供对嵌套表格数据进行增、删、改、查等操作的方法。
*   **数据序列化**: 作为可序列化的对象，便于数据的存储、传输和恢复。
*   **业务数据结构**: 服务于复杂的动态表单或数据管理场景，尤其是在需要表示一对多关系或可重复数据条目时。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class TableData` | `java.io.Serializable` | 作为表单中“嵌套模型 (NestingModel)”属性的值类型，封装和管理一系列 `Form` 对象（代表表格的行数据）。提供对行数据的增、删、改、查以及转换为映射结构的功能。 |

#### 方法与属性详情

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `serialVersionUID` | `long` | Java序列化版本UID。 |
| `MasterClass` | `String` | 来自 `CDoSlave` 的常量，可能用于标识主类。 |
| `MasterKey` | `String` | 来自 `CDoSlave` 的常量，可能用于标识主键。 |
| `MasterField` | `String` | 来自 `CDoSlave` 的常量，可能用于标识主字段。 |
| `OrderSeq` | `String` | 来自 `NestingData` 的常量，可能用于定义嵌套数据的排序序列。 |
| `formModelId` | `String` | 标识该表格数据所属的表单模型ID。 |
| `rows` | `List<Form>` | 存储表格的行数据，每一行都是一个 `Form` 对象。 |
| `TableData()` | 构造函数 | 无参构造函数。 |
| `TableData(String formModelId)` | 构造函数 | 根据表单模型ID初始化 `TableData`。 |
| `TableData(List<Form> rows)` | 构造函数 | 根据 `Form` 列表初始化 `TableData`，并尝试从第一个 `Form` 对象获取 `formModelId`。 |
| `getFormModelId()` | `String` | 获取表格的表单模型ID。 |
| `setFormModelId(String formModelId)` | `void` | 设置表格的表单模型ID。 |
| `getRows()` | `List<Form>` | 获取表格的所有行数据。 |
| `setRows(List<Form> rows)` | `TableData` | 设置表格的所有行数据，并返回当前对象以支持链式调用。 |
| `size()` | `int` | 获取表格的行数。 |
| `isEmtpy()` | `boolean` | 判断表格是否为空。 |
| `add(Form row)` | `TableData` | 向表格添加一行数据，并返回当前对象。 |
| `allAll(List<Form> rows)` | `TableData` | 向表格添加多行数据，并返回当前对象。 |
| `delete(int index)` | `TableData` | 根据索引删除一行数据，并返回当前对象。 |
| `delete(Form row)` | `TableData` | 根据 `Form` 对象的UUID删除一行数据（使用迭代器安全删除），并返回当前对象。 |
| `deleteByUuids(String... uuids)` | `TableData` | 根据一个或多个UUIDs批量删除行数据，通过先转换为Map再过滤的方式提高效率，并返回当前对象。 |
| `clear()` | `TableData` | 清空表格所有行数据，并返回当前对象。 |
| `getData(int index)` | `Form` | 根据索引获取一行数据。 |
| `getData(String formID)` | `Form` | 根据 `Form` 对象的UUID获取一行数据。 |
| `update(Form row)` | `TableData` | 根据 `Form` 对象的UUID更新一行数据；如果UUID不存在则添加为新行，并返回当前对象。 |
| `getRowUuidMap()` | `Map<String,Form>` | 将 `rows` 转换为 `Map`，其中键是 `Form` 对象的UUID，值是 `Form` 对象。使用 `LinkedHashMap` 保持插入顺序。 |
| `getRowMapByFieldCode(String fieldCode)` | `Map<String,Form>` | 将 `rows` 转换为 `Map`，其中键是指定字段 (`fieldCode`) 的值，值是 `Form` 对象。使用 `LinkedHashMap` 保持插入顺序。 |

### 3. 主要函数/方法 (如果适用)
本文件主要定义了一个类 `TableData` 及其成员方法，不包含独立的工具类函数。

### 4. 对外依赖与交互
`TableData.java` 依赖于以下外部库或项目内的其他类：

*   **Java标准库**:
    *   `java.io.Serializable`: 用于实现对象的序列化，使得 `TableData` 实例可以在网络传输或持久化存储后恢复。
    *   `java.util.ArrayList`, `java.util.Iterator`, `java.util.LinkedHashMap`, `java.util.List`, `java.util.Map`: Java集合框架的核心类，用于存储、遍历和操作表格的行数据 (`rows`)，特别是使用 `List` 存储顺序数据，以及 `Map` 快速查找数据。
    *   `java.util.stream.Collectors`: 用于Java 8 Stream API，特别是 `Collectors.toMap` 方法，简化了将 `List<Form>` 转换为 `Map<String, Form>` 的操作。

*   **项目内部/特定框架依赖**:
    *   `com.cdao.model.CDoSlave`: 一个自定义模型类，`TableData` 使用其定义的静态常量 `MasterClass`, `MasterKey`, `MasterField`。这表明 `TableData` 可能在一个更宏大的数据访问或模型层框架中使用，与主从数据关系相关。
    *   `com.kwaidoo.ms.tool.CmnUtil`: 一个通用的工具类，`TableData` 广泛使用其静态方法进行各种实用操作，例如：
        *   `CmnUtil.isCollectionEmpty()`: 判断集合是否为空。
        *   `CmnUtil.isStringEqual()`: 安全地比较字符串是否相等（可能处理null值）。
        *   `CmnUtil.getString()`: 获取字符串表示（可能处理null或非字符串类型）。
        *   这种依赖确保了数据操作的健壮性和一致性。
    *   `cmn.anotation.ClassDeclare`: 一个自定义注解，用于为 `TableData` 类提供元数据，如 `label`, `what`, `why`, `how`, `developer`, `version`, `createTime`, `updateTime`。这是一种项目内部的文档和代码生成机制，增强了代码的可读性和可维护性。
    *   `gpf.md.slave.NestingData`: 一个自定义数据模型或常量定义类，`TableData` 使用其定义的静态常量 `OrderSeq`。这进一步明确了 `TableData` 是为处理“嵌套数据”而设计的，并且可能与 `NestingData` 定义的结构或规则相关。
    *   `gpf.adur.data.Form`: **最核心的内部依赖**。`TableData` 内部的 `rows` 属性是 `List<Form>` 类型。这意味着 `TableData` 管理的每一行数据都是一个 `Form` 对象。`Form` 类本身很可能代表了一个动态表单实例或一个数据记录，它提供了获取UUID (`getUuid()`)、模型ID (`getFormModelId()`) 和属性值 (`getAttrValueByCode()`) 的方法，这些方法被 `TableData` 用来标识和操作行数据。

**交互方式**:
`TableData` 与这些依赖项的交互主要是通过调用它们的静态方法（如 `CmnUtil`）或使用它们定义的常量（如 `CDoSlave`, `NestingData`）。更重要的是，它**组合**了 `Form` 对象，将其作为其核心数据结构进行存储和管理，并通过 `Form` 对象的方法来实现对嵌套数据的具体操作。整个设计体现了数据模型的分层和职责分离。

