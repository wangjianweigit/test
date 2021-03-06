package com.tk.oms.basicinfo.dao;

import java.util.List;
import java.util.Map;

import com.tk.sys.common.BaseDao;

public interface LogisticsCompanyDao extends BaseDao<Map<String, Object>>{
	/**
	 * 查询物流公司列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryLogisticsCompanyList(Map<String, Object> paramMap);
	/**
	 * 查询物流公司总数量
	 * @param paramMap
	 * @return
	 */
	public int queryLogisticsCompanyCount(Map<String, Object> paramMap);
	/**
	 * 查询物流公司详情
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryLogisticsCompanyDetail(Map<String, Object> paramMap);
	/**
	 * 物流公司代码是否重复
	 * @param paramMap
	 * @return
	 */
	public int queryLogisticsByCode(Map<String, Object> paramMap);
	/**
	 * 删除物流公司信息
	 * @param paramMap
	 * @return
	 */
	public int delete(Map<String, Object> paramMap);
	/**
	 * 删除物流公司为用户可用记录
	 * @param paramMap
	 * @return
	 */
	public int deleteLogisticsForUser(Map<String, Object> paramMap);
	/**
	 * 查看物流公司关联用户数量
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryLogisticsForUser(Map<String, Object> paramMap);
	/**
	 * 分类返回物流公司
	 * @param company
	 * @return
	 */
	public List<Map<String, Object>> queryLogisticsList(Map<String, Object> paramMap);
	
	/**
	 * 查询物流公司列表（下拉框）
	 * @param company
	 * @return
	 */
	public List<Map<String, Object>> queryLogisticsCompany(Map<String, Object> paramMap);
	/**
	 * 更新排序
	 * @param paramMap
	 * @return
	 */
	public int updateSort(Map<String, Object> paramMap);
	/**
	 * 物流公司名称是否重复
	 * @param paramMap
	 * @return
	 */
	public int queryLogisticsByName(Map<String, Object> paramMap);
	/**
	 * 查询配送方式下拉列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryDistributionMethodOption(Map<String, Object> paramMap);
	/**
	 * 批量新增平台会员可用配送方式与标准物流的关联关系表数据
	 * @param paramMap
	 * @return
	 */
	public int batchStandardLogisticsComRef(Map<String, Object> paramMap);
	/**
	 * 批量删除平台会员可用配送方式与标准物流的关联关系表数据
	 * @param paramMap
	 * @return
	 */
	public int delStandardLogisticsComRef(Map<String, Object> paramMap);
}
