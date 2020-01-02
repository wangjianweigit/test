package com.tk.store.product.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.store.product.service.StoreProductService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : ProductControl
 * 商品管理(店铺)
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018-2-27
 */
@Controller
@RequestMapping("/store_product")
public class StoreProductControl {
	@Resource
	private StoreProductService storeProductService;
	/**
    *
    * @api {post} /{project_name}/store_product/list 商品列表
    * @apiGroup store_product
    * @apiName list
    * @apiDescription  商品列表
    * @apiVersion 0.0.1
	
	* @apiParam {number} user_id				门店ID
	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条   

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 商品列表
    * @apiSuccess {string} obj.itemnumber			货号
	* @apiSuccess {number} obj.brand				品牌
	* @apiSuccess {string} obj.product_name			商品名称
	* @apiSuccess {string} obj.season				季节
	* @apiSuccess {number} obj.year					年份
	* @apiSuccess {number} obj.product_type_id		商品类别ID(暂时不用)
	* @apiSuccess {string} obj.product_img_url		商品主图路径
	* @apiSuccess {string} obj.update_time			更新日期
	* @apiSuccess {string} obj.min_price			最小零售价
	* @apiSuccess {string} obj.max_price			最大零售价
	* @apiSuccess {number} obj.state				上下架状态(1上架/2下架/3待上架)
    */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryProductListForPage(HttpServletRequest request) {
		return Transform.GetResult(storeProductService.queryProductListForPage(request));
	}
	
	/**
	 * 是否销售
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update_state", method = RequestMethod.POST)
	@ResponseBody
	public Packet updateStoreProductState(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = storeProductService.updateStoreProductState(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	/**
	 * 商品库列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/library_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryProductLibraryListForPage(HttpServletRequest request) {
		return Transform.GetResult(storeProductService.queryProductLibraryListForPage(request));
	}
	
	/**
	 * 导入商品
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Packet insertStoreProduct(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = storeProductService.insertStoreProduct(request);
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
		return Transform.GetResult(storeProductService.queryProductRetailDetail(request));
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
			pr = storeProductService.updateRetailPrice(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
	 * 查询所属商家
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/user_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryUserListByItemnumber(HttpServletRequest request) {
		return Transform.GetResult(storeProductService.queryUserListByItemnumber(request));
	}
	
	/**
	 * 编辑所属商家
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/user_edit", method = RequestMethod.POST)
	@ResponseBody
	public Packet editUser(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = storeProductService.editUser(request);
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
			pr = storeProductService.addBatch(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
}
