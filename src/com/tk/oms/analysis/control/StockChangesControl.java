package com.tk.oms.analysis.control;

import com.tk.oms.analysis.service.StockChangesService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (c), 2017, Tongku
 * FileName : StockChangesControl
 * 库存异动参考接口
 *
 * @author zhenghui
 * @version 1.00
 * @date 2017/7/19
 */
@Controller
@RequestMapping("/stock_changes")
public class StockChangesControl {

    @Resource
    private StockChangesService stockChangesService;

    /**
     * @api {post} /{project_name}/stock_changes/list 库存异动参考列表
     * @apiName stock_changes_list
     * @apiGroup stock_changes
     * @apiDescription  分页获取库存异动参考列表数据
     * @apiVersion 0.0.1
     *
     * @apiParam {number}   warehouse_id 	    仓库ID
     * @apiParam {string}   product_itemnumber 	商品货号
     * @apiParam {number}   site_id 	        站点ID
     * @apiParam {number}   activity_id 	    活动ID
     * @apiParam {number}   season_id 	        季节ID
     * @apiParam {number}   year 	            年份
     * @apiParam {number}   brand_id 	        商品品牌ID
     * @apiParam {number}   mark_id 	        标记ID
     * @apiParam {number}   is_repair 	        是否不但
     * @apiParam {date}   start_date 	        首次入库开始时间
     * @apiParam {date}   end_date 	            首次入库结束时间
     *
     * @apiSuccess {boolean}    state                   接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message                 接口返回信息
     * @apiSuccess {object[]}   obj 	                库存异动参考列表
     * @apiSuccess {string}   obj.product_itemnumber 	    商品货号
     * @apiSuccess {string}   obj.product_color 	        商品颜色
     * @apiSuccess {string}   obj.product_specs 	        商品规格（如：21-25）
     * @apiSuccess {string}   obj.product_img_url 	        商品主图
     * @apiSuccess {number}   obj.warehouse_total_count 	库存总数量
     * @apiSuccess {number}   obj.stock_count 	            库存数量（尺码-库存数.如：21-1）
     * @apiSuccess {number}   obj.purchase_num 	            未出入库数量
     * @apiSuccess {number}   obj.activity_count 	        活动数量
     * @apiSuccess {string}   obj.mark_img_url 	            标记图片
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryStockChangesListForPage(HttpServletRequest request) {
        return Transform.GetResult(this.stockChangesService.queryStockChangesListForPage(request));
    }

    /**
     * @api {post} /{project_name}/stock_changes/activity_list  获取活动列表
     * @apiName  activity_list
     * @apiGroup stock_changes
     * @apiDescription  获取活动列表
     * @apiVersion 0.0.1
     *
     * @apiParam {string} product_itemnumber 	        商品货号
     *
     * @apiSuccess {boolean}    state                   接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message                 接口返回信息
     * @apiSuccess {object[]}   obj 	                活动列表
     * @apiSuccess {string}     obj.activity_name 	    活动名称
     * @apiSuccess {string}     obj.site_name 	        站点名称
     * @apiSuccess {date}       obj.begin_date 	        活动开始时间
     * @apiSuccess {date}       obj.end_time 	        活动结束时间
     */
    @RequestMapping(value = "/activity_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryActivityList(HttpServletRequest request) {
        return Transform.GetResult(this.stockChangesService.queryActivityList(request));
    }

    /**
     * @api {post} /{project_name}/stock_changes/detail_list 库存异动参考详情
     * @apiName stock_changes_detail_list
     * @apiGroup stock_changes
     * @apiDescription  查询库存异动参考详情
     * @apiVersion 0.0.1
     *
     * @apiParam {string}   product_itemnumber 	    商品货号
     * @apiParam {string}   product_color 	        商品颜色
     * @apiParam {string}   product_specs 	        商品规格（如：21-25）
     *
     * @apiSuccess {boolean}    state                       接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message                     接口返回信息
     * @apiSuccess {object[]}   obj 	                    库存异动参考详情列表
     * @apiSuccess {number}     obj.warehouse_total_count 	库存总数量
     * @apiSuccess {number}     obj.stock_count 	        库存数量（尺码-库存数.如：21-1）
     * @apiSuccess {number}     obj.warehouse_name 	        仓库名称
     */
    @RequestMapping(value = "/detail_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryStockChangesDetailList(HttpServletRequest request) {
        return Transform.GetResult(this.stockChangesService.queryStockChangesDetailList(request));
    }

    /**
     * @api {post} /{project_name}/stock_changes/warehouse_list 获取仓库列表
     * @apiName stock_changes_warehouse_list
     * @apiGroup stock_changes
     * @apiDescription  获取仓库列表（大仓）
     * @apiVersion 0.0.1
     *
     * @apiSuccess {boolean}    state               接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message             接口返回信息
     * @apiSuccess {object[]}   obj 	            库存异动参考详情列表
     * @apiSuccess {number}     obj.id 	            仓库ID
     * @apiSuccess {string}     obj.option 	        仓库名称
     */
    @RequestMapping(value = "/warehouse_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryWarehouseList(HttpServletRequest request) {
        return Transform.GetResult(this.stockChangesService.queryWarehouseList(request));
    }

    /**
     * @api {post} /{project_name}/stock_changes/mark 库存异动标记
     * @apiName stock_changes_mark
     * @apiGroup stock_changes
     * @apiDescription  库存异动标记
     * @apiVersion 0.0.1
     *
     * @apiParam {string}   product_itemnumber 	    商品货号
     * @apiParam {string}   product_color 	        商品颜色
     * @apiParam {string}   product_specs 	        商品规格（如：21-25）
     * @apiParam {number}   mark_id 	            标记ID
     * @apiParam {string}   remark 	                备注
     *
     * @apiSuccess {boolean}    state                       接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message                     接口返回信息
     */
    @RequestMapping(value = "/mark", method = RequestMethod.POST)
    @ResponseBody
    public Packet stockChangesMark(HttpServletRequest request) {
        return Transform.GetResult(this.stockChangesService.stockChangesMark(request));
    }

    /**
     * @api {post} /{project_name}/stock_changes/mark_remove 删除库存异动标记
     * @apiName stock_changes_mark_remove
     * @apiGroup stock_changes
     * @apiDescription  删除库存异动标记
     * @apiVersion 0.0.1
     *
     * @apiParam {string}   product_itemnumber 	    商品货号
     * @apiParam {string}   product_color 	        商品颜色
     * @apiParam {string}   product_specs 	        商品规格（如：21-25）
     *
     * @apiSuccess {boolean}    state               接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message             接口返回信息
     */
    @RequestMapping(value = "/mark_remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeStockChangesMark(HttpServletRequest request) {
        return Transform.GetResult(this.stockChangesService.removeStockChangesMark(request));
    }
}
