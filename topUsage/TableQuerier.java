package fe.cmn.table;

import fe.cmn.data.FePojo;

public class TableQuerier extends FePojo
{
    private static final long serialVersionUID = -1443792424624506681L;

    // 分页信息，负数表示不分页
    int startPos=-1;
    int pageSize=-1;
    
    /**
     * 是否查询总数
     */
    boolean queryCount=false;
    
    public TableQuerier() {}
    
    public TableQuerier(int startPos, int pageSize) {
    	this.startPos = startPos;
    	this.pageSize = pageSize;
    }
    
    public TableQuerier(int startPos, int pageSize, boolean queryCount) {
    	this.startPos = startPos;
    	this.pageSize = pageSize;
    	this.queryCount = queryCount;
    }
    
    public boolean isMultiPage() {
        return getStartPos() >=0 && getPageSize() > 0;
    }

    public int getStartPos()
    {
        return startPos;
    }

    public TableQuerier setStartPos(int startPos)
    {
        this.startPos = startPos;
        return this;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public TableQuerier setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
        return this;
    }

    public boolean isQueryCount()
    {
        return queryCount;
    }

    public TableQuerier setQueryCount(boolean queryCount)
    {
        this.queryCount = queryCount;
        return this;
    }

    public TableQuerier setSelfBinaryData() {
        setBinaryDataIgnoreErr(this);
        return this;
    }
}
