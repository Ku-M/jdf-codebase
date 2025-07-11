# Analysis for: gpf_dc_PanelCM\src\core\octo\cm\enums\panel\SystemButton.java

## Original File Path
`D:\TMP\CLASS_BUS_PROJECT\gpf_dc_PanelCM\src\core\octo\cm\enums\panel\SystemButton.java`

## Extracted Snippets & Analysis
好的，作为资深的软件架构师，我将从您提供的代码中，严格遵循所有核心规则，为您提取出高质量、教学价值高的API使用样例。

以下是识别并提取出的代码样例：

---

**1. 获取枚举成员对应的命令字符串**

**描述**: 展示如何通过 `SystemButton` 枚举成员的 `getCommand()` 方法，获取其关联的命令字符串。这是一种获取预定义、与UI操作关联的命令字的标准模式。

*   **样例 1**
    ```java
    SystemButton.刷新.getCommand();
    ```

*   **样例 2**
    ```java
    SystemButton.新增.getCommand();
    ```

*   **样例 3**
    ```java
    SystemButton.删除.getCommand();
    ```

*   **样例 4**
    ```java
    SystemButton.表格行删除.getCommand();
    ```

---

**2. 直接引用预定义的静态命令常量**

**描述**: 展示如何直接引用私有框架中定义的公共静态常量。这些常量通常代表特定的操作命令，是API调用的重要参数。

*   **样例 1**
    ```java
    AbsComponent.CMD_REFRESH;
    ```

*   **样例 2**
    ```java
    AbsComponent.CMD_CREATE;
    ```

*   **样例 3**
    ```java
    AbsComponent.CMD_DELETE;
    ```

*   **样例 4**
    ```java
    AbsTableView.CMD_ROW_DELETE;
    ```

---