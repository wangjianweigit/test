package com.tk.pvtp.order.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.pvtp.order.service.PvtpReturnOrderService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;

/**
 * 私有平台退货单管理
 */
@Controller
@RequestMapping("/pvtp_return_order")
public class PvtpReturnOrderControl {
	
	@Resource
    private PvtpReturnOrderService pvtpReturnOrderService;
	
	
	/**
	 * 私有平台仅退款列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/refund_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryRefundList(HttpServletRequest request) {
		return Transform.GetResult(pvtpReturnOrderService.queryRefundList(request));
	}
	
	/**
	 * 私有平台退货退款列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/returnInfo_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryReturnInfoList(HttpServletRequest request) {
		return Transform.GetResult(pvtpReturnOrderService.queryReturnInfoList(request));
	}
}
