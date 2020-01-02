package com.tk.oms.analysis.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.analysis.service.ProductOperationLogService;
import com.tk.oms.analysis.service.UserOperationLogService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
/**
 * Copyright (c), 2018, TongKu
 * FileName : UserOperationLogControl
 * 用户操作日志接口访问层
 *
 * @author liujialong
 * @version 1.00
 * @date 2019/05/30
 */
@Controller
@RequestMapping("/user_operation_log")
public class UserOperationLogControl {
	
	@Resource
    private UserOperationLogService userOperationLogService;

    /**
     * 分页查询商品操作日志列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet userOperationLogListForPage(HttpServletRequest request) {
        return Transform.GetResult(userOperationLogService.userOperationLogListForPage(request));
    }

}
