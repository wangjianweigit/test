package com.tk.oms.basicinfo.control;

import com.tk.oms.basicinfo.service.WaterTanBasicService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * Copyright (c), 2017, Tongku
 * FileName : WaterTanBasicControl
 * 聚水谭公司基础信息维护控制器
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/11/22 16:54
 */
@Controller
@RequestMapping("/water_tan")
public class WaterTanBasicControl {
	@Resource
	private WaterTanBasicService waterTanBasicService;
	
	 /**
      * @api{post} /{oms_server}/water_tan/list 获取聚水谭公司列表数据
      * @apiGroup water_tan_list
      * @apiName list
      * @apiDescription  获取聚水谭公司列表数据
      * @apiVersion 0.0.1
      * @apiParam {number} [pageIndex=1] 		起始页
      * @apiParam {number} [pageSize=10] 		分页大小
      * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
      * @apiSuccess {string} message 接口返回信息
      * @apiSuccess {object} obj 聚水谭公司列表数据
	  * @apiSuccess {string} obj.NAME    			公司名称
	  * @apiSuccess {string} obj.ADMIN_NAME    		管理员名
	  * @apiSuccess {string} obj.MOBILE    			联系电话/手机
	  * @apiSuccess {string} obj.PARTNERID    		合作方编号
	  * @apiSuccess {string} obj.PATRNERKEY    		接入密钥
	  * @apiSuccess {string} obj.TOKEN    			店铺授权码
	  * @apiSuccess {string} obj.TAOBAO_APPKEY    	淘宝APPKEY
	  * @apiSuccess {string} obj.TAOBAO_APPSECRET    淘宝APPSECRET
	  * @apiSuccess {string} obj.STATE    			启用状态：1、启用；2、禁用
      * @apiSuccess {number} total
	  */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryWaterTanList(HttpServletRequest request) {
        return Transform.GetResult(waterTanBasicService.queryWaterTanList(request));
    }

    /**@api{post} /{oms_server}/water_tan/detail 获取聚水谭公司明细
      * @apiGroup water_tan_detail
      * @apiName detail
      * @apiDescription 获取聚水谭公司明细
      * @apiSuccess {number} ID
	  * @apiSuccess {string} NAME    			公司名称
	  * @apiSuccess {string} ADMIN_NAME    		管理员名
	  * @apiSuccess {string} MOBILE    			联系电话/手机
	  * @apiSuccess {string} PARTNERID    		合作方编号
	  * @apiSuccess {string} PATRNERKEY    		接入密钥
	  * @apiSuccess {string} TOKEN    			店铺授权码
	  * @apiSuccess {string} TAOBAO_APPKEY    	淘宝APPKEY
	  * @apiSuccess {string} TAOBAO_APPSECRET    淘宝APPSECRET
	  * @apiSuccess {string} STATE    			启用状态：1、启用；2、禁用
	  * @apiSuccess {string} message 接口返回信息
      * @apiSuccess {object} obj 聚水谭公司明细
      * @apiSuccess {number} total 总条数
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryWaterTanDetail(HttpServletRequest request) {
        return Transform.GetResult(waterTanBasicService.queryWaterTanDetail(request));
    }

	/**@api{post} /{oms_server}/sys_update_msg/edit 编辑聚水谭公司明细
	 * @apiGroup water_tan_edit
	 * @apiName edit
	 * @apiDescription  编辑聚水谭公司明细
	 * @apiParam {string} NAME    			公司名称
	 * @apiParam {string} ADMIN_NAME    	管理员名
	 * @apiParam {string} MOBILE    		联系电话/手机
	 * @apiParam {string} PARTNERID    		合作方编号
	 * @apiParam {string} PATRNERKEY    	接入密钥
	 * @apiParam {string} TOKEN    			店铺授权码
	 * @apiParam {string} TAOBAO_APPKEY    	淘宝APPKEY
	 * @apiParam {string} TAOBAO_APPSECRET  淘宝APPSECRET
	 * @apiSuccess {string} STATE    		启用状态：1、启用；2、禁用
	 * @apiSuccess {object} obj
	 * @apiSuccess {number} total 总条数
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Packet addWaterTanDetail(HttpServletRequest request) {
		return Transform.GetResult(waterTanBasicService.addWaterTanDetail(request));
	}

    /**@api{post} /{oms_server}/sys_update_msg/edit 编辑聚水谭公司明细
     * @apiGroup water_tan_edit
     * @apiName edit
     * @apiDescription  编辑聚水谭公司明细
     * @apiSuccess {number} ID				编号
	 * @apiParam {string} NAME    			公司名称
	 * @apiParam {string} ADMIN_NAME    	管理员名
	 * @apiParam {string} MOBILE    		联系电话/手机
	 * @apiParam {string} PARTNERID    		合作方编号
	 * @apiParam {string} PATRNERKEY    	接入密钥
	 * @apiParam {string} TOKEN    			店铺授权码
	 * @apiParam {string} TAOBAO_APPKEY    	淘宝APPKEY
	 * @apiParam {string} TAOBAO_APPSECRET  淘宝APPSECRET
     * @apiSuccess {object} obj
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editWaterTanDetail(HttpServletRequest request) {
		return Transform.GetResult(waterTanBasicService.editWaterTanDetail(request));
    }

    /**@api{post} /{oms_server}/water_tan/update_state 更改聚水谭公司启用状态
     * @apiGroup water_tan_update_state
     * @apiName update_state
     * @apiDescription  更改聚水谭公司启用状态
     * @apiParam {number} id
	 * @apiParam {string} state    消息状态 1（启用）2（停用）
	 * @apiSuccess {string} message 接口返回信息
     * @apiSuccess {object} obj
     */
    @RequestMapping(value = "/update_state", method = RequestMethod.POST)
    @ResponseBody
    public Packet updateWaterTanDetailState(HttpServletRequest request) {
		return Transform.GetResult(waterTanBasicService.updateWaterTanDetailState(request));
    }


