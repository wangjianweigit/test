package com.tk.oms.marketing.control;

import com.tk.oms.marketing.service.RefillCardService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (c), 2018,  TongKu
 * FileName : RefillCardControl
 * 会员充值卡相关
 * @author: zhengfy
 * @version: 1.00
 * @date: 2018/7/4
 */
@Controller
@RequestMapping("refill_card")
public class RefillCardControl {

    @Resource
    private RefillCardService refillCardService;


    /**
     * @api {post} /{project_name}/refill_card/list 充值卡列表
     * @apiGroup list
     * @apiName refill_card
     * @apiDescription  充值卡列表
     * @apiVersion 0.0.1
     *
     * @apiParam {number} pageIndex 开始页码
     * @apiParam {number} pageSize 分页大小
     * @apiParam {string} [refill_card_name] 充值卡名称
     * @apiParam {string} [user_type] 用户类型
     * @apiParam {number} [state] 状态
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * @apiSuccess {number} total 总条数
     * @apiSuccess {object[]} obj
     * @apiSuccess {number} obj.STATE               充值卡状态
     *                                              1：草稿(状态显示“草稿”，启用状态显示“禁用”；可编辑，不可销售))
     *                                              2：已发布(状态显示“已发布”，启用状态显示“启用”；不可编辑，可销售)
     *                                              3：禁用(状态显示“已发布”，启用状态显示“禁用”；不可编辑，不可销售) "
     * @apiSuccess {string} obj.REFILL_CARD_NAME    充值卡名称
     * @apiSuccess {number} obj.COUNT               数量
     * @apiSuccess {number} obj.ID                  ID
     * @apiSuccess {number} obj.USER_TYPE           允许购买的用户类型
     * @apiSuccess {number} obj.REFILL_CARD_AMOUNT  充值卡额度
     * @apiSuccess {number} obj.EXPIRY_PERIOD       充值卡有效期，单位（月）
     * @apiSuccess {number} obj.REFILL_CARD_PRICE   充值卡售价
     * @apiSuccess {string} obj.SELL_START_DATE     销售开始时间
     * @apiSuccess {string} obj.SELL_END_DATE       销售结束时间
     *
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryList(HttpServletRequest request) {
        return Transform.GetResult(refillCardService.queryList(request));
    }

    /**
     * @api {post} /{project_name}/refill_card/detail 充值卡详情
     * @apiGroup list
     * @apiName refill_card
     * @apiDescription  充值卡列表
     * @apiVersion 0.0.1
     *
     * @apiParam {number} id 充值卡ID
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * @apiSuccess {number} total 总条数
     * @apiSuccess {object[]} obj
     * @apiSuccess {number} obj.STATE               充值卡状态
     *                                              1：草稿(状态显示“草稿”，启用状态显示“禁用”；可编辑，不可销售))
     *                                              2：已发布(状态显示“已发布”，启用状态显示“启用”；不可编辑，可销售)
     *                                              3：禁用(状态显示“已发布”，启用状态显示“禁用”；不可编辑，不可销售) "
     * @apiSuccess {string} obj.REFILL_CARD_NAME    充值卡名称
     * @apiSuccess {number} obj.ID                  ID
     * @apiSuccess {number} obj.USER_TYPE           允许购买的用户类型
     * @apiSuccess {number} obj.REFILL_CARD_AMOUNT  充值卡额度
     * @apiSuccess {number} obj.EXPIRY_PERIOD       充值卡有效期，单位（月）
     * @apiSuccess {number} obj.REFILL_CARD_PRICE   充值卡售价
     * @apiSuccess {string} obj.SELL_START_DATE     销售开始时间
     * @apiSuccess {string} obj.SELL_END_DATE       销售结束时间
     *
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDetail(HttpServletRequest request) {
        return Transform.GetResult(refillCardService.queryDetail(request));
    }


    /**
     * @api {post} /{project_name}/refill_card/edit 编辑充值卡
     * @apiGroup edit
     * @apiName refill_card
     * @apiDescription  编辑充值卡
     * @apiVersion 0.0.1
     *
     * @apiParam {string} ID
     * @apiParam {string} [REFILL_CARD_NAME] 充值卡名称
     * @apiParam {string} [REFILL_CARD_AMOUNT] 充值卡额度
     * @apiParam {string} [REFILL_CARD_PRICE] 充值卡售价
     * @apiParam {string} [EXPIRY_PERIOD] 充值卡有效期，单位（月）
     * @apiParam {string} [USER_TYPE] 允许购买的用户类型
     * @apiParam {string} [SELL_START_DATE] 销售开始时间
     * @apiParam {string} [SELL_END_DATE] 销售结束时间
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     *
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet edit(HttpServletRequest request) {
        return Transform.GetResult(refillCardService.edit(request));
    }

    /**
     * @api {post} /{project_name}/refill_card/remove 删除充值卡
     * @apiGroup remove
     * @apiName refill_card
     * @apiDescription  删除充值卡
     * @apiVersion 0.0.1
     *
     * @apiParam {string} id
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     *
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet remove(HttpServletRequest request) {
        return Transform.GetResult(refillCardService.remove(request));
    }

    /**
     * @api {post} /{project_name}/refill_card/edit_state 更改充值卡状态
     * @apiGroup edit_state
     * @apiName refill_card
     * @apiDescription  更改充值卡状态
     * @apiVersion 0.0.1
     *
     * @apiParam {string} id
     * @apiParam {string} state 状态
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     *
     */
    @RequestMapping(value = "/edit_state", method = RequestMethod.POST)
    @ResponseBody
    public Packet editState(HttpServletRequest request) {
        return Transform.GetResult(refillCardService.editState(request));
    }

