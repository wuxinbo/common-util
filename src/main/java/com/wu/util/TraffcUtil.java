package com.wu.util;

import com.wu.model.StationInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 交通出行
 * @author wuxinbo
 *
 */
public class TraffcUtil {
	private static List<Line> lines =new ArrayList<Line>();
	public static int beginIndex =0;
	public static int stopIndex =0;
	public static List<StationInfo> infos=new ArrayList<StationInfo>();;
	static{
		initList();
	}
	/**
	 * 线路枚举
	 * @author wuxinbo
	 *
	 */
	enum Line{
		/**
		 * 1号线
		 */
		ONE("one",new String[]{"朝天门","小什字","较场口","七星岗","两路口","鹅岭","大坪","石油路","歇台子","石桥铺","高庙村","马家岩","小龙坎","沙坪坝"}),
		/**
		 * 2号线
		 */
		TWO("TWO",new String[]{"较场口","临江门","黄花园","大溪沟","曾家岩","牛角沱","李子坝","佛图关","大坪","袁家岗","谢家湾","杨家坪","动物园","大堰村","马王场","平安","大渡口","新山村"});
		private String name;
		public   String [] line ;
		public String getName() {
			return name;
		}
		public String[] getLine() {
			return line;
		}
		private Line(String name,String [] line){
			this.name=name;
			this.line=line;
		}
	}
	/**
	 * 路线计算
	 * @param begin 起点站
	 * @param stop 终点站
	 */
	public static void calculateWay(String begin,String stop){
		StationInfo info =null;
		/*
		 *获取起点终点站信息.
		 */
		getBeginAndStopInfo(begin,stop,info);
		
		}
	/**
	 * 获取线路交叉的地方
	 */
	private static void getaccrossPoint(){
		
	}
	/**
	 * 获取起点终点站信息.
	 * @param begin 起点
	 * @param stop 终点
	 * @param info 信息
	 */
	private static void getBeginAndStopInfo(String begin,String stop,StationInfo info){
		for(Line line: lines){
			if (Arrays.asList(line.getLine()).contains(begin)) {
				info=new StationInfo();
				info.setNumber(getIndexFromArray(begin, (line.getLine())));
				info.setLineName(line.getName());
			}
			if (Arrays.asList(line.getLine()).contains(stop)) {
				info=new StationInfo();
				info.setNumber(getIndexFromArray(stop, (line.getLine())));
				info.setLineName(line.getName());
			}
			infos.add(info);
		}
	}
	/**
	 * 初始化数据
	 */
	private static void initList(){
		lines.add(Line.ONE);
		lines.add(Line.TWO);
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
