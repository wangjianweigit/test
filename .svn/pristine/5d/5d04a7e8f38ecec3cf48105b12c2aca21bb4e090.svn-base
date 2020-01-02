package com.tk.pvtp.product.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tk.pvtp.product.dao.PvtpProductDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Service("PvtpProductService")
public class PvtpProductService {
	@Resource
	private PvtpProductDao pvtpProductDao;
	
	/**
	 * 获取商品列表
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryProductList(HttpServletRequest request) {
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
			if((!StringUtils.isEmpty(paramMap.get("state")))&&paramMap.get("state") instanceof String){
				paramMap.put("state",(paramMap.get("state")+"").split(","));
			}
			List<Map<String, Object>> list = pvtpProductDao.queryProductList(paramMap);
			int count = pvtpProductDao.queryProductCount(paramMap);
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
	 * 获取商品详情
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			Map<String,Object> resultMap = new HashMap<String,Object>();
			//获取待审批商品详情基本信息
			Map<String,Object> detail = pvtpProductDao.queryProductDetail(paramMap);
			if(detail!=null){
				paramMap.put("id", detail.get("ID"));
				//获取商品详情参数信息
				List<Map<String,Object>> paramsList = pvtpProductDao.queryProductParamsList(paramMap);
				//获取商品sku列表信息
				paramMap.put("product_group", "尺码");
				List<Map<String,Object>> skusList = pvtpProductDao.queryProductSkuList(paramMap);
				//获取商品图片
				List<Map<String,Object>> imgsList = pvtpProductDao.queryProductImagesList(paramMap);
				//获取商品站点显示时间
				List<Map<String,Object>> siteDelayList = pvtpProductDao.queryProductSiteDelayList(paramMap);
				resultMap.put("site_delay_info", siteDelayList);
                List<Map<String, Object>> wrapperList = pvtpProductDao.queryProductWrapperList(paramMap);
                resultMap.put("wrapper_info", wrapperList);
				//获取商品站点显示区域数据
				paramMap.put("district_templet_id", detail.get("DISTRICT_TEMPLET_ID"));
				List<Map<String,Object>> regionAreaList = pvtpProductDao.queryProductRegionAreaList(paramMap);
				resultMap.put("areas_info", regionAreaList);
				
				resultMap.put("base_info", detail);
				resultMap.put("params_info", paramsList);
				resultMap.put("imgs_info", imgsList);
				resultMap.put("skus_info", skusList);
				pr.setMessage("获取数据成功");
				pr.setObj(resultMap);
			} else {
				pr.setMessage("无数据");
			}
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
}
