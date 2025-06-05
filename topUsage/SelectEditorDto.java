package fe.cmn.editor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.leavay.common.util.ToolBasic;
import com.leavay.common.util.ToolUtilities;
import com.leavay.ms.tool.CmnUtil;

import fe.cmn.data.PairDto;
import fe.cmn.editor.listener.OnSelectEditorOptionSelect;
import fe.cmn.editor.listener.OnValueChanged;
import fe.cmn.event.EventSubscriberDto;
import fe.cmn.pojo.annotation.FieldDefine;
import fe.cmn.pojo.annotation.PojoMeta;
import fe.cmn.widget.DraggableDto;
import fe.cmn.widget.DropListener;
import fe.cmn.widget.GestureDetectorDto;
import fe.cmn.widget.SizeDto;
import fe.cmn.widget.ToolTipDto;
import fe.cmn.widget.WidgetDto;
import fe.cmn.widget.decoration.DecorationDto;
import flutter.coder.annt.DefaultGetter;

/**
 * 下拉框
 * 
 * <p>界面选择的结果单选为PairDto，多选为List<PairDto>。
 */
@PojoMeta(label = "下拉框", icon="res://images/units/drop_down.png")
public class SelectEditorDto extends ClearableEditorDto<Object> {
	
	private static final long serialVersionUID = -2012414978951109975L;
	
	Object value;

	@Deprecated
	// 是否允许自主编辑输入内容（默认false），allowCreate替代此字段
	Boolean editable;
	
	// 是否多选，默认单选
	Boolean multiple;
	
	// 多选时限制最多可选项，null及0为不限制
	Integer multipleLimit;

	// 下拉框里显示的项，（名、值对，以key为比对依据，显示的是value）
	PairDto[] items;

	// 可编辑时的提示占位符
	String hintText;
	
	// 下拉选项面板高度（为最大高度）
	double panelHeight;
	
	// 视口高度倍数关系设置下拉选项面板高度（为最大高度，优先级大于panelHeight）
	Double panelHeightByWindowSize;
	
	@DefaultGetter("false")
	// 是否开启选项搜索
	Boolean filterable;
	
	//本地搜索时根据什么进行匹配
	@DefaultGetter("FilterPairBy.value")
	FilterPairBy fileterBy;
	
	@DefaultGetter("false")
	// 是否开启选项搜索
	Boolean remoteFilter;
	
	/**
	 * 聚焦时是否过滤搜索（有值的情况下），默认不开启。
	 */
	@DefaultGetter("false")
	Boolean focusFilter;
	
	@DefaultGetter("false")
	// 是否允许创建选择项
	Boolean allowCreate;
	
	@DefaultGetter("1")
	//单选时输入框最大显示行数
	Integer maxLine;
	
	// 下拉选项请求服务，设置后每次打开选项面板都会重新请求数据
	String panelService;
	
	SelectEditorQuerier querier;
	
	@FieldDefine(label = "缓存请求数据", description = "下拉请求数据只请求一次，仅在panelService不为空时生效，默认为false")
	@DefaultGetter("false")
	Boolean cachedFromService;

	//代码提示选项插槽模板，key为和选项中的templateName对应
	Map<String,WidgetDto> itemTemplateMap;
	
	/**
	 * 扩展事件，例如遍历焦点选中高亮项、点击外部创建输入项等。
	 */
	List<SelectEditorExtendedEvent> extendedEvent;
	
	/**
	 * 选项选中监听。
	 * 
	 * 和onValueChanged的主要区别在于：
	 * 1. 单选选中值无变化时不会触发onValueChanged，但是会触发此监听。
	 * 2. 多选仅在确认所有选中值后才会触发onValueChanged，但是切换选项选中状态就会触发此监听。
	 */
	OnSelectEditorOptionSelect onOptionSelect;

	public String getHintText() {
		return hintText;
	}

	public SelectEditorDto setHintText(String hintText) {
		this.hintText = hintText;
		return this;
	}

	public PairDto[] getItems() {
		return items;
	}

