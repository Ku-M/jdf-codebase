package gpf.dc.basic.util;

import java.awt.Color;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;

import com.kwaidoo.ms.tool.CmnUtil;
import com.kwaidoo.ms.tool.ToolUtilities;
import com.leavay.common.util.Utils;
import com.leavay.common.util.javac.ClassFactory;

import cmn.util.Nulls;
import fe.cmn.FeUtil;
import fe.cmn.data.CColor;
import fe.cmn.data.KeyboardDto;
import fe.cmn.data.KeyboardPressType;
import fe.cmn.panel.BoxDto;
import fe.cmn.panel.CrossAxisAlign;
import fe.cmn.panel.MainAxisAlign;
import fe.cmn.panel.PanelDto;
import fe.cmn.text.CFontWeight;
import fe.cmn.text.CTextStyle;
import fe.cmn.widget.CLabelAlign;
import fe.cmn.widget.ImageDto;
import fe.cmn.widget.LabelDto;
import fe.cmn.widget.SizeDto;
import fe.cmn.widget.WidgetDto;
import fe.cmn.widget.decoration.DecorationDto;
import fe.cmn.widget.decoration.FilterQualityType;
import fe.cmn.widget.decoration.FitType;
import fe.cmn.widget.decoration.ImageDecorationDto;
import fe.cmn.widget.decoration.LabelDecorationDto;
import fe.cmn.widget.decoration.MouseCursorType;
import fe.cmn.widget.decoration.ShapeType;
import fe.cmn.widget.listener.ListenerFeedbackDto;
import fe.cmn.widget.listener.OnClickListener;
import fe.util.component.Component;
import fe.util.component.param.WidgetParam;
import fe.util.style.FeStyleSetting;
import gpf.dc.basic.fe.component.view.ViewListenerBuilder;
import gpf.dc.basic.fe.enums.ListenerApplyLocation;
import gpf.dc.basic.fe.enums.ListenerType;
import gpf.dc.basic.param.view.dto.ButtonDefine;
import gpf.dc.basic.param.view.dto.ListenerDefine;

public class GpfDCBasicFeUtil extends FeUtil{
	
	/**
	 * 16进制转颜色，格式 : #FFABABAB
	 * 1-2位：透明度
	 * 3-4位：R
	 * 5-6位：G
	 * 7-8位：B
	 * @param hexString
	 * @return
	 * @throws Exception 
	 */
	public static CColor hex2CColor(String hexString) throws Exception {
		if(!hexString.startsWith("#") && hexString.length() != 9) {
			throw new Exception("HEX Format invalid! Example : #FFABABAB");
		}
		int alpha = CmnUtil.getInteger(ToolUtilities.hex2Dec(hexString.substring(1, 3)));
		int r = CmnUtil.getInteger(ToolUtilities.hex2Dec(hexString.substring(3, 5)));
		int g = CmnUtil.getInteger(ToolUtilities.hex2Dec(hexString.substring(5, 7)));
		int b = CmnUtil.getInteger(ToolUtilities.hex2Dec(hexString.substring(7, 9)));
		return CColor.rgba(r, g, b, alpha);
	}
	
	public static String CColor2Hex(CColor color)throws Exception{
		String alphaHex = paddingHex(ToolUtilities.dec2Hex(CmnUtil.getInteger(color.getOpacity()*255)+""));
		String rHex = paddingHex(ToolUtilities.dec2Hex(color.getR()+""));
		String gHex = paddingHex(ToolUtilities.dec2Hex(color.getG()+""));
		String bHex = paddingHex(ToolUtilities.dec2Hex(color.getB()+""));
		String hex = "#"+alphaHex+rHex+gHex+bHex;
		return hex.toUpperCase();
	}
	
	public static String paddingHex(String hexStr) {
		if(hexStr.length() == 1) {
			return "0"+hexStr;
		}
		return hexStr;
	}
	
