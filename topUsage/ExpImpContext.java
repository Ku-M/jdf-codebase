package gpf.dc.expimp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.cdao.model.CDoModel;
import com.kwaidoo.ms.tool.CmnUtil;

import cell.cdao.IDao;
import cell.gpf.adur.action.IActionMgr;
import cell.gpf.adur.data.IFormMgr;
import cell.gpf.dc.concrete.ICDCMgr;
import cell.gpf.dc.config.IPDCMgr;
import cell.gpf.dc.runtime.IDCRuntimeContext;
import gpf.adur.action.Action;
import gpf.adur.action.ActionModel;
import gpf.adur.data.Form;
import gpf.adur.data.FormModel;
import gpf.dc.concrete.CDC;
import gpf.dc.config.PDC;
import gpf.dc.config.PDF;
import gpf.dc.i18n.GpfDCI18n;
import gpf.dc.intf.FormOpObserver;

/**
 * @author chenxb
 *
 */
public class ExpImpContext implements Serializable{

	IDao dao;
	String user;
	Map<String,FormModel> formModelCache = new LinkedHashMap<>();
	Map<String,ActionModel> actionModelCache = new LinkedHashMap<>();
	Map<String,CDC> cdcCache = new LinkedHashMap<>();
	Map<String,Form> dependForm = new LinkedHashMap<>();
	Map<String,Form> dependUuidForm = new LinkedHashMap<>();
	Map<String,Action> actionCodeCache = new LinkedHashMap<>();
	Map<String,PDC> pdcCodeCache = new LinkedHashMap<>();
	
	ExportSetting exportSetting;
	//解析数据包后得到的导入模型列表，包含动作、数据、角色、用户、节点、流程所有模型
	public List<FormModel> importModels = new ArrayList<>();
	public List<PDF> importPdfs = new ArrayList<>();
//	RelateDataPackage relateDataPack = new RelateDataPackage();
	FormOpObserver formOpObserver;
	IDCRuntimeContext rtx;
	Map<String,Object> contextMap = new LinkedHashMap<>();
	public IDao getDao() {
		return dao;
	}
	public void setDao(IDao dao) throws Exception {
		this.dao = dao;
		if(rtx != null) {
			rtx.setDao(dao);
		}
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) throws Exception {
		this.user = user;
		if(rtx != null) {
			rtx.setOperator(user);
		}
	}
	
	public FormOpObserver getFormOpObserver() {
		return formOpObserver;
	}
	
	public ExpImpContext setFormOpObserver(FormOpObserver formOpObserver) {
		this.formOpObserver = formOpObserver;
		return this;
	}
	
//	public RelateDataPackage getRelateDataPack() {
//		return relateDataPack;
//	}
	
	public ExportSetting getExportSetting() {
		return exportSetting;
	}
	public void setExportSetting(ExportSetting exportSetting) {
		this.exportSetting = exportSetting;
	}
	
	public FormModel getFormModel(String formModelId) throws Exception {
		if(!formModelCache.containsKey(formModelId)) {
			FormModel model = IFormMgr.get().queryFormModel(formModelId);
			if(model != null)
				formModelCache.put(formModelId, model);
		}
		return formModelCache.get(formModelId);
	}
	
	public ActionModel getActionModel(String actionModelId) throws Exception {
		if(!actionModelCache.containsKey(actionModelId)) {
			ActionModel model = IActionMgr.get().queryActionModel(actionModelId);
			if(model != null)
				actionModelCache.put(actionModelId, model);
		}
		return actionModelCache.get(actionModelId);
	}
	
	
	public CDC getCDC(String cdcId) throws Exception {
		if(!cdcCache.containsKey(cdcId)) {
			CDC model = ICDCMgr.get().queryCDC(cdcId);
			if(model != null)
				cdcCache.put(cdcId, model);
		}
		return cdcCache.get(cdcId);
	}
	
	public Form getDependForm(String dependKey) throws Exception {
		if(!dependForm.containsKey(dependKey)) {
			int index = dependKey.indexOf(":");
			if(index != -1) {
				String formModelId = dependKey.substring(0, index);
				String code = dependKey.substring(index+1);
				if(CmnUtil.isStringEqual(formModelId, CDoModel.class.getName())) {
					FormModel model = getFormModel(code);
					if(model == null)
						throw new Exception(GpfDCI18n.getString(GpfDCI18n.TIPS_DEPEND_DATA_IS_NOT_EXIST, dependKey));
					Form form = new Form(formModelId);
					form.setUuid(model.getUuid());
					return form;
				}else {
					Form form = IFormMgr.get().queryFormByCode(dao, formModelId, code);
					if(form != null)
						dependForm.put(dependKey, form);
					else
						throw new Exception(GpfDCI18n.getString(GpfDCI18n.TIPS_DEPEND_DATA_IS_NOT_EXIST, formModelId + ",code = "+code));
				}
			}
		}
		return dependForm.get(dependKey);
	}
	
	public Form getDependFormByUuid(String dependUuid) throws Exception {
		if(!dependUuidForm.containsKey(dependUuid)) {
			Form form = IFormMgr.get().queryFormByUuid(dependUuid, null);
			if(form != null)
				dependUuidForm.put(dependUuid, form);
		}
		return dependUuidForm.get(dependUuid);
	}
	
	public Action getActionByCode(String actionModelId,String code) throws Exception {
		if(!actionCodeCache.containsKey(actionModelId+":"+code)) {
			Action form = IActionMgr.get().queryActionByCode(dao, actionModelId, code);
			if(form != null)
				actionCodeCache.put(actionModelId+":"+code, form);
		}
		return actionCodeCache.get(actionModelId+":"+code);
	}
	
	public PDC getPDCByCode(String cdcId,String code) throws Exception {
		if(!pdcCodeCache.containsKey(cdcId+":"+code)) {
			PDC form = IPDCMgr.get().queryPDCByCode(dao, cdcId, code);
			if(form != null)
				pdcCodeCache.put(cdcId+":"+code, form);
		}
		return pdcCodeCache.get(cdcId+":"+code);
	}
	
	public List<FormModel> getImportModels() {
		return importModels;
	}
	public void setImportModels(List<FormModel> importModels) {
		this.importModels = importModels;
	}
	
	public void addImportModel(FormModel formModel) {
		importModels.add(formModel);
	}
	
	public List<PDF> getImportPdfs() {
		return importPdfs;
	}
	public void setImportPdfs(List<PDF> importPdfs) {
		this.importPdfs = importPdfs;
	}
	public void addImportPDF(PDF pdf) {
		importPdfs.add(pdf);
	}
	
	public IDCRuntimeContext getRtx() {
		return rtx;
	}
	public ExpImpContext setRtx(IDCRuntimeContext rtx) {
		this.rtx = rtx;
		return this;
	}
	public Map<String, Object> getContextMap() {
		return contextMap;
	}
	public ExpImpContext setContextMap(Map<String, Object> contextMap) {
		this.contextMap = contextMap;
		return this;
	}
}
