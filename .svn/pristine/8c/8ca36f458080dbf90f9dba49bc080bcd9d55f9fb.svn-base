package com.tk.oms.marketing.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tk.sys.common.BaseDao;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : RegionProductDao
 * 
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-4-10
 */
@Repository
public interface RegionProductDao extends BaseDao<Map<String, Object>> {
	/**
	 * 查询区域关联商品列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryRegionProductList(Map<String, Object> paramMap);
	/**
	 * 查询区域关联商品总数
	 * @param paramMap
	 * @return
	 */
	public int queryRegionProductCount(Map<String, Object> paramMap);
	/**
	 * 根据父id查询所有的省市县数据,返回值中携带参数：是否有子节点
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryDicRegionByParentId(Map<String, Object> params);
	/**
	 * 根据父id查询所有的省市县数据,返回值中携带参数：是否有子节点
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryDicRegionByParentIdWidthChildren(Map<String, Object> params);
	/**
	 * 获取所有省市县数据信息
	 * @return
	 */
	public List<Map<String, Object>> queryDicRegionWithoutCounty();
	
}
