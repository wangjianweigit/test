package com.tk.oms.oauth2.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tk.oms.oauth2.service.UserAuthorService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;

@Controller
@RequestMapping("/user/author")
public class UserAuthorControl {
	@Resource
	private UserAuthorService userAuthorService;
	/**
	 * 通过OA的openId判断OA与运营总后台是否已经关联
	 */
	@RequestMapping(value = "/get_oa", method = RequestMethod.POST)
	@ResponseBody
	public Packet getByOAOpenId(HttpServletRequest request) {
		return Transform.GetResult(userAuthorService.getByOAOpenId(request));
	}
	/**
	 * ERP使用自身的帐户，密码登录，同时对ERP用户与OA用户进行绑定操作
	 */
	@RequestMapping(value = "/bind", method = RequestMethod.POST)
	@ResponseBody
	public Packet accountBind(HttpServletRequest request) {
		return Transform.GetResult(userAuthorService.accountBind(request));
	}
	/**
	 * 使用OA的openId直接获取用户信息，用于登录
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Packet loginByOpenId(HttpServletRequest request) {
		return Transform.GetResult(userAuthorService.loginByOpenId(request));
	}
}
