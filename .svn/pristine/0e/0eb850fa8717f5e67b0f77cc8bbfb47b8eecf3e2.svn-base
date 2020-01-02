package com.tk.oms.decoration.control;

import com.tk.oms.decoration.service.MobileVideoService;
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
 * FileName : AppVideoControl
 * 移动视频管理相关
 * @author: zhengfy
 * @version: 1.00
 * @date: 2019/1/11
 */
@Controller
@RequestMapping("/mobile_video")
public class MobileVideoControl {

    @Resource
    private MobileVideoService mobileVideoService;

    /**
     * @api {post}/{project_name}/mobile_video/category_list 查询移动视频分类列表
     * @apiGroup mobile_video
     * @apiDescription 查询移动视频分类列表
     *
     *
     * @apiSuccess {boolean} state 接口返回结果是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object[]} obj 接口返回数据
     * @apiSuccess {number} obj.id  主键ID
     * @apiSuccess {string} obj.category_name  分类名称
     */
    @RequestMapping(value = "/category_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryCategoryList(HttpServletRequest request) {
        return Transform.GetResult(this.mobileVideoService.queryCategoryList(request));
    }

    /**
     * @api {post}/{project_name}/mobile_video/detail 查询移动视频分类详情
     * @apiGroup mobile_video
     * @apiDescription 查询移动视频分类详情
     *
     * @apiParam {number} id 分类ID
     *
     * @apiSuccess {boolean} state 接口返回结果是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object[]} obj 接口返回数据
     * @apiSuccess {string} obj.detail_json  数据详情json
     *                     (推荐方式：select_type; 1：自动，2：手动推荐)
     *                     (宝贝分类：product_type_id; 全部：0)
     *                     (排序规则：sort; 上新时间=sell_start_date,销售数量=product_count)
     *                     (所选商品：products; 货号以逗号分隔)
     *                     (展示方式：sort_type; 1:默认顺序，2：随机顺序)
     *
     * @apiSuccessExample {json} Success-response
     *
     * 情况一 --手动推荐；宝贝分类：全部；排序规则：按照上新时间排序；所选商品货号：0615001,0636001,0645001；展示方式：默认排序
     *      {
     *          "select_type":"2",
     *          "product_type_id":"0",
     *          "sort":"sell_start_date",
     *          "products":"0615001,0636001,0645001",
     *          "sort_type":"1"
     *      }
     *
     *
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDetail(HttpServletRequest request) {
        return Transform.GetResult(this.mobileVideoService.detail(request));
    }


    /**
     * @api {post}/{project_name}/mobile_video/remove 删除移动视频分类数据
     * @apiGroup mobile_video
     * @apiParam {number} id 分类ID
     *
     * @apiSuccess {boolean} state 接口返回结果是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet remove(HttpServletRequest request) {
        return Transform.GetResult(this.mobileVideoService.remove(request));
    }


    /**
     * @api {post}/{project_name}/mobile_video/save 保存移动视频分类数据
     * @apiGroup mobile_video
     * @apiDescription 保存移动视频分类数据
     *
     * @apiParam {number} id 分类ID
     * @apiParam {string} obj.category_name  分类名称
     * @apiParam {string} obj.detail_json  数据详情json， key值如下
     *                     (推荐方式：select_type; 1：自动，2：手动推荐)
     *                     (宝贝分类：product_type_id; 全部：0)
     *                     (排序规则：sort; 上新时间=sell_start_date,销售数量=product_count)
     *                     (所选商品：products; 货号以逗号分隔)
     *                     (展示方式：sort_type; 1:默认顺序，2：随机顺序)
     * @apiParamExample {json} request
     *
     * 情况一 --手动推荐；宝贝分类：全部；排序规则：按照上新时间排序；所选商品货号：0615001,0636001,0645001；展示方式：默认排序
     *      {
     *          "select_type":"2",
     *          "product_type_id":"0",
     *          "sort":"sell_start_date",
     *          "products":"0615001,0636001,0645001",
     *          "sort_type":"1"
     *      }
     *
     * @apiSuccess {boolean} state 接口返回结果是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Packet save(HttpServletRequest request) {
        return Transform.GetResult(this.mobileVideoService.save(request));
    }



    /**
     * @api {post}/{project_name}/mobile_video/sort 排序
     * @apiGroup mobile_video
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
        return Transform.GetResult(this.mobileVideoService.sort(request));
    }

}

    
    
