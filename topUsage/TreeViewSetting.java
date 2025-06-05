package gpf.dc.basic.param.view.dto;

import java.io.Serializable;
import java.util.List;

import com.kwaidoo.ms.tool.CmnUtil;

import cn.hutool.core.collection.CollUtil;

public class TreeViewSetting implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8244967888306613680L;
	boolean isMultiSelect = false;
	/**
	 * 节点Key属性
	 */
	String keyFieldName = "uuid";
	/**
	 * 父节点属性
	 */
	String parentKeyFieldName;
	/**
	 * 排序属性
	 */
	String sortFieldName;
	/**
	 * 节点类型属性
	 */
	String nodeTypeFieldName;
	/**
	 * 节点标签属性
	 */
	String labelFieldName;
	/**
	 * 节点标签拼接字符
	 */
	String labelJoiner;
	/**
	 * 根节点取值
	 */
	String rootKeyValue;
	
	/**
	 * 是否允许节点拖拽
	 */
	boolean isEnableRowDrag = true;
	/**
	 * 是否显示节点操作栏
	 */
	boolean isEnableRowOperate = true;
	/**
	 * 允许刷新
	 */
	boolean isAllowRefresh = true;
	/**
	 * 允许新增
	 */
	boolean isAllowCreate = true;
	/**
	 * 允许查看详情
	 */
	boolean isAllowRowClick = true;
	/**
	 * 允许修改
	 */
	boolean isAllowUpdate = true;
	/**
	 * 允许删除
	 */
	boolean isAllowDelete = true;
	/**
	 * 允许级联删除
	 */
	boolean isAllowCascadingDeletion = false;
	/**
	 * 懒加载方式渲染界面
	 */
	boolean lazyRender = false;
	/**
	 * 懒加载方式加载数据
	 */
	boolean lazyLoad = true;
	
	String treeStyle;
	/**
	 * 允许导出数据
	 */
	boolean isAllowExport = false;
	/**
	 * 允许导入数据
	 */
	boolean isAllowImport = false;
	/**
	 * 导入时允许选择多个文件
	 */
	boolean isAllowMultipleImport= false;
	/**
	 * 允许导入的文件类型
	 */
	String allowImportFileType;
	
	/**
	 * 查看详情时重新请求数据
	 */
	boolean isReGetDataOnRowClick = true;
	/**
	 * 显示搜索栏
	 */
	boolean showSearchBar = false;
	/**
	 * 显示工具提示
	 */
	boolean showToolTip = false;
	
	/**
	 *  在显示复选框的情况下，是否严格的遵循父子不互相关联的做法,默认为false
	 */
	boolean checkStrictly = false;
	/**
	 * 永远显示按钮栏
	 */
	Boolean alwaysShowButtonBar;
	/**
	 * 默认展开层级
	 */
	Integer defaultExpandLayer;

	public boolean isMultiSelect() {
		return isMultiSelect;
	}
	public TreeViewSetting setMultiSelect(boolean isMultiSelect) {
		this.isMultiSelect = isMultiSelect;
		return this;
	}
	public String getKeyFieldName() {
		return keyFieldName;
	}
	public TreeViewSetting setKeyFieldName(String keyFieldName) {
		this.keyFieldName = keyFieldName;
		return this;
	}
	public String getParentKeyFieldName() {
		return parentKeyFieldName;
	}
	public TreeViewSetting setParentKeyFieldName(String parentKeyFieldName) {
		this.parentKeyFieldName = parentKeyFieldName;
		return this;
	}
	public String getNodeTypeFieldName() {
		return nodeTypeFieldName;
	}
	public TreeViewSetting setNodeTypeFieldName(String nodeTypeFieldName) {
		this.nodeTypeFieldName = nodeTypeFieldName;
		return this;
	}
	public String getLabelFieldName() {
		return labelFieldName;
	}
	public TreeViewSetting setLabelFieldName(String labelFieldName) {
		this.labelFieldName = labelFieldName;
		return this;
	}
	public String getLabelJoiner() {
		return labelJoiner;
	}
	public TreeViewSetting setLabelJoiner(String labelJoiner) {
		this.labelJoiner = labelJoiner;
		return this;
	}
	public String getSortFieldName() {
		return sortFieldName;
	}
	public TreeViewSetting setSortFieldName(String sortFieldName) {
		this.sortFieldName = sortFieldName;
		return this;
	}
	public String getRootKeyValue() {
		return rootKeyValue;
	}
	public TreeViewSetting setRootKeyValue(String rootKeyValue) {
		this.rootKeyValue = rootKeyValue;
		return this;
	}
	public boolean isEnableRowDrag() {
		return isEnableRowDrag;
	}
	public TreeViewSetting setEnableRowDrag(boolean isEnableRowDrag) {
		this.isEnableRowDrag = isEnableRowDrag;
		return this;
	}
	public boolean isEnableRowOperate() {
		return isEnableRowOperate;
	}
	public TreeViewSetting setEnableRowOperate(boolean isEnableRowOperate) {
		this.isEnableRowOperate = isEnableRowOperate;
		return this;
	}
	public boolean isAllowRefresh() {
		return isAllowRefresh;
	}
	public TreeViewSetting setAllowRefresh(boolean isAllowRefresh) {
		this.isAllowRefresh = isAllowRefresh;
		return this;
	}
	public boolean isAllowCreate() {
		return isAllowCreate;
	}
	public TreeViewSetting setAllowCreate(boolean isAllowCreate) {
		this.isAllowCreate = isAllowCreate;
		return this;
	}
	public boolean isAllowRowClick() {
		return isAllowRowClick;
	}
	public TreeViewSetting setAllowRowClick(boolean isAllowRowClick) {
		this.isAllowRowClick = isAllowRowClick;
		return this;
	}
	public boolean isAllowUpdate() {
		return isAllowUpdate;
	}
	public TreeViewSetting setAllowUpdate(boolean isAllowUpdate) {
		this.isAllowUpdate = isAllowUpdate;
		return this;
	}
	public boolean isAllowDelete() {
		return isAllowDelete;
	}
	public TreeViewSetting setAllowDelete(boolean isAllowDelete) {
		this.isAllowDelete = isAllowDelete;
		return this;
	}
	public boolean isLazyRender() {
		return lazyRender;
	}
	public TreeViewSetting setLazyRender(boolean lazyRender) {
		this.lazyRender = lazyRender;
		return this;
	}
	public boolean islazyLoad() {
		return lazyLoad;
	}
	public TreeViewSetting setLazyLoad(boolean lazyLoad) {
		this.lazyLoad = lazyLoad;
		return this;
	}
	public boolean isAllowCascadingDeletion() {
		return isAllowCascadingDeletion;
	}
	public TreeViewSetting setAllowCascadingDeletion(boolean isAllowCascadingDeletion) {
		this.isAllowCascadingDeletion = isAllowCascadingDeletion;
		return this;
	}
	
	public String getTreeStyle() {
		return treeStyle;
	}
	public TreeViewSetting setTreeStyle(String treeStyle) {
		this.treeStyle = treeStyle;
		return this;
	}
	
	public boolean isAllowExport() {
		return isAllowExport;
	}
	
	public TreeViewSetting setAllowExport(boolean isAllowExport) {
		this.isAllowExport = isAllowExport;
		return this;
	}
	public boolean isAllowImport() {
		return isAllowImport;
	}
	public TreeViewSetting setAllowImport(boolean isAllowImport) {
		this.isAllowImport = isAllowImport;
		return this;
	}
	
	public boolean isAllowMultipleImport() {
		return isAllowMultipleImport;
	}
	public TreeViewSetting setAllowMultipleImport(boolean isAllowMultipleImport) {
		this.isAllowMultipleImport = isAllowMultipleImport;
		return this;
	}
	public String getAllowImportFileType() {
		return allowImportFileType;
	}
	public TreeViewSetting setAllowImportFileType(String allowImportFileType) {
		this.allowImportFileType = allowImportFileType;
		return this;
	}
	public List<String> getAllowImportFileTypeList(){
		if(CmnUtil.isStringEmpty(allowImportFileType))
			return null;
		String[] array = allowImportFileType.split(",");
		return CollUtil.newArrayList(array);
	}
	public boolean isReGetDataOnRowClick() {
		return isReGetDataOnRowClick;
	}
	public TreeViewSetting setReGetDataOnRowClick(boolean isReGetDataOnRowClick) {
		this.isReGetDataOnRowClick = isReGetDataOnRowClick;
		return this;
	}
	/**
	 * 查看详情嵌套数据是否在表单加载后再查询
	 */
	boolean isLazyQueryCompoundField = false;

	public boolean isLazyQueryCompoundField() {
		return isLazyQueryCompoundField;
	}
	public TreeViewSetting setLazyQueryCompoundField(boolean isLazyQueryCompoundField) {
		this.isLazyQueryCompoundField = isLazyQueryCompoundField;
		return this;
	}
	
	public boolean isShowSearchBar() {
		return showSearchBar;
	}
	public TreeViewSetting setShowSearchBar(boolean showSearchBar) {
		this.showSearchBar = showSearchBar;
		return this;
	}
	public boolean isLazyLoad() {
		return lazyLoad;
	}
	public boolean isShowToolTip() {
		return showToolTip;
	}
	public boolean isCheckStrictly() {
		return checkStrictly;
	}
	public Boolean getAlwaysShowButtonBar() {
		return alwaysShowButtonBar;
	}
	public TreeViewSetting setShowToolTip(boolean showToolTip) {
		this.showToolTip = showToolTip;
		return this;
	}
	public TreeViewSetting setCheckStrictly(boolean checkStrictly) {
		this.checkStrictly = checkStrictly;
		return this;
	}
	public TreeViewSetting setAlwaysShowButtonBar(Boolean alwaysShowButtonBar) {
		this.alwaysShowButtonBar = alwaysShowButtonBar;
		return this;
	}
	
	public Integer getDefaultExpandLayer() {
		return defaultExpandLayer;
	}
	public TreeViewSetting setDefaultExpandLayer(Integer defaultExpandLayer) {
		this.defaultExpandLayer = defaultExpandLayer;
		return this;
	}
}
