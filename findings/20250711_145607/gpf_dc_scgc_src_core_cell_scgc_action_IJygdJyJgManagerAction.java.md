# Analysis for: gpf_dc_scgc\src\core\cell\scgc\action\IJygdJyJgManagerAction.java

## Original File Path
`D:\TMP\CLASS_BUS_PROJECT\gpf_dc_scgc\src\core\cell\scgc\action\IJygdJyJgManagerAction.java`

## Extracted Snippets & Analysis
好的，作为一名资深的软件架构师，我将严格遵循您的核心规则，从提供的代码中提炼出简洁、优雅且极具教学价值的核心代码模式。

以下是识别并提取出的代码样例：

---

### 提取的框架API代码样例

#### 1. 获取核心框架对象

##### 1.1 从 `ViewActionParameter` 获取 `IDao` 实例
```java
// 假设 yourViewActionParameter 是一个已存在的 ViewActionParameter 实例
IDao yourDao = yourViewActionParameter.getRtx().getDao();
```

##### 1.2 从 `ViewActionParameter` 获取 `PanelContext` 实例
```java
// 假设 yourViewActionParameter 是一个已存在的 ViewActionParameter 实例
PanelContext yourPanelContext = yourViewActionParameter.getPanelContext();
```

##### 1.3 从 `ViewActionParameter` 获取 `Form` 实例
```java
// 假设 yourViewActionParameter 是一个已存在的 ViewActionParameter 实例
Form yourForm = yourViewActionParameter.getForm();
```

#### 2. 加载蒙版操作

##### 2.1 显示圆形进度加载蒙版
```java
// 假设 yourPanelContext 是一个已存在的 PanelContext 实例
LoadingMask.showCircularProgress(yourPanelContext);
```

##### 2.2 隐藏加载蒙版
```java
// 假设 yourPanelContext 是一个已存在的 PanelContext 实例
LoadingMask.hide(yourPanelContext);
```

#### 3. 表单（Form）数据操作

##### 3.1 从 Form 获取关联数据 (AssociationData)
```java
// 假设 yourForm 是一个已存在的 Form 实例
AssociationData yourAssociationData = yourForm.getAssociation("your_association_key");
```

##### 3.2 从 AssociationData 获取嵌套 Form
```java
// 假设 yourAssociationData 是一个已存在的 AssociationData 实例
Form yourNestedForm = yourAssociationData.getForm();
```

##### 3.3 从 Form 获取指定键的字符串值
```java
// 假设 yourForm 是一个已存在的 Form 实例
String yourStringValue = yourForm.getString("your_attribute_name");
```

##### 3.4 向 Form 设置属性值
```java
// 假设 yourForm 是一个已存在的 Form 实例
// your_attribute_value 可以是 String, Number, Boolean, DateTime 等类型
yourForm.setAttrValue("your_attribute_key", your_attribute_value);

// 示例：设置当前时间为某个属性值
yourForm.setAttrValue("your_time_attribute_key", cn.hutool.core.date.DateTime.now());
```

#### 4. 框架日志与消息提示

##### 4.1 框架内日志打印 (带格式化)
```java
// 假设 yourValue1, yourValue2 是需要打印的变量
logf("your_log_message_with_placeholder_1:{}, placeholder_2:{}", yourValue1, yourValue2);
```

##### 4.2 显示信息提示 (Toast)
```java
// 假设 yourPanelContext 是一个已存在的 PanelContext 实例
PopToast.info(yourPanelContext.getChannel(), "your_info_message_content");
```

##### 4.3 框架内跟踪信息
```java
LvUtil.trace("your_trace_message_content");
```

#### 5. 面板上下文 (PanelContext) 转换

##### 5.1 转换 PanelContext 到指定字段代码的上下文
```java
// 假设 yourOriginalPanelContext 是一个已存在的 PanelContext 实例
// getFieldCode("your_field_name") 是一个框架方法，用于获取字段的唯一代码
PanelContext convertedContext = ConvertPanelContext.convert(yourOriginalPanelContext, getFieldCode("your_field_name"));
```

#### 6. 表格 (Table) 数据操作

##### 6.1 查询所有表格行数据
```java
// 假设 yourPanelContext 是一个已存在的 PanelContext 实例
List<TableRowDto> allRows = QueryTableRows.queryAll(yourPanelContext);
```

