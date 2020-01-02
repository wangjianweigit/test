package com.tk.oms.analysis.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.tk.oms.analysis.dao.ProductAnalysisDao;
import com.tk.sys.util.DateUtils;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Service("ProductAnalysisService")
public class ProductAnalysisService {
	private Log logger = LogFactory.getLog(this.getClass());
	
	@Resource
	private ProductAnalysisDao productAnalysisDao;
	@Value("${jdbc_user}")
	private String jdbc_user;
	
	private String[] colors={"#2f4554 ", "#c23531 ", "#c23531", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF", "#001cf1", "#000000", "#50d400", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF","#001cf1", "#000000", "#50d400", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF"};
	/**
	 * 查询商品分析列表
	 * @param request
	 * @return
	 */
	public GridResult queryProductAnalysisList(HttpServletRequest request) {
		GridResult pr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			GridResult pageParamResult = PageUtil.handlePageParams(params);
			if(pageParamResult!=null){
				return pageParamResult;
			}
			if(StringUtils.isEmpty(params.get("product_type"))) {
				pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
			}
			int total = 0;
			List<Map<String,Object>> list = null;
			params.put("jdbc_user", jdbc_user);
			int productType = Integer.parseInt(params.get("product_type").toString());
			if(productType == 0) {//普通商品
				total = productAnalysisDao.queryProductAnalysisCount(params);
				list = productAnalysisDao.queryProductAnalysisList(params);
			}else {//私有商品
				total = productAnalysisDao.queryPvtpProductAnalysisCount(params);
				list = productAnalysisDao.queryPvtpProductAnalysisList(params);
			}
			pr.setState(true);
			pr.setMessage("获取商品分析列表数据成功");
			pr.setObj(list);
			pr.setTotal(total);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}
	
	
	
	/**
	 * 查询会员图表列表
	 * @param request
	 * @return
	 */
	public ProcessResult queryProductChartList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(StringUtils.isEmpty(paramMap.get("product_type"))) {
				pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
			}
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
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            int productType = Integer.parseInt(paramMap.get("product_type").toString());
            //商品分析列表
			if(productType == 0) {//普通商品
				list = productAnalysisDao.queryProductChartList(paramMap);
			}else {//私有商品
				list = productAnalysisDao.queryPvtpProductChartList(paramMap);
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

}
