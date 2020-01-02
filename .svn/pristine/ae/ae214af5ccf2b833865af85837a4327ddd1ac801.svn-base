package com.tk.oms.marketing.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.marketing.service.RetailActivityService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;

/**
 * Copyright (c), 2019, TongKu
 * FileName : RetailActivityControl
 * 新零售营销活动
 *
 * @author liujialong
 * @version 1.00
 * @date 2019/07/08
 */
@Controller
@RequestMapping("retail_activity")
public class RetailActivityControl {
	
	@Resource
    private RetailActivityService retailActivityService;
	
	/**
	 * 新零售营销活动列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRetailActivityListForPage(HttpServletRequest request) {
        return Transform.GetResult(this.retailActivityService.queryRetailActivityListForPage(request));
    }
	
	/**
	 * 新零售营销活动详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRetailActivityDetail(HttpServletRequest request) {
        return Transform.GetResult(this.retailActivityService.queryRetailActivityDetail(request));
    }
	
	/**
	 * 新零售营销活动商品详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/product_detail_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRetailActivityProductDetailList(HttpServletRequest request) {
        return Transform.GetResult(this.retailActivityService.queryRetailActivityProductDetailList(request));
    }
	
	/**
	 * 获取店铺名称列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/query_shop_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryShopList(HttpServletRequest request) {
        return Transform.GetResult(this.retailActivityService.queryShopList(request));
    }
	
	/**
	 * 获取新零售概括数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/query_data_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDataDetail(HttpServletRequest request) {
        return Transform.GetResult(this.retailActivityService.queryDataDetail(request));
    }
	
	/**
	 * 获取获取活动工具数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/query_tool_manage_info", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryToolManageInfo(HttpServletRequest request) {
        return Transform.GetResult(this.retailActivityService.queryToolManageInfo(request));
    }
	
	/**
	 * 保存获取活动工具数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/save_tool_manage_info", method = RequestMethod.POST)
    @ResponseBody
    public Packet saveToolManageInfo(HttpServletRequest request) {
        return Transform.GetResult(this.retailActivityService.saveToolManageInfo(request));
    }

}
