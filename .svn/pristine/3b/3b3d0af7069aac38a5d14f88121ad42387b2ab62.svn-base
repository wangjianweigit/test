package com.tk.oms.basicinfo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public interface ProductPopularKeyWordsDao {
	/**
     * 获取商品搜索关键字列表数据
     * @param request
     * @return
     */
	public List<Map<String,Object>> queryList(
			Map<String, Object> paramMap);
	/**
     * 商品搜索关键字添加
     * @param request
     * @return
     */
	public int insert(Map<String, Object> paramMap);
	/**
     * 商品搜索关键字编辑更新
     * @param request
     * @return
     */
	public int update(Map<String, Object> paramMap);
	/**
     * 商品搜索关键字删除
     * @param request
     * @return
     */
	public int remove(Map<String, Object> paramMap);
	/**
     * 获取商品搜索关键字数据数量
     * @param request
     * @return
     */
	public int queryCount(Map<String, Object> paramMap);
	  /**
     * 更新热门关键字状态
     *
     * @param storeInfo
     * @return
     */
    public int updateState(Map<String,Object> params);
    /**
	 * 更新排序
	 * @param paramMap
	 * @return
	 */
	public int updateSort(Map<String, Object> paramMap);
	/**
	 * 更新排序
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryById(long id);
}
