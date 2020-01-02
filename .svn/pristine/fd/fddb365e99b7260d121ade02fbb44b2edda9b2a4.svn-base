package com.tk.analysis.retail.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
/**
 * 
 * Copyright (c), 2019, Tongku
 * FileName : RetailAnalysisDao.java
 * 运营分析-新零售 dao层
 *
 * @author yejingquan
 * @version 1.00
 * @date Dec 3, 2019
 */
@Repository
public interface RetailAnalysisDao {
	/**
	 * 经销商总数
	 * @param paramMap
	 * @return
	 */
	int r_queryAgentCount(Map<String, Object> paramMap);
	/**
	 * 小程序开通数
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentProgramCount(Map<String, Object> paramMap);
	/**
	 * 零售客户总数
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentCustomerTotal(Map<String, Object> paramMap);
	/**
	 * 今日新增零售客户
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentAddCustomerCount(Map<String, Object> paramMap);
	/**
	 * 日活跃客户数
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentCustomerLivelyCount(Map<String, Object> paramMap);
	/**
	 * 实时汇总 支付买家数、销售件数、支付单笔数、订单销售总额
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> r_queryAgentRealTime_PurchaseNumberCountMoney(Map<String, Object> paramMap);
	/**
	 * 实时汇总 访客数
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentRealTime_VisitorCount(Map<String, Object> paramMap);
	/**
	 * 实时汇总 订单笔数
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentRealTime_OrderNum(Map<String, Object> paramMap);
	/**
	 * 实时汇总 折线图 订单销售总额
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAgentRealTime_PayMoney_Chart(Map<String, Object> paramMap);
	/**
	 * 实时汇总 总订单量
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentRealTime_PayNum(Map<String, Object> paramMap);
	/**
	 * 实时汇总 线上订单量
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentRealTime_OnlinePayNum(Map<String, Object> paramMap);
	/**
	 * 实时汇总 总交易额
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentRealTime_PayMoney(Map<String, Object> paramMap);
	/**
	 * 实时汇总 线上交易额
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentRealTime_OnlinePayMoney(Map<String, Object> paramMap);
	/**
	 * 实时汇总 自营交易额
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentRealTime_ZyPayMoney(Map<String, Object> paramMap);
	/**
	 * 支付买家数---获取排序后的经销商列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryAgent_PurchaseNumber_Agent(Map<String, Object> paramMap);
	/**
	 * 支付单数---获取排序后的经销商列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryAgent_PayNum_Agent(Map<String, Object> paramMap);
	/**
	 * 成交额---获取排序后的经销商列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryAgent_PayMoney_Agent(Map<String, Object> paramMap);
	/**
	 * 线上支付笔数---获取排序后的经销商列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryAgent_OnlinePayNum_Agent(Map<String, Object> paramMap);
	/**
	 * 线上成交额---获取排序后的经销商列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryAgent_OnlinePayMoney_Agent(Map<String, Object> paramMap);
	/**
	 * 查询默认排序的经销商列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryAgentListBy_Default(Map<String, Object> paramMap);
	/**
	 * 经销商列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAgentList(Map<String, Object> paramMap);
	/**
	 * 经销商列表-支付单数，成交额，支付买家数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAgent_PurchaseNumberMoneyNum(Map<String, Object> paramMap);
	/**
	 * 经销商列表-线上支付笔数，线上成交额
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAgent_OnlinePayMoneyNum(Map<String, Object> paramMap);
	/**
	 * 经销商分析
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAgentSearch(Map<String, Object> paramMap);
	/**
	 * 经销商分析详情-基本信息
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> r_queryAgentDetailBasic(Map<String, Object> paramMap);
	/**
	 * 经销商详情-实时汇总 支付买家数、销售件数、支付单笔数、订单销售总额
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> r_queryAgentDetail_RealTime_PurchaseNumberCountMoney(Map<String, Object> paramMap);
	/**
	 * 经销商详情-实时汇总 访客数
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentDetail_RealTime_VisitorCount(Map<String, Object> paramMap);
	/**
	 * 经销商详情-实时汇总 订单笔数
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentDetail_RealTime_OrderNum(Map<String, Object> paramMap);
	/**
	 * 经销商详情-实时汇总 折线图 订单销售总额
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAgentDetail_RealTime_PayMoney_Chart(Map<String, Object> paramMap);
	/**
	 * 经销商详情-实时汇总 总订单量
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentDetail_RealTime_PayNum(Map<String, Object> paramMap);
	/**
	 * 经销商详情-实时汇总 线上订单量
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentDetail_RealTime_OnlinePayNum(Map<String, Object> paramMap);
	/**
	 * 经销商详情-实时汇总 总交易额
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentDetail_RealTime_PayMoney(Map<String, Object> paramMap);
	/**
	 * 经销商详情-实时汇总 线上交易额
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentDetail_RealTime_OnlinePayMoney(Map<String, Object> paramMap);
	/**
	 * 经销商详情-实时汇总 新客户
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentDetail_RealTime_NewCustomer(Map<String, Object> paramMap);
	/**
	 * 经销商详情-客户数
	 * @param paramMap
	 * @return
	 */
	int r_queryAgentDetail_CustomerCount(Map<String, Object> paramMap);
	/**
	 * 经销商详情-今日新增零售客户
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentDetail_AddCustomerCount(Map<String, Object> paramMap);
	/**
	 * 经销商详情-日活跃客户数
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentDetail_CustomerLivelyCount(Map<String, Object> paramMap);
	/**
	 * 经销商详情-累计购买客户
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentDetail_SupCustomerCount(Map<String, Object> paramMap);
	/**
	 * 经销商详情-复购客户数
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentDetail_RpCustomerCount(Map<String, Object> paramMap);
	/**
	 * 经销商详情-客户来源
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentDetail_CustomerSource(Map<String, Object> paramMap);
	/**
	 * 经销商详情-支付单数---获取排序后的客户列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryCustomer_PayNum_Customer(Map<String, Object> paramMap);
	/**
	 * 经销商详情-成交额---获取排序后的客户列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryCustomer_PayMoney_Customer(Map<String, Object> paramMap);
	/**
	 * 经销商详情-查询默认排序的客户列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryCustomerListBy_Default(Map<String, Object> paramMap);
	/**
	 * 经销商详情-查询客户列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryCustomerList(Map<String, Object> paramMap);
	/**
	 * 经销商详情-客户-购买次数，成交额
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryCustomer_PayMoneyNum(Map<String, Object> paramMap);
	/**
	 * 所属门店【下拉框】
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryStoreOption(Map<String, Object> paramMap);
	/**
	 * 业务员【下拉框】
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryYwyOption(Map<String, Object> paramMap);
	/**
	 * 经销商详情-销售分析-下单笔数
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentDetail_Sale_OrderNum(Map<String, Object> paramMap);
	/**
	 * 经销商详情-销售分析-支付笔数
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentDetail_Sale_PayNum(Map<String, Object> paramMap);
	/**
	 * 经销商详情-销售分析-成交额
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentDetail_Sale_PayMoney(Map<String, Object> paramMap);
	/**
	 * 经销商详情-销售分析-商品销售总额
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentDetail_Sale_ProductMoney(Map<String, Object> paramMap);
	/**
	 * 经销商详情-销售分析-商品销量
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentDetail_Sale_PayCount(Map<String, Object> paramMap);
	/**
	 * 经销商详情-销售分析-退款金额
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentDetail_Sale_ReturnMoney(Map<String, Object> paramMap);
	/**
	 * 经销商详情-销售分析-未发退货数
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentDetail_Sale_UnsentReturnCount(Map<String, Object> paramMap);
	/**
	 * 经销商详情-销售分析-已发退货数
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentDetail_Sale_SentReturnCount(Map<String, Object> paramMap);
	/**
	 * 经销商详情-销售分析-支付买家数
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentDetail_Sale_PurchaseNumber(Map<String, Object> paramMap);
	/**
	 * 经销商详情-销售分析-访客数
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentDetail_Sale_VisitorCount(Map<String, Object> paramMap);
	/**
	 * 折线图 下单笔数<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAgentDetail_OrderNumD_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 支付笔数<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAgentDetail_PayNumD_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 支付买家数、支付件数、成交额<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAgentDetail_PurchaseNumberCountMoneyD_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 退款金额<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAgentDetail_ReturnMoneyD_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 未发退货数<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAgentDetail_UnsentReturnCountD_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 已发退货数<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAgentDetail_SentReturnCountD_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 访客数<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAgentDetail_VisitorCountD_Chart(Map<String, Object> paramMap);
	
	/**
	 * 折线图 下单笔数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAgentDetail_OrderNum_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 支付笔数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAgentDetail_PayNum_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 支付买家数、支付件数、成交额
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAgentDetail_PurchaseNumberCountMoney_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 退款金额
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAgentDetail_ReturnMoney_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 未发退货数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAgentDetail_UnsentReturnCount_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 已发退货数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAgentDetail_SentReturnCount_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 访客数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAgentDetail_VisitorCount_Chart(Map<String, Object> paramMap);
	/**
	 * 经销商商品总数
	 * @param paramMap
	 * @return
	 */
	int r_queryAgentDetailProductCount(Map<String, Object> paramMap);
	/**
	 * 销量---获取排序后的商品列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryAgentDetail_PayCount_Product(Map<String, Object> paramMap);
	/**
	 * 销售总额---获取排序后的商品列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryAgentDetail_PayMoney_Product(Map<String, Object> paramMap);
	/**
	 * 未发退货数---获取排序后的商品列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryAgentDetail_UnsentReturnCount_Product(Map<String, Object> paramMap);
	/**
	 * 已发退货数---获取排序后的商品列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryAgentDetail_SentReturnCount_Product(Map<String, Object> paramMap);
	/**
	 * 查询默认排序的商品列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryAgentDetailProductListBy_Default(Map<String, Object> paramMap);
	/**
	 * 查询经销商商品列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAgentDetailProductList(Map<String, Object> paramMap);
	/**
	 * 商品-销量，销售总额
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAgentDetail_Product_PayCountMoney(Map<String, Object> paramMap);
	/**
	 * 商品-未发退货数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAgentDetail_Product_UnsentReturnCount(Map<String, Object> paramMap);
	/**
	 * 商品-已发退货数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAgentDetail_Product_SentReturnCount(Map<String, Object> paramMap);
	/**
	 * 经销商详情-退款原因分析-商品退货数
	 * @param paramMap
	 * @return
	 */
	float r_queryAgentDetail_AfterSale_ReturnCount(Map<String, Object> paramMap);
	/**
	 * 经销商详情-退款原因分析-退货总数
	 * @param paramMap
	 * @return
	 */
	int r_queryAgentDetailAfterSaleReturnCount(Map<String, Object> paramMap);
	/**
	 * 经销商详情-退款原因分析-退货原因总数
	 * @param paramMap
	 * @return
	 */
	int r_queryAgentDetailAfterSaleReturnReasonCount(Map<String, Object> paramMap);
	/**
	 * 经销商详情-退款原因分析-退货原因
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAgentDetailAfterSaleReturnReason(Map<String, Object> paramMap);
	/**
	 * 折线图 活动数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAgentDetail_ActivityCount_Chart(Map<String, Object> paramMap);
	/**
	 * 经销商详情-活动分析-活动总数
	 * @param paramMap
	 * @return
	 */
	int r_queryAgentDetailActivityCount(Map<String, Object> paramMap);
	/**
	 * 经销商详情-活动分析-活动列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAgentDetailActivityList(Map<String, Object> paramMap);
	
}
