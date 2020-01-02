package com.tk.oms.finance.control;

import com.tk.oms.finance.service.OrderProductPriceService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author : zhengfy
 * @version V1.0
 * @date 2019/11/22
 * 记录商品售价功能
 */

@Controller
@RequestMapping("order_product_price")
public class OrderProductPriceControl {


    @Resource
    private OrderProductPriceService orderProductPriceService;

    /**
     * 分页获取订单商品价格明细
     *
     */
    @RequestMapping (value = "/detail_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDetailList (HttpServletRequest request) {
        return Transform.GetResult(orderProductPriceService.queryDetailList(request));
    }

    /**
     * 获取计算过程详情
     *
     */
    @RequestMapping (value = "/process", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProcess (HttpServletRequest request) {
        return Transform.GetResult(orderProductPriceService.queryProcess(request));
    }

}