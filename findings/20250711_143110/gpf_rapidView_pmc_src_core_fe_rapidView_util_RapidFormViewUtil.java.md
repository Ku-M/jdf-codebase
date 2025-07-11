# Analysis for: gpf_rapidView_pmc\src\core\fe\rapidView\util\RapidFormViewUtil.java

## Original File Path
`D:\TMP\CLASS_BUS_PROJECT\gpf_rapidView_pmc\src\core\fe\rapidView\util\RapidFormViewUtil.java`

## Extracted Snippets & Analysis
好的，作为一名资深软件架构师，我已仔细分析了您提供的代码，并严格遵循了您提出的核心规则。以下是识别并提取出的、符合高标准的代码样例，旨在帮助AI编程助手学习如何正确使用您的框架API：

---

### 提取的代码样例

#### 1. 创建一个垂直布局的BoxDto

**描述:** 展示如何使用静态工厂方法 `BoxDto.vbar()` 创建一个垂直排列的UI容器。

```java
// 示例：创建一个垂直布局的BoxDto
BoxDto verticalBox = BoxDto.vbar();
```

#### 2. 将BoxDto包装成SinglePanelDto

**描述:** 展示如何使用静态工厂方法 `SinglePanelDto.wrap()` 将一个 `BoxDto` （UI容器）包装成一个 `SinglePanelDto`，这是显示UI内容的基础。

```java
// 示例：将BoxDto包装成SinglePanelDto
// BoxDto contentBox 可以是任何通过 BoxDto 静态方法（如 vbar(), hbar()）创建的实例
BoxDto contentBox = BoxDto.vbar(); 
SinglePanelDto panelDto = SinglePanelDto.wrap(contentBox);
```

#### 3. 设置SinglePanelDto的宽度为窗口宽度的百分比

**描述:** 展示如何为 `SinglePanelDto` 设置相对于当前窗口宽度的百分比尺寸。

```java
// 示例：设置SinglePanelDto的宽度为窗口宽度的百分比
// 前提：panelDto 需为一个已存在的 SinglePanelDto 实例
SinglePanelDto panelDto = SinglePanelDto.wrap(BoxDto.vbar()); // 假设panelDto已存在或已创建

panelDto.setPreferWidthByWindowSize(your_relative_width_double_value); // 例如 0.8 表示窗口宽度的80%
```

#### 4. 设置SinglePanelDto的高度为窗口高度的百分比

**描述:** 展示如何为 `SinglePanelDto` 设置相对于当前窗口高度的百分比尺寸。

```java
// 示例：设置SinglePanelDto的高度为窗口高度的百分比
// 前提：panelDto 需为一个已存在的 SinglePanelDto 实例
SinglePanelDto panelDto = SinglePanelDto.wrap(BoxDto.vbar()); // 假设panelDto已存在或已创建

panelDto.setPreferHeightByWindowSize(your_relative_height_double_value); // 例如 0.5 表示窗口高度的50%
```

#### 5. 设置SinglePanelDto的绝对首选尺寸

**描述:** 展示如何为 `SinglePanelDto` 设置具体的像素尺寸（绝对尺寸）。

```java
// 示例：设置SinglePanelDto的绝对首选尺寸
// 前提：panelDto 需为一个已存在的 SinglePanelDto 实例
SinglePanelDto panelDto = SinglePanelDto.wrap(BoxDto.vbar()); // 假设panelDto已存在或已创建

panelDto.setPreferSize(SizeDto.all(your_width_double_value, your_height_double_value)); // 例如 SizeDto.all(800.0, 600.0)
```

#### 6. 构建一个整数输入框

**描述:** 展示如何使用 `ETSStyleUtil` 静态方法构建一个用于整数输入的UI组件 `BoxDto`。

```java
// 示例：构建一个整数输入框
// 前提：需要一个 PanelContext 实例。如果框架有获取默认 PanelContext 的方法，请替换或注释此行。
PanelContext yourPanelContext = new PanelContext(); // 或者 PanelContext.getDefaultInstance();

BoxDto integerInputBox = ETSStyleUtil.buildIntegerInput(
    yourPanelContext,
    "your_field_name_string",         // 字段名称，例如 "年龄"
    "your_initial_value_string",      // 初始值，例如 "123"
    your_boolean_value_for_is_editable, // 是否可编辑，例如 true
    your_boolean_value_for_is_required  // 是否必填，例如 false
);
```

