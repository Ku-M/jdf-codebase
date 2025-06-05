package gpf.dc.fe.component.adur.data.field;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.leavay.common.util.ToolUtilities;
import com.leavay.ms.tool.CmnUtil;

import bap.cells.Cells;
import cell.fe.cmn.IFeI18nPlugin;
import cmn.util.NullUtil;
import cmn.util.TraceUtil;
import cmn.util.Tracer;
import crpc.CRpcContainerIntf;
import fe.cmn.data.DateDto;
import fe.cmn.data.DatePickerType;
import fe.cmn.data.NullPojo;
import fe.cmn.data.PairDto;
import fe.cmn.editor.CheckboxDto;
import fe.cmn.editor.DatePickerDto;
import fe.cmn.editor.SelectEditorDto;
import fe.cmn.panel.PanelContext;
import fe.cmn.panel.PanelValue;
import fe.cmn.table.TableCellDto;
import fe.cmn.table.TableColumnDto;
import fe.cmn.widget.LabelDto;
import fe.cmn.widget.WidgetDto;
import fe.util.component.AbsComponent;
import fe.util.component.Component;
import fe.util.component.builder.DatePickerBuilder;
import fe.util.component.builder.FeBuilderPortal;
import fe.util.component.param.WidgetParam;
import fe.util.editor.valuehanlder.EditorFieldDefine;
import fe.util.intf.ServiceIntf;
import gpf.adur.data.AssociationData;
import gpf.adur.data.AttachData;
import gpf.adur.data.BaseFormFieldExtend;
import gpf.adur.data.DataType;
import gpf.adur.data.Form;
import gpf.adur.data.FormField;
import gpf.adur.data.Password;
import gpf.adur.data.WebAttachData;
import gpf.dc.fe.component.editor.SelectModelListEditor;
import gpf.dc.fe.dto.fieldextend.FormFieldDisplayIntf;
import gpf.dc.fe.util.GpfDCFeI18n;

public class FormFieldEditorFactory implements Serializable,CRpcContainerIntf{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String panelGolbalKey;
	
	boolean isMobileEnv = false;
	
	public boolean isMobileEnv() {
		return isMobileEnv;
	}
	public void setMobileEnv(boolean isMobileEnv) {
		this.isMobileEnv = isMobileEnv;
	}

	public FieldEditorIntf[] _editorFactory = { new FieldEditor_NestingTable(this) ,new FieldEditor_SelectDepend(this) , 
			new FieldEditor_SelectList(this),
			new FieldEditor_KeyFormField(this),new FieldEditor_KeyValue(this),
			new FieldEditor_Long(this), new FieldEditor_Decimal(this),
			new FieldEditor_Password(this),new FieldEditor_RichDocument(this),
			new FieldEditor_Date(this),new FieldEditor_Bool(this),
			new FieldEditor_Attach(this),
			new FieldEditor_WebAttach(this),
			new FieldEditor_Image(this),new FieldEditor_Binary(this),new FieldEditor_Text(this)};

	
	public static FormFieldEditorFactory create(String panelGlobalKey,Component component,boolean lazyQueryNestingData) {
		FormFieldEditorFactory factory = new FormFieldEditorFactory();
		factory.setPanelGlobalKey(panelGlobalKey);
		factory.setComponent(component);
		factory.setWidgetParam(component.getWidgetParam());
		factory.setLazyQueryCompoundField(lazyQueryNestingData);
		return factory;
	}
	
	public String getPanelGlobalKey() {
		return panelGolbalKey;
	}
	public void setPanelGlobalKey(String panelGolbalKey) {
		this.panelGolbalKey = panelGolbalKey;
	}
	
	public FieldEditorIntf[] getEditorFactory() {
		return _editorFactory;
	}
	
	public void setEditorFactory(FieldEditorIntf[] editorFactory) {
		this._editorFactory = editorFactory;
	}

