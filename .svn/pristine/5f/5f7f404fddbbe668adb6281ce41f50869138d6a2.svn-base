package com.tk.oms.stationed.dao;

import java.util.List;
import java.util.Map;
import com.tk.sys.common.BaseDao;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : StationedSettlementDao.java
 * 
 *
 * @author songwangwen
 * @version 1.00
 * @date 2017-12-14
 */
public interface StationedSettlementDao extends BaseDao<Map<String, Object>> {
	/**
	 * 查询有月结权限的平台会员对于各个入驻商的月结欠款信息
	 * 以及代发费、物流费、仓储费、平台服务费、入驻商服务费信息
	 * @param paramMap 查询条件
	 * @return
	 */
	public List<Map<String, Object>> queryUserMonthlySettlement(Map<String, Object> paramMap);
	/**
	 * 查询有月结权限的门店的对于各个入驻商的月结欠款信息
	 * 以及代发费、物流费、仓储费、平台服务费、入驻商服务费信息
	 * @param paramMap 查询条件
	 * @return
	 */
	public List<Map<String, Object>> queryStoreMonthlySettlement(Map<String, Object> paramMap);
	/**
	 * 入驻商信息列表
	 * @param paramMap 查询条件
	 * @return
	 */
	public List<Map<String, Object>> queryStationedList(Map<String, Object> paramMap);
}
