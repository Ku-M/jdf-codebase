package gpf.dc.basic.fe.component.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.kwaidoo.ms.tool.ToolUtilities;
import com.leavay.common.util.javac.ClassFactory;
import com.leavay.ms.tool.CmnUtil;

import bap.cells.Cells;
import cell.cdao.IDao;
import cell.cdao.IDaoService;
import cell.cmn.IJson;
import cell.cmn.IJsonService;
import cell.fe.gpf.dc.basic.IGpfDCBasicFeService;
import cell.gpf.adur.action.IActionMgr;
import cell.gpf.adur.role.IRoleMgr;
import cell.gpf.adur.user.IUserMgr;
import cell.gpf.dc.basic.IExpressionMgr;
import cell.gpf.dc.runtime.IDCRuntimeContext;
import cell.gpf.dc.runtime.IPDFRuntimeMgr;
import cmn.anotation.FieldDeclare;
import cmn.anotation.I18nDeclare;
import cmn.dto.Progress;
import cmn.i18n.I18nIntf;
import cmn.util.NullUtil;
import cmn.util.TraceUtil;
import cmn.util.Tracer;
import cson.core.CsonPojo;
import fe.cmn.FeUtil;
import fe.cmn.app.ability.BatchExecuteCallback;
import fe.cmn.app.ability.PopToast;
import fe.cmn.data.BeFile;
import fe.cmn.data.BinPojo;
import fe.cmn.data.KeyboardDto;
import fe.cmn.data.NullPojo;
import fe.cmn.data.PairDto;
import fe.cmn.editor.CustomizeEditorDto;
import fe.cmn.editor.DatePickerDto;
import fe.cmn.editor.DateTimePickerDto;
import fe.cmn.editor.EditorDto;
import fe.cmn.editor.SelectEditorDto;
import fe.cmn.editor.TextEditorDto;
import fe.cmn.editor.TimePickerDto;
import fe.cmn.editor.listener.OnValueChanged;
import fe.cmn.menu.MenuDto;
import fe.cmn.menu.MenuItemDto;
import fe.cmn.panel.BoxDto;
import fe.cmn.panel.BoxMainAxisSize;
import fe.cmn.panel.ContainerDto;
import fe.cmn.panel.CrossAxisAlign;
import fe.cmn.panel.MenuPosition;
import fe.cmn.panel.PanelContext;
import fe.cmn.panel.PanelDto;
import fe.cmn.panel.PanelValue;
import fe.cmn.panel.PanelValueAdapter;
import fe.cmn.panel.PlaceholderDto;
import fe.cmn.panel.ScrollBoxDto;
import fe.cmn.panel.SinglePanelDto;
import fe.cmn.panel.ability.PopDialog;
import fe.cmn.panel.ability.PopMenu;
import fe.cmn.panel.ability.QueryChildVisible;
import fe.cmn.panel.ability.QueryEditorsWritable;
import fe.cmn.panel.ability.QueryWidget;
import fe.cmn.panel.ability.QuitPopup;
import fe.cmn.panel.ability.RebuildChild;
import fe.cmn.panel.ability.SetBinaryData;
import fe.cmn.panel.ability.SetChildEditorWritable;
import fe.cmn.panel.ability.SetChildVisible;
import fe.cmn.panel.ability.SetEditorValue;
import fe.cmn.studio.EmptySlotDto;
import fe.cmn.table.TableDto;
import fe.cmn.widget.ButtonDto;
import fe.cmn.widget.InsetDto;
import fe.cmn.widget.LabelDto;
import fe.cmn.widget.ListenerDto;
import fe.cmn.widget.ListenerInterface;
import fe.cmn.widget.SizeDto;
import fe.cmn.widget.WidgetDto;
import fe.cmn.widget.WindowSizeDto;
import fe.cmn.widget.decoration.DecorationDto;
import fe.util.FeDebugUtil;
import fe.util.FeFileUtil;
import fe.util.FeLayoutUtil;
import fe.util.FeListenerUtil;
import fe.util.component.AbsFormEditPanel;
import fe.util.component.dto.FeDeliverData;
import fe.util.component.editor.AbsUploadPanel;
import fe.util.component.extlistener.CommandCallbackListener;
import fe.util.component.extlistener.CommandListener;
import fe.util.component.param.BaseWidgetParam;
import fe.util.component.param.WidgetParam;
import fe.util.dto.loading.LoadingMaskConfigDto;
import fe.util.editor.valuehanlder.EditorFieldDefine;
import fe.util.editor.valuehanlder.EditorValueHandlerFactory;
import fe.util.enums.LabelLayoutDirection;
import fe.util.exception.handler.ExceptionHandlerFactory;
import fe.util.intf.ServiceIntf;
import fe.util.style.FeStyleConst;
import fe.util.style.FeStyleSetting;
import gpf.adur.action.Action;
import gpf.adur.data.AssociationData;
import gpf.adur.data.AttachData;
import gpf.adur.data.DataType;
import gpf.adur.data.Form;
import gpf.adur.data.Password;
import gpf.adur.data.TableData;
import gpf.adur.role.Org;
import gpf.adur.user.User;
import gpf.dc.basic.dto.view.FeEventSubscribeDto;
import gpf.dc.basic.dto.view.SubmitButtonHookConfigDto;
import gpf.dc.basic.dto.view.TimerConfigDto;
import gpf.dc.basic.expression.RuleIntf;
import gpf.dc.basic.fe.component.app.AppCacheUtil;
import gpf.dc.basic.fe.component.fieldextend.editor.WidgetLayoutUtil;
import gpf.dc.basic.fe.component.param.BaseDataViewParam;
import gpf.dc.basic.fe.enums.ColumnAlignType;
import gpf.dc.basic.fe.enums.ListenerApplyLocation;
import gpf.dc.basic.fe.enums.TriggerTime;
import gpf.dc.basic.field.extend.KeyValueTableExtend;
import gpf.dc.basic.param.view.BaseFeActionParameter;
import gpf.dc.basic.param.view.FormParameter;
import gpf.dc.basic.param.view.ViewActionParameterIntf;
import gpf.dc.basic.param.view.dto.ApplicationSetting;
import gpf.dc.basic.param.view.dto.ButtonDefine;
import gpf.dc.basic.param.view.dto.FormFieldDefine;
import gpf.dc.basic.param.view.dto.FormViewSetting;
import gpf.dc.basic.param.view.dto.IgnoreRequireSetting;
import gpf.dc.basic.param.view.dto.ListenerDefine;
import gpf.dc.basic.param.view.dto.ViewInitActionDefine;
import gpf.dc.basic.progress.AbsProgressBuilder;
import gpf.dc.basic.util.GpfDCBasicFeUtil;
import gpf.dc.concrete.RefActionConfig;
import gpf.dc.fe.component.adur.data.FormCompnentIntf;
import gpf.dc.fe.component.adur.data.field.FormFieldEditorFactory;
import gpf.dc.fe.component.adur.data.field.handler.AttachEditorHandler;
import gpf.dc.fe.component.adur.data.field.handler.FormFieldEditorValueHandlerFactory;
import gpf.dc.fe.component.adur.data.field.handler.FormVisitor;
import gpf.dc.fe.component.editor.FormModelSelector;
import gpf.dc.fe.component.param.FormModelSelectorParam;
import gpf.dc.intf.InheritableIntf;
import gpf.dto.model.data.ActionPrivilegeDto;
import gpf.dto.model.data.FieldPrivilegeDto;
import gpf.dto.model.data.FormPrivilegeDto;
import gpf.dto.model.data.GroupPrivilegeDto;
import web.dto.Pair;
@I18nDeclare
public abstract class AbsFormView <T extends BaseDataViewParam<R>,R extends Form> extends AbsFormEditPanel<T,R> implements ViewListenerBuilder,ListenerInterface,FormViewActionIntf<T>,FormCompnentIntf<T>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1973292312665766837L;
	
	@I18nDeclare
	public static final String BUILD_PROGRESS_FAILED = "进度条对象构建失败！";
	
	@FieldDeclare(label = "值变更事件", desc = "当值发生变化时触发的事件")
	public static final String CMD_ON_VALUE_CHANGED = "CMD_ON_VALUE_CHANGED";

	@FieldDeclare(label = "退出弹窗", desc = "关闭或退出当前弹出窗口的命令")
	public static final String CMD_QUIT_POPUP = "CMD_QUIT_POPUP";

	@FieldDeclare(label = "渲染后", desc = "内容渲染完成后的操作")
	public static final String CMD_AFTER_RENDERED = "CMD_AFTER_RENDERED";

	@FieldDeclare(label = "点击表单按钮组", desc = "点击表单工具栏按钮组的事件")
	public final static String CMD_CLICK_FORM_BUTTON_GROUP = "CMD_CLICK_FORM_BUTTON_GROUP";

	@FieldDeclare(label = "点击按钮组弹窗菜单项", desc = "点击表单按钮组中弹窗菜单项的事件")
	public final static String CMD_CLICK_FORM_BUTTON_GROUP_MENU_ITEM = "CMD_CLICK_FORM_BUTTON_GROUP_MENU_ITEM";
	
	public static final String WidgetID_SelectModel = "SelectModel";
	@Override
	public Class<? extends ServiceIntf> getService() {
		return IGpfDCBasicFeService.class;
	}
