package com.tk.oms.marketing.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface CustomMessageDao {

	/**
	 * 获取自定义消息列表
	 */
	public List<Map<String,Object>> queryCustomMessageList(Map<String,Object> paramMap);
	/**
	 * 获取自定义消息总数
	 */
	public int queryCustomMessageCount(Map<String,Object> paramMap);
	/**
	 * 获取自定义消息详情
	 */
	public Map<String, Object> queryCustomMessageDetail(Map<String,Object> paramMap);
	/**
	 *自定义消息编辑
	 */
	public int updateCustomMessage(Map<String,Object> paramMap);
	/**
	 *自定义消息审批
	 */
	public int updateCustomMessageAuditInfo(Map<String,Object> paramMap);
	/**
	 *获取自定义消息id
	 */
	public int queryCustomMessageId(Map<String, Object> paramMap);
	/**
	 *插入自定义消息
	 */
	public int insertCustomMessage(Map<String,Object> paramMap);
	/**
	 *获取自定义消息用户配置
	 */
	public List<Map<String, Object>> queryCustomMessageUserConfig(Map<String, Object> paramMap);
	/**
	 *获取自定义消息用户列表
	 */
	public List<Map<String, Object>> queryCustomMessageUserList(Map<String, Object> paramMap);
	/**
	 *获取自定义消息用户数量
	 */
	public int queryCustomMessageUserCount(Map<String, Object> paramMap);
	/**
	 *插入自定义消息配置
	 */
	public int insertCustomMessageUserConfig(Map<String, Object> paramMap);
	/**
	 *插入自定义消息用户
	 */
	public int insertCustomMessageUser(Map<String, Object> paramMap);
	/**
	 *获取频道页列表
	 */
	public List<Map<String, Object>> queryCustomMessageChannelPageList(Map<String, Object> paramMap);
	/**
	 *清除自定义消息用户配置
	 */
	public int deleteCustomMessageUserConfig(Map<String, Object> paramMap);
	/**
	 *清除自定义消息用户
	 */
	public int deleteCustomMessageUser(Map<String, Object> paramMap);
	/**
	 *清除自定义消息
	 */
	public int deleteCustomMessage(Map<String, Object> paramMap);
	/**
	 *自定义消息计算用户列表
	 */
	public List<Map<String, Object>> queryCustomMessageCalUserList(Map<String, Object> paramMap);
	/**
	 *自定义消息计算用户数量
	 */
	public int queryCustomMessageCalUserCount(Map<String, Object> paramMap);
	/**
	 *自定义消息推送站点名称
	 */
	public String querySiteNames(Map<String, Object> tempParamMap);
	/**
	 *自定义消息推送用户组名称
	 */
	public String queryUserGroupNames(Map<String, Object> tempParamMap);
	/**
	 *自定义消息推送 区域用户名称
	 */
	public String queryMarketUserNames(Map<String, Object> tempParamMap);
	/**
	 *批量插入自定义消息推送用户
	 */
	public int batchInsertCustomMessageUser(Map<String, Object> paramMap);
	/**
     * 分页查询会员列表记录数
     */
    public int queryMemberListCount(Map param);

    /**
     * 分页查询会员列表
     */
    public List<Map<String, Object>> queryMemberList(Map param);
    /**
     * 自定义页列表
     * @param paramMap
     * @return
     */
	public List<Map<String, Object>> queryCustomPageList(Map<String, Object> paramMap);
}