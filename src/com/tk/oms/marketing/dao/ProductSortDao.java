package com.tk.oms.marketing.dao;

import java.util.List;
import java.util.Map;

import com.tk.sys.common.BaseDao;


public interface ProductSortDao extends BaseDao<Map<String, Object>>{
	/**
	 * 更新规则权重百分比
	 * @param list
	 * @return
	 */
	public int updateWeightPercent(List<Map<String, Object>> list);
	/**
	 * 查询规则列表
	 * @return
	 */
	public List<Map<String, Object>> querySortRole();
	/**
	 * 查询人工加权商品总数
	 * @param paramMap
	 * @return
	 */
	public int queryProductCount(Map<String, Object> paramMap);
	/**
	 * 查询人工加权商品列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryProductList(Map<String, Object> paramMap);
	/**
	 * 新增人工加权
	 * @param productMap
	 * @return
	 */
	public int insertProductWeighting(Map<String, Object> productMap);
	/**
	 * 更新人工加权
	 * @param productMap
	 * @return
	 */
	public int updateProductWeighting(Map<String, Object> productMap);
	/**
	 * 发布人工加权
	 * @param paramMap
	 * @return
	 */
	public int releaseProductWeighting(Map<String, Object> paramMap);
	/**
	 * 根据站点ID和货号查询站点商品配置信息
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> querySiteProduct(Map<String, Object> paramMap);
	/**
	 *上新商品查询上新商品列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryNewProductList(Map<String, Object> paramMap);
	/**
	 *上新商品查询上新商品总数
	 * @param paramMap
	 * @return
	 */
	public int queryNewProductCount(Map<String, Object> paramMap);
	/**
	 *查询货号销量
	 * @param paramMap
	 * @return
	 */
	public int querySaleVolume(Map<String, Object> paramMap);
	/**
	 *当前货号排序值设为最大或最小
	 * @param paramMap
	 * @return
	 */
	public int updateMaxMinFirstSellSortValue(Map<String, Object> paramMap);
	/**
	 *将当前商品排序值加上升值
	 * @param paramMap
	 * @return
	 */
	public int updateFirstSellSortValue(Map<String, Object> paramMap);
	/**
	 *将当前商品排序值增加或减少
	 * @param paramMap
	 * @return
	 */
	public int updateRiseOrDownFirstSellSortValue(Map<String, Object> paramMap);
	/**
	 * 查询当前商品排序值
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryProductFirstSellSortValue(Map<String, Object> paramMap);
	/**
	 *查询最大排名
	 * @param paramMap
	 * @return
	 */
	public int queryProductMaxNum();
	
	
	
}
