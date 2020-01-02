package com.tk.oms.basicinfo.control;

import com.tk.oms.basicinfo.service.MemberFlagService;
import com.tk.oms.basicinfo.service.OrderImportModelService;
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
 * FileName : OrderImportModelControl
 * 订单导入模版控制器
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/8/23 14:50
 */
@Controller
@RequestMapping("/order_import_model")
public class OrderImportModelControl {

    @Resource
    private OrderImportModelService orderImportModelService;

    /**
     * @param request
     * @return
     * @api {post} /{project_name}/order_import_model/list 查询订单导入模版列表接口
     * @apiGroup order_import_model
     * @apiName order_import_model_list
     * @apiDescription                    查询订单导入模版列表接口
     * @apiSuccess {object} obj                 单导入模版列表
     * @apiSuccess {boolean} state              接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message             接口返回信息.
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryList(HttpServletRequest request) {
        return Transform.GetResult(orderImportModelService.queryList(request));
    }


    /**
     * @param request
     * @return
     * @api {post} /{project_name}/order_import_model/update_state 编辑订单导入模版启用状态
     * @apiGroup order_import_model
     * @apiName order_import_model_list
     * @apiDescription                    查询订单导入模版数据信息接口
     * @apiSuccess {object} obj                 订单导入模版数据信息
     * @apiSuccess {boolean} state              接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message             接口返回信息.
     */
    @RequestMapping(value = "/update_state", method = RequestMethod.POST)
    @ResponseBody
    public Packet editOrderImportModelState(HttpServletRequest request) {
        return Transform.GetResult(orderImportModelService.editOrderImportModelState(request));
    }

    /**
     * @param request
     * @return
     * @api {post} /{project_name}/order_import_model/list 查询订单导入模版数据信息接口
     * @apiGroup order_import_model
     * @apiName order_import_model_list
     * @apiDescription                    查询订单导入模版数据信息接口
     * @apiSuccess {object} obj                 订单导入模版数据信息
     * @apiSuccess {boolean} state              接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message             接口返回信息.
     */
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryOrderImportModelInfo(HttpServletRequest request) {
        return Transform.GetResult(orderImportModelService.queryOrderImportModelInfo(request));
    }

    /**
     * @param request
     * @return
     * @api {post} /{project_name}/order_import_model/add 新增订单导入数据接口
     * @apiGroup order_import_model
     * @apiName order_import_model_add
     * @apiDescription                     新增订单导入数据接口
     * @apiVersion 0.0.1
     * @apiSuccess {object} obj                 新增订单导入数据信息
     * @apiSuccess {boolean} state              接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message             接口返回信息
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addOrderImportModel(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try{
            return Transform.GetResult(orderImportModelService.addOrderImportModel(request));
        }catch (Exception e){
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }
    /**
     * @api {post} /{project_name}/order_import_model/edit 编辑订单导入数据接口
     * @apiGroup order_import_model
     * @apiName order_import_model_edit
     * @apiDescription                    编辑订单导入数据接口
     * @apiVersion 0.0.1
     * @apiSuccess {object} obj                 编辑订单导入数据信息
     * @apiSuccess {boolean} state              接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message             接口返回信息
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editOrderImportModel(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = orderImportModelService.editOrderImportModel(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/order_import_model/remove 删除订单导入模版数据接口
     * @apiGroup order_import_model
     * @apiName order_import_model_remove
     * @apiDescription                  删除订单导入模版数据接口
     * @apiVersion 0.0.1
     * @apiSuccess {boolean} state              接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message             接口返回信息
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeOrderImportModel(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = orderImportModelService.removeOrderImportModel(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/order_import_model/config_detail 获取订单导入模版配置数据接口
     * @apiGroup order_import_model
     * @apiName order_import_model_config_detail
     * @apiDescription                  获取订单导入模版配置数据接口
     * @apiVersion 0.0.1
     * @apiSuccess {boolean} state              接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message             接口返回信息
     */
    @RequestMapping(value = "/config_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryOrderImportModelConfigDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = orderImportModelService.queryOrderImportModelConfigDetail(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/order_import_model/config_edit 编辑订单导入模版配置数据接口
     * @apiGroup order_import_model
     * @apiName order_import_model_config_edit
     * @apiDescription                  编辑订单导入模版配置数据接口
     * @apiVersion 0.0.1
     * @apiSuccess {boolean} state              接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message             接口返回信息
     */
    @RequestMapping(value = "/config_edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editOrderImportModelConfig(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = orderImportModelService.editOrderImportModelConfig(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/order_import_model/config_remove 删除订单导入模版配置数据接口
     * @apiGroup order_import_model
     * @apiName order_import_model_config_remove
     * @apiDescription                  删除订单导入模版配置数据接口
     * @apiVersion 0.0.1
     * @apiSuccess {boolean} state              接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message             接口返回信息
     */
    @RequestMapping(value = "/config_remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeOrderImportModelConfig(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = orderImportModelService.removeOrderImportModelConfig(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }


}
