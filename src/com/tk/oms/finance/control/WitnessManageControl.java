package com.tk.oms.finance.control;

import com.tk.oms.finance.service.WitnessManageService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 见证宝管理
 * @zhenghui
 */
@Controller
@RequestMapping("/witness_manage")
public class WitnessManageControl {

    @Resource
    private WitnessManageService witnessManageService;

    /**
     * @api {post} /{project_name}/user_extract/list 平台会员提现申请列表
     * @apiName user_extract
     * @apiGroup user_extract
     * @apiDescription  平台会员提现申请列表
     * @apiVersion 0.0.1
     *
     * @apiParam {number}	pageIndex 			开始页码
     * @apiParam {number}	pageSize 			每页数据量
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 提现申请详情列表
     *
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryUserExtractList(HttpServletRequest request) {
        return Transform.GetResult(witnessManageService.queryBankTransactionDetail(request));
    }


    /**
     * @api {post} /{project_name}/witness_manage/transaction_detail 查询见证宝交易明细列表
     * @apiName transaction_detail
     * @apiGroup transaction_detail
     * @apiDescription  查询见证宝交易明细列表
     * @apiVersion 0.0.1
     *
     * @apiParam {number}	pageIndex 			开始页码
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 见证宝交易明细列表
     *
     */
    @RequestMapping(value = "/transaction_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryTransaction_detail(HttpServletRequest request) {
        return Transform.GetResult(witnessManageService.queryBankTransactionDetail(request));
    }

    /**
     * @api {post} /{project_name}/witness_manage/withdraw_deposit_detail 见证宝充值提现明细列表
     * @apiName withdraw_deposit_detail
     * @apiGroup withdraw_deposit_detail
     * @apiDescription  见证宝充值提现明细列表
     * @apiVersion 0.0.1
     *
     * @apiParam {number}	pageIndex 			开始页码
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 见证宝充值提现明细列表
     *
     */
    @RequestMapping(value = "/withdraw_deposit_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryWithdrawDepositDetail(HttpServletRequest request) {
        return Transform.GetResult(witnessManageService.queryWithdrawDepositDetail(request));
    }

    /**
     * @api {post} /{project_name}/witness_manage/account_balance 账户余额
     * @apiName witness_manage
     * @apiGroup witness_manage
     * @apiDescription  用户见证宝账户余额查询
     * @apiVersion 0.0.1
     *
     * @apiParam {number}	pageIndex 			开始页码
     * @apiParam {number}	selectFlag 			查询标志（2、普通会员子账户；3、功能子账户）
     * @apiParam {number}	user_id 			用户ID
     *
     * @apiSuccess {boolean} state              接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}  message            接口返回信息
     * @apiSuccess {object[]} obj               提现申请详情列表
     * @apiSuccess {boolean} custAcctId         子账户
     * @apiSuccess {boolean} custType           子账户属性：1、普通子账户；2、挂账子账户；3、手续费子账户；4、利息子账户；5、平台担保子账户；6、新零售
     * @apiSuccess {boolean} thirdCustId：      会员ID
     * @apiSuccess {boolean} custName：         账户名称
     * @apiSuccess {boolean} totalBalance：     账户可用余额
     * @apiSuccess {boolean} totalTranOutAmount 账户可提现余额
     * @apiSuccess {boolean} tranDate           维护日期
     */
    @RequestMapping(value = "/account_balance", method = RequestMethod.POST)
    @ResponseBody
    public Packet userAccountBalance(HttpServletRequest request){
        return Transform.GetResult(this.witnessManageService.userAccountBalance(request));
    }

    /**
     * @api {post} /{project_name}/witness_manage/stationed_user_list 获取入驻商列表
     * @apiName witness_manage
     * @apiGroup witness_manage
     * @apiDescription  分页获取入驻商列表
     * @apiVersion 0.0.1
     *
     * @apiParam {number}	pageIndex 			       开始页码
     * @apiParam {number}	pageSize			       页面记录数
     *
     * @apiSuccess {boolean} state                     接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}  message                   接口返回信息
     * @apiSuccess {object[]} obj                      提现申请详情列表
     * @apiSuccess {boolean} obj.id                    入驻商ID
     * @apiSuccess {boolean} obj.user_name             入驻商用户名
     * @apiSuccess {boolean} obj.legal_personality     法人代表（企业法人的姓名）
     * @apiSuccess {boolean} obj.contact_phone_number  手机号码
     * @apiSuccess {boolean} obj.create_date           注册时间
     * @apiSuccess {boolean} obj.company_name          公司名称（企业名称）
     */
    @RequestMapping(value = "/stationed_user_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryStationedUserListForPage(HttpServletRequest request){
        return Transform.GetResult(this.witnessManageService.queryStationedUserListForPage(request));
    }

