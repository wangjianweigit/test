package com.tk.pvtp.stationed.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PvtpStationedDao {
	/**
	 * 查询私有平台商家列表数量
	 * @param params
	 * @return
	 */
	int queryPvtpStationedCount(Map<String, Object> params);
	/**
	 * 查询私有平台商家列表
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> queryPvtpStationedList(Map<String, Object> params);
	/**
	 * 查询入驻商详情
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryStationedDetail(Map<String, Object> paramMap);
	/**
	 * 查询入驻商-私有商家相关服务费详情
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryStationedServiceRateDetail(Map<String, Object> paramMap);
	/**
	 * 更新入驻商-私有商家相关服务费
	 * @param paramMap
	 * @return
	 */
	public int updateStationedServiceRate(Map<String, Object> paramMap);
	/**
	 * 批量更新或 插入入驻商仓储费设置
	 * @param codeParams
	 * @return
	 */
	public int updateOrInsertStorageCharges(List<Map<String, Object>> list);
	/**
	 * 仓储费列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryStorageChargesList(Map<String, Object> paramMap);
	/**
	 * 仓储费列表(过滤鞋类)
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryStorageChargesTypeList(Map<String, Object> paramMap);

	/**
	 * 保证金配置
	 * @param params
	 * @return
	 */
	public int editDepositMoney(Map<String, Object> params);

	/**
	 * 查询保证金
	 * @param paramMap
	 * @return
	 */
	public String queryDepositMoney(Map<String, Object> paramMap);

	/**
	 * 查询入驻商-私有商家账户信息
	 * @param codeParams
	 * @return
	 */
	public Map<String, Object> queryBankAccountInfo(Map<String, Object> codeParams);

	/**
	 * 入驻商-私有商家数据更新
	 * @param paramMap
	 * @return
	 */
	public int updatePvtpStationed(Map<String, Object> paramMap);

	/**
     * 记录用户操作日志
     * @param login_name
     * @return
     */
    public int insertUserOperationLog(Map<String,Object> param);
	/**
	 * 入驻商收支记录数量
	 * @param paramMap
	 * @return
	 */
	public int queryInExpRecordCount(Map<String, Object> paramMap);
	/**
	 * 入驻商收支记录列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryInExpRecordList(Map<String, Object> paramMap);

	/**
	 * 校验私有商家保证金余额校验码
	 * @param codeParams
	 * @return
	 */
	public int queryCheckDepositBalance(Map<String, Object> codeParams);

	/**
	 * 更新私有商家保证金余额信息
	 * @param codeParams
	 * @return
	 */
	public int updateDepositMoneyBalanceInfo(Map<String, Object> codeParams);

	/**
	 * 更新私有商家余额校验码
	 * @param codeParams
	 */
	public void updateUserAccountCheckCode(Map<String, Object> codeParams);

	/**
	 * 校验入驻商余额校验码
	 * @param codeParams
	 * @return
	 */
	public int queryCheckAccountBalance(Map<String, Object> codeParams);

	/**
	 * 更新入驻商余额信息
	 * @param codeParams
	 * @return
	 */
	public int updateAccountBalanceInfo(Map<String, Object> codeParams);


	/**
	 * 修改用户帐户余额
	 *
	 * @param paramMap
	 * @return
	 */
	void updateStationedAccountBalance(Map<String, Object> paramMap);

	/**
	 * 根据私有商用户ID获取账号信息
	 *
	 * @param stationedUserId
	 * @return
	 */
	Map<String, Object> queryBankAccountById(@Param("stationed_user_id") long stationedUserId);

	/**
	 * 统计私有商家的待结算金额
	 *
	 * @param stationedUserId
	 * @return
	 */
	Map<String, Object> queryWaitSettlementAmount(@Param("stationed_user_id") long stationedUserId);
}
