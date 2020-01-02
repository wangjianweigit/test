package com.tk.oms.product.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.product.service.ProductSameService;
import com.tk.oms.product.service.ProductService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Controller
@RequestMapping("/product_same")
public class ProductSameControl {
	
	@Resource
	private ProductSameService productSameService;
	
	
	/**
    *
    * @api {post} /{project_name}/product_same/list 同款商品列表
    * @apiGroup product_same
    * @apiDescription  同款商品列表
    * @apiVersion 0.0.1

	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条   

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 商品列表
    * @apiSuccess {number} obj.CID		品牌ID
	* @apiSuccess {number} obj.BRAND_ID		品牌ID
	* @apiSuccess {number} obj.PRODUCT_TYPE_ID			商品类别ID  
	* @apiSuccess {string} obj.ITEMNUMBER			货号   
	* @apiSuccess {string} obj.PRODUCT_NAME		商品名称,不允许重名      
	* @apiSuccess {number} obj.YEAR		年份
	* @apiSuccess {string} obj.SEASON	季节
	* @apiSuccess {string} obj.PRODUCT_IMG_URL	商品图片
	* @apiSuccess {object[]} obj.dataPid	子商品信息
    */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryProductSameList(HttpServletRequest request) {
		return Transform.GetResult(productSameService.queryProductSameList(request));
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryProductSameDetail(HttpServletRequest request) {
		return Transform.GetResult(productSameService.productSameDetail(request));
	}
	
	/** 
	* @api {post} /{project_name}/product_same/edit 同款商品编辑
    * @apiGroup product_same
    * @apiDescription  同款商品编辑
    * @apiVersion 0.0.1
    * 
	* @apiParam {string} itemnumber				货号 
	* @apiParam {string} parentItemnumber		父级货号 
    * @apiSuccess {boolean} state 接口审核驳回是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Packet productSameEdit(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=productSameService.productSameEdit(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
	
	/** 
	* @api {post} /{project_name}/product_same/add 同款商品新增
    * @apiGroup product_same
    * @apiDescription  同款商品新增
    * @apiVersion 0.0.1
    * 
	* @apiParam {string} itemnumber				货号 
	* @apiParam {string} parentItemnumber		父级货号 
    * @apiSuccess {boolean} state 接口审核驳回是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Packet productSameAdd(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=productSameService.productSameAdd(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
	/** 
	* @api {post} /{project_name}/product_same/remove 同款商品删除
    * @apiGroup product_same
    * @apiDescription  同款商品删除
    * @apiVersion 0.0.1
    * 
	* @apiParam {string} itemnumber				货号 
    * @apiSuccess {boolean} state 接口审核驳回是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    */
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	@ResponseBody
	public Packet productSameRemove(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=productSameService.productSameRemove(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
	
	
	/** 
	* @api {post} /{project_name}/product_same/judge 同款商品添加条件判断(有相同的颜色和尺码不能设为同款)
    * @apiGroup product_same
    * @apiDescription  同款商品添加条件判断(有相同的颜色和尺码不能设为同款)
    * @apiVersion 0.0.1
    * 
	* @apiParam {string} itemnumber				货号 
	* @apiParam {string} parentItemnumber		父级货号  
    * @apiSuccess {boolean} state 接口审核驳回是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    */
	@RequestMapping(value = "/judge", method = RequestMethod.POST)
	@ResponseBody
	public Packet productSameJudge(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=productSameService.productSameJudge(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}

}
