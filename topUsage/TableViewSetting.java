package gpf.dc.basic.param.view.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.kwaidoo.ms.tool.CmnUtil;
import com.leavay.common.util.ToolUtilities;
import com.leavay.common.util.javac.ClassFactory;
import com.leavay.dfc.gui.LvUtil;

import bap.cells.Cells;
import cell.CellIntf;
import cn.hutool.core.collection.CollUtil;
import fe.cmn.table.ColumnFrozenType;
import fe.cmn.table.ColumnWidthAutoFitType;
import fe.cmn.table.HighlightType;
import fe.cmn.table.TableCheckType;
import fe.cmn.table.TextEditorEditMouseEventType;
import fe.util.component.dto.TableSetting;
import fe.util.component.param.TableParam;
import gpf.dc.basic.fe.intf.TableRowDtoInterceptor;
import gpf.exception.VerifyException;

public class TableViewSetting extends TableSetting{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8024590545664185092L;

	public final static String FilterMode_Normal = "nomral";
	public final static String FilterMode_Summary = "summary";
	public final static String FilterMode_NormalAndSummary = "normalAndSummary";
	
	/**
	 * 显示隐藏列设置按钮
	 */
	Boolean showHiddenColBtn;
	/**
	 * 高级搜索
	 */
	Boolean showAdvanceFilter;
	/**
	 * 搜索模式
	 */
	String filterMode = "";
	/**
	 * 模糊搜索时的属性名称，多个用“,”分隔，为空时默认搜索所有属性
	 */
	String summaryFilterCols;
	/**
	 * 普通搜索栏显示重置按钮
	 */
	boolean showNormalFilterReset = true;
	/**
	 * 显示分页
	 */
	boolean paginationable = true;
	/**
	 * 分页条数选项列表，用,分割，如：10,20,50,100
	 */
	String pageSizes;
	/**
	 * 表格行数据变更后是否刷新表格
	 */
	boolean refreshAfterTableRowChanged = true;
	/**
	 * 新增行的插入位置，顶部，底部
	 */
	String addRowsPosition;
	public final static String ADD_ROWS_TOP = "top";
	public final static String ADD_ROWS_BOTTOM = "bottom";

	/**
	 * 行双击打开
	 */
	boolean isDoubleClickOpen = false;
	/**
	 * 显示行编辑按钮
	 */
	boolean isShowRowDetailBtn = false;
	/**
	 * 查看详情时重新请求数据
	 */
	boolean isReGetDataOnRowClick = true;
	/**
	 * 列自适应宽度类型（仅在autoFit为true时适用）
	 */
	String autoFitType;
	/**
	 * 是否启用默认数据权限
	 */
	boolean enableDefaultDataPrivilege = true;
	/**
	 * 表格样式
	 */
	String tableStyle;
	/**
	 * 允许导出数据
	 */
	boolean isAllowExport = false;
	/**
	 * 允许导入数据
	 */
	boolean isAllowImport = false;
	/**
	 * 允许导入时选择多个文件
	 */
	boolean isAllowMultipleImport = false;
	/**
	 * 触发监听时默认加载loading
	 */
	Boolean showLoadingOnListener;
	/**
	 * 允许导入的文件类型
	 */
	String allowImportFileType;
	
//	/**
//	 * 启用嵌套模型独立事务，只在独立表格（非表单内嵌套表格）时生效
//	 */
//	boolean enableNestingModelTransaction = false;
//	/**
//	 * 单元格编辑器类型
//	 */
//	String tableCellEditorType = TableCellEditorType.Label.name();
	
	/**
	 * 行操作列位置
	 */
	String rowOperateColumnFrozenType = null;
	
	/**
	 * 隐藏表头
	 */
	boolean hideHeader;
	/**
	 * 是否允许界面修改勾选值，默认允许
	 */
	Boolean enableCheckboxChange = true;
	/**
	 * 列拖拽
	 */
	Boolean enableColumnDrag = false;
	
