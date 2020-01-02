package com.tk.analysis.product.service;

import java.math.BigDecimal;
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

import com.tk.analysis.home.service.HomeAnalysisService;
import com.tk.analysis.product.dao.ProductAnalysisOperationDao;
import com.tk.sys.util.DateUtils;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Service("ProductAnalysisOperationService")
public class ProductAnalysisOperationService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private ProductAnalysisOperationDao productAnalysisOperationDao;
	@Resource
	private HomeAnalysisService homeAnalysisService;
	@Value("${jdbc_user}")
	private String jdbc_user;
	private String[] colors={"#2f4554 ", "#c23531", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF", "#001cf1", "#000000", "#50d400", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF", "#001cf1", "#000000", "#50d400", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF", "#001cf1", "#000000", "#50d400", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF", "#001cf1", "#000000", "#50d400", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF", "#001cf1", "#000000", "#50d400", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF", "#001cf1", "#000000", "#50d400", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF"};
	
	/**
	 * 商品概况
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductSurvey(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            Map<String, Object> resultMap = productAnalysisOperationDao.queryProductSurvey(paramMap);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            
            /**************************************************今日相关统计    begin****************************************************/
            String td_date = sdf.format(new Date());
            paramMap.put("start_date", td_date);
            paramMap.put("end_date", td_date);
            float money = 0;
            /** 其他 商品销售总额(包含尾款定金) */
            float allPayMoney = productAnalysisOperationDao.r_queryOther_AllPayMoney(paramMap);
            /** 其他 预定订单的定金 */
            float preFirstMoney = productAnalysisOperationDao.r_queryOther_PreFirstMoney(paramMap);
            money = allPayMoney + preFirstMoney;
          	resultMap.put("TD_PAYMENT_MONEY", money);
          	/**************************************************今日相关统计    end****************************************************/
          	
          	/**************************************************昨日相关统计    begin****************************************************/
          	Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, -1);
			String yd_date = sdf.format(c.getTime());
            paramMap.put("start_date", yd_date);
            paramMap.put("end_date", yd_date);
            /** 其他 商品销售总额(包含尾款定金) */
            allPayMoney = productAnalysisOperationDao.r_queryOther_AllPayMoney(paramMap);
            /** 其他 预定订单的定金 */
            preFirstMoney = productAnalysisOperationDao.r_queryOther_PreFirstMoney(paramMap);
            money = allPayMoney +preFirstMoney;
          	resultMap.put("YD_PAYMENT_MONEY", money);
          	/**************************************************昨日相关统计    end****************************************************/
          	
          	/**************************************************本周相关统计    begin****************************************************/
          	//最近7天 不包含今天
          	c = Calendar.getInstance();
			c.add(Calendar.DATE, -7);
			String tw_date = sdf.format(c.getTime());
            paramMap.put("start_date", tw_date);
            paramMap.put("end_date", yd_date);
            /** 其他 商品销售总额(包含尾款定金) */
            allPayMoney = productAnalysisOperationDao.r_queryOther_AllPayMoney(paramMap);
            /** 其他 预定订单的定金 */
            preFirstMoney = productAnalysisOperationDao.r_queryOther_PreFirstMoney(paramMap);
            money = allPayMoney +preFirstMoney;
          	resultMap.put("TW_PAYMENT_MONEY", money);
          	resultMap.put("START_THIS_WEEK", tw_date);
          	resultMap.put("END_THIS_WEEK", yd_date);
          	/**************************************************本周相关统计    end****************************************************/
          	
          	/**************************************************上周相关统计    begin****************************************************/
          	//最近14天
          	c = Calendar.getInstance();
			c.add(Calendar.DATE, -14);
			String lw_date = sdf.format(c.getTime());
			paramMap.put("start_date", lw_date);
			c = Calendar.getInstance();
			c.add(Calendar.DATE, -8);
			String ttw_date = sdf.format(c.getTime());
            paramMap.put("end_date", ttw_date);
            /** 其他 商品销售总额(包含尾款定金) */
            allPayMoney = productAnalysisOperationDao.r_queryOther_AllPayMoney(paramMap);
            /** 其他 预定订单的定金 */
            preFirstMoney = productAnalysisOperationDao.r_queryOther_PreFirstMoney(paramMap);
            money = allPayMoney +preFirstMoney;
          	resultMap.put("LW_PAYMENT_MONEY", money);
          	resultMap.put("START_LAST_WEEK", lw_date);
          	resultMap.put("END_LAST_WEEK", ttw_date);
          	/**************************************************上周相关统计    end****************************************************/
          	
          	/**************************************************本月相关统计    begin****************************************************/
          	c = Calendar.getInstance();  
            c.add(Calendar.MONTH, 0);  
            c.set(Calendar.DAY_OF_MONTH, 1);  
            String firstday = sdf.format(c.getTime()); 
            paramMap.put("start_date", firstday);
            paramMap.put("end_date", td_date);
            /** 其他 商品销售总额(包含尾款定金) */
            allPayMoney = productAnalysisOperationDao.r_queryOther_AllPayMoney(paramMap);
            /** 其他 预定订单的定金 */
            preFirstMoney = productAnalysisOperationDao.r_queryOther_PreFirstMoney(paramMap);
            money = allPayMoney +preFirstMoney;
          	resultMap.put("TM_PAYMENT_MONEY", money);
            /**************************************************本月相关统计    end****************************************************/
          	
            pr.setState(true);
			pr.setMessage("获取商品概况成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 核心数据分析-商品访客数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryKernelData_ProductVisitorCount(HttpServletRequest request) {
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
        	/** 其他 核心数据分析-商品访客数 */
            t_productVisitorCount = productAnalysisOperationDao.r_queryOther_VisitorCount(paramMap);
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
			l_productVisitorCount = productAnalysisOperationDao.r_queryOther_VisitorCount(paramMap);
            if(t_productVisitorCount == 0 || l_productVisitorCount == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_productVisitorCount-l_productVisitorCount)/l_productVisitorCount*100));
            }
            resultMap.put("p_visitor_count", t_productVisitorCount);
            
            pr.setState(true);
            pr.setMessage("获取商品访客数成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 核心数据分析-商品浏览量
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryKernelData_ProductPvCount(HttpServletRequest request) {
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
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_productPvCount = 0;
        	/** 其他 核心数据分析-商品浏览量 */
            t_productPvCount = productAnalysisOperationDao.r_queryOther_PvCount(paramMap);
        	Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            //较上个周期
			float l_productPvCount = 0;
			l_productPvCount = productAnalysisOperationDao.r_queryOther_PvCount(paramMap);
            if(t_productPvCount == 0 || l_productPvCount == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_productPvCount-l_productPvCount)/l_productPvCount*100));
            }
            resultMap.put("p_pv_count", t_productPvCount);
            
            pr.setState(true);
            pr.setMessage("获取商品浏览量成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 核心数据分析-下单买家数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryKernelData_OPurchaseNumber(HttpServletRequest request) {
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
        	/** 其他 核心数据分析-下单买家数 */
        	t_purchase_number = productAnalysisOperationDao.r_queryOther_OPurchaseNumber(paramMap);
        	Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            //较上个周期
			float l_purchase_number = 0;
            l_purchase_number = productAnalysisOperationDao.r_queryOther_OPurchaseNumber(paramMap);
            if(t_purchase_number == 0 || l_purchase_number == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_purchase_number-l_purchase_number)/l_purchase_number*100));
            }
            resultMap.put("o_purchase_number", t_purchase_number);
            
            pr.setState(true);
            pr.setMessage("获取下单买家数成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 核心数据分析-订单商品件数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryKernelData_OCount(HttpServletRequest request) {
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
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_count = 0;
        	/** 其他 核心数据分析-订单商品件数 */
        	t_count = productAnalysisOperationDao.r_queryOther_OCount(paramMap);
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
            l_count = productAnalysisOperationDao.r_queryOther_OCount(paramMap);
            if(t_count == 0 || l_count == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_count-l_count)/l_count*100));
            }
            resultMap.put("o_count", t_count);
            
            pr.setState(true);
            pr.setMessage("获取订单商品件数成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 核心数据分析-订单商品总额
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryKernelData_OMoney(HttpServletRequest request) {
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
        	/** 其他 核心数据分析-订单商品总额 */
        	t_money = productAnalysisOperationDao.r_queryOther_OMoney(paramMap);
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
            l_money = productAnalysisOperationDao.r_queryOther_OMoney(paramMap);
            if(t_money == 0 || l_money == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_money-l_money)/l_money*100));
            }
            resultMap.put("o_money", t_money);
            
            pr.setState(true);
            pr.setMessage("获取订单商品总额成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 核心数据分析-下单转化率
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryKernelData_OZhl(HttpServletRequest request) {
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
            /** 其他 核心数据分析-商品访客数 */
            t_productVisitorCount = productAnalysisOperationDao.r_queryOther_VisitorCount(paramMap);
            /** 其他 核心数据分析-下单买家数 */
            t_purchase_number = productAnalysisOperationDao.r_queryOther_OPurchaseNumber(paramMap);
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
            /** 其他 核心数据分析-商品访客数 */
            l_productVisitorCount = productAnalysisOperationDao.r_queryOther_VisitorCount(paramMap);
            /** 其他 核心数据分析-下单买家数 */
            l_purchase_number = productAnalysisOperationDao.r_queryOther_OPurchaseNumber(paramMap);
            if(l_productVisitorCount == 0 || l_purchase_number == 0) {
            	l_zhl = 0;
            }else {
            	l_zhl = 100*(l_purchase_number/l_productVisitorCount);
            }
            
            if(t_productVisitorCount == 0 || t_purchase_number == 0) {
            	resultMap.put("ratio", 0);
            	t_zhl = 0;
            }else {
            	t_zhl = 100*(t_purchase_number/t_productVisitorCount);
            	if(l_zhl == 0) {
            		resultMap.put("ratio", 0);
            	}else {
            		resultMap.put("ratio", m2((t_zhl-l_zhl)/l_zhl*100));
            	}
            }
            resultMap.put("o_zhl", t_zhl);
            
            pr.setState(true);
            pr.setMessage("获取下单转化率成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 核心数据分析-支付买家数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryKernelData_PayPurchaseNumber(HttpServletRequest request) {
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
        	/** 其他 核心数据分析-支付买家数 */
        	t_purchase_number = productAnalysisOperationDao.r_queryOther_PayPurchaseNumber(paramMap);
        	Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            //较上个周期
			float l_purchase_number = 0;
            l_purchase_number = productAnalysisOperationDao.r_queryOther_PayPurchaseNumber(paramMap);
            if(t_purchase_number == 0 || l_purchase_number == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_purchase_number-l_purchase_number)/l_purchase_number*100));
            }
            resultMap.put("p_purchase_number", t_purchase_number);
            
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
	 * 核心数据分析-支付件数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryKernelData_PayCount(HttpServletRequest request) {
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
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_count = 0;
        	/** 其他 核心数据分析-支付件数 */
        	t_count = productAnalysisOperationDao.r_queryOther_PayCount(paramMap);
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
            l_count = productAnalysisOperationDao.r_queryOther_PayCount(paramMap);
            if(t_count == 0 || l_count == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_count-l_count)/l_count*100));
            }
            resultMap.put("p_count", t_count);
            
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
	 * 核心数据分析-商品销售总额
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryKernelData_PayMoney(HttpServletRequest request) {
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
        	/** 其他 核心数据分析-商品销售总额 */
        	t_money = productAnalysisOperationDao.r_queryOther_PayMoney(paramMap);
        	/** 其他 核心数据分析-预定订单的定金 */
        	t_preFirstMoney = productAnalysisOperationDao.r_queryOther_PreFirstMoney(paramMap);
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
            l_money = productAnalysisOperationDao.r_queryOther_PayMoney(paramMap);
            l_preFirstMoney = productAnalysisOperationDao.r_queryOther_PreFirstMoney(paramMap);
            l_money+=l_preFirstMoney;
            if(t_money == 0 || l_money == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_money-l_money)/l_money*100));
            }
            resultMap.put("p_money", t_money);
            
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
	 * 核心数据分析-支付转化率
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryKernelData_PayZhl(HttpServletRequest request) {
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
            
            /** 其他 核心数据分析-商品访客数 */
            t_productVisitorCount = productAnalysisOperationDao.r_queryOther_VisitorCount(paramMap);
            /** 其他 核心数据分析-支付买家数 */
            t_purchase_number = productAnalysisOperationDao.r_queryOther_PayPurchaseNumber(paramMap);
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
            /** 其他 核心数据分析-商品访客数 */
            l_productVisitorCount = productAnalysisOperationDao.r_queryOther_VisitorCount(paramMap);
            /** 其他 核心数据分析-支付买家数 */
            l_purchase_number = productAnalysisOperationDao.r_queryOther_PayPurchaseNumber(paramMap);
            if(l_productVisitorCount == 0 || l_purchase_number == 0) {
            	l_zhl = 0;
            }else {
            	l_zhl = 100*(l_purchase_number/l_productVisitorCount);
            }
            
            if(t_productVisitorCount == 0 || t_purchase_number == 0) {
            	resultMap.put("ratio", 0);
            	t_zhl = 0;
            }else {
            	t_zhl = 100*(t_purchase_number/t_productVisitorCount);
            	if(l_zhl == 0) {
            		resultMap.put("ratio", 0);
            	}else {
            		resultMap.put("ratio", m2((t_zhl-l_zhl)/l_zhl*100));
            	}
            }
            resultMap.put("p_zhl", t_zhl);
            
            pr.setState(true);
            pr.setMessage("获取支付转化率成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 核心数据分析-退款商品金额
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryKernelData_ReturnMoney(HttpServletRequest request) {
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
            /** 其他 核心数据分析-退款商品金额 */
            t_money = productAnalysisOperationDao.r_queryOther_ReturnMoney(paramMap);
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
            l_money = productAnalysisOperationDao.r_queryOther_ReturnMoney(paramMap);
            if(t_money == 0 || l_money == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_money-l_money)/l_money*100));
            }
            
            resultMap.put("return_money", t_money);
            
            pr.setState(true);
            pr.setMessage("获取退款商品金额成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 核心数据分析-退款商品数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryKernelData_ReturnCount(HttpServletRequest request) {
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
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_count = 0;
            /** 其他 核心数据分析-退款商品数 */
            t_count = productAnalysisOperationDao.r_queryOther_ReturnCount(paramMap);
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
            l_count = productAnalysisOperationDao.r_queryOther_ReturnCount(paramMap);
            
            if(t_count == 0 || l_count == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_count-l_count)/l_count*100));
            }
            
            resultMap.put("return_count", t_count);
            
            pr.setState(true);
            pr.setMessage("获取退款商品数成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 核心数据分析-商品客单价
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryKernelData_Kdj(HttpServletRequest request) {
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
            
            /** 其他 核心数据分析-商品销售总额 */
            t_money = productAnalysisOperationDao.r_queryOther_AllPayMoney(paramMap);
            /** 其他 核心数据分析-预定订单的定金 */
            t_preFirstMoney = productAnalysisOperationDao.r_queryOther_PreFirstMoney(paramMap);
            /** 其他 核心数据分析-支付买家数 */
            t_purchaseNumber = productAnalysisOperationDao.r_queryOther_PayPurchaseNumber(paramMap);
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
            /** 其他 核心数据分析-商品销售总额 */
            l_money = productAnalysisOperationDao.r_queryOther_AllPayMoney(paramMap);
            /** 其他 核心数据分析-预定订单的定金 */
            l_preFirstMoney = productAnalysisOperationDao.r_queryOther_PreFirstMoney(paramMap);
            /** 其他 核心数据分析-支付买家数 */
            l_purchaseNumber = productAnalysisOperationDao.r_queryOther_PayPurchaseNumber(paramMap);
            l_money+=l_preFirstMoney;
            if(l_purchaseNumber == 0 || l_money == 0) {
            	l_kdj = 0;
            }else {
            	l_kdj = l_money/l_purchaseNumber;
            }
            
            if(t_purchaseNumber == 0 || t_money == 0) {
            	resultMap.put("ratio", 0);
            	t_kdj = 0;
            }else {
            	t_kdj = t_money/t_purchaseNumber;
            	if(l_kdj == 0) {
            		resultMap.put("ratio", 0);
            	}else {
            		resultMap.put("ratio", m2((t_kdj-l_kdj)/l_kdj*100));
            	}
            }
            resultMap.put("kdj", t_kdj);
            
            pr.setState(true);
            pr.setMessage("获取商品客单价成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 核心数据分析-折线
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryKernelDataChart(HttpServletRequest request) {
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
            List<Map<String, Object>> productVisitorPvCountList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> purchaseNumberList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> orderCountMoneyList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> payPurchaseNumberList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> payCountMoneyList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> preFirstMoneyList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
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
            	/** 其他 折线图 商品访客数和商品浏览量 */
            	productVisitorPvCountList = productAnalysisOperationDao.r_queryOther_ProductVisitorPvCountD_Chart(paramMap);
                /** 其他 折线图 下单买家数 */
                purchaseNumberList = productAnalysisOperationDao.r_queryOther_PurchaseNumberD_Chart(paramMap);
                /** 其他 折线图 订单商品件数、订单商品总额 */
                orderCountMoneyList = productAnalysisOperationDao.r_queryOther_OrderCountMoneyD_Chart(paramMap);
                /** 其他 折线图 支付买家数 */
                payPurchaseNumberList = productAnalysisOperationDao.r_queryOther_PayPurchaseNumberD_Chart(paramMap);
                /** 其他 折线图 支付件数、商品销售总额 */
                payCountMoneyList = productAnalysisOperationDao.r_queryOther_PayCountMoneyD_Chart(paramMap);
                /** 其他 折线图 预定订单的定金 */
                preFirstMoneyList = productAnalysisOperationDao.r_queryOther_PreFirstMoneyD_Chart(paramMap);
                /** 其他 折线图 退款商品数和退款商品金额 */
                returnList = productAnalysisOperationDao.r_queryOther_ReturnD_Chart(paramMap);
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
                /** 其他 折线图 商品访客数和商品浏览量 */
                productVisitorPvCountList = productAnalysisOperationDao.r_queryOther_ProductVisitorPvCount_Chart(paramMap);
                /** 其他 折线图 下单买家数 */
                purchaseNumberList = productAnalysisOperationDao.r_queryOther_PurchaseNumber_Chart(paramMap);
                /** 其他 折线图 订单商品件数、订单商品总额 */
                orderCountMoneyList = productAnalysisOperationDao.r_queryOther_OrderCountMoney_Chart(paramMap);
                /** 其他 折线图 支付买家数 */
                payPurchaseNumberList = productAnalysisOperationDao.r_queryOther_PayPurchaseNumber_Chart(paramMap);
                /** 其他 折线图 支付件数、商品销售总额 */
                payCountMoneyList = productAnalysisOperationDao.r_queryOther_PayCountMoney_Chart(paramMap);
                /** 其他 折线图 预定订单的定金 */
                preFirstMoneyList = productAnalysisOperationDao.r_queryOther_PreFirstMoney_Chart(paramMap);
                /** 其他 折线图 退款商品数和退款商品金额 */
                returnList = productAnalysisOperationDao.r_queryOther_Return_Chart(paramMap);
            }
            
            //商品访客数
            Map<String,Object> productVisitorCountMap = list2Map(productVisitorPvCountList,"CREATE_DATE","VISITOR_COUNT");
            //商品浏览量
            Map<String,Object> productPvCountMap = list2Map(productVisitorPvCountList,"CREATE_DATE","PV_COUNT");
            //下单买家数
            Map<String,Object> purchaseNumberMap = list2Map(purchaseNumberList,"CREATE_DATE","PURCHASE_NUMBER");
            //订单商品总额
            Map<String,Object> orderMoneyMap = list2Map(orderCountMoneyList,"CREATE_DATE","ORDER_MONEY");
            //订单商品件数
            Map<String,Object> orderCountMap = list2Map(orderCountMoneyList,"CREATE_DATE","ORDER_COUNT");
            //支付买家数
            Map<String,Object> payPurchaseNumberMap = list2Map(payPurchaseNumberList,"CREATE_DATE","PURCHASE_NUMBER");
            //支付件数
            Map<String,Object> payCountMap = list2Map(payCountMoneyList,"CREATE_DATE","PAY_COUNT");
            //商品销售总额
            Map<String,Object> payMoneyMap = list2Map(payCountMoneyList,"CREATE_DATE","PAY_MONEY");
            //预定订单的定金
            Map<String,Object> preFirstMoneyMap = list2Map(preFirstMoneyList,"CREATE_DATE","PRE_FIRST_MONEY");
            //退款商品金额
            Map<String,Object> returnMoneyMap = list2Map(returnList,"CREATE_DATE","RETURN_MONEY");
            //退款商品数
            Map<String,Object> returnCountMap = list2Map(returnList,"CREATE_DATE","RETURN_COUNT");
            
            //开始数据拼装
          	String key="";
          	
          	Map<String, Object> map = null;
            for(String time : time_list) {
            	key = time;
            	/** 核心数据分析-商品访客数 */
                float productVisitorCount = productVisitorCountMap.get(key)==null?0:Float.parseFloat(productVisitorCountMap.get(key).toString());
                /** 核心数据分析-商品浏览量 */
                float productPvCount = productPvCountMap.get(key)==null?0:Float.parseFloat(productPvCountMap.get(key).toString());
                /** 核心数据分析-下单买家数 */
                float purchaseNumber = purchaseNumberMap.get(key)==null?0:Float.parseFloat(purchaseNumberMap.get(key).toString());
                /** 核心数据分析-订单商品件数 */
                float orderCount = orderCountMap.get(key)==null?0:Float.parseFloat(orderCountMap.get(key).toString());
                /** 核心数据分析-订单商品总额 */
                float orderMoney = orderMoneyMap.get(key)==null?0:Float.parseFloat(orderMoneyMap.get(key).toString());
                /** 核心数据分析-支付买家数 */
                float payPurchaseNumber = payPurchaseNumberMap.get(key)==null?0:Float.parseFloat(payPurchaseNumberMap.get(key).toString());
                /** 核心数据分析-支付件数 */
                float payCount = payCountMap.get(key)==null?0:Float.parseFloat(payCountMap.get(key).toString());
                /** 核心数据分析-商品销售总额 */
                float payMoney = payMoneyMap.get(key)==null?0:Float.parseFloat(payMoneyMap.get(key).toString());
                /** 核心数据分析-预定订单的定金 */
                float preFirstMoney = preFirstMoneyMap.get(key)==null?0:Float.parseFloat(preFirstMoneyMap.get(key).toString());
                /** 核心数据分析-退款商品金额 */
                float returnMoney = returnMoneyMap.get(key)==null?0:Float.parseFloat(returnMoneyMap.get(key).toString());
                /** 核心数据分析-退款商品数 */
                float returnCount = returnCountMap.get(key)==null?0:Float.parseFloat(returnCountMap.get(key).toString());
                payMoney+=preFirstMoney;
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
              	//订单商品件数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", orderCount);
              	map.put("PAGE_NAME", "订单商品件数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//订单商品件数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", orderMoney);
              	map.put("PAGE_NAME", "订单商品总额");
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
              	//支付件数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", payCount);
              	map.put("PAGE_NAME", "支付件数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//商品销售总额
              	map.put("CREATE_DATE", key);
              	map.put("CNT", payMoney);
              	map.put("PAGE_NAME", "商品销售总额");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//退款商品金额
              	map.put("CNT", returnMoney);
              	map.put("CREATE_DATE", key);
              	map.put("PAGE_NAME", "退款商品金额");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//退款商品数
              	map.put("CNT", returnCount);
              	map.put("CREATE_DATE", key);
              	map.put("PAGE_NAME", "退款商品数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//商品客单价
              	if(payPurchaseNumber == 0||payMoney == 0) {
              		map.put("CNT", 0);
              	}else {
              		map.put("CNT", payMoney/payPurchaseNumber);
              	}
              	map.put("CREATE_DATE", key);
              	map.put("PAGE_NAME", "商品客单价");
              	allDataList.add(map);
            }
            
            //将原始数据组装为Echart可识别的格式
            resultMap = createData("PAGE_NAME", time_list, allDataList);
            
            pr.setState(true);
			pr.setMessage("获取核心数据分析成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	
	/**
	 * 商品汇总排行
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductSummaryRank(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("queryNames"))) {
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
			String file_names_pvc = ":P_VISITOR_COUNT:";
			//商品浏览量
			String file_names_ppc = ":P_PV_COUNT:";
			//下单买家数
			String file_names_opn = ":O_PURCHASE_NUMBER:";
			//订单商品件数
			String file_names_oc = ":O_COUNT:";
			//订单商品总额
			String file_names_om = ":O_MONEY:";
			//下单转化率
			String file_names_ozhl = ":O_ZHL:";
			//支付买家数
			String file_names_ppn = ":P_PURCHASE_NUMBER:";
			//支付件数
			String file_names_pc = ":P_COUNT:";
			//商品销售总额
			String file_names_pm = ":P_MONEY:";
			//支付转化率
			String file_names_pzhl = ":P_ZHL:";
			//退款商品数
			String file_names_rc = ":RETURN_COUNT:";
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
					productList=productAnalysisOperationDao.r_queryProductRank_ProductVisitorCount_Product(paramMap);
				}else if(file_names_ppc.indexOf(sort)!=-1) {
					//商品浏览量---获取排序后的商品排行
					productList=productAnalysisOperationDao.r_queryProductRank_ProductPvCount_Product(paramMap);
				}else if(file_names_opn.indexOf(sort)!=-1) {
					//下单买家数---获取排序后的商品排行
					productList=productAnalysisOperationDao.r_queryProductRank_PurchaseNumber_Product(paramMap);
				}else if(file_names_oc.indexOf(sort)!=-1) {
					//订单商品件数---获取排序后的商品排行
					productList=productAnalysisOperationDao.r_queryProductRank_OrderCount_Product(paramMap);
				}else if(file_names_om.indexOf(sort)!=-1) {
					//下单商品总额---获取排序后的商品排行
					productList=productAnalysisOperationDao.r_queryProductRank_OrderMoney_Product(paramMap);
				}else if(file_names_ozhl.indexOf(sort)!=-1) {
					//下单转化率---获取排序后的商品排行
					productList=productAnalysisOperationDao.r_queryProductRank_Zhl_Product(paramMap);
				}else if(file_names_ppn.indexOf(sort)!=-1) {
					//支付买家数---获取排序后的商品排行
					productList=productAnalysisOperationDao.r_queryProductRank_PayPurchaseNumber_Product(paramMap);
				}else if(file_names_pc.indexOf(sort)!=-1) {
					//支付件数---获取排序后的商品排行
					productList=productAnalysisOperationDao.r_queryProductRank_PayCount_Product(paramMap);
				}else if(file_names_pm.indexOf(sort)!=-1) {
					//商品销售总额---获取排序后的商品排行
					productList=productAnalysisOperationDao.r_queryProductRank_PayMoney_Product(paramMap);
				}else if(file_names_pzhl.indexOf(sort)!=-1) {
					//支付转化率---获取排序后的商品排行
					productList=productAnalysisOperationDao.r_queryProductRank_PayZhl_Product(paramMap);
				}else if(file_names_rc.indexOf(sort)!=-1) {
					//退款商品数---获取排序后的商品排行
					productList=productAnalysisOperationDao.r_queryProductRank_ReturnCount_Product(paramMap);
				}else if(file_names_rm.indexOf(sort)!=-1) {
					//退款商品金额---获取排序后的商品排行
					productList=productAnalysisOperationDao.r_queryProductRank_ReturnMoney_Product(paramMap);
				}else {
					pr.setState(false);
					pr.setMessage("未配置的排序字段，请联系管理员【"+paramMap.get("sort").toString()+"】");
					return pr;
				}
			}else{
				//查询默认排序的商品排行
				productList=productAnalysisOperationDao.r_queryProductRankListBy_Default(paramMap);
			}
			
			if(!productList.isEmpty()&&productList.size()>0){
				paramMap.put("productList", productList);
				list = productAnalysisOperationDao.r_queryProductList(paramMap);
			}
			
			if (list != null && list.size() > 0) {
				String queryNames = paramMap.get("queryNames").toString();
				List<Map<String, Object>> productVisitorCountList = new ArrayList<Map<String, Object>>();
				Map<String, Object> productVisitorCountMap = new HashMap<String, Object>();
				if(queryNames.indexOf(file_names_pvc)!=-1) {
					//数据获取-商品访客数,商品浏览量
					productVisitorCountList = productAnalysisOperationDao.r_queryProductVisitorCount(paramMap);
					productVisitorCountMap = list2Map(productVisitorCountList,"ITEMNUMBER","VISITOR_COUNT");
				}
				List<Map<String, Object>> productPvCountList = new ArrayList<Map<String, Object>>();
				Map<String, Object> productPvCountMap = new HashMap<String, Object>();
				if(queryNames.indexOf(file_names_ppc)!=-1) {
					productPvCountList = productAnalysisOperationDao.r_queryProductPvCount(paramMap);
					productPvCountMap = list2Map(productPvCountList,"ITEMNUMBER","PV_COUNT");
				}
				List<Map<String,Object>> purchaseNumberCountMoneyList = new ArrayList<Map<String, Object>>();
				Map<String,Object> purchaseNumberMap =new HashMap<String, Object>();
				Map<String,Object> orderCountMap = new HashMap<String, Object>();
				Map<String,Object> orderMoneyMap = new HashMap<String, Object>();
				if(queryNames.indexOf(file_names_opn)!=-1||queryNames.indexOf(file_names_oc)!=-1||queryNames.indexOf(file_names_om)!=-1||queryNames.indexOf(file_names_ozhl)!=-1) {
					//数据获取-下单买家数、订单商品件数、订单商品总额
					purchaseNumberCountMoneyList = productAnalysisOperationDao.r_queryProduct_PurchaseNumberCountMoney(paramMap);
					purchaseNumberMap = list2Map(purchaseNumberCountMoneyList,"ITEMNUMBER","PURCHASE_NUMBER");
					orderCountMap = list2Map(purchaseNumberCountMoneyList,"ITEMNUMBER","ORDER_COUNT");
					orderMoneyMap = list2Map(purchaseNumberCountMoneyList,"ITEMNUMBER","ORDER_MONEY");
				}
				List<Map<String,Object>> payPurchaseNumberCountMoneyList = new ArrayList<Map<String, Object>>();
				Map<String,Object> payPurchaseNumberMap =new HashMap<String, Object>();
				Map<String,Object> payCountMap = new HashMap<String, Object>();
				Map<String,Object> payMoneyMap = new HashMap<String, Object>();
				List<Map<String,Object>> preFirstMoneyList = new ArrayList<Map<String, Object>>();
				Map<String,Object> preFirstMoneyMap = new HashMap<String, Object>();
				if(queryNames.indexOf(file_names_ppn)!=-1||queryNames.indexOf(file_names_pc)!=-1||queryNames.indexOf(file_names_pm)!=-1||queryNames.indexOf(file_names_pzhl)!=-1) {
					//数据获取-支付买家数、支付件数、商品销售总额
					payPurchaseNumberCountMoneyList = productAnalysisOperationDao.r_queryProduct_PayPurchaseNumberCountMoney(paramMap);
					payPurchaseNumberMap = list2Map(payPurchaseNumberCountMoneyList,"ITEMNUMBER","PURCHASE_NUMBER");
					payCountMap = list2Map(payPurchaseNumberCountMoneyList,"ITEMNUMBER","PAY_COUNT");
					payMoneyMap = list2Map(payPurchaseNumberCountMoneyList,"ITEMNUMBER","PAY_MONEY");
					//数据获取-预定订单的定金
					preFirstMoneyList = productAnalysisOperationDao.r_queryProduct_PreFirstMoney(paramMap);
					preFirstMoneyMap = list2Map(preFirstMoneyList,"ITEMNUMBER","PRE_FIRST_MONEY");
				}
				List<Map<String,Object>> reuturnList = new ArrayList<Map<String, Object>>();
				Map<String,Object> returnMoneyMap =new HashMap<String, Object>();
				Map<String,Object> returnCountMap = new HashMap<String, Object>();
				if(queryNames.indexOf(file_names_rc)!=-1||queryNames.indexOf(file_names_rm)!=-1) {
					//数据获取-退货信息
					reuturnList = productAnalysisOperationDao.r_queryProduct_ReturnInfo(paramMap);
	                returnMoneyMap = list2Map(reuturnList,"ITEMNUMBER","RETURN_MONEY");
	                returnCountMap = list2Map(reuturnList,"ITEMNUMBER","RETURN_COUNT");
				}
				
				
                
				Map<String,Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String itemnumber = tempMap.get("ITEMNUMBER").toString();
					float productVisitorCount = productVisitorCountMap.get(itemnumber)==null?0:Float.parseFloat(productVisitorCountMap.get(itemnumber).toString());
					//商品访客数
					tempMap.put("P_VISITOR_COUNT",productVisitorCount);
					//商品浏览量
					tempMap.put("P_PV_COUNT",productPvCountMap.get(itemnumber)==null?0:Float.parseFloat(productPvCountMap.get(itemnumber).toString()));
					//订单商品件数
					tempMap.put("O_COUNT",orderCountMap.get(itemnumber)==null?0:Float.parseFloat(orderCountMap.get(itemnumber).toString()));
					//订单商品总额
					tempMap.put("O_MONEY",orderMoneyMap.get(itemnumber)==null?0:Float.parseFloat(orderMoneyMap.get(itemnumber).toString()));
					//支付件数
					tempMap.put("P_COUNT",payCountMap.get(itemnumber)==null?0:Float.parseFloat(payCountMap.get(itemnumber).toString()));
					//商品销售总额
					float payMoney = payMoneyMap.get(itemnumber)==null?0:Float.parseFloat(payMoneyMap.get(itemnumber).toString());
					//预定订单的定金
					float preFirstMoney = preFirstMoneyMap.get(itemnumber)==null?0:Float.parseFloat(preFirstMoneyMap.get(itemnumber).toString());
					tempMap.put("P_MONEY",payMoney+preFirstMoney);
					//退款商品数
					tempMap.put("RETURN_COUNT",returnCountMap.get(itemnumber)==null?0:Float.parseFloat(returnCountMap.get(itemnumber).toString()));
					//退款商品金额
					tempMap.put("RETURN_MONEY",returnMoneyMap.get(itemnumber)==null?0:Float.parseFloat(returnMoneyMap.get(itemnumber).toString()));
					
					float purchaseNumber = purchaseNumberMap.get(itemnumber)==null?0:Float.parseFloat(purchaseNumberMap.get(itemnumber).toString());
					//下单买家数
					tempMap.put("O_PURCHASE_NUMBER",purchaseNumber);
					//下单转化率
					if(purchaseNumber == 0 || productVisitorCount == 0) {
						tempMap.put("O_ZHL", "0.00%");
					}else {
						tempMap.put("O_ZHL", m2((purchaseNumber/productVisitorCount)*100)+"%");
					}
					float payPurchaseNumber = payPurchaseNumberMap.get(itemnumber)==null?0:Float.parseFloat(payPurchaseNumberMap.get(itemnumber).toString());
					//支付买家数
					tempMap.put("P_PURCHASE_NUMBER",payPurchaseNumber);
					//支付转化率
					if(payPurchaseNumber == 0 || productVisitorCount == 0) {
						tempMap.put("P_ZHL", "0.00%");
					}else {
						tempMap.put("P_ZHL", m2((payPurchaseNumber/productVisitorCount)*100)+"%");
					}
				}
				pr.setState(true);
				pr.setMessage("获取商品汇总排行成功");
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
	 * 查询销售区域排行
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
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            paramMap.put("num", 10);
            List<Map<String, Object>> provinceList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> cityList = new ArrayList<Map<String, Object>>();
            long count = 0;
            float money = 0;
        	if("2".equals(paramMap.get("type").toString())) {//下单买家数
            	/** 其他 下单买家总数 */
                count = productAnalysisOperationDao.r_queryOther_PurchaseNumberTotal(paramMap);
            	/** 其他 省份 下单买家数 排行榜 */
                provinceList = productAnalysisOperationDao.r_queryOther_PurchaseNumberProvince_Rank(paramMap);
                /** 其他 城市 下单买家数 排行榜 */
                cityList = productAnalysisOperationDao.r_queryOther_PurchaseNumberCity_Rank(paramMap);
            }else {//成交金额
            	/** 其他 成交总金额 */
            	money = productAnalysisOperationDao.r_queryOther_PayMoneyTotal(paramMap);
            	/** 其他 省份 成交金额 排行榜 */
                provinceList = productAnalysisOperationDao.r_queryOther_PayMoneyProvince_Rank(paramMap);
                /** 其他 城市 成交金额 排行榜 */
                cityList = productAnalysisOperationDao.r_queryOther_PayMoneyCity_Rank(paramMap);
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
	 * 查询销售区域明细
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySaleAreaDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("province_id"))||StringUtils.isEmpty(paramMap.get("type"))) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            paramMap.put("num", 10);
            List<Map<String, Object>> resultList = productAnalysisOperationDao.r_queryOther_SaleAreaDetail(paramMap);
            pr.setState(true);
			pr.setMessage("获取销售区域明细成功");
			pr.setObj(resultList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	
	/**
	 * 查询销售区域地图
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
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            List<Map<String, Object>> resultList = productAnalysisOperationDao.r_queryOther_SaleAreaMap(paramMap);
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
	 * 查询商品销售分析-商品列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryProductSaleListForPage(HttpServletRequest request) {
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
			/** 其他 商品汇总排行总数 */
			int total = productAnalysisOperationDao.r_queryOther_ProductSummaryCount(paramMap);
			//商品访客数
			String file_names_pvc = ":P_VISITOR_COUNT:";
			//下单买家数
			String file_names_opn = ":O_PURCHASE_NUMBER:";
			//订单商品件数
			String file_names_oc = ":O_COUNT:";
			//支付买家数
			String file_names_ppn = ":P_PURCHASE_NUMBER:";
			//商品销售总额
			String file_names_pm = ":P_MONEY:";
			//支付转化率
			String file_names_pzhl = ":P_ZHL:";
			List<Map<String, Object>> list=null;
			
			//需要查询的商品列表
			List<String> productList = new ArrayList<String>();
			if((paramMap.containsKey("sort")&&!"".equals(paramMap.get("sort").toString()))
					&&(paramMap.containsKey("sort_by")&&!"".equals(paramMap.get("sort_by").toString()))){
				String sort = ":"+paramMap.get("sort").toString()+":";
				if(file_names_pvc.indexOf(sort)!=-1) {
					//商品访客数---获取排序后的商品列表
					productList=productAnalysisOperationDao.r_queryProductSale_ProductVisitorCount_Product(paramMap);
				}else if(file_names_opn.indexOf(sort)!=-1) {
					//下单买家数---获取排序后的商品列表
					productList=productAnalysisOperationDao.r_queryProductSale_PurchaseNumber_Product(paramMap);
				}else if(file_names_oc.indexOf(sort)!=-1) {
					//订单商品件数---获取排序后的商品列表
					productList=productAnalysisOperationDao.r_queryProductSale_OrderCount_Product(paramMap);
				}else if(file_names_ppn.indexOf(sort)!=-1) {
					//支付买家数---获取排序后的商品列表
					productList=productAnalysisOperationDao.r_queryProductSale_PayPurchaseNumber_Product(paramMap);
				}else if(file_names_pm.indexOf(sort)!=-1) {
					//商品销售总额---获取排序后的商品列表
					productList=productAnalysisOperationDao.r_queryProductSale_PayMoney_Product(paramMap);
				}else if(file_names_pzhl.indexOf(sort)!=-1) {
					//支付转化率---获取排序后的商品列表
					productList=productAnalysisOperationDao.r_queryProductSale_PayZhl_Product(paramMap);
				}else {
					gr.setState(false);
					gr.setMessage("未配置的排序字段，请联系管理员【"+paramMap.get("sort").toString()+"】");
					return gr;
				}
			}else{
				//查询默认排序的商品列表
				productList=productAnalysisOperationDao.r_queryProductSaleListBy_Default(paramMap);
			}
			
			if(!productList.isEmpty()&&productList.size()>0){
				paramMap.put("productList", productList);
				list = productAnalysisOperationDao.r_queryProductList(paramMap);
			}
			
			if (list != null && list.size() > 0) {
				//数据获取-商品访客数
				List<Map<String,Object>> productVisitorCountList = productAnalysisOperationDao.r_queryProductVisitorPvCount(paramMap);
				Map<String,Object> productVisitorCountMap = list2Map(productVisitorCountList,"ITEMNUMBER","VISITOR_COUNT");
				
				List<Map<String,Object>> stockList = productAnalysisOperationDao.queryProductStockCount(productList);//数据获取-商品库存
                Map<String,Object> stockMap = list2Map(stockList,"ITEMNUMBER","STOCK_COUNT");
				
				//数据获取-下单买家数、订单商品件数
				List<Map<String,Object>> purchaseNumberCountList = productAnalysisOperationDao.r_queryProduct_PurchaseNumberCountMoney(paramMap);
				Map<String,Object> purchaseNumberMap = list2Map(purchaseNumberCountList,"ITEMNUMBER","PURCHASE_NUMBER");
				Map<String,Object> orderCountMap = list2Map(purchaseNumberCountList,"ITEMNUMBER","ORDER_COUNT");
				
				//数据获取-支付买家数、支付件数、商品销售总额
				List<Map<String,Object>> payPurchaseNumberCountMoneyList = productAnalysisOperationDao.r_queryProduct_PayPurchaseNumberCountMoney(paramMap);
				Map<String,Object> payPurchaseNumberMap = list2Map(payPurchaseNumberCountMoneyList,"ITEMNUMBER","PURCHASE_NUMBER");
				Map<String,Object> payCountMap = list2Map(payPurchaseNumberCountMoneyList,"ITEMNUMBER","PAY_COUNT");
				Map<String,Object> payMoneyMap = list2Map(payPurchaseNumberCountMoneyList,"ITEMNUMBER","PAY_MONEY");
				
				//数据获取-预定订单的定金
				List<Map<String,Object>> preFirstMoneyList = productAnalysisOperationDao.r_queryProduct_PreFirstMoney(paramMap);
				Map<String,Object> preFirstMoneyMap = list2Map(preFirstMoneyList,"ITEMNUMBER","PRE_FIRST_MONEY");
                
				Map<String,Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String itemnumber = tempMap.get("ITEMNUMBER").toString();
					float payCount = payCountMap.get(itemnumber)==null?0:Float.parseFloat(payCountMap.get(itemnumber).toString());
					float payMoney = payMoneyMap.get(itemnumber)==null?0:Float.parseFloat(payMoneyMap.get(itemnumber).toString());
					//预定订单的定金
					float preFirstMoney = preFirstMoneyMap.get(itemnumber)==null?0:Float.parseFloat(preFirstMoneyMap.get(itemnumber).toString());
					payMoney+=preFirstMoney;
					//销售均价
					if(payCount == 0) {
						tempMap.put("SALE_PRICE",0);
					}else {
						tempMap.put("SALE_PRICE",payMoney/payCount);
					}
					float productVisitorCount = productVisitorCountMap.get(itemnumber)==null?0:Float.parseFloat(productVisitorCountMap.get(itemnumber).toString());
					//商品访客数
					tempMap.put("P_VISITOR_COUNT",productVisitorCount);
					//库存数量
					tempMap.put("STOCK_COUNT", stockMap.get(itemnumber)==null?0:Float.parseFloat(stockMap.get(itemnumber).toString()));
					//下单买家数
					tempMap.put("O_PURCHASE_NUMBER",purchaseNumberMap.get(itemnumber)==null?0:Float.parseFloat(purchaseNumberMap.get(itemnumber).toString()));
					//订单商品件数
					tempMap.put("O_COUNT",orderCountMap.get(itemnumber)==null?0:Float.parseFloat(orderCountMap.get(itemnumber).toString()));
					//商品销售总额
					tempMap.put("P_MONEY",payMoney);
					float payPurchaseNumber = payPurchaseNumberMap.get(itemnumber)==null?0:Float.parseFloat(payPurchaseNumberMap.get(itemnumber).toString());
					//支付买家数
					tempMap.put("P_PURCHASE_NUMBER",payPurchaseNumber);
					//支付转化率
					if(payPurchaseNumber == 0 || productVisitorCount == 0) {
						tempMap.put("P_ZHL", "0.00%");
					}else {
						tempMap.put("P_ZHL", m2((payPurchaseNumber/productVisitorCount)*100)+"%");
					}
				}
				gr.setState(true);
				gr.setMessage("获取商品销售分析-商品列表成功");
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
	 * 查询商品销售分析-折线
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductSaleChart(HttpServletRequest request) {
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
            List<Map<String, Object>> productVisitorPvCountList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> purchaseNumberList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> orderCountMoneyList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> payPurchaseNumberList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> payCountMoneyList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> preFirstMoneyList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
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
            	/** 其他 折线图 商品访客数和商品浏览量 */
            	productVisitorPvCountList = productAnalysisOperationDao.r_queryOther_ProductVisitorPvCountD_Chart(paramMap);
                /** 其他 折线图 下单买家数 */
                purchaseNumberList = productAnalysisOperationDao.r_queryOther_PurchaseNumberD_Chart(paramMap);
                /** 其他 折线图 订单商品件数、订单商品总额 */
                orderCountMoneyList = productAnalysisOperationDao.r_queryOther_OrderCountMoneyD_Chart(paramMap);
                /** 其他 折线图 支付买家数 */
                payPurchaseNumberList = productAnalysisOperationDao.r_queryOther_PayPurchaseNumberD_Chart(paramMap);
                /** 其他 折线图 支付件数、商品销售总额 */
                payCountMoneyList = productAnalysisOperationDao.r_queryOther_PayCountMoneyD_Chart(paramMap);
                /** 其他 折线图 预定订单的定金 */
                preFirstMoneyList = productAnalysisOperationDao.r_queryOther_PreFirstMoneyD_Chart(paramMap);
                /** 其他 折线图 退款商品数和退款商品金额 */
                returnList = productAnalysisOperationDao.r_queryOther_ReturnD_Chart(paramMap);
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
                /** 其他 折线图 商品访客数和商品浏览量 */
                productVisitorPvCountList = productAnalysisOperationDao.r_queryOther_ProductVisitorPvCount_Chart(paramMap);
                /** 其他 折线图 下单买家数 */
                purchaseNumberList = productAnalysisOperationDao.r_queryOther_PurchaseNumber_Chart(paramMap);
                /** 其他 折线图 订单商品件数、订单商品总额 */
                orderCountMoneyList = productAnalysisOperationDao.r_queryOther_OrderCountMoney_Chart(paramMap);
                /** 其他 折线图 支付买家数 */
                payPurchaseNumberList = productAnalysisOperationDao.r_queryOther_PayPurchaseNumber_Chart(paramMap);
                /** 其他 折线图 支付件数、商品销售总额 */
                payCountMoneyList = productAnalysisOperationDao.r_queryOther_PayCountMoney_Chart(paramMap);
                /** 其他 折线图 预定订单的定金 */
                preFirstMoneyList = productAnalysisOperationDao.r_queryOther_PreFirstMoney_Chart(paramMap);
                /** 其他 折线图 退款商品数和退款商品金额 */
                returnList = productAnalysisOperationDao.r_queryOther_Return_Chart(paramMap);
            }
            
            //商品访客数
            Map<String,Object> productVisitorCountMap = list2Map(productVisitorPvCountList,"CREATE_DATE","VISITOR_COUNT");
            //商品浏览量
            Map<String,Object> productPvCountMap = list2Map(productVisitorPvCountList,"CREATE_DATE","PV_COUNT");
            //下单买家数
            Map<String,Object> purchaseNumberMap = list2Map(purchaseNumberList,"CREATE_DATE","PURCHASE_NUMBER");
            //订单商品总额
            Map<String,Object> orderMoneyMap = list2Map(orderCountMoneyList,"CREATE_DATE","ORDER_MONEY");
            //订单商品件数
            Map<String,Object> orderCountMap = list2Map(orderCountMoneyList,"CREATE_DATE","ORDER_COUNT");
            //支付买家数
            Map<String,Object> payPurchaseNumberMap = list2Map(payPurchaseNumberList,"CREATE_DATE","PURCHASE_NUMBER");
            //支付件数
            Map<String,Object> payCountMap = list2Map(payCountMoneyList,"CREATE_DATE","PAY_COUNT");
            //商品销售总额
            Map<String,Object> payMoneyMap = list2Map(payCountMoneyList,"CREATE_DATE","PAY_MONEY");
          //预定订单的定金
            Map<String,Object> preFirstMoneyMap = list2Map(preFirstMoneyList,"CREATE_DATE","PRE_FIRST_MONEY");
            //退款商品金额
            Map<String,Object> returnMoneyMap = list2Map(returnList,"CREATE_DATE","RETURN_COUNT");
            //退款商品数
            Map<String,Object> returnCountMap = list2Map(returnList,"CREATE_DATE","RETURN_MONEY");
            
            //开始数据拼装
          	String key="";
          	
          	Map<String, Object> map = null;
            for(String time : time_list) {
            	key = time;
            	/** 商品销售分析-商品访客数 */
                float productVisitorCount = productVisitorCountMap.get(key)==null?0:Float.parseFloat(productVisitorCountMap.get(key).toString());
                /** 商品销售分析-商品浏览量 */
                float productPvCount = productPvCountMap.get(key)==null?0:Float.parseFloat(productPvCountMap.get(key).toString());
                /** 商品销售分析-下单买家数 */
                float purchaseNumber = purchaseNumberMap.get(key)==null?0:Float.parseFloat(purchaseNumberMap.get(key).toString());
                /** 商品销售分析-订单商品件数 */
                float orderCount = orderCountMap.get(key)==null?0:Float.parseFloat(orderCountMap.get(key).toString());
                /** 商品销售分析-订单商品总额 */
                float orderMoney = orderMoneyMap.get(key)==null?0:Float.parseFloat(orderMoneyMap.get(key).toString());
                /** 商品销售分析-支付买家数 */
                float payPurchaseNumber = payPurchaseNumberMap.get(key)==null?0:Float.parseFloat(payPurchaseNumberMap.get(key).toString());
                /** 商品销售分析-支付件数 */
                float payCount = payCountMap.get(key)==null?0:Float.parseFloat(payCountMap.get(key).toString());
                /** 商品销售分析-商品销售总额 */
                float payMoney = payMoneyMap.get(key)==null?0:Float.parseFloat(payMoneyMap.get(key).toString());
                /** 商品销售分析-预定订单的定金 */
                float preFirstMoney = preFirstMoneyMap.get(key)==null?0:Float.parseFloat(preFirstMoneyMap.get(key).toString());
                /** 商品销售分析-退款商品金额 */
                float returnMoney = returnMoneyMap.get(key)==null?0:Float.parseFloat(returnMoneyMap.get(key).toString());
                /** 商品销售分析-退款商品数 */
                float returnCount = returnCountMap.get(key)==null?0:Float.parseFloat(returnCountMap.get(key).toString());
                payMoney+=preFirstMoney;
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
              	//订单商品件数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", orderCount);
              	map.put("PAGE_NAME", "订单商品件数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//订单商品件数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", orderMoney);
              	map.put("PAGE_NAME", "订单商品总额");
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
              	//支付件数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", payCount);
              	map.put("PAGE_NAME", "支付件数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//商品销售总额
              	map.put("CREATE_DATE", key);
              	map.put("CNT", payMoney);
              	map.put("PAGE_NAME", "商品销售总额");
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
              	//退款商品金额
              	map.put("CNT", returnMoney);
              	map.put("CREATE_DATE", key);
              	map.put("PAGE_NAME", "退款商品金额");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//退款商品数
              	map.put("CNT", returnCount);
            
              	map.put("CREATE_DATE", key);
              	map.put("PAGE_NAME", "退款商品数");
              	allDataList.add(map);
            }
            
            //将原始数据组装为Echart可识别的格式
            resultMap = createData("PAGE_NAME", time_list, allDataList);
            
            pr.setState(true);
			pr.setMessage("获取商品销售分析成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	
	/**
	 * 查询商品销售分析-库存明细
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductSaleStockDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
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
            
            pr.setState(true);
			pr.setMessage("获取商品库存明细成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	
	/**
     * 查询商品退货分析
     * @param request
     * @return
     */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductReturn(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
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
            paramMap.put("jdbc_user", jdbc_user);
            /**************************************************待处理    begin****************************************************/
            Map<String, Object> dclMap = productAnalysisOperationDao.queryPendingDetail(paramMap);
            resultMap.put("dclMap", dclMap);
            /**************************************************待处理    end****************************************************/
          	
          	/**************************************************待买家退货    begin****************************************************/
            //待暂收
            paramMap.put("state", 3);
            Map<String, Object> dmjthMap = productAnalysisOperationDao.queryProductReturnDetail(paramMap);
            resultMap.put("dmjthMap", dmjthMap);
            /**************************************************待买家退货    end****************************************************/
            
            /**************************************************待仓库收货    begin****************************************************/
            //待点数
            paramMap.put("state", 4);
            Map<String, Object> dckshMap = productAnalysisOperationDao.queryProductReturnDetail(paramMap);
            resultMap.put("dckshMap", dckshMap);
            /**************************************************待仓库收货    end****************************************************/
            
            /**************************************************已收货待退款    begin****************************************************/
            Map<String, Object> yshdtkMap = productAnalysisOperationDao.queryTakeDeliveryRefundDetail(paramMap);
            resultMap.put("yshdtkMap", yshdtkMap);
            /**************************************************已收货待退款    end****************************************************/
            
            pr.setState(true);
			pr.setMessage("获取商品退货分析成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	
	/**
	 * 退货数据分析-成功退款笔数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryReturnData_ReturnNum(HttpServletRequest request) {
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
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_returnNum = 0;
        	/** 其他 退货数据分析-成功退款笔数 */
            t_returnNum = productAnalysisOperationDao.r_queryOther_ReturnNum(paramMap);
        	Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            //较上个周期
			float l_returnNum = 0;
			l_returnNum = productAnalysisOperationDao.r_queryOther_ReturnNum(paramMap);
            if(t_returnNum == 0 || l_returnNum == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_returnNum-l_returnNum)/l_returnNum*100));
            }
            resultMap.put("return_num", t_returnNum);
            
            pr.setState(true);
            pr.setMessage("获取成功退款笔数成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 退货数据分析-退款商品数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryReturnData_ReturnCount(HttpServletRequest request) {
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
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_returnCount = 0;
        	/** 其他 退货数据分析-退款商品数 */
            t_returnCount = productAnalysisOperationDao.r_queryOther_ReturnCount(paramMap);
        	Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            //较上个周期
			float l_returnCount = 0;
			l_returnCount = productAnalysisOperationDao.r_queryOther_ReturnCount(paramMap);
            if(t_returnCount == 0 || l_returnCount == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_returnCount-l_returnCount)/l_returnCount*100));
            }
            resultMap.put("return_count", t_returnCount);
            
            pr.setState(true);
            pr.setMessage("获取退款商品数成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 退货数据分析-驳回申请笔数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryReturnData_RejectNum(HttpServletRequest request) {
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
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_rejectNum = 0;
        	/** 其他 退货数据分析-驳回申请笔数 */
            t_rejectNum = productAnalysisOperationDao.r_queryOther_RejectNum(paramMap);
        	Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            //较上个周期
			float l_rejectNum = 0;
			l_rejectNum = productAnalysisOperationDao.r_queryOther_RejectNum(paramMap);
            if(t_rejectNum == 0 || l_rejectNum == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_rejectNum-l_rejectNum)/l_rejectNum*100));
            }
            resultMap.put("reject_num", t_rejectNum);
            
            pr.setState(true);
            pr.setMessage("获取驳回申请笔数成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 退货数据分析-品质退换货率
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryReturnData_Pzthhl(HttpServletRequest request) {
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
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_qualityCount = 0;
            float t_returnCount = 0;
            float t_pzthhl = 0;
        	/** 其他 退货数据分析-品质退换货笔数 */
        	t_qualityCount = productAnalysisOperationDao.r_queryOther_QualityReturnCount(paramMap);
        	/** 其他 退货数据分析-成功退款笔数 */
        	t_returnCount = productAnalysisOperationDao.r_queryOther_ReturnCount(paramMap);
        	
        	Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            //较上个周期
			float l_qualityCount = 0;
            float l_returnCount = 0;
            float l_pzthhl = 0;
			l_qualityCount = productAnalysisOperationDao.r_queryOther_QualityReturnCount(paramMap);
			l_returnCount = productAnalysisOperationDao.r_queryOther_ReturnCount(paramMap);
            if(l_returnCount == 0 || l_qualityCount == 0) {
            	l_pzthhl = l_qualityCount;
            }else {
            	l_pzthhl = 100*(l_qualityCount/l_returnCount);
            }
            
            if(t_returnCount == 0 || t_qualityCount == 0) {
            	resultMap.put("ratio", 0);
            	t_pzthhl = 0;
            }else {
            	t_pzthhl = 100*(t_qualityCount/t_returnCount);
            	if(l_pzthhl == 0) {
            		resultMap.put("ratio", 0);
            	}else {
            		resultMap.put("ratio", m2((t_pzthhl-l_pzthhl)/l_pzthhl*100));
            	}
            }
            resultMap.put("pzthhl", t_pzthhl);
            
            pr.setState(true);
            pr.setMessage("获取品质退换货率成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 退货数据分析-品质退换件数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryReturnData_QualityReturnCount(HttpServletRequest request) {
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
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_returnCount = 0;
        	/** 其他 退货数据分析-品质退换件数 */
            t_returnCount = productAnalysisOperationDao.r_queryOther_QualityReturnCount(paramMap);
        	
        	Calendar c = Calendar.getInstance();
        	c.setTime(startDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("start_date", sdf.format(c.getTime()));
			
			c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DATE, -day);
			paramMap.put("end_date", sdf.format(c.getTime()));
            //较上个周期
			float l_returnCount = 0;
			l_returnCount = productAnalysisOperationDao.r_queryOther_QualityReturnCount(paramMap);
            
            if(t_returnCount == 0 || l_returnCount == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_returnCount-l_returnCount)/l_returnCount*100));
            }
            resultMap.put("quality_return_count", t_returnCount);
            
            pr.setState(true);
            pr.setMessage("获取品质退换件数成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 退货数据分析-折线
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryReturnDataChart(HttpServletRequest request) {
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
            List<Map<String, Object>> returnCountList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> rejectNumList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> qualityReturnCountList = new ArrayList<Map<String, Object>>();
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
            	/** 其他 折线图 成功退款笔数和退款商品数 */
                returnCountList = productAnalysisOperationDao.r_queryOther_ReturnCountD_Chart(paramMap);
                /** 其他 折线图 驳回申请笔数 */
                rejectNumList = productAnalysisOperationDao.r_queryOther_RejectNumD_Chart(paramMap);
                /** 其他 折线图 品质退换货笔数和品质退换件数 */
                qualityReturnCountList = productAnalysisOperationDao.r_queryOther_QualityReturnCountD_Chart(paramMap);
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
                /** 其他 折线图 成功退款笔数和退款商品数 */
                returnCountList = productAnalysisOperationDao.r_queryOther_ReturnCount_Chart(paramMap);
                /** 其他 折线图 驳回申请笔数 */
                rejectNumList = productAnalysisOperationDao.r_queryOther_RejectNum_Chart(paramMap);
                /** 其他 折线图 品质退换货笔数和品质退换件数 */
                qualityReturnCountList = productAnalysisOperationDao.r_queryOther_QualityReturnCount_Chart(paramMap);
            }
            //成功退款笔数
            Map<String,Object> returnNumMap = list2Map(returnCountList,"CREATE_DATE","RETURN_NUM");
            //退款商品数
            Map<String,Object> returnCountMap = list2Map(returnCountList,"CREATE_DATE","RETURN_COUNT");
            //驳回申请笔数
            Map<String,Object> rejectNumMap = list2Map(rejectNumList,"CREATE_DATE","REJECT_NUM");
            //品质退换货笔数
            Map<String,Object> qualityNumMap = list2Map(qualityReturnCountList,"CREATE_DATE","RETURN_NUM");
            //品质退换件数
            Map<String,Object> qualityReturnCountMap = list2Map(qualityReturnCountList,"CREATE_DATE","RETURN_COUNT");
            
            //开始数据拼装
          	String key="";
          	
          	Map<String, Object> map = null;
            for(String time : time_list) {
            	key = time;
            	/** 退货数据分析-成功退款笔数 */
                float returnNum = returnNumMap.get(key)==null?0:Float.parseFloat(returnNumMap.get(key).toString());
                /** 退货数据分析-退款商品数 */
                float returnCount = returnCountMap.get(key)==null?0:Float.parseFloat(returnCountMap.get(key).toString());
                /** 退货数据分析-驳回申请笔数 */
                float rejectNum = rejectNumMap.get(key)==null?0:Float.parseFloat(rejectNumMap.get(key).toString());
                /** 退货数据分析-品质退换货笔数 */
                float qualityNum = qualityNumMap.get(key)==null?0:Float.parseFloat(qualityNumMap.get(key).toString());
                /** 退货数据分析-品质退换件数 */
                float qualityReturnCount = qualityReturnCountMap.get(key)==null?0:Float.parseFloat(qualityReturnCountMap.get(key).toString());
                
                map = new HashMap<String, Object>();
                //成功退款笔数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", returnNum);
              	map.put("PAGE_NAME", "成功退款笔数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//退款商品数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", returnCount);
              	map.put("PAGE_NAME", "退款商品数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//驳回申请笔数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", rejectNum);
              	map.put("PAGE_NAME", "驳回申请笔数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//品质退换货率
              	map.put("CREATE_DATE", key);
              	if(returnCount==0||qualityNum==0){
              		map.put("CNT", 0);
              	}else{
              		float pzthhl = 100*(qualityNum/returnCount);
              		map.put("CNT", m2(pzthhl));
              	}
              	map.put("PAGE_NAME", "品质退换货率");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//品质退换件数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", qualityReturnCount);
              	map.put("PAGE_NAME", "品质退换件数");
              	allDataList.add(map);
            }
            
            //将原始数据组装为Echart可识别的格式
            resultMap = createData("PAGE_NAME", time_list, allDataList);
            
            pr.setState(true);
			pr.setMessage("获取成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 商品退货分析-商品排行
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryProductReturnListForPage(HttpServletRequest request) {
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
			paramMap.put("jdbc_user", jdbc_user);
			String file_names_return = "STAY_PRODUCT_COUNT";//待退货数
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			List<String> list = new ArrayList<String>();
			//商品总数
			int total = productAnalysisOperationDao.r_queryOther_ReturnProductRankCount(paramMap);
			String sort = "";
			if(!StringUtils.isEmpty(paramMap.get("sort"))) {
				sort = paramMap.get("sort").toString();
			}
			if(file_names_return.equals(sort)) {
				//根据待退货数排序
				resultList = productAnalysisOperationDao.queryReturnStayProductCountListForPage(paramMap);
				if(resultList != null && resultList.size()>0) {
					for(Map<String, Object> r : resultList) {
	                 	list.add(r.get("ITEMNUMBER").toString());
	                }
					paramMap.put("list", list);
					List<Map<String,Object>> reuturn_list = productAnalysisOperationDao.r_queryOther_ProductReturnInfo(paramMap);//数据获取-退货信息
	                Map<String,Object> saleMoneyMap = list2Map(reuturn_list,"ITEMNUMBER","SALE_MONEY");
	                Map<String,Object> saleCountMap = list2Map(reuturn_list,"ITEMNUMBER","SALE_COUNT");
	                Map<String,Object> returnMoneyMap = list2Map(reuturn_list,"ITEMNUMBER","RETURN_MONEY");
	                Map<String,Object> returnCountMap = list2Map(reuturn_list,"ITEMNUMBER","RETURN_COUNT");
	                
	                for(Map<String, Object> r :resultList) {
	                	//销量
	                	float saleCount = saleCountMap.get(r.get("ITEMNUMBER").toString())==null?0:Float.parseFloat(saleCountMap.get(r.get("ITEMNUMBER").toString()).toString());
	                	//退款商品数
	                	float returnCount = returnCountMap.get(r.get("ITEMNUMBER").toString())==null?0:Float.parseFloat(returnCountMap.get(r.get("ITEMNUMBER").toString()).toString());
	                	//退货占比
	                	if(saleCount==0||returnCount==0){
	                  		r.put("RETURN_RATIO", 0);
	                  	}else{
	                  		float return_ratio = 100*(returnCount/saleCount);
	                  		r.put("RETURN_RATIO", return_ratio);
	                  	}
	                	r.put("SALE_MONEY", saleMoneyMap.get(r.get("ITEMNUMBER").toString())==null?0:Float.parseFloat(saleMoneyMap.get(r.get("ITEMNUMBER").toString()).toString()));
	                	r.put("SALE_COUNT", saleCount);
	                	r.put("RETURN_MONEY", returnMoneyMap.get(r.get("ITEMNUMBER").toString())==null?0:Float.parseFloat(returnMoneyMap.get(r.get("ITEMNUMBER").toString()).toString()));
	                	r.put("RETURN_COUNT", returnCount);
	                }
					gr.setState(true);
	    			gr.setMessage("获取商品列表成功");
	    			gr.setObj(resultList);
	    			gr.setTotal(total);
				}else {
					gr.setState(true);
					gr.setMessage("无数据");
				}
			}else {
				//默认-商品列表
				resultList = productAnalysisOperationDao.r_queryOther_ReturnProductListForPage(paramMap);
				if(resultList != null && resultList.size()>0) {
					for(Map<String, Object> r : resultList) {
	                 	list.add(r.get("ITEMNUMBER").toString());
	                }
					paramMap.put("list", list);
					List<Map<String,Object>> stay_list = productAnalysisOperationDao.queryReturnStayProductCount(paramMap);//数据获取-待退货数
					Map<String,Object> stayMap = list2Map(stay_list,"ITEMNUMBER","STAY_PRODUCT_COUNT");
					
					for(Map<String, Object> r :resultList) {
	                	r.put("STAY_PRODUCT_COUNT", stayMap.get(r.get("ITEMNUMBER").toString())==null?0:Float.parseFloat(stayMap.get(r.get("ITEMNUMBER").toString()).toString()));
	                }
					gr.setState(true);
	    			gr.setMessage("获取商品列表成功");
	    			gr.setObj(resultList);
	    			gr.setTotal(total);
	            }else {
	            	gr.setState(true);
					gr.setMessage("无数据");
				}
			}
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.error(e);
        }
        return gr;
	}
	/**
	 * 商品退货分析-商品详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductReturnDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("itemnumber"))) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            paramMap.put("num", 5);
            /** 退货原因分析 */
            List<Map<String, Object>> reasonList = productAnalysisOperationDao.r_queryOther_ProductSingleAfterSale_Chart(paramMap);
            
        	/** 品质退换货商品总数 */
        	float total = productAnalysisOperationDao.r_queryReturnQualityCount(paramMap);
            /** 品质退换货分析 */
        	List<Map<String, Object>> qualityList = productAnalysisOperationDao.r_queryReturnQualityList(paramMap);
            
        	for(Map<String, Object> q :qualityList) {
             	//问题原因 商品数
             	float productCount = Float.parseFloat(q.get("PRODUCT_COUNT").toString());
             	//占比
             	if(total==0||productCount==0){
               		q.put("RATIO", 0);
               	}else{
               		float ratio = 100*(productCount/total);
               		q.put("RATIO", ratio);
               	}
            }
        	
        	retMap.put("reasonList", reasonList);
        	retMap.put("qualityList", qualityList);
            pr.setState(true);
			pr.setMessage("获取商品退货详情成功");
			pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}	
	
	/**
	 * 商品退货分析-退货原因分析
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryReturnReason(HttpServletRequest request) {
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
            /** 退货原因分析 */
            List<Map<String, Object>> resultList = productAnalysisOperationDao.r_queryOther_ProductSingleAfterSale_Chart(paramMap);
            
            pr.setState(true);
			pr.setMessage("获取退货原因分析成功");
			pr.setObj(resultList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	
	/**
	 * 商品退货分析-品质退换货分析
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryReturnQuality(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> retMap = new HashMap<String, Object>();
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
            /** 品质退换货商品总数 */
            float total = productAnalysisOperationDao.r_queryReturnQualityCount(paramMap);
            /** 品质退换货分析 */
            List<Map<String, Object>> resultList = productAnalysisOperationDao.r_queryReturnQualityList(paramMap);
          
            for(Map<String, Object> r :resultList) {
            	//问题原因 商品数
            	float productCount = Float.parseFloat(r.get("PRODUCT_COUNT").toString());
            	//占比
            	if(total==0||productCount==0){
              		r.put("RATIO", 0);
              	}else{
              		float ratio = 100*(productCount/total);
              		r.put("RATIO", ratio);
              	}
            }
            retMap.put("dataList", resultList);
            pr.setState(true);
			pr.setMessage("获取品质退换货分析成功");
			pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 商品退货分析-退货商家排行
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryReturnStationedRank(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> retMap = new HashMap<String, Object>();
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
            /** 退货商家排行 */
            List<Map<String, Object>> resultList = productAnalysisOperationDao.r_queryReturnStationed_Rank(paramMap);
           
            retMap.put("dataList", resultList);
            pr.setState(true);
			pr.setMessage("获取退货商家排行成功");
			pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 商品退货分析-退货品牌排行
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryReturnBrandRank(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> retMap = new HashMap<String, Object>();
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
            /** 退货品牌排行 */
            List<Map<String, Object>> resultList = productAnalysisOperationDao.r_queryReturnBrand_Rank(paramMap);
            
            retMap.put("dataList", resultList);
            pr.setState(true);
			pr.setMessage("获取退货品牌排行成功");
			pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	
	
	/**
	 * 单品分析搜索
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductSingleSearch(HttpServletRequest request) {
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
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            /** 单品分析搜索列表 最多10条 */
            List<Map<String, Object>> resultList = productAnalysisOperationDao.r_queryProductSingleSearch(paramMap);
            
            pr.setState(true);
			pr.setMessage("搜索成功");
			pr.setObj(resultList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}

	/**
	 * 查询单品分析列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryProductSingleListForPage(HttpServletRequest request) {
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
			int total = productAnalysisOperationDao.r_queryProductSingleCount(paramMap);
			
			//销售价
			String file_names_msp = ":MAX_SALE_PRICE:";
			//累计销量
			String file_names_sc = ":SALE_COUNT:";
			//剩余库存
//			String file_names_stc = ":STOCK_COUNT:";
			//发布时间
			String file_names_pd = ":FIRST_SELL_STATE_DATE:";
			
			List<Map<String, Object>> list=null;
			//需要查询的商品列表
			List<String> productList = new ArrayList<String>();
			if((paramMap.containsKey("sort")&&!"".equals(paramMap.get("sort").toString()))
					&&(paramMap.containsKey("sort_by")&&!"".equals(paramMap.get("sort_by").toString()))){
				String sort = ":"+paramMap.get("sort").toString()+":";
				if(file_names_msp.indexOf(sort)!=-1) {
					//销售价---获取排序后的商品信息
					productList=productAnalysisOperationDao.querySalePrice_Product(paramMap);
				}else if(file_names_sc.indexOf(sort)!=-1) {
					//累计销量---获取排序后的商品信息
					productList=productAnalysisOperationDao.r_querySaleCount_Product(paramMap);
				}
//				else if(file_names_stc.indexOf(sort)!=-1) {
//					//剩余库存---获取排序后的用户信息
//					productList=productAnalysisOperationDao.queryStockCount_Product(paramMap);
//				}
				else if(file_names_pd.indexOf(sort)!=-1) {
					//发布时间---获取排序后的用户信息
					productList=productAnalysisOperationDao.r_queryPublishDate_Product(paramMap);
				}else {
					gr.setState(false);
					gr.setMessage("未配置的排序字段，请联系管理员【"+paramMap.get("sort").toString()+"】");
					return gr;
				}
			}else{
				//查询默认排序的商品信息
				productList=productAnalysisOperationDao.r_queryProductSingleListBy_Default(paramMap);
			}
			
			if(!productList.isEmpty()&&productList.size()>0){
				paramMap.put("productList", productList);
				list = productAnalysisOperationDao.r_queryProductSingleList(paramMap);
			}
            
			if (list != null && list.size() > 0) {
				//数据获取-销售价
				List<Map<String,Object>> salePriceList = productAnalysisOperationDao.querySalePrice(paramMap);
				Map<String,Object> minSalePriceMap = list2Map(salePriceList,"ITEMNUMBER","MIN_SALE_PRICE");
				Map<String,Object> maxSalePriceMap = list2Map(salePriceList,"ITEMNUMBER","MAX_SALE_PRICE");
				//数据获取-累计销量
				List<Map<String,Object>> saleCountList = productAnalysisOperationDao.r_querySaleCount(paramMap);
				Map<String,Object> saleCountMap = list2Map(saleCountList,"ITEMNUMBER","SALE_COUNT");
				//数据获取-剩余库存
//				List<Map<String,Object>> stockCountList = productAnalysisOperationDao.queryStockCount(paramMap);
//				Map<String,Object> stockCountMap = list2Map(stockCountList,"ITEMNUMBER","STOCK_COUNT");
				
				Map<String,Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String itemnumber = tempMap.get("ITEMNUMBER").toString();
					tempMap.put("MIN_SALE_PRICE",minSalePriceMap.get(itemnumber)==null?0:Float.parseFloat(minSalePriceMap.get(itemnumber).toString()));
					tempMap.put("MAX_SALE_PRICE",maxSalePriceMap.get(itemnumber)==null?0:Float.parseFloat(maxSalePriceMap.get(itemnumber).toString()));
					tempMap.put("SALE_COUNT",saleCountMap.get(itemnumber)==null?0:Float.parseFloat(saleCountMap.get(itemnumber).toString()));
					//tempMap.put("STOCK_COUNT",stockCountMap.get(itemnumber)==null?0:Float.parseFloat(stockCountMap.get(itemnumber).toString()));
				}
				List<Map<String,Object>> stock_list = productAnalysisOperationDao.queryProductStockCount(productList);//数据获取-商品库存
    	        Map<String,Object> stock_map = list2Map(stock_list,"ITEMNUMBER","STOCK_COUNT");
    	        for(Map<String, Object> d : list) {
    	        	d.put("STOCK_COUNT", stock_map.get(d.get("ITEMNUMBER").toString())==null?0:Float.parseFloat(stock_map.get(d.get("ITEMNUMBER").toString()).toString()));
    	        }
				gr.setState(true);
				gr.setMessage("查询单品分析列表成功");
				gr.setObj(list);
				gr.setTotal(total);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return gr;
	}
	
	/**
	 * 单品分析-商品信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySingleProductInfo(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("itemnumber"))) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            List<String> productList = new ArrayList<String>();
            productList.add(paramMap.get("itemnumber").toString());
            paramMap.put("productList", productList);
            /** 获取销售价 */
			List<Map<String,Object>> salePriceList = productAnalysisOperationDao.querySalePrice(paramMap);
            
            /** 单品分析-商品信息 */
            Map<String, Object> resultMap = productAnalysisOperationDao.r_queryOther_SignleProductInfo(paramMap);
            if(salePriceList != null && salePriceList.size() > 0) {
            	resultMap.put("MIN_SALE_PRICE", salePriceList.get(0).get("MIN_SALE_PRICE"));
            	resultMap.put("MAX_SALE_PRICE", salePriceList.get(0).get("MAX_SALE_PRICE"));
            }else {
            	resultMap.put("MIN_SALE_PRICE", 0);
            	resultMap.put("MAX_SALE_PRICE", 0);
            }
            pr.setState(true);
			pr.setMessage("获取单品分析商品信息成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	
	/**
	 * 查询单品分析详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductSingleDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> retMap = new HashMap<String, Object>();
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
            /** 单品分析-商品总销量 */
            float saleTotalCount = productAnalysisOperationDao.r_queryOther_SignleProductTotalSaleCount(paramMap);
            /** 单品分析-按颜色统计 */
            List<Map<String, Object>> colorList = productAnalysisOperationDao.r_queryOther_ProductSignleColorList(paramMap);
            /** 单品分析-按规格统计 */
            List<Map<String, Object>> specsList = productAnalysisOperationDao.r_queryOther_ProductSignleSpecsList(paramMap);
            /** 单品分析-商品销量sku明细 */
            List<Map<String, Object>> skuList = productAnalysisOperationDao.r_queryOther_ProductSignleSkuList(paramMap);
            
            //总销量
            for(Map<String, Object> color : colorList) {
            	float saleCount = Float.parseFloat(color.get("SALE_COUNT")+"");
            	//占比
            	if(saleTotalCount==0||saleCount==0){
            		color.put("RATIO", 0);
              	}else{
              		color.put("RATIO",  100*saleCount/saleTotalCount);
              	}
            }
            retMap.put("colorList", colorList);
            for(Map<String, Object> specs : specsList) {
            	float saleCount = Float.parseFloat(specs.get("SALE_COUNT")+"");
            	//占比
            	if(saleTotalCount==0||saleCount==0){
            		specs.put("RATIO", 0);
              	}else{
              		specs.put("RATIO",  100*saleCount/saleTotalCount);
              	}
            }
            retMap.put("specsList", specsList);
            if(skuList != null && skuList.size() > 0) {
            	List<String> list = new ArrayList<String>();
            	for(Map<String, Object> s : skuList) {
            		list.add(s.get("PRODUCT_SKU_ID").toString());
            	}
            	
            	List<Map<String, Object>> stock_list = productAnalysisOperationDao.queryProductSkuStockCount(list);
            	Map<String, Object> stock_map = list2Map(stock_list, "PRODUCT_SKU", "STOCK_COUNT");
            	for(Map<String, Object> s : skuList) {
            		s.put("STOCK_COUNT", stock_map.get(s.get("PRODUCT_SKU_ID").toString())==null?0:Float.parseFloat(stock_map.get(s.get("PRODUCT_SKU_ID").toString()).toString()));
            	}
            }
            retMap.put("skuList", skuList);
            
            pr.setState(true);
			pr.setMessage("获取单品分析详情成功");
			pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	
	/**
	 * 查询单品分析-销量分析-折线
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySingleSaleVolumeChart(HttpServletRequest request) {
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
            List<Map<String, Object>> productVisitorPvCountList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> purchaseNumberList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> orderCountMoneyList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> payPurchaseNumberList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> payCountMoneyList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> preFirstMoneyList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
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
            	/** 其他 折线图 商品访客数和商品浏览量 */
                productVisitorPvCountList = productAnalysisOperationDao.r_queryOther_ProductVisitorPvCountD_Chart(paramMap);
                /** 其他 折线图 下单买家数 */
                purchaseNumberList = productAnalysisOperationDao.r_queryOther_PurchaseNumberD_Chart(paramMap);
                /** 其他 折线图 订单商品件数、订单商品总额 */
                orderCountMoneyList = productAnalysisOperationDao.r_queryOther_OrderCountMoneyD_Chart(paramMap);
                /** 其他 折线图 支付买家数 */
                payPurchaseNumberList = productAnalysisOperationDao.r_queryOther_PayPurchaseNumberD_Chart(paramMap);
                /** 其他 折线图 支付件数、商品销售总额 */
                payCountMoneyList = productAnalysisOperationDao.r_queryOther_PayCountMoneyD_Chart(paramMap);
                /** 其他 折线图 预定订单的定金 */
                preFirstMoneyList = productAnalysisOperationDao.r_queryOther_PreFirstMoneyD_Chart(paramMap);
                /** 其他 折线图 退款商品数和退款商品金额 */
                returnList = productAnalysisOperationDao.r_queryOther_ReturnD_Chart(paramMap);
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
                /** 其他 折线图 商品访客数和商品浏览量 */
                productVisitorPvCountList = productAnalysisOperationDao.r_queryOther_ProductVisitorPvCount_Chart(paramMap);
                /** 其他 折线图 下单买家数 */
                purchaseNumberList = productAnalysisOperationDao.r_queryOther_PurchaseNumber_Chart(paramMap);
                /** 其他 折线图 订单商品件数、订单商品总额 */
                orderCountMoneyList = productAnalysisOperationDao.r_queryOther_OrderCountMoney_Chart(paramMap);
                /** 其他 折线图 支付买家数 */
                payPurchaseNumberList = productAnalysisOperationDao.r_queryOther_PayPurchaseNumber_Chart(paramMap);
                /** 其他 折线图 支付件数、商品销售总额 */
                payCountMoneyList = productAnalysisOperationDao.r_queryOther_PayCountMoney_Chart(paramMap);
                /** 其他 折线图 预定订单的定金 */
                preFirstMoneyList = productAnalysisOperationDao.r_queryOther_PreFirstMoney_Chart(paramMap);
                /** 其他 折线图 退款商品数和退款商品金额 */
                returnList = productAnalysisOperationDao.r_queryOther_Return_Chart(paramMap);
            }
        	
            //商品访客数
            Map<String,Object> productVisitorCountMap = list2Map(productVisitorPvCountList,"CREATE_DATE","VISITOR_COUNT");
            //商品浏览量
            Map<String,Object> productPvCountMap = list2Map(productVisitorPvCountList,"CREATE_DATE","PV_COUNT");
            //下单买家数
            Map<String,Object> purchaseNumberMap = list2Map(purchaseNumberList,"CREATE_DATE","PURCHASE_NUMBER");
            //订单商品总额
            Map<String,Object> orderMoneyMap = list2Map(orderCountMoneyList,"CREATE_DATE","ORDER_MONEY");
            //订单商品件数
            Map<String,Object> orderCountMap = list2Map(orderCountMoneyList,"CREATE_DATE","ORDER_COUNT");
            //支付买家数
            Map<String,Object> payPurchaseNumberMap = list2Map(payPurchaseNumberList,"CREATE_DATE","PURCHASE_NUMBER");
            //支付件数
            Map<String,Object> payCountMap = list2Map(payCountMoneyList,"CREATE_DATE","PAY_COUNT");
            //商品销售总额
            Map<String,Object> payMoneyMap = list2Map(payCountMoneyList,"CREATE_DATE","PAY_MONEY");
            //预定订单的定金
            Map<String,Object> preFirstMoneyMap = list2Map(preFirstMoneyList,"CREATE_DATE","PRE_FIRST_MONEY");
            //退款商品金额
            Map<String,Object> returnMoneyMap = list2Map(returnList,"CREATE_DATE","RETURN_MONEY");
            //退款商品数
            Map<String,Object> returnCountMap = list2Map(returnList,"CREATE_DATE","RETURN_COUNT");
            
            //开始数据拼装
          	String key="";
          	
          	Map<String, Object> map = null;
            for(String time : time_list) {
            	key = time;
            	/** 销量分析-商品访客数 */
                float productVisitorCount = productVisitorCountMap.get(key)==null?0:Float.parseFloat(productVisitorCountMap.get(key).toString());
                /** 销量分析-商品浏览量 */
                float productPvCount = productPvCountMap.get(key)==null?0:Float.parseFloat(productPvCountMap.get(key).toString());
                /** 销量分析-下单买家数 */
                float purchaseNumber = purchaseNumberMap.get(key)==null?0:Float.parseFloat(purchaseNumberMap.get(key).toString());
                /** 销量分析-订单商品件数 */
                float orderCount = orderCountMap.get(key)==null?0:Float.parseFloat(orderCountMap.get(key).toString());
                /** 销量分析-订单商品总额 */
                float orderMoney = orderMoneyMap.get(key)==null?0:Float.parseFloat(orderMoneyMap.get(key).toString());
                /** 销量分析-支付买家数 */
                float payPurchaseNumber = payPurchaseNumberMap.get(key)==null?0:Float.parseFloat(payPurchaseNumberMap.get(key).toString());
                /** 销量分析-支付件数 */
                float payCount = payCountMap.get(key)==null?0:Float.parseFloat(payCountMap.get(key).toString());
                /** 销量分析-商品销售总额 */
                float payMoney = payMoneyMap.get(key)==null?0:Float.parseFloat(payMoneyMap.get(key).toString());
                /** 销量分析-预定订单的定金 */
                float preFirstMoney = preFirstMoneyMap.get(key)==null?0:Float.parseFloat(preFirstMoneyMap.get(key).toString());
                /** 销量分析-退款商品金额 */
                float returnMoney = returnMoneyMap.get(key)==null?0:Float.parseFloat(returnMoneyMap.get(key).toString());
                /** 销量分析-退款商品数 */
                float returnCount = returnCountMap.get(key)==null?0:Float.parseFloat(returnCountMap.get(key).toString());
                payMoney+=preFirstMoney;
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
              	//订单商品件数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", orderCount);
              	map.put("PAGE_NAME", "订单商品件数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//订单商品件数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", orderMoney);
              	map.put("PAGE_NAME", "订单商品总额");
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
              	//支付件数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", payCount);
              	map.put("PAGE_NAME", "支付件数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//商品销售总额
              	map.put("CREATE_DATE", key);
              	map.put("CNT", payMoney);
              	map.put("PAGE_NAME", "商品销售总额");
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
              	//退款商品金额
              	map.put("CNT", returnMoney);
              	map.put("CREATE_DATE", key);
              	map.put("PAGE_NAME", "退款商品金额");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//退款商品数
              	map.put("CNT", returnCount);
              	map.put("CREATE_DATE", key);
              	map.put("PAGE_NAME", "退款商品数");
              	allDataList.add(map);
            }
            
            //将原始数据组装为Echart可识别的格式
            resultMap = createData("PAGE_NAME", time_list, allDataList);
            
            pr.setState(true);
			pr.setMessage("获取销量分析成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	
	/**
	 * 查询单品分析-销售趋势
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductSingleTrend(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("product_sku"))) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            List<Map<String, Object>> allDataList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> payCountList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> returnCountList = new ArrayList<Map<String, Object>>();
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
            	
            	/** 其他 折线图 sku支付件数 */
                payCountList = productAnalysisOperationDao.r_queryOther_SkuPayCountD_Chart(paramMap);
                /** 其他 折线图 sku退款商品数 */
                returnCountList = productAnalysisOperationDao.r_queryOther_SkuReturnCountD_Chart(paramMap);
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
                /** 其他 折线图 sku支付件数 */
                payCountList = productAnalysisOperationDao.r_queryOther_SkuPayCount_Chart(paramMap);
                /** 其他 折线图 sku退款商品数 */
                returnCountList = productAnalysisOperationDao.r_queryOther_SkuReturnCount_Chart(paramMap);
            }
        	
            //支付件数
            Map<String,Object> payCountMap = list2Map(payCountList,"CREATE_DATE","PAY_COUNT");
            //退款商品数
            Map<String,Object> returnCountMap = list2Map(returnCountList,"CREATE_DATE","RETURN_COUNT");
            
            //开始数据拼装
          	String key="";
          	
          	Map<String, Object> map = null;
            for(String time : time_list) {
            	key = time;
                /** 销售趋势-支付件数 */
                float payCount = payCountMap.get(key)==null?0:Float.parseFloat(payCountMap.get(key).toString());
                /** 销售趋势-退款商品数 */
                float returnCount = returnCountMap.get(key)==null?0:Float.parseFloat(returnCountMap.get(key).toString());
                
              	
              	map = new HashMap<String, Object>();
              	//支付件数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", payCount);
              	map.put("PAGE_NAME", "支付件数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//退款商品数
              	map.put("CNT", returnCount);
              	map.put("CREATE_DATE", key);
              	map.put("PAGE_NAME", "退款商品数");
              	allDataList.add(map);
            }
            
            //将原始数据组装为Echart可识别的格式
            resultMap = createData("PAGE_NAME", time_list, allDataList);
            
            pr.setState(true);
			pr.setMessage("获取sku销售趋势成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	
	/**
	 * 查询单品分析-销售区域分析
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryProductSingleSaleArea(HttpServletRequest request) {
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
            //销售区域列表总数
            int total = productAnalysisOperationDao.r_queryOther_ProductSingleSaleAreaCount(paramMap);
            /** 其他 单品分析-销售区域分析 */
            List<Map<String, Object>> resultList = productAnalysisOperationDao.r_queryOther_ProductSingleSaleArea(paramMap);
        	if(resultList != null && resultList.size() > 0) {
        		gr.setState(true);
    			gr.setMessage("获取销售区域分析成功");
    			gr.setTotal(total);
    			gr.setObj(resultList);
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
	 * 查询单品分析-销售区域地图
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySingleSaleAreaMap(HttpServletRequest request) {
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
            List<Map<String, Object>> resultList = productAnalysisOperationDao.r_queryOther_SingleSaleAreaMap(paramMap);
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
     * 查询单品分析-销售区域分析-sku销量明细
     * @param request
     * @return
     */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductSingleSaleDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("itemnumber"))||StringUtils.isEmpty(paramMap.get("province_id"))
            		||StringUtils.isEmpty(paramMap.get("city_id"))||StringUtils.isEmpty(paramMap.get("start_date"))||StringUtils.isEmpty(paramMap.get("end_date"))) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            paramMap.put("num", 10);
            /** sku销量明细 */
            List<Map<String, Object>> skuList = productAnalysisOperationDao.r_queryOther_SkuSaleDetail(paramMap);
            
            pr.setState(true);
			pr.setMessage("获取sku销量明细成功");
			pr.setObj(skuList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 查询单品分析-售后分析
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductSingleAfterSale(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("itemnumber"))) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            List<String> ids = homeAnalysisService.queryByUserId(paramMap);
            paramMap.put("ids", ids);
            List<String> typeIds = homeAnalysisService.queryByProductTypeId(paramMap);
            paramMap.put("typeIds", typeIds);
            paramMap.put("num", 5);
            /** 售后汇总 */
            List<Map<String, Object>> reasonList = productAnalysisOperationDao.r_queryOther_ProductSingleAfterSale_Chart(paramMap);
            
        	/** 品质退换货商品总数 */
        	float total = productAnalysisOperationDao.r_queryReturnQualityCount(paramMap);
            /** 品质退换货分析 */
        	List<Map<String, Object>> qualityList = productAnalysisOperationDao.r_queryReturnQualityList(paramMap);
            
        	for(Map<String, Object> q :qualityList) {
             	//问题原因 商品数
             	float productCount = Float.parseFloat(q.get("PRODUCT_COUNT").toString());
             	//占比
             	if(total==0||productCount==0){
               		q.put("RATIO", 0);
               	}else{
               		float ratio = 100*(productCount/total);
               		q.put("RATIO", ratio);
               	}
            }
        	
        	retMap.put("reasonList", reasonList);
        	retMap.put("qualityList", qualityList);
            pr.setState(true);
			pr.setMessage("获取售后分析成功");
			pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	
	/**
     * 异常商品-流量下跌
     * 条件：最近7天浏览量/上周期7天浏览量*100 <= 50%
     * 
     * @param request
     * @return
     */
	@SuppressWarnings("unchecked")
	public GridResult queryAbnormalFlowListForPage(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> retMap = new HashMap<String, Object>();
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
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        	
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, -1);
			String first_end_date = sdf.format(c.getTime());
			paramMap.put("first_end_date", first_end_date);
			retMap.put("first_end_date", first_end_date);
			Date firstEndDate = sdf.parse(first_end_date);
			
			c = Calendar.getInstance();
			c.setTime(firstEndDate);
			c.add(Calendar.DATE, -6);
			String first_start_date = sdf.format(c.getTime());
			paramMap.put("first_start_date", first_start_date);
			retMap.put("first_start_date", first_start_date);
			Date firstStartDate = sdf.parse(first_start_date);
			
			c = Calendar.getInstance();
			c.setTime(firstStartDate);
			c.add(Calendar.DATE, -1);
			String last_end_date = sdf.format(c.getTime());
			paramMap.put("last_end_date", last_end_date);
			retMap.put("last_end_date", last_end_date);
			Date lastEndDate = sdf.parse(last_end_date);
			
			c = Calendar.getInstance();
			c.setTime(lastEndDate);
			c.add(Calendar.DATE, -6);
			String last_start_date = sdf.format(c.getTime());
			paramMap.put("last_start_date", last_start_date);
			retMap.put("last_start_date", last_start_date);
			/** 流量下跌总数 */
			int total = productAnalysisOperationDao.r_queryAbnormalFlowCount(paramMap);
            /** 流量下跌列表 */
            List<Map<String, Object>> resultList = productAnalysisOperationDao.r_queryAbnormalFlowListForPage(paramMap);
            
            if (resultList != null && resultList.size() > 0) {
            	retMap.put("dataList", resultList);
				gr.setState(true);
				gr.setMessage("查询流量下跌列表成功");
				gr.setObj(retMap);
				gr.setTotal(total);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
				gr.setObj(retMap);
			}
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	
	/**
     * 异常商品-支付转化率低
     * 条件：最近7天支付率 < 同类商品的平均转化率
     * 
     * @param request
     * @return
     */
	@SuppressWarnings("unchecked")
	public GridResult queryAbnormalZhlListForPage(HttpServletRequest request) {
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
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        	
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, -1);
			String first_end_date = sdf.format(c.getTime());
			paramMap.put("first_end_date", first_end_date);
			Date firstEndDate = sdf.parse(first_end_date);
			
			c = Calendar.getInstance();
			c.setTime(firstEndDate);
			c.add(Calendar.DATE, -6);
			String first_start_date = sdf.format(c.getTime());
			paramMap.put("first_start_date", first_start_date);
			Date firstStartDate = sdf.parse(first_start_date);
			
			c = Calendar.getInstance();
			c.setTime(firstStartDate);
			c.add(Calendar.DATE, -1);
			String last_end_date = sdf.format(c.getTime());
			paramMap.put("last_end_date", last_end_date);
			Date lastEndDate = sdf.parse(last_end_date);
			
			c = Calendar.getInstance();
			c.setTime(lastEndDate);
			c.add(Calendar.DATE, -6);
			String last_start_date = sdf.format(c.getTime());
			paramMap.put("last_start_date", last_start_date);
			
			/** 同类商品支付转化率平均值 */
			List<Map<String, Object>> list = productAnalysisOperationDao.r_queryAvgZhlList(paramMap);
			if(list != null && list.size() > 0) {
				paramMap.put("list", list);
				/** 支付转化率低总数 */
				int total = productAnalysisOperationDao.r_queryAbnormalZhlCount(paramMap);
	            /** 支付转化率低列表 */
	            List<Map<String, Object>> resultList = productAnalysisOperationDao.r_queryAbnormalZhlListForPage(paramMap);
	            
	            if (resultList != null && resultList.size() > 0) {
					gr.setState(true);
					gr.setMessage("查询支付转化率低列表成功");
					gr.setObj(resultList);
					gr.setTotal(total);
				} else {
					gr.setState(true);
					gr.setMessage("无数据");
				}
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
     * 异常商品-退款预警
     * 条件：(最近7天退货数-上个周期7天退货数)/上个周期7天退货数*100 >= 50
     * 
     * @param request
     * @return
     */
	@SuppressWarnings("unchecked")
	public GridResult queryAbnormalRefundListForPage(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> retMap = new HashMap<String, Object>();
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
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        	
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, -1);
			String first_end_date = sdf.format(c.getTime());
			paramMap.put("first_end_date", first_end_date);
			retMap.put("first_end_date", first_end_date);
			Date firstEndDate = sdf.parse(first_end_date);
			
			c = Calendar.getInstance();
			c.setTime(firstEndDate);
			c.add(Calendar.DATE, -6);
			String first_start_date = sdf.format(c.getTime());
			paramMap.put("first_start_date", first_start_date);
			retMap.put("first_start_date", first_start_date);
			Date firstStartDate = sdf.parse(first_start_date);
			
			c = Calendar.getInstance();
			c.setTime(firstStartDate);
			c.add(Calendar.DATE, -1);
			String last_end_date = sdf.format(c.getTime());
			paramMap.put("last_end_date", last_end_date);
			retMap.put("last_end_date", last_end_date);
			Date lastEndDate = sdf.parse(last_end_date);
			
			c = Calendar.getInstance();
			c.setTime(lastEndDate);
			c.add(Calendar.DATE, -6);
			String last_start_date = sdf.format(c.getTime());
			paramMap.put("last_start_date", last_start_date);
			retMap.put("last_start_date", last_start_date);
			
			/** 退款预警总数 */
			int total = productAnalysisOperationDao.r_queryAbnormalRefundCount(paramMap);
            /** 退款预警列表 */
            List<Map<String, Object>> resultList = productAnalysisOperationDao.r_queryAbnormalRefundListForPage(paramMap);
            
            if (resultList != null && resultList.size() > 0) {
            	retMap.put("dataList", resultList);
				gr.setState(true);
				gr.setMessage("查询退款预警列表成功");
				gr.setObj(retMap);
				gr.setTotal(total);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
				gr.setObj(retMap);
			}
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	
	/**
	 * 异常商品-库存预警
	 * 条件：最近3天销量>昨日库存量*80%
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryAbnormalStockListForPage(HttpServletRequest request) {
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
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        	
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, -1);
			String first_end_date = sdf.format(c.getTime());
			paramMap.put("first_end_date", first_end_date);
			Date firstEndDate = sdf.parse(first_end_date);
			
			c = Calendar.getInstance();
			c.setTime(firstEndDate);
			c.add(Calendar.DATE, -3);
			String first_start_date = sdf.format(c.getTime());
			paramMap.put("first_start_date", first_start_date);
			
			paramMap.put("last_end_date", first_end_date);
			Date lastEndDate = sdf.parse(first_end_date);
			
			c = Calendar.getInstance();
			c.setTime(lastEndDate);
			c.add(Calendar.DATE, -6);
			String last_start_date = sdf.format(c.getTime());
			paramMap.put("last_start_date", last_start_date);
			
			/** 库存预警总数 */
			int total = productAnalysisOperationDao.r_queryAbnormalStockCount(paramMap);
            /** 库存预警列表 */
            List<Map<String, Object>> resultList = productAnalysisOperationDao.r_queryAbnormalStockListForPage(paramMap);
            
            List<String> list = new ArrayList<String>();
            
            if (resultList != null && resultList.size() > 0) {
            	for(Map<String, Object> r : resultList) {
                 	list.add(r.get("ITEMNUMBER").toString());
                }
                 
                List<Map<String,Object>> stock_list = productAnalysisOperationDao.queryProductStockCount(list);//数据获取-商品库存
                Map<String,Object> stock_map = list2Map(stock_list,"ITEMNUMBER","STOCK_COUNT");
                 
    	        for(Map<String, Object> r : resultList) {
    	        	r.put("STOCK_COUNT", stock_map.get(r.get("ITEMNUMBER").toString())==null?0:Float.parseFloat(stock_map.get(r.get("ITEMNUMBER").toString()).toString()));
    	        }
				gr.setState(true);
				gr.setMessage("查询库存预警列表成功");
				gr.setObj(resultList);
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
	 * 异常商品-滞销商品
	 * 条件：上架时间 > 15天，剩余库存数 > 累计销量，最近7天销量 < 50 的商品
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryAbnormalUnsalableListForPage(HttpServletRequest request) {
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
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        	
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, -1);
			String first_end_date = sdf.format(c.getTime());
			paramMap.put("first_end_date", first_end_date);
			Date firstEndDate = sdf.parse(first_end_date);
			
			c = Calendar.getInstance();
			c.setTime(firstEndDate);
			c.add(Calendar.DATE, -6);
			String first_start_date = sdf.format(c.getTime());
			paramMap.put("first_start_date", first_start_date);
			
			/** 滞销商品总数 */
			int total = productAnalysisOperationDao.r_queryAbnormalUnsalableCount(paramMap);
            /** 滞销商品列表 */
            List<Map<String, Object>> resultList = productAnalysisOperationDao.r_queryAbnormalUnsalableListForPage(paramMap);
            
            List<String> list = new ArrayList<String>();
            
            if (resultList != null && resultList.size() > 0) {
            	for(Map<String, Object> r : resultList) {
                 	list.add(r.get("ITEMNUMBER").toString());
                }
                 
                List<Map<String,Object>> stock_list = productAnalysisOperationDao.queryProductStockCount(list);//数据获取-商品库存
                Map<String,Object> stock_map = list2Map(stock_list,"ITEMNUMBER","STOCK_COUNT");
                 
    	        for(Map<String, Object> r : resultList) {
    	        	r.put("STOCK_COUNT", stock_map.get(r.get("ITEMNUMBER").toString())==null?0:Float.parseFloat(stock_map.get(r.get("ITEMNUMBER").toString()).toString()));
    	        }
				gr.setState(true);
				gr.setMessage("查询滞销商品列表成功");
				gr.setObj(resultList);
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
	 * 商品库存明细
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductStockDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("itemnumber"))) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            Map<String, Object> warehouseStockMap = new HashMap<String, Object>();
            //商品库存明细
            List<Map<String, Object>> skuList = productAnalysisOperationDao.queryProductStockDetail(paramMap);
            //获取成品仓列表
            List<Map<String, Object>> warehouseList = productAnalysisOperationDao.queryWarehouseList(paramMap);
            paramMap.put("list", warehouseList);
            //根据仓库查询商品SKU库存
            List<Map<String, Object>> warehouseStockList = productAnalysisOperationDao.queryWarehouseProductSkuStock(paramMap);
            if(warehouseStockList!=null&&warehouseStockList.size()>0){
            	for(Map<String,Object> map:warehouseStockList){
            		int stock_count = ((BigDecimal) map.get("STOCK_COUNT")).intValue();
            		warehouseStockMap.put(map.get("PRODUCT_SKU")+"_"+map.get("WAREHOUSE_ID"), stock_count);
            	}
            }
            retMap.put("warehouseList", warehouseList);
            retMap.put("skuList", skuList);
            retMap.put("warehouseStockMap", warehouseStockMap);
            pr.setState(true);
			pr.setMessage("获取商品库存明细成功");
			pr.setObj(retMap);
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
