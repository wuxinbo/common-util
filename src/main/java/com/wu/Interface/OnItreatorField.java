package com.wu.Interface;

import org.apache.poi.ss.usermodel.Row;

import java.lang.reflect.Field;


/**
 * 获取对象中的所有成员属性。
 * @author wuxinbo
 *
 */
public interface OnItreatorField {
	
	/**
	 * 迭代对象中所有的属性，
	 * @param obj 对象
	 * @param fields 成员属性
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void ItratorObject(Object obj,Field[] fields,Row row) throws IllegalArgumentException,IllegalAccessException;
	
}
