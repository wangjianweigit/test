package com.tk.oms.finance.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.finance.service.OrderAuditingService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Controller
@RequestMapping("order_auditing")
public class OrderAuditingControl {
	@Resource
	private OrderAuditingService orderAuditingService;
	
	/**
     * @api {post} /{project_name}/order_auditing/list
     * @apiName list
     * @apiGroup order_auditing
     * @apiDescription  订单审核列表
     * @apiVersion 0.0.1
     * 
     * @apiParam {number}	pageIndex 			开始页码
     * @apiParam {number}	pageSize 			每页数据量
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object[]} obj 订单列表
     * @apiSuccess {number} obj.id    主键ID
     * @apiSuccess {string} obj.order_number    订单号
     * @apiSuccess {string} obj.create_date    订单时间
     * @apiSuccess {string} obj.user_name    下订单人用户名（关联tbl_user_info的user_name）
     * @apiSuccess {string} obj.user_manage_name    下订单人用户姓名（关联tbl_user_info的USER_MANAGE_NAME）
     * @apiSuccess {string} obj.order_type    订单类型（批发、零售、微信分销）
     * @apiSuccess {number} obj.order_state    总状态：待付款（1），待发货（2） ，待收货（3） ，退款中（4） ，交易完成（5） ，交易关闭 （6）
     * @apiSuccess {string} obj.order_remark    订单备注
     * @apiSuccess {string} obj.receiving_name    收货人姓名
     * @apiSuccess {string} obj.receiving_address    收货人地址
     * @apiSuccess {string} obj.receiving_phone    收货人手机
     * @apiSuccess {string} obj.logistics_company_code    物流公司代码
     * @apiSuccess {string} obj.logistics_company_name    物流公司名称
     * @apiSuccess {string} obj.logistics_number    物流单号
     * @apiSuccess {number} obj.logistics_money    物流费用，通过商品设置的运费模板进行计算得出
     * @apiSuccess {string} obj.cancel_reason    订单取消原因
     * @apiSuccess {string} obj.cancel_date    订单取消时间
     * @apiSuccess {number} obj.payment_state    付款状态：待付款（1），已付款 （2）,付款待审核（3）
     * @apiSuccess {string} obj.payment_date    付款时间
     * @apiSuccess {string} obj.payment_type    付款渠道（余额、微信、支付宝、授信、银联、余额+授信。。。。。POS刷卡、转账、现金）
     * @apiSuccess {number} obj.payment_money    总付总额
     * @apiSuccess {string} obj.check_cancle_reason    审核驳回原因
     * @apiSuccess {number} obj.product_money    商品总额(优惠后)
     * @apiSuccess {number} obj.product_count    商品总数
     * @apiSuccess {number} obj.discount_money    优惠金额
     * @apiSuccess {string} obj.order_source    订单来源（PC,微信）
     * @apiSuccess {string} obj.cancel_user_name    取消人用户名
     * @apiSuccess {string} obj.cancel_user_realname    取消人姓名
     * @apiSuccess {number} obj.check_state    审核状态： 待审核（1），已审核 （2），已驳回（3）
     * @apiSuccess {number} obj.refund_state    退款状态：待退款（1），已退款（2）
     * @apiSuccess {number} obj.df_money    代发费用
     * @apiSuccess {string} obj.ywjl_user_name    业务经理用户名
     * @apiSuccess {string} obj.ywy_user_name    业务员用户名
     * @apiSuccess {number} obj.md_id    门店ID
     * @apiSuccess {string} obj.md_name  门店名称
     * @apiSuccess {string} obj.xdr_user_type    下单人用户类型（3：业务员，4：业务经理，5：店长，6：营业员，7：自行下单）
     * @apiSuccess {string} obj.xdr_user_name    下单人用户名
     * @apiSuccess {string} obj.payment_method    订单付款方式(转账汇款)
     * @apiSuccess {string} obj.payment_send_state    订单付款码发送状态(0：未发送，1：已发送)
     * @apiSuccess {string} obj.payment_transfer_user_name    订单转账付款审核用户名
     * @apiSuccess {string} obj.payment_transfer_user_realname    订单转账付款审核姓名
     * @apiSuccess {string} obj.payment_transfer_date    订单转账付款审核时间
     * @apiSuccess {string} obj.user_pay_state    用户支付状态(0：未支付,1：已支付)
     * @apiSuccess {string} obj.user_voucher_url	用户支付凭证
     * @apiSuccess {number} obj.warehouse_id 			仓库ID
     * @apiSuccess {string} obj.warehouse_name 			仓库名称
     * 
     */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryOrderAuditingList(HttpServletRequest request) {
        return Transform.GetResult(orderAuditingService.queryOrderAuditingList(request));
    }
	
