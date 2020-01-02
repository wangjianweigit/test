package com.tk.oms.order.dao;

import java.util.List;
import java.util.Map;

public interface OrderUnusualDao {
	
	/**
	 * 查询异常订单列表总数量
	 * @param paramMap
	 * @return
	 */
	public int queryOrderUnusualCount(Map<String, Object> paramMap);
	/**
	 * 查询异常订单列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryOrderUnusualList(Map<String, Object> paramMap);
	/**
	 * 获取订单商品sku列表信息
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryOrderProductSkuList(Map<String, Object> paramMap);
	/**
	 * 新增活更新异常订单
	 * @param paramMap
	 * @return
	 */
	public int addOrUpdateOrderUnusual(Map<String, Object> paramMap);
	/**
	 * 异常订单备注详情
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> orderUnusualRemarkDetail(Map<String, Object> paramMap);

}
