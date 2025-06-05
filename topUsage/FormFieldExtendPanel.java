package gpf.dc.fe.component.adur;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.kwaidoo.ms.tool.CmnUtil;
import com.kwaidoo.ms.tool.ToolUtilities;
import com.leavay.common.util.javac.ClassFactory;

import cell.fe.gpf.dc.IGpfDCFeService;
import cmn.anotation.ClassDeclare;
import cmn.anotation.FieldDeclare;
import cmn.i18n.AbsI18n;
import cmn.util.TraceUtil;
import cmn.util.Tracer;
import fe.cmn.data.NullPojo;
import fe.cmn.data.PairDto;
import fe.cmn.editor.SelectEditorDto;
import fe.cmn.editor.SelectEditorInterface;
import fe.cmn.editor.SelectEditorQuerier;
import fe.cmn.editor.SelectEditorQuerierContext;
import fe.cmn.panel.BoxDto;
import fe.cmn.panel.PanelContext;
import fe.cmn.panel.PanelValue;
import fe.cmn.panel.PanelValueAdapter;
import fe.cmn.panel.SinglePanelDto;
import fe.cmn.widget.WidgetDto;
import fe.component.page.config.WidgetConfigEditorIntf;
import fe.util.FeLayoutUtil;
import fe.util.FeSelectUtil;
import fe.util.component.AbsFormEditPanel;
import fe.util.component.dto.FeDeliverData;
import fe.util.component.param.DataEditParam;
import fe.util.editor.valuehanlder.DoubleEditorHandler;
import fe.util.editor.valuehanlder.EditorFieldDefine;
import fe.util.editor.valuehanlder.IntegerEditorHandler;
import fe.util.editor.valuehanlder.LongEditorHandler;
import fe.util.editor.valuehanlder.SelectEditorHandler;
import fe.util.editor.valuehanlder.SelectEnumEditorHandler;
import fe.util.intf.ServiceIntf;
import gpf.adur.data.BaseFormFieldExtend;
import gpf.adur.data.FormField;
import gpf.dc.fe.util.GpfDCFeI18n;
import gpf.dc.fe.util.GpfDCFeSelect;
@ClassDeclare(
	    label = "表单字段扩展面板",
	    what = "提供一个用于管理和编辑表单字段扩展属性的面板组件。",
	    why = "在动态表单设计中，字段扩展属性的配置是实现个性化和高级功能的重要部分，需要一个工具来支持这些配置。",
	    how = "通过继承`AbsFormEditPanel`类，复用表单编辑功能，并结合`SelectEditorInterface`和`WidgetConfigEditorIntf`接口，提供编辑器选择和小部件配置能力。",
	    developer = "陈晓斌",
	    version = "1.0",
	    createTime = "2024-11-22",
	    updateTime = "2024-11-22"
	)
public class FormFieldExtendPanel<T extends BaseFormFieldExtend> extends AbsFormEditPanel<DataEditParam<T>, T> implements SelectEditorInterface,WidgetConfigEditorIntf{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 140698383384498175L;
	public final static String DataTransClass = "dataTransClass";
	@Override
	public void verifyValue(PanelContext context, List<EditorFieldDefine> fieldDefs, T object,
			PanelValue panelValue) throws Exception {
		// TODO Auto-generated method stub
		
	}
	public final static String CacheKey_FormField = "$formField$";
	public FormField getContextFormField() throws Exception {
		if(widgetParam == null)
			throw new Exception("Please setWidgetParam");
		return (FormField) widgetParam.getContext().get(CacheKey_FormField);
	}
	public void setContextFormField(FormField formField) throws Exception {
		if(widgetParam == null)
			throw new Exception("Please setWidgetParam");
		widgetParam.getContext().put(CacheKey_FormField,formField);
	}
	
