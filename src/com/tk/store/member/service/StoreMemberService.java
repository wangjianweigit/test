package com.tk.store.member.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tk.oms.sysuser.dao.SysUserInfoDao;
import com.tk.store.marking.dao.StoreActivityDao;
import com.tk.store.member.dao.StoreMemberDao;
import com.tk.sys.config.EsbConfig;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpClientUtil;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.Jackson;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Service("StoreMemberService")
public class StoreMemberService {
	@Value("${store_service_url}")
	private String store_service_url;// 联营门店服务地址
	@Value("${member_list}")
	private String member_list;// 会员列表
	@Value("${member_detail}")
	private String member_detail;// 会员列表
	@Value("${member_trad_record}")
	private String member_trad_record;// 成交记录
	@Value("${member_integral_record}")
	private String member_integral_record;// 积分记录
	@Value("${member_status}")
	private String member_status;// 解冻冻结
	
	@Resource
	private StoreMemberDao storeMemberDao;
	@Resource
	private StoreActivityDao storeActivityDao;
	@Resource
	private SysUserInfoDao sysUserInfoDao;
	
	/**
	 * 会员列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryStoreMemberListForPage(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> totalData = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(StringUtils.isEmpty(paramMap.get("public_user_type"))){
				gr.setMessage("缺少必要参数");
				gr.setState(false);
				return gr;
			}
			if(Integer.parseInt(paramMap.get("public_user_type")+"")==1||Integer.parseInt(paramMap.get("public_user_type")+"")==2){
				if(StringUtils.isEmpty(paramMap.get("region_id"))){
					//查询所有区域数据
					List<Map<String, Object>> regionList = storeMemberDao.userSelect(paramMap);
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < regionList.size(); i++) {
			            sb.append(regionList.get(i).get("id")).append(",");
			        }
			        String regionStr=sb.toString().substring(0, sb.toString().length() - 1);
			        param.put("COOPER_ID",regionStr);
				}else{
					param.put("COOPER_ID", paramMap.get("region_id"));
				}
			}else if(Integer.parseInt(paramMap.get("public_user_type")+"")==9){
				//查询当前区域数据
				param.put("COOPER_ID", paramMap.get("public_user_id"));
			}else{
				totalData.put("INTEGRAL", 0);
			    totalData.put("ACCOUNT_BALANCE", 0);
			    totalData.put("USER_DEAL_ORDER_COUNT",  0);
			    totalData.put("USER_DEAL_MONEY_SUM", 0);
			    totalData.put("NO_GIVE_MONEY", 0);
			    resultMap.put("list", new ArrayList<Map<String, Object>>());
				resultMap.put("totalData", totalData);
				gr.setState(true);
				gr.setMessage("无数据");
				gr.setTotal(0);
				gr.setObj(resultMap);
				return gr;
			}
			param.put("PAGE", paramMap.get("pageIndex"));
			param.put("PAGESIZE", paramMap.get("pageSize"));
			if(!StringUtils.isEmpty(paramMap.get("phone"))){param.put("PHONE", paramMap.get("phone"));}
			if(!StringUtils.isEmpty(paramMap.get("name"))){param.put("NAME", paramMap.get("name"));}
			if(!StringUtils.isEmpty(paramMap.get("business_id"))){param.put("AGENT_ID", paramMap.get("business_id"));}
			if(!StringUtils.isEmpty(paramMap.get("store_id"))){
				param.put("STORE_ID", storeMemberDao.queryAgentStoreId(paramMap));
				}
			if(!StringUtils.isEmpty(paramMap.get("sex"))){param.put("GENDER", paramMap.get("sex"));}
			if(!StringUtils.isEmpty(paramMap.get("start_date"))){param.put("START_TIME", dateToStamp(paramMap.get("start_date").toString()));}
			if(!StringUtils.isEmpty(paramMap.get("end_date"))){param.put("END_TIME", dateToStamp(paramMap.get("end_date").toString()));}
			if((!StringUtils.isEmpty(paramMap.get("status")))){param.put("STATUS", paramMap.get("status"));}
			
			Map<String, Object> retMap= (Map<String, Object>) queryForPost(param,store_service_url+member_list);
			if(StringUtils.isEmpty(retMap.get("data"))||"0".equals(retMap.get("state").toString())){
				totalData.put("INTEGRAL", 0);
			    totalData.put("ACCOUNT_BALANCE", 0);
			    totalData.put("USER_DEAL_ORDER_COUNT",  0);
			    totalData.put("USER_DEAL_MONEY_SUM", 0);
			    totalData.put("NO_GIVE_MONEY", 0);
			    resultMap.put("list", new ArrayList<Map<String, Object>>());
				resultMap.put("totalData", totalData);
				gr.setState(true);
				gr.setMessage("获取会员列表失败："+retMap.get("message"));
				gr.setTotal(0);
				gr.setObj(resultMap);
				return gr;
			}
			Map<String, Object> map = (Map<String, Object>) retMap.get("data");
			List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("DATA");
			if (list != null && list.size() > 0) {
			    for(Map<String, Object> resmap : list) {
			        if(!resmap.containsKey("LEVEL_NAME")) {
                        resmap.put("LEVEL_NAME", null);
                    }
                    if(!resmap.containsKey("LEVEL_ID")) {
                        resmap.put("LEVEL_ID", null);
                    }
                    if(!resmap.containsKey("DISCOUNT")) {
                        resmap.put("DISCOUNT", null);
                    }
                }
			    List<Map<String, Object>> retList = storeMemberDao.queryStoreMemberList(list);
			    if(map.containsKey("INTEGRAL")&&!StringUtils.isEmpty(map.get("INTEGRAL"))) {
			    	totalData.put("INTEGRAL", map.get("INTEGRAL"));
			    }else {
			    	totalData.put("INTEGRAL", 0);
			    }
			    if(map.containsKey("MONEY")&&!StringUtils.isEmpty(map.get("MONEY"))) {
			    	totalData.put("ACCOUNT_BALANCE", map.get("MONEY"));
			    }else {
			    	totalData.put("ACCOUNT_BALANCE", 0);
			    }
			    if(map.containsKey("DEALCOUNT")&&!StringUtils.isEmpty(map.get("DEALCOUNT"))) {
			    	totalData.put("USER_DEAL_ORDER_COUNT", map.get("DEALCOUNT"));
			    }else {
			    	totalData.put("USER_DEAL_ORDER_COUNT", 0);
			    }
			    if(map.containsKey("MONEYCOUNT")&&!StringUtils.isEmpty(map.get("MONEYCOUNT"))) {
			    	totalData.put("USER_DEAL_MONEY_SUM", map.get("MONEYCOUNT"));
			    }else {
			    	totalData.put("USER_DEAL_MONEY_SUM", 0);
			    }
			    if(map.containsKey("GIVE_MONEY")&&!StringUtils.isEmpty(map.get("GIVE_MONEY"))) {
			    	totalData.put("NO_GIVE_MONEY", map.get("GIVE_MONEY"));
			    }else {
			    	totalData.put("NO_GIVE_MONEY", 0);
			    }
			    resultMap.put("list", retList);
			    resultMap.put("totalData", totalData);
				gr.setMessage("获取数据成功");
				gr.setObj(resultMap);
			} else {
				totalData.put("INTEGRAL", 0);
			    totalData.put("ACCOUNT_BALANCE", 0);
			    totalData.put("USER_DEAL_ORDER_COUNT",  0);
			    totalData.put("USER_DEAL_MONEY_SUM", 0);
			    totalData.put("NO_GIVE_MONEY", 0);
			    resultMap.put("list", new ArrayList<Map<String, Object>>());
				resultMap.put("totalData", totalData);
				gr.setMessage("无数据");
				gr.setObj(resultMap);
			}
			if(!StringUtils.isEmpty(map.get("COUNT"))){
				gr.setTotal(Integer.valueOf(map.get("COUNT").toString()));
			}else{
				gr.setTotal(0);
			}
			gr.setState(true);
			
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	/**
	 * 会员详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryStoreMemberDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			
			param.put("AGENT_ID", paramMap.get("agent_id"));
			param.put("USER_ID", paramMap.get("user_id"));
			
			Map<String, Object> retMap= (Map<String, Object>) queryForPost(param,store_service_url+member_detail);
			if(!StringUtils.isEmpty(retMap.get("data"))){
				pr.setState(true);
				pr.setMessage("获取会员详情成功");
				pr.setObj(storeMemberDao.queryStoreMemberDetail((Map<String, Object>) retMap.get("data")));
			}else{
				pr.setState(false);
				pr.setMessage("获取会员详情失败");
			}
			
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 成交记录
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryTradRecordListForPage(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			param.put("PAGE", paramMap.get("pageIndex"));
			param.put("PAGESIZE", paramMap.get("pageSize"));
			param.put("COOPER_ID", paramMap.get("region_id"));
			param.put("USER_ID", paramMap.get("user_id"));
			if(!StringUtils.isEmpty(paramMap.get("start_date"))){param.put("START_TIME", dateToStamp(paramMap.get("start_date").toString()));}
			if(!StringUtils.isEmpty(paramMap.get("end_date"))){param.put("END_TIME", dateToStamp(paramMap.get("end_date").toString()));}
			
			Map<String, Object> retMap= (Map<String, Object>) queryForPost(param,store_service_url+member_trad_record);
			if(StringUtils.isEmpty(retMap.get("data"))){
				gr.setState(true);
				gr.setMessage("获取成交记录失败："+retMap.get("message"));
				gr.setTotal(0);
				return gr;
			}
			Map<String, Object> map = (Map<String, Object>) retMap.get("data");
			List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("data");
			
			if (list != null && list.size() > 0) {
				gr.setMessage("获取成交记录成功");
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
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	/**
	 * 积分记录
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryIntegralRecordListForPage(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			param.put("PAGE", paramMap.get("pageIndex"));
			param.put("PAGESIZE", paramMap.get("pageSize"));
			param.put("COOPER_ID", paramMap.get("region_id"));
			param.put("USER_ID", paramMap.get("user_id"));
			
			if(!StringUtils.isEmpty(paramMap.get("start_date"))){param.put("START_TIME", dateToStamp(paramMap.get("start_date").toString()));}
			if(!StringUtils.isEmpty(paramMap.get("end_date"))){param.put("END_TIME", dateToStamp(paramMap.get("end_date").toString()));}
			
			Map<String, Object> retMap= (Map<String, Object>) queryForPost(param,store_service_url+member_integral_record);
			if(StringUtils.isEmpty(retMap.get("data"))){
				gr.setState(true);
				gr.setMessage("获取积分记录失败："+retMap.get("message"));
				gr.setTotal(0);
				return gr;
			}
			
			Map<String, Object> map = (Map<String, Object>) retMap.get("data");
			List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("data");
			
			if (list != null && list.size() > 0) {
				gr.setMessage("获取积分记录成功");
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
			
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	/**
	 * 解冻/冻结
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult state(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			param.put("AGENT_ID", paramMap.get("agent_id"));
			param.put("USER_ID", paramMap.get("user_id"));
			param.put("STATUS", paramMap.get("status"));
			
			Map<String, Object> retMap= (Map<String, Object>) queryForPost(param,store_service_url+member_status);
			if(Integer.parseInt(retMap.get("state").toString()) != 1){
				pr.setState(false);
				pr.setMessage("操作失败");
			}else{
				pr.setState(true);
				pr.setMessage("操作成功");
			}
			
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 门店下拉框
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object storeSelect(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(StringUtils.isEmpty(params.get("user_id"))){
  				pr.setState(false);
  				pr.setMessage("缺少参数user_id");
  				return pr;
  			}
			//门店列表
  			List<Map<String, Object>> list = storeMemberDao.queryUserStoreList(params);
  			if (list != null && list.size() > 0) {
  				pr.setState(true);
  				pr.setMessage("获取门店下拉框成功");
  				pr.setObj(list);
  			} else {
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
	 * 区域下拉框
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object userSelect(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			
  			List<Map<String, Object>> list = storeMemberDao.userSelect(params);
  			if (list != null && list.size() > 0) {
  				pr.setState(true);
  				pr.setMessage("获取区域下拉框成功");
  				pr.setObj(list);
  			} else {
  				pr.setState(true);
  				pr.setMessage("无数据");
  			}
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
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
	
	/*  
	 * 将时间转换为时间戳 
	 */      
	public String dateToStamp(String s) throws Exception {  
	    String res;  
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    Date date = sdf.parse(s);  
	    long ts = date.getTime();  
	    res = String.valueOf(ts/1000);  
	    return res;  
	}
	
