package com.tk.oms.finance.service;


import com.tk.oms.finance.dao.OrderAuditingDao;
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

@Service("OrderAuditingService")
public class OrderAuditingService {
    private Log logger = LogFactory.getLog(this.getClass());
    @Resource
    private OrderAuditingDao orderAuditingDao;

    @Resource
    private TransactionService transactionService;

    /**
     * 订单审核列表
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public GridResult queryOrderAuditingList(HttpServletRequest request) {
        GridResult gr = new GridResult();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);

            if (!StringUtils.isEmpty(json)) {
                paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
                PageUtil.handlePageParams(paramMap);
            }
            if (paramMap.size() == 0) {
                gr.setState(false);
                gr.setMessage("参数缺失");
                return gr;
            }
            if (paramMap.containsKey("audit_state")) {
                String[] states = ((String) paramMap.get("audit_state")).split(",");
                paramMap.put("audit_state", states);
            }
            //查询订单审核数量
            int total = orderAuditingDao.queryOrderAuditingCount(paramMap);
            //查询订单审核列表
            List<Map<String, Object>> dataList = orderAuditingDao.queryOrderAuditingList(paramMap);
            if (dataList != null && dataList.size() > 0) {
                gr.setState(true);
                gr.setMessage("查询成功!");
                gr.setObj(dataList);
                gr.setTotal(total);
            } else {
                gr.setState(true);
                gr.setMessage("无数据");
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            e.printStackTrace();
            logger.error("查询失败：" + e.getMessage());
        }

        return gr;
    }

    /**
     * 订单审核详情
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public ProcessResult queryOrderAuditingDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);

            if (!StringUtils.isEmpty(json)) {
                paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            }
            if (paramMap.size() == 0) {
                pr.setState(false);
                pr.setMessage("参数缺失");
                return pr;
            }
            //1.订单审核详情查询
            Map<String, Object> retMap = orderAuditingDao.queryOrderAuditingDetail(paramMap);
            pr.setState(true);
            pr.setMessage("查询成功!");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("查询失败：" + e.getMessage());
        }
        return pr;
    }

    /**
     * 订单审核 通过,驳回
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public ProcessResult orderAuditing(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            // 前台用户数据
            Map<String, Object> map = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (!map.containsKey("pay_trade_number") || StringUtils.isEmpty(map.get("pay_trade_number").toString())) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            if (!map.containsKey("check_type") || StringUtils.isEmpty(map.get("check_type").toString())) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            //check_type= 1 表示审批通过   2:表示审批驳回 
            if ("1".equals(map.get("check_type").toString())) {
                String prechargeState = orderAuditingDao.queryPayPrechargeState(map.get("pay_trade_number").toString());
                if ("1".equals(prechargeState)) {
                    transactionService.orderAuditPrecharge(map);
                }
            }

            return transactionService.orderAudit(map);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
    }

    /**
     * 查询交易关联订单列表
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public ProcessResult queryOrderUnionPayList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);

            if (!StringUtils.isEmpty(json)) {
                paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            }
            if (paramMap.size() == 0) {
                pr.setState(false);
                pr.setMessage("参数缺失");
                return pr;
            }
            //1.查询交易关联订单
            List<Map<String, Object>> orderList = orderAuditingDao.queryOrderUnionPayList(paramMap);
            pr.setState(true);
            pr.setMessage("查询成功!");
            pr.setObj(orderList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("查询失败：" + e.getMessage());
        }
        return pr;
    }


}
