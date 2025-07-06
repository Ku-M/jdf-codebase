这是一个文件级的技术知识库，用于帮助AI更好地理解Java代码。

---

### 1. 文件核心功能
`TableBuilder.java` 文件的主要职责是作为一个配置对象或数据载体（POJO），用于定义和管理表格中需要被过滤或屏蔽的列。它在整个项目中扮演的角色是提供一种标准化的方式来指定哪些列在数据处理、显示或传输时应该被忽略，从而实现数据的定制化过滤。它本身不执行过滤操作，而是提供过滤的规则。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class TableBuilder` | `FePojo` | 这是一个POJO类，用于封装表格列的过滤规则。它维护一个字符串列表，该列表包含所有需要被忽略或屏蔽的列名。同时，它继承了 `FePojo` 的特性，可能涉及数据的序列化或二进制处理。 |

#### 方法与属性详情

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `serialVersionUID` | `static final long` | Java序列化机制中的一个版本UID，用于在序列化和反序列化过程中验证类的兼容性。 |
| `columnFilter` | `List<String>` | 实例属性，存储需要被过滤或屏蔽的列名列表。 |
| `getColumnFilter()` | `List<String>` | 获取当前设置的列过滤列表。 |
| `setColumnFilter(String ... columnFilter)` | `TableBuilder` (返回自身) | 设置列过滤列表。接受可变参数的字符串数组，并使用 `ToolBasic.newArrayList()` 工具方法将其转换为 `List<String>`。支持链式调用。 |
| `setColumnFilter(List<String> columnFilter)` | `TableBuilder` (返回自身) | 设置列过滤列表。直接接受一个 `List<String>` 对象。支持链式调用。 |
| `isColumnIgnore(String col)` | `boolean` | 判断给定的列名 (`col`) 是否存在于 `columnFilter` 列表中，即该列是否应该被忽略。在执行判断前会检查 `columnFilter` 是否为空。 |
| `setSelfBinaryData()` | `TableBuilder` (返回自身) | 调用父类 `FePojo` 中定义的 `setBinaryDataIgnoreErr(this)` 方法。这表明 `TableBuilder` 实例自身可能会被转换为二进制数据或进行相关的二进制数据处理，并且该过程会忽略可能发生的错误。支持链式调用。 |

### 3. 主要函数/方法 (如果适用)
该文件主要定义了一个类及其成员方法，不包含独立的工具函数。

### 4. 对外依赖与交互
这个文件导入了以下重要的外部库或项目内的其他类：

*   **`java.util.List`**: 这是一个Java标准库接口，用于处理 `columnFilter` 属性，允许存储和操作列名的集合。
*   **`com.leavay.common.util.ToolBasic`**: 这是一个项目内部或公共的工具类库。`TableBuilder` 使用了其 `newArrayList()` 方法来方便地将可变参数的字符串数组转换为 `ArrayList`。这表明 `ToolBasic` 提供了一些常见的、方便的集合操作工具。
*   **`fe.cmn.data.FePojo`**: 这是一个项目内部的基类。`TableBuilder` 继承自 `FePojo`，这意味着 `TableBuilder` 实例具备 `FePojo` 定义的通用数据处理能力，例如 `setBinaryDataIgnoreErr()` 方法，它可能用于处理POJO的二进制序列化或数据持久化。这表明 `FePojo` 是一个用于定义通用数据对象的基础抽象。

**交互方式**:
*   `TableBuilder` 与 `java.util.List` 直接交互，作为其内部数据的存储结构。
*   `TableBuilder` 在构造其 `columnFilter` 列表时，通过调用 `ToolBasic.newArrayList()` 方法与 `com.leavay.common.util.ToolBasic` 交互。
*   `TableBuilder` 通过继承 `FePojo` 并调用其 `setBinaryDataIgnoreErr()` 方法，与 `fe.cmn.data.FePojo` 间接交互，利用了父类提供的二进制数据处理能力。

