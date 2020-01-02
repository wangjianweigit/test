package com.tk.oms.analysis.dao;

import java.util.List;
import java.util.Map;

import com.tk.sys.common.BaseDao;

public interface MemberVisitDao extends BaseDao<Map<String, Object>>{
	/**
	 * 会员访问统计数量
	 * @param paramMap
	 * @return
	 */
	public int queryMemberVisitCount(Map<String, Object> paramMap);
	/**
	 * 会员访问统计列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryMemberVisitList(Map<String, Object> paramMap);
	/**
	 * 查询用户是否存在标记
	 * @param paramMap
	 * @return
	 */
	public int queryUserMarkByUserName(Map<String, Object> paramMap);
	/**
	 * 添加用户标记
	 * @param paramMap
	 * @return
	 */
	public int insertUserMark(Map<String, Object> paramMap);
	/**
	 * 更新用户标记
	 * @param paramMap
	 * @return
	 */
	public int updateUserMark(Map<String, Object> paramMap);
	/**
	 * 删除用户标记
	 * @param paramMap
	 * @return
	 */
	public int deleteUserMark(Map<String, Object> paramMap);
	/**
	 * 查询用户IP访问记录
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> queryUserIpRecord(Map<String, Object> params);
	/**
	 * 查询用户IP访问记录数量
	 * @param paramMap
	 * @return
	 */
	public int queryUserIpRecordCount(Map<String, Object> paramMap);
	/**
	 * 查询用户订单记录
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> queryUserOrderRecord(Map<String, Object> params);
	/**
	 * 查询用户订单记录数量
	 * @param paramMap
	 * @return
	 */
	public int queryUserOrderRecordCount(Map<String, Object> paramMap);
	/**
	 * 查询用户浏览记录
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> queryUserBrowseRecord(Map<String, Object> params);
	/**
	 * 查询用户浏览记录数量
	 * @param paramMap
	 * @return
	 */
	public int queryUserBrowseRecordCount(Map<String, Object> paramMap);
	
}
