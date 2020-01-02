package com.tk.oms.finance.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tk.sys.common.BaseDao;

@Repository
public interface SysUserExtractDao  extends BaseDao<Map<String,Object>> {
	/**
	 * 对用户提现操作记录进行审批
	 * @param param
	 * @return
	 */
	int approval(Map<String,Object> param);
	/**
	 * 系统用户银行卡信息
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> querySysUserBankInfo(Map<String, Object> paramMap);
	/**
	 * 添加系统用户提现记录
	 * @param sysUserExtractMap
	 * @return
	 */
	int insertSysUserExtractRecord(Map<String, Object> sysUserExtractMap);
	/**
	 * 查询系统用户账号信息
	 * @param params
	 * @return
	 */
	Map<String, Object> querySysBankAccountInfo(Map<String, Object> params);
	/**
	 * 获取用户key
	 * @param codeParams
	 * @return
	 */
	String getUserKey(Map<String, Object> codeParams);
	/**
	 * 获取余额校验码
	 * @param codeParams
	 * @return
	 */
	String getCheck_Code(Map<String, Object> codeParams);
	/**
	 * 生成个人收支记录
	 * @param codeParams
	 * @return
	 */
	int insertSysAccountRecord(Map<String, Object> codeParams);
	/**
	 * 更新账户余额
	 * @param codeParams
	 */
	int updateSysBankAccountBalance(Map<String, Object> codeParams);
	/**
	 * 更新用户账户key
	 * @param codeParams
	 */
	int updateSysUserCacheKey(Map<String, Object> codeParams);
}
