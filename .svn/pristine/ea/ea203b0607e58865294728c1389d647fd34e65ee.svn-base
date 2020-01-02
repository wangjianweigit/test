package com.tk.oms.finance.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.finance.service.CreditBillService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : CreditBillControl
 * 资金清算
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-5-16
 */
@Controller
@RequestMapping("credit_bill")
public class CreditBillControl {
	@Resource
	private CreditBillService creditBillService;
	
	/**
     * @api {post} /{project_name}/credit_bill/capital_settlement
     * @apiName capital_settlement
     * @apiGroup credit_bill
     * @apiDescription  资金清算查询
     * @apiVersion 0.0.1
     * 
     * @apiParam {number}	pageIndex 			开始页码
     * @apiParam {number}	pageSize 			每页数据量
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object} obj 	资金清算查询
     * @apiSuccess {number} obj.id    		主键ID
     * @apiSuccess {string}	obj.bill_number	账单号
	 * @apiSuccess {number}	obj.bill_amount	账单金额
	 * @apiSuccess {number}	obj.user_id		用户ID
	 * @apiSuccess {number}	obj.should_repayment_amount	本期剩余应还金额
	 * @apiSuccess {number}	obj.tran_amount	未出账单金额
	 * @apiSuccess {number}	obj.credit_money_balance 可用额度
	 * @apiSuccess {number}	obj.credit_money 月结额度
	 * @apiSuccess {string}	obj.login_name 	 用户名
	 * @apiSuccess {string}	obj.user_manage_name 用户姓名
     * 
     */
	@RequestMapping(value = "/capital_settlement", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryCapitalSettlementList(HttpServletRequest request) {
        return Transform.GetResult(creditBillService.queryCapitalSettlementList(request));
    }
	
	/**
     * @api {post} /{project_name}/credit_bill/view_accounts
     * @apiName view_accounts
     * @apiGroup credit_bill
     * @apiDescription  账户概况
     * @apiVersion 0.0.1
     * 
     * @apiParam {number}	user_id 用户ID
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object} obj 	账户概况
     * @apiSuccess {number} id    		主键ID
	 * @apiSuccess {number}	bill_amount	本期账单金额
	 * @apiSuccess {number}	should_repayment_amount		本期剩余应还金额
	 * @apiSuccess {string}	repayment_date	本期到期还款日
	 * @apiSuccess {number}	tran_amount	未出账单金额
	 * @apiSuccess {number}	credit_money_balance 可用额度
	 * @apiSuccess {number}	credit_money 月结额度
     * 
     */
	@RequestMapping(value = "/view_accounts", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryViewAccounts(HttpServletRequest request) {
        return Transform.GetResult(creditBillService.queryViewAccounts(request));
    }
	
	
	/**
     * @api {post} /{project_name}/credit_bill/bill_query_ok
     * @apiName bill_query_ok
     * @apiGroup credit_bill
     * @apiDescription  已出账单查询
     * @apiVersion 0.0.1
     * 
     * @apiParam {number}	pageIndex 			开始页码
     * @apiParam {number}	pageSize 			每页数据量
     * @apiParam {number}	user_id 用户ID
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object} obj 	已出账单查询
     * @apiSuccess {number} obj.id    		主键ID
	 * @apiSuccess {number}	obj.bill_month	账单月份
	 * @apiSuccess {string}	obj.bill_amount	应还金额
	 * @apiSuccess {number}	obj.repayment_amount 还款金额
     * 
     */
	@RequestMapping(value = "/bill_query_ok", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryBillQueryOkList(HttpServletRequest request) {
        return Transform.GetResult(creditBillService.queryBillQueryOkList(request));
    }
	
	/**
     * @api {post} /{project_name}/credit_bill/bill_query_no
     * @apiName bill_query_no
     * @apiGroup credit_bill
     * @apiDescription  未出账单查询
     * @apiVersion 0.0.1
     * 
     * @apiParam {number}	user_id 用户ID
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object} obj 	未出账单查询
     * @apiSuccess {number} obj.id    		主键ID
	 * @apiSuccess {string}	obj.tran_date	交易时间
	 * @apiSuccess {number}	obj.tran_amount	交易金额
     * 
     */
	@RequestMapping(value = "/bill_query_no", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryBillQueryNo(HttpServletRequest request) {
        return Transform.GetResult(creditBillService.queryBillQueryNoList(request));
    }
	
	/**
     * @api {post} /{project_name}/credit_bill/bill_detail
     * @apiName bill_detail
     * @apiGroup credit_bill
     * @apiDescription  账单明细
     * @apiVersion 0.0.1
     * 
     * @apiParam {number}	user_id 用户ID
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object} obj 	账单明细
     * @apiSuccess {number} id    				主键ID
	 * @apiSuccess {string}	tally_start_date	账单周期开始时间
	 * @apiSuccess {string}	tally_end_date		账单周期结束时间
	 * @apiSuccess {string}	repayment_date		到期还款日
	 * @apiSuccess {string}	bill_amount			本期应还款总额
	 * @apiSuccess {string}	repayment_amount	本期已还款
     * 
     */
	@RequestMapping(value = "/bill_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryBillDetail(HttpServletRequest request) {
        return Transform.GetResult(creditBillService.queryBillDetail(request));
    }
	
	/**
     * @api {post} /{project_name}/credit_bill/settlement_situation
     * @apiName settlement_situation
     * @apiGroup credit_bill
     * @apiDescription  结算情况
     * @apiVersion 0.0.1
     * 
     * @apiParam {number}	user_id 用户ID
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object} obj 	结算情况
     * @apiSuccess {number} id    					主键ID
	 * @apiSuccess {string}	total_settlement_amount	应结算金额
	 * @apiSuccess {string}	stay_settlement_amount	待结算金额
	 * @apiSuccess {string}	done_settlement_amount	已结算金额
     * 
     */
	@RequestMapping(value = "/settlement_situation", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySettlementSituation(HttpServletRequest request) {
        return Transform.GetResult(creditBillService.querySettlementSituation(request));
    }
	
	/**
     * @api {post} /{project_name}/credit_bill/settlement_situation_list
     * @apiName settlement_situation_list
     * @apiGroup credit_bill
     * @apiDescription  结算列表
     * @apiVersion 0.0.1
     * 
     * @apiParam {number}	id 账单ID
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object} obj 	结算列表
     * @apiSuccess {number} obj.id    				主键ID
	 * @apiSuccess {string}	obj.order_number		订单号
	 * @apiSuccess {string}	obj.create_date			创建时间
	 * @apiSuccess {string}	obj.liquidation_date	结算时间
	 * @apiSuccess {string}	obj.settlement_amount	结算金额
	 * @apiSuccess {string}	obj.stay_settlement_amount	应结算金额
	 * @apiSuccess {string}	obj.settlement_state	结算状态
     * 
     */
	@RequestMapping(value = "/settlement_situation_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySettlementSituationList(HttpServletRequest request) {
        return Transform.GetResult(creditBillService.querySettlementSituationList(request));
    }

	/**
     * @api {post} /{project_name}/credit_bill/settlement_details
     * @apiName settlement_details
     * @apiGroup credit_bill
     * @apiDescription  结算明细
     * @apiVersion 0.0.1
     * 
     * @apiParam {number}	id 账单ID
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object} obj 	结算明细
     * @apiSuccess {number} obj.stationed_user_id   入驻商ID
	 * @apiSuccess {string}	obj.stationed_name		入驻商名称
	 * @apiSuccess {string}	obj.remark				结算项
	 * @apiSuccess {number}	obj.settlement_amount	结算金额
	 * @apiSuccess {string}	obj.settlement_number	结算单号
     * 
     */
	@RequestMapping(value = "/settlement_details", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySettlementDetails(HttpServletRequest request) {
        return Transform.GetResult(creditBillService.querySettlementDetails(request));
    }
	
	/**
     * @api {post} /{project_name}/credit_bill/share_money
     * @apiName share_money
     * @apiGroup credit_bill
     * @apiDescription  查询运费、代发费
     * @apiVersion 0.0.1
     * 
     * @apiParam {string}	order_number 订单号
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object} obj 			结算明细
     * @apiSuccess {number} df_money   			代发费
	 * @apiSuccess {string}	logistics_money		运费
     * 
     */
	@RequestMapping(value = "/share_money", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryOrderShareMoney(HttpServletRequest request) {
        return Transform.GetResult(creditBillService.queryOrderShareMoney(request));
    }
	/**
     * @api {post} /{project_name}/credit_bill/monthly_repayment_list
     * @apiName monthly_repayment_list
     * @apiGroup credit_bill
     * @apiDescription  月结还款列表
     * @apiVersion 0.0.1
     * 
     * @apiParam {number}	pageIndex 			开始页码
     * @apiParam {number}	pageSize 			每页数据量
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object} obj 	月结还款列表
     * @apiSuccess {number} obj.id    		主键ID
     * @apiSuccess {string}	obj.bill_number	账单号
	 * @apiSuccess {number}	obj.bill_amount	账单金额
	 * @apiSuccess {number}	obj.user_id		用户ID
	 * @apiSuccess {number}	obj.should_repayment_amount	本期剩余应还金额
	 * @apiSuccess {number}	obj.tran_amount	未出账单金额
	 * @apiSuccess {number}	obj.credit_money_balance 可用额度
	 * @apiSuccess {number}	obj.credit_money 月结额度
	 * @apiSuccess {string}	obj.login_name 	 用户名
	 * @apiSuccess {string}	obj.user_manage_name 用户姓名
	 * @apiSuccess {string}	obj.bill_month 账单月份
	 * @apiSuccess {string}	obj.repayment_date 到期还款日
     * 
     */
	@RequestMapping(value = "/monthly_repayment_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMonthlyRepaymentList(HttpServletRequest request) {
        return Transform.GetResult(creditBillService.queryMonthlyRepaymentList(request));
    }
	
	/**
     * @api {post} /{project_name}/credit_bill/monthly_repayment_prepaid
     * @apiName monthly_repayment_prepaid
     * @apiGroup credit_bill
     * @apiDescription  充值还款
     * @apiVersion 0.0.1
     * 
     * @apiParam {string}	voucher_img_url 	支付凭证
     * @apiParam {number}	user_id 			用户ID
     * @apiParam {number}	bill_amount			还款金额
     * @apiParam {number}	bill_date			账单日期
     * @apiParam {string}	login_name			用户名
     * @apiParam {string}	user_manage_name	用户姓名
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * 
     */
	@RequestMapping(value = "/monthly_repayment_prepaid", method = RequestMethod.POST)
    @ResponseBody
    public Packet addMonthlyRepaymentPrepaid(HttpServletRequest request) {
        return Transform.GetResult(creditBillService.addMonthlyRepaymentPrepaid(request));
    }
	
	/**
     * @api {post} /{project_name}/credit_bill/monthly_repayment_list
     * @apiName monthly_repayment_list
     * @apiGroup credit_bill
     * @apiDescription  还款记录列表
     * @apiVersion 0.0.1
     * 
     * @apiParam {number}	pageIndex 			开始页码
     * @apiParam {number}	pageSize 			每页数据量
     * @apiParam {number}	user_id 			用户ID
     * @apiParam {string}	bill_date 			账单日期
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object} obj 	还款记录列表
     * @apiSuccess {number} obj.login_name    		用户名
     * @apiSuccess {string}	obj.user_manage_name	用户姓名
	 * @apiSuccess {number}	obj.bill_date			账单日期
	 * @apiSuccess {number}	obj.bill_amount			还款金额
	 * @apiSuccess {number}	obj.create_user_name	申请人
	 * @apiSuccess {number}	obj.create_date			申请时间
	 * @apiSuccess {number}	obj.state 				还款状态
	 * @apiSuccess {number}	obj.approval_user_name  审批人
	 * @apiSuccess {string}	obj.approval_date 	 	审批时间
	 * @apiSuccess {string}	obj.rejected_reason		驳回原因
     * 
     */
	@RequestMapping(value = "/monthly_repayment_history", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMonthlyRepaymentHistory(HttpServletRequest request) {
        return Transform.GetResult(creditBillService.queryMonthlyRepaymentHistory(request));
    }
	
	/**
     * @api {post} /{project_name}/credit_bill/monthly_repayment_approval_list
     * @apiName monthly_repayment_approval_list
     * @apiGroup credit_bill
     * @apiDescription  月结还款审批列表
     * @apiVersion 0.0.1
     * 
     * @apiParam {number}	pageIndex 			开始页码
     * @apiParam {number}	pageSize 			每页数据量
     * @apiParam {number}	user_id 			用户ID
     * @apiParam {string}	bill_date 			账单日期
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object} obj 	月结还款审批列表
     * @apiSuccess {number} obj.login_name    		用户名
     * @apiSuccess {string}	obj.user_manage_name	用户姓名
	 * @apiSuccess {number}	obj.bill_date			账单日期
	 * @apiSuccess {number}	obj.bill_amount			还款金额
	 * @apiSuccess {number}	obj.create_user_name	申请人
	 * @apiSuccess {number}	obj.create_date			申请时间
	 * @apiSuccess {number}	obj.state 				还款状态
	 * @apiSuccess {number}	obj.approval_user_name  审批人
	 * @apiSuccess {string}	obj.approval_date 	 	审批时间
	 * @apiSuccess {string}	obj.rejected_reason		驳回原因
     * 
     */
	@RequestMapping(value = "/monthly_repayment_approval_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMonthlyRepaymentApprovalList(HttpServletRequest request) {
        return Transform.GetResult(creditBillService.queryMonthlyRepaymentApprovalList(request));
    }
	
	/**
     * @api {post} /{project_name}/credit_bill/monthly_repayment_approval_detail
     * @apiName monthly_repayment_approval_detail
     * @apiGroup credit_bill
     * @apiDescription  月结还款审批详情
     * @apiVersion 0.0.1
     * 
     * @apiParam {number}	id 		主键ID
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object} obj 	月结还款审批详情
     * @apiSuccess {number} obj.login_name    		用户名
     * @apiSuccess {string}	obj.user_manage_name	用户姓名
	 * @apiSuccess {number}	obj.bill_date			账单日期
	 * @apiSuccess {number}	obj.bill_amount			还款金额
	 * @apiSuccess {number}	obj.user_id				用户ID
	 * @apiSuccess {number}	obj.create_user_name	申请人
	 * @apiSuccess {number}	obj.create_date			申请时间
	 * @apiSuccess {number}	obj.state 				还款状态
     * 
     */
	@RequestMapping(value = "/monthly_repayment_approval_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMonthlyRepaymentApprovalDetail(HttpServletRequest request) {
        return Transform.GetResult(creditBillService.queryMonthlyRepaymentApprovalDetail(request));
    }
	
	/**
     * @api {post} /{project_name}/credit_bill/monthly_repayment_approval_check
     * @apiName monthly_repayment_approval_check
     * @apiGroup credit_bill
     * @apiDescription  月结还款审批审核
     * @apiVersion 0.0.1
     * 
     * @apiParam {number}	id 		主键ID
     * @apiParam {string}	state 	还款状态
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * 
     */
	@RequestMapping(value = "/monthly_repayment_approval_check", method = RequestMethod.POST)
    @ResponseBody
    public Packet monthlyRepaymentApprovalCheck(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
		try {
			pr = creditBillService.monthlyRepaymentApprovalCheck(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
	
	
}
