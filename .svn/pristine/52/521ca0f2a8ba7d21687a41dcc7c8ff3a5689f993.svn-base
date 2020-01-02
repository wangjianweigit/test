package com.tk.store.member.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.store.member.service.PublicNumberManageService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;

/**
 * 
 * Copyright (c), 2018, Tongku
 * FileName : PublicNumberManageControl.java
 * 公众号管理
 *
 * @author liujialong
 * @version 1.00
 * @date 2018年9月7日
 */
@Controller
@RequestMapping("/public_number_manage")
public class PublicNumberManageControl {
	
	@Resource
	private PublicNumberManageService publicNumberManageService;
	
	/**
	 * 查询公众号管理列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryPublicNumberManageList(HttpServletRequest request) {
		return Transform.GetResult(publicNumberManageService.queryPublicNumberManageList(request));
	}
	
	/**
	 * 查询公众号管理详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryPublicNumberManageDetail(HttpServletRequest request) {
		return Transform.GetResult(publicNumberManageService.queryPublicNumberManageDetail(request));
	}
	
	/**
	 * 获取公众号二维码
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/preview", method = RequestMethod.POST)
	@ResponseBody
	public Packet publicNumberPreview(HttpServletRequest request) {
		return Transform.GetResult(publicNumberManageService.publicNumberPreview(request));
	}

}
