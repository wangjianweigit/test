package com.tk.oms.analysis.service;

import com.tk.oms.analysis.dao.ProductOperationLogDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.Transform;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2018, TongKu
 * FileName : ProductOperationLogService
 * 商品操作日志业务层
 *
 * @author zhenghui
 * @version 1.00
 * @date 2019/05/15
 */
@Service("ProductOperationLogService")
public class ProductOperationLogService {

    @Resource
    private ProductOperationLogDao productOperationLogDao;

    /**
     * 分页查询商品操作日志列表
     * @param request
     * @return
     */
    public GridResult queryProductOperationLogListForPage(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺失参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            int total = productOperationLogDao.countProductOperationLogForPage(params);
            List<Map<String, Object>> dataList = null;
            if (total > 0) {
                dataList = productOperationLogDao.listProductOperationLogForPage(params);
                gr.setMessage("查询查询商品操作日志列表成功!");
            } else {
                gr.setMessage("无数据");
            }
            gr.setState(true);
            gr.setObj(dataList);
            gr.setTotal(total);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
    }
}
