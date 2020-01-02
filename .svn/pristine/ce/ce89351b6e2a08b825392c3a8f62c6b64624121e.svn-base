package com.tk.oms.member.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2017, Tongku
 * FileName : DecorationUserManageDao
 * 装修用户管理数据访问接口
 *
 * @author zhenghui
 * @version 1.00
 * @date 2017/09/14
 */
@Repository
public interface DecorationUserManageDao {

    /**
     * 查询装修用户列表数据
     * @param params
     * @return
     */
    List<Map<String, Object>> queryDecorationUserList(Map<String, Object> params);

    /**
     * 查询装修用户列表总数量
     * @param params
     * @return
     */
    int queryDecorationUserCount(Map<String, Object> params);

    /**
     * 更新用户装修状态
     * @param params
     * @return
     */
    int updateDecorationStateById(Map<String, Object> params);
}