    /**
     * @api {post} /{project_name}/witness_manage/allocating_detail 查询清分明细列表
     * @apiName allocating_detail
     * @apiGroup allocating_detail
     * @apiDescription  分页获取清分明细列表
     * @apiVersion 0.0.1
     *
     * @apiParam {number}	pageIndex 			       开始页码
     * @apiParam {number}	pageSize			       页面记录数
     *
     * @apiSuccess {boolean} state                     接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}  message                   接口返回信息
     * @apiSuccess {object[]} obj                      清分明细列表
     */
    @RequestMapping(value = "/allocating_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAllocatingDetailForPage(HttpServletRequest request){
        return Transform.GetResult(this.witnessManageService.queryAllocatingDetailForPage(request));
    }

    /**
     * @api {post} /{project_name}/witness_manage/refund_detail 查询退款明细列表
     * @apiName refund_detail
     * @apiGroup refund_detail
     * @apiDescription  分页获取退款明细列表
     * @apiVersion 0.0.1
     *
     * @apiParam {number}	pageIndex 			       开始页码
     * @apiParam {number}	pageSize			       页面记录数
     *
     * @apiSuccess {boolean} state                     接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}  message                   接口返回信息
     * @apiSuccess {object[]} obj                      退款明细列表
     */
    @RequestMapping(value = "/refund_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRefundDetailForPage(HttpServletRequest request){
        return Transform.GetResult(this.witnessManageService.queryRefundDetailForPage(request));
    }

    /**
     * @api {post} /{project_name}/witness_manage/income_detail 查询收入明细列表
     * @apiName income_detail
     * @apiGroup income_detail
     * @apiDescription  分页获取收入明细列表
     * @apiVersion 0.0.1
     *
     * @apiParam {number}	pageIndex 			       开始页码
     * @apiParam {number}	pageSize			       页面记录数
     *
     * @apiSuccess {boolean} state                     接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}  message                   接口返回信息
     * @apiSuccess {object[]} obj                      收入明细列表
     */
    @RequestMapping(value = "/income_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryIncomeDetailForPage(HttpServletRequest request){
        return Transform.GetResult(this.witnessManageService.queryIncomeDetailForPage(request));
    }

    /**
     * @api {post} /{project_name}/witness_manage/clear_funds_detail 查询资金清分明细列表
     * @apiName clear_funds_detail
     * @apiGroup clear_funds_detail
     * @apiDescription   查询资金清分明细列表
     * @apiVersion 0.0.1
     *
     * @apiParam {number}	pageIndex 			       开始页码
     * @apiParam {number}	pageSize			       页面记录数
     *
     * @apiSuccess {boolean} state                     接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}  message                   接口返回信息
     * @apiSuccess {object[]} obj                      资金清分明细
     */
    @RequestMapping(value = "/clear_funds_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryClearFundsDetailForPage(HttpServletRequest request){
        return Transform.GetResult(this.witnessManageService.queryClearingFundsDetailForPage(request));
    }

