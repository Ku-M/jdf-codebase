### 1. 文件核心功能
`WidgetParam.java`文件的核心功能是定义一个抽象基类，用于承载Web UI组件（或称“Widget”）在运行时和组件间传递的参数。它封装了组件所需的通用信息，如内部上下文数据、回调设置、指令监听器、组件唯一标识符（widgetId）、面板全局标识符（panelGlobalKey和srcPanelGlobalKey）以及用户操作事务ID（opTransId和lastOpTransId）。该类的设计目标是提供一个统一且可扩展的参数载体，方便组件间的通信、状态管理和事件回调机制的实现。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public abstract class WidgetParam` | `fe.cmn.data.FePojo`, `java.io.Serializable` | 作为Web UI组件传递参数的抽象基类。它定义了组件运行时和组件间通信所需的一系列通用参数及其访问方法。作为抽象类，它要求具体的组件参数类继承并扩展其功能，以实现强类型参数定义。它支持参数上下文传递、回调设置、指令监听、组件身份识别以及操作事务追踪。 |

#### 方法与属性详情

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `serialVersionUID` | `long` | 序列化版本唯一标识符，用于类在序列化和反序列化过程中的兼容性验证。 |
| `context` | `Map<String,Object>` | 用于在组件（页面）内部传递的通用参数，不建议用于页面间传递，建议页面间参数通过继承此基类并进行强类型声明。 |
| `callBackMap` | `Map<String,CallBackSetting>` | 存储回调设置的映射，键通常是回调的标识，值是具体的`CallBackSetting`对象，定义了回调的行为。 |
| `commandCallbackLsnrs` | `List<CommandCallbackListener>` | 指令监听回调列表。当当前组件触发指定指令执行结束时，列表中注册的监听器将被触发执行。 |
| `invokeClass` | `String` | 存储需要通过反射机制调用的界面类的全限定名。 |
| `widgetId` | `String` | 该组件的唯一标识ID，通常由其父面板或上层组件在初始化时指定并往下层传递。 |
| `panelGlobalKey` | `String` | 组件当前所属面板的全局唯一标识符，用于在组件调用时传递面板信息。 |
| `srcPanelGlobalKey` | `String` | 组件的源面板的全局唯一标识符，用于在当前组件中获取调用来源面板的信息。 |
| `opTransId` | `String` | 当前用户操作的事务标识符，通过`OperateTransaction.getTransId()`获取，用于追踪用户操作链。 |
| `lastOpTransId` | `String` | 上一个用户操作的事务标识符，通过`OperateTransaction.getLastTransId()`获取。 |
| `getContext()` | `Map<String, Object>` | 获取内部上下文参数映射。 |
| `setContext(Map<String, Object> context)` | `WidgetParam` | 设置内部上下文参数映射，支持链式调用。 |
| `getCallBackMap()` | `Map<String, CallBackSetting>` | 获取回调设置映射。 |
| `setCallBackMap(Map<String, CallBackSetting> callBackMap)` | `WidgetParam` | 设置回调设置映射，支持链式调用。 |
| `getInvokeClass()` | `String` | 获取反射调用的界面类名。 |
| `setInvokeClass(String invokeClass)` | `WidgetParam` | 设置反射调用的界面类名，支持链式调用。 |
| `getWidgetId()` | `String` | 获取组件ID。 |
| `setWidgetId(String widgetId)` | `WidgetParam` | 设置组件ID，支持链式调用。 |
| `getPanelGlobalKey()` | `String` | 获取当前面板全局标识。 |
| `setPanelGlobalKey(String panelGlobalKey)` | `void` | 设置当前面板全局标识。 |
| `getSrcPanelGlobalKey()` | `String` | 获取源面板全局标识。 |
| `setSrcPanelGlobalKey(String srcPanelGlobalKey)` | `void` | 设置源面板全局标识。 |
| `getCommandCallbackLsnrs()` | `List<CommandCallbackListener>` | 获取指令回调监听器列表。 |
| `setCommandCallbackLsnrs(List<CommandCallbackListener> commandCallbackLsnrs)` | `WidgetParam` | 设置指令回调监听器列表，支持链式调用。 |
| `addCommandCallbackLsnr(CommandCallbackListener commandCallbackLsnr)` | `void` | 向指令回调监听器列表中添加一个监听器。如果列表中已存在同名的监听器（通过`getName()`比较），则更新现有监听器；否则，添加新监听器。包含对集合类型污染问题的修复逻辑。 |
| `searchCommandCallbackLsnr(String command)` | `CommandCallbackListener` | 根据指令名称（`command`）搜索并返回对应的`CommandCallbackListener`。如果列表为空或未找到匹配的监听器，则返回`null`。 |
| `getOpTransId()` | `String` | 获取用户操作事务标识符。 |
| `getLastOpTransId()` | `String` | 获取上一个用户操作事务标识符。 |
| `allockWidgetIdIfNull()` | `void` | 如果`widgetId`属性当前为空（`null`或空字符串），则使用`ToolUtilities.allockUUIDWithUnderline()`为其分配一个带有下划线的UUID。 |
| `allockPanelGlobalKeyIfNull()` | `void` | 如果`panelGlobalKey`属性当前为空，则使用`ToolUtilities.allockUUIDWithUnderline()`为其分配一个带有下划线的UUID。 |

### 3. 主要函数/方法 (如果适用)
该文件主要定义了一个抽象类`WidgetParam`及其成员方法，不包含独立的工具函数。

### 4. 对外依赖与交互
`WidgetParam`文件导入并依赖以下重要的外部库或项目内的其他类：

*   **`java.io.Serializable`**: 实现了此接口，表明`WidgetParam`的实例可以被序列化，这对于跨进程通信、网络传输或持久化存储至关重要。
*   **`java.util.*` (ArrayList, HashMap, List, Map)**: 广泛使用了Java集合框架，作为内部数据结构来存储通用上下文参数 (`context`)、回调设置 (`callBackMap`) 和指令回调监听器 (`commandCallbackLsnrs`)。
*   **`com.kwaidoo.ms.tool.ToolUtilities`**:
    *   通过`ToolUtilities.allockUUIDWithUnderline()`方法，用于为`widgetId`和`panelGlobalKey`生成唯一的UUID标识符。这表明系统有一个通用的工具集来处理各种工具函数，特别是ID生成。
*   **`com.leavay.ms.tool.CmnUtil`**:
    *   通过`CmnUtil.isStringEqual()`、`CmnUtil.isCollectionEmpty()`和`CmnUtil.isStringEmpty()`等方法，提供字符串和集合的判空及比较功能。这些实用方法在内部逻辑（如`addCommandCallbackLsnr`、`searchCommandCallbackLsnr`、`allockWidgetIdIfNull`等）中用于参数校验和条件判断。
*   **`fe.cmn.data.FePojo`**:
    *   `WidgetParam`继承自`FePojo`。这暗示`FePojo`可能是框架中定义的基础数据对象（Plain Old Java Object），可能包含了通用的属性、方法或约定，例如序列化、通用数据处理或与ORM/数据层的集成。
*   **`fe.util.OperateTransaction`**:
    *   通过静态方法`OperateTransaction.getTransId()`和`OperateTransaction.getLastTransId()`初始化`opTransId`和`lastOpTransId`。这表明`WidgetParam`与业务操作事务管理机制紧密集成，能够追踪和传递用户操作的上下文信息。
*   **`fe.util.component.extlistener.CommandCallbackListener`**:
    *   `WidgetParam`内部持有`CommandCallbackListener`的列表，并提供了添加和搜索这些监听器的方法。这表明`WidgetParam`是组件指令回调机制中的一个核心载体，允许组件在特定指令执行完毕后触发预定义的回调逻辑。
*   **`fe.util.component.param.CallBackSetting`**:
    *   `callBackMap`属性的Value类型是`CallBackSetting`，这意味着`WidgetParam`能够承载关于如何执行回调的详细配置。

**交互总结**:
`WidgetParam`作为组件参数的基类，通过组合和继承的方式，聚合了多种与UI组件状态、交互和业务逻辑相关的信息。它利用外部工具类进行辅助操作（如UUID生成和数据校验），并与框架内的事务管理、基础数据模型以及自定义的事件/回调机制紧密集成。其抽象设计鼓励子类通过继承来定义更具体的、强类型的组件参数，形成一个灵活且可扩展的组件参数传递体系。

