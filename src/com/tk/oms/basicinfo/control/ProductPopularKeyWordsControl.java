package com.tk.oms.basicinfo.control;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.basicinfo.service.ProductPopularKeyWordsService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;





@Controller
@RequestMapping("/product_popular_key_words")
public class ProductPopularKeyWordsControl {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private ProductPopularKeyWordsService productPopularKeyWordsService;
	 /**
     * @api{post} /{oms_server}/product_popular_key_words/list 热门关键字列表
     * @apiGroup list
     * @apiName list
     * @apiDescription  热门关键字
     * @apiVersion 0.0.1
     * @apiParam{number} [pageIndex=1] 起始页
     * @apiParam{number} [pageSize=10] 分页大小
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 热门关键字信息
     * @apiSuccess{number} total 总条数
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductRedkeywordList(HttpServletRequest request) {
        return Transform.GetResult(productPopularKeyWordsService.queryList(request));
    }
    
    /**
     * @api{post} /{oms_server}/product_popular_key_words/add 热门关键字新增
     * @apiGroup add
     * @apiName add
     * @apiDescription  热门关键字新增
     * @apiVersion 0.0.1
     * @apiSuccess {number} ID    主键ID
     * @apiSuccess {number} SITE_ID    站点ID
     * @apiSuccess {string} KEYWORD    关键字
     * @apiSuccess {string} URL    链接地址
     * @apiSuccess {number} SORTID    排序ID（越大越靠前）
     * @apiSuccess {number} CREATE_USER_ID    创建人ID
     * @apiSuccess {string} CREATE_DATE    创建时间
     * @apiSuccess {string} STATE    状态1（停用）2（启用）
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 新增热门关键字信息
     * 
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addProductRedkeyword(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = productPopularKeyWordsService.add(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    /**
     * @api{post} /{oms_server}/product_popular_key_words/edit 热门关键字修改
     * @apiGroup edit
     * @apiName edit
     * @apiDescription  热门关键字修改
     * @apiVersion 0.0.1
     * @apiSuccess {number} ID    主键ID
     * @apiSuccess {number} SITE_ID    站点ID
     * @apiSuccess {string} KEYWORD    关键字
     * @apiSuccess {string} URL    链接地址
     * @apiSuccess {number} SORTID    排序ID（越大越靠前）
     * @apiSuccess {number} CREATE_USER_ID    创建人ID
     * @apiSuccess {string} CREATE_DATE    创建时间
     * @apiSuccess {string} STATE    状态1（停用）2（启用）
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 修改热门关键字信息
     * 
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editProductRedkeyword(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = productPopularKeyWordsService.edit(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
    /**
     * @api{post} /{oms_server}/product_popular_key_words/remove 热门关键字删除
     * @apiGroup remove
     * @apiName remove
     * @apiDescription  热门关键字删除
     * @apiVersion 0.0.1
     * @apiParam{number} [id] 热门关键字id
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 热门关键字信息
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet delProductRedkeyword(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = productPopularKeyWordsService.remove(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    /**
     * @api{post} /{oms_server}/product_popular_key_words/sort 热门关键字排序
     * @apiGroup sort
     * @apiName sort
     * @apiDescription  热门关键字排序
     * @apiVersion 0.0.1
     * @apiParam{number} [id] 需要排序的id
     * @apiParam{number} [toId]   需要排序的id
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 热门关键字信息
     */
    @RequestMapping(value = "/sort", method = RequestMethod.POST)
    @ResponseBody
    public Packet sortProductRedkeyword(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
    	try{
    		return Transform.GetResult(productPopularKeyWordsService.sort(request));
    	} catch (Exception e) {
         pr.setState(false);
         pr.setMessage("操作失败！");
         logger.error("商品搜索关键字排序操作失败"+ e.getMessage());
         return Transform.GetResult(pr);
     }
    }
    /**
     * @api{post} /{oms_server}/product_popular_key_words/update_state 热门关键字更新状态
     * @apiGroup update_state
     * @apiName update_state
     * @apiDescription  热门关键字更新状态
     * @apiVersion 0.0.1
     * @apiParam{number} id
     * @apiParam{number} [state]   状态  1停用 2启用
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 热门关键字信息
     */
    @RequestMapping(value = "/update_state", method = RequestMethod.POST)
    @ResponseBody
    public Packet productRedkeywordUpdateState(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = productPopularKeyWordsService.updateState(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
    /**
     * @api{post} /{oms_server}/product_popular_key_words/detail 热门关键字详情
     * @apiGroup detail
     * @apiName detail
     * @apiDescription  热门关键字详情
     * @apiVersion 0.0.1
     * @apiParam{number} id
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 热门关键字信息
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet productRedkeywordDetail(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
    	return Transform.GetResult(productPopularKeyWordsService.detail(request));
    }
}
