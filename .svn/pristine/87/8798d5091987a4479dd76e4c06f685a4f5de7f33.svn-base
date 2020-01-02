package com.tk.analysis.retail.service;

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
import com.tk.analysis.retail.dao.RetailAnalysisDao;
import com.tk.sys.util.DateUtils;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2019, Tongku
 * FileName : RetailAnalysisService.java
 * 
 *
 * @author yejingquan
 * @version 1.00
 * @date Dec 3, 2019
 */
@Service("RetailAnalysisService")
public class RetailAnalysisService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private RetailAnalysisDao retailAnalysisDao;
	@Resource
	private HomeAnalysisService homeAnalysisService;
	private String[] colors={"#2f4554 ", "#c23531", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF", "#001cf1", "#000000", "#50d400", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4",
            "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3"};
	/**
	 * 经销商概况-基本汇总
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAgentBasicSummary(HttpServletRequest request) {
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
            
        	/** 经销商总数 */
            int agentTotal = retailAnalysisDao.r_queryAgentCount(paramMap);
            retMap.put("agent_total", agentTotal);
            
            /** 小程序开通数 */
            float programCount = retailAnalysisDao.r_queryAgentProgramCount(paramMap);
            retMap.put("program_count", programCount);
            
            /** 零售客户总数 */
            float customerTotal = retailAnalysisDao.r_queryAgentCustomerTotal(paramMap);
            retMap.put("customer_total", customerTotal);
            
            /** 今日新增零售客户 */
            float addCustomerCount = retailAnalysisDao.r_queryAgentAddCustomerCount(paramMap);
            retMap.put("add_customer_count", addCustomerCount);
            
            /** 日活跃客户数 */
            float customer_livelyCount = retailAnalysisDao.r_queryAgentCustomerLivelyCount(paramMap);
            retMap.put("customer_lively_count", customer_livelyCount);
            
            pr.setState(true);
            pr.setMessage("获取基本汇总成功");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	/**
	 * 经销商概况-实时汇总
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAgentRealTimeSummary(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
        	List<String> time_list = new ArrayList<String>();
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
            //查询今日实时概况
            Map<String, Object> rtMap = new HashMap<String, Object>();
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
            
            /**************************************************今日相关统计    begin****************************************************/
            String dateNowStr = sdf.format(new Date()); 
            paramMap.put("date_short", dateNowStr);
            //实时汇总 支付买家数、销售件数、支付单笔数、订单销售总额
            Map<String,Object> purchaseNumberCountMoneyMap = retailAnalysisDao.r_queryAgentRealTime_PurchaseNumberCountMoney(paramMap);
            //支付买家数
            float td_payPurchaseNumber = purchaseNumberCountMoneyMap.get("PURCHASE_NUMBER")==null?0:Float.parseFloat(purchaseNumberCountMoneyMap.get("PURCHASE_NUMBER").toString());
            //订单销售总额
            float td_payMoney = purchaseNumberCountMoneyMap.get("PAY_MONEY")==null?0:Float.parseFloat(purchaseNumberCountMoneyMap.get("PAY_MONEY").toString());
            //销售件数
            float td_payCount = purchaseNumberCountMoneyMap.get("PAY_COUNT")==null?0:Float.parseFloat(purchaseNumberCountMoneyMap.get("PAY_COUNT").toString());
            //支付单笔数
            float td_payNum = purchaseNumberCountMoneyMap.get("PAY_NUM")==null?0:Float.parseFloat(purchaseNumberCountMoneyMap.get("PAY_NUM").toString());
          	float td_kdj = 0;
          	if(td_payPurchaseNumber > 0) {
          		td_kdj = td_payMoney/td_payPurchaseNumber;
          	}
          	rtMap.put("TD_PAY_MONEY", td_payMoney); //订单销售总额
          	rtMap.put("TD_PAY_NUM", td_payNum); 	//支付单笔数
          	rtMap.put("TD_PURCHASE_NUMBER", td_payPurchaseNumber); //支付买家数
          	rtMap.put("TD_PAY_COUNT", td_payCount); //销售件数
			rtMap.put("TD_KDJ", td_kdj); //客单价
			/**************************************************今日相关统计     end****************************************************/
			
			/**************************************************今日折线图相关统计     begin****************************************************/
			List<Float> lineTd = new ArrayList<Float>();
			/** 实时汇总 折线图 订单销售总额 **/
			List<Map<String, Object>> td_payMoneyList = retailAnalysisDao.r_queryAgentRealTime_PayMoney_Chart(paramMap);
			Map<String, Object> td_payMoneyMap = list2Map(td_payMoneyList,"CREATE_DATE","PAY_MONEY");

           	//开始数据拼装
          	String key="";
            for(int i=0;i<time_list.size();i++){
                key = time_list.get(i);
                //订单销售总额
                lineTd.add(td_payMoneyMap.get(key)==null?0:Float.parseFloat(td_payMoneyMap.get(key).toString()));
			}
			/**************************************************今日折线图相关统计     end****************************************************/
          	
			/**************************************************昨日相关统计     begin****************************************************/
			Calendar c = Calendar.getInstance();  
			c.add(Calendar.DATE, -1);  
			dateNowStr = sdf.format(c.getTime());  
            paramMap.put("date_short", dateNowStr);
            //实时汇总 支付买家数、销售件数、支付单笔数、订单销售总额
            purchaseNumberCountMoneyMap.clear();
            purchaseNumberCountMoneyMap = retailAnalysisDao.r_queryAgentRealTime_PurchaseNumberCountMoney(paramMap);
            //支付买家数
            float yd_payPurchaseNumber = purchaseNumberCountMoneyMap.get("PURCHASE_NUMBER")==null?0:Float.parseFloat(purchaseNumberCountMoneyMap.get("PURCHASE_NUMBER").toString());
            //订单销售总额
            float yd_payMoney = purchaseNumberCountMoneyMap.get("PAY_MONEY")==null?0:Float.parseFloat(purchaseNumberCountMoneyMap.get("PAY_MONEY").toString());
            //销售件数
            float yd_payCount = purchaseNumberCountMoneyMap.get("PAY_COUNT")==null?0:Float.parseFloat(purchaseNumberCountMoneyMap.get("PAY_COUNT").toString());
            //支付单笔数
            float yd_payNum = purchaseNumberCountMoneyMap.get("PAY_NUM")==null?0:Float.parseFloat(purchaseNumberCountMoneyMap.get("PAY_NUM").toString());
          	float yd_kdj = 0;
          	if(yd_payPurchaseNumber > 0) {
          		yd_kdj = yd_payMoney/yd_payPurchaseNumber;
          	}
          	rtMap.put("YD_PAY_MONEY", yd_payMoney); //订单销售总额
          	rtMap.put("YD_PAY_NUM", yd_payNum); 	//支付单笔数
          	rtMap.put("YD_PURCHASE_NUMBER", yd_payPurchaseNumber); //支付买家数
          	rtMap.put("YD_PAY_COUNT", yd_payCount); //销售件数
			rtMap.put("YD_KDJ", yd_kdj); //客单价
          	/**************************************************昨日相关统计     end****************************************************/
			
			/**************************************************昨日折线图相关统计     begin****************************************************/
			List<Float> lineYd = new ArrayList<Float>();
			/** 实时汇总 折线图 订单销售总额 **/
			List<Map<String, Object>> yd_payMoneyList = retailAnalysisDao.r_queryAgentRealTime_PayMoney_Chart(paramMap);
			Map<String, Object> yd_payMoneyMap = list2Map(yd_payMoneyList,"CREATE_DATE","PAY_MONEY");
          

           	//开始数据拼装
            for(int i=0;i<time_list.size();i++){
                key = time_list.get(i);
                //订单销售总额
                lineYd.add(yd_payMoneyMap.get(key)==null?0:Float.parseFloat(yd_payMoneyMap.get(key).toString()));
			}
			/**************************************************昨日折线图相关统计     end****************************************************/
			
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("rtMap", rtMap);
            resultMap.put("time_list", time_list);
            resultMap.put("lineTd", lineTd);
            resultMap.put("lineYd", lineYd);
            pr.setState(true);
            pr.setMessage("获取数据成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 经销商概况-实时汇总-线上订单量占比
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAgentRealTimeOrderRatio(HttpServletRequest request) {
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
            String dateNowStr = sdf.format(new Date()); 
            paramMap.put("date_short", dateNowStr);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            //查询总订单量
        	float orderNum = retailAnalysisDao.r_queryAgentRealTime_PayNum(paramMap);
        	resultMap.put("order_number", orderNum);
        	
            //查询线上订单量
            float online_orderNum = retailAnalysisDao.r_queryAgentRealTime_OnlinePayNum(paramMap);
            resultMap.put("online_order_number", online_orderNum);
          	
            pr.setState(true);
			pr.setMessage("获取线上订单量占比成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.info(e);
        }
        return pr;
	}
	/**
	 * 经销商概况-实时汇总-线上交易额占比
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAgentRealTimeTradeRatio(HttpServletRequest request) {
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
            String dateNowStr = sdf.format(new Date()); 
            paramMap.put("date_short", dateNowStr);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            //查询总交易额
        	float payMoney = retailAnalysisDao.r_queryAgentRealTime_PayMoney(paramMap);
        	resultMap.put("pay_money", payMoney);
            //查询线上交易额
            float online_payMoney = retailAnalysisDao.r_queryAgentRealTime_OnlinePayMoney(paramMap);
            resultMap.put("online_pay_money", online_payMoney);
          	
            pr.setState(true);
			pr.setMessage("获取线上交易额占比成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.info(e);
        }
        return pr;
	}
	
	
	/**
	 * 经销商概况-实时汇总-自营交易额占比
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAgentRealTimeZyRatio(HttpServletRequest request) {
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
            String dateNowStr = sdf.format(new Date()); 
            paramMap.put("date_short", dateNowStr);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            //查询总交易额
        	float payMoney = retailAnalysisDao.r_queryAgentRealTime_PayMoney(paramMap);
        	resultMap.put("pay_money", payMoney);
            //查询自营交易额
            float zy_payMoney = retailAnalysisDao.r_queryAgentRealTime_ZyPayMoney(paramMap);
            resultMap.put("zy_pay_money", zy_payMoney);
          	
            pr.setState(true);
			pr.setMessage("获取自营交易额占比成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.info(e);
        }
        return pr;
	}
	/**
	 * 经销商列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryAgentList(HttpServletRequest request) {
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
			List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
			/** 经销商总数 */
			int total = retailAnalysisDao.r_queryAgentCount(paramMap);
			//支付买家数
			String file_names_pnu = ":PURCHASE_NUMBER:";
			//支付单数
			String file_names_pn = ":PAY_NUM:";
			//成交额
			String file_names_pm = ":PAY_MONEY:";
			//线上支付笔数
			String file_names_opn = ":ONLINE_PAY_NUM:";
			//线上成交额
			String file_names_opm = ":ONLINE_PAY_MONEY:";
			List<Map<String, Object>> list=null;
			
			//需要查询的经销商列表
			List<String> userList = new ArrayList<String>();
			if((paramMap.containsKey("sort")&&!"".equals(paramMap.get("sort").toString()))
					&&(paramMap.containsKey("sort_by")&&!"".equals(paramMap.get("sort_by").toString()))){
				String sort = ":"+paramMap.get("sort").toString()+":";
				if(file_names_pnu.indexOf(sort)!=-1) {
					//支付买家数---获取排序后的经销商列表
					userList=retailAnalysisDao.r_queryAgent_PurchaseNumber_Agent(paramMap);
				}else if(file_names_pn.indexOf(sort)!=-1) {
					//支付单数---获取排序后的经销商列表
					userList=retailAnalysisDao.r_queryAgent_PayNum_Agent(paramMap);
				}else if(file_names_pm.indexOf(sort)!=-1) {
					//成交额---获取排序后的经销商列表
					userList=retailAnalysisDao.r_queryAgent_PayMoney_Agent(paramMap);
				}else if(file_names_opn.indexOf(sort)!=-1) {
					//线上支付笔数---获取排序后的经销商列表
					userList=retailAnalysisDao.r_queryAgent_OnlinePayNum_Agent(paramMap);
				}else if(file_names_opm.indexOf(sort)!=-1) {
					//线上成交额---获取排序后的经销商列表
					userList=retailAnalysisDao.r_queryAgent_OnlinePayMoney_Agent(paramMap);
				}else {
					gr.setState(false);
					gr.setMessage("未配置的排序字段，请联系管理员【"+paramMap.get("sort").toString()+"】");
					return gr;
				}
			}else{
				//查询默认排序的经销商列表
				userList=retailAnalysisDao.r_queryAgentListBy_Default(paramMap);
			}
			
			if(!userList.isEmpty()&&userList.size()>0){
				paramMap.put("userList", userList);
				list = retailAnalysisDao.r_queryAgentList(paramMap);
			}
			
			if (list != null && list.size() > 0) {
				paramMap.put("list", list);
				//支付单数，成交额，支付买家数
				List<Map<String,Object>> purchaseNumberMoneyNumList = retailAnalysisDao.r_queryAgent_PurchaseNumberMoneyNum(paramMap);
				Map<String,Object> purchaseNumberMap = list2Map(purchaseNumberMoneyNumList,"USER_ID","PURCHASE_NUMBER");
				Map<String,Object> payMoneyMap = list2Map(purchaseNumberMoneyNumList,"USER_ID","PAY_MONEY");
				Map<String,Object> payNumMap = list2Map(purchaseNumberMoneyNumList,"USER_ID","PAY_NUM");
				
				//线上支付笔数，线上成交额
				List<Map<String,Object>> onlinePayMoneyNumList = retailAnalysisDao.r_queryAgent_OnlinePayMoneyNum(paramMap);
				Map<String,Object> onlinePayMoneyMap = list2Map(onlinePayMoneyNumList,"USER_ID","ONLINE_PAY_MONEY");
				Map<String,Object> onlinePayNumMap = list2Map(onlinePayMoneyNumList,"USER_ID","ONLINE_PAY_NUM");
                
				Map<String,Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String userId = tempMap.get("USER_ID").toString();
					
					//支付买家数
					tempMap.put("PURCHASE_NUMBER", purchaseNumberMap.get(userId)==null?0:Float.parseFloat(purchaseNumberMap.get(userId).toString()));
					//成交额
					tempMap.put("PAY_MONEY", payMoneyMap.get(userId)==null?0:Float.parseFloat(payMoneyMap.get(userId).toString()));
					//支付单数
					tempMap.put("PAY_NUM", payNumMap.get(userId)==null?0:Float.parseFloat(payNumMap.get(userId).toString()));
					//线上支付笔数
					tempMap.put("ONLINE_PAY_NUM", onlinePayNumMap.get(userId)==null?0:Float.parseFloat(onlinePayNumMap.get(userId).toString()));
					//线上成交额
					tempMap.put("ONLINE_PAY_MONEY", onlinePayMoneyMap.get(userId)==null?0:Float.parseFloat(onlinePayMoneyMap.get(userId).toString()));
				}
				
				gr.setState(true);
				gr.setMessage("获取经销商列表成功");
				gr.setObj(list);
				gr.setTotal(total);
			}else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	/**
	 * 经销商分析
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryAgentSearch(HttpServletRequest request) {
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
			List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
			/** 经销商总数 */
			int total = retailAnalysisDao.r_queryAgentCount(paramMap);
			/** 经销商分析 */
			List<Map<String, Object>> list= retailAnalysisDao.r_queryAgentSearch(paramMap);
			
			if (list != null && list.size() > 0) {
				gr.setState(true);
				gr.setMessage("获取经销商列表成功");
				gr.setObj(list);
				gr.setTotal(total);
			}else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	
	/**
	 * 经销商分析详情-基本信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAgentDetailBasic(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("user_id"))) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
            String dateNowStr = sdf.format(new Date()); 
            paramMap.put("date_short", dateNowStr);
            //查询基本信息
            Map<String, Object> resultMap = retailAnalysisDao.r_queryAgentDetailBasic(paramMap);
          	
            pr.setState(true);
			pr.setMessage("获取经销商详情-基本信息成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.info(e);
        }
        return pr;
	}
	
	/**
	 * 经销商详情-实时汇总
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAgentDetailRealTimeSummary(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
        	// 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("user_id"))) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            
        	List<String> time_list = new ArrayList<String>();
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
            //查询今日实时概况
            Map<String, Object> rtMap = new HashMap<String, Object>();
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
            
            /**************************************************今日相关统计    begin****************************************************/
            String dateNowStr = sdf.format(new Date()); 
            paramMap.put("date_short", dateNowStr);
            //实时汇总 支付买家数、销售件数、支付单笔数、订单销售总额
            Map<String,Object> purchaseNumberCountMoneyMap = retailAnalysisDao.r_queryAgentDetail_RealTime_PurchaseNumberCountMoney(paramMap);
            //支付买家数
            float td_payPurchaseNumber = purchaseNumberCountMoneyMap.get("PURCHASE_NUMBER")==null?0:Float.parseFloat(purchaseNumberCountMoneyMap.get("PURCHASE_NUMBER").toString());
            //订单销售总额
            float td_payMoney = purchaseNumberCountMoneyMap.get("PAY_MONEY")==null?0:Float.parseFloat(purchaseNumberCountMoneyMap.get("PAY_MONEY").toString());
            //销售件数
            float td_payCount = purchaseNumberCountMoneyMap.get("PAY_COUNT")==null?0:Float.parseFloat(purchaseNumberCountMoneyMap.get("PAY_COUNT").toString());
            //支付单笔数
            float td_payNum = purchaseNumberCountMoneyMap.get("PAY_NUM")==null?0:Float.parseFloat(purchaseNumberCountMoneyMap.get("PAY_NUM").toString());
            //访客数
            float td_visitorCount = retailAnalysisDao.r_queryAgentDetail_RealTime_VisitorCount(paramMap);
          	//订单笔数
            float td_orderNum = retailAnalysisDao.r_queryAgentDetail_RealTime_OrderNum(paramMap);
          	float td_kdj = 0;
          	if(td_payPurchaseNumber > 0) {
          		td_kdj = td_payMoney/td_payPurchaseNumber;
          	}
          	rtMap.put("TD_PAY_MONEY", td_payMoney); //订单销售总额
          	rtMap.put("TD_VISITOR_COUNT", td_visitorCount); //访客数
          	rtMap.put("TD_ORDER_NUM", td_orderNum); //订单数
          	rtMap.put("TD_PAY_NUM", td_payNum); 	//支付单笔数
          	rtMap.put("TD_PURCHASE_NUMBER", td_payPurchaseNumber); //支付买家数
          	rtMap.put("TD_PAY_COUNT", td_payCount); //销售件数
			rtMap.put("TD_KDJ", td_kdj); //客单价
			/**************************************************今日相关统计     end****************************************************/
			
			/**************************************************今日折线图相关统计     begin****************************************************/
			List<Float> lineTd = new ArrayList<Float>();
			/** 实时汇总 折线图 订单销售总额 **/
			List<Map<String, Object>> td_payMoneyList = retailAnalysisDao.r_queryAgentDetail_RealTime_PayMoney_Chart(paramMap);
			Map<String, Object> td_payMoneyMap = list2Map(td_payMoneyList,"CREATE_DATE","PAY_MONEY");

           	//开始数据拼装
          	String key="";
            for(int i=0;i<time_list.size();i++){
                key = time_list.get(i);
                //订单销售总额
                lineTd.add(td_payMoneyMap.get(key)==null?0:Float.parseFloat(td_payMoneyMap.get(key).toString()));
			}
			/**************************************************今日折线图相关统计     end****************************************************/
          	
			/**************************************************昨日相关统计     begin****************************************************/
			Calendar c = Calendar.getInstance();  
			c.add(Calendar.DATE, -1);  
			dateNowStr = sdf.format(c.getTime());  
            paramMap.put("date_short", dateNowStr);
            //实时汇总 支付买家数、销售件数、支付单笔数、订单销售总额
            purchaseNumberCountMoneyMap.clear();
            purchaseNumberCountMoneyMap = retailAnalysisDao.r_queryAgentDetail_RealTime_PurchaseNumberCountMoney(paramMap);
            //支付买家数
            float yd_payPurchaseNumber = purchaseNumberCountMoneyMap.get("PURCHASE_NUMBER")==null?0:Float.parseFloat(purchaseNumberCountMoneyMap.get("PURCHASE_NUMBER").toString());
            //订单销售总额
            float yd_payMoney = purchaseNumberCountMoneyMap.get("PAY_MONEY")==null?0:Float.parseFloat(purchaseNumberCountMoneyMap.get("PAY_MONEY").toString());
            //销售件数
            float yd_payCount = purchaseNumberCountMoneyMap.get("PAY_COUNT")==null?0:Float.parseFloat(purchaseNumberCountMoneyMap.get("PAY_COUNT").toString());
            //支付单笔数
            float yd_payNum = purchaseNumberCountMoneyMap.get("PAY_NUM")==null?0:Float.parseFloat(purchaseNumberCountMoneyMap.get("PAY_NUM").toString());
            //访客数
            float yd_visitorCount = retailAnalysisDao.r_queryAgentDetail_RealTime_VisitorCount(paramMap);
          	//订单笔数
            float yd_orderNum = retailAnalysisDao.r_queryAgentDetail_RealTime_OrderNum(paramMap);
          	float yd_kdj = 0;
          	if(yd_payPurchaseNumber > 0) {
          		yd_kdj = yd_payMoney/yd_payPurchaseNumber;
          	}
          	rtMap.put("YD_PAY_MONEY", yd_payMoney); //订单销售总额
          	rtMap.put("YD_VISITOR_COUNT", yd_visitorCount); //访客数
          	rtMap.put("YD_ORDER_NUM", yd_orderNum); //订单数
          	rtMap.put("YD_PAY_NUM", yd_payNum); 	//支付单笔数
          	rtMap.put("YD_PURCHASE_NUMBER", yd_payPurchaseNumber); //支付买家数
          	rtMap.put("YD_PAY_COUNT", yd_payCount); //销售件数
			rtMap.put("YD_KDJ", yd_kdj); //客单价
          	/**************************************************昨日相关统计     end****************************************************/
			
			/**************************************************昨日折线图相关统计     begin****************************************************/
			List<Float> lineYd = new ArrayList<Float>();
			/** 实时汇总 折线图 订单销售总额 **/
			List<Map<String, Object>> yd_payMoneyList = retailAnalysisDao.r_queryAgentDetail_RealTime_PayMoney_Chart(paramMap);
			Map<String, Object> yd_payMoneyMap = list2Map(yd_payMoneyList,"CREATE_DATE","PAY_MONEY");
          

           	//开始数据拼装
            for(int i=0;i<time_list.size();i++){
                key = time_list.get(i);
                //订单销售总额
                lineYd.add(yd_payMoneyMap.get(key)==null?0:Float.parseFloat(yd_payMoneyMap.get(key).toString()));
			}
			/**************************************************昨日折线图相关统计     end****************************************************/
			
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("rtMap", rtMap);
            resultMap.put("time_list", time_list);
            resultMap.put("lineTd", lineTd);
            resultMap.put("lineYd", lineYd);
            pr.setState(true);
            pr.setMessage("获取数据成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 经销商详情-实时汇总-线上订单量占比
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAgentDetailRealTimeOrderRatio(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("user_id"))) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
            String dateNowStr = sdf.format(new Date()); 
            paramMap.put("date_short", dateNowStr);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            //查询总订单量
        	float orderNum = retailAnalysisDao.r_queryAgentDetail_RealTime_PayNum(paramMap);
        	resultMap.put("order_number", orderNum);
        	
            //查询线上订单量
            float online_orderNum = retailAnalysisDao.r_queryAgentDetail_RealTime_OnlinePayNum(paramMap);
            resultMap.put("online_order_number", online_orderNum);
          	
            pr.setState(true);
			pr.setMessage("获取线上订单量占比成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.info(e);
        }
        return pr;
	}
	/**
	 * 经销商详情-实时汇总-线上交易额占比
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAgentDetailRealTimeTradeRatio(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("user_id"))) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
            String dateNowStr = sdf.format(new Date()); 
            paramMap.put("date_short", dateNowStr);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            //查询总交易额
        	float payMoney = retailAnalysisDao.r_queryAgentDetail_RealTime_PayMoney(paramMap);
        	resultMap.put("pay_money", payMoney);
            //查询线上交易额
            float online_payMoney = retailAnalysisDao.r_queryAgentDetail_RealTime_OnlinePayMoney(paramMap);
            resultMap.put("online_pay_money", online_payMoney);
          	
            pr.setState(true);
			pr.setMessage("获取线上交易额占比成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.info(e);
        }
        return pr;
	}
	
	
	/**
	 * 经销商详情-实时汇总-新老客户占比
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAgentDetailRealTimeCustomerRatio(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("user_id"))) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
            String dateNowStr = sdf.format(new Date()); 
            paramMap.put("date_short", dateNowStr);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            //查询新客户
        	float newCustomer = retailAnalysisDao.r_queryAgentDetail_RealTime_NewCustomer(paramMap);
        	resultMap.put("new_customer", newCustomer);
            //查询总客户
            int customerTotal = retailAnalysisDao.r_queryAgentDetail_CustomerCount(paramMap);
            resultMap.put("customer_total", customerTotal);
          	
            pr.setState(true);
			pr.setMessage("获取新老客户占比成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.info(e);
        }
        return pr;
	}
	
	/**
	 * 经销商详情-客户分析-基本汇总
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAgentDetailCustomerSummary(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("user_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            
        	/** 客户数 */
            int customerTotal = retailAnalysisDao.r_queryAgentDetail_CustomerCount(paramMap);
            retMap.put("customer_total", customerTotal);
            
            /** 今日新增零售客户 */
            float addCustomerCount = retailAnalysisDao.r_queryAgentDetail_AddCustomerCount(paramMap);
            retMap.put("add_customer_count", addCustomerCount);
            
            /** 日活跃客户数 */
            float customer_livelyCount = retailAnalysisDao.r_queryAgentDetail_CustomerLivelyCount(paramMap);
            retMap.put("customer_lively_count", customer_livelyCount);
            
            /** 累计购买客户 */
            float supCustomerCount = retailAnalysisDao.r_queryAgentDetail_SupCustomerCount(paramMap);
            retMap.put("sup_customer_count", supCustomerCount);
            
            /** 复购客户数 */
            float rpCustomerCount = retailAnalysisDao.r_queryAgentDetail_RpCustomerCount(paramMap);
            retMap.put("rp_customer_count", rpCustomerCount);
            
            pr.setState(true);
            pr.setMessage("获取经销商详情-基本汇总成功");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	/**
	 * 经销商详情-客户分析-来源渠道
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAgentDetailCustomerSource(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("user_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
            paramMap.put("type", 1);//线下
        	/** 客户来源 */
            float offline_customerCount = retailAnalysisDao.r_queryAgentDetail_CustomerSource(paramMap);
            Map<String, Object> m = new HashMap<String, Object>();
        	m.put("PAGE_NAME", "线下");
        	m.put("CNT", offline_customerCount);
        	resultList.add(m);
            
            paramMap.put("type", 2);//线上
            /** 客户来源 */
            float online_customerCount = retailAnalysisDao.r_queryAgentDetail_CustomerSource(paramMap);
            m = new HashMap<String, Object>();
        	m.put("PAGE_NAME", "线上");
        	m.put("CNT", online_customerCount);
        	resultList.add(m);
            
            pr.setState(true);
            pr.setMessage("获取经销商详情-来源渠道成功");
            pr.setObj(resultList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	/**
	 * 经销商详情-客户分析-客户列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryAgentDetailCustomerList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
			if(paramMap.size() == 0||StringUtils.isEmpty(paramMap.get("user_id"))) {
            	gr.setState(false);
            	gr.setMessage("参数缺失");
                return gr;
            }
			/** 客户总数 */
			int total = retailAnalysisDao.r_queryAgentDetail_CustomerCount(paramMap);
			//支付单数
			String file_names_pn = ":PAY_NUM:";
			//成交额
			String file_names_pm = ":PAY_MONEY:";
			List<Map<String, Object>> list=null;
			
			//需要查询的客户列表
			List<String> userList = new ArrayList<String>();
			if((paramMap.containsKey("sort")&&!"".equals(paramMap.get("sort").toString()))
					&&(paramMap.containsKey("sort_by")&&!"".equals(paramMap.get("sort_by").toString()))){
				String sort = ":"+paramMap.get("sort").toString()+":";
				if(file_names_pn.indexOf(sort)!=-1) {
					//支付单数---获取排序后的客户列表
					userList=retailAnalysisDao.r_queryCustomer_PayNum_Customer(paramMap);
				}else if(file_names_pm.indexOf(sort)!=-1) {
					//成交额---获取排序后的客户列表
					userList=retailAnalysisDao.r_queryCustomer_PayMoney_Customer(paramMap);
				}else {
					gr.setState(false);
					gr.setMessage("未配置的排序字段，请联系管理员【"+paramMap.get("sort").toString()+"】");
					return gr;
				}
			}else{
				//查询默认排序的客户列表
				userList=retailAnalysisDao.r_queryCustomerListBy_Default(paramMap);
			}
			
			if(!userList.isEmpty()&&userList.size()>0){
				paramMap.put("userList", userList);
				list = retailAnalysisDao.r_queryCustomerList(paramMap);
			}
			
			if (list != null && list.size() > 0) {
				paramMap.put("list", list);
				//购买次数，成交额
				List<Map<String,Object>> payMoneyNumList = retailAnalysisDao.r_queryCustomer_PayMoneyNum(paramMap);
				Map<String,Object> payMoneyMap = list2Map(payMoneyNumList,"USER_ID","PAY_MONEY");
				Map<String,Object> payNumMap = list2Map(payMoneyNumList,"USER_ID","PAY_NUM");
				
				Map<String,Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String userId = tempMap.get("USER_ID").toString();
					//购买次数
					tempMap.put("PAY_NUM", payNumMap.get(userId)==null?0:Float.parseFloat(payNumMap.get(userId).toString()));
					//成交额
					tempMap.put("PAY_MONEY", payMoneyMap.get(userId)==null?0:Float.parseFloat(payMoneyMap.get(userId).toString()));
				}
				
				gr.setState(true);
				gr.setMessage("获取客户列表成功");
				gr.setObj(list);
				gr.setTotal(total);
			}else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	/**
	 * 经销商详情-销售分析-下单笔数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAgentDetailSaleOrderNum(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("user_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_count = 0;
        	/** 下单笔数 */
        	t_count = retailAnalysisDao.r_queryAgentDetail_Sale_OrderNum(paramMap);
        	Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            //较上个周期
			float l_count = 0;
            l_count = retailAnalysisDao.r_queryAgentDetail_Sale_OrderNum(paramMap);
            if(t_count == 0 || l_count == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_count-l_count)/l_count*100));
            }
            resultMap.put("order_num", t_count);
            
            pr.setState(true);
            pr.setMessage("获取下单笔数成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 经销商详情-销售分析-支付笔数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAgentDetailSalePayNum(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("user_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_count = 0;
        	/** 支付笔数 */
        	t_count = retailAnalysisDao.r_queryAgentDetail_Sale_PayNum(paramMap);
        	Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            //较上个周期
			float l_count = 0;
            l_count = retailAnalysisDao.r_queryAgentDetail_Sale_PayNum(paramMap);
            if(t_count == 0 || l_count == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_count-l_count)/l_count*100));
            }
            resultMap.put("pay_num", t_count);
            
            pr.setState(true);
            pr.setMessage("获取支付笔数成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 经销商详情-销售分析-成交额
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAgentDetailSalePayMoney(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("user_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_money = 0;
        	/** 成交额 */
        	t_money = retailAnalysisDao.r_queryAgentDetail_Sale_PayMoney(paramMap);
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
			l_money = retailAnalysisDao.r_queryAgentDetail_Sale_PayMoney(paramMap);
            if(t_money == 0 || l_money == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_money-l_money)/l_money*100));
            }
            resultMap.put("pay_money", t_money);
            
            pr.setState(true);
            pr.setMessage("获取成交额成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 经销商详情-销售分析-商品销售总额
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAgentDetailSaleProductMoney(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("user_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_money = 0;
        	/** 商品销售总额 */
        	t_money = retailAnalysisDao.r_queryAgentDetail_Sale_ProductMoney(paramMap);
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
			l_money = retailAnalysisDao.r_queryAgentDetail_Sale_ProductMoney(paramMap);
            if(t_money == 0 || l_money == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_money-l_money)/l_money*100));
            }
            resultMap.put("product_money", t_money);
            
            pr.setState(true);
            pr.setMessage("获取商品销售总额成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 经销商详情-销售分析-商品销量
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAgentDetailSalePayCount(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("user_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_count = 0;
        	/** 商品销量 */
        	t_count = retailAnalysisDao.r_queryAgentDetail_Sale_PayCount(paramMap);
        	Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            //较上个周期
			float l_count = 0;
            l_count = retailAnalysisDao.r_queryAgentDetail_Sale_PayCount(paramMap);
            if(t_count == 0 || l_count == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_count-l_count)/l_count*100));
            }
            resultMap.put("pay_count", t_count);
            
            pr.setState(true);
            pr.setMessage("获取商品销量成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 经销商详情-销售分析-退款金额
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAgentDetailSaleReturnMoney(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("user_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_money = 0;
        	/** 退款金额 */
        	t_money = retailAnalysisDao.r_queryAgentDetail_Sale_ReturnMoney(paramMap);
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
			l_money = retailAnalysisDao.r_queryAgentDetail_Sale_ReturnMoney(paramMap);
            if(t_money == 0 || l_money == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_money-l_money)/l_money*100));
            }
            resultMap.put("pay_money", t_money);
            
            pr.setState(true);
            pr.setMessage("获取成交额成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 经销商详情-销售分析-未发退货数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object queryAgentDetailSaleUnsentReturnCount(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("user_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_count = 0;
        	/** 未发退货数 */
        	t_count = retailAnalysisDao.r_queryAgentDetail_Sale_UnsentReturnCount(paramMap);
        	Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            //较上个周期
			float l_count = 0;
            l_count = retailAnalysisDao.r_queryAgentDetail_Sale_UnsentReturnCount(paramMap);
            if(t_count == 0 || l_count == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_count-l_count)/l_count*100));
            }
            resultMap.put("pay_count", t_count);
            
            pr.setState(true);
            pr.setMessage("获取未发退货数成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 经销商详情-销售分析-已发退货数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAgentDetailSaleSentReturnCount(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("user_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_count = 0;
        	/** 未发退货数 */
        	t_count = retailAnalysisDao.r_queryAgentDetail_Sale_SentReturnCount(paramMap);
        	Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            //较上个周期
			float l_count = 0;
            l_count = retailAnalysisDao.r_queryAgentDetail_Sale_SentReturnCount(paramMap);
            if(t_count == 0 || l_count == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_count-l_count)/l_count*100));
            }
            resultMap.put("pay_count", t_count);
            
            pr.setState(true);
            pr.setMessage("获取已发退货数成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 经销商详情-销售分析-支付买家数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAgentDetailSalePurchaseNumber(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("user_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_count = 0;
        	/** 支付买家数 */
        	t_count = retailAnalysisDao.r_queryAgentDetail_Sale_PurchaseNumber(paramMap);
        	Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            //较上个周期
			float l_count = 0;
            l_count = retailAnalysisDao.r_queryAgentDetail_Sale_PurchaseNumber(paramMap);
            if(t_count == 0 || l_count == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_count-l_count)/l_count*100));
            }
            resultMap.put("pay_count", t_count);
            
            pr.setState(true);
            pr.setMessage("获取支付买家数成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 经销商详情-销售分析-客单价
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAgentDetailSaleKdj(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("user_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_count = 0;
        	/** 支付买家数 */
        	t_count = retailAnalysisDao.r_queryAgentDetail_Sale_PurchaseNumber(paramMap);
        	float t_money = 0;
        	/** 成交额 */
        	t_money = retailAnalysisDao.r_queryAgentDetail_Sale_PayMoney(paramMap);
        	float t_kdj = 0;
          	if(t_count > 0) {
          		t_kdj = t_money/t_count;
          	}
        	Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            //较上个周期
			float l_count = 0;
			l_count = retailAnalysisDao.r_queryAgentDetail_Sale_PurchaseNumber(paramMap);
        	float l_money = 0;
        	l_money = retailAnalysisDao.r_queryAgentDetail_Sale_PayMoney(paramMap);
        	float l_kdj = 0;
          	if(l_count > 0) {
          		l_kdj = l_money/l_count;
          	}
            if(t_kdj == 0 || l_kdj == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_kdj-l_kdj)/l_kdj*100));
            }
            resultMap.put("kdj", t_kdj);
            
            pr.setState(true);
            pr.setMessage("获取客单价成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 经销商详情-销售分析-访客数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAgentDetailSaleVisitorCount(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("user_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_count = 0;
        	/** 访客数 */
        	t_count = retailAnalysisDao.r_queryAgentDetail_Sale_VisitorCount(paramMap);
        	Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            //较上个周期
			float l_count = 0;
            l_count = retailAnalysisDao.r_queryAgentDetail_Sale_VisitorCount(paramMap);
            if(t_count == 0 || l_count == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_count-l_count)/l_count*100));
            }
            resultMap.put("visitor_count", t_count);
            
            pr.setState(true);
            pr.setMessage("获取访客数成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 经销商详情-销售分析-折线
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAgentDetailSaleChart(HttpServletRequest request) {
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
            List<Map<String, Object>> allDataList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> ordeNumList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> payNumList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> purchaseNumberCountMoneyList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> returnMoneyList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> unsentReturnCountList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> sentReturnCountList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> visitorCountList = new ArrayList<Map<String, Object>>();
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
                /** 折线图 下单笔数 */
            	ordeNumList = retailAnalysisDao.r_queryAgentDetail_OrderNumD_Chart(paramMap);
            	/** 折线图 支付笔数 */
            	payNumList = retailAnalysisDao.r_queryAgentDetail_PayNumD_Chart(paramMap);
                /** 折线图 支付买家数、支付件数、成交额 */
                purchaseNumberCountMoneyList = retailAnalysisDao.r_queryAgentDetail_PurchaseNumberCountMoneyD_Chart(paramMap);
                /** 折线图 退款金额 */
                returnMoneyList = retailAnalysisDao.r_queryAgentDetail_ReturnMoneyD_Chart(paramMap);
                /** 折线图 未发退货数 */
                unsentReturnCountList = retailAnalysisDao.r_queryAgentDetail_UnsentReturnCountD_Chart(paramMap);
                /** 折线图 已发退货数 */
                sentReturnCountList = retailAnalysisDao.r_queryAgentDetail_SentReturnCountD_Chart(paramMap);
                /** 折线图 访客数 */
                visitorCountList = retailAnalysisDao.r_queryAgentDetail_VisitorCountD_Chart(paramMap);
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
                /** 折线图 下单笔数 */
            	ordeNumList = retailAnalysisDao.r_queryAgentDetail_OrderNum_Chart(paramMap);
            	/** 折线图 支付笔数 */
            	payNumList = retailAnalysisDao.r_queryAgentDetail_PayNum_Chart(paramMap);
                /** 折线图 支付买家数、支付件数、成交额 */
                purchaseNumberCountMoneyList = retailAnalysisDao.r_queryAgentDetail_PurchaseNumberCountMoney_Chart(paramMap);
                /** 折线图 退款金额 */
                returnMoneyList = retailAnalysisDao.r_queryAgentDetail_ReturnMoney_Chart(paramMap);
                /** 折线图 未发退货数 */
                unsentReturnCountList = retailAnalysisDao.r_queryAgentDetail_UnsentReturnCount_Chart(paramMap);
                /** 折线图 已发退货数 */
                sentReturnCountList = retailAnalysisDao.r_queryAgentDetail_SentReturnCount_Chart(paramMap);
                /** 折线图 访客数 */
                visitorCountList = retailAnalysisDao.r_queryAgentDetail_VisitorCount_Chart(paramMap);
            }
            
            //下单笔数
            Map<String,Object> ordeNumMap = list2Map(ordeNumList,"CREATE_DATE","ORDER_NUM");
            //支付单笔数
            Map<String,Object> payNumMap = list2Map(payNumList,"CREATE_DATE","PAY_NUM");
            //支付买家数
            Map<String,Object> purchaseNumberMap = list2Map(purchaseNumberCountMoneyList,"CREATE_DATE","PURCHASE_NUMBER");
            //支付件数
            Map<String,Object> payCountMap = list2Map(purchaseNumberCountMoneyList,"CREATE_DATE","PAY_COUNT");
            //成交额
            Map<String,Object> payMoneyMap = list2Map(purchaseNumberCountMoneyList,"CREATE_DATE","PAY_MONEY");
            //商品销售总额
            Map<String,Object> productMoneyMap = list2Map(purchaseNumberCountMoneyList,"CREATE_DATE","PRODUCT_MONEY");
            //退款金额
            Map<String,Object> returnMoneyMap = list2Map(returnMoneyList,"CREATE_DATE","RETURN_MONEY");
            //未发退货数
            Map<String,Object> unsentReturnCountMap = list2Map(unsentReturnCountList,"CREATE_DATE","UNSENT_RETURN_COUNT");
            //已发退货数
            Map<String,Object> sentReturnCountMap = list2Map(sentReturnCountList,"CREATE_DATE","SENT_RETURN_COUNT");
            //访客数
            Map<String,Object> visitorCountMap = list2Map(visitorCountList,"CREATE_DATE","VISITOR_COUNT");
            
            //开始数据拼装
          	String key="";
          	
          	Map<String, Object> map = null;
            for(String time : time_list) {
            	key = time;
                /** 下单笔数 */
                float orderNum = ordeNumMap.get(key)==null?0:Float.parseFloat(ordeNumMap.get(key).toString());
                /** 支付单笔数 */
                float payNum = payNumMap.get(key)==null?0:Float.parseFloat(payNumMap.get(key).toString());
                /** 支付买家数 */
                float purchaseNumber = purchaseNumberMap.get(key)==null?0:Float.parseFloat(purchaseNumberMap.get(key).toString());
                /** 支付件数 */
                float payCount = payCountMap.get(key)==null?0:Float.parseFloat(payCountMap.get(key).toString());
                /** 成交额 */
                float payMoney = payMoneyMap.get(key)==null?0:Float.parseFloat(payMoneyMap.get(key).toString());
                /** 商品销售总额 */
                float productMoney = productMoneyMap.get(key)==null?0:Float.parseFloat(productMoneyMap.get(key).toString());
                /** 退款金额 */
                float returnMoney = returnMoneyMap.get(key)==null?0:Float.parseFloat(returnMoneyMap.get(key).toString());
                /** 未发退货数 */
                float unsentReturnCount = unsentReturnCountMap.get(key)==null?0:Float.parseFloat(unsentReturnCountMap.get(key).toString());
                /** 已发退货数 */
                float sentReturnCount = sentReturnCountMap.get(key)==null?0:Float.parseFloat(sentReturnCountMap.get(key).toString());
                /** 访客数 */
                float visitorCount = visitorCountMap.get(key)==null?0:Float.parseFloat(visitorCountMap.get(key).toString());
                
              	map = new HashMap<String, Object>();
              	//下单笔数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", orderNum);
              	map.put("PAGE_NAME", "下单笔数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//支付笔数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", payNum);
              	map.put("PAGE_NAME", "支付笔数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//商品销量
              	map.put("CREATE_DATE", key);
              	map.put("CNT", payCount);
              	map.put("PAGE_NAME", "商品销量");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//成交额
              	map.put("CREATE_DATE", key);
              	map.put("CNT", payMoney);
              	map.put("PAGE_NAME", "成交额");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//商品销售总额
              	map.put("CREATE_DATE", key);
              	map.put("CNT", productMoney);
              	map.put("PAGE_NAME", "商品销售总额");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//支付买家数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", purchaseNumber);
              	map.put("PAGE_NAME", "支付买家数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//退款金额
              	map.put("CNT", returnMoney);
              	map.put("CREATE_DATE", key);
              	map.put("PAGE_NAME", "退款金额");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//未发退货数
              	map.put("CNT", unsentReturnCount);
              	map.put("CREATE_DATE", key);
              	map.put("PAGE_NAME", "未发退货数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//已发退货数
              	map.put("CNT", sentReturnCount);
              	map.put("CREATE_DATE", key);
              	map.put("PAGE_NAME", "已发退货数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//访客数
              	map.put("CNT", visitorCount);
              	map.put("CREATE_DATE", key);
              	map.put("PAGE_NAME", "访客数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//客单价
              	if(purchaseNumber == 0||payMoney == 0) {
              		map.put("CNT", 0);
              	}else {
              		map.put("CNT", payMoney/purchaseNumber);
              	}
              	map.put("CREATE_DATE", key);
              	map.put("PAGE_NAME", "客单价");
              	allDataList.add(map);
            }
            
            //将原始数据组装为Echart可识别的格式
            resultMap = createData("PAGE_NAME", time_list, allDataList);
            
            pr.setState(true);
			pr.setMessage("获取销售分析折线图成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 经销商详情-销售分析-商品列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryAgentDetailSaleProductList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
			if(paramMap.size() == 0||StringUtils.isEmpty(paramMap.get("user_id"))) {
            	gr.setState(false);
            	gr.setMessage("参数缺失");
                return gr;
            }
			
			/** 经销商商品总数 */
			int total = retailAnalysisDao.r_queryAgentDetailProductCount(paramMap);
			//销量
			String file_names_pc = ":PAY_COUNT:";
			//销售总额
			String file_names_pm = ":PAY_MONEY:";
			//未发退货数
			String file_names_urc = ":UNSENT_RETURN_COUNT:";
			//已发退货数
			String file_names_src = ":SENT_RETURN_COUNT:";
			List<Map<String, Object>> list=null;
			
			//需要查询的商品列表
			List<String> productList = new ArrayList<String>();
			if((paramMap.containsKey("sort")&&!"".equals(paramMap.get("sort").toString()))
					&&(paramMap.containsKey("sort_by")&&!"".equals(paramMap.get("sort_by").toString()))){
				String sort = ":"+paramMap.get("sort").toString()+":";
				if(file_names_pc.indexOf(sort)!=-1) {
					//销量---获取排序后的商品列表
					productList=retailAnalysisDao.r_queryAgentDetail_PayCount_Product(paramMap);
				}else if(file_names_pm.indexOf(sort)!=-1) {
					//销售总额---获取排序后的商品列表
					productList=retailAnalysisDao.r_queryAgentDetail_PayMoney_Product(paramMap);
				}else if(file_names_urc.indexOf(sort)!=-1) {
					//未发退货数---获取排序后的商品列表
					productList=retailAnalysisDao.r_queryAgentDetail_UnsentReturnCount_Product(paramMap);
				}else if(file_names_src.indexOf(sort)!=-1) {
					//已发退货数---获取排序后的商品列表
					productList=retailAnalysisDao.r_queryAgentDetail_SentReturnCount_Product(paramMap);
				}else {
					gr.setState(false);
					gr.setMessage("未配置的排序字段，请联系管理员【"+paramMap.get("sort").toString()+"】");
					return gr;
				}
			}else{
				//查询默认排序的商品列表
				productList=retailAnalysisDao.r_queryAgentDetailProductListBy_Default(paramMap);
			}
			
			if(!productList.isEmpty()&&productList.size()>0){
				paramMap.put("productList", productList);
				list = retailAnalysisDao.r_queryAgentDetailProductList(paramMap);
			}
			
			if (list != null && list.size() > 0) {
				paramMap.put("list", list);
				Map<String,Object> payCountMap = new HashMap<String,Object>();
				Map<String,Object> payMoneyMap = new HashMap<String,Object>();
				Map<String,Object> unsentReturnCountMap = new HashMap<String,Object>();
				Map<String,Object> sentReturnCountMap = new HashMap<String,Object>();
				//销量，销售总额
				List<Map<String,Object>> payCountMoneyList = retailAnalysisDao.r_queryAgentDetail_Product_PayCountMoney(paramMap);
				if(payCountMoneyList != null && payCountMoneyList.size()>0) {
					payCountMap = list2Map(payCountMoneyList,"ITEMNUMBER","PAY_COUNT");
					payMoneyMap = list2Map(payCountMoneyList,"ITEMNUMBER","PAY_MONEY");
				}
				//未发退货数
				List<Map<String,Object>> unsentReturnCountList = retailAnalysisDao.r_queryAgentDetail_Product_UnsentReturnCount(paramMap);
				if(unsentReturnCountList != null && unsentReturnCountList.size()>0) {
					unsentReturnCountMap = list2Map(unsentReturnCountList,"ITEMNUMBER","UNSENT_RETURN_COUNT");
				}
				//已发退货数
				List<Map<String,Object>> sentReturnCountList = retailAnalysisDao.r_queryAgentDetail_Product_SentReturnCount(paramMap);
				if(sentReturnCountList != null && sentReturnCountList.size()>0) {
					sentReturnCountMap = list2Map(sentReturnCountList,"ITEMNUMBER","SENT_RETURN_COUNT");
				}
                
				Map<String,Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String userId = tempMap.get("ITEMNUMBER").toString();
					//销量
					tempMap.put("PAY_COUNT", payCountMap.get(userId)==null?0:Float.parseFloat(payCountMap.get(userId).toString()));
					//销售总额
					tempMap.put("PAY_MONEY", payMoneyMap.get(userId)==null?0:Float.parseFloat(payMoneyMap.get(userId).toString()));
					//未发退货数
					tempMap.put("UNSENT_RETURN_COUNT", unsentReturnCountMap.get(userId)==null?0:Float.parseFloat(unsentReturnCountMap.get(userId).toString()));
					//已发退货数
					tempMap.put("SENT_RETURN_COUNT", sentReturnCountMap.get(userId)==null?0:Float.parseFloat(sentReturnCountMap.get(userId).toString()));
				}
				
				gr.setState(true);
				gr.setMessage("获取经销商商品列表成功");
				gr.setObj(list);
				gr.setTotal(total);
			}else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	
	/**
     * 经销商详情-退款原因分析-商品退货数占比
     * @param request
     * @return
     */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAgentDetailAfterSaleReturnCount(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("user_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
            paramMap.put("refund_type", 1);//仅退款
        	/** 仅退款 */
            float returnCount = retailAnalysisDao.r_queryAgentDetail_AfterSale_ReturnCount(paramMap);
            Map<String, Object> m = new HashMap<String, Object>();
        	m.put("PAGE_NAME", "仅退款");
        	m.put("CNT", returnCount);
        	resultList.add(m);
            
        	paramMap.put("refund_type", 2);//退货退款
            /** 退货退款 */
            returnCount = retailAnalysisDao.r_queryAgentDetail_AfterSale_ReturnCount(paramMap);
            m = new HashMap<String, Object>();
        	m.put("PAGE_NAME", "退货退款");
        	m.put("CNT", returnCount);
        	resultList.add(m);
            
            pr.setState(true);
            pr.setMessage("获取经销商详情-商品退货数占比成功");
            pr.setObj(resultList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	
	/**
	 * 经销商详情-退款原因分析-商品退货原因
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAgentDetailAfterSaleReturnReason(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
			if(paramMap.size() == 0||StringUtils.isEmpty(paramMap.get("user_id"))) {
            	gr.setState(false);
            	gr.setMessage("参数缺失");
                return gr;
            }
			/** 退货总数 */
			int count = retailAnalysisDao.r_queryAgentDetailAfterSaleReturnCount(paramMap);
			/** 退货原因总数 */
			int total = retailAnalysisDao.r_queryAgentDetailAfterSaleReturnReasonCount(paramMap);
			/** 退货原因列表 */
			List<Map<String,Object>> list = retailAnalysisDao.r_queryAgentDetailAfterSaleReturnReason(paramMap);
			
			if (list != null && list.size() > 0) {
				for(Map<String, Object> reason : list) {
					float returnReasonCount = reason.get("RETURN_REASON_COUNT")==null?0:Float.parseFloat(reason.get("RETURN_REASON_COUNT").toString());
					if(count == 0) {
						reason.put("RATIO",0);
					}else {
						reason.put("RATIO",m2((returnReasonCount/count)*100));
					}
				}
				gr.setState(true);
				gr.setMessage("获取退货原因列表成功");
				gr.setObj(list);
				gr.setTotal(total);
			}else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	/**
	 * 经销商详情-活动分析-活动折线图
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAgentDetailActivityChart(HttpServletRequest request) {
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
            List<Map<String, Object>> allDataList = new ArrayList<Map<String, Object>>();
            List<String> time_list = new ArrayList<String>();
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
            /** 折线图 下单笔数 */
            List<Map<String, Object>> activityCountList = retailAnalysisDao.r_queryAgentDetail_ActivityCount_Chart(paramMap);
            
            //活动数
            Map<String,Object> activityCountMap = list2Map(activityCountList,"CREATE_DATE","ACTIVITY_COUNT");
            
            //开始数据拼装
          	String key="";
          	
          	Map<String, Object> map = null;
            for(String time : time_list) {
            	key = time;
                /** 活动数 */
                float activityCount = activityCountMap.get(key)==null?0:Float.parseFloat(activityCountMap.get(key).toString());
                
              	map = new HashMap<String, Object>();
              	//活动数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", activityCount);
              	map.put("PAGE_NAME", "活动数");
              	allDataList.add(map);
            }
            
            //将原始数据组装为Echart可识别的格式
            resultMap = createData("PAGE_NAME", time_list, allDataList);
            
            pr.setState(true);
			pr.setMessage("获取活动折线图成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 经销商详情-活动分析-活动列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryAgentDetailActivityList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
			if(paramMap.size() == 0||StringUtils.isEmpty(paramMap.get("user_id"))) {
            	gr.setState(false);
            	gr.setMessage("参数缺失");
                return gr;
            }
			/** 活动总数 */
			int total = retailAnalysisDao.r_queryAgentDetailActivityCount(paramMap);
			/** 活动列表 */
			List<Map<String,Object>> list = retailAnalysisDao.r_queryAgentDetailActivityList(paramMap);
			
			if (list != null && list.size() > 0) {
				gr.setState(true);
				gr.setMessage("获取活动列表成功");
				gr.setObj(list);
				gr.setTotal(total);
			}else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	
	/**
	 * 所属门店【下拉框】
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryStoreOption(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            List<Map<String,Object>> storeList = retailAnalysisDao.queryStoreOption(paramMap);
            pr.setState(true);
            pr.setMessage("获取门店列表成功!");
            pr.setObj(storeList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 业务员【下拉框】
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryYwyOption(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            List<Map<String,Object>> storeList = retailAnalysisDao.queryYwyOption(paramMap);
            pr.setState(true);
            pr.setMessage("获取业务员列表成功!");
            pr.setObj(storeList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
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
