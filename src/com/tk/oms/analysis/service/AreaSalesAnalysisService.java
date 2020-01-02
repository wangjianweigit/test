package com.tk.oms.analysis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tk.oms.analysis.dao.AreaSalesAnalysisDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.Transform;

@Service("AreaSalesAnalysisService")
public class AreaSalesAnalysisService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private AreaSalesAnalysisDao areaSalesAnalysisDao;
	@Value("${jdbc_user}")
	private String jdbc_user;
	
	/**
	 * 区域销售报表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryList(HttpServletRequest request) {
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
			if(StringUtils.isEmpty(paramMap.get("threshold"))){
				gr.setState(false);
            	gr.setMessage("缺少大客户销售额阈值");
                return gr;
			}
			paramMap.put("jdbc_user", jdbc_user);
			paramMap.put("threshold", Integer.parseInt(paramMap.get("threshold").toString()));
			Map<String,Object> retMap = new HashMap<String,Object>();
			//查询区域销售报表数量
			int total = areaSalesAnalysisDao.queryCount(paramMap);
			//查询区域销售报表列表
			List<Map<String, Object>> dataList = areaSalesAnalysisDao.queryList(paramMap);
			//查询区域销售报表总合计
			Map<String, Object> map = areaSalesAnalysisDao.queryListTotal(paramMap);
			
			retMap.put("totalData",map);
			retMap.put("list", dataList);
			if (dataList != null && dataList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功");
				gr.setObj(retMap);
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
