package com.tk.pvtp.finance.dao;

import com.tk.sys.common.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface PvtpExtractDao extends BaseDao<Map<String,Object>> {

	/**
	 * 对用户提现操作记录进行审批
	 * @param param
	 * @return
	 */
	int approval(Map<String, Object> param);
	/**
	 * 对用户提现操作记录进行打款
	 * @param param
	 * @return
	 */
	int pay(Map<String, Object> param);
}
