package com.tk.oms.returns.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.returns.service.ReturnsService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : ReturnsControl
 * 退货单管理
 * 
 * @author shifan
 * @version 1.00
 * @date 2017-4-27
 */
@Controller
@RequestMapping("/returns")
public class ReturnsControl {

	@Resource
	private ReturnsService returnsService;
	
	/**
    *
    * @api {post} /{project_name}/returns/list 退货单列表
    * @apiGroup returns
    * @apiDescription  退货单列表
    * @apiVersion 0.0.1

	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条   

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 退货单列表
    * @apiSuccess {number} obj.ID    主键ID
    * @apiSuccess {string} obj.RETURN_NUMBER    退货单号
    * @apiSuccess {string} obj.ORDER_NUMBER    订单号
    * @apiSuccess {string} obj.USER_NAME    退货用户名
    * @apiSuccess {string} obj.USER_MANAGE_NAME    退货用户姓名
    * @apiSuccess {number} obj.PRODUCT_MONEY    退货商品金额
    * @apiSuccess {number} obj.PRODUCT_COUNT    退货商品数量
    * @apiSuccess {number} obj.LOGISTICS_MONEY    退款物流费用
    * @apiSuccess {number} obj.DF_MONEY    退款代发费用
    * @apiSuccess {number} obj.RETURN_TOTAL_MONEY    退款总额
    * @apiSuccess {string} obj.STATE    退货单状态 0 草稿状态 1-待审核 2 审核通过 3 已驳回
    * @apiSuccess {string} obj.RETURN_REMARK    退货说明
    * @apiSuccess {string} obj.APPLY_USER_NAME    退货申请人用户名
    * @apiSuccess {string} obj.APPLY_USER_REALNAME    退货申请人用户姓名
    * @apiSuccess {string} obj.CREATE_DATE    申请日期
    * @apiSuccess {string} obj.CHECK_USER_NAME    审核人用户名
    * @apiSuccess {string} obj.CHECK_USER_REALNAME    审核人用户姓名
    * @apiSuccess {string} obj.CHECK_DATE    审核时间
    * @apiSuccess {string} obj.CHECK_CANCLE_REASON    审核驳回原因
    */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryReturnsList(HttpServletRequest request) {
		return Transform.GetResult(returnsService.queryReturnsList(request));
	}
	
	/**
    *
    * @api {post} /{project_name}/returns/detail 退货单详情
    * @apiGroup returns
    * @apiDescription  退货单详情
    * @apiVersion 0.0.1

	* @apiParam {string} order_number				退货单号

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object} obj 退货单详情
    * @apiSuccess {object} base_info 退货单基本信息详情
    * @apiSuccess {number} obj.base_info.ID    主键ID
    * @apiSuccess {string} obj.base_info.RETURN_NUMBER    退货单号
    * @apiSuccess {string} obj.base_info.ORDER_NUMBER    订单号
    * @apiSuccess {string} obj.base_info.USER_NAME    退货用户名
    * @apiSuccess {string} obj.base_info.USER_MANAGE_NAME    退货用户姓名
    * @apiSuccess {number} obj.base_info.PRODUCT_MONEY    退货商品金额
    * @apiSuccess {number} obj.base_info.PRODUCT_COUNT    退货商品数量
    * @apiSuccess {number} obj.base_info.LOGISTICS_MONEY    退款物流费用
    * @apiSuccess {number} obj.base_info.DF_MONEY    退款代发费用
    * @apiSuccess {number} obj.base_info.RETURN_TOTAL_MONEY    退款总额
    * @apiSuccess {string} obj.base_info.STATE    退货单状态 0 草稿状态 1-待审核 2 审核通过 3 已驳回
    * @apiSuccess {string} obj.base_info.RETURN_REMARK    退货说明
    * @apiSuccess {string} obj.base_info.APPLY_USER_NAME    退货申请人用户名
    * @apiSuccess {string} obj.base_info.APPLY_USER_REALNAME    退货申请人用户姓名
    * @apiSuccess {string} obj.base_info.CREATE_DATE    申请日期
    * @apiSuccess {string} obj.base_info.CHECK_USER_NAME    审核人用户名
    * @apiSuccess {string} obj.base_info.CHECK_USER_REALNAME    审核人用户姓名
    * @apiSuccess {string} obj.base_info.CHECK_DATE    审核时间
    * @apiSuccess {string} obj.base_info.CHECK_CANCLE_REASON    审核驳回原因
    * @apiSuccess {object[]} product_info 退货单商品详情
    * @apiSuccess {number} obj.product_info.ID    主键ID
    * @apiSuccess {string} obj.product_info.RETURN_NUMBER    退货单号
    * @apiSuccess {string} obj.product_info.ORDER_NUMBER    订单号
    * @apiSuccess {number} obj.product_info.CODENUMBER    码号
    * @apiSuccess {number} obj.product_info.COUNT    数量
    * @apiSuccess {number} obj.product_info.PRODUCT_UNIT_PRICE    现销售单价
    * @apiSuccess {number} obj.product_info.PRODUCT_TOTAL_MONEY    商品总价
    * @apiSuccess {number} obj.product_info.PRODUCT_OLD_UNIT_PRICE    原折扣后单价
    * @apiSuccess {number} obj.product_info.PRODUCT_TOTAL_DISCOUNT_MONEY    优惠总金额
    * @apiSuccess {string} obj.product_info.USER_NAME    客户用户名（关联tbl_user_info的user_name）
    * @apiSuccess {string} obj.product_info.RETURN_DATE    退货单时间
    * @apiSuccess {number} obj.product_info.PRODUCT_SKU    sku
    * @apiSuccess {string} obj.product_info.PRODUCT_SKU_NAME    sku名称
    * @apiSuccess {string} obj.product_info.PRODUCT_ITEMNUMBER    货号
    * @apiSuccess {string} obj.product_info.PRODUCT_COLOR    颜色
    * @apiSuccess {string} obj.product_info.PRODUCT_SPECS    规格
    * @apiSuccess {number} obj.product_info.PRODUCT_OLDSALE_PRIZE    原批发价
    */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryReturnsDetail(HttpServletRequest request) {
		return Transform.GetResult(returnsService.queryReturnsDetail(request));
	}


