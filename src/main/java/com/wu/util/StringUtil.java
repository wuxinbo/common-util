package com.wu.util;

/**
 * String 常用工具类
 * @author wuxinbo
 * @version 1.0
 */
public class StringUtil {
	/**
	 * 判断字符串是否是null或者空字符串
	 * @return 如果是Null或者空字符串 返回Flase,否则返回True
	 */
	public static boolean isNullOrEmpty(String str){
		if (str!=null&&!str.equals("")) {
			return true;
		}
		return false;
	}
}
