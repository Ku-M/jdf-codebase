package fe.cmn.panel.ability;

import fe.cmn.data.BasicAbility;
import fe.cmn.menu.MenuDto;
import fe.cmn.panel.PanelContext;
import fe.cmn.widget.OffsetDto;
import fe.cmn.panel.MenuPosition;
import flutter.coder.annt.NullSafe;

/**
 * 
 * 弹出菜单
 * 主要有以下三种方式：
 * 1.依附于组件，相对组件位置进行显示
 * 2.直接指定位置显示
 * 3.当前鼠标位置显示
 * 
 */
public class PopMenu extends BasicAbility<Void> {

	private static final long serialVersionUID = -2085781336296431218L;
	
	@NullSafe
	// 菜单项
	MenuDto menu;
	
	// 若此值为空，则相对于当前鼠标位置；反之则依附于组件，相对于组件位置
	String attachWidgetId;
	
	// 默认为left_bottom（左下角）
	MenuPosition attachPosition;
	
	// 也可直接传入位置（例如点击事件可以获取鼠标位置）
	OffsetDto position;

	public MenuDto getMenu() {
		return menu;
	}

	public PopMenu setMenu(MenuDto menu) {
		this.menu = menu;
		return this;
	}
	
	public String getAttachWidgetId() {
		return attachWidgetId;
	}

	public PopMenu setAttachWidgetId(String attachWidgetId) {
		this.attachWidgetId = attachWidgetId;
		return this;
	}

	public MenuPosition getAttachPosition() {
		return attachPosition;
	}

	public PopMenu setAttachPosition(MenuPosition attachPosition) {
		this.attachPosition = attachPosition;
		return this;
	}

	public OffsetDto getPosition() {
		return position;
	}

	public PopMenu setPosition(OffsetDto position) {
		this.position = position;
		return this;
	}
	
	// 当前鼠标位置显示
	public static void show(PanelContext ctx, MenuDto menu) throws Exception
	{
		PopMenu callback = new PopMenu().setMenu(menu);
		ctx.callback(callback);
	}
	
	// 直接指定位置
	public static void show(PanelContext ctx, MenuDto menu, OffsetDto position) throws Exception
	{
		PopMenu callback = new PopMenu().setMenu(menu).setPosition(position);
		ctx.callback(callback);
	}
	
	// 依附于组件(默认左下角)
	public static void attachShow(PanelContext ctx, MenuDto menu, String attachWidgetId) throws Exception
	{
		PopMenu callback = new PopMenu().setMenu(menu).setAttachWidgetId(attachWidgetId);
		ctx.callback(callback);
	}
	
	// 依附于组件
	public static void attachShow(PanelContext ctx, MenuDto menu, String attachWidgetId, MenuPosition attachPosition) throws Exception
	{
		PopMenu callback = new PopMenu().setMenu(menu).setAttachPosition(attachPosition).setAttachWidgetId(attachWidgetId);
		ctx.callback(callback);
	}
	
	
	public static void attachShow(PanelContext ctx, MenuDto menu, String attachWidgetId, MenuPosition attachPosition, OffsetDto offset) throws Exception
	{
		PopMenu callback = new PopMenu().setMenu(menu).setAttachPosition(attachPosition).setAttachWidgetId(attachWidgetId).setPosition(offset);
		ctx.callback(callback);
	}
}
