package com.tk.oms.marketing.dao;

import com.tk.oms.basicinfo.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2017, TongKu
 * FileName : UserGroupDao
 * 用户分组管理数据库操作类
 *
 * @author zhenghui
 * @version 1.00
 * @date 2017/09/21
 */
@Repository
public interface UserGroupDao extends BaseDao<Map<String, Object>> {

    /**
     * 通过分组ID分页查询用户分组的用户列表
     * @param params
     * @return
     */
    List<Map<String,Object>> queryUserListById(Map<String,Object> params);

    /**
     * 通过分组ID分页查询用户分组的用户总数量
     * @param params
     * @return
     */
    int queryUserCountById(Map<String,Object> params);

    /**
     * 查询用户分组列表
     * @param params
     * @return
     */
    List<Map<String,Object>> queryUserGroupList(Map<String,Object> params);

    /**
     * 新增用户分组详细信息
     * @param params
     * @return
     */
    int insertUserGroupDetail(Map<String,Object> params);

    /**
     * 删除用户分组详细信息
     * @param params
     * @return
     */
    int deleteUserGroupDetail(Map<String,Object> params);

    /**
     * 删除用户分组的用户
     * @param params
     * @return
     */
    int deleteUserGroupForUser(Map<String,Object> params);

    /**
     * 通过分组名称查询用户分组数量
     * @param params
     * @return
     */
    int queryUserGroupCountByName(Map<String,Object> params);

    /**
     * 通过分组ID查询装修导航数量
     * @param params
     * @return
     */
    int queryDecorateNavCountById(Map<String,Object> params);

    /**
     * 通过分组ID查询装修组件数量
     * @param params
     * @return
     */
    int queryPageModuleCountById(Map<String,Object> params);

    /**
     * 通过分组ID查询订货会活动数量
     * @param params
     * @return
     */
    int queryActivityCountById(Map<String,Object> params);
}
