package com.tk.oms.finance.dao;

import com.tk.sys.common.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderAuditingDao extends BaseDao<Map<String, Object>>{
	/**
	 * 订单审核列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryOrderAuditingList(Map<String, Object> paramMap);
	/**
	 * 订单审核数量
	 * @param paramMap
	 * @return
	 */
	public int queryOrderAuditingCount(Map<String, Object> paramMap);
	/**
	 * 订单审核详情
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryOrderAuditingDetail(Map<String, Object> paramMap);
	/**
	 * 订单审核 通过,驳回
	 * @param map
	 */
	public void orderAuditing(Map<String, Object> map);
	/**
	 * 查询交易关联订单
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryOrderUnionPayList(Map<String, Object> paramMap);

	/**
	 * 查询现金支付预充值状态
	 *
	 * @param pay_trade_number
	 * @return
     */
	String queryPayPrechargeState(@Param("pay_trade_number") String pay_trade_number);

	/**
	 * 根据支付关联号查询会员ID和银行子账户
	 *
	 * @param pay_trade_number
	 * @return
     */
	Map<String, Object> queryUserAccountByPayTradeNumber(@Param("pay_trade_number") String pay_trade_number);

	/**
	 * 根据支付关联号查询订单支付总额
	 *
	 * @param pay_trade_number
	 * @return
     */
	double queryPayAmountByPayTradeNumber(@Param("pay_trade_number") String pay_trade_number);

	/**
	 * 更新现金支付预充值状态
	 *
	 * @param pay_trade_number
	 * @return
     */
	int updatePreChargeState(@Param("pay_trade_number") String pay_trade_number);
}
