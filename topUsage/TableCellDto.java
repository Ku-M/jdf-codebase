package fe.cmn.table;

import com.leavay.ms.tool.CmnUtil;

import cson.core.CsonPojo;
import fe.cmn.table.decoration.TableCellDecorationDto;
import fe.cmn.widget.WidgetDto;
import flutter.coder.annt.FlutterToString;

@FlutterToString("// 单元格渲染到界面就用toString\n\t\treturn value==null?'':value.toString();")
public class TableCellDto extends CsonPojo
{
    private static final long serialVersionUID = 6746233105347071799L;

    // 动态类型的值，一定要实现toString（用于界面显示）
    Object value;
    
    // 样式
    TableCellDecorationDto decoration;
    
    // tooltip显示内容
    String tooltip;
    
    // 单元格编辑器，优先级高于表头的编辑器
    WidgetDto editor;
    
    // 编辑器是否可编辑（不会影响editor字段，仅控制列编辑器）
    Boolean writable;
    
    public TableCellDto()
    {
    }

    public TableCellDto(Object value)
    {
        setValue(value);
    }

    public Object getValue()
    {
        return value;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }
    
    public String toString()
    {
        return CmnUtil.getString(value,"");
    }

	public TableCellDecorationDto getDecoration() {
		return decoration;
	}

	public TableCellDto setDecoration(TableCellDecorationDto decoration) {
		this.decoration = decoration;
		return this;
	}

	public String getTooltip() {
		return tooltip;
	}

	public TableCellDto setTooltip(String tooltip) {
		this.tooltip = tooltip;
		return this;
	}

	public WidgetDto getEditor() {
		return editor;
	}

	public TableCellDto setEditor(WidgetDto editor) {
		this.editor = editor;
		return this;
	}

	public Boolean getWritable() {
		return writable;
	}

	public TableCellDto setWritable(Boolean writable) {
		this.writable = writable;
		return this;
	}
}
