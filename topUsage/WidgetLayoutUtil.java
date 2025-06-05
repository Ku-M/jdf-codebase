package gpf.dc.basic.fe.component.fieldextend.editor;

import java.util.LinkedHashSet;
import java.util.Set;

import com.kwaidoo.ms.tool.CmnUtil;
import com.kwaidoo.ms.tool.ToolUtilities;

import cmn.i18n.I18nIntf;
import fe.cmn.FeUtil;
import fe.cmn.panel.BoxDto;
import fe.cmn.panel.CollapseDto;
import fe.cmn.panel.ContainerDto;
import fe.cmn.panel.GridDto;
import fe.cmn.panel.GridViewDto;
import fe.cmn.panel.IndexedStackDto;
import fe.cmn.panel.PanelDto;
import fe.cmn.panel.SinglePanelDto;
import fe.cmn.panel.SplitViewDto;
import fe.cmn.panel.TabDto;
import fe.cmn.panel.WrapDto;
import fe.cmn.studio.DesignerSettingsDto;
import fe.cmn.widget.WidgetDto;
import fe.util.component.AbsComponent;

public class WidgetLayoutUtil {
	
	public final static String WidgetId_DesignContainer_Prefix = "DesignContainer_";
	public static final String UNIT_NAME_CUSTOM_SPLIT_VIEW = "customSplitView";
	public static final String UNIT_NAME_CUSTOM_BUTTON = "customButton";
	public static final String UNIT_NAME_CUSTOM_IMAGE = "customImage";
	public static final String UNIT_NAME_PLACEHOLDER = "placeHolder";
	public static final String UNIT_NAME_CUSTOM_LABEL = "customLabel";
	public static final String UNIT_NAME_CUSTOM_BOX = "customBox";
	public static final String UNIT_NAME_CUSTOM_TAB = "customTab";
	public static final String UNIT_NAME_CUSTOM_WRAP = "customWrap";
	public static final String UNIT_NAME_CUSTOM_GRID_VIEW = "customGridView";
	public static final String UNIT_NAME_CUSTOM_CONTAINER = "customContainer";
	public static final String UNIT_NAME_CUSTOM_TABLE = "customTable";
	public static final String UNIT_NAME_CUSTOM_TREE = "customTree";
	public static final String UNIT_NAME_CUSTOM_GRID = "customGrid";
	public static final String UNIT_NAME_CUSTOM_COLLAPASE = "customCollapse";
	public static final String UNIT_NAME_CUSTOM_INDEXED_STACK = "customIndexedStack";
	public static final String UNIT_NAME_CUSTOM_STACK = "customStack";
	public static final String UNIT_NAME_CUSTOM_GROUP = "customGroup";
	public static final String UNIT_NAME_CUSTOM_UNGROUP = "customUnGroup";
	
	public static String getWidgetIdInContainer(ContainerDto container) {
		String widgetId = container.getWidgetId().replaceAll(WidgetId_DesignContainer_Prefix, "");
		return widgetId;
	}
	
	public static ContainerDto wrapDesignContainer(String tag,WidgetDto widget) {
		ContainerDto container = new ContainerDto();
		String widgetId = widget.getWidgetId();
		if(widget instanceof BoxDto) {
			if(widget.getWidgetId().startsWith(AbsComponent.FIELD_BOX_WIDGET_ID_PREFIX)) {
				widgetId = widgetId.replaceAll(AbsComponent.FIELD_BOX_WIDGET_ID_PREFIX, "");
			}
		}
		container.setWidgetId(WidgetId_DesignContainer_Prefix+widgetId);
		container.setChild(widget);
		container.setExpandInBox(false);
		container.setBindInsideWidget(true);
		container.setUnitName(WidgetLayoutDesigner.UNIT_NAME_CUSTOM_CONTAINER);
		DesignerSettingsDto designerSettings = new DesignerSettingsDto();
		container.setDesignerSettings(designerSettings);
		if(CmnUtil.isStringEmpty(tag)) {
			designerSettings.setTag(widgetId);
		}else {
			designerSettings.setTag(tag);
		}
		return container;
	}

	public static WidgetDto convert2Layout(WidgetDto widget) throws Exception {
		WidgetDto cloneWidget = ToolUtilities.clone(widget);
		WidgetLayoutReplacer replacer = new WidgetLayoutReplacer();
		replacer.visit(cloneWidget);
		return cloneWidget;
	}
	
	public static WidgetDto setRealWidget2Layout(WidgetDto widget,WidgetDto layout,I18nIntf i18n,String keyGroup) throws Exception{
		WidgetDtoVisitor visitor = new WidgetDtoVisitor();
		visitor.visit(widget);
		WidgetDto copyLayout = ToolUtilities.clone(layout);
		WidgetLayoutReplacer replacer = new WidgetLayoutReplacer();
		replacer.setDesignWidgets(visitor.getDesignWidgets());
//			long start = System.currentTimeMillis();
		replacer.setI18n(i18n).setI18nGroup(keyGroup);
		replacer.visit(copyLayout);
//		long end = System.currentTimeMillis();
//		LvUtil.trace("widget i18n : " + (end-start)+ "ms");
		setLayout(widget, copyLayout);
		return widget;
	}
	
