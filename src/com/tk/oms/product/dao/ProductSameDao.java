package com.tk.oms.product.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
@Repository
public interface ProductSameDao {
	/**
	 * 同款商品列表
	 */
	public List<Map<String,Object>> queryProductSameList(Map<String,Object> paramMap);
	/**
	 * 查询出满足条件的父级商品以及满足条件的子级商品的父级商品
	 */
	public List<Map<String,Object>> queryProductSameListByCondition(Map<String,Object> paramMap);
	/**
	 * 同款商品列表详情
	 */
	public List<Map<String,Object>> queryProductSameDetail(Map<String,Object> paramMap);
	/**
	 * 同款商品总数
	 */
	public int queryProductSameCount(Map<String,Object> paramMap);
	/**
	 *查询出满足条件的父级商品以及满足条件的子级商品的父级商品总数
	 */
	public int queryProductSameCountByCondition(Map<String,Object> paramMap);
	/**
	 * 同款商品新增
	 */
	public int insertProductSame(Map<String, Object> paramMap);
	/**
	 * 同款商品删除
	 */
	public int removeProductSame(Map<String, Object> paramMap);
	/**
	 * 查询指定父级货号同款商品
	 */
	public List<Map<String,Object>> queryProductSameByParentItemnumber(String parent_itemnumber);
	/**
	 * 判断是否有重复的颜色
	 */
	public int queryCountByProductSize(Map<String,Object> paramMap);

	/**
	 * 判断是否存在已经参加订货会和清尾活动商品
	 * @param paramMap
	 * @return
	 */
	public int countJoinActivityProduct(Map<String,Object> paramMap);
}
