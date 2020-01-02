package com.tk.store.returns.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.store.returns.service.StoreReturnsService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : ReturnsControl
 * 店铺退货单管理
 * 
 * @author shifan
 * @version 1.00
 * @date 2018-3-1
 */
@Controller
@RequestMapping("/store_returns")
public class StoreReturnsControl {

	@Resource
	private StoreReturnsService storeReturnsService;
	
   /**
    *
    * @api {post} /{project_name}/store_returns/list 店铺退货单列表
    * @apiGroup store_returns
    * @apiDescription  店铺退货单列表
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
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryReturnList(HttpServletRequest request) {
		return Transform.GetResult(storeReturnsService.queryReturnList(request));
	}
	/**
    *
    * @api {post} /{project_name}/store_returns/detail 店铺退货单详情
    * @apiGroup store_returns
    * @apiDescription  店铺退货单详情
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
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryReturnDetail(HttpServletRequest request) {
		return Transform.GetResult(storeReturnsService.queryReturnDetail(request));
	}
	
	/**
	 * @param request
	 * @return
	 * @api {post} /{project_name}/store_returns/audit 店铺退货单合作商审批
	 * @apiGroup store_returns
	 * @apiName return_audit
	 * @apiDescription 店铺退货单合作商审批
	 * @apiVersion 0.0.1
	 * 
	 * @apiParam {string} return_number 退货单号 必填
	 * 
	 * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string} message 接口返回信息.
	 */
	@RequestMapping(value = "/audit", method = RequestMethod.POST)
	@ResponseBody
	public Packet auditReturnInfo(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr =storeReturnsService.auditReturn(request);
		}catch (Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr);
	}
	
}