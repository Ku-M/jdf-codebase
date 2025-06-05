package gpf.dc.basic.util;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.kwaidoo.ms.tool.ToolUtilities;
import com.leavay.common.util.Utils;
import com.leavay.common.util.javac.ClassFactory;
import com.leavay.ms.tool.CmnUtil;

import cell.cmn.IJson;
import cell.cmn.IJsonService;
import cell.cmn.util.IServerConfig;
import cell.fe.gpf.dc.basic.IAppCacheMgr;
import cmn.dto.PreloadTreeNode;
import cmn.reflect.TypeToken;
import cmn.util.ClassUtil;
import cmn.util.StringUtil;
import cmn.util.TraceUtil;
import cmn.util.Tracer;
import fe.cmn.data.UrlMsgDto;
import fe.cmn.panel.PanelContext;
import fe.cmn.sys.QueryUrl;
import fe.cmn.sys.QueryWebSocketUrl;
import gpf.adur.data.DataType;
import gpf.adur.data.Form;
import gpf.adur.data.FormField;
import gpf.dc.basic.fe.intf.AppCacheMgrIntf;
import gpf.dc.basic.i18n.GpfDCBasicI18n;
import gpf.dc.basic.param.view.dto.FormFieldDefine;
import gpf.dc.basic.param.view.dto.SearchBarDefine;
import gpf.dc.basic.param.view.dto.SettingItemDto;
import gpf.dc.basic.param.view.dto.TableColumnDefine;
import gpf.exception.VerifyException;

public class GpfDCBasicUtil {

	public static List<InetAddress> addressList = null;

