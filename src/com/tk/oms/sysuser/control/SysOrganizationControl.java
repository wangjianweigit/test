package com.tk.oms.sysuser.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.sysuser.service.SysOrganizationService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2016, Tongku
 * FileName : SysOrganizationControl
 * 商品分类
 *
 * @author wangpeng
 * @version 1.00
 * @date 2017-5-10
 */
@Controller
@RequestMapping("/organization")
public class SysOrganizationControl {

    @Resource
    private SysOrganizationService sysOrganizationService;

    /**
     * @api {post} /{project_name}/organization/list 
     * @apiName list
     * @apiGroup organization
     * @apiDescription  查询组织信息
     * @apiVersion 0.0.1
     * 
     * @apiParam {string} organization_name 组织名称
     * @apiParam {number} parent_id 分类父id
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 组织列表
     * @apiSuccess {number}   	obj.id 分类id
     * @apiSuccess {string}   	obj.organization_name 组织名称
     * @apiSuccess {number}   	obj.parent_id 分类父id
     * @apiSuccess {number}   	obj.manager_limit 机构管理人员上限
     * @apiSuccess {string}   	obj.remark 备注
     * @apiSuccess {number}   	obj.children 子集个数
     * @apiSuccess {date}     	obj.create_date 组织创建日期
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySysOrganizationList(HttpServletRequest request) {
        return Transform.GetResult(sysOrganizationService.querySysOrganizationList(request));
    }
    /**
     * @api {post} /{project_name}/organization/add 
     * @apiName add
     * @apiGroup organization
     * @apiDescription  新增组织架构信息
     * @apiVersion 0.0.1
     * 
     * @apiParam {string} organization_name 组织名称
     * @apiParam {string} parent_id 组织父id
     * @apiParam {number} manager_limit 机构管理人员上限
     * @apiParam {string} remark 备注
     * @apiParam {number} public_user_id 创建用户ID
     *  
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息.
     * @apiSuccess {object[]}   obj 
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addSysOrganization(HttpServletRequest request) {
        return Transform.GetResult(sysOrganizationService.addSysOrganization(request));
    }

    /**
     * @api {post} /{project_name}/organization/batch_edit
     * @apiName batch_edit
     * @apiGroup organization
     * @apiDescription  批量更新组织信息
     * @apiVersion 0.0.1
     * 
     * @apiParam {Object[]} dataList 备注
     * @apiParam {number} dataList.id                      	组织ID，此处有值则为更新
     * @apiParam {string} dataList.organization_name        组织新名字
     * @apiParam {number} dataList.parent_id                上级ID，如为新增，则必传值
     * @apiParam {number} dataList.manager_limit            机构管理人员上限
     * @apiParam {number} dataList.remark                   备注
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息.
     * @apiSuccess {object[]}   obj 
     */
	@RequestMapping(value = "/batch_edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet batchEditOrganization(HttpServletRequest request) {
        return Transform.GetResult(sysOrganizationService.batchEditOrganization(request));
    }
	
    /**
     * @api {post} /{project_name}/organization/delete 
     * @apiName delete
     * @apiGroup organization
     * @apiDescription  删除组织信息
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id 分类id
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息.
     * @apiSuccess {object[]}   obj 
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeOrganization(HttpServletRequest request) {
        return Transform.GetResult(sysOrganizationService.removeOrganization(request));
    }
   
    /**
     * @api {post} /{project_name}/organization/all_list 
     * @apiName all_list
     * @apiGroup organization
     * @apiDescription  查询所有组织信息列表
     * @apiVersion 0.0.1
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 组织列表
     * @apiSuccess {number}   	obj.id 分类id
     * @apiSuccess {string}   	obj.option 组织名称
     * @apiSuccess {number}   	obj.pid 分类父id
     */
    @RequestMapping(value = "/all_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySysOrganizationAllList(HttpServletRequest request) {
        return Transform.GetResult(sysOrganizationService.queryAllList(request));
    }
}
