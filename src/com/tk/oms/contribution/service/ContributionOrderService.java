package com.tk.oms.contribution.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tk.oms.basicinfo.dao.StoreInfoDao;
import com.tk.oms.contribution.dao.ContributionOrderDao;
import com.tk.oms.sysuser.dao.SysUserInfoDao;
import com.tk.oms.sysuser.entity.SysUserInfo;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.Jackson;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : ContributionOrderService
 * 缴款单
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-4-19
 */
@Service("ContributionOrderService")
public class ContributionOrderService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private ContributionOrderDao contributionOrderDao;
	@Resource
	private SysUserInfoDao sysUserInfoDao;
	@Resource
	private StoreInfoDao storeInfoDao;
	/**
	 * 查询缴款单列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryList(HttpServletRequest request) {
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
			if (paramMap.containsKey("states")) {
				String[] states = ((String) paramMap.get("states")).split(",");
				int[] intStr = new int[states.length];
				for (int i = 0; i < states.length; i++) {
					intStr[i] = Integer.parseInt(states[i]);
				}
				paramMap.put("states", intStr);
			}
			//查询缴款单数量
			int total = contributionOrderDao.queryContributionOrderCount(paramMap);
			//查询缴款单列表
			List<Map<String, Object>> dataList = contributionOrderDao.queryContributionOrderList(paramMap);
			if (dataList != null && dataList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询缴款单列表成功!");
				gr.setObj(dataList);
				gr.setTotal(total);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
		} catch (Exception e) {
        	gr.setState(false);
        	gr.setMessage(e.getMessage());
        	logger.error("查询失败："+e.getMessage());
        }

        return gr;
	}
	/**
	 * 查询缴款单详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
            if(paramMap.size() == 0) {
            	pr.setState(false);
            	pr.setMessage("参数缺失");
                return pr;
            }
		
			if(StringUtils.isEmpty(paramMap.get("contribution_number"))){
	            pr.setState(false);
	            pr.setMessage("缺少参数contribution_number");
	            return pr;
	        }
			Map<String,Object> contributionOrderDetail = contributionOrderDao.queryContributionOrderDetail(paramMap);
	        Map<String,Object> resultMap=(Map<String,Object>)Jackson.readJson2Object(Jackson.writeObject2Json(contributionOrderDetail),Map.class);
	        
	        List<Map<String,Object>> contributionWaitList=contributionOrderDao.queryContributionWaitList(paramMap);
	        resultMap.put("contribution_wait_size", contributionWaitList.size());
	        resultMap.put("contribution_wait_list", contributionWaitList);
	        pr.setState(true);
	        pr.setMessage("获取成功");
	        pr.setObj(resultMap);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
        	logger.error("查询失败："+e.getMessage());
        }
		return pr;
	}

	/**
	 * 缴款单审核，驳回
	 * @param request
	 * @return
	 */
	public ProcessResult paymentAuditing(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			// 前台用户数据
			Map<String, Object> map = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			if (StringUtils.isEmpty(map.get("contribution_number"))) {
				pr.setState(false);
				pr.setMessage("缺少参数contribution_number");
				return pr;
			}
			if (StringUtils.isEmpty(map.get("operate_type"))) {
				pr.setState(false);
				pr.setMessage("缺少参数operate_type");
				return pr;
			}
			contributionOrderDao.paymentAuditing(map);
			String output_status = String.valueOf(map.get("output_status"));//状态 0-失败 1-成功
			String output_msg = String.valueOf(map.get("output_msg"));//当成功时为：验款成功!   当失败是：为错误消息内容
			if ("1".equals(output_status)) {//成功
				pr.setState(true);
			} else {
				pr.setState(false);
			}
			pr.setMessage(output_msg);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
            e.printStackTrace();
			logger.error(e);
		}
		return pr;
	}

}
