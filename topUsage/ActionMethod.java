package jit.anotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cmn.anotation.ClassDeclare;
@ClassDeclare(label = "定制动作方法注解类"
,what="提供对定制动作方法的说明"
, why = ""
, how = ""
,developer="陈晓斌"
,version = "1.0"
,createTime = "2024-11-11"
,updateTime = "2024-11-11")
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@Deprecated
/**
 * 使用cmn.anotation.MethodDeclare代替
 */
public @interface ActionMethod {
	
	/**
	 * 方法名
	 * @return
	 */
	String label();
}