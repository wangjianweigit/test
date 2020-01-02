package com.tk.oms.contribution.dao;

import java.util.List;
import java.util.Map;

import com.tk.sys.common.BaseDao;

public interface ContributionOrderDao extends BaseDao<Map<String, Object>>{
	/**
	 * 查询缴款单列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryContributionOrderList(Map<String, Object> paramMap);
	/**
	 * 查询缴款单数量
	 * @param paramMap
	 * @return
	 */
	public int queryContributionOrderCount(Map<String, Object> paramMap);
	/**
	 * 查询缴款单详情
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryContributionOrderDetail(Map<String, Object> paramMap);
	/**
	 * 查询待缴款订单列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryContributionWaitList(Map<String, Object> paramMap);

	/**
	 * 缴款单审核与驳回
	 * @param paramMap
	 */
	public void paymentAuditing(Map<String, Object> paramMap);

}
