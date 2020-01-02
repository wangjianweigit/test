package com.tk.store.stock.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tk.oms.basicinfo.dao.BaseDao;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : StockAllocateDao
 * 库存调拨DAO层
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018-3-2
 */
@Repository
public interface StockAllocateDao extends BaseDao<Map<String, Object>> {
	/**
	 * 库存调拨数量
	 * @param paramMap
	 * @return
	 */
	int queryStockAllocateCount(Map<String, Object> paramMap);
	/**
	 * 库存调拨列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryStockAllocateListForPage(Map<String, Object> paramMap);
	/**
	 * 获取调拨单详情
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryStockAllocateDetail(Map<String, Object> paramMap);
	/**
	 * 获取调拨单商品数据
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryStockAllocateProductDetail(Map<String, Object> paramMap);
	/**
	 * 获取调拨单商品sku数据
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryStockAllocateDetailSku(Map<String, Object> paramMap);
	/**
	 * 查询商品所属分类
	 * @param m
	 * @return
	 */
	String queryProductTypeByItemnumber(Map<String, Object> m);
	/**
	 * 获取商品分组信息
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryProductGroup(Map<String, Object> paramMap);
	/**
	 * 审批
	 * @param paramMap
	 * @return
	 */
	int approvalStockAllocate(Map<String, Object> paramMap);
	/**
	 * 新增调拨单
	 * @param paramMap
	 * @return
	 */
	int insertStockAllocate(Map<String, Object> paramMap);
	/**
	 * 新增调拨单明细
	 * @return
	 */
	int insertStockAllocateDetail(Map<String, Object> m);
	/**
	 * 是否更换调出调入门店
	 * @param paramMap
	 * @return
	 */
	int queryStockAllocateIsExist(Map<String, Object> paramMap);
	/**
	 * 更新调拨单基本信息
	 * @param paramMap
	 * @return
	 */
	int updateStockAllocate(Map<String, Object> paramMap);
	/**
	 * 是否存在商品sku
	 * @param m
	 * @return
	 */
	int queryProductSkuIsExist(Map<String, Object> m);
	/**
	 * 更新调拨单明细
	 * @param m
	 * @return
	 */
	int updateStockAllocateDetail(Map<String, Object> m);
	/**
	 * 查询商品库存信息【推送】
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryProductStockSku(Map<String, Object> paramMap);
	/**
	 * 获取差异列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryDiffList(Map<String, Object> paramMap);
	/**
	 * 获取调拨信息【调拨任务】
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryAllotTaskDetail(Map<String, Object> paramMap);
	/**
	 * 获取商品分组信息【调拨任务】
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryProductGroupByTask(Map<String, Object> paramMap);
	/**
	 * 删除调拨任务
	 * @param paramMap
	 * @return
	 */
	int deleteAllotTask(Map<String, Object> paramMap);
	/**
	 * 删除调拨任务详情
	 * @param paramMap
	 * @return
	 */
	int deleteAllotTaskDetail(Map<String, Object> paramMap);
	/**
	 * 门店ID转化
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> storeIdToAgentStoreId(Map<String, Object> paramMap);
	/**
	 * 查询商家ID
	 * @param paramMap
	 * @return
	 */
	List<String> queryAgentId(Map<String, Object> paramMap);
	/**
	 * 获取商品颜色尺码信息
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> getProductInfo(Map<String, Object> paramMap);
	/**
	 * 获取商家ID
	 * @param paramMap
	 * @return
	 */
	String getAgentId(Map<String, Object> paramMap);
	/**
	 * 新零售门店下拉框
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> storeSelect(Map<String, Object> paramMap);
	/**
	 * 门店分组下拉框
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> storeGroupSelect(Map<String, Object> paramMap);
	/**
	 * 门店分组详情
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryStoreGroupDetail(Map<String, Object> paramMap);
	/**
	 * 是否存在相同分组名称
	 * @param paramMap
	 * @return
	 */
	int queryGroupNameIsExists(Map<String, Object> paramMap);
	/**
	 * 新增门店分组
	 * @param paramMap
	 * @return
	 */
	int insertStoreGroup(Map<String, Object> paramMap);
	/**
	 * 删除门店分组
	 * @param paramMap
	 * @return
	 */
	int deleteStoreGroup(Map<String, Object> paramMap);
	/**
	 * 新增调拨单(调出调入)
	 * @param map
	 * @return
	 */
	int insertStockAllocateOutIn(Map<String, Object> map);
	/**
	 * 获取调拨任务信息(调出)
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryAllotOutTask(Map<String, Object> paramMap);
	/**
	 * 获取调拨任务商品(调出)
	 * @param paramMap
	 * @return
	 */
	List<String> queryProductOutTask(Map<String, Object> paramMap);
	/**
	 * 要货/退货单数量
	 * @param paramMap
	 * @return
	 */
	int storeOrderQueryCount(Map<String, Object> paramMap);
	/**
	 * 要货/退货单列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> storeOrderQueryListForPage(Map<String, Object> paramMap);
	/**
	 * 获取商品要货数量
	 * @param p
	 * @return
	 */
	int getStoreOrderProductCount(Map<String, Object> p);
	/**
	 * 获取商品sku要货数量
	 * @param p
	 * @return
	 */
	List<Map<String, Object>> getStoreOrderSkuCount(Map<String, Object> p);
	/**
	 * 获取门店ID
	 * @param paramMap
	 * @return
	 */
	String getStoreId(Map<String, Object> paramMap);
	/**
	 * 更新调拨单清分交易的付款状态
	 * @return
	 */
	int updateStoreTradePaymentState(Map<String, Object> paramMap);
	
}
