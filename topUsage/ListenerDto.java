package fe.cmn.widget;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.leavay.ms.tool.CmnUtil;

import fe.cmn.data.FePojo;
import fe.cmn.event.EventDto;
import fe.cmn.pojo.annotation.FieldDefine;
import flutter.coder.annt.NullSafe;

// 在各种Widget中会提供相关定义，后端可自行设置监听器，前端触发后调用指定的后端服务并传入监听命令
// 泛型T代表扩展信息data的类型，触发时会被传入执行器
public class ListenerDto<T> extends FePojo
{
    private static final long serialVersionUID = -2405459696307438769L;

    @FieldDefine(targetClass = {ServerListenerExecutorDto.class,EventListenerExecutorDto.class, ScriptExecutorDto.class})
    @NullSafe(initCode="ListenerExecutorDto()")
    protected ListenerExecutorDto executor;

    // 是否同步监听器（默认为异步、且会过滤高频触发）
    // 如果设定为同步，比较危险，每次触发都同步实施调用执行
    @NullSafe(initCode = "false")
    protected boolean synchronize = false;
    
    // 触发后端调用时，会回填此值代表触发源的WidgetId（省去了每次传递WidgetDto）
    // 由前端填值，对后端为只读
    public String sourceWidgetId;
    
    // 触发后端调用时，是否自动携带源WidgetDto对象下到后端，即监听器函数的参数：source
    // 默认为空代表不携带，如果设置为true，比较危险，可能传递大量垃圾数据
    public Boolean bringbackSourceWidget;
    
    // 构建listener时就可以传入用于扩展的信息，触发时会被传入执行器
    protected T data;
    
    // 校验检查列表
    public List<ValidationDto> validations;

    public ListenerDto() {
    }

    public ListenerDto(EventDto eventDto, boolean synchronize) {
        this.executor = new EventListenerExecutorDto(eventDto);
        this.synchronize = synchronize;
    }

    public ListenerDto(Class<? extends ListenerInterface> service, String command, boolean synchronize) {
        this.executor =  new ServerListenerExecutorDto(service, command);
        this.synchronize = synchronize;
    }
    
    public ListenerDto(Class<? extends ListenerInterface> service, String command, boolean synchronize, T data) {
        this.executor =  new ServerListenerExecutorDto(service, command);
        this.synchronize = synchronize;
        this.data = data;
    }

    public ListenerDto(ListenerExecutorDto executor, boolean synchronize) {
        this.executor = executor;
        this.synchronize = synchronize;
    }

    public Boolean getBringbackSourceWidget()
    {
        return bringbackSourceWidget;
    }

    public ListenerDto setBringbackSourceWidget(Boolean bringbackSourceWidget)
    {
        this.bringbackSourceWidget = bringbackSourceWidget;
        return this;
    }

    public ListenerExecutorDto getExecutor()
    {
        return executor;
    }

    public ServerListenerExecutorDto getServerExecutor()
    {
        return (ServerListenerExecutorDto) executor;
    }

    public EventListenerExecutorDto getEventExecutor()
    {
        return (EventListenerExecutorDto) executor;
    }

    public String getSourceWidgetId()
    {
        return sourceWidgetId;
    }

    public String getServiceCommand()
    {
        return getServerExecutor().getCommand();
    }
    
    public boolean isServiceCommand(String cmd)
    {
        ListenerExecutorDto exe = getExecutor();
        if (!(exe instanceof ServerListenerExecutorDto))
            return false;
        
        return CmnUtil.isStringEqual(cmd, ((ServerListenerExecutorDto)exe).getCommand());
    }

    public ListenerDto setExecutor(ListenerExecutorDto executor)
    {
        this.executor = executor;
        return this;
    }

    public ListenerDto setServerExecutor(Class<? extends ListenerInterface> service, String command)
    {
        this.executor = new ServerListenerExecutorDto(service, command);
        return this;
    }

    public ListenerDto setEventExecutor(EventDto event)
    {
        this.executor = new EventListenerExecutorDto(event);
        return this;
    }

    public boolean isSynchronize()
    {
        return synchronize;
    }

    public ListenerDto setSynchronize(boolean synchronize)
    {
        this.synchronize = synchronize;
        return this;
    }

    public T getData()
    {
        return data;
    }

    public ListenerDto setData(T data)
    {
        this.data = data;
        return this;
    }

    public static ListenerDto buildServiceListener(Class<? extends ListenerInterface> service, String command) {
        return new ListenerDto().setServerExecutor(service, command);
    }
    
    public static ListenerDto buildServiceListener(Class<? extends ListenerInterface> service, String command, Serializable binaryData) {
        return (ListenerDto) new ListenerDto().setServerExecutor(service, command).setBinaryDataIgnoreErr(binaryData);
    }

    public static ListenerDto buildEventListener(EventDto event) {
        return new ListenerDto().setEventExecutor(event);
    }

    public ListenerDto setSelfBinaryData() {
        setBinaryDataIgnoreErr(this);
        return this;
    }

	public List<ValidationDto> getValidations() {
		return validations;
	}

	public ListenerDto setValidations(List<ValidationDto> validations) {
		this.validations = validations;
		return this;
	}
    
	public ListenerDto setValidations(ValidationDto... validations) {
		this.validations = Arrays.stream(validations).collect(Collectors.toList());
		return this;
	}
	
	public Boolean isAllValidationsPassed() {
		Boolean passed = true;
		for(ValidationDto validation : validations) {
			if(!validation.getVerificationPassed()) {
				passed = false;
				break;
			}
		}
		return passed;
	}
	
	public ValidationDto getFirstUnPassedValidation() {
		ValidationDto unPassedValidation = null;
		for(ValidationDto validation : validations) {
			if(!validation.getVerificationPassed()) {
				unPassedValidation = validation;
				break;
			}
		}
		return unPassedValidation;
	}
}
