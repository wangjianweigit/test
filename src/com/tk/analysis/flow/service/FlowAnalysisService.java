package com.tk.analysis.flow.service;

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

import com.tk.analysis.flow.dao.FlowAnalysisDao;
import com.tk.analysis.home.service.HomeAnalysisService;
import com.tk.sys.util.DateUtils;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * 
 * Copyright (c), 2019, Tongku
 * FileName : FlowAnalysisService.java
 * 运营分析-流量 service层
 *
 * @author yejingquan
 * @version 1.00
 * @date Jun 18, 2019
 */
@Service("FlowAnalysisService")
public class FlowAnalysisService {
	
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private HomeAnalysisService homeAnalysisService;
	@Resource
	private FlowAnalysisDao flowAnalysisDao;
	private String[] colors={"#2f4554 ", "#c23531", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF", "#001cf1", "#000000", "#50d400", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF", "#001cf1", "#000000", "#50d400", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF", "#001cf1", "#000000", "#50d400", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF", "#001cf1", "#000000", "#50d400", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF", "#001cf1", "#000000", "#50d400", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF", "#001cf1", "#000000", "#50d400", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF"};
	
	/**
	 * 流量总览-全站访客数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryOverview_VisitorCount(HttpServletRequest request) {
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
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_visitorCount = 0;
            float l_visitorCount = 0;
        	/** 流量总览-全站访客数 */
            t_visitorCount = flowAnalysisDao.r_queryOverview_VisitorCount(paramMap);
            Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            //较上个周期
            l_visitorCount = flowAnalysisDao.r_queryOverview_VisitorCount(paramMap);
            if(t_visitorCount == 0 || l_visitorCount == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_visitorCount-l_visitorCount)/l_visitorCount*100));
            }
            resultMap.put("visitor_count", t_visitorCount);
            
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
	 * 流量总览-全站浏览量
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryOverview_PvCount(HttpServletRequest request) {
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
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_pvCount = 0;
            float l_pvCount = 0;
        	/** 流量总览-浏览量 */
        	t_pvCount = flowAnalysisDao.r_queryOverview_PvCount(paramMap);
        	Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            //较上个周期
            l_pvCount = flowAnalysisDao.r_queryOverview_PvCount(paramMap);
            if(t_pvCount == 0 || l_pvCount == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_pvCount-l_pvCount)/l_pvCount*100));
            }
            resultMap.put("pv_count", t_pvCount);
            
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
	 * 流量总览-商品收藏数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryOverview_CollectCount(HttpServletRequest request) {
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
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_collectCount = 0;
            float l_collectCount = 0;
        	/** 流量总览-商品收藏数 */
        	t_collectCount = flowAnalysisDao.r_queryOverview_CollectCount(paramMap);
        	Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            //较上个周期
			l_collectCount = flowAnalysisDao.r_queryOverview_CollectCount(paramMap);
            if(t_collectCount == 0 || l_collectCount == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_collectCount-l_collectCount)/l_collectCount*100));
            }
            resultMap.put("collect_count", t_collectCount);
            
            pr.setState(true);
            pr.setMessage("获取商品收藏数成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 流量总览-折线
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryOverviewChart(HttpServletRequest request) {
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
            List<Map<String, Object>> visitorPvCountList = new ArrayList<Map<String, Object>>();
            //List<Map<String, Object>> collectCountList = new ArrayList<Map<String, Object>>();
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
            	/** 流量总览 折线图 全站访客数和全站浏览量 */
            	visitorPvCountList = flowAnalysisDao.r_queryVisitorPvCountD_Chart(paramMap);
                /** 流量总览 折线图 商品收藏数 */
            	//collectCountList = flowAnalysisDao.queryOverview_CollectCountD_Chart(paramMap);
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
            	/** 流量总览 折线图 全站访客数和全站浏览量 */
            	visitorPvCountList = flowAnalysisDao.r_queryVisitorPvCount_Chart(paramMap);
                /** 流量总览 折线图 商品收藏数 */
            	//collectCountList = flowAnalysisDao.queryOverview_CollectCount_Chart(paramMap);
            }
            
            //全站访客数
            Map<String,Object> visitorCountMap = list2Map(visitorPvCountList,"CREATE_DATE","VISITOR_COUNT");
            //全站浏览量
            Map<String,Object> pvCountMap = list2Map(visitorPvCountList,"CREATE_DATE","PV_COUNT");
            //商品收藏数
           // Map<String,Object> collectCountMap = list2Map(collectCountList,"CREATE_DATE","collect_count");
            
            //开始数据拼装
          	String key="";
          	
          	Map<String, Object> map = null;
            for(String time : time_list) {
            	key = time;
            	
            	/** 流量总览-全站访客数 */
                float visitorCount = visitorCountMap.get(key)==null?0:Float.parseFloat(visitorCountMap.get(key).toString());
                /** 流量总览-全站浏览量 */
                float pvCount = pvCountMap.get(key)==null?0:Float.parseFloat(pvCountMap.get(key).toString());
                /** 流量总览-商品收藏数 */
                //float collectCount = collectCountMap.get(key)==null?0:Float.parseFloat(collectCountMap.get(key).toString());
                
                map = new HashMap<String, Object>();
                //全站访客数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", visitorCount);
              	map.put("PAGE_NAME", "全站访客数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//全站浏览量
              	map.put("CREATE_DATE", key);
              	map.put("CNT", pvCount);
              	map.put("PAGE_NAME", "全站浏览量");
              	allDataList.add(map);
              	
//              	map = new HashMap<String, Object>();
//              	//商品收藏数
//              	map.put("CREATE_DATE", key);
//              	map.put("CNT", collectCount);
//              	map.put("PAGE_NAME", "商品收藏数");
//              	allDataList.add(map);
            }
            
            //将原始数据组装为Echart可识别的格式
            resultMap = createData("PAGE_NAME", time_list, allDataList);
            
            pr.setState(true);
			pr.setMessage("获取流量总览折线成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	
	/**
	 * 流量来源TOP
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryFlowSourceTop(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
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
        	/** 流量来源列表 */
            List<Map<String, Object>> list = flowAnalysisDao.r_queryFlowSourceList(paramMap);
            
            if(list !=null && list.size() > 0) {
            	/** 流量来源-引导下单买家数 */
            	List<Map<String, Object>> purchaseNumberList = flowAnalysisDao.r_queryFlowSource_PurchaseNumber(paramMap);
                Map<String, Object> purchaseNumberMap = list2Map(purchaseNumberList, "CHANNEL_NAME", "PURCHASE_NUMBER");
            	
            	Map<String, Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String channel_name = tempMap.get("CHANNEL_NAME").toString();
					//全站访客数
					float visitorCount = Float.parseFloat(tempMap.get("VISITOR_COUNT").toString());
					//引导下单买家数
					float purchaseNumber = purchaseNumberMap.get(channel_name)==null?0:Float.parseFloat(purchaseNumberMap.get(channel_name).toString());
					tempMap.put("O_PURCHASE_NUMBER",purchaseNumber);
					//引导下单转化率
					if(purchaseNumber == 0 || visitorCount == 0) {
						tempMap.put("O_ZHL", 0);
					}else {
						tempMap.put("O_ZHL", m2((purchaseNumber/visitorCount)*100));
					}
				}
            }
            pr.setState(true);
            pr.setMessage("获取流量来源TOP成功");
            pr.setObj(list);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 流量趋势
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryFlowTrendChart(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(paramMap.get("start_date"))||StringUtils.isEmpty(paramMap.get("end_date"))||StringUtils.isEmpty(paramMap.get("channel_name"))) {
            	 pr.setState(false);
                 pr.setMessage("缺少参数");
                 return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<Map<String, Object>> allDataList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> visitorPvCountList = new ArrayList<Map<String, Object>>();
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
            	/** 流量趋势 折线图 全站访客数，全站浏览量 */
            	visitorPvCountList = flowAnalysisDao.r_queryVisitorPvCountD_Chart(paramMap);
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
            	/** 流量趋势 折线图 全站访客数，全站浏览量 */
            	visitorPvCountList = flowAnalysisDao.r_queryVisitorPvCount_Chart(paramMap);
            }
            
            //全站访客数
            Map<String,Object> visitorCountMap = list2Map(visitorPvCountList,"CREATE_DATE","VISITOR_COUNT");
            //全站浏览量
            Map<String,Object> pvCountMap = list2Map(visitorPvCountList,"CREATE_DATE","PV_COUNT");
            
            //开始数据拼装
          	String key="";
          	
          	Map<String, Object> map = null;
            for(String time : time_list) {
            	key = time;
            	/** 流量趋势-全站访客数 */
                float visitorCount = visitorCountMap.get(key)==null?0:Float.parseFloat(visitorCountMap.get(key).toString());
                /** 流量趋势-全站浏览量 */
                float pvCount = pvCountMap.get(key)==null?0:Float.parseFloat(pvCountMap.get(key).toString());
                
                map = new HashMap<String, Object>();
                //全站访客数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", visitorCount);
              	map.put("PAGE_NAME", "全站访客数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//全站浏览量
              	map.put("CREATE_DATE", key);
              	map.put("CNT", pvCount);
              	map.put("PAGE_NAME", "全站浏览量");
              	allDataList.add(map);
            }
            
            //将原始数据组装为Echart可识别的格式
            resultMap = createData("PAGE_NAME", time_list, allDataList);
            
            pr.setState(true);
			pr.setMessage("获取流量趋势折线成功");
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
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            paramMap.put("num", 10);
            //商品访客数
			String file_names_vc = ":P_VISITOR_COUNT:";
			//商品浏览量
			String file_names_pc = ":P_PV_COUNT:";
			//支付买家数
			String file_names_pn = ":P_PURCHASE_NUMBER:";
			//支付转化率
			String file_names_zhl = ":P_ZHL:";
			List<Map<String, Object>> list=null;
			
			//需要查询的商品列表
			List<String> productList = new ArrayList<String>();
			if((paramMap.containsKey("sort")&&!"".equals(paramMap.get("sort").toString()))
					&&(paramMap.containsKey("sort_by")&&!"".equals(paramMap.get("sort_by").toString()))){
				String sort = ":"+paramMap.get("sort").toString()+":";
				if(file_names_vc.indexOf(sort)!=-1) {
					//商品访客数---获取排序后的商品排行
					productList=flowAnalysisDao.r_queryProductRank_ProductVisitorCount_Product(paramMap);
				}else if(file_names_pc.indexOf(sort)!=-1) {
					//商品浏览量---获取排序后的商品排行
					productList=flowAnalysisDao.r_queryProductRank_ProductPvCount_Product(paramMap);
				}else if(file_names_pn.indexOf(sort)!=-1) {
					//支付买家数---获取排序后的商品排行
					productList=flowAnalysisDao.r_queryProductRank_PayPurchaseNumber_Product(paramMap);
				}else if(file_names_zhl.indexOf(sort)!=-1) {
					//支付转化率---获取排序后的商品排行
					productList=flowAnalysisDao.r_queryProductRank_PayZhl_Product(paramMap);
				}else {
					pr.setState(false);
					pr.setMessage("未配置的排序字段，请联系管理员【"+paramMap.get("sort").toString()+"】");
					return pr;
				}
			}else{
				//查询默认排序的商品信息(默认抓取浏览量排名前十的商品)
				productList=flowAnalysisDao.r_queryProductRankListBy_Default(paramMap);
			}
			
			if(!productList.isEmpty()&&productList.size()>0){
				paramMap.put("productList", productList);
				list = flowAnalysisDao.r_queryProductList(paramMap);
			}
			
			if (list != null && list.size() > 0) {
	            /** 商品访客总数 */
	            long productVisitorCountTotal = flowAnalysisDao.r_queryProductVisitorCountTotal(paramMap);
				//数据获取-商品访客数,商品浏览量
				List<Map<String,Object>> visitorPvCountList = flowAnalysisDao.r_queryProductVisitorPvCount(paramMap);
				Map<String,Object> visitorCountMap = list2Map(visitorPvCountList,"ITEMNUMBER","VISITOR_COUNT");
				Map<String,Object> pvCountMap = list2Map(visitorPvCountList,"ITEMNUMBER","PV_COUNT");
				//数据获取-支付买家数
				List<Map<String,Object>> payPurchaseNumberList = flowAnalysisDao.r_queryProduct_PayPurchaseNumber(paramMap);
				Map<String,Object> payPurchaseNumberMap = list2Map(payPurchaseNumberList,"ITEMNUMBER","PURCHASE_NUMBER");
				
				Map<String,Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String itemnumber = tempMap.get("ITEMNUMBER").toString();
					float productVisitorCount = visitorCountMap.get(itemnumber)==null?0:Float.parseFloat(visitorCountMap.get(itemnumber).toString());
					float payPurchaseNumber = payPurchaseNumberMap.get(itemnumber)==null?0:Float.parseFloat(payPurchaseNumberMap.get(itemnumber).toString());
					tempMap.put("P_VISITOR_COUNT",productVisitorCount);
					tempMap.put("P_PV_COUNT",pvCountMap.get(itemnumber)==null?0:Float.parseFloat(pvCountMap.get(itemnumber).toString()));
					if(payPurchaseNumber == 0 || productVisitorCount == 0) {
						tempMap.put("P_ZHL", 0);
					}else {
						tempMap.put("P_ZHL", m2((payPurchaseNumber/productVisitorCount)*100));//支付转化率
					}
					tempMap.put("P_PURCHASE_NUMBER",payPurchaseNumber);
					if(productVisitorCount == 0 || productVisitorCountTotal == 0) {
						tempMap.put("ratio", 0);
					}else {
						tempMap.put("ratio", m2((productVisitorCount/productVisitorCountTotal)*100));//占比
					}
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
	 * 流量分布排行
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryFlowDistributionRank(HttpServletRequest request) {
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
            paramMap.put("num", 10);
            /** 全站访客总数 */
            long visitorCountTotal = flowAnalysisDao.r_queryAreaVisitorCountTotal(paramMap);
            /** 全站访客数 排行榜 */
            List<Map<String, Object>> list = flowAnalysisDao.r_queryVisitorCountCity_Rank(paramMap);
            if (list != null && list.size() > 0) {
            	for(Map<String, Object> map : list) {
            		float visitorCount = map.get("VISITOR_COUNT")==null?0:Float.parseFloat(map.get("VISITOR_COUNT").toString());
            		if(visitorCount == 0 || visitorCountTotal == 0) {
            			map.put("RATIO", 0);
            		}else {
            			map.put("RATIO", m2((visitorCount/visitorCountTotal)*100));//占比
            		}
            	}
            	pr.setState(true);
    			pr.setMessage("获取流量分布排行成功");
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
	 * 流量分布图
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryFlowDistributionMap(HttpServletRequest request) {
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
            paramMap.put("num", 10);
            //查询访客数排名前十的城市的所属省份
            List<Map<String, Object>> resultList = flowAnalysisDao.r_queryFlowDistributionMap(paramMap);
            
            pr.setState(true);
			pr.setMessage("获取流量分布图成功");
			pr.setObj(resultList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	
	/**
	 * 查询系统页和自定义页
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryPageList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
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
            //全站访客数
			String file_names_vc = ":VISITOR_COUNT:";
			//全站浏览量
			String file_names_pc = ":PV_COUNT:";
			//下单买家数
			String file_names_opn = ":O_PURCHASE_NUMBER:";
			//下单转化率
			String file_names_ozhl = ":O_ZHL:";
			//支付买家数
			String file_names_ppn = ":P_PURCHASE_NUMBER:";
			//支付转化率
			String file_names_pzhl = ":P_ZHL:";
			List<Map<String, Object>> list=null;
			
			//需要查询的页面列表
			List<String> pageList = new ArrayList<String>();
			if((paramMap.containsKey("sort")&&!"".equals(paramMap.get("sort").toString()))
					&&(paramMap.containsKey("sort_by")&&!"".equals(paramMap.get("sort_by").toString()))){
				String sort = ":"+paramMap.get("sort").toString()+":";
				if(file_names_vc.indexOf(sort)!=-1) {
					//全站访客数---获取排序后的频道页
					pageList=flowAnalysisDao.r_queryPage_VisitorCount_Page(paramMap);
				}else if(file_names_pc.indexOf(sort)!=-1) {
					//全站浏览量---获取排序后的频道页
					pageList=flowAnalysisDao.r_queryPage_PvCount_Page(paramMap);
				}else if(file_names_opn.indexOf(sort)!=-1) {
					//引导下单买家数---获取排序后的频道页
					pageList=flowAnalysisDao.r_queryPage_PurchaseNumber_Page(paramMap);
				}else if(file_names_ozhl.indexOf(sort)!=-1) {
					//引导下单转化率---获取排序后的频道页
					pageList=flowAnalysisDao.r_queryPage_Zhl_Page(paramMap);
				}else if(file_names_ppn.indexOf(sort)!=-1) {
					//引导支付买家数---获取排序后的频道页
					pageList=flowAnalysisDao.r_queryPage_PayPurchaseNumber_Page(paramMap);
				}else if(file_names_pzhl.indexOf(sort)!=-1) {
					//引导支付转化率---获取排序后的频道页
					pageList=flowAnalysisDao.r_queryPage_PayZhl_Page(paramMap);
				}else {
					pr.setState(false);
					pr.setMessage("未配置的排序字段，请联系管理员【"+paramMap.get("sort").toString()+"】");
					return pr;
				}
			}else{
				//查询默认排序的频道页
				pageList=flowAnalysisDao.r_queryPageListBy_Default(paramMap);
			}
			
			if(!pageList.isEmpty()&&pageList.size()>0){
				paramMap.put("pageList", pageList);
				list = flowAnalysisDao.r_queryPageList(paramMap);
			}
            
            if(list !=null && list.size() > 0) {
            	/** 页面列表-全站访客数，全站浏览量 */
            	List<Map<String, Object>> visitorPvCountList = flowAnalysisDao.r_queryPage_VisitorPvCount(paramMap);
                Map<String, Object> visitorCountMap = list2Map(visitorPvCountList, "CHANNEL_NAME", "VISITOR_COUNT");
                Map<String, Object> pvCountMap = list2Map(visitorPvCountList, "CHANNEL_NAME", "PV_COUNT");
            	/** 页面列表-引导下单买家数 */
            	List<Map<String, Object>> purchaseNumberList = flowAnalysisDao.r_queryPage_PurchaseNumber(paramMap);
                Map<String, Object> purchaseNumberMap = list2Map(purchaseNumberList, "CHANNEL_NAME", "PURCHASE_NUMBER");
                /** 页面列表-引导支付买家数 */
            	List<Map<String, Object>> payPurchaseNumberList = flowAnalysisDao.r_queryPage_PayPurchaseNumber(paramMap);
                Map<String, Object> payPurchaseNumberMap = list2Map(payPurchaseNumberList, "CHANNEL_NAME", "PURCHASE_NUMBER");
            	
            	Map<String, Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String channel_name = tempMap.get("CHANNEL_NAME").toString();
					//全站浏览量
					tempMap.put("PV_COUNT", pvCountMap.get(channel_name)==null?0:Float.parseFloat(pvCountMap.get(channel_name).toString()));
					//全站访客数
					float visitorCount = visitorCountMap.get(channel_name)==null?0:Float.parseFloat(visitorCountMap.get(channel_name).toString());
					tempMap.put("VISITOR_COUNT", visitorCount);
					//引导下单买家数
					float purchaseNumber = purchaseNumberMap.get(channel_name)==null?0:Float.parseFloat(purchaseNumberMap.get(channel_name).toString());
					tempMap.put("O_PURCHASE_NUMBER",purchaseNumber);
					//引导下单转化率
					if(purchaseNumber == 0 || visitorCount == 0) {
						tempMap.put("O_ZHL", "0.00%");
					}else {
						tempMap.put("O_ZHL", m2((purchaseNumber/visitorCount)*100)+"%");
					}
					//引导支付买家数
					float payPurchaseNumber = payPurchaseNumberMap.get(channel_name)==null?0:Float.parseFloat(payPurchaseNumberMap.get(channel_name).toString());
					tempMap.put("P_PURCHASE_NUMBER", payPurchaseNumber);
					//引导支付转化率
					if(payPurchaseNumber == 0 || visitorCount == 0) {
						tempMap.put("P_ZHL", "0.00%");
					}else {
						tempMap.put("P_ZHL", m2((payPurchaseNumber/visitorCount)*100)+"%");
					}
					
				}
            }
			
            pr.setState(true);
            pr.setMessage("获取页面列表成功");
            pr.setObj(list);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 页面列表-趋势分析
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryPageTrendChart(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(paramMap.get("start_date"))||StringUtils.isEmpty(paramMap.get("end_date"))||StringUtils.isEmpty(paramMap.get("channel_name"))
            		||StringUtils.isEmpty(paramMap.get("old_start_date"))||StringUtils.isEmpty(paramMap.get("old_end_date"))) {
            	 pr.setState(false);
                 pr.setMessage("缺少参数");
                 return pr;
            }
            
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            List<Map<String, Object>> allDataList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> visitorPvCountList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> purchaseNumberList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> payPurchaseNumberList = new ArrayList<Map<String, Object>>();
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
            	/** 趋势分析 折线图 全站访客数，全站浏览量 */
            	visitorPvCountList = flowAnalysisDao.r_queryVisitorPvCountD_Chart(paramMap);
            	/** 趋势分析 折线图 下单买家数 */
                purchaseNumberList = flowAnalysisDao.r_queryPageTrend_PurchaseNumberD_Chart(paramMap);
                /** 趋势分析 折线图 支付买家数 */
                payPurchaseNumberList = flowAnalysisDao.r_queryPageTrend_PayPurchaseNumberD_Chart(paramMap);
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
                /** 趋势分析 折线图 全站访客数，全站浏览量 */
            	visitorPvCountList = flowAnalysisDao.r_queryVisitorPvCount_Chart(paramMap);
            	/** 趋势分析 折线图 下单买家数 */
                purchaseNumberList = flowAnalysisDao.r_queryPageTrend_PurchaseNumber_Chart(paramMap);
                /** 趋势分析 折线图 支付买家数 */
                payPurchaseNumberList = flowAnalysisDao.r_queryPageTrend_PayPurchaseNumber_Chart(paramMap);
            }
            
            //全站访客数
            Map<String,Object> visitorCountMap = list2Map(visitorPvCountList,"CREATE_DATE","VISITOR_COUNT");
            //全站浏览量
            Map<String,Object> pvCountMap = list2Map(visitorPvCountList,"CREATE_DATE","PV_COUNT");
            //引导下单买家数
            Map<String,Object> purchaseNumberMap = list2Map(purchaseNumberList,"CREATE_DATE","PURCHASE_NUMBER");
            //引导支付买家数
            Map<String,Object> payPurchaseNumberMap = list2Map(payPurchaseNumberList,"CREATE_DATE","PURCHASE_NUMBER");
            //开始数据拼装
          	String key="";
          	
          	Map<String, Object> map = null;
            for(String time : time_list) {
            	key = time;
            	/** 趋势分析-全站访客数 */
                float visitorCount = visitorCountMap.get(key)==null?0:Float.parseFloat(visitorCountMap.get(key).toString());
                /** 趋势分析-全站浏览量 */
                float pvCount = pvCountMap.get(key)==null?0:Float.parseFloat(pvCountMap.get(key).toString());
                /** 趋势分析 引导下单买家数 */
                float purchaseNumber = purchaseNumberMap.get(key)==null?0:Float.parseFloat(purchaseNumberMap.get(key).toString());
                /** 趋势分析 引导支付买家数 */
                float payPurchaseNumber = payPurchaseNumberMap.get(key)==null?0:Float.parseFloat(payPurchaseNumberMap.get(key).toString());
                map = new HashMap<String, Object>();
                //全站访客数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", visitorCount);
              	map.put("PAGE_NAME", "全站访客数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//全站浏览量
              	map.put("CREATE_DATE", key);
              	map.put("CNT", pvCount);
              	map.put("PAGE_NAME", "全站浏览量");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//引导下单买家数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", purchaseNumber);
              	map.put("PAGE_NAME", "引导下单买家数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
                //引导下单转化率
              	if(visitorCount==0||purchaseNumber==0){
              		map.put("CNT", 0);
              	}else{
              		float zhl = 100*(purchaseNumber/visitorCount);
              		map.put("CNT", m2(zhl));
              	}
              	map.put("CREATE_DATE", key);
              	map.put("PAGE_NAME", "引导下单转化率");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//引导支付买家数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", payPurchaseNumber);
              	map.put("PAGE_NAME", "引导支付买家数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
                //引导支付转化率
              	if(visitorCount==0||payPurchaseNumber==0){
              		map.put("CNT", 0);
              	}else{
              		float zhl = 100*(payPurchaseNumber/visitorCount);
              		map.put("CNT", m2(zhl));
              	}
              	map.put("CREATE_DATE", key);
              	map.put("PAGE_NAME", "引导支付转化率");
              	allDataList.add(map);
            }
            
            //将原始数据组装为Echart可识别的格式
            resultMap = createData("PAGE_NAME", time_list, allDataList);
            
            pr.setState(true);
			pr.setMessage("获取趋势分析折线成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 商品列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryProductList(HttpServletRequest request) {
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
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
			int total = flowAnalysisDao.r_queryProductCount(paramMap);
            //商品访客数
			String file_names_vc = ":P_VISITOR_COUNT:";
			//商品浏览量
			String file_names_pc = ":P_PV_COUNT:";
			//支付买家数
			String file_names_opn = ":O_PURCHASE_NUMBER:";
			//支付转化率
			String file_names_ozhl = ":O_ZHL:";
			//支付买家数
			String file_names_ppn = ":P_PURCHASE_NUMBER:";
			//支付转化率
			String file_names_pzhl = ":P_ZHL:";
			List<Map<String, Object>> list=null;
			
			//需要查询的商品列表
			List<String> productList = new ArrayList<String>();
			if((paramMap.containsKey("sort")&&!"".equals(paramMap.get("sort").toString()))
					&&(paramMap.containsKey("sort_by")&&!"".equals(paramMap.get("sort_by").toString()))){
				String sort = ":"+paramMap.get("sort").toString()+":";
				if(file_names_vc.indexOf(sort)!=-1) {
					//商品访客数---获取排序后的商品信息
					productList=flowAnalysisDao.r_queryProductVisitorCount_Product(paramMap);
				}else if(file_names_pc.indexOf(sort)!=-1) {
					//商品浏览量---获取排序后的商品信息
					productList=flowAnalysisDao.r_queryProductPvCount_Product(paramMap);
				}else if(file_names_opn.indexOf(sort)!=-1) {
					//下单买家数---获取排序后的商品信息
					productList=flowAnalysisDao.r_queryProduct_PurchaseNumber_Product(paramMap);
				}else if(file_names_ozhl.indexOf(sort)!=-1) {
					//下单转化率---获取排序后的商品信息
					productList=flowAnalysisDao.r_queryProduct_Zhl_Product(paramMap);
				}else if(file_names_ppn.indexOf(sort)!=-1) {
					//支付买家数---获取排序后的商品信息
					productList=flowAnalysisDao.r_queryProduct_PayPurchaseNumber_Product(paramMap);
				}else if(file_names_pzhl.indexOf(sort)!=-1) {
					//支付转化率---获取排序后的商品信息
					productList=flowAnalysisDao.r_queryProduct_PayZhl_Product(paramMap);
				}else {
					gr.setState(false);
					gr.setMessage("未配置的排序字段，请联系管理员【"+paramMap.get("sort").toString()+"】");
					return gr;
				}
			}else{
				//查询默认排序的商品信息
				productList=flowAnalysisDao.r_queryProductListBy_Default(paramMap);
			}
			
			if(!productList.isEmpty()&&productList.size()>0){
				paramMap.put("productList", productList);
				list = flowAnalysisDao.r_queryProductList(paramMap);
			}
			
			if (list != null && list.size() > 0) {
				//数据获取-商品访客数,商品浏览量
				List<Map<String,Object>> productVisitorPvCountList = flowAnalysisDao.r_queryProductVisitorPvCount(paramMap);
				Map<String,Object> productVisitorCountMap = list2Map(productVisitorPvCountList,"ITEMNUMBER","VISITOR_COUNT");
				Map<String,Object> productPvCountMap = list2Map(productVisitorPvCountList,"ITEMNUMBER","PV_COUNT");
				//数据获取-下单买家数
				List<Map<String,Object>> purchaseNumberList = flowAnalysisDao.r_queryProduct_PurchaseNumber(paramMap);
				Map<String,Object> purchaseNumberMap = list2Map(purchaseNumberList,"ITEMNUMBER","PURCHASE_NUMBER");
				//数据获取-支付买家数
				List<Map<String,Object>> payPurchaseNumberList = flowAnalysisDao.r_queryProduct_PayPurchaseNumber(paramMap);
				Map<String,Object> payPurchaseNumberMap = list2Map(payPurchaseNumberList,"ITEMNUMBER","PURCHASE_NUMBER");
				
				Map<String,Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String itemnumber = tempMap.get("ITEMNUMBER").toString();
					float productVisitorCount = productVisitorCountMap.get(itemnumber)==null?0:Float.parseFloat(productVisitorCountMap.get(itemnumber).toString());
					tempMap.put("P_PV_COUNT",productPvCountMap.get(itemnumber)==null?0:Float.parseFloat(productPvCountMap.get(itemnumber).toString()));
					tempMap.put("P_VISITOR_COUNT",productVisitorCount);
					float purchaseNumber = purchaseNumberMap.get(itemnumber)==null?0:Float.parseFloat(purchaseNumberMap.get(itemnumber).toString());
					tempMap.put("O_PURCHASE_NUMBER",purchaseNumber);
					//下单转化率
					if(purchaseNumber == 0 || productVisitorCount == 0) {
						tempMap.put("O_ZHL", "0.00%");
					}else {
						tempMap.put("O_ZHL", m2((purchaseNumber/productVisitorCount)*100)+"%");
					}
					float payPurchaseNumber = payPurchaseNumberMap.get(itemnumber)==null?0:Float.parseFloat(payPurchaseNumberMap.get(itemnumber).toString());
					tempMap.put("P_PURCHASE_NUMBER",payPurchaseNumber);
					//支付转化率
					if(payPurchaseNumber == 0 || productVisitorCount == 0) {
						tempMap.put("P_ZHL", "0.00%");
					}else {
						tempMap.put("P_ZHL", m2((payPurchaseNumber/productVisitorCount)*100)+"%");
					}
				}
				gr.setState(true);
				gr.setMessage("获取商品列表成功");
				gr.setObj(list);
				gr.setTotal(total);
			}else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.error(e);
        }
        return gr;
	}
	/**
	 * 商品列表-趋势分析
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductTrendList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(paramMap.get("start_date"))||StringUtils.isEmpty(paramMap.get("end_date"))||StringUtils.isEmpty(paramMap.get("itemnumber"))
            		||StringUtils.isEmpty(paramMap.get("old_start_date"))||StringUtils.isEmpty(paramMap.get("old_end_date"))) {
            	 pr.setState(false);
                 pr.setMessage("缺少参数");
                 return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            List<Map<String, Object>> allDataList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> productVisitorPvCountList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> purchaseNumberList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> payPurchaseNumberList = new ArrayList<Map<String, Object>>();
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
            	/** 趋势分析 折线图 商品访客数，商品浏览量 */
            	productVisitorPvCountList = flowAnalysisDao.r_queryProductVisitorPvCountD_Chart(paramMap);
            	/** 趋势分析 折线图 下单买家数 */
                purchaseNumberList = flowAnalysisDao.r_queryPurchaseNumberD_Chart(paramMap);
                /** 趋势分析 折线图 支付买家数 */
                payPurchaseNumberList = flowAnalysisDao.r_queryPayPurchaseNumberD_Chart(paramMap);
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
                /** 趋势分析 折线图 商品访客数，商品浏览量 */
                productVisitorPvCountList = flowAnalysisDao.r_queryProductVisitorPvCount_Chart(paramMap);
            	/** 趋势分析 折线图 下单买家数 */
                purchaseNumberList = flowAnalysisDao.r_queryPurchaseNumber_Chart(paramMap);
                /** 趋势分析 折线图 支付买家数 */
                payPurchaseNumberList = flowAnalysisDao.r_queryPayPurchaseNumber_Chart(paramMap);
            }
            
            //商品访客数
            Map<String,Object> productVisitorCountMap = list2Map(productVisitorPvCountList,"CREATE_DATE","VISITOR_COUNT");
            //商品浏览量
            Map<String,Object> productPvCountMap = list2Map(productVisitorPvCountList,"CREATE_DATE","PV_COUNT");
            //引导下单买家数
            Map<String,Object> purchaseNumberMap = list2Map(purchaseNumberList,"CREATE_DATE","PURCHASE_NUMBER");
            //引导支付买家数
            Map<String,Object> payPurchaseNumberMap = list2Map(payPurchaseNumberList,"CREATE_DATE","PURCHASE_NUMBER");
            //开始数据拼装
          	String key="";
          	
          	Map<String, Object> map = null;
            for(String time : time_list) {
            	key = time;
            	/** 趋势分析-商品访客数 */
                float productVisitorCount = productVisitorCountMap.get(key)==null?0:Float.parseFloat(productVisitorCountMap.get(key).toString());
                /** 趋势分析-商品浏览量 */
                float productPvCount = productPvCountMap.get(key)==null?0:Float.parseFloat(productPvCountMap.get(key).toString());
                /** 趋势分析 下单买家数 */
                float purchaseNumber = purchaseNumberMap.get(key)==null?0:Float.parseFloat(purchaseNumberMap.get(key).toString());
                /** 趋势分析 支付买家数 */
                float payPurchaseNumber = payPurchaseNumberMap.get(key)==null?0:Float.parseFloat(payPurchaseNumberMap.get(key).toString());
                map = new HashMap<String, Object>();
                //商品访客数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", productVisitorCount);
              	map.put("PAGE_NAME", "商品访客数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//商品浏览量
              	map.put("CREATE_DATE", key);
              	map.put("CNT", productPvCount);
              	map.put("PAGE_NAME", "商品浏览量");
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
            }
            
            //将原始数据组装为Echart可识别的格式
            resultMap = createData("PAGE_NAME", time_list, allDataList);
            
            pr.setState(true);
			pr.setMessage("获取趋势分析折线成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	
	/**
	 * 访客分析-时段分布折线图
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryTimeChart(HttpServletRequest request) {
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
            List<Map<String, Object>> visitorCountList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> purchaseNumberList = new ArrayList<Map<String, Object>>();
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
            	/** 访客分析 折线图 全站访客数 */
            	visitorCountList = flowAnalysisDao.r_queryVisitorPvCountD_Chart(paramMap);
                /** 访客分析 折线图 下单买家数 */
            	purchaseNumberList = flowAnalysisDao.r_queryPurchaseNumberD_Chart(paramMap);
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
                /** 访客分析 折线图 全站访客数 */
                visitorCountList = flowAnalysisDao.r_queryVisitorPvCount_Chart(paramMap);
                /** 访客分析 折线图 下单买家数 */
                purchaseNumberList = flowAnalysisDao.r_queryPurchaseNumber_Chart(paramMap);
            }
            
            //全站访客数
            Map<String,Object> visitorCountMap = list2Map(visitorCountList,"CREATE_DATE","VISITOR_COUNT");
            //下单买家数
            Map<String,Object> purchaseNumberMap = list2Map(purchaseNumberList,"CREATE_DATE","PURCHASE_NUMBER");
            
            //开始数据拼装
          	String key="";
          	
          	Map<String, Object> map = null;
            for(String time : time_list) {
            	key = time;
            	/** 访客分析-全站访客数 */
                float visitorCount = visitorCountMap.get(key)==null?0:Float.parseFloat(visitorCountMap.get(key).toString());
                /** 访客分析-下单买家数 */
                float purchaseNumber = purchaseNumberMap.get(key)==null?0:Float.parseFloat(purchaseNumberMap.get(key).toString());
                
                map = new HashMap<String, Object>();
                //全站访客数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", visitorCount);
              	map.put("PAGE_NAME", "全站访客数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//下单买家数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", purchaseNumber);
              	map.put("PAGE_NAME", "下单买家数");
              	allDataList.add(map);
            }
            
            //将原始数据组装为Echart可识别的格式
            resultMap = createData("PAGE_NAME", time_list, allDataList);
            
            pr.setState(true);
			pr.setMessage("获取时段分布折线成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 访客分析-区域分布排行
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAreaRank(HttpServletRequest request) {
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
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            paramMap.put("num", 10);
            List<Map<String, Object>> provinceList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> cityList = new ArrayList<Map<String, Object>>();
            long count = 0;
            float visitorCountTotal = 0;
        	if("2".equals(paramMap.get("type").toString())) {//下单买家数
            	/** 下单买家总数 */
                count = flowAnalysisDao.r_queryPurchaseNumberTotal(paramMap);
            	/** 省份 下单买家数 排行榜 */
                provinceList = flowAnalysisDao.r_queryPurchaseNumberProvince_Rank(paramMap);
                /** 城市 下单买家数 排行榜 */
                cityList = flowAnalysisDao.r_queryPurchaseNumberCity_Rank(paramMap);
            }else {//全站访客数
            	/** 全站访客总数 */
            	visitorCountTotal = flowAnalysisDao.r_queryVisitorCountTotal(paramMap);
            	/** 省份 全站访客数 排行榜 */
                provinceList = flowAnalysisDao.r_queryVisitorCountProvince_Rank(paramMap);
                /** 城市 全站访客数 排行榜 */
                cityList = flowAnalysisDao.r_queryVisitorCountCity_Rank(paramMap);
            }
            
            if("2".equals(paramMap.get("type").toString())) {
            	for(Map<String, Object> p : provinceList) {
                	float purchaseNumber = Float.parseFloat(p.get("PURCHASE_NUMBER").toString());
                	p.put("RATIO", m2((purchaseNumber/count)*100));
                }
            	for(Map<String, Object> c : cityList) {
                	float purchaseNumber = Float.parseFloat(c.get("PURCHASE_NUMBER").toString());
                	c.put("RATIO", m2((purchaseNumber/count)*100));
                }
            }else {
            	for(Map<String, Object> p : provinceList) {
                	float visitorCount = Float.parseFloat(p.get("VISITOR_COUNT").toString());
                	p.put("RATIO", m2((visitorCount/visitorCountTotal)*100));
                }
            	for(Map<String, Object> c : cityList) {
                	float visitorCount = Float.parseFloat(c.get("VISITOR_COUNT").toString());
                	c.put("RATIO", m2((visitorCount/visitorCountTotal)*100));
                }
            }
            resultMap.put("provinceList", provinceList);
            resultMap.put("cityList", cityList);
            pr.setState(true);
			pr.setMessage("获取区域分布排行成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	
	/**
	 * 访客分析-区域分布明细
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAreaDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("type"))||StringUtils.isEmpty(paramMap.get("province_id"))) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            paramMap.put("num", 10);
            List<Map<String, Object>> resultList = flowAnalysisDao.r_queryAreaDetail(paramMap);
            pr.setState(true);
			pr.setMessage("获取区域分布明细成功");
			pr.setObj(resultList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 访客分析-区域分布地图
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAreaMap(HttpServletRequest request) {
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
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            List<Map<String, Object>> resultList = flowAnalysisDao.r_queryAreaMap(paramMap);
            pr.setState(true);
			pr.setMessage("获取区域分布地图成功");
			pr.setObj(resultList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 访问设备
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryFacilityChart(HttpServletRequest request) {
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
            /** 访问设备 */
            List<Map<String, Object>> resultList = flowAnalysisDao.r_queryFacility_Chart(paramMap);
            
            pr.setState(true);
			pr.setMessage("获取访问设备成功");
			pr.setObj(resultList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 移动端分布
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryMobileTerminalChart(HttpServletRequest request) {
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
            /** 移动端分布 */
            List<Map<String, Object>> resultList = flowAnalysisDao.r_queryMobileTerminalChart(paramMap);
            
            pr.setState(true);
			pr.setMessage("获取移动端分布成功");
			pr.setObj(resultList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	
	/**
	 * 手机品牌 折线
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryMobileBrandChart(HttpServletRequest request) {
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
            /** 手机品牌 */
            List<Map<String, Object>> resultList = flowAnalysisDao.r_queryMobileBrandChart(paramMap);
            
            pr.setState(true);
			pr.setMessage("获取手机品牌折线成功");
			pr.setObj(resultList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 手机品牌列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryMobileBrandList(HttpServletRequest request) {
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
			int total = flowAnalysisDao.r_queryMobileBrandCount(paramMap);
            //访问人数
			String file_names_vc = ":VISITOR_COUNT:";
			//访问次数
			String file_names_pc = ":PV_COUNT:";
			//每次平均停留时长
			String file_names_ta = ":TIME_AVG:";
			List<Map<String, Object>> list=null;
			
			//需要查询的手机品牌列表
			List<String> mobileBrandList = new ArrayList<String>();
			if((paramMap.containsKey("sort")&&!"".equals(paramMap.get("sort").toString()))
					&&(paramMap.containsKey("sort_by")&&!"".equals(paramMap.get("sort_by").toString()))){
				String sort = ":"+paramMap.get("sort").toString()+":";
				if(file_names_vc.indexOf(sort)!=-1) {
					//访问人数---获取排序后的手机品牌
					mobileBrandList=flowAnalysisDao.r_queryMobileBrand_VisitorCount(paramMap);
				}else if(file_names_pc.indexOf(sort)!=-1) {
					//访问次数---获取排序后的手机品牌
					mobileBrandList=flowAnalysisDao.r_queryMobileBrand_PvCount(paramMap);
				}else if(file_names_ta.indexOf(sort)!=-1) {
					//每次平均停留时长---获取排序后的手机品牌
					mobileBrandList=flowAnalysisDao.r_queryMobileBrand_TimeAvg(paramMap);
				}else {
					gr.setState(false);
					gr.setMessage("未配置的排序字段，请联系管理员【"+paramMap.get("sort").toString()+"】");
					return gr;
				}
			}else{
				//查询默认排序的手机品牌
				mobileBrandList=flowAnalysisDao.r_queryMobileBrandListBy_Default(paramMap);
			}
			
			if(!mobileBrandList.isEmpty()&&mobileBrandList.size()>0){
				paramMap.put("mobileBrandList", mobileBrandList);
				list = flowAnalysisDao.r_queryMobileBrandList(paramMap);
			}
			
			if (list != null && list.size() > 0) {
				//访问人数，访问次数，页面停留时长
				List<Map<String,Object>> logInfoList = flowAnalysisDao.r_queryMobileBrand_LogInfo(paramMap);
				Map<String,Object> visitorCountMap = list2Map(logInfoList,"ACCESS_DEVICE_BRAND","VISITOR_COUNT");
				Map<String,Object> pvCountMap = list2Map(logInfoList,"ACCESS_DEVICE_BRAND","PV_COUNT");
				Map<String,Object> timeOnPageMap = list2Map(logInfoList,"ACCESS_DEVICE_BRAND","TIME_ON_PAGE");
				
				Map<String,Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String access_device_brand = tempMap.get("ACCESS_DEVICE_BRAND").toString();
					float pvCount = pvCountMap.get(access_device_brand)==null?0:Float.parseFloat(pvCountMap.get(access_device_brand).toString());
					tempMap.put("PV_COUNT",pvCount);
					tempMap.put("VISITOR_COUNT",visitorCountMap.get(access_device_brand)==null?0:Float.parseFloat(visitorCountMap.get(access_device_brand).toString()));
					float timeOnPage = timeOnPageMap.get(access_device_brand)==null?0:Float.parseFloat(timeOnPageMap.get(access_device_brand).toString());
					
					//每次平均停留时长
					if(timeOnPage == 0 || pvCount == 0) {
						tempMap.put("TIME_AVG", "--");
					}else {
						long timeAvg = (long) (timeOnPage/pvCount);
						tempMap.put("TIME_AVG", getTime(timeAvg));
					}
				}
				gr.setState(true);
				gr.setMessage("获取手机品牌列表成功");
				gr.setObj(list);
				gr.setTotal(total);
			}else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.error(e);
        }
        return gr;
	}
	/**
	 * 机型分析
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryMobileModelList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("access_device_brand"))) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            //访问人数
			String file_names_vc = ":VISITOR_COUNT:";
			//访问次数
			String file_names_pc = ":PV_COUNT:";
			//每次平均停留时长
			String file_names_ta = ":TIME_AVG:";
			List<Map<String, Object>> list=null;
			
			//需要查询的手机型号列表
			List<String> mobileModelList = new ArrayList<String>();
			if((paramMap.containsKey("sort")&&!"".equals(paramMap.get("sort").toString()))
					&&(paramMap.containsKey("sort_by")&&!"".equals(paramMap.get("sort_by").toString()))){
				String sort = ":"+paramMap.get("sort").toString()+":";
				if(file_names_vc.indexOf(sort)!=-1) {
					//访问人数---获取排序后的手机型号
					mobileModelList=flowAnalysisDao.r_queryMobileModel_VisitorCount(paramMap);
				}else if(file_names_pc.indexOf(sort)!=-1) {
					//访问次数---获取排序后的手机型号
					mobileModelList=flowAnalysisDao.r_queryMobileModel_PvCount(paramMap);
				}else if(file_names_ta.indexOf(sort)!=-1) {
					//每次平均停留时长---获取排序后的手机型号
					mobileModelList=flowAnalysisDao.r_queryMobileModel_TimeAvg(paramMap);
				}else {
					pr.setState(false);
					pr.setMessage("未配置的排序字段，请联系管理员【"+paramMap.get("sort").toString()+"】");
					return pr;
				}
			}else{
				//查询默认排序的手机型号
				mobileModelList=flowAnalysisDao.r_queryMobileModelListBy_Default(paramMap);
			}
			
			if(!mobileModelList.isEmpty()&&mobileModelList.size()>0){
				paramMap.put("mobileModelList", mobileModelList);
				list = flowAnalysisDao.r_queryMobileModelList(paramMap);
			}
			
			if (list != null && list.size() > 0) {
				//访问人数，访问次数，页面停留时长
				List<Map<String,Object>> logInfoList = flowAnalysisDao.r_queryMobileModel_LogInfo(paramMap);
				Map<String,Object> visitorCountMap = list2Map(logInfoList,"ACCESS_DEVICE_MODEL","VISITOR_COUNT");
				Map<String,Object> pvCountMap = list2Map(logInfoList,"ACCESS_DEVICE_MODEL","PV_COUNT");
				Map<String,Object> timeOnPageMap = list2Map(logInfoList,"ACCESS_DEVICE_MODEL","TIME_ON_PAGE");
				
				Map<String,Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String access_device_model = tempMap.get("ACCESS_DEVICE_MODEL").toString();
					float pvCount = pvCountMap.get(access_device_model)==null?0:Float.parseFloat(pvCountMap.get(access_device_model).toString());
					tempMap.put("PV_COUNT",pvCount);
					tempMap.put("VISITOR_COUNT",visitorCountMap.get(access_device_model)==null?0:Float.parseFloat(visitorCountMap.get(access_device_model).toString()));
					float timeOnPage = timeOnPageMap.get(access_device_model)==null?0:Float.parseFloat(timeOnPageMap.get(access_device_model).toString());
					
					//每次平均停留时长
					if(timeOnPage == 0 || pvCount == 0) {
						tempMap.put("TIME_AVG", "--");
					}else {
						long timeAvg = (long) (timeOnPage/pvCount);
						tempMap.put("TIME_AVG", getTime(timeAvg));
					}
				}
				pr.setState(true);
				pr.setMessage("获取手机型号列表成功");
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
    /**
     * 时分秒格式转化
     * @param m
     * @return
     */
    public String getTime(long m) {
 
		if(m < 60) {//秒
				
			return NumFormat(0) + ":" + NumFormat(m);
		}
		
		if(m < 3600) {//分
			
			return NumFormat(m / 60) + ":" + NumFormat(m % 60);
		}
		
		if(m < 3600 * 24) {//时
			
			return NumFormat(m / 60 / 60) + ":" + NumFormat(m / 60 % 60) + ":" + NumFormat(m % 60);
		}
		
		if(m >= 3600 * 24) {//天
 
			return NumFormat(m / 60 / 60 /24) + "天" +NumFormat(m / 60 / 60 % 24) + ":" + NumFormat(m / 60 % 60) + ":" + NumFormat(m % 60);
		}
		
		return "--";
	}
	
	public  String NumFormat(long i) {
		if(String.valueOf(i).length() < 2) {
			return "0"+i;
		}else {
			return String.valueOf(i);
		}
    }
}
