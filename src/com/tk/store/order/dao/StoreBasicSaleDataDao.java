package com.tk.store.order.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface StoreBasicSaleDataDao {
	
	/**
	 * 获取商家数据
	 */
	public Map<String,Object> queryAgentId(Map<String,Object> paramMap);
	/**
	 * 获取商家下拉框数据(新零售经销商ID)
	 */
	public List<Map<String,Object>> queryAgentIdOption(Map<String,Object> paramMap);
	/**
	 * 查询门店下拉数据(新零售门店ID)
	 */
	public List<Map<String,Object>> queryStoreListOption(Map<String,Object> paramMap);
	/**
	 * 查询门店经销商ID
	 */
	public String queryAgentStoreId(Map<String,Object> paramMap);
	/**
	 * 根据会员经销商ID查询会员信息
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> queryUserInfoByAgentId(List<Map<String, Object>> list);

}
