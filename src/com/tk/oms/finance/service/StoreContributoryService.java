package com.tk.oms.finance.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.finance.dao.StoreContributoryDao;
import com.tk.sys.config.EsbConfig;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpClientUtil;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.Jackson;
import com.tk.sys.util.Packet;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : StoreContributoryService.java
 * 店铺缴款service
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018-3-8
 */
@Service("StoreContributoryService")
public class StoreContributoryService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private StoreContributoryDao storeContributoryDao;
	@Value("${store_service_url}")
	private String store_service_url;// 店铺缴款服务地址
	@Value("${agent_esbPaymentCheck}")
	private String agent_esbPaymentCheck;// 审批接口
	
	/**
	 * 店铺缴款单列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryListForPage(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
			if(pageParamResult!=null){
				return pageParamResult;
			}
            if((!StringUtils.isEmpty(paramMap.get("audit_state")))&&paramMap.get("audit_state") instanceof String){
            	paramMap.put("audit_state",paramMap.get("audit_state").toString().split(","));
			}
			//查询店铺缴款单数量
			int total = storeContributoryDao.queryListCount(paramMap);
			//查询店铺缴款单列表
			List<Map<String, Object>> dataList = storeContributoryDao.queryListForPage(paramMap);
			if (dataList != null && dataList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询店铺缴款单列表成功!");
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
	 * 店铺缴款详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryStoreContributoryDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(StringUtils.isEmpty(paramMap.get("ctrb_number"))){
				pr.setState(false);
				pr.setMessage("缺少参数ctrb_number");
				return pr;
			}
			//获取店铺缴款详情
			Map<String, Object> detail = storeContributoryDao.queryStoreContributoryDetail(paramMap);
			//获取店铺缴款交易单列表
			List<Map<String, Object>> trade_list = storeContributoryDao.queryStoreContributoryTradeList(paramMap);
			
			detail.put("trade_list", trade_list);
			
			pr.setState(true);
			pr.setMessage("获取店铺缴款详情成功");
			pr.setObj(detail);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 审批
	 * @param request
	 * @return
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public ProcessResult auditStoreContributory(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(StringUtils.isEmpty(paramMap.get("ctrb_number"))){
				pr.setState(false);
				pr.setMessage("缺少参数ctrb_number");
				return pr;
			}
			if(storeContributoryDao.queryStoreContributoryIsExists(paramMap) == 0){
				pr.setState(false);
				pr.setMessage("当前缴款单审批状态异常！");
				return pr;
			}
			//审批类型判断
			if("pass".equals(paramMap.get("checkType"))){
				//通过
				param.put("PAYMENT_NUMBER", paramMap.get("ctrb_number"));
				param.put("AGENT_ID", paramMap.get("store_id"));
				param.put("PAYMENT_STATE", "4");
				param.put("STAFF_ID", paramMap.get("public_user_id"));
				param.put("STAFF_NAME", paramMap.get("public_user_realname"));
				param.put("CHECK_FROM", "1");
				
				//调用远程接口
				Map<String, Object> listMap = (Map<String, Object>) queryForPost(param,store_service_url+agent_esbPaymentCheck);
				if (Integer.parseInt(listMap.get("state").toString()) != 1) {
					throw new RuntimeException("调用远程接口异常");
				}
				
				paramMap.put("audit_state", 2);
				storeContributoryDao.auditStoreContributory(paramMap);
				//更新付款状态
				if(storeContributoryDao.updateStoreTrade(paramMap)==0){
					throw new RuntimeException("更新付款状态失败！");
				}
				//更新清分状态
				if(storeContributoryDao.updateStoreTradeDivide(paramMap)==0){
					throw new RuntimeException("更新清分状态失败！");
				}
				
			}else{
				//驳回
				param.put("PAYMENT_NUMBER", paramMap.get("ctrb_number"));
				param.put("AGENT_ID", paramMap.get("store_id"));
				param.put("PAYMENT_STATE", "3");
				param.put("STAFF_ID", paramMap.get("public_user_id"));
				param.put("STAFF_NAME",  paramMap.get("public_user_realname"));
				param.put("REMARK",  paramMap.get("audit_reject_reason"));
				param.put("CHECK_FROM", "1");
				
				//调用远程接口
				Map<String, Object> listMap = (Map<String, Object>) queryForPost(param,store_service_url+agent_esbPaymentCheck);
				if (Integer.parseInt(listMap.get("state").toString()) != 1) {
					throw new RuntimeException("调用远程接口异常");
				}
				
				paramMap.put("audit_state", 3);
				storeContributoryDao.auditStoreContributory(paramMap);
			}
			
			pr.setState(true);
			pr.setMessage("审批成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException("审批异常："+e.getMessage());
		}
		return pr;
	}
	
	public Object queryForPost(Map<String,Object> obj,String url) throws Exception{
        String params = "";
        if(obj != null){
            Packet packet = Transform.GetResult(obj, EsbConfig.ERP_FORWARD_KEY_NAME);//加密数据
            params = Jackson.writeObject2Json(packet);//对象转json、字符串
        }
        //发送至服务端
        String json = HttpClientUtil.post(url, params,30000);
        return Transform.GetPacketJzb(json,Map.class);
    }
	
}
