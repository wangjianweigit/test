package com.tk.oms.platform.control;

import com.tk.oms.platform.service.DecorateCommonService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (c), 2018, TongKu
 * FileName : DecorateCommonControl
 * 装修通用接口访问层
 *
 * @author zhenghui
 * @version 1.00
 * @date 2018/09/12
 */
@Controller
@RequestMapping("/decorate_common")
public class DecorateCommonControl {

    @Resource
    private DecorateCommonService decorateCommonService;

    /**
     * @api {post}/{project_name}/decorate_common/product_list 商品列表
     * @apiGroup decorate_common
     * @apiDescription 分页查询固定模块选择商品列表
     *
     * @apiParam {number} pageIndex 当前页数
     * @apiParam {number} pageSize 分页大小
     * @apiParam {number} site_id 站点ID
     * @apiParam {string} [sort] 排序字段 <p>sell_state_date:上架时间</p><p>product_count:商品销量</p>
     * @apiParam {number} [product_type_id] 分类ID
     * @apiParam {string} [keyword] 商品货号或者商品名称
     * @apiParam {number} [show_video] 显示视频商品
     * @apiParam {number} [show_custom] 展示定制商品
     * @apiParam {number} [show_presell] 展示预售商品
     *
     * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object[]} obj 接口返回数据
     * @apiSuccess {object[]} obj.product_itemnumber 商品货号
     * @apiSuccess {object[]} obj.product_name 商品名称
     * @apiSuccess {object[]} obj.product_img_url 商品图片
     * @apiSuccess {object[]} obj.sale_price_min 商品价格区间
     */
    @RequestMapping(value = "/product_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySelectProductListForPage(HttpServletRequest request) {
        return Transform.GetResult(this.decorateCommonService.querySelectProductListForPage(request));
    }