	/**
     * @api {post} /{project_name}/order_auditing/detail
     * @apiName detail
     * @apiGroup order_auditing
     * @apiDescription  订单审核详情
     * @apiVersion 0.0.1
     * 
     * @apiParam {string}	order_number 			订单号
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object} obj 订单审核详情
     * @apiSuccess {number} obj.id    主键ID
     * @apiSuccess {string} obj.order_number    订单号
     * @apiSuccess {string} obj.create_date    订单时间
     * @apiSuccess {string} obj.user_name    下订单人用户名（关联tbl_user_info的user_name）
     * @apiSuccess {string} obj.user_manage_name    下订单人用户姓名（关联tbl_user_info的USER_MANAGE_NAME）
     * @apiSuccess {string} obj.order_type    订单类型（批发、零售、微信分销）
     * @apiSuccess {number} obj.order_state    总状态：待付款（1），待发货（2） ，待收货（3） ，退款中（4） ，交易完成（5） ，交易关闭 （6）
     * @apiSuccess {string} obj.order_remark    订单备注
     * @apiSuccess {string} obj.receiving_name    收货人姓名
     * @apiSuccess {string} obj.receiving_address    收货人地址
     * @apiSuccess {string} obj.receiving_phone    收货人手机
     * @apiSuccess {string} obj.logistics_company_code    物流公司代码
     * @apiSuccess {string} obj.logistics_company_name    物流公司名称
     * @apiSuccess {string} obj.logistics_number    物流单号
     * @apiSuccess {number} obj.logistics_money    物流费用，通过商品设置的运费模板进行计算得出
     * @apiSuccess {string} obj.cancel_reason    订单取消原因
     * @apiSuccess {string} obj.cancel_date    订单取消时间
     * @apiSuccess {number} obj.payment_state    付款状态：待付款（1），已付款 （2）,付款待审核（3）
     * @apiSuccess {string} obj.payment_date    付款时间
     * @apiSuccess {string} obj.payment_type    付款渠道（余额、微信、支付宝、授信、银联、余额+授信。。。。。POS刷卡、转账、现金）
     * @apiSuccess {number} obj.payment_money    总付总额
     * @apiSuccess {string} obj.check_cancle_reason    审核驳回原因
     * @apiSuccess {number} obj.product_money    商品总额(优惠后)
     * @apiSuccess {number} obj.product_count    商品总数
     * @apiSuccess {number} obj.discount_money    优惠金额
     * @apiSuccess {string} obj.order_source    订单来源（PC,微信）
     * @apiSuccess {string} obj.cancel_user_name    取消人用户名
     * @apiSuccess {string} obj.cancel_user_realname    取消人姓名
     * @apiSuccess {number} obj.check_state    审核状态： 待审核（1），已审核 （2），已驳回（3）
     * @apiSuccess {number} obj.refund_state    退款状态：待退款（1），已退款（2）
     * @apiSuccess {number} obj.df_money    代发费用
     * @apiSuccess {string} obj.ywjl_user_name    业务经理用户名
     * @apiSuccess {string} obj.ywy_user_name    业务员用户名
     * @apiSuccess {number} obj.md_id    门店ID
     * @apiSuccess {string} obj.md_name  门店名称
     * @apiSuccess {string} obj.xdr_user_type    下单人用户类型（3：业务员，4：业务经理，5：店长，6：营业员，7：自行下单）
     * @apiSuccess {string} obj.xdr_user_name    下单人用户名
     * @apiSuccess {string} obj.payment_method    订单付款方式(转账汇款)
     * @apiSuccess {string} obj.payment_send_state    订单付款码发送状态(0：未发送，1：已发送)
     * @apiSuccess {string} obj.payment_transfer_user_name    订单转账付款审核用户名
     * @apiSuccess {string} obj.payment_transfer_user_realname    订单转账付款审核姓名
     * @apiSuccess {string} obj.payment_transfer_date    订单转账付款审核时间
     * @apiSuccess {string} obj.user_pay_state    用户支付状态(0：未支付,1：已支付)
     * @apiSuccess {string} obj.user_voucher_url	用户支付凭证
     * @apiSuccess {number} obj.warehouse_id 			仓库ID
     * @apiSuccess {string} obj.warehouse_name 			仓库名称
     * 
     */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryOrderAuditingDetail(HttpServletRequest request) {
        return Transform.GetResult(orderAuditingService.queryOrderAuditingDetail(request));
    }
	/**
     * @api {post} /{project_name}/order_auditing/check
     * @apiName check
     * @apiGroup order_auditing
     * @apiDescription  订单审核 通过,驳回
     * @apiVersion 0.0.1
     * 
     * @apiParam {string}	check_user_name			审核人
     * @apiParam {string}	pay_trade_number		付款交易号
     * @apiParam {string}	order_number 			订单号
     * @apiParam {string}	check_type				审核类型			
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
    @ResponseBody
    public Packet orderAuditing(HttpServletRequest request) {
		ProcessResult pr = new	ProcessResult();
		try{
			pr = orderAuditingService.orderAuditing(request);
		}catch(Exception ex){
			pr.setState(false);
			pr.setMessage(ex.getMessage());
		}
        return Transform.GetResult(pr);
    }
	
	/**
     * @api {post} /{project_name}/order_auditing/order_list
     * @apiName order_list
     * @apiGroup order_auditing
     * @apiDescription  交易关联订单列表
     * @apiVersion 0.0.1
     * 
     * @apiParam {string}	check_user_name			审核人
     * @apiParam {string}	pay_trade_number		付款交易号
     * @apiParam {string}	order_number 			订单号
     * @apiParam {string}	check_type				审核类型			
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     */
	@RequestMapping(value = "/order_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryOrderUnionPayList(HttpServletRequest request) {
        return Transform.GetResult(orderAuditingService.queryOrderUnionPayList(request));
    }
}
