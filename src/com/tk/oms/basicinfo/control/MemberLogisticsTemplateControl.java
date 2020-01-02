package com.tk.oms.basicinfo.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.basicinfo.service.MemberLogisticsTemplateService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * 会员物流模板管理
 * @author liujialong
 * @date 2018-9-10
 */
@Controller
@RequestMapping("/member_logistics_template")
public class MemberLogisticsTemplateControl {
	
	@Resource
    private MemberLogisticsTemplateService memberLogisticsTemplateService;
	
	/**
     * 获取会员物流模板列表
     * @param request
     * @return
     */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberLogisticsTemplateList(HttpServletRequest request) {
        return Transform.GetResult(memberLogisticsTemplateService.queryMemberLogisticsTemplateList(request));
    }
	
	/**
     * 查询仓库物流信息
     * @param request
     * @return
     */
	@RequestMapping(value = "/query_warehouse_logistics", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryWarehouseLogistics(HttpServletRequest request) {
        return Transform.GetResult(memberLogisticsTemplateService.queryWarehouseLogistics(request));
    }
	
	 /**
     * 新增会员物流模板
     * @param request
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addMemberLogisticsTemplate(HttpServletRequest request){
    	ProcessResult pr = new ProcessResult();
		try {
			pr = memberLogisticsTemplateService.addMemberLogisticsTemplate(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    
    /**
     * 编辑会员物流模板
     * @param request
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editMemberLogisticsTemplate(HttpServletRequest request){
    	ProcessResult pr = new ProcessResult();
		try {
			pr = memberLogisticsTemplateService.editMemberLogisticsTemplate(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    
    /**
     * 删除会员物流模板
     * @param request
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Packet deleteMemberLogisticsTemplate(HttpServletRequest request){
    	ProcessResult pr = new ProcessResult();
		try {
			pr = memberLogisticsTemplateService.deleteMemberLogisticsTemplate(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    
    /**
     * 会员物流模板详情
     * @param request
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberLogisticsTemplateDetail(HttpServletRequest request) {
        return Transform.GetResult(memberLogisticsTemplateService.queryMemberLogisticsTemplateDetail(request));
    }
    
    /**
     * 会员物流模板下拉数据
     * @param request
     * @return
     */
	@RequestMapping(value = "/query_option", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryOption(HttpServletRequest request) {
        return Transform.GetResult(memberLogisticsTemplateService.queryOption(request));
    }

}
