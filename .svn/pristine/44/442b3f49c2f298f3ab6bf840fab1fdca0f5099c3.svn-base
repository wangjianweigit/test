package com.tk.oms.basicinfo.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : WaterTanBasicDao
 * 聚水谭公司基础数据访问接口
 * 
 * @author wangjianwei
 * @version 1.00
 * @date 2017/11/22 16:58
 */
@Repository
public interface WaterTanBasicDao {

	/**
     * 获取聚水谭公司列表数据
     * @param params
     * @return
     */
    List<Map<String, Object>> queryWaterTanList(Map<String, Object> params);

    /**
     * 获取聚水谭公司记录数
     * @param params
     * @return
     */
    int queryWaterTanCount(Map<String, Object> params);
    
    /**
     * 获取聚水谭公司明细
     * @param params
     * @return
     */
    Map<String, Object> queryWaterTanDetail(Map<String, Object> params);

    /**
     * 新增聚水谭公司明细
     * @param params
     * @return
     */
    int insertWaterTanDetail(Map<String, Object> params);
    
    /**
     * 编辑聚水谭公司明细
     * @param params
     * @return
     */
    int editWaterTanDetail(Map<String, Object> params);


    /**
     * 获取聚水谭会员店铺列表数据
     * @param params
     * @return
     */
    List<Map<String, Object>> queryWaterTanMemberStoreList(Map<String, Object> params);

    /**
     * 获取聚水谭会员店铺记录数
     * @param params
     * @return
     */
    int queryWaterTanMemberStoreCount(Map<String, Object> params);

    /**
     * 获取聚水谭会员店铺明细
     * @param params
     * @return
     */
    Map<String, Object> queryWaterTanMemberStoreDetail(Map<String, Object> params);

    /**
     * 店铺取消授权
     * @param paramMap
     * @return
     */
    int storeAuthCancel(Map<String, Object> paramMap);

    /**
     * 新增聚水谭会员店铺明细
     * @param params
     * @return
     */
    int insertWaterTanMemberStoreDetail(Map<String, Object> params);

    /**
     * 编辑聚水谭会员店铺明细
     * @param params
     * @return
     */
    int editWaterTanMemberStoreDetail(Map<String, Object> params);

    /**
     * 查询聚水谭公司下拉框
     * @param params
     * @return
     */
    List<Map<String, Object>> queryWaterTanCompanyOption(Map<String, Object> params);

    /**
     * 查询聚水谭系统配置托管公司id
     * @return
     */
    String queryWaterTanHostCompany();

    /**
     * 查询用户聚水谭店铺授权信息
     * @param params
     * @return
     */
    Map<String, Object> queryWaterTanUserShopInfo(Map<String, Object> params);

    /**
     * 获取聚水谭公司店铺列表数据
     * @param params
     * @return
     */
    List<Map<String, Object>> queryWaterTanCompanyStoreList(Map<String, Object> params);

    /**
     * 获取聚水谭公司店铺记录数
     * @param params
     * @return
     */
    int queryWaterTanCompanyStoreCount(Map<String, Object> params);

    /**
     * 聚水谭会员店铺解除绑定
     * @param id
     * @return
     */
    int deleteWaterTanMemberStore(long id);
}
