package com.wu.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * properties操作工具类。
 * @author xbwuc
 * 
 *
 */
public class PropertiesUtil {
	
	/**
	 * 获取Properties对象。
	 * @return Properties
	 */
	public static Properties getProperties(String fileName){
		Properties property =new Properties();
		try {
			property.load(Object.class.getResourceAsStream(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return property;
	}
	/**
	 * 根据键找到对应的值。
	 * @param key
	 */
	public static String getValueFromKey(String key,String fileName){
		Properties property =getProperties(fileName);
		String value =(String) property.get(key);
		try {
			return new String(value.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
