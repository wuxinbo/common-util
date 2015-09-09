package com.wu.test;

import com.wu.util.PropertiesUtil;
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
