package com.tk.oms.finance.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.tk.sys.common.BaseDao;

@Repository
public interface StationedWithdrawalDao  extends BaseDao<Map<String,Object>> {
	/**
	 * 入驻商提现申请审批
	 * @param params
	 * @return
	 */
    int approval(Map<String,Object> params);
    /**
     * 入驻商提现申请打款
     * @param params
     * @return
     */
    int pay(Map<String,Object> params);

	/**
	 * 更新入驻商提现银行处理状态
	 *
	 * @param paramsMap
	 * @return
	 */
	int updateWithdrawApplyStateToStationed(Map<String, Object> paramsMap);

	/**
	 * 标记驻商提现为成功状态
	 * @param paramsMap
	 * @return
	 */
	int updateWithdrawApplyMarkSuccessState(Map<String, Object> paramsMap);

	/**
	 * 标记驻商提现为失败状态
	 * @param paramsMap
	 * @return
	 */
	int updateWithdrawApplyMarkFailState(Map<String, Object> paramsMap);

	/**
	 * 根据入驻商ID锁表
	 *
	 * @param userId
	 * @return
	 */
	int lockTable(@Param ("user_id") long userId);

	/**
	 * 更新账户余额
	 *
	 * @param paramsMap
	 */
	void updateAccountBalance(Map<String, Object> paramsMap);

	/**
	 * 增加入驻商资金流水
	 *
	 * @param paramsMap
	 * @return
	 */
	int insertStationedCapitalLogs(Map<String, Object> paramsMap);

	/**
	 * 增加入驻商收支记录
	 *
	 * @param paramsMap
	 * @return
	 */
	int insertUserAccountRecordForStationed(Map<String, Object> paramsMap);

}
