package gpf.dc.basic.util;

import java.util.Arrays;
import java.util.List;

import com.leavay.common.util.javac.ClassFactory;

import fe.cmn.data.CColor;
import gpf.dc.basic.i18n.GpfDCBasicI18n;
import gpf.exception.VerifyException;

public class GpfDCBasicConst {
	/**
	 * 视图树模型ID
	 */
	
	public final static String ViewTreeModelId = "gpf.md.ViewTree";
	public final static String ViewTreeViewCode = "视图配置树";
	public final static String TreeViewModelId = "gpf.md.udf.view.ViewTree";
	public final static String FormViewModelId = "gpf.md.udf.view.ViewForm";
	public final static String TableViewModelId = "gpf.md.udf.view.FormTableView";
	/**
	 * 应用模型ID
	 */
	public final static String ApplicationModelId = "gpf.md.Application";
	public final static String ApplicationMenuTreeModelId = "gpf.md.slave.tree.MenuTree";

	public final static String ViewActionModelRootId = "gpf.md.udf.view.View";

	public static final CColor MAIN_COLOR = new CColor(231, 96, 38, 1);

	public final static String FieldCode_ModelId = "mo2Sing2ID";// 模型ID

	public final static String Bussiness_Design = "业务设计";
	public final static String Software_Manufacturing = "软件制造";
	public final static String Setting = "设置";

	public static final List<CColor> deployMenuIconPresetColors = Arrays.asList(new CColor(54, 94, 254, 1),
			new CColor(21, 194, 124, 1), new CColor(231, 96, 38, 1), new CColor(160, 133, 92, 1),
			new CColor(255, 71, 71, 1), new CColor(147, 43, 254, 1), new CColor(255, 6, 146, 1),
			new CColor(59, 68, 82, 1));

	public static final List<CColor> deployMenuBoxPresetColors = Arrays.asList(new CColor(236, 246, 255, 1),
			new CColor(215, 251, 232, 1), new CColor(255, 249, 224, 1), new CColor(249, 246, 242, 1),
			new CColor(255, 242, 242, 1), new CColor(255, 236, 255, 1), new CColor(255, 246, 251, 1),
			new CColor(244, 244, 242, 1));

	public final static String ApplicationMenuNodeType_View = "视图";
	public final static String ApplicationMenuNodeType_Folder = "目录";
	
	//视图-表单属性定义
	public final static String NestingModel_FormField = "gpf.md.slave.view.FormField";
	
	public final static void assertDCBasicModelIsNotImported(String modelClass) {
		try {
			ClassFactory.getValidClassLoader().loadClass(modelClass);
		}catch (Exception e) {
			throw new VerifyException(GpfDCBasicI18n.getString(GpfDCBasicI18n.TIPS_DC_BASIC_MODEL_IS_NOT_IMPORTED,modelClass));
		}
	}
	//微信账号信息
	public final static String WeChatAccountInfoModelId = "gpf.md.slave.basic.WxAccountInfo";
	public final static String Field_AppId = "appId";
	public final static String Field_OpenId = "openId";
	//用户关联微信账号查询SQL
	public final static String UserJoinWxAccountSql = "SELECT T1.*,T2.appId,T2.openId from #userTable# T1 left join #wxAccountTable# T2 on T1.uuid = T2.masterKey";
}
