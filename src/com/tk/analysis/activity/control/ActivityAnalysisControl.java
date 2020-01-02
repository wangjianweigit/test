package com.tk.analysis.activity.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.analysis.activity.service.ActivityAnalysisService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;

/**
 * 
 * Copyright (c), 2019, Tongku
 * FileName : ActivityAnalysisControl.java
 * 
 *
 * @author yejingquan
 * @version 1.00
 * @date Aug 29, 2019
 */
@Controller
@RequestMapping("/activity_analysis")
public class ActivityAnalysisControl {
	@Resource
	private ActivityAnalysisService activityAnalysisService;
	
	/**
	 * 实时概况-实时汇总
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/realTime_summary", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRealTime_Summary(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryRealTime_Summary(request));
    }
	
	/**
	 * 实时概况-实时汇总 折线
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/realTime_summary_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRealTime_SummaryChart(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryRealTime_SummaryChart(request));
    }
	
	/**
	 * 实时概况-今日商品销售额占比
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/realTime_payMoneyRatio", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRealTime_PayMoneyRatio(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryRealTime_PayMoneyRatio(request));
    }

	/**
	 * 实时概况-今日支付单数占比
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/realTime_payNumRatio", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRealTime_PayNumRatio(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryRealTime_PayNumRatio(request));
    }
	
	/**
	 * 实时概况-今日活动列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/realTime_activityList", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRealTime_ActivityList(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryRealTime_ActivityList(request));
    }
	
	/**
	 * 实时概况-今日活动详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/realTime_activityDetail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRealTime_ActivityDetail(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryRealTime_ActivityDetail(request));
    }
	
	/**
	 * 实时概况-今日活动定金情况
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/realTime_activityPreDetail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRealTime_ActivityPreDetail(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryRealTime_ActivityPreDetail(request));
    }
	
	/**
	 * 实时概况-今日活动趋势分析-商品分享次数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/realTime_trend_shareCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRealTime_Trend_ShareCount(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryRealTime_Trend_ShareCount(request));
    }
	
	/**
	 * 实时概况-今日活动趋势分析-商品销售总额
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/realTime_trend_payMoney", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRealTime_Trend_PayMoney(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryRealTime_Trend_PayMoney(request));
    }
	
	/**
	 * 实时概况-今日活动趋势分析-活动支付单笔数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/realTime_trend_payNum", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRealTime_Trend_PayNum(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryRealTime_Trend_PayNum(request));
    }
	
	/**
	 * 实时概况-今日活动趋势分析-分享支付单数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/realTime_trend_sharePayNum", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRealTime_Trend_SharePayNum(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryRealTime_Trend_SharePayNum(request));
    }
	
	/**
	 * 实时概况-今日活动趋势分析-折线图
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/realTime_trendChart", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRealTime_TrendChart(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryRealTime_TrendChart(request));
    }
	
	/**
	 * 实时概况-今日活动商品列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/realTime_productList", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRealTime_ProductList(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryRealTime_ProductList(request));
    }
	
	/**
	 * 活动分析-活动日历
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/activityAnalysis_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryActivityAnalysisList(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryActivityAnalysisList(request));
    }
	
	/**
	 * 活动分析-活动详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/activityAnalysis_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryActivityAnalysisDetail(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryActivityAnalysisDetail(request));
    }
	/**
	 * 活动分析-活动汇总
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/activityAnalysis_summary", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryActivityAnalysisSummary(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryActivityAnalysisSummary(request));
    }
	
	/**
	 * 活动分析-活动成交总额
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/activity_paymentMoney", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryActivityPaymentMoney(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryActivityPaymentMoney(request));
    }
	
	/**
	 * 活动趋势分析-活动商品访客数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/activityTrend_visitorCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryActivityTrend_VisitorCount(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryActivityTrend_VisitorCount(request));
    }
	
	/**
	 * 活动趋势分析-活动销售总额
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/activityTrend_payMoney", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryActivityTrend_PayMoney(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryActivityTrend_PayMoney(request));
    }
	
	/**
	 * 活动趋势分析-活动支付单笔数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/activityTrend_payNum", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryActivityTrend_PayNum(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryActivityTrend_PayNum(request));
    }
	
	/**
	 * 活动趋势分析-活动支付买家数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/activityTrend_purchaseNumber", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryActivityTrend_PurchaseNumber(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryActivityTrend_PurchaseNumber(request));
    }
	
	/**
	 * 活动趋势分析-活动支付件数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/activityTrend_payCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryActivityTrend_PayCount(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryActivityTrend_PayCount(request));
    }
	
	/**
	 * 活动趋势分析-活动商品分享次数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/activityTrend_shareCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryActivityTrend_ShareCount(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryActivityTrend_ShareCount(request));
    }
	
	/**
	 * 活动趋势分析-分享支付单数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/activityTrend_sharePayNum", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryActivityTrend_SharePayNum(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryActivityTrend_SharePayNum(request));
    }
	
	/**
	 * 活动趋势分析-折线图
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/activityTrend_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryActivityTrendChart(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryActivityTrendChart(request));
    }
	
	/**
	 * 活动商品销售额占比
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/activity_payMoneyRatio", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryActivityPayMoneyRatio(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryActivityPayMoneyRatio(request));
    }

	/**
	 * 活动支付单数占比
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/activity_payNumRatio", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryActivityPayNunRatio(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryActivityPayNunRatio(request));
    }
	
	/**
	 * 活动商品列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/activity_productList", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryActivityProductList(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryActivityProductList(request));
    }
	
	/**
	 * 活动品牌列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/activity_brandList", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryActivityBrandList(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryActivityBrandList(request));
    }
	
	/**
	 * 活动入驻商列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/activity_stationedList", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryActivityStationedList(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryActivityStationedList(request));
    }
	
	/**
	 * 活动会员列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/activity_userList", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryActivityUserList(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryActivityUserList(request));
    }
	
	/**
	 * 活动商品阶梯价
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/activity_productSpecpize", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryActivityProductSpecpize(HttpServletRequest request){
        return Transform.GetResult(activityAnalysisService.queryActivityProductSpecpize(request));
    }
	/**
	 * 活动站点
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/select_site", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryActivitySite(HttpServletRequest request) {
		return Transform.GetResult(activityAnalysisService.queryActivitySite(request));
	}
	
	/**
	 * 用户站点
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/user_site", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryUserSite(HttpServletRequest request) {
		return Transform.GetResult(activityAnalysisService.queryUserSite(request));
	}
}
