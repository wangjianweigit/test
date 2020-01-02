package com.tk.oms.member.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * Copyright (c), 2019, Tongku
 * FileName : MemberInfoChangeDao
 * 会员资料信息变更接口
 *
 * @author liujialong
 * @version 1.00
 * @date 2019/1/10 10:49
 */
@Repository
public interface MemberInfoChangeDao {

	 /**
     * 查询用户资料变更审批列表
     * @param params
     * @return
     */
    public List<Map<String, Object>> queryMemberInfoApprovalList(Map<String, Object> params);

    /**
     * 查询用户资料变更审批列表记录数
     * @param params
     * @return
     */
    public int queryMemberInfoApprovalCount(Map<String, Object> params);
    
    /**
     * 根据用户ID查询用户变更信息
     * @param params
     * @return
     */
    public Map<String, Object> queryMemberInfoChangeById(Map<String, Object> params);
    
    /**
     * 查询用户资料变更详情(待审和驳回状态)
     * @param params
     * @return
     */
    public Map<String, Object> queryMemberInfoChangeDetail(Map<String, Object> params);
    
    /**
     * 查询用户资料变更详情(已审核状态)
     * @param params
     * @return
     */
    public Map<String, Object> queryMemberInfoChangeApprovalDetail(Map<String, Object> params);
    
    /**
     * 存储原会员资料信息
     * @param params
     * @return
     */
    public int insertOldMemberInfoChange(Map<String, Object> params);
    
    /**
     * 更新会员变更信息状态
     * @param params
     * @return
     */
    public int updateMemberInfoChangeState(Map<String, Object> params);
    
    /**
     * 查询会员业务处理列表
     * @param params
     * @return
     */
    public List<Map<String, Object>> queryMemberInfoBussinessDealList(Map<String, Object> params);

    /**
     * 查询会员业务处理列表记录数
     * @param params
     * @return
     */
    public int queryMemberInfoBussinessDealCount(Map<String, Object> params);
    
    /**
     * 会员业务处理详情
     * @param params
     * @return
     */
    public Map<String, Object> queryBussinessDealDetailByUserId(Map<String, Object> params);
    
    /**
     * 更新会员业务处理状态
     * @param params
     * @return
     */
    public int updateMemberInfoBusinessDealState(Map<String, Object> params);
    
    /**
     * 查询会员反馈管理列表
     * @param params
     * @return
     */
    public List<Map<String, Object>> queryFeedbackManageList(Map<String, Object> params);

    /**
     * 查询会员反馈管理列表记录数
     * @param params
     * @return
     */
    public int queryFeedbackManageCount(Map<String, Object> params);
    
    /**
     * 会员反馈管理详情
     * @param params
     * @return
     */
    public Map<String, Object> queryFeedbackDetail(Map<String, Object> params);
    
    /**
     * 会员反馈处理
     * @param params
     * @return
     */
    public int updateMemberInfoFeedbackDealState(Map<String, Object> params);
    
    /**
     * 更新会员主表信息
     * @param params
     * @return
     */
    public int updateUserInfo(Map<String, Object> params);
    
    
}