	/**
	 * 前置操作列（行下标 + 勾选 + 拖拽）是否悬浮固定
	 */
	boolean preOperateColumnFrozen = true;
	/**
	 * 勾选类型控制（将替代showCheckbox）
	 */
	String checkType;
	/**
	 * 单击选择行时是否保持只有一行可以选中，默认为true
	 */
	Boolean keepOneRowSelectedOnSingleClick = true;
	/**
	 * 鼠标单击或双击进行编辑
	 */
	String textEditorEditMouseEventType = TextEditorEditMouseEventType.click.name();
	/**
	 * 鼠标点击是默认高亮行
	 */
	String highlightType = HighlightType.row.name();
	/**
	 * 表格单元格高亮通过shift多选
	 */
	Boolean tableCellsHighlightByShift;
	/**
	 * 表格最后一列拖拽后如果出现空白区域，是否由其他列填充
	 */
	Boolean lastColumnDragFillArea;
	/**
	 * 限制勾选数量
	 */
	Integer checkLimit;
	/**
	 * 是否显示行下标列
	 */
	Boolean showRowIndex;
	/**
	 * 是否开启界面列排序功能。（默认为false，可被列设置（TabelCoumnDto.enableGuiSorting）覆盖）
	 * 
	 */
	Boolean enableGuiSorting;
	/**
	 * 表格行数据拦截器，在表单数据转成TableRowDto的前后提供干预操作
	 */
	String tableRowDtoInterceptor;


	public boolean isHideHeader() {
		return hideHeader;
	}

	public String getCheckType() {
		return checkType;
	}
	
	public TableCheckType getCheckTypeEnum() {
		for(TableCheckType item : TableCheckType.values()) {
			if(CmnUtil.isStringEqual(checkType, item.name()))
				return item;
		}
		if(isShowCheckBox())
			return TableCheckType.checkbox;
		return TableCheckType.none;
	}

	public Boolean getKeepOneRowSelectedOnSingleClick() {
		return keepOneRowSelectedOnSingleClick;
	}

	public String getTextEditorEditMouseEventType() {
		return textEditorEditMouseEventType;
	}
	
	public TextEditorEditMouseEventType getTextEditorEditMouseEventTypeEnum() {
		for(TextEditorEditMouseEventType item : TextEditorEditMouseEventType.values()) {
			if(CmnUtil.isStringEqual(textEditorEditMouseEventType, item.name()))
				return item;
		}
		return TextEditorEditMouseEventType.click;
	}

	public String getHighlightType() {
		return highlightType;
	}
	
	public HighlightType getHighlightTypeEnum() {
		for(HighlightType item : HighlightType.values()) {
			if(CmnUtil.isStringEqual(highlightType, item.name()))
				return item;
		}
		return HighlightType.row;
	}

	public Boolean getTableCellsHighlightByShift() {
		return tableCellsHighlightByShift;
	}

	public Boolean getLastColumnDragFillArea() {
		return lastColumnDragFillArea;
	}

	public Integer getCheckLimit() {
		return checkLimit;
	}

	public Boolean getShowRowIndex() {
		return showRowIndex;
	}

	public Boolean getEnableGuiSorting() {
		return enableGuiSorting;
	}
	
	public Boolean getEnableCheckboxChange() {
		return enableCheckboxChange;
	}
	public TableViewSetting setEnableCheckboxChange(Boolean enableCheckboxChange) {
		this.enableCheckboxChange = enableCheckboxChange;
		return this;
	}
	public Boolean getEnableColumnDrag() {
		return enableColumnDrag;
	}
	public TableViewSetting setEnableColumnDrag(Boolean enableColumnDrag) {
		this.enableColumnDrag = enableColumnDrag;
		return this;
	}

	public TableViewSetting setHideHeader(boolean hideHeader) {
		this.hideHeader = hideHeader;
		return this;
	}

	public TableViewSetting setCheckType(String checkType) {
		this.checkType = checkType;
		return this;
	}

	public TableViewSetting setKeepOneRowSelectedOnSingleClick(Boolean keepOneRowSelectedOnSingleClick) {
		this.keepOneRowSelectedOnSingleClick = keepOneRowSelectedOnSingleClick;
		return this;
	}

	public TableViewSetting setTextEditorEditMouseEventType(String textEditorEditMouseEventType) {
		this.textEditorEditMouseEventType = textEditorEditMouseEventType;
		return this;
	}

