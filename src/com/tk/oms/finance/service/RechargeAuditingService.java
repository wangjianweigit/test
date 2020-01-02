package com.tk.oms.finance.service;


import com.tk.oms.finance.dao.RechargeAuditingDao;
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
 * 
 * Copyright (c), 2017, Tongku
 * FileName : RechargeCheckService
 * 充值审批
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-4-25
 */
@Service
public class RechargeAuditingService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private RechargeAuditingDao rechargeAuditingDao;
	/**
	 * 充值审批列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryRechargeAuditingList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
            if(paramMap.size() == 0) {
            	gr.setState(false);
            	gr.setMessage("参数缺失");
                return gr;
            }
            //查询充值审批数量
			int total = rechargeAuditingDao.queryRechargeAuditingCount(paramMap);
            //查询充值审批列表
			List<Map<String, Object>> dataList = rechargeAuditingDao.queryRechargeAuditingList(paramMap);
			if (dataList != null && dataList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(dataList);
				gr.setTotal(total);
			} else {
				gr.setState(false);
				gr.setMessage("无数据");
			}
        } catch (Exception e) {
        	gr.setState(false);
        	gr.setMessage(e.getMessage());
        }

        return gr;
	}
	
	/**
	 * 充值审批详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryRechargeAuditingDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
			if(paramMap.size() == 0) {
            	pr.setState(false);
            	pr.setMessage("参数缺失");
                return pr;
            }
			//查询充值审批详情
			Map<String, Object> retMap = rechargeAuditingDao.queryRechargeAuditingDetail(paramMap);
			pr.setState(true);
			pr.setMessage("查询成功!");
			pr.setObj(retMap);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error("查询失败："+e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 充值审批通过、驳回
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult rechargeAuditing(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			// 前台用户数据
			Map<String,Object> map = (Map<String,Object>)Transform.GetPacket(json, HashMap.class);
			rechargeAuditingDao.rechargeAuditing(map);
			String output_status = String.valueOf(map.get("output_status"));//状态 0-失败 1-成功
			String output_msg = String.valueOf(map.get("output_msg"));//当成功时为：验款成功!   当失败是：为错误消息内容
			if("1".equals(output_status)){//成功
				pr.setState(true);
			}else{
				pr.setState(false);
			}
			pr.setMessage(output_msg);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}

}
