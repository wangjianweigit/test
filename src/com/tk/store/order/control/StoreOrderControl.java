package com.tk.store.order.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.store.order.service.StoreOrderService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : OrderControl
 * 店铺订货单管理
 * 
 * @author shifan
 * @version 1.00
 * @date 2018-2-27
 */
@Controller
@RequestMapping("/store_order")
public class StoreOrderControl {

	@Resource
	private StoreOrderService storeOrderService;
	
	/**
    *
    * @api {post} /{project_name}/store_order/list 店铺订货单列表
    * @apiGroup store_order
    * @apiDescription  店铺订货单列表
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
		return Transform.GetResult(storeOrderService.queryOrderList(request));
	}
	
	/**
    *
    * @api {post} /{project_name}/store_order/detail 店铺订货单详情
    * @apiGroup store_order
    * @apiDescription  店铺订货单详情
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
		return Transform.GetResult(storeOrderService.queryOrderDetail(request));
	}

	/** 
	* @api {post} /{project_name}/store_order/cancel 店铺订货单取消【合作商审核驳回】
    * @apiGroup order
    * @apiDescription  店铺订货单取消
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
			pr=storeOrderService.orderCancel(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
	

   /** 
	* @api {post} /{project_name}/store_order/payment_store_order 店铺订货单支付【合作商审核驳回】
    * @apiGroup store_order
    * @apiDescription  店铺订货单支付
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
	@RequestMapping(value = "/payment_store_order", method = RequestMethod.POST)
	@ResponseBody
	public Packet paymentStoreOrder(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=storeOrderService.paymentStoreOrder(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}

}