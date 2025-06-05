package fe.cmn.widget;

/**
 * 
 * 窗口比例大小：width及height取值均为 0 ~ 1
 *
 */
public class WindowSizeDto extends SizeDto {

	private static final long serialVersionUID = 3840862677849893567L;
	
	public WindowSizeDto() {
		
	}
	
	public WindowSizeDto(Double w, Double h) {
		setWidth(w).setHeight(h);
	}
	
	public static WindowSizeDto width(double d)
    {
        return new WindowSizeDto(d, null);
    }
    
    public static WindowSizeDto height(double h)
    {
        return new WindowSizeDto(null, h);
    }
    
    public static WindowSizeDto all(double d, double h)
    {
        return new WindowSizeDto(d, h);
    }
	
	@Override
	public WindowSizeDto setWidth(Double width)
    {
        return (WindowSizeDto) super.setWidth(width);
    }
	
	@Override
	public WindowSizeDto setHeight(Double height)
    {
		return (WindowSizeDto) super.setHeight(height);
    }
}
