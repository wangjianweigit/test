package com.tk.store.finance.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.store.finance.service.DailyHandOverService;
import com.tk.store.finance.service.StoreMoneyService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;

@Controller
@RequestMapping("/daily_handover")
public class DailyHandOverControl {
	
	@Resource
	private DailyHandOverService dailyHandOverService;
	
	/**
    *
    * @api {post} /{project_name}/daily_handover/daily_list  查询日结记录列表
    * @apiGroup daily_handover
    * @apiName daily_list
    * @apiDescription  查询日结记录列表
    * @apiVersion 0.0.1
	
	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条   
	* @apiParam {number} AGENT_ID				店铺id 
	* @apiParam {number} STAFF_ID				收银员id 
	* @apiParam {string} START_TIME				开始时间
	* @apiParam {string} END_TIME				结束时间 

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 查询日结记录列表
    * @apiSuccess {string} obj.STORE_NAME		门店名称
    * @apiSuccess {string} obj.STAFF_NAME	           收银员
    * @apiSuccess {number} obj.SALES_MONEY_TOTAL	销售总额
    * @apiSuccess {number} obj.SALES_NUM			销售单数
    * @apiSuccess {number} obj.REFUND_TOTAL		退款总额
    * @apiSuccess {number} obj.REFUND_NUM		退款单数
    * @apiSuccess {string} obj.START_TIMES		开始时间
    * @apiSuccess {string} obj.END_TIMES	结束时间
    * 
    */
	@RequestMapping(value = "/daily_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryDailyListForPage(HttpServletRequest request) {
		return Transform.GetResult(dailyHandOverService.queryDailyListForPage(request));
	}
	
	/**
    *
    * @api {post} /{project_name}/daily_handover/handover_list  查询交接班记录列表
    * @apiGroup daily_handover
    * @apiName handover_list
    * @apiDescription  查询交接班记录列表
    * @apiVersion 0.0.1
	
	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条   
	* @apiParam {number} AGENT_ID				店铺id 
	* @apiParam {number} STAFF_ID				收银员id 
	* @apiParam {string} START_TIME				开始时间
	* @apiParam {string} END_TIME				结束时间 

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 查询日结记录列表
    * @apiSuccess {string} obj.STORE_NAME		门店名称
    * @apiSuccess {string} obj.STAFF_NAME	           收银员
    * @apiSuccess {number} obj.SALES_MONEY_TOTAL	销售总额
    * @apiSuccess {number} obj.SALES_NUM			销售单数
    * @apiSuccess {number} obj.REFUND_TOTAL		退款总额
    * @apiSuccess {number} obj.REFUND_NUM		退款单数
    * @apiSuccess {string} obj.START_TIMES		开始时间
    * @apiSuccess {string} obj.END_TIMES	结束时间
    * 
    */
	@RequestMapping(value = "/handover_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryHandoverListForPage(HttpServletRequest request) {
		return Transform.GetResult(dailyHandOverService.queryHandoverListForPage(request));
	}
	
	/**
    *
    * @api {post} /{project_name}/daily_handover/daily_detail  查询日结详情
    * @apiGroup daily_handover
    * @apiName daily_detail
    * @apiDescription查询日结详情
    * @apiVersion 0.0.1
	
	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条   
	* @apiParam {number} AGENT_ID				店铺id 
	* @apiParam {number} STAFF_ID				收银员id 
	* @apiParam {number} STORE_ID				店铺id
	* @apiParam {number} DAILY_ID				日结id  
	* @apiParam {string} TYPE					1 销售订单 2退款订单  
	* @apiParam {string} START_TIME				开始时间
	* @apiParam {string} END_TIME				结束时间 

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 查询日结记录列表
    * @apiSuccess {string} obj.STORE_NAME		门店名称
    * @apiSuccess {string} obj.STAFF_NAME	           收银员
    * @apiSuccess {number} obj.SALES_MONEY_TOTAL	销售总额
    * @apiSuccess {number} obj.SALES_NUM			销售单数
    * @apiSuccess {number} obj.REFUND_TOTAL		退款总额
    * @apiSuccess {number} obj.REFUND_NUM		退款单数
    * @apiSuccess {string} obj.START_TIMES		开始时间
    * @apiSuccess {string} obj.END_TIMES	结束时间
    * 
    */
	@RequestMapping(value = "/daily_detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryDailyDetail(HttpServletRequest request) {
		return Transform.GetResult(dailyHandOverService.queryDailyDetail(request));
	}
	
	/**
    *
    * @api {post} /{project_name}/daily_handover/handover_detail  查询交接班详情
    * @apiGroup daily_handover
    * @apiName handover_detail
    * @apiDescription 查询交接班详情
    * @apiVersion 0.0.1
	
	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条   
	* @apiParam {number} AGENT_ID				店铺id 
	* @apiParam {number} STAFF_ID				收银员id 
	* @apiParam {number} STORE_ID				店铺id
	* @apiParam {number} WORK_ID				交接班id  
	* @apiParam {string} TYPE					1 销售订单 2退款订单  
	* @apiParam {string} START_TIME				开始时间
	* @apiParam {string} END_TIME				结束时间 

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 查询日结记录列表
    * @apiSuccess {string} obj.STORE_NAME		门店名称
    * @apiSuccess {string} obj.STAFF_NAME	           收银员
    * @apiSuccess {number} obj.SALES_MONEY_TOTAL	销售总额
    * @apiSuccess {number} obj.SALES_NUM			销售单数
    * @apiSuccess {number} obj.REFUND_TOTAL		退款总额
    * @apiSuccess {number} obj.REFUND_NUM		退款单数
    * @apiSuccess {string} obj.START_TIMES		开始时间
    * @apiSuccess {string} obj.END_TIMES	结束时间
    * 
    */
	@RequestMapping(value = "/handover_detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryHandoverDetail(HttpServletRequest request) {
		return Transform.GetResult(dailyHandOverService.queryHandoverDetail(request));
	}

}
