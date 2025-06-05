package fe.cmn.table.listener;

import java.util.List;

import fe.cmn.event.EventDto;
import fe.cmn.widget.ListenerExecutorDto;

// 默认返回TableRowDto
public class TableRowListener<T> extends TableBasicListener<T> {

	private static final long serialVersionUID = 4017421582293676539L;
	
	// 排除在外的单元格类型，某些内部自带点击事件的不会触发
	// 默认排除EditorDto、BoxDto
	// 例如：[LabelDto.class.getName()]
	List<String> excludeEditorType;
	
	public TableRowListener() {
		
	}
	
	public TableRowListener(Class service, String command, boolean synchronize) {
		super(service, command, synchronize);
	}
	
	public TableRowListener(Class service, String command, boolean synchronize,T data) {
		super(service, command, synchronize, data);
	}

	public List<String> getExcludeEditorType() {
		return excludeEditorType;
	}

	public TableRowListener setExcludeEditorType(List<String> excludeEditorType) {
		this.excludeEditorType = excludeEditorType;
		return this;
	}
	
	@Override
	public TableRowListener setBringBackTableRowDto(Boolean bringBackTableRowDto) {
		this.bringBackTableRowDto = bringBackTableRowDto;
		return this;
	}
	
	@Override
	public TableRowListener setBringBackTableRowFePojo(Boolean bringBackTableRowFePojo) {
		this.bringBackTableRowFePojo = bringBackTableRowFePojo;
		return this;
	}
	
	@Override
	public TableRowListener setBringBackTableColumnDto(Boolean bringBackTableColumnDto) {
		this.bringBackTableColumnDto = bringBackTableColumnDto;
		return this;
	}
	
	@Override
	public TableRowListener setBringBackTableCellDto(Boolean bringBackTableCellDto) {
		this.bringBackTableCellDto = bringBackTableCellDto;
		return this;
	}

	@Override
	public TableRowListener setExecutor(ListenerExecutorDto executor) {
		super.setExecutor(executor);
		return this;
	}

	@Override
	public TableRowListener setServerExecutor(Class service, String command) {
		super.setServerExecutor(service, command);
		return this;
	}

	@Override
	public TableRowListener setEventExecutor(EventDto event) {
		super.setEventExecutor(event);
		return this;
	}

	@Override
	public TableRowListener setSynchronize(boolean synchronize) {
		super.setSynchronize(synchronize);
		return this;
	}

	@Override
	public TableRowListener setData(T data) {
		super.setData(data);
		return this;
	}

	@Override
	public TableRowListener setSelfBinaryData() {
		super.setSelfBinaryData();
		return this;
	}
}
