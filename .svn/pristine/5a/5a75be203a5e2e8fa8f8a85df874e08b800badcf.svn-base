package com.tk.oms.finance.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tk.sys.common.BaseDao;

@Repository
public interface RetailExtractDao  extends BaseDao<Map<String,Object>> {
	/**
	 * 对新零售商家提现操作记录进行审批
	 * @param param
	 * @return
	 */
	int approval(Map<String,Object> param);
	/**
	 * 对新零售商家提现操作记录进行打款
	 * @param param
	 * @return
	 */
	int pay(Map<String,Object> param);

	/**
	 * 查询新零售提现申请记录数
	 * @param paramMap
	 * @return
	 */
	int queryListBalanceTransferApplyForCount(Map<String, Object> paramMap);

	/**
	 * 新零售商家提现转余额申请列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryBalanceTransferApplyListForPage(Map<String, Object> paramMap);
}
