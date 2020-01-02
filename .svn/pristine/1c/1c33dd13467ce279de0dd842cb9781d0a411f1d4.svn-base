package com.tk.store.member.control;

import com.tk.store.member.service.StoreMemberLevelService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (c), 2018,  TongKu
 * FileName : StoreMemberLevelControl
 *
 * @author: zhengfy
 * @version: 1.00
 * @date: 2018/8/1
 */
@Controller
@RequestMapping("/store_member_level")
public class StoreMemberLevelControl {

    @Resource
    private StoreMemberLevelService storeMemberLevelService;

    /**
     * 会员等级列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryList(HttpServletRequest request) {
        return Transform.GetResult(storeMemberLevelService.queryList(request));
    }

    /**
     * 编辑会员等级
     * @param request
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet edit(HttpServletRequest request) {
        return Transform.GetResult(storeMemberLevelService.edit(request));
    }

    /**
     * 删除会员等级
     * @param request
     * @return
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet remove(HttpServletRequest request) {
        return Transform.GetResult(storeMemberLevelService.remove(request));
    }


     /**
     * 设置会员的等级
     * @param request
     * @return
     */
    @RequestMapping(value = "/set_user_level", method = RequestMethod.POST)
    @ResponseBody
    public Packet setUserLevel(HttpServletRequest request) {
        return Transform.GetResult(storeMemberLevelService.setUserLevel(request));
    }






}

    
    
