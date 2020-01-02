package com.tk.oms.notice.control;

import com.tk.oms.notice.service.MessageCenterService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (c), 2017, TongKu
 * FileName : MessageCenterControl
 * 消息中心管理访问接口类
 *
 * @author zhenghui
 * @version 1.00
 * @date 2017/09/05
 */
@Controller
@RequestMapping("/message_center")
public class MessageCenterControl {

    @Resource
    private MessageCenterService messageCenterService;

    /**
     * 分页获取商品上下架消息列表数据
     * @param request
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMessageRemindListForPage(HttpServletRequest request) {
        return Transform.GetResult(this.messageCenterService.queryMessageRemindListForPage(request));
    }

    /**
     * 获取不同消息提醒的未读数量
     * @param request
     * @return
     */
    @RequestMapping(value = "/count", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMessageRemindForCount(HttpServletRequest request) {
        return Transform.GetResult(this.messageCenterService.queryMessageRemindForCount(request));
    }

    /**
     * 获取sku列表数据
     * @param request
     * @return
     */
    @RequestMapping(value = "/sku_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySkuDetailForList(HttpServletRequest request) {
        return Transform.GetResult(this.messageCenterService.querySkuDetailForList(request));
    }

    /**
     * 更新消息状态
     * @param request
     * @return
     */
    @RequestMapping(value = "/edit_state", method = RequestMethod.POST)
    @ResponseBody
    public Packet updateMessageRemindState(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.messageCenterService.updateMessageRemindState(request);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
        }
        return Transform.GetResult(pr);
    }
}