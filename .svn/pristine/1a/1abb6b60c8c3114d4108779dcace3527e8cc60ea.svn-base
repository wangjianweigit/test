package com.tk.oms.finance.dao;

import com.tk.sys.common.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *
 * Copyright (c), 2017, Tongku
 * FileName : WithdrawalDao
 * 提现数据访问接口
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/6/13 16:18
 */
@Repository
public interface ServerBusinessWithdrawalDao extends BaseDao<Map<String, Object>> {

    /**
     * 获取服务商已提现金额
     * @param params
     * @return
     */
    float queryWithdrawalAmount(Map<String, Object> params);

    /**
     * 保存提现收支记录
     * @param params
     * @return
     */
    int insertRevenueRecord(Map<String, Object> params);

    /**
     * 获取提现审批明细
     * @param id 提现申请id
     */
    Map<String,Object> queryWithdrawalApprovalDetail(long id);

    /**
     * 获取提现审批明细
     * @param params
     */
    List<Map<String,Object>> queryWithdrawalApprovalList(Map<String, Object> params);

    /**
     * 获取提现审批记录数
     * @param params
     * @return
     */
    int queryWithdrawalApprovalCount(Map<String, Object> params);

    /**
     * 审批成功服务商提现打款
     * @param params
     * @return
     */
    int withdrawPay(Map<String, Object> params);

    /**
     * 根据银行卡号查询提现记录数
     * @param bank_card_id
     * @return
     */
    int queryWithdrawalApprovalCountByBankCardId(long bank_card_id);

    /**
     * 服务商收支记录数量
     * @param paramMap
     * @return
     */
    int queryBalanceOfPaymentsForCount(Map<String, Object> paramMap);

    /**
     * 服务商收支记录列表
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> queryBalanceOfPaymentsForList(Map<String, Object> paramMap);

    /**
     * 获取服务商账户数据
     *
     * @param params
     * @return
     */
    Map<String, Object> queryFacilitator(Map<String, Object> params);

    /**
     * 根据user_id获取服务商账户数据
     *
     * @param user_id
     * @return
     */
    Map<String, Object> queryFacilitatorByUserId(long user_id);

}
