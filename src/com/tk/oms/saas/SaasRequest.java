package com.tk.oms.saas;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c), 2016, Tongku
 * FileName : SaasRequest
 * 聚水潭请求数据
 *
 * @author wanghai
 * @version 1.00
 * @date 2017-11-23
 */
public class SaasRequest implements Serializable{

    /** 合作方编号（聚水潭相关，客户提供） */
    private String partnerid;
    /** 接入密钥（聚水潭相关，客户提供） */
    private String patrnerkey;
    /** 接口名称 */
    private String method;
    /** 店铺授权码（聚水潭相关，客户提供） */
    private String token;
    /** 淘宝APPKEY（客户提供，奇门查询使用） */
    private String taobao_appkey;
    /** 淘宝APPSECRET（客户提供，奇门查询使用） */
    private String taobao_appsecret;
    /** 请求参数 */
    private Object req_params = new Object();

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPatrnerkey() {
        return patrnerkey;
    }

    public void setPatrnerkey(String patrnerkey) {
        this.patrnerkey = patrnerkey;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTaobao_appkey() {
        return taobao_appkey;
    }

    public void setTaobao_appkey(String taobao_appkey) {
        this.taobao_appkey = taobao_appkey;
    }

    public String getTaobao_appsecret() {
        return taobao_appsecret;
    }

    public void setTaobao_appsecret(String taobao_appsecret) {
        this.taobao_appsecret = taobao_appsecret;
    }

    public Object getReq_params() {
        return req_params;
    }

    public void setReq_params(Object req_params) {
        this.req_params = req_params;
    }
}
