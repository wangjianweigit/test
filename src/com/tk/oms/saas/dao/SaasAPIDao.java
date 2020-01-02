package com.tk.oms.saas.dao;

import com.tk.oms.saas.SaasRequest;
import com.tk.oms.saas.entity.SaasShop;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2016, Tongku
 * FileName : SaasAPIDao
 * 聚水潭请求参数工具类
 *
 * @author wanghai
 * @version 1.00
 * @date 2017-11-29
 */
@Repository
public interface SaasAPIDao {

    /**
     * 根据店铺ID获取聚水潭请求参数
     *
     * @param shopId
     * @return
     */
    SaasRequest querySaasInfoByShopId(@Param("shop_id") long shopId);

    /**
     * 根据公司ID获取聚水潭请求参数
     *
     * @param id
     * @return
     */
    SaasRequest querySaasInfoById(long id);

    /**
     * 获取聚水潭公司列表
     *
     * @return
     */
    List<Map<String, Object>> querySaasCompanyList();

    /**
     * 获取公司店铺列表
     *
     * @param companyId
     * @return
     */
    List<SaasShop> querySaasShopList(@Param("company_id") long companyId);

    /**
     * 获取任务执行数据
     *
     * @param map
     * @return
     */
    Map<String, Object> queryTaskExecute(Map<String, Object> map);

    /**
     * 更新任务执行数据
     *
     * @param map
     * @return
     */
    int updateTaskExecute(Map<String, Object> map);
}
