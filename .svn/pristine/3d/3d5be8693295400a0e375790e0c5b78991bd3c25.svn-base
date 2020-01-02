package com.tk.store.finance.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.store.finance.service.AccountBalanceService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;

/**
 * 
 * Copyright (c), 2018, Tongku
 * FileName : AccountBalanceControl
 * 账户余额control层
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018-3-9
 */
@Controller
@RequestMapping("/account_balance")
public class AccountBalanceControl {
	@Resource
	private AccountBalanceService accountBalanceService;
	
	/**
    *
    * @api {post} /{project_name}/account_balance/list  收支记录列表
    * @apiGroup account_balance
    * @apiName list
    * @apiDescription  收支记录列表
    * @apiVersion 0.0.1
	
	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条   

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 收支记录列表
    * @apiSuccess {number} obj.record_type		收支类型
    * @apiSuccess {string} obj.docket_type		单据类型
    * @apiSuccess {string} obj.docket_number	单据号
    * @apiSuccess {number} obj.money			金额
    * @apiSuccess {string} obj.create_date		结算时间
    * 
    */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryAccountRecordListForPage(HttpServletRequest request) {
		return Transform.GetResult(accountBalanceService.queryAccountRecordListForPage(request));
	}
	
	/**
    *
    * @api {post} /{project_name}/account_balance/query  账户余额
    * @apiGroup account_balance
    * @apiName query
    * @apiDescription  账户余额
    * @apiVersion 0.0.1
	

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object} obj 
	* @apiSuccess {number} obj.account_balance			账号余额
    */
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryAccountBalance(HttpServletRequest request) {
		return Transform.GetResult(accountBalanceService.queryAccountBalance(request));
	}
	
	/**
    *
    * @api {post} /{project_name}/account_balance/bank 获取用户银行卡信息
    * @apiGroup account_balance
    * @apiName bank
    * @apiDescription  获取用户银行卡信息
    * @apiVersion 0.0.1
	

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object} obj 
	* @apiSuccess {number} obj.account_balance			账号余额
    */
	@RequestMapping(value = "/bank", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryBankInfo(HttpServletRequest request) {
		return Transform.GetResult(accountBalanceService.queryBankInfo(request));
	}
	
	
}
