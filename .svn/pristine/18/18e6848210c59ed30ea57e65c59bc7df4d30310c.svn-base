package com.tk.oms.order.service;

import com.tk.oms.order.dao.ExecOrderDao;
import com.tk.oms.order.entity.ExecOrder;
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
 * Copyright (c), 2018, TongKu
 * FileName : ExecOrderService
 * 异常订单业务类
 * @author wangjianwei
 * @version 1.00
 * @date 2019/11/27 16:25
 */
@Service
public class ExecOrderService {

    @Resource
    private ExecOrderDao execOrderDao;

    /**
     * 获取异常订单列表
     * @param request
     * @return
     */
    public GridResult listExecOrder(HttpServletRequest request){
        GridResult gr = new GridResult();
        Map<String, Object> paramMap = new HashMap<String, Object>(16);
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(! StringUtils.isEmpty(json)){
                paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            }
            GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
            if(pageParamResult!=null){
                return pageParamResult;
            }
            List<ExecOrder> list = execOrderDao.listExecOrder(paramMap);
            int count = execOrderDao.countExecOrder(paramMap);
            if (list != null && list.size() > 0) {
                gr.setMessage("获取数据成功");
                gr.setObj(list);
            } else {
                gr.setMessage("无数据");
            }
            gr.setState(true);
            gr.setTotal(count);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
    }

    /**
     * 标记异常订单
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ProcessResult markExecOrder(HttpServletRequest request) throws Exception{
        ProcessResult pr = new ProcessResult();
        Map<String, Object> paramMap = new HashMap<String, Object>(16);
        String json = HttpUtil.getRequestInputStream(request);
        if(! StringUtils.isEmpty(json)){
            paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
        }
        if(StringUtils.isEmpty(paramMap.get("markState"))){
            pr.setMessage("缺少参数【markState】标记状态");
            pr.setState(false);
            return pr;
        }
        if(StringUtils.isEmpty(paramMap.get("id"))){
            pr.setMessage("缺少参数【id】异常记录ID");
            pr.setState(false);
            return pr;
        }
        // 修改异常订单标记状态
        int count = execOrderDao.updateExecOrderRecord(paramMap);
        if(count ==0){
            pr.setMessage("标记失败");
            pr.setState(false);
            return pr;
        }
        // 新增标记记录
        execOrderDao.insertExecOrderMarkRecord(paramMap);
        pr.setState(true);
        pr.setMessage("标记成功");
        return pr;
    }

    /**
     * 获取白名单列表
     * @param request
     * @return
     */
    public GridResult listWhiteList(HttpServletRequest request){
        GridResult gr = new GridResult();
        Map<String, Object> paramMap = new HashMap<String, Object>(16);
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(! StringUtils.isEmpty(json)){
                paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            }
            GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
            if(pageParamResult!=null){
                return pageParamResult;
            }
            List<Map<String, Object>> list = execOrderDao.listExecOrderWhiteList(paramMap);
            int count = execOrderDao.countExecOrderWhiteList(paramMap);
            if (list != null && list.size() > 0) {
                gr.setMessage("获取数据成功");
                gr.setObj(list);
            } else {
                gr.setMessage("无数据");
            }
            gr.setState(true);
            gr.setTotal(count);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
    }

    /**
     * 移除白名单
     * @param request
     * @return
     */
    public ProcessResult removeWhiteList(HttpServletRequest request) throws Exception{
        ProcessResult pr = new ProcessResult();
        Map<String, Object> paramMap = new HashMap<String, Object>(16);
        String json = HttpUtil.getRequestInputStream(request);
        if(! StringUtils.isEmpty(json)){
            paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
        }
        if(StringUtils.isEmpty(paramMap.get("ids"))){
            pr.setMessage("缺少参数【ids】用户ID");
            pr.setState(false);
            return pr;
        }
        List<Long> ids = (List<Long>)paramMap.get("ids");
        if(ids.size() > 0){
            execOrderDao.deleteExecOrderWhiteList(ids);
        }
        pr.setState(true);
        pr.setMessage("删除成功");
        return pr;
    }

    /**
     * 新增白名单
     * @param request
     * @return
     */
    public ProcessResult addWhiteList(HttpServletRequest request) throws Exception{
        ProcessResult pr = new ProcessResult();
        Map<String, Object> paramMap = new HashMap<String, Object>(16);
        String json = HttpUtil.getRequestInputStream(request);
        if(! StringUtils.isEmpty(json)){
            paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
        }
        if(StringUtils.isEmpty(paramMap.get("ids"))){
            pr.setMessage("缺少参数【ids】用户ID");
            pr.setState(false);
            return pr;
        }
        List ids = (List<Long>)paramMap.get("ids");
        if(ids.size() > 0){
            execOrderDao.insertExecOrderWhiteList(paramMap);
        }
        pr.setState(true);
        pr.setMessage("添加成功");
        return pr;
    }

    /**
     * 更新异常订单处理状态
     * @param orderNumber
     */
    public void updateExecOrderHandleStateByClose(String orderNumber){
        execOrderDao.updateExecOrderHandleStateByClose(orderNumber);
    }

}
