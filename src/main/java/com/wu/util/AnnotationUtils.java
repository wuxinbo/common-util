package com.wu.util;


import com.wu.model.FieldAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 注解工具类
 * @author wuxinbo
 *
 */
public class AnnotationUtils {
		/**
		 * 返回一个对象所有的字段注解集合。
		 * @param className 遍历字段注解的对象
		 * @param annotationClass
		 * @return 返回给定的注解集合
		 */
		public static  List<FieldAnnotation>  getFieldAnnotation(String className, Class<? extends Annotation> annotationClass){
			Field[] fields = ReflectUtil.getFieldsFromObject(className);
			List<FieldAnnotation> list =new ArrayList<FieldAnnotation>();
			for (Field field : fields) {
				if (field.getAnnotation(annotationClass)!=null) {
					FieldAnnotation fieldAnnotation =new FieldAnnotation();
					fieldAnnotation.setAnnotation(field.getAnnotation(annotationClass));
					fieldAnnotation.setField(field);
					list.add(fieldAnnotation);
				}
			}
			return list;
		}
}
