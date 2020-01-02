package com.tk.oms.basicinfo.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface ClassifyDao extends BaseDao<Map<String, Object>>{
	
	/**
	 * 查询分类总数
	 * @param paramMap
	 * @return
	 */
	public int queryClassifyCount(Map<String, Object> paramMap);
	/**
	 * 查询分类列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryClassifyList(Map<String, Object> paramMap);

	/**
	 * 查询大类
	 * @return
	 */
	public List<Map<String, Object>> queryLargeClass();
	/**
	 * 新增分类
	 * @param paramMap
	 */
	public int addClassify(Map<String, Object> paramMap);
	/**
	 * 更新分类
	 * @param paramMap
	 */
	public int updateClassify(Map<String, Object> paramMap);
	/**
	 * 删除分类
	 * @param paramMap
	 */
	public int deleteClassify(Map<String, Object> paramMap);
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
	public int addClassifyInfo(Map<String, Object> paramMap);
	/**
	 * 更新子类信息
	 * @param paramMap
	 */
	public int updateClassifyInfo(Map<String, Object> paramMap);
	/**
	 * 删除子类信息
	 * @param id
	 */
	public int deleteClassifyInfo(Map<String, Object> paramMap);

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
	public Map<String, Object> queryClassifyDetail(Map<String, Object> map);

	/**
	 * 更新排序
	 * @param paramMap
	 * @return
	 */
	public int updateClassifySort(Map<String, Object> paramMap);

	/**
	 * 更新子类排序
	 * @param paramMap
	 * @return
	 */
	public int updateClassifyInfoSort(Map<String, Object> paramMap);

	/**
	 * 是否启用
	 * @param paramMap
	 */
	public void updateClassifyState(Map<String, Object> paramMap);

	/**
	 * 查询关联数据数量
	 * @param paramMap
	 * @return
	 */
	public int selectClassifyInfoCount(Map<String, Object> paramMap);
	/**
	 * 分类下拉框
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryClassifyCombobox(Map<String, Object> paramMap);
	
}
