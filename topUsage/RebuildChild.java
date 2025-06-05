package fe.cmn.panel.ability;

import java.util.List;

import com.leavay.common.util.ToolUtilities;

import fe.cmn.panel.BasicBatchAbility;
import fe.cmn.panel.PanelContext;
import fe.cmn.widget.WidgetDto;
import flutter.coder.annt.NullSafe;

// 替换重建一个子组件（Widget或Panel），不能重建自身
public class RebuildChild extends BasicBatchAbility<Void>
{
    private static final long serialVersionUID = 2036496216981358395L;
    
    // 新的Widget元数据
    @NullSafe
    List<WidgetDto> newWidgets;
    
    // 是否清理界面当前的值（仅当Editor时有意义）
    @NullSafe(initCode="true")
    boolean clearGuiValue = true;
    
    @NullSafe(initCode="true")
    boolean rebuildTimers = true;
    
    public List<WidgetDto> getNewWidgets()
    {
        return newWidgets;
    }

    public RebuildChild setNewWidgets(List<WidgetDto> newWidgets)
    {
        this.newWidgets = newWidgets;
        return this;
    }

    public boolean isClearGuiValue()
    {
        return clearGuiValue;
    }

    public RebuildChild setClearGuiValue(boolean clearGuiValue)
    {
        this.clearGuiValue = clearGuiValue;
        return this;
    }
    
    public boolean isRebuildTimers() {
		return rebuildTimers;
	}

	public RebuildChild setRebuildTimers(boolean rebuildTimers) {
		this.rebuildTimers = rebuildTimers;
		return this;
	}
	
	@Override
    public RebuildChild setDeferErrors(Boolean deferErrors) {
		super.setDeferErrors(deferErrors);
		return this;
	}

	// 清空编辑器界面旧值
    public static void rebuild(PanelContext ctx, WidgetDto ... newWidget) throws Exception
    {
        RebuildChild callback = new RebuildChild().setNewWidgets(ToolUtilities.newArrayList(newWidget));
        ctx.callback(callback);
    }

    public static void rebuild(PanelContext ctx, List<WidgetDto> newWidget) throws Exception
    {
        RebuildChild callback = new RebuildChild().setNewWidgets(newWidget);
        ctx.callback(callback);
    }
    
    // 保留编辑器界面旧值
    public static void rebuildWithGuiValue(PanelContext ctx, WidgetDto ... newWidget) throws Exception
    {
        RebuildChild callback = new RebuildChild().setNewWidgets(ToolUtilities.newArrayList(newWidget)).setClearGuiValue(false);
        ctx.callback(callback);
    }

    public static void rebuildWithGuiValue(PanelContext ctx, List<WidgetDto> newWidget) throws Exception
    {
        RebuildChild callback = new RebuildChild().setNewWidgets(newWidget).setClearGuiValue(false);
        ctx.callback(callback);
    }
    
    public static void rebuild(PanelContext ctx, Boolean clearGuiValue, Boolean rebuildTimers, List<WidgetDto> newWidget) throws Exception
    {
        RebuildChild callback = new RebuildChild().setNewWidgets(newWidget).setClearGuiValue(clearGuiValue).setRebuildTimers(rebuildTimers);
        ctx.callback(callback);
    }
    
    public static void rebuild(PanelContext ctx, Boolean clearGuiValue, Boolean rebuildTimers, List<WidgetDto> newWidget, boolean deferErrors) throws Exception
    {
        RebuildChild callback = new RebuildChild().setNewWidgets(newWidget).setClearGuiValue(clearGuiValue).setRebuildTimers(rebuildTimers).setDeferErrors(deferErrors);
        ctx.callback(callback);
    }
}
