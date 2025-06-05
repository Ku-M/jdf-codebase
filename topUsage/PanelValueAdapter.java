package fe.cmn.panel;

import fe.cmn.data.FePojo;
import flutter.coder.annt.FlutterCode;

/**
 * 取值适配器基类：
 *
 * 把Panel看做单个Editor，对其进行总体取值、设值、显示值的适配器
 * 例如针对Table的Panel，可以制作多种取值适配器，有些以选中行为值，有些以全表数据为值，有些以某固定列选中行为值等等可扩展
 *
 * 默认则以Panel获取所有子控件的值组成Map
 */

@FlutterCode("\tdynamic getValue(PanelValue panelValue, String panelId) => panelValue.mapValue?[panelId];\n") // 为flutter生成一个工具函数
public class PanelValueAdapter<T> extends FePojo
{
    private static final long serialVersionUID = -4697661993941325766L;
    
    // 保持层级结构
    Boolean keeyStructure;
    
    public Boolean getKeeyStructure() {
		return keeyStructure;
	}

	public PanelValueAdapter setKeeyStructure(Boolean keeyStructure) {
		this.keeyStructure = keeyStructure;
		return this;
	}

	public void setValue(PanelValue panelValue, String widgetId, T value)
    {
        panelValue.putValue(widgetId, value);
    }
    
    public T getValue(PanelValue panelValue, String widgetId) {
        return (T) panelValue.getValue(widgetId);
    }
}
