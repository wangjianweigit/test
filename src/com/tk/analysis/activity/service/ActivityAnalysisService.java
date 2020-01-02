package com.tk.analysis.activity.service;

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

import com.tk.analysis.activity.dao.ActivityAnalysisDao;
import com.tk.analysis.home.service.HomeAnalysisService;
import com.tk.sys.util.DateUtils;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Service("ActivityAnalysisService")
public class ActivityAnalysisService {
	
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private HomeAnalysisService homeAnalysisService;
	@Resource
	private ActivityAnalysisDao activityAnalysisDao;
	private String[] colors={"#2f4554 ", "#c23531", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF", "#001cf1", "#000000", "#50d400", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4",
            "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3"};
	
	/**
	 * 实时汇总
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryRealTime_Summary(HttpServletRequest request) {
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
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
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
            //支付买家数、活动销售总额
            Map<String,Object> purchaseNumberMoneyMap = activityAnalysisDao.r_queryRealTime_ActivityPayPurchaseNumberMoney(paramMap);
            //支付买家数
            float td_payPurchaseNumber = purchaseNumberMoneyMap.get("PURCHASE_NUMBER")==null?0:Float.parseFloat(purchaseNumberMoneyMap.get("PURCHASE_NUMBER").toString());
            //活动销售总额
            float td_payMoney = purchaseNumberMoneyMap.get("PAY_MONEY")==null?0:Float.parseFloat(purchaseNumberMoneyMap.get("PAY_MONEY").toString());
            //预定订单的定金
            float td_preFirstMoney = activityAnalysisDao.r_queryRealTime_ActivityPreFirstMoney(paramMap);
            //活动数
            float td_activityCount = activityAnalysisDao.r_queryRealTime_ActivityCount(paramMap);
            //活动商品数
            float td_productCount = activityAnalysisDao.r_queryRealTime_ActivityProductCount(paramMap);
            //活动商品访客数
            float td_visitorCount = activityAnalysisDao.r_queryRealTime_ActivityProductVisitorCount(paramMap);
          	//活动商品分享次数
            float td_shareCount = activityAnalysisDao.r_queryRealTime_ActivityProductShareCount(paramMap);
            td_payMoney+=td_preFirstMoney;
          
          	rtMap.put("TD_PAY_MONEY", td_payMoney); 		//活动销售总额
          	rtMap.put("TD_ACTIVITY_COUNT", td_activityCount); //活动数
          	rtMap.put("TD_PRODUCT_COUNT", td_productCount); 		//活动商品数
  			rtMap.put("TD_VISITOR_COUNT", td_visitorCount); 	//活动商品访客数
  			rtMap.put("TD_SHARE_COUNT", td_shareCount);			//活动商品分享次数
  			rtMap.put("TD_PURCHASE_NUMBER", td_payPurchaseNumber); 			//活动支付买家数
			/**************************************************今日相关统计     end****************************************************/
			
			/**************************************************昨日相关统计     begin****************************************************/
			Calendar c = Calendar.getInstance();  
			c.add(Calendar.DATE, -1);  
			dateNowStr = sdf.format(c.getTime());  
            paramMap.put("date_short", dateNowStr);
            purchaseNumberMoneyMap.clear();
            //支付买家数、活动销售总额
            purchaseNumberMoneyMap = activityAnalysisDao.r_queryRealTime_ActivityPayPurchaseNumberMoney(paramMap);
            //支付买家数
            float yd_payPurchaseNumber = purchaseNumberMoneyMap.get("PURCHASE_NUMBER")==null?0:Float.parseFloat(purchaseNumberMoneyMap.get("PURCHASE_NUMBER").toString());
            //活动销售总额
            float yd_payMoney = purchaseNumberMoneyMap.get("PAY_MONEY")==null?0:Float.parseFloat(purchaseNumberMoneyMap.get("PAY_MONEY").toString());
            //预定订单的定金
            float yd_preFirstMoney = activityAnalysisDao.r_queryRealTime_ActivityPreFirstMoney(paramMap);
            //活动数
            float yd_activityCount = activityAnalysisDao.r_queryRealTime_ActivityCount(paramMap);
            //活动商品数
            float yd_productCount = activityAnalysisDao.r_queryRealTime_ActivityProductCount(paramMap);
            //活动商品访客数
            float yd_visitorCount = activityAnalysisDao.r_queryRealTime_ActivityProductVisitorCount(paramMap);
          	//活动商品分享次数
            float yd_shareCount = activityAnalysisDao.r_queryRealTime_ActivityProductShareCount(paramMap);
            yd_payMoney+=yd_preFirstMoney;
          
          	rtMap.put("YD_PAY_MONEY", yd_payMoney); 		//活动销售总额
          	rtMap.put("YD_ACTIVITY_COUNT", yd_activityCount); //活动数
          	rtMap.put("YD_PRODUCT_COUNT", yd_productCount); 		//活动商品数
  			rtMap.put("YD_VISITOR_COUNT", yd_visitorCount); 	//活动商品访客数
  			rtMap.put("YD_SHARE_COUNT", yd_shareCount);			//活动商品分享次数
  			rtMap.put("YD_PURCHASE_NUMBER", yd_payPurchaseNumber); 			//活动支付买家数
          	/**************************************************昨日相关统计     end****************************************************/
			
