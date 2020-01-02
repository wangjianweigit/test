package com.tk.oms.decoration.control;

import com.tk.oms.decoration.service.NavigationService;
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
 * 导航管理
 * @author zhenghui
 * @data 2017.1.16
 */
@Controller
@RequestMapping("/navigation")
public class NavigationControl {

    @Resource
    private NavigationService navigationService;

    /**
     * @api {post} /{project_name}/navigation/list 获取导航列表
     * @apiGroup navigation
     * @apiDescription  获取导航列表信息
     * @apiVersion 0.0.1
     *
     * @apiParam {number}   template_id 装修模板ID
     * @apiParam {number}   site_id 站点ID
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 目录结构层级列表
     * @apiSuccess {number}   	obj.id 导航ID
     * @apiSuccess {string}   	obj.name 导航名称
     * @apiSuccess {string}   	obj.url 导航链接
     * @apiSuccess {string}   	obj.tag_name 导航标签名称
     * @apiSuccess {string}   	obj.tag_color 导航标签颜色
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryNavigationListForPage(HttpServletRequest request) {
        return Transform.GetResult(this.navigationService.queryNavigationListForPage(request));
    }

    /**
     * @api {post} /{project_name}/navigation/detail 获取导航详情
     * @apiGroup navigation
     * @apiDescription  获取导航详细信息
     * @apiVersion 0.0.1
     *
     * @apiParam {number}   id 导航ID
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 目录结构层级列表
     * @apiSuccess {number}   	obj.id 导航ID
     * @apiSuccess {string}   	obj.name 导航名称
     * @apiSuccess {string}   	obj.url 导航链接
     * @apiSuccess {string}   	obj.tag_name 导航标签名称
     * @apiSuccess {string}   	obj.tag_color 导航标签颜色
     * @apiSuccess {char}   	obj.url_type 导航路径类型
     * @apiSuccess {number}   	obj.page_id 页面ID
     * @apiSuccess {number}   	obj.sort_id 排序ID
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryNavigationDetail(HttpServletRequest request) {
        return Transform.GetResult(this.navigationService.queryNavigationDetail(request));
    }

    /**
     * @api {post} /{project_name}/navigation/add 新增导航
     * @apiGroup navigation
     * @apiDescription  添加装修导航信息
     * @apiVersion 0.0.1
     *
     * @apiParam {string}   name 导航名称
     * @apiParam {string}   url 导航链接
     * @apiParam {string}   tag_name 导航标签名称
     * @apiParam {string}   tag_color 导航标签颜色
     * @apiParam {char}     url_type 导航路径类型
     * @apiParam {number}   page_id 页面ID
     * @apiParam {number}   sort_id 排序ID
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addNavigation(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.navigationService.addNavigation(request);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            ex.printStackTrace();
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/navigation/edit 编辑导航
     * @apiGroup navigation
     * @apiDescription  编辑装修导航信息
     * @apiVersion 0.0.1
     *
     * @apiParam {number}   id 导航ID
     * @apiParam {string}   name 导航名称
     * @apiParam {string}   url 导航链接
     * @apiParam {string}   tag_name 导航标签名称
     * @apiParam {string}   tag_color 导航标签颜色
     * @apiParam {char}     url_type 导航路径类型
     * @apiParam {number}   page_id 页面ID
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editNavigation(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.navigationService.editNavigation(request);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            ex.printStackTrace();
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/navigation/remove 删除导航
     * @apiGroup navigation
     * @apiDescription  删除装修导航信息
     * @apiVersion 0.0.1
     *
     * @apiParam {number}   id 导航ID
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeNavigation(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.navigationService.removeNavigation(request);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            ex.printStackTrace();
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/navigation/sort 导航排序
     * @apiGroup navigation
     * @apiDescription  排序装修导航信息
     * @apiVersion 0.0.1
     *
     * @apiParam {number}   toId 导航ID
     * @apiParam {number}   fromId 导航ID
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     */
    @RequestMapping(value = "/sort", method = RequestMethod.POST)
    @ResponseBody
    public Packet updateNavSort(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.navigationService.updateNavSort(request);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            ex.printStackTrace();
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/navigation/drumbeating_operation 导航广告操作
     * @apiGroup navigation
     * @apiDescription  添加导航广告图片
     * @apiVersion 0.0.1
     *
     * @apiParam {number}   site_id 站点ID
     * @apiParam {number}   template_id 装修模板ID
     * @apiParam {string}   img_url 导航广告图片路径
     * @apiParam {number}   img_x_value 广告图片X坐标
     * @apiParam {number}   img_y_value 广告图片Y坐标
     * @apiParam {string}   img_href 导航广告链接
     * @apiParam {char}     state 导航广告状态
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     */
    @RequestMapping(value = "/drumbeating_operation", method = RequestMethod.POST)
    @ResponseBody
    public Packet addOrEditNavDrumbeating(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.navigationService.addOrEditNavDrumbeating(request);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            ex.printStackTrace();
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/navigation/drumbeating_detail 获取导航广告详情
     * @apiGroup navigation
     * @apiDescription  获取导航广告详细信息
     * @apiVersion 0.0.1
     *
     * @apiParam {number}   template_id 装修模板ID
     * @apiParam {number}   site_id 站点ID
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 目录结构层级列表
     * @apiSuccess {number}   	obj.id 导航广告ID
     * @apiSuccess {string}   	obj.img_url 导航广告图片路径
     * @apiSuccess {number}   	obj.img_x_value 广告图片X坐标
     * @apiSuccess {number}   	obj.img_y_value 广告图片Y坐标
     * @apiSuccess {string}   	obj.img_href 导航广告链接
     * @apiSuccess {char}   	obj.state 导航广告状态
     */
    @RequestMapping(value = "/drumbeating_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryNavDrumbeatingDetail(HttpServletRequest request) {
        return Transform.GetResult(this.navigationService.queryNavDrumbeatingDetail(request));
    }

    /**
     * @api {post} /{project_name}/navigation/page_list 获取页面列表
     * @apiGroup navigation
     * @apiDescription  获取页面列表信息
     * @apiVersion 0.0.1
     *
     * @apiParam {number}   template_id 装修模板ID
     * @apiParam {number}   site_id 站点ID
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 目录结构层级列表
     * @apiSuccess {number}   	obj.id 页面ID
     * @apiSuccess {string}   	obj.page_name 页面名称
     */
    @RequestMapping(value = "/page_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryPageList(HttpServletRequest request) {
        return Transform.GetResult(this.navigationService.queryPageList(request));
    }
}
