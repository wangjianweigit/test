package com.tk.oms.marketing.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.marketing.service.CustomMessageService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * 
 * Copyright (c), 2019, Tongku
 * FileName : CustomMessageControl
 * 自定义消息管理
 * 
 * @author shifan
 * @version 1.00
 * @date 2019-10-30
 */
@Controller
@RequestMapping("/custom_message")
public class CustomMessageControl {

	@Resource
	private CustomMessageService customMessageService;
	
	
	/**
    *
    * @api {post} /{project_name}/custom_message/list 自定义消息列表
    * @apiGroup custom_message
    * @apiDescription  自定义消息列表
    * @apiVersion 0.0.1

	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条   

    * @apiSuccess {boolean}  state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string}   message 接口返回信息说明
    * @apiSuccess {object[]} obj 自定义消息列表
    * @apiSuccess {number}   obj.ID    主键ID
    */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryCustomMessageList(HttpServletRequest request) {
		return Transform.GetResult(customMessageService.queryCustomMessageList(request));
	}
	
	/**
    *
    * @api {post} /{project_name}/custom_message/edit 自定义消息编辑
    * @apiGroup custom_message
    * @apiDescription  自定义消息编辑
    * @apiVersion 0.0.1
	* @apiParam {string} 				 
	* @apiParam {string} 	   

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string}  message 接口返回信息说明
    * @apiSuccess {object}  obj	   
    */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Packet customMessageEdit(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=customMessageService.customMessageEdit(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
	
	/**
    *
    * @api {post} /{project_name}/custom_message/detail 自定义消息详情
    * @apiGroup custom_message
    * @apiDescription  自定义消息详情
    * @apiVersion 0.0.1

	* @apiParam {number}  
	* @apiParam {number} 

    * @apiSuccess {boolean}  state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string}   message 接口返回信息说明
    * @apiSuccess {object[]} obj 自定义消息列表
    * @apiSuccess {number}   obj.ID    主键ID
    */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryCustomMessageDetail(HttpServletRequest request) {
		return Transform.GetResult(customMessageService.queryCustomMessageDetail(request));
	}
	
	
	/**
    *
    * @api {post} /{project_name}/custom_message/audit 自定义消息审批
    * @apiGroup custom_message
    * @apiDescription  自定义消息审批
    * @apiVersion 0.0.1
	* @apiParam {string} 				 
	* @apiParam {string} 	   

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string}  message 接口返回信息说明
    * @apiSuccess {object}  obj	   
    */
	@RequestMapping(value = "/audit", method = RequestMethod.POST)
	@ResponseBody
	public Packet customMessageAudit(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=customMessageService.customMessageAudit(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
	
	/**
    *
    * @api {post} /{project_name}/custom_message/audit 自定义消息删除
    * @apiGroup custom_message
    * @apiDescription  自定义消息删除
    * @apiVersion 0.0.1
	* @apiParam {string} 				 
	* @apiParam {string} 	   

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string}  message 接口返回信息说明
    * @apiSuccess {object}  obj	   
    */
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public Packet customMessageDel(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=customMessageService.customMessageDel(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
	
	/**
    *
    * @api {post} /{project_name}/custom_message/stop 自定义消息终止
    * @apiGroup custom_message
    * @apiDescription  自定义消息终止
    * @apiVersion 0.0.1
	* @apiParam {string} 				 
	* @apiParam {string} 	   

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string}  message 接口返回信息说明
    * @apiSuccess {object}  obj	   
    */
	@RequestMapping(value = "/stop", method = RequestMethod.POST)
	@ResponseBody
	public Packet customMessageStop(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=customMessageService.customMessageStop(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
	
	/**
    *
    * @api {post} /{project_name}/custom_message/channel_page_list 自定义消息推送频道页列表
    * @apiGroup custom_message
    * @apiDescription  自定义消息推送频道页列表
    * @apiVersion 0.0.1

	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条   

    * @apiSuccess {boolean}  state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string}   message 接口返回信息说明
    * @apiSuccess {object[]} obj 自定义消息列表
    * @apiSuccess {number}   obj.ID    主键ID
    */
	@RequestMapping(value = "/channel_page_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryCustomMessageChannelPageList(HttpServletRequest request) {
		return Transform.GetResult(customMessageService.queryCustomMessageChannelPageList(request));
	}
	
	/**
    *
    * @api {post} /{project_name}/custom_message/list 自定义消息计算用户列表
    * @apiGroup custom_message
    * @apiDescription  自定义消息计算用户列表
    * @apiVersion 0.0.1

	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条   

    * @apiSuccess {boolean}  state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string}   message 接口返回信息说明
    * @apiSuccess {object[]} obj 自定义消息列表
    * @apiSuccess {number}   obj.ID    主键ID
    */
	@RequestMapping(value = "/cal_user_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryCustomMessageCalUserList(HttpServletRequest request) {
		return Transform.GetResult(customMessageService.queryCustomMessageCalUserList(request));
	}
	
	/**
    *
    * @api {post} /{project_name}/custom_message/user_list 自定义消息列表
    * @apiGroup custom_message
    * @apiDescription  自定义消息列表
    * @apiVersion 0.0.1

	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条   

    * @apiSuccess {boolean}  state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string}   message 接口返回信息说明
    * @apiSuccess {object[]} obj 自定义消息列表
    * @apiSuccess {number}   obj.ID    主键ID
    */
	@RequestMapping(value = "/user_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryCustomMessageUserList(HttpServletRequest request) {
		return Transform.GetResult(customMessageService.queryCustomMessageUserList(request));
	}
	
    /**
     * @api {post} /{project_name}/custom_message/select_user_list 查询会员列表
     * @apiGroup custom_message
     * @apiName custom_message_select_user_list
     * @apiDescription 查询会员列表
     * @apiVersion 0.0.1
     * @apiParam {string} user_name 姓名 选填
     * @apiParam {string} referee_user_id 业务员名称 选填
     * @apiParam {string} login_name 用户名 选填
     * @apiParam {string} store_id 门店id 选填
     * @apiParam {string} mobilephone 用户手机号 选填
     * @apiParam {string} notice_channel 多个逗号分隔  发送渠道  1:短信   2：微信   3：站内信 选填
     *
     * @apiSuccess {object} obj 会员信息列表数据
     * @apiSuccess {number} obj.ID 活动通知发送信息id
     * @apiSuccess {string} obj.USER_NAME 姓名
     * @apiSuccess {number} obj.LOGIN_NAME 用户名
     * @apiSuccess {string} obj.USER_MANAGE_MOBILEPHONE 用户手机号
     * @apiSuccess {string} obj.USER_MANAGE_CURRENT_ADDRESS 用户地址
     * @apiSuccess {number} obj.USER_RESOURCE 用户来源
     * @apiSuccess {number} obj.STORE_NAME 门店名称
     * @apiSuccess {string} obj.REFEREE_USER_NAME 业务员名称
     * @apiSuccess {number} total 数据总量
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     */
    @RequestMapping(value = "/select_member_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = customMessageService.queryMemberList(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }
    
    /**
    *
    * @api {post} /{project_name}/custom_message/custom_page_list 自定义页列表
    * @apiGroup custom_message
    * @apiDescription  自定义页列表
    * @apiVersion 0.0.1

	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条   

    * @apiSuccess {boolean}  state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string}   message 接口返回信息说明
    * @apiSuccess {object[]} obj 自定义页列表
    * @apiSuccess {number}   obj.id    
    * @apiSuccess {number}   obj.option    
    */
	@RequestMapping(value = "/custom_page_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryCustomPageList(HttpServletRequest request) {
		return Transform.GetResult(customMessageService.queryCustomPageList(request));
	}
}