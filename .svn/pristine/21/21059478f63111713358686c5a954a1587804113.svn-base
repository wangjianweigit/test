package com.tk.store.marking.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
@Repository
public interface StoreMarketActivityDao {
	
	/**
     * 分页查询店铺营销活动列表
     * @param paramMap
     * @return
     */
	public List<Map<String, Object>> queryStoreMarketActivityList(Map<String, Object> paramMap);
	
	/**
     * 查询店铺营销活动数量
     * @param paramMap
     * @return
     */
	public int queryStoreMarketActivityCount(Map<String, Object> paramMap);
	
	/**
     * 店铺营销活动新增
     * @param paramMap
     * @return
     */
	public int insertStoreMarketActivity(Map<String, Object> paramMap);
	
	/**
     * 店铺营销活动商品新增
     * @param paramMap
     * @return
     */
	public int insertStoreMarketActivityProduct(List<Map<String, Object>> user_list);
	
	/**
     * 店铺营销活动编辑
     * @param paramMap
     * @return
     */
	public int updateStoreMarketActivity(Map<String, Object> paramMap);
	
	/**
     * 店铺营销活动删除
     * @param paramMap
     * @return
     */
	public int deleteStoreMarketActivity(Map<String, Object> paramMap);
	
	/**
     * 店铺营销活动商品删除
     * @param paramMap
     * @return
     */
	public int deleteStoreMarketActivityProduct(Map<String, Object> paramMap);
	
	/**
	 * 商品库数量
	 * @param paramMap
	 * @return
	 */
	int queryProductLibraryCount(Map<String, Object> paramMap);
	
	/**
	 * 商品库列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryProductLibraryListForPage(Map<String, Object> paramMap);
	
	/**
     * 查询店铺营销活动详情
     * @param paramMap
     * @return
     */
	public Map<String, Object> queryStoreMarketActivityDetail(Map<String, Object> paramMap);
	
	/**
     * 查询店铺营销活动商品
     * @param paramMap
     * @return
     */
	public List<Map<String,Object>> queryStoreMarketActivityProduct(Map<String, Object> paramMap);
	
	/**
     * 店铺营销活动审批
     * @param paramMap
     * @return
     */
	public int queryStoreMarketActivityApproval(Map<String, Object> paramMap);
	
	/**
     * 查询店铺营销活动所有货号
     * @param paramMap
     * @return
     */
	public List<String> queryStoreMarketActivityItemnumber(Map<String, Object> paramMap);
	
	/**
     * 查询商家下面的店铺列表
     * @param paramMap
     * @return
     */
	public List<Map<String,Object>> queryUserStoreList(Map<String, Object> paramMap);
	

}