	public SelectEditorDto setItems(PairDto[] items) {
		this.items = items;
		return this;
	}
	
	public SelectEditorDto setItems(List<PairDto> items)
	{
	    if (CmnUtil.isObjectEmpty(items))
	        return setItems((PairDto[])null);
	    
	    this.items =  (PairDto[]) ToolBasic.list2Array(items, new PairDto[items.size()]);
	    return this;
	}
	
	public SelectEditorDto addItem(PairDto item)
	{
	    List<PairDto> lst = ToolUtilities.array2List(items);
	    if (lst == null)
	        lst = new LinkedList<PairDto>();
	    
	    lst.add(item);
	    
	    items = (PairDto[]) ToolBasic.list2Array(lst, new PairDto[lst.size()]);
	    return this;
	}
	
	@Deprecated
	public Boolean getEditable() {
		return editable;
	}
	
	@Deprecated
	public SelectEditorDto setEditable(Boolean editable) {
		this.editable = editable;
		return this;
	}

	public Object getSelected() {
		return value;
	}

	public SelectEditorDto setSelected(Object pair) {
		value = pair;
		return this;
	}

	public Boolean getMultiple() {
		return multiple;
	}

	public Integer getMultipleLimit() {
		return multipleLimit;
	}

	public SelectEditorDto setMultipleLimit(Integer multipleLimit) {
		this.multipleLimit = multipleLimit;
		return this;
	}

	public SelectEditorDto setMultiple(Boolean multiple) {
		this.multiple = multiple;
		return this;
	}
	
	public double getPanelHeight() {
		return panelHeight;
	}

	public SelectEditorDto setPanelHeight(double panelHeight) {
		this.panelHeight = panelHeight;
		return this;
	}

	public Double getPanelHeightByWindowSize() {
		return panelHeightByWindowSize;
	}

	public SelectEditorDto setPanelHeightByWindowSize(Double panelHeightByWindowSize) {
		this.panelHeightByWindowSize = panelHeightByWindowSize;
		return this;
	}

	public Boolean getFilterable() {
		return filterable;
	}

	public SelectEditorDto setFilterable(Boolean filterable) {
		this.filterable = filterable;
		return this;
	}

	public FilterPairBy getFileterBy() {
		return fileterBy;
	}

	public SelectEditorDto setFileterBy(FilterPairBy fileterBy) {
		this.fileterBy = fileterBy;
		return this;
	}

	public String getPanelService() {
		return panelService;
	}

	public SelectEditorDto setPanelService(Class<? extends SelectEditorInterface> service) {
		this.panelService = service.getName();
		return this;
	}
	
	public Boolean getAllowCreate() {
		return allowCreate;
	}

	public SelectEditorDto setAllowCreate(Boolean allowCreate) {
		this.allowCreate = allowCreate;
		return this;
	}

	public Boolean getCachedFromService() {
		return cachedFromService;
	}

	public SelectEditorDto setCachedFromService(Boolean cachedFromService) {
		this.cachedFromService = cachedFromService;
		return this;
	}

	public SelectEditorQuerier getQuerier() {
		return querier;
	}

	public SelectEditorDto setQuerier(SelectEditorQuerier querier) {
		this.querier = querier;
		return this;
	}

	public Boolean getFocusFilter() {
		return focusFilter;
	}

	public SelectEditorDto setFocusFilter(Boolean focusFilter) {
		this.focusFilter = focusFilter;
		return this;
	}

	@Override
	public Object getValue() {
		return getSelected();
	}

	@Override
	public void setValue(Object v) {
		value = v;
	}

	@Override
	public SelectEditorDto setWidgetId(String widgetId) {
		super.setWidgetId(widgetId);
		return this;
	}

	@Override
	public SelectEditorDto setDropListener(DropListener dropListener) {
		super.setDropListener(dropListener);
		return this;
	}

	@Override
	public SelectEditorDto setSubscribeEvents(List<EventSubscriberDto> subscribeEvents) {
		super.setSubscribeEvents(subscribeEvents);
		return this;
	}

