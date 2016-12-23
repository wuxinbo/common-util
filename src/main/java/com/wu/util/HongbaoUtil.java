package com.wu.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 红包相关方法
 * @author wuxinbo
 *
 */
public class HongbaoUtil {
	private static Random random =new Random();
	/**
	 * 总金额
	 */
	private static final double  TOTAL_MONEY=1000;
	/**
	 * 剩余金额。
	 */
	private static double  remainMoney=TOTAL_MONEY;
	/**
	 * 红包因子
	 */
	private static int [] weight= new int[]{9,11,240,50,60,21,10,1,16,80,3,5,6};
	private static final int SCALE=1;
	/**
	 * 获取单个金额		
	 * @param multiple 基数
	 * @return  返回一个随机的金额
	 */
	public static  double  getRadomMoney(int multiple){
		double money=random.nextDouble()*multiple;
		return formatDouble(money, SCALE); //四舍五入，保留一位小数
	}
	
	/**
	 * 生成红包数组
	 * @return
	 */
	public static List<Double> getRedPacket(){
		List< Double> list =new ArrayList<Double>();
		while (remainMoney!=0) {
			double money =getRadomMoney(weight[random.nextInt(weight.length)]);
			if (money<remainMoney) {
				list.add(money);
				remainMoney=remainMoney-money;
			}else {
				list.add(formatDouble(remainMoney, SCALE));
				remainMoney=0;
			}
		}
		return list;
	}
//	/**
//	 * 生成一个红包
//	 * @return
//	 */
//	private  static Double getOneRedPacket(double remainMony){
//		double money=0;
//		
//	}
	/**
	 * 格式化数字。
	 * @param money
	 * @param newScale
	 * @return
	 */
	public static double formatDouble(double money,int newScale) {
		return new BigDecimal(money).setScale(newScale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
