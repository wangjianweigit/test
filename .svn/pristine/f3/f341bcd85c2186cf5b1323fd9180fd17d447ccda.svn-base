package com.tk.store.order.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface StoreOrderApplyDao {
	
	/**
	 * 获取要货单、退款单列表
	 */
	public List<Map<String,Object>> queryStoreOrderApplyList(Map<String,Object> paramMap);
	/**
	 * 获取要货单、退款单总数
	 */
	public int queryStoreOrderApplyCount(Map<String,Object> paramMap);
	/**
	 * 获取要货单、退款单详情
	 */
	public Map<String,Object> queryStoreOrderApplyDetail(Map<String,Object> paramMap);
	/**
	 * 获取要货单、退款单商品sku列表
	 */
	public List<Map<String,Object>> queryOrderProductSkuList(Map<String,Object> paramMap);
	/**
	 * 查询要货单、退款单商品总金额
	 */
	public float queryProductTotalMoney(Map<String,Object> paramMap);
	/**
	 * 要货单、退款单确认
	 */
	public int storeOrderApplyConfirm(Map<String,Object> paramMap);
	/**
	 * 要货单、退款单审批
	 */
	public int storeOrderApplyApproval(Map<String,Object> paramMap);
	/**
	 * 按照登入用户查询门店列表
	 */
	public List<Map<String,Object>> queryStoreList(Map<String,Object> paramMap);

}
