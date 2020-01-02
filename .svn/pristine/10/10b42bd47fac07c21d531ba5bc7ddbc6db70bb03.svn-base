package com.tk.oms.marketing.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.marketing.service.RegionProductService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;

/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : RegionalProductControl
 * 区域商品
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-4-10
 */
@Controller
@RequestMapping("/region_product")
public class RegionProductControl {
	@Resource
	private RegionProductService  regionaProductService;
	
	/**
     * @api {post} /{project_name}/region_product/list
     * @apiName list
     * @apiGroup region_product
     * @apiDescription  查询某一个地区可见的商品列表
     * @apiVersion 0.0.1
     *
     * @apiParam {number}  [pageIndex=0] 开始页码.
     * @apiParam {number}  [pageSize=10] 每页数据量.
     * @apiParam {number}  id 区域ID.


     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息.
     * @apiSuccess {object[]} obj 区域关联商品列表.
     * @apiSuccess {number} obj.id 商品ID
     * @apiSuccess {number} obj.product_img_url 商品主图路径
     * @apiSuccess {string} obj.product_name 商品名称
     * @apiSuccess {string} obj.itemnumber 货号
     *
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet list(HttpServletRequest request) {
        return Transform.GetResult(regionaProductService.queryRegionProductList(request));
    }
    /**
     * @api {post} /{project_name}/region_product/region
     * @apiName region
     * @apiGroup region_product
     * @apiDescription  根据父ID查找子节点信息
     * @apiVersion 0.0.1
     *
     * @apiParam {number} parent_id 区域父ID.
	 * @apiParam {number} level 	(活动功能接口调用)初始值0
	 * @apiParam {number} id		(活动功能接口调用)

     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息.
     * @apiSuccess {object} obj 区域信息
     * @apiSuccess {number} obj.id 区域ID
     * @apiSuccess {number} obj.parent_id 区域父ID
     * @apiSuccess {string} obj.name 区域名称
     * @apiSuccess {string} isParent 是否上级区域
     *
     */
	@RequestMapping(value = "/region", method = RequestMethod.POST)
	@ResponseBody
	public Packet region(HttpServletRequest request) {
		return Transform.GetResult(regionaProductService.queryDicRegionByParentId(request));
	}
	/**
     * @api {post} /{project_name}/region_product/whole_region
     * @apiName whole_region
     * @apiGroup region_product
     * @apiDescription  查询所有省市县数据信息
     * @apiVersion 0.0.1
     *
     * @apiParam {number} parent_id 区域父ID(必填)


     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息.
     * @apiSuccess {object} obj 区域信息
     * @apiSuccess {number} obj.id 区域ID
     * @apiSuccess {number} obj.parent_id 区域父ID
     * @apiSuccess {string} obj.name 区域名称
     * @apiSuccess {string} isParent 是否上级区域
     *
     */
	@RequestMapping(value = "/whole_region", method = RequestMethod.POST)
	@ResponseBody
	public Packet wholeRegion(HttpServletRequest request) {
		return Transform.GetResult(regionaProductService.queryDicRegionWithoutCounty());
	}
	
	/**
     * @api {post} /{project_name}/region_product/region_first
     * @apiName region_first
     * @apiGroup region_product
     * @apiDescription  查询大区及省区域
     * @apiVersion 0.0.1
     *
     * @apiParam {number} parent_id 区域父ID


     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息.
     * @apiSuccess {object} obj 区域信息
     * @apiSuccess {number} obj.id 区域ID
     * @apiSuccess {number} obj.parent_id 区域父ID
     * @apiSuccess {string} obj.name 区域名称
     * @apiSuccess {string} isParent 是否上级区域
     *
     */
	@RequestMapping(value = "/region_first", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryRegionListFirst(HttpServletRequest request) {
		return Transform.GetResult(regionaProductService.queryRegionListFirst(request));
	}
}
