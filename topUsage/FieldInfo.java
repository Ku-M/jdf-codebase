package gpf.dc.anotation.dto;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * DTO属性声明
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface FieldInfo {
	/**
	 * 属性中文名
	 * @return
	 */
	String label();
}
