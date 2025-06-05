package fe.cmn.panel.ability;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.leavay.common.util.ToolUtilities;

import fe.cmn.data.BasicAbility;
import fe.cmn.data.ImageCompressDto;
import fe.cmn.data.PickFileType;
import fe.cmn.data.UploadFileListener;
import fe.cmn.data.UploadFileResult;
import fe.cmn.panel.PanelContext;
import fe.cmn.widget.UploadFileSizeLimitDto;
import flutter.coder.annt.DefaultGetter;

/**
 * 回调前端选择文件进行上传至服务端
 * 该动作会调用公共服务（IFileUploadService）将实际文件传递到后端并获得后端生成的RemoteFile
 * 
 * 然后前端将此RemoteFile作为该回调的返回对象给到调用端进行管理、销毁等
 * 
 * 1、后端某监听器调用该回调
 * 2、前端执行回调（选取文件，并调用后端接口上传到后端，并获得后端产生的临时文件信息RemoteFile）
 * 3、返回文件信息给调用者
 * 
 * IOS Web（高版本）要求在界面点击后短时间内触发才能成功调起文件选取，回调存在异步问题，因此推荐直接使用UploadFileDto组件。
 */
public class UploadFile extends BasicAbility<UploadFileResult>
{
 
	private static final long serialVersionUID = 7292489096131535248L;
	
	/**
	 * 可以指定一个目标上传的目录（服务端相对路径）
	 * 如果为空则由公共服务（IFileUploadService）自行决定暂存目录，默认为temp/XXXXXX（系统自动分配的文件名）
	 */
	String tempFolder;
	
	/**
	 * 指定暂存临时文件的前缀，如果不指定则由系统内定
	 */
	String tempFilePrefix;
	
	/**
	 * 是否允许多选
	 */
	boolean allowMultiple = false;
	
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
	 * 指定上传文件路径（web不可用）。
	 */
	List<String> filePaths;
	
	/**
	 * 是否静默方式（不会有等待、进度框等），默认false。
	 */
	@DefaultGetter("false")
	Boolean silence;
	
	/**
	 * 异步模式下，完成时调用此监听器，并传入执行结果
	 */
	protected UploadFileListener completeListener;
	
	/**
	 * 上传文件大小限制设置。
	 */
	UploadFileSizeLimitDto sizeLimit;
	
	/**
	 * 图片压缩设置。
	 */
	ImageCompressDto imageCompress;
	
	public UploadFile()
	{
	    super();
	    
	    setTimeout(120*60*1000);// 默认两小时
	}
	
	public static UploadFile specifiedFile(Boolean silence, List<String> filePath) {
		return new UploadFile().setFilePaths(filePath).setSilence(silence);
	}
	
	public static UploadFile specifiedFile(Boolean silence, String... filePath) {
		return new UploadFile().setFilePaths(filePath).setSilence(silence);
	}

    public boolean isAllowMultiple()
    {
        return allowMultiple;
    }


    public UploadFile setAllowMultiple(boolean allowMultiple)
    {
        this.allowMultiple = allowMultiple;
        return this;
    }

    public PickFileType getFileType()
    {
        return fileType;
    }

    public UploadFile setFileType(PickFileType fileType)
    {
        this.fileType = fileType;
        return this;
    }


    public String getTempFolder()
    {
        return tempFolder;
    }


    public UploadFile setTempFolder(String tempFolder)
    {
        this.tempFolder = tempFolder;
        return this;
    }


    public String getTempFilePrefix()
    {
        return tempFilePrefix;
    }


    public UploadFile setTempFilePrefix(String tempFilePrefix)
    {
        this.tempFilePrefix = tempFilePrefix;
        return this;
    }
    
    public List<String> getExtFilter()
    {
        return extFilter;
    }

    public UploadFile setExtFilter(List<String> extFilter)
    {
        this.extFilter = extFilter;
        return this;
    }
    
    public UploadFile setExtFilter(String ... extFilter)
    {
        this.extFilter = ToolUtilities.newArrayList(extFilter);
        return this;
    }

	public List<String> getFilePaths() {
		return filePaths;
	}

	public UploadFile setFilePaths(List<String> filePaths) {
		this.filePaths = filePaths;
		return this;
	}
	
	public UploadFile setFilePaths(String... filePath) {
		this.filePaths = Arrays.stream(filePath).collect(Collectors.toList());
		return this;
	}

	public Boolean getSilence() {
		return silence;
	}

	public UploadFile setSilence(Boolean silence) {
		this.silence = silence;
		return this;
	}

	public void setCompleteListener(UploadFileListener completeListener)
    {
        this.completeListener = completeListener;
    }

    public UploadFileSizeLimitDto getSizeLimit() {
		return sizeLimit;
	}

	public UploadFile setSizeLimit(UploadFileSizeLimitDto sizeLimit) {
		this.sizeLimit = sizeLimit;
		return this;
	}

	public ImageCompressDto getImageCompress() {
		return imageCompress;
	}

	public UploadFile setImageCompress(ImageCompressDto imageCompress) {
		this.imageCompress = imageCompress;
		return this;
	}

	public UploadFileResult action(PanelContext ctx)   throws Exception
    {
        return (UploadFileResult) ctx.callback(this);
    }
    
    // 异步执行，通过监听器接收结果
    public void asyncAction(PanelContext ctx, UploadFileListener onComplete) throws Exception
    {
        this.setCompleteListener(onComplete);
        ctx.callback(this);
    }

    /**
     * 如果返回NULL，说明行动取消了，什么都没做
     * 如果返回结果中userCanceled为true，说明过程中被取消，那么可能已经上传了部分文件会随着result返回，后端需自行清理垃圾残留
     * 如果出现前端异常，也会返回结果给后端，以便后端处理已经上传的残留垃圾
     */
    public static UploadFileResult upload(PanelContext ctx, String targetFolder)   throws Exception
    {
        UploadFile callback = new UploadFile();
        callback.setTempFolder(targetFolder);
        callback.setTimeout(120*60*1000);// 两小时
        return callback.action(ctx);
    }
}
