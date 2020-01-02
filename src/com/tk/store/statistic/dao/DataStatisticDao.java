package com.tk.store.statistic.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface DataStatisticDao {

	/**
	 * 查询商家ID
	 * @param paramMap
	 * @return
	 */
	List<String> queryAgentId(Map<String, Object> paramMap);
	/**
	 * 查询门店ID
	 * @param paramMap
	 * @return
	 */
	List<String>  queryStoreId(Map<String, Object> paramMap);
	/**
	 * 查询进销存列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryPsiList(List<Map<String, Object>> list);
	/**
	 * 查询门店在线支付总数
	 * @param paramMap
	 * @return
	 */
	int queryPayOnlineCount(Map<String, Object> paramMap);
	/**
	 * 查询门店在线支付列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryPayOnlineListForPage(Map<String, Object> paramMap);

}