	public static void main(String[] args) throws Exception {
		List<WidgetDto> matchWidgets = new ArrayList<>();
		Set<String> groupIdSet = new LinkedHashSet<>();
		groupIdSet.add("行业标签ID");
		byte[] bytes = Utils.getFileBytes("D:/panel.data");
		PanelDto panel = (PanelDto) ToolUtilities.unserialize(bytes);
		GpfDCBasicFeUtil.searchWidget(panel, v->{
//			if(fieldBoxCodes.contains(v.getWidgetId()))
//				return false;
//			if(buttons.contains(v.getWidgetId()))
//				return false;
			String[] groupIds = v.getGroupIds();
			if(groupIds != null) {
				for(String groupId : groupIds) {
					if(groupIdSet.contains(groupId))
						return true;
				}
			}
			return false;
		},matchWidgets);
		System.out.println(matchWidgets.size());
	}
	
	public static void setOnClickListenerFeedback(FeStyleSetting setting,OnClickListener onClickListener) {
		CColor mainColor = setting.getMainColor();
        onClickListener.setFeedback(ListenerFeedbackDto.borderFeedback(mainColor, 1.0));
	}
	
	public static void setBorder(WidgetDto widget) {
//		BorderSideDto bottomBorder = new BorderSideDto(CColor.fromColor(Color.BLUE), 0);
//		BorderDto border = new BorderDto().setTop(bottomBorder).setRight(bottomBorder);
		DecorationDto decoration = DecorationDto
				.borderAll(Color.GRAY, 0).setMargin(5.0).setPadding(0);
		widget.setDecoration(decoration);
	}


	public static LabelDecorationDto getLabelDecorationDto(double fontSize, boolean isFontWeight, Color color) {
		return getLabelDecorationDto(fontSize, isFontWeight, CLabelAlign.CENTER, color);
	}

	public static LabelDecorationDto getLabelDecorationDto(double fontSize, boolean isFontWeight, CLabelAlign align, Color color) {
		return (LabelDecorationDto) new LabelDecorationDto().setAlign(align).setTextStyle(getTextStyle(fontSize, isFontWeight, color, 1));
	}

	public static CTextStyle getTextStyle(double fontSize, boolean isFontWeight) {
		return getTextStyle(fontSize, isFontWeight, Color.WHITE, 1);
	}

	public static CTextStyle getTextStyle(double fontSize, boolean isFontWeight, Color color, double height) {
		CTextStyle textStyle = new CTextStyle()
//				.setFontFamily(fontFamily)
				.setFontSize(fontSize).setColor(color).setHeight(height);
		if (isFontWeight) {
			textStyle.setFontWeight(CFontWeight.bold);
		}
		return textStyle;
	}

	public static ImageDto createImage(String src, double scale) {
		ImageDecorationDto imageDecorationDto = new ImageDecorationDto().setFit(FitType.contain).setScale(scale).setFilterQuality(FilterQualityType.medium);
				imageDecorationDto.setMouseCursorType(MouseCursorType.click);
		return new ImageDto(src).setDecoration(imageDecorationDto);
	}

	public static BoxDto getCenterBox(WidgetDto widgetDto) {
		return BoxDto.vbar(widgetDto.setExpandInBox(true)).setMainAxisAlignment(MainAxisAlign.center).setCrossAxisAlignment(CrossAxisAlign.center);
	}

	/**
	 *功能描述 得到一个矩形带颜色盒子
	 * @author Chand
	 * @date 2022/11/16
	 * @param wide
	 * @param high
	 * @param color
	 * @return fe.cmn.panel.BoxDto
	 */
	public static BoxDto createRectangle(double wide, double high, CColor color) {
		BoxDto boxDto = BoxDto.hbar(
				new LabelDto("")
		);
		boxDto.setDecoration(DecorationDto.background(color));
		boxDto.setPreferSize(new SizeDto(wide, high));
		return boxDto;
	}

