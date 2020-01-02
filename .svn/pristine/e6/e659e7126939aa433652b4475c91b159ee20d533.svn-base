package com.tk.oms.marketing.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ActivitySiteLinkDao {


    /**
     * 批量添加
     * @param param
     * @return
     */
    int batchInsert(Map<String, Object> param);

    /**
     * 根据活动ID删除信息
     * @param activity_id
     * @return
     */
    int deleteByActivityId(@Param("activity_id") int activity_id);

    /**
     * 获取列表数据
     * @param activity_id
     * @return
     */
    List<Map<String, Object>> queryList(@Param("activity_id") int activity_id);

}