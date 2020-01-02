package com.tk.oms.stationed.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.stationed.service.StationedService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;


/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : StationedControl
 * 入驻商管理
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-4-10
 */
@Controller
@RequestMapping("/stationed")
public class StationedControl {
	@Resource
	private StationedService stationedService;
	/**
     * @api {post} /{project_name}/stationed/add
     * @apiName add
     * @apiGroup stationed
     * @apiDescription  添加入驻商
     * @apiVersion 0.0.1
     * 
     * @apiParam {string} user_name 登录名称
     * @apiParam {string} user_pwd 登录密码
     * @apiParam {string} company_type 企业类型
     * @apiParam {string} company_name 企业名称 
     * @apiParam {string} registration_number 企业注册号
     * @apiParam {string} registration_capital 注册资本
     * @apiParam {string} legal_personality 法人代表
     * @apiParam {date}   established_date 成立时间
     * @apiParam {date}   business_start_date 经营开始时间
     * @apiParam {date}   business_end_date 经营结束时间
     * @apiParam {string} business_scope 经营范围
     * @apiParam {string} registration_office 登记机关
     * @apiParam {date}   registration_date 登记时间
     * @apiParam {string} contacts 联系人
     * @apiParam {string} contact_phone_number 联系电话
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   total 成功数量
     * 
     */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Packet addStationed(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = stationedService.addStationed(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	/**
     * @api {post} /{project_name}/stationed/apply_list
     * @apiName apply_list
     * @apiGroup stationed
     * @apiDescription  入驻商申请数据列表
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} pageIndex 		开始页码
     * @apiParam {number} pageSize 			每页数据量 
     * @apiParam {string} user_name 		登录名称	
     * @apiParam {char}	  user_state 		审核状态
     
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 查询入驻商申请数据列表
     * @apiSuccess {number} 	obj.id 入驻商ID
     * @apiSuccess {string} 	obj.user_name 登录名称
     * @apiSuccess {string} 	obj.user_pwd 登录密码
     * @apiSuccess {string} 	obj.company_type 企业类型
     * @apiSuccess {string} 	obj.company_name 企业名称 
     * @apiSuccess {string} 	obj.registration_number 企业注册号
     * @apiSuccess {string} 	obj.registration_capital 注册资本
     * @apiSuccess {string} 	obj.legal_personality 法人代表
     * @apiSuccess {date}   	obj.established_date 成立时间
     * @apiSuccess {date}   	obj.business_start_date 经营开始时间
     * @apiSuccess {date}   	obj.business_end_date 经营结束时间
     * @apiSuccess {string} 	obj.business_scope 经营范围
     * @apiSuccess {string} 	obj.registration_office 登记机关
     * @apiSuccess {date}   	obj.registration_date 登记时间
     * @apiSuccess {string} 	obj.contacts 联系人
     * @apiSuccess {string} 	obj.contact_phone_number 联系电话
     * 
     */
	@RequestMapping(value = "/audit_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStationedAuditList(HttpServletRequest request) {
		return Transform.GetResult(stationedService.queryStationedAuditList(request));
	}
	
	/**
     * @api {post} /{project_name}/stationed/detail
     * @apiName detail
     * @apiGroup stationed
     * @apiDescription  查询入驻商详情
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id   入驻商ID
     * @apiParam {string} type 查询类型
     
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object}     obj 查询入驻商申请数据列表
     * @apiSuccess {number} 	obj.id 入驻商ID
     * @apiSuccess {string} 	obj.user_name 登录名称
     * @apiSuccess {string} 	obj.user_pwd 登录密码
     * @apiSuccess {string} 	obj.company_type 企业类型
     * @apiSuccess {string} 	obj.company_name 企业名称 
     * @apiSuccess {string} 	obj.registration_number 企业注册号
     * @apiSuccess {string} 	obj.registration_capital 注册资本
     * @apiSuccess {string} 	obj.legal_personality 法人代表
     * @apiSuccess {date}   	obj.established_date 成立时间
     * @apiSuccess {date}   	obj.business_start_date 经营开始时间
     * @apiSuccess {date}   	obj.business_end_date 经营结束时间
     * @apiSuccess {string} 	obj.business_scope 经营范围
     * @apiSuccess {string} 	obj.registration_office 登记机关
     * @apiSuccess {date}   	obj.registration_date 登记时间
     * @apiSuccess {string} 	obj.contacts 联系人
     * @apiSuccess {string} 	obj.contact_phone_number 联系电话
     * 
     */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStationedDetail(HttpServletRequest request) {
		return Transform.GetResult(stationedService.queryStationedDetail(request));
	}
	
	/**
     * @api {post} /{project_name}/stationed/audit
     * @apiName audit
     * @apiGroup stationed
     * @apiDescription  入驻商审核 
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id 入驻商ID
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
	@RequestMapping(value = "/audit", method = RequestMethod.POST)
	@ResponseBody
	public Packet auditStationed(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = stationedService.auditStationed(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
		
	}
	
	/**
     * @api {post} /{project_name}/stationed/remove
     * @apiName remove
     * @apiGroup stationed
     * @apiDescription  入驻商删除 
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id 入驻商ID
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	@ResponseBody
	public Packet removeStationed(HttpServletRequest request) {
		return Transform.GetResult(stationedService.removeStationed(request));
	}
	/**
     * @api {post} /{project_name}/stationed/edit
     * @apiName edit
     * @apiGroup stationed
     * @apiDescription  入驻商编辑
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id   		入驻商ID
     * @apiParam {string} edit_type 编辑类型
     * @apiParam {string} user_name 登录名称
     * @apiParam {string} user_pwd 登录密码
     * @apiParam {string} company_type 企业类型
     * @apiParam {string} company_name 企业名称 
     * @apiParam {string} registration_number 企业注册号
     * @apiParam {string} registration_capital 注册资本
     * @apiParam {string} legal_personality 法人代表
     * @apiParam {date}   established_date 成立时间
     * @apiParam {date}   business_start_date 经营开始时间
     * @apiParam {date}   business_end_date 经营结束时间
     * @apiParam {string} business_scope 经营范围
     * @apiParam {string} registration_office 登记机关
     * @apiParam {date}   registration_date 登记时间
     * @apiParam {string} contacts 联系人
     * @apiParam {string} contact_phone_number 联系电话
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Packet editStationed(HttpServletRequest request) {
		return Transform.GetResult(stationedService.editStationed(request));
	}
	
	/**
     * @api {post} /{project_name}/stationed/list
     * @apiName list
     * @apiGroup stationed
     * @apiDescription  查询入驻商列表
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} pageIndex 		开始页码
     * @apiParam {number} pageSize 			每页数据量
     * @apiParam {string} user_name 		登录名称
     
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 查询入驻商申请数据列表
     * @apiSuccess {number} 	obj.id 入驻商ID
     * @apiSuccess {string} 	obj.user_name 登录名称
     * @apiSuccess {string} 	obj.user_pwd 登录密码
     * @apiSuccess {string} 	obj.company_type 企业类型
     * @apiSuccess {string} 	obj.company_name 企业名称 
     * @apiSuccess {string} 	obj.registration_number 企业注册号
     * @apiSuccess {string} 	obj.registration_capital 注册资本
     * @apiSuccess {string} 	obj.legal_personality 法人代表
     * @apiSuccess {date}   	obj.established_date 成立时间
     * @apiSuccess {date}   	obj.business_start_date 经营开始时间
     * @apiSuccess {date}   	obj.business_end_date 经营结束时间
     * @apiSuccess {string} 	obj.business_scope 经营范围
     * @apiSuccess {string} 	obj.registration_office 登记机关
     * @apiSuccess {date}   	obj.registration_date 登记时间
     * @apiSuccess {string} 	obj.contacts 联系人
     * @apiSuccess {string} 	obj.contact_phone_number 联系电话
     * @apiSuccess {string}     obj.service_charges 服务费
     * 
     */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStationedList(HttpServletRequest request) {
		return Transform.GetResult(stationedService.queryStationedList(request));
	}
	
	/**
     * @api {post} /{project_name}/stationed/service_charges
     * @apiName service_charges
     * @apiGroup stationed
     * @apiDescription  入驻商服务费设置
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id 入驻商ID
     * @apiParam {number} service_charges 服务费率
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
	@RequestMapping(value = "/service_charges_edit", method = RequestMethod.POST)
	@ResponseBody
	public Packet editStationedServiceCharges(HttpServletRequest request) {
		return Transform.GetResult(stationedService.editStationedServiceCharges(request));
	}
	/**
     * @api {post} /{project_name}/stationed/storage_charges_edit
     * @apiName storage_charges_edit
     * @apiGroup stationed
     * @apiDescription  仓储费编辑
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} create_user_id                创建人ID
     * @apiParam {number} stationed_user_id				入驻商ID
     * @apiParam {list[]} dataList 						仓储费信息
     * @apiParam {number} dataList.storage_charges 		仓储费
     * @apiParam {string} dataList.product_size			商品尺码	
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
	@RequestMapping(value = "/storage_charges_edit", method = RequestMethod.POST)
	@ResponseBody
	public Packet editStorageCharges(HttpServletRequest request) {
		return Transform.GetResult(stationedService.editStorageCharges(request));
	}
	/**
     * @api {post} /{project_name}/stationed/storage_charges_list
     * @apiName storage_charges_list
     * @apiGroup stationed
     * @apiDescription  仓储费列表
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id 入驻商ID
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj
     * @apiSuccess {string}   	obj.product_size 商品尺码
     * @apiSuccess {number}   	obj.storage_charges 仓储费用
     * 
     */
	@RequestMapping(value = "/storage_charges_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStorageChargesList(HttpServletRequest request) {
		return Transform.GetResult(stationedService.queryStorageChargesList(request));
	}
	/**
     * @api {post} /{project_name}/stationed/brand_licensing_edit
     * @apiName brand_licensing_edit
     * @apiGroup stationed
     * @apiDescription  入驻商品牌授权
     * @apiVersion 0.0.1
     * 
     * @apiParam {list[]} dataList 			品牌列表
     * @apiParam {number} dataList.brand_id 品牌ID
     * @apiParam {number} stationed_user_id 入驻商ID
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
	@RequestMapping(value = "/brand_licensing_edit", method = RequestMethod.POST)
	@ResponseBody
	public Packet editBrandLicensing(HttpServletRequest request) {
		return Transform.GetResult(stationedService.editBrandLicensing(request));
	}
	/**
     * @api {post} /{project_name}/stationed/brand_licensing_list
     * @apiName brand_licensing_list
     * @apiGroup stationed
     * @apiDescription  入驻商品牌授权列表
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} stationed_user_id 入驻商ID
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj
     * @apiSuccess {number}   	obj.id 品牌ID
     * @apiSuccess {number}   	obj.exist 是否存在关联 0否 1是
     * 
     */
	@RequestMapping(value = "/brand_licensing_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryBrandLicensingList(HttpServletRequest request) {
		return Transform.GetResult(stationedService.queryBrandLicensingList(request));
	}
	
	/**
     * @api {post} /{project_name}/stationed/plan_list
     * @apiName plan_list
     * @apiGroup stationed
     * @apiDescription  生产计划列表
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} pageIndex 		开始页码
     * @apiParam {number} pageSize 			每页数据量 
     * @apiParam {string} plan_number		计划编号
     * @apiParam {string} start_create_date	创建时间(开始)
     * @apiParam {string} end_create_date	创建时间(结束)
     * @apiParam {char}   state				状态
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj	生产计划列表
     * @apiSuccess {string}   	obj.plan_number 		计划编号
     * @apiSuccess {char}   	obj.state 				状态
     * @apiSuccess {date}   	obj.completion_date 	计划完成时间
     * @apiSuccess {string}   	obj.product_type_name 	商品分类
     * @apiSuccess {number}   	obj.plan_totality		生产总数
     * @apiSuccess {date}   	obj.create_date 		创建时间
     * 
     */
	@RequestMapping(value = "/plan_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryProductionPlanList(HttpServletRequest request) {
		return Transform.GetResult(stationedService.queryProductionPlanList(request));
	}
	
	/**
     * @api {post} /{project_name}/stationed/plan_detail
     * @apiName plan_detail
     * @apiGroup stationed
     * @apiDescription  生产计划详情
     * @apiVersion 0.0.1
     *
	 * @apiParam {string} plan_number 计划编号 选填
	 *
	 * @apiSuccess {object[]} obj 生产计划单明细
	 *
	 * @apiSuccess {object} obj 计划规格分组明细
	 * @apiSuccess {string} obj.PLAN_NUMBER 计划单号
	 * @apiSuccess {number} obj.PRODUCT_ITEMNUMBER 商品货号
	 * @apiSuccess {string} obj.PRODUCT_NAME 商品名称
	 * @apiSuccess {string} obj.PRODUCT_IMG_URL 商品主图
	 * @apiSuccess {string} obj.PRODUCT_COLOR 商品颜色
	 * @apiSuccess {string} obj.PRODUCT_SPECS 商品规格
	 * @apiSuccess {number} obj.PLAN_NUM 计划生产数量
	 *
	 * @apiSuccess {object} obj.size_detail 计划尺码分组明细
	 * @apiSuccess {number} obj.size_detail.PRODUCT_SKU sku
	 * @apiSuccess {number} obj.size_detail.PRODUCT_SIZE 商品尺码
	 * @apiSuccess {number} obj.size_detail.PLAN_NUM 计划生产数量
	 * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string} message 接口返回信息     *
     * 
     */
	@RequestMapping(value = "/plan_detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryProductionPlanDetail(HttpServletRequest request) {
		return Transform.GetResult(stationedService.queryProductionPlanDetail(request));
	}
	
	/**
     * @api {post} /{project_name}/stationed/in_storage_list
     * @apiName in_storage_list
     * @apiGroup stationed
     * @apiDescription  入库申请列表
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} pageIndex 		开始页码
     * @apiParam {number} pageSize 			每页数据量 
     * @apiParam {string} apply_number		申请单号
     * @apiParam {string} start_create_date	创建时间(开始)
     * @apiParam {string} end_create_date	创建时间(结束)
     * @apiParam {char}   state				状态
     * 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj	入库申请列表
     * @apiSuccess {string}   	obj.apply_number 		申请单号
     * @apiSuccess {char}   	obj.state 				状态
     * @apiSuccess {string}   	obj.instorage_count 	实际入库数量
     * @apiSuccess {number}   	obj.instorage_totality	申请入库总数
     * @apiSuccess {string}   	obj.approver_name		审批人
     * @apiSuccess {date}   	obj.approval_date		审批时间
     * @apiSuccess {string}   	obj.create_user_name	申请人
     * @apiSuccess {date}   	obj.create_date 		创建时间
     * 
     */
	@RequestMapping(value = "/in_storage_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryInStorageApplyList(HttpServletRequest request) {
		return Transform.GetResult(stationedService.queryInStorageApplyList(request));
	}
	
	/**
     * @api {post} /{project_name}/stationed/plan_detail
     * @apiName plan_detail
     * @apiGroup stationed
     * @apiDescription  入库申请详情
     * @apiVersion 0.0.1
     * 
     * @apiParam {string} apply_number 申请单号
     *
	 * @apiSuccess {object[]} obj 入库申请单详细
	 *
	 * @apiSuccess {object} obj 入库申请规格分组明细
	 * @apiSuccess {string} obj.PRODUCT_ITEMNUMBER 商品货号
	 * @apiSuccess {string} obj.PRODUCT_COLOR 商品颜色
	 * @apiSuccess {string} obj.PRODUCT_SPECS 商品规格
	 * @apiSuccess {number} obj.APPLY_COUNT 申请数量
	 * @apiSuccess {number} obj.IN_STORAGE_COUNT 入库数量
	 *
	 * @apiSuccess {object[]} obj.size_detail 入库申请尺码分组明细
	 * @apiSuccess {number} obj.size_detail.ID 入库申请ID
	 * @apiSuccess {string} obj.size_detail.PRODUCT_UNIQUE_CODE 商品唯一码
	 * @apiSuccess {string} obj.size_detail.PRODUCT_SIZE 商品尺码
	 * @apiSuccess {string} obj.size_detail.IN_STORAGE_COUNT 入库数量
	 * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string} message 接口返回信息
     * 
     */
	@RequestMapping(value = "/in_storage_detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryInStorageApplyDetail(HttpServletRequest request) {
		return Transform.GetResult(stationedService.queryInStorageApplyDetail(request));
	}
	
	/**
     * @api {post} /{project_name}/stationed/edit_state
     * @apiName edit_state
     * @apiGroup stationed
     * @apiDescription  是否启用
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id	入驻商ID
     * @apiParam {string} state	是否启用状态
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
	@RequestMapping(value = "/edit_state", method = RequestMethod.POST)
	@ResponseBody
	public Packet editState(HttpServletRequest request) {
		return Transform.GetResult(stationedService.editState(request));
	}
	
	/**
     * @api {post} /{project_name}/stationed/usernode_list
     * @apiName usernode_list
     * @apiGroup stationed
     * @apiDescription  获取入驻商节点权限列表
     * @apiVersion 0.0.1
     *  
     * @apiParam {number} user_id			入驻商ID
     * 
     * 
     * @apiSuccess {boolean}    state 	接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj		入驻商权限列表，
     * @apiSuccess {string}   	obj.id 		权限菜单ID
     * @apiSuccess {char}   	obj.name 	权限菜单名称
     * @apiSuccess {string}   	obj.level 	权限菜单级别
     * @apiSuccess {number}   	obj.chk		是否拥有权限（0：没有权限，非0为有权限）
     * 
     */
	@RequestMapping(value = "/usernode_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryUserNodeList(HttpServletRequest request) {
		return Transform.GetResult(stationedService.queryUserNodeList(request));
	}
	
	/**
     * @api {post} /{project_name}/stationed/edit_usernode
     * @apiName edit_usernode
     * @apiGroup stationed
     * @apiDescription  编辑入驻商权限
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} user_id	入驻商ID
     * @apiParam {list[]} node_ids 			菜单或权限列表
     * @apiParam {number} node_ids.id       菜单或权限ID
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
	@RequestMapping(value = "/edit_usernode", method = RequestMethod.POST)
	@ResponseBody
	public Packet editUserNode(HttpServletRequest request) {
		return Transform.GetResult(stationedService.editUserNode(request));
	}
	/**
     * @api {post} /{project_name}/stationed/reset_pwd
     * @apiName reset_pwd
     * @apiGroup stationed
     * @apiDescription  密码重置
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id				入驻商ID
     * @apiParam {string} user_pwd 			密码
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
	@RequestMapping(value = "/reset_pwd", method = RequestMethod.POST)
	@ResponseBody
	public Packet resetPwd(HttpServletRequest request) {
		return Transform.GetResult(stationedService.resetPwd(request));
	}
	
	/**
     * @api {post} /{project_name}/stationed/deposit_money
     * @apiName deposit_money
     * @apiGroup stationed
     * @apiDescription  保证金配置
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id				入驻商ID
     * @apiParam {string} deposit_money 	保证金
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
	@RequestMapping(value = "/deposit_money_edit", method = RequestMethod.POST)
	@ResponseBody
	public Packet editDepositMoney(HttpServletRequest request) {
		return Transform.GetResult(stationedService.editDepositMoney(request));
	}
	
	/**
     * @api {post} /{project_name}/stationed/deposit_money_query
     * @apiName deposit_money_query
     * @apiGroup stationed
     * @apiDescription  查询保证金
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id				入驻商ID
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {string} 	deposit_money 	保证金
     * 
     */
	@RequestMapping(value = "/deposit_money_query", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryDepositMoney(HttpServletRequest request) {
		return Transform.GetResult(stationedService.queryDepositMoney(request));
	}
	
	/**
     * @api {post} /{project_name}/stationed/inexp_record
     * @apiName inexp_record
     * @apiGroup stationed
     * @apiDescription  查询入驻商收支记录
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} pageIndex 		开始页码
     * @apiParam {number} pageSize 			每页数据量 
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj		入驻商收支记录列表
     * @apiSuccess {number}   	obj.id 				
     * @apiSuccess {string}   	obj.STATIONED_NAME 	入驻商名称
     * @apiSuccess {string}   	obj.RECORD_NUMBER 	收付单号
     * @apiSuccess {string}   	obj.RECORD_TYPE		收支类型
     * @apiSuccess {string}   	obj.REMARK			摘要
     * @apiSuccess {string}   	obj.TURNOVER_TYPE	收付类型
     * @apiSuccess {string}   	obj.TURNOVER_NUMBER	收付关联号
     * @apiSuccess {string}   	obj.CREATE_DATE		业务时间
     * @apiSuccess {number}   	obj.MONEY			金额
     * @apiSuccess {number}   	obj.SURPLUS_MONEY	余额
     * 
     */
	@RequestMapping(value = "/inexp_record", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryInExpRecord(HttpServletRequest request) {
		return Transform.GetResult(stationedService.queryInExpRecord(request));
	}
	/**
	 * @api {post} /{project_name}/stationed/simple_list 入驻商主账号简要信息列表
	 * @apiName inexp_record
	 * @apiGroup stationed
	 * @apiDescription  查询入驻商主账号简要信息列表，仅返回部分关键信息，主要用户选择入驻商
	 * @apiVersion 0.0.1
	 * 
	 * 
	 * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string}     message 接口返回信息
	 * @apiSuccess {object[]}   obj		入驻商收支记录列表
	 * @apiSuccess {number}   	obj.ID 				入驻商ID	
	 * @apiSuccess {string}   	obj.USER_CODE 		入驻商代码
	 * @apiSuccess {string}   	obj.USER_NUMBER 	入驻商编号,用于入驻商货号的创建，最大长度为3
	 * @apiSuccess {string}   	obj.USER_NAME		登录名称，用于登录
	 * @apiSuccess {string}   	obj.COMPANY_NAME	企业名称
	 * 
	 */
	@RequestMapping(value = "/simple_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet querySimpleStationedList(HttpServletRequest request) {
		return Transform.GetResult(stationedService.querySimpleStationedList(request));
	}
	
	/**
	 * @api {post} /{project_name}/stationed/option 入驻商下拉列表
	 * @apiName inexp_record
	 * @apiGroup stationed
	 * @apiDescription  查询入驻商下拉列表
	 * @apiVersion 0.0.1
	 * 
	 * 
	 * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string}     message 接口返回信息
	 * @apiSuccess {object[]}   obj		入驻商收支记录列表
	 * @apiSuccess {number}   	obj.id 				入驻商ID	
	 * @apiSuccess {string}   	obj.name 		            入驻商名称
	 */
	@RequestMapping(value = "/option", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStationedOption(HttpServletRequest request) {
		return Transform.GetResult(stationedService.queryStationedOption(request));
	}

	/**
	 * 查询入驻商角色列表，如果有权限则选中
	 */
	/**
	 * @api {post} /{project_name}/stationed/user_role_list 入驻商角色列表
	 * @apiName inexp_record
	 * @apiGroup stationed
	 * @apiDescription  入驻商角色列表
	 * @apiVersion 0.0.1
	 *
	 * @apiParam {number} user_id 			入驻商id
	 *
	 * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string}     message 接口返回信息
	 * @apiSuccess {object[]}   obj		入驻商角色列表
	 * @apiSuccess {number}   	obj.id 							角色ID
	 * @apiSuccess {string}   	obj.ROLE_NAME 		            角色名称
	 * @apiSuccess {string}   	obj.REMARKS 		            角色描述
	 */
	@RequestMapping(value = "/user_role_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet user_role_list(HttpServletRequest request) {
		return Transform.GetResult(stationedService.queryStationedRole(request));
	}

	/**
	 * @api {post} /{project_name}/stationed/user_role_edit 配置用户角色权限
	 * @apiName inexp_record
	 * @apiGroup stationed
	 * @apiDescription  配置用户角色权限
	 * @apiVersion 0.0.1
	 *
	 * @apiParam {number} user_id 			入驻商id
	 * @apiParam {number} role_ids 			角色id
	 *
	 * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string}     message 接口返回信息
	 * @apiSuccess {object}     obj
	 */
	@RequestMapping(value = "/user_role_edit", method = RequestMethod.POST)
	@ResponseBody
	public Packet editStationedRole(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			return Transform.GetResult(stationedService.editStationedRole(request));
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			return Transform.GetResult(pr);
		}
	}

	/**
	 * @api {post} /{project_name}/stationed/role_list 系统入驻商角色列表
	 * @apiName inexp_record
	 * @apiGroup stationed
	 * @apiDescription  系统入驻商角色列表
	 * @apiVersion 0.0.1
	 *
	 * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string}     message 接口返回信息
	 * @apiSuccess {object}     obj
	 * @apiSuccess {number}   	obj.id 							角色ID
	 * @apiSuccess {string}   	obj.ROLE_NAME 		            角色名称
	 * @apiSuccess {string}   	obj.CREATE_DATE 		        创建时间
	 * @apiSuccess {string}   	obj.REMARKS 		            角色描述
	 */
	@RequestMapping(value = "/role_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet querySysStationedRoleList(HttpServletRequest request) {
		return Transform.GetResult(stationedService.querySysStationedRoleList(request));
	}

	/**
	 * @api {post} /{project_name}/stationed/role_detail 系统入驻商角色明细
	 * @apiName inexp_record
	 * @apiGroup stationed
	 * @apiDescription  系统入驻商角色明细
	 * @apiVersion 0.0.1
	 *
	 * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string}     message 接口返回信息
	 * @apiSuccess {object}     obj
	 * @apiSuccess {number}   	obj.id 							角色ID
	 * @apiSuccess {string}   	obj.ROLE_NAME 		            角色名称
	 * @apiSuccess {string}   	obj.CREATE_DATE 		        创建时间
	 * @apiSuccess {string}   	obj.REMARKS 		            角色描述
	 */
	@RequestMapping(value = "/role_detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet querySysStationedRoleDetail(HttpServletRequest request) {
		return Transform.GetResult(stationedService.querySysStationedRoleDetail(request));
	}

	/**
	 * @api {post} /{project_name}/stationed/user_role_edit 配置用户角色权限
	 * @apiName inexp_record
	 * @apiGroup stationed
	 * @apiDescription  配置用户角色权限
	 * @apiVersion 0.0.1
	 *
	 * @apiParam {number} user_id 			入驻商id
	 * @apiParam {number} role_nodes 		角色id
	 * @apiParam {string} role_name 		角色名称
	 *
	 * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string}     message 接口返回信息
	 * @apiSuccess {object}     obj
	 */
	@RequestMapping(value = "/role_update", method = RequestMethod.POST)
	@ResponseBody
	public Packet editSysStationedRole(HttpServletRequest request) {
		return Transform.GetResult(stationedService.editSysStationedRole(request));
	}

	/**
	 * @api {post} /{project_name}/stationed/role_node_list 查询菜单或按钮节点列表，如果有权限则选中
	 * @apiName inexp_record
	 * @apiGroup stationed
	 * @apiDescription  查询菜单或按钮节点列表，如果有权限则选中
	 * @apiVersion 0.0.1
	 *
	 * @apiParam {number} role_id 			角色id
	 *
	 * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string}     message 接口返回信息
	 * @apiSuccess {object}     obj
	 */
	@RequestMapping(value = "/role_node_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet querySysStationedRoleNode(HttpServletRequest request) {
		return Transform.GetResult(stationedService.querySysStationedRoleNodeList(request));
	}

	/**
	 * @api {post} /{project_name}/stationed/role_add 新增系统入驻商角色
	 * @apiName inexp_record
	 * @apiGroup stationed
	 * @apiDescription  新增系统入驻商角色
	 * @apiVersion 0.0.1
	 *
	 * @apiParam {number} user_id 			入驻商id
	 * @apiParam {number} role_nodes 		角色id
	 * @apiParam {string} role_name 		角色名称
	 *
	 * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string}     message 接口返回信息
	 * @apiSuccess {object}     obj
	 */
	@RequestMapping(value = "/role_add", method = RequestMethod.POST)
	@ResponseBody
	public Packet addSysStationedRole(HttpServletRequest request) {
		return Transform.GetResult(stationedService.addSysStationedRole(request));
	}

	/**
	 * @api {post} /{project_name}/stationed/user_role_edit 删除系统入驻商角色
	 * @apiName inexp_record
	 * @apiGroup stationed
	 * @apiDescription  删除系统入驻商角色
	 * @apiVersion 0.0.1
	 *
	 * @apiParam {number} role_id 			角色id
	 *
	 * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string}     message 接口返回信息
	 * @apiSuccess {object}     obj
	 */
	@RequestMapping(value = "/role_delete", method = RequestMethod.POST)
	@ResponseBody
	public Packet delSysStationedRole(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			return Transform.GetResult(stationedService.delSysStationedRole(request));
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			return Transform.GetResult(pr);
		}
	}

	/**
	 * @api {post} /{project_name}/stationed/node_list
	 * @apiName inexp_record
	 * @apiGroup stationed
	 * @apiDescription  获取入驻商节点权限列表
	 * @apiVersion 0.0.1
	 *
	 * @apiSuccess {boolean}    state 	接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string}     message 接口返回信息
	 * @apiSuccess {object[]}   obj		入驻商权限列表，
	 * @apiSuccess {string}   	obj.id 		权限菜单ID
	 * @apiSuccess {char}   	obj.name 	权限菜单名称
	 * @apiSuccess {string}   	obj.level 	权限菜单级别
	 * @apiSuccess {number}   	obj.chk		是否拥有权限（0：没有权限，非0为有权限）
	 *
	 */
	@RequestMapping(value = "/node_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStationedUserNodeList(HttpServletRequest request) {
		return Transform.GetResult(stationedService.queryStationedUserNodeList(request));
	}
	/**
     * @api {post} /{project_name}/stationed/supplier_list
     * @apiName supplier_list
     * @apiGroup stationed
     * @apiDescription  查询供应商
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} stationed_user_id			入驻商ID
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object}     obj
     * @apiSuccess {number}   	obj.id 					 ID
	 * @apiSuccess {string}   	obj.user_name 		            用户名
	 * @apiSuccess {string}   	obj.company_name 		 公司名称
	 * @apiSuccess {string}   	obj.contacts 		   	 联系人
	 * @apiSuccess {string}   	obj.create_date 		 注册时间
     * 
     */
	@RequestMapping(value = "/supplier_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet querySupplierList(HttpServletRequest request) {
		return Transform.GetResult(stationedService.querySupplierList(request));
	}
	/**
     * @api {post} /{project_name}/stationed/supplier_edit
     * @apiName supplier_edit
     * @apiGroup stationed
     * @apiDescription  供应商配置
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} stationed_user_id			入驻商ID
     * @apiParam {Object[]} supplier_id				供应商ID
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * 
     */
	@RequestMapping(value = "/supplier_edit", method = RequestMethod.POST)
	@ResponseBody
	public Packet editSupplier(HttpServletRequest request) {
		return Transform.GetResult(stationedService.editSupplier(request));
	}
	
	/**
     * @api{post} /{oms_server}/stationed/stationed_certification_approval_list 入驻商认证审批列表
     * @apiGroup stationed
     * @apiName member_certification_approval_list
     * @apiDescription  入驻商认证审批列表
     * @apiVersion 0.0.1
     * @apiParam{string} [pageIndex=1] 起始页
     * @apiParam{string} [pageSize=10] 分页大小
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object[]} obj 会员认证审批列表集合
     * @apiSuccess {number} obj.ID    				主键id
     * @apiSuccess {number} obj.USER_ID    			会员id
     * @apiSuccess {string} obj.USER_REAL_NAME    			真实姓名
     * @apiSuccess {string} obj.USER_MANAGE_CARDID    用户身份证号
     * @apiSuccess {string} obj.USER_MANAGE_CARDID_IMG    			身份证正面照
     * @apiSuccess {number} obj.USER_COMPANY_ADDRESS_PROVINCE       公司/店铺所在省id
     * @apiSuccess {number} obj.USER_COMPANY_ADDRESS_CITY        公司/店铺所在市id
     * @apiSuccess {number} USER_COMPANY_ADDRESS_COUNTY 公司/店铺所在县id
     * @apiSuccess {string} USER_COMPANY_ADDRESS_DEAILS 公司/店铺详细地址
     * @apiSuccess {number} STATE 认证状态：0、待审核；1、已审批；2、驳回
     * @apiSuccess {number} TYPE 1 会员信息  2入驻商信息
     * @apiSuccess {string} CREATE_DATE 创建时间
     * @apiSuccess {string} USER_COMPANY_IMG 公司/店铺照片
     * @apiSuccess {number} APPROVAL_USER_ID 审批人id
     * @apiSuccess {string} APPROVAL_DATE 审批时间
     * @apiSuccess {string} RREJECTED_REASON 驳回原因
     * @apiSuccess {string} LOGIN_NAME 用户名
     * @apiSuccess {string} USER_MANAGE_NAME 姓名
     * @apiSuccess {string} ADDRESS 公司/店铺全地址
     */
    @RequestMapping(value = "/stationed_certification_approval_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet stationedCertificationApprovalList(HttpServletRequest request) {
        return Transform.GetResult(stationedService.stationedCertificationApprovalList(request));
    }
    
    /**
     * @api{post} /{oms_server}/stationed/stationed_certification_approval_detail 会员认证审批详情
     * @apiGroup stationed
     * @apiName stationed_certification_approval_detail
     * @apiDescription  会员认证审批详情
     * @apiVersion 0.0.1
     * @apiParam{string} [pageIndex=1] 起始页
     * @apiParam{string} [pageSize=10] 分页大小
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object[]} obj 会员认证审批列表集合
     * @apiSuccess {number} obj.ID    				主键id
     * @apiSuccess {number} obj.USER_ID    			会员id
     * @apiSuccess {string} obj.USER_REAL_NAME    			真实姓名
     * @apiSuccess {string} obj.USER_MANAGE_CARDID    用户身份证号
     * @apiSuccess {string} obj.USER_MANAGE_CARDID_IMG    			身份证正面照
     * @apiSuccess {number} obj.USER_COMPANY_ADDRESS_PROVINCE       公司/店铺所在省id
     * @apiSuccess {number} obj.USER_COMPANY_ADDRESS_CITY        公司/店铺所在市id
     * @apiSuccess {number} USER_COMPANY_ADDRESS_COUNTY 公司/店铺所在县id
     * @apiSuccess {string} USER_COMPANY_ADDRESS_DEAILS 公司/店铺详细地址
     * @apiSuccess {number} STATE 认证状态：0、待审核；1、已审批；2、驳回
     * @apiSuccess {number} TYPE 1 会员信息  2入驻商信息
     * @apiSuccess {string} CREATE_DATE 创建时间
     * @apiSuccess {string} USER_COMPANY_IMG 公司/店铺照片
     * @apiSuccess {number} APPROVAL_USER_ID 审批人id
     * @apiSuccess {string} APPROVAL_DATE 审批时间
     * @apiSuccess {string} RREJECTED_REASON 驳回原因
     * @apiSuccess {string} LOGIN_NAME 用户名
     * @apiSuccess {string} USER_MANAGE_NAME 姓名
     * @apiSuccess {string} ADDRESS 公司/店铺全地址
     */
    @RequestMapping(value = "/stationed_certification_approval_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet stationedCertificationApprovalDetail(HttpServletRequest request) {
        return Transform.GetResult(stationedService.stationedCertificationApprovalDetail(request));
    }
    
    /**
     * @api{post} /{oms_server}/stationed/stationed_certification_approval 会员认证审批
     * @apiGroup stationed
     * @apiName stationed_certification_approval
     * @apiDescription  会员认证审批
     * @apiVersion 0.0.1
     * @apiParam{number} id 主键id
     * @apiParam{number} approva_user_id 审批人id
     * @apiParam{string} rejected_reason  驳回原因(驳回时含改参数)
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     */
    @RequestMapping(value = "/stationed_certification_approval", method = RequestMethod.POST)
	@ResponseBody
	public Packet stationedCertificationApproval(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = stationedService.stationedCertificationApproval(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
    /**
     * @api{post} /{oms_server}/stationed/set/outstock/warehouse 配置入驻商服务默认缺货订购仓库
     * @apiGroup stationed
     * @apiName  set/outstock/warehouse
     * @apiDescription  配置入驻商服务默认缺货订购仓库
     * @apiVersion 0.0.1
     * @apiParam{number} id 							入驻商ID
     * @apiParam{number} default_outstock_warehouse 	仓库ID
     * 
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     */
    @RequestMapping(value = "/set/outstock/warehouse", method = RequestMethod.POST)
    @ResponseBody
    public Packet editStationedDefaultOutstockWarehouse(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
    	try {
    		pr = stationedService.editStationedDefaultOutstockWarehouse(request);
    	} catch (Exception e) {
    		pr.setState(false);
    		pr.setMessage(e.getMessage());
    		e.printStackTrace();
    	}
    	return Transform.GetResult(pr);
    }
    
    /**
     * @api {post} /{project_name}/stationed/deposit_money_balance_query
     * @apiName deposit_money_balance_query
     * @apiGroup stationed
     * @apiDescription  查询保证金余额和账户余额
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id				入驻商ID
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {string} 	deposit_money_balance 	保证金余额
     * @apiSuccess {string} 	account_balance 		余额
     * 
     */
	@RequestMapping(value = "/deposit_money_balance_query", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryDepositMoneyBalance(HttpServletRequest request) {
		return Transform.GetResult(stationedService.queryDepositMoneyBalance(request));
	}
	
    /**
     * 查询入驻商补扣款列表
     * @param request
     * @return
     */
	@RequestMapping(value = "/sup_deduct_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet querySupDeductListForPage(HttpServletRequest request) {
		return Transform.GetResult(stationedService.querySupDeductListForPage(request));
	}
	
	/**
	 * 新增入驻商补扣款
	 * @param request
	 * @return
	 */
    @RequestMapping(value = "/sup_deduct_add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addSupDeduct(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
    	try {
    		pr = stationedService.addSupDeduct(request);
    	} catch (Exception e) {
    		pr.setState(false);
    		pr.setMessage(e.getMessage());
    		e.printStackTrace();
    	}
    	return Transform.GetResult(pr);
    }
    
    /**
	 * 删除入驻商补扣款
	 * @param request
	 * @return
	 */
    @RequestMapping(value = "/sup_deduct_remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeSupDeduct(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
    	try {
    		pr = stationedService.removeSupDeduct(request);
    	} catch (Exception e) {
    		pr.setState(false);
    		pr.setMessage(e.getMessage());
    		e.printStackTrace();
    	}
    	return Transform.GetResult(pr);
    }
    
    /**
	 * 编辑入驻商补扣款
	 * @param request
	 * @return
	 */
    @RequestMapping(value = "/sup_deduct_edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editSupDeduct(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
    	try {
    		pr = stationedService.editSupDeduct(request);
    	} catch (Exception e) {
    		pr.setState(false);
    		pr.setMessage(e.getMessage());
    		e.printStackTrace();
    	}
    	return Transform.GetResult(pr);
    }
    
    /**
     * 查询入驻商补扣款详情
     * @param request
     * @return
     */
    @RequestMapping(value = "/sup_deduct_detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet querySupDeductDetail(HttpServletRequest request) {
		return Transform.GetResult(stationedService.querySupDeductDetail(request));
	}
    
    /**
	 * 入驻商补扣款审批
	 * @param request
	 * @return
	 */
    @RequestMapping(value = "/sup_deduct_approval", method = RequestMethod.POST)
    @ResponseBody
    public Packet approvalSupDeduct(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
    	try {
    		pr = stationedService.approvalSupDeduct(request);
    	} catch (Exception e) {
    		pr.setState(false);
    		pr.setMessage(e.getMessage());
    		e.printStackTrace();
    	}
    	return Transform.GetResult(pr);
    }
    
    /**
	 * 入驻商补扣款签批
	 * @param request
	 * @return
	 */
    @RequestMapping(value = "/sup_deduct_sign", method = RequestMethod.POST)
    @ResponseBody
    public Packet signSupDeduct(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
    	try {
    		pr = stationedService.signSupDeduct(request);
    	} catch (Exception e) {
    		pr.setState(false);
    		pr.setMessage(e.getMessage());
    		e.printStackTrace();
    	}
    	return Transform.GetResult(pr);
    }
    
    /** 
	 * 云仓货主配置【童库-私有商品共享】
	 */
    @RequestMapping(value = "/cloud_warehouse_owner_set", method = RequestMethod.POST)
    @ResponseBody
    public Packet cloudWarehouseOwnerSet(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
    	try {
    		pr = stationedService.cloudWarehouseOwnerSet(request);
    	} catch (Exception e) {
    		pr.setState(false);
    		pr.setMessage(e.getMessage());
    		e.printStackTrace();
    	}
    	return Transform.GetResult(pr);
    }
    
    /** 
   	 * 获取云仓货主配置详情【童库-私有商品共享】
   	 */
       @RequestMapping(value = "/get_cloud_warehouse_owner_set", method = RequestMethod.POST)
       @ResponseBody
       public Packet getCloudWarehouseOwnerSet(HttpServletRequest request) {
       	ProcessResult pr = new ProcessResult();
       	try {
       		pr = stationedService.getCloudWarehouseOwnerSet(request);
       	} catch (Exception e) {
       		pr.setState(false);
       		pr.setMessage(e.getMessage());
       		e.printStackTrace();
       	}
       	return Transform.GetResult(pr);
       }
    
	/** 
	 * 私有商家配置【童库-私有商品共享】
	 */
    @RequestMapping(value = "/private_business_set", method = RequestMethod.POST)
    @ResponseBody
    public Packet privateBusinessSet(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
    	try {
    		pr = stationedService.privateBusinessSet(request);
    	} catch (Exception e) {
    		pr.setState(false);
    		pr.setMessage(e.getMessage());
    		e.printStackTrace();
    	}
    	return Transform.GetResult(pr);
    }
    
	/** 
	 * 获取私有商家配置详情【童库-私有商品共享】
	 */
    @RequestMapping(value = "/get_private_business_set", method = RequestMethod.POST)
    @ResponseBody
    public Packet getPrivateBusinessSet(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
    	try {
    		pr = stationedService.getPrivateBusinessSet(request);
    	} catch (Exception e) {
    		pr.setState(false);
    		pr.setMessage(e.getMessage());
    		e.printStackTrace();
    	}
    	return Transform.GetResult(pr);
    }
    
}
