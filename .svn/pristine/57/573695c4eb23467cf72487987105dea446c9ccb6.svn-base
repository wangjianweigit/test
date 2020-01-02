package com.tk.oms.product.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tk.sys.common.BaseDao;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : ProductConsortiumDao
 * 联营门店商品管理DAO
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018-2-26
 */
@Repository
public interface ProductConsortiumDao extends BaseDao<Map<String, Object>> {
	/**
	 * 商品数量
	 * @param paramMap
	 * @return
	 */
	int queryProductCount(Map<String, Object> paramMap);
	/**
	 * 商品列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryProductListForPage(Map<String, Object> paramMap);
	/**
	 * 商品详情
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryProductConsortiumDetail(Map<String, Object> paramMap);
	/**
	 * 联营商品sku数量
	 * @param map 
	 * @return
	 */
	int queryProductConsortiumSkuCount(Map<String, Object> map);
	/**
	 * 商品是否被门店关联
	 * @param paramMap
	 * @return
	 */
	int queryProductStoreIsExists(Map<String, Object> paramMap);
	/**
	 * 查询sku零售价
	 * @param list
	 * @return
	 */
	List<Map<String, Object>> querySkuPrice(List<Map<String, Object>> list);
	/**
	 * 查询店铺ID
	 * @param paramMap
	 * @return
	 */
	List<String> queryStoreUserList(Map<String, Object> paramMap);
	/**
	 * 商品规格sku数量
	 * @param map
	 * @return
	 */
	int queryProductSkuCount(Map<String, Object> map);

}
