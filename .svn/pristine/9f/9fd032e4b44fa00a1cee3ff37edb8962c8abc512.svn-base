package com.tk.oms.stationed.control;

import com.tk.oms.stationed.service.StationedTemplateService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (c), 2018,  TongKu
 * FileName : TemplateControl
 *
 * @author: zhengfy
 * @version: 1.00
 * @date: 2018/8/10
 */

@Controller
@RequestMapping("/stationed_template")
public class StationedTemplateControl {


    @Resource
    private StationedTemplateService stationedTemplateService;


    /**
     * @api {post} /{project_name}/stationed_template/list
     * @apiName list
     * @apiGroup stationed_template
     * @apiDescription  入驻商模板列表
     * @apiVersion 0.0.1
     *
     * @apiParam {number} pageIndex 		开始页码
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 查询入驻商申请数据列表
     * @apiSuccess {number} 	obj.id 入驻商ID
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryList(HttpServletRequest request) {
        return Transform.GetResult(stationedTemplateService.queryList(request));
    }


    /**
     * @api {post} /{project_name}/stationed_template/detail
     * @apiName detail
     * @apiGroup stationed_template
     * @apiDescription  入驻商模板详情
     * @apiVersion 0.0.1
     *
     * @apiParam {number} pageIndex 		开始页码
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 查询入驻商申请数据列表
     * @apiSuccess {number} 	obj.id 入驻商ID
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDetail(HttpServletRequest request) {
        return Transform.GetResult(stationedTemplateService.queryDetail(request));
    }

    /**
     * @api {post} /{project_name}/stationed_template/edit
     * @apiName edit
     * @apiGroup stationed_template
     * @apiDescription  编辑入驻商模板
     * @apiVersion 0.0.1
     *
     * @apiParam {number} pageIndex 		开始页码
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 查询入驻商申请数据列表
     * @apiSuccess {number} 	obj.id 入驻商ID
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet edit(HttpServletRequest request) {
        return Transform.GetResult(stationedTemplateService.edit(request));
    }


    /**
     * @api {post} /{project_name}/stationed_template/template_id
     * @apiName template_id
     * @apiGroup stationed_template
     * @apiDescription  查询模板序列ID
     * @apiVersion 0.0.1
     *
     * @apiParam {number} pageIndex 		开始页码
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 查询入驻商申请数据列表
     * @apiSuccess {number} 	obj.id 入驻商ID
     */
    @RequestMapping(value = "/template_id", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryTemplateId(HttpServletRequest request) {
        return Transform.GetResult(stationedTemplateService.queryTemplateId());
    }



    @RequestMapping(value = "/default", method = RequestMethod.POST)
    @ResponseBody
    public Packet templateDefault(HttpServletRequest request) {
        return Transform.GetResult(stationedTemplateService.templateDefault(request));
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Packet update(HttpServletRequest request) {
        return Transform.GetResult(stationedTemplateService.update(request));
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet remove(HttpServletRequest request) {
        return Transform.GetResult(stationedTemplateService.remove(request));
    }


}

    
    
