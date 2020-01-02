package com.tk.oms.finance.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : StoreContributoryDao
 * 店铺缴款Dao层
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018-3-8
 */
@Repository
public interface StoreContributoryDao {
	/**
	 * 店铺缴款单数量
	 * @param paramMap
	 * @return
	 */
	int queryListCount(Map<String, Object> paramMap);
	/**
	 * 店铺缴款单列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryListForPage(Map<String, Object> paramMap);
	/**
	 * 店铺缴款详情
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryStoreContributoryDetail(Map<String, Object> paramMap);
	/**
	 * 店铺缴款交易单列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryStoreContributoryTradeList(Map<String, Object> paramMap);
	/**
	 * 审批
	 * @param paramMap
	 * @return
	 */
	int auditStoreContributory(Map<String, Object> paramMap);
	/**
	 * 校验审批状态
	 * @param paramMap
	 * @return
	 */
	int queryStoreContributoryIsExists(Map<String, Object> paramMap);
	/**
	 * 更新付款状态
	 * @param paramMap
	 * @return
	 */
	int updateStoreTrade(Map<String, Object> paramMap);
	/**
	 * 更新清分状态
	 * @param paramMap
	 * @return
	 */
	int updateStoreTradeDivide(Map<String, Object> paramMap);
	
}
