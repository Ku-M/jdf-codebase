package gpf.dc.runtime;

import java.io.Serializable;
import java.util.List;

import gpf.adur.data.AssociationData;
import gpf.adur.data.Form;
import gpf.md.slave.NestingData;

public class OperateLog extends Form implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -549419989684052755L;
//	public final static String FormModelId = gpf.md.slave.OperateLog.class.getName();
//	public final static String TABLE = gpf.md.slave.OperateLog.TABLE;
	public final static String MasterClass = NestingData.MasterClass;
	public final static String MasterKey = NestingData.MasterKey;
	public final static String MasterField = NestingData.MasterField;
	//此属性属于历史问题，调整了底层操作记录做了分表和当前操作状态记录，补充stepUuid,上层为了屏蔽差异，将dao的uuid值写到_daoUuid属性上
	public static final String DaoUuid = "_daoUuid";
	public static final String Uuid = "uuid";
	
	
	
	public static final String Creator = "creator";
	public static final String CreatorCnName = "creatorCnName";
	public static final String Assignee = "assignee";
	public static final String AssigneeCnName = "assigneeCnName";
	public static final String StepOperator = "stepOperator";
	public static final String StepOperatorCnName = "stepOperatorCnName";
	public static final String Status = "status";
	public static final String StatusEnum = "statusEnum";
	public static final String ExecuteTime = "executeTime";
	public static final String CreateTime = "createTime";
	public static final String UpdateTime = "updateTime";
	public static final String NodeKey = "nodeKey";
	public static final String StepName = "stepName";
	public static final String NodeName = "nodeName";
	public static final String ActionName = "actionName";
	public static final String LastStepName = "lastStepName";
	public static final String LastStepOperator = "lastStepOperator";
	public static final String LastStepOperatorCnName = "lastStepOperatorCnName";
	public static final String LastStepTag = "lastStepTag";
	public static final String LastNodeKey = "lastNodeKey";
	public static final String LastNodeName = "lastNodeName";
	public static final String StepTag = "stepTag";
	public static final String NextNodeKeys = "nextNodeKeys";
	public static final String StartTrace = "startTrace";
	public static final String Trace = "trace";
	public static final String ErrorMsg = "errorMsg";
    public static final String ErrorDetail = "errorDetail";
    
//    public String
    
    public OperateLog(String historyOpLogModelId) {
    	super(historyOpLogModelId);
	}
