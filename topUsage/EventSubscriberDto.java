package fe.cmn.event;

import cson.core.CsonPojo;
import fe.cmn.pojo.annotation.FieldDefine;
import flutter.coder.annt.NullSafe;

/**
 * 设置订阅消息
 * 按类型订阅或者按命令订阅，二选一
 * 
 * 订阅消息会转换为对后端服务的调用（EventInterface）
 */
public class EventSubscriberDto extends CsonPojo
{

    private static final long serialVersionUID = 1687422722135786945L;

    // 要订阅的消息命令字
    @NullSafe
    String command;
    
    // 收到订阅消息后，转为对后端的服务调用
    @NullSafe
    String service;

    // 更精准订阅相同Command但identifyCode不同的事件，以正则表达式匹配事件的identifyCode，如未指定则全收（不过滤）
    // identifyFilter为空表示全收，如果和identifyCode完全相同或正则匹配，可接收
    // 如果消息本身没携带识别码，而订阅器设定了识别过滤器，则拒收
    @FieldDefine(label = "识别码过滤器", description = "以正则表达式匹配识别码。当Command相同，需要进一步精确接收时，可设置此识别码过滤器进行精确匹配。对应发送端，则需在EventDto中设置identifyCode")
    String identifyFilter;
    
    // 触发后端调用时，是否自动携带源WidgetDto对象下到后端，即监听器函数的参数：source
    // 默认为空代表不携带，如果设置为true，比较危险，可能传递大量垃圾数据
    public Boolean bringbackSourceWidget;
    
    public EventSubscriberDto()
    {
        
    }
    
    public EventSubscriberDto(String subCommand)
    {
        setCommand(subCommand);
    }
    
    public EventSubscriberDto(Class<? extends EventDto> clazz)
    {
        setCommandType(clazz);
    }

    public String getCommand()
    {
        return command;
    }

    public EventSubscriberDto setCommand(String command)
    {
        this.command = command;
        return this;
    }
    
    public EventSubscriberDto setCommandType(Class<? extends EventDto> clazz)
    {
        this.command = clazz.getName();
        return this;
    }

    public String getService()
    {
        return service;
    }

    public EventSubscriberDto setService(Class<? extends EventInterface> service)
    {
        this.service = service.getName();
        return this;
    }

	public Boolean getBringbackSourceWidget() {
		return bringbackSourceWidget;
	}

	public EventSubscriberDto setBringbackSourceWidget(Boolean bringbackSourceWidget) {
		this.bringbackSourceWidget = bringbackSourceWidget;
		return this;
	}

    public String getIdentifyFilter()
    {
        return identifyFilter;
    }

    public EventSubscriberDto setIdentifyFilter(String identifyFilter)
    {
        this.identifyFilter = identifyFilter;
        return this;
    }
	
	
}
