package com.tk.oms.marketing.control;

import com.tk.oms.marketing.service.UserGroupService;
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
 * Copyright (c), 2017, TongKu
 * FileName : UserGroupControl
 * 用户分组管理接口
 *
 * @author zhenghui
 * @version 1.00
 * @date 2017/09/21
 */
@Controller
@RequestMapping("/user_group")
public class UserGroupControl {

    @Resource
    private UserGroupService userGroupService;

    /**
     * @api {post} /{project_name}/user_group/list  分组分页列表
     * @apiName user_group_list
     * @apiGroup user_group
     * @apiDescription  分页获取用户分组列表
     * @apiVersion 0.0.1
     *
     * @apiParam {number} pageIndex 开始页码.
     * @apiParam {number} pageSize  每页数据量.
     * @apiParam {string} group_name 分组名称
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}  message 接口返回信息
     * @apiSuccess {object[]} obj 分组列表集合
     * @apiSuccess {number} total 分组列表总数
     * @apiSuccess {number} obj.id 分组ID
     * @apiSuccess {string} obj.name 分组名称
     * @apiSuccess {string} obj.remark 备注
     * @apiSuccess {char}   obj.state 停用状态 1:停用,2:启用
     * @apiSuccess {number} obj.user_num 用户数量（每个分组下包含用户的数量）
     * @apiSuccess {date}   obj.create_date 创建时间 yyyy-MM-dd HH:mm:ss格式
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryUserGroupListForPage(HttpServletRequest request) {
        return Transform.GetResult(this.userGroupService.queryUserGroupListForPage(request));
    }

    @RequestMapping(value = "/user_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryUserListForPage(HttpServletRequest request) {
        return Transform.GetResult(this.userGroupService.queryUserListForPage(request));
    }


    /**
     * @api {post} /{project_name}/user_group/common_list  用户分组列表
     * @apiName user_group_common_list
     * @apiGroup user_group
     * @apiDescription  获取用户分组列表(共用)
     * @apiVersion 0.0.1
     *
     * @apiParam {char} state 分组状态
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}  message 接口返回信息
     * @apiSuccess {object[]} obj 分组列表集合
     * @apiSuccess {number} obj.id 分组ID
     * @apiSuccess {string} obj.option 分组名称
     */
    @RequestMapping(value = "/common_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryUserGroupList(HttpServletRequest request) {
        return Transform.GetResult(this.userGroupService.queryUserGroupList(request));
    }

    /**
     * @api {post} /{project_name}/user_group/detail  用户分组详细信息
     * @apiName user_group_detail
     * @apiGroup user_group
     * @apiDescription  获取用户分组详细信息（包含分组用户列表）
     * @apiVersion 0.0.1
     *
     * @apiParam {number} group_id 分组ID
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}  message 接口返回信息
     * @apiSuccess {object[]} obj 分组详细信息集合
     * @apiSuccess {object[]} obj.user_group 信息
     * @apiSuccess {number} obj.user_group.id 分组ID
     * @apiSuccess {string} obj.user_group.name 分组名称
     * @apiSuccess {string} obj.user_group.remark 备注
     * @apiSuccess {char}   obj.user_group.state 停用状态 1:停用,2:启用
     * @apiSuccess {number} obj.user_group.user_num 用户数量（每个分组下包含用户的数量）
     * @apiSuccess {object[]} obj.user_list  用户列表
     * @apiSuccess {number} obj.user_list.user_id 用户ID
     * @apiSuccess {string} obj.user_list.login_name 用户名
     * @apiSuccess {string} obj.user_list.user_manage_name 用户姓名
     * @apiSuccess {string} obj.user_list.score_grade 用户等级
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryUserGroupDetail(HttpServletRequest request) {
        return Transform.GetResult(this.userGroupService.queryUserGroupDetail(request));
    }

    /**
     * @api {post} /{project_name}/user_group/add  添加用户分组
     * @apiName user_group_add
     * @apiGroup user_group
     * @apiDescription  添加用户分组信息以及分组用户添加
     * @apiVersion 0.0.1
     *
     * @apiParam {string} group_name 分组名称
     * @apiParam {string} remark 备注
     * @apiParam {number} public_user_id 创建用户ID
     * @apiParam {object[]} user_list 用户ID集合
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}  message 接口返回信息
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addUsergroup(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.userGroupService.addUserGroup(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/user_group/add_user  添加用户分组
     * @apiName user_group_add
     * @apiGroup user_group
     * @apiDescription  添加用户分组信息以及分组用户添加
     * @apiVersion 0.0.1
     *
     * @apiParam {string} group_name 分组名称
     * @apiParam {string} remark 备注
     * @apiParam {number} public_user_id 创建用户ID
     * @apiParam {object[]} user_list 用户ID集合
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}  message 接口返回信息
     */
    @RequestMapping(value = "/add_user", method = RequestMethod.POST)
    @ResponseBody
    public Packet addUserGroupForUser(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.userGroupService.addUserGroupForUser(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/user_group/edit  编辑用户分组
     * @apiName user_group_edit
     * @apiGroup user_group
     * @apiDescription  编辑用户分组信息以及分组用户编辑
     * @apiVersion 0.0.1
     *
     * @apiParam {number} group_id 分组ID
     * @apiParam {string} group_name 分组名称
     * @apiParam {string} remark 备注
     * @apiParam {object[]} user_list 用户ID集合
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}  message 接口返回信息
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editUserGroup(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.userGroupService.editUserGroup(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/user_group/edit_state  启用和禁用
     * @apiName user_group_edit_state
     * @apiGroup user_group
     * @apiDescription  编辑用户分组状态
     * @apiVersion 0.0.1
     *
     * @apiParam {number} group_id 分组ID
     * @apiParam {string} state 停用状态 1:停用,2:启用
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}  message 接口返回信息
     */
    @RequestMapping(value = "/edit_state", method = RequestMethod.POST)
    @ResponseBody
    public Packet editUserGroupSate(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.userGroupService.editUserGroupSate(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/user_group/remove  删除用户分组
     * @apiName user_group_remove
     * @apiGroup user_group
     * @apiDescription  删除用户分组以及每个的用户列表
     * @apiVersion 0.0.1
     *
     * @apiParam {number} group_id 分组ID
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}  message 接口返回信息
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeUsergroup(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.userGroupService.removeUserGroup(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return Transform.GetResult(pr);
    }

    @RequestMapping(value = "/remove_user", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeUserGroupDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.userGroupService.removeUserGroupForUser(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return Transform.GetResult(pr);
    }
}
