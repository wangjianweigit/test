package com.tk.oms.finance.control;



import com.tk.oms.finance.service.SalesmanCreditLineService;
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
 * FileName : SalesmanCreditLineControl
 * 业务员授信额度管理Control
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/5/6 14:54
 */
@Controller
@RequestMapping("/salesman_credit_line")
public class SalesmanCreditLineControl {

    @Resource
    private SalesmanCreditLineService salesmanCreditLineService;

    /**
     * @param request
     * @return
     * @api {post} /{project_name}/salesman_credit_line/list 查询业务员授信额度列表
     * @apiGroup salesman_credit_line
     * @apiName salesman_credit_line_list
     * @apiDescription 查询业务员授信额度列表
     * @apiVersion 0.0.1
     * @apiParam {number} pageIndex 起始页
     * @apiParam {number} pageSize 分页大小
     * @apiParam {string} user_name 内容名称 选填
     * @apiParam {string} user_realname 内容名称 选填
     * @apiParam {number} user_company_address_province 所在地省份id 选填
     * @apiParam {number} user_company_address_city 所在地城市id 选填
     * @apiParam {number} user_company_address_county 所在地区县id 选填
     * @apiSuccess {object} obj 业务员授信额度列表
     * @apiSuccess {number} total 数据总量
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息.
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryList(HttpServletRequest request) {
        return Transform.GetResult(salesmanCreditLineService.queryList(request));
    }


    /**
     * @api {post} /{project_name}/salesman_credit_line/update_verify_code 修改业务员验证码
     * @apiGroup salesman_credit_line
     * @apiName salesman_credit_line_update_verify_code
     * @apiDescription 更新活动通知内容接口
     * @apiVersion 0.0.1
     * @apiParam {number} id 用户id 必填
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     */
    @RequestMapping(value = "/update_verify_code", method = RequestMethod.POST)
    @ResponseBody
    public Packet update_verify_code(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = salesmanCreditLineService.update_verify_code(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/activty_notice/update_credit 修改授信额度
     * @apiGroup activty_notice
     * @apiName activty_notice_context_update
     * @apiDescription 更新活动通知内容接口
     * @apiVersion 0.0.1
     * @apiParam {string} id 用户id 必填
     * @apiParam {number} credit_money 授信额度 必填
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     */
    @RequestMapping(value = "/update_credit", method = RequestMethod.POST)
    @ResponseBody
    public Packet update_credit(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = salesmanCreditLineService.update_credit(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }


}
