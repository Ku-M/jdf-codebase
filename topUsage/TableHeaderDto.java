package fe.cmn.table;

import java.util.List;

import cson.core.CsonPojo;
import com.leavay.common.util.ToolUtilities;

/**
 * 后端构建的表头
 * 
 *
 */
public class TableHeaderDto extends CsonPojo {
	private static final long serialVersionUID = 423659359840213511L;

	List<TableColumnDto> columns;

	// 分组
	List<TableColumnGroupDto> columnGroups;

	// 事件过滤器（过滤指定列）
	TableHeaderGestureFilterDto gestureFilter;
	
	/**
	 * 排序设置（指定排序列、排序方式）。
	 */
	TableSortDto sorter;

	public TableHeaderDto() {
	}

	public TableHeaderDto(List<TableColumnDto> columns) {
		this.columns = columns;
	}

	public TableHeaderDto(TableColumnDto... columns) {
		this.columns = ToolUtilities.array2List(columns);
	}

	public TableHeaderDto(TableColumnGroupDto... columnGroups) {
		this.columnGroups = ToolUtilities.array2List(columnGroups);
	}

	public TableHeaderDto(List<TableColumnDto> columns, List<TableColumnGroupDto> columnGroups) {
		this.columns = columns;
		this.columnGroups = columnGroups;
	}

	public List<TableColumnDto> getColumns() {
		return columns;
	}

	public TableHeaderDto setColumns(List<TableColumnDto> columns) {
		this.columns = columns;
		return this;
	}

	public TableHeaderDto setColumns(TableColumnDto... columns) {
		this.columns = ToolUtilities.array2List(columns);
		return this;
	}

	public List<TableColumnGroupDto> getColumnGroups() {
		return columnGroups;
	}

	public TableHeaderDto setColumnGroups(List<TableColumnGroupDto> columnGroups) {
		this.columnGroups = columnGroups;
		return this;
	}

	public TableHeaderDto setColumnGroups(TableColumnGroupDto... columnGroups) {
		this.columnGroups = ToolUtilities.array2List(columnGroups);
		return this;
	}

	public TableHeaderGestureFilterDto getGestureFilter() {
		return gestureFilter;
	}

	public void setGestureFilter(TableHeaderGestureFilterDto gestureFilter) {
		this.gestureFilter = gestureFilter;
	}

	public TableSortDto getSorter() {
		return sorter;
	}

	public TableHeaderDto setSorter(TableSortDto sorter) {
		this.sorter = sorter;
		return this;
	}
}
