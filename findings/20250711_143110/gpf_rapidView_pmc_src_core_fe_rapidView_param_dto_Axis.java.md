# Analysis for: gpf_rapidView_pmc\src\core\fe\rapidView\param\dto\Axis.java

## Original File Path
`D:\TMP\CLASS_BUS_PROJECT\gpf_rapidView_pmc\src\core\fe\rapidView\param\dto\Axis.java`

## Extracted Snippets & Analysis
好的，我将严格遵守您提供的核心规则，从 `Axis` 类的代码中提炼出高质量的、可用于AI训练的代码样例。

---

### 提取出的高质量代码样例

**1. 样例名称：创建 `Axis` 对象**
*   **任务描述**：演示如何使用构造函数创建一个新的 `Axis` 实例。
*   **代码**：
    ```java
    // 使用构造函数创建 Axis 实例
    // 参数依次为：标签（String）、步长（int）、间隔号（int）
    fe.rapidView.param.dto.Axis axisObject = new fe.rapidView.param.dto.Axis("your_axis_label", your_step_value_int, your_interval_no_int);
    ```

**2. 样例名称：通过链式调用设置 `Axis` 属性**
*   **任务描述**：演示 `Axis` 类中 Setter 方法的链式调用（Fluent API）模式，用于一次性配置多个属性。
*   **代码**：
    ```java
    // 创建 Axis 实例并链式设置属性
    fe.rapidView.param.dto.Axis axisConfigured = new fe.rapidView.param.dto.Axis("initial_label_string", initial_step_int, initial_interval_no_int)
        .setLabel("new_label_string") // 设置标签
        .setStep(new_step_int)       // 设置步长
        .setIntervalNo(new_interval_no_int); // 设置间隔号
    ```

**3. 样例名称：设置 `Axis` 对象的单个属性**
*   **任务描述**：演示如何修改 `Axis` 实例的单个属性值。
*   **代码**：
    ```java
    // 假设 axisInstance 已经被初始化
    fe.rapidView.param.dto.Axis axisInstance = new fe.rapidView.param.dto.Axis("existing_label", existing_step_int, existing_interval_no_int);

    // 设置 Axis 对象的标签
    axisInstance.setLabel("your_new_label_string");

    // 设置 Axis 对象的步长
    axisInstance.setStep(your_new_step_int);

    // 设置 Axis 对象的间隔号
    axisInstance.setIntervalNo(your_new_interval_no_int);
    ```

**4. 样例名称：获取 `Axis` 对象的属性值**
*   **任务描述**：演示如何从 `Axis` 实例中获取各个属性的值。
*   **代码**：
    ```java
    // 假设 axisInstance 已经被初始化并配置
    fe.rapidView.param.dto.Axis axisInstance = new fe.rapidView.param.dto.Axis("example_label", 100, 5);

    // 获取 Axis 对象的标签
    String label = axisInstance.getLabel();

    // 获取 Axis 对象的步长
    double step = axisInstance.getStep();

    // 获取 Axis 对象的间隔号
    int intervalNo = axisInstance.getIntervalNo();
    ```