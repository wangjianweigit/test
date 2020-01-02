package com.tk.analysis.retail.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.analysis.retail.service.RetailAnalysisService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;

@Controller
@RequestMapping("/retail_analysis")
public class RetailAnalysisControl {
	@Resource
	private RetailAnalysisService retailAnalysisService;
	
	/**
	 * 经销商概况-基本汇总
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agent_basic_summary", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentBasicSummary(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentBasicSummary(request));
    }
	/**
	 * 经销商概况-实时汇总
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agent_realTime_summary", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentRealTimeSummary(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentRealTimeSummary(request));
    }
	
	/**
	 * 经销商概况-实时汇总-线上订单量占比
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agent_realTime_orderRatio", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentRealTimeOrderRatio(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentRealTimeOrderRatio(request));
    }
	
	/**
	 * 经销商概况-实时汇总-线上交易额占比
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agent_realTime_tradeRatio", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentRealTimeTradeRatio(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentRealTimeTradeRatio(request));
    }
	
	/**
	 * 经销商概况-实时汇总-自营交易额占比
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agent_realTime_zyRatio", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentRealTimeZyRatio(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentRealTimeZyRatio(request));
    }
	
	/**
	 * 经销商概况-经销商排行
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agent_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentList(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentList(request));
    }
	
	/**
	 * 经销商分析
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agent_search", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentSearch(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentSearch(request));
    }
	
	/**
	 * 经销商分析详情-基本信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agentDetail_basic", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentDetailBasic(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentDetailBasic(request));
    }
	
	/**
	 * 经销商详情-实时汇总
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agentDetail_realTime_summary", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentDetailRealTimeSummary(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentDetailRealTimeSummary(request));
    }
	
	/**
	 * 经销商详情-实时汇总-线上订单量占比
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agentDetail_realTime_orderRatio", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentDetailRealTimeOrderRatio(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentDetailRealTimeOrderRatio(request));
    }
	
	/**
	 * 经销商详情-实时汇总-线上交易额占比
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agentDetail_realTime_tradeRatio", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentDetailRealTimeTradeRatio(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentDetailRealTimeTradeRatio(request));
    }
	
	/**
	 * 经销商详情-实时汇总-新老客户占比
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agentDetail_realTime_customerRatio", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentDetailRealTimeCustomerRatio(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentDetailRealTimeCustomerRatio(request));
    }
	
	/**
	 * 经销商详情-客户分析-基本汇总
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agentDetail_customer_summary", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentDetailCustomerSummary(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentDetailCustomerSummary(request));
    }
	
	/**
	 * 经销商详情-客户分析-来源渠道
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agentDetail_customer_source", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentDetailCustomerSource(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentDetailCustomerSource(request));
    }
	
	/**
	 * 经销商详情-客户分析-客户列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agentDetail_customer_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentDetailCustomerList(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentDetailCustomerList(request));
    }
	
	/**
	 * 经销商详情-销售分析-下单笔数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agentDetail_sale_orderNum", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentDetailSaleOrderNum(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentDetailSaleOrderNum(request));
    }
	
	/**
	 * 经销商详情-销售分析-支付笔数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agentDetail_sale_payNum", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentDetailSalePayNum(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentDetailSalePayNum(request));
    }
	
	/**
	 * 经销商详情-销售分析-成交额
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agentDetail_sale_payMoney", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentDetailSalePayMoney(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentDetailSalePayMoney(request));
    }
	
	/**
	 * 经销商详情-销售分析-商品销售总额
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agentDetail_sale_productMoney", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentDetailSaleProductMoney(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentDetailSaleProductMoney(request));
    }
	
	/**
	 * 经销商详情-销售分析-商品销量
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agentDetail_sale_payCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentDetailSalePayCount(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentDetailSalePayCount(request));
    }
	
	/**
	 * 经销商详情-销售分析-退款金额
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agentDetail_sale_returnMoney", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentDetailSaleReturnMoney(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentDetailSaleReturnMoney(request));
    }
	
	/**
	 * 经销商详情-销售分析-未发退货数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agentDetail_sale_unsentReturnCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentDetailSaleUnsentReturnCount(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentDetailSaleUnsentReturnCount(request));
    }
	
	/**
	 * 经销商详情-销售分析-已发退货数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agentDetail_sale_sentReturnCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentDetailSaleSentReturnCount(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentDetailSaleSentReturnCount(request));
    }
	
	/**
	 * 经销商详情-销售分析-支付买家数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agentDetail_sale_purchaseNumber", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentDetailSalePurchaseNumber(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentDetailSalePurchaseNumber(request));
    }
	
	/**
	 * 经销商详情-销售分析-客单价
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agentDetail_sale_kdj", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentDetailSaleKdj(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentDetailSaleKdj(request));
    }
	
	/**
	 * 经销商详情-销售分析-访客数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agentDetail_sale_visitorCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentDetailSaleVisitorCount(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentDetailSaleVisitorCount(request));
    }
	
	/**
	 * 经销商详情-销售分析-折线
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agentDetail_sale_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentDetailSaleChart(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentDetailSaleChart(request));
    }
	
	/**
	 * 经销商详情-销售分析-商品列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agentDetail_sale_productList", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentDetailSaleProductList(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentDetailSaleProductList(request));
    }
	
	/**
	 * 经销商详情-退款原因分析-商品退货数占比
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agentDetail_afterSale_returnCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentDetailAfterSaleReturnCount(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentDetailAfterSaleReturnCount(request));
    }
	
	/**
	 * 经销商详情-退款原因分析-商品退货原因
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agentDetail_afterSale_returnReason", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentDetailAfterSaleReturnReason(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentDetailAfterSaleReturnReason(request));
    }
	
	/**
	 * 经销商详情-活动分析-活动折线图
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agentDetail_activity_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentDetailActivityChart(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentDetailActivityChart(request));
    }
	
	/**
	 * 经销商详情-活动分析-活动列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agentDetail_activity_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentDetailActivityList(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryAgentDetailActivityList(request));
    }
	
	/**
	 * 所属门店【下拉框】
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agent_store_option", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryStoreOption(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryStoreOption(request));
    }
	
	/**
	 * 业务员【下拉框】
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agent_ywy_option", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryYwyOption(HttpServletRequest request){
        return Transform.GetResult(retailAnalysisService.queryYwyOption(request));
    }
}
