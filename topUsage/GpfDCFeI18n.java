package gpf.dc.fe.util;

import fe.util.i18n.FeI18n;

/**
 * GPF Flutter国际化资源变量定义
 * 
 * @author chenxb
 *
 */
public class GpfDCFeI18n extends FeI18n{

//	private static BasicRes res = new BasicRes("LanguageRes/gpfdcfe_zh_CN.properties",
//			"LanguageRes/gpfdcfe_en_US.properties");
	
	private static GpfDCFeI18n inst = new GpfDCFeI18n();
	public GpfDCFeI18n() {
		super();
	}
	public static GpfDCFeI18n get() {
		return inst;
	}
	
	@Override
	public String getResourceFileName() {
		return "gpfdcfe_i18n.setting";
	}

	public static String getString(String s, Object... params) {
//		return String.format(res.getString(s), params);
		return inst.format(s, params);
	}
	
	public final static String TIPS = getString("TIPS");
	public final static String TRUE = getString("TRUE");
	public final static String FALSE = getString("FALSE");
	public final static String field = getString("field");
	public final static String Description = getString("description");
	public final static String Folder = getString("folder");
	public final static String Name = getString("name");
	public final static String Label = getString("label");
	public final static String Tab = getString("tab");
	public final static String Icon = getString("icon");
	public final static String Select = getString("select");
	public final static String Clear = getString("clear");
	public static final String PleaseSelect = getString("Please select");
	
	public final static String FontIcon = getString("fontIcon");
	public final static String ProjectIcon = getString("projectIcon");
	public final static String CustomIcon = getString("customIcon");
	public final static String BackgroundColor = getString("backgroundColor");
	public final static String Selected = getString("selected");
	public final static String Edit = getString("edit");
	
	public static final String ReloadPlugin = getString("reloadPlugin");
	public static final String EngineerMode= getString("engineerMode");
	public static final String ChangePassword = getString("changePassword");
	public static final String Logout = getString("logout");
	
	public static final String Element = getString("element");
	public static final String Action = getString("action");
	public static final String Data = getString("data");
	public static final String User = getString("user");
	public static final String Role = getString("role");
	public static final String Design = getString("design");
	public final static String Config = getString("config");
	public final static String Runtime = getString("runtime");
	public final static String OpenEngineerMode = getString("openEngineerMode");
	public final static String BomList = getString("bomList");
	public final static String NodeAction = getString("nodeAction");
	public final static String RouterAction = getString("routerAction");
	public final static String BusinessEntity = getString("businessEntity");
	public final static String NestingEntity = getString("nestingEntity");
	public final static String Organization = getString("organization");
	public final static String Flow = getString("flow");
	public final static String FunctionLib = getString("FunctionLib");
	public final static String SqlFunctionLib = getString("SqlFunctionLib");
	public final static String ParamDefine = getString("ParamDefine");
	public final static String DBDataSource = getString("DBDataSource");
	
	public final static String SynchornizePdfInstance = getString("synchornizePdfInstance");
//	public final static String TriggerAction = getString("triggerAction");
	
	public final static String Model = getString("model");
	public final static String Instance = getString("instance");
	public final static String Function = getString("function");
	public final static String CustomField = getString("customField");
	public final static String InstanceInheritSetting = getString("instanceInheritSetting");
	public final static String OtherInformation = getString("otherInformation");
	public final static String TestRun = getString("testRun");
	public final static String ViewSystemField = getString("viewSystemField");
	public final static String BasicInformation = getString("basicInformation");
	public final static String ExtendInformation = getString("extendInformation");
	public final static String Code = getString("code");
	public final static String EnName = getString("enName");
	public final static String CnName = getString("cnName");
	public final static String FieldName = getString("fieldName");
	public final static String IsNotNull = getString("isNotNull");
	public final static String DataType = getString("dataType");
	public final static String Override = getString("override");
	public final static String Preview= getString("preview");//预览
	public final static String Params = getString("params");
	public final static String ParamMapping = "paramMapping";// 参数映射
	public final static String ExposeContextParam = getString("exposeContextParam");// 开放的上下文参数
	public final static String TIPS_EXPOSE_CONTEXT_PARAMS_HELP = getString("TIPS_EXPOSE_CONTEXT_PARAMS_HELP");
	public final static String TIPS_CONFIRM_CHANGE_PARENT = getString("TIPS_CONFIRM_CHANGE_PARENT");
	public final static String TIPS_FILE_SUFFIX_ILLEGAL = getString("TIPS_FILE_SUFFIX_ILLEGAL");
	public final static String CHANGE_PARENT = getString("CHANGE_PARENT");
	
