package com.tk.store.finance.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface StoreMoneyDao {
	
	/**
	 * 门店资金查询列表总数
	 * @param paramMap
	 * @return
	 */
	int queryStoreMoneyCount(Map<String, Object> paramMap);
	/**
	 * 门店资金查询列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryStoreMoneyListForPage(Map<String, Object> paramMap);
	/**
	 * 查询账户余额详情列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryStoreAccountDetailList(Map<String, Object> paramMap);
	/**
	 * 查询账户余额详情列表总数
	 * @param paramMap
	 * @return
	 */
	int queryStoreAccountDetailCount(Map<String, Object> paramMap);
	/**
	 * 查询收支记录收入支出金额
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryStoreIncomeSpendMoney(Map<String, Object> paramMap);
	/**
	 * 查询待结算金额记录
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryStoreWaitSettleDetail(Map<String, Object> paramMap);
	/**
	 * 查询待结算列表数量
	 * @param paramMap
	 * @return
	 */
	int queryStoreWaitSettleDetailCount(Map<String, Object> paramMap);
	/**
	 * 新增押金记录
	 * @param paramMap
	 * @return
	 */
	int insertStoreDepositRecharge(Map<String, Object> paramMap);
	/**
	 * 押金记录列表总数
	 * @param paramMap
	 * @return
	 */
	int queryStoreMoneyDepositRechargeRecordCount(Map<String, Object> paramMap);
	/**
	 * 押金记录列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryStoreMoneyDepositRechargeRecordList(Map<String, Object> paramMap);
	/**
	 * 押金审批详情
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryStoreMoneyDepositRechargeApprovalDetail(Map<String, Object> paramMap);
	/**
	 * 押金充值审批
	 * @param paramMap
	 * @return
	 */
	int updateStoreDepositRechargeState(Map<String, Object> paramMap);
	/**
	 * 更新店铺账户余额
	 * @param paramMap
	 * @return
	 */
	int updateStoreBankAccountBalance(Map<String, Object> paramMap);
	/**
	 * 新增店铺押金充值记录
	 * @param paramMap
	 * @return
	 */
	int insertStoreGoodsDepositAccountRecord(Map<String, Object> paramMap);
	/**
	 * 新增货品押金充值记录
	 * @param paramMap
	 * @return
	 */
	int insertGoodsDepositAccountRecord(Map<String, Object> paramMap);
	/**
	 * 查询店铺账户信息
	 * @param paramMap
	 * @return
	 */
	Map<String,Object> queryStoreBankAccountForUpdate(Map<String, Object> paramMap);
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
	public Map<String,Object> getCheck_Code(Map<String, Object> param);
	/**
	 * 更新账户账户key
	 * @param param
	 * @return
	 */
	public int updateSysUserCacheKey(Map<String, Object> param);

}
