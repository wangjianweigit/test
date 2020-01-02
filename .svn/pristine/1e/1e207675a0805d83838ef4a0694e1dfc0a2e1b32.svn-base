package com.tk.store.stock.service;

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

import com.tk.store.stock.dao.StockAllocateDao;
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
 * FileName : StockAllotService
 * 库存调拨service层
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018-3-2
 */
@Service("StockAllocateService")
public class StockAllocateService {
	@Resource
	private StockAllocateDao stockAllocateDao;
	@Value("${store_service_url}")
	private String store_service_url;// 联营门店服务地址
	@Value("${ly_stock_detail}")
	private String ly_stock_detail;// 联营商品库存明细查询
	@Value("${get_allot_commandOrder}")
	private String get_allot_commandOrder;// 生成调拨单号
	@Value("${allot_commandDetail}")
	private String allot_commandDetail;// 调拨指令单详情
	@Value("${allot_basicsMes}")
	private String allot_basicsMes;// 调拨指令单基本信息
	@Value("${allot_commandAdd}")
	private String allot_commandAdd;// 调拨指令单增加
	@Value("${stock_list}")
	private String stock_list;
	
	/**
	 * 查询库存调拨列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryStockAllocateListForPage(HttpServletRequest request) {
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
			if((!StringUtils.isEmpty(paramMap.get("state")))&&paramMap.get("state") instanceof String){
            	paramMap.put("state",paramMap.get("state").toString().split(","));
			}
			//数量
			int count = stockAllocateDao.queryStockAllocateCount(paramMap);
			//列表
			List<Map<String, Object>> list = stockAllocateDao.queryStockAllocateListForPage(paramMap);
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
	 * 新增调拨指令单
	 * @param request
	 * @return
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public ProcessResult insertStockAllocate(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			
			if(StringUtils.isEmpty(paramMap.get("out_user_id"))){
				pr.setState(false);
				pr.setMessage("缺少参数out_user_id");
				return pr;
			}
			
			if(StringUtils.isEmpty(paramMap.get("in_user_id"))){
				pr.setState(false);
				pr.setMessage("缺少参数in_user_id");
				return pr;
			}
			//新增
			param.put("AGENT_ID", stockAllocateDao.getAgentId(paramMap));
			//生成调拨单号
			Map<String, Object> retMap = (Map<String, Object>) queryForPost(param,store_service_url+get_allot_commandOrder);
			if (Integer.parseInt(retMap.get("state").toString()) != 1) {
				throw new RuntimeException("调用远程接口异常");
			}
			String allocate_number = (String) retMap.get("data");
			paramMap.put("allocate_number", allocate_number);
			stockAllocateDao.insertStockAllocate(paramMap);
			List<Map<String, Object>> data = (List<Map<String, Object>>) paramMap.get("data");
			for(int i=0;i<data.size();i++){
				Map<String, Object> skuMap = (Map<String, Object>) data.get(i);
				List<Map<String, Object>> list = (List<Map<String, Object>>) skuMap.get("list");
				if(list != null && list.size() > 0){
					for(Map<String, Object> m : list){
						//新增调拨单明细
						m.put("allocate_number", allocate_number);
						stockAllocateDao.insertStockAllocateDetail(m);
					}
				}
			}
			if(paramMap.containsKey("task_id")&&!StringUtils.isEmpty(paramMap.get("task_id"))){
				//删除调拨任务
				stockAllocateDao.deleteAllotTask(paramMap);
				//删除调拨任务详情
				stockAllocateDao.deleteAllotTaskDetail(paramMap);
			}
			pr.setObj(allocate_number);
			pr.setState(true);
			pr.setMessage("提交成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException("提交异常："+e.getMessage());
		}
		return pr;
	}
	/**
	 * 调拨单详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryStockAllocateDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(StringUtils.isEmpty(paramMap.get("allocate_number"))){
				pr.setState(false);
				pr.setMessage("缺少参数allocate_number");
				return pr;
			}
			paramMap.put("type", 1);
			//获取调拨单详情
			Map<String, Object> detail = stockAllocateDao.queryStockAllocateDetail(paramMap);
			
			//获取调拨单商品数据
			List<Map<String, Object>> productList = stockAllocateDao.queryStockAllocateProductDetail(paramMap);
			for(Map<String, Object> map :productList){
				Map<String, Object> param = new HashMap<String, Object>();
				List<String> store_id = new ArrayList<String>();
				String out_agent_store_id = detail.get("OUT_AGENT_STORE_ID").toString();
				String in_agent_store_id = detail.get("IN_AGENT_STORE_ID").toString();
				store_id.add(out_agent_store_id);
				store_id.add(in_agent_store_id);
				param.put("STORE_ID", store_id);
				param.put("ITEMNUMBER", map.get("PRODUCT_ITEMNUMBER"));
				
				//调用远程接口
				Map<String, Object> retMap = (Map<String, Object>) queryForPost(param,store_service_url+ly_stock_detail);
				if (Integer.parseInt(retMap.get("state").toString()) != 1) {
					throw new RuntimeException("调用远程接口异常："+retMap.get("message"));
				}
				param.put("allocate_number", paramMap.get("allocate_number"));
				List<Map<String, Object>> skuList = stockAllocateDao.queryStockAllocateDetailSku(param);
				
				List<Map<String, Object>> data = (List<Map<String, Object>>) retMap.get("data");
				if(data != null && data.size()>0){
					for(Map<String, Object> m : data){
						Map<String, Object> stock =(Map<String, Object>) m.get("stock");
						m.put("OUT_STOCK", stock.get(out_agent_store_id));
						m.put("IN_STOCK", stock.get(in_agent_store_id));
						for(Map<String, Object> sku : skuList){
							if(m.get("SKU_ID").equals(sku.get("PRODUCT_SKU").toString())){
								m.put("ALLOCATE_COUNT", sku.get("ALLOCATE_COUNT"));
								break;
							}else{
								m.put("ALLOCATE_COUNT", 0);
							}
						}
						Map<String, Object> p = new HashMap<String, Object>();
						p.put("store_id", detail.get("IN_USER_ID"));
						p.put("product_itemnumber", m.get("ITEM_ID"));
						p.put("type", paramMap.get("type"));
						List<Map<String, Object>> dataList = stockAllocateDao.getStoreOrderSkuCount(p);
						if(dataList != null && dataList.size() > 0){
							for(Map<String, Object> d : dataList){
								if(m.get("SKU_ID").equals(d.get("PRODUCT_SKU").toString())){
									m.put("COUNT", d.get("COUNT"));
									break;
								}else{
									m.put("COUNT", 0);
								}
							}
						}else{
							m.put("COUNT", 0);
						}
					}
					map.put("skuList", data);
				}else{
					map.put("skuList", data);
				}
				Map<String, Object> p = new HashMap<String, Object>();
				p.put("product_itemnumber", map.get("PRODUCT_ITEMNUMBER"));
				p.put("store_id", detail.get("IN_USER_ID"));
				p.put("type", paramMap.get("type"));
				map.put("PRODUCT_COUNT", stockAllocateDao.getStoreOrderProductCount(p));
			}
			detail.put("productList", productList);
			
			pr.setState(true);
			pr.setMessage("获取调拨单详情成功");
			pr.setObj(detail);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 获取调拨单商品sku数据
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryStockAllocateDetailSku(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			
			if(StringUtils.isEmpty(paramMap.get("product_itemnumber"))){
				pr.setState(false);
				pr.setMessage("缺少参数product_itemnumber");
				return pr;
			}
			
			Map<String, Object> detail = stockAllocateDao.storeIdToAgentStoreId(paramMap);
			List<String> store_id = new ArrayList<String>();
			String out_agent_store_id = detail.get("OUT_AGENT_STORE_ID").toString();
			String in_agent_store_id = detail.get("IN_AGENT_STORE_ID").toString();
			store_id.add(out_agent_store_id);
			store_id.add(in_agent_store_id);
			param.put("STORE_ID", store_id);
			param.put("ITEMNUMBER", paramMap.get("product_itemnumber"));
			
			//调用远程接
			Map<String, Object> retMap = (Map<String, Object>) queryForPost(param,store_service_url+ly_stock_detail);
			if (Integer.parseInt(retMap.get("state").toString()) != 1) {
				throw new RuntimeException("调用远程接口异常："+retMap.get("message"));
			}
			List<Map<String, Object>> data = (List<Map<String, Object>>) retMap.get("data");
			if(data != null && data.size()>0){
				for(Map<String, Object> m : data){
					Map<String, Object> stock =(Map<String, Object>) m.get("stock");
					m.put("OUT_STOCK", stock.get(out_agent_store_id));
					m.put("IN_STOCK", stock.get(in_agent_store_id));
					Map<String, Object> p = new HashMap<String, Object>();
					p.put("store_id", paramMap.get("in_user_id"));
					p.put("product_itemnumber", m.get("ITEM_ID"));
					p.put("type", paramMap.get("type"));
					List<Map<String, Object>> dataList = stockAllocateDao.getStoreOrderSkuCount(p);
					if(dataList != null && dataList.size() > 0){
						for(Map<String, Object> d : dataList){
							if(m.get("SKU_ID").equals(d.get("PRODUCT_SKU").toString())){
								m.put("COUNT", d.get("COUNT"));
								break;
							}else{
								m.put("COUNT", 0);
							}
						}
					}else{
						m.put("COUNT", 0);
					}
				}
			}
			pr.setState(true);
			pr.setMessage("获取商品sku库存数据成功");
			pr.setObj(data);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 商品库存
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryProductStock(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("index", paramMap.get("pageIndex"));
			param.put("pageSize", paramMap.get("pageSize"));
			if(StringUtils.isEmpty(paramMap.get("store_str"))){
				paramMap.put("in_user_id", 0);
				Map<String, Object> detail = stockAllocateDao.storeIdToAgentStoreId(paramMap);
				param.put("AGENT_ID", detail.get("OUT_AGENT_ID"));
				param.put("STORE_ID", detail.get("OUT_AGENT_STORE_ID"));
			}else{
				String[] store= (paramMap.get("store_str")+"").split(",");
				List<String> stores = new ArrayList<String>();
				for(int i=0;i<store.length;i++){
					stores.add(store[i]);
				}
				paramMap.put("stores", stores);
				param.put("AGENT_ID", stockAllocateDao.queryAgentId(paramMap));
				param.put("STORE_ID", stores);
			}
			
			if(!StringUtils.isEmpty(paramMap.get("brand"))){param.put("BRAND", paramMap.get("brand"));}
			if(!StringUtils.isEmpty(paramMap.get("itemnumber"))){param.put("ITEMNUMBER", paramMap.get("itemnumber"));}
			if(!StringUtils.isEmpty(paramMap.get("stock"))){param.put("MIN_STOCK", paramMap.get("stock"));}
			if(!StringUtils.isEmpty(paramMap.get("year"))){param.put("YEAR", paramMap.get("year"));}
			if(!StringUtils.isEmpty(paramMap.get("season"))){param.put("SEASON", paramMap.get("season"));}
			if(!StringUtils.isEmpty(paramMap.get("nItemnumbers"))){param.put("FILTER_ITEMS", paramMap.get("nItemnumbers"));}
			
			Map<String, Object> retMap= (Map<String, Object>) queryForPost(param,store_service_url+"product/Product/lyProductList");
			if(retMap.get("data") instanceof List<?> || StringUtils.isEmpty(retMap.get("data"))){
				gr.setState(true);
				gr.setMessage("无数据");
				gr.setTotal(0);
			}else{
				Map<String, Object> map = (Map<String, Object>) retMap.get("data");
				List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("data");
				
				if (list != null && list.size() > 0) {
					for(Map<String,Object> m:list){
						m.put("PRODUCT_TYPE", stockAllocateDao.queryProductTypeByItemnumber(m));
					}
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
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	/**
	 * 查询商品分组信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductGroup(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(StringUtils.isEmpty(paramMap.get("product_itemnumbers"))){
				pr.setState(false);
				pr.setMessage("缺少参数product_itemnumbers");
				return pr;
			}
			if(paramMap.get("product_itemnumbers") instanceof String){
            	paramMap.put("product_itemnumbers",paramMap.get("product_itemnumbers").toString().split(","));
			}
			//获取商品分组信息
			List<Map<String, Object>> list = stockAllocateDao.queryProductGroup(paramMap);
			for(Map<String, Object> m : list){
				Map<String, Object> p = new HashMap<String, Object>();
				p.put("product_itemnumber", m.get("PRODUCT_ITEMNUMBER"));
				p.put("store_id", paramMap.get("in_user_id"));
				p.put("type", paramMap.get("type"));
				m.put("PRODUCT_COUNT", stockAllocateDao.getStoreOrderProductCount(p));
			}
			pr.setState(true);
			pr.setMessage("获取商品分组信息成功");
			pr.setObj(list);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 审批
	 * @param request
	 * @return
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public ProcessResult approvalStockAllocate(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(StringUtils.isEmpty(paramMap.get("allocate_number"))){
				pr.setState(false);
				pr.setMessage("缺少参数allocate_number");
				return pr;
			}
			//审批类型判断
			if("pass".equals(paramMap.get("checkType"))){
				//通过
				Map<String, Object> detail = stockAllocateDao.queryStockAllocateDetail(paramMap);
				
				paramMap.put("approval_state", 3);
				paramMap.put("state", 2);
				if(stockAllocateDao.approvalStockAllocate(paramMap) <= 0){
					throw new RuntimeException("更新审批状态异常");
				}
				
				param.put("COOPER_ID", paramMap.get("public_user_id"));
				param.put("COMMAND_ORDER", detail.get("ALLOCATE_NUMBER"));
				param.put("IN_AGENT_ID", detail.get("IN_AGENT_ID"));
				param.put("OUT_AGENT_ID", detail.get("OUT_AGENT_ID"));
				param.put("IN_STORE_ID", detail.get("IN_AGENT_STORE_ID"));
				param.put("OUT_STORE_ID", detail.get("OUT_AGENT_STORE_ID"));
				param.put("SUM_COUNT", detail.get("ALLOCATE_COUNT"));
				param.put("PROPOSER", detail.get("CREATE_USER_NAME"));
				param.put("PROPOSER_TIME", detail.get("CREATE_DATE"));
				param.put("REMARK", "无");
				List<Map<String, Object>> product = stockAllocateDao.queryProductStockSku(paramMap);
				param.put("product", product);
				//调用远程接口
				Map<String, Object> listMap = (Map<String, Object>) queryForPost(param,store_service_url+allot_commandAdd);
				if (Integer.parseInt(listMap.get("state").toString()) != 1) {
					throw new RuntimeException("调用远程接口异常");
				}
				
				
			}else{
				//驳回
				paramMap.put("approval_state", 4);
				stockAllocateDao.approvalStockAllocate(paramMap);
			}
			
			pr.setState(true);
			pr.setMessage("审批成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException("审批异常："+e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 出入库详情【商品列表】
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryInoutListDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			
			if(StringUtils.isEmpty(paramMap.get("allocate_number"))){
				pr.setState(false);
				pr.setMessage("缺少参数allocate_number");
				return pr;
			}
			param.put("COMMAND_ORDER", paramMap.get("allocate_number"));
			
			//调用远程接口获取调拨单商品列表
			Map<String, Object> listMap = (Map<String, Object>) queryForPost(param,store_service_url+allot_commandDetail);
			if (Integer.parseInt(listMap.get("state").toString()) != 1) {
				throw new RuntimeException("调用远程接口异常");
			}
			
			pr.setState(true);
			pr.setMessage("获取商品sku库存数据成功");
			pr.setObj((List<Map<String, Object>>) listMap.get("data"));
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 出入库详情【基本信息】
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryInoutInfoDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			
			if(StringUtils.isEmpty(paramMap.get("allocate_number"))){
				pr.setState(false);
				pr.setMessage("缺少参数allocate_number");
				return pr;
			}
			
			param.put("COMMAND_ORDER", paramMap.get("allocate_number"));
			
			//调用远程接口获取调拨单基本信息
			Map<String, Object> detailMap = (Map<String, Object>) queryForPost(param,store_service_url+allot_basicsMes);
			if (Integer.parseInt(detailMap.get("state").toString()) != 1) {
				throw new RuntimeException("调用远程接口异常");
			}
			Map<String, Object> data = (Map<String, Object>) detailMap.get("data");
			Map<String, Object> retMap = stockAllocateDao.queryStockAllocateDetail(paramMap);
			retMap.put("ALLOCATE_COUNT", data.get("SUM_COUNT"));//调拨总数
			retMap.put("OUT_COUNT", data.get("SUM_OUT_COUNT"));//出库总数
			retMap.put("IN_COUNT", data.get("SUM_IN_COUNT"));//入库总数
			retMap.put("OUT_NAME", data.get("OUT_NAME"));//出库人
			retMap.put("OUT_DATE", data.get("OUT_DATE"));//出库时间
			retMap.put("IN_NAME", data.get("IN_NAME"));//入库人
			retMap.put("IN_DATE", data.get("IN_DATE"));//入库时间
			retMap.put("DIFF_COUNT", data.get("SUM_DIFF_COUNT"));//差异总数
			retMap.put("STATE", data.get("STATE"));//状态（1.待入库 2.待出库 3.已完成）
			pr.setState(true);
			pr.setMessage("获取商品sku库存数据成功");
			pr.setObj(retMap);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 获取差异列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryDiffList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			
			if(StringUtils.isEmpty(paramMap.get("allocate_number"))){
				pr.setState(false);
				pr.setMessage("缺少参数allocate_number");
				return pr;
			}
			//差异数据
			List<Map<String, Object>> list= stockAllocateDao.queryDiffList(paramMap);
			
			pr.setState(true);
			pr.setMessage("获取差异列表成功");
			pr.setObj(list);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 核销
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult cancelVer(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			
			if(StringUtils.isEmpty(paramMap.get("allocate_number"))){
				pr.setState(false);
				pr.setMessage("缺少参数allocate_number");
				return pr;
			}
			
			if(StringUtils.isEmpty(paramMap.get("diff_attr_type"))){
				pr.setState(false);
				pr.setMessage("缺少参数diff_attr_type");
				return pr;
			}
			if(stockAllocateDao.updateStockAllocate(paramMap) > 0){
				//核销后，更新调拨单清分交易的付款状态为已支付
				stockAllocateDao.updateStoreTradePaymentState(paramMap);
				pr.setState(true);
				pr.setMessage("核销成功");
			}else{
				pr.setState(false);
				pr.setMessage("核销失败");
			}
			
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 获取调拨任务单详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAllotTaskDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(StringUtils.isEmpty(paramMap.get("task_id"))){
				pr.setState(false);
				pr.setMessage("缺少参数task_id");
				return pr;
			}
			//获取调拨信息【调拨任务】
			Map<String, Object> retMap = stockAllocateDao.queryAllotTaskDetail(paramMap);
			
			//获取商品分组信息【调拨任务】
			List<Map<String, Object>> list = stockAllocateDao.queryProductGroupByTask(paramMap);
			retMap.put("productList", list);
			pr.setState(true);
			pr.setMessage("获取调拨任务单详情成功");
			pr.setObj(retMap);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 获取商品库存查询
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryStockProductList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			List<String> product_itemnumbers = (List<String>) paramMap.get("product_itemnumbers");
			if(product_itemnumbers.size() == 0){
				gr.setState(true);
				gr.setMessage("无数据");
				return gr;
			}
			List<String> stores = (List<String>) paramMap.get("stores");
			if(stores.size() > 0){
				param.put("index", paramMap.get("pageIndex"));
				param.put("pageSize", paramMap.get("pageSize"));
				param.put("AGENT_ID", stockAllocateDao.queryAgentId(paramMap));
				param.put("STORE_ID", paramMap.get("stores"));
				param.put("ITEM_ID", paramMap.get("product_itemnumbers"));
				
				Map<String, Object> retMap= (Map<String, Object>) queryForPost(param,store_service_url+stock_list);
				if(retMap.get("data") instanceof List<?>){
					gr.setState(true);
					gr.setMessage("无数据");
				}else{
					if(StringUtils.isEmpty(retMap.get("data"))){
						gr.setState(false);
						gr.setMessage("获取商品数据失败");
						return gr;
					}
					Map<String, Object> map = (Map<String, Object>) retMap.get("data");
					List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("data");
					
					if (list != null && list.size() > 0) {
						for(Map<String, Object> rm : list){
							Map<String, Object> p = new HashMap<String, Object>();
							p.put("product_itemnumber", rm.get("ITEM_ID"));
							rm.putAll(stockAllocateDao.getProductInfo(p));
							if(!StringUtils.isEmpty(paramMap.get("user_id"))){
								p.put("store_id", stockAllocateDao.getStoreId(paramMap));
								p.put("type", paramMap.get("type"));
								rm.put("PRODUCT_COUNT", stockAllocateDao.getStoreOrderProductCount(p));
							}
						}
						gr.setMessage("获取数据成功");
						gr.setObj(list);
					} else {
						gr.setMessage("无数据");
					}
					gr.setState(true);
				}
			}else{
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
	 * 获取sku库存列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryStockSkuList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			param.put("STORE_ID", paramMap.get("stores"));
			param.put("ITEMNUMBER", paramMap.get("product_itemnumber"));
			
			//调用远程接口
			Map<String, Object> retMap = (Map<String, Object>) queryForPost(param,store_service_url+ly_stock_detail);
			List<String> stores = (List<String>) paramMap.get("stores");
			List<Map<String, Object>> data = (List<Map<String, Object>>) retMap.get("data");
			if(data != null && data.size()>0){
				for(Map<String, Object> m : data){
					Map<String, Object> stock =(Map<String, Object>) m.get("stock");
					for(String store_id : stores){
						if(StringUtils.isEmpty(stock.get(store_id))){
							m.put(store_id, 0);
						}else{
							m.put(store_id, stock.get(store_id));
						}
					}
					if(!StringUtils.isEmpty(paramMap.get("user_id"))){
						Map<String, Object> p = new HashMap<String, Object>();
						p.put("store_id", stockAllocateDao.getStoreId(paramMap));
						p.put("product_itemnumber", m.get("ITEM_ID"));
						p.put("type", paramMap.get("type"));
						List<Map<String, Object>> dataList = stockAllocateDao.getStoreOrderSkuCount(p);
						if(dataList != null && dataList.size() > 0){
							for(Map<String, Object> d : dataList){
								if(m.get("SKU_ID").equals(d.get("PRODUCT_SKU").toString())){
									m.put("COUNT", d.get("COUNT"));
									break;
								}else{
									m.put("COUNT", 0);
								}
							}
						}else{
							m.put("COUNT", 0);
						}
					}
					if(!StringUtils.isEmpty(paramMap.get("user_ids"))){
						String[] user_ids= (paramMap.get("user_ids")+"").split(",");
						for(int i=0;i<user_ids.length;i++){
							Map<String, Object> p = new HashMap<String, Object>();
							p.put("user_id", user_ids[i]);
							p.put("store_id", stockAllocateDao.getStoreId(p));
							p.put("product_itemnumber", m.get("ITEM_ID"));
							p.put("type", paramMap.get("type").toString().equals("1")?2:1);
							List<Map<String, Object>> dataList = stockAllocateDao.getStoreOrderSkuCount(p);
							if(dataList != null && dataList.size() > 0){
								for(Map<String, Object> d : dataList){
									if(m.get("SKU_ID").equals(d.get("PRODUCT_SKU").toString())){
										m.put("c"+user_ids[i], d.get("COUNT"));
										break;
									}else{
										m.put("c"+user_ids[i], 0);
									}
								}
							}else{
								m.put("c"+user_ids[i], 0);
							}
						}
					}
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
			pr.setObj(stockAllocateDao.storeSelect(paramMap));
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 门店分组下拉框
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult storeGroupSelect(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			
			pr.setState(true);
			pr.setMessage("查询成功");
			pr.setObj(stockAllocateDao.storeGroupSelect(paramMap));
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 门店分组详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryStoreGroupDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(StringUtils.isEmpty(paramMap.get("id"))){
				pr.setState(false);
				pr.setMessage("缺少分组id");
				return pr;
			}
			
			pr.setState(true);
			pr.setMessage("查询成功");
			pr.setObj(stockAllocateDao.queryStoreGroupDetail(paramMap));
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 新增门店分组
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult addStoreGroup(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			//校验 分组名称是否存在
			if(stockAllocateDao.queryGroupNameIsExists(paramMap) > 0){
				pr.setState(false);
				pr.setMessage("分组名称已经存在");
				return pr;
			}
			stockAllocateDao.insertStoreGroup(paramMap);
			pr.setState(true);
			pr.setMessage("新增成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 删除门店分组
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult delStoreGroup(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(StringUtils.isEmpty(paramMap.get("id"))){
				pr.setState(false);
				pr.setMessage("缺少分组id");
				return pr;
			}
			stockAllocateDao.deleteStoreGroup(paramMap);
			pr.setState(true);
			pr.setMessage("删除成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 新增调拨单(调出)
	 * @param request
	 * @return
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public ProcessResult insertStockAllocateOut(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			
			if(StringUtils.isEmpty(paramMap.get("agent_store_id"))){
				pr.setState(false);
				pr.setMessage("缺少调出门店id");
				return pr;
			}
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) paramMap.get("list");
			if(list != null && list.size() > 0){
				for(Map<String, Object> map : list){
					Map<String, Object> param = new HashMap<String, Object>();
					//新增
					param.put("AGENT_ID", stockAllocateDao.getAgentId(paramMap));
					//生成调拨单号
					Map<String, Object> retMap = (Map<String, Object>) queryForPost(param,store_service_url+get_allot_commandOrder);
					if (Integer.parseInt(retMap.get("state").toString()) != 1) {
						throw new RuntimeException("调用远程接口异常："+retMap.get("message"));
					}
					String allocate_number = (String) retMap.get("data");
					map.put("allocate_number", allocate_number);
					map.put("out_agent_store_id", paramMap.get("agent_store_id"));
					map.put("public_user_id", paramMap.get("public_user_id"));
					stockAllocateDao.insertStockAllocateOutIn(map);
					List<Map<String, Object>> produtList = (List<Map<String, Object>>) map.get("productList");
					for(Map<String, Object> m : produtList){
						//新增调拨单明细
						m.put("allocate_number", allocate_number);
						stockAllocateDao.insertStockAllocateDetail(m);
					}
				}
			}
			
			if(paramMap.containsKey("task_id")&&!StringUtils.isEmpty(paramMap.get("task_id"))){
				//删除调拨任务
				stockAllocateDao.deleteAllotTask(paramMap);
				//删除调拨任务详情
				stockAllocateDao.deleteAllotTaskDetail(paramMap);
			}
			pr.setState(true);
			pr.setMessage("提交成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException("提交异常："+e.getMessage());
		}
		return pr;
	}
	/**
	 * 新增调拨单(调入)
	 * @param request
	 * @return
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public ProcessResult insertStockAllocateIn(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			
			if(StringUtils.isEmpty(paramMap.get("agent_store_id"))){
				pr.setState(false);
				pr.setMessage("缺少调入门店id");
				return pr;
			}
			
			List<Map<String, Object>> list = (List<Map<String, Object>>) paramMap.get("list");
			if(list != null && list.size() > 0){
				for(Map<String, Object> map : list){
					Map<String, Object> param = new HashMap<String, Object>();
					//新增
					param.put("AGENT_ID", stockAllocateDao.getAgentId(paramMap));
					//生成调拨单号
					Map<String, Object> retMap = (Map<String, Object>) queryForPost(param,store_service_url+get_allot_commandOrder);
					if (Integer.parseInt(retMap.get("state").toString()) != 1) {
						throw new RuntimeException("调用远程接口异常："+retMap.get("message"));
					}
					String allocate_number = (String) retMap.get("data");
					map.put("allocate_number", allocate_number);
					map.put("in_agent_store_id", paramMap.get("agent_store_id"));
					map.put("public_user_id", paramMap.get("public_user_id"));
					stockAllocateDao.insertStockAllocateOutIn(map);
					List<Map<String, Object>> produtList = (List<Map<String, Object>>) map.get("productList");
					for(Map<String, Object> m : produtList){
						//新增调拨单明细
						m.put("allocate_number", allocate_number);
						stockAllocateDao.insertStockAllocateDetail(m);
					}
				}
			}
			
			pr.setState(true);
			pr.setMessage("提交成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException("提交异常："+e.getMessage());
		}
		return pr;
	}
	/**
	 * 获取调拨任务单(调出)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAllotOutTask(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(StringUtils.isEmpty(paramMap.get("task_id"))){
				pr.setState(false);
				pr.setMessage("缺少参数task_id");
				return pr;
			}
			//获取调拨信息(调出)
			Map<String, Object> retMap = stockAllocateDao.queryAllotOutTask(paramMap);
			//获取调拨任务商品(调出)
			List<String> list = stockAllocateDao.queryProductOutTask(paramMap);
			retMap.put("productList", list);
			pr.setState(true);
			pr.setMessage("获取调拨任务单详情成功");
			pr.setObj(retMap);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 查看要货单/退货单
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult storeOrderQuery(HttpServletRequest request) {
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
			if(StringUtils.isEmpty(paramMap.get("user_id"))){
				gr.setState(false);
				gr.setMessage("缺少参数门店ID");
				return gr;
			}
			if(paramMap.get("query_type").equals("agent")){
				paramMap.put("user_id", stockAllocateDao.getStoreId(paramMap));
			}
			//数量
			int count = stockAllocateDao.storeOrderQueryCount(paramMap);
			//列表
			List<Map<String, Object>> list = stockAllocateDao.storeOrderQueryListForPage(paramMap);
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
	 * 关闭调拨单
	 * @param request
	 * @return
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public ProcessResult closeStockAllocate(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			
			if(StringUtils.isEmpty(paramMap.get("allocate_number"))){
				pr.setState(false);
				pr.setMessage("缺少参数allocate_number");
				return pr;
			}
			
			if(StringUtils.isEmpty(paramMap.get("allocate_number"))){
				pr.setState(false);
				pr.setMessage("缺少参数allocate_number");
				return pr;
			}
			param.put("COMMAND_ORDER", paramMap.get("allocate_number"));
			param.put("STATE", 4);//调拨状态: 4，关闭(PHP)
			//更新调拨指令单状态
			stockAllocateDao.updateStockAllocate(paramMap);
			//调用远程接口 调拨指令单编辑
			Map<String, Object> retMap = (Map<String, Object>) queryForPost(param,store_service_url+"allot/Allot/allotCommandEdit");
			if (Integer.parseInt(retMap.get("state").toString()) != 1) {
				throw new RuntimeException("调用远程接口异常："+retMap.get("message").toString());
			}
			
			pr.setState(true);
			pr.setMessage("关闭调拨指令单成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
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
