package com.wu.model;

import lombok.Data;

/**
 * Created by wuxinbo on 5/24/16.
 *  https 双向认证 基本信息
 * @author wuxinbo
 * @version 1.0
 */
@Data
public class HttpsInfo {
    /**
     * 证书路径
     */
    private String keyPath;
    /**
     * keystore路径
     */
    private String trustPath;
    /**
     * https 端口
     */
    private int httpsPort;
    /**
     * 证书密码
     */
    private String keyStorePass;
    /**
     * 密钥库密码
     */
    private String trustStorePass;

}