	@Override
	public SelectEditorDto addSubscribeEvent(EventSubscriberDto subscriber) {
		super.addSubscribeEvent(subscriber);
		return this;
	}

	@Override
	public SelectEditorDto setPreferSize(SizeDto preferSize) {
		super.setPreferSize(preferSize);
		return this;
	}

	@Override
	public SelectEditorDto setMinSize(SizeDto minSize) {
		super.setMinSize(minSize);
		return this;
	}

	@Override
	public SelectEditorDto setMaxSize(SizeDto maxSize) {
		super.setMaxSize(maxSize);
		return this;
	}

	@Override
	public SelectEditorDto setExpandInBox(boolean expandInBox) {
		super.setExpandInBox(expandInBox);
		return this;
	}

	@Override
	public SelectEditorDto setVisible(boolean visible) {
		super.setVisible(visible);
		return this;
	}

	@Override
	public SelectEditorDto setDraggable(DraggableDto draggableData) {
		super.setDraggable(draggableData);
		return this;
	}

	@Override
	public SelectEditorDto setDecoration(DecorationDto decoration) {
		super.setDecoration(decoration);
		return this;
	}

	@Override
	public SelectEditorDto setGestureDetector(GestureDetectorDto gestureDetector) {
		super.setGestureDetector(gestureDetector);
		return this;
	}

	@Override
	public SelectEditorDto setStyleName(String styleName) {
		super.setStyleName(styleName);
		return this;
	}

	@Override
	public SelectEditorDto setToolTip(ToolTipDto toolTip) {
		super.setToolTip(toolTip);
		return this;
	}

	@Override
	public SelectEditorDto setToolTip(String message) {
		super.setToolTip(message);
		return this;
	}

	@Override
	public SelectEditorDto setWritable(Boolean writable) {
		super.setWritable(writable);
		return this;
	}

	@Override
	public SelectEditorDto setOnValueChanged(OnValueChanged onValueChanged) {
		super.setOnValueChanged(onValueChanged);
		return this;
	}
	
	@Override
	public SelectEditorDto setClearable(Boolean clearable) {
		this.clearable = clearable;
		return this;
	}

	public Integer getMaxLine() {
		return maxLine;
	}

	public SelectEditorDto setMaxLine(Integer maxLine) {
		this.maxLine = maxLine;
		return this;
	}

	public Boolean getsetRemoteFilter() {
		return remoteFilter;
	}

	public SelectEditorDto setRemoteFilter(Boolean remoteFilter) {
		this.remoteFilter = remoteFilter;
		return this;
	}

	public Map<String, WidgetDto> getItemTemplateMap() {
		return itemTemplateMap;
	}

	public SelectEditorDto setItemTemplateMap(Map<String, WidgetDto> itemTemplateMap) {
		this.itemTemplateMap = itemTemplateMap;
		return this;
	}

	public SelectEditorDto addItemTemplate(String templateName, WidgetDto template) {
		if(this.itemTemplateMap == null) {
			this.itemTemplateMap = new HashMap<String, WidgetDto>();
		}
		this.itemTemplateMap.put(templateName, template);
		return this;
	}

	public List<SelectEditorExtendedEvent> getExtendedEvent() {
		return extendedEvent;
	}

	public SelectEditorDto setExtendedEvent(List<SelectEditorExtendedEvent> extendedEvent) {
		this.extendedEvent = extendedEvent;
		return this;
	}
	
	public SelectEditorDto setExtendedEvent(SelectEditorExtendedEvent... extendedEvent) {
		this.extendedEvent = Arrays.stream(extendedEvent).collect(Collectors.toList());
		return this;
	}

	public OnSelectEditorOptionSelect getOnOptionSelect() {
		return onOptionSelect;
	}

	public SelectEditorDto setOnOptionSelect(OnSelectEditorOptionSelect onOptionSelect) {
		this.onOptionSelect = onOptionSelect;
		return this;
	}
}
