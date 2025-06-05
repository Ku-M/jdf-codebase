package gpf.dc.fe.component.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.leavay.dfc.gui.LvUtil;
import com.leavay.ms.tool.CmnUtil;

import cell.fe.gpf.dc.IGpfDCFeService;
import cell.gpf.adur.action.IActionMgr;
import cell.gpf.adur.data.IFormMgr;
import cell.gpf.adur.role.IRoleMgr;
import cell.gpf.adur.user.IUserMgr;
import cell.gpf.dc.concrete.ICDCMgr;
import cell.gpf.dc.config.IPDFMgr;
import cmn.anotation.ClassDeclare;
import fe.cmn.data.NullPojo;
import fe.cmn.data.PairDto;
import fe.cmn.editor.CustomizeEditorDto;
import fe.cmn.editor.EditorDto;
import fe.cmn.editor.SelectEditorDto;
import fe.cmn.editor.SelectEditorInterface;
import fe.cmn.editor.SelectEditorQuerier;
import fe.cmn.editor.SelectEditorQuerierContext;
import fe.cmn.editor.listener.OnValueChanged;
import fe.cmn.panel.BoxDto;
import fe.cmn.panel.PanelContext;
import fe.cmn.panel.SinglePanelDto;
import fe.cmn.panel.ability.PopDialog;
import fe.cmn.panel.ability.QueryEditorValue;
import fe.cmn.panel.ability.SetCustomizeEditorValue;
import fe.cmn.res.JDFICons;
import fe.cmn.tree.TreeDto;
import fe.cmn.widget.ButtonDto;
import fe.cmn.widget.ListenerDto;
import fe.cmn.widget.ListenerInterface;
import fe.cmn.widget.WidgetDto;
import fe.cmn.widget.WindowSizeDto;
import fe.util.FeDebugUtil;
import fe.util.component.AbsComponent;
import fe.util.component.dto.FeDeliverData;
import fe.util.component.param.DataEditParam;
import fe.util.intf.ServiceIntf;
import gpf.adur.action.ActionModel;
import gpf.adur.data.FormModel;
import gpf.adur.data.ResultSet;
import gpf.dc.config.PDF;
import gpf.dc.fe.component.ActionModelTree;
import gpf.dc.fe.component.FormModelTree;
import gpf.dc.fe.component.OrgModelTree;
import gpf.dc.fe.component.UserModelTree;
import gpf.dc.fe.component.adur.ActionModelDefinePanel;
import gpf.dc.fe.component.adur.FormModelDefinePanel;
import gpf.dc.fe.component.concrete.CDCTree;
import gpf.dc.fe.component.config.PDFEditPanel;
import gpf.dc.fe.component.config.tree.PDFTree;
import gpf.dc.fe.component.param.FormModelSelectorParam;
import gpf.dc.fe.util.GpfDCFeI18n;
@ClassDeclare(
	    label = "表单模型选择器",
	    what = "用于选择表单模型的组件",
	    why = "提供界面以选择或检索特定的表单模型",
	    how = "",
	    developer = "陈晓斌",
	    version = "1.0",
	    createTime = "2024-11-22",
	    updateTime = "2024-11-22"
	)