            pr.setState(true);
            pr.setMessage("获取数据成功!");
            pr.setObj(rtMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.info(e);
        }
        return pr;
	}
	
	/**
	 * 实时汇总-折线
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryRealTime_SummaryChart(HttpServletRequest request) {
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
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
            String dateNowStr = sdf.format(new Date()); 
            paramMap.put("date_short", dateNowStr);
			
			/**************************************************今日折线图相关统计     begin****************************************************/
			List<Float> lineTd = new ArrayList<Float>();
			/** 折线图 活动销售额 **/
			List<Map<String, Object>> td_payMoneyList = activityAnalysisDao.r_queryRealTime_ActivityPayMoney_Chart(paramMap);
			Map<String, Object> td_payMoneyMap = list2Map(td_payMoneyList,"CREATE_DATE","PAY_MONEY");
			/** 折线图 预定订单的定金 **/
			List<Map<String, Object>> td_preFirstMoneyList = activityAnalysisDao.r_queryRealTime_ActivityPreFirstMoney_Chart(paramMap);
			Map<String, Object> td_preFirstMoneyMap = list2Map(td_preFirstMoneyList,"CREATE_DATE","PRE_FIRST_MONEY");
			
           	//开始数据拼装
          	String key="";
            for(int i=0;i<time_list.size();i++){
                key = time_list.get(i);
                //活动销售额
                float td_payMoney = td_payMoneyMap.get(key)==null?0:Float.parseFloat(td_payMoneyMap.get(key).toString());
                //预订订单的定金
                float td_preFirstMoney = td_preFirstMoneyMap.get(key)==null?0:Float.parseFloat(td_preFirstMoneyMap.get(key).toString());
                td_payMoney+=td_preFirstMoney;
                lineTd.add(td_payMoney);
			}
			/**************************************************今日折线图相关统计     end****************************************************/
          	
			Calendar c = Calendar.getInstance();  
			c.add(Calendar.DATE, -1);  
			dateNowStr = sdf.format(c.getTime());  
            paramMap.put("date_short", dateNowStr);
			
			/**************************************************昨日折线图相关统计     begin****************************************************/
			List<Float> lineYd = new ArrayList<Float>();
			/** 折线图 活动销售额 **/
			List<Map<String, Object>> yd_payMoneyList = activityAnalysisDao.r_queryRealTime_ActivityPayMoney_Chart(paramMap);
			Map<String, Object> yd_payMoneyMap = list2Map(yd_payMoneyList,"CREATE_DATE","PAY_MONEY");
			/** 折线图 预定订单的定金 **/
			List<Map<String, Object>> yd_preFirstMoneyList = activityAnalysisDao.r_queryRealTime_ActivityPreFirstMoney_Chart(paramMap);
			Map<String, Object> yd_preFirstMoneyMap = list2Map(yd_preFirstMoneyList,"CREATE_DATE","PRE_FIRST_MONEY");
			

           	//开始数据拼装
            for(int i=0;i<time_list.size();i++){
                key = time_list.get(i);
                //活动销售额
                float yd_payMoney = yd_payMoneyMap.get(key)==null?0:Float.parseFloat(yd_payMoneyMap.get(key).toString());
                //预订订单的定金
                float yd_preFirstMoney = yd_preFirstMoneyMap.get(key)==null?0:Float.parseFloat(yd_preFirstMoneyMap.get(key).toString());
                yd_payMoney+=yd_preFirstMoney;
                lineYd.add(yd_payMoney);
			}
			/**************************************************昨日折线图相关统计     end****************************************************/
			
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("time_list", time_list);
            resultMap.put("lineTd", lineTd);
            resultMap.put("lineYd", lineYd);
            pr.setState(true);
            pr.setMessage("获取数据成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.info(e);
        }
        return pr;
	}
	/**
	 * 今日商品销售额占比
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryRealTime_PayMoneyRatio(HttpServletRequest request) {
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
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            
            //今日
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String dateNowStr = sdf.format(new Date()); 
            paramMap.put("date_short", dateNowStr);
            List<Map<String, Object>> allDataList = new ArrayList<Map<String, Object>>();
            /** 活动商品销售额 */
            float acPayMoney = activityAnalysisDao.r_queryRealTime_ActivityPayMoney(paramMap);
            /** 活动商品定金 */
            float acPreFirstMoney = activityAnalysisDao.r_queryRealTime_ActivityPreFirstMoney(paramMap);
            acPayMoney+=acPreFirstMoney;
            
            /** 平台商品销售额 */
            float payMoney = activityAnalysisDao.r_queryRealTime_PayMoney(paramMap);
            /** 平台商品定金 */
            float preFirstMoney = activityAnalysisDao.r_queryRealTime_PreFirstMoney(paramMap);
            payMoney+=preFirstMoney;
            
            Map<String, Object> map = new HashMap<String, Object>();
            //活动
          	map.put("PAGE_NAME", "活动");
          	map.put("CNT", acPayMoney);
          	allDataList.add(map);
          	
          	map = new HashMap<String, Object>();
            //平台
          	map.put("PAGE_NAME", "平台");
          	map.put("CNT", payMoney);
          	allDataList.add(map);
          	
            pr.setState(true);
			pr.setMessage("获取今日商品销售额占比成功");
			pr.setObj(allDataList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.info(e);
        }
        return pr;
	}
	/**
	 * 今日支付单数占比
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryRealTime_PayNumRatio(HttpServletRequest request) {
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
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            
            //今日
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String dateNowStr = sdf.format(new Date()); 
            paramMap.put("date_short", dateNowStr);
            List<Map<String, Object>> allDataList = new ArrayList<Map<String, Object>>();
            /** 活动支付单数 */
            float acPayNum = activityAnalysisDao.r_queryRealTime_ActivityPayNum(paramMap);
            
            /** 平台支付单数 */
            float payNum = activityAnalysisDao.r_queryRealTime_PayNum(paramMap);
            
            Map<String, Object> map = new HashMap<String, Object>();
            //活动
          	map.put("PAGE_NAME", "活动");
          	map.put("CNT", acPayNum);
          	allDataList.add(map);
          	
          	map = new HashMap<String, Object>();
            //平台
          	map.put("PAGE_NAME", "平台");
          	map.put("CNT", payNum);
          	allDataList.add(map);
            
            pr.setState(true);
			pr.setMessage("获取今日支付单数占比成功");
			pr.setObj(allDataList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.info(e);
        }
        return pr;
	}
	
	/**
	 * 今日活动
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryRealTime_ActivityList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
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
            /** 限时折扣 */
            paramMap.put("activity_type", 1);
            Map<String, Object> xszkMap = new HashMap<String, Object>();
            List<Map<String, Object>> xszkList = activityAnalysisDao.r_queryRealTime_ActivityList(paramMap);
            xszkMap.put("name", "限时折扣");
            xszkMap.put("list", xszkList);
            resultMap.put("xszkMap", xszkMap);
            /** 订货会 */
            paramMap.put("activity_type", 2);
            Map<String, Object> dhhMap = new HashMap<String, Object>();
            List<Map<String, Object>> dhhList = activityAnalysisDao.r_queryRealTime_ActivityList(paramMap);
            dhhMap.put("name", "订货会");
            dhhMap.put("list", dhhList);
            resultMap.put("dhhMap", dhhMap);
            /** 预售 */
            paramMap.put("activity_type", 4);
            Map<String, Object> ysMap = new HashMap<String, Object>();
            List<Map<String, Object>> ysList = activityAnalysisDao.r_queryRealTime_ActivityList(paramMap);
            ysMap.put("name", "预售");
            ysMap.put("list", ysList);
            resultMap.put("ysMap", ysMap);
            /** 清尾 */
            paramMap.put("activity_type", 5);
            Map<String, Object> qwMap = new HashMap<String, Object>();
            List<Map<String, Object>> qwList = activityAnalysisDao.r_queryRealTime_ActivityList(paramMap);
            qwMap.put("name", "清尾");
            qwMap.put("list", qwList);
            resultMap.put("qwMap", qwMap);
            pr.setState(true);
			pr.setMessage("获取今日活动成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.info(e);
        }
        return pr;
	}
	/**
	 * 今日活动详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryRealTime_ActivityDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
        try {
            String json = HttpUtil.getRequestInputStream(request);
            paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("activity_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
        	//活动详情
            Map<String, Object> resultMap = activityAnalysisDao.r_queryActivityDetail(paramMap);
            //参与活动会员数
            float memberCount = activityAnalysisDao.r_queryActivityMemberCount(paramMap);
            resultMap.put("MEMBER_COUNT", memberCount);
            //活动商品数
            float productCount = activityAnalysisDao.r_queryActivityProductCount(paramMap);
            resultMap.put("PRODUCT_COUNT",productCount);
            //品牌数量
            float brandCount = activityAnalysisDao.r_queryActivityBrandCount(paramMap);
            resultMap.put("BRAND_COUNT", brandCount);
            //商家数量
            float stationedCount = activityAnalysisDao.r_queryActivityStationedCount(paramMap);
            resultMap.put("STATIONED_COUNT", stationedCount);
            pr.setState(true);
			pr.setMessage("获取今日活动详情成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.info(e);
        }
        return pr;
	}
	
	/**
	 * 今日活动定金情况
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryRealTime_ActivityPreDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("activity_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            //今日
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String dateNowStr = sdf.format(new Date());
            paramMap.put("date_short", dateNowStr);
            
            //待付定金笔数
            paramMap.put("order_state", 1);
            float stayPayNum = activityAnalysisDao.r_queryRealTime_PreOrderNum(paramMap);
            resultMap.put("STAY_PAY_NUM", stayPayNum);
            //待付尾款笔数
            paramMap.put("order_state", 2);
            float stayPayFinalNum = activityAnalysisDao.r_queryRealTime_PreOrderNum(paramMap);
            resultMap.put("STAY_PAY_FINAL_NUM", stayPayFinalNum);
            //已付部分尾款笔数
            paramMap.put("order_state", 3);
            float payFinalPartNum = activityAnalysisDao.r_queryRealTime_PreOrderNum(paramMap);
            resultMap.put("PAY_FINAL_PART_NUM", payFinalPartNum);
            //已付尾款笔数
            paramMap.put("order_state", 5);
            float payFinalNum = activityAnalysisDao.r_queryRealTime_PreOrderNum(paramMap);
            resultMap.put("PAY_FINAL_NUM", payFinalNum);
            //成交总额
            float paymentMoney = activityAnalysisDao.r_queryRealTime_PaymentMoney(paramMap);
            //预定订单的定金
            float earnestMoney = activityAnalysisDao.r_queryRealTime_earnestMoney(paramMap);
            resultMap.put("PAYMENT_MONEY", paymentMoney+earnestMoney);
            
            pr.setState(true);
			pr.setMessage("获取今日活动定金情况成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.info(e);
        }
        return pr;
	}
	/**
	 * 今日活动趋势分析-商品分享次数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryRealTime_Trend_ShareCount(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("activity_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            //今日
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String td_date = sdf.format(new Date());
            paramMap.put("start_date", td_date);
            paramMap.put("end_date", td_date);
            
        	/** 商品分享次数 */
            float shareCount = activityAnalysisDao.r_queryActivityTrend_ProductShareCount(paramMap);
            
            pr.setState(true);
            pr.setMessage("获取商品分享次数成功");
            pr.setObj(shareCount);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	/**
	 * 今日活动趋势分析-商品销售总额
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryRealTime_Trend_PayMoney(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("activity_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            //今日
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String td_date = sdf.format(new Date());
            paramMap.put("start_date", td_date);
            paramMap.put("end_date", td_date);
            
        	/** 商品销售总额 */
            float payMoney = activityAnalysisDao.r_queryActivityTrend_PayMoney(paramMap);
            /** 预定订单的定金 */
            float preFirstMoney = activityAnalysisDao.r_queryActivityTrend_PreFirstMoney(paramMap);
            payMoney+=preFirstMoney;
            
            pr.setState(true);
            pr.setMessage("获取商品销售总额成功");
            pr.setObj(payMoney);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	
	/**
	 * 今日活动趋势分析-活动支付单笔数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryRealTime_Trend_PayNum(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("activity_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            //今日
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String td_date = sdf.format(new Date());
            paramMap.put("start_date", td_date);
            paramMap.put("end_date", td_date);
            
        	/** 活动支付单笔数 */
            float payNum = activityAnalysisDao.r_queryActivityTrend_PayNum(paramMap);
            
            pr.setState(true);
            pr.setMessage("获取活动支付单笔数成功");
            pr.setObj(payNum);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	/**
	 * 今日活动趋势分析-分享支付单数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryRealTime_Trend_SharePayNum(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("activity_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            //今日
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String td_date = sdf.format(new Date());
            paramMap.put("start_date", td_date);
            paramMap.put("end_date", td_date);
            
        	/** 分享支付单数 */
            float sharePayNum = activityAnalysisDao.r_queryActivityTrend_SharePayNum(paramMap);
            
            pr.setState(true);
            pr.setMessage("获取分享支付单数成功");
            pr.setObj(sharePayNum);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	
	/**
	 * 今日活动趋势分析 折线图
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryRealTime_TrendChart(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("activity_id"))) {
            	pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            //今日
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
        	/** 折线图 商品分享次数 */
        	List<Map<String, Object>> shareCountList = activityAnalysisDao.r_queryRealTime_Trend_ShareCountD_Chart(paramMap);
        	/** 折线图 商品销售总额 */
        	List<Map<String, Object>> payMoneyList = activityAnalysisDao.r_queryRealTime_Trend_PayMoneyD_Chart(paramMap);
        	List<Map<String, Object>> preFirstMoneyList = activityAnalysisDao.r_queryRealTime_Trend_PreFirstMoneyD_Chart(paramMap);
        	/** 折线图 活动支付单笔数 */
        	List<Map<String, Object>> payNumList = activityAnalysisDao.r_queryRealTime_Trend_PayNumD_Chart(paramMap);
        	/** 折线图 分享支付单数 */
        	List<Map<String, Object>> sharePayNumList = activityAnalysisDao.r_queryRealTime_Trend_SharePayNumD_Chart(paramMap);
            
            //商品分享次数
            Map<String,Object> shareCountMap = list2Map(shareCountList,"CREATE_DATE","SHARE_COUNT");
            //商品销售总额
            Map<String,Object> payMoneyMap = list2Map(payMoneyList,"CREATE_DATE","PAY_MONEY");
            //预定订单的定金
            Map<String,Object> preFirstMoneyMap = list2Map(preFirstMoneyList,"CREATE_DATE","PRE_FIRST_MONEY");
            //活动支付单笔数
            Map<String,Object> payNumMap = list2Map(payNumList,"CREATE_DATE","PAY_NUM");
            //分享支付单数
            Map<String,Object> sharePayNumMap = list2Map(sharePayNumList,"CREATE_DATE","SHARE_PAY_NUM");
            
            //开始数据拼装
          	String key="";
          	
          	Map<String, Object> map = null;
            for(String time : time_list) {
            	key = time;
            	/** 今日活动趋势分析-商品分享次数 */
                float shareCount = shareCountMap.get(key)==null?0:Float.parseFloat(shareCountMap.get(key).toString());
                /** 今日活动趋势分析-商品销售总额 */
                float payMoney = payMoneyMap.get(key)==null?0:Float.parseFloat(payMoneyMap.get(key).toString());
                /** 今日活动趋势分析-预定订单的定金 */
                float preFirstMoney = preFirstMoneyMap.get(key)==null?0:Float.parseFloat(preFirstMoneyMap.get(key).toString());
                /** 今日活动趋势分析-活动支付单笔数 */
                float payNum = payNumMap.get(key)==null?0:Float.parseFloat(payNumMap.get(key).toString());
                /** 今日活动趋势分析-分享支付单数 */
                float sharePayNum = sharePayNumMap.get(key)==null?0:Float.parseFloat(sharePayNumMap.get(key).toString());
                payMoney+=preFirstMoney;
                map = new HashMap<String, Object>();
                //商品分享次数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", shareCount);
              	map.put("PAGE_NAME", "商品分享次数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//商品销售总额
              	map.put("CREATE_DATE", key);
              	map.put("CNT", payMoney);
              	map.put("PAGE_NAME", "商品销售总额");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//活动支付单笔数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", payNum);
              	map.put("PAGE_NAME", "活动支付单笔数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//分享支付单数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", sharePayNum);
              	map.put("PAGE_NAME", "分享支付单数");
              	allDataList.add(map);
            }
            
            //将原始数据组装为Echart可识别的格式
            resultMap = createData("PAGE_NAME", time_list, allDataList);
            
            pr.setState(true);
			pr.setMessage("获取今日活动趋势分析成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	/**
	 * 今日活动商品排行
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryRealTime_ProductList(HttpServletRequest request) {
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
            //今日
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String td_date = sdf.format(new Date());
            paramMap.put("start_date", td_date);
            paramMap.put("end_date", td_date);
			/** 其他 活动商品总数 */
			int total = activityAnalysisDao.r_queryProductCount(paramMap);
			//浏览量
			String file_names_pvc = ":PV_COUNT:";
			//活动销量
			String file_names_pc = ":PAY_COUNT:";
			//活动销售总额
			String file_names_pm = ":PAY_MONEY:";
			//活动买家数
			String file_names_pn = ":PURCHASE_NUMBER:";
			List<Map<String, Object>> list=null;
			
			//需要查询的商品列表
			List<String> productList = new ArrayList<String>();
			if((paramMap.containsKey("sort")&&!"".equals(paramMap.get("sort").toString()))
					&&(paramMap.containsKey("sort_by")&&!"".equals(paramMap.get("sort_by").toString()))){
				String sort = ":"+paramMap.get("sort").toString()+":";
				if(file_names_pvc.indexOf(sort)!=-1) {
					//浏览量---获取排序后的商品列表
					productList=activityAnalysisDao.r_queryProduct_PvCount(paramMap);
				}else if(file_names_pc.indexOf(sort)!=-1) {
					//活动销量---获取排序后的商品列表
					productList=activityAnalysisDao.r_queryProduct_PayCount(paramMap);
				}else if(file_names_pm.indexOf(sort)!=-1) {
					//活动销售总额---获取排序后的商品列表
					productList=activityAnalysisDao.r_queryProduct_PayMoney(paramMap);
				}else if(file_names_pn.indexOf(sort)!=-1) {
					//活动买家数---获取排序后的商品列表
					productList=activityAnalysisDao.r_queryProduct_PurchaseNumber(paramMap);
				}else {
					gr.setState(false);
					gr.setMessage("未配置的排序字段，请联系管理员【"+paramMap.get("sort").toString()+"】");
					return gr;
				}
			}else{
				//查询默认排序的商品列表
				productList=activityAnalysisDao.r_queryProductListBy_Default(paramMap);
			}
			
			if(!productList.isEmpty()&&productList.size()>0){
				paramMap.put("productList", productList);
				list = activityAnalysisDao.r_queryProductList(paramMap);
			}
			
			if (list != null && list.size() > 0) {
				paramMap.put("list", list);
				List<Map<String,Object>> ladderList = new ArrayList<Map<String,Object>>();
				Map<String,Object> hdPriceMap = new HashMap<String,Object>();
				if(!StringUtils.isEmpty(paramMap.get("activity_type"))) {
					if(paramMap.get("activity_type").toString().equals("5")) {//清尾
						//商品阶梯价
						ladderList = activityAnalysisDao.queryProductLadderDiscount(paramMap);
					}else {
						//活动价
						List<Map<String,Object>> hdPriceList = activityAnalysisDao.queryProductHdPrice(paramMap);
						hdPriceMap = list2Map(hdPriceList,"ITEMNUMBER","HD_PRICE");
					}
				}
				//浏览量
				List<Map<String,Object>> pvCountList = activityAnalysisDao.r_queryProductPvCount(paramMap);
				Map<String,Object> pvCountMap = list2Map(pvCountList,"ITEMNUMBER","PV_COUNT");
				
				//活动销量，活动销售总额，活动买家数
				List<Map<String,Object>> payPurchaseNumberCountMoneyList = activityAnalysisDao.r_queryProduct_PayPurchaseNumberCountMoney(paramMap);
				Map<String,Object> purchaseNumberMap = list2Map(payPurchaseNumberCountMoneyList,"ITEMNUMBER","PURCHASE_NUMBER");
				Map<String,Object> payCountMap = list2Map(payPurchaseNumberCountMoneyList,"ITEMNUMBER","PAY_COUNT");
				Map<String,Object> payMoneyMap = list2Map(payPurchaseNumberCountMoneyList,"ITEMNUMBER","PAY_MONEY");
				
				//预定订单的定金
				List<Map<String,Object>> preFirstMoneyList = activityAnalysisDao.r_queryProduct_PreFirstMoney(paramMap);
				Map<String,Object> preFirstMoneyMap = list2Map(preFirstMoneyList,"ITEMNUMBER","PRE_FIRST_MONEY");
                
				Map<String,Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String itemnumber = tempMap.get("ITEMNUMBER").toString();
					//阶梯价
					List<Map<String, Object>> newLadderList = new ArrayList<Map<String, Object>>();
					for (Map<String, Object> ladderMap : ladderList) {
						String productItemnumber = ladderMap.get("PRODUCT_ITEMNUMBER").toString();
						if (itemnumber.equals(productItemnumber)) {
							newLadderList.add(ladderMap);
						}
					}
					tempMap.put("LADDER_LIST", newLadderList);
					//活动价
					tempMap.put("HD_PRICE", hdPriceMap.get(itemnumber)==null?"-":hdPriceMap.get(itemnumber));
					float payMoney = payMoneyMap.get(itemnumber)==null?0:Float.parseFloat(payMoneyMap.get(itemnumber).toString());
					//预定订单的定金
					float preFirstMoney = preFirstMoneyMap.get(itemnumber)==null?0:Float.parseFloat(preFirstMoneyMap.get(itemnumber).toString());
					payMoney+=preFirstMoney;
					//浏览量
					tempMap.put("PV_COUNT", pvCountMap.get(itemnumber)==null?0:Float.parseFloat(pvCountMap.get(itemnumber).toString()));
					//活动销量
					tempMap.put("PAY_COUNT", payCountMap.get(itemnumber)==null?0:Float.parseFloat(payCountMap.get(itemnumber).toString()));
					//活动销售总额
					tempMap.put("PAY_MONEY",payMoney);
					//活动买家数
					tempMap.put("PURCHASE_NUMBER", purchaseNumberMap.get(itemnumber)==null?0:Float.parseFloat(purchaseNumberMap.get(itemnumber).toString()));
				}
				gr.setState(true);
				gr.setMessage("获取今日活动商品列表成功");
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
	 * 活动分析-活动日历
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryActivityAnalysisList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("year"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            if((!StringUtils.isEmpty(paramMap.get("start_state")))&&paramMap.get("start_state") instanceof String){
				paramMap.put("start_state",(paramMap.get("start_state")+"").split(","));
			}
        	/** 活动列表 */
            List<Map<String, Object>> resultList = activityAnalysisDao.r_queryActivityAnalysisList(paramMap);
            
            pr.setState(true);
            pr.setMessage("获取活动日历成功");
            pr.setObj(resultList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	
	/**
	 * 活动分析-活动详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryActivityAnalysisDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("activity_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
        	/** 活动详情 */
            Map<String, Object> resultMap = activityAnalysisDao.r_queryActivityDetail(paramMap);
            /** 参与会员数 */
            float memberCount = activityAnalysisDao.r_queryActivityMemberCount(paramMap);
            resultMap.put("MEMBER_COUNT", memberCount);
            /** 活动商品数 */
            float productCount = activityAnalysisDao.r_queryActivityProductCount(paramMap);
            resultMap.put("PRODUCT_COUNT",productCount);
            /** 参与品牌数 */
            float brandCount = activityAnalysisDao.r_queryActivityBrandCount(paramMap);
            resultMap.put("BRAND_COUNT", brandCount);
            /** 参与商家数 */
            float stationedCount = activityAnalysisDao.r_queryActivityStationedCount(paramMap);
            resultMap.put("STATIONED_COUNT", stationedCount);
            
            pr.setState(true);
            pr.setMessage("获取活动详情成功");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	
	/**
	 * 活动分析-活动汇总
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryActivityAnalysisSummary(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("activity_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            
            /** 活动商品销量、商品销售总额、支付买家数 */
            Map<String, Object> purchaseNumberCountMoneyMap = activityAnalysisDao.r_queryActivityPurchaseNumberCountMoney(paramMap);
            resultMap.put("PURCHASE_NUMBER", purchaseNumberCountMoneyMap.get("PURCHASE_NUMBER")==null?0:Float.parseFloat(purchaseNumberCountMoneyMap.get("PURCHASE_NUMBER").toString()));
            resultMap.put("PAY_COUNT", purchaseNumberCountMoneyMap.get("PAY_COUNT")==null?0:Float.parseFloat(purchaseNumberCountMoneyMap.get("PAY_COUNT").toString()));
            float payMoney = purchaseNumberCountMoneyMap.get("PAY_MONEY")==null?0:Float.parseFloat(purchaseNumberCountMoneyMap.get("PAY_MONEY").toString());
            //预定订单的定金
            float preFirstMoney = activityAnalysisDao.r_queryActivityProductPreFirstMoney(paramMap);
            payMoney+=preFirstMoney;
            resultMap.put("PAY_MONEY", payMoney);
            /** 活动未发退款总额 */
            float unsentRefundMoney = activityAnalysisDao.r_queryActivityUnsentRefundMoney(paramMap);
            resultMap.put("UNSENT_REFUND_MONEY", unsentRefundMoney);
            pr.setState(true);
            pr.setMessage("获取活动汇总成功");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	
	/**
	 * 活动分析-活动成交总额
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryActivityPaymentMoney(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("activity_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            /** 活动成交总额 */
            float paymentMoney = activityAnalysisDao.r_queryActivityPaymentMoney(paramMap);;
            //预定订单的定金
            float preFirstMoney = activityAnalysisDao.r_queryActivityPreFirstMoney(paramMap);
            paymentMoney+=preFirstMoney;
            pr.setState(true);
            pr.setMessage("获取活动成交总额成功");
            pr.setObj(paymentMoney);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	/**
	 * 活动趋势分析-活动商品访客数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryActivityTrend_VisitorCount(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("activity_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            
            /** 活动商品访客数 */
            float visitorCount = activityAnalysisDao.r_queryActivityTrend_ProductVisitorCount(paramMap);
            retMap.put("visitor_count", visitorCount);
            
            pr.setState(true);
            pr.setMessage("获取活动商品访客数成功");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	/**
	 * 活动趋势分析-活动销售总额
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryActivityTrend_PayMoney(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("activity_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            
            /** 商品销售总额 */
            float payMoney = activityAnalysisDao.r_queryActivityTrend_PayMoney(paramMap);
            /** 预定订单的定金 */
            float preFirstMoney = activityAnalysisDao.r_queryActivityTrend_PreFirstMoney(paramMap);
            payMoney+=preFirstMoney;
            
            pr.setState(true);
            pr.setMessage("获取商品销售总额成功");
            pr.setObj(payMoney);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	/**
	 * 活动趋势分析-活动支付单笔数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryActivityTrend_PayNum(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("activity_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            
            /** 活动支付单笔数 */
            float payNum = activityAnalysisDao.r_queryActivityTrend_PayNum(paramMap);
            
            pr.setState(true);
            pr.setMessage("获取活动支付单笔数成功");
            pr.setObj(payNum);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	/**
	 * 活动趋势分析-活动支付买家数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryActivityTrend_PurchaseNumber(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("activity_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            
            /** 活动支付买家数 */
            float payNum = activityAnalysisDao.r_queryActivityTrend_PayPurchaseNumber(paramMap);
            
            pr.setState(true);
            pr.setMessage("获取活动支付买家数成功");
            pr.setObj(payNum);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	/**
	 * 活动趋势分析-活动支付件数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryActivityTrend_PayCount(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("activity_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            
            /** 活动支付件数 */
            float payNum = activityAnalysisDao.r_queryActivityTrend_PayCount(paramMap);
            
            pr.setState(true);
            pr.setMessage("获取活动支付件数成功");
            pr.setObj(payNum);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	/**
	 * 活动趋势分析-活动商品分享次数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryActivityTrend_ShareCount(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("activity_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            
            /** 活动商品分享次数 */
            float payNum = activityAnalysisDao.r_queryActivityTrend_ProductShareCount(paramMap);
            
            pr.setState(true);
            pr.setMessage("获取活动商品分享次数成功");
            pr.setObj(payNum);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	/**
	 * 活动趋势分析-分享支付单数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryActivityTrend_SharePayNum(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("activity_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            
            /** 分享支付单数 */
            float payNum = activityAnalysisDao.r_queryActivityTrend_SharePayNum(paramMap);
            
            pr.setState(true);
            pr.setMessage("获取分享支付单数成功");
            pr.setObj(payNum);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	/**
	 * 活动趋势分析 折线图
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryActivityTrendChart(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("activity_id"))) {
            	pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
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
            /** 折线图 活动商品访客数 */
            List<Map<String, Object>> visitorCountList = activityAnalysisDao.r_queryActivityTrend_ProductVisitorCount_Chart(paramMap);
            /** 折线图 活动销售总额，活动支付件数，活动支付买家数 */
            List<Map<String, Object>> purchaseNumberCountMoneyList = activityAnalysisDao.r_queryActivityTrend_PayPurchaseNumberCountMoney_Chart(paramMap);
            /** 折线图 预定订单的定金 */
            List<Map<String, Object>> preFirstMoneyList = activityAnalysisDao.r_queryActivityTrend_PreFirstMoney_Chart(paramMap);
            /** 折线图 活动支付单笔数 */
            List<Map<String, Object>>  payNumList = activityAnalysisDao.r_queryActivityTrend_PayNum_Chart(paramMap);
            /** 折线图 商品分享次数 */
            List<Map<String, Object>> shareCountList = activityAnalysisDao.r_queryActivityTrend_ShareCount_Chart(paramMap);
        	/** 折线图 分享支付单数 */
            List<Map<String, Object>> sharePayNumList = activityAnalysisDao.r_queryActivityTrend_SharePayNum_Chart(paramMap);
            //活动商品访客数
            Map<String,Object> visitorCountMap = list2Map(visitorCountList,"CREATE_DATE","VISITOR_COUNT");
            //活动销售总额
            Map<String,Object> payMoneyMap = list2Map(purchaseNumberCountMoneyList,"CREATE_DATE","PAY_MONEY");
            //活动支付件数
            Map<String,Object> payCountMap = list2Map(purchaseNumberCountMoneyList,"CREATE_DATE","PAY_COUNT");
            //活动支付买家数
            Map<String,Object> purchaseNumberMap = list2Map(purchaseNumberCountMoneyList,"CREATE_DATE","PURCHASE_NUMBER");
            //预定订单的定金
            Map<String,Object> preFirstMoneyMap = list2Map(preFirstMoneyList,"CREATE_DATE","PRE_FIRST_MONEY");
            //活动支付单笔数
            Map<String,Object> payNumMap = list2Map(payNumList,"CREATE_DATE","PAY_NUM");
            //商品分享次数
            Map<String,Object> shareCountMap = list2Map(shareCountList,"CREATE_DATE","SHARE_COUNT");
            //分享支付单数
            Map<String,Object> sharePayNumMap = list2Map(sharePayNumList,"CREATE_DATE","SHARE_PAY_NUM");
            
            //开始数据拼装
          	String key="";
          	
          	Map<String, Object> map = null;
            for(String time : time_list) {
            	key = time;
            	/** 活动趋势分析-活动商品访客数 */
            	float visitorCount = visitorCountMap.get(key)==null?0:Float.parseFloat(visitorCountMap.get(key).toString());
            	/** 活动趋势分析-活动销售总额 */
                float payMoney = payMoneyMap.get(key)==null?0:Float.parseFloat(payMoneyMap.get(key).toString());
                /** 活动趋势分析-活动支付件数 */
                float payCount = payCountMap.get(key)==null?0:Float.parseFloat(payCountMap.get(key).toString());
                /** 活动趋势分析-活动支付买家数 */
                float purchaseNumber = purchaseNumberMap.get(key)==null?0:Float.parseFloat(purchaseNumberMap.get(key).toString());
                /** 活动趋势分析-预定订单的定金 */
                float preFirstMoney = preFirstMoneyMap.get(key)==null?0:Float.parseFloat(preFirstMoneyMap.get(key).toString());
                /** 活动趋势分析-活动支付单笔数 */
                float payNum = payNumMap.get(key)==null?0:Float.parseFloat(payNumMap.get(key).toString());
            	/** 活动趋势分析-商品分享次数 */
                float shareCount = shareCountMap.get(key)==null?0:Float.parseFloat(shareCountMap.get(key).toString());
                /** 活动趋势分析-分享支付单数 */
                float sharePayNum = sharePayNumMap.get(key)==null?0:Float.parseFloat(sharePayNumMap.get(key).toString());
                payMoney+=preFirstMoney;
                map = new HashMap<String, Object>();
                //活动商品访客数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", visitorCount);
              	map.put("PAGE_NAME", "活动商品访客数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
                //活动销售总额
              	map.put("CREATE_DATE", key);
              	map.put("CNT", payMoney);
              	map.put("PAGE_NAME", "活动销售总额");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//活动支付件数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", payCount);
              	map.put("PAGE_NAME", "活动支付件数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//活动支付买家数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", purchaseNumber);
              	map.put("PAGE_NAME", "活动支付买家数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//活动支付单笔数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", payNum);
              	map.put("PAGE_NAME", "活动支付单笔数");
              	allDataList.add(map);
                
                map = new HashMap<String, Object>();
                //商品分享次数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", shareCount);
              	map.put("PAGE_NAME", "商品分享次数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//分享支付单数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", sharePayNum);
              	map.put("PAGE_NAME", "分享支付单数");
              	allDataList.add(map);
            }
            
            //将原始数据组装为Echart可识别的格式
            resultMap = createData("PAGE_NAME", time_list, allDataList);
            
            pr.setState(true);
			pr.setMessage("获取活动趋势分析成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	/**
	 * 活动商品销售额占比
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryActivityPayMoneyRatio(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("activity_id"))) {
            	pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
        	
            List<Map<String, Object>> allDataList = new ArrayList<Map<String, Object>>();
            /** 活动商品销售额 */
            float acPayMoney = activityAnalysisDao.r_queryActivityTrend_PayMoney(paramMap);
            /** 活动商品定金 */
            float acPreFirstMoney = activityAnalysisDao.r_queryActivityTrend_PreFirstMoney(paramMap);
            acPayMoney+=acPreFirstMoney;
            
            /** 平台商品销售额 */
            float payMoney = activityAnalysisDao.r_queryPayMoney(paramMap);
            /** 平台商品定金 */
            float preFirstMoney = activityAnalysisDao.r_queryPreFirstMoney(paramMap);
            payMoney+=preFirstMoney;
            
            Map<String, Object> map = new HashMap<String, Object>();
            //活动
          	map.put("PAGE_NAME", "活动");
          	map.put("CNT", acPayMoney);
          	allDataList.add(map);
          	
          	map = new HashMap<String, Object>();
            //平台
          	map.put("PAGE_NAME", "平台");
          	map.put("CNT", payMoney);
          	allDataList.add(map);
            
            pr.setState(true);
			pr.setMessage("获取活动商品销售额占比成功");
			pr.setObj(allDataList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.info(e);
        }
        return pr;
	}
	/**
	 * 活动支付单数占比
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryActivityPayNunRatio(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
        	// 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("activity_id"))) {
            	pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
        	
            List<Map<String, Object>> allDataList = new ArrayList<Map<String, Object>>();
            /** 活动支付单数 */
            float acPayNum = activityAnalysisDao.r_queryActivityTrend_PayNum(paramMap);
            
            /** 平台支付单数 */
            float payNum = activityAnalysisDao.r_queryPayNum(paramMap);
            
            Map<String, Object> map = new HashMap<String, Object>();
            //活动
          	map.put("PAGE_NAME", "活动");
          	map.put("CNT", acPayNum);
          	allDataList.add(map);
          	
          	map = new HashMap<String, Object>();
            //平台
          	map.put("PAGE_NAME", "平台");
          	map.put("CNT", payNum);
          	allDataList.add(map);
            
            pr.setState(true);
			pr.setMessage("获取活动支付单数占比成功");
			pr.setObj(allDataList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.info(e);
        }
        return pr;
	}
	/**
	 * 活动商品列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryActivityProductList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
			if(paramMap.size() == 0||StringUtils.isEmpty(paramMap.get("activity_id"))) {
            	gr.setState(false);
            	gr.setMessage("参数缺失");
                return gr;
            }
			List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
			/** 其他 活动商品总数 */
			int total = activityAnalysisDao.r_queryProductCount(paramMap);
			//浏览量
			String file_names_pvc = ":PV_COUNT:";
			//活动销量
			String file_names_pc = ":PAY_COUNT:";
			//活动销售总额
			String file_names_pm = ":PAY_MONEY:";
			//活动买家数
			String file_names_pn = ":PURCHASE_NUMBER:";
			//未发退款总额
			String file_names_urm = ":UNSEND_REFUND_MONEY:";
			List<Map<String, Object>> list=null;
			
			//需要查询的商品列表
			List<String> productList = new ArrayList<String>();
			if((paramMap.containsKey("sort")&&!"".equals(paramMap.get("sort").toString()))
					&&(paramMap.containsKey("sort_by")&&!"".equals(paramMap.get("sort_by").toString()))){
				String sort = ":"+paramMap.get("sort").toString()+":";
				if(file_names_pvc.indexOf(sort)!=-1) {
					//浏览量---获取排序后的商品列表
					productList=activityAnalysisDao.r_queryProduct_PvCount(paramMap);
				}else if(file_names_pc.indexOf(sort)!=-1) {
					//活动销量---获取排序后的商品列表
					productList=activityAnalysisDao.r_queryProduct_PayCount(paramMap);
				}else if(file_names_pm.indexOf(sort)!=-1) {
					//活动销售总额---获取排序后的商品列表
					productList=activityAnalysisDao.r_queryProduct_PayMoney(paramMap);
				}else if(file_names_pn.indexOf(sort)!=-1) {
					//活动买家数---获取排序后的商品列表
					productList=activityAnalysisDao.r_queryProduct_PurchaseNumber(paramMap);
				}else if(file_names_urm.indexOf(sort)!=-1) {
					//未发退款总额---获取排序后的商品列表
					productList=activityAnalysisDao.r_queryProduct_UnsendRefundMoney(paramMap);
				}else {
					gr.setState(false);
					gr.setMessage("未配置的排序字段，请联系管理员【"+paramMap.get("sort").toString()+"】");
					return gr;
				}
			}else{
				//查询默认排序的商品列表
				productList=activityAnalysisDao.r_queryProductListBy_Default(paramMap);
			}
			
			if(!productList.isEmpty()&&productList.size()>0){
				paramMap.put("productList", productList);
				list = activityAnalysisDao.r_queryProductList(paramMap);
			}
			
			if (list != null && list.size() > 0) {
				paramMap.put("list", list);
				List<Map<String,Object>> ladderList = new ArrayList<Map<String,Object>>();
				Map<String,Object> hdPriceMap = new HashMap<String,Object>();
				if(!StringUtils.isEmpty(paramMap.get("activity_type"))) {
					if(paramMap.get("activity_type").toString().equals("5")) {//清尾
						//商品阶梯价
						ladderList = activityAnalysisDao.queryProductLadderDiscount(paramMap);
					}else {
						//活动价
						List<Map<String,Object>> hdPriceList = activityAnalysisDao.queryProductHdPrice(paramMap);
						hdPriceMap = list2Map(hdPriceList,"ITEMNUMBER","HD_PRICE");
					}
				}
				//浏览量
				List<Map<String,Object>> pvCountList = activityAnalysisDao.r_queryProductPvCount(paramMap);
				Map<String,Object> pvCountMap = list2Map(pvCountList,"ITEMNUMBER","PV_COUNT");
				
				//活动销量，活动销售总额，活动买家数
				List<Map<String,Object>> payPurchaseNumberCountMoneyList = activityAnalysisDao.r_queryProduct_PayPurchaseNumberCountMoney(paramMap);
				Map<String,Object> purchaseNumberMap = list2Map(payPurchaseNumberCountMoneyList,"ITEMNUMBER","PURCHASE_NUMBER");
				Map<String,Object> payCountMap = list2Map(payPurchaseNumberCountMoneyList,"ITEMNUMBER","PAY_COUNT");
				Map<String,Object> payMoneyMap = list2Map(payPurchaseNumberCountMoneyList,"ITEMNUMBER","PAY_MONEY");
				
				//预定订单的定金
				List<Map<String,Object>> preFirstMoneyList = activityAnalysisDao.r_queryProduct_PreFirstMoney(paramMap);
				Map<String,Object> preFirstMoneyMap = list2Map(preFirstMoneyList,"ITEMNUMBER","PRE_FIRST_MONEY");
				//未发退款总额
				List<Map<String,Object>> unsendRefundMoneyList = activityAnalysisDao.r_queryProductUnsendRefundMoney(paramMap);
				Map<String,Object> unsendRefundMoneyMap = list2Map(unsendRefundMoneyList,"ITEMNUMBER","UNSEND_REFUND_MONEY");
                
				Map<String,Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String itemnumber = tempMap.get("ITEMNUMBER").toString();
					//阶梯价
					List<Map<String, Object>> newLadderList = new ArrayList<Map<String, Object>>();
					for (Map<String, Object> ladderMap : ladderList) {
						String productItemnumber = ladderMap.get("PRODUCT_ITEMNUMBER").toString();
						if (itemnumber.equals(productItemnumber)) {
							newLadderList.add(ladderMap);
						}
					}
					tempMap.put("LADDER_LIST", newLadderList);
					//活动价
					tempMap.put("HD_PRICE", hdPriceMap.get(itemnumber)==null?"-":hdPriceMap.get(itemnumber));
					float payMoney = payMoneyMap.get(itemnumber)==null?0:Float.parseFloat(payMoneyMap.get(itemnumber).toString());
					//预定订单的定金
					float preFirstMoney = preFirstMoneyMap.get(itemnumber)==null?0:Float.parseFloat(preFirstMoneyMap.get(itemnumber).toString());
					payMoney+=preFirstMoney;
					//浏览量
					tempMap.put("PV_COUNT", pvCountMap.get(itemnumber)==null?0:Float.parseFloat(pvCountMap.get(itemnumber).toString()));
					//活动销量
					tempMap.put("PAY_COUNT", payCountMap.get(itemnumber)==null?0:Float.parseFloat(payCountMap.get(itemnumber).toString()));
					//活动销售总额
					tempMap.put("PAY_MONEY",payMoney);
					//活动买家数
					tempMap.put("PURCHASE_NUMBER", purchaseNumberMap.get(itemnumber)==null?0:Float.parseFloat(purchaseNumberMap.get(itemnumber).toString()));
					//未发退款总额
					tempMap.put("UNSEND_REFUND_MONEY", unsendRefundMoneyMap.get(itemnumber)==null?0:Float.parseFloat(unsendRefundMoneyMap.get(itemnumber).toString()));
				}
				gr.setState(true);
				gr.setMessage("获取活动商品列表成功");
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
	 * 活动品牌列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryActivityBrandList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
			if(paramMap.size() == 0||StringUtils.isEmpty(paramMap.get("activity_id"))) {
            	gr.setState(false);
            	gr.setMessage("参数缺失");
                return gr;
            }
			List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
			/** 其他 品牌总数 */
			int total = activityAnalysisDao.r_queryBrandCount(paramMap);
			//活动销量
			String file_names_pc = ":PAY_COUNT:";
			//活动销售总额
			String file_names_pm = ":PAY_MONEY:";
			//活动买家数
			String file_names_pn = ":PURCHASE_NUMBER:";
			List<Map<String, Object>> list=null;
			
			//需要查询的品牌列表
			List<String> brandList = new ArrayList<String>();
			if((paramMap.containsKey("sort")&&!"".equals(paramMap.get("sort").toString()))
					&&(paramMap.containsKey("sort_by")&&!"".equals(paramMap.get("sort_by").toString()))){
				String sort = ":"+paramMap.get("sort").toString()+":";
				if(file_names_pc.indexOf(sort)!=-1) {
					//活动销量---获取排序后的品牌列表
					brandList=activityAnalysisDao.r_queryBrand_PayCount(paramMap);
				}else if(file_names_pm.indexOf(sort)!=-1) {
					//活动销售总额---获取排序后的品牌列表
					brandList=activityAnalysisDao.r_queryBrand_PayMoney(paramMap);
				}else if(file_names_pn.indexOf(sort)!=-1) {
					//活动买家数---获取排序后的品牌列表
					brandList=activityAnalysisDao.r_queryBrand_PayPurchaseNumber(paramMap);
				}else {
					gr.setState(false);
					gr.setMessage("未配置的排序字段，请联系管理员【"+paramMap.get("sort").toString()+"】");
					return gr;
				}
			}else{
				//查询默认排序的品牌列表
				brandList=activityAnalysisDao.r_queryBrandListBy_Default(paramMap);
			}
			
			if(!brandList.isEmpty()&&brandList.size()>0){
				paramMap.put("brandList", brandList);
				list = activityAnalysisDao.r_queryBrandList(paramMap);
			}
			
			if (list != null && list.size() > 0) {
				//活动销量，活动销售总额，活动买家数
				List<Map<String,Object>> payPurchaseNumberCountMoneyList = activityAnalysisDao.r_queryBrand_PayPurchaseNumberCountMoney(paramMap);
				Map<String,Object> purchaseNumberMap = list2Map(payPurchaseNumberCountMoneyList,"BRAND_ID","PURCHASE_NUMBER");
				Map<String,Object> payCountMap = list2Map(payPurchaseNumberCountMoneyList,"BRAND_ID","PAY_COUNT");
				Map<String,Object> payMoneyMap = list2Map(payPurchaseNumberCountMoneyList,"BRAND_ID","PAY_MONEY");
				
				//预定订单的定金
				List<Map<String,Object>> preFirstMoneyList = activityAnalysisDao.r_queryBrand_PreFirstMoney(paramMap);
				Map<String,Object> preFirstMoneyMap = list2Map(preFirstMoneyList,"BRAND_ID","PRE_FIRST_MONEY");
                
				Map<String,Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String brand_id = tempMap.get("BRAND_ID").toString();
					float payMoney = payMoneyMap.get(brand_id)==null?0:Float.parseFloat(payMoneyMap.get(brand_id).toString());
					//预定订单的定金
					float preFirstMoney = preFirstMoneyMap.get(brand_id)==null?0:Float.parseFloat(preFirstMoneyMap.get(brand_id).toString());
					payMoney+=preFirstMoney;
					//活动销量
					tempMap.put("PAY_COUNT", payCountMap.get(brand_id)==null?0:Float.parseFloat(payCountMap.get(brand_id).toString()));
					//活动销售总额
					tempMap.put("PAY_MONEY",payMoney);
					//活动买家数
					tempMap.put("PURCHASE_NUMBER", purchaseNumberMap.get(brand_id)==null?0:Float.parseFloat(purchaseNumberMap.get(brand_id).toString()));
				}
				gr.setState(true);
				gr.setMessage("获取品牌列表成功");
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
	 * 入驻商列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryActivityStationedList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
			if(paramMap.size() == 0||StringUtils.isEmpty(paramMap.get("activity_id"))) {
            	gr.setState(false);
            	gr.setMessage("参数缺失");
                return gr;
            }
			List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
			/** 其他 入驻商总数 */
			int total = activityAnalysisDao.r_queryStationedCount(paramMap);
			//活动销量
			String file_names_pc = ":PAY_COUNT:";
			//活动销售总额
			String file_names_pm = ":PAY_MONEY:";
			//活动买家数
			String file_names_pn = ":PURCHASE_NUMBER:";
			List<Map<String, Object>> list=null;
			
			//需要查询的入驻商列表
			List<String> stationedList = new ArrayList<String>();
			if((paramMap.containsKey("sort")&&!"".equals(paramMap.get("sort").toString()))
					&&(paramMap.containsKey("sort_by")&&!"".equals(paramMap.get("sort_by").toString()))){
				String sort = ":"+paramMap.get("sort").toString()+":";
				if(file_names_pc.indexOf(sort)!=-1) {
					//活动销量---获取排序后的入驻商列表
					stationedList=activityAnalysisDao.r_queryStationed_PayCount(paramMap);
				}else if(file_names_pm.indexOf(sort)!=-1) {
					//活动销售总额---获取排序后的入驻商列表
					stationedList=activityAnalysisDao.r_queryStationed_PayMoney(paramMap);
				}else if(file_names_pn.indexOf(sort)!=-1) {
					//活动买家数---获取排序后的入驻商列表
					stationedList=activityAnalysisDao.r_queryStationed_PayPurchaseNumber(paramMap);
				}else {
					gr.setState(false);
					gr.setMessage("未配置的排序字段，请联系管理员【"+paramMap.get("sort").toString()+"】");
					return gr;
				}
			}else{
				//查询默认排序的入驻商列表
				stationedList=activityAnalysisDao.r_queryStationedListBy_Default(paramMap);
			}
			
			if(!stationedList.isEmpty()&&stationedList.size()>0){
				paramMap.put("stationedList", stationedList);
				list = activityAnalysisDao.r_queryStationedList(paramMap);
			}
			
			if (list != null && list.size() > 0) {
				//活动销量，活动销售总额，活动买家数
				List<Map<String,Object>> payPurchaseNumberCountMoneyList = activityAnalysisDao.r_queryStationed_PayPurchaseNumberCountMoney(paramMap);
				Map<String,Object> purchaseNumberMap = list2Map(payPurchaseNumberCountMoneyList,"STATIONED_USER_ID","PURCHASE_NUMBER");
				Map<String,Object> payCountMap = list2Map(payPurchaseNumberCountMoneyList,"STATIONED_USER_ID","PAY_COUNT");
				Map<String,Object> payMoneyMap = list2Map(payPurchaseNumberCountMoneyList,"STATIONED_USER_ID","PAY_MONEY");
				
				//预定订单的定金
				List<Map<String,Object>> preFirstMoneyList = activityAnalysisDao.r_queryStationed_PreFirstMoney(paramMap);
				Map<String,Object> preFirstMoneyMap = list2Map(preFirstMoneyList,"STATIONED_USER_ID","PRE_FIRST_MONEY");
                
				Map<String,Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String stationed_user_id = tempMap.get("STATIONED_USER_ID").toString();
					float payMoney = payMoneyMap.get(stationed_user_id)==null?0:Float.parseFloat(payMoneyMap.get(stationed_user_id).toString());
					//预定订单的定金
					float preFirstMoney = preFirstMoneyMap.get(stationed_user_id)==null?0:Float.parseFloat(preFirstMoneyMap.get(stationed_user_id).toString());
					payMoney+=preFirstMoney;
					//活动销量
					tempMap.put("PAY_COUNT", payCountMap.get(stationed_user_id)==null?0:Float.parseFloat(payCountMap.get(stationed_user_id).toString()));
					//活动销售总额
					tempMap.put("PAY_MONEY",payMoney);
					//活动买家数
					tempMap.put("PURCHASE_NUMBER", purchaseNumberMap.get(stationed_user_id)==null?0:Float.parseFloat(purchaseNumberMap.get(stationed_user_id).toString()));
				}
				gr.setState(true);
				gr.setMessage("获取入驻商列表成功");
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
	 * 活动会员列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryActivityUserList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
			if(paramMap.size() == 0||StringUtils.isEmpty(paramMap.get("activity_id"))) {
            	gr.setState(false);
            	gr.setMessage("参数缺失");
                return gr;
            }
			List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            //查询活动的参与会员的类型, 1全部会员，2部分会员
            int type = activityAnalysisDao.r_queryActivityMemberType(paramMap);
            paramMap.put("type", type);
			/** 其他 会员总数 */
			int total = activityAnalysisDao.r_queryUserCount(paramMap);
			//购买数量
			String file_names_pc = ":PAY_COUNT:";
			//支付商品总额
			String file_names_pm = ":PAY_MONEY:";
			//未发退款商品总额
			String file_names_urm = ":UNSEND_REFUND_MONEY:";
			List<Map<String, Object>> list=null;
			
			//需要查询的会员列表
			List<String> userList = new ArrayList<String>();
			if((paramMap.containsKey("sort")&&!"".equals(paramMap.get("sort").toString()))
					&&(paramMap.containsKey("sort_by")&&!"".equals(paramMap.get("sort_by").toString()))){
				String sort = ":"+paramMap.get("sort").toString()+":";
				if(file_names_pc.indexOf(sort)!=-1) {
					//购买数量---获取排序后的会员列表
					userList=activityAnalysisDao.r_queryUser_PayCount(paramMap);
				}else if(file_names_pm.indexOf(sort)!=-1) {
					//支付商品总额---获取排序后的会员列表
					userList=activityAnalysisDao.r_queryUser_PayMoney(paramMap);
				}else if(file_names_urm.indexOf(sort)!=-1) {
					//未发退款商品总额---获取排序后的会员列表
					userList=activityAnalysisDao.r_queryUser_UnsendRefundMoney(paramMap);
				}else {
					gr.setState(false);
					gr.setMessage("未配置的排序字段，请联系管理员【"+paramMap.get("sort").toString()+"】");
					return gr;
				}
			}else{
				//查询默认排序的会员列表
				userList=activityAnalysisDao.r_queryUserListBy_Default(paramMap);
			}
			
			if(!userList.isEmpty()&&userList.size()>0){
				paramMap.put("userList", userList);
				list = activityAnalysisDao.r_queryUserList(paramMap);
			}
			
			if (list != null && list.size() > 0) {
				//购买数量，支付商品总额
				List<Map<String,Object>> payCountMoneyList = activityAnalysisDao.r_queryUser_PayCountMoney(paramMap);
				Map<String,Object> payCountMap = list2Map(payCountMoneyList,"USER_ID","PAY_COUNT");
				Map<String,Object> payMoneyMap = list2Map(payCountMoneyList,"USER_ID","PAY_MONEY");
				
				//预定订单的定金
				List<Map<String,Object>> preFirstMoneyList = activityAnalysisDao.r_queryUser_PreFirstMoney(paramMap);
				Map<String,Object> preFirstMoneyMap = list2Map(preFirstMoneyList,"USER_ID","PRE_FIRST_MONEY");
                
				//未发退款商品总额
				List<Map<String,Object>> unsendRefundMoneyList = activityAnalysisDao.r_queryUserUnsendRefundMoney(paramMap);
				Map<String,Object> unsendRefundMoneyMap = list2Map(unsendRefundMoneyList,"USER_ID","UNSEND_REFUND_MONEY");
				
				Map<String,Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String user_id = tempMap.get("USER_ID").toString();
					float payMoney = payMoneyMap.get(user_id)==null?0:Float.parseFloat(payMoneyMap.get(user_id).toString());
					//预定订单的定金
					float preFirstMoney = preFirstMoneyMap.get(user_id)==null?0:Float.parseFloat(preFirstMoneyMap.get(user_id).toString());
					payMoney+=preFirstMoney;
					//购买数量
					tempMap.put("PAY_COUNT", payCountMap.get(user_id)==null?0:Float.parseFloat(payCountMap.get(user_id).toString()));
					//支付商品总额
					tempMap.put("PAY_MONEY",payMoney);
					//未发退款商品总额
					tempMap.put("UNSEND_REFUND_MONEY", unsendRefundMoneyMap.get(user_id)==null?0:Float.parseFloat(unsendRefundMoneyMap.get(user_id).toString()));
				}
				gr.setState(true);
				gr.setMessage("获取会员列表成功");
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
	 * 活动商品阶梯价
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryActivityProductSpecpize(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
        	// 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("activity_id"))||StringUtils.isEmpty(paramMap.get("itemnumber"))) {
            	pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            
            List<Map<String, Object>> resultList = activityAnalysisDao.r_queryActivityProductSpecpize(paramMap);
          
            
            pr.setState(true);
			pr.setMessage("获取活动商品阶梯价成功");
			pr.setObj(resultList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.info(e);
        }
        return pr;
	}
	/**
	 * 活动站点
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryActivitySite(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			List<Map<String,Object>> resultList=activityAnalysisDao.r_queryActivitySite(paramMap);
			pr.setState(true);
			pr.setObj(resultList);
			pr.setMessage("获取数据成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.info(e);
		}
		return pr;
	}
	
	/**
	 * 用户站点
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryUserSite(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			List<Map<String,Object>> resultList=activityAnalysisDao.queryUserSite(paramMap);
			pr.setState(true);
			pr.setObj(resultList);
			pr.setMessage("获取数据成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.info(e);
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

	
}
