package fe.cmn.panel.ability;

import java.util.List;

import com.leavay.ms.tool.CmnUtil;

import fe.cmn.data.BasicAbility;
import fe.cmn.panel.PanelContext;
import fe.cmn.panel.PanelInfo;
import flutter.coder.annt.DefaultGetter;

// 查询父Panel列表，顺序按照：先父后子
public class QueryParentPanel extends BasicAbility<List<PanelInfo>>
{
    private static final long serialVersionUID = 2036496216981358395L;
    
    // 可以限定只获取哪些类型的panel信息
    String[] typeFilter;
    
    Boolean queryOne;
    
    /**
     * 返回的PanelInfo是否携带PanelContext。
     * 
     * <p>默认为false。
     */
    @DefaultGetter("false")
    Boolean bringPanelContext;

    public String[] getTypeFilter()
    {
        return typeFilter;
    }

    public QueryParentPanel setTypeFilter(String ... typeFilter)
    {
        this.typeFilter = typeFilter;
        return this;
    }

    public Boolean getQueryOne() {
		return queryOne;
	}

	public QueryParentPanel setQueryOne(Boolean queryOne) {
		this.queryOne = queryOne;
		return this;
	}

	public Boolean getBringPanelContext() {
		return bringPanelContext;
	}

	public QueryParentPanel setBringPanelContext(Boolean bringPanelContext) {
		this.bringPanelContext = bringPanelContext;
		return this;
	}

	public static List<PanelInfo> query(PanelContext ctx, String ... typeFilter) throws Exception
    {
        return  query(ctx, false, typeFilter);
    }
    
    public static PanelInfo queryOne(PanelContext ctx, String ... typeFilter) throws Exception
    {	
        return queryOne(ctx, false, typeFilter);
    }
    
    public static List<PanelInfo> query(PanelContext ctx, Boolean bringPanelContext, String ... typeFilter) throws Exception
    {
        QueryParentPanel callback = new QueryParentPanel().setTypeFilter(typeFilter).setBringPanelContext(bringPanelContext);
        return  (List<PanelInfo>) ctx.callback(callback);
    }
    
    public static PanelInfo queryOne(PanelContext ctx, Boolean bringPanelContext, String ... typeFilter) throws Exception
    {	
    	QueryParentPanel callback = new QueryParentPanel().setTypeFilter(typeFilter).setQueryOne(true).setBringPanelContext(bringPanelContext);
        List<PanelInfo> lst = (List<PanelInfo>) ctx.callback(callback);
        return CmnUtil.isObjectEmpty(lst)?null:lst.get(0);
    }
}
