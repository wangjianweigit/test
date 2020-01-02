package com.tk.oms.analysis.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.analysis.service.HomeWorkbenchService;
import com.tk.oms.analysis.service.MemberAnalysisService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;

@Controller
@RequestMapping("/home_work")
public class HomeWorkbenchControl {
	
	@Resource
	private HomeWorkbenchService homeWorkbenchService;
	
	/**
     * @api {post} /{project_name}/home_work/query_order_info 首页工作台查询订单信息
     * @apiName query_order_info
     * @apiGroup home_work
     * @apiDescription  首页工作台查询订单信息
     * @apiVersion 0.0.1
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 查询数据列表
     * @apiSuccess {number}   	obj.order_count 今日下单数
     * @apiSuccess {number}   	obj.pay_money 今日已付款钱
     * @apiSuccess {number}   	obj.wait_pay_count 待付款订单数
     * @apiSuccess {number} 	obj.wait_receive_count 待收货数
     */
	@RequestMapping(value = "/query_order_info", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryHomeWorkOrderInfo(HttpServletRequest request){
        return Transform.GetResult(this.homeWorkbenchService.queryHomeWorkOrderInfo(request));
    }
	
	/**
     * @api {post} /{project_name}/home_work/query_member_transactions 首页工作台会员成交信息
     * @apiName query_member_transactions
     * @apiGroup home_work
     * @apiDescription  首页工作台会员成交信息
     * @apiVersion 0.0.1
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 查询数据列表
     * @apiSuccess {string}   	obj.login_name 用户名
     * @apiSuccess {string}   	obj.user_manage_name 姓名
     * @apiSuccess {number}   	obj.payment_count 成交数
     * @apiSuccess {number}   	obj.payment_money 成交金额
     */
	@RequestMapping(value = "/query_member_transactions", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryHomeWorkMemberTransactions(HttpServletRequest request){
        return Transform.GetResult(this.homeWorkbenchService.queryHomeWorkMemberTransactions(request));
    }
	/**
     * @api {post} /{project_name}/home_work/query_unusual_member 首页工作台异常会员信息
     * @apiName query_unusual_member
     * @apiGroup home_work
     * @apiDescription  首页工作台异常会员信息
     * @apiVersion 0.0.1
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 查询数据列表
     * @apiSuccess {string}   	obj.login_name 用户名
     * @apiSuccess {string}   	obj.user_manage_name 姓名
     * @apiSuccess {number}   	obj.login_count 登录次数
     * @apiSuccess {number}   	obj.ip_count   ip数
     * @apiSuccess {number}   	obj.pv_count 浏览量
     * @apiSuccess {number}   	obj.payment_money 成交金额
     */
	@RequestMapping(value = "/query_unusual_member", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryHomeWorkUnusualMember(HttpServletRequest request){
        return Transform.GetResult(this.homeWorkbenchService.queryHomeWorkUnusualMember(request));
    }
	/**
     * @api {post} /{project_name}/home_work/query_member_store_address 首页工作台会员门店地址信息
     * @apiName query_member_store_address
     * @apiGroup home_work
     * @apiDescription  首页工作台会员门店地址信息
     * @apiVersion 0.0.1
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 查询数据列表
     * @apiSuccess {string}   	obj.login_name 用户名
     * @apiSuccess {string}   	obj.user_manage_name 姓名
     * @apiSuccess {number}   	obj.longitude 经度
     * @apiSuccess {number}   	obj.latitude 维度
     */
	@RequestMapping(value = "/query_member_store_address", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryHomeWorkMemberStoreAddress(HttpServletRequest request){
        return Transform.GetResult(this.homeWorkbenchService.queryHomeWorkMemberStoreAddress(request));
    }
	/**
     * @api {post} /{project_name}/home_work/query_region_sale 首页工作台查询区域销售
     * @apiName query_region_sale
     * @apiGroup home_work
     * @apiDescription  首页工作台查询区域销售
     * @apiVersion 0.0.1
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 查询数据列表
     * @apiSuccess {number}   	obj.add_user_count 新增客户数
     * @apiSuccess {number}   	obj.payment_count 今日成交量
     * @apiSuccess {number}   	obj.payment_money 今日成交金额
     */
	@RequestMapping(value = "/query_region_sale", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryHomeWorkMemberRegionSale(HttpServletRequest request){
        return Transform.GetResult(this.homeWorkbenchService.queryHomeWorkMemberRegionSale(request));
    }
	/**
     * @api {post} /{project_name}/home_work/query_member_activity 首页工作台会员活跃度
     * @apiName query_member_activity
     * @apiGroup home_work
     * @apiDescription  首页工作台会员活跃度
     * @apiVersion 0.0.1
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 查询数据列表
     * @apiSuccess {number}   	obj.not_login_user_count 未登录人数
     * @apiSuccess {number}   	obj.login_user_count 登录人数
     * @apiSuccess {number}   	obj.transaction_user_count 成交人数
     */
	@RequestMapping(value = "/query_member_activity", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryHomeWorkMemberActivity(HttpServletRequest request){
        return Transform.GetResult(this.homeWorkbenchService.queryHomeWorkMemberActivity(request));
    }
	
	/**
     * @api {post} /{project_name}/home_work/query_member_activity 首页工作台查询会员数
     * @apiName query_member_count
     * @apiGroup home_work
     * @apiDescription  首页工作台查询会员数
     * @apiVersion 0.0.1
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 查询数据列表
     * @apiSuccess {number}   	obj.member_count 会员数
     */
	@RequestMapping(value = "/query_member_count", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryHomeWorkMemberCount(HttpServletRequest request){
        return Transform.GetResult(this.homeWorkbenchService.queryHomeWorkMemberCount(request));
    }

}
