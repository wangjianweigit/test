package com.tk.oms.analysis.control;

import com.tk.oms.analysis.service.SalesAchievementService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (c), 2018, TongKu
 * FileName : SalesAchievementControl
 * 销售人员绩效相关统计接口访问层
 *
 * @author zhenghui
 * @version 1.00
 * @date 2018-7-10
 */
@Controller
@RequestMapping("/sales_achievement")
public class SalesAchievementControl {

    @Resource
    private SalesAchievementService salesAchievementService;

    /**
     * @api {post} /{project_name}/sales_achievement/list 查询销售人员绩效分析列表
     * @apiName list
     * @apiGroup sales_achievement
     * @apiDescription  查询销售人员绩效分析列表
     * @apiVersion 0.0.1
     *
     * @apiParam {number} pageIndex 		开始页码
     * @apiParam {number} pageSize 			每页数据量
     * @apiParam {number} type 				1-会员等级达标统计规则,2-休眠会员激活统计规则,3-新增有效会员统计规则

     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 查询入驻商申请数据列表
     * @apiSuccess {string} 	obj.manager_user_realname 业务经理
     * @apiSuccess {string} 	obj.store_name 门店
     * @apiSuccess {string} 	obj.referee_user_realname 业务员
     * @apiSuccess {number} 	obj.member_count 会员数
     *
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryListForPage(HttpServletRequest request){
        return Transform.GetResult(salesAchievementService.queryListForPage(request));
    }

    /**
     * @api {post} /{project_name}/sales_achievement/member/list 分页查询会员列表
     * @apiGroup sales_achievement
     * @apiDescription  分页查询会员列表
     * @apiVersion 0.0.1
     *
     * @apiParam {number} pageIndex 开始页码
     * @apiParam {number} pageSize 每页数据量
     * @apiParam {number} store_id 门店ID
     * @apiParam {number} [ywy_user_id] 业务员ID
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * @apiSuccess {object[]} obj 接口返回数据集合
     * @apiSuccess {number} obj.user_state 用户状态   1.启用    2.禁用    4.预审通过
     * @apiSuccess {string} obj.login_name 登录名称，用于登录
     * @apiSuccess {string} obj.user_manage_name 负责人姓名
     * @apiSuccess {date} obj.create_date 创建时间
     */
    @RequestMapping(value = "/member/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberListForPage(HttpServletRequest request){
        return Transform.GetResult(this.salesAchievementService.queryMemberListForPage(request));
    }

    /**
     * @api {post} /{project_name}/sales_achievement/effective_member/detail 查询新增有效会员详细信息
     * @apiGroup sales_achievement
     * @apiDescription  查询新增有效会员详细信息
     * @apiVersion 0.0.1
     *
     * @apiParam {number} pageIndex 开始页码
     * @apiParam {number} pageSize 每页数据量
     * @apiParam {number} rule_type 1-会员等级达标统计规则,2-休眠会员激活统计规则,3-新增有效会员统计规则
     * @apiParam {number} store_id 门店ID
     * @apiParam {number} [ywy_user_id] 业务员ID
     * @apiParam {number} [start_date] 开始时间，格式yyyy-MM-dd HH:mm:ss
     * @apiParam {number} [end_date] 结束时间，格式yyyy-MM-dd HH:mm:ss
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * @apiSuccess {object[]} obj 接口返回数据集合
     * @apiSuccess {number} obj.order_number 订单号
     * @apiSuccess {date} obj.payment_date 支付时间
     * @apiSuccess {number} obj.order_state 订单状态
     * @apiSuccess {date} obj.last_login_date 最后登录时间
     * @apiSuccess {number} obj.user_state 用户状态   1.启用    2.禁用    4.预审通过
     * @apiSuccess {string} obj.login_name 登录名称，用于登录
     * @apiSuccess {string} obj.user_manage_name 负责人姓名
     * @apiSuccess {date} obj.create_date 创建时间
     */
    @RequestMapping(value = "/effective_member/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryEffectiveMemberDetail(HttpServletRequest request) {
        return Transform.GetResult(this.salesAchievementService.queryEffectiveMemberDetail(request));
    }

    /**
     * @api {post} /{project_name}/sales_achievement/rule_detail 统计规则详情
     * @apiGroup sales_achievement
     * @apiDescription 查询统计规则详情
     *
     * @apiParam {number} rule_type 1-会员等级达标统计规则,2-休眠会员激活统计规则,3-新增有效会员统计规则
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * @apiSuccess {object[]} obj 接口返回数据集合
     * @apiSuccess {number} obj.id 主键ID
     * @apiSuccess {number} obj.grade_name 级别名称
     * @apiSuccess {string} obj.money 采购金额
     * @apiSuccess {number} obj.return_rate 退货率
     * @apiParam {number} obj.sleep_date_type 沉睡时间类型(1-年，-2月，3-日)
     * @apiParam {number} obj.sleep_date_value 沉睡间隔
     * @apiParam {number} obj.active_date_type 有效激活类型(1-年，-2月，3-日)
     * @apiParam {number} obj.active_date_value 有效激活间隔
     */
    @RequestMapping(value = "/rule_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySalesAchieveRuleDetail(HttpServletRequest request) {
        return Transform.GetResult(this.salesAchievementService.querySalesAchieveRuleDetail(request));
    }

