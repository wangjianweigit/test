package com.tk.oms.basicinfo.service;

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

import com.tk.oms.basicinfo.dao.ProductPopularKeyWordsDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
@Service("PoductPopularKeyWordsService")
public class ProductPopularKeyWordsService {

	private Log logger = LogFactory.getLog(this.getClass());
    
    @Resource
    private ProductPopularKeyWordsDao productPopularKeyWordsDao;
	/**
     * 获取商品搜索关键字列表数据
     * @param request
     * @return
     */
	public GridResult queryList(HttpServletRequest request) {
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

	            if(paramMap.size() == 0) {
	            	gr.setState(false);
	            	gr.setMessage("参数缺失");
	                return gr;
	            }
	            int productPopularCount=productPopularKeyWordsDao.queryCount(paramMap);
	            List<Map<String,Object>> productPopularKeyWordsList = productPopularKeyWordsDao.queryList(paramMap);
	            if(productPopularKeyWordsList != null&&productPopularKeyWordsList.size()>0) {
	                //保存成功，返回保存内容
	                gr.setState(true);
	                gr.setMessage("获取成功");
	                gr.setObj(productPopularKeyWordsList);
	                gr.setTotal(productPopularCount);
	            }else{
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
	 * 排序
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult sort(HttpServletRequest request)throws Exception {
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
		        Map<String, Object> t1 = productPopularKeyWordsDao.queryById(Long.parseLong(paramMap.get("id1").toString()));
		        Map<String, Object> t2 = productPopularKeyWordsDao.queryById(Long.parseLong(paramMap.get("id2").toString()));
		        t1.put("id", paramMap.get("id1"));
	            t1.put("sortid", t2.get("SORTID"));
	            t2.put("id", paramMap.get("id2"));
	            t2.put("sortid", t1.get("SORTID"));
	            if(productPopularKeyWordsDao.updateSort(t1)>0&&productPopularKeyWordsDao.updateSort(t2)>0){
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
            	if(productPopularKeyWordsDao.updateSort(paramMap)>0){
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
            throw new RuntimeException(e);
        }

        return pr;
	}
	
	/**
	 * 商品搜索关键字添加
	 * @param request
	 * @return
	 */
	@Transactional
	public ProcessResult add(HttpServletRequest request)throws Exception {
		ProcessResult pr = new ProcessResult();

        try {
        	Map<String, Object> paramMap = new HashMap<String, Object>();
    	    String json = HttpUtil.getRequestInputStream(request);
    	    paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            // 
            int  num = productPopularKeyWordsDao.insert(paramMap);

            pr.setState(true);
            pr.setMessage("热门关键字添加成功！");
            pr.setObj(num);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
	}
	/**
	 * 商品搜索关键字编辑更新
	 * @param request
	 * @return
	 */
	@Transactional
	public ProcessResult edit(HttpServletRequest request)throws Exception {
		ProcessResult pr = new ProcessResult();
        try {
        	Map<String, Object> paramMap = new HashMap<String, Object>();
    	    String json = HttpUtil.getRequestInputStream(request);
    	    paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            int  num = productPopularKeyWordsDao.update(paramMap);
            pr.setState(true);
            pr.setMessage("更新成功！");
            pr.setObj(num);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
	}
	/**
	 * 商品搜索关键字删除
	 * @param request
	 * @return
	 */
	@Transactional
	public ProcessResult remove(HttpServletRequest request)throws Exception {
		ProcessResult pr = new ProcessResult();
        try {
        	Map<String, Object> paramMap = new HashMap<String, Object>();
    	    String json = HttpUtil.getRequestInputStream(request);
    	    paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            int  num = productPopularKeyWordsDao.remove(paramMap);
            pr.setState(true);
            pr.setMessage("删除成功！");
            pr.setObj(num);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
	}
	/**
     * 更新热门关键字状态
     *
     * @param request
     * @return
     */
    public ProcessResult updateState(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
    	try {
    		Map<String, Object> Info = new HashMap<String, Object>();
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Info = (Map<String, Object>) Transform.GetPacket(json,
					Map.class);
            if((Integer)Info.get("id") == 0 ||Info.get("state")==null){
                pr.setState(false);
                pr.setMessage("缺少参数id 或者 state");
                return pr;
            }
            productPopularKeyWordsDao.updateState(Info);
            pr.setState(true);
            pr.setMessage("更新热门关键字状态成功");
    	} catch (Exception e) {
    		pr.setState(false);
    		pr.setMessage(e.getMessage());
    	}
    	return pr;
    }
    
    /**
     * 查看热门关键字详情
     *
     * @param request
     * @return
     */
    public ProcessResult detail(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
    	try {
    		Map<String, Object> Info = new HashMap<String, Object>();
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Info = (Map<String, Object>) Transform.GetPacket(json,Map.class);
            if(Integer.parseInt(Info.get("id").toString()) == 0){
                pr.setState(false);
                pr.setMessage("缺少参数id");
                return pr;
            }
            Map<String, Object> detail=productPopularKeyWordsDao.queryById(Long.parseLong(Info.get("id").toString()));
            pr.setState(true);
            pr.setMessage("查询热门关键字详情成功");
            pr.setObj(detail);
    	} catch (Exception e) {
    		pr.setState(false);
    		pr.setMessage(e.getMessage());
    	}
    	return pr;
    }
	
}

