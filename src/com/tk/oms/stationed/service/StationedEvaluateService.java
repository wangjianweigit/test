package com.tk.oms.stationed.service;

import com.tk.oms.stationed.dao.StationedEvaluateDao;
import com.tk.sys.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("StationedEvaluateService")
public class StationedEvaluateService{
	private Log logger = LogFactory.getLog(this.getClass());

	@Resource
	private StationedEvaluateDao stationedEvaluateDao;
	@Value("${jdbc_user}")
	private String jdbc_user;
	/**
	 * 获取入驻商评估表数据
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);

			if(StringUtils.isEmpty(json)) {
				gr.setState(false);
				gr.setMessage("缺少参数");
				return gr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			PageUtil.handlePageParams(paramMap);

			paramMap.put("jdbc_user", jdbc_user);
			//查询入驻商审核列表
			int total = stationedEvaluateDao.queryCountForPage(paramMap);
			List<Map<String, Object>> dataList = stationedEvaluateDao.queryListForPage(paramMap);

			if (dataList != null && dataList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(dataList);
				gr.setTotal(total);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error("查询失败："+e.getMessage());
		}
		return gr;
	}

	/**
	 * 获取入驻商评估表详情数据
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryDetail(HttpServletRequest request) {
		GridResult gr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);

			if(StringUtils.isEmpty(json)) {
				gr.setState(false);
				gr.setMessage("缺少参数");
				return gr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			PageUtil.handlePageParams(paramMap);

			paramMap.put("jdbc_user", jdbc_user);
			//查询入驻商审核列表
			int total = stationedEvaluateDao.queryDetailCount(paramMap);
			List<Map<String, Object>> dataList = stationedEvaluateDao.queryDetailList(paramMap);

			if (dataList != null && dataList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(dataList);
				gr.setTotal(total);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error("查询失败："+e.getMessage());
		}
		return gr;
	}
}