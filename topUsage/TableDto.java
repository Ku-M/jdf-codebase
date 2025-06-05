package fe.cmn.table;

import java.util.List;

import com.leavay.common.util.ToolUtilities;

import fe.cmn.event.EventSubscriberDto;
import fe.cmn.panel.PanelDto;
import fe.cmn.pojo.annotation.FieldDefine;
import fe.cmn.pojo.annotation.PojoMeta;
import fe.cmn.table.listener.OnTableCellValueChanged;
import fe.cmn.table.listener.OnTableHeaderClick;
import fe.cmn.table.listener.OnTableSelectValueChanged;
import fe.cmn.widget.DraggableDto;
import fe.cmn.widget.DropListener;
import fe.cmn.widget.MergeableDraggableDto;
import fe.cmn.widget.SizeDto;
import fe.cmn.widget.WidgetDto;
import fe.cmn.widget.decoration.DecorationDto;
import flutter.coder.annt.DefaultGetter;
import flutter.coder.annt.NullSafe;

/**
 * TableDto是表格组件(数据表格视图)
 * 
 * JDF中构建表格TableDto不可以直接设置表头等元数据, 必须通过捆绑设定TableOpener/TableInterface服务来实现表头构建以及表数据查询的.
 * 
 * 表格的表头构建, 以及数据查询都要通过配套实现后端服务(TableInterface)来实现, 而后端服务名是设置在TableOpener中的, 再把TableOpener设置到TableDto中.
 * 
 * 也就是说每构建一个TableDto都要实现一组TableInterface服务与之配套使用
 * 
 * 要给一个TableDto设置表头也不是直接可以设置的, 需实现TableInterface服务, 再通过实现其queryTableMeta提供表头元数据, 从而构建出表头
 * 
 * TableDto(表格组件)必须设置TableOpener,其中设置后端服务接口, 来提供表格结构(表头,列格式等)的构建以及表数据查询服务
 * 而在TableOpener中设定的后端服务需派生于TableInterface, 通过此后端服务返回表头结构来构建出表格的列以及单元格编辑器等设定
 * 当表格初次在界面构建时, 会调用TableOpener中指定的后端服务获取表头结构及其它元数据, 并通过传入首页查询器(firstPageQuerier)获取第一页表格数据
 * 
 * 使用TableDto, 以及构建表头和查询数据的样例代码:
 *      //构建一个TableOpener其中指定了后端服务类(IFeDemoService.class), 以及查询器querier(查询第一页数据所需的必要信息)
        TableOpener opener = new TableOpener().setBuilder(new DaoDataTableBuilder().setModel(model)).setFirstPageQuerier(new DaoDataTableQuerier().setModel(model).setStartPos(0).setPageSize(10).setQueryCount(true));
        opener.setServiceName(IFeDemoService.class); // 给表格设定后端服务(提供表头构建以及表格数据查询服务)
        TableDto tbl = new TableDto().setOpener(opener);
        
       //然后在IFeDemoService类中实现TableInterface的必要接口:
       public interface IFeDemoService extends CellIntf, TableInterface
       {
            // 此后端服务接口构建并返回表格的构建元数据, 其中包括表头列的构建以及编辑器设定等
            @Override
            public TableHeaderDto queryTableMeta(TableBuilder builder, TableQuerierContext context) throws Exception
            {
                // 根据builder附带的信息结合业务逻辑, 构建出所需的表头结构
                List<TableColumnDto> lstCols = new LinkedList<TableColumnDto>();
                for (CDaoMappingField f : entity.getMappingFields())
                {
                    if (builder.isColumnIgnore(f.getName()))
                        continue;
        
                    // 构建表头列
                    TableColumnDto c = new TableColumnDto();
                    // 设置该列的编辑器(普通标签框), 及其样式
                    c.setEditor(new LabelDto().setDecoration(new DecorationDto().setTextStyle(new CTextStyle().setItalic(true)).setBackground(CColor.fromColor(GuiUtil.randomColor()).setOpacity(0.3f))));
                    c.setName(f.getName());
                    lstCols.add(c);
                }
        
                // 构建并返回表头元数据
                TableHeaderDto meta = new TableHeaderDto();
                meta.setColumns(lstCols);
        
                return meta;
            }

            // 此后端服务接口提供表格数据查询的功能
            @Override
            public TableRowsDto queryTableRows(TableBuilder builder, TableQuerier querier, TableQuerierContext context) throws Exception
            {
                // 查询表格数据并返回
                return new TableRowsDto();
            }
       }
       
       详细样例可参考:CStudyTable
 */
