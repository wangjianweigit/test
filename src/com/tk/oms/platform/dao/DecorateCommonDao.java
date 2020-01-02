package com.tk.oms.platform.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2018, TongKu
 * FileName : DecorateCommonDao
 * 装修通用数据库访问层
 *
 * @author zhenghui
 * @version 1.00
 * @date 2018/09/12
 */
@Repository
public interface DecorateCommonDao {

    /**
     * 查询固定模块选择商品列表
     * @param params
     * @return
     */
    List<Map<String,Object>> listSelectProductForPage(Map<String,Object> params);

    /**
     * 查询固定模块选择商品总数量
     * @param params
     * @return
     */
    int countSelectProductForPage(Map<String,Object> params);

    /**
     * 查询选中商品列表
     * @param params
     * @return
     */
    List<Map<String,Object>> listSelectProduct(Map<String,Object> params);

    /**
     * 查询商品分组
     * @return
     */
    List<Map<String,Object>> listProductGroup();

    /**
     * 查询父级商品分组
     * @return
     */
    List<Map<String,Object>> listParentProductGroup(Map<String,Object> params);

    /**
     * 查询活动列表
     * @param params
     * @return
     */
    List<Map<String,Object>> listActivity(Map<String,Object> params);

    /**
     * 查询装修页面列表
     * @param params
     * @return
     */
    List<Map<String,Object>> listDecoratePage(Map<String,Object> params);

    /**
     * 查询商品季节
     * @return
     */
    List<Map<String,Object>> listProductSeason();

    /**
     * 查询商品品牌
     * @return
     */
    List<Map<String,Object>> listProductBrand();

    /**
     * 分页查询商品品牌列表
     * @param params
     * @return
     */
    List<Map<String,Object>> listProductBrandForPage(Map<String,Object> params);

    /**
     * 查询商品品牌数量
     * @param params
     * @return
     */
    int countProductBrandForPage(Map<String,Object> params);

    /**
     * 查询已选中品牌列表
     * @param params
     * @return
     */
    List<Map<String,Object>> listSelectProductBrand(Map<String,Object> params);

    /**
     * 查询商品规格
     * @return
     */
    List<Map<String,Object>> listProductSpec();

    /**
     * 查询仓库列表
     * @param paran
     * @return
     */
    List<Map<String,Object>> listWarehouse(@Param("site_id") long paran);

    /**
     * 查询商品列表（带视频）
     * @param params
     * @return
     */
    List<Map<String, Object>> listProductForPage(Map<String, Object> params);

    /**
     * 查询商品数量（带视频）
     * @param params
     * @return
     */
    int countProductForPage(Map<String, Object> params);


}
