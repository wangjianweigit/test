package com.tk.oms.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface SysConfigDao {
	/**
     * 获取系统配置列表数据
     * @param request
     * @return
     */
	public Map<String,Object> querySysConfigList(Map<String, Object> paramMap);
	/**
     * 新增系统配置
     * @param request
     * @return
     */
	public int insertSysConfig(Map<String, Object> paramMap);
	/**
     * 系统配置编辑更新
     * @param request
     * @return
     */
	public int updateSysConfig(Map<String, Object> paramMap);
	/**
     * 系统配置删除
     * @param request
     * @return
     */
	public int deleteSysConfig(Map<String, Object> paramMap);
	/**
     * 系统配置数量
     * @param request
     * @return
     */
	public int querySysConfigCount(Map<String, Object> paramMap);

	/**
	 * 获取系统配置参数信息
	 *
	 * @param key
	 * @return
     */
	public String querySysParamConfig(@Param("key") String key);
}
