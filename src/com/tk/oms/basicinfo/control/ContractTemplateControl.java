package com.tk.oms.basicinfo.control;

import com.tk.oms.basicinfo.service.ContractTemplateService;
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
 * Copyright (c), 2018,  TongKu
 * FileName : ContractTemplateControl
 *
 * @author: zhengfy
 * @version: 1.00
 * @date: 2018/12/10
 */
@Controller
@RequestMapping("/contract_template")
public class ContractTemplateControl {

    @Resource
    private ContractTemplateService contractTemplateService;

    /**
     * @api {post} /{project_name}/contract_template/list
     * @apiName list
     * @apiGroup contract_template
     * @apiDescription  合同模板列表
     * @apiVersion 0.0.1
     *
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 				合同模板列表
     * @apiSuccess {number}   	obj.id 				主键ID
     * @apiSuccess {string}   	obj.template_number	模板编号
     * @apiSuccess {string}   	obj.template_name 	模板名称
     * @apiSuccess {number}   	obj.type            合同模板类型（1：入驻商合同， 2：会员合同）
     * @apiSuccess {string}   	obj.organ_code 		所属公司代码
     * @apiSuccess {string}   	obj.create_date 	创建时间
     * @apiSuccess {string}   	obj.state 			状态（1：草稿，2：已发布，3：已失效）
     *
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryList(HttpServletRequest request) {
        return Transform.GetResult(contractTemplateService.queryList(request));
    }


    /**
     * @api {post} /{project_name}/contract_template/save
     * @apiName save
     * @apiGroup contract_template
     * @apiDescription  保存合同模板数据
     * @apiVersion 0.0.1
     *
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     *
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Packet save(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.contractTemplateService.save(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return Transform.GetResult(pr);
    }


    /**
     * @api {post} /{project_name}/contract_template/release
     * @apiName release
     * @apiGroup contract_template
     * @apiDescription  发布合同模板数据
     * @apiVersion 0.0.1
     *
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     *
     */
    @RequestMapping(value = "/release", method = RequestMethod.POST)
    @ResponseBody
    public Packet release(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.contractTemplateService.release(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return Transform.GetResult(pr);
    }


    /**
     * @api {post} /{project_name}/contract_template/detail
     * @apiName detail
     * @apiGroup contract_template
     * @apiDescription  获取合同模板详情
     * @apiVersion 0.0.1
     *
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     *
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDetail(HttpServletRequest request) {
        return Transform.GetResult(contractTemplateService.queryDetail(request));
    }

    /**
     * @api {post} /{project_name}/contract_template/remove
     * @apiName remove
     * @apiGroup contract_template
     * @apiDescription  删除合同模板
     * @apiVersion 0.0.1
     *
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     *
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet remove(HttpServletRequest request) {
        return Transform.GetResult(contractTemplateService.remove(request));
    }

}

    
    
