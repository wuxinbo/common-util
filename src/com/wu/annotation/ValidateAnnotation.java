package com.wu.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 验证注解,验证数据是否符合要求
 * @author wuxinbo
 *
 */
@Target(value=ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateAnnotation {
		/**
		 * 是否可以为空
		 * @return true可以为null,false为not null
		 */
		public  boolean isBlank () default true;
}
