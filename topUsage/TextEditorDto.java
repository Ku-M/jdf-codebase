package fe.cmn.editor;

import java.util.List;

import fe.cmn.data.BasicKeyboardDto;
import fe.cmn.editor.listener.OnKeyboard;
import fe.cmn.editor.listener.OnValueChanged;
import fe.cmn.editor.listener.OnTextEditorSubmitted;
import fe.cmn.event.EventSubscriberDto;
import fe.cmn.pojo.annotation.FieldDefine;
import fe.cmn.pojo.annotation.PojoMeta;
import fe.cmn.widget.DraggableDto;
import fe.cmn.widget.DropListener;
import fe.cmn.widget.SizeDto;
import fe.cmn.widget.WidgetDto;
import fe.cmn.widget.decoration.DecorationDto;
import fe.cmn.widget.decoration.TableCellTextEditorDecorationDto;
import fe.cmn.widget.decoration.TextEditorDecorationDto;
import flutter.coder.annt.DefaultGetter;

/**
 * 输入框
 * 
 * <p>装饰类为TextEditorDecorationDto
 */
@PojoMeta(label = "输入框", icon="res://images/units/input.png", fields= {@FieldDefine(fieldName = "decoration", targetClass = TextEditorDecorationDto.class)})
public class TextEditorDto extends BasicTextEditorDto<String>
{
    private static final long serialVersionUID = 5472247890965396570L;
    
    String textEditorService;
    
    TextEditorServiceParamDto textEditorServiceParam;

    @FieldDefine(label="文本值")
    String value;
    
    // 校验类型，不设置默认为失焦校验
    RegularExpType regularExpType;
    
    // 正则表达式约束
    String regularExp;
    
    // 密码类型显示
    @FieldDefine(isStyleField = true, label = "是否密码类型")
    Boolean obscureText;
    
    // 表格单元格中编辑输入框样式
    TableCellTextEditorDecorationDto tableCellTextEditorDecoration;
    
    /*
     * 自动聚焦
     */
    @DefaultGetter("false")
    Boolean autofocus;
    
    public TextEditorDto()
    {
    }
    
    public TextEditorDto(String value)
    {
        setValue(value);
    }

	public String getTextEditorService() {
		return textEditorService;
	}

	public TextEditorDto setTextEditorService(Class<? extends TextEditorInterface> serviceClass) {
		this.textEditorService = serviceClass.getName();
		return this;
	}

	public TextEditorServiceParamDto getTextEditorServiceParam() {
		return textEditorServiceParam;
	}

	public TextEditorDto setTextEditorServiceParam(TextEditorServiceParamDto textEditorServiceParam) {
		this.textEditorServiceParam = textEditorServiceParam;
		return this;
	}

	public String getValue() {
        return value;
    }

    @Override
    public void setValue(String v)
    {
        value = v;
    }

    public String getRegularExp()
    {
        return regularExp;
    }

    public TextEditorDto setRegularExp(String regularExp)
    {
        this.regularExp = regularExp;
        return this;
    }
    
    @Override
    public TextEditorDto setMaxRenderLines(int maxRenderLines)
    {
        this.maxRenderLines = maxRenderLines;
        return this;
    }
    
    @Override
	public TextEditorDto setMinRenderLines(int minRenderLines) {
		this.minRenderLines = minRenderLines;
		return this;
	}
    
    @Override
	public TextEditorDto setHelperText(String helperText) {
		this.helperText = helperText;
		return this;
	}
	
	@Override
	public TextEditorDto setHelperMaxLines(int helperMaxLines) {
		this.helperMaxLines = helperMaxLines;
		return this;
	}
	
	@Override
	public TextEditorDto setHintText(String hintText) {
		this.hintText = hintText;
		return this;
	}

	@Override
	public TextEditorDto setHintMaxLines(int hintMaxLines) {
		this.hintMaxLines = hintMaxLines;
		return this;
	}

	public Boolean getObscureText() {
		return obscureText;
	}

	public TextEditorDto setObscureText(Boolean obscureText) {
		this.obscureText = obscureText;
		return this;
	}
	
	@Override
	public TextEditorDto setPrefix(String prefix) {
		this.prefix = prefix;
		return this;
	}
	
	@Override
	public TextEditorDto setSuffix(String suffix) {
		this.suffix = suffix;
		return this;
	}
	
	@Override
	public TextEditorDto setOutsideIcon(String outsideIcon) {
		this.outsideIcon = outsideIcon;
		return this;
	}
	
	@Override
	public TextEditorDto setPrefixIcon(String prefixIcon) {
		this.prefixIcon = prefixIcon;
		return this;
	}
	
	@Override
	public TextEditorDto setSuffixIcon(String suffixIcon) {
		this.suffixIcon = suffixIcon;
		return this;
	}

	@Override
	public TextEditorDto setOnKeyboard(OnKeyboard onKeyboard) {
		this.onKeyboard = onKeyboard;
		return this;
	}
	
	@Override
	public TextEditorDto setPrefixWidget(WidgetDto prefixWidget) {
		this.prefixWidget = prefixWidget;
		return this;
	}
	
