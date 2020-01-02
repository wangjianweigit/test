package com.tk.oms.product.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.tk.sys.common.BaseDao;


/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : ProductParamDao
 * 商品参数Dao类
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-4-6
 */
@Repository
public interface ProductParamDao extends BaseDao<Map<String, Object>>{

	/**
     * 查询商品参数基本信息 -- 根据所属类型
     * @param type
     * @return
     */
    public List<Map<String, Object>> queryProductParamListByType(String type);

    /**
     * 获取商品参数基本列表数据
     * @param request
     * @return
     */
	public List<Map<String, Object>> queryProductParamList(Map<String, Object> paramMap);
	
	  /**
     * 获取商品参数列表数据数量
     * @param request
     * @return
     */
	public int queryProductParamCount(Map<String, Object> paramMap);

	/**
     * 删除商品参数
     * @param request
     * @return
     */
	public int delete(Map<String, Object> paramMap);
	/**
	 * 参数名称是否存在
	 * @param paramMap
	 * @return
	 */
	public int isExist(Map<String, Object> paramMap);
	/**
	 * 查询商品参数信息
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryProductParamInfo(Map<String, Object> paramMap);
	/**
	 * 更新排序
	 * @param paramMap
	 * @return
	 */
	public int updateSort(Map<String, Object> paramMap);
	/**
	 * 查询参数是否存在关联商品
	 * @param paramMap
	 * @return
	 */
	public int queryProductParamRelevance(Map<String, Object> paramMap);

}
