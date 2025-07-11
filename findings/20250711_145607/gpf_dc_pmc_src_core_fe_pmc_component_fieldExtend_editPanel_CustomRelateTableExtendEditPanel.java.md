# Analysis for: gpf_dc_pmc\src\core\fe\pmc\component\fieldExtend\editPanel\CustomRelateTableExtendEditPanel.java

## Original File Path
`D:\TMP\CLASS_BUS_PROJECT\gpf_dc_pmc\src\core\fe\pmc\component\fieldExtend\editPanel\CustomRelateTableExtendEditPanel.java`

## Extracted Snippets & Analysis
好的，我将作为资深软件架构师，严格按照您的[核心规则]来分析提供的Java代码，并提取出符合条件、可用于AI编程助手训练的高质量API调用样例。

---

### 提取的代码样例

以下是从您提供的代码中提炼出的、符合所有规则的核心API使用模式：

---

#### 样例 1: 构建 `FormModelSelectorParam` 实例

**描述**: 展示如何实例化 `FormModelSelectorParam` 并设置其基本属性，包括选中ID、标签显示、父模型ID列表和控件ID。

**代码**:
```java
import fe.dc.fe.component.param.FormModelSelectorParam;
import java.util.ArrayList;
import java.util.List;

// 构建FormModelSelectorParam实例
FormModelSelectorParam param = new FormModelSelectorParam();
param.setSelectedModelId("your_model_id_variable"); // 设置选中的模型ID
param.setShowModelIdAtLabel(true); // 设置是否在标签上显示模型ID

List<String> parentModelIds = new ArrayList<>();
parentModelIds.add("your_root_model_id_constant"); // 例如: GpfDCBasicConst.ViewActionModelRootId
param.setParentModelIds(parentModelIds); // 设置父模型ID列表

param.setWidgetId("your_widget_id_constant"); // 设置控件ID，例如: "ViewModelId"
```

---

#### 样例 2: 通过 `FormModelSelector` 静态方法创建选择器实例

**描述**: 展示如何使用 `FormModelSelector` 的静态工厂方法 `newSelector` 来创建一个 `EditorDto` 类型的模型选择器控件。

**代码**:
```java
import fe.cmn.editor.EditorDto;
import fe.cmn.panel.PanelContext;
import fe.dc.fe.component.editor.FormModelSelector;
import fe.dc.fe.component.param.FormModelSelectorParam;

// 假设 yourPanelContextInstance 和 yourFormModelSelectorParamInstance 已准备好
// PanelContext yourPanelContextInstance = ...; // 这是一个框架上下文实例
// FormModelSelectorParam yourFormModelSelectorParamInstance = ...; // 参考样例1的构建方式

// 通过FormModelSelector的静态方法创建FormModelSelector实例
EditorDto selector = FormModelSelector.newSelector(yourPanelContextInstance, yourFormModelSelectorParamInstance);
```

---

#### 样例 3: 构建 `FormCodeSelectorParam` 实例

**描述**: 展示如何实例化 `FormCodeSelectorParam` 并设置其模型ID、选中的代码列表和控件ID。

**代码**:
```java
import fe.dc.fe.component.param.FormCodeSelectorParam;
import java.util.Arrays;

// 构建FormCodeSelectorParam实例
FormCodeSelectorParam param = new FormCodeSelectorParam();
param.setModelId("your_model_id_variable"); // 设置模型ID
param.setSelectedCodes(Arrays.asList("your_code_variable")); // 设置选中的代码列表

param.setWidgetId("your_widget_id_constant"); // 设置控件ID，例如: "ViewCode"
```

---

#### 样例 4: 使用 `SqlExpressionGroup` 构建查询条件

**描述**: 展示如何使用 `SqlExpressionGroup` 来构建一个简单的SQL查询表达式，例如用于设置过滤条件。

