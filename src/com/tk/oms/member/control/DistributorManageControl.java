package com.tk.oms.member.control;

import com.tk.oms.member.service.DistributorManageService;
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
 * Copyright (c), 2017, Tongku
 * FileName : DistributorManageControl
 * 经销商管理访问接口
 *
 * @author zhenghui
 * @version 1.00
 * @date 2017/08/09
 */
@Controller
@RequestMapping("/distributor_manage")
public class DistributorManageControl {

    @Resource
    private DistributorManageService distributorManageService;

    /**
     * @api{post} /{oms_server}/distributor_manage/list 获取经销商列表
     * @apiGroup distributor_manage
     * @apiName distributor_manage_list
     * @apiDescription 分页获取经销商列表数据
     * @apiVersion 0.0.1
     *
     * @apiParam {char}  state   用户分销状态
     *
     * @apiSuccess {boolean}  state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}   message 接口返回信息
     * @apiSuccess {object}   obj 经销商列表集合
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDistributorListForPage(HttpServletRequest request) {
        return Transform.GetResult(this.distributorManageService.queryDistributorListForPage(request));
    }

    /**
     * @api{post} /{oms_server}/distributor_manage/edit_state 编辑分销状态
     * @apiGroup distributor_manage
     * @apiName distributor_manage_edit_state
     * @apiDescription 编辑经销商分销状态
     * @apiVersion 0.0.1
     *
     * @apiParam {number}  id   用户ID
     *
     * @apiSuccess {boolean}  state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}   message 接口返回信息
     */
    @RequestMapping(value = "/edit_state", method = RequestMethod.POST)
    @ResponseBody
    public Packet editDistributionState(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.distributorManageService.editDistributionState(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api{post} /{oms_server}/distributor_manage/edit_rate 编辑服务费率
     * @apiGroup distributor_manage
     * @apiName distributor_manage_edit_rate
     * @apiDescription 编辑经销商的服务费率
     * @apiVersion 0.0.1
     *
     * @apiParam {number}  id   用户ID
     *
     * @apiSuccess {boolean}  state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}   message 接口返回信息
     */
    @RequestMapping(value = "/edit_rate", method = RequestMethod.POST)
    @ResponseBody
    public Packet editServiceRateForDistribution(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.distributorManageService.editServiceRateForDistribution(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api{post} /{oms_server}/distributor_manage/edit_authority 设置权限
     * @apiGroup distributor_manage
     * @apiName distributor_manage_edit_authority
     * @apiDescription 设置经销商权限
     * @apiVersion 0.0.1
     *
     * @apiParam {number}  id   用户ID
     *
     * @apiSuccess {boolean}  state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}   message 接口返回信息
     */
    @RequestMapping(value = "/edit_authority", method = RequestMethod.POST)
    @ResponseBody
    public Packet editAuthorityForDistribution(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.distributorManageService.editAuthorityForDistribution(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api{post} /{oms_server}/distributor_manage/revenue_list 收支列表
     * @apiGroup distributor_manage
     * @apiName distributor_manage_revenue_list
     * @apiDescription 分页获取经销商收支列表数据
     * @apiVersion 0.0.1
     *
     * @apiParam {char}  state   用户分销状态
     *
     * @apiSuccess {boolean}  state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}   message 接口返回信息
     * @apiSuccess {object}   obj 经销商列表集合
     */
    @RequestMapping(value = "/revenue_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRevenueListForDistribution(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.distributorManageService.queryRevenueListForDistribution(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api{post} /{oms_server}/distributor_manage/revenue_detail 支付方式列表
     * @apiGroup distributor_manage
     * @apiName distributor_manage_revenue_detail
     * @apiDescription 经销商收支支付方式列表数据
     * @apiVersion 0.0.1
     *
     * @apiParam {char}  state   用户分销状态
     *
     * @apiSuccess {boolean}  state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}   message 接口返回信息
     * @apiSuccess {object}   obj 经销商收支集合
     */
    @RequestMapping(value = "/trade_style_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryTradeStyleList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.distributorManageService.queryTradeStyleList(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return Transform.GetResult(pr);
    }
    
    
    /**
     * 经销商下拉框
     * @param request
     * @return
     */
    @RequestMapping(value = "/option", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDistributionOption(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.distributorManageService.queryDistributionOption(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return Transform.GetResult(pr);
    }
    
    /**
     * 选择经销商查询其它信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/distributor_query_info", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDistributionInfo(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.distributorManageService.queryDistributionInfo(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return Transform.GetResult(pr);
    }

}
