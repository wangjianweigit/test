package com.tk.oms.member.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2017, Tongku
 * FileName : DistributorManageDao
 * 经销商管理数据访问接口
 *
 * @author zhenghui
 * @version 1.00
 * @date 2017/08/09
 */
@Repository
public interface DistributorManageDao {

    /**
     * 查询经销商列表数据
     * @param params
     * @return
     */
    List<Map<String, Object>> queryDistributorList(Map<String, Object> params);
    /**
     * 查询经销商下拉框
     * @param params
     * @return
     */
    List<Map<String, Object>> queryDistributorOption();

    /**
     * 查询经销商列表总数量
     * @param params
     * @return
     */
    int queryDistributorCount(Map<String, Object> params);

    /**
     * 通过用户ID更新分销状态
     * @param params
     * @return
     */
    int updateDistributionStateById(Map<String, Object> params);

    /**
     * 通过用户ID更新服务费率
     * @param params
     * @return
     */
    int updateServiceRateById(Map<String, Object> params);

    /**
     * 更新经销商权限
     * @param params
     * @return
     */
    int updateAuthorityForDistribution(Map<String, Object> params);

    /**
     * 通过用户ID获取用户信息
     * @param params
     * @return
     */
    Map<String, Object> queryUserById(Map<String, Object> params);


    List<Map<String, Object>> queryUserByAccount(List<Map<String, Object>> list);

    List<Map<String, Object>> queryDistributorListNoPage(Map<String, Object> params);

}
