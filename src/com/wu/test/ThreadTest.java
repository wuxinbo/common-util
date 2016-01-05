package com.wu.test;

import java.lang.invoke.VolatileCallSite;
import java.util.Date;

/**
 * 线程并发测试
 * @author wuxinbo
 *
 */
public class ThreadTest {
	private static volatile int  number=0;
	
	public  static int getNumberAndIncreament(){
		synchronized (ThreadTest.class) {
			return number++;
		}
		
	}
	public static void main(String[] args) {
		long  begain =System.currentTimeMillis();
//		System.out.println(new Date(begain));
		for (int i = 0; i < 1000; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					getNumberAndIncreament();
				}
			}).start();
			
		}
		System.out.println(number);
		System.out.println((System.currentTimeMillis()-begain));
	}
}
