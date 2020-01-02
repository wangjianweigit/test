package com.tk.oms.platform.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2018, TongKu
 * FileName : FixedModuleDao
 * 固定模块数据库访问层
 *
 * @author zhenghui
 * @version 1.00
 * @date 2018/09/12
 */
@Repository
public interface FixedModuleDao {

    /**
     * 根据页面ID查询页面信息
     * @param param
     * @return
     */
    Map<String,Object> getDecoratePageById(@Param("page_id") long param);

    /**
     * 根据模块代码查询页面固定模块详情
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> listPageFixedModuleDetailByModuleCode(Map<String, Object> params);

    /**
     * 根据模块代码查询页面固定模块信息
     *
     * @param params
     * @return
     */
    Map<String, Object> getPageFixedModuleByModuleCode(Map<String, Object> params);

    /**
     * 新增页面固定模块
     * @param params
     * @return
     */
    int insertPageFixedModule(Map<String, Object> params);

    /**
     * 新增页面模块详细信息
     *
     * @param params
     * @return
     */
    int insertPageFixedModuleDetail(Map<String, Object> params);

    /**
     * 删除页面模块详细信息
     *
     * @param params
     * @return
     */
    int deletePageFixedModuleDetail(Map<String, Object> params);

    /**
     * 新增初始页面固定模块
     *
     * @param params
     * @return
     */
    int insertInitPageFixedModule(Map<String, Object> params);

    /**
     * 新增初始页面固定模块详细数据
     *
     * @param params
     * @return
     */
    int insertInitPageFixedModuleDetail(Map<String, Object> params);

    /**
     * 更新固定模块排序
     * @param params
     * @return
     */
    int updatePageFixedModuleSort(Map<String, Object> params);

    /**
     * 更新当前所有固定模块排序
     * @param params
     * @return
     */
    int updatePageFixedModuleSortId(Map<String, Object> params);

    /**
     * 新增初始页面布局
     *
     * @param params
     * @return
     */
    int insertInitPageLayout(Map<String, Object> params);

    /**
     * 更新现有的排序
     * @param params
     * @return
     */
    int updatePageFixedModuleAllSort(Map<String, Object> params);

    /**
     * 更新页面固定模块状态
     * @param params
     * @return
     */
    int updatePageFixedModuleState(Map<String, Object> params);

    /**
     * 查询页面固定模块数量
     * @param param
     * @return
     */
    List<Map<String,Object>> listPageFixedModuleCountByPageId(@Param("page_id") long param);

    /**
     * 查询商品品牌列表
     * @param params
     * @return
     */
    List<Map<String,Object>> listProductBrand(Map<String, Object> params);

    /**
     * 查询每日上新（视频）商品信息
     * @param params
     * @return
     */
    List<Map<String,Object>> listUpdateVideoProduct(Map<String, Object> params);

    /**
     * 查询每日上新商品信息
     * @param params
     * @return
     */
    List<Map<String,Object>> listUpdateProduct(Map<String, Object> params);

    /***
     * 按分类查询商品信息
     * @param params
     * @return
     */
    List<Map<String,Object>> listClassifyProduct(Map<String, Object> params);

    /**
     * 查询实力定制商品信息
     * @param params
     * @return
     */
    List<Map<String,Object>> listCustomProduct(Map<String, Object> params);

    /**
     * 分页查询猜你喜欢商品信息
     * @param params
     * @return
     */
    List<Map<String,Object>> listLikeProductForPage(Map<String, Object> params);

    /**
     * 查询猜你喜欢商品信息总数量
     * @param params
     * @return
     */
    int countLikeProductForPage(Map<String, Object> params);

    /**
     * 查询限时秒杀商品列表
     * @param params
     * @return
     */
    List<Map<String,Object>> listSeckillProduct(Map<String, Object> params);

    /**
     * 查询限时秒杀商品列表
     * @param params
     * @return
     */
    Map<String,Object> getActivityInfo(Map<String, Object> params);

    /**
     * 查询预售抢先商品列表
     * @param params
     * @return
     */
    List<Map<String,Object>> listPreSellProduct(Map<String, Object> params);

    /**
     * 查询自动选择商品列表
     * @param params
     * @return
     */
    List<Map<String,Object>> listAutoSelectProduct(Map<String, Object> params);

    /**
     * 查询手动选择商品列表
     * @param params
     * @return
     */
    List<Map<String,Object>> listManualSelectProduct(Map<String, Object> params);

    /**
     * 查询自动选择定制商品列表
     * @param params
     * @return
     */
    List<Map<String,Object>> listAutoSelectCustomProduct(Map<String, Object> params);

    /**
     * 查询手动选择定制商品列表
     * @param params
     * @return
     */
    List<Map<String,Object>> listManualSelectCustomProduct(Map<String, Object> params);

    /**
     * 根据活动ID查询活动信息
     * @param params
     * @return
     */
    List<Map<String,Object>> listActivityById(Map<String, Object> params);

    /**
     * 询活动模块商品列表
     * @param params
     * @return
     */
    List<Map<String,Object>> listActivityProduct(Map<String, Object> params);

    /**
     * 分页查询活动模块商品列表
     * @param params
     * @return
     */
    List<Map<String,Object>> listActivityProductForPage(Map<String, Object> params);

    /**
     * 查询活动模块商品列表总数量
     * @param params
     * @return
     */
    int countActivityProductForPage(Map<String, Object> params);

    /**
     * 查询往期预售活动商品列表
     * @param params
     * @return
     */
    List<Map<String,Object>> listPastPreSellProduct(Map<String, Object> params);

    /**
     * 查询最新十条定制订单列表
     * @param params
     * @return
     */
    List<Map<String,Object>> listNewCustomOrder(Map<String, Object> params);
}
