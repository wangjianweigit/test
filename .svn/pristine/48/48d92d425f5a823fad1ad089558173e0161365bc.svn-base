package com.tk.oms.sysuser.control;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.sysuser.service.SysUserLogsService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;

/**
* Copyright (c), 2016, Tongku
* FileName : SysUserLogsControl.java
* ERP系统用户日志管理
* @author  wangpeng
* @date 2016-06-20
* @version1.00
*/
@Controller
@RequestMapping("/sys_user_logs")
public class SysUserLogsControl {

    @Resource
    private SysUserLogsService sysUserLogsService;
    /**
     * 新增系统用户日志
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet login(HttpServletRequest request) {
        return Transform.GetResult(sysUserLogsService.addSysUserLogs(request));
    }
    /**
     * 用户操作日志列表查询
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/userlogslistforpage", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryListPage(HttpServletRequest request) {
        return Transform.GetResult(sysUserLogsService.queryListPage(request));
    }
    /**
     * 用户操作日志数量查询
     * 
     * @param request
     * @return
	 */
	@RequestMapping(value = "/userlogscount", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryCount(HttpServletRequest request) {
		return Transform.GetResult(sysUserLogsService.queryUserLogsCount(request));
	}
}