    /**
     * @api {post} /{project_name}/refill_card/sale_detail 查询销售记录
     * @apiGroup sale_detail
     * @apiName refill_card
     * @apiDescription  查询销售记录
     * @apiVersion 0.0.1
     *
     * @apiParam {number} pageIndex 开始页码
     * @apiParam {number} pageSize 分页大小
     * @apiParam {string} id id
     *
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * @apiSuccess {number} total 总条数
     *
     * @apiSuccess {object}   obj
     * @apiSuccess {string}   obj.LOGIN_NAME 用户名
     * @apiSuccess {string}   obj.REAL_NAME  姓名
     * @apiSuccess {string}   obj.CARD_NUM   售卡数量
     * @apiSuccess {string}   obj.CREATE_DATE 销售时间
     *
     */
    @RequestMapping(value = "/sale_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySaleDetail(HttpServletRequest request) {
        return Transform.GetResult(refillCardService.querySaleDetail(request));
    }

    /**
     * @api {post} /{project_name}/refill_card/buy_card_detail 查询购卡记录
     * @apiGroup buy_card_detail
     * @apiName refill_card
     * @apiDescription  查询购卡记录
     * @apiVersion 0.0.1
     *
     *
     * @apiParam {number} pageIndex 开始页码
     * @apiParam {number} pageSize 分页大小
     * @apiParam {string} login_name 用户名
     * @apiParam {string} real_name 姓名
     * @apiParam {string} begin_time 起始时间
     * @apiParam {string} end_time  结束时间
     *
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * @apiSuccess {number} total 总条数
     *
     * @apiSuccess {object}   obj
     * @apiSuccess {string}   obj.MBR_CARD_ID 会员卡ID
     * @apiSuccess {string}   obj.LOGIN_NAME 用户名
     * @apiSuccess {string}   obj.REAL_NAME  姓名
     * @apiSuccess {string}   obj.REFILL_CARD_AMOUNT   累计额度
     * @apiSuccess {string}   obj.PAYMENT_MONEY   累计支付金额
     * @apiSuccess {string}   obj.CARD_NUM   累计购卡数量
     * @apiSuccess {string}   obj.CARD_BALANCE   会员卡余额
     * @apiSuccess {string}   obj.EXPIRATION_DATE 过期时间
     *
     */
    @RequestMapping(value = "/buy_card_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryBuyCardDetail(HttpServletRequest request) {
        return Transform.GetResult(refillCardService.queryBuyCardDetail(request));
    }


