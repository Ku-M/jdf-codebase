package fe.cmn.menu;

import java.util.LinkedList;
import java.util.List;

import com.leavay.common.util.ToolUtilities;
import com.leavay.ms.tool.CmnUtil;

import cson.core.CsonPojo;
import fe.cmn.widget.ListenerDto;
import fe.cmn.widget.WidgetDto;

public class MenuItemDto extends CsonPojo
{
    private static final long serialVersionUID = -3654261488598012191L;

    String icon;
    
    String label;
    
    // 位于图标之前，必须有prefSize设置了width（以便计算菜单项宽度），否则视为非法
    WidgetDto headerWidget;
    
    List<MenuItemDto> subItems;
    
    ListenerDto onClick;
    
    MenuItemDecorationDto menuItemDecoration;
    
    MenuItemDecorationDto highlightMenuItemDecoration;

    public String getIcon()
    {
        return icon;
    }

    public MenuItemDto setIcon(String icon)
    {
        this.icon = icon;
        return this;
    }

    
    public String getLabel()
    {
        return label;
    }
    

    public MenuItemDto setLabel(String label)
    {
        this.label = label;
        return this;
    }


    public WidgetDto getHeaderWidget()
    {
        return headerWidget;
    }

    public MenuItemDto setHeaderWidget(WidgetDto headerWidget)
    {
        this.headerWidget = headerWidget;
        CmnUtil.assertNotNull(headerWidget.getPreferSize(), "Should set prefer size for menu item widget");
        CmnUtil.assertNotNull(headerWidget.getPreferSize().getWidth(), "Should set prefer width for menu item widget");
        
        return this;
    }

    public List<MenuItemDto> getSubItems()
    {
        return subItems;
    }

    public MenuItemDto setSubItems(List<MenuItemDto> subItems)
    {
        this.subItems = subItems;
        return this;
    }
    

    public void addSubItems(MenuItemDto ...menuItems)
    {
        if (menuItems == null)
            setSubItems(ToolUtilities.array2List(menuItems));
        else
        {
            if (this.subItems == null)
                this.subItems = new LinkedList<MenuItemDto>();
            
            this.subItems.addAll(ToolUtilities.array2List(menuItems));
        }
    }

    public ListenerDto getOnClick()
    {
        return onClick;
    }

    public MenuItemDto setOnClick(ListenerDto onClick)
    {
        this.onClick = onClick;
        return this;
    }

	public MenuItemDecorationDto getMenuItemDecoration() {
		return menuItemDecoration;
	}

	public MenuItemDto setMenuItemDecoration(MenuItemDecorationDto menuItemDecoration) {
		this.menuItemDecoration = menuItemDecoration;
		return this;
	}

	public MenuItemDecorationDto getHighlightMenuItemDecoration() {
		return highlightMenuItemDecoration;
	}

	public MenuItemDto setHighlightMenuItemDecoration(MenuItemDecorationDto highlightMenuItemDecoration) {
		this.highlightMenuItemDecoration = highlightMenuItemDecoration;
		return this;
	}
}
