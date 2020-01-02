package com.tk.oms.sysuser.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tk.oms.sysuser.entity.SysUserInfo;

/**
 * @author wangpeng
 * @data 2017/4/18 001.
 *
 */
@Repository
public interface SysUserSiteDao {

    /**
     * 配置用户站点
     * @param list
     */
    public int insert(Map<String,Object> paramMap);
    
    /**
     * 删除用户站点相关关联信息
     * @param userrole
     */
    public int delete(Map<String,Object> paramMap);

    /**
     * 查询站点列表，如果有权限则选中
     * @param userrole
     * @return
     */
    public List<Map<String,Object>> queryUserSiteList(Map<String,Object> paramMap);
    
    /**
     * 查询用户中可使用的站点列表
     * @param userrole
     * @return
     */
    public List<Map<String,Object>> queryList(SysUserInfo userInfo);
    
}
