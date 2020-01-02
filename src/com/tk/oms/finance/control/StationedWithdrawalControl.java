package com.tk.oms.finance.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.finance.service.StationedWithdrawalService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 入驻商提现申请
 * @author songwangwen
 * @date  2017-5-6  下午8:21:16
 */
@Controller
@RequestMapping("stationed_withdrawal")
public class StationedWithdrawalControl {
	@Resource
	private StationedWithdrawalService stationedWithdrawalService;
	/**
     * @api {post} /{project_name}/stationed_withdrawal/list 入驻商提现申请列表
     * @apiName stationed_withdrawal
     * @apiGroup stationed_withdrawal
     * @apiDescription  入驻商提现申请列表
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
    public Packet queryStationedWithdrawalList(HttpServletRequest request) {
        return Transform.GetResult(stationedWithdrawalService.queryStationedWithdrawalList(request));
    }
	
	/**
     * @api {post} /{project_name}/stationed_withdrawal/detail 入驻商提现申请列表
     * @apiName stationed_withdrawal
     * @apiGroup stationed_withdrawal
     * @apiDescription  入驻商提现申请列表
     * @apiVersion 0.0.1
     * 
     * @apiParam {number}	id 			申请记录ID
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 提现申请详情
     * 
     */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryStationedWithdrawalDetail(HttpServletRequest request) {
        return Transform.GetResult(stationedWithdrawalService.queryStationedWithdrawalDetail(request));
    }
	/**
     * @api {post} /{project_name}/stationed_withdrawal/approval 入驻商提现申请审批
     * @apiName stationed_withdrawal
     * @apiGroup stationed_withdrawal
     * @apiDescription  入驻商提现申请审批
     * @apiVersion 0.0.1
     * 
     * @apiParam {number}	id 						申请记录ID
     * @apiParam {string}	state 					申请单状态  1、待财务审核  ;2、待打款;  3、打款成功; 4、打款失败 ; 10、审核驳回  ; -1提现用户取消提现申请
     * @apiParam {string}	[reject_reason] 		申请驳回原因,驳回时必填
     * @apiParam {number}	public_user_name 		操作人ID
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 提现申请详情
     * 
     */
	@RequestMapping(value = "/approval", method = RequestMethod.POST)
    @ResponseBody
    public Packet approvalStationedWithdrawal(HttpServletRequest request) {
        ProcessResult pr= new ProcessResult();
		try {
			pr = stationedWithdrawalService.approvalStationedWithdrawal(request);
		} catch (Exception e) {
			// TODO: handle exception
			pr.setMessage(e.toString());
		}
		return Transform.GetResult(pr);
    }
	/**
	 * @api {post} /{project_name}/stationed_withdrawal/approval 入驻商提现申请打款
	 * @apiName stationed_withdrawal
	 * @apiGroup stationed_withdrawal
	 * @apiDescription  入驻商提现申请打款
	 * @apiVersion 0.0.1
	 * 
	 * @apiParam {number}	id 						申请记录ID
	 * @apiParam {string}	state 					申请单状态  1、待财务审核  ;2、待打款;  3、打款成功; 4、打款失败 ; 10、审核驳回  ; -1提现用户取消提现申请
	 * @apiParam {string}	[reject_reason] 		申请驳回原因,驳回时必填
	 * @apiParam {number}	public_user_name 		操作人ID
	 * 
	 * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string}     message 接口返回信息
	 * @apiSuccess {object[]}   obj 提现申请详情
	 * 
	 */
	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	@ResponseBody
	public Packet payStationedWithdrawal(HttpServletRequest request) {
		ProcessResult pr= new ProcessResult();
		try {
			pr = stationedWithdrawalService.payStationedWithdrawal(request);
		} catch (Exception e) {
			// TODO: handle exception
			pr.setMessage(e.toString());
		}
		return Transform.GetResult(pr);
	}

	/**
	 * @api {post} /{project_name}/stationed_withdrawal/mark_success 标记成功
	 * @apiName stationed_withdrawal
	 * @apiGroup mark_success
	 * @apiDescription  标记成功
	 * @apiVersion 0.0.1
	 *
	 * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string}     message 接口返回信息
	 *
	 */
	@RequestMapping(value = "/mark_success", method = RequestMethod.POST)
	@ResponseBody
	public Packet markSuccess(HttpServletRequest request) {
		ProcessResult pr= new ProcessResult();
		try {
			pr = stationedWithdrawalService.markSuccess(request);
		} catch (Exception e) {
			// TODO: handle exception
			pr.setMessage(e.toString());
		}
		return Transform.GetResult(pr);
	}
	/**
	 * @api {post} /{project_name}/stationed_withdrawal/look_state 查看打款状态
	 * @apiName stationed_withdrawal
	 * @apiGroup look_state
	 * @apiDescription  查看打款状态
	 * @apiVersion 0.0.1
	 *
	 * @apiParam {number}	id 						申请记录ID
	 *
	 * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string}     message 接口返回信息
	 *
	 */
	@RequestMapping(value = "/look_state", method = RequestMethod.POST)
	@ResponseBody
	public Packet lookState(HttpServletRequest request) {
		ProcessResult pr= new ProcessResult();
		try {
			pr = stationedWithdrawalService.lookState(request);
		} catch (Exception e) {
			// TODO: handle exception
			pr.setMessage(e.toString());
		}
		return Transform.GetResult(pr);
	}
	/**
	 * @api {post} /{project_name}/stationed_withdrawal/mark_fail_charge 标记失败并反充
	 * @apiName stationed_withdrawal
	 * @apiGroup mark_fail_charge
	 * @apiDescription  标记失败并反充
	 * @apiVersion 0.0.1
	 *
	 * @apiParam {number}	id 						申请记录ID
	 *
	 * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string}     message 接口返回信息
	 */
	@RequestMapping(value = "/mark_fail_charge", method = RequestMethod.POST)
	@ResponseBody
	public Packet markFailCharge(HttpServletRequest request) {
		ProcessResult pr= new ProcessResult();
		try {
			pr = stationedWithdrawalService.markFailCharge(request);
		} catch (Exception e) {
			// TODO: handle exception
			pr.setMessage(e.toString());
		}
		return Transform.GetResult(pr);
	}

	/**
	 * @api {post} /{project_name}/stationed_withdrawal/update_pay_success_state 修改打款单为打款成功状态
	 * @apiName stationed_withdrawal
	 * @apiGroup update_pay_success_state
	 * @apiDescription  修改打款单为打款成功状态
	 * @apiVersion 0.0.1
	 *
	 * @apiParam {number}	id 						申请记录ID
	 *
	 * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string}     message 接口返回信息
	 *
	 */
	@RequestMapping(value = "/update_pay_success_state", method = RequestMethod.POST)
	@ResponseBody
	public Packet updatePaySuccessState(HttpServletRequest request) {
		ProcessResult pr= new ProcessResult();
		try {
			pr = stationedWithdrawalService.updatePaySuccessState(request);
		} catch (Exception e) {
			// TODO: handle exception
			pr.setMessage(e.toString());
		}
		return Transform.GetResult(pr);
	}
}
