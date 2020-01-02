package com.tk.oms.sys.control;

import com.tk.oms.sys.service.WechatService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (c), 2016, Tongku
 * FileName : WechatControl
 * 类的详细说明
 *
 * @author wanghai
 * @version 1.00
 * @date 2019-11-26
 */
@Controller
@RequestMapping("/wechat")
public class WechatControl {

    @Resource
    private WechatService wechatService;

    /**
     * 获取unionid
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/unionid", method = RequestMethod.POST)
    @ResponseBody
    public Packet getWchatUnionid(HttpServletRequest request) {
        return Transform.GetResult(wechatService.getWchatUnionid(request));
    }

    /**
     * 获取微信扫码参数
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/scan_params", method = RequestMethod.POST)
    @ResponseBody
    public Packet getWchatScanParams(HttpServletRequest request) {
        return Transform.GetResult(wechatService.getWchatScanParams(request));
    }

    /**
     * 微信扫码参数
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/scan_login", method = RequestMethod.POST)
    @ResponseBody
    public Packet wchatScanLogin(HttpServletRequest request) {
        return Transform.GetResult(wechatService.wchatScanLogin(request));
    }


}
