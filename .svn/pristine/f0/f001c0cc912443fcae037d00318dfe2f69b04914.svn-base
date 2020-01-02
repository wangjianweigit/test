package com.tk.oms.basicinfo.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.basicinfo.service.LogisticsCompanyService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : LogisticsCompanyControl
 * 物流公司管理
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-4-14
 */

@Controller
@RequestMapping("logistics_company")
public class LogisticsCompanyControl {
	
	@Resource
	private LogisticsCompanyService logisticsCompanyService;
	
	/**
     * @api {post} /{project_name}/logistics_company/list
     * @apiName list
     * @apiGroup logistics_company
     * @apiDescription  物流公司信息列表
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} pageIndex 		开始页码
     * @apiParam {number} pageSize 			每页数据量
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 					查询物流公司列表
     * @apiSuccess {number}     total 					物流公司数量
     * @apiSuccess {number}   	obj.id 					物流公司ID
     * @apiSuccess {string}   	obj.code				快递公司代码
     * @apiSuccess {string}   	obj.name 				快递公司名称
     * @apiSuccess {char}   	obj.type				类型   1 普通物流 ，2 代发物流
     * @apiSuccess {number}   	obj.sort_id 			排序ID
     * @apiSuccess {date}   	obj.create_date 		创建时间
     * @apiSuccess {number}   	obj.cnt					会员可用物流数量
     * 
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryLogisticsCompanyList(HttpServletRequest request) {
        return Transform.GetResult(logisticsCompanyService.queryLogisticsCompanyList(request));
    }
    /**
     * @api {post} /{project_name}/logistics_company/detail
     * @apiName detail
     * @apiGroup logistics_company
     * @apiDescription  物流公司详细信息
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id 		物流公司ID
     * @apiParam {number} code 		快递公司代码
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object}   	obj 					物流公司详情
     * @apiSuccess {number}   	obj.id 					物流公司ID
     * @apiSuccess {string}   	obj.code				快递公司代码
     * @apiSuccess {string}   	obj.name 				快递公司名称
     * 
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryLogisticsCompanyDetail(HttpServletRequest request) {
        return Transform.GetResult(logisticsCompanyService.queryLogisticsCompanyDetail(request));
    }
    /**
     * @api {post} /{project_name}/logistics_company/add
     * @apiName add
     * @apiGroup logistics_company
     * @apiDescription  添加物流公司信息
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} code 				快递公司代码
     * @apiParam {string} name				快递公司名称
     * @apiParam {char}   type				类型   1 普通物流 ，2 代发物流		
     * @apiParam {number} create_user_id	创建人ID
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addLogisticsCompany(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
    	try {
    		pr = logisticsCompanyService.addLogisticsCompany(request);
		} catch (Exception e) {
			// TODO: handle exception
		}
        return Transform.GetResult(pr);
    }
    /**
     * @api {post} /{project_name}/logistics_company/remove
     * @apiName remove
     * @apiGroup logistics_company
     * @apiDescription  删除物流公司信息
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id 				物流公司ID
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeLogisticsCompany(HttpServletRequest request) {
        return Transform.GetResult(logisticsCompanyService.removeLogisticsCompany(request));
    }
    /**
     * @api {post} /{project_name}/logistics_company/edit
     * @apiName edit
     * @apiGroup logistics_company
     * @apiDescription  编辑物流公司信息
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id				物流公司ID
     * @apiParam {number} code 				快递公司代码
     * @apiParam {string} name				快递公司名称
     * @apiParam {char}   type				类型   1 普通物流 ，2 代发物流		
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editLogisticsCompany(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
    	try {
    		pr = logisticsCompanyService.editLogisticsCompany(request);
		} catch (Exception e) {
			// TODO: handle exception
		}
        return Transform.GetResult(pr);
    } 
    
    /**
     * @api {post} /{project_name}/logistics_company/sort
     * @apiName sort
     * @apiGroup logistics_company
     * @apiDescription  物流公司排序
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id1				第一个分组ID
     * @apiParam {number} id2 				第二个分组ID
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
	@RequestMapping(value = "/sort", method = RequestMethod.POST)
    @ResponseBody
    public Packet logisticsCompanySort(HttpServletRequest request) {
        return Transform.GetResult(logisticsCompanyService.logisticsCompanySort(request));
    }
	/**
     * @api {post} /{project_name}/logistics_company/user_count
     * @apiName user_count
     * @apiGroup logistics_company
     * @apiDescription  查看物流公司关联用户数量
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id				物流公司ID
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object}   	obj 	物流公司关联用户数量
     * 
     */
    @RequestMapping(value = "/user_count", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryLogisticsForUser(HttpServletRequest request) {
       return Transform.GetResult(logisticsCompanyService.queryLogisticsForUser(request));
    }
    /**
     * @api {post} /{project_name}/logistics_company/classify
     * @apiName classify
     * @apiGroup logistics_company
     * @apiDescription  分类返回物流公司
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id				物流公司ID
     * @apiParam {number} code 				快递公司代码
     * @apiParam {string} name				快递公司名称
     * @apiParam {char}   type				类型   1 普通物流 ，2 代发物流	
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 					分类返回物流公司
     * @apiSuccess {list}   	obj.logistics_detail
     * @apiSuccess {number}   	obj.logistics_detail.id 		物流公司ID
     * @apiSuccess {string}   	obj.logistics_detail.code		快递公司代码
     * @apiSuccess {string}   	obj.logistics_detail.name 		快递公司名称
     * @apiSuccess {char}   	obj.logistics_detail.type		类型   1 普通物流 ，2 代发物流
     * @apiSuccess {number}   	obj.logistics_detail.sort_id 	排序ID
     * @apiSuccess {list}   	obj.issuing_detail
     * @apiSuccess {number}   	obj.issuing_detail.id 			物流公司ID
     * @apiSuccess {string}   	obj.issuing_detail.code			快递公司代码
     * @apiSuccess {string}   	obj.issuing_detail.name 		快递公司名称
     * @apiSuccess {char}   	obj.issuing_detail.type			类型   1 普通物流 ，2 代发物流
     * @apiSuccess {number}   	obj.issuing_detail.sort_id 		排序ID
     * 
     */
    @RequestMapping(value = "/classify", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryLogisticsList(HttpServletRequest request) {
       return Transform.GetResult(logisticsCompanyService.queryLogisticsList(request));
    }
   /**
    *
    * @api {post} /{project_name}/logistics_company/state
    * @apiName state
    * @apiGroup logistics_company
    * @apiDescription  是否启用
    * @apiVersion 0.0.1
	*
    * @apiParam {number} id				分组ID
    * @apiParam {char}   state          分组状态（1:禁用，2:启用）

    * @apiSuccess {boolean} state  接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息.
    */
    @RequestMapping(value = "/state", method = RequestMethod.POST)
    @ResponseBody
    public Packet editLogisticsCompanyState(HttpServletRequest request) {
        return Transform.GetResult(logisticsCompanyService.editLogisticsCompanyState(request));
    }
    
    /**
     * 查询配送方式下拉列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/distribution_method_option", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDistributionMethodOption(HttpServletRequest request) {
       return Transform.GetResult(logisticsCompanyService.queryDistributionMethodOption(request));
    }
    
}
