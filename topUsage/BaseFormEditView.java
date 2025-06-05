package gpf.dc.basic.fe.component.view;

import java.util.List;
import java.util.Map;

import com.leavay.ms.tool.CmnUtil;

import cell.cdao.IDao;
import cell.cdao.IDaoService;
import cell.gpf.adur.action.IActionMgr;
import cell.gpf.dc.runtime.IDCRuntimeContext;
import cmn.anotation.ClassDeclare;
import cmn.anotation.FieldDeclare;
import cmn.anotation.I18nDeclare;
import cmn.dto.Progress;
import cmn.util.ProgressUtil;
import cmn.util.TraceUtil;
import cmn.util.Tracer;
import fe.cmn.panel.BoxDto;
import fe.cmn.panel.PanelContext;
import fe.cmn.panel.PanelValue;
import fe.cmn.panel.PlaceholderDto;
import fe.cmn.panel.SinglePanelDto;
import fe.cmn.panel.ability.QueryPanelValue;
import fe.cmn.panel.ability.QuitPopup;
import fe.cmn.widget.ButtonDto;
import fe.cmn.widget.ListenerDto;
import fe.cmn.widget.WidgetDto;
import fe.util.component.dto.FeDeliverData;
import fe.util.component.extlistener.CommandListener;
import fe.util.editor.valuehanlder.EditorFieldDefine;
import fe.util.style.FeStyleConst;
import gpf.adur.action.Action;
import gpf.adur.data.Form;
import gpf.dc.basic.dto.view.SubmitButtonHookConfigDto;
import gpf.dc.basic.fe.component.fieldextend.editor.WidgetLayoutUtil;
import gpf.dc.basic.fe.component.param.BaseDataViewParam;
import gpf.dc.basic.fe.enums.ListenerApplyLocation;
import gpf.dc.basic.i18n.GpfDCBasicI18n;
import gpf.dc.basic.param.view.dto.ButtonDefine;
import gpf.dc.basic.param.view.dto.FormViewSetting;
import gpf.dc.concrete.RefActionConfig;
import gpf.dc.fe.component.adur.data.field.handler.FormVisitor;
import gpf.dc.runtime.PDCForm;
import web.dto.Pair;
@ClassDeclare(label = "表单面板"
,what=""
, why = ""
, how = ""
,developer="陈晓斌"
,version = "1.0"
,createTime = "2024-11-22"
,updateTime = "2024-11-22")
@I18nDeclare
public class BaseFormEditView<T extends BaseDataViewParam<R>,R extends Form> extends BaseFormView<T,R>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3489965601186054663L;
	
	@FieldDeclare(label = "确认", desc = "执行确认操作的命令")
	public static final String CMD_CONFIRM = "CMD_CONFIRM";

	@FieldDeclare(label = "取消", desc = "执行取消操作的命令")
	public static final String CMD_CANCEL = "CMD_CANCEL";
	
	@FieldDeclare(label = "按钮确认", desc = "点击按钮执行确认操作的命令")
	public static final String CMD_BUTTON_CONFIRM = "CMD_BUTTON_CONFIRM";

	@FieldDeclare(label = "按钮取消", desc = "点击按钮执行取消操作的命令")
	public static final String CMD_BUTTON_CANCEL = "CMD_BUTTON_CANCEL";
	@Override
	public WidgetDto doGetWidget(PanelContext panelContext) throws Exception {
		SinglePanelDto panel = (SinglePanelDto) super.doGetWidget(panelContext);
		insertModelSelector(panelContext, panel);
		ListenerDto lsnr = newListener(getService(), CMD_CONFIRM, true, null);
		CommandListener refreshLsnr = new CommandListener(CMD_CONFIRM, "提交表单", "", lsnr, true);
		panel.addExtendListener(refreshLsnr);
		ListenerDto lsnr2 = newListener(getService(), CMD_CANCEL, true, null);
		CommandListener cancelLsnr = new CommandListener(CMD_CANCEL, "取消表单", "", lsnr2, true);
		panel.addExtendListener(cancelLsnr);
		if(!CmnUtil.isStringEmpty(widgetParam.getWidgetId())) {
			panel.setWidgetId(widgetParam.getWidgetId());
		}
		setListenerByListenerDefine(panelContext,panel, widgetParam.getListenerDefines(),ListenerApplyLocation.Form);
		return panel;
	}

	@Override
	public WidgetDto getBottomBar(PanelContext panelContext) throws Exception {
		if(widgetParam.isEmbedForm())
			return null;
		
		BoxDto bottomBar = new BoxDto().setVertical(false).setExpandInBox(false);
		if(widgetParam.isWritable()) {
			ButtonDto cancelBtn = null;
			ButtonDefine cancelBtnDef = getButtonDefineByCommand(CMD_CANCEL);
			if(cancelBtnDef != null) {
				cancelBtn = buildButton(panelContext,cancelBtnDef);
				cancelBtn.getOnClick().setServerExecutor(getService(), CMD_BUTTON_CANCEL);
			}else {
				cancelBtn = buildButton(getI18nString(panelContext,GpfDCBasicI18n.Cancel), null, CMD_BUTTON_CANCEL, null);
			}
			setButtonPrivilege(cancelBtn,panelContext);
			cancelBtn.setWidgetId(CMD_CANCEL);
			cancelBtn.setUnitName(WidgetLayoutUtil.UNIT_NAME_CUSTOM_BUTTON);
			bottomBar.addChild(cancelBtn);
			
			ButtonDefine confirmBtnDef = getButtonDefineByCommand(CMD_CONFIRM);
			ButtonDto confirmBtn = null; 
			if(confirmBtnDef != null) {
				confirmBtn = buildButton(panelContext,confirmBtnDef);
				confirmBtn.getOnClick().setServerExecutor(getService(), CMD_BUTTON_CONFIRM);
			}else {
				confirmBtn = buildButton(getI18nString(panelContext,GpfDCBasicI18n.Confirm), null, CMD_BUTTON_CONFIRM, null);
			}
			setButtonPrivilege(confirmBtn,panelContext);
			//FIXME 设置组件ID是因为要兼容旧的配置，原先用了CMD_CONFIRM 作为按钮的指令，现在换了之后布局器上已有的组件还得处理
			confirmBtn.setWidgetId(CMD_CONFIRM);
			confirmBtn.setConfirmStyle();
			confirmBtn.setUnitName(WidgetLayoutUtil.UNIT_NAME_CUSTOM_BUTTON);
			bottomBar.addChild(confirmBtn);
		}
		bottomBar.addChild(new PlaceholderDto());
		bottomBar.setStyleName(FeStyleConst.common_form_bottom_bar_right);
		FormViewSetting setting = widgetParam.getSetting(FormViewSetting.class);
		
		String bottomBarAlign = null;
		if(!CmnUtil.isStringEmpty(setting.getFormActionsPosition())) {
			bottomBarAlign = setting.getFormActionsPosition();
		}
		if(CmnUtil.isStringEqual(bottomBarAlign, "left")) {
			bottomBar.setStyleName(FeStyleConst.common_form_bottom_bar_left);
		}else if(CmnUtil.isStringEqual(bottomBarAlign, "center")) {
			bottomBar.setStyleName(FeStyleConst.common_form_bottom_bar_center);
		}else if(CmnUtil.isStringEqual(bottomBarAlign, "right")) {
			bottomBar.setStyleName(FeStyleConst.common_form_bottom_bar_right);
		}else {
			bottomBar.setStyleName(getFeStyleSetting(panelContext).getDefaultFormBottomBarStyle());
		}
		bottomBar.setUnitName(WidgetLayoutUtil.UNIT_NAME_CUSTOM_BOX);
		return bottomBar;
	}
	
	@SuppressWarnings({ "rawtypes" })
	@Override
	public Object onListener(ListenerDto listener, PanelContext panelContext, WidgetDto source) throws Exception {
		Pair<Boolean, Object> resultPair = findListenerDefineAndRun(panelContext, listener);
		if(resultPair.left)
			return resultPair;
		if(listener.isServiceCommand(CMD_CONFIRM) || listener.isServiceCommand(CMD_CANCEL)){
			FeDeliverData<PDCForm> feData = (FeDeliverData<PDCForm>) listener.getBinaryData();
			return feData.getData();
		} else if(listener.isServiceCommand(CMD_BUTTON_CONFIRM)) {
			return onConfirm(panelContext);
		}else if(listener.isServiceCommand(CMD_BUTTON_CANCEL)){
			return onCancle(panelContext);
		}else if (listener.isServiceCommand(CMD_ON_VALUE_CHANGED)) {
			onValueChanged(listener, panelContext, source);
		}else if(listener.isServiceCommand(CMD_QUIT_POPUP)){
			QuitPopup.quit(panelContext);
		}else  if(listener.isServiceCommand(CMD_ON_BLUR)){
			onBlur(listener, panelContext, source);
		}else if(listener.isServiceCommand(CMD_REFRESH)) {
			onRefresh(panelContext);
		}else {
			return super.onListener(listener, panelContext, source);
		}
		return null;
	}
	
	public R onConfirm(PanelContext context) throws Exception {
		Tracer tracer = TraceUtil.getCurrentTracer();
		tracer.logStart();
		R data = widgetParam.getData();
		PanelValue panelValue = QueryPanelValue.query(context);
		tracer.debugCost("", "QueryPanelValue");
		tracer.logStart();
		writePanelValue(context, data, panelValue);
		new FormVisitor().visit(data);
		tracer.debugCost("", "writePanelValue");
		tracer.logStart();
		List<EditorFieldDefine> fieldDefs = getEditorFieldDefine(context, data, panelValue);
		verifyRequireFields(context, fieldDefs, panelValue);
		tracer.debugCost("", "verifyRequireFields");
		Map<String, SubmitButtonHookConfigDto> submitButtonHookConfigMap = widgetParam.getSubmitButtonHookConfigMap();
		Map<String,RefActionConfig> actionDefMap = widgetParam.getActionDefineMap();
		SubmitButtonHookConfigDto submitButtonHookConfig = submitButtonHookConfigMap.get(CMD_CONFIRM);
		Progress prog = newSubmitProgress(context, submitButtonHookConfig);
		IDCRuntimeContext rtx = null;
		boolean success = false;
		try(IDao dao = IDaoService.newIDao()) {
			if(submitButtonHookConfig != null) {
				rtx = getOrPrepareRtx(context, context.getCurrentPanelGlobalKey());
				rtx.setProgress(prog);
				rtx.setDao(dao);
				rtx.setInteractiveForm(data);
				List<String> actionList = submitButtonHookConfig.getActionListBeforeSubmit();
				for(String action : actionList) {
					RefActionConfig refAction = actionDefMap.get(action);
					if(refAction != null) {
						tracer.info("执行提交前动作");
						tracer.logStart();
						Action actionInst = refAction.getAction();
						IActionMgr.get().executeAction(actionInst, rtx);
						tracer.infoCost("", "提交前动作执行结束");
					}
				}
			}
			if(submitButtonHookConfig != null) {
				List<String> actionList = submitButtonHookConfig.getActionListAfterSubmit();
				for(String action : actionList) {
					RefActionConfig refAction = actionDefMap.get(action);
					if(refAction != null) {
						tracer.info("执行提交后动作");
						tracer.logStart();
						Action actionInst = refAction.getAction();
						IActionMgr.get().executeAction(actionInst, rtx);
						tracer.infoCost("", "提交后动作执行结束");
					}
				}
			}
			ProgressUtil.finish(prog);
			success = true;
		}catch (Exception e) {
			if(prog != null)
				prog.sendDataFrame(e);
			else
				throw e;
		}finally {
			ProgressUtil.sendStopProcess(prog);
		}
		if(success) {
			fireCommandListener(context, context.getCurrentPanelWidgetId(), CMD_CONFIRM, data);
			return data;
		}else {
			return null;
		}
	}
	
	public R onCancle(PanelContext context) throws Exception {
		fireCommandListener(context, context.getCurrentPanelWidgetId(), CMD_CANCEL, null);
		return null;
	}
	

}
