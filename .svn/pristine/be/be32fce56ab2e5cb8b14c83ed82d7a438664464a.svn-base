package com.tk.analysis.member.service;

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
import com.tk.analysis.member.dao.MemberAnalysisOperationDao;
import com.tk.sys.util.DateUtils;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;


/**
 * 
 * Copyright (c), 2019, Tongku
 * FileName : MemberAnalysisOperationService.java
 * 
 *
 * @author yejingquan
 * @version 1.00
 * @date Jul 23, 2019
 */
@Service("MemberAnalysisOperationService")
public class MemberAnalysisOperationService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private HomeAnalysisService homeAnalysisService;
	@Resource
	private MemberAnalysisOperationDao memberAnalysisOperationDao;
	private String[] colors={"#2f4554 ", "#c23531", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF", "#001cf1", "#000000", "#50d400", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4",
            "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3"};
	
	/**
	 * 会员概况-会员总数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryMemberTotal(HttpServletRequest request) {
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
            
        	/** 会员总数 */
            float memberTotal = memberAnalysisOperationDao.r_queryMemberTotal(paramMap);
            retMap.put("member_total", memberTotal);
            
            pr.setState(true);
            pr.setMessage("获取会员总数成功");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	
	/**
	 * 会员概况-昨日新增会员
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryMemberAddCount(HttpServletRequest request) {
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
            Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, -1);
			String yd_date = sdf.format(c.getTime());
            paramMap.put("start_date", yd_date);
            paramMap.put("end_date", yd_date);
        	/** 昨日新增会员 */
            float memberAddCount = memberAnalysisOperationDao.r_queryMemberAddCount(paramMap);
            retMap.put("member_add_count", memberAddCount);
            
            pr.setState(true);
            pr.setMessage("获取昨日新增会员成功");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	/**
	 * 会员概况-沉睡会员
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryMemberSleepCount(HttpServletRequest request) {
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
            //沉睡会员
            float memberSleepCount = memberAnalysisOperationDao.r_queryMemberSleepCount(paramMap);
            retMap.put("member_sleep_count", memberSleepCount);
            
            pr.setState(true);
            pr.setMessage("获取沉睡会员成功");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	/**
	 * 会员概况-近3月异常会员
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryMemberAbnormalCount(HttpServletRequest request) {
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
            //异常会员
            float memberAbnormalCount = memberAnalysisOperationDao.r_queryMemberAbnormalCount(paramMap);
            retMap.put("member_abnormal_count", memberAbnormalCount);
            
            pr.setState(true);
            pr.setMessage("获取近3月异常会员成功");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	/**
	 * 会员概况-昨日活跃会员
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryMemberLivelyCount(HttpServletRequest request) {
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
            Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, -1);
			String yd_date = sdf.format(c.getTime());
            paramMap.put("start_date", yd_date);
            paramMap.put("end_date", yd_date);
        	/** 昨日活跃会员 */
            float memberLivelyCount = memberAnalysisOperationDao.r_queryMemberLivelyCount(paramMap);
            retMap.put("member_lively_count", memberLivelyCount);
            
            pr.setState(true);
            pr.setMessage("获取昨日活跃会员成功");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	/**
	 * 会员概况-成交会员
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryMemberPayCount(HttpServletRequest request) {
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
            
        	/** 成交会员 */
            float memberLivelyCount = memberAnalysisOperationDao.r_queryMemberPayCount(paramMap);
            retMap.put("member_lively_count", memberLivelyCount);
            
            pr.setState(true);
            pr.setMessage("获取成交会员成功");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	/**
	 * 会员概况折线图
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryMemberChart(HttpServletRequest request) {
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
            List<Map<String, Object>> allDataList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> memberAddCountList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> memberLivelyCountList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> memberPayCountList = new ArrayList<Map<String, Object>>();
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
            	/** 会员概况 折线图 新增会员 */
            	memberAddCountList = memberAnalysisOperationDao.r_queryMemberAddCountD_Chart(paramMap);
                /** 会员概况 折线图 活跃会员 */
            	memberLivelyCountList = memberAnalysisOperationDao.r_queryMemberLivelyCountD_Chart(paramMap);
            	/** 会员概况 折线图 成交会员 */
            	memberPayCountList = memberAnalysisOperationDao.r_queryMemberPayCountD_Chart(paramMap);
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
                /** 会员概况 折线图 新增会员 */
            	memberAddCountList = memberAnalysisOperationDao.r_queryMemberAddCount_Chart(paramMap);
                /** 会员概况 折线图 活跃会员 */
            	memberLivelyCountList = memberAnalysisOperationDao.r_queryMemberLivelyCount_Chart(paramMap);
            	/** 会员概况 折线图 成交会员 */
            	memberPayCountList = memberAnalysisOperationDao.r_queryMemberPayCount_Chart(paramMap);
            }
            
            //新增会员
            Map<String,Object> memberAddCountMap = list2Map(memberAddCountList,"CREATE_DATE","MEMBER_ADD_COUNT");
            //活跃会员
            Map<String,Object> memberLivelyCountMap = list2Map(memberLivelyCountList,"CREATE_DATE","MEMBER_LIVELY_COUNT");
            //成交会员
            Map<String,Object> memberPayCountMap = list2Map(memberPayCountList,"CREATE_DATE","MEMBER_PAY_COUNT");
            
            //开始数据拼装
          	String key="";
          	
          	Map<String, Object> map = null;
            for(String time : time_list) {
            	key = time;
            	/** 会员概况-新增会员 */
                float memberAddCount = memberAddCountMap.get(key)==null?0:Float.parseFloat(memberAddCountMap.get(key).toString());
                /** 会员概况-活跃会员 */
                float memberLivelyCount = memberLivelyCountMap.get(key)==null?0:Float.parseFloat(memberLivelyCountMap.get(key).toString());
                /** 会员概况-成交会员 */
                float memberPayCount = memberPayCountMap.get(key)==null?0:Float.parseFloat(memberPayCountMap.get(key).toString());
                
                map = new HashMap<String, Object>();
                //新增会员
              	map.put("CREATE_DATE", key);
              	map.put("CNT", memberAddCount);
              	map.put("PAGE_NAME", "新增会员");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//活跃会员
              	map.put("CREATE_DATE", key);
              	map.put("CNT", memberLivelyCount);
              	map.put("PAGE_NAME", "活跃会员");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//成交会员
              	map.put("CREATE_DATE", key);
              	map.put("CNT", memberPayCount);
              	map.put("PAGE_NAME", "成交会员");
              	allDataList.add(map);
            }
            
            //将原始数据组装为Echart可识别的格式
            resultMap = createData("PAGE_NAME", time_list, allDataList, 1);
            
            pr.setState(true);
			pr.setMessage("获取会员概况折线成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	/**
	 * 会员概况-会员成交TOP
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryMemberPayTop(HttpServletRequest request) {
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
			List<Map<String, Object>> list=null;
			/** 会员总数 */
			int total = memberAnalysisOperationDao.r_queryMemberCount(paramMap);
			List<String> userList = new ArrayList<String>();
			//查询默认排序的会员成交信息
			userList = memberAnalysisOperationDao.r_queryMemberPayTopListBy_Default(paramMap);
			
			if(!userList.isEmpty()&&userList.size()>0){
				paramMap.put("userList", userList);
				list = memberAnalysisOperationDao.r_queryMemberList(paramMap);
			}
            
            if (list != null && list.size() > 0) {
            	/** 查询成交金额,成交商品数 */
            	List<Map<String, Object>> payCountMoneyList = memberAnalysisOperationDao.r_queryMemberPayCountMoney(paramMap);
                Map<String,Object> payMoneyMap = list2Map(payCountMoneyList,"USER_ID","PAY_MONEY");
                Map<String,Object> payCountMap = list2Map(payCountMoneyList,"USER_ID","PAY_COUNT");
                Map<String,Object> preFirstMoneyMap = new HashMap<String,Object>();
                if("real_time".equals(paramMap.get("query_type")+"")) {
                	 /** 查询预定订单的定金 */
                    List<Map<String, Object>> preFirstMoneyList = memberAnalysisOperationDao.r_queryMemberPreFirstMoney(paramMap);
                    preFirstMoneyMap = list2Map(preFirstMoneyList,"USER_ID","PRE_FIRST_MONEY");
                }
                /** 查询最近一次成交时间 */
            	List<Map<String, Object>> lastPayDateList = memberAnalysisOperationDao.r_queryMemberLastPayDate(paramMap);
            	Map<String,Object> lastPayDateMap = list2Map(lastPayDateList,"USER_ID","PAY_DATE");
            	
            	Map<String,Object> lastPayMoneyMap = new HashMap<String,Object>();
            	if(lastPayDateList.size() > 0) {
            		paramMap.put("payDateList", lastPayDateList);
            		/** 查询最近一次成交金额 */
            		List<Map<String, Object>> lastPayMoneyList = memberAnalysisOperationDao.r_queryMemberLastPayMoney(paramMap);
            		lastPayMoneyMap = list2Map(lastPayMoneyList,"USER_ID","PAY_MONEY");
            	}
            	
            	/** 查询最近一次预定订单支付时间 */
            	List<Map<String, Object>> lastPrePayDateList = memberAnalysisOperationDao.r_queryMemberLastPrePayDate(paramMap);
            	Map<String,Object> lastPreFirstMoneyMap = new HashMap<String,Object>();
            	if(lastPrePayDateList.size() > 0) {
            		paramMap.put("prePayDateList", lastPrePayDateList);
            		/** 查询最近一次预定订单的定金 */
            		List<Map<String, Object>> lastPreFirstMoneyList = memberAnalysisOperationDao.r_queryMemberLastPreFirstMoney(paramMap);
            		lastPreFirstMoneyMap = list2Map(lastPreFirstMoneyList,"USER_ID","PRE_FIRST_MONEY");
            	}
            	
            	Map<String, Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String user_id = tempMap.get("USER_ID").toString();
					float payMoney = payMoneyMap.get(user_id)==null?0:Float.parseFloat(payMoneyMap.get(user_id).toString());
					//预定订单的定金
					float preFirstMoney = preFirstMoneyMap.get(user_id)==null?0:Float.parseFloat(preFirstMoneyMap.get(user_id).toString());
					float lastPayMoney = lastPayMoneyMap.get(user_id)==null?0:Float.parseFloat(lastPayMoneyMap.get(user_id).toString());
					//最后一次预定订单的定金
					float lastPreFirstMoney = lastPreFirstMoneyMap.get(user_id)==null?0:Float.parseFloat(lastPreFirstMoneyMap.get(user_id).toString());
					//成交金额
					tempMap.put("PAY_MONEY",payMoney+preFirstMoney);
					//成交商品数
					tempMap.put("PAY_COUNT",payCountMap.get(user_id)==null?0:Float.parseFloat(payCountMap.get(user_id).toString()));
					//最近一次成交金额
					tempMap.put("LAST_PAY_MONEY",lastPayMoney+lastPreFirstMoney);
					//最近一次成交时间
					tempMap.put("LAST_PAY_DATE",lastPayDateMap.get(user_id)==null?"":lastPayDateMap.get(user_id));
				}
				
				gr.setState(true);
				gr.setMessage("查询会员成交TOP成功");
				gr.setObj(list);
				gr.setTotal(total);
			} else {
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
	 * 会员概况-会员退款TOP
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryMemberReturnTop(HttpServletRequest request) {
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
			List<Map<String, Object>> list=null;
			/** 会员总数 */
			int total = memberAnalysisOperationDao.r_queryMemberCount(paramMap);
			List<String> userList = new ArrayList<String>();
			//查询默认排序的会员退款信息
			userList = memberAnalysisOperationDao.r_queryMemberReturnTopListBy_Default(paramMap);
			
			if(!userList.isEmpty()&&userList.size()>0){
				paramMap.put("userList", userList);
				list = memberAnalysisOperationDao.r_queryMemberList(paramMap);
			}
            
            if (list != null && list.size() > 0) {
            	/** 查询已退款总额,退款商品数 */
            	List<Map<String, Object>> returnCountMoneyList = memberAnalysisOperationDao.r_queryMemberReturnCountMoney(paramMap);
                Map<String,Object> returnMoneyMap = list2Map(returnCountMoneyList,"USER_ID","RETURN_MONEY");
                Map<String,Object> returnCountMap = list2Map(returnCountMoneyList,"USER_ID","RETURN_COUNT");
            	/** 查询待退款金额,待退款笔数 */
            	List<Map<String, Object>> stayReturnMoneyNumList = memberAnalysisOperationDao.r_queryMemberStayReturnMoneyNum(paramMap);
            	Map<String,Object> stayReturnMoneyMap = list2Map(stayReturnMoneyNumList,"USER_ID","RETURN_MONEY");
            	Map<String,Object> stayReturnNumMap = list2Map(stayReturnMoneyNumList,"USER_ID","RETURN_NUM");
            	
            	Map<String, Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String user_id = tempMap.get("USER_ID").toString();
					//已退款总额
					tempMap.put("RETURN_MONEY",returnMoneyMap.get(user_id)==null?0:Float.parseFloat(returnMoneyMap.get(user_id).toString()));
					//退款商品数
					tempMap.put("RETURN_COUNT",returnCountMap.get(user_id)==null?0:Float.parseFloat(returnCountMap.get(user_id).toString()));
					//待退款金额
					tempMap.put("STAY_RETURN_MONEY",stayReturnMoneyMap.get(user_id)==null?0:Float.parseFloat(stayReturnMoneyMap.get(user_id).toString()));
					//待退款笔数
					tempMap.put("STAY_RETURN_NUM",stayReturnNumMap.get(user_id)==null?0:Float.parseFloat(stayReturnNumMap.get(user_id).toString()));
				}
				
				gr.setState(true);
				gr.setMessage("查询会员退款TOP成功");
				gr.setObj(list);
				gr.setTotal(total);
			} else {
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
	 * 会员概况-异常会员排行
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryMemberAbnormalRank(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
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
            paramMap.put("num", 10);
            List<Map<String, Object>> resultList = memberAnalysisOperationDao.r_queryMemberAbnormalRank(paramMap);
            
            pr.setState(true);
            pr.setMessage("获取异常会员排行成功");
            pr.setObj(resultList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	/**
	 * 会员概况-沉睡会员排行
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryMemberSleepRank(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
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
            paramMap.put("num", 10);
            List<Map<String, Object>> resultList = memberAnalysisOperationDao.r_queryMemberSleepRank(paramMap);
            for(Map<String, Object> map : resultList) {
            	//未登录时长
            	int notLoginTime = Integer.parseInt(map.get("NOT_LOGIN_TIME").toString());
            	int day = notLoginTime/24;
            	int hour = notLoginTime % 24;
            	map.put("NOT_LOGIN_TIMES", day+"天"+hour+"时");
            }
            pr.setState(true);
            pr.setMessage("获取沉睡会员排行成功");
            pr.setObj(resultList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	/**
	 * 会员预警-异常会员饼图
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryMemberWarningAbnormalChart(HttpServletRequest request) {
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
            /** 异常会员饼图 */
            List<Map<String, Object>> resultList = memberAnalysisOperationDao.r_queryMemberWarningAbnormal_Chart(paramMap);
            
            pr.setState(true);
			pr.setMessage("获取异常会员饼图成功");
			pr.setObj(resultList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	/**
	 * 会员预警-异常会员列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryMemberWarningAbnormalList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
			if(paramMap.size() == 0||StringUtils.isEmpty(paramMap.get("date_type"))) {
            	gr.setState(false);
            	gr.setMessage("参数缺失");
                return gr;
            }
			List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            if((!StringUtils.isEmpty(paramMap.get("flag_types")))&&paramMap.get("flag_types") instanceof String){
                paramMap.put("flag_types",(paramMap.get("flag_types")+"").split(","));
            }
			/** 异常会员总数 */
			int total = memberAnalysisOperationDao.r_queryMemberAbnormalListCount(paramMap);
			
			List<Map<String, Object>> resultlist = memberAnalysisOperationDao.r_queryMemberAbnormalListForPage(paramMap);
			
			if(resultlist != null &&resultlist.size() > 0) {
				List<String> userList = new ArrayList<String>();
				for(Map<String, Object> r : resultlist) {
					userList.add(r.get("USER_ID")+"");
				}
				paramMap.put("userList", userList);
				List<Map<String, Object>> userInfoList = memberAnalysisOperationDao.r_queryMemberList(paramMap);
				//用户姓名
				Map<String, Object> userManageNameMap = list2Map(userInfoList, "USER_ID", "USER_MANAGE_NAME");
				//业务归属
				Map<String, Object> ywNameMap = list2Map(userInfoList, "USER_ID", "YW_NAME");
				for(Map<String, Object> r : resultlist) {
					r.put("USER_MANAGE_NAME", userManageNameMap.get(r.get("USER_ID").toString())==null?"":userManageNameMap.get(r.get("USER_ID").toString()));
					r.put("YW_NAME", ywNameMap.get(r.get("USER_ID").toString())==null?"":ywNameMap.get(r.get("USER_ID").toString()));
				}
				
				gr.setState(true);
				gr.setMessage("查询异常会员列表成功");
				gr.setObj(resultlist);
				gr.setTotal(total);
			} else {
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
	 * 会员预警-沉睡会员饼图
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryMemberWarningSleepChart(HttpServletRequest request) {
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
            /** 沉睡会员饼图 */
            List<Map<String, Object>> resultList = memberAnalysisOperationDao.r_queryMemberSleep_Chart(paramMap);
            
            pr.setState(true);
			pr.setMessage("获取沉睡会员饼图成功");
			pr.setObj(resultList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	/**
	 * 会员预警-沉睡会员列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryMemberWarningSleepList(HttpServletRequest request) {
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
			/** 沉睡会员总数 */
			int total = memberAnalysisOperationDao.r_queryMemberSleepListCount(paramMap);
			
			List<Map<String, Object>> resultlist = memberAnalysisOperationDao.r_queryMemberSleepListForPage(paramMap);
			
			if(resultlist != null &&resultlist.size() > 0) {
				List<String> userList = new ArrayList<String>();
				for(Map<String, Object> r : resultlist) {
					userList.add(r.get("USER_ID")+"");
				}
				paramMap.put("userList", userList);
				List<Map<String, Object>> userInfoList = memberAnalysisOperationDao.r_queryMemberList(paramMap);
				//用户姓名
				Map<String, Object> userManageNameMap = list2Map(userInfoList, "USER_ID", "USER_MANAGE_NAME");
				//最后一次登录时间
				Map<String, Object> userLastLoginDateMap = list2Map(userInfoList, "USER_ID", "USER_LAST_LOGIN_DATE");
				//业务归属
				Map<String, Object> ywNameMap = list2Map(userInfoList, "USER_ID", "YW_NAME");
				for(Map<String, Object> r : resultlist) {
					r.put("USER_MANAGE_NAME", userManageNameMap.get(r.get("USER_ID").toString())==null?"":userManageNameMap.get(r.get("USER_ID").toString()));
					r.put("USER_LAST_LOGIN_DATE", userLastLoginDateMap.get(r.get("USER_ID").toString())==null?"":userLastLoginDateMap.get(r.get("USER_ID").toString()));
					r.put("YW_NAME", ywNameMap.get(r.get("USER_ID").toString())==null?"":ywNameMap.get(r.get("USER_ID").toString()));
					//未登录时长
	            	int notLoginTime = Integer.parseInt(r.get("NOT_LOGIN_TIME").toString());
	            	int day = notLoginTime/24;
	            	int hour = notLoginTime % 24;
	            	r.put("NOT_LOGIN_TIMES", day+"天"+hour+"时");
				}
				
				gr.setState(true);
				gr.setMessage("查询沉睡会员列表成功");
				gr.setObj(resultlist);
				gr.setTotal(total);
			} else {
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
	 * 会员活跃度折线图
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryMemberLivelyChart(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("dateState"))) {
            	pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            int dateState = Integer.parseInt(paramMap.get("dateState").toString());
            List<Map<String, Object>> allDataList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> memberLivelyCountList = new ArrayList<Map<String, Object>>();
            List<String> time_list = new ArrayList<String>();
            if(dateState == 2) {//月活跃
            	if(StringUtils.isEmpty(paramMap.get("year"))) {
            		pr.setState(false);
                    pr.setMessage("缺少参数");
                    return pr;
            	}
            	time_list.add(paramMap.get("year")+"-01");
            	time_list.add(paramMap.get("year")+"-02");
            	time_list.add(paramMap.get("year")+"-03");
            	time_list.add(paramMap.get("year")+"-04");
            	time_list.add(paramMap.get("year")+"-05");
            	time_list.add(paramMap.get("year")+"-06");
            	time_list.add(paramMap.get("year")+"-07");
            	time_list.add(paramMap.get("year")+"-08");
            	time_list.add(paramMap.get("year")+"-09");
            	time_list.add(paramMap.get("year")+"-10");
            	time_list.add(paramMap.get("year")+"-11");
            	time_list.add(paramMap.get("year")+"-12");
                /** 会员活跃度 折线图 活跃会员 */
            	memberLivelyCountList = memberAnalysisOperationDao.r_queryMemberLivelyCountM_Chart(paramMap);
            }else {//日活跃
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
                /** 会员活跃度 折线图 活跃会员 */
            	memberLivelyCountList = memberAnalysisOperationDao.r_queryMemberLivelyCount_Chart(paramMap);
            }
            
            //活跃会员
            Map<String,Object> memberLivelyCountMap = list2Map(memberLivelyCountList,"CREATE_DATE","MEMBER_LIVELY_COUNT");
            
            //开始数据拼装
          	String key="";
          	
          	Map<String, Object> map = null;
            for(String time : time_list) {
            	key = time;
                /** 会员概况-活跃会员 */
                float memberLivelyCount = memberLivelyCountMap.get(key)==null?0:Float.parseFloat(memberLivelyCountMap.get(key).toString());
                
              	map = new HashMap<String, Object>();
              	//活跃会员
              	map.put("CREATE_DATE", key);
              	map.put("CNT", memberLivelyCount);
              	map.put("PAGE_NAME", "活跃会员");
              	allDataList.add(map);
            }
            
            //将原始数据组装为Echart可识别的格式
            resultMap = createData("PAGE_NAME", time_list, allDataList, dateState);
            
            pr.setState(true);
			pr.setMessage("获取会员活跃度折线成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	/**
	 * 会员活跃度列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryMemberLivelyList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("dateState"))) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> lastResultList = new ArrayList<Map<String, Object>>();
            int dateState = Integer.parseInt(paramMap.get("dateState").toString());
            if(dateState == 2) {//月活跃
            	resultList = memberAnalysisOperationDao.r_queryMemberLivelyListM(paramMap);
            	//较去年同期
            	int lastYear = Integer.parseInt(paramMap.get("year")+"") - 1;
            	paramMap.put("year", lastYear);
            	lastResultList = memberAnalysisOperationDao.r_queryMemberLivelyListM(paramMap);
            }else {//日活跃
            	resultList = memberAnalysisOperationDao.r_queryMemberLivelyList(paramMap);
            	//较上月同期
            	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            	Calendar c = Calendar.getInstance();
        		c.setTime(sdf.parse(paramMap.get("start_date").toString()));
                c.add(Calendar.MONTH, -1);
                c.set(Calendar.DAY_OF_MONTH, 1);
                String startDate = sdf.format(c.getTime());
                paramMap.put("start_date", startDate);
                c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
                String endDate = sdf.format(c.getTime());
                paramMap.put("end_date", endDate);
                lastResultList = memberAnalysisOperationDao.r_queryMemberLivelyList(paramMap);
            }
            
            if(resultList !=null && resultList.size()>0) {
            	for(Map<String, Object> r : resultList) {
            		float memberLivelyCount = Float.parseFloat(r.get("MEMBER_LIVELY_COUNT").toString());
            		for(Map<String, Object> lr : lastResultList) {
                		if(r.get("CNT").equals(lr.get("CNT"))) {
                			float lastMemberLivelyCount = Float.parseFloat(lr.get("MEMBER_LIVELY_COUNT").toString());
                			if(lastMemberLivelyCount == 0) {
                				r.put("MEMBER_LIVELY_RATIO", 0);
                			}else {
                				r.put("MEMBER_LIVELY_RATIO", m2((memberLivelyCount-lastMemberLivelyCount)/lastMemberLivelyCount*100));
                			}
                		}
                	}
                }
            	
            	pr.setState(true);
    			pr.setMessage("获取会员活跃度列表成功");
    			pr.setObj(resultList);
            }else {
            	pr.setState(true);
    			pr.setMessage("无数据");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
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
	
	private Map<String, Object> createData(String seriesName,List<String> time_list,List<Map<String, Object>> data_list,int dateState) {
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
                String ORDER_DATE = "";
                if(dateState == 2) {
                	ORDER_DATE = data.get("CREATE_DATE") == null ? DateUtils.format(new Date(), "YYYY-MM") : data.get("CREATE_DATE").toString();
                }else {
                	ORDER_DATE = data.get("CREATE_DATE") == null ? DateUtils.format(new Date(), "YYYY-MM-dd") : data.get("CREATE_DATE").toString();
                }
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
