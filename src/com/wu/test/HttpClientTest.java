package com.wu.test;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import com.wu.util.HttpClientUtil;

public class HttpClientTest {
	@Test
	public void testSendGet() throws Exception{
		HttpClientUtil clientUtil =new HttpClientUtil();
		clientUtil.SendHttpsGet("https://192.168.1.108:8443/exam/");
	}
}
