package fe.cmn.editor;

import fe.cmn.editor.decoration.EditorReadonlyStyleTheme;
import fe.cmn.editor.listener.OnValueChanged;
import fe.cmn.panel.PanelContext;
import fe.cmn.pojo.annotation.FieldDefine;
import fe.cmn.widget.ListenerDto;
import fe.cmn.widget.WidgetDto;
import flutter.coder.annt.DefaultGetter;
import flutter.coder.annt.FlutterCode;

@FlutterCode("String? rowId;\t\nString? columnName;")
public abstract class EditorDto<T> extends WidgetDto
{

    private static final long serialVersionUID = -4105226775938581770L;

    // 此字段用于前端设置，后端获取，在Java端无需set方法
    // 必须是CsonPojo，否则无法序列化（尤其在合并到map后）
    abstract public T getValue();

    abstract public void setValue(T v);
    
    // 监听值变化：（对于可能高频变化的值监听是有昂贵开销的，虽然前端做了秒级限流，仍需谨慎使用）
    @FieldDefine(label="监听取值变化")
    OnValueChanged onValueChanged;
    public final static String LISTENER_onValueChanged="onValueChanged";
    
    // 界面修改后缓存于此，否则重建Widget时会冲掉GuiValue，通常只在界面端使用此字段
    @FieldDefine(visible = false)
    Object guiValue;

    @FieldDefine(label = "监听失焦事件")
    ListenerDto onBlur;
    
    // 是否可写
    @FieldDefine(label="是否可写")
    @DefaultGetter("true")
    Boolean writable;
    
    // editor只读状态样式主题
    // 优先级EditorDto > PanelDto > AppDto
    EditorReadonlyStyleTheme editorReadonlyStyleTheme;
    
    public Object getGuiValue()
    {
        return guiValue;
    }

    public Boolean getWritable() {
    	return writable;
    }
    
    public EditorDto setWritable(Boolean writable)
    {
        this.writable = writable;
        return this;
    } 
    
    public EditorReadonlyStyleTheme getEditorReadonlyStyleTheme() {
		return editorReadonlyStyleTheme;
	}

	public EditorDto setEditorReadonlyStyleTheme(EditorReadonlyStyleTheme editorReadonlyStyleTheme) {
		this.editorReadonlyStyleTheme = editorReadonlyStyleTheme;
		return this;
	}

	public ListenerDto getOnBlur()
    {
        return onBlur;
    }

    public EditorDto setOnBlur(ListenerDto onBlur)
    {
        this.onBlur = onBlur;
        return this;
    }
    
    public OnValueChanged getOnValueChanged()
    {
        return onValueChanged;
    }

    public EditorDto setOnValueChanged(OnValueChanged onValueChanged)
    {
        this.onValueChanged = onValueChanged;
        return this;
    }

    public void restoreFromContext(PanelContext panelContext)
    {
        restoreFromContext(panelContext, null);
    }

    // 从面板上下文获取值，恢复到DTO数据中（常用于再次构建界面）
    public void restoreFromContext(PanelContext panelContext, T defaultValue)
    {
        T v = defaultValue;
        if (getWidgetId() != null)
        {
            T vv = (T) panelContext.getWidgetValue(getWidgetId());
            if (vv != null)
                v = vv;
        }

        setValue(v);
    }
    
    
}