    /**
     * @api {post} /{project_name}/refill_card/query_use_record 查询收支记录
     * @apiGroup query_use_record
     * @apiName refill_card
     * @apiDescription  查询收支记录
     * @apiVersion 0.0.1
     *
     *
     * @apiParam {number} pageIndex 开始页码
     * @apiParam {number} pageSize 分页大小
     * @apiParam {string} mbr_card_id 会员卡ID
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * @apiSuccess {number} total 总条数
     *
     * @apiSuccess {object}   obj
     * @apiSuccess {string}   obj.TYPE 类型
     * @apiSuccess {string}   obj.USED_AMOUNT 使用额度
     * @apiSuccess {string}   obj.REMARK  备注
     * @apiSuccess {string}   obj.CREATE_DATE   创建日期
     *
     */
    @RequestMapping(value = "/balance_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryBalanceDetail(HttpServletRequest request) {
        return Transform.GetResult(refillCardService.queryBalanceDetail(request));
    }

    /**
     * @api {post} /{project_name}/refill_card/buy_detail 查询会员卡销售记录
     * @apiGroup buy_detail
     * @apiName refill_card
     * @apiDescription  查询会员卡销售记录
     * @apiVersion 0.0.1
     *
     * @apiParam {number} pageIndex 开始页码
     * @apiParam {number} pageSize 分页大小
     * @apiParam {string} login_name 用户名
     * @apiParam {string} real_name 姓名
     * @apiParam {string} begin_time 起始时间
     * @apiParam {string} end_time  结束时间
     * @apiParam {string} order_number  订单号码
     * @apiParam {string} ywy_user_id  业务员ID
     * @apiParam {string} ywjl_user_id  业务经理ID
     * @apiParam {string} md_id  店铺ID
     *
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * @apiSuccess {number} total 总条数
     *
     * @apiSuccess {object}   obj
     * @apiSuccess {string}   obj.ORDER_NUMBER 订单号
     * @apiSuccess {string}   obj.PAYMENT_MONEY 支付金额
     * @apiSuccess {string}   obj.PAYMENT_TYPE  收付渠道(余额、银行转账、POS刷卡、微信、支付宝)
     * @apiSuccess {string}   obj.LOGIN_NAME   用户名
     * @apiSuccess {string}   obj.REAL_NAME   姓名
     * @apiSuccess {string}   obj.REMARK   备注
     * @apiSuccess {string}   obj.SALESMAN_NAME  售卡业务员
     * @apiSuccess {string}   obj.REFILL_CARD_NAME 充值卡名称
     * @apiSuccess {string}   obj.CARD_NUM 售卡数量
     * @apiSuccess {string}   obj.CREATE_DATE 创建日期
     * @apiSuccess {string}   obj.REFEREE_USER_ID 所属业务员ID
     * @apiSuccess {string}   obj.REFEREE_USER_REALNAME 所属业务员姓名
     * @apiSuccess {string}   obj.STORE_NAME 所属门店名称
     * @apiSuccess {string}   obj.STORE_ID 所属门店ID
     * @apiSuccess {string}   obj.MARKET_SUPERVISION_USER_REALNA 所属业务经理姓名
     * @apiSuccess {string}   obj.MARKET_SUPERVISION_USER_ID 所属业务经理ID
     *
     */
    @RequestMapping(value = "/buy_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryBuyDetail(HttpServletRequest request) {
        return Transform.GetResult(refillCardService.queryBuyDetail(request));
    }



}

    
    
