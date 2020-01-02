package com.tk.oms.finance.dao;

import com.tk.sys.common.BaseDao;

import java.util.List;
import java.util.Map;

public interface RechargeAuditingDao extends BaseDao<Map<String, Object>>{
	/**
	 * 充值审批列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryRechargeAuditingList(Map<String, Object> paramMap);
	/**
	 * 充值审批数量
	 * @param paramMap
	 * @return
	 */
	public int queryRechargeAuditingCount(Map<String, Object> paramMap);
	/**
	 * 充值审批详情
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryRechargeAuditingDetail(Map<String, Object> paramMap);
	/**
	 * 充值审批通过，驳回
	 * @param map
	 */
	public void rechargeAuditing(Map<String, Object> map);
	

}
