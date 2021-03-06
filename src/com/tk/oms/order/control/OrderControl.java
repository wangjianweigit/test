package com.tk.oms.order.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.order.service.OrderService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : OrderControl
 * 订单管理
 * 
 * @author shifan
 * @version 1.00
 * @date 2017-4-14
 */
@Controller
@RequestMapping("/order")
public class OrderControl {

	@Resource
	private OrderService orderService;
	
	/**
    *
    * @api {post} /{project_name}/order/list 订单列表
    * @apiGroup order
    * @apiDescription  订单列表
    * @apiVersion 0.0.1

	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条   

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 订单列表
    * @apiSuccess {number} obj.ID    主键ID
    * @apiSuccess {string} obj.ORDER_NUMBER    订单号
    * @apiSuccess {string} obj.CREATE_DATE    订单时间
    * @apiSuccess {string} obj.USER_NAME    下订单人用户名（关联tbl_user_info的user_name）
    * @apiSuccess {string} obj.USER_MANAGE_NAME    下订单人用户姓名（关联tbl_user_info的USER_MANAGE_NAME）
    * @apiSuccess {string} obj.ORDER_TYPE    订单类型（批发、零售、微信分销）
    * @apiSuccess {number} obj.ORDER_STATE    总状态：待付款（1），待发货（2） ，待收货（3） ，退款中（4） ，交易完成（5） ，交易关闭 （6）
    * @apiSuccess {string} obj.ORDER_REMARK    订单备注
    * @apiSuccess {string} obj.RECEIVING_NAME    收货人姓名
    * @apiSuccess {string} obj.RECEIVING_ADDRESS    收货人地址
    * @apiSuccess {string} obj.RECEIVING_PHONE    收货人手机
    * @apiSuccess {string} obj.LOGISTICS_COMPANY_CODE    物流公司代码
    * @apiSuccess {string} obj.LOGISTICS_COMPANY_NAME    物流公司名称
    * @apiSuccess {string} obj.LOGISTICS_NUMBER    物流单号
    * @apiSuccess {number} obj.LOGISTICS_MONEY    物流费用，通过商品设置的运费模板进行计算得出
    * @apiSuccess {string} obj.CANCEL_REASON    订单取消原因
    * @apiSuccess {string} obj.CANCEL_DATE    订单取消时间
    * @apiSuccess {number} obj.PAYMENT_STATE    付款状态：待付款（1），已付款 （2）,付款待审核（3）
    * @apiSuccess {string} obj.PAYMENT_DATE    付款时间
    * @apiSuccess {string} obj.PAYMENT_TYPE    付款渠道（余额、微信、支付宝、授信、银联、余额+授信。。。。。POS刷卡、转账、现金）
    * @apiSuccess {number} obj.PAYMENT_MONEY    总付总额
    * @apiSuccess {string} obj.CHECK_CANCLE_REASON    审核驳回原因
    * @apiSuccess {number} obj.PRODUCT_MONEY    商品总额(优惠后)
    * @apiSuccess {number} obj.PRODUCT_COUNT    商品总数
    * @apiSuccess {number} obj.DISCOUNT_MONEY    优惠金额
    * @apiSuccess {string} obj.ORDER_SOURCE    订单来源（PC,微信）
    * @apiSuccess {string} obj.CANCEL_USER_NAME    取消人用户名
    * @apiSuccess {string} obj.CANCEL_USER_REALNAME    取消人姓名
    * @apiSuccess {number} obj.CHECK_STATE    审核状态： 待审核（1），已审核 （2），已驳回（3）
    * @apiSuccess {number} obj.REFUND_STATE    退款状态：待退款（1），已退款（2）
    * @apiSuccess {number} obj.DF_MONEY    代发费用
    * @apiSuccess {string} obj.YWJL_USER_NAME    业务经理用户名
    * @apiSuccess {string} obj.YWY_USER_NAME    业务员用户名
    * @apiSuccess {number} obj.MD_ID    门店ID
    * @apiSuccess {string} obj.XDR_USER_TYPE    下单人用户类型（3：业务员，4：业务经理，5：店长，6：营业员，7：自行下单）
    * @apiSuccess {string} obj.XDR_USER_NAME    下单人用户名
    * @apiSuccess {string} obj.PAYMENT_METHOD    订单付款方式(转账汇款)
    * @apiSuccess {string} obj.PAYMENT_SEND_STATE    订单付款码发送状态(0：未发送，1：已发送)
    * @apiSuccess {string} obj.PAYMENT_TRANSFER_USER_NAME    订单转账付款审核用户名
    * @apiSuccess {string} obj.PAYMENT_TRANSFER_USER_REALNAME    订单转账付款审核姓名
    * @apiSuccess {string} obj.PAYMENT_TRANSFER_DATE    订单转账付款审核时间
    * @apiSuccess {string} obj.USER_PAY_STATE    用户支付状态(0：未支付,1：已支付)
    * @apiSuccess {number} obj.IMPORT_TYPE		是否导入订单 0否 1是
    * 
    */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryOrderList(HttpServletRequest request) {
		return Transform.GetResult(orderService.queryOrderList(request));
	}
	
