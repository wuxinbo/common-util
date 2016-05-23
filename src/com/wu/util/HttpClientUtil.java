package com.wu.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * http 请求工具类。
 * @author wuxinbo
 *
 */
public class HttpClientUtil {
	CloseableHttpClient httpClient=null;
	/**
	 * 发送简单的get请求
	 * @param url 
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public  void sendGet(String url) throws ClientProtocolException, IOException {
		httpClient=HttpClients.createDefault();
		HttpGet Get =new HttpGet(url);
		CloseableHttpResponse response =httpClient.execute(Get);
		System.out.println(response.getStatusLine());
		System.out.println(EntityUtils.toString(response.getEntity()));
		
	}
	/**
	 * 
	 * @param url
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws KeyManagementException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyStoreException 
	 */
	@SuppressWarnings("deprecation")
	public void SendHttpsGet(String url) throws Exception{
		 KeyStore keyStore  = KeyStore.getInstance("PKCS12");  
         KeyStore trustStore  = KeyStore.getInstance("jks");  
         FileInputStream ksIn = new FileInputStream("d:/clent.p12");  
         FileInputStream tsIn = new FileInputStream(new File("d:/test.keystore"));  
         try {  
             keyStore.load(ksIn, "tomcat".toCharArray());  
             trustStore.load(tsIn, "tomcat".toCharArray());  
         } finally {  
             try { ksIn.close(); } catch (Exception ignore) {}  
             try { tsIn.close(); } catch (Exception ignore) {}  
         }  
        SSLSocketFactory socketFactory = new SSLSocketFactory(keyStore, "tomcat", trustStore);  
        Scheme sch = new Scheme("https", 8443, socketFactory);  
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getConnectionManager().getSchemeRegistry().register(sch);
		HttpGet Get =new HttpGet(url);
		 System.out.println("Executing request " + Get.getRequestLine());
		CloseableHttpResponse response =(CloseableHttpResponse) httpClient.execute(Get);
		System.out.println(response.getStatusLine());
		System.out.println(EntityUtils.toString(response.getEntity()));
	}
}