**代码**:
```java
import org.nutz.dao.util.cri.SqlExpressionGroup;

// 构建SQL表达式组（例如，用于设置过滤器）
String fieldCodeToMatch = "your_field_code_variable"; // 例如: GpfDCBasicConst.FieldCode_ModelId
String valueToMatch = "your_value_variable"; // 例如: "field.getTableFormModel()" 的值

SqlExpressionGroup filterGroup = new SqlExpressionGroup().andEquals(fieldCodeToMatch, valueToMatch);
// 此 filterGroup 对象可以进一步用于 FormCodeSelectorParam.setDefaultFilter() 等场景
```

---

#### 5: 通过 `FormCodeSelector` 静态方法创建选择器实例

**描述**: 展示如何使用 `FormCodeSelector` 的静态工厂方法 `newSelector` 来创建一个 `WidgetDto` 类型的代码选择器控件。

**代码**:
```java
import fe.cmn.panel.PanelContext;
import fe.cmn.widget.WidgetDto;
import fe.dc.fe.component.editor.FormCodeSelector;
import fe.dc.fe.component.param.FormCodeSelectorParam;

// 假设 yourPanelContextInstance 和 yourFormCodeSelectorParamInstance 已准备好
// PanelContext yourPanelContextInstance = ...; // 这是一个框架上下文实例
// FormCodeSelectorParam yourFormCodeSelectorParamInstance = ...; // 参考样例3的构建方式，可能包含样例4的过滤设置

// 通过FormCodeSelector的静态方法创建FormCodeSelector实例
WidgetDto selector = FormCodeSelector.newSelector(yourPanelContextInstance, yourFormCodeSelectorParamInstance);
```

---

#### 6a: 创建基本 `EditorFieldDefine` 实例

**描述**: 展示如何使用 `EditorFieldDefine` 的构造函数来创建一个基本的字段定义实例。

**代码**:
```java
import fe.util.editor.valuehanlder.EditorFieldDefine;

// 创建一个基本EditorFieldDefine实例
EditorFieldDefine fieldDefine = new EditorFieldDefine("your_field_code", "your_field_label", false);
```

---

#### 6b: 创建带处理器类型的 `EditorFieldDefine` 实例

**描述**: 展示如何使用 `EditorFieldDefine` 的构造函数，并指定一个处理器类（例如 `SelectEditorHandler.class`）来创建一个字段定义实例。

**代码**:
```java
import fe.util.editor.valuehanlder.EditorFieldDefine;
import fe.util.editor.valuehanlder.SelectEditorHandler; // 假设此Handler是公开可用的

// 创建一个带有特定处理器的EditorFieldDefine实例
EditorFieldDefine selectFieldDefine = new EditorFieldDefine(
    "your_select_field_code",
    "your_select_field_label",
    false, // 例如，是否是必填字段
    SelectEditorHandler.class // 指定字段的编辑器处理器类
);
```

---

#### 7: 使用 `CmnUtil.isStringEqual` 比较字符串

**描述**: 展示如何使用 `CmnUtil` 静态工具类中的 `isStringEqual` 方法安全地比较两个字符串是否相等。

**代码**:
```java
import com.leavay.ms.tool.CmnUtil;

// 比较两个字符串是否相等
String string1 = "your_first_string_variable";
String string2 = "your_second_string_variable"; // 例如: "ViewModelId"

boolean areEqual = CmnUtil.isStringEqual(string1, string2);
// areEqual 将为 true 或 false，即使字符串为 null 也不会抛出 NullPointerException
```

---

#### 8: 使用 `NullPojo.isNull` 检查对象是否为“空”实例

**描述**: 展示如何使用 `NullPojo` 静态工具类中的 `isNull` 方法来检查一个对象是否被视为框架层面的“空”实例。

**代码**:
```java
import fe.cmn.data.NullPojo;

// 检查一个对象是否为 NullPojo 的“空”实例
Object objectToCheck = null; // 可以是任意对象，包括 NullPojo.NULL
// Object objectToCheck = NullPojo.NULL; // 示例：一个真正的NullPojo空实例

boolean isNull = NullPojo.isNull(objectToCheck);
// isNull 将为 true 或 false
```