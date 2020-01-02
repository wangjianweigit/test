package com.tk.oms.product.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.product.dao.ProductParamDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;


/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : ProductParamService
 * 商品参数service类
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-4-6
 */
@Service("ProductParamService")
public class ProductParamService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
    private ProductParamDao productParamDao;
	
    /**
     * 查询商品参数列表
     * @param request
     * @return
     */
	@SuppressWarnings("unchecked")
	public GridResult queryProductParamList(HttpServletRequest request) {
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
            //查询商品参数总数
			int total = productParamDao.queryProductParamCount(paramMap);
            //查询商品参数列表
			List<Map<String, Object>> productParamList = productParamDao.queryProductParamList(paramMap);
			if (productParamList != null && productParamList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(productParamList);
				gr.setTotal(total);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
        } catch (IOException e) {
        	gr.setState(false);
        	gr.setMessage(e.getMessage());
        }

        return gr;
	}
	/**
	 * 新增商品参数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult addProductParam(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
	        if(productParamDao.isExist(paramMap)>0){
	        	pr.setState(false);
	            pr.setMessage("参数名称已经存在!");
	            return pr;
	        }else{
	        	//新增商品参数
	           productParamDao.insert(paramMap);
	           pr.setState(true);
	           pr.setMessage("新增成功！");
	        }

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage("新增失败！");
            logger.error("新增商品参数失败，"+e.getMessage());
        }

        return pr;
	}
	/**
	 * 更新商品参数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult editProductParam(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
	        if(productParamDao.isExist(paramMap)>0){
	        	pr.setState(false);
	            pr.setMessage("参数名称已经存在!");
	            return pr;
	        }else{
	        	// 更新商品参数
	        	if(productParamDao.update(paramMap)>0){
	        		pr.setState(true);
		            pr.setMessage("更新成功！");
	        	}else{
	        		pr.setState(false);
		            pr.setMessage("更新失败！");
	        	}
	        	
	        }

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage("更新失败！");
            logger.error("更新商品参数信息失败，"+e.getMessage());
        }

        return pr;
	}
	/**
	 * 删除商品参数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult removeProductParam(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
	        //1.查询参数是否存在关联商品
	        if(productParamDao.queryProductParamRelevance(paramMap) > 0){
	        	pr.setState(false);
	            pr.setMessage("参数存在关联商品，不允许删除");
	            return pr;
	        }else{
	        	//2.删除商品参数
	            if(productParamDao.delete(paramMap)>0){
	            	pr.setState(true);
	                pr.setMessage("删除成功！");
	            }else{
	            	pr.setState(false);
	                pr.setMessage("删除失败！");
	            }
	        }
            

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage("删除失败！");
            logger.error("删除商品参数信息失败，"+e.getMessage());
        }

        return pr;
	}
	/**
	 * 商品参数需要填写的参数信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductParamInfo(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
            //1.查询商品参数信息
	        List<Map<String, Object>> retMap = productParamDao.queryProductParamInfo(paramMap);
            pr.setState(true);
            pr.setMessage("查询成功！");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage("查询失败！");
            logger.error("查询失败，"+e.getMessage());
        }

        return pr;
	}
	/**
	 * 商品参数详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductParamDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
	        if(StringUtils.isEmpty(paramMap.get("id"))){
                pr.setState(false);
                pr.setMessage("参数缺失，参数ID为空");
                return pr;
            }
            //1.商品参数详情
	        Map<String, Object> retMap = productParamDao.queryById(Long.parseLong(paramMap.get("id").toString()));
            pr.setState(true);
            pr.setMessage("查询成功！");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage("查询失败！");
            logger.error("查询失败，"+e.getMessage());
        }

        return pr;
	}
	/**
	 * 排序
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult sortProductParam(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
	        if(paramMap.size() == 0) {
            	pr.setState(false);
            	pr.setMessage("参数缺失");
                return pr;
            }
	        if(StringUtils.isEmpty(paramMap.get("type"))){
		        if(StringUtils.isEmpty(paramMap.get("id1"))||StringUtils.isEmpty(paramMap.get("id2"))){
	                pr.setState(false);
	                pr.setMessage("参数错误，需要两个交换的id（id1、id2）");
	                return pr;
	            }
		        Map<String, Object> t1 = productParamDao.queryById(Long.parseLong(paramMap.get("id1").toString()));
		        Map<String, Object> t2 = productParamDao.queryById(Long.parseLong(paramMap.get("id2").toString()));
		        t1.put("id", paramMap.get("id1"));
	            t1.put("sort_id", t2.get("SORT_ID"));
	            t2.put("id", paramMap.get("id2"));
	            t2.put("sort_id", t1.get("SORT_ID"));
	            if(productParamDao.updateSort(t1)>0&&productParamDao.updateSort(t2)>0){
	                pr.setState(true);
	                pr.setMessage("排序字段修改成功");
	                pr.setObj(null);
	            } else {
	                pr.setState(false);
	                pr.setMessage("排序字段修改失败");
	            }
	        }else{
            	if(StringUtils.isEmpty(paramMap.get("id"))){
                    pr.setState(false);
                    pr.setMessage("参数缺失，参数ID为空");
                    return pr;
                }
            	if(productParamDao.updateSort(paramMap)>0){
            		pr.setState(true);
                    pr.setMessage("排序字段修改成功");
            	}else{
            		pr.setState(false);
                    pr.setMessage("排序字段修改失败");
            	}
            }
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("更新失败："+e.getMessage());
        }

        return pr;
	}

}