	public final static String DependEffectAnalyze = getString("dependEffectAnalyze");
	public final static String Invisible = getString("invisible");
	public final static String Visible = getString("visible");
	public final static String Writable = getString("writable");
	public final static String Operatable = getString("operatable");
	public final static String FieldPrivilege = getString("fieldPrivilege");
	public final static String ActionPrivilege = getString("actionPrivilege");
	public final static String FormPrivilege = getString("formPrivilege");
		
	public final static String ReferenceForm = getString("referenceForm");
	public final static String ReferenceField = getString("referenceField");
	public final static String ModelField = getString("modelField");
	public final static String Privilege = getString("privilege");
	public final static String List = getString("list");
	public final static String RouterTable = getString("routerTable");
	public final static String RouterSetting = getString("routerSetting");
	public final static String TriggerTable = getString("triggerTable");
	public final static String NodeName = getString("nodeName");
	public final static String LinkName = getString("linkName");
	public final static String StartNode = getString("startNode");
	public final static String Shape = getString("shape");
	public final static String Rectangle = getString("rectangle");
	public final static String Diamond = getString("diamond");
	public final static String Oval = getString("oval");
	public final static String SetColor = getString("setColor");
	public final static String Blue = getString("blue");
	public final static String Green = getString("green");
	public final static String Red = getString("red");
	public final static String ModifyNodeName = getString("modifyNodeName");
	public final static String ViewActionModel = getString("viewActionModel");
	public final static String ViewNodeInfo = getString("viewNodeInfo");
	public final static String NodeInfo = getString("nodeInfo");
	public final static String ControlFlow = getString("controlFlow");
	public final static String TriggerTime = getString("triggerTime");
	public final static String OriginalForm = getString("originalForm");
	public final static String NewForm = getString("newForm");
	public final static String ChangeReferenceForm = getString("changeReferenceForm");
	public final static String LeaveType = getString("leaveType");
	public final static String TargetNode = getString("targetNode");
	public final static String MatchAction = getString("matchAction");
	public final static String MatchFunction = getString("matchFunction");
	public final static String LeaveFunction = getString("leaveFunction");
	public final static String IsRefInstModified = getString("isRefInstModified");
	public final static String MatchUserFunctions = getString("matchUserFunctions");
	public final static String PrivilegeFunctions = getString("privilegeFunctions");
	public final static String Identify = getString("identify");
	public final static String Node = getString("node");
	public final static String EntryRouter = getString("entryRouter");
	public final static String ExitRouter = getString("exitRouter");
	public final static String TriggerType = getString("triggerType");
	public final static String TimeRule = getString("timeRule");
	public final static String TriggerCondition = getString("triggerCondition");
	public final static String TriggerAction = getString("triggerAction");
	public final static String Command = getString("command");
	public final static String EffectScope = getString("effectScope");
	public final static String ProjectManage = getString("projectManage");
	public final static String EventManage = getString("eventManage");
	public final static String ScheduleTask = getString("scheduleTask");
	public final static String TotalForm = getString("totalForm");
	public final static String RuntimeFlow = getString("runtimeFlow");
	public final static String OpertateLog = getString("opertateLog");
	public final static String ViewPrivilege = getString("viewPrivilege");
	

	public final static String ExportModel = getString("exportModel");
	public final static String ExportData = getString("exportData");
	public final static String ExportSelectedData = getString("exportSelectedData");
	public final static String ImportData = getString("importData");
	
	public final static String FormData = getString("formData");

