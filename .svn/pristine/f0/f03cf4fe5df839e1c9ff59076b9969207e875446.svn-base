package com.tk.analysis.flow.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
/**
 * 
 * Copyright (c), 2019, Tongku
 * FileName : FlowAnalysisDao.java
 * 运营分析-流量 DAO层
 *
 * @author yejingquan
 * @version 1.00
 * @date Jun 18, 2019
 */

@Repository
public interface FlowAnalysisDao {
	/**
	 * 折线图 全站访客数，全站浏览量<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryVisitorPvCountD_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 商品访客数，商品浏览量<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryProductVisitorPvCountD_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 下单买家数<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryPurchaseNumberD_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 支付买家数<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryPayPurchaseNumberD_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 全站访客数，全站浏览量
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryVisitorPvCount_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 商品访客数，商品浏览量
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryProductVisitorPvCount_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 下单买家数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryPurchaseNumber_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 支付买家数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryPayPurchaseNumber_Chart(Map<String, Object> paramMap);
	/**
	 * 流量总览-全站访客数
	 * @param paramMap
	 * @return
	 */
	float r_queryOverview_VisitorCount(Map<String, Object> paramMap);
	/**
	 * 流量总览-全站浏览量
	 * @param paramMap
	 * @return
	 */
	float r_queryOverview_PvCount(Map<String, Object> paramMap);
	/**
	 * 流量总览-商品收藏数
	 * @param paramMap
	 * @return
	 */
	float r_queryOverview_CollectCount(Map<String, Object> paramMap);
	/**
	 * 流量总览 折线图 商品收藏数<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryOverview_CollectCountD_Chart(Map<String, Object> paramMap);
	/**
	 * 流量总览 折线图 商品收藏数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryOverview_CollectCount_Chart(Map<String, Object> paramMap);
	/**
	 * 流量来源列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryFlowSourceList(Map<String, Object> paramMap);
	/**
	 * 流量来源-引导下单买家数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryFlowSource_PurchaseNumber(Map<String, Object> paramMap);
	/**
	 * 商品访客总数
	 * @param paramMap
	 * @return
	 */
	long r_queryProductVisitorCountTotal(Map<String, Object> paramMap);
	/**
	 * 商品访客数---获取排序后的商品排行
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductRank_ProductVisitorCount_Product(Map<String, Object> paramMap);
	/**
	 * 商品浏览量---获取排序后的商品排行
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductRank_ProductPvCount_Product(Map<String, Object> paramMap);
	/**
	 * 支付买家数---获取排序后的商品排行
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductRank_PayPurchaseNumber_Product(Map<String, Object> paramMap);
	/**
	 * 支付转化率---获取排序后的商品排行
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductRank_PayZhl_Product(Map<String, Object> paramMap);
	/**
	 * 查询默认排序后的商品排行
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductRankListBy_Default(Map<String, Object> paramMap);
	/**
	 * 查询商品信息列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryProductList(Map<String, Object> paramMap);
	/**
	 * 商品-商品访客数,商品浏览量
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryProductVisitorPvCount(Map<String, Object> paramMap);
	/**
	 * 商品-下单买家数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryProduct_PurchaseNumber(Map<String, Object> paramMap);
	/**
	 * 商品-支付买家数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryProduct_PayPurchaseNumber(Map<String, Object> paramMap);
	/**
	 * 流量分布-地区访客总数
	 * @param paramMap
	 * @return
	 */
	long r_queryAreaVisitorCountTotal(Map<String, Object> paramMap);
	/**
	 * 流量分布-城市 访客数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryVisitorCountCity_Rank(Map<String, Object> paramMap);
	/**
	 * 查询访客数排名前十的城市所属省份
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryFlowDistributionMap(Map<String, Object> paramMap);
	/**
	 * 全站访客数---获取排序后的频道页
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryPage_VisitorCount_Page(Map<String, Object> paramMap);
	/**
	 * 全站浏览量---获取排序后的频道页
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryPage_PvCount_Page(Map<String, Object> paramMap);
	/**
	 * 引导下单买家数---获取排序后的频道页
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryPage_PurchaseNumber_Page(Map<String, Object> paramMap);
	/**
	 * 引导下单转化率---获取排序后的频道页
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryPage_Zhl_Page(Map<String, Object> paramMap);
	/**
	 * 引导支付买家数---获取排序后的频道页
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryPage_PayPurchaseNumber_Page(Map<String, Object> paramMap);
	/**
	 * 引导支付转化率---获取排序后的频道页
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryPage_PayZhl_Page(Map<String, Object> paramMap);
	/**
	 * 查询默认排序的频道页
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryPageListBy_Default(Map<String, Object> paramMap);
	/**
	 * 查询系统页和自定义页列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryPageList(Map<String, Object> paramMap);
	
	/**
	 * 页面列表-访客数，浏览量
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryPage_VisitorPvCount(Map<String, Object> paramMap);
	/**
	 * 页面列表-引导下单买家数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryPage_PurchaseNumber(Map<String, Object> paramMap);
	/**
	 * 页面列表-引导支付买家数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryPage_PayPurchaseNumber(Map<String, Object> paramMap);
	/**
	 * 趋势分析 折线图 访客数，浏览量<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryPageTrend_VisitorPvCountD_Chart(Map<String, Object> paramMap);
	/**
	 * 趋势分析 折线图 引导下单买家数<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryPageTrend_PurchaseNumberD_Chart(Map<String, Object> paramMap);
	/**
	 * 趋势分析 折线图 引导支付买家数<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryPageTrend_PayPurchaseNumberD_Chart(Map<String, Object> paramMap);
	/**
	 * 趋势分析 折线图 访客数，浏览量
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryPageTrend_VisitorPvCount_Chart(Map<String, Object> paramMap);
	/**
	 * 趋势分析 折线图 引导下单买家数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryPageTrend_PurchaseNumber_Chart(Map<String, Object> paramMap);
	/**
	 * 趋势分析 折线图 引导支付买家数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryPageTrend_PayPurchaseNumber_Chart(Map<String, Object> paramMap);
	/**
	 * 查询商品总数
	 * @param paramMap
	 * @return
	 */
	int r_queryProductCount(Map<String, Object> paramMap);
	/**
	 * 商品访客数---获取排序后的商品信息
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductVisitorCount_Product(Map<String, Object> paramMap);
	/**
	 * 商品浏览量---获取排序后的商品信息
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductPvCount_Product(Map<String, Object> paramMap);
	/**
	 * 下单买家数---获取排序后的商品信息
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProduct_PurchaseNumber_Product(Map<String, Object> paramMap);
	/**
	 * 下单转化率---获取排序后的商品信息
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProduct_Zhl_Product(Map<String, Object> paramMap);
	/**
	 * 支付买家数---获取排序后的商品信息
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProduct_PayPurchaseNumber_Product(Map<String, Object> paramMap);
	/**
	 * 支付转化率---获取排序后的商品信息
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProduct_PayZhl_Product(Map<String, Object> paramMap);
	/**
	 * 查询默认排序后的商品信息
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductListBy_Default(Map<String, Object> paramMap);
	/**
	 * 访客分析-下单买家总数
	 * @param paramMap
	 * @return
	 */
	long r_queryPurchaseNumberTotal(Map<String, Object> paramMap);
	/**
	 * 访客分析-省份 下单买家数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryPurchaseNumberProvince_Rank(Map<String, Object> paramMap);
	/**
	 * 访客分析-城市 下单买家数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryPurchaseNumberCity_Rank(Map<String, Object> paramMap);
	/**
	 * 访客分析-全站访客总数
	 * @param paramMap
	 * @return
	 */
	float r_queryVisitorCountTotal(Map<String, Object> paramMap);
	/**
	 * 访客分析-省份 全站访客数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryVisitorCountProvince_Rank(Map<String, Object> paramMap);
	/**
	 * 区域分布明细
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAreaDetail(Map<String, Object> paramMap);
	/**
	 * 区域分布地图
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAreaMap(Map<String, Object> paramMap);
	/**
	 * 访问设备
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryFacility_Chart(Map<String, Object> paramMap);
	/**
	 * 移动端分布
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMobileTerminalChart(Map<String, Object> paramMap);
	/**
	 * 手机品牌 折线
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMobileBrandChart(Map<String, Object> paramMap);
	/**
	 * 手机品牌总数
	 * @param paramMap
	 * @return
	 */
	int r_queryMobileBrandCount(Map<String, Object> paramMap);
	/**
	 * 访问人数---获取排序后的手机品牌
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryMobileBrand_VisitorCount(Map<String, Object> paramMap);
	/**
	 * 访问次数---获取排序后的手机品牌
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryMobileBrand_PvCount(Map<String, Object> paramMap);
	/**
	 * 每次平均停留时长---获取排序后的手机品牌
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryMobileBrand_TimeAvg(Map<String, Object> paramMap);
	/**
	 * 查询默认排序的手机品牌
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryMobileBrandListBy_Default(Map<String, Object> paramMap);
	/**
	 * 手机品牌列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMobileBrandList(Map<String, Object> paramMap);
	/**
	 * 查询手机品牌日志信息
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMobileBrand_LogInfo(Map<String, Object> paramMap);
	/**
	 * 访问人数---获取排序后的手机型号
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryMobileModel_VisitorCount(Map<String, Object> paramMap);
	/**
	 * 访问次数---获取排序后的手机型号
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryMobileModel_PvCount(Map<String, Object> paramMap);
	/**
	 * 每次平均停留时长---获取排序后的手机型号
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryMobileModel_TimeAvg(Map<String, Object> paramMap);
	/**
	 * 查询默认排序的手机型号
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryMobileModelListBy_Default(Map<String, Object> paramMap);
	/**
	 * 手机型号列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMobileModelList(Map<String, Object> paramMap);
	/**
	 * 查询手机型号日志信息
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMobileModel_LogInfo(Map<String, Object> paramMap);
}
