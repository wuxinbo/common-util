package com.wu.util;
import com.alibaba.fastjson.JSON;
import com.zte.ztepay.bchannel.dto.HttpsInfo;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyStore;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;

/**
 * 发送Httpclient消息
 * 
 * @author dzl 2015.11
 * 
 */
public class HttpClientUtil {
    private static Logger logger =Logger.getLogger(HttpClientUtil.class);
    /**
     *
     */
    private  static CloseableHttpClient httpClient=null;
    /**
     * https 请求使用的httpclient
     */
    private  static CloseableHttpClient httpsClient=null;
    private static RequestConfig httpConfig= RequestConfig.custom()
            .setSocketTimeout(Integer.parseInt(PropertiesUtil.getPropertie("readTimeout")))
            .setConnectTimeout( Integer.parseInt(PropertiesUtil.getPropertie("conTimeout")))
            .build();

    /**
     * 获取httpClient示例
     * @return
     */
    public  synchronized static CloseableHttpClient getHttpClient(){
        CloseableHttpClient tempHttpClient =null;
//        MultiThreadedHttpConnectionManager httpConnectionManager=new MultiThreadedHttpConnectionManager();
        if (httpClient!=null){
            tempHttpClient= httpClient;
        }else{
            tempHttpClient = HttpClients.createDefault();

        }
        httpClient=tempHttpClient;
        return tempHttpClient;
    }
    /**
     *获取post示例
     * @param postObj 需要传输的数据对象
     * @param httpHeader http请求头
     * @param url 请求地址
     * @return
     */
    public static HttpPost getPostMethod(String url,Object postObj, Map<String,String> httpHeader) throws UnsupportedEncodingException,
            URIException, URISyntaxException {
        HttpPost   mypost = null;
        mypost=new HttpPost();
        mypost.setURI(new URI(url));
        if (httpHeader!=null &&httpHeader.size()>0){
            Set<String> headerKeys =httpHeader.keySet(); //读取map中的httpheader数据
            for (String key  : headerKeys) {
                mypost.setHeader(key,httpHeader.get(key));
                logger.info("header :"+key+",value: "+httpHeader.get(key));
            }
        }
        if (mypost.getConfig()==null){
            mypost.setConfig(httpConfig);
        }
        //print send data
        logger.info(MessageFormat.format("Send message to url:{0},message:{1}",url, JSON.toJSONString(postObj)));
        StringEntity requestEntity = new StringEntity(postObj.toString(), httpHeader.get("Content-Type"),
                "utf-8");
        mypost.setEntity(requestEntity);
        return mypost;
    }
    /**
     * 获取https Client
     * @return 返回httpsClient示例
     */
    public  synchronized static CloseableHttpClient getHttpsClient(String url)  {
        CloseableHttpClient tempHttpClient =null;
        if (httpsClient!=null){
            tempHttpClient= httpsClient;
        }else{
            tempHttpClient = new DefaultHttpClient();
        }
        HttpsInfo info =new HttpsInfo();
        //获取端口号
        String  [] urlinfo =url.split(":");
        String port=urlinfo.length <3?"443": urlinfo[2].substring(0,urlinfo[2].indexOf("/"));
        info.setHttpsPort(Integer.parseInt(port));
        info.setKeyStorePass(PropertiesUtil.getPropertie("clientP12Pwd"));
        info.setTrustStorePass(PropertiesUtil.getPropertie("keystorePwd"));
        try {
            tempHttpClient.getConnectionManager().getSchemeRegistry().register(new HttpClientUtil().getSchme(info));
        } catch (Exception e) {
            logger.error("clientConnectionManager"+tempHttpClient.getConnectionManager());
            logger.error("SchemeRegistry"+tempHttpClient.getConnectionManager().getSchemeRegistry());
            logger.error("httpsClient初始化失败!"+e);
        }
        httpsClient=tempHttpClient;
        return tempHttpClient;
    }
    /**
     * 关闭httpClient连接
     * @param response
     */
    public static void closeConnection(CloseableHttpResponse response){
        try {
            if (response != null) {
                response.close(); // 释放连接
            }
        } catch (Exception e3) {
            logger.error("releaseConnection() failed!", e3);
        }
    }

    /**
     * 获取用于https双向认证使用的
     * @return 生成一个实例
     */
    public Scheme getSchme(HttpsInfo httpsInfo) throws Exception{
        KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        KeyStore trustStore  = KeyStore.getInstance("jks");
        keyStore.load(getClass().getClassLoader().getResourceAsStream("config/cert/appserver.p12"), httpsInfo.getKeyStorePass().toCharArray());
        trustStore.load(getClass().getClassLoader().getResourceAsStream("config/cert/keystore"),httpsInfo.getTrustStorePass().toCharArray());
        SSLSocketFactory socketFactory = new SSLSocketFactory(keyStore, httpsInfo.getKeyStorePass(), trustStore);
        return  new Scheme("https", httpsInfo.getHttpsPort(), socketFactory);
    }
    /**
     * 采用POST发消息到支付平台
     *
     * @param message
     *
     * @param url
     * @param param
     * @return
     */
    public  static String sendRequestByPost(Object message, String url, String param,Map header)  {
        String result = "";
        if (null == url || "".equals(url) || "null".equals(url)) {
            logger.error("url is null.");
            return "-1";
        }
        CloseableHttpClient httpClient = url.startsWith("https")?getHttpsClient(url):getHttpClient();
        HttpPost mypost = null;
        CloseableHttpResponse response =null;
        try {
            mypost = getPostMethod(param!=null &&!param.equals("")?url+param:url,message,header);
            response =httpClient.execute(mypost);
            int re_code = response.getStatusLine().getStatusCode(); // 发送
            if (re_code == HttpStatus.SC_OK||re_code==HttpStatus.SC_INTERNAL_SERVER_ERROR
                    ||re_code==HttpStatus.SC_BAD_REQUEST) { //状态码 200 /500 400
                result = EntityUtils.toString(response.getEntity());
                logger.info(" http response body:" + result);
                return result; // 获取到的内容
            }
            else {
                logger.warn(" http response code:" + re_code);
                logger.error(" http response ErrorInfo:"+result);
                return "-1";
            }

        } catch (Exception e) {
            logger.error("sendMessage failed. url=" + url, e);
            return "-1";
        } finally {
            closeConnection(response);
        }
    }

}

