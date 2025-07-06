作为一名资深的Java软件工程师，我对`LoadingMask.java`文件进行了如下技术知识库分析：

---

### 1. 文件核心功能
`LoadingMask.java` 文件的核心职责是提供一个通用的、静态的工具类，用于在富客户端（Rich Client）UI框架的特定面板或组件上显示和隐藏各种加载遮罩层。它封装了加载遮罩的创建、配置和展示逻辑，支持多种遮罩类型（文本提示、圆形进度条、线性进度条），并提供了同步和异步的操作接口。它在整个项目中扮演着用户体验增强的角色，通过友好的加载提示来改善用户等待时的感受。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class LoadingMask` | 无（工具类） | 提供静态方法，用于在用户界面组件上显示和隐藏各种加载遮罩层，包括文本提示、圆形进度条和线性进度条。支持同步和异步操作，并管理遮罩的默认样式和行为。 |

#### 方法与属性详情

针对 `LoadingMask` 类：

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `public static CColor defaultBgColor` | `CColor` | 静态属性，定义了遮罩层的默认背景颜色（半透明的灰色调），由 `FeStyleSetting` 初始化。 |
| `public static void show(PanelContext panelContext, String widgetId, LoadingMaskConfigDto config)` | `void` | 根据 `LoadingMaskConfigDto` 配置，在指定的 `widgetId` 组件上同步显示相应的加载遮罩（文本、圆形或线性）。这是一个高层入口。 |
| `public static void asyncShow(PanelContext panelContext, String widgetId, LoadingMaskConfigDto config)` | `void` | 与 `show` 方法类似，但以异步方式在指定的 `widgetId` 组件上显示加载遮罩。 |
| `public static void showTextProgress(PanelContext panelContext, ...)` (多重载) | `void` | 同步显示一个带有文本提示的加载遮罩。提供了不同参数组合的重载方法，允许自定义文本、最短显示时长和背景颜色。内部会构造 `BoxDto` 和 `LabelDto` 来实现文本布局。 |
| `public static void asyncShowTextProgress(PanelContext panelContext, ...)` | `void` | 异步显示一个带有文本提示的加载遮罩，功能与同步版本类似，但使用 `FeCallbackPool` 进行异步调度。 |
| `public static void showCircularProgress(PanelContext panelContext, ...)` (多重载) | `void` | 同步显示一个带有圆形进度条的加载遮罩。同样提供了重载以允许自定义最短显示时长。内部会构造 `BoxDto` 和 `CircularProgressIndicatorDto`。 |
| `public static void asyncShowCircularProgress(PanelContext panelContext, ...)` | `void` | 异步显示一个带有圆形进度条的加载遮罩。 |
| `public static void showLinearProgress(PanelContext panelContext, ...)` (多重载) | `void` | 同步显示一个带有线性进度条的加载遮罩。同样提供了重载以允许自定义最短显示时长。内部会构造 `BoxDto` 和 `LinearProgressIndicatorDto`。 |
| `public static void asyncShowLinearProgress(PanelContext panelContext, ...)` | `void` | 异步显示一个带有线性进度条的加载遮罩。 |
| `public static void hide(PanelContext panelContext, ...)` (多重载) | `void` | 同步隐藏指定 `widgetId` 或当前面板上的加载遮罩。 |
| `public static void asyncHide(PanelContext panelContext, String widgetId)` | `void` | 异步隐藏指定 `widgetId` 上的加载遮罩，通过 `FeCallbackPool` 调度。 |

### 3. 主要函数/方法

考虑到 `LoadingMask` 是一个静态工具类，其所有公共方法都是核心功能。上述表格已详细列出，这里再进行高层次的功能归纳。

| 函数名分类 | 参数 | 返回值 | 功能描述 |
| :--- | :--- | :--- | :--- |
| **通用显示/隐藏** | `PanelContext`, `String widgetId`, `LoadingMaskConfigDto config` | `void` | 提供基于配置的通用入口，根据 `config` 中的 `LoadingType` 分发到具体的显示方法。`hide` 负责移除遮罩。 |
| **同步显示** | `PanelContext`, `widgetId`, `minDisplayTime`, `loadingText`, `bgColor` 等不同组合 | `void` | 直接在当前线程中创建并显示遮罩层。适用于操作完成后立即移除的场景。它们通过 `PopMaskOfWidget.show()` 直接操作UI。 |
| **异步显示** | `PanelContext`, `widgetId`, `minDisplayTime`, `loadingText`, `bgColor` 等不同组合 | `void` | 将显示遮罩的任务加入到异步回调池 (`FeCallbackPool`) 中执行。适用于耗时操作前，不阻塞主UI线程的场景。 |
| **遮罩构建** | 内部实现 | `BoxDto` (返回) | 这些方法在内部构建 `BoxDto`、`LabelDto`、`CircularProgressIndicatorDto` 或 `LinearProgressIndicatorDto` 等UI组件的数据传输对象，并设置其样式和布局，最终将其作为遮罩内容传递给 `PopMaskOfWidget`。 |
| **错误处理与跟踪** | 内部实现 | `void` | 所有方法都包含了 `try-catch` 块来捕获潜在的 `FeInvalidPanelException` 或其他异常。在调试模式下会重新抛出，非调试模式下则记录警告日志。同时，集成了 `Tracer` 进行性能跟踪和日志记录。 |

### 4. 对外依赖与交互

`LoadingMask.java` 依赖于多个内部和外部库，主要用于UI组件的构建、面板操作、通用工具和日志跟踪。

*   **UI组件与面板框架依赖 (`fe.cmn.*`):**
    *   `fe.cmn.panel.PanelContext`: UI操作的上下文，用于定位和管理面板及组件。
    *   `fe.cmn.panel.BoxDto`, `fe.cmn.panel.MainAxisAlign`: 用于构建遮罩层的布局容器，遵循盒模型布局。
    *   `fe.cmn.panel.ability.PopMaskOfWidget`: **核心依赖**，该类负责将实际的遮罩层（通过 `OverlayDto` 封装的 `BoxDto`）添加到指定的UI组件上或从中移除。
    *   `fe.cmn.widget.*` (如 `LabelDto`, `CircularProgressIndicatorDto`, `LinearProgressIndicatorDto`, `OverlayDto`, `CLabelAlign`): 提供构成遮罩层具体视觉元素的DTOs（数据传输对象）。
    *   `fe.cmn.widget.decoration.*` (如 `DecorationDto`, `LabelDecorationDto`, `PositionedDto`): 用于定义遮罩背景、文本样式和定位方式的DTOs。
    *   `fe.cmn.data.CColor`: 项目自定义的颜色类，用于颜色处理。
    *   `fe.cmn.text.CTextStyle`, `fe.cmn.text.CFontWeight`: 用于定义文本样式的DTOs。
    *   `fe.cmn.exception.FeInvalidPanelException`: 项目自定义的异常类，用于处理无效面板操作。
    *   `fe.util.FeCallbackPool`: **异步操作的核心依赖**，用于将异步任务（如异步显示/隐藏遮罩）添加到队列中，等待UI线程处理，避免阻塞。
    *   `fe.util.FeDebugUtil`: 用于判断是否处于调试模式，从而决定异常的处理方式（抛出或警告）。
    *   `fe.util.style.FeStyleSetting`: 用于获取项目默认的样式设置，如默认背景颜色。

*   **通用工具库依赖 (`com.kwaidoo.ms.tool.*`, `cmn.util.*`):**
    *   `com.kwaidoo.ms.tool.CmnUtil`: 提供字符串工具方法，如 `isStringEmpty` 用于判断字符串是否为空。
    *   `com.kwaidoo.ms.tool.ToolUtilities`: 提供通用的工具方法，如 `warning` 用于记录警告日志，`getFullExceptionStack` 用于获取完整的异常堆栈信息。
    *   `cmn.util.TraceUtil`, `cmn.util.Tracer`: 用于性能跟踪和日志记录，帮助调试和分析方法执行时间。

*   **标准Java库:**
    *   `java.awt.Color`: Java AWT库中的颜色类，被转换为 `CColor` 使用。

**交互方式:**
`LoadingMask` 类主要通过构造各种 **DTOs (Data Transfer Objects)** 来定义遮罩的视觉和行为，然后将这些DTOs传递给 `PopMaskOfWidget` 来执行实际的UI操作。对于异步任务，它将 `PopMaskOfWidget` 封装成回调添加到 `FeCallbackPool` 中。同时，它利用 `Tracer` 进行方法级别的性能监控，并根据 `FeDebugUtil` 的设置进行异常处理和日志记录。

