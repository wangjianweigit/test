package com.tk.oms.member.control;

import com.tk.oms.member.service.MemberPreloadChargeService;
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
 * Copyright (c), 2017, Tongku
 * FileName : MemberPreloadChargeControl
 * 会员现金预充值管理
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/7/12 11:18
 */
@Controller
@RequestMapping("/member_preload_charge")
public class MemberPreloadChargeControl {

    @Resource
    private MemberPreloadChargeService memberPreloadChargeService;

    /**
     * @api{post} /{oms_server}/member_preload_charge/add 会员现金预充值申请
     * @apiGroup add
     * @apiName add
     * @apiDescription 会员现金预充值申请
     * @apiVersion 0.0.1
     *
     * @apiParam {string} record_channel              收付渠道,直接使用中文可选信息如下(现金、银行转账、POS刷卡、微信、支付宝)
     * @apiParam {string} collect_user_name           收款人用户名
     * @apiParam {string} money                       充值金额
     * @apiParam {string} turnover_number             收付关联号
     * @apiParam {string} third_number                第三方支付号码，只有支付类型为 支付宝 微信  银联  时此处才有值
     * @apiParam {string} voucher_img_url             凭证图片地址
     * @apiParam {string} public_user_id              操作人姓名
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * @apiSuccess {object} obj 会员现金预充值申请信息
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addPreloadedCharge(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = memberPreloadChargeService.addPreloadedCharge(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api{post} /{oms_server}/member_preload_charge/list 会员现金预充值申请列表
     * @apiGroup list
     * @apiName list
     * @apiDescription 会员现金预充值申请列表
     * @apiVersion 0.0.1
     *
     * @apiParam {number} record_number                     充值单号
     * @apiParam {number} login_name                        会员帐号
     * @apiParam {number} user_manage_name                  会员姓名
     * @apiParam [char] [state]                             申请状态
     *
     * @apiSuccess {string} obj.ID                          申请id
     * @apiSuccess {string} obj.RECORD_NUMBER               申请单号
     * @apiSuccess {string} obj.RECORD_CHANNEL              收付渠道,直接使用中文可选信息如下(现金、银行转账、POS刷卡、微信、支付宝)
     * @apiSuccess {string} obj.LOGIN_NAME                  收款人用户名
     * @apiSuccess {string} obj.USER_MANAGE_NAME            收款人姓名
     * @apiSuccess {string} obj.MONEY                       充值金额
     * @apiSuccess {string} obj.STATE                       充值状态 1：待验款、2:：验款通过 、 3：:验款驳回
     * @apiSuccess {string} obj.CHECK_USER_NAME             验款人用户名
     * @apiSuccess {string} obj.CHECK_USER_BUSINESS_NAME    验款人名称
     * @apiSuccess {string} obj.CHECK_REJECT_REASON         验款驳回原因
     * @apiSuccess {string} obj.TURNOVER_NUMBER             收付关联号
     * @apiSuccess {string} obj.THIRD_NUMBER                第三方支付号码，只有支付类型为 支付宝 微信  银联  时此处才有值
     * @apiSuccess {string} obj.VOUCHER_IMG_URL             凭证图片地址
     * @apiSuccess {string} obj.CREATE_DATE                 信息创建时间
     * @apiSuccess {string} obj.CREATE_USER_NAME            申请人
     * @apiSuccess {string} obj.TRAN_NUMBER                 见证宝交易号
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * @apiSuccess {object} obj 会员预充值申请列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryPreloadedChargeList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = memberPreloadChargeService.queryPreloadedChargeList(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api{post} /{oms_server}/member_preload_charge/list 会员现金预充值申请
     * @apiGroup list
     * @apiName list
     * @apiDescription 会员现金预充值申请
     * @apiVersion 0.0.1
     *
     * @apiParam {number} id                                申请id
     *
     * @apiSuccess {string} obj.ID                          申请id
     * @apiSuccess {string} obj.RECORD_NUMBER               申请单号
     * @apiSuccess {string} obj.RECORD_CHANNEL              收付渠道,直接使用中文可选信息如下(现金、银行转账、POS刷卡、微信、支付宝)
     * @apiSuccess {string} obj.LOGIN_NAME                  收款人用户名
     * @apiSuccess {string} obj.USER_MANAGE_NAME            收款人姓名
     * @apiSuccess {string} obj.MONEY                       充值金额
     * @apiSuccess {string} obj.STATE                       1：待验款、2:：验款通过 、 3：:验款驳回
     * @apiSuccess {string} obj.CHECK_USER_NAME             验款人用户名
     * @apiSuccess {string} obj.CHECK_USER_BUSINESS_NAME    验款人名称
     * @apiSuccess {string} obj.CHECK_REJECT_REASON         验款驳回原因
     * @apiSuccess {string} obj.TURNOVER_NUMBER             收付关联号
     * @apiSuccess {string} obj.THIRD_NUMBER                第三方支付号码，只有支付类型为 支付宝 微信  银联  时此处才有值
     * @apiSuccess {string} obj.VOUCHER_IMG_URL             凭证图片地址
     * @apiSuccess {string} obj.CREATE_DATE                 信息创建时间
     * @apiSuccess {string} obj.CREATE_USER_NAME            申请人
     * @apiSuccess {string} obj.TRAN_NUMBER                 见证宝交易号
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * @apiSuccess {object} obj 会员预充值申请明细
     */
    @RequestMapping(value = "/approve_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryPreloadedChargeInfo(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = memberPreloadChargeService.queryPreloadedChargeInfo(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api{post} /{oms_server}/member_preload_charge/approve 会员现金预充值审批
     * @apiGroup approve
     * @apiName approve
     * @apiDescription 会员现金预充值审批
     * @apiVersion 0.0.1
     *
     * @apiParam {number} id                                申请id
     * @apiParam {string} type                              审批类型(approve:审批通过,reject:审批驳回)
     * @apiParam {string} check_reject_reason               驳回原因
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * @apiSuccess {object} obj 审批结果
     */
    @RequestMapping(value = "/approve_edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet approvePreloadedCharge(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = memberPreloadChargeService.approvePreloadedCharge(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }
}