@PojoMeta(label = "表格", icon="res://images/units/table.png")
public class TableDto extends PanelDto {
	private static final long serialVersionUID = -4712995940313203321L;

	@NullSafe
	TableOpener opener;

	// 单页数据量下拉选择框值
	List<Integer> pageSizes;

	/**
	 * 列宽度自适应开关
	 * 
	 * 列宽度自适应需要同时满足以下三个要求：
	 * 1.此字段为true
	 * 2.列的宽度自适应类型不为ColumnWidthAutoFitType.none
	 * 3.列无设置固定宽度（fixWidth）
	 */
	@NullSafe(initCode = "true")
	Boolean autoFit = true;
	
	/**
	 * 默认列宽度自适应设置（优先级低于列自身设置）
	 * 
	 * 以下单元格满足宽度计算：
	 * 1.表格原生文本显示（无插槽（editorDto）设置）
	 * 2.插槽类型（editorDto）为LabelDto
	 * 3.插槽组件设置了固定宽度
	 * 
	 * 无法计算宽度的插槽单元格宽按200处理
	 */
	@NullSafe(initCode = "ColumnWidthAutoFitType.content")
	ColumnWidthAutoFitType autoFitType = ColumnWidthAutoFitType.content;
	
	// 隐藏表头
	boolean hideHeader;

	// 勾选框显示控制
	@Deprecated
	@NullSafe(initCode = "false")
	boolean showCheckbox = false;
	
	/**
	 * 勾选类型控制（将替代showCheckbox）
	 */
	TableCheckType checkType;
	
	// 是否允许界面修改勾选值，默认允许
	@DefaultGetter("true")
	Boolean enableCheckboxChange = true;

	// 列拖拽
	@NullSafe(initCode = "false")
	boolean enableColumnDrag = false;

	// 行拖拽排序
	@NullSafe(initCode = "false")
	boolean enableRowDrag = false;
	
	// 前置操作列（行下标 + 勾选 + 拖拽）是否悬浮固定
	@NullSafe(initCode = "true")
	boolean preOperateColumnFrozen = true;
	
	// 单击选择行时是否保持只有一行可以选中，默认为true
	@DefaultGetter("true")
	Boolean keepOneRowSelectedOnSingleClick;
	
	// 自定义按钮操作列
	OperateTableColumnDto operateTableColumn;
	
	// 鼠标单击或双击进行编辑
	TextEditorEditMouseEventType textEditorEditMouseEventType;
	
	// 单位时间内勾选点击仅触发一次(毫秒)
	Integer checkDelayed;
	
	// 通过勾选监听返回最终的勾选结果显示（后端监听干预决定）
	@DefaultGetter("false")
	Boolean checkedByListener;
	
	// 表头点击监听
	@Deprecated
	OnTableHeaderClick onTableHeaderClick;
	
	// 表头事件
	TableHeaderGestureDetectorDto headerGestureDetector;
	
	// 行事件
	TableRowGestureDetectorDto tableRowGestureDetector;

	// 单元格值变化监听
	OnTableCellValueChanged onTableCellValueChanged;

	// 勾选行变化监听
	OnTableSelectValueChanged onTableSelectValueChanged;
	
	@DefaultGetter("false")
	@FieldDefine(label="表格单元格选中时是否高亮显示")
	@Deprecated
	Boolean tableCellHighlightWhenSelected;
	
	// 鼠标点击是默认高亮行
	@DefaultGetter("HighlightType.row")
	HighlightType highlightType;
	
	// 表格单元格高亮通过shift多选
	Boolean tableCellsHighlightByShift;
	
	// 可应用于表格行拖拽设置匹配合并的列表（TableRowDto中的draggable也需要同时为MergeableDraggableDto）
	List<MergeableDraggableDto> rowDraggableList;
	
	// 表格最后一列拖拽后如果出现空白区域，是否由其他列填充
	Boolean lastColumnDragFillArea;
	
	// 是否显示行下标列
	@DefaultGetter("false")
	Boolean showRowIndex;
	