	/**
	 * 会员充值列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryStoreMemberRechargeRecordListForPage(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> sendMap = new HashMap<String, Object>();
		Map<String, Object> totalData = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(Integer.parseInt(paramMap.get("public_user_type")+"")==1||Integer.parseInt(paramMap.get("public_user_type")+"")==2){
				if(StringUtils.isEmpty(paramMap.get("region_id"))){
					//查询所有区域数据
					List<Map<String, Object>> regionList = storeMemberDao.userSelect(paramMap);
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < regionList.size(); i++) {
			            sb.append(regionList.get(i).get("id")).append(",");
			        }
			        String regionStr=sb.toString().substring(0, sb.toString().length() - 1);
			        sendMap.put("COOPER_ID",regionStr);
				}else{
					sendMap.put("COOPER_ID", paramMap.get("region_id"));
				}
			}else if(Integer.parseInt(paramMap.get("public_user_type")+"")==9){
				//查询当前区域数据
				sendMap.put("COOPER_ID", paramMap.get("public_user_id"));
			}else{
				totalData.put("TRADE_MONEY", 0);
			    totalData.put("NO_GIVE_MONEY", 0);
			    resultMap.put("list", new ArrayList<Map<String, Object>>());
				resultMap.put("totalData", totalData);
				gr.setState(true);
				gr.setMessage("无数据");
				gr.setTotal(0);
				gr.setObj(resultMap);
				return gr;
			}
			sendMap.put("PAGE", paramMap.get("pageIndex"));
			sendMap.put("PAGESIZE", paramMap.get("pageSize"));
			
			if(!StringUtils.isEmpty(paramMap.get("user_mobile"))){sendMap.put("USER_MOBILE", paramMap.get("user_mobile"));}
			if(!StringUtils.isEmpty(paramMap.get("user_name"))){sendMap.put("USER_NAME", paramMap.get("user_name"));}
			if(!StringUtils.isEmpty(paramMap.get("start_time"))){sendMap.put("START_TIME", paramMap.get("start_time"));}
			if(!StringUtils.isEmpty(paramMap.get("end_time"))){sendMap.put("END_TIME", paramMap.get("end_time"));}
			if(!StringUtils.isEmpty(paramMap.get("trade_style"))){sendMap.put("TRADE_STYLE", paramMap.get("trade_style"));}
			if(!StringUtils.isEmpty(paramMap.get("trade_order"))){sendMap.put("TRADE_ORDER", paramMap.get("trade_order"));}
			if(!StringUtils.isEmpty(paramMap.get("user_id"))){
				sendMap.put("AGENT_ID", storeActivityDao.queryUserManageNameByAgentId(paramMap).get("AGENT_ID"));
			}
			if(!StringUtils.isEmpty(paramMap.get("store_id"))){
				sendMap.put("STORE_ID", storeMemberDao.queryAgentStoreId(paramMap));
			}
			Map<String, Object> retMap= (Map<String, Object>) queryForPost(sendMap,store_service_url+"/user/User/getRechargeList");
			if(StringUtils.isEmpty(retMap.get("data"))){
				totalData.put("TRADE_MONEY", 0);
			    totalData.put("NO_GIVE_MONEY", 0);
			    resultMap.put("list", new ArrayList<Map<String, Object>>());
				resultMap.put("totalData", totalData);
				gr.setState(true);
				gr.setMessage("无数据");
				gr.setTotal(0);
				gr.setObj(resultMap);
				return gr;
			}
			if (Integer.parseInt(retMap.get("state").toString()) == 1) {
               gr.setState(true);
               gr.setMessage("获取充值记录成功");
               Map<String, Object> data = (Map<String, Object>) retMap.get("data");
               List<Map<String, Object>> list = (List<Map<String, Object>>) data.get("data");
               for(Map<String, Object> map:list){
            	   map.put("USER_MANAGE_NAME", storeActivityDao.queryUserManageNameByAgentId(map).get("USER_MANAGE_NAME"));
            	   map.put("REGION_NAME", sysUserInfoDao.queryByUserId(Long.parseLong(map.get("COOPER_ID")+"")).getUser_realname());
               }
               int total=Integer.parseInt(data.get("total").toString());
               totalData.put("TRADE_MONEY", data.containsKey("RECHARGE_MONEY")==false ?0:data.get("RECHARGE_MONEY"));
			   totalData.put("NO_GIVE_MONEY", data.containsKey("NO_GIVE_MONEY")==false ?0:data.get("NO_GIVE_MONEY"));
			   resultMap.put("totalData", totalData);
               resultMap.put("list", list);
               gr.setTotal(total);
               gr.setObj(resultMap);
			}else{
				totalData.put("TRADE_MONEY", 0);
			    totalData.put("NO_GIVE_MONEY", 0);
			    resultMap.put("list", new ArrayList<Map<String, Object>>());
			    resultMap.put("totalData", totalData);
                gr.setState(false);
                gr.setMessage(retMap.get("msg").toString());
                gr.setObj(resultMap);
           }
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	
	
}
