package com.tk.store.member.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
/**
 * 
 * Copyright (c), 2018, Tongku
 * FileName : StoreMemberDao.java
 * 会员管理DAO层
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018年6月11日
 */
@Repository
public interface StoreMemberDao {
	/**
	 * 会员列表
	 * @param list
	 * @return
	 */
	List<Map<String, Object>> queryStoreMemberList(List<Map<String, Object>> list);
	/**
	 * 会员详情
	 * @param map
	 * @return
	 */
	Map<String, Object> queryStoreMemberDetail(Map<String, Object> map);
	
	/**
	 * 门店下拉框
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> queryUserStoreList(Map<String, Object> params);
	/**
	 * 获取新零售门店ID
	 * @param paramMap
	 * @return
	 */
	String queryAgentStoreId(Map<String, Object> paramMap);
	/**
	 * 区域下拉框
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> userSelect(Map<String, Object> params);

}
