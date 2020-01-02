package com.tk.sys.common;

import java.util.List;
import java.util.Map;

/**
 * @author songwangwen
 * @date 2016/06/06
 */
public interface BaseDao<T> {
	
    /**
     * 插入实体对象
     * @param t
     * @return
     */
    public int insert(T t);

    /**
     * 插入实体对象
     * @param t
     * @return
     */
    public int batchInsert(Iterable<T> t);

    /**
     * 更新实体对象
     * @param t
     * @return
     */
    public int update(T t);

    /**
     * 根据主键删除记录
     * @param id 主键
     * @return
     */
    public int deletedById(long id);
    
    /**
     * 根据实体删除实体记录
     * @param t
     * @return
     */
    public int deleted(T t);

    /**
     * 根据主建查询对象信息
     * @param id
     * @return
     */
    public T queryById(long id);

	/**
	 * 根据实体查询实体列表
	 * 
	 * @param id
	 * @return
	 */
    public List<T> queryListBy(T t);

	/**
	 * 根据实体查询实体列表
	 * 
	 * @param id
	 * @return
	 */
    public List<T> queryListBy(Map<String,Object> params);

	/**
	 * 分页查询实体列表
	 * 
	 * @param id
	 * @return
	 */
    public List<T> queryListForPage(Map<String,Object> params);

	/**
	 * 查询实体总数，用于分页
	 * 
	 * @param id
	 * @return
	 */
    public int queryListForCount(Map<String,Object> params);
    
}