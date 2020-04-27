package com.mall.dataobject;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;

@Component
@ConfigurationProperties(prefix = "alipayconfig")
public class AlipayConfig {

    //appId
    private String appId;
    //私钥
    private String rsaPrivateKey;
    //请求网关地址
    private String url;
    //编码格式
    private String charset;
    //返回格式
    private String format;
    //支付宝公钥
    private String alipayPublicKey;
    //签名方式
    private String signType;


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getRsaPrivateKey() {
        return rsaPrivateKey;
    }

    public void setRsaPrivateKey(String rsaPrivateKey) {
        this.rsaPrivateKey = rsaPrivateKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public AlipayConfig() {
    }

    public AlipayConfig(String appId, String rsaPrivateKey, String url, String charset, String format, String alipayPublicKey, String signType) {
        this.appId = appId;
        this.rsaPrivateKey = rsaPrivateKey;
        this.url = url;
        this.charset = charset;
        this.format = format;
        this.alipayPublicKey = alipayPublicKey;
        this.signType = signType;
    }
}
