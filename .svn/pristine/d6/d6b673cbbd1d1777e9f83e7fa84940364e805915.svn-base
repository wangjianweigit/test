package com.tk.oms.finance.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tk.sys.common.BaseDao;

@Repository
public interface UserExtractDao  extends BaseDao<Map<String,Object>> {
	/**
	 * 对用户提现操作记录进行审批
	 * @param param
	 * @return
	 */
	int approval(Map<String,Object> param);
	/**
	 * 对用户提现操作记录进行打款
	 * @param param
	 * @return
	 */
	int pay(Map<String,Object> param);
}
