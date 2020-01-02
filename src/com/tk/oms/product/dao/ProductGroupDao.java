package com.tk.oms.product.dao;

import java.util.List;
import java.util.Map;

import com.tk.sys.common.BaseDao;

public interface ProductGroupDao extends BaseDao<Map<String, Object>>{
	/**
	 * 查询商品分组列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryProductGroupList(Map<String, Object> paramMap);
	/**
	 * 查询商品分组层级信息
	 * @return
	 */
	public List<Map<String, Object>> queryProductGroupAll();
	/**
	 * 删除商品分组
	 * @param paramMap
	 * @return
	 */
	public int delete(Map<String, Object> paramMap);
	/**
	 * 根据分组名称查询分组信息是否存在
	 * @param map
	 * @return
	 */
	public int isExist(Map<String, Object> map);
	/**
	 * 批量更新商品分组
	 * @param updateList
	 * @return
	 */
	public int batchUpdate(List<Map<String, Object>> updateList);
	/**
	 * 根据父节点ID查询所有的子节点
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryChildrenByParentId(Map<String, Object> paramMap);
	/**
	 * 更新是否展开
	 * @param map
	 * @return
	 */
	public int updateIsDisplay(Map<String, Object> map);

}
