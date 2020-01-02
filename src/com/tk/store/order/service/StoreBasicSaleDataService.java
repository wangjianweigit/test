package com.tk.store.order.service;

import com.tk.oms.member.dao.DistributorManageDao;
import com.tk.store.order.dao.StoreBasicSaleDataDao;
import com.tk.sys.config.EsbConfig;
import com.tk.sys.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Copyright (c), 2018,  TongKu
 * FileName : StoreBasicSaleDataService
 *
 * @author: liujialong
 * @version: 1.00
 * @date: 2018/9/19
 */
@Service("StoreBasicSaleDataService")
public class StoreBasicSaleDataService {

    @Resource
    private StoreBasicSaleDataDao storeBasicSaleDataDao;
    @Value("${store_service_url}")
    private String store_service_url;


    /**
     * 查询门店下拉数据(新零售门店ID)
     * @param request
     * @return
     */
    public ProcessResult queryStoreList(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
            pr.setState(true);
            pr.setMessage("查询成功！");
            pr.setObj(storeBasicSaleDataDao.queryStoreListOption(paramMap));
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage("查询失败！");
        }

        return pr;
    }

    /**
     * 获取销售基础数据列表
     * @param request
     * @return
     */
    public GridResult queryBasicSaleDataForPage(HttpServletRequest request) {
        GridResult gr = new GridResult();
        List<Map<String, Object>> list; //经销商列表
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if(pageParamResult!=null){
                return pageParamResult;
            }
            Map<String, Object> sendMap = new HashMap<String, Object>();
            sendMap.put("PAGE_SIZE",params.get("pageSize"));
            sendMap.put("INDEX",params.get("pageIndex"));
            //传商家id查当前商家数据  不传则查询当前登录用户下的所有商家数据
            Map<String, Object> agentIdMap=storeBasicSaleDataDao.queryAgentId(params);
            if(agentIdMap==null){
            	gr.setState(true);
            	gr.setMessage("无数据");
            	return gr;
            }
            sendMap.put("AGENT_ID",agentIdMap.get("AGENT_ID"));
            if(params.containsKey("ORDER_ID") && !StringUtils.isEmpty(params.get("ORDER_ID"))){
                sendMap.put("ORDER_ID",params.get("ORDER_ID"));
            }
            if(params.containsKey("ORDER_SOURCE") && !StringUtils.isEmpty(params.get("ORDER_SOURCE"))){
                sendMap.put("ORDER_SOURCE",params.get("ORDER_SOURCE"));
            }
            if(params.containsKey("USER_NAME") && !StringUtils.isEmpty(params.get("USER_NAME"))){
                sendMap.put("USER_NAME",params.get("USER_NAME"));
            }
            if(params.containsKey("ITEMNUMBER") && !StringUtils.isEmpty(params.get("ITEMNUMBER"))){
                sendMap.put("ITEMNUMBER",params.get("ITEMNUMBER"));
            }
            if(params.containsKey("STORE_ID") && !StringUtils.isEmpty(params.get("STORE_ID"))){
                sendMap.put("STORE_ID",storeBasicSaleDataDao.queryAgentStoreId(params));
            }
            if(params.containsKey("STAFF_ID") && !StringUtils.isEmpty(params.get("STAFF_ID"))){
                sendMap.put("STAFF_ID",params.get("STAFF_ID"));
            }
            if(params.containsKey("STORE_SALES_ID") && !StringUtils.isEmpty(params.get("STORE_SALES_ID"))){
                sendMap.put("STORE_SALES_ID",params.get("STORE_SALES_ID"));
            }
            if(params.containsKey("CREATE_TIME_START") && !StringUtils.isEmpty(params.get("CREATE_TIME_START"))){
                sendMap.put("CREATE_TIME_START",params.get("CREATE_TIME_START"));
            }
            if(params.containsKey("CREATE_TIME_END") && !StringUtils.isEmpty(params.get("CREATE_TIME_END"))){
                sendMap.put("CREATE_TIME_END",params.get("CREATE_TIME_END"));
            }
            if(params.containsKey("ORDER_STATE") && !StringUtils.isEmpty(params.get("ORDER_STATE"))){
            	if(params.get("ORDER_STATE") instanceof String){
            		sendMap.put("ORDER_STATE",(params.get("ORDER_STATE")+"").split(","));
            	}else{
            		sendMap.put("ORDER_STATE",params.get("ORDER_STATE"));
            	}
            }
            if(params.containsKey("UNIQUE_CODE") && !StringUtils.isEmpty(params.get("UNIQUE_CODE"))){
                sendMap.put("UNIQUE_CODE",params.get("UNIQUE_CODE"));
            }
            if(params.containsKey("YEAR") && !StringUtils.isEmpty(params.get("YEAR"))){
                sendMap.put("YEAR",params.get("YEAR"));
            }
            Map<String, Object> resPr= (Map<String, Object>) queryForPost(sendMap,store_service_url + "order/Order/orderSaleBasisData");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
                gr.setState(true);
                gr.setMessage("获取销售订单列表成功");
                Map<String, Object> data = (Map<String, Object>)resPr.get("data");
                List<Map<String, Object>> dataList=(List<Map<String, Object>>)data.get("data");
                if(dataList!=null&&dataList.size()>0){
                	//查询商家相关信息
                	List<Map<String, Object>> userList=storeBasicSaleDataDao.queryUserInfoByAgentId(dataList);
                    for(Map<String, Object> map:dataList){
                    	for(Map<String, Object> userMap:userList){
                    		if(Integer.parseInt(map.get("AGENT_ID")+"")==Integer.parseInt(userMap.get("AGENT_ID")+"")){
                    			map.put("USER_MANAGE_NAME", userMap.get("USER_MANAGE_NAME"));
                    			map.put("PARTNER_USER_REALNA", userMap.get("PARTNER_USER_REALNA"));
                            	map.put("YWJL_USER_NAME", userMap.get("YWJL_USER_NAME"));
                            	map.put("YWY_USER_NAME", userMap.get("YWY_USER_NAME"));
                            	map.put("MD_NAME", userMap.get("MD_NAME"));
                            	break;
                    		}
                    	}
                    }
                }
                int total=Integer.parseInt(data.get("total").toString());
                gr.setTotal(total);
                gr.setObj(dataList);
            }else{
            	gr.setState(false);
            	gr.setMessage(resPr.get("message")+"");
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
        String json = HttpClientUtil.post(url, params,30000);
        return Transform.GetPacketJzb(json,Map.class);
    }
    
    /**
	 * 查询商家下拉数据(新零售经销商ID)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAgentId(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
            pr.setState(true);
            pr.setMessage("查询成功！");
            pr.setObj(storeBasicSaleDataDao.queryAgentIdOption(paramMap));
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage("查询失败！");
        }

        return pr;
	}

	public ProcessResult exportExcel(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
            
            Map<String, Object> sendMap = new HashMap<String, Object>();
            sendMap.put("PAGE_SIZE",0);
            sendMap.put("INDEX",0);
            //传商家id查当前商家数据  不传则查询当前登录用户下的所有商家数据
            Map<String, Object> agentIdMap=storeBasicSaleDataDao.queryAgentId(params);
            if(agentIdMap==null){
            	pr.setState(true);
            	pr.setMessage("无数据");
            	return pr;
            }
            sendMap.put("AGENT_ID",agentIdMap.get("AGENT_ID"));
            if(params.containsKey("ORDER_ID") && !StringUtils.isEmpty(params.get("ORDER_ID"))){
                sendMap.put("ORDER_ID",params.get("ORDER_ID"));
            }
            if(params.containsKey("ORDER_SOURCE") && !StringUtils.isEmpty(params.get("ORDER_SOURCE"))){
                sendMap.put("ORDER_SOURCE",params.get("ORDER_SOURCE"));
            }
            if(params.containsKey("USER_NAME") && !StringUtils.isEmpty(params.get("USER_NAME"))){
                sendMap.put("USER_NAME",params.get("USER_NAME"));
            }
            if(params.containsKey("ITEMNUMBER") && !StringUtils.isEmpty(params.get("ITEMNUMBER"))){
                sendMap.put("ITEMNUMBER",params.get("ITEMNUMBER"));
            }
            if(params.containsKey("STORE_ID") && !StringUtils.isEmpty(params.get("STORE_ID"))){
                sendMap.put("STORE_ID",storeBasicSaleDataDao.queryAgentStoreId(params));
            }
            if(params.containsKey("STAFF_ID") && !StringUtils.isEmpty(params.get("STAFF_ID"))){
                sendMap.put("STAFF_ID",params.get("STAFF_ID"));
            }
            if(params.containsKey("STORE_SALES_ID") && !StringUtils.isEmpty(params.get("STORE_SALES_ID"))){
                sendMap.put("STORE_SALES_ID",params.get("STORE_SALES_ID"));
            }
            if(params.containsKey("CREATE_TIME_START") && !StringUtils.isEmpty(params.get("CREATE_TIME_START"))){
                sendMap.put("CREATE_TIME_START",params.get("CREATE_TIME_START"));
            }
            if(params.containsKey("CREATE_TIME_END") && !StringUtils.isEmpty(params.get("CREATE_TIME_END"))){
                sendMap.put("CREATE_TIME_END",params.get("CREATE_TIME_END"));
            }
            if(params.containsKey("ORDER_STATE") && !StringUtils.isEmpty(params.get("ORDER_STATE"))){
            	if(params.get("ORDER_STATE") instanceof String){
            		sendMap.put("ORDER_STATE",(params.get("ORDER_STATE")+"").split(","));
            	}else{
            		sendMap.put("ORDER_STATE",params.get("ORDER_STATE"));
            	}
            }
            if(params.containsKey("UNIQUE_CODE") && !StringUtils.isEmpty(params.get("UNIQUE_CODE"))){
                sendMap.put("UNIQUE_CODE",params.get("UNIQUE_CODE"));
            }
            if(params.containsKey("YEAR") && !StringUtils.isEmpty(params.get("YEAR"))){
                sendMap.put("YEAR",params.get("YEAR"));
            }
            sendMap.put("IS_EXCEL","1");
            Map<String, Object> resPr= (Map<String, Object>) queryForPost(sendMap,store_service_url + "order/Order/orderSaleBasisData");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
                pr.setState(true);
                pr.setMessage("导出excel成功");
                pr.setObj(resPr.get("data"));
            }else{
            	pr.setState(false);
            	pr.setMessage(resPr.get("message")+"");
            }
            
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}



}

    
    