#### 7. 构建一个小数输入框

**描述:** 展示如何使用 `ETSStyleUtil` 静态方法构建一个用于小数输入的UI组件 `BoxDto`。

```java
// 示例：构建一个小数输入框
// 前提：需要一个 PanelContext 实例。
PanelContext yourPanelContext = new PanelContext();

BoxDto decimalInputBox = ETSStyleUtil.buildDecimalInput(
    yourPanelContext,
    "your_field_name_string",         // 字段名称，例如 "金额"
    "your_initial_value_string",      // 初始值，例如 "123.45"
    your_boolean_value_for_is_editable, // 是否可编辑
    your_boolean_value_for_is_required  // 是否必填
);
```

#### 8. 构建一个单行文本输入框

**描述:** 展示如何使用 `ETSStyleUtil` 静态方法构建一个用于单行文本输入的UI组件 `BoxDto`。

```java
// 示例：构建一个单行文本输入框
// 前提：需要一个 PanelContext 实例。
PanelContext yourPanelContext = new PanelContext();

BoxDto singleLineTextInputBox = ETSStyleUtil.buildTextInput(
    yourPanelContext,
    "your_field_name_string",         // 字段名称，例如 "姓名"
    "your_initial_value_string",      // 初始值
    your_boolean_value_for_is_editable, // 是否可编辑
    your_boolean_value_for_is_required  // 是否必填
);
```

#### 9. 构建一个多行文本输入框

**描述:** 展示如何使用 `ETSStyleUtil` 静态方法构建一个用于多行文本输入的UI组件 `BoxDto`，并指定最小/最大行数。

```java
// 示例：构建一个多行文本输入框
// 前提：需要一个 PanelContext 实例。
PanelContext yourPanelContext = new PanelContext();

BoxDto multiLineTextInputBox = ETSStyleUtil.buildTextInput(
    yourPanelContext,
    "your_field_name_string",         // 字段名称，例如 "备注"
    "your_initial_value_string",      // 初始值
    your_boolean_value_for_is_editable, // 是否可编辑
    your_boolean_value_for_is_required,  // 是否必填
    your_min_line_number_int,         // 最小行数，例如 1
    your_max_line_number_int          // 最大行数，例如 5
);
```

#### 10. 通过RapidViewBasicFunc记录异常

**描述:** 展示如何通过 `RapidViewBasicFunc` 的单例实例来记录运行时捕获的异常。

```java
// 示例：通过RapidViewBasicFunc记录异常
try {
    // 此处放置可能抛出异常的代码
    throw new RuntimeException("此处填写您的业务错误信息");
} catch (Exception e) {
    RapidViewBasicFunc.get().logException(e);
}
```

#### 11. 使用链式调用配置FormFieldDto

**描述:** 展示如何创建 `FormFieldDto` 实例并使用其流畅的链式方法来设置字段的名称、类型、值和必填状态。

```java
// 示例：使用链式调用配置FormFieldDto
FormFieldDto fieldDto = new FormFieldDto();
fieldDto.setFieldName("your_field_name_string")
        .setFieldType(DataTypeEnum.findByCode("your_data_type_code_string")) // 例如 "INT", "DECIMAL", "TEXT"
        .setFieldValue("your_initial_value_string")
        .setRequire(your_boolean_value_for_is_required); // 例如 true 或 false
```

#### 12. 将TableData转换为FormFieldDto列表

**描述:** 展示如何使用 `RapidFormViewUtil` 的静态方法，将包含表单参数定义的 `TableData` 转换为 `FormFieldDto` 对象的列表。

