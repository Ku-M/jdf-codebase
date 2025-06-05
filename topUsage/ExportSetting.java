package gpf.dc.expimp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.kwaidoo.ms.tool.CmnUtil;

import cell.gpf.adur.action.IActionMgr;
import cell.gpf.adur.data.IFormMgr;
import cell.gpf.adur.role.IRoleMgr;
import cell.gpf.adur.user.IUserMgr;
import cell.gpf.dc.concrete.ICDCMgr;
import cell.gpf.dc.config.IPDFMgr;
import gpf.adur.action.Action;
import gpf.adur.action.ActionModel;
import gpf.adur.data.Form;
import gpf.adur.data.FormModel;
import gpf.adur.role.Org;
import gpf.adur.role.Role;
import gpf.adur.user.User;
import gpf.category.Category;
import gpf.dc.concrete.CDC;
import gpf.dc.config.PDC;
import gpf.dc.config.PDF;
import web.dto.Pair;

public class ExportSetting implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2729428534039426460L;
	String fileName;
	/**
	 * 动作模型
	 */
	/**
	 * 动作模型
	 */
	List<ExportModelBriefInfo> actions = new ArrayList<>();
	/**
	 * 表单模型
	 */
	List<ExportModelBriefInfo> forms = new ArrayList<>();
	/**
	 * 嵌套模型
	 */
	List<ExportModelBriefInfo> nestingForms = new ArrayList<>();
	/**
	 * 组织模型
	 */
	List<ExportModelBriefInfo> organizations = new ArrayList<>();
	/**
	 * 角色、身份数据
	 */
	List<ExportDataBriefInfo> roles = new ArrayList<>();
	/**
	 * 用户模型
	 */
	List<ExportModelBriefInfo> users = new ArrayList<>();
	/**
	 * CDC
	 */
	List<ExportModelBriefInfo> cdcs = new ArrayList<>();
	/**
	 * PDF
	 */
	List<ExportModelBriefInfo> pdfs = new ArrayList<>();
	/**
	 * 要素目录
	 */
	List<Category> categorys = new ArrayList<>();
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public List<ExportModelBriefInfo> getActions() {
		return actions;
	}
	public void setActions(List<ExportModelBriefInfo> actions) {
		this.actions = actions;
	}
	public List<ExportModelBriefInfo> getForms() {
		return forms;
	}
	public void setForms(List<ExportModelBriefInfo> forms) {
		this.forms = forms;
	}
	public List<ExportModelBriefInfo> getNestingForms() {
		return nestingForms;
	}
	public void setNestingForms(List<ExportModelBriefInfo> nestingForms) {
		this.nestingForms = nestingForms;
	}
	public List<ExportModelBriefInfo> getOrganizations() {
		return organizations;
	}
	public void setOrganizations(List<ExportModelBriefInfo> organizations) {
		this.organizations = organizations;
	}
	public List<ExportDataBriefInfo> getRoles() {
		return roles;
	}
	public void setRoles(List<ExportDataBriefInfo> roles) {
		this.roles = roles;
	}
	public Map<String,ExportDataBriefInfo> getRoleMap(){
		Map<String,ExportDataBriefInfo> map = new LinkedHashMap<>();
		for(ExportDataBriefInfo data : roles) {
			map.put(data.getId(), data);
		}
		return map;
	}
	public List<ExportModelBriefInfo> getUsers() {
		return users;
	}
	public void setUsers(List<ExportModelBriefInfo> users) {
		this.users = users;
	}
	public List<ExportModelBriefInfo> getCdcs() {
		return cdcs;
	}
	public void setCdcs(List<ExportModelBriefInfo> cdcs) {
		this.cdcs = cdcs;
	}
	
	public List<ExportModelBriefInfo> getPdfs() {
		return pdfs;
	}
	public void setPdfs(List<ExportModelBriefInfo> pdfs) {
		this.pdfs = pdfs;
	}
	public void addModelBriefInfo(ExportModelBriefInfo briefInfo) throws Exception {
		if(IActionMgr.get().isActionModel(briefInfo.getId())) {
			addActionModel(briefInfo);
		}else if(IFormMgr.get().isBusinessEntityModel(briefInfo.getId())) {
			addFormModel(briefInfo);
		}else if(IFormMgr.get().isNestingEntityModel(briefInfo.getId())){
			addNestingFormModel(briefInfo);
		}else  if(IRoleMgr.get().isOrgModel(briefInfo.getId())) {
			addOrgModel(briefInfo);
		}else if(IUserMgr.get().isUserModel(briefInfo.getId())) {
			addUserModel(briefInfo);
		}else if(ICDCMgr.get().isCDC(briefInfo.getId())) {
			addCDC(briefInfo);
		}else if(IPDFMgr.get().isPDF(briefInfo.getId())) {
			addPDF(briefInfo);
		}
	}
	
	public void mergeModelBriefInfo(ExportModelBriefInfo briefInfo) throws Exception {
		if(IActionMgr.get().isActionModel(briefInfo.getId())) {
			mergeExportModelBrief(actions, briefInfo);
		}else if(IFormMgr.get().isBusinessEntityModel(briefInfo.getId())) {
			mergeExportModelBrief(forms, briefInfo);
		}else if(IFormMgr.get().isNestingEntityModel(briefInfo.getId())){
			mergeExportModelBrief(nestingForms, briefInfo);
		}else  if(IRoleMgr.get().isOrgModel(briefInfo.getId())) {
			mergeExportModelBrief(organizations, briefInfo);
		}else if(IUserMgr.get().isUserModel(briefInfo.getId())) {
			mergeExportModelBrief(users, briefInfo);
		}else if(ICDCMgr.get().isCDC(briefInfo.getId())) {
			mergeExportModelBrief(cdcs, briefInfo);
		}else if(IPDFMgr.get().isPDF(briefInfo.getId())) {
			mergeExportModelBrief(pdfs, briefInfo);
		}
	}
	
	public int indexOfModel(List<ExportModelBriefInfo> modelInfos,String modelId) {
		for(int i =0;i<modelInfos.size();i++) {
			ExportModelBriefInfo briefInfo = modelInfos.get(i);
			if(CmnUtil.isStringEqual(modelId,briefInfo.getId()))
				return i;
		}
		return -1;
	}
	public ExportModelBriefInfo getModelBriefInfo(List<ExportModelBriefInfo> modelInfos,String modelId) {
		for(int i =0;i<modelInfos.size();i++) {
			ExportModelBriefInfo briefInfo = modelInfos.get(i);
			if(CmnUtil.isStringEqual(modelId,briefInfo.getId()))
				return briefInfo;
		}
		return null;
	}
	
	public ExportModelBriefInfo searchModelBriefInfo(String modelId) {
		ExportModelBriefInfo modelBrief = null;
		modelBrief = getModelBriefInfo(actions, modelId);
		if(modelBrief == null) {
			modelBrief = getModelBriefInfo(forms, modelId);
		}
		if(modelBrief == null) {
			modelBrief = getModelBriefInfo(nestingForms, modelId);
		}
		if(modelBrief == null) {
			modelBrief = getModelBriefInfo(users, modelId);
		}
		if(modelBrief == null) {
			modelBrief = getModelBriefInfo(organizations, modelId);
		}
		if(modelBrief == null) {
			modelBrief = getModelBriefInfo(cdcs, modelId);
		}
		if(modelBrief == null) {
			modelBrief = getModelBriefInfo(pdfs, modelId);
		}
		return modelBrief;
	}
	
	public Set<String> getAllModelIds(List<ExportModelBriefInfo> modelInfos) {
		Set<String> modelIds = new LinkedHashSet<>();
		for(int i =0;i<modelInfos.size();i++) {
			ExportModelBriefInfo briefInfo = modelInfos.get(i);
			modelIds.add(briefInfo.getId());
		}
		return modelIds;
	}
	
	public Set<String> getAllActionModelIds(){
		return getAllModelIds(actions);
	}
	
	public void addActionModel(ActionModel model) {
		int index = indexOfModel(actions,model.getId());
		ExportModelBriefInfo modelInfo = toExportData(model);
		if(index == -1) {
			this.actions.add(modelInfo);
		}else {
			ExportModelBriefInfo existModel = this.actions.get(index);
			existModel.mergeModelInfo(modelInfo);
			this.actions.set(index, existModel);
		}
	}
	public void addActionModel(ExportModelBriefInfo modelInfo) {
		int index = indexOfModel(actions,modelInfo.getId());
		if(index == -1) {
			this.actions.add(modelInfo);
		}else {
			ExportModelBriefInfo existModel = this.actions.get(index);
			existModel.mergeModelInfo(modelInfo);
			this.actions.set(index, existModel);
		}
	}
	
	public void removeActionModel(String id) {
		int index = indexOfModel(this.actions, id);
		if(index == -1)
			return;
		this.actions.remove(index);
	}
	
