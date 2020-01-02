package com.tk.oms.analysis.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * Copyright (c), 2018, Tongku
 * FileName : SaleTargetDao
 * 业务员、会员销售指标接口
 *
 * @author liujialong
 * @version 1.00
 * @date 2018/8/28
 */
@Repository
public interface SaleTargetDao {
	
	/**
     * 获取业务员销售指标列表数据
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryYwySaleTargetList(Map<String,Object> params);
    /**
     * 获取业务员销售指标总数
     * @param params
     * @return
     */
    public int queryYwySaleTargetCount(Map<String,Object> params);
    /**
     * 获取会员销售指标列表数据
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryHySaleTargetList(Map<String,Object> params);
    /**
     * 获取业务员销售指标总数
     * @param params
     * @return
     */
    public int queryHySaleTargetCount(Map<String,Object> params);
    /**
     * 获取会员销售指标总数
     * @param params
     * @return
     */
    public Map<String,Object> queryHyTotalAnnualTarget(Map<String,Object> params);
    /**
     * 获取会员销售指标信息
     * @param params
     * @return
     */
    public Map<String,Object> querySaleTargetsHyInfo(Map<String,Object> params);
    /**
     * 获取业务员列表数据
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryUserTypeList(Map<String,Object> params);
    /**
     * 获取业务员列表总数
     * @param params
     * @return
     */
    public int queryUserTypeCount(Map<String,Object> params);
    /**
     * 新增业务员销售指标主表信息
     * @param params
     * @return
     */
    public int insertSaleTargetYwy(Map<String,Object> params);
    /**
     * 新增业务员销售指标详表信息
     * @param params
     * @return
     */
    public int insertSaleTargetYwyDetail(List<Map<String,Object>> list);
    /**
     * 业务员销售指标详情(支持单个和批量查看)
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryYwySaleTargetDetail(Map<String,Object> params);
    /**
     * 业务员销售指标详情(获取每月设置的月度指标)
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryYwySaleTargetDetailList(Map<String,Object> params);
    /**
     * 编辑业务员销售指标主表信息
     * @param params
     * @return
     */
    public int updateSaleTargetYwy(Map<String,Object> params);
    /**
     * 删除业务员销售指标详表信息
     * @param params
     * @return
     */
    public int deleteSaleTargetYwyDetail(Map<String,Object> params);
    /**
     * 查询已设置用户指标的列表数据
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryUserTypeOption(Map<String,Object> params);
    /**
     * 查询当前指标完成人在当前年份设置的销售指标
     * @param params
     * @return
     */
    public Map<String,Object> queryUserSalesTarget(Map<String,Object> params);
    /**
     * 查询总销售指标
     * @param params
     * @return
     */
    public Map<String,Object> queryTotalAnnualTarget(Map<String,Object> params);
    /**
     * 查询销售人员指标信息
     * @param params
     * @return
     */
    public Map<String,Object> querySaleTargetsYwyInfo(Map<String,Object> params);
    /**
     * 查询会员库列表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryMemberLibraryList(Map<String,Object> params);
    /**
     * 查询会员库列表总数
     * @param params
     * @return
     */
    public int queryMemberLibraryCount(Map<String,Object> params);
    /**
     * 新增会员销售指标主表信息
     * @param params
     * @return
     */
    public int insertSaleTargetHy(Map<String,Object> params);
    /**
     * 新增会员销售指标详表信息
     * @param params
     * @return
     */
    public int insertSaleTargetHyDetail(List<Map<String,Object>> list);
    /**
     * 编辑会员销售指标主表信息
     * @param params
     * @return
     */
    public int updateSaleTargetHy(Map<String,Object> params);
    /**
     * 删除会员销售指标详表信息
     * @param params
     * @return
     */
    public int deleteSaleTargetHyDetail(Map<String,Object> params);
    /**
     * 会员销售指标详情(支持单个和批量查看)
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryHySaleTargetDetail(Map<String,Object> params);
    /**
     * 会员销售指标详情(获取每月设置的月度指标)
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryHySaleTargetDetailList(Map<String,Object> params);
    /**
     * 审批业务员销售指标
     * @param params
     * @return
     */
    public int approvalYwySaleTarget(Map<String,Object> params);
    /**
     * 审批业务员销售指标详情
     * @param params
     * @return
     */
    public int approvalYwySaleTargetDetail(Map<String,Object> params);
    /**
     * 审批会员销售指标详情
     * @param params
     * @return
     */
    public int approvalHySaleTargetDetail(Map<String,Object> params);
    /**
     * 审批业务员销售指标
     * @param params
     * @return
     */
    public int approvalHySaleTarget(Map<String,Object> params);
    /**
     * 查询销售人员关联的业务经理和门店
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryUserYwjlAndStoreData(Map<String,Object> params);
    
    /**
	 * 销售指标完成情况统计总数 (按人员)
	 * @param paramMap
	 * @return
	 */
	int querySaleTargetTotalYwyCount(Map<String, Object> paramMap);
	/**
	 * 销售指标完成情况统计分页查询 (按人员)
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> querySaleTargetTotalYwyListForPage(Map<String, Object> paramMap);
	/**
	 * 销售指标完成情况统计汇总 (按人员)
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> querySaleTargetTotalYwySummary(Map<String, Object> paramMap);
	/**
	 * 获取销售总量
	 * @return
	 */
	int getTodayTotalCount();
	/**
	 * 获取销售总额
	 * @return
	 */
	double getTodayTotalMoney();
	/**
	 * 销售指标完成情况统计总数 (按会务员)
	 * @param paramMap
	 * @return
	 */
	int querySaleTargetTotalHyCount(Map<String, Object> paramMap);
	/**
	 * 销售指标完成情况统计分页查询 (按会员)
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> querySaleTargetTotalHyListForPage(Map<String, Object> paramMap);
	/**
	 * 销售指标完成情况统计汇总 (按会员)
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> querySaleTargetTotalHySummary(Map<String, Object> paramMap);
	/**
	 * 重点客户跟进表总数(按会员)
	 * @param paramMap
	 * @return
	 */
	int querySaleTargetTotalEmpHyCount(Map<String, Object> paramMap);
	/**
	 * 重点客户跟进表(按会员)
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> querySaleTargetTotalEmpHyListForPage(Map<String, Object> paramMap);
	/**
	 * 获取每天销售额和销售数量
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> querySaleMoneyAndCountToDate(Map<String, Object> map);
	/**
	 * 业务员完成量(按人员)
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryYwyPerformMoney(Map<String, Object> paramMap);
	/**
	 * 业务员今天销额销量(按人员)
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryYwyTodayMoneyAndCount(Map<String, Object> paramMap);
	/**
	 * 完成量汇总(按人员)
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryPerformMoneyYwySummary(Map<String, Object> paramMap);
	/**
	 * 今日销额和销量汇总(按人员)
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryTodayMoneyAndCountYwySummary(Map<String, Object> paramMap);
	
	/**
	 * 业务员退款数据(按人员)
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryYwyRefundList(Map<String, Object> paramMap);
	/**
	 * 业务员退货数据(按人员)
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryYwyReturnList(Map<String, Object> paramMap);
	/**
	 * 业务员今天退款数据(按人员)
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryYwyTodayRefundList(Map<String, Object> paramMap);
	/**
	 * 业务员今天退货数据(按人员)
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryYwyTodayReturnList(Map<String, Object> paramMap);
	/**
	 * 退款数据汇总(按人员)
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryRefundYwySummary(Map<String, Object> paramMap);
	/**
	 * 退货数据汇总(人员)
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryReturnYwySummary(Map<String, Object> paramMap);
	/**
	 * 今天退款数据汇总(按人员)
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryTodayRefundYwySummary(Map<String, Object> paramMap);
	/**
	 * 今天退货数据汇总(人员)
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryTodayReturnYwySummary(Map<String, Object> paramMap);
	/**
	 * 完成量(按会员)
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryHyPerformMoney(Map<String, Object> paramMap);
	/**
	 * 退款数据(按会员)
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryHyRefundList(Map<String, Object> paramMap);
	/**
	 * 退货数据(按会员)
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryHyReturnList(Map<String, Object> paramMap);
	/**
	 * 今天销售额和销售量(按会员)
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryHyTodayMoneyAndCount(Map<String, Object> paramMap);
	/**
	 * 今天退款数据(按会员)
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryHyTodayRefundList(Map<String, Object> paramMap);
	/**
	 * 今天退货数据(按会员)
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryHyTodayReturnList(Map<String, Object> paramMap);
	/**
	 * 完成量汇总(按会员)
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryPerformMoneyHySummary(Map<String, Object> paramMap);
	/**
	 * 今天销售额和销售量汇总(按会员)
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryTodayMoneyAndCountHySummary(Map<String, Object> paramMap);
	/**
	 * 退款数据汇总(按会员)
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryRefundHySummary(Map<String, Object> paramMap);
	/**
	 * 退货数据汇总(按会员)
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryReturnHySummary(Map<String, Object> paramMap);
	/**
	 * 今天退款数据(按会员)
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryTodayRefundHySummary(Map<String, Object> paramMap);
	/**
	 * 今天退货数据(按会员)
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryTodayReturnHySummary(Map<String, Object> paramMap);
	/**
	 * 数据获取-年度完成量(按会员)
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryAnnualPerformMoney(Map<String, Object> paramMap);
	
	/**
	 * 年度退款数据(按会员)
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryAnnualRefundList(Map<String, Object> paramMap);
	/**
	 * 年度退货数据(按会员)
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryAnnualReturnList(Map<String, Object> paramMap);
	/**
	 * 数据获取-月度完成量(按会员)
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryMonthPerformMoney(Map<String, Object> paramMap);
	/**
	 * 月度退款数据(按会员)
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryMonthRefundList(Map<String, Object> paramMap);
	/**
	 * 月度退货数据(按会员)
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryMonthReturnList(Map<String, Object> paramMap);
	/**
	 * 每天退款数据(按会员)
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryRefundMoneyAndCountToDate(Map<String, Object> map);
	/**
	 * 每天退货数据(按会员)
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryReturnMoneyAndCountToDate(Map<String, Object> map);
	/**
	 * 查询单据信息
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryDocumentList(Map<String, Object> paramMap);
	/**
	 * 任务完成量---获取排序后的用户信息
	 * @param paramMap
	 * @return
	 */
	public List<String> querySaleTargetTotalYwyListByPerformMoney(Map<String, Object> paramMap);
	/**
	 * 查询默认排序的人员
	 * @param paramMap
	 * @return
	 */
	public List<String> querySaleTargetTotalYwyListByDefault(Map<String, Object> paramMap);
	/**
	 * 今日销售量---获取排序后的用户信息
	 * @param paramMap
	 * @return
	 */
	public List<String> querySaleTargetTotalYwyListByTodayCount(Map<String, Object> paramMap);
	/**
	 * 销售指标完成情况统计列表(按人员)
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> querySaleTargetTotalYwyList(Map<String, Object> paramMap);
	/**
	 * 任务指标---获取排序后的用户信息(按会员)
	 * @param paramMap
	 * @return
	 */
	public List<String> queryHySumMouthValue_User(Map<String, Object> paramMap);
	/**
	 * 指标占比---获取排序后的用户信息(按会员)
	 * @param paramMap
	 * @return
	 */
	public List<String> queryHyTargetRatio_User(Map<String, Object> paramMap);
	/**
	 * 任务完成量---获取排序后的用户信息(按会员)
	 * @param paramMap
	 * @return
	 */
	public List<String> queryHyPerformMoney_User(Map<String, Object> paramMap);
	/**
	 * 任务完成率---获取排序后的用户信息(按会员)
	 * @param paramMap
	 * @return
	 */
	public List<String> queryHyPerformRatio_User(Map<String, Object> paramMap);
	/**
	 * 今日销售量---获取排序后的用户信息(按会员)
	 * @param paramMap
	 * @return
	 */
	public List<String> queryHyTodayCount_User(Map<String, Object> paramMap);
	/**
	 * 今日销售额---获取排序后的用户信息(按会员)
	 * @param paramMap
	 * @return
	 */
	public List<String> queryHyTodayMoney_User(Map<String, Object> paramMap);
	/**
	 * 今日销售量占比---获取排序后的用户信息(按会员)
	 * @param paramMap
	 * @return
	 */
	public List<String> queryHyTodayCountRatio_User(Map<String, Object> paramMap);
	/**
	 * 今日销售额占比---获取排序后的用户信息(按会员)
	 * @param paramMap
	 * @return
	 */
	public List<String> queryHyTodayMoneyRatio_User(Map<String, Object> paramMap);
	/**
	 * 任务完成情况汇总--默认排序的用户信息(按会员)
	 * @param paramMap
	 * @return
	 */
	public List<String> querySaleTargetTotalHyListBy_Default(Map<String, Object> paramMap);
	/**
	 * 任务完成情况汇总--用户基本信息(按会员)
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> querySaleTargetTotalHyList(Map<String, Object> paramMap);
	/**
	 * 年度指标---获取排序后的用户信息(按会员)
	 * @param paramMap
	 * @return
	 */
	public List<String> queryAnnualTarget_User(Map<String, Object> paramMap);
	/**
	 * 年度完成量---获取排序后的用户信息(按会员)
	 * @param paramMap
	 * @return
	 */
	public List<String> queryAnnualPerformMoney_User(Map<String, Object> paramMap);
	/**
	 * 年度完成率---获取排序后的用户信息(按会员)
	 * @param paramMap
	 * @return
	 */
	public List<String> queryAnnualPerformRatio_User(Map<String, Object> paramMap);
	/**
	 * 月份指标---获取排序后的用户信息(按会员)
	 * @param paramMap
	 * @return
	 */
	public List<String> queryMonthTarget_User(Map<String, Object> paramMap);
	/**
	 * 月份完成量---获取排序后的用户信息(按会员)
	 * @param paramMap
	 * @return
	 */
	public List<String> queryMonthPerformMoney_User(Map<String, Object> paramMap);
	/**
	 * 月份完成率---获取排序后的用户信息(按会员)
	 * @param paramMap
	 * @return
	 */
	public List<String> queryMonthPerformRatio_User(Map<String, Object> paramMap);
	/**
	 * 重点客户跟进表--默认排序的用户信息(按会员)
	 * @param paramMap
	 * @return
	 */
	public List<String> querySaleTargetTotalEmpHyListBy_Default(Map<String, Object> paramMap);
	/**
	 * 重点客户跟进表--用户基本信息(按会员)
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> querySaleTargetTotalEmpHyList(Map<String, Object> paramMap);

}
