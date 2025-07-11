# Analysis for: gpf_rapidView_pmc\src\core\fe\rapidView\param\CardWithProgressBarViewParam.java

## Original File Path
`D:\TMP\CLASS_BUS_PROJECT\gpf_rapidView_pmc\src\core\fe\rapidView\param\CardWithProgressBarViewParam.java`

## Extracted Snippets & Analysis
根据您提供的代码和严格的提取规则，我分析并识别出以下符合条件的、有价值的代码样例：

---

### 提取的API使用样例

**1. 创建 `CardWithProgressBarViewParam` 的无参实例**

*   **目的**: 展示如何使用无参构造函数创建一个 `CardWithProgressBarViewParam` 的基本实例。
*   **可靠性说明**: 这是一个独立的构造函数调用，不依赖任何外部上下文，参数为零，符合可靠性要求。

```java
new fe.rapidView.param.CardWithProgressBarViewParam();
```

---

**2. 创建 `CardWithProgressBarViewParam` 并初始化所有属性的实例**

*   **目的**: 展示如何使用全参构造函数一次性构建并初始化 `CardWithProgressBarViewParam` 的所有属性。
*   **可靠性说明**: 这是一个独立的构造函数调用，所有参数都是通用Java类型 (String, boolean)，不依赖任何未知上下文，符合可靠性要求。
*   **去业务化**: 代码中的具体字符串值和布尔值被替换为通用占位符，以展示API调用模式而非特定业务数据。

```java
new fe.rapidView.param.CardWithProgressBarViewParam(
    "your_title_string",            // title
    "your_progress_before_string",  // progBefore
    "your_progress_after_string",   // progAfter
    "your_progress_value_string",   // progValue
    "your_color_string",            // color
    "your_progress_color_string",   // progressColor
    true                            // showProgress (或者 false)
);
```

---