package com.tk.oms.decoration.dao;

import com.tk.sys.common.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 导航管理
 * @author zhenghui
 * @data 2017.1.16
 */
@Repository
public interface NavigationDao extends BaseDao<Map<String,Object>>{

    /**
     * 获取页面列表
     * @param params
     * @return
     */
    List<Map<String,Object>> queryPageList(Map<String,Object> params);

    /**
     * 导航排序
     * @param params
     * @return
     */
    int updateNavSort(Map<String,Object> params);

    /**
     * 添加导航广告
     * @param params
     * @return
     */
    int addNavDrumbeating(Map<String,Object> params);

    /**
     * 获取导航广告详情
     * @param params
     * @return
     */
    Map<String,Object> queryNavDrumbeatingDetail(Map<String,Object> params);

    /**
     * 更新导航广告
     * @param params
     * @return
     */
    int updateNavDrumbeating(Map<String,Object> params);
}
