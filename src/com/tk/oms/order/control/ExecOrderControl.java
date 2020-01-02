package com.tk.oms.order.control;

import com.tk.oms.order.service.ExecOrderService;
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
 * Copyright (c), 2018, TongKu
 * FileName : ExecOrderControl
 * 异常订单Control
 * @author wangjianwei
 * @version 1.00
 * @date 2019/11/27 16:24
 */
@Controller
@RequestMapping ("/exec_order")
public class ExecOrderControl {
    @Resource
    private ExecOrderService execOrderService;

    /**
     * 异常订单列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet list(HttpServletRequest request) {
        return Transform.GetResult(execOrderService.listExecOrder(request));
    }

    /**
     * 标记异常订单
     * @param request
     * @return
     */
    @RequestMapping(value = "/mark", method = RequestMethod.POST)
    @ResponseBody
    public Packet mark(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try{
            pr= execOrderService.markExecOrder(request);
        }catch(Exception e){
            e.printStackTrace();
            pr.setMessage(e.getMessage());
            pr.setState(false);
        }
        return Transform.GetResult(pr);
    }

    /**
     * 查看白名单列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/white_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet listWhiteList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try{
            pr= execOrderService.listWhiteList(request);
        }catch(Exception e){
            e.printStackTrace();
            pr.setMessage(e.getMessage());
            pr.setState(false);
        }
        return Transform.GetResult(pr);
    }

    /**
     * 移除白名单
     * @param request
     * @return
     */
    @RequestMapping(value = "/remove_white_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeWhiteList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try{
            pr= execOrderService.removeWhiteList(request);
        }catch(Exception e){
            e.printStackTrace();
            pr.setMessage(e.getMessage());
            pr.setState(false);
        }
        return Transform.GetResult(pr);
    }

    /**
     * 新增白名单
     * @param request
     * @return
     */
    @RequestMapping(value = "/add_white_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet addWhiteList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try{
            pr= execOrderService.addWhiteList(request);
        }catch(Exception e){
            e.printStackTrace();
            pr.setMessage(e.getMessage());
            pr.setState(false);
        }
        return Transform.GetResult(pr);
    }
}
