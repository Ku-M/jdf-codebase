package fe.cmn.listView;

import fe.cmn.widget.LabelDto;
import fe.cmn.widget.WidgetDto;
import flutter.coder.annt.NullSafe;

public class ListViewItemDto extends SelectedListViewItemDto {
	
	@NullSafe
	WidgetDto content;
	
	// 勾选框是否选中
    boolean selected;
	
	private static final long serialVersionUID = -6122475225656679989L;
	
	public ListViewItemDto() {
		
	}
	
	public ListViewItemDto(String key) {
		this.content = new LabelDto(key);
		this.setKey(key);
	}
	
	public ListViewItemDto(String key, String text) {
		this.content = new LabelDto(text);
		this.setKey(key);
	}
	
	public ListViewItemDto(String key, WidgetDto content) {
		this.content = content;
		this.setKey(key);
	}
	
	@Override
	public ListViewItemDto setKey(String key) {
		this.key = key;
		return this;
	}

	public WidgetDto getContent() {
		return content;
	}

	public ListViewItemDto setContent(WidgetDto content) {
		this.content = content;
		return this;
	}

	public boolean isSelected() {
		return selected;
	}

	public ListViewItemDto setSelected(boolean selected) {
		this.selected = selected;
		return this;
	}
}
