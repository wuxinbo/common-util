package com.wu.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.wu.util.CalUtil;

public class CalUtilTest {

	@Test
	public void testBinaryTodecimal() {
		System.out.println(Integer.valueOf("0101",2));
		
		System.out.println(CalUtil.binaryTodecimal("01000001"));
	}

}
