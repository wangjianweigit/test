package com.tk.store.stock.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.store.stock.service.StockService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : StockControl
 * 库存管理
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018-3-5
 */
@Controller
@RequestMapping("/stock")
public class StockControl {
	@Resource
	private StockService stockService;
	
	/**
    *
    * @api {post} /{project_name}/stock/list 库存查询列表
    * @apiGroup stock
    * @apiName list
    * @apiDescription  库存查询列表
    * @apiVersion 0.0.1
	
	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条   

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 商品列表
    * 
    */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStockListForPage(HttpServletRequest request) {
		return Transform.GetResult(stockService.queryStockListForPage(request));
	}
	/**
	 * 新零售门店下拉框
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/store_select", method = RequestMethod.POST)
	@ResponseBody
	public Packet storeSelect(HttpServletRequest request) {
		return Transform.GetResult(stockService.storeSelect(request));
	}
	
	/**
	 * 商品库存详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryProductDetail(HttpServletRequest request) {
		return Transform.GetResult(stockService.queryProductDetail(request));
	}
	
	/**
	 * 查询调拨任务数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/task_count", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStoreAllotTaskCount(HttpServletRequest request) {
		return Transform.GetResult(stockService.queryStoreAllotTaskCount(request));
	}
	
	/**
	 * 查询调拨任务列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/task_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStoreAllotTaskListForPage(HttpServletRequest request) {
		return Transform.GetResult(stockService.queryStoreAllotTaskListForPage(request));
	}
	
	/**
	 * 查询调拨任务明细
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/task_detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStoreAllotTaskDetail(HttpServletRequest request) {
		return Transform.GetResult(stockService.queryStoreAllotTaskDetail(request));
	}
	
	/**
	 * 新增调拨任务
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/task_add", method = RequestMethod.POST)
	@ResponseBody
	public Packet addAllotTask(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = stockService.addAllotTask(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
}
