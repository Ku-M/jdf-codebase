package gpf.dc.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kwaidoo.ms.tool.CmnUtil;
import com.kwaidoo.ms.tool.ToolUtilities;

import cell.gpf.adur.data.IFormMgr;
import cmn.util.NullUtil;
import gpf.adur.data.FormField;
import gpf.adur.data.FormModel;
import gpf.dc.concrete.ControlFlow;
import gpf.dc.concrete.FlowLink;
import gpf.dc.concrete.RefFormField;

public class PDF extends ControlFlow<RefPDCNode> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8547570703859453646L;
	String label;
	String packagePath;
	/**
	 * 上级模型ID
	 */
	String parentId;
	//产线表单
	String formModelID;
	//流程属性
	List<RefFormField> fieldList = new ArrayList<>();
	//节点状态变更监听钩子
	List<OperateLogStatusHookDto> operateLogStatusHooks = new ArrayList<>();
	
	boolean isSystemModel = false;
	
	PDFSetting setting;
	
	public String getPackagePath() {
		return packagePath;
	}
	public PDF setPackagePath(String packagePath) {
		this.packagePath = packagePath;
		return this;
	}
	public String getParentId() {
		return parentId;
	}
	public PDF setParentId(String parentId) {
		this.parentId = parentId;
		return this;
	}
	
	public PDC getPDC(String instID) throws Exception {
		RefPDCNode node = getDCNodeByInstanceId(instID);
		if(node == null) {
			return null;
		}else {
			PDC inst = node.getData();
			return inst;
		}
		
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * PDF表单模型ID
	 * @return
	 */
	public String getFormModelID() {
		return formModelID;
	}
	public PDF setFormModelID(String formModelID) {
		this.formModelID = formModelID;
		return this;
	}
	/**
	 * PDF数据属性列表
	 * @return
	 */
	public List<RefFormField> getFieldList() {
		return fieldList;
	}
	public PDF setFieldList(List<RefFormField> fieldList) {
		this.fieldList = fieldList;
		return this;
	}
	
	public Map<String,RefFormField> getDCFieldMap(){
		Map<String,RefFormField> map = new LinkedHashMap<>();
		for(RefFormField field : NullUtil.get(fieldList)) {
			map.put(field.getCode(), field);
		}
		return map;
	}
	
	public boolean isSystemModel() {
		return isSystemModel;
	}
	
	public List<RefPDCNode> getTargetNodes(String srcNodeKey){
		Map<String,RefPDCNode> nodeMap = getNodeMap();
		List<RefPDCNode> targetNodes = new ArrayList<>();
		for(FlowLink link : NullUtil.get(getLinks())) {
			if(CmnUtil.isStringEqual(link.getSrc(), srcNodeKey)) {
				RefPDCNode targetNode = nodeMap.get(link.getDst());
				if(targetNode != null)
					targetNodes.add(targetNode);
			}
		}
		return targetNodes;
	}
	
	public List<RefPDCNode> getSourceNodes(String targetNodeKey){
		Map<String,RefPDCNode> nodeMap = getNodeMap();
		List<RefPDCNode> srcNodes = new ArrayList<>();
		for(FlowLink link : NullUtil.get(getLinks())) {
			if(CmnUtil.isStringEqual(link.getDst(), targetNodeKey)) {
				RefPDCNode srcNode = nodeMap.get(link.getSrc());
				if(srcNode != null)
					srcNodes.add(srcNode);
			}
		}
		return srcNodes;
	}
	
	private List<RefPDCNode> _getAllSourceNodes(String targetNodeKey,Set<String> excludeNodes){
		if(excludeNodes.contains(targetNodeKey))
			return new ArrayList<>();
		Map<String,RefPDCNode> nodeMap = (Map<String, RefPDCNode>) getNodeMap();
		List<RefPDCNode> srcNodes = new ArrayList<>();
		excludeNodes.add(targetNodeKey);
		for(FlowLink link : NullUtil.get(getLinks())) {
			if(CmnUtil.isStringEqual(link.getDst(), targetNodeKey)) {
				RefPDCNode srcNode = nodeMap.get(link.getSrc());
				if(srcNode != null) {
					if(excludeNodes.contains(srcNode.getKey()))
						continue;
					srcNodes.add(srcNode);
					List<RefPDCNode> srcSrcNodes = _getAllSourceNodes(srcNode.getKey(), excludeNodes);
					srcNodes.addAll(srcSrcNodes);
				}
			}
		}
		return srcNodes;
	}
	
	public List<RefPDCNode> getAllSourceNodes(String targetNodeKey){
		Set<String> excludeNodes = new LinkedHashSet<>();
		return _getAllSourceNodes(targetNodeKey, excludeNodes);
	}
	
	public Map<String,List<PDFFormField>> getPDFFormModelFields() throws Exception{
		Map<String,List<PDFFormField>> map = new LinkedHashMap<>();
		FormModel formModel = IFormMgr.get().queryFormModel(formModelID);
		List<String> hiddenFields = formModel.getHiddenFields();
		Map<String,FormField> fieldMap = formModel.getFieldMap();
		for(RefFormField dcField : fieldList) {
			FormField field = fieldMap.remove(dcField.getCode());
			if(field != null) {
				List<PDFFormField> refFieldList = map.get(dcField.getFormModelID());
				if(refFieldList == null) {
					refFieldList = new ArrayList<>();
					map.put(dcField.getFormModelID(), refFieldList);
				}
				PDFFormField pdfFormField = new PDFFormField();
				ToolUtilities.copyFields(field, pdfFormField);
				pdfFormField.setRefFormModelId(dcField.getFormModelID()).setRefFormFieldCode(dcField.getFormFieldCode());
				refFieldList.add(pdfFormField);
			}
		}
		List<PDFFormField> sysFields = new ArrayList<>();
		for(FormField field : fieldMap.values()) {
			if(!hiddenFields.contains(field.getCode())) {
				PDFFormField pdfFormField = new PDFFormField();
				ToolUtilities.copyFields(field, pdfFormField);
				sysFields.add(pdfFormField);
			}
		}
		map.put(formModelID, sysFields);
		return map;
	}
	
	public List<OperateLogStatusHookDto> getOperateLogStatusHooks() {
		return operateLogStatusHooks;
	}
	public PDF setOperateLogStatusHooks(List<OperateLogStatusHookDto> operateLogStatusHooks) {
		this.operateLogStatusHooks = operateLogStatusHooks;
		return this;
	}
	
	public PDFSetting getSetting() {
		return setting;
	}
	public PDF setSetting(PDFSetting setting) {
		this.setting = setting;
		return this;
	}
}