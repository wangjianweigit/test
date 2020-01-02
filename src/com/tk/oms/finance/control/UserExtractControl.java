package com.tk.oms.finance.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.finance.service.UserExtractService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 平台会员提现申请
 * @author songwangwen
 * @date  2017-5-8  下午2:21:02
 */
@Controller
@RequestMapping("user_extract")
public class UserExtractControl {
	@Resource
	private UserExtractService userExtractService;
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
        return Transform.GetResult(userExtractService.queryUserExtractList(request));
    }
	
	/**
     * @api {post} /{project_name}/user_extract/detail 获取平台会员提现申请详情
     * @apiName user_extract
     * @apiGroup user_extract
     * @apiDescription  获取平台会员提现申请详情
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
    public Packet queryUserExtractDetail(HttpServletRequest request) {
        return Transform.GetResult(userExtractService.queryUserExtractDetail(request));
    }
	/**
     * @api {post} /{project_name}/user_extract/edit  审批平台会员提现申请
     * @apiName user_extract
     * @apiGroup user_extract
     * @apiDescription  审批平台会员提现申请
     * @apiVersion 0.0.1
     * 
     * @apiParam {number}	id 						申请记录ID
     * @apiParam {string}	state 					申请单状态  1、待财务审核  ;2、待打款;  3、打款成功; 4、打款失败 ; 10、审核驳回  ; -1提现用户取消提现申请
     * @apiParam {string}	[reject_reason] 		申请驳回原因,驳回时必填
     * @apiParam {number}	public_user_id 			操作人ID
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 提现申请详情
     * 
     */
	@RequestMapping(value = "/approval", method = RequestMethod.POST)
    @ResponseBody
    public Packet editUserExtract(HttpServletRequest request) {
		ProcessResult pr= new ProcessResult();
    	try {
    		pr = userExtractService.approvalUserExtract(request);
		} catch (Exception e) {
			// TODO: handle exception
			pr.setMessage(e.toString());
		}
    	return Transform.GetResult(pr);
    }
	/**
	 * @api {post} /{project_name}/user_extract/edit  平台会员提现申请打款
	 * @apiName user_extract
	 * @apiGroup user_extract
	 * @apiDescription  平台会员提现申请打款
	 * @apiVersion 0.0.1
	 * 
	 * @apiParam {number}	id 						申请记录ID
	 * @apiParam {number}	public_user_id 		操作人ID
	 * 
	 * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string}     message 接口返回信息
	 * @apiSuccess {object[]}   obj 提现申请详情
	 * 
	 */
	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	@ResponseBody
	public Packet payUserExtract(HttpServletRequest request) {
		ProcessResult pr= new ProcessResult();
		try {
			pr = userExtractService.payUserExtract(request);
		} catch (Exception e) {
			// TODO: handle exception
			pr.setMessage(e.toString());
		}
		return Transform.GetResult(pr);
	}
}
