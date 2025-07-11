# Analysis for: gpf_dc_ReportGenerator\src\core\fe\reportGenerator\component\ProjectTableEditDataPage.java

## Original File Path
`D:\TMP\CLASS_BUS_PROJECT\gpf_dc_ReportGenerator\src\core\fe\reportGenerator\component\ProjectTableEditDataPage.java`

## Extracted Snippets & Analysis
根据提供的代码和核心规则，我提取了以下代码样例：

**样例 1: 显示加载进度**

```java
LoadingMask.showLinearProgress(panelContext);
```

这个样例展示了如何使用`LoadingMask`类显示一个线性进度指示器。`panelContext`是已知的上下文对象，不会影响样例的独立性。


**样例 2: 隐藏加载进度**

```java
LoadingMask.hide(panelContext);
```

这个样例展示了如何隐藏加载进度指示器。


**样例 3: 显示信息提示框**

```java
PopToast.info(panelContext.getChannel(), "此处填写您的提示信息");
```

此样例展示了如何使用`PopToast`显示一个信息提示框，提示信息被替换为占位符。


**样例 4: 显示确认对话框**

```java
PopDialog.showConfirm(panelContext, "提示", "确定进行保存？");
```

此样例展示了如何使用`PopDialog`显示一个确认对话框。


**样例 5: 设置编辑器值**

```java
SetEditorValue.set(panelContext, "your_widget_id", "此处填写您的编辑器内容");
```

此样例展示了如何使用`SetEditorValue`设置编辑器的值。`your_widget_id` 和 `"此处填写您的编辑器内容"` 为占位符。


**样例 6: 退出弹出窗口**

```java
QuitPopup.quit(panelContext, true);
```

这个样例展示了如何使用`QuitPopup`关闭当前弹出窗口。


**样例 7: 生成UUID**

```java
IdUtil.fastSimpleUUID();
```

这个样例展示了如何使用`IdUtil`生成一个UUID。


**样例 8:  构建按钮**

虽然代码中没有直接构建按钮的独立方法调用，但`buildTopBar`方法中体现了按钮构建的模式：

```java
new ButtonDto("导出", JDFICons.export_data).setOnClick(newOnClick(getBuilderService(), "CMD_EXPORT_REPORT", false, null)).setMargin(new InsetDto().setRight(10d));
```

这展示了如何创建一个按钮，设置文本，图标和点击事件。  需要注意的是`newOnClick`方法的内部实现未知，但我们关注的是`ButtonDto`的构建模式。  `JDFICons.export_data`和`"CMD_EXPORT_REPORT"`分别需要替换为通用的图标资源标识符和命令标识符。


这些样例都符合核心规则：它们是独立的、可执行的代码片段，展示了如何使用框架的API完成特定任务，并且使用了通用的占位符来代替业务相关的具体值，保持了原子性和可复用性。  其他的代码，例如`ProjectDataConversionUtil.doDownloadReport`方法调用，由于依赖于私有库和业务数据，不符合规则，因此没有提取。
