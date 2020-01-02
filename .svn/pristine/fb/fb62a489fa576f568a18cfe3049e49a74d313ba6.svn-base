package com.tk.oms.basicinfo.service;

import com.tk.oms.basicinfo.dao.StandardLogisticsDao;
import com.tk.sys.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2018, TongKu
 * FileName : StandardLogisticsService
 * 标准物流公司业务层
 *
 * @author zhenghui
 * @version 1.00
 * @date 2018-06-12
 */
@Service("StandardLogisticsService")
public class StandardLogisticsService {

    @Resource
    private StandardLogisticsDao standardLogisticsDao;

    /**
     * 分页查询标准物流公司列表
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public GridResult queryStandardLogisticsListForPage(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)){
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
			Map<String, Object> paramsMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            GridResult pageParamResult = PageUtil.handlePageParams(paramsMap);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            int count = this.standardLogisticsDao.countStandardLogisticsForPage(paramsMap);
            List<Map<String, Object>> list = null;
            if (count > 0) {
                list = this.standardLogisticsDao.listStandardLogisticsForPage(paramsMap);
                gr.setMessage("查询标准物流公司成功");
            } else {
                gr.setMessage("无标准物流公司数据");
            }
            gr.setState(true);
            gr.setTotal(count);
            gr.setObj(list);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
    }

    /**
     * 查询标准物流公司列表
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	public ProcessResult queryStandardLogisticsList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
        	Map<String, Object> paramsMap = new HashMap<String, Object>();
        	 String json = HttpUtil.getRequestInputStream(request);
             if (!StringUtils.isEmpty(json)){
                 paramsMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
             }
            List<Map<String, Object>> list = this.standardLogisticsDao.listStandardLogistics(paramsMap);
            pr.setState(true);
            pr.setMessage("查询物流公司列表成功");
            pr.setObj(list);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 查询物流公司详情
     * @param request
     * @return
     */
    public ProcessResult queryStandardLogisticsDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramsMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(paramsMap.get("logistics_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[logistics_id]");
                return pr;
            }
            long logisticsId = Long.parseLong(paramsMap.get("logistics_id").toString());
            Map<String, Object> standardLogistics = this.standardLogisticsDao.getStandardLogisticsById(logisticsId);
            if (standardLogistics == null) {
                pr.setState(false);
                pr.setMessage("物流公司不存在或已删除");
                return pr;
            }
            pr.setState(true);
            pr.setMessage("查询物流公司详情成功");
            pr.setObj(standardLogistics);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 新增物流公司
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public ProcessResult addStandardLogistics(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramsMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(paramsMap.get("logistics_name"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[logistics_name]");
                return pr;
            }
            if (StringUtils.isEmpty(paramsMap.get("logistics_code"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[logistics_code]");
                return pr;
            }
            if(StringUtils.isEmpty(paramsMap.get("shipping_method_id"))){
                pr.setMessage("缺少参数[shipping_method_id]");
                return pr;
            }
            if(this.standardLogisticsDao.countStandardLogisticsByName(paramsMap) > 0){
                pr.setState(false);
                pr.setMessage("物流公司名称已存在");
                return pr;
            }
            if(this.standardLogisticsDao.countStandardLogisticsByCode(paramsMap) > 0){
                pr.setState(false);
                pr.setMessage("物流公司代码已存在");
                return pr;
            }
            int count = this.standardLogisticsDao.insertStandardLogistics(paramsMap);
            if (count <= 0) {
                throw new RuntimeException("新增物流公司失败");
            }
            pr.setState(true);
            pr.setMessage("新增物流公司成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }

    /**
     * 编辑物流公司
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public ProcessResult editStandardLogistics(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramsMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(paramsMap.get("logistics_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[logistics_id]");
                return pr;
            }
            if(StringUtils.isEmpty(paramsMap.get("shipping_method_id"))){
                pr.setMessage("缺少参数[shipping_method_id]");
                return pr;
            }
            long logisticsId = Long.parseLong(paramsMap.get("logistics_id").toString());
            Map<String, Object> standardLogistics = this.standardLogisticsDao.getStandardLogisticsById(logisticsId);
            if (standardLogistics == null) {
                pr.setState(false);
                pr.setMessage("物流公司不存在或已删除");
                return pr;
            }
            if(this.standardLogisticsDao.countStandardLogisticsByName(paramsMap) > 0){
                pr.setState(false);
                pr.setMessage("物流公司名称已存在");
                return pr;
            }
            if(this.standardLogisticsDao.countStandardLogisticsByCode(paramsMap) > 0){
                pr.setState(false);
                pr.setMessage("物流公司代码已存在");
                return pr;
            }
            int count = this.standardLogisticsDao.updateStandardLogistics(paramsMap);
            if (count <= 0) {
                throw new RuntimeException("编辑物流公司失败");
            }
            pr.setState(true);
            pr.setMessage("编辑物流公司成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }

    /**
     * 编辑物流公司状态
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public ProcessResult editStandardLogisticsState(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramsMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(paramsMap.get("logistics_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[logistics_id]");
                return pr;
            }
            if (StringUtils.isEmpty(paramsMap.get("state"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[state]");
                return pr;
            }
            long logisticsId = Long.parseLong(paramsMap.get("logistics_id").toString());
            Map<String, Object> standardLogistics = this.standardLogisticsDao.getStandardLogisticsById(logisticsId);
            if (standardLogistics == null) {
                pr.setState(false);
                pr.setMessage("物流公司不存在或已删除");
                return pr;
            }
            int count = this.standardLogisticsDao.updateStandardLogistics(paramsMap);
            if (count <= 0) {
                throw new RuntimeException("编辑物流公司状态失败");
            }
            pr.setState(true);
            pr.setMessage("编辑物流公司状态成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }

    /**
     * 删除物流公司
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public ProcessResult removeStandardLogistics(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramsMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(paramsMap.get("logistics_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[logistics_id]");
                return pr;
            }
            long logisticsId = Long.parseLong(paramsMap.get("logistics_id").toString());
            Map<String, Object> standardLogistics = this.standardLogisticsDao.getStandardLogisticsById(logisticsId);
            if (standardLogistics == null) {
                pr.setState(false);
                pr.setMessage("物流公司不存在或已删除");
                return pr;
            }
            if(this.standardLogisticsDao.countLogisticsByStandardId(logisticsId) > 0){
                pr.setState(false);
                pr.setMessage("物流公司已关联，不允许删除");
                return pr;
            }
            int count = this.standardLogisticsDao.deleteStandardLogistics(paramsMap);
            if (count <= 0) {
                throw new RuntimeException("删除物流公司失败");
            }
            pr.setState(true);
            pr.setMessage("删除物流公司成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }
    
    /**
	 * 物流公司列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult logisticsCompanyList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
	        gr.setState(true);
	        gr.setMessage("查询成功");
	        gr.setObj(standardLogisticsDao.queryLogisticsCompanyList(paramMap));
        } catch (Exception e) {
        	gr.setState(false);
        	gr.setMessage(e.getMessage());
        }

        return gr;
	}
	
	/**
	 * 物流公司排序
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult standardLogisticsSort(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        Map<String,Object> paramMap = new HashMap<String,Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(!StringUtils.isEmpty(json))
                //参数实体
            	paramMap = (Map<String,Object>) Transform.GetPacket(json, Map.class);
            
            if(StringUtils.isEmpty(paramMap.get("type"))){
            	if(StringUtils.isEmpty(paramMap.get("id1"))||StringUtils.isEmpty(paramMap.get("id2"))){
                    pr.setState(false);
                    pr.setMessage("参数错误，需要两个交换的id（id1、id2）");
                    return pr;
                }
	            Map<String,Object> t1 = standardLogisticsDao.queryById(Long.parseLong(paramMap.get("id1").toString()));
	            Map<String,Object> t2 = standardLogisticsDao.queryById(Long.parseLong(paramMap.get("id2").toString()));
	            t1.put("id", paramMap.get("id1"));
	            t1.put("sort_id",t2.get("SORT_ID"));
	            t2.put("id", paramMap.get("id2"));
	            t2.put("sort_id",t1.get("SORT_ID"));
	            if(standardLogisticsDao.updateSort(t1)>0&&standardLogisticsDao.updateSort(t2)>0){
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
            	if(standardLogisticsDao.updateSort(paramMap)>0){
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
        }
        return pr;
	}
}
