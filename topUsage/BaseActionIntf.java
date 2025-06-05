package gpf.dc.action.intf;

import cmn.util.TraceUtil;
import cmn.util.Tracer;
import gpf.action.intf.BaseUdfIntf;
import gpf.dc.action.param.BaseActionParameter;

public interface BaseActionIntf<T extends BaseActionParameter> extends BaseUdfIntf<T>{

	public Object execute(T input)throws Exception;
	
	public Class<? extends T> getInputParamClass();
	
//	default void trace(T input,Object msg) {
//		LvUtil.trace(msg);
//	}
	default Tracer newTracer() {
		return TraceUtil.getCurrentTracer();
	}
	
}
