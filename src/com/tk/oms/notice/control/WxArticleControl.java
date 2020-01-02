package com.tk.oms.notice.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.notice.service.WxArticleService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : WxArticleControl
 * 微信文章管理  
 * control层 负责业务模块的流程控制
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-10-25
 */
@Controller
@RequestMapping("/wx_article")
public class WxArticleControl {
	@Resource
	private WxArticleService wxArticleService;
	
	/**
     * @api {post} /{project_name}/wx_article/list 查询文章列表
     * @apiGroup wx_article
     * @apiName wx_article_list
     * @apiDescription 查询文章列表
     * @apiVersion 0.0.1
     * @apiParam {number} pageIndex 起始页
     * @apiParam {number} pageSize 分页大小
     * 
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * @apiSuccess {number} total 数量
     * @apiSuccess {object[]} obj 微信文章列表
     * @apiSuccess {number} obj.id 		          文章ID
     * @apiSuccess {number} obj.name 		文章标题
     * @apiSuccess {string} obj.content 	文章内容
     * @apiSuccess {string} obj.img_url 	封面图url
     * @apiSuccess {string} obj.create_date 创建时间
     * @apiSuccess {string} obj.sort_id 	排序ID
     * 
     */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryList(HttpServletRequest request) {
        return Transform.GetResult(wxArticleService.queryList(request));
    }
	
	/**
     * @api {post} /{project_name}/wx_article/detail 查询文章详情
     * @apiGroup wx_article
     * @apiName wx_article_detail
     * @apiDescription 查询文章详情
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id		文章ID
     * 
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * @apiSuccess {object} obj 文章详情
     * @apiSuccess {number} obj.id 		          文章ID
     * @apiSuccess {number} obj.name 		文章标题
     * @apiSuccess {string} obj.content 	文章内容
     * @apiSuccess {string} obj.img_url		封面图
     * 
     */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDetail(HttpServletRequest request) {
        return Transform.GetResult(wxArticleService.queryDetail(request));
    }
	
	/**
     * @api {post} /{project_name}/wx_article/add 新增文章
     * @apiGroup wx_article
     * @apiName wx_article_add
     * @apiDescription 新增文章
     * @apiVersion 0.0.1
     * 
     * @apiParam {string} name			文章标题
     * @apiParam {string} content		文章内容
     * @apiParam {string} img_url		封面图
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * 
     */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet add(HttpServletRequest request) {
        return Transform.GetResult(wxArticleService.add(request));
    }
	
	/**
     * @api {post} /{project_name}/wx_article/edit 编辑文章
     * @apiGroup wx_article
     * @apiName wx_article_edit
     * @apiDescription 编辑文章
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id			文章ID
     * @apiParam {string} name			文章标题
     * @apiParam {string} content		文章内容
     * @apiParam {string} img_url		封面图
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * 
     */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet edit(HttpServletRequest request) {
        return Transform.GetResult(wxArticleService.edit(request));
    }
	
	/**
     * @api {post} /{project_name}/wx_article/remove 逻辑删除文章
     * @apiGroup wx_article
     * @apiName wx_article_remove
     * @apiDescription 逻辑删除文章
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id			文章ID
	 *
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * 
     */
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet remove(HttpServletRequest request) {
        return Transform.GetResult(wxArticleService.remove(request));
    }
	
	/**
     * @api {post} /{project_name}/wx_article/sort 文章排序
     * @apiGroup wx_article
     * @apiName wx_article_sort
     * @apiDescription 文章排序
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id1			第一个ID
	 * @apiParam {number} id2			第二个ID
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * 
     */
	@RequestMapping(value = "/sort", method = RequestMethod.POST)
    @ResponseBody
    public Packet sort(HttpServletRequest request) {
        return Transform.GetResult(wxArticleService.sort(request));
    }
}
