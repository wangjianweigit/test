package com.tk.oms.basicinfo.control;

import com.tk.oms.basicinfo.service.ColorsService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 商品颜色管理
 * @author zhenghui
 */
@Controller
@RequestMapping("/colors")
public class ColorsControl {

    @Resource
    private ColorsService colorsService;

    /**
     * @api {post} /{project_name}/colors/group_list
     * @apiName colors_group_list
     * @apiGroup colors
     * @apiDescription  查询色系列表
     * @apiVersion 0.0.1
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 					查询色系列表
     * @apiSuccess {number}   	obj.id 					色系ID
     * @apiSuccess {string}   	obj.color_name  		色系名称
     * @apiSuccess {string}   	obj.color_code  		色系代码
     */
    @RequestMapping(value = "/group_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryColorsGroupList(HttpServletRequest request) {
        return Transform.GetResult(colorsService.queryColorsGroupList(request));
    }

    /**
     * @api {post} /{project_name}/colors/color_list
     * @apiName colors_color_list
     * @apiGroup colors
     * @apiDescription  查询颜色列表
     * @apiVersion 0.0.1
     *
     * @apiParam {number} parent_id 		色系ID
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 					查询颜色列表
     * @apiSuccess {number}   	obj.id 					颜色ID
     * @apiSuccess {string}   	obj.color_name  		颜色名称
     * @apiSuccess {string}   	obj.color_code  		颜色代码
     */
    @RequestMapping(value = "/color_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryColorsList(HttpServletRequest request) {
        return Transform.GetResult(colorsService.queryColorsList(request));
    }

    /**
     * @api {post} /{project_name}/colors/group_add
     * @apiName colors_group_add
     * @apiGroup colors
     * @apiDescription  添加色系
     * @apiVersion 0.0.1
     *
     * @apiParam {string} color_name 		色系名称
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     */
    @RequestMapping(value = "/group_add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addColorsGroup(HttpServletRequest request) {
        return Transform.GetResult(colorsService.addColorsGroup(request));
    }

    /**
     * @api {post} /{project_name}/colors/color_add
     * @apiName colors_color_add
     * @apiGroup colors
     * @apiDescription  添加颜色
     * @apiVersion 0.0.1
     *
     * @apiParam {string} color_name 		颜色名称
     * @apiParam {string} color_code  		颜色代码
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     */
    @RequestMapping(value = "/color_add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addColors(HttpServletRequest request) {
        return Transform.GetResult(colorsService.addColors(request));
    }

    /**
     * @api {post} /{project_name}/colors/group_edit
     * @apiName colors_group_edit
     * @apiGroup colors
     * @apiDescription  编辑色系
     * @apiVersion 0.0.1
     *
     * @apiParam {string} color_name 		色系名称
     * @apiParam {number} id  		        色系ID
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     */
    @RequestMapping(value = "/group_edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editColorsGroup(HttpServletRequest request) {
        return Transform.GetResult(colorsService.editColorsGroup(request));
    }

    /**
     * @api {post} /{project_name}/colors/color_edit
     * @apiName colors_color_edit
     * @apiGroup colors
     * @apiDescription  编辑颜色
     * @apiVersion 0.0.1
     *
     * @apiParam {string} color_name 		颜色名称
     * @apiParam {string} color_code  		颜色代码
     * @apiParam {number} id  		        颜色ID
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     */
    @RequestMapping(value = "/color_edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editColors(HttpServletRequest request) {
        return Transform.GetResult(colorsService.editColors(request));
    }


    @RequestMapping(value = "/group_remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeColorsGroup(HttpServletRequest request) {
        return Transform.GetResult(colorsService.removeColorsGroup(request));
    }


    @RequestMapping(value = "/color_remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeColors(HttpServletRequest request) {
        return Transform.GetResult(colorsService.removeColors(request));
    }

}
