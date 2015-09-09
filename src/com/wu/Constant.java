package com.wu;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量类
 * @author xbwuc
 *
 */
public class Constant {
	/**
	 * 年计划Map，将中文表头转换为对应的字段。
	 */
	public static Map<String,String> planOfYearMap = new HashMap<String,String>();
	/**
	 * 用户信息。
	 */
	public static Map<String,String> userMap =new HashMap<String,String>();

	public static  void initMap() {
		//年计划Map
		planOfYearMap.put("计划年","year");
		planOfYearMap.put("机构代码","deptCde");
		planOfYearMap.put("年计划目标","planGoal");
		//UserMap
		userMap.put("用户代码","userCde");
		userMap.put("用户名", "userName");
		userMap.put("机构代码","deptCde");
	}
}
