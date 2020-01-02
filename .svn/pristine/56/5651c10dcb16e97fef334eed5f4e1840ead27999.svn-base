package com.tk.analysis.home.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tk.oms.basicinfo.dao.BaseDao;

@Repository
public interface HomeAnalysisDao extends BaseDao<Map<String, Object>> {
	/**
	 * 根据用户ID查询用户权限信息
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryByUserId(Map<String, Object> paramMap);
	
	/**
	 * 根据用户ID查询用户品类权限信息
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryByProductTypeId(Map<String, Object> paramMap);
	/**
	 * 实时基本概况 支付买家数、销售件数、支付单笔数、销售总额
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> r_queryRealTimeDetail_PurchaseNumberCountMoney(Map<String, Object> paramMap);
	/**
	 * 实时基本概况 日活跃用户数
	 * @param paramMap
	 * @return
	 */
	float r_queryRealTimeDetail_LivelyCount(Map<String, Object> paramMap);
	/**
	 * 实时基本概况 访客数
	 * @param paramMap
	 * @return
	 */
	float r_queryRealTimeDetail_VisitorCount(Map<String, Object> paramMap);
	/**
	 * 实时基本概况 预订订单的定金
	 * @param paramMap
	 * @return
	 */
	float r_queryRealTimeDetail_PreFirstMoney(Map<String, Object> paramMap);
	/**
	 * 实时基本概况 无线支付金额
	 * @param paramMap
	 * @return
	 */
	float r_queryRealTimeDetail_MobileMoney(Map<String, Object> paramMap);
	/**
	 * 实时基本概况 销售总额 折线
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryRealTimeDetail_PayMoney_Chart(Map<String, Object> paramMap);
	/**
	 * 实时基本概况 预定订单的定金 折线
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryRealTimeDetail_PreFirstMoney_Chart(Map<String, Object> paramMap);
	/**
	 * 查询会员成交TOP
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMemberPayTopList(Map<String, Object> paramMap);
	/**
	 * 查询会员退款TOP
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMemberReturnTopList(Map<String, Object> paramMap);
	/**
	 * 实时流量看板-浏览量
	 * @param paramMap
	 * @return
	 */
	float r_queryRealTimeFlow_PvCount(Map<String, Object> paramMap);
	/**
	 * 实时流量看板-浏览量 折线
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryRealTimeFlow_PvCount_Chart(Map<String, Object> paramMap);
	/**
	 * 下单买家总数
	 * @param paramMap
	 * @return
	 */
	long r_queryPurchaseNumberTotal(Map<String, Object> paramMap);
	/**
	 * 按省份 下单买家数 排行榜
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryPurchaseNumberProvince_Rank(Map<String, Object> paramMap);
	/**
	 * 按城市 下单买家数 排行榜
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryPurchaseNumberCity_Rank(Map<String, Object> paramMap);
	/**
	 * 成交总额
	 * @param paramMap
	 * @return
	 */
	float r_queryPayMoneyTotal(Map<String, Object> paramMap);
	/**
	 * 按省份 成交总额 排行榜
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryPayMoneyProvince_Rank(Map<String, Object> paramMap);
	/**
	 * 按城市 成交总额 排行榜
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryPayMoneyCity_Rank(Map<String, Object> paramMap);
	/**
	 * 区域销售分析-地图
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_querySaleAreaMap(Map<String, Object> paramMap);
	/**
	 * 区域销售分析-会员总数
	 * @param paramMap
	 * @return
	 */
	float r_queryMemberTotal(Map<String, Object> paramMap);
	/**
	 * 区域销售分析-新增会员数
	 * @param paramMap
	 * @return
	 */
	float r_queryMemberAddCount(Map<String, Object> paramMap);
	/**
	 * 区域销售分析-活跃用户数
	 * @param paramMap
	 * @return
	 */
	float r_queryLivelyCount(Map<String, Object> paramMap);
	/**
	 * 区域销售分析-下单笔数
	 * @param paramMap
	 * @return
	 */
	float r_queryOrderNum(Map<String, Object> paramMap);
	/**
	 * 区域销售分析-支付笔数
	 * @param paramMap
	 * @return
	 */
	float r_queryPayNum(Map<String, Object> paramMap);
	/**
	 * 区域销售分析-支付件数
	 * @param paramMap
	 * @return
	 */
	float r_queryPayCount(Map<String, Object> paramMap);
	/**
	 * 区域销售分析-成交额
	 * @param paramMap
	 * @return
	 */
	float r_queryPayMoney(Map<String, Object> paramMap);
	/**
	 * 区域销售分析-支付买家数
	 * @param paramMap
	 * @return
	 */
	float r_queryPayPurchaseNumber(Map<String, Object> paramMap);
	/**
	 * 区域销售分析-成功退款金额
	 * @param paramMap
	 * @return
	 */
	float r_queryReturnMoney(Map<String, Object> paramMap);
	/**
	 * 折线图 新增会员数<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMemberAddCountD_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 活跃用户数<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryLivelyCountD_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 下单笔数<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOrderNumD_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 支付单笔数<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryPayNumD_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 支付买家数、支付件数、成交额<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryPayPurchaseNumberCountMoneyD_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 预定订单的定金<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryPreFirstMoneyD_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 成功退款金额<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryReturnMoneyD_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 新增会员数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMemberAddCount_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 活跃用户数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryLivelyCount_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 下单笔数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryOrderNum_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 支付单笔数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryPayNum_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 支付买家数、支付件数、成交额
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryPayPurchaseNumberCountMoney_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 预定订单的定金
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryPreFirstMoney_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 成功退款金额
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryReturnMoney_Chart(Map<String, Object> paramMap);
	/**
	 * 商品访客数---获取排序后的商品排行
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductRank_ProductVisitorCount(Map<String, Object> paramMap);
	/**
	 * 商品浏览量---获取排序后的商品排行
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductRank_ProductPvCount(Map<String, Object> paramMap);
	/**
	 * 商品收藏数---获取排序后的商品排行
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductRank_CollectCount(Map<String, Object> paramMap);
	/**
	 * 下单件数---获取排序后的商品排行
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductRank_OrderCount(Map<String, Object> paramMap);
	/**
	 * 支付件数---获取排序后的商品排行
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductRank_PayCount(Map<String, Object> paramMap);
	/**
	 * 退款件数---获取排序后的商品排行
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductRank_ReturnCount(Map<String, Object> paramMap);
	/**
	 * 商品销售总额---获取排序后的商品排行
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductRank_PayMoney(Map<String, Object> paramMap);
	/**
	 * 退款商品金额---获取排序后的商品排行
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductRank_ReturnMoney(Map<String, Object> paramMap);
	/**
	 * 查询默认排序的商品信息(默认按商品销售总额排序)
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryProductRankListBy_Default(Map<String, Object> paramMap);
	/**
	 * 商品列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryProductList(Map<String, Object> paramMap);
	/**
	 * 商品排行-商品访客数,商品浏览量
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryProductVisitorPvCount(Map<String, Object> paramMap);
	/**
	 * 商品排行-商品收藏数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryProductCollectCount(Map<String, Object> paramMap);
	/**
	 * 商品排行-下单件数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryProductOrderCount(Map<String, Object> paramMap);
	/**
	 * 商品排行-支付件数、商品销售总额
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryProductPayInfo(Map<String, Object> paramMap);
	/**
	 * 商品排行-预定订单的定金
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryProductPreFirstMoney(Map<String, Object> paramMap);
	/**
	 * 商品排行-退款件数、退款商品金额
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryProductReturnInfo(Map<String, Object> paramMap);
	/**
	 * 商品浏览量---获取排序后的品牌排行
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryBrandRank_ProductPvCount(Map<String, Object> paramMap);
	/**
	 * 商品销售总额---获取排序后的品牌排行
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryBrandRank_PayMoney(Map<String, Object> paramMap);
	/**
	 * 退款商品金额---获取排序后的商品排行
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryBrandRank_ReturnMoney(Map<String, Object> paramMap);
	/**
	 * 查询默认排序的品牌信息(默认按商品销售总额排序)
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryBrandRankListBy_Default(Map<String, Object> paramMap);
	/**
	 * 品牌列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryBrandList(Map<String, Object> paramMap);
	/**
	 * 品牌排行-商品浏览量
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryBrandPvCount(Map<String, Object> paramMap);
	/**
	 * 品牌排行-商品销售总额
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryBrandPayMoney(Map<String, Object> paramMap);
	/**
	 * 品牌排行-预定订单的定金
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryBrandPreFirstMoney(Map<String, Object> paramMap);
	/**
	 * 品牌排行-退款商品金额
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryBrandReturnMoney(Map<String, Object> paramMap);
	/**
	 * 商品销量---获取排序后的入驻商排行
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryStationedRank_ProductPvCount(Map<String, Object> paramMap);
	/**
	 * 商品销售总额---获取排序后的入驻商排行
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryStationedRank_PayMoney(Map<String, Object> paramMap);
	/**
	 * 未发退货数---获取排序后的入驻商排行
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryStationedRank_UnsentReturnCount(Map<String, Object> paramMap);
	/**
	 * 已发退货数---获取排序后的入驻商排行
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryStationedRank_SentReturnCount(Map<String, Object> paramMap);
	/**
	 * 查询默认排序的入驻商信息(默认按商品销售总额排序)
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryStationedRankListBy_Default(Map<String, Object> paramMap);
	/**
	 * 入驻商列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryStationedList(Map<String, Object> paramMap);
	/**
	 * 入驻商排行-商品销量、商品销售总额
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryStationedPayInfo(Map<String, Object> paramMap);
	/**
	 * 入驻商排行-预定订单的定金
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryStationedPreFirstMoney(Map<String, Object> paramMap);
	/**
	 * 入驻商排行-未发退货数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryStationedUnsentReturnCount(Map<String, Object> paramMap);
	/**
	 * 入驻商排行-已发退货数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryStationedSentReturnCount(Map<String, Object> paramMap);
}
