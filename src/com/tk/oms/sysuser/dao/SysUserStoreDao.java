package com.tk.oms.sysuser.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tk.oms.sysuser.entity.SysUserInfo;
import com.tk.oms.sysuser.entity.SysUserStore;

/**
 * @author wangpeng
 * @data 2016/4/25 001.
 *
 */
@Repository
public interface SysUserStoreDao {

    /**
     * 配置用户门店 
     * @param list
     */
    public int insert(Map<String,Object> paramMap);
    
    /**
     * 删除用户门店相关关联信息
     * @param userstore
     */
    public int delete(Map<String,Object> paramMap);

    /**
     * 查询门店列表，如果有权限则选中
     * @param userstore
     * @return
     */
    public List<Map<String,Object>> queryUserStoreList(Map<String,Object> paramMap);
    
    /**
     * 查询登录用户信息门店
     * @param userstore
     * @return
     */
    public List<Map<String,Object>> queryUserStoreInfo(SysUserInfo sysUser);
    /**
     * 删除用户关联的私有门店
     * @param map
     * @return
     */
    public int deleteUserPvtpStore(Map<String, Object> map);
	/**
     * 新增用户关联的私有门店
     * @param map
     * @return
     */
    public int insertUserPvtpStore(Map<String, Object> map);
	/**
	 * 查询私有门店列表，如果有权限则选中
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryUserPvtpStoreList(Map<String, Object> map);
}
