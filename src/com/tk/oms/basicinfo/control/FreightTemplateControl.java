package com.tk.oms.basicinfo.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.basicinfo.service.FreightTemplateService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : FreightTemplateControl
 * 运费模板管理
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-4-14
 */
@Controller
@RequestMapping("freight_template")
public class FreightTemplateControl {
	@Resource
	private FreightTemplateService  freightTemplateService;
	
	/**
     * @api {post} /{project_name}/freight_template/list
     * @apiName list
     * @apiGroup freight_template
     * @apiDescription  查询运费模板列表
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} pageIndex 		开始页码
     * @apiParam {number} pageSize 			每页数据量
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 					查询运费模板列表
     * @apiSuccess {number}     total 					运费模板数量
     * @apiSuccess {number}   	obj.id 					模板ID
     * @apiSuccess {string}   	obj.name				模板名称
     * @apiSuccess {string}   	obj.time 				运送时间
     * @apiSuccess {char}   	obj.state				是否启用  1（停用）2（启用）
     * @apiSuccess {char}   	obj.is_default 			是否默认,同时只允许存在一个默认运费模板  1（非默认）2（默认）
     * @apiSuccess {date}   	obj.edit_date 			最后编辑时间
     * 
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryFreightTemplateList(HttpServletRequest request) {
        return Transform.GetResult(freightTemplateService.queryFreightTemplateList(request));
    }
    
    /**
     * @api {post} /{project_name}/freight_template/add
     * @apiName add
     * @apiGroup freight_template
     * @apiDescription  添加运费模板
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} create_user_id		创建人ID
     * @apiParam {string} name  				模板名称
     * @apiParam {string} time 					运送时间
     * @apiParam {string} logistic_ids			快递ID("|"拼接)
     * @apiParam {string} logistic_names		快递名称("|"拼接)
     * @apiParam {string} send_to_areas			运送范围（省ID以逗号分隔）("|"拼接)
     * @apiParam {string} send_to_area_names	运送范围文字（以逗号分隔）("|"拼接)
     * @apiParam {string} first_counts			首件数量("|"拼接)
     * @apiParam {string} first_moneys			首件价格("|"拼接)
     * @apiParam {string} continue_counts		续件数量("|"拼接)
     * @apiParam {string} continue_moneys		续件价格("|"拼接)
     * @apiParam {string} warehouse_ids			所属仓库【大仓】("|"拼接)
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addFreightTemplate(HttpServletRequest request) {
        return Transform.GetResult(freightTemplateService.addFreightTemplate(request));
    }
    
    /**
     * @api {post} /{project_name}/freight_template/edit
     * @apiName edit
     * @apiGroup freight_template
     * @apiDescription  编辑运费模板
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} create_user_id		创建人ID
     * @apiParam {number} id					模板ID
     * @apiParam {string} name  				模板名称
     * @apiParam {string} time 					运送时间
     * @apiParam {char}	  state					是否启用  1（停用）2（启用）
     * @apiParam {char}   is_default			是否默认,同时只允许存在一个默认运费模板  1（非默认）2（默认）
     * @apiParam {string} logistic_ids			快递ID("|"拼接)
     * @apiParam {string} logistic_names		快递名称("|"拼接)
     * @apiParam {string} send_to_areas			运送范围（省ID以逗号分隔）("|"拼接)
     * @apiParam {string} send_to_area_names	运送范围文字（以逗号分隔）("|"拼接)
     * @apiParam {string} first_counts			首件数量("|"拼接)
     * @apiParam {string} first_moneys			首件价格("|"拼接)
     * @apiParam {string} continue_counts		续件数量("|"拼接)
     * @apiParam {string} continue_moneys		续件价格("|"拼接)
     * @apiParam {string} warehouse_ids			所属仓库【大仓】("|"拼接)
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editFreightTemplate(HttpServletRequest request) {
        return Transform.GetResult(freightTemplateService.editFreightTemplate(request));
    }
    
    /**
     * 删除运费模板
     * @param request
     * @return
     */
    /**
     * @api {post} /{project_name}/freight_template/remove
     * @apiName remove
     * @apiGroup freight_template
     * @apiDescription  删除运费模板
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id			模板ID
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeFreightTemplate(HttpServletRequest request) {
        return Transform.GetResult(freightTemplateService.removeFreightTemplate(request));
    }
    
    /**
     * @api {post} /{project_name}/freight_template/detail
     * @apiName detail
     * @apiGroup freight_template
     * @apiDescription  查询运费模板详情
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id 				模板ID
     * @apiParam {number} warehouse_id  	仓库ID
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 					查询运费模板详情
     * @apiSuccess {list}		obj.list_detail			运费模板详情
     * @apiSuccess {list}		obj.logistics_detail	普通物流
     * @apiSuccess {list}		obj.issuing_detail		代发物流
     * 
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryFreightTemplateDetail(HttpServletRequest request) {
        return Transform.GetResult(freightTemplateService.queryFreightTemplateDetail(request));
    }
    
    /**
     * @api {post} /{project_name}/freight_template/state
     * @apiName state
     * @apiGroup freight_template
     * @apiDescription  启停用运费模板设置
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} 	id 	模板ID
     * @apiParam {char} 	state  			是否启用  1（停用）2（启用）
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
    @RequestMapping(value = "/state", method = RequestMethod.POST)
    @ResponseBody
    public Packet freightTemplateState(HttpServletRequest request) {
        return Transform.GetResult(freightTemplateService.freightTemplateState(request));
    }
    
    /**
     * @api {post} /{project_name}/freight_template/default
     * @apiName default
     * @apiGroup freight_template
     * @apiDescription  默认模板设置
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} 	id 	模板ID
     * @apiParam {char} 	is_default  	是否默认,同时只允许存在一个默认运费模板  1（非默认）2（默认）
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
    @RequestMapping(value = "/default", method = RequestMethod.POST)
    @ResponseBody
    public Packet freightTemplateDefault(HttpServletRequest request) {
        return Transform.GetResult(freightTemplateService.freightTemplateDefault(request));
    }
    
    /**
     * @api {post} /{project_name}/freight_template/warehouse_list
     * @apiName warehouse_list
     * @apiGroup freight_template
     * @apiDescription  可用仓库列表
     * @apiVersion 0.0.1
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 		仓库列表
     * @apiSuccess {list}		obj.id		仓库ID
     * @apiSuccess {list}		obj.name	仓库名称
     * 
     */
    @RequestMapping(value = "/warehouse_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryWarehouseList(HttpServletRequest request) {
        return Transform.GetResult(freightTemplateService.queryWarehouseList(request));
    }
    
}
