package fe.util;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.cdao.model.type.Password;
import com.kwaidoo.ms.tool.CmnUtil;
import com.leavay.common.nio.ws.cache.InvalidCacheException;
import com.leavay.common.util.MppContext;
import com.leavay.common.util.ToolUtilities;

import cell.cmn.io.IFiles;
import cell.fe.IFileService;
import cell.fe.cmn.IFeI18nPlugin;
import cell.fe.example.IStudyWxMini.ShowWaitMask;
import cell.nio.ws.IWsCallbackChannel;
import cn.hutool.core.io.FileUtil;
import fe.cmn.app.ability.QueryDeviceInfo;
import fe.cmn.data.BeFile;
import fe.cmn.data.DeviceInfoDto;
import fe.cmn.data.PickFileType;
import fe.cmn.data.PickMediaFileType;
import fe.cmn.data.UploadFileResult;
import fe.cmn.panel.PanelContext;
import fe.cmn.panel.ability.DownloadFile;
import fe.cmn.panel.ability.UploadFile;
import fe.cmn.panel.ability.UploadMediaFile;
import fe.cmn.weixin.ability.WxMiniCommand;
import fe.cmn.weixin.mini.TempHttpFile;
import fe.cmn.weixin.mini.WxHttpFileService;
import fe.util.enums.FileType;
import fe.util.i18n.FeI18n;

public class FeFileUtil {
	
	private static String getI18nString(PanelContext panelContext,String key) throws Exception {
		return IFeI18nPlugin.get().getI18nString(panelContext, key);
	}
	/**
	 * 模拟文件上传操作
	 * @param fileName
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static BeFile simulateUploadFile(String fileName,byte[] content) throws Exception {
		BeFile file = new BeFile();
		file.setName(fileName);
		//如果初始附件已经是内存里的字节数组，那么要先上传文件
		file.setBytes(content);
		file.setLength(content.length);
		file.setStorPath("./temp/Upload/TempUpload"+ToolUtilities.allockUUIDWithUnderline());
		IFiles.get().saveFile(file.getStorPath(), file.getBytes(), false);
		return file;
	}
	/**
	 * 文件名中包含中文时进行URL编码
	 * @param fileName
	 * @throws UnsupportedEncodingException 
	 */
	public static String encodeFileName(String fileName) throws UnsupportedEncodingException {
//		String regex = ".*[\u4e00-\u9fa5]+.*";
//		if(fileName.matches(regex)) {
//			int index = fileName.lastIndexOf(".");
//			if(index > -1) {
//				String tmpFileName = fileName.substring(0, index);
//				tmpFileName = fileName.substring(0, index);
//				tmpFileName = URLEncoder.encode(tmpFileName, "UTF-8");
////				tmpFileName = ToolUtilities.allockUUIDWithUnderline();
//				String suffix = fileName.substring(index);
//				tmpFileName = tmpFileName + suffix;
//				return tmpFileName;
//			}
//		}
		fileName = URLEncoder.encode(fileName, "UTF-8");
		return fileName;
	}
	
	public final static String Key_Wechat_Mini_App_WxHttpFileService_Url = "key.wechat.mini.app.WxhttpFileService.url";
	public final static String Key_Wechat_Mini_App_WxHttpFileService_User = "key.wechat.mini.app.WxhttpFileService.user";
	public final static String Key_Wechat_Mini_App_WxHttpFileService_Password = "key.wechat.mini.app.WxhttpFileService.password";
	
