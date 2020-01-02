package com.tk.oms.finance.control;

import com.tk.oms.finance.service.UserAccountCheckService;
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
 * FileName : UserAccountCheckControl
 * 用户账户对账控制器
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/9/4 17:47
 */
@Controller
@RequestMapping("/account_check_task")
public class UserAccountCheckControl {
    @Resource
    private UserAccountCheckService userAccountCheckService;

    /**
     * @api{post} /{oms_server}/account_check_task/list 对账任务列表
     * @apiGroup account_check_task
     * @apiName account_check_task
     * @apiDescription  分页获取会员充值记录列表
     * @apiVersion 0.0.1
     *
     * @apiParam   {number} [pageIndex=1]  起始页
     * @apiParam   {number} [pageSize=20]  分页大小
     *
     * @apiSuccess  {boolean} state    接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess  {string} message   接口返回信息
     * @apiSuccess  {number} total     总条数
     * @apiSuccess  {object} obj       对账任务列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryUserAccountCheckTaskByPage(HttpServletRequest request){
        return Transform.GetResult(this.userAccountCheckService.queryUserAccountCheckTaskByPage(request));

    }

    /**
     * @api{post} /{oms_server}/account_check_task/add 添加对账任务
     * @apiGroup account_check_task
     * @apiName account_check_task_add
     * @apiDescription  添加对账任务
     * @apiVersion 0.0.1
     *
     * @apiParam   {number} title  	         标题
     * @apiParam   {number} remark           备注
     * @apiParam   {string} public_user_id   创建用户
     *
     * @apiSuccess  {boolean} state    接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess  {string} message   接口返回信息
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addUserAccountCheckTask(HttpServletRequest request){
        ProcessResult pr= new ProcessResult();
        try {
            pr = this.userAccountCheckService.addUserAccountCheckTask(request);
        } catch (Exception e) {
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }


    /**
     * @api{post} /{oms_server}/account_check_task/detail 对账任务明细列表
     * @apiGroup account_check_task
     * @apiName account_check_task
     * @apiDescription  获取对账任务明细数据
     * @apiVersion 0.0.1
     *
     * @apiParam   {number} state      对账状态 1、异常 2、正常
     * @apiParam   {number} task_id    对账任务id
     *
     * @apiSuccess  {boolean} state    接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess  {string} message   接口返回信息
     * @apiSuccess  {object} obj       对账任务明细数据
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAccountCheckDetailByPage(HttpServletRequest request){
        return Transform.GetResult(this.userAccountCheckService.queryAccountCheckDetailByPage(request));
    }

    /**
     * @api{post} /{oms_server}/account_check_task/again_check 对账任务重新核对
     * @apiGroup account_check_task
     * @apiName account_check_task_again_check
     * @apiDescription  对账任务重新核对
     * @apiVersion 0.0.1
     *
     * @apiParam   {number} id         对账明细id
     * @apiParam   {number} task_id    对账任务id
     *
     * @apiSuccess  {boolean} state    接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess  {string} message   接口返回信息
     */
    @RequestMapping(value = "/again_check", method = RequestMethod.POST)
    @ResponseBody
    public Packet accountAgainCheck(HttpServletRequest request){
        ProcessResult pr= new ProcessResult();
        try {
            pr = this.userAccountCheckService.accountAgainCheck(request);
        } catch (Exception e) {
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }
}
