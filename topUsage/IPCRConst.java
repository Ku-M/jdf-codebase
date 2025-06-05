package pcr.basic.util;

public interface IPCRConst {
	String jitUserModel = "gpf.md.user.jitUser";
	String jitOrgModel = "gpf.md.org.jitOrg";
	String jitUser = "jitUser_admin";
    //业务模型
    String PCR_DEFINE_MODEL = "gpf.md.PCRDefine";

    //维表
    String PGF_DATA_TYPE_MODEL = "gpf.md.GPFdataType";
    String PCR_IS_NULL_MODEL = "gpf.md.isNull";

    //嵌套模型
    //PDCA字段定义
    String PCR_ATTR_DEFINE_MODEL = "gpf.md.slave.attrDefine";
    //PDF视图表格列
    String VIEW_TABLE_COLUMN_MODEL = "gpf.md.slave.view.TableColumn";

    //PDF
    String PDF_PCR_MANAGEMENT_PROCESS = "com.kwaidoo.pcr.model.process.PCRManagementProcess";

    //视图模型
    String VIEW_PDC_FORM_VIEW = "gpf.md.udf.view.PDCFormView";
    String VIEW_PDF_INSTANCE_TABLE_VIEW = "gpf.md.udf.view.PDFInstanceTableView";

    //维表PDF模板名称
    String DIMENSION_TABLE_PDF_TEMP = "维表_表格模板";

    //工程里PCR模型定义模板路径
    String PCR_MODEL_TEMP_FILE_PATH = "temp/MODEL_DEF_TEMP.xlsx";
    //工程里维表数据模板路径
    String DIMENSION_TEMP_FILE_PATH = "temp/DIMENSION_TEMP.xlsx";
    String DATA_TEMP_FILE_PATH = "temp/DATA_TEMP.xlsx";

    String PCR_FIELD_工作空间 = "工作空间";
    String PCR_FIELD_英文名称 = "英文名称";
    String PCR_FIELD_中文名称 = "中文名称";
    String PCR_FIELD_上层模型 = "上层模型";
    String PCR_FIELD_是否维表 = "是否维表";
    String PCR_FIELD_模型属性 = "模型属性";
    String PCR_FIELD_生效记录 = "生效记录";
    String PCR_FIELD_影响分析 = "影响分析";
    String PCR_FIELD_模型类型 = "模型类型";
    
    String ACTION_MODEL_DEF = "gpf.md.action_mgr_model";
    
    String FormModel_RootNodeMapping = "gpf.md.rootNodeMapping";
    String FormModel_ActionRootNodeMapping = "gpf.md.actionRootNodeMapping";
    /**
     * 视图配置
     */
    String FormModel_ViewConfig = "gpf.md.viewCfg";
}
