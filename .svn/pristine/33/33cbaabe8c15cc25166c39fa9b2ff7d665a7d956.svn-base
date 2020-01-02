package com.tk.oms.sysuser.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tk.oms.sysuser.entity.SysRole;
import com.tk.oms.sysuser.entity.SysUserInfo;
import com.tk.oms.sysuser.entity.SysUserRole;

/**
 * @author wangpeng
 * @data 2016/4/25 001.
 *
 */
@Repository
public interface SysUserRoleDao {

    /**
     * 配置用户角色 
     * @param list
     */
    public int insert(HashMap<String,Object> paramMap);
    
    /**
     * 删除用户角色相关关联信息
     * @param userrole
     */
    public int delete(HashMap<String,Object> paramMap);

    /**
     * 查询角色列表，如果有权限则选中
     * @param userrole
     * @return
     */
    public List<SysRole> queryUserRoleList(HashMap<String,Object> paramMap);
    
    /**
     * 查询用户角色权限中可使用的菜单或按钮节点列表 
     * @param userrole
     * @return
     */
    public List<Map<String,Object>> queryList(SysUserInfo userinfo);
    
    /**
     * 查询用户角色权限中不可使用的字段列表 
     * @param userrole
     * @return
     */
    public List<Map<String,Object>> queryNoFieldList(long user_id);
}
