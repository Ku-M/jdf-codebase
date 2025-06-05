package gpf.dc.basic.i18n;

import cmn.anotation.I18nDeclare;
import cmn.i18n.AbsI18n;
import fe.util.i18n.FeI18n;
import gpf.dc.fe.component.relation.FilterColumnTable;
import gpf.dc.fe.util.GpfDCFeI18n;
@I18nDeclare(defaultGroup=true)
public class GpfDCBasicI18n extends AbsI18n{
	//	private static BasicRes res = new BasicRes("LanguageRes/gpfdcbasic_zh_CN.properties", "LanguageRes/gpfdcbasic_en_US.properties");
	protected static GpfDCBasicI18n inst = new GpfDCBasicI18n();
	private GpfDCBasicI18n() {
		super();
	}
	public static GpfDCBasicI18n get() {
		return inst;
	}

	@Override
	public String getResourceFileName() {
		return "gpfdcbasic_i18n.setting";
	}

	public static String getString(String s, Object... params) {
//		return String.format(res.getString(s), params);
		return inst.format(s, params);
	}
	@I18nDeclare
	public static final String Language = "Language";
	@I18nDeclare
	public static final String Loading = "页面加载中...";
	@I18nDeclare
	public static final String Close = "关闭";
	@I18nDeclare
	public static final String CloseOther = "关闭其他";
	@I18nDeclare
	public static final String CloseLeftSide = "关闭左侧标签页";
	@I18nDeclare
	public static final String CloseRightSide = "关闭右侧标签页";
	@I18nDeclare
	public static final String Hello ="你好";
	@I18nDeclare
	public static final String HomePage ="首页";
	@I18nDeclare
	public static final String Login ="登录";
	@I18nDeclare
	public static final String Register = "注册";
	@I18nDeclare
	public static final String Share = "分享";
	@I18nDeclare
	public static final String Sharing_failed = "分享失败";
	@I18nDeclare
	public static final String Sharing_success = "分享成功";
	@I18nDeclare
	public static final String Download = "下载";
	@I18nDeclare
	public static final String HiddenColum = "隐藏列";
	@I18nDeclare
	public final static String sOldPassword = "旧密码";
	@I18nDeclare
	public final static String sNewPassword = "新密码";
	@I18nDeclare
	public final static String sConfirmNewPassword = "确认新密码";
	@I18nDeclare
	public static final String PASSWORD_INCONSISTENT = "两次输入密码不一致！";
	@I18nDeclare
	public static final String PASSWORD_CHANGED_SUCCESS = "密码修改成功！";
	@I18nDeclare
	public static final String Application_Submitted = "已提交申请！";
	@I18nDeclare
	public static final String Please_input_a_password = "请输入密码！";
	@I18nDeclare
	public static final String The_user_or_password_is_incorrect = "用户或密码不正确!";
	@I18nDeclare
	public static final String The_password_can_not_be_empty = "密码不能为空";
	@I18nDeclare
	public static final String PASSWORD_VERIFY_MESSAGE = "密码必须包含字母和数字，且在6~18位之间";
	@I18nDeclare
	public static final String The_user_has_been_locked = "用户已被锁定！";
	@I18nDeclare
	public static final String The_user_does_not_exist = "用户不存在！";
	@I18nDeclare
	public static final String Welcome_to_log_in="欢迎登录{1}";
	@I18nDeclare
	public static final String Registration_function_unavailable = "未配置用户注册视图，注册功能不可用！";
	@I18nDeclare
	public static final String Application_for_registration = "注册申请";
	@I18nDeclare
	public static final String Enter_user_name = "请输入用户名";
	@I18nDeclare
	public static final String Enter_password = "请输入密码";
	@I18nDeclare
	public static final String Application_does_not_exist = "应用配置不存在！";
	@I18nDeclare
	public static final String UserName ="用户名";
	@I18nDeclare
	public static final String Password = "密码";
	@I18nDeclare
	public static final String AdminBackend = "管理后台";
	@I18nDeclare
	public static final String TODO = "待办";
	@I18nDeclare
	public static final String TODO_List = "待办事项";
	@I18nDeclare
	public static final String AdvanceFilter = "高级搜索";
	@I18nDeclare
	public static final String Create = "新增";
	@I18nDeclare
	public static final String Search = FeI18n.Search;
	@I18nDeclare
	public static final String Confirm = FeI18n.Confirm;
	@I18nDeclare
	public static final String Cancel = FeI18n.Cancel;
	@I18nDeclare
	public final static String Warning = FeI18n.Warning;
	@I18nDeclare
	public final static String ErrorDetail = FeI18n.ErrorDetail;
	@I18nDeclare
	public final static String Config = FeI18n.Config;
	@I18nDeclare
	public final static String CREATE_SUCCESS = FeI18n.CREATE_SUCCESS;
	@I18nDeclare
	public final static String UPDATE_SUCCESS = FeI18n.UPDATE_SUCCESS;
	@I18nDeclare
	public final static String DELETE_SUCCESS = FeI18n.DELETE_SUCCESS;
	@I18nDeclare
	public final static String SET_SEARCH_START = FeI18n.SET_SEARCH_START;
	@I18nDeclare
	public final static String CAN_NOT_BE_NULL = FeI18n.CAN_NOT_BE_NULL;
	@I18nDeclare
	public final static String TIPS_FILE_SUFFIX_ILLEGAL = FeI18n.TIPS_FILE_SUFFIX_ILLEGAL;
	@I18nDeclare
	public final static String TIPS_OLD_PASSWORD_INCORRECT = FeI18n.TIPS_OLD_PASSWORD_INCORRECT;
	@I18nDeclare
	public final static String Refresh = FeI18n.Refresh;//刷新
	@I18nDeclare
	public final static String Delete = FeI18n.Delete;//删除
	@I18nDeclare
	public final static String Upload = FeI18n.Upload;
	@I18nDeclare
	public final static String Export = FeI18n.Export;
	@I18nDeclare
	public final static String Import = FeI18n.Import;
	@I18nDeclare
	public final static String Preview = FeI18n.Preview;
	@I18nDeclare
	public final static String Operate = FeI18n.Operate;
	@I18nDeclare
	public final static String OPERATE_SUCCESS = FeI18n.OPERATE_SUCCESS;
	@I18nDeclare
	public final static String DELETE_CONFIRM_TIPS = FeI18n.DELETE_CONFIRM_TIPS;
	@I18nDeclare
	public final static String RUNTIME_ERROR = FeI18n.RUNTIME_ERROR;
	@I18nDeclare
	public final static String FontIcon = FeI18n.FontIcon;
	@I18nDeclare
	public final static String ProjectIcon = FeI18n.ProjectIcon;
	@I18nDeclare
	public final static String CustomIcon = FeI18n.CustomIcon;
	@I18nDeclare
	public final static String BackgroundColor = FeI18n.BackgroundColor;
	@I18nDeclare
	public final static String Selected = FeI18n.Selected;
	@I18nDeclare
	public final static String Clear  = FeI18n.Clear;
	@I18nDeclare
	public final static String Basic  = FeI18n.Basic;
	@I18nDeclare
	public final static String Decoration  = FeI18n.Decoration;
	@I18nDeclare
	public final static String File = FeI18n.File;
	@I18nDeclare
	public final static String Downloading = FeI18n.Downloading;
	@I18nDeclare
	public final static String Transporting = FeI18n.Transporting;
	@I18nDeclare
	public final static String TITLE_FILE_DOWNLOADING = FeI18n.TITLE_FILE_DOWNLOADING;
	@I18nDeclare
	public static final String Find = "查询";
	@I18nDeclare
	public static final String Reset = "重置";
	@I18nDeclare
	public static final String Edit = "编辑";
	@I18nDeclare
	public static final String UserList = "用户列表";
	@I18nDeclare
	public static final String System_Announcement = "系统公告";
	@I18nDeclare
	public static final String See_All = "查看全部";
	@I18nDeclare
	public static final String Process_tracking = "流程跟踪";
	@I18nDeclare
	public static final String To_be_submitted = "待提交";
	@I18nDeclare
	public static final String Processing = "处理中";
	@I18nDeclare
	public static final String Ended = "已结束";
	@I18nDeclare
	public static final String ImportData = "导入文件";
	@I18nDeclare
	public static final String ExportData = "导出文件";
	@I18nDeclare
	public static final String DesignMode = "设计态";
	@I18nDeclare
	public static final String RuntimeMode = "运行态";
	@I18nDeclare
	public static final String ProcessDetail = "流程详情";
	@I18nDeclare
	public static final String SelectOrg = "选择组织";
	@I18nDeclare
	public static final String 关联模型 = "关联模型";
	@I18nDeclare
	public static final String 关联数据 = "关联数据";
	@I18nDeclare
	public final static String TITLE_ADD_USER = "添加用户";
	@I18nDeclare
	public final static String Value_Label = FilterColumnTable.Value_Label;
	@I18nDeclare
	public static final String RunTimeLog = getString("runTimeLog");
	@I18nDeclare
	public static final String ReloadPlugin = getString("reloadPlugin");
	@I18nDeclare
	public static final String Setting = getString("setting");
	@I18nDeclare
	public static final String Tools = getString("tools");
	@I18nDeclare
	public static final String RequestLog= getString("requestLog");
	@I18nDeclare
	public static final String GlobalOpObserver = "全局数据操作监听";
	@I18nDeclare
	public static final String EngineerMode= getString("engineerMode");
	@I18nDeclare
	public static final String CacheBlockMgr= getString("CacheBlockMgr");
	@I18nDeclare
	public static final String ShortCutKeys= getString("ShortCutKeys");
	@I18nDeclare
	public static final String ShortLinkMgr= getString("ShortLinkMgr");
	@I18nDeclare
	public static final String IconMgr= getString("IconMgr");
	public static final String ExportGdfStandardMapping= getString("exportGdfStandardMapping");
	public static final String ExportGdfOrgRoleUserRelationView= getString("exportGdfOrgRoleUserRelationView");
	@I18nDeclare
	public static final String ChangePassword = getString("changePassword");
	@I18nDeclare
	public static final String Logout = getString("logout");
	@I18nDeclare
	public static final String CONFIRM_LOGOUT = getString("CONFIRM_LOGOUT");

