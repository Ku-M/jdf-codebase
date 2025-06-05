package gpf.dc.basic.anotation.excel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel单元格声明
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface ExcelCell {
	/**
	 * 单元格的含义
	 * @return
	 */
	String value() default "";
	/**
	 * 是否必填
	 * @return
	 */
	boolean require() default false;
}
