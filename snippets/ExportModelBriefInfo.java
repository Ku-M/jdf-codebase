### 1. 文件核心功能
`ExportModelBriefInfo.java` 文件的主要职责是封装和管理一个数据导出模型（或数据模块）的概要信息。它作为数据传输对象（DTO）或值对象（VO），用于表示用户或系统在执行数据导出操作时，对某个特定“模型”或“模块”的选择配置。

该类扮演的角色是：
*   **配置载体**: 承载一个导出模型的基本信息（如ID、标签、描述）以及其关联数据的导出模式（全部、无、部分）。
*   **状态管理**: 通过 `exportModel` 和 `dataExportMode` 字段，明确指示该模型是否被选中导出，以及其包含的数据的导出策略。
*   **配置合并逻辑**: 提供 `mergeModelInfo` 方法，支持将多个导出配置的逻辑合并，这在需要整合不同来源的导出需求时非常有用（例如，不同用户角色或不同模块的导出权限合并）。
*   **可序列化**: 实现 `Serializable` 接口，表明该对象可以被序列化为字节流，方便进行网络传输、持久化存储或跨进程通信。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class ExportModelBriefInfo` | `Serializable` | 封装一个导出模型的概要配置信息，包括模型本身的导出状态（是否导出）及其关联数据的具体导出模式（全部、部分、不导出）。提供配置合并功能。 |

#### 方法与属性详情

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `serialVersionUID` | `long` | 用于序列化的版本控制ID。 |
| `DATA_EXPORT_ALL` | `String` (常量) | 定义数据导出模式：全部数据。 |
| `DATA_EXPORT_NONE` | `String` (常量) | 定义数据导出模式：不导出任何数据。 |
| `DATA_EXPORT_PART` | `String` (常量) | 定义数据导出模式：导出部分数据。 |
| `id` | `String` | 模型的唯一标识符。 |
| `label` | `String` | 模型的显示名称或标签。 |
| `description` | `String` | 模型的详细描述。 |
| `exportModel` | `boolean` | 指示整个模型是否被选中导出。默认为`true`。 |
| `dataExportMode` | `String` | 关联数据的导出模式，取值为 `DATA_EXPORT_ALL`, `DATA_EXPORT_NONE`, `DATA_EXPORT_PART`。默认为 `DATA_EXPORT_NONE`。 |
| `dataList` | `List<ExportDataBriefInfo>` | 当 `dataExportMode` 为 `DATA_EXPORT_PART` 时，存储具体要导出的数据项的简要信息列表。 |
| `getId()` | `String` | 获取模型的ID。 |
| `setId(String id)` | `ExportModelBriefInfo` | 设置模型的ID，支持链式调用。 |
| `getLabel()` | `String` | 获取模型的标签。 |
| `setLabel(String label)` | `ExportModelBriefInfo` | 设置模型的标签，支持链式调用。 |
| `getDescription()` | `String` | 获取模型的描述。 |
| `setDescription(String description)` | `ExportModelBriefInfo` | 设置模型的描述，支持链式调用。 |
| `isExportModel()` | `boolean` | 判断模型是否被选中导出。 |
| `setExportModel(boolean exportModel)` | `ExportModelBriefInfo` | 设置模型是否选中导出，支持链式调用。 |
| `getDataExportMode()` | `String` | 获取数据导出模式。 |
| `isExportAllData()` | `boolean` | 判断数据导出模式是否为“全部”。 |
| `isExportNoneData()` | `boolean` | 判断数据导出模式是否为“不导出”。 |
| `isExportPartData()` | `boolean` | 判断数据导出模式是否为“部分”。 |
| `setDataExportMode(String dataExportMode)` | `ExportModelBriefInfo` | 设置数据导出模式，支持链式调用。 |
| `getDataList()` | `List<ExportDataBriefInfo>` | 获取要导出的数据项列表。 |
| `setDataList(List<ExportDataBriefInfo> dataList)` | `ExportModelBriefInfo` | 设置要导出的数据项列表，支持链式调用。 |
| `getDataMap()` | `Map<String,ExportDataBriefInfo>` | 将 `dataList` 转换为一个以数据项ID为键的Map，方便通过ID快速查找数据项。 |
| `addData(ExportDataBriefInfo dataInfo)` | `void` | 向 `dataList` 中添加一个数据项。如果列表中已存在相同ID的数据项，则不重复添加。 |
| `mergeModelInfo(ExportModelBriefInfo modelInfo)` | `void` | 将另一个 `ExportModelBriefInfo` 对象的配置信息合并到当前对象。处理逻辑优先级：`ALL` > `PART` > `NONE`。如果合并源为 `PART` 模式，且当前对象也为 `PART` 模式，则会合并两者的数据项列表。 |

### 3. 主要函数/方法 (如果适用)
本文件不包含独立的工具类函数，所有核心逻辑都封装在 `ExportModelBriefInfo` 类的方法中。

### 4. 对外依赖与交互
*   **`java.io.Serializable`**:
    *   **依赖**: Java标准库接口。
    *   **交互**: 通过实现此接口，`ExportModelBriefInfo` 类的实例可以被序列化（转换为字节流）和反序列化，从而支持对象在网络传输、磁盘存储或跨JVM进程间的传递。
*   **`java.util.ArrayList`, `java.util.LinkedHashMap`, `java.util.List`, `java.util.Map`**:
    *   **依赖**: Java标准库集合框架。
    *   **交互**: `ExportModelBriefInfo` 使用 `List` (`dataList`) 来存储多个 `ExportDataBriefInfo` 对象，表示部分导出模式下的具体数据项。`getDataMap()` 方法利用 `LinkedHashMap` 将 `dataList` 转换为 Map 结构，以方便通过ID快速查找和处理数据项（例如在 `mergeModelInfo` 中合并数据）。
*   **`com.kwaidoo.ms.tool.CmnUtil`**:
    *   **依赖**: 项目内部或共享的公共工具库。
    *   **交互**: 主要使用 `CmnUtil.isStringEqual(String s1, String s2)` 方法进行字符串比较，例如在判断 `dataExportMode` 和 `addData` 方法中比较 `ExportDataBriefInfo` 的ID时。这表明字符串比较逻辑被统一封装，可能考虑了空值处理或忽略大小写等细节，提高了代码的健壮性和一致性。
*   **`ExportDataBriefInfo`**:
    *   **依赖**: 项目内部的另一个数据结构类。
    *   **交互**: `ExportModelBriefInfo` 通过组合（持有）`List<ExportDataBriefInfo>` 来表示一个模型中需要被“部分导出”的具体数据项。`ExportDataBriefInfo` 预期是用于描述单个可导出数据项的摘要信息（例如，可能包含ID、名称、状态等），它是 `ExportModelBriefInfo` 内部列表的元素类型。

