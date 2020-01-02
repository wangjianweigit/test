package com.tk.oms.finance.dao;

import java.util.Map;

import com.tk.sys.common.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface UserChargeRecordDao extends BaseDao<Map<String,Object>> {
	/**
	 * 修改用户帐户余额，入驻商或者平台会员通用 
	 * @param param
	 */
	void updateUserAccountBalance(Map<String,Object> param);
	/**
	 * 修改用户帐户余额和冻结余额【预付充值】
	 * @param param
	 */
	void updateAccountAndFrozenBalance(Map<String, Object> rechange_param);
	
	/**
	 * 修改用户帐户余额【联营】
	 * @param param
	 */
	void updateStoreAccountBalance(Map<String,Object> param);
}
