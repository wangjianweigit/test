package com.tk.store.order.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.store.order.service.StoreOrderService;
import com.tk.store.order.service.StoreTransactService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;

@Controller
@RequestMapping("/store_transact")
public class StoreTransactControl {
	
	@Resource
	private StoreTransactService storeTransactService;
	
	@RequestMapping(value = "/syy_select_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet storeTranscatSyySelectList(HttpServletRequest request) {
		return Transform.GetResult(storeTransactService.storeTranscatSyySelectList(request));
	}
	
	/**
    *
    * @api {post} /{project_name}/store_transact/sales_order_list 销售订单列表
    * @apiGroup store_order
    * @apiDescription  销售订单列表
    * @apiVersion 0.0.1

	* @apiParam {number} AGENT_ID				经销商的ID 
	* @apiParam {string} ORDER_ID				订单号 
	* @apiParam {number} STORE_SALES_ID			导购员id 
	* @apiParam {number} STAFF_ID				收银员id
	* @apiParam {string} MIN_DATE				下单时间起（年月日格式） 
	* @apiParam {string} MAX_DATE				下单时间止（年月日格式）
	* @apiParam {string} ORDER_STATE_NORMAL		订单状态 1=待付款,2=已付款,3=待收货,4=交易完成,6=交易关闭，7=退款订单 (多个","分割)

    * @apiSuccess {boolean} state 接口获取数据是否成功.1:成功  0:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {number} total 总条数
    * @apiSuccess {object[]} obj 订单列表
    * @apiSuccess {string} obj.ORDER_STATE_NORMAL    订单状态 1=待付款/待付款,2=待发货/已付款,3=已发货/待收货,4=已确认收货/交易完成,6=交易关闭
    * @apiSuccess {string} obj.ID    订单号
    * @apiSuccess {number} obj.SKU_NUM    商品数
    * @apiSuccess {number} obj.ORDER_PRICE    订单金额
    * @apiSuccess {string} obj.STORE_SALES_NAME   导购
    * @apiSuccess {string} obj.STAFF_NAME    收银
    * @apiSuccess {string} obj.ORDER_CREATE_TIME  下单时间
    */
	@RequestMapping(value = "/sales_order_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet storeTranscatSalesList(HttpServletRequest request) {
		return Transform.GetResult(storeTransactService.storeTranscatSalesList(request));
	}
	
	@RequestMapping(value = "/sales_order_detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet storeTranscatSalesDetail(HttpServletRequest request) {
		return Transform.GetResult(storeTransactService.storeTranscatSalesDetail(request));
	}
	
	/**
    *
    * @api {post} /{project_name}/store_transact/returns_order_list 售后订单列表
    * @apiGroup store_order
    * @apiDescription  售后订单列表
    * @apiVersion 0.0.1

	* @apiParam {number} AGENT_ID				经销商的ID 
	* @apiParam {string} ORDER_ID				订单号 
	* @apiParam {string} REFUND_ID				退款单号 
	* @apiParam {number} STAFF_ID				收银员id
	* @apiParam {string} MIN_DATE				下单时间起（年月日格式） 
	* @apiParam {string} MAX_DATE				下单时间止（年月日格式）

    * @apiSuccess {boolean} state 接口获取数据是否成功.1:成功  0:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {number} total 总条数
    * @apiSuccess {object[]} obj 订单列表
    * @apiSuccess {string} obj.ORDER_STATE_NORMAL    订单状态 1=待付款/待付款,2=待发货/已付款,3=已发货/待收货,4=已确认收货/交易完成,6=交易关闭
    * @apiSuccess {string} obj.REFUND_ID    退款单号
    * @apiSuccess {string} obj.ID    订单号
    * @apiSuccess {string} obj.STORE_NAME    门店
    * @apiSuccess {number} obj.REFUND_MONEY    退款金额
    * @apiSuccess {number} obj.ACT_MONEY   实际 退款金额
    * @apiSuccess {string} obj.CREATE_TIME   申请退款时间
    */
	@RequestMapping(value = "/returns_order_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet storeTranscatReturnsList(HttpServletRequest request) {
		return Transform.GetResult(storeTransactService.storeTranscatReturnsList(request));
	}
	
	@RequestMapping(value = "/returns_order_detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet storeTranscatReturnsDetail(HttpServletRequest request) {
		return Transform.GetResult(storeTransactService.storeTranscatReturnsDetail(request));
	}
	
	/**
     * @api{post} /{oms_server}/store_transact/store_list 商家列表
     * @apiGroup member
     * @apiName store_list
     * @apiDescription  商家列表
     * @apiVersion 0.0.1
     * 
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object[]} obj 
     * @apiSuccess{number} obj.id			会员id
     * @apiSuccess{string} obj.store_name	门店名称
     * 
     */
    @RequestMapping(value = "/store_select_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryMemberStoreSelectList(HttpServletRequest request) {
		return Transform.GetResult(storeTransactService.queryMemberStoreSelectList(request));
	}
    
    /**
	 * 查询商家下面的店铺列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/query_user_store_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryUserStoreList(HttpServletRequest request) {
		return Transform.GetResult(storeTransactService.queryUserStoreList(request));
	}

}
