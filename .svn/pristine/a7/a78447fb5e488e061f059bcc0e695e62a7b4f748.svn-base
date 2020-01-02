package com.tk.store.statistic.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.store.statistic.service.LyHomeWorkbenchService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Controller
@RequestMapping("/ly_home_work")
public class LyHomeWorkbenchControl {
	
	@Resource
	private LyHomeWorkbenchService lyHomeWorkbenchService;
	
	
	/**
    *
    * @api {post} /{project_name}/ly_home_work/query_statistic_data 查询联营工作台统计数据
    * @apiGroup ly_home_work
    * @apiDescription  查询联营工作台统计数据
    * @apiVersion 0.0.1

	* @apiParam {number} AGENT_ID				经销商的ID 
	* @apiParam {string} ITEM_ID				货号
	* @apiParam {number} YEAR					年份 
	* @apiParam {number} SEASON					季节
	* @apiParam {string} START_TIME				统计周期时间开始 
	* @apiParam {string} END_TIME				统计周期时间结束
	* @apiParam {string} TYPE					查询类型 1=库存 2=销售数 3=销售额 4=会员数 5=新增会员 6=成交会员可多传，举例[1,2,3,4,5],不传相应参数则不查询,相应的字段和值也不会返回

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {number} total 总条数
    * @apiSuccess {object[]} obj 订单列表
    * @apiSuccess {string} obj.AGENT_ID    	经销商id
    * @apiSuccess {string} obj.STORE_NAME    	门店名称
    * @apiSuccess {number} obj.STORE_ID    	门店id
    * @apiSuccess {number} obj.USER_NUM    	会员数
    * @apiSuccess {string} obj.THIS_STOCK   	当前库存
    * @apiSuccess {string} obj.NEW_USER_NUM    新增会员数
    * @apiSuccess {string} obj.SALE_NUM  		商品销售数
    * @apiSuccess {string} obj.SALE_TOTAL  	销售额
    * @apiSuccess {string} obj.USER_BUY_NUM  	成交会员
    * @apiSuccess {string} obj.JOINT_ID  		平台合作商下属用户id，批发平台用户的ACCOUT字段
    */
	@RequestMapping(value = "/query_statistic_data", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStatisticData(HttpServletRequest request) {
		return Transform.GetResult(lyHomeWorkbenchService.queryStatisticData(request));
	}
	
	/**
    *
    * @api {post} /{project_name}/ly_home_work/query_ly_sales_detail 查询联营工作台销售详情
    * @apiGroup ly_home_work
    * @apiDescription  查询联营工作台销售详情
    * @apiVersion 0.0.1

	* @apiParam {number} AGENT_ID				经销商的ID 
	* @apiParam {string} ITEMNUMBER				货号
	* @apiParam {number} YEAR					年份 
	* @apiParam {number} SEASON					季节
	* @apiParam {string} MIN_DATE				下单时间起 
	* @apiParam {string} MAX_DATE				下单时间止

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {number} total 总条数
    * @apiSuccess {object[]} obj 订单列表
    * @apiSuccess {string} obj.AGENT_ID    	经销商id
    * @apiSuccess {string} obj.STORE_NAME    	门店名称
    * @apiSuccess {number} obj.STORE_ID    	门店id
    * @apiSuccess {number} obj.USER_NUM    	会员数
    * @apiSuccess {string} obj.THIS_STOCK   	当前库存
    * @apiSuccess {string} obj.NEW_USER_NUM    新增会员数
    * @apiSuccess {string} obj.SALE_NUM  		商品销售数
    * @apiSuccess {string} obj.SALE_TOTAL  	销售额
    * @apiSuccess {string} obj.USER_BUY_NUM  	成交会员
    * @apiSuccess {string} obj.JOINT_ID  		平台合作商下属用户id，批发平台用户的ACCOUT字段
    */
	@RequestMapping(value = "/query_ly_sales_detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryLySalesDetail(HttpServletRequest request) {
		return Transform.GetResult(lyHomeWorkbenchService.queryLySalesDetail(request));
	}
	
	
	@RequestMapping(value = "/add_allot_task", method = RequestMethod.POST)
    @ResponseBody
    public Packet addAllotTask(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = lyHomeWorkbenchService.addAllotTask(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
	
	@RequestMapping(value = "/query_store_stock", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStoreStock(HttpServletRequest request) {
		return Transform.GetResult(lyHomeWorkbenchService.queryStoreStock(request));
	}
	
	@RequestMapping(value = "/query_store_allot_task_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStoreAllotTaskList(HttpServletRequest request) {
		return Transform.GetResult(lyHomeWorkbenchService.queryStoreAllotTaskList(request));
	}
	
	@RequestMapping(value = "/query_store_allot_task_detail_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStoreAllotTaskDetailList(HttpServletRequest request) {
		return Transform.GetResult(lyHomeWorkbenchService.queryStoreAllotTaskDetailList(request));
	}
	
	@RequestMapping(value = "/revocat_store_allot_task", method = RequestMethod.POST)
    @ResponseBody
    public Packet revocatStoreAllotTask(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = lyHomeWorkbenchService.revocatStoreAllotTask(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
	
	@RequestMapping(value = "/delete_store_allot_task_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet deleteStoreAllotTaskDetail(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = lyHomeWorkbenchService.deleteStoreAllotTaskDetail(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }

}