//	@Override
//	public I18nIntf getI18n(PanelContext context) throws Exception {
//		return AppCacheUtil.getAppFeI18n(context);
//	}
	/**
	 * 获取国际化配置
	 * @param context
	 * @param key
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getI18nString(PanelContext context,String key,Object... params) throws Exception {
		I18nIntf i18n = getI18n(context);
		if(i18n == null)
			return key;
		return i18n.formatInGroup(key, widgetParam.getModelId(),params);
	}
	
	@Override
	public boolean isLazyQueryCompoundField() {
		return widgetParam.isLazyQueryCompoundField();
	}
	/**
	 * 获取表单属性定义
	 * @param panelContext
	 * @return
	 * @throws Exception
	 */
	public abstract List<FormFieldDefine> getFormFieldDefines(PanelContext panelContext)throws Exception;
	/**
	 * 初始化面板缓存
	 * @param panelContext
	 * @param panelGlobalKey
	 */
	public void initPanelCache(PanelContext panelContext,String panelGlobalKey) throws Exception{
		initPanelCacheValue(panelContext, panelGlobalKey, CacheKey_MaxLabelCharLength, null);
		initPanelCacheValue(panelContext, panelGlobalKey, CacheKey_ActionPrivileges, null);
		initPanelCacheValue(panelContext, panelGlobalKey, CacheKey_EditorFieldDefine, null);
		initDefaultFieldLabelWidth(panelContext, panelGlobalKey);
		if(ExceptionHandlerFactory.isDebug()) {
			initPanelCacheValue(panelContext, panelGlobalKey, CacheKey_DebugLog, "");
		}
		initPanelCacheValue(panelContext, panelGlobalKey, ServiceIntf.getCacheWidgetParamKey(widgetParam.getWidgetId()), widgetParam);
	}
	
	public final static String CacheKey_MaxLabelCharLength = "$labelWidth";
	@Override
	public int getDefaultFieldLabelWidth() {
		try {
			Integer labelCharLength = (Integer) getPanelCacheValue(getPanelContext(), CacheKey_MaxLabelCharLength);
			if(labelCharLength != null) {
				FeStyleSetting setting = getFeStyleSetting(getPanelContext());
				return (int) (labelCharLength * setting.getMainBodyFontSize());
			}
			FormViewSetting setting = widgetParam.getSetting(FormViewSetting.class);
			if(setting.getLabelWidth() != null) {
				return CmnUtil.getInteger(setting.getLabelWidth());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.getDefaultFieldLabelWidth();
	}
	
	public void initDefaultFieldLabelWidth(PanelContext panelContext,String panelGlobalKey) {
		try {
			FormViewSetting setting = widgetParam.getSetting(FormViewSetting.class);
			if(setting.getLabelAdaptive()) {
				List<FormFieldDefine> fields = getFormFieldDefines(panelContext);
				double maxCharLength = 0;
				String maxLabel = "";
				for(FormFieldDefine field : fields) {
					String label = getFieldLabel(panelContext, widgetParam.getData(), field);//CmnUtil.isStringEmpty(field.getAlias()) ? field.getName() : field.getAlias();
//					label = getI18nString(panelContext, label);
					double labelLength = GpfDCBasicFeUtil.caculateLabelFontCnt(label);
					if(field.isNotNull()) {
						labelLength+=1;
					}
					if(!CmnUtil.isStringEmpty(field.getDescription())) {
						labelLength+=1;
					}
					if(maxCharLength < labelLength) {
						maxLabel = label;
						maxCharLength = labelLength;
					}
				}
				maxCharLength+=2;
//				LvUtil.trace("启用表单属性宽度自适应，最长属性名：" + maxLabel + "，标签字符数量：" + CmnUtil.getInteger(Math.ceil(maxCharLength)));
				initPanelCacheValue(panelContext, panelGlobalKey, CacheKey_MaxLabelCharLength, CmnUtil.getInteger(Math.ceil(maxCharLength)));
			}
		} catch (Exception e) {
		}
	}
	/**
	 * 初始化表单值
	 * @param panelContext
	 * @throws Exception
	 */
	public void initDataValue(IDCRuntimeContext rtx,PanelContext panelContext) throws Exception {
		Form data = widgetParam.getData();
		if(data == null)
			return;
		List<FormFieldDefine> fields = getFormFieldDefines(panelContext);
		Map<String,Object> exprEnv = new LinkedHashMap<>();
		exprEnv.put("$appContext", AppCacheUtil.getAppContext(panelContext));
		exprEnv.put("$panelContext", panelContext);
		ApplicationSetting appSetting = AppCacheUtil.getSetting(panelContext);
		String orgModelId = null;
		String userModelId = null;
		if(appSetting != null) {
			orgModelId = appSetting.getOrgModelId();
			userModelId = appSetting.getUserModelId();
		}
		RuleIntf.prepareEnv(exprEnv, rtx, data, orgModelId, userModelId);
		for(FormFieldDefine field : fields) {
			if(!CmnUtil.isStringEmpty(field.getInitValue())) {
				Object realValue = field.getInitValue();
				if(field.getInitValue().startsWith("Expression:")) {
					try {
						RuleIntf.setField(exprEnv, field.getName());
						realValue = IExpressionMgr.get().execute(exprEnv, field.getInitValue().substring("Expression:".length()));
					}catch (Exception e) {
						ExceptionHandlerFactory.get().handle(getService(),panelContext, e);
					}
				}
				DataType dataType = field.getDataTypeEnum();
				try {
				switch (dataType) {
				case Text:
					data.setAttrValueByCode(field.getCode(), realValue);
					break;
				case Boolean:
					data.setAttrValueByCode(field.getCode(), CmnUtil.getBoolean(realValue, false));
					break;
				case Long:
				case Date:
					data.setAttrValueByCode(field.getCode(), CmnUtil.getLong(realValue));
					break;
				case Decimal:
					data.setAttrValueByCode(field.getCode(), CmnUtil.getDouble(realValue));
					break;
				case Depend:
					data.setAttrValueByCode(field.getCode(), realValue);
				case Relate:{
					if(field.isAssocMultiSelect()) {
						String[] values = ((String)realValue).split(",");
						List<AssociationData> list = new ArrayList<>();
						for(String value : values) {
							list.add(new AssociationData(field.getAssocFormModel(), value));
						}
						data.setAttrValueByCode(field.getCode(), list);
					}else {
						data.setAttrValueByCode(field.getCode(), new AssociationData(field.getAssocFormModel(), field.getInitValue()));
					}
				}
				break;
				case Password:{
					Password password = new Password().setValue((String)realValue);
					data.setAttrValueByCode(field.getCode(), password);
				}
				case Attach:
				case Image:
				case KeyFormField:
				case KeyValue:
				case NestingModel:
				case RichDocument:
					break;

				default:
					break;
				}
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
	}
	/**
	 * 根据动作权限初始化widgetParam中的权限复写配置
	 * @param panelContext
	 * @param panelGlobalKey
	 * @param dao
	 * @throws Exception
	 */
	private void initWidgetParamOverride(PanelContext panelContext,String panelGlobalKey,IDao dao) throws Exception {
		if(widgetParam.isLayoutMode())
			return;
		FormPrivilegeDto formPrivilege = caculateFormPrivilegeDto(panelContext, panelGlobalKey, dao, widgetParam.getActionPrivileges());
		if(formPrivilege == null)
			return;
		Tracer tracer = TraceUtil.getCurrentTracer();
		if(FeDebugUtil.isEnableDebug(panelContext)) {
			try(IJson json = IJsonService.get().getJson()){
				String privilegeJson = json.toJson(formPrivilege);
				tracer.debug("表单权限: " + privilegeJson);
			}
		}
		Map<String,Boolean> visbileOverride = new LinkedHashMap<>();
		Map<String,Boolean> writableOverride = new LinkedHashMap<>();
		for(FieldPrivilegeDto fieldPriv : formPrivilege.getFieldPrivileges()) {
			visbileOverride.put(fieldPriv.getField(), fieldPriv.isVisible());
			writableOverride.put(fieldPriv.getField(), fieldPriv.isWritable());
		}
		widgetParam.mergeVisibleOverride(visbileOverride);
		widgetParam.mergeWritableOverride(writableOverride);
		Map<String,Boolean> buttonVisibleOverride = new LinkedHashMap<>();
		for(ActionPrivilegeDto actionPriv : formPrivilege.getActionPrivileges()) {
			buttonVisibleOverride.put(actionPriv.getName(), actionPriv.isVisible());
		}
		widgetParam.mergeButtonVisibleOverride(buttonVisibleOverride);
		
		Map<String,Boolean> groupVisibleOverride = new LinkedHashMap<>();
		Map<String,Boolean> groupWritableOverride = new LinkedHashMap<>();
		for(GroupPrivilegeDto groupPriv : formPrivilege.getGroupPrivileges()) {
			groupVisibleOverride.put(groupPriv.getName(), groupPriv.isVisible());
			groupWritableOverride.put(groupPriv.getName(), groupPriv.isWritable());
		}
		widgetParam.megerGroupVisibleOverride(groupVisibleOverride);
		widgetParam.megerGroupWritableOverride(groupWritableOverride);
		if(FeDebugUtil.isEnableDebug(panelContext)) {
			tracer.debug("visbileOverride: " + visbileOverride);
			tracer.debug("writableOverride: " + writableOverride);
			tracer.debug("buttonVisibleOverride: " + buttonVisibleOverride);
			tracer.debug("groupVisibleOverride: " + groupVisibleOverride);
			tracer.debug("groupWritableOverride: " + groupWritableOverride);
		}
	}
	@Override
	public List<String> getRequestCategorys(PanelContext panelContext) {
		try {
//			TraceUtil.logStart();
//			FormModel model = IFormMgr.get().queryFormModel(widgetParam.getModelId());
			String modelLabel = widgetParam.getModelId();//model.getLabel();
			Form data = widgetParam.getData();
			String code = data.getStringByCode(Form.Code);
			if(code == null) {
				code = data.getUuid();
			}
//			TraceUtil.printCost("getRequestCategorys", false);
			return Arrays.asList(modelLabel,code);
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public WidgetDto getWidget(PanelContext panelContext) throws Exception {
		return FormViewActionIntf.super.getWidget(panelContext);
	}
	@Override
	public WidgetDto doGetWidget(PanelContext panelContext) throws Exception {
//		if(CmnUtil.isStringEmpty(widgetParam.getWidgetId())) {
//			widgetParam.setWidgetId(ToolUtilities.allockUUIDWithUnderline());
//		}
//		if(CmnUtil.isStringEmpty(widgetParam.getPanelGlobalKey())) {
//			widgetParam.setPanelGlobalKey(ToolUtilities.allockUUIDWithUnderline());
//		}
//		setPanelContext(panelContext);
		String panelGlobalKey = widgetParam.getPanelGlobalKey();
//		initPanelCache(panelContext, panelGlobalKey);
		FormViewSetting setting = widgetParam.getSetting(FormViewSetting.class);
		try(IDao dao = IDaoService.get().newDao()){
			if(widgetParam.getModelSelectParam() != null && widgetParam.getData().getFormModelId().equals(KeyValueTableExtend.FakeFormModelId)) {
				return newEmptyFormPanel(panelContext,panelGlobalKey);
			}
			IDCRuntimeContext rtx = getOrPrepareRtx(panelContext, panelGlobalKey);
			rtx.setDao(dao);
			initWidgetParamOverride(panelContext, panelGlobalKey, dao);
//			doBeforeInit(panelContext,panelGlobalKey);
			R cdo = widgetParam.getData();
			cdo.setAttrValueByCode(InheritableIntf.Override, true);
			List<FormFieldDefine> fieldDefs = getFormFieldDefines(panelContext);
			BoxDto mainBox = buildContentBox(panelGlobalKey,panelContext,cdo,fieldDefs);
			
			SinglePanelDto panel = SinglePanelDto.wrap(FeLayoutUtil.wrapScorllBox(mainBox).setDecoration(new DecorationDto().setPadding(new InsetDto(0.0, 0.0, 20.0, 0.0))));
			panel.setBarAlign(CrossAxisAlign.start);
			panel.setBinaryData(widgetParam);
			panel.setTopBar(getTopBar(panelContext));
			panel.setBottomBar(getBottomBar(panelContext));
			panel.setFontSizeAutoFix(false);
			panel.setExpandInBox(true);
			panel.setPanelGlobalKey(panelGlobalKey);
			panel.setValueAdapter(new PanelValueAdapter<>().setKeeyStructure(true));
			panel.setWrapMask(true);
//			//启用屏幕截图，开启后会在界面上截图时构建图层，图层过多回导致性能问题，谨慎使用
//			panel.setEnableScreenshot(true);
			ListenerDto lsnr = newListener(getService(), CMD_REFRESH, true, null);
			CommandListener refreshLsnr = new CommandListener(CMD_REFRESH, "重载界面", "", lsnr, true);
			panel.addExtendListener(refreshLsnr);
			ListenerDto lsnr2 = newListener(getService(), CMD_DELETE, true, null);
			CommandListener deleteLsnr = new CommandListener(CMD_DELETE, "取消表单", "", lsnr2, true);
			panel.addExtendListener(deleteLsnr);
			if(!CmnUtil.isStringEmpty(widgetParam.getWidgetId())) {
				panel.setWidgetId(widgetParam.getWidgetId());
			}
			if(widgetParam.getLayout() != null) {
				panel = (SinglePanelDto) WidgetLayoutUtil.setRealWidget2Layout(panel, widgetParam.getLayout(),getI18n(panelContext),widgetParam.getModelId());
				//标签页里没有属性时是否显示标签页
				if(setting != null && !setting.isShowTabWhenFieldsRFalse()) {
					WidgetLayoutUtil.removeTabWhenFieldsRFalse(panel);
					WidgetLayoutUtil.removeCollapseWhenFieldsRFalse(panel);
				}
				setChildWidgetVisibleAfterInited(panelContext, panelGlobalKey, panel);
			}
//			//设置指令回调监听
//			setCommandCallbackListener(panel, widgetParam.getCommandCallbackLsnrs());
//			subscribEvent(panelContext, panel, getEventSubscribes());
//			//设置定时器
//			initTimer(panelContext, this,panel, widgetParam.getTimerConfigs());
			panel.setStyleName(getPanelStyle());
			panel.setPostFrameCallback(newListener(getService(), CMD_AFTER_RENDERED, true, null));
//			doAfterInited(panelContext,panelGlobalKey,panel);
			return panel;
		}
	}
	
	@Override
	public List<TimerConfigDto> getTimerConfigs() {
		return widgetParam.getTimerConfigs();
	}
	
//	public void onAfterRender(PanelContext panelContext) throws Exception {
//		if(widgetParam.getModelSelectParam() != null) {
//			FormModelSelectorParam modelSelectParam = widgetParam.getModelSelectParam();
//			modelSelectParam.setWidgetId(WidgetID_SelectModel);
//			EditorDto modelSelector = FormModelSelector.newSelector(panelContext, modelSelectParam);
//			setOnValueChanged(modelSelector, null);
//			try {
//				RebuildChild.rebuild(panelContext, modelSelector);
//			}catch (Exception e) {
//			}
//		}
//	}
	
	public void insertModelSelector(PanelContext panelContext,SinglePanelDto panel) throws Exception {
		if(widgetParam.getModelSelectParam() != null) {
			EditorDto modelSelector = (EditorDto) FeUtil.searchWidgetById(panel, WidgetID_SelectModel);
			if(modelSelector == null) {
				FormModelSelectorParam modelSelectParam = ToolUtilities.clone(widgetParam.getModelSelectParam());
				Form data = widgetParam.getData();
				if(!CmnUtil.isStringEqual(data.getFormModelId(),KeyValueTableExtend.FakeFormModelId)) {
					modelSelectParam.setSelectedModelId(data.getFormModelId());
				}
				modelSelectParam.setWritable(widgetParam.isWritable());
				modelSelectParam.setWidgetId(WidgetID_SelectModel);
				modelSelector = FormModelSelector.newSelector(panelContext, modelSelectParam);
				setOnValueChanged(modelSelector, null);
				WidgetDto content = panel.getContent();
				if(content instanceof ScrollBoxDto) {
					WidgetDto child = ((ScrollBoxDto) content).getChild();
					if(child instanceof BoxDto) {
						((BoxDto) child).setMainAxisSize(BoxMainAxisSize.min).setExpandInBox(true);
						((BoxDto) child).getChildren().add(0, wrapEditor(modelSelector));
					}else {
						content = BoxDto.vbar(wrapEditor(modelSelector),child).setMainAxisSize(BoxMainAxisSize.min).setScrollable(true);
						panel.setContent(content);
					}
				}else if(content instanceof BoxDto) {
					((BoxDto) content).getChildren().add(0, wrapEditor(modelSelector));
				}else {
					content = BoxDto.vbar(wrapEditor(modelSelector),content).setMainAxisSize(BoxMainAxisSize.min);
					panel.setContent(content);
				}
			}
		}
		if(widgetParam.isEmbedForm()) {
			panel.setTopBar(null);
			panel.setBottomBar(null);
		}
	}
	
	public void appendModelSelector(PanelContext panelContext,BoxDto mainBox) throws Exception {
		if(widgetParam.getModelSelectParam() != null) {
			FormModelSelectorParam modelSelectParam = ToolUtilities.clone(widgetParam.getModelSelectParam());
			Form data = widgetParam.getData();
			if(!CmnUtil.isStringEqual(data.getFormModelId(),KeyValueTableExtend.FakeFormModelId)) {
				modelSelectParam.setSelectedModelId(data.getFormModelId());
			}
			modelSelectParam.setWritable(widgetParam.isWritable());
			modelSelectParam.setWidgetId(WidgetID_SelectModel);
			EditorDto modelSelector = FormModelSelector.newSelector(panelContext, modelSelectParam);
			setOnValueChanged(modelSelector, null);
			mainBox.addChild(wrapEditor(modelSelector));
		}
	}
	
	public void appendDebugLog(PanelContext panelContext,String log) throws Exception {
		if(ExceptionHandlerFactory.isDebug()) {
			String debugLog = (String) getPanelCacheValue(panelContext, CacheKey_DebugLog);
			debugLog = debugLog + log;
			setPanelCacheValue(panelContext, CacheKey_DebugLog, debugLog);
		}
	}
	
	@Override
	public IDCRuntimeContext getOrPrepareRtx(PanelContext panelContext,String panelGlobalKey) throws Exception {
//		IMapCell map = panelContext.getOrCreatePanelCache(panelGlobalKey);
		IDCRuntimeContext rtx = null;//(IDCRuntimeContext) map.getValue(PanelCacheKey_IDCRuntimeContext);
		if(rtx == null) {
			rtx = IPDFRuntimeMgr.get().newRuntimeContext();
			rtx.setOperator(panelContext.getCurrentUser());
			rtx.setPdfUuid(widgetParam.getModelId());
			BaseFeActionParameter.prepareFeActionParameter(rtx, panelContext, (ListenerDto)null, this);
			rtx.setInteractiveForm(widgetParam.getData());
			rtx.setI18n(getI18n(panelContext));
//			ApplicationSetting appSetting = AppCacheUtil.getSetting(panelContext);
//			if(appSetting != null) {
//				BaseFeActionParameter.setAppSetting(rtx, appSetting);
//			}
		}
		return rtx;
	}
	@Override
	public boolean isLayoutMode() {
		return widgetParam.isLayoutMode();
	}
	
	public void doBeforeInit(PanelContext panelContext,String panelGlobalKey) throws Exception {
		//布局器模式下，不需要初始化
		if(widgetParam.isLayoutMode())
			return;
		List<ViewInitActionDefine> actionDefs = widgetParam.getInitActionDefines(TriggerTime.TriggerBeforeInit);
		List<ListenerDefine> initLsnrs = widgetParam.getViewInitListenerDefines(ListenerApplyLocation.Form);
		if(CmnUtil.isCollectionEmpty(actionDefs) && CmnUtil.isCollectionEmpty(initLsnrs))
			return;
		Tracer tracer =TraceUtil.getCurrentTracer();
		try(IDao dao = IDaoService.newIDao()){
			IDCRuntimeContext rtx = getOrPrepareRtx(panelContext,panelGlobalKey);
			BaseFeActionParameter.setTriggerTime(rtx, TriggerTime.TriggerBeforeInit);
			ViewActionParameterIntf.setCurrentComponentPanelGlobalKey(rtx, panelGlobalKey);
			rtx.setDao(dao);
			for(ListenerDefine initLsnr : NullUtil.get(initLsnrs)) {
				for(RefActionConfig func : getActionDefines()) {
					boolean isMatch = false;
					if(initLsnr != null && initLsnr.containResponseCommand(func.getName())) {
						isMatch = true;
					}
					if(!isMatch)
						continue;
					Action action = func.getAction();
					if(action == null)
						continue;
					IActionMgr.get().executeAction(action, rtx);
					AbsFormView comp = (AbsFormView) rtx.getParam(BaseFeActionParameter.FeActionParameter_CurrentComponent);
					BaseDataViewParam compWidgetParam = (BaseDataViewParam) comp.getWidgetParam();
					if(compWidgetParam != null)
						widgetParam = (T) compWidgetParam;
				}
			}
			if(!CmnUtil.isCollectionEmpty(actionDefs)) {
				for(ViewInitActionDefine initActDef : actionDefs) {
					String comand = initActDef.getCommand();
					RefActionConfig function = widgetParam.getActionDefineMap().get(comand);
					if(function == null)
						continue;
					Action action = function.getAction();
					if(action == null)
						continue;
					IActionMgr.get().executeAction(action, rtx);
					AbsFormView comp = (AbsFormView) rtx.getParam(BaseFeActionParameter.FeActionParameter_CurrentComponent);
					BaseDataViewParam compWidgetParam = (BaseDataViewParam) comp.getWidgetParam();
					if(compWidgetParam != null)
						widgetParam = (T) compWidgetParam;
					if(FeDebugUtil.isEnableDebug(panelContext)) {
						try(IJson json = IJsonService.get().getJson()){
							tracer.debug("groupVisibleOverride：" + json.toJson(widgetParam.getGroupVisibleOverride()));
							tracer.debug("groupWritableOverride：" + json.toJson(widgetParam.getGroupWritableOverride()));
						}
					}
		//			widgetParam.setVisibleOverride(compWidgetParam.getVisibleOverride());
		//			widgetParam.setWritableOverride(compWidgetParam.getWritableOverride());
		//			widgetParam.setRequireOverride(compWidgetParam.getRequireOverride());
		//			widgetParam.setContext(compWidgetParam.getContext());
				}
			}
		}
	}
	
	private void setChildWidgetVisibleAfterInited(PanelContext panelContext,String panelGlobalKey,PanelDto panel) {
		List<WidgetDto> matchWidgets = new ArrayList<>();
		Set<String> groupIdSet = new LinkedHashSet<>();
		if(widgetParam.getGroupVisibleOverride() != null) {
			groupIdSet.addAll(widgetParam.getGroupVisibleOverride().keySet());
		}
//		if(widgetParam.getGroupWritableOverride() != null) {
//			groupIdSet.addAll(widgetParam.getGroupWritableOverride().keySet());
//		}
		//排除属性定义、按钮定义以外，所有在标签组里的组件
		Set<String> fieldBoxCodes = new LinkedHashSet<>();
		for(FormFieldDefine fieldDef : widgetParam.getFieldDefines()) {
			fieldBoxCodes.add(getEditorBoxWidgetId(fieldDef.getCode()));
		}
		Set<String> buttons = new LinkedHashSet<>();
		for(ButtonDefine button : widgetParam.getButtonDefines()) {
			buttons.add(button.getName());
		}
		
		GpfDCBasicFeUtil.searchWidget(panel, v->{
			if(fieldBoxCodes.contains(v.getWidgetId()))
				return false;
			if(buttons.contains(v.getWidgetId()))
				return false;
			String[] groupIds = v.getGroupIds();
			if(groupIds != null) {
				for(String groupId : groupIds) {
					if(groupIdSet.contains(groupId))
						return true;
				}
			}
			return false;
		},matchWidgets);
		for(WidgetDto childWidget : matchWidgets) {
			if(widgetParam.getGroupVisibleOverride() != null) {
				for(String groupId : childWidget.getGroupIds()) {
					if(widgetParam.getGroupVisibleOverride().containsKey(groupId)) {
						childWidget.setVisible(widgetParam.getGroupVisibleOverride().get(groupId));
					}
				}
			}
		}
	}
	
	public void doAfterInited(PanelContext panelContext,String panelGlobalKey,PanelDto panel) throws Exception {
		if(widgetParam.isLayoutMode())
			return;
		List<ViewInitActionDefine> actionDefs = widgetParam.getInitActionDefines(TriggerTime.TriggerAfterInited);
		List<ListenerDefine> initedLsnrs = widgetParam.getViewInitedListenerDefines(ListenerApplyLocation.Form);
		if(CmnUtil.isCollectionEmpty(actionDefs) && CmnUtil.isCollectionEmpty(initedLsnrs))
			return;
		try(IDao dao = IDaoService.newIDao()){
			IDCRuntimeContext rtx = getOrPrepareRtx(panelContext,panelGlobalKey);
			BaseFeActionParameter.setTriggerTime(rtx, TriggerTime.TriggerBeforeInit);
			BaseFeActionParameter.setInitedWidget(rtx, panel);
			for(ListenerDefine initedLsnr : NullUtil.get(initedLsnrs)) {
				for(RefActionConfig func : getActionDefines()) {
					boolean isMatch = false;
					if(initedLsnr != null && initedLsnr.containResponseCommand(func.getName())) {
						isMatch = true;
					}
					if(!isMatch)
						continue;
					Action action = func.getAction();
					if(action == null)
						continue;
					IActionMgr.get().executeAction(action, rtx);
				}
			}
			if(!CmnUtil.isCollectionEmpty(actionDefs)) {
				for(ViewInitActionDefine initActDef : actionDefs) {
					String comand = initActDef.getCommand();
					RefActionConfig function = widgetParam.getActionDefineMap().get(comand);
					if(function == null)
						continue;
					Action action = function.getAction();
					if(action == null)
						continue;
					IActionMgr.get().executeAction(action, rtx);
				}
			}
		}
	}
	
	public String getPanelStyle() {
		return null;
	}
	
	public String getMainBoxStyle() {
		if(widgetParam.isEmbedForm())
			return null;
		return FeStyleConst.common_form_box;
	}
	
	public String getLabelSpanStyle() {
		return null;
	}
	
	public String getLabelBoxStyle() {
		return null;
	}
	
	public String getFieldBoxStyle() throws Exception {
		FormViewSetting setting = widgetParam.getSetting(FormViewSetting.class);
		if(!CmnUtil.isStringEmpty(setting.getFieldBoxStyle())) {
			return setting.getFieldBoxStyle();
		}
		return FeStyleConst.common_field_box;
	}
	
	public String getEditorStyle(FormFieldDefine fieldDef,boolean isWritable) {
		if(!CmnUtil.isStringEmpty(fieldDef.getComponentStyle())) {
//			LvUtil.trace(fieldDef.getComponentStyle());
			return fieldDef.getComponentStyle();
		}
		ColumnAlignType fieldAlignType = fieldDef.getComponentAlignEnum();
		if(fieldAlignType != null) {
			if(fieldAlignType == ColumnAlignType.left)
				return FeStyleConst.edtior_align_left;
			else if(fieldAlignType == ColumnAlignType.right)
				return FeStyleConst.edtior_align_right;
			else if(fieldAlignType == ColumnAlignType.center)
				return FeStyleConst.edtior_align_center;
		}
		try {
			FormViewSetting setting = widgetParam.getSetting(FormViewSetting.class);
			ColumnAlignType alignType = null;
			if(isWritable)
				alignType = setting.getComponentAlignEnum();
			else
				alignType = setting.getReadOnlyComponentAlignEnum();
			if(alignType != null) {
				if(alignType == ColumnAlignType.left)
					return FeStyleConst.edtior_align_left;
				else if(alignType == ColumnAlignType.right)
					return FeStyleConst.edtior_align_right;
				else if(alignType == ColumnAlignType.center)
					return FeStyleConst.edtior_align_center;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public final static String CMD_DEBUG = "CMD_DEBUG";
	
	public BoxDto buildContentBox(String panelGlobalKey,PanelContext panelContext,R data,List<FormFieldDefine> fieldDefs) throws Exception {
		FormViewSetting setting = widgetParam.getSetting(FormViewSetting.class);
		BoxDto mainBox = new BoxDto().setVertical(true).setExpandInBox(false);
		mainBox.setUnitName(WidgetLayoutUtil.UNIT_NAME_CUSTOM_BOX);
		mainBox.setStyleName(getMainBoxStyle());
		//动态关联模型选择器
		appendModelSelector(panelContext, mainBox);
//		if(ExceptionHandlerFactory.isDebug()) {
//			mainBox.addChild(buildButton("", JDFICons.bug, CMD_DEBUG, null));
//		}
		FormFieldEditorFactory factory = getEditorFactory(panelGlobalKey,widgetParam);
		Map<String,WidgetDto> editorMap = new HashMap<>();
		for (FormFieldDefine fieldDef : fieldDefs) {
			Object value = data.getAttrValueByCode(fieldDef.getCode());
			WidgetDto editor = buildEditor(panelContext, factory, data, fieldDef, value);
			if(editor == null) {
				continue;
			}
			editorMap.put(editor.getWidgetId(), editor);
			BoxDto editorBox = buildFieldEditorBox(panelContext, fieldDef, setting, data, editor);
			mainBox.addChild(WidgetLayoutUtil.wrapDesignContainer(fieldDef.getName(),editorBox));
		}
		buildFormButtonBox(panelContext, panelGlobalKey,mainBox);
		buildExtView(panelContext,panelGlobalKey,mainBox);
		return mainBox;
	}
	
	protected BoxDto buildFieldEditorBox(PanelContext panelContext,FormFieldDefine fieldDef,FormViewSetting setting,R data,WidgetDto editor) throws Exception {
		String toolTips = getI18nString(panelContext, fieldDef.getTips());
		String descritption = getI18nString(panelContext,fieldDef.getDescription());
		String fieldLabel = getFieldLabel(panelContext, data, fieldDef);
		LabelLayoutDirection fieldLabelLayoutDirection = setting.getLabelLayoutDirectionEnum();
		fieldLabelLayoutDirection = fieldLabelLayoutDirection == null ? LabelLayoutDirection.horizional : fieldLabelLayoutDirection;
		fieldLabelLayoutDirection = fieldDef.getLabelLayoutDirection() == null ? fieldLabelLayoutDirection : fieldDef.getLabelLayoutDirection();
		String fieldBoxStyle = getFieldBoxStyle();
		String labelSpanStyle = getLabelSpanStyle();
		String labelBoxStyle = getLabelBoxStyle();
		boolean visible = isFieldVisible(panelContext, fieldDef);
		boolean require = isFieldRequire(panelContext, fieldDef);
		Long labelWidth = fieldDef.getLabelWidth();
		if(labelWidth == null) {
			setPanelContext(panelContext);
			labelWidth = (long) getDefaultFieldLabelWidth();
		}
		setEditorSize(fieldDef, setting, editor);
		if(!CmnUtil.isStringEmpty(toolTips)) {
			if(editor instanceof TextEditorDto) {
				((TextEditorDto) editor).setHintText(toolTips);
				if(toolTips.contains("\n")) {
					//设置多行显示的提示语，只对多行文本有效
					String[] toolTipsArr = toolTips.split("\n");
					((TextEditorDto) editor).setHintMaxLines(toolTipsArr.length);
				}
			}else if(editor instanceof SelectEditorDto) {
				((SelectEditorDto) editor).setHintText(toolTips);
			}
		}
		BoxDto editorBox = wrapEditor(fieldLabel, labelWidth,fieldLabelLayoutDirection, descritption, editor, require, visible, fieldBoxStyle, labelSpanStyle,labelBoxStyle); 
		editorBox.setGroupIds(fieldDef.getTagGroupIdArray());
		return editorBox;
	}
	
	protected void setEditorSize(FormFieldDefine fieldDef,FormViewSetting setting,WidgetDto editor) {
		boolean isComponentWidthAdaptive = fieldDef.isComponentExpandInBox();
		if(!isComponentWidthAdaptive) {
			isComponentWidthAdaptive = setting.isComponentWidthAdaptive();
		}
		if(isComponentWidthAdaptive) {
			editor.setExpandInBox(true);
			if(editor.getPreferSize() != null) {
				editor.getPreferSize().setWidth(null);
			}
			if(editor.getMaxSize() != null) {
				editor.getMaxSize().setWidth(null);
			}
		}else {
			Long componentWidth = fieldDef.getComponentWitdh();
			if(componentWidth == null) {
				componentWidth = setting.getComponentWidth();
			}
			if(componentWidth != null) {
				if(editor.getPreferSize() != null) {
					editor.getPreferSize().setWidth(Double.valueOf(componentWidth));
				}else {
					editor.setPreferSize(SizeDto.width(componentWidth));
				}
			}
		}
		if(fieldDef.getComponentHeight() != null) {
			if(editor.getPreferSize() != null) {
				editor.getPreferSize().setHeight(Double.valueOf(fieldDef.getComponentHeight()));
			}else {
				editor.setPreferSize(SizeDto.height(fieldDef.getComponentHeight()));
			}
		}
	}
	
	public void buildFormButtonBox(PanelContext panelContext,String panelGlobalKey,BoxDto mainBox) throws Exception {
		if(widgetParam.getButtonDefines().isEmpty())
			return;
		BoxDto box = new BoxDto().setVertical(false).setExpandInBox(false);
		for(ButtonDefine buttonDef : widgetParam.getRootFormButtons()) {
			String buttonLabel = getI18nString(panelContext, buttonDef.getLabel());
			if(buttonDef.isButtonGroup()) {
				ButtonDto button = buildButton(buttonLabel, buttonDef.getIconSrc(), CMD_CLICK_FORM_BUTTON_GROUP, null);
				button.setWidgetId(buttonDef.getName());
				button.setUnitName(WidgetLayoutUtil.UNIT_NAME_CUSTOM_BUTTON);
				button.setGroupIds(buttonDef.getTagGroupIdArray());
//				setButtonPrivilege(actionPriviegeMap, button);
				setButtonPrivilege(button,panelContext);
				box.addChild(button);
			}else {
				ButtonDto button = buildButton(buttonLabel, buttonDef.getIconSrc(), buttonDef.getName(), null);
				button.setUnitName(WidgetLayoutUtil.UNIT_NAME_CUSTOM_BUTTON);
//				setButtonPrivilege(actionPriviegeMap, button);
				button.setGroupIds(buttonDef.getTagGroupIdArray());
				setButtonPrivilege(button,panelContext);
				box.addChild(button);
			}
		}
		mainBox.addChild(box);
	}
	/**
	 * 设置按钮权限，生效顺序：buttonOverride > groupOverride
	 * @param button
	 * @throws Exception 
	 */
	protected void setButtonPrivilege(ButtonDto button,PanelContext panelContext) throws Exception {
		Tracer tracer = TraceUtil.getCurrentTracer();
		if(FeDebugUtil.isEnableDebug(panelContext)) {
			if(button.getGroupIds() != null)
				tracer.debug("按钮["+button.getWidgetId()+"]标签组：" + String.join(",", button.getGroupIds()));
			else
				tracer.debug("按钮["+button.getWidgetId()+"]标签组：[]" );
		}
		if(widgetParam.getButtonVisibleOverride() != null && widgetParam.getButtonVisibleOverride().containsKey(button.getWidgetId())) {
			button.setVisible(widgetParam.getButtonVisibleOverride().get(button.getWidgetId()));
		}else if(widgetParam.getGroupVisibleOverride() != null && !CmnUtil.isArrayEmpty(button.getGroupIds())) {
			for(String tagGroup : button.getGroupIds()) {
				if(widgetParam.getGroupVisibleOverride().containsKey(tagGroup)) {
					button.setVisible(widgetParam.getGroupVisibleOverride().get(tagGroup));
				}
			}
		}
		if(widgetParam.getButtonWritableOverride() != null && widgetParam.getButtonWritableOverride().containsKey(button.getWidgetId())) {
			button.setWritable(widgetParam.getButtonWritableOverride().get(button.getWidgetId()));
		}else if(widgetParam.getGroupWritableOverride() != null && !CmnUtil.isArrayEmpty(button.getGroupIds())) {
			for(String tagGroup : button.getGroupIds()) {
				if(widgetParam.getGroupWritableOverride().containsKey(tagGroup)) {
					button.setWritable(widgetParam.getGroupWritableOverride().get(tagGroup));
				}
			}
		}
	}
	/**
	 * 添加扩展组件
	 * @param panelContext
	 * @param panelGlobalKey
	 * @param mainBox
	 * @throws Exception
	 */
	public void buildExtView(PanelContext panelContext,String panelGlobalKey,BoxDto mainBox)throws Exception{
		if(widgetParam.getExtViewFuncs().isEmpty())
			return;
		for(RefActionConfig func : widgetParam.getExtViewFuncs()) {
			Action viewAction = func.getAction();
			if(viewAction == null)
				continue;
			IDCRuntimeContext rtx = getOrPrepareRtx(panelContext, panelGlobalKey);
			BaseFeActionParameter.setTriggerTime(rtx, TriggerTime.TriggerBeforeInit);
			WidgetDto widget = (WidgetDto) IActionMgr.get().executeAction(viewAction, rtx);
			ContainerDto container = WidgetLayoutUtil.wrapDesignContainer(func.getName(), widget);
			mainBox.addChild(container);
		}
	}
	/**
	 * 在表单界面初始化时调用有效，生效顺序：visibleOverride > groupVisibleOverride
	 * @param panelContext
	 * @param fieldDef
	 * @return
	 */
	public boolean isFieldVisible(PanelContext panelContext,FormFieldDefine fieldDef) {
		if(widgetParam.getVisibleOverride() != null) {
			Boolean flag = widgetParam.getVisibleOverride().get(fieldDef.getCode());
			if(flag != null)
				return flag;
		}
		if(!fieldDef.getTagGroupList().isEmpty() && widgetParam.getGroupVisibleOverride() != null) {
			for(String tagGroup : fieldDef.getTagGroupList()) {
				Boolean flag = widgetParam.getGroupVisibleOverride().get(tagGroup);
				if(flag != null)
					return flag;
			}
		}
		return true;
	}
	/**
	 * 在表单界面初始化时调用有效，生效顺序：writableOverride > groupWritableOverride
	 * @param panelContext
	 * @param fieldDef
	 * @return
	 */
	public boolean isFieldWritable(PanelContext panelContext,FormFieldDefine fieldDef) {
		boolean writable = widgetParam.isWritable();
		if(writable) {
			if(widgetParam.getWritableOverride() != null && widgetParam.getWritableOverride().containsKey(fieldDef.getCode())) {
				return widgetParam.getWritableOverride().get(fieldDef.getCode());
			}
		}
		if(!fieldDef.getTagGroupList().isEmpty() && widgetParam.getGroupWritableOverride() != null) {
			for(String tagGroup : fieldDef.getTagGroupList()) {
				Boolean flag = widgetParam.getGroupWritableOverride().get(tagGroup);
				if(flag != null)
					return flag;
			}
		}
		return writable;
	}
	/**
	 * 在表单界面初始化时调用有效，生效顺序：requireOverride > groupRequireOverride
	 * @param panelContext
	 * @param fieldDef
	 * @return
	 */
	public boolean isFieldRequire(PanelContext panelContext,FormFieldDefine fieldDef) {
		if(!isFieldWritable(panelContext, fieldDef)) {
			//不可写时，不需要必填
			return false;
		}
		if(!fieldDef.isNotNull()) {
			if(widgetParam.getRequireOverride() != null) {
				Boolean require = widgetParam.getRequireOverride().get(fieldDef.getCode());
				if(require != null)
					return require;
			}
			if(!fieldDef.getTagGroupList().isEmpty() && widgetParam.getGroupRequireOverride() != null) {
				for(String tagGroup : fieldDef.getTagGroupList()) {
					Boolean flag = widgetParam.getGroupRequireOverride().get(tagGroup);
					if(flag != null)
						return flag;
				}
			}
		}
		return fieldDef.isNotNull();
	}
	/**
	 * 获取属性显示标签
	 * @param panelContext
	 * @param data
	 * @param fieldDef
	 * @return
	 * @throws Exception
	 */
	public String getFieldLabel(PanelContext panelContext,R data,FormFieldDefine fieldDef) throws Exception{
		String fieldLabel = fieldDef.getAlias();
		fieldLabel = !CmnUtil.isStringEmpty(fieldLabel) ? fieldLabel : fieldDef.getName();
		return getI18nString(panelContext, fieldLabel);
	}
	
	public WidgetDto buildEditor(PanelContext panelContext,FormFieldEditorFactory factory,R data,FormFieldDefine fieldConfig,Object value) throws Exception {
		boolean writable = isFieldWritable(panelContext,fieldConfig);
		WidgetDto editor = factory.buildEditor(panelContext,data, fieldConfig, value,writable);
		if(editor instanceof EditorDto) {
			editor.setStyleName(getEditorStyle(fieldConfig,writable));
		}else {
			if(!CmnUtil.isStringEmpty(fieldConfig.getComponentStyle()))
				editor.setStyleName(fieldConfig.getComponentStyle());
		}
		return editor;
	}
	
	public FormFieldEditorFactory getEditorFactory(String panelGlobalKey,WidgetParam widgetParam) {
		FormFieldEditorFactory factory =  FormFieldEditorFactory.create(panelGlobalKey,this,isLazyQueryCompoundField());
		factory.setLayoutMode(isLayoutMode());
		return factory;
	}

	@Override
	public void verifyValue(PanelContext context, List<EditorFieldDefine> fieldDefs, R object,
			PanelValue panelValue) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public BoxDto getTopBar(PanelContext context)throws Exception{
		if(widgetParam.isEmbedForm())
			return null;
		BoxDto topBar = new BoxDto().setVertical(false).setExpandInBox(false);
		topBar.addChild(new PlaceholderDto());
		topBar.setStyleName(FeStyleConst.common_form_top_bar_right);
		return topBar;
	}
	
	public WidgetDto getBottomBar(PanelContext panelContext) throws Exception {
		return null;
	}
	
	public final static String CacheKey_EditorFieldDefine = "$cacheEditorFieldDefine";
	@Override
	public List<EditorFieldDefine> getEditorFieldDefine(PanelContext context, R data, PanelValue panelValue)
			throws Exception {
//		List<EditorFieldDefine> cacheEditorDefs = (List<EditorFieldDefine>) getPanelCacheValue(context, CacheKey_EditorFieldDefine);
//		if(cacheEditorDefs != null) {
//			return cacheEditorDefs;
//		}
		List<FormFieldDefine> fieldDefs = getFormFieldDefines(context);
		List<EditorFieldDefine> editorFieldDefines = new ArrayList<>();
		for(FormFieldDefine field : fieldDefs) {
			EditorFieldDefine fieldEditorDef = getEditorFactory(context.getCurrentPanelGlobalKey(),widgetParam).getEditorFieldDefine(context, panelValue, field);
			if(fieldEditorDef != null) {
				Boolean require = isFieldRequire(context, field);
				if(require != null) {
					fieldEditorDef.setRequire(require);
				}
				editorFieldDefines.add(fieldEditorDef);
			}
		}
//		setPanelCacheValue(context, CacheKey_EditorFieldDefine, editorFieldDefines);
		return editorFieldDefines;
	}
	
	public R getDataFormGui(PanelContext panelContext,boolean verifyRequire,String action) throws Exception {
		PanelValue panelValue = queryPanelValue(panelContext, false);
		R form = widgetParam.getData();
		if(verifyRequire) {
			List<EditorFieldDefine> fieldDefs = getEditorFieldDefine(panelContext, form, panelValue);
			verifyRequireFields(panelContext, fieldDefs, panelValue,action);
		}
		R cloneForm = ToolUtilities.clone(form);
		writePanelValue(panelContext, cloneForm, panelValue);
		new FormVisitor().visit(cloneForm);
		return cloneForm;
	}
	
	public IgnoreRequireSetting getIgnoreSetting(String action) {
		return widgetParam.getIgnoreRequireSettingByAction(action);
	}
	/**
	 * 校验必填字段，调用此方法时，将根据属性定义列表中定义的属性对可见属性进行必填校验
	 *
	 * @param context
	 * @param fieldDefs
	 * @param panelValue
	 * @throws Exception
	 */
	public void verifyRequireFields(PanelContext context, List<EditorFieldDefine> fieldDefs, PanelValue panelValue,String action) throws Exception {
		IgnoreRequireSetting ignoreSetting = getIgnoreSetting(action);
		if(ignoreSetting == null) {
			super.verifyRequireFields(context, fieldDefs, panelValue);
		}else {
			List<String> ignoreRequireFields = ignoreSetting.getFieldCodes();
			List<EditorFieldDefine> copyFieldDefs = new ArrayList<>();
			for(EditorFieldDefine fieldDef : fieldDefs) {
				EditorFieldDefine copyFieldDef = ToolUtilities.clone(fieldDef);
				if(fieldDef.isRequire()) {
					copyFieldDef.setRequire(!ignoreRequireFields.contains(copyFieldDef.getName()));
				}else if(widgetParam.getRequireOverride() != null) {
					Boolean require = widgetParam.getRequireOverride().get(fieldDef.getName());
					if(require != null && require) {
						copyFieldDef.setRequire(!ignoreRequireFields.contains(copyFieldDef.getName()));
					}
				}
				copyFieldDefs.add(copyFieldDef);
			}
			super.verifyRequireFields(context, copyFieldDefs, panelValue);
		}
	}
	/**
	 * 设置属性编辑器的值，在监听触发时可用
	 * @param ctx	面板上下文
	 * @param form	表单
	 * @param widgetId	属性编码
	 * @param value	设置的值
	 * @throws Exception
	 */
	public void setEditorValue(PanelContext ctx, R form,String widgetId, Object value) throws Exception {
		FormFieldEditorFactory factory = getEditorFactory(ctx.getCurrentPanelGlobalKey(), widgetParam);
		List<FormFieldDefine> fieldConfigs = getFormFieldDefines(ctx);
		Optional<FormFieldDefine> option = fieldConfigs.stream().filter(v->v.getCode().equals(widgetId)).findFirst();
		Tracer tracer =TraceUtil.getCurrentTracer();
		if(!option.isPresent()) {
			tracer.warning("未找到属性code为：" + widgetId+"的属性！");
			return;
		}
		FormFieldDefine fieldConfig = option.get();
		//附件类型设置需要从缓存的attachMap进行设置，与正常设值不同
		if(fieldConfig.getDataTypeEnum() == DataType.Attach) {
			rebuildExtBeFileMap(form, widgetId, (List<AttachData>)value);
		}else if(fieldConfig.getDataTypeEnum() == DataType.NestingModel) {
			TableData tableData = value == null ? new TableData(fieldConfig.getTableFormModel()) : (TableData) value;
			rebuildExtBeFileMap(tableData);
		}
		WidgetDto editor = buildEditor(ctx, factory, form, fieldConfig, value);
		if(editor instanceof EditorDto && !(editor instanceof CustomizeEditorDto)) {
			Object editorValue = ((EditorDto) editor).getValue();
			tracer.debug("设置属性["+fieldConfig.getName()+"]值=" + editorValue);
			if(editorValue == null) {
				setEditorValue(ctx, widgetId, new NullPojo());
			}else {
				setEditorValue(ctx, widgetId, ((EditorDto) editor).getValue());
			}
		}else if(editor instanceof LabelDto){
			throw new Exception("属性["+fieldConfig.getName()+"]设置值不合法：" + value);
		}else {
			FormViewSetting setting = widgetParam.getSetting(FormViewSetting.class);
			setEditorSize(fieldConfig, setting, editor);
			SinglePanelDto panel = SinglePanelDto.wrap(editor);
			panel.setWidgetId(ctx.getCurrentPanelWidgetId()).setPanelGlobalKey(ctx.getCurrentPanelGlobalKey());
			panel.setBinaryData(widgetParam);
			setListenerByListenerDefine(ctx,panel, widgetParam.getListenerDefines(),ListenerApplyLocation.Form);
//			setListenerByListenerDefine(ctx,editor, widgetParam.getListenerDefines(),ListenerApplyLocation.Form);
			rebuildChild(ctx, editor);
		}
//		setEditorValues(ctx, form, ImmutableMap.of(widgetId, value));
	}
	/**
	 * 更新界面属性编辑组件的值
	 * @param ctx
	 * @param form	表单
	 * @param fieldValueMap	更新的属性值列表	key：属性code value：属性值
	 * @throws Exception
	 */
	public void setEditorValues(PanelContext ctx, R form,Map<String,Object> fieldValueMap) throws Exception {
		setEditorValues(ctx, form, fieldValueMap, true);
	}
	/**
	 * 更新界面属性编辑组件的值
	 * @param ctx
	 * @param form	表单
	 * @param fieldValueMap	更新的属性值列表	key：属性code value：属性值
	 * @param triggerOnVlueChange 是否触发值改变监听
	 * @throws Exception
	 */
	public void setEditorValues(PanelContext ctx, R form,Map<String,Object> fieldValueMap,boolean triggerOnVlueChange) throws Exception {
		List<CsonPojo> callbacks = buildSetEditorValuesCallback(ctx, form, fieldValueMap);
		if(!callbacks.isEmpty())
			BatchExecuteCallback.batchExecute(ctx.getChannel(), callbacks);
	}
	/**
	 * 构建更新界面属性编辑组件的值的前端回调
	 * @param ctx
	 * @param form
	 * @param fieldValueMap
	 * @return
	 * @throws Exception
	 */
	public List<CsonPojo> buildSetEditorValuesCallback(PanelContext ctx, R form,Map<String,Object> fieldValueMap) throws Exception {
		return buildSetEditorValuesCallback(ctx, form, fieldValueMap, true);
	}
	/**
	 * 构建更新界面属性编辑组件的值的前端回调
	 * @param ctx
	 * @param form
	 * @param fieldValueMap
	 * @param triggerOnVlueChange 是否触发值改变监听
	 * @return
	 * @throws Exception
	 */
	public List<CsonPojo> buildSetEditorValuesCallback(PanelContext ctx, R form,Map<String,Object> fieldValueMap,boolean triggerOnVlueChange) throws Exception {
		FormFieldEditorFactory factory = getEditorFactory(ctx.getCurrentPanelGlobalKey(), widgetParam);
		List<FormFieldDefine> fieldConfigs = getFormFieldDefines(ctx);
		List<WidgetDto> rebuildEditors = new ArrayList<>();
		Map<String,Object> rebuildEditorValues = new LinkedHashMap<>();
		Tracer tracer = TraceUtil.getCurrentTracer();
		List<CsonPojo> callbacks = new ArrayList<>();
		for(String widgetId : fieldValueMap.keySet()) {
			Object value = fieldValueMap.get(widgetId);
			Optional<FormFieldDefine> option = fieldConfigs.stream().filter(v->v.getCode().equals(widgetId)).findFirst();
			if(!option.isPresent()) {
				tracer.warning("未找到属性code为：" + widgetId+"的属性！");
				continue;
			}
			FormFieldDefine fieldConfig = option.get();
			//附件类型设置需要从缓存的attachMap进行设置，与正常设值不同
			if(fieldConfig.getDataTypeEnum() == DataType.Attach) {
				rebuildExtBeFileMap(form, widgetId, (List<AttachData>)value);
			}else if(fieldConfig.getDataTypeEnum() == DataType.NestingModel) {
				TableData tableData = value == null ? new TableData(fieldConfig.getTableFormModel()) : (TableData) value;
				rebuildExtBeFileMap(tableData);
			}
			WidgetDto editor = buildEditor(ctx, factory, form, fieldConfig, value);
			if(editor instanceof EditorDto && !(editor instanceof CustomizeEditorDto)) {
				Object editorValue = ((EditorDto) editor).getValue();
				tracer.debug("设置属性["+fieldConfig.getName()+"]值=" + editorValue);
				if(editorValue == null) {
//					setEditorValue(ctx, widgetId, new NullPojo());
					if(editor instanceof DateTimePickerDto
							|| editor instanceof DatePickerDto
							|| editor instanceof TimePickerDto) {
						rebuildEditorValues.put(widgetId, new LinkedList<>());
					}else {
						rebuildEditorValues.put(widgetId, new NullPojo());
					}
				}else {
//					setEditorValue(ctx, widgetId, ((EditorDto) editor).getValue());
					rebuildEditorValues.put(widgetId, ((EditorDto) editor).getValue());
				}
			}else if(editor instanceof LabelDto){
				throw new Exception("属性["+fieldConfig.getName()+"]设置值不合法：" + value);
			}else {
				FormViewSetting setting = widgetParam.getSetting(FormViewSetting.class);
				setEditorSize(fieldConfig, setting, editor);
				SinglePanelDto panel = SinglePanelDto.wrap(editor);
				panel.setWidgetId(ctx.getCurrentPanelWidgetId()).setPanelGlobalKey(ctx.getCurrentPanelGlobalKey());
				panel.setBinaryData(widgetParam);
				setListenerByListenerDefine(ctx,panel, widgetParam.getListenerDefines(),ListenerApplyLocation.Form);
//				rebuildChild(ctx, editor);
				rebuildEditors.add(editor);
			}
			
			for(String rebuildEditorWidgetId : rebuildEditorValues.keySet()) {
				SetEditorValue callback = new SetEditorValue();
				callback.setPanelGlobalKey(ctx.getCurrentPanelGlobalKey());
				callback.setWidgetId(rebuildEditorWidgetId).setValue(rebuildEditorValues.get(rebuildEditorWidgetId))
				.setTriggerOnValueChange(triggerOnVlueChange);
				callbacks.add(callback);
			}
		}
		if(!rebuildEditors.isEmpty())
			callbacks.add(new RebuildChild().setNewWidgets(rebuildEditors).setPanelGlobalKey(ctx.getCurrentPanelGlobalKey()));
		return callbacks;
	}
	
	private void rebuildExtBeFileMap(TableData tableData) throws Exception {
		for(Form nestingForm : tableData.getRows()) {
			rebuildExtBeFileMap(nestingForm);
		}
	}
	
	private void rebuildExtBeFileMap(Form form) throws Exception {
		for(String key : form.getData().keySet()) {
			Object nestingValue = form.getAttrValueByCode(key);
			if(nestingValue instanceof List) {
				List nestingList = (List) nestingValue;
				if(!CmnUtil.isCollectionEmpty(nestingList)) {
					if(nestingList.get(0) instanceof AttachData) {
						rebuildExtBeFileMap(form, key, nestingList);
					}
				}
			}else if(nestingValue instanceof TableData) {
				rebuildExtBeFileMap((TableData)nestingValue);
			}
		}
	}
	/**
	 * 设置界面属性值时重建附件map
	 * @param form
	 * @param fieldCode
	 * @param lstAttach
	 * @throws Exception
	 */
	private void rebuildExtBeFileMap(Form form,String fieldCode,List<AttachData> lstAttach) throws Exception {
		Map<String,List<BeFile>> attachMap = (Map<String, List<BeFile>>) form.getExtField(AttachEditorHandler.EXT_FIELD_KEY_BEFILE_MAP);
		if(attachMap != null) {
			List<BeFile> beFiles = new ArrayList<>();
			if(lstAttach != null) {
				//这里对附件做特殊处理，做仿真缓存
				for(AttachData attach : lstAttach) {
					BeFile file = new BeFile();
					if(attach.isDirty()) {
						file = FeFileUtil.simulateUploadFile(attach.getName(), attach.getContent());
					}else {
						file.setName(attach.getName());
						file.setStorPath(AbsUploadPanel.CUSTOM_FILE_RESOURCE+attach.getFormUuid()+"/"+attach.getAttrName()+"/"+attach.getName());
					}
					beFiles.add(file);
				}
			}
			attachMap.put(fieldCode, beFiles);
		}
	}
	
	/**
	 * 根据配置构建提交进度条
	 * @param context
	 * @param submitButtonHook
	 * @return
	 * @throws Exception
	 */
	public Progress newSubmitProgress(PanelContext context,SubmitButtonHookConfigDto submitButtonHook) throws Exception {
		if(submitButtonHook == null || CmnUtil.isStringEmpty(submitButtonHook.getProgressClass())) {
//			FeLoadingProgressCtrlContextDto ctrlContextDto = new FeLoadingProgressCtrlContextDto(context, null);
//			return Progress.wrap(new CFeProgressCtrlWithToast(ctrlContextDto));
			return null;
		}
		try(IJson json = IJsonService.get().getJson()) {
			Class<? extends AbsProgressBuilder> clazz = (Class<? extends AbsProgressBuilder>) ClassFactory.getValidClassLoader().loadClass(submitButtonHook.getProgressClass());
			AbsProgressBuilder progresBuilder = json.fromJson(submitButtonHook.getProgressParams(), clazz);
			return progresBuilder.buildProgress(context);
		}catch(Exception e){
			Tracer tracer = TraceUtil.getCurrentTracer();
			String msg = getI18nString(context, BUILD_PROGRESS_FAILED);
			tracer.warning(msg);
			PopToast.warning(context.getChannel(), msg);
//			FeLoadingProgressCtrlContextDto ctrlContextDto = new FeLoadingProgressCtrlContextDto(context, null);
//			return Progress.wrap(new CFeProgressCtrlWithToast(ctrlContextDto));
			return null;
		}
	}
	
	@Override
	public List<RefActionConfig> getActionDefines() throws Exception {
		return getWidgetParam().getActionDefines();
	}
	
	@Override
	public ButtonDefine getButtonDefineByCommand(String command) throws Exception {
		return widgetParam.getButtonDefineByName(command);
	}
	@Override
	public List<ListenerDefine> getViewInitListenerDefines() throws Exception {
		return widgetParam.getViewInitListenerDefines(ListenerApplyLocation.Form);
	}

	@Override
	public List<ListenerDefine> getViewInitedListenerDefines() throws Exception {
		return widgetParam.getViewInitedListenerDefines(ListenerApplyLocation.Form);
	}
	@Override
	public ListenerDefine getListenerDefineByCommand(String command) throws Exception {
		return getWidgetParam().getListenerDefineByCommand(command);
	}
	@Override
	public ListenerDefine getListenerDefineByKeyBoard(KeyboardDto keyboard) throws Exception {
		return getWidgetParam().getListenerDefineByKeyboard(keyboard);
	}
//	@Override
//	public ListenerDefine getListenerDefineBySourceWidgetId(String sourceWidgetId) throws Exception {
//		return getWidgetParam().getListenerDefineBySourceWidgetId(sourceWidgetId);
//	}
	@SuppressWarnings({ "rawtypes" })
	@Override
	public Object onListener(ListenerDto listener, PanelContext panelContext, WidgetDto source) throws Exception {
		Pair<Boolean, Object> resultPair = findListenerDefineAndRun(panelContext, listener);
		if(resultPair.left)
			return resultPair;
		if(listener instanceof OnValueChanged && CmnUtil.isStringEqual(listener.getSourceWidgetId(), WidgetID_SelectModel)) {
			onModelChange(panelContext, ((OnValueChanged)listener).getValue());
		}else if(listener.isServiceCommand(CMD_AFTER_RENDERED)) {
			
		}else if(listener.isServiceCommand(CMD_DEBUG)) {
			onDebug(panelContext);
		}else if (listener.isServiceCommand(CMD_ON_VALUE_CHANGED)) {
			onValueChanged(listener, panelContext, source);
		}else if(listener.isServiceCommand(CMD_QUIT_POPUP)){
			QuitPopup.quit(panelContext);
		}else  if(listener.isServiceCommand(CMD_ON_BLUR)){
			onBlur(listener, panelContext, source);
		}else if(listener.isServiceCommand(CMD_REFRESH)) {
			onRefresh(panelContext);
		}else if(listener.isServiceCommand(CMD_DELETE)) {
			R data = widgetParam.getData();
			return data;
		}else if(listener.isServiceCommand(CMD_CLICK_FORM_BUTTON_GROUP)){
			onClickFormButtonGroup(listener,panelContext,source);
		} else if(listener.isServiceCommand(CMD_CLICK_FORM_BUTTON_GROUP_MENU_ITEM)){
			onClickFormButtonGroupMenuItem(listener,panelContext,source);
		}else {
        	boolean isTimerTrigge = onTimerTrigge(panelContext, listener, widgetParam.getTimerConfigs(), widgetParam.getActionDefineMap());
        	if(isTimerTrigge) {
				return null;
			}
		}
			
		return null;
	}
	
	public SinglePanelDto newEmptyFormPanel(PanelContext panelContext,String panelGlobalKey) throws Exception {
		BoxDto mainBox = new BoxDto().setVertical(true).setExpandInBox(false);
		mainBox.setUnitName(WidgetLayoutUtil.UNIT_NAME_CUSTOM_BOX);
		mainBox.setStyleName(getMainBoxStyle());
		//动态关联模型占位
		appendModelSelector(panelContext, mainBox);
		SinglePanelDto panel = SinglePanelDto.wrap(mainBox).setWidgetId(widgetParam.getWidgetId()).setExpandInBox(true);
		//FIXME chenxb 这里必须设置允许遮罩,否则无法扩张到整个界面，导致切换时子表单面板无法撑开
		panel.setBinaryData(widgetParam);
		panel.setWrapMask(true);
		panel.setPanelGlobalKey(panelGlobalKey);
		return panel;
	}
	
	public void onModelChange(PanelContext panelContext,Object value) throws Exception {
		PanelDto panel = null;
		R data = widgetParam.getData();
		String owner = data.getStringByCode(Form.Owner);
		Tracer tracer = TraceUtil.getCurrentTracer();
		if(NullPojo.isNull(value)) {
			Form newData = new Form(KeyValueTableExtend.FakeFormModelId);
			newData.setAttrValueByCode(Form.Owner,owner);
			widgetParam.setData((R)newData);
			panel = newEmptyFormPanel(panelContext,panelContext.getCurrentPanelGlobalKey());
			tracer.debug("模型清除，空面板。");
		}else {
			PairDto<String, String> pair = (PairDto<String, String>) value;
			
			String relateModel = pair.getKey();
			tracer.debug("选择模型："+relateModel);
			Form relateForm = null;
			boolean isAdd = true;
			if(IUserMgr.get().isUserModel(relateModel)){
				relateForm = new User(relateModel);
			}else if(IRoleMgr.get().isOrgModel(relateModel)) {
				relateForm = new Org(relateModel);
			}else {
				relateForm = new Form(relateModel);
			}
			relateForm.setAttrValueByCode(Form.Owner, owner);
			widgetParam.setData((R)relateForm);
			Action dynamicAction = widgetParam.getDynamicFormModelViewAction(relateForm);
			if(dynamicAction != null) {
				try(IDao dao = IDaoService.newIDao()){
					IDCRuntimeContext rtx = getOrPrepareRtx(panelContext, panelContext.getCurrentPanelGlobalKey());
					rtx.setDao(dao);
					BaseFeActionParameter.setWidgetId(rtx, panelContext.getCurrentPanelWidgetId());
					FormParameter.setEmbedForm(rtx, true);
					FormParameter.setInitDataValue(rtx, isAdd);
					FormParameter.setFormModelSelectorParam(rtx, widgetParam.getModelSelectParam());
					FormParameter.setCommandCallbackListener(rtx, widgetParam.getCommandCallbackLsnrs());
					FormParameter.setDynamicFormModelViewActionSettings(rtx, widgetParam.getFormViewSettings());
					panel = (PanelDto) IActionMgr.get().executeAction(dynamicAction, rtx);
					tracer.debug("通过视图["+dynamicAction.getCode()+"]构建界面！");
				}
			}else {
				//考虑到切换面板时的参数都需要重新初始化，这里只设置默认初始化时的参数
				BaseDataViewParam<Form> editParam = new BaseDataViewParam<>();
				editParam.setModelId(relateForm.getFormModelId());
				editParam.setData(relateForm);
				editParam.setWritable(widgetParam.isWritable());
				editParam.setSetting(AppCacheUtil.newFormSetting(panelContext));
				editParam.setModelSelectParam(widgetParam.getModelSelectParam());
				editParam.setFormViewSettings(widgetParam.getFormViewSettings());
				editParam.setWidgetId(widgetParam.getWidgetId()).setPanelGlobalKey(widgetParam.getPanelGlobalKey());
				editParam.setEmbedForm(widgetParam.isEmbedForm());
				editParam.setInvokeClass(widgetParam.getInvokeClass());
				editParam.setCommandCallbackLsnrs(widgetParam.getCommandCallbackLsnrs());
				widgetParam = (T) editParam;
				panel = (PanelDto) getWidget(panelContext);
				tracer.debug("默认getWidget构建界面！");
			}
		}
		RebuildChild.rebuild(panelContext, panel);
		Cells.get(getService()).setPanelWidgetParam(panelContext, panel.getWidgetId(), widgetParam);
	}
	
	/**
	 * 点击工具栏按钮组响应
	 * @param listener
	 * @param context
	 * @param source
	 * @throws Exception
	 */
	public void onClickFormButtonGroup(ListenerDto listener,PanelContext context,WidgetDto source) throws Exception {
		String groupName = listener.getSourceWidgetId();
		List<ButtonDefine> childButtons = getWidgetParam().getFormButtonInGroup(groupName);
		Map<String,ActionPrivilegeDto> actionPrivMap = getActionPrivielge(context);
		List<MenuItemDto> items = new ArrayList<>();
		for(ButtonDefine childButton : childButtons) {
			String buttonLabel = getI18nString(context, childButton.getLabel());
			MenuItemDto item = new MenuItemDto().setLabel(buttonLabel).setIcon(childButton.getIconSrc());
			Map<String,Object> data = new LinkedHashMap<String,Object>();
			data.put("button", childButton.getName());
			item.setOnClick(newListener(getService(), CMD_CLICK_FORM_BUTTON_GROUP_MENU_ITEM, true, data));
			ActionPrivilegeDto actionPriv = getButtonPrivilege(actionPrivMap, childButton.getName());
			if(actionPriv != null) {
				if(actionPriv.isVisible() && actionPriv.isOperatable())
					items.add(item);
			}else {
				items.add(item);
			}
		}
		if(items.isEmpty())
			return;
		MenuDto menu = new MenuDto(items);
		PopMenu.attachShow(context, menu, null, MenuPosition.right_top);
	}
	/**
	 * 点击工具栏按钮组菜单项
	 * @param listener
	 * @param context
	 * @param source
	 * @throws Exception
	 */
	public void onClickFormButtonGroupMenuItem(ListenerDto listener,PanelContext context,WidgetDto source) throws Exception {
		FeDeliverData<Map<String,Object>> feData = (FeDeliverData<Map<String, Object>>) listener.getBinaryData();
		Map<String,Object> data = feData.getData();
		String buttonName = (String) data.get("button");
		//转换成表格的上下文进行操作，与按钮平铺相同处理逻辑
		ListenerDefine listenDef = getWidgetParam().getListenerDefineBySourceWidgetId(buttonName);
		Tracer tracer = TraceUtil.getCurrentTracer();
		if(listenDef == null) {
			tracer.warning("未找到源组件" + buttonName +"的监听定义！");
			return;
		}
		ListenerDto buttonListener = newListener(getService(), listenDef.getResponseCommand(), true, null);
		onListener(buttonListener, context, source);
	}
	
	public final static String CacheKey_DebugLog = "$debugLog";
	public void onDebug(PanelContext panelContext) throws Exception {
		String debugLog = (String) getPanelCacheValue(panelContext, CacheKey_DebugLog);
		SinglePanelDto panel = SinglePanelDto.wrap(newTextArea("log", debugLog, 30, 30));
		panel.setPreferSize(WindowSizeDto.all(0.6, 0.6));
		PopDialog.show(panelContext, "日志", panel);
	}
	
	/**
	 * 查询表单属性是否可见，只在界面渲染完成后调用有效
	 * @param panelContext
	 * @param fieldCodes
	 * @return
	 * @throws Exception
	 */
	public Map<String,Boolean> queryFieldVisibleMap(PanelContext panelContext,Set<String> fieldCodes) throws Exception{
		if(fieldCodes.isEmpty())
			return new LinkedHashMap<>();
		Set<String> widgetIds = new LinkedHashSet<>();
		for(String fieldCode : fieldCodes) {
			String boxWidgetId = getEditorBoxWidgetId(fieldCode);
			widgetIds.add(boxWidgetId);
		}
		return QueryChildVisible.query(panelContext, widgetIds);
	}
	/**
	 * 控制属性显示隐藏，只在界面渲染完成后调用有效
	 * @param panelContext
	 * @param mapVisible
	 * @throws Exception
	 */
	public void setFieldVisible(PanelContext panelContext,Map<String,Boolean> mapVisible)throws Exception{
		Map<String,Boolean> mapBoxVisible = new HashMap<>();
		for(String fieldCode : mapVisible.keySet()) {
			String boxWidgetId = getEditorBoxWidgetId(fieldCode);
			mapBoxVisible.put(boxWidgetId, mapVisible.get(fieldCode));
		}
		if(!mapBoxVisible.isEmpty()) {
			try {
				SetChildVisible.set(panelContext, mapBoxVisible,true);
			}catch (Exception e) {
				ToolUtilities.warning(AbsFormView.class.getSimpleName(), "SetChildVisible Error:", e);
			}
		}
	}
	/**
	 * 构建设置属性可见的前端回调，，只在界面渲染完成后调用有效，用于批量向前端发送回调，减少通信次数
	 * @param panelContext
	 * @param mapVisible	属性可见配置	key：属性code，value：布尔值，true：可见，false：不可见
	 * @return
	 */
	public List<CsonPojo> buildSetFieldVisibleCallback(PanelContext panelContext,Map<String,Boolean> mapVisible){
		Map<String,Boolean> mapBoxVisible = new HashMap<>();
		for(String fieldCode : mapVisible.keySet()) {
			String boxWidgetId = getEditorBoxWidgetId(fieldCode);
			mapBoxVisible.put(boxWidgetId, mapVisible.get(fieldCode));
		}
		
		List<CsonPojo> callbacks = new ArrayList<>();
		if(!mapBoxVisible.isEmpty()) {
			SetChildVisible childVisibleCallback = new SetChildVisible();
			childVisibleCallback.setPanelGlobalKey(panelContext.getCurrentPanelGlobalKey());
			childVisibleCallback.setMapVisible(mapBoxVisible).setDeferErrors(true);
			callbacks.add(childVisibleCallback);
		}
		return callbacks;
	}
	/**
	 * 查询表单属性是否可写，只在界面渲染完成后调用有效
	 * @param panelContext
	 * @param fieldCodes
	 * @return
	 * @throws Exception
	 */
	public Map<String,Boolean> queryFieldWritableMap(PanelContext panelContext,Set<String> fieldCodes)throws Exception{
		if(fieldCodes.isEmpty())
			return new LinkedHashMap<>();
		List<WidgetDto> widgetDtos = QueryWidget.query(panelContext, ToolUtilities.toStringArray(fieldCodes));
		Set<String> basicWidgetIds = new LinkedHashSet<>();
		Set<String> panelWidgetIds = new LinkedHashSet<>();
		Map<String,Boolean> writable = new LinkedHashMap<>();
		for(WidgetDto widget : widgetDtos) {
			if(widget instanceof PanelDto) {
				Object widgetParamObj = widget.getBinaryData();
				if(widgetParamObj instanceof BaseWidgetParam) {
					writable.put(widget.getWidgetId(), ((BaseWidgetParam) widgetParamObj).isWritable());
				}
				panelWidgetIds.add(widget.getWidgetId());
			}else {
				basicWidgetIds.add(widget.getWidgetId());
			}
		}
		if(!basicWidgetIds.isEmpty()) {
			Map<String,Boolean> basicMap = QueryEditorsWritable.query(panelContext, basicWidgetIds);
			writable.putAll(basicMap);
		}
		return writable;
	}
	/**
	 * 控制属性是否可写，只在界面渲染完成后调用有效
	 * @param panelContext
	 * @param mapWritable	属性可写配置	key：属性code，value：布尔值，true：可写，false：不可写
	 * @throws Exception
	 */
	public void setFieldWritable(PanelContext panelContext,Map<String,Boolean> mapWritable)throws Exception{
		if(!mapWritable.isEmpty()) {
			List<WidgetDto> widgetDtos = QueryWidget.query(panelContext, ToolUtilities.toStringArray(mapWritable.keySet()));
			Map<String,Boolean> eidtorWritable = new HashMap<>();
			List<String> fieldCodes = new ArrayList<>();
			for(WidgetDto widget : widgetDtos) {
				if((!(widget instanceof EditorDto) || (widget instanceof CustomizeEditorDto))) {
					fieldCodes.add(widget.getWidgetId());
				}else {
					eidtorWritable.put(widget.getWidgetId(), mapWritable.get(widget.getWidgetId()));
				}
			}
			try {
				SetChildEditorWritable.set(panelContext, eidtorWritable,true);
			}catch (Exception e) {
				ToolUtilities.warning(AbsFormView.class.getSimpleName(), "SetChildEditorWritable Error:", e);
			}
			if(!fieldCodes.isEmpty()) {
				//切换属性的读写状态
				widgetParam.mergeWritableOverride(mapWritable);
				rebuildFieldWidgets(panelContext, fieldCodes);
				SetBinaryData.set(panelContext, panelContext.getCurrentPanelWidgetId(), widgetParam);
			}
			
		}
	}
	/**
	 * 构建控制属性是否可写的前端回调，只在界面渲染完成后调用有效，用于批量向前端发送回调，减少通信次数
	 * @param panelContext
	 * @param mapWritable	属性可写配置	key：属性code，value：布尔值，true：可写，false：不可写
	 * @throws Exception
	 */
	public List<CsonPojo> buildSetFieldWritableCallback(PanelContext panelContext,Map<String,Boolean> mapWritable) throws Exception{
		List<CsonPojo> callbacks = new ArrayList<>();
		if(!mapWritable.isEmpty()) {
			List<WidgetDto> widgetDtos = QueryWidget.query(panelContext, ToolUtilities.toStringArray(mapWritable.keySet()));
			Map<String,Boolean> eidtorWritable = new HashMap<>();
			List<String> fieldCodes = new ArrayList<>();
			for(WidgetDto widget : widgetDtos) {
				if((!(widget instanceof EditorDto) || (widget instanceof CustomizeEditorDto))) {
					fieldCodes.add(widget.getWidgetId());
				}else {
					eidtorWritable.put(widget.getWidgetId(), mapWritable.get(widget.getWidgetId()));
				}
			}
			SetChildEditorWritable childEditorWritableCallback = new SetChildEditorWritable();
			childEditorWritableCallback.setPanelGlobalKey(panelContext.getCurrentPanelGlobalKey());
			childEditorWritableCallback.setMapWritable(eidtorWritable).setDeferErrors(true);
			callbacks.add(childEditorWritableCallback);
			if(!fieldCodes.isEmpty()) {
				//切换属性的读写状态
				widgetParam.mergeWritableOverride(mapWritable);
				List<WidgetDto> rebuildWidgets = buildFieldWidgets(panelContext, fieldCodes);
				if(!rebuildWidgets.isEmpty()) {
					RebuildChild rebuildFieldEditorCallback = new RebuildChild();
					rebuildFieldEditorCallback.setPanelGlobalKey(panelContext.getCurrentPanelGlobalKey());
					rebuildFieldEditorCallback.setNewWidgets(rebuildWidgets);
					callbacks.add(rebuildFieldEditorCallback);
				}
				SetBinaryData setBinaryData = new SetBinaryData();
				setBinaryData.setPanelGlobalKey(panelContext.getCurrentPanelGlobalKey());
				setBinaryData.setWidgetId(panelContext.getCurrentPanelWidgetId()).setBinPojo((BinPojo)(new BinPojo()).setBinaryData(widgetParam));
				callbacks.add(setBinaryData);
			}
		}
		return callbacks;
	}
	/**
	 * 查询表单属性是否不可为空，只在界面渲染完成后调用有效
	 * @param panelContext
	 * @param fieldCodes
	 * @return
	 * @throws Exception
	 */
	public Map<String,Boolean> queryFieldRequireMap(PanelContext panelContext,Set<String> fieldCodes)throws Exception{
		Map<String,Boolean> mapRequire = new LinkedHashMap<>();
		if(fieldCodes.isEmpty())
			return mapRequire;
		Map<String,Boolean> requireOverride = widgetParam.getRequireOverride();
		List<FormFieldDefine> fieldDefines = getFormFieldDefines(panelContext);
		for(FormFieldDefine fieldDef : fieldDefines) {
			if(fieldCodes.contains(fieldDef.getCode()))
				continue;
			if(requireOverride.containsKey(fieldDef.getCode()))
				mapRequire.put(fieldDef.getCode(), requireOverride.get(fieldDef.getCode()));
			else
				mapRequire.put(fieldDef.getCode(), fieldDef.isNotNull());
		}
		return mapRequire;
	}

	private List<WidgetDto> buildFieldLabelWidgets(PanelContext panelContext,Map<String,Boolean> mapRequire)throws Exception{
		List<WidgetDto> fieldLabelWidgets = new ArrayList<>();
		if(!mapRequire.isEmpty()) {
			List<FormFieldDefine> fieldDefines = getFormFieldDefines(panelContext);
			Map<String,FormFieldDefine> fieldDefMap = new HashMap<>();
			for(FormFieldDefine fieldDef : fieldDefines) {
				fieldDefMap.put(fieldDef.getCode(), fieldDef);
			}
			for(String key : mapRequire.keySet()) {
				FormFieldDefine fieldDef = fieldDefMap.get(key);
				if(fieldDef == null)
					continue;
				String label = fieldDef.getAlias();
				label = CmnUtil.isStringEmpty(label) ? fieldDef.getName() : label;
				WidgetDto orgLabelBox = QueryWidget.queryOne(panelContext, getLabelBoxWidgetId(key));
				Double labelWidth = null;
				if(orgLabelBox != null && orgLabelBox.getPreferSize() != null)
					labelWidth = orgLabelBox.getPreferSize().getWidth();
				WidgetDto labelDto = newLabelBox(key, label, labelWidth,"", mapRequire.get(key));
				fieldLabelWidgets.add(labelDto);
			}
		}
		return fieldLabelWidgets;
	}
	/**
	 * 
	 * 控制属性是否必填，只在界面渲染完成后调用有效
	 * @param panelContext
	 * @param mapRequire	属性必填配置	key：属性code，value：布尔值，true：必填，false：不必填
	 * @throws Exception
	 */
	public void setFieldRequire(PanelContext panelContext,Map<String,Boolean> mapRequire)throws Exception{
		if(!mapRequire.isEmpty()) {
			widgetParam.mergeRequireOverride(mapRequire);
			List<WidgetDto> fieldLabelWidgets = buildFieldLabelWidgets(panelContext, mapRequire);
			//重建标签样式
			if(!fieldLabelWidgets.isEmpty())
				RebuildChild.rebuild(panelContext, fieldLabelWidgets);
			SetBinaryData.set(panelContext, panelContext.getCurrentPanelWidgetId(), widgetParam);
		}
	}
	/**
	 * 
	 * 构建控制属性必填的前端回调，只在界面渲染完成后调用有效，用于批量向前端发送回调，减少通信次数
	 * @param panelContext
	 * @param mapRequire	属性必填配置	key：属性code，value：布尔值，true：必填，false：不必填
	 * @throws Exception
	 */
	public List<CsonPojo> buildSetFieldRequireCallback(PanelContext panelContext,Map<String,Boolean> mapRequire) throws Exception{
		List<CsonPojo> callbacks = new ArrayList<>();
		if(!mapRequire.isEmpty()) {
			widgetParam.mergeRequireOverride(mapRequire);
			List<WidgetDto> fieldLabelWidgets = buildFieldLabelWidgets(panelContext, mapRequire);
			//重建标签样式
			if(!fieldLabelWidgets.isEmpty()) {
				RebuildChild rebuildFieldLabelCallback = new RebuildChild();
				rebuildFieldLabelCallback.setPanelGlobalKey(panelContext.getCurrentPanelGlobalKey());
				rebuildFieldLabelCallback.setNewWidgets(fieldLabelWidgets);
				callbacks.add(rebuildFieldLabelCallback);
			}
			SetBinaryData setBinaryData = new SetBinaryData();
			setBinaryData.setPanelGlobalKey(panelContext.getCurrentPanelGlobalKey());
			setBinaryData.setWidgetId(panelContext.getCurrentPanelWidgetId()).setBinPojo((BinPojo)(new BinPojo()).setBinaryData(widgetParam));
			callbacks.add(setBinaryData);
		}
		return callbacks;
	}
	private List<WidgetDto> buildFieldWidgets(PanelContext panelContext,List<String> fieldCodes)throws Exception{
		List<FormFieldDefine> fieldDefines = getFormFieldDefines(panelContext);
		Map<String,FormFieldDefine> fieldDefMap = new HashMap<>();
		for(FormFieldDefine fieldDef : fieldDefines) {
			fieldDefMap.put(fieldDef.getCode(), fieldDef);
		}
		R data = widgetParam.getData();
		data = ToolUtilities.clone(data);
		PanelValue panelValue = queryPanelValue(panelContext);
		writePanelValue(panelContext, data, panelValue);
		new FormVisitor().visit(data);
		FormFieldEditorFactory factory = getEditorFactory(panelContext.getCurrentPanelGlobalKey(), widgetParam);
		List<WidgetDto> newWidgets = new ArrayList<>();
		Tracer tracer = TraceUtil.getCurrentTracer();
		for(String fieldCode : fieldCodes) {
			FormFieldDefine fieldDef = fieldDefMap.get(fieldCode);
			if(fieldDef == null)
				continue;
			if(FeDebugUtil.isEnableDebug(panelContext)) {
				tracer.debug("重建属性编辑器：" + fieldDef.getName()+",是否可写："+isFieldWritable(panelContext, fieldDef));
			}
			Object value = data.getAttrValueByCode(fieldCode);
			
			WidgetDto editor = buildEditor(panelContext, factory, data, fieldDef, value);
			setListenerByListenerDefine(panelContext, editor, widgetParam.getListenerDefines(), ListenerApplyLocation.Form);
			newWidgets.add(editor);
		}
		return newWidgets;
	}
	/**
	 * 重建指定属性的组件
	 * @param panelContext
	 * @param fieldCodes
	 * @throws Exception
	 */
	public void rebuildFieldWidgets(PanelContext panelContext,List<String> fieldCodes)throws Exception{
		List<WidgetDto> newWidgets = buildFieldWidgets(panelContext, fieldCodes);
		if(!newWidgets.isEmpty()) {
			RebuildChild.rebuild(panelContext, newWidgets);
		}
	}
	
	public List<WidgetDto> buildFieldEditorBoxes(PanelContext panelContext,List<String> fieldCodes)throws Exception{
		List<FormFieldDefine> fieldDefines = getFormFieldDefines(panelContext);
		Map<String,FormFieldDefine> fieldDefMap = new HashMap<>();
		for(FormFieldDefine fieldDef : fieldDefines) {
			fieldDefMap.put(fieldDef.getCode(), fieldDef);
		}
		R data = widgetParam.getData();
		FormFieldEditorFactory factory = getEditorFactory(panelContext.getCurrentPanelGlobalKey(), widgetParam);
		List<WidgetDto> newWidgets = new ArrayList<>();
		FormViewSetting setting = widgetParam.getSetting(FormViewSetting.class);
		for(String fieldCode : fieldCodes) {
			FormFieldDefine fieldDef = fieldDefMap.get(fieldCode);
			if(fieldDef == null)
				continue;
			Object value = data.getAttrValueByCode(fieldDef.getCode());
			WidgetDto editor = buildEditor(panelContext, factory, data, fieldDef, value);
			if(editor == null) {
				continue;
			}
			setListenerByListenerDefine(panelContext, editor, widgetParam.getListenerDefines(), ListenerApplyLocation.Form);
			BoxDto editorBox = buildFieldEditorBox(panelContext, fieldDef, setting, data, editor);
			newWidgets.add(editorBox);
		}
		return newWidgets;
	}
	
	public void rebuildFieldEditorBoxes(PanelContext panelContext,List<String> fieldCodes)throws Exception{
		List<WidgetDto> newWidgets = buildFieldEditorBoxes(panelContext, fieldCodes);
		if(!newWidgets.isEmpty()) {
			RebuildChild.rebuild(panelContext, newWidgets);
		}
	}
	
	public List<WidgetDto> buildFieldLabelBoxes(PanelContext panelContext,List<String> fieldCodes)throws Exception{
		List<FormFieldDefine> fieldDefines = getFormFieldDefines(panelContext);
		Map<String,FormFieldDefine> fieldDefMap = new HashMap<>();
		for(FormFieldDefine fieldDef : fieldDefines) {
			fieldDefMap.put(fieldDef.getCode(), fieldDef);
		}
		R data = widgetParam.getData();
		List<WidgetDto> newWidgets = new ArrayList<>();
		FormViewSetting setting = widgetParam.getSetting(FormViewSetting.class);
		for(String fieldCode : fieldCodes) {
			FormFieldDefine fieldDef = fieldDefMap.get(fieldCode);
			if(fieldDef == null)
				continue;
			WidgetDto editor = new EmptySlotDto().setWidgetId(fieldCode);
			BoxDto editorBox = buildFieldEditorBox(panelContext, fieldDef, setting, data, editor);
			BoxDto labelBox = (BoxDto) FeUtil.searchWidget(editorBox, v->{
				return v.getWidgetId().equals(getLabelBoxWidgetId(fieldCode));
			});
			newWidgets.add(labelBox);
		}
		return newWidgets;
	}
	
	public void rebuildFieldLabelBoxes(PanelContext panelContext,List<String> fieldCodes)throws Exception{
		List<WidgetDto> newWidgets = buildFieldLabelBoxes(panelContext, fieldCodes);
		if(!newWidgets.isEmpty()) {
			RebuildChild.rebuild(panelContext, newWidgets);
		}
	}
	
	public void onRefresh(PanelContext panelContext) throws Exception {
		asyncShowLoading(panelContext, getLoadingMaskConfig(panelContext));
		try {
			PanelDto widget = (PanelDto) getWidget(panelContext);
			WidgetDto orgWidget = QueryWidget.queryOne(panelContext, panelContext.getCurrentPanelWidgetId());
			widget.setExtendListeners(orgWidget.getExtendListeners());
			hideLoading(panelContext, panelContext.getCurrentPanelWidgetId());
			RebuildChild.rebuild(panelContext, widget);
		}catch (Exception e) {
			throw e;
		}finally {
			asyncHideLoading(panelContext, panelContext.getCurrentPanelWidgetId());
		}
	}
	
	public R fireDeleteCommand(PanelContext context) throws Exception {
		fireCommandListener(context, context.getCurrentPanelWidgetId(), CMD_DELETE, null);
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void onValueChanged(ListenerDto listener, PanelContext panelContext, WidgetDto source) throws Exception {
	
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void onBlur(ListenerDto listener, PanelContext panelContext, WidgetDto source)
			throws Exception {
	}
	
	@SuppressWarnings({ "rawtypes" })
	public void setOnValueChanged(PanelContext panelContext,WidgetDto editor) throws Exception {
		if (editor instanceof EditorDto) {
			((EditorDto) editor).setOnValueChanged(newOnValueChanged(getService(), CMD_ON_VALUE_CHANGED, true, null).setChangeInterval(1500));
		} else if (editor instanceof TableDto) {
			ListenerDto lsnr = newListener(getService(), CMD_ON_VALUE_CHANGED, true, null);
			CommandCallbackListener extLsnr = newCommandCallback(NestingFormTableView.CMD_VALUE_CHANGED,"值改变监听", "", lsnr, false);
//			editor.addExtendListener(extLsnr);
			FeListenerUtil.setWidgetCommandCallbackListener(panelContext,editor, extLsnr);
		}
	}

	@Override
	public EditorValueHandlerFactory getEditorValueHandlerFactory() {
		return FormFieldEditorValueHandlerFactory.create();
	}
	
	public LoadingMaskConfigDto getLoadingMaskConfig(PanelContext panelContext) throws Exception {
		LoadingMaskConfigDto config = AppCacheUtil.getDefaultLoadingMaskConfig(panelContext);
		return config;
	}
	@Override
	public List<FeEventSubscribeDto> getEventSubscribes() throws Exception {
		return widgetParam.getEventSubscribes();
	}
}