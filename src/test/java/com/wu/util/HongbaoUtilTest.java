package com.wu.util;


import java.util.List;

import org.junit.Test;


public class HongbaoUtilTest {

	@Test
	public void testGetRadomMoney() {
//		System.out.println(new Random().nextInt(6));
			List<Double> list =HongbaoUtil.getRedPacket();
			double sum=0;
			for (Double money : list) {
				System.out.println(money);
				sum+=money;
			}
			System.out.println("红包个数："+list.size());
			System.out.println(sum);
	}

}
