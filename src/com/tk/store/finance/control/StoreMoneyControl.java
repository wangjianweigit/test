package com.tk.store.finance.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.store.finance.service.StoreMoneyService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Controller
@RequestMapping("store_money")
public class StoreMoneyControl {
	@Resource
	private StoreMoneyService storeMoneyService;
	
	
	/**
    *
    * @api {post} /{project_name}/store_money/list  资金查询列表
    * @apiGroup store_money
    * @apiName list
    * @apiDescription  资金查询列表
    * @apiVersion 0.0.1
	
	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条   

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 收支记录列表
    * @apiSuccess {string} obj.STORE_NAME		门店名称
    * @apiSuccess {string} obj.USER_REALNAME	门店负责人
    * @apiSuccess {number} obj.ACCOUNT_BALANCE	账户余额
    * @apiSuccess {number} obj.WAIT_SETTLE_MONEY			待结算金额
    * @apiSuccess {number} obj.GOODS_DEPOSIT_BALANCE		押金余额
    * @apiSuccess {number} obj.GOODS_DEPOSIT		押金总额
    * @apiSuccess {number} obj.STORE_DEPOSIT		保证金
    * @apiSuccess {number} obj.WAIT_PAYMENT_MONEY		待缴款账单金额
    * 
    */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStoreMoneyListForPage(HttpServletRequest request) {
		return Transform.GetResult(storeMoneyService.queryStoreMoneyListForPage(request));
	}
	
	/**
    *
    * @api {post} /{project_name}/store_money/store_account_detail_list  收支记录查看金额详情列表
    * @apiGroup store_money
    * @apiName list
    * @apiDescription  收支记录查看金额详情列表
    * @apiVersion 0.0.1
	
	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条   
	* @apiParam {number} store_id				店铺id
	* @apiParam {number} record_channel			收付渠道（佣金、货品押金、店铺货品押金）

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 收支记录列表
    * @apiSuccess {number} obj.RECORD_TYPE		收付类型（1-收款 2-付款）
    * @apiSuccess {number} obj.DOCKET_TYPE		单据类型（押金【门店订货、门店退货、销售结算、销售退货、充值】 佣金【结算、提现、退货】）
    * @apiSuccess {string} obj.RECORD_NUMBER	收付单号
    * @apiSuccess {number} obj.MONEY	金额
    * @apiSuccess {string} obj.CREATE_DATE			创建时间
    * @apiSuccess {string} obj.DOCKET_NUMBER		单据号码
    */
	@RequestMapping(value = "/store_account_detail_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStoreAccountDetailList(HttpServletRequest request) {
		return Transform.GetResult(storeMoneyService.queryStoreAccountDetailList(request));
	}
	
