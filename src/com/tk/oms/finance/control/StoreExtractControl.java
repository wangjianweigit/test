package com.tk.oms.finance.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.finance.service.StoreExtractService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * 
 * Copyright (c), 2018, Tongku
 * FileName : StoreExtractControl.java
 * 联营商户提现管理
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018年5月23日
 */
@Controller
@RequestMapping("/store_extract")
public class StoreExtractControl {
	@Resource
	private StoreExtractService storeExtractService;
	/**
     * @api {post} /{project_name}/store_extract/list 联营商户提现申请列表
     * @apiName store_extract
     * @apiGroup store_extract
     * @apiDescription  联营商户提现申请列表
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
    public Packet queryStoreExtractList(HttpServletRequest request) {
        return Transform.GetResult(storeExtractService.queryStoreExtractList(request));
    }
	
	/**
     * @api {post} /{project_name}/store_extract/detail 获取联营商户提现申请详情
     * @apiName store_extract
     * @apiGroup store_extract
     * @apiDescription  获取联营商户提现申请详情
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
    public Packet queryStoreExtractDetail(HttpServletRequest request) {
        return Transform.GetResult(storeExtractService.queryStoreExtractDetail(request));
    }
	/**
     * @api {post} /{project_name}/store_extract/edit  审批联营商户提现申请
     * @apiName store_extract
     * @apiGroup store_extract
     * @apiDescription  审批联营商户提现申请
     * @apiVersion 0.0.1
     * 
     * @apiParam {number}	id 						申请记录ID
     * @apiParam {string}	state 					申请单状态  1、待财务审核  ;2、待打款;  3、打款成功; 4、打款失败 ; 10、审核驳回  ; -1提现用户取消提现申请
     * @apiParam {string}	[reject_reason] 		申请驳回原因,驳回时必填
     * @apiParam {number}	public_user_id 			操作人ID
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 提现申请详情
     * 
     */
	@RequestMapping(value = "/approval", method = RequestMethod.POST)
    @ResponseBody
    public Packet editStoreExtract(HttpServletRequest request) {
		ProcessResult pr= new ProcessResult();
    	try {
    		pr = storeExtractService.approvalStoreExtract(request);
		} catch (Exception e) {
			pr.setMessage(e.toString());
		}
    	return Transform.GetResult(pr);
    }
	/**
	 * @api {post} /{project_name}/store_extract/edit  联营商户提现申请打款
	 * @apiName store_extract
	 * @apiGroup store_extract
	 * @apiDescription  联营商户提现申请打款
	 * @apiVersion 0.0.1
	 * 
	 * @apiParam {number}	id 						申请记录ID
	 * @apiParam {number}	public_user_id 		操作人ID
	 * 
	 * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string}     message 接口返回信息
	 * @apiSuccess {object[]}   obj 提现申请详情
	 * 
	 */
	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	@ResponseBody
	public Packet payStoreExtract(HttpServletRequest request) {
		ProcessResult pr= new ProcessResult();
		try {
			pr = storeExtractService.payStoreExtract(request);
		} catch (Exception e) {
			pr.setMessage(e.toString());
		}
		return Transform.GetResult(pr);
	}
}