	/**
	 * @api{post} /{oms_server}/water_tan/member_store_list 获取聚水谭会员店铺列表数据
	 * @apiGroup water_tan_member_store_list
	 * @apiName list
	 * @apiDescription  获取聚水谭会员店铺列表数据
	 * @apiVersion 0.0.1
	 * @apiParam {number} [pageIndex=1] 		起始页
	 * @apiParam {number} [pageSize=10] 		分页大小
	 * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string} message 接口返回信息
	 * @apiSuccess {object} obj 聚水谭会员店铺列表数据
	 * @apiSuccess {string} obj.ID					编号
	 * @apiSuccess {string} obj.SHOP_ID    			店铺ID
	 * @apiSuccess {string} obj.TYPE    		 	店铺类型：1、托管；2、对接
	 * @apiSuccess {string} obj.SYNC_TYPE    		同步模式：1、同步所有；2、同步关联订单
	 * @apiSuccess {string} obj.STATE    		 	授权状态：1、未授权；2、已授权
	 * @apiSuccess {string} obj.USER_ID    		 	所属会员
	 * @apiSuccess {string} obj.COMPANY_ID    		所属聚水潭公司
	 * @apiSuccess {string} obj.CREATER_ID    		创建人ID
	 * @apiSuccess {string} obj.STOCK_SYNC_STATE	库存同步启用状态：1、未启用；2、启用
	 * @apiSuccess {number} total
	 */
	@RequestMapping(value = "/member_store_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryWaterTanMemberStoreList(HttpServletRequest request) {
		return Transform.GetResult(waterTanBasicService.queryWaterTanMemberStoreList(request));
	}

	/**@api{post} /{oms_server}/water_tan/member_store_detail 获取聚水谭会员店铺明细
	 * @apiGroup water_tan_member_store_detail
	 * @apiName detail
	 * @apiDescription 获取聚水谭会员店铺明细
	 * @apiSuccess {number} ID
	 * @apiSuccess {string} obj.ID					编号
	 * @apiSuccess {string} obj.SHOP_ID    			店铺ID
	 * @apiSuccess {string} obj.TYPE    		 	店铺类型：1、托管；2、对接
	 * @apiSuccess {string} obj.SYNC_TYPE    		同步模式：1、同步所有；2、同步关联订单
	 * @apiSuccess {string} obj.STATE    		 	授权状态：1、未授权；2、已授权
	 * @apiSuccess {string} obj.USER_ID    		 	所属会员
	 * @apiSuccess {string} obj.COMPANY_ID    		所属聚水潭公司
	 * @apiSuccess {string} obj.CREATER_ID    		创建人ID
	 * @apiSuccess {string} message 接口返回信息
	 * @apiSuccess {object} obj 聚水谭会员店铺明细
	 * @apiSuccess {number} total 总条数
	 */
	@RequestMapping(value = "/member_store_detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryWaterTanMemberStoreDetail(HttpServletRequest request) {
		return Transform.GetResult(waterTanBasicService.queryWaterTanMemberStoreDetail(request));
	}


	/**@api{post} /{oms_server}/water_tan/member_store_auth_cancel 取消会员店铺授权
	 * @apiGroup water_tan_member_store_auth_cancel
	 * @apiName edit
	 * @apiDescription  取消会员店铺授权
	 * @apiParam {number} id    			编号
	 * @apiSuccess {object} obj
	 */
	@RequestMapping(value = "/member_store_auth_cancel", method = RequestMethod.POST)
	@ResponseBody
	public Packet authCancel(HttpServletRequest request) {
		return Transform.GetResult(waterTanBasicService.authCancel(request));
	}

