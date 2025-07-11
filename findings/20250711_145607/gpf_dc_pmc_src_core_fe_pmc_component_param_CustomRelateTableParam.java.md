# Analysis for: gpf_dc_pmc\src\core\fe\pmc\component\param\CustomRelateTableParam.java

## Original File Path
`D:\TMP\CLASS_BUS_PROJECT\gpf_dc_pmc\src\core\fe\pmc\component\param\CustomRelateTableParam.java`

## Extracted Snippets & Analysis
作为一名资深的软件架构师，我已仔细审阅您提供的代码，并严格遵循您的核心规则，从中提炼出以下高质量、高教学价值的代码样例。这些样例旨在展示如何正确、可靠地使用 `CustomRelateTableParam` 类的API，即使AI无法访问其私有父类的源码。

---

### 提取的代码样例

---

#### 1. 模式：实例化 CustomRelateTableParam

*   **说明**：展示如何创建一个 `CustomRelateTableParam` 的新实例。由于 `CustomRelateTableParam` 是一个泛型类，这里使用 `Object` 作为泛型参数以确保最广泛的兼容性和通用性，AI可以根据具体场景替换为其他类型。
*   **符合规则**：执行“动作”（对象创建）、绝对可靠（使用通用 `Object` 泛型，依赖默认构造函数）、去业务化、原子性。
*   **代码样例**：

    ```java
    import fe.pmc.component.param.CustomRelateTableParam;

    // 创建一个 CustomRelateTableParam 的新实例
    CustomRelateTableParam<Object> your_param_instance = new CustomRelateTableParam<>();
    ```

---

#### 2. 模式：设置 CustomRelateTableParam 的 headers 属性

*   **说明**：演示如何为 `CustomRelateTableParam` 实例设置表头列表。`List<String>` 是一个标准的Java类型，可以灵活填充内容。
*   **符合规则**：执行“动作”（方法调用）、绝对可靠（参数为通用 `List<String>`）、去业务化（使用占位符内容）、原子性。
*   **代码样例**：

    ```java
    import fe.pmc.component.param.CustomRelateTableParam;
    import java.util.List;
    import java.util.ArrayList; // 也可以使用 java.util.Arrays.asList()

    // 假设 your_param_instance 已经存在（如通过上面的实例化模式创建）
    CustomRelateTableParam<Object> your_param_instance = new CustomRelateTableParam<>();

    // 准备要设置的表头列表
    List<String> your_headers_list = new ArrayList<>();
    your_headers_list.add("此处填写您的第一个表头");
    your_headers_list.add("此处填写您的第二个表头");
    // ... 可以添加更多表头

    // 设置 CustomRelateTableParam 的 headers 属性
    your_param_instance.setHeaders(your_headers_list);
    ```

---

#### 3. 模式：设置 CustomRelateTableParam 的 keepOneRowSelectedOnSingleClick 属性

*   **说明**：演示如何设置 `keepOneRowSelectedOnSingleClick` 属性，该属性控制单击选择行时是否保持只有一行可以被选中。
*   **符合规则**：执行“动作”（方法调用）、绝对可靠（参数为通用 `Boolean`）、去业务化（使用占位符布尔值）、原子性。
*   **代码样例**：

    ```java
    import fe.pmc.component.param.CustomRelateTableParam;

    // 假设 your_param_instance 已经存在
    CustomRelateTableParam<Object> your_param_instance = new CustomRelateTableParam<>();

    // 准备要设置的布尔值，可以为 true 或 false
    Boolean your_boolean_value = true; // 或 false

    // 设置 CustomRelateTableParam 的 keepOneRowSelectedOnSingleClick 属性
    your_param_instance.setKeepOneRowSelectedOnSingleClick(your_boolean_value);
    ```

---

#### 4. 模式：设置 CustomRelateTableParam 的 selectFrist 属性

*   **说明**：演示如何设置 `selectFrist` 属性，该属性控制是否默认选中第一行。
*   **符合规则**：执行“动作”（方法调用）、绝对可靠（参数为通用 `Boolean`）、去业务化（使用占位符布尔值）、原子性。
*   **代码样例**：

    ```java
    import fe.pmc.component.param.CustomRelateTableParam;

    // 假设 your_param_instance 已经存在
    CustomRelateTableParam<Object> your_param_instance = new CustomRelateTableParam<>();

    // 准备要设置的布尔值，可以为 true 或 false
    Boolean your_boolean_value = false; // 或 true

    // 设置 CustomRelateTableParam 的 selectFrist 属性
    your_param_instance.setSelectFrist(your_boolean_value);
    ```

---

#### 5. 模式：链式设置 CustomRelateTableParam 的多个属性

*   **说明**：演示如何利用 `CustomRelateTableParam` 的setter方法返回 `this` 的特性，进行链式调用（Fluent API），在一个语句中设置多个属性，提高代码的简洁性和可读性。
*   **符合规则**：执行“动作”（链式方法调用）、绝对可靠（参数为通用类型）、去业务化（使用占位符）、原子性（作为一个整体的配置模式）。
*   **代码样例**：

    ```java
    import fe.pmc.component.param.CustomRelateTableParam;
    import java.util.List;
    import java.util.Arrays; // 通常用于快速创建 List

    // 实例化 CustomRelateTableParam 并进行链式属性设置
    CustomRelateTableParam<Object> your_param_instance = new CustomRelateTableParam<>();

    // 准备用于链式调用的参数值
    List<String> your_headers_list = Arrays.asList("此处填写表头1", "此处填写表头2", "此处填写表头3");
    Boolean keep_one_row_selected = true; // 例如，保持单选
    Boolean select_first_row = false;     // 例如，不默认选中第一行

    // 链式调用设置多个属性
    your_param_instance.setHeaders(your_headers_list)
                       .setKeepOneRowSelectedOnSingleClick(keep_one_row_selected)
                       .setSelectFrist(select_first_row);
    ```