	// 限制勾选数量
	Integer checkLimit;
		
	// 没有数据时展示的文本
	String noDataText;
	
	/**
	 * 行前面的组件，仅在自身位置及行hover触发时显示。
	 * 最大高度为行高。
	 */
	WidgetDto rowPrefixWidget;
	
	/**
	 * 行流图锚点设置。
	 */
	TableRowAnchorSettingsDto rowAnchorSettings;
	
	/**
	 * 是否开启界面列排序功能。（默认为false，可被列设置（TabelCoumnDto.enableGuiSorting）覆盖）
	 * 
	 * 可通过TableHeaderDto.sorter，指定数据排序。
	 */
	@DefaultGetter("false")
	Boolean enableGuiSorting;

	public TableDto() {
	    this(new TableOpener());
	}

	public TableDto(String panelKey) {
        this(panelKey, new TableOpener());
	}
	
	public TableDto(TableOpener opener) {
		super();
		this.opener = opener;
	}

	public TableDto(String panelKey, TableOpener opener) {
		super(panelKey);
		this.opener = opener;
	}

	public TableDto(String panelKey, TableOpener opener, List<Integer> pageSizes) {
		super(panelKey);
		this.opener = opener;
		this.pageSizes = pageSizes;
	}

	public TableDto(String panelKey, TableOpener opener, Integer... pageSizes) {
		super(panelKey);
		this.opener = opener;
		this.pageSizes = ToolUtilities.array2List(pageSizes);
	}

	public TableDto(String panelKey, TableOpener opener, Boolean autoFit) {
		super(panelKey);
		this.opener = opener;
		this.autoFit = autoFit;
	}

	public TableDto(String panelKey, TableOpener opener, OnTableCellValueChanged onTableCellValueChanged) {
		super(panelKey);
		this.opener = opener;
		this.onTableCellValueChanged = onTableCellValueChanged;
	}

	public TableDto(String panelKey, TableOpener opener, OnTableCellValueChanged onTableCellValueChanged,
			List<Integer> pageSizes) {
		super(panelKey);
		this.opener = opener;
		this.pageSizes = pageSizes;
		this.onTableCellValueChanged = onTableCellValueChanged;
	}

	public TableDto(String panelKey, TableOpener opener, OnTableCellValueChanged onTableCellValueChanged,
			Integer... pageSizes) {
		super(panelKey);
		this.opener = opener;
		this.pageSizes = ToolUtilities.array2List(pageSizes);
		this.onTableCellValueChanged = onTableCellValueChanged;
	}

	public TableOpener getOpener() {
		return opener;
	}

	public TableDto setOpener(TableOpener opener) {
		this.opener = opener;
		return this;
	}

	public List<Integer> getPageSizes() {
		return this.pageSizes;
	}

	public TableDto setPageSizes(List<Integer> pageSizes) {
		this.pageSizes = pageSizes;
		return this;
	}

	public Boolean getAutoFit() {
		return autoFit;
	}

	public TableDto setAutoFit(Boolean autoFit) {
		this.autoFit = autoFit;
		return this;
	}

	public ColumnWidthAutoFitType getAutoFitType() {
		return autoFitType;
	}

	public TableDto setAutoFitType(ColumnWidthAutoFitType autoFitType) {
		this.autoFitType = autoFitType;
		return this;
	}

	public TableDto setPageSizes(Integer... pageSizes) {
		this.pageSizes = ToolUtilities.array2List(pageSizes);
		return this;
	}
	
	public TextEditorEditMouseEventType getEditMouseEventType() {
		return textEditorEditMouseEventType;
	}

	public TableDto setTextEditorEditMouseEventType(TextEditorEditMouseEventType textEditorEditMouseEventType) {
		this.textEditorEditMouseEventType = textEditorEditMouseEventType;
		return this;
	}

	public OnTableCellValueChanged getOnTableCellValueChanged() {
		return onTableCellValueChanged;
	}

	public TableDto setOnTableCellValueChanged(OnTableCellValueChanged onTableCellValueChanged) {
		this.onTableCellValueChanged = onTableCellValueChanged;
		return this;
	}

	public boolean isShowCheckbox() {
		return showCheckbox;
	}

	public TableDto setShowCheckbox(boolean showCheckbox) {
		this.showCheckbox = showCheckbox;
		return this;
	}

