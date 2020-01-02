package com.tk.oms.analysis.service;

import com.tk.oms.analysis.dao.ChannelHeatDao;
import com.tk.sys.util.DateUtils;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 频道热度分析
 * @author zhenghui
 */
@Service("ChannelHeatService")
public class ChannelHeatService {

    @Resource
    private ChannelHeatDao channelHeatDao;

    private String[] colors={"#2f4554 ", "#61a0a8 ", "#c23531", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF", "#001cf1", "#000000", "#50d400", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF","#001cf1", "#000000", "#50d400", "#f000e8", "#3af3e8", "#ff4e00", "#7e00ff", "#0064d4", "#8f00d4", "#2B2B2B", "#1E90FF", "#00FF00", "#9400D3",
            "#CD0000", "#CD853F", "#EE1289", "#FFFF00", "#FF00FF"};

    /**
     * 统计用户日志数量
     * @param request
     * @return
     */
    public ProcessResult queryLogsNumberByPageType(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        List<String> time_list = new ArrayList<String>();//存储所有的时间数据
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少请求参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);

            String start_time = params.get("start_time").toString();        //开始时间
            String end_time = params.get("end_time").toString();            //结束时间
            Date start_time_date = DateUtils.parse(start_time, DateUtils.DATE_FORMAT_YYYYMMDD);
            Date end_time_date = DateUtils.parse(end_time, DateUtils.DATE_FORMAT_YYYYMMDD);
            String tempDate = "";
            while (end_time_date.compareTo(start_time_date) >= 0) {
                tempDate = DateUtils.format(start_time_date, DateUtils.DATE_FORMAT_YYYYMMDD);
                time_list.add(tempDate);
                start_time_date = DateUtils.addDay(start_time_date, 1);
            }
            //查询角色列表总数
            List<Map<String, Object>> list = channelHeatDao.queryLogsNumberByPageType(params);
            //将原始数据组装为Echart可识别的格式
            Map<String, Object> resultMap = createData("PAGE_NAME", time_list, list);
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
     * 根据时间轴以及实际数组组装Echart的数据格式
     *
     * @param time_list
     *            时间轴点集合
     * @param data_list
     *            数据集合
     *            标题
     *            搜索的结果，货号，颜色，码段，尺码等
     * @return
     */
    private Map<String, Object> createData(String seriesName,List<String> time_list,List<Map<String, Object>> data_list) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<Map<String, Object>> seriesData = new ArrayList<Map<String, Object>>();
        Map<String, Map<String, Object>> dataMap = new HashMap<String, Map<String, Object>>();
        Map<String, Object> dataMapVal = null;// 存储一个货号的数据列表
        List<Double> dataList = null;
        Set<String> seriesNameSet = new HashSet<String>();
        if(data_list!=null && !data_list.isEmpty()){//有数据
            for (Map<String, Object> data : data_list) {
                String PRODUCT_ITEMNUMBER = data.get(seriesName) == null ? null: data.get(seriesName).toString();
                seriesNameSet.add(PRODUCT_ITEMNUMBER);
                String ORDER_DATE = data.get("CREATE_DATE") == null ? DateUtils.format(new Date(), DateUtils.DATE_FORMAT_YYYYMMDD) : data.get("CREATE_DATE").toString();
                double COUNT = data.get("CNT") == null ? 0 : Double.parseDouble(data.get("CNT").toString());
                if (dataMap.containsKey(PRODUCT_ITEMNUMBER)) {// 已存在直接put
                    dataMapVal = dataMap.get(PRODUCT_ITEMNUMBER);
                    dataMapVal.put(ORDER_DATE, COUNT);
                } else {
                    dataMapVal = new HashMap<String, Object>();
                    dataMapVal.put(ORDER_DATE, COUNT);
                    dataMap.put(PRODUCT_ITEMNUMBER, dataMapVal);
                }
            }
            //考虑有些货号销量完全为0的情况，再进行一次
            int i=0;
            for (String PRODUCT_ITEMNUMBER : dataMap.keySet()) {
                dataMapVal = dataMap.get(PRODUCT_ITEMNUMBER);
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
                seriesDataMap.put("name", PRODUCT_ITEMNUMBER);
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
