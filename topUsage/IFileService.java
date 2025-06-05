package cell.fe;

import java.io.File;
import java.io.IOException;

import com.leavay.common.util.ToolUtilities;

import bap.cells.Cells;
import cell.ServiceCellIntf;
import fe.cmn.data.ByteArrayDto;
import fe.cmn.data.DownloadInterface;
import fe.cmn.data.FileInfo;
import fe.cmn.exception.NoResourceException;

/**
 * 提供远程文件访问的服务（主要提供给前端）
 *  
 * 例如前端通过读取服务端文件，进行下载到客户端的操作
 * 
 */
public interface IFileService extends ServiceCellIntf, DownloadInterface
{
    public static IFileService get(){ return Cells.get(IFileService.class);}
    

    public default long getResourceSize(String resource) throws NoResourceException
    {
        File fl = new File(resource);
        if (!fl.exists() || !fl.isFile())
           throw new NoResourceException(resource);
        
        return fl.length();
    }
    
    public default ByteArrayDto getResource(String resource) throws Exception
    {
        byte[] bt = ToolUtilities.readFile(resource);
        return new ByteArrayDto(bt);
    }
    
    public default ByteArrayDto getResourcePiece(String resource, long offset, long length) throws Exception
    {
        byte[] bt = ToolUtilities.readFile(resource, (int)offset, (int)length);
        return new ByteArrayDto(bt);
    }
    
    public default FileInfo getFileInfo(String serverPath) throws IOException
    {
        File fl = new File(serverPath);
        if (!fl.exists())
            return null;
        
        FileInfo info = new FileInfo();
        info.setName(fl.getName());
        info.setFullPath(fl.getCanonicalPath());
        info.setDir(fl.isDirectory());
        if (fl.isFile())
            info.setLength(fl.length());
        
        return info;
    }
    
    public default boolean isReadableFile(String serverPath)
    {
        File fl = new File(serverPath);
        return fl.exists() &&  fl.isFile() && fl.canRead();
    }
    
}