package com.tk.pvtp.product.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.pvtp.product.service.PvtpProductService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
@Controller
@RequestMapping("pvtp_product")
public class PvtpProductControl {
	@Resource
	private PvtpProductService pvtpProductService;
	/**
    *
    * @api {post} /{project_name}/pvtp/product/list 商品列表[已审批]
    * @apiGroup product
    * @apiDescription  入驻商品发布成功的商品列表
    * @apiVersion 0.0.1

	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条   

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 商品列表
    * @apiSuccess {number} obj.ID			主键ID
    * @apiSuccess {number} obj.STATIONED_USER_ID 入驻商ID
	* @apiSuccess {number} obj.BRAND_ID		品牌ID
	* @apiSuccess {number} obj.PRODUCT_TYPE_ID			商品类别ID  
	* @apiSuccess {string} obj.ITEMNUMBER			货号   
	* @apiSuccess {string} obj.PRODUCT_NAME		商品名称,不允许重名      
	* @apiSuccess {number} obj.YEAR		年份
	* @apiSuccess {string} obj.SEASON	季节
	* @apiSuccess {string} obj.SEX			性别（男童、女童、中性）
	* @apiSuccess {string} obj.CREATE_DATE			创建日期
	* @apiSuccess {number} obj.CREATE_USER_ID		创建人
	* @apiSuccess {string} obj.UPDATE_DATE			编辑日期
	* @apiSuccess {number} obj.UPDATE_USER_ID		编辑人ID
	* @apiSuccess {string} obj.APPROVAL_DATE		审批日期
	* @apiSuccess {number} obj.APPROVAL_USER_ID		审批人ID
	* @apiSuccess {string} obj.UNIT			计量单位
	* @apiSuccess {string} obj.PRODUCT_IMG_URL		商品主图路径
	* @apiSuccess {string} obj.PRODUCT_STATE		商品状态
	* @apiSuccess {number} obj.IS_OUTSTOCK			是否支持缺货订购（0：不支持，1：支持）
	* @apiSuccess {number} obj.IS_OUTSTOCK_DAY		备货周期
	* @apiSuccess {string} obj.PRODUCT_CONTENT		商品详情
	* @apiSuccess {string} obj.LAST_UP_DATE		上新日期
	* @apiSuccess {number} obj.UP_PERIOD		上新周期（默认5天）
	* @apiSuccess {number} obj.DISTRICT_TEMPLET_ID 区域模板ID
    */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryProductList(HttpServletRequest request) {
		return Transform.GetResult(pvtpProductService.queryProductList(request));
	}
	
