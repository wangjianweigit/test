package com.tk.oms.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.sys.dao.ExchangeDateDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : ExchangeDateService
 * 调换货时间配置
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018-1-3
 */
@Service("ExchangeDateService")
public class ExchangeDateService {
	@Resource
	private ExchangeDateDao exchangeDateDao;
	/**
	 * 列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryExchangeDateList(HttpServletRequest request) {
		GridResult gr = new GridResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            // 查询调换货时间配置总条数
            int total = exchangeDateDao.queryListForCount(paramMap);
            // 分页查询调换货时间配置列表
            List<Map<String, Object>> list = exchangeDateDao.queryListForPage(paramMap);
            if (list != null && list.size() > 0) {
                gr.setMessage("获取调换货时间配置成功");
                gr.setObj(list);
            } else {
                gr.setState(true);
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
	 * 详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryExchangeDateDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(paramMap.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少id参数");
                return pr;
            }
            Map<String, Object> retMap = exchangeDateDao.queryExchangeDateDetail(paramMap);
            pr.setState(true);
            pr.setMessage("获取调换货时间配置详情成功");
            pr.setObj(retMap);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
        }
        return pr;
	}
	/**
	 * 新增
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult addExchangeDate(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            
            if (exchangeDateDao.isExist(paramMap) > 0) {
                pr.setState(false);
                pr.setMessage("该年份调换货时间配置已存在");
                return pr;
            }
            //新增
            if (exchangeDateDao.insert(paramMap) > 0) {
                pr.setState(true);
                pr.setMessage("新增成功");
            }else{
            	pr.setState(false);
                pr.setMessage("新增失败");
            }
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
        return pr;
	}
	/**
	 * 更新
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult updateExchangeDate(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(paramMap.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少id参数");
                return pr;
            }
            if (exchangeDateDao.isExist(paramMap) > 0) {
                pr.setState(false);
                pr.setMessage("该年份调换货时间配置已存在");
                return pr;
            }
            //更新
            if (exchangeDateDao.update(paramMap) > 0) {
                pr.setState(true);
                pr.setMessage("更新成功");
            }else{
            	pr.setState(false);
                pr.setMessage("更新失败");
            }
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
        return pr;
	}
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult deleteExchangeDate(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(paramMap.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少id参数");
                return pr;
            }
            
            //逻辑删除
            if (exchangeDateDao.deleted(paramMap) > 0) {
                pr.setState(true);
                pr.setMessage("删除成功");
            }else{
            	pr.setState(false);
                pr.setMessage("删除失败");
            }
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
        return pr;
	}
}
