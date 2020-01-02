package com.tk.oms.basicinfo.control;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.basicinfo.service.HelpCenterService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : HelpCenterControl
 * 新零售帮助中心
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-8-7
 */
@Controller
@RequestMapping("/help_center")
public class HelpCenterControl {
	@Resource
	private HelpCenterService helpCenterService;

	/**
	 * 分页查询子分类列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	@ResponseBody
    public Packet queryHelpCenterList(HttpServletRequest request){
    	return Transform.GetResult(helpCenterService.queryHelpCenterList(request));
    }
    
    /**
     * 查询父分类列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/parent_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryLargeClass(HttpServletRequest request){
    	return Transform.GetResult(helpCenterService.queryLargeClass(request));
    }

    /**
     * 新增分类
     * @param request
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addHelpCenter(HttpServletRequest request){
    	ProcessResult pr = new ProcessResult();
		try {
			pr = helpCenterService.addHelpCenter(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }

    /**
     * 更新分类
     * @param request
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet updateHelpCenter(HttpServletRequest request){
    	ProcessResult pr = new ProcessResult();
		try {
			pr = helpCenterService.updateHelpCenter(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();

		}
		return Transform.GetResult(pr);
    }
    /**
     * 删除分类
     * @param request
     * @return
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet deleteHelpCenter(HttpServletRequest request){
    	ProcessResult pr = new ProcessResult();
		try {
			pr = helpCenterService.deleteHelpCenter(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }

    /**
     * 是否启用
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/state", method = RequestMethod.POST)
    @ResponseBody
    public Packet updateIsDelete(HttpServletRequest request) throws IOException{
        return Transform.GetResult(helpCenterService.updateIsDelete(request));
    }

    /**
     * 排序
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/sort", method = RequestMethod.POST)
    @ResponseBody
    public Packet helpCenterSort(HttpServletRequest request) throws IOException{
        return Transform.GetResult(helpCenterService.helpCenterSort(request));
    }

    /**
     * 查询视频列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/video_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryVideoList(HttpServletRequest request){
    	return Transform.GetResult(helpCenterService.queryVideoList(request));
    }

    /**
     * 新增子类信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/helpCenterInfo_add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addHelpCenterInfo(HttpServletRequest request){
    	ProcessResult pr = new ProcessResult();
		try {
			pr = helpCenterService.addHelpCenterInfo(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    
    /**
     * 更新子类信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/helpCenterInfo_edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet updateHelpCenterInfo(HttpServletRequest request){
    	ProcessResult pr = new ProcessResult();
		try {
			pr = helpCenterService.updateHelpCenterInfo(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    
    /**
     * 删除子类信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/helpCenterInfo_remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet deleteHelpCenterInfo(HttpServletRequest request){
    	ProcessResult pr = new ProcessResult();
		try {
			pr = helpCenterService.deleteHelpCenterInfo(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }

    /**
     * 查询知识库列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/library_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRepositoryList(HttpServletRequest request){
    	return Transform.GetResult(helpCenterService.queryRepositoryList(request));
    }
    
    /**
     * 查询视频和知识库列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryLargeClassifyById(HttpServletRequest request){
    	return Transform.GetResult(helpCenterService.queryLargeClassifyById(request));
    }

    /**
     * 查询子类明细
     * @param request
     * @return
     */
    @RequestMapping(value = "/helpCenterInfo_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryHelpCenterDetail(HttpServletRequest request){
    	return Transform.GetResult(helpCenterService.queryHelpCenterDetail(request));
    }

    /**
     * 子类排序
     * @param request
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/helpCenterInfo_sort", method = RequestMethod.POST)
    @ResponseBody
    public Packet helpCenterInfoSort(HttpServletRequest request) throws IOException{
    	return Transform.GetResult(helpCenterService.helpCenterInfoSort(request));
    }
    
    /**
     * 分类下拉框
     * @param request
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/help_center_combobox", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryHelpCenterCombobox(HttpServletRequest request){
    	return Transform.GetResult(helpCenterService.queryHelpCenterCombobox(request));
    }
}