    /**
     * @api {post}/{project_name}/decorate_common/select_product 选中商品列表
     * @apiGroup decorate_common
     * @apiDescription 查询固定模块选中商品列表
     *
     * @apiParam {object[]} [itemnumbers] 商品货号数组
     * @apiParam {number} site_id 站点ID
     *
     * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object[]} obj 接口返回数据
     * @apiSuccess {object[]} obj.product_itemnumber 商品货号
     * @apiSuccess {object[]} obj.product_name 商品名称
     * @apiSuccess {object[]} obj.product_img_url 商品图片
     * @apiSuccess {object[]} obj.sale_price_min 商品价格区间
     */
    @RequestMapping(value = "/select_product", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySelectProductList(HttpServletRequest request) {
        return Transform.GetResult(this.decorateCommonService.querySelectProductList(request));
    }

    /**
     * @api {post}/{project_name}/decorate_common/group_list 商品分类列表
     * @apiGroup decorate_common
     * @apiDescription 查询商品分类列表
     *
     * @apiParam {number} [parent_id] 父级ID
     *
     * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object[]} obj 接口返回数据
     * @apiSuccess {number} obj.id 分组ID
     * @apiSuccess {number} obj.parent_id 父级分组ID
     * @apiSuccess {string} obj.name 分组名称
     */
    @RequestMapping(value = "/group_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductGroupList(HttpServletRequest request) {
        return Transform.GetResult(this.decorateCommonService.queryProductGroupList(request));
    }

    /**
     * @api {post}/{project_name}/decorate_common/activity_list 查询活动列表
     * @apiGroup decorate_common
     * @apiDescription 查询活动列表
     *
     * @apiParam {number} site_id 站点ID
     * @apiParam {string} [not_show_sale] 不展示限时折扣活动，1.不展示，不传则展示
     * @apiParam {string} [not_show_presell] 不展示预售活动，1.不展示，不传则展示
     * @apiParam {string} [not_show_order] 不展示订货会活动，1.不展示，不传则展示
     *
     * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object[]} obj 接口返回数据
     * @apiSuccess {number} obj.id 活动ID
     * @apiSuccess {string} obj.name 活动名称
     */
    @RequestMapping(value = "/activity_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryActivityList(HttpServletRequest request) {
        return Transform.GetResult(this.decorateCommonService.queryActivityList(request));
    }

    /**
     * @api {post}/{project_name}/decorate_common/page_list 页面列表
     * @apiGroup decorate_common
     * @apiDescription 查询装修页面列表，包含系统页，自定义页，商品列表页
     *
     * @apiParam {number} template_id 装修模板ID
     * @apiParam {number} site_id 站点ID
     *
     * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object[]} obj 接口返回数据
     * @apiSuccess {number} obj.id 页面ID
     * @apiSuccess {string} obj.page_name 页面名称
     * @apiSuccess {number} obj.home_page_flag 页面类型 <p>1.自定义页</p> <p>2.首页</p> <p>3.活动商品列表页</p>
     * @apiSuccess {number} obj.page_type 页面类型<p> 1.自定义页</p> <p>2.系统首页</p>
     */
    @RequestMapping(value = "/page_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDecoratePageList(HttpServletRequest request) {
        return Transform.GetResult(this.decorateCommonService.queryDecoratePageList(request));
    }

    /**
     * @api {post}/{project_name}/decorate_common/season 查询商品季节
     * @apiGroup decorate_common
     * @apiDescription 查询商品季节
     *
     * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object[]} obj 接口返回数据
     * @apiSuccess {number} obj.id 季节ID
     * @apiSuccess {string} obj.name 季节名称
     */
    @RequestMapping(value = "/season", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductSeasonList(HttpServletRequest request) {
        return Transform.GetResult(this.decorateCommonService.queryProductSeasonList(request));
    }

    /**
     * @api {post}/{project_name}/decorate_common/brand 查询商品品牌
     * @apiGroup decorate_common
     * @apiDescription 查询商品品牌
     *
     * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object[]} obj 接口返回数据
     * @apiSuccess {number} obj.id 品牌ID
     * @apiSuccess {string} obj.name 品牌名称
     */
    @RequestMapping(value = "/brand", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductBrandList(HttpServletRequest request) {
        return Transform.GetResult(this.decorateCommonService.queryProductBrandList(request));
    }

    /**
     * @api {post}/{project_name}/decorate_common/page_brand 分页查询商品品牌
     * @apiGroup decorate_common
     * @apiDescription 分页查询商品品牌列表数据
     *
     * @apiParam {number} pageIndex 起始页
     * @apiParam {number} pageSize 分页大小
     * @apiParam {string} [keyword] 商品品牌名称
     *
     * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object[]} obj 接口返回数据
     * @apiSuccess {number} obj.id 品牌ID
     * @apiSuccess {string} obj.name 品牌名称
     * @apiSuccess {string} obj.code 品牌代码
     * @apiSuccess {string} obj.logo 品牌LOGO
     */
    @RequestMapping(value = "/page_brand", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductBrandListForPage(HttpServletRequest request) {
        return Transform.GetResult(this.decorateCommonService.queryProductBrandListForPage(request));
    }

    /**
     * @api {post}/{project_name}/decorate_common/select_brand 选中品牌列表
     * @apiGroup decorate_common
     * @apiDescription 查询选中品牌列表
     *
     * @apiParam {object[]} brand_ids 品牌ID数组
     *
     * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object[]} obj 接口返回数据
     * @apiSuccess {number} obj.id 品牌ID
     * @apiSuccess {string} obj.name 品牌名称
     * @apiSuccess {string} obj.code 品牌代码
     * @apiSuccess {string} obj.logo 品牌LOGO
     */
    @RequestMapping(value = "/select_brand", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySelectBrandList(HttpServletRequest request) {
        return Transform.GetResult(this.decorateCommonService.querySelectBrandList(request));
    }

    /**
     * @api {post}/{project_name}/decorate_common/spec 查询商品规格
     * @apiGroup decorate_common
     * @apiDescription 查询商品规格
     *
     * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object[]} obj 接口返回数据
     * @apiSuccess {number} obj.id 商品规格ID
     * @apiSuccess {string} obj.name 商品规格名称
     */
    @RequestMapping(value = "/spec", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductSpecList(HttpServletRequest request) {
        return Transform.GetResult(this.decorateCommonService.queryProductSpecList(request));
    }

    /**
     * @api {post}/{project_name}/decorate_common/warehouse 查询仓库列表
     * @apiGroup decorate_common
     * @apiDescription 查询仓库列表
     *
     * @apiParam {number} site_id 站点ID
     *
     * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object[]} obj 接口返回数据
     * @apiSuccess {number} obj.id 仓库ID
     * @apiSuccess {string} obj.name 仓库名称
     */
    @RequestMapping(value = "/warehouse", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryWarehouseList(HttpServletRequest request) {
        return Transform.GetResult(this.decorateCommonService.queryWarehouseList(request));
    }

    /**
     * @api {post}/{project_name}/decorate_common/all_product_list 商品列表
     * @apiGroup decorate_common
     * @apiDescription 分页查询固定模块选择商品列表
     *
     * @apiParam {number} pageIndex 当前页数
     * @apiParam {number} pageSize 分页大小
     * @apiParam {string} [sort] 排序字段 <p>sell_state_date:上架时间</p><p>product_count:商品销量</p>
     * @apiParam {number} [product_type_id] 分类ID
     * @apiParam {string} [keyword] 商品货号或者商品名称
     * @apiParam {number} [show_video] 显示视频商品
     *
     * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object[]} obj 接口返回数据
     * @apiSuccess {object[]} obj.product_itemnumber 商品货号
     * @apiSuccess {object[]} obj.product_name 商品名称
     * @apiSuccess {object[]} obj.product_img_url 商品图片
     * @apiSuccess {object[]} obj.sale_price_min 商品价格区间
     */
    @RequestMapping(value = "/all_product_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAllProductListForPage(HttpServletRequest request) {
        return Transform.GetResult(this.decorateCommonService.queryAllProductListForPage(request));
    }
}
