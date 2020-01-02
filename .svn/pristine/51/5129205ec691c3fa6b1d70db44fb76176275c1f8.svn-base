package com.tk.oms.basicinfo.dao;

import java.util.List;
import java.util.Map;

import com.tk.sys.common.BaseDao;

public interface FreightTemplateDao extends BaseDao<Map<String, Object>>{
	/**
	 * 运费模板列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryFreightTemplateList(Map<String, Object> paramMap);
	/**
	 * 运费模板数量
	 * @param paramMap
	 * @return
	 */
	public int queryFreightTemplateCount(Map<String, Object> paramMap);
	/**
	 * 运费模板详情列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryFreightTemplateDetailList(Map<String, Object> paramMap);
	/**
	 * 删除运费模板
	 * @param paramMap
	 */
	public int delete(Map<String, Object> paramMap);
	/**
	 * 删除运费模板详情
	 * @param paramMap
	 */
	public void deleteDetail(Map<String, Object> paramMap);
	/**
	 * 查询运费模板是否有商品在使用
	 * @param paramMap
	 * @return
	 */
	public int queryFreightTemplateProduct(Map<String, Object> paramMap);
	/**
	 * 新增运费模板详情
	 * @param dataList
	 * @return
	 */
	public int insertDetail(List<Map<String, Object>> dataList);
	/**
	 * 更新不等于模板ID的运费模板默认状态
	 * @param paramMap
	 * @return
	 */
	public int updateByDefault(Map<String, Object> paramMap);
	/**
	 * 仓库列表
	 * @return
	 */
	public List<Map<String, Object>> queryWarehouseList();
	/**
	 * 模板名称是否重复 
	 * @param paramMap
	 * @return
	 */
	public int queryNameIsExist(Map<String, Object> paramMap);
	
}
