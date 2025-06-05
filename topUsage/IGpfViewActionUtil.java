package cell.fe.gpf.dc.basic.view.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kwaidoo.ms.tool.ToolUtilities;

import bap.cells.Cells;
import cell.CellIntf;
import cell.cdao.IDao;
import cell.gpf.dc.config.IPDFMgr;
import cell.gpf.dc.runtime.IDCRuntimeContext;
import cell.gpf.dc.runtime.IPDFRuntimeMgr;
import fe.cmn.app.PopupRouteSettingsDto;
import fe.cmn.panel.PanelContext;
import fe.cmn.panel.PanelDto;
import fe.cmn.widget.ListenerDto;
import fe.util.component.AbsComponent;
import fe.util.component.Component;
import fe.util.component.extlistener.CommandCallbackListener;
import gpf.adur.action.Action;
import gpf.adur.data.Form;
import gpf.adur.data.FormField;
import gpf.dc.basic.fe.util.GpfViewActionUtil;
import gpf.dc.basic.param.view.dto.FormFieldDefine;
import gpf.dc.concrete.DCAction;
import gpf.dc.concrete.RefFormField;
import gpf.dc.config.PDC;
import gpf.dc.config.PDF;
import gpf.dc.config.RefPDCNode;
import gpf.dc.runtime.PDCForm;
import gpf.enums.NodeTriggerTime;
/**
 * 视图动作工具类
 * @author chenxb
 *
 */
public interface IGpfViewActionUtil extends CellIntf{

	public static IGpfViewActionUtil get() {
		return Cells.get(IGpfViewActionUtil.class);
	}
	/**
	 * 根据路径获取面板组件
	 * 此方法需要多次与前端交互获取父面板和子面板信息，耗时较长，需要谨慎使用
	 * @param panelContext	触发查询操作的面板上下文
	 * @param path	组件相对路径 ..表示上一层，属性编号表示当前层级下的指定属性的面板，表格、表单、列表、弹窗为每一层级的面板，例如
	 * 				A表单面版下的B表格面板，触发弹窗显示了C表单面板下，C面板下有一个D表格面板，通过D面板的按钮触发查询A表格下的E表格面板，路径表示为：
	 * 				../../../E	: 第一个.. 取到 C面板，第二个.. 取到B面板，第三个..取到A面板，最后的E取到E面板
	 * 				更多举例如： .. 上一层级面板
	 * 				   ../.. 上上层级面板
	 * 				   ../../xxx 上上层级面板下的xxx面板
	 * 					xxx	当前层级面板下的xxx面板
	 * @return
	 * @throws Exception
	 */
	default AbsComponent getComponentByPath(PanelContext panelContext,String path)throws Exception{
		return GpfViewActionUtil.getComponentByPath(panelContext, path);
	}
	/**
	 * 筹备界面动作运行的上下文对象
	 * @param dao	DAO对象
	 * @param panelContext	面板上下文
	 * @param listener	监听器
	 * @param currComponent	当前执行动作的界面实例，没有可传null
	 * @return
	 * @throws Exception
	 */
	default IDCRuntimeContext prepareRtx(IDao dao,PanelContext panelContext,ListenerDto listener,Component currComponent) throws Exception {
		return GpfViewActionUtil.prepareRtx(dao, panelContext, listener, currComponent);
	}
	/**
	 * 构建一个用于布局器布局的PDC表单
	 * @param pdfUuid
	 * @return
	 * @throws Exception
	 */
	default PDCForm newLayoutPDCForm(String pdfUuid) throws Exception {
		PDCForm pdcForm = new PDCForm();
		List<FormField> fields = IPDFRuntimeMgr.get().queryPDFFormFields(pdfUuid);
		Map<String,FormField> allFieldMap = new LinkedHashMap<>();
		for(FormField field : fields) {
			allFieldMap.put(field.getCode(), field);
		}
		Map<String,FormField> fieldMap = new LinkedHashMap<>();
		Set<String> actions = new LinkedHashSet<>();
		PDF pdf = IPDFMgr.get().queryPDF(pdfUuid);
		for(RefPDCNode node : pdf.getNodes()) {
			PDC pdc = node.getData();
			for(RefFormField field : pdc.getRefFieldList()) {
				FormField formField = allFieldMap.get(field.getCode());
				if(formField != null) {
					fieldMap.put(field.getCode(), formField);
				}
			}
			for(DCAction action : pdc.getActionList()) {
				if(action.getExcuteTiming().equals(NodeTriggerTime.ExternalInput.name())) {
					actions.add(action.getName());
				}
			}
		}
		pdcForm.setMeta(new ArrayList<>(fieldMap.values()));
		pdcForm.setActions(new ArrayList<>(actions));
		return pdcForm;
	}
	
