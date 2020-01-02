package com.tk.oms.finance.control;

import com.tk.oms.finance.service.RechargeAuditingService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("recharge_auditing")
public class RechargeAuditingControl {
	@Resource
	private RechargeAuditingService rechargeAuditingService;
	/**
     * @api {post} /{project_name}/recharge_auditing/list
     * @apiName list
     * @apiGroup recharge_auditing
     * @apiDescription  充值审批列表
     * @apiVersion 0.0.1
     * 
     * @apiParam {number}	pageIndex 			开始页码
     * @apiParam {number}	pageSize 			每页数据量
     * @apiParam {number}	record_number 		充值单号
     * @apiParam {string}	login_name 			充值用户名
     * @apiParam {string}	user_realname 		充值用户姓名
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 充值审批列表
     * @apiSuccess {number}     total 充值审批数量
     * @apiSuccess {number}   	obj.id 收支记录ID
     * @apiSuccess {string}   	obj.record_number 收付单号
     * @apiSuccess {number}   	obj.record_item_number 收付项次
     * @apiSuccess {string}   	obj.record_channel 收付渠道
     * @apiSuccess {string}   	obj.record_type 收付类型
     * @apiSuccess {string}   	obj.remark 摘要
     * @apiSuccess {date}     	obj.create_date 创建日期
     * @apiSuccess {string}     obj.collect_user_name 收款人用户名
     * @apiSuccess {string}     obj.collect_user_manager_name 收款人姓名
     * @apiSuccess {string}     obj.accountants_subject_id 会计科目ID
     * @apiSuccess {string}     obj.accountants_subject_name 会计科目名称
     * @apiSuccess {string}     obj.parent_acc_subject_id 上级科目ID
     * @apiSuccess {string}     obj.parent_acc_subject_name 上级科目名称
     * @apiSuccess {number}     obj.money 金额
     * @apiSuccess {number}     obj.count 数量
     * @apiSuccess {number}     obj.surplus_money 	余额
     * @apiSuccess {string}     obj.state			状态
     * @apiSuccess {string}     obj.docket_number	单据号码
     * @apiSuccess {string}     obj.create_user		创建人用户名
     * @apiSuccess {string}     obj.user_name		信息所属用户的用户名
     * @apiSuccess {string}     obj.check_user_name	验款人用户名
     * @apiSuccess {string}     obj.check_user_business_name	验款人名称
     * @apiSuccess {date}     	obj.check_date			验款日期
     * @apiSuccess {string}     obj.check_reject_reason	验款驳回原因
     * @apiSuccess {string}     obj.turnover_number		收付关联号
     * @apiSuccess {string}     obj.third_number		第三方支付号码，只有支付类型为  支付宝 微信  银联  时此处才有值
     * @apiSuccess {string}     obj.voucher_img_url		凭证图片地址
     * @apiSuccess {string}     obj.ywjl_user_name		业务经理用户名
     * @apiSuccess {number}     obj.md_id				门店ID
     * @apiSuccess {string}     obj.ywy_user_name		下单人用户名	
     * 
     */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRechargeAuditingList(HttpServletRequest request) {
        return Transform.GetResult(rechargeAuditingService.queryRechargeAuditingList(request));
    }
	
	/**
     * @api {post} /{project_name}/recharge_auditing/detail
     * @apiName detail
     * @apiGroup recharge_auditing
     * @apiDescription  充值审批详情
     * @apiVersion 0.0.1
     * 
     * @apiParam {string} record_number 		收付单号
     * @apiParam {string} turnover_number 		收付关联号
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object}   	obj 
     * @apiSuccess {number}   	obj.id 收支记录ID
     * @apiSuccess {string}   	obj.record_number 收付单号
     * @apiSuccess {number}   	obj.record_item_number 收付项次
     * @apiSuccess {string}   	obj.record_channel 收付渠道
     * @apiSuccess {string}   	obj.record_type 收付类型
     * @apiSuccess {string}   	obj.remark 摘要
     * @apiSuccess {date}     	obj.create_date 创建日期
     * @apiSuccess {string}     obj.collect_user_name 收款人用户名
     * @apiSuccess {string}     obj.collect_user_manager_name 收款人姓名
     * @apiSuccess {string}     obj.accountants_subject_id 会计科目ID
     * @apiSuccess {string}     obj.accountants_subject_name 会计科目名称
     * @apiSuccess {string}     obj.parent_acc_subject_id 上级科目ID
     * @apiSuccess {string}     obj.parent_acc_subject_name 上级科目名称
     * @apiSuccess {number}     obj.money 金额
     * @apiSuccess {number}     obj.count 数量
     * @apiSuccess {number}     obj.surplus_money 	余额
     * @apiSuccess {string}     obj.state			状态
     * @apiSuccess {string}     obj.docket_number	单据号码
     * @apiSuccess {string}     obj.create_user		创建人用户名
     * @apiSuccess {string}     obj.user_name		信息所属用户的用户名
     * @apiSuccess {string}     obj.check_user_name	验款人用户名
     * @apiSuccess {string}     obj.check_user_business_name	验款人名称
     * @apiSuccess {date}     	obj.check_date			验款日期
     * @apiSuccess {string}     obj.check_reject_reason	验款驳回原因
     * @apiSuccess {string}     obj.turnover_number		收付关联号
     * @apiSuccess {string}     obj.third_number		第三方支付号码，只有支付类型为  支付宝 微信  银联  时此处才有值
     * @apiSuccess {string}     obj.voucher_img_url		凭证图片地址
     * @apiSuccess {string}     obj.ywjl_user_name		业务经理用户名
     * @apiSuccess {number}     obj.md_id				门店ID
     * @apiSuccess {string}     obj.ywy_user_name		下单人用户名	
     * 
     */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRechargeAuditingDetail(HttpServletRequest request) {
        return Transform.GetResult(rechargeAuditingService.queryRechargeAuditingDetail(request));
    }
	
	/**
     * @api {post} /{project_name}/recharge_auditing/check
     * @apiName check
     * @apiGroup recharge_auditing
     * @apiDescription  充值审批通过、驳回
     * @apiVersion 0.0.1
     * 
     * @apiParam {string} operate_type 		操作类型(02：通过，03：驳回)
     * @apiParam {string} user_name 		用户名
     * @apiParam {string} regect_reason 	驳回原因
     * @apiParam {string} record_number 	充值单号
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
    @ResponseBody
    public Packet rechargeAuditing(HttpServletRequest request) {
        return Transform.GetResult(rechargeAuditingService.rechargeAuditing(request));
    }
}
