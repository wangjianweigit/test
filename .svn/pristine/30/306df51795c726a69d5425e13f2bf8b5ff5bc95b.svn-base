package com.tk.oms.finance.service;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.tk.oms.attesttreas.dao.AttesttreasDao;
import com.tk.oms.attesttreas.service.AttesttreasTimeoutService;
import com.tk.oms.finance.dao.UserAccountRecordDao;
import com.tk.oms.stationed.dao.StationedDao;
import com.tk.sys.util.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.finance.dao.CreditBillDao;
import com.tk.oms.finance.dao.OrderAuditingDao;
import com.tk.oms.finance.dao.SettlementDao;


/**
 * Copyright (c), 2017, Tongku
 * FileName : TransactionService
 * 交易业务处理
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-6-8
 */
@Service("TransactionService")
public class TransactionService {
    @Value("${pay_service_url}")
    private String pay_service_url;
    @Value("${tran_rechange}")
    private String tran_rechange;//会员充值地址
    @Value("${tran_capital_frozen}")//见证宝资金冻结
    private String tran_capital_frozen;
    @Value("${tran_directpay}")//见证宝直接支付
    private String tran_directpay;

    @Resource
    private CreditBillDao creditBillDao;
    @Resource
    private OrderAuditingDao orderAuditingDao;
    @Resource
    private SettlementDao settlementDao;
    @Resource
    private StationedDao stationedDao;
    @Resource
    private UserAccountRecordDao userAccountRecordDao;
    @Resource
    private AttesttreasDao attesttreasDao;

    @Resource
    private MessageService messageService;

    @Resource
    private AttesttreasTimeoutService attesttreasTimeoutService;

    @Resource(name = "esbRabbitTemplate")
    private RabbitTemplate esbRabbitTemplate;

    /**
     * 月结还款登记挂账
     *
     * @param param
     */
    @Transactional
    public ProcessResult monthlyRepaymentCharge(Map<String, Object> param) throws Exception {
        //查询会员银行子账户
        String bankAccount = creditBillDao.queryBankAccountByUserId(param);
        //更新登记状态
        creditBillDao.updateRegisterState(param);
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("user_id", param.get("user_id"));
        retMap.put("bank_account", bankAccount);
        retMap.put("user_name", param.get("user_name"));
        retMap.put("tran_amount", param.get("tran_amount"));
        retMap.put("order_number", param.get("trade_number"));
        retMap.put("thirdLogNo", param.get("tran_logno"));

        // 请求见证宝会员登记挂账
        ProcessResult pr = HttpClientUtil.post(pay_service_url + "/tran/charge_account", retMap);
        if (!pr.getState()) {
            throw new RuntimeException(pr.getMessage());
        }
        return pr;
    }

