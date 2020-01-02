package com.tk.store.order.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.store.order.service.StoreOrderApplyService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : OrderControl
 * 要货单、退款单申请
 * 
 * @author liujialong
 * @version 1.00
 * @date 2018-6-27
 */
@Controller
@RequestMapping("/store_order_apply")
public class StoreOrderApplyControl {
	
	@Resource
	private StoreOrderApplyService storeOrderApplyService;
	
	/**
    *
    * @api {post} /{project_name}/store_order_apply/list 要货单、退款单列表
    * @apiGroup store_order_apply
    * @apiDescription  要货单、退款单列表
    * @apiVersion 0.0.1

	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条   

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 订单列表
    * @apiSuccess {number} obj.ID    			主键ID
    * @apiSuccess {string} obj.ORDER_NUMBER     订单号
    * @apiSuccess {number} obj.STORE_ID    		店铺id
    * @apiSuccess {string} obj.STORE_NAME    	店铺名称
    * @apiSuccess {number} obj.STATE    		状态(1-待审核,2-审核通过,3-驳回,4-已完成,5-已关闭)
    * @apiSuccess {number} obj.TYPE    			类型(1-要货单,2-退货单)
    * @apiSuccess {number} obj.CREATE_USER_ID   创建人
    * @apiSuccess {string} obj.CREATE_DATE    	创建时间
    * @apiSuccess {number} obj.APPROVAL_USER_ID 审批人
    * @apiSuccess {string} obj.APPROVAL_DATE    审批时间
    * @apiSuccess {string} obj.REJECTED_REASON  驳回原因
    * @apiSuccess {string} obj.CLOSE_DATE    	关闭时间
    * @apiSuccess {string} obj.END_DATE    		完成时间
    * 
    */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStoreOrderApplyList(HttpServletRequest request) {
		return Transform.GetResult(storeOrderApplyService.queryStoreOrderApplyList(request));
	}
	
	/**
    *
    * @api {post} /{project_name}/store_order_apply/detail 要货单、退款单详情
    * @apiGroup store_order_apply
    * @apiDescription  要货单、退款单详情
    * @apiVersion 0.0.1

	* @apiParam {string} order_number	订单号
	* @apiParam {number} type			类型(1-要货单,2-退货单)

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object} obj 订单详情
    * @apiSuccess {object} base_info 要货单、退款单基本信息详情
    * @apiSuccess {number} obj.base_info.ID    			主键ID
    * @apiSuccess {string} obj.base_info.ORDER_NUMBER     订单号
    * @apiSuccess {number} obj.base_info.STORE_ID    		店铺id
    * @apiSuccess {string} obj.base_info.STORE_NAME    	店铺名称
    * @apiSuccess {number} obj.base_info.STATE    		状态(1-待审核,2-审核通过,3-驳回,4-已完成,5-已关闭)
    * @apiSuccess {number} obj.base_info.TYPE    			类型(1-要货单,2-退货单)
    * @apiSuccess {number} obj.base_info.CREATE_USER_ID   创建人
    * @apiSuccess {string} obj.base_info.CREATE_DATE    	创建时间
    * @apiSuccess {number} obj.base_info.APPROVAL_USER_ID 审批人
    * @apiSuccess {string} obj.base_info.APPROVAL_DATE    审批时间
    * @apiSuccess {string} obj.base_info.REJECTED_REASON  驳回原因
    * @apiSuccess {string} obj.base_info.CLOSE_DATE    	关闭时间
    * @apiSuccess {string} obj.base_info.END_DATE    		完成时间
    * @apiSuccess {object[]} product_sku_info 要货单、退款单商品sku信息列表
    * @apiSuccess {number} obj.product_sku_info.ID    				主键ID
    * @apiSuccess {string} obj.product_sku_info.ORDER_NUMBER    	订单号
    * @apiSuccess {string} obj.product_sku_info.PRODUCT_SKU    	商品sku
    * @apiSuccess {string} obj.product_sku_info.PRODUCT_ITEMNUMBER 货号
    * @apiSuccess {string} obj.product_sku_info.PRODUCT_COLOR    	商品颜色
    * @apiSuccess {string} obj.product_sku_info.CODENUMBER    		码号
    * @apiSuccess {number} obj.product_sku_info.COUNT    			要(退)货数
    * @apiSuccess {string} obj.product_sku_info.RETAIL_PRICE    	零售价
    * @apiSuccess {object[]} product_total_money 要货单、退款单总金额
    * @apiSuccess {string} obj.product_total_money.product_total_money    要货单、退款单总金额
    */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStoreOrderApplyDetail(HttpServletRequest request) {
		return Transform.GetResult(storeOrderApplyService.queryStoreOrderApplyDetail(request));
	}
	
	/** 
	* @api {post} /{project_name}/store_order_apply/confirm 查询要货单、退货单确认完成
    * @apiGroup store_order_apply
    * @apiDescription  查询要货单、退货单确认完成
    * @apiVersion 0.0.1
    * 
	* @apiParam {string} order_number		订单号
	* @apiParam {number} type				类型(1-要货单,2-退货单)
	* 
    * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    */
	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	@ResponseBody
	public Packet storeOrderApplyConfirm(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=storeOrderApplyService.storeOrderApplyConfirm(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
	
	/** 
	* @api {post} /{project_name}/store_order_apply/confirm 查询要货单、退货单审批
    * @apiGroup store_order_apply
    * @apiDescription  查询要货单、退货单审批
    * @apiVersion 0.0.1
    * 
	* @apiParam {string} order_number		订单号
	* @apiParam {number} state				订单状态(2-审核通过,3-驳回)
	* @apiParam {string} rejected_reason	驳回原因(驳回时传驳回原因)
	* @apiParam {number} type				类型(1-要货单,2-退货单)
	* 
    * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    */
	@RequestMapping(value = "/approval", method = RequestMethod.POST)
	@ResponseBody
	public Packet storeOrderApplyApproval(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=storeOrderApplyService.storeOrderApplyApproval(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
	
	/**
    *
    * @api {post} /{project_name}/store_order_apply/query_store_list 按照登入用户查询门店列表
    * @apiGroup store_order_apply
    * @apiDescription 按照登入用户查询门店列表
    * @apiVersion 0.0.1

	* @apiParam {number} public_user_type		登入用户类型
	* @apiParam {number} public_user_id			登入用户id

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 订单列表
    * @apiSuccess {number} obj.id   		门店id
    * @apiSuccess {string} obj.option   	门店名称
    * 
    * 
    */
	@RequestMapping(value = "/query_store_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStoreList(HttpServletRequest request) {
		return Transform.GetResult(storeOrderApplyService.queryStoreList(request));
	}

}
