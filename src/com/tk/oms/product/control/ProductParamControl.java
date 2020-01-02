package com.tk.oms.product.control;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.product.service.ProductParamService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;


/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : ProductParamControl
 * 商品参数控制类
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-4-6
 */
@Controller
@RequestMapping("/product_param")
public class ProductParamControl {
	@Resource
    private ProductParamService productParamService;
	/**
     * @api {post} /{project_name}/product_param/list
     * @apiName list
     * @apiGroup product_param
     * @apiDescription  查询商品参数信息
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} pageIndex 		开始页码
     * @apiParam {number} pageSize 			每页数据量
     * @apiParam {number} product_type_id 商品分类ID
     * @apiParam {string} parameter_name  参数名称
     * @apiParam {string} parameter_type  参数类型
     * @apiParam {string} parameter_values 参数值
     * @apiParam {char}   required_flag  是否必填：1，不必填 2，必填
     * @apiParam {char}   is_display 是否显示：0，不显示 1，显示
     * @apiParam {number} id  商品参数ID
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 商品参数列表
     * @apiSuccess {number}     total 商品参数数据总数
     * @apiSuccess {number}   	obj.id 商品参数ID
     * @apiSuccess {number}   	obj.product_type_id 商品分类ID
     * @apiSuccess {string}   	obj.parameter_name 参数名称
     * @apiSuccess {string}   	obj.parameter_type 参数类型
     * @apiSuccess {string}   	obj.parameter_values 参数值
     * @apiSuccess {char}   	obj.required_flag 是否必填：1，不必填。2，必填
     * @apiSuccess {date}     	obj.create_date 分类创建日期
     * @apiSuccess {char}       obj.is_display 是否显示：1，不显示 2，显示
     * 
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductParamList(HttpServletRequest request) {
        return Transform.GetResult(productParamService.queryProductParamList(request));
    }
    /**
     * @api {post} /{project_name}/product_param/add
     * @apiName add
     * @apiGroup product_param
     * @apiDescription  新增商品参数
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} product_type_id 商品分类ID
     * @apiParam {string} parameter_name  参数名称
     * @apiParam {string} parameter_type  参数类型
     * @apiParam {string} parameter_values 参数值
     * @apiParam {char}   required_flag 是否必填：1，不必填。2，必填
     * @apiParam {char}   is_display 是否显示：1，不显示 2，显示
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   total 成功数量
     * 
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addProductParam(HttpServletRequest request) {
        return Transform.GetResult(productParamService.addProductParam(request));
    }
    /**
     * @api {post} /{project_name}/product_param/edit
     * @apiName edit
     * @apiGroup product_param
     * @apiDescription  更新商品参数
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id 商品参数ID
     * @apiParam {number} product_type_id 商品分类ID
     * @apiParam {string} parameter_name  参数名称
     * @apiParam {string} parameter_type  参数类型
     * @apiParam {string} parameter_values 参数值
     * @apiParam {char}   required_flag   是否必填：1，不必填。2，必填
     * @apiParam {char}   is_display 是否显示：1，不显示 2，显示
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   total 成功数量
     * 
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editProductParam(HttpServletRequest request) {
        return Transform.GetResult(productParamService.editProductParam(request));
    }
    /**
     * @api {post} /{project_name}/product_param/remove
     * @apiName remove
     * @apiGroup product_param
     * @apiDescription  删除商品参数
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id 商品参数ID
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   total 成功数量
     * 
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeProductParam(HttpServletRequest request) {
        return Transform.GetResult(productParamService.removeProductParam(request));
    }
    
    /**
     * @api {post} /{project_name}/product_param/info_list
     * @apiName info_list
     * @apiGroup product_param
     * @apiDescription  查询分类对应的所有商品参数信息
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} product_type_id 商品分类ID
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 商品参数列表
     * @apiSuccess {number}     total 商品参数数据总数
     * @apiSuccess {number}   	obj.id 商品参数ID
     * @apiSuccess {number}   	obj.product_type_id 商品分类ID
     * @apiSuccess {string}   	obj.parameter_name 参数名称
     * @apiSuccess {string}   	obj.parameter_type 参数类型
     * @apiSuccess {string}   	obj.parameter_values 参数值
     * @apiSuccess {char}   	obj.required_flag 是否必填：1，不必填。2，必填
     * @apiSuccess {date}     	obj.create_date 分类创建日期
     * @apiSuccess {char}       obj.is_display 是否显示：1，不显示 2，显示
     * 
     */
    @RequestMapping(value = "/info_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductParamInfo(HttpServletRequest request) {
        return Transform.GetResult(productParamService.queryProductParamInfo(request));
    }
    
    /**
     * @api {post} /{project_name}/product_param/detail
     * @apiName detail
     * @apiGroup product_param
     * @apiDescription  商品参数详情
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id  商品参数ID
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object}     obj 商品参数详情
     * @apiSuccess {number}   	obj.id 商品参数ID
     * @apiSuccess {number}   	obj.product_type_id 商品分类ID
     * @apiSuccess {string}   	obj.parameter_name 参数名称
     * @apiSuccess {string}   	obj.parameter_type 参数类型
     * @apiSuccess {string}   	obj.parameter_values 参数值
     * @apiSuccess {char}   	obj.required_flag 是否必填：1，不必填。2，必填
     * @apiSuccess {char}       obj.is_display 是否显示：1，不显示 2，显示
     * 
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductParamDetail(HttpServletRequest request) {
        return Transform.GetResult(productParamService.queryProductParamDetail(request));
    }
    
    /**
     * @api {post} /{project_name}/product_param/sort
     * @apiName sort
     * @apiGroup product_param
     * @apiDescription  排序
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id1  第一个参数id
     * @apiParam {number} id2  第二个参数id
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
    @RequestMapping(value = "/sort", method = RequestMethod.POST)
    @ResponseBody
    public Packet sortProductParam(HttpServletRequest request) {
        return Transform.GetResult(productParamService.sortProductParam(request));
    }
    
}
