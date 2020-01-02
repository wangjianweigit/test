package com.tk.oms.analysis.dao;


import com.tk.oms.product.entity.ProductOperationLog;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2019, TongKu
 * FileName : ProductOperationLogDao
 * 商品操作日志数据库访问层
 *
 * @author zhenghui
 * @version 1.00
 * @date 2019/05/15
 */
@Repository
public interface ProductOperationLogDao {

    /**
     * 分页查询商品操作日志列表
     * @param params
     * @return
     */
    List<Map<String,Object>> listProductOperationLogForPage(Map<String, Object> params);

    /**
     * 查询商品操作日志列表总数量
     * @param params
     * @return
     */
    int countProductOperationLogForPage(Map<String, Object> params);

    /**
     * 新增商品操作日志
     * @param params
     * @return
     */
    int insertProductOperationLog(ProductOperationLog params);
    
    /**
     * 批量新增商品操作日志
     * @param params
     * @return
     */
    int batchInsertProductOperationLog(List<ProductOperationLog> logList);
}
