# Analysis for: gpf_rapidView_pmc\src\core\fe\rapidView\view\builder\SimpleHtmlViewBuilder.java

## Original File Path
`D:\TMP\CLASS_BUS_PROJECT\gpf_rapidView_pmc\src\core\fe\rapidView\view\builder\SimpleHtmlViewBuilder.java`

## Extracted Snippets & Analysis
好的，我将作为资深软件架构师，从您提供的代码中提取符合所有核心规则的高价值代码样例。

---

经过对 `SimpleHtmlViewBuilder` 类的分析，我识别出以下两个符合要求的、可供AI学习的API调用模式：

### 样例1: 通过HTML内容构建嵌入式页面DTO

**描述**: 这个样例展示了如何使用 `SimpleHtmlViewBuilder.newViewByHtmlContent` 静态方法，将一段HTML内容和可选的JSON数据转换为一个 `EmbedPageDto` 对象。这是构建可嵌入HTML视图的核心API模式。

**代码**:

```java
import fe.cmn.widget.EmbedPageDto; // 引入框架私有DTO类

// 1. 定义HTML内容字符串。可以是完整的HTML文档，也可以是HTML片段。
String htmlContent = "<html><head><title>此处填写您的页面标题</title></head><body><h1>欢迎！</h1><p>这是您的自定义内容。</p></body></html>";

// 2. 定义用于页面渲染的JSON数据字符串。如果不需要，可以传入null或一个空JSON字符串。
String jsonData = "{\"key1\": \"value1\", \"key2\": \"value2\"}";

// 3. 调用静态方法构建EmbedPageDto实例。
EmbedPageDto embedPage = SimpleHtmlViewBuilder.newViewByHtmlContent(htmlContent, jsonData);

// 现在，'embedPage' 对象已成功创建，您可以将其用于后续的框架操作，例如展示或渲染。
// 示例：someFrameworkService.display(embedPage);
```

### 样例2: 通过HTML文件路径构建嵌入式页面DTO

**描述**: 这个样例展示了如何使用 `SimpleHtmlViewBuilder.newViewByHtmlFilePath` 静态方法，通过指定服务器上的HTML文件路径和可选的JSON数据来构建一个 `EmbedPageDto` 对象。这适用于从预定义模板文件加载视图的场景。

**代码**:

```java
import fe.cmn.widget.EmbedPageDto; // 引入框架私有DTO类

// 1. 定义HTML文件在服务器上的绝对或相对路径。
String filePath = "path/to/your/template/file.html"; // 例如: "/WEB-INF/views/myPageTemplate.html"

// 2. 定义用于页面渲染的JSON数据字符串。如果不需要，可以传入null或一个空JSON字符串。
String jsonData = "{\"productId\": \"your_product_id\", \"locale\": \"en_US\"}";

// 3. 调用静态方法构建EmbedPageDto实例。
EmbedPageDto embedPage = SimpleHtmlViewBuilder.newViewByHtmlFilePath(filePath, jsonData);

// 现在，'embedPage' 对象已成功创建，您可以将其用于后续的框架操作。
// 示例：anotherFrameworkComponent.render(embedPage);
```