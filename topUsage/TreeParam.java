package fe.util.component.param;

import fe.util.component.param.WidgetParam;

public class TreeParam extends BaseWidgetParam{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4178976058551423707L;
	/**
	 * 树的使用场景
	 */
	String scene;
	/**
	 *查询的根节点类型
	 */
	String rootNodeType;
	/**
	 * 查询的根节点key
	 */
	String rootNodeKey;
	/**
	 * 搜索关键字
	 */
	String filtersKeyWord;
	/**
	 * 是否懒加载
	 */
	boolean lazyLoad = true;
	// 在显示复选框的情况下，是否严格的遵循父子不互相关联的做法,默认为false
	Boolean checkStrictly = false;
	
	public String getScene() {
		return scene;
	}
	
	public TreeParam setScene(String scene) {
		this.scene = scene;
		return this;
	}
	
	public String getFiltersKeyWord() {
		return filtersKeyWord;
	}
	
	public TreeParam setFiltersKeyWord(String filtersKeyWord) {
		this.filtersKeyWord = filtersKeyWord;
		return this;
	}
	
	public boolean isLazyLoad() {
		return lazyLoad;
	}
	public TreeParam setLazyLoad(boolean lazyLoad) {
		this.lazyLoad = lazyLoad;
		return this;
	}
	
	public String getRootNodeKey() {
		return rootNodeKey;
	}
	
	public TreeParam setRootNodeKey(String rootNodeKey) {
		this.rootNodeKey = rootNodeKey;
		return this;
	}
	
	public String getRootNodeType() {
		return rootNodeType;
	}
	
	public TreeParam setRootNodeType(String rootNodeType) {
		this.rootNodeType = rootNodeType;
		return this;
	}
	
	@Override
	public TreeParam setWritable(boolean isWritable) {
		return (TreeParam) super.setWritable(isWritable);
	}
	
	public Boolean getCheckStrictly() {
		return checkStrictly;
	}
	
	public TreeParam setCheckStrictly(Boolean checkStrictly) {
		this.checkStrictly = checkStrictly;
		return this;
	}
	
	public boolean isCheckStrictly() {
		return checkStrictly != null && checkStrictly;
	}
}
