package com.tk.oms.finance.control;

import com.tk.oms.finance.service.PlatformDeliveryService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (c), 2017, TongKu
 * FileName : PlatformDeliveryControl
 * 电商平台发货查询接口访问类
 *
 * @author zhenghui
 * @version 1.00
 * @date 2018/1/16
 */
@Controller
@RequestMapping("/platform_delivery")
public class PlatformDeliveryControl {

    @Resource
    private PlatformDeliveryService platformDeliveryService;

    @RequestMapping(value = "/order_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryPlatformDeliveryOrderListForPage(HttpServletRequest request) {
        return Transform.GetResult(this.platformDeliveryService.queryPlatformDeliveryOrderListForPage(request));
    }

    @RequestMapping(value = "/form_data", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryCreatePlatformDeliveryOrderData(HttpServletRequest request) {
        return Transform.GetResult(this.platformDeliveryService.queryCreatePlatformDeliveryOrderData(request));
    }
}
