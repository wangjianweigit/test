package com.tk.analysis.activity.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * Copyright (c), 2019, Tongku
 * FileName : ActivityAnalysisDao.java
 * 运营分析-活动 dao层
 *
 * @author yejingquan
 * @version 1.00
 * @date Aug 29, 2019
 */
@Repository
public interface ActivityAnalysisDao {
	
	/**
	 * 支付买家数、活动销售总额
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> r_queryRealTime_ActivityPayPurchaseNumberMoney(Map<String, Object> paramMap);
	/**
	 * 今日活动商品定金
	 * @param paramMap
	 * @return
	 */
	float r_queryRealTime_ActivityPreFirstMoney(Map<String, Object> paramMap);
	/**
	 * 活动数
	 * @param paramMap
	 * @return
	 */
	float r_queryRealTime_ActivityCount(Map<String, Object> paramMap);
	/**
	 * 活动商品数
	 * @param paramMap
	 * @return
	 */
	float r_queryRealTime_ActivityProductCount(Map<String, Object> paramMap);
	/**
	 * 活动商品访客数
	 * @param paramMap
	 * @return
	 */
	float r_queryRealTime_ActivityProductVisitorCount(Map<String, Object> paramMap);
	/**
	 * 活动商品分享次数
	 * @param paramMap
	 * @return
	 */
	float r_queryRealTime_ActivityProductShareCount(Map<String, Object> paramMap);
	/**
	 * 折线图 活动销售额
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryRealTime_ActivityPayMoney_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 预定订单的定金
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryRealTime_ActivityPreFirstMoney_Chart(Map<String, Object> paramMap);
	/**
	 * 今日活动商品销售额
	 * @param paramMap
	 * @return
	 */
	float r_queryRealTime_ActivityPayMoney(Map<String, Object> paramMap);
	/**
	 * 今日平台商品销售额
	 * @param paramMap
	 * @return
	 */
	float r_queryRealTime_PayMoney(Map<String, Object> paramMap);
	/**
	 * 今日平台商品定金
	 * @param paramMap
	 * @return
	 */
	float r_queryRealTime_PreFirstMoney(Map<String, Object> paramMap);
	/**
	 * 今日活动支付单数
	 * @param paramMap
	 * @return
	 */
	float r_queryRealTime_ActivityPayNum(Map<String, Object> paramMap);
	/**
	 * 今日平台支付单数
	 * @param paramMap
	 * @return
	 */
	float r_queryRealTime_PayNum(Map<String, Object> paramMap);
	/**
	 * 今日活动
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryRealTime_ActivityList(Map<String, Object> paramMap);
	/**
	 * 活动详情
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> r_queryActivityDetail(Map<String, Object> paramMap);
	/**
	 * 参与活动会员数
	 * @param paramMap
	 * @return
	 */
	float r_queryActivityMemberCount(Map<String, Object> paramMap);
	/**
	 * 活动商品数
	 * @param paramMap
	 * @return
	 */
	float r_queryActivityProductCount(Map<String, Object> paramMap);
	/**
	 * 品牌数量
	 * @param paramMap
	 * @return
	 */
	float r_queryActivityBrandCount(Map<String, Object> paramMap);
	/**
	 * 商家数量
	 * @param paramMap
	 * @return
	 */
	float r_queryActivityStationedCount(Map<String, Object> paramMap);
	/**
	 * 预定订单笔数
	 * @param paramMap
	 * @return
	 */
	float r_queryRealTime_PreOrderNum(Map<String, Object> paramMap);
	/**
	 * 成交总额
	 * @param paramMap
	 * @return
	 */
	float r_queryRealTime_PaymentMoney(Map<String, Object> paramMap);
	/**
	 * 预定订单的定金
	 * @param paramMap
	 * @return
	 */
	float r_queryRealTime_earnestMoney(Map<String, Object> paramMap);
	/**
	 * 商品分享次数
	 * @param paramMap
	 * @return
	 */
	float r_queryActivityTrend_ProductShareCount(Map<String, Object> paramMap);
	/**
	 * 商品销售总额
	 * @param paramMap
	 * @return
	 */
	float r_queryActivityTrend_PayMoney(Map<String, Object> paramMap);
	/**
	 * 预定订单的定金
	 * @param paramMap
	 * @return
	 */
	float r_queryActivityTrend_PreFirstMoney(Map<String, Object> paramMap);
	/**
	 * 活动支付单笔数
	 * @param paramMap
	 * @return
	 */
	float r_queryActivityTrend_PayNum(Map<String, Object> paramMap);
	/**
	 * 分享支付单数
	 * @param paramMap
	 * @return
	 */
	float r_queryActivityTrend_SharePayNum(Map<String, Object> paramMap);
	/**
	 * 今日活动趋势分析 折线图 商品分享次数<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryRealTime_Trend_ShareCountD_Chart(Map<String, Object> paramMap);
	/**
	 * 今日活动趋势分析 折线图 商品销售总额<天>
	 * @param paramMap
	 * @return	
	 */
	List<Map<String, Object>> r_queryRealTime_Trend_PayMoneyD_Chart(Map<String, Object> paramMap);
	/**
	 * 今日活动趋势分析 折线图 预定订单的定金<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryRealTime_Trend_PreFirstMoneyD_Chart(Map<String, Object> paramMap);
	/**
	 * 今日活动趋势分析 折线图 活动支付单笔数<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryRealTime_Trend_PayNumD_Chart(Map<String, Object> paramMap);
	/**
	 * 今日活动趋势分析 折线图 分享支付单数<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryRealTime_Trend_SharePayNumD_Chart(Map<String, Object> paramMap);
	/**
	 * 活动商品总数
	 * @param paramMap
	 * @return
	 */
	int r_queryProductCount(Map<String, Object> paramMap);
	/**
	 * 浏览量---获取排序后的商品列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProduct_PvCount(Map<String, Object> paramMap);
	/**
	 * 活动销量---获取排序后的商品列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProduct_PayCount(Map<String, Object> paramMap);
	/**
	 * 活动销售总额---获取排序后的商品列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProduct_PayMoney(Map<String, Object> paramMap);
	/**
	 * 活动买家数---获取排序后的商品列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProduct_PurchaseNumber(Map<String, Object> paramMap);
	/**
	 * 查询默认排序的商品列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductListBy_Default(Map<String, Object> paramMap);
	/**
	 * 查询商品信息
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryProductList(Map<String, Object> paramMap);
	/**
	 * 获取商品阶梯价
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryProductLadderDiscount(Map<String, Object> paramMap);
	/**
	 * 获取商品活动价
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryProductHdPrice(Map<String, Object> paramMap);
	/**
	 * 浏览量
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryProductPvCount(Map<String, Object> paramMap);
	/**
	 * 活动销量，活动销售总额，活动买家数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryProduct_PayPurchaseNumberCountMoney(Map<String, Object> paramMap);
	/**
	 * 查询预定订单的定金
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryProduct_PreFirstMoney(Map<String, Object> paramMap);
	/**
	 * 活动分析-活动日历
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryActivityAnalysisList(Map<String, Object> paramMap);
	/**
	 * 活动分析-活动商品销量、商品销售总额、支付买家数
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> r_queryActivityPurchaseNumberCountMoney(Map<String, Object> paramMap);
	/**
	 * 活动分析-活动商品预定订单的定金
	 * @param paramMap
	 * @return
	 */
	float r_queryActivityProductPreFirstMoney(Map<String, Object> paramMap);
	/**
	 * 活动分析-活动未发退款总额
	 * @param paramMap
	 * @return
	 */
	float r_queryActivityUnsentRefundMoney(Map<String, Object> paramMap);
	/**
	 * 活动分析-活动成交总额
	 * @param paramMap
	 * @return
	 */
	float r_queryActivityPaymentMoney(Map<String, Object> paramMap);
	/**
	 * 活动分析-活动预定订单的定金
	 * @param paramMap
	 * @return
	 */
	float r_queryActivityPreFirstMoney(Map<String, Object> paramMap);
	/**
	 * 活动商品访客数
	 * @param paramMap
	 * @return
	 */
	float r_queryActivityTrend_ProductVisitorCount(Map<String, Object> paramMap);
	/**
	 * 活动支付买家数
	 * @param paramMap
	 * @return
	 */
	float r_queryActivityTrend_PayPurchaseNumber(Map<String, Object> paramMap);
	/**
	 * 活动支付件数
	 * @param paramMap
	 * @return
	 */
	float r_queryActivityTrend_PayCount(Map<String, Object> paramMap);
	/**
	 * 折线图 活动商品访客数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryActivityTrend_ProductVisitorCount_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 活动销售总额，活动支付件数，活动支付买家数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryActivityTrend_PayPurchaseNumberCountMoney_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 预定订单的定金
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryActivityTrend_PreFirstMoney_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 活动支付单笔数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryActivityTrend_PayNum_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 商品分享次数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryActivityTrend_ShareCount_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 分享支付单数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryActivityTrend_SharePayNum_Chart(Map<String, Object> paramMap);
	/**
	 * 活动商品销售额
	 * @param paramMap
	 * @return
	 */
	float r_queryActivity_PayMoney(Map<String, Object> paramMap);
	/**
	 * 活动商品定金
	 * @param paramMap
	 * @return
	 */
	float r_queryActivity_PreFirstMoney(Map<String, Object> paramMap);
	/**
	 * 平台商品销售额
	 * @param paramMap
	 * @return
	 */
	float r_queryPayMoney(Map<String, Object> paramMap);
	/**
	 * 平台商品定金
	 * @param paramMap
	 * @return
	 */
	float r_queryPreFirstMoney(Map<String, Object> paramMap);
	/**
	 * 平台支付单笔数
	 * @param paramMap
	 * @return
	 */
	float r_queryPayNum(Map<String, Object> paramMap);
	/**
	 * 未发退款总额---获取排序后的商品列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProduct_UnsendRefundMoney(Map<String, Object> paramMap);
	/**
	 * 未发退款总额
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryProductUnsendRefundMoney(Map<String, Object> paramMap);
	/**
	 * 品牌总数
	 * @param paramMap
	 * @return
	 */
	int r_queryBrandCount(Map<String, Object> paramMap);
	/**
	 * 活动销量---获取排序后的品牌列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryBrand_PayCount(Map<String, Object> paramMap);
	/**
	 * 活动销售总额---获取排序后的品牌列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryBrand_PayMoney(Map<String, Object> paramMap);
	/**
	 * 活动买家数---获取排序后的品牌列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryBrand_PayPurchaseNumber(Map<String, Object> paramMap);
	/**
	 * 查询默认排序的品牌列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryBrandListBy_Default(Map<String, Object> paramMap);
	/**
	 * 查询品牌列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryBrandList(Map<String, Object> paramMap);
	/**
	 * 品牌列表-活动销量，活动销售总额，活动买家数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryBrand_PayPurchaseNumberCountMoney(Map<String, Object> paramMap);
	/**
	 * 品牌列表-预定订单的定金
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryBrand_PreFirstMoney(Map<String, Object> paramMap);
	/**
	 * 入驻商总数
	 * @param paramMap
	 * @return
	 */
	int r_queryStationedCount(Map<String, Object> paramMap);
	/**
	 * 活动销量---获取排序后的入驻商列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryStationed_PayCount(Map<String, Object> paramMap);
	/**
	 * 活动销售总额---获取排序后的入驻商列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryStationed_PayMoney(Map<String, Object> paramMap);
	/**
	 * 活动买家数---获取排序后的入驻商列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryStationed_PayPurchaseNumber(Map<String, Object> paramMap);
	/**
	 * 查询默认排序的入驻商列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryStationedListBy_Default(Map<String, Object> paramMap);
	/**
	 * 查询入驻商列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryStationedList(Map<String, Object> paramMap);
	/**
	 * 入驻商列表-活动销量，活动销售总额，活动买家数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryStationed_PayPurchaseNumberCountMoney(Map<String, Object> paramMap);
	/**
	 * 入驻商列表-预定订单的定金
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryStationed_PreFirstMoney(Map<String, Object> paramMap);
	/**
	 * 查询活动参与会员的类型
	 * @param paramMap
	 * @return
	 */
	int r_queryActivityMemberType(Map<String, Object> paramMap);
	/**
	 * 会员总数
	 * @param paramMap
	 * @return
	 */
	int r_queryUserCount(Map<String, Object> paramMap);
	/**
	 * 购买数量---获取排序后的会员列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryUser_PayCount(Map<String, Object> paramMap);
	/**
	 * 支付商品总额---获取排序后的会员列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryUser_PayMoney(Map<String, Object> paramMap);
	/**
	 * 未发退款商品总额---获取排序后的会员列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryUser_UnsendRefundMoney(Map<String, Object> paramMap);
	/**
	 * 查询默认排序的会员列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryUserListBy_Default(Map<String, Object> paramMap);
	/**
	 * 查询会员列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryUserList(Map<String, Object> paramMap);
	/**
	 * 会员列表-购买数量，支付商品总额
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryUser_PayCountMoney(Map<String, Object> paramMap);
	/**
	 * 会员列表-预定订单的定金
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryUser_PreFirstMoney(Map<String, Object> paramMap);
	/**
	 * 会员列表-未发退款商品总额
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryUserUnsendRefundMoney(Map<String, Object> paramMap);
	/**
	 * 查询活动商品阶梯价
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryActivityProductSpecpize(Map<String, Object> paramMap);
	/**
	 * 查询活动站点
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryActivitySite(Map<String, Object> paramMap);
	/**
	 * 查询用户站点
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryUserSite(Map<String, Object> paramMap);
}
