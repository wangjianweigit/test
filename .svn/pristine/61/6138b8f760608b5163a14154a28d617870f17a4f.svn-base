package com.tk.analysis.member.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
/**
 * 
 * Copyright (c), 2019, Tongku
 * FileName : MemberAnalysisOperationDao.java
 * 运营分析-会员
 *
 * @author yejingquan
 * @version 1.00
 * @date Jul 24, 2019
 */
@Repository
public interface MemberAnalysisOperationDao {
	
	/**
	 * 会员概况-会员总数
	 * @param paramMap
	 * @return
	 */
	float r_queryMemberTotal(Map<String, Object> paramMap);
	/**
	 * 会员概况-昨日新增会员
	 * @param paramMap
	 * @return
	 */
	float r_queryMemberAddCount(Map<String, Object> paramMap);
	/**
	 * 会员概况-沉睡会员
	 * @param paramMap
	 * @return
	 */
	float r_queryMemberSleepCount(Map<String, Object> paramMap);

	/**
	 * 会员概况-近3月异常会员
	 * @param paramMap
	 * @return
	 */
	float r_queryMemberAbnormalCount(Map<String, Object> paramMap);
	/**
	 * 会员概况-昨日活跃会员
	 * @param paramMap
	 * @return
	 */
	float r_queryMemberLivelyCount(Map<String, Object> paramMap);
	/**
	 * 会员概况-成交会员
	 * @param paramMap
	 * @return
	 */
	float r_queryMemberPayCount(Map<String, Object> paramMap);
	/**
	 * 折线图 新增会员<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMemberAddCountD_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 活跃会员<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMemberLivelyCountD_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 成交会员<天>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMemberPayCountD_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 新增会员
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMemberAddCount_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 活跃会员
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMemberLivelyCount_Chart(Map<String, Object> paramMap);
	/**
	 * 折线图 成交会员
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMemberPayCount_Chart(Map<String, Object> paramMap);
	/**
	 * 会员总数
	 * @param paramMap
	 * @return
	 */
	int r_queryMemberCount(Map<String, Object> paramMap);
	/**
	 * 查询默认排序的会员成交信息
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryMemberPayTopListBy_Default(Map<String, Object> paramMap);
	/**
	 * 会员列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMemberList(Map<String, Object> paramMap);
	/**
	 * 查询成交金额,成交商品数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMemberPayCountMoney(Map<String, Object> paramMap);
	/**
	 * 查询预定订单的定金
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMemberPreFirstMoney(Map<String, Object> paramMap);
	/**
	 * 查询最近一次成交时间
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMemberLastPayDate(Map<String, Object> paramMap);
	/**
	 * 查询最近一次成交金额
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMemberLastPayMoney(Map<String, Object> paramMap);
	/**
	 * 查询最近一次预定订单支付时间
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMemberLastPrePayDate(Map<String, Object> paramMap);
	/**
	 * 查询最近一次预定订单的定金
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMemberLastPreFirstMoney(Map<String, Object> paramMap);
	/**
	 * 查询默认排序的会员退款信息
	 * @param paramMap
	 * @return
	 */
	List<String> r_queryMemberReturnTopListBy_Default(Map<String, Object> paramMap);
	/**
	 * 查询已退款总额,退款商品数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMemberReturnCountMoney(Map<String, Object> paramMap);
	/**
	 * 查询待退款金额,待退款笔数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMemberStayReturnMoneyNum(Map<String, Object> paramMap);
	/**
	 * 异常会员排行
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMemberAbnormalRank(Map<String, Object> paramMap);
	/**
	 * 沉睡会员排行
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMemberSleepRank(Map<String, Object> paramMap);
	/**
	 * 异常会员饼图
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMemberWarningAbnormal_Chart(Map<String, Object> paramMap);
	/**
	 * 异常会员总数
	 * @param paramMap
	 * @return
	 */
	int r_queryMemberAbnormalListCount(Map<String, Object> paramMap);
	
	/**
	 * 异常会员列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMemberAbnormalListForPage(Map<String, Object> paramMap);
	/**
	 * 沉睡会员饼图
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMemberSleep_Chart(Map<String, Object> paramMap);
	/**
	 * 沉睡会员总数
	 * @param paramMap
	 * @return
	 */
	int r_queryMemberSleepListCount(Map<String, Object> paramMap);
	/**
	 * 沉睡会员列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMemberSleepListForPage(Map<String, Object> paramMap);
	/**
	 * 折线图 会员活跃度<月>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMemberLivelyCountM_Chart(Map<String, Object> paramMap);
	/**
	 * 会员活跃度列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMemberLivelyList(Map<String, Object> paramMap);
	/**
	 * 会员活跃度列表<月>
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> r_queryMemberLivelyListM(Map<String, Object> paramMap);
	
}
