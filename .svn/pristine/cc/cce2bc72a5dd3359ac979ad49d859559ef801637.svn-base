package com.tk.store.stock.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.store.stock.dao.StockDao;
import com.tk.sys.config.EsbConfig;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpClientUtil;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.Jackson;
import com.tk.sys.util.Packet;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : StockService
 * 库存管理
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018-3-5
 */
@Service("StockService")
public class StockService {
	@Value("${store_service_url}")
	private String store_service_url;// 联营门店服务地址
	@Value("${stock_list}")
	private String stock_list;// 库存查询
	@Value("${ly_stock_detail}")
	private String ly_stock_detail;// 商品库存详情
	
	@Resource
	private StockDao stockDao;
	
	/**
	 * 库存查询
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryStockListForPage(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(StringUtils.isEmpty(paramMap.get("region_id"))){
				gr.setState(false);
				gr.setMessage("缺少区域ID");
				return gr;
			}
			List<String> stores = (List<String>) paramMap.get("stores");
			if(stores.size() > 0){
				param.put("index", paramMap.get("pageIndex"));
				param.put("pageSize", paramMap.get("pageSize"));
				param.put("AGENT_ID", stockDao.queryAgentId(paramMap));
				param.put("STORE_ID", paramMap.get("stores"));
				if(!StringUtils.isEmpty(paramMap.get("product_name"))){param.put("PRODUCT_NAME", paramMap.get("product_name"));}
				if(!StringUtils.isEmpty(paramMap.get("brand"))){param.put("BRAND", paramMap.get("brand"));}
				if(!StringUtils.isEmpty(paramMap.get("itemnumber"))){param.put("ITEM_ID", paramMap.get("itemnumber"));}
				if(!StringUtils.isEmpty(paramMap.get("year"))){param.put("YEAR", paramMap.get("year"));}
				if(!StringUtils.isEmpty(paramMap.get("season"))){param.put("SEASON", paramMap.get("season"));}
				if(!StringUtils.isEmpty(paramMap.get("min_stock"))){param.put("MIN_STOCK", paramMap.get("min_stock"));}//1有货
				
				Map<String, Object> retMap= (Map<String, Object>) queryForPost(param,store_service_url+stock_list);
				if(retMap.get("data") instanceof List<?>){
					gr.setState(true);
					gr.setMessage("无数据");
					gr.setTotal(0);
				}else{
					if(StringUtils.isEmpty(retMap.get("data"))){
						gr.setState(false);
						gr.setMessage("获取库存查询列表失败");
						gr.setTotal(0);
						return gr;
					}
					Map<String, Object> map = (Map<String, Object>) retMap.get("data");
					List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("data");
					
					if (list != null && list.size() > 0) {
						gr.setMessage("获取数据成功");
						gr.setObj(list);
					} else {
						gr.setMessage("无数据");
					}
					if(!StringUtils.isEmpty(map.get("total"))){
						gr.setTotal(Integer.valueOf(map.get("total").toString()));
					}else{
						gr.setTotal(0);
					}
					gr.setState(true);
				}
			}else{
				gr.setState(true);
				gr.setMessage("无数据");
				gr.setTotal(0);
			}
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	/**
	 * 新零售门店下拉框
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult storeSelect(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			
			pr.setState(true);
			pr.setMessage("查询成功");
			pr.setObj(stockDao.storeSelect(paramMap));
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 商品库存详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			
			param.put("STORE_ID", paramMap.get("stores"));
			param.put("ITEMNUMBER", paramMap.get("itemnumber"));
			
			List<Integer> stores = (List<Integer>) paramMap.get("stores");
			//调用远程接口
			Map<String, Object> retMap = (Map<String, Object>) queryForPost(param,store_service_url+ly_stock_detail);
			
			List<Map<String, Object>> data = (List<Map<String, Object>>) retMap.get("data");
			if(data != null && data.size()>0){
				for(Map<String, Object> m : data){
					Map<String, Object> stock =(Map<String, Object>) m.get("stock");
					int count = 0;
					for(Integer store_id : stores){
						if(StringUtils.isEmpty(stock.get(store_id.toString()))){
							m.put(store_id.toString(), 0);
						}else{
							m.put(store_id.toString(), stock.get(store_id.toString()));
							count+= Integer.parseInt(stock.get(store_id.toString()).toString());
						}
					}
					m.put("STOCK", count);
				}
			}
			pr.setState(true);
			pr.setMessage("查询成功");
			pr.setObj(data);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 获取调拨任务数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryStoreAllotTaskCount(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(StringUtils.isEmpty(paramMap.get("region_id"))){
				pr.setState(false);
				pr.setMessage("缺少区域ID");
				return pr;
			}
			
			pr.setState(true);
			pr.setMessage("查询成功");
			pr.setObj(stockDao.queryStoreAllotTaskCount(paramMap));
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 调拨任务列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryStoreAllotTaskListForPage(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
			if(pageParamResult!=null){
				return pageParamResult;
			}
			if(StringUtils.isEmpty(paramMap.get("region_id"))){
				gr.setState(false);
				gr.setMessage("缺少区域ID");
				return gr;
			}
			//列表
			List<Map<String, Object>> list = stockDao.queryStoreAllotTaskListForPage(paramMap);
			if(list != null && list.size()>0){
				gr.setState(true);
				gr.setMessage("查询调拨任务列表成功");
				gr.setObj(list);
				gr.setTotal(stockDao.queryStoreAllotTaskCount(paramMap));
			}else{
				gr.setState(true);
				gr.setMessage("无数据");
				gr.setTotal(0);
			}
			
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	/**
	 * 调拨任务明细
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryStoreAllotTaskDetail(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
			if(pageParamResult!=null){
				return pageParamResult;
			}
			
			if(StringUtils.isEmpty(paramMap.get("task_id"))){
				gr.setState(false);
				gr.setMessage("缺少[task_id]");
				return gr;
			}
			
			//调拨任务商品列表
			List<Map<String, Object>> list = stockDao.queryStoreAllotTaskProductListForPage(paramMap);
			
			if(list != null && list.size()>0){
				gr.setState(true);
				gr.setMessage("查询调拨任务明细成功");
				gr.setObj(list);
				gr.setTotal(stockDao.queryStoreAllotTaskProductCount(paramMap));
			}else{
				gr.setState(true);
				gr.setMessage("无数据");
				gr.setTotal(0);
			}
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	/**
	 * 新增调拨任务
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult addAllotTask(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
        	Map<String, Object> paramMap = new HashMap<String, Object>();
    	    String json = HttpUtil.getRequestInputStream(request);
    	    paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
    	    
    	    if(StringUtils.isEmpty(paramMap.get("region_id"))){
				pr.setState(false);
				pr.setMessage("缺少区域ID");
				return pr;
			}
    	    paramMap.put("out_store_id", stockDao.queryStoreId(paramMap));
    	    //查询调拨任务信息
    	    Map<String, Object> info= stockDao.queryStoreAllotTaskInfo(paramMap);
    	    if(info==null){
    	    	//新增调拨单主表信息
                int  num = stockDao.insertStoreAllotTask(paramMap);
                if(num<0){
                	throw new RuntimeException("新增调拨单主表信息失败");
                }
                //新增挑拨单详细信息
                paramMap.put("task_id", paramMap.get("id"));
    	    }else{
    	    	paramMap.put("task_id", info.get("ID"));
    	    }
    	    //查询当前调拨任务单里货号是否已存在
    	    if(stockDao.queryTaskProductIsExists(paramMap) == 0){
                if(stockDao.insertStoreAllotTaskDetail(paramMap)<0){
                	throw new RuntimeException("新增调拨单详细信息失败");
                }
                pr.setState(true);
                pr.setMessage("添加调拨任务成功！");
    	    }else{
    	    	pr.setState(true);
                pr.setMessage("已添加调拨任务！");
    	    }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException("新增调拨任务异常："+e.getMessage());
        }
        return pr;
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

}
