package com.tk.oms.basicinfo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tk.oms.basicinfo.dao.ArticleDao;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : ArticleService
 * 单页管理
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-5-31
 */
@Service("ArticleService")
public class ArticleService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private ArticleDao articleDao;
	
	/**
	 * 单页列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryCustomList(HttpServletRequest request) {
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
	        //自定义单页列表
	        List<Map<String, Object>> customList = articleDao.queryCustomList(paramMap);
	        
	        pr.setState(true);
	        pr.setMessage("查询成功");
	        pr.setObj(customList);
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("查询失败："+e.getMessage());
        }

		return pr;
	}
	
	/**
	 * 编辑单页
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult editCustom(HttpServletRequest request) {
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
	        //单页名称是否重复
	        if(articleDao.queryCustomByName(paramMap)>0){
	        	pr.setState(false);
	        	pr.setMessage("单页名称已存在,请修改后提交");
	        	return pr;
	        }
	        if(StringUtils.isEmpty(paramMap.get("id"))){
	        	//新增
	        	if(articleDao.insertCustom(paramMap)>0){
	        		pr.setState(true);
	        		pr.setMessage("新增成功");
	        	}else{
	        		pr.setState(false);
	        		pr.setMessage("新增失败");
	        	}
	        }else{
	        	//编辑
	        	if(articleDao.updateCustom(paramMap)>0){
	        		pr.setState(true);
	        		pr.setMessage("更新成功");
	        	}else{
	        		pr.setState(false);
	        		pr.setMessage("更新失败");
	        	}
	        }
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("操作失败："+e.getMessage());
        }

		return pr;
	}
	
	/**
	 * 删除单页
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult removeCustom(HttpServletRequest request) {
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
	        //删除
	        if(articleDao.deleteCustom(paramMap)>0){
	        	pr.setState(true);
	        	pr.setMessage("删除成功");
	        }else{
	        	pr.setState(false);
	        	pr.setMessage("删除失败");
	        }
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("删除失败："+e.getMessage());
        }

		return pr;
	}
	
	/**
	 * 单页排序
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult editCustomSort(HttpServletRequest request) {
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
	        if(StringUtils.isEmpty(paramMap.get("id1"))||StringUtils.isEmpty(paramMap.get("id2"))){
                pr.setState(false);
                pr.setMessage("参数错误，需要两个交换的id（id1、id2）");
                return pr;
            }
	        Map<String,Object> t1 = articleDao.queryCustomById(Long.parseLong(paramMap.get("id1").toString()));
            Map<String,Object> t2 = articleDao.queryCustomById(Long.parseLong(paramMap.get("id2").toString()));
            t1.put("id", paramMap.get("id1"));
            t1.put("sort_id",t2.get("SORT_ID"));
            t2.put("id", paramMap.get("id2"));
            t2.put("sort_id",t1.get("SORT_ID"));
            if(articleDao.updateCustom(t1)>0&&articleDao.updateCustom(t2)>0){
                pr.setState(true);
                pr.setMessage("排序字段修改成功");
                pr.setObj(null);
            } else {
                pr.setState(false);
                pr.setMessage("排序字段修改失败");
            }
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("操作失败："+e.getMessage());
        }

		return pr;
	}
	
	/**
	 * 单页启/禁用
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult editCustomState(HttpServletRequest request) {
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
	        //编辑
        	if(articleDao.updateCustom(paramMap)>0){
        		pr.setState(true);
        		pr.setMessage("更新成功");
        	}else{
        		pr.setState(false);
        		pr.setMessage("更新失败");
        	}
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("更新失败："+e.getMessage());
        }

		return pr;
	}
	
	/**
	 * 栏目列表
	 * @param request
	 * @return
	 */
	public ProcessResult queryProgramaList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
	       
	        //栏目列表
	        List<Map<String, Object>> programaList = articleDao.queryProgramaList();
	        pr.setState(true);
	        pr.setMessage("查询成功");
	        pr.setObj(programaList);
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("查询失败："+e.getMessage());
        }

		return pr;
	}
	
	/**
	 * 编辑栏目
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult editPrograma(HttpServletRequest request) {
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
	        //栏目名称是否重复
	        if(articleDao.queryProgramaByName(paramMap)>0){
	        	pr.setState(false);
	        	pr.setMessage("栏目名称已存在,请修改后提交");
	        	return pr;
	        }
	        if(StringUtils.isEmpty(paramMap.get("id"))){
	        	//新增
	        	if(articleDao.insertPrograma(paramMap)>0){
	        		pr.setState(true);
	        		pr.setMessage("新增成功");
	        		pr.setObj(paramMap);
	        	}else{
	        		pr.setState(false);
	        		pr.setMessage("新增失败");
	        	}
	        }else{
	        	//编辑
	        	if(articleDao.updatePrograma(paramMap)>0){
	        		pr.setState(true);
	        		pr.setMessage("更新成功");
	        	}else{
	        		pr.setState(false);
	        		pr.setMessage("更新失败");
	        	}
	        }
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("操作失败："+e.getMessage());
        }

		return pr;
	}
	
	/**
	 * 删除栏目
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult removePrograma(HttpServletRequest request) {
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
	        //是否存在关联单页
	        if(articleDao.queryCustomByArticleInfoId(paramMap)>0){
	        	pr.setState(false);
	        	pr.setMessage("请先删除关联的单页信息");
	        	return pr;
	        }
	        //删除
        	if(articleDao.deletePrograma(paramMap)>0){
        		pr.setState(true);
        		pr.setMessage("删除成功");
        	}else{
        		pr.setState(false);
        		pr.setMessage("删除失败");
        	}
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("删除失败："+e.getMessage());
        }

		return pr;
	}
	/**
	 * 栏目启/禁用
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult editProgramaState(HttpServletRequest request) {
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
	        if(StringUtils.isEmpty(paramMap.get("id"))){
                pr.setState(false);
                pr.setMessage("参数错误，栏目ID为空");
                return pr;
            }
	        //编辑
        	if(articleDao.updatePrograma(paramMap)>0){
        		pr.setState(true);
        		pr.setMessage("更新成功");
        	}else{
        		pr.setState(false);
        		pr.setMessage("更新失败");
        	}
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("操作失败："+e.getMessage());
        }

		return pr;
	}
	
	/**
	 * 栏目排序
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult editProgramaSort(HttpServletRequest request) {
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
	        if(StringUtils.isEmpty(paramMap.get("id1"))||StringUtils.isEmpty(paramMap.get("id2"))){
                pr.setState(false);
                pr.setMessage("参数错误，需要两个交换的id（id1、id2）");
                return pr;
            }
	        Map<String,Object> t1 = articleDao.queryProgramaById(Long.parseLong(paramMap.get("id1").toString()));
            Map<String,Object> t2 = articleDao.queryProgramaById(Long.parseLong(paramMap.get("id2").toString()));
            t1.put("id", paramMap.get("id1"));
            t1.put("sort_id",t2.get("SORT_ID"));
            t2.put("id", paramMap.get("id2"));
            t2.put("sort_id",t1.get("SORT_ID"));
            if(articleDao.updatePrograma(t1)>0&&articleDao.updatePrograma(t2)>0){
                pr.setState(true);
                pr.setMessage("排序字段修改成功");
                pr.setObj(null);
            } else {
                pr.setState(false);
                pr.setMessage("排序字段修改失败");
            }
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("操作失败："+e.getMessage());
        }

		return pr;
	}
	
	/**
	 * 栏目下拉框
	 * @param request
	 * @return
	 */
	public ProcessResult queryProgramaCombobox(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
	       
	        //栏目下拉框
	        List<Map<String, Object>> programaList = articleDao.queryProgramaCombobox();
	        pr.setState(true);
	        pr.setMessage("查询成功");
	        pr.setObj(programaList);
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("查询失败："+e.getMessage());
        }

		return pr;
	}
	
	/**
	 * 固定单页列表
	 * @param request
	 * @return
	 */
	public ProcessResult queryFixedList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
	       
	        //固定单页列表
	        List<Map<String, Object>> fixedList = articleDao.queryFixedList();
	        
	        pr.setState(true);
	        pr.setMessage("查询成功");
	        pr.setObj(fixedList);
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("查询失败："+e.getMessage());
        }

		return pr;
	}
	
	/**
	 * 编辑固定单页
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult editFixed(HttpServletRequest request) {
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
	        //单页名称是否重复
	        if(articleDao.queryCustomByName(paramMap)>0){
	        	pr.setState(false);
	        	pr.setMessage("单页名称已存在,请修改后提交");
	        	return pr;
	        }
	        //编辑
        	if(articleDao.updateCustom(paramMap)>0){
        		pr.setState(true);
        		pr.setMessage("更新成功");
        	}else{
        		pr.setState(false);
        		pr.setMessage("更新失败");
        	}
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("操作失败："+e.getMessage());
        }

		return pr;
	}
	
	/**
	 * 查询单页详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryArticleDetail(HttpServletRequest request) {
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
	        Map<String, Object> retMap = articleDao.queryArticleDetail(paramMap);
	        
	        pr.setState(true);
	        pr.setMessage("查询成功");
	        pr.setObj(retMap);
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("查询失败："+e.getMessage());
        }

		return pr;
	}
	
	/**
	 * 固定单页启/禁用
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult editFixedState(HttpServletRequest request) {
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
	        //编辑
        	if(articleDao.updateCustom(paramMap)>0){
        		pr.setState(true);
        		pr.setMessage("更新成功");
        	}else{
        		pr.setState(false);
        		pr.setMessage("更新失败");
        	}
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("更新失败："+e.getMessage());
        }

		return pr;
	}

}