	@Override
	public WidgetDto getWidget(PanelContext panelContext) throws Exception {
		Tracer tracer = TraceUtil.getCurrentTracer();
		T data = widgetParam.getData();
		Map<String, EditorFieldDefine> fieldDefMap = getEditorFieldDefineMap(panelContext, data, null);
		BoxDto mainBox = new BoxDto().setVertical(true).setExpandInBox(false).setScrollable(true);
		PairDto[] items = null;
		PairDto selectItem = null;
		if(!CmnUtil.isStringEmpty(data.getDataTransClass())) {
			try {
				Class transClass = ClassFactory.getValidClassLoader().loadClass(data.getDataTransClass());
				ClassDeclare comment = (ClassDeclare) transClass.getAnnotation(ClassDeclare.class);
				String label = data.getDataTransClass();
				if(comment != null) {
					label = comment.label();
				}
				selectItem = new PairDto(data.getDataTransClass(), label);
			}catch (Exception e) {
				tracer.warning("getWidget",ToolUtilities.getFullExceptionStack(e));
			}
		}
		SelectEditorDto transClassSelector = newSelect(DataTransClass, items, selectItem);
		transClassSelector.setPreferWidth(400);
		transClassSelector.setPanelService(getService());
		SelectEditorQuerier querier = new SelectEditorQuerier();
		querier.setBinaryData(new FeDeliverData<T>(getClass(), DataTransClass, null));
		transClassSelector.setQuerier(querier);
//		transClassSelector.setCachedFromService(true);
		transClassSelector.setBinaryData(widgetParam);
		addEditor(mainBox, fieldDefMap.get(DataTransClass), transClassSelector);
		buildExtendInfoBox(panelContext,mainBox, fieldDefMap,data);
		SinglePanelDto panel = SinglePanelDto.wrap(FeLayoutUtil.wrapScorllBox(mainBox));
		if(!CmnUtil.isStringEmpty(widgetParam.getWidgetId())) {
			panel.setWidgetId(widgetParam.getWidgetId());
		}
		panel.setValueAdapter(new PanelValueAdapter<>().setKeeyStructure(true));
		panel.setBinaryData(widgetParam);
		return panel;
	}
	
	public void buildExtendInfoBox(PanelContext panelContext,BoxDto mainBox,Map<String,EditorFieldDefine> fieldDefMap,T data) throws Exception{
		//TODO 扩展新的属性配置项
	}
	
