package com.tk.oms.stationed.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tk.oms.stationed.service.StationedSettlementService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
/**
 * 入驻商月结信息统计，主要用于统计平台会员、门店对于各个入驻商的月结欠款信息
 * Copyright (c), 2017, Tongku
 * FileName : StationedSettlementControl.java
 * 
 * @author songwangwen
 * @version 1.00
 * @date 2017-12-14
 */
@Controller
@RequestMapping("/stationed_settlement")
public class StationedSettlementControl {
	
	@Resource
	private StationedSettlementService stationedSettlementService;
	/**
     * @api {post} /{project_name}/stationed_settlement/stationed_list 入驻商信息列表
     * @apiName stationed_list
     * @apiGroup stationed_settlement
     * @apiDescription  查询有月结权限的平台会员对于各个入驻商的月结欠款信息
     * @apiVersion 0.0.1
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 查询入驻商信息
     * 
     */
	@RequestMapping(value = "/stationed_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStationedList(HttpServletRequest request) {
		return Transform.GetResult(stationedSettlementService.queryStationedList(request));
	}
	/**
	 * @api {post} /{project_name}/stationed_settlement/user_monthly 月结客户结算核对表
	 * @apiName user_monthly
	 * @apiGroup stationed_settlement
	 * @apiDescription  查询有月结权限的平台会员对于各个入驻商的月结欠款信息
	 * @apiVersion 0.0.1
	 * 
	 * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string}     message 接口返回信息
	 * @apiSuccess {object[]}   obj 结客户结算核对表信息
	 * 
	 */
	@RequestMapping(value = "/user_monthly", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryUserMonthlySettlement(HttpServletRequest request) {
		return Transform.GetResult(stationedSettlementService.queryUserMonthlySettlement(request));
	}
	/**
	 * @api {post} /{project_name}/stationed_settlement/store_monthly 门店结算核对表
	 * @apiName store_monthly
	 * @apiGroup stationed_settlement
	 * @apiDescription  查询门店结算核对表信息
	 * @apiVersion 0.0.1
	 * 
	 * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string}     message 接口返回信息
	 * @apiSuccess {object[]}   obj 结客户结算核对表信息
	 * 
	 */
	@RequestMapping(value = "/store_monthly", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStoreMonthlySettlement(HttpServletRequest request) {
		return Transform.GetResult(stationedSettlementService.queryStoreMonthlySettlement(request));
	}
}
