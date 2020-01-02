package com.tk.oms.analysis.control;

import com.tk.oms.analysis.service.ProductOperationLogService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (c), 2018, TongKu
 * FileName : ProductOperationLogControl
 * 商品操作日志接口访问层
 *
 * @author zhenghui
 * @version 1.00
 * @date 2019/05/15
 */
@Controller
@RequestMapping("/product_operation_log")
public class ProductOperationLogControl {

    @Resource
    private ProductOperationLogService productOperationLogService;

    /**
     * 分页查询商品操作日志列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductOperationLogListForPage(HttpServletRequest request) {
        return Transform.GetResult(productOperationLogService.queryProductOperationLogListForPage(request));
    }
}
