package com.wu.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class FieldAnnotation {
	/**
	 * 字段
	 */
	private Field field;
	/**
	 * 字段对应的注解
	 */
	private Annotation annotation;
	public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}
	public Annotation getAnnotation() {
		return annotation;
	}
	public void setAnnotation(Annotation annotation) {
		this.annotation = annotation;
	}
	
}