	/**
    *
    * @api {post} /{project_name}/order/detail 待审批商品详情
    * @apiGroup order
    * @apiDescription  订单详情
    * @apiVersion 0.0.1

	* @apiParam {string} order_number				订单号

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object} obj 订单详情
    * @apiSuccess {object} base_info 订单基本信息详情
    * @apiSuccess {number} obj.base_info.ID    主键ID
    * @apiSuccess {string} obj.base_info.ORDER_NUMBER    订单号
    * @apiSuccess {string} obj.base_info.CREATE_DATE    订单时间
    * @apiSuccess {string} obj.base_info.USER_NAME    下订单人用户名（关联tbl_user_info的user_name）
    * @apiSuccess {string} obj.base_info.USER_MANAGE_NAME    下订单人用户姓名（关联tbl_user_info的USER_MANAGE_NAME）
    * @apiSuccess {string} obj.base_info.ORDER_TYPE    订单类型（批发、零售、微信分销）
    * @apiSuccess {number} obj.base_info.ORDER_STATE    总状态：待付款（1），待发货（2） ，待收货（3） ，退款中（4） ，交易完成（5） ，交易关闭 （6）
    * @apiSuccess {string} obj.base_info.ORDER_REMARK    订单备注
    * @apiSuccess {string} obj.base_info.RECEIVING_NAME    收货人姓名
    * @apiSuccess {string} obj.base_info.RECEIVING_ADDRESS    收货人地址
    * @apiSuccess {string} obj.base_info.RECEIVING_PHONE    收货人手机
    * @apiSuccess {string} obj.base_info.LOGISTICS_COMPANY_CODE    物流公司代码
    * @apiSuccess {string} obj.base_info.LOGISTICS_COMPANY_NAME    物流公司名称
    * @apiSuccess {string} obj.base_info.LOGISTICS_NUMBER    物流单号
    * @apiSuccess {number} obj.base_info.LOGISTICS_MONEY    物流费用，通过商品设置的运费模板进行计算得出
    * @apiSuccess {string} obj.base_info.CANCEL_REASON    订单取消原因
    * @apiSuccess {string} obj.base_info.CANCEL_DATE    订单取消时间
    * @apiSuccess {number} obj.base_info.PAYMENT_STATE    付款状态：待付款（1），已付款 （2）,付款待审核（3）
    * @apiSuccess {string} obj.base_info.PAYMENT_DATE    付款时间
    * @apiSuccess {string} obj.base_info.PAYMENT_TYPE    付款渠道（余额、微信、支付宝、授信、银联、余额+授信。。。。。POS刷卡、转账、现金）
    * @apiSuccess {number} obj.base_info.PAYMENT_MONEY    总付总额
    * @apiSuccess {string} obj.base_info.PAYMENT_NUMBER    付款关联号
    * @apiSuccess {string} obj.base_info.CHECK_USER_NAME    审核人用户名，关联表（TBL_SYS_USER_INFO的user_name）
    * @apiSuccess {string} obj.base_info.CHECK_USER_REALNAME    审核人姓名
    * @apiSuccess {string} obj.base_info.CHECK_DATE    审核日期
    * @apiSuccess {string} obj.base_info.CHECK_CANCLE_REASON    审核驳回原因
    * @apiSuccess {number} obj.base_info.PRODUCT_MONEY    商品总额(优惠后)
    * @apiSuccess {number} obj.base_info.PRODUCT_COUNT    商品总数
    * @apiSuccess {number} obj.base_info.DISCOUNT_MONEY    优惠金额
    * @apiSuccess {string} obj.base_info.UPDATE_REASON    修改或调价原因
    * @apiSuccess {string} obj.base_info.CONFIRM_DATE    确认收货时间
    * @apiSuccess {string} obj.base_info.ORDER_SOURCE    订单来源（PC,微信）
    * @apiSuccess {string} obj.base_info.CANCEL_USER_NAME    取消人用户名
    * @apiSuccess {string} obj.base_info.CANCEL_USER_REALNAME    取消人姓名
    * @apiSuccess {number} obj.base_info.CHECK_STATE    审核状态： 待审核（1），已审核 （2），已驳回（3）
    * @apiSuccess {number} obj.base_info.REFUND_STATE    退款状态：待退款（1），已退款（2）
    * @apiSuccess {string} obj.base_info.LAST_UPDATE_TIME    最后更新时间--【同步用】
    * @apiSuccess {number} obj.base_info.DF_MONEY    代发费用
    * @apiSuccess {string} obj.base_info.YWJL_USER_NAME    业务经理用户名
    * @apiSuccess {string} obj.base_info.YWY_USER_NAME    业务员用户名
    * @apiSuccess {number} obj.base_info.MD_ID    门店ID
    * @apiSuccess {string} obj.base_info.XDR_USER_TYPE    下单人用户类型（3：业务员，4：业务经理，5：店长，6：营业员，7：自行下单）
    * @apiSuccess {string} obj.base_info.XDR_USER_NAME    下单人用户名
    * @apiSuccess {string} obj.base_info.PAYMENT_METHOD    订单付款方式(转账汇款)
    * @apiSuccess {string} obj.base_info.TRANSFER_VOUCHER_URL    订单转账凭证(图片地址)
    * @apiSuccess {string} obj.base_info.PAYMENT_SEND_STATE    订单付款码发送状态(0：未发送，1：已发送)
    * @apiSuccess {string} obj.base_info.PAYMENT_TRANSFER_USER_NAME    订单转账付款审核用户名
    * @apiSuccess {string} obj.base_info.PAYMENT_TRANSFER_USER_REALNAME    订单转账付款审核姓名
    * @apiSuccess {string} obj.base_info.PAYMENT_TRANSFER_DATE    订单转账付款审核时间
    * @apiSuccess {string} obj.base_info.USER_PAY_STATE    用户支付状态(0：未支付,1：已支付)
    * @apiSuccess {string} obj.base_info.USER_VOUCHER_URL    用户支付凭证
    * @apiSuccess {object[]} base_info 订单商品sku信息列表
    * @apiSuccess {number} obj.product_sku_info.WAREHOUSE_ID    下单仓库ID
    * @apiSuccess {number} obj.product_sku_info.ID    主键ID
    * @apiSuccess {string} obj.product_sku_info.ORDER_NUMBER    订单号
    * @apiSuccess {number} obj.product_sku_info.ORDER_ITEM_NUMBER    订单项次号
    * @apiSuccess {number} obj.product_sku_info.CODENUMBER    码号
    * @apiSuccess {number} obj.product_sku_info.COUNT    数量
    * @apiSuccess {number} obj.product_sku_info.PRODUCT_UNIT_PRICE    现销售单价
    * @apiSuccess {number} obj.product_sku_info.PRODUCT_TOTAL_MONEY    商品总价
    * @apiSuccess {number} obj.product_sku_info.PRODUCT_OLD_UNIT_PRICE    原折扣后单价
    * @apiSuccess {number} obj.product_sku_info.PRODUCT_TOTAL_DISCOUNT_MONEY    优惠总金额
    * @apiSuccess {string} obj.product_sku_info.USER_NAME    客户用户名（关联tbl_user_info的user_name）
    * @apiSuccess {string} obj.product_sku_info.ORDER_DATE    订单时间
    * @apiSuccess {number} obj.product_sku_info.PRODUCT_SKU    sku
    * @apiSuccess {string} obj.product_sku_info.PRODUCT_SKU_NAME    sku名称
    * @apiSuccess {string} obj.product_sku_info.PRODUCT_ITEMNUMBER    货号
    * @apiSuccess {string} obj.product_sku_info.PRODUCT_COLOR    颜色
    * @apiSuccess {number} obj.product_sku_info.PRODUCT_OUT_COUNT    已同步确认数
    * @apiSuccess {number} obj.product_sku_info.PRODUCT_SURE_COUNT    可配数
    * @apiSuccess {string} obj.product_sku_info.PRODUCT_CONFIGURE_STATE    配货状态
    * @apiSuccess {number} obj.product_sku_info.PRODUCT_LACK_COUNT    缺货数
    * @apiSuccess {string} obj.product_sku_info.PRODUCT_SPECS    规格
    * @apiSuccess {number} obj.product_sku_info.PRODUCT_OLDSALE_PRIZE    原批发价
    * @apiSuccess {number} obj.product_sku_info.TOTAL_SEND_COUNT    总发货数
    * @apiSuccess {object[]} box_info 订单包裹信息列表
    * @apiSuccess {string} obj.box_info.BOX_NUMBER    箱码号
    * @apiSuccess {string} obj.box_info.ORDER_NUMBER    订单号
    * @apiSuccess {string} obj.box_info.LOGISTICS_NUMBER    运单号
    * @apiSuccess {string} obj.box_info.LOGISTICS_COMPANY_NAME    物流公司名称
    * @apiSuccess {string} obj.box_info.SEND_DATE    发货时间
    */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryOrderDetail(HttpServletRequest request) {
		return Transform.GetResult(orderService.queryOrderDetail(request));
	}

