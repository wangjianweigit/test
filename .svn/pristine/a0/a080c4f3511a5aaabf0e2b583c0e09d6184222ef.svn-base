package com.tk.oms.sys.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.sys.service.ExchangeDateService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : ExchangeDateControl\
 * 调换货时间配置
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018-1-3
 */
@Controller
@RequestMapping("/exchange_date")
public class ExchangeDateControl {
	@Resource
	private ExchangeDateService exchangeDateService;
	
	/**
	 * 列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryExchangeDateList(HttpServletRequest request) {
        return Transform.GetResult(exchangeDateService.queryExchangeDateList(request));
    }
	/**
	 * 详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryExchangeDateDetail(HttpServletRequest request) {
        return Transform.GetResult(exchangeDateService.queryExchangeDateDetail(request));
    }
	
	/**
	 * 新增
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addExchangeDate(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = exchangeDateService.addExchangeDate(request);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
        }
        return Transform.GetResult(pr);
    }
	
	/**
	 * 更新
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet updateExchangeDate(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = exchangeDateService.updateExchangeDate(request);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
        }
        return Transform.GetResult(pr);
    }
	
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet deleteExchangeDate(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = exchangeDateService.deleteExchangeDate(request);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
        }
        return Transform.GetResult(pr);
    }
}
