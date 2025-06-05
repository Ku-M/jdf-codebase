package fe.cmn.editor;

import fe.cmn.pojo.annotation.PojoMeta;
import fe.cmn.widget.WidgetDto;
import flutter.coder.annt.NullSafe;

/**
 * 自定义编辑器
 * 
 * <p>空的包裹任何自定义组件、panel作为Editor进行取值、回显等功能
 * 
 * 一般值对象支持以下三类：
 * 1、普通的CsonPojo（平台固化的的类，前端有对应代码的）
 * 2、ValuePojo（包裹简单值，string、int之类）
 * 3、BinPojo（复杂第三方对象，非框架固化，前端不识别直接以二进制传递）
 *
 *	<p>writable设置会影响其底下所有编辑器的可编辑性，不过优先级低于底下编辑器自身设置
 */
@PojoMeta(label = "自定义编辑器")
public class CustomizeEditorDto extends EditorDto<Object>
{
    private static final long serialVersionUID = -7906304200853607521L;
    
    Object value;
    
    @NullSafe
    WidgetDto child;

    public Object getValue()
    {
        return value;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

    public WidgetDto getChild()
    {
        return child;
    }

    public CustomizeEditorDto setChild(WidgetDto child)
    {
        this.child = child;
        return this;
    }
    
    public static CustomizeEditorDto wrap(WidgetDto child)
    {
        return new CustomizeEditorDto().setChild(child);
    }
    
}
