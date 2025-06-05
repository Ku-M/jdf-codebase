package fe.cmn.tab;

import java.util.List;

import cson.core.CsonPojo;
import fe.cmn.pojo.annotation.FieldDefine;
import fe.cmn.widget.WidgetDto;
import flutter.coder.annt.DefaultGetter;
import flutter.coder.annt.NullSafe;

import com.leavay.common.util.ToolBasic;
import com.leavay.common.util.ToolUtilities;

public class TabItemDto extends CsonPojo {

	private static final long serialVersionUID = 5946235066239377541L;
	
	// 唯一标识
	@FieldDefine(label="唯一标识")
	@NullSafe(initCode = "Cson.allocUuid()")
	String tabId = ToolBasic.allockUUID();
	
	/**
	 * 标签文本
	 * 
	 * <p>折叠项菜单搜索根据此字段进行匹配。
	 */
	@FieldDefine(label="标签文本", description = "折叠项菜单搜索根据此字段进行匹配。")
	String text;
	
	// 显示插槽，可自定义显示
	@FieldDefine(label="标签文本插槽")
	WidgetDto textSlot;

	// 内容
	@FieldDefine(visible=false)
	WidgetDto content;

	// 是否可关闭
	@FieldDefine(label="是否可关闭")
	boolean closable;

	// 附带的icon按钮
	@FieldDefine(label="图标按钮")
	List<TabButtonDto> buttons;

	// 是否预加载
	@FieldDefine(label="是否预加载")
	@NullSafe(initCode="true")
	boolean keepAlive = true;
	
	// 首次渲染后是否缓存
	@FieldDefine(label="首次渲染后是否缓存")
	@DefaultGetter("true")
	Boolean cacheable;

	public TabItemDto() {
	}
	
	public TabItemDto(String tabId, String text, WidgetDto content) {
		this.tabId = tabId;
		this.text = text;
		this.content = content;
	}

	public TabItemDto(String text, WidgetDto content) {
		this.text = text;
		this.content = content;
	}
	
	public String getTabId() {
		return tabId;
	}

	public TabItemDto setTabId(String tabId) {
		this.tabId = tabId;
		return this;
	}

	public String getText() {
		return text;
	}

	public TabItemDto setText(String text) {
		this.text = text;
		return this;
	}

	public WidgetDto getContent() {
		return content;
	}

	public TabItemDto setContent(WidgetDto content) {
		this.content = content;
		return this;
	}

	public boolean isClosable() {
		return closable;
	}

	public TabItemDto setClosable(boolean closable) {
		this.closable = closable;
		return this;
	}

	public List<TabButtonDto> getButtons() {
		return buttons;
	}

	public TabItemDto setButtons(List<TabButtonDto> buttons) {
		this.buttons = buttons;
		return this;
	}

	public TabItemDto setButtons(TabButtonDto... buttons) {
		this.buttons = ToolUtilities.array2List(buttons);
		return this;
	}

	public boolean isKeepAlive() {
		return keepAlive;
	}

	public TabItemDto setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
		return this;
	}

	public WidgetDto getTextSlot() {
		return textSlot;
	}

	public TabItemDto setTextSlot(WidgetDto textSlot) {
		this.textSlot = textSlot;
		return this;
	}

	public Boolean getCacheable() {
		return cacheable;
	}

	public TabItemDto setCacheable(Boolean cacheable) {
		this.cacheable = cacheable;
		return this;
	}
}
