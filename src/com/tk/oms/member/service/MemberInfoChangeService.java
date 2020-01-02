package com.tk.oms.member.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.member.dao.MemberBonusPointsDao;
import com.tk.oms.member.dao.MemberInfoChangeDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpClientUtil;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * Copyright (c), 2019, Tongku
 * FileName : MemberInfoChangeService
 * 会员资料变更类
 *
 * @author liujialong
 * @version 1.00
 * @date 2019/1/10 11:14
 */
@Service
public class MemberInfoChangeService {
	
	@Resource
    private MemberInfoChangeDao memberInfoChangeDao;
	
	/**
     * 用户资料变更审批列表
     * @param request
     * @return
     */
    public GridResult queryApprovalList(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            //分页参数
            GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            if((!StringUtils.isEmpty(paramMap.get("approval_state")))&&paramMap.get("approval_state") instanceof String){
            	paramMap.put("approval_state",(paramMap.get("approval_state")+"").split(","));
			}
            //记录数据
            List<Map<String, Object>>  list = memberInfoChangeDao.queryMemberInfoApprovalList(paramMap);
            int count = memberInfoChangeDao.queryMemberInfoApprovalCount(paramMap);
            if (list != null) {
                gr.setState(true);
                gr.setMessage("获取成功");
                gr.setObj(list);
                gr.setTotal(count);
            } else {
                gr.setState(true);
                gr.setMessage("无数据");
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
    }
    
    /**
	 * 用户资料变更详情
	 * @param request
	 * @return
	 */
	public ProcessResult memberInfoDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			Map<String,Object> params = null;
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(params==null||StringUtils.isEmpty(params.get("id"))){
				pr.setState(false);
				pr.setMessage("参数错误，缺少ID");
				return pr;
			}
			Map<String,Object> info = memberInfoChangeDao.queryMemberInfoChangeById(params);
			if(null==info){
				pr.setObj("");
				pr.setState(false);
				pr.setMessage("未找到当前会员资料变更信息");
				return pr;
			}
			Map<String,Object> detail=new HashMap<String,Object>();
			if(Integer.parseInt(info.get("APPROVAL_STATE").toString())==1||Integer.parseInt(info.get("APPROVAL_STATE").toString())==3){
				 detail = memberInfoChangeDao.queryMemberInfoChangeDetail(params);
			}
			if(Integer.parseInt(info.get("APPROVAL_STATE").toString())==2){
				 detail = memberInfoChangeDao.queryMemberInfoChangeApprovalDetail(params);
			}
			pr.setObj(detail);
			pr.setState(true);
			pr.setMessage("获取会员信息成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 用户资料变更审批
	 * @param request
	 * @return
	 */
	@Transactional
	public ProcessResult memberInfoApproval(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String,Object> paramMap=new HashMap<String,Object>();
		List<String>change_list = new ArrayList<String>();
		try {
			Map<String,Object> params = null;
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(params==null||StringUtils.isEmpty(params.get("id"))||StringUtils.isEmpty(params.get("approval_state"))){
				pr.setState(false);
				pr.setMessage("参数错误，缺少必要参数");
				return pr;
			}
			if(!("2".equals(params.get("approval_state")) || "3".equals(params.get("approval_state")))){
				pr.setState(false);
				pr.setMessage("审核状态有误");
				return pr;
			}
			if((!StringUtils.isEmpty(params.get("id")))&&params.get("id") instanceof String){
				String[] arrId=(params.get("id")+"").split(",");
				change_list=Arrays.asList(arrId);
				params.put("id",arrId);
			}
			if("2".equals(params.get("approval_state"))){
				//审核通过操作先存储会员原资料信息
				if(memberInfoChangeDao.insertOldMemberInfoChange(params)<=0){
					pr.setState(false);
					throw new RuntimeException("存储会员原资料信息失败");
				}
				for(String id:change_list){
					paramMap.put("id", id);
					Map<String,Object> changeInfo=memberInfoChangeDao.queryMemberInfoChangeById(paramMap);
					//更新会员表信息
					if(memberInfoChangeDao.updateUserInfo(changeInfo)<=0){
						pr.setState(false);
						throw new RuntimeException("更新会员信息失败");
					}
				}
			}
			//更新会员变更信息状态
			if(memberInfoChangeDao.updateMemberInfoChangeState(params)<=0){
				pr.setState(false);
				throw new RuntimeException("审批失败");
			}
			pr.setState(true);
			pr.setMessage("审批成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	/**
     * 会员业务处理列表
     * @param request
     * @return
     */
    public GridResult queryBussinessDealList(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            //分页参数
            GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            if((!StringUtils.isEmpty(paramMap.get("state")))&&paramMap.get("state") instanceof String){
            	paramMap.put("state",(paramMap.get("state")+"").split(","));
			}
            //记录数据
            List<Map<String, Object>>  list = memberInfoChangeDao.queryMemberInfoBussinessDealList(paramMap);
            int count = memberInfoChangeDao.queryMemberInfoBussinessDealCount(paramMap);
            if (list != null) {
                gr.setState(true);
                gr.setMessage("获取成功");
                gr.setObj(list);
                gr.setTotal(count);
            } else {
                gr.setState(true);
                gr.setMessage("无数据");
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
    }
    
    /**
   	 * 会员业务处理详情
   	 * @param request
   	 * @return
   	 */
   	public ProcessResult queryBussinessDealDetail(HttpServletRequest request) {
   		ProcessResult pr = new ProcessResult();
   		try {
   			Map<String,Object> params = null;
   			String json = HttpUtil.getRequestInputStream(request);
   			if(!StringUtils.isEmpty(json))
   				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
   			if(params==null||StringUtils.isEmpty(params.get("id"))){
   				pr.setState(false);
   				pr.setMessage("参数错误，缺少用户id");
   				return pr;
   			}
   			Map<String,Object> detail = memberInfoChangeDao.queryBussinessDealDetailByUserId(params);
   			if(null==detail){
   				pr.setState(false);
   				pr.setMessage("未找到当前用户业务信息");
   				return pr;
   			}
   			pr.setObj(detail);
   			pr.setState(true);
   			pr.setMessage("获取会员业务处理详情成功");
   		} catch (Exception e) {
   			pr.setState(false);
   			pr.setMessage(e.getMessage());
   		}
   		return pr;
   	}
   	
   	/**
	 * 会员业务处理
	 * @param request
	 * @return
	 */
	@Transactional
	public ProcessResult memberInfoBussinessDeal(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			Map<String,Object> params = null;
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(params==null||StringUtils.isEmpty(params.get("id"))||StringUtils.isEmpty(params.get("state"))){
				pr.setState(false);
				pr.setMessage("参数错误，缺少必要参数");
				return pr;
			}
			if(!("2".equals(params.get("state")) || "3".equals(params.get("state")))){
				pr.setState(false);
				pr.setMessage("处理状态有误");
				return pr;
			}
			//更新会员业务处理状态
			if(memberInfoChangeDao.updateMemberInfoBusinessDealState(params)<=0){
				pr.setState(false);
				throw new RuntimeException("会员业务处理失败");
			}
			pr.setState(true);
			pr.setMessage("会员业务处理成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	/**
     * 会员反馈管理列表
     * @param request
     * @return
     */
    public GridResult queryFeedbackManageList(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            //分页参数
            GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            if((!StringUtils.isEmpty(paramMap.get("state")))&&paramMap.get("state") instanceof String){
            	paramMap.put("state",(paramMap.get("state")+"").split(","));
			}
            //记录数据
            List<Map<String, Object>>  list = memberInfoChangeDao.queryFeedbackManageList(paramMap);
            int count = memberInfoChangeDao.queryFeedbackManageCount(paramMap);
            if (list != null) {
                gr.setState(true);
                gr.setMessage("获取成功");
                gr.setObj(list);
                gr.setTotal(count);
            } else {
                gr.setState(true);
                gr.setMessage("无数据");
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
    }
    
    /**
   	 * 会员反馈管理详情
   	 * @param request
   	 * @return
   	 */
   	public ProcessResult queryFeedbackDetail(HttpServletRequest request) {
   		ProcessResult pr = new ProcessResult();
   		try {
   			Map<String,Object> params = null;
   			String json = HttpUtil.getRequestInputStream(request);
   			if(!StringUtils.isEmpty(json))
   				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
   			if(params==null||StringUtils.isEmpty(params.get("id"))){
   				pr.setState(false);
   				pr.setMessage("参数错误，缺少id");
   				return pr;
   			}
   			Map<String,Object> detail = memberInfoChangeDao.queryFeedbackDetail(params);
   			if(null==detail){
   				pr.setState(false);
   				pr.setMessage("未找到当前用户反馈管理信息");
   				return pr;
   			}
   			pr.setObj(detail);
   			pr.setState(true);
   			pr.setMessage("获取会员反馈管理详情成功");
   		} catch (Exception e) {
   			pr.setState(false);
   			pr.setMessage(e.getMessage());
   		}
   		return pr;
   	}
   	
   	/**
	 * 会员反馈处理
	 * @param request
	 * @return
	 */
	@Transactional
	public ProcessResult memberFeedbackDeal(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			Map<String,Object> params = null;
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(params==null||StringUtils.isEmpty(params.get("id"))){
				pr.setState(false);
				pr.setMessage("参数错误，缺少必要参数");
				return pr;
			}
			//更新会员业务处理状态
			if(memberInfoChangeDao.updateMemberInfoFeedbackDealState(params)<=0){
				pr.setState(false);
				throw new RuntimeException("会员反馈处理失败");
			}
			pr.setState(true);
			pr.setMessage("会员反馈处理成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}

}
