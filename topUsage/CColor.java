package fe.cmn.data;

import java.awt.Color;

import cson.core.CsonPojo;
import flutter.coder.annt.FlutterCode;
import flutter.coder.annt.NullSafe;

@FlutterCode("CColor.build(this.r, this.g, this.b, this.opacity) {setObjectType(JAVA_TYPE);}")
public class CColor extends CsonPojo
{
    private static final long serialVersionUID = 3556007450911826057L;

    @NullSafe(initCode = "0")
    int r = 0;
    
    @NullSafe(initCode = "0")
    int g = 0;

    @NullSafe(initCode = "0")
    int b=0;
    
    // 0-1的小数，代表透明度
    @NullSafe(initCode = "1")
    float opacity=1;
    
    public CColor(){
        
    }
    
    public CColor(int r, int g, int b, float opacity)
    {
        setR(r);
        setG(g);
        setB(b);
        setOpacity(opacity);
    }
    
    public static CColor fromColor(Color color)
    {
        CColor c = new CColor().setR(color.getRed()).setG(color.getGreen()).setB(color.getBlue());
        c.setOpacity((float)color.getAlpha()/255);
        return c;
    }
    
    public Color toColor()
    {
        return new Color(getR(), getG(), getB(), (int)getOpacity()*255);
    }
    
    public static CColor rgba(int r, int g, int b, int alpha)
    {
        return new CColor().setR(r).setG(g).setB(b).setOpacity((float)alpha/255);
    }
    
    public static CColor transparent() {
    	return CColor.rgba(0,0,0,0);
    }

    public int getR()
    {
        return r;
    }

    public CColor setR(int r)
    {
        this.r = r;
        return this;
    }

    public int getG()
    {
        return g;
    }

    public CColor setG(int g)
    {
        this.g = g;
        return this;
    }

    public int getB()
    {
        return b;
    }

    public CColor setB(int b)
    {
        this.b = b;
        return this;
    }

    public float getOpacity()
    {
        return opacity;
    }

    public CColor setOpacity(float opacity)
    {
        this.opacity = opacity;
        return this;
    }
    
    public String toString()
    {
        return r+","+g+","+b+"("+opacity+")";
    }
    
}
