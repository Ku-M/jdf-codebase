package fe.cmn.panel;

import com.leavay.ms.tool.CmnUtil;

import cson.core.CsonPojo;

// 一般用于前端返回数据给后端
public class PanelInfo extends CsonPojo
{
    private static final long serialVersionUID = 8342174102825126468L;

    // GlobalKey
    String panelGlobalKey;
    
    // 当前Panel的WidgetId（用于值搜集、获取）
    String panelWidgetId;
    
    // Panel的DTO类名
    String panelClass;
    
    // 父Panel的全局Key
    String parentGlobalKey;
    
    // 当前面板PanelContext
    PanelContext panelContext;

    public String getPanelGlobalKey()
    {
        return panelGlobalKey;
    }

    public void setPanelGlobalKey(String panelGlobalKey)
    {
        this.panelGlobalKey = panelGlobalKey;
    }

    public String getPanelWidgetId()
    {
        return panelWidgetId;
    }

    public void setPanelWidgetId(String panelWidgetId)
    {
        this.panelWidgetId = panelWidgetId;
    }

    public String getPanelClass()
    {
        return panelClass;
    }

    public void setPanelClass(String panelClass)
    {
        this.panelClass = panelClass;
    }

    public String getParentGlobalKey()
    {
        return parentGlobalKey;
    }

    public void setParentGlobalKey(String parentGlobalKey)
    {
        this.parentGlobalKey = parentGlobalKey;
    }

    public PanelContext getPanelContext() {
		return panelContext;
	}

	public PanelInfo setPanelContext(PanelContext panelContext) {
		this.panelContext = panelContext;
		return this;
	}

	public PanelContext toContext()
    {
        PanelContext ctx = new PanelContext();
        ctx.setCurrentPanelGlobalKey(getPanelGlobalKey());
        ctx.currentPanelClass = getPanelClass();
        ctx.currentPanelWidgetId = getPanelWidgetId();
        return ctx;
    }
    
    public String toString()
    {
        return getPanelClass()+"="+CmnUtil.getNameAndLabel(getPanelGlobalKey(), getPanelWidgetId());
    }
    
}
