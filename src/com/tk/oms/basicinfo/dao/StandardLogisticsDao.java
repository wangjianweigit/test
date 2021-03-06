package com.tk.oms.basicinfo.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2018, TongKu
 * FileName : StandardLogisticsDao
 * 标准物流公司数据库访问层
 *
 * @author zhenghui
 * @version 1.00
 * @date 2018-06-12
 */
@Repository
public interface StandardLogisticsDao {

    /**
     * 分页查询标准物流公司
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> listStandardLogisticsForPage(Map<String, Object> params);

    /**
     * 查询标准物流公司总数量
     *
     * @param params
     * @return
     */
    int countStandardLogisticsForPage(Map<String, Object> params);

    /**
     * 查询标准物流公司
     * @return
     */
    List<Map<String, Object>> listStandardLogistics(Map<String, Object> params);

    /**
     * 根据ID查询标准物流公司
     *
     * @param param
     * @return
     */
    Map<String, Object> getStandardLogisticsById(@Param("logistics_id") long param);

    /**
     * 根据物流代码查询标准物流公司数量
     *
     * @param params
     * @return
     */
    int countStandardLogisticsByCode(Map<String, Object> params);

    /**
     * 根据物流名称查询标准物流公司数量
     *
     * @param params
     * @return
     */
    int countStandardLogisticsByName(Map<String, Object> params);

    /**
     * 根据标准物流公司ID查询平台物流公司数量
     * @param param
     * @return
     */
    int countLogisticsByStandardId(@Param("logistics_id") long param);

    /**
     * 新增标准物流公司
     *
     * @param params
     * @return
     */
    int insertStandardLogistics(Map<String, Object> params);

    /**
     * 更新标准物流公司
     *
     * @param params
     * @return
     */
    int updateStandardLogistics(Map<String, Object> params);

    /**
     * 删除标准物流公司
     *
     * @param params
     * @return
     */
    int deleteStandardLogistics(Map<String, Object> params);
    
    /**
	 * 查询物流公司列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryLogisticsCompanyList(Map<String, Object> paramMap);
	/**
     * 根据ID查询标准物流公司
     *
     * @param param
     * @return
     */
    Map<String, Object> queryById(long id);
    
    /**
     * 更新标准物流公司排序值
     *
     * @param param
     * @return
     */
   int updateSort(Map<String, Object> params);
}
