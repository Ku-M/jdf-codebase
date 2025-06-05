package fe.cmn.panel.decoration;

import fe.cmn.data.CColor;
import fe.cmn.pojo.annotation.FieldDefine;
import fe.cmn.pojo.annotation.PojoMeta;
import fe.cmn.widget.decoration.DecorationDto;

// 分割布局样式
@PojoMeta(label = "分割视图样式", icon="res://images/units/split-h.png")
public class SplitViewDecorationDto extends DecorationDto {

	private static final long serialVersionUID = 724370447314005261L;
	
	public SplitViewDecorationDto() {
		
	}
	
	// 分割线颜色
	@FieldDefine(label = "分割线颜色")
	CColor dividerColor;
	
    @FieldDefine(label = "分割线宽度")
	Double dividerThickness;

	public CColor getDividerColor() {
		return dividerColor;
	}

	public SplitViewDecorationDto setDividerColor(CColor dividerColor) {
		this.dividerColor = dividerColor;
		return this;
	}

    public Double getDividerThickness()
    {
        return dividerThickness;
    }

    public SplitViewDecorationDto setDividerThickness(Double dividerThickness)
    {
        this.dividerThickness = dividerThickness;
        return this;
    }
}
