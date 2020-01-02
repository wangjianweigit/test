package com.tk.oms.basicinfo.control;

import com.tk.oms.basicinfo.service.MemberFlagService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 会员标记配置管理
 * @author zhenghui
 * @date 2017-7-4
 */
@Controller
@RequestMapping("/member_flag")
public class MemberFlagControl {

    @Resource
    private MemberFlagService memberFlagService;

    /**
     * @api {post} /{project_name}/member_flag/page_list
     * @apiName member_flag_page_list
     * @apiGroup member_flag
     * @apiDescription  分页获取会员标记列表
     * @apiVersion 0.0.1
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 					会员标记集合
     * @apiSuccess {number}   	obj.id 					会员标记ID
     * @apiSuccess {string}   	obj.mark_name  		    标记名称
     * @apiSuccess {string}   	obj.mark_img_url  		标记图片路径
     * @apiSuccess {date}   	obj.create_date  		标记创建时间
     * @apiSuccess {number}   	obj.create_user_id  	标记创建人ID
     */
    @RequestMapping(value = "/page_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberFlagForPage(HttpServletRequest request) {
        return Transform.GetResult(memberFlagService.queryMemberFlagForPage(request));
    }

    /**
     * @api {post} /{project_name}/member_flag/list
     * @apiName member_flag_list
     * @apiGroup member_flag
     * @apiDescription  获取所有会员标记列表
     * @apiVersion 0.0.1
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 					会员标记集合
     * @apiSuccess {number}   	obj.id 					会员标记ID
     * @apiSuccess {string}   	obj.mark_name  		    标记名称
     * @apiSuccess {string}   	obj.mark_img_url  		标记图片路径
     * @apiSuccess {date}   	obj.create_date  		标记创建时间
     * @apiSuccess {number}   	obj.create_user_id  	标记创建人ID
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberFlagForList(HttpServletRequest request) {
        return Transform.GetResult(memberFlagService.queryMemberFlagForList(request));
    }

    /**
     * @api {post} /{project_name}/member_flag/detail
     * @apiName member_flag_detail
     * @apiGroup member_flag
     * @apiDescription  获取会员标记详情
     * @apiVersion 0.0.1
     *
     * @apiParam {number} id 		会员标记ID
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 					会员标记Map
     * @apiSuccess {number}   	obj.id 					会员标记ID
     * @apiSuccess {string}   	obj.mark_name  		    标记名称
     * @apiSuccess {string}   	obj.mark_img_url  		标记图片路径
     * @apiSuccess {date}   	obj.create_date  		标记创建时间
     * @apiSuccess {number}   	obj.create_user_id  	标记创建人ID
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberFlagForDetail(HttpServletRequest request) {
        return Transform.GetResult(memberFlagService.queryMemberFlagForDetail(request));
    }

    /**
     * @api {post} /{project_name}/member_flag/add
     * @apiName member_flag_add
     * @apiGroup member_flag
     * @apiDescription  添加会员标记
     * @apiVersion 0.0.1
     *
     * @apiParam {string} mark_name         标记名称
     * @apiParam {string} mark_img_url      标记图片路径
     * @apiParam {number} create_user_id  	标记创建人ID
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addMemberFlag(HttpServletRequest request) {
        return Transform.GetResult(memberFlagService.addMemberFlag(request));
    }

    /**
     * @api {post} /{project_name}/member_flag/edit
     * @apiName member_flag_edit
     * @apiGroup member_flag
     * @apiDescription  编辑会员标记
     * @apiVersion 0.0.1
     *
     * @apiParam {number} id 		        会员标记ID
     * @apiParam {string} mark_name         标记名称
     * @apiParam {string} mark_img_url      标记图片路径
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editMemberFlag(HttpServletRequest request) {
        return Transform.GetResult(memberFlagService.editMemberFlag(request));
    }

    /**
     * @api {post} /{project_name}/member_flag/remove
     * @apiName member_flag_remove
     * @apiGroup member_flag
     * @apiDescription  删除会员标记
     * @apiVersion 0.0.1
     *
     * @apiParam {number} id 		        会员标记ID
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeMemberFlag(HttpServletRequest request) {
        return Transform.GetResult(memberFlagService.removeMemberFlag(request));
    }

    /**
     * @api {post} /{project_name}/member_flag/sort
     * @apiName member_flag_sort
     * @apiGroup member_flag
     * @apiDescription  会员标记排序
     * @apiVersion 0.0.1
     *
     * @apiParam {number} toId 		    会员标记ID
     * @apiParam {number} fromId 		会员标记ID
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     */
    @RequestMapping(value = "/sort", method = RequestMethod.POST)
    @ResponseBody
    public Packet editMemberFlagForSort(HttpServletRequest request) {
        return Transform.GetResult(memberFlagService.editMemberFlagForSort(request));
    }
}