	public static void checkAllowWebAccess(PanelContext context,AppCacheMgrIntf cacheMgr) throws Exception {
		
		UrlMsgDto urlMsgDto = null;
		String wsUrl = null;
		if(cacheMgr != null) {
			urlMsgDto = (UrlMsgDto) cacheMgr.getCacheValue(context, IAppCacheMgr.UrlMsg);
			//FIXME 临时处理有可能缓存拿不到值的情况，后续要看看怎么追加保证缓存有值
			if(urlMsgDto == null) {
				urlMsgDto = QueryUrl.queryUrlMsg(context.getChannel());
				cacheMgr.setCacheValue(context, IAppCacheMgr.UrlMsg, urlMsgDto);
			}
			wsUrl = (String) cacheMgr.getCacheValue(context, IAppCacheMgr.WebSocketUrl);
			if(wsUrl == null) {
				wsUrl = QueryWebSocketUrl.query(context.getChannel());
				cacheMgr.setCacheValue(context, IAppCacheMgr.WebSocketUrl, wsUrl);
			}
		}else {
			urlMsgDto = QueryUrl.queryUrlMsg(context.getChannel());
			wsUrl = QueryWebSocketUrl.query(context.getChannel());
		}
		Tracer tracer = TraceUtil.getCurrentTracer();
		String whiteList = IServerConfig.get().getString("web.access.whitelist", "");//IServerConfig.get().getWebAccessWhiteList();
		tracer.info("Web Access White List = " + whiteList);
		System.out.println("web访问白名单："+whiteList);
		String errorMsg = GpfDCBasicI18n.TIPS_PAGE_ACCESS_DENY;
		String hostOrigin = urlMsgDto.getHost();
		String pathOrigin = urlMsgDto.getPathname();
		if (hostOrigin == null) {
			throw new VerifyException(errorMsg);
		}
		String httpHost = null;
		int httpPort = -1;
		String[] hostArr = hostOrigin.split(":");
		if(hostArr.length == 1) {
			httpHost = hostOrigin;
		}else if(hostArr.length == 2) {
			httpHost = hostArr[0];
			httpPort = ToolUtilities.getInteger(hostArr[1]);
		}
		int httpConfPort = IServerConfig.get().getHttpPort();
		//TODO 获取websocket的方式需要调整
		String wsHost = null;
		int wsPort = -1;
//  List<String> channelInfo = context.getChannel().getChannelInfoOfCache();
//  //[[id: 0x1b3a169c, L:/127.0.0.1:2020 - R:/127.0.0.1:65074]]
//  String info = channelInfo.get(0);
//  int startIdx = info.indexOf("L:/")+3;
//  int endIdx = info.indexOf(" - R:/");
//  String wsUrl = info.substring(startIdx, endIdx);
		if(wsUrl.indexOf("://") < 0) {
			throw new VerifyException(errorMsg);
		}
		String[] arr = wsUrl.split("://")[1].split(":");
		if(arr.length == 2) {
			wsHost = arr[0];
			wsPort = ToolUtilities.getInteger(arr[1]);
		}else if(arr.length == 1){
			wsHost = arr[0];
		}
		int wsConfPort = IServerConfig.get().getRpcPort();
		//如果http请求端口和websocket请求端口都与服务配置是一致的，那么检查host是否相同，相同则允许访问
		if(httpPort == httpConfPort && wsPort == wsConfPort) {
			if(CmnUtil.isStringEqual(wsHost, httpHost)) {
				return;
			}else {
				List<String> localhosts = Arrays.asList("localhost","127.0.0.1");
				if(localhosts.contains(wsHost) && localhosts.contains(httpHost)) {
					return;
				}
				//检查wsHost 或者httpHost 是否127.0.0.1，检查host是否在本机IP列表中
				if(addressList == null)
					addressList = ToolUtilities.getAllAddress(true);
				for(InetAddress address : addressList) {
					tracer.info("检查地址：" + address.getHostAddress());
					if(CmnUtil.isStringEqual(address.getHostAddress(), httpHost))
						return;
				}
			}
		}
		//检查不通过时，需要检查白名单中是否包含当前访问地址
		if(!CmnUtil.isStringEmpty(whiteList)) {
			String[] allowHosts = whiteList.split(",");
			//先通过文本比对，文本比对没找到时，再通过正则匹配
			for(String allowHost : allowHosts) {
				if(CmnUtil.isStringEqual(allowHost.trim(), hostOrigin)) {
					return;
				}
			}
			
			for(String allowHost : allowHosts) {
				if(hostOrigin.matches(allowHost.trim())) {
					return;
				}
			}
		}
		throw new VerifyException(errorMsg+"当前地址["+hostOrigin+"]，websocket地址["+wsUrl+"]");
	}
	
	public static <T> T getSetting(T setting,List<Map<String,String>> settingProps) throws Exception {
//		T setting = settingClazz.newInstance();
		Map<String,Field> fieldMap = ToolUtilities.getAllFieldMap(setting.getClass(), false);
		if(settingProps != null) {
			for(Map<String,String> settingRow : settingProps) {
				String key = (String) settingRow.get("key");
				String value = (String) settingRow.get("value");
				if(fieldMap.containsKey(key)) {
					Field f = fieldMap.get(key);
					setFieldValue(setting, f, value);
				}
			}
		}
		return setting;
	}

