package com.tk.oms.analysis.control;

import com.tk.oms.analysis.service.UserDailyStatisticsService;
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
 * 
 * Copyright (c), 2017, Tongku
 * FileName : UserDailyStatisticsControl
 * 用户日统计
 * 
 * @author wangjianwei
 * @version 1.00
 * @date 2017/5/13 14:03
 */
@Controller
@RequestMapping("user_daily_statistics")
public class UserDailyStatisticsControl {

    @Resource
    private UserDailyStatisticsService userDailyStatisticsService;

    /**
     * @api {post} /{project_name}/colors/group_list
     * @apiName colors_group_list
     * @apiGroup colors
     * @apiDescription  统计查询每日统计
     * @apiVersion 0.0.1
     *
     * @apiParam {string} user_name 	    用户名
     * @apiParam {string} user_manage_name 	姓名
     * @apiParam {string} start_time 	    开始时间
     * @apiParam {string} end_time 	        结束时间
     * @apiParam {string} resource 	        来源
     * @apiParam {string} search_type 	    查询类型
     * @apiSuccess {boolean}    state       接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message     接口返回信息
     * @apiSuccess {object[]}   obj 	    流量/销售分析列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet get_DayGourp(HttpServletRequest request){
        return Transform.GetResult(userDailyStatisticsService.queryList(request));
    }


}
