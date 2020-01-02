package com.tk.oms.stationed.service;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.tk.oms.stationed.dao.StationedSettlementDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : StationedService
 * 入驻商管理
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-4-10
 */

@Service("StationedSettlementService")
public class StationedSettlementService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private StationedSettlementDao stationedSettlementDao;
	/**
	 * 查询所有的入驻商信息列表
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryStationedList(HttpServletRequest request){
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = null;
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			//查询数据库信息
			List<Map<String,Object>> sourceList = stationedSettlementDao.queryStationedList(paramMap);
			pr.setState(true);
			pr.setMessage("查询入驻商信息列表成功");
			pr.setObj(sourceList);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e.toString());
		}
		return pr;
	}
	/**
	 * 查询有月结权限的平台会员对于各个入驻商的月结欠款信息
	 * 以及代发费、物流费、仓储费、平台服务费、入驻商服务费信息
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryUserMonthlySettlement(HttpServletRequest request){
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = null;
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			//查询数据库信息
			List<Map<String,Object>> sourceList = stationedSettlementDao.queryUserMonthlySettlement(paramMap);
			/**
			 * 对数据进行处理，一个平台会员一行数据
			 */
			Map<String,List<Map<String,Object>>> tempMap = new HashMap<String, List<Map<String,Object>>>();
			List<Map<String,Object>> tempList = null;
			for(Map<String,Object> m:sourceList){
				String USER_MANAGE_NAME = String.valueOf(m.get("USER_MANAGE_NAME"));
				if(tempMap.containsKey(USER_MANAGE_NAME)){
					tempList = tempMap.get(USER_MANAGE_NAME);
					tempList.add(m);
				}else{
					tempList = new ArrayList<Map<String,Object>>();
					tempList.add(m);
					tempMap.put(USER_MANAGE_NAME, tempList);
				}
			}
			 DecimalFormat df=(DecimalFormat)NumberFormat.getInstance(); 
			 df.setMaximumFractionDigits(2); 
			/***
			 * 将一个用户的所有信息存放到一个Map之中
			 */
			List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();//最终的返回数据列表
			for(String user_manage_name:tempMap.keySet()){
				Map<String,Object> userMap = new HashMap<String, Object>();
				List<Map<String,Object>> list = tempMap.get(user_manage_name);
				int i = 0;
				float count = 0;
				for(Map<String,Object> map:list){
					if(i==0){
						userMap.put("name",map.get("USER_MANAGE_NAME").toString());
						userMap.put("login_name",map.get("LOGIN_NAME").toString());
					}
					float SETTLEMENT_AMOUNT = Float.parseFloat(map.get("SETTLEMENT_AMOUNT").toString());
					userMap.put(String.valueOf(map.get("COMPANY_NAME")), SETTLEMENT_AMOUNT);
					i++;
					count+=SETTLEMENT_AMOUNT;
				}
				userMap.put("count", df.format(count));
				resultList.add(userMap);
			}
			gr.setState(true);
			gr.setMessage("查询月结客户结算核对表成功");
			gr.setObj(resultList);
			gr.setTotal(resultList.size());
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error(e.toString());
		}
		return gr;
	}
	/**
	 * 查询有月结权限的门店的对于各个入驻商的月结欠款信息
	 * 以及代发费、物流费、仓储费、平台服务费、入驻商服务费信息
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryStoreMonthlySettlement(HttpServletRequest request){
		DecimalFormat df=(DecimalFormat)NumberFormat.getInstance(); 
		df.setMaximumFractionDigits(2); 
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = null;
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			//查询数据库信息
			List<Map<String,Object>> sourceList = stationedSettlementDao.queryStoreMonthlySettlement(paramMap);
			/**
			 * 对数据进行处理，一个门店一行数据
			 */
			Map<String,List<Map<String,Object>>> tempMap = new HashMap<String, List<Map<String,Object>>>();
			List<Map<String,Object>> tempList = null;
			for(Map<String,Object> m:sourceList){
				String STORE_NAME = String.valueOf(m.get("STORE_NAME"));
				if(tempMap.containsKey(STORE_NAME)){
					tempList = tempMap.get(STORE_NAME);
					tempList.add(m);
				}else{
					tempList = new ArrayList<Map<String,Object>>();
					tempList.add(m);
					tempMap.put(STORE_NAME, tempList);
				}
			}
			/***
			 * 将一个用户的所有信息存放到一个Map之中
			 */
			List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();//最终的返回数据列表
			for(String user_manage_name:tempMap.keySet()){
				Map<String,Object> storeMap = new HashMap<String, Object>();
				List<Map<String,Object>> list = tempMap.get(user_manage_name);
				int i = 0;
				float count = 0;
				for(Map<String,Object> map:list){
					if(i==0){
						storeMap.put("store_name",map.get("STORE_NAME").toString());
					}
					float REIMBURSEMENT_AMOUNT = Float.parseFloat(map.get("REIMBURSEMENT_AMOUNT").toString());//授信未清分
					float UN_SETTLEMENT_AMOUNT = Float.parseFloat(map.get("UN_SETTLEMENT_AMOUNT").toString());//未结算金额
					storeMap.put(String.valueOf(map.get("COMPANY_NAME"))+"_1", REIMBURSEMENT_AMOUNT);//授信未清分
					storeMap.put(String.valueOf(map.get("COMPANY_NAME"))+"_2", UN_SETTLEMENT_AMOUNT);//未结算金额
					i++;
					count+=(REIMBURSEMENT_AMOUNT+UN_SETTLEMENT_AMOUNT);
				}
				storeMap.put("count", df.format(count));
				resultList.add(storeMap);
			}
			gr.setState(true);
			gr.setMessage("查询门店结算核对表成功");
			gr.setObj(resultList);
			gr.setTotal(resultList.size());
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error(e.toString());
		}
		return gr;
	}
}
