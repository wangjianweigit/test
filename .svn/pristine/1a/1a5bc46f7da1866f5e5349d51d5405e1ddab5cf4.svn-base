package com.tk.oms.analysis.dao;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tk.oms.basicinfo.dao.BaseDao;

/**
 *
 * Copyright (c), 2017, Tongku
 * FileName : UserDailyStatisticsDao
 * 用户每日统计数据访问接口
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/5/13 14:49
 */
@Repository
public interface UserDailyStatisticsDao extends BaseDao {

    /**
     * 浏览分析 数据列表
     * @param params
     * @return
     */
    List<Map<String,Object>> queryUserDilyStatisicsList_Flow(Map<String,Object> params);
    
    /**
     * 订单数-订单商品数-所有支付金额 数据列表
     * @param params
     * @return
     */
    List<Map<String,Object>> queryUserDilyStatisicsList_OrderInfo(Map<String,Object> params);
    
    /**
     * 预订支付的首款 数据列表
     * @param params
     * @return
     */
    List<Map<String,Object>> queryUserDilyStatisicsList_PreFirstMoney(Map<String,Object> params);
    
    /**
     * 尾款订单的定金金额 数据列表
     * @param params
     * @return
     */
    List<Map<String,Object>> queryUserDilyStatisicsList_PreOrderFirstMoney(Map<String,Object> params);

}
