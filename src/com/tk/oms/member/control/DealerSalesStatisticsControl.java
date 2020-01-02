package com.tk.oms.member.control;

import com.tk.oms.member.service.DealerSalesStatisticsService;
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
 * FileName : DealerSalesStatisticsControl
 * 经销商销售报表统计Control
 * 
 * @author wangjianwei
 * @version 1.00
 * @date 2017/11/21 9:01
 */
@Controller
@RequestMapping("/dealer_sales")
public class DealerSalesStatisticsControl {

    @Resource
    private DealerSalesStatisticsService dealerSalesStatisticsService;

    /**
     * @api{post} /{oms_server}/dealer_sales/list 获取经销商销售列表
     * @apiGroup dealer_sales
     * @apiName dealer_sales_list
     * @apiDescription 分页经销商销售列表数据
     * @apiVersion 0.0.1
     *
     * @apiParam {string}  current_time_start      初始时间
     * @apiParam {string}  current_time_end        结束时间
     * @apiParam {string}  prev_time_start         初始时间
     * @apiParam {string}  prev_time_end           结束时间
     *
     * @apiSuccess {boolean}  state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}   message 接口返回信息
     * @apiSuccess {object}   obj 经销商销售列表列表集合
     * @apiSuccess {number }   obj.ACCOUNT               经销商账户数组
     * @apiSuccess {number }   obj.ORDER_TOTAL           当期销售订单数
     * @apiSuccess {number }   obj.SALE_COUNT            当期销售商品数
     * @apiSuccess {number }   obj.PAY_TOTAL             当期销售金额
     * @apiSuccess {number }   obj.REFUND_MONEY          当期退货数
     * @apiSuccess {number }   obj.REFUND_NUM            当期退货金额
     * @apiSuccess {number }   obj.MEMBER_ADD            当期新增会员数
     * @apiSuccess {number }   obj.VISIT_COUNT           当期微商城访客数
     * @apiSuccess {number }   obj.SCAN_COUNT            当期微商城浏览量
     * @apiSuccess {number }   obj.PREV_ORDER_TOTAL      上期销售订单数
     * @apiSuccess {number }   obj.PREV_SALE_COUNT       上期销售商品数
     * @apiSuccess {number }   obj.PREV_PAY_TOTAL        上期销售金额
     * @apiSuccess {number }   obj.PREV_REFUND_MONEY     上期退货数
     * @apiSuccess {number }   obj.PREV_REFUND_NUM       上期退货金额
     * @apiSuccess {number }   obj.PREV_MEMBER_ADD       上期新增会员数
     * @apiSuccess {number }   obj.PREV_VISIT_COUNT      上期微商城访客数
     * @apiSuccess {number }   obj.PREV_SCAN_COUNT       上期微商城浏览量
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDealerSalesListForPage(HttpServletRequest request) {
        return Transform.GetResult(this.dealerSalesStatisticsService.queryDealerSalesListForPage(request));
    }

    /**
     * @api{post} /{oms_server}/dealer_sales/order_detail 获取经销商订单销售列表
     * @apiGroup dealer_sales
     * @apiName dealer_sales_order_detail
     * @apiDescription 获取经销商订单销售列表
     * @apiVersion 0.0.1
     *
     * @apiParam {number} accounts      经销商账户
     * @apiParam {char}  time_start     初始时间
     * @apiParam {char}  time_end       结束时间
     *
     * @apiSuccess {boolean}  state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}   message 接口返回信息
     * @apiSuccess {object}   obj 经销商订单销售列表集合
     * @apiSuccess {number }   obj.STORE_SALE        门店销售订单数
     * @apiSuccess {number }   obj.STORE_SEND_SALE   门店自发订单数
     * @apiSuccess {number }   obj.DAIFA_SALE        云仓代发订单数
     * @apiSuccess {number }   obj.WMALL_SALE        微商城订单数
     */
    @RequestMapping(value = "/order_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDealerSalesOrderDetailList(HttpServletRequest request) {
        return Transform.GetResult(this.dealerSalesStatisticsService.queryDealerSalesOrderDetailList(request));
    }

