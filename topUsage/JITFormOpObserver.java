package jit.observer;

import java.util.List;

import com.cdao.model.CDoRole;
import com.kwaidoo.ms.tool.CmnUtil;
import com.kwaidoo.ms.tool.ToolUtilities;
import com.leavay.common.util.javac.ClassFactory;
import com.leavay.dfc.gui.LvUtil;

import bap.cells.Cells;
import cell.CellIntf;
import cell.cdao.IDao;
import cell.cdao.IDaoService;
import cell.gpf.adur.data.IFormMgr;
import cell.jit.IActionModelDefineService;
import cell.jit.IDataModelDefineService;
import cell.jit.IWorkSpaceService;
import cmn.dto.Progress;
import cmn.dto.model.extend.intf.ObserverContext;
import cn.hutool.core.collection.CollUtil;
import gpf.adur.data.AssociationData;
import gpf.adur.data.Form;
import gpf.adur.role.Role;
import gpf.adur.user.User;
import gpf.dc.basic.dto.GlobalVariableDto;
import gpf.dc.basic.dto.privilege.PrivilegeMatrix;
import gpf.dc.intf.FormOpObserver;
import gpf.dc.runtime.PDCForm;
import gpf.dc.util.DtoConvertUtil;
import gpf.exception.VerifyException;
import jit.dto.ActionModelDefineDto;
import jit.dto.DataItemStandardDto;
import jit.dto.DataModelDefineDto;
import jit.dto.JitSettingDto;
import jit.dto.WorkSpace;
import jit.dto.WorkSpaceLink;
import jit.dto.flowcombine.ActionDefineDto;
import jit.dto.flowcombine.BusinessFlowDto;
import jit.dto.flowcombine.DataModelDto;
import jit.dto.flowcombine.FlowCombineDto;
import jit.dto.layout.MobileFormLayoutDto;
import jit.dto.layout.MobileListLayoutDto;
import jit.dto.layout.PCFormLayoutDto;
import jit.dto.layout.PCTableLayoutDto;
import jit.dto.style.FormFieldStyleDto;
import jit.dto.style.PCTableStyleDto;
import jit.dto.style.SearchStyleDto;
import jit.excel.dto.view.ViewConfigSheetDto;

