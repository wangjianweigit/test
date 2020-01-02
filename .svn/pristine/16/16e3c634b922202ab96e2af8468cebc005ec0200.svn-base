package com.tk.oms.analysis.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface OperationAnalysisDao {
	
	/**
	 * 实时概况：支付买家数、支付订单数
	 * @param paramMap
	 * @return  支付买家数、支付订单数
	 */
	public Map<String, Object> queryRealTimeDetail_custom_order_Count(Map<String, Object> paramMap);
	
	/**
	 * 实时概况：访客数,浏览量
	 * @param paramMap
	 * @return 访客数,浏览量
	 */
	public Map<String, Object> queryRealTimeDetail_visitor_pv_Count(Map<String, Object> paramMap);
	
	/**
	 * 实时概况明细(今天) 废弃
	 * @param paramMap 
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryRealTimeDetailTD(Map<String, Object> paramMap);
	/**
	 * 实时概况明细(昨天) 废弃
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryRealTimeDetailYD(Map<String, Object> paramMap);
	/**
	 * 查询是实时概况折线(今天)
	 * @param paramMap 
	 * @return
	 */
	public List<Map<String, Object>> queryRealTimeLineTD(Map<String, Object> paramMap);
	/**
	 * 查询是实时概况折线(昨天)
	 * @param paramMap 
	 * @return
	 */
	public List<Map<String, Object>> queryRealTimeLineYD(Map<String, Object> paramMap);
	/**
	 * 实时访客榜
	 * @param paramMap 
	 * @return
	 */
	public List<Map<String, Object>> queryRealTimeVisitor(Map<String, Object> paramMap);
	/**
	 * 商品浏览排行(移动端、PC端)
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryProductBrowse(Map<String, Object> paramMap);
	/**
	 * 商品销售排行
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryProductRank(Map<String, Object> paramMap);
	/**
	 * 区域销售排行
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryAreaRank(Map<String, Object> paramMap);
	/**
	 * 滞销排行
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryUnsalableRank(Map<String, Object> paramMap);
	/**
	 * 频道热度(移动端、PC端)浏览量
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryChannelHeatPv(Map<String, Object> paramMap);
	/**
	 * 频道热度(移动端、PC端)访客数
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryChannelHeatVisitor(Map<String, Object> paramMap);
	/**
	 * 频道热度(移动端、PC端)浏览量(今天)
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryChannelHeatPvTd(Map<String, Object> paramMap);
	/**
	 * 频道热度(移动端、PC端)访客数(今天)
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryChannelHeatVisitorTd(Map<String, Object> paramMap);
	/**
	 * 品牌数据  废弃
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryBrandData(Map<String, Object> paramMap);
	/**
	 * 品牌折线数据  废弃
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryBrandDataChart(Map<String, Object> paramMap);
	/**
	 * 查询区域销售商品排行
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryAreaProductRank(Map<String, Object> paramMap);
	/**
	 * 查询页面名称
	 * @param page_id
	 * @return
	 */
	public String queryPageNameByPageId(String page_id);

	/** 品牌统计数据-所有支付金额 */
  	public float queryBrandData_AllPayMoney(Map<String, Object> paramMap);
  	
  	/** 品牌统计数据-预订支付的首款 */
  	public float queryBrandData_PreFirstMoney(Map<String, Object> paramMap);
  	
  	/** 品牌统计数据-尾款订单的定金金额 */
  	public float queryBrandData_PreOrderFirstMoney(Map<String, Object> paramMap);
  	
  	/** 品牌统计数据-商品访客人数 */
  	public float queryBrandData_VisitorCount(Map<String, Object> paramMap);
  	
  	/** 品牌统计数据-购买的人数和总单数 */
  	public Map<String, Object> queryBrandData_PurchaseNumberOrderCount(Map<String, Object> paramMap);
  	
  	/** 品牌统计数据-仅退款金额 */
  	public float queryBrandData_OnlyReturnMoney(Map<String, Object> paramMap);
  	
  	/** 品牌统计数据-售后退款*/
  	public float queryBrandData_AfterSaleReturnMoney(Map<String, Object> paramMap);
  	
  	/** 品牌统计数据-总成交商品数*/
  	public float queryBrandData_SaleCount(Map<String, Object> paramMap);
  	
  	
  	/** 品牌折线图统计数据-所有支付金额 */
  	public List<Map<String, Object>> queryBrandData_AllPayMoney_Chart(Map<String, Object> paramMap);
  	
  	/** 品牌折线图统计数据-预订支付的首款 */
  	public List<Map<String, Object>> queryBrandData_PreFirstMoney_Chart(Map<String, Object> paramMap);
  	
  	/** 品牌折线图统计数据-尾款订单的定金金额 */
  	public List<Map<String, Object>> queryBrandData_PreOrderFirstMoney_Chart(Map<String, Object> paramMap);
  	
  	/** 品牌折线图统计数据-商品访客人数 */
  	public List<Map<String, Object>> queryBrandData_VisitorCount_Chart(Map<String, Object> paramMap);
  	
  	/** 品牌折线图统计数据-购买的人数和总单数 */
  	public List<Map<String, Object>> queryBrandData_PurchaseNumberOrderCount_Chart(Map<String, Object> paramMap);
  	
  	/** 品牌折线图统计数据-仅退款金额 */
  	public List<Map<String, Object>> queryBrandData_OnlyReturnMoney_Chart(Map<String, Object> paramMap);
  	
  	/** 品牌折线图统计数据-售后退款*/
  	public List<Map<String, Object>> queryBrandData_AfterSaleReturnMoney_Chart(Map<String, Object> paramMap);
  	
  	/** 品牌折线图统计数据-总成交商品数*/
  	public List<Map<String, Object>> queryBrandData_SaleCount_Chart(Map<String, Object> paramMap);
  	
  	
  	/** 实时概况：折线图统计数据-所有支付金额 */
  	public List<Map<String, Object>> queryRealTimeDetail_AllPayMoney_Chart(Map<String, Object> paramMap);
  	
  	/** 实时概况：折线图统计数据-预订支付的首款 */
  	public List<Map<String, Object>> queryRealTimeDetail_PreFirstMoney_Chart(Map<String, Object> paramMap);
  	
  	/** 实时概况：折线图统计数据-尾款订单的定金金额 */
  	public List<Map<String, Object>> queryRealTimeDetail_PreOrderFirstMoney_Chart(Map<String, Object> paramMap);
}
