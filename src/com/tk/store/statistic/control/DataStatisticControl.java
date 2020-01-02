package com.tk.store.statistic.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.store.statistic.service.DataStatisticService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : DataStatisticControl
 * 数据统计control层
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018-3-14
 */
@Controller
@RequestMapping("/data_statistic")
public class DataStatisticControl {
	@Resource
	private DataStatisticService dataStatisticService;
	
	/**
	 * 交易概况
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/trading_outlook", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryTradingOutlook(HttpServletRequest request) {
		return Transform.GetResult(dataStatisticService.queryTradingOutlook(request));
	}
	/**
	 * 销售动态
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sale", method = RequestMethod.POST)
	@ResponseBody
	public Packet querySale(HttpServletRequest request) {
		return Transform.GetResult(dataStatisticService.querySale(request));
	}
	/**
	 * 商品销售排行
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sale_rank", method = RequestMethod.POST)
	@ResponseBody
	public Packet querySaleRank(HttpServletRequest request) {
		return Transform.GetResult(dataStatisticService.querySaleRank(request));
	}
	
	/**
    *
    * @api {post} /{project_name}/data_statistic/psi_list 门店进销存报表
    * @apiGroup data_statistic
    * @apiName psi_list
    * @apiDescription  门店进销存报表
    * @apiVersion 0.0.1
	
	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条   

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 门店进销存报表
    * 
    */
	@RequestMapping(value = "/psi_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryPsiListForPage(HttpServletRequest request) {
		return Transform.GetResult(dataStatisticService.queryPsiListForPage(request));
	}
	
	/**
    *
    * @api {post} /{project_name}/data_statistic/pay_online_list 门店在线支付分析表
    * @apiGroup data_statistic
    * @apiName pay_online_list
    * @apiDescription  门店进销存报表
    * @apiVersion 0.0.1
	
	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条   

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 门店在线支付分析表
    * 
    */
	@RequestMapping(value = "/pay_online_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryPayOnlineListForPage(HttpServletRequest request) {
		return Transform.GetResult(dataStatisticService.queryPayOnlineListForPage(request));
	}
	
}