	public final static String DATA_EXPORT_ALL = getString("DATA_EXPORT_ALL");
	public final static String DATA_EXPORT_NONE = getString("DATA_EXPORT_NONE");
	public final static String DATA_EXPORT_PART = getString("DATA_EXPORT_PART");
	
	public final static String ExportCategory = getString("exportCategory");
	public final static String ExportCategoryAndChildren = getString("exportCategoryAndChildren");
	

	public final static String FORCE_UPDATE_WRANING = getString("FORCE_UPDATE_WRANING");//"部分属性强制保存将导致数据丢失，是否确认保存！\n属性变更详情：\n";
	public final static String TIPS_SELECT_OPERATE_ROW = getString("TIPS_SELECT_OPERATE_ROW");
	public final static String TIPS_SET_DATA_EXPORT_MODE_TO_PART = getString("TIPS_SET_DATA_EXPORT_MODE_TO_PART");
	public final static String TIPS_CONTINUE_UPDATE = getString("TIPS_CONTINUE_UPDATE");
	public final static String TIPS_SCRIPT_IS_NOT_FOUND = getString("TIPS_SCRIPT_IS_NOT_FOUND");
	public final static String TIPS_ATTACH_UPLOAD_SIZE_EXCEED = getString("TIPS_ATTACH_UPLOAD_SIZE_EXCEED");
	public final static String TIPS_SELECT_EXPORT_ROW = getString("TIPS_SELECT_EXPORT_ROW");
	public final static String TIPS_ACTION_MODEL_IS_UNSET = getString("TIPS_ACTION_MODEL_IS_UNSET");
	public final static String TIPS_START_NODE_IS_UNSET = getString("TIPS_START_NODE_IS_UNSET");
	public final static String DELETE_CONFIRM_TIPS = getString("DELETE_CONFIRM_TIPS");
	
	
	public final static String TITLE_SELECT_NODE = getString("TITLE_SELECT_NODE");
	public final static String TITLE_SELECT_DATA = getString("TITLE_SELECT_DATA");
	public final static String TITLE_SELECT_EXPORT_DATA = getString("TITLE_SELECT_EXPORT_DATA");
	public final static String TITLE_SELECT_DEPEND_DATA = getString("TITLE_SELECT_DEPEND_DATA");
	public final static String TITLE_SELECT_REFERENCE_FIELD = getString("TITLE_SELECT_REFERENCE_FIELD");
	public final static String TITLE_ADD = getString("TITLE_ADD");
	public final static String TITLE_ADD_USER = getString("TITLE_ADD_USER");
	public final static String TITLE_ADD_ACTION = getString("TITLE_ADD_ACTION");
	public final static String TITLE_USER_MANAGE = getString("TITLE_USER_MANAGE");
	public final static String TITLE_SCRIPT_EXAMPLE = getString("TITLE_SCRIPT_EXAMPLE");
	public final static String TITLE_UPDATE_MODEL = getString("TITLE_UPDATE_MODEL");
	public final static String TITLE_ACTION_FLOW = getString("TITLE_ACTION_FLOW");
	
	
	
////	public final static String UPDATE_SUCESS = getString("Update Sucess!");
////	public final static String OPERATE_SUCCESS = getString("Operate Sucess!");
////	public final static String CAN_NOT_BE_NULL = getString("CAN_NOT_BE_NULL");
//	
//	
//	
//	
//	public final static String H5Icon = getString("h5Icon");
//	public final static String Domain = getString("domain");
//	public final static String Form = getString("form");
//	public final static String Path = getString("path");
//	
//	public final static String Deploy = getString("deploy");
//	public final static String Setting = getString("setting");
//	public final static String NodeType = getString("nodeType");
//	public final static String Process = getString("process");
//	
//	
//	public final static String InformationModel = getString("informationModel");
//	
//	public final static String ProcessConfig = Process+Config;
//

//	
//	public final static String ExportExcel = getString("exportExcel");
//
//	
//	
////	public final static String Create = "create";// 新增
////	public final static String Refresh = "refresh";// 刷新
////	public final static String Update = "update";// 保存
////	public final static String Delete = "delete";// 删除
////	public final static String Search = "Search";
//	public final static String unlimited = getString("unlimited");
//	
//	
//
//	public final static String Model = getString(GpfFeConst.Model);// 模型
//	public final static String Data = getString(GpfFeConst.Data);// 数据
//	public final static String Other = getString(GpfFeConst.Other);// 其他
//	public final static String OtherSetting = getString(GpfFeConst.OtherSetting);// 其他配置
//	
//	public final static String CmnTable = getString("cmnTable");// 模型
//	public final static String CmnTree = getString("cmnTree");// 模型
//	
//	// 流程节点
//	public final static String Type = getString("type");
//	public final static String Enter = getString("enter");
//	public final static String Interaction = getString("interaction");
//	public final static String Leave = getString("leave");
//	public final static String EnterWay = getString("enterWay");
//	public final static String AccessStrategy = getString("accessStrategy");// 准入条件
//	public final static String ScheduleTask = getString("scheduleTask");// 定时任务
//	public final static String MessageNotify = getString("messageNotify");// 消息通知
//	public final static String SubFlow = getString("subFlow");// 消息通知
//	public final static String SubButton = getString("subButton");// 消息通知
//	public final static String SubFlowFinishButton = getString("subFlowFinishButton");// 消息通知
//	public final static String Operators = getString("operators");// 操作人
////	public final static String Strategy = "strategy";// 策略
//	public final static String Buttons = getString("buttons");// 按钮
//	public final static String Revokable = getString("revokable");// 允许撤回
//	public final static String TaskName = getString("taskName");// 任务名称
//	public final static String TriggerWay = getString("triggerWay");// 触发方式
//	public final static String IsImmediately = getString("isImmediately");
//	public final static String PeriodUnit = getString("periodUnit");
//	public final static String PeriodInterval = getString("periodInterval");
//	public final static String UserDefine = getString("UserDefine");
//	public final static String TaskStrategy = getString("taskStrategy");
//	public final static String ActionStrategy = getString("actionStrategy");
//	public final static String TaskType = getString("taskType");// 任务类型
//	public final static String Message = getString("message");// 消息
//	public final static String Visible = getString("visible");// 可见
//	public final static String DefaultConfig = getString("DefaultConfig");// 默认配置
//	public final static String MessageTemplate = getString("messageTemplate");// 消息模板
//	public final static String SubmitButton= getString("submitButton");//按钮
//
//	// 流程配置
//	public final static String FlowConfig = getString(GpfFeConst.FlowConfig);// 流图配置
//	public final static String PrivilegeControl = getString(GpfFeConst.PrivilegeControl);// 权限控制
//	public final static String UI = getString("UI");// 界面配置
//	public final static String UIConfig = getString(GpfFeConst.UIConfig);// 界面配置
//	public final static String ProcessInstance = getString(GpfFeConst.ProcessInstance);// 流程实例
//	public final static String StrategySummary = getString(GpfFeConst.StrategySummary);// 策略总览
//	public final static String PCClient = getString(GpfFeConst.PCClient);// 电脑端
//	public final static String MobileClient = getString(GpfFeConst.MobileClient);// 移动端
//	
//	public final static String CnName= getString(GpfFeConst.CnName);//中午名称
//	public final static String Button= getString(GpfFeConst.Button);//按钮
//	public final static String ButtonCnName= Button+CnName;//按钮中文名称
//	public final static String Column = getString(GpfFeConst.Column);
//	public final static String Content = getString(GpfFeConst.Content);
//	public final static String TodoTitle = getString(GpfFeConst.TodoTitle);
//	public final static String TodoSubTitleField = getString(GpfFeConst.TodoSubTitleField);
//	public final static String Sponse = getString("sponse");
//	public final static String Close = getString("close");
//	public final static String Query = getString("query");
//	public final static String Strategy = getString("strategy");
//	
//	public final static String Privilege = getString("privilege");
//	public final static String SponsePrivilege = Sponse+Privilege;
//	public final static String ClosePrivilege = Close+Privilege;
//	public final static String SponseStrategy = Sponse+Strategy;
//	public final static String CloseStrategy = Close+Strategy;
//	public final static String QueryStrategy = Query+Strategy;
//	public final static String Revise = getString("revise");
//	
//	public final static String DisplayColumn = getString("displayColumn");
//	public final static String FileType = getString("fileType");
//	// 策略
//	public final static String HelpDoc = "helpDoc";
//	public final static String Method = "method";// 方法
//	public final static String ParamMapping = "paramMapping";// 参数映射
//	
//	public final static String PAGE_LAYOUT = getString("page")+getString("layout");
//	
//	//安全管理
//	
//	public final static String User = getString("user");
//	public final static String UserName = getString("userName");
//	public final static String FullName = getString("fullName");
//	public final static String Gender = getString("gender");
//	public final static String Male = getString("male");
//	public final static String Female = getString("female");
//	public final static String Status = getString("status");
//	public final static String TokenExpireTime = getString("tokenExpireTime");
//	public final static String UserGroup = getString("userGroup");
//	public final static String Phone = getString("phone");
//	public final static String Email = getString("email");
//	public final static String List = getString("list");
//	public final static String UserPosition = getString("userPosition");
//	public final static String Role = getString("role");
//	public final static String RoleList = Role+List;
//	public final static String UserGroupList = UserGroup+List;
//	public final static String UserPositionList= UserPosition+List;
//
//	//导出模型
//	public final static String ExportBizModel = getString(GpfFeConst.ExportBizModel);
//	public final static String ExportBizModelData = getString(GpfFeConst.ExportBizModelData);
//	public final static String ExportCmnTable = getString(GpfFeConst.ExportCmnTable);
//	public final static String ExportCmnTableData = getString(GpfFeConst.ExportCmnTableData);
//	public final static String ExportCmnTree = getString(GpfFeConst.ExportCmnTree);
//	public final static String ExportCmnTreeData = getString(GpfFeConst.ExportCmnTreeData);
//	public final static String ExportRelateStrategy = getString(GpfFeConst.ExportRelateStrategy);
//	
//	public final static String ExportInstanceData = getString(GpfFeConst.ExportInstanceData);
//	public final static String ExportMessageTemplate = getString(GpfFeConst.ExportMessageTemplate);
//	public final static String ExportServerInfo = getString(GpfFeConst.ExportServerInfo);
//	
//	public final static String ContainUser = getString(GpfFeConst.ContainUser);
//	
//	//用户注册
//	public final static String USER_REGISTER_PROCESS = "用户注册流程";
//	//超链接
//	public final static String Hyperlink = getString("hyperlink");
//	//自定义
//	public final static String Custom = getString("custom");
//	//打开方式
//	public final static String OpenMode = getString("openMode");
//	
//	//流程详情
//	public final static String ProcessDetail = "流程详情";
//	public final static String ParentFormDetail = "父流程详情";
//	public final static String ChildFormDetail = "子流程详情";
//	
//	public final static String LabelWidth = getString("labelWidth");
//	public final static String ComponentLabelWidths = getString("componentLabelWidths");
//	
//	public final static String FORCE_UPDATE_WRANING = "部分属性强制保存将导致数据丢失，是否确认保存！\n属性变更详情：\n";
//	public final static String Update_Domain_Model = Update+Domain+Model;
//	public final static String Update_Information_Model = Update+InformationModel;
//	public final static String Update_CmnTable_Model = Update+CmnTable+Model;
//	public final static String Update_CmnTree_Model = Update+CmnTree+Model;
//	public final static String Update_Strategy_Model = Update+Strategy+Model;
//	public final static String Update_MessageTemplate_Model = Update+MessageTemplate+Model;
//	
//	public final static String UNABLE_DELETE_MODEL_WRANING = "当前模型已被引用，禁止删除！";
//	
//	public final static String CONFIRM_SYNCHORNIZE_INSTANCE = "是否确定同步流程实例？";
	
	public static void main(String[] args) {
		System.out.println(TITLE_ACTION_FLOW);
	}
}
