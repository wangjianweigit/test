package com.tk.analysis.member.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.analysis.member.service.MemberAnalysisOperationService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2019, Tongku
 * FileName : MemberAnalysisOperationControl.java
 * 运营分析-会员
 *
 * @author yejingquan
 * @version 1.00
 * @date Jul 23, 2019
 */
@Controller
@RequestMapping("/member_analysis_operation")
public class MemberAnalysisOperationControl {
	@Resource
	private MemberAnalysisOperationService memberAnalysisOperationService;
	
	/**
	 * 会员概况-会员总数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/member_total", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberTotal(HttpServletRequest request){
        return Transform.GetResult(memberAnalysisOperationService.queryMemberTotal(request));
    }
	
	/**
	 * 会员概况-昨日新增会员
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/member_addCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberAddCount(HttpServletRequest request){
        return Transform.GetResult(memberAnalysisOperationService.queryMemberAddCount(request));
    }
	
	/**
	 * 会员概况-沉睡会员
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/member_sleepCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberSleepCount(HttpServletRequest request){
        return Transform.GetResult(memberAnalysisOperationService.queryMemberSleepCount(request));
    }
	
	/**
	 * 会员概况-近3月异常会员
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/member_abnormalCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberAbnormalCount(HttpServletRequest request){
        return Transform.GetResult(memberAnalysisOperationService.queryMemberAbnormalCount(request));
    }
	
	/**
	 * 会员概况-昨日活跃会员
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/member_livelyCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberLivelyCount(HttpServletRequest request){
        return Transform.GetResult(memberAnalysisOperationService.queryMemberLivelyCount(request));
    }
	
	/**
	 * 会员概况-成交会员
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/member_payCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberPayCount(HttpServletRequest request){
        return Transform.GetResult(memberAnalysisOperationService.queryMemberPayCount(request));
    }
	
	/**
	 * 会员概况折线图
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/member_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberChart(HttpServletRequest request){
        return Transform.GetResult(memberAnalysisOperationService.queryMemberChart(request));
    }
	
	/**
	 * 会员概况-会员成交TOP
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/member_pay_top", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberPayTop(HttpServletRequest request){
        return Transform.GetResult(memberAnalysisOperationService.queryMemberPayTop(request));
    }
	
	/**
	 * 会员概况-会员退款TOP
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/member_return_top", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberReturnTop(HttpServletRequest request){
        return Transform.GetResult(memberAnalysisOperationService.queryMemberReturnTop(request));
    }
	
	/**
	 * 会员概况-异常会员排行
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/member_abnormal_rank", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberAbnormalRank(HttpServletRequest request){
        return Transform.GetResult(memberAnalysisOperationService.queryMemberAbnormalRank(request));
    }
	
	/**
	 * 会员概况-沉睡会员排行
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/member_sleep_rank", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberSleepRank(HttpServletRequest request){
        return Transform.GetResult(memberAnalysisOperationService.queryMemberSleepRank(request));
    }
	
	/**
	 * 会员预警-异常会员饼图
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/memberWarning_abnormal_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberWarningAbnormalChart(HttpServletRequest request){
        return Transform.GetResult(memberAnalysisOperationService.queryMemberWarningAbnormalChart(request));
    }
	
	/**
	 * 会员预警-异常会员列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/memberWarning_abnormal_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberWarningAbnormalList(HttpServletRequest request){
        return Transform.GetResult(memberAnalysisOperationService.queryMemberWarningAbnormalList(request));
    }
	
	/**
	 * 会员预警-沉睡会员饼图
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/memberWarning_sleep_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberWarningSleepChart(HttpServletRequest request){
        return Transform.GetResult(memberAnalysisOperationService.queryMemberWarningSleepChart(request));
    }
	
	/**
	 * 会员预警-沉睡会员列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/memberWarning_sleep_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberWarningSleepList(HttpServletRequest request){
        return Transform.GetResult(memberAnalysisOperationService.queryMemberWarningSleepList(request));
    }
	
	/**
	 * 会员活跃度折线图
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/memberLively_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberLivelyChart(HttpServletRequest request){
        return Transform.GetResult(memberAnalysisOperationService.queryMemberLivelyChart(request));
    }
	
	/**
	 * 会员活跃度列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/memberLively_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberLivelyList(HttpServletRequest request){
        return Transform.GetResult(memberAnalysisOperationService.queryMemberLivelyList(request));
    }
	
}
