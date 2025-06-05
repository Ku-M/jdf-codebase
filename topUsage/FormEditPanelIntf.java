package fe.util.component;

import fe.cmn.panel.PanelContext;
import fe.cmn.panel.PanelValue;
import fe.util.component.param.WidgetParam;
import fe.util.editor.valuehanlder.EditorFieldDefine;

import java.util.List;

public interface FormEditPanelIntf<T extends WidgetParam, R> extends Component<T> {

	/**
	 * 获取表单属性定义，每一个属性定义对应表单绑定DTO(R类型）中的每个属性，
	 * 基础定义属性包括：name，label，require
	 * 当PanelValue中的值与DTO中的值类型不匹配时，可使用EditorTypeHandler接口的实现类进行值转换处理，
	 * 当表单定义为动态变化时，需跟随数据及界面值进行计算表单定义
	 * 常用的值转换处理类包括：
	 * ● IntegerEditorHandler.java 整数值处理类
	 * ● LongEditorHandler.java	长数值处理类
	 * ● DoubleEditorHandler.java	浮点数值处理类
	 * ● EmbedPanelEditorHandler.java	内部DTO对象值处理类，此时内部DTO绑定在表单的一个内嵌面板中，需要定义embedPanelClazz和embedFieldDefs声明对内嵌面板的值处理
	 * ● EmbedTableEditorHandler.java	DTO列表值处理类，此时内部DTO列表绑定在表单的内嵌表格中
	 * ● MultiSelectEditorHandler.java	基础类型列表值处理类，对应多选下拉列表
	 * ● SelectEditorHandler.java	对应单选下来列表
	 * ● SelectTreeEditorHandler.java	对应选择树节点处理类，使用节点key作为取值
	 *
	 * @param context
	 * @param data  表单对象数据
	 * @param panelValue 当前界面值
	 * @return
	 * @throws Exception
	 */
	List<EditorFieldDefine> getEditorFieldDefine(PanelContext context, R data, PanelValue panelValue) throws Exception;

	/**
	 * 将界面上的值写回绑定的DTO对象（R类型）中，
	 *
	 * @param context
	 * @param object     DTO对象
	 * @param panelValue 界面值，一般在弹出面板退出时返回值，或通过界面能力QueryPanelValue获取
	 */
	void writePanelValue(PanelContext context, R object, PanelValue panelValue) throws Exception;

	/**
	 * 校验DTO取值，重写此方法在调用writePanelValue时在界面值回写后回进行值校验
	 *
	 * @param context
	 * @param fieldDefs
	 * @param object     DTO对象
	 * @param panelValue 界面值，一般在弹出面板退出时返回值，或通过界面能力QueryPanelValue获取
	 * @throws Exception
	 */
	void verifyValue(PanelContext context, List<EditorFieldDefine> fieldDefs, R object, PanelValue panelValue) throws Exception;

	/**
	 * 校验必填字段，调用此方法时，将根据属性定义列表中定义的属性对可见属性进行必填校验
	 *
	 * @param context
	 * @param fieldDefs
	 * @param panelValue 界面值，一般在弹出面板退出时返回值，或通过界面能力QueryPanelValue获取
	 * @throws Exception
	 */
	void verifyRequireFields(PanelContext context, List<EditorFieldDefine> fieldDefs, PanelValue panelValue) throws Exception;
}
