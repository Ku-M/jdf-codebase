package gpf.dc.basic.dto.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kwaidoo.ms.tool.CmnUtil;

import gpf.dc.anotation.dto.FieldInfo;
import gpf.dc.dto.NestingDto;

/**
 * 事件订阅
 *
 */
public class FeEventSubscribeDto extends NestingDto implements Serializable {
	public final static String FormModelId = "gpf.md.slave.view.FeEventSubscribe";
	public final static String sEvent = "事件";
	public final static String sResponseAction = "响应动作";
	public final static String sIdentifyFilter = "标识码过滤器";
	@FieldInfo(label = sEvent)
	String event;

	public String getEvent() {
		return event;
	}

	public FeEventSubscribeDto setEvent(String v) {
		this.event = v;
		return this;
	}

	@FieldInfo(label = sResponseAction)
	String responseAction;

	public String getResponseAction() {
		return responseAction;
	}

	public FeEventSubscribeDto setResponseAction(String v) {
		this.responseAction = v;
		return this;
	}
	
	public List<String> getResponseActionList(){
		if(CmnUtil.isStringEmpty(responseAction))
			return new ArrayList<>();
		List<String> responseActionList = new ArrayList<>();
		String[] array = responseAction.split(",");
		for(String action :array) {
			responseActionList.add(action.trim());
		}
		return responseActionList;
	}

	@FieldInfo(label = sIdentifyFilter)
	String identifyFilter;

	public String getIdentifyFilter() {
		return identifyFilter;
	}

	public FeEventSubscribeDto setIdentifyFilter(String v) {
		this.identifyFilter = v;
		return this;
	}

}
