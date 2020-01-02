package com.tk.oms.finance.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author : zhengfy
 * @version V1.0
 * @date 2019/11/22
 */
@Repository
public interface OrderProductPriceDao {


    /**
     * 分页获取订单商品价格明细
     * @param params
     * @return
     */
    List<Map<String, Object>> queryDetailListForPage(Map<String ,Object> params);

    /**
     * 获取订单商品价格明细总数
     * @param params
     * @return
     */
    int queryDetailListForCount(Map<String ,Object> params);

    /**
     * 获取计算过程
     * @param order_specs_price_id
     * @return
     */
    Map<String, Object> queryProcess(@Param("order_specs_price_id") long order_specs_price_id);
}