package com.tk.oms.finance.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.finance.service.SysUserExtractService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : SysUserExtractControl.java
 * 系统用户提现
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018-3-10
 */
@Controller
@RequestMapping("/sys_user_extract")
public class SysUserExtractControl {
	@Resource
	private SysUserExtractService sysUserExtractService;
	
	/**
     * @api {post} /{project_name}/sys_user_extract/list 系统用户提现申请列表
     * @apiName list
     * @apiGroup sys_user_extract
     * @apiDescription  系统用户提现申请列表
     * @apiVersion 0.0.1
     * 
     * @apiParam {number}	pageIndex 			开始页码
     * @apiParam {number}	pageSize 			每页数据量
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 提现申请详情列表
     * 
     */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySysUserExtractList(HttpServletRequest request) {
        return Transform.GetResult(sysUserExtractService.querySysUserExtractList(request));
    }
	
	/**
     * @api {post} /{project_name}/sys_user_extract/detail 获取系统用户提现申请详情
     * @apiName detail
     * @apiGroup sys_user_extract
     * @apiDescription  获取系统用户提现申请详情
     * @apiVersion 0.0.1
     * 
     * @apiParam {number}	id 			申请记录ID
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 提现申请详情
     * 
     */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryUserExtractDetail(HttpServletRequest request) {
        return Transform.GetResult(sysUserExtractService.queryUserExtractDetail(request));
    }
	/**
     * @api {post} /{project_name}/sys_user_extract/approval  审批系统用户提现申请
     * @apiName approval
     * @apiGroup sys_user_extract
     * @apiDescription  审批系统用户提现申请
     * @apiVersion 0.0.1
     * 
     * @apiParam {number}	id 						申请记录ID
     * @apiParam {string}	state 					申请单状态  1、待财务审核  ;2、待打款;  3、打款成功; 4、打款失败 ; 10、审核驳回  ; -1提现用户取消提现申请
     * @apiParam {string}	[reject_reason] 		申请驳回原因,驳回时必填
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 提现申请详情
     * 
     */
	@RequestMapping(value = "/approval", method = RequestMethod.POST)
    @ResponseBody
    public Packet approvalSysUserExtract(HttpServletRequest request) {
		ProcessResult pr= new ProcessResult();
    	try {
    		pr = sysUserExtractService.approvalSysUserExtract(request);
		} catch (Exception e) {
			pr.setMessage(e.toString());
		}
    	return Transform.GetResult(pr);
    }
	
	/**
	 * 添加系统用户提现信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addSysUserExtract(HttpServletRequest request) {
		ProcessResult pr= new ProcessResult();
    	try {
    		pr = sysUserExtractService.addSysUserExtract(request);
		} catch (Exception e) {
			pr.setMessage(e.toString());
		}
    	return Transform.GetResult(pr);
    }
}
