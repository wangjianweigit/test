package com.tk.oms.contribution.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.contribution.service.ContributionWaitService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : ContributionWaitControl
 * 待缴款订单
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-4-19
 */
@Controller
@RequestMapping("contribution_wait")
public class ContributionWaitControl {
	@Resource
	private ContributionWaitService contributionWaitService;
	
	/**
     * @api {post} /{project_name}/contribution_wait/list
     * @apiName list
     * @apiGroup contribution_wait
     * @apiDescription  查询待缴款订单列表
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} pageIndex 		开始页码
     * @apiParam {number} pageSize 			每页数据量
     * @apiParam {number} state 			缴款状态（1：待缴款，2：已缴款，待审核，3缴款成功）
     * @apiParam {string} sale_user_name  	相关单号-下单人用户名
     * @apiParam {number} md_id  			相关单号-门店ID
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 					查询待缴款订单列表
     * @apiSuccess {number}     total 					待缴款订单总数
     * @apiSuccess {number}   	obj.id 					待缴款ID
     * @apiSuccess {number}   	obj.sale_user_name 		相关单号-下单人用户名
     * @apiSuccess {number}   	obj.contribution_type 	缴款类型（1：代客户订单现金支付缴款，2：代客户现金充值缴款）
     * @apiSuccess {string}   	obj.order_number 		相关单号，订单号或充值单号
     * @apiSuccess {number}   	obj.contribution_money 	缴款金额
     * @apiSuccess {date}   	obj.create_date 		创建时间
     * @apiSuccess {number}   	obj.create_user_id 		创建人ID（财务审核人ID）
     * @apiSuccess {string}   	obj.create_user_name 	创建人姓名（财务审核人姓名）
     * @apiSuccess {number}   	obj.state				缴款状态（1：待缴款，2：已缴款，待审核，3缴款成功）
     * @apiSuccess {date}   	obj.contribution_date	缴款时间
     * @apiSuccess {string}   	obj.receipt_type		收款方式
     * 
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryList(HttpServletRequest request) {
        return Transform.GetResult(contributionWaitService.queryList(request));
    }

    /**
     * @api {post} /{project_name}/contribution_wait/contribution
     * @apiName contribution
     * @apiGroup contribution_wait
     * @apiDescription  缴款
     * @apiVersion 0.0.1
     * 
     * @apiParam {string} 	voucher_path 	缴款凭证附件路径
     * @apiParam {string} 	remark  		备注
     * @apiParam {string[]} ids  			待缴款单ID数组
     * @apiParam {string} 	sale_user_name	缴款用户名
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
    @RequestMapping(value = "/contribution", method = RequestMethod.POST)
    @ResponseBody
    public Packet contribution(HttpServletRequest request) {
        ProcessResult pr= new ProcessResult();
    	try {
    		pr = contributionWaitService.contribution(request);
		} catch (Exception e) {
			pr.setMessage(e.toString());
		}
    	return Transform.GetResult(pr);
    }

    /**
     * 充值缴款详情
     * @param request
     * @return
     */
    @RequestMapping(value = "/recharge_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryCashPaymentDetailForRecharge(HttpServletRequest request) {
        return Transform.GetResult(contributionWaitService.queryCashPaymentDetailForRecharge(request));
    }
}