    /**
     * 月结还款
     *
     * @param param
     * @return
     */
    @Transactional
    public ProcessResult monthlyRepayment(Map<String, Object> param) throws Exception {
        ProcessResult pr = new ProcessResult();
        //本期账单
        Map<String, Object> billMap = creditBillDao.queryCurrentBill(param);
        //查询结算金额
        String settlement_balance = creditBillDao.querySettlementBalance(param);

        String settlementBalance = "";
        if (StringUtils.isEmpty(settlement_balance)) {
            settlementBalance = "0";
        } else {
            settlementBalance = settlement_balance;
        }
        //还款金额 +结算金额
        double repayment_balance = DoubleUtils.add(Double.parseDouble(param.get("tran_amount").toString()), Double.parseDouble(settlementBalance));

        double frozenAmount = Double.parseDouble(param.get("tran_amount").toString());
        double tranAmount = Double.parseDouble(param.get("tran_amount").toString());
        // 处理会员历史欠款
        // 查询会员历史欠款金额
        Double debtAmount = creditBillDao.queryUserDebtAmount(Long.parseLong(param.get("user_id").toString()));
        if(debtAmount != null && debtAmount > 0){
            if(debtAmount >= Double.parseDouble(param.get("tran_amount").toString())){
                repayment_balance = 0;
                frozenAmount = repayment_balance;
            }else{
                repayment_balance = DoubleUtils.sub(Double.parseDouble(param.get("tran_amount").toString()), debtAmount);
                frozenAmount = repayment_balance;
            }

            long public_user_stationed_user_id = 90000001;

            // 锁入驻商
            Map<String, Object> stattionedMap = new HashMap<String, Object>();
            stattionedMap.put("public_user_stationed_user_id", public_user_stationed_user_id);
            stationedDao.lockTable(stattionedMap);

            double debtRepaymentAmount = debtAmount > tranAmount ? tranAmount : debtAmount;

            // 更新入驻商余额
            stattionedMap.put("tran_amount", debtRepaymentAmount);
            // 获取入驻商账户信息
            Map<String, Object> accountMap = stationedDao.queryBankAccountById(public_user_stationed_user_id);
            stattionedMap.put("account_balance", DoubleUtils.add(debtRepaymentAmount, Double.parseDouble(accountMap.get("ACCOUNT_BALANCE").toString())));
            if(stationedDao.updateAccountBalance(stattionedMap) <= 0){
                throw new RuntimeException("还款审批异常，请稍后再试！");
            }

            // 更新入驻商校验码
            stattionedMap.put("user_id", public_user_stationed_user_id);
            creditBillDao.updateUserAccountCheckCode(stattionedMap);

            // 增加入驻商收支记录
            stattionedMap.put("user_id", Long.parseLong(param.get("user_id").toString()));
            stattionedMap.put("turnover_number", "8888888888" + param.get("user_id").toString());
            stattionedMap.put("account_id", accountMap.get("ID"));
            stattionedMap.put("account_flag", 0);

            if(userAccountRecordDao.insertStationRecordByUserRepayment(stattionedMap) <= 0){
                throw new RuntimeException("增加入驻商收支记录失败");
            }

            // 增加入驻商资金流水
            stattionedMap.put("log_type", "欠款还款");
            stattionedMap.put("deposit_balance", Double.parseDouble(accountMap.get("DEPOSIT_MONEY_BALANCE").toString()));
            // 获取待清分金额
            Map<String, Object> unliquidated_balance_Map = stationedDao.queryWaitSettlementAmount(public_user_stationed_user_id);
            stattionedMap.put("unliquidated_balance", unliquidated_balance_Map.get("UNLIQUIDATED_BALANCE").toString());
            stattionedMap.put("record_channel","余额");//收付渠道：余额、保证金、待结算

            if(userAccountRecordDao.insertStationedCapitalLogs(stattionedMap) <= 0){
                throw new RuntimeException("增加资金流水记录失败");
            }

            // 更新会员欠款金额
            if(creditBillDao.updateUserDebtAmount(stattionedMap) <= 0){
                throw new RuntimeException("更新会员欠款金额失败");
            }

            // 见证宝会员间转账
            //查询会员银行子账户
            String bankAccount = creditBillDao.queryBankAccountByUserId(param);

            Map<String, Object> retMap = new HashMap<String, Object>();
            retMap.put("out_user_id", param.get("user_id"));
            retMap.put("out_bank_account", bankAccount);
            retMap.put("out_user_name", param.get("user_id"));
            retMap.put("in_user_id", public_user_stationed_user_id);
            retMap.put("in_bank_account", accountMap.get("BANK_ACCOUNT").toString());
            retMap.put("in_user_name", public_user_stationed_user_id);
            retMap.put("tran_amount", debtRepaymentAmount);
            retMap.put("order_number",  param.get("trade_number").toString());
            retMap.put("thirdLogNo", attesttreasDao.getThiredLogNo());              //交易流水号

            //请求见证宝会员直接支付
            pr = HttpClientUtil.post(pay_service_url + tran_directpay, retMap);
            if (!pr.getState()) {
                throw new RuntimeException(pr.getMessage());
            }
        }

        // 获取订单结算金额
        // update by wanghai
        List<Map<String, Object>> payList = creditBillDao.queryOrderSettlementAmount(param);
        for (Map<String, Object> map : payList) {
            if (repayment_balance >= Double.parseDouble(map.get("SETTLEMENT_AMOUNT").toString())) {
                repayment_balance = DoubleUtils.sub(repayment_balance, Double.parseDouble(map.get("SETTLEMENT_AMOUNT").toString()));

                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("user_id", param.get("user_id"));
                paramMap.put("order_number", map.get("ORDER_NUMBER"));
                //更新待结算单信息
                if (creditBillDao.updateBussSettlementInfo(paramMap) <= 0) {
                    throw new RuntimeException("还款审批异常，请稍后再试！");
                }

                creditBillDao.updateBussSettlementShipmentsInfo(paramMap);
                creditBillDao.updateFeeSettlementShipmentsInfo(paramMap);
            } else {
                break;
            }
        }
        // 增加结算余额
        param.put("settlement_balance", repayment_balance);
        //不为空则是本期还款，如果为空则为未出账单还款操作
        if (billMap != null) {
            //账单金额
            param.put("creditbill_amount", billMap.get("BILL_AMOUNT"));
            //还款金额
            param.put("repayment_amount", billMap.get("REPAYMENT_AMOUNT"));
            //账单ID
            param.put("bill_id", billMap.get("ID"));
            if (!billMap.get("REPAYMENT_STATE").toString().equals("3")) {
                //更新账单还款金额
                if (creditBillDao.updateCreditBill(param) <= 0) {
                    throw new RuntimeException("还款审批异常，请稍后再试！");
                }
            }
        }
        if (StringUtils.isEmpty(settlement_balance)) {
            //新增用户结算金额
            if (creditBillDao.insertSettlementBalance(param) <= 0) {
                throw new RuntimeException("还款审批异常，请稍后再试！");
            }
        } else {
            //更新用户结算金额
            if (creditBillDao.updateSettlementBalance(param) <= 0) {
                throw new RuntimeException("还款审批异常，请稍后再试！");
            }
        }
        //新增还款记录
        if (creditBillDao.insertCreditBillDetail(param) <= 0) {
            throw new RuntimeException("还款审批异常，请稍后再试！");
        }


        param.put("user_manager_name", param.get("user_name"));
        param.put("user_name", param.get("user_id"));
        //生成还款记录
        if (creditBillDao.insertUserAccountRecord(param) <= 0) {
            throw new RuntimeException("还款审批异常，请稍后再试！");
        }
        //更新月结还款审批状态
        if (creditBillDao.updateMonthlyRepaymentApproval(param) <= 0) {
            throw new RuntimeException("还款审批异常，请稍后再试！");
        }
        //更新用户授信余额信息
        if (creditBillDao.updateUserAccountInfo(param) <= 0) {
            throw new RuntimeException("还款审批异常，请稍后再试！");
        }
        //更新用户账户校验码
        creditBillDao.updateUserAccountCheckCode(param);

        // 当冻结金额大于0，需要见证宝冻结金额
        if(frozenAmount > 0) {
            //查询会员银行子账户
            String bankAccount = creditBillDao.queryBankAccountByUserId(param);

            // 请求见证宝资金冻结
            Map<String, Object> retMap = new HashMap<String, Object>();
            retMap.put("user_id", param.get("user_id"));
            retMap.put("bank_account", bankAccount);
            retMap.put("tran_amount", frozenAmount);
            retMap.put("order_number", param.get("user_id"));
            retMap.put("thirdLogNo", attesttreasDao.getThiredLogNo());              //交易流水号

            //请求见证宝会员资金冻结
            try {
                pr = HttpClientUtil.post(pay_service_url + "/tran/capital/frozen", retMap);
            }catch (SocketTimeoutException e) {
                // 记录超时异常记录
                retMap.put("tran_type", 6007);
                attesttreasTimeoutService.saveTimeoutException(retMap);
                throw new RuntimeException("冻结超时");
            }
            if (!pr.getState()) {
                throw new RuntimeException(pr.getMessage());
            } else {
                //异步发送还款成功通知
                messageService.asyncSendCreditBillMessage(param);
            }
        }

        return pr;
    }

