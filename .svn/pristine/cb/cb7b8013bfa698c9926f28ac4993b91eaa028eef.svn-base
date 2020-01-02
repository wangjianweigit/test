package com.tk.oms.analysis.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.analysis.service.MemberVisitService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : MemberVisitControl
 * 会员访问统计
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-5-13
 */
@Controller
@RequestMapping("member_visit")
public class MemberVisitControl {
	@Resource
	private MemberVisitService memberVisitService;
	
	/**
     * @api {post} /{project_name}/member_visit/list
     * @apiName list
     * @apiGroup member_visit
     * @apiDescription  会员访问列表
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} pageIndex 		开始页码
     * @apiParam {number} pageSize 			每页数据量
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 					查询运费模板列表
     * @apiSuccess {number}     total 					运费模板数量
     * @apiSuccess {number}   	obj.id 					模板ID
     * @apiSuccess {string}   	obj.name				模板名称
     * @apiSuccess {string}   	obj.time 				运送时间
     * @apiSuccess {char}   	obj.state				是否启用  1（停用）2（启用）
     * @apiSuccess {char}   	obj.is_default 			是否默认,同时只允许存在一个默认运费模板  1（非默认）2（默认）
     * @apiSuccess {date}   	obj.edit_date 			最后编辑时间
     * 
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberVisitList(HttpServletRequest request) {
        return Transform.GetResult(memberVisitService.queryMemberVisitList(request));
    }
    
    /**
     * @api {post} /{project_name}/member_visit/mark_edit
     * @apiName mark_edit
     * @apiGroup member_visit
     * @apiDescription  更新用户标记
     * @apiVersion 0.0.1
     * 
     * @apiParam {string} user_name 	用户名
     * @apiParam {number} mark 			标记：1、红；2、黄；3、蓝
     * @apiParam {string} remark		标记描述
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
    @RequestMapping(value = "/mark_edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editUserMark(HttpServletRequest request) {
        return Transform.GetResult(memberVisitService.editUserMark(request));
    }

    /**
     * @api {post} /{project_name}/member_visit/mark_remove
     * @apiName mark_remove
     * @apiGroup member_visit
     * @apiDescription  删除用户标记
     * @apiVersion 0.0.1
     * 
     * @apiParam {string} user_name 	用户名
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
    @RequestMapping(value = "/mark_remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeUserMark(HttpServletRequest request) {
        return Transform.GetResult(memberVisitService.removeUserMark(request));
    }

    /**
     * @api {post} /{project_name}/member_visit/ip_record
     * @apiName ip_record
     * @apiGroup member_visit
     * @apiDescription  查询用户IP访问记录
     * @apiVersion 0.0.1
     * 
     * @apiParam {string} user_name 	用户名
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
    @RequestMapping(value = "/ip_record", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryUserIpRecord(HttpServletRequest request) {
        return Transform.GetResult(memberVisitService.queryUserIpRecord(request));
    }

    
    /**
     * @api {post} /{project_name}/member_visit/order_record
     * @apiName order_record
     * @apiGroup member_visit
     * @apiDescription  查询用户订单记录
     * @apiVersion 0.0.1
     * 
     * @apiParam {string} user_name 	用户名
     * @apiParam {string} type 			查询类型
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
    @RequestMapping(value = "/order_record", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryUserOrderRecord(HttpServletRequest request) {
        return Transform.GetResult(memberVisitService.queryUserOrderRecord(request));
    }

    
    /**
     * @api {post} /{project_name}/member_visit/browse_record
     * @apiName browse_record
     * @apiGroup member_visit
     * @apiDescription  查询用户浏览记录
     * @apiVersion 0.0.1
     * 
     * @apiParam {string} user_name 	用户名
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
    @RequestMapping(value = "/browse_record", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryUserBrowseRecord(HttpServletRequest request) {
        return Transform.GetResult(memberVisitService.queryUserBrowseRecord(request));
    }
}