	/**
	 * 构建表单弹窗的路由参数配置
	 * @param panelContext
	 * @param title
	 * @param viewAction
	 * @param form
	 * @return
	 * @throws Exception
	 */
	default PopupRouteSettingsDto buildFormPopRouterSetting(PanelContext panelContext,String title,Action viewAction,Form form) throws Exception {
		return GpfViewActionUtil.buildFormPopRouterSetting(panelContext, title, null, viewAction, form);
	}
	default PopupRouteSettingsDto buildFormPopRouterSetting(PanelContext panelContext,String title,String imageUrl,Action viewAction,Form form) throws Exception {
		return GpfViewActionUtil.buildFormPopRouterSetting(panelContext, title, imageUrl, viewAction, form);
	}
	default PopupRouteSettingsDto buildPopRouterSetting(PanelContext panelContext,String title,Action viewAction,Map<String,Object> actionParams) throws Exception {
		return GpfViewActionUtil.buildPopRouterSetting(panelContext, title, null, viewAction, actionParams);
	}
	default PopupRouteSettingsDto buildPopRouterSetting(PanelContext panelContext,String title,String imageUrl,Action viewAction,Map<String,Object> actionParams) throws Exception {
		return GpfViewActionUtil.buildPopRouterSetting(panelContext, title, imageUrl, viewAction, actionParams);
	}
	
	default PanelDto newFormView(ListenerDto listener,PanelContext panelContext,Component currComponent,Form form,boolean isAdd,boolean isWritable,Action viewAction,List<CommandCallbackListener> callbackLsnrs) throws Exception {
		return GpfViewActionUtil.newFormView(listener, panelContext, currComponent, form, isAdd, isWritable, viewAction, callbackLsnrs);
	}
	
	/**
	 * 弹窗显示表单面板
	 * @param listener	监听器
	 * @param panelContext	面板上下文
	 * @param title	标题
	 * @param form	表单数据
	 * @param isAdd	是否新增表单
	 * @param viewAction	视图动作实例
	 * @param callbackLsnrs	表单面板的回调监听
	 * @throws Exception
	 */
	default void popupFormView(ListenerDto listener,PanelContext panelContext,Component currComponent,String title,Form form,boolean isAdd,boolean isWritable,Action viewAction,List<CommandCallbackListener> callbackLsnrs) throws Exception {
		GpfViewActionUtil.popupFormView(listener, panelContext, currComponent, title, form, isAdd, isWritable, viewAction, callbackLsnrs);
	}
	
	/**
	 * 弹出编辑面板，适用于表单视图的弹窗显示
	 */
	default void popupEditPanel(PanelContext panelContext,String title,PanelDto panel,PopupRouteSettingsDto routeSetting) throws Exception {
		GpfViewActionUtil.popupEditPanel(panelContext, title, panel, routeSetting);
	}
	/**
	 * 根据配置计算弹窗的标题
	 * @return
	 * @throws Exception 
	 */
	default String caculateTitle(PanelContext context,List<FormFieldDefine> fieldDefines,Form form,String titleExpression) throws Exception {
		return GpfViewActionUtil.caculateTitle(context, fieldDefines, form, titleExpression);
	}
	
	public static void main(String[] args) throws Exception {
		String titleExpression = "'测试'-#saa###";
		Map<String,String> regexMap = new LinkedHashMap<>();
		regexMap.put("#saa#", "edad");
		String value = ToolUtilities.replaceAll(titleExpression, regexMap);
		System.out.println(value);
	}
	
}