	public TableViewSetting setHighlightType(String highlightType) {
		this.highlightType = highlightType;
		return this;
	}

	public TableViewSetting setTableCellsHighlightByShift(Boolean tableCellsHighlightByShift) {
		this.tableCellsHighlightByShift = tableCellsHighlightByShift;
		return this;
	}

	public TableViewSetting setLastColumnDragFillArea(Boolean lastColumnDragFillArea) {
		this.lastColumnDragFillArea = lastColumnDragFillArea;
		return this;
	}

	public TableViewSetting setCheckLimit(Integer checkLimit) {
		this.checkLimit = checkLimit;
		return this;
	}

	public TableViewSetting setShowRowIndex(Boolean showRowIndex) {
		this.showRowIndex = showRowIndex;
		return this;
	}

	public TableViewSetting setEnableGuiSorting(Boolean enableGuiSorting) {
		this.enableGuiSorting = enableGuiSorting;
		return this;
	}

	public TableViewSetting() {
		super();
		setIsAllowRefresh(true);
		setIsOpShowPopToast(false);
	}
	
	public boolean isNormalFilter() {
		return CmnUtil.isStringEqual(FilterMode_Normal, filterMode);
	}
	
	public boolean isSummaryFilter() {
		return CmnUtil.isStringEqual(FilterMode_Summary, filterMode);
	}
	
	public boolean isNormalAndSummaryFilter() {
		return CmnUtil.isStringEqual(FilterMode_NormalAndSummary, filterMode);
	}
	
	public String getSummaryFilterCols() {
		return summaryFilterCols;
	}
	public List<String> getSummaryFilterColList() {
		if(CmnUtil.isStringEmpty(summaryFilterCols))
			return new ArrayList<>();
		return Arrays.asList(summaryFilterCols.split(","));
	}
	public TableViewSetting setSummaryFilterCols(String summaryFilterCols) {
		this.summaryFilterCols = summaryFilterCols;
		return this;
	}
	
	public Boolean getShowHiddenColBtn() {
		return showHiddenColBtn;
	}
	public boolean isShowHiddenColBtn() {
		return showHiddenColBtn != null && showHiddenColBtn;
	}

	public TableViewSetting setShowHiddenColBtn(boolean showHiddenColBtn) {
		this.showHiddenColBtn = showHiddenColBtn;
		return this;
	}

	public Boolean getShowAdvanceFilter() {
		return showAdvanceFilter;
	}
	public boolean isShowAdvanceFilter() {
		return showAdvanceFilter != null && showAdvanceFilter;
	}

	public TableViewSetting setShowAdvanceFilter(boolean showAdvanceFilter) {
		this.showAdvanceFilter = showAdvanceFilter;
		return this;
	}

	public String getFilterMode() {
		return filterMode;
	}

	public TableViewSetting setFilterMode(String filterMode) {
		this.filterMode = filterMode;
		return this;
	}
	
	public boolean isShowNormalFilterReset() {
		return showNormalFilterReset;
	}

	public TableViewSetting setShowNormalFilterReset(boolean showNormalFilterReset) {
		this.showNormalFilterReset = showNormalFilterReset;
		return this;
	}

	public boolean isDoubleClickOpen() {
		return isDoubleClickOpen;
	}

	public TableViewSetting setDoubleClickOpen(boolean doubleClickOpen) {
		isDoubleClickOpen = doubleClickOpen;
		return this;
	}
	
	public boolean isShowRowDetailBtn() {
		return isShowRowDetailBtn;
	}
	
	public TableViewSetting setShowRowDetailBtn(boolean isShowRowDetailBtn) {
		this.isShowRowDetailBtn = isShowRowDetailBtn;
		return this;
	}

	public boolean isPaginationable() {
		return paginationable;
	}

	public TableViewSetting setPaginationable(boolean paginationable) {
		this.paginationable = paginationable;
		return this;
	}
	
	public String getPageSizes() {
		return pageSizes;
	}
	public TableViewSetting setPageSizes(String pageSizes) {
		this.pageSizes = pageSizes;
		return this;
	}
	
