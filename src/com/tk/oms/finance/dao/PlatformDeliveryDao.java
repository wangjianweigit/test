package com.tk.oms.finance.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2017, TongKu
 * FileName : PlatformDeliveryDao
 * 电商平台发货查询数据库操作类
 *
 * @author zhenghui
 * @version 1.00
 * @date 2018/1/16
 */
@Repository
public interface PlatformDeliveryDao {
    /**
     * 查询电商平台发货订单列表
     * @param params
     * @return
     */
    List<Map<String,Object>> queryPlatformDeliveryOrderList(Map<String,Object> params);

    /**
     * 查询电商平台发货订单总数量
     * @param params
     * @return
     */
    int queryPlatformDeliveryOrderCount(Map<String,Object> params);
}
