package com.tk.oms.finance.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.finance.service.StoreContributoryService;
import com.tk.oms.finance.service.SysUserAuthApprovalService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Controller
@RequestMapping("/sys_user_auth_approval")
public class SysUserAuthApprovalControl {
	@Resource
	private SysUserAuthApprovalService sysUserAuthApprovalService;
	/**
     * @api {post} /{project_name}/sys_user_auth_approval/list 查询用户认证审批列表
     * @apiName list
     * @apiGroup sys_user_auth_approval
     * @apiDescription  查询用户认证审批列表
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} pageIndex 		开始页码
     * @apiParam {number} pageSize 			每页数据量
     * @apiParam {string} user_name 		用户名
     * @apiParam {string} user_real_name 	姓名
     * @apiParam {string} user_manage_mobilephone 手机号
     * @apiParam {list} state 认证状态：0、待审核；1、已审批；2、驳回
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {number}     total 					                     总数
     * @apiSuccess {object[]}   obj 					                      查询缴款单列表
     * @apiSuccess {number}   	obj.ID 					                       主键ID
     * @apiSuccess {number}   	obj.USER_ID			                                   用户id
     * @apiSuccess {string}   	obj.USER_NAME 		           	用户名
     * @apiSuccess {string}   	obj.APPROVAL_USER_REALNAME		审批人
     * @apiSuccess {string}   	obj.USER_REAL_NAME 				真实姓名
     * @apiSuccess {number}   	obj.STATE			  			认证状态：0、待审核；1、已审批；2、驳回
     * @apiSuccess {string}   	obj.CREATE_DATE 				创建时间
     * @apiSuccess {string}   	obj.APPROVAL_DATE 				审批时间
     * @apiSuccess {string}   	obj.REJECTED_REASON 			驳回原因
     * 
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySysUserAuthApprovalList(HttpServletRequest request) {
        return Transform.GetResult(sysUserAuthApprovalService.querySysUserAuthApprovalList(request));
    }
    
    /**
     * @api {post} /{project_name}/sys_user_auth_approval/detail 查询用户认证审批详情
     * @apiName detail
     * @apiGroup sys_user_auth_approval
     * @apiDescription  查询用户认证审批详情
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id 	主键ID
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {number}     total 					                     总数
     * @apiSuccess {object[]}   obj 					                      查询缴款单列表
     * @apiSuccess {number}   	obj.ID 					                       主键ID
     * @apiSuccess {number}   	obj.USER_ID			                                   用户id
     * @apiSuccess {string}   	obj.USER_NAME 		           	用户名
     * @apiSuccess {string}   	obj.APPROVAL_USER_REALNAME		审批人
     * @apiSuccess {string}   	obj.USER_REAL_NAME 				真实姓名
     * @apiSuccess {number}   	obj.STATE			  			认证状态：0、待审核；1、已审批；2、驳回
     * @apiSuccess {string}   	obj.CREATE_DATE 				创建时间
     * @apiSuccess {string}   	obj.APPROVAL_DATE 				审批时间
     * @apiSuccess {string}   	obj.REJECTED_REASON 			驳回原因
     * 
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySysUserAuthApprovalDetail(HttpServletRequest request) {
        return Transform.GetResult(sysUserAuthApprovalService.querySysUserAuthApprovalDetail(request));
    }
    
    /**
    *
    * @api {post} /{project_name}/account_info/authentication  认证信息审批
    * @apiGroup account_info
    * @apiName authentication_approval
    * @apiDescription  认证信息审批
    * @apiVersion 0.0.1
    * 
    * @apiParam{number} id 主键id
    * @apiParam{number} state 认证状态：0、待审核；1、已审批；2、驳回
    * @apiParam{string} rejected_reason  驳回原因(驳回时含改参数)
    * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess{string} message 接口返回信息
    */
	@RequestMapping(value = "/approval", method = RequestMethod.POST)
	@ResponseBody
	public Packet authenticationInfoApproval(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = sysUserAuthApprovalService.authenticationInfoApproval(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}

}
