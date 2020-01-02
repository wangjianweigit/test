package com.tk.oms.basicinfo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
@Repository
public interface HelpCenterDao extends BaseDao<Map<String, Object>>{
	
	/**
	 * 新闻中心分类总数
	 * @param paramMap
	 * @return
	 */
	public int queryHelpCenterCount(Map<String, Object> paramMap);
	/**
	 * 新闻中心分类列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryHelpCenterList(Map<String, Object> paramMap);

	/**
	 * 查询大类
	 * @return
	 */
	public List<Map<String, Object>> queryLargeClass();
	/**
	 * 新闻中心新增分类
	 * @param paramMap
	 */
	public int addHelpCenter(Map<String, Object> paramMap);
	/**
	 * 新闻中心更新分类
	 * @param paramMap
	 */
	public int updateHelpCenter(Map<String, Object> paramMap);
	/**
	 * 新闻中心删除分类
	 * @param paramMap
	 */
	public int deleteHelpCenter(Map<String, Object> paramMap);
	/**
	 * 查询视频列表总数
	 * @param map
	 * @return
	 */
	public int queryVideoListCount(Map<String, Object> map);
	/**
	 * 查询视频列表
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryVideoList(Map<String, Object> map);
	/**
	 * 查询知识库列表总数
	 * @param map
	 * @return
	 */
	public int queryRepositoryListCount(Map<String, Object> map);
	/**
	 * 查询知识库列表
	 * 
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryRepositoryList(Map<String, Object> map);
	/**
	 * 新增子类信息
	 * @param paramMap
	 */
	public int addHelpCenterInfo(Map<String, Object> paramMap);
	/**
	 * 更新子类信息
	 * @param paramMap
	 */
	public int updateHelpCenterInfo(Map<String, Object> paramMap);
	/**
	 * 删除子类信息
	 * @param id
	 */
	public int deleteHelpCenterInfo(Map<String, Object> paramMap);

	/**
	 * 查询视频和知识库列表
	 * @param map
	 * @return
	 */
	public Map<String, Object> queryLargeClassifyById(Map<String, Object> map);
	/**
	 * 查询子类明细
	 * @param map
	 * @return
	 */
	public Map<String, Object> queryHelpCenterDetail(Map<String, Object> map);

	/**
	 * 更新排序
	 * @param paramMap
	 * @return
	 */
	public int updateHelpCenterSort(Map<String, Object> paramMap);

	/**
	 * 更新子类排序
	 * @param paramMap
	 * @return
	 */
	public int updateHelpCenterInfoSort(Map<String, Object> paramMap);

	/**
	 * 是否启用
	 * @param paramMap
	 */
	public void updateHelpCenterState(Map<String, Object> paramMap);

	/**
	 * 查询关联数据数量
	 * @param paramMap
	 * @return
	 */
	public int queryHelpCenterInfoCount(Map<String, Object> paramMap);
	/**
	 * 分类下拉框
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryHelpCenterCombobox(Map<String, Object> paramMap);

}
