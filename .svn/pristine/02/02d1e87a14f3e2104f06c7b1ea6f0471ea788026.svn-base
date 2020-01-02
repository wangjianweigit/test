package com.tk.oms.marketing.dao;

import java.util.List;
import java.util.Map;

import com.tk.sys.common.BaseDao;

public interface RecommendedDao extends BaseDao<Map<String, Object>>{
	/**
	 * 查询推荐列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryRecommendedList(Map<String, Object> paramMap);
	/**
	 * 查询推荐信息总数
	 * @param paramMap
	 * @return
	 */
	public int queryRecommendedCount(Map<String, Object> paramMap);
	/**
	 * 新增推荐信息
	 * @param paramMap
	 * @return
	 */
	public int insert(Map<String, Object> paramMap);
	/**
	 * 新增推荐信息
	 * @param paramMap
	 * @return
	 */
	public int insertNew(Map<String, Object> paramMap);
	/**
	 * 删除推荐信息 
	 * @param paramMap
	 * @return
	 */
	public int delete(Map<String, Object> paramMap);
	/**
	 * 检测推荐名称是否重复
	 * @param info
	 * @return  0：不存在，可用 ；  1：已经存在，不可用
	 */
	public int checkRecommendedName(Map<String, Object> paramMap);
	/**
	 * 批量插入推荐商品信息
	 * @param list
	 * @return
	 */
	public int batchInsertRecommendedProduct(List<Map<String, Object>> list);
	
	/**
	 * 清除一个推荐策略下的商品
	 * @param Map
	 * @return
	 */
	public int deleteRecommendedProductByRid(Map<String, Object> paramMap);
	/**
	 * 启用禁用推荐策略
	 * @param Map
	 * @return
	 */
	public int updateState(Map<String, Object> paramMap);
	/**
	 * 获取一个推荐策略下的商品列表
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryProductListByRId(Map<String, Object> paramMap);
	/**
	 * 查询推荐详情
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryRecommendDetailById(Map<String, Object> paramMap);
	/**
	 * 查询关联商品单号
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryProductItemnumbers(Map<String, Object> paramMap);

}