	/**@api{post} /{oms_server}/water_tan/member_store_add 编辑聚水谭会员店铺明细
	 * @apiGroup water_tan_member_store_add
	 * @apiName edit
	 * @apiDescription  编辑聚水谭会员店铺明细
	 * @apiParam {string} SHOP_ID    			店铺ID
	 * @apiParam {string} TYPE    				店铺类型：1、托管；2、对接
	 * @apiParam {string} SYNC_TYPE    			同步模式：1、同步所有；2、同步关联订单
	 * @apiParam {string} STATE    				授权状态：1、未授权；2、已授权
	 * @apiParam {string} USER_ID    			所属会员
	 * @apiParam {string} COMPANY_ID    		所属聚水潭公司
	 * @apiSuccess {object} obj
	 * @apiSuccess {number} total 总条数
	 */
	@RequestMapping(value = "/member_store_add", method = RequestMethod.POST)
	@ResponseBody
	public Packet addWaterTanMemberStoreDetail(HttpServletRequest request) {
		return Transform.GetResult(waterTanBasicService.addWaterTanMemberStoreDetail(request));
	}

	/**@api{post} /{oms_server}/water_tan/member_store_edit 编辑聚水谭会员店铺明细
	 * @apiGroup water_tan_member_store_edit
	 * @apiName edit
	 * @apiDescription  编辑聚水谭会员店铺明细
	 * @apiSuccess {number} ID					编号
	 * @apiParam {string} SHOP_ID    			店铺ID
	 * @apiParam {string} TYPE    				店铺类型：1、托管；2、对接
	 * @apiParam {string} SYNC_TYPE    			同步模式：1、同步所有；2、同步关联订单
	 * @apiParam {string} STATE    				授权状态：1、未授权；2、已授权
	 * @apiParam {string} USER_ID    			所属会员
	 * @apiParam {string} COMPANY_ID    		所属聚水潭公司
	 * @apiParam {string} STOCK_SYNC_STATE		库存同步启用状态：1、未启用；2、启用
	 * @apiSuccess {object} obj
	 */
	@RequestMapping(value = "/member_store_edit", method = RequestMethod.POST)
	@ResponseBody
	public Packet editWaterTanMemberStoreDetail(HttpServletRequest request) {
		return Transform.GetResult(waterTanBasicService.editWaterTanMemberStoreDetail(request));
	}

	/**@api{post} /{oms_server}/water_tan/member_store_delete 聚水谭会员店铺解除授权
	 * @apiGroup water_tan_member_store_delete
	 * @apiName
	 * @apiDescription  聚水谭会员店铺解除授权
	 * @apiParam {number} id    			会员店铺绑定id
	 * @apiSuccess {object} obj
	 */
	@RequestMapping(value = "/member_store_delete", method = RequestMethod.POST)
	@ResponseBody
	public Packet removeWaterTanMemberStore(HttpServletRequest request) {
		return Transform.GetResult(waterTanBasicService.removeWaterTanMemberStore(request));
	}

	/**
	 * 聚水谭公司下拉框
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/company_option", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryWaterTanCompanyOption(HttpServletRequest request) {
		return Transform.GetResult(this.waterTanBasicService.queryWaterTanCompanyOption(request));
	}

	/**
	 * 聚水谭店铺列表
	 *
	 * @api{post} /{oms_server}/water_tan/saas_shop 聚水谭店铺列表
	 * @apiGroup water_tan
	 * @apiName water_tan_saas_shop
	 * @apiDescription  聚水谭店铺列表
	 *
	 * @apiParam {number} company_id 	聚水潭公司ID 必填
	 * @apiParam {string} shop_name    	店铺名称
	 * @apiParam {string} state    	    授权状态
	 *
	 * @apiSuccess {string} message 接口返回信息
	 * @apiSuccess {object} obj		返回结果
	 * @apiSuccess {number} obj.SHOP_ID			店铺编号
	 * @apiSuccess {number} obj.SHOP_NAME		店铺名称
	 * @apiSuccess {number} obj.SHOP_SITE		店铺站点
	 * @apiSuccess {number} obj.NICK			店铺昵称
	 * @apiSuccess {number} obj.SESSION_EXPIRED		店铺授权过期时间
	 * @apiSuccess {number} obj.CREATED				店铺创建时间
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saas_shop", method = RequestMethod.POST)
	@ResponseBody
	public Packet getSaasShopList(HttpServletRequest request) {
		return Transform.GetResult(this.waterTanBasicService.getSaasShopList(request));
	}

}
