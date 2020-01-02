package com.tk.oms.contribution.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.contribution.service.ContributionOrderService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : ContributionOrderControl
 * 缴款单
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-4-19
 */
@Controller
@RequestMapping("contribution_order")
public class ContributionOrderControl {
	@Resource
	private ContributionOrderService contributionOrderService;
	
	/**
     * @api {post} /{project_name}/contribution_order/list
     * @apiName list
     * @apiGroup contribution_order
     * @apiDescription  查询缴款单列表
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} pageIndex 		开始页码
     * @apiParam {number} pageSize 			每页数据量
     * @apiParam {number} state 			缴款状态（1：待审批，2已审批，3已驳回）
     * @apiParam {string} sale_user_name  	相关单号-下单人用户名
     * @apiParam {string} receipt_type		缴款方式（转账、其他）
     * @apiParam {number} md_id  			相关单号-门店ID
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 					查询缴款单列表
     * @apiSuccess {number}     total 					缴款单总数
     * @apiSuccess {number}   	obj.id 					缴款单ID
     * @apiSuccess {string}   	obj.contribution_number	收款单号
     * @apiSuccess {string}   	obj.sale_user_name 		缴款用户名
     * @apiSuccess {number}   	obj.total_money			缴款总额
     * @apiSuccess {date}   	obj.create_date 		创建时间
     * @apiSuccess {number}   	obj.state				缴款状态（1：待审批，2已审批，3已驳回）
     * @apiSuccess {number}   	obj.audit_user_id 		财务审核人ID
     * @apiSuccess {string}   	obj.create_user_id 		财务审核人姓名
     * @apiSuccess {date}   	obj.audit_date 			审核时间
     * @apiSuccess {string}   	obj.voucher_path		缴款凭证附件路径
     * @apiSuccess {string}   	obj.receipt_type		缴款方式（转账、其他）
     * @apiSuccess {string}   	obj.remark				备注
     * 
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryList(HttpServletRequest request) {
        return Transform.GetResult(contributionOrderService.queryList(request));
    }
    
    /**
     * @api {post} /{project_name}/contribution_order/detail
     * @apiName detail
     * @apiGroup contribution_order
     * @apiDescription  查询缴款单详情
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id 					缴款单ID
     * @apiParam {string} contribution_number  	收款单号
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object}   	obj 					查询缴款单列表
     * @apiSuccess {number}   	obj.id 					缴款单ID
     * @apiSuccess {string}   	obj.contribution_number	收款单号
     * @apiSuccess {string}   	obj.sale_user_name 		缴款用户名
     * @apiSuccess {number}   	obj.total_money			缴款总额
     * @apiSuccess {date}   	obj.create_date 		创建时间
     * @apiSuccess {number}   	obj.state				缴款状态（1：待审批，2已审批，3已驳回）
     * @apiSuccess {number}   	obj.audit_user_id 		财务审核人ID
     * @apiSuccess {string}   	obj.create_user_id 		财务审核人姓名
     * @apiSuccess {date}   	obj.audit_date 			审核时间
     * @apiSuccess {string}   	obj.voucher_path		缴款凭证附件路径
     * @apiSuccess {string}   	obj.receipt_type		缴款方式（转账、其他）
     * @apiSuccess {string}   	obj.remark				备注
     * 
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDetail(HttpServletRequest request) {
        return Transform.GetResult(contributionOrderService.queryDetail(request));
    }

    /**
     * @api {post} /{project_name}/contribution_order/detail
     * @apiName detail
     * @apiGroup contribution_order
     * @apiDescription  缴款单审核，驳回
     * @apiVersion 0.0.1
     *
     * @apiParam {string} public_user_name 		当前登录用户名
     * @apiParam {string} contribution_number  	收款单号
     * @apiParam {string} operate_type  	审核类型 01-审核通过 02-审核驳回
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     *
     */
    @RequestMapping(value = "/auditing", method = RequestMethod.POST)
    @ResponseBody
    public Packet paymentAuditing(HttpServletRequest request) {
        return Transform.GetResult(contributionOrderService.paymentAuditing(request));
    }
}