	public static WidgetDto replaceBottomBarStyle(WidgetDto widget,WidgetDto layout) throws Exception{
		WidgetDtoVisitor visitor = new WidgetDtoVisitor();
		visitor.visit(layout);
		WidgetLayoutReplacer_BottomBar replacer = new WidgetLayoutReplacer_BottomBar();
		replacer.setDesignWidgets(visitor.getDesignWidgets());
		replacer.visit(widget);
		return widget;
	}
	
	public static void removeTabWhenFieldsRFalse(WidgetDto widget) throws Exception{
		Set<String> tabWidgetIds = new LinkedHashSet<>();
		TabDto tab = null;
		tab = (TabDto) FeUtil.searchWidget(widget, (w) -> {
			return w instanceof TabDto && !tabWidgetIds.contains(w.getWidgetId());});
		while(tab != null) {
			tabWidgetIds.add(tab.getWidgetId());
			WidgetEmptyTabHandler handler = new WidgetEmptyTabHandler();
			boolean isVisible = handler.visit(tab);
			tab.setVisible(isVisible);
			tab = (TabDto) FeUtil.searchWidget(widget, (w) -> {
				return w instanceof TabDto && !tabWidgetIds.contains(w.getWidgetId());});
		}
	}
	
	public static void removeCollapseWhenFieldsRFalse(WidgetDto widget) throws Exception{
		Set<String> tabWidgetIds = new LinkedHashSet<>();
		CollapseDto tab = null;
		tab = (CollapseDto) FeUtil.searchWidget(widget, (w) -> {
			return w instanceof CollapseDto && !tabWidgetIds.contains(w.getWidgetId());});
		while(tab != null) {
			tabWidgetIds.add(tab.getWidgetId());
			WidgetEmptyTabHandler handler = new WidgetEmptyTabHandler();
			boolean isVisible = handler.visit(tab);
			tab.setVisible(isVisible);
			tab = (CollapseDto) FeUtil.searchWidget(widget, (w) -> {
				return w instanceof CollapseDto && !tabWidgetIds.contains(w.getWidgetId());});
		}
	}
	
	public static void setLayout(WidgetDto panel,WidgetDto layout)throws Exception{
		if(panel instanceof WrapDto) {
			setLayout((WrapDto)panel, (WrapDto)layout);
		}else if(panel instanceof ContainerDto) {
			setLayout((ContainerDto)panel, (ContainerDto)layout);
		}else if(panel instanceof GridDto) {
			setLayout((GridDto)panel, (GridDto)layout);
		}else if(panel instanceof GridViewDto) {
			setLayout((GridViewDto)panel, (GridViewDto)layout);
		}else if(panel instanceof TabDto) {
			setLayout((TabDto)panel, (TabDto)layout);
		}else if(panel instanceof CollapseDto) {
			setLayout((CollapseDto)panel, (CollapseDto)layout);
		}else if(panel instanceof BoxDto) {
			setLayout((BoxDto)panel, (BoxDto)layout);
		}else if(panel instanceof SplitViewDto) {
			setLayout((SplitViewDto)panel, (SplitViewDto)layout);
		}else if(panel instanceof IndexedStackDto) {
			setLayout((IndexedStackDto)panel, (IndexedStackDto)layout);
		}else if(panel instanceof SinglePanelDto) {
			setLayout((SinglePanelDto)panel, (SinglePanelDto)layout);
		}else if(panel instanceof PanelDto) {
			setLayout((PanelDto)panel, (PanelDto)layout);
		}
	}
	
	public static void setLayout(WrapDto panel,WrapDto layout)throws Exception{
		panel.setChildren(layout.getChildren());
	}
	
	public static void setLayout(IndexedStackDto panel,IndexedStackDto layout)throws Exception{
		panel.setChildren(layout.getChildren());
	}
	
	public static void setLayout(ContainerDto panel,ContainerDto layout)throws Exception{
		panel.setChild(layout.getChild());
	}
	public static void setLayout(GridViewDto panel,GridViewDto layout)throws Exception{
		panel.setListChild(layout.getListChild());
	}
	
	public static void setLayout(GridDto panel,GridDto layout)throws Exception{
		panel.setAreaMap(layout.getAreaMap());
		panel.setBlocks(layout.getBlocks());
	}
	
	public static void setLayout(TabDto panel,TabDto layout)throws Exception{
		panel.setTabItems(layout.getTabItems());
	}
	
	public static void setLayout(CollapseDto panel,CollapseDto layout)throws Exception{
		panel.setCollapseItems(layout.getCollapseItems());
	}
	
	public static void setLayout(BoxDto panel,BoxDto layout)throws Exception{
		panel.setChildren(layout.getChildren());
	}
	
	public static void setLayout(SplitViewDto panel,SplitViewDto layout)throws Exception{
		panel.setLeft(layout.getLeft());
		panel.setRight(layout.getRight());
	}
	
	public static void setLayout(PanelDto panel,PanelDto layout)throws Exception{
		panel.setBottomBar(layout.getBottomBar());
		panel.setTopBar(layout.getTopBar());
	}
	
	public static void setLayout(SinglePanelDto panel,SinglePanelDto layout)throws Exception{
		panel.setBottomBar(layout.getBottomBar());
		panel.setTopBar(layout.getTopBar());
		panel.setContent(layout.getContent());
	}
}
