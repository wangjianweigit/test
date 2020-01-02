package com.tk.oms.member.service;

import com.tk.oms.member.dao.DecorationUserManageDao;
import com.tk.sys.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2017, Tongku
 * FileName : DecorationUserManageService
 * 装修用户管理业务操作类
 *
 * @author zhenghui
 * @version 1.00
 * @date 2017/09/14
 */
@Service("DecorationUserManageService")
public class DecorationUserManageService {

    @Resource
    private DecorationUserManageDao decorationUserManageDao;

    /**
     * 分页获取装修用户列表数据
     * @param request
     * @return
     */
    public GridResult queryDecorationUserListForPage(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            int total = this.decorationUserManageDao.queryDecorationUserCount(params);
            List<Map<String, Object>> list = this.decorationUserManageDao.queryDecorationUserList(params);
            if (list != null && list.size() > 0) {
                gr.setMessage("获取经销商列表成功!");
                gr.setObj(list);
            } else {
                gr.setMessage("无数据");
            }
            gr.setState(true);
            gr.setTotal(total);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return gr;
    }

    /**
     * 用户开通或关闭装修状态
     * @param request
     * @return
     */
    public ProcessResult editDecorationState(HttpServletRequest request){
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(params.get("user_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[user_id]");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("decoration_state"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[decoration_state]");
                return pr;
            }
            if (this.decorationUserManageDao.updateDecorationStateById(params) > 0) {
                pr.setState(true);
                pr.setMessage("编辑装修状态成功");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }
}
