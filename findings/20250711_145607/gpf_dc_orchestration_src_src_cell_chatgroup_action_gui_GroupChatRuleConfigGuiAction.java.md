# Analysis for: gpf_dc_orchestration\src\src\cell\chatgroup\action\gui\GroupChatRuleConfigGuiAction.java

## Original File Path
`D:\TMP\CLASS_BUS_PROJECT\gpf_dc_orchestration\src\src\cell\chatgroup\action\gui\GroupChatRuleConfigGuiAction.java`

## Extracted Snippets & Analysis
好的，我理解您的任务和严格的筛选标准。作为资深架构师，我将从代码中精炼出符合“可执行、可靠、可复用、原子性、去业务化”的核心API使用模式。

以下是我从您提供的代码中识别并提取出的、符合所有核心规则的代码样例：

---

**提取的框架API使用样例：**

```java
// 样例 1: 获取IFormMgr实例 (静态方法调用)
// 描述: 展示如何通过静态工厂方法获取IFormMgr的单例或默认实例。
// 规则依据: 可执行的逻辑片段，静态方法调用是绝对可靠的，去业务化。
// 预期学习点: 学习如何获取框架中某种Manager类型的实例。
//-------------------------------------------------------------------------
import cell.gpf.adur.data.IFormMgr;

public class SampleFormMgrGet {
    public void demonstrate() {
        IFormMgr formMgr = IFormMgr.get();
        // 此处可以进一步使用formMgr实例，例如 formMgr.queryForm(...)
        // 但由于 queryForm 依赖 dao, masterClass, masterKey 这些非通用类型上下文，
        // 故不将其纳入此原子样例。
    }
}
```

```java
// 样例 2: 使用Lists工具类创建ArrayList (静态方法调用)
// 描述: 展示如何使用Google Guava的Lists工具类便捷地创建并初始化一个ArrayList。
// 规则依据: 可执行的逻辑片段，静态方法调用是绝对可靠的，去业务化。
// 预期学习点: 学习如何使用框架或常用库提供的集合创建工具。
//-------------------------------------------------------------------------
import com.google.common.collect.Lists;
import java.util.List;

public class SampleListsNewArrayList {
    public void demonstrate() {
        List<String> attributeList = Lists.newArrayList(
            "YOUR_RULE_ATTRIBUTE_1",
            "YOUR_RULE_ATTRIBUTE_2",
            "YOUR_RULE_ATTRIBUTE_3"
        );
        // 您也可以这样初始化：Lists.newArrayList("item1", "item2");
    }
}
```

```java
// 样例 3: 实例化Form对象 (构造函数调用)
// 描述: 展示如何创建一个新的Form对象，并指定其模型ID。
// 规则依据: 可执行的逻辑片段，构造函数调用是绝对可靠的，去业务化。
// 预期学习点: 学习如何创建框架中的核心数据载体Form对象。
//-------------------------------------------------------------------------
import gpf.adur.data.Form;

public class SampleNewForm {
    public void demonstrate() {
        // Form构造函数通常需要一个模型ID来标识其类型
        String yourModelId = "YOUR_FORM_MODEL_ID";
        Form newForm = new Form(yourModelId);
    }
}
```

```java
// 样例 4: 实例化AssociationData对象 (构造函数调用)
// 描述: 展示如何创建一个新的AssociationData对象，用于表示数据之间的关联。
// 规则依据: 可执行的逻辑片段，构造函数调用是绝对可靠的，去业务化。
// 预期学习点: 学习如何创建框架中表示关联数据的AssociationData对象。
//-------------------------------------------------------------------------
import gpf.adur.data.AssociationData;

public class SampleNewAssociationData {
    public void demonstrate() {
        // AssociationData 通常需要一个关联的模型ID和具体的值
        String associatedModelId = "YOUR_ASSOCIATED_MODEL_ID";
        String associatedValue = "YOUR_ASSOCIATED_VALUE";
        AssociationData association = new AssociationData(associatedModelId, associatedValue);
    }
}
```

```java
// 样例 5: 设置Form对象的属性值 (对象构建与方法调用组合)
// 描述: 展示如何创建一个Form对象，并为其设置一个属性值，该属性值是一个AssociationData。
// 规则依据: 可执行的逻辑片段，对象创建后紧接的方法调用是可靠的，去业务化，保持原子性（构建并配置一个Form）。
// 预期学习点: 学习如何构建并初始化一个Form对象，特别是包含关联数据类型的属性。
//-------------------------------------------------------------------------
import gpf.adur.data.AssociationData;
import gpf.adur.data.Form;

public class SampleFormSetAttributeValue {
    public void demonstrate() {
        String yourFormModelId = "YOUR_FORM_MODEL_ID";
        String attributeKey = "YOUR_ATTRIBUTE_KEY"; // 例如：ChatGroupConst.对话规则_角色
        String associatedModelId = "YOUR_ASSOCIATED_MODEL_ID"; // 例如：ChatGroupConst.ModelId_群组角色
        String associatedValue = "YOUR_ASSOCIATED_VALUE"; // 例如：角色编码

        Form newForm = new Form(yourFormModelId);
        newForm.setAttrValue(attributeKey, new AssociationData(associatedModelId, associatedValue));

        // 您也可以设置其他类型的属性，例如字符串：
        // newForm.setAttrValue("ANOTHER_ATTRIBUTE_KEY", "simple_string_value");
    }
}
```