	/** 
	* @api {post} /{project_name}/returns/apply 退货单申请
    * @apiGroup returns
    * @apiDescription  退货单申请
    * @apiVersion 0.0.1
    * 
	* @apiParam {string} order_number 申请信息
	* 
    * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    */
	@RequestMapping(value = "/apply", method = RequestMethod.POST)
	@ResponseBody
	public Packet returnsApply(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=returnsService.returnsApply(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
	
	/** 
	* @api {post} /{project_name}/returns/can_apply 可申请退货商品查询
    * @apiGroup returns
    * @apiDescription 可申请退货商品查询
    * @apiVersion 0.0.1
    * 
	* @apiParam {string} order_number 申请信息
	* 
    * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    */
	@RequestMapping(value = "/can_apply", method = RequestMethod.POST)
	@ResponseBody
	public Packet returnsCanApply(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=returnsService.returnsCanApply(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}

	/**
	 * @param request
	 * @return
	 * @api {post} /{project_name}/returns/approval_list 查询退款订单审核列表
	 * @apiGroup returns
	 * @apiName oreturns_approval_list
	 * @apiDescription 查询退款订单审核列表
	 * @apiVersion 0.0.1
	 * @apiParam {number} pageIndex 起始页
	 * @apiParam {number} pageSize 分页大小
	 * @apiParam {number} refund_state 退款状态：待退款（1），已退款（2）， 驳回(3)
	 * @apiParam {string} user_name 退款用户名 选填
	 * @apiParam {string} user_manage_name 退款用户姓名 选填
	 * @apiParam {string} user_manage_mobilephone 手机号 选填
	 * @apiSuccess {object} obj 退款订单审核列表
	 * @apiSuccess {string} obj.ORDER_NUMBER 订单号
	 * @apiSuccess {string} obj.USER_NAME 用户名
	 * @apiSuccess {string} obj.USER_MANAGE_NAME 用户姓名
	 * @apiSuccess {string} obj.USER_MANAGE_MOBILEPHONE 手机号
	 * @apiSuccess {string} obj.CREATE_DATE  创建时间
	 * @apiSuccess {string} obj.PAYMENT_DATE  付款时间
	 * @apiSuccess {number} obj.PAYMENT_MONEY  付款总额
	 * @apiSuccess {string} obj.PAYMENT_TYPE  付款渠道
	 * @apiSuccess {number} obj.PRODUCT_MONEY  商品总额(优惠后)
	 * @apiSuccess {number} obj.PRODUCT_COUNT  商品总数
	 * @apiSuccess {string} obj.ORDER_TYPE  订单类型（批发、零售、微信分销）
	 * @apiSuccess {number} obj.ORDER_STATE  订单状态：待付款（1），待发货（2） ，待收货（3） ，退款中（4） ，交易完成（5） ，交易关闭 （6）
	 * @apiSuccess {string} obj.ORDER_SOURCE  订单来源（PC、微信、新零售）
	 * @apiSuccess {string} obj.REFUND_STATE  退款状态：待审批（1），已通过（2），驳回（3）
	 * @apiSuccess {string} obj.CHECK_USER_REALNAME  退款审核人姓名
	 * @apiSuccess {string} obj.CHECK_DATE  退款审核时间
	 * @apiSuccess {number} total 数据总量
	 * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string} message 接口返回信息.
	 */
	@RequestMapping(value = "/approval_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryOrderRefundApprovalList(HttpServletRequest request) {
		return Transform.GetResult(returnsService.queryOrderRefundApprovalList(request));
	}

	/**
	 * @param request
	 * @return
	 * @api {post} /{project_name}/returns/reject 退款订单审批
	 * @apiGroup returns
	 * @apiName returns_reject
	 * @apiDescription 退款订单审批
	 * @apiVersion 0.0.1
	 * @apiParam {string} return_number 退货单号 必填
	 * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string} message 接口返回信息.
	 */
	@RequestMapping(value = "/approval", method = RequestMethod.POST)
	@ResponseBody
	public Packet approvalOrderRefund(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr = returnsService.approvalOrderRefund(request);
		}catch (Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr);
	}

	/**
	 * @param request
	 * @return
	 * @api {post} /{project_name}/returns/reject 退款订单驳回
	 * @apiGroup returns
	 * @apiName returns_reject
	 * @apiDescription 退款订单审批
	 * @apiVersion 0.0.1
	 * @apiParam {string} return_number 退货单号 必填
	 * @apiParam {string} return_remark 驳回原因 必填
	 * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string} message 接口返回信息.
	 */
	@RequestMapping(value = "/reject", method = RequestMethod.POST)
	@ResponseBody
	public Packet rejectOrderRefund(HttpServletRequest request) {
		return Transform.GetResult(returnsService.rejectOrderRefund(request));
	}
		
	/**
	 * @param request
	 * @return
	 * @api {post} /{project_name}/returns/apply_logistics_money 
	 * @apiGroup returns
	 * @apiName apply_logistics_money
	 * @apiDescription 计算退货商品应退运费
	 * @apiVersion 0.0.1
	 * @apiParam {string} order_number 退货订单号 必填
	 * @apiParam {Object[]} return_sku_list 退货订单sku列表 必填
	 * @apiParam {return_sku_list.order_number}  退货订单号 必填
	 * @apiParam {return_sku_list.product_sku}  退货订单sku 必填
	 * @apiParam {return_sku_list.count}  退货sku数量 必填
	 * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string} message 接口返回消息.
	 * @apiSuccess {object} obj 接口返回信息.
	 * @apiSuccess {number} obj.return_logistics_money 运费 接口返回信息.
	 */
	@RequestMapping(value = "/apply_logistics_money", method = RequestMethod.POST)
	@ResponseBody
	public Packet applyLogisticsMoney(HttpServletRequest request) {
		return Transform.GetResult(returnsService.applyLogisticsMoney(request));
	}
	
   /**
    *
    * @api {post} /{project_name}/returns/returnInfo_list 退货退款列表
    * @apiGroup returns
    * @apiDescription  退货退款列表
    * @apiVersion 0.0.1

	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条   

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 退货退款列表
    * @apiSuccess {number} obj.ID    主键ID
    * @apiSuccess {string} obj.RETURN_NUMBER    退货单号
    * @apiSuccess {string} obj.USER_NAME    退货用户名
    * @apiSuccess {string} obj.USER_MANAGE_NAME    退货用户姓名
    * @apiSuccess {string} obj.APPLY_STATE    退货申请状态  1-待审核 2 很强通过 3 申请驳回 4 申请撤回
    * @apiSuccess {string} obj.RETURN_REMARK    退货说明
    * @apiSuccess {string} obj.CREATE_DATE    申请日期
    * @apiSuccess {string} obj.CHECK_USER_NAME    审核人用户名
    * @apiSuccess {string} obj.CHECK_USER_REALNAME    审核人用户姓名
    * @apiSuccess {string} obj.CHECK_DATE    审核时间
    * @apiSuccess {string} obj.CHECK_CANCEL_REASON    审核驳回原因
    */
	@RequestMapping(value = "/returnInfo_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryReturnInfoList(HttpServletRequest request) {
		return Transform.GetResult(returnsService.queryReturnInfoList(request));
	}
	/**
    *
    * @api {post} /{project_name}/returns/returnInfo_detail 退货退款详情
    * @apiGroup returns
    * @apiDescription  退货退款详情
    * @apiVersion 0.0.1

	* @apiParam {string} order_number				退货单号

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object} obj 退货退款详情
    * @apiSuccess {object} base_info 退货退款基本信息详情
    * @apiSuccess {number} obj.base_info.ID    主键ID
    * @apiSuccess {string} obj.base_info.RETURN_NUMBER    退货单号
    * @apiSuccess {string} obj.base_info.ORDER_NUMBER    订单号
    * @apiSuccess {string} obj.base_info.USER_NAME    退货用户名
    * @apiSuccess {string} obj.base_info.USER_MANAGE_NAME    退货用户姓名
    * @apiSuccess {number} obj.base_info.PRODUCT_MONEY    退货商品金额
    * @apiSuccess {number} obj.base_info.PRODUCT_COUNT    退货商品数量
    * @apiSuccess {number} obj.base_info.LOGISTICS_MONEY    退款物流费用
    * @apiSuccess {number} obj.base_info.DF_MONEY    退款代发费用
    * @apiSuccess {number} obj.base_info.RETURN_TOTAL_MONEY    退款总额
    * @apiSuccess {string} obj.base_info.STATE    退货单状态 0 草稿状态 1-待审核 2 审核通过 3 已驳回
    * @apiSuccess {string} obj.base_info.RETURN_REMARK    退货说明
    * @apiSuccess {string} obj.base_info.APPLY_USER_NAME    退货申请人用户名
    * @apiSuccess {string} obj.base_info.APPLY_USER_REALNAME    退货申请人用户姓名
    * @apiSuccess {string} obj.base_info.CREATE_DATE    申请日期
    * @apiSuccess {string} obj.base_info.CHECK_USER_NAME    审核人用户名
    * @apiSuccess {string} obj.base_info.CHECK_USER_REALNAME    审核人用户姓名
    * @apiSuccess {string} obj.base_info.CHECK_DATE    审核时间
    * @apiSuccess {string} obj.base_info.CHECK_CANCLE_REASON    审核驳回原因
    * @apiSuccess {object[]} product_info 退货单商品详情
    * @apiSuccess {number} obj.product_info.ID    主键ID
    * @apiSuccess {string} obj.product_info.RETURN_NUMBER    退货单号
    * @apiSuccess {string} obj.product_info.ORDER_NUMBER    订单号
    * @apiSuccess {number} obj.product_info.CODENUMBER    码号
    * @apiSuccess {number} obj.product_info.COUNT    数量
    * @apiSuccess {number} obj.product_info.PRODUCT_UNIT_PRICE    现销售单价
    * @apiSuccess {number} obj.product_info.PRODUCT_TOTAL_MONEY    商品总价
    * @apiSuccess {number} obj.product_info.PRODUCT_OLD_UNIT_PRICE    原折扣后单价
    * @apiSuccess {number} obj.product_info.PRODUCT_TOTAL_DISCOUNT_MONEY    优惠总金额
    * @apiSuccess {string} obj.product_info.USER_NAME    客户用户名（关联tbl_user_info的user_name）
    * @apiSuccess {string} obj.product_info.RETURN_DATE    退货单时间
    * @apiSuccess {number} obj.product_info.PRODUCT_SKU    sku
    * @apiSuccess {string} obj.product_info.PRODUCT_SKU_NAME    sku名称
    * @apiSuccess {string} obj.product_info.PRODUCT_ITEMNUMBER    货号
    * @apiSuccess {string} obj.product_info.PRODUCT_COLOR    颜色
    * @apiSuccess {string} obj.product_info.PRODUCT_SPECS    规格
    * @apiSuccess {number} obj.product_info.PRODUCT_OLDSALE_PRIZE    原批发价
    */
	@RequestMapping(value = "/returnInfo_detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryReturnInfoDetail(HttpServletRequest request) {
		return Transform.GetResult(returnsService.queryReturnInfoDetail(request));
	}
	
	/**
	 * @param request
	 * @return
	 * @api {post} /{project_name}/returns/returnInfo_check 退货退款审批
	 * @apiGroup returns
	 * @apiName returnInfo_check
	 * @apiDescription 退货退款审批
	 * @apiVersion 0.0.1
	 * 
	 * @apiParam {string} return_number 退货单号 必填
	 * 
	 * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string} message 接口返回信息.
	 */
	@RequestMapping(value = "/returnInfo_check", method = RequestMethod.POST)
	@ResponseBody
	public Packet checkReturnInfo(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr =returnsService.checkReturnInfo(request);
		}catch (Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr);
		//return Transform.GetResult(returnsService.checkReturnInfo(request));
	}
	
	@RequestMapping(value = "/mark_remark", method = RequestMethod.POST)
	@ResponseBody
	public Packet orderReturnMarkRemark(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr =returnsService.orderReturnMarkRemark(request);
		}catch (Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr);
	}
	
}