    /**
     * @api{post} /{oms_server}/witness_manage/clear_funds_details  资金清分明细详情
     * @apiGroup clear_funds_details
     * @apiName clear_funds_details
     * @apiDescription  资金清分明细详情
     * @apiVersion 0.0.1
     *
     * @apiParam   {string} order_number         订单号
     *
     * @apiSuccess  {boolean} state    接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess  {string} message   接口返回信息
     */
    @RequestMapping(value = "/clear_funds_details", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryClearFundsDetails(HttpServletRequest request){
        return Transform.GetResult(this.witnessManageService.queryClearFundsDetails(request));
    }

    /**
     * @api{post} /{oms_server}/witness_manage/clear_funds_info  清分类型，清分明细查询
     * @apiGroup clear_funds_info
     * @apiName clear_funds_info
     * @apiDescription  清分类型，清分明细查询
     * @apiVersion 0.0.1
     *
     * @apiParam   {string} order_number         订单号
     * @apiParam   {number} stationed_user_id    入驻商ID
     *
     * @apiSuccess  {boolean} state    接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess  {string} message   接口返回信息
     */
    @RequestMapping(value = "/clear_funds_info", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryClearFundsInfo(HttpServletRequest request){
        return Transform.GetResult(this.witnessManageService.queryClearFundsInfo(request));
    }

    /**
     * @api{post} /{oms_server}/witness_manage/other_charges_detail  查询代发费、运费详情
     * @apiGroup other_charges_detail
     * @apiName other_charges_detail
     * @apiDescription  查询代发费、运费详情
     * @apiVersion 0.0.1
     *
     * @apiParam   {string} order_number         订单号
     *
     * @apiSuccess  {boolean} state    接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess  {string} message   接口返回信息
     */
    @RequestMapping(value = "/other_charges_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryOtherChargesDetail(HttpServletRequest request){
        return Transform.GetResult(this.witnessManageService.queryOtherChargesDetail(request));
    }


    /**
     * @api {post}/{scs_server}/witness_manage/sd_account_balance 获取(衫徳)见证宝账户余额
     * @apiGroup
     * @apiName sd_account_balance
     * @apiDescription 获取(衫徳)见证宝账户余额
     * @apiVersion 0.0.1
     *
     * @apiSucess {boolean} state   接口获取数据是否成功.true:成功  false:失败
     * @apiSucess {string} message  接口返回信息
     */
    @RequestMapping(value = "/sd_account_balance", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAccountBalance() {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.witnessManageService.querySdAccountBalance();
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post}/{scs_server}/witness_manage/sd_account_balance_withdrawal (衫徳)见证宝账户余额提现
     * @apiGroup
     * @apiName sd_account_balance_withdrawal
     * @apiDescription (衫徳)见证宝账户余额提现
     * @apiVersion 0.0.1
     *
     * @apiSucess {boolean} state   接口获取数据是否成功.true:成功  false:失败
     * @apiSucess {string} message  接口返回信息
     */
    @RequestMapping(value = "/sd_account_withdrawal", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAccountWithdrawal(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.witnessManageService.sdAccountWithdrawal(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post}/{scs_server}/witness_manage/sd_account_balance_withdrawal (衫徳)见证宝账户余额提现
     * @apiGroup
     * @apiName sd_account_balance_withdrawal
     * @apiDescription (衫徳)见证宝账户余额提现
     * @apiVersion 0.0.1
     *
     * @apiSucess {boolean} state   接口获取数据是否成功.true:成功  false:失败
     * @apiSucess {string} message  接口返回信息
     */
    @RequestMapping(value = "/sd_account_withdrawal_record", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAccountWithdrawalRecord(HttpServletRequest request) {
        return Transform.GetResult(this.witnessManageService.querySdAccountWithdrawalRecord(request));
    }


    /**
     * @api {post} /{project_name}/witness_manage/refund_return_detail 查询退款退货明细列表
     * @apiName refund_return_detail
     * @apiGroup refund_return_detail
     * @apiDescription   查询退款退货细列表
     * @apiVersion 0.0.1
     *
     * @apiParam {number}	pageIndex 			       开始页码
     * @apiParam {number}	pageSize			       页面记录数
     *
     * @apiSuccess {boolean} state                     接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}  message                   接口返回信息
     * @apiSuccess {object[]} obj                      退款退货明细
     */
    @RequestMapping(value = "/refund_return_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRefundReturnDetailForPage(HttpServletRequest request){
        return Transform.GetResult(this.witnessManageService.queryRefundReturnDetailForPage(request));
    }
    
    /**
     * @api{post} /{oms_server}/witness_manage/refund_return_details  资金清分明细详情
     * @apiGroup refund_return_details
     * @apiName refund_return_details
     * @apiDescription  退款退货详情
     * @apiVersion 0.0.1
     *
     * @apiParam   {string} return_number        退货单号
     *
     * @apiSuccess  {boolean} state    接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess  {string} message   接口返回信息
     */
    @RequestMapping(value = "/refund_return_details", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRefundReturnDetails(HttpServletRequest request){
        return Transform.GetResult(this.witnessManageService.queryRefundReturnDetails(request));
    }
    /**
     * @api{post} /{oms_server}/witness_manage/refund_return_info  退款类型，退款明细查询
     * @apiGroup refund_return_info
     * @apiName refund_return_info
     * @apiDescription  退款类型，退款明细查询
     * @apiVersion 0.0.1
     *
     * @apiParam   {string} return_number         订单号
     * @apiParam   {number} user_id    入驻商ID
     *
     * @apiSuccess  {boolean} state    接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess  {string} message   接口返回信息
     */
    @RequestMapping(value = "/refund_return_info", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRefundReturnInfo(HttpServletRequest request){
        return Transform.GetResult(this.witnessManageService.queryRefundReturnInfo(request));
    }

    /**
     * @api{post} /{oms_server}/witness_manage/withdraw_check  提现验证
     * @apiGroup witness_manage
     * @apiName witness_manage_withdraw_check
     * @apiDescription  提现验证
     * @apiVersion 0.0.1
     *
     * @apiParam   {string} id        银行卡编号
     *
     * @apiSuccess  {boolean} state    接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess  {string} message   接口返回信息
     */
    @RequestMapping(value = "/withdraw_check", method = RequestMethod.POST)
    @ResponseBody
    public Packet withdrawCheck(HttpServletRequest request){
        return Transform.GetResult(this.witnessManageService.withdrawCheck(request));
    }

    /**
     * @api{post} /{oms_server}/witness_manage/clear_funds_list  清分明细列表查询
     * @apiGroup clear_funds_list
     * @apiName clear_funds_list
     * @apiDescription  清分明细列表查询
     * @apiVersion 0.0.1
     *
     * @apiParam   {string} order_number         订单号
     * @apiParam   {number} stationed_user_id    入驻商ID
     * @apiParam   {string} remark               子账户类型
     *
     * @apiSuccess  {boolean} state    接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess  {string} message   接口返回信息
     */
    @RequestMapping(value = "/clear_funds_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryClearFundsList(HttpServletRequest request){
        return Transform.GetResult(this.witnessManageService.queryClearFundsList(request));
    }
    
}
