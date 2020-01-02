package com.tk.oms.sysuser.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tk.oms.sysuser.entity.SysField;
import com.tk.oms.sysuser.entity.SysRoleField;

/**
 * @author wangpeng
 * @data 2016/4/25 001.
 *
 */
@Repository
public interface SysRoleFieldDao {

    /**
     * 新增角色相关字段节点
     * @param list
     */
    public int insert(HashMap<String,Object> paramMap);
    
    /**
     * 删除角色字段节点的相关关联信息
     * @param rolenode
     */
    public int delete(HashMap<String,Object> paramMap);

    /**
     * 查询字段节点列表，如果有权限则选中
     * @param rolenode
     * @return
     */
    public List<SysField> queryList(SysRoleField rolefield);
}
