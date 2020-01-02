package com.tk.oms.demo.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.demo.service.TestUserService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;

/**
 * 测试
 */
@Controller
@RequestMapping("/test_user")
public class TestUserControl {

	@Resource
	private TestUserService testUserService;

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryList(HttpServletRequest request) {
		return Transform.GetResult(testUserService.queryList(request));
	}

}