package com.tk.oms.marketing.service;


import java.io.IOException;
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

import com.tk.oms.marketing.dao.RegionProductDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : RegionProductService.java
 * 区域商品
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-4-10
 */
@Service("RegionProductService")
public class RegionProductService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
    private RegionProductDao regionProductDao;
	
	/**
	 * 查询区域关联商品列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryRegionProductList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			PageUtil.handlePageParams(paramMap);
	        }
            //查询区域关联商品总数
			int total = regionProductDao.queryRegionProductCount(paramMap);
            //查询区域关联商品列表
			List<Map<String, Object>> dataList = regionProductDao.queryRegionProductList(paramMap);
			if (dataList != null && dataList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(dataList);
				gr.setTotal(total);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
        } catch (IOException e) {
        	gr.setState(false);
        	gr.setMessage(e.getMessage());
        	logger.error("查询区域关联商品列表失败，"+e.getMessage());
        }

        return gr;
	}
	/**
     * 根据父ID查找子节点信息
     */
    @SuppressWarnings("unchecked")
	public ProcessResult queryDicRegionByParentId(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(!StringUtils.isEmpty(json))
                params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            List<Map<String, Object>> regionList = null;
            if(params.get("level").equals(0)){
                regionList = regionProductDao.queryDicRegionByParentId(params);
            }else{
                regionList = regionProductDao.queryDicRegionByParentIdWidthChildren(params);
            }
            pr.setState(true);
            pr.setMessage("获取省市县区域数据成功");
            pr.setObj(regionList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }
    /**
     * 获取所有省市县数据信息
     */
    public ProcessResult queryDicRegionWithoutCounty() {
        ProcessResult pr = new ProcessResult();
        try {
            List<Map<String,Object>> regionList = regionProductDao.queryDicRegionWithoutCounty();
            if (regionList != null && regionList.size()>0){
                pr.setState(true);
                pr.setMessage("获取省市区域数据成功");
                pr.setObj(regionList);
            } else {
                pr.setState(true);
                pr.setMessage("无省市区域数据");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }
    /**
     * 获取大区及省一级区域数据
     * @param request
     * @return
     */
	@SuppressWarnings("unchecked")
	public ProcessResult queryRegionListFirst(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> params = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(!StringUtils.isEmpty(json))
                params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            List<Map<String, Object>> regionList = regionProductDao.queryDicRegionByParentId(params);
            if (regionList != null && regionList.size()>0){
            	List<Map<String, Object>> newRegionList=new ArrayList<Map<String, Object>>();
            	for(Map<String, Object> dicregion: regionList){
            		 HashMap<String,Object> regionMap=new HashMap<String,Object>();
            		regionMap.put("name", dicregion.get("NAME"));
            		regionMap.put("id", dicregion.get("ID"));
            		params.put("parent_id",dicregion.get("ID"));
            		List<Map<String, Object>> regionNextList = regionProductDao.queryDicRegionByParentId(params);
            		regionMap.put("regionNextData", regionNextList);
            		newRegionList.add(regionMap);
            	}
                pr.setState(true);
                pr.setMessage("获取省区域数据成功");
                pr.setObj(newRegionList);
            } else {
                pr.setState(true);
                pr.setMessage("无省区域数据");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
}
