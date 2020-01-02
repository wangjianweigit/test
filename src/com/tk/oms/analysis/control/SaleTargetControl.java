package com.tk.oms.analysis.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.analysis.service.SaleTargetService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * Copyright (c), 2018, Tongku
 * FileName : SaleTargetControl
 * 业务员、会员销售指标相关接口
 *
 * @author liujialong
 * @version 1.00
 * @date 2018/8/28
 */
@Controller
@RequestMapping("/sale_target")
public class SaleTargetControl {
	
	@Resource
    private SaleTargetService saleTargetService;
	
	/**
     * 获取业务员销售指标列表数据
     * @param request
     * @return
     */
	@RequestMapping(value = "/query_ywy_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryYwySaleTargetList(HttpServletRequest request) {
        return Transform.GetResult(this.saleTargetService.queryYwySaleTargetList(request));
    }
	
	 /**
     * 获取业务员列表数据
     * @param request
     * @return
     */
	@RequestMapping(value = "/query_user_type_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryUserTypeList(HttpServletRequest request) {
        return Transform.GetResult(this.saleTargetService.queryUserTypeList(request));
    }
	
	/**
     * 获取会员销售指标列表数据
     * @param request
     * @return
     */
	@RequestMapping(value = "/query_hy_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryHySaleTargetList(HttpServletRequest request) {
        return Transform.GetResult(this.saleTargetService.queryHySaleTargetList(request));
    }
	
	/**
     * 新增业务员销售指标
     * @param request
     * @return
     */
    @RequestMapping(value = "/add_ywy", method = RequestMethod.POST)
    @ResponseBody
    public Packet addYwy(HttpServletRequest request){
    	ProcessResult pr = new ProcessResult();
		try {
			pr = saleTargetService.addYwy(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    
    /**
     * 新增会员销售指标
     * @param request
     * @return
     */
    @RequestMapping(value = "/add_hy", method = RequestMethod.POST)
    @ResponseBody
    public Packet addHy(HttpServletRequest request){
    	ProcessResult pr = new ProcessResult();
		try {
			pr = saleTargetService.addHy(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    
    /**
     * 编辑业务员销售指标
     * @param request
     * @return
     */
    @RequestMapping(value = "/edit_ywy", method = RequestMethod.POST)
    @ResponseBody
    public Packet editYwy(HttpServletRequest request){
    	ProcessResult pr = new ProcessResult();
		try {
			pr = saleTargetService.editYwy(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    
    /**
     * 审批业务员销售指标
     * @param request
     * @return
     */
    @RequestMapping(value = "/approval_ywy", method = RequestMethod.POST)
    @ResponseBody
    public Packet approvalYwy(HttpServletRequest request){
    	ProcessResult pr = new ProcessResult();
		try {
			pr = saleTargetService.approvalYwy(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    
    /**
     * 编辑会员销售指标
     * @param request
     * @return
     */
    @RequestMapping(value = "/edit_hy", method = RequestMethod.POST)
    @ResponseBody
    public Packet editHy(HttpServletRequest request){
    	ProcessResult pr = new ProcessResult();
		try {
			pr = saleTargetService.editHy(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    
    /**
     * 审批会员销售指标
     * @param request
     * @return
     */
    @RequestMapping(value = "/approval_hy", method = RequestMethod.POST)
    @ResponseBody
    public Packet approvalHy(HttpServletRequest request){
    	ProcessResult pr = new ProcessResult();
		try {
			pr = saleTargetService.approvalHy(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    
    /**
     * 业务员销售指标详情(支持单个和批量查看)
     * @param request
     * @return
     */
	@RequestMapping(value = "/ywy_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet ywyDetail(HttpServletRequest request) {
        return Transform.GetResult(this.saleTargetService.ywyDetail(request));
    }
	
	/**
     * 会员销售指标详情(支持单个和批量查看)
     * @param request
     * @return
     */
	@RequestMapping(value = "/hy_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet hyDetail(HttpServletRequest request) {
        return Transform.GetResult(this.saleTargetService.hyDetail(request));
    }
	
	/**
     * 查询已设置用户指标的列表数据
     * @param request
     * @return
     */
	@RequestMapping(value = "/user_type_option", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryUserTypeOption(HttpServletRequest request) {
        return Transform.GetResult(this.saleTargetService.queryUserTypeOption(request));
    }
	
	/**
     * 查询当前指标完成人在当前年份设置的销售指标
     * @param request
     * @return
     */
	@RequestMapping(value = "/user_sale_target_value", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryUserSalesTarget(HttpServletRequest request) {
        return Transform.GetResult(this.saleTargetService.queryUserSalesTarget(request));
    }
	
	/**
     * 查询会员库
     * @param request
     * @return
     */
	@RequestMapping(value = "/query_member_library", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberLibrary(HttpServletRequest request) {
        return Transform.GetResult(this.saleTargetService.queryMemberLibrary(request));
    }
	
	/**
	 * 销售情况完成情况统计列表(按人员)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ywy/total_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySaleTargetTotalYwyListForPage(HttpServletRequest request){
        return Transform.GetResult(saleTargetService.querySaleTargetTotalYwyListForPage(request));
    }
	
	/**
	 * 销售情况完成情况统计列表(按会员)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/hy/total_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySaleTargetTotalHyListForPage(HttpServletRequest request){
        return Transform.GetResult(saleTargetService.querySaleTargetTotalHyListForPage(request));
    }
	
	/**
	 * 重点客户跟进表(按会员)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/emphy/total_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySaleTargetTotalEmpHyListForPage(HttpServletRequest request){
        return Transform.GetResult(saleTargetService.querySaleTargetTotalEmpHyListForPage(request));
    }
	
	/**
	 * 查询日期列
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/date_line", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDateLine(HttpServletRequest request){
        return Transform.GetResult(saleTargetService.queryDateLine(request));
    }
	
	/**
	 * 查询日期列
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/document_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDocumentList(HttpServletRequest request){
        return Transform.GetResult(saleTargetService.queryDocumentList(request));
    }

}
