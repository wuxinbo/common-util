package com.wu.util;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.*;

public class PropertiesUtilTest extends TestCase{

	@Test
	public void test() {
		String value =PropertiesUtil.getValueFromKey("firstRowNodata","/message-error.properties");
			System.out.println(value);
	}

}
