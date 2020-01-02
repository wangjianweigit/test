package com.tk.oms.sys.service;

import com.tk.sys.config.EsbConfig;
import com.tk.sys.util.HttpClientUtil;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c), 2016, Tongku
 * FileName : WechatService
 * 类的详细说明
 *
 * @author wanghai
 * @version 1.00
 * @date 2019-11-26
 */
@Service
public class WechatService {

    @Value("${oauth.sso_service_url}")
    private String ssoServerUrl;
    @Value("${sso.access_token}")
    private String accessToken;
    @Value("${sso.get_wechat_scan_params}")
    private String scanParams;
    @Value("${sso.oauth2_scan_login}")
    private String oauth2ScanLogin;
    @Value ("${oauth.oa_company_code}")
    private String oaCompanyCode;

    public ProcessResult getWchatUnionid(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(paramMap.get("code"))) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }

            pr = HttpClientUtil.post(ssoServerUrl + accessToken, paramMap, EsbConfig.SSO_REVERSE_KEY_NAME, EsbConfig.SSO_FORWARD_KEY_NAME);
        }catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            pr.setObj(null);
        }
        return pr;
    }

    /**
     * 获取微信扫码参数
     *
     * @param request
     * @return
     */
    public ProcessResult getWchatScanParams(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(paramMap.get("origin"))) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }

            pr = HttpClientUtil.post(ssoServerUrl + scanParams, paramMap, EsbConfig.SSO_REVERSE_KEY_NAME, EsbConfig.SSO_FORWARD_KEY_NAME);
        }catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            pr.setObj(null);
        }
        return pr;
    }

    /**
     * 微信扫码登录
     *
     * @param request
     * @return
     */
    public ProcessResult wchatScanLogin(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);

            if(paramMap.containsKey("user_name") || paramMap.containsKey("user_pwd")){
                paramMap.put("userName", paramMap.get("user_name"));
                paramMap.put("userPwd", paramMap.get("user_pwd"));
                paramMap.put("company", oaCompanyCode);
            }

            pr = HttpClientUtil.post(ssoServerUrl + oauth2ScanLogin, paramMap, EsbConfig.SSO_REVERSE_KEY_NAME, EsbConfig.SSO_FORWARD_KEY_NAME);
        }catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            pr.setObj(null);
        }
        return pr;
    }
}
