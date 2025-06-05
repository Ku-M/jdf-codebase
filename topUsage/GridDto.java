package fe.cmn.panel;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.leavay.common.util.ToolUtilities;

import fe.cmn.pojo.annotation.FieldDefine;
import fe.cmn.pojo.annotation.PojoMeta;
import fe.cmn.widget.WidgetDto;
import flutter.coder.annt.NullSafe;

/**
 * 单元格布局
 * 
 * <p>按Excel类似的方式，划分基础单元格，然后可以按给定区域进行空间布局
 * 
 * <p>有两种方式，选一种即可： 一、给单元格命名，然后按名字对应组件的方式布局，需填areas、areaMap两个字段
 * 二、按行列坐标、占宽来布局组件，需填blocks字段
 * 
 * <p>需要注意的是行列尺寸约束都必须填，且数量必须和相应占位设定匹配，例如area矩阵中行列个数需与rows、columns个数匹配
 * 
 * <p>rows、columns中填写行列尺寸约束， 例如：columns= GridSize.fix(120), GridSize.auto,
 * GridSize.flex(1), GridSize.flex(2)
 * 代表第一列固定宽度120像素、第二列自动（按组件固有尺寸）、第三列按1个单位弹性分配、第四列按2个单位弹性分配
 * 
 * <p>【注】：auto的使用需非常谨慎，只适用于有固定尺寸的组件，如label、按钮等，如果遇到弹性组件如列表、滚动容器等会出无法确定尺寸的布局异常
 */
@PojoMeta(label="单元格布局", icon="res://images/units/grid.png")
public class GridDto extends SpecialLayoutDto
{

    private static final long serialVersionUID = 3703678711495953562L;

    // 不命名，仅用于areas中设定某格留空
    public final static String NO_NAME = ".";

    // 按名字划分区域（如果按行列索引分配，则可不填该字段）
    @FieldDefine(visible = false) // 由布局器控制
    public String[][] areas;

    // 配合areas，以名字安排组件位置
    @FieldDefine(visible = false) // 由布局器控制
    public Map<String, GridCell> areaMap;

    // 按行列索引安排组件，可重叠覆盖（填了这个就无需填areas、areaMap）
    @FieldDefine(visible = false) // 由布局器控制
    public List<GridBlock> blocks;

    // 列设定，例如：150.px，auto， 5.fr, 10.fr
    @NullSafe
    @FieldDefine(visible = false) // 由布局器控制
    public List<GridSize> columns;

    // 行设定，例如：5.fr, 5.fr, auto
    @NullSafe
    @FieldDefine(visible = false) // 由布局器控制
    public List<GridSize> rows;

    // 行间隔
    Double rowGap;

    // 列间隔
    Double columnGap;

    public String[][] getAreas()
    {
        return areas;
    }

    public GridDto setAreas(String[][] areas)
    {
        this.areas = areas;
        return this;
    }

    public Map<String, GridCell> getAreaMap()
    {
        return areaMap;
    }

    public GridDto setAreaMap(Map<String, GridCell> areaMap)
    {
        this.areaMap = areaMap;
        return this;
    }

    public GridDto setArea(String area, WidgetDto wgt) {
        return setArea(area, GridCell.wrap(wgt));
    }

    public GridDto setArea(String area, GridCell wgt)
    {
        if (areaMap == null)
            areaMap = new HashMap<String, GridCell>();

        areaMap.put(area, wgt);

        return this;
    }

    public List<GridBlock> getBlocks()
    {
        return blocks;
    }

    public GridDto setBlocks(List<GridBlock> blocks)
    {
        this.blocks = blocks;
        return this;
    }

    public GridDto addBlock(GridBlock... blocks)
    {
        for (GridBlock blk : blocks)
            this.blocks = ToolUtilities.addToList(this.blocks, blk);

        return this;
    }

    public List<GridSize> getColumns()
    {
        return columns;
    }

    public GridDto setColumns(GridSize... columns)
    {
        this.columns = ToolUtilities.newArrayList(columns);
        return this;
    }

    public GridDto setColumns(List<GridSize> columns)
    {
        this.columns = columns;
        return this;
    }

    public List<GridSize> getRows()
    {
        return rows;
    }
    
    public GridDto addRow(GridSize gridSize)
    {
        if (rows == null)
            rows = new LinkedList<GridSize>();
        rows.add(gridSize);
        return this;
    }
    
    public GridDto setRows(GridSize... rows)
    {
        this.rows = ToolUtilities.newArrayList(rows);
        return this;
    }

    public GridDto setRows(List<GridSize> rows) {
        this.rows = rows;
        return this;
    }

    public Double getRowGap()
    {
        return rowGap;
    }

    public GridDto setRowGap(Double rowGap)
    {
        this.rowGap = rowGap;
        return this;
    }

    public Double getColumnGap()
    {
        return columnGap;
    }
    
    public GridDto addColumn(GridSize gridSize)
    {
        if (columns == null)
            columns = new LinkedList<GridSize>();
        columns.add(gridSize);
        return this;
    }

    public GridDto setColumnGap(Double columnGap)
    {
        this.columnGap = columnGap;
        return this;
    }

    public static GridDto empty(int row, int column)
    {
        GridDto g = new GridDto();
        for (int r =0; r< row; r++)
            g.addRow(new GridSize().setType(GridSizeType.flex).setValue(1));
        for (int c =0; c< column; c++)
            g.addColumn(new GridSize().setType(GridSizeType.flex).setValue(1));
        
        return g;
        
    }
}
