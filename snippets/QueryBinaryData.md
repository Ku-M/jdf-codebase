以下是对 `QueryBinaryData.java` 文件的技术分析：

---

### 1. 文件核心功能
`QueryBinaryData.java` 文件的核心职责是提供一个机制，用于从应用程序的“面板上下文（PanelContext）”中查询特定“部件（widget）”的二进制数据。这些部件可以通过正则表达式或明确的ID列表进行匹配。它支持两种查询方式：
1.  获取经过反序列化后的Java对象数据。
2.  获取原始的二进制字节数组数据。

它在整个项目中扮演的角色是一个数据查询能力（Ability），封装了从运行时环境中获取和处理二进制数据的逻辑，并将其暴露为易于使用的静态方法。这使得其他业务逻辑可以方便地按需获取面板部件的配置或运行时数据。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class QueryBinaryData` | `BasicAbility<Map<String, Object>>` | 定义了查询面板部件二进制数据的能力。它包含用于指定查询条件的属性（正则表达式或ID列表）以及执行查询逻辑的方法。作为 `BasicAbility` 的子类，它很可能被一个框架或运行时环境（如 `PanelContext`）所调用和执行。 |

#### 方法与属性详情

**类：`QueryBinaryData`**

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `private static final long serialVersionUID` | `long` | Java序列化版本UID。 |
| `String widgetIdRegEx` | `String` | 用于匹配widget ID的正则表达式。例如：`"(ROW_ID:){1}"`。 |
| `String[] widgetIds` | `String[]` | 直接指定的widget ID列表。如果与 `widgetIdRegEx` 同时设置，则两者为“或”关系。 |
| `public String getWidgetIdRegEx()` | `String` | 获取 `widgetIdRegEx` 属性的值。 |
| `public QueryBinaryData setWidgetIdRegEx(String widgetIdRegEx)` | `QueryBinaryData` | 设置 `widgetIdRegEx` 属性的值，并返回当前对象，支持链式调用。 |
| `public String[] getWidgetIds()` | `String[]` | 获取 `widgetIds` 属性的值。 |
| `public void setWidgetIds(String[] widgetIds)` | `void` | 设置 `widgetIds` 属性的值。 |
| `public static Object queryOne(PanelContext ctx)` | `Object` | 查询符合条件的第一个widget的**反序列化对象**。不指定具体widget ID或正则表达式。 |
| `public static Object queryOne(PanelContext ctx, String widgetId)` | `Object` | 查询指定 `widgetId` 的widget的**反序列化对象**。 |
| `public static Map<String, Object> query(PanelContext ctx, String regularExp, String... widgetIds)` | `Map<String, Object>` | **核心查询方法**。根据正则表达式或widget ID列表查询符合条件的所有widget的**反序列化对象**。它会创建一个 `QueryBinaryData` 实例，设置查询参数，并通过 `PanelContext` 执行回调，然后对返回的二进制数据进行反序列化。 |
| `public static byte[] queryOneBytes(PanelContext ctx)` | `byte[]` | 查询符合条件的第一个widget的**原始字节数组**。不指定具体widget ID或正则表达式。 |
| `public static byte[] queryOneBytes(PanelContext ctx, String widgetId)` | `byte[]` | 查询指定 `widgetId` 的widget的**原始字节数组**。 |
| `public static Map<String, byte[]> queryBytes(PanelContext ctx, String regularExp, String... widgetIds)` | `Map<String, byte[]>` | **核心查询方法**。根据正则表达式或widget ID列表查询符合条件的所有widget的**原始字节数组**。与 `query` 方法类似，但不对二进制数据进行反序列化，直接返回 `byte[]`。 |

### 3. 主要函数/方法 (如果适用)
该文件主要通过类 `QueryBinaryData` 中的一系列静态方法提供功能，这些方法充当了工具函数。

| 函数名 | 参数 | 返回值 | 功能描述 |
| :--- | :--- | :--- | :--- |
| `query(PanelContext ctx, String regularExp, String... widgetIds)` | `ctx`: `PanelContext` <br> `regularExp`: `String` <br> `widgetIds`: `String...` | `Map<String, Object>` | 这是获取反序列化数据的主要入口。它封装了 `QueryBinaryData` 实例的创建、参数设置、通过 `PanelContext` 进行实际数据获取（通过回调机制），以及将获取到的 `BinPojo` 中的二进制数据反序列化为Java对象。 |
| `queryBytes(PanelContext ctx, String regularExp, String... widgetIds)` | `ctx`: `PanelContext` <br> `regularExp`: `String` <br> `widgetIds`: `String...` | `Map<String, byte[]>` | 这是获取原始二进制数据的主要入口。其逻辑与 `query` 类似，但跳过了反序列化步骤，直接返回 `BinPojo` 中的原始字节数组。 |
| `queryOne(PanelContext ctx, String widgetId)` | `ctx`: `PanelContext` <br> `widgetId`: `String` | `Object` | 用于便捷地查询单个指定widget的反序列化数据。 |
| `queryOneBytes(PanelContext ctx, String widgetId)` | `ctx`: `PanelContext` <br> `widgetId`: `String` | `byte[]` | 用于便捷地查询单个指定widget的原始二进制数据。 |

### 4. 对外依赖与交互
`QueryBinaryData` 类依赖于以下外部库或项目内部的其他类，并通过它们与系统其他部分进行交互：

*   **`java.util.HashMap`**, **`java.util.Map`**: Java标准库，用于存储和操作键值对数据结构，特别是在返回查询结果时。
*   **`com.leavay.common.util.javac.ClassFactory`**: 这是一个自定义或第三方工具类，用于执行对象的序列化和反序列化操作。在本文件中，`ClassFactory.unserialize()` 方法用于将获取到的二进制数据转换回Java对象。
*   **`fe.cmn.data.BasicAbility`**: `QueryBinaryData` 的父类。它定义了“能力”或“功能”的基本结构，很可能包含通用的生命周期方法、回调接口或参数设置（如 `setTimeout`）。这意味着 `QueryBinaryData` 是一个遵循特定框架规范的组件。
*   **`fe.cmn.data.BinPojo`**: 一个数据传输对象（DTO），用于封装二进制数据及其相关元信息。它提供了 `hasBinaryData()` 方法检查是否有实际数据，以及 `getBinaryBytes()` 方法获取原始字节数组。
*   **`fe.cmn.panel.PanelContext`**: 这是核心的运行时上下文对象。`QueryBinaryData` 通过调用 `ctx.callback(callback)` 方法与 `PanelContext` 进行交互，将自身（作为一个 `BasicAbility` 实例）传递给上下文，由上下文负责实际的数据检索和执行。`PanelContext` 充当了数据源的抽象层和执行器。

**交互方式总结：**
1.  `QueryBinaryData` 实例作为查询条件和回调对象，被传递给 `PanelContext`。
2.  `PanelContext` 执行实际的数据获取逻辑，并返回 `Map<String, BinPojo>`。
3.  `QueryBinaryData` 接收到 `BinPojo` 数据后，根据需要（是返回原始字节还是反序列化对象）进行进一步处理，利用 `ClassFactory` 进行反序列化。
4.  最终，处理后的数据以 `Map<String, Object>` 或 `Map<String, byte[]>` 的形式返回给调用者。

