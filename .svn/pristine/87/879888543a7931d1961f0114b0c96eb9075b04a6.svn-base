package com.tk.store.store.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
@Repository
public interface StoreManageDao {
	
	/**
     * 店铺新增
     * @param paramMap
     * @return
     */
	public int addStore(Map<String, Object> paramMap);
	
	/**
     * 分页查询店铺列表
     * @param paramMap
     * @return
     */
	public List<Map<String, Object>> queryStoreListForPage(Map<String, Object> paramMap);
	
	/**
     * 查询店铺数量
     * @param paramMap
     * @return
     */
	public int queryStoreCount(Map<String, Object> paramMap);
    
    /**
     * 店铺详情
     * @param paramMap
     * @return
     */
	public Map<String, Object> queryStoreDetail(Map<String, Object> paramMap);
    
    /**
     * 店铺编辑
     * @param paramMap
     * @return
     */
	public int editStore(Map<String, Object> paramMap);
	
	/**
     * 店铺启用/停用
     * @param paramMap
     * @return
     */
	public int updateStoreState(Map<String, Object> paramMap);
	
	/**
     * 店铺佣金比例分配
     * @param paramMap
     * @return
     */
	public int storeCommissionRateSet(Map<String, Object> paramMap);
	
	/**
     * 支付服务费设置
     * @param paramMap
     * @return
     */
	public int storePayServiceRateSet(Map<String, Object> paramMap);
	
	/**
     * 店铺平台登入设置
     * @param paramMap
     * @return
     */
	public int storePlatformLoginSet(Map<String, Object> paramMap);
	
	/**
     * 分页查询店铺审批列表
     * @param paramMap
     * @return
     */
	public List<Map<String, Object>> queryStoreApprovalList(Map<String, Object> paramMap);
	
	/**
     * 查询店铺审批列表数量
     * @param paramMap
     * @return
     */
	public int queryStoreApprovalCount(Map<String, Object> paramMap);
	
	/**
     * 店铺会员认证审批列表
     * @param paramMap
     * @return
     */
	public List<Map<String, Object>> queryStoreMemberAuthApprovalList(Map<String, Object> paramMap);
	
	/**
     * 店铺会员认证审批列表总数
     * @param paramMap
     * @return
     */
	public int queryStoreMemberAuthApprovalCount(Map<String, Object> paramMap);
    
    /**
     * 店铺会员认证审批详情
     * @param paramMap
     * @return
     */
	public Map<String, Object> queryStoreMemberAuthApprovalDetail(Map<String, Object> paramMap);
	
	/**
     * 店铺会员密码重置
     * @param paramMap
     * @return
     */
	public int updateStoreMemberPwd(Map<String, Object> paramMap);
	
	/**
     * 查询当前业务经理的合作商
     * @param paramMap
     * @return
     */
	public List<Map<String, Object>> queryPartnerUserIds(Map<String, Object> paramMap);
	
	/**
     * 查询商家经营门店列表总数
     * @param paramMap
     * @return
     */
	public int queryStoreUserManageCount(Map<String, Object> paramMap);
	
	/**
     * 查询商家经营门店列表
     * @param paramMap
     * @return
     */
	public List<Map<String, Object>> queryStoreUserManageList(Map<String, Object> paramMap);
	
	/**
     * 查询商家库列表总数
     * @param paramMap
     * @return
     */
	public int queryStoreMemberLibraryCount(Map<String, Object> paramMap);
	
	/**
     * 查询商家库列表
     * @param paramMap
     * @return
     */
	public List<Map<String, Object>> queryStoreMemberLibraryList(Map<String, Object> paramMap);
	
	/**
     * 商家经营店铺新增
     * @param paramMap
     * @return
     */
	public int storeUserManageAdd(Map<String, Object> paramMap);
	
	/**
     * 商家经营店铺编辑
     * @param paramMap
     * @return
     */
	public int storeUserManageEdit(Map<String, Object> paramMap);
	
	/**
     * 查询商家店铺详情
     * @param paramMap
     * @return
     */
	public Map<String, Object> queryStoreUserManageDetail(Map<String, Object> paramMap);
	
	/**
     * 商家经营店铺审批
     * @param paramMap
     * @return
     */
	public int storeUserManageApproval(Map<String, Object> paramMap);
	
	/**
     * 查询经营店铺相关信息
     * @param paramMap
     * @return
     */
	public Map<String, Object> queryStoreUserManageInfo(Map<String, Object> paramMap);
	
	/**
     * 查询店铺名称数量
     * @param paramMap
     * @return
     */
	public int queryStoreNameCount(Map<String, Object> paramMap);
	
	/**
     * 更新本地php店铺id
     * @param paramMap
     * @return
     */
	public int updateStoreAgentStoreId(Map<String, Object> paramMap);
	/**
	 * 门店下拉框
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> storeSelect(Map<String, Object> paramMap);
	
}
