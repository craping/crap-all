package org.crap.jrain.core.validate.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/** 
 * @project Crap
 * 
 * @author Crap
 * 
 * @Copyright 2013 - 2014 All rights reserved. 
 * 
 * @email 422655094@qq.com
 * 
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StructParam {
	//参数名称
	String value() default "";
	//参数类型
	Class<?> type();
	//参数描述
	String desc() default "";
	//是否为必要参数
	boolean required() default true;
	//是否可以为空
	boolean empty() default false;
	//默认值
	String defaultValue() default "";
	//参数枚举范围
	String[] enums() default {};
	//参数最小值
	String min() default "";
	//参数最小值
	String max() default "";
}