	/**
	 * 构建默认的微信小程序文件中转服务
	 * @return
	 * @throws InvalidCacheException 
	 */
	public static WxHttpFileService newWxHttpFileService(PanelContext panelContext) throws InvalidCacheException {
		IWsCallbackChannel channel = panelContext.getChannel();
		String wxUrl = null;
		try {
			wxUrl = (String)channel.getCacheValue(Key_Wechat_Mini_App_WxHttpFileService_Url);
		}catch (Exception e) {
			// TODO: handle exception
		}
		if(CmnUtil.isStringEmpty(wxUrl)) {
			wxUrl = MppContext.getString(Key_Wechat_Mini_App_WxHttpFileService_Url);
			if(!CmnUtil.isStringEmpty(wxUrl)) {
				channel.putCacheValue(Key_Wechat_Mini_App_WxHttpFileService_Url, wxUrl);
			}
		}
		String user = null;
		try {
			user = (String)channel.getCacheValue(Key_Wechat_Mini_App_WxHttpFileService_User);
		}catch (Exception e) {
			// TODO: handle exception
		}
		if(CmnUtil.isStringEmpty(user)) {
			user = MppContext.getString(Key_Wechat_Mini_App_WxHttpFileService_User);
			if(!CmnUtil.isStringEmpty(user)) {
				channel.putCacheValue(Key_Wechat_Mini_App_WxHttpFileService_User, user);
			}
		}
		String password = null;
		try {
			password = (String)channel.getCacheValue(Key_Wechat_Mini_App_WxHttpFileService_Password);
		}catch (Exception e) {
			// TODO: handle exception
		}
		if(CmnUtil.isStringEmpty(password)) {
			password = MppContext.getString(Key_Wechat_Mini_App_WxHttpFileService_Password);
			if(!CmnUtil.isStringEmpty(password)) {
				channel.putCacheValue(Key_Wechat_Mini_App_WxHttpFileService_Password, password);
			}
		}
		WxHttpFileService srv = new WxHttpFileService();
		if(!CmnUtil.isStringEmpty(wxUrl)) {
			srv.setBaseUrl(wxUrl);
		}
		if(!CmnUtil.isStringEmpty(user)) {
			srv.setUser(user);
		}
		if(!CmnUtil.isStringEmpty(password)) {
			srv.setPwd(Password.encode(password));
		}
		return srv;
	}
	/**
	 * 构建微信小程序文件中转服务
	 * @param url	文件服务url
	 * @param user	用户	
	 * @param password	密码（明文）
	 * @return
	 */
	public static WxHttpFileService newWxHttpFileService(String url,String user,String password) {
		WxHttpFileService srv = new WxHttpFileService();
		srv.setBaseUrl(url);
		srv.setUser(user);
		srv.setPwd(Password.encode(password));
		return srv;
	}
	/**
	 * 下载文件
	 * @param panelContext
	 * @param fileName
	 * @param content
	 * @throws Exception
	 */
	public static void downloadFile(PanelContext panelContext, String fileName,byte[] content) throws Exception {
		DeviceInfoDto device = FePaltformUtil.getCacheDeviceInfo(panelContext);
		if(FePaltformUtil.isMiniProgram(device)) {
			WxHttpFileService srv = newWxHttpFileService(panelContext);
			ByteArrayInputStream bis = new ByteArrayInputStream(content);
//			fileName = encodeFileName(fileName);
			try(AutoCloseable showMask = new ShowWaitMask(panelContext,getI18nString(panelContext, FeI18n.TITLE_FILE_DOWNLOADING)+"...")){
				try(TempHttpFile tmpFile = srv.uploadTempFile(bis, fileName)){
					String fileUrl = srv.buildDownloadUrl(tmpFile.getServerFileName(), true);
					String wxFilePath = WxMiniCommand.downloadFile(panelContext.getChannel(), fileUrl);
					String suffix = FileUtil.getSuffix(fileName);
					if(!CmnUtil.isStringEmpty(suffix)) {
						if(FileType.image.getSuffixs().contains(suffix)) {
							WxMiniCommand.saveImageToPhotosAlbum(panelContext.getChannel(), wxFilePath);
						}else if(FileType.video.getSuffix().contains(suffix)) {
							WxMiniCommand.saveVideoToPhotosAlbum(panelContext.getChannel(), wxFilePath);
						}
					}else {
						WxMiniCommand.openDocument(panelContext.getChannel(), wxFilePath);
					}
				}
			}
		}else {
			BeFile file = new BeFile();
			file.setBytes(content);
			file.setName(fileName);
			file.setStorPath("./uploadTemp/"+panelContext.getCurrentUser()+"/"+file.getName());
			file.setLength(content.length);
			AutoCloseable showMask = null;
			//文件大于5MB时，显示下载进度条
			if(content.length > 5* 1024* 1024) {
				showMask = new ShowWaitMask(panelContext,getI18nString(panelContext, FeI18n.TITLE_FILE_DOWNLOADING)+"...");
			}
			try{
				IFiles.get().saveFile(file.getStorPath(), file.getBytes(), false);
				DownloadFile.start(panelContext, IFileService.class, file.getStorPath(), fileName);
			}finally {
				IFiles.get().deleteFile(file.getStorPath());
				if(showMask != null)
					showMask.close();
			}
		}
	}
	
	public static List<BeFile> uploadFile(PanelContext panelContext,boolean isMobileEnv,PickMediaFileType mediaType,boolean isAllowMultiple,List<String> extFilter) throws Exception {
//		DeviceInfoDto device = QueryDeviceInfo.query(panelContext.getChannel());
//		if(FePaltformUtil.isMiniProgram(device)) {
//			String javaFile = "./temp/Upload/TempUpload" + ToolUtilities.allockUUIDWithUnderline(); // java端文件路径
//			String mobileFilePath = "";//TODO 补充小程序文件选择器
//			WxHttpFileService fileSrv = new WxHttpFileService();
//			File mobileFile = new File(mobileFilePath);
//			// 转运到本地文件的方式
//			fileSrv.transportFile(panelContext.getChannel(), mobileFilePath, javaFile, 10*60*1000);
//			BeFile beFile = BeFile.fromFile(new File(javaFile));
//			beFile.setName(mobileFile.getName());
//			return Arrays.asList(beFile);
//		}else {
			UploadFileResult result = null;
			if (isMobileEnv && mediaType != null) {
				UploadMediaFile upload = new UploadMediaFile().setTempFolder("temp/Upload").setTempFilePrefix("TempUpload");
				upload.setAllowMultiple(isAllowMultiple);
				upload.setFileType(mediaType);
				result = upload.action(panelContext);
			} else {
				UploadFile upload = new UploadFile().setTempFolder("temp/Upload").setTempFilePrefix("TempUpload");
				upload.setExtFilter(extFilter).setFileType(PickFileType.custom);
				upload.setAllowMultiple(isAllowMultiple);
	
				result = upload.action(panelContext);
			}
			if (result == null)
				return new ArrayList<>();
			return result.getLstFiles();
//		}
	}
}