	public boolean isEnableColumnDrag() {
		return enableColumnDrag;
	}

	public TableDto setEnableColumnDrag(boolean enableColumnDrag) {
		this.enableColumnDrag = enableColumnDrag;
		return this;
	}

	public boolean isEnableRowDrag() {
		return enableRowDrag;
	}

	public TableDto setEnableRowDrag(boolean enableRowDrag) {
		this.enableRowDrag = enableRowDrag;
		return this;
	}

	public boolean isPreOperateColumnFrozen() {
		return preOperateColumnFrozen;
	}

	public TableDto setPreOperateColumnFrozen(boolean preOperateColumnFrozen) {
		this.preOperateColumnFrozen = preOperateColumnFrozen;
		return this;
	}

	public OnTableSelectValueChanged getOnTableSelectValueChanged() {
		return onTableSelectValueChanged;
	}

	public TableDto setOnTableSelectValueChanged(OnTableSelectValueChanged onTableSelectValueChanged) {
		this.onTableSelectValueChanged = onTableSelectValueChanged;
		return this;
	}

	public OnTableHeaderClick getOnTableHeaderClick() {
		return onTableHeaderClick;
	}

	public TableDto setOnTableHeaderClick(OnTableHeaderClick onTableHeaderClick) {
		this.onTableHeaderClick = onTableHeaderClick;
		return this;
	}

	public TableRowGestureDetectorDto getTableRowGestureDetector() {
		return tableRowGestureDetector;
	}

	public TableDto setTableRowGestureDetector(TableRowGestureDetectorDto tableRowGestureDetector) {
		this.tableRowGestureDetector = tableRowGestureDetector;
		return this;
	}

	public boolean isHideHeader() {
		return hideHeader;
	}

	public TableDto setHideHeader(boolean hideHeader) {
		this.hideHeader = hideHeader;
		return this;
	}

	public OperateTableColumnDto getOperateTableColumn() {
		return operateTableColumn;
	}

	public TableDto setOperateTableColumn(OperateTableColumnDto operateTableColumn) {
		this.operateTableColumn = operateTableColumn;
		return this;
	}

	public Boolean getKeepOneRowSelectedOnSingleClick() {
		return keepOneRowSelectedOnSingleClick;
	}

	public TableDto setKeepOneRowSelectedOnSingleClick(Boolean keepOneRowSelectedOnSingleClick) {
		this.keepOneRowSelectedOnSingleClick = keepOneRowSelectedOnSingleClick;
		return this;
	}

	public Integer getCheckDelayed() {
		return checkDelayed;
	}

	public TableDto setCheckDelayed(Integer checkDelayed) {
		this.checkDelayed = checkDelayed;
		return this;
	}

	public Boolean getCheckedByListener() {
		return checkedByListener;
	}

	public TableDto setCheckedByListener(Boolean checkedByListener) {
		this.checkedByListener = checkedByListener;
		return this;
	}

	public Boolean getEnableCheckboxChange() {
		return enableCheckboxChange;
	}

	public TableDto setEnableCheckboxChange(Boolean enableCheckboxChange) {
		this.enableCheckboxChange = enableCheckboxChange;
		return this;
	}

	public Boolean getTableCellHighlightWhenSelected() {
		return tableCellHighlightWhenSelected;
	}

	public TableDto setTableCellHighlightWhenSelected(Boolean tableCellHighlightWhenSelected) {
		this.tableCellHighlightWhenSelected = tableCellHighlightWhenSelected;
		return this;
	}

	public HighlightType getHighlightType() {
		return highlightType;
	}

	public TableDto setHighlightType(HighlightType highlightType) {
		this.highlightType = highlightType;
		return this;
	}

	public Boolean getTableCellsHighlightByShift() {
		return tableCellsHighlightByShift;
	}

	public TableDto setTableCellsHighlightByShift(Boolean tableCellsHighlightByShift) {
		this.tableCellsHighlightByShift = tableCellsHighlightByShift;
		return this;
	}

	public List<MergeableDraggableDto> getRowDraggableList() {
		return rowDraggableList;
	}

	public TableDto setRowDraggableList(List<MergeableDraggableDto> rowDraggableList) {
		this.rowDraggableList = rowDraggableList;
		return this;
	}

