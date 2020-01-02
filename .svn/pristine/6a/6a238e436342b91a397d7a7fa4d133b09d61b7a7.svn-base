package com.tk.oms.finance.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface FinanceStatisticsDao {
	
	/**
	 * 商家客户资金查询汇总(按商家)数量
	 * @param paramMap
	 * @return
	 */
	public int queryStationedFundSummaryCount(Map<String, Object> paramMap);
	/**
	 * 商家客户资金查询汇总(按商家)列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryStationedFundSummaryList(Map<String, Object> paramMap);
	/**
	 * 商家客户资金查询汇总合计
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryStationedFundSummaryTotal(Map<String, Object> paramMap);
	/**
	 * 商家客户资金查询汇总(按会员)数量
	 * @param paramMap
	 * @return
	 */
	public int queryUserFundSummaryCount(Map<String, Object> paramMap);
	/**
	 * 商家客户资金查询汇总(按商家)列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryUserFundSummaryList(Map<String, Object> paramMap);
	/**
	 * 商家客户资金查询汇总合计
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryUserFundSummaryTotal(Map<String, Object> paramMap);
	/**
	 * 查询支付统计列表数量
	 * @param paramMap
	 * @return
	 */
	public int queryPayStatisticsReportCount(Map<String, Object> paramMap);
	/**
	 * 查询支付统计列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryPayStatisticsReportList(Map<String, Object> paramMap);
	/**
	 * 支付统计报表核销
	 * @param paramMap
	 * @return
	 */
	public int insertOrUpdatePayReportStatisticsAprv(Map<String, Object> paramMap);
	/**
	 * 查询支付统计报表详情列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryPayStatisticsReportDetailList(Map<String, Object> paramMap);
	/**
	 * 查询支付统计报表详情列表数量
	 * @param paramMap
	 * @return
	 */
	public int queryPayStatisticsReportDetailCount(Map<String, Object> paramMap);
	/**
	 * 支付统计报表查询合并支付订单详情
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryPayStatisticsReportMergeDetailList(Map<String, Object> paramMap);
	/**
	 * 查询支付统计报表详情列表数量(现金渠道)
	 * @param paramMap
	 * @return
	 */
	public int queryPayStatisticsReportDetailCashCount(Map<String, Object> paramMap);
	/**
	 * 支付统计报表查询合并支付订单详情(现金渠道)
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryPayStatisticsReportDetailCashList(Map<String, Object> paramMap);
	/**
	 * 查询支付统计报表数据配置列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryPayStatisticsReportDataConfig(Map<String, Object> paramMap);
	/**
	 * 支付统计报表数据配置保存
	 * @param paramMap
	 * @return
	 */
	public int payStatisticsReportSaveDataConfig(Map<String, Object> paramMap);
	/**
	 * 查询每个支付渠道的实收金额
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryPayPlatformActualMoney(Map<String, Object> paramMap);
	/**
	 * 查询支付统计报表充值记录详情列表数量
	 * @param paramMap
	 * @return
	 */
	public int queryPayStatisticsReportRechargeDetailCount(Map<String, Object> paramMap);
	/**
	 * 查询支付统计报表充值记录详情列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryPayStatisticsReportRechargeDetailList(Map<String, Object> paramMap);
	/**
	 * 查询经销商ID
	 * @param paramMap
	 * @return
	 */
	public String queryAllAccountIds();

}
