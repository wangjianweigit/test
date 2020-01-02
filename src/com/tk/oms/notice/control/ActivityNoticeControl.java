package com.tk.oms.notice.control;

import com.tk.oms.notice.service.ActivityNoticeService;
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
 * 
 * Copyright (c), 2017, Tongku
 * FileName : NoticeControl
 * 活动通知Control
 * 
 * @author wangjianwei
 * @version 1.00
 * @date 2017/4/13 17:47
 */
@Controller
@RequestMapping("/activity_notice")
public class ActivityNoticeControl {

    @Resource
    private ActivityNoticeService activityNoticeService;//活动通知业务操作类

    /**
     * @param request
     * @return
     * @api {post} /{project_name}/activty_notice/context_list 查询活动通知内容列表接口
     * @apiGroup activty_notice
     * @apiName activty_notice_context_list
     * @apiDescription 查询活动通知内容列表接口
     * @apiVersion 0.0.1
     * @apiParam {number} pageIndex 起始页
     * @apiParam {number} pageSize 分页大小
     * @apiParam {string} name 内容名称 选填
     * @apiParam {date} create_start_time  创建活动内容开始时间 选填
     * @apiParam {date} create_end_time  创建活动内容结束时间 选填
     * @apiParam {string} state 内容状态 选填
     * @apiSuccess {object} obj 活动通知内容列表
     * @apiSuccess {number} obj.ID 编号
     * @apiSuccess {number} obj.SITE_NAME 站点名称
     * @apiSuccess {string} obj.TEXT_CONTENT 活动通知内容
     * @apiSuccess {string} obj.STATE 状态 1.未启用 2.启用
     * @apiSuccess {string} obj.NAME 活动通知内容名称
     * @apiSuccess {string} obj.REMARK 备注
     * @apiSuccess {string} obj.CREATE_DATE  创建时间
     * @apiSuccess {number} total 数据总量
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息.
     */
    @RequestMapping(value = "/context_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryList(HttpServletRequest request) {
        return Transform.GetResult(activityNoticeService.queryList(request));
    }