public class FormModelSelector extends AbsComponent<FormModelSelectorParam> implements ListenerInterface,SelectEditorInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2611776508175337754L;
	public final static String CacheKey_SelectedModel = "$selectModel";
	public final static String CMD_VALUE_CHANGED = "CMD_VALUE_CHANGED";
	public final static String CMD_VIEW_DETAIL = "CMD_VIEW_DETAIL";
	public final static String CMD_MANAGE_ENTRY = "CMD_MANAGE_ENTRY";
	
	@Override
	public WidgetDto getWidget(PanelContext panelContext) throws Exception {
		String modelId = widgetParam.getSelectedModelId();
		PairDto<String, String> item = null;
		PairDto[] items = null;
		if(widgetParam.isMultiSelect()) {
			if(!CmnUtil.isCollectionEmpty(widgetParam.getSelectedModelIds())) {
				List<PairDto> itemList = new ArrayList<>();
				for(String selectModelId : widgetParam.getSelectedModelIds()) {
					if(!CmnUtil.isStringEmpty(selectModelId)) {
						FormModel relateModel = IFormMgr.get().queryFormModel(selectModelId);
						if(relateModel != null) {
							if(widgetParam.isShowModelIdAtLabel()) {
								PairDto<String, String> item1 = new PairDto<String, String>(relateModel.getId(), relateModel.getLabel() + "(" + relateModel.getId()+")");
								itemList.add(item1);
							}else {
								PairDto<String, String> item1 = new PairDto<String, String>(relateModel.getId(), relateModel.getLabel());
								itemList.add(item1);
							}
						}
					}
				}
				items = itemList.toArray(new PairDto[0]);
			}
		}else {
			if(!CmnUtil.isStringEmpty(modelId)) {
				FormModel relateModel = IFormMgr.get().queryFormModel(modelId);
				if(relateModel != null) {
					if(widgetParam.isShowModelIdAtLabel()) {
						item = new PairDto<String, String>(relateModel.getId(), relateModel.getLabel() + "(" + relateModel.getId()+")");
					}else {
						item = new PairDto<String, String>(relateModel.getId(), relateModel.getLabel());
					}
				}
			}
		}
		SelectEditorDto relateModelEditor = null;
		if(widgetParam.isMultiSelect()) {
			relateModelEditor = newMultiSelect("RealSelector_"+widgetParam.getWidgetId(), null, items);
		}else {
			relateModelEditor = newSelect("RealSelector_"+widgetParam.getWidgetId(), null, item);
		}
		relateModelEditor.setMaxSize(null);
		relateModelEditor.setAllowCreate(widgetParam.isAllowCreate());
		relateModelEditor.setFilterable(true);
		relateModelEditor.setRemoteFilter(true);
		relateModelEditor.setMultiple(widgetParam.isMultiSelect());
		relateModelEditor.setPanelService(getBuilderService());
		if(!CmnUtil.isStringEmpty(widgetParam.getWidgetId())) {
			relateModelEditor.setWidgetId(widgetParam.getWidgetId());
		}
		relateModelEditor.setWritable(widgetParam.isWritable());
		relateModelEditor.setHintText(GpfDCFeI18n.PleaseSelect+"...");
		SelectEditorQuerier querier = new SelectEditorQuerier();
		FeDeliverData data = new FeDeliverData<>(getClass());
		data.setWidgetIdOfWidgetParam(widgetParam.getWidgetId());
		querier.setBinaryData(data);
		relateModelEditor.setQuerier(querier);
		OnValueChanged onValueChanged = newListener(OnValueChanged.class, getBuilderService(), CMD_VALUE_CHANGED, true, null,widgetParam.getWidgetId());
		relateModelEditor.setOnValueChanged(onValueChanged);
		relateModelEditor.setBinaryData(widgetParam);
		ButtonDto viewBtn = buildButton("", JDFICons.preview, CMD_VIEW_DETAIL, null);
		viewBtn.setVisible(widgetParam.isViewDetail() && !widgetParam.isMultiSelect());
		viewBtn.getOnClick().setBinaryData(data);
		ButtonDto mgrBtn = buildButton("", JDFICons.mt_configuration, CMD_MANAGE_ENTRY, null);
		mgrBtn.setVisible(widgetParam.isShowManageEntry());
		mgrBtn.getOnClick().setBinaryData(data);
		CustomizeEditorDto editor = new CustomizeEditorDto();
		editor.setWidgetId(widgetParam.getWidgetId());
		editor.setChild(BoxDto.hbar(relateModelEditor,viewBtn,mgrBtn));
		editor.setBinaryData(widgetParam);
		editor.setValue(item);
//		return relateModelEditor;
		return editor;
	}

	@Override
	public Class<? extends ServiceIntf> getService() {
		return IGpfDCFeService.class;
	}
	
	@Override
	public Object onListener(ListenerDto listener, PanelContext panelContext, WidgetDto source) throws Exception {
		if(listener instanceof OnValueChanged) {
			onValueChanged(panelContext,((OnValueChanged) listener).getValue());
		}else if(listener.isServiceCommand(CMD_VIEW_DETAIL)) {
			onViewDetail(panelContext);
		}else if(listener.isServiceCommand(CMD_MANAGE_ENTRY)) {
			onMgrEntry(panelContext);
		}
		return null;
	}
	
	public void onValueChanged(PanelContext panelContext,Object value) throws Exception {
		if(NullPojo.isNull(value)) {
//			setPanelCacheValue(panelContext, CacheKey_SelectedModel, null);
//			if(FeDebugUtil.isEnableDebug(panelContext)) {
//				LvUtil.trace("清空选项~");
//			}
			SetCustomizeEditorValue.set(panelContext, widgetParam.getWidgetId(), new NullPojo());
		}else {
//			String sValue = ((PairDto<String, String>)value).getKey();
//			setPanelCacheValue(panelContext, CacheKey_SelectedModel, sValue);
//			if(FeDebugUtil.isEnableDebug(panelContext)) {
//				LvUtil.trace("选项变更~"+value);
//			}
			SetCustomizeEditorValue.set(panelContext, widgetParam.getWidgetId(), value);
		}
	}
	
	public void onViewDetail(PanelContext panelContext) throws Exception {
		Object value = QueryEditorValue.query(panelContext, widgetParam.getWidgetId());
		if(NullPojo.isNull(value)) {
			return;
		}
		String modelId = ((PairDto<String, String>)value).getKey();
		String title = "";
		SinglePanelDto panel = null;
		if(IActionMgr.get().isActionModel(modelId)) {
			ActionModel actionModel = IActionMgr.get().queryActionModel(modelId);
			if(actionModel == null)
				return;
			title = actionModel.getLabel();
			ActionModelDefinePanel definePanel = new ActionModelDefinePanel();
			DataEditParam<ActionModel> param = DataEditParam.create(actionModel);
			definePanel.setWidgetParam(param);
			panel = SinglePanelDto.wrap(definePanel.getWidget(panelContext));
			
		}else if(IPDFMgr.get().isPDF(modelId)) {
			PDF pdf = IPDFMgr.get().queryPDF(modelId);
			if(pdf == null)
				return;
			title = pdf.getLabel();
			PDFEditPanel definePanel = new PDFEditPanel();
			DataEditParam<PDF> param = DataEditParam.create(pdf);
			definePanel.setWidgetParam(param);
			panel = SinglePanelDto.wrap(definePanel.getWidget(panelContext));
		}else {
			FormModel actionModel = IFormMgr.get().queryFormModel(modelId);
			if(actionModel == null)
				return;
			title = actionModel.getLabel();
			FormModelDefinePanel definePanel = new FormModelDefinePanel();
			DataEditParam<FormModel> param = DataEditParam.create(actionModel);
			definePanel.setWidgetParam(param);
			panel = SinglePanelDto.wrap(definePanel.getWidget(panelContext));
		}
		if(panel == null)
			return;
		panel.setPreferSize(WindowSizeDto.all(0.7, 0.7));
		PopDialog.show(panelContext, title, panel);
	}
	
	public void onMgrEntry(PanelContext panelContext) throws Exception {
		List<String> parentModelIds = widgetParam.getParentModelIds();
		String parentModelId = parentModelIds.get(0);
		String title = "";
		SinglePanelDto panel = null;
		if(IActionMgr.get().isActionModel(parentModelId)) {
			TreeDto tree = ActionModelTree.newTree(panelContext, null, parentModelId);
			panel = SinglePanelDto.wrap(tree);
		}else if(IPDFMgr.get().isPDF(parentModelId)) {
			TreeDto tree = PDFTree.newTree(panelContext, null, parentModelId);
			panel = SinglePanelDto.wrap(tree);
		}else if(ICDCMgr.get().isCDC(parentModelId)){
			TreeDto tree = CDCTree.newTree(panelContext, null, parentModelId);
			panel = SinglePanelDto.wrap(tree);
		}else if(IUserMgr.get().isUserModel(parentModelId)){
			TreeDto tree = UserModelTree.newTree(panelContext, null, parentModelId);
			panel = SinglePanelDto.wrap(tree);
		}else if(IRoleMgr.get().isOrgModel(parentModelId)){
			TreeDto tree = OrgModelTree.newTree(panelContext, null, parentModelId);
			panel = SinglePanelDto.wrap(tree);
		}else {
			TreeDto tree = FormModelTree.newTree(panelContext, null, parentModelId);
			panel = SinglePanelDto.wrap(tree);
		}
		if(panel == null)
			return;
		panel.setPreferSize(WindowSizeDto.all(0.3, 0.7));
		PopDialog.show(panelContext, title, panel);
	}

	@Override
	public List<PairDto> querySelectItems(SelectEditorQuerier querier, SelectEditorQuerierContext context)
			throws Exception {
		String keyword = querier.getKeyWord();
		List<FormModel> formModels = new ArrayList<>();
		if(!CmnUtil.isCollectionEmpty(widgetParam.getSelectableModelIds())) {
			for(String formModelId : widgetParam.getSelectableModelIds()) {
				FormModel formModel = IFormMgr.get().queryFormModel(formModelId);
				if(formModel != null)
					formModels.add(formModel);
			}
		}else {
			List<String> parentIds = widgetParam.getParentModelIds();
			ResultSet<FormModel> rs = IFormMgr.get().queryFormModelPage(parentIds, null, keyword, 1, 20);
			formModels = rs.getDataList();
			if(!widgetParam.isParentModelSelectable()) {
				formModels = formModels.stream().filter(v->!parentIds.contains(v.getId())).collect(Collectors.toList());
			}
		}
		List<PairDto> items = new ArrayList<>();
		for(FormModel model:formModels) {
			if(widgetParam.isShowModelIdAtLabel()) {
				items.add(new PairDto(model.getId(), model.getLabel() + "("+model.getId()+")"));
			}else {
				items.add(new PairDto(model.getId(), model.getLabel()));
			}
		}
		return items;
	}

	@Override
	public List<PairDto> filterSelectItems(SelectEditorQuerier querier, SelectEditorQuerierContext context)
			throws Exception {
		return querySelectItems(querier, context);
	}
	
	public static EditorDto newSelector(PanelContext panelContext,FormModelSelectorParam param) throws Exception {
		FormModelSelector selector = new FormModelSelector();
		selector.setWidgetParam(param);
		return (EditorDto) selector.getWidget(panelContext);
	}

}
