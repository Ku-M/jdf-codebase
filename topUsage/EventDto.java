package fe.cmn.event;

import java.io.IOException;
import java.io.Serializable;

import com.leavay.common.util.ToolUtilities;
import fe.cmn.data.FePojo;
import fe.cmn.pojo.annotation.FieldDefine;
import flutter.coder.annt.NullSafe;

/**
 * 发送的消息体 
 */
public class EventDto extends FePojo
{
    private static final long serialVersionUID = -181766888163222746L;

    // 必填，按此命令字订阅（默认就以类名作为命令字）
    @NullSafe
    String command = getClass().getName();
    
    // 与订阅端的identifyFilter（正则式）配套使用，如果订阅端未指定则表示全收（不过滤）
    @FieldDefine(label = "识别码", description = "同一个command需要进一步精确识别时，可发送此识别码。对应订阅端则可以identifyFilter（正则式）来过滤")
    String identifyCode;
    
    // 可附带任意数据，发送和监听端知道含义即可
    Object data;
    
    // 触发后端调用时，会回填此值代表触发源的WidgetId（省去了每次传递WidgetDto）
    // 由前端填值，对后端为只读
    String sourceWidgetId;

    public EventDto()
    {
        
    }
    
    public EventDto(String cmd)
    {
        setCommand(cmd);
    }
    
    public String getCommand()
    {
        return command;
    }

    public EventDto setCommand(String command)
    {
        this.command = command;
        return this;
    }
    
    public boolean isCommand(String cmd)
    {
        return ToolUtilities.isStringEqual(cmd, this.command);
    }

    public Object getData()
    {
        return data;
    }

    public EventDto setData(Object data)
    {
        this.data = data;
        return this;
    }

    public String getSourceWidgetId() {
		return sourceWidgetId;
	}

	public EventDto setSourceWidgetId(String sourceWidgetId) {
		this.sourceWidgetId = sourceWidgetId;
		return this;
	}

	@Override
    public EventDto setUserObject(Object userObject) {
        return (EventDto)super.setUserObject(userObject);
    }

    @Override
    public EventDto setBinaryData(Serializable binaryData) throws IOException {
        return (EventDto)super.setBinaryData(binaryData);
    }

    @Override
    public EventDto getBinaryDataIgnoreErr() {
        return (EventDto)super.getBinaryDataIgnoreErr();
    }

    @Override
    public EventDto setBinaryDataIgnoreErr(Object binaryData) {
        return (EventDto)super.setBinaryDataIgnoreErr(binaryData);
    }

    public EventDto setSelfBinaryData() {
        setBinaryDataIgnoreErr(this);
        return this;
    }

    public String getIdentifyCode()
    {
        return identifyCode;
    }

    public EventDto setIdentifyCode(String identifyCode)
    {
        this.identifyCode = identifyCode;
        return this;
    }
    
    
}
