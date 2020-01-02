package com.tk.oms.product.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.product.service.SampleProductService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : SampleProductControl.java
 * 样品订购-商品管理
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018-4-2
 */
@Controller
@RequestMapping("/sample_product")
public class SampleProductControl {
	@Resource
	private SampleProductService sampleProductService;
	
	/**
    *
    * @api {post} /{project_name}/sample_product/list 商品列表
    * @apiGroup sample_product
    * @apiName list
    * @apiDescription  商品列表
    * @apiVersion 0.0.1

	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 样品审批列表
    * @apiSuccess {number} obj.ID					主键ID
	* 
    */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet querySampleProductListForPage(HttpServletRequest request) {
		return Transform.GetResult(sampleProductService.querySampleProductListForPage(request));
	}
	
	/**
	 * 新增
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Packet insertSampleProduct(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=sampleProductService.insertSampleProduct(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
	
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	@ResponseBody
	public Packet deleteSampleProduct(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=sampleProductService.deleteSampleProduct(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
}
