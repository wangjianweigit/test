package com.tk.oms.decoration.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.decoration.service.SocialProductService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * Copyright (c), 2019,  TongKu
 * FileName : SocialProductControl
 * 社交首页内容管理相关
 * @author: liujialong
 * @version: 1.00
 * @date: 2019/4/26
 */
@Controller
@RequestMapping("/social_product")
public class SocialProductControl {
	
	@Resource
    private SocialProductService socialProductService;
	
	/**
	 * 查询社交首页商品列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySocialProductList(HttpServletRequest request) {
        return Transform.GetResult(this.socialProductService.querySocialProductList(request));
    }
	
	/**
	 * 社交首页商品发布或移除
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update_state", method = RequestMethod.POST)
    @ResponseBody
    public Packet socialProductUpdateState(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.socialProductService.socialProductUpdateState(request);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            ex.printStackTrace();
        }
        return Transform.GetResult(pr);
    }
	
	/**
	 * 社交首页内容切换展示方式
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update_display_way", method = RequestMethod.POST)
    @ResponseBody
    public Packet socialProductUpdateDisplayWay(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.socialProductService.socialProductUpdateDisplayWay(request);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            ex.printStackTrace();
        }
        return Transform.GetResult(pr);
    }
	
	/**
	 * 社交首页内容排序
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sort", method = RequestMethod.POST)
    @ResponseBody
    public Packet socialProductSort(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.socialProductService.socialProductSort(request);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            ex.printStackTrace();
        }
        return Transform.GetResult(pr);
    }

}