	/**
	 * 通过反射构建属性值的输入组件
	 * @param context
	 * @param data
	 * @param panelValue
	 * @return
	 * @throws Exception
	 */
	public void buildExtendInfoByReflect(PanelContext panelContext,BoxDto mainBox,Map<String,EditorFieldDefine> fieldDefMap,T data) throws Exception {
		List<Field> fields = ToolUtilities.getAllField(data.getClass(), false);
		AbsI18n i18n = (AbsI18n) getI18n(panelContext);
		for(Field field : fields){
			if(CmnUtil.isStringEqual(field.getName(), DataTransClass)) {
				continue;
			}
			Object value = ToolUtilities.getFieldValue(data, field);
			Type type = field.getType();
			if(type == Long.class || type == long.class) {
				addEditor(mainBox, fieldDefMap.get(field.getName()), newLongEditor(field.getName(), (Long)value));
			}else if(type == Integer.class || type == int.class) {
				addEditor(mainBox, fieldDefMap.get(field.getName()), newIntegerEditor(field.getName(), (Integer)value));
			}else if(type == Double.class || type == double.class) {
				addEditor(mainBox, fieldDefMap.get(field.getName()), newDecimalEditor(field.getName(), (Double)value));
			}else if(type == Boolean.class || type == boolean.class) {
				addEditor(mainBox, fieldDefMap.get(field.getName()), newSwitch(field.getName(), (Boolean)value));
			}else if(type == String.class) {
				addEditor(mainBox, fieldDefMap.get(field.getName()), newTextArea(field.getName(), (String)value, 1, 3));
			}else if(type == List.class) {
				ParameterizedType paramType = (ParameterizedType) type;
				Type actualType = paramType.getActualTypeArguments()[0];
				Class actualClass = (Class) actualType;
				if(actualClass.isEnum()) {
					List<String> keys = new ArrayList<>();
					if(value != null) {
						List<Enum> list = (List<Enum>) value;
						for(Enum enum1 : list) {
							keys.add(enum1.name());
						}
					}
					PairDto<String, String>[] items = FeSelectUtil.getEnumItemsInGroup(actualClass, i18n);
					PairDto<String, String>[] item = FeSelectUtil.matchItems(items, ToolUtilities.toStringArray(keys));
					addEditor(mainBox, fieldDefMap.get(field.getName()), newMultiSelect(field.getName(), items, item));
				}
			}else {
				Class actualClass = (Class) type;
				if(actualClass.isEnum()) {
					PairDto<String, String>[] items = FeSelectUtil.getEnumItemsInGroup(actualClass, i18n);
					PairDto<String, String> item = FeSelectUtil.matchItem(items, ((Enum)value).name());
					addEditor(mainBox, fieldDefMap.get(field.getName()), newSelect(field.getName(), items, item));
				}
			}
		}
	}
	/**
	 * 通过反射获取数据的编辑器定义
	 * @param context
	 * @param data
	 * @param panelValue
	 * @return
	 * @throws Exception
	 */
	public List<EditorFieldDefine> getEditorDefineByReflect(PanelContext context, T data,
			PanelValue panelValue) throws Exception {
		List<Field> fields = ToolUtilities.getAllField(data.getClass(), false);
		AbsI18n i18n = (AbsI18n) getI18n(context);
		List<EditorFieldDefine> editorDefs = new ArrayList<>();
		for(Field field : fields){
			if(CmnUtil.isStringEqual(field.getName(), DataTransClass)) {
				continue;
			}
			Type type = field.getType();
			FieldDeclare fieldDeclare = field.getAnnotation(FieldDeclare.class);
			String label = null;
			if(fieldDeclare != null) {
				label = fieldDeclare.label();
			}else if(i18n != null) {
				label = i18n.formatInGroup(field.getName(), data.getClass().getSimpleName());
			}else {
				label = field.getName();
			}
			if(type == Long.class || type == long.class) {
				editorDefs.add(new EditorFieldDefine(field.getName(), label, false,LongEditorHandler.class));
			}else if(type == Integer.class || type == int.class) {
				editorDefs.add(new EditorFieldDefine(field.getName(), label, false,IntegerEditorHandler.class));
			}else if(type == Double.class || type == double.class) {
				editorDefs.add(new EditorFieldDefine(field.getName(), label, false,DoubleEditorHandler.class));
			}else if(type == Boolean.class || type == boolean.class) {
				editorDefs.add(new EditorFieldDefine(field.getName(), label, false));
			}else if(type == String.class) {
				editorDefs.add(new EditorFieldDefine(field.getName(), label, false));
			}else if(type == List.class) {
				ParameterizedType paramType = (ParameterizedType) type;
				Type actualType = paramType.getActualTypeArguments()[0];
				Class actualClass = (Class) actualType;
				if(actualClass.isEnum()) {
					editorDefs.add(new EditorFieldDefine(field.getName(), label, false,SelectEnumEditorHandler.class));
				}
			}else {
				Class actualClass = (Class) type;
				if(actualClass.isEnum()) {
					editorDefs.add(new EditorFieldDefine(field.getName(), label, false,SelectEnumEditorHandler.class));
				}
			}
		}
		return editorDefs;
	}

	@Override
	public List<EditorFieldDefine> getEditorFieldDefine(PanelContext context, T data,
			PanelValue panelValue) throws Exception {
		List<EditorFieldDefine> editorDefs = new ArrayList<>();
		editorDefs.add(new EditorFieldDefine(DataTransClass, GpfDCFeI18n.getString(DataTransClass), false,SelectEditorHandler.class));
		return editorDefs;
	}
	
	public PairDto getValueAsPairDto(PanelValue panelValue,String widgetId) {
		Object obj = panelValue.getValue(widgetId);
		if(NullPojo.isNull(obj)) {
			return null;
		}
		if(obj instanceof PairDto) {
			return (PairDto) obj;
		}
		return null;
	}

	@Override
	public Class<? extends ServiceIntf> getService() {
		return IGpfDCFeService.class;
	}
	@Override
	public List<PairDto> querySelectItems(SelectEditorQuerier querier, SelectEditorQuerierContext context)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<PairDto> filterSelectItems(SelectEditorQuerier querier, SelectEditorQuerierContext context)
			throws Exception {
		PairDto[] items = GpfDCFeSelect.getFormFieldDataTransClassOptions(getContextFormField().getDataType());
		if(items == null)
			return new ArrayList<>();
		return Arrays.asList(items);
	}

}
