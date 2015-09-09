package com.wu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String month ="2014-07";
//		String getMOnth =month.substring(month.length()-2,month.length());
//		Calendar cal =Calendar.getInstance();
//		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM");
//		Date date =null;
//		try {
//			 date =sdf.parse(month);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		cal.setTime(date);
//		cal.add(Calendar.MONTH, -date.getMonth());
//		cal.getTime();
//		System.out.println(sdf.format(cal.getTime()));
		System.out.println(getFirstMonthOfYear(month));

	}
	/**
	 * 获取该年的一月。
	 * @param Month 月份格式为 yyyy-MM
	 * @return
	 */
	public static String getFirstMonthOfYear(String Month){
		Calendar cal =Calendar.getInstance();
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM");
		Date date =null;
		try {
			 date =sdf.parse(Month);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sdf =new SimpleDateFormat("yyyy/MM");
		cal.setTime(date);
		cal.add(Calendar.MONTH, -cal.get(Calendar.MONTH));
		return sdf.format(cal.getTime());
	}

}
