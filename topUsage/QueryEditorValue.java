package fe.cmn.panel.ability;

import com.leavay.common.util.ToolUtilities;

import cson.core.CsonPojo;
import fe.cmn.data.BasicAbility;
import fe.cmn.data.PairDto;
import fe.cmn.panel.PanelContext;
import flutter.coder.annt.NullSafe;

// 查询子Editor，从而获取其当前值
public class QueryEditorValue extends BasicAbility<CsonPojo>
{
    private static final long serialVersionUID = 2036496216981358395L;
    
    @NullSafe
    String widgetId;
    
    /**
     * 是否只获取界面做过动作、修改后的值
     * 默认为False：获取界面最终的值，修改后的值和初始化的值二取其一
     * 如果为True：则只取界面做过修改的值，没修改过的应该为null
     */
    @NullSafe
    boolean onlyGuiValue = false;
    
    public String getWidgetId()
    {
        return widgetId;
    }

    public QueryEditorValue setWidgetId(String widgetId)
    {
        this.widgetId = widgetId;
        return this;
    }

    public boolean isOnlyGuiValue()
    {
        return onlyGuiValue;
    }

    public QueryEditorValue setOnlyGuiValue(boolean onlyGuiValue)
    {
        this.onlyGuiValue = onlyGuiValue;
        return this;
    }
    
    public static Object query(PanelContext ctx, String widgetId) throws Exception
    {
        return  query(ctx, false, widgetId);
    }
    
    public static Object query(PanelContext ctx, boolean onlyGuiValue, String widgetId) throws Exception
    {
        QueryEditorValue callback = new QueryEditorValue().setWidgetId(widgetId);
        callback.setOnlyGuiValue(onlyGuiValue);
        return  ctx.callback(callback);
    }
    

    public static String queryString(PanelContext ctx, String widgetId) throws Exception
    {
        return (String) query(ctx, widgetId);
    }
    public static double queryDouble(PanelContext ctx, String widgetId) throws Exception
    {
        return ToolUtilities.getDouble(query(ctx, widgetId));
    }
    public static long queryLong(PanelContext ctx, String widgetId) throws Exception
    {
        return ToolUtilities.getLong(query(ctx, widgetId));
    }
    public static int queryInt(PanelContext ctx, String widgetId) throws Exception
    {
        return ToolUtilities.getInteger(query(ctx, widgetId));
    }
    
    public static Object queryPairKey(PanelContext ctx, String widgetId) throws Exception
    {
        PairDto pair = (PairDto)query(ctx, widgetId);
        return pair==null?null:pair.getKey();
    }
}
