package com.tk.oms.member.control;

import com.tk.oms.member.service.MemberBonusPointsService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (c), 2017, Tongku
 * FileName : MemberBonusPointsControl
 * 会员积分Control
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/4/20 11:14
 */
@Controller
@RequestMapping("/member_bonus_points")
public class MemberBonusPointsControl {
    @Resource
    private MemberBonusPointsService memberBonusPointsService;

    /**
     * @param request
     * @return
     * @api {post} /{project_name}/member_bonus_points/list 查询会员消费积分列表接口
     * @apiGroup bonus_points
     * @apiName bonus_points_list
     * @apiDescription 查询会员消费积分列表接口
     * @apiVersion 0.0.1
     * @apiParam {number} pageIndex 起始页 必填
     * @apiParam {number} pageSize 分页大小 必填
     * @apiParam {string} user_name 用户名 选填
     * @apiParam {String} day 加速成长查询
     * @apiSuccess {object[]} obj 会员消费积分列表
     * @apiSuccess {number} obj.USER_NAME 会员用户名
     * @apiSuccess {string} obj.LOGIN_NAME 登录名
     * @apiSuccess {string} obj.USER_MANAGE_NAME 用户负责人姓名
     * @apiSuccess {number} obj.SCORE 会员积分
     * @apiSuccess {string} obj.GRADENAME  等级名称
     * @apiSuccess {string} obj.RANKING  积分排名
     * @apiSuccess {number} total 数据总量
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息.
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryList(HttpServletRequest request) {
        return Transform.GetResult(memberBonusPointsService.queryList(request));
    }

    /**
     * @param request
     * @return
     * @api {post} /{project_name}/member_bonus_points/detail_list 查询积分详情列表接口
     * @apiGroup bonus_points
     * @apiName bonus_points_score_detail_list
     * @apiDescription 查询积分详情列表接口
     * @apiVersion 0.0.1
     * @apiParam {number} pageIndex 起始页
     * @apiParam {number} pageSize 分页大小
     * @apiParam {string} user_name 用户名 必填
     * @apiParam {String} day 加速成长查询
     * @apiSuccess {object[]} obj 积分详情列表
     * @apiSuccess {number} obj.USER_NAME 会员用户名
     * @apiSuccess {string} obj.ORDER_SOURCE 订单来源
     * @apiSuccess {string} obj.ORDER_NUMBER 关联订单号
     * @apiSuccess {string} obj.TYPE 积分类型 1.加分 2.扣分
     * @apiSuccess {string} obj.SCORE  积分数量（正数表示加分，负数表示扣分）
     * @apiSuccess {string} obj.CREATE_DATE 创建时间
     * @apiSuccess {string} obj.REMARK  备注
     * @apiSuccess {number} total 数据总量
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息.
     */
    @RequestMapping(value = "/detail_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryScoreDetailList(HttpServletRequest request) {
        return Transform.GetResult(memberBonusPointsService.queryScoreDetailList(request));
    }
}
