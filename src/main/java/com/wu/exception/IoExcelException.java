package com.wu.exception;

import com.wu.util.LogUtil;
import org.apache.log4j.Logger;

/**
 * 读取Excel出现异常。
 * 
 * @author xbwuc
 *
 */
public class IoExcelException extends Exception {
	private Logger log4j = LogUtil.createlog4j(this.getClass());
	public IoExcelException(String exceptionInfo){
		
		log4j.error(exceptionInfo);
	}
}
