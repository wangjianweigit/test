package com.tk.oms.basicinfo.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.basicinfo.service.ArticleService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;


/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : ArticleControl
 * 单页管理
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-5-31
 */
@Controller
@RequestMapping("article")
public class ArticleControl {
	@Resource
    private ArticleService articleService;
	
	/**
     * @api {post} /{project_name}/article/custom_list
     * @apiName custom_list
     * @apiGroup article
     * @apiDescription  自定义单页列表
     * @apiVersion 0.0.1
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 				自定义单页列表
     * @apiSuccess {number}   	obj.id 				单页ID
     * @apiSuccess {string}   	obj.name			单页名称
     * @apiSuccess {string}   	obj.content 		单页内容
     * @apiSuccess {number}   	obj.article_info_id 栏目ID
     * @apiSuccess {string}   	obj.state 			启/禁用  1禁用,2启用
     * 
     */
    @RequestMapping(value = "/custom_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryCustomList(HttpServletRequest request) {
        return Transform.GetResult(articleService.queryCustomList(request));
    }
    
    /**
     * @api {post} /{project_name}/article/custom_edit
     * @apiName custom_edit
     * @apiGroup article
     * @apiDescription  自定义单页编辑
     * @apiVersion 0.0.1
     * 
     * 
     * @apiParam {number} id 				单页ID
     * @apiParam {string} name 				单页名称
     * @apiParam {string} content 			单页内容
     * @apiParam {number} article_info_id 	栏目ID
     * @apiParam {string} state 			启/禁用  1禁用,2启用
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
    @RequestMapping(value = "/custom_edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editCustom(HttpServletRequest request) {
        return Transform.GetResult(articleService.editCustom(request));
    }
    
    /**
     * @api {post} /{project_name}/article/custom_remove
     * @apiName custom_remove
     * @apiGroup article
     * @apiDescription  自定义单页删除
     * @apiVersion 0.0.1
     * 
     * 
     * @apiParam {number} id 				单页ID
     * @apiParam {string} name 				单页名称
     * @apiParam {string} content 			单页内容
     * @apiParam {number} article_info_id 	栏目ID
     * @apiParam {string} state 			启/禁用  1禁用,2启用
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
    @RequestMapping(value = "/custom_remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeCustom(HttpServletRequest request) {
        return Transform.GetResult(articleService.removeCustom(request));
    }
    
    /**
     * @api {post} /{project_name}/article/custom_sort
     * @apiName custom_sort
     * @apiGroup article
     * @apiDescription  自定义单页排序
     * @apiVersion 0.0.1
     * 
     * 
     * @apiParam {number} id1				第一个单页ID
     * @apiParam {number} id2 				第二个单页ID
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
    @RequestMapping(value = "/custom_sort", method = RequestMethod.POST)
    @ResponseBody
    public Packet editCustomSort(HttpServletRequest request) {
        return Transform.GetResult(articleService.editCustomSort(request));
    }
    
    /**
     * @api {post} /{project_name}/article/custom_state
     * @apiName custom_state
     * @apiGroup article
     * @apiDescription  自定义单页启/禁用
     * @apiVersion 0.0.1
     * 
     * 
     * @apiParam {number} id				单页ID
     * @apiParam {string} state 			启/禁用  1禁用,2启用
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
    @RequestMapping(value = "/custom_state", method = RequestMethod.POST)
    @ResponseBody
    public Packet editCustomState(HttpServletRequest request) {
        return Transform.GetResult(articleService.editCustomState(request));
    }
    
    /**
     * @api {post} /{project_name}/article/programa_list
     * @apiName programa_list
     * @apiGroup article
     * @apiDescription  栏目列表
     * @apiVersion 0.0.1
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 		栏目列表
     * @apiSuccess {number}   	obj.id 		栏目ID
     * @apiSuccess {string}   	obj.name	栏目名称
     * 
     */
    @RequestMapping(value = "/programa_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProgramaList(HttpServletRequest request) {
        return Transform.GetResult(articleService.queryProgramaList(request));
    }
    
