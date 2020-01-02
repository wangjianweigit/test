package com.tk.oms.sys.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2018, TongKu
 * FileName : AppVersionDao
 * 应用程序版本管理数据库访问层
 *
 * @author zhenghui
 * @version 1.00
 * @date 2018-06-12
 */
@Repository
public interface AppVersionDao {

    /**
     * 分页查询App版本记录列表
     * @param params
     * @return
     */
    List<Map<String,Object>> listAppVersionForPage(Map<String,Object> params);

    /**
     * 查询App版本记录列表总数量
     * @param params
     * @return
     */
    int countAppVersionForPage(Map<String,Object> params);

    /**
     * 根据ID查询App版本
     * @param param
     * @return
     */
    Map<String,Object> getAppVersionById(@Param("version_id") long param);

    /**
     * 新增App版本
     * @param params
     * @return
     */
    int insertAppVersion(Map<String,Object> params);

    /**
     * 更新App版本
     * @param params
     * @return
     */
    int updateAppVersion(Map<String,Object> params);

    /**
     * 删除App版本
     * @param param
     * @return
     */
    int deleteAppVersion(@Param("version_id") long param);

    /**
     * 根据版本号查询版本数量
     * @param params
     * @return
     */
    int countAppVersionByVersion(Map<String,Object> params);

    /**
     * 查询最大的版本代号
     * @param params
     * @return
     */
    int getMaxVersionCode(Map<String,Object> params);
}
