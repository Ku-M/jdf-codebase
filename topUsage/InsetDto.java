package fe.cmn.widget;

import cson.core.CsonPojo;
import flutter.coder.annt.FlutterCode;

// 用来指定边距，可以不指定，也可都指定
// 不指定则不填
@FlutterCode("@override\n\tbool operator ==(Object other){return other is InsetDto && other.left == left && other.right == right && other.top == top && other.bottom == bottom;}")
public class InsetDto extends CsonPojo
{
    private static final long serialVersionUID = -2055900684298967054L;
    
    Double left;
    Double top;
    Double right;
    Double bottom;
    
    public InsetDto(Double left, Double top, Double right, Double bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }
    
    public InsetDto() {
    }

    
    public Double getLeft()
    {
        return left;
    }
    public InsetDto setLeft(double left)
    {
        this.left = left;
        return this;
    }
    public Double getTop()
    {
        return top;
    }
    public InsetDto setTop(double top)
    {
        this.top = top;
        return this;
    }
    public Double getRight()
    {
        return right;
    }
    public InsetDto setRight(double right)
    {
        this.right = right;
        return this;
    }
    public Double getBottom()
    {
        return bottom;
    }
    public InsetDto setBottom(double bottom)
    {
        this.bottom = bottom;
        return this;
    }
    
    public static InsetDto all(double allInset)
    {
        return new InsetDto().setLeft(allInset).setRight(allInset).setTop(allInset).setBottom(allInset);
    }
    
    public static InsetDto symmetric(double horizontal, double vertical) {
    	return new InsetDto().setLeft(horizontal).setRight(horizontal).setTop(vertical).setBottom(vertical);
    }
    
    public static InsetDto leftRight(double allInset)
    {
        return new InsetDto().setLeft(allInset).setRight(allInset);
    }
    
    public static InsetDto topBottom(double allInset)
    {
        return new InsetDto().setTop(allInset).setBottom(allInset);
    }
}
