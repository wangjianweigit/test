package com.tk.store.finance.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tk.sys.common.BaseDao;

@Repository
public interface AccountInfoDao extends BaseDao<Map<String, Object>> {
	/**
	 * 获取个人基本信息
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryAccountInfoBasic(Map<String, Object> paramMap);
	/**
	 * 获取银行卡列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryBankCardList(Map<String, Object> paramMap);
	/**
	 * 获取认证信息
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryAuthenticationInfo(Map<String, Object> paramMap);
	/**
	 * 解绑
	 * @param paramMap
	 * @return
	 */
	int unbind(Map<String, Object> paramMap);
	/**
	 * 查询银行卡信息
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryBankCardDetail(Map<String, Object> paramMap);
	/**
	 * 银行子账户
	 * @param user_id
	 * @return
	 */
	Map<String, Object> queryBankAccountByUserId(int user_id);
	/**
	 * 判断是否已绑定银行卡
	 * @param paramMap
	 * @return
	 */
	int queryUserBankCardCount(Map<String, Object> paramMap);
	/**
	 * 获取银行信息
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryBankForNote(Map<String, Object> paramMap);
	/**
	 * 添加银行卡
	 * @param paramMap
	 * @return
	 */
	int insert(Map<String, Object> paramMap);
	/**
	 * 通过bank_clscode获取银行信息
	 * @param string
	 * @return
	 */
	Map<String, Object> queryBankByClscode(String string);
	
	/**
	 * 增加认证信息
	 * @param paramMap
	 * @return
	 */
	int insertAuthenticationInfo(Map<String, Object> paramMap);
	/**
	 * 更新认证信息
	 * @param paramMap
	 * @return
	 */
	int updateAuthenticationInfo(Map<String, Object> paramMap);
	/**
	 * 判断当前身份证号是否重复
	 * @param paramMap
	 * @return
	 */
	int checkUserManageCardIdCount(Map<String, Object> paramMap);

}
