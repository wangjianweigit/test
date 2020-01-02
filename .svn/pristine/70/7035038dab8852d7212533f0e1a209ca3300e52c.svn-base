package com.tk.store.statistic.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface LyHomeWorkbenchDao {
	
	/**
	 * 查询商品信息
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryProductInfo(Map<String, Object> paramMap);
	
	/**
     * 查询指定区域内的店铺会员
     * @param paramMap
     * @return
     */
	public List<String> queryStoreListByArea(Map<String, Object> paramMap);
	
	/**
	 * 新增调拨单主表信息
	 * @param paramMap
	 * @return
	 */
	public int insertStoreAllotTask(Map<String, Object> paramMap);
	
	/**
	 * 新增调拨单详情表信息
	 * @param paramMap
	 * @return
	 */
	public int insertStoreAllotTaskDetail(Map<String, Object> paramMap);
	
	/**
	 * 根据调入调出门店查询调拨任务信息
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryStoreAllotTaskByInOutStoreId(Map<String, Object> paramMap);
	
	/**
	 * 查询调拨任务数量
	 * @param paramMap
	 * @return
	 */
	public int queryStoreAllotTask(Map<String, Object> paramMap);
	
	/**
     * 联营工作台查询调拨任务列表
     * @param paramMap
     * @return
     */
	public List<Map<String, Object>> queryStoreAllotTaskList(Map<String, Object> paramMap);
	
	/**
	 * 查询调拨任务详情
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryStoreAllotTaskDetail(Map<String, Object> paramMap);
	
	/**
	 * 删除调拨任务主表信息
	 * @param paramMap
	 * @return
	 */
	public int deleteStoreAllotTask(Map<String, Object> paramMap);
	
	/**
	 * 删除调拨任务详情表信息
	 * @param paramMap
	 * @return
	 */
	public int deleteStoreAllotTaskDetail(Map<String, Object> paramMap);
	
	/**
     * 查询当前任务单所有货号
     * @param paramMap
     * @return
     */
	public List<String> queryStoreAllotTaskItemnumber(Map<String, Object> paramMap);
	
	/**
     * 查询当前调拨任务单的所有货号信息
     * @param paramMap
     * @return
     */
	public List<Map<String, Object>> queryStoreAllotTaskItemnumberInfoList(Map<String, Object> paramMap);
	
	/**
	 * 查询当前调拨任务单里货号是否已存在
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryStoreAllotTaskDetailByTaskIdAndItemnumber(Map<String, Object> paramMap);
	
	/**
     * 根据商家id查询门店id
     * @param paramMap
     * @return
     */
	public List<String> queryUserStores(Map<String, Object> paramMap);
	
	/**
	 * 将经销商门店id转换为本地id
	 * @param paramMap
	 * @return
	 */
	public String queryStoreIdByAgentStoreId(Map<String, Object> paramMap);
}
