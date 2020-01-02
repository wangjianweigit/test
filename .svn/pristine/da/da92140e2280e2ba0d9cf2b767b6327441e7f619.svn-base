package com.tk.store.finance.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
/**
 * 
 * Copyright (c), 2018, Tongku
 * FileName : AccountBalanceDao.java
 * 账户余额dao层
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018-3-9
 */
@Repository
public interface AccountBalanceDao {
	/**
	 * 账户余额
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryAccountBalance(Map<String, Object> paramMap);
	/**
	 * 收支记录数量
	 * @param paramMap
	 * @return
	 */
	int queryAccountRecordCount(Map<String, Object> paramMap);
	/**
	 * 收支记录列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryAccountRecordListForPage(Map<String, Object> paramMap);
	/**
	 * 获取用户银行卡信息
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryBankInfo(Map<String, Object> paramMap);

}