    /**
     * @api{post} /{oms_server}/dealer_sales/product_detail 获取经销商销售商品明细列表
     * @apiGroup dealer_sales
     * @apiName dealer_sales_product_detail
     * @apiDescription 获取经销商销售商品明细列表
     * @apiVersion 0.0.1
     *
     * @apiParam {number} accounts      经销商账户
     * @apiParam {char}  time_start     初始时间
     * @apiParam {char}  time_end       结束时间
     *
     * @apiSuccess {boolean}  state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}   message 接口返回信息
     * @apiSuccess {object}   obj 经销商销售商品明细列表集合
     * @apiSuccess {number }   obj.STORE_SALE_COUNT         门店销售商品数
     * @apiSuccess {number }   obj.STORE_SEND_SALE_COUNT    门店自发商品数
     * @apiSuccess {number }   obj.DAIFA_SALE_COUNT         云仓代发商品数
     * @apiSuccess {number }   obj.WMALL_SALE_COUNT         微商城商品数
     * @apiSuccess {number }   obj.SELF_PRODUCT_ADD         自建商品新增数
     * @apiSuccess {number }   obj.DAIFA_PRODUCT_ADD        批发商品新增数
     */
    @RequestMapping(value = "/product_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDealerSalesProductDetailList(HttpServletRequest request) {
        return Transform.GetResult(this.dealerSalesStatisticsService.queryDealerSalesProductDetailList(request));
    }

    /**
     * @api{post} /{oms_server}/dealer_sales/money_detail 获取经销商销售金额明细列表
     * @apiGroup dealer_sales
     * @apiName dealer_sales_money_detail
     * @apiDescription 获取经销商销售金额明细列表
     * @apiVersion 0.0.1
     *
     * @apiParam {number} accounts      经销商账户
     * @apiParam {char}  time_start     初始时间
     * @apiParam {char}  time_end       结束时间
     *
     * @apiSuccess {boolean}  state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}   message 接口返回信息
     * @apiSuccess {object}   obj 经销商订单销售列表集合
     * @apiSuccess {number }   obj.USE_BALANCE          余额支付
     * @apiSuccess {number }   obj.PAY_CASH             现金支付
     * @apiSuccess {number }   obj.PAY_ALIPAY           线上支付宝支付
     * @apiSuccess {number }   obj.PAY_POS              线上pos
     * @apiSuccess {number }   obj.PAY_SELF_ALIPAY      线上微信支付
     * @apiSuccess {number }   obj.PAY_SELF_WXPAY       门店微信
     * @apiSuccess {number }   obj.PAY_SELF_POS         门店pos
     */
    @RequestMapping(value = "/money_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDealerSalesMoneyDetailList(HttpServletRequest request) {
        return Transform.GetResult(this.dealerSalesStatisticsService.queryDealerSalesMoneyDetailList(request));
    }

    /**
     * @api{post} /{oms_server}/dealer_sales/scan_detail 获取经销商微商城商品浏览排行明细
     * @apiGroup dealer_sales
     * @apiName dealer_sales_scan_detail
     * @apiDescription 获取经销商微商城商品浏览排行明细
     * @apiVersion 0.0.1
     *
     * @apiParam {number} accounts      经销商账户
     * @apiParam {char}  time_start     初始时间
     * @apiParam {char}  time_end       结束时间
     *
     * @apiSuccess {boolean}  state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}   message 接口返回信息
     * @apiSuccess {object}   obj 经销商微商城商品浏览排行明细列表集合
     */
    @RequestMapping(value = "/scan_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryScanDetailList(HttpServletRequest request) {
        return Transform.GetResult(this.dealerSalesStatisticsService.queryScanDetailList(request));
    }


    /**
     * @api{post} /{oms_server}/dealer_sales/visit_detail 获取经销商微商城访客数排行明细
     * @apiGroup dealer_sales
     * @apiName dealer_sales_visit_detail
     * @apiDescription 获取经销商微商城访客数排行明细
     * @apiVersion 0.0.1
     *
     * @apiParam {number} accounts      经销商账户
     * @apiParam {char}  time_start     初始时间
     * @apiParam {char}  time_end       结束时间
     *
     * @apiSuccess {boolean}  state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}   message 接口返回信息
     * @apiSuccess {object}   obj 经销商微商城访客数排行明细列表集合
     */
    @RequestMapping(value = "/visit_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryVisitDetailList(HttpServletRequest request) {
        return Transform.GetResult(this.dealerSalesStatisticsService.queryVisitDetailList(request));
    }

    /**
     * @api{post} /{oms_server}/dealer_sales/conversion_rate_detail 获取经销商会员下单转换率明细列表
     * @apiGroup dealer_sales
     * @apiName dealer_sales_conversion_rate_detail
     * @apiDescription 获取经销商会员下单转换率明细列表
     * @apiVersion 0.0.1
     *
     * @apiParam {number} accounts      经销商账户
     * @apiParam {char}  time_start     初始时间
     * @apiParam {char}  time_end       结束时间
     *
     * @apiSuccess {boolean}  state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}   message 接口返回信息
     * @apiSuccess {object}   obj 经销商会员下单转换率明细列表集合
     */
    @RequestMapping(value = "/conversion_rate_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryConversionRateDetailDetailList(HttpServletRequest request) {
        return Transform.GetResult(this.dealerSalesStatisticsService.queryConversionRateDetailDetailList(request));
    }

}
