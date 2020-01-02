package com.tk.oms.product.control;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.product.service.ProductBrandService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;


@Controller
@RequestMapping("/product_brand")
public class ProductBrandControl {
	
	private Log logger = LogFactory.getLog(this.getClass());
	
	@Resource
    private ProductBrandService productBrandService;
	
	/**
     * @api{post} /{oms_server}/productBrand/product_brand_list 品牌信息查询
     * @apiGroup ProductBrand
     * @apiName product_brand_list
     * @apiDescription  分页查询品牌信息列表
     * @apiVersion 0.0.1
     * @apiParam{number} [pageIndex=1] 起始页
     * @apiParam{number} [pageSize=10] 分页大小
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 品牌信息
     * @apiSuccess{number} total 总条数
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductBrandList(HttpServletRequest request) {
        return Transform.GetResult(productBrandService.queryProductBrandList(request));
    }
    
    /**
     * @api{post} /{oms_server}/productBrand/product_brand_list 品牌信息查询(下拉框专用)
     * @apiGroup all_list
     * @apiName all_list
     * @apiDescription  品牌信息查询(下拉框专用)
     * @apiVersion 0.0.1
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 品牌信息
     */
    @RequestMapping(value = "/all_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductBrandAllList(HttpServletRequest request) {
        return Transform.GetResult(productBrandService.queryProductBrandAllList(request));
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductBrandDetail(HttpServletRequest request) {
        return Transform.GetResult(productBrandService.queryProductBrandDetail(request));
    }
    
    /**
     * @api{post} /{oms_server}/productBrand/product_brand_add 品牌新增
     * @apiGroup addProductBrand
     * @apiName product_brand_add
     * @apiDescription  新增品牌信息
     * @apiVersion 0.0.1
     * @apiParam{number} id 主键
     * @apiParam{String} brand_name 品牌名称
     * @apiParam{String} state 状态
     * @apiParam{date} create_date 创建时间
     * @apiParam{date} update_date 更新时间
     * @apiParam{number} sortid 排序ID（越大越靠前）
     * @apiParam{number} code 品牌代码
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 操作记录数
     * 
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addProductBrand(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.productBrandService.addProductBrand(request);
        }catch (Exception e){
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return Transform.GetResult(pr);
    }
    /**
     * @api{post} /{oms_server}/productBrand/product_brand_edit 品牌修改
     * @apiGroup editProductBrand
     * @apiName product_brand_edit
     * @apiDescription  修改品牌信息
     * @apiVersion 0.0.1
     * @apiParam{number} id 主键
     * @apiParam{String} brand_name 品牌名称
     * @apiParam{String} state 状态
     * @apiParam{number} sortid 排序ID（越大越靠前）
     * @apiParam{number} code 品牌代码
     * @apiParam{number} create_user_id 创建人id）
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 操作记录数
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editProductBrand(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = productBrandService.editProductBrand(request);
        }catch (Exception e){
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return Transform.GetResult(pr);
    }
    /**
     * @api{post} /{oms_server}/productBrand/product_brand_del 品牌删除
     * @apiGroup delProductBrand
     * @apiName product_brand_del
     * @apiDescription  删除品牌信息
     * @apiVersion 0.0.1
     * @apiParam{number} id 主键
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 操作记录数
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet delProductBrand(HttpServletRequest request) {
        return Transform.GetResult(productBrandService.removeProductBrand(request));
    }
    /**
     * @api{post} /{oms_server}/productBrand/product_brand_count 商品的排序（排序数字交换）
     * @apiGroup sortProductBrand
     * @apiName product_brand_sort
     * @apiDescription  商品的排序（排序数字交换）
     * @apiVersion 0.0.1
     * @apiParam{number} id 需排序品牌id
     * @apiParam{number} toId 需排序品牌id
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     */
    @RequestMapping(value = "/sort", method = RequestMethod.POST)
    @ResponseBody
    public Packet sortProductBrand(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            return Transform.GetResult(productBrandService.sortProductBrand(request));
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage("操作失败！");
            logger.error("商品基本参数排序操作失败" + e.getMessage());
            return Transform.GetResult(pr);
        }
    }

    /**
     * @api{post} /{oms_server}/productBrand/product_brand_edit 品牌状态修改
     * @apiGroup editProductBrand
     * @apiName product_brand_edit
     * @apiDescription  修改品牌信息
     * @apiVersion 0.0.1
     * @apiParam{number} id 主键
     * @apiParam{String} brand_name 品牌名称
     * @apiParam{String} state 状态
     */
    @RequestMapping(value = "/state", method = RequestMethod.POST)
    @ResponseBody
    public Packet stateProductBrand(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            return Transform.GetResult(productBrandService.stateProductBrand(request));
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage("操作失败！");
            return Transform.GetResult(pr);
        }
    }
    
    /**
     * @api{post} /{oms_server}/productBrand/product_brand_show 品牌展示状态修改
     * @apiGroup editProductBrand
     * @apiName product_brand_show
     * @apiDescription  修改品牌展示信息
     * @apiVersion 0.0.1
     * @apiParam{number} id 主键
     * @apiParam{String} brand_name 品牌名称
     * @apiParam{String} state 状态
     */
    @RequestMapping(value = "/show", method = RequestMethod.POST)
    @ResponseBody
    public Packet showProductBrand(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
    	try {
			return Transform.GetResult(productBrandService.showProductBrand(request));
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage("操作失败！");
			return Transform.GetResult(pr);
		}
    }
    
    /**
     * @api{post} /{oms_server}/product_brand/info_list 品牌信息列表
     * @apiGroup info_list
     * @apiName product_brand_info_list
     * @apiDescription  品牌信息列表
     * @apiVersion 0.0.1
     * 
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 品牌信息
     * 
     */
    @RequestMapping(value = "/info_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductBrandInfoList(HttpServletRequest request) {
        return Transform.GetResult(productBrandService.queryProductBrandInfoList(request));
    }

    /**
     * @api{post} /{oms_server}/product_brand/check 验证品牌分类是否占用
     * @apiGroup product_brand
     * @apiName product_brand_check
     * @apiDescription  验证品牌分类是否已被商品使用
     * @apiVersion 0.0.1
     *
     * @apiParam {number} brand_id 商品品牌id
     * @apiParam {number} classify_id 分类id
     *
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     *
     */
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    @ResponseBody
    public Packet checkProductBrand(HttpServletRequest request) {
        return Transform.GetResult(productBrandService.checkProductBrand(request));
    }

    /**
     * 查询已开通店铺的入驻商列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/shop_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryOpenShopStationedList(HttpServletRequest request) {
        return Transform.GetResult(productBrandService.queryOpenShopStationedList(request));
    }

}
