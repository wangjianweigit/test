package com.tk.oms.analysis.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.analysis.service.OperationAnalysisService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : OperationAnalysisControl
 * 运营分析
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-9-20
 */
@Controller
@RequestMapping("operation_analysis")
public class OperationAnalysisControl {
	@Resource
	private OperationAnalysisService operationAnalysisService;
	
	/**
     * @api {post} /{project_name}/operation_analysis/real_time 实时概况
     * @apiName real_time
     * @apiGroup operation_analysis
     * @apiDescription  实时概况
     * @apiVersion 0.0.1
     * 
     
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 
     * 
     */
    @RequestMapping(value = "/real_time", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRealTime(HttpServletRequest request){
        return Transform.GetResult(operationAnalysisService.queryRealTime(request));
    }
    
    /**
     * @api {post} /{project_name}/operation_analysis/real_time_visitor 实时访客榜
     * @apiName real_time_visitor
     * @apiGroup operation_analysis
     * @apiDescription  实时访客榜
     * @apiVersion 0.0.1
     * 
     
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 
     * 
     */
    @RequestMapping(value = "/real_time_visitor", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRealTimeVisitor(HttpServletRequest request){
        return Transform.GetResult(operationAnalysisService.queryRealTimeVisitor(request));
    }
    
    /**
     * @api {post} /{project_name}/operation_analysis/product_browse 商品浏览排行(移动端、PC端)
     * @apiName product_browse
     * @apiGroup operation_analysis
     * @apiDescription  商品浏览排行(移动端、PC端)
     * @apiVersion 0.0.1
     * 
     
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 
     * 
     */
    @RequestMapping(value = "/product_browse", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductBrowse(HttpServletRequest request){
        return Transform.GetResult(operationAnalysisService.queryProductBrowse(request));
    }
    
    /**
     * @api {post} /{project_name}/operation_analysis/sales_rank 销售排行(商品、地区)
     * @apiName sales_rank
     * @apiGroup operation_analysis
     * @apiDescription  销售排行(商品、地区)
     * @apiVersion 0.0.1
     * 
     
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 
     * 
     */
    @RequestMapping(value = "/sales_rank", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySalesRank(HttpServletRequest request){
        return Transform.GetResult(operationAnalysisService.querySalesRank(request));
    }
    
    /**
     * @api {post} /{project_name}/operation_analysis/unsalable_rank 滞销排行
     * @apiName unsalable_rank
     * @apiGroup operation_analysis
     * @apiDescription  滞销排行
     * @apiVersion 0.0.1
     * 
     
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 
     * 
     */
    @RequestMapping(value = "/unsalable_rank", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryUnsalableRank(HttpServletRequest request){
        return Transform.GetResult(operationAnalysisService.queryUnsalableRank(request));
    }
    
    /**
     * @api {post} /{project_name}/operation_analysis/channel_heat 频道热度(移动端、PC端)
     * @apiName channel_heat
     * @apiGroup operation_analysis
     * @apiDescription  频道热度(移动端、PC端)
     * @apiVersion 0.0.1
     * 
     
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 
     * 
     */
    @RequestMapping(value = "/channel_heat", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryChannelHeat(HttpServletRequest request){
        return Transform.GetResult(operationAnalysisService.queryChannelHeat(request));
    }
    
    /**
     * 品牌统计数据-支付金额
     * @param request
     * @return
     */
    @RequestMapping(value = "/brand_data_allpaymoney", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryBrandData_AllPayMoney(HttpServletRequest request){
        return Transform.GetResult(operationAnalysisService.queryBrandData_AllPayMoney(request));
    }
    
    /**
     * 品牌统计数据-访客数
     * @param request
     * @return
     */
    @RequestMapping(value = "/brand_data_visitorcount", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryBrandData_VisitorCount(HttpServletRequest request){
        return Transform.GetResult(operationAnalysisService.queryBrandData_VisitorCount(request));
    }
    
    /**
     * 品牌统计数据-支付转换率
     * @param request
     * @return
     */
    @RequestMapping(value = "/brand_data_zhl", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryBrandData_Zhl(HttpServletRequest request){
        return Transform.GetResult(operationAnalysisService.queryBrandData_Zhl(request));
    }
    
    /**
     * 品牌统计数据-客单价-总单数
     * @param request
     * @return
     */
    @RequestMapping(value = "/brand_data_kdjordercount", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryBrandData_KdjOrderCount(HttpServletRequest request){
        return Transform.GetResult(operationAnalysisService.queryBrandData_KdjOrderCount(request));
    }
    
    /**
     * 品牌统计数据-成功退款金额
     * @param request
     * @return
     */
    @RequestMapping(value = "/brand_data_returnmoney", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryBrandData_ReturnMoney(HttpServletRequest request){
        return Transform.GetResult(operationAnalysisService.queryBrandData_ReturnMoney(request));
    }
    
    /**
     * 品牌统计数据-总成交商品数
     * @param request
     * @return
     */
    @RequestMapping(value = "/brand_data_salecount", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryBrandData_SaleCount(HttpServletRequest request){
        return Transform.GetResult(operationAnalysisService.queryBrandData_SaleCount(request));
    }
    
    /**
     * @api {post} /{project_name}/operation_analysis/brand_data 品牌数据
     * @apiName brand_data
     * @apiGroup operation_analysis
     * @apiDescription  品牌数据
     * @apiVersion 0.0.1
     * 
     
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 
     * 
     */
    @RequestMapping(value = "/brand_data", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryBrandData(HttpServletRequest request){
        return Transform.GetResult(operationAnalysisService.queryBrandData(request));
    }
    
    /**
     * @api {post} /{project_name}/operation_analysis/brand_data_chart 品牌折线数据
     * @apiName brand_data_chart
     * @apiGroup operation_analysis
     * @apiDescription  品牌折线数据
     * @apiVersion 0.0.1
     * 
     
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 
     * 
     */
    @RequestMapping(value = "/brand_data_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryBrandDataChart(HttpServletRequest request){
        return Transform.GetResult(operationAnalysisService.queryBrandDataChart(request));
    }
    
}
