package com.wu.util;

import org.apache.log4j.Logger;

/**
 * log4j的工具类。
 * @author xbwuc
 *
 */
public class LogUtil {
	/**
	 * 获取log4j的工厂方法。
	 * @param classObj 
	 * @return Log4j对象。
	 */
	public static Logger createlog4j(Class classObj){
		return Logger.getLogger(classObj);
	}
}
