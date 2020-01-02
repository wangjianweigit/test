package com.tk.oms.basicinfo.service;

import com.tk.oms.basicinfo.dao.ClassifyDao;
import com.tk.sys.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("ClassifyService")
public class ClassifyService {

	@Resource
	private ClassifyDao classifyDao;

	/**
	 * 分页查询分类列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryClassifyList(HttpServletRequest request) {
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
			int total = classifyDao.queryClassifyCount(paramMap);
			List<Map<String, Object>> list = classifyDao.queryClassifyList(paramMap);
			if (list != null && list.size() > 0) {
				gr.setMessage("获取分类列表数据成功");
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
			List<Map<String, Object>> map = classifyDao.queryLargeClass();
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
	public ProcessResult addClassify(HttpServletRequest request) {
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
			if(classifyDao.addClassify(paramMap) > 0){
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
	public ProcessResult updateClassify(HttpServletRequest request) {
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
			if(classifyDao.updateClassify(paramMap) > 0){
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
	public ProcessResult deleteClassify(HttpServletRequest request) {
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
			if(classifyDao.selectClassifyInfoCount(paramMap) > 0){
				pr.setState(false);
				pr.setMessage("请先删除其关联的子类信息，删除失败！");
				return pr;
			}
			if(classifyDao.deleteClassify(paramMap) > 0){
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
			int count = classifyDao.queryVideoListCount(paramMap);
			List<Map<String, Object>> list = classifyDao.queryVideoList(paramMap);
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
	public ProcessResult addClassifyInfo(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			if(StringUtils.isEmpty(paramMap.get("classify_id"))){
				pr.setState(false);
				pr.setMessage("缺少参数classify_id");
				return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("headline"))){
				pr.setState(false);
				pr.setMessage("缺少参数headline");
				return pr;
			}
			if(classifyDao.addClassifyInfo(paramMap) > 0){
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
	public ProcessResult updateClassifyInfo(HttpServletRequest request) {
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
			if(classifyDao.updateClassifyInfo(paramMap) > 0){
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
	public ProcessResult deleteClassifyInfo(HttpServletRequest request) {
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
			if(classifyDao.deleteClassifyInfo(paramMap)>0){
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
			List<Map<String, Object>> list = classifyDao.queryRepositoryList(paramMap);
			int count = classifyDao.queryRepositoryListCount(paramMap);
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
			Map<String,Object> list = classifyDao.queryLargeClassifyById(paramMap);
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
	public ProcessResult queryClassifyDetail(HttpServletRequest request) {
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
			Map<String,Object> retMap = classifyDao.queryClassifyDetail(map);
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
	public ProcessResult classifySort(HttpServletRequest request) throws IOException {
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
			if(this.classifyDao.updateClassifySort(paramMap) > 0){
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
	public ProcessResult classifyInfoSort(HttpServletRequest request) throws IOException {
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
			if (this.classifyDao.updateClassifyInfoSort(paramMap) > 0) {
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
			classifyDao.updateClassifyState(paramMap);
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
	public ProcessResult queryClassifyCombobox(HttpServletRequest request) {
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
			List<Map<String, Object>> dataList= classifyDao.queryClassifyCombobox(paramMap);
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
