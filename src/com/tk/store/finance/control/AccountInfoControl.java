package com.tk.store.finance.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.store.finance.service.AccountInfoService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2018, Tongku
 * FileName : AccountInfoControl
 * 账户信息control层
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018-3-9
 */
@Controller
@RequestMapping("account_info")
public class AccountInfoControl {
	@Resource
	private AccountInfoService accountInfoService;
	/**
    *
    * @api {post} /{project_name}/account_info/basic  个人基本信息
    * @apiGroup account_balance
    * @apiName basic
    * @apiDescription  个人基本信息
    * @apiVersion 0.0.1
    * 

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object} obj 
	* @apiSuccess {number} obj.
    */
	@RequestMapping(value = "/basic", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryAccountInfoBasic(HttpServletRequest request) {
		return Transform.GetResult(accountInfoService.queryAccountInfoBasic(request));
	}
	
	/**
    *
    * @api {post} /{project_name}/account_info/card_list  银行卡列表
    * @apiGroup account_info
    * @apiName card_list
    * @apiDescription  银行卡列表
    * @apiVersion 0.0.1
	
    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 银行卡列表
    * @apiSuccess {number} obj.id
    * @apiSuccess {string} obj.bank_card	银行卡号
    * @apiSuccess {string} obj.bank_name	开户行
    * @apiSuccess {string} obj.owner_name	持卡人姓名
    * @apiSuccess {string} obj.bind_date	绑定事件
    * 
    */
	@RequestMapping(value = "/card_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryBankCardList(HttpServletRequest request) {
		return Transform.GetResult(accountInfoService.queryBankCardList(request));
	}
	
	/**
    *
    * @api {post} /{project_name}/account_info/authentication  认证信息
    * @apiGroup account_info
    * @apiName authentication
    * @apiDescription  认证信息
    * @apiVersion 0.0.1
	
    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object} obj 认证信息
    * @apiSuccess {number} obj.user_real_name			真实姓名
    * @apiSuccess {string} obj.user_manage_cardid		用户身份证号
    * @apiSuccess {string} obj.user_manage_cardid_img	身份证正面照
    * @apiSuccess {string} obj.state					审批状态
    * 
    */
	@RequestMapping(value = "/authentication", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryAuthenticationInfo(HttpServletRequest request) {
		return Transform.GetResult(accountInfoService.queryAuthenticationInfo(request));
	}
	
	/**
    *
    * @api {post} /{project_name}/account_info/bind 绑卡
    * @apiGroup account_info
    * @apiName bind
    * @apiDescription  绑卡
    * @apiVersion 0.0.1
    * 
	* @apiParam {number} id			主键ID
	* 
    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object} obj 认证信息
    * @apiSuccess {number} obj.user_real_name			真实姓名
    * @apiSuccess {string} obj.user_manage_cardid		用户身份证号
    * @apiSuccess {string} obj.user_manage_cardid_img	身份证正面照
    * @apiSuccess {string} obj.state					审批状态
    * 
    */
	@RequestMapping(value = "/bind", method = RequestMethod.POST)
	@ResponseBody
	public Packet bind(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            pr = accountInfoService.bind(request);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
        }
        return Transform.GetResult(pr);
	}
	
	/**
    *
    * @api {post} /{project_name}/account_info/unbind 解绑
    * @apiGroup account_info
    * @apiName unbind
    * @apiDescription  解绑
    * @apiVersion 0.0.1
    * 
	* @apiParam {number} id			主键ID
	* 
    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object} obj 认证信息
    * 
    */
	@RequestMapping(value = "/unbind", method = RequestMethod.POST)
	@ResponseBody
	public Packet unbind(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            pr = accountInfoService.unbind(request);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
        }
        return Transform.GetResult(pr);
	}
	/**
    *
    * @api {post} /{project_name}/account_info/check_note 短信验证
    * @apiGroup account_info
    * @apiName check_note
    * @apiDescription  短信验证
    * @apiVersion 0.0.1
    * 
    * @apiParam {string}   bank_card 银行卡卡号
    * @apiParam {string}   bank_code 银行代码
    * @apiParam {string}   mobile_phone 银行绑定手机号
	* 
    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object} obj 
    * 
    */
	@RequestMapping(value = "/check_note", method = RequestMethod.POST)
	@ResponseBody
	public Packet checkNote(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            pr = accountInfoService.checkNote(request);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
        }
        return Transform.GetResult(pr);
	}
	
	/**
    *
    * @api {post} /{project_name}/account_info/authentication_add  添加认证信息
    * @apiGroup account_info
    * @apiName authentication_add
    * @apiDescription  添加认证信息
    * @apiVersion 0.0.1
    * 
    * @apiParam {string} user_real_name				姓名
	* @apiParam {string} user_manage_cardid			身份证号
	* @apiParam {string} user_manage_cardid_img		身份证正面图
	
    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * 
    */
	@RequestMapping(value = "/authentication_add", method = RequestMethod.POST)
	@ResponseBody
	public Packet addAuthenticationInfo(HttpServletRequest request) {
		return Transform.GetResult(accountInfoService.addAuthenticationInfo(request));
	}
	
}
