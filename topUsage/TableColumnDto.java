package fe.cmn.table;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import fe.cmn.data.FieldMeta;
import fe.cmn.table.decoration.TableColumnDecorationDto;
import fe.cmn.widget.ToolTipDto;
import fe.cmn.widget.WidgetDto;
import flutter.coder.annt.DefaultGetter;

/**
 * 增加表头专用的一些配置信息
 */
public class TableColumnDto extends FieldMeta
{
    private static final long serialVersionUID = -5257393534076457007L;
    
    public static final String rowIdVariable = "${rowId}";
    
    public static final String columnNameVariable = "${columnName}";
    
    double minWidth;

    double maxWidth;

    double fixWidth;
    
    // 是否隐藏
    Boolean hide = false;
    
    Boolean enableColumnDrag;
    
    ColumnFrozenType columnFrozenType;

	@Deprecated
    ColumnDragIconPositionType columnDragIconPositionType;
    
	TableColumnGestureDetectorDto gestureDetector;
	
	/**
	 * 是否在表头显示一个布尔值总控编辑器，显示在buttons之前。
	 * 1. 具体类型同此列单元格默认编辑器（editor字段）一致，且必须为布尔值编辑器（BooleanEditorDto类型）才会生效。
	 * 2. 值由整列的所有同类型布尔值编辑器单元格决定，同时也可切换控制整列的所有同类型布尔值编辑器单元格。
	 */
	@DefaultGetter("false")
	Boolean enableToggleAll;
	
    List<WidgetDto> buttons;
    
    TableColumnDecorationDto decoration;
    
    // toolTip提示设置
    ToolTipDto toolTip;
    
    /*
     * 此列的宽度自适应控制
     * 
     * 仅在以下情况生效
     * 1.表格开启列自适应（TabelDto.autoFit为true）
     * 2.此列无设置固定宽度（fixWidth）
     * 
     * 若此字段为空，则默认取表格设置（TableDto.autoFitType）
     */
    ColumnWidthAutoFitType autoFitType;
    
    /**
     * editor的widgetId模板。
     * 当单元格eidtor的widgetId为空时，将会被赋予此值。
     * 
     * 可使用rowId及columnName变量，会被替换为目标相对应值。
     * 例如：TableColumnDto.rowIdVariable + "-" + TableColumnDto.columnNameVariable。
     * (即"${rowId}-${columnName}")。
     */
    String editorWidgetIdTemplate;
    
    /**
     * 是否开启界面排序功能（若为null，则默认取TableDto.enableGuiSorting）。
     */
 	Boolean enableGuiSorting;

    public TableColumnDto() {
    }

    public TableColumnDto(String name,String label) {
        setName(name);
        setLabel(label);
    }

    public double getMinWidth()
    {
        return minWidth;
    }

    public TableColumnDto setMinWidth(double minWidth)
    {
        this.minWidth = minWidth;
        return this;
    }

    public double getMaxWidth()
    {
        return maxWidth;
    }

    public TableColumnDto setMaxWidth(double maxWidth)
    {
        this.maxWidth = maxWidth;
        return this;
    }

    public double getFixWidth()
    {
        return fixWidth;
    }

    public TableColumnDto setFixWidth(double fixWidth)
    {
        this.fixWidth = fixWidth;
        return this;
    }
    
    public Boolean getHide() {
		return hide;
	}

	public TableColumnDto setHide(Boolean hide) {
		this.hide = hide;
		return this;
	}

	public Boolean getEnableColumnDrag() {
		return enableColumnDrag;
	}

	public TableColumnDto setEnableColumnDrag(Boolean enableColumnDrag) {
		this.enableColumnDrag = enableColumnDrag;
		return this;
	}

	public ColumnFrozenType getColumnFrozenType() {
		return columnFrozenType;
	}

	public TableColumnDto setColumnFrozenType(ColumnFrozenType columnFrozenType) {
		this.columnFrozenType = columnFrozenType;
		return this;
	}

	public ColumnDragIconPositionType getColumnDragIconPositionType() {
		return columnDragIconPositionType;
	}

	public TableColumnDto setColumnDragIconPositionType(ColumnDragIconPositionType columnDragIconPositionType) {
		this.columnDragIconPositionType = columnDragIconPositionType;
		return this;
	}

	public List<WidgetDto> getButtons() {
		return buttons;
	}

	public TableColumnDto setButtons(List<WidgetDto> buttons) {
		this.buttons = buttons;
		return this;
	}
	
	public TableColumnDto setButtons(WidgetDto... buttons) {
		this.buttons = Arrays.stream(buttons).collect(Collectors.toList());
		return this;
	}

	public TableColumnDecorationDto getDecoration() {
		return decoration;
	}

	public TableColumnDto setDecoration(TableColumnDecorationDto decoration) {
		this.decoration = decoration;
		return this;
	}

	public TableColumnGestureDetectorDto getGestureDetector() {
		return gestureDetector;
	}

	public TableColumnDto setGestureDetector(TableColumnGestureDetectorDto gestureDetector) {
		this.gestureDetector = gestureDetector;
		return this;
	}

	public ToolTipDto getToolTip() {
		return toolTip;
	}

	public TableColumnDto setToolTip(ToolTipDto toolTip) {
		this.toolTip = toolTip;
		return this;
	}

	public Boolean getEnableToggleAll() {
		return enableToggleAll;
	}

	public TableColumnDto setEnableToggleAll(Boolean enableToggleAll) {
		this.enableToggleAll = enableToggleAll;
		return this;
	}

	public ColumnWidthAutoFitType getAutoFitType() {
		return autoFitType;
	}

	public TableColumnDto setAutoFitType(ColumnWidthAutoFitType autoFitType) {
		this.autoFitType = autoFitType;
		return this;
	}

	public String getEditorWidgetIdTemplate() {
		return editorWidgetIdTemplate;
	}

	public TableColumnDto setEditorWidgetIdTemplate(String editorWidgetIdTemplate) {
		this.editorWidgetIdTemplate = editorWidgetIdTemplate;
		return this;
	}

	public Boolean getEnableGuiSorting() {
		return enableGuiSorting;
	}

	public TableColumnDto setEnableGuiSorting(Boolean enableGuiSorting) {
		this.enableGuiSorting = enableGuiSorting;
		return this;
	}

	@Override
    public TableColumnDto setName(String name) {
        return (TableColumnDto)super.setName(name);
    }

    @Override
    public TableColumnDto setLabel(String label) {
        return (TableColumnDto)super.setLabel(label);
    }

    @Override
    public TableColumnDto setDescription(String description) {
        return (TableColumnDto)super.setDescription(description);
    }

    @Override
    public TableColumnDto setReadOnly(boolean readOnly) {
        return (TableColumnDto)super.setReadOnly(readOnly);
    }

    @Override
    public TableColumnDto setEditor(WidgetDto editor) {
        return (TableColumnDto)super.setEditor(editor);
    }
}
