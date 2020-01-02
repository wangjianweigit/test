package com.tk.oms.analysis.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2017, Tongku
 * FileName : StockChangesDao
 * 库存异动参考数据访问接口
 *
 * @author zhenghui
 * @version 1.00
 * @date 2017/7/19
 */
@Repository
public interface StockChangesDao {

    /**
     * 分页查询商品sku列表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryProductSkuList(Map<String,Object> params);

    /**
     * 通过sku查询商品库存数量
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryProductStockListBySku(Map<String,Object> params);

    /**
     * 通过货号查询活动数量
     * @param params
     * @return
     */
    public int queryActivityNumByItemnumber(Map<String,Object> params);

    /**
     * 查询库存异动参考总数量
     * @param params
     * @return
     */
    public int queryStockChangesCount(Map<String,Object> params);

    /**
     * 通过货号查询活动列表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryActivityListByNumber(Map<String,Object> params);

    /**
     * 通过站点ID查询活动列表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryActivityListBySite(Map<String,Object> params);

    /**
     * 查询库存异动参考详情
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryStockChangesDetailList(Map<String,Object> params);


    /**
     * 查询仓库列表（大仓）
     * @return
     */
    public List<Map<String,Object>> queryWarehouseList();

    /**
     * 新增库存异动标记
     * @param params
     * @return
     */
    public int insertStockChangesMark(Map<String,Object> params);

    /**
     * 修改库存异动标记
     * @param params
     * @return
     */
    public int updateStockChangesMark(Map<String,Object> params);

    /**
     * 删除库存异动标记
     * @param params
     * @return
     */
    public int deleteStockChangesMark(Map<String,Object> params);

    /**
     * 查询库存异动标记数量
     * @param params
     * @return
     */
    public int queryStockChangesMarkCount(Map<String,Object> params);
}
