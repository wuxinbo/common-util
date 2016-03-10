package com.wu.test;

import com.wu.util.FileUtil;
import org.junit.Test;

import java.io.IOException;

public class FileUtilTest {

	@Test
	public void test() {
		System.out.println(Math.pow(2, 3));
	}
	@org.testng.annotations.Test
	public void testDownload(){
		try {
			FileUtil.DownloadFile("http://101.231.114.233:8085/TSM_DEV//resources/images/04_03100000_20140813092443.png","/home/wuxinbo/bank/");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
