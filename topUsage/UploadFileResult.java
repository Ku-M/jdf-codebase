package fe.cmn.data;

import java.util.LinkedList;
import java.util.List;

import com.leavay.common.util.ToolUtilities;
import com.leavay.ms.tool.CmnUtil;

/**
 * 前端上传文件的结果
 * 
 * 当用户选择中途cancel，那么已经上传的文件（残留）需要后端清理，所以也会放在lstFiles中传回后端
 */
public class UploadFileResult extends FileResult
{
    private static final long serialVersionUID = 7167170702417311510L;

    List<BeFile> lstFiles;
    
    /**
     * 超出大小限制文件。
     */
    List<FileInfo> exceedSizeLimitFiles;

    public List<BeFile> getLstFiles()
    {
        return lstFiles;
    }
    
	public List<FileInfo> getExceedSizeLimitFiles() {
		return exceedSizeLimitFiles;
	}
	
	public boolean isExceedSizeLimit() {
		return !exceedSizeLimitFiles.isEmpty();
	}
	
	public List<String> getExceedSizeLimitFileNames() {
		List<String> exceedSizeLimitFileNames = new LinkedList<String>();
		
		for(FileInfo fileInfo : exceedSizeLimitFiles) {
			String fileName = fileInfo.getName();
			exceedSizeLimitFileNames.add(fileName);
		}
		
		return exceedSizeLimitFileNames;
	}
	
	@Override
	public boolean isSucceed()
    {
        return !isExceedSizeLimit() && !userCanceled && CmnUtil.isStringEmpty(getException());
    }
	
	@Override
    public String toString()
    {	
		String lst = ToolUtilities.logString(lstFiles, false);
		
		if (!exceedSizeLimitFiles.isEmpty()) {
			String exceedSizeLimitFilesLog = ToolUtilities.logString(exceedSizeLimitFiles, false);
			String log = "Exceed size limit files: " + exceedSizeLimitFilesLog + ".";
			
			if(!lstFiles.isEmpty()) {
				log += "\nUploaded files: " + lst + ".";
			}
			
			return log;
		}
        
        if (isSucceed())
            return "File List : " + lst + ".";

        if (isUserCanceled())
            return "User canceled. Rubbish Files : " + lst + ".";
        else
            return "Exception : " + getException() + ". Rubbish Files : " + lst + ".";
    }
}
