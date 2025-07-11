# Analysis for: gpf_rapidView_pmc\src\core\cell\rapidView\function\ViewFunctions.java

## Original File Path
`D:\TMP\CLASS_BUS_PROJECT\gpf_rapidView_pmc\src\core\cell\rapidView\function\ViewFunctions.java`

## Extracted Snippets & Analysis
好的，作为一名资深架构师，我将严格遵循您的核心规则，从提供的代码中提炼出简洁、优雅且极具教学价值的API使用模式。

以下是我从代码中识别并提取出的高质量代码样例：

---

```java
// 核心API: PopupPanel.showConfirm
// Pattern: 显示一个带有自定义尺寸的确认对话框，并等待用户操作
// 前提条件: PanelContext panelContext 已知
public void showCustomConfirmDialogPattern(PanelContext panelContext) throws Exception {
    boolean defaultShowClose = true;
    boolean defaultBarrierDismissible = true;
    // 动作: 显示确认对话框并捕获结果
    boolean confirmed = PopupPanel.showConfirm(
        panelContext,
        "此处填写对话框标题",
        "此处填写提示信息",
        SizeDto.all(300, 100), // 自定义尺寸
        defaultShowClose,
        null, // 此处可填加取消按钮的EscapeButtonDto
        defaultBarrierDismissible
    );
    // 后续处理 (例如):
    // if (confirmed) {
    //     System.out.println("用户点击了确认");
    // } else {
    //     System.out.println("用户点击了取消或关闭");
    // }
}

// 核心API: PopToast.success
// Pattern: 显示一个成功的提示信息 (Toast)
// 前提条件: PanelContext panelContext 已知
public void showSuccessToastPattern(PanelContext panelContext) throws Exception {
    // 动作: 显示成功Toast
    PopToast.success(panelContext.getChannel(), "此处填写成功提示信息");
}

// 核心API: PopToast.warning
// Pattern: 显示一个警告的提示信息 (Toast)
// 前提条件: PanelContext panelContext 已知
public void showWarningToastPattern(PanelContext panelContext) throws Exception {
    // 动作: 显示警告Toast
    PopToast.warning(panelContext.getChannel(), "此处填写警告提示信息");
}

// 核心API: QuitPopup.quit
// Pattern: 关闭当前弹窗
// 前提条件: PanelContext panelContext 已知
public void quitCurrentPopupPattern(PanelContext panelContext) throws Exception {
    // 动作: 关闭当前弹窗
    QuitPopup.quit(panelContext);
}

// 核心API: EscapeButtonDto
// Pattern: 创建一个带有文本、ID并设置为确认风格的EscapeButtonDto
public EscapeButtonDto createConfirmEscapeButtonDtoPattern() {
    // 动作: 创建并配置EscapeButtonDto
    EscapeButtonDto button = (EscapeButtonDto) new EscapeButtonDto()
        .setText("此处填写按钮文本")
        .setWidgetId("your_button_widget_id") // 按钮的唯一ID
        .setConfirmStyle(); // 设置为确认风格
    return button;
}

// 核心API: TextEditorDto, TextEditorDecorationDto, CTextStyle
// Pattern: 创建一个不可写且带文本样式装饰的TextEditorDto
public TextEditorDto createDecoratedTextEditorDtoPattern() {
    // 动作: 创建并配置TextEditorDto及其装饰
    TextEditorDto textEditor = new TextEditorDto("此处填写初始文本内容")
        .setWritable(false) // 设置为不可编辑
        .setMinRenderLines(20) // 设置最小显示行数
        .setDecoration(new TextEditorDecorationDto()
            .setTextStyle(new CTextStyle()
                .setFontSize(15D))); // 设置文本字体大小
    return textEditor;
}

// 核心API: LabelDto, LabelDecorationDto, CTextStyle
// Pattern: 创建一个带文本样式装饰的LabelDto
public LabelDto createDecoratedLabelDtoPattern() {
    // 动作: 创建并配置LabelDto及其装饰
    LabelDto label = new LabelDto("此处填写标签文本")
        .setDecoration(new LabelDecorationDto()
            .setTextStyle(new CTextStyle()
                .setFontSize(15D))); // 设置文本字体大小
    return label;
}

// 核心API: SinglePanelDto, SizeDto
// Pattern: 创建一个SinglePanelDto并设置首选尺寸
// 前提条件: Component yourWidgetDto (如 LabelDto, TextEditorDto) 已知
public SinglePanelDto createSizedSinglePanelDtoPattern(Component yourWidgetDto) {
    // 动作: 创建并配置SinglePanelDto
    SinglePanelDto panel = new SinglePanelDto(yourWidgetDto)
        .setPreferSize(SizeDto.all(800, 600)); // 设置面板的首选宽度和高度
    return panel;
}

// 核心API: PopDialog.build
// Pattern: 构建一个弹出对话框 PopDialog (链式调用)
// 前提条件: String title, SinglePanelDto panel, EscapeButtonDto okButton (可以为null), EscapeButtonDto cancelButton (可以为null)
public PopDialog buildPopDialogPattern(String title, SinglePanelDto panel, EscapeButtonDto okButton, EscapeButtonDto cancelButton) {
    // 动作: 链式构建PopDialog实例
    PopDialog dialog = PopDialog.build(
            title,
            panel,
            okButton,
            cancelButton,
            true // 是否显示关闭按钮 (右上角X)
        )
        .setDecoration(null) // 设置对话框装饰，此处可替换为 DialogDecorationDto 实例
        .setBarrierDismissible(true) // 是否允许点击对话框外部关闭
        .setWaitForClose(10 * 60 * 1000L) // 设置等待对话框关闭的超时时间 (毫秒)
        .setTimeout(10 * 60 * 1000L); // 设置对话框的总超时时间 (毫秒)
    return dialog;
}

// 核心API: PanelContext.callback
// Pattern: 通过PanelContext回调显示一个对话框并等待结果
// 前提条件: PanelContext panelContext 已知, PopDialog dialog 已知
public PanelValue showDialogAndGetResultPattern(PanelContext panelContext, PopDialog dialog) throws Exception {
    // 动作: 执行回调，显示对话框并等待其关闭，返回PanelValue
    PanelValue panelValue = (PanelValue) panelContext.callback(dialog);
    // 后续处理 (例如):
    // if (panelValue != null) {
    //     int clickOK = ToolUtilities.getInteger(panelValue.getValue("your_button_widget_id"), -1);
    //     if (clickOK > 0) {
    //         System.out.println("用户点击了确认按钮");
    //     }
    // }
    return panelValue;
}

// 核心API: ToolUtilities.getInteger
// Pattern: 从Map中获取Integer值，提供默认值
// 前提条件: Map<String, Object> yourMap 已知
public int getIntegerFromMapPattern(Map<String, Object> yourMap) {
    // 动作: 从Map中安全获取Integer值
    int value = ToolUtilities.getInteger(yourMap.get("your_key_for_integer"), -1); // 如果键不存在或值无效，则返回-1
    return value;
}

// 核心API: LoadingMask.showCircularProgress
// Pattern: 显示加载遮罩 (圆形进度条)
// 前提条件: PanelContext panelContext 已知
public void showLoadingMaskPattern(PanelContext panelContext) throws Exception {
    // 动作: 显示加载遮罩
    LoadingMask.showCircularProgress(panelContext);
}

// 核心API: LoadingMask.hide
// Pattern: 隐藏加载遮罩
// 前提条件: PanelContext panelContext 已知
public void hideLoadingMaskPattern(PanelContext panelContext) throws Exception {
    // 动作: 隐藏加载遮罩
    LoadingMask.hide(panelContext);
}

// 核心API: LaunchUrl.launch
// Pattern: 跳转到指定URL，可选择在新标签页打开
// 前提条件: PanelContext panelContext 已知
public void jumpToUrlPattern(PanelContext panelContext) throws Exception {
    // 动作: 执行URL跳转
    LaunchUrl.launch(panelContext.getChannel(), "此处填写目标URL路径", true); // true表示在新标签页打开，false表示在当前页打开
}

// 核心API: QueryPopContextStack.queryRoot
// Pattern: 查询弹出上下文栈的根列表
// 前提条件: PanelContext panelContext 已知
public List<PanelContext> queryRootPopContextStackPattern(PanelContext panelContext) {
    // 动作: 查询根级别的PanelContext栈
    List<PanelContext> contextsStacks = QueryPopContextStack.queryRoot(panelContext.getChannel());
    return contextsStacks;
}

// 核心API: QueryBinaryData.queryOne
// Pattern: 从PanelContext中查询二进制数据
// 前提条件: PanelContext context 已知
public Object queryBinaryDataFromContextPattern(PanelContext context) {
    // 动作: 查询上下文关联的二进制数据
    Object data = QueryBinaryData.queryOne(context);
    return data;
}

// 核心API: IFeCmnService.get().getComponentInstance
// Pattern: 通过IFeCmnService获取组件实例
// 前提条件: PanelContext panelContext 已知, WidgetParam widgetParam 已知
public AbsFormView getComponentInstancePattern(PanelContext panelContext, WidgetParam widgetParam) throws Exception {
    // 动作: 获取特定参数的组件实例
    AbsFormView formView = (AbsFormView) IFeCmnService.get()
        .getComponentInstance(panelContext, widgetParam);
    return formView;
}

// 核心API: WriteLocalStorage.write, PairDto
// Pattern: 向本地存储写入数据 (使用PairDto封装键值对)
// 前提条件: PanelContext panelContext 已知
public void writeDataToLocalStoragePattern(PanelContext panelContext) throws Exception {
    // 动作: 将键值对写入本地存储
    // "your_storage_prefix" 应替换为您实际使用的存储前缀
    WriteLocalStorage.write(panelContext.getChannel(), "your_storage_prefix", new PairDto<>("your_key", "your_object_value"));
}

// 核心API: ReadLocalStorage.read
// Pattern: 从本地存储读取数据
// 前提条件: PanelContext panelContext 已知
public Object readDataFromLocalStoragePattern(PanelContext panelContext) throws Exception {
    // 动作: 从本地存储读取数据
    // "your_storage_prefix" 应替换为您实际使用的存储前缀
    Object value = ReadLocalStorage.read(panelContext.getChannel(), "your_storage_prefix", "your_key");
    return value;
}

// 核心API: QueryTableRows.queryOne
// Pattern: 查询表格中的一行数据 (通过行ID)
// 前提条件: TableContext tableContext 已知 (或其子类如 TableSelectEditorQuerierContext)
public TableRowDto queryTableRowByIdPattern(TableContext tableContext) throws Exception {
    // 动作: 根据行ID查询表格中的单行数据
    TableRowDto tableRow = QueryTableRows.queryOne(tableContext, "your_row_id"); // "your_row_id" 替换为实际行ID
    return tableRow;
}

// 核心API: QueryListViewItem.query
// Pattern: 查询列表视图中的一个项目 (通过项目键)
// 前提条件: ListViewContext listViewContext 已知
public ListViewItemDto queryListViewItemByKeyPattern(ListViewContext listViewContext) throws Exception {
    // 动作: 根据项目键查询列表视图中的单个项目
    ListViewItemDto itemDto = QueryListViewItem.query(listViewContext, "your_item_key"); // "your_item_key" 替换为实际项目键
    return itemDto;
}

// 核心API: IFormMgr.get().queryForm
// Pattern: 通过Form管理器查询完整的表单数据
// 前提条件: IDao dao 已知, String formModelId, String uuid 已知
public Form queryFullFormDataPattern(IDao dao, String formModelId, String uuid) throws Exception {
    // 动作: 查询完整的表单数据
    Form fullDataForm = IFormMgr.get().queryForm(dao, formModelId, uuid);
    return fullDataForm;
}

// 核心API: QueryTableRows.querySelected
// Pattern: 查询表格中所有选中的行
// 前提条件: TableContext panelContext 已知
public List<TableRowDto> querySelectedTableRowsPattern(TableContext panelContext) throws Exception {
    // 动作: 查询表格中所有被选中的行
    List<TableRowDto> rows = QueryTableRows.querySelected(panelContext);
    return rows;
}

// 核心API: IdUtil.fastUUID
// Pattern: 生成一个快速UUID
public String generateFastUUIDPattern() {
    // 动作: 生成一个不带连字符的UUID字符串
    String uuid = IdUtil.fastUUID();
    return uuid;
}

// 核心API: IDaoService.newIDao, IDao.commit, IFormMgr.get().createForm
// Pattern: 创建IDao实例，执行表单创建操作并提交事务
// 前提条件: Form yourFormObject 已知
public void createFormAndCommitPattern(Form yourFormObject) throws Exception {
    // 动作: 创建IDao实例，在事务中创建表单，并提交
    try (IDao dao = IDaoService.newIDao()) { // 使用 try-with-resources 确保dao关闭
        IFormMgr.get().createForm(dao, yourFormObject); // 创建表单数据
        dao.commit(); // 提交事务
    } catch (Exception e) {
        // 通常此处需要记录日志或执行 dao.rollback()
        e.printStackTrace();
        throw e; // 重新抛出异常，以便调用方处理
    }
}
```