package com.tk.oms.finance.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.finance.service.FinanceStatisticsService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * 
 * Copyright (c), 2019, Tongku
 * FileName : FinanceStatisticsControl
 * 财务记账相关报表
 *
 * @author liujialong
 * @version 1.00
 * @date 2019-4-18
 */
@Controller
@RequestMapping("finance_statistics")
public class FinanceStatisticsControl {
	
	@Resource
	private FinanceStatisticsService financeStatisticsService;
	
	/**
	 * 查询支付统计报表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pay_statistics_report_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryPayStatisticsReportList(HttpServletRequest request) {
        return Transform.GetResult(financeStatisticsService.queryPayStatisticsReportList(request));
    }
	
	/**
	 * 查询支付统计报表数据配置
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/query_data_config", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryPayStatisticsReportDataConfig(HttpServletRequest request) {
        return Transform.GetResult(financeStatisticsService.queryPayStatisticsReportDataConfig(request));
    }
	
	/**
	 * 查询支付统计详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pay_statistics_report_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryPayStatisticsReportDetail(HttpServletRequest request) {
        return Transform.GetResult(financeStatisticsService.queryPayStatisticsReportDetail(request));
    }
	
	/**
	 * 支付统计报表详情(新零售交易充值详情老)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pay_statistics_report_detail_retail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryPayStatisticsReportDetailRetail(HttpServletRequest request) {
        return Transform.GetResult(financeStatisticsService.queryPayStatisticsReportDetailRetail(request));
    }
	
	/**
	 * 支付统计报表详情(新零售交易充值详情新)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pay_statistics_report_detail_retail_new", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryPayStatisticsReportRechargeDetailNew(HttpServletRequest request) {
        return Transform.GetResult(financeStatisticsService.queryPayStatisticsReportRechargeDetailNew(request));
    }
	
	/**
	 * 支付统计报表查询合并支付订单详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pay_statistics_report_merge_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryPayStatisticsReportMergeDetail(HttpServletRequest request) {
        return Transform.GetResult(financeStatisticsService.queryPayStatisticsReportMergeDetail(request));
    }
	
	/**
	 * 支付统计报表充值详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pay_statistics_report_recharge_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryPayStatisticsReportRechargeDetail(HttpServletRequest request) {
        return Transform.GetResult(financeStatisticsService.queryPayStatisticsReportRechargeDetail(request));
    }
	
	/**
	 * 支付统计报表核销
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pay_statistics_report_deal", method = RequestMethod.POST)
    @ResponseBody
    public Packet payStatisticsReportDeal(HttpServletRequest request) {
		ProcessResult pr = new	ProcessResult();
		try{
			pr = financeStatisticsService.payStatisticsReportDeal(request);
		}catch(Exception ex){
			pr.setState(false);
			pr.setMessage(ex.getMessage());
		}
        return Transform.GetResult(pr);
    }
	
	/**
	 * 商家客户资金查询汇总(按商家)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/stationed_fund_summary", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryStationedFundSummaryList(HttpServletRequest request) {
        return Transform.GetResult(financeStatisticsService.queryStationedFundSummaryList(request));
    }
	
	/**
	 * 商家客户资金查询汇总(按会员)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/user_fund_summary", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryUserFundSummaryList(HttpServletRequest request) {
        return Transform.GetResult(financeStatisticsService.queryUserFundSummaryList(request));
    }
	
	/**
	 * 支付统计报表数据配置保存
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/save_data_config", method = RequestMethod.POST)
    @ResponseBody
    public Packet payStatisticsReportSaveDataConfig(HttpServletRequest request) {
		ProcessResult pr = new	ProcessResult();
		try{
			pr = financeStatisticsService.payStatisticsReportSaveDataConfig(request);
		}catch(Exception ex){
			pr.setState(false);
			pr.setMessage(ex.getMessage());
		}
        return Transform.GetResult(pr);
    }

}
