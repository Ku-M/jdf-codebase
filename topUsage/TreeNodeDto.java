package fe.cmn.tree;

import fe.cmn.tree.decoration.TreeNodeDecorationDto;
import fe.cmn.widget.DraggableDto;
import fe.cmn.widget.GestureDetectorDto;
import fe.cmn.widget.ToolTipDto;
import flutter.coder.annt.FlutterCode;
import flutter.coder.annt.NullSafe;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import cmn.util.Nulls;
import cson.core.CsonPojo;

@FlutterCode("@override\n\tbool compare(dynamic other) {if(other is TreeNodeDto){return key == other.key;}return false;}")
public class TreeNodeDto extends SelectedTreeNodeDto {
	private static final long serialVersionUID = 6282823899406683728L;

	/**
	 * 标签
	 */
	String label;

	/**
	 * 图标（对应后端动态图标管理中的图标编码：code）
	 */
	String icon;

	/**
	 * 是否叶子节点（叶子节点将不会触发加载子节点的动作）
	 */
	boolean isLeaf;
	
	// 非叶子节点是否默认展开
	boolean expanded;


	/**
	 * 是否高亮
	 */
	Boolean highLighted;
	
	/**
	 * 是否可勾选
	 */
	@NullSafe(initCode = "true")
	boolean isSelect = true;
	
	// 是否可点击选中高亮
	@NullSafe(initCode = "true")
	boolean canHighlight = true;

	// toolTip配置(无需设置child)，若无设置且树开启toolTip,则直接显示节点label
	ToolTipDto toolTipDto;

	// 右侧悬浮按钮
	ButtonBarDto buttonBar;

	/**
	 * 可拖拽对象，如果为NULL表示该对象不可拖拽
	 * 所有Widget都可以被设定为拖拽，且自定义拖拽数据
	 * <p>
	 * 释放时会触发目标组件的拽入投放事件：dropListener
	 */
	DraggableDto draggable;

	// 点击动作
	GestureDetectorDto gestureDetector;

	// 高亮项变更监听（普通点选，非checkbox）
	// TreeDto.expandOnClick为false时，点击展开按钮不会触发节点高亮（但会触发节点点击监听）
	TreeHighlightListener onHignlight;
	
	// 路径
	List<String> path;
	
	TreeNodeDecorationDto decoration;

	public TreeNodeDto() {
	}

	public TreeNodeDto(String key, String parentKey, String label, String icon, boolean isLeaf) {
		this.key = key;
		this.parentKey = parentKey;
		this.label = label;
		this.icon = icon;
		this.isLeaf = isLeaf;
	}
	
	@Override
	public TreeNodeDto setKey(String key) {
		this.key = key;
		return this;
	}

	@Override
	public TreeNodeDto setParentKey(String parentKey) {
		this.parentKey = parentKey;
		return this;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getLabel() {
		return label;
	}

	public TreeNodeDto setLabel(String label) {
		this.label = label;
		return this;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public TreeNodeDto setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
		return this;
	}

	public boolean isSelect() {
		return isSelect;
	}

	public TreeNodeDto setSelect(boolean isSelect) {
		this.isSelect = isSelect;
		return this;
	}
	
	
	
	public boolean isHighLighted() {
		return highLighted;
	}

	public TreeNodeDto setHighLighted(Boolean highLighted) {
		this.highLighted = highLighted;
		return this;
	}

	@Override
	public TreeNodeDto setSelected(boolean selected) {
		this.selected = selected;
		return this;
	}

	public GestureDetectorDto getGestureDetector() {
		return gestureDetector;
	}

	public TreeNodeDto setGestureDetector(GestureDetectorDto gestureDetector) {
		this.gestureDetector = gestureDetector;
		return this;
	}

	public TreeHighlightListener getOnHignlight() {
		return onHignlight;
	}

	public TreeNodeDto setOnHignlight(TreeHighlightListener onHignlight) {
		this.onHignlight = onHignlight;
		return this;
	}

	public DraggableDto getDraggable() {
		return draggable;
	}

	public TreeNodeDto setDraggable(DraggableDto draggable) {
		this.draggable = draggable;
		return this;
	}

	public ToolTipDto getToolTipDto() {
		return toolTipDto;
	}

	public TreeNodeDto setToolTipDto(ToolTipDto toolTipDto) {
		this.toolTipDto = toolTipDto;
		return this;
	}

	public ButtonBarDto getButtonBar() {
		return buttonBar;
	}

	public TreeNodeDto setButtonBar(ButtonBarDto buttonBar) {
		this.buttonBar = buttonBar;
		return this;
	}

	public List<String> getPath() {
		return path;
	}

	public TreeNodeDto setPath(List<String> path) {
		this.path = path;
		return this;
	}
	
	public boolean canHighlight() {
		return canHighlight;
	}

	public TreeNodeDto setCanHighlight(boolean canHighlight) {
		this.canHighlight = canHighlight;
		return this;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public TreeNodeDto setExpanded(boolean expanded) {
		this.expanded = expanded;
		return this;
	}

	public TreeNodeDecorationDto getDecoration() {
		return decoration;
	}

	public TreeNodeDto setDecoration(TreeNodeDecorationDto decoration) {
		this.decoration = decoration;
		return this;
	}

	@Override
    public TreeNodeDto setUserObject(Object userObject)
    {
        super.setUserObject(userObject);
        return this;
    }
	
	@Override
	public TreeNodeDto setUserPojo(CsonPojo userPojo)
    {
        super.setUserPojo(userPojo);
        return this;
    }
	
	@Override
	public TreeNodeDto setBinaryData(Serializable binaryData) throws IOException
    {
        super.setBinaryData(binaryData);
        return this;
    }
	
	@Override
	public TreeNodeDto setBinaryDataIgnoreErr(Object binaryData)
    {
		super.setBinaryDataIgnoreErr(binaryData);
        return this;
    }
}
