package com.tk.oms.analysis.service;

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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tk.oms.analysis.dao.OperationAnalysisDao;
import com.tk.sys.util.DateUtils;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Service("OperationAnalysisService")
public class OperationAnalysisService {
	@Resource
	private OperationAnalysisDao operationAnalysisDao;
	@Resource
	@Value("${jdbc_user}")
	private String jdbc_user;
	
	private String[] colors={"#2f4554 ", "#c23531", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF", "#001cf1", "#000000", "#50d400", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF","#001cf1", "#000000", "#50d400", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF", "#001cf1", "#000000", "#50d400", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF", "#001cf1", "#000000", "#50d400", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF", "#001cf1", "#000000", "#50d400", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF", "#001cf1", "#000000", "#50d400", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF"};
	
	@SuppressWarnings("unchecked")
	public ProcessResult queryRealTime(HttpServletRequest request) {
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
            
        	List<String> time_list = new ArrayList<String>();
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
        	time_list.add("24:00");
            //查询今日实时概况
            Map<String, Object> rtMap = new HashMap<String, Object>();
            //以下内容废弃
            //rtMap.putAll(operationAnalysisDao.queryRealTimeDetailTD(paramMap));
            //rtMap.putAll(operationAnalysisDao.queryRealTimeDetailYD(paramMap));
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
            
            /**************************************************今日相关统计    begin****************************************************/
            String dateNowStr = sdf.format(new Date()); 
            paramMap.put("start_date", dateNowStr+" 00:00:00");
            paramMap.put("end_date", dateNowStr+" 23:59:59");
            //支付买家数、支付订单数
            Map<String,Object> custom_order_CountMap = operationAnalysisDao.queryRealTimeDetail_custom_order_Count(paramMap);
            //访客数,浏览量
            Map<String,Object> visitor_pv_CountMap = operationAnalysisDao.queryRealTimeDetail_visitor_pv_Count(paramMap);
            //所有支付金额 
            float allPayMoney = operationAnalysisDao.queryBrandData_AllPayMoney(paramMap);
          	//品牌统计数据-预订支付的首款 
            float preFirstMoney = operationAnalysisDao.queryBrandData_PreFirstMoney(paramMap);
          	//品牌统计数据-尾款订单的定金金额 
            float preOrderFirstMoney = operationAnalysisDao.queryBrandData_PreOrderFirstMoney(paramMap);
          	//计算支付金额    
          	float money = allPayMoney + preFirstMoney - preOrderFirstMoney;
          	
          	float zhl =0;
          	//访客数
          	float visitorCount = Float.parseFloat(visitor_pv_CountMap.get("VISITOR_COUNT").toString());
          	//订单支付客户数
          	float customCount = Float.parseFloat(custom_order_CountMap.get("CUSTOM_COUNT").toString());
          	if(visitorCount!=0){
          		//转换率  订单支付人数/访客数
          		zhl = 100*(customCount/visitorCount);
          	}
          	
          	rtMap.put("TD_PAYMENT_MONEY",money); 		//支付金额
  			rtMap.put("TD_ORDER_COUNT",custom_order_CountMap.get("ORDER_COUNT")); 			//支付订单数
			rtMap.put("TD_VISITOR_COUNT",visitorCount); 		//访客数
			rtMap.put("TD_PV",visitor_pv_CountMap.get("PV_COUNT")); 				//浏览量
			rtMap.put("TD_CUSTOM_COUNT",custom_order_CountMap.get("CUSTOM_COUNT")); 			//支付买家数
			rtMap.put("TD_ZHL",zhl);			//转换率
			/**************************************************今日相关统计     end****************************************************/
          	
          	
          	
			/**************************************************昨日相关统计     begin****************************************************/
			Calendar c = Calendar.getInstance();  
			c.add(Calendar.DATE, -1);  
			dateNowStr = sdf.format(c.getTime());  
            paramMap.put("start_date", dateNowStr+" 00:00:00");
            paramMap.put("end_date", dateNowStr+" 23:59:59");
            //支付买家数、支付订单数
            custom_order_CountMap.clear();
            custom_order_CountMap = operationAnalysisDao.queryRealTimeDetail_custom_order_Count(paramMap);
            //访客数,浏览量
            visitor_pv_CountMap.clear();
            visitor_pv_CountMap = operationAnalysisDao.queryRealTimeDetail_visitor_pv_Count(paramMap);
            //所有支付金额 
            allPayMoney = operationAnalysisDao.queryBrandData_AllPayMoney(paramMap);
          	//品牌统计数据-预订支付的首款 
            preFirstMoney = operationAnalysisDao.queryBrandData_PreFirstMoney(paramMap);
          	//品牌统计数据-尾款订单的定金金额 
            preOrderFirstMoney = operationAnalysisDao.queryBrandData_PreOrderFirstMoney(paramMap);
          	//计算支付金额    
          	money = allPayMoney + preFirstMoney - preOrderFirstMoney;
          	
          	zhl =0;
          	//访客数
          	visitorCount = Float.parseFloat(visitor_pv_CountMap.get("VISITOR_COUNT").toString());
          	//订单支付客户数
          	customCount = Float.parseFloat(custom_order_CountMap.get("CUSTOM_COUNT").toString());
          	if(visitorCount!=0){
          		//转换率  订单支付人数/访客数
          		zhl = 100*(customCount/visitorCount);
          	}
          	
          	rtMap.put("YD_PAYMENT_MONEY",money); 		//支付金额
  			rtMap.put("YD_ORDER_COUNT",custom_order_CountMap.get("ORDER_COUNT")); 			//支付订单数
			rtMap.put("YD_VISITOR_COUNT",visitorCount); 		//访客数
			rtMap.put("YD_PV",visitor_pv_CountMap.get("PV_COUNT")); 				//浏览量
			rtMap.put("YD_CUSTOM_COUNT",custom_order_CountMap.get("CUSTOM_COUNT")); 			//支付买家数
			rtMap.put("YD_ZHL",zhl);			//转换率
          	/**************************************************昨日相关统计     end****************************************************/
            
			//以下内容屏蔽
            //List<Map<String, Object>> tdList = operationAnalysisDao.queryRealTimeLineTD(paramMap);
            //List<Map<String, Object>> ydList = operationAnalysisDao.queryRealTimeLineYD(paramMap);
			
			/**************************************************今日折线图相关统计     begin****************************************************/
			List<Float> lineTd = new ArrayList<Float>();
			dateNowStr = sdf.format(new Date()); 
            paramMap.put("start_date", dateNowStr+" 00:00:00");
            paramMap.put("end_date", dateNowStr+" 23:59:59");
			/** 实时概况：所有支付金额 **/
			List<Map<String, Object>> allPayMoneyList = operationAnalysisDao.queryRealTimeDetail_AllPayMoney_Chart(paramMap);
			Map<String,Object> allPayMoneyMap = list2Map(allPayMoneyList,"TIME_DATE","CNT");
			/** 实时概况：预订支付的首款 **/
			List<Map<String, Object>> firstMoneyList = operationAnalysisDao.queryRealTimeDetail_PreFirstMoney_Chart(paramMap);
			Map<String,Object> firstMoneyMap = list2Map(firstMoneyList,"TIME_DATE","CNT");
           	/** 实时概况：尾款订单的定金金额 **/
           	List<Map<String, Object>> preOrderFirstMoneyList = operationAnalysisDao.queryRealTimeDetail_PreOrderFirstMoney_Chart(paramMap);
           	Map<String,Object> preOrderFirstMoneyMap = list2Map(preOrderFirstMoneyList,"TIME_DATE","CNT");

           	//开始数据拼装
          	String key="";
            for(int i=0;i<time_list.size();i++){
                key = time_list.get(i);
                /** 实时概况：所有支付金额 */
                allPayMoney = allPayMoneyMap.get(key)==null?0:Float.parseFloat(allPayMoneyMap.get(key).toString());
                /** 实时概况：预订支付的首款 */
                preFirstMoney = firstMoneyMap.get(key)==null?0:Float.parseFloat(firstMoneyMap.get(key).toString());
                /** 实时概况：尾款订单的定金金额 */
                preOrderFirstMoney = preOrderFirstMoneyMap.get(key)==null?0:Float.parseFloat(preOrderFirstMoneyMap.get(key).toString());

                //存储支付金额   
                money = allPayMoney + preFirstMoney - preOrderFirstMoney;
                lineTd.add(money);
			}

			/**************************************************今日折线图相关统计     end****************************************************/
			
			/**************************************************昨日折线图相关统计     begin****************************************************/
            List<Float> lineYd = new ArrayList<Float>();
			dateNowStr = sdf.format(c.getTime());  
            paramMap.put("start_date", dateNowStr+" 00:00:00");
            paramMap.put("end_date", dateNowStr+" 23:59:59");
            /** 实时概况：所有支付金额 **/
			allPayMoneyList = operationAnalysisDao.queryRealTimeDetail_AllPayMoney_Chart(paramMap);
			allPayMoneyMap = list2Map(allPayMoneyList,"TIME_DATE","CNT");
			/** 实时概况：预订支付的首款 **/
			firstMoneyList = operationAnalysisDao.queryRealTimeDetail_PreFirstMoney_Chart(paramMap);
			firstMoneyMap = list2Map(firstMoneyList,"TIME_DATE","CNT");
           	/** 实时概况：尾款订单的定金金额 **/
           	preOrderFirstMoneyList = operationAnalysisDao.queryRealTimeDetail_PreOrderFirstMoney_Chart(paramMap);
           	preOrderFirstMoneyMap = list2Map(preOrderFirstMoneyList,"TIME_DATE","CNT");

           	//开始数据拼装
            for(int i=0;i<time_list.size();i++){
                key = time_list.get(i);
                /** 实时概况：所有支付金额 */
                allPayMoney = allPayMoneyMap.get(key)==null?0:Float.parseFloat(allPayMoneyMap.get(key).toString());
                /** 实时概况：预订支付的首款 */
                preFirstMoney = firstMoneyMap.get(key)==null?0:Float.parseFloat(firstMoneyMap.get(key).toString());
                /** 实时概况：尾款订单的定金金额 */
                preOrderFirstMoney = preOrderFirstMoneyMap.get(key)==null?0:Float.parseFloat(preOrderFirstMoneyMap.get(key).toString());

                //存储支付金额   
                money = allPayMoney + preFirstMoney - preOrderFirstMoney;
                lineYd.add(money);
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
	 * 实时访客榜
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryRealTimeVisitor(HttpServletRequest request) {
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
            if("all".equals(paramMap.get("query"))){
            	paramMap.put("num", 50);
            }else{
            	paramMap.put("num", 5);
            }
        	Map<String, Object> retMap = new HashMap<String, Object>();
            List<Map<String, Object>> dataList = operationAnalysisDao.queryRealTimeVisitor(paramMap);
            retMap.put("dataList", dataList);
            pr.setState(true);
            pr.setMessage("获取数据成功!");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 商品浏览排行(移动端、PC端)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductBrowse(HttpServletRequest request) {
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
            if(StringUtils.isEmpty(paramMap.get("source_type"))){
            	pr.setState(false);
                pr.setMessage("缺少参数,source_type");
                return pr;
            }
            if("all".equals(paramMap.get("query"))){
            	paramMap.put("num", 50);
            }else{
            	paramMap.put("num", 5);
            }
            Map<String, Object> retMap = new HashMap<String, Object>();
            List<Map<String, Object>> dataList = operationAnalysisDao.queryProductBrowse(paramMap);
            retMap.put("dataList", dataList);
            pr.setState(true);
            pr.setMessage("获取数据成功!");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 销售排行(商品、地区)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySalesRank(HttpServletRequest request) {
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
            Map<String, Object> retMap = new HashMap<String, Object>();
            if("all".equals(paramMap.get("query"))){
            	paramMap.put("num", 10);
            	List<Map<String, Object>> areaProductList = operationAnalysisDao.queryAreaProductRank(paramMap);
            	retMap.put("areaProductList", areaProductList);
            	paramMap.put("num", 50);
            }else{
            	paramMap.put("num", 10);
            	List<Map<String, Object>> areaList = operationAnalysisDao.queryAreaRank(paramMap);
            	retMap.put("areaList", areaList);
            }
            List<Map<String, Object>> productList = operationAnalysisDao.queryProductRank(paramMap);
            retMap.put("productList", productList);
            pr.setState(true);
            pr.setMessage("获取数据成功!");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 滞销排行
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryUnsalableRank(HttpServletRequest request) {
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
            if("all".equals(paramMap.get("query"))){
            	paramMap.put("num", 50);
            }else{
            	paramMap.put("num", 4);
            }
            Map<String, Object> retMap = new HashMap<String, Object>();
            List<Map<String, Object>> dataList = operationAnalysisDao.queryUnsalableRank(paramMap);
            retMap.put("dataList", dataList);
            pr.setState(true);
            pr.setMessage("获取数据成功!");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 频道热度(移动端、PC端)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryChannelHeat(HttpServletRequest request) {
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
            if(StringUtils.isEmpty(paramMap.get("source_type"))){
            	pr.setState(false);
                pr.setMessage("缺少参数,source_type");
                return pr;
            }
            List<String> time_list = new ArrayList<String>();//存储所有的时间数据
            List<Map<String, Object>> dataList = null;
            if("td".equals(paramMap.get("query_type"))){
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
            	time_list.add("24:00");
            	 //type 1 浏览量 2访客数
                if("1".equals(paramMap.get("type"))){
                	dataList = operationAnalysisDao.queryChannelHeatPvTd(paramMap);
                }else{
                	dataList = operationAnalysisDao.queryChannelHeatVisitorTd(paramMap);
                }
            }else{
            	String start_time = paramMap.get("start_date").toString();		//开始时间
                String end_time = paramMap.get("end_date").toString();			//结束时间
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
                
                //type 1 浏览量 2访客数
                if("1".equals(paramMap.get("type"))){
                	dataList = operationAnalysisDao.queryChannelHeatPv(paramMap);
                }else{
                	dataList = operationAnalysisDao.queryChannelHeatVisitor(paramMap);
                }
            }
           
            //将原始数据组装为Echart可识别的格式
            Map<String, Object> resultMap = createData("PAGE_NAME", "PAGE_ID", time_list, dataList);
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
	 * 品牌统计数据-支付金额
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryBrandData_AllPayMoney(HttpServletRequest request) {
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
            /** 品牌统计数据-所有支付金额 */
            float allPayMoney = operationAnalysisDao.queryBrandData_AllPayMoney(paramMap);
          	
          	/** 品牌统计数据-预订支付的首款 */
            float preFirstMoney = operationAnalysisDao.queryBrandData_PreFirstMoney(paramMap);
          	
          	/** 品牌统计数据-尾款订单的定金金额 */
            float preOrderFirstMoney = operationAnalysisDao.queryBrandData_PreOrderFirstMoney(paramMap);
          	
          	//计算支付金额    
          	float money = allPayMoney + preFirstMoney - preOrderFirstMoney;
            pr.setState(true);
            pr.setMessage("获取总支付金额成功!");
            pr.setObj(money);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 品牌统计数据-访客数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryBrandData_VisitorCount(HttpServletRequest request) {
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
            /** 品牌统计数据-商品访客人数 */
            float visitorCount = operationAnalysisDao.queryBrandData_VisitorCount(paramMap);
            pr.setState(true);
            pr.setMessage("获取访客数成功!");
            pr.setObj(visitorCount);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 品牌统计数据-支付转换率
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryBrandData_Zhl(HttpServletRequest request) {
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
            /** 品牌统计数据-商品访客人数 */
            float visitorCount = operationAnalysisDao.queryBrandData_VisitorCount(paramMap);
          	
          	/** 品牌统计数据-购买的人数和总单数 */
          	Map<String,Object> purchaseNumberOrderCountMap = operationAnalysisDao.queryBrandData_PurchaseNumberOrderCount(paramMap);
          	
          	if(visitorCount==0){
          		pr.setObj(0);
          	}else{
          		//支付转换率
          		float zhl = 100*Float.parseFloat(purchaseNumberOrderCountMap.get("PURCHASENUMBER").toString())/visitorCount;
          		pr.setObj(zhl);
          	}
            pr.setState(true);
            pr.setMessage("获取转换率成功!");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	
	/**
	 * 品牌统计数据-客单价-总单数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryBrandData_KdjOrderCount(HttpServletRequest request) {
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
            
            /** 品牌统计数据-所有支付金额 */
            float allPayMoney = operationAnalysisDao.queryBrandData_AllPayMoney(paramMap);
            
            /** 品牌统计数据-购买的人数和总单数 */
          	Map<String,Object> purchaseNumberOrderCountMap = operationAnalysisDao.queryBrandData_PurchaseNumberOrderCount(paramMap);
          	
          	Map<String,Object> returnMap = new HashMap<String,Object>();
          	
          	if(Float.parseFloat(purchaseNumberOrderCountMap.get("ORDERCOUNT").toString())==0){
          		returnMap.put("kdj", 0);
          	}else{
          	//支付客单价
              	float kdj = allPayMoney/Float.parseFloat(purchaseNumberOrderCountMap.get("ORDERCOUNT").toString());
              	returnMap.put("kdj", kdj);
          	}
          	returnMap.putAll(purchaseNumberOrderCountMap);
          	
            pr.setState(true);
            pr.setMessage("获取客单价和订单总笔数成功!");
            pr.setObj(returnMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 品牌统计数据-成功退款金额
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryBrandData_ReturnMoney(HttpServletRequest request) {
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
            paramMap.put("jdbc_user", jdbc_user);
            /** 品牌统计数据-仅退款金额 */
            float onlyReturnMoney = operationAnalysisDao.queryBrandData_OnlyReturnMoney(paramMap);
          	
          	/** 品牌统计数据-售后退款*/
            float afterSaleReturnMoney = operationAnalysisDao.queryBrandData_AfterSaleReturnMoney(paramMap);
          	
          	//总退款
            float ztk = onlyReturnMoney + afterSaleReturnMoney;
          	
            pr.setState(true);
            pr.setMessage("获取成功退款金额成功!");
            pr.setObj(ztk);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 品牌统计数据-总成交商品数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryBrandData_SaleCount(HttpServletRequest request) {
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
            
            /** 品牌统计数据-总成交商品数*/
            float saleProductCount = operationAnalysisDao.queryBrandData_SaleCount(paramMap);
          	
            pr.setState(true);
            pr.setMessage("获取订单商品数成功!");
            pr.setObj(saleProductCount);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 品牌数据
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryBrandData(HttpServletRequest request) {
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
            
            paramMap.put("jdbc_user", jdbc_user);
            Map<String, Object> resultMap = operationAnalysisDao.queryBrandData(paramMap);
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
	 * 品牌折线数据
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryBrandDataChart(HttpServletRequest request) {
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
            
            List<String> time_list = new ArrayList<String>();//存储所有的时间数据
        	String start_time = paramMap.get("start_date").toString();		//开始时间
            String end_time = paramMap.get("end_date").toString();			//结束时间
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
            paramMap.put("jdbc_user", jdbc_user);
            /****************************以下下方法注释****王朋     改用新方法****************************/
            //List<Map<String, Object>> dataList = operationAnalysisDao.queryBrandDataChart(paramMap);
            
            
            /****************************以下为新方法*****王朋   begin  ******************************************************************************/
            List<Map<String, Object>> alldataList = new ArrayList<Map<String, Object>>();
            
            /** 品牌折线图统计数据-所有支付金额 **/
          	List<Map<String, Object>> allPayMoneyList = operationAnalysisDao.queryBrandData_AllPayMoney_Chart(paramMap);
          	Map<String,Object> allPayMoneyMap = list2Map(allPayMoneyList,"CREATE_DATE","CNT");
          	/** 品牌折线图统计数据-预订支付的首款 **/
          	List<Map<String, Object>> firstMoneyList = operationAnalysisDao.queryBrandData_PreFirstMoney_Chart(paramMap);
          	Map<String,Object> firstMoneyMap = list2Map(firstMoneyList,"CREATE_DATE","CNT");
          	/** 品牌折线图统计数据-尾款订单的定金金额 **/
          	List<Map<String, Object>> preOrderFirstMoneyList = operationAnalysisDao.queryBrandData_PreOrderFirstMoney_Chart(paramMap);
          	Map<String,Object> preOrderFirstMoneyMap = list2Map(preOrderFirstMoneyList,"CREATE_DATE","CNT");
          	/** 品牌折线图统计数据-商品访客人数 **/
          	List<Map<String, Object>> visitorCountList = operationAnalysisDao.queryBrandData_VisitorCount_Chart(paramMap);
          	Map<String,Object> visitorCountMap = list2Map(visitorCountList,"CREATE_DATE","CNT");
          	/** 品牌折线图统计数据-购买的人数和总单数 **/
          	List<Map<String, Object>> purchaseNumberOrderCountList = operationAnalysisDao.queryBrandData_PurchaseNumberOrderCount_Chart(paramMap);
          	Map<String,Object> purchaseNumberMap = list2Map(purchaseNumberOrderCountList,"CREATE_DATE","PURCHASENUMBER");
          	Map<String,Object> orderCountMap = list2Map(purchaseNumberOrderCountList,"CREATE_DATE","ORDERCOUNT");
          	/** 品牌折线图统计数据-仅退款金额 **/
          	List<Map<String, Object>> onlyReturnMoneyList = operationAnalysisDao.queryBrandData_OnlyReturnMoney_Chart(paramMap);
          	Map<String,Object> onlyReturnMoneyMap = list2Map(onlyReturnMoneyList,"CREATE_DATE","CNT");
          	/** 品牌折线图统计数据-售后退款**/
          	List<Map<String, Object>> afterSaleReturnMoneyList = operationAnalysisDao.queryBrandData_AfterSaleReturnMoney_Chart(paramMap);
          	Map<String,Object> afterSaleReturnMoneyMap = list2Map(afterSaleReturnMoneyList,"CREATE_DATE","CNT");
          	/** 品牌折线图统计数据-总成交商品数**/
          	List<Map<String, Object>> saleCountList = operationAnalysisDao.queryBrandData_SaleCount_Chart(paramMap);
          	Map<String,Object> saleCountMap = list2Map(saleCountList,"CREATE_DATE","CNT");
          	//开始数据拼装
          	String key="";
          	
          	Map<String, Object> map = null;
          	
            for(int i=0;i<time_list.size();i++){
            	key = time_list.get(i);
            	/** 品牌统计数据-所有支付金额 */
                float allPayMoney = allPayMoneyMap.get(key)==null?0:Float.parseFloat(allPayMoneyMap.get(key).toString());
              	/** 品牌统计数据-预订支付的首款 */
                float preFirstMoney = firstMoneyMap.get(key)==null?0:Float.parseFloat(firstMoneyMap.get(key).toString());
              	/** 品牌统计数据-尾款订单的定金金额 */
                float preOrderFirstMoney = preOrderFirstMoneyMap.get(key)==null?0:Float.parseFloat(preOrderFirstMoneyMap.get(key).toString());
                /** 品牌统计数据-商品访客人数 */
                float visitorCount = visitorCountMap.get(key)==null?0:Float.parseFloat(visitorCountMap.get(key).toString());
                /** 品牌统计数据-购买的人数 */
                float purchaseNumber = purchaseNumberMap.get(key)==null?0:Float.parseFloat(purchaseNumberMap.get(key).toString());
                /** 品牌统计数据-购买总单数 */
                float orderCount = orderCountMap.get(key)==null?0:Float.parseFloat(orderCountMap.get(key).toString());
                /** 品牌统计数据-仅退款金额 */
                float onlyReturnMoney = onlyReturnMoneyMap.get(key)==null?0:Float.parseFloat(onlyReturnMoneyMap.get(key).toString());
                /** 品牌统计数据-售后退款*/
                float afterSaleReturnMoney = afterSaleReturnMoneyMap.get(key)==null?0:Float.parseFloat(afterSaleReturnMoneyMap.get(key).toString());
                /** 品牌统计数据-总成交商品数*/
                float saleProductCount = saleCountMap.get(key)==null?0:Float.parseFloat(saleCountMap.get(key).toString());
                
                map = new HashMap<String, Object>();
                //存储支付金额    
              	float money = allPayMoney + preFirstMoney - preOrderFirstMoney;
              	map.put("CREATE_DATE", key);
              	map.put("CNT", money);
              	map.put("PAGE_NAME", "支付金额");
              	alldataList.add(map);
                
                
              	map = new HashMap<String, Object>();
                //存储访客数   
              	map.put("CREATE_DATE", key);
              	map.put("CNT", visitorCount);
              	map.put("PAGE_NAME", "访客数");
              	alldataList.add(map);
                
              	
              	map = new HashMap<String, Object>();
                //存储支付转化率
              	if(visitorCount==0){
              		map.put("CNT", visitorCount);
              	}else{
              		//支付转化率
              		float zhl = 100*purchaseNumber/visitorCount;
              		map.put("CNT", zhl);
              	}
              	map.put("CREATE_DATE", key);
              	map.put("PAGE_NAME", "支付转化率");
              	alldataList.add(map);
              	
              	map = new HashMap<String, Object>();
                //存储订单笔数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", orderCount);
              	map.put("PAGE_NAME", "订单笔数");
              	alldataList.add(map);
              	

              	map = new HashMap<String, Object>();
              	//存储客单价
              	if(orderCount==0){
              		map.put("CNT", orderCount);
              	}else{
              		//支付客单价
                  	float kdj = allPayMoney/orderCount;
                  	map.put("CNT", kdj);
              	}
              	map.put("CREATE_DATE", key);
              	map.put("PAGE_NAME", "客单价");
              	alldataList.add(map);
              	
              	
              	//总退款
              	map = new HashMap<String, Object>();
                float ztk = onlyReturnMoney + afterSaleReturnMoney;
                //存储总退款
              	map.put("CREATE_DATE", key);
              	map.put("CNT", ztk);
              	map.put("PAGE_NAME", "成功退款金额");
              	alldataList.add(map);
              	
              	
                
              	map = new HashMap<String, Object>();
                //存储订单商品数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", saleProductCount);
              	map.put("PAGE_NAME", "订单商品数");
              	alldataList.add(map);
            }
            /****************************以上为新方法*****王朋   end  **********************************************************************************/
            
            //将原始数据组装为Echart可识别的格式
            Map<String, Object> resultMap = createData("PAGE_NAME", null, time_list, alldataList);
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
	
	private Map<String, Object> createData(String seriesName,String idName,List<String> time_list,List<Map<String, Object>> data_list) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<Map<String, Object>> seriesData = new ArrayList<Map<String, Object>>();
        Map<String, Map<String, Object>> dataMap = new HashMap<String, Map<String, Object>>();
        Map<String, Object> dataMapVal = null;// 存储一个统计分组段的数据列表
        List<Double> dataList = null;
        Set<String> seriesNameSet = new HashSet<String>();
        if(data_list!=null && !data_list.isEmpty()){//有数据
        	if(StringUtils.isEmpty(idName)){
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
        	}else{
        		for (Map<String, Object> data : data_list) {
                    String PAGE_NAME = data.get(seriesName) == null ? null: data.get(seriesName).toString();//分组统计类型
                    String PAGE_ID = data.get(idName) == null ? null: data.get(idName).toString();//分组统计类型
                    seriesNameSet.add(PAGE_NAME);
                    String ORDER_DATE = data.get("CREATE_DATE") == null ? DateUtils.format(new Date(), "YYYY-MM-dd") : data.get("CREATE_DATE").toString();
                    double COUNT = data.get("CNT") == null ? 0 : Double.parseDouble(data.get("CNT").toString());//分组中某天的统计数量
                    if (dataMap.containsKey(PAGE_ID)) {// 已存在直接put
                        dataMapVal = dataMap.get(PAGE_ID);
                        dataMapVal.put(ORDER_DATE, COUNT);
                    } else {
                        dataMapVal = new HashMap<String, Object>();
                        dataMapVal.put(ORDER_DATE, COUNT);
                        dataMap.put(PAGE_ID, dataMapVal);
                    }
                }
                int i=0;
                for (String PAGE_ID : dataMap.keySet()) {
                    dataMapVal = dataMap.get(PAGE_ID);
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
                    String PAGE_NAME = "";
                    if("8888".equals(PAGE_ID)){
                    	PAGE_NAME = "首页(系统)";
                    }else{
                    	PAGE_NAME = operationAnalysisDao.queryPageNameByPageId(PAGE_ID);
                    }
                    seriesDataMap.put("name", PAGE_NAME);
                    seriesDataMap.put("type", "line");
                    seriesDataMap.put("yAxis", i);
                    seriesDataMap.put("color", colors[i]);
                    i++;
                    seriesDataMap.put("data", dataList);
                    seriesData.add(seriesDataMap);
                }
        		
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

}
