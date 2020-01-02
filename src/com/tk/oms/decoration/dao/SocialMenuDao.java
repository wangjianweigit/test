package com.tk.oms.decoration.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SocialMenuDao {

    /**
     * 查询列表
     * @param params
     * @return
     */
    List<Map<String, Object>> queryListForPage(Map<String, Object> params);

    /**
     * 查询总数
     * @param params
     * @return
     */
    int queryCountForPage(Map<String, Object> params);

    /**
     * 新增
     * @param params
     * @return
     */
    int insert(Map<String, Object> params);

    /**
     * 更新
     * @param params
     * @return
     */
    int update(Map<String, Object> params);

    /**
     * 删除
     * @param params
     * @return
     */
    int delete(Map<String, Object> params);

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

    /**
     * 获取详情
     * @param params
     * @return
     */
    Map<String, Object> queryDetail(Map<String, Object> params);

    /**
     * 更新非当前ID的其余导航的默认状态
     * @param params
     * @return
     */
    int updateByDefault(Map<String, Object> params);


}
