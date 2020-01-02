package com.tk.oms.basicinfo.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ContractTemplateDao {


    /**
     * 获取分页列表
     * @param params
     * @return
     */
    List<Map<String, Object>> queryListForPage(Map<String, Object> params);

    /**
     * 获取列表总数
     * @param params
     * @return
     */
    int queryCountForPage(Map<String, Object> params);

    /**
     * 新增数据
     * @param params
     */
    int insert(Map<String, Object> params);
    /**
     * 更新数据
     * @param params
     */
    int update(Map<String, Object> params);

    /**
     * 令同公司同类型的其他发布状态的模板失效
     * @param params
     * @return
     */
    int invalidOther(Map<String, Object> params);

    /**
     * 根据ID获取数据
     * @param id
     * @return
     */
    Map<String, Object> queryById(@Param("id") long id);

    /**
     * 根据ID删除数据
     * @param id
     * @return
     */
    int deleteById(@Param("id") long id);

}
