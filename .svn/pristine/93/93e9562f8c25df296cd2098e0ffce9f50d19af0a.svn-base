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

import com.tk.oms.notice.dao.NewsDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Service("NewsService")
public class NewsService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private NewsDao newsDao;
	
	/**
	 * 查询新闻列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryNewsList(HttpServletRequest request) {
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
			//查询新闻数量
			int total = newsDao.queryNewsCount(paramMap);
			//查询新闻列表
			List<Map<String, Object>> dataList = newsDao.queryNewsList(paramMap);
			
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
	 * 查询新闻详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryNewsDetail(HttpServletRequest request) {
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
            //新闻详情
	        Map<String, Object> retMap = newsDao.queryNewsDetail(paramMap);
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
	 * 新增新闻
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult addNews(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
	        if(newsDao.isExist(paramMap)>0){
	        	pr.setState(false);
	            pr.setMessage("新闻标题已经存在");
	            return pr;
	        }else{
	           newsDao.insertNews(paramMap);
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
	 * 编辑新闻
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult editNews(HttpServletRequest request) {
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
	        
	        if(newsDao.isExist(paramMap)>0){
	        	pr.setState(false);
	            pr.setMessage("新闻标题已经存在");
	            return pr;
	        }else{
	           newsDao.updateNews(paramMap);
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
	 * 删除新闻
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult removeNews(HttpServletRequest request) {
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
	      paramMap.put("is_delete", "2");
          if(newsDao.updateNews(paramMap)>0){
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
	public ProcessResult sortNews(HttpServletRequest request) {
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
		        Map<String, Object> t1 = newsDao.queryById(Long.parseLong(paramMap.get("id1").toString()));
		        Map<String, Object> t2 = newsDao.queryById(Long.parseLong(paramMap.get("id2").toString()));
		        t1.put("id", paramMap.get("id1"));
	            t1.put("sort_id", t2.get("SORT_ID"));
	            t2.put("id", paramMap.get("id2"));
	            t2.put("sort_id", t1.get("SORT_ID"));
	            if(newsDao.updateSort(t1)>0&&newsDao.updateSort(t2)>0){
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
            	if(newsDao.updateSort(paramMap)>0){
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
	/**
	 * 启/停用
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult editNewsState(HttpServletRequest request) {
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
	        
           if(newsDao.updateNews(paramMap)>0){
        	   pr.setState(true);
               pr.setMessage("更新成功");
           }else{
        	   pr.setState(false);
               pr.setMessage("更新失败");
           }
           
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage("更新失败");
        }

        return pr;
	}
	

}
