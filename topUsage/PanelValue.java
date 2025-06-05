package fe.cmn.panel;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.leavay.common.util.ToolBasic;
import com.leavay.common.util.ToolUtilities;
import com.leavay.ms.tool.CmnUtil;

import cson.core.CsonPojo;
import fe.cmn.data.WrongPojo;
import flutter.coder.annt.FlutterToString;

@FlutterToString("return display??'';")
public class PanelValue extends CsonPojo
{

    private static final long serialVersionUID = -8972068664264063896L;

    Map<String, Object> mapValue;
    
    List lstTestValue;
    
    String display;


    public Map<String, Object> getMapValue()
    {
        return mapValue;
    }

    public PanelValue setMapValue(Map<String, Object> mapValue)
    {
        this.mapValue = mapValue;
        return this;
    }
    
    public List getLstTestValue()
    {
        return lstTestValue;
    }

    public void setLstTestValue(List lstTestValue)
    {
        this.lstTestValue = lstTestValue;
    }

    public Object getValue(String widgetId)
    {
        return mapValue == null?null:mapValue.get(widgetId);
    }
    
    public PanelValue putValue(String widgetId, Object value)
    {
        mapValue = mapValue==null?new HashMap<String, Object>():mapValue;
        
        mapValue.put(widgetId, value);
        return this;
    }

    public String getDisplay()
    {
        return display;
    }

    public PanelValue setDisplay(String display)
    {
        this.display = display;
        return this;
    }
    
    public Object getFirstValue()
    {
        if (ToolUtilities.isObjectEmpty(mapValue))
            return null;
        return mapValue.entrySet().iterator().next().getValue();
    }
    
    public String toString()
    {
        String dis = CmnUtil.getString(display, "");
        dis += " - "+ToolBasic.logString(mapValue, false);
        
        return dis;
    }
    
    public List<WrongPojo> getWrongPojo() {
    	List<WrongPojo> wrongPojos = new LinkedList();
    	
    	for(Object value : mapValue.values()) {
    		if(value instanceof WrongPojo) {
    			wrongPojos.add((WrongPojo) value);
    		}
    	}
    	
    	return wrongPojos;
    }
}
