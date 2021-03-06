package com.tk.oms.product.control;

import com.tk.oms.product.service.ProductAnalyseService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
/**
 * 
 * Copyright (c), 2018, Tongku
 * FileName : ProductAnalyseControl.java
 * 商品信息分析查询
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018年5月15日
 */
@Controller
@RequestMapping("/product_analyse")
public class ProductAnalyseControl {
	@Resource
	private ProductAnalyseService productAnalyseService;
	
	/**
    *
    * @api {post} /{project_name}/product/list 商品列表
    * @apiGroup product
    * @apiDescription  商品信息分析查询列表
    * @apiVersion 0.0.1

	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条   

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 商品列表
    * @apiSuccess {string} obj.itemnumber			货号   
    * @apiSuccess {number} obj.main_img_count	主图数量
    * @apiSuccess {number} obj.color_img_count	颜色图数量
    * @apiSuccess {number} obj.detail_img_count	详情图数量
    * @apiSuccess {number} obj.video_count		视频数量
	* @apiSuccess {number} obj.state  			上架状态（待上架、上架、下架） 
	* @apiSuccess {number} obj.is_outstock		是否支持缺货订购（0：不支持，1：支持）
	* @apiSuccess {number} obj.is_custom		是否支持定制（0:不支持，1:支持）
	* @apiSuccess {number} obj.is_in_stock		是否已入库（是/否）
	* 
    */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryProductAnalyseListForPage(HttpServletRequest request) {
		return Transform.GetResult(productAnalyseService.queryProductAnalyseListForPage(request));
	}
}
