package gpf.dc.basic.anotation.excel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel列声明
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface ExcelColumn {
	/**
	 * 列名
	 * @return
	 */
	String value() default "";
	/**
	 * 是否必填
	 * @return
	 */
	boolean require() default false;
}
