package com.tk.analysis.transaction.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tk.oms.basicinfo.dao.BaseDao;

@Repository
public interface TransactionAnalysisDao extends BaseDao<Map<String, Object>> {
	
	/**
	 * 折线图 商品访客数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryProductVisitorCount_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 商品访客数<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryProductVisitorCountD_Chart(Map<String, Object> paramMap);
	/**
	 * 交易总览-全站访客数
	 * @param paramMap
	 * @return
	 */
	float r_queryVisitorCount(Map<String, Object> paramMap);
	/**
	 * 交易总览-商品访客数
	 * @param paramMap
	 * @return
	 */
	float r_queryProductVisitorCount(Map<String, Object> paramMap);
	/**
	 * 交易总览-下单买家数
	 * @param paramMap
	 * @return
	 */
	float r_queryPurchaseNumber(Map<String, Object> paramMap);
	/**
	 * 交易总览-订单商品总额
	 * @param paramMap
	 * @return
	 */
	float r_queryOrderMoney(Map<String, Object> paramMap);
	/**
	 * 交易总览-支付买家数
	 * @param paramMap
	 * @return
	 */
	float r_queryPayPurchaseNumber(Map<String, Object> paramMap);
	/**
	 * 交易总览-销售总额
	 * @param paramMap
	 * @return
	 */
	float r_queryPayMoney(Map<String, Object> paramMap);
	/**
	 * 交易总览-预定订单的定金
	 * @param paramMap
	 * @return
	 */
	float r_queryPreFirstMoney(Map<String, Object> paramMap);
	/**
	 * 交易趋势-商品访客数
	 * @param paramMap
	 * @return
	 */
	float r_queryTrend_ProductVisitorCount(Map<String, Object> paramMap);
	/**
	 * 交易趋势-支付买家数
	 * @param paramMap
	 * @return
	 */
	float r_queryTrend_PayPurchaseNumber(Map<String, Object> paramMap);
	/**
	 * 交易趋势-订单笔数
	 * @param paramMap
	 * @return
	 */
	float r_queryTrend_OrderNum(Map<String, Object> paramMap);
	/**
	 * 交易趋势-订单商品数
	 * @param paramMap
	 * @return
	 */
	float r_queryTrend_OrderCount(Map<String, Object> paramMap);
	/**
	 * 交易趋势-成功退款金额
	 * @param paramMap
	 * @return
	 */
	float r_queryTrend_ReturnMoney(Map<String, Object> paramMap);
	/**
	 * 交易趋势-下单买家数
	 * @param paramMap
	 * @return
	 */
	float r_queryTrend_PurchaseNumber(Map<String, Object> paramMap);
	/**
	 * 交易趋势-商品访客数 折线图<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryTrend_ProductVisitorCountD_Chart(Map<String, Object> paramMap);
	/**
	 * 交易趋势-订单笔数 折线图<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryTrend_OrderNumD_Chart(Map<String, Object> paramMap);
	/**
	 * 交易趋势-下单买家数、订单商品数 折线图<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryTrend_PurchaseNumberCountD_Chart(Map<String, Object> paramMap);
	/**
	 * 交易趋势-支付买家数、销售总额 折线图<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryTrend_PayPurchaseNumberMoneyD_Chart(Map<String, Object> paramMap);
	/**
	 * 交易趋势-预订订单的订金 折线图<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryTrend_PreFirstMoneyD_Chart(Map<String, Object> paramMap);
	/**
	 * 交易趋势-成功退款金额 折线图<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryTrend_ReturnMoneyD_Chart(Map<String, Object> paramMap);
	/**
	 * 交易趋势-商品访客数 折线图
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryTrend_ProductVisitorCount_Chart(Map<String, Object> paramMap);
	/**
	 * 交易趋势-订单笔数 折线图
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryTrend_OrderNum_Chart(Map<String, Object> paramMap);
	/**
	 * 交易趋势-下单买家数、订单商品数 折线图
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryTrend_PurchaseNumberCount_Chart(Map<String, Object> paramMap);
	/**
	 * 交易趋势-支付买家数、销售总额 折线图
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryTrend_PayPurchaseNumberMoney_Chart(Map<String, Object> paramMap);
	/**
	 * 交易趋势-预定订单的定金 折线图
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryTrend_PreFirstMoney_Chart(Map<String, Object> paramMap);
	/**
	 * 交易趋势-成功退款金额 折线图
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryTrend_ReturnMoney_Chart(Map<String, Object> paramMap);
	/**
	 * 终端列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryTerminalList(Map<String, Object> paramMap);
	/**
	 * 终端构成-商品访客数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryTerminal_ProductVisitorCount(Map<String, Object> paramMap);
	/**
	 * 终端构成-支付买家数、销售总额、支付商品数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryTerminal_PayPurchaseNumberMoneyCount(Map<String, Object> paramMap);
	/**
	 * 终端构成-预定订单的定金
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryTerminal_PreFirstMoney(Map<String, Object> paramMap);
	/**
	 * 商品分类列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryProductTypeList(Map<String, Object> paramMap);
	/**
	 * 商品类目构成-访客数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryClassify_VisitorCount(Map<String, Object> paramMap);
	/**
	 * 商品类目构成-支付买家数、销售总额、支付商品数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryClassify_PurchaseNumberMoneyCount(Map<String, Object> paramMap);
	/**
	 * 品牌列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryBrandList(Map<String, Object> paramMap);
	/**
	 * 品牌构成-商品访客数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryBrand_ProductVisitorCount(Map<String, Object> paramMap);
	/**
	 * 品牌构成-支付买家数、销售总额、支付商品数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryBrand_PayPurchaseNumberMoneyCount(Map<String, Object> paramMap);
	/**
	 * 品牌构成-预定订单的定金
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryBrand_PreFirstMoney(Map<String, Object> paramMap);
	/**
	 * 品牌构成-所有品牌的支付总金额
	 * @param paramMap
	 * @return
	 */
	float r_queryBrand_AllPayMoney(Map<String, Object> paramMap);
	/**
	 * 品牌构成-所有品牌的预定订单的定金
	 * @param paramMap
	 * @return
	 */
	float r_queryBrand_AllPreFirstMoney(Map<String, Object> paramMap);
	/**
	 * 数据趋势-支付买家数、销售总额和支付商品数 折线图
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryDataTrend_PayPurchaseNumberMoneyCount_Chart(Map<String, Object> paramMap);
	/**
	 * 数据趋势-预定订单的定金 折线图
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryDataTrend_PreFirstMoney_Chart(Map<String, Object> paramMap);
	/**
	 * 数据趋势-支付买家数、销售总额和支付商品数 折线图<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryDataTrend_PayPurchaseNumberMoneyCountD_Chart(Map<String, Object> paramMap);
	/**
	 * 数据趋势-预定订单的定金 折线图<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryDataTrend_PreFirstMoneyD_Chart(Map<String, Object> paramMap);
	/**
	 * 终端下拉框
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryTerminalSelect(Map<String, Object> paramMap);
	/**
	 * 品牌下拉框
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryBrandSelect(Map<String, Object> paramMap);
	/**
	 * 查询默认排序的品牌信息
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryBrandListBy_Default(Map<String, Object> paramMap);
	/**
	 * 商品访客数---获取排序后的品牌信息
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductVisitorCount_Brand(Map<String, Object> paramMap);
	/**
	 * 销售总额---获取排序后的品牌信息
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryPayMoney_Brand(Map<String, Object> paramMap);
	/**
	 * 销售总额占比---获取排序后的品牌信息
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryMoneyRatio_Brand(Map<String, Object> paramMap);
	/**
	 * 支付商品数---获取排序后的品牌信息
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryPayCount_Brand(Map<String, Object> paramMap);
	/**
	 * 支付买家数---获取排序后的品牌信息
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryPayPurchaseNumber_Brand(Map<String, Object> paramMap);
	/**
	 * 支付转化率---获取排序后的品牌信息
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryPayZhl_Brand(Map<String, Object> paramMap);

}
