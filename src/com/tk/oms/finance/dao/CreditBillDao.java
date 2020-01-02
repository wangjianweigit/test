package com.tk.oms.finance.dao;

import java.util.List;
import java.util.Map;

import com.tk.sys.common.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditBillDao extends BaseDao<Map<String, Object>>{
	/**
	 * 资金清算数量
	 * @param paramMap
	 * @return
	 */
	public int queryCapitalSettlementCount(Map<String, Object> paramMap);
	/**
	 * 资金清算列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryCapitalSettlementList(Map<String, Object> paramMap);
	/**
	 * 账户概况
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryViewAccounts(Map<String, Object> paramMap);
	/**
	 * 已出账单数量
	 * @param paramMap
	 * @return
	 */
	public int queryBillQueryOkCount(Map<String, Object> paramMap);
	/**
	 * 已出账单列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryBillQueryOkList(Map<String, Object> paramMap);
	/**
	 * 未出账单数量
	 * @param paramMap
	 * @return
	 */
	public int queryBillQueryNoCount(Map<String, Object> paramMap);
	/**
	 * 未出账单列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryBillQueryNoList(Map<String, Object> paramMap);
	/**
	 * 账单详情
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryBillDetail(Map<String, Object> paramMap);
	/**
	 * 账单交易明细
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryBillDetailList(Map<String, Object> paramMap);
	/**
	 * 结算情况
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> querySettlementSituation(Map<String, Object> paramMap);
	/**
	 * 结算单数量
	 * @param paramMap
	 * @return
	 */
	public int querySettlementSituationCount(Map<String, Object> paramMap);
	/**
	 * 结算单列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> querySettlementSituationList(Map<String, Object> paramMap);
	/**
	 * 查询还款金额
	 * @param paramMap
	 * @return
	 */
	public String queryRepaymentAmountById(Map<String, Object> paramMap);
	/**
	 * 查询运费 代发费
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryOrderShareMoney(Map<String, Object> paramMap);
	/**
	 * 查询结算明细列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> querySettlementDetailsList(Map<String, Object> paramMap);
	/**
	 * 查询结算明细数量
	 * @param paramMap
	 * @return
	 */
	public int querySettlementDetailsCount(Map<String, Object> paramMap);
	/**
	 * 月结还款数量
	 * @param paramMap
	 * @return
	 */
	public int queryMonthlyRepaymentCount(Map<String, Object> paramMap);
	/**
	 * 月结还款列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryMonthlyRepaymentList(Map<String, Object> paramMap);
	
	/**
	 * 充值还款
	 * @return
	 */
	public int insertCreditRepaymentApply(Map<String, Object> paramMap);
	/**
	 * 还款记录数量
	 * @param paramMap
	 * @return
	 */
	public int queryMonthlyRepaymentHistoryCount(Map<String, Object> paramMap);
	/**
	 * 还款记录列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryMonthlyRepaymentHistoryList(Map<String, Object> paramMap);
	/**
	 * 生成交易号
	 * @return
	 */
	public String createTradeNumber();
	/**
	 * 月结还款审批数量
	 * @param paramMap
	 * @return
	 */
	public int queryMonthlyRepaymentApprovalCount(Map<String, Object> paramMap);
	/**
	 * 月结还款审批列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryMonthlyRepaymentApprovalList(Map<String, Object> paramMap);
	/**
	 * 月结还款审批详情
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryMonthlyRepaymentApprovalDetail(Map<String, Object> paramMap);
	/**
	 * 月结还款审批
	 * @param paramMap
	 * @return
	 */
	public int updateMonthlyRepaymentApproval(Map<String, Object> paramMap);
	/**
	 * 新增还款记录
	 * @param param
	 */
	public int insertCreditBillDetail(Map<String, Object> param);

	/**
	 * 新增退款记录
	 *
	 * @param param
	 * @return
	 */
	int insertCreditBillDetailOfReturn(Map<String, Object> param);
	/**
	 * 更新账单还款金额
	 * @param param
	 * @return
	 */
	public int updateCreditBill(Map<String, Object> param);
	/**
	 * 查询会员银行子账户
	 * @param param
	 * @return
	 */
	public String queryBankAccountByUserId(Map<String, Object> param);
	/**
	 * 是否登记
	 * @param paramMap 
	 * @return
	 */
	public int queryRegisterState(Map<String, Object> paramMap);
	/**
	 * 查询会员还款信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> queryUserRepaymentInfo(Map<String, Object> param);
	/**
	 * 查询本期账单
	 * @param param
	 * @return
	 */
	public Map<String, Object> queryCurrentBill(Map<String, Object> param);
	/**
	 * 订单付款金额
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> queryOrderPaymentMoney(Map<String, Object> param);

	/**
	 * 获取订单结算金额
	 *
	 * @param param
	 * @return
     */
	public List<Map<String, Object>> queryOrderSettlementAmount(Map<String, Object> param);
	/**
	 * 更新待结算单信息
	 * @param paramMap
	 */
	public int updateBussSettlementInfo(Map<String, Object> paramMap);
	/**
	 * 生成还款记录
	 * @param paramMap
	 * @return
	 */
	public int insertUserAccountRecord(Map<String, Object> paramMap);
	/**
	 * 用户账户信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> queryBankAccount(Map<String, Object> param);
	/**
	 * 获取用户key
	 * @param param
	 * @return
	 */
	public String queryUserKey(Map<String, Object> param);
	/**
	 * 获取授信校验码
	 * @param param
	 * @return
	 */
	public String queryCreateCode(Map<String, Object> param);
	/**
	 * 更新登记状态
	 * @param param
	 */
	public int updateRegisterState(Map<String, Object> param);
	/**
	 * 更新用户授信余额信息
	 * @param param
	 * @return
	 */
	public int updateUserAccountInfo(Map<String, Object> param);
	/**
	 * 更新用户账户校验码
	 * @param param
	 * @return
	 */
	public int updateUserAccountCheckCode(Map<String, Object> param);
	/**
	 * 查询用户结算金额
	 * @param param
	 * @return
	 */
	public String querySettlementBalance(Map<String, Object> param);
	/**
	 * 新增用户结算金额
	 * @param param
	 */
	public int insertSettlementBalance(Map<String, Object> param);
	/**
	 * 更新用户结算金额
	 * @param param
	 * @return
	 */
	public int updateSettlementBalance(Map<String, Object> param);
	/**
	 * 审核数据校验
	 * @param paramMap
	 * @return
	 */
	public int queryMonthlyRepaymentById(Map<String, Object> paramMap);
	/**
	 * 查询下期账单年、月
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryNextYearAndMonth(Map<String, Object> paramMap);
	/**
	 * 查询下期账单ID
	 * @param paramMap
	 * @return
	 */
	public String queryNextBillId(Map<String, Object> paramMap);

	/**
	 * 查询用户欠款金额
	 *
	 * @return
     */
	public Double queryUserDebtAmount(@Param("user_id") long userId);

	/**
	 * 更新用户欠款金额
	 *
	 * @param paramMap
	 * @return
     */
	int updateUserDebtAmount(Map<String, Object> paramMap);

	/**
	 * 更新发货待结算单信息
	 *
	 * @param paramMap
	 * @return
     */
	int updateBussSettlementShipmentsInfo(Map<String, Object> paramMap);

	/**
	 * 更新服务费发货待结算状态
	 *
	 * @param paramMap
	 * @return
	 */
	int updateFeeSettlementShipmentsInfo(Map<String, Object> paramMap);
}