    /**
     * 订单审核预充值
     *
     * @param param
     * @return
     * @throws Exception
     */
    @Transactional
    public ProcessResult orderAuditPrecharge(Map<String, Object> param) throws Exception {
        //查询会员账户信息
        Map<String, Object> userAccountMap = orderAuditingDao.queryUserAccountByPayTradeNumber(param.get("pay_trade_number").toString());
        if(userAccountMap == null || userAccountMap.isEmpty()) {
            throw new RuntimeException("用户信息不存在");
        }

        // 获取支付关联号关联订单的支付总额
        double payAmount = orderAuditingDao.queryPayAmountByPayTradeNumber(param.get("pay_trade_number").toString());
        if(payAmount <= 0){
            throw new RuntimeException("支付金额为0");
        }

        //更新预充值状态
        if(orderAuditingDao.updatePreChargeState(param.get("pay_trade_number").toString()) <= 0) {
            throw new RuntimeException("审批异常，请重试");
        }

        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("user_id", userAccountMap.get("USER_ID").toString());
        retMap.put("bank_account", userAccountMap.get("BANK_ACCOUNT").toString());
        retMap.put("rechange_number", param.get("pay_trade_number").toString());
        retMap.put("tran_amount", payAmount);
        retMap.put("order_number", param.get("pay_trade_number").toString());
        retMap.put("thirdLogNo", attesttreasDao.getThiredLogNo());              //交易流水号

        ProcessResult pr = new ProcessResult();

        // 请求见证宝会员充值
        try {
            pr = HttpClientUtil.post(pay_service_url + tran_rechange, retMap);
        }catch (SocketTimeoutException e) {
            // 记录超时异常记录
            retMap.put("tran_type", 6056);
            attesttreasTimeoutService.saveTimeoutException(retMap);
            throw new RuntimeException("支付超时");
        }
        if (!pr.getState()) {
            throw new RuntimeException(pr.getMessage());
        }
        return pr;
    }

