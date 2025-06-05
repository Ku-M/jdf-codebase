package gpf.dc.fe.component.param;

import java.util.ArrayList;
import java.util.List;

import fe.util.component.param.BaseWidgetParam;

public class FormModelSelectorParam extends BaseWidgetParam{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6220463351823668330L;
	/**
	 * 模型选择样式
	 */
	String modelSelectComponent;
	/**
	 * 已选中的模型ID
	 */
	String selectedModelId;
	/**
	 * 已选中的模型ID列表，多选时使用
	 */
	List<String> selectedModelIds = new ArrayList<>();
	/**
	 * 显示时是否显示模型ID
	 */
	boolean showModelIdAtLabel = false;
	/**
	 * 显示管理入口
	 */
	boolean showManageEntry = false;
	/**
	 * 显示详情
	 */
	boolean viewDetail = true;
	/**
	 * 允许新增
	 */
	boolean allowCreate = true;
	
	/**
	 * 是否多选
	 */
	boolean isMultiSelect = false;
	/**
	 * 开始搜索的父节点
	 */
	List<String> parentModelIds;
	/**
	 * 父节点允许选中
	 */
	boolean parentModelSelectable = true;
	/**
	 * 可选的模型节点，配置此项时，搜索的父节点配置不生效
	 */
	List<String> selectableModelIds;
	
	public String getModelSelectComponent() {
		return modelSelectComponent;
	}
	public FormModelSelectorParam setModelSelectComponent(String modelSelectComponent) {
		this.modelSelectComponent = modelSelectComponent;
		return this;
	}
	
	public String getSelectedModelId() {
		return selectedModelId;
	}
	public FormModelSelectorParam setSelectedModelId(String selectedModelId) {
		this.selectedModelId = selectedModelId;
		return this;
	}
	public List<String> getSelectedModelIds() {
		return selectedModelIds;
	}
	public FormModelSelectorParam setSelectedModelIds(List<String> selectedModelIds) {
		this.selectedModelIds = selectedModelIds;
		return this;
	}
	public boolean isShowModelIdAtLabel() {
		return showModelIdAtLabel;
	}
	public FormModelSelectorParam setShowModelIdAtLabel(boolean showModelIdAtLabel) {
		this.showModelIdAtLabel = showModelIdAtLabel;
		return this;
	}
	public boolean isViewDetail() {
		return viewDetail;
	}
	public FormModelSelectorParam setViewDetail(boolean viewDetail) {
		this.viewDetail = viewDetail;
		return this;
	}
	public boolean isAllowCreate() {
		return allowCreate;
	}
	public FormModelSelectorParam setAllowCreate(boolean allowCreate) {
		this.allowCreate = allowCreate;
		return this;
	}
	public List<String> getParentModelIds() {
		return parentModelIds;
	}
	public FormModelSelectorParam setParentModelIds(List<String> parentModelIds) {
		this.parentModelIds = parentModelIds;
		return this;
	}
	public List<String> getSelectableModelIds() {
		return selectableModelIds;
	}
	public FormModelSelectorParam setSelectableModelIds(List<String> selectableModelIds) {
		this.selectableModelIds = selectableModelIds;
		return this;
	}
	public FormModelSelectorParam setShowManageEntry(boolean showManageEntry) {
		this.showManageEntry = showManageEntry;
		return this;
	}
	public boolean isShowManageEntry() {
		return showManageEntry;
	}
	public boolean isMultiSelect() {
		return isMultiSelect;
	}
	public FormModelSelectorParam setMultiSelect(boolean isMultiSelect) {
		this.isMultiSelect = isMultiSelect;
		return this;
	}
	public boolean isParentModelSelectable() {
		return parentModelSelectable;
	}
	public FormModelSelectorParam setParentModelSelectable(boolean parentModelSelectable) {
		this.parentModelSelectable = parentModelSelectable;
		return this;
	}
	
}
