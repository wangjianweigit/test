package com.tk.oms.product.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.product.service.ProductConsortiumService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * 
 * Copyright (c), 2018, Tongku
 * FileName : ProductConsortiumControl
 * 联营门店商品管理
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018-2-26
 */
@Controller
@RequestMapping("/product_consortium")
public class ProductConsortiumControl {
	@Resource
	private ProductConsortiumService productConsortiumService;
	/**
	 * 联营门店商品列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductListForPage(HttpServletRequest request) {
        return Transform.GetResult(productConsortiumService.queryProductListForPage(request));
    }
	
	/**
     * @api {post} /{project_name}/product_consortium/add 设置商品零售价
     * @apiGroup product_consortium
     * @apiName insert
     * @apiDescription  设置商品零售价
     * @apiVersion 0.0.1
     * 
     * @apiParam {object[]} list
     * @apiParam {string} list.product_itemnumber 商品货号
     * @apiParam {string} list.product_specs  商品规格
     * @apiParam {number} list.retail_price  零售价
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   total 成功数量
     * 
     */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet insertProductConsortium(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
		try {
			pr = productConsortiumService.insertProductConsortium(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
	
	/**
     * @api {post} /{project_name}/product_consortium/remove 删除
     * @apiGroup product_consortium
     * @apiName remove
     * @apiDescription  删除
     * @apiVersion 0.0.1
     * 
     * @apiParam {string} product_itemnumber 商品货号
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   total 成功数量
     * 
     */
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet deleteProductConsortium(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
		try {
			pr = productConsortiumService.deleteProductConsortium(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
	
	/**
	 * 联营门店商品详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductConsortiumDetail(HttpServletRequest request) {
        return Transform.GetResult(productConsortiumService.queryProductConsortiumDetail(request));
    }
	
}