	/**
    *
    * @api {post} /{project_name}/store_money/store_income_spend_money  查询收入支出金额
    * @apiGroup store_money
    * @apiName list
    * @apiDescription  查询收入支出金额
    * @apiVersion 0.0.1
	
	* @apiParam {number} store_id				店铺id
	* @apiParam {number} record_channel			收付渠道（佣金、货品押金、店铺货品押金）

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 收入支出金额
    * @apiSuccess {number} obj.INCOME		收入金额
    * @apiSuccess {number} obj.SPEND		支出金额
    */
	@RequestMapping(value = "/store_income_spend_money", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStoreIncomeSpendMoney(HttpServletRequest request) {
		return Transform.GetResult(storeMoneyService.queryStoreIncomeSpendMoney(request));
	}
	
	/**
    *
    * @api {post} /{project_name}/store_money/store_wait_settle_detail  待结算金额记录
    * @apiGroup store_money
    * @apiName list
    * @apiDescription  待结算金额记录
    * @apiVersion 0.0.1
	
	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条   

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 待结算金额记录
    * @apiSuccess {string} obj.TRADE_NUMBER		         交易号
    * @apiSuccess {string} obj.TRADE_MONEY		          金额
    * @apiSuccess {number} obj.PRODUCT_COUNT	           数量
    * @apiSuccess {number} obj.TRADE_CREATE_DATE	创建时间
    */
	@RequestMapping(value = "/store_wait_settle_detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStoreWaitSettleDetail(HttpServletRequest request) {
		return Transform.GetResult(storeMoneyService.queryStoreWaitSettleDetail(request));
	}
	
	
	/**
	* @api {post} /{project_name}/store_money/deposit_recharge 押金充值
    * @apiGroup deposit_recharge
    * @apiDescription  押金充值
    * @apiVersion 0.0.1
    * 
    * @apiParam {number} store_id   店铺id
    * @apiParam {number} money      金额
    * @apiParam {string} vouchar_img_url      上传凭证
    * @apiParam {string} remark      备注
    * 
    * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
	 */
	@RequestMapping(value = "/deposit_recharge", method = RequestMethod.POST)
	@ResponseBody
	public Packet storeMoneyDepositRecharge(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = storeMoneyService.storeMoneyDepositRecharge(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
    *
    * @api {post} /{project_name}/store_money/deposit_recharge_record  押金充值记录列表
    * @apiGroup store_money
    * @apiName list
    * @apiDescription  押金充值记录列表
    * @apiVersion 0.0.1
	
	 @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条   

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 收入支出金额
    * @apiSuccess {number} obj.STATE		1：待验款、2:：验款通过 、 3：:验款驳回
    * @apiSuccess {string} obj.STORE_LOGIN_NAME		用户名
    * @apiSuccess {string} obj.STORE_MANAGE_NAME		姓名
    * @apiSuccess {number} obj.MONEY		充值金额
    * @apiSuccess {string} obj.CREATE_DATE		充值时间
    * @apiSuccess {string} obj.CREATE_USER_REALNAME		充值人
    * @apiSuccess {string} obj.APPROVAL_DATE		审批时间
    * @apiSuccess {string} obj.APPROVAL_USER_REALNAME			审批人
    */
	@RequestMapping(value = "/deposit_recharge_record", method = RequestMethod.POST)
	@ResponseBody
	public Packet storeMoneyDepositRechargeRecord(HttpServletRequest request) {
		return Transform.GetResult(storeMoneyService.storeMoneyDepositRechargeRecord(request));
	}
	
	/**
    *
    * @api {post} /{project_name}/store_money/deposit_recharge_approval_detail  押金充值审批详情
    * @apiGroup store_money
    * @apiName list
    * @apiDescription  押金充值审批详情
    * @apiVersion 0.0.1
	
	* @apiParam {number} id				主键id 

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 收入支出金额
    * @apiSuccess {number} obj.STATE		1：待验款、2:：验款通过 、 3：:验款驳回
    * @apiSuccess {string} obj.STORE_LOGIN_NAME		用户名
    * @apiSuccess {string} obj.STORE_MANAGE_NAME		姓名
    * @apiSuccess {number} obj.MONEY		充值金额
    * @apiSuccess {string} obj.CREATE_DATE		充值时间
    * @apiSuccess {string} obj.CREATE_USER_REALNAME		充值人
    * @apiSuccess {string} obj.APPROVAL_DATE		审批时间
    * @apiSuccess {string} obj.APPROVAL_USER_REALNAME			审批人
    */
	@RequestMapping(value = "/deposit_recharge_approval_detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet storeMoneyDepositRechargeApprovalDetail(HttpServletRequest request) {
		return Transform.GetResult(storeMoneyService.storeMoneyDepositRechargeApprovalDetail(request));
	}
	
	/**
	* @api {post} /{project_name}/store_money/deposit_recharge_approval 押金充值审批
    * @apiGroup deposit_recharge_approval
    * @apiDescription  押金充值审批
    * @apiVersion 0.0.1
    * 
    * @apiParam {number} id   主键id
    * @apiParam {number} state      1：待验款、2:：验款通过 、 3：:验款驳回
    * @apiParam {string} rejected_reason      驳回原因
    * 
    * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
	 */
	@RequestMapping(value = "/deposit_recharge_approval", method = RequestMethod.POST)
	@ResponseBody
	public Packet storeMoneyDepositRechargeApproval(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = storeMoneyService.storeMoneyDepositRechargeApproval(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
}
