package com.tk.oms.finance.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface SysUserAuthApprovalDao {
	
	/**
	 * 用户认证信息列表总数
	 * @param paramMap
	 * @return
	 */
	int querySysUserAuthApprovalCount(Map<String, Object> paramMap);
	/**
	 * 分页查询用户认证信息审批列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> querySysUserAuthApprovalList(Map<String, Object> paramMap);
	/**
	 * 用户认证信息详情
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> querySysUserAuthApprovalDetail(Map<String, Object> paramMap);
	/**
	 * 查询系统用户认证信息
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> querySysUserCertificationApprovalDetail(Map<String, Object> paramMap);
	/**
	 * 会员账户表更新见证宝相关信息
	 *
	 * @param params
	 * @return
     */
	public int updateBankAccountInfo(Map<String, Object> params);
	/**
	 * 更新银行卡会员资料信息状态
	 *
	 * @param params
	 * @return
     */
	public int updateBankCardUserInfo(Map<String, Object> params);

}