	/**
	 *功能描述 得到一个圆形带颜色盒子
	 * @author Chand
	 * @date 2022/11/16
	 * @param diameter
	 * @param color
	 * @return fe.cmn.panel.BoxDto
	 */
	public static BoxDto createcircular(double diameter, CColor color) {
		BoxDto boxDto = BoxDto.hbar(
				new LabelDto("")
		);
		boxDto.setDecoration(DecorationDto.background(color).setShape(ShapeType.circle));
		boxDto.setPreferSize(new SizeDto(diameter, diameter));
		return boxDto;
	}
	
	public static Component<WidgetParam> newComponentInstance(String compomentClass,WidgetParam widgetParam) throws Exception{
		Class clazz = ClassFactory.getValidClassLoader().loadClass(compomentClass);
		if(!Component.class.isAssignableFrom(clazz)) {
			throw new Exception("Class "+compomentClass+"is not implmenets " + Component.class.getName());
		}
		Component inst = (Component) clazz.newInstance();
		inst.setWidgetParam(widgetParam);
		return inst;
	}
	
	
	public static void searchWidget(Object obj, Function<WidgetDto, Boolean> accepter,List<WidgetDto> matchWidgets) 
    {
        if (obj == null)
            return;

        if (obj instanceof WidgetDto && accepter.apply((WidgetDto) obj)) {
        	matchWidgets.add((WidgetDto) obj);
            return;
        }

        for (Field fd : ToolUtilities.getAllField(obj.getClass(), false))
        {
            if (CmnUtil.isInheritFrom(fd.getType(), WidgetDto.class))
            {
                // 递归子字段
                WidgetDto child = (WidgetDto) ToolUtilities.getFieldValue(obj, fd);
                searchWidget(child, accepter,matchWidgets);
//                return;
            } else if (fd.getType().isArray() && CmnUtil.isInheritFrom(fd.getType().getComponentType(), WidgetDto.class))
            {
                // 遍历数组
                Object array = ToolUtilities.getFieldValue(obj, fd);
                if (array != null)
                {
                    int len = Array.getLength(array);
                    for (int i = 0; i < len; i++)
                    {
                        searchWidget(Array.get(array, i), accepter,matchWidgets);
                    }
                }
            } else if (CmnUtil.isInheritFrom(fd.getType(), Map.class))
            {
                Map map = (Map) ToolUtilities.getFieldValue(obj, fd);
                for (Object child : Nulls.get(map).values())
                {
                	searchWidget(child, accepter,matchWidgets);
                }
            } else if (CmnUtil.isInheritFrom(fd.getType(), Iterable.class))
            {
                Iterable lst = (Iterable) ToolUtilities.getFieldValue(obj, fd);
                if (lst != null)
                    for (Object child : lst)
                    {
                    	searchWidget(child, accepter,matchWidgets);
                    }
            }
        }
    }
	
	
	/**
	 * 替换widget模板中的指定的widget为目标widget
	 * @param obj
	 * @param accepter	替换widget的函数
	 * @return
	 * @throws Exception
	 */
	public static Object replaceWidget(Object obj, Function<WidgetDto, WidgetDto> accepter) throws Exception 
    {
        if (obj == null)
            return null;

        if ((obj instanceof WidgetDto)) {
	        Object replaceObj = accepter.apply((WidgetDto) obj);
	        if(replaceObj != null) {
	        	return replaceObj;
	        }
        }

        for (Field fd : ToolUtilities.getAllField(obj.getClass(), false))
        {
            if (CmnUtil.isInheritFrom(fd.getType(), WidgetDto.class))
            {
                // 递归子字段
                WidgetDto child = (WidgetDto) ToolUtilities.getFieldValue(obj, fd);
                WidgetDto replaceChild = (WidgetDto) replaceWidget(child, accepter);
                if(replaceChild != null)
                	ToolUtilities.setFieldValue(obj, fd, replaceChild);
            } else if (fd.getType().isArray() && CmnUtil.isInheritFrom(fd.getType().getComponentType(), WidgetDto.class))
            {
                // 遍历数组
                Object array = ToolUtilities.getFieldValue(obj, fd);
                if (array != null)
                {
                    int len = Array.getLength(array);
                    for (int i = 0; i < len; i++)
                    {
                    	WidgetDto replaceChild = (WidgetDto) replaceWidget(Array.get(array, i), accepter);
                    	if(replaceChild != null)
                    		Array.set(array, i, replaceChild);
                    }
                }
            } else if (CmnUtil.isInheritFrom(fd.getType(), Map.class))
            {
                Map map = (Map) ToolUtilities.getFieldValue(obj, fd);
                if(!CmnUtil.isMapEmpty(map)) {
                	for (Object element : map.entrySet())
                	{
                		Entry entry = (Entry) element;
                		Object entryObj = entry.getValue();
                		Object replaceEntryObj = replaceWidget(entryObj, accepter);
                		if(replaceEntryObj != null)
                			entry.setValue(replaceEntryObj);
                	}
                }
            } else if (CmnUtil.isInheritFrom(fd.getType(), List.class))
            {
            	List lst = (List) ToolUtilities.getFieldValue(obj, fd);
                if (lst != null) {
                	List copyLst = ToolUtilities.clone(lst);
                	for(int i =0;i<copyLst.size();i++) {
                		Object child = copyLst.get(i);
                		Object replaceChild = replaceWidget(child, accepter);
                		if(replaceChild != null) {
                			lst.set(i,replaceChild);
                		}
                	}
                }
            }
        }
        return obj;
    }
	
