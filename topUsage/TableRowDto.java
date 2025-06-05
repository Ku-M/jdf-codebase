package fe.cmn.table;

import java.util.HashMap;
import java.util.Map;

import cson.core.CsonPojo;
import fe.cmn.table.decoration.TableRowDecorationDto;
import fe.cmn.widget.DraggableDto;
import flutter.coder.annt.DefaultGetter;
import flutter.coder.annt.FlutterCode;
import flutter.coder.annt.NullSafe;

@FlutterCode("@override\n\tbool compare(dynamic other) {if(other is TableRowDto){return this.rowId == other.rowId;}return false;}")
public class TableRowDto extends SelectedTableRowDto
{
    private static final long serialVersionUID = -7967175574355001966L;

    Map<String, TableCellDto> mapFields;
    
    // 自定义按钮操作列上的按钮显示控制（不设置默认全部显示），key值为按钮widgetId
    Map<String, Boolean> operateColumnButtonsVisible;
    
    DraggableDto draggable;
    
    TableRowDecorationDto decoration;
    
    /**
     * 是否允许显示行前面组件（通过TableDto.rowPrefixWidget设置）
     */
    @DefaultGetter("true")
    Boolean enablePrefixWidget;

    public TableRowDto() {
    }

    public TableRowDto(String rowId) {
        this.rowId = rowId;
    }

    @Override
    public TableRowDto setRowId(String rowId)
    {
        this.rowId = rowId;
        return this;
    }

    public Map<String, TableCellDto> getMapFields()
    {
        return mapFields;
    }

    public TableRowDto setMapFields(Map<String, TableCellDto> mapFields)
    {
        this.mapFields = mapFields;
        return this;
    }
    
    public void putValue(String field, TableCellDto value)
    {
        if (mapFields == null)
            mapFields = new HashMap<String, TableCellDto>();
        mapFields.put(field, value);
    }

    public Map<String, Boolean> getOperateColumnButtonsVisible() {
		return operateColumnButtonsVisible;
	}

	public TableRowDto setOperateColumnButtonsVisible(Map<String, Boolean> operateColumnButtonsVisible) {
		this.operateColumnButtonsVisible = operateColumnButtonsVisible;
		return this;
	}

	public TableRowDecorationDto getDecoration() {
		return decoration;
	}

	public TableRowDto setDecoration(TableRowDecorationDto decoration) {
		this.decoration = decoration;
		return this;
	}

	public DraggableDto getDraggable() {
		return draggable;
	}

	public TableRowDto setDraggable(DraggableDto draggable) {
		this.draggable = draggable;
		return this;
	}

	public Boolean getEnablePrefixWidget() {
		return enablePrefixWidget;
	}

	public TableRowDto setEnablePrefixWidget(Boolean enablePrefixWidget) {
		this.enablePrefixWidget = enablePrefixWidget;
		return this;
	}

	@Override
	public TableRowDto setSelected(boolean selected) {
		this.selected = selected;
		return this;
	}
}
