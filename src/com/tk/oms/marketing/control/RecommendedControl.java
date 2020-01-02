package com.tk.oms.marketing.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.marketing.service.RecommendedService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : RecommendedControl
 * 为你推荐管理
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-4-12
 */
@Controller
@RequestMapping("/recommended")
public class RecommendedControl {
	@Resource
    private RecommendedService recommendedService;
	
	
	/**
     * @api {post} /{project_name}/recommended/list
     * @apiName list
     * @apiGroup recommended
     * @apiDescription  查询推荐列表
     * @apiVersion 0.0.1
     *
     * @apiParam {number} pageIndex 开始页码.
     * @apiParam {number} pageSize  每页数据量.
     * @apiParam {string} name 推荐名称
     * @apiParam {date} start_create_date 开始创建时间
     * @apiParam {date} end_create_date 结束创建时间
     * @apiParam {string} state 停用状态 1:停用,2:启用


     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}  message 接口返回信息.
     * @apiSuccess {object[]} obj 推荐列表
     * @apiSuccess {number} total 推荐总数
     * @apiSuccess {number} obj.id 推荐ID
     * @apiSuccess {string} obj.name 推荐名称
     * @apiSuccess {date}   obj.start_date 推荐开始时间
     * @apiSuccess {char}   obj.state 停用状态 1:停用,2:启用
     * @apiSuccess {char}   obj.type  显示方式
     * @apiSuccess {string} obj.remark 备注信息
     * @apiSuccess {date}   obj.create_date 创建时间 yyyy-MM-dd HH:mm:ss格式
     * @apiSuccess {number} obj.create_user_id 创建 人ID
     *
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRecommendedList(HttpServletRequest request) {
        return Transform.GetResult(recommendedService.queryRecommendedList(request));
    }
    /**
     * @api {post} /{project_name}/recommended/add
     * @apiName add
     * @apiGroup recommended
     * @apiDescription  新增推荐
     * @apiVersion 0.0.1
     *
     * @apiParam {string} name 推荐名称
     * @apiParam {date}   start_date 推荐开始时间
	 * @apiParam {char}   type 显示方式
	 * @apiParam {string} remark 备注信息
	 * @apiParam {number} create_user_id 创建 人ID
	 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}  message 接口返回信息.
     * @apiSuccess {object[]} 
     *
     */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addRecommended(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
    	try {
    		pr =  recommendedService.addRecommended(request);
		}catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
    	return Transform.GetResult(pr);
    }
	/**
     * @api {post} /{project_name}/recommended/edit
     * @apiName edit
     * @apiGroup recommended
     * @apiDescription  编辑推荐信息
     * @apiVersion 0.0.1
     *
     *
     * @apiParam {number} id 推荐id
     * @apiParam {string} name 推荐名称
     * @apiParam {date}   start_date 推荐开始时间
     * @apiParam {char}   state 停用状态 1:停用,2:启用
	 * @apiParam {char}   type 显示方式
	 * @apiParam {string} remark 备注信息
	 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}  message 接口返回信息.
     * @apiSuccess {object[]} 
     *
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editRecommended(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
    	try {
    		pr =  recommendedService.editRecommended(request);
    	}catch (Exception e) {
    		pr.setState(false);
    		pr.setMessage(e.getMessage());
    	}
    	return Transform.GetResult(pr);
    }
    
    /**
     * @api {post} /{project_name}/recommended/remove
     * @apiName remove
     * @apiGroup recommended
     * @apiDescription  删除推荐信息
     * @apiVersion 0.0.1
     *
     * @apiParam {number} id 推荐id
	 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}  message 接口返回信息.
     * @apiSuccess {object[]} 
     *
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeRecommended(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = recommendedService.removeRecommended(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    /**
     * @api {post} /{project_name}/recommended/detail
     * @apiName add
     * @apiGroup recommended
     * @apiDescription  查询推荐详情
     * @apiVersion 0.0.1
     *
     * @apiParam {number} id 推荐id
	 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}  message 接口返回信息.
     * @apiSuccess {object}  obj
     * @apiSuccess {number} obj.id 推荐ID
     * @apiSuccess {string} obj.name 推荐名称
     * @apiSuccess {date}   obj.start_date 推荐开始时间
     * @apiSuccess {char}   obj.state 停用状态 1:停用,2:启用
     * @apiSuccess {char}   obj.type  显示方式
     * @apiSuccess {string} obj.remark 备注信息
     * @apiSuccess {date}   obj.create_date 创建时间 yyyy-MM-dd HH:mm:ss格式
     * @apiSuccess {number} obj.create_user_id 创建 人ID
     *
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet detailRecommended(HttpServletRequest request) {
    	return Transform.GetResult(recommendedService.detailRecommended(request));
    }
    /**
     * @api {post} /{project_name}/recommended/product_list
     * @apiName product_list
     * @apiGroup recommended
     * @apiDescription  查询推荐的商品列表
     * @apiVersion 0.0.1
     *
     * @apiParam {number} id 推荐id
	 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}  message 接口返回信息.
     * @apiSuccess {object[]}  obj 推荐的商品列表
     * @apiSuccess {number} obj.recommended_id 推荐ID
     * @apiSuccess {string} obj.product_itemnumber 商品货号
     * @apiSuccess {number} obj.sort 排序
     * @apiSuccess {string} obj.product_img_url 商品主图路径
     *
     */
    @RequestMapping(value = "/product_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRecommendedProductList(HttpServletRequest request) {
    	return Transform.GetResult(recommendedService.queryRecommendedProductList(request));
    }
    
    /**
     * @api {post} /{project_name}/recommended/update_state
     * @apiName update_state
     * @apiGroup recommended
     * @apiDescription  启用禁用推荐信息
     * @apiVersion 0.0.1
     * @apiParam {number} id 推荐id
	 * @apiParam {String} state 状态
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}  message 接口返回信息.
     * @apiSuccess {object[]} 
     *
     */
    @RequestMapping(value = "/update_state", method = RequestMethod.POST)
    @ResponseBody
    public Packet updateRecommendState(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = recommendedService.updateRecommendState(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
}
