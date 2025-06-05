package fe.util.i18n;

import cmn.anotation.I18nDeclare;
import cmn.i18n.AbsI18n;
/**
 *  国际化资源变量定义
 * @author chenxb
 *
 */
@I18nDeclare
public class FeI18n  extends AbsI18n{
//	private BasicRes res = new BasicRes("LanguageRes/cmnutil_zh_CN.properties", "LanguageRes/cmnutil_en_US.properties");
	private static FeI18n inst = new FeI18n();
	public FeI18n() {
		super();
	}
	public static FeI18n get() {
		return inst;
	}
	
	@Override
	public String getResourceFileName() {
		return "feutil_i18n.setting";
	}

//	private static BasicRes res = new BasicRes("LanguageRes/feutil_zh_CN.properties", "LanguageRes/feutil_en_US.properties");


	public static String getString(String s, Object... params) {
//		return String.format(res.getString(s), params);
		return inst.format(s, params);
	}
	@I18nDeclare
	public final static String Warning = getString("warning");
	@I18nDeclare
	public final static String ErrorDetail = getString("Error Detail");
	@I18nDeclare
	public final static String Config = getString("config");
	@I18nDeclare
	public final static String Confirm = getString("confirm");
	@I18nDeclare
	public final static String Cancel = getString("cancel");
	@I18nDeclare
	public final static String Close = getString("close");
	@I18nDeclare
	public final static String CREATE_SUCCESS = getString("Create Success!");
	@I18nDeclare
	public final static String UPDATE_SUCCESS = getString("Update Success!");
	@I18nDeclare
	public final static String DELETE_SUCCESS = getString("Delete Success!");
	@I18nDeclare
	public final static String SET_SEARCH_START = getString("SET_SEARCH_START");
	@I18nDeclare
	public final static String CAN_NOT_BE_NULL = getString("CAN_NOT_BE_NULL");
	@I18nDeclare
	public final static String TIPS_FILE_SUFFIX_ILLEGAL = getString("TIPS_FILE_SUFFIX_ILLEGAL");
	@I18nDeclare
	public final static String TIPS_OLD_PASSWORD_INCORRECT = getString("TIPS_OLD_PASSWORD_INCORRECT");
	@I18nDeclare
	public final static String Create = getString("create");//新增
	@I18nDeclare
	public final static String Refresh = getString("refresh");//刷新
	@I18nDeclare
	public final static String Update = getString("update");//保存
	@I18nDeclare
	public final static String Delete = getString("delete");//删除
	@I18nDeclare
	public final static String Search = getString("search");
	@I18nDeclare
	public final static String Upload = getString(FeUtilConst.Upload);
	@I18nDeclare
	public final static String Download = getString(FeUtilConst.Download);
	@I18nDeclare
	public final static String Export = getString("export");
	@I18nDeclare
	public final static String Import = getString("import");
	@I18nDeclare
	public final static String Preview = getString("preview");
	@I18nDeclare
	public final static String Operate = getString("operate");
	@I18nDeclare
	public final static String OPERATE_SUCCESS = getString("Operate Success!");
	@I18nDeclare
	public final static String DELETE_CONFIRM_TIPS = getString("DELETE_CONFIRM_TIPS");
	@I18nDeclare
	public final static String TIPS_SELECT_ROW = "请选择表格行！";
	@I18nDeclare
	public final static String RUNTIME_ERROR = getString("Runtime Error!");
	@I18nDeclare
	public final static String FontIcon = getString("fontIcon");
	@I18nDeclare
	public final static String ProjectIcon = getString("projectIcon");
	@I18nDeclare
	public final static String CustomIcon = getString("customIcon");
	@I18nDeclare
	public final static String BackgroundColor = getString("backgroundColor");
	@I18nDeclare
	public final static String Selected = getString("selected");
	@I18nDeclare
	public final static String Edit = getString("edit");
	@I18nDeclare
	public final static String Clear  = getString("clear");
	@I18nDeclare
	public final static String Basic  = getString("basic");
	@I18nDeclare
	public final static String Decoration  = getString("decoration");
	@I18nDeclare
	public final static String File = getString("file");
	@I18nDeclare
	public final static String Downloading = getString("downloading");
	@I18nDeclare
	public final static String Transporting = getString("transporting");
	@I18nDeclare
	public final static String TITLE_FILE_DOWNLOADING = getString("TITLE_FILE_DOWNLOADING");
	
	public static void main(String[] args) {
		System.out.println(RUNTIME_ERROR);
	}
}
