package com.tk.oms.notice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tk.oms.notice.dao.WxArticleDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : WxArticleService.java
 * 微信文章管理 
 * service层
 * 负责业务模块的逻辑应用设计
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-10-25
 */
@Service("WxArticleService")
public class WxArticleService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private WxArticleDao wxArticleDao;
	/**
	 * 查询文章列表
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
			//查询文章数量
			int total = wxArticleDao.queryCount(paramMap);
			//查询文章列表
			List<Map<String, Object>> dataList = wxArticleDao.queryList(paramMap);
			
			if (dataList != null && dataList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功");
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
	 * 查询文章详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
	        if(StringUtils.isEmpty(paramMap.get("id"))){
                pr.setState(false);
                pr.setMessage("文章ID为空");
                return pr;
            }
            //文章详情
	        Map<String, Object> retMap = wxArticleDao.queryDetail(paramMap);
            pr.setState(true);
            pr.setMessage("查询成功");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage("查询失败");
            logger.error("查询失败，"+e.getMessage());
        }

        return pr;
	}
	/**
	 * 新增文章
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult add(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
	        if(wxArticleDao.isExist(paramMap)>0){
	        	pr.setState(false);
	            pr.setMessage("文章标题已经存在");
	            return pr;
	        }else{
	           wxArticleDao.insert(paramMap);
	           pr.setState(true);
	           pr.setMessage("新增成功");
	        }

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage("新增失败");
            logger.error("新增失败，"+e.getMessage());
        }

        return pr;
	}
	/**
	 * 编辑文章
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult edit(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
	        if(StringUtils.isEmpty(paramMap.get("id"))){
                pr.setState(false);
                pr.setMessage("新闻ID为空");
                return pr;
            }
	        
	        if(wxArticleDao.isExist(paramMap)>0){
	        	pr.setState(false);
	            pr.setMessage("文章标题已经存在");
	            return pr;
	        }else{
	           wxArticleDao.update(paramMap);
	           pr.setState(true);
	           pr.setMessage("更新成功");
	        }

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage("更新失败");
            logger.error("更新失败，"+e.getMessage());
        }

        return pr;
	}
	/**
	 * 逻辑删除文章
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult remove(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
	        if(StringUtils.isEmpty(paramMap.get("id"))){
                pr.setState(false);
                pr.setMessage("文章ID为空");
                return pr;
            }
	      paramMap.put("is_delete", "2");
          if(wxArticleDao.update(paramMap)>0){
        	  pr.setState(true);
	          pr.setMessage("删除成功");
          }else{
        	  pr.setState(false);
	          pr.setMessage("删除失败");
          }

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage("删除失败");
            logger.error("删除失败，"+e.getMessage());
        }

        return pr;
	}
	/**
	 * 排序
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult sort(HttpServletRequest request) {
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
		        Map<String, Object> t1 = wxArticleDao.queryById(Long.parseLong(paramMap.get("id1").toString()));
		        Map<String, Object> t2 = wxArticleDao.queryById(Long.parseLong(paramMap.get("id2").toString()));
		        t1.put("id", paramMap.get("id1"));
	            t1.put("sort_id", t2.get("SORT_ID"));
	            t2.put("id", paramMap.get("id2"));
	            t2.put("sort_id", t1.get("SORT_ID"));
	            if(wxArticleDao.updateSort(t1)>0&&wxArticleDao.updateSort(t2)>0){
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
            	if(wxArticleDao.updateSort(paramMap)>0){
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
