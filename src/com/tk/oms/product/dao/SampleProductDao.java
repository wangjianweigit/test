package com.tk.oms.product.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tk.sys.common.BaseDao;

@Repository
public interface SampleProductDao extends BaseDao<Map<String, Object>>{
	/**
	 * 商品数量
	 * @param paramMap
	 * @return
	 */
	int querySampleProductCount(Map<String, Object> paramMap);
	/**
	 * 商品列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> querySampleProductListForPage(Map<String, Object> paramMap);
	/**
	 * 删除
	 * @param paramMap
	 * @return
	 */
	int delete(Map<String, Object> paramMap);

}
