package com.tk.oms.finance.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.finance.service.StoreContributoryService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : StoreContributoryControl
 * 店铺缴款control类
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018-3-8
 */
@Controller
@RequestMapping("/store_contributory")
public class StoreContributoryControl {
	@Resource
	private StoreContributoryService storeContributoryService;
	
	/**
     * @api {post} /{project_name}/store_contributory/list 查询店铺缴款单列表
     * @apiName list
     * @apiGroup store_contributory
     * @apiDescription  查询店铺缴款单列表
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} pageIndex 		开始页码
     * @apiParam {number} pageSize 			每页数据量
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 					查询缴款单列表
     * @apiSuccess {number}     total 					缴款单总数
     * @apiSuccess {number}   	obj.id 					缴款单ID
     * @apiSuccess {string}   	obj.ctrb_number			缴款单号
     * @apiSuccess {string}   	obj.ctrb_user_name 		缴款人姓名
     * @apiSuccess {number}   	obj.ctrb_money			缴款金额
     * @apiSuccess {string}   	obj.ctrb_date 			缴款时间
     * @apiSuccess {number}   	obj.audit_state			缴款审批状态（1：待审批，2已审批，3已驳回）
     * @apiSuccess {number}   	obj.audit_user_id 		财务审核人ID
     * @apiSuccess {string}   	obj.audit_user_name 	财务审核人姓名
     * @apiSuccess {date}   	obj.audit_date 			审核时间
     * @apiSuccess {string}   	obj.voucher_path		缴款凭证附件路径
     * @apiSuccess {string}   	obj.ctrb_type			缴款单类型（1-日结缴款）
     * @apiSuccess {string}   	obj.ctrb_remark			缴款备注
     * 
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryListForPage(HttpServletRequest request) {
        return Transform.GetResult(storeContributoryService.queryListForPage(request));
    }
    
    /**
     * @api {post} /{project_name}/store_contributory/detail 查询店铺缴款详情
     * @apiName detail
     * @apiGroup store_contributory
     * @apiDescription  查询店铺缴款详情
     * @apiVersion 0.0.1
     * 
     * @apiParam {string} ctrb_number  	缴款单号
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object}   	obj 					查询缴款单列表
     * @apiSuccess {number}   	obj.id 					缴款单ID
     * @apiSuccess {string}   	obj.ctrb_number			缴款单号
     * @apiSuccess {string}   	obj.ctrb_user_name 		缴款姓名
     * @apiSuccess {number}   	obj.ctrb_money			缴款金额
     * @apiSuccess {string}   	obj.ctrb_date 			缴款时间
     * @apiSuccess {number}   	obj.audit_state			缴款状态（1：待审批，2已审批，3已驳回）
     * @apiSuccess {string}   	obj.store_name			店铺会员
     * @apiSuccess {string}   	obj.partner_user_name	店铺所属合作商
     * @apiSuccess {string}   	obj.voucher_path		缴款凭证附件路径
     * @apiSuccess {string}   	obj.ctrb_remark			缴款备注
     * @apiSuccess {object[]}   obj.trade_list			交易单列表
     * @apiSuccess {string}   	obj.trade_list.trade_number	交易单号
     * @apiSuccess {number}   	obj.trade_list.trade_type	交易类型(1-门店销售订单 2-门店退货订单)
     * @apiSuccess {number}   	obj.trade_list.trade_product_count	交易商品数量
     * @apiSuccess {number}   	obj.trade_list.trade_money	交易金额
     * @apiSuccess {string}   	obj.trade_list.trade_create_date	交易时间
     * 
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryStoreContributoryDetail(HttpServletRequest request) {
        return Transform.GetResult(storeContributoryService.queryStoreContributoryDetail(request));
    }
    
    /**
	 * 审批
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/audit", method = RequestMethod.POST)
	@ResponseBody
	public Packet auditStoreContributory(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = storeContributoryService.auditStoreContributory(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
}
