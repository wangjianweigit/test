package com.tk.analysis.transaction.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tk.analysis.home.service.HomeAnalysisService;
import com.tk.analysis.transaction.dao.TransactionAnalysisDao;
import com.tk.sys.util.DateUtils;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : TransactionAnalysisService.java
 * 运营分析-交易
 *
 * @author yejingquan
 * @version 1.00
 * @date May 22, 2019
 */
@Service("TransactionAnalysisService")
public class TransactionAnalysisService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private HomeAnalysisService homeAnalysisService;
	@Resource
	private TransactionAnalysisDao transactionAnalysisDao;
	private String[] colors={"#2f4554 ", "#c23531", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF", "#001cf1", "#000000", "#50d400", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3"};
	
	/**
	 * 交易总览
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryTransactionDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_visitorCount = 0;//全站访客数
            float t_productVisitorCount = 0;//商品访客数
            float t_oPurchaseNumber = 0;//下单买家数
            float t_oMoney = 0;//订单商品总额
            float t_payPurchaseNumber = 0;//支付买家数
            float t_payMoney = 0;//销售总额
            float t_kdj = 0;//客单价
            float t_oZhl = 0;//下单转换率
            float t_pZhl = 0;//支付转化率
            float t_opZhl = 0;//下单支付转化率
            float t_fkZhl = 0;//访客转化率
            float t_poZhl = 0;//商品下单转化率
            float t_ppZhl = 0;//商品支付转化率
            
        	/** 交易总览-全站访客数 */
            t_visitorCount = transactionAnalysisDao.r_queryVisitorCount(paramMap);
            /** 交易总览-商品访客数 */
            t_productVisitorCount = transactionAnalysisDao.r_queryProductVisitorCount(paramMap);
            /** 交易总览-下单买家数 */
            t_oPurchaseNumber = transactionAnalysisDao.r_queryPurchaseNumber(paramMap);
            /** 交易总览-订单商品总额 */
            t_oMoney = transactionAnalysisDao.r_queryOrderMoney(paramMap);
            /** 交易总览-支付买家数 */
            t_payPurchaseNumber = transactionAnalysisDao.r_queryPayPurchaseNumber(paramMap);
            /** 交易总览-销售总额 */
            t_payMoney = transactionAnalysisDao.r_queryPayMoney(paramMap);
            
            //客单价
            if(t_payPurchaseNumber > 0) {
            	t_kdj = t_payMoney/t_payPurchaseNumber;
            }
            
          	if(t_visitorCount>0){
          		// 下单转化率
          		t_oZhl = 100*(t_oPurchaseNumber/t_visitorCount);
          		// 支付转化率
          		t_pZhl = 100*(t_payPurchaseNumber/t_visitorCount);
          		//访客转化率
          		t_fkZhl = 100*(t_productVisitorCount/t_visitorCount);
          	}
          	if(t_productVisitorCount>0) {
          		//商品下单转化率
          		t_poZhl = 100*(t_oPurchaseNumber/t_productVisitorCount);
          		//商品支付转化率
          		t_ppZhl = 100*(t_payPurchaseNumber/t_productVisitorCount);
          	}
          	retMap.put("o_zhl", t_oZhl);
          	retMap.put("p_zhl", t_pZhl);
          	retMap.put("fk_zhl", t_fkZhl);
          	retMap.put("po_zhl", t_poZhl);
          	retMap.put("pp_zhl", t_ppZhl);
          	// 下单支付转化率
          	if(t_oPurchaseNumber > 0) {
          		t_opZhl = 100*(t_payPurchaseNumber/t_oPurchaseNumber);
          	}
          	retMap.put("op_zhl", t_opZhl);
          	
          	 //访客转化率
            if(t_visitorCount == 0||t_productVisitorCount == 0) {
            	retMap.put("visitor_ratio", 0);
            }else {
            	retMap.put("visitor_ratio", m2((t_productVisitorCount-t_visitorCount)*100));
            }
          	
            Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
			
			float l_visitorCount = 0;//全站访客数
			float l_productVisitorCount = 0;//商品访客数
            float l_oPurchaseNumber = 0;//下单买家数
            float l_oMoney = 0;//订单商品总额
            float l_payPurchaseNumber = 0;//支付买家数
            float l_payMoney = 0;//销售总额
            float l_kdj = 0;//客单价
            
            //较上个周期
            /** 交易总览-全站访客数 */
            l_visitorCount = transactionAnalysisDao.r_queryVisitorCount(paramMap);
            /** 交易总览-商品访客数 */
            l_productVisitorCount = transactionAnalysisDao.r_queryProductVisitorCount(paramMap);
            /** 交易总览-下单买家数 */
            l_oPurchaseNumber = transactionAnalysisDao.r_queryPurchaseNumber(paramMap);
            /** 交易总览-订单商品总额 */
            l_oMoney = transactionAnalysisDao.r_queryOrderMoney(paramMap);
            /** 交易总览-支付买家数 */
            l_payPurchaseNumber = transactionAnalysisDao.r_queryPayPurchaseNumber(paramMap);
            /** 交易总览-销售总额 */
            l_payMoney = transactionAnalysisDao.r_queryPayMoney(paramMap);
            
            //客单价
            if(l_payPurchaseNumber > 0) {
            	l_kdj = l_payMoney/l_payPurchaseNumber;
            }
            
            //全站访客数
            if(t_visitorCount == 0||l_visitorCount == 0) {
            	retMap.put("vc_ratio", 0);
            }else {
            	retMap.put("vc_ratio", m2((t_visitorCount-l_visitorCount)/l_visitorCount*100));
            }
            retMap.put("visitor_count", t_visitorCount);
            
            //商品访客数
            if(t_productVisitorCount == 0||l_productVisitorCount == 0) {
            	retMap.put("pvc_ratio", 0);
            }else {
            	retMap.put("pvc_ratio", m2((t_productVisitorCount-l_productVisitorCount)/l_productVisitorCount*100));
            }
            retMap.put("p_visitor_count", t_productVisitorCount);
            
            //下单买家数
            if(t_oPurchaseNumber == 0||l_oPurchaseNumber == 0) {
            	retMap.put("opn_ratio", 0);
            }else {
            	retMap.put("opn_ratio", m2((t_oPurchaseNumber-l_oPurchaseNumber)/l_oPurchaseNumber*100));
            }
            retMap.put("o_purchase_number", t_oPurchaseNumber);
            
            //订单商品总额
            if(t_oMoney == 0||l_oMoney == 0) {
            	retMap.put("om_ratio", 0);
            }else {
            	retMap.put("om_ratio", m2((t_oMoney-l_oMoney)/l_oMoney*100));
            }
            retMap.put("o_money", t_oMoney);
            
            //支付买家数
            if(t_payPurchaseNumber == 0||l_payPurchaseNumber == 0) {
            	retMap.put("pp_ratio", 0);
            }else {
            	retMap.put("pp_ratio", m2((t_payPurchaseNumber-l_payPurchaseNumber)/l_payPurchaseNumber*100));
            }
            retMap.put("p_purchase_number", t_payPurchaseNumber);
            
          	//销售总额
            if(t_payMoney == 0||l_payMoney == 0) {
            	retMap.put("pm_ratio", 0);
            }else {
            	retMap.put("pm_ratio", m2((t_payMoney-l_payMoney)/l_payMoney*100));
            }
            retMap.put("p_money", t_payMoney);
            
            //客单价
            if(l_kdj == 0|| l_kdj == 0) {
            	retMap.put("kdj_ratio", 0);
            }else {
            	retMap.put("kdj_ratio", m2((t_kdj-l_kdj)/l_kdj*100));
            }
            retMap.put("kdj", t_kdj);
            
            pr.setState(true);
            pr.setMessage("获取交易总览成功!");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 交易总览-全站访客数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryPandect_VisitorCount(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_visitorCount = 0;//全站访客数
            
        	/** 交易总览-全站访客数 */
            t_visitorCount = transactionAnalysisDao.r_queryVisitorCount(paramMap);
          	
            Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
			
			float l_visitorCount = 0;//全站访客数
            
            //较上个周期
            /** 交易总览-全站访客数 */
            l_visitorCount = transactionAnalysisDao.r_queryVisitorCount(paramMap);
            
            //全站访客数
            if(t_visitorCount == 0||l_visitorCount == 0) {
            	retMap.put("ratio", 0);
            }else {
            	retMap.put("ratio", m2((t_visitorCount-l_visitorCount)/l_visitorCount*100));
            }
            retMap.put("visitor_count", t_visitorCount);
          
            
            pr.setState(true);
            pr.setMessage("获取交易总览-全站访客数成功!");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	
	/**
	 * 交易总览-商品访客数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryPandect_ProductVisitorCount(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_productVisitorCount = 0;//商品访客数
            
            /** 交易总览-商品访客数 */
            t_productVisitorCount = transactionAnalysisDao.r_queryProductVisitorCount(paramMap);
          	
            Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
			
			float l_productVisitorCount = 0;//商品访客数
            
            //较上个周期
            /** 交易总览-商品访客数 */
            l_productVisitorCount = transactionAnalysisDao.r_queryProductVisitorCount(paramMap);
            
            //商品访客数
            if(t_productVisitorCount == 0||l_productVisitorCount == 0) {
            	retMap.put("ratio", 0);
            }else {
            	retMap.put("ratio", m2((t_productVisitorCount-l_productVisitorCount)/l_productVisitorCount*100));
            }
            retMap.put("p_visitor_count", t_productVisitorCount);
            
            
            pr.setState(true);
            pr.setMessage("获取交易总览-商品访客数成功!");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	
	/**
	 * 交易总览-下单买家数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryPandect_PurchaseNumber(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_oPurchaseNumber = 0;//下单买家数
            
            /** 交易总览-下单买家数 */
            t_oPurchaseNumber = transactionAnalysisDao.r_queryPurchaseNumber(paramMap);
          	
            Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
			
            float l_oPurchaseNumber = 0;//下单买家数
            //较上个周期
            /** 交易总览-下单买家数 */
            l_oPurchaseNumber = transactionAnalysisDao.r_queryPurchaseNumber(paramMap);
            
            //下单买家数
            if(t_oPurchaseNumber == 0||l_oPurchaseNumber == 0) {
            	retMap.put("ratio", 0);
            }else {
            	retMap.put("ratio", m2((t_oPurchaseNumber-l_oPurchaseNumber)/l_oPurchaseNumber*100));
            }
            retMap.put("o_purchase_number", t_oPurchaseNumber);
            
            pr.setState(true);
            pr.setMessage("获取交易总览-下单买家数成功!");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	
	/**
	 * 交易总览-订单商品总额
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryPandect_OrderMoney(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_oMoney = 0;//订单商品总额
            
            /** 交易总览-订单商品总额 */
            t_oMoney = transactionAnalysisDao.r_queryOrderMoney(paramMap);
            
          	
            Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
			
            float l_oMoney = 0;//订单商品总额
            
            //较上个周期
            /** 交易总览-订单商品总额 */
            l_oMoney = transactionAnalysisDao.r_queryOrderMoney(paramMap);
            
            //订单商品总额
            if(t_oMoney == 0||l_oMoney == 0) {
            	retMap.put("ratio", 0);
            }else {
            	retMap.put("ratio", m2((t_oMoney-l_oMoney)/l_oMoney*100));
            }
            retMap.put("o_money", t_oMoney);
            
            pr.setState(true);
            pr.setMessage("获取交易总览-订单商品总额成功!");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	
	/**
	 * 交易总览-支付买家数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryPandect_PayPurchaseNumber(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
        	float t_payPurchaseNumber = 0;//支付买家数
            /** 交易总览-支付买家数 */
            t_payPurchaseNumber = transactionAnalysisDao.r_queryPayPurchaseNumber(paramMap);
          	
            Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
	
            float l_payPurchaseNumber = 0;//支付买家数
            /** 交易总览-支付买家数 */
            l_payPurchaseNumber = transactionAnalysisDao.r_queryPayPurchaseNumber(paramMap);
            
            //支付买家数
            if(t_payPurchaseNumber == 0||l_payPurchaseNumber == 0) {
            	retMap.put("ratio", 0);
            }else {
            	retMap.put("ratio", m2((t_payPurchaseNumber-l_payPurchaseNumber)/l_payPurchaseNumber*100));
            }
            retMap.put("p_purchase_number", t_payPurchaseNumber);
            
            pr.setState(true);
            pr.setMessage("获取交易总览-支付买家数成功!");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	
	/**
	 * 交易总览-销售总额
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryPandect_PayMoney(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
        	float t_payMoney = 0;//销售总额
        	float t_preFirstMoney = 0;//预定订单的定金
            /** 交易总览-销售总额 */
            t_payMoney = transactionAnalysisDao.r_queryPayMoney(paramMap);
            /** 交易总览-预定订单的定金 */
        	t_preFirstMoney = transactionAnalysisDao.r_queryPreFirstMoney(paramMap);
        	t_payMoney+=t_preFirstMoney;
            Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
			
            float l_payMoney = 0;//销售总额
            float l_preFirstMoney = 0;//预定订单的定金
            //较上个周期
            /** 交易总览-销售总额 */
            l_payMoney = transactionAnalysisDao.r_queryPayMoney(paramMap);
            /** 交易总览-预定订单的定金 */
        	l_preFirstMoney = transactionAnalysisDao.r_queryPreFirstMoney(paramMap);
        	l_payMoney+=l_preFirstMoney;
          	//销售总额
            if(t_payMoney == 0||l_payMoney == 0) {
            	retMap.put("ratio", 0);
            }else {
            	retMap.put("ratio", m2((t_payMoney-l_payMoney)/l_payMoney*100));
            }
            retMap.put("p_money", t_payMoney);
            
            pr.setState(true);
            pr.setMessage("获取交易总览-销售总额成功!");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	
	/**
	 * 交易总览-客单价
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryPandect_Kdj(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
        	float t_payPurchaseNumber = 0;//支付买家数
            float t_payMoney = 0;//销售总额
            float t_preFirstMoney = 0;//预定订单的定金
        	float t_kdj = 0;//客单价
            
            /** 交易总览-支付买家数 */
            t_payPurchaseNumber = transactionAnalysisDao.r_queryPayPurchaseNumber(paramMap);
            /** 交易总览-销售总额 */
            t_payMoney = transactionAnalysisDao.r_queryPayMoney(paramMap);
            /** 交易总览-预定订单的定金 */
        	t_preFirstMoney = transactionAnalysisDao.r_queryPreFirstMoney(paramMap);
        	t_payMoney+=t_preFirstMoney;
            //客单价
            if(t_payPurchaseNumber > 0) {
            	t_kdj = t_payMoney/t_payPurchaseNumber;
            }
          	
            Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
			
            float l_payPurchaseNumber = 0;//支付买家数
            float l_payMoney = 0;//销售总额
            float l_preFirstMoney = 0;//预定订单的定金
            float l_kdj = 0;//客单价
            
            //较上个周期
            /** 交易总览-支付买家数 */
            l_payPurchaseNumber = transactionAnalysisDao.r_queryPayPurchaseNumber(paramMap);
            /** 交易总览-销售总额 */
            l_payMoney = transactionAnalysisDao.r_queryPayMoney(paramMap);
            /** 交易总览-预定订单的定金 */
        	l_preFirstMoney = transactionAnalysisDao.r_queryPreFirstMoney(paramMap);
        	l_payMoney+=l_preFirstMoney;
            //客单价
            if(l_payPurchaseNumber > 0) {
            	l_kdj = l_payMoney/l_payPurchaseNumber;
            }
            
            //客单价
            if(t_kdj == 0|| l_kdj == 0) {
            	retMap.put("ratio", 0);
            }else {
            	retMap.put("ratio", m2((t_kdj-l_kdj)/l_kdj*100));
            }
            retMap.put("kdj", t_kdj);
            
            pr.setState(true);
            pr.setMessage("获取交易总览-客单价成功!");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	
	/**
	 * 交易趋势-商品访客数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryTrend_ProductVisitorCount(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_productVisitorCount = 0;
        	/** 交易趋势-商品访客数 */
            t_productVisitorCount = transactionAnalysisDao.r_queryTrend_ProductVisitorCount(paramMap);
            Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            //较上个周期
			float l_productVisitorCount = 0;
			/** 交易趋势-商品访客数 */
			l_productVisitorCount = transactionAnalysisDao.r_queryTrend_ProductVisitorCount(paramMap);
            if(t_productVisitorCount == 0||l_productVisitorCount == 0) {
            	retMap.put("ratio", 0);
            }else {
            	retMap.put("ratio", m2((t_productVisitorCount-l_productVisitorCount)/l_productVisitorCount*100));
            }
            retMap.put("p_visitor_count", t_productVisitorCount);
            
            pr.setState(true);
            pr.setMessage("获取商品访客数成功!");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 交易趋势-销售总额
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryTrend_PayMoney(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_money = 0;
            float t_preFirstMoney = 0;
        	/** 交易趋势-销售总额 */
            t_money = transactionAnalysisDao.r_queryPayMoney(paramMap);
            /** 交易趋势-预定订单的定金 */
        	t_preFirstMoney = transactionAnalysisDao.r_queryPreFirstMoney(paramMap);
        	t_money+=t_preFirstMoney;
            Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            //较上个周期
			float l_money = 0;
			float l_preFirstMoney = 0;
			/** 交易趋势-销售总额 */
			l_money = transactionAnalysisDao.r_queryPayMoney(paramMap);
			 /** 交易趋势-预定订单的定金 */
        	l_preFirstMoney = transactionAnalysisDao.r_queryPreFirstMoney(paramMap);
        	l_money+=l_preFirstMoney;
            if(l_money == 0||l_money == 0) {
            	retMap.put("ratio", 0);
            }else {
            	retMap.put("ratio", m2((t_money-l_money)/l_money*100));
            }
            retMap.put("p_money", t_money);
            
            pr.setState(true);
            pr.setMessage("获取销售总额成功!");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 交易趋势-支付买家数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryTrend_PayPurchaseNumber(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_purchase_number = 0;
            float l_purchase_number = 0;
        	/** 交易趋势-支付买家数 */
            t_purchase_number = transactionAnalysisDao.r_queryTrend_PayPurchaseNumber(paramMap);
            Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            //较上个周期
			/** 交易趋势-支付买家数 */
			l_purchase_number = transactionAnalysisDao.r_queryTrend_PayPurchaseNumber(paramMap);
            if(t_purchase_number == 0||l_purchase_number == 0) {
            	retMap.put("ratio", 0);
            }else {
            	retMap.put("ratio", m2((t_purchase_number-l_purchase_number)/l_purchase_number*100));
            }
            retMap.put("p_purchase_number", t_purchase_number);
            
            pr.setState(true);
            pr.setMessage("获取支付买家数成功!");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 交易趋势-支付转化率
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryTrend_PayZhl(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
        	float t_productVisitorCount = 0;
            float t_purchase_number = 0;
            float t_zhl = 0;
            
        	/** 交易趋势-商品访客数 */
            t_productVisitorCount = transactionAnalysisDao.r_queryTrend_ProductVisitorCount(paramMap);
            /** 交易趋势-支付买家数 */
            t_purchase_number = transactionAnalysisDao.r_queryTrend_PayPurchaseNumber(paramMap);
            Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            //较上个周期
			float l_productVisitorCount = 0;
            float l_purchase_number = 0;
            float l_zhl = 0;
			/** 交易趋势-商品访客数 */
            l_productVisitorCount = transactionAnalysisDao.r_queryTrend_ProductVisitorCount(paramMap);
            /** 交易趋势-支付买家数 */
			l_purchase_number = transactionAnalysisDao.r_queryTrend_PayPurchaseNumber(paramMap);
			if(l_productVisitorCount == 0||l_purchase_number == 0) {
            	l_zhl = 0;
            }else {
            	l_zhl = 100*(l_purchase_number/l_productVisitorCount);
            }
            
            if(t_productVisitorCount == 0||t_purchase_number == 0) {
            	retMap.put("ratio", 0);
            	t_zhl = 0;
            }else {
            	t_zhl = 100*(t_purchase_number/t_productVisitorCount);
            	if(l_zhl == 0) {
            		retMap.put("ratio", 0);
            	}else {
            		retMap.put("ratio", m2((t_zhl-l_zhl)/l_zhl*100));
            	}
            }
            retMap.put("p_zhl", t_zhl);
            
            pr.setState(true);
            pr.setMessage("获取支付转化率成功!");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 交易趋势-客单价
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryTrend_Kdj(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
        	float t_money = 0;
        	float t_preFirstMoney = 0;
            float t_purchaseNumber = 0;
            float t_kdj = 0;
            
            /** 交易趋势-销售总额 */
            t_money = transactionAnalysisDao.r_queryPayMoney(paramMap);
            /** 交易趋势-预定订单的定金 */
        	t_preFirstMoney = transactionAnalysisDao.r_queryPreFirstMoney(paramMap);
            /** 交易趋势-支付买家数 */
            t_purchaseNumber = transactionAnalysisDao.r_queryTrend_PayPurchaseNumber(paramMap);
            t_money+=t_preFirstMoney;
            Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            //较上个周期
			float l_money = 0;
			float l_preFirstMoney = 0;
            float l_purchaseNumber = 0;
            float l_kdj = 0;
			/** 交易趋势-销售总额 */
			l_money = transactionAnalysisDao.r_queryPayMoney(paramMap);
			/** 交易趋势-预定订单的定金 */
        	l_preFirstMoney = transactionAnalysisDao.r_queryPreFirstMoney(paramMap);
            /** 交易趋势-支付买家数 */
            l_purchaseNumber = transactionAnalysisDao.r_queryTrend_PayPurchaseNumber(paramMap);
            l_money+=l_preFirstMoney;
            if(l_purchaseNumber == 0||l_money == 0) {
            	l_kdj = 0;
            }else {
            	l_kdj = l_money/l_purchaseNumber;
            }
            
            if(t_purchaseNumber == 0||t_money == 0) {
            	retMap.put("ratio", 0);
            	t_kdj = 0;
            }else {
            	t_kdj = t_money/t_purchaseNumber;
            	if(l_kdj == 0) {
            		retMap.put("ratio", 0);
            	}else {
            		retMap.put("ratio", m2((t_kdj-l_kdj)/l_kdj*100));
            	}
            }
            retMap.put("kdj", t_kdj);
            
            pr.setState(true);
            pr.setMessage("获取客单价成功!");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 交易趋势-订单笔数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryTrend_OrderNum(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_orderNum = 0;
        	/** 交易趋势-订单笔数 */
            t_orderNum = transactionAnalysisDao.r_queryTrend_OrderNum(paramMap);
            Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            //较上个周期
			float l_orderNum = 0;
			/** 交易趋势-订单笔数 */
			l_orderNum = transactionAnalysisDao.r_queryTrend_OrderNum(paramMap);
            if(t_orderNum == 0||l_orderNum == 0) {
            	retMap.put("ratio", 0);
            }else {
            	retMap.put("ratio", m2((t_orderNum-l_orderNum)/l_orderNum*100));
            }
            retMap.put("order_num", t_orderNum);
            
            pr.setState(true);
            pr.setMessage("获取订单笔数成功!");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 交易趋势-订单商品数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryTrend_OrderCount(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_orderCount = 0;
        	/** 交易趋势-订单商品数 */
            t_orderCount = transactionAnalysisDao.r_queryTrend_OrderCount(paramMap);
            Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            //较上个周期
			float l_orderCount = 0;
			/** 交易趋势-订单商品数 */
			l_orderCount = transactionAnalysisDao.r_queryTrend_OrderCount(paramMap);
            if(t_orderCount == 0||l_orderCount == 0) {
            	retMap.put("ratio", 0);
            }else {
            	retMap.put("ratio", m2((t_orderCount-l_orderCount)/l_orderCount*100));
            }
            retMap.put("order_count", t_orderCount);
            
            pr.setState(true);
            pr.setMessage("获取订单商品数成功!");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 交易趋势-成功退款金额
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryTrend_ReturnMoney(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_money = 0;
        	/** 交易趋势-成功退款金额 */
            t_money = transactionAnalysisDao.r_queryTrend_ReturnMoney(paramMap);
            Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            //较上个周期
			float l_money = 0;
			/** 交易趋势-成功退款金额 */
			l_money = transactionAnalysisDao.r_queryTrend_ReturnMoney(paramMap);
            if(t_money == 0||l_money == 0) {
            	retMap.put("ratio", 0);
            }else {
            	retMap.put("ratio", m2((t_money-l_money)/l_money*100));
            }
            retMap.put("return_money", t_money);
            
            pr.setState(true);
            pr.setMessage("获取成功退款金额成功!");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 交易趋势-下单买家数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryTrend_PurchaseNumber(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_purchase_number = 0;
            float l_purchase_number = 0;
        	/** 交易趋势-下单买家数 */
            t_purchase_number = transactionAnalysisDao.r_queryTrend_PurchaseNumber(paramMap);
            Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            //较上个周期
			/** 交易趋势-下单买家数 */
			l_purchase_number = transactionAnalysisDao.r_queryTrend_PurchaseNumber(paramMap);
            if(t_purchase_number == 0||l_purchase_number == 0) {
            	retMap.put("ratio", 0);
            }else {
            	retMap.put("ratio", m2((t_purchase_number-l_purchase_number)/l_purchase_number*100));
            }
            retMap.put("o_purchase_number", t_purchase_number);
            
            pr.setState(true);
            pr.setMessage("获取下单买家数成功!");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 交易趋势-下单转化率
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryTrend_Zhl(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
        	float t_visitorCount = 0;
            float t_purchase_number = 0;
            float t_zhl = 0;
            float l_visitorCount = 0;
            float l_purchase_number = 0;
            float l_zhl = 0;
        	/** 交易趋势-商品访客数 */
            t_visitorCount = transactionAnalysisDao.r_queryTrend_ProductVisitorCount(paramMap);
            /** 交易趋势-下单买家数 */
            t_purchase_number = transactionAnalysisDao.r_queryTrend_PurchaseNumber(paramMap);
            Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            //较上个周期
			/** 交易趋势-商品访客数 */
			l_visitorCount = transactionAnalysisDao.r_queryTrend_ProductVisitorCount(paramMap);
            /** 交易趋势-下单买家数 */
			l_purchase_number = transactionAnalysisDao.r_queryTrend_PurchaseNumber(paramMap);
			if(l_visitorCount == 0||l_purchase_number == 0) {
            	l_zhl = 0;
            }else {
            	l_zhl = 100*(l_purchase_number/l_visitorCount);
            }
            
            if(t_visitorCount == 0||t_purchase_number == 0) {
            	retMap.put("ratio", 0);
            	t_zhl = 0;
            }else {
            	t_zhl = 100*(t_purchase_number/t_visitorCount);
            	if(l_zhl == 0) {
            		retMap.put("ratio", 0);
            	}else {
            		retMap.put("ratio", m2((t_zhl-l_zhl)/l_zhl*100));
            	}
            }
            retMap.put("o_zhl", t_zhl);
            
            pr.setState(true);
            pr.setMessage("获取下单转化率成功!");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 交易趋势-折线
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryTrendChart(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(paramMap.get("start_date"))||StringUtils.isEmpty(paramMap.get("end_date"))||StringUtils.isEmpty(paramMap.get("old_start_date"))||StringUtils.isEmpty(paramMap.get("old_end_date"))) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            List<Map<String, Object>> allDataList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> productVisitorCountList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> orderNumList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> purchaseNumberCountList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> payPurchaseNumberMoneyList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> preFirstMoneyList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> returnMoneyList = new ArrayList<Map<String, Object>>();
            List<String> time_list = new ArrayList<String>();
            if(paramMap.get("start_date").equals(paramMap.get("end_date"))) {//一天
            	time_list.add("00:00");
            	time_list.add("01:00");
            	time_list.add("02:00");
            	time_list.add("03:00");
            	time_list.add("04:00");
            	time_list.add("05:00");
            	time_list.add("06:00");
            	time_list.add("07:00");
            	time_list.add("08:00");
            	time_list.add("09:00");
            	time_list.add("10:00");
            	time_list.add("11:00");
            	time_list.add("12:00");
            	time_list.add("13:00");
            	time_list.add("14:00");
            	time_list.add("15:00");
            	time_list.add("16:00");
            	time_list.add("17:00");
            	time_list.add("18:00");
            	time_list.add("19:00");
            	time_list.add("20:00");
            	time_list.add("21:00");
            	time_list.add("22:00");
            	time_list.add("23:00");
            	/** 折线图 商品访客数 */
                productVisitorCountList = transactionAnalysisDao.r_queryTrend_ProductVisitorCountD_Chart(paramMap);
                /** 折线图 订单笔数 */
                orderNumList = transactionAnalysisDao.r_queryTrend_OrderNumD_Chart(paramMap);
                /** 折线图 下单买家数、订单商品数 */
                purchaseNumberCountList = transactionAnalysisDao.r_queryTrend_PurchaseNumberCountD_Chart(paramMap);
                /** 折线图 支付买家数、销售总额 */
                payPurchaseNumberMoneyList = transactionAnalysisDao.r_queryTrend_PayPurchaseNumberMoneyD_Chart(paramMap);
                /** 折线图 预定订单的定金 */
                preFirstMoneyList = transactionAnalysisDao.r_queryTrend_PreFirstMoneyD_Chart(paramMap);
                /** 折线图 成功退款金额 */
                returnMoneyList = transactionAnalysisDao.r_queryTrend_ReturnMoneyD_Chart(paramMap);
            }else {
            	String start_time = paramMap.get("old_start_date").toString();		//开始时间
                String end_time = paramMap.get("old_end_date").toString();			//结束时间
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                Date start_time_date = sf.parse(start_time);
                Date end_time_date = sf.parse(end_time);
                String tempDate = null;

                //获取指定时间段所有日期数据
                while(end_time_date.compareTo(start_time_date)>=0){
                    tempDate =  sf.format(start_time_date);
                    time_list.add(tempDate);
                    start_time_date = DateUtils.addDay(start_time_date,1);
                }
                /** 折线图 商品访客数 */
                productVisitorCountList = transactionAnalysisDao.r_queryTrend_ProductVisitorCount_Chart(paramMap);
                /** 折线图 订单笔数 */
                orderNumList = transactionAnalysisDao.r_queryTrend_OrderNum_Chart(paramMap);
                /** 折线图 下单买家数、订单笔数和订单商品数 */
                purchaseNumberCountList = transactionAnalysisDao.r_queryTrend_PurchaseNumberCount_Chart(paramMap);
                /** 折线图 支付买家数、销售总额 */
                payPurchaseNumberMoneyList = transactionAnalysisDao.r_queryTrend_PayPurchaseNumberMoney_Chart(paramMap);
                /** 折线图 预定订单的定金 */
                preFirstMoneyList = transactionAnalysisDao.r_queryTrend_PreFirstMoney_Chart(paramMap);
                /** 折线图 成功退款金额 */
                returnMoneyList = transactionAnalysisDao.r_queryTrend_ReturnMoney_Chart(paramMap);
            }
            
            //商品访客数
            Map<String,Object> productVisitorCountMap = list2Map(productVisitorCountList,"CREATE_DATE","VISITOR_COUNT");
            //下单买家数
            Map<String,Object> purchaseNumberMap = list2Map(purchaseNumberCountList,"CREATE_DATE","PURCHASE_NUMBER");
            //订单笔数
            Map<String,Object> orderNumMap = list2Map(orderNumList,"CREATE_DATE","ORDER_NUM");
            //订单商品数
            Map<String,Object> orderCountMap = list2Map(purchaseNumberCountList,"CREATE_DATE","ORDER_COUNT");
            //支付买家数
            Map<String,Object> payPurchaseNumberMap = list2Map(payPurchaseNumberMoneyList,"CREATE_DATE","PURCHASE_NUMBER");
            //销售总额
            Map<String,Object> payMoneyMap = list2Map(payPurchaseNumberMoneyList,"CREATE_DATE","PAY_MONEY");
            //预定订单的定金
            Map<String,Object> preFirstMoneyMap = list2Map(preFirstMoneyList,"CREATE_DATE","PRE_FIRST_MONEY");
            //成功退款金额
            Map<String,Object> returnMoneyMap = list2Map(returnMoneyList,"CREATE_DATE","RETURN_MONEY");
            
            //开始数据拼装
          	String key="";
          	
          	Map<String, Object> map = null;
            for(String time : time_list) {
            	key = time;
            	/** 交易趋势-商品访客数 */
                float productVisitorCount = productVisitorCountMap.get(key)==null?0:Float.parseFloat(productVisitorCountMap.get(key).toString());
                /** 交易趋势-下单买家数 */
                float purchaseNumber = purchaseNumberMap.get(key)==null?0:Float.parseFloat(purchaseNumberMap.get(key).toString());
                /** 交易趋势-订单笔数 */
                float orderNum = orderNumMap.get(key)==null?0:Float.parseFloat(orderNumMap.get(key).toString());
                /** 交易趋势-订单商品数 */
                float orderCount = orderCountMap.get(key)==null?0:Float.parseFloat(orderCountMap.get(key).toString());
                /** 交易趋势-支付买家数 */
                float payPurchaseNumber = payPurchaseNumberMap.get(key)==null?0:Float.parseFloat(payPurchaseNumberMap.get(key).toString());
                /** 交易趋势-销售总额 */
                float payMoney = payMoneyMap.get(key)==null?0:Float.parseFloat(payMoneyMap.get(key).toString());
                /** 交易趋势-预定订单的定金 */
                float preFirstMoney = preFirstMoneyMap.get(key)==null?0:Float.parseFloat(preFirstMoneyMap.get(key).toString());
                /** 交易趋势-成功退款金额 */
                float returnMoney = returnMoneyMap.get(key)==null?0:Float.parseFloat(returnMoneyMap.get(key).toString());
                payMoney+=preFirstMoney;
                map = new HashMap<String, Object>();
                //商品访客数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", productVisitorCount);
              	map.put("PAGE_NAME", "商品访客数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
                //订单笔数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", orderNum);
              	map.put("PAGE_NAME", "订单笔数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
                //订单商品数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", orderCount);
              	map.put("PAGE_NAME", "订单商品数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//下单买家数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", purchaseNumber);
              	map.put("PAGE_NAME", "下单买家数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//下单转化率
              	if(productVisitorCount==0||purchaseNumber==0){
              		map.put("CNT", 0);
              	}else{
              		float zhl = 100*(purchaseNumber/productVisitorCount);
              		map.put("CNT", m2(zhl));
              	}
              	map.put("CREATE_DATE", key);
              	map.put("PAGE_NAME", "下单转化率");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//支付买家数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", payPurchaseNumber);
              	map.put("PAGE_NAME", "支付买家数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//支付转化率
              	if(productVisitorCount==0||payPurchaseNumber==0){
              		map.put("CNT", 0);
              	}else{
              		float zhl = 100*(payPurchaseNumber/productVisitorCount);
              		map.put("CNT", m2(zhl));
              	}
              	map.put("CREATE_DATE", key);
              	map.put("PAGE_NAME", "支付转化率");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//销售总额
              	map.put("CREATE_DATE", key);
              	map.put("CNT", payMoney);
              	map.put("PAGE_NAME", "销售总额");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//成功退款金额
              	map.put("CNT", returnMoney);
              	map.put("CREATE_DATE", key);
              	map.put("PAGE_NAME", "成功退款金额");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//客单价
              	if(payPurchaseNumber == 0||payMoney == 0) {
              		map.put("CNT", 0);
              	}else {
              		map.put("CNT", payMoney/payPurchaseNumber);
              	}
              	map.put("CREATE_DATE", key);
              	map.put("PAGE_NAME", "客单价");
              	allDataList.add(map);
              	
            }
            
            //将原始数据组装为Echart可识别的格式
            resultMap = createData("PAGE_NAME", time_list, allDataList);
            
            pr.setState(true);
			pr.setMessage("获取交易趋势折线成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	
	/**
	 * 终端构成
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryTerminalForm(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            //终端列表
            List<Map<String, Object>> list = transactionAnalysisDao.r_queryTerminalList(paramMap);
            if (list != null && list.size() > 0) {
            	//数据获取-商品访客数(按终端)
            	List<Map<String,Object>> productVisitorCountList = transactionAnalysisDao.r_queryTerminal_ProductVisitorCount(paramMap);
				Map<String,Object> productVisitorCountMap = list2Map(productVisitorCountList,"ACCESS_TERMINAL_ID","VISITOR_COUNT");
				//数据获取-支付买家数、销售总额、支付商品数(按终端)
				List<Map<String,Object>> purchaseNumberMoneyCountList = transactionAnalysisDao.r_queryTerminal_PayPurchaseNumberMoneyCount(paramMap);
				Map<String,Object> purchaseNumberMap = list2Map(purchaseNumberMoneyCountList,"ACCESS_TERMINAL_ID","PURCHASE_NUMBER");
				Map<String,Object> payMoneyMap = list2Map(purchaseNumberMoneyCountList,"ACCESS_TERMINAL_ID","PAY_MONEY");
				Map<String,Object> payCountMap = list2Map(purchaseNumberMoneyCountList,"ACCESS_TERMINAL_ID","PAY_COUNT");
				//数据获取-预定订单的定金(按终端)
				List<Map<String,Object>> preFirstMoneyList = transactionAnalysisDao.r_queryTerminal_PreFirstMoney(paramMap);
				Map<String,Object> preFirstMoneyMap = list2Map(preFirstMoneyList,"ACCESS_TERMINAL_ID","PRE_FIRST_MONEY");
				
				Map<String,Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String access_terminal_id = tempMap.get("ACCESS_TERMINAL_ID").toString();
					float productVisitorCount = productVisitorCountMap.get(access_terminal_id)==null?0:Float.parseFloat(productVisitorCountMap.get(access_terminal_id).toString());
					float purchaseNumber = purchaseNumberMap.get(access_terminal_id)==null?0:Float.parseFloat(purchaseNumberMap.get(access_terminal_id).toString());
					float payMoney = payMoneyMap.get(access_terminal_id)==null?0:Float.parseFloat(payMoneyMap.get(access_terminal_id).toString());
					float preFirstMoney = preFirstMoneyMap.get(access_terminal_id)==null?0:Float.parseFloat(preFirstMoneyMap.get(access_terminal_id).toString());
					payMoney+=preFirstMoney;
					//支付转化率
					if(productVisitorCount == 0||purchaseNumber == 0) {
						tempMap.put("P_ZHL", 0);
					}else {
						tempMap.put("P_ZHL", 100*(purchaseNumber/productVisitorCount));
					}
					tempMap.put("P_VISITOR_COUNT", productVisitorCount);//商家访客数
					tempMap.put("P_PURCHASE_NUMBER", purchaseNumber);//支付买家数
					tempMap.put("P_COUNT", payCountMap.get(access_terminal_id)==null?0:Float.parseFloat(payCountMap.get(access_terminal_id).toString()));//支付商品数
					tempMap.put("P_MONEY", payMoney);//销售总额
				}
            }
            
            pr.setState(true);
			pr.setMessage("获取终端设备构成成功");
			pr.setObj(list);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
            logger.error(e);
        }
        return pr;
	}
	
	/**
     * 交易构成-商品类目构成
     * @param request
     * @return
     */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductClassifyForm(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            
            //商品分类列表
            List<Map<String, Object>> list = transactionAnalysisDao.queryProductTypeList(paramMap);
            if (list != null && list.size() > 0) {
            	//数据获取-访客数(按类别)
            	List<Map<String,Object>> visitorCountList = transactionAnalysisDao.r_queryClassify_VisitorCount(paramMap);
				Map<String,Object> visitorCountMap = list2Map(visitorCountList,"PRODUCT_FIRST_CLASSIFY","VISITOR_COUNT");
				//数据获取-支付买家数、销售总额、支付商品数(按类别)
				List<Map<String,Object>> purchaseNumberMoneyCountList = transactionAnalysisDao.r_queryClassify_PurchaseNumberMoneyCount(paramMap);
				Map<String,Object> purchaseNumberMap = list2Map(purchaseNumberMoneyCountList,"PRODUCT_FIRST_CLASSIFY","PURCHASE_NUMBER");
				Map<String,Object> paymentMoneyMap = list2Map(purchaseNumberMoneyCountList,"PRODUCT_FIRST_CLASSIFY","PAYMENT_MONEY");
				Map<String,Object> saleCountMap = list2Map(purchaseNumberMoneyCountList,"PRODUCT_FIRST_CLASSIFY","SALE_COUNT");
				
				Map<String,Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String product_first_classify = tempMap.get("ID").toString();
					float visitor_count = visitorCountMap.get(product_first_classify)==null?0:Float.parseFloat(visitorCountMap.get(product_first_classify).toString());
					float purchase_number = purchaseNumberMap.get(product_first_classify)==null?0:Float.parseFloat(purchaseNumberMap.get(product_first_classify).toString());
					float payment_money = paymentMoneyMap.get(product_first_classify)==null?0:Float.parseFloat(paymentMoneyMap.get(product_first_classify).toString());
					//支付转化率
					if(visitor_count == 0||purchase_number == 0) {
						tempMap.put("ZHL", 0);
					}else {
						tempMap.put("ZHL", 100*(purchase_number/visitor_count));
					}
					tempMap.put("VISITOR_COUNT", visitor_count);//访客数
					tempMap.put("PURCHASE_NUMBER", purchase_number);//支付买家数
					tempMap.put("SALE_COUNT", saleCountMap.get(product_first_classify)==null?0:Float.parseFloat(saleCountMap.get(product_first_classify).toString()));//支付商品数
					tempMap.put("PAYMENT_MONEY", payment_money);//销售总额
				}
            }
            
            pr.setState(true);
			pr.setMessage("获取商品类目构成成功");
			pr.setObj(list);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
            logger.error(e);
        }
        return pr;
	}
	
	/**
	 * 交易构成-品牌构成
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryBrandForm(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            //商品访客数
			String file_names_pvc = ":P_VISITOR_COUNT:";
            //销售总额
			String file_names_pm = ":P_MONEY:";
			//销售总额占比
			String file_names_mr = ":MONEY_RATIO:";
			//支付商品数
			String file_names_sc = ":P_COUNT:";
			//支付买家数
			String file_names_pn = ":P_PURCHASE_NUMBER:";
			//支付转化率
			String file_names_zhl = ":P_ZHL:";
			
			List<Map<String, Object>> list=null;
			//需要查询的品牌列表
			List<String> brandList = new ArrayList<String>();
			//数据获取-所有支付总金额(按品牌)
			float allPayMoney = transactionAnalysisDao.r_queryBrand_AllPayMoney(paramMap);
			//数据获取-预定订单的定金(按品牌)
			float allPreFirstMoney = transactionAnalysisDao.r_queryBrand_AllPreFirstMoney(paramMap);
			allPayMoney+=allPreFirstMoney;
			if((paramMap.containsKey("sort")&&!"".equals(paramMap.get("sort").toString()))
					&&(paramMap.containsKey("sort_by")&&!"".equals(paramMap.get("sort_by").toString()))){
				String sort = ":"+paramMap.get("sort").toString()+":";
				if(file_names_pvc.indexOf(sort)!=-1) {
					//商品访客数---获取排序后的品牌信息
					brandList=transactionAnalysisDao.r_queryProductVisitorCount_Brand(paramMap);
				}else if(file_names_pm.indexOf(sort)!=-1) {
					//销售总额---获取排序后的品牌信息
					brandList=transactionAnalysisDao.r_queryPayMoney_Brand(paramMap);
				}else if(file_names_mr.indexOf(sort)!=-1) {
					paramMap.put("allPayMoney", allPayMoney);
					//销售总额占比---获取排序后的品牌信息
					brandList=transactionAnalysisDao.r_queryMoneyRatio_Brand(paramMap);
				}else if(file_names_sc.indexOf(sort)!=-1) {
					//支付商品数---获取排序后的品牌信息
					brandList=transactionAnalysisDao.r_queryPayCount_Brand(paramMap);
				}else if(file_names_pn.indexOf(sort)!=-1) {
					//支付买家数---获取排序后的品牌信息
					brandList=transactionAnalysisDao.r_queryPayPurchaseNumber_Brand(paramMap);
				}else if(file_names_zhl.indexOf(sort)!=-1) {
					//支付转化率---获取排序后的品牌信息
					brandList=transactionAnalysisDao.r_queryPayZhl_Brand(paramMap);
				}else {
					pr.setState(false);
					pr.setMessage("未配置的排序字段，请联系管理员【"+paramMap.get("sort").toString()+"】");
					return pr;
				}
			}else{
				//查询默认排序的品牌信息
				brandList=transactionAnalysisDao.r_queryBrandListBy_Default(paramMap);
			}
			
			if(!brandList.isEmpty()&&brandList.size()>0){
				paramMap.put("brandList", brandList);
				list = transactionAnalysisDao.r_queryBrandList(paramMap);
			}
            
            if (list != null && list.size() > 0) {
            	//数据获取-商品访客数(按品牌)
            	List<Map<String,Object>> productVisitorCountList = transactionAnalysisDao.r_queryBrand_ProductVisitorCount(paramMap);
				Map<String,Object> productVisitorCountMap = list2Map(productVisitorCountList,"BRAND_ID","VISITOR_COUNT");
				//数据获取-支付买家数、销售总额、支付商品数(按品牌)
				List<Map<String,Object>> purchaseNumberMoneyCountList = transactionAnalysisDao.r_queryBrand_PayPurchaseNumberMoneyCount(paramMap);
				Map<String,Object> purchaseNumberMap = list2Map(purchaseNumberMoneyCountList,"BRAND_ID","PURCHASE_NUMBER");
				Map<String,Object> payMoneyMap = list2Map(purchaseNumberMoneyCountList,"BRAND_ID","PAY_MONEY");
				Map<String,Object> payCountMap = list2Map(purchaseNumberMoneyCountList,"BRAND_ID","PAY_COUNT");
				//数据获取-预定订单的定金(按品牌)
				List<Map<String,Object>> preFirstMoneyList = transactionAnalysisDao.r_queryBrand_PreFirstMoney(paramMap);
				Map<String,Object> preFirstMoneyMap = list2Map(preFirstMoneyList,"BRAND_ID","PRE_FIRST_MONEY");
				
				Map<String,Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String brand_id = tempMap.get("ID").toString();
					float productVisitorCount = productVisitorCountMap.get(brand_id)==null?0:Float.parseFloat(productVisitorCountMap.get(brand_id).toString());
					float purchaseNumber = purchaseNumberMap.get(brand_id)==null?0:Float.parseFloat(purchaseNumberMap.get(brand_id).toString());
					float payMoney = payMoneyMap.get(brand_id)==null?0:Float.parseFloat(payMoneyMap.get(brand_id).toString());
					float preFirstMoney = preFirstMoneyMap.get(brand_id)==null?0:Float.parseFloat(preFirstMoneyMap.get(brand_id).toString());
					payMoney+=preFirstMoney;
					//支付转化率
					if(productVisitorCount == 0||purchaseNumber == 0) {
						tempMap.put("P_ZHL", 0);
					}else {
						tempMap.put("P_ZHL", 100*(purchaseNumber/productVisitorCount));
					}
					//销售总额占比
					if(allPayMoney == 0||payMoney == 0) {
						tempMap.put("MONEY_RATIO", 0);
					}else {
						tempMap.put("MONEY_RATIO", 100*(payMoney/allPayMoney));
					}
					tempMap.put("P_VISITOR_COUNT", productVisitorCount);//商品访客数
					tempMap.put("P_PURCHASE_NUMBER", purchaseNumber);//支付买家数
					tempMap.put("P_COUNT", payCountMap.get(brand_id)==null?0:Float.parseFloat(payCountMap.get(brand_id).toString()));//支付商品数
					tempMap.put("P_MONEY", payMoney);//销售总额
				}
            }
            
            pr.setState(true);
			pr.setMessage("获取品牌构成成功");
			pr.setObj(list);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
            logger.error(e);
        }
        return pr;
	}
	
	/**
	 * 交易构成-数据趋势
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryDataTrend(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(paramMap.get("start_date"))||StringUtils.isEmpty(paramMap.get("end_date"))) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            List<Map<String, Object>> allDataList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> productVisitorCountList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> payPurchaseNumberMoneyCountList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> preFirstMoneyList = new ArrayList<Map<String, Object>>();
            List<String> time_list = new ArrayList<String>();
            if(paramMap.get("start_date").equals(paramMap.get("end_date"))) {//一天
            	time_list.add("00:00");
            	time_list.add("01:00");
            	time_list.add("02:00");
            	time_list.add("03:00");
            	time_list.add("04:00");
            	time_list.add("05:00");
            	time_list.add("06:00");
            	time_list.add("07:00");
            	time_list.add("08:00");
            	time_list.add("09:00");
            	time_list.add("10:00");
            	time_list.add("11:00");
            	time_list.add("12:00");
            	time_list.add("13:00");
            	time_list.add("14:00");
            	time_list.add("15:00");
            	time_list.add("16:00");
            	time_list.add("17:00");
            	time_list.add("18:00");
            	time_list.add("19:00");
            	time_list.add("20:00");
            	time_list.add("21:00");
            	time_list.add("22:00");
            	time_list.add("23:00");
            	/** 折线图 商品访客数 */
            	productVisitorCountList = transactionAnalysisDao.r_queryProductVisitorCountD_Chart(paramMap);
                /** 折线图 支付买家数、支付订单数、销售总额和支付商品数*/
                payPurchaseNumberMoneyCountList = transactionAnalysisDao.r_queryDataTrend_PayPurchaseNumberMoneyCountD_Chart(paramMap);
                /** 折线图 预定订单的定金 */
                preFirstMoneyList = transactionAnalysisDao.r_queryDataTrend_PreFirstMoneyD_Chart(paramMap);
            }else {
            	String start_time = paramMap.get("old_start_date").toString();		//开始时间
                String end_time = paramMap.get("old_end_date").toString();			//结束时间
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                Date start_time_date = sf.parse(start_time);
                Date end_time_date = sf.parse(end_time);
                String tempDate = null;

                //获取指定时间段所有日期数据
                while(end_time_date.compareTo(start_time_date)>=0){
                    tempDate =  sf.format(start_time_date);
                    time_list.add(tempDate);
                    start_time_date = DateUtils.addDay(start_time_date,1);
                }
                /** 折线图 商品访客数 */
                productVisitorCountList = transactionAnalysisDao.r_queryProductVisitorCount_Chart(paramMap);
                /** 折线图 支付买家数、支付订单数、销售总额和支付商品数 */
                payPurchaseNumberMoneyCountList = transactionAnalysisDao.r_queryDataTrend_PayPurchaseNumberMoneyCount_Chart(paramMap);
                /** 折线图 预定订单的定金 */
                preFirstMoneyList = transactionAnalysisDao.r_queryDataTrend_PreFirstMoney_Chart(paramMap);
            }
            
            //商品访客数
            Map<String,Object> productVisitorCountMap = list2Map(productVisitorCountList,"CREATE_DATE","VISITOR_COUNT");
            //支付商品数
            Map<String,Object> payCountMap = list2Map(payPurchaseNumberMoneyCountList,"CREATE_DATE","PAY_COUNT");
            //支付订单数
            Map<String,Object> payOrderNumMap = list2Map(payPurchaseNumberMoneyCountList,"CREATE_DATE","ORDER_NUM");
            //支付买家数
            Map<String,Object> payPurchaseNumberMap = list2Map(payPurchaseNumberMoneyCountList,"CREATE_DATE","PURCHASE_NUMBER");
            //销售总额
            Map<String,Object> payMoneyMap = list2Map(payPurchaseNumberMoneyCountList,"CREATE_DATE","PAY_MONEY");
            //预定订单的定金
            Map<String,Object> preFirstMoneyMap = list2Map(preFirstMoneyList,"CREATE_DATE","PRE_FIRST_MONEY");
            
            //开始数据拼装
          	String key="";
          	
          	Map<String, Object> map = null;
            for(String time : time_list) {
            	key = time;
            	/** 数据趋势-商品访客数 */
                float productVisitorCount = productVisitorCountMap.get(key)==null?0:Float.parseFloat(productVisitorCountMap.get(key).toString());
                /** 数据趋势-支付商品数 */
                float payCount = payCountMap.get(key)==null?0:Float.parseFloat(payCountMap.get(key).toString());
                /** 数据趋势-支付订单数 */
                float payOrderNum = payOrderNumMap.get(key)==null?0:Float.parseFloat(payOrderNumMap.get(key).toString());
                /** 数据趋势-支付买家数 */
                float payPurchaseNumber = payPurchaseNumberMap.get(key)==null?0:Float.parseFloat(payPurchaseNumberMap.get(key).toString());
                /** 数据趋势-销售总额 */
                float payMoney = payMoneyMap.get(key)==null?0:Float.parseFloat(payMoneyMap.get(key).toString());
                /** 数据趋势-预定订单的定金 */
                float preFirstMoney = preFirstMoneyMap.get(key)==null?0:Float.parseFloat(preFirstMoneyMap.get(key).toString());
                payMoney+=preFirstMoney;
                map = new HashMap<String, Object>();
                //商品访客数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", productVisitorCount);
              	map.put("PAGE_NAME", "商品访客数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//支付商品数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", payCount);
              	map.put("PAGE_NAME", "支付商品数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//支付订单数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", payOrderNum);
              	map.put("PAGE_NAME", "支付订单数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//支付买家数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", payPurchaseNumber);
              	map.put("PAGE_NAME", "支付买家数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//支付转化率
              	if(productVisitorCount==0||payPurchaseNumber==0){
              		map.put("CNT", 0);
              	}else{
              		float zhl = 100*(payPurchaseNumber/productVisitorCount);
              		map.put("CNT", m2(zhl));
              	}
              	map.put("CREATE_DATE", key);
              	map.put("PAGE_NAME", "支付转化率");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//销售总额
              	map.put("CREATE_DATE", key);
              	map.put("CNT", payMoney);
              	map.put("PAGE_NAME", "销售总额");
              	allDataList.add(map);
            }
            
            //将原始数据组装为Echart可识别的格式
            resultMap = createData("PAGE_NAME", time_list, allDataList);
            
            pr.setState(true);
			pr.setMessage("获取数据趋势折线成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
            logger.error(e);
        }
        return pr;
	}
	
	/**
	 * 终端下拉框
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryTerminalSelect(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            //终端下拉框列表
            List<Map<String, Object>> resultList = transactionAnalysisDao.r_queryTerminalSelect(paramMap);
          
            pr.setState(true);
			pr.setMessage("获取终端下拉框列表成功");
			pr.setObj(resultList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 品牌下拉框
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryBrandSelect(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            //品牌下拉框列表
            List<Map<String, Object>> resultList = transactionAnalysisDao.r_queryBrandSelect(paramMap);
          
            pr.setState(true);
			pr.setMessage("获取品牌下拉框列表成功");
			pr.setObj(resultList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	
	/**
	 * 将list转换成map
	 * @param list
	 * @param mapKey
	 * @param valueKey
	 * @return
	 */
	private Map<String,Object> list2Map(List<Map<String, Object>> list ,String mapKey,String valueKey){
		Map<String,Object> returnMap = new HashMap<String,Object>();
		int size = list.size();
		String key ="";
		Object value ="";
		for(int i=0;i<size;i++)
		{
			key = list.get(i).get(mapKey).toString();
			value = list.get(i).get(valueKey);
			returnMap.put(key, value);
		}
		return returnMap;
	}
	
	/**
	 * chart数据组装
	 * @param seriesName
	 * @param time_list
	 * @param data_list
	 * @return
	 */
	private Map<String, Object> createData(String seriesName,List<String> time_list,List<Map<String, Object>> data_list) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<Map<String, Object>> seriesData = new ArrayList<Map<String, Object>>();
        Map<String, Map<String, Object>> dataMap = new HashMap<String, Map<String, Object>>();
        Map<String, Object> dataMapVal = null;// 存储一个统计分组段的数据列表
        List<Double> dataList = null;
        Set<String> seriesNameSet = new HashSet<String>();
        if(data_list!=null && !data_list.isEmpty()){//有数据
            for (Map<String, Object> data : data_list) {
                String PAGE_NAME = data.get(seriesName) == null ? null: data.get(seriesName).toString();//分组统计类型
                seriesNameSet.add(PAGE_NAME);
                String ORDER_DATE = data.get("CREATE_DATE") == null ? DateUtils.format(new Date(), "YYYY-MM-dd") : data.get("CREATE_DATE").toString();
                double COUNT = data.get("CNT") == null ? 0 : Double.parseDouble(data.get("CNT").toString());//分组中某天的统计数量
                if (dataMap.containsKey(PAGE_NAME)) {// 已存在直接put
                    dataMapVal = dataMap.get(PAGE_NAME);
                    dataMapVal.put(ORDER_DATE, COUNT);
                } else {
                    dataMapVal = new HashMap<String, Object>();
                    dataMapVal.put(ORDER_DATE, COUNT);
                    dataMap.put(PAGE_NAME, dataMapVal);
                }
            }
            int i=0;
            for (String PAGE_NAME : dataMap.keySet()) {
                dataMapVal = dataMap.get(PAGE_NAME);
                dataList = new ArrayList<Double>();
                double count = 0;
                for (String time : time_list) {
                    if (StringUtils.isEmpty(time))
                        continue; // 日期为空，跳过该次循环
                    if (dataMapVal!=null && !StringUtils.isEmpty(dataMapVal.get(time))) {
                        count = dataMapVal.get(time) == null ? 0 : Double.parseDouble(dataMapVal.get(time).toString());
                    }
                    dataList.add(count);
                    count = 0;
                }
                Map<String, Object> seriesDataMap = new HashMap<String, Object>();
                seriesDataMap.put("name", PAGE_NAME);
                seriesDataMap.put("type", "line");
                seriesDataMap.put("yAxis", i);
                seriesDataMap.put("color", colors[i]);
                i++;
                seriesDataMap.put("data", dataList);
                seriesData.add(seriesDataMap);
            }
        }
        //排序
        Collections.sort(seriesData, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                return o1.get("name").toString().compareTo(o2.get("name").toString());
            }
        });
        resultMap.put("seriesData", seriesData);
        resultMap.put("xAxis", time_list);
        resultMap.put("legend", seriesNameSet);
        return resultMap;
    }
	
	/**
     * Float类型 保留两位小数
     */
    public static String m2(float v) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(v);
    }

}
