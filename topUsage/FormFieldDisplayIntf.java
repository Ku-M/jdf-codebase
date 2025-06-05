package gpf.dc.fe.dto.fieldextend;

import org.nutz.dao.util.cri.SqlExpression;

import cell.fe.cmn.IFeI18nPlugin;
import fe.cmn.data.NullPojo;
import fe.cmn.panel.PanelContext;
import fe.cmn.panel.PanelValue;
import fe.cmn.table.TableCellDto;
import fe.cmn.table.TableColumnDto;
import fe.cmn.widget.WidgetDto;
import fe.util.component.AbsComponent;
import fe.util.component.builder.FeBuilderPortal;
import fe.util.editor.valuehanlder.EditorFieldDefine;
import fe.util.intf.ServiceIntf;
import gpf.adur.data.BaseFormFieldExtend;
import gpf.adur.data.Form;
import gpf.adur.data.FormField;
import gpf.dc.fe.component.adur.FormFieldExtendPanel;
import gpf.dc.fe.component.adur.data.field.FormFieldEditorFactory;
/**
 * 表单属性定义扩展显示类型
 * 所有在界面上属性扩展类型需要实现此接口，定义属性扩展类型的参数值编辑界面和表单属性值编辑界面
 * @author chenxb
 *
 */
public interface FormFieldDisplayIntf {

	/**
	 * 适用的属性定义类型
	 * @param field
	 * @return
	 */
	public boolean accept(FormField field); 
	/**
	 * 扩展类型的参数值配置界面，在属性定义界面中提供参数值配置时应用
	 * @param context
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public FormFieldExtendPanel getExtendInfoEditPanel(PanelContext context,BaseFormFieldExtend data)throws Exception;
	
	default void writeExtendInfoPaneValue(PanelContext context,BaseFormFieldExtend data,PanelValue panelValue) throws Exception {
		FormFieldExtendPanel extendPanel = getExtendInfoEditPanel(context, data);
		if(extendPanel != null) {
			extendPanel.writePanelValue(context, data, panelValue);
		}
	}
	
	default FeBuilderPortal getFeBuilderPortal(){
    	return new FeBuilderPortal() {
			
			@Override
			public Class<? extends ServiceIntf> getBuilderService() {
				// TODO Auto-generated method stub
				return null;
			}
		};
    }
	/**
	 * 构建表单面板上的属性编辑器
	 * @param factory	表单属性编辑器工厂
	 * @param context	面板上下文
	 * @param fieldDef	属性定义
	 * @param form	表单数据
	 * @param fieldValue	属性值
	 * @param writable 是否可编辑
	 * @return
	 * @throws Exception
	 */
	default WidgetDto buildFormFieldEditor(FormFieldEditorFactory factory,PanelContext context,FormField fieldDef,Object form,Object fieldValue,boolean writable)throws Exception{
		return null;
	}
	/**
	 * 构建表格上展示为文本标签是的单元格值
	 * @param factory
	 * @param context
	 * @param field
	 * @param value
	 * @return
	 * @throws Exception
	 */
	default TableCellDto buildLabelTableCell(FormFieldEditorFactory factory,PanelContext context,Object data,FormField field, Object value) throws Exception {
		return null;
	}
	/**
	 * 构架可编辑表格上的列编辑组件
	 * @param factory
	 * @param context
	 * @param field
	 * @return
	 * @throws Exception
	 */
	default TableColumnDto buildTableColumnEditor(FormFieldEditorFactory factory,PanelContext context,FormField field) throws Exception {
		return null;
	}
	/**
	 * 构建可编辑表格上的单元格值
	 * @param factory
	 * @param context
	 * @param field
	 * @param value
	 * @return
	 * @throws Exception
	 */
	default TableCellDto buildEditorTableCell(FormFieldEditorFactory factory,PanelContext context,Object form,FormField field, Object value) throws Exception {
		return null;
	}
	/**
	 * 将表格上的单元格取值转换成表格属性值
	 * @param factory
	 * @param form
	 * @param field
	 * @param guiValue
	 * @return
	 * @throws Exception
	 */
	default Object convert2FormFieldValue(FormFieldEditorFactory factory,Form form,FormField field,Object guiValue) throws Exception {
		return new NullPojo();
	}
	/**
	 * 定义属性编辑器的取值处理器
	 * @param panelContext
	 * @param panelValue
	 * @param field
	 * @return
	 * @throws Exception
	 */
	default EditorFieldDefine getFormFieldEditorHandler(PanelContext panelContext,PanelValue panelValue,FormField field,FormFieldEditorFactory factory)throws Exception{
		return null;
	}
	
	default EditorFieldDefine buildDefaultFormFieldEditorHandler(PanelContext panelContext,PanelValue panelValue,FormField field,FormFieldEditorFactory factory) throws Exception {
		String fieldLabel = getI18nString(factory, panelContext, field.getName());
		return new EditorFieldDefine(field.getCode(), fieldLabel, field.isNotNull());
	}
	/**
	 * 构建属性的条件表达式
	 * @param field
	 * @param filter
	 * @param value
	 * @return
	 */
	default SqlExpression buildSqlExpression(FormField field,Object filterDto,Object value) {
		return null;
	}
	
	default String getI18nString(FormFieldEditorFactory factory,PanelContext panelContext,String key)throws Exception{
		if(factory == null)
			return IFeI18nPlugin.get().getI18nString(panelContext, key);
		AbsComponent component = (AbsComponent) factory.getComponent();
		if(component == null)
			return IFeI18nPlugin.get().getI18nString(panelContext, key);
		return component.getI18nString(panelContext, key);
	}
	
	default Class<? extends ServiceIntf> getService() {
		return null;
	}
}
