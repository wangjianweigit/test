package com.tk.oms.sysuser.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tk.oms.sysuser.entity.SysRole;

/**
 * @author wangpeng
 * @data 2016/4/25 001.
 *
 */
@Repository
public interface SysRoleDao {

    /**
     * 新增角色
     * @param role
     */
    public int insert(HashMap<String,Object> paramMap);
    /**
     * 删除角色
     * @param role
     */
    public int delete(HashMap<String,Object> paramMap);

    /**
     * 修改角色
     * @param role
     */
    public int update(HashMap<String,Object> paramMap);

    /**
     * 查询角色列表
     * @param params
     * @return
     */
    public List<SysRole> queryList(Map<String, Object> params);
    
    /**
     * 查询角色列表总数
     * @param params
     * @return
     */
    public int queryCount(Map<String, Object> params);
    
    /**
     * 查询角色详情
     * @param role
     * @return
     */
    public SysRole queryById(Map<String, Object> params);
    
    /**
     * 判断是否有重复的角色名称
     * @param role
     * @return
     */
    public int queryByRoleName(Map<String, Object> params);
}
