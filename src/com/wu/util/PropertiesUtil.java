package com.wu.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
	 * @return 返回对应的值
	 */
	public static String getValueFromKey(String key,String fileName){
		Properties property =getProperties(fileName);
		String value =(String) property.get(key);
		try {
			return new String(value.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 将键值对保存到配置文件中。
	 * @param key 键
	 * @param value 值
	 * @param fileName 文件名
	 * @param updateInfo 更新信息
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void setValue(String key,String value,String fileName, String updateInfo) throws FileNotFoundException, IOException{
		Properties property =getProperties(fileName);
		/*
		 * 配置文件路径。
		 */
		String  path =Properties.class.getResource(fileName).getPath().toString();
		property.setProperty(key, value);
		property.store(new FileOutputStream(path.substring(1, path.length())), updateInfo);
	}
}
