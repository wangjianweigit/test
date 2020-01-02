package com.tk.oms.sysuser.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tk.sys.common.BaseDao;

/**
 * @author wangpeng
 * @data 2017/5/10 001.
 *
 */
@Repository
public interface SysOrganizationDao extends BaseDao<Map<String, Object>>{

    /**
     * 查询组织机构列表信息
     * @param map
     * @return
     */
    public List<Map<String, Object>> querySysOrganizationList(Map<String, Object> map);
	
	/**
     * 批量更新
     * @param updateList
     * @return
     */
	public int batchUpdate(List<Map<String, Object>> updateList);
	
	/**
	 * 删除组织架构信息
	 * @param params
	 * @return
	 */
	public int delete(Map<String, Object> params);
	
    /**
     * 根据父节点ID查询所有的子节点
     * @return
     */
    public Map<String,Object> queryChildrenByParentId(Map<String, Object> map);
    
    /**
     * 查询所有组织机构列表信息
     * @param map
     * @return
     */
    public List<Map<String, Object>> queryAllList();
}
