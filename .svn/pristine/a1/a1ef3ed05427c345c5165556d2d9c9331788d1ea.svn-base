package com.tk.oms.finance.dao;

import com.tk.sys.common.BaseDao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 入驻商结算管理
 * @author zhenghui
 */
@Repository
public interface SettlementDao extends BaseDao<Map<String,Object>> {
	/**
     * 添加入驻商待结算记录
     *
     * @param paramsMap
     * @return
     */
    int insertSettlementForStationed(Map<String, Object> paramsMap);

    /**
     * 添加平台服务待结算记录
     *
     * @param paramsMap
     * @return
     */
    int insertSettlementForPlatform(Map<String, Object> paramsMap);

    /**
     * 添加仓储服务待结算记录
     *
     * @param paramsMap
     * @return
     */
    int insertSettlementForStorage(Map<String, Object> paramsMap);

    /**
     * 添加入驻商服务待结算记录
     *
     * @param paramsMap
     * @return
     */
    int insertSettlementForStationedServer(Map<String, Object> paramsMap);

    /**
     * 添加入驻商支付服务费待结算记录
     *
     * @param paramsMap
     * @return
     */
    int insertSettlementForStationedPay(Map<String, Object> paramsMap);

    /**
     * 添加代发费待结算记录
     *
     * @param paramsMap
     * @return
     */
    int insertSettlementForConsignment(Map<String, Object> paramsMap);

    /**
     * 添加物流费待结算记录
     *
     * @param paramsMap
     * @return
     */
    int insertSettlementForFreight(Map<String, Object> paramsMap);

    /**
     * 增加入驻商资金流水
     *
     * @param orderNumber
     * @return
     */
    int insertStationedCapitalLogs(@Param("order_number") String orderNumber);

    /**
     * 锁表
     *
     * @param payTradeNumber
     * @return
     */
    int lockTable(@Param("pay_trade_number") String payTradeNumber);

    /**
     * 获取入驻商结算单详情
     *
     * @param settlement_number
     * @return
     */
    Map<String, Object> queryByNumber(@Param("settlement_number") String settlement_number);
}
