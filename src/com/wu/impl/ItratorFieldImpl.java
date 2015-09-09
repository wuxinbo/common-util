package com.wu.impl;

import com.wu.Interface.OnItreatorField;
import com.wu.PoiUtil;
import org.apache.poi.ss.usermodel.Row;

import java.lang.reflect.Field;

public class ItratorFieldImpl implements OnItreatorField {
	
	/**
	 * 将对象的不为null的属性输出到excelRow上
	 */
	@Override
	public void ItratorObject(Object obj, Field[] fields,Row row)
			throws IllegalArgumentException, IllegalAccessException {
		int i=0;
		for (Field field : fields) {
			field.setAccessible(true); //直接访问成员属性。
			if (field.get(obj)!=null) {
				PoiUtil.setCellValue(field.get(obj),row.createCell(i),field);
			}
			i++;
		}
		
	}

}
