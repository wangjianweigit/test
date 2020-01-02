package com.tk.oms.basicinfo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface MqErrorLogDao {
	/**
	 * 查询mq错误日志总数
	 * @param paramMap
	 * @return
	 */
	int queryMqErrorLogCount(Map<String, Object> paramMap);
	/**
	 * 查询mq错误日志列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryMqErrorLogListForPage(Map<String, Object> paramMap);
	/**
	 * 更新错误日志
	 * @param paramMap
	 * @return
	 */
	int updateMqErrorLog(Map<String, Object> paramMap);
	/**
	 * 查询错误日志详情
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryMqErrorLogDetail(Map<String, Object> paramMap);
	/**
	 * 批量更新错误日志
	 * @param paramMap
	 * @return
	 */
	int batchUpdateMqErrorLog(Map<String, Object> paramMap);
	/**
	 * 查询错误日志列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryMqErrorLogList(Map<String, Object> paramMap);
	/**
	 * 查询联营商家关联的商品
	 * @return
	 */
	List<Map<String, Object>> queryStoreProductList();
	/**
	 * 查询商品吊牌价
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> queryProductPrizeTag(Map<String, Object> param);
	
}
