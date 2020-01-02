package com.tk.oms.notice.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.notice.service.NewsService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;

@Controller
@RequestMapping("/news")
public class NewsControl {
	@Resource
    private NewsService newsService;
	/**
     * @param request
     * @return
     * @api {post} /{project_name}/news/list 查询新闻列表
     * @apiGroup news
     * @apiName news_list
     * @apiDescription 查询新闻列表
     * @apiVersion 0.0.1
     * @apiParam {number} pageIndex 起始页
     * @apiParam {number} pageSize 分页大小
     * 
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * @apiSuccess {number} total 数量
     * @apiSuccess {object[]} obj 新闻列表
     * @apiSuccess {number} obj.id 		          新闻ID
     * @apiSuccess {number} obj.name 		新闻标题
     * @apiSuccess {string} obj.content 	新闻内容
     * @apiSuccess {string} obj.state 		状态 1.未启用 2.启用
     * @apiSuccess {string} obj.create_date  创建时间
     * 
     */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryNewsList(HttpServletRequest request) {
        return Transform.GetResult(newsService.queryNewsList(request));
    }
	
	/**
     * @param request
     * @return
     * @api {post} /{project_name}/news/detail 查询新闻详情
     * @apiGroup news
     * @apiName news_detail
     * @apiDescription 查询新闻详情
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id		新闻ID
     * 
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * @apiSuccess {object} obj 新闻详情
     * @apiSuccess {number} obj.id 		          新闻ID
     * @apiSuccess {number} obj.name 		新闻标题
     * @apiSuccess {string} obj.content 	新闻内容
     * @apiSuccess {string} obj.label_color 标签颜色
     * @apiSuccess {string} obj.label_text	标签文字
     * @apiSuccess {string} obj.img_url		封面图
     * 
     */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryNewsDetail(HttpServletRequest request) {
        return Transform.GetResult(newsService.queryNewsDetail(request));
    }
	
	/**
     * @param request
     * @return
     * @api {post} /{project_name}/news/add 新增新闻
     * @apiGroup news
     * @apiName news_add
     * @apiDescription 新增新闻
     * @apiVersion 0.0.1
     * 
     * @apiParam {string} name			新闻标题
     * @apiParam {string} content		新闻内容
     * @apiParam {string} label_color	标签颜色
     * @apiParam {string} label_text	标签文字
     * @apiParam {string} img_url		封面图
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * 
     */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addNews(HttpServletRequest request) {
        return Transform.GetResult(newsService.addNews(request));
    }
	
	/**
     * @param request
     * @return
     * @api {post} /{project_name}/news/edit 编辑新闻
     * @apiGroup news
     * @apiName news_edit
     * @apiDescription 编辑新闻
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id			新闻ID
     * @apiParam {string} name			新闻标题
     * @apiParam {string} content		新闻内容
     * @apiParam {string} label_color	标签颜色
     * @apiParam {string} label_text	标签文字
     * @apiParam {string} img_url		封面图
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * 
     */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editNews(HttpServletRequest request) {
        return Transform.GetResult(newsService.editNews(request));
    }
	
	/**
     * @param request
     * @return
     * @api {post} /{project_name}/news/remove 逻辑删除新闻
     * @apiGroup news
     * @apiName news_remove
     * @apiDescription 逻辑删除新闻
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id			新闻ID
	 *
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * 
     */
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeNews(HttpServletRequest request) {
        return Transform.GetResult(newsService.removeNews(request));
    }
	
	/**
     * @param request
     * @return
     * @api {post} /{project_name}/news/sort 新闻排序
     * @apiGroup news
     * @apiName news_sort
     * @apiDescription 新闻排序
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
    public Packet sortNews(HttpServletRequest request) {
        return Transform.GetResult(newsService.sortNews(request));
    }
	
	/**
     * @param request
     * @return
     * @api {post} /{project_name}/news/state 启/停用
     * @apiGroup news
     * @apiName news_state
     * @apiDescription 启/停用
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id	新闻ID
     * 
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * 
     */
	@RequestMapping(value = "/state", method = RequestMethod.POST)
    @ResponseBody
    public Packet editNewsState(HttpServletRequest request) {
        return Transform.GetResult(newsService.editNewsState(request));
    }
}
