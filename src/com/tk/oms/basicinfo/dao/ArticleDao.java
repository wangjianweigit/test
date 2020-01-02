package com.tk.oms.basicinfo.dao;

import java.util.List;
import java.util.Map;

public interface ArticleDao {
	/**
	 * 栏目列表
	 * @return
	 */
	public List<Map<String, Object>> queryProgramaList();
	/**
	 * 自定义单页列表
	 * @param paramMap 
	 * @return
	 */
	public List<Map<String, Object>> queryCustomList(Map<String, Object> paramMap);
	/**
	 * 单页名称是否重复
	 * @param paramMap
	 * @return
	 */
	public int queryCustomByName(Map<String, Object> paramMap);
	/**
	 * 新增单页
	 * @param paramMap
	 * @return
	 */
	public int insertCustom(Map<String, Object> paramMap);
	/**
	 * 更新单页
	 * @param paramMap
	 * @return
	 */
	public int updateCustom(Map<String, Object> paramMap);
	/**
	 * 查询单页排序ID
	 * @param id
	 * @return
	 */
	public Map<String, Object> queryCustomById(long id);
	/**
	 * 删除单页
	 * @param paramMap
	 * @return
	 */
	public int deleteCustom(Map<String, Object> paramMap);
	/**
	 * 栏目名称是否重复
	 * @param paramMap
	 * @return
	 */
	public int queryProgramaByName(Map<String, Object> paramMap);
	/**
	 * 新增栏目
	 * @param paramMap
	 * @return
	 */
	public int insertPrograma(Map<String, Object> paramMap);
	/**
	 * 更新栏目
	 * @param paramMap
	 * @return
	 */
	public int updatePrograma(Map<String, Object> paramMap);
	/**
	 * 查询栏目排序ID
	 * @param id
	 * @return
	 */
	public Map<String, Object> queryProgramaById(long id);
	/**
	 * 是否存在关联单页
	 * @param paramMap
	 * @return
	 */
	public int queryCustomByArticleInfoId(Map<String, Object> paramMap);
	/**
	 * 删除栏目
	 * @param paramMap
	 * @return
	 */
	public int deletePrograma(Map<String, Object> paramMap);
	/**
	 * 栏目下拉框
	 * @return
	 */
	public List<Map<String, Object>> queryProgramaCombobox();
	/**
	 * 固定单页列表
	 * @return
	 */
	public List<Map<String, Object>> queryFixedList();
	/**
	 * 查询单页详情
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryArticleDetail(Map<String, Object> paramMap);

}
