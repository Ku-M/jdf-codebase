package gpf.dc.basic.fe.component;

import cmn.util.TraceUtil;
import cmn.util.Tracer;
import gpf.action.intf.BaseUdfIntf;
import gpf.dc.basic.param.view.BaseFeActionParameter;

public interface BaseFeActionIntf<T extends BaseFeActionParameter> extends BaseUdfIntf<T>{

	public Object execute(T input)throws Exception;
	
	public Class<? extends T> getInputParamClass();
	
	default Tracer newTracer() {
		return TraceUtil.getCurrentTracer();
	}
	
}
