package com.wu.util;

import java.util.Iterator;

/**
 * 交通出行
 * @author wuxinbo
 *
 */
public class TraffcUtil {
	public  static String [] one =new String[]{"朝天门","小什字","较场口","七星岗","两路口","鹅岭","大坪","石油路","歇台子","石桥铺","高庙村","马家岩","小龙坎","沙坪坝"};
	private  static String [] two =new String[]{"较场口","临江门","黄花园","大溪沟","曾家岩","牛角沱","李子坝","佛图关","大坪","袁家岗","谢家湾","杨家坪","动物园","大堰村","马王场","平安","大渡口","新山村"};
	/**
	 * 路线计算
	 * @param begin 起点站
	 * @param stop 终点站
	 */
	public static void calculateWay(String begin,String stop){
		int beginIndex =0;
		
	}
	/**
	 * 获取数组元素位置
	 * @param str
	 * @param array
	 * @return 如果没找到返回-1,找到则返回对应的索引
	 */
	public static int getIndexFromArray(String str,String [] array){
		if (str==null) {
			throw new NullPointerException("str不能为null");
		}
		for (int i = 0; i < array.length; i++) {
			if (str.equals(array[i])) {
				return i;
			}
		}
		return -1;
	}
}
