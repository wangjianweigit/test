package com.tk.oms.sys.service;

import com.tk.oms.sys.dao.CacheInfoDao;
import com.tk.sys.util.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2017, Tongku
 * FileName : CacheInfoService
 * 缓存信息操作业务类
 *
 * @author zhenghui
 * @version 1.00
 * @date 2017/8/22
 */
@Service("CacheInfoService")
public class CacheInfoService {

    @Resource
    private CacheInfoDao cacheInfoDao;

    /**
     * 分页获取缓存信息列表
     * @param request
     * @return
     */
    public GridResult queryCacheInfoListForPage(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            // 查询缓存信息总条数
            int total = this.cacheInfoDao.queryListForCount(paramMap);
            // 分页查询缓存信息列表
            List<Map<String, Object>> list = this.cacheInfoDao.queryListForPage(paramMap);
            if (list != null && list.size() > 0) {
                gr.setMessage("获取缓存信息列表成功");
                gr.setObj(list);
            } else {
                gr.setState(true);
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
     * 更新缓存信息
     * @param request
     * @return
     */
    public ProcessResult updateCacheInfo(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (StringUtils.isEmpty(paramMap.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数ID");
                return pr;
            }
            if (this.cacheInfoDao.update(paramMap) > 0) {
                pr.setState(true);
                pr.setMessage("修改成功");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }
}
