package gpf.dc.fe.util;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nutz.dao.entity.annotation.Comment;

import com.kwaidoo.ms.tool.CmnUtil;
import com.kwaidoo.ms.tool.ToolUtilities;
import com.leavay.common.util.javac.ClassFactory;

import cell.gpf.adur.data.IFormMgr;
import cmn.anotation.ClassDeclare;
import cmn.i18n.AbsI18n;
import fe.cmn.data.PairDto;
import fe.util.FeSelectUtil;
import fe.util.intf.CustomImageIntf;
import gpf.adur.action.ActionModel;
import gpf.adur.data.BaseFormFieldExtend;
import gpf.adur.data.BaseFormModelExtend;
import gpf.adur.data.DataType;
import gpf.adur.data.FormField;
import gpf.adur.data.FormFieldDataTransIntf;
import gpf.adur.data.FormModel;
import gpf.dc.fe.dto.fieldextend.FormFieldDisplayIntf;
import gpf.dc.fe.dto.modelextend.FormModelDisplayIntf;
import gpf.dto.model.TmpltAllowModifyFieldSetting;

public class GpfDCFeSelect extends FeSelectUtil{
	
	
	public static PairDto<String, String>[] getFormFieldDataTypeSelectItems(){
		List<DataType> ignoreDataTypes = Arrays.asList(DataType.Binary,DataType.Image,DataType.SubSheet,DataType.KeyFormField,DataType.SelectFormField,DataType.SelectSheetField,DataType.SelectNode);
		List<PairDto> items = new ArrayList<>() ;
	    for(int i =0;i<DataType.values().length;i++) {
	    	DataType type = DataType.values()[i];
	    	if(ignoreDataTypes.contains(type))
	    		continue;
	    	items.add(new PairDto<>(type.name(),GpfDCFeI18n.getString(type.name())));
	    }
	    return ToolUtilities.list2Array(items, PairDto.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static PairDto<String, String>[] getEnumItems(Class<? extends Enum> clazz,boolean labelWithPrefix){
		Enum[] enums = clazz.getEnumConstants();
		PairDto<String, String>[] items = new PairDto[enums.length] ;
		for(int i =0;i<enums.length;i++) {
			Enum type = enums[i];
			String cnLabel = null;
			if(labelWithPrefix)
				cnLabel = getEnumCnLabel(type);
			else
				cnLabel = GpfDCFeI18n.getString(type.name());
	    	items[i] = new PairDto<>(type.name(),cnLabel);
	    }
		return items;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static PairDto<String, String>[] getEnumItemsInGroup(Class<? extends Enum> clazz,AbsI18n i18n){
		Enum[] enums = clazz.getEnumConstants();
		PairDto<String, String>[] items = new PairDto[enums.length] ;
		for(int i =0;i<enums.length;i++) {
			Enum type = enums[i];
			String cnLabel = i18n.formatInGroup(type.name(), clazz.getSimpleName());
	    	items[i] = new PairDto<>(type.name(),cnLabel);
	    }
		return items;
	}
	
	@SuppressWarnings("rawtypes")
	public static String getEnumCnLabel(Class<? extends Enum> clazz,String name) {
		Enum[] enums = clazz.getEnumConstants();
		for(int i =0;i<enums.length;i++) {
			Enum type = enums[i];
			if(CmnUtil.isStringEqual(type.name(), name)){
				return getEnumCnLabel(type);
			}
	    }
		return name;
	}
	
	@SuppressWarnings("rawtypes")
	public static String getEnumCnLabel(Enum e) {
		return GpfDCFeI18n.getString(e.getClass().getSimpleName()+"_"+e.name());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static PairDto<String, String>[] getEnumItemsByGroup(Class<? extends Enum> clazz){
		Enum[] enums = clazz.getEnumConstants();
		PairDto<String, String>[] items = new PairDto[enums.length] ;
		for(int i =0;i<enums.length;i++) {
			Enum type = enums[i];
			String cnLabel = getEnumCnLabelByGroup(type);
	    	items[i] = new PairDto<>(type.name(),cnLabel);
	    }
		return items;
	}
	
	@SuppressWarnings("rawtypes")
	public static String getEnumCnLabelByGroup(Enum e) {
		return GpfDCFeI18n.get().formatInGroup(e.name(), e.getClass().getSimpleName());
	}
	
	/**
	 * 构建允许修改实例参数读写配置树
	 * @param parentPath
	 * @param model
	 * @param orgFieldSettings
	 * @return
	 * @throws Exception
	 */
	public static List<TmpltAllowModifyFieldSetting> getAllModifyFieldTree(String parentPath,ActionModel model,List<TmpltAllowModifyFieldSetting> orgFieldSettings,Set<String> excludeFields) throws Exception{
		 List<FormField> fields = model.getFieldList();
		 List<String> hiddendFields = model.getHiddenFields();
		 List<TmpltAllowModifyFieldSetting> allModifyFieldSettings = new ArrayList<>();
		 Map<String,TmpltAllowModifyFieldSetting> orgModifyFieldSettingMap = TmpltAllowModifyFieldSetting.toMap(orgFieldSettings);
		 for(FormField field : fields) {
			 if(excludeFields.contains(field.getCode()))
				 continue;
			 if(hiddendFields.contains(field.getCode()))
				 continue;
			 String path = CmnUtil.getString(parentPath,"")+"/"+field.getCode();
			 DataType display = field.getDataTypeEnum();
			 TmpltAllowModifyFieldSetting fieldSetting = new TmpltAllowModifyFieldSetting();
			 TmpltAllowModifyFieldSetting orgFieldSetting = orgModifyFieldSettingMap.get(field.getCode());
			 if(orgFieldSetting != null) {
				 fieldSetting.setWritable(orgFieldSetting.isWritable());
				 fieldSetting.setVisible(orgFieldSetting.isVisible());
			 }
			 fieldSetting.setCode(field.getCode());
			 fieldSetting.setName(field.getName());
			 fieldSetting.setParentPath(parentPath);
			 fieldSetting.setPath(path);
			 if(display == DataType.NestingModel) {
				 List<TmpltAllowModifyFieldSetting> orgEmbedFieldSettings = null;
				 if(orgFieldSetting != null) {
					 orgEmbedFieldSettings = orgFieldSetting.getChildFieldSettings();
				 }
				 FormModel embedModel = IFormMgr.get().queryFormModel(field.getTableFormModel());
				 List<TmpltAllowModifyFieldSetting> childFieldSettings = getAllModifyFieldTree(path,embedModel, orgEmbedFieldSettings,new HashSet<>());
				 fieldSetting.setChildFieldSettings(childFieldSettings);
			 }
			 allModifyFieldSettings.add(fieldSetting);
		 }
		 return allModifyFieldSettings;
	 }
	
	/**
	 * 构建允许修改实例参数读写配置树
	 * @param parentPath
	 * @param model
	 * @param orgFieldSettings
	 * @return
	 * @throws Exception
	 */
	public static List<TmpltAllowModifyFieldSetting> getAllModifyFieldTree(String parentPath,FormModel model,List<TmpltAllowModifyFieldSetting> orgFieldSettings,Set<String> excludeFields) throws Exception{
		 List<FormField> fields = model.getFieldList();
		 List<String> hiddendFields = model.getHiddenFields();
		 List<TmpltAllowModifyFieldSetting> allModifyFieldSettings = new ArrayList<>();
		 Map<String,TmpltAllowModifyFieldSetting> orgModifyFieldSettingMap = TmpltAllowModifyFieldSetting.toMap(orgFieldSettings);
		 for(FormField field : fields) {
			 if(excludeFields.contains(field.getCode()))
				 continue;
			 if(hiddendFields.contains(field.getCode()))
				 continue;
			 String path = CmnUtil.getString(parentPath,"")+"/"+field.getCode();
			 DataType display = field.getDataTypeEnum();
			 TmpltAllowModifyFieldSetting fieldSetting = new TmpltAllowModifyFieldSetting();
			 TmpltAllowModifyFieldSetting orgFieldSetting = orgModifyFieldSettingMap.get(field.getCode());
			 if(orgFieldSetting != null) {
				 fieldSetting.setWritable(orgFieldSetting.isWritable());
				 fieldSetting.setVisible(orgFieldSetting.isVisible());
			 }
			 fieldSetting.setCode(field.getCode());
			 fieldSetting.setName(field.getName());
			 fieldSetting.setParentPath(parentPath);
			 fieldSetting.setPath(path);
			 if(display == DataType.NestingModel) {
				 List<TmpltAllowModifyFieldSetting> orgEmbedFieldSettings = null;
				 if(orgFieldSetting != null) {
					 orgEmbedFieldSettings = orgFieldSetting.getChildFieldSettings();
				 }
				 FormModel embedModel = IFormMgr.get().queryFormModel(field.getTableFormModel());
				 List<TmpltAllowModifyFieldSetting> childFieldSettings = getAllModifyFieldTree(path,embedModel, orgEmbedFieldSettings,new HashSet<>());
				 fieldSetting.setChildFieldSettings(childFieldSettings);
			 }
			 allModifyFieldSettings.add(fieldSetting);
		 }
		 return allModifyFieldSettings;
	 }
	
	public static PairDto[] getFormModelExtendOptions(FormModel model) throws Exception {
		Set<Class> classes = ClassFactory.searchSubClassInPackage(ClassFactory.getValidClassLoader(), "", BaseFormModelExtend.class);
//		Set<Class> classes = ClassFactory.searchDynamicSubClass(BaseFormFieldExtend.class);
		List<PairDto> items = new ArrayList<>();
		for(Class clazz : classes) {
			if(!FormModelDisplayIntf.class.isAssignableFrom(clazz)) {
				continue;
			}
			if(Modifier.isAbstract(clazz.getModifiers())) {
				continue;
			}
			FormModelDisplayIntf inst = (FormModelDisplayIntf) clazz.newInstance();
			if(!inst.accept(model)) {
				continue;
			}
			String key = clazz.getName();
			String value = clazz.getSimpleName();
			Comment comment = (Comment) clazz.getAnnotation(Comment.class);
			if(comment != null) {
				value = comment.value();
			}
			items.add(new PairDto(key, value));
		}
		return ToolUtilities.list2Array(items, PairDto.class);
	}
	
	public static PairDto[] filterItems(PairDto[] items,String keyword) {
		if(items == null || CmnUtil.isStringEmpty(keyword))
			 return items;
		keyword = keyword.toLowerCase();
		List<PairDto> fitlerItems = new ArrayList<>();
		for(PairDto item : items) {
			if(item.getKey().toString().toLowerCase().contains(keyword)
					|| item.getValue().toString().toLowerCase().contains(keyword))
				fitlerItems.add(item);
		}
		return ToolUtilities.list2Array(fitlerItems, PairDto.class);
	}
	
	public static PairDto[] getFormFieldExtendOptions(FormField field) throws Exception {
		DataType type = DataType.fromValue(field.getDataType());
		if(type == null)
			return new PairDto[] {};
		Set<Class> classes = ClassFactory.searchSubClassInPackage(ClassFactory.getValidClassLoader(), "", BaseFormFieldExtend.class);
//		Set<Class> classes = ClassFactory.searchDynamicSubClass(BaseFormFieldExtend.class);
		List<PairDto> items = new ArrayList<>();
		for(Class clazz : classes) {
			if(!FormFieldDisplayIntf.class.isAssignableFrom(clazz)) {
				continue;
			}
			if(Modifier.isAbstract(clazz.getModifiers())) {
				continue;
			}
			FormFieldDisplayIntf inst = (FormFieldDisplayIntf) clazz.newInstance();
			if(!inst.accept(field)) {
				continue;
			}
			String key = clazz.getName();
			String value = clazz.getSimpleName();
			ClassDeclare comment = (ClassDeclare) clazz.getAnnotation(ClassDeclare.class);
			if(comment != null) {
				value = comment.label()+"("+clazz.getSimpleName()+")";
			}
			items.add(new PairDto(key, value));
		}
		return ToolUtilities.list2Array(items, PairDto.class);
	}
	
	public static PairDto[] getFormFieldDataTransClassOptions(String dataType) throws Exception {
		DataType type = DataType.fromValue(dataType);
		Set<Class> classes = ClassFactory.searchSubClassInPackage(ClassFactory.getValidClassLoader(), "", FormFieldDataTransIntf.class);
//		Set<Class> classes = ClassFactory.searchDynamicSubClass(FormFieldDataTransIntf.class);
		List<PairDto> items = new ArrayList<>();
		for(Class clazz : classes) {
			if(!FormFieldDataTransIntf.class.isAssignableFrom(clazz))
				continue;
			if(type != null) {
				FormFieldDataTransIntf inst = (FormFieldDataTransIntf) clazz.newInstance();
				if(!inst.accept(type)) {
					continue;
				}
			}
			String key = clazz.getName();
			String value = clazz.getSimpleName();
			ClassDeclare comment = (ClassDeclare) clazz.getAnnotation(ClassDeclare.class);
			if(comment != null) {
				value = comment.label()+"("+clazz.getSimpleName()+")";
			}
			items.add(new PairDto(key, value));
		}
		return ToolUtilities.list2Array(items, PairDto.class);
	}
	
	public static List<PairDto> getCustomImageItems() throws Exception {
		Set<Class> classes = ClassFactory.searchSubClassInPackage(ClassFactory.getValidClassLoader(), "", CustomImageIntf.class);
		List<PairDto> items = new ArrayList<>();
		for(Class clazz : classes) {
			String key = clazz.getName();
			String value = clazz.getSimpleName();
			Comment comment = (Comment) clazz.getAnnotation(Comment.class);
			if(comment != null) {
				value = comment.value();
			}
			items.add(new PairDto(key, value));
		}
		return items;
	}
	

}