    /**
     * @api {post} /{project_name}/article/programa_edit
     * @apiName programa_edit
     * @apiGroup article
     * @apiDescription  栏目编辑
     * @apiVersion 0.0.1
     * 
     * 
     * @apiParam {number} id 				栏目ID
     * @apiParam {string} name 				栏目名称
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
    @RequestMapping(value = "/programa_edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editPrograma(HttpServletRequest request) {
        return Transform.GetResult(articleService.editPrograma(request));
    }
    
    /**
     * @api {post} /{project_name}/article/programa_remove
     * @apiName programa_remove
     * @apiGroup article
     * @apiDescription  删除栏目
     * @apiVersion 0.0.1
     * 
     * 
     * @apiParam {number} id 				栏目ID
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
    @RequestMapping(value = "/programa_remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removePrograma(HttpServletRequest request) {
        return Transform.GetResult(articleService.removePrograma(request));
    }
    
    /**
     * @api {post} /{project_name}/article/programa_state
     * @apiName programa_state
     * @apiGroup article
     * @apiDescription  栏目启 /禁用
     * @apiVersion 0.0.1
     * 
     * 
     * @apiParam {number} id 				栏目ID
     * @apiParam {string} state 			启/禁用  1禁用,2启用
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
    @RequestMapping(value = "/programa_state", method = RequestMethod.POST)
    @ResponseBody
    public Packet editProgramaState(HttpServletRequest request) {
        return Transform.GetResult(articleService.editProgramaState(request));
    }
    
    /**
     * @api {post} /{project_name}/article/programa_combobox
     * @apiName programa_combobox
     * @apiGroup article
     * @apiDescription  栏目下拉框
     * @apiVersion 0.0.1
     * 
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object}     obj 		
     * @apiSuccess {number}   	obj.id 		栏目ID
     * @apiSuccess {string}   	obj.option	栏目名称
     * 
     */
    @RequestMapping(value = "/programa_combobox", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProgramaCombobox(HttpServletRequest request) {
        return Transform.GetResult(articleService.queryProgramaCombobox(request));
    }
    /**
     * @api {post} /{project_name}/article/programa_sort
     * @apiName programa_sort
     * @apiGroup article
     * @apiDescription  栏目排序
     * @apiVersion 0.0.1
     * 
     * 
     * @apiParam {number} id1				第一个单页ID
     * @apiParam {number} id2 				第二个单页ID
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
    @RequestMapping(value = "/programa_sort", method = RequestMethod.POST)
    @ResponseBody
    public Packet editProgramaSort(HttpServletRequest request) {
        return Transform.GetResult(articleService.editProgramaSort(request));
    }
    
    /**
     * @api {post} /{project_name}/article/fixed_list
     * @apiName fixed_list
     * @apiGroup article
     * @apiDescription  固定单页列表
     * @apiVersion 0.0.1
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 					固定单页列表
     * @apiSuccess {number}   	obj.id 					单页ID
     * @apiSuccess {string}   	obj.name				单页名称
     * @apiSuccess {string}   	obj.content 			单页内容
     * @apiSuccess {string}   	obj.state 				启/禁用  1禁用,2启用
     * 
     */
    @RequestMapping(value = "/fixed_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryFixedList(HttpServletRequest request) {
        return Transform.GetResult(articleService.queryFixedList(request));
    }
    
    /**
     * @api {post} /{project_name}/article/fixed_edit
     * @apiName fixed_edit
     * @apiGroup article
     * @apiDescription  固定单页编辑
     * @apiVersion 0.0.1
     * 
     * 
     * @apiParam {number} id 				单页ID
     * @apiParam {string} name 				单页名称
     * @apiParam {string} content 			单页内容
     * @apiParam {string} state 			启/禁用  1禁用,2启用
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
    @RequestMapping(value = "/fixed_edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editFixed(HttpServletRequest request) {
        return Transform.GetResult(articleService.editFixed(request));
    }
    
    /**
     * @api {post} /{project_name}/article/fixed_state
     * @apiName fixed_state
     * @apiGroup article
     * @apiDescription  固定单页启/禁用
     * @apiVersion 0.0.1
     * 
     * 
     * @apiParam {number} id				单页ID
     * @apiParam {string} state 			启/禁用  1禁用,2启用
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
    @RequestMapping(value = "/fixed_state", method = RequestMethod.POST)
    @ResponseBody
    public Packet editFixedState(HttpServletRequest request) {
        return Transform.GetResult(articleService.editFixedState(request));
    }
    
    /**
     * @api {post} /{project_name}/article/detail
     * @apiName detail
     * @apiGroup article
     * @apiDescription  单页详情
     * @apiVersion 0.0.1
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object}   obj 					单页详情
     * @apiSuccess {number}   	obj.id 					单页ID
     * @apiSuccess {string}   	obj.name				单页名称
     * @apiSuccess {string}   	obj.content 			单页内容
     * @apiSuccess {string}   	obj.state 				启/禁用  1禁用,2启用
     * 
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryArticleDetail(HttpServletRequest request) {
        return Transform.GetResult(articleService.queryArticleDetail(request));
    }
}
