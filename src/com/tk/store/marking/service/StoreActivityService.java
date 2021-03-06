package com.tk.store.marking.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.store.marking.dao.StoreActivityDao;
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
 * Copyright (c), 2018, Tongku
 * FileName : StoreActivityService.java
 * 营销活动 service层
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018年5月30日
 */
@Service("StoreActivityService")
public class StoreActivityService {
	@Resource
	private StoreActivityDao storeActivityDao;
	
	@Value("${store_service_url}")
	private String store_service_url;// 联营门店服务地址
	@Value("${ly_activity_insert}")
	private String ly_activity_insert;// 联营活动新增
	@Value("${ly_activity_edit}")
	private String ly_activity_edit;// 联营活动编辑
	
	/**
	 * 活动列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryActivityListForPage(HttpServletRequest request) {
		GridResult gr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			GridResult pageParamResult = PageUtil.handlePageParams(params);
			if(pageParamResult!=null){
				return pageParamResult;
			}
			
			if((!StringUtils.isEmpty(params.get("activity_state")))&&params.get("activity_state") instanceof String){
				params.put("activity_state",(params.get("activity_state")+"").split(","));
			}
			if((!StringUtils.isEmpty(params.get("start_state")))&&params.get("start_state") instanceof String){
				params.put("start_state",(params.get("start_state")+"").split(","));
			}
			if((!StringUtils.isEmpty(params.get("state")))&&params.get("state") instanceof String){
				params.put("state",(params.get("state")+"").split(","));
			}
			
			int count=storeActivityDao.queryActivityListCount(params);
			List<Map<String,Object>> list = storeActivityDao.queryActivityListForPage(params);
			if (list != null && list.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(list);
				gr.setTotal(count);
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
	 * 是否启用
	 * @param request
	 * @return
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public ProcessResult updateActivityState(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(StringUtils.isEmpty(paramMap.get("activity_id"))){
				pr.setState(false);
				pr.setMessage("缺少参数[activity_id]");
				return pr;
			}
			
			storeActivityDao.updateActivityState(paramMap);
			Map<String, Object> param = storeActivityDao.queryStoreActivityInfo(paramMap);
			if("2".equals(param.get("STATE").toString())){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("DISCOUNT_ID", storeActivityDao.qeuryDiscountId(paramMap));
				map.put("AGENT_ID", storeActivityDao.queryPartnerUserList(paramMap));//获取商家
				String url="";
				if(Integer.parseInt(param.get("TYPE")+"")!=4){
					url="marketing/Discount/discountEdit";
					if("1".equals(param.get("START_STATE").toString())){
						map.put("STATUS", 1);
					}else{
						map.put("STATUS", 0);
					}
				}else{
					url="marketing/Recharge/rechargeActivityBatchEdit";
					if("1".equals(param.get("START_STATE").toString())){
						map.put("IS_OPEN", 1);
					}else{
						map.put("IS_OPEN", 0);
					}
				}
				Map<String, Object> retMap= (Map<String, Object>) queryForPost(map,store_service_url+url);
				if (Integer.parseInt(retMap.get("state").toString()) != 1) {
					throw new RuntimeException("调用远程接口异常:"+retMap.get("message"));
				}
			}
			
			pr.setState(true);
			pr.setMessage("更新成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException("更新状态异常："+e.getMessage());
		}
		return pr;
	}
	/**
	 * 删除活动
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult deleteActivityInfo(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(StringUtils.isEmpty(paramMap.get("activity_id"))){
				pr.setState(false);
				pr.setMessage("缺少参数[activity_id]");
				return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("type"))){
				pr.setState(false);
				pr.setMessage("缺少参数[type]");
				return pr;
			}
			//删除活动
			storeActivityDao.deleteActivityInfo(paramMap);
			//删除活动门店
			storeActivityDao.deleteStoreActivity(paramMap);
			//删除活动商品
			storeActivityDao.deleteStoreActivityProduct(paramMap);
			
			pr.setState(true);
			pr.setMessage("删除成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException("删除异常："+e.getMessage());
		}
		return pr;
	}
	/**
	 * 获取商品库列表
	 * @param request
	 * @return
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public GridResult queryProductLibraryListForPage(HttpServletRequest request) {
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
			if((!StringUtils.isEmpty(paramMap.get("store_list")))&&paramMap.get("store_list") instanceof String){
				paramMap.put("store_list",(paramMap.get("store_list")+"").split(","));
			}
			if((!StringUtils.isEmpty(paramMap.get("product_itemnumbers")))&&paramMap.get("product_itemnumbers") instanceof String){
				paramMap.put("product_itemnumbers",(paramMap.get("product_itemnumbers")+"").split(","));
			}
			//将需要过滤的商品货号插入到临时表
			storeActivityDao.insertTempProductItemnumber(paramMap);
			//数量
			int count = storeActivityDao.queryProductLibraryCount(paramMap);
			//列表
			List<Map<String, Object>> list = storeActivityDao.queryProductLibraryListForPage(paramMap);
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
	 * 获取门店库列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryZyStoreLibraryListForPage(HttpServletRequest request) {
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
			Map<String, Object> sendMap = new HashMap<String, Object>();
			Map<String, Object> specQuery = new HashMap<String, Object>();
			sendMap.put("pageSize",paramMap.get("pageSize"));
            sendMap.put("index",paramMap.get("pageIndex"));
            sendMap.put("SOURCE_TYPE",2);
            sendMap.put("STOCK_SUPPORT", 0);
            sendMap.put("TYPE_SUPPORT", 0);
            //查询经销商ID
            if(paramMap.containsKey("user_id") && !StringUtils.isEmpty(paramMap.get("user_id"))){
                sendMap.put("AGENT_ID",storeActivityDao.queryUserManageNameByAgentId(paramMap).get("AGENT_ID"));
                paramMap.put("agent_id_u", paramMap.get("user_id"));
            }else{
            	List<String> store_list=Arrays.asList(paramMap.get("store_list").toString().split(","));
            	paramMap.put("store_list", store_list);
                sendMap.put("AGENT_ID",storeActivityDao.queryAgentId(paramMap));
                paramMap.put("agent_id_s",store_list);
            }
            //查询已经在其它活动中进行的自营商品
            Map<String,List<String>> tempAgentProductMap = new HashMap<String,List<String>>();
            List<String> tempAgentProductList = null;
            List<Map<String, Object>> activing_product_list=storeActivityDao.queryActivingZyProductList(paramMap);
            for(Map<String, Object> activing_product:activing_product_list){
            	String agent_id = activing_product.get("AGENT_ID")+"";
            	if(tempAgentProductMap.containsKey(agent_id)){
            			tempAgentProductList = tempAgentProductMap.get(agent_id);
            			tempAgentProductList.add(activing_product.get("PRODUCT_ITEMNUMBER")+"");
	                }else{
	                	tempAgentProductList = new ArrayList<String>();
	                	tempAgentProductList.add(activing_product.get("PRODUCT_ITEMNUMBER")+"");
	                	tempAgentProductMap.put(agent_id,tempAgentProductList);
	                }
            }
            Map<String, List<String>> itemnumberMap=(Map<String, List<String>>)paramMap.get("product_itemnumbers");
            if(itemnumberMap !=null && itemnumberMap.size()>0){
            	for (Entry<String,  List<String>> entry : itemnumberMap.entrySet()) {
            		String agent_id = entry.getKey();
            		if(tempAgentProductMap.containsKey(agent_id)){
            			tempAgentProductMap.get(agent_id).addAll(entry.getValue());
            		}else{
            			tempAgentProductMap.put(agent_id, entry.getValue());
            		}
            	}
            }
            if(tempAgentProductMap!=null && tempAgentProductMap.size()>0){
        		specQuery.put("TYPE",0 );
            	specQuery.put("DATA", tempAgentProductMap);
            	sendMap.put("SPEC_QUERY", specQuery);
        	}
            if(paramMap.containsKey("product_type_id") && !StringUtils.isEmpty(paramMap.get("product_type_id"))){
                sendMap.put("SECOND_TYPE",paramMap.get("product_type_id"));
            }
            if(paramMap.containsKey("season_name") && !StringUtils.isEmpty(paramMap.get("season_name"))){
                sendMap.put("SEASON",paramMap.get("season_name"));
            }
            if(paramMap.containsKey("itemnumber") && !StringUtils.isEmpty(paramMap.get("itemnumber"))){
                sendMap.put("ITEMNUMBER",paramMap.get("itemnumber"));
            }
            if(paramMap.containsKey("year") && !StringUtils.isEmpty(paramMap.get("year"))){
                sendMap.put("YEAR",paramMap.get("year"));
            }
            if(paramMap.containsKey("brand") && !StringUtils.isEmpty(paramMap.get("brand"))){
                sendMap.put("BRAND",paramMap.get("brand"));
            }
			Map<String, Object> retMap= (Map<String, Object>) queryForPost(sendMap,store_service_url+"/product/Product/productList");
			if (Integer.parseInt(retMap.get("state").toString()) != 1) {
				throw new RuntimeException("调用远程接口异常:"+retMap.get("message"));
			}
			Map<String, Object> data=(Map<String, Object>)retMap.get("data");
            List<Map<String, Object>> dataList=(List<Map<String, Object>>)data.get("data");
            for(Map<String, Object> map:dataList){
            	//查询商家名称
            	map.put("USER_MANAGE_NAME", storeActivityDao.queryUserManageNameByAgentId(map).get("USER_MANAGE_NAME"));
            	//查询商品分类
            	map.put("PRODUCT_TYPE", storeActivityDao.queryProductTypeByTypeId(map).get("TYPE_NAME"));
            	
            }
			gr.setState(true);
			gr.setObj(dataList);
			gr.setTotal(Integer.parseInt(data.get("total")+""));
			gr.setMessage("查询成功");
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	
	/**
	 * 获取门店库列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryStoreLibraryListForPage(HttpServletRequest request) {
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
			
			//数量
			int count = storeActivityDao.queryStoreLibraryCount(paramMap);
			//列表
			List<Map<String, Object>> list = storeActivityDao.queryStoreLibraryListForPage(paramMap);
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
	 * 新增活动
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult addActivity(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(StringUtils.isEmpty(paramMap.get("type"))){
				pr.setState(false);
				pr.setMessage("缺少参数[type]");
				return pr;
			}
			
			if(StringUtils.isEmpty(paramMap.get("store_id"))){
				pr.setState(false);
				pr.setMessage("缺少参数[store_id]");
				return pr;
			}
			
			List<Integer> store_ids = (List<Integer>) paramMap.get("store_id");
			
			//新增活动信息
			storeActivityDao.insertStoreActivityInfo(paramMap);
			//如果为满减满折类型需要保存满减满折相关数据
			if(Integer.parseInt(paramMap.get("type").toString())==2){
				if(Integer.parseInt(paramMap.get("activity_type").toString())==1){
					//满减类型
					List<Map<String,Object>> minusList=(List<Map<String,Object>>)paramMap.get("minus_list");
					for(Map<String,Object> minus:minusList){
						minus.put("type", paramMap.get("activity_type"));
						minus.put("activity_id", paramMap.get("id"));
					}
					int count=storeActivityDao.insertStoreActivityPreferential(minusList);
					if(count<=0){
						pr.setState(false);
						throw new RuntimeException("新增满减满折数据失败");
					}
				}else if(Integer.parseInt(paramMap.get("activity_type").toString())==2){
					//满折类型
					List<Map<String,Object>> saleList=(List<Map<String,Object>>)paramMap.get("sale_list");
					for(Map<String,Object> sale:saleList){
						sale.put("type", paramMap.get("activity_type"));
						sale.put("activity_id", paramMap.get("id"));
						float discount=Float.parseFloat(sale.get("discount").toString());
						discount=(float)discount/10;
						sale.put("discount", discount);
					}
					int count=storeActivityDao.insertStoreActivityPreferential(saleList);
					if(count<=0){
						pr.setState(false);
						throw new RuntimeException("新增满减满折数据失败");
					}
				}
			}else if(Integer.parseInt(paramMap.get("type").toString())==4){
				//充值满送
				List<Map<String,Object>> ruleList=(List<Map<String,Object>>)paramMap.get("rule_list");
				for(Map<String,Object> rule:ruleList){
					rule.put("type", 4);
					rule.put("activity_id", paramMap.get("id"));
				}
				int count=storeActivityDao.insertStoreActivityPreferential(ruleList);
				if(count<=0){
					pr.setState(false);
					throw new RuntimeException("新增充值满送失败");
				}
			}
			for(Integer store_id : store_ids){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("id", paramMap.get("id"));
				param.put("store_id", store_id);
				param.put("public_user_id", paramMap.get("public_user_id"));
				//新增活动关联门店
				storeActivityDao.insertStoreActivity(param);
			}
			
			if(Integer.parseInt(paramMap.get("type").toString())!=4){
				List<String> itemnumbers = (List<String>) paramMap.get("product_itemnumber");
				if(itemnumbers!=null && itemnumbers.size()>0){
					for(String itemnumber : itemnumbers){
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("id", paramMap.get("id"));
						param.put("product_itemnumber", itemnumber);
						param.put("public_user_id", paramMap.get("public_user_id"));
						param.put("type",1);
						//新增活动关联联营商品
						storeActivityDao.insertStoreActivityProduct(param);
					}
				}
				List<Map<String, Object>> zy_itemnumbers = (List<Map<String, Object>>) paramMap.get("zy_product_itemnumber");
				if(zy_itemnumbers!=null && zy_itemnumbers.size()>0){
					for(Map<String, Object> zy_itemnumber : zy_itemnumbers){
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("id", paramMap.get("id"));
						param.put("product_itemnumber", zy_itemnumber.get("ITEMNUMBER"));
						param.put("user_id", storeActivityDao.queryUserManageNameByAgentId(zy_itemnumber).get("ID"));
						param.put("public_user_id", paramMap.get("public_user_id"));
						param.put("type",2);
						//新增活动关联自营商品
						storeActivityDao.insertStoreActivityProduct(param);
					}
				}
			}
			pr.setState(true);
			pr.setMessage("新增成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException("新增异常："+e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 活动编辑详情
	 * @param request
	 * @return
	 */
	public ProcessResult queryStoreActivityEditDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String,Object> resMap = new HashMap<String,Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(StringUtils.isEmpty(params.get("activity_id"))){
				pr.setState(false);
				pr.setMessage("缺少参数0");
                return pr;
            }
			//查询活动主表信息
			Map<String,Object> activityInfo = storeActivityDao.queryStoreActivityInfo(params);
			List<Map<String, Object>> zyDataList=null;
			List<Map<String,Object>> activityProductList=null;
			if(activityInfo!=null){
				if(Integer.parseInt(activityInfo.get("TYPE")+"")!=4){
					//查询联营活动商品列表
					params.put("type", 1);
					activityProductList=storeActivityDao.queryActivityProductList(params);
					//封装经销商ID
		            List<String> agentIds=storeActivityDao.queryActivityAgentID(params);
		            Map<String, Object> sendMap = new HashMap<String, Object>();
		            if(agentIds.size()>0){
		            	sendMap.put("AGENT_ID", agentIds);
						Map<String, Object> specQuery = new HashMap<String, Object>();
						sendMap.put("SOURCE_TYPE",2);
			            sendMap.put("STOCK_SUPPORT", 0);
			            sendMap.put("TYPE_SUPPORT", 0);
			            //封装每个经销商里的货号
			            Map<String, Object> agentProduct = new HashMap<String, Object>();
			            Map<String, Object> param = new HashMap<String, Object>();
			            for(String agent_id:agentIds){
			            	param.put("AGENT_ID", agent_id);
			            	Map<String, Object> userInfo =storeActivityDao.queryUserManageNameByAgentId(param);
			            	params.put("user_id", userInfo.get("ID"));
			            	agentProduct.put(agent_id,storeActivityDao.queryActivityItemnumber(params));
			            }
			            specQuery.put("DATA", agentProduct);
			            specQuery.put("TYPE", 1);
			            sendMap.put("SPEC_QUERY", specQuery);
			            
			            Map<String, Object> retMap= (Map<String, Object>) queryForPost(sendMap,store_service_url+"/product/Product/productList");
						if (Integer.parseInt(retMap.get("state").toString()) != 1) {
							throw new RuntimeException("调用远程接口异常:"+retMap.get("message"));
						}
						Map<String, Object> data=(Map<String, Object>)retMap.get("data");
						zyDataList=(List<Map<String, Object>>)data.get("data");
			            for(Map<String, Object> map:zyDataList){
			            	//查询商家名称
			            	map.put("USER_MANAGE_NAME", storeActivityDao.queryUserManageNameByAgentId(map).get("USER_MANAGE_NAME"));
			            	//查询商品分类
			            	map.put("PRODUCT_TYPE", storeActivityDao.queryProductTypeByTypeId(map).get("TYPE_NAME"));
			            	
			            }
		            }
					
				}
				//查询活动店铺列表
				List<Map<String,Object>> activityStoreList=storeActivityDao.queryActivityStoreList(params);
				//查询活动满减满折、充值满送信息
				List<Map<String,Object>> activityPreferentList=storeActivityDao.queryActivityPreferentList(params);
				if(activityPreferentList!=null && activityPreferentList.size()>0){
					resMap.put("activityPreferent", activityPreferentList);
				}
				resMap.put("activityInfo", activityInfo);
				resMap.put("activityProductList", activityProductList);
				resMap.put("activityZyProductList", zyDataList);
				resMap.put("activityStoreList", activityStoreList);
				pr.setMessage("查询数据成功");
				pr.setObj(resMap);
			}else{
				pr.setMessage("无数据");
			}
			pr.setState(true);
			
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 修改活动时间
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult updateActivityDate(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> sendMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(StringUtils.isEmpty(paramMap.get("activity_id"))){
				pr.setState(false);
				pr.setMessage("缺少参数[activity_id]");
				return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("begin_date"))){
				pr.setState(false);
				pr.setMessage("缺少参数[begin_date]");
				return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("end_date"))){
				pr.setState(false);
				pr.setMessage("缺少参数[end_date]");
				return pr;
			}
			//查询活动主表信息
			Map<String,Object> activityInfo = storeActivityDao.queryStoreActivityInfo(paramMap);
			if(activityInfo==null){
				pr.setState(false);
				pr.setMessage("未找到对应活动信息");
				return pr;
			}
			String url="";
			if(Integer.parseInt(activityInfo.get("TYPE")+"")!=4){
				url="marketing/Discount/discountEdit";
				sendMap.put("TIME_START", paramMap.get("begin_date"));
				sendMap.put("TIME_END", paramMap.get("end_date"));
			}else{
				//查询当前活动时间内是否存在已在活动中的门店
				int num=storeActivityDao.queryIsExistsActivingStore(paramMap);
				if(num>0){
					pr.setState(false);
					pr.setMessage("当前活动门店在当前时间段内已在其它活动中进行");
	                return pr;
				}
				url="marketing/Recharge/rechargeActivityBatchEdit";
				sendMap.put("START_TIME", paramMap.get("begin_date"));
				sendMap.put("END_TIME", paramMap.get("end_date"));
			}
			//修改活动时间
			int count =storeActivityDao.updateActivityDate(paramMap);
			if(count<=0){
				pr.setState(false);
				throw new RuntimeException("修改活动时间失败");
			}
			List<String> user_ids=storeActivityDao.queryPartnerUserList(paramMap);
			sendMap.put("AGENT_ID", user_ids);
			sendMap.put("DISCOUNT_ID", activityInfo.get("AGENT_ACTIVITY_ID"));
			Map<String, Object> retMap= (Map<String, Object>) queryForPost(sendMap,store_service_url+url);
			if (Integer.parseInt(retMap.get("state").toString()) != 1) {
				throw new RuntimeException("调用远程接口异常:"+retMap.get("message"));
			}
			pr.setState(true);
			pr.setMessage("修改活动时间成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException("修改活动时间异常："+e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 查询活动商品列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryActivityProductList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			GridResult pageParamResult = PageUtil.handlePageParams(params);
			if(pageParamResult!=null){
				return pageParamResult;
			}
			if(StringUtils.isEmpty(params.get("activity_id"))){
				gr.setState(false);
				gr.setMessage("缺少参数[activity_id]");
				return gr;
			}
			if((!StringUtils.isEmpty(params.get("state")))&&params.get("state") instanceof String){
				params.put("state",(params.get("state")+"").split(","));
			}
			int count=storeActivityDao.queryActivityProductCount(params);
			List<Map<String,Object>> list = storeActivityDao.queryActivityProductList(params);
			List<Map<String,Object>> zyDataList=null;
			//查询自营商品列表
			//封装经销商ID
            List<String> agentIds=storeActivityDao.queryActivityAgentID(params);
            Map<String, Object> sendMap = new HashMap<String, Object>();
            if(agentIds.size()>0){
            	sendMap.put("AGENT_ID", agentIds);
				Map<String, Object> specQuery = new HashMap<String, Object>();
				sendMap.put("SOURCE_TYPE",2);
	            sendMap.put("STOCK_SUPPORT", 0);
	            sendMap.put("TYPE_SUPPORT", 0);
	            if(params.containsKey("product_type_id") && !StringUtils.isEmpty(params.get("product_type_id"))){
	                sendMap.put("BASIC_TYPE",params.get("product_type_id"));
	            }
	            if(params.containsKey("itemnumber") && !StringUtils.isEmpty(params.get("itemnumber"))){
	                sendMap.put("ITEMNUMBER",params.get("itemnumber"));
	            }
	            //封装每个经销商里的货号
	            Map<String, Object> agentProduct = new HashMap<String, Object>();
	            Map<String, Object> param = new HashMap<String, Object>();
	            for(String agent_id:agentIds){
	            	param.put("AGENT_ID", agent_id);
	            	Map<String, Object> userInfo =storeActivityDao.queryUserManageNameByAgentId(param);
	            	params.put("user_id", userInfo.get("ID"));
	            	agentProduct.put(agent_id,storeActivityDao.queryActivityItemnumber(params));
	            }
	            specQuery.put("DATA", agentProduct);
	            specQuery.put("TYPE", 1);
	            sendMap.put("SPEC_QUERY", specQuery);
	            
	            Map<String, Object> retMap= (Map<String, Object>) queryForPost(sendMap,store_service_url+"/product/Product/productList");
				if (Integer.parseInt(retMap.get("state").toString()) != 1) {
					throw new RuntimeException("调用远程接口异常:"+retMap.get("message"));
				}
				Map<String, Object> data=(Map<String, Object>)retMap.get("data");
				zyDataList=(List<Map<String, Object>>)data.get("data");
	            for(Map<String, Object> map:zyDataList){
	            	//查询商家名称
	            	map.put("USER_MANAGE_NAME", storeActivityDao.queryUserManageNameByAgentId(map).get("USER_MANAGE_NAME"));
	            	//查询商品分类
	            	map.put("PRODUCT_TYPE", storeActivityDao.queryProductTypeByTypeId(map).get("TYPE_NAME"));
	            	
	            }
            }
            for (Map<String, Object> map:list){
            	if(Integer.parseInt(map.get("TYPE")+"")==2){
            		for(Map<String, Object> zyMap:zyDataList){
            			//根据经销商ID查询本地商家ID
            			Map<String, Object> userInfo=storeActivityDao.queryUserManageNameByAgentId(zyMap);
            			if(map.get("ITEMNUMBER").equals(zyMap.get("ITEMNUMBER"))&& Integer.parseInt(map.get("USER_ID").toString())==Integer.parseInt(userInfo.get("ID").toString())){
            				map.put("PRODUCT_NAME", zyMap.get("PRODUCT_NAME"));
            				map.put("PRODUCT_IMG_URL", zyMap.get("PRODUCT_IMG_URL"));
            				map.put("PRODUCT_TYPE", zyMap.get("PRODUCT_TYPE"));
            				map.put("MIN_RETAIL_PRICE", zyMap.get("MIN_PRICE"));
            				map.put("MAX_RETAIL_PRICE", zyMap.get("MAX_PRICE"));
            				map.put("YEAR", zyMap.get("YEAR"));
            				map.put("SEASON_NAME", zyMap.get("SEASON"));
            				break;
            			}
            		}
            		
            	}
            }
			if (list != null && list.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(list);
				gr.setTotal(count);
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
	 * 活动审核列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryActivityApprovalListForPage(HttpServletRequest request) {
		GridResult gr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			GridResult pageParamResult = PageUtil.handlePageParams(params);
			if(pageParamResult!=null){
				return pageParamResult;
			}
			
			if((!StringUtils.isEmpty(params.get("state")))&&params.get("state") instanceof String){
				params.put("state",(params.get("state")+"").split(","));
			}
			if((!StringUtils.isEmpty(params.get("activity_state")))&&params.get("activity_state") instanceof String){
				params.put("activity_state",(params.get("activity_state")+"").split(","));
			}
			int count=storeActivityDao.queryActivityApprovalListCount(params);
			List<Map<String,Object>> list = storeActivityDao.queryActivityApprovalListForPage(params);
			if (list != null && list.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(list);
				gr.setTotal(count);
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
	 * 活动详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryActivityDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String,Object> retMap = new HashMap<String,Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(StringUtils.isEmpty(params.get("activity_id"))){
				pr.setState(false);
				pr.setMessage("缺少参数[activity_id]");
                return pr;
            }
			//查询活动主表信息
			Map<String,Object> basicInfo = storeActivityDao.queryStoreActivityInfo(params);
			if(basicInfo!=null){
				//查询活动门店列表
				List<Map<String,Object>> storeList=storeActivityDao.queryActivityStoreList(params);
				retMap.put("basicInfo", basicInfo);
				retMap.put("storeList", storeList);
				pr.setMessage("获取活动详情成功");
				pr.setObj(retMap);
			}else{
				pr.setMessage("无数据");
			}
			pr.setState(true);
			
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
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult check(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> paramMap = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(StringUtils.isEmpty(paramMap.get("activity_id"))){
				pr.setState(false);
				pr.setMessage("缺少参数[activity_id]");
                return pr;
            }
			if(StringUtils.isEmpty(paramMap.get("state"))){
				pr.setState(false);
				pr.setMessage("缺少参数[state]");
                return pr;
            }
			Map<String, Object> activityInfo = storeActivityDao.queryStoreActivityInfo(paramMap);
			//校验活动是否存在
			if(activityInfo!=null){
				if("2".equals(paramMap.get("state").toString())){//通过
					String url="";
					if(Integer.parseInt(activityInfo.get("TYPE")+"")==4){
						//查询当前活动时间内是否存在已在活动中的门店
						Map<String, Object> checkMap=new HashMap<String,Object>();
						checkMap.put("activity_id", paramMap.get("activity_id"));
						checkMap.put("begin_date", activityInfo.get("BEGIN_DATE"));
						checkMap.put("end_date", activityInfo.get("END_DATE"));
						int count=storeActivityDao.queryIsExistsActivingStore(checkMap);
						if(count>0){
							pr.setState(false);
							pr.setMessage("当前活动门店在当前时间段内已在其它活动中进行");
			                return pr;
						}
						url="marketing/Recharge/rechargeActivityBatchInsert";
					}else{
						url="marketing/Discount/discountInsert";
					}
					storeActivityDao.updateActivityApprovalInfo(paramMap);
					Map<String, Object> map = getData(paramMap);
					Map<String, Object> retMap= (Map<String, Object>) queryForPost(map,store_service_url+url);
					if (Integer.parseInt(retMap.get("state").toString()) != 1) {
						throw new RuntimeException("调用远程接口异常:"+retMap.get("message"));
					}
					if(Integer.parseInt(activityInfo.get("TYPE")+"")!=4){
						Map<String, Object> data= (Map<String, Object>) retMap.get("data");
						paramMap.put("agent_activity_id",data.get("DISCOUNT_ID"));
					}else{
						paramMap.put("agent_activity_id",retMap.get("data"));
					}
					storeActivityDao.updateAgentActivityId(paramMap);//更新新零售活动ID
					pr.setState(true);
					pr.setMessage("审批通过");
				}else if("3".equals(paramMap.get("state").toString())){//驳回
					storeActivityDao.updateActivityApprovalInfo(paramMap);
					pr.setState(true);
					pr.setMessage("驳回成功");
				}else{
					pr.setState(false);
					pr.setMessage("审批状态错误");
				}
			}else{
				pr.setState(false);
				pr.setMessage("数据不存在");
			}
			
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 撤出活动商品(支持单个和多个商品撤出)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult removeProduct(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(StringUtils.isEmpty(paramMap.get("activity_id"))){
				pr.setState(false);
				pr.setMessage("缺少参数[activity_id]");
				return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("ids"))){
				pr.setState(false);
				pr.setMessage("缺少参数[ids]");
				return pr;
			}
			paramMap.put("ids", Arrays.asList(paramMap.get("ids").toString().split(",")));
			//撤出活动商品
			int count =storeActivityDao.removeProduct(paramMap);
			if(count<=0){
				pr.setState(false);
				pr.setMessage("撤出活动商品失败");
			}
			//查询活动信息
			Map<String, Object> activityInfo=storeActivityDao.queryStoreActivityInfo(paramMap);
			//查询经销商
			List<String> user_ids=storeActivityDao.queryPartnerUserList(paramMap);
			//查询未撤出活动商品的并且是审批通过的商品货号
			//List<String> itemnumbers=storeActivityDao.queryNotRemoveApprovalProduct(paramMap);
			Map<String, Object> sendMap = new HashMap<String, Object>();
			Map<String, Object> itemnumber = new HashMap<String, Object>();
			sendMap.put("DISCOUNT_ID", activityInfo.get("AGENT_ACTIVITY_ID"));
			sendMap.put("AGENT_ID", user_ids);
			sendMap.put("ITEM_TYPE", 1);
			//查询参加当前活动的经销商对应的货号
			for(String user_id:user_ids){
				paramMap.put("user_id", user_id);
				//查询当前经销商所有已审批的货号
				itemnumber.put(user_id, storeActivityDao.queryNotRemoveApprovalProduct(paramMap));
			}
			sendMap.put("ITEMNUMBER", itemnumber);
			Map<String, Object> retMap= (Map<String, Object>) queryForPost(sendMap,store_service_url+ly_activity_edit);
			if (Integer.parseInt(retMap.get("state").toString()) != 1) {
				throw new RuntimeException("调用远程接口异常:"+retMap.get("message"));
			}
			pr.setState(true);
			pr.setMessage("撤出活动商品成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException("撤出活动异常："+e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 商品追加
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult appendProduct(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(StringUtils.isEmpty(paramMap.get("activity_id"))){
				pr.setState(false);
				pr.setMessage("缺少参数[activity_id]");
				return pr;
			}
			List<String> itemnumbers = (List<String>) paramMap.get("product_itemnumber");
			if(itemnumbers!=null&&itemnumbers.size()>0){
				for(String itemnumber : itemnumbers){
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("id", paramMap.get("activity_id"));
					param.put("product_itemnumber", itemnumber);
					param.put("public_user_id", paramMap.get("public_user_id"));
					param.put("type", 1);
					//查询当前货号是否已存在
					int count =storeActivityDao.queryProductCountByItemnumber(param);
					if(count==0){
						//新增活动关联商品
						storeActivityDao.insertStoreActivityProduct(param);
					}else{
						//更新状态
						storeActivityDao.updateStoreActivityProduct(param);
					}
					
				}
			}
			List<Map<String, Object>> zy_itemnumbers = (List<Map<String, Object>>) paramMap.get("zy_product_itemnumber");
			if(zy_itemnumbers!=null&&zy_itemnumbers.size()>0){
				for(Map<String, Object> zy_itemnumber : zy_itemnumbers){
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("id", paramMap.get("activity_id"));
					param.put("product_itemnumber", zy_itemnumber.get("ITEMNUMBER"));
					param.put("public_user_id", paramMap.get("public_user_id"));
					param.put("user_id", storeActivityDao.queryUserManageNameByAgentId(zy_itemnumber).get("ID"));
					param.put("type", 2);
					//查询当前货号是否已存在
					int count =storeActivityDao.queryProductCountByItemnumber(param);
					if(count==0){
						//新增活动关联商品
						storeActivityDao.insertStoreActivityProduct(param);
					}else{
						//更新状态
						storeActivityDao.updateStoreActivityProduct(param);
					}
					
				}
			}
			pr.setState(true);
			pr.setMessage("追加商品成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException("撤出活动异常："+e.getMessage());
		}
		return pr;
	}
	/**
	 * 编辑活动
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult editActivity(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(StringUtils.isEmpty(paramMap.get("activity_id"))){
				pr.setState(false);
				pr.setMessage("缺少参数[activity_id]");
				return pr;
			}
			
			if(StringUtils.isEmpty(paramMap.get("store_id"))){
				pr.setState(false);
				pr.setMessage("缺少参数[store_id]");
				return pr;
			}
			
			//更新活动信息
			storeActivityDao.updateStoreActivityInfo(paramMap);
			
			//如果为满减满折类型需要保存满减满折相关数据
			if(Integer.parseInt(paramMap.get("type").toString())==2){
				//先删除
				storeActivityDao.deleteStoreActivityPreferential(paramMap);
				if(Integer.parseInt(paramMap.get("activity_type").toString())==1){
					//满减类型
					List<Map<String,Object>> minusList=(List<Map<String,Object>>)paramMap.get("minus_list");
					for(Map<String,Object> minus:minusList){
						minus.put("type", paramMap.get("activity_type"));
						minus.put("activity_id", paramMap.get("activity_id"));
					}
					int count=storeActivityDao.insertStoreActivityPreferential(minusList);
					if(count<=0){
						pr.setState(false);
						throw new RuntimeException("新增满减满折数据失败");
					}
				}else if(Integer.parseInt(paramMap.get("activity_type").toString())==2){
					//满折类型
					List<Map<String,Object>> saleList=(List<Map<String,Object>>)paramMap.get("sale_list");
					for(Map<String,Object> sale:saleList){
						sale.put("type", paramMap.get("activity_type"));
						sale.put("activity_id", paramMap.get("activity_id"));
						float discount=Float.parseFloat(sale.get("discount").toString());
						discount=(float)discount/10;
						sale.put("discount", discount);
					}
					int count=storeActivityDao.insertStoreActivityPreferential(saleList);
					if(count<=0){
						pr.setState(false);
						throw new RuntimeException("新增满减满折数据失败");
					}
				}
			}else if(Integer.parseInt(paramMap.get("type").toString())==4){
				storeActivityDao.deleteStoreActivityPreferential(paramMap);
				//充值满送
				List<Map<String,Object>> ruleList=(List<Map<String,Object>>)paramMap.get("rule_list");
				for(Map<String,Object> rule:ruleList){
					rule.put("type", 4);
					rule.put("activity_id", paramMap.get("activity_id"));
				}
				int count=storeActivityDao.insertStoreActivityPreferential(ruleList);
				if(count<=0){
					pr.setState(false);
					throw new RuntimeException("新增充值满送失败");
				}
			}
			
			//清除活动关联门店信息
			storeActivityDao.deleteStoreActivity(paramMap);
			List<Integer> store_ids = (List<Integer>) paramMap.get("store_id");
			for(Integer store_id : store_ids){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("id", paramMap.get("activity_id"));
				param.put("store_id", store_id);
				param.put("public_user_id", paramMap.get("public_user_id"));
				//新增活动关联门店
				storeActivityDao.insertStoreActivity(param);
			}
			
			//清除活动关联商品信息
			if(Integer.parseInt(paramMap.get("type").toString())!=4){
				storeActivityDao.deleteStoreActivityProduct(paramMap);
				List<String> itemnumbers = (List<String>) paramMap.get("product_itemnumber");
				if(itemnumbers!=null && itemnumbers.size()>0){
					for(String itemnumber : itemnumbers){
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("id", paramMap.get("activity_id"));
						param.put("product_itemnumber", itemnumber);
						param.put("public_user_id", paramMap.get("public_user_id"));
						param.put("type",1);
						//新增活动关联联营商品
						storeActivityDao.insertStoreActivityProduct(param);
					}
				}
				List<Map<String, Object>> zy_itemnumbers = (List<Map<String, Object>>) paramMap.get("zy_product_itemnumber");
				if(zy_itemnumbers!=null && zy_itemnumbers.size()>0){
					for(Map<String, Object> zy_itemnumber : zy_itemnumbers){
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("id",paramMap.get("activity_id"));
						param.put("product_itemnumber", zy_itemnumber.get("ITEMNUMBER"));
						param.put("user_id", storeActivityDao.queryUserManageNameByAgentId(zy_itemnumber).get("ID"));
						param.put("public_user_id", paramMap.get("public_user_id"));
						param.put("type",2);
						//新增活动关联自营商品
						storeActivityDao.insertStoreActivityProduct(param);
					}
				}
			}
			pr.setState(true);
			pr.setMessage("编辑成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException("编辑异常："+e.getMessage());
		}
		return pr;
	}
	/**
	 * 获取调用远程接口参数
	 * @param paramMap
	 * @return
	 */
	private Map<String, Object> getData(Map<String, Object> paramMap){
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, Object> storeListMap = new HashMap<String, Object>();
		storeListMap.put("activity_id", paramMap.get("activity_id"));
		Map<String, Object> param = storeActivityDao.queryStoreActivityInfo(paramMap);
		if("4".equals(param.get("TYPE").toString())){//充值满送
			retMap.put("COOPER_ID", paramMap.get("public_user_id"));
			retMap.put("COOPER_DISCOUNT_ID", paramMap.get("activity_id"));
			List<String> user_ids=storeActivityDao.queryPartnerUserList(paramMap);
			retMap.put("AGENT_ID", user_ids);
			retMap.put("ACTIVITIES_NAME", param.get("ACTIVITY_NAME"));
			retMap.put("START_TIME", param.get("BEGIN_DATE"));
			retMap.put("END_TIME", param.get("END_DATE"));
			if(Integer.parseInt(param.get("AUTO_MULTIP")+"")==1){
				retMap.put("IS_AUTOMATIC", 1);
			}else{
				retMap.put("IS_AUTOMATIC", 0);
			}
			retMap.put("RESTRICTIONS_NUMBER", param.get("LIMIT_CHARGE_TIMES"));
			Map<String,List<String>> data = new HashMap<String,List<String>>();
			for(String user_id:user_ids){
				storeListMap.put("user_id", user_id);
				data.put(user_id, storeActivityDao.queryStoreList(storeListMap));
			}
			retMap.put("STORE_ARR", data);
			Map<String, Object>activityPreferentInfo=storeActivityDao.queryActivityPreferentInfo(storeListMap);
			retMap.put("FULL_MONEY",java.util.Arrays.asList(activityPreferentInfo.get("SPEND_MONEY").toString().split(",")));
			retMap.put("GIVE_MONEY",java.util.Arrays.asList(activityPreferentInfo.get("DISCOUNT").toString().split(",")));
		}else{
			retMap.put("COOPER_ID", paramMap.get("public_user_id"));
			retMap.put("COOPER_DISCOUNT_ID", paramMap.get("activity_id"));
			List<String> user_ids=storeActivityDao.queryPartnerUserList(paramMap);
			retMap.put("AGENT_ID", user_ids);
			retMap.put("NAME", param.get("ACTIVITY_NAME"));
			retMap.put("TIME_START", param.get("BEGIN_DATE"));
			retMap.put("TIME_END", param.get("END_DATE"));
			if("1".equals(param.get("START_STATE").toString())){
				retMap.put("STATUS", 1);
			}else{
				retMap.put("STATUS", 0);
			}
			Map<String,List<String>> data = new HashMap<String,List<String>>();
			for(String user_id:user_ids){
				storeListMap.put("user_id", user_id);
				data.put(user_id, storeActivityDao.queryStoreList(storeListMap));
			}
			retMap.put("DATA", data);
			if("1".equals(param.get("TYPE").toString())){//限时折扣
				retMap.put("TYPE", 1);
				retMap.put("DISCOUNT", Double.parseDouble(param.get("DISCOUNT").toString())*100);
			}else if("2".equals(param.get("TYPE").toString())){//满减满折
				Map<String, Object>activityPreferentInfo=storeActivityDao.queryActivityPreferentInfo(storeListMap);
				if("1".equals(param.get("ACTIVITY_TYPE").toString())){
					retMap.put("TYPE", 3);//满减
					retMap.put("LIMIT_PRICE",activityPreferentInfo.get("SPEND_MONEY"));
					retMap.put("DISCOUNT",activityPreferentInfo.get("DISCOUNT"));
				}else{
					retMap.put("TYPE", 4);//满折
					retMap.put("LIMIT_PRICE",activityPreferentInfo.get("SPEND_MONEY"));
					retMap.put("DISCOUNT",activityPreferentInfo.get("DISCOUNT"));
				}
			}else{//一口价
				retMap.put("TYPE", 2);//一口价
				retMap.put("LIMIT_NUM",param.get("PURCHASE_RESTRICTION"));
				retMap.put("DISCOUNT",param.get("FIXED_PRICE"));
			}
		}
		return retMap;
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
	 * 审批商品
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult approvalProduct(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(StringUtils.isEmpty(paramMap.get("ids"))){
				pr.setState(false);
				pr.setMessage("缺少参数[ids]");
				return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("state"))){
				pr.setState(false);
				pr.setMessage("缺少参数[state]");
				return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("activity_id"))){
				pr.setState(false);
				pr.setMessage("缺少参数activity_id");
				return pr;
			}
			List<String> ids= Arrays.asList(paramMap.get("ids").toString().split(","));
			paramMap.put("ids",ids);
			if(Integer.parseInt(paramMap.get("state").toString())==1){
				//判断当前审批商品的活动是否已经结束
				Map<String, Object> storeActivityInfo=storeActivityDao.queryStoreActivityInfo(paramMap);
				if(Integer.parseInt(storeActivityInfo.get("ACTIVITY_STATE")+"")==3){
					pr.setState(false);
					pr.setMessage("当前活动已过期");
					return pr;
				}
				int flag=0;
				String product_itemnumber="";
				for(String id:ids){
					//判断审批的商品是否已在其它进行中的活动
					List<Map<String, Object>> productInfoList=storeActivityDao.queryActivingProductInfo(id);
					paramMap.put("product_id", id);
					//判断审批商品是否是否在本门店中进行
					int num =storeActivityDao.checkActivityStoreCount(paramMap);
					Map<String, Object> info=storeActivityDao.queryStoreActivityProductInfo(paramMap);
					if(productInfoList.size()>0 && num>0){
						flag=1;
						product_itemnumber=info.get("PRODUCT_ITEMNUMBER").toString();
						break;
					}
					if(Integer.parseInt(info.get("STATE")+"")!=0){
						flag=2;
						product_itemnumber=info.get("PRODUCT_ITEMNUMBER").toString();
						break;
					}
				}
				if(flag==1||flag==2){
					if(flag==1){
						pr.setState(false);
						pr.setMessage(product_itemnumber+" 已在其它活动中进行");
						return pr;
					}else{
						pr.setState(false);
						pr.setMessage("货号"+product_itemnumber+" 审核状态异常");
						return pr;
					}
				}
			}
			//修改活动商品审批状态
			int count =storeActivityDao.updateStoreActivityProductState(paramMap);
			if(count<=0){
				pr.setState(false);
				throw new RuntimeException("审批失败");
			}
			if(Integer.parseInt(paramMap.get("state").toString())==1){
				Map<String, Object> sendMap = new HashMap<String, Object>();
				Map<String, Object> itemnumber = new HashMap<String, Object>();
				List<String> user_ids=storeActivityDao.queryPartnerUserList(paramMap);
				sendMap.put("AGENT_ID", user_ids);
				sendMap.put("DISCOUNT_ID", storeActivityDao.qeuryDiscountId(paramMap));
				sendMap.put("ITEM_TYPE", 1);
				//查询参加当前活动的经销商对应的货号
				for(String user_id:user_ids){
					paramMap.put("user_id", user_id);
					//查询当前经销商所有已审批的货号
					itemnumber.put(user_id, storeActivityDao.queryProductItemnumberByActivityId(paramMap));
				}
				sendMap.put("ITEMNUMBER", itemnumber);
				Map<String, Object> retMap= (Map<String, Object>) queryForPost(sendMap,store_service_url+ly_activity_edit);
				if (Integer.parseInt(retMap.get("state").toString()) != 1) {
					throw new RuntimeException("调用远程接口异常:"+retMap.get("message"));
				}
			}
			pr.setState(true);
			pr.setMessage("审批活动商品成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	/**
	 *  查询指定商家下拉数据
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryUserOption(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			params.put("store_list",Arrays.asList(params.get("store_list").toString().split(",")));
			pr.setState(true);
			pr.setMessage("查询成功");
			pr.setObj(storeActivityDao.queryUserOption(params));
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
		return pr;
	}
}
