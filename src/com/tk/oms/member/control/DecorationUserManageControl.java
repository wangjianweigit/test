package com.tk.oms.member.control;

import com.tk.oms.member.service.DecorationUserManageService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (c), 2017, Tongku
 * FileName : DecorationUserManageControl
 * 装修用户管理访问接口
 *
 * @author zhenghui
 * @version 1.00
 * @date 2017/09/14
 */
@Controller
@RequestMapping("/decoration_user")
public class DecorationUserManageControl {

    @Resource
    private DecorationUserManageService decorationUserManageService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDecorationUserListForPage(HttpServletRequest request) {
        return Transform.GetResult(this.decorationUserManageService.queryDecorationUserListForPage(request));
    }

    @RequestMapping(value = "/edit_state", method = RequestMethod.POST)
    @ResponseBody
    public Packet editDecorationState(HttpServletRequest request) {
        return Transform.GetResult(this.decorationUserManageService.editDecorationState(request));
    }
}
