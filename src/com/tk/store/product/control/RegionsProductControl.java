package com.tk.store.product.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.store.product.service.RegionsProductService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2018, Tongku
 * FileName : RegionProductControl.java
 * 区域商品管理
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018年5月16日
 */
@Controller
@RequestMapping("/regions_product")
public class RegionsProductControl {
	@Resource
	private RegionsProductService regionsProductService;
	/**
    *
    * @api {post} /{project_name}/regions_product/list 商品列表
    * @apiGroup regions_product
    * @apiName list
    * @apiDescription  商品列表
    * @apiVersion 0.0.1
	
	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条   

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 商品列表
    * @apiSuccess {string} obj.product_itemnumber			货号
	* @apiSuccess {number} obj.brand				品牌
	* @apiSuccess {string} obj.product_name			商品名称
	* @apiSuccess {string} obj.season				季节
	* @apiSuccess {number} obj.year					年份
	* @apiSuccess {number} obj.product_type			商品类别
	* @apiSuccess {string} obj.product_img_url		商品主图路径
	* @apiSuccess {string} obj.create_date			导入时间
	* @apiSuccess {string} obj.min_price			最小零售价
	* @apiSuccess {string} obj.max_price			最大零售价
	* @apiSuccess {number} obj.state				是否销售，1是、2否
	* @apiSuccess {string} obj.product_region		所属区域
    */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryProductListForPage(HttpServletRequest request) {
		return Transform.GetResult(regionsProductService.queryProductListForPage(request));
	}
	
	/**
	 * 商品库列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/library_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryProductLibraryListForPage(HttpServletRequest request) {
		return Transform.GetResult(regionsProductService.queryProductLibraryListForPage(request));
	}
	
	/**
	 * 导入商品
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Packet insertRegionProduct(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = regionsProductService.insertRegionProduct(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	/**
	 * 商品零售详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/retail_detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryProductRetailDetail(HttpServletRequest request) {
		return Transform.GetResult(regionsProductService.queryProductRetailDetail(request));
	}
	
	/**
	 * 设置零售价
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/retail_price", method = RequestMethod.POST)
	@ResponseBody
	public Packet updateRetailPrice(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = regionsProductService.updateRetailPrice(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	/**
	 * 所属区域下拉框
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/user_select", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryUserSelect(HttpServletRequest request) {
		return Transform.GetResult(regionsProductService.queryUserSelect(request));
	}
	/**
	 * 查询所属区域
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/user_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryUserList(HttpServletRequest request) {
		return Transform.GetResult(regionsProductService.queryUserList(request));
	}
	
	/**
	 * 编辑所属区域
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/user_edit", method = RequestMethod.POST)
	@ResponseBody
	public Packet editUser(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = regionsProductService.editUser(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
	 * 批量导入
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/batch_add", method = RequestMethod.POST)
	@ResponseBody
	public Packet addBatch(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = regionsProductService.addBatch(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
    *
    * @api {post} /{project_name}/regions_product/region_list 查询区域商品列表
    * @apiGroup regions_product
    * @apiName region_list
    * @apiDescription  查询区域商品列表
    * @apiVersion 0.0.1
	
	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条   

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 商品列表
    * @apiSuccess {string} obj.product_itemnumber			货号
	* @apiSuccess {number} obj.brand				品牌
	* @apiSuccess {string} obj.product_name			商品名称
	* @apiSuccess {string} obj.season				季节
	* @apiSuccess {number} obj.year					年份
	* @apiSuccess {number} obj.product_type			商品类别
	* @apiSuccess {string} obj.product_img_url		商品主图路径
	* @apiSuccess {string} obj.create_date			导入时间
	* @apiSuccess {string} obj.min_price			最小零售价
	* @apiSuccess {string} obj.max_price			最大零售价
	* @apiSuccess {number} obj.state				是否销售，1是、2否
	* @apiSuccess {string} obj.product_region		所属区域
    */
	@RequestMapping(value = "/region_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryProductList(HttpServletRequest request) {
		return Transform.GetResult(regionsProductService.queryProductList(request));
	}
	
}
