package com.tk.oms.decoration.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MobileVideoDao {

    /**
     * 获取视频分类列表
     * @param params
     * @return
     */
    List<Map<String, Object>> queryCategoryList(Map<String, Object> params);

    /**
     * 根据ID获取详情
     * @param id
     * @return
     */
    Map<String, Object> queryById(@Param("id") long id);

    /**
     * 根据ID删除数据
     * @param id
     * @return
     */
    int delete(@Param("id") long id);

    /**
     * 新增
     * @param params
     * @return
     */
    int insert(Map<String, Object> params);

    /**
     * 修改
     * @param params
     * @return
     */
    int update(Map<String, Object> params);

    /**
     * 查询当前ID排序
     * @param id
     * @return
     */
    Map<String, Object> querySortById(@Param("id") long id);

    /**
     * 更新商品排序问题
     * @param params
     * @return
     */
    int updateSort(Map<String, Object> params);
}