//    public String errorDetail;
    
    public String getMasterClass() {
		return (String) getAttrValueByCode(MasterClass);
	}
    public OperateLog setMasterClass(String v) {
		setAttrValueByCode(MasterClass,v);
		return this;
	}
    public String getMasterField() {
		return (String) getAttrValueByCode(MasterField);
	}
    public OperateLog setMasterField(String v) {
		setAttrValueByCode(MasterField,v);
		return this;
	}
    
    public String getMasterKey() {
		return (String) getAttrValueByCode(MasterKey);
	}
    public OperateLog setMasterKey(String v) {
		setAttrValueByCode(MasterKey,v);
		return this;
	}
    
    public String getDaoUuid() {
    	return (String) getAttrValueByCode(DaoUuid);
    }
    public OperateLog setDaoUuid(String daoUuid) {
    	setAttrValueByCode(DaoUuid, daoUuid);
    	return this;
    }
    
    
    public String getCreator() {
		return (String) getAttrValueByCode(Creator);
	}

	public OperateLog setCreator(String v) {
		setAttrValueByCode(Creator, v);
		return this;
	}

	public Long getCreateTime() {
		return (Long) getAttrValueByCode(CreateTime);
	}

	public OperateLog setCreateTime(Long v) {
		setAttrValueByCode(CreateTime, v);
		return this;
	}

	public Long getUpdateTime() {
		return (Long) getAttrValueByCode(UpdateTime);
	}

	public OperateLog setUpdateTime(Long v) {
		setAttrValueByCode(UpdateTime, v);
		return this;
	}

	public List<AssociationData> getAssignee() throws Exception {
		return getAssociationsByCode(Assignee);
//		return (String) getAttrValueByCode(Assignee);
	}
	
	public OperateLog setAssignee(List<AssociationData> v) {
		setAttrValueByCode(Assignee, v);
		return this;
	}
	
	public String getAssigneeCnName() throws Exception {
		return getStringByCode(AssigneeCnName);
	}
	
	public OperateLog setAssigneeCnName(String v) {
		setAttrValueByCode(AssigneeCnName, v);
		return this;
	}

	public AssociationData getStepOperator() {
		return (AssociationData) getAttrValueByCode(StepOperator);
	}

	public OperateLog setStepOperator(AssociationData v) {
		setAttrValueByCode(StepOperator, v);
		return this;
	}
	
	public String getStepOperatorCnName() throws Exception {
		return getStringByCode(StepOperatorCnName);
	}

	public OperateLog setStepOperatorCnName(String v) {
		setAttrValueByCode(StepOperatorCnName, v);
		return this;
	}

	public Long getExecuteTime() {
		return (Long) getAttrValueByCode(ExecuteTime);
	}

	public OperateLog setExecuteTime(Long v) {
		setAttrValueByCode(ExecuteTime, v);
		return this;
	}

	public String getNodeKey() {
		return (String) getAttrValueByCode(NodeKey);
	}

	public OperateLog setNodeKey(String v) {
		setAttrValueByCode(NodeKey, v);
		return this;
	}

	public String getStepName() {
		return (String) getAttrValueByCode(StepName);
	}

	public OperateLog setStepName(String v) {
		setAttrValueByCode(StepName, v);
		return this;
	}

	public String getNodeName() {
		return (String) getAttrValueByCode(NodeName);
	}

	public OperateLog setNodeName(String v) {
		setAttrValueByCode(NodeName, v);
		return this;
	}

	public String getActionName() {
		return (String) getAttrValueByCode(ActionName);
	}

	public OperateLog setActionName(String v) {
		setAttrValueByCode(ActionName, v);
		return this;
	}

	public String getLastStepName() {
		return (String) getAttrValueByCode(LastStepName);
	}

	public OperateLog setLastStepName(String v) {
		setAttrValueByCode(LastStepName, v);
		return this;
	}

	public String getLastStepOperator() {
		return (String) getAttrValueByCode(LastStepOperator);
	}

	public OperateLog setLastStepOperator(String v) {
		setAttrValueByCode(LastStepOperator, v);
		return this;
	}

	public String getLastStepTag() {
		return (String) getAttrValueByCode(LastStepTag);
	}

	public OperateLog setLastStepTag(String v) {
		setAttrValueByCode(LastStepTag, v);
		return this;
	}

	public String getLastNodeKey() {
		return (String) getAttrValueByCode(LastNodeKey);
	}

	public OperateLog setLastNodeKey(String v) {
		setAttrValueByCode(LastNodeKey, v);
		return this;
	}

	public String getLastNodeName() {
		return (String) getAttrValueByCode(LastNodeName);
	}

	public OperateLog setLastNodeName(String v) {
		setAttrValueByCode(LastNodeName, v);
		return this;
	}

	public String getStepTag() {
		return (String) getAttrValueByCode(StepTag);
	}

	public OperateLog setStepTag(String v) {
		setAttrValueByCode(StepTag, v);
		return this;
	}

	public String getNextNodeKeys() {
		return (String) getAttrValueByCode(NextNodeKeys);
	}

	public OperateLog setNextNodeKeys(String v) {
		setAttrValueByCode(NextNodeKeys, v);
		return this;
	}
	
	public String getStartTrace() {
		return (String) getAttrValueByCode(StartTrace);
	}

	public OperateLog setStartTrace(String trace) {
		setAttrValueByCode(StartTrace, trace);
		return this;
	}
	
	public String getTrace() {
		return (String) getAttrValueByCode(Trace);
	}

	public OperateLog setTrace(String trace) {
		setAttrValueByCode(Trace, trace);
		return this;
	}

	public String getErrorMsg() {
		return (String) getAttrValueByCode(ErrorMsg);
	}

	public OperateLog setErrorMsg(String errorMsg) {
		setAttrValueByCode(ErrorMsg, errorMsg);
		return this;
	}

	public String getErrorDetail() {
		return (String) getAttrValueByCode(ErrorDetail);
	}

	public OperateLog setErrorDetail(String errorDetail) {
		setAttrValueByCode(ErrorDetail, errorDetail);
		return this;
	}

	public String getStatus() {
		return (String) getAttrValueByCode(Status);
	}

	public OperateLog setStatus(String status) {
		setAttrValueByCode(Status, status);
		return this;
	}
	
	public Long getStatusEnum() {
		return (Long) getAttrValueByCode(StatusEnum);
	}

	public OperateLog setStatusEnum(Long status) {
		setAttrValueByCode(StatusEnum, status);
		return this;
	}
	
	public static final String InitBeginTime = "initBeginTime";

	public Long getInitBeginTime() throws Exception {
		return getLongByCode(InitBeginTime);
	}

	public OperateLog setInitBeginTime(Long v) {
		setAttrValueByCode(InitBeginTime, v);
		return this;
	}
	
	public static final String InitEndTime = "initEndTime";

	public Long getInitEndTime() throws Exception {
		return getLongByCode(InitEndTime);
	}

	public OperateLog setInitEndTime(Long v) {
		setAttrValueByCode(InitEndTime, v);
		return this;
	}
	
	public static final String InitCost = "initCost";
	
	public Long getInitCost() throws Exception {
		return getLongByCode(InitCost);
	}
	public OperateLog setInitCost(Long initCost) {
		setAttrValueByCode(InitCost, initCost);
		return this;
	}
	
	public static final String FinishBeginTime = "finishBeginTime";

	public Long getFinishBeginTime() throws Exception {
		return getLongByCode(FinishBeginTime);
	}

	public OperateLog setFinishBeginTime(Long v) {
		setAttrValueByCode(FinishBeginTime, v);
		return this;
	}
	
	
	public static final String FinishEndTime = "finishEndTime";

	public Long getFinishEndTime() throws Exception {
		return getLongByCode(FinishEndTime);
	}

	public OperateLog setFinishEndTime(Long v) {
		setAttrValueByCode(FinishEndTime, v);
		return this;
	}
	
	public static final String FinishCost = "finishCost";
	public Long getFinishCost() throws Exception {
		return getLongByCode(FinishCost);
	}
	public OperateLog setFinishCost(Long finishCost) {
		setAttrValueByCode(FinishCost, finishCost);
		return this;
	}
	
	public static final String GoNextBeginTime = "goNextBeginTime";
	public Long goNextBeginTime;

	public Long getGoNextBeginTime() throws Exception {
		return getLongByCode(GoNextBeginTime);
	}

	public OperateLog setGoNextBeginTime(Long v) {
		setAttrValueByCode(GoNextBeginTime, v);
		return this;
	}
	
	
	public static final String GoNextEndTime = "goNextEndTime";

	public Long getGoNextEndTime() throws Exception {
		return getLongByCode(GoNextEndTime);
	}

	public OperateLog setGoNextEndTime(Long v) {
		setAttrValueByCode(GoNextEndTime, v);
		return this;
	}
	
	public static final String GoNextCost = "goNextCost";
	public Long getGoNextCost() throws Exception {
		return getLongByCode(GoNextCost);
	}
	public OperateLog setGoNextCost(Long goNextCost) {
		setAttrValueByCode(GoNextCost, goNextCost);
		return this;
	}
}
