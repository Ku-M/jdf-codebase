package fe.cmn.panel.ability;

import java.util.List;

import com.leavay.common.util.ToolBasic;
import com.leavay.ms.tool.CmnUtil;

import fe.cmn.data.BasicAbility;
import fe.cmn.panel.PanelContext;
import fe.cmn.panel.PanelValue;
import flutter.coder.annt.NullSafe;

// 查询Panel的所有值（ignoreWidgets和widgests同时设置，只有widgests会生效）
public class QueryPanelValue extends BasicAbility<PanelValue>
{
    private static final long serialVersionUID = 2036496216981358395L;
    
    // 忽略的组件
    List<String> ignoreWidgets;
    
    // 指定组件
    List<String> widgets;

    /**
     * 是否只获取界面做过动作、修改后的值 默认为False：获取界面最终的值，修改后的值和初始化的值二取其一
     * 如果为True：则只取界面做过修改的值，没修改过的应该为null
     */
    @NullSafe
    boolean onlyGuiValue = false;

    public List<String> getIgnoreWidgets()
    {
        return ignoreWidgets;
    }

    public QueryPanelValue setIgnoreWidgets(List<String> ignoreWidgets)
    {
        this.ignoreWidgets = ignoreWidgets;
        return this;
    }

    public boolean isOnlyGuiValue()
    {
        return onlyGuiValue;
    }

    public QueryPanelValue setOnlyGuiValue(boolean onlyGuiValue)
    {
        this.onlyGuiValue = onlyGuiValue;
        return this;
    }

	public List<String> getWidgets() {
		return widgets;
	}

	public QueryPanelValue setWidgets(List<String> widgets) {
		this.widgets = widgets;
		return this;
	}

	public static PanelValue query(PanelContext ctx, String... ignoreWidgets) throws Exception
    {
        return query(ctx, false, ignoreWidgets);
    }

    public static PanelValue queryOnlyGuiValue(PanelContext ctx, String... ignoreWidgets) throws Exception
    {
        return query(ctx, true, ignoreWidgets);
    }

    public static PanelValue query(PanelContext ctx, boolean onlyGuiValue, String... ignoreWidgets) throws Exception
    {
        QueryPanelValue callback = new QueryPanelValue().setOnlyGuiValue(onlyGuiValue);
        if (!CmnUtil.isObjectEmpty(ignoreWidgets))
            callback.setIgnoreWidgets(ToolBasic.newArrayList(ignoreWidgets));
        return (PanelValue) ctx.callback(callback);
    }
    
    public static PanelValue queryTargets(PanelContext ctx, String... widgets) throws Exception
    {
        return queryTargets(ctx, false, widgets);
    }

    public static PanelValue queryTargetsOnlyGuiValue(PanelContext ctx, String... widgets) throws Exception
    {
        return queryTargets(ctx, true, widgets);
    }

    public static PanelValue queryTargets(PanelContext ctx, boolean onlyGuiValue, String... widgets) throws Exception
    {
        QueryPanelValue callback = new QueryPanelValue().setOnlyGuiValue(onlyGuiValue);
        if (!CmnUtil.isObjectEmpty(widgets))
            callback.setWidgets(ToolBasic.newArrayList(widgets));
        return (PanelValue) ctx.callback(callback);
    }
}
