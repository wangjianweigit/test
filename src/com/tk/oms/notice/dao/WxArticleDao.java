package com.tk.oms.notice.dao;

import java.util.List;
import java.util.Map;

import com.tk.sys.common.BaseDao;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : WxArticleDao.java
 * 微信文章管理
 * Dao层
 * 做数据持久的工作
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-10-25
 */
public interface WxArticleDao extends BaseDao<Map<String, Object>>{
	/**
	 * 查询文章数量
	 * @param paramMap
	 * @return
	 */
	public int queryCount(Map<String, Object> paramMap);
	/**
	 * 查询文章列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryList(Map<String, Object> paramMap);
	/**
	 * 查询文章详情
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryDetail(Map<String, Object> paramMap);
	/**
	 * 校验文章标题是否存在
	 * @param paramMap
	 * @return
	 */
	public int isExist(Map<String, Object> paramMap);
	/**
	 * 更新排序
	 * @param map
	 * @return
	 */
	public int updateSort(Map<String, Object> map);
	
}
