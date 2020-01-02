package com.tk.analysis.home.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.analysis.home.service.HomeAnalysisService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2019, Tongku
 * FileName : HomeAnalysisControl.java
 * 运营分析-首页
 *
 * @author yejingquan
 * @version 1.00
 * @date Aug 5, 2019
 */
@Controller
@RequestMapping("/home_analysis")
public class HomeAnalysisControl {
	@Resource
	private HomeAnalysisService homeAnalysisService;
	
	/**
	 * 使用OA的openId直接获取用户平台权限
	 */
	@RequestMapping(value = "/get_user_platform", method = RequestMethod.POST)
	@ResponseBody
	public Packet getUserPlatformList(HttpServletRequest request) {
		return Transform.GetResult(homeAnalysisService.getUserPlatformList(request));
	}
	
	/**
	 * 实时基本概况
	 * @param request
	 * @return
	 */
    @RequestMapping(value = "/realTime_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRealTimeDetail(HttpServletRequest request){
        return Transform.GetResult(homeAnalysisService.queryRealTimeDetail(request));
    }
    
    /**
	 * 实时会员成交top
	 * @param request
	 * @return
	 */
    @RequestMapping(value = "/realTime_memberPayTop", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRealTimeMemberPayTop(HttpServletRequest request){
        return Transform.GetResult(homeAnalysisService.queryRealTimeMemberPayTop(request));
    }
    
    /**
	 * 实时会员退款top
	 * @param request
	 * @return
	 */
    @RequestMapping(value = "/realTime_memberReturnTop", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRealTimeMemberReturnTop(HttpServletRequest request){
        return Transform.GetResult(homeAnalysisService.queryRealTimeMemberReturnTop(request));
    }
    
    /**
	 * 实时流量看板-浏览量
	 * @param request
	 * @return
	 */
    @RequestMapping(value = "/realTime_flowPvCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRealTimeFlowPvCount(HttpServletRequest request){
        return Transform.GetResult(homeAnalysisService.queryRealTimeFlowPvCount(request));
    }
    
    /**
	 * 实时流量看板-浏览量 折线
	 * @param request
	 * @return
	 */
    @RequestMapping(value = "/realTime_flowPvCount_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRealTimeFlowPvCountChart(HttpServletRequest request){
        return Transform.GetResult(homeAnalysisService.queryRealTimeFlowPvCountChart(request));
    }
    
    
    /**
	 * 区域销售分析-销售区域排行
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saleArea_rank", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySaleAreaRank(HttpServletRequest request){
        return Transform.GetResult(homeAnalysisService.querySaleAreaRank(request));
    }
	
	/**
	 * 区域销售分析-地图
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saleArea_map", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySaleAreaMap(HttpServletRequest request){
        return Transform.GetResult(homeAnalysisService.querySaleAreaMap(request));
    }
	
	/**
	 * 区域销售分析-会员总数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saleArea_member_total", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySaleAreaMemberTotal(HttpServletRequest request){
        return Transform.GetResult(homeAnalysisService.querySaleAreaMemberTotal(request));
    }
	
	/**
	 * 区域销售分析-新增会员数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saleArea_member_addCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySaleAreaMemberAddCount(HttpServletRequest request){
        return Transform.GetResult(homeAnalysisService.querySaleAreaMemberAddCount(request));
    }
	
	/**
	 * 区域销售分析-活跃用户数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saleArea_livelyCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySaleAreaLivelyCount(HttpServletRequest request){
        return Transform.GetResult(homeAnalysisService.querySaleAreaLivelyCount(request));
    }
	
	/**
	 * 区域销售分析-下单笔数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saleArea_orderNum", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySaleAreaOrderNum(HttpServletRequest request){
        return Transform.GetResult(homeAnalysisService.querySaleAreaOrderNum(request));
    }
	
	/**
	 * 区域销售分析-支付笔数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saleArea_payNum", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySaleAreaPayNum(HttpServletRequest request){
        return Transform.GetResult(homeAnalysisService.querySaleAreaPayNum(request));
    }
	
	/**
	 * 区域销售分析-支付件数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saleArea_payCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySaleAreaPayCount(HttpServletRequest request){
        return Transform.GetResult(homeAnalysisService.querySaleAreaPayCount(request));
    }
	
	/**
	 * 区域销售分析-成交额
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saleArea_payMoney", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySaleAreaPayMoney(HttpServletRequest request){
        return Transform.GetResult(homeAnalysisService.querySaleAreaPayMoney(request));
    }
	
	/**
	 * 区域销售分析-支付买家数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saleArea_payPurchaseNumber", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySaleAreaPayPurchaseNumber(HttpServletRequest request){
        return Transform.GetResult(homeAnalysisService.querySaleAreaPayPurchaseNumber(request));
    }
	
	/**
	 * 区域销售分析-成功退款金额
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saleArea_returnMoney", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySaleAreaReturnMoney(HttpServletRequest request){
        return Transform.GetResult(homeAnalysisService.querySaleAreaReturnMoney(request));
    }
	
	/**
	 * 区域销售分析-趋势图
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saleArea_trend_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySaleAreaTrendChart(HttpServletRequest request){
        return Transform.GetResult(homeAnalysisService.querySaleAreaTrendChart(request));
    }
	
	/**
	 * 商品排行
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/homePage_product_rank", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductRank(HttpServletRequest request){
        return Transform.GetResult(homeAnalysisService.queryProductRank(request));
    }
	
	/**
	 * 品牌排行
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/homePage_brand_rank", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryBrandRank(HttpServletRequest request){
        return Transform.GetResult(homeAnalysisService.queryBrandRank(request));
    }
	
	/**
	 * 入驻商排行
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/homePage_stationed_rank", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryStationedRank(HttpServletRequest request){
        return Transform.GetResult(homeAnalysisService.queryStationedRank(request));
    }
    
}