	public FeBuilderPortal getFeBuilderPortal() {
		return new FeBuilderPortal() {
			
			@Override
			public Class<? extends ServiceIntf> getBuilderService() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

	public TableColumnDto buildTableColumnEditor(PanelContext context,Object form,FormField field,boolean isWritable) throws Exception {
		BaseFormFieldExtend extendInfo = field.getExtendInfo();
		if(extendInfo != null && extendInfo instanceof FormFieldDisplayIntf) {
			TableColumnDto cell = ((FormFieldDisplayIntf)extendInfo).buildTableColumnEditor(this,context, field);
			if(cell != null) {
				return cell;
			}
		}
		
//		boolean isWritable = true;
		
		TableColumnDto fieldCol = new TableColumnDto();
		fieldCol.setName(field.getCode());
		fieldCol.setLabel(field.getName());
		DataType type = field.getDataTypeEnum();
		switch (type) {
		case Text:
			fieldCol.setEditor(getFeBuilderPortal().newTextEditor(null, null).setWritable(isWritable));
			break;
		case Long:
			fieldCol.setEditor(getFeBuilderPortal().newLongEditor(null, null).setWritable(isWritable));
			break;
		case Decimal:
			fieldCol.setEditor(getFeBuilderPortal().newDecimalEditor(null, null).setWritable(isWritable));
			break;
		case Boolean:
			CheckboxDto editor = new CheckboxDto(field.getCode());
			fieldCol.setEditor(editor.setWritable(isWritable));
			break;
		case Date:
			DatePickerDto picker = new DatePickerDto(DatePickerType.date);
			fieldCol.setEditor(picker.setWritable(isWritable));
			break;
		case Password:
//			TextEditorDto passwdEdt = new TextEditorDto().setObscureText(true);
			fieldCol.setEditor(getFeBuilderPortal().newPasswordEditor(null, null).setWritable(isWritable));
//			fieldCol.setHide(true);
			break;
		case Relate:{
			SelectEditorDto selector = SelectModelListEditor.newSelector(context, null, field, null, isWritable);
			fieldCol.setEditor(selector);
//			fieldCol.setEditor(new LabelDto());
//			fieldCol.setHide(true);
			break;
		}
		case Attach:
		case WebAttach:
			fieldCol.setEditor(new LabelDto());
			break;
		case NestingModel:
			fieldCol.setEditor(new LabelDto());
			fieldCol.setHide(true);
			break;
		default:
			break;
		}
		return fieldCol;
	}
	
	public TableCellDto buildEditorTableCell(PanelContext panelContext,Object form,FormField field, Object value) throws Exception {
		BaseFormFieldExtend extendInfo = field.getExtendInfo();
		if(extendInfo != null && extendInfo instanceof FormFieldDisplayIntf) {
			TableCellDto cell = ((FormFieldDisplayIntf)extendInfo).buildEditorTableCell(this,panelContext, form, field, value);
			if(cell != null) {
				return cell;
			}
		}
		
		DataType type = field.getDataTypeEnum();
		switch (type) {
		case Text:
			return new TableCellDto(CmnUtil.getString(value, ""));
		case Long:
		case Decimal:
			return new TableCellDto(value == null ? "" : "" + value);
		case Boolean:
			return new TableCellDto(CmnUtil.getBoolean(value, false));
		case Date:{
			if(value != null)
				return new TableCellDto(Arrays.asList(DatePickerBuilder.convert2DateDto((Long)value)));
			else
				return new TableCellDto(new DateDto());
		}
		case Password:
			String passValue = value == null ? "" : ((Password) value).getValue();
			return new TableCellDto(passValue == null ? "" : passValue);
		case Relate:{
			if(field.isAssocMultiSelect()) {
				List<AssociationData> codes = (List<AssociationData>) value;
				List<PairDto<String, String>> pairs = new ArrayList<>();
				for(AssociationData code : NullUtil.get(codes)) {
					PairDto<String, String> pair = new PairDto<String, String>(code.getValue(), code.getValue());
					pairs.add(pair);
				}
				return new TableCellDto(pairs);
			}else {
				AssociationData cdo = (AssociationData)value;
				PairDto<String, String> pair = null;
				if(cdo != null) {
					pair = new PairDto<String, String>(cdo.getValue(), cdo.getValue());
				}
				return new TableCellDto(pair);
			}
		}
		case RichDocument:
			if(CmnUtil.isObjectEmpty(value)) {
				return new TableCellDto("");
			}else {
				byte[] bValue = (byte[])value;
				return new TableCellDto(new String(bValue));
			}
			
		default:
			break;
		}
		return new TableCellDto(CmnUtil.getString(value,""));
	}
	
	public Object convert2FormFieldValue(Form form,FormField field,Object guiValue) throws Exception {
		BaseFormFieldExtend extendInfo = field.getExtendInfo();
		if(extendInfo != null && extendInfo instanceof FormFieldDisplayIntf) {
			Object cell = ((FormFieldDisplayIntf)extendInfo).convert2FormFieldValue(this,form, field, guiValue);
			if(!(cell instanceof NullPojo)) {
				return cell;
			}
		}
		DataType display = field.getDataTypeEnum();
		switch (display) {
		case Text:
		case Boolean:
			return guiValue;
		case Password:{
			Password password = form.getPassword(field.getName());
			if(password == null) {
				password = new Password();
			}
			Password newPassword = ToolUtilities.clone(password);
			newPassword.setValue((String)guiValue);
	    	if(!newPassword.isPasswordNoChange()) {
	    		return newPassword;
	    	}
	    	break;
		}
		case Date:
			if(NullPojo.isNull(guiValue) || guiValue == null) {
				return null;
			}else {
				if(guiValue instanceof DateDto) {
					long lValue = DatePickerBuilder.convertValue((DateDto)guiValue);
					return lValue;
				}else if(guiValue instanceof List) {
					List list = (List)guiValue;
					if(CmnUtil.isCollectionEmpty(list)) {
						return null;
					}else {
						long lValue = DatePickerBuilder.convertValue((DateDto)list.get(0));
						return lValue;
					}
				}
			}
			break;
		case Decimal:
			try {
				return CmnUtil.getDouble(guiValue);
			}catch (Exception e) {
			}
			break;
		case Long:
			try {
				return CmnUtil.getLong(guiValue);
			}catch (Exception e) {
			}
			break;
		case Relate:
			if(field.isAssocMultiSelect()) {
				List<AssociationData> assocDatas= new ArrayList<>();
				if(!NullPojo.isNull(guiValue)) {
					List<PairDto> pairs = (List<PairDto>) guiValue;
					for(PairDto pair : pairs) {
						AssociationData data = new AssociationData(field.getAssocFormModel(),(String)pair.getKey());
						assocDatas.add(data);
					}
					
				}
				return assocDatas;
			}else {
				if(!NullPojo.isNull(guiValue)) {
					PairDto pair = (PairDto) guiValue;
					AssociationData data = new AssociationData(field.getAssocFormModel(),(String)pair.getKey());
					return data;
				}else {
					return null;
				}
			}
		default:
			break;
		}
		return null;
	}
	
	public String getI18nString(PanelContext panelContext,String key) throws Exception {
		if(component == null) {
			return IFeI18nPlugin.get().getI18nString(panelContext, key);
		}
		return ((AbsComponent)component).getI18nString(panelContext, key);
	}

	public TableCellDto buildLabelTableCell(PanelContext panelContext,Object form,FormField field, Object value) throws Exception {
		BaseFormFieldExtend extendInfo = field.getExtendInfo();
		if(extendInfo != null && extendInfo instanceof FormFieldDisplayIntf) {
			TableCellDto cell = ((FormFieldDisplayIntf)extendInfo).buildLabelTableCell(this,panelContext, form, field, value);
			if(cell != null) {
				return cell;
			}
		}
		
		DataType type = field.getDataTypeEnum();
		switch (type) {
		case Text:
			return new TableCellDto(value == null ? "" : (String) value);
		case Long:
		case Decimal:{
			String formattedValue = "";
			if(value != null) {
				// 使用 DecimalFormat 格式化输出
		        DecimalFormat decimalFormat = new DecimalFormat("#.######"); // 定义格式
		        formattedValue = decimalFormat.format(value);
			}
			return new TableCellDto(formattedValue);
		}
		case Boolean:
			return new TableCellDto(CmnUtil.getBoolean(value, false) ? getI18nString(panelContext, GpfDCFeI18n.TRUE): getI18nString(panelContext, GpfDCFeI18n.FALSE));
		case Date:{
			String date = ""; 
			if(value != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				date = sdf.format(new Date(CmnUtil.getLong(value)));
			}
			return new TableCellDto(date);
		}
		case NestingModel:
			return new TableCellDto("");
//		case Password:
//			String passValue = value == null ? "" : ((Password) value).getValue();
//			return new TableCellDto(passValue == null ? "" : passValue);
		case Relate:
			return new TableCellDto(CmnUtil.getString(value,""));
		case RichDocument:
			if(CmnUtil.isObjectEmpty(value)) {
				return new TableCellDto("");
			}else {
				byte[] bValue = (byte[])value;
				return new TableCellDto(new String(bValue));
			}
		case Attach:
			if(CmnUtil.isObjectEmpty(value)) {
				return new TableCellDto("");
			}else {
				List<AttachData>  attachBrief = (List<AttachData>) value;
				return new TableCellDto(attachBrief == null ? "":attachBrief.toString());
			}
		case WebAttach:
			if(CmnUtil.isObjectEmpty(value)) {
				return new TableCellDto("");
			}else {
				List<WebAttachData>  attachBrief = (List<WebAttachData>) value;
				StringBuffer sb = new StringBuffer();
				for(WebAttachData attach : attachBrief) {
					if(sb.length() > 0)
						sb.append(",");
					sb.append(attach.getName());
				}
				return new TableCellDto(sb.toString());
			}
		case Password:
			return new TableCellDto("********");
		default:
			break;
		}
		return new TableCellDto(CmnUtil.getString(value, ""));
	}
	
	public WidgetDto buildEditor(PanelContext panelContext,Object object,FormField field, Object value,boolean writable) throws Exception {
		Tracer tracer = TraceUtil.getCurrentTracer();
		WidgetDto editor = null;
		if(!isMaintainMode) {
			BaseFormFieldExtend extendInfo = field.getExtendInfo();
			if(extendInfo != null && extendInfo instanceof FormFieldDisplayIntf) {
				try
	            {
					if(((FormFieldDisplayIntf)extendInfo).getService() != null) {
						ServiceIntf serviceCell = Cells.get(((FormFieldDisplayIntf)extendInfo).getService());
						try {
							editor = (WidgetDto) serviceCell.callFunction(extendInfo, "buildFormFieldEditor", this, panelContext, field, object,value,writable);
						}catch (Exception e) {
						}
					}
					if(editor == null) {
						editor = ((FormFieldDisplayIntf)extendInfo).buildFormFieldEditor(this, panelContext, field, object,value,writable);
					}
				if(editor != null) {
					return editor;
				}	
	            }catch (Exception e) {
					throw new Exception("构建["+field.getName()+"]组件编辑器出错", e);
				}
			}
		}
        for (FieldEditorIntf editorIntf : _editorFactory)
        {
            try
            {
                editor = editorIntf.buildEditor(this,panelContext,object, field, value,writable);
            } catch (Exception exp)
            {
                exp.printStackTrace();
                editor = new LabelDto(ToolUtilities.getString(value, ""));
            }
            if (editor != null)
                break;
        }
        if(editor instanceof LabelDto) {
        	tracer.warning(field.getName() + "初始化编辑器出错！");
        }
        if(editor == null) {
        	return new LabelDto();
        }
        return editor;
	}
	
	public EditorFieldDefine getEditorFieldDefine(PanelContext panelContext,PanelValue panelValue,FormField field) throws Exception {
		if(!isMaintainMode) {
			BaseFormFieldExtend extendInfo = field.getExtendInfo();
			if(extendInfo != null && extendInfo instanceof FormFieldDisplayIntf) {
				EditorFieldDefine fieldDef = ((FormFieldDisplayIntf)extendInfo).getFormFieldEditorHandler(panelContext,panelValue,field,this);
				if(fieldDef != null)
					return fieldDef;
			}
		}
        for (FieldEditorIntf editorIntf : _editorFactory)
        {
        	boolean ismatch = editorIntf.isMatched(field, null);
        	if(ismatch) {
        		return editorIntf.getEditorFieldDefine(panelContext, panelValue, field);
        	}
        }
        return null;
	}
	/**
	 * 构建属性编辑器工厂类所属的组件
	 */
	Component component;
	public Component getComponent() {
		return component;
	}
	public FormFieldEditorFactory setComponent(Component component) {
		this.component = component;
		return this;
	}
	WidgetParam widgetParam;
	public WidgetParam getWidgetParam() {
		return widgetParam;
	}
	
	public void setWidgetParam(WidgetParam widgetParam) {
		this.widgetParam = widgetParam;
	}
	/**
	 * 是否布局器模式下构建,布局模式下，不执行初始化操作
	 */
	private boolean isLayoutMode = false;
	public FormFieldEditorFactory setLayoutMode(boolean isLayoutMode) {
		this.isLayoutMode = isLayoutMode;
		return this;
	}
	public boolean isLayoutMode() {
		return isLayoutMode;
	}
	/**
	 * 查看详情嵌套数据是否在表单加载后再查询
	 */
	private boolean isLazyQueryCompoundField = false;
	public boolean isLazyQueryCompoundField() {
		return isLazyQueryCompoundField;
	}
	public FormFieldEditorFactory setLazyQueryCompoundField(boolean isLazyQueryCompoundField) {
		this.isLazyQueryCompoundField = isLazyQueryCompoundField;
		return this;
	}
	/**
	 * 是否运维模式，运维模式下，显示扩展不显示
	 */
	private boolean isMaintainMode = false;
	
	public boolean isMaintainMode() {
		return isMaintainMode;
	}
	public FormFieldEditorFactory setMaintainMode(boolean isMaintainMode) {
		this.isMaintainMode = isMaintainMode;
		return this;
	}
}
