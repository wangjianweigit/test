package com.tk.analysis.stationed.service;

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

import com.tk.analysis.stationed.dao.StationedAnalysisDao;
import com.tk.sys.util.DateUtils;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Service("StationedAnalysisService")
public class StationedAnalysisService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private StationedAnalysisDao stationedAnalysisDao;
	private String[] colors={"#2f4554 ", "#c23531", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF", "#001cf1", "#000000", "#50d400", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4",
            "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3"};
	/**
	 * 商家今日实时销量-折线
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryRealTimeSaleChart(HttpServletRequest request) {
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
            if(StringUtils.isEmpty(paramMap.get("type"))) {
            	 pr.setState(false);
                 pr.setMessage("缺少参数type");
                 return pr;
            }
            //今日
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String dateNowStr = sdf.format(new Date()); 
            paramMap.put("date_short", dateNowStr);
            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            int index = 0;
            if("1".equals(paramMap.get("type").toString())) {//销售额
            	/** 商家销售额 */
            	list = stationedAnalysisDao.r_queryRealTime_StationedPayMoneyList(paramMap);
            }else {//销量
            	/** 商家销量 */
            	list = stationedAnalysisDao.r_queryRealTime_StationedPayCountList(paramMap);
            }
            float cnt = 0;
        	for(Map<String, Object> map :list) {
            	if(index >= 5) {
            		float val = map.get("CNT")==null?0:Float.parseFloat(map.get("CNT").toString());
            		cnt+=val;
            		index++;
            	}else{
            		resultList.add(map);
            		index++;
            	}
        	}
            if(index >= 5) {
            	Map<String, Object> m = new HashMap<String, Object>();
            	m.put("PAGE_NAME", "其他商家");
            	m.put("CNT", cnt);
            	resultList.add(m);
            }
            
            pr.setState(true);
			pr.setMessage("获取商家今日实时销量成功");
			pr.setObj(resultList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.info(e);
        }
        return pr;
	}
	
	/**
	 * 商家今日实时销量-商家销售列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryRealTimeSaleList(HttpServletRequest request) {
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
            //今日
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String td_date = sdf.format(new Date());
            paramMap.put("start_date", td_date);
            paramMap.put("end_date", td_date);
			/** 商家总数 */
			int total = stationedAnalysisDao.r_queryStationedCount(paramMap);
			//商品销量
			String file_names_pc = ":PAY_COUNT:";
			//商品销售总额
			String file_names_pm = ":PAY_MONEY:";
			//支付买家数
			String file_names_pn = ":PURCHASE_NUMBER:";
			List<Map<String, Object>> list=null;
			
			//需要查询的商家列表
			List<String> stationedList = new ArrayList<String>();
			if((paramMap.containsKey("sort")&&!"".equals(paramMap.get("sort").toString()))
					&&(paramMap.containsKey("sort_by")&&!"".equals(paramMap.get("sort_by").toString()))){
				String sort = ":"+paramMap.get("sort").toString()+":";
				if(file_names_pc.indexOf(sort)!=-1) {
					//商品销量---获取排序后的商家列表
					stationedList=stationedAnalysisDao.r_queryStationed_PayCount_Stationed(paramMap);
				}else if(file_names_pm.indexOf(sort)!=-1) {
					//商品销售总额---获取排序后的商家列表
					stationedList=stationedAnalysisDao.r_queryStationed_PayMoney_Stationed(paramMap);
				}else if(file_names_pn.indexOf(sort)!=-1) {
					//支付买家数---获取排序后的商家列表
					stationedList=stationedAnalysisDao.r_queryStationed_PurchaseNumber_Stationed(paramMap);
				}else {
					gr.setState(false);
					gr.setMessage("未配置的排序字段，请联系管理员【"+paramMap.get("sort").toString()+"】");
					return gr;
				}
			}else{
				//查询默认排序的商家列表
				stationedList=stationedAnalysisDao.r_queryRealTimeSaleStationedListBy_Default(paramMap);
			}
			
			if(!stationedList.isEmpty()&&stationedList.size()>0){
				paramMap.put("stationedList", stationedList);
				list = stationedAnalysisDao.r_queryStationedList(paramMap);
			}
			
			if (list != null && list.size() > 0) {
				paramMap.put("list", list);
				//商品销量，商品销售总额，支付买家数
				List<Map<String,Object>> payPurchaseNumberCountMoneyList = stationedAnalysisDao.r_queryRealTimeSale_PayPurchaseNumberCountMoney(paramMap);
				Map<String,Object> purchaseNumberMap = list2Map(payPurchaseNumberCountMoneyList,"STATIONED_USER_ID","PURCHASE_NUMBER");
				Map<String,Object> payCountMap = list2Map(payPurchaseNumberCountMoneyList,"STATIONED_USER_ID","PAY_COUNT");
				Map<String,Object> payMoneyMap = list2Map(payPurchaseNumberCountMoneyList,"STATIONED_USER_ID","PAY_MONEY");
				
				//预定订单的定金
				List<Map<String,Object>> preFirstMoneyList = stationedAnalysisDao.r_queryRealTimeSale_PreFirstMoney(paramMap);
				Map<String,Object> preFirstMoneyMap = list2Map(preFirstMoneyList,"STATIONED_USER_ID","PRE_FIRST_MONEY");
                
				Map<String,Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String stationedUserId = tempMap.get("STATIONED_USER_ID").toString();
					
					float payMoney = payMoneyMap.get(stationedUserId)==null?0:Float.parseFloat(payMoneyMap.get(stationedUserId).toString());
					//预定订单的定金
					float preFirstMoney = preFirstMoneyMap.get(stationedUserId)==null?0:Float.parseFloat(preFirstMoneyMap.get(stationedUserId).toString());
					payMoney+=preFirstMoney;
					//商品销量
					tempMap.put("PAY_COUNT", payCountMap.get(stationedUserId)==null?0:Float.parseFloat(payCountMap.get(stationedUserId).toString()));
					//商品销售总额
					tempMap.put("PAY_MONEY",payMoney);
					//支付买家数
					tempMap.put("PURCHASE_NUMBER", purchaseNumberMap.get(stationedUserId)==null?0:Float.parseFloat(purchaseNumberMap.get(stationedUserId).toString()));
				}
				gr.setState(true);
				gr.setMessage("获取商家销售列表成功");
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
	 * 商家分布地图
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryDistributionMap(HttpServletRequest request) {
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
            
            List<Map<String, Object>> resultList = stationedAnalysisDao.r_queryDistributionMap(paramMap);
            pr.setState(true);
			pr.setMessage("获取商家分布地图成功");
			pr.setObj(resultList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 商家分布区域排行
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryDistributionAreaRank(HttpServletRequest request) {
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
            paramMap.put("num", 5);
            /** 商家总数 */
            int count = stationedAnalysisDao.r_queryStationedCount(paramMap);
        	/** 省份 商家数量 */
            List<Map<String, Object>> provinceList = stationedAnalysisDao.r_queryStationedCountToProvince(paramMap);
            /** 城市 商家数量 */
            List<Map<String, Object>> cityList = stationedAnalysisDao.r_queryStationedCountToCity(paramMap);
            for(Map<String, Object> p : provinceList) {
            	float stationedCount = Float.parseFloat(p.get("STATIONED_COUNT").toString());
            	p.put("RATIO", 100*stationedCount/count);
            }
        	for(Map<String, Object> c : cityList) {
            	float stationedCount = Float.parseFloat(c.get("STATIONED_COUNT").toString());
            	c.put("RATIO", 100*stationedCount/count);
            }
            
            resultMap.put("provinceList", provinceList);
            resultMap.put("cityList", cityList);
            pr.setState(true);
			pr.setMessage("获取商家分布区域排行成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	
	/**
	 * 商家分布区域详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryDistributionAreaDetail(HttpServletRequest request) {
        GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
	        if(paramMap == null) {
            	gr.setState(false);
            	gr.setMessage("缺少参数");
            	return gr;
            }
			/** 商家总数 */
			int total = stationedAnalysisDao.r_queryStationedCount(paramMap);
			/** 按省份或城市 查询商家 */
			List<Map<String, Object>> resultList = stationedAnalysisDao.r_queryDistributionAreaDetail(paramMap);
			if(resultList != null && resultList.size() > 0) {
				gr.setState(true);
				gr.setMessage("获取商家分布区域详情成功");
				gr.setObj(resultList);
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
	 * 在售商品-折线
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryOnSaleProductChart(HttpServletRequest request) {
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
            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> list = stationedAnalysisDao.r_queryOnSaleProductChart(paramMap);
            int index = 0;
            float cnt = 0;
        	for(Map<String, Object> map :list) {
            	if(index >= 5) {
            		float val = map.get("CNT")==null?0:Float.parseFloat(map.get("CNT").toString());
            		cnt+=val;
            		index++;
            	}else {
            		resultList.add(map);
            		index++;
            	}
        	}
            if(index >= 5) {
            	Map<String, Object> m = new HashMap<String, Object>();
            	m.put("PAGE_NAME", "其他商家");
            	m.put("CNT", cnt);
            	resultList.add(m);
            }
            
            pr.setState(true);
			pr.setMessage("获取在售商品折线成功");
			pr.setObj(resultList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 在售商品-列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryOnSaleProductList(HttpServletRequest request) {
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
			/** 商家总数 */
			int total = stationedAnalysisDao.r_queryStationedCount(paramMap);
			//货号数
			String file_names_ic = ":ITEM_COUNT:";
			//sku数
			String file_names_sc = ":SKU_COUNT:";
			List<Map<String, Object>> list=null;
			
			//需要查询的商家列表
			List<String> stationedList = new ArrayList<String>();
			if((paramMap.containsKey("sort")&&!"".equals(paramMap.get("sort").toString()))
					&&(paramMap.containsKey("sort_by")&&!"".equals(paramMap.get("sort_by").toString()))){
				String sort = ":"+paramMap.get("sort").toString()+":";
				if(file_names_ic.indexOf(sort)!=-1) {
					//货号数---获取排序后的商家列表
					stationedList=stationedAnalysisDao.r_queryStationed_ItemCount_Stationed(paramMap);
				}else if(file_names_sc.indexOf(sort)!=-1) {
					//sku数---获取排序后的商家列表
					stationedList=stationedAnalysisDao.r_queryStationed_SkuCount_Stationed(paramMap);
				}else {
					gr.setState(false);
					gr.setMessage("未配置的排序字段，请联系管理员【"+paramMap.get("sort").toString()+"】");
					return gr;
				}
			}else{
				//查询默认排序的商家列表<货号数>
				stationedList=stationedAnalysisDao.r_queryOnSaleProductStationedListBy_Default(paramMap);
			}
			
			if(!stationedList.isEmpty()&&stationedList.size()>0){
				paramMap.put("stationedList", stationedList);
				list = stationedAnalysisDao.r_queryStationedList(paramMap);
			}
			
			if (list != null && list.size() > 0) {
				paramMap.put("list", list);
				//货号数
				List<Map<String,Object>> itemCountList = stationedAnalysisDao.r_queryOnSaleProduct_ItemCount(paramMap);
				Map<String,Object> itemCountMap = list2Map(itemCountList,"STATIONED_USER_ID","ITEM_COUNT");
				
				//sku数
				List<Map<String,Object>> skuCountList = stationedAnalysisDao.r_queryOnSaleProduct_SkuCount(paramMap);
				Map<String,Object> skuCountMap = list2Map(skuCountList,"STATIONED_USER_ID","SKU_COUNT");
                
				Map<String,Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String stationedUserId = tempMap.get("STATIONED_USER_ID").toString();
					//货号数
					tempMap.put("ITEM_COUNT", itemCountMap.get(stationedUserId)==null?0:Float.parseFloat(itemCountMap.get(stationedUserId).toString()));
					//sku数
					tempMap.put("SKU_COUNT", skuCountMap.get(stationedUserId)==null?0:Float.parseFloat(skuCountMap.get(stationedUserId).toString()));
				}
				gr.setState(true);
				gr.setMessage("获取在售商品商家列表成功");
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
	 * 商家注册资本-折线
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryRegisteredCapitalChart(HttpServletRequest request) {
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
            List<Map<String, Object>> allDataList = new ArrayList<Map<String, Object>>();
            Map<String, Object> map = null;
            
            paramMap.put("start", 0);
            paramMap.put("end", 20);
            float count = stationedAnalysisDao.r_queryRegisteredCapitalStationedCount(paramMap);
            
            map = new HashMap<String, Object>();
            map.put("PAGE_NAME", "0-20w");
          	map.put("CNT", count);
          	allDataList.add(map);
          	
          	paramMap.put("start", 20);
            paramMap.put("end", 100);
            count = stationedAnalysisDao.r_queryRegisteredCapitalStationedCount(paramMap);
            
            map = new HashMap<String, Object>();
            map.put("PAGE_NAME", "20-100w");
          	map.put("CNT", count);
          	allDataList.add(map);
          	
          	paramMap.put("start", 100);
            paramMap.put("end", 500);
            count = stationedAnalysisDao.r_queryRegisteredCapitalStationedCount(paramMap);
            
            map = new HashMap<String, Object>();
            map.put("PAGE_NAME", "100-500w");
          	map.put("CNT", count);
          	allDataList.add(map);
          	
          	paramMap.put("start", 500);
            paramMap.put("end", 1000);
            count = stationedAnalysisDao.r_queryRegisteredCapitalStationedCount(paramMap);
            
            map = new HashMap<String, Object>();
            map.put("PAGE_NAME", "500-1000w");
          	map.put("CNT", count);
          	allDataList.add(map);
          	
          	paramMap.put("start", 1000);
          	paramMap.remove("end");
            count = stationedAnalysisDao.r_queryRegisteredCapitalStationedCount(paramMap);
            
            map = new HashMap<String, Object>();
            map.put("PAGE_NAME", "大于1000w");
          	map.put("CNT", count);
          	allDataList.add(map);
            
            pr.setState(true);
			pr.setMessage("获取商家注册资本折线成功");
			pr.setObj(allDataList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 商家成立年数-折线
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryEstablishYearChart(HttpServletRequest request) {
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
            List<Map<String, Object>> allDataList = new ArrayList<Map<String, Object>>();
            Map<String, Object> map = null;
            
            paramMap.put("start", 0);
            paramMap.put("end", 1);
            float count = stationedAnalysisDao.r_queryEstablishYearStationedCount(paramMap);
            
            map = new HashMap<String, Object>();
            map.put("PAGE_NAME", "1年");
          	map.put("CNT", count);
          	allDataList.add(map);
          	
          	paramMap.put("start", 1);
            paramMap.put("end", 2);
            count = stationedAnalysisDao.r_queryEstablishYearStationedCount(paramMap);
            
            map = new HashMap<String, Object>();
            map.put("PAGE_NAME", "1-2年");
          	map.put("CNT", count);
          	allDataList.add(map);
          	
          	paramMap.put("start", 2);
            paramMap.put("end", 5);
            count = stationedAnalysisDao.r_queryEstablishYearStationedCount(paramMap);
            
            map = new HashMap<String, Object>();
            map.put("PAGE_NAME", "2-5年");
          	map.put("CNT", count);
          	allDataList.add(map);
          	
          	paramMap.put("start", 5);
            paramMap.put("end", 10);
            count = stationedAnalysisDao.r_queryEstablishYearStationedCount(paramMap);
            
            map = new HashMap<String, Object>();
            map.put("PAGE_NAME", "5-10年");
          	map.put("CNT", count);
          	allDataList.add(map);
          	
          	paramMap.put("start", 10);
          	paramMap.remove("end");
            count = stationedAnalysisDao.r_queryEstablishYearStationedCount(paramMap);
            
            map = new HashMap<String, Object>();
            map.put("PAGE_NAME", "10年以上");
          	map.put("CNT", count);
          	allDataList.add(map);
            
            pr.setState(true);
			pr.setMessage("获取商家成立年数折线成功");
			pr.setObj(allDataList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 在售商品过季情况-折线
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryOutOfSeasonChart(HttpServletRequest request) {
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
            List<Map<String, Object>> allDataList = new ArrayList<Map<String, Object>>();
            Map<String, Object> map = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            String year = sdf.format(new Date());
            paramMap.put("year", year);//年份
          	paramMap.put("season_id", getSeasonId());//季节id
            float count = stationedAnalysisDao.r_queryOutSeasonDateItemCount(paramMap);
            
            map = new HashMap<String, Object>();
            map.put("PAGE_NAME", "过季/过时");
          	map.put("CNT", count);
          	allDataList.add(map);
          	
            count = stationedAnalysisDao.r_querySeasonalItemCount(paramMap);
            
            map = new HashMap<String, Object>();
            map.put("PAGE_NAME", "应季");
          	map.put("CNT", count);
          	allDataList.add(map);
            
            pr.setState(true);
			pr.setMessage("获取在售商品过季情况折线成功");
			pr.setObj(allDataList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 在售商品过季情况-列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryOutOfSeasonList(HttpServletRequest request) {
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
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            String year = sdf.format(new Date());
            paramMap.put("year", year);//年份
          	paramMap.put("season_id", getSeasonId());//季节id
			/** 商家总数 */
			int total = stationedAnalysisDao.r_queryStationedCount(paramMap);
			//货号数
			String file_names_ic = ":ITEM_COUNT:";
			//过季/过时货号数
			String file_names_osc = ":OUT_SEASON_COUNT:";
			List<Map<String, Object>> list=null;
			
			//需要查询的商家列表
			List<String> stationedList = new ArrayList<String>();
			if((paramMap.containsKey("sort")&&!"".equals(paramMap.get("sort").toString()))
					&&(paramMap.containsKey("sort_by")&&!"".equals(paramMap.get("sort_by").toString()))){
				String sort = ":"+paramMap.get("sort").toString()+":";
				if(file_names_ic.indexOf(sort)!=-1) {
					//货号数---获取排序后的商家列表
					stationedList=stationedAnalysisDao.r_queryStationed_ItemCount_Stationed(paramMap);
				}else if(file_names_osc.indexOf(sort)!=-1) {
					//过季/过时货号数---获取排序后的商家列表
					stationedList=stationedAnalysisDao.r_queryStationed_OuteSeasonCount_Stationed(paramMap);
				}else {
					gr.setState(false);
					gr.setMessage("未配置的排序字段，请联系管理员【"+paramMap.get("sort").toString()+"】");
					return gr;
				}
			}else{
				//查询默认排序的商家列表<货号数>
				stationedList=stationedAnalysisDao.r_queryOnSaleProductStationedListBy_Default(paramMap);
			}
			
			if(!stationedList.isEmpty()&&stationedList.size()>0){
				paramMap.put("stationedList", stationedList);
				list = stationedAnalysisDao.r_queryStationedList(paramMap);
			}
			
			if (list != null && list.size() > 0) {
				paramMap.put("list", list);
				//货号数
				List<Map<String,Object>> itemCountList = stationedAnalysisDao.r_queryOnSaleProduct_ItemCount(paramMap);
				Map<String,Object> itemCountMap = list2Map(itemCountList,"STATIONED_USER_ID","ITEM_COUNT");
				
				//过季/过时货号数
				List<Map<String,Object>> outSeasonCountList = stationedAnalysisDao.r_queryStationed_OutSeasonCount(paramMap);
				Map<String,Object> outSeasonCountMap = list2Map(outSeasonCountList,"STATIONED_USER_ID","OUT_SEASON_COUNT");
                
				Map<String,Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String stationedUserId = tempMap.get("STATIONED_USER_ID").toString();
					float itemCount = itemCountMap.get(stationedUserId)==null?0:Float.parseFloat(itemCountMap.get(stationedUserId).toString());
					float outSeasonCount = outSeasonCountMap.get(stationedUserId)==null?0:Float.parseFloat(outSeasonCountMap.get(stationedUserId).toString());
					//货号数
					tempMap.put("ITEM_COUNT", itemCount);
					//过季/过时货号数
					tempMap.put("OUT_SEASON_COUNT", outSeasonCount);
					//占比
					if(itemCount > 0) {
						tempMap.put("RATIO", 100*outSeasonCount/itemCount);
	    			}else {
	    				tempMap.put("RATIO", 0);
	    			}
				}
				gr.setState(true);
				gr.setMessage("获取在售商品过季情况列表成功");
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
	 * 用户复购-折线
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryRepeatPurchaseChart(HttpServletRequest request) {
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
            List<Map<String, Object>> allDataList = new ArrayList<Map<String, Object>>();
            Map<String, Object> map = null;
            float count = stationedAnalysisDao.r_queryStationed_RepeatPurchaseNumber(paramMap);
            
            map = new HashMap<String, Object>();
            map.put("PAGE_NAME", "平台复购人数");
          	map.put("CNT", count);
          	allDataList.add(map);
          	
            float total = stationedAnalysisDao.r_queryStationed_PurchaseNumber(paramMap);
            
            map = new HashMap<String, Object>();
            map.put("PAGE_NAME", "平台未复购人数");
          	map.put("CNT", total-count);
          	allDataList.add(map);
            
            pr.setState(true);
			pr.setMessage("获取用户复购折线成功");
			pr.setObj(allDataList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 用户复购-列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryRepeatPurchaseList(HttpServletRequest request) {
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
			/** 商家总数 */
			int total = stationedAnalysisDao.r_queryStationedCount(paramMap);
			//支付买家数
			String file_names_pn = ":PURCHASE_NUMBER:";
			//用户复购数
			String file_names_rpn = ":REPEAT_PURCHASE_NUMBER:";
			//占比
			String file_names_ro = ":RATIO:";
			List<Map<String, Object>> list=null;
			
			//需要查询的商家列表
			List<String> stationedList = new ArrayList<String>();
			if((paramMap.containsKey("sort")&&!"".equals(paramMap.get("sort").toString()))
					&&(paramMap.containsKey("sort_by")&&!"".equals(paramMap.get("sort_by").toString()))){
				String sort = ":"+paramMap.get("sort").toString()+":";
				if(file_names_pn.indexOf(sort)!=-1) {
					//支付买家数---获取排序后的商家列表
					stationedList=stationedAnalysisDao.r_queryStationed_PurchaseNumber_Stationed(paramMap);
				}else if(file_names_rpn.indexOf(sort)!=-1) {
					//用户复购数---获取排序后的商家列表
					stationedList=stationedAnalysisDao.r_queryStationed_RepeatPurchaseNumber_Stationed(paramMap);
				}else if(file_names_ro.indexOf(sort)!=-1) {
					//占比---获取排序后的商家列表
					stationedList=stationedAnalysisDao.r_queryStationed_RepeatRatio_Stationed(paramMap);
				}else {
					gr.setState(false);
					gr.setMessage("未配置的排序字段，请联系管理员【"+paramMap.get("sort").toString()+"】");
					return gr;
				}
			}else{
				//查询默认排序的商家列表
				stationedList=stationedAnalysisDao.r_queryRepeatPurchaseStationedListBy_Default(paramMap);
			}
			
			if(!stationedList.isEmpty()&&stationedList.size()>0){
				paramMap.put("stationedList", stationedList);
				list = stationedAnalysisDao.r_queryStationedList(paramMap);
			}
			
			if (list != null && list.size() > 0) {
				paramMap.put("list", list);
				//支付买家数
				List<Map<String,Object>> purchaseNumberList = stationedAnalysisDao.r_queryRepeatPurchase_PurchaseNumber(paramMap);
				Map<String,Object> purchaseNumberMap = list2Map(purchaseNumberList,"STATIONED_USER_ID","PURCHASE_NUMBER");
				
				//用户复购数
				List<Map<String,Object>> repeatPurchaseNumberList = stationedAnalysisDao.r_queryRepeatPurchase_RepeatPurchaseNumber(paramMap);
				Map<String,Object> repeatPurchaseNumberMap = list2Map(repeatPurchaseNumberList,"STATIONED_USER_ID","REPEAT_PURCHASE_NUMBER");
                
				Map<String,Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String stationedUserId = tempMap.get("STATIONED_USER_ID").toString();
					float purchaseNumber = purchaseNumberMap.get(stationedUserId)==null?0:Float.parseFloat(purchaseNumberMap.get(stationedUserId).toString());
					float repeatPurchaseNumber = repeatPurchaseNumberMap.get(stationedUserId)==null?0:Float.parseFloat(repeatPurchaseNumberMap.get(stationedUserId).toString());
					//支付买家数
					tempMap.put("PURCHASE_NUMBER", purchaseNumber);
					//用户复购数
					tempMap.put("REPEAT_PURCHASE_NUMBER", repeatPurchaseNumber);
					//占比
					if(purchaseNumber > 0) {
						tempMap.put("RATIO", 100*repeatPurchaseNumber/purchaseNumber);
					}else {
						tempMap.put("RATIO", 0);
					}
				}
				gr.setState(true);
				gr.setMessage("获取在用户复购商家列表成功");
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
	 * 商家交易-商品销量
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryStationedTr_PayCount(HttpServletRequest request) {
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_count = 0;
        	/** 商品销量 */
        	t_count = stationedAnalysisDao.r_queryStationedTr_PayCount(paramMap);
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
            l_count = stationedAnalysisDao.r_queryStationedTr_PayCount(paramMap);
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
	 * 商家交易-商品销售总额
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryStationedTr_PayMoney(HttpServletRequest request) {
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_money = 0;
        	/** 商品销售总额 */
            t_money = stationedAnalysisDao.r_queryStationedTr_PayMoney(paramMap);
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
			l_money = stationedAnalysisDao.r_queryStationedTr_PayMoney(paramMap);
            if(t_money == 0 || l_money == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_money-l_money)/l_money*100));
            }
            resultMap.put("pay_money", t_money);
            
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
	 * 商家交易-未发退货数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryStationedTr_UnsentReturnCount(HttpServletRequest request) {
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_count = 0;
        	/** 未发退货数 */
            t_count = stationedAnalysisDao.r_queryStationedTr_UnsentReturnCount(paramMap);
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
			l_count = stationedAnalysisDao.r_queryStationedTr_UnsentReturnCount(paramMap);
            if(t_count == 0 || l_count == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_count-l_count)/l_count*100));
            }
            resultMap.put("unsent_return_count", t_count);
            
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
	 * 商家交易-已发退货数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryStationedTr_SentReturnCount(HttpServletRequest request) {
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_count = 0;
        	/** 已发退货数 */
            t_count = stationedAnalysisDao.r_queryStationedTr_SentReturnCount(paramMap);
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
			l_count = stationedAnalysisDao.r_queryStationedTr_SentReturnCount(paramMap);
            if(t_count == 0 || l_count == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_count-l_count)/l_count*100));
            }
            resultMap.put("sent_return_count", t_count);
            
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
	 * 商家交易-终检异常
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryStationedTr_FqcCount(HttpServletRequest request) {
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_count = 0;
        	/** 终检异常 */
            t_count = stationedAnalysisDao.r_queryStationedTr_FqcCount(paramMap);
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
			l_count = stationedAnalysisDao.r_queryStationedTr_FqcCount(paramMap);
            if(t_count == 0 || l_count == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_count-l_count)/l_count*100));
            }
            resultMap.put("fqc_count", t_count);
            
            pr.setState(true);
            pr.setMessage("获取终检异常成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 商家交易-售后异常
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryStationedTr_SaleReturnCount(HttpServletRequest request) {
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_count = 0;
        	/** 售后异常 */
            t_count = stationedAnalysisDao.r_queryStationedTr_SaleReturnCount(paramMap);
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
			l_count = stationedAnalysisDao.r_queryStationedTr_SaleReturnCount(paramMap);
            if(t_count == 0 || l_count == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_count-l_count)/l_count*100));
            }
            resultMap.put("sale_return_count", t_count);
            
            pr.setState(true);
            pr.setMessage("获取售后异常成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 商家交易-折线图
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryStationedTrChart(HttpServletRequest request) {
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
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            List<String> time_list = new ArrayList<String>();
            Map<String,Object> payCountMap = new HashMap<String,Object>();
            Map<String,Object> payMoneyMap = new HashMap<String,Object>();
            Map<String,Object> preFirstMoneyMap = new HashMap<String,Object>();
            Map<String,Object> unsentReturnCountMap = new HashMap<String,Object>();
            Map<String,Object> sentReturnCountMap = new HashMap<String,Object>();
            Map<String,Object> fqcCountMap = new HashMap<String,Object>();
            Map<String,Object> saleReturnCountMap = new HashMap<String,Object>();
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
            	/** 商家交易 商品销量、商品销售总额 */
            	List<Map<String, Object>> payCountMoneyList = stationedAnalysisDao.r_queryStationedTr_PayCountMoneyD_Chart(paramMap);
            	/** 商家交易 预定订单的定金 */
            	List<Map<String, Object>> preFirstMoneyList = stationedAnalysisDao.r_queryStationedTr_PreFirstMoneyD_Chart(paramMap);
            	/** 商家交易 未发退货数 */
            	List<Map<String, Object>> unsentReturnCountList = stationedAnalysisDao.r_queryStationedTr_UnsentReturnCountD_Chart(paramMap);
            	/** 商家交易 已发退货数 */
            	List<Map<String, Object>> sentReturnCountList = stationedAnalysisDao.r_queryStationedTr_SentReturnCountD_Chart(paramMap);
            	/** 商家交易 终检异常 */
            	List<Map<String, Object>> fqcCountList = stationedAnalysisDao.r_queryStationedTr_FqcCountD_Chart(paramMap);
            	/** 商家交易 售后异常 */
            	List<Map<String, Object>> saleReturnCountList = stationedAnalysisDao.r_queryStationedTr_SaleReturnCountD_Chart(paramMap);
            	//商品销量
                payCountMap = list2Map(payCountMoneyList,"CREATE_DATE","PAY_COUNT");
                //商品销售总额
                payMoneyMap = list2Map(payCountMoneyList,"CREATE_DATE","PAY_MONEY");
                //预定订单的定金
                preFirstMoneyMap = list2Map(preFirstMoneyList,"CREATE_DATE","PRE_FIRST_MONEY");
                //未发退货数
                unsentReturnCountMap = list2Map(unsentReturnCountList,"CREATE_DATE","UNSENT_RETURN_COUNT");
                //已发退货数
                sentReturnCountMap = list2Map(sentReturnCountList,"CREATE_DATE","SENT_RETURN_COUNT");
                //终检异常
                fqcCountMap = list2Map(fqcCountList,"CREATE_DATE","FQC_COUNT");
                //售后异常
                saleReturnCountMap = list2Map(saleReturnCountList,"CREATE_DATE","SALE_RETURN_COUNT");
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
                /** 商家交易 商家汇总 */
                list = stationedAnalysisDao.r_queryStationedSummary_Chart(paramMap);
                //商品销量
                payCountMap = list2Map(list,"CREATE_DATE","PAY_COUNT");
                //商品销售总额
                payMoneyMap = list2Map(list,"CREATE_DATE","PAY_MONEY");
                //未发退货数
                unsentReturnCountMap = list2Map(list,"CREATE_DATE","UNSENT_RETURN_COUNT");
                //已发退货数
                sentReturnCountMap = list2Map(list,"CREATE_DATE","SENT_RETURN_COUNT");
                //终检异常
                fqcCountMap = list2Map(list,"CREATE_DATE","FQC_COUNT");
                //售后异常
                saleReturnCountMap = list2Map(list,"CREATE_DATE","SALE_RETURN_COUNT");
            }
            
            //开始数据拼装
          	String key="";
          	
          	Map<String, Object> map = null;
            for(String time : time_list) {
            	key = time;
            	/** 商家交易-商品销量 */
                float payCount = payCountMap.get(key)==null?0:Float.parseFloat(payCountMap.get(key).toString());
                /** 商家交易-商品销售总额 */
                float payMoney = payMoneyMap.get(key)==null?0:Float.parseFloat(payMoneyMap.get(key).toString());
                /** 商家交易-预定订单的定金*/
                float preFirstMoney = preFirstMoneyMap.get(key)==null?0:Float.parseFloat(preFirstMoneyMap.get(key).toString());
                /** 商家交易-未发退货数 */
                float unsentReturnCount = unsentReturnCountMap.get(key)==null?0:Float.parseFloat(unsentReturnCountMap.get(key).toString());
                /** 商家交易-已发退货数 */
                float sentReturnCount = sentReturnCountMap.get(key)==null?0:Float.parseFloat(sentReturnCountMap.get(key).toString());
                /** 商家交易-终检异常 */
                float fqcCount = fqcCountMap.get(key)==null?0:Float.parseFloat(fqcCountMap.get(key).toString());
                /** 商家交易-售后异常 */
                float saleReturnCount = saleReturnCountMap.get(key)==null?0:Float.parseFloat(saleReturnCountMap.get(key).toString());
                payMoney+=preFirstMoney;
                map = new HashMap<String, Object>();
                //商品销量
              	map.put("CREATE_DATE", key);
              	map.put("CNT", payCount);
              	map.put("PAGE_NAME", "商品销量");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//商品销售总额
              	map.put("CREATE_DATE", key);
              	map.put("CNT", payMoney);
              	map.put("PAGE_NAME", "商品销售总额");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//未发退货数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", unsentReturnCount);
              	map.put("PAGE_NAME", "未发退货数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//已发退货数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", sentReturnCount);
              	map.put("PAGE_NAME", "已发退货数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//终检异常
              	map.put("CREATE_DATE", key);
              	map.put("CNT", fqcCount);
              	map.put("PAGE_NAME", "终检异常");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//售后异常
              	map.put("CREATE_DATE", key);
              	map.put("CNT", saleReturnCount);
              	map.put("PAGE_NAME", "售后异常");
              	allDataList.add(map);
            }
            
            //将原始数据组装为Echart可识别的格式
            resultMap = createData("PAGE_NAME", time_list, allDataList);
            
            pr.setState(true);
			pr.setMessage("获取商家汇总信息折线成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	/**
	 * 商家交易-商家列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryStationedTrList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
	        if(paramMap == null) {
            	gr.setState(false);
            	gr.setMessage("缺少参数");
            	return gr;
            }
			/** 商家总数 */
			int total = stationedAnalysisDao.r_queryStationedCount(paramMap);
			
			List<Map<String, Object>> resultList = stationedAnalysisDao.r_queryStationedSummaryList(paramMap);
			if(resultList != null && resultList.size() > 0) {
				gr.setState(true);
				gr.setMessage("获取商家列表成功");
				gr.setObj(resultList);
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
	 * 商家交易-商家配合度
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryCooperationDegreeList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
	        if(paramMap == null) {
            	gr.setState(false);
            	gr.setMessage("缺少参数");
            	return gr;
            }
	        String file_names_apc = "ACTIVITY_PRODUCT_COUNT";//活动商品数
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			List<String> list = new ArrayList<String>();
			
			/** 商家总数 */
			int total = stationedAnalysisDao.r_queryStationedCount(paramMap);
			String sort = "";
			if(!StringUtils.isEmpty(paramMap.get("sort"))) {
				sort = paramMap.get("sort").toString();
			}
			if(file_names_apc.equals(sort)) {
				//根据活动商品数排序
				resultList = stationedAnalysisDao.r_queryActivityProductCountListForPage(paramMap);
				if(resultList != null && resultList.size()>0) {
					for(Map<String, Object> r : resultList) {
	                 	list.add(r.get("STATIONED_USER_ID").toString());
	                }
					paramMap.put("stationedList", list);
					List<Map<String,Object>> cdList = stationedAnalysisDao.r_queryStationedCooperationDegreeList(paramMap);
	                Map<String,Object> newProductCountMap = list2Map(cdList,"STATIONED_USER_ID","NEW_PRODUCT_COUNT");
	                Map<String,Object> productionCountMap = list2Map(cdList,"STATIONED_USER_ID","PRODUCTION_COUNT");
	                Map<String,Object> instorageCountMap = list2Map(cdList,"STATIONED_USER_ID","INSTORAGE_COUNT");
	                
	                for(Map<String, Object> r :resultList) {
	                	//新品数量
	                	r.put("NEW_PRODUCT_COUNT", newProductCountMap.get(r.get("STATIONED_USER_ID").toString())==null?0:Float.parseFloat(newProductCountMap.get(r.get("STATIONED_USER_ID").toString()).toString()));
	                	//生产计划数
	                	r.put("PRODUCTION_COUNT", productionCountMap.get(r.get("STATIONED_USER_ID").toString())==null?0:Float.parseFloat(productionCountMap.get(r.get("STATIONED_USER_ID").toString()).toString()));
	                	//入库数
	                	r.put("INSTORAGE_COUNT", instorageCountMap.get(r.get("STATIONED_USER_ID").toString())==null?0:Float.parseFloat(instorageCountMap.get(r.get("STATIONED_USER_ID").toString()).toString()));
	                }
					gr.setState(true);
	    			gr.setMessage("获取商家配合度列表成功");
	    			gr.setObj(resultList);
	    			gr.setTotal(total);
				}else {
					gr.setState(true);
					gr.setMessage("无数据");
				}
			}else {
				//默认-商家排序
				resultList = stationedAnalysisDao.r_queryStationedCooperationDegreeListForPage(paramMap);
				if(resultList != null && resultList.size()>0) {
					for(Map<String, Object> r : resultList) {
	                 	list.add(r.get("STATIONED_USER_ID").toString());
	                }
					paramMap.put("stationedList", list);
					List<Map<String,Object>> apcList = stationedAnalysisDao.r_queryStationedCooperationDegree_ActivityProductCount(paramMap);
	                Map<String,Object> activityProductCountMap = list2Map(apcList,"STATIONED_USER_ID","ACTIVITY_PRODUCT_COUNT");
	                
	                for(Map<String, Object> r :resultList) {
	                	//活动商品数量
	                	r.put("ACTIVITY_PRODUCT_COUNT", activityProductCountMap.get(r.get("STATIONED_USER_ID").toString())==null?0:Float.parseFloat(activityProductCountMap.get(r.get("STATIONED_USER_ID").toString()).toString()));
	                }
					gr.setState(true);
	    			gr.setMessage("获取商家配合度列表成功");
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
		}
		return gr;
	}
	/**
	 * 商家分析-基本信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryStationedAnalysisDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("stationed_user_id"))) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            /** 基本信息 */
            Map<String, Object> retMap = stationedAnalysisDao.r_queryStationedAnalysisDetail(paramMap);
        	
            pr.setState(true);
			pr.setMessage("获取商家分析基本信息成功");
			pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 商家分析-今日实时销售-折线
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryStationedAnalysis_SaleChart(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("stationed_user_id"))) {
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
            String dateNowStr = sdf.format(new Date()); 
            paramMap.put("date_short", dateNowStr);
			
			/**************************************************今日折线图相关统计     begin****************************************************/
			List<Float> lineTd = new ArrayList<Float>();
			/** 折线图 商品销售总额 **/
			List<Map<String, Object>> td_payMoneyList = stationedAnalysisDao.r_queryStationed_PayMoney_Chart(paramMap);
			Map<String, Object> td_payMoneyMap = list2Map(td_payMoneyList,"CREATE_DATE","PAY_MONEY");
			/** 折线图 预定订单的定金 **/
			List<Map<String, Object>> td_preFirstMoneyList = stationedAnalysisDao.r_queryStationed_PreFirstMoney_Chart(paramMap);
			Map<String, Object> td_preFirstMoneyMap = list2Map(td_preFirstMoneyList,"CREATE_DATE","PRE_FIRST_MONEY");
			
           	//开始数据拼装
          	String key="";
            for(int i=0;i<time_list.size();i++){
                key = time_list.get(i);
                //商品销售总额
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
			List<Map<String, Object>> yd_payMoneyList = stationedAnalysisDao.r_queryStationed_PayMoney_Chart(paramMap);
			Map<String, Object> yd_payMoneyMap = list2Map(yd_payMoneyList,"CREATE_DATE","PAY_MONEY");
			/** 折线图 预定订单的定金 **/
			List<Map<String, Object>> yd_preFirstMoneyList = stationedAnalysisDao.r_queryStationed_PreFirstMoney_Chart(paramMap);
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
            pr.setMessage("获取今日实时销售折线成功");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.info(e);
        }
        return pr;
	}
	/**
	 * 商家分析-商品分类销量
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryStationedAnalysis_ProductTypeChart(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("stationed_user_id"))) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            //今日
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String dateNowStr = sdf.format(new Date()); 
            paramMap.put("date_short", dateNowStr);
            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
            //查询商品分类列表  
            List<Map<String, Object>> typeList = stationedAnalysisDao.r_queryProductTypeList(paramMap);
            paramMap.put("typeList", typeList);
            //查询商品分类销量
            List<Map<String, Object>> list = stationedAnalysisDao.r_queryStationedAnalysis_ProductType(paramMap);
            int index = 0;
            float cnt = 0;
        	for(Map<String, Object> map : list) {
            	if(index >= 4) {
            		float val = map.get("CNT")==null?0:Float.parseFloat(map.get("CNT").toString());
            		cnt+=val;
            		index++;
            	}else {
            		resultList.add(map);
            		index++;
            	}
        	}
            if(index >= 4) {
            	Map<String, Object> m = new HashMap<String, Object>();
            	m.put("PAGE_NAME", "其他");
            	m.put("CNT", cnt);
            	resultList.add(m);
            }
          	
            pr.setState(true);
			pr.setMessage("获取商品类型销量成功");
			pr.setObj(resultList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.info(e);
        }
        return pr;
	}
	/**
	 * 商家分析-今日实时销售
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryStationedAnalysis_SaleDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("stationed_user_id"))) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
            String dateNowStr = sdf.format(new Date()); 
            paramMap.put("start_date", dateNowStr);
            paramMap.put("end_date", dateNowStr);
            Map<String, Object> retMap = new HashMap<String, Object>();
            /**************************************************今日相关统计    begin****************************************************/
            //商品销售总额
            float td_payMoney = stationedAnalysisDao.r_queryStationed_PayMoney(paramMap);
            //商品销售总额
            float td_preFirstMoney = stationedAnalysisDao.r_queryStationed_PreFirstMoney(paramMap);
            //商品浏览量
			float td_pvCount = stationedAnalysisDao.r_queryStationed_PvCount(paramMap);
			//商品访客量
			float td_visitorCount = stationedAnalysisDao.r_queryStationed_VisitorCount(paramMap);
			//支付买家数
			float td_purchaseNumber = stationedAnalysisDao.r_queryStationed_PurchaseNumber(paramMap);
			//复购用户数
			float td_repeatPurchaseNumber = stationedAnalysisDao.r_queryStationed_RepeatPurchaseNumber(paramMap);
			td_payMoney+=td_preFirstMoney;
			
			retMap.put("TD_PAY_MONEY", td_payMoney);
			retMap.put("TD_PV_COUNT", td_pvCount);
			retMap.put("TD_VISITOR_COUNT", td_visitorCount);
			retMap.put("TD_PURCHASE_NUMBER", td_purchaseNumber);
			retMap.put("TD_REPEAT_PURCHASE_NUMBER", td_repeatPurchaseNumber);
            /**************************************************今日相关统计     end****************************************************/
            
			/**************************************************昨日相关统计     begin****************************************************/
			Calendar c = Calendar.getInstance();  
			c.add(Calendar.DATE, -1);  
			dateNowStr = sdf.format(c.getTime());  
            paramMap.put("date_short", dateNowStr);
            //商品销售总额
            float yd_payMoney = stationedAnalysisDao.r_queryStationed_PayMoney(paramMap);
            //商品销售总额
            float yd_preFirstMoney = stationedAnalysisDao.r_queryStationed_PreFirstMoney(paramMap);
			//商品浏览量
			float yd_pvCount = stationedAnalysisDao.r_queryStationed_PvCount(paramMap);
			//商品访客量
			float yd_visitorCount = stationedAnalysisDao.r_queryStationed_VisitorCount(paramMap);
			//支付买家数
			float yd_purchaseNumber = stationedAnalysisDao.r_queryStationed_PurchaseNumber(paramMap);
			//复购用户数
			float yd_repeatPurchaseNumber = stationedAnalysisDao.r_queryStationed_RepeatPurchaseNumber(paramMap);
			yd_payMoney+=yd_preFirstMoney;
			
			retMap.put("YD_PAY_MONEY", yd_payMoney);
			retMap.put("YD_PV_COUNT", yd_pvCount);
			retMap.put("YD_VISITOR_COUNT", yd_visitorCount);
			retMap.put("YD_PURCHASE_NUMBER", yd_purchaseNumber);
			retMap.put("YD_REPEAT_PURCHASE_NUMBER", yd_repeatPurchaseNumber);
			/**************************************************昨日相关统计     end****************************************************/
           
            
            pr.setState(true);
            pr.setMessage("获取今日实时销售成功");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.info(e);
        }
        return pr;
	}
	/**
	 * 商家分析-在售商品基本情况
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryOnSaleProductDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("stationed_user_id"))) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            String year = sdf.format(new Date());
            paramMap.put("year", year);//年份
          	paramMap.put("season_id", getSeasonId());//季节id
            float count = stationedAnalysisDao.r_queryStationed_OutSeasonDateItemCount(paramMap);
            //货号数,sku数,品牌数
            Map<String,Object> retMap = stationedAnalysisDao.r_queryStationed_ItemSkuBrandCount(paramMap);
            
            float itemCount = retMap.get("ITEM_COUNT")==null?0:Float.parseFloat(retMap.get("ITEM_COUNT").toString());
            //过时/过季商品占比
            if(itemCount > 0) {
            	retMap.put("RATIO", 100*count/itemCount);
			}else {
				retMap.put("RATIO", 0);
			}
            pr.setState(true);
			pr.setMessage("获取商家在售商品基本情况成功");
			pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 商品结构-基本情况
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductBasic(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("stationed_user_id"))) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            Map<String, Object> retMap = new HashMap<String, Object>();
            //累计货号数
            float itemCount= stationedAnalysisDao.r_queryStationed_Cumulative_ItemCount(paramMap);
            retMap.put("ITEM_COUNT", itemCount);
            //累计SKU数量
            float skuCount= stationedAnalysisDao.r_queryStationed_Cumulative_SkuCount(paramMap);
            retMap.put("SKU_COUNT", skuCount);
            //累计品牌数
            float brandCount= stationedAnalysisDao.r_queryStationed_Cumulative_BrandCount(paramMap);
            retMap.put("BRAND_COUNT", brandCount);
            
            pr.setState(true);
			pr.setMessage("获取商品结构基本情况成功");
			pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 商品结构-商品类型占比
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductTypeRatio(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("stationed_user_id"))) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
            //查询商品分类货号数
            List<Map<String, Object>> list = stationedAnalysisDao.r_queryStationed_ProductType_ItemCount_Chart(paramMap);
            int index = 0;
            float cnt = 0;
        	for(Map<String, Object> map :list) {
            	if(index >= 4) {
            		float val = map.get("CNT")==null?0:Float.parseFloat(map.get("CNT").toString());
            		cnt+=val;
            		index++;
            	}else {
            		resultList.add(map);
            		index++;
            	}
        	}
            if(index >= 4) {
            	Map<String, Object> m = new HashMap<String, Object>();
            	m.put("PAGE_NAME", "其他");
            	m.put("CNT", cnt);
            	resultList.add(m);
            }
          	
            pr.setState(true);
			pr.setMessage("获取商品类型占比成功");
			pr.setObj(resultList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.info(e);
        }
        return pr;
	}
	/**
     * 商家分析-商品类型列表
     * @param request
     * @return
     */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductTypeList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("stationed_user_id"))) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            //查询商家 所有货号数
            int count = stationedAnalysisDao.r_queryStationed_ItemCount(paramMap);
            //查询商品分类货号数
            List<Map<String, Object>> list = stationedAnalysisDao.r_queryStationed_ProductType_ItemCount(paramMap);
            
        	for(Map<String, Object> map : list) {
            	float itemCount = Float.parseFloat(map.get("ITEM_COUNT").toString());
            	if(count > 0) {
            		map.put("RATIO", 100*itemCount/count);
    			}else {
    				map.put("RATIO", 0);
    			}
        	}
            
            pr.setState(true);
			pr.setMessage("获取商品类型占比成功");
			pr.setObj(list);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.info(e);
        }
        return pr;
	}
	/**
	 * 商家分析-商品结构占比
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductStructureRatio(HttpServletRequest request) {
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
            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            
            if("1".equals(paramMap.get("type").toString())) {//年份占比
            	list = stationedAnalysisDao.r_queryStationed_ProductType_Year_Chart(paramMap);
            }else if("2".equals(paramMap.get("type").toString())) {//品牌占比
            	list = stationedAnalysisDao.r_queryStationed_ProductType_Brand_Chart(paramMap);
            }else if("3".equals(paramMap.get("type").toString())) {//季节占比
            	list = stationedAnalysisDao.r_queryStationed_ProductType_Season_Chart(paramMap);
            }else if("4".equals(paramMap.get("type").toString())) {//分类占比
            	list = stationedAnalysisDao.r_queryStationed_ProductType_Classify_Chart(paramMap);
            }else if("5".equals(paramMap.get("type").toString())) {//性别占比
            	list = stationedAnalysisDao.r_queryStationed_ProductType_Gender_Chart(paramMap);
            }else if("6".equals(paramMap.get("type").toString())) {//风格款式占比
            	list = stationedAnalysisDao.r_queryStationed_ProductType_Style_Chart(paramMap);
            }else if("7".equals(paramMap.get("type").toString())) {//价格分布占比
            	//获取 平均值和标准差
            	Map<String, Object> stdevMap = stationedAnalysisDao.r_queryProductStddevAvg(paramMap);
            	DecimalFormat df = new DecimalFormat("0.00");
            	double stdevValue = stdevMap.get("STDDEV_VALUE")==null?0:Double.parseDouble(stdevMap.get("STDDEV_VALUE").toString());
            	double avgValue = stdevMap.get("AVG_VALUE")==null?0:Double.parseDouble(stdevMap.get("AVG_VALUE").toString());
            	double first_start = avgValue+(stdevValue*-3)<0?0:avgValue+(stdevValue*-3);
            	double first_end = avgValue+(stdevValue*-1.28)<0?0:avgValue+(stdevValue*-1.28);
                
            	Map<String, Object> map = new HashMap<String, Object>();
            	//第一区间
            	map.put("PAGE_NAME", df.format(first_start)+"-"+df.format(first_end));
               	map.put("CNT", "10");
               	resultList.add(map);
               	
               	double second_start = first_end;
               	double second_end = avgValue+(stdevValue*-0.39)<0?0:avgValue+(stdevValue*-0.39);
               	map = new HashMap<String, Object>();
            	//第二区间
            	map.put("PAGE_NAME", df.format(second_start)+"-"+df.format(second_end));
               	map.put("CNT", "25");
               	resultList.add(map);
               	
               	double third_start = second_end;
               	double third_end = avgValue+(stdevValue*0.39);
               	map = new HashMap<String, Object>();
            	//第三区间
            	map.put("PAGE_NAME", df.format(third_start)+"-"+df.format(third_end));
               	map.put("CNT", "30");
               	resultList.add(map);
               	
               	double fourth_start = third_end;
               	double fourth_end = avgValue+(stdevValue*1.28);
               	map = new HashMap<String, Object>();
            	//第三区间
            	map.put("PAGE_NAME", df.format(fourth_start)+"-"+df.format(fourth_end));
               	map.put("CNT", "25");
               	resultList.add(map);
               	
               	double fifth_start = fourth_end;
               	double fifth_end = avgValue+(stdevValue*3);
               	map = new HashMap<String, Object>();
            	//第三区间
            	map.put("PAGE_NAME", df.format(fifth_start)+"-"+df.format(fifth_end));
               	map.put("CNT", "10");
               	resultList.add(map);
            }else {
            	pr.setState(false);
            	pr.setMessage("未知类型值");
            	return pr;
            }
            
            int index = 0;
            float cnt = 0;
        	for(Map<String, Object> map :list) {
            	if(index >= 5) {
            		float val = map.get("CNT")==null?0:Float.parseFloat(map.get("CNT").toString());
            		cnt+=val;
            		index++;
            	}else {
            		resultList.add(map);
            		index++;
            	}
        	}
            if(index >= 5) {
            	Map<String, Object> m = new HashMap<String, Object>();
            	m.put("PAGE_NAME", "其他");
            	m.put("CNT", cnt);
            	resultList.add(m);
            }
          	
            pr.setState(true);
			pr.setMessage("获取商品结构占比成功");
			pr.setObj(resultList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.info(e);
        }
        return pr;
	}
	/**
	 * 商家分析-商品结构列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryProductStructureList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
	        if(paramMap == null) {
            	gr.setState(false);
            	gr.setMessage("缺少参数");
            	return gr;
            }
	        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
	        int total = 0;
	        //商家所有货号数
	        int count = stationedAnalysisDao.r_queryStationed_ItemCount(paramMap);
	        if("1".equals(paramMap.get("type").toString())) {//年份占比
	        	total = stationedAnalysisDao.r_queryStationed_ProductType_YearCount(paramMap);
	        	resultList = stationedAnalysisDao.r_queryStationed_ProductType_YearList(paramMap);
            }else if("2".equals(paramMap.get("type").toString())) {//品牌占比
            	total = stationedAnalysisDao.r_queryStationed_ProductType_BrandCount(paramMap);
            	resultList = stationedAnalysisDao.r_queryStationed_ProductType_BrandList(paramMap);
            }else if("3".equals(paramMap.get("type").toString())) {//季节占比
            	total = stationedAnalysisDao.r_queryStationed_ProductType_SeasonCount(paramMap);
            	resultList = stationedAnalysisDao.r_queryStationed_ProductType_SeasonList(paramMap);
            }else if("4".equals(paramMap.get("type").toString())) {//分类占比
            	total = stationedAnalysisDao.r_queryStationed_ProductType_ClassifyCount(paramMap);
            	resultList = stationedAnalysisDao.r_queryStationed_ProductType_ClassifyList(paramMap);
            }else if("5".equals(paramMap.get("type").toString())) {//性别占比
            	total = stationedAnalysisDao.r_queryStationed_ProductType_GenderCount(paramMap);
            	resultList = stationedAnalysisDao.r_queryStationed_ProductType_GenderList(paramMap);
            }else if("6".equals(paramMap.get("type").toString())) {//风格款式占比
            	total = stationedAnalysisDao.r_queryStationed_ProductType_StyleCount(paramMap);
            	resultList = stationedAnalysisDao.r_queryStationed_ProductType_StyleList(paramMap);
            }else if("7".equals(paramMap.get("type").toString())) {//价格分布占比
            	total = 5;
            	//获取 平均值和标准差
            	Map<String, Object> stdevMap = stationedAnalysisDao.r_queryProductStddevAvg(paramMap);
            	DecimalFormat df = new DecimalFormat("0.00");
            	double stdevValue = stdevMap.get("STDDEV_VALUE")==null?0:Double.parseDouble(stdevMap.get("STDDEV_VALUE").toString());
            	double avgValue = stdevMap.get("AVG_VALUE")==null?0:Double.parseDouble(stdevMap.get("AVG_VALUE").toString());
            	double first_start = avgValue+(stdevValue*-3)<0?0:avgValue+(stdevValue*-3);
            	double first_end = avgValue+(stdevValue*-1.28)<0?0:avgValue+(stdevValue*-1.28);
                
            	Map<String, Object> map = new HashMap<String, Object>();
            	//第一区间
            	map.put("PAGE_NAME", df.format(first_start)+"-"+df.format(first_end));
               	map.put("RATIO", 10);
               	resultList.add(map);
               	
               	double second_start = first_end;
               	double second_end = avgValue+(stdevValue*-0.39)<0?0:avgValue+(stdevValue*-0.39);
               	map = new HashMap<String, Object>();
            	//第二区间
            	map.put("PAGE_NAME", df.format(second_start)+"-"+df.format(second_end));
               	map.put("RATIO", 25);
               	resultList.add(map);
               	
               	double third_start = second_end;
               	double third_end = avgValue+(stdevValue*0.39);
               	map = new HashMap<String, Object>();
            	//第三区间
            	map.put("PAGE_NAME", df.format(third_start)+"-"+df.format(third_end));
               	map.put("RATIO", 30);
               	resultList.add(map);
               	
               	double fourth_start = third_end;
               	double fourth_end = avgValue+(stdevValue*1.28);
               	map = new HashMap<String, Object>();
            	//第三区间
            	map.put("PAGE_NAME", df.format(fourth_start)+"-"+df.format(fourth_end));
               	map.put("RATIO", 25);
               	resultList.add(map);
               	
               	double fifth_start = fourth_end;
               	double fifth_end = avgValue+(stdevValue*3);
               	map = new HashMap<String, Object>();
            	//第三区间
            	map.put("PAGE_NAME", df.format(fifth_start)+"-"+df.format(fifth_end));
               	map.put("RATIO", 10);
               	resultList.add(map);
            }else {
            	gr.setState(false);
            	gr.setMessage("未知类型值");
            	return gr;
            }
			
			if(resultList != null && resultList.size() > 0) {
				if(!"7".equals(paramMap.get("type").toString())) {
					for(Map<String, Object> map : resultList) {
						float itemCount = Float.parseFloat(map.get("ITEM_COUNT").toString());
		            	if(count > 0) {
		            		map.put("RATIO", 100*itemCount/count);
		    			}else {
		    				map.put("RATIO", 0);
		    			}
					}
				}
				
				gr.setState(true);
				gr.setMessage("获取商品结构列表成功");
				gr.setObj(resultList);
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
	 * 商家分析-商品结构详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryProductStructureDetail(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
	        if(paramMap == null) {
            	gr.setState(false);
            	gr.setMessage("缺少参数");
            	return gr;
            }
	        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
	        int total = 0;
	        int type = Integer.parseInt(paramMap.get("type").toString());
	        if(type > 0) {
	        	total = stationedAnalysisDao.r_queryStationed_ProductStructure_ProductCount(paramMap);
	        	resultList = stationedAnalysisDao.r_queryStationed_ProductStructure_ProductList(paramMap);
            }else {
            	gr.setState(false);
            	gr.setMessage("未知类型值");
            	return gr;
            }
			
			if(resultList != null && resultList.size() > 0) {
				gr.setState(true);
				gr.setMessage("获取商品结构详情成功");
				gr.setObj(resultList);
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
	 * 商家分析-码段结构
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryCodeSegment(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null||StringUtils.isEmpty(paramMap.get("product_type_id"))) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            
            List<Map<String, Object>> resultList = stationedAnalysisDao.r_queryCodeSegmentList(paramMap);
          	
            pr.setState(true);
			pr.setMessage("获取码段结构成功");
			pr.setObj(resultList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.info(e);
        }
        return pr;
	}
	/**
	 * 销售-商品销量
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySalePayCount(HttpServletRequest request) {
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_count = 0;
        	/** 商品销量 */
        	t_count = stationedAnalysisDao.r_queryStationedTr_PayCount(paramMap);
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
            l_count = stationedAnalysisDao.r_queryStationedTr_PayCount(paramMap);
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
	 * 销售-商品销售总额
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySalePayMoney(HttpServletRequest request) {
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_money = 0;
        	/** 商品销售总额 */
            t_money = stationedAnalysisDao.r_queryStationedTr_PayMoney(paramMap);
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
			l_money = stationedAnalysisDao.r_queryStationedTr_PayCount(paramMap);
            if(t_money == 0 || l_money == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_money-l_money)/l_money*100));
            }
            resultMap.put("pay_money", t_money);
            
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
	 * 销售-支付买家数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySalePurchaseNumber(HttpServletRequest request) {
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_count = 0;
        	/** 支付买家数 */
            t_count = stationedAnalysisDao.r_queryStationed_PurchaseNumber(paramMap);
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
			l_count = stationedAnalysisDao.r_queryStationed_PurchaseNumber(paramMap);
            if(t_count == 0 || l_count == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_count-l_count)/l_count*100));
            }
            resultMap.put("purchase_number", t_count);
            
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
	 * 销售-未发退货数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySaleUnsentReturnCount(HttpServletRequest request) {
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_count = 0;
        	/** 未发退货数 */
            t_count = stationedAnalysisDao.r_queryStationedTr_UnsentReturnCount(paramMap);
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
			l_count = stationedAnalysisDao.r_queryStationedTr_UnsentReturnCount(paramMap);
            if(t_count == 0 || l_count == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_count-l_count)/l_count*100));
            }
            resultMap.put("unsent_return_count", t_count);
            
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
	 * 销售-已发退货数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySaleSentReturnCount(HttpServletRequest request) {
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_count = 0;
        	/** 已发退货数 */
            t_count = stationedAnalysisDao.r_queryStationedTr_SentReturnCount(paramMap);
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
			l_count = stationedAnalysisDao.r_queryStationedTr_SentReturnCount(paramMap);
            if(t_count == 0 || l_count == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_count-l_count)/l_count*100));
            }
            resultMap.put("sent_return_count", t_count);
            
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
	 * 销售-终检异常
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySaleFqcCount(HttpServletRequest request) {
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_count = 0;
        	/** 终检异常 */
            t_count = stationedAnalysisDao.r_queryStationedTr_FqcCount(paramMap);
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
			l_count = stationedAnalysisDao.r_queryStationedTr_FqcCount(paramMap);
            if(t_count == 0 || l_count == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_count-l_count)/l_count*100));
            }
            resultMap.put("fqc_count", t_count);
            
            pr.setState(true);
            pr.setMessage("获取终检异常成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 销售-售后异常
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySaleReturnCount(HttpServletRequest request) {
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_count = 0;
        	/** 售后异常 */
            t_count = stationedAnalysisDao.r_queryStationedTr_SaleReturnCount(paramMap);
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
			l_count = stationedAnalysisDao.r_queryStationedTr_SaleReturnCount(paramMap);
            if(t_count == 0 || l_count == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_count-l_count)/l_count*100));
            }
            resultMap.put("sale_return_count", t_count);
            
            pr.setState(true);
            pr.setMessage("获取售后异常成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 销售-新品数量
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySaleNewProductCount(HttpServletRequest request) {
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_count = 0;
        	/** 新品数量 */
            t_count = stationedAnalysisDao.r_queryStationed_NewProductCount(paramMap);
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
			l_count = stationedAnalysisDao.r_queryStationed_NewProductCount(paramMap);
            if(t_count == 0 || l_count == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_count-l_count)/l_count*100));
            }
            resultMap.put("new_product_count", t_count);
            
            pr.setState(true);
            pr.setMessage("获取新品数量成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 活动商品数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySaleActivityProductCount(HttpServletRequest request) {
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_count = 0;
        	/** 活动商品数量 */
            t_count = stationedAnalysisDao.r_queryStationed_ActivityProductCount(paramMap);
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
			l_count = stationedAnalysisDao.r_queryStationed_ActivityProductCount(paramMap);
            if(t_count == 0 || l_count == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_count-l_count)/l_count*100));
            }
            resultMap.put("activity_product_count", t_count);
            
            pr.setState(true);
            pr.setMessage("获取活动商品数量成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 查询销售分析折线
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySaleChart(HttpServletRequest request) {
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
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> purchaseNumberList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> newProductCountList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> activityProductCountList = new ArrayList<Map<String, Object>>();
            
            List<String> time_list = new ArrayList<String>();
            Map<String,Object> payCountMap = new HashMap<String,Object>();
            Map<String,Object> payMoneyMap = new HashMap<String,Object>();
            Map<String,Object> preFirstMoneyMap = new HashMap<String,Object>();
            Map<String,Object> purchaseNumberMap = new HashMap<String,Object>();
            Map<String,Object> unsentReturnCountMap = new HashMap<String,Object>();
            Map<String,Object> sentReturnCountMap = new HashMap<String,Object>();
            Map<String,Object> fqcCountMap = new HashMap<String,Object>();
            Map<String,Object> saleReturnCountMap = new HashMap<String,Object>();
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
            	/** 折线图 商品销量、商品销售总额、支付买家数 */
            	List<Map<String, Object>> payCountMoneyList = stationedAnalysisDao.r_queryStationedTr_PayCountMoneyD_Chart(paramMap);
            	/** 折线图 预定订单的定金 */
            	List<Map<String, Object>> preFirstMoneyList = stationedAnalysisDao.r_queryStationedTr_PreFirstMoneyD_Chart(paramMap);
            	/** 折线图 未发退货数 */
            	List<Map<String, Object>> unsentReturnCountList = stationedAnalysisDao.r_queryStationedTr_UnsentReturnCountD_Chart(paramMap);
            	/** 折线图 已发退货数 */
            	List<Map<String, Object>> sentReturnCountList = stationedAnalysisDao.r_queryStationedTr_SentReturnCountD_Chart(paramMap);
            	/** 折线图 终检异常 */
            	List<Map<String, Object>> fqcCountList = stationedAnalysisDao.r_queryStationedTr_FqcCountD_Chart(paramMap);
            	/** 折线图 售后异常 */
            	List<Map<String, Object>> saleReturnCountList = stationedAnalysisDao.r_queryStationedTr_SaleReturnCountD_Chart(paramMap);
            	/** 折线图 新品数量 */
                newProductCountList = stationedAnalysisDao.r_queryStationed_NewProductCountD_Chart(paramMap);
                /** 折线图 活动商品数 */
                activityProductCountList = stationedAnalysisDao.r_queryStationed_ActivityProductCountD_Chart(paramMap);
            	//商品销量
                payCountMap = list2Map(payCountMoneyList,"CREATE_DATE","PAY_COUNT");
                //商品销售总额
                payMoneyMap = list2Map(payCountMoneyList,"CREATE_DATE","PAY_MONEY");
                //预定订单的定金
                preFirstMoneyMap = list2Map(preFirstMoneyList,"CREATE_DATE","PRE_FIRST_MONEY");
                //支付买家数
                purchaseNumberMap = list2Map(payCountMoneyList,"CREATE_DATE","PURCHASE_NUMBER");
                //未发退货数
                unsentReturnCountMap = list2Map(unsentReturnCountList,"CREATE_DATE","UNSENT_RETURN_COUNT");
                //已发退货数
                sentReturnCountMap = list2Map(sentReturnCountList,"CREATE_DATE","SENT_RETURN_COUNT");
                //终检异常
                fqcCountMap = list2Map(fqcCountList,"CREATE_DATE","FQC_COUNT");
                //售后异常
                saleReturnCountMap = list2Map(saleReturnCountList,"CREATE_DATE","SALE_RETURN_COUNT");
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
                /** 商家 商家汇总 */
                list = stationedAnalysisDao.r_queryStationedSummary_Chart(paramMap);
                /** 折线图 支付买家数 */
                purchaseNumberList = stationedAnalysisDao.r_queryStationed_PurchaseNumber_Chart(paramMap);
                /** 折线图 新品数量 */
                newProductCountList = stationedAnalysisDao.r_queryStationed_NewProductCount_Chart(paramMap);
                /** 折线图 活动商品数 */
                activityProductCountList = stationedAnalysisDao.r_queryStationed_ActivityProductCount_Chart(paramMap);
                //商品销量
                payCountMap = list2Map(list,"CREATE_DATE","PAY_COUNT");
                //商品销售总额
                payMoneyMap = list2Map(list,"CREATE_DATE","PAY_MONEY");
                //支付买家数
                purchaseNumberMap = list2Map(purchaseNumberList,"CREATE_DATE","PURCHASE_NUMBER");
                //未发退货数
                unsentReturnCountMap = list2Map(list,"CREATE_DATE","UNSENT_RETURN_COUNT");
                //已发退货数
                sentReturnCountMap = list2Map(list,"CREATE_DATE","SENT_RETURN_COUNT");
                //终检异常
                fqcCountMap = list2Map(list,"CREATE_DATE","FQC_COUNT");
                //售后异常
                saleReturnCountMap = list2Map(list,"CREATE_DATE","SALE_RETURN_COUNT");
            }
            //新品数量
            Map<String,Object> newProductCountMap = list2Map(newProductCountList,"CREATE_DATE","NEW_PRODUCT_COUNT");
            //活动商品数
            Map<String,Object> activityProductCountMap = list2Map(activityProductCountList,"CREATE_DATE","ACTIVITY_PRODUCT_COUNT");
            
            //开始数据拼装
          	String key="";
          	
          	Map<String, Object> map = null;
            for(String time : time_list) {
            	key = time;
            	/** 商家-商品销量 */
                float payCount = payCountMap.get(key)==null?0:Float.parseFloat(payCountMap.get(key).toString());
                /** 商家-商品销售总额 */
                float payMoney = payMoneyMap.get(key)==null?0:Float.parseFloat(payMoneyMap.get(key).toString());
                /** 商家-支付买家数 */
                float purchaseNumber = purchaseNumberMap.get(key)==null?0:Float.parseFloat(purchaseNumberMap.get(key).toString());
                /** 商家-预定订单的定金*/
                float preFirstMoney = preFirstMoneyMap.get(key)==null?0:Float.parseFloat(preFirstMoneyMap.get(key).toString());
                /** 商家-未发退货数 */
                float unsentReturnCount = unsentReturnCountMap.get(key)==null?0:Float.parseFloat(unsentReturnCountMap.get(key).toString());
                /** 商家-已发退货数 */
                float sentReturnCount = sentReturnCountMap.get(key)==null?0:Float.parseFloat(sentReturnCountMap.get(key).toString());
                /** 商家-终检异常 */
                float fqcCount = fqcCountMap.get(key)==null?0:Float.parseFloat(fqcCountMap.get(key).toString());
                /** 商家-售后异常 */
                float saleReturnCount = saleReturnCountMap.get(key)==null?0:Float.parseFloat(saleReturnCountMap.get(key).toString());
                /** 商家-新品数量 */
                float newProductCount = newProductCountMap.get(key)==null?0:Float.parseFloat(newProductCountMap.get(key).toString());
                /** 商家-活动商品数 */
                float activityProductCount = activityProductCountMap.get(key)==null?0:Float.parseFloat(activityProductCountMap.get(key).toString());
                payMoney+=preFirstMoney;
                map = new HashMap<String, Object>();
                //商品销量
              	map.put("CREATE_DATE", key);
              	map.put("CNT", payCount);
              	map.put("PAGE_NAME", "商品销量");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//商品销售总额
              	map.put("CREATE_DATE", key);
              	map.put("CNT", payMoney);
              	map.put("PAGE_NAME", "商品销售总额");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//支付买家数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", purchaseNumber);
              	map.put("PAGE_NAME", "支付买家数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//未发退货数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", unsentReturnCount);
              	map.put("PAGE_NAME", "未发退货数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//已发退货数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", sentReturnCount);
              	map.put("PAGE_NAME", "已发退货数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//终检异常
              	map.put("CREATE_DATE", key);
              	map.put("CNT", fqcCount);
              	map.put("PAGE_NAME", "终检异常");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//售后异常
              	map.put("CREATE_DATE", key);
              	map.put("CNT", saleReturnCount);
              	map.put("PAGE_NAME", "售后异常");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//新品数量
              	map.put("CREATE_DATE", key);
              	map.put("CNT", newProductCount);
              	map.put("PAGE_NAME", "新品数量");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//活动商品数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", activityProductCount);
              	map.put("PAGE_NAME", "活动商品数");
              	allDataList.add(map);
            }
            
            //将原始数据组装为Echart可识别的格式
            resultMap = createData("PAGE_NAME", time_list, allDataList);
            
            pr.setState(true);
			pr.setMessage("获取销售分析折线成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	/**
	 * 销售-商品列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult querySaleProductList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
	        if(paramMap == null) {
            	gr.setState(false);
            	gr.setMessage("缺少参数");
            	return gr;
            }
			/** 商家-商品总数 */
			int total = stationedAnalysisDao.r_queryStationedProductCount(paramMap);
			
			List<Map<String, Object>> resultList = stationedAnalysisDao.r_queryStationedProductList(paramMap);
			if(resultList != null && resultList.size() > 0) {
				gr.setState(true);
				gr.setMessage("获取商品列表成功");
				gr.setObj(resultList);
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
	 * 商品列表-供应商查看
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult querySaleSupplierList(HttpServletRequest request) {
        GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
	        if(paramMap == null||StringUtils.isEmpty(paramMap.get("itemnumber"))) {
            	gr.setState(false);
            	gr.setMessage("缺少参数");
            	return gr;
            }
			/** 商品-供应商总数 */
			int total = stationedAnalysisDao.r_queryProductSupplierCount(paramMap);
			
			List<Map<String, Object>> resultList = stationedAnalysisDao.r_queryProductSupplierList(paramMap);
			if(resultList != null && resultList.size() > 0) {
				gr.setState(true);
				gr.setMessage("获取供应商查看成功");
				gr.setObj(resultList);
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
	 * 区域销售分析排行
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
            if(paramMap == null) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            paramMap.put("num", 5);
            /** 销售总数 */
            float count = stationedAnalysisDao.r_queryStationedTr_PayCount(paramMap);
        	/** 省份 销售数量 */
            List<Map<String, Object>> provinceList = stationedAnalysisDao.r_queryStationed_PayCountToProvince(paramMap);
            /** 城市 销售数量 */
            List<Map<String, Object>> cityList = stationedAnalysisDao.r_queryStationed_PayCountToCity(paramMap);
            for(Map<String, Object> p : provinceList) {
            	float payCount = Float.parseFloat(p.get("PAY_COUNT").toString());
            	p.put("RATIO", 100*payCount/count);
            }
        	for(Map<String, Object> c : cityList) {
            	float payCount = Float.parseFloat(c.get("PAY_COUNT").toString());
            	c.put("RATIO", 100*payCount/count);
            }
            
            resultMap.put("provinceList", provinceList);
            resultMap.put("cityList", cityList);
            pr.setState(true);
			pr.setMessage("获取区域销售排行成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 区域销售分析详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult querySaleAreaDetail(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
	        if(paramMap == null) {
            	gr.setState(false);
            	gr.setMessage("缺少参数");
            	return gr;
            }
			/** 区域销售 商品总数 */
			int total = stationedAnalysisDao.r_queryStationed_ProductCount(paramMap);
			/** 区域销售 商品列表 */
			List<Map<String, Object>> resultList = stationedAnalysisDao.r_queryStationed_ProductList(paramMap);
			if(resultList != null && resultList.size() > 0) {
				gr.setState(true);
				gr.setMessage("获取区域销售详情成功");
				gr.setObj(resultList);
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
	 * 区域销售分析地图
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
            if(paramMap == null) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            
            List<Map<String, Object>> resultList = stationedAnalysisDao.r_querySaleAreaMap(paramMap);
            pr.setState(true);
			pr.setMessage("获取区域销售分析地图成功");
			pr.setObj(resultList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 售后/品质问题分析 折线图
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryReturnQualityChart(HttpServletRequest request) {
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
            List<Map<String, Object>> allDataList = new ArrayList<Map<String, Object>>();
            Map<String, Object> map = null;
            // 折线图 售后/品质问题分析
            Map<String, Object> returnMap = stationedAnalysisDao.r_queryReturnQualityChart(paramMap);
            //未发退货数
            float unsentReturnCount= returnMap.get("UNSENT_RETURN_COUNT")==null?0:Float.parseFloat(returnMap.get("UNSENT_RETURN_COUNT").toString());;
            //政策内退货数
            float policyReturnCount= returnMap.get("POLICY_RETURN_COUNT")==null?0:Float.parseFloat(returnMap.get("POLICY_RETURN_COUNT").toString());;
            //品质问题退货数
            float qualityReturnCount= returnMap.get("QUALITY_RETURN_COUNT")==null?0:Float.parseFloat(returnMap.get("QUALITY_RETURN_COUNT").toString());;
            //终检异常数
            float fqcCount= returnMap.get("FQC_COUNT")==null?0:Float.parseFloat(returnMap.get("FQC_COUNT").toString());;
            
            map = new HashMap<String, Object>();
            map.put("PAGE_NAME", "未发退货");
          	map.put("CNT", unsentReturnCount);
          	allDataList.add(map);
          	
          	
            map = new HashMap<String, Object>();
            map.put("PAGE_NAME", "政策内");
          	map.put("CNT", policyReturnCount);
          	allDataList.add(map);
          	
          	map = new HashMap<String, Object>();
            map.put("PAGE_NAME", "品质问题");
          	map.put("CNT", qualityReturnCount);
          	allDataList.add(map);
          	
          	
            map = new HashMap<String, Object>();
            map.put("PAGE_NAME", "终检异常");
          	map.put("CNT", fqcCount);
          	allDataList.add(map);
            
            
            pr.setState(true);
			pr.setMessage("获取售后/品质问题分析折线成功");
			pr.setObj(allDataList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
	}
	/**
	 * 售后/品质问题分析 列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryReturnQualityList(HttpServletRequest request) {
        GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
	        if(paramMap == null) {
            	gr.setState(false);
            	gr.setMessage("缺少参数");
            	return gr;
            }
			/** 售后/品质问题分析 */
			int total = stationedAnalysisDao.r_queryReturnQualityCount(paramMap);
			float count = stationedAnalysisDao.r_queryReturnQualityProductCount(paramMap);
			List<Map<String, Object>> resultList = stationedAnalysisDao.r_queryReturnQualityList(paramMap);
			if(resultList != null && resultList.size() > 0) {
				for(Map<String, Object> r :resultList) {
	            	//问题原因 商品数
	            	float productCount = Float.parseFloat(r.get("PRODUCT_COUNT").toString());
	            	//占比
	            	if(count==0||productCount==0){
	              		r.put("RATIO", 0);
	              	}else{
	              		float ratio = 100*(productCount/count);
	              		r.put("RATIO", ratio);
	              	}
	            }
				gr.setState(true);
				gr.setMessage("获取品质退货问题列表成功");
				gr.setObj(resultList);
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
	 * 供应商-生产计划数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySupplierProductionCount(HttpServletRequest request) {
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_count = 0;
        	/** 生产计划数 */
            t_count = stationedAnalysisDao.r_querySupplier_ProductionCount(paramMap);
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
			l_count = stationedAnalysisDao.r_querySupplier_ProductionCount(paramMap);
            if(t_count == 0 || l_count == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_count-l_count)/l_count*100));
            }
            resultMap.put("production_count", t_count);
            
            pr.setState(true);
            pr.setMessage("获取生产计划数成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 供应商-入库数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySupplierInstorageCount(HttpServletRequest request) {
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_count = 0;
        	/** 入库数 */
            t_count = stationedAnalysisDao.r_querySupplier_InstorageCount(paramMap);
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
			l_count = stationedAnalysisDao.r_querySupplier_InstorageCount(paramMap);
            if(t_count == 0 || l_count == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_count-l_count)/l_count*100));
            }
            resultMap.put("instorage_count", t_count);
            
            pr.setState(true);
            pr.setMessage("获取入库数成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 供应商-出货数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySupplierOutProductCount(HttpServletRequest request) {
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_count = 0;
        	/** 出货数 */
            t_count = stationedAnalysisDao.r_querySupplier_OutProductCount(paramMap);
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
			l_count = stationedAnalysisDao.r_querySupplier_OutProductCount(paramMap);
            if(t_count == 0 || l_count == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_count-l_count)/l_count*100));
            }
            resultMap.put("out_product_count", t_count);
            
            pr.setState(true);
            pr.setMessage("获取出货数成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 供应商-终检异常
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySupplierFqcCount(HttpServletRequest request) {
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_count = 0;
        	/** 终检异常 */
            t_count = stationedAnalysisDao.r_querySupplier_FqcCount(paramMap);
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
			l_count = stationedAnalysisDao.r_querySupplier_FqcCount(paramMap);
            if(t_count == 0 || l_count == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_count-l_count)/l_count*100));
            }
            resultMap.put("fqc_count", t_count);
            
            pr.setState(true);
            pr.setMessage("获取终检异常成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 供应商-售后异常
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySupplierSaleReturnCount(HttpServletRequest request) {
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date startDate=sdf.parse(paramMap.get("start_date").toString());
        	Date endDate=sdf.parse(paramMap.get("end_date").toString());
        	int day=(int) ((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        	if(day == 0) {//day为0时，则开始时间和结束时间为同一天
        		day = 1;
        	}
            float t_count = 0;
        	/** 售后异常 */
            t_count = stationedAnalysisDao.r_querySupplier_SaleReturnCount(paramMap);
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
			l_count = stationedAnalysisDao.r_querySupplier_SaleReturnCount(paramMap);
            if(t_count == 0 || l_count == 0) {
            	resultMap.put("ratio", 0);
            }else {
            	resultMap.put("ratio", m2((t_count-l_count)/l_count*100));
            }
            resultMap.put("sale_return_count", t_count);
            
            pr.setState(true);
            pr.setMessage("获取售后异常成功!");
            pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	/**
	 * 供应商-折线图
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySupplierChart(HttpServletRequest request) {
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
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            List<String> time_list = new ArrayList<String>();
            Map<String,Object> productionCountMap = new HashMap<String,Object>();
            Map<String,Object> instorageCountMap = new HashMap<String,Object>();
            Map<String,Object> outProductCountMap = new HashMap<String,Object>();
            Map<String,Object> fqcCountMap = new HashMap<String,Object>();
            Map<String,Object> saleReturnCountMap = new HashMap<String,Object>();
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
            	/** 折线图 生产计划数 */
            	List<Map<String, Object>> productionCountList = stationedAnalysisDao.r_querySupplier_ProductionCountD_Chart(paramMap);
            	/** 折线图 入库数 */
            	List<Map<String, Object>> instorageCountList = stationedAnalysisDao.r_querySupplier_InstorageCountD_Chart(paramMap);
            	/** 折线图 出货数 */
            	List<Map<String, Object>> outProductCountList = stationedAnalysisDao.r_querySupplier_OutProductCountD_Chart(paramMap);
            	/** 折线图 终检异常 */
            	List<Map<String, Object>> fqcCountList = stationedAnalysisDao.r_querySupplier_FqcCountD_Chart(paramMap);
            	/** 折线图 售后异常 */
            	List<Map<String, Object>> saleReturnCountList = stationedAnalysisDao.r_querySupplier_SaleReturnCountD_Chart(paramMap);
            	
            	//生产计划数
                productionCountMap = list2Map(productionCountList,"CREATE_DATE","PRODUCTION_COUNT");
                //入库数
                instorageCountMap = list2Map(instorageCountList,"CREATE_DATE","INSTORAGE_COUNT");
                //出货数
                outProductCountMap = list2Map(outProductCountList,"CREATE_DATE","OUT_PRODUCT_COUNT");
                //终检异常
                fqcCountMap = list2Map(fqcCountList,"CREATE_DATE","FQC_COUNT");
                //售后异常
                saleReturnCountMap = list2Map(saleReturnCountList,"CREATE_DATE","SALE_RETURN_COUNT");
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
                /** 折线图 供应商汇总 */
                list = stationedAnalysisDao.r_querySupplierSummary_Chart(paramMap);
                
                //生产计划数
                productionCountMap = list2Map(list,"CREATE_DATE","PRODUCTION_COUNT");
                //入库数
                instorageCountMap = list2Map(list,"CREATE_DATE","INSTORAGE_COUNT");
                //出货数
                outProductCountMap = list2Map(list,"CREATE_DATE","OUT_PRODUCT_COUNT");
                //终检异常
                fqcCountMap = list2Map(list,"CREATE_DATE","FQC_COUNT");
                //售后异常
                saleReturnCountMap = list2Map(list,"CREATE_DATE","SALE_RETURN_COUNT");
            }
            
            //开始数据拼装
          	String key="";
          	
          	Map<String, Object> map = null;
            for(String time : time_list) {
            	key = time;
            	/** 供应商-生产计划数 */
                float productionCount = productionCountMap.get(key)==null?0:Float.parseFloat(productionCountMap.get(key).toString());
                /** 供应商-入库数 */
                float instorageCount = instorageCountMap.get(key)==null?0:Float.parseFloat(instorageCountMap.get(key).toString());
                /** 供应商-出货数 */
                float outProductCount = outProductCountMap.get(key)==null?0:Float.parseFloat(outProductCountMap.get(key).toString());
                /** 供应商-终检异常 */
                float fqcCount = fqcCountMap.get(key)==null?0:Float.parseFloat(fqcCountMap.get(key).toString());
                /** 供应商-售后异常 */
                float saleReturnCount = saleReturnCountMap.get(key)==null?0:Float.parseFloat(saleReturnCountMap.get(key).toString());
                
                map = new HashMap<String, Object>();
                //生产计划数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", productionCount);
              	map.put("PAGE_NAME", "生产计划数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//入库数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", instorageCount);
              	map.put("PAGE_NAME", "入库数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//出货数
              	map.put("CREATE_DATE", key);
              	map.put("CNT", outProductCount);
              	map.put("PAGE_NAME", "出货数");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//终检异常
              	map.put("CREATE_DATE", key);
              	map.put("CNT", fqcCount);
              	map.put("PAGE_NAME", "终检异常");
              	allDataList.add(map);
              	
              	map = new HashMap<String, Object>();
              	//售后异常
              	map.put("CREATE_DATE", key);
              	map.put("CNT", saleReturnCount);
              	map.put("PAGE_NAME", "售后异常");
              	allDataList.add(map);
            }
            
            //将原始数据组装为Echart可识别的格式
            resultMap = createData("PAGE_NAME", time_list, allDataList);
            
            pr.setState(true);
			pr.setMessage("获取商家汇总信息折线成功");
			pr.setObj(resultMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("messageError:",e);
        }
        return pr;
	}
	/**
	 * 供应商-列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult querySupplierList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
	        if(paramMap == null) {
            	gr.setState(false);
            	gr.setMessage("缺少参数");
            	return gr;
            }
			/** 供应商总数 */
			int total = stationedAnalysisDao.r_querySupplierCount(paramMap);
			/** 供应商列表 */
			List<Map<String, Object>> resultList = stationedAnalysisDao.r_querySupplierList(paramMap);
			if(resultList != null && resultList.size() > 0) {
				gr.setState(true);
				gr.setMessage("获取供应商列表成功");
				gr.setObj(resultList);
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
	 * 供应商-关联商品列表(无时间限制)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult querySupplierProductList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
	        if(paramMap == null) {
            	gr.setState(false);
            	gr.setMessage("缺少参数");
            	return gr;
            }
			/** 供应商商品总数 */
			int total = stationedAnalysisDao.r_querySupplierProductCount(paramMap);
			/** 供应商商品列表 */
			List<Map<String, Object>> resultList = stationedAnalysisDao.r_querySupplierProductList(paramMap);
			if(resultList != null && resultList.size() > 0) {
				gr.setState(true);
				gr.setMessage("获取商品列表成功");
				gr.setObj(resultList);
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
	 * 供应商-关联商品列表(有时间限制)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult querySupplierProductListByDate(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
	        if(paramMap == null||StringUtils.isEmpty(paramMap.get("type"))) {
            	gr.setState(false);
            	gr.setMessage("缺少参数");
            	return gr;
            }
	        int total = 0;
	        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
	        List<String> list = new ArrayList<String>();
	        if("instorage".equals(paramMap.get("type").toString())){//入库
	        	total = stationedAnalysisDao.r_querySupplierInstorageProductCount(paramMap);
	        	resultList = stationedAnalysisDao.r_querySupplierInstorageList(paramMap);
	        }else if("out_product".equals(paramMap.get("type").toString())) {//出货
	        	total = stationedAnalysisDao.r_querySupplierOutProductProductCount(paramMap);
	        	resultList = stationedAnalysisDao.r_querySupplierOutProductList(paramMap);
	        }else if("fqc".equals(paramMap.get("type").toString())) {//终检
	        	total = stationedAnalysisDao.r_querySupplierFqcProductCount(paramMap);
	        	resultList = stationedAnalysisDao.r_querySupplierFqcList(paramMap);
	        }else if("sale_return".equals(paramMap.get("type").toString())) {//售后
	        	total = stationedAnalysisDao.r_querySupplierSaleReturnProductCount(paramMap);
	        	resultList = stationedAnalysisDao.r_querySupplierSaleReturnList(paramMap);
	        }else {
	        	gr.setState(false);
            	gr.setMessage("未知类型值");
            	return gr;
	        }
			
			if(resultList != null && resultList.size() > 0) {
				for(Map<String, Object> r : resultList) {
					list.add(r.get("ITEMNUMBER").toString());
				}
				//库存量
				List<Map<String,Object>> stock_list = stationedAnalysisDao.queryProductStockCount(list);
				Map<String,Object> stock_map = list2Map(stock_list,"ITEMNUMBER","STOCK_COUNT");
				Map<String, Object> map = new HashMap<String, Object>();
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");  
				Calendar c = Calendar.getInstance();  
				c.add(Calendar.DATE, -1);  
				String start_date = sdf.format(c.getTime());  
		        map.put("start_date", start_date);
		        
		        c = Calendar.getInstance();  
				c.add(Calendar.DATE, -30);
				String end_date = sdf.format(c.getTime());  
				map.put("end_date", end_date);
				map.put("list", list);
				//近30天销量
				List<Map<String,Object>> sale_list = stationedAnalysisDao.r_queryProductSaleList(map);
				Map<String,Object> sale_map = list2Map(sale_list,"ITEMNUMBER","PAY_COUNT");
				
				for(Map<String, Object> r : resultList) {
    	        	r.put("STOCK_COUNT", stock_map.get(r.get("ITEMNUMBER").toString())==null?0:Float.parseFloat(stock_map.get(r.get("ITEMNUMBER").toString()).toString()));
    	            r.put("PAY_COUNT", sale_map.get(r.get("ITEMNUMBER").toString())==null?0:Float.parseFloat(sale_map.get(r.get("ITEMNUMBER").toString()).toString()));
    	        }
				gr.setState(true);
				gr.setMessage("获取商品列表成功");
				gr.setObj(resultList);
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
	 * 商品类型下拉框
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductTypeOption(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            List<Map<String, Object>> resultList = stationedAnalysisDao.r_queryProductTypeOption(paramMap);
            if(resultList != null && resultList.size() > 0) {
				pr.setState(true);
				pr.setMessage("获取商品分类成功");
				pr.setObj(resultList);
			}else {
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
	 * 获取季节id
	 * @return
	 */
	public int getSeasonId(){
	    int seasonNumber = Calendar.getInstance().get(Calendar.MONTH);
	    return seasonNumber>=1&&seasonNumber<=3?1:seasonNumber>=4&&seasonNumber<=6?2:seasonNumber>=7&&seasonNumber<=9?3:seasonNumber>=10?4:4;
	}
	/**
     * Float类型 保留两位小数
     */
    public static String m2(float v) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(v);
    }

}