	/**
    *
    * @api {post} /{project_name}/pvtp/product/detail 待审批商品详情
    * @apiGroup product
    * @apiDescription  入驻商发布的待审批详情
    * @apiVersion 0.0.1

	* @apiParam {string} itemnumber				商品货号 

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object} obj 商品详情
    * @apiSuccess {object} obj.base_info 商品基本信息
    * @apiSuccess {number} obj.base_info.ID			主键ID
    * @apiSuccess {number} obj.base_info.STATIONED_USER_ID 入驻商ID
	* @apiSuccess {number} obj.base_info.BRAND_ID		品牌ID
	* @apiSuccess {number} obj.base_info.PRODUCT_TYPE_ID			商品类别ID  
	* @apiSuccess {string} obj.base_info.ITEMNUMBER			货号   
	* @apiSuccess {string} obj.base_info.PRODUCT_NAME		商品名称,不允许重名      
	* @apiSuccess {number} obj.base_info.YEAR		年份
	* @apiSuccess {string} obj.base_info.SEASON	季节
	* @apiSuccess {string} obj.base_info.SEX			性别（男童、女童、中性）
	* @apiSuccess {string} obj.base_info.CREATE_DATE			创建日期
	* @apiSuccess {number} obj.base_info.CREATE_USER_ID		创建人
	* @apiSuccess {string} obj.base_info.UPDATE_DATE			编辑日期
	* @apiSuccess {number} obj.base_info.UPDATE_USER_ID		编辑人ID
	* @apiSuccess {string} obj.base_info.APPROVAL_DATE		审批日期
	* @apiSuccess {number} obj.base_info.APPROVAL_USER_ID		审批人ID
	* @apiSuccess {string} obj.base_info.UNIT			计量单位
	* @apiSuccess {string} obj.base_info.PRODUCT_IMG_URL		商品主图路径
	* @apiSuccess {string} obj.base_info.PRODUCT_STATE		商品状态
	* @apiSuccess {number} obj.base_info.IS_OUTSTOCK			是否支持缺货订购（0：不支持，1：支持）
	* @apiSuccess {number} obj.base_info.IS_OUTSTOCK_DAY		备货周期
	* @apiSuccess {string} obj.base_info.PRODUCT_CONTENT		商品详情
	* @apiSuccess {string} obj.base_info.LAST_UP_DATE		上新日期
	* @apiSuccess {number} obj.base_info.UP_PERIOD		上新周期（默认5天）
	* @apiSuccess {string} obj.base_info.STATE		状态 1（草稿）     2（待审批）    3（已审核通过）    4（已驳回）  -1(已归档历史记录)
	* @apiSuccess {number} obj.base_info.DISTRICT_TEMPLET_ID 区域模板ID
	* @apiSuccess {number} obj.base_info.ORDER_QUANTITY_TYPE 商品起订类型
																	<p>0:不限制起订量</p>
																	<p>1：整手批发：以“手”为单位，必须按“手”拿货</p>
																	<p>2：一手起批：满足最低要求一手即可，可以单个SKU数量进行调整</p>
	* 
	* @apiSuccess {object[]} obj.params_info 商品参数信息
	* @apiSuccess {number} obj.params_info.STATIONED_USER_ID 入驻商ID
	* @apiSuccess {string} obj.params_info.PRODUCT_ITEMNUMBER  商品货号 
	* @apiSuccess {number} obj.params_info.PARAMETER_ID 商品参数ID
	* @apiSuccess {string} obj.params_info.PARAMETER_VALUE 商品参数值
	* 
	* @apiSuccess {object[]} obj.skus_info 商品sku信息
	* @apiSuccess {number} obj.skus_info.ID sku主键ID
	* @apiSuccess {number} obj.skus_info.STATIONED_USER_ID  入驻商ID 
	* @apiSuccess {number} obj.skus_info.PRODUCT_ITEMNUMBER 商品货号
	* @apiSuccess {string} obj.skus_info.PRODUCT_COLOR 商品颜色
	* @apiSuccess {string} obj.skus_info.PRODUCT_SPECS 商品规格
	* @apiSuccess {string} obj.skus_info.PRODUCT_SIZE 商品尺码
	* @apiSuccess {string} obj.skus_info.PRODUCT_COLOR_IMGURL 商品颜色图片
	* @apiSuccess {number} obj.skus_info.PRODUCT_QUOTE  商品报价 
	* @apiSuccess {number} obj.skus_info.PRODUCT_PRIZE_TAG  吊牌价 
	* @apiSuccess {number} obj.skus_info.PRODUCT_PRIZE_SALE  销售价 
	* @apiSuccess {number} obj.skus_info.PRODUCT_PRIZE_COST  成本价 
	* @apiSuccess {number} obj.skus_info.PRODUCT_WARNING_COUNT  库存预警数 
	* @apiSuccess {number} obj.skus_info.PRODUCT_WEIGHT  商品重量 
	* @apiSuccess {string} obj.skus_info.PRODUCT_GBCODE 国标码
	* @apiSuccess {number} obj.skus_info.PRODUCT_INLONG  内长
	* @apiSuccess {number} obj.skus_info.PRODUCT_TOTAL_COUNT  总库存
	* @apiSuccess {number} obj.skus_info.PRODUCT_OCCUPY_COUNT  总占用量 
	* @apiSuccess {number} obj.skus_info.PRODUCT_ORDER_OCCUPY_COUNT  订单占用量 
	* @apiSuccess {string} obj.skus_info.STATE  上架状态（待上架、上架、下架）  
	* @apiSuccess {number} obj.skus_info.SORT_ID 排序，从小到大
    */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryProductDetail(HttpServletRequest request) {
		return Transform.GetResult(pvtpProductService.queryProductDetail(request));
	}
}
