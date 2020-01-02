package com.tk.oms.decoration.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 装修预览
 * Created by wangpeng on 2017/5/23 0001.
 */
@Repository
public interface DecorateDataDao {

    /**
     * 获取站点导航列表
     *
     * @param params
     * @return
     */
	public List<Map<String,Object>> queryNavList(Map<String, Object> params);
	
	/**
     * 查询站点首页
     *
     * @param params
     * @return
     */
	public Map<String,Object> queryHomePageIdBySiteId(Map<String, Object> params);
	
	/**
     * 通过页面查询对应数据源
     *
     * @param params
     * @return
     */
	public Map<String,Object> queryModuleBaseConfByPageModuleId(Map<String, Object> params);
	
	/**
     * 【商品控件数据】  【按活动】
     * @param params
     * @return
     */
	public List<Map<String,Object>> queryProductForActivity(Map<String, Object> params);
	
	/**
     * 【商品控件数据】  【按分类】
     * @param params
     * @return
     */
	public List<Map<String,Object>> queryProductForType(Map<String, Object> params);
	
	/**
	 * 【商品控件数据】  【按特定商品】
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> queryProductForSelect(Map<String, Object> params);
	/**
	 * 【新闻控件】数据
	 * @param paramMap 
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryNewsListForModul(Map<String, Object> paramMap);
	
	/**
	 * 查询页面是否合法,返回数量
	 * @param paramMap 
	 * @param map
	 * @return
	 */
	public int queryPageCountBySitePageTemplate(Map<String, Object> paramMap);
}
