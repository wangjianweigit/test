package com.tk.oms.finance.dao;

import java.util.Map;

import com.tk.sys.common.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRecordDao extends BaseDao<Map<String,Object>> {
	/**
	 * 根据平台会员充值记录表创建用户收支记录
	 * @param params
	 * @return
	 */
	int insertUserRecordByCharge(Map<String,Object> params);
	/**
	 * 会员提现驳回，增加充值的收支记录
	 * @param params
	 * @return
	 */
	int insertUserRecordByExtractReject(Map<String,Object> params);
	/**
	 * 入驻商提现驳回后，增加充值的收支记录
	 * @param params
	 * @return
	 */
	int insertStationRecordByExtractReject(Map<String,Object> params);
	/**
	 * 插入 入驻商资金流水记录
	 * @param params
	 * @return
	 */
	int insertStationedCapitalLogs(Map<String,Object> params);

	/**
	 * 会员还款历史欠款金额转到入驻商余额
	 *
	 * @param params
	 * @return
     */
	int insertStationRecordByUserRepayment(Map<String,Object> params);

	/**
	 * 会员定金退还，增加充值的收支记录
	 *
	 * @param params
	 * @return
     */
	int insertUserRecordByEarnest(Map<String,Object> params);
	/**
	 * 会员提现驳回，增加充值的收支记录【联营】
	 * @param params
	 * @return
	 */
	int insertStoreRecordByExtractReject(Map<String, Object> params);
}
