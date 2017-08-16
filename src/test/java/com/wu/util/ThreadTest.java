package com.wu.util;

import org.testng.annotations.Test;

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

	/**
	 * 并发测试
	 */
	@Test
	public void testConcurrent(){
		long  begain =System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			new Thread(new Runnable() {
				public void run() {
					getNumberAndIncreament();
				}
			}).start();

		}
		System.out.println(number);
		System.out.println((System.currentTimeMillis()-begain));
	}

}