	public static void collectWidget(Object obj, Function<WidgetDto, Boolean> accepter,Map<String,WidgetDto> collectMap) throws Exception {
		if (obj == null)
			return;
		
		if (obj instanceof WidgetDto && accepter.apply((WidgetDto) obj)) {
			collectMap.put(((WidgetDto)obj).getWidgetId(), (WidgetDto)obj);
		}
		
		for (Field fd : ToolUtilities.getAllField(obj.getClass(), false))
		{
			if (CmnUtil.isInheritFrom(fd.getType(), WidgetDto.class))
			{
				// 递归子字段
				WidgetDto child = (WidgetDto) ToolUtilities.getFieldValue(obj, fd);
				collectWidget(child, accepter,collectMap);
			} else if (fd.getType().isArray() && CmnUtil.isInheritFrom(fd.getType().getComponentType(), WidgetDto.class))
			{
				// 遍历数组
				Object array = ToolUtilities.getFieldValue(obj, fd);
				if (array != null)
				{
					int len = Array.getLength(array);
					for (int i = 0; i < len; i++)
					{
						collectWidget(Array.get(array, i), accepter,collectMap);
					}
				}
			} else if (CmnUtil.isInheritFrom(fd.getType(), Map.class))
			{
				Map map = (Map) ToolUtilities.getFieldValue(obj, fd);
				for (Object child : Nulls.get(map).values())
				{
					collectWidget(child, accepter,collectMap);
				}
			} else if (CmnUtil.isInheritFrom(fd.getType(), Iterable.class))
			{
				Iterable lst = (Iterable) ToolUtilities.getFieldValue(obj, fd);
				if (lst != null)
					for (Object child : lst)
					{
						collectWidget(child, accepter,collectMap);
					}
			}
		}
	}
	
	public static ListenerDefine getListenerDefineByKeyboard(List<ListenerDefine> listenerDefines,KeyboardDto keyboard){
		if(CmnUtil.isCollectionEmpty(listenerDefines))
			return null;
		ListenerDefine matchLsnr = null;
		for(ListenerDefine lsnr : Nulls.get(listenerDefines)) {
			if(lsnr.getListenerTypeEnum() == ListenerType.OnKeyboard) {
				KeyboardDto listernKeyboard = ViewListenerBuilder.toKeyboardDto(lsnr.getCombineKeyCode());
				if(listernKeyboard != null) {
					if(CmnUtil.getBoolean(listernKeyboard.getIsControlPressed(),false) == 
							CmnUtil.getBoolean(keyboard.getIsControlPressed(),false)
							&& CmnUtil.getBoolean(listernKeyboard.getIsAltPressed(),false) == 
									CmnUtil.getBoolean(keyboard.getIsAltPressed(),false)
							&& CmnUtil.getBoolean(listernKeyboard.getIsShiftPressed(),false) == 
									CmnUtil.getBoolean(keyboard.getIsShiftPressed(),false)
							&& listernKeyboard.getType() == KeyboardPressType.down
							&& CmnUtil.isStringEqual(listernKeyboard.getKeyCode(), keyboard.getKeyCode(), true)) {
						matchLsnr = lsnr;
						break;
					}
				}
			}
		}
		return matchLsnr;
	}
	
