package com.tk.oms.decoration.control;

import com.tk.oms.decoration.service.SocialMenuService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (c), 2019,  TongKu
 * FileName : SocialMenuControl
 *
 * @author: zhengfy
 * @version: 1.00
 * @date: 2019/3/11
 */
@Controller
@RequestMapping("/social_menu")
public class SocialMenuControl {


    @Resource
    private SocialMenuService socialMenuService;

    /**
     * @api {post}/{project_name}/social_menu/list 查询菜单列表
     * @apiGroup social_menu
     * @apiDescription 查询菜单列表
     *
     *
     * @apiSuccess {boolean} state 接口返回结果是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object[]} obj 接口返回数据
     * @apiSuccess {number} obj.id  主键ID
     * @apiSuccess {string} obj.menu_name  菜单名称
     * @apiSuccess {string} obj.menu_code  菜单代码
     * @apiSuccess {string} obj.is_hide  是否隐藏 （1.隐藏  2显示）
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryList(HttpServletRequest request) {
        return Transform.GetResult(this.socialMenuService.queryList(request));
    }



    /**
     * @api {post}/{project_name}/social_menu/remove 删除菜单数据
     * @apiGroup social_menu
     * @apiParam {number} id 菜单ID
     *
     * @apiSuccess {boolean} state 接口返回结果是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet remove(HttpServletRequest request) {
        return Transform.GetResult(this.socialMenuService.remove(request));
    }

    /**
     * @api {post}/{project_name}/social_menu/save 保存菜单数据
     * @apiGroup social_menu
     *
     * @apiSuccess {boolean} state 接口返回结果是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Packet save(HttpServletRequest request) {
        return Transform.GetResult(this.socialMenuService.save(request));
    }


    /**
     * @api {post}/{project_name}/social_menu/sort 排序
     * @apiGroup social_menu
     * @apiDescription 排序
     *
     * @apiParam {number} id1			第一个ID
     * @apiParam {number} id2			第二个ID
     *
     * @apiSuccess {boolean} state 接口返回结果是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     */
    @RequestMapping(value = "/sort", method = RequestMethod.POST)
    @ResponseBody
    public Packet sort(HttpServletRequest request) {
        return Transform.GetResult(this.socialMenuService.sort(request));
    }

    /**
     * @api {post}/{project_name}/social_menu/updateState 更改状态
     * @apiGroup social_menu
     * @apiDescription 更改状态
     *
     *
     * @apiSuccess {boolean} state 接口返回结果是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     */
    @RequestMapping(value = "/updateState", method = RequestMethod.POST)
    @ResponseBody
    public Packet updateState(HttpServletRequest request) {
        return Transform.GetResult(this.socialMenuService.updateState(request));
    }

    /**
     * @api {post}/{project_name}/social_menu/detail 获取详情
     * @apiGroup social_menu
     * @apiDescription 获取详情
     *
     *
     * @apiSuccess {boolean} state 接口返回结果是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDetail(HttpServletRequest request) {
        return Transform.GetResult(this.socialMenuService.queryDetail(request));
    }

    /**
     * @api {post}/{project_name}/social_menu/default 设置默认选中
     * @apiGroup social_menu
     * @apiDescription 设置默认选中
     *
     *
     * @apiSuccess {boolean} state 接口返回结果是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     */
    @RequestMapping(value = "/default", method = RequestMethod.POST)
    @ResponseBody
    public Packet updateDefault(HttpServletRequest request) {
        return Transform.GetResult(this.socialMenuService.updateDefault(request));
    }

}

    
    
