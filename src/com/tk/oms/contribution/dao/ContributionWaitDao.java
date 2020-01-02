package com.tk.oms.contribution.dao;

import java.util.List;
import java.util.Map;

import com.tk.sys.common.BaseDao;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : ContributionWaitDao
 * 待缴款订单
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-4-19
 */
public interface ContributionWaitDao extends BaseDao<Map<String, Object>>{
	/**
	 * 查询待缴款订单列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryContributionWaitList(Map<String, Object> paramMap);
	/**
	 * 查询待缴款订单总数
	 * @param paramMap
	 * @return
	 */
	public int queryContributionWaitCount(Map<String, Object> paramMap);
	/**
	 * 统计缴款金额
	 * @param ids
	 * @return
	 */
	public Map<String, Object> sumTotalMoney(String[] ids);

	/**
	 * 获取充值现金缴款详细信息
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryCashPaymentDetailForRecharge(Map<String, Object> paramMap);
	/**
	 * 生成缴款单详情信息
	 * @param waitMap
	 * @return
	 */
	public int insertContributionOrderDetail(Map<String, Object> waitMap);
	/**
	 * 检测用户是否有查看所有的代缴款订单的权限
	 * @param parseLong
	 * @return
	 */
	public int checkUserRoleNode(long parseLong);
	/**
	 * 代客户充值验款通过后，产生一条代缴款单记录
	 * @param id  代客户充值记录ID
	 * @return
	 */
	public int insertWaitByUserCharge(long id);

}
