package com.tk.oms.basicinfo.control;

import com.tk.oms.basicinfo.service.StandardLogisticsService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (c), 2018, TongKu
 * FileName : StandardLogisticsControl
 * 标准物流公司接口访问层
 *
 * @author zhenghui
 * @version 1.00
 * @date 2018-06-12
 */
@Controller
@RequestMapping("/standard_logistics")
public class StandardLogisticsControl {

    @Resource
    private StandardLogisticsService standardLogisticsService;

    /**
     * @api {post} /{project_name}/standard_logistics/page_list 物流公司列表
     * @apiGroup standard_logistics
     * @apiDescription  分页查询标准物流公司列表
     *
     * @apiParam {number} pageIndex 开始页码
     * @apiParam {number} pageSize 每页数据量
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * @apiSuccess {object[]} obj 接口返回集合
     * @apiSuccess {number} total 物流公司数量
     * @apiSuccess {number} obj.ID 物流公司ID
     * @apiSuccess {string} obj.LOGISTICS_CODE 快递公司代码
     * @apiSuccess {string} obj.LOGISTICS_NAME 快递公司名称
     * @apiSuccess {string} obj.LOGISTICS_LOGO 快递公司LOGO
     * @apiSuccess {number} obj.STATE 是否启用
     *                                       <p>1.启用</p>
     *                                       <p>2.禁用</p>
     */
    @RequestMapping(value = "/page_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryStandardLogisticsListForPage(HttpServletRequest request) {
        return Transform.GetResult(this.standardLogisticsService.queryStandardLogisticsListForPage(request));
    }

    /**
     * @api {post} /{project_name}/standard_logistics/list 物流公司列表
     * @apiGroup standard_logistics
     * @apiDescription  查询标准物流公司列表
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * @apiSuccess {object[]} obj 接口返回集合
     * @apiSuccess {number} obj.id 物流公司ID
     * @apiSuccess {string} obj.option 快递公司名称
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryStandardLogisticsList(HttpServletRequest request) {
        return Transform.GetResult(this.standardLogisticsService.queryStandardLogisticsList(request));
    }

    /**
     * @api {post} /{project_name}/standard_logistics/detail 物流公司详情
     * @apiGroup standard_logistics
     * @apiDescription 查询物流公司详情
     *
     * @apiParam {number} logistics_id 物流公司ID
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * @apiSuccess {object[]} obj 接口返回集合
     * @apiSuccess {number} obj.ID 物流公司ID
     * @apiSuccess {string} obj.LOGISTICS_CODE 快递公司代码
     * @apiSuccess {string} obj.LOGISTICS_NAME 快递公司名称
     * @apiSuccess {string} obj.LOGISTICS_LOGO 快递公司LOGO
     * @apiSuccess {number} obj.STATE 是否启用
     *                                       <p>1.启用</p>
     *                                       <p>2.禁用</p>
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryStandardLogisticsDetail(HttpServletRequest request) {
        return Transform.GetResult(this.standardLogisticsService.queryStandardLogisticsDetail(request));
    }

    /**
     * @api {post} /{project_name}/standard_logistics/add 新增物流公司
     * @apiGroup standard_logistics
     * @apiDescription 新增物流公司
     *
     * @apiParam {number} logistics_name 快递公司名称
     * @apiParam {number} logistics_code 快递公司代码
     * @apiParam {number} [logistics_logo] 快递公司LOGO
     * @apiParam {number} [state] 是否启用
     *                                 <p>1.启用</p>
     *                                 <p>2.禁用</p>
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addStandardLogistics(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr =  this.standardLogisticsService.addStandardLogistics(request);
        }catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/standard_logistics/edit 编辑物流公司
     * @apiGroup standard_logistics
     * @apiDescription 编辑物流公司
     *
     * @apiParam {number} logistics_id 物流公司ID
     * @apiParam {number} [logistics_name] 快递公司名称
     * @apiParam {number} [logistics_code] 快递公司代码
     * @apiParam {number} [logistics_logo] 快递公司LOGO
     * @apiParam {number} [state] 是否启用
     *                                 <p>1.启用</p>
     *                                 <p>2.禁用</p>
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editStandardLogistics(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr =  this.standardLogisticsService.editStandardLogistics(request);
        }catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/standard_logistics/edit_state 编辑物流公司状态
     * @apiGroup standard_logistics
     * @apiDescription 编辑物流公司状态
     *
     * @apiParam {number} logistics_id 物流公司ID
     * @apiParam {number} state 是否启用
     *                                 <p>1.启用</p>
     *                                 <p>2.禁用</p>
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     */
    @RequestMapping(value = "/edit_state", method = RequestMethod.POST)
    @ResponseBody
    public Packet editStandardLogisticsState(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr =  this.standardLogisticsService.editStandardLogisticsState(request);
        }catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/standard_logistics/remove 删除物流公司
     * @apiGroup standard_logistics
     * @apiDescription 删除物流公司
     *
     * @apiParam {number} logistics_id 物流公司ID
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeStandardLogistics(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr =  this.standardLogisticsService.removeStandardLogistics(request);
        }catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }
    
    /**
	 * 物流公司列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logistics_company_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet logisticsCompanyList(HttpServletRequest request) {
		return Transform.GetResult(standardLogisticsService.logisticsCompanyList(request));
	}
	
	 /**
     * 物流公司排序
     * 
     */
    @RequestMapping(value = "/sort", method = RequestMethod.POST)
    @ResponseBody
    public Packet standardLogisticsSort(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr =  this.standardLogisticsService.standardLogisticsSort(request);
        }catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }
}
