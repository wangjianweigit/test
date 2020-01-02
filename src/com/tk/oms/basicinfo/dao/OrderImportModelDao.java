package com.tk.oms.basicinfo.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *
 * Copyright (c), 2017, Tongku
 * FileName : OrderImportModelDao
 * 订单导入模版数据访问接口
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/8/23 14:51
 */
@Repository
public interface OrderImportModelDao {

    /**
     * 查询订单导入模版记录数
     * @param params
     * @return
     */
    int queryOrderImportModelCount(Map<String, Object> params);

    /**
     * 查询订单导入模版列表
     * @param params
     * @return
     */
    List<Map<String,Object>> queryOrderImportModelList(Map<String, Object> params);

    /**
     * 根据模版id查询订单模版数据信息
     * @param id
     * @return
     */
    Map<String,Object> queryOrderImportModelById(long id);

    /**
     * 查询相同名称的模版记录数
     * @param params
     * @return
     */
    int queryOrderImportModelByNameCount(Map<String, Object> params);

    /**
     * 新增订单导入模版数据
     * @param params
     * @return
     */
    int insertOrderImportModel(Map<String, Object> params);

    /**
     * 删除订单导入模版数据
     * @param id
     * @return
     */
    int deleteOrderImportModel(long id);

    /**
     * 修改订单导入模版数据
     * @param params
     * @return
     */
    int updateOrderImportModel(Map<String, Object> params);

    /**
     * 查询模版配置数据信息
     * @param params
     * @return
     */
    List<Map<String, Object>> queryOrderImportModelConfigDetail(Map<String, Object> params);

    /**
     * 查询订单导入模版配置记录数
     * @param modelId
     * @return
     */
    int queryOrderImportModelConfigCount(long modelId);

    /**
     * 查询订单导入模版配置数据
     * @param configId
     * @return
     */
    Map<String, Object> queryOrderImportModelConfig(long configId);

    /**
     * 根据模版配置id删除模版配置数据信息
     * @param configId
     * @return
     */
    int deleteOrderImportModelConfigById(long configId);

    /**
     * 删除配置表列数据
     * @param params
     * @return
     */
    int deleteTableColumn(Map<String, Object> params);

    /**
     * 添加配置表列数据
     * @param params
     * @return
     */
    int addTableColumn(Map<String, Object> params);

    /**
     * 修改配置表列数据
     * @param params
     * @return
     */
    int updateTableColumn(Map<String, Object> params);

    /**
     * 根据模版id删除模版配置数据信息
     * @param modelId
     * @return
     */
    int deleteOrderImportModelConfig(long modelId);

    /**
     * 批量新增模版配置数据
     * @param params
     * @return
     */
    int insertOrderImportModelConfigByBatch(Map<String, Object> params);

    /**
     * 查询表是否存在
     * @param table_name
     * @return
     */
    int queryExistTable(@Param("table_name")String table_name);

    /**
     * 删除表
     * @param table_name
     * @return
     */
    int dropTable(@Param("table_name")String table_name);

    /**
     * 创建导入订单表
     * @param params
     * @return
     */
    int createImportOrderTable(Map<String, Object> params);

    /**
     * 创建导入订单明细表
     * @param params
     * @return
     */
    int createImportOrderDetailTable(Map<String, Object> params);
}
