package com.tk.oms.sysuser.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * @author wangpeng
 * @data 2016/4/25 001.
 *
 */
@Repository
public interface SysRoleNodeDao {

    /**
     * 新增角色相关菜单或节点
     * @param list
     */
    public int insert(HashMap<String,Object> paramMap);
    
    /**
     * 删除角色或节点的相关关联信息
     * @param rolenode
     */
    public int delete(HashMap<String,Object> paramMap);

    /**
     * 查询菜单或按钮节点列表，如果有权限则选中
     * @param rolenode
     * @return
     */
    public List<HashMap<String,Object>> queryList(HashMap<String,Object> paramMap);
}
