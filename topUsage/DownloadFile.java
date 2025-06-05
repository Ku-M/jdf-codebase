package fe.cmn.panel.ability;

import java.util.List;

import com.leavay.common.util.ToolUtilities;

import fe.cmn.data.BasicAbility;
import fe.cmn.data.DownloadFileResult;
import fe.cmn.data.DownloadInterface;
import fe.cmn.data.FileStorageDirectory;
import fe.cmn.data.PickFileType;
import fe.cmn.panel.PanelContext;
import flutter.coder.annt.DefaultGetter;
import flutter.coder.annt.NullReturn;
import flutter.coder.annt.NullSafe;

/**
 * 回调前端下载文件到本地，选择客户端目标文件路径，并开始下载
 * 该动作会调用公共服务（DownloadInterface）将实际文件(或二进制资源）传递到客户端并保存
 * 
 * 【注】：此动作不等待前端操作完成，触发即返回
 */
public class DownloadFile extends BasicAbility<DownloadFileResult>
{
 
	private static final long serialVersionUID = 7292489096131535248L;
	
	/**
     * 指定提供下载资源的服务，可以是文件也可以是任何自定义服务，只要能提供二进制资源供下载即可 （实现DownloadInterface）
	 */
    @NullSafe
	String downloadService;
	
	/**
	 * 要下载的资源全路径，可以是文件路径、资源URL等等都可以，只要服务认识即可
	 */
	@NullSafe
	String resourcePath;
	
	/**
	 * 默认保存文件名（选取文件时的提示默认文件名）
	 */
	String fileName;
	
	/**
	 * 如果指定了保存到客户端本地文件路径名，则不会弹出选择文件、目录的界面，直接保存
	 */
	String saveToLocalFile;
	
	/**
	 * 如果指定了保存到客户端本地文件路径名，则此字段决定其目录。
	 * 默认为外部存储目录或者应用目录。
	 */
	@DefaultGetter("FileStorageDirectory.EXTERNAL_OR_APP_DIRECTORY")
	FileStorageDirectory saveDirectory;
	
	/**
	 * 指定文件类型，默认any
	 * 如果要限定扩展名，那么fileType必须指定为PickFileType.custom
	 */
	PickFileType fileType;
	
	/**
	 * 指定扩展名过滤器
	 */
	List<String> extFilter;
	
	/**
	 * 指定分页字节数（不填默认：64K=64*1024B）
	 * 超过分页字节数，则会分页下载，否则一次性下载
	 */
	long pageSize;

	/**
	 * 是否静默方式（不会有等待、进度框等），默认false
	 */
	boolean silence;
	
	public DownloadFile()
	{
	    super();
	}


    public PickFileType getFileType()
    {
        return fileType;
    }

    public DownloadFile setFileType(PickFileType fileType)
    {
        this.fileType = fileType;
        return this;
    }


    
    public String getDownloadService()
    {
        return downloadService;
    }


    public DownloadFile setDownloadService(Class<? extends DownloadInterface> downloadService)
    {
        this.downloadService = downloadService.getName();
        return this;
    }

    public String getResourcePath()
    {
        return resourcePath;
    }

    public DownloadFile setResourcePath(String resourcePath)
    {
        this.resourcePath = resourcePath;
        return this;
    }


    public String getFileName()
    {
        return fileName;
    }

    public DownloadFile setFileName(String fileName)
    {
        this.fileName = fileName;
        return this;
    }

    public List<String> getExtFilter()
    {
        return extFilter;
    }

    public DownloadFile setExtFilter(List<String> extFilter)
    {
        this.extFilter = extFilter;
        return this;
    }
    
    public DownloadFile setExtFilter(String ... extFilter)
    {
        this.extFilter = ToolUtilities.newArrayList(extFilter);
        return this;
    }

    public long getPageSize()
    {
        return pageSize;
    }

    public DownloadFile setPageSize(long pageSize)
    {
        this.pageSize = pageSize;
        return this;
    }

    public String getSaveToLocalFile()
    {
        return saveToLocalFile;
    }

    public DownloadFile setSaveToLocalFile(String saveToLocalFile)
    {
        this.saveToLocalFile = saveToLocalFile;
        return this;
    }

    public FileStorageDirectory getSaveDirectory() {
		return saveDirectory;
	}

	public DownloadFile setSaveDirectory(FileStorageDirectory saveDirectory) {
		this.saveDirectory = saveDirectory;
		return this;
	}

	public boolean isSilence()
    {
        return silence;
    }

    public DownloadFile setSilence(boolean silence)
    {
        this.silence = silence;
        return this;
    }

    @NullReturn
    public static DownloadFileResult start(PanelContext ctx, Class<? extends DownloadInterface> service, String resourcePath, String fileName)   throws Exception
    {
        DownloadFile callback = new DownloadFile();
        callback.setDownloadService(service).setResourcePath(resourcePath);
        callback.setFileName(fileName);
        return callback.execute(ctx);
    }
    
    // 
    /**
     * 直接下载到指定文件（saveToLocalFile是默认存到Application目录下的相对目录）
     * 
     * @param saveToLocalFile : 要保存到客户端的路径，是在Application目录下的相对路径（且Web下暂不支持）
     */
    @NullReturn
    public static DownloadFileResult pushToLocal(PanelContext ctx, Class<? extends DownloadInterface> service, String resourcePath, String saveToLocalFile)   throws Exception
    {
        return pushToLocal(ctx, service, resourcePath, saveToLocalFile, false, 2*60*1000);
    }
    
    public static DownloadFileResult pushToLocal(PanelContext ctx, Class<? extends DownloadInterface> service, String resourcePath, String saveToLocalFile, boolean silence, long timeout) throws Exception
    {
    	return pushToLocal(ctx, service, resourcePath, saveToLocalFile, silence, timeout, FileStorageDirectory.EXTERNAL_OR_APP_DIRECTORY);
    }
    
    public static DownloadFileResult pushToLocalTemp(PanelContext ctx, Class<? extends DownloadInterface> service, String resourcePath, String saveToLocalFile, boolean silence, long timeout) throws Exception
    {
    	return pushToLocal(ctx, service, resourcePath, saveToLocalFile, silence, timeout, FileStorageDirectory.TEMP_DIRECTORY);
    }
    
    public static DownloadFileResult pushToLocal(PanelContext ctx, Class<? extends DownloadInterface> service, String resourcePath, String saveToLocalFile, boolean silence, long timeout, FileStorageDirectory saveDirectory) throws Exception
    {
        DownloadFile callback = new DownloadFile();
        callback.setDownloadService(service).setResourcePath(resourcePath);
        callback.setSaveToLocalFile(saveToLocalFile);
        callback.setSaveDirectory(saveDirectory);
        
        callback.setSilence(silence);
        callback.setTimeout(timeout);
        return callback.execute(ctx);
    }
}
