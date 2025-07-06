### 1. 文件核心功能
`FeDebugUtil.java` 文件是一个静态工具类，其核心职责是提供统一的方法来管理与应用程序运行时调试相关的配置开关。它允许其他部分的代码查询和设置这些开关的状态。

该文件在整个项目中扮演的角色是：
*   **调试配置管理器**: 集中管理“是否启用调试日志”和“是否模拟前端行为”这两个重要的调试配置。
*   **运行时行为控制**: 影响应用程序在运行时是否输出详细的调试信息，或者在特定场景下模拟前端行为，这对于开发和问题排查至关重要。
*   **解耦**: 将调试配置的存取逻辑从具体的业务逻辑中解耦出来，使业务代码更清晰，且调试配置可以动态调整。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class FeDebugUtil` | 无（作为静态工具类） | 提供静态方法，用于读取和设置应用程序的调试相关配置（如调试日志开关、前端模拟开关），这些配置存储在 `Context` 对象的缓存中。 |

#### 方法与属性详情
针对 `FeDebugUtil` 类，以下是其关键方法的详细信息：

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `public static boolean isEnableDebug(Context panelContext)` | `boolean` | 用于判断是否启用了调试日志功能。它尝试从传入的 `Context` 对象的缓存中获取键名为 `$EnableDebug` 的值，并将其转换为布尔类型。如果获取失败或值为非真，则返回 `false`。 |
| `public static void setEnableDebug(Context panelContext, boolean enableDebug)` | `void` | 用于设置调试日志的启用状态。它将布尔值 `enableDebug` 存入 `Context` 对象的缓存中，键名为 `$EnableDebug`。特别地，如果 `enableDebug` 为 `false`（即禁用调试），它还会移除缓存中键名为 `$Trace` 的值。 |
| `public static boolean isSimulateFrontEnd(Context panelContext)` | `boolean` | 用于判断是否启用了前端模拟行为。它从传入的 `Context` 对象的缓存中获取键名为 `$SimulateFrontEnd` 的值，并将其转换为布尔类型。如果获取失败或值为非真，则返回 `false`。 |
| `public static void setSimulateFrontEnd(Context panelContext, boolean isSimulateForntEnd)` | `void` | 用于设置前端模拟行为的启用状态。它将布尔值 `isSimulateForntEnd` 存入 `Context` 对象的缓存中，键名为 `$SimulateFrontEnd`。 |

### 3. 主要函数/方法 (如果适用)
本文件中的所有公共方法均已在“方法与属性详情”中描述。

### 4. 对外依赖与交互
`FeDebugUtil.java` 依赖于以下外部库或项目内的其他类：

*   **`com.kwaidoo.ms.tool.CmnUtil`**:
    *   **用途**: 导入 `CmnUtil` 工具类，具体使用了其静态方法 `getBoolean(Object value, boolean defaultValue)`。
    *   **交互**: `FeDebugUtil` 使用 `CmnUtil.getBoolean()` 方法来安全地将从 `Context` 缓存中取出的值（可能为 `Object` 类型）转换为布尔类型。这有助于避免 `ClassCastException` 或 `NullPointerException`，并提供默认值处理。

*   **`fe.cmn.app.Context`**:
    *   **用途**: 导入 `Context` 类。这个 `Context` 对象是所有方法操作的核心载体，它似乎提供了一个键值存储机制（类似缓存或属性集合）。
    *   **交互**:
        *   `FeDebugUtil` 通过 `panelContext.getCacheValue(String key)` 方法从 `Context` 对象中读取调试配置值。
        *   `FeDebugUtil` 通过 `panelContext.putCacheValue(String key, Object value)` 方法将调试配置值写入 `Context` 对象。
        *   当禁用调试时，它还会调用 `panelContext.removeCacheValue(String key)` 方法来清除特定的缓存值（如 `$Trace`）。
    *   这表明 `Context` 类在系统中扮演着一个重要的角色，可能是应用程序的上下文环境、会话状态或运行时配置的统一存储。`FeDebugUtil` 通过操作这个 `Context` 对象来影响整个应用程序的调试行为。

