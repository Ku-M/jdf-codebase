package fe.util.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.leavay.ms.tool.CmnUtil;

import fe.cmn.panel.PanelContext;
import fe.cmn.panel.PanelValue;
import fe.cmn.panel.ability.QueryChildVisible;
import fe.cmn.panel.ability.QueryEditorsWritable;
import fe.util.component.ablity.PanelAblity;
import fe.util.component.param.WidgetParam;
import fe.util.editor.valuehanlder.EditorFieldDefine;
import fe.util.editor.valuehanlder.EditorTypeHandler;
import fe.util.editor.valuehanlder.EditorValueHandlerFactory;

/**
 * 抽象表单面板，构建表单编辑面板可派生于当前类
 * 继承此抽象面板的实现步骤：
 * 1.定义getEditorFieldDefine方法，
 * 2.构建DTO属性对应的编辑器组件（以DTO属性名作为编辑器组件的widgetId）
 * 构建DTO属性值对应的编辑器时，在getWidget方法中
 * 可通过 wrapEditor(EditorFieldDefine fieldDef,double labelWidth,WidgetDto editor)
 * 或 addEditor(BoxDto mainBox,EditorFieldDefine fieldDef,WidgetDto editor)
 * 可将DTO的属性值编辑器组件包装在一个带标签和必填提示的盒子中，
 * 3.界面填值提交后可通过 writePanelValue的方法将值回写到DTO方法中
 * 可选实现方法：verifyValue 实现此方法在调用writePanelValue回写值到DTO后回进行DTO值校验
 *
 * @param <T> 组件参数
 * @param <R> 表单绑定的DTO对象
 * @author chenxb
 */
public abstract class AbsFormEditPanel<T extends WidgetParam, R> extends AbsComponent<T> implements FormEditPanelIntf<T, R>, PanelAblity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5098687716523296781L;

	/**
	 * 获取表单属性定义，每一个属性定义对应表单绑定DTO(R类型）中的每个属性，
	 * 基础定义属性包括：name，label，require
	 * 当PanelValue中的值与DTO中的值类型不匹配时，可使用EditorTypeHandler接口的实现类进行值转换处理，
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
	 * @param data
	 * @param panelValue
	 * @return
	 * @throws Exception
	 */
	public abstract List<EditorFieldDefine> getEditorFieldDefine(PanelContext context, R data, PanelValue panelValue) throws Exception;

	/**
	 * 将 getEditorFieldDefine 获取 List 结果转成 Map 格式
	 *
	 * @param context
	 * @param data
	 * @param panelValue
	 * @return
	 * @throws Exception
	 */
	public Map<String, EditorFieldDefine> getEditorFieldDefineMap(PanelContext context, R data, PanelValue panelValue) throws Exception {
		List<EditorFieldDefine> fieldDefs = getEditorFieldDefine(context, data, panelValue);
		Map<String, EditorFieldDefine> fieldDefMap = new HashMap<>();
		if (!CmnUtil.isCollectionEmpty(fieldDefs)) {
			for (EditorFieldDefine fieldDef : fieldDefs) {
				fieldDefMap.put(fieldDef.getName(), fieldDef);
			}
		}
		return fieldDefMap;
	}

	/**
	 * 将界面上的值写回绑定的DTO对象（R类型）中，
	 *
	 * @param context
	 * @param data       DTO对象
	 * @param panelValue 界面值，一般在弹出面板退出时返回值，或通过界面能力QueryPanelValue获取
	 */
	public void writePanelValue(PanelContext context, R data, PanelValue panelValue) throws Exception {
		writePanelValue(context, data, panelValue, true);
	}
	
	public void writePanelValue(PanelContext context, R data, PanelValue panelValue,boolean verify) throws Exception {
		List<EditorFieldDefine> fieldDefs = getEditorFieldDefine(context, data, panelValue);
//		verifyRequireFields(context, fieldDefs, panelValue);
		getEditorValueHandlerFactory().writePanelValue(data, panelValue.getMapValue(), fieldDefs);
		if(verify)
			verifyValue(context, fieldDefs, data, panelValue);
	}
	
	public EditorValueHandlerFactory getEditorValueHandlerFactory() {
		return EditorValueHandlerFactory.create();
	}

	/**
	 * 校验必填字段，调用此方法时，将根据属性定义列表中定义的属性对可见属性进行必填校验
	 *
	 * @param context
	 * @param fieldDefs
	 * @param panelValue
	 * @throws Exception
	 */
	public void verifyRequireFields(PanelContext context, List<EditorFieldDefine> fieldDefs, PanelValue panelValue) throws Exception {

		Set<String> widgets = fieldDefs.stream()
				.filter(v -> v.isRequire()
						|| (v.getEmbedPanelDefine() != null && !CmnUtil.isCollectionEmpty(v.getEmbedPanelDefine().getEmbedPanelFieldDefines()))
				)
				.map(EditorFieldDefine::getName)
				.collect(Collectors.toSet());
		Map<String, Boolean> mapVisible = QueryChildVisible.query(context, widgets);
		Map<String, Boolean> mapWritable = QueryEditorsWritable.query(context, widgets);

		for (EditorFieldDefine fieldDef : fieldDefs) {
			Boolean visible = mapVisible.get(fieldDef.getName());
			if (visible == null || !visible)
				continue;
			Boolean writable = mapWritable.get(fieldDef.getName());
			Class<? extends EditorTypeHandler> handlerClazz = fieldDef.getEditorTypeHandler();
			EditorTypeHandler handler = handlerClazz.newInstance();
			if (writable == null || !writable) {
				if (handler.isBasicEditor())
					continue;
			}
			Object value = panelValue.getValue(fieldDef.getName());
			handler.verifyRequireValue(context, fieldDef, value, "");
		}
	}
}
