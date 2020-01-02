package com.tk.oms.finance.service;

import com.tk.oms.finance.dao.OrderProductPriceDao;
import com.tk.sys.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : zhengfy
 * @version V1.0
 * @date 2019/11/22
 */
@Service("OrderProductPriceService")
public class OrderProductPriceService {
    private Log logger = LogFactory.getLog(this.getClass());

    @Resource
    private OrderProductPriceDao orderProductPriceDao;

    /**
     * 分页获取订单商品价格明细
     * @param request
     * @return
     */
    public GridResult queryDetailList(HttpServletRequest request) {
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

            int total = this.orderProductPriceDao.queryDetailListForCount(params);
            List<Map<String, Object>> list = this.orderProductPriceDao.queryDetailListForPage(params);
            if (list != null && list.size() > 0) {
                gr.setMessage("获取订单商品价格明细成功");
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
     * 获取计算过程详情
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public ProcessResult queryProcess(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            // 前台用户数据
            Map<String,Object> map = (Map<String,Object>)Transform.GetPacket(json, HashMap.class);
            long order_specs_price_id = Long.parseLong(map.get("order_specs_price_id").toString());
            Map<String, Object> result = orderProductPriceDao.queryProcess(order_specs_price_id);
            pr.setObj(result);
            pr.setState(true);
            pr.setMessage("OK");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
    }
}