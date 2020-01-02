package com.tk.analysis.flow.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.analysis.flow.service.FlowAnalysisService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;

/**
 * 
 * Copyright (c), 2019, Tongku
 * FileName : FlowAnalysisControl.java
 * 运营分析-流量 control层 
 *
 * @author yejingquan
 * @version 1.00
 * @date Jun 18, 2019
 */
@Controller
@RequestMapping("/flow_analysis")
public class FlowAnalysisControl {
	@Resource
	private FlowAnalysisService flowAnalysisService;
	
	/**
	 * 流量总览-全站访客数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/overview_visitorCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryOverview_VisitorCount(HttpServletRequest request){
        return Transform.GetResult(flowAnalysisService.queryOverview_VisitorCount(request));
    }
	
	/**
	 * 流量总览-全站浏览量
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/overview_pvCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryOverview_PvCount(HttpServletRequest request){
        return Transform.GetResult(flowAnalysisService.queryOverview_PvCount(request));
    }
	
	/**
	 * 流量总览-商品收藏数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/overview_collectCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryOverview_CollectCount(HttpServletRequest request){
        return Transform.GetResult(flowAnalysisService.queryOverview_CollectCount(request));
    }
	
	/**
	 * 流量总览-折线
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/overview_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryOverviewChart(HttpServletRequest request){
        return Transform.GetResult(flowAnalysisService.queryOverviewChart(request));
    }
	
	/**
	 * 流量来源TOP
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/flow_source_top", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryFlowSourceTop(HttpServletRequest request){
        return Transform.GetResult(flowAnalysisService.queryFlowSourceTop(request));
    }
	
	/**
	 * 流量趋势
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/flow_trend_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryFlowTrendChart(HttpServletRequest request){
        return Transform.GetResult(flowAnalysisService.queryFlowTrendChart(request));
    }
	
	/**
	 * 商品排行
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/flow_product_rank", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductRank(HttpServletRequest request){
        return Transform.GetResult(flowAnalysisService.queryProductRank(request));
    }
	
	/**
	 * 查询流量分布排行
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/flow_distribution_rank", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryFlowDistributionRank(HttpServletRequest request){
        return Transform.GetResult(flowAnalysisService.queryFlowDistributionRank(request));
    }
	
	/**
	 * 查询流量分布图
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/flow_distribution_map", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryFlowDistributionMap(HttpServletRequest request){
        return Transform.GetResult(flowAnalysisService.queryFlowDistributionMap(request));
    }
	
	/**
	 * 查询系统页和自定义页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/flow_page_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryPageList(HttpServletRequest request){
        return Transform.GetResult(flowAnalysisService.queryPageList(request));
    }
	
	/**
	 * 页面列表-趋势分析
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/page_trend_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryPageTrendChart(HttpServletRequest request){
        return Transform.GetResult(flowAnalysisService.queryPageTrendChart(request));
    }
	
	/**
	 * 商品列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/flow_product_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductList(HttpServletRequest request){
        return Transform.GetResult(flowAnalysisService.queryProductList(request));
    }
	
	/**
	 * 商品列表-趋势分析
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/product_trend_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductTrendList(HttpServletRequest request){
        return Transform.GetResult(flowAnalysisService.queryProductTrendList(request));
    }
	
	/**
	 * 访客分析-时段分布折线图
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/flow_time_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryTimeChart(HttpServletRequest request){
        return Transform.GetResult(flowAnalysisService.queryTimeChart(request));
    }
	
	/**
	 * 访客分析-区域分布排行
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/flow_area_rank", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAreaRank(HttpServletRequest request){
        return Transform.GetResult(flowAnalysisService.queryAreaRank(request));
    }
	
	/**
	 * 访客分析-区域分布明细
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/flow_area_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAreaDetail(HttpServletRequest request){
        return Transform.GetResult(flowAnalysisService.queryAreaDetail(request));
    }
	
	/**
	 * 访客分析-区域分布地图
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/flow_area_map", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAreaMap(HttpServletRequest request){
        return Transform.GetResult(flowAnalysisService.queryAreaMap(request));
    }
	
	/**
	 * 流量来源-访问设备
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/facility_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryFacilityChart(HttpServletRequest request){
        return Transform.GetResult(flowAnalysisService.queryFacilityChart(request));
    }
	/**
	 * 流量来源-移动端分布
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/mobile_terminal_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMobileTerminalChart(HttpServletRequest request){
        return Transform.GetResult(flowAnalysisService.queryMobileTerminalChart(request));
    }
	/**
	 * 流量来源-手机品牌折线
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/mobile_brand_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMobileBrandChart(HttpServletRequest request){
        return Transform.GetResult(flowAnalysisService.queryMobileBrandChart(request));
    }
	/**
	 * 流量来源-手机品牌
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/mobile_brand_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMobileBrandList(HttpServletRequest request){
        return Transform.GetResult(flowAnalysisService.queryMobileBrandList(request));
    }
	/**
	 * 流量来源-机型分析
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/mobile_model_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMobileModelList(HttpServletRequest request){
        return Transform.GetResult(flowAnalysisService.queryMobileModelList(request));
    }
	
}
