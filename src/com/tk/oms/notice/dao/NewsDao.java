package com.tk.oms.notice.dao;

import java.util.List;
import java.util.Map;

import com.tk.sys.common.BaseDao;

public interface NewsDao extends BaseDao<Map<String, Object>>{
	/**
	 * 查询新闻数量
	 * @param paramMap
	 * @return
	 */
	public int queryNewsCount(Map<String, Object> paramMap);
	/**
	 * 查询新闻列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryNewsList(Map<String, Object> paramMap);
	/**
	 * 查询新闻详情
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryNewsDetail(Map<String, Object> paramMap);
	/**
	 * 是否存在相同标题
	 * @param paramMap
	 * @return
	 */
	public int isExist(Map<String, Object> paramMap);
	/**
	 * 新增新闻
	 * @param paramMap
	 */
	public int insertNews(Map<String, Object> paramMap);
	/**
	 * 编辑新闻
	 * @param paramMap
	 * @return
	 */
	public int updateNews(Map<String, Object> paramMap);
	/**
	 * 更新排序
	 * @param t1
	 * @return
	 */
	public int updateSort(Map<String, Object> t1);

}
