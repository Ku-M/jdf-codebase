package fe.cmn.table;

import java.util.List;

import cson.core.CsonPojo;
import com.leavay.common.util.ToolUtilities;

public class TableRowsDto  extends CsonPojo
{
    private static final long serialVersionUID = 6236169838362764638L;

    List<TableRowDto> rows;
    
    // 当前分页起始位置
    int pageStart = 0;
    
    // 全表总行数，用于计算分页（<0表示无总行数信息）
    int totalRows = -1;
    
    public TableRowsDto() {}
    
    public TableRowsDto(List<TableRowDto> rows) {
     this.rows = rows;
    }
    
    public TableRowsDto(List<TableRowDto> rows, int pageStart, int totalRows) {
     this.rows = rows;
     this.pageStart = pageStart;
     this.totalRows = totalRows;
    }
    
    public TableRowsDto(TableRowDto ... rows) {
     this.rows = ToolUtilities.array2List(rows);
    }

    public List<TableRowDto> getRows()
    {
        return rows;
    }

    public TableRowsDto setRows(List<TableRowDto> rows)
    {
        this.rows = rows;
        return this;
    }

    public int getPageStart()
    {
        return pageStart;
    }

    public TableRowsDto setPageStart(int pageStart)
    {
        this.pageStart = pageStart;
        return this;
    }

    public int getTotalRows()
    {
        return totalRows;
    }

    public TableRowsDto setTotalRows(int totalRows)
    {
        this.totalRows = totalRows;
        return this;
    }
    
    
}
