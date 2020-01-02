package com.tk.store.member.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.store.member.service.StoreMemberService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2018, Tongku
 * FileName : StoreMemberControl.java
 * 会员管理
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018年6月8日
 */
@Controller
@RequestMapping("/store_member")
public class StoreMemberControl {
	@Resource
	private StoreMemberService storeMemberService;
	
	/**
	 * 会员列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStoreMemberListForPage(HttpServletRequest request) {
		return Transform.GetResult(storeMemberService.queryStoreMemberListForPage(request));
	}
	
	/**
	 * 会员详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStoreMemberDetail(HttpServletRequest request) {
		return Transform.GetResult(storeMemberService.queryStoreMemberDetail(request));
	}
	
	/**
	 * 成交记录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/trad_record", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryTradRecordListForPage(HttpServletRequest request) {
		return Transform.GetResult(storeMemberService.queryTradRecordListForPage(request));
	}
	
	/**
	 * 积分记录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/integral_record", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryIntegralRecordListForPage(HttpServletRequest request) {
		return Transform.GetResult(storeMemberService.queryIntegralRecordListForPage(request));
	}
	/**
	 * 解冻/冻结
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/state", method = RequestMethod.POST)
	@ResponseBody
	public Packet state(HttpServletRequest request) {
		return Transform.GetResult(storeMemberService.state(request));
	}
	/**
	 * 门店下拉框
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/store_select", method = RequestMethod.POST)
	@ResponseBody
	public Packet storeSelect(HttpServletRequest request) {
		return Transform.GetResult(storeMemberService.storeSelect(request));
	}
	/**
	 * 区域下拉框
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/user_select", method = RequestMethod.POST)
	@ResponseBody
	public Packet userSelect(HttpServletRequest request) {
		return Transform.GetResult(storeMemberService.userSelect(request));
	}
	
	/**
	 * 会员充值列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/recharge_record_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStoreMemberRechargeRecordListForPage(HttpServletRequest request) {
		return Transform.GetResult(storeMemberService.queryStoreMemberRechargeRecordListForPage(request));
	}
	
}
