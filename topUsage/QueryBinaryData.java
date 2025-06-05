package fe.cmn.panel.ability;

import java.util.HashMap;
import java.util.Map;

import com.leavay.common.util.javac.ClassFactory;

import fe.cmn.data.BasicAbility;
import fe.cmn.data.BinPojo;
import fe.cmn.panel.PanelContext;

/**
 * 查询面板下符合规则的widget的二进制数据
 * 
 * 可以通过正则表达式匹配widgetID，也可以通过给定widgetID列表（两者都填则是或关系）
 * 
 */
public class QueryBinaryData extends BasicAbility<Map<String, Object>>
{
    private static final long serialVersionUID = 2036496216981358395L;

    // 正则表达式匹配Widget ID，如："(ROW_ID:){1}" 匹配开头为ROW_ID:的数据
    String widgetIdRegEx;

    // 直接指定需要的widgetID列表
    String[] widgetIds;

    public String getWidgetIdRegEx()
    {
        return widgetIdRegEx;
    }

    public QueryBinaryData setWidgetIdRegEx(String widgetIdRegEx)
    {
        this.widgetIdRegEx = widgetIdRegEx;
        return this;
    }

    public String[] getWidgetIds()
    {
        return widgetIds;
    }

    public void setWidgetIds(String[] widgetIds)
    {
        this.widgetIds = widgetIds;
    }
    
    public static Object queryOne(PanelContext ctx) throws Exception
    {
        Map<String, Object> map = query(ctx, null, null);
        return map == null ? null : (map.keySet().isEmpty() ? null : map.get(map.keySet().toArray()[0]));
    }

    public static Object queryOne(PanelContext ctx, String widgetId) throws Exception
    {
        Map<String, Object> map = query(ctx, null, widgetId);
        return map == null ? null : map.get(widgetId);
    }

    public static Map<String, Object> query(PanelContext ctx, String regularExp, String... widgetIds) throws Exception
    {
        QueryBinaryData callback = new QueryBinaryData().setWidgetIdRegEx(regularExp);
        callback.setTimeout(120*1000);
        
        callback.setWidgetIds(widgetIds);
        Map<String, Object> mapRet = new HashMap<String, Object>();
        Map<String, BinPojo> mapByte = (Map<String, BinPojo>) ctx.callback(callback);
        
        for (String s : mapByte.keySet())
        {
            BinPojo bt = mapByte.get(s);
            if (bt == null || !bt.hasBinaryData())
                mapRet.put(s, null);
            else
            {
                mapRet.put(s, ClassFactory.unserialize(bt.getBinaryBytes()));
            }
        }
        return mapRet;
    }
    
    public static byte[] queryOneBytes(PanelContext ctx) throws Exception
    {
        Map<String, byte[]> map = queryBytes(ctx, null, null);
        return map == null ? null : (map.keySet().isEmpty() ? null : map.get(map.keySet().toArray()[0]));
    }
    
    public static byte[] queryOneBytes(PanelContext ctx, String widgetId) throws Exception
    {
        Map<String, byte[]> map = queryBytes(ctx, null, widgetId);
        return map == null ? null : map.get(widgetId);
    }
    
    public static Map<String, byte[]> queryBytes(PanelContext ctx, String regularExp, String... widgetIds) throws Exception
    {
        QueryBinaryData callback = new QueryBinaryData().setWidgetIdRegEx(regularExp);
        callback.setTimeout(120*1000);
        
        callback.setWidgetIds(widgetIds);
        Map<String,  byte[]> mapRet = new HashMap<String,  byte[]>();
        Map<String, BinPojo> mapByte = (Map<String, BinPojo>) ctx.callback(callback);
        
        for (String s : mapByte.keySet())
        {
            BinPojo bt = mapByte.get(s);
            if (bt == null || !bt.hasBinaryData())
                mapRet.put(s, null);
            else
            {
                mapRet.put(s, bt.getBinaryBytes());
            }
        }
        return mapRet;
    }
}
