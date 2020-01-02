package com.tk.oms.notice.service;


import com.tk.oms.notice.dao.MessageCenterDao;
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
 * Copyright (c), 2017, TongKu
 * FileName : MessageCenterService
 * 消息中心管理业务操作类
 *
 * @author zhenghui
 * @version 1.00
 * @date 2017/09/05
 */
@Service("MessageCenterService")
public class MessageCenterService {

    @Resource
    private MessageCenterDao messageCenterDao;

    /**
     * 分页获取消息提醒列表数据
     * @param request
     * @return
     */
    public GridResult queryMessageRemindListForPage(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少请求参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            //是否缺少必要的参数
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            //获取总条数
            int total =0;
            //提醒消息提醒list
            List<Map<String, Object>> list =null; 
            //产品消息
            if("1".equals(params.get("message_type")+"")){
	            total = this.messageCenterDao.queryMessageRemindForCount(params);
	            list = this.messageCenterDao.queryMessageRemindForList(params);
            }
            //活动消息
            if("2".equals(params.get("message_type")+"")){
	            total = this.messageCenterDao.queryActivityProductAuditRemindCount(params);
	            list = this.messageCenterDao.queryActivityProductAuditRemindForList(params);
            }
            //会员反馈消息
            if("4".equals(params.get("message_type")+"")){
	            total = this.messageCenterDao.queryMemberFeedbackCount(params);
	            list = this.messageCenterDao.queryMemberFeedbackList(params);
            }
            //返回结果
            if (list != null && list.size() > 0) {
                gr.setMessage("获取消息提醒列表成功");
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
     * 获取sku列表数据
     * @param request
     * @return
     */
    public ProcessResult querySkuDetailForList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少请求参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            //是否缺少必要的参数
            if (StringUtils.isEmpty(params.get("product_itemnumber"))) {
                pr.setState(false);
                pr.setMessage("缺少参数PRODUCT_ITEMNUMBER");
                return pr;
            }
            //获取sku列表数据
            List<Map<String, Object>> list = this.messageCenterDao.querySkuListByNumber(params);
            if (list != null && list.size() > 0) {
                pr.setMessage("获取sku列表成功");
                pr.setObj(list);
            } else {
                pr.setMessage("无数据");
            }
            pr.setState(true);

        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            ex.printStackTrace();
        }
        return pr;
    }

    /**
     * 获取不同消息提醒的未读数量
     * @param request
     * @return
     */
    public ProcessResult queryMessageRemindForCount(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        //返回结果数据Map
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            //获取所有产品消息提醒数量
            int total_product_count= this.messageCenterDao.queryMessageRemindCountByType(params);
            //获取活动商品审批提醒数量
            int activity_count = this.messageCenterDao.queryActivityProductAuditRemindCount(params);
            //获取会员反馈提醒数量
            int member_count = this.messageCenterDao.queryMemberFeedbackRemindCount(params);
            resultMap.put("total_count", total_product_count + activity_count+member_count);
            //获取产品消息提醒数量
            params.put("remind_type", 0);
            resultMap.put("product_count", this.messageCenterDao.queryMessageRemindCountByType(params));
            resultMap.put("activity_count", activity_count);
            resultMap.put("member_count", member_count);
            pr.setState(true);
            pr.setMessage("获取消息未读数量成功");
            pr.setObj(resultMap);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            ex.printStackTrace();
        }
        return pr;
    }

    /**
     * 更新消息状态
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional
    public ProcessResult updateMessageRemindState(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少请求参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            //是否缺少必要的参数
            if (StringUtils.isEmpty(params.get("message_type"))) {
                pr.setState(false);
                pr.setMessage("缺少参数message_type");
                return pr;
            }
            //是否缺少必要的参数
            if (StringUtils.isEmpty(params.get("operation_type"))) {
                pr.setState(false);
                pr.setMessage("缺少参数OPERATION_TYPE");
                return pr;
            }
            if (params.containsKey("ids")) {
                List<String> id_list = (List<String>) params.get("ids");
                params.put("id_list", id_list);
            }
            String operation_type = params.get("operation_type").toString();
            //判断是否为零，是（删除），否（修改）
            if ("0".equals(operation_type)) {
                if (this.messageCenterDao.deleteMessageRemind(params) > 0) {
                    pr.setMessage("删除消息提醒成功");
                }
            } else {
            	if(Integer.parseInt(params.get("message_type")+"")==1){
            		//更新产品信息状态
            		if (this.messageCenterDao.updateMessageRemindState(params) > 0) {
                        pr.setMessage("更新消息提醒成功");
                    }
            	}else{
            		if(Integer.parseInt(params.get("message_type")+"")==4){
            			//更新会员消息状态
                		if (this.messageCenterDao.updateMemberFeedbackRemindState(params) > 0) {
                            pr.setMessage("更新消息提醒成功");
                        }
            		}
            	}
            }
            pr.setState(true);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
        return pr;
    }

}