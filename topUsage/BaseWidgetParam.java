package fe.util.component.param;

import fe.util.OperateTransaction;

public class BaseWidgetParam extends WidgetParam{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3366333278939807433L;
	
	boolean isWritable = true;
	
	public boolean isWritable() {
		return isWritable;
	}
	
	public BaseWidgetParam setWritable(boolean isWritable) {
		this.isWritable = isWritable;
		return this;
	}
}