    /**
     * 订单审核
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Transactional
    public ProcessResult orderAudit(Map<String, Object> map) throws Exception {
        ProcessResult pr = new ProcessResult();

        settlementDao.lockTable(map.get("pay_trade_number").toString());

        Map<String, Object> userAccountMap = orderAuditingDao.queryUserAccountByPayTradeNumber(map.get("pay_trade_number").toString());

        // 获取支付关联号关联订单的支付总额
        double payAmount = orderAuditingDao.queryPayAmountByPayTradeNumber(map.get("pay_trade_number").toString());

        orderAuditingDao.orderAuditing(map);
        String output_status = String.valueOf(map.get("output_status"));//状态 0-失败 1-成功
        String output_msg = String.valueOf(map.get("output_msg"));//当成功时为：验款成功!   当失败是：为错误消息内容
        if("1".equals(output_status)){//成功

            //如果执行的是审批通过则生成待清分记录
            if(map.get("check_type").toString().equals("1")){

                List<Map<String, Object>> orderInfoList = orderAuditingDao.queryOrderUnionPayList(map);
                if(orderInfoList == null || orderInfoList.isEmpty()) {
                    throw new RuntimeException("未获取到订单信息");
                }

                for(Map<String, Object> orderMap : orderInfoList) {
                    // 清分异步改造 -- by wanghai 20190710
                    Map<String, String> sendMap = new HashMap<String, String>();
                    sendMap.put("payTradeNumber", map.get("pay_trade_number").toString());
                    sendMap.put("orderNumber", orderMap.get("ORDER_NUMBER").toString());
                    sendMap.put("isSettlement", "1");
                    esbRabbitTemplate.convertAndSend(MqQueueKeyEnum.ESB_LIQUIDATION_ORDER_SETTLE.getKey(), Jackson.writeObject2Json(sendMap));
                }

                // 增加入驻资金流水记录
                //if(settlementDao.insertStationedCapitalLogs(map.get("pay_trade_number").toString()) <= 0){
                //    throw new RuntimeException("付款异常，请稍后再试！8");
                //}

                // 处理会员资金冻结
                // 请求见证宝资金冻结
                Map<String, Object> reqMap = new HashMap<String, Object>();
                reqMap.put("user_id", userAccountMap.get("USER_ID").toString());
                reqMap.put("bank_account", userAccountMap.get("BANK_ACCOUNT").toString());
                reqMap.put("tran_amount", payAmount);
                reqMap.put("order_number", map.get("pay_trade_number").toString());
                reqMap.put("thirdLogNo", attesttreasDao.getThiredLogNo());              //交易流水号

                try {
                    pr = HttpClientUtil.post(pay_service_url + tran_capital_frozen, reqMap);
                } catch (SocketTimeoutException e) {
                    // 记录超时异常记录
                    reqMap.put("tran_type", 6007);
                    attesttreasTimeoutService.saveTimeoutException(reqMap);
                    throw new RuntimeException("冻结超时");
                }
                if (!pr.getState()) {
                    throw new RuntimeException(pr.getMessage());
                }

                // 组装财务记账消息体
                Map<String, Object> messageMap = new HashMap<String, Object>();
                messageMap.put("user_name", userAccountMap.get("USER_ID").toString());
                messageMap.put("order_number", map.get("pay_trade_number").toString());
                messageMap.put("balance_money", "0");
                messageMap.put("credit_money", "0");
                messageMap.put("third_money", "");
                messageMap.put("third_type", "现金");
                messageMap.put("third_number", "");
                messageMap.put("pay_seq", 1);
                messageMap.put("operate_type", 1);
                messageMap.put("pay_type", "tk.cashpay.trade.create");

                // 发送财务记账消息
                esbRabbitTemplate.convertAndSend(MqQueueKeyEnum.ESB_FINANCE_PAYMENT_RECORD.getKey(), Jackson.writeObject2Json(messageMap));
            }

            pr.setState(true);
        }else{
            throw new RuntimeException("审核失败");
        }
        pr.setMessage(output_msg);

        return pr;
    }
}
