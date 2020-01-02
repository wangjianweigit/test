package com.tk.oms.analysis.service;

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

import com.tk.oms.analysis.dao.MemberVisitDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : MemberVisitService
 * 会员访问统计
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-5-13
 */
@Service("MemberVisitService")
public class MemberVisitService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private MemberVisitDao memberVisitDao;
	
	/**
	 * 会员访问统计
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryMemberVisitList(HttpServletRequest request) {
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
			
			//会员访问统计数量
			int total = memberVisitDao.queryMemberVisitCount(paramMap);
			//会员访问统计列表
			List<Map<String, Object>> dataList = memberVisitDao.queryMemberVisitList(paramMap);
			if (dataList != null && dataList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
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
	 * 更新用户标记
	 * @param request
	 * @return
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public ProcessResult editUserMark(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(paramMap.get("user_name"))){
                pr.setState(false);
                pr.setMessage("缺少参数user_name");
                return pr;
            }
            //查询用户是否存在标记
            if(memberVisitDao.queryUserMarkByUserName(paramMap)==0){
            	//2.新增
            	if(memberVisitDao.insertUserMark(paramMap) > 0){
                    pr.setState(true);
                    pr.setMessage("标记成功");
                }else{
                    pr.setState(false);
                    pr.setMessage("标记失败");
                }
            }else{
            	//3.更新
	            if(memberVisitDao.updateUserMark(paramMap) > 0){
	                pr.setState(true);
	                pr.setMessage("标记成功");
	            }else{
	                pr.setState(false);
	                pr.setMessage("标记失败");
	            }
            }
	            

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("标记失败："+e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        return pr;
	}
	/**
	 * 删除用户标记
	 * @param request
	 * @return
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public ProcessResult removeUserMark(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(paramMap.get("user_name"))){
                pr.setState(false);
                pr.setMessage("缺少参数user_name");
                return pr;
            }
            //1.删除
            if(memberVisitDao.deleteUserMark(paramMap) > 0){
                pr.setState(true);
                pr.setMessage("取消标记成功");
            }else{
                pr.setState(false);
                pr.setMessage("取消标记失败");
            }

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("取消标记失败："+e.getMessage());
            throw new RuntimeException(e.getMessage());
            
        }

        return pr;
	}
	/**
	 * 查询用户IP访问记录
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryUserIpRecord(HttpServletRequest request) {
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
            if(StringUtils.isEmpty(paramMap.get("user_name"))){
            	gr.setState(false);
            	gr.setMessage("缺少参数user_name");
                return gr;
            }
            
            int total = memberVisitDao.queryUserIpRecordCount(paramMap);
            
            List<Map<String, Object>> dataList = memberVisitDao.queryUserIpRecord(paramMap);
            
            if (dataList != null && dataList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询IP记录成功");
				gr.setObj(dataList);
				gr.setTotal(total);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.error("查询IP记录失败："+e.getMessage());
        }

        return gr;
	}
	/**
	 * 查询用户订单记录
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryUserOrderRecord(HttpServletRequest request) {
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
            if(StringUtils.isEmpty(paramMap.get("user_name"))){
            	gr.setState(false);
            	gr.setMessage("缺少参数user_name");
                return gr;
            }
            if(StringUtils.isEmpty(paramMap.get("type"))){
            	gr.setState(false);
            	gr.setMessage("缺少参数type");
                return gr;
            }
            int total = memberVisitDao.queryUserOrderRecordCount(paramMap);
            
            List<Map<String, Object>> dataList = memberVisitDao.queryUserOrderRecord(paramMap);

            if (dataList != null && dataList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询订单记录成功");
				gr.setObj(dataList);
				gr.setTotal(total);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.error("查询订单记录失败："+e.getMessage());
        }

        return gr;
	}
	/**
	 * 查询用户浏览记录
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryUserBrowseRecord(HttpServletRequest request) {
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
            if(StringUtils.isEmpty(paramMap.get("user_name"))){
            	gr.setState(false);
            	gr.setMessage("缺少参数user_name");
                return gr;
            }
            
            int total = memberVisitDao.queryUserBrowseRecordCount(paramMap);
            
            List<Map<String, Object>> dataList = memberVisitDao.queryUserBrowseRecord(paramMap);

            if (dataList != null && dataList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询浏览记录成功");
				gr.setObj(dataList);
				gr.setTotal(total);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.error("查询浏览记录失败："+e.getMessage());
        }

        return gr;
	}

}