```java
// 示例：将TableData转换为FormFieldDto列表
// 前提：假设 paramDefine 是一个包含表单参数定义的 TableData 实例。
// 您需要根据实际业务逻辑填充 paramDefine。
TableData paramDefine = new TableData(); // 模拟或获取实际的TableData实例

// 模拟填充 TableData 示例 (实际场景中可能从其他来源获取)
// Form field1 = new Form();
// field1.set("参数名", "用户姓名");
// field1.set("参数类型", new AssociationData("TEXT", "文本")); // DataTypeEnum.Text
// field1.set("参数值", "张三");
// field1.set("不可为空", true);
// paramDefine.addRow(field1);

// Form field2 = new Form();
// field2.set("参数名", "用户年龄");
// field2.set("参数类型", new AssociationData("INT", "整数")); // DataTypeEnum.Int
// field2.set("参数值", "30");
// field2.set("不可为空", false);
// paramDefine.addRow(field2);

List<FormFieldDto> formFields = RapidFormViewUtil.convertStandardParamDefineToFormField(paramDefine);

// formFields 现在包含了从 TableData 转换而来的 FormFieldDto 对象
```

#### 13. 快速构建一个表单视图（不显示）

**描述:** 展示如何使用 `RapidFormViewUtil` 静态方法，根据提供的 `FormFieldDto` 列表和尺寸参数，快速构建一个 `SinglePanelDto` 表示的表单视图，但不立即显示。

```java
// 示例：快速构建一个表单视图（不显示）
// 前提：
// yourPanelContext: PanelContext 实例
// yourFieldDtos: List<FormFieldDto>，包含表单字段定义
PanelContext yourPanelContext = new PanelContext(); // 或者 PanelContext.getDefaultInstance();
List<FormFieldDto> yourFieldDtos = new ArrayList<>();

// 示例：填充 yourFieldDtos （实际应用中可能通过 convertStandardParamDefineToFormField 或其他方式获取）
// yourFieldDtos.add(new FormFieldDto().setFieldName("姓名").setFieldType(DataTypeEnum.Text).setFieldValue("").setRequire(true));
// yourFieldDtos.add(new FormFieldDto().setFieldName("年龄").setFieldType(DataTypeEnum.Int).setFieldValue("0").setRequire(false));

SinglePanelDto formPanelDto = RapidFormViewUtil.buildFormView(
    yourPanelContext,
    yourFieldDtos,
    your_width_double_value,  // 例如 0.8 (窗口宽度的80%) 或 800.0 (像素宽度)
    your_height_double_value  // 例如 0.5 (窗口高度的50%) 或 600.0 (像素高度)
);

// formPanelDto 可用于后续的 PopDialog.showInput 等操作
```

#### 14. 快速构建并显示一个表单视图

**描述:** 这是高层次的封装，展示如何使用 `RapidFormViewUtil` 静态方法，一次性完成表单的构建、显示和获取用户输入。

```java
// 示例：快速构建并显示一个表单视图
// 前提：
// yourPanelContext: PanelContext 实例
// yourFieldDtos: List<FormFieldDto>，包含表单字段定义
PanelContext yourPanelContext = new PanelContext(); // 或者 PanelContext.getDefaultInstance();
List<FormFieldDto> yourFieldDtos = new ArrayList<>();

// 示例：填充 yourFieldDtos （实际应用中可能通过 convertStandardParamDefineToFormField 或其他方式获取）
// FormFieldDto nameField = new FormFieldDto().setFieldName("姓名").setFieldType(DataTypeEnum.Text).setFieldValue("").setRequire(true);
// yourFieldDtos.add(nameField);
// FormFieldDto ageField = new FormFieldDto().setFieldName("年龄").setFieldType(DataTypeEnum.Int).setFieldValue("0").setRequire(false);
// yourFieldDtos.add(ageField);

RapidFormViewRespondDto respondDto = RapidFormViewUtil.showFormView(
    yourPanelContext,
    yourFieldDtos,
    "此处填写您的表单对话框标题",
    your_width_double_value,  // 例如 0.8 (窗口宽度的80%) 或 800.0 (像素宽度)
    your_height_double_value // 例如 0.5 (窗口高度的50%) 或 600.0 (像素高度)
);

// 获取表单提交后的值
PanelValue submittedValue = respondDto.getPanelValue();
// 您可以进一步处理 submittedValue，例如将其解析并转换为业务对象
// if (submittedValue != null) {
//     String name = submittedValue.getString("姓名");
//     Integer age = submittedValue.getInteger("年龄");
//     // ...
// }
```