package com.tk.oms.basicinfo.control;


import com.tk.oms.basicinfo.service.ClassifyService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 
 * Copyright (c), 2016, Tongku
 * FileName : ClassifyControl
 * 分类信息
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-1-4
 */

@Controller
@RequestMapping("/classify")
public class ClassifyControl {
	@Resource
	private ClassifyService classifyService;

	/**
	 * 分页查询子分类列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	@ResponseBody
    public Packet queryClassifyList(HttpServletRequest request){
    	return Transform.GetResult(classifyService.queryClassifyList(request));
    }
    
    /**
     * 查询父分类列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/parent_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryLargeClass(HttpServletRequest request){
    	return Transform.GetResult(classifyService.queryLargeClass(request));
    }

    /**
     * 新增分类
     * @param request
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addClassify(HttpServletRequest request){
    	ProcessResult pr = new ProcessResult();
		try {
			pr = classifyService.addClassify(request);
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
    public Packet updateClassify(HttpServletRequest request){
    	ProcessResult pr = new ProcessResult();
		try {
			pr = classifyService.updateClassify(request);
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
    public Packet deleteClassify(HttpServletRequest request){
    	ProcessResult pr = new ProcessResult();
		try {
			pr = classifyService.deleteClassify(request);
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
        return Transform.GetResult(classifyService.updateIsDelete(request));
    }

    /**
     * 排序
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/sort", method = RequestMethod.POST)
    @ResponseBody
    public Packet classifySort(HttpServletRequest request) throws IOException{
        return Transform.GetResult(classifyService.classifySort(request));
    }

    /**
     * 查询视频列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/video_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryVideoList(HttpServletRequest request){
    	return Transform.GetResult(classifyService.queryVideoList(request));
    }

    /**
     * 新增子类信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/classifyInfo_add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addClassifyInfo(HttpServletRequest request){
    	ProcessResult pr = new ProcessResult();
		try {
			pr = classifyService.addClassifyInfo(request);
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
    @RequestMapping(value = "/classifyInfo_edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet updateClassifyInfo(HttpServletRequest request){
    	ProcessResult pr = new ProcessResult();
		try {
			pr = classifyService.updateClassifyInfo(request);
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
    @RequestMapping(value = "/classifyInfo_remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet deleteClassifyInfo(HttpServletRequest request){
    	ProcessResult pr = new ProcessResult();
		try {
			pr = classifyService.deleteClassifyInfo(request);
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
    	return Transform.GetResult(classifyService.queryRepositoryList(request));
    }
    
    /**
     * 查询视频和知识库列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryLargeClassifyById(HttpServletRequest request){
    	return Transform.GetResult(classifyService.queryLargeClassifyById(request));
    }

    /**
     * 查询子类明细
     * @param request
     * @return
     */
    @RequestMapping(value = "/classifyInfo_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryClassifyDetail(HttpServletRequest request){
    	return Transform.GetResult(classifyService.queryClassifyDetail(request));
    }

    /**
     * 子类排序
     * @param request
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/classifyInfo_sort", method = RequestMethod.POST)
    @ResponseBody
    public Packet classifyInfoSort(HttpServletRequest request) throws IOException{
    	return Transform.GetResult(classifyService.classifyInfoSort(request));
    }
    
    /**
     * 分类下拉框
     * @param request
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/classify_combobox", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryClassifyCombobox(HttpServletRequest request){
    	return Transform.GetResult(classifyService.queryClassifyCombobox(request));
    }
    

}