	public List<Integer> getPageSizeList() {
		if(CmnUtil.isStringEmpty(pageSizes))
			return TableParam.defaultPageSizes;
		List<Integer> pageSizeList = new ArrayList<>();
		String[] pageSizeArr = pageSizes.split(",");
		try {
			for(String pageSize : pageSizeArr) {
				pageSizeList.add(CmnUtil.getInteger(pageSize));
			}
		} catch (Exception e) {
			throw new VerifyException("Invalid pageSizes:" + pageSizes);
		}
		return pageSizeList;
	}

	public boolean isRefreshAfterTableRowChanged() {
		return refreshAfterTableRowChanged;
	}

	public TableViewSetting setRefreshAfterTableRowChanged(boolean refreshAfterTableRowChanged) {
		this.refreshAfterTableRowChanged = refreshAfterTableRowChanged;
		return this;
	}
	boolean lazyRender = false;
	public boolean isLazyRender() {
		return lazyRender;
	}
	public TableViewSetting setLazyRender(boolean lazyRender) {
		this.lazyRender = lazyRender;
		return this;
	}
	public String getAddRowsPosition() {
		return addRowsPosition;
	}
	public TableViewSetting setAddRowsPosition(String addRowsPosition) {
		this.addRowsPosition = addRowsPosition;
		return this;
	}
	public boolean isAddRowsAtTop() {
		return CmnUtil.isStringEqual(addRowsPosition, ADD_ROWS_TOP);
	}
	public boolean isAddRowsAtBottom() {
		return CmnUtil.isStringEqual(addRowsPosition, ADD_ROWS_BOTTOM);
	}
	public boolean isReGetDataOnRowClick() {
		return isReGetDataOnRowClick;
	}
	public TableViewSetting setReGetDataOnRowClick(boolean isReGetDataOnRowClick) {
		this.isReGetDataOnRowClick = isReGetDataOnRowClick;
		return this;
	}
	
	/**
	 * 查看详情嵌套数据是否在表单加载后再查询
	 */
	boolean isLazyQueryCompoundField = false;

	public boolean isLazyQueryCompoundField() {
		return isLazyQueryCompoundField;
	}
	public TableViewSetting setLazyQueryCompoundField(boolean isLazyQueryCompoundField) {
		this.isLazyQueryCompoundField = isLazyQueryCompoundField;
		return this;
	}
	
	public String getAutoFitType() {
		return autoFitType;
	}
	public TableViewSetting setAutoFitType(String autoFitType) {
		this.autoFitType = autoFitType;
		return this;
	}
	public ColumnWidthAutoFitType getColumnWidthAutoFitType() {
		for(ColumnWidthAutoFitType value : ColumnWidthAutoFitType.values()) {
			if(CmnUtil.isStringEqual(value.name(), autoFitType))
				return value;
		}
		return ColumnWidthAutoFitType.header;
	}
	public boolean isEnableDefaultDataPrivilege() {
		return enableDefaultDataPrivilege;
	}
	public TableViewSetting setEnableDefaultDataPrivilege(boolean enableDefaultDataPrivilege) {
		this.enableDefaultDataPrivilege = enableDefaultDataPrivilege;
		return this;
	}
	
	public String getTableStyle() {
		return tableStyle;
	}
	public TableViewSetting setTableStyle(String tableStyle) {
		this.tableStyle = tableStyle;
		return this;
	}
	/**
	 * 列筛选隐藏表头
	 */
	boolean columnFilterHideHeader = false;
	public boolean isColumnFilterHideHeader() {
		return columnFilterHideHeader;
	}
	public TableViewSetting setColumnFilterHideHeader(boolean columnFilterHideHeader) {
		this.columnFilterHideHeader = columnFilterHideHeader;
		return this;
	}
	/**
	 * 列筛选表格样式
	 */
	String columnFilterTableStyle;
	public String getColumnFilterTableStyle() {
		return columnFilterTableStyle;
	}
	public TableViewSetting setColumnFilterTableStyle(String columnFilterTableStyle) {
		this.columnFilterTableStyle = columnFilterTableStyle;
		return this;
	}
	
	public boolean isAllowExport() {
		return isAllowExport;
	}
	
