package com.tk.oms.marketing.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface ActivityDao {

	/**
	 * 获取促销活动列表
	 */
	public List<Map<String,Object>> queryActivityList(Map<String,Object> paramMap);
	/**
	 * 获取促销活动总数
	 */
	public int queryActivityCount(Map<String,Object> paramMap);
	/**
	 * 获取促销活动基本信息详情
	 */
	public Map<String, Object> queryActivityDetail(Map<String,Object> paramMap);
	
	/**
	 * 更新促销活动信息表（审核通过-驳回）
	 */
	public int updatePendingApprovalActivityState(Map<String, Object> paramMap);
	/**
	 * 更新促销活动信息表（提交审批）
	 */
	public int updateActivityStateForApproval(Map<String, Object> paramMap);
	/**
	 * 获取活动ID（通过序列生成）
	 */
	public int queryActivityId(Map<String, Object> baseParamMap);
	/**
	 * 插入活动基本信息
	 */
	public int insertActivity(Map<String, Object> baseParamMap);
	/**
	 * 更新活动基本信息
	 */
	public int updateActivity(Map<String, Object> paramMap);
	/**
	 * 更新活动基本信息
	 */
	public int updateActivityDecorate(Map<String, Object> paramMap);
	/**
	 * 删除活动基本信息
	 */
	public int deleteActivity(Map<String, Object> paramMap);
	/**
	 * 逻辑删除活动基本信息
	 */
	public int updateActivityIsDelete(Map<String, Object> paramMap);
	/**
	 * 活动启停用
	 */
	public int updateActivityState(Map<String, Object> paramMap);
	/**
	 * 活动站点列表
	 */
	public List<Map<String, Object>> queryActivitySitesList(Map<String, Object> paramMap);
	/**
	 * 删除活动站点配置
	 */
	public int deleteActivitySite(Map<String, Object> paramMap);
	/**
	 * 插入活动站点配置
	 */
	public int insertActivitySite(Map<String, Object> paramMap);


	public int countGoingActivityProduct(Map<String, Object> paramMap);
}