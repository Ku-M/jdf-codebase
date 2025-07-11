# Analysis for: gpf_dc_pmc\src\core\fe\pmc\component\param\CustomAssocDataQueryParam.java

## Original File Path
`D:\TMP\CLASS_BUS_PROJECT\gpf_dc_pmc\src\core\fe\pmc\component\param\CustomAssocDataQueryParam.java`

## Extracted Snippets & Analysis
好的，作为一名资深的软件架构师，我已仔细审阅了您提供的代码和核心规则。我将从 `CustomAssocDataQueryParam` 类中提取出符合要求的、高质量的代码样例，旨在帮助AI编程助手学习如何正确、高效地使用类似参数对象的API。

以下是提取出的代码样例：

---

### 样例一：实例化参数对象

**核心任务：** 创建一个 `CustomAssocDataQueryParam` 的新实例。

**可靠性说明：** `CustomAssocDataQueryParam` 是一个具体的公共类，可以直接通过 `new` 关键字实例化，无需依赖任何外部未知上下文。

```java
// 创建一个新的CustomAssocDataQueryParam实例
CustomAssocDataQueryParam queryParam = new CustomAssocDataQueryParam();
```

---

### 样例二：设置布尔类型属性

**核心任务：** 配置参数对象中的布尔类型属性（例如：`allowManageAssocData`）。

**可靠性说明：** 该操作在已实例化的对象上执行，并接受通用的 `boolean` 类型值作为参数，完全独立可靠。

```java
// 假设您已经创建了一个CustomAssocDataQueryParam实例
CustomAssocDataQueryParam queryParam = new CustomAssocDataQueryParam();

// 设置是否允许管理关联数据
queryParam.setAllowManageAssocData(your_boolean_value); // 例如：true 或 false
```

---

### 样例三：设置字符串类型属性

**核心任务：** 配置参数对象中的字符串类型属性（例如：`tableViewModelId`）。

**可靠性说明：** 该操作在已实例化的对象上执行，并接受通用的 `String` 类型值作为参数，完全独立可靠。

```java
// 假设您已经创建了一个CustomAssocDataQueryParam实例
CustomAssocDataQueryParam queryParam = new CustomAssocDataQueryParam();

// 设置表格视图的模型ID
queryParam.setTableViewModelId("your_table_view_model_id"); // 例如："model_123"
```

---

### 样例四：链式配置多个属性（Fluent API模式）

**核心任务：** 利用链式调用（Fluent API）一次性配置参数对象的多个属性，构建一个完整的查询参数对象。

**可靠性说明：** `CustomAssocDataQueryParam` 的所有 Setter 方法都返回 `this`，支持流畅的链式调用。此样例展示了如何高效地初始化和配置一个参数对象，所有参数都为通用Java类型。这是一种非常常见的API使用模式，对AI学习非常有价值。

```java
// 使用链式调用，一次性配置CustomAssocDataQueryParam的所有相关属性
CustomAssocDataQueryParam queryParam = new CustomAssocDataQueryParam()
    .setAllowManageAssocData(your_boolean_value_for_manage_assoc_data) // 例如：true
    .setAllowViewAssocData(your_boolean_value_for_view_assoc_data)     // 例如：false
    .setTableViewModelId("your_table_view_model_id")
    .setTableViewCode("your_table_view_code")
    .setFormViewModelId("your_form_view_model_id")
    .setFormViewCode("your_form_view_code");
```