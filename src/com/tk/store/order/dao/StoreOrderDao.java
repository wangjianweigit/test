package com.tk.store.order.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface StoreOrderDao {

	/**
	 * 获取店铺订货单列表
	 */
	public List<Map<String,Object>> queryOrderList(Map<String,Object> paramMap);
	/**
	 * 获取店铺订货单总数
	 */
	public int queryOrderCount(Map<String,Object> paramMap);
	/**
	 * 获取店铺订货单详情
	 */
	public Map<String, Object> queryOrderDetail(Map<String,Object> paramMap);
	/**
	 * 获取店铺订货单商品sku列表
	 */
	public List<Map<String,Object>> queryOrderProductSkuList(Map<String,Object> paramMap);
	/**
	 * 获取店铺订货单发货包裹列表
	 */
	public List<Map<String, Object>> queryOrderBoxList(Map<String, Object> paramMap);
	/**
	 * 取消店铺订货单【合作商审核驳回】
	 */
	public void cancelOrder(Map<String,Object> orderinfoMap);
	/**
	 * 店铺订货单支付【合作商审核】
	 */
	public void updatePaymentState(Map<String, Object> orderMap);
	/**
	 * 下单人信息
	 */
	public Map<String, Object> queryOrderUserInfo(Map<String, Object> paramMap);
	/**
	 * 查询交易号
	 */
	public String queryOrderPayTradeNumber(Map<String, Object> paramMap);
	
}