package com.tk.oms.finance.control;

import com.tk.oms.finance.service.ServerBusinessWithdrawalService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * Copyright (c), 2017, Tongku
 * FileName : WithdrawalControl
 * 服务商提现管理Control
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/6/14 14:57
 */
@Controller
@RequestMapping("/server_business_withdrawal")
public class ServerBusinessWithdrawalControl {

    @Resource
    private ServerBusinessWithdrawalService serverBusinessWithdrawalService;

    /**
     * @api {post}/{scs_server}/server_business_withdrawal/add 提现申请
     * @apiGroup
     * @apiName withdrawal_add
     * @apiDescription 添加提现申请单
     * @apiVersion 0.0.1
     *
     * @apiParam {string} user_type  服务商用户类型
     * @apiParam {float}  withdrawal_amount 提现金额  必填
     * @apiParam {number} id 提现打款银行帐户ID  必填
     * @apiParam {string} remark 备注
     *
     * @apiSucess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSucess {string} message 接口返回信息
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addWithdrawalApply(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.serverBusinessWithdrawalService.addWithdrawalApply(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post}/{scs_server}/server_business_withdrawal/list 查询提现申请单
     * @apiGroup
     * @apiName list
     * @apiDescription 分页查询提现申请单列表
     * @apiVersion 0.0.1
     *
     * @apiParam {number} pageIndex         当前页码
     * @apiParam {number} pageSize          每页显示行数
     * @apiParam {string} withdrawal_number 提现申请单号
     * @apiParam {string} apply_start_time  申请开始时间
     * @apiParam {string} apply_end_time    申请结束时间
     * @apiParam {string} state             提现状态（1：待审批 2：财务审批通过 10：财务审批驳回 4：出纳已经打款）
     * @apiParam {string} user_type         服务商用户类型
     *
     * @apiSucess {boolean} state                接口获取数据是否成功.true:成功  false:失败
     * @apiSucess {string} message               接口返回信息
     * @apiSucess {object[]} obj                 提现申请单列表
     * @apiSucess {number} obj.id                数据ID
     * @apiSucess {string} obj.withdrawal_number 提现申请单号
     * @apiSucess {date} obj.create_date         创建日期
     * @apiSucess {string} obj.create_user_name  创建人
     * @apiSucess {float} obj.withdrawal_amount  提现金额
     * @apiSucess {date} obj.approval_date       审批日期
     * @apiSucess {char} obj.state               提现状态
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryWithdrawalApplyForPage(HttpServletRequest request) {
        return Transform.GetResult(this.serverBusinessWithdrawalService.queryWithdrawalApplyForPage(request));
    }

    /**
     * @api {post}/{scs_server}/server_business_withdrawal/bankCard_detail 获取提现银行卡信息
     * @apiGroup
     * @apiName bankCard_detail
     * @apiDescription 获取提现银行卡信息
     * @apiVersion 0.0.1
     * @apiParam {number} bank_card_id  银行卡号 必填
     * @apiParam {string} user_type     服务商用户类型
     *
     * @apiSucess {boolean} state  接口获取数据是否成功.true:成功  false:失败
     * @apiSucess {string} message 接口返回信息
     */
    @RequestMapping(value = "/bankCard_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryBankCardWithdrawalDetail(HttpServletRequest request) {
        return Transform.GetResult(this.serverBusinessWithdrawalService.queryBankCardWithdrawalDetail(request));
    }

    /**
     * @api {post}/{scs_server}/server_business_withdrawal/approval_list 获取提现审批列表数据
     * @apiGroup
     * @apiName approval_list
     * @apiDescription 获取提现审批列表数据
     * @apiVersion 0.0.1
     * @apiParam {string} user_type          服务商用户类型
     *
     * @apiSucess {boolean} state  接口获取数据是否成功.true:成功  false:失败
     * @apiSucess {string} message 接口返回信息
     */
    @RequestMapping(value = "/approval_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryBankCardWithdrawalApprovalList(HttpServletRequest request) {
        return Transform.GetResult(this.serverBusinessWithdrawalService.queryWithdrawalApprovalList(request));
    }


    /**
     * @api {post}/{scs_server}/server_business_withdrawal/approval_detail 获取提现审批明细
     * @apiGroup
     * @apiName approval_detail
     * @apiDescription 获取提现审批明细
     * @apiVersion 0.0.1
     * @apiParam {number} withdrawal_number  提现申请单号 必填
     * @apiParam {string} user_type          服务商用户类型
     *
     * @apiSucess {boolean} state  接口获取数据是否成功.true:成功  false:失败
     * @apiSucess {string} message 接口返回信息
     */
    @RequestMapping(value = "/approval_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryBankCardWithdrawalApprovalDetail(HttpServletRequest request) {
        return Transform.GetResult(this.serverBusinessWithdrawalService.queryWithdrawalApprovalDetail(request));
    }


    /**
     * @api {post}/{scs_server}/server_business_withdrawal/account_balance 获取服务商账户余额
     * @apiGroup
     * @apiName account_balance
     * @apiDescription 获取服务商账户余额
     * @apiVersion 0.0.1
     *
     * @apiParam {number} id         提现申请单ID
     * @apiParam {string} user_type  服务商用户类型
     *
     * @apiSucess {boolean} state   接口获取数据是否成功.true:成功  false:失败
     * @apiSucess {string} message  接口返回信息
     */
    @RequestMapping(value = "/account_balance", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAccountBalance(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.serverBusinessWithdrawalService.queryAccountBalance(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post}/{scs_server}/server_business_withdrawal/approval 服务商提现审批通过
     * @apiGroup
     * @apiName approval
     * @apiDescription 服务商提现审批
     * @apiVersion 0.0.1
     *
     * @apiParam {number} id        提现申请单ID
     *
     * @apiSucess {boolean} state   接口获取数据是否成功.true:成功  false:失败
     * @apiSucess {string} message  接口返回信息
     */
    @RequestMapping(value = "/approval", method = RequestMethod.POST)
    @ResponseBody
    public Packet approveWithdrawal(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.serverBusinessWithdrawalService.approveWithdrawal(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post}/{scs_server}/server_business_withdrawal/reject 服务商提现审批驳回
     * @apiGroup
     * @apiName approve
     * @apiDescription 服务商提现审批驳回
     * @apiVersion 0.0.1
     *
     * @apiParam {number}           id              提现申请单ID
     * @apiParam {number}           reject_reason   驳回原因
     *
     * @apiSucess {boolean} state   接口获取数据是否成功.true:成功  false:失败
     * @apiSucess {string} message  接口返回信息
     */
    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    @ResponseBody
    public Packet rejectWithdrawal(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.serverBusinessWithdrawalService.rejectWithdrawal(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post}/{scs_server}/server_business_withdrawal/balance_of_payments_List 服务商收支记录列表查询
     * @apiGroup
     * @apiName balance_of_payments_List
     * @apiDescription 服务商收支记录列表查询
     * @apiVersion 0.0.1
     * @apiParam {string} user_type          服务商用户类型
     *
     * @apiSucess {boolean} state  接口获取数据是否成功.true:成功  false:失败
     * @apiSucess {string} message 接口返回信息
     */
    @RequestMapping(value = "/balance_payments_List", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryBalanceOfPaymentsList(HttpServletRequest request) {
        return Transform.GetResult(this.serverBusinessWithdrawalService.queryBalanceOfPaymentsList(request));
    }
}
