package com.wu.test;

import com.wu.util.TraffcUtil;
import org.junit.Test;

public class TraffcUtilTest {

	@Test
	public void testGetIndexFromArray() {
		TraffcUtil.calculateWay("较场口", "曾家岩");
		System.out.println(TraffcUtil.infos.get(0).getLineName() + TraffcUtil.infos.get(0).getNumber());
		System.out.println(TraffcUtil.infos.get(1).getLineName() + TraffcUtil.infos.get(1).getNumber());
	}

}
