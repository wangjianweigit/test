package com.tk.oms.marketing.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.marketing.service.ProductControlService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : ProductControlControl
 * 控货管理
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-7-6
 */
@Controller
@RequestMapping("product_control")
public class ProductControlControl {
	@Resource
	private ProductControlService productControlService;
	
	/**
     * @api {post} /{project_name}/product_control/list 控货查询列表
     * @apiGroup list
     * @apiName product_control
     * @apiDescription  控货查询列表
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} pageIndex 开始页码
     * @apiParam {number} pageSize  每页数据量
     *
     * @apiSuccess {boolean}  state  接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}   message 接口返回信息.
     * @apiSuccess {object[]} obj     	   控货查询列表
     * @apiSuccess {string}   obj.id   	
     * @apiSuccess {number}   obj.user_id   			用户名ID
     * @apiSuccess {number}   obj.user_store_address_id 用户门店地址ID
     * @apiSuccess {string}   obj.login_name  			用户名
     * @apiSuccess {string}   obj.user_manage_name  	姓名
     * @apiSuccess {string}   obj.store_name  			门店名称
     * @apiSuccess {string}   obj.store_address  		门店地址
     * @apiSuccess {string}   obj.management_address  	经营地址
     * @apiSuccess {number}   obj.longitude  			经度
     * @apiSuccess {number}   obj.latitude  			纬度
     * @apiSuccess {string}   obj.state  				控制开关
     * @apiSuccess {string}   obj.product_control_type  控货类型
     * @apiSuccess {number}   obj.product_control_radius 控货半径
     * @apiSuccess {string}   obj.user_company_type  	经营类型
     * @apiSuccess {string}   obj.total  				控货数
     * 
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductControlList(HttpServletRequest request) {
        return Transform.GetResult(productControlService.queryProductControlList(request));
    }
    
    /**
     * @api {post} /{project_name}/product_control/state 控货开关
     * @apiGroup state
     * @apiName product_control
     * @apiDescription  控货开关
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id 	主键ID
     * @apiParam {string} state 控货开关
     *
     * @apiSuccess {boolean}  state  接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}   message 接口返回信息.
     * 
     */
    @RequestMapping(value = "/state", method = RequestMethod.POST)
    @ResponseBody
    public Packet editState(HttpServletRequest request) {
        return Transform.GetResult(productControlService.editState(request));
    }
    
    /**
     * @api {post} /{project_name}/product_control/detail 控货详情
     * @apiGroup detail
     * @apiName product_control
     * @apiDescription  控货详情
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} user_store_address_id 	用户门店地址ID
     *
     * @apiSuccess {boolean}  state  接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}   message 接口返回信息.
     * @apiSuccess {object}   obj     	  				控货详情
     * @apiSuccess {string}   obj.id   	
     * @apiSuccess {string}   obj.management_address  	经营地址
     * @apiSuccess {string}   obj.product_control_types 控货类型(中文)
     * @apiSuccess {string}   obj.product_control_type  控货类型
     * @apiSuccess {number}   obj.product_control_radius 控货半径
     * @apiSuccess {string}   obj.remark  				备注
     * 
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductControlDetail(HttpServletRequest request) {
        return Transform.GetResult(productControlService.queryProductControlDetail(request));
    }
    
    /**
     * @api {post} /{project_name}/product_control/edit 申请控货
     * @apiGroup edit
     * @apiName product_control
     * @apiDescription  申请控货
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} user_store_address_id 	用户门店地址ID
     *
     * @apiSuccess {boolean}  state  接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}   message 接口返回信息.
     * 
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editProductControl(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
		try {
			pr = productControlService.editProductControl(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    
    /**
     * @api {post} /{project_name}/product_control/apply_detail 申请控货明细
     * @apiGroup apply_detail
     * @apiName product_control
     * @apiDescription  申请控货明细
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} user_store_address_id 	用户门店地址ID
     * @apiParam {string} type		访问类型
     *
     * @apiSuccess {boolean}  state  接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}   message 接口返回信息.
     * @apiSuccess {object}   obj     	  				申请控货明细
     * @apiSuccess {string}   obj.id   	
     * @apiSuccess {string}   obj.management_address  	经营地址
     * @apiSuccess {string}   obj.product_control_types 控货类型(中文)
     * @apiSuccess {string}   obj.product_control_type  控货类型
     * @apiSuccess {number}   obj.product_control_radius 控货半径
     * @apiSuccess {string}   obj.remark  				备注
     * 
     */
    @RequestMapping(value = "/apply_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductControlApplyDetail(HttpServletRequest request) {
        return Transform.GetResult(productControlService.queryProductControlApplyDetail(request));
    }
    
