package fe.util.component.param;

import com.leavay.common.util.ToolUtilities;

import fe.util.component.dto.FormSetting;

public class DataEditParam<T> extends BaseWidgetParam{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1828630565127779392L;
	
	public static <T> DataEditParam<T> create(T data) {
		return new DataEditParam<T>().setData(data);
	}
	
	T data;
	FormSetting setting;
	
	
	public T getData() {
		return data;
	}
	
	public DataEditParam<T> setData(T data) {
		this.data = data;
		return this;
	}
	
	public DataEditParam<T> setWritable(boolean isWritable) {
		this.isWritable = isWritable;
		return this;
	}
	
	public FormSetting getSetting() {
		return setting;
	}
	
	/**
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public <R extends FormSetting> R getSetting(Class<R> clazz) throws Exception {
		if(setting == null)
			return null;
		if(setting.getClass() == clazz)
			return (R) setting;
		else {
			R inst = clazz.newInstance();
			ToolUtilities.copyFields(setting, inst);
			this.setting = inst;
			return (R) setting;
		}
	}
	
	public void setSetting(FormSetting setting) {
		this.setting = setting;
	}
	

}
