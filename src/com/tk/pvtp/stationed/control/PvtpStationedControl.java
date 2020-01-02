package com.tk.pvtp.stationed.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.pvtp.stationed.service.PvtpStationedService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;

@Controller
@RequestMapping("/pvtp_stationed")
public class PvtpStationedControl {
	@Resource
	private PvtpStationedService pvtpStationedService;
    
    /**
	 * 查询私有平台商家列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryPvtpStationedlist(HttpServletRequest request) {
		return Transform.GetResult(pvtpStationedService.queryPvtpStationedList(request));
	}
	/**
     * @api {post} /{project_name}/pvtp_stationed/service_rate_detail
     * @apiName detail
     * @apiGroup stationed
     * @apiDescription  查询私有平台商家相关服务费详情
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id   入驻商ID
     * @apiParam {string} type 查询类型
     
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object}     obj 查询入驻商申请数据列表
     * 
     */
	@RequestMapping(value = "/service_rate_detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStationedServiceRateDetail(HttpServletRequest request) {
		return Transform.GetResult(pvtpStationedService.queryStationedServiceRateDetail(request));
	}
	
	/**
     * @api {post} /{project_name}/pvtp_stationed/service_charges
     * @apiName service_charges
     * @apiGroup stationed
     * @apiDescription  入驻商-私有商家服务费设置
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
		return Transform.GetResult(pvtpStationedService.editStationedServiceCharges(request));
	}
	
	/**
     * @api {post} /{project_name}/pvtp_stationed/storage_charges_edit
     * @apiName storage_charges_edit
     * @apiGroup stationed
     * @apiDescription  入驻商-私有商家仓储费编辑
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
		return Transform.GetResult(pvtpStationedService.editStorageCharges(request));
	}
	
	/**
     * @api {post} /{project_name}/pvtp_stationed/storage_charges_list
     * @apiName storage_charges_list
     * @apiGroup stationed
     * @apiDescription  入驻商-私有商家仓储费列表
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
		return Transform.GetResult(pvtpStationedService.queryStorageChargesList(request));
	}
	
	/**
     * @api {post} /{project_name}/pvtp_stationed/deposit_money
     * @apiName deposit_money
     * @apiGroup stationed
     * @apiDescription 入驻商-私有商家 保证金配置
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
		return Transform.GetResult(pvtpStationedService.editDepositMoney(request));
	}
	
	/**
     * @api {post} /{project_name}/pvtp_stationed/deposit_money_query
     * @apiName deposit_money_query
     * @apiGroup stationed
     * @apiDescription  查询入驻商-私有商家保证金
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
		return Transform.GetResult(pvtpStationedService.queryDepositMoney(request));
	}
	
	 /**
     * @api {post} /{project_name}/pvtp_stationed/deposit_money_balance_query
     * @apiName deposit_money_balance_query
     * @apiGroup stationed
     * @apiDescription  查询入驻商-私有商家保证金余额和账户余额
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
		return Transform.GetResult(pvtpStationedService.queryDepositMoneyBalance(request));
	}
	
	/**
     * @api {post} /{project_name}/pvtp_stationed/edit_state
     * @apiName edit_state
     * @apiGroup stationed
     * @apiDescription  是否启用入驻商-私有商家自动审核
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
		return Transform.GetResult(pvtpStationedService.editState(request));
	}
	
	/**
     * @api {post} /{project_name}/pvtp_stationed/inexp_record
     * @apiName inexp_record
     * @apiGroup pvtp_stationed
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
		return Transform.GetResult(pvtpStationedService.queryInExpRecord(request));
	}
}
