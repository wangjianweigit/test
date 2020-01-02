package com.tk.oms.analysis.service;

import com.tk.oms.analysis.dao.UserDailyStatisticsDao;
import com.tk.sys.util.DateUtils;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : UserDailyStatisticsService
 * 用户日统计业务操作业务类
 * 
 * @author wangjianwei
 * @version 1.00
 * @date 2017/5/13 14:04
 */
@Service("UserDailyStatisticsService")
public class UserDailyStatisticsService{

    private Log logger = LogFactory.getLog(this.getClass());

    @Resource
    private UserDailyStatisticsDao userDailyStatisticsDao;//用户日统计日统计数据访问接口
    private String[] colors={"#2f4554 ", "#61a0a8 ", "#c23531", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF", "#001cf1", "#000000", "#50d400", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF","#001cf1", "#000000", "#50d400", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF"};

    /**
     * 每日统计
     * @param request
     * @return
     */
    public ProcessResult queryList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            List<String> time_list = new ArrayList<String>();//存储所有的时间数据
            String start_time = paramMap.get("start_time").toString();		//开始时间
            String end_time = paramMap.get("end_time").toString();			//结束时间
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
            List<Map<String, Object>> list = null;
            // 查询用户日统计--浏览分析
            if(paramMap.get("search_type").equals("flow")){
            	list = userDailyStatisticsDao.queryUserDilyStatisicsList_Flow(paramMap);
            }
            
            // 查询用户日统计--商品销售分析
            if(paramMap.get("search_type").equals("market")){
            	//订单数-订单商品数-所有支付金额 数据列表
	            List<Map<String, Object>> orderInfolist = userDailyStatisticsDao.queryUserDilyStatisicsList_OrderInfo(paramMap);
	            Map<String,Object> orderCountMap = list2Map(orderInfolist,"CREATE_DATE","ORDER_COUNT");
	            Map<String,Object> productCountMap = list2Map(orderInfolist,"CREATE_DATE","PRODUCT_COUNT");
	            Map<String,Object> orderMoneyMap = list2Map(orderInfolist,"CREATE_DATE","ORDER_MONEY");
	            //预订支付的首款 数据列表
	            List<Map<String, Object>> firstMoneyList = userDailyStatisticsDao.queryUserDilyStatisicsList_PreFirstMoney(paramMap);
	            Map<String,Object> firstMoneyMap = list2Map(firstMoneyList,"CREATE_DATE","CNT");
	            //尾款订单的定金金额 数据列表
	            List<Map<String, Object>> preOrderFirstMoneyList = userDailyStatisticsDao.queryUserDilyStatisicsList_PreOrderFirstMoney(paramMap);
	            Map<String,Object> preOrderFirstMoneyMap = list2Map(preOrderFirstMoneyList,"CREATE_DATE","CNT");
	            //开始数据拼装
	          	String key="";
	          	//所有支付金额 
	            float allPayMoney = 0;
	            //订单商品数
	            float productCount = 0 ;
	            //订单数
	            float orderCount =0 ;
	          	//品牌统计数据-预订支付的首款 
	            float preFirstMoney = 0;
	          	//品牌统计数据-尾款订单的定金金额 
	            float preOrderFirstMoney = 0;
	          	//计算支付金额    
	          	float money = 0;
	          	Map map = null;
	          	list = new ArrayList<Map<String, Object>>();
	            for(int i=0;i<time_list.size();i++){
	            	key = time_list.get(i);
	            	/** 订单数 */
	            	orderCount = orderCountMap.get(key)==null?0:Float.parseFloat(orderCountMap.get(key).toString());
	                /** 订单商品数 */
	            	productCount = productCountMap.get(key)==null?0:Float.parseFloat(productCountMap.get(key).toString());
	            	/** 所有支付金额 */
	                allPayMoney = orderMoneyMap.get(key)==null?0:Float.parseFloat(orderMoneyMap.get(key).toString());
	                /** 预订支付的首款 */
	                preFirstMoney = firstMoneyMap.get(key)==null?0:Float.parseFloat(firstMoneyMap.get(key).toString());
	                /** 尾款订单的定金金额 */
	                preOrderFirstMoney = preOrderFirstMoneyMap.get(key)==null?0:Float.parseFloat(preOrderFirstMoneyMap.get(key).toString());
	                
	                map = new HashMap<String, Object>();
	                //存储支付金额   
	                money = allPayMoney + preFirstMoney - preOrderFirstMoney;
	                map.put("TYPE_NAME", "订单金额");
	                map.put("CNT", money);
	                map.put("CREATE_DATE", key);
	                list.add(map);
	                
	                map = new HashMap<String, Object>();
	                //存储订单商品数
	                map.put("TYPE_NAME", "订单商品数");
	                map.put("CNT", productCount);
	                map.put("CREATE_DATE", key);
	                list.add(map);
	                
	                map = new HashMap<String, Object>();
	                //存储订单数
	                map.put("TYPE_NAME", "订单数");
	                map.put("CNT", orderCount);
	                map.put("CREATE_DATE", key);
	                list.add(map);
	            }
	            
            }
            //将原始数据组装为Echart可识别的格式
            Map<String, Object> resultMap = createData("TYPE_NAME", time_list, list);
            if (resultMap != null) {
                pr.setState(true);
                pr.setMessage("获取成功");
                pr.setObj(resultMap);
            } else {
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

    private Map<String, Object> createData(String seriesName,List<String> time_list,List<Map<String, Object>> data_list) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<Map<String, Object>> seriesData = new ArrayList<Map<String, Object>>();
        Map<String, Map<String, Object>> dataMap = new HashMap<String, Map<String, Object>>();
        Map<String, Object> dataMapVal = null;// 存储一个统计分组段的数据列表
        List<Double> dataList = null;
        Set<String> seriesNameSet = new HashSet<String>();
        if(data_list!=null && !data_list.isEmpty()){//有数据
            for (Map<String, Object> data : data_list) {
                String TYPE_NAME = data.get(seriesName) == null ? null: data.get(seriesName).toString();//分组统计类型
                seriesNameSet.add(TYPE_NAME);
                String ORDER_DATE = data.get("CREATE_DATE") == null ? DateUtils.format(new Date(), DateUtils.DATE_FORMAT_YYYYMMDD) : data.get("CREATE_DATE").toString();
                double COUNT = data.get("CNT") == null ? 0 : Double.parseDouble(data.get("CNT").toString());//分组中某天的统计数量
                if (dataMap.containsKey(TYPE_NAME)) {// 已存在直接put
                    dataMapVal = dataMap.get(TYPE_NAME);
                    dataMapVal.put(ORDER_DATE, COUNT);
                } else {
                    dataMapVal = new HashMap<String, Object>();
                    dataMapVal.put(ORDER_DATE, COUNT);
                    dataMap.put(TYPE_NAME, dataMapVal);
                }
            }
            //考虑有些货号销量完全为0的情况，再进行一次
            int i=0;
            for (String TYPE_NAME : dataMap.keySet()) {
                dataMapVal = dataMap.get(TYPE_NAME);
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
                seriesDataMap.put("name", TYPE_NAME);
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
}
