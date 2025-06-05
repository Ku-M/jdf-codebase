package fe.cmn.table.ability;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.leavay.ms.tool.CmnUtil;


import fe.cmn.panel.PanelContext;
import fe.cmn.table.TableRowDto;

public class QueryTableRows extends TableCallback<List<TableRowDto>> {

	private static final long serialVersionUID = 5286051239335381911L;

	List<String> rowIds;

	// 是否获取处于勾选选中状态行
	Boolean isSelected;
	
	// TableRowDto的mapFields列名过滤
	List<String> columns;

	public QueryTableRows() {

	}

	public QueryTableRows(List<String> rowIds) {
		this.setRowIds(rowIds);
	}

	public QueryTableRows(Boolean isSelected) {
		this.setIsSelected(isSelected);
	}

	public QueryTableRows(Boolean isSelected, List<String> rowIds) {
		this.setRowIds(rowIds);
		this.setIsSelected(isSelected);
	}

	public QueryTableRows(Boolean isSelected, String... rowIds) {
		this.setRowIds(rowIds);
		this.setIsSelected(isSelected);
	}

	public List<String> getRowIds() {
		return rowIds;
	}

	public QueryTableRows setRowIds(List<String> rowIds) {
		this.rowIds = rowIds;
		return this;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public QueryTableRows setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
		return this;
	}

	public List<String> getColumns() {
		return columns;
	}

	public QueryTableRows setColumns(List<String> columns) {
		this.columns = columns;
		return this;
	}
	
	public QueryTableRows setColumns(String... columns) {
		this.columns = Arrays.stream(columns).collect(Collectors.toList());
		return this;
	}

	public QueryTableRows setRowIds(String... rowIds) {
		this.rowIds = Arrays.stream(rowIds).collect(Collectors.toList());
		return this;
	}

	public static List<TableRowDto> query(PanelContext context, List<String> rowIds, String... columns) throws Exception {
		QueryTableRows callback = new QueryTableRows(false, rowIds).setColumns(columns);
		return (List<TableRowDto>) context.callback(callback);
	}

	public static List<TableRowDto> query(PanelContext context, String... rowIds) throws Exception {
		QueryTableRows callback = new QueryTableRows(false, rowIds);
		return (List<TableRowDto>) context.callback(callback);
	}

	public static TableRowDto queryOne(PanelContext context, String rowId, String... columns) throws Exception {
		QueryTableRows callback = new QueryTableRows(false, rowId).setColumns(columns);;
		List<TableRowDto> lst = (List<TableRowDto>) context.callback(callback);
		return CmnUtil.isObjectEmpty(lst)?null:lst.get(0);
	}
	
	// 查询所有行
	public static List<TableRowDto> queryAll(PanelContext context, String... columns) throws Exception {
		QueryTableRows callback = new QueryTableRows(false).setColumns(columns);
		return (List<TableRowDto>) context.callback(callback);
	}

	// 查询所有勾选行
	public static List<TableRowDto> querySelected(PanelContext context, String... columns) throws Exception {
		QueryTableRows callback = new QueryTableRows(true).setColumns(columns);
		return (List<TableRowDto>) context.callback(callback);
	}

}
