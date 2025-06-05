package fe.util.component.dto;

import fe.cmn.data.FePojo;

import java.io.Serializable;

public class TableSetting extends FePojo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 3743075070499799097L;

//	String showDetailCol = "true"; //显示详情按钮
//	String operateShow = "true";//显示操作列
//	String tableIndexType = "true";//显示选择列
//	String showAdvanceScreen = "false";//高级搜索
//	String screenType = "summary";//normal,summary;
//	String created;//表格初始化完成事件
//	String tableRowClick;//表格行点击事件
//	String formShowChange;//表格表单打开触发事件
//	String etableFormShowAfter;//表格表单打开触发事件
//	Boolean paginationable;//显示分页
//	String tableCellStyle;//表格单元格样式
//	String tableRowStyle;//表格行样式
//	String headerCellStyle;//表头单元格样式
//	String updated;//表格数据改变事件
//	String deleteBefore;//表格行删除事件
//	String setTableListComponent;//移动端列表自定义显示
//	String popoverWidth;//选择表格弹框宽度
//	String popoverHeight;//选择表格弹框高度
//	String showNormalFilterReset = "true";
//	String refreshAfterTableRowChanged = "false";

	/**
	 * 每页显示行数
	 */
	Integer pageSize;
	/**
	 * 子表最大高度
	 */
	Integer maxEmbedHeight;
	/**
	 * 显示勾选框
	 */
	boolean showCheckBox = true;
	/**
	 * 是否允许刷新
	 */
	boolean isAllowRefresh = false;
	/**
	 * 是否允许新建
	 */
	boolean isAllowCreate = true;
	/**
	 * 是否允许复制行
	 */
	boolean isAllowCopyRow = false;
	/**
	 * 是否允许查看行详情
	 */
	boolean isAllowRowClick = true;
	/**
	 * 是否允许更新
	 */
	boolean isAllowUpdate = true;
	/**
	 * 是否允许删除
	 */
	boolean isAllowDelete = true;
	/**
	 * 是否允许列拖拽
	 */
	boolean isEnableRowDrag = true;
	/**
	 * 是否显示操作列
	 */
	boolean isEnableRowOperate = true;
	/**
	 * 行操作按钮自动计算宽度
	 */
	boolean isRowOperateAutoWidth = false;
	/**
	 * 行操作按钮固定宽度设置，如果设置了自动计算宽度
	 */
	Integer rowOperateFixWidth;
	/**
	 * 表格操作是否显示结果提示
	 */
	boolean isOpShowPopToast = true;


	public Integer getPageSize() {
		return pageSize;
	}

	public TableSetting setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	public Integer getMaxEmbedHeight() {
		return maxEmbedHeight;
	}

	public TableSetting setMaxEmbedHeight(Integer maxEmbedHeight) {
		this.maxEmbedHeight = maxEmbedHeight;
		return this;
	}

	public boolean isShowCheckBox() {
		return showCheckBox;
	}

	public TableSetting setShowCheckBox(boolean showCheckBox) {
		this.showCheckBox = showCheckBox;
		return this;
	}

	public boolean isAllowRefresh() {
		return isAllowRefresh;
	}

	public TableSetting setIsAllowRefresh(boolean allowRefresh) {
		isAllowRefresh = allowRefresh;
		return this;
	}

	public boolean isAllowCreate() {
		return isAllowCreate;
	}

	public TableSetting setIsAllowCreate(boolean allowCreate) {
		isAllowCreate = allowCreate;
		return this;
	}
	
	public boolean isAllowCopyRow() {
		return isAllowCopyRow;
	}
	public TableSetting setAllowCopyRow(boolean isAllowCopyRow) {
		this.isAllowCopyRow = isAllowCopyRow;
		return this;
	}

	public boolean isAllowRowClick() {
		return isAllowRowClick;
	}

	public TableSetting setIsAllowRowClick(boolean allowRowClick) {
		isAllowRowClick = allowRowClick;
		return this;
	}

	public boolean isAllowUpdate() {
		return isAllowUpdate;
	}

	public TableSetting setIsAllowUpdate(boolean allowUpdate) {
		isAllowUpdate = allowUpdate;
		return this;
	}

	public boolean isAllowDelete() {
		return isAllowDelete;
	}

	public TableSetting setIsAllowDelete(boolean allowDelete) {
		isAllowDelete = allowDelete;
		return this;
	}

	public boolean isEnableRowDrag() {
		return isEnableRowDrag;
	}

	public TableSetting setIsEnableRowDrag(boolean enableRowDrag) {
		isEnableRowDrag = enableRowDrag;
		return this;
	}

	public boolean isEnableRowOperate() {
		return isEnableRowOperate;
	}

	public TableSetting setIsEnableRowOperate(boolean enableRowOperate) {
		isEnableRowOperate = enableRowOperate;
		return this;
	}

	public boolean isRowOperateAutoWidth() {
		return isRowOperateAutoWidth;
	}

	public TableSetting setIsRowOperateAutoWidth(boolean rowOperateAutoWidth) {
		isRowOperateAutoWidth = rowOperateAutoWidth;
		return this;
	}

	public Integer getRowOperateFixWidth() {
		return rowOperateFixWidth;
	}

	public TableSetting setRowOperateFixWidth(Integer rowOperateFixWidth) {
		this.rowOperateFixWidth = rowOperateFixWidth;
		return this;
	}

	public boolean isOpShowPopToast() {
		return isOpShowPopToast;
	}

	public TableSetting setIsOpShowPopToast(boolean opShowPopToast) {
		isOpShowPopToast = opShowPopToast;
		return this;
	}
}