//	public void mergeActionModel(ExportModelBriefInfo modelInfo) {
//		mergeExportModelBrief(actions, modelInfo);
//	}
	public void mergeExportModelBrief(List<ExportModelBriefInfo> modelInfos,ExportModelBriefInfo modelInfo) {
		Map<String,ExportModelBriefInfo> modelMap = modelInfos.stream().collect(Collectors.toMap(ExportModelBriefInfo::getId, v->v));
		ExportModelBriefInfo orginalInfo = modelMap.get(modelInfo.getId());
		if(orginalInfo == null) {
			modelInfos.add(modelInfo);
		}else {
			if(modelInfo.isExportModel()) {
				orginalInfo.setExportModel(true);
			}
			if(modelInfo.isExportAllData()) {
				orginalInfo.setDataExportMode(ExportModelBriefInfo.DATA_EXPORT_ALL);
				orginalInfo.setDataList(new ArrayList<>());
			}else if(modelInfo.isExportPartData()) {
				if(orginalInfo.isExportAllData()) {
					
				}else if(orginalInfo.isExportPartData()){
					Map<String,ExportDataBriefInfo> dataMap = orginalInfo.getDataMap();
					Map<String,ExportDataBriefInfo> newDataMap = modelInfo.getDataMap();
					dataMap.putAll(newDataMap);
					orginalInfo.setDataList(new ArrayList<>(dataMap.values()));
				}else if(orginalInfo.isExportNoneData()) {
					orginalInfo.setDataExportMode(ExportModelBriefInfo.DATA_EXPORT_PART);
					orginalInfo.setDataList(modelInfo.getDataList());
				}
			}
		}
	}
	public void removeModel(List<ExportModelBriefInfo> modelInfos,String id) {
		int index = indexOfModel(modelInfos,id);
		if(index == -1)
			return;
		modelInfos.remove(index);
	}
	public void addAction(Action data) throws Exception {
		ExportModelBriefInfo modelInfo = getModelBriefInfo(actions, data.getActionModelId());
		if(modelInfo == null) {
			ActionModel actionModel = IActionMgr.get().queryActionModel(data.getActionModelId());
			addActionModel(actionModel);
			modelInfo = getModelBriefInfo(actions, data.getActionModelId());
			modelInfo.setExportModel(false);
			modelInfo.setDataExportMode(ExportModelBriefInfo.DATA_EXPORT_PART);
//			throw new Exception("未添加动作模型" + data.getActionModelId());
		}
		if(modelInfo.isExportNoneData())
			modelInfo.setDataExportMode(ExportModelBriefInfo.DATA_EXPORT_PART);
		modelInfo.addData(toExportData(data));
	}

	public Set<String> getAllFormModelIds(){
		return getAllModelIds(forms);
	}
	public void addFormModel(FormModel model) {
		int index = indexOfModel(this.forms, model.getId());
		ExportModelBriefInfo modelInfo = toExportData(model);
		if(index == -1) {
			this.forms.add(modelInfo);
		}else {
			ExportModelBriefInfo existModel = this.forms.get(index);
			existModel.mergeModelInfo(modelInfo);
			this.forms.set(index, existModel);
		}
	}
	public void addFormModel(ExportModelBriefInfo modelInfo) {
		int index = indexOfModel(this.forms, modelInfo.getId());
		if(index == -1) {
			this.forms.add(modelInfo);
		}else {
			ExportModelBriefInfo existModel = this.forms.get(index);
			existModel.mergeModelInfo(modelInfo);
			this.forms.set(index, existModel);
		}
	}
	public void removeFormModel(String id) {
		int index = indexOfModel(this.forms, id);
		if(index == -1)
			return;
		this.forms.remove(index);
	}
	public void addForm(Form data) throws Exception {
		ExportModelBriefInfo modelInfo = getModelBriefInfo(forms, data.getFormModelId());
		if(modelInfo == null) {
			FormModel formModel = IFormMgr.get().queryFormModel(data.getFormModelId());
			addFormModel(formModel);
			modelInfo = getModelBriefInfo(forms, data.getFormModelId());
			modelInfo.setExportModel(false);
			modelInfo.setDataExportMode(ExportModelBriefInfo.DATA_EXPORT_PART);
//			throw new Exception("未添加表单模型" + data.getFormModelId());
		}
		if(modelInfo.isExportNoneData())
			modelInfo.setDataExportMode(ExportModelBriefInfo.DATA_EXPORT_PART);
		modelInfo.addData(toExportData(data));
	}
	
	public void addNestingFormModel(FormModel model) {
		int index = indexOfModel(this.nestingForms, model.getId());
		ExportModelBriefInfo modelInfo = toExportData(model);
		if(index == -1) {
			this.nestingForms.add(modelInfo);
		}else {
			ExportModelBriefInfo existModel = this.nestingForms.get(index);
			existModel.mergeModelInfo(modelInfo);
			this.nestingForms.set(index, existModel);
		}
	}
	public void addNestingFormModel(ExportModelBriefInfo modelInfo) {
		int index = indexOfModel(this.nestingForms, modelInfo.getId());
		if(index == -1) {
			this.nestingForms.add(modelInfo);
		}else {
			ExportModelBriefInfo existModel = this.nestingForms.get(index);
			existModel.mergeModelInfo(modelInfo);
			this.nestingForms.set(index, existModel);
		}
	}
	public void removeNestingFormModel(String id) {
		int index = indexOfModel(this.nestingForms, id);
		if(index == -1)
			return;
		this.nestingForms.remove(index);
	}
	public Set<String> getAllOrgModelIds(){
		return getAllModelIds(organizations);
	}
	public void addOrgModel(FormModel model) {
		int index = indexOfModel(this.organizations, model.getId());
		ExportModelBriefInfo modelInfo = toExportData(model);
		if(index == -1) {
			this.organizations.add(modelInfo);
		}else {
			ExportModelBriefInfo existModel = this.organizations.get(index);
			existModel.mergeModelInfo(modelInfo);
			this.organizations.set(index, existModel);
		}
	}
	public void addOrgModel(ExportModelBriefInfo modelInfo) {
		int index = indexOfModel(this.organizations, modelInfo.getId());
		if(index == -1) {
			this.organizations.add(modelInfo);
		}else {
			ExportModelBriefInfo existModel = this.organizations.get(index);
			existModel.mergeModelInfo(modelInfo);
			this.organizations.set(index, existModel);
		}
	}
	public void removeOrgModel(String id) {
		int index = indexOfModel(this.organizations, id);
		if(index == -1)
			return;
		this.organizations.remove(index);
	}
	public void addOrg(Org data) throws Exception {
		ExportModelBriefInfo modelInfo = getModelBriefInfo(organizations, data.getFormModelId());
		if(modelInfo == null) {
			FormModel formModel = IRoleMgr.get().queryOrgModel(data.getFormModelId());
			addOrgModel(formModel);
			modelInfo = getModelBriefInfo(organizations, data.getFormModelId());
			modelInfo.setExportModel(false);
			modelInfo.setDataExportMode(ExportModelBriefInfo.DATA_EXPORT_PART);
//			throw new Exception("未添加组织模型" + data.getFormModelId());
		}
		if(modelInfo.isExportNoneData())
			modelInfo.setDataExportMode(ExportModelBriefInfo.DATA_EXPORT_PART);
		modelInfo.addData(toExportData(data));
	}
