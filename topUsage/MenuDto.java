package fe.cmn.menu;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.leavay.common.util.ToolUtilities;
import cson.core.CsonPojo;
import fe.cmn.menu.listener.OnPopMenuClose;
import fe.cmn.panel.MenuPosition;
import fe.cmn.pojo.annotation.FieldDefine;
import fe.cmn.pojo.annotation.PojoMeta;
import fe.cmn.widget.WidgetDto;
import fe.cmn.widget.decoration.DecorationDto;
import flutter.coder.annt.NullSafe;

/**
 * 菜单组件
 * 
 * <p>通过PopMenu调用。
 */
@PojoMeta(label = "菜单组件", desc = "通过PopMenu调用。")
//public class MenuDto extends CsonPojo
public class MenuDto extends WidgetDto
{
    private static final long serialVersionUID = -9136177032140918367L;
    
    @NullSafe
    List<MenuItemDto> menuItems;
    
    
    @FieldDefine(isStyleField = true, label = "目录样式")
    MenuDecorationDto menuDecoration;
    
    // 锚点，默认为左上角（例如弹出菜单给到的位置是位于菜单的哪个位置）
    MenuPosition anchorPoint;
    
    OnPopMenuClose onClose;
    
    public MenuDto() {
    	
    }
    
    public MenuDto(List<MenuItemDto> menuItems) {
    	setMenuItems(menuItems);
    }
    
    public MenuDto(MenuPosition anchorPoint, List<MenuItemDto> menuItems) {
    	setMenuItems(menuItems);
    	setAnchorPoint(anchorPoint);
    }
    
    public MenuDto(MenuItemDto ...menuItems) {
    	setMenuItems(menuItems);
    }
     
    public MenuDto(MenuPosition anchorPoint, MenuItemDto ...menuItems) {
    	setMenuItems(menuItems);
    	setAnchorPoint(anchorPoint);
    }

    public List<MenuItemDto> getMenuItems()
    {
        return menuItems;
    }

    public void setMenuItems(List<MenuItemDto> menuItems)
    {
        this.menuItems = menuItems;
    }
    
    public void setMenuItems(MenuItemDto ...menuItems)
    {
        this.menuItems = Arrays.stream(menuItems).collect(Collectors.toList());;
    }
    
    public void addMenuItems(MenuItemDto ...menuItems)
    {
        if (menuItems == null)
            setMenuItems(menuItems);
        else
        {
            if (this.menuItems == null)
                this.menuItems = new LinkedList<MenuItemDto>();
            
            this.menuItems.addAll(ToolUtilities.array2List(menuItems));
        }
    }
    
    @Deprecated
	public MenuDto setDecoration(DecorationDto menuDecoration) {
		return this;
	}
    
    @Deprecated
	public DecorationDto getDecoration() {
		return super.getDecoration();
	}
    

	public MenuDecorationDto getMenutDecoration() {
		return menuDecoration;
	}
    
	public MenuDto setDecoration(MenuDecorationDto menuDecoration) {
		this.menuDecoration = menuDecoration;
		return this;
	}

	public MenuPosition getAnchorPoint() {
		return anchorPoint;
	}

	public MenuDto setAnchorPoint(MenuPosition anchorPoint) {
		this.anchorPoint = anchorPoint;
		return this;
	}

	public OnPopMenuClose getOnClose() {
		return onClose;
	}

	public MenuDto setOnClose(OnPopMenuClose onClose) {
		this.onClose = onClose;
		return this;
	}
	
	
}
