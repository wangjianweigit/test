package com.tk.oms.product.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tk.oms.product.dao.SampleProductDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Service("SampleProductService")
public class SampleProductService {
	@Resource
	private SampleProductDao sampleProductDao;
	/**
	 * 样品订购-商品列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult querySampleProductListForPage(HttpServletRequest request) {
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
			int count = sampleProductDao.querySampleProductCount(paramMap);
			List<Map<String, Object>> list = sampleProductDao.querySampleProductListForPage(paramMap);
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
	 * 新增
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult insertSampleProduct(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if (StringUtils.isEmpty(paramMap.get("id"))) {
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
			}
			if((!StringUtils.isEmpty(paramMap.get("id")))&&paramMap.get("id") instanceof String){
            	paramMap.put("id",paramMap.get("id").toString().split(","));
			}
			//新增
			if (sampleProductDao.insert(paramMap) > 0) {
				pr.setState(true);
				pr.setMessage("添加成功");
			} else {
				pr.setState(false);
				pr.setMessage("添加失败");
			}
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult deleteSampleProduct(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if (StringUtils.isEmpty(paramMap.get("id"))) {
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
			}
			//删除
			if (sampleProductDao.delete(paramMap) > 0) {
				pr.setState(true);
				pr.setMessage("删除成功");
			} else {
				pr.setState(false);
				pr.setMessage("删除失败");
			}
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}

}
