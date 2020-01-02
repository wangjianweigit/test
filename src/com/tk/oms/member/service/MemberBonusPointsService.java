package com.tk.oms.member.service;

import com.tk.oms.member.dao.MemberBonusPointsDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Copyright (c), 2017, Tongku
 * FileName : MemberBonusPointsService
 * 会员积分业务操作类
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/4/20 11:14
 */
@Service
public class MemberBonusPointsService {

    @Resource
    private MemberBonusPointsDao memberBonusPointsDao;//会员消费积分数据访问接口

    /**
     * 分页查询积分用户记录总表
     *
     * @param request
     * @return
     */
    public GridResult queryList(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            //分页参数
            GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            //记录数据
            List<Map<String, Object>>  list = memberBonusPointsDao.queryMemberBonusPointsList(paramMap);
            int count = memberBonusPointsDao.queryMemberBonusPointsCount(paramMap);
            if (list != null) {
                gr.setState(true);
                gr.setMessage("获取成功");
                gr.setObj(list);
                gr.setTotal(count);
            } else {
                gr.setState(true);
                gr.setMessage("无数据");
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
    }

    /**
     * 分页查询积分详情记录总表
     *
     * @param request
     * @return
     */
    public GridResult queryScoreDetailList(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            //分页参数
            GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            if (!paramMap.containsKey("login_name") || StringUtils.isEmpty(paramMap.get("login_name"))) {
                gr.setState(false);
                gr.setMessage("缺少参数login_name");
                return gr;
            }
            //数据列表
            List<Map<String, Object>> list = memberBonusPointsDao.queryMemberScoreDetailList(paramMap);
            //数据记录数
            int count = memberBonusPointsDao.queryMemberScoreDetailCount(paramMap);

            if (list != null) {
                gr.setState(true);
                gr.setMessage("获取成功");
                gr.setObj(list);
                gr.setTotal(count);
            } else {
                gr.setState(true);
                gr.setMessage("无数据");
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
    }
}