	public static void setFieldValue(Object setting,Field f,String value) {
		try {
			if(f.getType() == Integer.class || f.getType() == int.class) {
				ToolUtilities.setFieldValue(setting, f.getName(), CmnUtil.getInteger(value));
			}else if(f.getType() == Long.class || f.getType() == long.class) {
				ToolUtilities.setFieldValue(setting, f.getName(), CmnUtil.getLong(value));
			}else if(f.getType() == Double.class || f.getType() == double.class) {
				ToolUtilities.setFieldValue(setting, f.getName(), CmnUtil.getDouble(value));
			}else if(f.getType() == Float.class || f.getType() == float.class) {
				ToolUtilities.setFieldValue(setting, f.getName(), CmnUtil.getFloat(value));
			}else if(f.getType() == Boolean.class || f.getType() == boolean.class) {
				ToolUtilities.setFieldValue(setting, f.getName(), CmnUtil.getBoolean(value));
			}else {
				ToolUtilities.setFieldValue(setting, f.getName(), value);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final static List<FormFieldDefine> mergeFormField(List<FormField> fields,List<FormFieldDefine> formFieldDefines)throws Exception{
		Map<String,FormFieldDefine> formFieldDefMap = new LinkedHashMap<>();
		for(FormFieldDefine fieldDef : formFieldDefines) {
			formFieldDefMap.put(fieldDef.getCode(), fieldDef);
		}
		List<FormFieldDefine> megerFielDefs = new ArrayList<>();
		for(FormField field : fields) {
			FormFieldDefine fieldDef = new FormFieldDefine();
			ToolUtilities.copyFields(field, fieldDef);
			if(formFieldDefMap.containsKey(field.getCode())) {
				FormFieldDefine configFieldDef = formFieldDefMap.get(field.getCode());
				fieldDef.setAlias(configFieldDef.getAlias());
				fieldDef.setTips(configFieldDef.getTips());
				fieldDef.setComponentExpandInBox(configFieldDef.isComponentExpandInBox());
				fieldDef.setComponentWitdh(configFieldDef.getComponentWitdh());
				fieldDef.setComponentAlign(configFieldDef.getComponentAlign());
				fieldDef.setComponentHeight(configFieldDef.getComponentHeight());
				fieldDef.setComponentStyle(configFieldDef.getComponentStyle());
				fieldDef.setLabelStyle(configFieldDef.getLabelStyle());
				fieldDef.setLabelWidth(configFieldDef.getLabelWidth());
				fieldDef.setDataFilterFuncs(configFieldDef.getDataFilterFuncs());
				fieldDef.setInitValue(configFieldDef.getInitValue());
				fieldDef.setTagGroupIds(configFieldDef.getTagGroupIds());
				if(configFieldDef.getExtendInfo() != null)
					fieldDef.setExtendInfo(configFieldDef.getExtendInfo());
			}
			megerFielDefs.add(fieldDef);
		}
		return megerFielDefs;
	}

	public final static List<SearchBarDefine> mergeSearchBarField(List<FormField> fields,List<SearchBarDefine> formFieldDefines)throws Exception{
		Map<String,SearchBarDefine> formFieldDefMap = new LinkedHashMap<>();
		for(SearchBarDefine fieldDef : formFieldDefines) {
			formFieldDefMap.put(fieldDef.getCode(), fieldDef);
		}
		List<SearchBarDefine> megerFielDefs = new ArrayList<>();
		for(FormField field : fields) {
			SearchBarDefine fieldDef = new SearchBarDefine(field.getCode());
			ToolUtilities.copyFields(field, fieldDef);
			if(formFieldDefMap.containsKey(field.getCode())) {
				SearchBarDefine configFieldDef = formFieldDefMap.get(field.getCode());
				fieldDef.setAlias(configFieldDef.getAlias());
				fieldDef.setLabelLayoutStyle(configFieldDef.getLabelLayoutStyle());
				fieldDef.setAdvanceLabelLayoutStyle(configFieldDef.getAdvanceLabelLayoutStyle());
				fieldDef.setTips(configFieldDef.getTips());
				fieldDef.setLabelWidth(configFieldDef.getLabelWidth());
				fieldDef.setComponentWidth(configFieldDef.getComponentWidth());
				fieldDef.setShowLocations(configFieldDef.getShowLocations());
				if(configFieldDef.getExtendInfo() != null)
					fieldDef.setExtendInfo(configFieldDef.getExtendInfo());
				fieldDef.setComponentStyle(configFieldDef.getComponentStyle());
				fieldDef.setDataFilterFuncs(configFieldDef.getDataFilterFuncs());
				fieldDef.setCustom(configFieldDef.getCustom());
				fieldDef.setExtFieldDataType(configFieldDef.getExtFieldDataType());
				fieldDef.setExtAssocFormModel(configFieldDef.getExtAssocFormModel());
			}
			//关联默认用多选模式
			if(field.getDataTypeEnum() ==DataType.Relate)
				fieldDef.setAssocMultiSelect(true);
			megerFielDefs.add(fieldDef);
		}
		return megerFielDefs;
	}

	public static List<FormFieldDefine> mergeFormFieldByTableColumnDefines(List<FormField> fields,List<TableColumnDefine> tableColDefines)throws Exception{
		Map<String,TableColumnDefine> formFieldDefMap = new LinkedHashMap<>();
		for(TableColumnDefine fieldDef : tableColDefines) {
			formFieldDefMap.put(fieldDef.getCode(), fieldDef);
		}
		List<FormFieldDefine> megerFielDefs = new ArrayList<>();
		for(FormField field : fields) {
			FormFieldDefine fieldDef = new FormFieldDefine();
			ToolUtilities.copyFields(field, fieldDef);
			if(formFieldDefMap.containsKey(field.getCode())) {
				TableColumnDefine configFieldDef = formFieldDefMap.get(field.getCode());
				fieldDef.setAlias(configFieldDef.getAlias());
				fieldDef.setComponentStyle(configFieldDef.getComponentStyle());
				fieldDef.setDataFilterFuncs(configFieldDef.getDataFilterFuncs());
				if(configFieldDef.getExtendInfo() != null)
					fieldDef.setExtendInfo(configFieldDef.getExtendInfo());
			}
			megerFielDefs.add(fieldDef);
		}
		//补充自定义列
		for(TableColumnDefine configFieldDef : tableColDefines) {
			if(configFieldDef.isCustom())
			{
				FormFieldDefine fieldDef = new FormFieldDefine();
				ToolUtilities.copyFields(configFieldDef, fieldDef);
				megerFielDefs.add(fieldDef);
			}
		}
		return megerFielDefs;
	}

	public static List<SettingItemDto> getTableSettingItems() throws Exception{
		URL url = ClassFactory.getResourceURL("resource/TableSetting.json");
		InputStream ins = url.openStream();
		try (IJson json = IJsonService.get().getJson()){
			String content = Utils.getFileContent(ins, "UTF-8");
			Type type = new TypeToken<List<SettingItemDto>>(){}.getType();
			List<SettingItemDto> items = json.fromJsonByType(content, type);
			return items;
		}finally {
			Utils.close(ins);
		}
	}

	public static List<SettingItemDto> getAppViewSettingItems() throws Exception{
		URL url = ClassFactory.getResourceURL("resource/ApplicationSetting.json");
		InputStream ins = url.openStream();
		try (IJson json = IJsonService.get().getJson()){
			String content = Utils.getFileContent(ins, "UTF-8");
			Type type = new TypeToken<List<SettingItemDto>>(){}.getType();
			List<SettingItemDto> items = json.fromJsonByType(content, type);
			return items;
		}finally {
			Utils.close(ins);
		}
	}

	public static List<SettingItemDto> getTreeSettingItems() throws Exception{
		URL url = ClassFactory.getResourceURL("resource/TreeSetting.json");
		InputStream ins = url.openStream();
		try (IJson json = IJsonService.get().getJson()){
			String content = Utils.getFileContent(ins, "UTF-8");
			Type type = new TypeToken<List<SettingItemDto>>(){}.getType();
			List<SettingItemDto> items = json.fromJsonByType(content, type);
			return items;
		}finally {
			Utils.close(ins);
		}
	}

	public static List<SettingItemDto> getCardSettingItems() throws Exception{
		URL url = ClassFactory.getResourceURL("resource/CardSetting.json");
		InputStream ins = url.openStream();
		try (IJson json = IJsonService.get().getJson()){
			String content = Utils.getFileContent(ins, "UTF-8");
			Type type = new TypeToken<List<SettingItemDto>>(){}.getType();
			List<SettingItemDto> items = json.fromJsonByType(content, type);
			return items;
		}
	}

	public static List<SettingItemDto> getFormSettingItems() throws Exception{
		URL url = ClassFactory.getResourceURL("resource/FormSetting.json");
		InputStream ins = url.openStream();
		try (IJson json = IJsonService.get().getJson()){
			String content = Utils.getFileContent(ins, "UTF-8");
			Type type = new TypeToken<List<SettingItemDto>>(){}.getType();
			List<SettingItemDto> items = json.fromJsonByType(content, type);
			return items;
		}finally {
			Utils.close(ins);
		}
	}

	public static <T extends Form> List<PreloadTreeNode<T>> buildPreloadTree(List<T> children,String parentKeyFieldName) throws Exception {
		Map<String, PreloadTreeNode<T>> map = new LinkedHashMap<>();
		if(children != null) {
			for (T treeNode : children) {
				PreloadTreeNode<T> preloadNode = new PreloadTreeNode<T>().setNode(treeNode);
				map.put(treeNode.getUuid(), preloadNode);
			}
		}
		List<PreloadTreeNode<T>> preloadChildrens = new ArrayList<>();
		if(children != null) {
			for (T treeNode : children) {
				PreloadTreeNode<T> pNode = map.get(treeNode.getString(parentKeyFieldName));
				PreloadTreeNode<T> node = map.get(treeNode.getUuid());
				if (pNode == null)
					preloadChildrens.add(node);
				else {
					List<PreloadTreeNode<T>> childChildren = pNode.getChildrens();
					if (childChildren == null) {
						childChildren = new ArrayList<>();
						pNode.setChildrens(childChildren);
					}
					childChildren.add(node);
				}
			}
		}
		return preloadChildrens;
	}
	
	/**
	 * 对目标对象中的文本属性进行去空格处理
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static <T> T trimObject(T object)throws Exception{
		if(object == null)
			return null;
		if(object instanceof String)
			return (T) StringUtil.trim(object.toString());
		else if(object.getClass().isPrimitive() || ClassUtil.isPrimitiveWrapper(object.getClass()))
			return object;
		List<Field> fields = ToolUtilities.getAllField(object.getClass(), false);
		for(Field field : fields) {
			Class type = field.getType();
			if(type == String.class) {
				String value = (String) ToolUtilities.getFieldValue(object, field);
				if(value != null) {
					String trimValue = StringUtil.trim(value);
					ToolUtilities.setFieldValue(object, field, trimValue);
				}
			}else if(CmnUtil.isInheritFrom(type, List.class)) {
				List list = (List)ToolUtilities.getFieldValue(object, field);
				if(list != null) {
					List copyList = new ArrayList<>(list);
					for(int i =0;i<copyList.size();i++) {
						Object ele = copyList.get(i);
						Object trimEle = trimObject(ele);
						list.set(i, trimEle);
					}
				}
			}else if(type.isArray()) {
				// 遍历数组
                Object array = ToolUtilities.getFieldValue(object, field);
                if (array != null)
                {
                    int len = Array.getLength(array);
                    for (int i = 0; i < len; i++)
                    {
                    	Object ele = Array.get(array, i);
                    	Object trimEle = trimObject(ele);
                    	Array.set(array, i, trimEle);
                    }
                }
			} else if (CmnUtil.isInheritFrom(type, Map.class))
            {
                Map map = (Map) ToolUtilities.getFieldValue(object, field);
                if(map!= null) {
	                Map copyMap = new LinkedHashMap(map);
	                for (Object key : copyMap.keySet())
	                {
	                	Object value = copyMap.get(key);
	                	Object trimValue = trimObject(value);
	                	Object trimKey = trimObject(key);
	                	map.remove(key);
	                	map.put(trimKey, trimValue);
	                }
                }
            } 
		}
		return object;
	}

}
