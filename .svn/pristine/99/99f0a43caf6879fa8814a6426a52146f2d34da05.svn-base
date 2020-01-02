package com.tk.oms.marketing.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.marketing.service.ActivityProductDiscountService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : ActivityProductDiscountControl
 * 促销活动商品优惠管理
 * 
 * @author shifan
 * @version 1.00
 * @date 2017-9-26
 */
@Controller
@RequestMapping("/activity_product_discount")
public class ActivityProductDiscountControl {

	@Resource
	private ActivityProductDiscountService activityProductDiscountService;
	

	/**
    *
    * @api {post} /{project_name}/activity_product_discount/list 活动商品已设置的阶梯优惠列表
    * @apiGroup activity_product
    * @apiDescription  活动商品已设置的阶梯优惠列表
    * @apiVersion 0.0.1

	* @apiParam {number} activity_id		                活动id 
	* @apiParam {number} activity_product_id		活动商品id 
	* @apiParam {string} product_itemnumber			活动商品货号

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 已参与活动的商品列表
    * @apiSuccess {number} obj.ID    主键ID
    * @apiSuccess {number} obj.STATIONED_USER_ID    所属入驻商ID，表TBL_STATIONED_USER_INFO主键
    * @apiSuccess {number} obj.ACTIVITY_ID    活动ID
    * @apiSuccess {string} obj.PRODUCT_ITEMNUMBER    商品货号
    * @apiSuccess {string} obj.PRODUCT_IMG_URL    商品图片，参加活动的商品可自定义主图
    * @apiSuccess {number} obj.ACTIVITY_DISCOUNT    折扣率 示例（1为没有折扣，0.9为9折）
    * @apiSuccess {string} obj.CREATE_DATE    创建时间
    * @apiSuccess {number} obj.CREATE_USER_ID    创建用户ID
    */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryActivityProductList(HttpServletRequest request) {
		return Transform.GetResult(activityProductDiscountService.queryActivityProductDiscountList(request));
	}
	
	/** 
	* @api {post} /{project_name}/activity_product_discount/save 活动商品强制退出
    * @apiGroup activity_product
    * @apiDescription  活动商品排序
    * @apiVersion 0.0.1
    * 
	* @apiParam {number} activity_product_id		活动商品id
    * @apiSuccess {boolean} state 接口审核驳回是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Packet saveActivityProductDiscount(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=activityProductDiscountService.saveActivityProductDiscount(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
}