	//	public static final String close= "close";
//	public static final String closeOther= "closeOther";
//	public static final String closeLeftTab= "closeLeftTab";
//	public static final String closeRightTab= "closeRightTab";
	@I18nDeclare
	public static final String TRUE = GpfDCFeI18n.TRUE;
	@I18nDeclare
	public static final String FALSE = GpfDCFeI18n.FALSE;
	@I18nDeclare
	public static final String Role = GpfDCFeI18n.Role;
	@I18nDeclare
	public static final String User = GpfDCFeI18n.User;
	@I18nDeclare
	public static final String Name = GpfDCFeI18n.Name;
	@I18nDeclare
	public static final String Label = GpfDCFeI18n.Label;
	@I18nDeclare
	public static final String Status = GpfDCFeI18n.getString("status");
	@I18nDeclare
	public static final String Description = GpfDCFeI18n.Description;
	@I18nDeclare
	public static final String Data = GpfDCFeI18n.Data;
	@I18nDeclare
	public static final String Organization = GpfDCFeI18n.Organization;
	@I18nDeclare
	public static final String Action = GpfDCFeI18n.Action;
	@I18nDeclare
	public static final String FormData = GpfDCFeI18n.FormData;
	@I18nDeclare
	public static final String TIPS_SELECT_EXPORT_ROW = GpfDCFeI18n.TIPS_SELECT_EXPORT_ROW;
	@I18nDeclare
	public static final String Copywriting = getString("Copywriting");
	@I18nDeclare
	public static final String Button = getString("Button");
	@I18nDeclare
	public static final String Image = getString("Image");
	@I18nDeclare
	public static final String Deploy = getString("deploy");
	@I18nDeclare
	public static final String PleaseSelect = getString("Please select");
	@I18nDeclare
	public static final String Default = getString("default");
	public static final String ResetDefault = getString("resetDefault");
	public static final String ExportLayout = getString("exportLayout");
	public static final String ImportLayout = getString("importLayout");
	public static final String ExportDesign = getString("exportDesign");
	public static final String ImportDesign = getString("importDesign");
	@I18nDeclare
	public static final String WebAccessControl = getString("webAccessControl");