public class JITFormOpObserver implements FormOpObserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5794502030924041778L;
	String orgModelId;
	User user;
	WorkSpace workSpace;
	public JITFormOpObserver(User user,String orgModelId,WorkSpace workSpace) {
		this.user = user;
		this.orgModelId = orgModelId;
		this.workSpace = workSpace;
	}
	public String getOrgModelId() {
		return orgModelId;
	}
	public JITFormOpObserver setOrgModelId(String orgModelId) {
		this.orgModelId = orgModelId;
		return this;
	}
	public User getUser() {
		return user;
	}
	public JITFormOpObserver setUser(User user) {
		this.user = user;
		return this;
	}
	public WorkSpace getWorkSpace() {
		return workSpace;
	}
	public JITFormOpObserver setWorkSpace(WorkSpace workSpace) {
		this.workSpace = workSpace;
		return this;
	}
	transient JitSettingDto jitSetting;
	public JitSettingDto getJitSetting() throws Exception {
		if(jitSetting == null) {
			try(IDao dao = IDaoService.newIDao()){
				Form form = IFormMgr.get().queryFormByCode(dao, JitSettingDto.FormModelId, JitSettingDto.DefaultSettinCode);
				if(form == null)
					return null;
				jitSetting = DtoConvertUtil.convertToDto(form, JitSettingDto.class);
				return jitSetting;
			}
		}
		return jitSetting;
	}
	public ActionInstanceImportObserver getActionInstanceImportObserver() throws Exception {
		JitSettingDto jitSetting = getJitSetting();
		if(jitSetting == null) {
			return null;
		}
		String javaClass = jitSetting.getActionInstanceImportObserver();
		if(CmnUtil.isStringEmpty(javaClass))
			return null;
		try {
			Class<? extends ActionInstanceImportObserver> clazz = ClassFactory.loadClass(javaClass);
			if(CellIntf.class.isAssignableFrom(clazz)) {
				return Cells.get(clazz);
			}else {
				return clazz.newInstance();
			}
		}catch (Exception e) {
			return null;
		}
	}
	public String getCodePrefix() {
		return workSpace.getLabel()+"_";
	}
	/**
	 * 根据名称构建编号
	 * @param name
	 * @return
	 */
	public String buildFormCode(String name) {
		return getCodePrefix()+name;
	}
	/**
	 * 获取底层视图模型的名称，截取掉工作空间
	 * @param name
	 * @return
	 */
	public String getViewName(String name) {
		String prefix = workSpace.getLabel()+"_";
		if(name.startsWith(prefix)) {
			name = "$工作空间_"+name.substring(prefix.length());
		}
		return name;
	}
	
	public String getViewCode(String name) {
		String prefix = workSpace.getLabel()+"_";
		if(name.startsWith("$工作空间_")) {
			name = prefix+name.substring("$工作空间_".length());
		}
		return name;
	}
	
	/**
	 * 根据编号获取名称
	 * @param code
	 * @return
	 */
	public String getNameByFormCode(String code) {
		String codePrefix = getCodePrefix();
		if(code.startsWith(codePrefix))
			return code.substring(codePrefix.length());
		return code;
	}
	public final static String getCodePrefix(WorkSpace workspace) {
		return workspace.getLabel()+"_";
	}
	public final static List<String> nameModelIds = CollUtil.newArrayList(
			FlowCombineDto.FormModelId,
			DataModelDto.FormModelId,ActionDefineDto.FormModelId,PrivilegeMatrix.FormModelId
			,PCTableStyleDto.FormModelId,SearchStyleDto.FormModelId,FormFieldStyleDto.FormModelId
			,PCTableLayoutDto.FormModelId,PCFormLayoutDto.FormModelId,MobileListLayoutDto.FormModelId,MobileFormLayoutDto.FormModelId
			,"gpf.md.jit.JITSqlMappingDefine",DataItemStandardDto.FormModelId);
	
	public final static List<String> timeStampModelIds = CollUtil.newArrayList(
			FlowCombineDto.FormModelId
			,ViewConfigSheetDto.FormModelId
			,PCTableStyleDto.FormModelId,SearchStyleDto.FormModelId,FormFieldStyleDto.FormModelId
			,PCTableLayoutDto.FormModelId,PCFormLayoutDto.FormModelId,MobileListLayoutDto.FormModelId,MobileFormLayoutDto.FormModelId
			,"gpf.md.jit.JITSqlMappingDefine");
	@Override
	public void onBeforeCreate(Progress prog, ObserverContext context) throws Exception {
		//根据名称补充编号
		Object data = context.getBeforeOpData();
		if(data instanceof Form) {
			Form form = (Form) data;
			setFormCode(form);
			setTimeStamp(form);
		}		
	}
	/**
	 * 设置时间戳
	 * @param form
	 * @throws Exception
	 */
	private void setTimeStamp(Form form) throws Exception {
		if(timeStampModelIds.contains(form.getFormModelId())) {
			Long createTime = form.getLong(PCFormLayoutDto.sCreateTime);
			Long updateTime = System.currentTimeMillis();
			if(createTime == null) {
				createTime = updateTime;
			}
			form.setAttrValue(PCFormLayoutDto.sCreateTime, createTime);
			form.setAttrValue(PCFormLayoutDto.sUpdateTime, updateTime);
		}
	}
	
	public String getNameByCode(AssociationData assocData) throws Exception {
		if(assocData == null)
			return "";
		String formModelId = assocData.getFormModelId();
//		if(CmnUtil.isStringEqual(formModelId, GlobalVariableDto.FormModelId)) {
//			Form form = assocData.getForm();
//			return form.getString(GlobalVariableDto.VariableName);
//		}else 
			if(nameModelIds.contains(formModelId)) {
			Form form = assocData.getForm();
			return form.getString(FlowCombineDto.sName);
		}else if(CmnUtil.isStringEqual(BusinessFlowDto.FormModelId, formModelId)) {
			Form form = assocData.getForm();
			return form.getString(BusinessFlowDto.CnName);
		}else if(CmnUtil.isStringEqual(ViewConfigSheetDto.FormModelId, formModelId)) {
			Form form = assocData.getForm();
			return form.getString(ViewConfigSheetDto.sName);
		}else if(CmnUtil.isStringEqual(DataModelDefineDto.FormModelId, formModelId)) {
			Form form = assocData.getForm();
			return form.getString(DataModelDefineDto.sCnName);
		}else if(CmnUtil.isStringEqual(ActionModelDefineDto.FormModelId, formModelId)) {
			Form form = assocData.getForm();
			return form.getString(ActionModelDefineDto.sActionName);
		}else {
			return assocData.getValue();
		}
	}
	
	public String getCodeByName(IDao dao,String formModelId,String name) throws Exception {
		if(CmnUtil.isStringEmpty(name))
			return "";
//		if(CmnUtil.isStringEqual(formModelId, GlobalVariableDto.FormModelId)) {
//			return buildFormCode(name);
//		}else 
			if(nameModelIds.contains(formModelId)) {
			return buildFormCode(name);
		}else if(CmnUtil.isStringEqual(BusinessFlowDto.FormModelId, formModelId)) {
			return buildFormCode(name+"_业务流图");
		}else if(CmnUtil.isStringEqual(ViewConfigSheetDto.FormModelId, formModelId)) {
			return buildFormCode(name);
		}else if(CmnUtil.isStringEqual(DataModelDefineDto.FormModelId, formModelId)) {
			Form form = IDataModelDefineService.get().queryDataModelDefineByCnName(dao, name, this);
			if(form == null)
				throw new VerifyException("数据模型["+name+"]不存在");
			return form.getStringByCode(Form.Code);
		}else if(CmnUtil.isStringEqual(ActionModelDefineDto.FormModelId, formModelId)) {
			Form form = IActionModelDefineService.get().queryModelDefineByName(dao, name, this);
			if(form == null)
				throw new VerifyException("动作模型["+name+"]不存在");
			return form.getStringByCode(Form.Code);
		}else {
			return name;
		}
	}
	
	
	public void setFormCode(Form form) throws Exception {
		if(CmnUtil.isStringEqual(form.getFormModelId(), GlobalVariableDto.FormModelId)) {
			form.setAttrValueByCode(GlobalVariableDto.Code, buildFormCode(form.getString(GlobalVariableDto.VariableName)));
		}else if(nameModelIds.contains(form.getFormModelId())) {
			form.setAttrValueByCode(Form.Code, buildFormCode(form.getString(FlowCombineDto.sName)));
		}else if(CmnUtil.isStringEqual(BusinessFlowDto.FormModelId, form.getFormModelId())) {
			form.setAttrValueByCode(Form.Code, buildFormCode(form.getString(BusinessFlowDto.CnName)+"_业务流图"));
		}else if(CmnUtil.isStringEqual(ViewConfigSheetDto.FormModelId, form.getFormModelId())) {
			form.setAttrValueByCode(Form.Code, buildFormCode(form.getString(ViewConfigSheetDto.sName)));
		}
	}

	@Override
	public void onAfterCreate(Progress prog, ObserverContext context) throws Exception {
		Object data = context.getAfterOpData();
		saveWorkspaceLink(context.getDao(), data);
	}
	
	public void saveWorkspaceLink(IDao dao,Object data) throws Exception {
		WorkSpaceLink link = new WorkSpaceLink();
		link.setCode(ToolUtilities.allockUUIDWithUnderline());
		link.setWorkSpace(workSpace.getCode()).setWorkSpaceUuid(workSpace.getUuid());
		if(data instanceof PDCForm) {
			PDCForm form = (PDCForm) data;
			LvUtil.trace(form.getData());
			LvUtil.trace(((PDCForm) form).getPdfUuid());
			link.setRelDataModel(((PDCForm) form).getPdfUuid()).setRelDataUuid(form.getUuid()).setRelDataLabel(form.getStringByCode(Form.Code));
		}else if(data instanceof Form){ 
			Form form = (Form) data;
			link.setRelDataModel(form.getFormModelId()).setRelDataUuid(form.getUuid()).setRelDataLabel(form.getStringByCode(Form.Code));
		}else if(data instanceof Role){ 
			Role form = (Role) data;
			link.setRelDataModel(CDoRole.class.getName()).setRelDataUuid(form.getUuid()).setRelDataLabel(form.getLabel());
		}else {
			throw new Exception("Unsupport data Type : " + data);
		}
		IWorkSpaceService.get().saveWorkSpaceLink(dao, link);
	}

	@Override
	public void onBeforeUpdate(Progress prog, ObserverContext context) throws Exception {
		Object data = context.getBeforeOpData();
		if(data instanceof Form) {
			Form form = (Form) data;
			setFormCode(form);
			setTimeStamp(form);
		}	
	}

	@Override
	public void onAfterUpdate(Progress prog, ObserverContext context) throws Exception {
		Object data = context.getAfterOpData();
		if(data instanceof PDCForm) {
			return;
		}
		saveWorkspaceLink(context.getDao(), data);
	}

	@Override
	public void onBeforeDelete(Progress prog, ObserverContext context) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAfterDelete(Progress prog, ObserverContext context) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBeforeBatchDelete(Progress prog, ObserverContext context) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAfterBatchDelete(Progress prog, ObserverContext context) throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onBeforeBatchCreate(Progress prog, ObserverContext observerContext) throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onAfterBatchCreate(Progress prog, ObserverContext observerContext) throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onBeforeBatchUpdate(Progress prog, ObserverContext observerContext) throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onAfterBatchUpdate(Progress prog, ObserverContext observerContext) throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onAfterImport(Progress prog, ObserverContext context) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
