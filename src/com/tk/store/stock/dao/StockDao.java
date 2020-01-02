package com.tk.store.stock.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface StockDao {
	/**
	 * 新零售门店下拉框
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> storeSelect(Map<String, Object> paramMap);
	/**
	 * 查询商家ID
	 * @param paramMap
	 * @return
	 */
	List<String> queryAgentId(Map<String, Object> paramMap);
	/**
	 * 查询调拨任务数量
	 * @param paramMap
	 * @return
	 */
	int queryStoreAllotTaskCount(Map<String, Object> paramMap);
	/**
	 * 查询调拨任务列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryStoreAllotTaskListForPage(Map<String, Object> paramMap);
	/**
	 * 查询调拨任务商品数量
	 * @param paramMap
	 * @return
	 */
	int queryStoreAllotTaskProductCount(Map<String, Object> paramMap);
	/**
	 * 查询调拨任务商品列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryStoreAllotTaskProductListForPage(Map<String, Object> paramMap);
	/**
	 * 查询调拨任务信息
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryStoreAllotTaskInfo(Map<String, Object> paramMap);
	/**
	 * 新增调拨任务
	 * @param paramMap
	 * @return
	 */
	int insertStoreAllotTask(Map<String, Object> paramMap);
	/**
	 * 调拨任务商品是否存在
	 * @param paramMap
	 * @return
	 */
	int queryTaskProductIsExists(Map<String, Object> paramMap);
	/**
	 * 新增调拨任务明细
	 * @param paramMap
	 * @return
	 */
	int insertStoreAllotTaskDetail(Map<String, Object> paramMap);
	/**
	 * 获取门店ID
	 * @param paramMap
	 * @return
	 */
	String queryStoreId(Map<String, Object> paramMap);

}
