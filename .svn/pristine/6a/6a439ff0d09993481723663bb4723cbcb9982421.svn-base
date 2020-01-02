package com.tk.oms.basicinfo.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.basicinfo.service.MqErrorLogService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : MqErrorLogControl.java
 * mq错误日志管理
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018年9月19日
 */
@Controller
@RequestMapping("mq_error_log")
public class MqErrorLogControl {
	@Resource
	private MqErrorLogService mqErrorLogService;
	
	/**
	 * 查询mq错误日志列表
	 * @param request
	 * @return
	 */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMqErrorLogListForPage(HttpServletRequest request) {
        return Transform.GetResult(mqErrorLogService.queryMqErrorLogListForPage(request));
    }
    
    /**
     * 处理错误日志
     * @param request
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet updateMqErrorLog(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
    	try {
    		pr =  mqErrorLogService.updateMqErrorLog(request);
		}catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
    	return Transform.GetResult(pr);
    }
    
    /**
     * 批量处理错误日志
     * @param request
     * @return
     */
    @RequestMapping(value = "/batch_edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet batchUpdateMqErrorLog(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
    	try {
    		pr =  mqErrorLogService.batchUpdateMqErrorLog(request);
		}catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
    	return Transform.GetResult(pr);
    }
    /**
     * 历史数据修复
     * @param request
     * @return
     */
    @RequestMapping(value = "/all_edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet allUpdateProduct(HttpServletRequest request) {
        return Transform.GetResult(mqErrorLogService.allUpdateProduct(request));
    }
}
