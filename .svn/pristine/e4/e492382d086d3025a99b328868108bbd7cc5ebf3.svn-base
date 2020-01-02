package com.tk.oms.marketing.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ActivityDetailDao {

    /**
     * 获取活动详情
     * @param activity_id
     * @return
     */
    Map<String, Object> queryByActivityId(@Param("activity_id") long activity_id);

    /**
     * 新增活动详情
     * @param param
     * @return
     */
    int insert(Map<String, Object> param);

    /**
     * 更新活动详情
     * @param param
     * @return
     */
    int update(Map<String, Object> param);

    /**
     *
     * @param param
     * @return
     */
    int updateDecorate(Map<String, Object> param);

    /**
     * 更新是否推荐至首页状态
     * @param paramMap
     * @return
     */
    int updateActivityRecommen(Map<String, Object> paramMap);

    /**
     * 查询预售活动SKU预售数量
     * @param params
     * @return
     */
    List<Map<String, Object>> queryActivityProductSkuList(Map<String,Object> params);
}
