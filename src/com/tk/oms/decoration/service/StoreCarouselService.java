package com.tk.oms.decoration.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.decoration.dao.StoreCarouselDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
@Service("StoreCarouselService")
public class StoreCarouselService {
	private int pageIndex = 0;  //默认起始页
    private int pageSize = 20;  //默认一页数量
	
	@Resource
	private StoreCarouselDao storeCarouselDao;
	/**
     * 查询轮播方案数据
     * @param request
     * @return
     */
    public GridResult QueryList(HttpServletRequest request) {
    	GridResult gr = new GridResult();

        try {
        	Map<String, Object> paramMap = new HashMap<String, Object>();
        	String json = HttpUtil.getRequestInputStream(request);
        	if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
				if(pageParamResult!=null){
					return pageParamResult;
				}
	        }

            int count=storeCarouselDao.querycount(paramMap);
            List<Map<String, Object>> list = storeCarouselDao.querylist(paramMap);
            if (list != null && list.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(list);
				gr.setTotal(count);
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
     * 添加新轮播方案
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult add_carousel(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
        	Map<String, Object> paramMap = new HashMap<String, Object>();
    	    String json = HttpUtil.getRequestInputStream(request);
    	    paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            // 添加一个新方案
            int count = storeCarouselDao.insert(paramMap);
    		if(count<1){
    			pr.setState(false);
        		pr.setMessage("数据添加失败!");
        		return pr;
    		}
    		List<Map<String, Object>> filelist = (List<Map<String, Object>>) paramMap.get("file_list");
    		if(filelist!=null && !filelist.isEmpty()){
    			for(Map<String, Object> map:filelist){
    				map.put("carousel_id", paramMap.get("id"));
    			}
    			if(storeCarouselDao.insert_filetable(filelist)>0){
    				pr.setState(true);
	        		pr.setMessage("图片或视频添加成功!");
    			}else{
    				pr.setState(false);
	        		pr.setMessage("图片或视频添加失败!");
    			}
    			
    		}
    		pr.setState(true);
    		pr.setMessage("新增成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
    }
    
    
    /**
     * 删除轮播方案
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult removeInfo(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
        	Map<String, Object> paramMap = new HashMap<String, Object>();
    	    String json = HttpUtil.getRequestInputStream(request);
    	    paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
           
            int delete_count = storeCarouselDao.remove(paramMap);
            if(delete_count>0){
            	pr.setState(true);
        		pr.setMessage("数据删除成功!");
            }else{
            	pr.setState(false);
        		pr.setMessage("数据删除失败!");
        		return pr;
            }
            storeCarouselDao.delete_carousel_files(paramMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
    }
    
    /**
     * 改变轮播方案状态
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult changeState(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
        	Map<String, Object> paramMap = new HashMap<String, Object>();
    	    String json = HttpUtil.getRequestInputStream(request);
    	    paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap==null 
            		|| StringUtils.isEmpty(paramMap.get("carouselid"))
            		|| StringUtils.isEmpty(paramMap.get("state"))
            		){
            	pr.setState(false);
        		pr.setMessage("缺少参数");
        		return pr;
            }
    		String carouselid = paramMap.get("carouselid").toString();
    		Map<String,Object> map = new HashMap<String,Object>();
    		/*
    		 * 全部停用，由于同一时间只允许一个启用的方案存在，所以，启用一个方案时，停用其他所有的方案
    		 * **/
    		if("2".equals(paramMap.get("state").toString())){
    			storeCarouselDao.changestateAll();
    		}
			map = new HashMap<String, Object>() ;
			map.put("id", carouselid);
			map.put("state", paramMap.get("state"));
			storeCarouselDao.changestate(map);
			
    		pr.setState(true);
    		pr.setMessage("操作成功!");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
    }
    
    /**
     * 方案详情
     * @param request
     * @return
     */
    public ProcessResult queryDetailInfo(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
        	Map<String, Object> paramMap = new HashMap<String, Object>();
    	    String json = HttpUtil.getRequestInputStream(request);
    	    paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
           if(paramMap.get("id")==null || paramMap.get("id").equals("")){
        	   pr.setState(false);
        	   pr.setMessage("主要参数缺失");
        	   return pr;
           }
           // 先查询主表信息
           Map<String,Object> data = storeCarouselDao.query_detail(paramMap);
           if(data == null || data.size() ==0){
        	   pr.setMessage("未获取到数据");
        	   pr.setState(false);
        	   return pr;
           }
           List<Map<String,Object>> list = storeCarouselDao.query_files(paramMap);
           //将图片按照所在分组进行分组
           Map<Integer,Map<String,Object>>  fileMap = new TreeMap<Integer,Map<String,Object>>();
           List<Map<String,Object>>  tempList = null;
           Map<String,Object>  tempMap = null;
           if(list!=null && !list.isEmpty()){
        	   for(Map<String,Object> map:list){
        		   int FILE_GROUP = Integer.parseInt(map.get("FILE_GROUP").toString());		//文件分组
        		   int FILE_TYPE = Integer.parseInt(map.get("FILE_TYPE").toString());		//文件类型  1.图片  2.视频
        			   if(fileMap.containsKey(FILE_GROUP)){
        				   tempList = (List<Map<String, Object>>) fileMap.get(FILE_GROUP).get("list");
        				   tempList.add(map);
        			   }else{
        				   tempMap = new HashMap<String, Object>();
        				   tempList = new ArrayList<Map<String,Object>>();
        				   tempList.add(map);
        				   tempMap.put("list", tempList);
        				   tempMap.put("file_type", FILE_TYPE);
        				   tempMap.put("file_group", FILE_GROUP);
        				   fileMap.put(FILE_GROUP, tempMap);
        			   }
        	   }
           }
           List<Map<String,Object>>  filelist = new ArrayList<Map<String,Object>>();
           for(Integer key:fileMap.keySet()){
        	    filelist.add(fileMap.get(key));
           }
           data.put("filelist", filelist);
           pr.setState(true);
           pr.setMessage("数据获取成功");
           pr.setObj(data);
           return pr;
           
        } catch (IOException e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }
    
    /**
     * 编辑方案信息
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult UpdateInfo(HttpServletRequest request)throws Exception {
        ProcessResult pr = new ProcessResult();

        try {
        	Map<String, Object> paramMap = new HashMap<String, Object>();
    	    String json = HttpUtil.getRequestInputStream(request);
    	    paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
    		if(paramMap.isEmpty() || paramMap.get("id") == null || paramMap.get("id").equals("")){
    			pr.setMessage("缺失参数");
    			pr.setState(false);
    			return pr;
    		}
            storeCarouselDao.delete_carousel_files(paramMap);
            //再次添加
            int count = storeCarouselDao.update_carousel(paramMap);
    		if(count<1){
    			pr.setState(false);
        		pr.setMessage("编辑失败!");
        		return pr;
    		}
    		List<Map<String, Object>> filelist = (List<Map<String, Object>>) paramMap.get("file_list");
    		if(filelist!=null && !filelist.isEmpty()){
    			for(Map<String, Object> map:filelist){
    				map.put("carousel_id", paramMap.get("id"));
    			}
    			if(storeCarouselDao.insert_filetable(filelist)>0){
    				pr.setState(true);
	        		pr.setMessage("图片或视频添加成功!");
    			}else{
    				pr.setState(false);
	        		pr.setMessage("图片或视频添加失败!");
    			}
    			
    		}
    		pr.setState(true);
    		pr.setMessage("操作成功!");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
    }
}