	public Boolean getLastColumnDragFillArea() {
		return lastColumnDragFillArea;
	}

	public TableDto setLastColumnDragFillArea(Boolean lastColumnDragFillArea) {
		this.lastColumnDragFillArea = lastColumnDragFillArea;
		return this;
	}
	
	public Boolean getShowRowIndex() {
		return showRowIndex;
	}

	public TableDto setShowRowIndex(Boolean showRowIndex) {
		this.showRowIndex = showRowIndex;
		return this;
	}
	
	public Integer getCheckLimit() {
		return checkLimit;
	}

	public TableDto setCheckLimit(Integer checkLimit) {
		this.checkLimit = checkLimit;
		return this;
	}

	public String getNoDataText() {
		return noDataText;
	}

	public TableDto setNoDataText(String noDataText) {
		this.noDataText = noDataText;
		return this;
	}

	public TableHeaderGestureDetectorDto getHeaderGestureDetector() {
		return headerGestureDetector;
	}

	public TableDto setHeaderGestureDetector(TableHeaderGestureDetectorDto headerGestureDetector) {
		this.headerGestureDetector = headerGestureDetector;
		return this;
	}

	public TextEditorEditMouseEventType getTextEditorEditMouseEventType() {
		return textEditorEditMouseEventType;
	}

	public WidgetDto getRowPrefixWidget() {
		return rowPrefixWidget;
	}

	public TableDto setRowPrefixWidget(WidgetDto rowPrefixWidget) {
		this.rowPrefixWidget = rowPrefixWidget;
		return this;
	}

	public TableRowAnchorSettingsDto getRowAnchorSettings() {
		return rowAnchorSettings;
	}

	public TableDto setRowAnchorSettings(TableRowAnchorSettingsDto rowAnchorSettings) {
		this.rowAnchorSettings = rowAnchorSettings;
		return this;
	}

	public Boolean getEnableGuiSorting() {
		return enableGuiSorting;
	}

	public TableDto setEnableGuiSorting(Boolean enableGuiSorting) {
		this.enableGuiSorting = enableGuiSorting;
		return this;
	}

	public TableCheckType getCheckType() {
		return checkType;
	}

	public TableDto setCheckType(TableCheckType checkType) {
		this.checkType = checkType;
		return this;
	}

	@Override
	public TableDto setPanelGlobalKey(String panelGlobalKey) {
		return (TableDto) super.setPanelGlobalKey(panelGlobalKey);
	}

	@Override
	public TableDto setTopBar(WidgetDto topBar) {
		return (TableDto) super.setTopBar(topBar);
	}

	@Override
	public TableDto setBottomBar(WidgetDto bottomBar) {
		return (TableDto) super.setBottomBar(bottomBar);
	}

	@Override
	public TableDto setWidgetId(String widgetId) {
		return (TableDto) super.setWidgetId(widgetId);
	}

	@Override
	public TableDto setDropListener(DropListener dropListener) {
		return (TableDto) super.setDropListener(dropListener);
	}

	@Override
	public TableDto setSubscribeEvents(List<EventSubscriberDto> subscribeEvents) {
		return (TableDto) super.setSubscribeEvents(subscribeEvents);
	}

	@Override
	public TableDto addSubscribeEvent(EventSubscriberDto subscriber) {
		return (TableDto) super.addSubscribeEvent(subscriber);
	}

	@Override
	public TableDto setPreferSize(SizeDto preferSize) {
		return (TableDto) super.setPreferSize(preferSize);
	}

	@Override
	public TableDto setMinSize(SizeDto minSize) {
		return (TableDto) super.setMinSize(minSize);
	}

	@Override
	public TableDto setMaxSize(SizeDto maxSize) {
		return (TableDto) super.setMaxSize(maxSize);
	}

	@Override
	public TableDto setExpandInBox(boolean expandInBox) {
		return (TableDto) super.setExpandInBox(expandInBox);
	}

	@Override
	public TableDto setVisible(boolean visible) {
		return (TableDto) super.setVisible(visible);
	}

	@Override
	public TableDto setDraggable(DraggableDto draggableData) {
		return (TableDto) super.setDraggable(draggableData);
	}

	@Override
	public TableDto setDecoration(DecorationDto decoration) {
		return (TableDto) super.setDecoration(decoration);
	}
}
