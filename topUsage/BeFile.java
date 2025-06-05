package fe.cmn.data;

import java.io.File;
import java.io.IOException;

import com.leavay.ms.tool.CmnUtil;

import flutter.coder.annt.NullSafe;

/**
 * 后端文件，后端实地存储，并分配UUID等
 * 
 * 记录文件的真实存储路径（完整含文件名）
 * 共识key
 */
public class BeFile extends FeFile
{

    private static final long serialVersionUID = 4365573771843095554L;
    
    // 共识Key（前后端都认的唯一标识，通常是由构造端自动分配的UUID）
    @NullSafe
    String key = CmnUtil.allocUUIDWithUnderline();
    
    // 文件存储路径
    String storPath;
    
    public String getKey()
    {
        return key;
    }

    public BeFile setKey(String key)
    {
        this.key = key;
        return this;
    }

    public String getStorPath()
    {
        return storPath;
    }

    public BeFile setStorPath(String storPath)
    {
        this.storPath = storPath;
        return this;
    }
    
    public String toString()
    {
        return getName()+"("+getStorPath()+")";
    }
    
    public static BeFile fromFile(File fl) throws IOException
    {
        BeFile beFile = new BeFile();
        beFile.setName(fl.getName());
        beFile.setFullPath(fl.getCanonicalPath());
        if (fl.isFile())
        {
            beFile.setLength(fl.length());
        }else
            beFile.setDir(false);
        return beFile;
    }
}
