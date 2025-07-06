### 1. 文件核心功能
`OnKeyboard.java` 文件定义了一个特定于键盘事件的监听器配置类。它的主要职责是：
*   **封装键盘事件监听器的配置信息**：包括后端需要监听的特定键盘组合（`monitoredKeyCombinations`），以及前端回传的实际触发的键盘事件信息（`keyboardDto`）。
*   **作为数据传输对象（DTO）**：在系统内部，尤其是在前端与后端之间，或不同模块之间传递键盘事件监听器相关的配置和状态信息。
*   **扩展通用监听器能力**：它继承自 `ListenerDto`，复用了通用监听器的基础功能（如指定服务、命令、同步方式、数据等），并在此基础上增加了键盘事件特有的属性。
*   **支持链式配置**：通过在setter方法中返回自身实例，实现了流畅的API（Fluent API），方便进行链式配置。

它在整个项目中扮演的角色是：提供一个标准化的、可配置的方式来定义和管理对键盘事件的响应逻辑，是系统事件处理机制中与键盘输入相关的一个关键配置组件。

### 2. 主要组件/类定义

| 类/组件名 | 继承自/实现 | 主要职责 |
| :--- | :--- | :--- |
| `public class OnKeyboard` | `ListenerDto` | 定义键盘事件监听器的配置和数据结构。它包含需要监听的键盘组合，以及实际触发的键盘事件数据，并支持链式配置其执行行为。 |

#### 方法与属性详情
**类: `OnKeyboard`**

| 方法/属性 | 类型 | 描述 |
| :--- | :--- | :--- |
| `serialVersionUID` | `private static final long` | 用于序列化的版本UID，确保类在不同版本间的兼容性。 |
| `keyboardDto` | `KeyboardDto` | **属性**：前端回填的当前触发的键盘事件组合。此属性为只读，用于表示实际发生的键盘事件数据。 |
| `monitoredKeyCombinations` | `List<KeyboardDto>` | **属性**：后端指定的需要监听的键盘事件组合列表。如果此列表为空或未指定，则默认监听所有键盘事件。 |
| `OnKeyboard()` | 构造函数 | 默认构造方法。 |
| `OnKeyboard(Class service, String command, boolean synchronize)` | 构造函数 | 构造方法，初始化监听器的后端服务类、命令和同步标志，委托给父类构造器。 |
| `OnKeyboard(Class service, String command, boolean synchronize, Object data)` | 构造函数 | 构造方法，在上述基础上增加一个数据参数，委托给父类构造器。 |
| `getKeyboardDto()` | `KeyboardDto` | 获取前端回填的当前触发的键盘事件组合。 |
| `getMonitoredKeyCombinations()` | `List<KeyboardDto>` | 获取后端指定的需要监听的键盘事件组合列表。 |
| `setMonitoredKeyCombinations(List<KeyboardDto> monitoredKeyCombinations)` | `OnKeyboard` | 设置需要监听的键盘事件组合列表。返回当前 `OnKeyboard` 实例，支持链式调用。 |
| `setMonitoredKeyCombinations(KeyboardDto ...monitoredKeyCombinations)` | `OnKeyboard` | 使用可变参数设置需要监听的键盘事件组合，内部将其转换为 `List`。返回当前 `OnKeyboard` 实例，支持链式调用。 |
| `setExecutor(ListenerExecutorDto executor)` | `OnKeyboard` | 重写父类方法，设置监听器的执行器。返回当前 `OnKeyboard` 实例，支持链式调用。 |
| `setServerExecutor(Class service, String command)` | `OnKeyboard` | 重写父类方法，设置服务器端执行的服务类和命令。返回当前 `OnKeyboard` 实例，支持链式调用。 |
| `setEventExecutor(EventDto event)` | `OnKeyboard` | 重写父类方法，设置事件执行器。返回当前 `OnKeyboard` 实例，支持链式调用。 |
| `setSynchronize(boolean synchronize)` | `OnKeyboard` | 重写父类方法，设置是否同步执行。返回当前 `OnKeyboard` 实例，支持链式调用。 |
| `setData(Object data)` | `OnKeyboard` | 重写父类方法，设置与监听器关联的额外数据。返回当前 `OnKeyboard` 实例，支持链式调用。 |
| `setSelfBinaryData()` | `OnKeyboard` | 重写父类方法，设置自身为二进制数据。返回当前 `OnKeyboard` 实例，支持链式调用。 |

### 3. 主要函数/方法 (如果适用)
本文件主要定义一个类及其成员方法，不包含独立的工具类函数。

### 4. 对外依赖与交互
`OnKeyboard.java` 文件依赖于以下重要的外部类和项目内其他类：

*   **`java.util.Arrays`**:
    *   **交互**: 在 `setMonitoredKeyCombinations(KeyboardDto ...monitoredKeyCombinations)` 方法中，用于将可变参数的 `KeyboardDto` 数组转换为流，进而收集为 `List`。
*   **`java.util.List`**:
    *   **交互**: 用于定义 `monitoredKeyCombinations` 属性，存储多个 `KeyboardDto` 实例。
*   **`java.util.stream.Collectors`**:
    *   **交互**: 在 `setMonitoredKeyCombinations(KeyboardDto ...monitoredKeyCombinations)` 方法中，与 `Stream` API 结合使用，将 `KeyboardDto` 流收集到 `List` 中。
*   **`fe.cmn.data.KeyboardDto`**:
    *   **交互**: `OnKeyboard` 类内部持有 `KeyboardDto` 类型的实例（`keyboardDto`）和列表（`monitoredKeyCombinations`）。`KeyboardDto` 被用作数据载体，封装具体的键盘组合信息（例如，按键码、修饰符等），在监听器配置和事件触发时传递键盘相关的数据。
*   **`fe.cmn.event.EventDto`**:
    *   **交互**: `OnKeyboard` 类重写了 `setEventExecutor(EventDto event)` 方法。这表明 `OnKeyboard` 可以被配置为在特定 `EventDto` 事件发生时执行，或者其执行逻辑与某个 `EventDto` 相关联。
*   **`fe.cmn.widget.ListenerDto`**:
    *   **交互**: `OnKeyboard` 是 `ListenerDto` 的子类。这意味着它继承了 `ListenerDto` 定义的通用监听器结构和行为，包括设置服务类、命令、同步标志和数据等通用属性。`OnKeyboard` 通过重写这些 `set*` 方法并返回自身实例，实现了方法链式调用，使得监听器的配置更为便捷和流畅。
*   **`fe.cmn.widget.ListenerExecutorDto`**:
    *   **交互**: `OnKeyboard` 类重写了 `setExecutor(ListenerExecutorDto executor)` 方法。这表示 `OnKeyboard` 可以配置一个 `ListenerExecutorDto` 实例，用于定义监听器实际执行的逻辑或方式，从而将监听器的配置与执行解耦。

综上，`OnKeyboard` 深度整合在 `fe.cmn` 包定义的通用监听器和数据传输框架中，是该框架内处理键盘事件的特定实现。

