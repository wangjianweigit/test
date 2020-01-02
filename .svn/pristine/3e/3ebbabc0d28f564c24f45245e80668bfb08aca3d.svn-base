package com.tk.oms.basicinfo.dao;


import java.util.List;
import java.util.Map;

public interface BaseDao<T> {
	
	/**
	 * 插入实体对象
	 * @param t
	 * @return
	 */
	public int insert(Map<String,Object> params);
	
	/**
	 * 插入实体对象
	 * @param t
	 * @return
	 */
	public int batchInsert(Iterable<T> t);
	/**
	 * 插入实体对象
	 * @param t
	 * @return
	 */
	public int batchUpdate(Iterable<T> t);

	/**
	 * 更新实体对象
	 * @param t
	 * @return
	 */
	public int update(Map<String,Object> params);


	/**
	 * 删除实体类
	 * @param t
	 * @return
     */
	public int delete(Map<String,Object> params);

	/**
	 * 根据主建查询对象信息
	 * @param id
	 * @return
	 */
	public T queryById(long id);
	/**
	 * 根据实体查询符合条件的实体列表信息
	 * @param t
	 * @return
	 */
	public List<T> queryListBy(T t);
	/**
	 * 记录是否已经存在
	 * @param t
	 * @return
	 */
	public int isExist(T t);
	
	/**
	 * 分页查询记录列表
	 * @param t
	 * @return
	 */
	public List<T> queryListForPage(Map<String,Object> params);
	/**
	 * 查询记录总条数
	 * @param t
	 * @return
	 */
	public int queryCountForPage(Map<String,Object> params);
}
