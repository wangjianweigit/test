package com.tk.oms.finance.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.tk.sys.util.ProcessResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.finance.service.AdvanceOrderService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : AdvanceOrderControl
 * 预定订单
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-9-26
 */
@Controller
@RequestMapping("advance_order")
public class AdvanceOrderControl {
	@Resource
	private AdvanceOrderService advanceOrderService;
	/**
     * @api {post} /{project_name}/advance_order/deposit_list 退定金审批列表
     * @apiName deposit_list
     * @apiGroup advance_order
     * @apiDescription  退定金审批列表
     * @apiVersion 0.0.1
     * 
     * @apiParam {number}	pageIndex 			开始页码
     * @apiParam {number}	pageSize 			每页数据量
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object} obj 	退定金审批列表
     * 
     */
	@RequestMapping(value = "/deposit_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDepositRefundList(HttpServletRequest request) {
        return Transform.GetResult(advanceOrderService.queryDepositRefundList(request));
    }
	
	/**
     * @api {post} /{project_name}/advance_order/deposit_detail 退定金详情
     * @apiName deposit_detail
     * @apiGroup advance_order
     * @apiDescription  退定金详情
     * @apiVersion 0.0.1
     * 
     * @apiParam {number}	pageIndex 			开始页码
     * @apiParam {number}	pageSize 			每页数据量
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object} obj 	退定金详情
     * 
     */
	@RequestMapping(value = "/deposit_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDepositRefundDetail(HttpServletRequest request) {
        return Transform.GetResult(advanceOrderService.queryDepositRefundDetail(request));
    }

	/**
     * @api {post} /{project_name}/advance_order/list 预定订单列表
     * @apiName list
     * @apiGroup advance_order
     * @apiDescription  预定订单列表
     * @apiVersion 0.0.1
     *
     * @apiParam {number}	pageIndex 			开始页码
     * @apiParam {number}	pageSize 			每页数据量
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object} obj 	预定订单列表
     *
     */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAdvanceOrderList(HttpServletRequest request) {
        return Transform.GetResult(advanceOrderService.queryAdvanceOrderList(request));
    }

	/**
     * @api {post} /{project_name}/advance_order/detail 预定订单详情
     * @apiName detail
     * @apiGroup advance_order
     * @apiDescription  预定订单详情
     * @apiVersion 0.0.1
     *
     * @apiParam {number}	pageIndex 			开始页码
     * @apiParam {number}	pageSize 			每页数据量
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object} obj 	预定订单详情
     *
     */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAdvanceOrderDetail(HttpServletRequest request) {
        return Transform.GetResult(advanceOrderService.queryAdvanceOrderDetail(request));
    }

	/**
     * @api {post} /{project_name}/advance_order/cancel 取消订单
     * @apiName cancel
     * @apiGroup advance_order
     * @apiDescription  取消订单
     * @apiVersion 0.0.1
     *
     * @apiParam {number}	pageIndex 			开始页码
     * @apiParam {number}	pageSize 			每页数据量
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object} obj
     *
     */
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
    @ResponseBody
    public Packet updateOrderCancel(HttpServletRequest request) {
        return Transform.GetResult(advanceOrderService.updateOrderCancel(request));
    }
	/**
	 * @api {post} /{project_name}/advance_order/close 关闭订单
	 * @apiName colse
	 * @apiGroup advance_order
	 * @apiDescription  取消订单
	 * @apiVersion 0.0.1
	 *
	 * @apiParam {string}	order_number 			预定订单号
	 *
	 * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string} message 接口返回信息说明
	 * @apiSuccess {object} obj
	 *
	 */
	@RequestMapping(value = "/close", method = RequestMethod.POST)
	@ResponseBody
	public Packet colseOrder(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = advanceOrderService.colseOrder(request);
		} catch (Exception e) {
			// TODO: handle exception
			pr.setMessage(e.getMessage());
		}
		return Transform.GetResult(pr);
	}


    /**
     * 退定金单审批通过
     *
     * @api {post} /{project_name}/advance_order/approve/pass 退定金单审批通过
     * @apiName advance_order_approve_pass
     * @apiGroup advance_order
     * @apiDescription  退定金单审批通过
     * @apiVersion 0.0.1
     *
     * @apiParam {string}	return_number 			退定金单号
     * @apiParam {string}	approval_user_name 			审批人
     * @apiParam {string}	approval_user_realname 			审批人姓名
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/approve/pass", method = RequestMethod.POST)
    @ResponseBody
    public Packet passDepositRefundApprove(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = advanceOrderService.approveDepositRefund(request, 2);
        } catch (Exception e) {
            e.printStackTrace();
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }

        return Transform.GetResult(pr);
    }

    /**
     * 退定金单审批驳回
     *
     * @api {post} /{project_name}/advance_order/approve/reject 退定金单审批驳回
     * @apiName advance_order_approve_reject
     * @apiGroup advance_order
     * @apiDescription  退定金单审批驳回
     * @apiVersion 0.0.1
     *
     * @apiParam {string}	return_number 			退定金单号
     * @apiParam {string}	approval_user_name 			审批人
     * @apiParam {string}	approval_user_realname 			审批人姓名
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/approve/reject", method = RequestMethod.POST)
    @ResponseBody
    public Packet rejectDepositRefundApprove(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = advanceOrderService.approveDepositRefund(request, 3);
        } catch (Exception e) {
            e.printStackTrace();
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }

        return Transform.GetResult(pr);
    }
    
    /**
	 * @api {post} /{project_name}/advance_order/allocation 手工占用
	 * @apiName allocation
	 * @apiGroup advance_order
	 * @apiDescription  手工占用
	 * @apiVersion 0.0.1
	 *
	 * @apiParam {string}	order_number 			预定订单号
	 *
	 * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string} message 接口返回信息说明
	 * @apiSuccess {object} obj
	 *
	 */
	@RequestMapping(value = "/allocation", method = RequestMethod.POST)
	@ResponseBody
	public Packet allocationOrder(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = advanceOrderService.allocationOrder(request);
		} catch (Exception e) {
			pr.setMessage(e.getMessage());
		}
		return Transform.GetResult(pr);
	}

	/**
	 * @api {post} /{project_name}/advance_order/custom_checking 定制订单审批
	 * @apiName custom_checking
	 * @apiGroup advance_order
	 * @apiDescription  定制订单审批
	 * @apiVersion 0.0.1
	 *
	 * @apiParam {string}	order_number 			预定订单号
	 * @apiParam {char}		check_state 			审核状态1-待审核 2-审核通过 3-审核驳回
	 *
	 * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string} message 接口返回信息说明
	 * @apiSuccess {object} obj 	定制订单详情
	 *
	 */
	@RequestMapping(value = "/custom_checking", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryAdvanceOrderCustomChecking(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = advanceOrderService.queryAdvanceOrderCustomChecking(request);
		} catch (Exception e) {
			pr.setMessage(e.getMessage());
		}
		return Transform.GetResult(pr);
	}
}
