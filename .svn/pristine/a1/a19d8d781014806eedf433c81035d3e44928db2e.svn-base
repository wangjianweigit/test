package com.tk.store.statistic.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.store.statistic.dao.LyHomeWorkbenchDao;
import com.tk.sys.config.EsbConfig;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpClientUtil;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.Jackson;
import com.tk.sys.util.Packet;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Service("LyHomeWorkbenchService")
public class LyHomeWorkbenchService {
	
	@Value("${store_service_url}")
    private String store_service_url;
	
	@Resource
	private LyHomeWorkbenchDao lyHomeWorkbenchDao;
	
	/**
	 * 查询联营工作台统计数据
	 * @param request
	 * @return
	 */
	public GridResult queryStatisticData(HttpServletRequest request) {
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
            sendMap.put("AGENT_ID",lyHomeWorkbenchDao.queryStoreListByArea(params));
            if(params.containsKey("ITEM_ID") && !StringUtils.isEmpty(params.get("ITEM_ID"))){
                sendMap.put("ITEM_ID",params.get("ITEM_ID"));
            }
            if(params.containsKey("YEAR") && !StringUtils.isEmpty(params.get("YEAR"))){
                sendMap.put("YEAR",params.get("YEAR"));
            }
            if(params.containsKey("SEASON") && !StringUtils.isEmpty(params.get("SEASON"))){
                sendMap.put("SEASON",params.get("SEASON"));
            }
            if(params.containsKey("START_TIME") && !StringUtils.isEmpty(params.get("START_TIME"))){
                sendMap.put("START_TIME",params.get("START_TIME"));
            }
            if(params.containsKey("END_TIME") && !StringUtils.isEmpty(params.get("END_TIME"))){
                sendMap.put("END_TIME",params.get("END_TIME"));
            }
            if(!StringUtils.isEmpty(params.get("TYPE"))){
            	if(params.get("TYPE") instanceof String){
            		sendMap.put("TYPE",(params.get("TYPE")+"").split(","));
            	}else{
            		sendMap.put("TYPE",(params.get("TYPE")));
            	}
			}
            Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, store_service_url+"order/Summary/agentStoreSummary");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
            	Map<String, Object> resMap=(Map<String, Object>) resPr.get("data");
            	//查询调拨任务数量
            	resMap.put("storeAllotTask", lyHomeWorkbenchDao.queryStoreAllotTask(params));
            	if(params.containsKey("ITEM_ID") && !StringUtils.isEmpty(params.get("ITEM_ID"))){
            		params.put("ITEMNUMBER", params.get("ITEM_ID"));
            		Map<String, Object> productInfo=lyHomeWorkbenchDao.queryProductInfo(params);
            		if(productInfo!=null){
            			resMap.put("ITEMNUMBER", productInfo.get("ITEMNUMBER"));
                		resMap.put("PRODUCT_NAME", productInfo.get("PRODUCT_NAME"));
                		resMap.put("PRODUCT_IMG_URL", productInfo.get("PRODUCT_IMG_URL"));
            		}
            	}
                gr.setState(true);
                gr.setMessage("获取数据列表成功");
                gr.setObj(resMap);
            }else{
            	gr.setState(false);
                gr.setMessage(resPr.get("message").toString());
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
	}
	
	/**
	 * 查询联营工作台销售详情
	 * @param request
	 * @return
	 */
	public GridResult queryLySalesDetail(HttpServletRequest request) {
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
            sendMap.put("ORDER_STATE",2);
            sendMap.put("ITEM_FIND",1);
            if(params.containsKey("AGENT_ID") && !StringUtils.isEmpty(params.get("AGENT_ID"))){
                sendMap.put("AGENT_ID",params.get("AGENT_ID"));
            }
            if(params.containsKey("ITEM_ID") && !StringUtils.isEmpty(params.get("ITEM_ID"))){
                sendMap.put("ITEMNUMBER",params.get("ITEM_ID"));
            }
            if(params.containsKey("YEAR") && !StringUtils.isEmpty(params.get("YEAR"))){
                sendMap.put("YEAR",params.get("YEAR"));
            }
            if(params.containsKey("SEASON") && !StringUtils.isEmpty(params.get("SEASON"))){
                sendMap.put("SEASON",params.get("SEASON"));
            }
            if(params.containsKey("START_TIME") && !StringUtils.isEmpty(params.get("START_TIME"))){
                sendMap.put("MIN_DATE",params.get("START_TIME"));
            }
            if(params.containsKey("END_TIME") && !StringUtils.isEmpty(params.get("END_TIME"))){
                sendMap.put("MAX_DATE",params.get("END_TIME"));
            }
            Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, store_service_url+"order/Order/orderList");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
            	Map<String, Object> resMap=(Map<String, Object>) resPr.get("data");
            	int total=Integer.parseInt(resMap.get("TOTAL").toString());
                gr.setState(true);
                gr.setMessage("获取数据列表成功");
                gr.setObj(resMap);
                gr.setTotal(total);
            }else{
            	gr.setState(false);
                gr.setMessage(resPr.get("message").toString());
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
	 * 联营工作台添加调拨任务
	 * @param request
	 * @return
	 */
	@Transactional
	public ProcessResult addAllotTask(HttpServletRequest request)throws Exception {
		ProcessResult pr = new ProcessResult();

        try {
        	Map<String, Object> paramMap = new HashMap<String, Object>();
    	    String json = HttpUtil.getRequestInputStream(request);
    	    paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
    	    if(StringUtils.isEmpty(paramMap.get("out_store_id"))){
    	    	pr.setState(false);
    	    	pr.setMessage("缺少参数【调出门店】");
    	    	return pr;
    	    }
    	    if(StringUtils.isEmpty(paramMap.get("in_store_id"))){
    	    	pr.setState(false);
    	    	pr.setMessage("缺少参数【调入门店】");
    	    	return pr;
    	    }
    	    //将经销商门店id转换为本地id
    	    paramMap.put("agent_store_id", paramMap.get("out_store_id"));
    	    paramMap.put("out_store_id", lyHomeWorkbenchDao.queryStoreIdByAgentStoreId(paramMap));
    	    paramMap.put("agent_store_id", paramMap.get("in_store_id"));
    	    paramMap.put("in_store_id", lyHomeWorkbenchDao.queryStoreIdByAgentStoreId(paramMap));
    	    //根据调入调出门店查询调拨任务信息
    	    Map<String, Object> storeAllotTask= lyHomeWorkbenchDao.queryStoreAllotTaskByInOutStoreId(paramMap);
    	    if(storeAllotTask==null){
    	    	//新增调拨单主表信息
                int  num = lyHomeWorkbenchDao.insertStoreAllotTask(paramMap);
                if(num<0){
                	throw new RuntimeException("新增调拨单主表信息失败");
                }
                //新增挑拨单详细信息
                paramMap.put("task_id", paramMap.get("id"));
    	    }else{
    	    	paramMap.put("task_id", storeAllotTask.get("ID"));
    	    }
    	    //查询当前调拨任务单里货号是否已存在
    	    Map<String, Object> storeAllotTaskDetail=lyHomeWorkbenchDao.queryStoreAllotTaskDetailByTaskIdAndItemnumber(paramMap);
    	    if(storeAllotTaskDetail==null){
    	    	int count =lyHomeWorkbenchDao.insertStoreAllotTaskDetail(paramMap);
                if(count<0){
                	throw new RuntimeException("新增挑拨单详细信息失败");
                }
    	    }
            pr.setState(true);
            pr.setMessage("新增调拨任务信息成功！");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
	}
	
	/**
	 * 联营工作台查询门店库存
	 * @param request
	 * @return
	 */
	public GridResult queryStoreStock(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> resMap=new HashMap<String,Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            Map<String, Object> sendMap = new HashMap<String, Object>();
            sendMap.put("SIMPLE_FETCH", 1);
            sendMap.put("GROUP_BY",1);
            if(params.containsKey("type") && !StringUtils.isEmpty(params.get("type"))){
            	sendMap.put("AGENT_ID",params.get("agent_id"));
            	sendMap.put("STORE_ID",params.get("store_id"));
            }else{
            	//查询商家id
            	List<String> user_ids=lyHomeWorkbenchDao.queryStoreListByArea(params);
            	sendMap.put("AGENT_ID",user_ids);
            	//根据商家id查询门店id
            	params.put("user_ids", user_ids);
            	List<String> store_ids=lyHomeWorkbenchDao.queryUserStores(params);
            	sendMap.put("STORE_ID",store_ids);
            }
            if(params.containsKey("ITEMNUMBER") && !StringUtils.isEmpty(params.get("ITEMNUMBER"))){
                sendMap.put("ITEMNUMBER",params.get("ITEMNUMBER"));
            }
            Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, store_service_url+"stock/Stock/lyStockDetail");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
            	List<Map<String, Object>> res=(List<Map<String, Object>>) resPr.get("data");
            	resMap.put("data", res);
            	if(params.containsKey("ITEMNUMBER") && !StringUtils.isEmpty(params.get("ITEMNUMBER"))){
            		Map<String, Object> productInfo=lyHomeWorkbenchDao.queryProductInfo(params);
            		if(productInfo!=null){
            			resMap.put("ITEMNUMBER", productInfo.get("ITEMNUMBER"));
                		resMap.put("PRODUCT_NAME", productInfo.get("PRODUCT_NAME"));
                		resMap.put("PRODUCT_IMG_URL", productInfo.get("PRODUCT_IMG_URL"));
            		}else{
            			resMap.put("ITEMNUMBER","");
                		resMap.put("PRODUCT_NAME","");
                		resMap.put("PRODUCT_IMG_URL","");
            		}
            	}
                gr.setState(true);
                gr.setMessage("获取数据列表成功");
                gr.setObj(resMap);
            }else{
            	gr.setState(false);
                gr.setMessage(resPr.get("message").toString());
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
	}
	
	/**
	 * 联营工作台查询调拨任务列表
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryStoreAllotTaskList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
			if(pageParamResult!=null){
				return pageParamResult;
			}
			List<Map<String, Object>> list = lyHomeWorkbenchDao.queryStoreAllotTaskList(paramMap);
			int count = lyHomeWorkbenchDao.queryStoreAllotTask(paramMap);
			if (list != null && list.size() > 0) {
				gr.setMessage("获取数据成功");
				gr.setObj(list);
			} else {
				gr.setMessage("无数据");
			}
			gr.setState(true);
			gr.setTotal(count);
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	
	/**
	 * 联营工作台查询调拨任务详情列表
	 * @param request
	 * @return
	 */
	public GridResult queryStoreAllotTaskDetailList(HttpServletRequest request) {
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
            sendMap.put("GROUP_BY",1);
            if(params.containsKey("AGENT_ID") && !StringUtils.isEmpty(params.get("AGENT_ID"))){
                sendMap.put("AGENT_ID",params.get("AGENT_ID"));
            }
            if(params.containsKey("STORE_ID") && !StringUtils.isEmpty(params.get("STORE_ID"))){
                sendMap.put("STORE_ID",params.get("STORE_ID"));
            }
            List<String> itemList=lyHomeWorkbenchDao.queryStoreAllotTaskItemnumber(params);
            if(itemList.size()==0){
            	gr.setState(true);
                gr.setMessage("无数据");
                gr.setObj(itemList);
                return gr;
            }
            sendMap.put("ITEMNUMBER",itemList);
            Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, store_service_url+"stock/Stock/lyStockDetail");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
            	List<Map<String, Object>> resList=(List<Map<String, Object>>) resPr.get("data");
            	Map<String, Object> tempMap=new HashMap<String,Object>();
            	for(Map<String, Object> res:resList){
            		Map<String, Object> stock =(Map<String, Object>) res.get("stock");
            		for(String key : stock.keySet()){
	    			   String item_store=res.get("ITEM_ID")+"_"+key;
	            	   tempMap.put(item_store,stock.get(key));
	    			}
            	}
            	//查询当前调拨任务单的所有货号信息
            	List<Map<String,Object>> storeAllotTaskItemnumberInfoList=lyHomeWorkbenchDao.queryStoreAllotTaskItemnumberInfoList(params);
            	for(Map<String,Object>storeAllotTaskItemnumberInfo:storeAllotTaskItemnumberInfoList){
            		boolean in_store_contains = tempMap.containsKey(storeAllotTaskItemnumberInfo.get("ITEM_IN_STORE"));    //判断是否包含指定的键值
            		boolean out_store_contains = tempMap.containsKey(storeAllotTaskItemnumberInfo.get("ITEM_OUT_STORE")); 
            		if(in_store_contains){
            			storeAllotTaskItemnumberInfo.put("IN_STORE_STOCK", tempMap.get(storeAllotTaskItemnumberInfo.get("ITEM_IN_STORE")));
            		}else{
            			storeAllotTaskItemnumberInfo.put("IN_STORE_STOCK", 0);
            		}
            		if(out_store_contains){
            			storeAllotTaskItemnumberInfo.put("OUT_STORE_STOCK", tempMap.get(storeAllotTaskItemnumberInfo.get("ITEM_OUT_STORE")));
            		}else{
            			storeAllotTaskItemnumberInfo.put("OUT_STORE_STOCK", 0);
            		}
            	}
                gr.setState(true);
                gr.setMessage("获取数据列表成功");
                gr.setObj(storeAllotTaskItemnumberInfoList);
                gr.setTotal(itemList.size());
            }else{
            	gr.setState(false);
                gr.setMessage(resPr.get("message").toString());
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
	}
	
	/**
	 * 联营工作台查询调拨任务撤销
	 * @param request
	 * @return
	 */
	@Transactional
	public ProcessResult revocatStoreAllotTask(HttpServletRequest request)throws Exception {
		ProcessResult pr = new ProcessResult();

        try {
        	Map<String, Object> paramMap = new HashMap<String, Object>();
    	    String json = HttpUtil.getRequestInputStream(request);
    	    paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
    	    if(!paramMap.containsKey("task_id") || StringUtils.isEmpty(paramMap.get("task_id"))) {
	   			 pr.setState(false);
	   			 pr.setMessage("缺少参数task_id");
	   			 return pr;
   		  	}
    	    //查询调拨任务详情
    	    Map<String, Object> storeAllotTask= lyHomeWorkbenchDao.queryStoreAllotTaskDetail(paramMap);
    	    if(storeAllotTask==null){
    	    	 pr.setState(false);
	   			 pr.setMessage("当前调拨任务不存在");
	   			 return pr;
    	    }
    	    //删除主表信息
    	    int count =lyHomeWorkbenchDao.deleteStoreAllotTask(paramMap);
            if(count<0){
            	throw new RuntimeException("删除主表信息失败");
            }
            //删除详情表信息
    	    lyHomeWorkbenchDao.deleteStoreAllotTaskDetail(paramMap);
            pr.setState(true);
            pr.setMessage("撤销成功！");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
	}
	
	/**
	 * 联营工作台查询调拨任务撤销
	 * @param request
	 * @return
	 */
	@Transactional
	public ProcessResult deleteStoreAllotTaskDetail(HttpServletRequest request)throws Exception {
		ProcessResult pr = new ProcessResult();

        try {
        	Map<String, Object> paramMap = new HashMap<String, Object>();
    	    String json = HttpUtil.getRequestInputStream(request);
    	    paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
    	    if(!paramMap.containsKey("id") || StringUtils.isEmpty(paramMap.get("id"))) {
	   			 pr.setState(false);
	   			 pr.setMessage("缺少参数id");
	   			 return pr;
   		  	}
            //删除详情表信息
    	    int count =lyHomeWorkbenchDao.deleteStoreAllotTaskDetail(paramMap);
            if(count<0){
            	throw new RuntimeException("删除详情表信息失败");
            }
            pr.setState(true);
            pr.setMessage("删除成功！");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
	}
}
