package fe.cmn.data;

// 用来标识界面设置为NULL，否则在有初始化值时，界面会认为GuiValue=null则自动取initValue返回
public class NullPojo extends FePojo
{
    private static final long serialVersionUID = -3036018172047066556L;
    
    public static boolean isNull(Object o)
    {
        return o == null || o instanceof NullPojo;
    }
    
}