	public static ButtonDefine getButtonDefineByName(List<ButtonDefine> buttonDefines,String btnName) {
		if(CmnUtil.isCollectionEmpty(buttonDefines))
			return null;
		for(ButtonDefine btnDef : Nulls.get(buttonDefines)) {
			if(CmnUtil.isStringEqual(btnDef.getName(), btnName))
				return btnDef;
		}
		return null;
	}
	
	public static List<ListenerDefine> getViewInitListenerDefines(List<ListenerDefine> listenerDefines,ListenerApplyLocation location) {
		if(CmnUtil.isCollectionEmpty(listenerDefines))
			return null;
		List<ListenerDefine> matchLsnrs = new ArrayList<>();
		for(ListenerDefine lsnr : Nulls.get(listenerDefines)) {
			if(lsnr.getListenerTypeEnum() == ListenerType.ViewInit) {
				List<ListenerApplyLocation> applyLocations = lsnr.getApplyLocationEnums();
				if(applyLocations.isEmpty()) {
					matchLsnrs.add(lsnr);
				}else {
					if(location != null && applyLocations.contains(location)) {
						matchLsnrs.add(lsnr);
					}
				}
			}
		}
		return matchLsnrs;
	}
	
	public static List<ListenerDefine> getViewInitedListenerDefines(List<ListenerDefine> listenerDefines,ListenerApplyLocation location) {
		if(CmnUtil.isCollectionEmpty(listenerDefines))
			return null;
		List<ListenerDefine> matchLsnrs = new ArrayList<>();
		for(ListenerDefine lsnr : Nulls.get(listenerDefines)) {
			if(lsnr.getListenerTypeEnum() == ListenerType.ViewInited) {
				List<ListenerApplyLocation> applyLocations = lsnr.getApplyLocationEnums();
				if(applyLocations.isEmpty()) {
					matchLsnrs.add(lsnr);
				}else {
					if(location != null && applyLocations.contains(location)) {
						matchLsnrs.add(lsnr);
					}
				}
			}
		}
		return matchLsnrs;
	}
	
	public static List<ListenerDefine> getAfterQueryTableRowListenerDefines(List<ListenerDefine> listenerDefines,ListenerApplyLocation location) {
		if(CmnUtil.isCollectionEmpty(listenerDefines))
			return null;
		List<ListenerDefine> matchLsnrs = new ArrayList<>();
		for(ListenerDefine lsnr : Nulls.get(listenerDefines)) {
			if(lsnr.getListenerTypeEnum() == ListenerType.AfterQueryTableRow) {
				List<ListenerApplyLocation> applyLocations = lsnr.getApplyLocationEnums();
				if(applyLocations.isEmpty()) {
					matchLsnrs.add(lsnr);
				}else {
					if(location != null && applyLocations.contains(location)) {
						matchLsnrs.add(lsnr);
					}
				}
			}
		}
		return matchLsnrs;
	}
	/**
	 * 计算标签的字符数量，中文算1个，英文算半个
	 * @param label
	 * @return
	 */
	public static double caculateLabelFontCnt(String label) {
		double cnt =0;
		for(int i =0;i<label.length();i++) {
			char c = label.charAt(i);
			if(CmnUtil.isChinese(c)) {
				cnt+=1;
			}else {
				cnt+=0.5;
			}
		}
		return cnt;
	}
	
}