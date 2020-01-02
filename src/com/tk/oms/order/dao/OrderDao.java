package com.tk.oms.order.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tk.oms.order.entity.OrderSkuMap;

@Repository
public interface OrderDao {

	/**
	 * 获取订单列表
	 */
	public List<Map<String,Object>> queryOrderList(Map<String,Object> paramMap);
	/**
	 * 获取订单总数
	 */
	public int queryOrderCount(Map<String,Object> paramMap);
	/**
	 * 获取订单详情
	 */
	public Map<String, Object> queryOrderDetail(Map<String,Object> paramMap);
	/**
	 * 获取订单商品列表
	 */
	public List<Map<String,Object>> queryOrderProductList(Map<String,Object> paramMap);
	/**
	 * 获取商品sku列表
	 */
	public List<Map<String,Object>> queryOrderProductSkuList(Map<String,Object> paramMap);

	/**
	 * 获取订单商品SKU信息列表(订单导出使用)
	 * @param paramMap
	 * @return
     */
	public List<Map<String,Object>> queryOrderProductSkuListForExport(Map<String,Object> paramMap);
	/**
	 * 获取订单发货包裹列表
	 */
	public List<Map<String, Object>> queryOrderBoxList(Map<String, Object> paramMap);
	/**
	 * 取消订单
	 */
	public void cancelOrder(Map<String,Object> orderinfoMap);
	/**
	 * 订单调价
	 */
	public void readjustOrder(OrderSkuMap orderSkuMap);
	/**
	 * 订单物流费计算
	 */
	public Map<String, Object> queryOrderFeightMoney(Map<String, Object> paramMap);
	/**
	 * 获取订单已发货商品列表
	 */
	public List<Map<String, Object>> queryDeliverProductList(Map<String, Object> paramMap);
	/**
	 * 获取订单已发货商品数量
	 */
	public int queryDeliverProductCount(Map<String, Object> paramMap);
	/**
	 * 转账支付数量
	 * @param paramMap
	 * @return
	 */
	public int queryTransferCount(Map<String, Object> paramMap);
	/**
	 * 转账支付列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryTransferList(Map<String, Object> paramMap);
	/**
	 * 转账支付详情
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryTransferDetail(Map<String, Object> paramMap);
	/**
	 * 确认转账支付
	 * @param orderMap
	 */
	public void updatePaymentState(Map<String, Object> orderMap);
	/**
	 * 下单人信息
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryOrderUserInfo(Map<String, Object> paramMap);
	/**
	 * 添加订单备注
	 * @param paramMap
	 * @return
	 */
	public int addCustomerServiceRemark(Map<String, Object> paramMap);
	/**
	 * 修改订单备注状态
	 * @param paramMap
	 * @return
	 */
	public int updateCustomerServiceRemarkState(Map<String, Object> paramMap);
	/**
	 * 查看当前订单备注记录
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryCustomerServiceRemarkList(Map<String, Object> paramMap);
	/**
	 * 查看当前订单备注记录详情
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryCustomerServiceRemarkDetail(Map<String, Object> paramMap);
}