	public TableViewSetting setAllowExport(boolean isAllowExport) {
		this.isAllowExport = isAllowExport;
		return this;
	}
	public boolean isAllowImport() {
		return isAllowImport;
	}
	public TableViewSetting setAllowImport(boolean isAllowImport) {
		this.isAllowImport = isAllowImport;
		return this;
	}
	
	public String getAllowImportFileType() {
		return allowImportFileType;
	}
	public TableViewSetting setAllowImportFileType(String allowImportFileType) {
		this.allowImportFileType = allowImportFileType;
		return this;
	}
	public boolean isAllowMultipleImport() {
		return isAllowMultipleImport;
	}
	public TableViewSetting setAllowMultipleImport(boolean isAllowMultipleImport) {
		this.isAllowMultipleImport = isAllowMultipleImport;
		return this;
	}
	public List<String> getAllowImportFileTypeList(){
		if(CmnUtil.isStringEmpty(allowImportFileType))
			return null;
		String[] array = allowImportFileType.split(",");
		return CollUtil.newArrayList(array);
	}
	
	public boolean isShowLoadingOnListener() {
		return showLoadingOnListener != null && showLoadingOnListener;
	}
	public TableViewSetting setShowLoadingOnListener(boolean showLoadingOnListener) {
		this.showLoadingOnListener = showLoadingOnListener;
		return this;
	}
	
//	public boolean isEnableNestingModelTransaction() {
//		return enableNestingModelTransaction;
//	}
//	
//	public TableViewSetting setEnableNestingModelTransaction(boolean enableNestingModelTransaction) {
//		this.enableNestingModelTransaction = enableNestingModelTransaction;
//		return this;
//	}
	
	public String getRowOperateColumnFrozenType() {
		return rowOperateColumnFrozenType;
	}
	public ColumnFrozenType getRowOperateColumnFrozenTypeEnum() {
		for(ColumnFrozenType item : ColumnFrozenType.values()) {
			if(CmnUtil.isStringEqual(rowOperateColumnFrozenType, item.name()))
				return item;
		}
		return ColumnFrozenType.right;
	}
	public TableViewSetting setRowOperateColumnFrozenType(ColumnFrozenType rowOperateColumnFrozenType) {
		if(rowOperateColumnFrozenType == null) {
			this.rowOperateColumnFrozenType = ColumnFrozenType.right.name();
		}else {
			this.rowOperateColumnFrozenType = rowOperateColumnFrozenType.name();
		}
		return this;
	}
	
//	public String getTableCellEditorType() {
//		return tableCellEditorType;
//	}
//	
//	public TableCellEditorType getTableCellEditorTypeEnum() {
//		TableCellEditorType type = EnumUtil.getEnumByName(TableCellEditorType.class,tableCellEditorType);
//		return type == null ? TableCellEditorType.Label : type;
//	}
//	public TableViewSetting setTableCellEditorType(String tableCellEditorType) {
//		this.tableCellEditorType = tableCellEditorType;
//		return this;
//	}
//	
	public String getTableRowDtoInterceptor() {
		return tableRowDtoInterceptor;
	}
	public TableViewSetting setTableRowDtoInterceptor(String tableRowDtoInterceptor) {
		this.tableRowDtoInterceptor = tableRowDtoInterceptor;
		return this;
	}
	
	public TableRowDtoInterceptor getTableRowDtoInterceptorInst() {
		if(CmnUtil.isStringEmpty(tableRowDtoInterceptor))
			return null;
		try {
			Class clazz = ClassFactory.loadClass(tableRowDtoInterceptor);
			if(CellIntf.class.isAssignableFrom(clazz)) {
				return (TableRowDtoInterceptor) Cells.get(clazz);
			}else {
				return (TableRowDtoInterceptor) clazz.newInstance();
			}
		}catch (Exception e) {
			LvUtil.trace(ToolUtilities.getExceptionMessage(e));
		}
		return null;
	}
	
	
	public static TableViewSetting newTableViewSetting() {
		TableViewSetting setting = new TableViewSetting();
		setting.setFilterMode(FilterMode_Summary);
		return setting;
	}
	
}
