package com.tk.oms.member.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.member.service.MemberInfoChangeService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * Copyright (c), 2019, Tongku
 * FileName : MemberInfoChangeControl
 * 用户资料变更Control
 *
 * @author liujialong
 * @version 1.00
 * @date 2019/1/10 11:00
 */
@Controller
@RequestMapping("/member_info_change")
public class MemberInfoChangeControl {
	
	@Resource
    private MemberInfoChangeService memberInfoChangeService;
	
	 /**
	  * 用户资料变更审批列表
	  * @param request
	  * @return
	  */
	@RequestMapping(value = "/approval_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryApprovalList(HttpServletRequest request) {
        return Transform.GetResult(memberInfoChangeService.queryApprovalList(request));
    }
	
	/**
	 * 用户资料变更详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/approval_detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet memberInfoDetail(HttpServletRequest request) {
		return Transform.GetResult(memberInfoChangeService.memberInfoDetail(request));
	}
	
	/**
	 * 用户资料变更审批
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/approval", method = RequestMethod.POST)
	@ResponseBody
	public Packet memberInfoApproval(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = memberInfoChangeService.memberInfoApproval(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
  /**
   * 会员业务处理列表
   * @param request
   * @return
   */
   @RequestMapping(value = "/bussiness_deal_list", method = RequestMethod.POST)
   @ResponseBody
   public Packet queryBussinessDealList(HttpServletRequest request) {
       return Transform.GetResult(memberInfoChangeService.queryBussinessDealList(request));
   }
   
   /**
    * 会员业务处理详情
    * @param request
    * @return
    */
    @RequestMapping(value = "/bussiness_deal_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryBussinessDealDetail(HttpServletRequest request) {
        return Transform.GetResult(memberInfoChangeService.queryBussinessDealDetail(request));
    }
    
    /**
	 * 会员业务处理
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/business_deal", method = RequestMethod.POST)
	@ResponseBody
	public Packet memberInfoBussinessDeal(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = memberInfoChangeService.memberInfoBussinessDeal(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
	 * 会员反馈管理列表
	 * @param request
	 * @return
	 */
    @RequestMapping(value = "/feedback_manage_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryFeedbackManageList(HttpServletRequest request) {
        return Transform.GetResult(memberInfoChangeService.queryFeedbackManageList(request));
    }
    
    /**
     * 会员反馈管理详情
     * @param request
     * @return
     */
     @RequestMapping(value = "/feedback_detail", method = RequestMethod.POST)
     @ResponseBody
     public Packet queryFeedbackDetail(HttpServletRequest request) {
         return Transform.GetResult(memberInfoChangeService.queryFeedbackDetail(request));
     }
     
     /**
 	 * 会员反馈处理
 	 * @param request
 	 * @return
 	 */
 	@RequestMapping(value = "/feedback_deal", method = RequestMethod.POST)
 	@ResponseBody
 	public Packet memberFeedbackDeal(HttpServletRequest request) {
 		ProcessResult pr = new ProcessResult();
 		try {
 			pr = memberInfoChangeService.memberFeedbackDeal(request);
 		} catch (Exception e) {
 			pr.setState(false);
 			pr.setMessage(e.getMessage());
 			e.printStackTrace();
 		}
 		return Transform.GetResult(pr);
 	}

}
