package com.tk.oms.order.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.order.service.OrderUnusualService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : OrderUnusualControl
 * 异常订单管理
 * 
 * @author liujialong
 * @version 1.00
 * @date 2019-5-13
 */
@Controller
@RequestMapping("/order_unusual")
public class OrderUnusualControl {
	
	@Resource
	private OrderUnusualService orderUnusualService;
	
	/**
	 * 异常订单列表查询
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryOrderList(HttpServletRequest request) {
		return Transform.GetResult(orderUnusualService.queryOrderUnusualList(request));
	}
	
	/**
	 * 异常订单详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryOrderUnusualDetail(HttpServletRequest request) {
		return Transform.GetResult(orderUnusualService.queryOrderUnusualDetail(request));
	}
	
	/**
	 * 异常订单备注
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/remark", method = RequestMethod.POST)
	@ResponseBody
	public Packet orderUnusualRemark(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=orderUnusualService.orderUnusualRemark(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
	
	/**
	 * 异常订单备注详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/remark_detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet orderUnusualRemarkDetail(HttpServletRequest request) {
		return Transform.GetResult(orderUnusualService.orderUnusualRemarkDetail(request));
	}

}
