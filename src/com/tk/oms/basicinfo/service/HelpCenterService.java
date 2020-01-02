package com.tk.oms.basicinfo.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.basicinfo.dao.HelpCenterDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : HelpCenterService.java
 * 新零售新闻中心
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-8-7
 */
@Service("HelpCenterService")
public class HelpCenterService {

	@Resource
	private HelpCenterDao helpCenterDao;

	/**
	 * 分页查询分类列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryHelpCenterList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				gr.setState(false);
				gr.setMessage("缺少参数");
				return gr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
			if (pageParamResult != null) {
				return pageParamResult;
			}
			if (StringUtils.isEmpty(paramMap.get("parent_id"))) {
				gr.setState(false);
				gr.setMessage("缺少参数parent_id");
				return gr;
			}
			//获取分类信息
			int total = helpCenterDao.queryHelpCenterCount(paramMap);
			List<Map<String, Object>> list = helpCenterDao.queryHelpCenterList(paramMap);
			if (list != null && list.size() > 0) {
				gr.setMessage("获取分类列表成功");
				gr.setObj(list);
			} else {
				gr.setMessage("无数据");
			}
			gr.setState(true);
			gr.setTotal(total);
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return gr;
	}

	/**
     * 查询大类
     * @param request
     * @return
     */
	public ProcessResult queryLargeClass(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			List<Map<String, Object>> map = helpCenterDao.queryLargeClass();
			pr.setState(true);
			pr.setMessage("获取成功");
			pr.setObj(map);
		} catch (Exception e) {
			pr.setState(false);
            pr.setMessage(e.getMessage());
		}
		return pr;
	}

    /**
     * 新增子类
     * @param request
     * @return
     */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult addHelpCenter(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			if(StringUtils.isEmpty(paramMap.get("name"))){
				pr.setState(false);
				pr.setMessage("缺少参数name");
				return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("parent_id"))){
				pr.setState(false);
				pr.setMessage("缺少参数parent_id");
				return pr;
			}
			if(helpCenterDao.addHelpCenter(paramMap) > 0){
				pr.setState(true);
				pr.setMessage("新增成功");
			}
		} catch (Exception e) {
			pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
		}
		return pr;
	}

	/**
	 * 更新分类
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult updateHelpCenter(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			if(StringUtils.isEmpty(paramMap.get("id"))){
				pr.setState(false);
				pr.setMessage("缺少参数id");
				return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("name"))){
				pr.setState(false);
				pr.setMessage("缺少参数name");
				return pr;
			}
			if(helpCenterDao.updateHelpCenter(paramMap) > 0){
				pr.setState(true);
				pr.setMessage("更新成功");
			}
		} catch (Exception e) {
			pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
		}
		return pr;
	}

	/**
	 * 删除分类信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult deleteHelpCenter(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			if(StringUtils.isEmpty(paramMap.get("id"))){
				pr.setState(false);
				pr.setMessage("缺少参数id");
				return pr;
			}
			//查询是否存在关联数据
			if(helpCenterDao.queryHelpCenterInfoCount(paramMap) > 0){
				pr.setState(false);
				pr.setMessage("请先删除其关联的子类信息，删除失败！");
				return pr;
			}
			if(helpCenterDao.deleteHelpCenter(paramMap) > 0){
				pr.setState(true);
				pr.setMessage("删除成功");
			}
			
		} catch (Exception e) {
			pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
		}
		return pr;
	}

	/**
	 * 查询视频列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryVideoList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				gr.setState(false);
				gr.setMessage("缺少参数");
				return gr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
			if (pageParamResult != null) {
				return pageParamResult;
			}
			//获取分类信息
			int count = helpCenterDao.queryVideoListCount(paramMap);
			List<Map<String, Object>> list = helpCenterDao.queryVideoList(paramMap);
			if (list != null && list.size() > 0) {
				gr.setMessage("获取视频列表成功");
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
	 * 新增子类信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult addHelpCenterInfo(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			if(StringUtils.isEmpty(paramMap.get("help_center_id"))){
				pr.setState(false);
				pr.setMessage("缺少参数help_center_id");
				return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("headline"))){
				pr.setState(false);
				pr.setMessage("缺少参数headline");
				return pr;
			}
			if(helpCenterDao.addHelpCenterInfo(paramMap) > 0){
				pr.setState(true);
				pr.setMessage("新增成功");
			}
		} catch (Exception e) {
			pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
		}
		return pr;
	}

	/**
	 * 更新子类信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult updateHelpCenterInfo(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			if(StringUtils.isEmpty(paramMap.get("id"))){
				pr.setState(false);
				pr.setMessage("缺少参数id");
				return pr;
			}
			if(helpCenterDao.updateHelpCenterInfo(paramMap) > 0){
				pr.setState(true);
				pr.setMessage("更新成功");
			}
		} catch (Exception e) {
			pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
		}
		return pr;
	}

	/**
	 * 删除子类信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult deleteHelpCenterInfo(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			if(StringUtils.isEmpty(paramMap.get("id"))){
				pr.setState(false);
				pr.setMessage("缺少参数id");
				return pr;
			}
			if(helpCenterDao.deleteHelpCenterInfo(paramMap)>0){
				pr.setState(true);
				pr.setMessage("删除成功");
			}else{
				pr.setState(false);
				pr.setMessage("删除失败");
			}
			
		} catch (Exception e) {
			pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
		}
		return pr;
	}

	/**
	 * 查询知识库列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryRepositoryList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				gr.setState(false);
				gr.setMessage("缺少参数");
				return gr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
			if (pageParamResult != null) {
				return pageParamResult;
			}
			//获取知识库信息
			List<Map<String, Object>> list = helpCenterDao.queryRepositoryList(paramMap);
			int count = helpCenterDao.queryRepositoryListCount(paramMap);
			if (list != null && list.size() > 0) {
				gr.setMessage("获取知识库列表成功");
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
	 * 查询分类详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryLargeClassifyById(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			if(StringUtils.isEmpty(paramMap.get("id"))){
				pr.setState(false);
				pr.setMessage("缺少参数id");
				return pr;
			}
            //获取分类信息
			Map<String,Object> list = helpCenterDao.queryLargeClassifyById(paramMap);
			if(list != null){
				pr.setMessage("获取视频和知识库列表成功");
				pr.setObj(list);
			}else {
				pr.setMessage("无数据");
			}
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}


	/**
	 * 查询子类明细
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryHelpCenterDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			 if(StringUtils.isEmpty(json)) {
				 pr.setState(false);
	             pr.setMessage("缺少参数");
	             return pr;
			 }
			Map<String, Object> map = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			if(StringUtils.isEmpty(map.get("id"))){
				pr.setState(false);
				pr.setMessage("参数缺失id");
				return pr;
			}
            //查询子类信息
			Map<String,Object> retMap = helpCenterDao.queryHelpCenterDetail(map);
			if(retMap != null){
				pr.setState(true);
				pr.setMessage("获取数据成功");
				pr.setObj(retMap);
			}else {
				pr.setState(false);
				pr.setMessage("无数据");
			}
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}

	/**
	 * 排序
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult helpCenterSort(HttpServletRequest request) throws IOException {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			if(StringUtils.isEmpty(paramMap.get("fromId"))){
				pr.setState(false);
				pr.setMessage("参数缺失fromId");
				return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("toId"))){
				pr.setState(false);
				pr.setMessage("参数缺失toId");
				return pr;
			}
			if(this.helpCenterDao.updateHelpCenterSort(paramMap) > 0){
				pr.setState(true);
				pr.setMessage("操作成功！");
			}else{
				pr.setState(false);
				pr.setMessage("操作失败！");
			}

		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
    	return pr;
	}

	/**
	 * 子类排序
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult helpCenterInfoSort(HttpServletRequest request) throws IOException {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			if (StringUtils.isEmpty(paramMap.get("fromId"))) {
				pr.setState(false);
				pr.setMessage("参数缺失fromId");
				return pr;
			}
			if (StringUtils.isEmpty(paramMap.get("toId"))) {
				pr.setState(false);
				pr.setMessage("参数缺失toId");
				return pr;
			}
			if (this.helpCenterDao.updateHelpCenterInfoSort(paramMap) > 0) {
				pr.setState(true);
				pr.setMessage("操作成功！");
			} else {
				pr.setState(false);
				pr.setMessage("操作失败！");
			}
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}

	/**
	 * 是否启用
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult updateIsDelete(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			if (StringUtils.isEmpty(paramMap.get("id"))) {
				pr.setState(false);
				pr.setMessage("参数缺失id");
				return pr;
			}
			if (StringUtils.isEmpty(paramMap.get("state"))) {
				pr.setState(false);
				pr.setMessage("参数缺失state");
				return pr;
			}
			helpCenterDao.updateHelpCenterState(paramMap);
			pr.setState(true);
			pr.setMessage("更新成功");
		} catch (Exception e) {
			pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	/**
	 * 分类下拉框
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryHelpCenterCombobox(HttpServletRequest request) {
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
			//分类下拉框
			List<Map<String, Object>> dataList= helpCenterDao.queryHelpCenterCombobox(paramMap);
			pr.setState(true);
			pr.setMessage("查询成功");
			pr.setObj(dataList);
		} catch (Exception e) {
			pr.setState(false);
            pr.setMessage(e.getMessage());
		}
		return pr;
	}
}