	@Override
	public TextEditorDto setPrefixWidgetFolllowFocus(WidgetDto prefixWidgetFollowFocus) {
		this.prefixWidgetFollowFocus = prefixWidgetFollowFocus;
		return this;
	}
	
	@Override
	public TextEditorDto setSuffixWidget(WidgetDto suffixWidget) {
		this.suffixWidget = suffixWidget;
		return this;
	}
	
	@Override
	public TextEditorDto setSuffixWidgetFollowFocus(WidgetDto suffixWidgetFollowFocus) {
		this.suffixWidgetFollowFocus = suffixWidgetFollowFocus;
		return this;
	}

	public RegularExpType getRegularExpType() {
		return regularExpType;
	}

	public TextEditorDto setRegularExpType(RegularExpType regularExpType) {
		this.regularExpType = regularExpType;
		return this;
	}
	
	@Override
	public TextEditorDto setErrorText(String errorText) {
		this.errorText = errorText;
		return this;
	}
	
	@Override
	public TextEditorDto setOnSubmitted(OnTextEditorSubmitted onSubmitted) {
		this.onSubmitted = onSubmitted;
		return this;
	}
	
	@Override
	public TextEditorDto setNewlineKeyCombination(BasicKeyboardDto newlineKeyCombination) {
		this.newlineKeyCombination = newlineKeyCombination;
		return this;
	}
	
	@Override
	public TextEditorDto setSubmitKeyCombination(BasicKeyboardDto submitKeyCombination) {
		this.submitKeyCombination = submitKeyCombination;
		return this;
	}

	@Override
    public TextEditorDto setWritable(Boolean writable) {
        return (TextEditorDto)super.setWritable(writable);
    }

    @Override
    public TextEditorDto setOnValueChanged(OnValueChanged onValueChanged) {
        return (TextEditorDto)super.setOnValueChanged(onValueChanged);
    }

    @Override
    public TextEditorDto setWidgetId(String widgetId) {
        return (TextEditorDto) super.setWidgetId(widgetId);
    }

    @Override
    public TextEditorDto setDropListener(DropListener dropListener) {
        return (TextEditorDto)super.setDropListener(dropListener);
    }

    @Override
    public TextEditorDto setSubscribeEvents(List<EventSubscriberDto> subscribeEvents) {
        return (TextEditorDto)super.setSubscribeEvents(subscribeEvents);
    }

    @Override
    public TextEditorDto addSubscribeEvent(EventSubscriberDto subscriber) {
        return (TextEditorDto)super.addSubscribeEvent(subscriber);
    }

    @Override
    public TextEditorDto setPreferSize(SizeDto preferSize) {
        return (TextEditorDto)super.setPreferSize(preferSize);
    }

    @Override
    public TextEditorDto setMinSize(SizeDto minSize) {
        return (TextEditorDto)super.setMinSize(minSize);
    }

    @Override
    public TextEditorDto setMaxSize(SizeDto maxSize) {
        return (TextEditorDto)super.setMaxSize(maxSize);
    }

    @Override
    public TextEditorDto setExpandInBox(boolean expandInBox) {
        return (TextEditorDto)super.setExpandInBox(expandInBox);
    }

    @Override
    public TextEditorDto setVisible(boolean visible) {
        return (TextEditorDto)super.setVisible(visible);
    }

    @Override
    public TextEditorDto setDraggable(DraggableDto draggableData) {
        return (TextEditorDto)super.setDraggable(draggableData);
    }

    @Override
    public TextEditorDto setDecoration(DecorationDto decoration) {
        return (TextEditorDto)super.setDecoration(decoration);
    }
    
    @Override
    public TextEditorDto setClearable(Boolean clearable) {
		super.setClearable(clearable);
		return this;
	}

	public Integer getCollapseMaxLines() {
		return collapseMaxLines;
	}

	public TextEditorDto setCollapseMaxLines(Integer collapseMaxLines) {
		this.collapseMaxLines = collapseMaxLines;
		return this;
	}

	public String getExpendButtonLabel() {
		return expendButtonLabel;
	}

	public TextEditorDto setExpendButtonLabel(String expendButtonLabel) {
		this.expendButtonLabel = expendButtonLabel;
		return this;
	}

	public String getCollapseButtonLabel() {
		return collapseButtonLabel;
	}

	public TextEditorDto setCollapseButtonLabel(String collapseButtonLabel) {
		this.collapseButtonLabel = collapseButtonLabel;
		return this;
	}

	public TableCellTextEditorDecorationDto getTableCellTextEditorDecoration() {
		return tableCellTextEditorDecoration;
	}

	public TextEditorDto setTableCellTextEditorDecoration(TableCellTextEditorDecorationDto tableCellTextEditorDecoration) {
		this.tableCellTextEditorDecoration = tableCellTextEditorDecoration;
		return this;
	}

	public Boolean getAutofocus() {
		return autofocus;
	}

	public TextEditorDto setAutofocus(Boolean autofocus) {
		this.autofocus = autofocus;
		return this;
	}
}