    /**
     * @api {post} /{project_name}/sales_achievement/setting_rule 设置统计规则
     * @apiGroup sales_achievement
     * @apiDescription 批量新增或编辑统计规则
     *
     * @apiParam {number} type 1-会员等级达标统计规则,2-休眠会员激活统计规则,3-新增有效会员统计规则
     * @apiParam {object[]} rule_list 数据集合
     * @apiParam {number} rule_list.rule_id 主键ID
     * @apiParam {string} rule_list.grade_name 级别名称
     * @apiParam {number} rule_list.money 采购金额
     * @apiParam {number} rule_list.return_rate 退货率
     * @apiParam {number} rule_list.sleep_date_type 沉睡时间类型(1-年，-2月，3-日)
     * @apiParam {number} rule_list.sleep_date_value 沉睡间隔
     * @apiParam {number} rule_list.active_date_type 有效激活类型(1-年，-2月，3-日)
     * @apiParam {number} rule_list.active_date_value 有效激活间隔
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     */
    @RequestMapping(value = "/setting_rule", method = RequestMethod.POST)
    @ResponseBody
    public Packet batchAddOrEditRule(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.salesAchievementService.batchAddOrEditRule(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/sales_achievement/remove_rule 删除统计规则
     * @apiGroup sales_achievement
     * @apiDescription 删除统计规则
     *
     * @apiParam {number} rule_id 主键ID
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     */
    @RequestMapping(value = "/remove_rule", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeRule(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.salesAchievementService.removeRule(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return Transform.GetResult(pr);
    }
    
    /**
     * @api {post} /{project_name}/sales_achievement/member_sleep_detail_list 查询休眠会员详情
     * @apiGroup sales_achievement
     * @apiDescription  查询休眠会员详情
     * @apiVersion 0.0.1
     *
     * @apiParam {number} pageIndex 开始页码
     * @apiParam {number} pageSize 每页数据量
     * @apiParam {number} store_id 门店ID
     * @apiParam {number} [ywy_user_id] 业务员ID
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * @apiSuccess {object[]} obj 接口返回数据集合
     * @apiSuccess {number} obj.USER_STATE 用户状态   1.启用    2.禁用    4.预审通过
     * @apiSuccess {string} obj.LOGIN_NAME 用户名
     * @apiSuccess {string} obj.USEER_MANAGE_NAME 姓名
     * @apiSuccess {date} obj.LAST_LOGIN_DATE 最后登录时间
     * @apiSuccess {number} obj.SLEEP_COUNT 沉睡天数
     */
    @RequestMapping(value = "member_sleep_detail_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet memberSleepDetailList(HttpServletRequest request) {
        return Transform.GetResult(this.salesAchievementService.memberSleepDetailList(request));
    }
    
    /**
     * @api {post} /{project_name}/sales_achievement/member_active_detail_list 查询激活会员详情
     * @apiGroup sales_achievement
     * @apiDescription 查询激活会员详情
     * @apiVersion 0.0.1
     *
     * @apiParam {number} pageIndex 开始页码
     * @apiParam {number} pageSize 每页数据量
     * @apiParam {number} store_id 门店ID
     * @apiParam {number} [ywy_user_id] 业务员ID
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * @apiSuccess {object[]} obj 接口返回数据集合
     * @apiSuccess {string} obj.LOGIN_NAME 用户名
     * @apiSuccess {string} obj.USEER_MANAGE_NAME 姓名
     * @apiSuccess {date} obj.LAST_LOGIN_DATE 最后登录时间
     * @apiSuccess {number} obj.SLEEP_COUNT 沉睡天数
     * @apiSuccess {number} obj.MONEY 采购金额
     */
    @RequestMapping(value = "member_active_detail_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet memberActiveDetailList(HttpServletRequest request) {
        return Transform.GetResult(this.salesAchievementService.memberActiveDetailList(request));
    }
    
    /**
     * @api {post} /{project_name}/sales_achievement/member_effect_active_detail_list 查询有效激活会员详情
     * @apiGroup sales_achievement
     * @apiDescription 查询有效激活会员详情
     * @apiVersion 0.0.1
     *
     * @apiParam {number} pageIndex 开始页码
     * @apiParam {number} pageSize 每页数据量
     * @apiParam {number} store_id 门店ID
     * @apiParam {number} [ywy_user_id] 业务员ID
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * @apiSuccess {object[]} obj 接口返回数据集合
     * @apiSuccess {string} obj.LOGIN_NAME 用户名
     * @apiSuccess {string} obj.USEER_MANAGE_NAME 姓名
     * @apiSuccess {date} obj.LAST_LOGIN_DATE 最后登录时间
     * @apiSuccess {number} obj.SLEEP_COUNT 沉睡天数
     * @apiSuccess {number} obj.MONEY 采购金额
     */
    @RequestMapping(value = "member_effect_active_detail_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet memberEffectActiveDetailList(HttpServletRequest request) {
        return Transform.GetResult(this.salesAchievementService.memberEffectActiveDetailList(request));
    }
}