//	public Map<String, Map<String, ExportModelBriefInfo>> getRoles() {
//		return roles;
//	}
//	public void setRoles(Map<String, Map<String, ExportModelBriefInfo>> roleMap) {
//		this.roles = roleMap;
//	}
	public Set<String> getAllUserModelIds(){
		return getAllModelIds(users);
	}
	public void addUserModel(FormModel model) {
		int index = indexOfModel(this.users, model.getId());
		ExportModelBriefInfo modelInfo = toExportData(model);
		if(index == -1) {
			this.users.add(modelInfo);
		}else {
			ExportModelBriefInfo existModel = this.users.get(index);
			existModel.mergeModelInfo(modelInfo);
			this.users.set(index, existModel);
		}
	}
	public void addUserModel(ExportModelBriefInfo modelInfo) {
		int index = indexOfModel(this.users, modelInfo.getId());
		if(index == -1) {
			this.users.add(modelInfo);
		}else {
			ExportModelBriefInfo existModel = this.users.get(index);
			existModel.mergeModelInfo(modelInfo);
			this.users.set(index, existModel);
		}
	}
	public void removeUserModel(String id) {
		int index = indexOfModel(this.users, id);
		if(index == -1)
			return;
		this.users.remove(index);
	}
	public void addUser(User data) throws Exception {
		ExportModelBriefInfo modelInfo = getModelBriefInfo(users, data.getFormModelId());
		if(modelInfo == null) {
			FormModel formModel = IUserMgr.get().queryUserModel(data.getFormModelId());
			addUserModel(formModel);
			modelInfo = getModelBriefInfo(users, data.getFormModelId());
			modelInfo.setExportModel(false);
			modelInfo.setDataExportMode(ExportModelBriefInfo.DATA_EXPORT_PART);
//			throw new Exception("未添加用户模型" + data.getFormModelId());
		}
		if(modelInfo.isExportNoneData())
			modelInfo.setDataExportMode(ExportModelBriefInfo.DATA_EXPORT_PART);
		modelInfo.addData(toExportData(data));
	}
	public Set<String> getAllCdcIds(){
		return getAllModelIds(cdcs);
	}
	public void addCDC(CDC model) {
		int index = indexOfModel(this.cdcs, model.getId());
		ExportModelBriefInfo modelInfo = toExportData(model);
		if(index == -1) {
			this.cdcs.add(modelInfo);
		}else {
			ExportModelBriefInfo existModel = this.cdcs.get(index);
			existModel.mergeModelInfo(modelInfo);
			this.cdcs.set(index, existModel);
		}
	}
	
	public void addCDC(ExportModelBriefInfo modelInfo) {
		int index = indexOfModel(this.cdcs, modelInfo.getId());
		if(index == -1) {
			this.cdcs.add(modelInfo);
		}else {
			ExportModelBriefInfo existModel = this.cdcs.get(index);
			existModel.mergeModelInfo(modelInfo);
			this.cdcs.set(index, existModel);
		}
	}
	public void removeCDC(String id) {
		int index = indexOfModel(this.cdcs, id);
		if(index == -1)
			return;
		this.cdcs.remove(index);
	}
	public void addPDC(PDC data) throws Exception {
		ExportModelBriefInfo modelInfo = getModelBriefInfo(cdcs, data.getCdcId());
		if(modelInfo == null) {
			CDC formModel = ICDCMgr.get().queryCDC(data.getCdcId());
			addCDC(formModel);
			modelInfo = getModelBriefInfo(cdcs, data.getCdcId());
			modelInfo.setExportModel(false);
			modelInfo.setDataExportMode(ExportModelBriefInfo.DATA_EXPORT_PART);
//			throw new Exception("未添加节点" + data.getCdcId());
		}
		if(modelInfo.isExportNoneData())
			modelInfo.setDataExportMode(ExportModelBriefInfo.DATA_EXPORT_PART);
		modelInfo.addData(toExportData(data));
	}
	public void addPDC(String cdcId,ExportDataBriefInfo data) throws Exception {
		ExportModelBriefInfo modelInfo = getModelBriefInfo(cdcs, cdcId);
		if(modelInfo == null) {
			CDC formModel = ICDCMgr.get().queryCDC(cdcId);
			addCDC(formModel);
			modelInfo = getModelBriefInfo(cdcs, cdcId);
			modelInfo.setExportModel(false);
			modelInfo.setDataExportMode(ExportModelBriefInfo.DATA_EXPORT_PART);
//			throw new Exception("未添加节点" + cdcId);
		}
		if(modelInfo.isExportNoneData())
			modelInfo.setDataExportMode(ExportModelBriefInfo.DATA_EXPORT_PART);
		modelInfo.addData(data);
	}
	public Set<String> getAllPdfIds(){
		return getAllModelIds(pdfs);
	}
	public void addPDF(PDF pdf) {
		int index = indexOfModel(this.pdfs, pdf.getUuid());
		ExportModelBriefInfo modelInfo = toExportData(pdf);
		if(index == -1) {
			this.pdfs.add(modelInfo);
		}else {
			this.pdfs.set(index, modelInfo);
		}
	}
	public void addPDF(ExportModelBriefInfo modelInfo) {
		int index = indexOfModel(this.pdfs, modelInfo.getId());
		if(index == -1) {
			this.pdfs.add(modelInfo);
		}else {
			this.pdfs.set(index, modelInfo);
		}
	}
	public void removePDF(String id) {
		int index = indexOfModel(this.pdfs, id);
		if(index == -1)
			return;
		this.pdfs.remove(index);
	}
	/**
	 * 合并导出配置到当前配置
	 * @param setting
	 */
	public void mergeExportSetting(ExportSetting setting) {
		List<ExportModelBriefInfo> newActions = mergeExportModelInfos(getActions(), setting.getActions());
		setActions(newActions);
		List<ExportModelBriefInfo> newForms = mergeExportModelInfos(getForms(), setting.getForms());
		setForms(newForms);
		List<ExportModelBriefInfo> newNestingForms = mergeExportModelInfos(getNestingForms(), setting.getNestingForms());
		setNestingForms(newNestingForms);
		List<ExportModelBriefInfo> newOrgs = mergeExportModelInfos(getOrganizations(), setting.getOrganizations());
		setOrganizations(newOrgs);
		List<ExportModelBriefInfo> newUsers = mergeExportModelInfos(getUsers(), setting.getUsers());
		setUsers(newUsers);
		List<ExportModelBriefInfo> newCdcs = mergeExportModelInfos(getCdcs(), setting.getCdcs());
		setCdcs(newCdcs);
		List<ExportModelBriefInfo> newPdfs = mergeExportModelInfos(getPdfs(), setting.getPdfs());
		setPdfs(newPdfs);
		Map<String,ExportDataBriefInfo> roleMap = getRoleMap();
		Map<String,ExportDataBriefInfo> newRoleMap = setting.getRoleMap();
		roleMap.putAll(newRoleMap);
		setRoles(new ArrayList<>(roleMap.values()));
	}
	
	private List<ExportModelBriefInfo> mergeExportModelInfos(List<ExportModelBriefInfo> orginalList,List<ExportModelBriefInfo> newList) {
		if(CmnUtil.isCollectionEmpty(newList))
			return orginalList;
		List<ExportModelBriefInfo> allList = new ArrayList<>();
		allList.addAll(orginalList);
		Map<String,ExportModelBriefInfo> modelMap = orginalList.stream().collect(Collectors.toMap(ExportModelBriefInfo::getId, v->v));
		for(ExportModelBriefInfo briefInfo : newList) {
			if(modelMap.containsKey(briefInfo.getId())) {
				ExportModelBriefInfo orginalInfo = modelMap.get(briefInfo.getId());
				if(briefInfo.isExportModel()) {
					orginalInfo.setExportModel(true);
				}
				if(briefInfo.isExportAllData()) {
					orginalInfo.setDataExportMode(ExportModelBriefInfo.DATA_EXPORT_ALL);
					orginalInfo.setDataList(new ArrayList<>());
				}else if(briefInfo.isExportPartData()) {
					if(orginalInfo.isExportAllData()) {
						
					}else if(orginalInfo.isExportPartData()){
						Map<String,ExportDataBriefInfo> dataMap = orginalInfo.getDataMap();
						Map<String,ExportDataBriefInfo> newDataMap = briefInfo.getDataMap();
						dataMap.putAll(newDataMap);
						orginalInfo.setDataList(new ArrayList<>(dataMap.values()));
					}else if(orginalInfo.isExportNoneData()) {
						orginalInfo.setDataExportMode(ExportModelBriefInfo.DATA_EXPORT_PART);
						orginalInfo.setDataList(briefInfo.getDataList());
					}
				}
						
			}else {
				allList.add(briefInfo);
			}
		}
		return allList;
	}
	
	public static ExportModelBriefInfo toExportData(FormModel model) {
		ExportModelBriefInfo info = new ExportModelBriefInfo();
		info.setId(model.getId()).setLabel(model.getLabel()).setDescription(model.getDescription());
		info.setDataExportMode(ExportModelBriefInfo.DATA_EXPORT_NONE);
		return info;
	}
	public static ExportDataBriefInfo toExportData(Form data) throws Exception {
		ExportDataBriefInfo info = new ExportDataBriefInfo();
		info.setModelId(data.getFormModelId());
		info.setId(data.getStringByCode(Form.Code)).setLabel(data.getStringByCode(Form.Code)).setDescription("");
		return info;
	}
	public static ExportDataBriefInfo toExportData(Role data) throws Exception {
		ExportDataBriefInfo info = new ExportDataBriefInfo();
		info.setModelId(Role.ModelId);
		info.setId(data.getCode()).setLabel(data.getLabel()).setDescription("");
		return info;
	}
	public static ExportModelBriefInfo toExportData(PDF pdf) {
		ExportModelBriefInfo info = new ExportModelBriefInfo();
		info.setId(pdf.getUuid()).setLabel(pdf.getLabel()).setDescription(pdf.getDescription());
		info.setDataExportMode(ExportModelBriefInfo.DATA_EXPORT_NONE);
		return info;
	}
	/**
	 * 对数据进行去重
	 */
	public void distinct() {
		setActions(distinctModelInfos(actions));
		setForms(distinctModelInfos(forms));
		setNestingForms(distinctModelInfos(nestingForms));
		setUsers(distinctModelInfos(users));
		setOrganizations(distinctModelInfos(organizations));
		setCdcs(distinctModelInfos(cdcs));
		setRoles(distinctDataInfos(roles));
		setPdfs(distinctModelInfos(pdfs));
	}
	
	public List<ExportModelBriefInfo> distinctModelInfos(List<ExportModelBriefInfo> modelInfos){
		Map<String,ExportModelBriefInfo> map = new LinkedHashMap<>();
		for(ExportModelBriefInfo modelInfo : modelInfos) {
			List<ExportDataBriefInfo> dataList = distinctDataInfos(modelInfo.getDataList());
			modelInfo.setDataList(dataList);
			map.put(modelInfo.getId(), modelInfo);
		}
		return new ArrayList<>(map.values());
	}
	
	public List<ExportDataBriefInfo> distinctDataInfos(List<ExportDataBriefInfo> dataInfos){
		Map<String,ExportDataBriefInfo> map = new LinkedHashMap<>();
		for(ExportDataBriefInfo dataInfo : dataInfos) {
			map.put(dataInfo.getId(), dataInfo);
		}
		return new ArrayList<>(map.values());
	}

	public List<Category> getCategorys() {
		return categorys;
	}
	public void setCategorys(List<Category> categorys) {
		this.categorys = categorys;
	}
	
	public void removeAdded(AddedExportSetting addedSetting) {
		removeAddedModel(actions, addedSetting.getActionModels(),addedSetting.getActions());
		removeAddedModel(forms, addedSetting.getFormModels(), addedSetting.getForms());
		removeAddedModel(nestingForms, addedSetting.getNestingFormModels(), new LinkedHashMap<>());
		removeAddedModel(users, addedSetting.getUserModels(), addedSetting.getUsers());
		removeAddedModel(organizations, addedSetting.getOrganizationModels(), addedSetting.getOrganizations());
		removeAddedModelData(roles, addedSetting.getRoles());
		removeAddedModel(cdcs, addedSetting.getCdcs(), addedSetting.getPdcs());
		removeAddedModel(pdfs, addedSetting.getPdfs(), new LinkedHashMap<>());
	}
	
	private void removeAddedModel(List<ExportModelBriefInfo> modelInfos,Map<String,Pair<ExportModelBriefInfo,Set<String>>> addedModels,Map<String,Pair<ExportDataBriefInfo,Set<String>>> addedDatas) {
		for(Iterator<ExportModelBriefInfo> it = modelInfos.iterator();it.hasNext();) {
			ExportModelBriefInfo modelInfo = it.next();
			if(modelInfo.isExportPartData()) {
				removeAddedModelData(modelInfo.getDataList(), addedDatas);
			}
			if(modelInfo.isExportModel()) {
				if(addedModels.containsKey(modelInfo.getId())) {
					if(modelInfo.isExportModel()) {
						modelInfo.setExportModel(false);
					}
					if(modelInfo.isExportPartData()) {
						if(CmnUtil.isCollectionEmpty(modelInfo.getDataList()))
							it.remove();
					}else if(modelInfo.isExportNoneData()) {
						it.remove();
					}
				}
			}
		}
	}
	
	private void removeAddedModelData(List<ExportDataBriefInfo> dataInfos,Map<String,Pair<ExportDataBriefInfo,Set<String>>> addedDatas) {
		for(Iterator<ExportDataBriefInfo> it = dataInfos.iterator();it.hasNext();) {
			ExportDataBriefInfo dataInfo = it.next();
			if(addedDatas.containsKey(dataInfo.getModelId()+"@"+dataInfo.getId())) {
				it.remove();
			}
		}
	}
}
