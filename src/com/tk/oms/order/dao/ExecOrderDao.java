package com.tk.oms.order.dao;

import com.tk.oms.order.entity.ExecOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2018, TongKu
 * FileName : ExecOrderDao
 * 异常订单数据接口
 * @author wangjianwei
 * @version 1.00
 * @date 2019/11/27 16:25
 */
@Repository
public interface ExecOrderDao {
    /**
     * 异常订单列表
     * @param paramMap
     * @return
     */
    List<ExecOrder> listExecOrder(Map<String, Object> paramMap);

    /**
     * 查询异常订单记录数
     * @param paramMap
     * @return
     */
    int countExecOrder(Map<String, Object> paramMap);

    /**
     * 修改异常订单记录
     * @param paramMap
     * @return
     */
    int updateExecOrderRecord(Map<String, Object> paramMap);

    /**
     * 新增异常订单标记记录
     * @param paramMap
     * @return
     */
    int insertExecOrderMarkRecord(Map<String, Object> paramMap);

    /**
     * 获取用户白名单列表
     * @return
     */
    List<Map<String, Object>> listExecOrderWhiteList(Map<String, Object> paramMap);

    /**
     * 查询移除订单白名单数量
     * @return
     */
    int countExecOrderWhiteList(Map<String, Object> paramMap);

    /**
     * 删除报名单
     * @param list
     * @return
     */
    int deleteExecOrderWhiteList(@Param("list") List<Long> list);

    /**
     * 新增异常订单用户至白名单
     * @param list
     * @return
     */
    int insertExecOrderWhiteList(Map<String, Object> paramMap);

    /**
     * 更新异常订单处理状态
     * @param orderNumer
     * @return
     */
    int updateExecOrderHandleStateByClose(@Param("orderNumber") String orderNumer);
}
