package com.tk.oms.finance.service;

import com.tk.oms.finance.dao.PlatformDeliveryDao;
import com.tk.sys.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2017, TongKu
 * FileName : PlatformDeliveryService
 * 电商平台发货查询业务操作类
 *
 * @author zhenghui
 * @version 1.00
 * @date 2018/1/16
 */
@Service("PlatformDeliveryService")
public class PlatformDeliveryService {

    @Resource
    private PlatformDeliveryDao platformDeliveryDao;

    @Value("${jdbc_user}")
    private String jdbc_user;

    /**
     * 分页获取电商平台发货订单列表数据
     * @param request
     * @return
     */
    public GridResult queryPlatformDeliveryOrderListForPage(HttpServletRequest request) {
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
            params.put("jdbc_user",jdbc_user);
            int total = this.platformDeliveryDao.queryPlatformDeliveryOrderCount(params);
            List<Map<String, Object>> list = this.platformDeliveryDao.queryPlatformDeliveryOrderList(params);
            if (list != null && list.size() > 0) {
                gr.setMessage("获取电商平台发货订单列表成功");
                gr.setObj(list);
            } else {
                gr.setMessage("无数据");
            }
            gr.setState(true);
            gr.setTotal(total);
        } catch (Exception ex) {
            gr.setState(false);
            gr.setMessage(ex.getMessage());
            ex.printStackTrace();
        }
        return gr;
    }

    /**
     * 获取导出电商平台发货表格的数据
     * @param request
     * @return
     */
    public ProcessResult queryCreatePlatformDeliveryOrderData(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            int total = 0;
            List<Map<String, Object>> list = null;
            params.put("jdbc_user",jdbc_user);
            if(!params.containsKey("type") || StringUtils.isEmpty(params.get("type"))){
                total = this.platformDeliveryDao.queryPlatformDeliveryOrderCount(params);
                pr.setObj(total);
            }else{
                list = this.platformDeliveryDao.queryPlatformDeliveryOrderList(params);
                pr.setObj(list);
            }
            pr.setMessage("获取电商平台发货订单列表成功");
            pr.setState(true);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            ex.printStackTrace();
        }
        return pr;
    }
}
