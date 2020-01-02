package com.tk.oms.sysuser.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.sysuser.service.PrivateInfoLogsService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;

@Controller
@RequestMapping("/private_info_logs")
public class PrivateInfoLogsControl {
	
	@Resource
    private PrivateInfoLogsService privateInfoLogsService;
	
	/**
     * 私密日志新增
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet add(HttpServletRequest request) {
        return Transform.GetResult(privateInfoLogsService.add(request));
    }

    /**
     * 私密日志查询
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryList(HttpServletRequest request) {
        return Transform.GetResult(privateInfoLogsService.queryList(request));
    }
    
    /**
     * 私密日志查询判断密码是否正确
     */
    @RequestMapping(value = "/judge_pwd", method = RequestMethod.POST)
    @ResponseBody
    public Packet judgePwd(HttpServletRequest request) {
        return Transform.GetResult(privateInfoLogsService.judgePwd(request));
    }
}