    /**
     * @param request
     * @return
     * @api {post} /{project_name}/activty_notice/context_add 新增活动通知内容接口
     * @apiGroup activty_notice
     * @apiName activty_notice_context_add
     * @apiDescription 新增活动通知内容接口
     * @apiVersion 0.0.1
     * @apiParam {string} remark 备注 选填
     * @apiParam {number} site_id 站点id 必填
     * @apiParam {string} name 活动通知内容名称 必填
     * @apiParam {string} text_content 活动通知内容 必填
     * @apiSuccess {object} obj 新增活动通知内容信息
     * @apiSuccess {string} obj.ID 编号
     * @apiSuccess {string} obj.SITE_ID 站点id
     * @apiSuccess {number} obj.NAME 活动通知内容名称
     * @apiSuccess {string} obj.TEXT_CONTENT 活动通知内容
     * @apiSuccess {number} obj.STATE 状态 1.未启用  2.启用
     * @apiSuccess {string} obj.CREATE_USER_ID 创建人
     * @apiSuccess {number} obj.CREATE_DATE 创建时间
     * @apiSuccess {number} obj.REMARK 备注信息
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     */
    @RequestMapping(value = "/context_add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addActivityNoticeContext(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try{
            return Transform.GetResult(activityNoticeService.addActivityNoticeContext(request));
        }catch (Exception e){
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @param request
     * @return
     * @api {post} /{project_name}/activty_notice/context_info 查询活动通知内容信息
     * @apiGroup activty_notice
     * @apiName activty_notice_search_context_info
     * @apiDescription 查询活动通知内容信息接口
     * @apiVersion 0.0.1
     * @apiParam {number} id 活动通知内容id 必填
     * @apiSuccess {string} obj.ID 编号
     * @apiSuccess {string} obj.SITE_ID 站点id
     * @apiSuccess {number} obj.NAME 活动通知内容名称
     * @apiSuccess {string} obj.TEXT_CONTENT 活动通知内容
     * @apiSuccess {number} obj.STATE 状态 1.未启用  2.启用
     * @apiSuccess {string} obj.CREATE_USER_ID 创建人
     * @apiSuccess {number} obj.CREATE_DATE 创建时间
     * @apiSuccess {number} obj.REMARK 备注信息
     */
    @RequestMapping(value = "/context_info", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryWaitUpdateActivityNoticeContextInfo(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = activityNoticeService.queryWaitUpdateActivityNoticeContextInfo(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/activty_notice/context_update 更新活动通知内容接口
     * @apiGroup activty_notice
     * @apiName activty_notice_context_update
     * @apiDescription 更新活动通知内容接口
     * @apiVersion 0.0.1
     * @apiParam {string} id 活动通知内容id 必填
     * @apiParam {string} remark 备注 选填
     * @apiParam {number} site_id 站点id 必填
     * @apiParam {string} name 活动通知内容名称 必填
     * @apiParam {string} text_content 活动通知内容 必填
     * @apiParam {string} state 状态 '1'.未启用  '2'.启用 必填
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     */
    @RequestMapping(value = "/context_update", method = RequestMethod.POST)
    @ResponseBody
    public Packet editActivityNoticeContext(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = activityNoticeService.editActivityNoticeContext(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/activty_notice/context_delete 删除活动通知内容接口
     * @apiGroup activty_notice
     * @apiName activty_notice_context_delete
     * @apiDescription 删除活动通知内容接口
     * @apiVersion 0.0.1
     * @apiParam {string} id 活动通知内容id 必填
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     */
    @RequestMapping(value = "/context_delete", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeActivityNoticeContext(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = activityNoticeService.removeActivityNoticeContext(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/activty_notice/context_update_status 更改活动通知内容启用状态接口
     * @apiGroup activty_notice
     * @apiName activty_notice_context_update_status
     * @apiDescription 更改活动通知内容启用状态接口
     * @apiVersion 0.0.1
     * @apiParam {string} id 活动通知内容id 必填
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     */
    @RequestMapping(value = "/context_update_status", method = RequestMethod.POST)
    @ResponseBody
    public Packet editActivityNoticeContextByStatus(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = activityNoticeService.editActivityNoticeContextByStatus(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @param request
     * @return
     * @api {post} /{project_name}/activty_notice/context_select 查询活动通知内容数据接口
     * @apiGroup activty_notice
     * @apiName activty_notice_context_select
     * @apiDescription 查询活动通知内容数据接口
     * @apiVersion 0.0.1
     * @apiSuccess {object} obj 活动通知内容数据列表
     * @apiSuccess {string} obj.TEXT_CONTENT 短信模板内容
     * @apiSuccess {string} obj.NAME 短信模板名称
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     */
    @RequestMapping(value = "/context_select", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryActivityNoticeContextList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try{
            return Transform.GetResult(activityNoticeService.queryActivityNoticeContextSelect(request));
        }catch (Exception e){
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @param request
     * @return
     * @api {post} /{project_name}/activty_notice/send_info_list 查询活动通知发送列表接口
     * @apiGroup activty_notice
     * @apiName activty_notice_send_info_list
     * @apiDescription 查询活动通知发送列表接口
     * @apiVersion 0.0.1
     * @apiParam {number} pageIndex 起始页
     * @apiParam {number} pageSize 分页大小
     * @apiParam {string} name 内容名称 选填
     * @apiParam {date} send_start_time  发送开始时间 选填
     * @apiParam {date} send_end_time  发送结束时间 选填
     * @apiParam {string} state 内容状态 选填
     * @apiSuccess {object} obj 活动通知发送列表
     * @apiSuccess {number} obj.ID 活动通知发送信息id
     * @apiSuccess {string} obj.NAME 活动通知名称
     * @apiSuccess {number} obj.STATE 状态: 0.待发送 1.发送中  2.全部发送（结果未知） 3.取消发送
     * @apiSuccess {string} obj.SITE_NAME 站点名称
     * @apiSuccess {string} obj.TEXT_CONTENT 活动通知内容
     * @apiSuccess {number} obj.TOTAL_AMOUNT 接收人数
     * @apiSuccess {number} obj.SUCCESS_AMOUNT 发送成功数量
     * @apiSuccess {string} obj.FAIL_AMOUNT 发送失败数量
     * @apiSuccess {date} obj.SEND_DATE 活动通知发送时间
     * @apiSuccess {number} total 数据总量
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息.
     */
    @RequestMapping(value = "/send_info_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySendInfoList(HttpServletRequest request) {
        return Transform.GetResult(activityNoticeService.querySendInfoList(request));
    }

    /**
     * @param request
     * @return
     * @api {post} /{project_name}/activty_notice/send_detail_add 新增活动通知发送明细接口
     * @apiGroup activty_notice
     * @apiName activty_notice_send_detail_add
     * @apiDescription 新增活动通知发送明细接口
     * @apiVersion 0.0.1
     * @apiParam {number} site_id 站点id 必填
     * @apiParam {number} text_content 短信通知内容 必填
     * @apiParam {date} send_date 通知发送时间 必填
     * @apiParam {string} user_name 通知接收会员user_name 多个逗号分隔 必填
     * @apiParam {string} openid 通知接收会员微信openid 多个逗号分隔 必填
     * @apiParam {string} phone_number 接收会员手机号 多个逗号分隔 必填
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     */
    @RequestMapping(value = "/send_detail_add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addActivityNoticeSendDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try{
            return Transform.GetResult(activityNoticeService.addActivityNoticeSendDetail(request));
        }catch (Exception e){
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @param request
     * @return
     * @api {post} /{project_name}/activty_notice/send_detail 查询活动通知发送明细接口
     * @apiGroup activty_notice
     * @apiName activty_notice_send_detail
     * @apiDescription 查询活动通知发送明细接口
     * @apiVersion 0.0.1
     * @apiParam {number} id 活动通知发送id 必填
     * @apiSuccess {object} obj 发送活动通知明细数据
     * @apiSuccess {object} obj.activityNotice 活动通知信息
     * @apiSuccess {number} obj.activityNotice.ID 活动通知发送信息id
     * @apiSuccess {string} obj.activityNotice.NAME 活动通知名称
     * @apiSuccess {string} obj.activityNotice.STATE 状态: 0.待发送 1.发送中  2.全部发送（结果未知） 3.取消发送
     * @apiSuccess {string} obj.activityNotice.TEXT_CONTENT 活动通知内容
     * @apiSuccess {string} obj.activityNotice.NOTICE_CHANNEL 发送渠道  1:短信   2：微信   3：站内信
     * @apiSuccess {string} obj.activityNotice.SEND_DATE 发送时间
     * @apiSuccess {string} obj.activityNotice.NAME 发送模版名称
     *
     * @apiSuccess {object} obj.activityNoticeSendDetail 活动通知明细
     * @apiSuccess {object} obj.activityNoticeSendDetail.USER_NAME 通知接收会员名称
     * @apiSuccess {object} obj.activityNoticeSendDetail.REFEREE_USER_NAME 业务员名称
     * @apiSuccess {object} obj.activityNoticeSendDetail.PHONE_NUMBER 接收手机号
     * @apiSuccess {object} obj.activityNoticeSendDetail.ID 明细id
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     */
    @RequestMapping(value = "/send_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryActivityNoticeSendDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try{
            return Transform.GetResult(activityNoticeService.queryActivityNoticeSendDetail(request));
        }catch (Exception e){
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @param request
     * @return
     * @api {post} /{project_name}/activty_notice/send_detail_update 修改活动通知发送明细接口
     * @apiGroup activty_notice
     * @apiName activty_notice_send_detail_update
     * @apiDescription 新增活动通知发送明细接口
     * @apiVersion 0.0.1
     * @apiParam {number} notice_id 活动通知发送id 必填
     * @apiParam {number} site_id 站点id 必填
     * @apiParam {number} text_content 短信通知内容 必填
     * @apiParam {date} send_date 通知发送时间 必填
     * @apiParam {string} user_name 通知接收会员user_name 多个逗号分隔 必填
     * @apiParam {string} openid 通知接收会员微信openid 多个逗号分隔 必填
     * @apiParam {string} phone_number 接收会员手机号 多个逗号分隔 必填
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     */
    @RequestMapping(value = "/send_detail_update", method = RequestMethod.POST)
    @ResponseBody
    public Packet editActivityNoticeSendDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try{
            return Transform.GetResult(activityNoticeService.editActivityNoticeSendDetail(request));
        }catch (Exception e){
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/activty_notice/send_detail_delete 删除活动发送通知接口
     * @apiGroup activty_notice
     * @apiName activty_notice_send_detail_delete
     * @apiDescription 删除活动发送通知接口
     * @apiVersion 0.0.1
     * @apiParam {string} ids 取消活动发送通知id (取消多个用逗号隔开) 必填
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     */
    @RequestMapping(value = "/send_detail_delete", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeActivityNoticeInfo(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = activityNoticeService.removeActivityNoticeInfo(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/activty_notice/send_cancel 取消活动发送通知接口
     * @apiGroup activty_notice
     * @apiName activty_notice_send_cancel
     * @apiDescription 取消活动发送通知接口
     * @apiVersion 0.0.1
     * @apiParam {string} id 取消活动发送通知id (取消多个用逗号隔开) 必填
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     */
    @RequestMapping(value = "/send_cancel", method = RequestMethod.POST)
    @ResponseBody
    public Packet editActivityNoticeCancelSend(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = activityNoticeService.editActivityNoticeInfo(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/activty_notice/select_user_list 查询会员列表
     * @apiGroup activty_notice
     * @apiName activty_notice_select_user_list
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
            pr = activityNoticeService.queryMemberList(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

}
