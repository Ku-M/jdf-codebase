package fe.cmn.table;

import java.util.List;

import com.leavay.common.util.ToolBasic;

import fe.cmn.data.FePojo;

/**
 * 构建
 * @author Cenke Cheng
 * 2022年2月11日
 * 
 *
 */
public class TableBuilder extends FePojo
{
    private static final long serialVersionUID = -1443792424624506681L;

    // 设置屏蔽字段
    List<String> columnFilter;

    public List<String> getColumnFilter()
    {
        return columnFilter;    
    }

    public TableBuilder setColumnFilter(String ... columnFilter)
    {
        this.columnFilter = ToolBasic.newArrayList(columnFilter);
        return this;
    }

    public TableBuilder setColumnFilter(List<String> columnFilter)
    {
        this.columnFilter = columnFilter;
        return this;
    }
    
    public boolean isColumnIgnore(String col)
    {
        return columnFilter!= null && columnFilter.contains(col);
    }

    public TableBuilder setSelfBinaryData() {
        setBinaryDataIgnoreErr(this);
        return this;
    }
}