##### 6.2 从 TableRowDto 获取二进制数据（并转换为 Form）
```java
// 假设 yourTableRowDto 是一个已存在的 TableRowDto 实例
Form yourFormObject = (Form) yourTableRowDto.getBinaryData();
```

##### 6.3 设置 TableRowDto 的二进制数据
```java
// 假设 yourTableRowDto 是一个已存在的 TableRowDto 实例
// yourDataObject 可以是任何实现了二进制存储接口的对象，例如 Form
yourTableRowDto.setBinaryData(yourDataObject);
```

##### 6.4 通过 NestingFormTableView 转换 Form 到 TableRowDto
```java
// 假设 yourNestingFormTableView 是一个已存在的 NestingFormTableView 实例
// yourForm 是需要转换的 Form 实例
TableRowDto newRow = yourNestingFormTableView.convert2TableRowDto(yourForm);
```

##### 6.5 设置 TableRowDto 的行 ID
```java
// 假设 yourTableRowDto 是一个已存在的 TableRowDto 实例
yourTableRowDto.setRowId("your_row_id_string");
```

##### 6.6 批量更新表格行数据
```java
// 假设 yourPanelContext 是一个已存在的 PanelContext 实例
// yourListOfTableRowDtos 是一个包含 TableRowDto 对象的 List
UpdateRows.execute(yourPanelContext, yourListOfTableRowDtos);
```

#### 7. 嵌套表单视图 (NestingFormTableView) 操作

##### 7.1 查询指定 PanelContext 和 Widget ID 的二进制数据（转换为 NestingTableViewParam）
```java
// 假设 yourPanelContext 是一个已存在的 PanelContext 实例
// yourPanelContext.getCurrentPanelWidgetId() 是一个框架方法，获取当前面板部件 ID
NestingTableViewParam yourParam = (NestingTableViewParam) QueryBinaryData.queryOne(yourPanelContext, yourPanelContext.getCurrentPanelWidgetId());
```

##### 7.2 实例化 NestingFormTableView
```java
NestingFormTableView<NestingTableViewParam> yourTableView = new NestingFormTableView<>();
```

##### 7.3 设置 NestingFormTableView 的部件参数
```java
// 假设 yourNestingFormTableView 是一个已存在的 NestingFormTableView 实例
// yourNestingTableViewParam 是一个已存在的 NestingTableViewParam 实例
yourNestingFormTableView.setWidgetParam(yourNestingTableViewParam);
```

##### 7.4 设置 NestingFormTableView 的面板上下文
```java
// 假设 yourNestingFormTableView 是一个已存在的 NestingFormTableView 实例
// yourPanelContext 是一个已存在的 PanelContext 实例
yourNestingFormTableView.setPanelContext(yourPanelContext);
```

#### 8. 数据查询（IFormMgr）

##### 8.1 构建查询条件 (Nutz.Dao Cnd)
```java
// Cnd (Condition) 是用于构建查询条件的类，广泛用于与 IDao 交互
Cnd yourCondition = Cnd.NEW();
yourCondition.where().andEquals(getFieldCode("your_field_code_1"), your_value_1)
                     .andEquals(getFieldCode("your_field_code_2"), your_value_2);
```

##### 8.2 通过 IFormMgr 查询表单分页数据
```java
// 假设 yourDao 是一个已存在的 IDao 实例
// YOUR_FORM_MODEL_CONSTANT 是框架定义的表单模型常量，如 SZBFormModel
// yourCondition 是一个已构建的 Cnd 查询条件
// yourPageIndex 是页码 (从1开始), yourPageSize 是每页大小
// yourUseCacheFlag 和 yourAnotherFlag 是布尔类型的控制参数
ResultSet<Form> yourResultSet = IFormMgr.get().queryFormPage(
    yourDao,
    YOUR_FORM_MODEL_CONSTANT,
    yourCondition,
    yourPageIndex,
    yourPageSize,
    yourUseCacheFlag,
    yourAnotherFlag
);
```

##### 8.3 检查 ResultSet 是否为空
```java
// 假设 yourResultSet 是一个已存在的 ResultSet 实例
boolean isEmpty = yourResultSet.isEmpty();
```

##### 8.4 从 ResultSet 获取数据列表并访问单个元素
```java
// 假设 yourResultSet 是一个已存在的 ResultSet 实例且不为空
List<Form> dataList = yourResultSet.getDataList();
Form firstForm = dataList.get(0); // 或者 dataList.get(yourIndex);
```
---