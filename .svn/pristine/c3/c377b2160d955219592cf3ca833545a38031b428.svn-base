package com.tk.oms.marketing.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tk.oms.marketing.dao.RetailActivityDao;
import com.tk.sys.config.EsbConfig;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpClientUtil;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.Jackson;
import com.tk.sys.util.Packet;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.Transform;

/**
 * Copyright (c), 2019, TongKu
 * FileName : RetailActivityService
 * 新零售营销活动
 *
 * @author liujialong
 * @version 1.00
 * @date 2019/07/08
 */
@Service("RetailActivityService")
public class RetailActivityService {
	
	@Value("${retail_service_new_url}")
    private String retail_service_new_url;
	@Resource
    private RetailActivityDao retailActivityDao;
	
	/**
     * 新零售营销活动列表
     * @param request
     * @return
     * @throws Exception
     */
    public GridResult queryRetailActivityListForPage(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            Map<String, Object> sendMap = new HashMap<String, Object>();
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            sendMap.put("PAGE_SIZE",params.get("pageSize"));
            sendMap.put("INDEX",params.get("pageIndex"));
            List<Map<String, Object>> list = this.retailActivityDao.queryDistributorList(params);
            if (list != null && list.size() > 0) {
            	String account_ids="";
            	for(int i=0;i<list.size();i++){
                 	if(i==list.size()-1){
                 		account_ids+=list.get(i).get("ID");
                 	}else{
                 		account_ids+=list.get(i).get("ID")+",";
                 	}
                 }
            	sendMap.put("AGENT_ID", account_ids);
            }
            if(params.containsKey("marketing_type")&& !StringUtils.isEmpty(params.get("marketing_type"))){
                sendMap.put("MARKETING_TYPE",params.get("marketing_type"));
            }
            if(params.containsKey("start_time")&& !StringUtils.isEmpty(params.get("start_time"))){
                sendMap.put("START_TIME",params.get("start_time"));
            }
            if(params.containsKey("end_time")&& !StringUtils.isEmpty(params.get("end_time"))){
                sendMap.put("END_TIME",params.get("end_time"));
            }
            if((!StringUtils.isEmpty(params.get("marketing_status")))&&params.get("marketing_status") instanceof String){
            	sendMap.put("MARKETING_STATUS",(params.get("marketing_status")+"").split(","));
			}
            Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, retail_service_new_url+"marketing/Activity/activityList");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
                gr.setState(true);
                gr.setMessage("获取数据列表成功");
                Map<String, Object> data =  (Map<String, Object>)resPr.get("data");
                List<Map<String, Object>> dataList=(List<Map<String, Object>>)data.get("data");
                //int total=dataList.size();
                int total=Integer.parseInt(data.get("total").toString());
                gr.setTotal(total);
                gr.setObj(dataList);
            }else{
            	gr.setState(true);
                gr.setMessage("无数据");
                gr.setObj("");
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
    }
    
    /**
     * 新零售营销活动详情
     * @param request
     * @return
     * @throws Exception
     */
    public GridResult queryRetailActivityDetail(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            Map<String, Object> sendMap = new HashMap<String, Object>();
            if (StringUtils.isEmpty(params.get("agent_id"))) {
                gr.setState(false);
                gr.setMessage("缺少参数[AGENT_ID]");
                return gr;
            }
            if (StringUtils.isEmpty(params.get("product_group_id"))) {
                gr.setState(false);
                gr.setMessage("缺少参数[PRODUCT_GROUP_ID]");
                return gr;
            }
            if (StringUtils.isEmpty(params.get("marketing_type"))) {
                gr.setState(false);
                gr.setMessage("缺少参数[marketing_type]");
                return gr;
            }
            sendMap.put("AGENT_ID",params.get("agent_id"));
            if(Integer.parseInt(params.get("marketing_type")+"")==1){
            	sendMap.put("DISCOUNT_ID",params.get("product_group_id"));
            	//限时折扣
            	Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, retail_service_new_url+"marketing/Discount/discountRow");
                if (Integer.parseInt(resPr.get("state").toString()) == 1) {
                    gr.setState(true);
                    gr.setMessage("获取数据详情成功");
                    Map<String, Object> data =  (Map<String, Object>)resPr.get("data");
                    gr.setObj(data);
                }else{
                	gr.setState(true);
                    gr.setMessage("无数据");
                    gr.setObj("");
                }
            }else{
            	sendMap.put("PRODUCT_GROUP_ID",params.get("product_group_id"));
            	Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, retail_service_new_url+"marketing/Activity/productGroupInfo");
                if (Integer.parseInt(resPr.get("state").toString()) == 1) {
                    gr.setState(true);
                    gr.setMessage("获取数据详情成功");
                    Map<String, Object> data =  (Map<String, Object>)resPr.get("data");
                    gr.setObj(data);
                }else{
                	gr.setState(true);
                    gr.setMessage("无数据");
                    gr.setObj("");
                }
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
    }
    public Object queryForPost(Map<String,Object> obj,String url) throws Exception{
        String params = "";
        if(obj != null){
            Packet packet = Transform.GetResult(obj, EsbConfig.ERP_FORWARD_KEY_NAME);//加密数据
            params = Jackson.writeObject2Json(packet);//对象转json、字符串
        }
        //发送至服务端
        String json = HttpClientUtil.post(url, params);
        return Transform.GetPacketJzb(json,Map.class);
    }
    
    /**
     * 获取新零售活动商品详情
     * @param request
     * @return
     * @throws Exception
     */
    public GridResult queryRetailActivityProductDetailList(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            Map<String, Object> sendMap = new HashMap<String, Object>();
            sendMap.put("PAGE_SIZE",params.get("pageSize"));
            sendMap.put("INDEX",params.get("pageIndex"));
            if (StringUtils.isEmpty(params.get("agent_id"))) {
                gr.setState(false);
                gr.setMessage("缺少参数[AGENT_ID]");
                return gr;
            }
            if (StringUtils.isEmpty(params.get("product_group_id"))) {
                gr.setState(false);
                gr.setMessage("缺少参数[PRODUCT_GROUP_ID]");
                return gr;
            }
            if (StringUtils.isEmpty(params.get("marketing_type"))) {
                gr.setState(false);
                gr.setMessage("缺少参数[marketing_type]");
                return gr;
            }
            sendMap.put("AGENT_ID",params.get("agent_id"));
            sendMap.put("PROMOTION_ID",params.get("product_group_id"));
            if(Integer.parseInt(params.get("marketing_type")+"")==1){
            	sendMap.put("PROMOTION_TYPE",2);
            }else{
            	sendMap.put("PROMOTION_TYPE",4);
            }
            Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, retail_service_new_url+"marketing/Activity/activityGoodsList");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
                gr.setState(true);
                gr.setMessage("获取商品详情列表成功");
                Map<String, Object> data =  (Map<String, Object>)resPr.get("data");
                List<Map<String, Object>> dataList=(List<Map<String, Object>>)data.get("data");
                int total=Integer.parseInt(data.get("total").toString());
                gr.setTotal(total);
                gr.setObj(dataList);
            }else{
            	gr.setState(true);
                gr.setMessage("无数据");
                gr.setObj("");
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
    }
    
    /**
     * 获取店铺名称列表
     * @param request
     * @return
     * @throws Exception
     */
    public GridResult queryShopList(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            List<Map<String, Object>> list = this.retailActivityDao.queryShopList(params);
            if(list.size()>0){
            	gr.setObj(list);
            }else{
            	gr.setMessage("无数据");
            }
            gr.setMessage("查询成功");
            gr.setState(true);
            return gr;
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
    }
    
    /**
     * 获取新零售活动概括数据
     * @param request
     * @return
     * @throws Exception
     */
    public GridResult queryDataDetail(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            Map<String, Object> sendMap = new HashMap<String, Object>();
            List<Map<String, Object>> list = this.retailActivityDao.queryDistributorList(params);
            if (list != null && list.size() > 0) {
            	String account_ids="";
            	for(int i=0;i<list.size();i++){
                 	if(i==list.size()-1){
                 		account_ids+=list.get(i).get("ID");
                 	}else{
                 		account_ids+=list.get(i).get("ID")+",";
                 	}
                 }
            	sendMap.put("AGENT_ID", account_ids);
            }
            if(params.containsKey("start_time")&& !StringUtils.isEmpty(params.get("start_time"))){
                sendMap.put("START_TIME",params.get("start_time"));
            }
            if(params.containsKey("end_time")&& !StringUtils.isEmpty(params.get("end_time"))){
                sendMap.put("END_TIME",params.get("end_time"));
            }
            String postUrl="";
            if(Integer.parseInt(params.get("type")+"")==1){
            	postUrl="order/Summary/discountDataStatistical";
            }else{
            	postUrl="order/Summary/productGroupDataStatistical";
            }
            Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, retail_service_new_url+postUrl);
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
                gr.setState(true);
                gr.setMessage("获取数据列表成功");
                Map<String, Object> data =  (Map<String, Object>)resPr.get("data");
                gr.setObj(data);
            }else{
            	gr.setState(true);
                gr.setMessage("无数据");
                gr.setObj("");
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
    }
    
    /**
     * 获取获取活动工具数据
     * @param request
     * @return
     * @throws Exception
     */
    public GridResult queryToolManageInfo(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> sendMap = new HashMap<String, Object>();
            Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, retail_service_new_url+"marketing/Activity/activityManageInfo");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
                gr.setState(true);
                gr.setMessage("获取数据列表成功");
                Map<String, Object> data =  (Map<String, Object>)resPr.get("data");
                gr.setObj(data);
            }else{
            	gr.setState(true);
                gr.setMessage("无数据");
                gr.setObj("");
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
    }
    
    /**
     * 保存获取活动工具数据
     * @param request
     * @return
     * @throws Exception
     */
    public GridResult saveToolManageInfo(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            Map<String, Object> sendMap = ( Map<String, Object>)params.get("param");
            Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, retail_service_new_url+"marketing/Activity/setActivityManage");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
                gr.setState(true);
                gr.setMessage("保存成功");
            }else{
            	gr.setState(false);
                gr.setMessage("保存失败");
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
    }

}
