package fe.cmn.table;

import fe.cmn.panel.PanelContext;

/**
 * 表容器上下文(前端传递给后端的)
 *
 * 附带一些基本的前端状态，如当前点中的行、列（点中未必代表选中，选中还可能很多）
 * 只传递必要信息，不可过多，因为非必要信息浪费带宽，后端会根据逻辑需要自行回访抓取更详细的信息
 */
public class TableContext extends PanelContext
{

    private static final long serialVersionUID = 5221826383870851L;

    // 这是前端当前排序后的索引编号，仅用于到前端查询行等操作
    int currentRowIdx = -1;
    
    // 当前选中行id
    String currentRowId;

    // 当前选中列名
    String currentColumnName;
    
    int selectCount = 0;
    
    // 当前hover触发的行id
    String currentHoverRowId;
    
    // 当前hover触发的行索引
    Integer currentHoverRowIdx;
    
    /**
     * 当前页码
     */
    Integer currentPage;
    
    /**
     * 当前单页数量
     */
    Integer pageSize;
    
    public TableContext() {
    	
    }

    public int getCurrentRowIdx()
    {
        return currentRowIdx;
    }

    public void setCurrentRowIdx(int currentRowIdx)
    {
        this.currentRowIdx = currentRowIdx;
    }

    public String getCurrentRowId()
    {
        return currentRowId;
    }

    public void setCurrentRowId(String currentRowId)
    {
        this.currentRowId = currentRowId;
    }

    public String getCurrentColumnName()
    {
        return currentColumnName;
    }

    public void setCurrentColumnName(String currentColumnName)
    {
        this.currentColumnName = currentColumnName;
    }

    public int getSelectCount()
    {
        return selectCount;
    }

    public void setSelectCount(int selectCount)
    {
        this.selectCount = selectCount;
    }

    public String getCurrentHoverRowId() {
		return currentHoverRowId;
	}

	public Integer getCurrentHoverRowIdx() {
		return currentHoverRowIdx;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public String toString()
    {
        return String.format("Row:%s, Column:%s [%s-%s]", getCurrentRowId(), getCurrentColumnName(), getCurrentPanelGlobalKey(), getCurrentPanelWidgetId());
    }
}