	/** 
	* @api {post} /{project_name}/order/cancel 订单取消
    * @apiGroup order
    * @apiDescription  订单取消
    * @apiVersion 0.0.1
    * 
	* @apiParam {string} order_number				订单号
	* 
    * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    */
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	@ResponseBody
	public Packet orderCancel(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=orderService.orderCancel(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
	
	/** 
	* @api {post} /{project_name}/order/readjust 订单调价
    * @apiGroup order
    * @apiDescription  订单调价
    * @apiVersion 0.0.1
    * 
	* @apiParam {string} order_number				订单号
	* @apiParam {string} order_sku_ids				订单sku-集合
	* @apiParam {string} order_old_prize			订单sku-原价集合
	* @apiParam {string} order_new_prize			订单sku-现价集合
	* @apiParam {string} logistics_company_code		订单物流公司代码
	* @apiParam {number} logistics_money			订单运费
	* @apiParam {string} update_reason				订单调价原因
	* @apiParam {number} df_money					订单代发费用
    * @apiSuccess {boolean} state 接口是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    */
	@RequestMapping(value = "/readjust", method = RequestMethod.POST)
	@ResponseBody
	public Packet orderReadjust(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=orderService.orderReadjust(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
	
	/** 
	* @api {post} /{project_name}/order/freight_money 订单物流费用计算
    * @apiGroup order
    * @apiDescription  订单物流费用计算
    * @apiVersion 0.0.1
    * 
	* @apiParam {string} order_number				订单号
	* @apiParam {number} logistics_company_id		订单物流公司id
    * @apiSuccess {boolean} state 接口是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object} obj 接口返回对象
    * @apiSuccess {number} obj.FREIGHT_MONEY 运费
    */
	@RequestMapping(value = "/freight_money", method = RequestMethod.POST)
	@ResponseBody
	public Packet orderFreightMoney(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=orderService.orderFreightMoney(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/transfer_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryTransferList(HttpServletRequest request) {
		return Transform.GetResult(orderService.queryTransferList(request));
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/transfer_detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryTransferDetail(HttpServletRequest request) {
		return Transform.GetResult(orderService.queryTransferDetail(request));
	}
   /** 
	* @api {post} /{project_name}/order/payment_state_edit
    * @apiGroup order
    * @apiDescription  确认订单转账支付
    * @apiVersion 0.0.1
    * 
	* @apiParam {string} order_number				订单号
	* @apiParam {number} transfer_voucher_url		订单付款凭证
	* @apiParam {string} user_name					用户名
	* 
	* 
    * @apiSuccess {boolean} state 接口是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * 
    */
	@RequestMapping(value = "/payment_state_edit", method = RequestMethod.POST)
	@ResponseBody
	public Packet editPaymentState(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=orderService.editPaymentState(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
	
	/**
	 * 经销商订单列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/distributor_order_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet distributorOrderList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=orderService.distributorOrderList(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
	
	/**
	 * 经销商订单详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/distributor_order_detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet distributorOrderDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=orderService.distributorOrderDetail(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
	
	/**
	 * 经销商订单获取物流信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/distributor_logistics_info", method = RequestMethod.POST)
	@ResponseBody
	public Packet distributorOrLogisticsInfo(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=orderService.distributorOrLogisticsInfo(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}

	/**
	 * 销售订单下载
	 * @param request
	 * @return
     */
	@RequestMapping(value = "/download", method = RequestMethod.POST)
	@ResponseBody
	public Packet downloadSaleOrder(HttpServletRequest request) {
		return Transform.GetResult(orderService.downloadSaleOrder(request));
	}
	
	/**
	 * 添加订单备注
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add_customer_service_remark", method = RequestMethod.POST)
	@ResponseBody
	public Packet addCustomerServiceRemark(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=orderService.addCustomerServiceRemark(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
	
	/**
	 * 查看当前订单备注记录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/query_customer_service_remark_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryCustomerServiceRemarkList(HttpServletRequest request) {
		return Transform.GetResult(orderService.queryCustomerServiceRemarkList(request));
	}
	
	/**
	 * 查看当前订单备注记录详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/customer_service_remark_detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryCustomerServiceRemarkDetail(HttpServletRequest request) {
		return Transform.GetResult(orderService.queryCustomerServiceRemarkDetail(request));
	}
	
	/**
	 * 查看当前订单发货排期信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deliver_schedule", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryOrderDeliverScheduleList(HttpServletRequest request) {
		return Transform.GetResult(orderService.queryOrderDeliverScheduleList(request));
	}
	
	/**
	 * 查看当前订单sku订单占用排名情况
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sku_order_count", method = RequestMethod.POST)
	@ResponseBody
	public Packet querySkuOrderCountList(HttpServletRequest request) {
		return Transform.GetResult(orderService.querySkuOrderCountList(request));
	}
	
	/**
	 * 查看当前订单sku生产计划情况
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sku_purchase_count", method = RequestMethod.POST)
	@ResponseBody
	public Packet querySkuPurchaseList(HttpServletRequest request) {
		return Transform.GetResult(orderService.querySkuPurchaseList(request));
	}
}