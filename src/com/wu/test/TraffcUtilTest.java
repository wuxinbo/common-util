package com.wu.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.wu.util.TraffcUtil;

public class TraffcUtilTest {

	@Test
	public void testGetIndexFromArray() {
		System.out.println(TraffcUtil.getIndexFromArray(null, TraffcUtil.one));
	}

}
