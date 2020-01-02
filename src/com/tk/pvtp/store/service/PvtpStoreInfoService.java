package com.tk.pvtp.store.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tk.pvtp.store.dao.PvtpStoreInfoDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.Transform;

@Service("PvtpStoreInfoService")
public class PvtpStoreInfoService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private PvtpStoreInfoDao pvtpStoreInfoDao;
	
	/**
	 * 查询私有平台商家列表
	 * @param request
	 * @return
	 */
	public GridResult queryPvtpStoreInfolist(HttpServletRequest request) {
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
			int count=pvtpStoreInfoDao.queryPvtpStoreInfoCount(params);
			List<Map<String,Object>> stationedList = pvtpStoreInfoDao.queryPvtpStoreInfoList(params);
			if (stationedList != null && stationedList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(stationedList);
				gr.setTotal(count);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error(e);
		}
		return gr;
	}
}
