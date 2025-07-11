# Analysis for: gpf_dc_PanelCM\src\core\octo\cm\enums\panel\CommonEvent.java

## Original File Path
`D:\TMP\CLASS_BUS_PROJECT\gpf_dc_PanelCM\src\core\octo\cm\enums\panel\CommonEvent.java`

## Extracted Snippets & Analysis
作为一名资深的软件架构师，我已仔细审阅您提供的代码，并严格遵循核心规则，从中提炼出以下高质量、可供AI编程助手学习的核心代码样例。

这些样例旨在展示如何调用您的框架API中的`CommonEvent`和`ListenerType`枚举。

---

### 提取的代码样例

#### 1. 访问 `CommonEvent` 枚举常量

*   **描述**: 展示如何直接引用和使用 `CommonEvent` 枚举中定义的常量。这是与框架交互的基础步骤。
*   **核心规则匹配**:
    *   **动作**: 访问静态枚举成员是明确的“动作”。
    *   **可靠性**: 枚举常量是静态且公开的，无需特定上下文即可访问。
    *   **可复用/去业务化**: `your_event_constant_name` 占位符确保模式通用。
    *   **原子性**: 仅关注枚举常量的引用。
*   **代码模式**:
    ```java
    CommonEvent.your_event_constant_name
    ```
*   **示例**:
    ```java
    CommonEvent.点击
    CommonEvent.界面初始化
    ```

#### 2. 获取 `CommonEvent` 枚举常量的 `ListenerType`

*   **描述**: 展示如何从 `CommonEvent` 枚举常量中提取关联的 `ListenerType`。这对于理解事件类型至关重要。
*   **核心规则匹配**:
    *   **动作**: 调用 `getListenType()` 方法是明确的“动作”。
    *   **可靠性**: `getListenType()` 是公开方法，可在静态枚举常量上安全调用。
    *   **可复用/去业务化**: `your_event_constant_name` 占位符确保模式通用。
    *   **原子性**: 仅关注获取 `ListenerType`。
*   **代码模式**:
    ```java
    CommonEvent.your_event_constant_name.getListenType()
    ```
*   **示例**:
    ```java
    CommonEvent.失焦.getListenType()
    CommonEvent.滑动结束监听.getListenType()
    ```

#### 3. 获取 `CommonEvent` 枚举常量的 `command` 字符串

*   **描述**: 展示如何从 `CommonEvent` 枚举常量中提取关联的命令字符串。
*   **核心规则匹配**:
    *   **动作**: 调用 `getCommand()` 方法是明确的“动作”。
    *   **可靠性**: `getCommand()` 是公开方法，可在静态枚举常量上安全调用。
    *   **可复用/去业务化**: `your_event_constant_name` 占位符确保模式通用。
    *   **原子性**: 仅关注获取命令字符串。
*   **代码模式**:
    ```java
    CommonEvent.your_event_constant_name.getCommand()
    ```
*   **示例**:
    ```java
    CommonEvent.长按.getCommand()
    CommonEvent.全局事件.getCommand()
    ```

#### 4. 访问 `ListenerType` 枚举常量

*   **描述**: 展示如何引用和使用 `gpf.dc.basic.fe.enums.ListenerType` 枚举中定义的常量，以及其允许的 `null` 值。这是框架API中重要的枚举类型。
*   **核心规则匹配**:
    *   **动作**: 访问静态枚举成员是明确的“动作”。
    *   **可靠性**: 枚举常量是静态且公开的，无需特定上下文即可访问。 `null` 值的出现也表明其在某些API场景中是允许的有效值。
    *   **可复用/去业务化**: `your_listener_type_constant` 占位符确保模式通用。
    *   **原子性**: 仅关注 `ListenerType` 常量的引用。
*   **代码模式**:
    ```java
    ListenerType.your_listener_type_constant
    ```
*   **示例**:
    ```java
    ListenerType.OnBlur
    ListenerType.OnDoubleClick
    ListenerType.OnKeyboard
    null // 示例中 CommonEvent.全局事件 使用了 null 作为 ListenerType
    ```