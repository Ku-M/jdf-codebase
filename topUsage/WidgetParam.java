package fe.util.component.param;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kwaidoo.ms.tool.ToolUtilities;
import com.leavay.ms.tool.CmnUtil;

import fe.cmn.data.FePojo;
import fe.util.OperateTransaction;
import fe.util.component.extlistener.CommandCallbackListener;

public abstract class WidgetParam extends FePojo implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 8236838913307912051L;
	//在页面内部传递的参数，不建议将页面间传递的参数设置在此处，而应该继承后进行强类型声明
	Map<String,Object> context = new HashMap<>();

	Map<String,CallBackSetting> callBackMap = new HashMap<>();
	/**
	 * 指令监听回调，传入回调监听器时，当前组件触发指定指令执行结束时，将触发回调监听器执行
	 */
	List<CommandCallbackListener> commandCallbackLsnrs = new ArrayList<>();
	/**
	 * 反射调用的界面类
	 */
	String invokeClass;
	/**
	 * 该组件的 widgetId,由父面板或组件指定后往下层携带
	 */
	String widgetId;
	//组件当前的panel全局标识，当需要在组件调用时传递面板信息时传递此参数
	String panelGlobalKey;
	//组件的源panel的全局标识，当在当前组件需要拿到来源面板信息时设置次参数
	String srcPanelGlobalKey;
	
	/**
	 * 用户操作事务标识
	 */
	String opTransId = OperateTransaction.getTransId();
	
	String lastOpTransId = OperateTransaction.getLastTransId();

	public Map<String, Object> getContext() {
		return context;
	}

	public WidgetParam setContext(Map<String, Object> context) {
		this.context = context;
		return this;
	}

	public Map<String, CallBackSetting> getCallBackMap() {
		return callBackMap;
	}

	public WidgetParam setCallBackMap(Map<String, CallBackSetting> callBackMap) {
		this.callBackMap = callBackMap;
		return this;
	}

	public String getInvokeClass() {
		return invokeClass;
	}

	public WidgetParam setInvokeClass(String invokeClass) {
		this.invokeClass = invokeClass;
		return this;
	}

	public String getWidgetId() {
		return widgetId;
	}

	public WidgetParam setWidgetId(String widgetId) {
		this.widgetId = widgetId;
		return this;
	}
	
	public String getPanelGlobalKey() {
		return panelGlobalKey;
	}
	
	public void setPanelGlobalKey(String panelGlobalKey) {
		this.panelGlobalKey = panelGlobalKey;
	}
	
	public String getSrcPanelGlobalKey() {
		return srcPanelGlobalKey;
	}
	
	public void setSrcPanelGlobalKey(String srcPanelGlobalKey) {
		this.srcPanelGlobalKey = srcPanelGlobalKey;
	}
	
	public List<CommandCallbackListener> getCommandCallbackLsnrs() {
		return commandCallbackLsnrs;
	}
	public WidgetParam setCommandCallbackLsnrs(List<CommandCallbackListener> commandCallbackLsnrs) {
		this.commandCallbackLsnrs = commandCallbackLsnrs;
		return this;
	}
	
	public void addCommandCallbackLsnr(CommandCallbackListener commandCallbackLsnr) {
		if(commandCallbackLsnrs == null)
			commandCallbackLsnrs = new ArrayList<>();
		if(!(this.commandCallbackLsnrs instanceof ArrayList)) {
			//FIXME 这里因为NullUtil.get()的问题导致污染了空集合，所以做个判断，如果不是ArrayList重新构建一下
			List<CommandCallbackListener> newList = new ArrayList<>();
			if(this.commandCallbackLsnrs != null) {
				newList.addAll(commandCallbackLsnrs);
				commandCallbackLsnrs= newList;
			}
		}
		int existIdx = -1;
		for(int i =0;i<commandCallbackLsnrs.size();i++) {
			if(CmnUtil.isStringEqual(commandCallbackLsnrs.get(i).getName(), commandCallbackLsnr.getName())) {
				existIdx = i;
				break;
			}
		}
		if(existIdx == -1)
			commandCallbackLsnrs.add(commandCallbackLsnr);
		else
			commandCallbackLsnrs.set(existIdx, commandCallbackLsnr);
	}
	
	public CommandCallbackListener searchCommandCallbackLsnr(String command) {
		if(CmnUtil.isCollectionEmpty(commandCallbackLsnrs))
			return null;
		for(CommandCallbackListener lsnr : commandCallbackLsnrs) {
			if(CmnUtil.isStringEqual(lsnr.getName(), command)) {
				return lsnr;
			}
		}
		return null;
	}
	
	public String getOpTransId() {
		return opTransId;
	}
	public String getLastOpTransId() {
		return lastOpTransId;
	}
	
	public void allockWidgetIdIfNull() {
		if(CmnUtil.isStringEmpty(widgetId)) {
			widgetId = ToolUtilities.allockUUIDWithUnderline();
		}
	}
	
	public void allockPanelGlobalKeyIfNull() {
		if(CmnUtil.isStringEmpty(panelGlobalKey)) {
			panelGlobalKey = ToolUtilities.allockUUIDWithUnderline();
		}
	}
}
