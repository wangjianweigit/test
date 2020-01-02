package com.tk.analysis.transaction.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.analysis.transaction.service.TransactionAnalysisService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : TransactionAnalysisControl.java
 * 运营分析-交易
 *
 * @author yejingquan
 * @version 1.00
 * @date May 22, 2019
 */
@Controller
@RequestMapping("transaction_analysis")
public class TransactionAnalysisControl {
	@Resource
	private TransactionAnalysisService transactionAnalysisService;
	
	/**
	 * 交易总览
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/transaction_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryTransactionDetail(HttpServletRequest request){
        return Transform.GetResult(transactionAnalysisService.queryTransactionDetail(request));
    }
	
	/**
	 * 交易总览-全站访客数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pandect_visitorCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryPandect_VisitorCount(HttpServletRequest request){
        return Transform.GetResult(transactionAnalysisService.queryPandect_VisitorCount(request));
    }

	/**
	 * 交易总览-商品访客数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pandect_productVisitorCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryPandect_ProductVisitorCount(HttpServletRequest request){
        return Transform.GetResult(transactionAnalysisService.queryPandect_ProductVisitorCount(request));
    }
	
	/**
	 * 交易总览-下单买家数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pandect_purchaseNumber", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryPandect_PurchaseNumber(HttpServletRequest request){
        return Transform.GetResult(transactionAnalysisService.queryPandect_PurchaseNumber(request));
    }
	
	/**
	 * 交易总览-订单商品总额
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pandect_orderMoney", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryPandect_OrderMoney(HttpServletRequest request){
        return Transform.GetResult(transactionAnalysisService.queryPandect_OrderMoney(request));
    }
	
	/**
	 * 交易总览-支付买家数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pandect_payPurchaseNumber", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryPandect_PayPurchaseNumber(HttpServletRequest request){
        return Transform.GetResult(transactionAnalysisService.queryPandect_PayPurchaseNumber(request));
    }

	/**
	 * 交易总览-销售总额
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pandect_payMoney", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryPandect_PayMoney(HttpServletRequest request){
        return Transform.GetResult(transactionAnalysisService.queryPandect_PayMoney(request));
    }

	/**
	 * 交易总览-客单价
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pandect_kdj", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryPandect_Kdj(HttpServletRequest request){
        return Transform.GetResult(transactionAnalysisService.queryPandect_Kdj(request));
    }
	
	/**
	 * 交易趋势-商品访客数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/trend_productVisitorCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryTrend_ProductVisitorCount(HttpServletRequest request){
        return Transform.GetResult(transactionAnalysisService.queryTrend_ProductVisitorCount(request));
    }
	
	/**
	 * 交易趋势-销售总额
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/trend_payMoney", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryTrend_PayMoney(HttpServletRequest request){
        return Transform.GetResult(transactionAnalysisService.queryTrend_PayMoney(request));
    }
	
	/**
	 * 交易趋势-下单买家数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/trend_payPurchaseNumber", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryTrend_PayPurchaseNumber(HttpServletRequest request){
        return Transform.GetResult(transactionAnalysisService.queryTrend_PayPurchaseNumber(request));
    }
	
	/**
	 * 交易趋势-支付转化率
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/trend_payZhl", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryTrend_PayZhl(HttpServletRequest request){
        return Transform.GetResult(transactionAnalysisService.queryTrend_PayZhl(request));
    }
	
	/**
	 * 交易趋势-客单价
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/trend_kdj", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryTrend_Kdj(HttpServletRequest request){
        return Transform.GetResult(transactionAnalysisService.queryTrend_Kdj(request));
    }
	
	/**
	 * 交易趋势-订单笔数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/trend_orderNum", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryTrend_OrderNum(HttpServletRequest request){
        return Transform.GetResult(transactionAnalysisService.queryTrend_OrderNum(request));
    }
	
	/**
	 * 交易趋势-订单商品数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/trend_orderCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryTrend_OrderCount(HttpServletRequest request){
        return Transform.GetResult(transactionAnalysisService.queryTrend_OrderCount(request));
    }
	
	/**
	 * 交易趋势-成功退款金额
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/trend_returnMoney", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryTrend_ReturnMoney(HttpServletRequest request){
        return Transform.GetResult(transactionAnalysisService.queryTrend_ReturnMoney(request));
    }
	
	/**
	 * 交易趋势-下单买家数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/trend_purchaseNumber", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryTrend_PurchaseNumber(HttpServletRequest request){
        return Transform.GetResult(transactionAnalysisService.queryTrend_PurchaseNumber(request));
    }
	
	/**
	 * 交易趋势-下单转化率
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/trend_zhl", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryTrend_Zhl(HttpServletRequest request){
        return Transform.GetResult(transactionAnalysisService.queryTrend_Zhl(request));
    }
	
	/**
	 * 交易趋势-折线
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/trend_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryTrendChart(HttpServletRequest request){
        return Transform.GetResult(transactionAnalysisService.queryTrendChart(request));
    }
	
	/**
	 * 交易构成-终端构成
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/terminal_form", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryTerminalForm(HttpServletRequest request){
        return Transform.GetResult(transactionAnalysisService.queryTerminalForm(request));
    }
	
	/**
	 * 交易构成-商品类目构成
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/product_classify_form", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductClassifyForm(HttpServletRequest request){
        return Transform.GetResult(transactionAnalysisService.queryProductClassifyForm(request));
    }
	
	/**
	 * 交易构成-品牌构成
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/brand_form", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryBrandForm(HttpServletRequest request){
        return Transform.GetResult(transactionAnalysisService.queryBrandForm(request));
    }
	
	/**
	 * 交易构成-数据趋势
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/data_trend", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDataTrend(HttpServletRequest request){
        return Transform.GetResult(transactionAnalysisService.queryDataTrend(request));
    }
	
	/**
	 * 终端下拉框
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/terminal_select", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryTerminalSelect(HttpServletRequest request){
        return Transform.GetResult(transactionAnalysisService.queryTerminalSelect(request));
    }
	
	/**
	 * 品牌下拉框
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/brand_select", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryBrandSelect(HttpServletRequest request){
        return Transform.GetResult(transactionAnalysisService.queryBrandSelect(request));
    }
}