    /**
     * @api {post} /{project_name}/product_control/apply_list 申请列表
     * @apiGroup apply_list
     * @apiName product_control
     * @apiDescription  申请列表
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} pageIndex 开始页码
     * @apiParam {number} pageSize  每页数据量
     *
     * @apiSuccess {boolean}  state  接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}   message 接口返回信息.
     * @apiSuccess {object[]} obj     	   控货查询列表
     * @apiSuccess {string}   obj.id   	
     * @apiSuccess {number}   obj.user_id   			用户名ID
     * @apiSuccess {number}   obj.user_store_address_id	用户门店地址ID
     * @apiSuccess {string}   obj.login_name  			用户名
     * @apiSuccess {string}   obj.user_manage_name  	姓名
     * @apiSuccess {string}   obj.brand_name  			品牌名称
     * @apiSuccess {string}   obj.management_address  	经营地址
     * @apiSuccess {number}   obj.longitude  			经度
     * @apiSuccess {number}   obj.latitude  			纬度
     * @apiSuccess {string}   obj.user_company_type  	经营类型
     * @apiSuccess {string}   obj.create_user_name  	创建人
     * @apiSuccess {string}   obj.create_date  			创建时间
     * @apiSuccess {string}   obj.product_control_type  控货类型
     * @apiSuccess {string}   obj.approval_user_name 	审核人
     * @apiSuccess {string}   obj.rejected_date 		审核时间
     * @apiSuccess {string}   obj.approval_state 		审核类型
     * @apiSuccess {string}   obj.rejected_reason 		审核驳回原因
     * 
     */
    @RequestMapping(value = "/apply_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductControlApplyList(HttpServletRequest request) {
        return Transform.GetResult(productControlService.queryProductControlApplyList(request));
    }
    
    /**
     * @api {post} /{project_name}/product_control/check 审批
     * @apiGroup check
     * @apiName product_control
     * @apiDescription  审批
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id 控货ID
     * @apiParam {string} approval_state 控货状态
     * 
     *
     * @apiSuccess {boolean}  state  接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}   message 接口返回信息.
     * 
     */
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    @ResponseBody
    public Packet productControlCheck(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
		try {
			pr = productControlService.productControlCheck(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    
    /**
     * @api {post} /{project_name}/product_control/periphery_list 查询周边控货信息
     * @apiGroup periphery_list
     * @apiName product_control
     * @apiDescription  查询周边控货信息
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} longitude 经度
     * @apiParam {number} latitude  纬度
     *
     * @apiSuccess {boolean}  state  接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}   message 接口返回信息.
     * @apiSuccess {object[]} obj     	   查询周边周边控货信息
     * @apiSuccess {string}   obj.name  				用户名
     * @apiSuccess {string}   obj.state					控货开关
     * @apiSuccess {string}   obj.radius  				控货半径
     * @apiSuccess {string}   obj.controlType  			控货类型
     * @apiSuccess {number}   obj.lng  					经度
     * @apiSuccess {number}   obj.lat  					纬度
     * @apiSuccess {number}   obj.total  				控货数
     * 
     */
    @RequestMapping(value = "/periphery_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryPeripheryList(HttpServletRequest request) {
        return Transform.GetResult(productControlService.queryPeripheryList(request));
    }
    
    /**
     * @api {post} /{project_name}/product_control/setting_list 查询控货设置列表
     * @apiGroup setting_list
     * @apiName product_control
     * @apiDescription  查询控货设置列表
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} pageIndex 开始页码
     * @apiParam {number} pageSize  每页数据量
     *
     * @apiSuccess {boolean}  state  接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}   message 接口返回信息.
     * @apiSuccess {object[]} obj     	   控货查询列表
     * @apiSuccess {number}   obj.user_id   			用户名ID
     * @apiSuccess {string}   obj.login_name  			用户名
     * @apiSuccess {string}   obj.user_manage_name  	姓名
     * @apiSuccess {string}   obj.user_manage_mobilephone  	手机号
	 * @apiSuccess {string}	  obj.referee_user_realname    业务员姓名
	 * @apiSuccess {string}	  obj.market_supervision_user_realna    业务经理姓名
     * @apiSuccess {string}   obj.create_date  			注册时间
     * @apiSuccess {string}   obj.store_count  			控货门店
     * @apiSuccess {string}   obj.state					控货限制
     * @apiSuccess {string}   obj.brand_name			控货品牌
     * @apiSuccess {string}   obj.condition				控货条件
     * @apiSuccess {string}   obj.approval_date		    开始控货时间
     * 
     */
    @RequestMapping(value = "/setting_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductControlSettingList(HttpServletRequest request) {
        return Transform.GetResult(productControlService.queryProductControlSettingList(request));
    }
    
    /**
     * 控货查询-达成率明细列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/achieving_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductControlAchievingList(HttpServletRequest request) {
        return Transform.GetResult(productControlService.queryProductControlAchievingList(request));
    }
    
    /**
     * 控货查询-控货门店列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/store_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductControlStoreList(HttpServletRequest request) {
        return Transform.GetResult(productControlService.queryProductControlStoreList(request));
    }
}
