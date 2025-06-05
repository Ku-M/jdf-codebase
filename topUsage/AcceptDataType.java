package gpf.dc.fe.annotation.formfielddisplay;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 
 * @What: 用于显示扩展类型支持属性类型的声明
 * @Why: 
 * @How: 
 * @Author 陈晓斌
 * @CreateTime : 2024年11月10日
 * @Version: 1.0
 */

import gpf.adur.data.DataType;
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface AcceptDataType {

	/**
	 * 支持的属性类型
	 * @return
	 */
	DataType[] value();
}
