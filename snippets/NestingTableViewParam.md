### 1. 文件核心功能
`NestingTableViewParam.java` 文件的核心功能是定义用于配置“嵌套表格视图”组件的参数。它继承自 `BaseTableViewParam`，旨在扩展基础表格参数，使其能够处理具有主从或父子数据关联的复杂表格展示场景。

该文件在项目中扮演的角色是：
*   **参数配置载体**: 作为前端组件（特别是表格视图）与后端或业务逻辑之间的数据传输对象（DTO），封装了渲染嵌套表格所需的所有参数。
*   **数据结构定义**: 明确了嵌套表格的数据结构，包括其自身的行数据 (`rows`)，以及与主数据关联的关键信息 (`masterClass`, `masterKey`, `masterField`)。
*   **UI行为配置**: 在构造函数中对父类的UI设置进行了初始化，例如禁用操作后显示弹窗提示，以提供更精细的UI控制。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class NestingTableViewParam` | `BaseTableViewParam` | 定义用于配置“嵌套表格视图”组件的参数，包括主从数据关联信息（`masterClass`, `masterKey`, `masterField`）和嵌套表格的行数据（`rows`）。它通过这些参数指导前端嵌套表格的渲染和数据绑定。 |

#### 方法与属性详情

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `serialVersionUID` | `private static final long` | Java序列化ID，用于确保序列化和反序列化过程中的版本兼容性。 |
| `masterClass` | `String` | 定义主数据的类名或标识符。在嵌套表格场景中，这通常指代父级数据的类型，用于建立数据关联。 |
| `masterKey` | `String` | 定义主数据的唯一标识符或主键值。这是父级数据中用于查找或关联其下嵌套子数据的关键字段。 |
| `masterField` | `String` | 定义主数据中与嵌套数据关联的特定字段名。例如，父对象中存储子对象列表的字段名称。 |
| `rows` | `List<Form>` | 存储嵌套表格的实际行数据。每一行被封装为一个 `Form` 对象，表示一个数据记录。 |
| `NestingTableViewParam()` | `构造函数` | 类的构造函数。在实例化时，调用 `getSetting().setIsOpShowPopToast(false)`，将父类 `BaseTableViewParam` 中与操作相关的设置（例如操作成功后是否显示弹出提示）默认设置为不显示，优化用户体验。 |
| `getRows()` | `List<Form>` | 获取嵌套表格的行数据列表。 |
| `setRows(List<Form> rows)` | `NestingTableViewParam` | 设置嵌套表格的行数据。此方法返回当前对象实例（`this`），支持链式调用。 |
| `getMasterClass()` | `String` | 获取主数据的类名。 |
| `setMasterClass(String masterClass)` | `NestingTableViewParam` | 设置主数据的类名。支持链式调用。 |
| `getMasterKey()` | `String` | 获取主数据的唯一标识符。 |
| `setMasterKey(String masterKey)` | `NestingTableViewParam` | 设置主数据的唯一标识符。支持链式调用。 |
| `getMasterField()` | `String` | 获取主数据中与嵌套数据关联的字段名。 |
| `setMasterField(String masterField)` | `NestingTableViewParam` | 设置主数据中与嵌套数据关联的字段名。支持链式调用。 |

### 3. 主要函数/方法 (如果适用)
不适用。此文件主要定义一个数据传输/参数配置类，不包含独立的工具函数或核心业务逻辑方法。其主要功能通过类的属性和标准的getter/setter方法体现。

### 4. 对外依赖与交互
`NestingTableViewParam.java` 依赖于以下外部库或项目内部的其他类：

*   **`java.util.List`**: 这是Java标准库中的接口，用于定义和操作 `rows` 属性，表明嵌套表格的数据是以列表形式组织的。该类通过此接口与Java集合框架进行交互。
*   **`gpf.adur.data.Form`**: 这是一个项目内部定义的类，很可能代表一个通用的数据表单或数据记录结构。`NestingTableViewParam` 通过 `List<Form> rows` 持有表格的行数据，这意味着它与 `Form` 对象密切相关，并负责承载和传递这些数据。当此参数对象被构建时，通常会填充一系列 `Form` 实例作为表格的实际内容。
*   **`gpf.dc.basic.fe.component.param.BaseTableViewParam`**: 这是 `NestingTableViewParam` 的直接父类。它继承了 `BaseTableViewParam` 定义的所有通用表格参数和方法。在构造函数中，通过调用 `getSetting().setIsOpShowPopToast(false)` 与父类提供的配置机制进行交互，以设置特定的UI行为。这种继承关系表明 `NestingTableViewParam` 是对基础表格参数的一种特殊化和扩展，专注于处理嵌套表格的特定需求。

