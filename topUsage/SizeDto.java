package fe.cmn.widget;

import cson.core.CsonPojo;
import flutter.coder.annt.FlutterCode;

@FlutterCode("\tSizeDto.build(this.width, this.height) {setObjectType(JAVA_TYPE);}")
public class SizeDto extends CsonPojo
{
    private static final long serialVersionUID = -4371359199373550317L;

    Double width;
    
    Double height;
    
    public SizeDto()
    {
        
    }
    
    public SizeDto(Double w, Double h)
    {
        setWidth(w).setHeight(h);
    }
    
    public static SizeDto width(double d)
    {
        return new SizeDto(d, null);
    }
    
    public static SizeDto height(double h)
    {
        return new SizeDto(null, h);
    }
    
    public static SizeDto all(double w, double h)
    {
        return new SizeDto(w, h);
    }

    public Double getWidth()
    {
        return width;
    }

    public SizeDto setWidth(Double width)
    {
        this.width = width;
        return this;
    }

    public Double getHeight()
    {
        return height;
    }

    public SizeDto setHeight(Double height)
    {
        this.height = height;
        return this;
    }
    
}