	@I18nDeclare
	public static final String CURRENT_USER = getString("CURRENT_USER");
	@I18nDeclare
	public static final String RETURN_LOGIN = getString("RETURN_LOGIN");
	public static final String TIPS_DC_BASIC_MODEL_IS_NOT_IMPORTED = getString("TIPS_DC_BASIC_MODEL_IS_NOT_IMPORTED");
	@I18nDeclare
	public static final String TIPS_MODEL_ID_IS_NULL = getString("TIPS_MODEL_ID_IS_NULL");
	@I18nDeclare
	public static final String TIPS_MODEL_IS_NOT_EXIST = getString("TIPS_MODEL_IS_NOT_EXIST"); // {1} 模型ID
	@I18nDeclare
	public static final String TIPS_PAGE_ERROR_CONTACT_ADMIN = getString("TIPS_PAGE_ERROR_CONTACT_ADMIN"); // {1} 模型ID
	@I18nDeclare
	public static final String TIPS_PAGE_ACCESS_DENY = getString("TIPS_PAGE_ACCESS_DENY"); // {1} 模型ID

	public static final String WRANNING_CONFIRM_REBUILD_CACHE = getString("WRANNING_CONFIRM_REBUILD_CACHE"); // {1} 模型ID

	public static final String WRANNING_CONFIRM_RELOAD_APP_CACHE = getString("WRANNING_CONFIRM_RELOAD_APP_CACHE"); //
	public static final String ReloadAppCache = getString("reloadAppCache"); //
	public static final String DeployAll = getString("deployAll"); //
	@I18nDeclare
	public static final String UserManual = getString("userManual"); //使用人员说明
	@I18nDeclare
	public static final String ReadAndAgree = getString("readAndAgree"); //阅读并同意
	@I18nDeclare
	public static final String ServiceAgreement = getString("serviceAgreement"); //服务协议
	@I18nDeclare
	public static final String AgreeAndContinue = getString("agreeAndContinue"); //同意并继续
	@I18nDeclare
	public static final String NotSelectServiceAgreementInfo = getString("notSelectServiceAgreementInfo"); //未勾选服务协议提示信息
	@I18nDeclare
	public static final String ServiceAgreementIsNullInfo = getString("serviceAgreementIsNullInfo"); //服务协议为空时的提示信息
	@I18nDeclare
	public static final String ServiceAgreementFileTypeInfo = getString("serviceAgreementFileTypeInfo"); //服务协议文件类型提示信息
	@I18nDeclare
	public static final String VersionNumber = getString("versionNumber"); //
	@I18nDeclare
	public static final String CheckUpdate = getString("checkUpdate"); //检查更新
	@I18nDeclare
	public static final String NewVersion = getString("newVersion"); //最新版本
	@I18nDeclare
	public static final String NewVersionSize = getString("newVersionSize"); //新版本大小
	@I18nDeclare
	public static final String Update = getString("update"); //更新
	@I18nDeclare
	public static final String Install = getString("install"); //安装
	@I18nDeclare
	public static final String WRANNING_FIND_FAIL_INSTALL_PACKAGE = getString("WRANNING_FIND_FAIL_INSTALL_PACKAGE"); //未能获取到安装包
	@I18nDeclare
	public static final String WRANNING_INSTALL_FAIL = getString("WRANNING_INSTALL_FAIL"); //安装失败，未能找到安装包
	@I18nDeclare
	public static final String WRANNING_DOWNLOAD_INTERRUPTED = getString("WRANNING_DOWNLOAD_INTERRUPTED"); //下载中断,点击重试

	public static void main(String[] args) {
		System.out.println(getString(TIPS_DC_BASIC_MODEL_IS_NOT_IMPORTED,"aa"));
	}


}
