package cell.fe.cmn;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.kwaidoo.ms.tool.ToolUtilities;
import com.leavay.common.util.javac.ClassFactory;

import bap.cells.Cells;
import cell.CellIntf;
import cmn.dto.Progress;
import cmn.enums.TraceLevel;
import cmn.util.DateUtil;
import cmn.util.ProgressUtil;
import cmn.util.TraceUtil;
import fe.cmn.app.ability.BatchExecuteCallback;
import fe.cmn.panel.PanelContext;
import fe.cmn.widget.WidgetDto;
import fe.util.LazyPanelUtil;
import fe.util.component.Component;
import fe.util.component.param.WidgetParam;
import fe.util.i18n.FeUtilConst;
import fe.util.intf.ServiceIntf;

/**
 * 通用的界面服务接口
 *
 * @author chenxb
 */
public interface IFeCmnService extends CellIntf,ServiceIntf {
	static IFeCmnService get() {
		return Cells.get(IFeCmnService.class);
	}
	
	default long getPluginTag() throws Exception{
		return ClassFactory.getPluginTag();
	}
	
	default boolean isEnableFeTracker() {
		return FeUtilConst.isEnableFeTracker();
	}
	default void setEnableFeTracker(boolean enableFeTracker) {
		FeUtilConst.setEnableFeTracker(enableFeTracker);
	}
	default TraceLevel getTraceLevel() {
		return TraceUtil.getLevel();
	}
	default void setTraceLevel(TraceLevel level) {
		TraceUtil.setLevel(level);
	}
	default Map<String,TraceLevel> getTraceLevelMap() throws Exception {
		return TraceUtil.getTracerLevelMap();
	}
	default void setTraceLevelMap(ConcurrentHashMap<String,TraceLevel> levelMap) {
		TraceUtil.setTracerLevelMap(levelMap);
	}
	/**
	 * 根据所有面板的上下文以及面板的widget对象生成对应的面板类，用于从界面上获取到widget后重新构建面板类，需要初始化是在widget有设置binaryData
	 * @param panelContext 要获取面板类所在的面板上下文
	 * @param widget
	 * @return
	 * @throws Exception
	 */
	default Component<WidgetParam> getComponentInstance(PanelContext panelContext,WidgetDto widget)throws Exception{
		return getComponentInstance(panelContext, widget, true);
	}
	default Component<WidgetParam> getComponentInstance(PanelContext panelContext,WidgetDto widget,boolean ignoreLazyPanel)throws Exception{
		WidgetParam widgetParam = (WidgetParam) widget.getBinaryData();
		if(widgetParam == null) {
			throw new Exception("widget未设置binaryData，请在初始化时设置widgetParam");
		}
		return getComponentInstance(panelContext, widgetParam,ignoreLazyPanel);
	}
	/**
	 * 根据所有面板的上下文以及面板的widgetParam，用于从界面上获取到widget的binaryData后重新构建面板类，需要初始化是在widget有设置binaryData
	 * @param panelContext 要获取面板类所在的面板上下文
	 * @param widget
	 * @return
	 * @throws Exception
	 */
	default Component<WidgetParam> getComponentInstance(PanelContext panelContext,WidgetParam widgetParam)throws Exception{
		return getComponentInstance(panelContext, widgetParam, true);
	}
	default Component<WidgetParam> getComponentInstance(PanelContext panelContext,WidgetParam widgetParam,boolean ignoreLazyPanel)throws Exception{
		String invokeClass = widgetParam.getInvokeClass();
		if(invokeClass == null) {
			throw new Exception("widgetParam中的invokeClass为空，请在widget初始化时设置widgetParam");
		}
		Class<Component> clazz = (Class<Component>) ClassFactory.getValidClassLoader().loadClass(invokeClass);
		Component instance = clazz.newInstance();
		instance.setWidgetParam(widgetParam);
		if(instance instanceof LazyPanelUtil) {
			instance = ((LazyPanelUtil) instance).getRealPanelComponent();
		}
		instance.setPanelContext(panelContext);
		return instance;
	}
	
	default void ping(Progress prog,int times,PanelContext panelContext)throws Exception{
		if(prog == null)
			prog = Progress.newOutput();
		prog.setMessage("执行前端的回调延迟测试：", true);
		Double[] numbers = new Double[times]; 
		long errorCnt = 0;
		for(int i =0;i<times;i++) {
			ProgressUtil.assertCancel(prog);
			long start = System.currentTimeMillis();
			try {
				BatchExecuteCallback.batchExecute(panelContext.getChannel(), new ArrayList<>());
				long end = System.currentTimeMillis();
				numbers[i] = (double) (end-start);
				prog.sendProcess(i*100/times, "第"+(i+1)+"次，BatchExecuteCallback空执行"+DateUtil.formatMs(end-start), true);
			}catch (Exception e) {
				errorCnt++;
				long end = System.currentTimeMillis();
				numbers[i] = (double) (end-start);
				prog.sendProcess(i*100/times, "第"+(i+1)+"次，BatchExecuteCallback空执行出错："+DateUtil.formatMs(end-start), true);
			}
			ToolUtilities.sleep(1000);
		}
		double[] result = calculateMaxMinAverage(numbers);
		prog.setMessage("统计信息：\n回调往返的估计时间(以毫秒为单位):", true);
		prog.setMessage("成功次数： "+(times-errorCnt)+"，失败次数" + errorCnt, true);
		prog.setMessage("最短 = "+result[1]+"ms，最长 = "+result[0]+"ms，平均 = "+result[2]+"ms", true);
		prog.finish();
	}
	
	
	public static double[] calculateMaxMinAverage(Double[] numbers) {
		if (numbers == null || numbers.length == 0) {
			throw new IllegalArgumentException("数组不能为空");
		}
		
		// 初始化最大值和最小值为数组的第一个元素
		double max = numbers[0];
		double min = numbers[0];
		double sum = 0;
		
		// 遍历数组
		for (Double number : numbers) {
			if(number == null)
				continue;
			// 更新最大值
			if (number > max) {
				max = number;
			}
			// 更新最小值
			if (number < min) {
				min = number;
			}
			// 累加求和
			sum += number;
		}
		
		// 计算平均值
		double average = sum / numbers.length;
		
		// 返回结果
		return new double[]{max, min, average};
	}
}
