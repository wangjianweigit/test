package com.tk.analysis.product.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tk.oms.basicinfo.dao.BaseDao;

@Repository
public interface ProductAnalysisOperationDao extends BaseDao<Map<String, Object>> {
	
	/**
	 * 查询商品概况
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryProductSurvey(Map<String, Object> paramMap);
	/**
	 * 其他 商品销售总额(包含尾款定金)
	 * @param paramMap
	 * @return
	 */
	float r_queryOther_AllPayMoney(Map<String, Object> paramMap);
	/**
	 * 其他 预定订单的定金
	 * @param paramMap
	 * @return
	 */
	float r_queryOther_PreFirstMoney(Map<String, Object> paramMap);
	/**
	 * 其他 折线图 商品访客数和商品浏览量
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_ProductVisitorPvCount_Chart(Map<String, Object> paramMap);
	/**
	 * 其他 折线图 下单买家数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_PurchaseNumber_Chart(Map<String, Object> paramMap);
	/**
	 * 其他 折线图 订单商品件数、订单商品总额
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_OrderCountMoney_Chart(Map<String, Object> paramMap);
	/**
	 * 其他 折线图 支付买家数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_PayPurchaseNumber_Chart(Map<String, Object> paramMap);
	/**
	 * 其他 折线图 支付件数、商品销售总额
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_PayCountMoney_Chart(Map<String, Object> paramMap);
	/**
	 * 其他 折线图 预定订单的定金
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_PreFirstMoney_Chart(Map<String, Object> paramMap);
	/**
	 * 其他 折线图 退款商品数和退款商品金额
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_Return_Chart(Map<String, Object> paramMap);
	/**
	 * 其他 折线图 商品访客数，商品浏览量<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_ProductVisitorPvCountD_Chart(Map<String, Object> paramMap);
	/**
	 * 其他 折线图 下单买家数<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_PurchaseNumberD_Chart(Map<String, Object> paramMap);
	/**
	 * 其他 折线图 订单商品件数、订单商品总额<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_OrderCountMoneyD_Chart(Map<String, Object> paramMap);
	/**
	 * 其他 折线图 支付买家数<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_PayPurchaseNumberD_Chart(Map<String, Object> paramMap);
	/**
	 * 其他 折线图 支付件数、商品销售总额<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_PayCountMoneyD_Chart(Map<String, Object> paramMap);
	/**
	 * 其他 折线图 预定订单的定金<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_PreFirstMoneyD_Chart(Map<String, Object> paramMap);
	/**
	 * 其他 折线图 退款商品数和退款商品金额<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_ReturnD_Chart(Map<String, Object> paramMap);
	/**
	 * 其他 下单买家总数
	 * @param paramMap
	 * @return
	 */
	long r_queryOther_PurchaseNumberTotal(Map<String, Object> paramMap);
	/**
	 * 其他 按省份 下单买家数 排行榜
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_PurchaseNumberProvince_Rank(Map<String, Object> paramMap);
	/**
	 * 其他 按城市 下单买家数 排行榜
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_PurchaseNumberCity_Rank(Map<String, Object> paramMap);
	/**
	 * 其他 成交总金额
	 * @param paramMap
	 * @return
	 */
	float r_queryOther_PayMoneyTotal(Map<String, Object> paramMap);
	/**
	 * 其他 按省份 成交金额 排行榜
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_PayMoneyProvince_Rank(Map<String, Object> paramMap);
	/**
	 * 其他 按城市 成交金额 排行榜
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_PayMoneyCity_Rank(Map<String, Object> paramMap);
	/**
	 * 其他 销售区域明细
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_SaleAreaDetail(Map<String, Object> paramMap);
	/**
	 * 其他 销售区域地图
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_SaleAreaMap(Map<String, Object> paramMap);
	/**
	 * 其他 核心数据分析-商品访客数
	 * @param paramMap
	 * @return
	 */
	float r_queryOther_VisitorCount(Map<String, Object> paramMap);
	/**
	 * 其他 核心数据分析-商品浏览量
	 * @param paramMap
	 * @return
	 */
	float r_queryOther_PvCount(Map<String, Object> paramMap);
	/**
	 * 其他 核心数据分析-下单买家数
	 * @param paramMap
	 * @return
	 */
	float r_queryOther_OPurchaseNumber(Map<String, Object> paramMap);
	/**
	 * 其他 核心数据分析-订单商品件数
	 * @param paramMap
	 * @return
	 */
	float r_queryOther_OCount(Map<String, Object> paramMap);
	/**
	 * 其他 核心数据分析-订单商品总额
	 * @param paramMap
	 * @return
	 */
	float r_queryOther_OMoney(Map<String, Object> paramMap);
	/**
	 * 其他 核心数据分析-支付买家数
	 * @param paramMap
	 * @return
	 */
	float r_queryOther_PayPurchaseNumber(Map<String, Object> paramMap);
	/**
	 * 其他 核心数据分析-支付件数
	 * @param paramMap
	 * @return
	 */
	float r_queryOther_PayCount(Map<String, Object> paramMap);
	/**
	 * 其他 核心数据分析-订单商品总额
	 * @param paramMap
	 * @return
	 */
	float r_queryOther_PayMoney(Map<String, Object> paramMap);
	/**
	 * 其他 核心数据分析-退款商品金额
	 * @param paramMap
	 * @return
	 */
	float r_queryOther_ReturnMoney(Map<String, Object> paramMap);
	/**
	 * 其他 核心数据分析-退款商品数
	 * @param paramMap
	 * @return
	 */
	float r_queryOther_ReturnCount(Map<String, Object> paramMap);
	/**
	 * 其他 商品汇总排行总数
	 * @param paramMap
	 * @return
	 */
	int r_queryOther_ProductSummaryCount(Map<String, Object> paramMap);
	/**
	 * 单品分析搜索
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryProductSingleSearch(Map<String, Object> paramMap);
	/**
	 * 单品分析总数
	 * @param paramMap
	 * @return
	 */
	int r_queryProductSingleCount(Map<String, Object> paramMap);
	/**
	 * 其他 单品分析-商品信息
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> r_queryOther_SignleProductInfo(Map<String, Object> paramMap);
	/**
	 * 其他 单品分析-商品总销量
	 * @param paramMap
	 * @return
	 */
	float r_queryOther_SignleProductTotalSaleCount(Map<String, Object> paramMap);
	/**
	 * 单品分析-按颜色统计
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryProductSignleColorList(Map<String, Object> paramMap);
	/**
	 * 其他 单品分析-按颜色统计
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_ProductSignleColorList(Map<String, Object> paramMap);
	/**
	 * 单品分析-按规格统计
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryProductSignleSpecsList(Map<String, Object> paramMap);
	/**
	 * 其他 单品分析-按规格统计
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_ProductSignleSpecsList(Map<String, Object> paramMap);
	/**
	 * 其他 单品分析-商品销量sku明细
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_ProductSignleSkuList(Map<String, Object> paramMap);
	/**
	 * 其他 折线图 sku支付件数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_SkuPayCount_Chart(Map<String, Object> paramMap);
	/**
	 * 其他 折线图 sku退款商品数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_SkuReturnCount_Chart(Map<String, Object> paramMap);
	/**
	 * 其他 折线图 sku支付件数<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_SkuPayCountD_Chart(Map<String, Object> paramMap);
	/**
	 * 其他 折线图 sku退款商品数<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_SkuReturnCountD_Chart(Map<String, Object> paramMap);
	/**
	 * 其他 单品分析-销售区域列表总数
	 * @param paramMap
	 * @return
	 */
	int r_queryOther_ProductSingleSaleAreaCount(Map<String, Object> paramMap);
	/**
	 * 其他 单品分析-销售区域分析
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_ProductSingleSaleArea(Map<String, Object> paramMap);
	
	/**
	 * 其他 单品分析-销售区域地图
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_SingleSaleAreaMap(Map<String, Object> paramMap);
	/**
	 * 其他 单品分析-销售区域分析-sku销量明细
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_SkuSaleDetail(Map<String, Object> paramMap);
	/**
	 * 其他 单品分析-售后分析
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_ProductSingleAfterSale_Chart(Map<String, Object> paramMap);
	/**
	 * 其他 商品退货分析-待处理
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryPendingDetail(Map<String, Object> paramMap);
	/**
	 * 其他 商品退货分析-退货退款信息
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryProductReturnDetail(Map<String, Object> paramMap);
	/**
	 * 其他 商品退货分析-已收货待退款
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryTakeDeliveryRefundDetail(Map<String, Object> paramMap);
	/**
	 * 其他 退货数据分析-成功退款笔数
	 * @param paramMap
	 * @return
	 */
	float r_queryOther_ReturnNum(Map<String, Object> paramMap);
	/**
	 * 其他 退货数据分析-驳回申请笔数
	 * @param paramMap
	 * @return
	 */
	float r_queryOther_RejectNum(Map<String, Object> paramMap);
	/**
	 * 其他 退货数据分析-品质退换货笔数
	 * @param paramMap
	 * @return
	 */
	float r_queryOther_QualityReturnNum(Map<String, Object> paramMap);
	/**
	 * 其他 退货数据分析-品质退换货件数
	 * @param paramMap
	 * @return
	 */
	float r_queryOther_QualityReturnCount(Map<String, Object> paramMap);
	/**
	 * 其他 折线图 成功退款笔数和退款商品数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_ReturnCount_Chart(Map<String, Object> paramMap);
	/**
	 * 其他 折线图 驳回申请笔数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_RejectNum_Chart(Map<String, Object> paramMap);
	/**
	 * 其他 折线图 品质退换货笔数和品质退换货件数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_QualityReturnCount_Chart(Map<String, Object> paramMap);
	/**
	 * 其他 折线图 成功退款笔数和退款商品数<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_ReturnCountD_Chart(Map<String, Object> paramMap);
	/**
	 * 其他 折线图 驳回申请笔数<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_RejectNumD_Chart(Map<String, Object> paramMap);
	/**
	 * 其他 折线图 品质退换货笔数和品质退换货件数<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_QualityReturnCountD_Chart(Map<String, Object> paramMap);
	/**
	 * 其他 商品退货分析-商品总数
	 * @param paramMap
	 * @return
	 */
	int r_queryOther_ReturnProductRankCount(Map<String, Object> paramMap);
	/**
	 * 其他 商品退货分析-商品列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_ReturnProductListForPage(Map<String, Object> paramMap);
	/**
	 * 品质退换货商品总数
	 * @param paramMap
	 * @return
	 */
	float r_queryReturnQualityCount(Map<String, Object> paramMap);
	/**
	 * 其他 商品退货分析-品质退换货分析
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryReturnQualityList(Map<String, Object> paramMap);
	/**
	 * 其他 商品退货分析-退货商家排行
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryReturnStationed_Rank(Map<String, Object> paramMap);
	/**
	 * 其他 商品退货分析-退货品牌排行
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryReturnBrand_Rank(Map<String, Object> paramMap);
	/**
	 * 异常商品-流量下跌总数
	 * @param paramMap
	 * @return
	 */
	int r_queryAbnormalFlowCount(Map<String, Object> paramMap);
	/**
	 * 异常商品-流量下跌列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAbnormalFlowListForPage(Map<String, Object> paramMap);
	/**
	 * 异常商品-支付转化率低总数
	 * @param paramMap
	 * @return
	 */
	int r_queryAbnormalZhlCount(Map<String, Object> paramMap);
	/**
	 * 异常商品-同类商品支付转化率平均值
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAvgZhlList(Map<String, Object> paramMap);
	/**
	 * 异常商品-支付转化率低列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAbnormalZhlListForPage(Map<String, Object> paramMap);
	/**
	 * 异常商品-退款预警总数
	 * @param paramMap
	 * @return
	 */
	int r_queryAbnormalRefundCount(Map<String, Object> paramMap);
	/**
	 * 异常商品-退款预警列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAbnormalRefundListForPage(Map<String, Object> paramMap);
	/**
	 * 异常商品-库存预警总数
	 * @param paramMap
	 * @return
	 */
	int r_queryAbnormalStockCount(Map<String, Object> paramMap);
	/**
	 * 异常商品-库存预警列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAbnormalStockListForPage(Map<String, Object> paramMap);
	/**
	 * 异常商品-滞销商品总数
	 * @param paramMap
	 * @return
	 */
	int r_queryAbnormalUnsalableCount(Map<String, Object> paramMap);
	/**
	 * 异常商品-滞销商品列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryAbnormalUnsalableListForPage(Map<String, Object> paramMap);
	/**
	 * 获取商品库存
	 * @param list
	 * @return
	 */
	List<Map<String, Object>> queryProductStockCount(List<String> list);
	/**
	 * 根据待退货数排序
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryReturnStayProductCountListForPage(Map<String, Object> paramMap);
	/**
	 * 商品退货信息
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOther_ProductReturnInfo(Map<String, Object> paramMap);
	/**
	 * 查询待退货数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryReturnStayProductCount(Map<String, Object> paramMap);
	/**
	 * 获取商品sku库存
	 * @param list
	 * @return
	 */
	List<Map<String, Object>> queryProductSkuStockCount(List<String> list);
	/**
	 * 商品库存明细
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryProductStockDetail(Map<String, Object> paramMap);
	/**
	 * 获取成品仓列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryWarehouseList(Map<String, Object> paramMap);
	/**
	 * 根据仓库查询商品SKU库存
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryWarehouseProductSkuStock(Map<String, Object> paramMap);
	/**
	 * 销售价---获取排序后的商品信息
	 * @param paramMap
	 * @return
	 */
	List<String> querySalePrice_Product(Map<String, Object> paramMap);
	/**
	 * 累计销量---获取排序后的商品信息
	 * @param paramMap
	 * @return
	 */
	List<String> r_querySaleCount_Product(Map<String, Object> paramMap);
	/**
	 * 剩余库存---获取排序后的用户信息
	 * @param paramMap
	 * @return
	 */
	List<String> queryStockCount_Product(Map<String, Object> paramMap);
	/**
	 * 发布时间---获取排序后的用户信息
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryPublishDate_Product(Map<String, Object> paramMap);
	/**
	 * 查询默认排序的商品信息
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductSingleListBy_Default(Map<String, Object> paramMap);
	/**
	 * 单品分析-商品列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryProductSingleList(Map<String, Object> paramMap);
	/**
	 * 获取销售价
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> querySalePrice(Map<String, Object> paramMap);
	/**
	 * 获取累计销量
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_querySaleCount(Map<String, Object> paramMap);
	/**
	 * 获取剩余库存
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryStockCount(Map<String, Object> paramMap);
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
	 * 下单买家数---获取排序后的商品排行
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductRank_PurchaseNumber_Product(Map<String, Object> paramMap);
	/**
	 * 订单商品件数---获取排序后的商品排行
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductRank_OrderCount_Product(Map<String, Object> paramMap);
	/**
	 * 下单商品总额---获取排序后的商品排行
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductRank_OrderMoney_Product(Map<String, Object> paramMap);
	/**
	 * 下单转化率---获取排序后的商品排行
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductRank_Zhl_Product(Map<String, Object> paramMap);
	/**
	 * 支付买家数---获取排序后的商品排行
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductRank_PayPurchaseNumber_Product(Map<String, Object> paramMap);
	/**
	 * 支付件数---获取排序后的商品排行
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductRank_PayCount_Product(Map<String, Object> paramMap);
	/**
	 * 商品销售总额---获取排序后的商品排行
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductRank_PayMoney_Product(Map<String, Object> paramMap);
	/**
	 * 支付转化率---获取排序后的商品排行
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductRank_PayZhl_Product(Map<String, Object> paramMap);
	/**
	 * 退款商品数---获取排序后的商品排行
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductRank_ReturnCount_Product(Map<String, Object> paramMap);
	/**
	 * 退款商品金额---获取排序后的商品排行
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductRank_ReturnMoney_Product(Map<String, Object> paramMap);
	/**
	 * 查询默认排序的商品排行
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
	 * 商品-商品访客数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryProductVisitorCount(Map<String, Object> paramMap);
	/**
	 * 商品-商品浏览量
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryProductPvCount(Map<String, Object> paramMap);
	/**
	 * 商品-下单买家数、订单商品件数、订单商品总额
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryProduct_PurchaseNumberCountMoney(Map<String, Object> paramMap);
	/**
	 * 商品-支付买家数、支付件数、商品销售总额
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryProduct_PayPurchaseNumberCountMoney(Map<String, Object> paramMap);
	/**
	 * 商品-预定订单的定金
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryProduct_PreFirstMoney(Map<String, Object> paramMap);
	/**
	 * 商品-退货信息
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryProduct_ReturnInfo(Map<String, Object> paramMap);
	/**
	 * 商品访客数---获取排序后的商品列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductSale_ProductVisitorCount_Product(Map<String, Object> paramMap);
	/**
	 * 下单买家数---获取排序后的商品列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductSale_PurchaseNumber_Product(Map<String, Object> paramMap);
	/**
	 * 订单商品件数---获取排序后的商品列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductSale_OrderCount_Product(Map<String, Object> paramMap);
	/**
	 * 支付买家数---获取排序后的商品列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductSale_PayPurchaseNumber_Product(Map<String, Object> paramMap);
	/**
	 * 商品销售总额---获取排序后的商品列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductSale_PayMoney_Product(Map<String, Object> paramMap);
	/**
	 * 支付转化率---获取排序后的商品列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductSale_PayZhl_Product(Map<String, Object> paramMap);
	/**
	 * 查询默认排序的商品列表
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductSaleListBy_Default(Map<String, Object> paramMap);

}
