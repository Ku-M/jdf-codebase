package pcr.basic.util;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.googlecode.aviator.AviatorEvaluator;
import com.kwaidoo.ms.tool.CmnUtil;

import cell.cdao.IDao;
import cell.cdao.IDaoService;
import cell.jit.IWorkSpaceService;
import fe.cmn.app.Context;
import fe.cmn.app.ability.ReadLocalStorage;
import fe.cmn.app.ability.ReadSessionStorage;
import fe.cmn.app.ability.RemoveLocalStorage;
import fe.cmn.app.ability.WriteLocalStorage;
import fe.cmn.app.ability.WriteSessionStorage;
import fe.cmn.data.PairDto;
import gpf.adur.data.Form;
import gpf.adur.user.User;
import gpf.dc.basic.fe.component.app.AppCacheUtil;
import gpf.dc.basic.param.view.dto.ApplicationSetting;
import jit.dto.WorkSpace;
import jit.observer.JITDefaultFilter;
import jit.observer.JITFormOpObserver;
/**
 * JIT应用上下文
 * @param panelContext
 * @return
 */
public class JITAppContext implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6669064850836719113L;
	public final static String Key_CurrentWorkSpace = "currentWorkSpace";
	public final static String Key_LastEnterWorkSpace ="LastEnterWorkSpace";

	/**
	 * 获取当前工作空间
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static WorkSpace getCurrentWorkSpace(Context context) throws Exception {
		WorkSpace workspace = (WorkSpace) AppCacheUtil.getAppContext(context).get(Key_CurrentWorkSpace);
		if(workspace == null) {
			String workspaceCode = (String) ReadSessionStorage.read(context.getChannel(), Key_CurrentWorkSpace);
			if(!CmnUtil.isStringEmpty(workspaceCode)) {
				try(IDao dao = IDaoService.newIDao()){
					Form form = IWorkSpaceService.get().queryByCode(dao, workspaceCode);
					if(form != null) {
						workspace = IWorkSpaceService.get().convert2WorkSapce(form);
						setCurrentWorkSpace(context, workspace);
					}
				}
			}
		}
		return workspace;
	}
	/**
	 * 设置当前工作空间
	 * @param context
	 * @param workspace
	 * @throws Exception
	 */
	public static void setCurrentWorkSpace(Context context,WorkSpace workspace) throws Exception {
		AppCacheUtil.getAppContext(context).put(Key_CurrentWorkSpace, workspace);
		if(workspace != null) {
			WriteSessionStorage.write(context.getChannel(), Key_CurrentWorkSpace, workspace.getCode());
			User user = AppCacheUtil.getCurrentUser(context);
			ApplicationSetting setting = AppCacheUtil.getSetting(context);
			JITFormOpObserver observer = new JITFormOpObserver(user,setting.getOrgModelId(),workspace);
			AppCacheUtil.setFormOpObserver(context, observer);
			JITDefaultFilter defaultFilter = new JITDefaultFilter(workspace);
			AppCacheUtil.setAppDefaultFilter(context, defaultFilter);
		}else {
			WriteSessionStorage.write(context.getChannel(), Key_CurrentWorkSpace, "");
			saveLastEnterWorkSpace(context, null);
			AppCacheUtil.setFormOpObserver(context, null);
		}
	}
	/**
	 * 保存最后一次进度的工作空间
	 * @param context
	 * @param workSpace
	 * @throws Exception
	 */
	public static void saveLastEnterWorkSpace(Context context,WorkSpace workSpace) throws Exception {
		String appCode = AppCacheUtil.getAppCode(context);
		if(workSpace == null) {
			RemoveLocalStorage.remove(context.getChannel(), appCode, Key_LastEnterWorkSpace);
		}else {
			WriteLocalStorage.write(context.getChannel(), appCode, new PairDto<String, Object>(Key_LastEnterWorkSpace,workSpace.getCode()));
		}
	}
	/**
	 * 获取最后一次进入的工作空间
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static String getLastEnterWorkSpace(Context context) throws Exception {
		String appCode = AppCacheUtil.getAppCode(context);
 		Object value = ReadLocalStorage.read(context.getChannel(), appCode, Key_LastEnterWorkSpace);
		if(CmnUtil.isObjectEmpty(value))
			return null;
		String code = (String) value;
		return code;
	}
	/**
	 * 获取工作区间内的表单数据操作的观察者对象，用于干预工作区间内数据的CUD联动处理
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static JITFormOpObserver getFormOpObserver(Context context) throws Exception {
		JITFormOpObserver observer = (JITFormOpObserver) AppCacheUtil.getFormOpObserver(context);
		if(observer == null) {
			WorkSpace workspace = getCurrentWorkSpace(context);
			if(workspace != null) {
				setCurrentWorkSpace(context, workspace);
				observer = (JITFormOpObserver) AppCacheUtil.getFormOpObserver(context);
			}
		}
		return observer;
	}
	/**
	 * 设置工作区间内的表单数据操作观察者
	 * @param context
	 * @param observer
	 * @throws Exception
	 */
	public static void setFormOpObserver(Context context,JITFormOpObserver observer)throws Exception{
		AppCacheUtil.setFormOpObserver(context, observer);
	}
	
	

	public static void main(String[] args) {
		Map<String,Object> appContext = new LinkedHashMap<>();
		WorkSpace ws = new WorkSpace().setOrgModel("aaa").setUserModel("sss");
		appContext.put(Key_CurrentWorkSpace, ws);
		Map<String,Object> envMap = new LinkedHashMap<>();
		envMap.put("$appContext", appContext);
		Object result = AviatorEvaluator.execute("$appContext.currentWorkSpace.userModel", envMap);
		System.out.println(result);
	}
}
