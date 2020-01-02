package com.tk.oms.analysis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tk.oms.analysis.dao.GroundPushDataStatisticsDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.Transform;

@Service("GroundPushDataStatisticsService")
public class GroundPushDataStatisticsService {
	
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
    private GroundPushDataStatisticsDao groundPushDataStatisticsDao;
	
	
	/**
	 * 业务人员地推数据查询
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryYwryList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
			if(paramMap.size() == 0) {
            	gr.setState(false);
            	gr.setMessage("参数缺失");
                return gr;
            }
			int total=groundPushDataStatisticsDao.queryYwryCount(paramMap);
			//查询业务人员列表
			List<Map<String,Object>> user_list=groundPushDataStatisticsDao.queryYwryList(paramMap);
			if(user_list==null||user_list.size()==0){
				gr.setMessage("无数据");
				gr.setObj("");
			}else{
				paramMap.put("user_list", user_list);
				//将新用户存放到临时表
				groundPushDataStatisticsDao.insertNewUser(paramMap);
				//将沉睡用户存放到临时表
				groundPushDataStatisticsDao.insertSleepUser(paramMap);
				
				List<Map<String,Object>> Resultlist=groundPushDataStatisticsDao.queryYwryGroundPushDataStatistics(paramMap);
				gr.setMessage("查询成功");
				gr.setObj(Resultlist);
			}
			gr.setState(true);
			gr.setTotal(total);
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error("查询失败："+e.getMessage());
		}
		return gr;
	}
	
	/**
	 * 推荐人地推数据查询
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryTjrList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
			if(paramMap.size() == 0) {
            	gr.setState(false);
            	gr.setMessage("参数缺失");
                return gr;
            }
			int total=groundPushDataStatisticsDao.queryTjrCount(paramMap);
			//查询推荐员列表
			List<Map<String,Object>> user_list=groundPushDataStatisticsDao.queryTjrList(paramMap);
			if(user_list==null||user_list.size()==0){
				gr.setMessage("无数据");
				gr.setObj("");
			}else{
				if(Integer.parseInt(paramMap.get("public_user_type")+"")==9){
					Map<String,Object> tempMap=new HashMap<String,Object>();
					tempMap.put("ID", paramMap.get("public_user_id"));
					tempMap.put("USER_NAME", paramMap.get("public_user_name"));
					user_list.add(tempMap);
					total=total+1;
				}
				paramMap.put("user_list", user_list);
				//将新用户存放到临时表
				groundPushDataStatisticsDao.insertNewUser(paramMap);
				//将沉睡用户存放到临时表
				groundPushDataStatisticsDao.insertSleepUser(paramMap);
				
				List<Map<String,Object>> Resultlist=groundPushDataStatisticsDao.queryTjrGroundPushDataStatistics(paramMap);
				gr.setMessage("查询成功");
				gr.setObj(Resultlist);
			}
			gr.setState(true);
			gr.setTotal(total);
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error("查询失败："+e.getMessage());
		}
		return gr;
	}

}
