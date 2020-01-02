package com.tk.oms.marketing.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.marketing.service.ProductSortService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : ProductSortControl
 * 商品搜索排序策略
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-4-17
 */
@Controller
@RequestMapping("product_sort")
public class ProductSortControl {
	@Resource
	private ProductSortService productSortService;
	
	/**
     * @api {post} /{project_name}/product_sort/edit
     * @apiGroup edit
     * @apiName product_sort
     * @apiDescription  编辑规则权重百分比
     * @apiVersion 0.0.1
     *
     * @apiParam {string} code 				规则代码
     * @apiParam {number} weight_percent 	权重占比（百分比）
     *
     * @apiSuccess {boolean} state  接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * 
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editWeightPercent(HttpServletRequest request) {
        return Transform.GetResult(productSortService.editWeightPercent(request));
    }

    /**
     * @api {post} /{project_name}/product_sort/list
     * @apiGroup list
     * @apiName product_sort
     * @apiDescription  查询规则列表
     * @apiVersion 0.0.1
     *
     *
     * @apiSuccess {boolean}  state  接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}   message 接口返回信息.
     * @apiSuccess {object[]} obj     	   规则列表
     * @apiSuccess {string}   obj.code   规则代码
     * @apiSuccess {string}   obj.name   规则名称
     * @apiSuccess {number}   obj.weight_percent  权重占比（百分比）
     * 
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySortRole(HttpServletRequest request) {
        return Transform.GetResult(productSortService.querySortRole(request));
    }

    /**
     * @api {post} /{project_name}/product_sort/product_list
     * @apiGroup product_list
     * @apiName product_sort
     * @apiDescription  查询人工加权商品列表
     * @apiVersion 0.0.1
     *
     * @apiParam {number} pageIndex 开始页码
     * @apiParam {number} pageSize  每页数据量
     * @apiParam {string} site_id	站点ID
     * @apiParam {string} product_itemnumber 货号
     *
     * @apiSuccess {boolean}  state  	   接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}   message 	   接口返回信息
     * @apiSuccess {object[]} obj     	   人工加权商品列表
     * @apiSuccess {string}   obj.product_itemnumber   	货号
     * @apiSuccess {string}   obj.product_img_url      	商品主图路径
     * @apiSuccess {number}   obj.weighting  		   	实际加权值
     * @apiSuccess {number}   obj.temp_weighting  	   	暂加权值
     * @apiSuccess {number}   obj.weight_value			分值
     * @apiSuccess {number}   obj.temp_weight_value		加权后值
     * @apiSuccess {number}   obj.sort_change			预计排名(提升，下降)
     * 
     */
    @RequestMapping(value = "/product_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductList(HttpServletRequest request) {
        return Transform.GetResult(productSortService.queryProductList(request));
    }

    /**
     * @api {post} /{project_name}/product_sort/weighting
     * @apiGroup weighting
     * @apiName product_sort
     * @apiDescription  编辑商品人工加权
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} create_user_id	入驻商创建ID
     * @apiParam {string} site_id	站点ID
     * @apiParam {string} product_itemnumber 货号
     * @apiParam {number} temp_weighting	  暂加权值
     *
     * @apiSuccess {boolean}  state  	   接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}   message 	   接口返回信息
     * 
     */
    @RequestMapping(value = "/weighting", method = RequestMethod.POST)
    @ResponseBody
    public Packet editProductWeighting(HttpServletRequest request) {
        return Transform.GetResult(productSortService.editProductWeighting(request));
    }

    /**
     * @api {post} /{project_name}/product_sort/weighting_release
     * @apiGroup weighting_release
     * @apiName product_sort
     * @apiDescription  发布人工加权
     * @apiVersion 0.0.1
     *
     * @apiParam {string} site_id	站点ID
     *
     * @apiSuccess {boolean}  state  	   接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}   message 	   接口返回信息
     * 
     */
    @RequestMapping(value = "/weighting_release", method = RequestMethod.POST)
    @ResponseBody
    public Packet releaseProductWeighting(HttpServletRequest request) {
        return Transform.GetResult(productSortService.releaseProductWeighting(request));
    }
    
    /**
     * @api {post} /{project_name}/product_sort/newProductList
     * @apiGroup newProductList
     * @apiName product_sort
     * @apiDescription  上新商品查询上新商品列表
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} pageIndex 开始页码
     * @apiParam {number} pageSize  每页数据量
     * @apiParam {string} itemnumber货号
     * @apiParam {number} year 年份
     * @apiParam {number} season_id 季节id
     *
     * @apiSuccess {boolean}  state  	   接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}   message 	   接口返回信息
     * @apiSuccess {object[]} obj     	   上新商品列表
     * @apiSuccess {number}   obj.ID   					主键ID
     * @apiSuccess {string}   obj.ITEMNUMBER   			货号
     * @apiSuccess {string}   obj.PRODUCT_IMG_URL      	商品主图路径
     * @apiSuccess {number}   obj.PRODUCT_NAME  		商品名称
     * @apiSuccess {number}   obj.PRODUCT_COUNT  	   	 商品 销售量
     * @apiSuccess {number}   obj.IS_OUTSTOCK			是否支持缺货订购（0：不支持，1：支持）
     *
     */
    @RequestMapping(value = "/new_product_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet newProductList(HttpServletRequest request) {
        return Transform.GetResult(productSortService.newProductList(request));
    }
    
    /**
     * @api {post} /{project_name}/product_sort/new_product_sort
     * @apiGroup new_product_sort
     * @apiName product_sort
     * @apiDescription  上新商品排序
     * @apiVersion 0.0.1
     *
     * @apiParam {string} type	排序类型(top 置顶  buttom 置尾  prev 上升  next 下降)
     * @apiParam {number} product_id   商品id	
     * @apiParam {number} rise   上升值（上升排序时必传）	
     * @apiParam {number} down   下降值（下降排序时必传）
     *
     * @apiSuccess {boolean}  state  	   接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}   message 	   接口返回信息
     * 
     */
    @RequestMapping(value = "/new_product_sort", method = RequestMethod.POST)
	@ResponseBody
	public Packet newProductSort(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=productSortService.newProductSort(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
}
