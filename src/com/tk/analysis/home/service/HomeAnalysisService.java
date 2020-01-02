package com.tk.analysis.home.service;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tk.analysis.home.dao.HomeAnalysisDao;
import com.tk.oms.sysuser.entity.SysUserInfo;
import com.tk.oms.sysuser.service.SysUserInfoService;
import com.tk.sys.util.DateUtils;
import com.tk.sys.util.HttpClientUtil;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2019, Tongku
 * FileName : HomeAnalysisService.java
 * 运营分析-首页
 *
 * @author yejingquan
 * @version 1.00
 * @date Aug 7, 2019
 */
@Service("HomeAnalysisService")
public class HomeAnalysisService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private HomeAnalysisDao homeAnalysisDao;
	@Resource
    private SysUserInfoService sysUserInfoService;
	/**OA服务地址*/
    @Value("${oa_service_url}")
    private String oa_service_url;
    /** 客户端系统Id */
	@Value("${oauth.oauth_client_id}")
	private String oauth_client_id;
	
	private String[] colors={"#2f4554 ", "#c23531", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF", "#001cf1", "#000000", "#50d400", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4",
            "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3"};
	
	/**
	 * 获取用户可用平台列表
	 * @param request
	 * @return
	 */
	public ProcessResult getUserPlatformList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
	        String json = HttpUtil.getRequestInputStream(request);
	        //  校验是否传入参数
            if(StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
			//  判断id是否已传入
            SysUserInfo userinfo = (SysUserInfo) Transform.GetPacket(json, SysUserInfo.class);
            if(userinfo.getId()==0) {
            	pr.setState(false);
            	pr.setMessage("缺少参数user_id");
            	return pr;
            }
			//  获取系统用户登录OA的openId
            String openId = sysUserInfoService.getSysUserOAOpenIdById(userinfo.getId());
            if(StringUtils.isEmpty(openId)){
                pr.setState(false);
                pr.setMessage("获取openId失败");
                return pr;
            }
            int isManager = 0;
            if(userinfo.getId() == 1) {
            	isManager = 1;
            }
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("clientId", oauth_client_id);
			paramMap.put("openId", openId);
			paramMap.put("isManager", isManager);

			pr = HttpClientUtil.postToOAByReverse(oa_service_url + "oauth2/user/get_data_auth", paramMap);
			
			if(!pr.getState()){
                pr.setState(false);
                pr.setMessage("获取用户平台权限失败，请联系管理员！");
                return pr;
            }
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 根据用户ID查询用户权限信息
	 * @param paramMap
	 * @return
	 */
	public List<String> queryByUserId(Map<String, Object> paramMap) {
		int platform_type = Integer.parseInt(paramMap.get("public_user_platform_type").toString()) ;
		List<String> list = new ArrayList<String>();
		if((platform_type == 1 || platform_type == 9) && ("2".equals(paramMap.get("public_user_type")+"") || "6".equals(paramMap.get("public_user_type")+"") || "9".equals(paramMap.get("public_user_type")+""))) {
			//根据用户ID查询用户权限信息
			list = homeAnalysisDao.r_queryByUserId(paramMap);
			if(list == null || list.size() <= 0) {
				list = new ArrayList<String>();
				list.add("0");
			}
		}
		return list;
	}
	
	/**
	 * 根据用户ID查询用户品类权限信息
	 * @param paramMap
	 * @return
	 */
	public List<String> queryByProductTypeId(Map<String, Object> paramMap) {
		int platform_type = Integer.parseInt(paramMap.get("public_user_platform_type").toString()) ;
		List<String> list = new ArrayList<String>();
		if((platform_type == 1 || platform_type == 9) && ("2".equals(paramMap.get("public_user_type")+""))) {
			//根据用户ID查询用户品类权限信息
			list = homeAnalysisDao.r_queryByProductTypeId(paramMap);
			if(list == null || list.size() <= 0) {
				list = new ArrayList<String>();
				list.add("0");
			}
		}
		return list;
	}
	
	/**
	 * 实时基本概况
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryRealTimeDetail(HttpServletRequest request) {
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
            
            List<String> ids = queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
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
            //实时基本概况 支付买家数、销售件数、支付单笔数、成交额
            Map<String,Object> purchaseNumberCountMoneyMap = homeAnalysisDao.r_queryRealTimeDetail_PurchaseNumberCountMoney(paramMap);
            //支付买家数
            float td_payPurchaseNumber = purchaseNumberCountMoneyMap.get("PURCHASE_NUMBER")==null?0:Float.parseFloat(purchaseNumberCountMoneyMap.get("PURCHASE_NUMBER").toString());
            //成交额
            float td_payMoney = purchaseNumberCountMoneyMap.get("PAY_MONEY")==null?0:Float.parseFloat(purchaseNumberCountMoneyMap.get("PAY_MONEY").toString());
            //销售件数
            float td_payCount = purchaseNumberCountMoneyMap.get("PAY_COUNT")==null?0:Float.parseFloat(purchaseNumberCountMoneyMap.get("PAY_COUNT").toString());
            //支付单笔数
            float td_payNum = purchaseNumberCountMoneyMap.get("PAY_NUM")==null?0:Float.parseFloat(purchaseNumberCountMoneyMap.get("PAY_NUM").toString());
            //实时基本概况 日活跃用户数
            float td_livelyCount = homeAnalysisDao.r_queryRealTimeDetail_LivelyCount(paramMap);
            //实时基本概况 全站访客数
            float td_visitorCount = homeAnalysisDao.r_queryRealTimeDetail_VisitorCount(paramMap);
          	//实时基本概况 预订订单的定金
            float td_preFirstMoney = homeAnalysisDao.r_queryRealTimeDetail_PreFirstMoney(paramMap);
            td_payMoney+=td_preFirstMoney;
          	//无线支付金额
            float mobileMoney = homeAnalysisDao.r_queryRealTimeDetail_MobileMoney(paramMap);
          	float td_zhl =0;
          	if(td_visitorCount>0){
          		//支付转化率
          		td_zhl = 100*(td_payPurchaseNumber/td_visitorCount);
          	}
          	float td_kdj = 0;
          	if(td_payPurchaseNumber > 0) {
          		td_kdj = td_payMoney/td_payPurchaseNumber;
          	}
          	float mobile_ratio = 0;
          	if(td_payMoney>0) {
          		mobile_ratio = mobileMoney/td_payMoney*100;
          	}
          	rtMap.put("TD_PAY_MONEY",td_payMoney); 		//支付金额
          	rtMap.put("TD_LIVELY_COUNT", td_livelyCount); //日活跃用户数
          	rtMap.put("TD_PAY_COUNT", td_payCount); 		//销售件数
  			rtMap.put("TD_PAY_NUM", td_payNum); 	//支付单笔数
  			rtMap.put("TD_PURCHASE_NUMBER", td_payPurchaseNumber); 			//支付买家数
			rtMap.put("TD_ZHL", td_zhl);			//支付转化率
			rtMap.put("TD_KDJ", td_kdj); //客单价
			rtMap.put("MOBILE_RATIO", mobile_ratio);//无线占比
			/**************************************************今日相关统计     end****************************************************/
			
			/**************************************************今日折线图相关统计     begin****************************************************/
			List<Float> lineTd = new ArrayList<Float>();
			/** 实时基本概况 折线图 成交额 **/
			List<Map<String, Object>> td_payMoneyList = homeAnalysisDao.r_queryRealTimeDetail_PayMoney_Chart(paramMap);
			Map<String, Object> td_payMoneyMap = list2Map(td_payMoneyList,"CREATE_DATE","PAY_MONEY");
			/** 实时基本概况 折线图 预定订单的定金 **/
			List<Map<String, Object>> td_preFirstMoneyList = homeAnalysisDao.r_queryRealTimeDetail_PreFirstMoney_Chart(paramMap);
			Map<String, Object> td_preFirstMoneyMap = list2Map(td_preFirstMoneyList,"CREATE_DATE","PRE_FIRST_MONEY");
          

           	//开始数据拼装
          	String key="";
            for(int i=0;i<time_list.size();i++){
                key = time_list.get(i);
                //成交额
                td_payMoney = td_payMoneyMap.get(key)==null?0:Float.parseFloat(td_payMoneyMap.get(key).toString());
                //预订订单的定金
                td_preFirstMoney = td_preFirstMoneyMap.get(key)==null?0:Float.parseFloat(td_preFirstMoneyMap.get(key).toString());
                td_payMoney+=td_preFirstMoney;
                lineTd.add(td_payMoney);
			}
			/**************************************************今日折线图相关统计     end****************************************************/
          	
			/**************************************************昨日相关统计     begin****************************************************/
			Calendar c = Calendar.getInstance();  
			c.add(Calendar.DATE, -1);  
			dateNowStr = sdf.format(c.getTime());  
            paramMap.put("date_short", dateNowStr);
            //实时基本概况 支付买家数、销售件数、支付单笔数、成交额
            purchaseNumberCountMoneyMap.clear();
            purchaseNumberCountMoneyMap = homeAnalysisDao.r_queryRealTimeDetail_PurchaseNumberCountMoney(paramMap);
            //支付买家数
            float yd_payPurchaseNumber = purchaseNumberCountMoneyMap.get("PURCHASE_NUMBER")==null?0:Float.parseFloat(purchaseNumberCountMoneyMap.get("PURCHASE_NUMBER").toString());
            //成交额
            float yd_payMoney = purchaseNumberCountMoneyMap.get("PAY_MONEY")==null?0:Float.parseFloat(purchaseNumberCountMoneyMap.get("PAY_MONEY").toString());
            //销售件数
            float yd_payCount = purchaseNumberCountMoneyMap.get("PAY_COUNT")==null?0:Float.parseFloat(purchaseNumberCountMoneyMap.get("PAY_COUNT").toString());
            //支付单笔数
            float yd_payNum = purchaseNumberCountMoneyMap.get("PAY_NUM")==null?0:Float.parseFloat(purchaseNumberCountMoneyMap.get("PAY_NUM").toString());
            //实时基本概况 日活跃用户数
            float yd_livelyCount = homeAnalysisDao.r_queryRealTimeDetail_LivelyCount(paramMap);
            //实时基本概况 访客数
            float yd_visitorCount = homeAnalysisDao.r_queryRealTimeDetail_VisitorCount(paramMap);
          	//实时基本概况 预订订单的定金
            float yd_preFirstMoney = homeAnalysisDao.r_queryRealTimeDetail_PreFirstMoney(paramMap);
            yd_payMoney+=yd_preFirstMoney;
          	
          	float yd_zhl =0;
          	if(yd_visitorCount>0){
          		//支付转化率
          		yd_zhl = 100*(yd_payPurchaseNumber/yd_visitorCount);
          	}
          	
          	float yd_kdj = 0;
          	if(yd_payPurchaseNumber > 0) {
          		yd_kdj = yd_payMoney/yd_payPurchaseNumber;
          	}
          	
          	rtMap.put("YD_PAY_MONEY",yd_payMoney); 		//支付金额
          	rtMap.put("YD_LIVELY_COUNT", yd_livelyCount); //日活跃用户数
          	rtMap.put("YD_PAY_COUNT", yd_payCount); 		//销售件数
  			rtMap.put("YD_PAY_NUM", yd_payNum); 	//支付单笔数
  			rtMap.put("YD_PURCHASE_NUMBER", yd_payPurchaseNumber); 			//支付买家数
			rtMap.put("YD_ZHL", yd_zhl);			//支付转化率
			rtMap.put("YD_KDJ", yd_kdj); //客单价
          	/**************************************************昨日相关统计     end****************************************************/
			
			/**************************************************昨日折线图相关统计     begin****************************************************/
			List<Float> lineYd = new ArrayList<Float>();
			/** 实时基本概况 折线图 成交额 **/
			List<Map<String, Object>> yd_payMoneyList = homeAnalysisDao.r_queryRealTimeDetail_PayMoney_Chart(paramMap);
			Map<String, Object> yd_payMoneyMap = list2Map(yd_payMoneyList,"CREATE_DATE","PAY_MONEY");
			/** 实时基本概况 折线图 预定订单的定金 **/
			List<Map<String, Object>> yd_preFirstMoneyList = homeAnalysisDao.r_queryRealTimeDetail_PreFirstMoney_Chart(paramMap);
			Map<String, Object> yd_preFirstMoneyMap = list2Map(yd_preFirstMoneyList,"CREATE_DATE","PRE_FIRST_MONEY");
          

           	//开始数据拼装
            for(int i=0;i<time_list.size();i++){
                key = time_list.get(i);
                //成交额
                yd_payMoney = yd_payMoneyMap.get(key)==null?0:Float.parseFloat(yd_payMoneyMap.get(key).toString());
                //预订订单的定金
                yd_preFirstMoney = yd_preFirstMoneyMap.get(key)==null?0:Float.parseFloat(yd_preFirstMoneyMap.get(key).toString());
                yd_payMoney+=yd_preFirstMoney;
                lineYd.add(yd_payMoney);
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
	 * 实时会员成交TOP
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryRealTimeMemberPayTop(HttpServletRequest request) {
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
            List<String> ids = queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            paramMap.put("num", 5);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
            String dateNowStr = sdf.format(new Date()); 
            paramMap.put("date_short", dateNowStr);
            //查询会员成交TOP
            List<Map<String, Object>> list = homeAnalysisDao.r_queryMemberPayTopList(paramMap);
            
            if (list != null && list.size() > 0) {
				pr.setState(true);
				pr.setMessage("查询会员成交TOP成功");
				pr.setObj(list);
			} else {
				pr.setState(true);
				pr.setMessage("无数据");
			}
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 实时会员退款TOP
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryRealTimeMemberReturnTop(HttpServletRequest request) {
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
            List<String> ids = queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            paramMap.put("num", 5);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
            String dateNowStr = sdf.format(new Date()); 
            paramMap.put("date_short", dateNowStr);
            //查询会员退款TOP
            List<Map<String, Object>> list = homeAnalysisDao.r_queryMemberReturnTopList(paramMap);
            
            if (list != null && list.size() > 0) {
				pr.setState(true);
				pr.setMessage("查询会员退款TOP成功");
				pr.setObj(list);
			} else {
				pr.setState(true);
				pr.setMessage("无数据");
			}
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 实时流量看板-浏览量
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryRealTimeFlowPvCount(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = queryByUserId(paramMap);
            paramMap.put("ids", ids);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
            String dateNowStr = sdf.format(new Date()); 
            paramMap.put("date_short", dateNowStr);
        	/** 实时流量看板 今日 浏览量 */
            float t_pvCount = homeAnalysisDao.r_queryRealTimeFlow_PvCount(paramMap);
            resultMap.put("td_pv_count", t_pvCount);
            
            Calendar c = Calendar.getInstance();  
			c.add(Calendar.DATE, -1);  
			dateNowStr = sdf.format(c.getTime());  
            paramMap.put("date_short", dateNowStr);
            /** 实时流量看板 昨日 浏览量 */
			float l_pvCount = homeAnalysisDao.r_queryRealTimeFlow_PvCount(paramMap);
            resultMap.put("yd_pv_count", l_pvCount);
            
            pr.setState(true);
            pr.setMessage("获取浏览量成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 实时流量看板-全站浏览量 折线
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryRealTimeFlowPvCountChart(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            List<String> ids = queryByUserId(paramMap);
            paramMap.put("ids", ids);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
            String dateNowStr = sdf.format(new Date()); 
            paramMap.put("date_short", dateNowStr);
            List<Map<String, Object>> allDataList = new ArrayList<Map<String, Object>>();
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
        	
            List<Map<String, Object>> pvCountList = homeAnalysisDao.r_queryRealTimeFlow_PvCount_Chart(paramMap);
            //全站浏览量
            Map<String,Object> pvCountMap = list2Map(pvCountList,"CREATE_DATE","PV_COUNT");
            
            paramMap.put("channel_name", "首页");
            List<Map<String, Object>> syPvCountList = homeAnalysisDao.r_queryRealTimeFlow_PvCount_Chart(paramMap);
            //首页浏览量
            Map<String,Object> syPvCountMap = list2Map(syPvCountList,"CREATE_DATE","PV_COUNT");
            
            paramMap.put("channel_name", "爆款秒批");
            List<Map<String, Object>> bkmpPvCountList = homeAnalysisDao.r_queryRealTimeFlow_PvCount_Chart(paramMap);
            //爆款秒批浏览量
            Map<String,Object> bkmpPvCountMap = list2Map(bkmpPvCountList,"CREATE_DATE","PV_COUNT");
            
            paramMap.put("channel_name", "限时秒杀");
            List<Map<String, Object>> xsmsPvCountList = homeAnalysisDao.r_queryRealTimeFlow_PvCount_Chart(paramMap);
            //限时秒杀浏览量
            Map<String,Object> xsmsPvCountMap = list2Map(xsmsPvCountList,"CREATE_DATE","PV_COUNT");
            
            paramMap.put("channel_name", "新品热卖");
            List<Map<String, Object>> xprmPvCountList = homeAnalysisDao.r_queryRealTimeFlow_PvCount_Chart(paramMap);
            //新品热卖浏览量
            Map<String,Object> xprmPvCountMap = list2Map(xprmPvCountList,"CREATE_DATE","PV_COUNT");
            
            paramMap.put("channel_name", "潮童搭配");
            List<Map<String, Object>> ctdpPvCountList = homeAnalysisDao.r_queryRealTimeFlow_PvCount_Chart(paramMap);
            //潮童搭配浏览量
            Map<String,Object> ctdpPvCountMap = list2Map(ctdpPvCountList,"CREATE_DATE","PV_COUNT");
            
            paramMap.put("channel_name", "定制服务");
            List<Map<String, Object>> dzfwPvCountList = homeAnalysisDao.r_queryRealTimeFlow_PvCount_Chart(paramMap);
            //定制服务浏览量
            Map<String,Object> dzfwPvCountMap = list2Map(dzfwPvCountList,"CREATE_DATE","PV_COUNT");
            
            paramMap.put("channel_name", "预售抢先");
            List<Map<String, Object>> ysqxPvCountList = homeAnalysisDao.r_queryRealTimeFlow_PvCount_Chart(paramMap);
            //预售抢先浏览量
            Map<String,Object> ysqxPvCountMap = list2Map(ysqxPvCountList,"CREATE_DATE","PV_COUNT");
            
            paramMap.put("channel_name", "商品列表");
            List<Map<String, Object>> splbPvCountList = homeAnalysisDao.r_queryRealTimeFlow_PvCount_Chart(paramMap);
            //商品列表浏览量
            Map<String,Object> splbPvCountMap = list2Map(splbPvCountList,"CREATE_DATE","PV_COUNT");
            
            paramMap.put("channel_name", "商品详情");
            List<Map<String, Object>> spxqPvCountList = homeAnalysisDao.r_queryRealTimeFlow_PvCount_Chart(paramMap);
            //商品详情浏览量
            Map<String,Object> spxqPvCountMap = list2Map(spxqPvCountList,"CREATE_DATE","PV_COUNT");
            
            paramMap.put("channel_name", "购物车");
            List<Map<String, Object>> gwcPvCountList = homeAnalysisDao.r_queryRealTimeFlow_PvCount_Chart(paramMap);
            //购物车浏览量
            Map<String,Object> gwcPvCountMap = list2Map(gwcPvCountList,"CREATE_DATE","PV_COUNT");
            
            //开始数据拼装
          	String key="";
          	
          	Map<String, Object> map = null;
            for(String time : time_list) {
            	key = time;
            	
                /** 全站浏览量 */
                float pvCount = pvCountMap.get(key)==null?0:Float.parseFloat(pvCountMap.get(key).toString());
                /** 首页 */
                float syPvCount = syPvCountMap.get(key)==null?0:Float.parseFloat(syPvCountMap.get(key).toString());
                /** 爆款秒批 */
                float bkmpPvCount = bkmpPvCountMap.get(key)==null?0:Float.parseFloat(bkmpPvCountMap.get(key).toString());
                /** 限时秒杀 */
                float xsmsPvCount = xsmsPvCountMap.get(key)==null?0:Float.parseFloat(xsmsPvCountMap.get(key).toString());
                /** 新品热卖 */
                float xprmPvCount = xprmPvCountMap.get(key)==null?0:Float.parseFloat(xprmPvCountMap.get(key).toString());
                /** 潮童搭配 */
                float ctdpPvCount = ctdpPvCountMap.get(key)==null?0:Float.parseFloat(ctdpPvCountMap.get(key).toString());
                /** 定制服务 */
                float dzfwPvCount = dzfwPvCountMap.get(key)==null?0:Float.parseFloat(dzfwPvCountMap.get(key).toString());
                /** 预售抢先 */
                float ysqxPvCount = ysqxPvCountMap.get(key)==null?0:Float.parseFloat(ysqxPvCountMap.get(key).toString());
                /** 商品列表 */
                float splbPvCount = splbPvCountMap.get(key)==null?0:Float.parseFloat(splbPvCountMap.get(key).toString());
                /** 商品详情 */
                float spxqPvCount = spxqPvCountMap.get(key)==null?0:Float.parseFloat(spxqPvCountMap.get(key).toString());
                /** 购物车 */
                float gwcPvCount = gwcPvCountMap.get(key)==null?0:Float.parseFloat(gwcPvCountMap.get(key).toString());
                
              	map = new HashMap<String, Object>();
              	//全站浏览量
              	map.put("CREATE_DATE", key);
              	map.put("CNT", pvCount);
              	map.put("PAGE_NAME", "全站浏览量");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//首页
              	map.put("CREATE_DATE", key);
              	map.put("CNT", syPvCount);
              	map.put("PAGE_NAME", "首页");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//爆款秒批
              	map.put("CREATE_DATE", key);
              	map.put("CNT", bkmpPvCount);
              	map.put("PAGE_NAME", "爆款秒批");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//限时秒杀
              	map.put("CREATE_DATE", key);
              	map.put("CNT", xsmsPvCount);
              	map.put("PAGE_NAME", "限时秒杀");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//新品热卖
              	map.put("CREATE_DATE", key);
              	map.put("CNT", xprmPvCount);
              	map.put("PAGE_NAME", "新品热卖");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//潮童搭配
              	map.put("CREATE_DATE", key);
              	map.put("CNT", ctdpPvCount);
              	map.put("PAGE_NAME", "潮童搭配");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//定制服务
              	map.put("CREATE_DATE", key);
              	map.put("CNT", dzfwPvCount);
              	map.put("PAGE_NAME", "定制服务");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//预售抢先
              	map.put("CREATE_DATE", key);
              	map.put("CNT", ysqxPvCount);
              	map.put("PAGE_NAME", "预售抢先");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//商品列表
              	map.put("CREATE_DATE", key);
              	map.put("CNT", splbPvCount);
              	map.put("PAGE_NAME", "商品列表");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//商品详情
              	map.put("CREATE_DATE", key);
              	map.put("CNT", spxqPvCount);
              	map.put("PAGE_NAME", "商品详情");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//购物车
              	map.put("CREATE_DATE", key);
              	map.put("CNT", gwcPvCount);
              	map.put("PAGE_NAME", "购物车");
              	allDataList.add(map);
            }
            
            //将原始数据组装为Echart可识别的格式
            resultMap = createData("PAGE_NAME", time_list, allDataList);
            
            pr.setState(true);
			pr.setMessage("获取流量总览折线成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 区域销售分析-销售区域排行
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySaleAreaRank(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("type"))) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            List<String> ids = queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            paramMap.put("num", 10);
            List<Map<String, Object>> provinceList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> cityList = new ArrayList<Map<String, Object>>();
            long count = 0;
            float money = 0;
        	if("2".equals(paramMap.get("type").toString())) {//下单买家数
            	/** 下单买家总数 */
                count = homeAnalysisDao.r_queryPurchaseNumberTotal(paramMap);
            	/** 省份 下单买家数 排行榜 */
                provinceList = homeAnalysisDao.r_queryPurchaseNumberProvince_Rank(paramMap);
                /** 城市 下单买家数 排行榜 */
                cityList = homeAnalysisDao.r_queryPurchaseNumberCity_Rank(paramMap);
            }else {//成交金额
            	/** 成交总金额 */
            	money = homeAnalysisDao.r_queryPayMoneyTotal(paramMap);
            	/** 省份 成交金额 排行榜 */
                provinceList = homeAnalysisDao.r_queryPayMoneyProvince_Rank(paramMap);
                /** 城市 成交金额 排行榜 */
                cityList = homeAnalysisDao.r_queryPayMoneyCity_Rank(paramMap);
            }
            
            if("2".equals(paramMap.get("type").toString())) {
            	for(Map<String, Object> p : provinceList) {
                	float purchaseNumber = Float.parseFloat(p.get("PURCHASE_NUMBER").toString());
                	p.put("RATIO", 100*purchaseNumber/count);
                }
            	for(Map<String, Object> c : cityList) {
                	float purchaseNumber = Float.parseFloat(c.get("PURCHASE_NUMBER").toString());
                	c.put("RATIO", 100*purchaseNumber/count);
                }
            }else {
            	for(Map<String, Object> p : provinceList) {
                	float payMoney = Float.parseFloat(p.get("PAY_MONEY").toString());
                	p.put("RATIO", 100*payMoney/money);
                }
            	for(Map<String, Object> c : cityList) {
                	float payMoney = Float.parseFloat(c.get("PAY_MONEY").toString());
                	c.put("RATIO", 100*payMoney/money);
                }
            }
            resultMap.put("provinceList", provinceList);
            resultMap.put("cityList", cityList);
            pr.setState(true);
			pr.setMessage("获取销售区域排行成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 区域销售分析-地图
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySaleAreaMap(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("type"))) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            List<String> ids = queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            List<Map<String, Object>> resultList = homeAnalysisDao.r_querySaleAreaMap(paramMap);
            pr.setState(true);
			pr.setMessage("获取销售区域地图成功");
			pr.setObj(resultList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	
	/**
     * 区域销售分析-会员总数
     * @param request
     * @return
     */
	@SuppressWarnings("unchecked")
	public ProcessResult querySaleAreaMemberTotal(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = queryByUserId(paramMap);
            paramMap.put("ids", ids);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
        	/** 会员总数 */
            float t_memberTotal = homeAnalysisDao.r_queryMemberTotal(paramMap);
            resultMap.put("member_total", t_memberTotal);
            
            Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            /** 会员总数 */
			float l_memberTotal = homeAnalysisDao.r_queryMemberTotal(paramMap);
            if(t_memberTotal == 0 || l_memberTotal == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_memberTotal-l_memberTotal)/l_memberTotal*100));
            }
            
            pr.setState(true);
            pr.setMessage("获取会员总数成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 区域销售分析-新增会员数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySaleAreaMemberAddCount(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = queryByUserId(paramMap);
            paramMap.put("ids", ids);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
        	/** 新增会员数 */
            float t_memberAddCount = homeAnalysisDao.r_queryMemberAddCount(paramMap);
            resultMap.put("member_add_count", t_memberAddCount);
            
            Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            /** 新增会员数 */
			float l_memberAddCount = homeAnalysisDao.r_queryMemberAddCount(paramMap);
            if(t_memberAddCount == 0 || l_memberAddCount == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_memberAddCount-l_memberAddCount)/l_memberAddCount*100));
            }
            
            pr.setState(true);
            pr.setMessage("获取新增会员数成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 区域销售分析-活跃用户数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySaleAreaLivelyCount(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = queryByUserId(paramMap);
            paramMap.put("ids", ids);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
        	/** 活跃用户数 */
            float t_livelyCount = homeAnalysisDao.r_queryLivelyCount(paramMap);
            resultMap.put("lively_count", t_livelyCount);
            
            Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            /** 活跃用户数 */
			float l_livelyCount = homeAnalysisDao.r_queryLivelyCount(paramMap);
            if(t_livelyCount == 0 || l_livelyCount == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_livelyCount-l_livelyCount)/l_livelyCount*100));
            }
            
            pr.setState(true);
            pr.setMessage("获取活跃用户数成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 区域销售分析-下单笔数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySaleAreaOrderNum(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = queryByUserId(paramMap);
            paramMap.put("ids", ids);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
        	/** 下单笔数 */
            float t_orderNum = homeAnalysisDao.r_queryOrderNum(paramMap);
            resultMap.put("order_num", t_orderNum);
            
            Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            /** 下单笔数 */
			float l_orderNum = homeAnalysisDao.r_queryOrderNum(paramMap);
            if(t_orderNum == 0 || l_orderNum == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_orderNum-l_orderNum)/l_orderNum*100));
            }
            
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
	 * 区域销售分析-支付笔数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySaleAreaPayNum(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = queryByUserId(paramMap);
            paramMap.put("ids", ids);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
        	/** 支付笔数 */
            float t_payNum = homeAnalysisDao.r_queryPayNum(paramMap);
            resultMap.put("pay_num", t_payNum);
            
            Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            /** 支付笔数 */
			float l_payNum = homeAnalysisDao.r_queryPayNum(paramMap);
            if(t_payNum == 0 || l_payNum == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_payNum-l_payNum)/l_payNum*100));
            }
            
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
	 * 区域销售分析-支付件数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySaleAreaPayCount(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = queryByUserId(paramMap);
            paramMap.put("ids", ids);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
        	/** 支付件数 */
            float t_payCount = homeAnalysisDao.r_queryPayCount(paramMap);
            resultMap.put("pay_count", t_payCount);
            
            Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            /** 支付件数 */
			float l_payCount = homeAnalysisDao.r_queryPayCount(paramMap);
            if(t_payCount == 0 || l_payCount == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_payCount-l_payCount)/l_payCount*100));
            }
            
            pr.setState(true);
            pr.setMessage("获取支付件数成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 区域销售分析-成交额
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySaleAreaPayMoney(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = queryByUserId(paramMap);
            paramMap.put("ids", ids);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
        	/** 成交额 */
            float t_payMoney = homeAnalysisDao.r_queryPayMoney(paramMap);
            resultMap.put("pay_money", t_payMoney);
            
            Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            /** 成交额 */
			float l_payMoney = homeAnalysisDao.r_queryPayMoney(paramMap);
            if(t_payMoney == 0 || l_payMoney == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_payMoney-l_payMoney)/l_payMoney*100));
            }
            
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
	 * 区域销售分析-支付买家数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySaleAreaPayPurchaseNumber(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = queryByUserId(paramMap);
            paramMap.put("ids", ids);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
        	/** 支付买家数 */
            float t_payPurchaseNumber = homeAnalysisDao.r_queryPayPurchaseNumber(paramMap);
            resultMap.put("pay_purchase_number", t_payPurchaseNumber);
            
            Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            /** 支付买家数 */
			float l_payPurchaseNumber = homeAnalysisDao.r_queryPayPurchaseNumber(paramMap);
            if(t_payPurchaseNumber == 0 || l_payPurchaseNumber == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_payPurchaseNumber-l_payPurchaseNumber)/l_payPurchaseNumber*100));
            }
            
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
	 * 区域销售分析-成功退款金额
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySaleAreaReturnMoney(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = queryByUserId(paramMap);
            paramMap.put("ids", ids);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
        	/** 成功退款金额 */
            float t_returnMoney = homeAnalysisDao.r_queryReturnMoney(paramMap);
            resultMap.put("return_money", t_returnMoney);
            
            Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            /** 成功退款金额 */
			float l_returnMoney = homeAnalysisDao.r_queryReturnMoney(paramMap);
            if(t_returnMoney == 0 || l_returnMoney == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_returnMoney-l_returnMoney)/l_returnMoney*100));
            }
            
            pr.setState(true);
            pr.setMessage("获取成功退款金额成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 区域销售分析-趋势图
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySaleAreaTrendChart(HttpServletRequest request) {
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
            List<String> ids = queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<Map<String, Object>> allDataList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> memberAddCountList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> livelyCountList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> ordeNumList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> payNumList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> payPurchaseNumberCountMoneyList = new ArrayList<Map<String, Object>>();
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
                /** 折线图 新增会员数 */
            	memberAddCountList = homeAnalysisDao.r_queryMemberAddCountD_Chart(paramMap);
            	/** 折线图 活跃用户数 */
            	livelyCountList = homeAnalysisDao.r_queryLivelyCountD_Chart(paramMap);
                /** 折线图 下单笔数 */
                ordeNumList = homeAnalysisDao.r_queryOrderNumD_Chart(paramMap);
                /** 折线图 支付单笔数 */
                payNumList = homeAnalysisDao.r_queryPayNumD_Chart(paramMap);
                /** 折线图 支付买家数、支付件数、成交额 */
                payPurchaseNumberCountMoneyList = homeAnalysisDao.r_queryPayPurchaseNumberCountMoneyD_Chart(paramMap);
                /** 折线图 预定订单的定金 */
                preFirstMoneyList = homeAnalysisDao.r_queryPreFirstMoneyD_Chart(paramMap);
                /** 折线图 成功退款金额 */
                returnMoneyList = homeAnalysisDao.r_queryReturnMoneyD_Chart(paramMap);
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
                /** 折线图 新增会员数 */
            	memberAddCountList = homeAnalysisDao.r_queryMemberAddCount_Chart(paramMap);
            	/** 折线图 活跃用户数 */
            	livelyCountList = homeAnalysisDao.r_queryLivelyCount_Chart(paramMap);
                /** 折线图 下单笔数 */
                ordeNumList = homeAnalysisDao.r_queryOrderNum_Chart(paramMap);
                /** 折线图 支付单笔数 */
                payNumList = homeAnalysisDao.r_queryPayNum_Chart(paramMap);
                /** 折线图 支付买家数、支付件数、成交额 */
                payPurchaseNumberCountMoneyList = homeAnalysisDao.r_queryPayPurchaseNumberCountMoney_Chart(paramMap);
                /** 折线图 预定订单的定金 */
                preFirstMoneyList = homeAnalysisDao.r_queryPreFirstMoney_Chart(paramMap);
                /** 折线图 成功退款金额 */
                returnMoneyList = homeAnalysisDao.r_queryReturnMoney_Chart(paramMap);
            }
            
            //新增会员数
            Map<String,Object> memberAddCountMap = list2Map(memberAddCountList,"CREATE_DATE","MEMBER_ADD_COUNT");
            //活跃用户数
            Map<String,Object> livelyCountMap = list2Map(livelyCountList,"CREATE_DATE","LIVELY_COUNT");
            //下单笔数
            Map<String,Object> ordeNumMap = list2Map(ordeNumList,"CREATE_DATE","ORDER_NUM");
            //支付单笔数
            Map<String,Object> payNumMap = list2Map(payNumList,"CREATE_DATE","PAY_NUM");
            //支付买家数
            Map<String,Object> payPurchaseNumberMap = list2Map(payPurchaseNumberCountMoneyList,"CREATE_DATE","PURCHASE_NUMBER");
            //支付件数
            Map<String,Object> payCountMap = list2Map(payPurchaseNumberCountMoneyList,"CREATE_DATE","PAY_COUNT");
            //成交额
            Map<String,Object> payMoneyMap = list2Map(payPurchaseNumberCountMoneyList,"CREATE_DATE","PAY_MONEY");
            //预定订单的定金
            Map<String,Object> preFirstMoneyMap = list2Map(preFirstMoneyList,"CREATE_DATE","PRE_FIRST_MONEY");
            //成功退款金额
            Map<String,Object> returnMoneyMap = list2Map(returnMoneyList,"CREATE_DATE","RETURN_MONEY");
            
            //开始数据拼装
          	String key="";
          	
          	Map<String, Object> map = null;
            for(String time : time_list) {
            	key = time;
                /** 新增会员数 */
                float memberAddCount = memberAddCountMap.get(key)==null?0:Float.parseFloat(memberAddCountMap.get(key).toString());
                /** 活跃用户数 */
                float livelyCount = livelyCountMap.get(key)==null?0:Float.parseFloat(livelyCountMap.get(key).toString());
                /** 下单笔数 */
                float orderNum = ordeNumMap.get(key)==null?0:Float.parseFloat(ordeNumMap.get(key).toString());
                /** 支付单笔数 */
                float payNum = payNumMap.get(key)==null?0:Float.parseFloat(payNumMap.get(key).toString());
                /** 支付买家数 */
                float payPurchaseNumber = payPurchaseNumberMap.get(key)==null?0:Float.parseFloat(payPurchaseNumberMap.get(key).toString());
                /** 支付件数 */
                float payCount = payCountMap.get(key)==null?0:Float.parseFloat(payCountMap.get(key).toString());
                /** 成交额 */
                float payMoney = payMoneyMap.get(key)==null?0:Float.parseFloat(payMoneyMap.get(key).toString());
                /** 预定订单的定金 */
                float preFirstMoney = preFirstMoneyMap.get(key)==null?0:Float.parseFloat(preFirstMoneyMap.get(key).toString());
                /** 成功退款金额 */
                float returnMoney = returnMoneyMap.get(key)==null?0:Float.parseFloat(returnMoneyMap.get(key).toString());
                payMoney+=preFirstMoney;
                
              	map = new HashMap<String, Object>();
              	//新增会员数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", memberAddCount);
              	map.put("PAGE_NAME", "新增会员数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//活跃用户数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", livelyCount);
              	map.put("PAGE_NAME", "活跃用户数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//下单笔数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", orderNum);
              	map.put("PAGE_NAME", "下单笔数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//支付单笔数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", payNum);
              	map.put("PAGE_NAME", "支付单笔数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//支付件数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", payCount);
              	map.put("PAGE_NAME", "支付件数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//成交额
              	map.put("CREATE_DATE", key);
              	map.put("CNT", payMoney);
              	map.put("PAGE_NAME", "成交额");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//支付买家数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", payPurchaseNumber);
              	map.put("PAGE_NAME", "支付买家数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//成功退款金额
              	map.put("CNT", returnMoney);
              	map.put("CREATE_DATE", key);
              	map.put("PAGE_NAME", "成功退款金额");
              	allDataList.add(map);
              	
            }
            
            //将原始数据组装为Echart可识别的格式
            resultMap = createData("PAGE_NAME", time_list, allDataList);
            
            pr.setState(true);
			pr.setMessage("获取区域取数图成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 商品排行
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductRank(HttpServletRequest request) {
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
            List<String> ids = queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            paramMap.put("num", 8);
            //商品访客数
			String file_names_pvc = ":P_VISITOR_COUNT:";
			//商品浏览量
			String file_names_ppc = ":P_PV_COUNT:";
			//商品收藏数
			String file_names_cc = ":COLLECT_COUNT:";
			//下单件数
			String file_names_oc = ":ORDER_COUNT:";
			//支付件数
			String file_names_pc = ":PAY_COUNT:";
			//退款件数
			String file_names_rc = ":RETURN_COUNT:";
			//商品销售额
			String file_names_pm = ":PAY_MONEY:";
			//退款商品金额
			String file_names_rm = ":RETURN_MONEY:";
			List<Map<String, Object>> list=null;
			
			//需要查询的商品列表
			List<String> productList = new ArrayList<String>();
			if((paramMap.containsKey("sort")&&!"".equals(paramMap.get("sort").toString()))
					&&(paramMap.containsKey("sort_by")&&!"".equals(paramMap.get("sort_by").toString()))){
				String sort = ":"+paramMap.get("sort").toString()+":";
				if(file_names_pvc.indexOf(sort)!=-1) {
					//商品访客数---获取排序后的商品排行
					productList=homeAnalysisDao.r_queryProductRank_ProductVisitorCount(paramMap);
				}else if(file_names_ppc.indexOf(sort)!=-1) {
					//商品浏览量---获取排序后的商品排行
					productList=homeAnalysisDao.r_queryProductRank_ProductPvCount(paramMap);
				}else if(file_names_cc.indexOf(sort)!=-1) {
					//商品收藏数---获取排序后的商品排行
					productList=homeAnalysisDao.r_queryProductRank_CollectCount(paramMap);
				}else if(file_names_oc.indexOf(sort)!=-1) {
					//下单件数---获取排序后的商品排行
					productList=homeAnalysisDao.r_queryProductRank_OrderCount(paramMap);
				}else if(file_names_pc.indexOf(sort)!=-1) {
					//支付件数---获取排序后的商品排行
					productList=homeAnalysisDao.r_queryProductRank_PayCount(paramMap);
				}else if(file_names_rc.indexOf(sort)!=-1) {
					//退款件数---获取排序后的商品排行
					productList=homeAnalysisDao.r_queryProductRank_ReturnCount(paramMap);
				}else if(file_names_pm.indexOf(sort)!=-1) {
					//商品销售额---获取排序后的商品排行
					productList=homeAnalysisDao.r_queryProductRank_PayMoney(paramMap);
				}else if(file_names_rm.indexOf(sort)!=-1) {
					//退款商品金额---获取排序后的商品排行
					productList=homeAnalysisDao.r_queryProductRank_ReturnMoney(paramMap);
				}else {
					pr.setState(false);
					pr.setMessage("未配置的排序字段，请联系管理员【"+paramMap.get("sort").toString()+"】");
					return pr;
				}
			}else{
				//查询默认排序的商品信息(默认按商品销售额排序)
				productList=homeAnalysisDao.r_queryProductRankListBy_Default(paramMap);
			}
			
			if(!productList.isEmpty()&&productList.size()>0){
				paramMap.put("productList", productList);
				list = homeAnalysisDao.r_queryProductList(paramMap);
			}
			
			if (list != null && list.size() > 0) {
				//商品访客数,商品浏览量
				List<Map<String,Object>> visitorPvCountList = homeAnalysisDao.r_queryProductVisitorPvCount(paramMap);
				Map<String,Object> visitorCountMap = list2Map(visitorPvCountList,"ITEMNUMBER","VISITOR_COUNT");
				Map<String,Object> pvCountMap = list2Map(visitorPvCountList,"ITEMNUMBER","PV_COUNT");
				//商品收藏数
				List<Map<String,Object>> collectCountList = homeAnalysisDao.r_queryProductCollectCount(paramMap);
				Map<String,Object> collectCountMap = list2Map(collectCountList,"ITEMNUMBER","COLLECT_COUNT");
				//下单件数
				List<Map<String,Object>> orderCountList = homeAnalysisDao.r_queryProductOrderCount(paramMap);
				Map<String,Object> orderCountMap = list2Map(orderCountList,"ITEMNUMBER","ORDER_COUNT");
				//支付件数、商品销售额
				List<Map<String,Object>> payList = homeAnalysisDao.r_queryProductPayInfo(paramMap);
				Map<String,Object> payCountMap = list2Map(payList,"ITEMNUMBER","PAY_COUNT");
				Map<String,Object> payMoneyMap = list2Map(payList,"ITEMNUMBER","PAY_MONEY");
				//预定订单的定金
				List<Map<String,Object>> preFirstMoneyList = homeAnalysisDao.r_queryProductPreFirstMoney(paramMap);
				Map<String,Object> preFirstMoneyMap = list2Map(preFirstMoneyList,"ITEMNUMBER","PRE_FIRST_MONEY");
				//退款件数、退款商品金额
				List<Map<String,Object>> returnList = homeAnalysisDao.r_queryProductReturnInfo(paramMap);
				Map<String,Object> returnCountMap = list2Map(returnList,"ITEMNUMBER","RETURN_COUNT");
				Map<String,Object> returnMoneyMap = list2Map(returnList,"ITEMNUMBER","RETURN_MONEY");
				
				Map<String,Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String itemnumber = tempMap.get("ITEMNUMBER").toString();
					tempMap.put("P_VISITOR_COUNT",visitorCountMap.get(itemnumber)==null?0:Float.parseFloat(visitorCountMap.get(itemnumber).toString()));
					tempMap.put("P_PV_COUNT",pvCountMap.get(itemnumber)==null?0:Float.parseFloat(pvCountMap.get(itemnumber).toString()));
					tempMap.put("COLLECT_COUNT",collectCountMap.get(itemnumber)==null?0:Float.parseFloat(collectCountMap.get(itemnumber).toString()));
					tempMap.put("ORDER_COUNT",orderCountMap.get(itemnumber)==null?0:Float.parseFloat(orderCountMap.get(itemnumber).toString()));
					tempMap.put("PAY_COUNT",payCountMap.get(itemnumber)==null?0:Float.parseFloat(payCountMap.get(itemnumber).toString()));
					tempMap.put("RETURN_COUNT",returnCountMap.get(itemnumber)==null?0:Float.parseFloat(returnCountMap.get(itemnumber).toString()));
					float payMoney = payMoneyMap.get(itemnumber)==null?0:Float.parseFloat(payMoneyMap.get(itemnumber).toString());
					float preFirstMoney = preFirstMoneyMap.get(itemnumber)==null?0:Float.parseFloat(preFirstMoneyMap.get(itemnumber).toString());
					tempMap.put("PAY_MONEY",payMoney+preFirstMoney);
					tempMap.put("RETURN_MONEY",returnMoneyMap.get(itemnumber)==null?0:Float.parseFloat(returnMoneyMap.get(itemnumber).toString()));
				}
				pr.setState(true);
				pr.setMessage("获取商品排行成功");
				pr.setObj(list);
			}else {
				pr.setState(true);
				pr.setMessage("无数据");
			}
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 品牌排行
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryBrandRank(HttpServletRequest request) {
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
            List<String> ids = queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            paramMap.put("num", 10);
			//商品浏览量
			String file_names_ppc = ":P_PV_COUNT:";
			//商品销售额
			String file_names_pm = ":PAY_MONEY:";
			//退款商品金额
			String file_names_rm = ":RETURN_MONEY:";
			List<Map<String, Object>> list=null;
			
			//需要查询的品牌列表
			List<String> brandList = new ArrayList<String>();
			if((paramMap.containsKey("sort")&&!"".equals(paramMap.get("sort").toString()))
					&&(paramMap.containsKey("sort_by")&&!"".equals(paramMap.get("sort_by").toString()))){
				String sort = ":"+paramMap.get("sort").toString()+":";
				if(file_names_ppc.indexOf(sort)!=-1) {
					//商品浏览量---获取排序后的品牌排行
					brandList=homeAnalysisDao.r_queryBrandRank_ProductPvCount(paramMap);
				}else if(file_names_pm.indexOf(sort)!=-1) {
					//商品销售额---获取排序后的品牌排行
					brandList=homeAnalysisDao.r_queryBrandRank_PayMoney(paramMap);
				}else if(file_names_rm.indexOf(sort)!=-1) {
					//退款商品金额---获取排序后的商品排行
					brandList=homeAnalysisDao.r_queryBrandRank_ReturnMoney(paramMap);
				}else {
					pr.setState(false);
					pr.setMessage("未配置的排序字段，请联系管理员【"+paramMap.get("sort").toString()+"】");
					return pr;
				}
			}else{
				//查询默认排序的品牌信息(默认按商品销售额排序)
				brandList=homeAnalysisDao.r_queryBrandRankListBy_Default(paramMap);
			}
			
			if(!brandList.isEmpty()&&brandList.size()>0){
				paramMap.put("brandList", brandList);
				list = homeAnalysisDao.r_queryBrandList(paramMap);
			}
			
			if (list != null && list.size() > 0) {
				//商品浏览量
				List<Map<String,Object>> pvCountList = homeAnalysisDao.r_queryBrandPvCount(paramMap);
				Map<String,Object> pvCountMap = list2Map(pvCountList,"BRAND_ID","PV_COUNT");
				//商品销售额
				List<Map<String,Object>> payMoneyList = homeAnalysisDao.r_queryBrandPayMoney(paramMap);
				Map<String,Object> payMoneyMap = list2Map(payMoneyList,"BRAND_ID","PAY_MONEY");
				//预定订单的定金
				List<Map<String,Object>> preFirstMoneyList = homeAnalysisDao.r_queryBrandPreFirstMoney(paramMap);
				Map<String,Object> preFirstMoneyMap = list2Map(preFirstMoneyList,"BRAND_ID","PRE_FIRST_MONEY");
				//退款商品金额
				List<Map<String,Object>> returnMoneyList = homeAnalysisDao.r_queryBrandReturnMoney(paramMap);
				Map<String,Object> returnMoneyMap = list2Map(returnMoneyList,"BRAND_ID","RETURN_MONEY");
				
				Map<String,Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String brand_id = tempMap.get("BRAND_ID").toString();
					tempMap.put("P_PV_COUNT",pvCountMap.get(brand_id)==null?0:Float.parseFloat(pvCountMap.get(brand_id).toString()));
					float payMoney = payMoneyMap.get(brand_id)==null?0:Float.parseFloat(payMoneyMap.get(brand_id).toString());
					float preFirstMoney = preFirstMoneyMap.get(brand_id)==null?0:Float.parseFloat(preFirstMoneyMap.get(brand_id).toString());
					tempMap.put("PAY_MONEY",payMoney+preFirstMoney);
					tempMap.put("RETURN_MONEY",returnMoneyMap.get(brand_id)==null?0:Float.parseFloat(returnMoneyMap.get(brand_id).toString()));
				}
				pr.setState(true);
				pr.setMessage("获取品牌排行成功");
				pr.setObj(list);
			}else {
				pr.setState(true);
				pr.setMessage("无数据");
			}
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 入驻商排行
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryStationedRank(HttpServletRequest request) {
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
            List<String> ids = queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            paramMap.put("num", 10);
			//商品销量
			String file_names_pc = ":PAY_COUNT:";
			//商品销售额
			String file_names_pm = ":PAY_MONEY:";
			//未发退货数
			String file_names_urc = ":UNSENT_RETURN_COUNT:";
			//已发退货数
			String file_names_src = ":SENT_RETURN_COUNT:";
			List<Map<String, Object>> list=null;
			
			//需要查询的入驻商列表
			List<String> stationedList = new ArrayList<String>();
			if((paramMap.containsKey("sort")&&!"".equals(paramMap.get("sort").toString()))
					&&(paramMap.containsKey("sort_by")&&!"".equals(paramMap.get("sort_by").toString()))){
				String sort = ":"+paramMap.get("sort").toString()+":";
				if(file_names_pc.indexOf(sort)!=-1) {
					//商品销量---获取排序后的入驻商排行
					stationedList=homeAnalysisDao.r_queryStationedRank_ProductPvCount(paramMap);
				}else if(file_names_pm.indexOf(sort)!=-1) {
					//商品销售额---获取排序后的入驻商排行
					stationedList=homeAnalysisDao.r_queryStationedRank_PayMoney(paramMap);
				}else if(file_names_urc.indexOf(sort)!=-1) {
					//未发退货数---获取排序后的入驻商排行
					stationedList=homeAnalysisDao.r_queryStationedRank_UnsentReturnCount(paramMap);
				}else if(file_names_src.indexOf(sort)!=-1) {
					//已发退货数---获取排序后的入驻商排行
					stationedList=homeAnalysisDao.r_queryStationedRank_SentReturnCount(paramMap);
				}else {
					pr.setState(false);
					pr.setMessage("未配置的排序字段，请联系管理员【"+paramMap.get("sort").toString()+"】");
					return pr;
				}
			}else{
				//查询默认排序的入驻商信息(默认按商品销售额排序)
				stationedList=homeAnalysisDao.r_queryStationedRankListBy_Default(paramMap);
			}
			
			if(!stationedList.isEmpty()&&stationedList.size()>0){
				paramMap.put("stationedList", stationedList);
				list = homeAnalysisDao.r_queryStationedList(paramMap);
			}
			
			if (list != null && list.size() > 0) {
				//商品销量、商品销售额
				List<Map<String,Object>> payList = homeAnalysisDao.r_queryStationedPayInfo(paramMap);
				Map<String,Object> payCountMap = list2Map(payList,"STATIONED_USER_ID","PAY_COUNT");
				Map<String,Object> payMoneyMap = list2Map(payList,"STATIONED_USER_ID","PAY_MONEY");
				//预定订单的定金
				List<Map<String,Object>> preFirstMoneyList = homeAnalysisDao.r_queryStationedPreFirstMoney(paramMap);
				Map<String,Object> preFirstMoneyMap = list2Map(preFirstMoneyList,"STATIONED_USER_ID","PRE_FIRST_MONEY");
				//未发退货数
				List<Map<String,Object>> unsentReturnCountList = homeAnalysisDao.r_queryStationedUnsentReturnCount(paramMap);
				Map<String,Object> unsentReturnCountMap = list2Map(unsentReturnCountList,"STATIONED_USER_ID","UNSENT_RETURN_COUNT");
				//已发退货数
				List<Map<String,Object>> sentReturnCountList = homeAnalysisDao.r_queryStationedSentReturnCount(paramMap);
				Map<String,Object> sentReturnCountMap = list2Map(sentReturnCountList,"STATIONED_USER_ID","SENT_RETURN_COUNT");
				
				Map<String,Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String stationed_user_id = tempMap.get("STATIONED_USER_ID").toString();
					tempMap.put("PAY_COUNT",payCountMap.get(stationed_user_id)==null?0:Float.parseFloat(payCountMap.get(stationed_user_id).toString()));
					float payMoney = payMoneyMap.get(stationed_user_id)==null?0:Float.parseFloat(payMoneyMap.get(stationed_user_id).toString());
					float preFirstMoney = preFirstMoneyMap.get(stationed_user_id)==null?0:Float.parseFloat(preFirstMoneyMap.get(stationed_user_id).toString());
					tempMap.put("PAY_MONEY",payMoney+preFirstMoney);
					tempMap.put("UNSENT_RETURN_COUNT",unsentReturnCountMap.get(stationed_user_id)==null?0:Float.parseFloat(unsentReturnCountMap.get(stationed_user_id).toString()));
					tempMap.put("SENT_RETURN_COUNT",sentReturnCountMap.get(stationed_user_id)==null?0:Float.parseFloat(sentReturnCountMap.get(stationed_user_id).toString()));
				}
				pr.setState(true);
				pr.setMessage("获取入驻商排行成功");
				pr.setObj(list);
			}else {
				pr.setState(true);
				pr.setMessage("无数据");
			}
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